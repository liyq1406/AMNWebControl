
   	var ic013layout=null;
   	var commoninfodivGoodsInserDate=null;
   	var commoninfodivGoodsInserTypeDate=null;
	var showDialogmanager=null;
	var IC013Tab=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            ic013layout= $("#ic013layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = ic013layout.centerWidth;
           	$("#IC013Tab").ligerTab();
            IC013Tab = $("#IC013Tab").ligerGetTabManager();
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
          	//--------销售条码卡
            commoninfodivGoodsInserDate=$("#commoninfodivGoodsInserDate").ligerGrid({
                columns: [
                { display: '物品编号', 			name: 'strGoodsId', 			width:100	,align: 'left' },
                { display: '物品名称', 			name: 'strGoodsName', 			width:240	,align: 'left' },
                { display: '单位', 				name: 'strGoodsUnit', 			width:60	,align: 'left' },
                { display: '物品类别', 			name: 'strGoodsType', 	 		width:100	,align: 'left'},
                { display: '入库日期', 			name: 'strInserDate',  			width:100	,align: 'left'},
                { display: '入库方式', 			name: 'strInserOptionName',  	width:100	,align: 'left'},
	            { display: '入库单号',   			name: 'strInserBillId',  		width:140	,align: 'left'},
	            { display: '供应商名称',   		name: 'strInserSuperName',  	width:110	,align: 'left'},
	            { display: '入库数量',   			name: 'BInserCount',  			width:80	,align: 'right'},
	            { display: '入库单价',   			name: 'BInserPrice',  			width:80	,align: 'right'},
	            { display: '入库金额',   			name: 'BInserAmt',  			width:80	,align: 'right'},
	            { display: '备注',   			name: 'strMark',  				width:120	,align: 'right'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: false,usePager: true
               
            });
    		commoninfodivGoodsInserTypeDate=$("#commoninfodivGoodsInserTypeDate").ligerGrid({
                columns: [
                { display: '入库日期', 			name: 'strInserDate', 			width:100	,align: 'left' }
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: false,usePager: true
               
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
	             text: '导出', width: 60,
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
            addinsertypetrade();
            f_setColumns();
   		}catch(e){alert(e.message);}
    });
    
    
     function f_setColumns()
     { 
     		var showColumns='['; 
     		showColumns+= '{ "display": "入库日期", "name": "strInserDate", "width":"100"	,"align":"left" }';
			var WPTJ=parent.gainCommonInfoByCode("WPTJ",0);
		    for(var i=0;i<WPTJ.length;i++)
			{	
				showColumns+=',{ "display": "'+WPTJ[i].parentcodevalue+'",  "name": "'+WPTJ[i].bparentcodekey+'Amt","width":"80","align":"right" }';
			}			
            showColumns+=']';
            commoninfodivGoodsInserTypeDate.set('columns', JSON.parse(showColumns)); 
            commoninfodivGoodsInserTypeDate.reRender();
     }
     
   	 	function  addinsertrade()
        {
        	var row = commoninfodivGoodsInserDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivGoodsInserDate.addRow({ 
				             
				            }, row, false);
        }
        
        function  addinsertypetrade()
        {
        	var row = commoninfodivGoodsInserTypeDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivGoodsInserTypeDate.addRow({ 
				             
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
		var excelDialog=$.ligerDialog.open({url: contextURL+'/ic013/loadic013Excel.action?'+params });
    	setTimeout(function () { excelDialog.close(); }, 10000);
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
     	var requestUrl ="ic013/loadDataSet.action"; 
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
				 	$.ligerDialog.warn("没有查到入库信息!");
		   			commoninfodivGoodsInserDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivGoodsInserDate.loadData(true);
	            	addinsertrade(); 
			}
			if(responsetext.lsTypeDataSet!=null && responsetext.lsTypeDataSet.length>0)
           	{
            		var curdate=loadPrjData(responsetext.lsTypeDataSet);
            		commoninfodivGoodsInserTypeDate.options.data=$.extend(true, {},curdate);
            		commoninfodivGoodsInserTypeDate.loadData(true);
            }
			else
			{
				 	commoninfodivGoodsInserTypeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivGoodsInserTypeDate.loadData(true);
	            	addinsertypetrade(); 
			}
			showDialogmanager.close();
		}
		
		function loadPrjData(lsDataSet)
		{
		try
		{
				var pjDataJson="";
				for(var i=0;i<lsDataSet.length;i++)
				{
					if(pjDataJson=="")
					{
						pjDataJson=pjDataJson+'[';
					}
					else
					{
						pjDataJson=pjDataJson+',';
					}
					pjDataJson=pjDataJson+'{"strInserDate": "'+lsDataSet[i].strInserDate+'"';
					var WPTJ=parent.gainCommonInfoByCode("WPTJ",0);
				    for(var j=0;j<WPTJ.length;j++)
					{	
						pjDataJson+=',  "'+WPTJ[j].bparentcodekey+'Amt":"'+ForDight(lsDataSet[i].goodsInserTypesAmt[j][0],2)+'" ';
					}
					pjDataJson=pjDataJson+'}';
					
				}
				if(pjDataJson!="")
				{
						pjDataJson=pjDataJson+']';
						return {Rows:JSON.parse(pjDataJson),Total: lsDataSet.length };
				}
			}
			catch(e){alert(e.message);}
				
		}
	
