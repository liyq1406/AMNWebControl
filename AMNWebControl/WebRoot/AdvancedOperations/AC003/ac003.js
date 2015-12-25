
   	var pc020layout=null;
   	var commoninfodivdetialtj=null;
	var showDialogmanager=null;
	var strEntryCompId="";
	var strEntryBillId="";
	var ibillstate=0;
	var curRecord=null;
	var arrayIdx = [];
	//var rewaedchooseData = [{ choose: '0', text: '奖励' }, { choose: '1', text: '处罚'}];
	//var rewardchooseData = [{ choose: 1, text: '处罚' }, { choose: 0, text: '奖励'}];
   	//var staffRewardData=JSON.parse(parent.loadCommonControlDate_select("ZBJF",0));
   	//var statechooseData = [{ choose: 3, text: '已作废' },{ choose: 2, text: '已驳回' },{ choose: 1, text: '已审核' }, { choose: 0, text: '未审核'}];
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc020layout= $("#pc020layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc020layout.centerWidth
       	   	//$("#strFromDate").ligerDateEditor({ labelWidth: 100,  labelAlign: 'right',width:'100' });
       	   	//$("#strToDate").ligerDateEditor({ labelWidth: 100,  labelAlign: 'right',width:'100' });
          commoninfodivdetialtj=$("#commoninfodivdetialtj").ligerGrid({
                columns: [
                { display: '门店编号', 	name: 'strCompId', 			width:80,align: 	'left'} ,
                { display: '门店名称', 	name: 'strCompName', 			width:80,align: 	'left'} ,
                { display: '消费单号',		name: 'billid', 			width:120,align: 	'left'},
                { display: '会员卡号', 	name: 'strCardNo', 			width:100,align: 	'left'} ,
                { display: '会员名称', 	name: 'membername', 			width:60,align: 	'left'
	                //editor: { type: 'select', data: rewardchooseData, valueField: 'choose' },
	            },
	            {name: 'uuid', hide:true,width:60,align: 	'left'
	                //editor: { type: 'select', data: rewardchooseData, valueField: 'choose' },
	            },
	            {display: '折扣',name: 'discount', width:60,align: 	'left'
	                //editor: { type: 'select', data: rewardchooseData, valueField: 'choose' },
	            },
                { display: '评价内容', 	name: 'content',  	width:300,align: 	'left'
 				},
 				{ display: '备注', 	name: 'remarks',  	width:300,align: 	'left',
 					editor: { type: 'text'}
 				}
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
                onCheckRow: null,//f_onCheckRow,
                onCheckAllRow: null,//f_onCheckAllRow,
                enabledEdit: true,  checkbox: true ,rownumbers: false,usePager: true,
                toolbar: { items: [
	                { text: '单据处理', click: post, img: contextURL+'/common/ligerui/ligerUI/skins/icons/ok.gif' },
	           		{ text: '单据驳回', click: updBillState, img: contextURL+'/common/ligerui/ligerUI/skins/icons/back.gif' }
	           		//{ text: '人事经理作废', click: comfrimstopBill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif' }
					]
                }
            });
            document.getElementById("showTable").width=centerWidth;
            /*$("#searchButton").ligerButton(
	         {
	             text: '查询报表', width: 120,
		         click: function ()
		         {
		             
		         }
	         });*/
            loadDataSet();
	        /*var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			document.getElementById("strToDate").value=today;*/
            $("#pageloading").hide(); 
            //addOption("","全部",document.getElementById("strSearchType"));
           	//var goodstypes=parent.gainCommonInfoByCode("ZBJF",0);
		    /*for(var i=0;i<goodstypes.length;i++)
			{
				addOption(goodstypes[i].bparentcodekey,goodstypes[i].parentcodevalue,document.getElementById("strSearchType"));
			}*/		
            //addDTjRecord();
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
    	var params="";	 
    	var requestUrl ="ac003/load.action"; 
		var responseMethod="loadMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
   		 function loadMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsEvaluations!=null && responsetext.lsEvaluations.length>0)
           	{
            		commoninfodivdetialtj.options.data=$.extend(true, {},{Rows: responsetext.lsEvaluations,Total: responsetext.lsEvaluations.length});
            		commoninfodivdetialtj.loadData(true);
            }
			//commoninfodivdetialtj.sortedData=false;
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
        
        function post()
        {
        	var rows=commoninfodivdetialtj.getCheckedRows();
        	if(rows.length==0)
        	{
        		$.ligerDialog.warn("请选择一条单据");
        		return;
        	}
        	if(rows.length>1)
        	{
        		$.ligerDialog.warn("一次只能处理一条单据");
        		return;
        	}
        	if(checkNull(rows[0].remarks)=="")
        	{
        		$.ligerDialog.warn("请填写好同意理由");
        		return;
        	}
        	try
        	{
	        	$.post(contextURL+"/ac003/post.action",{"curMaster.uuid":rows[0].uuid,"curMaster.billid":rows[0].billid,"curMaster.discount":rows[0].discount,"curMaster.content":rows[0].content,
	        		"curMaster.strCardNo":rows[0].strCardNo,"curMaster.membername":rows[0].membername,"curMaster.membermphone":rows[0].membermphone,"curMaster.remarks":rows[0].remarks,
	        		"curMaster.strCompId":rows[0].strCompId,"curMaster.strCompName":rows[0].strCompName},function(data){
	        			if(data.strMessage!="")
	        			{
	        				$.ligerDialog.warn(data.strMessage);
	        			}
	        			else
	        			{
	        				$.ligerDialog.warn("处理成功");
	        			}
	        			
	        		});
        	}
        	catch(e)
        	{
        		alert(e.message);
        	}
        	
        }
        
        
        function updBillState()
        {
        	var rows=commoninfodivdetialtj.getCheckedRows();
        	if(rows.length==0)
        	{
        		$.ligerDialog.warn("请选择一条单据");
        		return;
        	}
        	if(rows.length>1)
        	{
        		$.ligerDialog.warn("一次只能处理一条单据");
        		return;
        	}
        	if(checkNull(rows[0].remarks)=="")
        	{
        		$.ligerDialog.warn("请填写好同意理由");
        		return;
        	}
        	try
        	{
	        	$.post(contextURL+"/ac003/updBillState.action",{"curMaster.uuid":rows[0].uuid,"curMaster.billid":rows[0].billid,"curMaster.discount":rows[0].discount,"curMaster.content":rows[0].content,
	        		"curMaster.strCardNo":rows[0].strCardNo,"curMaster.membername":rows[0].membername,"curMaster.membermphone":rows[0].membermphone,"curMaster.remarks":rows[0].remarks,
	        		"curMaster.strCompId":rows[0].strCompId,"curMaster.strCompName":rows[0].strCompName},function(data){
	        			if(data.strMessage!="")
	        			{
	        				$.ligerDialog.warn(data.strMessage);
	        			}
	        			else
	        			{
	        				$.ligerDialog.warn("处理成功");
	        			}
	        			
	        		});
        	}
        	catch(e)
        	{
        		alert(e.message);
        	}
        	
        }