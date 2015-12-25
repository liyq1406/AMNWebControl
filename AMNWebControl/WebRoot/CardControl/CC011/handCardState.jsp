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
   			$("#bhandCardState").ligerButton(
	         {
	             text: '确认解冻(读经理卡)', width: 160,
		         click: function ()
		         {
		             bhandCardState();
		         }
	     	});
	     	document.getElementById("costCardNo").value=parent.document.getElementById("cscardno").value;
	     });
	     
	     function bhandCardState()
	     {
	     	if(document.getElementById("handMemberName").value=="")
	     	{
	     		$.ligerDialog.error("请输入会员姓名!");
				return;
	     	}
	     	if(document.getElementById("handPhone").value=="" && document.getElementById("handPcid").value=="")
	     	{
	     		$.ligerDialog.error("请输入手机号码或身份证号!");
				return;
	     	}
	     	var CardControl=parent.parent.document.getElementById("CardCtrl");
			CardControl.Init(parent.parent.commtype,parent.parent.prot,parent.parent.password1,parent.parent.password2,parent.parent.password3);
			var cardNo=CardControl.ReadCard();
			if(cardNo=="")
			{
				$.ligerDialog.error("请初始化卡号");
				return;
	    	}
	     	var params ="mangerCardNo="+cardNo;	
	     	params=params+"&strCardNo="+document.getElementById("costCardNo").value;
	     	params=params+"&handMemberName="+document.getElementById("handMemberName").value;
	     	params=params+"&handPcid="+document.getElementById("handPcid").value;
	     	params=params+"&handPhone="+document.getElementById("handPhone").value;
	     	var requestUrl ="cc011/handCardState.action";
   			var responseMethod="handCardStateMessage";;
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	     
	    
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td width="80">会员卡号</td><td><input type="text" name="costCardNo" id="costCardNo"  readonly="true"  style="width:120;background:#EDF1F8;"/></td></tr>
		<tr><td><font color="red">*</font> 会员姓名 </td><td><input type="text" name="handMemberName" id="handMemberName"  style="width:120;" /></td></tr>
		<tr><td><font color="blue">*</font> 手机号码 </td><td><input type="text" name="handPhone" id="handPhone"   style="width:120;" /></td></tr>
		<tr><td><font color="blue">*</font> 身份证号 </td><td><input type="text" name="handPcid" id="handPcid"   style="width:160;" /></td></tr>
		<tr><td colspan="2" align=center>&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="bhandCardState"></div></td></tr>
	</table>	
	</div>

</body>
</html>
