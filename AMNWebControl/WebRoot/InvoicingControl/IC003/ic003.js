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
   	var ic003layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
   	var insertypemanager=null;
	var curDetialRecord = null;

   	var showDialogmanager=null;
    var rightmenu;
    var iGoodsBarCode="";
    var istartBarCode="";
    var iendBarCode="";
    var postState=0;
   	//初始化属性
    $(function ()
   	{
	   try
	   {  
	   	 	  //布局
            ic003layout= $("#ic003layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
	   		var height = $(".l-layout-center").height();
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { 	name: 'binventcompid', hide:true	},
                { display: '盘点单号', 	name: 'binventbillid', 	width:120,align: 'left'},
	            { display: '单店日期', 	name: 'inventdate', 		width:100,align: 'left'}   
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '240',
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
           
        	menu = $.ligerMenu({ width: 120, items:
	            [
	            { text: '打印条形码', click: printBarcode, icon: 'add' }
	            ]
	            }); 
        
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '产品编号', 	name: 'inventgoodsno',  			width:110,align: 'left' },
                { display: '产品名称', 	name: 'inventgoodsname',  			width:150,align: 'left' },
                { display: '单位', 		name: 'inventunit', 				width:70,align: 'left'},
	            { display: '系统库存', 	name: 'curstockcount', 				width:70,align: 'right'},
	            { display: '盘点数量', 	name: 'inventcount', 				width:70,align: 'right'},
	            { display: '差异数量', 	name: 'discount', 					width:70,align: 'right'},
	            { display: '起始编码', 	name: 'inventfrombarno', 				width:100,align: 'left' }  ,   
	            { display: '结束编码', 	name: 'inventtobarno', 					width:100,align: 'left' } ,
	            { display: '生成标识', 	name: 'inventcreateflag', 			width:100,align: 'left' } ,
	            { hide:true, 	name: 'inserunit' },
	            { hide:true, 	name: 'inserprice' },
	            { hide:true, 	name: 'outerunit' },
	            { hide:true, 	name: 'outerprice' }
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '940',
                height:'500',
                clickToEdit: true,   enabledEdit: true,  checkbox:true,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curDetialRecord=data;
                } , onRClickToSelect:true,
                onContextmenu : function (parm,e)
                {
                	 istartBarCode=parm.data.frombarcode;
    				 iendBarCode=parm.data.tobarcode;
    				 iGoodsBarCode=parm.data.insergoodsno;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
                
            });
            
          	$('#thetable').tableScroll({
				width:280,
				height:620
			});
			
			$("#searchButton").ligerButton(
	         {
	             text: '查询单据', width: 80,
		         click: function ()
		         {
		             searchOrderBill();
		         }
	         });
	         
           $("#editInventInfo").ligerButton(
	         {
	             text: '库存调账', width: 160,
		         click: function ()
		         {
		             editInventInfo();
		         }
	         });
          	$("#pageloading").hide(); 
          	addMasterRecord();
          	addDetialRecord();
          	loadCardInsertList();
          	
   		}catch(e){alert(e.message);}
    });
    
    //加载卡入库主明细
    function loadCardInsertList()
    {
     	var requestUrl ="ic003/loadCardInventInfo.action"; 
		var responseMethod="loadCardInventInfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function loadCardInventInfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsMgoodsinventory!=null && responsetext.lsMgoodsinventory.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMgoodsinventory,Total: responsetext.lsMgoodsinventory.length};
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
				
		   		cinsertwaremanager= $("#inventwareid").ligerComboBox({  data: JSON.parse(returnValue)
	                , valueFieldID: 'factcinventwareid',label:'请选仓库',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'
	            }); 
	        }
	        else
	        {
	        	cinsertwaremanager= $("#inventwareid").ligerComboBox({  data: null , valueFieldID: 'factcinventwareid',label:'选择仓库',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220'
	            }); 
	        }
	        
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
		try
		{
	    	document.getElementById("inventcompid").value=checkNull(curMaster.binventcompid);
	    	document.getElementById("inventbillid").value=checkNull(curMaster.binventbillid);
	    	document.getElementById("inventdate").value=checkNull(curMaster.inventdate);
	    	document.getElementById("inventtime").value=checkNull(curMaster.inventtime);
	    	document.getElementById("inventstaffid").value=checkNull(curMaster.inventstaffid);
	    	document.getElementById("binventcompname").value=checkNull(curMaster.binventcompname);
	    	document.getElementById("inventstaffname").value=checkNull(curMaster.inventstaffname);
	    	cinsertwaremanager.selectValue(checkNull(curMaster.inventwareid));
	    	document.getElementById("inventopationerid").value=checkNull(curMaster.inventopationerid);
	    	document.getElementById("inventopationdate").value=checkNull(curMaster.inventopationdate);
	    	document.getElementById("inventinserbillid").value=checkNull(curMaster.inventinserbillid);
	    	document.getElementById("inventouterbillid").value=checkNull(curMaster.inventouterbillid);
	    	document.getElementById("inventflag").value=checkNull(curMaster.inventflag);
	    	document.getElementById("winsergoodsname").value="";
			document.getElementById("winsergoodsunit").value="";
			document.getElementById("winsergoodsprice").value="";
			document.getElementById("winsergoodscount").value="";
			document.getElementById("winsergoodsno").value="";
			document.getElementById("wfrombarcode").value="";
			document.getElementById("wtobarcode").value="";
			document.getElementById("wstandunit").value="";
			document.getElementById("wstandprice").value="";
			document.getElementById("winserunit").value="";
			document.getElementById("winserprice").value="";
			document.getElementById("wouterunit").value="";
			document.getElementById("wouterprice").value="";
			document.getElementById("wbargoodscount").value="";
			document.getElementById("wgoodsuniquebar").value="";
			if(document.getElementById("inventstaffid").value=="")
			{
				pageWriteState();
				curpagestate=1;
			}
			else
			{
				pageReadState();
			}
		}catch(e){alert(e.message);}
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	document.getElementById("inventstaffid").readOnly="";
	 
	   		cinsertwaremanager.setEnabled();
	    	document.getElementById("winsergoodsno").readOnly="";
	    	document.getElementById("winsergoodscount").readOnly="";
	    	document.getElementById("wbargoodscount").readOnly="";
	    	commoninfodivdetial.options.enabledEdit=true;
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("inventstaffid").readOnly="readOnly";
	   		cinsertwaremanager.setDisabled();
	    	document.getElementById("winsergoodsno").readOnly="readOnly";
	    	document.getElementById("winsergoodscount").readOnly="readOnly";
	    	document.getElementById("wbargoodscount").readOnly="readOnly";
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
        		 strCurCompId=data.binventcompid;
        		 strCurBillId=data.binventbillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"ic003/loadDcardnoinvent.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsDgoodsinventory!=null && responsetext.lsDgoodsinventory.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsinventory,Total: responsetext.lsDgoodsinventory.length});
					            	commoninfodivdetial.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
					            	addDetialRecord();
						   		}
	   							loadCurMaster(responsetext.curMgoodsinventory);
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
	    	 $.ligerDialog.confirm('确认提交 '+strCurCompId+' 产品盘点 '+item.text+' 操作', function (result)
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
    	if(parent.hasFunctionRights( "IC003",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       		postState=0;
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
	    if(document.getElementById("inventstaffid").value=="")
		{
			$.ligerDialog.error('请输入盘点员工!');
			postState=0;
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		
		if($("#factcinventwareid").val()=="")
		{
			$.ligerDialog.error('请选择盘点仓库!');
			postState=0;
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		document.getElementById("inventwareid_h").value=$("#factcinventwareid").val();
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic003/post.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.inventgoodsno)=="")
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
	        	 document.getElementById("inventflag").value=1;
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
     		if(parent.hasFunctionRights( "IC003",  "UR_MODIFY")!=true)
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
				                binventcompid: "",
				                binventbillid: "",
				                inventdate: ""
				             
				            }, row, false);
				var params = "";			
    	     	var requestUrl ="ic003/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	        }
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    		loadCurMaster(responsetext.curMgoodsinventory);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
            	addDetialRecord();
		   	}catch(e){alert(e.message);}
     }
     
    //删除行
    function deleteCurRecord()
    {
       	if(parent.hasFunctionRights( "IC003",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var requestUrl ="ic003/delete.action";
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
   		var requestUrl ="ic003/validateInserper.action";
        var params="strCurCompId="+document.getElementById("inventcompid").value;
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
	       		document.getElementById("inventstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("inventstaffname").value="";
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
				      inventgoodsno: "",
				      inventgoodsname: "",
				      inventunit: "",
				      inventcount: "" ,
				      curstockcount: "",
				      discount: "",
				      inventfrombarno: "",
				      inventtobarno: "",
				      inventcreateflag: 0,
				      inserunit:'',
				      inserprice:0,
				      outerunit:'',
				      outerprice:0
				      		
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
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
				
			}
			catch(e){alert(e.message);}
				
		}   
		
		function validateinserno(obj)
		{
			if(document.getElementById("inventstaffid").value=="")
	        {
	        	 $.ligerDialog.warn("请确认入库员工!");
	        	 obj.value="";
	        	 return;
	        }
	        if($("#factcinventwareid").val()=="")
	        {
	        	 $.ligerDialog.warn("请确认入库仓库!");
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
				document.getElementById("winserunit").value="";
				document.getElementById("winserprice").value="";
				document.getElementById("wouterunit").value="";
				document.getElementById("wouterprice").value="";
				document.getElementById("wbargoodscount").value="";
				obj.focus();
				obj.select();
			}
			else
			{
				var requestUrl ="ic003/validateGoodsNo.action";
        		var params="strCurCompId="+document.getElementById("inventcompid").value;
        		params=params+"&strCurGoodsId="+obj.value;
        		params=params+"&strWareId="+$("#factcinventwareid").val();
        		params=params+"&strCurDate="+document.getElementById("inventdate").value;
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
					document.getElementById("winsergoodsprice").value=ForDight(checkNull(responsetext.curGoodsinfo.storesalseprice),2);
					document.getElementById("wstandunit").value=checkNull(responsetext.curGoodsinfo.saleunit);
					document.getElementById("wstandprice").value=ForDight(responsetext.curGoodsinfo.standprice,2);
					document.getElementById("wgoodsuniquebar").value=checkNull(responsetext.curGoodsinfo.goodsuniquebar);
					document.getElementById("wcurgoodsstock").value=checkNull(responsetext.curgoodsstock);
					document.getElementById("winserunit").value=checkNull(responsetext.curGoodsinfo.purchaseunit);
					document.getElementById("winserprice").value=ForDight(checkNull(responsetext.curGoodsinfo.purchaseprice),2);
					document.getElementById("wouterunit").value=checkNull(responsetext.curGoodsinfo.saleunit);
					document.getElementById("wouterprice").value=ForDight(checkNull(responsetext.curGoodsinfo.storesalseprice),2);
					document.getElementById("winsergoodscount").value=0;
					document.getElementById("winsergoodscount").focus();
					document.getElementById("winsergoodscount").select();
					
		       	}
		       	else
		       	{
		       		$.ligerDialog.warn(strMessage);
		       		document.getElementById("winsergoodsno").value="";
		       		document.getElementById("winsergoodscount").value=0;
					document.getElementById("winsergoodscount").focus();
					document.getElementById("winsergoodscount").select();
					
		       	}
	        }
			catch(e)
			{
				alert(e.message);
			}
	   	}
	   	
	   	function loadGoodsCount(obj)
	   	{
	   		if(document.getElementById("winsergoodsno").value=="")
			{
				$.ligerDialog.warn("请确认入库产品编号!");
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
			if($("#factcinventwareid").val()=="03" || $("#factcinventwareid").val()=="05" || $("#factcinventwareid").val()=="06")
			{
				handGirdValue();
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
				
				//document.getElementById("wcurdiscount").value=obj.value*1-document.getElementById("wcurgoodsstock").value*1;
				if(document.getElementById("wbargoodscount").value*1>0)
				{
					if(document.getElementById("wgoodsuniquebar").value=="")
					{
						$.ligerDialog.warn("该产品没有设置条码前缀,不能生成!");
						return;
					}
					var requestUrl ="ic003/loadGoodsBarCord.action";
	        		var params="strCurGoodsId="+document.getElementById("winsergoodsno").value;
	        		params=params+"&barcodeCount="+document.getElementById("wbargoodscount").value;
	        		params=params+"&strIndexBar="+document.getElementById("wgoodsuniquebar").value;
					var responseMethod="loadGoodsBarCordMessage";		
					sendRequestForParams_p(requestUrl,responseMethod,params ); 
				}
				else
				{
					handGirdValue();
				}
				
				
			}catch(e){alert(e.message);}
		}
		
		function loadGoodsBarCordMessage(request)
	    {
	    		
	       	try
			{
		        var responsetext = eval("(" + request.responseText + ")");
		        document.getElementById("wfrombarcode").value= checkNull(responsetext.strStartBarNo);
		        document.getElementById("wtobarcode").value=checkNull(responsetext.strEndBarNo);
		        handGirdValue();
	        }
			catch(e)
			{
				alert(e.message);
			}
	    }
	    
	 
	    
	    function handGirdValue()
	    {
	    	
	    		var gridlen=commoninfodivdetial.rows.length*1;
				if(gridlen==0)
				{
					addDetialRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial.getRow(0).inventgoodsno)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}
					
				curDetialRecord=commoninfodivdetial.getRow(gridlen-1);

				commoninfodivdetial.updateRow(curDetialRecord,{inventgoodsno: document.getElementById("winsergoodsno").value
															  ,inventgoodsname: document.getElementById("winsergoodsname").value
															  ,inventunit  : document.getElementById("winsergoodsunit").value
															  ,inventcount : ForDight(document.getElementById("winsergoodscount").value*1,1)
															  ,curstockcount : ForDight(document.getElementById("wcurgoodsstock").value*1,1)
															  ,discount   : ForDight(document.getElementById("winsergoodscount").value*1-document.getElementById("wcurgoodsstock").value*1,1)
															  ,inventfrombarno : document.getElementById("wfrombarcode").value
															  ,inventtobarno : document.getElementById("wtobarcode").value
															  ,inserunit : document.getElementById("winserunit").value
															  ,inserprice : document.getElementById("winserprice").value
															  ,outerunit : document.getElementById("wouterunit").value
															  ,outerprice : document.getElementById("wouterprice").value});  
				      
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
				document.getElementById("wcurgoodsstock").value=0;
				document.getElementById("winserunit").value="";
				document.getElementById("winserprice").value="";
				document.getElementById("wouterunit").value="";
				document.getElementById("wouterprice").value="";
				document.getElementById("wbargoodscount").value="";
				document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
	    }
    
    function printBarcode(item, i)
    {
    	$.ligerDialog.confirm('确认打印 '+iGoodsBarCode+' 产品的条码 '+istartBarCode+' 至'+iendBarCode, function (result)
		{
			if(result==true)
            {
            	alert(istartBarCode);   
    			alert(iendBarCode); 
    			alert(iGoodsBarCode);
            }
		});
    }
    
    
    function editInventInfo()
    {
    	if(document.getElementById("inventflag").value==0)
    	{
    		$.ligerDialog.warn("请先保存该盘点单，请确认!");
			return ;
    	}
    	if(document.getElementById("inventflag").value==2)
    	{
    		$.ligerDialog.warn("请盘点单已经调帐，请确认!");
			return ;
    	}
    	var requestUrl ="ic003/editInventInfo.action";
	    var params = "strCurCompId="+document.getElementById("inventcompid").value;
	    params =params+ "&strCurBillId="+document.getElementById("inventbillid").value;
	    params =params+ "&strWareId="+$("#factcinventwareid").val();
       	params =params+ "&strCurEmpId="+document.getElementById("inventstaffid").value;;
	    //------卡号段列表
	    var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        var needInventFlag=0;
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.discount)*1!=0)
					needInventFlag=1;
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
		if(needInventFlag==0)    
		{
			$.ligerDialog.warn("该盘点单未生成差异数据，请确认!");
			return ;
		}  
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		var responseMethod="editInventInfoMessage";	
		showDialogmanager = $.ligerDialog.waitting('正在调帐中,请稍候...');		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
    }
    
    function editInventInfoMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("调帐成功!");
	        	 document.getElementById("inventflag").value=2;
	        	 document.getElementById("inventinserbillid").value=checkNull(responsetext.strCurInserBillNo);
	        	 document.getElementById("inventouterbillid").value=checkNull(responsetext.strCurOuterBillNo);
	        	 curpagestate=3;
	        	 pageReadState();
	        }
	        else
	        {
	        	alert(strMessage);
	        }
	        showDialogmanager.close();
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
    	var requestUrl ="ic003/searchOrderBill.action";
        var params="strCurCompId="+ document.getElementById("inventcompid").value;
        params=params+"&strCurBillId="+document.getElementById("strSearchBillno").value;
		var responseMethod="searchOrderBillMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function searchOrderBillMessage(request)
   	{
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMgoodsinventory!=null && responsetext.lsMgoodsinventory.length>0)
	   		{
	   			curpagestate=3;
	   			curMasterInfoDate={Rows: responsetext.lsMgoodsinventory,Total: responsetext.lsMgoodsinventory.length};
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
    