    var bc016layout=null;
   	var commoninfodivTradeDate=null;
   	var commoninfodivRecordDate=null;
	var showDialogmanager=null;
	var strEntryCompId="";
	var strEntryBillId="";
	var ibillstate=0;
	var BC016Tab=null;
	var curRecord=null;
	var subsidychooseData = [{ choose: 1, text: '全部满足' }, { choose: 2, text: '部分满足'}];
	var statechooseData = [{ choose: 3, text: '已作废' },{ choose: 2, text: '已驳回' },{ choose: 1, text: '已审核' }, { choose: 0, text: '未审核'}];
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            bc016layout= $("#bc016layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
            $("#BC016Tab").ligerTab();
            BC016Tab = $("#BC016Tab").ligerGetTabManager();
           	var height = $(".l-layout-center").height();
           	var centerWidth = bc016layout.centerWidth
       	   	$("#strFromDate").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM",  labelAlign: 'right',width:'100' });
       	   //--------销售条码卡
              commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compid', 				width:80,align: 	'left'} ,
	            { display: '门店名称', 		name: 'compname', 				width:120,align: 	'left'}, 
	            { display: '总虚业绩指标', 	name: 'ttotalyeji', 			width:120,align: 	'left'} ,
	            { display: '总实业绩指标', 	name: 'trealtotalyeji', 		width:120,align: 	'left'} ,
	            { display: '美容虚业绩指标', 	name: 'tbeatyyeji', 				width:120,align: 	'left'},
	            { display: '烫染虚业绩指标', 	name: 'ttrhyeji', 				width:120,align: 	'left'},
	            { display: '总客单指标', 		name: 'tcostcount', 			width:120,align: 	'left'},
	            { display: '员工流失数指标', 	name: 'tstaffleavelcount', 				width:120,align: 	'left'},
	            { display: '指标月份', 		name: 'targetmonth', 			width:80,align: 	'left'},
				{ display: '单据状态', 		name: 'targetflag', 		  	 		width:70,align: 	'left', 
	                editor: { type: 'select', data: statechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.targetflag == 1) return '已审核';
	                    else if (item.targetflag == 2) return '已驳回';
	                    else if (item.targetflag == 3) return '已作废';
	                      return '未审核';
	                }
	            },
	            { name: 'entrycompid', width:1,hide:true} ,
	            { name: 'entrybillid', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'92%',
                onSelectRow : function (data, rowindex, rowobj)
                {
                    strEntryCompId=data.entrycompid;
                    strEntryBillId=data.entrybillid;
                    ibillstate=data.targetflag;
                    curRecord=data;
                },
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow,
                enabledEdit: false,  checkbox: true ,rownumbers: false,usePager: true,
                toolbar: { items: [
	                { text: '经理审核', click: comfrimBill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/ok.gif' },
	           		{ text: '经理驳回', click: comfrimbackBill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/back.gif' },
	           		{ text: '经理作废', click: comfrimstopBill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif' }
					]
                }	  
            });
            
             commoninfodivRecordDate=$("#commoninfodivRecordDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compid', 					width:80,align: 	'left'} ,
	            { display: '门店名称', 		name: 'compname', 					width:120,align: 	'left'}, 
	            { display: '总虚业绩指标', 	name: 'ttotalyeji', 				width:120,align: 	'left'} ,
	            { display: '总实业绩指标', 	name: 'trealtotalyeji', 			width:120,align: 	'left'} ,
	            { display: '美容虚业绩指标', 	name: 'tbeatyyeji', 				width:120,align: 	'left'},
	            { display: '烫染虚业绩指标', 	name: 'ttrhyeji', 					width:120,align: 	'left'},
	            { display: '总客单指标', 		name: 'tcostcount', 				width:120,align: 	'left'},
	            { display: '员工流失数指标', 	name: 'tstaffleavelcount', 			width:120,align: 	'left'},
	            { display: '指标月份', 		name: 'targetmonth', 				width:80,align: 	'left'},
	            { name: 'entrycompid', width:1,hide:true} ,
	            { name: 'entrybillid', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'92%',
                onSelectRow : function (data, rowindex, rowobj)
                {
                    strEntryCompId=data.entrycompid;
                    strEntryBillId=data.entrybillid;
                    ibillstate=data.targetflag;
                    curRecord=data;
                },
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow,
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false
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
	         
	         $("#checkTargetButton").ligerButton(
	         {
	             text: '添加', width: 70,
		         click: function ()
		         {
		             checkTarget();
		         }
	         });
	           $("#postTargetButton").ligerButton(
	         {
	             text: '设置门店指标', width: 100,
		         click: function ()
		         {
		             postTargetInfo();
		         }
	         });
	         $("#excelButton").ligerButton(
	         {
	             text: '转Excel', width: 120,
		         click: function ()
		         {
		             loadDataSetExcel();
		         }
	         });
	          $("#targetmonth").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });

            $("#pageloading").hide(); 
            loadStaffTargetinfo();
   		}catch(e){alert(e.message);}
    });
    
    
     //加载卡入库主明细
    function loadStaffTargetinfo()
    {
     	var requestUrl ="bc016/loadCurData.action"; 
		var responseMethod="loadCurDataMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
    function loadCurDataMessage(request)
   	{
   		addtrade();
   		addrtrade();
   		var responsetext = eval("(" + request.responseText + ")");
   		document.getElementById("entrybillid").value=responsetext.curStoretargetinfo.entrybillid;
	   	document.getElementById("compid").value="";
	   	document.getElementById("compname").value="";
	   	document.getElementById("targetmonth").value="";
	   	document.getElementById("ttotalyeji").value="";
	   	document.getElementById("trealtotalyeji").value="";
	   	document.getElementById("tbeatyyeji").value="";
	   	document.getElementById("ttrhyeji").value="";
	   	document.getElementById("tstaffleavelcount").value="";
	   	document.getElementById("tcostcount").value="";
	   	var today = new Date();
		var intYear=today.getYear();
		var intMonth=today.getMonth()+1;
		var intDay=today.getDate();
		var today = intYear.toString()+"-"+fullStr(intMonth.toString());
		document.getElementById("targetmonth").value=today;
   	}
   	 
    function  addtrade()
    {
        	var row = commoninfodivTradeDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivTradeDate.addRow({ 
				                
				            }, row, false);
     }
     
    function  addrtrade()
    {
        	var row = commoninfodivRecordDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivRecordDate.addRow({ 
				                compid: "",
				                compname: "",
				                ttotalyeji: 0,
				             	trealtotalyeji:0,
				             	tbeatyyeji:0,
				             	ttrhyeji:0,
				             	tcostcount:0,
				             	tstaffleavelcount:0,
				             	targetmonth:"",
				             	entrycompid:"",
				             	entrybillid:""
				            }, row, false);
     }
     

    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();

    	var strFromDate=document.getElementById("strFromDate").value;
    	
    	var params = "strCurCompId="+strCurCompId;		
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&billstate="+document.getElementById("billstate").value;		
    	var requestUrl ="bc016/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
   		 function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivTradeDate.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			commoninfodivTradeDate.sortedData=false;
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
	    var requestUrl ="bc016/comfrimBill.action"; 
		var responseMethod="comfrimBillMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );*/
		var needReplaceStr="";
    	var curjosnparam="";
    	var jsonParam="";
		for (var rowid in commoninfodivTradeDate.records)
		{
				if(findCheckedComps(commoninfodivTradeDate.records[rowid]['entrycompid'])!=-1
				&& findCheckedBills(commoninfodivTradeDate.records[rowid]['entrybillid'])!=-1)
				{	
				    var row =commoninfodivTradeDate.records[rowid];
				    if(checkNull(row.targetflag)!=0)
				    {
				    	$.ligerDialog.warn(row.entrybillid+"已被处理,不能再次处理");
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
       	 		var requestUrl ="bc016/comfrimBill.action"; 
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
		   			//commoninfodivTradeDate.updateRow(curRecord,{ targetflag:1});
		   			for (var rowid in commoninfodivTradeDate.records)
					{
							if(findCheckedComps(commoninfodivTradeDate.records[rowid]['entrycompid'])!=-1
							&& findCheckedBills(commoninfodivTradeDate.records[rowid]['entrybillid'])!=-1)
							{	
							    var row =commoninfodivTradeDate.records[rowid];
							    commoninfodivTradeDate.updateRow(row,{ targetflag:1});
							 }            		 
					}
			}
		    checkedComps= [];
		    checkedBills= [];
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
	    var requestUrl ="bc016/comfrimbackBill.action"; 
		var responseMethod="comfrimbackBillMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );*/
		var needReplaceStr="";
    	var curjosnparam="";
    	var jsonParam="";
		for (var rowid in commoninfodivTradeDate.records)
		{
				if(findCheckedComps(commoninfodivTradeDate.records[rowid]['entrycompid'])!=-1
				&& findCheckedBills(commoninfodivTradeDate.records[rowid]['entrybillid'])!=-1)
				{	
				    var row =commoninfodivTradeDate.records[rowid];
				    if(checkNull(row.targetflag)!=0)
				    {
				    	$.ligerDialog.warn(row.entrybillid+"已被处理,不能再次处理");
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
       	 		var requestUrl ="bc016/comfrimbackBill.action"; 
				var responseMethod="comfrimbackBillMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		else
		{
				$.ligerDialog.warn("请选择需要审核的明细!");
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
		   		    //commoninfodivTradeDate.updateRow(curRecord,{ targetflag:2});
		   		    for (var rowid in commoninfodivTradeDate.records)
					{
							if(findCheckedComps(commoninfodivTradeDate.records[rowid]['entrycompid'])!=-1
							&& findCheckedBills(commoninfodivTradeDate.records[rowid]['entrybillid'])!=-1)
							{	
							    var row =commoninfodivTradeDate.records[rowid];
							    commoninfodivTradeDate.updateRow(row,{ targetflag:2});
							 }            		 
					}
			}
			checkedComps= [];
		    checkedBills= [];
			showDialogmanager.close();
	}
	
	
	
	
	function comfrimstopBill()
	{
		
		
		var needReplaceStr="";
    	var curjosnparam="";
    	var jsonParam="";
		for (var rowid in commoninfodivTradeDate.records)
		{
				if(findCheckedComps(commoninfodivTradeDate.records[rowid]['entrycompid'])!=-1
				&& findCheckedBills(commoninfodivTradeDate.records[rowid]['entrybillid'])!=-1)
				{	
				    var row =commoninfodivTradeDate.records[rowid];
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
       	 		var requestUrl ="bc016/comfrimstopBill.action"; 
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
		   		    //commoninfodivTradeDate.updateRow(curRecord,{ targetflag:2});
		   		    for (var rowid in commoninfodivTradeDate.records)
					{
							if(findCheckedComps(commoninfodivTradeDate.records[rowid]['entrycompid'])!=-1
							&& findCheckedBills(commoninfodivTradeDate.records[rowid]['entrybillid'])!=-1)
							{	
							    var row =commoninfodivTradeDate.records[rowid];
							    commoninfodivTradeDate.updateRow(row,{ targetflag:3});
							 }            		 
					}
			}
			checkedComps= [];
		    checkedBills= [];
			showDialogmanager.close();
	}
	
	
	
	//获取审核的单据
    	var checkedComps= [];
    	var checkedBills= [];
    	function f_onCheckAllRow(checked)
        {
            for (var rowid in commoninfodivTradeDate.records)
            {
                if(checked)
                {
                    addCheckedComps_bills(commoninfodivTradeDate.records[rowid]['entrycompid'],
                    commoninfodivTradeDate.records[rowid]['entrybillid']);
                }
                else
                {
                    removeCheckedComps_bills(commoninfodivTradeDate.records[rowid]['entrycompid'],
                    commoninfodivTradeDate.records[rowid]['entrybillid']);
                }
            }
        }
	   	function f_onCheckRow(checked, data)
        {
        	if (checked) 
            	addCheckedComps_bills(data.entrycompid,data.entrybillid);
            else 
            	removeCheckedComps_bills(data.entrycompid,data.entrybillid);
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
       
        function addCheckedComps_bills(compid,billid)
        {
       
            if(findCheckedComps(compid) == -1)
                checkedComps.push(compid);
            if(findCheckedBills(billid) == -1)
                checkedBills.push(billid);
        }
        function removeCheckedComps_bills(compid,billid)
        {
            i = findCheckedBills(billid);
            if(i==-1) 
            	return;
            checkedBills.splice(i,1);
        }
        
        
	    function validateShandcompid(obj)
	   	{
	   		if(obj.value=="")
	   		{
	   			document.getElementById("compname").value="";
	   		}
	   		else
	   		{
	   			var requestUrl ="bc016/validateShandcompid.action";
	       	 	var params="strCurCompId="+obj.value;
				var responseMethod="validateShandcompidMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 	 
	   		}
	   	}
	   	
	   	function validateShandcompidMessage(request)
	    {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)=="")
		       	{	       		 
		       		document.getElementById("compname").value=responsetext.strCurCompName;
		       	}
		       	else
		       	{
		       		
		       		document.getElementById("compid").value="";
		       		document.getElementById("compname").value="";
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
		commoninfodivRecordDate.deleteSelectedRow();
	}
		
		function checkTarget()
		{
			if(document.getElementById("compid").value=="")
	   		{
	   			$.ligerDialog.error("请确认登记门店");
	   			document.getElementById("compid").select();
	   			return;
	   		}
	   	
	   		else if(document.getElementById("targetmonth").value=="" )
	   		{
	   			$.ligerDialog.error("请确认指标月份");
	   			document.getElementById("targetmonth").select();
	   			return;
	   		}
	   		
	   		var gridlen=commoninfodivRecordDate.rows.length*1;
			if(gridlen==0)
			{
				addrtrade();
				gridlen=gridlen+1;
			} 
			if(checkNull(commoninfodivRecordDate.getRow(0).targetmonth)!="")
			{
				addrtrade();
				gridlen=gridlen+1;
			}
			var curDetialRecord=commoninfodivRecordDate.getRow(gridlen-1);
			commoninfodivRecordDate.updateRow(curDetialRecord,{      entrybillid: document.getElementById("entrybillid").value
															  ,compid :document.getElementById("compid").value
															   ,compname :document.getElementById("compname").value
															  ,targetmonth  : document.getElementById("targetmonth").value
															  ,ttotalyeji  : document.getElementById("ttotalyeji").value
															  ,trealtotalyeji  : document.getElementById("trealtotalyeji").value
															  ,tbeatyyeji  : document.getElementById("tbeatyyeji").value
															  ,ttrhyeji  : document.getElementById("ttrhyeji").value
															  ,tcostcount  : document.getElementById("tcostcount").value
															  ,tstaffleavelcount   : checkNull(document.getElementById("tstaffleavelcount").value)*1
															}); 
															
		  	document.getElementById("compid").value="";
	   		document.getElementById("compname").value="";
	   		document.getElementById("targetmonth").value="";
	   		document.getElementById("ttotalyeji").value="";
	   		document.getElementById("trealtotalyeji").value="";
	   		document.getElementById("tbeatyyeji").value="";
	   		document.getElementById("ttrhyeji").value="";
	   		document.getElementById("tstaffleavelcount").value="";
	   		document.getElementById("tcostcount").value="";
	   		var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString());
			document.getElementById("targetmonth").value=today;
			var startBill=document.getElementById("entrybillid").value.substring(0,document.getElementById("entrybillid").value.length-4);
	        var endBill=document.getElementById("entrybillid").value.substring(document.getElementById("entrybillid").value.length-4,document.getElementById("entrybillid").value.length);
	   
			document.getElementById("entrybillid").value=startBill+(endBill*1+1);
		}
		
		
		
		function postTargetInfo()
	    {
	   		try
			{
		    	if(parent.hasFunctionRights( "BC016",  "UR_POST")!=true)
		        {
		       		$.ligerDialog.warn("该用户没有修改权限,请确认!");
		       	 	return;
		        }
				var requestUrl ="bc016/postTargetInfo.action";
				var params="";
				var curjosnparam="";
		        var needReplaceStr="";
		        var strJsonParam_detial="";
		        //------卡号段列表
		        for (var rowid in commoninfodivRecordDate.records)
				{
						var row =commoninfodivRecordDate.records[rowid];
						if(checkNull(row.entrybillid)=="")
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
					 params=params+"strJsonParam=["+strJsonParam_detial+"]";
				}
				var responseMethod="postTargetInfoMessage";
				if(params=="")
					return ;
			    sendRequestForParams_p(requestUrl,responseMethod,params );
		    }catch(e){alert(e.message);}
	    }
	    
	   	function postTargetInfoMessage(request)
	    {
	    		
	       	try
			{
		        var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		        if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("更新成功,请查询!");
		        	var params = "";			
	    			var requestUrl ="bc016/addTarget.action"; 
					var responseMethod="addTargetMessage";		
					sendRequestForParams_p(requestUrl,responseMethod,params );
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
	    
	     function addTargetMessage(request)
	     {
		    	try
		        {
		        	var responsetext = eval("(" + request.responseText + ")");
		        	commoninfodivRecordDate.options.data=$.extend(true, {},{Rows: null,Total:0});
	            	commoninfodivRecordDate.loadData(true); 
	            	addrtrade(); 
	            	document.getElementById("entrybillid").value=responsetext.curStoretargetinfo.entrybillid;
		    		document.getElementById("compid").value="";
			   		document.getElementById("compname").value="";
			   		document.getElementById("targetmonth").value="";
			   		document.getElementById("ttotalyeji").value="";
			   		document.getElementById("trealtotalyeji").value="";
			   		document.getElementById("tbeatyyeji").value="";
			   		document.getElementById("ttrhyeji").value="";
			   		document.getElementById("tstaffleavelcount").value="";
			   		document.getElementById("tcostcount").value="";
			   		var today = new Date();
					var intYear=today.getYear();
					var intMonth=today.getMonth()+1;
					var intDay=today.getDate();
					var today = intYear.toString()+"-"+fullStr(intMonth.toString());
					document.getElementById("targetmonth").value=today;	
			   	}catch(e){alert(e.message);}
	     }
			   	