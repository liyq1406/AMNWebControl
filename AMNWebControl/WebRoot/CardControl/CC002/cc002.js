   	var compTreeManager;
   	var compTree;
   	var commoninfodivmaster=null;	//卡入库主档列表
   	var commoninfodivdetial=null;	//卡入库明细列表
   	var commoninfodivdetial_anyls=null;	//未销卡分析
   	var strCurCompId="";
   	var strCurBillId="";
   	var iState=3;
   	var curMasterInfoDate=null;
   	var fromValidate=null;
	var lsCardTypeInfo=new Array();
	var cardTypeChooseDate=showCardTypeDetial();
   	var cc002layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
    $(function ()
   	{
	   try
	   {
	   		  //布局
            cc002layout= $("#cc002layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false,rightWidth: 330 });
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
                { display: '申请门店', 	name: 'bcappcompid',  	width:50,align: 'left' },
                { display: '申请单号', 	name: 'bcappcompbillid', 	width:120,align: 'left'},
	            { display: '申请日期', 	name: 'cappdate', 		width:80,align: 'left'},
	            { display: '申请时间', 	name: 'capptime', 		width:70,align: 'right' },
	            { display: '单据状态', 	name: 'cappbillflagText', 		width:60,align: 'right' }
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '420',
                height:'685',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: true,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
               	 	{ text: '查询', click: itemclick_mcardappInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' },
	                { text: '增加', click: itemclick_mcardappInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_mcardappInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
					{ text: '删除', click: itemclick_mcardappInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
	                ]
                }	   
            });
            commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '会员卡类型', 	name: 'cappcardtypeid',  	width:180,
	            	editor: { type: 'select', data: cardTypeChooseDate, url:'loadAutoCardType',
                	autocomplete: true, valueField: 'choose',selectBoxHeight:'420',labelWidth:200},
	            	render: function (item)
	              	{
	              		for(var i=0;i<lsCardTypeInfo.length;i++)
						{
							if(lsCardTypeInfo[i].id.cardtypeno==item.cappcardtypeid)
							{
								return lsCardTypeInfo[i].cardtypename;
							}
						}
	                    return '';
	                } 
	            },
	            { display: '申请数量', 	name: 'cappcount', 		width:85,align: 'right', editor: { type: 'text' }  }  
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '300',
                height:'462',
                clickToEdit: true,   enabledEdit: true,  
                rownumbers: true, usePager: false,
                toolbar: { items: [
	                { text: '添加申请信息', click: itemclick_dcardappInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }]
                }	   
            });
            
         	commoninfodivdetial_anyls=$("#commoninfodivdetial_anyls").ligerGrid({
                columns: [
                { display: '卡类型', 			name: 'strCardTypeNo',  	width:60,align: 'left' },
                { display: '名称', 			name: 'strCardTypeName', 	width:120,align: 'left'},
	            { display: '剩余数量', 		name: 'NCardtypeCount', 	width:80,align: 'right'}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '300',
                height:'685',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: true,usePager: false
            });
            
             $("#confirmCurRootInfo").ligerButton(
	         {
	             text: '审核申请流程', width: 100,
		         click: function ()
		         {
		             confirmCurRootInfo();
		         }
	         });
	        $.metadata.setType("attr", "validate");
            fromValidate = $("form").validate({
                //调试状态，不会提交数据的
                debug: true,
                errorPlacement: function (lable, element)
                {

                    if (element.hasClass("l-textarea"))
                    {
                        element.addClass("l-textarea-invalid");
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().addClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                    $(element).attr("title", lable.html()).ligerTip();
                },
                success: function (lable)
                {
                    var element = $("#" + lable.attr("for"));
                    if (element.hasClass("l-textarea"))
                    {
                        element.removeClass("l-textarea-invalid");
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().removeClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                },
                submitHandler: function ()
                {
                    alert("Submitted!");
                }
            });
            $("#pageloading").hide(); 
          	f_selectNode();
          	
   		}
   		catch(e){alert(e.message);}
    });
    
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
       		params=params+"&strCurBillId="+checkNull("");
       		params=params+"&iState="+3;
     		var requestUrl ="cc002/loadMcardappInfo.action"; 
			var responseMethod="loadMcardappInfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadMcardappInfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.lsCardtypeinfos!=null && responsetext.lsCardtypeinfos.length>0)
	   		{
	   			lsCardTypeInfo=responsetext.lsCardtypeinfos;
	   		
	   		 	//卡类型选择框
	       		commoninfodivdetial.columns[1].editor.data=showCardTypeDetial();
	        }
	  		if(responsetext.lsMcardapponline!=null && responsetext.lsMcardapponline.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMcardapponline,Total: responsetext.lsMcardapponline.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivmaster.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
			
			if(responsetext.lsDcardapponline!=null && responsetext.lsDcardapponline.length>0)
	   		{
	   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDcardapponline,Total: responsetext.lsDcardapponline.length});
            	commoninfodivdetial.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
            	addDetial();
	   		}
	   		if(responsetext.lsCardTypeAnlisys!=null && responsetext.lsCardTypeAnlisys.length>0)
	   		{
	   			commoninfodivdetial_anyls.options.data=$.extend(true, {},{Rows: responsetext.lsCardTypeAnlisys,Total: responsetext.lsCardTypeAnlisys.length});
            	commoninfodivdetial_anyls.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodivdetial_anyls.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial_anyls.loadData(true);  
            	addDetial_anlys();
	   		}
	   		
	   		loadCurMaster(responsetext.curMcardappInfo);
	   		
	             
	   	}
	   	catch(e){alert(e.message);}
    }
    
    function showCardTypeDetial()
	{
		try
		{
			var strJson = "";//'{ "name": "cxh", "sex": "man" }';
			var ccount=0;
			for( var i=0;i<lsCardTypeInfo.length;i++)
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
					strJson=strJson+'{ "choose":"'+lsCardTypeInfo[i].id.cardtypeno+'", "text": "'+lsCardTypeInfo[i].id.cardtypeno+"_"+lsCardTypeInfo[i].cardtypename+'"}';
				
			}
			if(strJson!="")
			{
				strJson=strJson+']';
				return JSON.parse(strJson);
			}
			else 
			{
				strJson='[{ "choose":"", "text": "请选择"}]';
		
				return JSON.parse(strJson);
			}
			
			return null;
		}catch(e){alert(e.message)}
	}
	
	
	function loadAutoCardType(curmanager,curWriteStaff)
    {
   
    	curmanager.setData(loadGridChooseByCardType(lsCardTypeInfo,curWriteStaff,1));
    	curmanager.selectBox.show();
    }
	//----------------------------加载明细-------------------Start
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		 if(curpagestate!=3)
        		 {
        		 	$.ligerDialog.warn("请保持当前信息或刷新本界面后在进行切换操作!");
        		 	return ;
        		 }
        		 strCurCompId=data.bcappcompid;
        		 strCurBillId=data.bcappcompbillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"cc002/loadDcardappInfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsDcardapponline!=null && responsetext.lsDcardapponline.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDcardapponline,Total: responsetext.lsDcardapponline.length});
					            	commoninfodivdetial.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
						   		}
	   							loadCurMaster(responsetext.curMcardappInfo);
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
    }
    //----------------------------加载明细-------------------End
     //------------------------主档按钮操作-------------------Start
    function itemclick_mcardappInfo(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认提交 '+strCurCompId+' 卡号段入库 '+item.text+' 操作', function (result)
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
		        	else if(item.text=="查询")
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
    	if(parent.hasFunctionRights( "CC002",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       	 	return;
        }
	   if(fromValidate.element($("#cappempid"))==false )
		{
			$.ligerDialog.error('填入信息有误,请确认!');
			return;
		}
    	var queryStringTmp=$('#cardAppForm').serialize();
		var requestUrl ="cc002/post.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
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
	        	 $.ligerDialog.success("更新成功!");
	        	 curpagestate=3;
	        	 pageReadState();
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
     
			 
     //新增行
     function addRecord()
     {
     		if(parent.hasFunctionRights( "CC002",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.error("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        	
				var params = "strCurCompId="+strCurCompId;
    	     	var requestUrl ="cc002/add.action"; 
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
				                bcappcompid: responsetext.curMcardappInfo.bcappcompid,
				                bcappcompbillid: responsetext.curMcardappInfo.bcappcompbillid
				            }, row, false);
				            
	    		loadCurMaster(responsetext.curMcardappInfo);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
            	addDetial();
		   	}catch(e){alert(e.message);}
     }
     
    //删除行
    function deleteCurRecord()
    {
       	if(parent.hasFunctionRights( "CC002",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var requestUrl ="cc002/delete.action";
        var params="strCurCompId="+document.getElementById("cappcompid").value;
        params=params+"&strCurBillId="+document.getElementById("cappcompbillid").value;
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
   	//-------------------------查询-----------------------Start
   	function loadCardInsertList()
   	{
   		var params = "strCurCompId="+document.getElementById("txtSearchCompIdKey").value;				
       	params=params+"&strCurBillId="+document.getElementById("txtSearchBillIdKey").value;	
       	params=params+"&iState="+document.getElementById("txtSearchstateKey").value;	
     	var requestUrl ="cc002/loadMcardappInfo.action"; 
		var responseMethod="loadMcardappInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
   	}
   	//-------------------------查询-----------------------End
   //------------------------主档按钮操作-------------------End
   //------------------------明细按钮操作-------------------Start
   function itemclick_dcardappInfo(item)
   {
   		if(document.getElementById("cappconfirmper").value!="")
	     {
	        		$.ligerDialog.error("该申请单已经保存,不能再次操作");
	        		return;
	     }
   		addDetial();
   }
   
   function addDetial()
   {
   		var row = commoninfodivdetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
		 commoninfodivdetial.addRow({ 
			cappcardtypeid: '',
			cappcount: 0
		  }, row, false);
   }
   function addDetial_anlys()
   {
   		var row = commoninfodivdetial_anyls.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
		 commoninfodivdetial_anyls.addRow({ 
		  }, row, false);
   }
   //------------------------明细按钮操作-------------------End
  // ------------------------界面复杂操作-----------------Start
  	 function loadCurMaster(curMaster)
    {
    	document.getElementById("cappcompid").value=checkNull(curMaster.bcappcompid);
    	document.getElementById("bcappcompidText").value=checkNull(curMaster.bcappcompidText);
    	document.getElementById("cappcompbillid").value=checkNull(curMaster.bcappcompbillid);
    	document.getElementById("cappempid").value=checkNull(curMaster.cappempid);
    	document.getElementById("cappempText").value=checkNull(curMaster.cappempText);
    	document.getElementById("cappdate").value=checkNull(curMaster.cappdate);
    	document.getElementById("capptime").value=checkNull(curMaster.capptime);
    	document.getElementById("cappopationper").value=checkNull(curMaster.cappopationper);
    	document.getElementById("cappopationdate").value=checkNull(curMaster.cappopationdate);
    	document.getElementById("cappconfirmper").value=checkNull(curMaster.cappconfirmper);
    	document.getElementById("cappconfirmdate").value=checkNull(curMaster.cappconfirmdate);
    	handleRadio("curMcardappInfo.cappbillflag",checkNull(curMaster.cappbillflag));
		if(document.getElementById("cappopationper").value=="")
		{
			pageWriteState();
			curpagestate=1;
		}
		else
		{
			pageReadState();
			curpagestate=3;	
		}
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	document.getElementById("cappempid").readOnly="";
			commoninfodivdetial.options.clickToEdit=true;
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("cappempid").readOnly="readOnly";
			commoninfodivdetial.options.clickToEdit=false;
    	}catch(e){alert(e.message);}
    }
    
  // ------------------------界面复杂操作-----------------End
  
//--------------------------验证申请人员-----------------Start
   function validateAppempId(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("cappempText").value="";
   			return ;
   		}
   		var requestUrl ="cc002/validateAppempid.action";
        var params="strCurCompId="+document.getElementById("cappcompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validateAppempidMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validateAppempidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("cappempText").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("cappempid").value="";
	       		document.getElementById("cappempText").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	//--------------------------验证申请人员-----------------End
   	//---------------------------------审核申请单---------------Start
   	function confirmCurRootInfo()
   	{
   		if(parent.hasFunctionRights( "CC002",  "UR_SPECIAL_CHECK")!=true)
        {
        	 $.ligerDialog.warn("该用户没有审核权限,请确认!");
        	 return;
        }
        if(document.getElementById("cappconfirmper").value!="")
        {
        	 $.ligerDialog.warn("该单据已经审核,请确认!");
        	 return;
        }
         $.ligerDialog.confirm('确认审核 '+document.getElementById("cappcompid").value+' 店当前申请单!', function (result)
		 {
		     	if( result==true)
            	{
			        var requestUrl ="cc002/confirm.action";
			        var params="strCurCompId="+document.getElementById("cappcompid").value;
			        params=params+"&strCurBillId="+document.getElementById("cappcompbillid").value;
					var responseMethod="confirmMessage";		
					sendRequestForParams_p(requestUrl,responseMethod,params ); 
				}
		 });	   
   	}
   	
   	function confirmMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  $.ligerDialog.success("审核成功!");
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
   	
   	function hotKeyOfSelf(key)
	{
		if(key==13)//回车
		{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
		}
		else if( key == 83 &&  event.altKey)
		{
				editCurRecord();
		}
	}
   	//---------------------------------审核申请单---------------End