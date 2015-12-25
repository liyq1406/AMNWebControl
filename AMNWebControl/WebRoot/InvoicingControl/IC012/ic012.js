   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
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
   	var ic012layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
   	var insertypemanager=null;
   	var curDMasterlRecord = null;
	var curDetialRecord = null;
   	var insertypeData=JSON.parse(parent.loadCommonControlDate_select("RKFS",0));
   	var showDialogmanager=null;
    var rightmenu;
    var iGoodsBarCode="";
    var istartBarCode="";
    var iendBarCode="";
    var confirmInfo=null;
    var cancelInfo=null;
     	var postState=0;
    var statechooseData = [{ choose: 0, text: '登记' }, { choose: 1, text: '已售后'}];
   	//初始化属性
    $(function ()
   	{
	   try
	   {  
	   	
	   	 	  //布局
            ic012layout= $("#ic012layout").ligerLayout({ leftWidth: 270,rightWidth: 245,  allowBottomResize: false, isLeftCollapse:true});
	   		var height = $(".l-layout-center").height();
           //门店树形结构
   			compTree=$("#companyTree").ligerTree(
        	{
           		data: parent.complinkInfo,
           		idFieldName :'id',           		
           		onSelect: compSelect,
           		nodeWidth: 140,
           		slide: false,
           		checkbox: false,
           		height:'100%'
          	});
          	compTreeManager = $("#companyTree").ligerGetTreeManager();
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { 	name: 'breceiptcompid', hide:true	},
                { display: '收货单号', 	name: 'breceiptbillid', 	width:135,align: 'left'},
                { display: '单据状态', 	name: 'receiptstate', 		width:70,align: 'left', 
	                editor: { type: 'select', data: statechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.receiptstate == 0) return '登记';
	                    else if (item.receiptstate == 1) return '已收货';
	                }
	            },
	            { display: '收货日期', 	name: 'receiptdate', 		width:80,align: 'left'},
	            { display: '发货单号', 	name: 'receiptsendbillid', 	width:125,align: 'left'},
	            { display: '订单单号', 	name: 'receiptorderbillid', 	width:120,align: 'left'}   
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '540',
                height:'675',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }
            });
           
     
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '产品编号', 	name: 'receiptgoodsno',  				width:110,align: 'left' },
                { display: '产品名称', 	name: 'receiptgoodsname',  				width:150,align: 'left' },
                { display: '单位', 		name: 'receiptgoodsunit', 				width:50,align: 'left'},
	            { display: '采购数量', 	name: 'ordergoodscount', 				width:60,align: 'right'},
	            { display: '发货数量', 	name: 'sendgoodscount', 				width:60,align: 'right'},
	            { display: '采购单价', 	name: 'receiptprice', 					width:60,align: 'right' },
	            { display: '收货数量', 	name: 'receiptgoodscount', 				width:60,align: 'right' , editor: { type: 'int',onChanged : validatereceiptcount}},
	            { display: '损坏数量', 	name: 'damagegoodscount', 				width:60,align: 'right' , editor: { type: 'int'}},
	            { display: '欠货数量', 	name: 'debegiidscount', 				width:60,align: 'right'  , editor: { type: 'int'}},
	            { display: '收货金额', 	name: 'receiptgoodsamt', 				width:60,align: 'right'}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '750',
                height:'500',
                clickToEdit: true,   enabledEdit: true,  checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curDetialRecord=data;
                }
                
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
	         
           	 confirmInfo=$("#confirmInfo").ligerButton(
	         {
	             text: '确认收货', width: 100,
		         click: function ()
		         {
		             handconfirmInfo();
		         }
	         });
           	$("#printButton").ligerButton(
   	         {
   	             text: '打印', width: 100,
   		         click: function (){
   		        	$.ligerDialog.confirm('是否需要打印？', function (result){
   		        		if(result){
   				          	viewTicketReport();
   		        		}
   		        	});
   		         }
   	         });
          	$("#pageloading").hide(); 
          	addMasterRecord();
          	addDetialRecord();
          	f_selectNode();
          	
   		}catch(e){alert(e.message);}
    }); 
    //选择门店
    function f_selectNode()
	{
		var parm = function (data)
		{
		   	return data.id== parent.localCompid;
		};		
		compTree.selectNode(parm);
		
	}
	
		//加载门店信息
	function compSelect(note)
    {
        try{
        	strCurCompId=note.data.id;
       		var params = "strCurCompId="+note.data.id;				
     		var requestUrl ="ic012/loadGoodsOrderInfo.action"; 
			var responseMethod="loadGoodsOrderInfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
   
   
   function loadGoodsOrderInfoMessage(request)
   {
       		try
        	{
	 			curpagestate=3;
	        	var responsetext = eval("(" + request.responseText + ")");
	    		if(responsetext.lsMgoodsreceipt!=null && responsetext.lsMgoodsreceipt.length>0)
		   		{
		   			curMasterInfoDate={Rows: responsetext.lsMgoodsreceipt,Total: responsetext.lsMgoodsreceipt.length};
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
			$("#p_receiptbillid").html(checkNull(curMaster.breceiptbillid));
			$("#p_receiptcompid").html(checkNull(curMaster.breceiptcompid));
			$("#p_breceiptcompname").html(checkNull(curMaster.breceiptcompname));
			$("#p_receiptstaffid").html(checkNull(curMaster.receiptstaffid));
			$("#p_receiptstaffname").html(checkNull(curMaster.receiptstaffname));
			$("#p_receiptdate").html(checkNull(curMaster.receiptdate));
			$("#p_receipttime").html(checkNull(curMaster.receipttime));
	    	document.getElementById("receiptcompid").value=checkNull(curMaster.breceiptcompid);
	    	document.getElementById("breceiptcompname").value=checkNull(curMaster.breceiptcompname);
	    	document.getElementById("receiptbillid").value=checkNull(curMaster.breceiptbillid);
	    	document.getElementById("receiptdate").value=checkNull(curMaster.receiptdate);
	    	document.getElementById("receipttime").value=checkNull(curMaster.receipttime);
	    	document.getElementById("receiptwareid").value=checkNull(curMaster.receiptwareid);
	    	document.getElementById("receiptwarename").value=checkNull(curMaster.receiptwarename);
	    	document.getElementById("receiptstaffid").value=checkNull(curMaster.receiptstaffid);
	    	document.getElementById("receiptstaffname").value=checkNull(curMaster.receiptstaffname);
	    	document.getElementById("receiptsendbillid").value=checkNull(curMaster.receiptsendbillid);
	    	document.getElementById("receiptorderbillid").value=checkNull(curMaster.receiptorderbillid);
	    	document.getElementById("orderbilltype").value=checkNull(curMaster.orderbilltype);
	    	document.getElementById("receiptstate").value=checkNull(curMaster.receiptstate);
	    	document.getElementById("receiptopationerid").value=checkNull(curMaster.receiptopationerid);
	    	document.getElementById("receiptopationdate").value=checkNull(curMaster.receiptopationdate);
	    	if(document.getElementById("receiptstate").value==0)
			{
				pageWriteState();
				curpagestate=1;
			}
			else
			{
				pageReadState();
			}
			if(document.getElementById("receiptstate").value==0)
			{
				confirmInfo.setEnabled();
			}
			else
			{
				confirmInfo.setDisabled();
			}
			
		}catch(e){alert(e.message);}
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	document.getElementById("receiptstaffid").readOnly="";
	    
	    	commoninfodivdetial.options.enabledEdit=true;
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("receiptstaffid").readOnly="readOnly";
	    	commoninfodivdetial.options.enabledEdit=false;
    	}catch(e){alert(e.message);}
    }
    
    //根据主档选择加载明细
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        	
        		 if(curpagestate!=3)
        		 {
        		 	//$.ligerDialog.warn("请保持当前信息或刷新本界面后在进行切换操作!");
        		 	//return ;
        		 }
        		 strCurCompId=data.breceiptcompid;
        		 strCurBillId=data.breceiptbillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"ic012/loadDorderInfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsDgoodsreceipt!=null && responsetext.lsDgoodsreceipt.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsreceipt,Total: responsetext.lsDgoodsreceipt.length});
					            	commoninfodivdetial.loadData(true);
					            	var dgoods = responsetext.lsDgoodsreceipt;
					            	var _html = "";
					            	$.each(dgoods, function(i, goods){
					            		//<td style='width:60px;padding-left:10px;'>"+ goods.receiptgoodsno +"</td>
					            		_html+="<tr class='p_tr'><td style='width:230px;padding:0 5px;'>"+ goods.receiptgoodsname +"</td></tr>";
					            		_html+="<tr class='p_tr'><td style='width:230px;padding-right:10px;'>"+(goods.receiptgoodscount +"&nbsp;"+goods.receiptgoodsunit)+"</td></tr>";
					            	});
					            	$('#printContent table tr.p_tr').remove();
					            	$('#printContent table').append(_html);
					            }
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
					            	addDetialRecord();
					            }
	   							loadCurMaster(responsetext.curMgoodsreceipt);
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
   			document.getElementById("receiptstaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic012/validateInserper.action";
        var params="strCurCompId="+document.getElementById("receiptcompid").value;
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
	       		document.getElementById("receiptstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("receiptstaffid").value="";
	       		document.getElementById("receiptstaffname").value="";
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
				  breceiptcompid: "",
				  breceiptbillid: "",
				  receiptdate: "",
				  receiptsendbillid: "",
				  receiptorderbillid: ""          
			}, row, false);
    }
    
    function addDetialRecord()
    {
    		var row = commoninfodivdetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({ 
				      receiptgoodsno: "",
				      receiptgoodsname: "",
				      receiptgoodsunit: "",
				      receiptprice: "" ,
				      receiptgoodscount: "",
				      receiptgoodsamt: "",
				      sendgoodscount: "",
				      damagegoodscount: "",
				      debegiidscount: "",
				      ordergoodscount:""
			}, row, false);
    }

	            
    function deletedetialRecord()
    {
    	commoninfodivdetial.deleteSelectedRow();
    	
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
    	if(postState==1)
		{
			$.ligerDialog.error("正在保存中,请不要连续保存!");
			return ;
		}
		
   		postState=1;
   		if(commoninfodivdetial.rows.length*1>0 )
		{
			   commoninfodivdetial.endEdit();
		}
        showDialogmanager = $.ligerDialog.waitting('正在复合中,请稍候...');	
        if(parent.hasFunctionRights( "IC012",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       		postState=0;
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
	     if(document.getElementById("receiptstaffid").value=="")
		{
			$.ligerDialog.error('请输入收货员工!');
			postState=0;
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic012/handconfirmInfo.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        var needPost=1;
        //------卡号段列表
        var _html="";
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.receiptgoodsno)=="")
					continue;
				if(checkNull(row.damagegoodscount)*1+checkNull(row.debegiidscount)*1
				+checkNull(row.receiptgoodscount)*1!=checkNull(row.sendgoodscount)*1)
				{
						$.ligerDialog.error(checkNull(row.receiptgoodsname)+'收货数量(收货+损坏+欠货)与发货数量不符!');
						postState=0;
						showDialogmanager.close();
						curpagestate=2;
						return;
				}
				if(checkNull(row.receiptgoodscount)*1==0)
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
				
        		_html+="<tr class='p_tr'><td style='width:230px;padding:0 5px;'>"+ row.receiptgoodsname +"</td></tr>";
        		_html+="<tr class='p_tr'><td style='width:230px;padding-right:10px;'>"+(row.receiptgoodscount +"&nbsp;"+row.receiptgoodsunit)+"</td></tr>";
		}            		 
        $('#printContent table tr.p_tr').remove();
        $('#printContent table').append(_html);
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
			
		var responseMethod="handconfirmInfoMessage";		
		if(needPost==0)	
		{
			$.ligerDialog.confirm('收货明细中有收货数量为0,是否继续收货!', function (result)
			{
				if( result==true)
			    {
			        sendRequestForParams_p(requestUrl,responseMethod,params );    
			    }
			    else
			    {
			    	postState=0;
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
	        	//$.ligerDialog.success("操作成功!");
	        	pageReadState();
	        	curpagestate=3;
	        	confirmInfo.setDisabled();
	        	$.ligerDialog.confirm('操作成功！是否需要打印？', function (result){
	        		if(result){
			          	viewTicketReport();
	        		}
	        	});
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        	curpagestate=2;
	        }
	         postState=0;   
	       showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    function searchOrderBill()
    {
    	if(document.getElementById("strSearchSendBillno").value=="" && document.getElementById("strSearchOrderBillno").value=="")
		{
			$.ligerDialog.error('请填写查询信息!');
			return;
		}
    	var requestUrl ="ic012/searchOrderBill.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strSearchSendBillno="+document.getElementById("strSearchSendBillno").value;
        params=params+"&strSearchOrderBillno="+document.getElementById("strSearchOrderBillno").value;
		var responseMethod="searchOrderBillMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function searchOrderBillMessage(request)
   	{
   			
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMgoodsreceipt!=null && responsetext.lsMgoodsreceipt.length>0)
	   		{
	   			curpagestate=3;
	   			curMasterInfoDate={Rows: responsetext.lsMgoodsreceipt,Total: responsetext.lsMgoodsreceipt.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivmaster.loadData(true);   
            	commoninfodivmaster.select(0);
	   		}
	   		else
	   		{	
	   			$.ligerDialog.error('没有相关单据信息!');
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true); 
            	addMasterRecord(); 
	   		}
			
	}
	
	function validatereceiptcount(obj)
	{
		var sendgoodscount=checkNull(curDetialRecord.sendgoodscount)*1;
		var receiptprice=checkNull(curDetialRecord.receiptprice)*1;
		if(obj.value*1>sendgoodscount*1)
		{
			$.ligerDialog.error('收货量不能超过发货数量!');
			commoninfodivdetial.updateRow(curDetialRecord,{receiptgoodscount:0,receiptgoodsamt:0});
			return;
		}
		

		commoninfodivdetial.updateRow(curDetialRecord,{receiptgoodsamt: ForDight(receiptprice*1*obj.value*1,1)});
	
	}
	function viewTicketReport(){
	  try{
		var d = new Date();
		var vYear = d.getFullYear();
		var vMon = d.getMonth() + 1;
		var vDay = d.getDate();
		var h = d.getHours(); 
		var m = d.getMinutes(); 
		var se = d.getSeconds(); 
		var s =vYear+"-"+(vMon<10 ? "0" + vMon : vMon)+"-"+(vDay<10 ? "0"+ vDay : vDay)+
				" "+(h<10 ? "0"+ h : h)+":"+(m<10 ? "0" + m : m)+":"+(se<10 ? "0" +se : se);
		$("#printDate").html(s);
	  	Stand_CheckPrintControl();//检查是否有打印控件
		Stand_InitPrint("门店收货_小票打印作业");
		Stand_SetPrintStyle("FontSize",11);
		Stand_SetPrintStyle("Alignment",2);
		Stand_SetPrintStyle("HOrient",2);
		Stand_SetPrintStyle("Bold",1);
		Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,31,15,351,25,"阿玛尼护肤造型"+"("+checkNull(parent.document.getElementById("strCompName").value)+")");
		Stand_SetPrintStyle("FontSize",9);
		Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,55,15,351,25,"一切为你 想你所想");
		Stand_SetPrintStyle("FontSize",11);
		Stand_SetPrintStyle("Bold",0);
	    var printContent = document.getElementById("printContent").innerHTML;
	    Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_HTML,110,0,230,2000,printContent);
		Stand_Print();
	  }
	  catch(e){alert(e.message);}
	}
      	 