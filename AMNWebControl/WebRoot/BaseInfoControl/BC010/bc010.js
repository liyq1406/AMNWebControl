	var compTreeManager;
   	var compTree;
	var commoninfodivmaster = null;
	var commoninfodivdetial = null;
	var strCurCompId = "";
	var strCurPackageNo="";
	var bc010layout=null;
	var curMasterInfoDate = null;
	var curpagestate =3;
	var curDetialRecord = null;
	var showDialogmanager=null;
    $(function ()
   	{
	   try
	   {
	   		bc010layout= $("#bc010layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false });
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
                { display: '套餐编号', 		name: 'bpackageno', 		width:80	,align: 'left' },
                { display: '套餐名称', 		name: 'packagename', 			width:130	,align: 'left' },
                { display: '套餐价格', 		name: 'packageprice',  	width:60	,align: 'left'}
	            ],  pageSize:20, 
                data: null,      
                width: '320',
                height:height-20,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                },
                toolbar: { items: [
               	 	{ text: '增加', click: itemclick_mpackageInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_mpackageInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
					//{ text: '删除', click: itemclick_mpackageInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
	                ]
                }	   
            });
            
             commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '项目编号', 		name: 'bpackageprono', 			width:100	,align: 'left' ,editor: { type: 'text',onChanged : validateProject} },
                { display: '项目名称', 		name: 'bpackageproname', 			width:130	,align: 'left' },
                { display: '项目单价', 		name: 'packageproprice',  			width:60	,align: 'left',editor: { type: 'int' ,onChanged : validatePrice}},
                { display: '项目数量', 		name: 'packageprocount',  			width:60	,align: 'left',editor: { type: 'int' ,onChanged : validateCount} },
                { display: '项目金额', 		name: 'packageproamt',  			width:60	,align: 'left',editor: { type: 'int' }}
	            ],  pageSize:20, 
                data: null,      
                width: '460',
                height:height-323,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                      curDetialRecord = data;
                },
                 toolbar: { items: [
               	 	{ text: '增加', click: itemclick_bmpackageInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }
	                ]
                }	
            });
            $("#usedate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           
           $("#downByCompid").ligerButton(
	         {
	             text: '下发至该门店及子门店', width: 160,
		         click: function ()
		         {
		             downByCompid();
		         }
	         });
           	var list=parent.gainCommonInfoByCode("TCFW",0);
  			$.each(list, function(i, code){
  				addOption(code.bparentcodekey, code.parentcodevalue, document.getElementById("usetype"));
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
    	strCurCompId=note.data.id;
    	var param="strCurCompId="+strCurCompId;
     	var requestUrl ="bc010/loadMpackageinfos.action"; 
		var responseMethod="loadMpackageinfosMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,param );
    }
   
   function loadMpackageinfosMessage(request)
   {
       	try
        {
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsMpackageinfo!=null && responsetext.lsMpackageinfo.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMpackageinfo,Total: responsetext.lsMpackageinfo.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivmaster.loadData(true);  
            	commoninfodivmaster.select(0);          	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
	 	}
	 	catch(e){alert(e.message);}
    }
    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		 strCurPackageNo=data.bpackageno;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurPackageNo="+strCurPackageNo;
				 var myAjax = new parent.Ajax.Request(
						"bc010/loadCurMpackageinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");	
								document.getElementById("packageno").value=checkNull(action.curMpackageinfo.id.packageno);
								document.getElementById("packagename").value=checkNull(action.curMpackageinfo.packagename);
								document.getElementById("packageprice").value=checkNull(action.curMpackageinfo.packageprice);
								document.getElementById("paceageremark").value=checkNull(action.curMpackageinfo.paceageremark);
								document.getElementById("usemonths").value=checkNull(action.curMpackageinfo.usemonths);
								document.getElementById("usedate").value=checkNull(action.curMpackageinfo.usedate);
								document.getElementById("usetype").value=checkNull(action.curMpackageinfo.usetype);
								document.getElementById("ishz").value=checkNull(action.curMpackageinfo.ishz);
								document.getElementById("ratetype").value=checkNull(action.curMpackageinfo.ratetype);
								document.getElementById("wage").value=checkNull(action.curMpackageinfo.wage);
								if(action.lsDmpackageinfo!=null && action.lsDmpackageinfo.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: action.lsDmpackageinfo,Total: action.lsDmpackageinfo.length});
					            	commoninfodivdetial.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivdetial.loadData(true);   
						   		}
						   		readPage();
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   	}
   	
   	function itemclick_mpackageInfo(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认提交 '+strCurCompId+' 套餐 '+item.text+' 操作', function (result)
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
   
   
     //删除行
    function deleteCurRecord()
    {
       	if(parent.hasFunctionRights( "BC010",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var requestUrl ="bc010/delete.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strCurPackageNo="+strCurPackageNo;
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
   	
   	//新增行
     function addRecord()
     {
     		if(parent.hasFunctionRights( "BC010",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        	curpagestate=1;
				var row = commoninfodivmaster.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				commoninfodivmaster.addRow({ 
				                bpackageno: "",
				                packagename: "",
				                packageprice: ""
				            }, row, false);
				writePage();            
	        }
     }
     
     function editCurRecord()
    {
    	if(parent.hasFunctionRights( "BC010",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有修改权限,请确认!");
       	 	return;
        }
	   if(document.getElementById("packageno").value=="" )
		{
			$.ligerDialog.error('填入信息有误,请确认!');
			return;
		}
    	var queryStringTmp=$('#packageForm').serialize();
		var requestUrl ="bc010/post.action";
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
				}*/	            		   
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		params=params+"&strCurCompId="+strCurCompId;
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
	        	 readPage();
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
    
     function writePage()
     {
     	document.getElementById("packageno").value="";
     	document.getElementById("packageno").readOnly="";
     	document.getElementById("wage").readOnly="";
     	document.getElementById("packagename").value="";
     	document.getElementById("packagename").readOnly="";
     	document.getElementById("packageprice").value="";
     	document.getElementById("packageprice").readOnly="";
     	document.getElementById("paceageremark").value="";
     	document.getElementById("paceageremark").readOnly="";
     	document.getElementById("usemonths").value="";
     	document.getElementById("ishz").value="1";
     	document.getElementById("usemonths").readOnly="";
     	document.getElementById("usedate").value="";
     	document.getElementById("usetype").value=0;
     	document.getElementById("usetype").disabled=false;
     	document.getElementById("ratetype").value=0;
     	document.getElementById("ratetype").disabled=false;
     	commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
		commoninfodivdetial.loadData(true);   
     	commoninfodivdetial.options.clickToEdit=true;
     	commoninfodivdetial.options.enabledEdit=true;
     	
     	addDetialRecord();
     }
     function readPage()
     {

     	document.getElementById("packageno").readOnly="readOnly";
     	document.getElementById("packagename").readOnly="readOnly";
     	document.getElementById("packageprice").readOnly="readOnly";
     	document.getElementById("paceageremark").readOnly="readOnly";
     	document.getElementById("usemonths").readOnly="readOnly";
     	document.getElementById("wage").readOnly="readOnly";
     	document.getElementById("usetype").disabled=true;
     	document.getElementById("ratetype").disabled=true;
     	commoninfodivdetial.options.clickToEdit=false;
     	commoninfodivdetial.options.enabledEdit=false;
     }
     
     function validatePackageno(obj)
     {
     	if(obj.value=="")
     		return;
     	var requestUrl ="bc010/validatePackageno.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strCurPackageNo="+obj.value;
		var responseMethod="validatePackagenoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
     }
   
   	function validatePackagenoMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)!="")
	       	{	       		 
	       		document.getElementById("packageno").value="";
	       		document.getElementById("packageno").select();
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function itemclick_bmpackageInfo(item)
   	{
   		addDetialRecord();
   	}
   	
   	function addDetialRecord()
   	{
   		var row = commoninfodivdetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivdetial.addRow({ 
				                bpackageprono: "",
				                bpackageproname: "",
				                packageproprice: "",
				                packageprocount: "",
				                packageproamt: ""
				            }, row, false);
   	}
   	
   	function validateProject(obj)
   	{
   		if(obj.value=="")
   		{
   			commoninfodivdetial.updateRow(curDetialRecord,{bpackageprono:'',bpackageproname:'',packageproprice:0,
     				packageprocount:0,packageproamt:0}); 
   		}
   		else
   		{
   			var existscount=0;
   			for (var rowid in commoninfodivdetial.records)
			{
				var row =commoninfodivdetial.records[rowid];
				curjosnparam=JSON.stringify(row);
				if(obj.value==row.bpackageprono)
				{
					existscount=existscount*1+1;
				}
			}  
			if(existscount>1)
			{
				commoninfodivdetial.updateRow(curDetialRecord,{bpackageprono:'',bpackageproname:'',packageproprice:0,
     				packageprocount:0,packageproamt:0}); 
				$.ligerDialog.warn("该项目编号已经存在于此套餐中!");
				return ;
			}
   			var requestUrl ="bc010/validateProjectNo.action";
        	var params="strCurCompId="+strCurCompId;
       	 	params=params+"&strProjectNo="+obj.value;
			var responseMethod="validateProjectNoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
   		}
   	}
   	function validateProjectNoMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)!="")
	       	{	       		 
	       		$.ligerDialog.warn(strMessage);
	       		commoninfodivdetial.updateRow(curDetialRecord,{bpackageprono:'',bpackageproname:'',packageproprice:0,
     				packageprocount:0,packageproamt:0}); 
	       	}
	       	else
	       	{
	       		commoninfodivdetial.updateRow(curDetialRecord,{bpackageproname: responsetext.strProjectName,packageproprice:ForDight(responsetext.projectPrice,1),
     				packageprocount:1,packageproamt:ForDight(responsetext.projectPrice,1)}); 
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
    function validateCount(obj)
   	{
   		if(obj.value=="" || obj.value==0)
   		{
   			obj.value=1;
   		}
   		var oneprice=curDetialRecord.packageproprice;

   		commoninfodivdetial.updateRow(curDetialRecord,{packageproamt:ForDight(oneprice*1*obj.value*1,1)}); 
	      
   	}
   	
   	function validatePrice(obj)
	{
		if( ! isNumber(obj.value) )
	    {
			
			obj.value=0;
			obj.focus();
			obj.select();
		}
		var packageprocount=curDetialRecord.packageprocount;
		commoninfodivdetial.updateRow(curDetialRecord,{packageproamt:ForDight(packageprocount*1*obj.value*1,1)}); 
	}

    function	downByCompid()
    {
    	var requestUrl ="bc010/downByCompid.action";
    	var strDownCurCompId=getCurOrgFromSearchBar();
    	var params = "strCurCompId="+strCurCompId;	
    	params = params+"&strDownCurCompId="+strDownCurCompId;	
    	params = params+"&strCurPackageNo="+document.getElementById("packageno").value;	
		
		var responseMethod="downByCompidMessage";
		$.ligerDialog.confirm('确认下发套餐 '+strCurPackageNo+'到 '+strCurCompId+' 门店及其下属门店', function (result)
		{
		     	if( result==true)
            	{
            		showDialogmanager = $.ligerDialog.waitting('正在下发中,请稍候...');	
					sendRequestForParams_p(requestUrl,responseMethod,params );
				}
		});
    }
    function downByCompidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		$.ligerDialog.success("下发成功!");
	       	}
	       	else
	       	{
	       		$.ligerDialog.warn(strMessage);
	       	}
	       	showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
    
    function checkNum(obj)
    {
    	if(isNaN(obj.value))
    	{
    		obj.value="0";
    	}
    }
   	