   	var commoninfodivmaster=null;	//产品入库主档列表
   	var commoninfodivdetial=null;	//产品入库明细列表
   	var commoninfodivdetial_barcode=null; //条码卡列表
   	var commoninfodivdetial_pcinser=null;  //入库批次号
   	var commoninfodivdetial_pcouter=null;  //出库批次号
   	var strCurCompId="";
   	var strCurBillId="";
   	var curMasterInfoDate=null;
   	var fromValidate=null;
   	var cinsertwaremanager = null;
   	var cinsertcardtypemanager = null;
   	var cinsertpermanager = null;
   	var ic004layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
   	var insertypemanager=null;
	var curDetialRecord = null;
   	//var insertypeData=JSON.parse(parent.loadCommonControlDate_select("CKFS",0));
   	var showDialogmanager=null;
    var rightmenu;
    var iGoodsBarCode="";
    var istartBarCode="";
    var iendBarCode="";
    var confirmInfo=null;
    var cancelInfo=null;
    var lsDgoodsouters=null;
    var companyinfos=null;
       	var postState=0;
   	//初始化属性
    $(function ()
   	{
	   try
	   {  
	   	 	  //布局
            ic004layout= $("#ic004layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
	   		var height = $(".l-layout-center").height();
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { 	name: 'boutercompid', hide:true	},
                { display: '出库单号', 	name: 'bouterbillid', 	width:140,align: 'left'},
                { display: '出库方式', 	name: 'outertype', 		width:100,align: 'left',
	                	editor: { type: 'select', data: insertypeData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("CKFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.outertype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		        } 
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '260',
                height:'675',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
	                { text: '增加', click: itemclick_cardOuterInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_cardOuterInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
					{ text: '删除', click: itemclick_cardOuterInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
	                 ]
                },
                groupColumnName:'outerwareid',
 				groupRender: function (outerwareid,groupdata)
				{
					if(outerwareid=="03")
						return '自用品仓库';
					else if(outerwareid=="05")
						return '总部行政仓库';
					else if(outerwareid=="06")
						return '现金仓库';
				}       
            });
           
           commoninfodivdetial_pcinser=$("#commoninfodivdetial_pcinser").ligerGrid({
                columns: [
                { display: '入库批次', 	name: 'inserbillid',  			width:120,align: 'left' },
                { display: '入库日期', 	name: 'inserdate',  			width:90,align: 'left' },
                { display: '存量', 		name: 'curlavecount',  			width:40,align: 'left' },
                { display: '出库', 		name: 'outercount', 			width:40,align: 'left',align: 'center',editor: { type: 'int' }},
              	{  hide:true ,	name: 'inserseqno' },
              	{ hide:true ,	name: 'outerprice' },
              	{  hide:true ,	name: 'insergoodsno' }
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '300',
                height:'190',
                clickToEdit: true,   enabledEdit: true,  checkbox: false,
                rownumbers: false, usePager: false,
               
                toolbar: { items: [
	                		{ text: '选择入库批次', click: itemclick_GoodsPcfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/right.gif' }
    				 	 ]}	
	           
            });
       
         	$("#onebartoolbar").ligerToolBar({items: [
      		    { text: '<font color="blue">产品条码</font>:&nbsp;<input type="text" id="onebarNo" name="onebarNo" style="width:140" onchange="validateOneBar(this)"/> '}
            ]
            });	
       		commoninfodivdetial_pcouter=$("#commoninfodivdetial_pcouter").ligerGrid({
                columns: [
                { display: '入库批次', 	name: 'inserbillid',  			width:120,align: 'left' },
                { display: '产品编号', 	name: 'insergoodsno',  			width:80,align: 'left' },
                { display: '出库数量', 		name: 'outercount', 		width:80,align: 'left'},
                { hide:true ,	name: 'inserseqno' },
                { hide:true ,	name: 'outerprice' }
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '300',
                height:'475',
                clickToEdit: true,   enabledEdit: true,  checkbox: false,
                rownumbers: false, usePager: false
            });
       		
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '产品编号', 	name: 'outergoodsno',  			width:100,align: 'left' },
                { display: '产品名称', 	name: 'outergoodsname',  		width:250,align: 'left' },
                { display: '单位', 		name: 'outerunit', 				width:50,align: 'left'},
                { display: '库存', 	name: 'curgoodsstock', 				width:50,align: 'right'},
	            { display: '数量', 		name: 'outercount', 			width:50,align: 'right' , editor: { type: 'int' ,onChanged : validatefactoutcount}},
	            { display: '单价', 		name: 'outerprice', 			width:50,align: 'right' },
	            { display: '进货金额', 	name: 'outeramt', 				width:60,align: 'right' }  ,
	            {  hide:true ,	name: 'standunit' },
	            {  hide:true ,	name: 'standprice'}       
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '735',
                height:'500',
                clickToEdit: true,   enabledEdit: true,  checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	
                	curDetialRecord=data;
                   
                } ,
                 toolbar: { items: [
	                		{ text: '打印', click: printReport, img: contextURL+'/common/ligerui/ligerUI/skins/icons/print.gif' }
    				 	 ]}	
            });
            
          	$('#thetable').tableScroll({
				width:280,
				height:620
			});
			
			
			
			 var  insertypeData=JSON.parse(parent.loadCommonControlDate("CKFS",0));
           	 insertypemanager = $("#outertype").ligerComboBox({ data: insertypeData, isMultiSelect: false,valueFieldID: 'factoutertype',width:'140' });           	 
           	
           	 $("#searchButton").ligerButton(
	         {
	             text: '查询单据', width: 80,
		         click: function ()
		         {
		             searchOrderBill();
		         }
	         });
	          confirmInfo=$("#confirmInfo").ligerButton(
	         {
	             text: '复合出库', width: 120,
		         click: function ()
		         {
		             handconfirmInfo();
		         }
	         });
           	
          	$("#pageloading").hide(); 
          	addMasterRecord();
          	addDetialRecord();
          	addDetialinsertpcRecord();
          	addDetialoutertpcRecord();
          	loadCardInsertList();
          	
   		}catch(e){alert(e.message);}
    });
//    
     //加载卡出库主明细
    function loadprint()
    { 
    	 var params ="strCurCompId="+ document.getElementById("outercompid").value;
    	 	 params =params+"&strSendCurCompId="+ document.getElementById("outerstaffid").value;
        	 params=params+"&strCurBillId="+document.getElementById("outerbillid").value;
     	var requestUrl ="ic004/loadDgoodsouterss.action"; 
		var responseMethod="loadGoodprint";				
		sendRequestForParams_p(requestUrl,responseMethod,params);
    }
   
   function loadGoodprint(request)
   {
 			var responsetext = eval("(" + request.responseText + ")");
 			lsDgoodsouters=responsetext.lsDgoodsouter;
 			companyinfos=responsetext.companyinfo;
    }
    
    
    //加载卡出库主明细
    function loadCardInsertList()
    {
     	var requestUrl ="ic004/loadGoodsOuterInfo.action"; 
		var responseMethod="loadGoodsOuterInfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function loadGoodsOuterInfoMessage(request)
   {
       		try
        	{
 			var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.lsMgoodsouter!=null && responsetext.lsMgoodsouter.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMgoodsouter,Total: responsetext.lsMgoodsouter.length};
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
	   		document.getElementById("useBarFlag").value=responsetext.useBarFlag;
	   		
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
		try
		{
	    	document.getElementById("outercompid").value=checkNull(curMaster.boutercompid);
	    	document.getElementById("outerbillid").value=checkNull(curMaster.bouterbillid);
	    	document.getElementById("outerdate").value=checkNull(curMaster.outerdate);
	    	document.getElementById("outertime").value=checkNull(curMaster.outertime);
	    	document.getElementById("outerstaffid").value=checkNull(curMaster.outerstaffid);
	    	document.getElementById("boutercompname").value=checkNull(curMaster.boutercompname);
	    	document.getElementById("outerstaffname").value=checkNull(curMaster.outerstaffname);
	    	insertypemanager.selectValue(checkNull(curMaster.outertype));
	    	document.getElementById("outeropationerid").value=checkNull(curMaster.outeropationerid);
	    	document.getElementById("outeropationername").value=checkNull(curMaster.outeropationername);
	    	document.getElementById("outeropationdate").value=checkNull(curMaster.outeropationdate);
	    	document.getElementById("outerwareid").value=checkNull(curMaster.outerwareid);
	    	document.getElementById("orderbilltype").value=checkNull(curMaster.orderbilltype);
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
			document.getElementById("wcurgoodsstock").value=0;
			handleRadio("curMgoodsouter.revicetype",checkNull(curMaster.revicetype));
			loadprint();
			if(document.getElementById("outeropationdate").value=="")
			{
				pageWriteState();
				curpagestate=1;
			}
			else
			{
				pageReadState();
			}
			if(document.getElementById("outeropationdate").value=="")
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
	    	document.getElementById("outerstaffid").readOnly="";
	   		insertypemanager.setEnabled();
	   		
	    	document.getElementById("wfrombarcode").readOnly="";
	    	document.getElementById("wtobarcode").readOnly="";
	    	commoninfodivdetial.options.enabledEdit=true;
	    	commoninfodivdetial_pcinser.options.enabledEdit=true;
	    	
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("outerstaffid").readOnly="readOnly";
	   		insertypemanager.setDisabled();
	   		
	    	document.getElementById("wfrombarcode").readOnly="readOnly";
	    	document.getElementById("wtobarcode").readOnly="readOnly";
	    	commoninfodivdetial.options.enabledEdit=false;
	    	commoninfodivdetial_pcinser.options.enabledEdit=true;
    	}catch(e){alert(e.message);}
    }
    
    //根据主档选择加载明细
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        	
        		 
        		 strCurCompId=data.boutercompid;
        		 strCurBillId=data.bouterbillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"ic004/loadDgoodsouter.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsDgoodsouter!=null && responsetext.lsDgoodsouter.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsouter,Total: responsetext.lsDgoodsouter.length});
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
						   		
						   		
	   							loadCurMaster(responsetext.curMgoodsouter);
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
    }
    //------------------------主档按钮操作-------------------Start
    function itemclick_cardOuterInfo(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认提交 '+strCurCompId+' 产品出库 '+item.text+' 操作', function (result)
		     {
		     	if( result==true)
            	{
		          	if(item.text=="增加")
		        	{
		        		
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
    try
    {
    	if(postState==1)
		{
			$.ligerDialog.error("正在保存中,请不要连续保存!");
			return ;
		}
   		 postState=1;
    	 showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');	
    	 curpagestate=3;
    	if(parent.hasFunctionRights( "IC004",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       		postState=0;
       	 	return;
        }
        if(document.getElementById("needPost").value!="1")
        {
       		$.ligerDialog.error("该单据只能复合，不能保存,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       		postState=0;
       	 	return;
        }
	    if(document.getElementById("outerstaffid").value=="")
		{
			$.ligerDialog.error('请输出库员工!');
			showDialogmanager.close();
			curpagestate=2;
			postState=0;
			return;
		}
		if($("#factoutertype").val()=="")
		{
			$.ligerDialog.error('请选择出库方式!');
			showDialogmanager.close();
			curpagestate=2;
			postState=0;
			return;
		}
		if(commoninfodivdetial.rows.length*1>0 )
		{
			   commoninfodivdetial.endEdit();
		}
	
		document.getElementById("outertype_h").value=$("#factoutertype").val();
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic004/post.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------出库明细
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.outergoodsno)=="")
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
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		
		curjosnparam="";
        needReplaceStr="";
        strJsonParam_detial="";
        //------出库批次
        for (var rowid in commoninfodivdetial_pcouter.records)
		{
				var row =commoninfodivdetial_pcouter.records[rowid];
				curjosnparam=JSON.stringify(row);
			
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
			 params=params+"&strJsonPcParam=["+strJsonParam_detial+"]";
		}

		var responseMethod="postMessage";
	    sendRequestForParams_p(requestUrl,responseMethod,params );
		}catch(e){alert(e.message);}
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
	        	alert(strMessage);
	        }
	        postState=0;
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
     		if(parent.hasFunctionRights( "IC004",  "UR_MODIFY")!=true)
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
				                bouterbillid: "",
				                outertype: "",
				                outerdate: "",
				                boutercompid: ""
				             
				            }, row, false);
				var params = "";			
    	     	var requestUrl ="ic004/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	        }
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    		loadCurMaster(responsetext.curMgoodsouter);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
            	addDetialRecord();
            	document.getElementById("needPost").value="1"; 
		   	}catch(e){alert(e.message);}
     }
     
    //删除行
    function deleteCurRecord()
    {
       	if(parent.hasFunctionRights( "IC004",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var requestUrl ="ic004/delete.action";
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
   			document.getElementById("inserstaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic004/validateInserper.action";
        var params="strCurCompId="+document.getElementById("outercompid").value;
        params=params+"&strCurEmpId="+obj.value;
        if(document.getElementById("revicetype1").checked==true)
        {
        	 params=params+"&revicetype="+1;
        }
  		else
  		{
  			 params=params+"&revicetype="+2;
  		}
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
	       		document.getElementById("outerstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("outerstaffname").value="";
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
				          
			}, row, false);
    }
    
    function addDetialRecord()
    {
    		var row = commoninfodivdetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({ 
				      outergoodsno: "",
				      outergoodsname: "",
				      outerunit: "",
				      outercount: "" ,
				      outerprice: "",
				      outeramt: "",
				      frombarcode: "",
				      tobarcode:  "" ,
				      standunit:  "", 
				      standprice: "",
				      curgoodsstock:0
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
    function addDetialinsertpcRecord()
    {
    		var row = commoninfodivdetial_pcinser.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_pcinser.addRow({ 
				    
			}, row, false);
    }
    
  	function addDetialoutertpcRecord()
    {
    		var row = commoninfodivdetial_pcouter.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_pcouter.addRow({ 
				inserbillid:'',
				outercount:'',
				inserseqno:0,
				insergoodsno:'',
				outerprice:0
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
		 			$.ligerDialog.warn("非新增操作,不可编辑出库明细!");
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
		
		function validateinserno(obj)
		{
			if(document.getElementById("outerstaffid").value=="")
	        {
	        	 $.ligerDialog.warn("请确认出库员工!");
	        	 obj.value="";
	        	 return;
	        }
	        if(document.getElementById("outerwareid").value=="")
	        {
	        	 $.ligerDialog.warn("请确认出库仓库!");
	        	  obj.value="";
	        	 return;
	        }
	        if($("#factoutertype").val()=="")
	        {
	        	 $.ligerDialog.warn("请确认出库方式!");
	        	  obj.value="";
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
				document.getElementById("wcurgoodsstock").value=0;
				obj.focus();
				obj.select();
			}
			else
			{
				/*var checkflag=0;
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
							document.getElementById("winsergoodsunit").value=checkNull(parent.lsGoodsinfo[i].saleunit);
							document.getElementById("winsergoodsprice").value=ForDight(parent.lsGoodsinfo[i].storesalseprice,2);
							document.getElementById("wgoodsformat").value=checkNull(parent.lsGoodsinfo[i].goodsformat);
							document.getElementById("wgoodsuniquebar").value=checkNull(parent.lsGoodsinfo[i].goodsuniquebar);
							document.getElementById("wstandunit").value=checkNull(parent.lsGoodsinfo[i].saleunit);
							document.getElementById("wstandprice").value=ForDight(parent.lsGoodsinfo[i].storesalseprice,2);
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
									document.getElementById("winsergoodsunit").value=checkNull(parent.lsGoodsinfo[i].saleunit);
									document.getElementById("wgoodsformat").value=checkNull(parent.lsGoodsinfo[i].goodsformat);
									document.getElementById("winsergoodsprice").value=ForDight(checkNull(parent.lsGoodsinfo[i].storesalseprice),2);
									document.getElementById("wstandunit").value=checkNull(parent.lsGoodsinfo[i].saleunit);
									document.getElementById("wstandprice").value=ForDight(parent.lsGoodsinfo[i].standprice,2);
									document.getElementById("wgoodsuniquebar").value=checkNull(parent.lsGoodsinfo[i].goodsuniquebar);
									
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
	        	  
				}*/
				var requestUrl ="ic004/validateGoodsNo.action";
        		var params="strCurCompId="+document.getElementById("outercompid").value;
        		params=params+"&strCurGoodsId="+obj.value;
        		params=params+"&strWareId="+document.getElementById("outerwareid").value;
        		params=params+"&strCurDate="+document.getElementById("outerdate").value;
				var responseMethod="validateGoodsNoMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 	
			}
		}
		
		
		function validateGoodsNoMessage(request)
	    {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)=="")
		       	{	       		 
		       		document.getElementById("winsergoodsno").value=checkNull(responsetext.curGoodsinfo.id.goodsno);
					document.getElementById("winsergoodsname").value=checkNull(responsetext.curGoodsinfo.goodsname);
					document.getElementById("winsergoodsunit").value=checkNull(responsetext.curGoodsinfo.saleunit);
					document.getElementById("wgoodsformat").value=checkNull(responsetext.curGoodsinfo.goodsformat);
					document.getElementById("winsergoodsprice").value=ForDight(checkNull(responsetext.curGoodsinfo.costamtbysale),2);
					document.getElementById("wstandunit").value=checkNull(responsetext.curGoodsinfo.saleunit);
					document.getElementById("wstandprice").value=ForDight(responsetext.curGoodsinfo.standprice,2);
					document.getElementById("wgoodsuniquebar").value=checkNull(responsetext.curGoodsinfo.goodsuniquebar);
					document.getElementById("wcurgoodsstock").value=checkNull(responsetext.curgoodsstock);
					document.getElementById("winsergoodscount").value=0;
					document.getElementById("winsergoodscount").focus();
					document.getElementById("winsergoodscount").select();
					/*if(responsetext.lsDgoodsinsertpc!=null && responsetext.lsDgoodsinsertpc.length>0)
					{
						   			commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsinsertpc,Total: responsetext.lsDgoodsinsertpc.length});
					            	commoninfodivdetial_pcinser.loadData(true);            	
					}
					else
					{
						   			commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial_pcinser.loadData(true);  
					            	addDetialinsertpcRecord();
					}*/
		       	}
		       	else
		       	{
		       		$.ligerDialog.warn(strMessage);
		       		document.getElementById("wfrombarcode").value="";
		       		document.getElementById("winsergoodscount").value=0;
					document.getElementById("winsergoodscount").focus();
					document.getElementById("winsergoodscount").select();
					/*commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
					commoninfodivdetial_pcinser.loadData(true);  
					addDetialinsertpcRecord();*/
		       	}
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
				$.ligerDialog.warn("请确认出库产品编号!");
	        	document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
				obj.value=0;
	        	 return;
			}
			if( ! isNumber(obj.value) )
		    {
				alert( " 输入有误!" );
				obj.value=0;
				obj.focus();
				obj.select();
				return;
			}
			try
			{
				
				if(obj.value*1==0)
				{
					$.ligerDialog.warn("请确认出库数量!");
					return;
				}
				if(obj.value*1>document.getElementById("wcurgoodsstock").value)
				{
					$.ligerDialog.warn("出库数量不能超过现有库存量!");
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
				if(checkNull(commoninfodivdetial.getRow(0).outergoodsname)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}
				curDetialRecord=commoninfodivdetial.getRow(gridlen-1);
			
				commoninfodivdetial.updateRow(curDetialRecord,{outergoodsno: document.getElementById("winsergoodsno").value
															  ,outergoodsname :document.getElementById("winsergoodsname").value
															  ,outerunit  : document.getElementById("winsergoodsunit").value
															  ,outercount : ForDight(document.getElementById("winsergoodscount").value*1,1)
															  ,outerprice : ForDight(document.getElementById("winsergoodsprice").value*1,1)
															  ,outeramt   : ForDight(document.getElementById("winsergoodsprice").value*1*document.getElementById("winsergoodscount").value*1,1)
															  ,frombarcode : document.getElementById("wfrombarcode").value
															  ,tobarcode : document.getElementById("wtobarcode").value
															  ,standunit : document.getElementById("wstandunit").value 
															  ,standprice : document.getElementById("wstandprice").value 
															  ,curgoodsstock : document.getElementById("wcurgoodsstock").value });  
				      
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
				document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
				document.getElementById("wcurgoodsstock").value=0;
	        }
			catch(e)
			{
				alert(e.message);
			}
	    }
	    //检查开始条码
	    function validateFromBarNo(obj)
	    {
	    	if(obj.value=="")
	    	{
	    		document.getElementById("winsergoodscount").value=0;
	    		document.getElementById("wtobarcode").value=0;
	    		return;
	    	}
	    	if(document.getElementById("outerwareid").value=="")
	        {
	        	 $.ligerDialog.warn("请确认出库仓库!");
	        	  obj.value="";
	        	 return;
	        }
	        document.getElementById("wtobarcode").value="";
	        document.getElementById("winsergoodscount").value=0;
	    	var requestUrl ="ic004/validateFromBarNo.action";
        	var params="strCurCompId="+document.getElementById("outercompid").value;
        	params=params+"&strStartBarNo="+obj.value;
        	params=params+"&strWareId="+document.getElementById("outerwareid").value
        	params=params+"&strCurDate="+document.getElementById("outerdate").value;
			var responseMethod="validateFromBarNoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
	    }
	 	function validateFromBarNoMessage(request)
	    {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)=="")
		       	{	       		 
		       		document.getElementById("winsergoodsno").value=checkNull(responsetext.curGoodsinfo.id.goodsno);
					document.getElementById("winsergoodsname").value=checkNull(responsetext.curGoodsinfo.goodsname);
					document.getElementById("winsergoodsunit").value=checkNull(responsetext.curGoodsinfo.saleunit);
					document.getElementById("wgoodsformat").value=checkNull(responsetext.curGoodsinfo.goodsformat);
					document.getElementById("winsergoodsprice").value=ForDight(checkNull(responsetext.curGoodsinfo.costamtbysale),2);
					document.getElementById("wstandunit").value=checkNull(responsetext.curGoodsinfo.saleunit);
					document.getElementById("wstandprice").value=ForDight(responsetext.curGoodsinfo.standprice,2);
					document.getElementById("wgoodsuniquebar").value=checkNull(responsetext.curGoodsinfo.goodsuniquebar);
					document.getElementById("wcurgoodsstock").value=checkNull(responsetext.curgoodsstock);
					document.getElementById("winsergoodscount").value=0;
					/*if(responsetext.lsDgoodsinsertpc!=null && responsetext.lsDgoodsinsertpc.length>0)
					{
						   			commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsinsertpc,Total: responsetext.lsDgoodsinsertpc.length});
					            	commoninfodivdetial_pcinser.loadData(true);            	
					}
					else
					{
						   			commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial_pcinser.loadData(true);  
					            	addDetialinsertpcRecord();
					}*/
		       	}
		       	else
		       	{
		       		$.ligerDialog.warn(strMessage);
		       		document.getElementById("wfrombarcode").value="";
		       		document.getElementById("sendbillid").focus();
					document.getElementById("sendbillid").select();
					/*commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
					commoninfodivdetial_pcinser.loadData(true);  
					addDetialinsertpcRecord();*/
		       	}
	        }
			catch(e)
			{
				alert(e.message);
			}
	   	}
	    //检查结束条码
	    function validateToBarNo(obj)
	    {
	    	if(obj.value=="")
	    	{
	    		document.getElementById("winsergoodscount").value=0;
	    	}
	    	if(document.getElementById("wfrombarcode").value=="" || document.getElementById("winsergoodsno").value=="" )
	    	{
	    			
	    			obj.value="";
	    			document.getElementById("winsergoodscount").value=0;
	    			document.getElementById("wfrombarcode").focus();
					document.getElementById("wfrombarcode").select();
					$.ligerDialog.warn("请先确当次起始条码!");
					return;
	    	}
	    	var requestUrl ="ic004/validateToBarNo.action";
        	var params="strCurCompId="+document.getElementById("outercompid").value;
        	params=params+"&strCurGoodsId="+document.getElementById("winsergoodsno").value;
        	params=params+"&strStartBarNo="+document.getElementById("wfrombarcode").value;
        	params=params+"&strEndBarNo="+obj.value;
        	params=params+"&isOneBarFlag=0";
			var responseMethod="validateToBarNoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
	    }
	    
	    function validateToBarNoMessage(request)
	    {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)=="")
		       	{	       		 
		       		document.getElementById("winsergoodscount").value=responsetext.goodsbarcount;
		       		if(responsetext.goodsbarcount*1>0)
		       		{
		       			var wfrombarcode=document.getElementById("wfrombarcode").value;
		       			var wtobarcode=document.getElementById("wtobarcode").value;
		       			for (var rowid in commoninfodivdetial.records)
						{
						 	var row =commoninfodivdetial.records[rowid];
						 	if(checkNull(row.frombarcode)!="")
						 	{
						 		if((wfrombarcode<=checkNull(row.frombarcode) && wtobarcode>=checkNull(row.tobarcode))
						 		|| (wfrombarcode>=checkNull(row.frombarcode) && wfrombarcode<=checkNull(row.tobarcode))
						 		|| (wtobarcode>=checkNull(row.frombarcode) && wtobarcode<=checkNull(row.tobarcode)))
						 		{
						 			
					    			document.getElementById("winsergoodscount").value=0;
					    			document.getElementById("wtobarcode").value="";
					    			document.getElementById("sendbillid").focus();
									document.getElementById("sendbillid").select();
									$.ligerDialog.warn("该条码号段和出库明细中的重复,请确认!");
									return;
						 		}
						 	}
						 
						}
		       			loadGoodsBarCordMessage();
		       			if(responsetext.lsDgoodsinsertpc.length>0)
						{
							loadOutPc(responsetext.lsDgoodsinsertpc);
						}
		       		}
		       	}
		       	else
		       	{
		       		$.ligerDialog.warn(strMessage);
		       		document.getElementById("wtobarcode").value="";
		       		document.getElementById("winsergoodscount").value=0;
		       		document.getElementById("sendbillid").focus();
					document.getElementById("sendbillid").select();
		       	}
	        }
			catch(e)
			{
				alert(e.message);
			}
	   	}
	   	
	   	
	    function loadOutPc(lsDgoodsinsertpc)
	   	{
	   			var gridlen=commoninfodivdetial_pcouter.rows.length*1;
		        var curDetialbarRecord=null;
		        if(gridlen==0)
				{
					addDetialoutertpcRecord();
					gridlen=gridlen+1;
				} 
				var existsflag=0;
				var boutercount=0;
				for(var i=0;i<lsDgoodsinsertpc.length;i++)
				{
					existsflag=0;
					for (var rowid in commoninfodivdetial_pcouter.records)
					{
						var row =commoninfodivdetial_pcouter.records[rowid];
						if(row.inserbillid==lsDgoodsinsertpc[i].inserbillid
						&& row.inserseqno==lsDgoodsinsertpc[i].inserseqno)	
						{
							boutercount=row.outercount;
							commoninfodivdetial_pcouter.updateRow(row,{outercount : lsDgoodsinsertpc[i].outercount*1+boutercount*1});  
							existsflag=1;
							break;
						}	
					}            
						
					if(existsflag==0)
					{
						if(checkNull(commoninfodivdetial_pcouter.getRow(0).inserbillid)!="")
						{
							addDetialoutertpcRecord();
							gridlen=gridlen+1;
						}
						curDetialbarRecord=commoninfodivdetial_pcouter.getRow(gridlen-1);
						commoninfodivdetial_pcouter.updateRow(curDetialbarRecord,{inserbillid: lsDgoodsinsertpc[i].inserbillid
															  				,outercount : lsDgoodsinsertpc[i].outercount
															  				,inserseqno : lsDgoodsinsertpc[i].inserseqno
															  				,insergoodsno : lsDgoodsinsertpc[i].insergoodsno
															  				,outerprice : ForDight(document.getElementById("winsergoodsprice").value*1,1)});  
					}
					
				}
				
				
			
	   	}
	   	
	   	function changeOutType(obj)
	   	{
	   		if(document.getElementById("outercompid").value!="001" && document.getElementById("useBarFlag").value==1 )
	   		{
	   			obj.checked=true;
	   		}
	   		else
	   		{
	   			if(obj.checked==false)
	   			{
	   				document.getElementById("winsergoodsno").readOnly="";
	   				document.getElementById("winsergoodscount").readOnly="";
	   			}
	   			else
	   			{
	   				document.getElementById("winsergoodsno").readOnly="readOnly";
	   				document.getElementById("winsergoodscount").readOnly="readOnly";
	   			}
	   		}
	   	}
	   	
	   	function changeReviceType()
	   	{
	   			if(curpagestate==3)
		        {
		 			$.ligerDialog.warn("非新增操作,操作无效!");
		    		return ;
		    	}
		    	else
		    	{
		    		document.getElementById("outerstaffid").value="";
		    		document.getElementById("outerstaffname").value="";
		    	}
	   	}
	   	
	function loadGoodsPcinfo(data)
	{
	
		var requestUrl ="ic004/loadGoodsPc.action";
		var params="strCurCompId="+document.getElementById("outercompid").value;
        params=params+"&strCurGoodsId="+data.outergoodsno;
        
        params=params+"&strWareId="+document.getElementById("outerwareid").value;
    
        var responseMethod="loadGoodsPcMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
	}
	 function loadGoodsPcMessage(request)
	 {
		var responsetext = eval("(" + request.responseText + ")");
		if(responsetext.lsDgoodsinsertpc!=null && responsetext.lsDgoodsinsertpc.length>0)
		{
			commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsinsertpc,Total: responsetext.lsDgoodsinsertpc.length});
			commoninfodivdetial_pcinser.loadData(true);            	
		}
		else
		{
			commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
			commoninfodivdetial_pcinser.loadData(true);  
			addDetialinsertpcRecord();
		}
	 }	      
	function itemclick_GoodsPcfo(item)
    {
    	try
    	{
    		var detiallength=commoninfodivdetial_pcouter.rows.length*1;
    		
	    	for (var rowid in commoninfodivdetial_pcinser.records)
			{
				if(commoninfodivdetial_pcinser.records[rowid]['outercount']>0)
				{	
					var row =commoninfodivdetial_pcinser.records[rowid]; 
					if(checkNull(row.inserbillid)=="" || row.insergoodsno=="")
					{
						continue;
					}
					if(detiallength==0)
					{
						addDetialoutertpcRecord();
						detiallength=detiallength+1;
					}
					else if( commoninfodivdetial_pcouter.getRow(0)['inserbillid']!="")
					{
						addDetialoutertpcRecord();
						detiallength=detiallength+1;
					}
					var curPcRecord=commoninfodivdetial_pcouter.getRow(detiallength-1);
					commoninfodivdetial_pcouter.updateRow(curPcRecord,
					{inserbillid: row.inserbillid,insergoodsno :row.insergoodsno,outercount :ForDight(row.outercount,1),inserseqno : row.inserseqno,outerprice:curDetialRecord.outerprice});  
   	 			}
			}
			commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
			commoninfodivdetial_pcinser.loadData(true);  
			addDetialinsertpcRecord();
			
		}catch(e){alert(e.message);}
    }  
    
    
    //复合入库单
    function handconfirmInfo()
    {
    try
    {
        showDialogmanager = $.ligerDialog.waitting('正在复合中,请稍候...');	
		var requestUrl ="ic004/handconfirmInfo.action";
		var responseMethod="handconfirmInfoMessage";	
		if(parent.hasFunctionRights( "IC004",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
	    if(document.getElementById("outerstaffid").value=="")
		{
			$.ligerDialog.error('请输出库员工!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if($("#factoutertype").val()=="")
		{
			$.ligerDialog.error('请选择出库方式!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if(document.getElementById("outerwareid").value=="")
		{
			$.ligerDialog.error('请选择出库仓库!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if(commoninfodivdetial.rows.length*1>0 )
		{
			   commoninfodivdetial.endEdit();
		}
		
		document.getElementById("outertype_h").value=$("#factoutertype").val();
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var params=queryStringTmp;
	
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------出库明细
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.outergoodsno)=="")
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
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}

		curjosnparam="";
        needReplaceStr="";
        strJsonParam_detial="";
        //------出库批次
        for (var rowid in commoninfodivdetial_pcouter.records)
		{
				var row =commoninfodivdetial_pcouter.records[rowid];
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
			 params=params+"&strJsonPcParam=["+strJsonParam_detial+"]";
		}	

		sendRequestForParams_p(requestUrl,responseMethod,params );
		}catch(e){alert(e.message);}
    }
     //取肖复合入库单
    function handcancelInfo()
    {
    	var requestUrl ="ic004/handcancelInfo.action";
        var params="strCurCompId="+document.getElementById("outercompid").value;
        params=params+"&strCurBillId="+document.getElementById("outerbillid").value;
        params=params+"&handtype=2";
		var responseMethod="handcancelInfoMessage";		
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
	        	 curpagestate=3;
	        	 confirmInfo.setDisabled();
				 showDialogmanager.close();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        	showDialogmanager.close();
	        }
	       
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
    	if(document.getElementById("strSearchBillno").value=="" )
		{
			$.ligerDialog.error('请填写查询信息!');
			return;
		}
    	var requestUrl ="ic004/searchOrderBill.action";
        var params="strCurCompId="+document.getElementById("outercompid").value;
        params=params+"&strCurBillId="+document.getElementById("strSearchBillno").value;
		var responseMethod="searchOrderBillMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function searchOrderBillMessage(request)
   	{
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMgoodsouter!=null && responsetext.lsMgoodsouter.length>0)
	   		{
	   			curpagestate=3;
	   			curMasterInfoDate={Rows: responsetext.lsMgoodsouter,Total: responsetext.lsMgoodsouter.length};
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
    
    	function printReport()
		{
			loadprint_show();
		}
		
		 //加载卡出库主明细
    function loadprint_show()
    { 
    	 var params ="strCurCompId="+ document.getElementById("outercompid").value;
    	 	 params =params+"&strSendCurCompId="+ document.getElementById("outerstaffid").value;
        	 params=params+"&strCurBillId="+document.getElementById("outerbillid").value;
     	var requestUrl ="ic004/loadDgoodsouterss.action"; 
		var responseMethod="loadGoodprintshow";				
		sendRequestForParams_p(requestUrl,responseMethod,params);
    }
   
   function loadGoodprintshow(request)
   {
 			var responsetext = eval("(" + request.responseText + ")");
 			lsDgoodsouters=responsetext.lsDgoodsouter;
 			companyinfos=responsetext.companyinfo;
 			var specailurl=contextURL+'/InvoicingControl/IC004/ic004_print.jsp';
		 	window.open(specailurl,"","top=" +2+ ",left=" + 100+",width=800,height=600,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	
    }
    
 
      	 
    	function validateOneBar(obj)
		{
			if(obj.value!="")
			{
				if(document.getElementById("outerwareid").value=="")
		        {
		        	 $.ligerDialog.warn("请确认出库仓库!");
		        	  obj.value="";
		        	 return;
		        }
				var requestUrl ="ic004/validateToBarNo.action";
        		var params="strCurCompId="+document.getElementById("outercompid").value;
        		params=params+"&strCurGoodsId="+document.getElementById("winsergoodsno").value;
        		params=params+"&strStartBarNo="+obj.value;
        		params=params+"&strEndBarNo="+obj.value;
        		params=params+"&isOneBarFlag=1";
        		params=params+"&strWareId="+document.getElementById("outerwareid").value;
        		params=params+"&strCurDate="+document.getElementById("outerdate").value;
				var responseMethod="validateOneBarMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params ); 	
			}
		}
    
     
	    function validateOneBarMessage(request)
	    {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)=="")
		       	{	       		 
		       		if(responsetext.goodsbarcount*1>0)
		       		{
		       			var wfrombarcode=document.getElementById("onebarNo").value;
		       			var wtobarcode=document.getElementById("onebarNo").value;
		       			for (var rowid in commoninfodivdetial.records)
						{
						 	var row =commoninfodivdetial.records[rowid];
						 	if(checkNull(row.frombarcode)!="")
						 	{
						 		if((wfrombarcode<=checkNull(row.frombarcode) && wtobarcode>=checkNull(row.tobarcode))
						 		|| (wfrombarcode>=checkNull(row.frombarcode) && wfrombarcode<=checkNull(row.tobarcode))
						 		|| (wtobarcode>=checkNull(row.frombarcode) && wtobarcode<=checkNull(row.tobarcode)))
						 		{
						 			
					    			document.getElementById("onebarNo").value="";
					    			document.getElementById("onebarNo").focus();
									document.getElementById("onebarNo").select();
									$.ligerDialog.warn("该条码号段和出库明细中的重复,请确认!");
									return;
						 		}
						 	}
						 
						}
		       			loadGoodsOneBarCordMessage(responsetext.curGoodsinfo,responsetext.curgoodsstock);
		       			
		       			if(responsetext.lsDgoodsinsertpc.length>0)
						{
							loadOutPc(responsetext.lsDgoodsinsertpc);
							
						}
						document.getElementById("onebarNo").value="";
					    document.getElementById("onebarNo").focus();
						document.getElementById("onebarNo").select();
		       		}
		       	}
		       	else
		       	{
		       		$.ligerDialog.warn(strMessage);
		       		document.getElementById("onebarNo").value="";
		       		document.getElementById("onebarNo").focus();
					document.getElementById("onebarNo").select();
		       	}
	        }
			catch(e)
			{
				alert(e.message);
			}
	   	}
	   	
	   	
	   	function loadGoodsOneBarCordMessage(curGoodsinfo,curgoodsstock)
	    {
	    		
	       	try
			{
		        var gridlen=commoninfodivdetial.rows.length*1;
				if(gridlen==0)
				{
					addDetialRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial.getRow(0).outergoodsname)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}
				curDetialRecord=commoninfodivdetial.getRow(gridlen-1);
			
				commoninfodivdetial.updateRow(curDetialRecord,{outergoodsno: curGoodsinfo.id.goodsno
															  ,outergoodsname :curGoodsinfo.goodsname
															  ,outerunit  : curGoodsinfo.saleunit
															  ,outercount : 1
															  ,outerprice : ForDight(curGoodsinfo.costamtbysale,1)
															  ,outeramt   : ForDight(curGoodsinfo.costamtbysale,1)
															  ,frombarcode : document.getElementById("onebarNo").value
															  ,tobarcode : document.getElementById("onebarNo").value
															  ,standunit : curGoodsinfo.saleunit
															  ,standprice : ForDight(curGoodsinfo.costamtbysale,1)
															  ,curgoodsstock : curgoodsstock });  
				      
				
	        }
			catch(e)
			{
				alert(e.message);
			}
	    }
	    
	    function selectCall()
	   {
			validateinserno(document.getElementById("winsergoodsno"));
	   }
	   
	    function validatefactoutcount(obj)
	    {
	    	if(obj.value*1>curDetialRecord.curgoodsstock*1)
	    	{
	    		
	    		commoninfodivdetial.updateRow(curDetialRecord,{outercount:0,outeramt: 0});
	    		$.ligerDialog.warn("出库数量不能超过现有库存,请确认!");
				return;
	    	}
	    	commoninfodivdetial.updateRow(curDetialRecord,{outeramt: ForDight(obj.value*1*curDetialRecord.outerprice*1,1)});
		}