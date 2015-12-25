   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivmaster=null;	//产品入库主档列表
   	var commoninfodivdetial=null;	//产品入库明细列表
   	var commoninfodivdetial_barcode=null; //条码卡列表
   	var showCompInfodiv=null;
   	var strCurCompId="";
   	var strCurBillId="";
   	var curMasterInfoDate=null;
   	var fromValidate=null;
   	var cinsertwaremanager = null;
   	var cinsertcardtypemanager = null;
   	var cinsertpermanager = null;
   	var ic019layout=null;
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
            ic019layout= $("#ic019layout").ligerLayout({ rightWidth: 245,  allowBottomResize: false});
	   		var height = $(".l-layout-center").height();
          
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { 	name: 'bentrycompid', hide:true	},
                { display: '配发单号', 	name: 'bentrybillid', 	width:110,align: 'left'},
                { display: '配发门店', 	name: 'allotmentcompid', 		width:60,align: 'left'},
	            { display: '配发日期', 	name: 'allotmentdate', 		width:80,align: 'left'},
	            { display: '申请单号', 	name: 'apporderbillno', 	width:125,align: 'left'}   
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '400',
                height:height-70,
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
	                { text: '新增', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
					]
                }	   
            });
           
     
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '产品编号', 	name: 'allotmentgoodsno',  				width:100,align: 'left' },
                { display: '产品名称', 	name: 'allotmentgoodsname',  			width:200,align: 'left' },
                { display: '单位', 		name: 'allotmentgoodsunit', 			width:30,align: 'left'},
                { display: '库存', 		name: 'headstockcount', 				width:80,align: 'right'},
	            { display: '配发数量', 	name: 'allotmentgoodscount', 			width:80,align: 'right'},
	            { display: '配发单价', 	name: 'allotmentgoodsprice', 			width:80,align: 'right' },
	            { display: '配发金额', 	name: 'allotmentgoodsamt', 				width:80,align: 'right' },
	            { hide:true	,  name: 'supplierno' , 				    width:1},
	            { hide:true , 	name: 'headwareno', 				    width:1},
	             { hide:true , 	name: 'goodssource', 				    width:1}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '740',
                height:height-235,
                clickToEdit: true,   enabledEdit: true,  checkbox:true,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curDetialRecord=data;
                }
                
            });
            
             showCompInfodiv=$("#showCompInfodiv").ligerGrid({
                columns: [
                { display: '门店名称', name: 'allotmentcompname', width: 60, align: 'left'  },
                { display: '门店申请单', name: 'apporderbillno', width: 105, align: 'left'  },
                { display: '配发', name: 'checkFlag',  width: 50 , render: checkboxRender  },
                {  name: 'compno',   hide: true , width:1  }
                ],  pageSize:10, 
                data: null,      rownumbers: false,usePager: false,    
                width: 250,
                height: height-45
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
          	//addMasterRecord();
          	curpagestate=1;
		   	addRecord();
          	addDetialRecord();
          	pageWriteState();
          	addCompRecord();
   		}catch(e){alert(e.message);}
    }); 
    function loadCurMaster(curMaster)
    {
		try
		{
		
	    	document.getElementById("entrycompid").value=checkNull(curMaster.bentrycompid);
	    	document.getElementById("entrybillid").value=checkNull(curMaster.bentrybillid);
	    	document.getElementById("allotmenttaffid").value=checkNull(curMaster.allotmenttaffid);
	    	document.getElementById("bentrycompname").value=checkNull(curMaster.bentrycompname);
	    	document.getElementById("allotmenttaffname").value=checkNull(curMaster.allotmenttaffanme);
	    	document.getElementById("allotmentdate").value=checkNull(curMaster.allotmentdate);
	    	document.getElementById("allotmenttime").value=checkNull(curMaster.allotmenttime);
	    	document.getElementById("apporderbillno").value=checkNull(curMaster.apporderbillno);
	    	document.getElementById("allotmentcompid").value=checkNull(curMaster.allotmentcompid);
	    	document.getElementById("allotmentcompname").value=checkNull(curMaster.allotmentcompname);
	    	document.getElementById("recevicestaffid").value=checkNull(curMaster.recevicestaffid);
	    	document.getElementById("recevicestaffname").value=checkNull(curMaster.recevicestaffname);
	    	document.getElementById("allotmenttype").value=checkNull(curMaster.allotmenttype);
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
			document.getElementById("wheadstockcount").value=0;
			
			
		}catch(e){alert(e.message);}
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	document.getElementById("allotmenttaffid").readOnly="";
	    	document.getElementById("allotmentcompid").readOnly="";
	    	document.getElementById("recevicestaffid").readOnly="";
	    	document.getElementById("winsergoodsno").readOnly="";
	    	document.getElementById("winsergoodscount").readOnly="";
	    
	    	commoninfodivdetial.options.enabledEdit=true;
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("allotmenttaffid").readOnly="readOnly";
	    	document.getElementById("allotmentcompid").readOnly="readOnly";
	    	document.getElementById("recevicestaffid").readOnly="readOnly";
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
        		 strCurCompId=data.bentrycompid;
        		 strCurBillId=data.bentrybillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"ic019/loadDorderInfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsDgoodsallotmentinfo!=null && responsetext.lsDgoodsallotmentinfo.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsallotmentinfo,Total: responsetext.lsDgoodsallotmentinfo.length});
					            	commoninfodivdetial.loadData(true);  
					            	pageReadState();
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
					            	addDetialRecord();
					            	pageWriteState();
									curpagestate=1;
						   		}
						   		if(responsetext.lsDcompallotmentinfo!=null && responsetext.lsDcompallotmentinfo.length>0)
						   		{
						   			showCompInfodiv.options.data=$.extend(true, {},{Rows: responsetext.lsDcompallotmentinfo,Total: responsetext.lsDcompallotmentinfo.length});
					            	showCompInfodiv.loadData(true);  
						   		}
						   		else
						   		{
						   			showCompInfodiv.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	showCompInfodiv.loadData(true);  
					            	addCompRecord();
						   		}
	   							loadCurMaster(responsetext.curMgoodsallotmentinfo);
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
	    	 $.ligerDialog.confirm('确认提交 '+strCurCompId+' 产品配发 '+item.text+' 操作', function (result)
		     {
		     	if( result==true)
            	{
		          	if(item.text=="新增")
		        	{
		        		 if(curpagestate!=3)
		        		 {
		        		 	$.ligerDialog.error("请保存当前信息或刷新本界面后在进行新增操作!");
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
		        		 	$.ligerDialog.error("未修改数据,无需保存!");
		        		 	return ;
		        		 }
		        	 	editCurRecord();
		        	} 	
		        	else if(item.text=="删除")
		        	{
		        		 if(curpagestate!=3)
		        		 {
		        		 	$.ligerDialog.error("请保存当前信息或刷新本界面后在进行刷新操作!");
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
    	if(parent.hasFunctionRights( "IC019",  "UR_POST")!=true)
        {
       		$.ligerDialog.error("该用户没有保存权限,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
      	
	    if(document.getElementById("allotmenttaffid").value=="")
		{
			$.ligerDialog.error('请输入配发员工!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		
		if(document.getElementById("allotmenttype").value==1)
		{
			if(document.getElementById("allotmentcompid").value=="")
			{
				$.ligerDialog.error('请输入配发门店!');
				showDialogmanager.close();
				curpagestate=2;
				return;
			}
			if(document.getElementById("recevicestaffid").value=="")
			{
				$.ligerDialog.error('请输入门店接收人!');
				showDialogmanager.close();
				curpagestate=2;
				return;
			}
		}
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic019/post.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.allotmentgoodsno)=="")
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
		 curjosnparam="";
         needReplaceStr="";
         strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in showCompInfodiv.records)
		{
				var row =showCompInfodiv.records[rowid];
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
			 params=params+"&strCompJsonParam=["+strJsonParam_detial+"]";
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
     		if(parent.hasFunctionRights( "IC019",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        	  
				var params = "strCurCompId="+strCurCompId;		
    	     	var requestUrl ="ic019/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
				
	        }
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var row = commoninfodivmaster.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivmaster.addRow({ 
				                 bentrycompid: checkNull(responsetext.curMgoodsallotmentinfo.bentrycompid),
				  				 bentrybillid: checkNull(responsetext.curMgoodsallotmentinfo.bentrybillid),
				  				 allotmentcompid:'',
				  				 allotmentdate: checkNull(responsetext.curMgoodsallotmentinfo.allotmentdate),
				  				 apporderbillno: ''
				            }, row, false);
				            
	    		loadCurMaster(responsetext.curMgoodsallotmentinfo);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
            	addDetialRecord();
            	document.getElementById("needPost").value="1"; 
		   	}catch(e){alert(e.message);}
     }
     
   //--------------------------验证验货人员-----------------
   function validateInserper(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("allotmenttaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic019/validateInserper.action";
        var params="strCurCompId="+document.getElementById("entrycompid").value;
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
	       		document.getElementById("allotmenttaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("allotmenttaffid").value="";
	       		document.getElementById("allotmenttaffname").value="";
	       		$.ligerDialog.error(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function validateReceviceId(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("recevicestaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic019/validateInserper.action";
        var params="strCurCompId="+document.getElementById("allotmentcompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validateReceviceMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validateReceviceMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("recevicestaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("recevicestaffid").value="";
	       		document.getElementById("recevicestaffname").value="";
	       		$.ligerDialog.error(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	//--------------------------验证验货人员-----------------
   function validateAllotmentcompid(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("allotmentcompname").value="";
   			return ;
   		}
   		var requestUrl ="ic019/validateComp.action";
        var params="strCurCompId="+obj.value;
		var responseMethod="validateCompMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validateCompMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(responsetext.strCurCompName)!="")
	       	{	       		 
	       		document.getElementById("allotmentcompname").value=responsetext.strCurCompName;
	       	}
	       	else
	       	{
	       		document.getElementById("allotmentcompid").value="";
	       		document.getElementById("allotmentcompname").value="";
	       		$.ligerDialog.error("门店编号不存在!");
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
				  bentrycompid: "",
				  bentrybillid: "",
				  allotmentcompid:"",
				  allotmentdate: "",
				  apporderbillno: ""          
			}, row, false);
    }
    function addCompRecord()
    {
    		var row = showCompInfodiv.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			showCompInfodiv.addRow({ 
				  allotmentcompno: '',
				  allotmentcompname: '',
				  apporderbillno: '',
				  checkFlag: false          
			}, row, false);
    }
    function addDetialRecord()
    {
    		var row = commoninfodivdetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
		 	commoninfodivdetial.addRow({ 
				      allotmentgoodsno: "",
				      allotmentgoodsname: "",
				      allotmentgoodsunit: "",
				      headstockcount: "" ,
				      allotmentgoodscount: "" ,
				      allotmentgoodsprice: "",
				      allotmentgoodsamt: "",
				      supplierno : ''  ,
				      headwareno : '' ,
					  goodssource : ''
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
		        	$.ligerDialog.warn("未修改数据,无需保存!");
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
			 if(document.getElementById("allotmenttaffid").value=="")
			{
				$.ligerDialog.error('请输入申请员工!');
				curpagestate=2;
				return;
			}
			if(document.getElementById("allotmenttype").value==1)
			{
				if(document.getElementById("allotmentcompid").value=="")
				{
					$.ligerDialog.error('请输入配发门店!');
					curpagestate=2;
					return;
				}
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
							document.getElementById("winsergoodscount").value=0;
							document.getElementById("winsergoodscount").focus();
							document.getElementById("winsergoodscount").select();
							goodscrentwareno=checkNull(parent.lsGoodsinfo[i].goodswarehouse);
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
				var requestUrl ="ic019/loadCurStock.action";
        		var params="strCurCompId=001";
        		params=params+"&strCurGoodsId="+obj.value;
        		params=params+"&strWareId="+checkNull(parent.lsGoodsinfo[i].goodswarehouse);
        		params=params+"&strCurDate="+document.getElementById("allotmentdate").value;
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
				if(obj.value*1>document.getElementById("wheadstockcount").value)
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
				if(checkNull(commoninfodivdetial.getRow(0).allotmentgoodsno)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}   
				curDetialRecord=commoninfodivdetial.getRow(gridlen-1);
				commoninfodivdetial.updateRow(curDetialRecord,{allotmentgoodsno: document.getElementById("winsergoodsno").value
															  ,allotmentgoodsname : document.getElementById("winsergoodsname").value
															  ,allotmentgoodsunit  : document.getElementById("winsergoodsunit").value
															  ,headstockcount: ForDight(document.getElementById("wheadstockcount").value*1,1)
															  ,allotmentgoodscount : ForDight(document.getElementById("winsergoodscount").value*1,2)
															  ,allotmentgoodsprice : ForDight(document.getElementById("winsergoodsprice").value*1,2)
															  ,allotmentgoodsamt   : ForDight(document.getElementById("winsergoodsprice").value*1*document.getElementById("winsergoodscount").value*1,1)
															  ,supplierno : document.getElementById("wsupplierno").value
															  ,headwareno : document.getElementById("wheadwareno").value
															  ,goodssource : document.getElementById("wgoodssource").value});  
				      
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
				document.getElementById("wheadstockcount").value=0;
				document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
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
    	var requestUrl ="ic019/searchOrderBill.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strCurBillId="+document.getElementById("strSearchBillno").value;
        params=params+"&strCurDate="+document.getElementById("strSearchDate").value;
		var responseMethod="searchOrderBillMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function searchOrderBillMessage(request)
   	{
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMgoodsallotmentinfo!=null && responsetext.lsMgoodsallotmentinfo.length>0)
	   		{
	   			curpagestate=3;
	   			curMasterInfoDate={Rows: responsetext.lsMgoodsallotmentinfo,Total: responsetext.lsMgoodsallotmentinfo.length};
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
   
   
   function validateAllotmentType(obj)
   {
   		if(obj.value==1)
   		{
   			document.getElementById("allotmentcompid").readOnly="";
		    document.getElementById("allotmentcompid").style.background="#FFFFFF";
			document.getElementById("recevicestaffid").readOnly="";
		    document.getElementById("recevicestaffid").style.background="#FFFFFF";
		    showCompInfodiv.options.data=$.extend(true, {},{Rows: null,Total:0});
            showCompInfodiv.loadData(true); 
            addCompRecord(); 
   		}
   		else
   		{
   			document.getElementById("allotmentcompid").value="";
   			document.getElementById("recevicestaffid").value="";
   			document.getElementById("allotmentcompid").readOnly="readOnly";
		    document.getElementById("allotmentcompid").style.background="#EDF1F8";
			document.getElementById("recevicestaffid").readOnly="readOnly";
		    document.getElementById("recevicestaffid").style.background="#EDF1F8";
		    showCompInfodiv.options.data=loadCompnayDataGridByInfotype();
            showCompInfodiv.loadData(true); 
   		}
   }
   
     
	    function loadCompnayDataGridByInfotype()
		{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(parent.lsCompanyinfos!=null && parent.lsCompanyinfos.length>0)
				{	
					for(var i=0;i<parent.lsCompanyinfos.length;i++)
					{
						ccount=ccount*1+1;
						if(ccount==1)
						{
							strJson=strJson+'[';
						}
						else
						{
							strJson=strJson+',';
						}
						strJson=strJson+'{ "allotmentcompno":"'+parent.lsCompanyinfos[i].compno+'", "allotmentcompname": "'+parent.lsCompanyinfos[i].compname+'","checkFlag":""   }';
					}
					if(strJson!="")
					{
						strJson=strJson+']';
						return {Rows:JSON.parse(strJson),Total:2};
					}
					return null;
				}
			
			}catch(e){alert(e.message);}
		}
       //是否类型的模拟复选框的渲染函数
	function checkboxRender(rowdata, rowindex, value, column)
	{
	    var iconHtml = '<div class="chk-icon';
	    if (value) iconHtml += " chk-icon-selected";
	    iconHtml += '"';
	    iconHtml += ' rowid = "' + rowdata['__id'] + '"';
	    iconHtml += ' gridid = "' + this.id + '"';
	    iconHtml += ' columnname = "' + column.name + '"';
	    iconHtml += '></div>';
	    return iconHtml;
	}
	//是否类型的模拟复选框的点击事件
	$("div.chk-icon").live('click', function ()
	{
	    var grid = $.ligerui.get($(this).attr("gridid"));
	    var rowdata = grid.getRow($(this).attr("rowid"));
	    var columnname = $(this).attr("columnname");
	    var checked = rowdata[columnname];
	     
	    grid.updateCell(columnname, !checked, rowdata);
	});
	       