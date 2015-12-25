var dataLayout=null;
var dataGrid=null;
$(function (){
   try{
	  	//布局
	   dataLayout= $("#dataLayout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
       	 height = $(".l-layout-center").height();
       	 centerWidth = dataLayout.centerWidth;
       	$("#beginDate").ligerDateEditor({ labelWidth: 120,format: "yyyy-MM-dd", labelAlign: 'right',width:'120' });
       	$("#endDate").ligerDateEditor({ labelWidth: 120,format: "yyyy-MM-dd", labelAlign: 'right',width:'120' });
       	//表格
       	dataGrid=$("#dataGrid").ligerGrid({
            columns: [
            {display:'店号', name:'attr1', width:100, align:'center' },
            {display:'店名', name:'attr2', width:150, align:'center' },
            {display:'单号', name:'attr3', width:150, align:'center'},
            {display:'订货日期', name:'attr4', width:150,  align:'center'},
            {display:'分单日期', name:'attr5', width:150, align:'center'},
            {display:'发货日期', name:'attr6', width:150, align:'center'},
            {display:'收货日期', name:'attr7', width:150, align:'center'},
            {display:'状态', name:'attr8', width:100, align:'center'}
            ],
            pageSize:30, 
            data: null,      
            width: centerWidth,
            height:height-35,
            rownumbers:true, usePager:true
        });
        $("#searchButton").ligerButton({
             text: '查询', width: 80,
	         click: function (){
	        	var showDialog = $.ligerDialog.waitting('正在查询中,请稍候...');
	        	var url = contextURL+"/ic020/query.action"; 
	        	var strCurCompId=getCurOrgFromSearchBar();
        		var params = "strCurCompId="+strCurCompId;
        		params +="&beginDate="+$('#beginDate').val();
        		params +="&endDate="+$('#endDate').val();
        		params +="&projectKind="+$('#projectKind').val();
        		params +="&status="+$('#status').val();
        		$.post(url, params, function(data){
        			showDialog.close();
        			var list = data.dataSet;
        			if(list != null && list.length>0){
        				dataGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
        			}else{
        				$.ligerDialog.warn("没有查到数据信息!");
        				dataGrid.options.data=$.extend(true, {},{Rows: null,Total: 0});
        			}
        			dataGrid.loadData(true);
        		}).error(function(e){$.ligerDialog.error("获取数据失败，请重试！");});
	         }
         });
        $("#excelButton").ligerButton({
             text: '转Excel', width: 80,
	         click: function (){
	        	var strCurCompId=getCurOrgFromSearchBar();
        		var params = "strCurCompId="+strCurCompId;
        		params +="&beginDate="+$('#beginDate').val();
        		params +="&endDate="+$('#endDate').val();
        		params +="&projectKind="+$('#projectKind').val();
        		params +="&status="+$('#status').val();
        		var excelDialog=$.ligerDialog.open({url: contextURL+'/ic020/loadExcel.action?'+params });
        		setTimeout(function () { excelDialog.close(); }, 15000);
	         }
         });
        var today = new Date();
		var intYear=today.getFullYear();
		var intMonth=today.getMonth()+1;
		var intDay=today.getDate();
		var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
		
		$('#beginDate').val(today);
		$("#endDate").val(today);
        $("#pageloading").hide(); 
	}catch(e){alert(e.message);}
});