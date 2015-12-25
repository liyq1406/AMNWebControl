
   	var pc013layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc013layout= $("#pc013layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc013layout.centerWidth
       	   	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
       	   	 $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '公司编号', 			name: 'strCompid', 			width:100	,align: 'left' },
                { display: '公司名称', 			name: 'strCompName', 		width:160	,align: 'left' },
                { display: '员工编号', 			name: 'strEmpNo', 			width:120	,align: 'left' },
                { display: '员工名称', 			name: 'strEmpName',  		width:120	,align: 'left'},
                { display: '职位编号', 			name: 'strPostion', 		width:80	,align: 'left' },
                { display: '职位名称', 			name: 'strPostionText',  	width:120	,align: 'left'},
                { display: '入职日期', 			name: 'strEntrydata', 		width:120	,align: 'left' },
	            { display: '老客个数', 			name: 'ccount',  			width:80	,align: 'left'},
	            { display: '差异个数', 			name: 'difCount',  			width:80	,align: 'left'}
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
    	params=params+"&targetcount="+document.getElementById("targetcount").value;	
     	var requestUrl ="pc013/loadDataSet.action"; 
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

		
		function loadDataSetExcel()
		{
			
			//var PrintControl=parent.document.getElementById("PrintControl");
			
			var printExcel=parent.document.getElementById("PrintControl");
			printExcel.PRINT_INIT(""); 
			printExcel.ADD_PRINT_TABLE(100,20,500,60,document.getElementById("scroll").innerHTML); 
			printExcel.SET_SAVE_MODE("PAGE_TYPE",10000); 
			printExcel.SET_SAVE_MODE("CenterHeader","页眉"); //Excel文件的页面设置
			printExcel.SET_SAVE_MODE("CenterFooter","第&P页"); //Excel文件的页面设置
			printExcel.SET_SAVE_MODE("Caption","我的标题栏");//Excel文件的页面设置					 
			printExcel.SET_SAVE_MODE("RETURN_FILE_NAME",1); 
			printExcel.SET_SAVE_MODE("QUICK_SAVE",true);
			printExcel.SAVE_TO_FILE("员工工资明细.xls");
			
			/*var strCurCompId=getCurOrgFromSearchBar();

	    	var strFromDate=document.getElementById("strFromDate").value;
	    	var strToDate=document.getElementById("strToDate").value;
	    	
	    	var params = "strCurCompId="+strCurCompId;		
	    	params=params+"&strFromDate="+strFromDate;	
	    	params=params+"&strToDate="+strToDate;	
	    	params=params+"&targetcount="+document.getElementById("targetcount").value;	
			var excelDialog=$.ligerDialog.open({url: contextURL+'/pc013/loadpc013Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);*/
		}