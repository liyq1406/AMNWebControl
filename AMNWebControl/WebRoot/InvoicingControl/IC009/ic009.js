	var commoninfodivmaster=null;	//产品入库主档列表
   	var commoninfodivdetial=null;	//产品入库明细列表
   	var commoninfodivdetial_barcode=null; //条码卡列表
   	var strCurCompId="";
   	var strCurBillId="";
   	var curMasterInfoDate=null;
   	var fromValidate=null;
   	var cinsertwaremanager = null;
   	var cinsertcardtypemanager = null;
   	var cinsertpermanager = null;
   	var ic009layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
   	var insertypemanager=null;
   	var curDMasterlRecord = null;
	var curDetialRecord = null;
   	var insertypeData=JSON.parse(parent.loadCommonControlDate_select("RKFS",0));
   	var commonQHBZData=JSON.parse(parent.loadCommonControlDate_select("QHBZ",0));
   	var showDialogmanager=null;
    var rightmenu;
    var iGoodsBarCode="";
    var istartBarCode="";
    var iendBarCode="";
    var confirmInfo=null;
    var showDialog=null;
    var cancelInfo=null;
    var allDownLoad=null;
    var lsDgoodsorderinfo=null;
    var statechooseData = [{ choose: 0, text: '登记' }, { choose: 1, text: '已复合'}, { choose: 2, text: '已下单'}, { choose: 3, text: '已发货'}, { choose: 4, text: '已收货'}];
   	//初始化属性
    $(function ()
   	{
	   try
	   {  
	   	
	   	 	  //布局
            ic009layout= $("#ic009layout").ligerLayout({ leftWidth: 270,rightWidth: 245,  allowBottomResize: false, isLeftCollapse:true});
	   		var height = $(".l-layout-center").height();
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '申请门店',	name: 'bordercompid', width:80,align: 'left'	},
                { display: '申请单号', 	name: 'borderbillid', 	width:120,align: 'left'},
                { display: '单据状态', 	name: 'orderstate', 		width:90,align: 'left', 
	                editor: { type: 'select', data: statechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.orderstate == 0) return '登记';
	                    else if (item.orderstate == 1) return '已复合';
	                    else if (item.orderstate == 2) return '已下单';
	                    else if (item.orderstate == 3) return '已发货';
	                    else if (item.orderstate == 4) return '已收货';
	                    else if (item.orderstate == 5) return '已作废';
	                }
	            },
	            { display: '申请日期', 	name: 'orderdate', 		width:80,align: 'left'}
	           
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '380',
                height:'685',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: false,usePager: false,
                groupColumnName:'orderbilltype',
                groupRender: function (orderbilltype,groupdata)
				{
					if(orderbilltype==1)
						return '订单类型[门店采购]';
					else
						return '订单类型[员工采购]';
				},                
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }   
            });
           
     
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '下单单号', 	name: 'strSendBillId',  			width:130,align: 'left' },
                { display: '产品编号', 	name: 'ordergoodsno',  				width:90,align: 'left' },
                { display: '产品名称', 	name: 'ordergoodsname',  			width:270,align: 'left' },
                { display: '单位', 		name: 'ordergoodsunit', 			width:40,align: 'left'},
	            { display: '申请数', 	    name: 'ordergoodscount', 			width:50,align: 'right',
	            	render: function (row) {  
     					var html ="";
     					if(checkNull(row.ordergoodscount)!="")
     					  html="<b><font color=\"blue\"> "+row.ordergoodscount+"</font></b>";  
     					return html;  
 					}   },
	            { display: '单价', 		name: 'ordergoodsprice', 			width:40,align: 'right' },
	            { display: '金额', 		name: 'ordergoodsamt', 				width:40,align: 'right' }  ,
	            { display: '库存', 		name: 'headstockcount', 			width:40,align: 'right',
	            	render: function (row) {  
     					if(checkNull(row.goodssource)=="1"){
     						return "10000";//供应商产品，库存显示为10000
     					}
     					return row.headstockcount+"";  
 					}
	            },
	            { display: '下单量', 		name: 'downordercount', 			width:50,align: 'right' , editor: { type: 'int' ,onChanged : validateDownordercount},
	            	render: function (row) {  
     					var html ="";
     					if(checkNull(row.downordercount)!="")
     					  html="<b><font color=\"blue\"> "+row.downordercount+"</font></b>";  
     					return html;  
 					}   },
	            { display: '欠货量', 		name: 'nodowncount', 				width:40,align: 'right'},
	            { display: '备注', 	    name: 'ordermark', 				    width:140,align: 'right',
	            	editor: { type: 'select', data: commonQHBZData, valueField: 'choose',selectBoxWidth:155 },
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("QHBZ",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.ordermark)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
							    }
							}
		                    return '';
		                } 
		        },
	            { hide:true	,  name: 'norevicecount' },
	            { hide:true	,  name: 'supplierno' },
	            { hide:true , 	name: 'headwareno'},
	            { hide:true , 	name: 'goodssource'},
	             { hide:true , 	name: 'downorderamt'}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '950',
                height:'490',
                clickToEdit: true,   enabledEdit: true,  checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curDetialRecord=data;
                }
                 ,
                  toolbar: { items: [
	                		{ text: '打印', click: printReport, img: contextURL+'/common/ligerui/ligerUI/skins/icons/print.gif' }
    				]}	
            });
            
          	$('#thetable').tableScroll({
				width:280,
				height:620
			});
			$("#strSearchDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'140' });
			
           	 $("#searchButton").ligerButton(
	         {
	             text: '查询单据', width: 100,
		         click: function ()
		         {
		             searchOrderBill();
		         }
	         });
	        allDownLoad=$("#allDownLoad").ligerButton(
	         {
	             text: '全部下单', width: 140,
		         click: function ()
		         {
		             handallDownLoad();
		         }
	         });
	         
           	 confirmInfo=$("#confirmInfo").ligerButton(
	         {
	             text: '总部下单', width: 140,
		         click: function ()
		         {
		        	 confirmInfo.setDisabled();
		             handconfirmInfo();
		         }
	         });
	          cancelInfo=$("#cancelInfo").ligerButton(
	         {
	             text: '申请单作废', width: 140,
		         click: function ()
		         {
		             handcancelInfo();
		         }
	         });
          	$("#pageloading").hide(); 
          	addMasterRecord();
          	addDetialRecord();
    		loadAppOrder();
          	
   		}catch(e){alert(e.message);}
    }); 
   
		//加载门店信息
	function loadAppOrder()
    {
        try{
        	var params = "";			
     		var requestUrl ="ic009/loadGoodsOrderInfo.action"; 
			var responseMethod="loadGoodsOrderInfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
   
   
   function loadGoodsOrderInfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMgoodsorderinfo!=null && responsetext.lsMgoodsorderinfo.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMgoodsorderinfo,Total: responsetext.lsMgoodsorderinfo.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivmaster.loadData(true);   
            	commoninfodivmaster.select(0);
            	        	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true); 
            	addMasterRecord(); 
	   		}
			
			
	 
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
		try
		{
	    	document.getElementById("ordercompid").value=checkNull(curMaster.bordercompid);
	    	document.getElementById("orderbillid").value=checkNull(curMaster.borderbillid);
	    	document.getElementById("orderbilltype_h").value=checkNull(curMaster.orderbilltype);
	    	document.getElementById("orderbilltype").value=checkNull(curMaster.orderbilltype);
	    	document.getElementById("orderstate").value=checkNull(curMaster.orderstate);
	    	document.getElementById("orderstaffid").value=checkNull(curMaster.orderstaffid);
	    	document.getElementById("bordercompname").value=checkNull(curMaster.bordercompname);
	    	document.getElementById("orderstaffname").value=checkNull(curMaster.orderstaffname);
	    	document.getElementById("storewareid").value=checkNull(curMaster.storewareid);
	    	document.getElementById("storewarename").value=checkNull(curMaster.storewarename);
	    	document.getElementById("orderdate").value=checkNull(curMaster.orderdate);
	    	document.getElementById("ordertime").value=checkNull(curMaster.ordertime);
	    	document.getElementById("headwareid").value=checkNull(curMaster.headwareid);
	    	document.getElementById("headwarename").value=checkNull(curMaster.headwarename);
	    	document.getElementById("sendbillno").value=checkNull(curMaster.sendbillno);
	    	document.getElementById("downorderdate").value=checkNull(curMaster.downorderdate);
	    	document.getElementById("downordertime").value=checkNull(curMaster.downordertime);
	    	document.getElementById("revicebillno").value=checkNull(curMaster.revicebillno);
	    	document.getElementById("downorderstaffid").value=checkNull(curMaster.downorderstaffid);
	    	document.getElementById("downorderstaffname").value=checkNull(curMaster.downorderstaffname);
	    	if(checkNull(curMaster.orderbilltype)==1)
	    		document.getElementById("lbOrderTypeText").innerHTML=" 门店采购"
	    	else
	    		document.getElementById("lbOrderTypeText").innerHTML=" 员工采购"
			if(document.getElementById("orderstate").value==1)
			{
				confirmInfo.setEnabled();
				cancelInfo.setEnabled();
				allDownLoad.setEnabled();
				pageWriteState();
			}
			else 
			{
				confirmInfo.setDisabled();
				cancelInfo.setDisabled();
				allDownLoad.setDisabled();
				pageReadState();
			}
			
		}catch(e){alert(e.message);}
    }
    

    function pageWriteState()
    {
    	try
	    {
	    	document.getElementById("downorderstaffid").readOnly="";
	    	commoninfodivdetial.options.enabledEdit=true;
    	}catch(e){alert(e.message);}
    }
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("downorderstaffid").readOnly="readOnly";
	   		commoninfodivdetial.options.enabledEdit=false;
    	}catch(e){alert(e.message);}
    }
    
    //根据主档选择加载明细
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		 //开启屏蔽窗口
        	 	 //addcloud();
        		 showDialog=$.ligerDialog.waitting('正在查询中,请稍候...');
        		 if(curpagestate!=3)
        		 {
        		 	$.ligerDialog.warn("请保持当前信息或刷新本界面后在进行切换操作!");
        		 	return ;
        		 }
        		 strCurCompId=data.bordercompid;
        		 strCurBillId=data.borderbillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"ic009/loadDorderInfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsDgoodsorderinfo!=null && responsetext.lsDgoodsorderinfo.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsorderinfo,Total: responsetext.lsDgoodsorderinfo.length});
					            	commoninfodivdetial.loadData(true);    
					            	document.getElementById("needPost").value="0";   
					            	lsDgoodsorderinfo=responsetext.lsDgoodsorderinfo;
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
					            	addDetialRecord();
					            	document.getElementById("needPost").value="1"; 
						   		}
	   							loadCurMaster(responsetext.curMgoodsorderinfo);
	   							//关闭屏蔽窗口
	   							//removecloud();
	   							showDialog.close();
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
    }
    
  
 
   //--------------------------验证验货人员-----------------
   function validateInserper(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("orderstaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic009/validateInserper.action";
        var params="strCurCompId="+document.getElementById("ordercompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validateInserperMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validateInserperMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("orderstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("orderstaffid").value="";
	       		document.getElementById("orderstaffname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	//--------------------------验证验货人员-----------------
   function validatedowner(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("downorderstaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic009/validatedowner.action";
        var params="strCurEmpId="+obj.value;
		var responseMethod="validatedownerMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validatedownerMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("downorderstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("downorderstaffid").value="";
	       		document.getElementById("downorderstaffname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	



   	function addMasterRecord()
    {
    		var row = commoninfodivmaster.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivmaster.addRow({ 
				  bordercompid: "",
				  borderbillid: "",
				  orderdate: "",
				  sendbillno: "",
				  revicebillno: ""          
			}, row, false);
    }
    
    function addDetialRecord()
    {
    		var row = commoninfodivdetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({ 
				      ordergoodsno: "",
				      ordergoodsname: "",
				      ordergoodsunit: "",
				      ordergoodscount: "" ,
				      ordergoodsprice: "",
				      ordergoodsamt: "",
				      downordercount: "",
				      nodowncount: "",
				      norevicecount: "",
				      supplierno:"",
				      headwareno:"",
				      goodssource:""
			}, row, false);
    }

	            
    function deletedetialRecord()
    {
    	commoninfodivdetial.deleteSelectedRow();
    	
    }
    
    function addDetialBarnoRecord()
    {
    		var row = commoninfodivdetial_barcode.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_barcode.addRow({ 
				    
			}, row, false);
    }
    
  
    function validateFromBarno(obj)
    {
    	alert(obj.value);
    }
	function validateToBarno(obj)
    {
    	alert(obj.value);
    }
    
     //---------------------------------------------回车事件
		function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
			}
			
		}
		
		function  hotKeyEnter()
		{
		
			try
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
				
			}
			catch(e){alert(e.message);}
				
		}   
		
		
		
		
    
    //复合入库单
    function handconfirmInfo()
    {
    	showDialogmanager = $.ligerDialog.waitting('正在下单中,请稍候...');
        if(parent.hasFunctionRights( "IC009",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
        if(commoninfodivdetial.rows.length*1>0 )
		{
			   commoninfodivdetial.endEdit();
		}
	     if(document.getElementById("downorderstaffid").value=="")
		{
			$.ligerDialog.error('请输入下单员工!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic009/handconfirmInfo.action";
		var params=queryStringTmp;
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        var needPost=1;
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.ordergoodsno)=="")
					continue;
				if(checkNull(row.goodssource)=="1" || checkNull(row.goodssource)*1==1){//产品来源为供应商，则不校验库存量
					if(checkNull(row.downordercount)*1>checkNull(row.ordergoodscount)*1)
					{
						$.ligerDialog.error(checkNull(row.ordergoodsname)+'下单数量不能超过订单数量!');
						showDialogmanager.close();
						curpagestate=2;
						return;
					}
				}else{
					if(checkNull(row.downordercount)*1>checkNull(row.ordergoodscount)*1
							|| checkNull(row.downordercount)*1>checkNull(row.headstockcount)*1)
					{
						$.ligerDialog.error(checkNull(row.ordergoodsname)+'下单数量不能超过订单数量和现有库存量!');
						showDialogmanager.close();
						curpagestate=2;
						return;
					}
				}
				
				if(checkNull(row.downordercount)*1==0)
				{
					needPost=0;
				}
					
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				/*if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}	*/            		   
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		var responseMethod="handconfirmInfoMessage";		
		if(needPost==0)	
		{
			$.ligerDialog.confirm('下单明细中有下单数量为0,是否继续下单!', function (result)
			{
				if( result==true)
			    {
			            	
					sendRequestForParams_p(requestUrl,responseMethod,params );    
			    }
			    else
			    {
			    	confirmInfo.setDisabled();
			       showDialogmanager.close();
			       
			    }
			 });
		}        		 
		else
		{
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
    }
    
    function handconfirmInfoMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 pageReadState();
	        	 confirmInfo.setDisabled();
				 cancelInfo.setDisabled(); 
				 allDownLoad.setDisabled();
				 //发送短信给订单来源为供应商的
				 var url = contextURL+"/ic009/send.action"; 
				 $.post(url, null, function(data){
					 
				 }).error(function(e){$.ligerDialog.error(e.message);});
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	       	showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
    }
      //申请单作废
    function handcancelInfo()
    {
    	$.ligerDialog.confirm('是否作废采购单操作', function (result)
		{
		    if( result==true)
            {
		    	var requestUrl ="ic009/handcancelInfo.action";
		        var params="strCurCompId="+document.getElementById("ordercompid").value;
		        params=params+"&strCurBillId="+document.getElementById("orderbillid").value;
				var responseMethod="handcancelInfoMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
		});
    }
    
     function handcancelInfoMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	$.ligerDialog.success("操作成功!");
	        	pageReadState();
	        	confirmInfo.setDisabled();
				cancelInfo.setDisabled(); 
				allDownLoad.setDisabled();
	        }
	        else
	        {
	        	alert(strMessage);
	        }
	       
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    
    function searchOrderBill()
    {
    	if(document.getElementById("strSearchBillno").value=="" && document.getElementById("strSearchDate").value=="")
		{
			$.ligerDialog.error('请填写查询信息!');
			return;
		}
    	var requestUrl ="ic009/searchOrderBill.action";
        var params="strCurBillId="+document.getElementById("strSearchBillno").value;
		var responseMethod="searchOrderBillMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function searchOrderBillMessage(request)
   	{
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMgoodsorderinfo!=null && responsetext.lsMgoodsorderinfo.length>0)
	   		{
	   			curpagestate=3;
	   			curMasterInfoDate={Rows: responsetext.lsMgoodsorderinfo,Total: responsetext.lsMgoodsorderinfo.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivmaster.loadData(true);   
            	commoninfodivmaster.select(0);
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true); 
            	addMasterRecord(); 
	   		}
			
	}
    
    function validateDownordercount(obj)
    {
    	var headstockcount=checkNull(curDetialRecord.headstockcount)*1;
		var ordergoodscount=checkNull(curDetialRecord.ordergoodscount)*1;
		var ordergoodsprice=checkNull(curDetialRecord.ordergoodsprice)*1;
		var goodssource=checkNull(curDetialRecord.goodssource);//产品来源为供应商，则不校验库存量
		if(obj.value*1>ordergoodscount*1)
		{
			$.ligerDialog.error('下单量不能超过申请量!');
			commoninfodivdetial.updateRow(curDetialRecord,{downordercount:0,downorderamt:0,nodowncount :ForDight(ordergoodscount*1,1)});
			return;
		}
		if(goodssource!=1 && obj.value*1>headstockcount*1)
		{
			$.ligerDialog.error('下单量不能超过总部现有库存量!');
			commoninfodivdetial.updateRow(curDetialRecord,{downordercount:0,downorderamt:0,nodowncount :ForDight(ordergoodscount*1,1)});
			return;
		}

		commoninfodivdetial.updateRow(curDetialRecord,{downorderamt: ForDight(ordergoodsprice*1*obj.value*1,1),nodowncount :ForDight(ordergoodscount*1-obj.value*1,1)});
	
    }
 	 
 	function handallDownLoad()
 	{

 		for (var rowid in commoninfodivdetial.records)
		{
	
				var row =commoninfodivdetial.records[rowid];
				var ordergoodsprice=checkNull(row.ordergoodsprice)*1;
				var ordergoodscount=checkNull(row.ordergoodscount)*1;
				commoninfodivdetial.updateRow(row,{downordercount: ordergoodscount,downorderamt: ForDight(ordergoodsprice*1*ordergoodscount*1,1),nodowncount :0});
		}         
 	}
 	
 		function printReport()
		{
			var specailurl=contextURL+'/InvoicingControl/IC009/ic009_print.jsp';
		 	window.open(specailurl,"","top=" +2+ ",left=" + 100+",width=800,height=600,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
		}
		
		
		
function addcloud() { 
//var bodyWidth = document.documentElement.clientWidth; 
var bodyWidth=100;
//var bodyHeight = Math.max(document.documentElement.clientHeight, document.body.scrollHeight); 
var bodyHeight=100;
var bgObj = document.createElement("div" ); 
bgObj.setAttribute( 'id', 'bgDiv' ); 
bgObj.style.position = "absolute"; 
bgObj.style.top = "0"; 
bgObj.style.background = "#000000"; 
bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0,finishOpacity=0" ; 
//bgObj.style.opacity = "0.5"; 
//bgObj.style.left = "1"; 
bgObj.style.width = bodyWidth + "%"; 
bgObj.style.height = bodyHeight + "%"; 
//bgObj.style.zIndex = "100000"; //设置它的zindex属性，让这个div在z轴最大，用户点击页面任何东西都不会有反应| 
document.body.appendChild(bgObj); //添加遮罩 
var loadingObj = document.createElement("div"); 
loadingObj.setAttribute( 'id', 'loadingDiv' ); 
loadingObj.style.position = "absolute"; 
loadingObj.style.top = 30+"%"; 
loadingObj.style.left = 40+"%"; 
loadingObj.style.background = "url(images/load.gif)"; 
loadingObj.style.width = "250"; 
loadingObj.style.height = "250"; 
//loadingObj.style.zIndex = "100000"; 
document.body.appendChild(loadingObj); //添加loading动画- 
} 
function removecloud() { 
$( "#loadingDiv").remove(); 
$( "#bgDiv").remove(); 
} 
 	