<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>门店活动设定</title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.tablescroll.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script> 	
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/mindsearchitem.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC021/bc021.js"></script>
</head>

<body>
	<div class="l-loading" style="display:block;height:100%;" id="pageloading"></div> 
	 <div id="dataLayout" style="width:100%;margin:0 auto; margin-top:0px;">
	 	<div position="left" id="lsPanel" title="连锁结构"> 
		  	<div style="width: 220px; height: 650; overflow-y:auto;overflow-x:hidden;">
	  			<ul id="companyTree" style="margin-top:3px;"></ul>
		  	</div>
	  	</div> 
	    <div position="center" id="designPanel" style="width:100%;"> 
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
					<td valign="top">
						<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
							<tr><td valign="top"><div id="masterGrid" style="margin:0; padding:0"></div></td></tr>
						</table>
					</td>
				</tr>
			</table>
	    </div>
	 </div>
  <div style="display:none;">
  <!-- g data total ttt -->
  </div>

<script language="JavaScript">
 	 	var contextURL="${pageContext.request.contextPath}";
</script>
</body>
</html>
