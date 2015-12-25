<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		 
		$(function ()
   		{
   			$("#modifyCardInfo").ligerButton(
	         {
	             text: '确认更新', width: 160,
		         click: function ()
		         {
		             modifyCardInfo();
		         }
	     	});
			document.getElementById("strCardNo").value=parent.strCurCardNo;
			document.getElementById("strMemberName").value=parent.document.getElementById("membername").value;
			document.getElementById("strAccountType").value=parent.strAccountType;
			document.getElementById("strAccountTypeName").value=parent.strAccountTypeName;
			document.getElementById("accountBlance").value=parent.cardBalance;
			document.getElementById("accountDebts").value=parent.cardDebts;
	     });
	     
	     function modifyCardInfo()
	     {
	     	if(document.getElementById("strModifyMark").value=="")
	     	{
	     		$.ligerDialog.error("请输入修改备注!");
	     		return;
	     	}
	     	$.ligerDialog.confirm('确认将会员卡 '+document.getElementById("strCardNo").value+' 的'+document.getElementById("strAccountTypeName").value+'由'+document.getElementById("accountBlance").value+'改为'+document.getElementById("newaccountBlance").value, function (result)
			{
				if( result==true)
	           	{	
	           		parent.confirmModifyAccount(document.getElementById("strCardNo").value,
	           							document.getElementById("strAccountType").value,
	           							document.getElementById("accountBlance").value,
	           							document.getElementById("newaccountBlance").value,
	           							document.getElementById("accountDebts").value,
	           							document.getElementById("newaccountDebts").value,
	           							document.getElementById("strModifyMark").value);
	           	}
			});
	     }
	     
	    
	 
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td width="80">会员卡号</td>
		<td>
			<input type="text" name="strCardNo" id="strCardNo"  readonly="true" style="width:140;background:#EDF1F8;" />
		</td>
		</tr>
		<tr><td>会员姓名</td>
		<td>
			<input type="text" name="strMemberName" id="strMemberName"  readonly="true" style="width:140;background:#EDF1F8;" />
		</td>
		</tr>
		<tr><td>账户类型</td>
		<td>
			<input type="text" name="strAccountTypeName" id="strAccountTypeName"  readonly="true" style="width:140;background:#EDF1F8;" />
			<input type="hidden" id="strAccountType" name="strAccountType" />
		</td>
		</tr>
		<tr><td>账户余额</td>
		<td>
			<input type="text" name="accountBlance" id="accountBlance"  readonly="true" style="width:60;background:#EDF1F8;" />
			<font color="red">>></font>
			<input type="text" name="newaccountBlance" id="newaccountBlance"  style="width:60;" onchange="validatePrice(this)"/>
		</td>
		</tr>
		<tr><td>账户欠款</td>
		<td>
			<input type="text" name="accountDebts" id="accountDebts"  readonly="true" style="width:60;background:#EDF1F8;" />
			<font color="red">>></font>
			<input type="text" name="newaccountDebts" id="newaccountDebts"   style="width:60;" onchange="validatePrice(this)"/>
		</td>
		</tr>
		<tr><td>修改备注</td>
		<td>
			<input type="text" name="strModifyMark" id="strModifyMark"   style="width:180;" />
		</td>
		</tr>
		<tr><td colspan="2" align=center>&nbsp;&nbsp;&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="modifyCardInfo"></div></td></tr>
	</table>	
	</div>

</body>
</html>
