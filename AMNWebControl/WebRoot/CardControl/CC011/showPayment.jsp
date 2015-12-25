<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		var paymethod = parent.showDialogpayment.get('paymethod');
		$(function(){
			$("#payTotalAmt").html(parent.payTotalBank);
			$("#paybtn").val(parent.showDialogpayment.get('title'));
			$("#auth_code").focus();
		});
		function payment(){
			$("#auth_code").focus();
			if(checkNull($("#auth_code").val())==""){
				$.ligerDialog.warn("请使用终端扫描支付条码！");
				return;
			}else if(checkNull($("#auth_code").val()).length!=18){
				$.ligerDialog.warn("支付条码错误，请重新扫描！");
				$("#auth_code").val("");
				return;
			}else{
				$("#auth_code").attr("readonly",true);
				$("#paybtn").attr("disabled", "disabled");
				parent.handPayment(paymethod, $("#auth_code").val());
			}
		}
	</script>
</head>
<body style="padding:6px; overflow:hidden;">
	<div style="height: 30px;" id="layout1" class="l-layout" ligeruiid="layout1">
		<div style="top: 0px; height: 140px;" class="l-layout-top">
			<div class="l-layout-content" position="top" style="text-align: center;padding-top: 5px;">
				<div style="margin-bottom: 20px;">支付金额：<span id="payTotalAmt" style="color: red;font-size: 22px;"></span>&nbsp;&nbsp;元</div>
				扫描支付条码：&nbsp;&nbsp;<input name="auth_code" id="auth_code" style="width: 200px;"/><br/><br/>
				<input id="paybtn" class="l-button" value="支付" style="width: 100px;margin-top: 10px;" type="button" onclick="payment()"> 
			</div>
		</div>
	</div>
</body>
</html>