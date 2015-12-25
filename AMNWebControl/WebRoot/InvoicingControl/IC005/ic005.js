
   	var ic005layout=null;
   	var commoninfodivGoodsBarStockDate=null;
	var showDialogmanager=null;
	var strCurGoodsNo="";
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            ic005layout= $("#ic005layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = ic005layout.centerWidth;
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' });
          	//--------销售条码卡
            commoninfodivGoodsBarStockDate=$("#commoninfodivGoodsBarStockDate").ligerGrid({
                columns: [
                { display: '物品大类', 			name: 'strGoodsClassName', 			width:100	,align: 'left' },
                { display: '物品编号', 			name: 'strGoodsNo', 			width:100	,align: 'left' },
                { display: '物品名称', 			name: 'strGoodsName', 			width:200	,align: 'left' },
                { display: '物品单位', 			name: 'strGoodsUnit', 			width:100	,align: 'left' },
                { display: '期初量', 				name: 'BBeginGoodsCount', 	 	width:80	,align: 'right'},
                { display: '金额', 				name: 'BBeginGoodsAmt',  		width:80	,align: 'right'},
	            { display: '入库量',   			name: 'BInserGoodsCount',  		width:80	,align: 'right'},
	            { display: '入库金额',   			name: 'BInserGoodsAmt',  		width:80	,align: 'right'},
	            { display: '赠送量',   			name: 'BHandSelGoodsCount',  	width:80	,align: 'right'},
	            { display: '赠送金额',   			name: 'BHandSelGoodsAmt',  		width:80	,align: 'right'},
	            { display: '退货量',   			name: 'BQuitGoodsCount',  		width:80	,align: 'right'},
	            { display: '退货金额',   			name: 'BQuitGoodsAmt',  		width:80	,align: 'right'},
	            { display: '出库量',   			name: 'BOuterGoodsCount',  		width:80	,align: 'right'},
	            { display: '出库金额',   			name: 'BOuterGoodsAmt',  		width:80	,align: 'right'},
	            { display: '出库成本',   			name: 'BCostGoodsAmt',  		width:80	,align: 'right'},
	            { display: '期末库存量',   		name: 'BEndGoodsCount',  		width:80	,align: 'right'},
	            { display: '期末金额',   			name: 'BEndGoodsAmt',  			width:80	,align: 'right'},
	            { display: '待发货量',   			name: 'BSendGoodsCount',  		width:80	,align: 'right'},
	            { display: '待发货金额',   		name: 'BSendGoodsAmt',  		width:80	,align: 'right'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox: false,rownumbers: false,usePager: true,
                onContextmenu : function (parm,e)
                {
                	 strCurGoodsNo=parm.data.strGoodsNo;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            
    		
              menu = $.ligerMenu({ width: 160, items:
	            [
	            	{ text: '重新结算选中产品', click: reloadGoodsStock, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/ok.gif'}
	            ]
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
	          $("#excelButton").ligerButton(
	         {
	             text: '转Excel', width: 100,
		         click: function ()
		         {
		             loadDataSetExcel();
		         }
	         });
	         
	        addOption("","",document.getElementById("searchGoodsType"));
           	var goodstypes=parent.gainCommonInfoByCode("WPTJ",0);
		    for(var i=0;i<goodstypes.length;i++)
			{
				addOption(goodstypes[i].bparentcodekey,goodstypes[i].parentcodevalue,document.getElementById("searchGoodsType"));
			}	
			
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
    	params=params+"&searchGoodsType="+document.getElementById("searchGoodsType").value;	
     	var requestUrl ="ic005/loadDataSet.action"; 
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
		
		function loadDataSetExcel()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    	
    		var params = "strCurCompId="+strCurCompId;	
    		params=params+"&strFromDate="+document.getElementById("strFromDate").value;
    		params=params+"&strToDate="+document.getElementById("strToDate").value;
    		params=params+"&strFromGoodsNo="+document.getElementById("strFromGoodsNo").value;
    		params=params+"&strToGoodsNo="+document.getElementById("strToGoodsNo").value;
    		params=params+"&searchGoodsType="+document.getElementById("searchGoodsType").value;	
			var excelDialog=$.ligerDialog.open({url: contextURL+'/ic005/loadic005Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
		}
	
		function reloadGoodsStock()
		{	
			var strCurCompId=getCurOrgFromSearchBar();
			var params = "strCurCompId="+strCurCompId;	
    		params=params+"&strCurGoodsNo="+strCurGoodsNo;
    		params=params+"&strFromDate="+document.getElementById("strFromDate").value;
    		params=params+"&strToDate="+document.getElementById("strToDate").value;
     		var requestUrl ="ic005/reloadGoodsStock.action"; 
			var responseMethod="reloadGoodsStockMessage";		
			showDialogmanager = $.ligerDialog.waitting('正在重新结算中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function reloadGoodsStockMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			var strMessage=responsetext.strMessage;
			if(checkNull(strMessage)=="")
			{	        		 
			    $.ligerDialog.success("结算成功!");
			}
			else
			{
			    $.ligerDialog.error(strMessage);
			}
			showDialogmanager.close();
		}
