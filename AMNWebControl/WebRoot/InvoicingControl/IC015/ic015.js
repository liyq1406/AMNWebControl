
   	var ic015layout=null;
   	var commoninfodivGoodsBarStockDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            ic015layout= $("#ic015layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = ic015layout.centerWidth;
           //--------销售条码卡
            commoninfodivGoodsBarStockDate=$("#commoninfodivGoodsBarStockDate").ligerGrid({
                columns: [
                { display: '物品编号', 			name: 'bgoodsno', 			width:100	,align: 'left' },
                { display: '物品名称', 			name: 'goodsText', 			width:140	,align: 'left' },
                { display: '唯一条码', 			name: 'bgoodsbarno', 		width:100	,align: 'left' },
                { display: '条码状态', 			name: 'barnostateText', 	 	width:60	,align: 'left'},
                { display: '生成日期', 			name: 'createdate',  		width:90	,align: 'left'},
	            { display: '操作者',   			name: 'createstaffno',  	width:60	,align: 'left'},
	            { display: '入库日期',   			name: 'inserdate',  		width:90	,align: 'left'},
	            { display: '入库单号',   			name: 'inserbillno',  		width:120	,align: 'left'},
	            { display: '发货/出库日期',   		name: 'outerdate',  		width:90	,align: 'left'},
	            { display: '发货/出库单号',   		name: 'outerbill',  		width:130	,align: 'left'},
	            { display: '收货门店',   			name: 'receivestore',  		width:60	,align: 'left'},
	            { display: '消费日期/消耗日期',    name: 'costdate',  			width:110	,align: 'left'},
	            { display: '消费单号/消耗单号',   	name: 'costbillo',  		width:110	,align: 'left'},
	            { display: '消费门店',   			name: 'coststore',  		width:70	,align: 'left'},
	            { display: '生产日期',   			name: 'proddate',  			width:70	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: true,usePager: true
               
            });
            
    
            
        
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询库存', width: 80,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
            
            $("#addProdDateButton").ligerButton(
       	         {
       	             text: '增加生成日期', width: 80,
       		         click: function ()
       		         {
       		        	$.ligerDialog.open({height:600,width: 800,title : '增加生成日期',url: 'addProdDate.jsp',showMax: false,showToggle: false,showMin:false,isResize: true,slide: false});
       		         }
       	         });
	       
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
    	params=params+"&strGoodsNo="+document.getElementById("strGoodsNo").value;
    	params=params+"&strGoodsBarNo="+document.getElementById("strGoodsBarNo").value;	
    	params=params+"&iBarState="+document.getElementById("iBarState").value;	
     	var requestUrl ="ic015/loadDataSet.action"; 
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
				 	$.ligerDialog.warn("没有查到条码信息!");
		   			commoninfodivGoodsBarStockDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivGoodsBarStockDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
		
	
