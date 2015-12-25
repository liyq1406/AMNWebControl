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
	var commoninyearinfo = null;
	$(function() {
		try {
			parent.commoninfodivdetial_medical = $("#commoninfodivdetial_medical")
					.ligerGrid({
						columns : [ {
							display : '名称',
							name : 'packagename',
							width : 150,
							align : 'left'
						}, {
							display : '购买日期',
							name : 'saledate',
							width : 120,
							align : 'center'
						}, {
							display : '剩余次数',
							name : 'lastcount',
							width : 80,
							align : 'center'
						},
						{
							display : '总次数',
							name : 'salecount',
							width : 80,
							align : 'center'
						},
						{
							display:'状态',
							name : 'state',
							width : 50,
							render: function (item)
			              	{
			              		if(item.state==1)
			              		{
			              			return '正常';
			              		}
			              		else
			              		{
			              			return '停用';
			              		}
			                } 
						}],
						pageSize : 25,
						data :null,
						width : '750%',
						height : 450,
						onBeforeCheckRow : f_onCheckRow,
						enabledEdit : false,
						checkbox : true,
						rownumbers : false,
						usePager : false
					});
			$("#pageloading").hide();
		} catch (e) {
			alert(e.message);
		}
	});

	function queryData() {
		if ($("#curPhone").val() == "" && $("#curName").val() =="") {
			return;
		} else {
			$.getJSON(requestURL + "/cc011/loadMedical.action", {
				"strPhone" : $("#curPhone").val(),
				"strName":  $("#curName").val(),
				"_random": Math.random()
			}, function(data) {
				if (data.strMessage != "") {
					$.ligerDialog.warn(data.strMessage);
					$("#curPhone").val("");
					$("#curName").val("");
					parent.commoninfodivdetial_medical.options.data = $
					.extend(true, {}, {
						Rows : null,
						Total : 0
					});
				} else {
					parent.commoninfodivdetial_medical.options.data = $
						.extend(true, {}, {
							Rows : data.medicalSet,
							Total : data.medicalSet.length
						});
				}
				parent.commoninfodivdetial_medical.loadData(true);
			});
		}
	}

	function f_onCheckRow(checked, data, rowid) {
		if (data.lastcount <= 0) {
			$.ligerDialog.error("选择的合作项目疗程没有消费次数了，请重新选择！");
			return false;
		}
		if(data.state!=1){
			$.ligerDialog.error("选择的合作项目疗程已经停用，请重新选择！");
			return false;
		}
		return true;
	}
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
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<div style="height: 30px;" id="layout1" class="l-layout"
		ligeruiid="layout1">
		<div style="left: 0px; top: 35px; width: 620px; height: 450px;"
			class="l-layout-center">
			<div style="height: 453px;" class="l-layout-content" title=""
				position="center">
				<div id="commoninfodivdetial_medical" style="margin:0; padding:0"></div>
			</div>
		</div>
		<div style="top: 0px; height: 30px;" class="l-layout-top">
			<div class="l-layout-content" position="top">
				手机号码:<input name="curPhone" id="curPhone" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				姓名:<input name="curName" id="curName" /> 
				<input id="Button1"
					class="l-button l-button" value="查询" type="button"
					onclick="queryData()">
			</div>
		</div>
	</div>
</body>
</html>
<script language="JavaScript">
	var requestURL = "<%=request.getContextPath()%>";
	</script>
