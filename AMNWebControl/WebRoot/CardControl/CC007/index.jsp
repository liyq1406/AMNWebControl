<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC007/cc007.js"></script>

		<style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
  
		    body,html{height:100%;}
	    body{ padding:0px; margin:0;   overflow:hidden;}  
	    .l-link{ display:block; height:16px; line-height:16px; padding-left:10px; text-decoration:underline; color:#333;}
	    .l-link2{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
	    .l-link3{text-decoration:underline;  margin-left:2px;margin-right:2px;}
	    .l-layout-top{background:#102A49; color:White;}
	    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
		 #dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;}
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="cc007layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
  	 	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
	  	 <div position="center"   id="designPanel"  style="width:100%;" > 
		 	<form name="detailForm" method="post"  id="detailForm">
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
				<tr >
				<td width="205" valign="top" rowspan="3"><div id="commoninfodivmaster" style="margin:0; padding:0"></div></td>
				<td height="30px" colspan="3">	
					<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
					<table  id="mainToolTable" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:30px" >
						<tr>
							<td width="40%"><font color="bule" size="4">销售单号:&nbsp;&nbsp;&nbsp;<label id="lbBillId"></label>		
							<input type="hidden" name="curMsalecardinfo.id.salebillid" id="salebillid" />
							<input type="hidden"  name="curMsalecardinfo.id.salecompid" id="salecompid" />
							</font></td>
							<td width="40%" align="right">
							    <a onclick="freshCurRecord()"><img src="<%=ContextPath%>/common/funtionimage/refresh.png" 	style="width:25;height:25" alt="刷新"></img></a>
								<a onclick="editCurRecord()"><img src="<%=ContextPath%>/common/funtionimage/post.png" 	style="width:25;height:25" alt="保存"></img></a>
								<a onclick="viewTicketReport()"><img src="<%=ContextPath%>/common/funtionimage/print.png" 	style="width:25;height:25" alt="打印"></img></a>
								
								<input type="text" name="strSearchBillId" id="strSearchBillId"  
							onblur="if(this.value==''){this.value='输入销售单号后查询';};" 
							onfocus="if(this.value=='输入销售单号后查询'){this.value='';}" 
							value="输入销售单号后查询"  
							onMouseOver="this.style.border='1px solid #6C0'"   
							onMouseOut="this.style.border='1px solid #ccc' "  style="width:160px;height:30"/>
							 <a  onclick="searchCurRecord()"><img src="<%=ContextPath%>/common/funtionimage/search.png" 	style="width:25;height:25" alt="查询"></img></a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
					</div>
				</td>
				</tr>
				<tr >
				<td valign="top" >	
					<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
						<tr>
							<td width="500" valign="top">
							<div  style="width:100%;height:200  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
							<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
							
							<tr>
							<td><font color="red">会员卡号</font></td>
							<td ><input type="text" name="curMsalecardinfo.salecardno" id="salecardno"  readonly="true" style="width:140;" onchange="validateSalecardno(this)"  validate="{required:true,minlength:1,maxlength:18,notnull:false}"/></td>
							
							<td>卡类型</td>
							<td  > 
												<input type="text"  name="curMsalecardinfo.salecardtypename" id="salecardtypename"  readonly="true" style="width:140;"  />
												<input type="hidden"  name="curMsalecardinfo.salecardtype" id="salecardtype"  />
												<input type="hidden"  name="curMsalecardinfo.sendamtflag" id="sendamtflag"  />
							</td>
							</tr>
							<tr>
							<td><font color="blue">卡来源</font></td>
							<td>
								<select id="billinsertype" name="curMsalecardinfo.billinsertype" style="width:140"  onchange="validateInserType(this)">
									<option value="0">--</option>
									<option value="1">美容</option>
									<option value="2">美发</option>
									<option value="3">合作</option>
								</select>
							</td>
							<td>销售日期</td>
							<td ><input type="text"  name="curMsalecardinfo.saledate" id="saledate"  readonly="true" style="width:80;"  />
								<input type="text"  name="curMsalecardinfo.saletime" id="saletime"  readonly="true" style="width:60;"  /></td>
							</tr>
							<tr>
							<td><font color="blue">刷卡凭证号</font></td>
							<td><input type="text" name="curMsalecardinfo.bankcostno" id="bankcostno"  readonly="true" style="width:140;" maxlength="10" /></td>
							<td><font color="blue">手机号码</font></td>
							<td>
								<input type="text" id="temphone" readonly="true" style="width:140;" maxlength="20" onchange="changephone(this)" validate="{required:true,minlength:1,maxlength:20,notnull:false}" />
								<input type="hidden"  name="curMsalecardinfo.memberphone" id="memberphone"  readonly="true" style="width:140;" maxlength="20" validate="{required:true,minlength:1,maxlength:20,notnull:false}" />
							</td>
							
							</tr>
							
							<tr>
							<td><font color="blue">会员姓名</font></td>
							<td><input type="text" name="curMsalecardinfo.membername" id="membername"  readonly="true" style="width:140;" maxlength="20" validate="{required:true,minlength:1,maxlength:20,notnull:false}" /></td>
							<td><font color="blue">会员生日</font></td>
							<td><input type="text"  name="curMsalecardinfo.memberbirthday" id="memberbirthday"  readonly="true" style="width:140;" maxlength="20" validate="{required:true,minlength:1,maxlength:18,notnull:false}" /></td>
							
							</tr>
							<tr>
							<td><font color="blue">身份证号</font></td>
							<td ><input type="text"  name="curMsalecardinfo.memberpcid" id="memberpcid"  readonly="true" style="width:140;"maxlength="20"  validate="{required:true,minlength:1,maxlength:18,notnull:false}" />
							<td>顾客性别</td>
							<td >
							<input type="radio" id="membersex0" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMsalecardinfo.membersex" value="0"/>女客
							<input type="radio" id="membersex1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMsalecardinfo.membersex" value="1"/>男客
							</td>
							
							</tr>
						
							<tr>
							<td ><font color="blue">卡申请单</font></td>
							<td><input type="text" name="curMsalecardinfo.cardappbillid" id="cardappbillid" maxlength="20" readonly="true" style="width:140;" validate="{required:true,notnull:false}"/></td>
							<td >是否享受虚拟卡金</td>
							<td>
								<select id="isxnj" name="curMsalecardinfo.isxnj" style="width:140" onchange="xnchange(this)" disabled="true">
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</td>
							</tr>
							<tr>
							<td>团购号</td>
							<td><input type="text"  name="curMsalecardinfo.corpscardno" id="corpscardno"  readonly="true" style="width:140;"  onchange="dfgwCardNo(this)"/></td>
							<td>活动券号</td>
							<td><input type="text"  name="curMsalecardinfo.activtycardno" id="activtycardno"  readonly="true" style="width:140;"  onchange="validateActivtycardno(this)"/></td>
							</tr>
							<tr>
							<td>最低额度</td>
							<td><input type="text" name="loeweAmt" id="loeweAmt"  readonly="true" style="width:140;"/></td>
							<td><font color="blue">开卡金额</font></td>
							<td><input type="text"  name="curMsalecardinfo.saletotalamt" id="saletotalamt"  readonly="true" style="width:140;" onchange="validatePrice(this);validateRechargeAmt()" validate="{required:true,notnull:false}"/></td>
							</tr>
							<tr>
							<td>第一销售</td>
							<td>
								<input type="text" name="curMsalecardinfo.firstsalerid" id="firstsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)" />
								<input type="text" name="firstsalername" id="firstsalername"  readonly="true" style="width:80;"  />
							</td>
							<td>
								分享金额</td>
							<td><input type="text"  name="curMsalecardinfo.firstsaleamt" id="firstsaleamt"  readonly="true" style="width:60;" onchange="validatePrice(this)"/>
								&nbsp;业绩<input type="text"  name="curMsalecardinfo.firstsalecashamt" id="firstsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/>
							</td>
							</tr>
							<tr>
							<td>第一烫染</td>
							<td>
								<input type="text" name="curMsalecardinfo.fifthsalerid" id="fifthsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
								<input type="text" name="fifthsalername" id="fifthsalername"  readonly="true" style="width:80;"  />
							</td>
							<td>分享金额</td>
							<td><input type="text"  name="curMsalecardinfo.fifthsaleamt" id="fifthsaleamt"  readonly="true" style="width:60;" onchange="validatePrice(this)"/>
							&nbsp;业绩<input type="text"  name="curMsalecardinfo.fifthsalecashamt" id="fifthsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							<tr>
							<td>第二销售</td>
							<td>
								<input type="text" name="curMsalecardinfo.secondsalerid" id="secondsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
								<input type="text" name="secondsalername" id="secondsalername"  readonly="true" style="width:80;"  />
							</td>
							<td>分享金额</td>
							<td><input type="text"  name="curMsalecardinfo.secondsaleamt" id="secondsaleamt"  readonly="true" style="width:60;" onchange="validatePrice(this)"/>
							&nbsp;业绩<input type="text"  name="curMsalecardinfo.secondsalecashamt" id="secondsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							<tr>
							<td>第二烫染</td>
							<td>
								<input type="text" name="curMsalecardinfo.sixthsalerid" id="sixthsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
								<input type="text" name="sixthsalername" id="sixthsalername"  readonly="true" style="width:80;"  />
							</td>
							<td>分享金额</td>
							<td><input type="text"  name="curMsalecardinfo.sixthsaleamt" id="sixthsaleamt"  readonly="true" style="width:60;" onchange="validatePrice(this)"/>
							&nbsp;业绩<input type="text"  name="curMsalecardinfo.sixthsalecashamt" id="sixthsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/>
							</td>
							</tr>
							<tr>
							<td>第三销售</td>
							<td>
								<input type="text" name="curMsalecardinfo.thirdsalerid" id="thirdsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
								<input type="text" name="thirdsalername" id="thirdsalername"  readonly="true" style="width:80;"  />
							</td>
							<td>分享金额</td>
							<td><input type="text"  name="curMsalecardinfo.thirdsaleamt" id="thirdsaleamt"  readonly="true" style="width:60;" onchange="validatePrice(this)"/>
							&nbsp;业绩<input type="text"  name="curMsalecardinfo.thirdsalecashamt" id="thirdsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							<tr>
							<td>第三烫染</td>
							<td><input type="text" name="curMsalecardinfo.seventhsalerid" id="seventhsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
								<input type="text" name="seventhsalername" id="seventhsalername"  readonly="true" style="width:80;"  />
							</td>
							<td>分享金额</td>
							<td><input type="text"  name="curMsalecardinfo.seventhsaleamt" id="seventhsaleamt"  readonly="true" style="width:60;" onchange="validatePrice(this)"/>
							&nbsp;业绩<input type="text"  name="curMsalecardinfo.seventhsalecashamt" id="seventhsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							
							<tr>
								<!-- <td>经理/顾问</td> -->
								<td><input type="hidden" name="curMsalecardinfo.beautyManager" id="beautyManager"   style="width:60;" onchange="checkStaffValuex(this)"/>
									<input type="hidden" name="beautyManagerName" id="beautyManagerName"  readonly="true" style="width:80;"  />
								</td>
							</tr>
							<tr>
								<!-- <td>经理/顾问</td> -->
								<td><input type="hidden" name="curMsalecardinfo.consultant" id="consultant"   style="width:60;" onchange="checkStaffValuex(this)"/>
									<input type="hidden" name="consultantName" id="consultantName"  readonly="true" style="width:80;"  />
								</td>
							</tr>
							<tr>
								<!-- <td>经理/顾问</td> -->
								<td><input type="hidden" name="curMsalecardinfo.consultant1" id="consultant1"   style="width:60;" onchange="checkStaffValuex(this)"/>
									<input type="hidden" name="consultantName1" id="consultantName1"  readonly="true" style="width:80;"  />
								</td>
							</tr>
							<tr>
							<td>销售备注</td>
							<td colspan="3"><input type="text" name="curMsalecardinfo.saleremark" id="saleremark"  readonly="true" style="width:320;"/></td>
							
							</tr>
							</table>
							</div>
							</td>	
							<td valign="top" ><div id="commoninfodivdetial_pay" style="margin:0; padding:0"></div></td>
							
						</tr>
					</table>
						
				</td>
				</tr>
				<tr >
					<td height="70%"  valign="top">
						<div id="commoninfodivdetial_pro" style="margin:0; padding:0"></div>
					</td>
				</tr>
				
				</table>
			</form>
			<input  type="hidden" name="strShareCondition" id="strShareCondition"/>
			<input  type="hidden" name="cardsendamtflag" id="cardsendamtflag"/>
			<div id="printContent" style="position:absolute;left:10px; top:60px; z-index:-2;display:none">
				<table width="220px" border="0" cellspacing="0" cellpadding="2" id="text">
				
				<tr>
				<td align="left" width="90">日期:</td><td align="left"><label id="date_print"></label></td>
				</tr>
				<tr>
				<td align="left" width="90">卡号:</td><td align="left"><label id="memberCardId_print"></label></td>
				</tr>
				<tr>
				<td align="left" width="90">卡类型:</td><td align="left"><label id="memberCardType_print"></label></td>
				</tr>
				<tr>
				<td align="left" width="90">储值金额:</td><td align="left"><label id="memberCardAmt_print"></label></td>
				</tr>
				<tr>
				<td align="left" width="90">欠款金额:</td><td align="left"><label id="memberCardDef_print"></label></td>
				</tr>
				<tr><td>付款明细</td><td></td></tr>
				 <tbody id = "consumeDetail" name="consumeDetail">
				  </tbody>
				<tr><td colspan="2" ><hr width="100%"></td></tr>
				      <tr>
				      <td align="left" width="90">交易号:&nbsp;</td><td align="left" >&nbsp;<label id="tradebillId_print">&nbsp;</label></td>
				      </tr>
				     <tr>
				       <td align="left" width="90">收银员:&nbsp;</td><td align="left" >&nbsp;<label id="clerkName_print">&nbsp;</label></td>
				     </tr>
				     <tr>
				       <td align="left" width="90">打印时间:&nbsp;</td><td align="left" >&nbsp;<label id="printTime_print">&nbsp;</label></td>
				     </tr> 
				     <tr>
				       <td align="left" width="90">客户签名:&nbsp;</td><td align="left" >&nbsp;</td>
				     </tr>
				      <tr>
				     <td align="left" width="90"></td><td align="left" >&nbsp;</td>
				     </tr>
				      <tr>
				       <td align="left" width="90"></td><td align="left" >&nbsp;</td>
				     </tr>
				     
				</table>
			</div>
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