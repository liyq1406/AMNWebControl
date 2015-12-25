
   	var sc003layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var paymodemanager=null;
	var paymodeData=JSON.parse(parent.loadCommonControlDate_select("ZFFS",0));
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc003layout= $("#sc003layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc003layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '消费单号', 			name: 'costBillId', 	width:120	,align: 'left' },
                { display: '消费卡号', 			name: 'costCardNo', 	width:110	,align: 'left' },
                { display: '会员姓名', 			name: 'costMemberName', 	width:90	,align: 'left' },
                { display: '消费日期', 			name: 'costDate', 		width:80	,align: 'left' },
                { display: '消费时间', 			name: 'costTime',  		width:80	,align: 'left'},
	            { display: '产品/项目', 			name: 'costItemName', 	width:140	,align: 'left'},
	            { display: '支付方式',   			name: 'costPayMode',  	width:120	,align: 'left' ,
				       editor: { type: 'select', data: paymodeData, valueField: 'choose'},
					   		render: function (item)
					              	{
					              		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);
					              		for(var i=0;i<lsZw.length;i++)
										{
											if(lsZw[i].bparentcodekey==item.costPayMode)
											{	
												return lsZw[i].parentcodevalue;								
										    }
										}
					                    return '';
					                } 
	            },
	            { display: '大工工号',   			name: 'firstStaffNo',  	width:140	,align: 'left'},
	            { display: '中工工号',   			name: 'secondStaffNo',  width:140	,align: 'left'},
	            { display: '小工工号',   			name: 'thirthStaffNo',  width:140	,align: 'left'},
	            { display: '单价',   			name: 'costPrice',  	width:60	,align: 'right'},
	            { display: '数量',   			name: 'costCount',  	width:60	,align: 'right', 
	            	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '<div>' + suminf.sum + '</div>';
                        },
                        align: 'left'
                    }
	            },
	            { display: '金额',   			name: 'costAmt',  		width:60	,align: 'right', 
	            	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '<div>' + suminf.sum + '</div>';
                        },
                        align: 'left'
                    }
	            }
	             
	            ],  pageSize:50, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox: false,rownumbers: true,usePager: true
               
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询消费明细', width: 120,
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
			var paymodeData=JSON.parse(parent.loadCommonControlDate("ZFFS",0));
           	paymodemanager = $("#strPayMode").ligerComboBox({ data: paymodeData, isMultiSelect: false,valueFieldID: 'factStrPayMode',width:'130',selectBoxHeight:'220'});	
         
           
           
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
    	var strProjectNo=document.getElementById("strProjectNo").value;
    	var strGoodsNo=document.getElementById("strGoodsNo").value;
    	var strCardNo=document.getElementById("strCardNo").value;
    	var strPayMode=$("#factStrPayMode").val();
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strPayMode="+strPayMode;			
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
    	params=params+"&strProjectNo="+strProjectNo;	
    	params=params+"&strGoodsNo="+strGoodsNo;	
    	params=params+"&strCardNo="+strCardNo;	

     	var requestUrl ="sc003/loadDataSet.action"; 
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
				 	$.ligerDialog.warn("没有查到消费信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
