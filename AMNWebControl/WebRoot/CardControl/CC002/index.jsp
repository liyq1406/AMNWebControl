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
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC002/cc002.js"></script>

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

  	 <div id="cc002layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
		<div position="center"   id="designPanel"  style="width:100%;" > 
					  
	    <form name="cardAppForm" method="post"  id="cardAppForm">
		<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
		<tr>
			<td width="360" valign="top">
			<div id="searchbar">
			    门店：<input id="txtSearchCompIdKey" type="text" size="6"/> &nbsp;
			    单号：<input id="txtSearchBillIdKey" type="text"  size="12"/>&nbsp;
			    <s:select label="是否启用批次" id="txtSearchstateKey" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="txtSearchstateKey" list="#{0:\"申请中\",1:\"总部同意\",2:\"已领用\",3:\"所有\"}"
										listKey="key" theme="simple" listValue="value" />
			</div>
			
			<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
			</td>
			<td  valign="top">
			<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
				<td valign="top"><div id="commoninfodivdetial" style="margin:0; padding:0"></div></td>
				</tr>
				<tr height="250">
					<td valign="top">
						<div  style="width:300; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
							<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
								<tr>
									<td width="70">&nbsp;&nbsp;<font color="blue">门店编号</font></td>
									<td ><s:textfield name="curMcardappInfo.id.cappcompid" id="cappcompid" theme="simple"  readonly="true" style="width:80;" />
									-
									<s:textfield name="curMcardappInfo.bcappcompidText" id="bcappcompidText" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;<font color="blue">申请单号</font></td>
									<td ><s:textfield name="curMcardappInfo.id.cappcompbillid" id="cappcompbillid" theme="simple"  readonly="true" style="width:140;" />
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;<font color="blue">申请员工</font></td>
									<td ><s:textfield name="curMcardappInfo.cappempid" id="cappempid" theme="simple"  readonly="true" style="width:80;" onchange="validateAppempId(this)"/>
									-
									<s:textfield name="curMcardappInfo.cappempText" id="cappempText" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;申请日期</td>
									<td ><s:textfield name="curMcardappInfo.cappdate" id="cappdate" theme="simple"  readonly="true" style="width:80;" />
									-
									<s:textfield name="curMcardappInfo.capptime" id="capptime" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;操作员工</td>
									<td ><s:textfield name="curMcardappInfo.cappopationper" id="cappopationper" theme="simple"  readonly="true" style="width:80;" />
									-
									<s:textfield name="curMcardappInfo.cappopationdate" id="cappopationdate" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;审核员工</td>
									<td ><s:textfield name="curMcardappInfo.cappconfirmper" id="cappconfirmper" theme="simple"  readonly="true" style="width:80;" />
									-
									<s:textfield name="curMcardappInfo.cappconfirmdate" id="cappconfirmdate" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;申请流程</td>
									<td >
									<s:radio label="申请流程" id="cappbillflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curMcardappInfo.cappbillflag" list="#{0:\"申请中\",1:\"总部同意\",2:\"已领用\"}"
										listKey="key" theme="simple" listValue="value" disabled="true"  />
									</td>
								</tr>
								<tr> <td colspan="2" align="center"><div id="confirmCurRootInfo"></div></td></tr>
							</table>
						</div>
					</td>
				</tr>
				
			</table>
			</td>
			</tr>
			</table>
			</form>
	    </div>
			<div position="right"   id="cardTypeCount" title="门店未销卡分析">   
				<div id="commoninfodivdetial_anyls" style="margin:0; padding:0"></div>  
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