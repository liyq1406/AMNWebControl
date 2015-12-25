
   	var sc006layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc006layout= $("#sc006layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc006layout.centerWidth
       		$("#strFromDate").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM", labelWidth:80, labelAlign: 'right' })
			//--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店',frozen:true, columns:
	            	[
                		{ display: '门店', 			name: 'strCompId', 			width:60	,align: 'left',frozen:true},
                		{ display: '名称', 			name: 'strCompName', 			width:110	,align: 'left',frozen:true },
                		{ display: '月份', 			name: 'strMonth', 		width:60	,align: 'left',frozen:true }
    				]
                },
                { display: '售卡业绩', columns:
	                [
	                	 { display: '本月', 			name: 'totalAmt',  			width:70	,align: 'left'},
	                	 { display: '去年同期', 		name: 'totalAmtA',  			width:70	,align: 'left'},
	                	 { display: '同比', 			name: 'totalAmtRateAText',  			width:70	,align: 'left'},
	                	 { display: '上月同期', 		name: 'totalAmtB',  			width:70	,align: 'left'},
	                	 { display: '环比', 			name: 'totalAmtRateBText',  			width:70	,align: 'left'}
	                ]
                },
                { display: '实业绩', columns:
	                [
	                	 { display: '本月', 			name: 'totalFactAmt',  			width:70	,align: 'left'},
	                	 { display: '去年同期', 		name: 'totalFactAmtA',  			width:70	,align: 'left'},
	                	 { display: '同比', 			name: 'totalFactAmtRateAText',  			width:70	,align: 'left'},
	                	 { display: '上月同期', 		name: 'totalFactAmtB',  			width:70	,align: 'left'},
	                	 { display: '环比', 			name: 'totalFactAmtRateBText',  			width:70	,align: 'left'}
	                ]
                },
                { display: '卡异动', columns:
	                [
	                	 { display: '本月', 			name: 'saleCardAmt',  			width:70	,align: 'left'},
	                	 { display: '去年同期', 		name: 'saleCardAmtA',  			width:70	,align: 'left'},
	                	 { display: '同比', 			name: 'saleCardAmtRateAText',  			width:70	,align: 'left'},
	                	 { display: '上月同期', 		name: 'saleCardAmtB',  			width:70	,align: 'left'},
	                	 { display: '环比', 			name: 'saleCardAmtRateBText',  			width:70	,align: 'left'}
	                ]
                },
                { display: '销卡', columns:
	                [
	                	 { display: '本月', 			name: 'pinCardAmt',  			width:70	,align: 'left'},
	                	 { display: '去年同期', 		name: 'pinCardAmtA',  			width:70	,align: 'left'},
	                	 { display: '同比', 			name: 'pinCardAmtRateAText',  			width:70	,align: 'left'},
	                	 { display: '上月同期', 		name: 'pinCardAmtB',  			width:70	,align: 'left'},
	                	 { display: '环比', 			name: 'pinCardAmtRateBText',  			width:70	,align: 'left'}
	                ]
                },
                { display: '售产品', columns:
	                [
	                	 { display: '本月', 			name: 'buyGoodsAmt',  			width:70	,align: 'left'},
	                	 { display: '去年同期', 		name: 'buyGoodsAmtA',  			width:70	,align: 'left'},
	                	 { display: '同比', 			name: 'buyGoodsAmtRateAText',  			width:70	,align: 'left'},
	                	 { display: '上月同期', 		name: 'buyGoodsAmtB',  			width:70	,align: 'left'},
	                	 { display: '环比', 			name: 'buyGoodsAmtRateBText',  			width:70	,align: 'left'}
	                ]
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
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
	
			var today = intYear.toString()+"-"+fullStr(intMonth.toString());
			document.getElementById("strFromDate").value=today;
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
    	var params = "strCurCompId="+strCurCompId;		
    	params=params+"&strFromDate="+strFromDate;	
     	var requestUrl ="sc006/loadDataSet.action"; 
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
			showDialogmanager.close();
		}
