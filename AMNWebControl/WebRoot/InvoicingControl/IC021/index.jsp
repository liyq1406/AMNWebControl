<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>供应商结账</title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/InvoicingControl/IC021/ic021.js"></script>
	<style type="text/css">
	    body,html{height:100%;}
    	body{padding:0px; margin:0;overflow:hidden;font-size:12px;font-family:"微软雅黑", "宋体", Arial, sans-serif;}
    	table{
    	font-size:12px;font-family:"微软雅黑", "宋体", Arial, sans-serif;
    	}
   </style>
</head>
<body>
	<div class="l-loading" style="display:block" id="pageloading"></div> 
	<div id="dataLayout" style="width:100%; margin:0 auto; margin-top:4px;">
	  	<div position="center" style="width:100%;"> 
	  		<table id="dataTable" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
				<tr>
					<td width="10">&nbsp;</td>
				 	<td width="60">门店：</td>
				 	<td width="160"><select name="cominfo" id="cominfo"  style="width:150;">
				 	<s:iterator value="comList" id="com">
				 	<option value="<s:property value="#com.attr1" />" data="<s:property value="#com.attr2" />"><s:property value="#com.attr1" />-<s:property value="#com.attr2" /></option>
				 	</s:iterator>
				 	</select>
				 	</td>
				 	<td width="60">开始日期</td>
				 	<td width="130"><input id="beginDate" type="text" size="10" /></td>
				 	<td width="60">结束日期</td>
				 	<td width="130"><input id="endDate" type="text" size="10" /></td>
				 	<td width="65">供应商类别</td>
				 	<td width="85"><select name="projectKind" id="projectKind"  style="width:100;">
												<option value="003">003菲灵</option>
												</select></td>
				  	<td><div id="searchButton" style="margin-right:10px;margin-left:10px;"></div></td>
				  	<td><div id="excelButton"></div></td>
				  	<td><div id="excelAllButton" style="margin-left:10px;"></div></td>
				  	<td><div id="excelCompany" style="margin-right:10px;margin-left:10px;"></div></td>
				</tr>
				<tr><td style="border-bottom:1px #000000 dashed" colspan="13"></td></tr>
			</table>	
			<div id="dataGrid2" style="margin:0; padding:0;overflow:auto;" class="l-scroll">
		</div>
	</div> 
  	<div style="display:none;"></div>
</body>
<script type="text/javascript">
 	var contextURL="<%=request.getContextPath()%>";
</script>
</html>