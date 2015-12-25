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
	<script type="text/javascript" src="<%=ContextPath%>/SellReportControl/SC005/sc005.js"></script>

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

  	 <div id="sc005layout" style="width:100%; margin:0 auto; margin-top:4px; ">
	  	<div position="center"   id="designPanel"  style="width:100%;"> 
	  		<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
			
			<tr>
				 <td width="540"><div ><%@include file="/common/search.frag"%></div></td>
				 <td width="60">使用日期：</td>
				 <td width="130">
						<input id="strFromDate" type="text" size="10" />
				 </td>
				 <td width="130">
					<input id="strToDate" type="text" size="10" />
				 </td>
				  <td ><div id="searchButton"></div></td>
			</tr>
			<tr><td style="border-bottom:1px #000000 dashed" colspan="5"></td></tr>
			<tr><td colspan="5" width="100%"  bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;">
			当前门店：&nbsp;&nbsp;<font color="red"><label id="lbcurcompid">－</label></font> 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			美容排名：&nbsp;&nbsp;<font color="red"><label id="lbmryejisort">－</label> 名</font> 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			美发排名：&nbsp;&nbsp;<font color="red"><label id="lbmfyejisort">－</label> 名</font> 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			烫染护排名：&nbsp;&nbsp;<font color="red"><label id="lbtryejisort">－</label>名</font> 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			美甲排名：&nbsp;&nbsp;<font color="red"><label id="lbmjyejisort">－</label>名</font> 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			总业绩排名：&nbsp;&nbsp;<font color="red"> <label id="lbtotalyejisort">－</label>名 </font> &nbsp;&nbsp;&nbsp;&nbsp;
			</td>
			</tr>
			<tr>
				<td colspan="5" width="100%"  bgcolor="#d6ddd6" style="font-size:12px;line-height:30px;">
					<table width="900" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
						<tr>
							<td>美容虚业绩合计:&nbsp;&nbsp;<label id="lbhairyeji">0</label></td>
							<td>美发虚业绩合计:&nbsp;&nbsp;<label id="lbbeatyyeji">0</label></td>
							<td>烫染护虚业绩合计:&nbsp;&nbsp;<label id="lbtrhyeji">0</label></td>
							<td>美甲虚业绩合计:&nbsp;&nbsp;<label id="lbfingeryeji">0</label></td>
							<td>总虚业绩合计:&nbsp;&nbsp;<label id="lbtotalyeji">0</label></td>
						</tr>
						<tr>
							<td>美容实业绩合计:&nbsp;&nbsp;<label id="lbrealhairyeji">0</label></td>
							<td>美发实业绩合计:&nbsp;&nbsp;<label id="lbrealbeatyyeji">0</label></td>
							<td>烫染护实业绩合计:&nbsp;&nbsp;<label id="lbrealtrhyeji">0</label></td>
							<td>美甲实业绩合计:&nbsp;&nbsp;<label id="lbrealfingeryeji">0</label></td>
							<td>总实业绩合计:&nbsp;&nbsp;<label id="lbrealtotalyeji">0</label></td>
						</tr>
					</table>
				</td>
			</tr>
			</table>	
			<div id="commoninfodivTradeDate" style="margin:0; padding:0"></div>		
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