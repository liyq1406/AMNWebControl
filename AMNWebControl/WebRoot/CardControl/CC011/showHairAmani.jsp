<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8"/>
	<link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" /> 
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" /> 
	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>  
</head>

<body>
<div style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;padding: 5px;">
	<table style="font-size:13px;line-height: 20px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td>包数：</td>
			<td><input type="text" name="haircount" id="haircount" style="width:80px;"/></td>
			<td>总价：</td>
			<td><input type="text" name="hairamt" id="hairamt" readonly="readonly" style="width:80px;"/></td>
		</tr>
		<tr style="line-height: 30px;"><td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;每包：900</td></tr>
		<tr style="line-height: 40px;">
			<td style="text-align: right;"><input type="checkbox" id="customer" name="customer" value="1" /></td>
			<td>合作方客人</td>
			<td style="text-align: right;"><input type="checkbox" id="operate" name="operate" value="2" /></td>
			<td>合作方操作</td>
		</tr>
	</table>	
</div>
<script language="JavaScript">
    $(function(){
    	$("#haircount").change(function(){
    		if(!/^\d+$/.test($(this).val())){
				$.ligerDialog.warn("请输入正确的整数格式！");
				$(this).val("");
				$("#hairamt").val("");
				return;
			}
    		if($(this).val()*1<1){
				$.ligerDialog.warn("包数必须大于等于 1 包！");
				$(this).val("");
				$("#hairamt").val("");
				return;
			}
    		$("#hairamt").val($(this).val()*1*900);
    	});
    	$("#operate").click(function(){
   			//$("#customer").attr("checked", $(this).is(":checked"));
   			if(!$("#customer").is(":checked")){
   				$.ligerDialog.warn("必须选择合作方客人！");
   				$(this).attr("checked", false);
   			}
    	});
    	$("#customer").click(function(){
   			if(!$(this).is(":checked") && $("#operate").is(":checked")){
   				$("#operate").attr("checked", false);
   			}
    	});
    });
</script>
</body>
</html>
