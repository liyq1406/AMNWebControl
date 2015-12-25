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
   			$("#handInsertShare").ligerButton(
	         {
	             text: '确认消费(读经理卡)', width: 160,
		         click: function ()
		         {
		             handInsertShare();
		         }
	     	});
	     	document.getElementById("costCardNo").value=parent.document.getElementById("cscardno").value;
	     	document.getElementById("tCostAmt").value=parent.vTCostAmt;
	     	document.getElementById("tMrCostCount").value=parent.vTMrCostCount;
	     	document.getElementById("tMfCostCount").value=parent.vTMfCostCount;
	     });
	     
	     function handInsertShare()
	     {
	     	var CardControl=parent.parent.document.getElementById("CardCtrl");
			CardControl.Init(parent.parent.commtype,parent.parent.prot,parent.parent.password1,parent.parent.password2,parent.parent.password3);
			var cardNo=CardControl.ReadCard();
			if(cardNo=="")
			{
				$.ligerDialog.error("请初始化卡号");
				return;
	    	}
	     	var params ="mangerCardNo="+cardNo;	
	     	var requestUrl ="cc016/handInsertShare.action";
   			var responseMethod="handInsertShareMessage";;
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	     
	    
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td width="80">会员卡号</td><td><input type="text" name="costCardNo" id="costCardNo"  readonly="true"  style="width:120;background:#EDF1F8;"/></td></tr>
		<tr><td>储值账户累计消费 </td><td><input type="text" name="tCostAmt" id="tCostAmt"  readonly="true" style="width:60;background:#EDF1F8;" />元</td></tr>
		<tr><td>美容疗程累计消费 </td><td><input type="text" name="tMrCostCount" id="tMrCostCount"  readonly="true" style="width:60;background:#EDF1F8;" />次</td></tr>
		<tr><td>美发疗程累计消费 </td><td><input type="text" name="tMfCostCount" id="tMfCostCount"  readonly="true" style="width:60;background:#EDF1F8;" />次</td></tr>
		<tr><td colspan="2" align=center>&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="handInsertShare"></div></td></tr>
	</table>	
	</div>

</body>
</html>
