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
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC009/cc009.js"></script>

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

  	 <div id="cc009layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
		<div position="center"   id="designPanel"  style="width:100%;" title=""> 
			<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
				<tr>
					<td valign="top" width="260" >
						
						<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
					</td>
					<td valign="top">	
					<table  border="0" cellspacing="1" cellpadding="0" style="width:100%;font-size:12px;line-height:30px" >
					<tr>
						<td  valign="top">
							<div  style="width:100%;float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<table  id="mainToolTable" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:30px" >
									<tr>
										<td width="30%"><font color="bule" size="4">转卡单号:&nbsp;&nbsp;&nbsp;<label id="lbBillId"></label>		
										</font></td>
										<td width="30%">微信扫码:<input name="randomno" id="randomno" size="25" onchange="checkRandomno()"></td>
										<td width="40%" align="right">
										    <a onclick="freshCurRecord()"><img src="<%=ContextPath%>/common/funtionimage/refresh.png" 	style="width:25;height:25" alt="刷新"></img></a>
											<a onclick="editCurRecord()"><img src="<%=ContextPath%>/common/funtionimage/post.png" 	style="width:25;height:25" alt="保存"></img></a>
											<a onclick="viewTicketReport()"><img src="<%=ContextPath%>/common/funtionimage/print.png" 	style="width:25;height:25" alt="打印"></img></a>
											
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
								
								<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:13px;line-height:28px;" >
							
								<tr>
									
							
									<td><font color="red" >转卡类型</font></td>
									<td>
											<select id="changetype" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;" name="curMcardchangeinfo.id.changetype" onchange="changeType(this)" >
											<option value="0">折扣转卡</option>
											<option value="1">收购转卡</option>
											<option value="6">老卡并老卡</option>
											<option value="7">老卡并新卡</option>
											</select>
											</td>
									<td >是否享受虚拟卡金</td>
									<td>
										<select id="isxnj" name="curMcardchangeinfo.isxnj" style="width:140" onchange="xnchange(this)" disabled="true">
											<option value="0">否</option>
											<option value="1">是</option>
										</select>
									</td>
									<td valign="top" rowspan="8">	<div id="commoninfodivdetial_pay" style="margin:0; padding:0"></div></td>
									<td valign="top" rowspan="8"><div id="commoninfodivdetial_oldcustomer" style="margin:0; padding:0"></div></td>
							
								</tr>
								<tr>
									<td  ><div id="readCurCardInfo"></div></td>
									<td colspan="3">
										<input type="text" name="curMcardchangeinfo.changebeforcardno" id="changebeforcardno"  readonly="true" style="width:120;"  onchange="validateChangebeforcardno(this)"/>
									</td>
							
									
								</tr>
								<tr>
									<td><font color="red">卡来源</font></td>
									<td>
										<select id="billinsertype" name="curMcardchangeinfo.billinsertype" style="width:120" onchange="validateInserType(this)" >
											<option value="0">--</option>
											<option value="1">美容</option>
											<option value="2">美发</option>
											<option value="3">合作</option>
										</select>
									</td>
									
									<td><font color="blue">刷卡凭证号</font></td>
									<td><input type="text" name="curMcardchangeinfo.bankcostno" id="bankcostno"   style="width:140;" maxlength="10"  /></td>
							
								</tr>
								<tr>
								<td>
										<label id="lboldcardtype">卡类型</label>
									</td>
									<td >
										<input type="text" name="curMcardchangeinfo.changebeforcardtypename" id="changebeforcardtypename"  readonly="true" style="width:120;" />
										<input type="hidden" name="curMcardchangeinfo.changebeforcardtype" id="changebeforcardtype"   />
									</td>
								<td>
										转卡日期
									</td>
									<td width="180">
										<input type="text" name="curMcardchangeinfo.changedate" id="changedate"  readonly="true" style="width:80;" />
										<input type="text" name="curMcardchangeinfo.changetime" id="changetime"  readonly="true" style="width:80;" />
									</td>
								</tr>
								<tr>
									<td>
										会员姓名
									</td>
									<td>
										<input type="text" name="lbmembername" id="lbmembername"  readonly="true" style="width:120;"  />
										<input type="hidden" name="curMcardchangeinfo.membername" id="membername"  />
									</td>
							
									<td>
										手机号码
									</td>
									<td >	
										<input type="text" name="lbmemberphone" id="lbmemberphone"  readonly="true" style="width:80;" />
										<input type="hidden" name="curMcardchangeinfo.memberphone" id="memberphone" />
									</td>
									<td rowspan="10" valign="top">
										
									</td>
								</tr>
								<tr>
									<td>
										储值余额
									</td>
									<td>
										<input type="text" name="curMcardchangeinfo.curaccountkeepamt" id="curaccountkeepamt"  readonly="true" style="width:120;"  />
									</td>
							
									<td>
										储值欠款
									</td>
									<td >
										<input type="text" name="curMcardchangeinfo.curaccountdebtamt" id="curaccountdebtamt"  readonly="true" style="width:80;" />
									</td>
								</tr>
								<tr>
									<td>
										疗程余额
									</td>
									<td>
										<input type="text" name="curMcardchangeinfo.curproaccountkeepamt" id="curproaccountkeepamt"  readonly="true" style="width:120;" />
									</td>
							
									<td>
										疗程欠款
									</td>
									<td >
										<input type="text" name="curMcardchangeinfo.curproaccountdebtamt" id="curproaccountdebtamt"  readonly="true" style="width:80;" />
									</td>
								</tr>
								
								<tr id="newCardTr">
									
									<td>
										<font color="blue">新卡卡号</font>
									</td>
									<td>
										<input type="text" name="curMcardchangeinfo.changeaftercardno" id="changeaftercardno"  readonly="true" style="width:120;" onchange="validateChangeaftercardno(this)" />
									</td>
							
									<td>
										卡类型
									</td>
									<td >
										<input type="text" name="curMcardchangeinfo.changeaftercardtypename" id="changeaftercardtypename"  readonly="true" style="width:140;" />
										<input type="hidden" name="curMcardchangeinfo.changeaftercardtype" id="changeaftercardtype"  />
										
									</td>
									
								</tr>
								
							  
								<tr>
									<td>
										<font color="red">最低额度</font>
									</td>
									<td>
										<input type="hidden" name="combinlowamt" id="combinlowamt"/>
										<input type="text" name="curMcardchangeinfo.changelowamt" id="changelowamt"  readonly="true" style="width:120;" />
									</td>
							
									<td>
										<font color="blue">充值金额</font>
									</td>
									<td >
										<input type="text" name="curMcardchangeinfo.changefillamt" id="changefillamt"  readonly="true" style="width:80;"  onchange="validatePrice(this);validateRechargeAmt(this)"/>
									</td>
								</tr>
								<tr>
									<td>第一销售</td>
									<td><input type="text" name="curMcardchangeinfo.firstsalerid" id="firstsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
										<input type="text" name="firstsalername" id="firstsalername"  readonly="true" style="width:80;"  /></td>
									<td>分享金额</td>
									<td><input type="text"  name="curMcardchangeinfo.firstsaleamt" id="firstsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/>
									&nbsp;分享业绩<input type="text"  name="curMcardchangeinfo.firstsalecashamt" id="firstsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
									
								</tr>
								<tr>
									<td>第一烫染</td>
									<td><input type="text" name="curMcardchangeinfo.fifthsalerid" id="fifthsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
										<input type="text" name="fifthsalername" id="fifthsalername"  readonly="true" style="width:80;"  /></td>
									<td>分享金额</td>
									<td><input type="text"  name="curMcardchangeinfo.fifthsaleamt" id="fifthsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/>
									&nbsp;分享业绩<input type="text"  name="curMcardchangeinfo.fifthsalecashamt" id="fifthsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
								</tr>
								<tr>
									<td>第二销售</td>
									<td><input type="text" name="curMcardchangeinfo.secondsalerid" id="secondsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
										<input type="text" name="secondsalername" id="secondsalername"  readonly="true" style="width:80;"  /></td>
									<td>分享金额</td>
									<td><input type="text"  name="curMcardchangeinfo.secondsaleamt" id="secondsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/>
									&nbsp;分享业绩<input type="text"  name="curMcardchangeinfo.secondsalecashamt" id="secondsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
									<td valign="top" rowspan="6" colspan="2" id="oldcarddetial_id" style="display:none">	<div id="commoninfodivdetial_combin" style="margin:0; padding:0"></div></td>
									
								</tr>
								<tr>
									<td>第二烫染</td>
									<td><input type="text" name="curMcardchangeinfo.sixthsalerid" id="sixthsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
										<input type="text" name="sixthsalername" id="sixthsalername"  readonly="true" style="width:80;"  /></td>
									<td>分享金额</td>
									<td><input type="text"  name="curMcardchangeinfo.sixthsaleamt" id="sixthsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/>
									&nbsp;分享业绩<input type="text"  name="curMcardchangeinfo.sixthsalecashamt" id="sixthsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
								</tr>
								<tr>
									<td>第三销售</td>
									<td><input type="text" name="curMcardchangeinfo.thirdsalerid" id="thirdsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
										<input type="text" name="thirdsalername" id="thirdsalername"  readonly="true" style="width:80;"  /></td>
									<td>分享金额</td>
									<td><input type="text"  name="curMcardchangeinfo.thirdsaleamt" id="thirdsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/>
									&nbsp;分享业绩<input type="text"  name="curMcardchangeinfo.thirdsalecashamt" id="thirdsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
								</tr>
								<tr>
									<td>第三烫染</td>
									<td><input type="text" name="curMcardchangeinfo.seventhsalerid" id="seventhsalerid"  readonly="true" style="width:60;" onchange="checkStaffValuex(this)"/>
											<input type="text" name="seventhsalername" id="seventhsalername"  readonly="true" style="width:80;"  /></td>
									<td>分享金额</td>
									<td><input type="text"  name="curMcardchangeinfo.seventhsaleamt" id="seventhsaleamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/>
									&nbsp;分享业绩<input type="text"  name="curMcardchangeinfo.seventhsalecashamt" id="seventhsalecashamt"  readonly="true" style="width:50;" onchange="validatePrice(this)"/></td>
								</tr>
							
								<tr>
									<!-- <td>经理/顾问</td> -->
									<td><input type="hidden" name="curMcardchangeinfo.beautyManager" id="beautyManager"   style="width:60;" onchange="checkStaffValuex(this)"/>
										<input type="hidden" name="beautyManagerName" id="beautyManagerName"  readonly="true" style="width:80;"  />
									</td>
								</tr>
								<tr>
									<!-- <td>经理/顾问</td> -->
									<td><input type="hidden" name="curMcardchangeinfo.consultant" id="consultant"   style="width:60;" onchange="checkStaffValuex(this)"/>
										<input type="hidden" name="consultantName" id="consultantName"  readonly="true" style="width:80;"  />
									</td>
								</tr>
								<tr>
								<tr>
									<!-- <td>经理/顾问</td> -->
									<td><input type="hidden" name="curMcardchangeinfo.consultant1" id="consultant1"   style="width:60;" onchange="checkStaffValuex(this)"/>
										<input type="hidden" name="consultantName1" id="consultantName1"  readonly="true" style="width:80;"  />
									</td>
								</tr>
					
								<tr>
									<td>
										转卡备注
									</td>
									<td colspan="4">
										<input type="text" name="curMcardchangeinfo.rechargeremark" id="rechargeremark"  readonly="true" style="width:250;" />
									</td>
									
								</tr>
								
								
								</table>
								<input type="hidden" name="curMcardchangeinfo.id.changecompid" id="changecompid" />
							    <input type="hidden" name="curMcardchangeinfo.id.changebillid" id="changebillid"   />
								<input type="hidden"  name="curMcardchangeinfo.sendamtflag" id="sendamtflag"  />
								<input name="curCardSaleCardAmt" id="curCardSaleCardAmt" type="hidden"/>
								</form>
								<input  type="hidden" name="strShareCondition" id="strShareCondition"/>
								<input  type="hidden" name="cardsendamtflag" id="cardsendamtflag"/>
								<div id="printContent" style="position:absolute;left:10px; top:60px; z-index:-2;display:none" >
									<table  border="0" width="230px" cellspacing="0" cellpadding="2" style="line-height:20px;" id="text">
									    
									     <tr>
									       <td align="left" width="90">日期:&nbsp;</td><td>&nbsp;<label id="currdate_print">&nbsp;</label></td>
									     </tr>
									      <tr>
									       <td align="left" width="90">原会员卡号:&nbsp;</td><td>&nbsp;<label id="memberOldCardId_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									       <td align="left" width="90">新会员卡号:&nbsp;</td><td>&nbsp;<label id="memberCardId_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									       <td align="left" width="90">卡类型:&nbsp;</td><td>&nbsp;<label id="memberCardType_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									       <td  colspan="2"><hr  width="100%"></td>
									     </tr>
									     <tr>
									       <td align="left" width="90">原金额:&nbsp;</td><td>&nbsp;<label id="keepAmountOld_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									       <td align="left" width="90">充值金额:&nbsp;</td><td>&nbsp;<label id="keepAmount_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									       <td align="left" width="90">欠款金额:&nbsp;</td><td>&nbsp;<label id="debtAmount_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									       <td align="left" width="90">最新余额:&nbsp;</td><td>&nbsp;<label id="incardAmount_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									       <td align="left" width="90">最新欠款:</td><td>&nbsp;<label id="cardDebtAmount_print">&nbsp;</label></td>
									     </tr>
									   
									      <tr>
									        <td  colspan="2">
									          付款明细
									        <table >
									        <tbody id = "consumeDetail" name="consumeDetail">
									        </tbody>
									       </table>
									       </td>
									     </tr>
									     <tr>
									       <td align="left" width="90">交易号:&nbsp;</td><td>&nbsp;<label id="tradebillId_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									       <td align="left" width="90">收银员:&nbsp;</td><td>&nbsp;<label id="clerkName_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									       <td align="left" width="90">打印时间:&nbsp;</td><td>&nbsp;<label id="printTime_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									       <td align="left" width="90">客户签名:&nbsp;</td><td>&nbsp;</td>
									     </tr>
									      <tr>
									       <td align="left" width="90">&nbsp;</td><td>&nbsp;</td>
									     </tr>
									      <tr>
									       <td align="left" width="90">&nbsp;</td><td>&nbsp;</td>
									     </tr>
									     <tr>
									       <td align="left" width="90">门店电话:&nbsp;</td><td>&nbsp;<label id="telephone_print">&nbsp;</label></td>
									     </tr>
									     <tr>
									     	<td colspan="2">&nbsp;<label id="address_print">&nbsp;</label></td>
									     </tr>
									    </table>
							    </div>
			    
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