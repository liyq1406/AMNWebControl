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
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/PersonnelControl/PC015/pc015.js"></script>

		<style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
  
		    body,html{height:100%;}
	    body{ padding:0px; margin:0;   overflow:hidden;}  
	    .l-link{ display:block; height:16px; line-height:16px; padding-left:10px; text-decoration:underline; color:#333;}
	    .l-link2{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
	    .l-link3{text-decoration:underline;  margin-left:2px;margin-right:2px;}
	    .l-layout-top{background:#102A49; color:White;}
	    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
		 #dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;}
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 
  	 <div id="pc015layout" style="width:100%; margin:0 auto; margin-top:4px;">
  	 	<div style="width:100%;height:20px;"> 
	  	 	<table border="0" cellspacing="0" cellpadding="0" style="font-size:12px;line-height:25px;">
				<tr>
					<td width="540"><div ><%@include file="/common/search.frag"%></div></td>
				</tr>
			</table>
		</div>
  	 	<div position="center" id="detialTab" style="width:100%; height:100%; float:left; clear:both;overflow:auto;font-size:12px;"> 
		 	<div title="门店职位异动统计" style="font-size:12px;width:100%; height:98%;">
				<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
					<tr>
						<td width="80">查询日期：</td>
						<td width="130"><input id="strFromDate" name="strFromDate" type="text"  style="width:120"  /></td>
						<td width="130"><input id="strToDate" name="strToDate" type="text"  style="width:120" /></td>
						<td>异动类型</td>
						<td> <select id="searchType" name="searchType" style="width:140">
						<option value="5">在职员工</option>
						<option value="1">离职申请</option>
						<option value="2">入职申请</option>
						<option value="3">本店调动</option>
						<option value="4">跨店调动</option>
						<option value="6">重回公司</option></select></td>
						<td><div id="searchButton"></div></td>
						<td><div id="excelButton"></div></td>
						<td width="500">&nbsp;</td>
					</tr>
					<tr><td style="border-bottom:1px #000000 dashed" colspan="8"></td></tr>
				</table>
				<div id="commoninfodivTradeDate" style="margin:0; padding:0;"></div>
			</div>	
		 	<div title="入职率统计" style="font-size:12px;width:100%; height:98%;">
				<table id="inTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
					<tr>
						<td width="80">查询日期：</td>
						<td width="130"><input id="inFromDate" name="inFromDate" type="text"  style="width:120"  /></td>
						<td width="130"><input id="inToDate" name="inToDate" type="text"  style="width:120" /></td>
						<td><input type="checkbox" id="inDetail" name="inDetail" /> </td>
						<td style="width: 100px;">显示明细</td>
						<td style="width: 140px;"><div id="insearchBtn"></div></td>
						<td><div id="inexcelBtn"></div></td>
						<td width="500">&nbsp;</td>
					</tr>
					<tr><td style="border-bottom:1px #000000 dashed" colspan="8"></td></tr>
				</table>
				<div id="inDataGrid" style="margin:0; padding:0;"></div>
			</div>	
			<div title="离职率统计" style="font-size:12px;width:100%; height:98%;">
				<table id="outTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
					<tr>
						<td width="80">查询日期：</td>
						<td width="130"><input id="outFromDate" name="outFromDate" type="text"  style="width:120"  /></td>
						<td width="130"><input id="outToDate" name="outToDate" type="text"  style="width:120" /></td>
						<td><input type="checkbox" id="outDetail" name="outDetail" /> </td>
						<td style="width: 100px;">显示明细</td>
						<td style="width: 140px;"><div id="outsearchBtn"></div></td>
						<td><div id="outexcelBtn"></div></td>
						<td width="500">&nbsp;</td>
					</tr>
					<tr><td style="border-bottom:1px #000000 dashed" colspan="8"></td></tr>
				</table>
				<div id="outDataGrid" style="margin:0; padding:0;"></div>
			</div>
		</div>	
	</div> 
  <div style="display:none;">
  <!-- g data total ttt -->
  </div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
	</script>