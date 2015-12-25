var pc024layout=null;
var commoninfodivTradeDate=null;
var showDialogmanager=null;
$(function ()
{
   try
   {
	  	//布局
        pc024layout= $("#pc024layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
       	var height = $(".l-layout-center").height();
       	var centerWidth = pc024layout.centerWidth;
       	//积分表格
        commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
            columns: [
			{display:'门店编号', name:'compno', width:110, align:'left' },
			{display:'门店名称', name:'compname', width:110, align:'left'},
            {display:'员工编号', name:'staffno', width:110, align:'left' },
            {display:'员工姓名', name:'staffname', width:110, align:'left'},
            {display:'员工职位', name:'position', width:140, align:'left'},
            {display:'积分', name:'credit', width:110, type: 'int', align:'left'}
            ],  pageSize:50, 
            data: null,      
            width: centerWidth,
            height:height-35,
            enabledEdit:false, checkbox:false, rownumbers:false, usePager:true,
            detail: {onShowDetail: showDetailinfo}
        });
        document.getElementById("showTable").width=centerWidth;
        $("#searchButton").ligerButton(
         {
             text: '查询积分', width: 120,
	         click: function ()
	         {
	             loadDataSet();
	         }
         });
        $("#excelButton").ligerButton(
         {
             text: '转Excel', width: 120,
	         click: function ()
	         {
	             loadDataSetExcel();
	         }
         });
        $("#pageloading").hide(); 
        addtrade();
	}catch(e){alert(e.message);}
});

function addtrade()
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
//加载数据集
function loadDataSet()
{	
	var strCurCompId=getCurOrgFromSearchBar();
	var params = "strCurCompId="+strCurCompId;		
 	var requestUrl ="pc024/loadDataSet.action"; 
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
	 	$.ligerDialog.warn("没有查到员工积分信息!");
		commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
    	commoninfodivTradeDate.loadData(true);
    	addtrade(); 
	}
	showDialogmanager.close();
}
	
function loadDataSetExcel()
{
	var strCurCompId=getCurOrgFromSearchBar();
	var params = "strCurCompId="+strCurCompId;		
	var excelDialog=$.ligerDialog.open({url: contextURL+'/pc024/loadPC024Excel.action?'+params });
	setTimeout(function () { excelDialog.close(); }, 15000);
}

function showDetailinfo(row, detailPanel, callback){
	if(checkNull(row.staffno)==""){
		return;
	}
	var params = {strSearchType: row.staffno};
	var url = contextURL+"/pc024/loadDetail.action"; 
	$.post(url, params, function(dataSet){
		var detailGrid = document.createElement('div'); 
        $(detailPanel).append(detailGrid);
        $(detailGrid).css('margin','10').ligerGrid({
            columns:[
				{display:'业绩分类', name:'classify', width:300, align:'left'},
	            {display:'业绩数量', name:'number', width:110, type: 'int', align:'left' },
	            {display:'计量单位', name:'unit', width:110, align:'left'},
	            {display:'积分数', name:'credit', width:110, type: 'int', align:'left'}
             ], isScroll: false, showToggleColBtn: false, width: '95%',  height:100,
             data:{Rows: dataSet.lsDataSet,Total: dataSet.lsDataSet.length}, 
             showTitle: false, rownumbers:false,columnWidth: 100, 
             onAfterShowData: callback,frozen:false,usePager:false
        });
	}).error(function(e){$.ligerDialog.error(e.message);});
}
	
