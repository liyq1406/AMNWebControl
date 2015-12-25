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
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC012/bc012OutPageDD.js"></script>

	<script language="JavaScript">
	</script>
		
</head>
<body style="padding:6px; overflow:hidden;">
	<form name="curRZFrom" method="post"  id="curRZFrom">
		<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:36px;" >
		<tr>
			<td><font color="blue">调动工号</font></td>
			<td><input type="text" id="changestaffno" name="curStaffchangeinfo.changestaffno" style="width:120" maxlength="20" readonly="true"/></td>
			<td><font color="red">新工号</font></td>
			<td><input type="text" id="afterstaffno" name="curStaffchangeinfo.afterstaffno" style="width:140" maxlength="20" />
				<input type="hidden" id="appchangecompid" name="curStaffchangeinfo.appchangecompid" style="width:120" readonly="true" /></td>
			
		</tr>
	
		<tr>
			<td>员工原部门</td>
			<td>
				<input type="text" id="departmentshow" name="departmentshow" style="width:120" />
			</td>
			<td>员工新部门</td>
			<td>
				<select id="afterdepartment" name="curStaffchangeinfo.afterdepartment" style="width:140" >
				</select>
				<input type="hidden" id="newdepartmentshow" name="newdepartmentshow"  />
			</td>
		</tr>
		<tr>
			<td>员工原职位</td>
			<td>
				<input type="text" id="positionshow" name="positionshow" style="width:120" />
			</td>
			<td>员工新职位</td>
			<td>
				<select id="afterpostation" name="curStaffchangeinfo.afterpostation" style="width:140" >
				</select>
				<input type="hidden" id="newpositionshow" name="newpositionshow" />
			</td>
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
			<td>手机号码</td>
			<td><input type="text" id="staffphone" name="curStaffchangeinfo.staffphone" style="width:120"  maxlength="11" readonly="true"/></td>
			<td><font color="blue">身份证号</font></td>
			<td><input type="text" id="staffpcid" name="curStaffchangeinfo.staffpcid" style="width:140"  maxlength="18" readonly="true"/></td>
		</tr>
		<tr>
			<td>原基本工资</td>
			<td>
				<input type="text" id="beforesalary" name="curStaffchangeinfo.beforesalary" style="width:120" maxlength="8" readonly="true"/>
			</td>
			<td>新基本工资</td>
			<td>
				<input type="text" id="aftersalary" name="curStaffchangeinfo.aftersalary" style="width:140" maxlength="8" onchange="validatePrice(this)"/>
			</td>
		</tr>
		<tr>	
			<td>原业绩方式</td>
			<td>
				<input type="text" id="resulttyeshow" name="resulttyeshow" style="width:120"  readonly="true"/>
			</td>
			<td>新业绩方式</td>
			<td>
				<input type="text" id="newresulttyeshow" name="newresulttyeshow" style="width:120"  readonly="true"/>
			</td>
		</tr>
		<tr>
			<td>原业绩系数</td>
			<td>
				<input type="text" id="beforeyejirate" name="curStaffchangeinfo.beforeyejirate" style="width:120" maxlength="8"  readonly="true"/>
			</td>
			<td>新业绩系数</td>
			<td>
				<input type="text" id="afteryejirate" name="curStaffchangeinfo.afteryejirate" style="width:120" maxlength="8"  onchange="validatePrice(this)" />
			</td>
		</tr>
		<tr>
			<td>原业绩基数</td>
			<td>
				<input type="text" id="beforeyejiamt" name="curStaffchangeinfo.beforeyejiamt" style="width:120" maxlength="8"  readonly="true"/>
			</td>
			<td>新业绩基数</td>
			<td>
				<input type="text" id="afteryejiamt" name="curStaffchangeinfo.afteryejiamt" style="width:120" maxlength="8"  onchange="validatePrice(this)"/>
			</td>
		</tr>
		<tr>
		</tr>
		
		<tr>
			<td>本店调动备注</td>
			<td colspan="3"><input type="text" id="remark" name="curStaffchangeinfo.remark" style="width:320"  maxlength="60"/></td>
		</tr>
	
		<tr>
			<td><font color="red">生效日期</font></td>
			<td><input type="text" id="validatestartdate" name="curStaffchangeinfo.validatestartdate" style="width:120"  maxlength="11" readonly="true"/></td>
		 	<td colspan="2" align="center"><div id="comfirmButton"></div></td>
		</tr>
		</table>
		<input type="hidden" id="aftercompid" name="curStaffchangeinfo.aftercompid"/>
		<input type="hidden" id="beforedepartment" name="curStaffchangeinfo.beforedepartment"/>
		<input type="hidden" id="beforepostation" name="curStaffchangeinfo.beforepostation"/>
		<input type="hidden" id="beforeyejitype" name="curStaffchangeinfo.beforeyejitype"/>
		<input type="hidden" id="afteryejitype" name="curStaffchangeinfo.afteryejitype"/>
	</form>					
   <script type="text/javascript">
   		
   </script>
</body>
</html>
