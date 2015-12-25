
   	var pc015layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var departmentmanager=null;
	/*var yejichooseData = [{ choose: '1', text: '业务' }, { choose: '0', text: '非业务'}];
	var sexchooseData = [{ choose: '1', text: '男' }, { choose: '0', text: '女'}];
	var departBMZLData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));*/
	var detialTab=null;
	var GZGW=null;
	var inDataGrid=null;
	var outDataGrid=null;
	var curpagestate=1;
   	$(function ()
   	{
	   try
	   {
	   		//布局
            pc015layout= $("#pc015layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
            $(".l-layout-center").css("top", "25px")
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc015layout.centerWidth;
            $("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100'});
          	$("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
          	$("#inFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100'});
          	$("#inToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
          	$("#outFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100'});
          	$("#outToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
           	$("#detialTab").ligerTab();
           	detialTab = $("#detialTab").ligerGetTabManager();
       		
            //人事职位异动分析
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
            	columns: null,  pageSize:25, 
            	data: null,      
            	width: centerWidth,
            	height:height-88,
            	enabledEdit: false,rownumbers: true,usePager: false
            });
            //入职率统计
            inDataGrid=$("#inDataGrid").ligerGrid({
            	columns: null,  pageSize:25, 
            	data: null,      
            	width: centerWidth,
            	height:height-88,
            	enabledEdit: false,rownumbers: false,usePager: false
            });
            //离职率统计
            outDataGrid=$("#outDataGrid").ligerGrid({
            	columns: null,  pageSize:25, 
            	data: null,      
            	width: centerWidth,
            	height:height-88,
            	enabledEdit: false,rownumbers: false,usePager: false
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton({
	             text: '查询门店人事', width: 120,
		         click: function (){
		        	 curpagestate=0;
		             loadDataSet();
		         }
	         });
	         
	         $("#excelButton").ligerButton({
	             text: '转Excel', width: 120,
		         click: function (){
		        	 curpagestate=0;
		             loadDataSetExcel();
		         }
	         });
	         $("#insearchBtn").ligerButton({
    			 text: '查询入职率', width: 120,
    			 click: function (){
    				 curpagestate=1;
    				 loadDataSet();
    			 }
    		 });
	         
	         $("#inexcelBtn").ligerButton({
    			 text: '转Excel', width: 120,
    			 click: function (){
    				 curpagestate=1;
    				 loadDataSetExcel();
    			 }
    		 });
	         $("#outsearchBtn").ligerButton({
	        	 text: '查询离职率', width: 120,
	        	 click: function (){
	        		 curpagestate=2;
	        		 loadDataSet();
	        	 }
	         });
	         
	         $("#outexcelBtn").ligerButton({
	        	 text: '转Excel', width: 120,
	        	 click: function (){
	        		 curpagestate=2;
	        		 loadDataSetExcel();
	        	 }
	         });
	         
	        var today = new Date();
	        var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var curdate = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
	 		$("#strFromDate").val(curdate);
          	$("#strToDate").val(curdate);
          	$("#inFromDate").val(curdate);
          	$("#inToDate").val(curdate);
          	$("#outFromDate").val(curdate);
          	$("#outToDate").val(curdate);
            $("#pageloading").hide(); 
            f_setColumns();
            addtrade(commoninfodivTradeDate);
            addtrade(inDataGrid);
            addtrade(outDataGrid);
   		}catch(e){alert(e.message);}
    });
    
     function f_setColumns(){ 
     		var showColumns='[', newColumns=null; 
     		showColumns+='{"display":"编号","frozen":"true","name":"strCompId","width":"60","align":"left"},';
     		showColumns+='{"display":"门店","frozen":"true","name":"strCompName","width":"100","align":"left"}';
     		newColumns = showColumns;
     		newColumns+=',{"display":"日期","frozen":"true","name":"strDate","width":"100","align":"left"}';
     		GZGW=parent.gainCommonInfoByCodeByUse("GZGW",0,1);
		    for(var i=0;i<GZGW.length;i++)
			{	
				showColumns+= ',{"display":"'+GZGW[i].parentcodevalue+'","name":"'+GZGW[i].bparentcodekey+'Count","width":"80","align":"right","type":"int","totalSummary":{"type":"tsum"}}';
				newColumns += ',{"display":"'+GZGW[i].parentcodevalue+'","name":"'+GZGW[i].bparentcodekey+'Count","width":"80","align":"right","type":"float"}';
			}
			showColumns+= ',{"display":"合计","name": "totalCount","width":"60","align":"right","type":"int","totalSummary":{"type":"tsum"}}]';
			newColumns += ',{"display":"合计","name": "totalCount","width":"60","align":"right","type":"float"}]';
            commoninfodivTradeDate.set('columns', JSON.parse(showColumns)); 
            commoninfodivTradeDate.reRender();
            var cols = JSON.parse(newColumns);
            inDataGrid.set('columns', cols); 
            inDataGrid.reRender();
            outDataGrid.set('columns', cols); 
            outDataGrid.reRender();
     }
    
    function addtrade(datagrid){
        	var row = datagrid.getSelectedRow();
		     //参数1:rowdata(非必填)
		    //参数2:插入的位置 Row Data 
		    //参数3:之前或者之后(非必填)
        	datagrid.addRow({shopId: "",shopName: "",dateReport: ""}, row, false);
    }
    function procParam(){
    	var strCurCompId=getCurOrgFromSearchBar();
        var params = "strCurCompId="+strCurCompId;	
    	params +="&searchType="+(curpagestate==0 ? $("#searchType").val():curpagestate);
    	params +="&procType=";
    	var beginDate=null, endDate=null;
    	if(curpagestate==1){
    		params +=($("#inDetail").is(":checked")?1:2);
    		beginDate=$("#inFromDate").val();
    		endDate=$("#inToDate").val();
    	}else if(curpagestate==2){
    		params +=($("#outDetail").is(":checked")?1:2);
    		beginDate=$("#outFromDate").val();
    		endDate=$("#outToDate").val();
    	}else{
    		params +=0;
    		beginDate=$("#strFromDate").val();
    		endDate=$("#strToDate").val();
    	}
    	params +="&strFromDate="+beginDate;
        params +="&strToDate="+endDate;
    	return params;
    }
    //登记储值卡
    function loadDataSet(){	
    	var params = procParam();
    	var requestUrl=contextURL+"/pc015/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params);
    }
    
    function loadDataSetMessage(request){
		var responsetext = eval("(" + request.responseText + ")");
		if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0){
   			var curdate=loadPrjData(responsetext.lsDataSet);
   			if(curpagestate==0){
   				commoninfodivTradeDate.options.data=$.extend(true, {},curdate);
   				commoninfodivTradeDate.loadData(true);
   			}else if(curpagestate==1){
   				inDataGrid.options.data=$.extend(true, {},curdate);
   				inDataGrid.loadData(true);
   			}else if(curpagestate==2){
   				outDataGrid.options.data=$.extend(true, {},curdate);
   				outDataGrid.loadData(true);
   			}
        }else{
		 	$.ligerDialog.warn("没有查到报表数据!");
		 	if(curpagestate==0){
		 		commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        	commoninfodivTradeDate.loadData(true);
	        	addtrade(commoninfodivTradeDate);
   			}else if(curpagestate==1){
   				inDataGrid.options.data=$.extend(true, {},{Rows: null,Total: 0});
   				inDataGrid.loadData(true);
   	        	addtrade(inDataGrid);
   			}else if(curpagestate==2){
   				outDataGrid.options.data=$.extend(true, {},{Rows: null,Total: 0});
   				outDataGrid.loadData(true);
   				addtrade(outDataGrid); 
   			}
		}
		showDialogmanager.close();
	}
	
	function loadPrjData(lsDataSet){
		var pjDataJson="";
		for(var i=0;i<lsDataSet.length;i++){
			if(pjDataJson==""){
				pjDataJson=pjDataJson+'[';
			}else{
				pjDataJson=pjDataJson+',';
			}
			pjDataJson=pjDataJson+'{"strCompId":"'+lsDataSet[i].strCompId+'","strCompName":"'+lsDataSet[i].strCompName+'"';
			pjDataJson=pjDataJson+',"strDate":"'+lsDataSet[i].strDate+'"';
		    pjDataJson=pjDataJson+',"totalCount":"'+lsDataSet[i].totalCount+'"';
		    for(var j=0;j<GZGW.length;j++){	
				pjDataJson+=',"'+GZGW[j].bparentcodekey+'Count":"'+lsDataSet[i].prjTypesAmt[j][0]+'"';
			}
			pjDataJson=pjDataJson+'}';
		}
		if(pjDataJson!=""){
			pjDataJson=pjDataJson+']';
			return {Rows:JSON.parse(pjDataJson),Total: lsDataSet.length };
		}
	}
	
	function loadDataSetExcel(){
		var params = procParam();
		var excelDialog=$.ligerDialog.open({url: contextURL+'/pc015/loadpc015Excel.action?'+params});
		setTimeout(function() { excelDialog.close();}, 10000);
	}