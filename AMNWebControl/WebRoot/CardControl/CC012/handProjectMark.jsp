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
   			$("#handInsertMark").ligerButton(
	         {
	             text: '确认备注', width: 160,
		         click: function ()
		         {
		             handInsertMark();
		         }
	     	});
	     	
	     	addOption("","请选择备注  (或在下方文本编辑备注)",document.getElementById("targetMark"));
           	var marksource=parent.parent.gainCommonInfoByCode("LCBZ",0);
		    for(var i=0;i<marksource.length;i++)
			{
				addOption(marksource[i].parentcodevalue,marksource[i].parentcodevalue,document.getElementById("targetMark"));
			}		
           
	     
	     });
	     
	     function handInsertMark()
	     {
	     	if(document.getElementById("factMark").value=="")
	     	{
	     		$.ligerDialog.error("请输入本疗程备注!");
	     		return ;
	     	}
	     	
	     	parent.document.getElementById("fastMask").value=document.getElementById("factMark").value;
	     	parent.document.getElementById("fastProDkAmt").focus();
	     	parent.document.getElementById("fastProDkAmt").select();
	     	parent.commoninfodivdetial.updateRow(parent.curRecord,{changemark:parent.document.getElementById("fastMask").value});
	     	parent.markcustomerDialog.close();
	     }
	     
	     function loadMark(obj)
	     {
	     	document.getElementById("factMark").value=obj.value;
	     }
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td width="60">标准备注</td><td><select name="targetMark" id="targetMark"   style="width:300;" onchange="loadMark(this)" ></select></td></tr>
		<tr><td>核对备注</td><td><input type="text" name="factMark" id="factMark"  style="width:300;" /></td></tr>
		<tr><td colspan="2" align=center>&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="handInsertMark"></div></td></tr>
	</table>	
	</div>

</body>
</html>
