<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*,com.amani.tools.CommonTool"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>

	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC014/cc014.js"></script>

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

  	 <div id="cc014layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
		<div position="center"   id="designPanel"  style="width:100%;" title=""> 
			<table  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
				<tr>
					<td valign="top" >
						<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
					</td>
					<td valign="top">	
					<table  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:30px" >
					<tr>
						<td  valign="top">
							<div  style="width:1000;float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<table  id="mainToolTable" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:30px" >
									<tr>
										<td width="700"><font color="bule" size="4">退卡单号:&nbsp;&nbsp;&nbsp;<label id="lbBillId"></label>		
										</font></td>
										<td  align="right">
										    <a onclick="editCurRecord()"><img src="<%=ContextPath%>/common/funtionimage/post.png" 	style="width:25;height:25" alt="保存"></img></a>
											<a><img src="<%=ContextPath%>/common/funtionimage/print.png" 	style="width:25;height:25" alt="打印"></img></a>
											
											<input type="text" name="strSearchContent" id="strSearchContent"  
										onblur="if(this.value==''){this.value='输入单号或卡号后查询';};" 
										onfocus="if(this.value=='输入单号或卡号后查询'){this.value='';}" 
										value="输入单号或卡号后查询"  
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
					<tr>
						<td  valign="top">
								<div  style=" height:100%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<form name="detailForm" method="post"  id="detailForm">
								
								<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:13px;line-height:20px;" >
							
								<tr>
									<td >
										<div id="readCurCardInfo"></div>
									</td>
									<td>
										<input type="text" name="curMcardchangeinfo.changebeforcardno" id="changebeforcardno"  readonly="true" style="width:100;background:#EDF1F8;"  onchange="validateChangebeforcardno(this)"/>
									</td>
									<td>
										归属门店
									</td>
									<td>
										<input type="text" name="curMcardchangeinfo.changecardfrom" id="changecardfrom"  readonly="true" style="width:100;background:#EDF1F8;"  onchange="validateChangebeforcardno(this)"/>
									</td>
									<td>
										<label id="lboldcardtype">卡类型</label>
									</td>
									<td >
										
										<input type="text" name="curMcardchangeinfo.changebeforcardtypename" id="changebeforcardtypename"  readonly="true" style="width:160;background:#EDF1F8;" />
										<input type="hidden" name="curMcardchangeinfo.changebeforcardtype" id="changebeforcardtype"   />
									</td>
									<td rowspan="11" width="350" valign="top">
										<div  style="width:100%;height:500;float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
											<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:13px;line-height:30px;" >
											<tr>
												<td>工号</td>
												<td><input type="text" name="firstsalerid" id="firstsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
												<td>金额</td>
												<td><input type="text"  name="curMcardchangeinfo.firstsaleamt" id="firstsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
												<td>业绩</td>
												<td><input type="text"  name="curMcardchangeinfo.firstsalecashamt" id="firstsalecashamt"  readonly="true" style="width:50;background:#EDF1F8;" onchange="validatePrice(this)"/></td>
											</tr>
											<tr>
												<td>工号</td>
												<td><input type="text" name="secondsalerid" id="secondsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
												<td>金额</td>
												<td><input type="text"  name="curMcardchangeinfo.secondsaleamt" id="secondsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
												<td>业绩</td>
												<td><input type="text"  name="curMcardchangeinfo.secondsalecashamt" id="secondsalecashamt"  readonly="true" style="width:50;background:#EDF1F8;" onchange="validatePrice(this)"/></td>
											</tr>
											<tr>
												<td>工号</td>
												<td><input type="text" name="thirdsalerid" id="thirdsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
												<td>金额</td>
												<td><input type="text"  name="curMcardchangeinfo.thirdsaleamt" id="thirdsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
												<td>业绩</td>
												<td><input type="text"  name="curMcardchangeinfo.thirdsalecashamt" id="thirdsalecashamt"  readonly="true" style="width:50;background:#EDF1F8;" onchange="validatePrice(this)"/></td>
												
											</tr>
											<tr>
												<td>工号</td>
												<td><input type="text" name="fourthsalerid" id="fourthsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
												<td>金额</td>
												<td><input type="text"  name="curMcardchangeinfo.fourthsaleamt" id="fourthsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
												<td>业绩</td>
												<td><input type="text"  name="curMcardchangeinfo.fourthsalecashamt" id="fourthsalecashamt"  readonly="true" style="width:50;background:#EDF1F8;" onchange="validatePrice(this)"/></td>
												
											</tr>
											<tr>
												<td>工号</td>
												<td><input type="text" name="fifthsalerid" id="fifthsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
												<td>金额</td>
												<td><input type="text"  name="curMcardchangeinfo.fifthsaleamt" id="fifthsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
												<td>业绩</td>
												<td><input type="text"  name="curMcardchangeinfo.fifthsalecashamt" id="fifthsalecashamt"  readonly="true" style="width:50;background:#EDF1F8;" onchange="validatePrice(this)"/></td>
											</tr>
											<tr>
												<td>工号</td>
												<td><input type="text" name="sixthsalerid" id="sixthsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
												<td>金额</td>
												<td><input type="text"  name="curMcardchangeinfo.sixthsaleamt" id="sixthsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
												<td>业绩</td>
												<td><input type="text"  name="curMcardchangeinfo.sixthsalecashamt" id="sixthsalecashamt"  readonly="true" style="width:50;background:#EDF1F8;" onchange="validatePrice(this)"/></td>
											</tr>
												<tr>
												<td>工号</td>
												<td><input type="text" name="seventhsalerid" id="seventhsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
												<td>金额</td>
												<td><input type="text"  name="curMcardchangeinfo.seventhsaleamt" id="seventhsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
												<td>业绩</td>
												<td><input type="text"  name="curMcardchangeinfo.seventhsalecashamt" id="seventhsalecashamt"  readonly="true" style="width:50;background:#EDF1F8;" onchange="validatePrice(this)"/></td>
											</tr>
											<tr>
												<td>工号</td>
												<td><input type="text" name="eighthsalerid" id="eighthsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
												<td>金额</td>
												<td><input type="text"  name="curMcardchangeinfo.eighthsaleamt" id="eighthsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
												<td>业绩</td>
												<td><input type="text"  name="curMcardchangeinfo.eighthsalecashamt" id="eighthsalecashamt"  readonly="true" style="width:50;background:#EDF1F8;" onchange="validatePrice(this)"/></td>
											</tr>
										</table>
										</div>
									</td>
								</tr>
								
								<tr>
									<td>
										<font color="blue">会员姓名</font>
									</td>
									<td>
										<input type="text" name="curMcardchangeinfo.membername" id="membername"  readonly="true" style="width:100;background:#EDF1F8;"  />
									</td>
							
									<td>
										手机号码
									</td>
									<td >
										<input type="text" name="curMcardchangeinfo.memberphone" id="memberphone"  readonly="true" style="width:100;background:#EDF1F8;" />
									</td>
										<td>
										<font color="blue">退卡日期</font>
									</td>
									<td>
										<input type="text" name="curMcardchangeinfo.changedate" id="changedate"  readonly="true" style="width:80;background:#EDF1F8;" />
										<input type="text" name="curMcardchangeinfo.changetime" id="changetime"  readonly="true" style="width:80;background:#EDF1F8;" />
									</td>
								</tr>
								<tr>
									<td>
										<font color="blue">工资模式</font>
									</td>
									<td>
										<select id="salarytype" name="curMcardchangeinfo.salarytype" style="width:100" onchange="validateSalarytype(this)" >
											<option value="0">原退卡模式</option>
											<option value="1">新退卡模式</option>
											
										</select>
									</td>
									<td>
										<font color="blue">卡来源</font>
									</td>
									<td>
										<select id="billinsertype" name="curMcardchangeinfo.billinsertype" style="width:100"  disabled="true" >
											<option value="0">--</option>
											<option value="1">美容</option>
											<option value="2">美发</option>
											<option value="3">合作</option>
										</select>
									</td>
									
								</tr>
								<tr>
									<td>
										<font color="red">储值余额</font>
									</td>
									<td>
										<input type="text" name="curMcardchangeinfo.curaccountkeepamt" id="curaccountkeepamt"  readonly="true" style="width:100;background:#EDF1F8;"  />
									</td>
							
									<td>
										赠送余额
									</td>
									<td >
										<input type="text" name="curMcardchangeinfo.curpointaccountkeepamt" id="curpointaccountkeepamt"  readonly="true" style="width:100;background:#EDF1F8;" />
										
										<input type="hidden" name="curMcardchangeinfo.curaccountdebtamt" id="curaccountdebtamt"  />
									</td>
								</tr>
								<tr>
									<td>
										<font color="red">疗程余额</font>
									</td>
									<td>
										<input type="text" name="curMcardchangeinfo.curproaccountkeepamt" id="curproaccountkeepamt" readonly="true" style="width:100;background:#EDF1F8;"/>
									</td>
							
									<td>
										积分余额
									</td>
									<td >
										<input type="text" name="curMcardchangeinfo.cursendaccountkeepamt" id="cursendaccountkeepamt"  readonly="true" style="width:100;background:#EDF1F8;" />
										<input type="hidden" name="curMcardchangeinfo.curproaccountdebtamt" id="curproaccountdebtamt" />
									</td>
								</tr>
								
								
							  	<tr >
											<td colspan="6" color="#0000cc">
												<hr width="100%" />
											</td>
								</tr>
								<tr>
									<td colspan="6" valign="top">
										<div  style="width:100%;float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
											<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:13px;line-height:25px;" >
											<tr>
												<td>卡号:<input type="text" name="returnCardNo" id="returnCardNo"  style="width:100;" onchange="validateNewCardNo(this)" />
													&nbsp;&nbsp;&nbsp;类型:<input type="text" name="returnCardTypename" id="returnCardTypename"  readonly="true" style="width:140;background:#EDF1F8;" />
																	
													&nbsp;&nbsp;&nbsp;金额:<input type="text" name="returnCardAmt" id="returnCardAmt"   style="width:50;" onchange="validatePrice(this);"/></td>
													<input type="hidden" name="returnCardType" id="returnCardType"   />
												<td><div id="insCardInfo"></div></td>
											</tr>
											<tr>
											<td colspan="2"><div id="commoninfodivdetial_cardPay"></div></td>
											</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr >
											<td colspan="6" color="#0000cc">
												<hr width="100%" />
											</td>
								</tr>
								<tr>
									<td colspan="6" >
										<font color="blue">现金退卡:&nbsp;</font>
										<input type="text" name="curMcardchangeinfo.cashfillamt" id="cashfillamt"  readonly="true" style="width:70;"  onchange="validatePrice(this);validateCashfillamt(this)"/>
										&nbsp;&nbsp;&nbsp;<font color="blue">银行卡退卡:&nbsp;</font>
										<input type="text" name="curMcardchangeinfo.bankfillamt" id="bankfillamt"  readonly="true" style="width:70;"  onchange="validatePrice(this);validateBankfillamt(this)"/>
										&nbsp;&nbsp;&nbsp;<font color="red">储值抵扣:&nbsp;</font>
										<input type="text" name="curMcardchangeinfo.keepamtfillamt" id="keepamtfillamt"  readonly="true" style="width:70;background:#EDF1F8;"  onchange="validatePrice(this);validateRechargeAmt(this)"/>
										&nbsp;&nbsp;&nbsp;扣除成本:&nbsp;
										<input type="text" name="curMcardchangeinfo.deductamt" id="deductamt"  readonly="true" style="width:70;background:#EDF1F8;"  onchange="validatePrice(this);validateRechargeAmt(this)"/>
										<input type="hidden" name="curMcardchangeinfo.changefillamt" id="changefillamt"/>
									</td>
								</tr>
								<tr >
											<td colspan="6" color="#0000cc">
												<hr width="100%" />
											</td>
								</tr>
								<tr>
									<td>
										退卡备注
									</td>
									<td colspan="4">
										<input type="text" name="curMcardchangeinfo.rechargeremark" id="rechargeremark"  readonly="true" style="width:300;" />
									</td>
									
								</tr>
								
								
								</table>
								<input type="hidden" name="curMcardchangeinfo.billflag" id="billflag"/>
								<input type="hidden" name="curMcardchangeinfo.id.changecompid" id="changecompid" />
							    <input type="hidden" name="curMcardchangeinfo.id.changebillid" id="changebillid"   />
							    <input type="hidden" name="curMcardchangeinfo.id.changetype" id="changetype"   />
								<input name="curMcardchangeinfo.salecooperid" 	id="salecooperid_h" type="hidden"/>
								<input name="curMcardchangeinfo.firstsalerid" 	id="firstsalerid_h" type="hidden"/>
								<input name="curMcardchangeinfo.secondsalerid" 	id="secondsalerid_h" type="hidden"/>
								<input name="curMcardchangeinfo.thirdsalerid" 	id="thirdsalerid_h" type="hidden"/>
								<input name="curMcardchangeinfo.fourthsalerid" 	id="fourthsalerid_h" type="hidden"/>
								<input name="curMcardchangeinfo.fifthsalerid" 	id="fifthsalerid_h" type="hidden"/>
								<input name="curMcardchangeinfo.sixthsalerid" 	id="sixthsalerid_h" type="hidden"/>
								<input name="curMcardchangeinfo.seventhsalerid" id="seventhsalerid_h" type="hidden"/>
								<input name="curMcardchangeinfo.eighthsalerid"  id="eighthsalerid_h" type="hidden"/>
								<input name="curCardSaleCardAmt" id="curCardSaleCardAmt" type="hidden"/>
								</form>
								</div>
						</td>
					</tr>
					</table>
					</td>
				</tr>
			</table>		
		</div>
				        
	</div> 

	
  <div style="display:none;">

</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  	
	</script>