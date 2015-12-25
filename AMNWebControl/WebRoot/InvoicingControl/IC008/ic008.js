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
   	var ic008layout=null;
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
    var cancelInfo=null;
    var goodsdilog=null;
    var goodsdilogText="";
    var statechooseData = [{ choose: 0, text: '登记' }, { choose: 1, text: '已复合'}, { choose: 2, text: '已下单'}, { choose: 3, text: '已发货'}, { choose: 4, text: '已收货'}];
   	//初始化属性
    $(function ()
   	{
	   try
	   {  
	   	
	   	 	  //布局
            ic008layout= $("#ic008layout").ligerLayout({ leftWidth: 270,rightWidth: 245,  allowBottomResize: false, isLeftCollapse:true});
	   		var height = $(".l-layout-center").height();
	   		//到货日期
	   		$("#arrivaldate").ligerDateEditor({ labelWidth: 260,format: "yyyy-MM-dd", labelAlign: 'right',width:'260' });
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
                { 	name: 'bordercompid', hide:true	},
                { display: '采购单号', 	name: 'borderbillid', 	width:110,align: 'left'},
                { display: '单据状态', 	name: 'orderstate', 		width:60,align: 'left', 
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
	            { display: '申请日期', 	name: 'orderdate', 		width:80,align: 'left'},
	            { display: '收货单号', 	name: 'revicebillno', 	width:125,align: 'left'}   
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '400',
                height:'675',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
	                { text: '增加', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
					{ text: '删除', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
	                ]
                }	   
            });
           
     
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '产品编号', 	name: 'ordergoodsno',  				width:100,align: 'left' },
                { display: '产品名称', 	name: 'ordergoodsname',  			width:150,align: 'left' },
                { display: '单位', 		name: 'ordergoodsunit', 			width:30,align: 'left'},
                { display: '上级库存', 	name: 'headstockcount', 			width:60,align: 'right',
                	render: function (row) {  
     					if(checkNull(row.goodssource)=="1"){
     						return "10000";//供应商产品，库存显示为10000
     					}
     					return row.headstockcount;  
 					}
                },
	            { display: '申请数量', 	name: 'ordergoodscount', 			width:60,align: 'right'},
	            { display: '单价', 	name: 'ordergoodsprice', 			width:50,align: 'right' },
	            { display: '金额', 	name: 'ordergoodsamt', 				width:60,align: 'right' }  ,
	            { display: '已下单量', 	name: 'downordercount', 			width:60,align: 'right'},
	            { display: '未下单量', 	name: 'nodowncount', 				width:60,align: 'right'},
	            { display: '备注', 	    name: 'ordermark', 				    width:120,align: 'right',
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
	            { hide:true	,  name: 'norevicecount', 				    width:1 },
	            { hide:true	,  name: 'supplierno' , 				    width:1},
	            { hide:true , 	name: 'headwareno', 				    width:1},
	             { hide:true , 	name: 'goodssource', 				    width:1},
	             { hide:true , 	name: 'goodspricetype', 				    width:1}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '800',
                height:'470',
                clickToEdit: true,   enabledEdit: true,  checkbox:true,
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
			$("#strSearchDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
			
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
	             text: '复合采购', width: 100,
		         click: function ()
		         {
		             handconfirmInfo();
		         }
	         });
	         cancelInfo=$("#cancelInfo").ligerButton(
	         {
	             text: '取消复合', width: 100,
		         click: function ()
		         {
		             handcancelInfo();
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
     		var requestUrl ="ic008/loadGoodsOrderInfo.action"; 
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
			
			
	 
	   		//仓库选择框
	   		var returnValue='';
	   		if(responsetext.lsCompwarehouses!=null && responsetext.lsCompwarehouses.length>0)
	   		{
	   			for(var i=0;i<responsetext.lsCompwarehouses.length;i++)
				{
				    
					if(returnValue!='')
					{
						returnValue=returnValue+',';
					}
					else
					{
						returnValue=returnValue+'[';
					}
					returnValue=returnValue+'{"id": "'+responsetext.lsCompwarehouses[i].id.warehouseno+'","text": "'+responsetext.lsCompwarehouses[i].warehousename+'"}';
				}
	   		}
	   		if(returnValue!='')
	   		{
	   			
				returnValue=returnValue+']';	
				
		   		cinsertwaremanager= $("#storewareid").ligerComboBox({  data: JSON.parse(returnValue)
	                , valueFieldID: 'factcstorewareid',label:'请选仓库',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'
	            }); 
	        }
	        else
	        {
	        	cinsertwaremanager= $("#storewareid").ligerComboBox({  data: null , valueFieldID: 'factcstorewareid',label:'选择仓库',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220'
	            }); 
	        }
	        
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
		try
		{
	    	document.getElementById("ordercompid").value=checkNull(curMaster.bordercompid);
	    	document.getElementById("orderbillid").value=checkNull(curMaster.borderbillid);
	    	document.getElementById("orderbilltype").value=checkNull(curMaster.orderbilltype);
	    	document.getElementById("orderstate").value=checkNull(curMaster.orderstate);
	    	document.getElementById("orderstaffid").value=checkNull(curMaster.orderstaffid);
	    	document.getElementById("bordercompname").value=checkNull(curMaster.bordercompname);
	    	document.getElementById("orderstaffname").value=checkNull(curMaster.orderstaffname);
	    	cinsertwaremanager.selectValue(checkNull(curMaster.storewareid));
	    	document.getElementById("orderdate").value=checkNull(curMaster.orderdate);
	    	document.getElementById("ordertime").value=checkNull(curMaster.ordertime);
	    	document.getElementById("sendbillno").value=checkNull(curMaster.sendbillno);
	    	document.getElementById("downorderdate").value=checkNull(curMaster.downorderdate);
	    	document.getElementById("downordertime").value=checkNull(curMaster.downordertime);
	    	document.getElementById("revicebillno").value=checkNull(curMaster.revicebillno);
	    	document.getElementById("orderopationerid").value=checkNull(curMaster.orderopationerid);
	    	document.getElementById("orderopationdate").value=checkNull(curMaster.orderopationdate);
	    	document.getElementById("arrivaldate").value=checkNull(curMaster.arrivaldate);
			document.getElementById("winsergoodsname").value="";
			document.getElementById("winsergoodsunit").value="";
			document.getElementById("winsergoodsprice").value="";
			document.getElementById("winsergoodscount").value="";
			document.getElementById("winsergoodsno").value="";
			document.getElementById("wfrombarcode").value="";
			document.getElementById("wtobarcode").value="";
			document.getElementById("wstandunit").value="";
			document.getElementById("wstandprice").value="";
			document.getElementById("wgoodsuniquebar").value="";
			document.getElementById("wsupplierno").value="";
			document.getElementById("wheadwareno").value="";
			document.getElementById("wgoodssource").value="";
			document.getElementById("wminordercount").value=0;
			document.getElementById("wmaxordercount").value=0;
			document.getElementById("wheadstockcount").value=0;
			if(document.getElementById("orderstate").value==0)
			{
				pageWriteState();
				curpagestate=1;
			}
			else
			{
				pageReadState();
			}
			if(document.getElementById("orderstate").value==0)
			{
				confirmInfo.setEnabled();
				cancelInfo.setDisabled();
			}
			else if(document.getElementById("orderstate").value==1)
			{
				confirmInfo.setDisabled();
				cancelInfo.setEnabled();
			}
			else
			{
				confirmInfo.setDisabled();
				cancelInfo.setDisabled();
			}
			
		}catch(e){alert(e.message);}
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	document.getElementById("orderstaffid").readOnly="";
	   		cinsertwaremanager.setEnabled();
	   		document.getElementById("orderbilltype").disabled=false;
	    	document.getElementById("winsergoodsno").readOnly="";
	    	document.getElementById("winsergoodscount").readOnly="";
	    
	    	commoninfodivdetial.options.enabledEdit=true;
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("orderstaffid").readOnly="readOnly";
	   		cinsertwaremanager.setDisabled();
	    	document.getElementById("orderbilltype").disabled=true;
	    	document.getElementById("winsergoodsno").readOnly="readOnly";
	    	document.getElementById("winsergoodscount").readOnly="readOnly";
	    	commoninfodivdetial.options.enabledEdit=false;
    	}catch(e){alert(e.message);}
    }
    
    //根据主档选择加载明细
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        	
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
						"ic008/loadDorderInfo.action",
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
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
					            	addDetialRecord();
					            	document.getElementById("needPost").value="1"; 
						   		}
	   							loadCurMaster(responsetext.curMgoodsorderinfo);
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
    }
    //------------------------主档按钮操作-------------------Start
    function itemclick_cardInsertInfo(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认提交 '+strCurCompId+' 产品采购 '+item.text+' 操作', function (result)
		     {
		     	if( result==true)
            	{
		          	if(item.text=="增加")
		        	{
		        		 if(curpagestate!=3)
		        		 {
		        		 	$.ligerDialog.warn("请保存当前信息或刷新本界面后在进行新增操作!");
		        		 	return ;
		        		 }
		        		curpagestate=1;
		        	 	addRecord();
		        	 	pageWriteState();
		        	}
		        	else if(item.text=="保存")
		        	{
		        		 if(curpagestate==3)
		        		 {
		        		 	$.ligerDialog.warn("未保存数据,无需保存!");
		        		 	return ;
		        		 }
		        	 	editCurRecord();
		        	} 	
		        	else if(item.text=="删除")
		        	{
		        		 if(curpagestate!=3)
		        		 {
		        		 	$.ligerDialog.warn("请保存当前信息或刷新本界面后在进行刷新操作!");
		        		 	return ;
		        		 }
		        	 	deleteCurRecord();
		        	} 
		        	else if(item.text=="刷新")
		        	{
		        		loadCardInsertList();
		        	} 		
		        	
		        }
		      });  
    	}
    	catch(e){alert(e.message);}
    }
    
    function editCurRecord()
    {
    	
    	 showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
    	 curpagestate=3;
    	if(parent.hasFunctionRights( "IC008",  "UR_POST")!=true)
        {
       		$.ligerDialog.error("该用户没有保存权限,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
        if(document.getElementById("needPost").value!="1")
        {
       		$.ligerDialog.error("该单据只能复合，不能保存,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
	    if(document.getElementById("orderstaffid").value=="")
		{
			$.ligerDialog.error('请输入申请员工!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if(document.getElementById("orderbilltype").value=="")
		{
			$.ligerDialog.error('请选择采购方式!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if($("#factcstorewareid").val()=="")
		{
			$.ligerDialog.error('请选择入库仓库!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		document.getElementById("storewareid_h").value=$("#factcstorewareid").val();
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic008/post.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.ordergoodsno)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				/*if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}	   */         		   
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
			
		var responseMethod="postMessage";
	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function postMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("保存成功!");
	        	 curpagestate=3;
	        	 pageReadState();
	        	 confirmInfo.setDisabled();
				 cancelInfo.setEnabled();
				
				
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        	curpagestate=2;
	        }
	        showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
    }
     
			 
     //新增行
     function addRecord()
     {
     		if(parent.hasFunctionRights( "IC008",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        }
	        else
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
				var params = "strCurCompId="+strCurCompId;		
    	     	var requestUrl ="ic008/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
				
	        }
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    		loadCurMaster(responsetext.curMgoodsorderinfo);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
            	addDetialRecord();
            	document.getElementById("needPost").value="1"; 
		   	}catch(e){alert(e.message);}
     }
     
    //删除行
    function deleteCurRecord()
    {
       	if(parent.hasFunctionRights( "IC008",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var requestUrl ="ic008/delete.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strCurBillId="+strCurBillId;
		var responseMethod="deleteMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	   
    }
   	function deleteMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  commoninfodivmaster.deleteSelectedRow();
	       		  $.ligerDialog.success("删除成功!");
	       	}
	       	else
	       	{
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   //------------------------主档按钮操作-------------------End
    
  //-------------------------查询信息 ---------------------Start
     function f_searchCardInsertInfo()
     {
    
     	  
     }
   //--------------------------验证验货人员-----------------
   function validateInserper(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("orderstaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic008/validateInserper.action";
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
			else if(key==114)//F3
			{
				//addRecord();
			}
			else if(key==115)//F4
			{
				if(curpagestate==3)
		        {
		 			$.ligerDialog.warn("非新增操作,不可编辑入库明细!");
		    		return ;
		    	}
				 deletedetialRecord();
			}
			else if( key == 83 &&  event.altKey)
			{
				if(curpagestate==3)
		        {
		        	$.ligerDialog.warn("未保存数据,无需保存!");
		        	return ;
		        }
				editCurRecord();
			}
	
		}
		
		function  hotKeyEnter()
		{
		
			try
			{
				var fieldName = document.activeElement.name;
				var fieldId = document.activeElement.id ;
				if(fieldId=="winsergoodsno")
				{
					document.activeElement.onchange();
				}
				else
				{
					window.event.keyCode=9; //tab
					window.event.returnValue=true;
				}
				
			}
			catch(e){alert(e.message);}
				
		}   
		
		
		 function selectCall()
		 {
		 	validateinserno(document.getElementById("winsergoodsno"));
		 } 
		function validateinserno(obj)
		{
			 if(document.getElementById("orderstaffid").value=="")
			{
				$.ligerDialog.error('请输入申请员工!');
				curpagestate=2;
				return;
			}
			if(document.getElementById("orderbilltype").value=="")
			{
				$.ligerDialog.error('请选择采购方式!');
				curpagestate=2;
				return;
			}
			if($("#factcstorewareid").val()=="")
			{
				$.ligerDialog.error('请选择入库仓库!');
				curpagestate=2;
				return;
			}
			if(obj.value=="")
			{
				document.getElementById("winsergoodsname").value="";
				document.getElementById("winsergoodsunit").value="";
				document.getElementById("winsergoodsprice").value="";
				document.getElementById("wgoodsformat").value="";
				document.getElementById("winsergoodscount").value="0";
				document.getElementById("wfrombarcode").value="";
				document.getElementById("wtobarcode").value="";
				document.getElementById("wgoodsuniquebar").value="";
				document.getElementById("wstandunit").value="";
				document.getElementById("wstandprice").value="";
				document.getElementById("wsupplierno").value="";
				document.getElementById("wheadwareno").value="";
				document.getElementById("wminordercount").value=0;
				document.getElementById("wmaxordercount").value=0;
				document.getElementById("wheadstockcount").value=0;
				document.getElementById("wgoodssource").value="";
				obj.focus();
				obj.select();
			}
			else
			{
				var checkflag=0;
				var goodsbarno="";
				var strgoodsbarno=null;
				var goodsBarFlag=0;
				var goodscrentwareno="";
				if(parent.lsGoodsinfo!=null && parent.lsGoodsinfo.length>0)
				{
					for(var i=0;i<parent.lsGoodsinfo.length;i++)
					{
						if( parent.lsGoodsinfo[i].id.goodsno==obj.value
						|| checkNull(parent.lsGoodsinfo[i].goodsabridge).toLowerCase()==obj.value.toLowerCase())
						{
							if(checkNull(parent.lsGoodsinfo[i].useflag)==1)
							{
	        	 				$.ligerDialog.error("产品!"+parent.lsGoodsinfo[i].goodsname+"已停用");
	        	 				obj.focus();
								obj.select();
	        	  				obj.value="";
	        	 				return ;
	        				}
	        				if(checkNull(parent.lsGoodsinfo[i].appflag)==0)
							{
	        	 				$.ligerDialog.error("产品!"+parent.lsGoodsinfo[i].goodsname+"已停止采购");
	        	 				obj.focus();
								obj.select();
	        	  				obj.value="";
	        	 				return ;
	        				}
	        				if(checkNull(parent.lsGoodsinfo[i].enablecompany)==1)
							{
	        					var companys = checkNull(parent.lsGoodsinfo[i].goodscompany);
	        					if(companys!=""){
	        						var ispass=true;c_array = companys.split(";");
	        						var ordercompid = $("#ordercompid").val();
	        						$(c_array).each(function(i, val){//判断匹配门店
	        							if(ordercompid==val){
	        								ispass=false;
	        								return false;
	        							}
	        						});
	        						if(ispass){
	        							$.ligerDialog.error("产品!"+parent.lsGoodsinfo[i].goodsname+"不允许采购");
	        							obj.focus();
	        							obj.select();
	        							obj.value="";
	        							return ;
	        						}
	        					}
	        				}
							document.getElementById("winsergoodsname").value=checkNull(parent.lsGoodsinfo[i].goodsname);
							document.getElementById("winsergoodsunit").value=checkNull(parent.lsGoodsinfo[i].purchaseunit);
							document.getElementById("winsergoodsprice").value=ForDight(parent.lsGoodsinfo[i].costamtbysale,2);
							document.getElementById("wgoodsformat").value=checkNull(parent.lsGoodsinfo[i].goodsformat);
							document.getElementById("wgoodsuniquebar").value=checkNull(parent.lsGoodsinfo[i].goodsuniquebar);
							document.getElementById("wstandunit").value=checkNull(parent.lsGoodsinfo[i].saleunit);
							document.getElementById("wstandprice").value=ForDight(parent.lsGoodsinfo[i].standprice,2);
							document.getElementById("wsupplierno").value=checkNull(parent.lsGoodsinfo[i].goodssupplier);
							document.getElementById("wheadwareno").value=checkNull(parent.lsGoodsinfo[i].goodswarehouse);
							document.getElementById("wgoodssource").value=checkNull(parent.lsGoodsinfo[i].goodsappsource);
							document.getElementById("wminordercount").value=checkNull(parent.lsGoodsinfo[i].minordercount);
							document.getElementById("wmaxordercount").value=checkNull(parent.lsGoodsinfo[i].goodsbarno);
							document.getElementById("goodspricetype").value=checkNull(parent.lsGoodsinfo[i].goodspricetype);
							document.getElementById("winsergoodscount").value=0;
							document.getElementById("winsergoodscount").focus();
							document.getElementById("winsergoodscount").select();
							goodscrentwareno=checkNull(parent.lsGoodsinfo[i].goodswarehouse);
							checkflag=1;
							break;
						}
						//goodsbarno=checkNull(parent.lsGoodsinfo[i].goodsbarno);
						if(goodsbarno!="")
						{
							
							strgoodsbarno= new Array(); 
						
							strgoodsbarno=goodsbarno.split(";");  
							goodsBarFlag=0;
								
							for (j=0;j<strgoodsbarno.length ;j++ )   
							{
								if(strgoodsbarno[j]==obj.value)
								{
									goodsBarFlag=1;
									document.getElementById("winsergoodsno").value=checkNull(parent.lsGoodsinfo[i].id.goodsno);
									document.getElementById("winsergoodsname").value=checkNull(parent.lsGoodsinfo[i].goodsname);
									document.getElementById("winsergoodsunit").value=checkNull(parent.lsGoodsinfo[i].saleunit);
									document.getElementById("wgoodsformat").value=checkNull(parent.lsGoodsinfo[i].goodsformat);
									document.getElementById("winsergoodsprice").value=ForDight(checkNull(parent.lsGoodsinfo[i].costamtbysale),2);
									document.getElementById("wstandunit").value=checkNull(parent.lsGoodsinfo[i].saleunit);
									document.getElementById("wstandprice").value=ForDight(parent.lsGoodsinfo[i].standprice,2);
									document.getElementById("wgoodsuniquebar").value=checkNull(parent.lsGoodsinfo[i].goodsuniquebar);
									document.getElementById("wsupplierno").value=checkNull(parent.lsGoodsinfo[i].goodssupplier);
									document.getElementById("wheadwareno").value=checkNull(parent.lsGoodsinfo[i].goodswarehouse);
									document.getElementById("wgoodssource").value=checkNull(parent.lsGoodsinfo[i].goodsappsource);
									document.getElementById("wminordercount").value=checkNull(parent.lsGoodsinfo[i].minordercount);
									document.getElementById("wmaxordercount").value=checkNull(parent.lsGoodsinfo[i].goodsbarno);
									document.getElementById("winsergoodscount").value=0;
									goodscrentwareno=checkNull(parent.lsGoodsinfo[i].goodswarehouse);
									document.getElementById("winsergoodscount").focus();
									document.getElementById("winsergoodscount").select();
									break;
								}
							}
							if(goodsBarFlag==1)
							{
								checkflag=1;
								break;
							}
						}		 
					}
				}
				if(checkflag==0)
				{
					$.ligerDialog.warn("产品编号不存在或已停用");
	        	 	document.getElementById("winsergoodsno").focus();
					document.getElementById("winsergoodsno").select();
	        	 	obj.value="";
	        	  	return ;
				}
				if(checkNull(parent.lsGoodsinfo[i].goodswarehouse)=="06" && document.getElementById("orderbilltype").value=="1")
				{
					$.ligerDialog.warn(checkNull(parent.lsGoodsinfo[i].goodsname)+"属于现金产品,只能使用员工订购");
	        	 	document.getElementById("winsergoodsno").focus();
					document.getElementById("winsergoodsno").select();
	        	 	obj.value="";
	        	  	return ;
				}
				if($("#wgoodssource").val()==1){//供应商产品，上级库存显示为10000
					$("#wheadstockcount").val("10000");
					return;
				}
				var requestUrl ="ic008/loadCurStock.action";
        		var params="strCurCompId=001";
        		params=params+"&strCurGoodsId="+obj.value;
        		params=params+"&strWareId="+checkNull(parent.lsGoodsinfo[i].goodswarehouse);
        		params=params+"&strCurDate="+document.getElementById("orderdate").value;
				var responseMethod="loadCurStockMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 	
			}
		}
		
		function loadCurStockMessage(request)
	    {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       	document.getElementById("wheadstockcount").value=responsetext.curgoodsstock;
	        }
			catch(e)
			{
				alert(e.message);
			}
	   	}
		
		function loadGoodsGrid(obj)
		{
			
			if(document.getElementById("winsergoodsno").value=="")
			{
				$.ligerDialog.warn("请确认入库产品编号!");
	        	document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
				obj.value=0;
	        	 return;
			}
			if(validatePrice(obj)==false)
			{
				$.ligerDialog.warn("请确认入数量格式!");
	        	document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
				obj.value=0;
	        	 return;
			}
			if(obj.value.indexOf(".")>-0)
			{
				$.ligerDialog.error("只能申请整数单位数量!");
	        	document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
				obj.value=0;
	        	return;
			}
			for (var rowid in commoninfodivdetial.records)
			{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.ordergoodsno)==document.getElementById("winsergoodsno").value)
				{
					$.ligerDialog.error("产品编号已在申请列表中!");
					return;
				}
			}          
			if( ! isNumber(obj.value) )
		    {
				$.ligerDialog.error( " 输入有误!" );
				obj.value=0;
				obj.focus();
				obj.select();
				return;
			}
			try
			{
				
				if(obj.value*1==0)
				{
					$.ligerDialog.error("请确认入库数量!");
					return;
				}
				
				if($("#wgoodssource").val()!=1 && obj.value*1>document.getElementById("wheadstockcount").value)
				{
					$.ligerDialog.error("申请量不能超过总部库存量!");
					return;
				}
				if(obj.value<document.getElementById("wminordercount").value*1 
				||  obj.value*1%document.getElementById("wminordercount").value*1>0)
				{
					$.ligerDialog.error("该产品不能小于系统设定的最低订货量或订货数量的倍数!");
					return;
				}
				var wmaxordercount = $("#wmaxordercount").val();
				if($("#wgoodssource").val()==1 && checkNull(wmaxordercount)!="" && obj.value*1>wmaxordercount*1){
					$.ligerDialog.error("该产品不能大于系统设定的最大订货量!");
					return;
				}
				loadGoodsBarCordMessage();
				
			}catch(e){alert(e.message);}
		}
		
		function loadGoodsBarCordMessage()
	    {
	    		
	       	try
			{
		        var gridlen=commoninfodivdetial.rows.length*1;
				if(gridlen==0)
				{
					addDetialRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial.getRow(0).ordergoodsname)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}   
				curDetialRecord=commoninfodivdetial.getRow(gridlen-1);
				commoninfodivdetial.updateRow(curDetialRecord,{ordergoodsno: document.getElementById("winsergoodsno").value
															  ,ordergoodsname : document.getElementById("winsergoodsname").value
															  ,ordergoodsunit  : document.getElementById("winsergoodsunit").value
															  ,headstockcount: ForDight(document.getElementById("wheadstockcount").value*1,1)
															  ,ordergoodscount : ForDight(document.getElementById("winsergoodscount").value*1,2)
															  ,ordergoodsprice : ForDight(document.getElementById("winsergoodsprice").value*1,2)
															  ,ordergoodsamt   : ForDight(document.getElementById("winsergoodsprice").value*1*document.getElementById("winsergoodscount").value*1,1)
															  ,producenorm : document.getElementById("wgoodsformat").value 
															  ,supplierno : document.getElementById("wsupplierno").value
															  ,headwareno : document.getElementById("wheadwareno").value
															  ,goodssource : document.getElementById("wgoodssource").value
															  ,goodspricetype : document.getElementById("goodspricetype").value});  
				      
				document.getElementById("winsergoodsno").value="";
				document.getElementById("winsergoodsname").value="";
				document.getElementById("winsergoodsunit").value="";
				document.getElementById("winsergoodsprice").value="";
				document.getElementById("wgoodsformat").value="";
				document.getElementById("wfrombarcode").value="";
				document.getElementById("wtobarcode").value="";
				document.getElementById("wgoodsuniquebar").value="";
				document.getElementById("wstandunit").value="";
				document.getElementById("wstandprice").value="";	
				document.getElementById("winsergoodscount").value=0;
				document.getElementById("wsupplierno").value="";
				document.getElementById("wheadwareno").value="";
				document.getElementById("wgoodssource").value="";
				document.getElementById("wminordercount").value=0;
				document.getElementById("wmaxordercount").value=0;
				document.getElementById("wheadstockcount").value=0;
				document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
	        }
			catch(e)
			{
				alert(e.message);
			}
	    }
    
    //复合入库单
    function handconfirmInfo()
    {
        showDialogmanager = $.ligerDialog.waitting('正在复合中,请稍候...');	
        if(parent.hasFunctionRights( "IC008",  "UR_SPECIAL_CHECK")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
	     if(document.getElementById("orderstaffid").value=="")
		{
			$.ligerDialog.error('请输入申请员工!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if(document.getElementById("orderbilltype").value=="")
		{
			$.ligerDialog.error('请选择采购方式!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if($("#factcstorewareid").val()=="")
		{
			$.ligerDialog.error('请选择入库仓库!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		document.getElementById("storewareid_h").value=$("#factcstorewareid").val();
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic008/handconfirmInfo.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //菲灵产品的总金额
        /*var flamount = 0;
        var hasflProduct = false;*/
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.ordergoodsno)=="")
					continue;
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
				/*if(3==row.goodspricetype){
					hasflProduct = true;
					flamount = flamount+parseFloat(row.ordergoodsamt);
				}*/
		} 
        /*if(hasflProduct){
        	if(flamount<600){
        		$.ligerDialog.warn("菲灵产品总金额必须大于600!");
        		 showDialogmanager.close();
        		return;
        	}
        }*/
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
			
		var responseMethod="handconfirmInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
     //取肖复合入库单
    function handcancelInfo()
    {
    	if(parent.hasFunctionRights( "IC008",  "UR_SPECIAL_CHECK")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
    	$.ligerDialog.confirm('是否作废申请单操作', function (result)
		{
		    if( result==true)
            {
		    	var requestUrl ="ic008/handcancelInfo.action";
		        var params="strCurCompId="+document.getElementById("ordercompid").value;
		        params=params+"&strCurBillId="+document.getElementById("orderbillid").value;
				var responseMethod="handcancelInfoMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
		});
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
	        	 curpagestate=3;
	        	confirmInfo.setDisabled();
	        	cancelInfo.setDisabled();//cancelInfo.setEnabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        	curpagestate=2;
	        }
	       showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
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
	        	pageWriteState();
				curpagestate=1;
				confirmInfo.setEnabled();
				cancelInfo.setDisabled(); 
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
    	var requestUrl ="ic008/searchOrderBill.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strCurBillId="+document.getElementById("strSearchBillno").value;
        params=params+"&strCurDate="+document.getElementById("strSearchDate").value;
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
    
   	
	function loadGoodsInfo(obj)
	{
		goodsdilogText=obj;
		goodsdilog=$.ligerDialog.open({ height: 600, url: contextURL+'/common/commonDilog/GoodsInfo.html', width: 750, isResize:true, title: '产品列表' });
   }
   
   function loadGoodsInfoByWare(obj)
   {
   		if(obj.value!="")
   		{
   			document.getElementById("winsergoodsno").onfocus=function(){ itemsearchbegin_ware(document.getElementById("winsergoodsno"),7,obj.value);};
   		}
   }
    