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
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>  
	<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC022/ac022.js"></script>
	<style type="text/css">
           body{ font-size:12px;} 
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;} 
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;} 
        .scr_con {position:relative;width:298px; height:98%;border:solid 1px #ddd;margin:0px auto;font-size:12px;} 
        #dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;} 
		#dv_scroll .Scroller-Container{width:100%;} 
		#dv_scroll_bar {position:absolute;right:0;bottom:30px;width:14px;height:150px;border-left:1px solid #B5B5B5;} 
		#dv_scroll_bar .Scrollbar-Track{position:absolute;left:0;top:5px;width:14px;height:150px;} 
		#dv_scroll_bar .Scrollbar-Handle{position:absolute;left:-7px;bottom:10;width:13px;height:29px;overflow:hidden;background:url('<%=ContextPath%>/common/ligerui/images/srcoll.gif') no-repeat;cursor:pointer;}
		#dv_scroll_text {position:absolute;} 
   </style>
</head>
<body>

	<table  border="0" cellspacing="1" cellpadding="0" style="width:100%; font-size:12px;line-height:35px;" >
		<tr>
			<td colspan="3" width="100%" align="center" bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;">微信券配置</td>
	    </tr>
		<tr>
			<td width="60">cardId</td>
			<td>
				<input id="cardId" name="cardId" type="text" size="20"  />
			</td>
			<td rowspan="9" valign="top"><div id="commoninfodivdetial" style="margin:0; padding:0"></div></td>
		</tr>
		<tr>
			<td >卡券类型</td>
			<td><select id="type" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"name="type" >
					<option value="0">现金抵用券</option>
					<option value="1">项目抵用券</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>面值</td>
			<td><input id="cardAmt" name="cardAmt" type="text" size="20"/></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><div id="handDyqOuterCardInfo"></div></td>
		</tr>
	</table>
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
	</script>