
   	var pc012layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc012layout= $("#pc012layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc012layout.centerWidth
       	   	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
       	   	 $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '内部工号', 			name: 'strInit', 			width:100	,align: 'left' },
                { display: '员工工号', 			name: 'strEmpNo', 			width:80	,align: 'left' },
                { display: '员工名称', 			name: 'strEmpName', 			width:120	,align: 'left' },
                { display: '老客洗发', 			name: 'oldxf',  		width:80	,align: 'left'},
	            { display: '洗发个数', 			name: 'xfcount',  			width:80	,align: 'left'},
	            { display: '老客项目', 			name: 'olditem',  			width:80	,align: 'left'},
	            { display: '项目个数', 			name: 'itemcount',  			width:80	,align: 'left'},
	            { display: '美容项目', 			name: 'mrcount',  			width:80	,align: 'left'},
	            { display: '老客人数', 			name: 'oldcount',  			width:80	,align: 'left'},
	            { display: '护理个数', 			name: 'hlcount',  			width:80	,align: 'left'},
	            { display: '染发个数', 			name: 'rfcount',  			width:80	,align: 'left'},
	            { display: '烫发个数', 			name: 'tfcount',  			width:80	,align: 'left'},
	            { display: '热烫个数', 			name: 'recount',  			width:80	,align: 'left'},
	            { display: '疗程指标', 			name: 'gmitem',  			width:80	,align: 'left'}
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: true,usePager: true
               
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
            addtrade();
   		}catch(e){alert(e.message);}
    });
    
    function  addtrade()
        {
        	var row = commoninfodivTradeDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivTradeDate.addRow({ 
				                shopId: "",
				                shopName: "",
				                dateReport: ""
				             
				            }, row, false);
        }
    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();

    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCurCompId="+strCurCompId;		
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
    	params=params+"&strSearchType="+document.getElementById("strSearchType").value;	
     	var requestUrl ="pc012/loadDataSet.action"; 
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
			showDialogmanager.close();
		}
