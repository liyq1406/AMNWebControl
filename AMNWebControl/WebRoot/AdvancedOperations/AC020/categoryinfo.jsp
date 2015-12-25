<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
  
  	<link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC020/categoryinfo.js"></script>
</head>
<body style="padding:6px; overflow:hidden;">
	<form name="categoryinfoFrom" method="post"  id="categoryinfoFrom">

		<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:36px;" >
		<tr>
			<td><font color="red">申请门店</font></td>
			<td><input type="text" id="compno" name="compno" style="width:120" readonly="true" /></td>
		</tr>
		<tr>
			<td><font color="red">类别编号</font></td>
			<td><input type="text" id="categoryno" name="categoryno" style="width:120"/></td>
		</tr>
		<tr>
			<td><font color="red">类别名称</font></td>
			<td><input type="text" id="categoryname" name="categoryname" style="width:120"/></td>
		</tr>
		<tr>
			<td>类别备注</td>
			<td  colspan="3"><textarea  rows="4"  cols="20" name="categorymark" id="categorymark"></textarea></td>
		</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
		<tr>
			<td colspan="5" align="center"><div id="comfirmButton"></div></td>
		</tr>
		</table>	
	</form>				
  
</body>
</html>
