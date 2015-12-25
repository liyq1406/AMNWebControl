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
 	 <script src="<%=ContextPath%>/common/ligerui/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=ContextPath%>/common/ligerui/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script> 
	<script type="text/javascript" src="<%=ContextPath %>/PersonnelControl/PC014/pc014.js"></script>
</head>
<body style="padding:6px; overflow:hidden;">
 <div id="pc014layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
<div id="PC014Tab" style="width:100%; height:98%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
	<div title="员工培训录入" style=" ;font-size:12px;" >
	<form name="intentionform" method="post"  id="intentionform">
		<table border="0" style="font-size:12px;width:100%">
			<tr>
				<td valign="top">
					<table border="0" style="font-size:12px;border:solid 1px #aaa;height:100%;width:500px">
						<tr><td colspan="4"><div id="intentions"></div></td></tr>
						<tr>
							<td height="28px" width="80px">门店编号</td><td><input type="text" id="intcomplynos"/></td>
							<td height="28px" width="80px">员工工号</td><td><input type="text" id="instaffnos"/></td>
						</tr>
						<tr>
							<td height="28px" >省份证号</td><td><input type="text" id="incardnos"/></td>
							<td height="28px">手册号码</td><td><input type="text" id="intstunos"/></td>
						</tr>
					</table>
					<div id="intention" style=" margin-top: 1px"></div>
				</td>
				<td>&nbsp;</td>
				<td valign="top">
						<table border="0" style="font-size:12px;border:solid 1px #aaa;line-height:25px;width:860px">
							<tr>
								<td valign="top" colspan="6"  ><div id="intentionss"></div></td>
							</tr>
						  
							<tr>
								<td width="80px"  >单据编号</td>
								<td width="200px"><input type="text" id="intbillid" readonly="readonly" style="height:21px;width: 150px"/>
								<input type="hidden" name="intcomplyno" id="intcomplyno"/></td>
								<td width="80px"  height="28px">登记日期</td>
								<td><input type="text" id="intdata" readonly="readonly" style="height:21px;width: 150px"/></td>
							</tr>
							<tr>
								<td height="30px">岗位课程</td><td><select id="intdproject" style="height:21px;width: 150px">
								
								</select></td>
								<td height="30px">阶段</td><td><select id="intdstage" style="height:21px;width: 150px">
								<option value="0">无</option>
								<option value="1">第一阶段</option>
								<option value="2">第二阶段</option>
								<option value="3">第三阶段</option>
								<option value="4">第四阶段</option>
								<option value="5">其他</option>
								</select></td>
							</tr>
							<tr>
								<td height="30px">培训起始时间</td>
									<td><input type="text" id="intdstarttime" /></td>
									<td colspan="4"><input type="text" id="intdendtime"  /></td>
							</tr>
							 </table>
							 <table border="0" style="font-size:12px;border:solid 1px #aaa;line-height:25px;width:860px">
								<tr>
									<td valign="top" colspan="8"  ><div id="intentionsss"></div></td>
								</tr>
								<tr>
									<td width="90px">培训门店</td>
									<td colspan="5"  ><div ><%@include file="/common/search.frag"%></div></td>
								</tr>
							
							<tr>
								<td width="90px">员工编号</td><td  ><input type="text" id="instaffno" style="height:21px;width: 120px" onchange="validateInserper(this)"/></td>
							    <td  width="90px"> 员工姓名</td><td><input type="text" id="instaffname" readonly="readonly" style="height:21px;width: 120px"/></td>
								<td width="110px">身份证号码</td><td  ><input type="text" id="incardno" readonly="readonly"  style="height:21px;width: 120px"/></td>
								<td height="30px" >职位</td><td><input type="text" id="intposition" readonly="readonly" style="height:21px;width: 120px"/></td>
							</tr>
							 <tr>
							 	<td height="30px" width="9%">手册号码</td><td ><input type="text" id="intstuno" style="height:21px;width: 120px"/></td>
								<td height="30px">选修课名称</td><td><input type="text" id="intdproname" style="height:21px;width: 120px"/></td>
								<td height="30px">建议岗位</td><td><select id="intpositions" style="height:21px;width: 120px">
								<option value="0">初级技师</option>
								<option value="1">高级技师</option>
								<option value="2">预备发型师</option>
								<option value="3">发型师</option>
								<option value="4">首席</option>
								<option value="5">总监</option>
								<option value="6">美发经理</option>
								</select></td>
								<td height="30px">成绩</td><td><select id="intdscore" style="height:21px;width: 120px">
								<option value="0">不合格</option>
								<option value="1">合格</option>
								</select></td>
							</tr>
							<tr>
								<td height="30px" > 出生日期</td><td><input type="text" id="intbirthday"  /></td>
								<td height="30px">奖罚情况</td><td><input type="text" id="intdpunish" style="height:21px;width: 120px"/></td>
							</tr>
							<tr>
								<td height="30px">备注</td><td  colspan="4"><input type="text"  style="height:21px;width: 350px" id="intdremark"/></td>
								<td colspan="3"><div id="editaddDetailInfo"></div></td>
							</tr>
						</table>
						<div id="intetiondetails" style=" margin-top: 1px"></div>
				</td>
			</tr>
		</table>
	  </form>
	  </div>
	  <div title="员工培训统计" style=" ;font-size:12px;" >
	 				<table id="confirmTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
						<tr>
							 <td width="540"><div ><%@include file="/common/search1.frag"%></div></td>	
							 <td width="80">岗位课程：</td>
							 <td width="120">
									<select id="iSearchIintdproject" style="height:21px;width: 150px">
											<option value="99"></option>
											
									</select>
							 </td>
							 <td width="80">员工工号：</td>
							 <td width="120">
									<input id="strSearchStaffno" type="text" size="10" />
							 </td>
							 <td ><div id="searchTJButton"></div></td>
						</tr>
						<tr><td style="border-bottom:1px #000000 dashed" colspan="7"></td></tr>
					</table>	
					<div id="commoninfodivdetialtj" style="margin:0; padding:0"></div>
	  </div>
	   </div>
	   </div>
	</body>
</html>

	<script language="JavaScript">
	  	 var contextURL="<%=request.getContextPath()%>";
	</script>