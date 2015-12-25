	var compTreeManager;
   	var compTree;
	var commoninfodivmaster = null;
	var commoninfodivdetial = null;
	var strCurCompId = "";
	var strCurProjecttypeid = "";
	var strCurPackageNo="";
	var bc015layout=null;
	var curMasterInfoDate = null;
	var curpagestate =3;
	var curDetialRecord = null;
	var showDialogmanager=null;
	
    $(function ()
   	{
	   try
	   {
	   		bc015layout= $("#bc015layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false });
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
	   		
          	var isCardDate=[{ choose: '1', text: '是' }, { choose: '2', text: '否'}];
          	
            commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '产品类别', 		name: 'bprojecttypeid', 				width:80	,align: 'left' },
                { display: '类别名称', 		name: 'bprojecttypename', 			width:80	,align: 'left' },
                { display: '是否卡付', 		name: 'iscard',width:80	,align: 'left',editor:{type:'select',data:isCardDate,valueField: 'choose',onChanged:changeIsCard },render: function (item){
                	if(item.iscard==2)
                	{
                		return "否";
                	}
                	else
                	{
                		return "是";
                	}
                } }
	            ],  pageSize:20, 
                data: loadCommonControlDate("WPTJ",0),      
                width: '250',
                height:height-20,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                },
                toolbar: { items: [
               	 	{ text: '保存主档', click: editGood, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
               	 	{ text: '分发主档', click: copyGoodIsCard, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
               	 	{ text: '保存明细', click: itemclick_mpackageInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
               	 	
					]
                },	   
                
                enabledEdit: true,
                clickToEdit: true
            });
            
            
             commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { name: 	'bacounttypeno', hide:true },
                { display: '卡类型编号', 		name: 'bcardtypeno', 				width:100	,align: 'left' },
                { display: '卡类型名称', 		name: 'cardtypename', 				width:140	,align: 'left' },
                { display: '账户类型', 		name: 'bacounttypename', 				width:120	,align: 'left' },
                { display: '开始日期', 		name: 'startdate', type: 'date', editor: { type: 'date'}	,   width:90	,align: 'left' },
                { display: '结束日期', 		name: 'enddate',   type: 'date', editor: { type: 'date'},	width:90	,align: 'left'  },
                { display: '消费折扣', 		name: 'costrate',  				width:60	,align: 'left',editor: { type: 'float' } }
	            ],  pageSize:20, 
                data: null,      
                width: '100%',
                height:height-45,
                enabledEdit: true,  checkbox:false,rownumbers:false,usePager:false
            });
            $("#toptoolbar").ligerToolBar({ items: [
               { text: '账户&nbsp;<select name="accountType" id="accountType" style="width:120"></select>' },
               { text: '加载',click: LoadByAccountType, img: contextURL+'/common/ligerui/ligerUI/skins/icons/communication.gif' },
               { text: '类型:&nbsp;<select name="downPrjType" id="downPrjType" style="width:120"></select>' },
               { text: '下发',click: downLoadByPrjType, img: contextURL+'/common/ligerui/ligerUI/skins/icons/back.gif' },
               { text: '所有类型',click: downLoadAllPrjType, img: contextURL+'/common/ligerui/ligerUI/skins/icons/back.gif' },
      		   { text: '店号:&nbsp;<input type="text"  name="downShopNo" id="downShopNo"  style="width:60;"  />' },
               { text: '下发',click: downLoadByCompNo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/back.gif' } 
                	 	
            ]
            });
          	$("#pageloading").hide(); 
          	var items = parent.gainCommonInfoByCode("WPTJ",0);
			for(var i=0;i<items.length;i++)
			{
				addOption(items[i].bparentcodekey,items[i].bparentcodekey+"."+items[i].parentcodevalue,document.getElementById("downPrjType"));
	  		}
			
	  		addOption("2","储值账户",document.getElementById("accountType"));
	  		addOption("3","积分账户",document.getElementById("accountType"));
	  		addOption("5","收购账户",document.getElementById("accountType"));
	  		addOption("999","现金账户",document.getElementById("accountType"));
            f_selectNode();
   		}
   		catch(e){alert(e.message);}
    });
    
    
    function loadCommonControlDate(code,lvl)
	{
		var returnValue='';
		var items = parent.gainCommonInfoByCode(code,lvl);
		var ccount=0;
		for(var i=0;i<items.length;i++)
		{
		    if(lvl*1==0)
			{
				key = items[i].bparentcodekey;
				value = items[i].parentcodevalue;
			}
			else
			{
				key = items[i].bcodekey;
				value = items[i].codevalue;
			}
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			ccount=ccount*1+1;
			returnValue=returnValue+'{"bprojecttypeid": "'+key+'","bprojecttypename": "'+value+'"}';
		}
		if(returnValue!='')
		{
			returnValue=returnValue+']';
			return {Rows:JSON.parse(returnValue),Total:ccount*1};
		}
		else
		{
			return null;
		}
	}
	
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
    	document.getElementById("downShopNo").value=strCurCompId;
    	commoninfodivmaster.select(0);
    	loadGoodIsCard(strCurCompId);
    }
	
	function loadGoodIsCard(strCurCompId)
	{
		//alert(contextURL+"bc015/loadGooddisInfo.action");
		$.get(contextURL+"/bc015/loadGooddisInfo.action",{"strCurCompId":strCurCompId},function(data){
			commoninfodivmaster.options.data=$.extend(true, {},{Rows: data.lsGood,Total: data.lsGood.length});
			commoninfodivmaster.loadData(true);
		});
	}
   
   
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
       			 strCurProjecttypeid =data.bprojecttypeid;
        		 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurProjecttypeid="+data.bprojecttypeid;
       			 params =params+ "&strCurAccounttypeid=2";
				 var myAjax = new parent.Ajax.Request(
						"bc015/loadCurProjectRateinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");	
								if(action.lsMaster!=null && action.lsMaster.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: action.lsMaster,Total: action.lsMaster.length});
					            	commoninfodivdetial.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivdetial.loadData(true);
					            	addDetialRecord();   
						   		}
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   	}
   	
   	 function LoadByAccountType(data, rowindex, rowobj)
    {
        try{
        		 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurProjecttypeid="+strCurProjecttypeid;
       			 params =params+ "&strCurAccounttypeid="+document.getElementById("accountType").value;
				 var myAjax = new parent.Ajax.Request(
						"bc015/loadCurProjectRateinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");	
								if(action.lsMaster!=null && action.lsMaster.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: action.lsMaster,Total: action.lsMaster.length});
					            	commoninfodivdetial.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivdetial.loadData(true);
					            	addDetialRecord();   
						   		}
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   	}
   	
   	function addDetialRecord()
    {
    		var row = commoninfodivdetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({ });
	}
   	function itemclick_mpackageInfo(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认提交 '+strCurCompId+' 套餐 '+item.text+' 操作', function (result)
		     {
		     	if( result==true)
            	{
		        	if(item.text=="保存明细")
		        	{
		        		 
		        	 	editCurRecord();
		        	} 
		        	
		        }
		      });  
    	}
    	catch(e){alert(e.message);}
    }
   
     
    function editCurRecord()
    {
    	if(parent.hasFunctionRights( "BC015",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       	 	return;
        }
		var requestUrl ="bc015/post.action";
		var params="strCurCompId="+strCurCompId;
		params=params+"&strCurProjecttypeid="+strCurProjecttypeid;
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
    
    function editGood()
    {
    	commoninfodivmaster.endEdit();
    	var requestUrl ="bc015/postGoodIsCard.action";
		var params="strCurCompId="+strCurCompId;
		params=params+"&strCurProjecttypeid="+strCurProjecttypeid;
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivmaster.records)
		{
				var row =commoninfodivmaster.records[rowid];
				curjosnparam=JSON.stringify(row);
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
		alert(params);
		var responseMethod="postGoodMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    
    function postGoodMessage(request)
    {
    	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("更新成功!");
	        	
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
    
    function postMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("更新成功!");
	        	
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

   	

   	
   	
   	function downLoadByPrjType()
   	{
   		if(parent.hasFunctionRights( "BC015",  "UR_POST")!=true)
        {
       		$.ligerDialog.error("该用户没有保存权限,请确认!");
       	 	return;
        }
		var requestUrl ="bc015/downLoadByPrjType.action";
		var params="strCurCompId="+strCurCompId;
		if(document.getElementById("downPrjType").value==strCurProjecttypeid)
		{
			$.ligerDialog.error("需要下发的项目类型与选择类型一致,无需下发,请确认!");
       	 	return;
		}
		params=params+"&strCurProjecttypeid="+strCurProjecttypeid;
		params =params+ "&strDownCurProjecttypeid="+document.getElementById("downPrjType").value;
		params=params+"&strCurAccounttypeid="+document.getElementById("accountType").value;
		$.ligerDialog.confirm('确认下发?', function (result)
		{
		    if( result==true)
           	{
           		var responseMethod="downLoadByPrjTypeMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params );
				showDialogmanager = $.ligerDialog.waitting('正在下发中,请稍候...');	
			}
		}); 
		
   	}
   	
   	
   	
   	function downLoadByPrjTypeMessage(request)
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
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    
    
    function downLoadAllPrjType()
   	{
   		if(parent.hasFunctionRights( "BC015",  "UR_POST")!=true)
        {
       		$.ligerDialog.error("该用户没有保存权限,请确认!");
       	 	return;
        }
		var requestUrl ="bc015/downLoadAllPrjType.action";
		var params="strCurCompId="+strCurCompId;
		params=params+"&strCurProjecttypeid="+strCurProjecttypeid;
		params=params+"&strCurAccounttypeid="+document.getElementById("accountType").value;
		$.ligerDialog.confirm('确认下发到所有的项目类型?', function (result)
		{
		    if( result==true)
           	{
           		var responseMethod="downLoadAllPrjTypeMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params );
				showDialogmanager = $.ligerDialog.waitting('正在下发中,请稍候...');	
			}
		}); 
   	}
   	
   	function downLoadAllPrjTypeMessage(request)
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
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
    }
   	
   	function downLoadByCompNo()
   	{
   		if(parent.hasFunctionRights( "BC015",  "UR_POST")!=true)
        {
       		$.ligerDialog.error("该用户没有保存权限,请确认!");
       	 	return;
        }
		var requestUrl ="bc015/downLoadByCompNo.action";
		var params="strCurCompId="+strCurCompId;
		if(document.getElementById("downShopNo").value==strCurCompId)
		{
			$.ligerDialog.error("需要下发的门店与选择门店一致,无需下发,请确认!");
       	 	return;
		}
		params =params+ "&strDownCurCompId="+document.getElementById("downShopNo").value;
		$.ligerDialog.confirm('确认下发?', function (result)
		{
		    if( result==true)
           	{
           		var responseMethod="downLoadByCompNoMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params );
				showDialogmanager = $.ligerDialog.waitting('正在下发中,请稍候...');	
			}
		}); 
		
   	}
   	
   	function downLoadByCompNoMessage(request)
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
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
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
   	function hotKeyOfSelf(key)
	{
			 if( key == 83 &&  event.altKey)
			{
				editCurRecord();
			}
	
	}
   	
   	function changeIsCard(curRecord)
   	{
   		commoninfodivmaster.updateRow(curRecord,{iscard: curRecord.value});
   	}
   	
   	function copyGoodIsCard()
   	{
   		$.ligerDialog.confirm('确认下发?', function (result)
   		{
   			$.get(contextURL+"/bc015/copyGoodIsCard.action",{"strCurCompId":strCurCompId},function(data){
	   			if(data.strMessage!="")
	   			{
	   				$.ligerDialog.success(data.strMessage);
	   			}
	   			else
	   			{
	   				$.ligerDialog.success("下发成功");
	   			}
			//commoninfodivmaster.options.data=$.extend(true, {},{Rows: data.lsGood,Total: data.lsGood.length});
			//commoninfodivmaster.loadData(true);
   			});
   		});
   	}
   	