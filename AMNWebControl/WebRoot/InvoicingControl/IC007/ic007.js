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
   	var ic007layout=null;
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
    var returnchooseData = [{ choose: 1, text: '总仓' }, { choose: 2, text: '供应商'}];
    var statechooseData = [{ choose: 0, text: '登记' }, { choose: 1, text: '申请待批'}, { choose: 2, text: '总部审核'}, { choose: 3, text: '总部驳回'}];
   	//初始化属性
    $(function ()
   	{
	   try
	   {  
	   	
	   	 	  //布局
            ic007layout= $("#ic007layout").ligerLayout({ leftWidth: 270,rightWidth: 245,  allowBottomResize: false, isLeftCollapse:true});
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
                { 	name: 'breturncompid', hide:true	},
                { display: '退货单号', 	name: 'breturnbillid', 	width:120,align: 'left'},
                { display: '单据状态', 	name: 'returnstate', 		width:80,align: 'left', 
	                editor: { type: 'select', data: statechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.returnstate == 0) return '登记';
	                    else if (item.returnstate == 1) return '申请待批';
	                    else if (item.returnstate == 2) return '总部复合';
	                    else if (item.returnstate == 3) return '总部驳回';
	                }
	            },
	            { display: '退货日期', 	name: 'returndate', 		width:90,align: 'left'}  
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '300',
                height:'675',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
	                { text: '增加', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
					]
                }	   
            });
           
     
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '产品编号', 		name: 'returngoodsno',  			width:110,align: 'left' },
                { display: '产品名称', 		name: 'returngoodsname',  			width:150,align: 'left' },
                { display: '单位', 			name: 'returngoodsunit', 			width:50,align: 'left'},
	            { display: '退货数量', 		name: 'returncount', 				width:60,align: 'right'},
	            { display: '退货方向', 		name: 'returntype', 				width:60,align: 'left' , 
	                editor: { type: 'select', data: returnchooseData, valueField: 'choose',onChanged : validateReturnType },
	                render: function (item)
	                {
	                    if (item.returntype == 1) return '总仓';
	                    else if (item.returntype == 2) return '供应商';
	                }
	            },
	            { display: '仓库/供应商', 	name: 'revicestoreno', 				width:80,align: 'left', editor: { type: 'text' } }  ,
	            { display: '实际退货量', 		name: 'factreturncount', 			width:80,align: 'right', editor: { type: 'int' ,onChanged : validatefactreturncount}},
	            { display: '实际退货单价', 	name: 'factreturnprice', 			width:80,align: 'right',editor: { type: 'float' ,onChanged : validatefactreturnprice }},
	            { display: '实际退货金额', 	name: 'factreturnamt', 				width:80,align: 'right'}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '800',
                height:'500',
                clickToEdit: true,   enabledEdit: true,  checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curDetialRecord=data;
                    if(curpagestate==1)
                    	showTextByinfoType(curDetialRecord,1);
                    else
	                    showTextByinfoType(curDetialRecord,2);
                },
                toolbar: { items: [
                 { text: '打印', click: printReport, img: contextURL+'/common/ligerui/ligerUI/skins/icons/print.gif' }
                ]
                }
                
            });
            
            commoninfodivdetial_barcode=$("#commoninfodivdetial_barcode").ligerGrid({
                columns: [
                { 	name: 'tobarcode', hide:true	},
                { 	name: 'returncount', hide:true	},
                { display: '产品条码', 		name: 'frombarcode',  			width:110,align: 'left' },
                { display: '产品编号', 		name: 'returngoodsno',  		width:80,align: 'left' }
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '220',
                height:'700',
                clickToEdit: true,   enabledEdit: true,  checkbox: true,
                rownumbers: false,usePager: false
                
            });
              $("#toptoolbardetial").ligerToolBar({ items: [
      		     { text: '条码:&nbsp;<input type="text"  name="winsergoodsbarno" id="winsergoodsbarno" maxlength="20" style="width:120;"  onchange="validateinserbarno(this)"/>' }
	         ]
            });
          
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
	             text: '总部复合', width: 140,
		         click: function ()
		         {
		             handconfirmInfo();
		         }
	         });
	         cancelInfo=$("#cancelInfo").ligerButton(
	         {
	             text: '总部驳回', width: 140,
		         click: function ()
		         {
		             handcancelInfo();
		         }
	         });
          	$("#pageloading").hide(); 
          	addMasterRecord();
          	addDetialRecord();
          	addbardetialRecord()
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
     		var requestUrl ="ic007/loadGoodsOrderInfo.action"; 
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
    
	   		if(responsetext.lsMreturngoodsinfo!=null && responsetext.lsMreturngoodsinfo.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMreturngoodsinfo,Total: responsetext.lsMreturngoodsinfo.length};
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
	    	document.getElementById("returncompid").value=checkNull(curMaster.breturncompid);
	    	document.getElementById("breturncompname").value=checkNull(curMaster.breturncompname);
	    	document.getElementById("returnbillid").value=checkNull(curMaster.breturnbillid);
	    	document.getElementById("returndate").value=checkNull(curMaster.returndate);
	    	document.getElementById("returntime").value=checkNull(curMaster.returntime);
	    	cinsertwaremanager.selectValue(checkNull(curMaster.returnwareid));
	    	document.getElementById("returnstaffid").value=checkNull(curMaster.returnstaffid);
	    	document.getElementById("returnstaffname").value=checkNull(curMaster.returnstaffname);
	    	document.getElementById("returnstate").value=checkNull(curMaster.returnstate);
	    	document.getElementById("returnopationerid").value=checkNull(curMaster.returnopationerid);
	    	document.getElementById("returnopationdate").value=checkNull(curMaster.returnopationdate);
	    	document.getElementById("confirmopationerid").value=checkNull(curMaster.confirmopationerid);
	    	document.getElementById("confirmopationdate").value=checkNull(curMaster.confirmopationdate);
			document.getElementById("winsergoodsname").value="";
			document.getElementById("winsergoodsunit").value="";
			document.getElementById("winsergoodsprice").value="";
			document.getElementById("winsergoodsno").value="";
			document.getElementById("wrevicestoreno").value=""; 
			document.getElementById("winsergoodsbarno").value="";
			if(document.getElementById("returnstate").value==0)
			{
				pageWriteState();
				curpagestate=1;
			}
			else
			{
				pageReadState();
			}
			if(document.getElementById("returnstate").value==1)
			{
				confirmInfo.setEnabled();
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
	    	document.getElementById("returnstaffid").readOnly="";
	   		cinsertwaremanager.setEnabled();
	    	commoninfodivdetial.options.enabledEdit=false;
	    	document.getElementById("winsergoodsno").readOnly="";
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("returnstaffid").readOnly="readOnly";
	   		cinsertwaremanager.setDisabled();
	    	
	    	document.getElementById("winsergoodsno").readOnly="readOnly";
	    	commoninfodivdetial.options.enabledEdit=true;
    	}catch(e){alert(e.message);}
    }
    
    function showTextByinfoType(rowdata,readType)
	{
		try
		{
			var column  =null ;
			
				if(readType==2)//
				{
					column=commoninfodivdetial.columns[4];
					commoninfodivdetial.setCellEditing(rowdata, column, false);
					column=commoninfodivdetial.columns[6];
					commoninfodivdetial.setCellEditing(rowdata, column, false);
					column=commoninfodivdetial.columns[7];
					commoninfodivdetial.setCellEditing(rowdata, column, false);
					column=commoninfodivdetial.columns[8];
					commoninfodivdetial.setCellEditing(rowdata, column, false);
				}
				else if(readType==1)
				{
						column=commoninfodivdetial.columns[4];
						commoninfodivdetial.setCellEditing(rowdata, column, true);
						column=commoninfodivdetial.columns[6];
						commoninfodivdetial.setCellEditing(rowdata, column, true);
						column=commoninfodivdetial.columns[7];
						commoninfodivdetial.setCellEditing(rowdata, column, true);
						column=commoninfodivdetial.columns[8];
						commoninfodivdetial.setCellEditing(rowdata, column, false);
					
				}
			
	}
	catch(e){alert(e.message);}
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
        		
       			 var params = "strCurCompId="+data.breturncompid;
       			 params =params+ "&strCurBillId="+data.breturnbillid;
				 var myAjax = new parent.Ajax.Request(
						"ic007/loadDorderInfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsDreturngoodsinfo!=null && responsetext.lsDreturngoodsinfo.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDreturngoodsinfo,Total: responsetext.lsDreturngoodsinfo.length});
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
						   		if(responsetext.lsDgoodsreturnbarinfo!=null && responsetext.lsDgoodsreturnbarinfo.length>0)
						   		{
						   			commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsreturnbarinfo,Total: responsetext.lsDgoodsreturnbarinfo.length});
					            	commoninfodivdetial_barcode.loadData(true);    
					            }
						   		else
						   		{
						   			commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial_barcode.loadData(true);  
					            	addbardetialRecord();
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
									
							   		cinsertwaremanager= $("#returnwareid").ligerComboBox({  data: JSON.parse(returnValue)
						                , valueFieldID: 'factcreturnwareid',label:'请选仓库',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'
						            }); 
						        }
						        else
						        {
						        	cinsertwaremanager= $("#returnwareid").ligerComboBox({  data: null , valueFieldID: 'factcreturnwareid',label:'选择仓库',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220'
						            }); 
						        }
	   							loadCurMaster(responsetext.curMreturngoodsinfo);
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
		        	
		        	
		        }
		      });  
    	}
    	catch(e){alert(e.message);}
    }
    
    function editCurRecord()
    {
    	
    	 showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
    	 curpagestate=3;
    	if(parent.hasFunctionRights( "IC007",  "UR_POST")!=true)
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
	    if(document.getElementById("returnstaffid").value=="")
		{
			$.ligerDialog.error('请输入退货员工!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if($("#factcreturnwareid").val()=="")
		{
			$.ligerDialog.error('请选择退货仓库!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if(commoninfodivdetial.rows.length*1>0 )
		{
			   commoninfodivdetial.endEdit();
		}
		
		document.getElementById("returnwareid_h").value=$("#factcreturnwareid").val();
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic007/post.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.returngoodsno)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				/*if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}	    */      
					   
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		strJsonParam_detial="";
        for (var rowid in commoninfodivdetial_barcode.records)
		{
				var row =commoninfodivdetial_barcode.records[rowid];
				if(checkNull(row.returngoodsno)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				/*if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}*/	            		   
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strBarJsonParam=["+strJsonParam_detial+"]";
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
     		if(parent.hasFunctionRights( "IC007",  "UR_MODIFY")!=true)
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
    	     	var requestUrl ="ic007/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
				
	        }
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
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
					cinsertwaremanager= $("#returnwareid").ligerComboBox({  data: JSON.parse(returnValue)
						                , valueFieldID: 'factcreturnwareid',label:'请选仓库',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'
					}); 
				}
				else
				{
					cinsertwaremanager= $("#returnwareid").ligerComboBox({  data: null , valueFieldID: 'factcreturnwareid',label:'选择仓库',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220'
					}); 
				}
	    		loadCurMaster(responsetext.curMreturngoodsinfo);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
            	addDetialRecord();
            	commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial_barcode.loadData(true);  
            	addbardetialRecord();
            	document.getElementById("needPost").value="1"; 
		   	}catch(e){alert(e.message);}
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
   			document.getElementById("returnstaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic007/validateInserper.action";
        var params="strCurCompId="+document.getElementById("returncompid").value;
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
	       		document.getElementById("returnstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("returnstaffid").value="";
	       		document.getElementById("returnstaffname").value="";
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
    function addbardetialRecord()
    {
    		var row = commoninfodivdetial_barcode.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_barcode.addRow({ 
				  returngoodsno: "",
				  frombarcode: ""         
			}, row, false);
    }
    function addDetialRecord()
    {
    		var row = commoninfodivdetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({ 
				      returngoodsno: "",
				      returngoodsname: "",
				      returngoodsunit: "",
				      returncount: "" ,
				      returntype: "",
				      revicestoreno: "",
				      factreturncount: "",
				      factreturnprice: "",
				      factreturnamt: ""
			}, row, false);
    }

	            
    function deletedetialRecord()
    {
    	commoninfodivdetial_barcode.deleteSelectedRow();
    	
    }
    
    function addDetialBarnoRecord()
    {
    		var row = commoninfodivdetial_barcode.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_barcode.addRow({ 
				returngoodsno:'',
				frombarcode:''   
			}, row, false);
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
					validateinserno(document.getElementById("winsergoodsno"));
				}
				else
				{
					window.event.keyCode=9; //tab
					window.event.returnValue=true;
				}
				
			}
			catch(e){alert(e.message);}
				
		}   
		
		function validateinserno(obj)
		{
			if(document.getElementById("returnstaffid").value=="")
	        {
	        
	        	 obj.value="";
	        	 document.getElementById("returnstaffid").focus();
				 document.getElementById("returnstaffid").select();
				 $.ligerDialog.error("请确认退货员工!");
	        	 return;
	        }
	        if($("#factcreturnwareid").val()=="")
	        {
	        	
	        	 obj.value="";
	        	 document.getElementById("returnstaffname").focus();
				 document.getElementById("returnstaffname").select();
				  $.ligerDialog.error("退货仓库!");
	        	 return;
	        }
	        
			if(obj.value=="")
			{
				document.getElementById("winsergoodsname").value="";
				document.getElementById("winsergoodsunit").value="";
				document.getElementById("winsergoodsprice").value="";
				document.getElementById("winsergoodscount").value="0";
				obj.focus();
				obj.select();
			}
			else
			{
				var checkflag=0;
				var goodsbarno="";
				var strgoodsbarno=null;
				var goodsBarFlag=0;
				if(parent.lsGoodsinfo!=null && parent.lsGoodsinfo.length>0)
				{
					for(var i=0;i<parent.lsGoodsinfo.length;i++)
					{
						if( parent.lsGoodsinfo[i].id.goodsno==obj.value
						|| checkNull(parent.lsGoodsinfo[i].goodsabridge).toLowerCase()==obj.value.toLowerCase())
						{
							if(checkNull(parent.lsGoodsinfo[i].useflag)==1)
							{
	        	 				$.ligerDialog.warn("产品!"+parent.lsGoodsinfo[i].goodsname+"已停用");
	        	 				obj.focus();
								obj.select();
	        	  				obj.value="";
	        	 				return ;
	        				}
							document.getElementById("winsergoodsname").value=checkNull(parent.lsGoodsinfo[i].goodsname);
							document.getElementById("winsergoodsunit").value=checkNull(parent.lsGoodsinfo[i].purchaseunit);
							document.getElementById("winsergoodsprice").value=ForDight(parent.lsGoodsinfo[i].storesalseprice,2);
							document.getElementById("winsergoodscount").value=0;
							document.getElementById("winsergoodscount").focus();
							document.getElementById("winsergoodscount").select();
							checkflag=1;
							break;
						}
						goodsbarno=checkNull(parent.lsGoodsinfo[i].goodsbarno);
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
									document.getElementById("winsergoodsunit").value=checkNull(parent.lsGoodsinfo[i].purchaseunit);
									document.getElementById("winsergoodsprice").value=ForDight(checkNull(parent.lsGoodsinfo[i].storesalseprice),2);
									document.getElementById("winsergoodscount").value=0;
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
	        	  
				}
			}
		}
		
		
		function loadGoodsGrid()
	    {
	    	try
			{
		     		var gridlen=commoninfodivdetial.rows.length*1;
					if(checkNull(commoninfodivdetial.getRow(0).returngoodsno)!="")
					{
						addDetialRecord();
						gridlen=gridlen+1;
					}
					curDetialRecord=commoninfodivdetial.getRow(gridlen-1);
					commoninfodivdetial.updateRow(curDetialRecord,{returngoodsno: document.getElementById("winsergoodsno").value
															  ,returngoodsname : document.getElementById("winsergoodsname").value
															  ,returngoodsunit  : document.getElementById("winsergoodsunit").value
															  ,returncount :document.getElementById("winsergoodscount").value
															  ,returntype : 1
															  ,revicestoreno   : document.getElementById("wrevicestoreno").value
															   }); 
				
					document.getElementById("winsergoodsno").value="";
					document.getElementById("winsergoodsname").value="";
					document.getElementById("winsergoodsunit").value="";
					document.getElementById("winsergoodsprice").value="";
					document.getElementById("wrevicestoreno").value="";
					document.getElementById("winsergoodsbarno").value="";
					document.getElementById("winsergoodsno").focus();
					document.getElementById("winsergoodsno").select();
	        }
			catch(e)
			{
				alert(e.message);
			}
	    }
	    
		
		function validateinserbarno(obj)
		{
			
	    	var requestUrl ="ic007/validateFromBarNo.action";
        	var params="strCurCompId="+document.getElementById("returncompid").value;
        	params=params+"&strStartBarNo="+obj.value;
			var responseMethod="validateFromBarNoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
		}
		//验证起始条码
    	function validateFromBarNoMessage(request)
	    {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)=="")
		       	{	       		 
		       		for (var rowid in commoninfodivdetial_barcode.records)
					{
						var row =commoninfodivdetial_barcode.records[rowid];
						if(row.frombarcode==document.getElementById("winsergoodsbarno").value )	
						{
							$.ligerDialog.error("该条码已在退卡条码列表之中!");
							document.getElementById("winsergoodsbarno").value="";
		       				document.getElementById("winsergoodsbarno").focus();
		       				document.getElementById("winsergoodsbarno").select();
							return ;
						}	
					}
					if(loadGoodsBarCordMessage(responsetext.curGoodsinfo.id.goodsno)==true)
					{
					
						var gridlen=commoninfodivdetial_barcode.rows.length*1;
						if(gridlen==0)
						{
							addDetialBarnoRecord();
							gridlen=gridlen+1;
						} 
						if(checkNull(commoninfodivdetial_barcode.getRow(0).frombarcode)!="")
						{
							addDetialBarnoRecord();
							gridlen=gridlen+1;
						}
						var curaarDetialRecord=commoninfodivdetial_barcode.getRow(gridlen-1);
						commoninfodivdetial_barcode.updateRow(curaarDetialRecord,{frombarcode: document.getElementById("winsergoodsbarno").value
																  				 ,returngoodsno : responsetext.curGoodsinfo.id.goodsno }); 
					}
		       	}
		       	else
		       	{
		       		$.ligerDialog.warn(strMessage);
		       		
		       	}
		       	document.getElementById("winsergoodsbarno").value="";
		       	document.getElementById("winsergoodsbarno").focus();
		       	document.getElementById("winsergoodsbarno").select();
	        }
			catch(e)
			{
				alert(e.message);
			}
	   	}
		
		function loadGoodsBarCordMessage(goodsno)
	    {
	    	try
			{
		       
				var existsflag=0;
				var returncount=0;
				for (var rowid in commoninfodivdetial.records)
				{
						var row =commoninfodivdetial.records[rowid];
						if(row.returngoodsno==goodsno )	
						{
							factreturncount=row.factreturncount;
							if(parent.lsGoodsinfo!=null && parent.lsGoodsinfo.length>0)
							{
								for(var i=0;i<parent.lsGoodsinfo.length;i++)
								{
									if( parent.lsGoodsinfo[i].id.goodsno==goodsno)
									{
										commoninfodivdetial.updateRow(row,{ factreturncount : factreturncount*1+1,
																						factreturnprice: parent.lsGoodsinfo[i].costamtbysale,
										                                                factreturnamt  : ForDight(parent.lsGoodsinfo[i].costamtbysale*(factreturncount*1+1)*1,2)}); 
									}
								}
							}
							existsflag=1;
							break;
						}	
				}
				if(existsflag==0)
				{
					$.ligerDialog.error("该条码对应的产品不在退货列表之内!");
					return false;
				}
				return true;
	        }
			catch(e)
			{
				alert(e.message);
			}
	    }
    
    
    
       
    function validateReturnType(obj)
    {
    	var returngoodsno=curDetialRecord.returngoodsno;
    	if(parent.lsGoodsinfo!=null && parent.lsGoodsinfo.length>0)
		{
			for(var i=0;i<parent.lsGoodsinfo.length;i++)
			{
				if( parent.lsGoodsinfo[i].id.goodsno==returngoodsno)
				{
					if(obj.value==1)
					{
						commoninfodivdetial.updateRow(curDetialRecord,{revicestoreno   : parent.lsGoodsinfo[i].goodswarehouse}); 
					}
					else
					{
						commoninfodivdetial.updateRow(curDetialRecord,{revicestoreno   : parent.lsGoodsinfo[i].goodssupplier}); 
					}
				}
			}
		}
    }
    
    function validatefactreturncount(obj)
    {
    	var returncount=curDetialRecord.returncount;
    	if(returncount*1<obj.value*1)
    	{
    		$.ligerDialog.error("实际退货数量不能超过申请退货数量!");
    		commoninfodivdetial.updateRow(curDetialRecord,{factreturncount:0 ,factreturnprice:0,factreturnamt  : 0}); 
			return ;
    	}
    	var returngoodsno=curDetialRecord.returngoodsno;
    	if(parent.lsGoodsinfo!=null && parent.lsGoodsinfo.length>0)
		{
			for(var i=0;i<parent.lsGoodsinfo.length;i++)
			{
				if( parent.lsGoodsinfo[i].id.goodsno==returngoodsno)
				{
					commoninfodivdetial.updateRow(curDetialRecord,{factreturnprice: parent.lsGoodsinfo[i].costamtbysale,
					                                                factreturnamt  : ForDight(parent.lsGoodsinfo[i].costamtbysale*obj.value*1,2)}); 
				}
			}
		}
    }
    
    function validatefactreturnprice(obj)
    {
    	commoninfodivdetial.updateRow(curDetialRecord,{factreturnamt  : ForDight(curDetialRecord.factreturncount*1*obj.value*1,2)}); 
			
    }
  
    function searchOrderBill()
    {
    	if(document.getElementById("strSearchBillId").value=="")
		{
			$.ligerDialog.error('请填写查询信息!');
			return;
		}
    	var requestUrl ="ic007/searchOrderBill.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strSearchBillId="+document.getElementById("strSearchBillId").value;
		var responseMethod="searchOrderBillMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function searchOrderBillMessage(request)
   	{
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMreturngoodsinfo!=null && responsetext.lsMreturngoodsinfo.length>0)
	   		{
	   			curpagestate=3;
	   			curMasterInfoDate={Rows: responsetext.lsMreturngoodsinfo,Total: responsetext.lsMreturngoodsinfo.length};
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
	 function handconfirmInfo()
    {
    	 showDialogmanager = $.ligerDialog.waitting('正在复合中,请稍候...');
    	 curpagestate=3;
    	if(parent.hasFunctionRights( "IC007",  "UR_POST")!=true)
        {
       		$.ligerDialog.error("该用户没有保存权限,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
	    if(document.getElementById("returnstaffid").value=="")
		{
			$.ligerDialog.error('请输入退货员工!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if($("#factcreturnwareid").val()=="")
		{
			$.ligerDialog.error('请选择退货仓库!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if(commoninfodivdetial.rows.length*1>0 )
		{
			   commoninfodivdetial.endEdit();
		}
		document.getElementById("returnwareid_h").value=$("#factcreturnwareid").val();
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic007/handconfirmInfo.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.returngoodsno)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				/*if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}	      */      	
				if(checkNull(row.returntype)=="" && checkNull(row.factreturncount)*1>0 )	   
				{
					$.ligerDialog.error(checkNull(row.returngoodsname)+'请选择退货方向!');
					showDialogmanager.close();
					curpagestate=2;
					return;
				}
				
				if(checkNull(row.returntype)==1 && checkNull(row.factreturncount)*1>0 && checkNull(row.revicestoreno)==""  )	   
				{
					$.ligerDialog.error(checkNull(row.returngoodsname)+'请选择退货仓库!');
					showDialogmanager.close();
					curpagestate=2;
					return;
				}
				
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		strJsonParam_detial="";
        for (var rowid in commoninfodivdetial_barcode.records)
		{
				var row =commoninfodivdetial_barcode.records[rowid];
				if(checkNull(row.returngoodsno)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				/*if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}*/	            		   
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strBarJsonParam=["+strJsonParam_detial+"]";
		}	
		var responseMethod="handconfirmInfoMessage";
	
		sendRequestForParams_p(requestUrl,responseMethod,params );
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
    	$.ligerDialog.confirm('是否驳回操作', function (result)
		{
		    if( result==true)
            {
    			var requestUrl ="ic007/handcancelInfo.action";
        		var params="strCurCompId="+document.getElementById("returncompid").value;
        		params=params+"&strCurBillId="+document.getElementById("returnbillid").value;
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
    
    
	function printReport()
	{
		var specailurl=contextURL+'/InvoicingControl/IC007/ic007_print.jsp';
	 	window.open(specailurl,"","top=" +2+ ",left=" + 100+",width=800,height=600,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	}
	function selectCall()
   {
		validateinserno(document.getElementById("winsergoodsno"));
   }
      	 