<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/WdatePicker.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		 
		$(function ()
   		{
   			$("#confirmOrder").ligerButton(
	         {
	             text: '核实预约', width: 120,
		         click: function ()
		         {
		             confirmOrder();
		         }
	     	});
	     	$("#confirmMsg").ligerButton(
	         {
	             text: '确认预约信息', width: 120,
		         click: function ()
		         {
		             confirmMsg();
		         }
	     	});
	     });
	     
	     
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td>手机号码:</td><td><input type="text" name="ordermphone" id="ordermphone"  readonly="true" style="width:140;background:#EDF1F8;" /></td>
			<td rowspan="5" valign="top"><textarea  rows="6"  cols="30" name="orderconfirmmsg" id="orderconfirmmsg"></textarea></td>
		</tr>
		<tr><td>确认日期:</td><td><input type="text" name="orderdate" id="orderdate" style="width:140;"  class="Wdate" onclick="WdatePicker()" /></td></tr>
		<tr><td>确认时间:</td><td><input type="text" name="ordertime" id="ordertime" style="width:140;" /></td></tr>
		<tr><td>服务项目:</td><td><input type="text" name="ordertime" id="ordertime" style="width:140;" /></td></tr>
		<tr><td>服务人员:</td><td><input type="text" name="ordertime" id="ordertime" style="width:140;" /></td></tr>
		<tr><td colspan="2" align=center><div id="confirmOrder"></div></td>
			<td align=center><div id="confirmMsg"></div></td></tr>
	</table>	
	</div>

</body>
</html>
