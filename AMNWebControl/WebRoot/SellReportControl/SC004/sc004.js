
   	var sc004layout=null;
   	var SC004Tab = null;
   	var commoninfodivData1=null;
   	var commoninfodivData2=null;
   	var commoninfodivData3=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc004layout= $("#sc004layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc004layout.centerWidth
       		
          	$("#SC004Tab").ligerTab();
            SC004Tab = $("#SC004Tab").ligerGetTabManager();
            
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
          
           commoninfodivData1=$("#commoninfodivData1").ligerGrid({
                columns: [
                  { display: '消费门店', 			name: 'costCompId', 		width:100	,align: 'left' },
                { display: '会员卡号', 			name: 'costCardNo', 		width:150	,align: 'left' },
                { display: '卡类型', 				name: 'costCardType', 		width:200	,align: 'left' },
                { display: '卡归属门店', 			name: 'homeCompId',  		width:180	,align: 'left' },
	            { display: '消费金额', 			name: 'costAmt', 	 		width:100	,align: 'right' ,  
	            	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '<div>' + ForDight(suminf.sum,2) + '</div>';
                        },
                        align: 'left'
                    }
	            }
	            ],  pageSize:20, 
                data: null,     
                width: 800,
                height:'600',
                enabledEdit: true,  checkbox:false,rownumbers: false,usePager: true
               
            });
            
            commoninfodivData2=$("#commoninfodivData2").ligerGrid({
                columns: [
                { display: '消费门店', 			name: 'costCompId', 		width:100	,align: 'left' },
                { display: '会员卡号', 			name: 'costCardNo', 		width:150	,align: 'left' },
                { display: '卡类型', 				name: 'costCardType', 		width:200	,align: 'left' },
                { display: '卡归属门店', 			name: 'homeCompId',  		width:180	,align: 'left' },
	            { display: '消费金额', 			name: 'costAmt', 	 		width:100	,align: 'right' , 
	            	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                              return '<div>' + ForDight(suminf.sum,2) + '</div>';
                        },
                        align: 'left'
                    }
	            }
	            ],  pageSize:20, 
                data: null,    
                width: 800,
                height:'600',
                enabledEdit: true,  checkbox:false,rownumbers: false,usePager: true
               
            });
            
           commoninfodivData3=$("#commoninfodivData3").ligerGrid({
                columns: [
                { display: '消费门店', 			name: 'costCompId', 		width:100	,align: 'left' },
                { display: '会员卡号', 			name: 'costCardNo', 		width:150	,align: 'left' },
                { display: '卡类型', 				name: 'costCardType', 		width:200	,align: 'left' },
                { display: '卡归属门店', 			name: 'homeCompId',  		width:180	,align: 'left' },
	            { display: '消费金额', 			name: 'costAmt', 	 		width:100	,align: 'right' , 
	            	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                                    return '<div>' + ForDight(suminf.sum,2) + '</div>';
                        },
                        align: 'left'
                    }
	            }
	            ],  pageSize:20, 
                data: null,     
                width: 800,
                height:'600',
                enabledEdit: true,  checkbox:false,rownumbers: false,usePager: true
               
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
            addCostData1();
            addCostData2();
            addCostData3();
   		}catch(e){alert(e.message);}
    });
    
    function  addCostData1()
   	{
        	var row = commoninfodivData1.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivData1.addRow({ 
				                shopId: "",
				                shopName: "",
				                dateReport: ""
				             
				            }, row, false);
    }
    
    function  addCostData2()
   	{
        	var row = commoninfodivData2.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivData2.addRow({ 
				                shopId: "",
				                shopName: "",
				                dateReport: ""
				             
				            }, row, false);
    }
    
    function  addCostData3()
   	{
        	var row = commoninfodivData3.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivData3.addRow({ 
				                shopId: "",
				                shopName: "",
				                dateReport: ""
				             
				            }, row, false);
    }
    
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();
		var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCurCompId="+strCurCompId;				
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
     	var requestUrl ="sc004/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSetA!=null && responsetext.lsDataSetA.length>0)
           	{
            		commoninfodivData1.options.data=$.extend(true, {},{Rows: responsetext.lsDataSetA,Total: responsetext.lsDataSetA.length});
            		commoninfodivData1.loadData(true);
            }
			else
			{
				 	commoninfodivData1.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivData1.loadData(true);
	            	addCostData1(); 
			}
			if(responsetext.lsDataSetB!=null && responsetext.lsDataSetB.length>0)
           	{
            		commoninfodivData2.options.data=$.extend(true, {},{Rows: responsetext.lsDataSetB,Total: responsetext.lsDataSetB.length});
            		commoninfodivData2.loadData(true);
            }
			else
			{
				 	commoninfodivData2.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivData2.loadData(true);
	            	addCostData2(); 
			}
			if(responsetext.lsDataSetC!=null && responsetext.lsDataSetC.length>0)
           	{
            		commoninfodivData3.options.data=$.extend(true, {},{Rows: responsetext.lsDataSetC,Total: responsetext.lsDataSetC.length});
            		commoninfodivData3.loadData(true);
            }
			else
			{
				 	commoninfodivData3.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivData3.loadData(true);
	            	addCostData3(); 
			}
			showDialogmanager.close();
		}
