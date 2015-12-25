
   	var pc019layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var strEntryCompId="";
	var strEntryBillId="";
	var ibillstate=0;
	var arrayIdx = [];
	var curRecord=null;
	var f_detailPanel=null;
    var f_callback=null;
	var subsidychooseData = [{ choose: 1, text: '全部满足' }, { choose: 2, text: '部分满足'}];
	var statechooseData = [{ choose: 3, text: '已作废' },{ choose: 2, text: '已驳回' },{ choose: 1, text: '已审核' }, { choose: 0, text: '未审核'}];
	var targetData = [{ choose: 1, text: '个人虚业绩' }, { choose: 2, text: '个人实业绩'}, { choose: 3, text: '个人老客数'}, { choose: 4, text: '美容大项数'}, { choose: 5, text: '烫染疗程数'}];
   	var targemodechooseData = [{ choose: 1, text: '额度' }, { choose: 2, text: '比率'}];
   	var targeyejitypechooseData = [{ choose: 1, text: '个人现金业绩' }, { choose: 2, text: '个人实业绩'}, { choose: 3, text: '个人总业绩'}];
  
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc019layout= $("#pc019layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc019layout.centerWidth
       	   	$("#strFromDate").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM",  labelAlign: 'right',width:'120' });
       	   	
           //--------销售条码卡
              commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '保底门店', 	name: 'handcompid', 			width:80,align: 	'left'} ,
	            { display: '门店名称', 	name: 'handcompname', 			width:120,align: 	'left'}, 
	            { display: '指标工号', 	name: 'handstaffid', 			width:80,align: 	'left'} ,
	            { display: '内部工号', 	name: 'handstaffinid', 			width:90,align: 	'left'} ,
	            { display: '员工名称', 	name: 'handstaffname', 			width:80,align: 	'left'},
	             { display: '计算方式', 	name: 'targemode', 				width:60,align: 	'left', 
	                editor: { type: 'select', data: targemodechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.targemode == 1) return '额度';
	                      return '比率';
	                }
	            },
	            { display: '计算类型', 	name: 'targeyejitype', 				width:80,align: 	'left', 
	                editor: { type: 'select', data: targeyejitypechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.targeyejitype == 1) return '个人现金业绩';
	                    if (item.targeyejitype == 2) return '个人实业绩';
	                    if (item.targeyejitype == 3) return '个人总业绩';
	                      return '';
	                }
	             },
	            { display: '指标额度', 	name: 'subsidyamt', 			width:60,align: 	'left'},
	            { display: '指标条件', 	name: 'subsidyconditiontext', 	width:300,align: 	'left' ,
	            	render: function (row) { 
     					return"<font color=\"blue\">"+row.subsidyconditiontext+"</font>";  
 					} 
 				},
	            { display: '指标方式', 	name: 'targeflag', 			width:60,align: 	'left', 
	                editor: { type: 'select', data: subsidychooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.targeflag == 1) return '全部满足';
	                      return '部分满足';
	                }},
	            
	          
	            { display: '起始月份', 	name: 'startdate', 				width:60,align: 	'left'},
	            { display: '结束月份', 	name: 'enddate', 				width:60,align: 	'left'},
				{ display: '单据状态', 	name: 'billflag', 		   width:70,align: 	'left', 
	                editor: { type: 'select', data: statechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.billflag == 1) return '已审核';
	                    else if (item.billflag == 2) return '已驳回';
	                    else if (item.billflag == 3) return '已作废';
	                      return '未审核';
	                }
	            },
	            { name: 'bentrycompid', width:1,hide:true},
	            { name: 'bentrybillid', width:1,hide:true}      
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'92%',
                onSelectRow : function (data, rowindex, rowobj)
                {
                    strEntryCompId=data.bentrycompid;
                    strEntryBillId=data.bentrybillid;
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
                },
                detail: { onShowDetail: showTragetDetialinfo}	  
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
	         $("#excelButton").ligerButton(
	         {
	             text: '转Excel', width: 120,
		         click: function ()
		         {
		             loadDataSetExcel();
		         }
	         });
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString());
			document.getElementById("strFromDate").value=today;
            $("#pageloading").hide(); 
            addtrade();
   		}catch(e){alert(e.message);}
    });
    
    
    	function showTragetDetialinfo(row, detailPanel,callback)
    	{
			f_detailPanel=detailPanel;
			f_callback=callback;
			loadTragetDetiaByCardNo(row.bentrycompid,row.bentrybillid);
		}
		
		function loadTragetDetiaByCardNo(strEntryCompId,strEntryBillId)
		{
			arrayIdx=[];
			var requestUrl ="pc019/loadDetialDataSet.action"; 
			var responseMethod="loadDetialDataSetMessage";		
			var param="strCurCompId="+strEntryCompId;	
			param=param+"&strCurBillId="+strEntryBillId;
			sendRequestForParams_p(requestUrl,responseMethod,param );
		}
		
		function loadDetialDataSetMessage(request)
	    {
	    	try
	    	{
	    	var responsetext = eval("(" + request.responseText + ")");
	    
	   		if(responsetext.lsDetialDataSet!=null && responsetext.lsDetialDataSet.length>0)
	   		{
	   			var detailGrid = document.createElement('div'); 
	            $(f_detailPanel).append(detailGrid);
	            $(detailGrid).css('margin',10).ligerGrid({
	                columns:
	                            [
	                            { display: '指标类型', 	name: 'targettype' ,width: 130 , 
					                editor: { type: 'select', data: subsidychooseData, valueField: 'choose' },
					                render: function (item)
					                {
					                    if (item.targettype == 1) return '个人虚业绩';
					                    else if (item.targettype == 2) return '个人实业绩';
					                    else if (item.targettype == 3) return '个人老客数';
					                    else if (item.targettype == 4) return '美容大项数';
					                      return '烫染疗程数';
					                }
					             },
	                            { display: '指标金额',   name: 'targetamt' ,width: 60 },
	                          	{ display: '开始日期',   name: 'startdate' ,width: 100 },
	                          	{ display: '结束日期', 	name: 'enddate' ,width: 100 }
	                            ], isScroll: false, showToggleColBtn: false, width: '400', 
	                 data:{Rows: responsetext.lsDetialDataSet,Total: responsetext.lsDetialDataSet.length}
	                 , showTitle: false, rownumbers:false,columnWidth: 100
	                 , onAfterShowData: f_callback,frozen:false,usePager:false
	            });
	   		}
	   		}catch(e){alert(e.message)}
	   	}
		
    function  addtrade()
        {
        	var row = commoninfodivTradeDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivTradeDate.addRow({ 
				                subsidyconditiontext: "",
				                conditionnum: "",
				                billflag: 0
				             
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
    	var requestUrl ="pc019/loadDataSet.action"; 
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

		
		function loadDataSetExcel()
		{
			var strCurCompId=getCurOrgFromSearchBar();

	    	var strFromDate=document.getElementById("strFromDate").value;
	    	
	    	var params = "strCurCompId="+strCurCompId;		
	    	params=params+"&strFromDate="+strFromDate;	
	    	params=params+"&billstate="+document.getElementById("billstate").value;	
			var excelDialog=$.ligerDialog.open({url: contextURL+'/pc019/loadpc019Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
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
	    var requestUrl ="pc019/comfrimBill.action"; 
		var responseMethod="comfrimBillMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );*/
		var needReplaceStr="";
    	var curjosnparam="";
    	var jsonParam="";
		for (var rowid in commoninfodivTradeDate.records)
		{
			if(findIdx(rowid)!=-1)
			{	
				    var row =commoninfodivTradeDate.records[rowid];
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
       	 		var requestUrl ="pc019/comfrimBill.action"; 
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
		   			for (var rowid in commoninfodivTradeDate.records)
					{
		   				if(findIdx(rowid)!=-1)
						{	
							    var row =commoninfodivTradeDate.records[rowid];
							    commoninfodivTradeDate.updateRow(row,{ billflag:1});
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
	    var requestUrl ="pc019/comfrimbackBill.action"; 
		var responseMethod="comfrimbackBillMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );*/
		var needReplaceStr="";
    	var curjosnparam="";
    	var jsonParam="";
		for (var rowid in commoninfodivTradeDate.records)
		{
			if(findIdx(rowid)!=-1)
			{	
				    var row =commoninfodivTradeDate.records[rowid];
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
       	 		var requestUrl ="pc019/comfrimbackBill.action"; 
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
		   		    //commoninfodivTradeDate.updateRow(curRecord,{ billflag:2});
		   		    for (var rowid in commoninfodivTradeDate.records)
					{
		   		    	if(findIdx(rowid)!=-1)
						{	
							    var row =commoninfodivTradeDate.records[rowid];
							    commoninfodivTradeDate.updateRow(row,{ billflag:2});
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
			if(findIdx(rowid)!=-1)
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
   				showDialogmanager = $.ligerDialog.waitting('正在审核中,请稍候...');	
       	 		var requestUrl ="pc019/comfrimstopBill.action"; 
				var responseMethod="comfrimstopBillMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		else
		{
				$.ligerDialog.warn("请选择需要审核的明细!");
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
		   		    //commoninfodivTradeDate.updateRow(curRecord,{ billflag:2});
		   		    for (var rowid in commoninfodivTradeDate.records)
					{
		   		    	if(findIdx(rowid)!=-1)
						{	
							    var row =commoninfodivTradeDate.records[rowid];
							    commoninfodivTradeDate.updateRow(row,{ billflag:3});
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
                	addIdx(rowid);
                    //addCheckedComps_bills(commoninfodivTradeDate.records[rowid]['bentrycompid'],
                    //commoninfodivTradeDate.records[rowid]['bentrybillid']);
                }
                else
                {
                    //removeCheckedComps_bills(commoninfodivTradeDate.records[rowid]['bentrycompid'],
                    //commoninfodivTradeDate.records[rowid]['bentrybillid']);
                	removeIdx(rowid);
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