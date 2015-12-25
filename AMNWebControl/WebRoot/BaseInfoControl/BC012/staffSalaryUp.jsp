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
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		 
		$(function ()
   		{
   			$("#dispatchStaff").ligerButton(
	         {
	             text: '确认调整', width: 120,
		         click: function ()
		         {
		             dispatchSalaryUp();
		         }
	     	});
	     	var today = new Date();
			var intYear=today.getYear();
	
			var intMonth=today.getMonth()+1;
		
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			addOption("","",document.getElementById("oldyjtype"));
			addOption("","",document.getElementById("newyjtype"));
			var yitype=parent.parent.gainCommonInfoByCode("YJFS",0);
		    for(var i=0;i<yitype.length;i++)
			{
				addOption(yitype[i].bparentcodekey,yitype[i].parentcodevalue,document.getElementById("oldyjtype"));
				addOption(yitype[i].bparentcodekey,yitype[i].parentcodevalue,document.getElementById("newyjtype"));
			}
			//document.getElementById("dispatchdate").value=today;
			if(intMonth==1 || intMonth==3 || intMonth==5 || intMonth==7 || intMonth==8 || intMonth==10 || intMonth==12 )
				document.getElementById("dispatchdate").value= intYear.toString()+"-"+fullStr(intMonth.toString())+"-31";
			else if(intMonth==4 || intMonth==6 || intMonth==9 || intMonth==11  )
				document.getElementById("dispatchdate").value= intYear.toString()+"-"+fullStr(intMonth.toString())+"-30";
			else
				document.getElementById("dispatchdate").value= intYear.toString()+"-"+fullStr(intMonth.toString())+"-28";
	     
			document.getElementById("oldcompno").value=parent.document.getElementById("compno").value;
			document.getElementById("oldstaffno").value=parent.document.getElementById("staffno").value;
			document.getElementById("oldstaffinid").value=parent.document.getElementById("manageno").value;
			document.getElementById("olddepid").value=parent.document.getElementById("department").value;
			document.getElementById("oldpostion").value=parent.document.getElementById("position").value;
			document.getElementById("oldyjtype").value=parent.document.getElementById("resulttye").value;
			document.getElementById("oldyjrate").value=parent.document.getElementById("resultrate").value;
			document.getElementById("oldyjamt").value=parent.document.getElementById("baseresult").value;
			document.getElementById("oldSalary").value=parent.document.getElementById("basesalary").value;
			document.getElementById("newSalary").value=parent.document.getElementById("basesalary").value;
			document.getElementById("newyjtype").value=parent.document.getElementById("resulttye").value;
			document.getElementById("oldstaffname").value=parent.document.getElementById("staffname").value;
			//$("#dispatchdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
	     });
	     
	     function dispatchSalaryUp()
	     {
	     	
	     	var params = "oldcompno="+document.getElementById("oldcompno").value;			
    		params =params+ "&oldstaffno="+document.getElementById("oldstaffno").value;	
    		params =params+ "&oldstaffinid="+document.getElementById("oldstaffinid").value;	
    		params =params+ "&olddepid="+document.getElementById("olddepid").value;	
    		params =params+ "&oldpostion="+document.getElementById("oldpostion").value;	
    		params =params+ "&oldyjtype="+document.getElementById("oldyjtype").value;	
    		params =params+ "&oldyjrate="+document.getElementById("oldyjrate").value;	
    		params =params+ "&oldyjamt="+document.getElementById("oldyjamt").value;
    		params =params+ "&oldSalary="+document.getElementById("oldSalary").value;	
    		params =params+ "&newyjtype="+document.getElementById("newyjtype").value;	
    		params =params+ "&newyjrate="+document.getElementById("newyjrate").value;	
    		params =params+ "&newyjamt="+document.getElementById("newyjamt").value;
    		params =params+ "&newSalary="+document.getElementById("newSalary").value;
    		params =params+ "&dispatchdate="+document.getElementById("dispatchdate").value;	
	     	var requestUrl ="bc012/dispatchSalaryUp.action";
   			var responseMethod="dispatchSalaryUpMessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr>
		<td>员工工号: </td>
		<td ><input type="text" name="oldstaffno" id="oldstaffno"  readonly="true" style="width:100;background:#EDF1F8;" /> </td>
		<td colspan="2"><input type="text" name="oldstaffname" id="oldstaffname"  readonly="true" style="width:140;background:#EDF1F8;" />
		</td>
		</tr>
		<tr>
		<td>原工资:</td>
		<td><input type="text" name="oldSalary" id="oldSalary"  readonly="true" style="width:100;background:#EDF1F8;" /></td>
		<td><font color="blue">新工资:</font>  </td>
		<td><input type="text" name="newSalary" id="newSalary"  style="width:100;" onchange="validatePrice(this)" />
		</td>
		</tr>
		<tr>
		<td>原业绩方式:</td>
		<td><select name="oldyjtype" id="oldyjtype" style="width:100"></select></td>
		<td><font color="blue">新业绩方式:</font> </td>
		<td><select name="newyjtype" id="newyjtype" style="width:100"></select>
		</td>
		</tr>
		<tr>
		<td>原业绩系数:</td>
		<td><input type="text" name="oldyjrate" id="oldyjrate"  readonly="true" style="width:100;background:#EDF1F8;" /></td>
		<td><font color="blue">新业绩系数:</font>  </td>
		<td><input type="text" name="newyjrate" id="newyjrate"  style="width:100;" onchange="validatePrice(this)"/>
		</td>
		</tr>
		<tr>
		<td>原业绩基数:</td>
		<td><input type="text" name="oldyjamt" id="oldyjamt"  readonly="true" style="width:100;background:#EDF1F8;" /></td>
		<td><font color="blue">新业绩基数:</font>  </td>
		<td><input type="text" name="newyjamt" id="newyjamt"   style="width:100;" onchange="validatePrice(this)"/>
		</td>
		</tr>
		<tr>
		<td><font color="blue">生效日期:</font> </td>
		<td colspan="2" ><input type="text" name="dispatchdate" id="dispatchdate"  readonly="true" style="width:120;" />
		<input type="hidden" name="oldcompno" id="oldcompno"/>
		<input type="hidden" name="oldstaffinid" id="oldstaffinid"/>
		<input type="hidden" name="olddepid" id="olddepid"/>
		<input type="hidden" name="oldpostion" id="oldpostion"/>
		</td><td ><div id="dispatchStaff"></div></td></tr>
	</table>	
	</div>

</body>
</html>
