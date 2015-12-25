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
   			$("#handLoadTypeRate").ligerButton(
	         {
	             text: '确认设置折扣系数', width: 160,
		         click: function ()
		         {
		             handLoadTypeRate();
		         }
	     	});
	     	$("#strStartDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120',showtype:true });
            $("#strEndDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120',showtype:true });
			document.getElementById("strPrjTypeId").value=parent.curRecord.bprojecttypeid;
			document.getElementById("strPrjTypeName").value=parent.curRecord.bprojecttypename;
			document.getElementById("costRate").value=parent.curRecord.costrate;
			document.getElementById("changeRate").value=parent.curRecord.changerate;
			document.getElementById("strStartDate").value=parent.curRecord.startdate;
			document.getElementById("strEndDate").value=parent.curRecord.enddate;
		
	     });
	     
	     function handLoadTypeRate()
	     {
	     	if(document.getElementById("strStartDate").value=="" 
	     	|| document.getElementById("strEndDate").value=="")
	     	{
	     		$.ligerDialog.error("请确认折扣的起始日期!");
       	 		return;
	     	}
	     	if(document.getElementById("costRate").value*1==0)
	     	{
	     		$.ligerDialog.error("请确认消费折扣系数!");
       	 		return;
	     	}
	     	if(document.getElementById("changeRate").value*1==0)
	     	{
	     		$.ligerDialog.error("请确认兑换折扣系数!");
       	 		return;
	     	}
	     	parent.handCostrate(document.getElementById("strPrjTypeId").value,
	     						document.getElementById("strStartDate").value,
	     						document.getElementById("strEndDate").value,
	     						document.getElementById("costRate").value,
	     						document.getElementById("changeRate").value);
	     }
	 
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td width="80">项目类型</td>
		<td><input type="text" name="strPrjTypeId" id="strPrjTypeId"  readonly="true" style="width:80;background:#EDF1F8;" />
			<input type="text" name="strPrjTypeName" id="strPrjTypeName"  readonly="true" style="width:120;background:#EDF1F8;" />
		</td>
		</tr>
		<tr><td>开始日期</td><td><input type="text" name="strStartDate" id="strStartDate"  style="width:120;"/></td></tr>
		<tr><td>结束日期</td><td><input type="text" name="strEndDate"   id="strEndDate"  style="width:120;"/></td></tr>
		<tr><td>消费折扣</td><td><input type="text" name="costRate" id="costRate"  style="width:80;" onchange="validatePrice(this);"/></td></tr>
		<tr><td>兑换折扣</td><td><input type="text" name="changeRate" id="changeRate"  style="width:80;" onchange="validatePrice(this);"/></td></tr>
		
		<tr><td colspan="2" align=center>&nbsp;&nbsp;&nbsp;</td></tr>
		<tr><td colspan="2" align=center>&nbsp;&nbsp;&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="handLoadTypeRate"></div></td></tr>
	</table>	
	</div>

</body>
</html>
