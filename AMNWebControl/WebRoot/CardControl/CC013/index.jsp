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

	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC013/cc013.js"></script>

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

  	 <div id="cc013layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
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
										<td width="40%"><font color="bule" size="4">录入单号:&nbsp;&nbsp;&nbsp;<label id="lbBillId"></label>		
										</font></td>
										<td width="40%" align="right">
										    <a onclick="freshCurRecord()"><img src="<%=ContextPath%>/common/funtionimage/refresh.png" 	style="width:25;height:25" alt="刷新"></img></a>
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
								
								<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:13px;line-height:28px;" >
							
								<tr>
									<td nowrap="nowrap"><font color="red" >合作单位</font></td>
									<td nowrap="nowrap">
										<input type="text" name="salecooperid" id="salecooperid"  readonly="true" style="width:120;" />
										
									</td>
									<td class="hzxk" nowrap="nowrap" rowspan="14" valign="top" width="270">
										<div id="hzlcmaster"></div>
									</td>
									<td nowrap="nowrap">
										转卡日期
									</td>
									<td width="180" nowrap="nowrap">
										<input type="text" name="curMcooperatesaleinfo.saledate" id="saledate"  readonly="true" style="width:80;" />
										<input type="text" name="curMcooperatesaleinfo.saletime" id="saletime"  readonly="true" style="width:80;" />
									</td>
									<td valign="top" rowspan="6">	<div id="commoninfodivdetial_pay" style="margin:0; padding:0"></div></td>
								</tr>
								<tr>
									<td>
										<div id="vipCardNo">会员卡号</div>
										<div id="readCurCardInfo"></div>
									</td>
									<td>
										<input type="text" name="curMcooperatesaleinfo.salecostcardno" id="salecostcardno"  readonly="true" style="width:120;"  onchange="validateCostcardno(this)"/>
									</td>
									<td>
										<label id="lboldcardtype">卡类型</label>
									</td>
									<td >
										<input type="text" name="curMcooperatesaleinfo.salecostcardtype" id="salecostcardtype"  maxlength="20" readonly="true" style="width:80;" />
										<input type="text" name="curMcooperatesaleinfo.salecostcardtypename" id="salecostcardtypename"  maxlength="20"  readonly="true" style="width:80;" />
									</td>
								</tr>
								<tr>
									<td>
										<font color="blue">会员姓名</font>
									</td>
									<td>
										<input type="text" name="curMcooperatesaleinfo.membername" id="membername"  readonly="true" style="width:120;"  />
									</td>
									<td>
										<font color="blue">手机号码</font>
									</td>
									<td >
										<input type="text" id="temphone"  readonly="true" style="width:100;" onchange="changephone(this)"/>
										<input type="hidden" name="curMcooperatesaleinfo.memberphone" id="memberphone"  readonly="true" style="width:100;" />
									</td>
								</tr>
								<tr>
									<td>
										<font color="blue">项目额度</font>
									</td>
									<td>
										<input type="text" name="curMcooperatesaleinfo.salecostproamt" id="salecostproamt"  readonly="true" style="width:120;"  onchange="validatePrice(this);validateSalecostproamt(this)"/>
									</td>
									
									<td>
										<font color="blue">支付方向</font>
									</td>
									<td >
										<select id="slaepaymode" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;" name="curMcooperatesaleinfo.slaepaymode" >
										<option value="1">店内支付</option>
										<option value="2">合作单位支付</option>
										</select>
									</td>
								</tr>
								<tr class="hzxk">
									<td>
										<font color="blue">储值余额</font>
									</td>
									<td>
										<input type="text" id="account2Amt" name="curMcooperatesaleinfo.account2Amt"  readonly="true" style="width:120;" />
										<input type="hidden" id="slaeproerate"/>
									</td>
									
									<td>
										<font color="blue">储值支付</font>
									</td>
									<td >
										<input type="text" id="salescardpayment" name="curMcooperatesaleinfo.salescardpayment" readonly="true" style="width:120;" />
									</td>
								</tr>
								<tr class="hzxk">
									<td>
										<font color="blue">支付方式</font>
									</td>
									<td>
										<select id="paymentmethod" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;" name="curMcooperatesaleinfo.paymentmethod" >
										<option value="1">现金</option>
										<option value="6">银行卡</option>
										</select>
									</td>
									
									<td>
										<font color="blue">支付金额</font>
									</td>
									<td >
										<input type="text" id="paymentamount" name="curMcooperatesaleinfo.paymentamount" style="width:120;" readonly="readonly" />
									</td>
								</tr>
								<tr>
									<td>第一销售</td>
									<td><input type="text" name="firstsalerid" id="firstsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
									
									<td>分享金额</td>
									<td><input type="text"  name="curMcooperatesaleinfo.firstsaleamt" id="firstsaleamt"  readonly="true" style="width:100;" onchange="validatePrice(this)"/></td>
									
									
								</tr>
								<tr>
									<td>第二销售</td>
									<td><input type="text" name="secondsalerid" id="secondsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
									
									<td>分享金额</td>
									<td><input type="text"  name="curMcooperatesaleinfo.secondsaleamt" id="secondsaleamt"  readonly="true" style="width:100;" onchange="validatePrice(this)"/></td>
								</tr>
								<tr>
									<td>第三销售</td>
									<td><input type="text" name="thirdsalerid" id="thirdsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
									
									<td>分享金额</td>
									<td><input type="text"  name="curMcooperatesaleinfo.thirdsaleamt" id="thirdsaleamt"  readonly="true" style="width:100;" onchange="validatePrice(this)"/></td>
									<td ><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实际支付方式</font></td>
								</tr>
								<tr>
									<td>第四销售</td>
									<td><input type="text" name="fourthsalerid" id="fourthsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
									
									<td>分享金额</td>
									<td><input type="text"  name="curMcooperatesaleinfo.fourthsaleamt" id="fourthsaleamt"  readonly="true" style="width:100;" onchange="validatePrice(this)"/></td>
									<td valign="top" rowspan="5">	<div id="commoninfodivdetial_payFact" style="margin:0; padding:0"></div></td>
							
								</tr>
								<tr>
									<td>第五销售</td>
									<td><input type="text" name="fifthsalerid" id="fifthsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
									
									<td>分享金额</td>
									<td><input type="text"  name="curMcooperatesaleinfo.fifthsaleamt" id="fifthsaleamt"  readonly="true" style="width:100;" onchange="validatePrice(this)"/></td>
								</tr>
								<tr>
									<td>第六销售</td>
									<td><input type="text" name="sixthsalerid" id="sixthsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
									
									<td>分享金额</td>
									<td><input type="text"  name="curMcooperatesaleinfo.sixthsaleamt" id="sixthsaleamt"  readonly="true" style="width:100;" onchange="validatePrice(this)"/></td>
								</tr>
									<tr>
									<td>第七销售</td>
									<td><input type="text" name="seventhsalerid" id="seventhsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
									
									<td>分享金额</td>
									<td><input type="text"  name="curMcooperatesaleinfo.seventhsaleamt" id="seventhsaleamt"  readonly="true" style="width:100;" onchange="validatePrice(this)"/></td>
								</tr>
								<tr>
									<td>第八销售</td>
									<td><input type="text" name="eighthsalerid" id="eighthsalerid"  readonly="true" style="width:140;" onchange="checkStaffValuex(this)"/></td>
									
									<td>分享金额</td>
									<td><input type="text"  name="curMcooperatesaleinfo.eighthsaleamt" id="eighthsaleamt"  readonly="true" style="width:100;" onchange="validatePrice(this)"/></td>
								</tr>
								<tr><td><font color="red">单据状态</font></td>
									<td  colspan="5">
									<input type="radio" id="salebillflag0" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMcooperatesaleinfo.salebillflag" value="0"   disabled="true"/>单据录入
									<input type="radio" id="salebillflag1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMcooperatesaleinfo.salebillflag" value="1"   disabled="true"/>已登记
									<input type="radio" id="salebillflag2" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMcooperatesaleinfo.salebillflag" value="2"   disabled="true"/>已审核
									</td>
									
								</tr>
								<tr>
									<td>
										录入备注
									</td>
									<td colspan="4" >
										<input type="text" name="curMcooperatesaleinfo.salemark" id="salemark"  readonly="true" style="width:250;" />
									</td>
									<td><div id="confirmBillInfo"></div></td>
									
								</tr>
								
								
								</table>
								<input type="hidden" name="curMcooperatesaleinfo.id.salecompid" id="salecompid" />
							    <input type="hidden" name="curMcooperatesaleinfo.id.salebillid" id="salebillid"   />
							    <input name="curMcooperatesaleinfo.salecooperid" 	id="salecooperid_h" type="hidden"/>
								<input name="curMcooperatesaleinfo.firstsalerid" 	id="firstsalerid_h" type="hidden"/>
								<input name="curMcooperatesaleinfo.secondsalerid" 	id="secondsalerid_h" type="hidden"/>
								<input name="curMcooperatesaleinfo.thirdsalerid" 	id="thirdsalerid_h" type="hidden"/>
								<input name="curMcooperatesaleinfo.fourthsalerid" 	id="fourthsalerid_h" type="hidden"/>
								<input name="curMcooperatesaleinfo.fifthsalerid" 	id="fifthsalerid_h" type="hidden"/>
								<input name="curMcooperatesaleinfo.sixthsalerid" 	id="sixthsalerid_h" type="hidden"/>
								<input name="curMcooperatesaleinfo.seventhsalerid" id="seventhsalerid_h" type="hidden"/>
								<input name="curMcooperatesaleinfo.eighthsalerid"  id="eighthsalerid_h" type="hidden"/>
							
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