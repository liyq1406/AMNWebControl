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
   			addOption("","",document.getElementById("strEntryCompId"));
           	var lsCompanyinfos=parent.parent.lsCompanyinfos;
		    for(var i=0;i<lsCompanyinfos.length;i++)
			{
				addOption(lsCompanyinfos[i].compno,lsCompanyinfos[i].compname,document.getElementById("strEntryCompId"));
			}
			
	     	addOption("","",document.getElementById("strMachineVersion"));
           	var machinetypes=parent.parent.gainCommonInfoByCode("KQBB",0);
		    for(var i=0;i<machinetypes.length;i++)
			{
				addOption(machinetypes[i].bparentcodekey,machinetypes[i].parentcodevalue,document.getElementById("strMachineVersion"));
			}
			
	    	$("#strStartDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120',showtype:true  });
            $("#strEndDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' ,showtype:true });	
            $("#handBandComp").ligerButton(
	         {
	             text: '确认新增', width: 160,
		         click: function ()
		         {
		             handBandCompMachine();
		         }
	     	});
	     });
	     
	     function handBandCompMachine()
	     {
	     	if(document.getElementById("strEntryCompId").value=="")
	     	{
	     		$.ligerDialog.error("请确认门店编号!");
	     		return ;
	     	}
	     	if(document.getElementById("strEntryMachineId").value=="")
	     	{
	     		$.ligerDialog.error("请确认机器编号!");
	     		return ;
	     	}
	     	if(document.getElementById("strMachineVersion").value=="")
	     	{
	     		$.ligerDialog.error("请确认机器版本!");
	     		return ;
	     	}
	     	if(document.getElementById("strStartDate").value=="")
	     	{
	     		$.ligerDialog.error("请确认启用日期!");
	     		return ;
	     	}
	     	var params =" strEntryCompId="+document.getElementById("strEntryCompId").value;
		    params=params+"&strEntryMachineId="+document.getElementById("strEntryMachineId").value;
		    params=params+"&strMachineVersion="+document.getElementById("strMachineVersion").value;
		    params=params+"&strEntryMachineIP="+document.getElementById("strEntryMachineIP").value;
		    params=params+"&strStartDate="+document.getElementById("strStartDate").value;
		    params=params+"&strEndDate="+document.getElementById("strEndDate").value;
		    var requestUrl ="ac019/handBandCompMachine.action";
	   		var responseMethod="handBandCompMachineMessage";
		   	parent.sendRequestForParams_p(requestUrl,responseMethod,params);
	     }
	    
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 26px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td >门店编号</td><td><select name="strEntryCompId" id="strEntryCompId" style="width:160"></select></td></tr>
		<tr><td >机器编号</td><td><input type="text" name="strEntryMachineId" id="strEntryMachineId"   style="width:160;"/></td></tr>
		<tr><td >机器版本</td><td><select name="strMachineVersion" id="strMachineVersion" style="width:160"></select></td></tr>
		<tr><td >门店IP号</td><td><input type="text" name="strEntryMachineIP" id="strEntryMachineIP"   style="width:160;"/></td></tr>
		<tr><td >起始日期</td><td><input type="text" name="strStartDate" id="strStartDate"   style="width:120;" onchange="validateInserStaffno(this)" /></td></tr>
		<tr><td >结束日期</td><td><input type="text" name="strEndDate" id="strEndDate"   style="width:120;" onchange="validateInserStaffno(this)" /></td></tr>
		<tr><td colspan="2" align=center>&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="handBandComp"></div></td></tr>
	</table>	
	</div>

</body>
</html>
