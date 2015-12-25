<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC012/bc012.js"></script>
	 <script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
		<style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
        .scr_con {position:relative;width:298px; height:98%;border:solid 1px #ddd;margin:0px auto;font-size:12px;}
        #dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;}
		#dv_scroll .Scroller-Container{width:100%;}
		#dv_scroll_bar {position:absolute;right:0;bottom:30px;width:14px;height:150px;border-left:1px solid #B5B5B5;}
		#dv_scroll_bar .Scrollbar-Track{position:absolute;left:0;top:5px;width:14px;height:150px;}
		#dv_scroll_bar .Scrollbar-Handle{position:absolute;left:-7px;bottom:10;width:13px;height:29px;overflow:hidden;background:url('<%=ContextPath%>/common/ligerui/images/srcoll.gif') no-repeat;cursor:pointer;}
		#dv_scroll_text {position:absolute;}
    </style>
</head>
<body style="padding:6px; overflow:hidden;">
<div class="l-loading" style="display:block" id="pageloading"></div> 

 <div id="bc012layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
  	 	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
	  	<div position="center"   id="designPanel"  style="width:100%;" > 
	  		<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:36px;" >
	  		<tr>
	  			<td>关键字：</td>
	  			<td><input id="txtSearchKey" type="text"  style="width:120"/></td>
	  			<td><div id="dingweiButton"></div></td>
	  			<td>工号</td>
	  			<td><input id="strStaffNo" type="text" style="width:60"/></td>
	  			<td>姓名</td>
	  			<td><input id="strStaffName" type="text" style="width:80"/></td>
	  			<td>身份证</td>
	  			<td><input id="strPCID" type="text" style="width:140"/></td>
	  			<td>内部工号</td>
	  			<td><input id="strStaffInNo" type="text" style="width:120"/></td>
	  			<td><div id="searchButton"></div></td>
	  		</tr>
	  		</table>
	  		
  			 <div id="commoninfodivsecond" style="margin:0; padding:0"></div>
	  	</div>
	  	<div position="right"   id="lsPanel" title="基础信息"> 
	  		 <div  id="projectInfoDiv" style="width:100%; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
						   <form name="staffinfoForm" method="post"  id="staffinfoForm">
						   	<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px;" >
							   <tr class="tr">
							    	<td>归属门店</td>
							    	<td><input type="text" name="curStaffinfo.id.compno" id="compno"  readonly="true" style="width:140;background:#EDF1F8;" /></td>
							      </tr>
							    <tr class="tr">
							    	<td>员工编号</td>
							    	<td colspan="3">
							    	<input type="text" name="curStaffinfo.id.staffno" id="staffno"  readonly="true" style="width:140;background:#EDF1F8;"  />
							    	<input type="hidden" name="curStaffinfo.manageno" id="manageno"   />
							    	<input type="hidden" name="curStaffinfo.department" id="department"   />
							    	<input type="hidden" name="curStaffinfo.position" id="position"   />
							    	<input type="hidden" name="curStaffinfo.resulttye" id="resulttye"   />
							    	<input type="hidden" name="curStaffinfo.resultrate" id="resultrate"   />
							    	<input type="hidden" name="curStaffinfo.baseresult" id="baseresult"   />
							    	<input type="hidden" name="curStaffinfo.basesalary" id="basesalary"   />
							    	</td>
							    </tr>
							     <tr class="tr">
							    	<td>员工名称</td>
							    	<td colspan="3"><input type="text" name="curStaffinfo.staffname" id="staffname"  readonly="true" style="width:140;background:#EDF1F8;" /></td>
							    </tr>	
							      <tr class="tr">
							    	<td>员工职称</td>
							    	<td colspan="3"><select name="curStaffinfo.positiontitle" id="positiontitle" style="width:140;"></select></td>
							    </tr>		
							    <tr class="tr">
							   		<td>员工性别</td>
							    	<td>
							    		<select name="curStaffinfo.staffsex" id="staffsex" style="width:100;" >
							    		<option value="0">女</option>
							    		<option value="1">男</option>
							    		</select>
							    	</td>
							    </tr>
							    <tr class="tr">
							   		<td>联络地址</td>
							    	<td><input type="text" name="curStaffinfo.aaddress" id="aaddress" style="width:140;" maxlength="40"   /></td>
							    </tr>
							    <tr class="tr">
							   		<td>身份证号</td>
							    	<td><input type="text" name="curStaffinfo.pccid" id="pccid"  style="width:140;"  maxlength="20" /></td>
							    </tr>
							     <tr class="tr">
							   		<td>手机号码</td>
							    	<td><input type="text" name="curStaffinfo.mobilephone" id="mobilephone"   style="width:140;" maxlength="12"  /></td>
							     </tr>
							     <tr class="tr">
							   		<td>紧急联系人</td>
							    	<td><input type="text" name="curStaffinfo.reservecontect" id="reservecontect"  style="width:140;"  maxlength="20" /></td>
							      </tr>	
							     <tr class="tr">
							   		<td>紧急联系人电话</td>
							   		<td><input type="text" name="curStaffinfo.reservephone" id="reservephone"   style="width:140;" maxlength="12" /></td>
							    </tr>	
							     <tr class="tr">
							   		<td>介绍人</td>
							    	<td><input type="text" name="curStaffinfo.introductioner" id="introductioner"   style="width:140;" maxlength="20" /></td>
							    </tr>
							     <tr class="tr">
							   		<td>合约到期日</td>
							    	<td><input type="text" name="curStaffinfo.contractdate" id="contractdate"   style="width:140;" maxlength="20" /></td>
							    </tr>
							      <tr class="tr">
							    	<td>缺勤底薪</td>
							    	<td colspan="3"><select name="curStaffinfo.absencesalary" id="absencesalary" style="width:140;"></select></td>
							    </tr>
							      <tr class="tr">
							   		<td>社保</td>
							    	<td><input type="text" name="curStaffinfo.socialsecurity" id="socialsecurity"   style="width:140;" maxlength="20" onchange="validatePrice(this)" /></td>
							    </tr>
							      <tr class="tr">
							    	<td>社保归属</td>
							    	<td colspan="3"><select name="curStaffinfo.socialsource" id="socialsource" style="width:100;"></select></td>
							    </tr>	
							    
							    <tr class="tr">
							   		<td>银行卡类型</td>
							    	<td>
							    		<select name="curStaffinfo.banktype" id="banktype"  style="width:100;">
							    		<option value="0">请选择</option>
							    		<option value="1">中国银行</option>
							    		<option value="2">中国建设银行</option>
							    		<option value="3">中国工商银行</option>
							    		<option value="4">中国农业银行</option>
							    		<option value="5">招商银行</option>
							    		<option value="6">交通银行</option>
							    		<option value="7">浦发银行</option>
							    		</select>
							    	</td>
							    </tr>
							    <tr class="tr">
							   		<td>银行卡号</td>
							    	<td><input type="text" name="curStaffinfo.bankno" id="bankno"  style="width:140;"  maxlength="20"  onchange="validateStaffBank(this)"/></td>
							    </tr>
							     <tr class="tr">
							   		<td>是否为业务</td>
							    	<td>
							    		<select name="curStaffinfo.businessflag" id="businessflag" style="width:100;" >
							    		<option value="0">非业务人员</option>
							    		<option value="1">业务人员</option>
							    		</select>
							    	</td>
							    </tr>
							     <tr class="tr">
							    	<td>薪资模式</td>
							    	<td colspan="3"><select name="curStaffinfo.tichengmode" id="tichengmode" style="width:140;"></select></td>
							    </tr>
							    <tr class="tr">
							   		<td>员工备注</td>
							    	<td><textarea  rows="5"  cols="17" name="curStaffinfo.staffmark" id="staffmark"></textarea></td>
							    </tr>
							     <tr class="tr">
							   		<td>工资批注</td>
							    	<td><textarea  rows="5"  cols="17" name="curStaffinfo.remark" id="remark"></textarea></td>
							    </tr>
							    <tr class="tr">
							   		<td colspan="2" align="center" ><div id="editCurRecord"></div></td>
							    </tr>
						   	</table>
						   	</form>
	  	</div>
  </div>	

	
  <div style="display:none;">
  <!-- g data total ttt -->
</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
	</script>