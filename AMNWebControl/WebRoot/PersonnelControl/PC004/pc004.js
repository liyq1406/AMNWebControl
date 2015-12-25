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
   	var staffRewardData=JSON.parse(parent.loadCommonControlDate_select("JFLB",0));
   	var statechooseData = [{ choose: 3, text: '已作废' },{ choose: 2, text: '已驳回' },{ choose: 1, text: '已审核' }, { choose: 0, text: '未审核'}];
   	var PC004Tab=null;
   	var commoninfodivdetialtj=null;
   	//初始化属性
    $(function ()
   	{
	   try
	   {
	   	 	  //布局
            pc004layout= $("#pc004layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false ,isRightCollapse:true});
	   		 $("#PC004Tab").ligerTab();
            PC004Tab = $("#PC004Tab").ligerGetTabManager();
	   		var height = $(".l-layout-center").height();
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '奖罚单号', 	name: 'bentrybillid',  			width:120,align: 	'left' },
	            { display: '奖罚门店', 	name: 'handcompid', 			width:60,align: 	'left'} ,
	            { display: '门店名称', 	name: 'handcompname', 		width:110,align: 	'left'}  
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '320',
                height:'660',
                clickToEdit: false,   enabledEdit: false, usePager: true,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }	, 
                toolbar: { items: [
	                { text: '增加', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
					]
                }	   
            });
            $(".l-panel-bbar-inner").css("width", "320");
            commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '处罚员工', 	name: 'handstaffid',  		width:80,align: 	'left' ,
	            	render: function (row) {  
     					var html =row.handstaffid;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.handstaffid+"</font>";  
     					return html;  
 					}  },
                { display: '员工姓名', 	name: 'handstaffname',  	width:100,align: 	'left' ,
	            	render: function (row) {  
     					var html =row.handstaffname;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.handstaffname+"</font>";  
     					return html;  
 					}  },
	            { display: '处罚日期', 	name: 'entrydate', 			width:100,align: 	'left'} ,
	            { display: '处罚类型', 	name: 'entrytype', 			width:100,align: 	'left',
                    editor: { type: 'select', data: staffRewardData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("JFLB",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.entrytype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            } ,
	            { display: '处罚金额', 	name: 'rewardamt', 			width:60,align: 	'left',
	            	render: function (row) {  
     					var html =row.rewardamt;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.rewardamt+"</font>";  
     					return html;  
 					}  
 				} , 
	            { display: '处罚原因', 	name: 'entryreason', 		width:220,align: 	'left'} , 
	            { name: 'handstaffinid',  width:1,hide:true }
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '680',
                height:'540',
                clickToEdit: false,   enabledEdit: false, usePager: false
            });
            
               commoninfodivdetialtj=$("#commoninfodivdetialtj").ligerGrid({
                columns: [
                { display: '处罚门店', 	name: 'handcompid', 			width:60,align: 	'left'} ,
                { display: '门店名称', 	name: 'handcompname', 			width:100,align: 	'left'} ,
                { display: '处罚员工', 	name: 'handstaffid',  		width:80,align: 	'left' ,
	            	render: function (row) {  
     					var html =row.handstaffid;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.handstaffid+"</font>";  
     					return html;  
 					}  
 				},
                { display: '员工姓名', 	name: 'handstaffname',  	width:100,align: 	'left' ,
	            	render: function (row) {  
     					var html =row.handstaffname;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.handstaffname+"</font>";  
     					return html;  
 					}  },
	            { display: '处罚日期', 	name: 'entrydate', 			width:100,align: 	'left'} ,
	            { display: '处罚类型', 	name: 'entrytype', 			width:100,align: 	'left',
                    editor: { type: 'select', data: staffRewardData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("JFLB",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.entrytype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            } ,
	            { display: '处罚金额', 	name: 'rewardamt', 			width:60,align: 	'left',
	            	render: function (row) {  
     					var html =row.rewardamt;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.rewardamt+"</font>";  
     					return html;  
 					}  
 				} , 
	            { display: '处罚原因', 	name: 'entryreason', 		width:220,align: 	'left'} , 
	            { display: '处罚状态', 	name: 'billflag', 		   width:80,align: 	'left', 
	                editor: { type: 'select', data: statechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                   if (item.billflag == 1) return '已审核';
	                   else if (item.billflag == 2) return '已驳回';
	                   else if (item.billflag == 3) return '已作废';
	                    else  return '<font color=\"red\">未审核</font>';
	                }
	            } ,
	            { display: '登记门店', 	name: 'bentrycompid', 			width:60,align: 	'left'}
	            
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'600',
                clickToEdit: false,   enabledEdit: false, usePager: true
            });
            
             var  rewardmodeData=JSON.parse(parent.loadCommonControlDate("JFLB",0));
           	 rewardmodemanager = $("#entrytype").ligerComboBox({ data: rewardmodeData, isMultiSelect: false,valueFieldID: 'factentrytype',width:'140' });           	 
           	
           	 checkButton=$("#checkButton").ligerButton(
	         {
	             text: '经理审核', width: 140,
		         click: function ()
		         {
		             checkbill();
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
	         
	         $("#searchTJButton").ligerButton(
	         {
	             text: '统计查询', width: 100,
		         click: function ()
		         {
		             searchTjInfo();
		         }
	         });
	      
	      	addOption("","",document.getElementById("ientrytype"));
           	var JFLBtypes=parent.gainCommonInfoByCode("JFLB",0);
		    for(var i=0;i<JFLBtypes.length;i++)
			{
				addOption(JFLBtypes[i].bparentcodekey,JFLBtypes[i].parentcodevalue,document.getElementById("ientrytype"));
			}		
            $("#ientrydate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strTJFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            $("#strTJToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
          	$("#pageloading").hide(); 
          	loadStaffrewardinfo();
          	addDTjRecord();
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
    	if(document.getElementById("strSearchBillno").value=="" )
    	{
    		$.ligerDialog.error("请输入查询信息!");
		    return ;
    	}
    	var params="strSearchBillno="+document.getElementById("strSearchBillno").value;
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
   
   
	function getCurOrgFromSearchBarself1()
	{
			var elemId = null;
			var i=2;
			var element;
			var ret="";
		try{	
			elemId = "s1level_"+i;
			element = document.getElementById(elemId);             
			while(typeof(element)!="undefined"&&element != null)
			{
				var seltext = element.options[element.selectedIndex].text;      
				var selvalue = element.options[element.selectedIndex].value;   
			                         
				if(seltext == "*"&&i==2)
				{
					ret = "001";
					return ret;
				}
				else if(seltext == "*"&&i!=2)
				{
					i--;
					tmpElemId = "s1level_"+i;
					tmpElem = document.getElementById(tmpElemId);
					var elemValue = tmpElem.options[tmpElem.selectedIndex].value;
					while(elemValue == "no")
					{
						i--;
						if(i>1)
						{
							tmpElemId = "s1level_"+i;
							tmpElem = document.getElementById(tmpElemId);
							elemValue = tmpElem.options[tmpElem.selectedIndex].value;
						}
						else if(i == 1)
						{
							ret = "001";
							return ret;
						}
						
					}
					ret = elemValue;//tmpElem.options[tmpElem.selectedIndex].value;
				
					return ret;
				}
				i++;                                            
				elemId = "s1level_"+i;                            
				element = document.getElementById(elemId);      
			}
		
			i--;
			tmpElemId = "s1level_"+i;
			tmpElem = document.getElementById(tmpElemId);
			ret = tmpElem.options[tmpElem.selectedIndex].value;
		}catch(Exception)	{alert(Exception.message);}
			return ret;
	}
       //加载卡入库主明细
    function searchTjInfo()
    {
    	var strCurCompId=getCurOrgFromSearchBarself1();
	
    	var strFromDate=document.getElementById("strTJFromDate").value;
    	var strToDate=document.getElementById("strTJToDate").value;
    	if(strFromDate=="" || strToDate=="")
    	{
    		$.ligerDialog.error("请确认查询日期范围!");
		    return ;
    	}
    	var params = "strCurCompId="+strCurCompId;		
    	params=params+"&strTJFromDate="+strFromDate;	
    	params=params+"&strTJToDate="+strToDate;	
     	var requestUrl ="pc004/searchTjInfo.action"; 
		var responseMethod="searchTjInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
   function searchTjInfoMessage(request)
   {
       		try
        	{
        
 			curpagestate=3;
        	var responsetext = eval("(" + request.responseText + ")");
        	if(responsetext.lsDmstaffrewardinfo!=null && responsetext.lsDmstaffrewardinfo.length>0)
			{
				commoninfodivdetialtj.options.data=$.extend(true, {},{Rows: responsetext.lsDmstaffrewardinfo,Total: responsetext.lsDmstaffrewardinfo.length});
				commoninfodivdetialtj.loadData(true);   
					           
			}
			else
			{
				commoninfodivdetialtj.options.data=$.extend(true, {},{Rows: null,Total:0});
				commoninfodivdetialtj.loadData(true);  
				addDTjRecord();
			}
			
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
    	try
    	{
    		
    		document.getElementById("entrycompid").value=checkNull(curMaster.id.entrycompid);
	    	document.getElementById("entrybillid").value=checkNull(curMaster.id.entrybillid);
	    	document.getElementById("entryflag").value=checkNull(curMaster.entryflag);
	    	document.getElementById("billflag").value=checkNull(curMaster.billflag);
	    	document.getElementById("handcompid").value=checkNull(curMaster.handcompid);
	        document.getElementById("ihandstaffid").value="";
	        document.getElementById("ihandstaffname").value="";
	        document.getElementById("ihandstaffinid").value="";
	        document.getElementById("ientrytype").value="";
	        document.getElementById("ientrydate").value="";
	        document.getElementById("irewardamt").value="";
	        document.getElementById("ientryreason").value="";
	        if(document.getElementById("billflag").value==1)
	        {
	       		checkButton.setDisabled();
	        }
	        else
	        {
	        	checkButton.setEnabled();
	        }
			if(curpagestate==1 )
			{
				
				pageWriteState();
				
			}
			else
			{
			
				pageReadState();
			}
    	}
    	catch(e){alert(e.message);}
    	
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	 document.getElementById("ihandstaffid").readOnly="";
	        document.getElementById("ihandstaffname").readOnly="";
	        document.getElementById("ientrydate").readOnly="";
	        document.getElementById("irewardamt").readOnly="";
	        document.getElementById("ientryreason").readOnly="";
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState(type)
    {
    	try
	    {
	    	document.getElementById("ihandstaffid").readOnly="readOnly";
	        document.getElementById("ihandstaffname").readOnly="readOnly";
	        document.getElementById("ientrydate").readOnly="readOnly";
	        document.getElementById("irewardamt").readOnly="readOnly";
	        document.getElementById("ientryreason").readOnly="readOnly";
    	}catch(e){alert(e.message);}
    }
    
    //根据主档选择加载明细
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		
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
	   							
	   							if(responsetext.lsDmstaffrewardinfo!=null && responsetext.lsDmstaffrewardinfo.length>0)
						   		{
									commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDmstaffrewardinfo,Total: responsetext.lsDmstaffrewardinfo.length});
					            	commoninfodivdetial.loadData(true);   
					            	curpagestate=3;	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
					            	addDRecord();
					            	curpagestate=1;	
						   		}
								clearOption("otherStaffNo");
								addOption("","",document.getElementById("otherStaffNo"));
						   		loadCurMaster(responsetext.curStaffrewardinfo);
						   		if(responsetext.lsOldStaffInfo!=null && responsetext.lsOldStaffInfo.length>0)
						   		{	
						   			for(var i=0;i<responsetext.lsOldStaffInfo.length;i++)
									{
										addOption(responsetext.lsOldStaffInfo[i].manageno+"_"+responsetext.lsOldStaffInfo[i].bstaffno+"_"+responsetext.lsOldStaffInfo[i].staffname,responsetext.lsOldStaffInfo[i].bstaffno+"_"+responsetext.lsOldStaffInfo[i].staffname,document.getElementById("otherStaffNo"));
									}	
						   		}
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
		        	else if(item.text=="保存")
		        	{
		        		 if(curpagestate==3)
		        		 {
		        		 	$.ligerDialog.warn("未修改数据,无需保存!");
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
    	if(parent.hasFunctionRights( "PC004",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有修改权限,请确认!");
       	 	return;
        }
        if(curpagestate==3)
		{
		    $.ligerDialog.warn("该单据已经保存,不能再次保存!");
		     return ;
		}
		document.getElementById("handcompid").value=getCurOrgFromSearchBar();
    	var queryStringTmp=$('#curRewardFrom').serialize();
		var requestUrl ="pc004/post.action";
		var params=queryStringTmp;
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.handstaffid)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 strJsonParam_detial=strJsonParam_detial.replace(/\+/g," ");
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
				                bcinsertbillid: ""
				            }, row, false);
				var params = "";			
    	     	var requestUrl ="pc004/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	        }
     }
     function addDRecord()
     {
     		if(parent.hasFunctionRights( "PC004",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        	var row = commoninfodivdetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivdetial.addRow({ 
				                handstaffid: "",
				                handstaffinid: "",
				                entryreason:"",
				                entrydate:"",
				                entrytype:"",
				                rewardamt:""
				            }, row, false);
				       
	        }
     }
     
     function addDTjRecord()
     {
     		
	        	var row = commoninfodivdetialtj.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivdetialtj.addRow({ 
				                handstaffid: "",
				                handstaffinid: "",
				                entryreason:"",
				                entrydate:"",
				                entrytype:"",
				                rewardamt:""
				            }, row, false);
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    		loadCurMaster(responsetext.curStaffrewardinfo);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
				commoninfodivdetial.loadData(true);
				clearOption("otherStaffNo");
				addOption("","",document.getElementById("otherStaffNo"));
				loadCurMaster(responsetext.curStaffrewardinfo);
				if(responsetext.lsOldStaffInfo!=null && responsetext.lsOldStaffInfo.length>0)
				{	
					for(var i=0;i<responsetext.lsOldStaffInfo.length;i++)
					{
						addOption(responsetext.lsOldStaffInfo[i].manageno+"_"+responsetext.lsOldStaffInfo[i].bstaffno+"_"+responsetext.lsOldStaffInfo[i].staffname,responsetext.lsOldStaffInfo[i].bstaffno+"_"+responsetext.lsOldStaffInfo[i].staffname,document.getElementById("otherStaffNo"));
					}	
				}   		  
				addDRecord();
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
   function validateStaffNo(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("ihandstaffname").value="";
   			document.getElementById("ihandstaffinid").value="";
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
	       		document.getElementById("ihandstaffname").value=responsetext.strCurEmpName;
	       		document.getElementById("ihandstaffinid").value=responsetext.strCurEmpInid;
	       	}
	       	else
	       	{
	       		document.getElementById("ihandstaffid").value="";
	       		document.getElementById("ihandstaffinid").value="";
	       		document.getElementById("ihandstaffname").value="";
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
   		/*if(parent.hasFunctionRights( "PC004",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有修改权限,请确认!");
       	 	return;
        }
        $.ligerDialog.confirm('确认该罚款单已保存?', function (result)
		{
		    if( result==true)
           	{
				  $.ligerDialog.prompt('输入经理工号','', function (yes,value) { if(yes) checkmanagerStaffNo(value); });
			
			}
		});  */
      	$.ligerDialog.confirm("请将店长卡插入读卡器中!", function (result)
		{
				if( result==true)
				{
					var CardControl=parent.document.getElementById("CardCtrl");
					CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
					var cardNo=CardControl.ReadCard();
					if(cardNo=="")
					{
						$.ligerDialog.error("请初始化卡号");
						return;
			    	}
					var requestUrl ="pc004/checkmanagerPass.action";
					var responseMethod="checkmanagerPassMessage";	
					var params="mangerCardNo="+cardNo;
					params=params+"&strCurCompId="+strCurCompId;
				    params=params+"&strCurBillId="+strCurBillId;
					sendRequestForParams_p(requestUrl,responseMethod,params ); 
				}
		});
   	}
   	
   	function checkmanagerPassMessage(request)
	{
	    try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  $.ligerDialog.success("门店审核成功!");
	       		  checkButton.setDisabled();
				
	       	}
	       	else
	       	{
	       		$.ligerDialog.error(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
	}  
   	
   	function checkbillMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
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
   	
   	function checkmanagerStaffNo(value)
	{
		  	var staffNo=value;
		  	$.ligerDialog.prompt('输入经理签单密码','', function (yes,value) { if(yes) checkmanagerPass(staffNo,value); });
		
	}
		  
	function checkmanagerPass(staffNo,value)
	{
		  		var requestUrl ="pc004/checkmanagerPass.action";
				var responseMethod="checkmanagerPassMessage";	
				var params="strCurManagerNo="+staffNo;	
				params =params+ "&strCurManagerPass="+value;	
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
				
	}
	/*function checkmanagerPassMessage(request)
	{
	    		
	        	try
				{
					var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	     
		        		var requestUrl ="pc004/checkbill.action";
				        var params="strCurCompId="+strCurCompId;
				        params=params+"&strCurBillId="+strCurBillId;
				        var responseMethod="checkbillMessage";		
						sendRequestForParams_p(requestUrl,responseMethod,params ); 	
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
   	*/
   	function checkbillMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
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

   	
   	function loadGird(obj)
   	{
   		if(obj.value=="" || curpagestate==3)
   		{
   			return;
   		}
   		if(document.getElementById("ihandstaffid").value=="")
   		{
   			$.ligerDialog.error("请确认处罚员工");
   			document.getElementById("ihandstaffid").select();
   			obj.value="";
   			return;
   		}
   		if(document.getElementById("ientrytype").value=="")
   		{
   			$.ligerDialog.error("请确认处罚类型");
   			document.getElementById("ientrytype").select();
   			obj.value="";
   			return;
   		}
   		if(document.getElementById("ientrydate").value=="")
   		{
   			$.ligerDialog.error("请确认处罚日期");
   			document.getElementById("ientrydate").select();
   			obj.value="";
   			return;
   		}
   		if(document.getElementById("irewardamt").value*1==0)
   		{
   			$.ligerDialog.error("请确认处罚金额");
   			document.getElementById("irewardamt").select();
   			obj.value="";
   			return;
   		}
   		
   		if(document.getElementById("ientrydate").value=="")
   		{
   			$.ligerDialog.error("请确认处罚日期");
   			document.getElementById("ientrydate").select();
   			obj.value="";
   			return;
   		}
   		
   		var today = new Date();
		var intYear=today.getYear();
		var intMonth=today.getMonth()+1;
		var intDay=today.getDate();
		var entrydate=document.getElementById("ientrydate").value;
		/*
		if(entrydate.substring(0,4)*1!=intYear*1 )
		{
			$.ligerDialog.error("只能输入本年度的奖罚信息");
   			document.getElementById("ientrydate").select();
   			obj.value="";
   			return;
		}
		
		if(entrydate.substring(5,7)*1!=intMonth*1 && entrydate.substring(5,7)*1+1!=intMonth*1)
		{
			$.ligerDialog.error("只能输入本月和上月奖罚信息");
   			document.getElementById("ientrydate").select();
   			obj.value="";
   			return;
		}
		
		if(entrydate.substring(5,7)*1+1==intMonth*1 && intDay*1>10)
		{
			$.ligerDialog.error("10号以后不能输入上个月的奖罚单!");
   			document.getElementById("ientrydate").select();
   			obj.value="";
   			return;
		}*/
			
   		var gridlen=commoninfodivdetial.rows.length*1;
		if(gridlen==0)
		{
			addDRecord();
			gridlen=gridlen+1;
		} 
		if(checkNull(commoninfodivdetial.getRow(0).handstaffid)!="")
		{
			addDRecord();
			gridlen=gridlen+1;
		}
		try
		{
		var curDetialRecord=commoninfodivdetial.getRow(gridlen-1);
		commoninfodivdetial.updateRow(curDetialRecord,{       handstaffid: document.getElementById("ihandstaffid").value
															  ,handstaffname :document.getElementById("ihandstaffname").value
															  ,handstaffinid  : document.getElementById("ihandstaffinid").value
															  ,entryreason  : document.getElementById("ientryreason").value
															  ,entrydate  : document.getElementById("ientrydate").value
															  ,entrytype  : document.getElementById("ientrytype").value
															  ,rewardamt   : ForDight(document.getElementById("irewardamt").value*1,1)
															   });  
		document.getElementById("ihandstaffid").value="";
		document.getElementById("ihandstaffname").value="";
		document.getElementById("ihandstaffinid").value="";
		document.getElementById("ientryreason").value="";
		document.getElementById("ientrydate").value="";
		document.getElementById("ientrytype").value="";
		document.getElementById("irewardamt").value="";	
		document.getElementById("ihandstaffid").select();
		document.getElementById("ihandstaffid").focus();	 
		}catch(e){alert(e.message);}     
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
		else if(key==115)//F4
		{
				
				 deletedetialRecord();
		}
	}
	
	function deletedetialRecord()
	{
		commoninfodivdetial.deleteSelectedRow();
	}
	
	function validateOtherStaffNo(obj)
	{
		if(obj.value=="")
		{
			document.getElementById("ihandstaffid").value="";
			document.getElementById("ihandstaffname").value="";
			document.getElementById("ihandstaffinid").value="";
		}
		else
		{
			var oldinfo=obj.value.split("_");
			document.getElementById("ihandstaffid").value=oldinfo[1];
			document.getElementById("ihandstaffname").value=oldinfo[2];
			document.getElementById("ihandstaffinid").value=oldinfo[0];
		}
	}