<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
			<%
				response.setHeader("Pragma", "No-cache");

				response.setHeader("Cache-Control", "no-cache");

				response.setDateHeader("Expires", 0);
			%>
		
<title></title>
<link
	href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js"
	type="text/javascript"></script>
<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
<script src="<%=ContextPath%>/common/ligerui/json2.js"
	type="text/javascript"></script>
<script
	src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js"
	type="text/javascript"></script>
<script
	src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script
	src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js"
	type="text/javascript"></script>
<script language="JavaScript">
	var _company = parent.showDialogCompany.get('_company');
	$(function() {
		try {
			parent.commoninfodivdetial_company = $("#commoninfodivdetial_company").ligerGrid({
				columns: [ 
		          	{ display:'门店编号', name:'attr1', width:150 ,align:'left'},
	                { display:'门店名称', name:'attr2', width:250,align:'left'}
				],
				pageSize : 50, data :null,checkbox : true,
				width : '674', height : 517,
				rownumbers : true,usePager : false
			});
			//产品限制采购设定
			var showDialog=$.ligerDialog.waitting('正在查询中,请稍候...');
			$.getJSON(requestURL + "/bc013/loadCompanyData.action", {
				"_random": Math.random()
			}, function(data) {
				showDialog.close();
				var commoninfo = data.commoninfo;
				if(commoninfo!=null && commoninfo.length>0){
					parent.commoninfodivdetial_company.options.data = $.extend(true, {}, {
						Rows : commoninfo,Total : commoninfo.length
					});
				}
				parent.commoninfodivdetial_company.loadData(true);
				if(checkNull(_company)!=""){
					for (var rowid in parent.commoninfodivdetial_company.records){
						var row =parent.commoninfodivdetial_company.records[rowid];
						if(_company.indexOf(row.attr1)!=-1){
							parent.commoninfodivdetial_company.select(rowid);
						}
					}
				}
			});
			$("#pageloading").hide();
		} catch (e) {
			alert(e.message);
		}
	});
</script>
<style type="text/css">
body {
	font-size: 12px;
}

.l-table-edit {
	
}

.l-table-edit-td {
	padding: 4px;
}

.l-button-submit,.l-button-test {
	width: 80px;
	float: left;
	margin-left: 10px;
	padding-bottom: 2px;
}

.l-verify-tip {
	left: 230px;
	top: 120px;
}

.scr_con {
	position: relative;
	width: 298px;
	height: 98%;
	border: solid 1px #ddd;
	margin: 0px auto;
	font-size: 12px;
}

#dv_scroll {
	position: absolute;
	height: 98%;
	overflow: hidden;
	width: 298px;
}

#dv_scroll .Scroller-Container {
	width: 100%;
}

#dv_scroll_bar {
	position: absolute;
	right: 0;
	bottom: 30px;
	width: 14px;
	height: 150px;
	border-left: 1px solid #B5B5B5;
}

#dv_scroll_bar .Scrollbar-Track {
	position: absolute;
	left: 0;
	top: 5px;
	width: 14px;
	height: 150px;
}

#dv_scroll_bar .Scrollbar-Handle {
	position: absolute;
	left: -7px;
	bottom: 10;
	width: 13px;
	height: 29px;
	overflow: hidden;
	background: url('<%=ContextPath%>/common/ligerui/images/srcoll.gif')
		no-repeat;
	cursor: pointer;
}

#dv_scroll_text {
	position: absolute;
}
</style>
</head>
<body style="padding:6px; overflow:hidden;">
	<div class="l-loading" style="display:block;" id="pageloading"></div>
	<div style="height: 30px;" id="layout1" class="l-layout" ligeruiid="layout1">
		<div style="left: 0px; top: 0px; width: 100%; height: 490px;" class="l-layout-center">
			<div style="height: 500px;" class="l-layout-content" title="" position="center">
				<div id="commoninfodivdetial_company" style="margin:0; padding:0;"></div>
			</div>
		</div>
	</div>
</body>
</html>
<script language="JavaScript">var requestURL = "<%=request.getContextPath()%>";</script>