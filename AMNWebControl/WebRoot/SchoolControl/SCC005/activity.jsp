<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="pragma" content="no-cache">  
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="expires" content="0">  
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.tablescroll.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script> 	
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
</head>
<body>
	<div id="actDialog">
		<div id="actGrid" style="margin:0 5px; padding:0"></div>
	</div>
	<script type="text/javascript">
		var actGrid=null;
		$(function(){
			actGrid=$("#actGrid").ligerGrid({
	            columns: [
	            { display: '编号', 	name: 'no', width:100,align: 'left'},
	            { display: '名称', 	name: 'name', width:225,align: 'left', editor:{type:'text'}},
	            { display: '概述', 	name: 'remark', width:450,align: 'left', editor:{type:'text'}}
	           ],  pageSize:50, 
	            data:{Rows: null,Total:0},      
	          	width: 775,
	            height: 415,
	            enabledEdit: true, checkbox: false,clickToEdit: true,
	            rownumbers: false,usePager: false,
	            toolbar: { items: [
	                { text: '增加', click: addDataRow, img: '${pageContext.request.contextPath}/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: postAct, img: '${pageContext.request.contextPath}/common/ligerui/ligerUI/skins/icons/save.gif' }
               	]}
	        });
	    	$("#actGrid .l-grid-body-inner").css('width', 750);
	    	loadData();
		});
		function addDataRow(item){
			var row = actGrid.getSelectedRow();
   			actGrid.addRow({ 
   				name: "",
   				remark: ""
   		    }, row, false);
		}
		
		function loadData(){
			var requestUrl ="${pageContext.request.contextPath}/scc005/loadAct.action"; 
			$.get(requestUrl, {"_stamp":Math.random()}, function(data){
				var list = data.actSet;
				if(list != null && list.length>0){
					actGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
					actGrid.loadData(true);   
					actGrid.select(0);          	
				}else{
					actGrid.options.data=$.extend(true, {},{Rows: null,Total:0});
					actGrid.loadData(true);
				}
			});
		}
		
		function postAct(item){
			var add = actGrid.getAdded();
			var update = actGrid.getUpdated();
			if(add.length == 0 && update.length == 0){
				$.ligerDialog.warn("未保存数据,无需保存!");
			}else{
				var requestUrl ="${pageContext.request.contextPath}/scc005/postAct.action"; 
				var remark = add.length == 0 ? "" : JSON.stringify(add);
				var staffname = update.length == 0 ? "" : JSON.stringify(update);
				var params = {remark: remark, staffname:staffname, _stamp:Math.random()};
				$.get(requestUrl, params, function(data){
					var strMessage=data.strMessage;
			        if(data.sysStatus==1){	        		 
			        	 $.ligerDialog.success(strMessage);
			        	 parent.loadActData();
			        	 setTimeout(function(){
			        		 parent.actDialog.close();
			        	 }, 3000);
			        }else{
			        	$.ligerDialog.error(strMessage);
			        }
				});
			}
		}
	</script>
</body>
</html>