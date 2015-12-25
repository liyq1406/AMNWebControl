<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
>
<!-- <input type="submit" value="手动添加" style="width:70px" class="showdiv"/> -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加任务信息</title>
    <link href="<%=ContextPath%>/AdvancedOperations/AC002/demo.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
    <script type="text/javascript" src="<%=ContextPath %>/AdvancedOperations/AC001/ac003.js"></script>	
</head>
<body style="padding:6px; overflow:hidden;" style="font-size:12px;line-height:35px;">
	    	<div id="selectcall"></div>
</body>
</html>
	<script language="JavaScript">
	var contextURL="<%=request.getContextPath()%>";
	</script>
