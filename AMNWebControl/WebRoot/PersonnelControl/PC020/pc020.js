
   	var pc020layout=null;
   	var commoninfodivdetialtj=null;
	var showDialogmanager=null;
	var strEntryCompId="";
	var strEntryBillId="";
	var ibillstate=0;
	var curRecord=null;
	var arrayIdx = [];
	var rewaedchooseData = [{ choose: '0', text: '奖励' }, { choose: '1', text: '处罚'}];
	var rewardchooseData = [{ choose: 1, text: '处罚' }, { choose: 0, text: '奖励'}];
   	var staffRewardData=JSON.parse(parent.loadCommonControlDate_select("ZBJF",0));
   	var statechooseData = [{ choose: 3, text: '已作废' },{ choose: 2, text: '已驳回' },{ choose: 1, text: '已审核' }, { choose: 0, text: '未审核'}];
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc020layout= $("#pc020layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc020layout.centerWidth
       	   	$("#strFromDate").ligerDateEditor({ labelWidth: 100,  labelAlign: 'right',width:'100' });
       	   	$("#strToDate").ligerDateEditor({ labelWidth: 100,  labelAlign: 'right',width:'100' });
          commoninfodivdetialtj=$("#commoninfodivdetialtj").ligerGrid({
                columns: [
                { display: '奖罚门店', 	name: 'handcompid', 			width:80,align: 	'left'} ,
                { display: '奖罚单号',	name: 'bentrybillid', 			width:120,align: 	'left'},
                { display: '门店名称', 	name: 'handcompname', 			width:100,align: 	'left'} ,
                { display: '奖罚类型', 	name: 'entryflag', 			width:60,align: 	'left', 
	                editor: { type: 'select', data: rewardchooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                	var html ="";
	                    if (item.entryflag == 1) 
	                    	html= '处罚';
	                    else
	                      html= '奖励';
	                    if(checkNull(item.rewardamt)*1>100)
     					  html="<font color=\"red\">"+html+"</font>";  
     					return html;
	                }
	            } ,
                { display: '奖罚员工', 	name: 'handstaffid',  		width:80,align: 	'left' ,
	            	render: function (row) {  
     					var html =row.handstaffid;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.handstaffid+"</font>";  
     					return html;  
 					}  
 				},
                { display: '员工姓名', 	name: 'handstaffname',  	width:80,align: 	'left' ,
	            	render: function (row) {  
     					var html =row.handstaffname;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.handstaffname+"</font>";  
     					return html;  
 					}  },
	            { display: '奖罚日期', 	name: 'entrydate', 			width:90,align: 	'left'} ,
	            { display: '奖罚项目', 	name: 'entrytype', 			width:80,align: 	'left',
                    editor: { type: 'select', data: staffRewardData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("ZBJF",0);
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
	            { display: '奖罚金额', 	name: 'rewardamt', 			width:60,align: 	'left',
	            	render: function (row) {  
     					var html =row.rewardamt;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.rewardamt+"</font>";  
     					return html;  
 					}  
 				} , 
	            { display: '奖罚原因', 	name: 'entryreason', 		width:250,align: 	'left'} , 
	            { display: '奖罚状态', 	name: 'billflag', 		   width:70,align: 	'left', 
	                editor: { type: 'select', data: statechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.billflag == 1) return '已审核';
	                    else if (item.billflag == 2) return '已驳回';
	                     else if (item.billflag == 3) return '已作废';
	                    else  return '未审核';
	                }
	            },
	            { display: '操作人员', 	name: 'operationer', 			width:100,align: 	'left'} ,
	            { display: '操作日期', 	name: 'operationdate', 			width:90,align: 	'left'},
	            {  name: 'bentrycompid', 			width:1,hide:true},
	            { 	name: 'handstaffinid', 			width:1,hide:true} ,
	            { 	name: 'bentryseqno', 			width:1,hide:true} 
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'600',
                onSelectRow : function (data, rowindex, rowobj)
                {
                    strEntryCompId=data.bentrycompid;
                    strEntryBillId=data.bentrybillid;
                    entryseqno=data.bentryseqno;
                    ibillstate=data.billflag;
                    curRecord=data;
                },
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow,
                enabledEdit: false,  checkbox: true ,rownumbers: false,usePager: true,
                toolbar: { items: [
	                { text: '人事经理审核', click: comfrimBill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/ok.gif' },
	           		{ text: '人事经理驳回', click: comfrimbackBill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/back.gif' },
	           		{ text: '人事经理作废', click: comfrimstopBill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif' }
					]
                }
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询报表', width: 120,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			document.getElementById("strToDate").value=today;
            $("#pageloading").hide(); 
            addOption("","全部",document.getElementById("strSearchType"));
           	var goodstypes=parent.gainCommonInfoByCode("ZBJF",0);
		    for(var i=0;i<goodstypes.length;i++)
			{
				addOption(goodstypes[i].bparentcodekey,goodstypes[i].parentcodevalue,document.getElementById("strSearchType"));
			}		
            addDTjRecord();
   		}catch(e){alert(e.message);}
    });
    
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
    //登记储值卡
    function loadDataSet()
    {	
    	arrayIdx=[];
    	var strCurCompId=getCurOrgFromSearchBar();
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	if(strFromDate=="" || strToDate=="")
    	{
    		$.ligerDialog.error("请确认查询日期范围!");
		    return ;
    	}
    	var params = "strCurCompId="+strCurCompId;		
    	params=params+"&strFromDate="+strFromDate;	
		params=params+"&strToDate="+strToDate;	
		params=params+"&strSearchType="+document.getElementById("strSearchType").value;
		params=params+"&billstate="+document.getElementById("billstate").value;	
		params=params+"&strSearchStaffNo="+document.getElementById("strSearchStaffNo").value;	 
    	var requestUrl ="pc020/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
   		 function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivdetialtj.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivdetialtj.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到信息!");
		   			commoninfodivdetialtj.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivdetialtj.loadData(true);
	            	addDTjRecord(); 
			}
			commoninfodivdetialtj.sortedData=false;
			showDialogmanager.close();
		}
		
		function comfrimBill()
		{	
			/*if(checkNull(ibillstate)!=0)
			{
				$.ligerDialog.error("该单据已经处理，不能再次处理！");
				return;
			}
			var params = "strCompId="+strEntryCompId;	
	    	params=params+"&strBillId="+strEntryBillId;	
	    	params=params+"&entryseqno="+entryseqno;	
	  
		    var requestUrl ="pc020/comfrimBill.action"; 
			var responseMethod="comfrimBillMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );*/
			var needReplaceStr="";
    		var curjosnparam="";
    		var jsonParam="";
			for (var rowid in commoninfodivdetialtj.records)
			{
				if(findIdx(rowid)!=-1)
				{	
				    var row =commoninfodivdetialtj.records[rowid];
				    if(checkNull(row.billflag)!=0)
				    {
				    	$.ligerDialog.warn(row.bentrybillid+"已被处理,不能再次处理");
       	 				return;
				    }
				    curjosnparam=JSON.stringify(row);
					if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }	            		   
				    if(jsonParam!="")
				      	jsonParam=jsonParam+",";
				    jsonParam= jsonParam+curjosnparam;
				 }            		 
			}
			if(jsonParam!="")
			{
				var params = "strJsonParam=["+jsonParam+"]"
   				showDialogmanager = $.ligerDialog.waitting('正在审核中,请稍候...');	
       	 		var requestUrl ="pc020/comfrimBill.action"; 
				var responseMethod="comfrimBillMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
			else
			{
					$.ligerDialog.warn("请选择需要审核的明细!");
       	 			return;
			}
			
		}
	
		function comfrimBillMessage(request)
		{
				var responsetext = eval("(" + request.responseText + ")");
				if(checkNull(responsetext.strMessage)!="")
	           	{
	            		$.ligerDialog.warn(responsetext.strMessage);
	            }
				else
				{	
			   			$.ligerDialog.success("操作成功");
			   			//commoninfodivTradeDate.updateRow(curRecord,{ billflag:1});
			   			for (var rowid in commoninfodivdetialtj.records)
						{
			   				if(findIdx(rowid)!=-1)
							{
							    var row =commoninfodivdetialtj.records[rowid];
							    commoninfodivdetialtj.updateRow(row,{ billflag:1});
							}            		 
						}
		
				}
				 checkedComps= [];
		    	 checkedBills= [];
		    	 checkedItems= [];
		    	 showDialogmanager.close();
		}
	
		function comfrimbackBill()
		{
			/*if(checkNull(ibillstate)!=0)
			{
				$.ligerDialog.error("该单据已经处理，不能再次处理！");
				return;
			}
			var params = "strCompId="+strEntryCompId;	
	    	params=params+"&strBillId="+strEntryBillId;	
	    	params=params+"&entryseqno="+entryseqno;	
		    var requestUrl ="pc020/comfrimbackBill.action"; 
			var responseMethod="comfrimbackBillMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );*/
			var needReplaceStr="";
    		var curjosnparam="";
    		var jsonParam="";
			for (var rowid in commoninfodivdetialtj.records)
			{
				if(findIdx(rowid)!=-1)
				{	
				    var row =commoninfodivdetialtj.records[rowid];
				    if(checkNull(row.billflag)!=0)
				    {
				    	$.ligerDialog.warn(row.bentrybillid+"已被处理,不能再次处理");
       	 				return;
				    }
				    
				    curjosnparam=JSON.stringify(row);
					if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }	            		   
				    if(jsonParam!="")
				      	jsonParam=jsonParam+",";
				    jsonParam= jsonParam+curjosnparam;
				 }            		 
			}
			if(jsonParam!="")
			{
				var params = "strJsonParam=["+jsonParam+"]"
   				showDialogmanager = $.ligerDialog.waitting('正在驳回中,请稍候...');	
       	 		var requestUrl ="pc020/comfrimbackBill.action"; 
				var responseMethod="comfrimbackBillMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
			else
			{
					$.ligerDialog.warn("请选择需要驳回的明细!");
       	 			return;
			}
			
			
		}
		
		function comfrimbackBillMessage(request)
		{
				var responsetext = eval("(" + request.responseText + ")");
				if(checkNull(responsetext.strMessage)!="")
	           	{
	            		$.ligerDialog.warn(responsetext.strMessage);
	            }
				else
				{	
			   			$.ligerDialog.success("操作成功");
			   		    for (var rowid in commoninfodivdetialtj.records)
						{
							if(findIdx(rowid)!=-1)
							{	
							    var row =commoninfodivdetialtj.records[rowid];
							    commoninfodivdetialtj.updateRow(row,{ billflag:2});
							 }            		 
						}
				}
				checkedComps= [];
		    	checkedBills= [];
		         checkedItems= [];
		         showDialogmanager.close();
		}
		
	
		
		function comfrimstopBill()
		{
			
			var needReplaceStr="";
    		var curjosnparam="";
    		var jsonParam="";
			for (var rowid in commoninfodivdetialtj.records)
			{
				
				if(findIdx(rowid)!=-1)
				{	
				    var row =commoninfodivdetialtj.records[rowid];
				    curjosnparam=JSON.stringify(row);
					if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }	            		   
				    if(jsonParam!="")
				      	jsonParam=jsonParam+",";
				    jsonParam= jsonParam+curjosnparam;
				 }            		 
			}
			if(jsonParam!="")
			{
				var params = "strJsonParam=["+jsonParam+"]"
   				showDialogmanager = $.ligerDialog.waitting('正在作废中,请稍候...');	
       	 		var requestUrl ="pc020/comfrimstopBill.action"; 
				var responseMethod="comfrimstopBillMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
			else
			{
					$.ligerDialog.warn("请选择需要作废的明细!");
       	 			return;
			}
			
			
		}
		
		function comfrimstopBillMessage(request)
		{
				var responsetext = eval("(" + request.responseText + ")");
				if(checkNull(responsetext.strMessage)!="")
	           	{
	            		$.ligerDialog.warn(responsetext.strMessage);
	            }
				else
				{	
			   			$.ligerDialog.success("操作成功");
			   		    for (var rowid in commoninfodivdetialtj.records)
						{
							if(findIdx(rowid)!=-1)
							{	
							    var row =commoninfodivdetialtj.records[rowid];
							    commoninfodivdetialtj.updateRow(row,{ billflag:3});
							 }            		 
						}
				}
				checkedComps= [];
		    	checkedBills= [];
		        checkedItems= [];
		        showDialogmanager.close();
		}
                    
		//获取审核的单据
    	var checkedComps= [];
    	var checkedBills= [];
    	var checkedItems= [];
    	function f_onCheckAllRow(checked)
        {
            for (var rowid in commoninfodivdetialtj.records)
            {
                if(checked)
                {
                	addIdx(rowid);
                    /*addCheckedComps_bills(commoninfodivdetialtj.records[rowid]['bentrycompid'],
                    commoninfodivdetialtj.records[rowid]['bentrybillid'],
                    commoninfodivdetialtj.records[rowid]['bentryseqno']);*/
                }
                else
                {
                	removeIdx(rowid);
                    /*removeCheckedComps_bills(commoninfodivdetialtj.records[rowid]['bentrycompid'],
                    commoninfodivdetialtj.records[rowid]['bentrybillid'],
                    commoninfodivdetialtj.records[rowid]['bentryseqno']);*/
                }
            }
        }
	   	function f_onCheckRow(checked, data,rowid)
        {
        	if (checked) 
        		addIdx(rowid);
            else 
            	removeIdx(rowid);
        }
	   	
	   	function addIdx(rowid)
	   	{
	   		if(findIdx(rowid)==-1)
	   		{
	   			arrayIdx.push(rowid);
	   		}
	   	}
	   	
	   	function removeIdx(rowid)
	   	{
	   		var i=findIdx(rowid);
	   		if(i==-1)
	   		{
	   			return;
	   		}
	   		arrayIdx.splice(i, 	1);
	   	}
	   	
	   	
	   	function findIdx(rowid)
	   	{
	   		for(var i=0;i<arrayIdx.length;i++)
	   		{
	   			if(arrayIdx[i]==rowid)
	   			{
	   				return i;
	   			}
	   		}
	   		return -1;
	   	}
	   
	   	function findCheckedComps(compid)
        {
            for(var i =0;i<checkedComps.length;i++)
            {
                if(checkedComps[i] == compid) return i;
            }
            return -1;
        }
        function findCheckedBills(billid)
        {
            for(var i =0;i<checkedBills.length;i++)
            {
                if(checkedBills[i] == billid) return i;
            }
            return -1;
        }
        function findCheckedItems(item)
        {
            for(var i =0;i<checkedItems.length;i++)
            {
                if(checkedItems[i] == item) return i;
            }
            return -1;
        }
        function addCheckedComps_bills(compid,billid,item)
        {
       
            if(findCheckedComps(compid) == -1)
                checkedComps.push(compid);
            if(findCheckedBills(billid) == -1)
                checkedBills.push(billid);
            if(findCheckedItems(item) == -1)
                checkedItems.push(item);
        }
        function removeCheckedComps_bills(compid,billid,item)
        {
            /*var i = findCheckedComps(compid);
            if(i==-1) 
            	return;
            checkedComps.splice(i,1);
            
            i = findCheckedBills(billid);
            if(i==-1) 
            	return;
            checkedBills.splice(i,1);*/
            
            var i = findCheckedItems(item);
            if(i==-1) 
            	return;
            checkedItems.splice(i,1);
        }