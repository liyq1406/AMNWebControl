<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC002/bc002.js"></script>
</head>
<body style="padding:6px; overflow:hidden;">
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	<table width="100%" border="0" cellspacing="1" cellpadding="0" >
  	<tr>
  		<td valign="top" width="228px" > <div id="commondetial" style="margin: 0; padding: 0;"></td>
  		<td valign="top" >&nbsp;</td>
  		<td valign="top" width="550px" > <div id="commoninfodivsecond" style="margin:0; padding:0"></div></td>
  		<td valign="top" >&nbsp;</td>
  		<td valign="top" width="443px" > <div id="commoninfodivthirth" style="margin:0; padding:0"></div></td>
  	</tr>
  	</table>

	
  <div style="display:none;">
  <!-- g data total ttt -->
</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
	</script>