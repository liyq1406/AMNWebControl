
   	var ic018layout=null;
   	var commoninfodivGoodsInserDate=null;
   	var commoninfodivGoodsInserTypeDate=null;
	var showDialogmanager=null;
	var IC018Tab=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            ic018layout= $("#ic018layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = ic018layout.centerWidth;
           	$("#IC018Tab").ligerTab();
            IC018Tab = $("#IC018Tab").ligerGetTabManager();
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' });
          	//--------销售条码卡
            commoninfodivGoodsInserDate=$("#commoninfodivGoodsInserDate").ligerGrid({
                columns: [
                { display: '物品编号', 			name: 'returngoodsno', 			width:100	,align: 'left' },
                { display: '物品名称', 			name: 'returngoodsname', 			width:240	,align: 'left' },
                { display: '单位', 				name: 'returngoodsunit', 			width:60	,align: 'left' },
                { display: '物品类别', 			name: 'strGoodsType', 	 		width:100	,align: 'left'},
                { display: '退货日期', 			name: 'returndate',  			width:100	,align: 'left'},
                { display: '方向', 				name: 'returntypename',  		width:50	,align: 'left'},
                { display: '仓库编号', 			name: 'revicestoreno',  		width:80	,align: 'left'},
	            { display: '退货单号',   			name: 'breturnbillid',  		width:140	,align: 'left'},
	            { display: '门店',   			name: 'breturncompid',  		width:70	,align: 'left'},
	            { display: '门店名称',   			name: 'breturncompname',  		width:110	,align: 'left'},
	            { display: '数量',   			name: 'factreturncount',  			width:40	,align: 'right'},
	            { display: '退货单价',   			name: 'factreturnprice',  			width:70	,align: 'right'},
	            { display: '退货金额',   			name: 'factreturnamt',  			width:70	,align: 'right'},
	            { display: '成本单价',   			name: 'costreturnprice',  		width:70	,align: 'right'},
	            { display: '成本金额',   			name: 'costreturnamt',  		width:70	,align: 'right'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: false,usePager: true
               
            });
            
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询', width: 120,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	          $("#excelButton").ligerButton(
	         {
	             text: 'Excel导出', width: 120,
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
            addinsertrade();
   		}catch(e){alert(e.message);}
    });
    
    
     
   	 	function  addinsertrade()
        {
        	var row = commoninfodivGoodsInserDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivGoodsInserDate.addRow({ 
				             
				            }, row, false);
        }
        
        

    


	function loadDataSetExcel()
	{
		var strCurCompId=getCurOrgFromSearchBar();
		var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromDate="+document.getElementById("strFromDate").value;
    	params=params+"&strToDate="+document.getElementById("strToDate").value;
    	params=params+"&strFromGoodsNo="+document.getElementById("strFromGoodsNo").value;
    	params=params+"&strToGoodsNo="+document.getElementById("strToGoodsNo").value;
		var excelDialog=$.ligerDialog.open({url: contextURL+'/ic018/loadic018Excel.action?'+params });
    	setTimeout(function () { excelDialog.close(); }, 10000);
	}
		
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();
    	
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromDate="+document.getElementById("strFromDate").value;
    	params=params+"&strToDate="+document.getElementById("strToDate").value;
    	params=params+"&strFromGoodsNo="+document.getElementById("strFromGoodsNo").value;
    	params=params+"&strToGoodsNo="+document.getElementById("strToGoodsNo").value;;
     	var requestUrl ="ic018/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivGoodsInserDate.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivGoodsInserDate.loadData(true);
            }
			else
			{
				 	commoninfodivGoodsInserDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivGoodsInserDate.loadData(true);
	            	addinsertrade(); 
			}
			
			showDialogmanager.close();
		}
		
		