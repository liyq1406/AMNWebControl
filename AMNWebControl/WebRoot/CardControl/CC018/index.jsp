<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*,com.amani.tools.CommonTool"%>
<%@include file="/include/sysfinal.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">  
<%
	response.setHeader("Pragma","No-cache"); 
 
	response.setHeader("Cache-Control","no-cache"); 
 
	response.setDateHeader("Expires", 0);  
	
 %>
<title></title>
<link
	href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js"
	type="text/javascript"></script>
<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
<script src="<%=ContextPath%>/common/ligerui/json2.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
<script type="text/javascript"
	src="<%=ContextPath%>/common/mindsearchitem.js"></script>
<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
<script type="text/javascript"
	src="<%=ContextPath%>/common/amnreport.js"></script>
<script type="text/javascript"
	src="<%=ContextPath%>/common/standprint.js"></script>
<script type="text/javascript"
	src="<%=ContextPath%>/CardControl/CC018/CC018.js"></script>
<style type="text/css">
body {
	padding: 5px;
	margin: 0;
	padding-bottom: 15px;
}

#layout1 {
	width: 100%;
	margin: 0;
	padding: 0;
}

.l-page-top {
	height: 80px;
	background: #f8f8f8;
	margin-bottom: 3px;
}

h4 {
	margin: 20px;
}
</style>
</head>
<body onunload="destory_activex()">
	<form id="frm">
		<div style="height: 586px;" id="layout1" class="l-layout"
			ligeruiid="layout1">
			<input name="curMaster.strImage" id="strImage" type="hidden" /> <input
				name="" id="strImage" type="hidden" /> <input name="totalamt"
				type="hidden" id="totalamt" size="10" readonly="true"><input
				id="curstate" type="hidden">
			<input name="curMaster.accountdate" id="accountdate" value="${curMaster.accountdate }" type="hidden" />
			<div class="l-layout-left"
				style="left: 0px; top: 260px; height: 40px;">
				<table width="100%" border="0" cellspacing="1" cellpadding="0"
					style="font-size:12px;line-height:40px;">
					<tr>
						<td>套餐编号</td>
						<td><input id="packno" name="packno" style="width:100px;"
							onfocus="itemsearchbeginInid(this,8)" onchange="checkPack()" /> <input
							id="packname" name="packname" style="width:120px;"
							disabled="true" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;赠送账户抵扣 <input id="zsdkamt" style="width:50px;" onchange="checkAmt(this)"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;抵用券<input id="dyqno" style="width:50px;" onchange="validateFastDyNo(this)"/>额度<input id="dyqamt" readonly="true" style="width:50px;"/></td>
						<td style="display:none">老疗程抵扣</td>
						<td style="display:none"><input id="oldproamt"
							style="width:50px;" disabled="true" />
						</td>
						<td></td>
						<td >
							部位备注 <input id="bz" maxlength="250" style="width:100px"/>
						</td>
						<td id="ishd"><input type="checkbox" id="ischeck"/>是否参加3.8节当日免单活动</td>
						<td><font color="red">输入完项目后，继续输入后面类容。部位备注回车。项目会加载到列表中</font></td>
					</tr>


				</table>
			</div>
			<div class="l-layout-left"
				style="left: 155px; top: 31px; width: 200px; height: 220px;">
				<table width="100%" border="0" cellspacing="1" cellpadding="0"
					style="font-size:12px;line-height:40px;">
					<tr>
						<td><font color="blue">手机号码</font></td>
						<td><input type="text" id="temphone" style="width:120;"
							maxlength="11" onchange="tempChange()" /> <input type="hidden"
							name="curMaster.phone" id="phone" style="width:120;"
							maxlength="10" readonly="true" /></td>
					</tr>
					<tr>
						<td>姓名</td>
						<td><input type="text" name="curMaster.name" id="name"
							style="width:120;" readonly="true" /></td>
					</tr>
					<tr>
						<td>介绍人手机</td>
						<td><input name="curMaster.idtphone" id="idtphone"
							style="width:120;" onchange="loadYearCard()" /></td>
					</tr>
					<tr>
						<td>介绍人姓名</td>
						<td><input name="idtname" id="idtname" readonly="true"
							style="width:120;" /></td>
					</tr>
				</table>
			</div>
			<div class="l-layout-left"
				style="left: 360px; top: 31px; width: 410px; height: 220px;">
				<table width="100%" border="1" cellspacing="1" cellpadding="0"
					style="font-size:12px;line-height:30px;">
					<tr>
						<td>卡号:<input name="curMaster.cardno" id="cardno"
							readonly="true" style="width:120;" />
						</td>
						<td>卡类别:<input type="text" name="curMaster.cardtype"
							id="cardtype" readonly="true" style="width:120;" />
						</td>
						<td align="center"><input id="readCard" class="l-button l-button" value="读卡"
							type="button" onclick="validateCscardno('')">
						</td>
					</tr>
					<tr>
						<td>姓名:<input type="text" name="menbername" id="menbername"
							style="width:120px;" readonly="true" /></td>
						<td>折扣:<input id="slaeproerate" name="slaeproerate"
							readonly="true" style="width:120px;margin-left:11px;" />
						</td>
						<td></td>
					</tr>
					<tr align="center">
						<td>支付方式</td>
						<td>支付金额</td>
						<td>账户余额</td>
					</tr>
					<tr align="center">
						<td>储值账户</td>
						<td><input name="curMaster.storedamt" id="storedamt" readonly="true"
							style="width:100%;height:30px;text-align:center;"
							onchange="changeStoreAmt(this)">
						</td>
						<td><input name="storeAmt" id="storeAmt" readonly="true"
							style="width:100%;height:30px;text-align:center;" /></td>
					</tr>
					<tr align="center">
						<td><select name="curMaster.cashpaycode" style="width:100px;"
							id="cashpaycode">
								<option value="1">现金</option>
								<option value="6">银行卡</option>
						</td>
						<td><input name="curMaster.cashamt" id="cashamt"
							style="width:100%;height:30px;text-align:center;"
							onchange="changeCashAmt(this)" />
						</td>
						<td>赠送账户</td>
					</tr>
					<tr align="center">
						<td>赠送支付</td>
						<td><input name="curMaster.zsamt" id="zsamt" only="true"
							style="width:100%;height:30px;text-align:center;"
							onchange="changeZSAmt(this)" />
						</td>
						<td><input name="zsyeamt" id="zsyeamt" readonly="true"
							style="width:100%;height:30px;text-align:center;"
							onchange="changeZSAmt(this)" /></td>
					</tr>
				</table>
			</div>
			<div style="left: 0px; top: 31px; width: 150px; height: 220px;"
				class="l-layout-center">
				<div style="height: 453px;" class="l-layout-content" title=""
					position="center">
					<object classid="clsid:34681DB3-58E6-4512-86F2-9477F1A9F3D8"
						id="cap" width="147.84" height="215.04"
						codebase="../cabs/ImageCapOnWeb.cab#version=2,0,0,0">
						<param name="Visible" value="0">
							<param name="AutoScroll" value="0">
								<param name="AutoSize" value="0">
									<param name="AxBorderStyle" value="1">
										<param name="Caption" value="scaner">
											<param name="Color" value="4278190095">
												<param name="Font" value="宋体">
													<param name="KeyPreview" value="0">
														<param name="PixelsPerInch" value="96">
															<param name="PrintScale" value="1">
																<param name="Scaled" value="-1">
																	<param name="DropTarget" value="0">
																		<param name="HelpFile" value>
																			<param name="PopupMode" value="0">
																				<param name="ScreenSnap" value="0">
																					<param name="SnapBuffer" value="10">
																						<param name="DockSite" value="0">
																							<param name="DoubleBuffered" value="0">
																								<param name="ParentDoubleBuffered" value="0">
																									<param name="UseDockManager" value="0">
																										<param name="Enabled" value="-1">
																											<param name="AlignWithMargins" value="0">
																												<param name="ParentCustomHint" value="-1">
																													<param name="licenseMode" value="3">
																														<param name="key1"
																															value="pR8GeRxKoiPWLbq/jUZyDCGRe+YyReKgT9i8Ho4PBkyGMl5fz3MSMg3gceSvovo+RyQ0Qrg5RAhcdcENmCBu7xgT0BwDAvQ3DBS3rokAfuBq9g==">
																															<param name="key2"
																																value="IY0dXhRqR5PQYBW68UEkeNAS609sHmtcGFhwu8zRUpJwz1XqUArZtRY0QLctXk2IUkQ+Fk9UHT78XPpUNnuqlny8O0lpfnP1R6hgc6BbrbGpbCjQAsTWvgt92L2qK/0cNRiQ1furHp2ZcyYJfqLVitJ7u18VLmEjtYAF+b0ekJSIjYoABvuxbhYixYKu8sdQLV+LctQFUkY=">
					</object>
					<!--  <table width="750px" border="0" cellspacing="1" cellpadding="0"
					style="font-size:12px;line-height:40px;">
					<tr>
						<td></td>
						<td>卡号:</td>
						<td><input name="curMaster.cardno" id="cardno"
							readonly="true" style="width:120;" />
						</td>
						<td></td>
						<td><input type="radio" id="isuse" name="isuse" onclick="ischeck()"/>不使用本卡资料</td>
						<td></td>
					</tr>
					<tr>
						<td><input id="readCard" class="l-button l-button" value="读卡"
							type="button" onclick="validateCscardno()">
						</td>
						<td>卡类型:</td>
						<td><input type="text" name="curMaster.cardtype"
							id="cardtype" readonly="true" style="width:120;" /></td>
						<td>姓名:</td>
						<td><input type="text" name="curMaster.name" id="name"
							style="width:120;" readonly="true"/></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td><font color="red">使用新号码:</font>
						</td>
						<td><input type="text" name="curMaster.oldphone" id="oldphone" onchange="changePhone()"
							 style="width:120;" /></td>
						<td><font color="blue">手机:</font>
						</td>
						<td>
							<input type="text" id="temphone" style="width:120;" maxlength="11" readonly="true" onchange="tempChange()"/>
							<input type="hidden" name="curMaster.phone" id="phone" style="width:120;" maxlength="10" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td></td>
						
						<td><select name="curMaster.ctype" id="ctype" style="display:none">
								<option value="1">身份证</option>
						</select>
						储值余额:
						</td>
						<td><input name="curMaster.cid" id="cid" style="width:120;" readonly="true" style="display:none"/>
							<input name="storeAmt" id="storeAmt" style="width:120;" readonly="true" />
						</td>
						<td>介绍人手机:</td>
						<td><input name="curMaster.idtphone" id="idtphone"style="width:120;" onchange="loadYearCard()"/> 
						<input name="idtname" id="idtname" readonly="true" style="width:120;" /></td>
						
					</tr>
					<tr>
						<td><input id="curstate" type="hidden"></td>
						<td>年卡套餐</td>
						<td colspan="3"><select name="packno" id="packno"
							style="width:450px">
							<option value=""></option>
								<s:iterator id="lsPacks" value="lsPacks" status="st">
									<option value="<s:property value="#lsPacks.id.packageno"/>">
										<s:property value="#lsPacks.packagename" />(<s:property value="#lsPacks.packageprice" />)
									</option>
								</s:iterator>
						</select>
						<input id="Button1" class="l-button l-button" value="确认添加" type="button" onclick="lsDkageinfos()">
						</td>
					</tr>
					<tr>
						<td></td>
						<td>总金额:<input name="totalamt" id="totalamt" size="10" readonly="true"></td>
						<td>储值支付:<input name="curMaster.storedamt" id="storedamt" size="5" onchange="changeStoreAmt(this)" readonly="true"></td>
						<td colspan="1">现金支付:<select name="curMaster.cashpaycode" id="cashpaycode">
											  <option value="1">现金</option>
											  <option value="6">银行卡</option>
							</select>
							<input name="curMaster.cashamt" id="cashamt" size="5" onchange="changeCashAmt(this)"/>	
						</td>
						<td colspan="1">
							赠送支付:<input name="curMaster.zsamt" id="zsamt" size="5" onchange="changeZSAmt(this)"/>
						</td>
					</tr>
				</table>-->

				</div>
			</div>
			<div style="left: 765px; top: 31px; height: 220px;"
				class="l-layout-right">
				<div class="l-layout-content" position="right">
					<div id="proItem"></div>
				</div>
			</div>
			<div style="top: 304px; height: 320px;" class="l-layout-bottom">
				<div class="l-layout-content" position="bottom">
					<table width="100%" border="1px" cellspacing="0" cellpadding="0"
						style="font-size:12px;line-height:18px;float:left">
						<tr align="center" style="background-color:#E2EEFE">
							<td width="10%">疗程名称</td>
							<td width="3%">套数</td>
							<td width="3%">原价</td>
							<td width="3%">金额</td>
							<td width="5%">第一销售</td>
							<td width="5%">姓名</td>
							<td width="5%">金额</td>
							<td width="5%">第二销售</td>
							<td width="5%">姓名</td>
							<td width="5%">金额</td>
							<td width="5%">第三销售</td>
							<td width="5%">姓名</td>
							<td width="5%">金额</td>
							<td width="5%">烫染师</td>
							<td width="5%">姓名</td>
							<td width="5%">金额</td>
							<td width="5%">备注</td>
						</tr>
						<tbody id="tabItem">

						</tbody>
					</table>

				</div>
			</div>
			<div style="top: 0px; height: 30px;" class="l-layout-top">
				<div class="l-layout-content" position="top">
					<table border="0" cellspacing="1" cellpadding="0"
						style="width:100%;font-size:12px;line-height:30px">
						<tr align="center">
							<td align="left"><font color="bule" size="4">销售单号:&nbsp;&nbsp;&nbsp;<input
									id="billid" name="curMaster.id.billid"
									value="${curMaster.id.billid }" readonly="true">
							</td>
							<td width="30%">微信扫码:<input name="randomno" id="randomno" size="25" onchange="checkRandomno()"></td>
							<td width="40%" align="right">
								<!--  <input id="custphone" class="l-button l-button" value="拍照" type="button" onclick="Camp(this)">-->
								<input id="Button1" class="l-button l-button" value="刷新"
								type="button" onclick="javascript:location.reload();"> <input
									id="postbill" class="l-button l-button" value="保存单据"
									onclick="post()" type="button"><input type="text"
										name="strSearchContent" id="strSearchContent"
										onblur="if(this.value==''){this.value='输入单号或卡号后查询';};"
										onfocus="if(this.value=='输入单号或卡号后查询'){this.value='';}"
										value="输入单号或卡号后查询"
										onMouseOver="this.style.border='1px solid #6C0'"
										onMouseOut="this.style.border='1px solid #ccc' "
										style="width:160px;height:22" /> <input id="Button1"
										class="l-button l-button" value="查询" type="button"
										onclick="sechbill()"> <input id="Button1"
											class="l-button l-button" value="打印" onclick="printBill()"
											type="button">
							</td>
						</tr>
					</table>

				</div>
			</div>
		</div>
		<div style="display: none;"></div>
		<div id="printContent"
			style="position:absolute;left:10px; top:60px; z-index:-2;display:none">
			<table width="260px" border="0" cellspacing="0" cellpadding="2"
				id="text">
				<tr>
					<td align="left" width="90">日期:&nbsp;</td>
					<td><label id="currdate_print"> &nbsp; </label></td>
				</tr>
				<tr>
					<td align="left" width="90">会员卡号:&nbsp;</td>
					<td><label id="memberCardId_print"> &nbsp; </label></td>
				</tr>

				<tr>
					<td align="left" width="90">储值余额:&nbsp;</td>
					<td><label id="keepAmount_print"> &nbsp; </label></td>
				</tr>
				<tr>
					<td colspan="2">
						<hr width="100%" /></td>
				</tr>
			</table>
			<table width="260px" border="0" cellspacing="0" cellpadding="2"
				id="text">
				<tr>
					<td width="180px" style="font-size:12px">年卡疗程名称</td>
					<td width="40px" style="font-size:12px">金额</td>
					<td width="40px" style="font-size:12px">套餐</td>

				</tr>
				<tbody id="changeDetail" style="width:180px;">
				</tbody>
			</table>
			<table width="260px" border="0" cellspacing="0" cellpadding="2"
				id="text">
				<tr>
					<td colspan="2">
						<hr width="100%" /></td>
				</tr>
				<tr>
					<td align="left" width="90">交易号:&nbsp;</td>
					<td><label id="tradebillId_print"> &nbsp; </label></td>
				</tr>
				<tr>
					<td align="left" width="90">打印时间:&nbsp;</td>
					<td><label id="printTime_print"> &nbsp; </label></td>
				</tr>
				<tr>
					<td align="left" width="90">客户签名:&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="left">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="left">门店电话:&nbsp;</td>
					<td><label id="telephone_print"> &nbsp; </label></td>
				</tr>

				<tr>

					<td align="left" colspan="2">&nbsp; <label id="address_print">
							&nbsp; </label></td>
				</tr>

			</table>
		</div>
	</form>
</body>
</html>
<script language="JavaScript">
	var contextURL = "<%=request.getContextPath()%>";
	document
			.write("<div id=\"keysList\" style=\"z-index:2;width:350px;position:absolute;display:none;background:#FFFFFF;border: 2px solid #a4a6a1;font-size:13px;cursor: default;\" onblur> </div>");
	document
			.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
</script>
