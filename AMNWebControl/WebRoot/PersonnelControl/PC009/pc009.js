
   	var pc009layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var departBMZLData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc009layout= $("#pc009layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc009layout.centerWidth
       		$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                {
                	display: '门店',frozen:true, columns:
	            	[	
	            		{ display: '内部编号', 		name: 'staffinid', 		frozen:true,	width:80	,align: 'left' },
	            		{ display: '员工编号', 		name: 'staffno', 		frozen:true,	width:80	,align: 'left' },
	            		{ display: '员工姓名', 		name: 'staffname', 		frozen:true,	width:80	,align: 'left' },
	            		{ display: '职位', 			name: 'staffposition', 		frozen:true,	width:80	,align: 'left' }
	            	]
                },
	            {
                	display: '门店客单数', columns:
	            	[	
	            		{ display: '老客单数', 		name: 'oldcostcount', 			width:80	,align: 'left' },
	            		{ display: '新客单数', 		name: 'newcostcount', 			width:80	,align: 'left' },
	            		{ display: '烫染单数', 		name: 'trcostcount', 			width:80	,align: 'left' }
	            	]
                },	
                {
                	display: '现金消费', columns:
	            	[	
	            		{ display: '大项', 		name: 'cashbigcost', 			width:80	,align: 'left' },
	            		{ display: '小项', 		name: 'cashsmallcost', 			width:80	,align: 'left' },
	            		{ display: '护理', 		name: 'cashhulicost', 			width:80	,align: 'left' }
	            	]
                },
	            {
                	display: '销卡消费', columns:
	            	[	
	            		{ display: '大项', 		name: 'cardbigcost', 			width:80	,align: 'left' },
	            		{ display: '小项', 		name: 'cardsmallcost', 			width:80	,align: 'left' },
	            		{ display: '护理', 		name: 'cardhulicost', 			width:80	,align: 'left' }
	            	]
                },
                {
                	display: '其他消费', columns:
	            	[	
	            		{ display: '疗程消费', 		name: 'cardprocost', 			width:80	,align: 'left' },
	            		{ display: '收购卡消费', 		name: 'cardsgcost', 			width:80	,align: 'left' },
	            		{ display: '积分消费', 		name: 'cardpointcost', 			width:80	,align: 'left' },
	            		{ display: '项目抵用券', 		name: 'projectdycost', 			width:80	,align: 'left' },
	            		{ display: '现金抵用券', 		name: 'cashdycost', 			width:80	,align: 'left' },
	            		{ display: '条码卡', 			name: 'tmcardcost', 			width:80	,align: 'left' }
	            	]
                }, 
                {
                	display: '售卡/售产品', columns:
	            	[	
	            		{ display: '产品销售', 			name: 'salegoodsamt', 			width:80	,align: 'left' },
	            		{ display: '会员卡销售', 			name: 'salecardsamt', 			width:80	,align: 'left' },
	            		{ display: '条码卡销售', 			name: 'saletmkamt', 			width:80	,align: 'left' },
	            		{ display: '疗程兑换', 			name: 'prochangeamt', 			width:80	,align: 'left' },
	            		{ display: '全韩店内支付', 		name: 'qhpayinner', 			width:80	,align: 'left' },
	            		{ display: '全韩对方支付', 		name: 'qhpayouter', 			width:80	,align: 'left' },
	            		{ display: '暨大店内支付', 		name: 'jdpayinner', 			width:80	,align: 'left' },
	            		{ display: '私密店内支付', 		name: 'smpayinner', 			width:80	,align: 'left' }
	            	]
                }, 
                {
                	display: '业绩汇总', columns:
	            	[	
	            		{ display: '现金业绩汇总', 		name: 'totalcashamt', 			width:80	,align: 'left' },
	            		{ display: '个人业绩汇总', 		name: 'staffyeji', 			width:80	,align: 'left' }
	            	]
                }
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox: false,rownumbers: true,usePager: false
               
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
     	var requestUrl ="pc009/loadDataSet.action"; 
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
				 	$.ligerDialog.warn("没有查到员工提成信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
