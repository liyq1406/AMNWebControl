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
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC003/cc003.js"></script>
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
		
    </style>

</head>
<body onLoad="initMap()">
	<div class="l-loading" style="display:block;height:100%;" id="pageloading"></div> 
	 <div id="cc001layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
	    <div position="center"   id="designPanel"  style="width:100%;"> 
	    	<form name="cardCallotForm" method="post"  id="cardCallotForm">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
				<td width="552" valign="top">
						<div id="searchbar" style="width:100%;">
						<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
					
						<tr>
						<td>申请门店：<input id="txtSearchCompIdKey" type="text" size="6"/> &nbsp;</td>
						<td>申请单号：<input id="txtSearchBillIdKey" type="text"  size="12"/>&nbsp;</td>
						<td>配发日期：</td>
						<td><input id="txtSearchDateKey" type="text"  size="12"/></td>
						</tr>
						</table>
						</div>
			
						<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
				</td>
				<td  valign="top">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
					<tr height="300">
							<td valign="top">
							<div  style="width:480; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
										<tr>
											<td width="100">&nbsp;&nbsp;<font color="blue">配发门店-单号</font></td>
											<td ><s:textfield name="curMcardallotment.id.callotcompid" id="callotcompid" theme="simple"  readonly="true" style="width:140;"/>
											&nbsp;&nbsp;<s:textfield name="curMcardallotment.id.callotbillid" id="callotbillid" theme="simple"  readonly="true" style="width:140;"/></td>
										</tr>
										<tr>
											<td >&nbsp;&nbsp;<font color="blue">申请门店</font></td>
											<td ><s:textfield name="curMcardallotment.cappcompid" id="cappcompid" theme="simple"  readonly="true" style="width:140;" validate="{required:true,maxlength:20,notnull:false}" onchange="validateCappcompid(this)"/>
											&nbsp;&nbsp;<s:textfield name="curMcardallotment.cappcompidText" id="cappcompidText" theme="simple"  readonly="true" style="width:140;"/></td>
										</tr>
										<tr>
											<td >&nbsp;&nbsp;<font color="blue">申请单号</font></td>
											<td ><s:textfield name="curMcardallotment.cappbillid" id="cappbillid" theme="simple"  readonly="true" style="width:140;" validate="{required:true,maxlength:20,notnull:false}" onchange="validateCappbillid(this)"/>
											&nbsp;&nbsp;<a onclick="showConfirmBill()"><img src="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/images/tree/tree-leaf.gif" /></a>
											</td>
										
										</tr>
										<tr>
											<td >&nbsp;&nbsp;<font color="blue">配发仓库</font></td>
											<td ><s:textfield name="curMcardallotment.callotwareid" id="callotwareid" theme="simple"  readonly="true" style="width:140;" validate="{required:true,maxlength:20,notnull:false}"/></td>
											
										</tr>
										<tr>	
											<td >&nbsp;&nbsp;配发日期</td>
											<td ><s:textfield name="curMcardallotment.callotdate" id="callotdate" theme="simple"  readonly="true" style="width:140;" validate="{required:true,maxlength:20,notnull:false}"/>
											&nbsp;&nbsp;<s:textfield name="curMcardallotment.callottime" id="callottime" theme="simple"  readonly="true" style="width:100;" validate="{maxlength:10,notnull:false}"/></td>
										</tr>
										<tr>
											<td >&nbsp;&nbsp;配发人员</td>
											<td ><s:textfield name="curMcardallotment.callotempid" id="callotempid" theme="simple"  readonly="true" style="width:140;" validate="{required:true,maxlength:20,notnull:false}" onchange="validateCallotempid(this)"/>
												&nbsp;&nbsp;<s:textfield name="curMcardallotment.callotempText" id="callotempText" theme="simple"  readonly="true" style="width:100;" /></td>
								
										</tr>
										<tr>
											<td >&nbsp;&nbsp;领取人员</td>
											<td ><s:textfield name="curMcardallotment.recevieempid" id="recevieempid" theme="simple"  readonly="true" style="width:140;" validate="{required:true,maxlength:20,notnull:false}" onchange="validateRecevieempid(this)"/>
												&nbsp;&nbsp;<s:textfield name="curMcardallotment.recevieempText" id="recevieempText" theme="simple"  readonly="true" style="width:100;" /></td>
								
										</tr>
										
										<tr>
										<td>
										&nbsp;&nbsp;是否结账</td>	<td ><s:radio label="是否启用批次" id="checkoutflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curMcardallotment.checkoutflag" list="#{0:\"没有结帐\",1:\"已经结帐\"}"
										listKey="key" theme="simple" listValue="value"/> 
										</td>
										</tr>
										<tr>
											<td >&nbsp;&nbsp;结账日期</td>
											<td ><s:textfield name="curMcardallotment.checkoutdate" id="checkoutdate" theme="simple"  readonly="true" style="width:120;" validate="{maxlength:40,notnull:false}"/></td>
										</tr>
										
										<tr>	
											<td >&nbsp;&nbsp;操作人员</td>
											<td colspan="3"><s:textfield name="curMcardallotment.callotopationempid" id="callotopationempid" theme="simple"  readonly="true" style="width:140;" validate="{notnull:false}"/>
											&nbsp;&nbsp;<s:textfield name="curMcardallotment.callotopationdate" id="callotopationdate" theme="simple"  readonly="true" style="width:100;" validate="{notnull:false}"/></td>
										</tr>
									</table>
									<s:hidden theme="simple" id="cardIdLength" name="cardIdLength"></s:hidden>
									<s:hidden theme="simple" id="numberOfCardFilter" name="numberOfCardFilter"></s:hidden>
								</div>
						
							</td>
					</tr>
				
					<tr >
							<td valign="top">
							<div id="commoninfodivdetial" style="margin:0; padding:0"></div>
							</td>
					</tr>
					</table>
					
						
				
				</td>
				</tr>
				</table>
			</form>
	    </div>
	    <div position="right"   id="designPane2"  style="width:100%;"  title="卡来源分析统计"> 
	    		<div id="CC003Tab" style="width:335; height:100%; margin:10px; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
				     	<div title="卡库存查询" style="width:330;height:100% ;font-size:12px;" >
				     		<div id="commoninfodivcardstock" style="margin:0; padding:0"></div>
				       	</div>
				       	<div title="卡领用配发" style="width:330;height:100%; ;font-size:12px;">
				       		 <div id="searchbar" style="width:100%;">
							<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
							<tr>
								<td>门店：<input id="txtAllotCompIdKey" type="text" size="6"/> &nbsp;</td>
								<td>类型：</td><td><input id="txtAllotCardTypeKey" type="text"  size="12"/>&nbsp;</td>
							</tr>
							</table>
							</div>
				       		 <div id="commoninfodivcardallto" style="margin:0; padding:0"></div>
				       	</div>
				   
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
