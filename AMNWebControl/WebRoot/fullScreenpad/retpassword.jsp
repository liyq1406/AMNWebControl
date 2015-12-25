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
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script language="JavaScript">
		 
		$(function ()
   		{
   			$("#confirmSet").ligerButton(
	         {
	             text: '确认设置', width: 120,
		         click: function ()
		         {
		             confirmSetPass();
		         }
	     	});
	    	document.getElementById("retUserNo").value=parent.document.getElementById("strUserId").value;	
	    	document.getElementById("retUserName").value=parent.document.getElementById("strUserName").value;		
	     })
	    
	    function hotKeyOfSelf(key)
		{
				if(key==13)//回车
				{
					window.event.keyCode=9; //tab
					window.event.returnValue=true;
				}
			
		}
		
	     function confirmSetPass()
	     {
	     	if(document.getElementById("retPassword").value=="")
	     	{
	     		$.ligerDialog.error("请确认新密码");
	     		return ;
	     	}
	     	if(document.getElementById("retPassword").value!=document.getElementById("retConfirmParrword").value)
	     	{
	     		$.ligerDialog.error("新密码与确认密码不一致");
	     		return ;
	     	}
	     	var params = "retUserNo="+document.getElementById("retUserNo").value;			
    		params =params+ "&retPassword="+document.getElementById("retPassword").value;	
    		var requestUrl ="retPassword.action";
   			var responseMethod="retPasswordMessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	    
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td>使用者号:</td><td><input type="text" name="retUserNo" id="retUserNo"  style="width:80;"  maxlength="20" readonly="true" style="width:200;background:#EDF1F8;" />
		<input type="text" name="retUserName" id="retUserName"  style="width:100;"  maxlength="20" readonly="true" style="width:200;background:#EDF1F8;" /></td></tr>
		<tr><td>新密码:</td><td><input type="password" name="retPassword" id=""retPassword""  style="width:200;"  maxlength="20" /></td></tr>
		<tr><td>确认密码: </td><td><input type="password" name="retConfirmParrword" id="retConfirmParrword" style="width:200;" maxlength="20"/></td></tr>
		<tr><td colspan="2" align=center>&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="confirmSet"></div></td></tr>
	</table>	
	</div>

</body>

</html>
