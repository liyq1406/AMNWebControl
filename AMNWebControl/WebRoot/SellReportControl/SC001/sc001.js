
   	var sc001layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc001layout= $("#sc001layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc001layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'shopId', 			width:60	,align: 'left'},
                { display: '名称', 			name: 'shopName', 			width:110	,align: 'left',isSort: true},
                { display: '日期', 			name: 'dateReport', 		width:140	,align: 'left'},
                { display: '接发应收款(现金)', 			name: 'jfysk_xj',  			width:100	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
                { display: '接发应收款(银行卡)', 			name: 'jfysk_yhk',  			width:110	,type: 'float',align: 'right',
                	totalSummary:
                	{
                		render: function (suminf, column, cell)
                		{
                			return ForDight(suminf.sum,2);
                		},
                		align: 'right'
                	}},
            	{ display: '接发应收款(其他)', 			name: 'jfysk_qt',  			width:100	,type: 'float',align: 'right',
                	totalSummary:
                	{
                		render: function (suminf, column, cell)
                		{
                			return ForDight(suminf.sum,2);
                		},
                		align: 'right'
                	}},
            	{ display: '接发应收款(合计)', 			name: 'jfysk',  			width:100	,type: 'float',align: 'right',
                	totalSummary:
                	{
                		render: function (suminf, column, cell)
                		{
                			return ForDight(suminf.sum,2);
                		},
                		align: 'right'
                	}},
                { display: '总收入', 			name: 'strtotal',  			width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '现金合计', 		name: 'cashtotal', 	 		width:80	,type: 'float',align: 'right',
	           		 render: function (item)
	                {
     					return "<font color=\"red\">"+checkNull(item.cashtotal)+"</font>";  
	                },
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return "<font color=\"red\">"+ForDight(suminf.sum,2)+"</font>";
                        },
						align: 'right'
                    }},
	            { display: '银行卡合计',   	name: 'credittotal',  		width:80	,type: 'float',align: 'right',
                	 render: function (item)
	                {
     					return "<font color=\"red\">"+checkNull(item.credittotal)+"</font>";  
	                },
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return "<font color=\"red\">"+ForDight(suminf.sum,2)+"</font>";
                        },
						align: 'right'
                    }},
	            { display: '第三方支付合计',   		name: 'ocktotal',  			width:100	,type: 'float',align: 'right',
                	 render: function (item)
	                {
     					return "<font color=\"red\">"+checkNull(item.ocktotal)+"</font>";  
	                },
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return "<font color=\"red\">"+ForDight(suminf.sum,2)+"</font>";
                        },
						align: 'right'
                    }},
	            { display: '团购卡合计',   	name: 'tgktotal',  			width:80	,type: 'float',align: 'right',
                	 render: function (item)
	                {
     					return "<font color=\"red\">"+checkNull(item.tgktotal)+"</font>";  
	                },
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return "<font color=\"red\">"+ForDight(suminf.sum,2)+"</font>";
                        },
						align: 'right'
                    }},
	            { display: '指付通合计',   	name: 'zfttotal',  			width:80	,type: 'float',align: 'right',
                	 render: function (item)
	                {
     					return "<font color=\"red\">"+checkNull(item.zfttotal)+"</font>";  
	                },
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return "<font color=\"red\">"+ForDight(suminf.sum,2)+"</font>";
                        },
						align: 'right'
                    }},
	            { display: '员工购买产品',   	name: 'staffsallprod',  	width:90	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '卡异动',   		name: 'totalcardtrans',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '销卡合计',   		name: 'cardsalestotal',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            
	            { display: '现金服务',   		name: 'cashservice',  		width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '现金产品',   		name: 'cashprod',  			width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '现金退卡',   		name: 'cashbackcard',  		width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
                    { display: '现金年卡',   		name: 'cashYearCard',  		width:80	,type: 'float',align: 'right',
                    	totalSummary:
                        {
                            render: function (suminf, column, cell)
                            {
                                return ForDight(suminf.sum,2);
                            },
    						align: 'right'
                        }},
	            { display: '现金卡异动',   	name: 'cashcardtrans',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '现金条码卡',   	name: 'cashbytmksale',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '现金兑换',   		name: 'cashchangesale',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '现金合作项目',   	name: 'cashhezprj',  		width:90	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            
	            { display: '银行卡服务',   	name: 'creditservice',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '银行卡产品',   	name: 'creditprod',  		width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '银行卡退卡',   	name: 'creditbackcard',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
                    { display: '银行卡年卡',   	name: 'creditYeardcard',  	width:80	,type: 'float',align: 'right',
                    	totalSummary:
                        {
                            render: function (suminf, column, cell)
                            {
                                return ForDight(suminf.sum,2);
                            },
    						align: 'right'
                        }},
	            { display: '银行卡卡异动',   	name: 'credittrans',  		width:90	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '银行卡条码卡',   	name: 'bankbytmksale',  	width:90	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '银行卡兑换',   	name: 'bankchangesale',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '银行卡合作项目',   name: 'bankhezprj',  		width:100	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            
	            
	            { display: '指付通卡服务',   	name: 'zftservice',  		width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '指付通产品',   	name: 'zftprod',  			width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '指付通卡异动',   	name: 'zfttrans',  			width:90	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '指付通条码卡',   	name: 'fingerbytmksale',  	width:90	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            
	            
	            { display: '第三方支付服务',   		name: 'ockservice',  		width:100	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '第三方支付产品',   		name: 'ockkprod',  			width:100	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '第三方支付卡异动',   	name: 'ocktrans',  			width:110	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '第三方支付条码卡',   	name: 'okpqypwybytmksale',  width:110	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            
	            { display: '团购卡服务',   	name: 'tgkservice',  		width:80	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '团购卡产品',   	name: 'tgkkprod',  			width:80	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '团购卡卡异动',   	name: 'tgktrans',  			width:100	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	           
	            { display: '年卡服务',   		name: 'yearsaleservice',  width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
                    { display: '销卡服务',   		name: 'cardsalesservices',  width:80	,type: 'float',align: 'right',
                    	totalSummary:
                        {
                            render: function (suminf, column, cell)
                            {
                                return ForDight(suminf.sum,2);
                            },
    						align: 'right'
                        }},
	            { display: '销卡产品',   		name: 'cardsalesprod',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '收购转卡服务',   	name: 'acquisitioncardservices',  		width:100,type: 'float'	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '积分服务',   		name: 'costpointtotal',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '现金抵用券服务',   name: 'cashdyservice',  	width:100	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '项目抵用券服务',   name: 'prjdyservice',  		width:100	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '销售条码卡服务',   	name: 'tmkservice',  		width:100	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
               { display: '赠送条码卡服务',   	name: 'tmksendservice',  		width:100	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '经理签单 ',   	name: 'managesigning',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '支出登记 ',   	name: 'payoutRegister',  	width:80	,type: 'float',align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }}
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

    	var strSearchType=document.getElementById("strSearchType").value;
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strSearchType="+strSearchType;			
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
     	var requestUrl ="sc001/loadDataSet.action"; 
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
