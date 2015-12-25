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
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC006/cc006.js"></script>

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

  	 <div id="cc006layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
  	 	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
	  			 	<div position="center"   id="designPanel"  style="width:100%;" title="会员资料列表"> 
					           <div id="searchbar">
			    				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
								<tr>
									<td>会员编号：<input id="searchMemberNoKey" type="text" size="10"/> &nbsp;
									&nbsp;会员姓名：<input id="searchMemberNameKey" type="text" size="10"/> &nbsp;
									&nbsp;手机号码：<input id="searchMemberPhoneKey" type="text" size="10"/> &nbsp;
									&nbsp;证件号码：<input id="searchMemberPCIDKey" type="text" size="14"/> &nbsp;</td>
									<td><div id="searchMember"></div></td>
								</tr>
								</table>
								</div>
								<div id="commoninfodivsecond" style="margin:0; padding:0"></div>
				        </div>
				        <div position="right"   id="lsPanel" title="会员资料基础信息"> 
				        	<form name="detailForm" method="post"  id="detailForm">
				     		  <table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;" >
								<tr>
									<td width="30%">&nbsp;&nbsp;归属门店</td>
									<td width="70%"><s:textfield name="curMemberinfo.id.membervesting" id="membervesting" theme="simple"  readonly="true" style="width:120;" validate="{required:true,maxlength:10,notnull:false}"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;会员编号</td>
									<td><s:textfield name="curMemberinfo.id.memberno" id="memberno" theme="simple" style="width:120" readonly="true" validate="{maxlength:40,notnull:false}"/></td>
								</tr>
							
								<tr>
									<td>&nbsp;&nbsp;会员姓名</td>
									<td><s:textfield name="curMemberinfo.membername" id="membername" theme="simple" style="width:120" readonly="true" validate="{maxlength:20,notnull:false}"/>
									<input type="text" name="lbmembername" id="lbmembername"  style="width:120"  readonly="true"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;会员性别</td>
									<td>
									<s:radio label="会员性别" id="membersex" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curMemberinfo.membersex" list="#{0:\"女\",1:\"男\"}"
										listKey="key" theme="simple" listValue="value"/></td>
								</tr>
								
								<tr>
									<td>&nbsp;&nbsp;家庭电话</td>
									<td><s:textfield name="curMemberinfo.membertphone" id="membertphone" theme="simple" style="width:120"  validate="{maxlength:10,notnull:false}"/></td>
								</tr>
								
								<tr>
									<td>&nbsp;&nbsp;移动电话</td>
									<td ><s:textfield name="curMemberinfo.membermphone" id="membermphone" theme="simple" style="width:120" readonly="true" validate="{maxlength:11,notnull:false}"/>
										<input type="text" name="lbmembermphone" id="lbmembermphone"  style="width:120"  readonly="true"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;家庭地址</td>
									<td ><s:textarea name="curMemberinfo.memberaddress" id="memberaddress" theme="simple" rows="3" cols="15" validate="{maxlength:60,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;电子邮件</td>
									<td><s:textfield name="curMemberinfo.memberemail" id="memberemail" theme="simple" style="width:140"  validate="{maxlength:30,notnull:false}"/>&nbsp;&nbsp;</td>
								</tr>
								
									<tr>
									<td>&nbsp;&nbsp;出生日期</td>
									<td ><s:textfield name="curMemberinfo.memberbirthday" id="memberbirthday" theme="simple" style="width:140" readonly="true"/>
											<s:textfield name="lbmemberbirthday" id="lbmemberbirthday" theme="simple" style="width:140" readonly="true" />
									
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;职业</td>
									<td ><s:textfield name="curMemberinfo.memberjob" id="memberjob" theme="simple" style="width:140" validate="{maxlength:8,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;证件号码</td>
									<td ><s:textfield name="curMemberinfo.memberpaperworkno" id="memberpaperworkno" theme="simple" style="width:140" validate="{maxlength:20,notnull:false}"/>
										<s:textfield name="lbmemberpaperworkno" id="lbmemberpaperworkno" theme="simple" style="width:140"  readonly="true"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;QQ号码</td>
									<td ><s:textfield name="curMemberinfo.memberqqno" id="memberqqno" theme="simple" style="width:140" validate="{maxlength:20,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;会员类别</td>
									<td> <input type="text" id="membertype" name="membertype" />
									<s:hidden id="curmembertype" name="curMemberinfo.membertype" theme="simple"></s:hidden>
									</td>
								</tr>
								
								<tr>
									<td>&nbsp;&nbsp;发送短息</td>
									<td><s:radio label="会员性别" id="isSendMsg" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curMemberinfo.isSendMsg" list="#{0:\"发送\",1:\"不发送\"}"
										listKey="key" theme="simple" listValue="value"/>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center"><div id="editMember"></div></td>
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