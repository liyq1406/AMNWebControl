
   	var sc010layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc010layout= $("#sc010layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc010layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'shopId', 			width:60	,align: 'left' },
                { display: '名称', 			name: 'shopName', 			width:110	,align: 'left' },
                { display: '日期', 			name: 'dateReport', 		width:100	,align: 'left' },
              	{ display: '卡异动',   		name: 'totalCardtrans',  	width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }
                 },
	            { display: '美容业绩',   		name: 'totalMrSale',  		width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '美发业绩',   		name: 'totalMfSale',  		width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '美甲业绩',   		name: 'totalMjSale',  		width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '其他业绩',   		name: 'totalOther',  		width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '销售合计',   		name: 'totalcardSales',  	width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '现金合计', 		name: 'totalcash', 	 		width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '银行卡合计',   	name: 'totalcredit',  		width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '第三方支付合计',   		name: 'totalock',  			width:120	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '大众点评',   		name: 'totalDztgk',  		width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '美团',   		name: 'totalMttgk',  		width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '指付通合计',   	name: 'totalzft',  			width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '销卡合计',   		name: 'totalCardsCost',  	width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '现金抵用券服务',   name: 'cashdyservice',  	width:120	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '项目抵用券服务',   name: 'prjdyservice',  		width:120	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '条码卡服务',   	name: 'tmkservice',  		width:120	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '积分服务',   		name: 'totalCostpoint',  	width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '正常条码卡销售',  	name: 'tmkASale',  			width:120	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '赠送条码卡销售',  	name: 'tmkBSale',  			width:120	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	           	{ display: '合作项目销售',   	name: 'totalHzSale',  		width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '退卡',   		name: 'totalBackCard',  	width:80	,align: 'right',
              		totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }
                 }
	             
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: true,usePager:false
               
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询日报表', width: 120,
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
     	var requestUrl ="sc010/loadDataSet.action"; 
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
				 	$.ligerDialog.warn("没有查到营业信息!");
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
    		var strToDate=document.getElementById("strToDate").value;
    	
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&strToDate="+strToDate;	
			var excelDialog=$.ligerDialog.open({url: contextURL+'/sc010/loadSC010Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
		}
