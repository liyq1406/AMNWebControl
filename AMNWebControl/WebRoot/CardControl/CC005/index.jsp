<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>

	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC005/cc005.js"></script>
 	<style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
  
		    body,html{height:100%;}
	    body{ padding:0px; margin:0;   overflow:hidden;}  
	   
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="cc005layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
  	 
	  			 	<div position="center"   id="designPanel"  style="width:100%;" > 
					          <div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<table width="1300px" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
								<tr>
									<td   width="100"><div id="readCurCardInfo"></div></td>
									<td><input id="searchMemberNoKey" type="text" style="width:140"/> &nbsp;
									&nbsp;会员姓名：<input id="searchMemberNameKey" type="text" style="width:120"/> &nbsp;
									&nbsp;手机号码：<input id="searchMemberPhoneKey" type="text" size="10"/> &nbsp;
									&nbsp;证件号码：<input id="searchMemberPCIDKey" type="text" size="14"/> &nbsp;
									</td>
									<td>&nbsp;<select id="searcharDataType" name="searcharDataType" style="width:100">
									<option value="1" selected="true">新系统卡</option>
									<option value="2" >老系统卡</option>
									</select> &nbsp;
									</td>
									<td><div id="searchCurCardInfo"></div></td>
									<td colspan="4">&nbsp;<font color="red"><b>十周年验证码：</b></font><input id="searchMemberCode" disabled="true" size="14"/> &nbsp;</td>
								</tr>
								</table>
								</div>
								<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
								<tr>
								<td valign="top" >
									<div id="commoninfodivsecond" style="margin:0; padding:0"></div>
								</td>
								<td valign="top" width="80%">
								<div id="CC005DetialTab" style="width:100%; height:95%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
									<div title="会员卡资料信息" style="font-size:12px;" style="width:100%; height:98%; ">
										<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
											<tr>
												<td colspan="4" align="center">
													<div id="initCurCardInfo"></div>
												</td>
											</tr>
											<tr>
											<td>归属门店</td>
											<td><s:textfield name="curCardinfo.id.cardvesting" id="cardvesting" theme="simple" style="width:140" readonly="true" /></td>
											<td rowspan="11" valign="top" width="500">
											<div id="commoninfodivAccount" style="margin:0; padding:0"></div>
											<div id="dhpro"></div>
											<div id="dhpromark"></div>
											</td>
											<td  rowspan="11" valign="top">
											 <div  id="projectInfoDiv" style="width:100%; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
												<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
												<tr><td>设计师洗吹折后价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="costxc1" name="costxc1" style="width:60" onchange="validatePrice(this)"/></td></tr>
												<tr><td>首席洗吹折后价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="costxc2" name="costxc2" style="width:60" onchange="validatePrice(this)"/></td></tr>
												<tr><td>总监洗吹折后价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="costxc3" name="costxc3" style="width:60" onchange="validatePrice(this)"/></td></tr>
												<tr><td>创意总监洗吹折后价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="costxc4" name="costxc4" style="width:60" onchange="validatePrice(this)"/></td></tr>
												<tr><td>设计师洗剪吹折后价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="costxc5" name="costxc5" style="width:60" onchange="validatePrice(this)"/></td></tr>
												<tr><td>首席洗剪吹折后价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="costxc6" name="costxc6" style="width:60" onchange="validatePrice(this)"/></td></tr>
												<tr><td>总监洗剪吹折后价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="costxc7" name="costxc7" style="width:60" onchange="validatePrice(this)"/></td></tr>
												<tr><td>创意总监洗剪吹折后价&nbsp;&nbsp;&nbsp;<input type="text" id="costxc8" name="costxc8" style="width:60" onchange="validatePrice(this)"/></td></tr>
												<tr><td>店长洗剪吹折后价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="costxc9" name="costxc9" style="width:60" onchange="validatePrice(this)"/></td></tr>
												<tr><td align="center"><div id="initProjectInfo"></div></td></tr>
												</table>
											</div>
											</td>
											</tr>
											<tr>
											<td>会员卡号</td>
											<td><s:textfield name="curCardinfo.id.cardno" id="cardno" theme="simple" style="width:140" readonly="true" /></td>
											</tr>
											<tr>
											<td>卡类型</td>
											<td><s:textfield name="curCardinfo.cardtype" id="cardtype" theme="simple" style="width:40" readonly="true" />
											<s:textfield name="curCardinfo.cardtypeName" id="cardtypeName" theme="simple" style="width:95" readonly="true" />
											</td>
											</tr>
											<tr>
											<td>会员卡状态</td>
											<td><s:textfield name="curCardinfo.cardstate" id="cardstate" theme="simple" style="width:140" readonly="true" /></td>
											</tr>
											<tr>
											<td>卡来源</td>
											<td>
											<s:radio label="卡来源" id="cardsource" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
												name="curCardinfo.cardsource" list="#{0:\"内部卡\",1:\"收购卡\"}"
												listKey="key" theme="simple" listValue="value" disabled="true"/></td>
											</tr>
											<tr>
											<td>会员姓名</td>
											<td><s:textfield name="curCardinfo.membername" id="membername" theme="simple" style="width:140" readonly="true" /></td>
											</tr>
											<tr>
											<td>手机号码</td>
											<td><s:textfield name="curCardinfo.memberphone" id="memberphone" theme="simple" style="width:140" readonly="true" /></td>
											</tr>
											<tr>
											<td>身份证号</td>
											<td><s:textfield name="curCardinfo.memberpcid" id="memberpcid" theme="simple" style="width:140" readonly="true" /></td>
											</tr>
											<tr>
											<td>欠款消费额度</td>
											<td><s:textfield name="ccurCardinfo.costamtbydebts" id="costamtbydebts" theme="simple" style="width:120" readonly="true" /></td>
											</tr>
											<tr>
											<td>欠款消费次数</td>
											<td><s:textfield name="curCardinfo.costcountbydebts" id="costcountbydebts" theme="simple" style="width:120" readonly="true" /></td>
											</tr>
											<tr>
											<td>销售日期</td>
											<td><s:textfield name="curCardinfo.salecarddate" id="salecarddate" theme="simple" style="width:120" readonly="true" /></td>
											</tr>
											<tr>
											<td>截止有效期</td>
											<td><s:textfield name="curCardinfo.cutoffdate" id="cutoffdate" theme="simple" style="width:120" readonly="true" /></td>
											</tr>
											
										</table>
									</div>		
									<div title="疗程列表" style="font-size:12px;" style="width:100%; height:98%; ">
										<div id="commoninfodivProAccount" style="margin:0; padding:0"></div>
										
									</div>
									<div title="异动历史-子卡" style="font-size:12px;" style="width:100%; height:98%; ">
										<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
										<tr>
										<td valign="top"><div id="commoninfodivCardchange" style="margin:0; padding:0"></div></td>
										<td valign="top"><div id="commoninfodivCardson" style="margin:0; padding:0"></div></td>
										</tr>
										</table>
										
									</div>	
								
									<div title="账户-交易历史" style="font-size:12px;" style="width:100%; height:98%; ">
										<div id="commoninfodivAccountchange" style="margin:0; padding:0"></div>
									</div>	
									<div title="赠送历史" style="font-size:12px;" style="width:100%; height:98%; ">
										<div id="commoninfodivSendpointcard" style="margin:0; padding:0"></div>
									</div>
									
									<%--<div title="年卡消费历史" style="font-size:12px;" style="width:100%; height:98%; ">
										<div id="commoninfodivYearCardInfo style="margin:0; padding:0"></div>
									</div>--%>
								  </div>
								</td>
								</tr>
								
								</table>
								
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