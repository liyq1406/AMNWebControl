<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>业务报表</title>
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
	<script type="text/javascript" src="<%=ContextPath %>/AdvancedOperations/AC011/ac011.js"></script>
</head>
<body>
<center>
				<table width="100%"  border="0" style="font-size:12px;line-height:35px;">
							<tr>
								<td valign="top" width="460px">
									<table  style="width:400px;font-size:12px;line-height:35px;border:1px solid #A3C0E8;">
										<tr>
											<td  colspan="2"><div id="callbaobar"> </div></td>
										</tr>
										<tr>
											<td >&nbsp;&nbsp;分类&nbsp;&nbsp;<select id="calls" onchange=" selectbaos()" style="width:160;height:22px;">
												<option value="">请选择</option>
												<option value="1">呼入</option>
												<option value="0">呼出</option>
											</select></td>
											<td><select id="calluseridss" name="calluseridss" onchange=" selectbaos();" style="width:160;height:22px;">
												</select></td>
										</tr>
									</table>
									<div id="callbao"> </div>
								</td>
								<td valign="top" >
									<div id="navtab2" style="width:800px;heigth:900px;overflow:hidden; border:1px solid #A3C0E8;background:#ffffff ">
										<div  title="预约报表" style="height:730px;width: 100%;"  >
											<div id="orderbao"></div>
										</div>
										<div  title="咨询报表" style="height:730px;width: 100%;"  >
											<div id="referbao"></div>
										</div>
										<div  title="投诉报表" style="height:730px;width: 100%;"  >
											<div id="peiierbao"></div>
										</div>
										<div  title="挂失报表" style="height:730px;width: 100%;"  >
											<div id="carretuningbao"></div>
										</div>
									</div>
								</td>
							</tr>
						</table>
		 </center>
</body>
</html>
	<script language="JavaScript">
	  	 	var contextURL="<%=request.getContextPath()%>";
	</script>
