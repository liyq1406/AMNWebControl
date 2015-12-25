   	var commoninfodivmaster=null;	//卡入库主档列表
   	var commoninfodivdetial=null;	//卡入库明细列表
   	var strCurCompId="";
   	var strCurBillId="";
   	var rewardmodemanager=null;
   	var pc004layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
   	var checkButton=null;
   	var confirmButton=null;
   	var rewaedchooseData = [{ choose: '0', text: '奖励' }, { choose: '1', text: '处罚'}];
   	//初始化属性
    $(function ()
   	{
	   try
	   {
	   	 	  //布局
            pc004layout= $("#pc004layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false ,isRightCollapse:true});
	   		var height = $(".l-layout-center").height();
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '奖罚单号', 	name: 'bentrybillid',  		width:120,align: 	'left' },
                { display: '类型', 		name: 'entryflag', 			width:50,align: 	'left', 
	                editor: { type: 'select', data: rewaedchooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.entryflag == 1) return '处罚';
	                      return '奖励';
	                }
	            },
	            { display: '奖罚门店', 	name: 'handcompid', 		width:60,align: 	'left'},
	            { display: '奖罚日期', 	name: 'entrydate', 			width:80,align: 	'left'},
	            { display: '员工名称', 	name: 'handstaffname', 		width:100,align: 	'left' }   
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '550',
                height:'690',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: true,usePager: true,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
	                { text: '增加', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '修改', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
					{ text: '删除', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
	                { text: '刷新', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif' }
	                ]
                }	   
            });
             var  rewardmodeData=JSON.parse(parent.loadCommonControlDate("JFLB",0));
           	 rewardmodemanager = $("#entrytype").ligerComboBox({ data: rewardmodeData, isMultiSelect: false,valueFieldID: 'factentrytype',width:'140' });           	 
           	
           	 checkButton=$("#checkButton").ligerButton(
	         {
	             text: '门店审核', width: 140,
		         click: function ()
		         {
		             checkbill();
		         }
	         });
	         
	        confirmButton=$("#confirmButton").ligerButton(
	         {
	             text: '人事专员审核', width: 140,
		         click: function ()
		         {
		             confirmbill();
		         }
	         });
	         $("#searchButton").ligerButton(
	         {
	             text: '查询', width: 100,
		         click: function ()
		         {
		             searchInfo();
		         }
	         });
	         $("#entrydate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
	       	 $("#strSearchDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
          	$("#pageloading").hide(); 
          	loadStaffrewardinfo();
   		}catch(e){alert(e.message);}
    });
    
    //加载卡入库主明细
    function loadStaffrewardinfo()
    {
     	var requestUrl ="pc004/loadStaffrewardinfo.action"; 
		var responseMethod="loadStaffrewardinfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function loadStaffrewardinfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
        	if(responsetext.lsStaffrewardinfo!=null && responsetext.lsStaffrewardinfo.length>0)
	   		{
	   			curStaffrewardDate={Rows: responsetext.lsStaffrewardinfo,Total: responsetext.lsStaffrewardinfo.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curStaffrewardDate);
            	commoninfodivmaster.loadData(true);   
            	commoninfodivmaster.select(0);        	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
			
	   		}catch(e){alert(e.message);}
    }
    
    
     //加载卡入库主明细
    function searchInfo()
    {
    	if(document.getElementById("strSearchBillno").value=="" && document.getElementById("strSearchDate").value=="")
    	{
    		$.ligerDialog.error("请输入查询信息!");
		    return ;
    	}
    	var params="strSearchBillno="+document.getElementById("strSearchBillno").value;
        params=params+"&strSearchDate="+document.getElementById("strSearchDate").value;
     	var requestUrl ="pc004/searchInfo.action"; 
		var responseMethod="searchInfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
   function searchInfoMessage(request)
   {
       		try
        	{
        
 			curpagestate=3;
        	var responsetext = eval("(" + request.responseText + ")");
        	if(responsetext.lsStaffrewardinfo!=null && responsetext.lsStaffrewardinfo.length>0)
	   		{
	   			curStaffrewardDate={Rows: responsetext.lsStaffrewardinfo,Total: responsetext.lsStaffrewardinfo.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curStaffrewardDate);
            	commoninfodivmaster.loadData(true);   
            	commoninfodivmaster.select(0);        	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
			
	   		}catch(e){alert(e.message);}
    }
   
    
    function loadCurMaster(curMaster)
    {
    	try
    	{
    		document.getElementById("entrycompid").value=checkNull(curMaster.id.entrycompid);
	    	document.getElementById("entrybillid").value=checkNull(curMaster.id.entrybillid);
	    	document.getElementById("entrydate").value=checkNull(curMaster.entrydate);
	    	document.getElementById("entryflag").value=checkNull(curMaster.entryflag);
	    	document.getElementById("entrytypehid").value=checkNull(curMaster.entrytype);
	    	rewardmodemanager.selectValue(checkNull(curMaster.entrytype));
	    	document.getElementById("handcompid").value=checkNull(curMaster.handcompid);
	   		document.getElementById("handstaffid").value=checkNull(curMaster.handstaffid);
	    	document.getElementById("handstaffname").value=checkNull(curMaster.handstaffname);
	    	document.getElementById("entryreason").value=checkNull(curMaster.entryreason);
	    	document.getElementById("checkflag").value=checkNull(curMaster.checkflag);
	    	document.getElementById("checkdate").value=checkNull(curMaster.checkdate);
	    	document.getElementById("checkpersonid").value=checkNull(curMaster.checkpersonid);
	    	
	    	document.getElementById("checkrewardstaff").value=checkNull(curMaster.checkrewardstaff);
	    	document.getElementById("checkrewardstaffname").value=checkNull(curMaster.checkrewardstaffname);
	    	document.getElementById("checkrewardremark").value=checkNull(curMaster.checkrewardremark);
	    	document.getElementById("checkrewardamt").value=checkNull(curMaster.checkrewardamt);
	    	
	    	document.getElementById("checkinheadflag").value=checkNull(curMaster.checkinheadflag);
	    	document.getElementById("checkinheaddate").value=checkNull(curMaster.checkinheaddate);
	    	document.getElementById("checkinheadpersonid").value=checkNull(curMaster.checkinheadpersonid);
	    	
			document.getElementById("checkinheadrewardstaff").value=checkNull(curMaster.checkinheadrewardstaff);
	    	document.getElementById("checkinheadrewardstaffname").value=checkNull(curMaster.checkinheadrewardstaffname);
	    	document.getElementById("checkinheadrewardremark").value=checkNull(curMaster.checkinheadrewardremark);
	    	document.getElementById("checkinheadrewardamt").value=checkNull(curMaster.checkinheadrewardamt);
	    	handleRadio("curStaffrewardinfo.billflag",checkNull(curMaster.billflag));
			if(checkNull(curMaster.billflag)==0 && document.getElementById("handstaffid").value=="")
			{
				curpagestate=1;
				pageWriteState();
				
			}
			else if(checkNull(curMaster.billflag)==0 && document.getElementById("handstaffid").value!="")
			{
				curpagestate=3;
				pageReadState(0);
			}
			else if(checkNull(curMaster.billflag)==1)
			{
				curpagestate=3;
				pageReadState(1);
			}
			else
			{
				curpagestate=3;
				pageReadState(2);
			}
    	}
    	catch(e){alert(e.message);}
    	
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	document.getElementById("handstaffid").readOnly="";
	   		rewardmodemanager.setEnabled();
	    	document.getElementById("entryreason").readOnly="";
	    	document.getElementById("checkrewardstaff").readOnly="readOnly";
	    	document.getElementById("checkrewardremark").readOnly="readOnly";
	    	document.getElementById("checkrewardamt").readOnly="readOnly";
	    	document.getElementById("checkinheadrewardstaff").readOnly="readOnly";
			document.getElementById("checkinheadrewardremark").readOnly="readOnly";
			document.getElementById("checkinheadrewardamt").readOnly="readOnly";
			checkButton.setDisabled();
			confirmButton.setDisabled();
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState(type)
    {
    	try
	    {
	    	if(type==0) //门店登记
	    	{
		    	document.getElementById("handstaffid").readOnly="readOnly";
		   		rewardmodemanager.setDisabled();
		    	document.getElementById("entryreason").readOnly="readOnly";
		    	document.getElementById("checkrewardstaff").readOnly="";
		    	document.getElementById("checkrewardremark").readOnly="";
		    	document.getElementById("checkrewardamt").readOnly="";
		    	document.getElementById("checkinheadrewardstaff").readOnly="readOnly";
				document.getElementById("checkinheadrewardremark").readOnly="readOnly";
				document.getElementById("checkinheadrewardamt").readOnly="readOnly";
				checkButton.setEnabled();
				confirmButton.setDisabled();
			}
	    	else if(type==1) //门店审核
	    	{
		    	document.getElementById("handstaffid").readOnly="readOnly";
		   		rewardmodemanager.setDisabled();
		    	document.getElementById("entryreason").readOnly="readOnly";
		    	document.getElementById("checkrewardstaff").readOnly="readOnly";
		    	document.getElementById("checkrewardremark").readOnly="readOnly";
		    	document.getElementById("checkrewardamt").readOnly="readOnly";
		    	document.getElementById("checkinheadrewardstaff").readOnly="";
				document.getElementById("checkinheadrewardremark").readOnly="";
				document.getElementById("checkinheadrewardamt").readOnly="";
				checkButton.setDisabled();
				confirmButton.setEnabled();
			}
			else
			{
				document.getElementById("handstaffid").readOnly="readOnly";
		   		rewardmodemanager.setDisabled();
		    	document.getElementById("entryreason").readOnly="readOnly";
		    	document.getElementById("checkrewardstaff").readOnly="readOnly";
		    	document.getElementById("checkrewardremark").readOnly="readOnly";
		    	document.getElementById("checkrewardamt").readOnly="readOnly";
		    	document.getElementById("checkinheadrewardstaff").readOnly="readOnly";
				document.getElementById("checkinheadrewardremark").readOnly="readOnly";
				document.getElementById("checkinheadrewardamt").readOnly="readOnly";
				checkButton.setDisabled();
				confirmButton.setDisabled();
			}	
	    	
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
						"pc004/loadCurStaffrewardinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {	
								var responsetext = eval( "("+request.responseText+")");		
	   							loadCurMaster(responsetext.curStaffrewardinfo);
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
	    	 $.ligerDialog.confirm('确认提交 员工奖罚 '+item.text+' 操作', function (result)
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
		        	else if(item.text=="修改")
		        	{
		        		 if(curpagestate==3)
		        		 {
		        		 	$.ligerDialog.warn("未修改数据,无需保存!");
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
    	if(parent.hasFunctionRights( "PC004",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有修改权限,请确认!");
       	 	return;
        }
	   if(document.getElementById("handstaffid").value=="")
		{
			$.ligerDialog.error('请确认奖罚员工资料!');
			return;
		}
		 if($("#factentrytype").val()=="")
		{
			$.ligerDialog.error('请确认奖罚类型!');
			return;
		}
		document.getElementById("entrytypehid").value=$("#factentrytype").val();
		document.getElementById("handcompid").value=getCurOrgFromSearchBar();
    	var queryStringTmp=$('#curRewardFrom').serialize();
		var requestUrl ="pc004/post.action";
		var params=queryStringTmp;
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
     		if(parent.hasFunctionRights( "PC004",  "UR_MODIFY")!=true)
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
				                bcinsertcompid: "",
				                bcinsertbillid: "",
				                cinsertdate: "",
				                cinserttime: ""
				             
				            }, row, false);
				var params = "";			
    	     	var requestUrl ="pc004/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	        }
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    		loadCurMaster(responsetext.curStaffrewardinfo);
		   		 
		   	}catch(e){alert(e.message);}
     }
     
    //删除行
    function deleteCurRecord()
    {
       	if(parent.hasFunctionRights( "PC004",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var requestUrl ="pc004/delete.action";
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
  
   //--------------------------验证验货人员-----------------
   function validateHandstaffid(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("handstaffname").value="";
   			return ;
   		}
   		var requestUrl ="pc004/validateInserper.action";
        var params="strCurCompId="+getCurOrgFromSearchBar();
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
	       		document.getElementById("handstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("handstaffid").value="";
	       		document.getElementById("handstaffname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   function validatecheckrewardstaff(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("checkrewardstaffname").value="";
   			return ;
   		}
   		var requestUrl ="pc004/validateInserper.action";
        var params="strCurCompId="+document.getElementById("handcompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validatecheckrewardstaffMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validatecheckrewardstaffMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("checkrewardstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("checkrewardstaff").value="";
	       		document.getElementById("checkrewardstaffname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   function validatecheckinheadrewardstaff(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("checkinheadrewardstaffname").value="";
   			return ;
   		}
   		var requestUrl ="pc004/validateInserper.action";
        var params="strCurCompId="+document.getElementById("handcompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validatecheckinheadrewardstaffMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validatecheckinheadrewardstaffMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("checkinheadrewardstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("checkinheadrewardstaff").value="";
	       		document.getElementById("checkinheadrewardstaffname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
  
   	function checkbill()
   	{
   		if(parent.hasFunctionRights( "PC004",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有修改权限,请确认!");
       	 	return;
        }
   		var requestUrl ="pc004/checkbill.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strCurBillId="+strCurBillId;
        params=params+"&curStaffrewardinfo.checkrewardstaff="+document.getElementById("checkrewardstaff").value;
        params=params+"&curStaffrewardinfo.checkrewardstaffname="+document.getElementById("checkrewardstaffname").value;
        params=params+"&curStaffrewardinfo.checkrewardremark="+document.getElementById("checkrewardremark").value;
         params=params+"&curStaffrewardinfo.checkrewardamt="+document.getElementById("checkrewardamt").value;
        var responseMethod="checkbillMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
   	}
   	
   	function checkbillMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  commoninfodivmaster.deleteSelectedRow();
	       		  $.ligerDialog.success("门店审核成功!");
	       		  checkButton.setDisabled();
				
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
   	
   	function confirmbill()
   	{
   		if(parent.hasFunctionRights( "PC004",  "UR_SPECIAL_CHECK")!=true)
        {
       		$.ligerDialog.warn("该用户没有审核权限,请确认!");
       	 	return;
        }
   		var requestUrl ="pc004/comfirmkbill.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strCurBillId="+strCurBillId;
        params=params+"&curStaffrewardinfo.checkinheadrewardstaff="+document.getElementById("checkinheadrewardstaff").value;
        params=params+"&curStaffrewardinfo.checkinheadrewardstaffname="+document.getElementById("checkinheadrewardstaffname").value;
        params=params+"&curStaffrewardinfo.checkinheadrewardremark="+document.getElementById("checkinheadrewardremark").value;
         params=params+"&curStaffrewardinfo.checkinheadrewardamt="+document.getElementById("checkinheadrewardamt").value;
        var responseMethod="checkbillMessage";		
        sendRequestForParams_p(requestUrl,responseMethod,params ); 	
   	}
   	
   	function checkbillMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  commoninfodivmaster.deleteSelectedRow();
	       		  $.ligerDialog.success("总部审核成功!");
	       		  confirmButton.setDisabled();
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
   
/** 是否都是数字 **/
function isNumber_E(sNum) {
	  var   re   =   /^\d+(?=\.\d+$|$)/   
      return   re.test(sNum);
}

function hotKeyOfSelf(key)
	{
	   if(key==13)//回车
		{
			window.event.keyCode=9; //tab
			window.event.returnValue=true;
   			userempmanager1.selectBox.hide();
   			userempmanager2.selectBox.hide();
		}
		else if( key == 83 &&  event.altKey)
		{
			editCurRecord();
		}
	}