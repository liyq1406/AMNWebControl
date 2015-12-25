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
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC013/ac013.js"></script>

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

  	 <div id="ac013layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  			 	
				        <div position="center"   id="designPanel"  style="width:100%;"> 
					         	<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
			
								<tr>
							
									 <td width="540"><div ><%@include file="/common/search.frag"%></div></td>
									 <td width="40">日期</td>
									 <td width="90">
											<input id="strSearchDate" type="text" size="10" />
									 </td>
									  <td width="110">卡号
											<input id="strMemberNo" type="text" style="width:70" />
									 </td>
									 <td width="110">工号
											<input id="strStaffNo" type="text" style="width:70" />
									 </td>
									 <td width="100">状态
										<select id="iSearchState" type="text" style="width:60">
										<option value="3">全部</option>
										<option value="0">未预约</option>
										<option value="1">已预约</option>
										</select>
									 </td>				
									 <td ><div id="searchButton"></div></td>
									
								</tr>
								<tr><td style="border-bottom:1px #000000 dashed" colspan="6"></td></tr>
					
								</table>	
								<div id="commoninfodivsecond" style="margin:0; padding:0"></div>
				        </div>
				        <div position="right"   id="lsPanel" title="门店预约员工明细"> 
				        <form name="detailForm" method="post"  id="detailForm">
				     		  <table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;" >
								<tr>
									<td >&nbsp;&nbsp;<font color="red">消费门店 </font>&nbsp;&nbsp;<s:textfield name="curStoreflowinfo.id.cscompid" id="cscompid" theme="simple"  readonly="true" style="width:120;" readonly="true"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;<font color="red">消费单号 </font>&nbsp;&nbsp;<s:textfield name="curMconsumeinfo.id.csbillid" id="csbillid" theme="simple" style="width:120"  readonly="true"/></td>
								</tr>
							
							
								<tr>
									<td>&nbsp;&nbsp;消费日期&nbsp;&nbsp;&nbsp;<s:textfield name="curMconsumeinfo.financedate" id="financedate" theme="simple" style="width:120" readonly="true"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;会员卡号&nbsp;&nbsp;&nbsp;<s:textfield name="curMconsumeinfo.cscardno" id="cscardno" theme="simple" style="width:120" readonly="true"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;会员姓名&nbsp;&nbsp;&nbsp;<s:textfield name="curMconsumeinfo.csname" id="csname" theme="simple"style="width:120" readonly="true"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;手机号码&nbsp;&nbsp;&nbsp;<s:textfield name="curMconsumeinfo.strMemberPhone" id="strMemberPhone" theme="simple" style="width:120" readonly="true"/>
										<s:hidden name="curMconsumeinfo.reserveStaffinfo" id="reserveStaffinfo" theme="simple" />
									</td>
								</tr>
								<tr>
									<td><div id="commoninfodivdetial" style="margin:0; padding:0"></div></td>
								</tr>
								<tr>
									<td align="center">
									 	<div id="confirmCurRootInfo"></div>
									 </td>
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