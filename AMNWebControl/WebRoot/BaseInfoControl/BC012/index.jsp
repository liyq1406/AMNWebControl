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
	  
	  	<table  border="0" cellspacing="0" cellpadding="0"  style="font-size:12px;line-height:20px;margin-left:20px;margin-top:3px;">
	  		<tr>
	  			<td valign="top" ><div id="commoninfodivsecond" style="margin:0; padding:0"></div> </td>
	  			<td valign="top">
	  					<div id="toptoolbardetial" style="margin:0; padding:0;font-size:12px;"></div>
						<form name="staffinfoForm" method="post"  id="staffinfoForm">
						   	<table width="700" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px;margin-left:20px;margin-top:3px;" >
							   <tr class="tr">
							    	<td>门店</td>
							    	<td>
							    	<input type="text" name="curStaffinfo.id.compno" id="compno"  readonly="true" style="width:40;background:#EDF1F8;" />
							    	<input type="text" name="curStaffinfo.bcompname" id="bcompname"  readonly="true" style="width:95;background:#EDF1F8;" />
							    	</td>
							    	<td>部门</td>
							    	<td ><select name="curStaffinfo.department" id="department" style="width:140;" disabled="true"></select></td>
							    	<td>考勤权限</td>
							    	<td>
							    		<select name="curStaffinfo.mangerflag" id="mangerflag" style="width:140;" >
							    		<option value="0">否</option>
							    		<option value="1">是</option>
							    		</select>
							    	</td>
							    	
							    	
							    </tr>
							     <tr class="tr">
							    	<td><font color="red">工号 </font></td>
							    	<td>
							    	<input type="text" name="curStaffinfo.id.staffno" id="staffno"  readonly="true" style="width:70;background:#EDF1F8;"  />
							    	<select name="curStaffinfo.staffsex" id="staffsex" style="width:65;" >
							    		<option value="0">女</option>
							    		<option value="1">男</option>
							    		</select>
							  		</td>
							  		<td><font color="red">内部工号 </font></td>
							    	<td><input type="text" name="curStaffinfo.manageno" id="manageno"  readonly="true" style="width:140;background:#EDF1F8;"   /></td>
							   
							  		<td>职位</td>
							    	<td ><select name="curStaffinfo.position" id="position" style="width:140;" disabled="true"></select></td>
							   		
							    	</tr>	
							       <tr class="tr">
							    	<td>姓名</td>
							    	<td ><input type="text" name="curStaffinfo.staffname" id="staffname"  readonly="true" style="width:140;background:#EDF1F8;" /></td>
							   	 	<td>基本工资</td>
							    	<td ><span id="boxA"><input type="text" name="curStaffinfo.basesalary" id="basesalary"  readonly="true" style="width:140;background:#EDF1F8;" /></span></td>
							  
							    	<td>职称</td>
							    	<td ><select name="curStaffinfo.positiontitle" id="positiontitle" style="width:140;"></select></td>
							     	
							     </tr>
							    <tr class="tr">
							    	
							    	<td>业绩方式</td>
							    	<td ><select name="curStaffinfo.resulttye" id="resulttye" style="width:140;" disabled="true"></select></td>
							    	
							    	
							    </tr>	
							    
							      <tr class="tr">
							      	<td>业绩比率</td>
							    	<td ><span id="boxB"><input type="text" name="curStaffinfo.resultrate" id="resultrate"  readonly="true" style="width:140;background:#EDF1F8;" /></span></td>
							   	 	<td>业绩基数</td>
							    	<td ><span id="boxC"><input type="text" name="curStaffinfo.baseresult" id="baseresult"  readonly="true" style="width:140;background:#EDF1F8;" /></span></td>
							   		 <td><font color="red">业务类型</font></td>
							    	<td>
							    		<select name="curStaffinfo.businessflag" id="businessflag" style="width:140;" >
							    		<option value="0">非业务人员</option>
							    		<option value="1">业务人员</option>
							    		</select>
							    	</td>
							    	
							    	
							   	 </tr>
							     <tr class="tr">
							    	<td colspan="6">&nbsp;</td>
							    	
							   	 </tr>
							   	 
							   	 <tr class="tr">
							    	
							    	<td>到职日期</td>
							    	<td><input type="text" name="curStaffinfo.arrivaldate" id="arrivaldate"  readonly="true" style="width:140;background:#EDF1F8;"  maxlength="20" /></td>
							     	
							   	 	<td><font color="red">合约到期日</font></td>
							    	<td><input type="text" name="curStaffinfo.contractdate" id="contractdate"   style="width:140;" maxlength="20" /></td>
							     	<td>档案号</td>
							     	<td><input type="text" name="curStaffinfo.fillno" id="fillno"  style="width:140;"  maxlength="20" /></td>
							   	 </tr>
							 
							   	  <tr class="tr">
							    	<td>社保归属</td>
							    	<td ><select name="curStaffinfo.socialsource" id="socialsource" style="width:140;"></select></td>
							    	<td>银行卡类型</td>
							    	<td>
							    		<select name="curStaffinfo.banktype" id="banktype"  style="width:140;">
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
							    	<td>薪资模式</td>
							    	<td ><select name="curStaffinfo.tichengmode" id="tichengmode" style="width:140;"></select></td>
							   
							    </tr>	
							    
							    <tr class="tr">
							   		
							    <td>社保金额</td>
							    	<td><input type="text" name="curStaffinfo.socialsecurity" id="socialsecurity"   style="width:140;" maxlength="20" onchange="validatePrice(this)" /></td>
							   		
							   		<td>银行卡卡号</td>
							    	<td><span id="boxG"><input type="text" name="curStaffinfo.bankno" id="bankno"  style="width:140;"  maxlength="20"  onchange="validateStaffBank(this)"/></span></td>
							    	<td>缺勤底薪</td>
							    	<td ><select name="curStaffinfo.absencesalary" id="absencesalary" style="width:140;"></select></td>
							    	
							    </tr>
							    
							    <tr class="tr">
							    	<td colspan="6">&nbsp;</td>
							    	
							   	 </tr>
							   	 
							    <tr class="tr">
							   		
							   		<td><font color="blue">身份证号</font></td>
							    	<td><span id="boxE"><input type="text" name="curStaffinfo.pccid" id="pccid"  style="width:140;"  maxlength="20" /></td>
							    	
							   		<td>手机号码</td>
							    	<td><span id="boxF"><input type="text" name="curStaffinfo.mobilephone" id="mobilephone"   style="width:140;" maxlength="12"  /></span></td>
							    	 <td><font color="green">介绍人</font></td>
							    	<td><input type="text" name="curStaffinfo.introductioner" id="introductioner"   style="width:140;" maxlength="20" /></td>
							   		
							    </tr>
							    
							   
							    
							     
							    
							     <tr class="tr">
							    	 
							    	<td>联络地址</td>
							    	<td colspan="5"><span id="boxH"><input type="text" name="curStaffinfo.aaddress" id="aaddress" style="width:550;" maxlength="40"   /></span></td>
							     </tr>
							      
							     <tr class="tr">
							   		<td><font color="blue">紧急联系人 </font></td>
							    	<td><input type="text" name="curStaffinfo.reservecontect" id="reservecontect"  style="width:140;"  maxlength="20" /></td>
							     
							   		<td><font color="blue">联系人电话</font></td>
							   		<td><span id="boxD"><input type="text" name="curStaffinfo.reservephone" id="reservephone"   style="width:140;" maxlength="12" /></span></td>
									<td><font color="red">洗头成绩</font></td>
							    	<td>
							    		<select name="curStaffinfo.hairqualified" id="hairqualified" style="width:80;" disabled="true" >
							    		<option value="0">合格</option>
							    		<option value="1">不合格</option>
							    		</select>
							    		<img src="<%=ContextPath%>/common/ligerui/ligerUI/skins/icons/true.gif" alt="" onclick="setStaffHairState(0)" />
							    		&nbsp;&nbsp;
							    		<img src="<%=ContextPath%>/common/ligerui/ligerUI/skins/icons/candle.gif"  alt=""  onclick="setStaffHairState(1)" />
							    	</td>
							   	   
							   	 </tr>
							   	  <tr class="tr">
							   		<td>健康证号 </td>
							    	<td><input type="text" name="curStaffinfo.healthno" id="healthno"  style="width:140;"  maxlength="20" /></td>
							     
							   		<td>健康证有效期</td>
							   		<td><span id="boxD"><input type="text" name="curStaffinfo.healthdate" id="healthdate"   style="width:140;" maxlength="12" /></span></td>
									 <td><font color="red">烫染课程</font></td>
							    	<td>
							    		<select name="curStaffinfo.trkcqualified" id="trkcqualified" style="width:80;" disabled="true" >
							    		<option value="0">合格</option>
							    		<option value="1">不合格</option>
							    		</select>
							    		<img src="<%=ContextPath%>/common/ligerui/ligerUI/skins/icons/true.gif" alt="" onclick="setStaffTrkcState(0)" />
							    		&nbsp;&nbsp;
							    		<img src="<%=ContextPath%>/common/ligerui/ligerUI/skins/icons/candle.gif"  alt=""  onclick="setStaffTrkcState(1)" />
							    	</td>
							   	   
							   	 </tr>
							   	 <tr>
							   	 	<td>是否学习课程</td>
							   	 	<td><select name="curStaffinfo.iscurr" id="iscurr" style="width:100px">
							   	 			<option value=""></option>
							   	 			<option value="0">无</option>
							   	 			<option value="1">有</option>
							   	 		</select>
							   	 	</td>
							   	 	<td>
							   	 		扣款金额
							   	 	</td>
							   	 	<td>
							   	 		<select name="curStaffinfo.ismoney" id="ismoney" style="width:100px">
							   	 			<option value=""></option>
							   	 			<option value="0">0</option>
							   	 			<option value="400">400</option>
							   	 			<option value="700">700</option>
							   	 		</select>
							   	 	</td>
							   	 	<td></td>
							   	 	<td></td>
							   	 </tr>
							   	 
							   	 <tr class="tr">
							    	<td colspan="6">&nbsp;</td>
							   	 </tr>
							     <tr class="tr">
							   		<td  colspan="3">员工备注:</td>
							   		<td  colspan="3">工资批注:</td>
							   	 </tr>
							     <tr class="tr">
							    	<td  colspan="3"><textarea  rows="3"  cols="30" name="curStaffinfo.staffmark" id="staffmark"></textarea>
							    	<td  colspan="3"><textarea  rows="3"  cols="30" name="curStaffinfo.remark" id="remark"></textarea>
							    </tr>
							    
		
							     <tr class="tr">
							   		<td  colspan="3">展示名称:</td>
							   		<td  colspan="3">员工介绍:</td>
							   	 </tr>
							     <tr class="tr">
							    	<td  colspan="3"><textarea  rows="3"  cols="30" name="curStaffinfo.displayname" id="displayname"></textarea>
							    	<td  colspan="3"><textarea  rows="3"  cols="30" name="curStaffinfo.staffintroduction" id="staffintroduction"></textarea>
							    </tr>
							     
							  
						   	</table>
						   	</form>
						   	<input type="hidden"   name="sysParamSp103" id="sysParamSp103"/>
	  			</td>
	  			<td valign="top" >
	  				<div id="onebartoolbar"></div>
	  				<div id="commoninfodivchilden" style="margin:0; padding:0"></div>
	  			</td>
	  		</tr>
	  		</table>
	  	</div>
	  	<div position="right"   id="lsPanel" title="功能区">
	  		<div id="optionBut2"></div>
	  		<div id="optionBut8"></div>&nbsp;
	  		
	  	    <div id="optionBut5"></div>&nbsp;
	  		
	  	
	  		<div id="optionBut4"></div>
	  		<div id="optionBut6"></div>
	  		<div id="optionBut7"></div>	&nbsp;
	  		<div id="optionBut1" style="width:100%;"></div>	&nbsp;
	  	
	  		
	  		<div id="optionBut3"></div>&nbsp;
	  		
	  		<div id="optionBut9"></div>
	  		<div id="optionBut10"></div>
	  		<div id="optionBut11"></div>&nbsp;
	  		<div id="optionBut12"></div>&nbsp;
	  		<div id="optionBut13"></div>&nbsp;
	  		

  		</div>	
	  	 <div position="bottom">
	  	 	<div id="commoninfodivthirth" style="margin:0; padding:0"></div>
	  	 </div>
	
  <div style="display:none;">
  <!-- g data total ttt -->
</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
	</script>