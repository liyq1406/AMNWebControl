<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>呼叫中心</title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script> 
	<script type="text/javascript" src="<%=ContextPath %>/AdvancedOperations/AC009/ac009.js"></script>
</head>
<body>
<center> 
				<table width="100%"  border="0" style="font-size:12px;">
							<tr>
							<td valign="top">
								<table width="352px"  border="0" style="height:100%;font-size:12px;border:1px solid #A3C0E8;">
								  <tr><td colspan="6" valign="top" height="35px"><div id="customselects"></div></td></tr>
								<tr>
								<td  height="35px" width="120px">客户姓名</td><td width="210"><input type="text" id="membername" style="width:160;height:22px;"/></td>
								</tr>
								<tr>
								<td  height="35px" width="120px">联系方式</td><td width="210"><input type="text" id="callon" style="width:160;height:22px;"/></td>
								</tr>
								<tr>
								<td height="35px">更新人</td><td>
									<select id="calluserids" name="calluserids" style="width:160;height:22px;">
									  <option value=""> 请选择</option>
									</select>
								</td>
								</tr>
								<tr>
								<td height="35px">更新时间</td><td><input type="text" id="ordertime"/></td>
								</tr>
								</table>
								<div id="customdetails"></div>
								</td>
								<td colspan="6" width="1000px" heigth="100%" valign="top"><div id="selectcustom"></div></td> 
								</tr>
						</table>
		 </center>
</body>
</html>
	<script language="JavaScript">
	  	 	var contextURL="<%=request.getContextPath()%>";
	</script>
