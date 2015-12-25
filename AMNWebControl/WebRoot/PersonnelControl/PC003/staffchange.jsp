<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
  <body style="margin:0;">
	<table  align="center" border="0" cellspacing="1" cellpadding="0" style="line-height:20px;" >
	<tr>
	<td height="40" align="center"><font size="3" style="padding-top:2px;"><b>上海阿玛尼企业管理</b></font></td>
	</tr>
	<tr>
		<td  height="30" align="center" ><font size="2" style="padding-top:2px;"><b>　<员工异动>审查表</b></font></td>
	</tr>

	</table>

	<table border="0" cellspacing="1" cellpadding="0" style=" font-size:16px; border:1px solid #000; padding:3px;line-height:30px;"  width="95%">
			<tr>
				<td align="left" height="25";>&nbsp;&nbsp;&nbsp;调动类型：<label id="changetype">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
				<td align="left" height="25";>&nbsp;&nbsp;&nbsp;调动单号：<label id="billno">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
		
			<tr >
			<td >&nbsp;&nbsp;&nbsp;员工姓名：<label id="staffname">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td  >&nbsp;&nbsp;&nbsp;手机号码：<label id="mobile">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
			
			<tr >
			<td >&nbsp;&nbsp;&nbsp;申请门店：<label id="appcompid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
		
			<td   align="left">&nbsp;&nbsp;&nbsp;身份证号码：<label id="staffpCDID">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			
			</tr>
			
			<tr >
			<td align="left">&nbsp;&nbsp;&nbsp;原员工工号：<label id="oldstaffno">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td align="left">&nbsp;&nbsp;&nbsp;新员工工号：<label id="newstaffno">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
			
			<tr >
			<td align="left">&nbsp;&nbsp;&nbsp;原部门：<label id="olddepartments">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td align="left">&nbsp;&nbsp;&nbsp;新部门：<label id="newdepartments">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
			
			<tr >
			<td align="left">&nbsp;&nbsp;&nbsp;原职位：<label id="oldjobs">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td align="left">&nbsp;&nbsp;&nbsp;新职位：<label id="newjobs">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
		
			<tr >
			<td align="left">&nbsp;&nbsp;&nbsp;调前工资：<label id="oldsalary">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>元/月</td>
			<td align="left">&nbsp;&nbsp;&nbsp;调后工资：<label id="newsalary">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>元/月</td>
			</tr>
			
			<tr >
			<td align="left">&nbsp;&nbsp;&nbsp;调前业绩方式：<label id="beforeyejitype">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td align="left">&nbsp;&nbsp;&nbsp;调后业绩方式：<label id="afteryejitype">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
		
			<tr >
			<td align="left">&nbsp;&nbsp;&nbsp;调前业绩系数：<label id="beforeyejirate">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td align="left">&nbsp;&nbsp;&nbsp;调后业绩系数：<label id="afteryejirate">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
		
			<tr>
			<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;门店审核：<label id="exam_store">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
			
			
			<tr >
			<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;人事专员审核：<label id="exam_perS">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
			
			
			<tr>
			<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;人事经理审核：<label id="exam_perM">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
		
			
			<tr >
			<td >&nbsp;&nbsp;&nbsp;申请日期：<label id="appDate">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
			<td 　align="left"><b>生效日期：<label id="exDate"> &nbsp;</label>　&nbsp;&nbsp;&nbsp;</b></td>
			</tr>
		
			<tr >
			<td colspan="2" ><b>&nbsp;&nbsp;&nbsp;备注:</b><label id="remark">&nbsp;&nbsp;&nbsp;</label></td>
			</tr>
			
</table  >
<script type="text/javascript">
if(parent.curRecord.bchangetype==0)
	document.getElementById("changetype").innerHTML="薪资调整";
else if(parent.curRecord.bchangetype==1)
	document.getElementById("changetype").innerHTML="离职申请";
else if(parent.curRecord.bchangetype==2)
	document.getElementById("changetype").innerHTML="入职申请";
else if(parent.curRecord.bchangetype==3)
	document.getElementById("changetype").innerHTML="重回公司";
else if(parent.curRecord.bchangetype==4)
	document.getElementById("changetype").innerHTML="请假申请";
	
document.getElementById("appcompid").innerHTML=parent.curRecord.appchangecompname;
document.getElementById("billno").innerHTML=parent.curRecord.bchangebillid;
document.getElementById("staffname").innerHTML=parent.curRecord.changestaffname;
document.getElementById("staffpCDID").innerHTML=parent.curRecord.staffpcid;
document.getElementById("appDate").innerHTML=parent.curRecord.changedate;
document.getElementById("oldstaffno").innerHTML=parent.curRecord.changestaffno;

document.getElementById("olddepartments").innerHTML=parent.parent.loadCommonControlValue('BMZL',0, checkNull(parent.curRecord.beforedepartment));
document.getElementById("newdepartments").innerHTML=parent.parent.loadCommonControlValue('BMZL',0, checkNull(parent.curRecord.afterdepartment));
document.getElementById("oldjobs").innerHTML=checkNull(parent.parent.loadCommonControlValue('GZGW',0, checkNull(parent.curRecord.beforepostation)));
document.getElementById("oldsalary").innerHTML=parent.curRecord.beforesalary;
document.getElementById("newstaffno").innerHTML=parent.curRecord.afterstaffno;
document.getElementById("newjobs").innerHTML=checkNull(parent.parent.loadCommonControlValue('GZGW',0, checkNull(parent.curRecord.afterpostation))); 
document.getElementById("newsalary").innerHTML=parent.curRecord.aftersalary;
document.getElementById("exDate").innerHTML=parent.curRecord.validatestartdate;
document.getElementById("mobile").innerHTML=parent.curRecord.staffphone;
document.getElementById("beforeyejitype").innerHTML=checkNull(parent.parent.loadCommonControlValue('YJFS',0, checkNull(parent.curRecord.beforeyejitype))); 

document.getElementById("afteryejitype").innerHTML=checkNull(parent.parent.loadCommonControlValue('YJFS',0, checkNull(parent.curRecord.afteryejitype))); 

document.getElementById("beforeyejirate").innerHTML=parent.curRecord.beforeyejirate;
document.getElementById("afteryejirate").innerHTML=parent.curRecord.afteryejirate;
document.getElementById("exam_store").innerHTML ="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+parent.curRecord.checkstaffid+"&nbsp;&nbsp;&nbsp;&nbsp;["+parent.curRecord.checkdate+"]";
document.getElementById("exam_perS").innerHTML =parent.curRecord.checkinheadstaffid+"&nbsp;&nbsp;&nbsp;&nbsp;["+parent.curRecord.checkinheaddate+"]";
document.getElementById("exam_perM").innerHTML =parent.curRecord.comfirmstaffid+"&nbsp;&nbsp;&nbsp;&nbsp;["+parent.curRecord.comfirmdate+"]";
document.getElementById("remark").innerHTML=parent.curRecord.remark;

function checkNull(strValue)
{
	if(strValue == null)
	{
		return "";
	}
	else
	{
		return strValue;
	}
}

</script>

</body>
</html>
