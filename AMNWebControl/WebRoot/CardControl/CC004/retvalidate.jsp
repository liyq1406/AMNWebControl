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
		             confirmSetValidate();
		         }
	     	});
	    	document.getElementById("retCardNo").value=parent.strDYCardNo;	
	    	document.getElementById("oldvalidate").value=parent.strOldValidate;		
	     })
	    
	    function hotKeyOfSelf(key)
		{
				if(key==13)//回车
				{
					window.event.keyCode=9; //tab
					window.event.returnValue=true;
				}
			
		}
		
	     function confirmSetValidate()
	     {
	     	if(document.getElementById("newvalidate").value=="")
	     	{
	     		$.ligerDialog.error("请确认新的有效期");
	     		return ;
	     	}
	     	
	     	var params = "retCardNo="+document.getElementById("retCardNo").value;			
    		params =params+ "&newvalidate="+document.getElementById("newvalidate").value;	
    		var requestUrl ="cc004/retNewDate.action";
   			var responseMethod="retNewDateMessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	    
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td>抵用券号:</td><td><input type="text" name="retCardNo" id="retCardNo"  style="width:150;"  maxlength="20" readonly="true" style="width:200;background:#EDF1F8;" />
		</td></tr>
		<tr><td>原有效期:</td><td><input type="text" name="oldvalidate" id="oldvalidate"  style="width:150;"  maxlength="20" readonly="true" style="width:200;background:#EDF1F8;" /></td></tr>
		<tr><td>新有效期: </td><td><input type="text" name="newvalidate" id="newvalidate" style="width:150;" maxlength="20"/></td></tr>
		<tr><td></td><td ><font color="red">例 20141231 或 2014-12-31</font></td></tr>
		<tr><td colspan="2" align=center><div id="confirmSet"></div></td></tr>
	</table>	
	</div>

</body>

</html>
