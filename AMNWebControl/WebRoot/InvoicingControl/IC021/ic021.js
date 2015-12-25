var dataLayout=null;
var dataGrid=null;
$(function (){
   try{
	  	//布局
	   dataLayout= $("#dataLayout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
       	 height = $(".l-layout-center").height();
       	 centerWidth = dataLayout.centerWidth;
       	 $("#dataGrid2").css({width: centerWidth,
            height:height-35});
       	$("#beginDate").ligerDateEditor({ labelWidth: 120,format: "yyyy-MM-dd", labelAlign: 'right',width:'120' });
       	$("#endDate").ligerDateEditor({ labelWidth: 120,format: "yyyy-MM-dd", labelAlign: 'right',width:'120' });
        $("#searchButton").ligerButton({
             text: '查询', width: 80,
	         click: function (){
	        	var showDialog = $.ligerDialog.waitting('正在查询中,请稍候...');
	        	var url = contextURL+"/ic021/query.action"; 
	        	var strCurCompId=$("#cominfo").val();
        		var params = "strCurCompId="+strCurCompId;
        		params +="&beginDate="+$('#beginDate').val();
        		params +="&endDate="+$('#endDate').val();
        		params +="&projectKind="+$('#projectKind').val();
        		$.post(url, params, function(data){
        			showDialog.close();
        			var list = data.dataSet;
        			if(!jQuery.isEmptyObject(list)){
        				$("#dataGrid2").html("");
        				createFirstTable();
        				for(key in list){
            				var shbh = key;
            				createTable(list[shbh]);
            			}
        				var lastTable = $("#dataGrid2 table:last");
        				var tr =  $("<tr class=\"l-grid-row\"></tr>");
        				var td = $("<td class=\"l-grid-row-cell\" style=\"width:400px; height:30px;border-left:solid 1px #a3c0e8;\"colspan=\"4\" >"
        						+"合计"
        						+"</td>"
        						+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
        						+totalZb.toFixed(2)
        						+"</td>"
        						+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
        						+"</td>"
        						+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
        						+totalMd.toFixed(2)
        						+"</td>");
        				tr.append(td);
        				lastTable.append(tr);
        			}else{
        				$("#dataGrid2").html("");
        				$.ligerDialog.warn("没有查到数据信息!");
        				//dataGrid.options.data=$.extend(true, {},{Rows: null,Total: 0});
        			}
        		}).error(function(e){$.ligerDialog.error("获取数据失败，请重试！");});
	         }
         });
        $("#excelButton").ligerButton({
             text: '转Excel', width: 80,
	         click: function (){
	        	var comObj = $("#cominfo option[selected]");
	        	var strCurCompId=$("#cominfo").val();
	        	var comName = comObj.attr('data');
        		var params = "strCurCompId="+strCurCompId;
        		params +="&beginDate="+$('#beginDate').val();
        		params +="&endDate="+$('#endDate').val();
        		params +="&projectKind="+$('#projectKind').val();
        		params +="&status="+$('#status').val();
        		params +="&comName="+encodeURI(comName);
        		var excelDialog=$.ligerDialog.open({url: contextURL+'/ic021/loadExcel.action?'+params });
        		setTimeout(function () { excelDialog.close(); }, 15000);
	         }
         });
        
        $("#excelAllButton").ligerButton({
            text: '分状态转Excel', width: 120,
	         click: function (){
	        	var comObj = $("#cominfo option[selected]");
	        	var strCurCompId=$("#cominfo").val();
	        	var comName = comObj.attr('data');
       		var params = "strCurCompId="+strCurCompId;
       		params +="&beginDate="+$('#beginDate').val();
       		params +="&endDate="+$('#endDate').val();
       		params +="&projectKind="+$('#projectKind').val();
       		params +="&status="+$('#status').val();
       		params +="&comName="+encodeURI(comName);
       		var excelDialog=$.ligerDialog.open({url: contextURL+'/ic021/loadAllExcel.action?'+params });
       		setTimeout(function () { excelDialog.close(); }, 100000);
	         }
        });
        $("#excelCompany").ligerButton({
        	text: '分门店转Excel', width: 120,
        	click: function (){
        		var comObj = $("#cominfo option[selected]");
        		var strCurCompId=$("#cominfo").val();
        		var comName = comObj.attr('data');
        		var params = "strCurCompId="+strCurCompId;
        		params +="&beginDate="+$('#beginDate').val();
        		params +="&endDate="+$('#endDate').val();
        		params +="&projectKind="+$('#projectKind').val();
        		params +="&status="+$('#status').val();
        		params +="&comName="+encodeURI(comName);
        		var excelDialog=$.ligerDialog.open({url: contextURL+'/ic021/loadCompanyExcel.action?'+params });
        		setTimeout(function () { excelDialog.close(); }, 100000);
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
var totalZb = 0;
var totalMd = 0;
function createTable(json){
	var firstObj = json[0];
	var table=$("<table class=\"l-grid-body-table\" cellSpacing=\"0\" cellPadding=\"0\"></table>");
	var tr1 = $("<tr class=\"l-grid-row\"></tr>");
	var td1 = $("<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;border-left:solid 1px #a3c0e8;\">单号</td>"
			+" <td class=\"l-grid-row-cell\" style=\"width:200px; height:30px;\">"+firstObj.attr12+"</td>"
			+" <td class=\"l-grid-row-cell\" style=\"width:200px; height:30px;\" colspan=\"2\">"+firstObj.attr9+"</td>"
			+"<td class=\"l-grid-row-cell\" style=\"width:200px; height:30px;\" colspan=\"2\">"+firstObj.attr10+"</td>"
			+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;color:red;font-weight:bold;\">"+firstObj.attr13+"</td>");
	tr1.append(td1);
	table.append(tr1);
	var tr2 =  $("<tr class=\"l-grid-row\"></tr>");
	var td2 = $("<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;border-left:solid 1px #a3c0e8;\">"
					+" 产品编号"
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:200px; height:30px;\">"
					+"产品名称"
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+"数量"
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+"单价"
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+"厂家结算金额"
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+"门店单价"
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+"门店结账金额"
					+"</td>");
	tr2.append(td2);
	table.append(tr2);
	var zbxj = 0;
	var mdxj = 0;
	for(var i=0;i<json.length;i++){
		var obj = json[i];
		zbxj = zbxj + parseFloat(obj.attr6);
		mdxj = mdxj + parseFloat(obj.attr8);
		var commontr = $("<tr class=\"l-grid-row\"></tr>");
		var commonTd = $("<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;border-left:solid 1px #a3c0e8;\" >"
					+obj.attr3
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:200px; height:30px;\">"
					+obj.attr4
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+obj.attr5
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+obj.attr11
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+obj.attr6
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+obj.attr7
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+obj.attr8
					+"</td>");
		commontr.append(commonTd);
		table.append(commontr);
	}
	zbxj = zbxj.toFixed(2);
	mdxj = mdxj.toFixed(2);
	var lastTr = $("<tr class=\"l-grid-row\"></tr>");
	var lastTd = $("<td class=\"l-grid-row-cell\" style=\"width:300px; height:30px;border-left:solid 1px #a3c0e8;\"colspan=\"3\" >"
				    +"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+"小计"
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+zbxj
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+"</td>"
					+"<td class=\"l-grid-row-cell\" style=\"width:100px; height:30px;\">"
					+mdxj
					+"</td>");
	lastTr.append(lastTd);
	table.append(lastTr);
	$("#dataGrid2").append(table);
	totalZb = totalZb+parseFloat(zbxj);
	totalMd = totalMd+parseFloat(mdxj);
}

function createFirstTable(){
	var obj = $("#cominfo option[selected]");
	var fTable = $("<table class=\"l-grid-body-table\" cellSpacing=\"0\" cellPadding=\"0\"></table>");
	var fTr = $("<tr class=\"l-grid-row\"></tr>");
	var fTd = $("<td class=\"l-grid-row-cell\" style=\"width:806px; height:30px;border:solid 1px #a3c0e8;\">"
						+"<div style=\"float:left;width:50px;height:30px;line-height:30px;\">"
							+"店号："
						+"</div>"
						+"<div style=\"float:left;width:50px;height:30px;line-height:30px;\">"
						+obj.val()
						+"</div>"
						+"<div style=\"float:left;width:50px;height:30px;line-height:30px;\">"
						+"	店名："
						+"</div>"
						+"<div style=\"float:left;width:150px;height:30px;line-height:30px;\">"
							+obj.attr("data")
						+"</div>"
						+"<div style=\"float:left;width:100px;height:30px;margin-left:200px;line-height:30px;\">"
						+"	结算日期："
						+"</div>"
						+"<div style=\"float:left;width:200px;height:30px;line-height:30px;\">"
							+$('#beginDate').val()+"~"+$('#endDate').val()
						+"</div>"
					+"</td>");
	fTr.append(fTd);
	fTable.append(fTr);
	$("#dataGrid2").append(fTable);
}