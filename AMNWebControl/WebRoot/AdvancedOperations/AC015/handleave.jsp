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
   			$("#handStaffLeave").ligerButton(
	         {
	             text: '确认请假(读经理卡)', width: 160,
		         click: function ()
		         {
		             handStaffLeave();
		         }
	     	});
	     	document.getElementById("leavestaffno").value=parent.strCurStaffNo;
	     	document.getElementById("leavestaffname").value=parent.strCurStaffName;
	     	document.getElementById("leavedate").value=parent.strCurDate;
	     	addOption("0","",document.getElementById("leavetype"));
           	var leavetype=parent.parent.gainCommonInfoByCode("QJLX",0);
		    for(var i=0;i<leavetype.length;i++)
			{
				addOption(leavetype[i].bparentcodekey,leavetype[i].parentcodevalue,document.getElementById("leavetype"));
			}	
			addOption("","",document.getElementById("fromtime"));
           	addOption("","",document.getElementById("totime"));
           	addOption("09:00","09:00",document.getElementById("fromtime"));
           	addOption("09:30","09:30",document.getElementById("fromtime"));
           	addOption("10:00","10:00",document.getElementById("fromtime"));
           	addOption("10:30","10:30",document.getElementById("fromtime"));
           	addOption("11:00","11:00",document.getElementById("fromtime"));
           	addOption("11:30","11:30",document.getElementById("fromtime"));
           	addOption("12:00","12:00",document.getElementById("fromtime"));
           	addOption("12:30","12:30",document.getElementById("fromtime"));
           	addOption("13:00","13:00",document.getElementById("fromtime"));
           	addOption("13:30","13:30",document.getElementById("fromtime"));
           	addOption("14:00","14:00",document.getElementById("fromtime"));
           	addOption("14:30","14:30",document.getElementById("fromtime"));
           	addOption("15:00","15:00",document.getElementById("fromtime"));
           	addOption("15:30","15:30",document.getElementById("fromtime"));
           	addOption("16:00","16:00",document.getElementById("fromtime"));
           	addOption("16:30","16:30",document.getElementById("fromtime"));
           	addOption("17:00","17:00",document.getElementById("fromtime"));
           	addOption("17:30","17:30",document.getElementById("fromtime"));
           	addOption("18:00","18:00",document.getElementById("fromtime"));
           	addOption("18:30","18:30",document.getElementById("fromtime"));
           	addOption("19:00","19:00",document.getElementById("fromtime"));
           	addOption("19:00","19:30",document.getElementById("fromtime"));
           	addOption("20:00","20:00",document.getElementById("fromtime"));
           	addOption("20:00","20:30",document.getElementById("fromtime"));
           	addOption("21:00","21:00",document.getElementById("fromtime"));
           	addOption("21:00","21:30",document.getElementById("fromtime"));
           	addOption("22:00","22:00",document.getElementById("fromtime"));
           	addOption("22:30","22:30",document.getElementById("fromtime"));
           	addOption("23:00","23:00",document.getElementById("fromtime"));
           	addOption("23:30","23:30",document.getElementById("fromtime"));
           	addOption("09:00","09:00",document.getElementById("totime"));
           	addOption("09:30","09:30",document.getElementById("totime"));
           	addOption("10:00","10:00",document.getElementById("totime"));
           	addOption("10:30","10:30",document.getElementById("totime"));
           	addOption("11:00","11:00",document.getElementById("totime"));
           	addOption("11:30","11:30",document.getElementById("totime"));
           	addOption("12:00","12:00",document.getElementById("totime"));
           	addOption("12:30","12:30",document.getElementById("totime"));
           	addOption("13:00","13:00",document.getElementById("totime"));
           	addOption("13:30","13:30",document.getElementById("totime"));
           	addOption("14:00","14:00",document.getElementById("totime"));
           	addOption("14:30","14:30",document.getElementById("totime"));
           	addOption("15:00","15:00",document.getElementById("totime"));
           	addOption("15:30","15:30",document.getElementById("totime"));
           	addOption("16:00","16:00",document.getElementById("totime"));
           	addOption("16:30","16:30",document.getElementById("totime"));
           	addOption("17:00","17:00",document.getElementById("totime"));
           	addOption("17:30","17:30",document.getElementById("totime"));
           	addOption("18:00","18:00",document.getElementById("totime"));
           	addOption("18:30","18:30",document.getElementById("totime"));
           	addOption("19:00","19:00",document.getElementById("totime"));
           	addOption("19:30","19:30",document.getElementById("totime"));
           	addOption("20:00","20:00",document.getElementById("totime"));
           	addOption("20:30","20:30",document.getElementById("totime"));
           	addOption("21:00","21:00",document.getElementById("totime"));
           	addOption("21:30","21:30",document.getElementById("totime"));
           	addOption("22:00","22:00",document.getElementById("totime"));
           	addOption("22:30","22:30",document.getElementById("totime"));
           	addOption("23:00","23:00",document.getElementById("totime"));
           	addOption("23:30","23:30",document.getElementById("totime"));
           	addOption("00:00","00:00",document.getElementById("totime"));
           	addOption("00:30","00:30",document.getElementById("totime"));
           	addOption("01:00","01:00",document.getElementById("totime"));
           	addOption("01:30","01:30",document.getElementById("totime"));
           	addOption("02:00","02:00",document.getElementById("totime"));
           	addOption("02:30","02:30",document.getElementById("totime"));
           	addOption("03:00","03:00",document.getElementById("totime"));
           	addOption("03:30","03:30",document.getElementById("totime"));
           	addOption("04:00","04:00",document.getElementById("totime"));
           	addOption("04:30","04:30",document.getElementById("totime"));
           	addOption("05:00","05:00",document.getElementById("totime"));
           	addOption("05:30","05:30",document.getElementById("totime"));
           	addOption("06:00","06:00",document.getElementById("totime"));
           	addOption("","",document.getElementById("totime"));
	     });
	     
	     function handStaffLeave()
	     {
	     	if(document.getElementById("leavetype").value=="0")
	     	{
	     		$.ligerDialog.error("请确请假类型!");
	     		return ;
	     	}
	     	if(document.getElementById("leavemark").value=="")
	     	{
	     		$.ligerDialog.error("请确请假原因!");
	     		return ;
	     	}
	     	if(document.getElementById("fromtime").value=="")
	     	{
	     		$.ligerDialog.error("请确请假起始时间!");
	     		return ;
	     	}
	     	if(document.getElementById("totime").value=="")
	     	{
	     		$.ligerDialog.error("请确请假结束时间!");
	     		return ;
	     	}
	     	try
	     	{
	     			var cardNo="";
	     			if(parent.document.getElementById("shoplevel").value!="1")
	     			{
				     	var CardControl=parent.parent.document.getElementById("CardCtrl");
						CardControl.Init(parent.parent.commtype,parent.parent.prot,parent.parent.password1,parent.parent.password2,parent.parent.password3);
						cardNo=CardControl.ReadCard();
						if(cardNo=="")
						{
							$.ligerDialog.error("请初始化卡号");
							return;
				    	}
				    	
		    		}
		    	}catch(e){alert(e.message);}
		     	var params = "leavestaffno="+document.getElementById("leavestaffno").value;			
	    		params =params+ "&leavedate="+document.getElementById("leavedate").value;	
	    		params =params+ "&leavetype="+document.getElementById("leavetype").value;
	    		params =params+ "&strFromTime="+document.getElementById("fromtime").value;	
	    		params =params+ "&strToTime="+document.getElementById("totime").value;	
	    		params =params+ "&leavemark="+document.getElementById("leavemark").value;	
	    		params =params+ "&mangerCardNo="+cardNo;	
		     	var requestUrl ="ac015/handStaffLeave.action";
	   			var responseMethod="handStaffLeaveMessage";
	   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td width="80">员工工号</td><td><input type="text" name="leavestaffno" id="leavestaffno"  readonly="true" style="width:120;background:#EDF1F8;" /></td></tr>
		<tr><td>员工名称 </td><td><input type="text" name="leavestaffname" id="leavestaffname"  readonly="true" style="width:120;background:#EDF1F8;" />&nbsp;&nbsp;起始时间&nbsp;<select id="fromtime" name="fromtime" style="width:60"></select></td></tr>
		<tr><td>请假日期</td><td><input type="text" name="leavedate" id="leavedate"  readonly="true" style="width:120;background:#EDF1F8;"/>&nbsp;&nbsp;结束时间&nbsp;<select id="totime" name="totime" style="width:60"></select></td></tr>
		<tr><td>请假类型</td><td><select id="leavetype" name="leavetype" style="width:120"></select></td></tr>
		<tr><td>请假备注</td><td><input type="text" name="leavemark" id="leavemark"  style="width:220"/></td></tr>
		<tr><td colspan="2" align=center><div id="handStaffLeave"></div></td></tr>
	</table>	
	</div>

</body>
</html>
