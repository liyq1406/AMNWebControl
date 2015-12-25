var dataLayout=null;
var dataGrid=null;
var goodsComboBox=null;
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
            {display:'店号 ', name:'attr1', width:100, align:'center' },
            {display:'店名', name:'attr9', width:150, align:'center' },
            {display:'单号', name:'attr2', width:300, align:'center' },
            {display:'产品', name:'attr8', width:150, align:'center' },
            {display:'数量', name:'attr3', width:150, align:'center'},
            {display:'单价', name:'attr4', width:150,  align:'center'},
            {display:'厂家结算金额', name:'attr5', width:150, align:'center'},
            {display:'门店单价', name:'attr6', width:150, align:'center'},
            {display:'门店结算金额', name:'attr7', width:150, align:'center'},
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
	        	var url = contextURL+"/ic022/query.action"; 
	        	var strCurCompId=getCurOrgFromSearchBar();
        		var params = "strCurCompId="+strCurCompId;
        		params +="&beginDate="+$('#beginDate').val();
        		params +="&endDate="+$('#endDate').val();
        		params +="&projectKind="+goodsComboBox.getValue().replace(/\;/g,"','");
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
        		params +="&projectKind="+goodsComboBox.getValue().replace(/\;/g,"','");
        		var excelDialog=$.ligerDialog.open({url: contextURL+'/ic022/loadExcel.action?'+params });
        		setTimeout(function () { excelDialog.close(); }, 45000);
	         }
         });
        $.post((contextURL+"/ic022/loadGoods.action"), null, function(data){
			var list = data.goodsList;
			if(list == null || list.length==0){
				$.ligerDialog.warn("加载产品数据失败，请重试！!");
			}
			goodsComboBox=$("#projectKind").ligerComboBox({
	            width: 250, slide: false,
	            selectBoxWidth: 410, selectBoxHeight: 240,
	            valueField: 'attr1', textField: 'attr2',
	            grid: {
	                columns: [
	                { display: '产品编号', name: 'attr1', align: 'left', width: 100, minWidth: 60 }, 
	                { display: '产品名称', name: 'attr2', width: 200 }
	                ], switchPageSizeApplyComboBox: false,usePager: false,
	                data: $.extend(true, {},{Rows: list,Total: list.length}),pageSize: 30,checkbox: true
	            }
	        });
		}).error(function(e){$.ligerDialog.error("获取数据失败，请重试！");});
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