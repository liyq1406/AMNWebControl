var sc018layout=null;
var commoninfodivTradeDate=null;
var showDialogmanager=null;
$(function ()
{
   try
   {
	  	//布局
        sc018layout= $("#sc018layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
       	var height = $(".l-layout-center").height();
       	var centerWidth = sc018layout.centerWidth;
       	$("#strDate").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'120' });
       	//积分表格
        commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
            columns: [
            {display:'店编号', frozen:true, name:'compid', width:70, align:'left' },
            {display:'店名称', frozen:true, name:'compname', width:120, align:'left'},
            {display:'日期', frozen:true, name:'dqny', width:100, align:'left'},
            {display:'耗卡率', frozen:true, name:'hkl', width:120, align:'right'},
            {display:'门店会员', columns:
            	[
            	 	{display:'客单量', name:'kdzs', width:70, align:'right'},
            	 	{display:'虚客单价', name:'xkdj', width:70, align:'right'},
            	 	{display:'实客单价', name:'skdj', width:70, align:'right'},
					{display:'新增会员', name:'mdhy_xzhy', width:70, align:'right'},
					{display:'总会员量', name:'mdhy_hyzs', width:70, align:'right'},
					{display:'常到会员', name:'mdhy_cdhy', width:70, align:'right'},
					{display:'有效会员', name:'mdhy_yxhy', width:70, align:'right'},
					{display:'沉睡会员', name:'mdhy_cshy', width:70, align:'right'},
					{display:'流失会员', name:'mdhy_lshy', width:70, align:'right'}
				]
            },
            {display:'美容疗程', columns:
            	[
            	 {display:'客单量', name:'mr_kdzs', width:70, align:'right'},
            	 {display:'虚客单价', name:'mr_xkdj', width:70, align:'right'},
            	 {display:'实客单价', name:'mr_skdj', width:70, align:'right'},
            	 {display:'会员量', name:'mrhy_hyzs', width:70, align:'right'},
            	 {display:'新增会员', name:'mrhy_xzhy', width:70, align:'right'},
            	 {display:'常到会员', name:'mrhy_cdhy', width:70, align:'right'},
            	 {display:'有效会员', name:'mrhy_yxhy', width:70, align:'right'},
            	 {display:'沉睡会员', name:'mrhy_cshy', width:70, align:'right'},
            	 {display:'流失会员', name:'mrhy_lshy', width:70, align:'right'}
            	 ]
            },
            {display:'美发疗程', columns:
            	[
            	 {display:'客单量', name:'mf_kdzs', width:70, align:'right'},
            	 {display:'虚客单价', name:'mf_xkdj', width:70, align:'right'},
            	 {display:'实客单价', name:'mf_skdj', width:70, align:'right'},
            	 {display:'会员量', name:'mfhy_hyzs', width:70, align:'right'},
            	 {display:'新增会员', name:'mfhy_xzhy', width:70, align:'right'},
            	 {display:'常到会员', name:'mfhy_cdhy', width:70, align:'right'},
            	 {display:'有效会员', name:'mfhy_yxhy', width:70, align:'right'},
            	 {display:'沉睡会员', name:'mfhy_cshy', width:70, align:'right'},
            	 {display:'流失会员', name:'mfhy_lshy', width:70, align:'right'}
            	 ]
            }
            ],  pageSize:50, 
            data: null,      
            width: centerWidth,
            height:height-35,
            enabledEdit: true,  checkbox:false,rownumbers: true,usePager: true
           
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
             text: '转Excel', width: 120,
	         click: function ()
	         {
	             loadDataSetExcel();
	         }
         });
        var today = new Date();
		var intYear=today.getFullYear();
		var intMonth=today.getMonth()+1;
		var today = intYear.toString()+"-"+fullStr(intMonth.toString());
		$('#strDate').val(today);
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
	params +="&strDate="+$('#strDate').val();	
 	var requestUrl ="sc018/loadDataSet.action"; 
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
	 	$.ligerDialog.warn("没有查到运营分析信息!");
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
	params +="&strDate="+$('#strDate').val();
	var excelDialog=$.ligerDialog.open({url: contextURL+'/sc018/loadSC018Excel.action?'+params });
	setTimeout(function () { excelDialog.close(); }, 10000);
}
	
