
   	var sc012layout=null;
   	var commoninfodivPjDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc012layout= $("#sc012layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc012layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'110' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'110' });
           //--------销售条码卡
            commoninfodivPjDate=$("#commoninfodivPjDate").ligerGrid({
                columns: [
                 { display: '门店', 				name: 'strCompId', 				width:70	,align: 'left' },
                 { display: '门店名称', 			name: 'strCompName', 			width:120	,align: 'left' },
                 { display: '异动类型', 			name: 'changeTypename', 		width:90	,align: 'left' },
                 { display: '异动日期', 			name: 'changeDate', 			width:90	,align: 'left' },
                 { display: '异动单号', 			name: 'changeBillno', 			width:120	,align: 'left' },
                 { display: '原卡号', 			name: 'oldCardNo', 				width:100	,align: 'left' },
                 { display: '原卡类型', 			name: 'oldCardType', 			width:60	,align: 'left' },
                 { display: '类型名称', 			name: 'oldCardTypename', 		width:90	,align: 'left' },
                 { display: '新卡号', 			name: 'newCardNo', 				width:100	,align: 'left' },
                 { display: '新卡类型', 			name: 'newCardType', 			width:60	,align: 'left' },
                 { display: '类型名称', 			name: 'newCardTypeName', 		width:90	,align: 'left' },
                 { display: '会员名称', 			name: 'membername', 		width:90	,align: 'left' },
                 { display: '异动金额', 			name: 'changeamt', 				width:80	,align: 'right' }
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: false ,usePager: true
               
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询明细', width: 120,
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
        	var row = commoninfodivPjDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivPjDate.addRow({ 
				                shopId: "",
				                shopName: "",
				                changeTypename: ""
				             
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
    	params=params+"&searchType="+document.getElementById("searchType").value;
     	var requestUrl ="sc012/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivPjDate.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivPjDate.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到异动信息!");
		   			commoninfodivPjDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivPjDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
	}
	
	function loadDataSetExcel()
	{
			var strCurCompId=getCurOrgFromSearchBar();

    		var strFromDate=document.getElementById("strFromDate").value;
    		var strToDate=document.getElementById("strToDate").value;
    	
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&strToDate="+strToDate;	
    		params=params+"&searchType="+document.getElementById("searchType").value;
			var excelDialog=$.ligerDialog.open({url: contextURL+'/sc012/loadSC012Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
	}
	
	
