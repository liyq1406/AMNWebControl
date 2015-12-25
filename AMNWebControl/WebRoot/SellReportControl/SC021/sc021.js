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
            {display:'编号', name:'cscompid', width:100, align:'center' },
            {display:'门店', name:'compname', width:150, align:'center' },
            {display:'日期', name:'RQ', width:200, align:'center'},
            {display:'疗程人数', name:'nb_card_count', width:100,  align:'center'},
            {display:'总客单量', name:'zkdl', width:100, align:'center'},
            {display:'总实业绩', name:'zsyj', width:100, align:'center'}
            ],
            pageSize:50, 
            data: null,      
            width: centerWidth,
            height:height-35,
            rownumbers:true, usePager:false
        });
        $("#searchButton").ligerButton({
             text: '查询', width: 120,
	         click: function (){
	        	var showDialog = $.ligerDialog.waitting('正在查询中,请稍候...');
	        	var url = contextURL+"/sc021/query.action"; 
	        	var strCurCompId=getCurOrgFromSearchBar();
        		var params = "strCurCompId="+strCurCompId;
        		params +="&beginDate="+$('#beginDate').val();
        		params +="&endDate="+$('#endDate').val();
        		$.post(url, params, function(data){
        			showDialog.close();
        			var dataNB = data.nbDatas;
        			if(dataNB != null && dataNB.projectName.projectName){
        				var namesArray = dataNB.projectName.projectName.split(",");
        				var columnsNamsArray = dataNB.projectColumnsNames.projectColumns.split("-_-");
        				var column = [];
        				var jsonGdData =  [ {display:'编号', name:'cscompid', width:100, align:'center' },
        	        		            {display:'门店', name:'compname', width:150, align:'center' },
        	        		            {display:'日期', name:'RQ', width:200, align:'center'},
        	        		            {display:'疗程人数', name:'nb_card_count', width:100,  align:'center'},
        	        		            {display:'总客单量', name:'zkdl', width:100, align:'center'},
        	        		            {display:'总实业绩', name:'zsyj', width:100, align:'center'}];
        				for(var j=0;j<namesArray.length;j++){
        					var projectName = namesArray[j];
        					var projectColumnsArray = columnsNamsArray[j].split(",");
        					
        					var columnSingle1 = {
        							display:'客单量',
        							name :'',
        							width:100,
        							align:"right"
        					};
        					var columnSingle2 = {
        							display:'实业绩',
        							name :'',
        							width:100,
        							align:"right"
        					};
        					var jsonColumn = {
        							display:"",
        							columns:[]
        					};
        					jsonColumn.display = projectName;
        					columnSingle1.name = projectColumnsArray[0];
        					columnSingle2.name = projectColumnsArray[1];
        					jsonColumn.columns.push(columnSingle1);
        					jsonColumn.columns.push(columnSingle2);
        					jsonGdData.push(jsonColumn);
        				}
        				var dataList = dataNB.projectDataList;
        				var json = {columns:jsonGdData, pageSize:50, data: null,width: centerWidth,height:height-35,rownumbers:true, usePager:false};
        				dataGrid=$("#dataGrid").ligerGrid(json);
        				dataGrid.options.data=$.extend(true, {}, {Rows: dataList,Total: dataList.length});
        			}else{
        				var jsonNOData =  [ {display:'编号', name:'cscompid', width:100, align:'center' },
            	        		            {display:'门店', name:'compname', width:150, align:'center' },
            	        		            {display:'日期', name:'RQ', width:200, align:'center'},
            	        		            {display:'疗程人数', name:'nb_card_count', width:100,  align:'center'},
            	        		            {display:'总客单量', name:'zkdl', width:100, align:'center'},
            	        		            {display:'总实业绩', name:'zsyj', width:100, align:'center'}];
        				var noDatajson = {columns:jsonNOData, pageSize:50, data: null,width: centerWidth,height:height-35,rownumbers:true, usePager:false};
        				dataGrid=$("#dataGrid").ligerGrid(noDatajson);
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
        		var excelDialog=$.ligerDialog.open({url: contextURL+'/sc021/loadExcelNB.action?'+params });
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