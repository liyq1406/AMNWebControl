
   	var ic006layout=null;
   	var commoninfodivGoodsBarStockDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            ic006layout= $("#ic006layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = ic006layout.centerWidth;
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
          	//--------销售条码卡
            commoninfodivGoodsBarStockDate=$("#commoninfodivGoodsBarStockDate").ligerGrid({
                columns: [
                { display: '物品编号', 			name: 'strGoodsNo', 			width:100	,align: 'left' },
                { display: '物品名称', 			name: 'strGoodsName', 			width:240	,align: 'left' },
                { display: '仓库编号', 			name: 'strWareId', 				width:100	,align: 'left' },
                { display: '仓库名称', 			name: 'strWareName', 			width:100	,align: 'left' },
                { display: '异动日期', 			name: 'strChangeDate', 	 		width:90	,align: 'left'},
                { display: '异动单号',   			name: 'strChangeBillNo',  		width:135	,align: 'left'},
	            { display: '异动类别',   			name: 'strChangeType',  		width:90	,align: 'right'},
	            { display: '入库数量',   			name: 'BInserGoodsCount',  		width:90	,align: 'right'},
	            { display: '出库数量',   			name: 'BOuterGoodsCount',  		width:90	,align: 'right'},
	            { display: '库存量',   			name: 'BCurGoodsCount',  		width:90	,align: 'right'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: true,usePager: true
               
            });
            
    
            
        
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询', width: 60,
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
        	var row = commoninfodivGoodsBarStockDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivGoodsBarStockDate.addRow({ 
				                inserbillid: "",
				                curlavecount: "",
				                producedate: "",
				                expireddate: ""
				             
				            }, row, false);
        }

    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();
    	
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromDate="+document.getElementById("strFromDate").value;
    	params=params+"&strToDate="+document.getElementById("strToDate").value;
    	params=params+"&strFromGoodsNo="+document.getElementById("strFromGoodsNo").value;
    	params=params+"&strToGoodsNo="+document.getElementById("strToGoodsNo").value;
    	params=params+"&strWareId="+document.getElementById("strWareId").value;	
     	var requestUrl ="ic006/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivGoodsBarStockDate.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivGoodsBarStockDate.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到库存异动信息!");
		   			commoninfodivGoodsBarStockDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivGoodsBarStockDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
		
	
