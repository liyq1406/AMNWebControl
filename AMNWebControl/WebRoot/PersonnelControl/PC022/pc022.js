
   	var pc022layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var departmentmanager=null;
	var departBMZLData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc022layout= $("#pc022layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc022layout.centerWidth
	
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
	            { display: '工号', 			name: 'bstaffno', 			width:60	,align: 'left' },
                { display: '姓名', 			name: 'staffname',  		width:80	,align: 'left' },
				{ display: '1',   		name: 'data1',  		width:80	,align: 'left'},
				{ display: '2',   		name: 'data2',  		width:80	,align: 'left'},
				{ display: '3',   		name: 'data3',  		width:80	,align: 'left'},
				{ display: '4',   		name: 'data4',  		width:80	,align: 'left'},
				{ display: '5',   		name: 'data5',  		width:80	,align: 'left'},
				{ display: '6',   		name: 'data6',  		width:80	,align: 'left'},
				{ display: '7',   		name: 'data7',  		width:80	,align: 'left'},
				{ display: '8',   		name: 'data8',  		width:80	,align: 'left'},
				{ display: '9',   		name: 'data9',  		width:80	,align: 'left'},
				{ display: '10',   		name: 'data10',  		width:80	,align: 'left'},
				{ display: '11',   		name: 'data11',  		width:80	,align: 'left'},
				{ display: '12',   		name: 'data12',  		width:80	,align: 'left'},
				{ display: '13',   		name: 'data13',  		width:80	,align: 'left'},
				{ display: '14',   		name: 'data14',  		width:80	,align: 'left'},
				{ display: '15',   		name: 'data15',  		width:80	,align: 'left'},
				{ display: '16',   		name: 'data16',  		width:80	,align: 'left'},
				{ display: '17',   		name: 'data17',  		width:80	,align: 'left'},
				{ display: '18',   		name: 'data18',  		width:80	,align: 'left'},
				{ display: '19',   		name: 'data19',  		width:80	,align: 'left'},
				{ display: '20',   		name: 'data20',  		width:80	,align: 'left'},
				{ display: '21',   		name: 'data21',  		width:80	,align: 'left'},
				{ display: '22',   		name: 'data22',  		width:80	,align: 'left'},
				{ display: '23',   		name: 'data23',  		width:80	,align: 'left'},
				{ display: '24',   		name: 'data24',  		width:80	,align: 'left'},
				{ display: '25',   		name: 'data25',  		width:80	,align: 'left'},
				{ display: '26',   		name: 'data26',  		width:80	,align: 'left'},
				{ display: '27',   		name: 'data27',  		width:80	,align: 'left'},
				{ display: '28',   		name: 'data28',  		width:80	,align: 'left'},
				{ display: '29',   		name: 'data29',  		width:80	,align: 'left'},
				{ display: '30',   		name: 'data30',  		width:80	,align: 'left'},
				{ display: '31',   		name: 'data31',  		width:80	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-60,
                enabledEdit: false,  checkbox: false,rownumbers: true,usePager: true
            });
  		 	$("#strFromDate").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM", labelWidth:80, labelAlign: 'right' })
            //$("#strDdatelast").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString());
			document.getElementById("strFromDate").value=today;
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '门店考勤统计', width: 120,
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
   
    	var strCurCompId=getCurOrgFromSearchBar();
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromDate="+document.getElementById("strFromDate").value;
    	var requestUrl ="pc022/loadDataSet.action"; 
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
				 	$.ligerDialog.warn("没有查到考情信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
		
		function loadDataSetExcel()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var params = "strCurCompId="+strCurCompId;	
    		params=params+"&strFromDate="+document.getElementById("strFromDate").value;
			var excelDialog=$.ligerDialog.open({url: contextURL+'/pc022/loadpc022Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
		}
