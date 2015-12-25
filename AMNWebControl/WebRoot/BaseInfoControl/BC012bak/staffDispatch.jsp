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
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		 
		$(function ()
   		{
   			$("#dispatchStaff").ligerButton(
	         {
	             text: '确认派遣', width: 120,
		         click: function ()
		         {
		             dispatchStaff();
		         }
	     	});
	     	var today = new Date();
			var intYear=today.getYear();
	
			var intMonth=today.getMonth()+1;
		
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("dispatchdate").value=today;
			document.getElementById("tdispatchdate").value=today;
			document.getElementById("oldcompno").value=parent.document.getElementById("compno").value;
			document.getElementById("oldstaffno").value=parent.document.getElementById("staffno").value;
			document.getElementById("oldstaffinid").value=parent.document.getElementById("manageno").value;
			document.getElementById("olddepid").value=parent.document.getElementById("department").value;
			document.getElementById("oldpostion").value=parent.document.getElementById("position").value;
			document.getElementById("oldyjtype").value=parent.document.getElementById("resulttye").value;
			document.getElementById("oldyjrate").value=parent.document.getElementById("resultrate").value;
			document.getElementById("oldyjamt").value=parent.document.getElementById("baseresult").value;
			document.getElementById("oldstaffname").value=parent.document.getElementById("staffname").value;
	     });
	     
	     function dispatchStaff()
	     {
	     	if(document.getElementById("dispatchcompno").value=="")
	     	{
	     		$.ligerDialog.error("请确认派遣门店!");
	     		return ;
	     	}
	     	var params = "oldcompno="+document.getElementById("oldcompno").value;			
    		params =params+ "&oldstaffno="+document.getElementById("oldstaffno").value;	
    		params =params+ "&oldstaffinid="+document.getElementById("oldstaffinid").value;	
    			
    		params =params+ "&olddepid="+document.getElementById("olddepid").value;	
    		params =params+ "&oldpostion="+document.getElementById("oldpostion").value;	
    		params =params+ "&oldyjtype="+document.getElementById("oldyjtype").value;	
    		params =params+ "&oldyjrate="+document.getElementById("oldyjrate").value;	
    		params =params+ "&oldyjamt="+document.getElementById("oldyjamt").value;	
    		
    		params =params+ "&oldstaffname="+document.getElementById("oldstaffname").value;	
    		params =params+ "&dispatchcompno="+document.getElementById("dispatchcompno").value;	
    		params =params+ "&dispatchdate="+document.getElementById("dispatchdate").value;	
    		params =params+ "&tdispatchdate="+document.getElementById("tdispatchdate").value;
	     	var requestUrl ="bc012/dispatchStaff.action";
   			var responseMethod="dispatchStaffMessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td>门店号:</td><td><input type="text" name="oldcompno" id="oldcompno"  readonly="true" style="width:160;background:#EDF1F8;" /></td></tr>
		<tr><td>员工号: </td><td><input type="text" name="oldstaffno" id="oldstaffno"  readonly="true" style="width:80;background:#EDF1F8;" /><input type="text" name="oldstaffname" id="oldstaffname"  readonly="true" style="width:80;background:#EDF1F8;" /></td></tr>
		<tr><td>派遣门店:</td><td><input type="text" name="dispatchcompno" id="dispatchcompno"   style="width:160;" /></td></tr>
		<tr><td>派遣日期:</td><td><input type="text" name="dispatchdate" id="dispatchdate"  style="width:80;" /><input type="text" name="tdispatchdate" id="tdispatchdate"  style="width:80;" /></td></tr>
		<tr><td colspan="2" align=center><font color="red">[格式:20140201 或 2014-02-01]</font>
		<input type="hidden" name="oldstaffinid" id="oldstaffinid"/>
		<input type="hidden" name="olddepid" id="olddepid"/>
		<input type="hidden" name="oldpostion" id="oldpostion"/>
		<input type="hidden" name="oldyjtype" id="oldyjtype"/>
		<input type="hidden" name="oldyjrate" id="oldyjrate"/>
		<input type="hidden" name="oldyjamt" id="oldyjamt"/>
		</td></tr>
		<tr><td colspan="2" align=center><div id="dispatchStaff"></div></td></tr>
	</table>	
	</div>

</body>
</html>
