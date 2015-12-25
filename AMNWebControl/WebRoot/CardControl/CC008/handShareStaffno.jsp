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
	             text: '确认新增(读经理卡)', width: 160,
		         click: function ()
		         {
		             handInsertShare();
		         }
	     	});
	     
	     });
	     
	     function handInsertShare()
	     {
	     	if(document.getElementById("inserStaffno").value=="")
	     	{
	     		$.ligerDialog.error("请输入新增工号!");
	     		return ;
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
	     	var requestUrl ="cc008/handInsertShare.action";
   			var responseMethod="handInsertShareMessage";
   			parent.addStaffNo=document.getElementById("inserStaffno").value;
   			parent.addstaffName=document.getElementById("inserStaffname").value;
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	     
	     function validateInserStaffno(obj)
	     {
	     	if(obj.value=="")
	     	{
	     		document.getElementById("inserStaffname").value="";
	     		return ;
	     	}
	     	var exists=0;
	     	for(var i=0;i<parent.lsStaffinfo.length;i++)
			{
			    if(obj.value==parent.lsStaffinfo[i].bstaffno)
			    {
			    	if(parent.lsStaffinfo[i].department=="004")
			    	{
			    		document.getElementById("inserStaffname").value=checkNull(parent.lsStaffinfo[i].staffname);;
			    		exists=1;
			    	}
			    	break;
			    }	
			}
			if(exists==0)
			{
			 	$.ligerDialog.warn("输入的员工编号不是美发部员工或不存在!");
			    obj.value="";
			    document.getElementById("inserStaffname").value="";
			}	
	     }
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td width="80">员工工号</td><td><input type="text" name="inserStaffno" id="inserStaffno"   style="width:120;" onchange="validateInserStaffno(this)" /></td></tr>
		<tr><td>员工名称 </td><td><input type="text" name="inserStaffname" id="inserStaffname"  readonly="true" style="width:120;background:#EDF1F8;" /></td></tr>
		<tr><td colspan="2" align=center>&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="handInsertShare"></div></td></tr>
	</table>	
	</div>

</body>
</html>
