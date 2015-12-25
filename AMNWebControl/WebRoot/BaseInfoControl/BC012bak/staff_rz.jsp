<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
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
 	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC012/bc012OutPageRZ.js"></script>
</head>
<body style="padding:6px; overflow:hidden;">
	<form name="curRZFrom" method="post"  id="curRZFrom">
		<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:36px;" >
		<tr>
			<td><font color="red">申请门店</font></td>
			<td><input type="text" id="compno" name="curStaffinfo.id.compno" style="width:120" readonly="true" /></td>
			<td><font color="blue">入职工号</font></td>
			<td><input type="text" id="staffno" name="curStaffinfo.id.staffno" style="width:140" maxlength="20"/></td>
		</tr>
		<tr>
			<td><font color="blue">员工姓名</font></td>
			<td><input type="text" id="staffname" name="curStaffinfo.staffname" style="width:120"  maxlength="20"/></td>
			<td>员工性别</td>
			<td>
				<input type="radio" id="staffsex" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curStaffinfo.staffsex" value="0"  />女
				<input type="radio" id="staffsex" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curStaffinfo.staffsex" value="1" checked="true" />男
			</td>
		</tr>
		<tr>
			<td>员工部门</td>
			<td>
				<select id="department" name="curStaffinfo.department" style="width:120" >
				</select>
	
				<input type="hidden" id="departmentshow" name="departmentshow" style="width:120" />
			</td>
			<td>员工职位</td>
			<td>
				<select id="position" name="curStaffinfo.position" style="width:140">
				</select>
				<input type="hidden" id="positionshow" name="positionshow" style="width:140" />
			</td>
		</tr>
		<tr>
			<td>手机号码</td>
			<td><input type="text" id="mobilephone" name="curStaffinfo.mobilephone" style="width:120"  maxlength="11"/></td>
			<td><font color="blue">身份证号</font></td>
			<td><input type="text" id="pccid" name="curStaffinfo.pccid" style="width:140"  maxlength="18"/></td>
		</tr>
	
		
	
		<tr>
			<td><font color="blue">健康证号</font></td>
			<td>
				<input type="text" id="healthno" name="curStaffinfo.healthno" style="width:120" maxlength="20"/>
			</td>
			<td><font color="blue">有效期限</font></td>
			<td>
				<input type="text" id="healthdate" name="curStaffinfo.healthdate" style="width:140" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td><font color="blue">紧急联系人</font></td>
			<td>
				<input type="text" id="reservecontect" name="curStaffinfo.reservecontect" style="width:120" maxlength="20"/>
			</td>
			<td><font color="blue">紧急联系号</font></td>
			<td>
				<input type="text" id="reservephone" name="curStaffinfo.reservephone" style="width:140" maxlength="11"/>
			</td>
		</tr>
		<tr>
			<td>出生日期</td>
			<td><input type="text" id="birthdate" name="curStaffinfo.birthdate" style="width:120"  maxlength="11" readonly="true"/></td>
			<td>QQ号码</td>
			<td><input type="text" id="qqno" name="curStaffinfo.qqno" style="width:140"  maxlength="18"/></td>
		</tr>
		<tr>
			<td>最高学历</td>
			<td>
				<input type="text" id="educational" name="curStaffinfo.educational" style="width:120" maxlength="20" />
			</td>
			<td>介绍人</td>
			<td>
				<input type="text" id="introductioner" name="curStaffinfo.introductioner" style="width:140" maxlength="20" />
			</td>
		</tr>
		<tr>
			
			<td>银行卡类型</td>
			<td>
				<input type="text" id="banktypeshow" name="banktypeshow" style="width:120" />
			</td>
			<td>银行卡号</td>
			<td>
				<input type="text" id="bankno" name="curStaffinfo.bankno" style="width:140" maxlength="20" />
			</td>
		</tr>
		<tr>
			<td>基本工资</td>
			<td>
				<input type="text" id="basesalary" name="curStaffinfo.basesalary" style="width:120" maxlength="8" />
			</td>
			<td>社保金额</td>
			<td>
				<input type="text" id="socialsecurity" name="curStaffinfo.socialsecurity" style="width:120" maxlength="8"/>
			</td>
		</tr>
		<tr>
			<td>业绩属性</td>
			<td>
				<input type="radio" id="businessflag" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curStaffinfo.businessflag" value="0"   checked="true" />非业务
				<input type="radio" id="businessflag" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curStaffinfo.businessflag" value="1"/>业务
			</td>
			<td>业绩方式</td>
			<td>
				<input type="text" id="resulttyeshow" name="resulttyeshow" style="width:120" />
			</td>
		</tr>
		
		<tr>
			<td>业绩系数</td>
			<td>
				<input type="text" id="resultrate" name="curStaffinfo.resultrate" style="width:120" maxlength="8"/>
			</td>
			<td>业绩基数</td>
			<td>
				<input type="text" id="baseresult" name="curStaffinfo.baseresult" style="width:120" maxlength="8"/>
			</td>
		</tr>
		<tr>
			<td><font color="blue">身份证地址</font></td>
			<td colspan="3"><input type="text" id="aaddress" name="curStaffinfo.aaddress" style="width:320"  maxlength="100"/></td>
		</tr>
		<tr>
			<td><font color="blue">紧急联系地址</font></td>
			<td colspan="3"><input type="text" id="reserveaddress" name="curStaffinfo.reserveaddress" style="width:320"  maxlength="100"/></td>
			
		</tr>
		<tr>
			<td>入职备注</td>
			<td colspan="3"><input type="text" id="remark" name="curStaffinfo.remark" style="width:320"  maxlength="60"/></td>
		</tr>
		
		<tr>
			<td colspan="4" align="center">&nbsp;</td>
		</tr>
		<tr>
			<td><font color="red">生效日期</font></td>
			<td><input type="text" id="apparrivaldate" name="curStaffinfo.apparrivaldate" style="width:120"  maxlength="11" readonly="true"/></td>
		 	<td colspan="2" align="center"><div id="comfirmButton"></div></td>
		</tr>
		</table>
	
		<input type="hidden" id="resulttye" name="curStaffinfo.resulttye"/>
		<input type="hidden" id="banktype" name="curStaffinfo.banktype"/>
	</form>					
  
</body>
</html>
