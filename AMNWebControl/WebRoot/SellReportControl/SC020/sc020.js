var dataLayout=null;
var dataGrid=null;
$(function (){
   try{
	  	//布局
	   dataLayout= $("#dataLayout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
       	var height = $(".l-layout-center").height();
       	var centerWidth = dataLayout.centerWidth;
       	$("#beginDate").ligerDateEditor({ labelWidth: 120,format: "yyyy-MM-dd", labelAlign: 'right',width:'120' });
       	$("#endDate").ligerDateEditor({ labelWidth: 120,format: "yyyy-MM-dd", labelAlign: 'right',width:'120' });
       	//表格
       	dataGrid=$("#dataGrid").ligerGrid({
            columns: [
            {display:'店号', name:'attr1', width:70, align:'left' },
            {display:'名称', name:'attr2', width:150, align:'left'},
            {display:'总客单数', name:'attr3', width:100, type:'int', align:'right'},
            {display:'现金客单总数', name:'attr10', width:100, type:'int', align:'right'},
            {display:'美容大项客单数', name:'attr4', width:100, type:'int', align:'right'},
            {display:'美容现金客单总数', name:'attr11', width:100, type:'int', align:'right'},
            {display:'美发大项客单数', name:'attr5', width:100, type:'int', align:'right'},
            {display:'美发现金客单总数', name:'attr12', width:100, type:'int', align:'right'},
            {display:'烫染护项目数', columns:
            	[
            	 	{display:'染发', name:'attr6', width:100, type:'int', align:'right'},
            	 	{display:'烫发', name:'attr7', width:100, type:'int', align:'right'},
            	 	{display:'护理', name:'attr8', width:100, type:'int', align:'right'},
					{display:'合计', name:'attr9', width:100, type:'int', align:'right'}
				]
            }],  pageSize:50, 
            data: null,      
            width: centerWidth,
            height:height-35,
            rownumbers:true, usePager:false
        });
        $("#searchButton").ligerButton({
             text: '查询', width: 120,
	         click: function (){
	        	var showDialog = $.ligerDialog.waitting('正在查询中,请稍候...');
	        	var url = contextURL+"/sc020/query.action"; 
	        	var strCurCompId=getCurOrgFromSearchBar();
        		var params = "strCurCompId="+strCurCompId;
        		params +="&beginDate="+$('#beginDate').val();
        		params +="&endDate="+$('#endDate').val();
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
             text: '转Excel', width: 120,
	         click: function (){
	        	var strCurCompId=getCurOrgFromSearchBar();
        		var params = "strCurCompId="+strCurCompId;
        		params +="&beginDate="+$('#beginDate').val();
        		params +="&endDate="+$('#endDate').val();
        		var excelDialog=$.ligerDialog.open({url: contextURL+'/sc020/loadExcel.action?'+params });
        		setTimeout(function () { excelDialog.close(); }, 10000);
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