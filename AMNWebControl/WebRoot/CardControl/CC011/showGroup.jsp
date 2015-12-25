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
	var commoninfodivdetial_group = null;
	$(function() {
		try {
			commoninfodivdetial_group = $("#commoninfodivdetial_group").ligerGrid({
					columns: [ 
					   {display : '套餐编号',name : 'bpackageno', width : 80, align : 'left'},
					   {display : '套餐名称',name : 'packagename',width : 130,align : 'left'},
					   {display : '套餐金额',name : 'packageprice',width : 70,align : 'left'}
					],
					pageSize : 25, data :null,
					width : '360', height : 485,
					rownumbers : true,usePager : false,
					onSelectRow : function (data, rowindex, rowobj){
						queryData(data, rowindex, rowobj);
	                } 
				});
			parent.commoninfodivdetial_group_item = $("#commoninfodivdetial_group_item").ligerGrid({
				columns: [ 
		          	{ display:'项目编号', name:'bpackageprono', width:100 ,align:'left'},
	                { display:'项目名称', name:'bpackageproname', width:130,align:'left'},
	                { display:'项目单价', name:'packageproprice', width:60,align:'left', type:'int'},
	                { display:'项目数量', name:'packageprocount', width:60,align:'left', type:'int'},
	                { display:'项目金额', name:'packageproamt', width:60	,align:'left', type:'int'}
				],
				pageSize : 25, data :null,
				width : '490', height : 485,
				rownumbers : true,usePager : false
			});
			//加载团购套餐
			$.getJSON(requestURL + "/cc011/loadGroup.action", {
				"_random": Math.random()
			}, function(data) {
				if (data.strMessage != ""){
					$.ligerDialog.warn(data.strMessage);
					commoninfodivdetial_group.options.data = $.extend(true, {}, {
						Rows : null,Total : 0
					});
					commoninfodivdetial_group.loadData(true);
				} else {
					commoninfodivdetial_group.options.data = $.extend(true, {}, {
						Rows : data.lsMpackageinfo,Total : data.lsMpackageinfo.length
					});
					commoninfodivdetial_group.loadData(true);
					commoninfodivdetial_group.select(0);
				}
			});
			$("#pageloading").hide();
		} catch (e) {
			alert(e.message);
		}
	});

	function queryData(data, rowindex, rowobj) {
		$.getJSON(requestURL + "/cc011/loadGroupPro.action", {
			"strCurCompId": data.bcompid,
			"strCardNo" : data.bpackageno,
			"_random": Math.random()
		}, function(data) {
			var list = data.lsDmpackageinfo;
			if(list != null && list.length>0){
				parent.commoninfodivdetial_group_item.options.data = $.extend(true, {}, {
					Rows : list,Total : list.length
				});
			} else {
				parent.commoninfodivdetial_group_item.options.data = $.extend(true, {}, {
					Rows : null,Total : 0
				});
			}
			parent.commoninfodivdetial_group_item.loadData(true);
		});
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
	<div class="l-loading" style="display:block;" id="pageloading"></div>
	<div style="height: 30px;" id="layout1" class="l-layout" ligeruiid="layout1">
		<div style="left: 0px; top: 0px; width: 100%; height: 490px;" class="l-layout-center">
			<div style="height: 500px;" class="l-layout-content" title="" position="center">
				<div id="commoninfodivdetial_group" style="margin:0; padding:0;float: left;"></div>
				<div id="commoninfodivdetial_group_item" style="margin:0; padding:0;float: right;"></div>
			</div>
		</div>
	</div>
</body>
</html>
<script language="JavaScript">var requestURL = "<%=request.getContextPath()%>";</script>