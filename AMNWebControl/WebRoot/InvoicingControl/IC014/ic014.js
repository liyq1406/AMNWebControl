
   	var ic014layout=null;
   	var commoninfodivGoodsStockDate=null;
	var showDialogmanager=null;
	var commoninfodivGoodsPcDate =null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            ic014layout= $("#ic014layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = ic014layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivGoodsStockDate=$("#commoninfodivGoodsStockDate").ligerGrid({
                columns: [
                { display: '物品编号', 			name: 'strGoodsNo', 			width:100	,align: 'left' },
                { display: '物品名称', 			name: 'strGoodsName', 			width:180	,align: 'left' },
                { display: '物品类别', 			name: 'strGoodsTypeName', 		width:100	,align: 'left' },
                { display: '单位', 				name: 'strGoodsUnit', 	 		width:70	,align: 'left'},
                { display: '数量', 				name: 'BGoodsCount',  			width:70	,align: 'right'},
	            { display: '单价',   			name: 'BGoodsPrice',  			width:70	,align: 'right'},
	            { display: '金额',   			name: 'BGoodsAmt',  			width:70	,align: 'right'}
	             
	            ],  pageSize:20, 
                data: null,      
                width: 720,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: true,usePager:true,
              	onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }
               
            });
            
            commoninfodivGoodsPcDate=$("#commoninfodivGoodsPcDate").ligerGrid({
                columns: [
                { display: '批次编号', 			name: 'inserbillid', 		width:120	,align: 'left' },
                { display: '剩余数量', 			name: 'curlavecount', 		width:80	,align: 'left' },
                { display: '生效日期', 			name: 'producedate', 		width:100	,align: 'left' },
                { display: '过期日期', 			name: 'expireddate', 	 	width:100	,align: 'left'}
	             
	            ],  pageSize:20, 
                data: null,      
                width: 410,
                height:height-100,
                enabledEdit: false,  checkbox:false,rownumbers: false,usePager: false
               
            });
            
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询库存', width: 120,
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
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			document.getElementById("strToDate").value=today;
            $("#pageloading").hide(); 
            addtrade();
            addgoodspc();
   		}catch(e){alert(e.message);}
    });
    
    function  addtrade()
        {
        	var row = commoninfodivGoodsStockDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivGoodsStockDate.addRow({ 
				                inserbillid: "",
				                curlavecount: "",
				                producedate: "",
				                expireddate: ""
				             
				            }, row, false);
        }
        
      function  addgoodspc()
        {
        	var row = commoninfodivGoodsPcDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivGoodsPcDate.addRow({ 
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
    	params=params+"&strWareId="+document.getElementById("strWareId").value;	
    	params=params+"&strFromGoodsNo="+document.getElementById("strFromGoodsNo").value;
    	params=params+"&strToGoodsNo="+document.getElementById("strToGoodsNo").value;	
     	var requestUrl ="ic014/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivGoodsStockDate.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivGoodsStockDate.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到库存信息!");
		   			commoninfodivGoodsStockDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivGoodsStockDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
		
		
		 //根据主档选择加载明细
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        	var params = "strCurCompId="+getCurOrgFromSearchBar();	
    				params=params+"&strFromGoodsNo="+data.strGoodsNo
    				params=params+"&strToGoodsNo="+data.strGoodsNo;	
       				params=params+"&strWareId="+document.getElementById("strWareId").value;	
				 var myAjax = new parent.Ajax.Request(
						"ic014/loadPcDataSet.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsPcDataSet!=null && responsetext.lsPcDataSet.length>0)
						   		{
						   			commoninfodivGoodsPcDate.options.data=$.extend(true, {},{Rows: responsetext.lsPcDataSet,Total: responsetext.lsPcDataSet.length});
					            	commoninfodivGoodsPcDate.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivGoodsPcDate.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivGoodsPcDate.loadData(true);  
					            	addgoodspc();
						   		}
	   							
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
    }
    
    	
		function loadDataSetExcel()
		{
		var strCurCompId=getCurOrgFromSearchBar();
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCurCompId="+strCurCompId;			
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
    	params=params+"&strWareId="+document.getElementById("strWareId").value;	
    	params=params+"&strFromGoodsNo="+document.getElementById("strFromGoodsNo").value;
    	params=params+"&strToGoodsNo="+document.getElementById("strToGoodsNo").value;	
			var excelDialog=$.ligerDialog.open({url: contextURL+'/ic014/loadic014Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
		}
	
