<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>录音查询</title>
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
	<script type="text/javascript" src="<%=ContextPath %>/AdvancedOperations/AC010/ac010.js"></script>
</head>
<body>
<center>
				<table width="100%"  border="0" style="font-size:12px;line-height:35px;">
							<tr>
								<td valign="top" width="100px"> <div id="selectludata"> </div></td>
								<td width="800px" valign="top"><table width="100%" height="100%" border="0" style="font-size:12px;line-height:35px;border:1px solid #A3C0E8; ">
										<tr>
											<td colspan="6" valign="top"><div id="sercher"></div></td>
										</tr>
										<tr>
											<td width="60px">主叫号码</td><td colspan="2"><input type="text" id="callon" style="width:160;height:22px;"/></td>
											<td  width="60px">被叫号码</td><td><input type="text" id="calloned" style="width:160;height:22px;"/></td>
										</tr>
										<tr>
											<td>来电日期</td><td width="150px"><input type="text" id="calltime" /></td><td  width="150px"><input type="text" id="calltimes" /></td>
											<td>分类</td><td><select id="calltype" style="width:160;height:22px;">
												<option value="">请选择</option>
												<option value="1">呼入</option>
												<option value="0">呼出</option>
											</select>
											</td>
										</tr>
								 </table>
								 <div id="selectludatas"></div></td>
							</tr>
						</table>
			</center>
</body>
</html>
	<script language="JavaScript">
	  	 	var contextURL="<%=request.getContextPath()%>";
	</script>
