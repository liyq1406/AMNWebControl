<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
</head>
<body style="padding:6px; overflow:hidden;">
	<div style="height: 40px;" id="layout1" class="l-layout" ligeruiid="layout1">
		<div style="top: 0px; height: 40px;" class="l-layout-top">
			<div class="l-layout-content" position="top" style="text-align: center;padding-top: 5px;">
				输入活动券号：&nbsp;&nbsp;<input name="ticketCode" id="ticketCode" style="width: 200px;"/>
			</div>
		</div>
	</div>
</body>
</html>