
   	var pc023layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc023layout= $("#pc023layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc023layout.centerWidth
	
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '手机号码',name: 'mobilephone',width:120	,align: 'left'},
                { display: '接收日期',name: 'operdate' ,width:120	,align: 'left'},
                { display: '内容',   name:     'content' ,width:120	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-60,
                enabledEdit: false,  checkbox: false,rownumbers: true,usePager: true
               
            });
            $("#strFromDate").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM-dd", labelWidth:80, labelAlign: 'right' });
  		 	$("#strToDate").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM-dd", labelWidth:80, labelAlign: 'right' });
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			document.getElementById("strToDate").value=today;
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询短信', width: 120,
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

    function loadDataSet()
    {	
    	var params = "strFromDate="+document.getElementById("strFromDate").value;
    	params=params+"&strToDate="+document.getElementById("strToDate").value;
    	
    	var requestUrl ="pc023/loadDataSet.action"; 
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
				 	$.ligerDialog.warn("没有查到短信信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
		
		function loadDataSetExcel()
		{
    		var params = "strFromDate="+document.getElementById("strFromDate").value;
    		params=params+"&strToDate="+document.getElementById("strToDate").value;
			var excelDialog=$.ligerDialog.open({url: contextURL+'/pc023/loadpc023Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
		}
