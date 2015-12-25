<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>员工培训经历表</title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/SchoolControl/SCC007/scc007.js"></script>
	<style type="text/css">
	    body,html{height:100%;}
    	body{padding:0px; margin:0;overflow:hidden;font-size:12px;}
   </style>
</head>
<body>
	<div class="l-loading" style="display:block" id="pageloading"></div> 
	<div id="dataLayout" style="width:100%; margin:0 auto; margin-top:4px;">
	  	<div position="center" style="width:100%;"> 
		  	<div id="dataTab" style="width:100%; height:100%; float:left; clear:both;overflow:auto;font-size:12px;"> 
				<div title="汇总" style="font-size:12px;width:100%; height:98%;">
			 		<div style="width:100%;clear:both;overflow:auto;">
						<table border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
							<tr>
								<td width="100" align="right">课程&nbsp;&nbsp;</td>
							 	<td width="160"><select id="m_credit" style="width: 150px;"></select></td>
								<td width="50" align="right">职位&nbsp;&nbsp;</td>
							 	<td width="160"><select id="m_position" style="width: 150px;"></select></td>
							 	<td width="80" align="right">开始日期&nbsp;&nbsp;</td>
							 	<td><input id="m_begindate" type="text" size="10" /></td>
							 	<td width="100" align="right">结束日期&nbsp;&nbsp;</td>
							 	<td><input id="m_enddate" type="text" size="10" /></td>
							  	<td><div id="m_search" style="margin: 0 30px;"></div></td>
							   	<td><div id="m_excel"></div></td>
							</tr>
							<tr><td style="border-bottom:1px #000000 dashed" colspan="10"></td></tr>
						</table>
					</div>
					<div id="dataGrid" style="margin:0; padding:0;"></div>
				</div>					
				<div title="明细" style="font-size:12px;width:100%; height:98%;">
			 		<div style="width:100%;clear:both;overflow:auto;">
						<table border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
							<tr>
								<td width="100" align="right">工号&nbsp;&nbsp;</td>
							 	<td width="160"><input type="text" id="d_staffno" style="width: 150px;"></select></td>
								<td width="50" align="right">姓名&nbsp;&nbsp;</td>
							 	<td width="160"><input type="text" id="d_staffname" style="width: 150px;"></select></td>
							 	<td width="80" align="right">开始日期&nbsp;&nbsp;</td>
							 	<td><input id="d_begindate" type="text" size="10" /></td>
							 	<td width="100" align="right">结束日期&nbsp;&nbsp;</td>
							 	<td><input id="d_enddate" type="text" size="10" /></td>
							  	<td><div id="d_search" style="margin: 0 30px;"></div></td>
							   	<td><div id="d_excel"></div></td>
							</tr>
							<tr><td style="border-bottom:1px #000000 dashed" colspan="10"></td></tr>
						</table>
					</div>
					<div id="detailGrid" style="margin:0; padding:0;"></div>
				</div>					
			 </div>
		 </div>	
	</div> 
  	<div style="display:none;"></div>
</body>
<script type="text/javascript">
 	var contextURL="<%=request.getContextPath()%>";
</script>
</html>