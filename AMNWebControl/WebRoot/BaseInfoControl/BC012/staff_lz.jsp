<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
  
  	<link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC012/bc012OutPageLZ.js"></script>

	<script language="JavaScript">
	</script>
		
</head>
<body style="padding:6px; overflow:hidden;">
	<form name="curRZFrom" method="post"  id="curRZFrom">
		<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:36px;" >
		<tr>
			<td><font color="red">申请门店</font></td>
			<td><input type="text" id="appchangecompid" name="curStaffchangeinfo.appchangecompid" style="width:120" readonly="true" /></td>
			<td><font color="blue">离职工号</font></td>
			<td><input type="text" id="changestaffno" name="curStaffchangeinfo.changestaffno" style="width:140" maxlength="20" readonly="true"/></td>
		</tr>
		<tr>
			<td><font color="blue">员工姓名</font></td>
			<td><input type="text" id="staffname" name="staffname" style="width:120"  maxlength="20" readonly="true"/></td>
			<td>内部编号</td>
			<td>
				<input type="text" id="staffmangerno" name="curStaffchangeinfo.staffmangerno" style="width:140" maxlength="8" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td>员工部门</td>
			<td>
				<input type="text" id="departmentshow" name="departmentshow" style="width:120" />
			</td>
			<td>员工职位</td>
			<td>
				<input type="text" id="positionshow" name="positionshow" style="width:140" />
			</td>
		</tr>
		<tr>
			<td>手机号码</td>
			<td><input type="text" id="staffphone" name="curStaffchangeinfo.staffphone" style="width:120"  maxlength="11" readonly="true"/></td>
			<td><font color="blue">身份证号</font></td>
			<td><input type="text" id="staffpcid" name="curStaffchangeinfo.staffpcid" style="width:140"  maxlength="18" readonly="true"/></td>
		</tr>
		
		<tr>
			<td>基本工资</td>
			<td>
				<input type="text" id="beforesalary" name="curStaffchangeinfo.beforesalary" style="width:120" maxlength="8" />
			</td>
			
			<td>业绩方式</td>
			<td>
				<input type="text" id="resulttyeshow" name="resulttyeshow" style="width:120"  readonly="true"/>
			</td>
		</tr>
		
		<tr>
			<td>业绩系数</td>
			<td>
				<input type="text" id="beforeyejirate" name="curStaffchangeinfo.beforeyejirate" style="width:120" maxlength="8"  readonly="true"/>
			</td>
			<td>业绩基数</td>
			<td>
				<input type="text" id="beforeyejiamt" name="curStaffchangeinfo.beforeyejiamt" style="width:120" maxlength="8"  readonly="true"/>
			</td>
		</tr>
		
		<tr>
			<td>离职职备注</td>
			<td colspan="2"><input type="text" id="remark" name="curStaffchangeinfo.remark" style="width:220"  maxlength="60"/></td>
			<td><select id="leaveltype" name="curStaffchangeinfo.leaveltype" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333; width:120;" >
					<option value="1">正常离职</option>
					<option value="2">自动离职</option>
			</select>
			</td>
		</tr>
	
		<tr>
			<td><font color="red">生效日期</font></td>
			<td><input type="text" id="validatestartdate" name="curStaffchangeinfo.validatestartdate" style="width:120"  maxlength="11" readonly="true"/></td>
		 	<td colspan="2" align="center"><div id="comfirmButton"></div></td>
		</tr>
		</table>
		<input type="hidden" id="beforedepartment" name="curStaffchangeinfo.beforedepartment"/>
		<input type="hidden" id="beforepostation" name="curStaffchangeinfo.beforepostation"/>
		<input type="hidden" id="beforeyejitype" name="curStaffchangeinfo.beforeyejitype"/>
	</form>					
   <script type="text/javascript">
   		
   </script>
</body>
</html>
