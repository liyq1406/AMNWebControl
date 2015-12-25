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
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC004/bc004.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
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
		#dv_scroll_bar .Scrollbar-Handle{position:absolute;left:-7px;bottom:10;width:13px;height:29px;overflow:hidden;background:url('<%=ContextPath%>/common/ligerui/images/srcoll.gif') no-repeat;cursor:pointer;}
		#dv_scroll_text {position:absolute;}
    </style>
</head>
<body style="padding:6px; overflow:hidden;">
<div class="l-loading" style="display:block" id="pageloading"></div> 
  	<table width="100%" border="0" cellspacing="1" cellpadding="0" align="left" style="height:100%;font-size:12px;">
  	<tr>
  		<td algin="left" valign="top" width="298px" >
  		 	<div class="scr_con">
		    <div id="dv_scroll">
  		   		<div id="dv_scroll_text" class="Scroller-Container">
					<ul id="companyTree"></ul>
				</div> 
			</div> 
				   			   
			<div id="dv_scroll_bar" width="230px" >
				<div id="dv_scroll_track" class="Scrollbar-Track">
					<div class="Scrollbar-Handle"></div>
				</div>
			</div>
			</div>
  		</td>
  		<td valign="top"  align="left" width="320px">

  			 <div id="commoninfodivsecond" style="margin:0; padding:0"></div>
  
  		</td>
  		<td valign="top" align="left"> 
  		 
  		  	<form name="curCompanyFrom" method="post"  id="curCompanyFrom">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:40px;" >
				
					<tr >
					<td  >
					<div  style="width:95%; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
						<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:40px;" >
							
							<tr>
								<td>&nbsp;&nbsp;用户编号:</td>
								<td><s:textfield name="curUserInfo.userno" id="userno" theme="simple"  readonly="true" style="width:110;"  validate="{required:true,minlength:5,maxlength:10,notnull:false}" onchange="validateUserno(this)"/></td>
								<td>&nbsp;&nbsp;用户密码:</td>
								<td><s:password name="curUserInfo.userpwd" id="userpwd" theme="simple"  style="width:110;"  validate="{required:true,minlength:1,maxlength:10,notnull:false}"/></td>
							</tr>
							
							<tr>	
								<td>&nbsp;&nbsp;用户角色:</td>
								<td>  <select  id="userrole" name="curCompanyinfo.userrole" style="width:110;"> </select></td>
							
								<td>&nbsp;&nbsp;归属门店:</td>
								<td><s:textfield name="curUserInfo.fromcompno" id="fromcompno" theme="simple"  readonly="true" style="width:110;" readonly="true"/></td>
							</tr>
							
							<tr>
								<td>&nbsp;&nbsp;员工编号:</td>
								<td><s:textfield name="curUserInfo.strEmpId" id="strEmpId" theme="simple"  readonly="true" style="width:110;" readonly="true" onchange="validateEmpId(this)"/></td>
								<td>&nbsp;&nbsp;内部编号:</td>
								<td><s:textfield name="curUserInfo.frominnerno" id="frominnerno" theme="simple"  readonly="true" style="width:110;" readonly="true"/>
						
								<s:hidden name="curUserInfo.datefrom" id="datefrom" theme="simple"></s:hidden>
								<s:hidden name="curUserInfo.dateto" id="dateto" theme="simple"></s:hidden>
								</td>
							</tr>
							<tr>	
								<td>&nbsp;&nbsp;呼叫组号:</td>
								<td>  <input type="text" id="callcenterqueue" name="curCompanyinfo.callcenterqueue" maxlength="20"/> </td>
							
								<td>&nbsp;&nbsp;呼叫席别:</td>
								<td><input type="text" name="curUserInfo.callcenterinterface" id="callcenterinterface" maxlength="20" style="width:110;" /></td>
							</tr>
						</table>
					</div>
					</td>
					</tr>
					
					<tr><td style="border-bottom:1px #000000 dashed" ></td></tr>
					<tr>
					<td>
					 <div id="BC004Tab" style="width:100%; height:95%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
						<div title="角色功能编辑" style="font-size:12px;" style="width:100%;">
							<div id="commoninfodiveight" style="margin:0; padding:0"></div>
						</div>
						
						<div id="disableFunction" title="禁用功能" style="font-size:12px;" style="width:100%; height:98%; ">
							<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:40px;" >
							<tr>
								<td valign="top"><div id="commoninfodivforth" style="margin:0; padding:0"></div></td>
								<td valign="top"><div id="commoninfodivfive" style="margin:0; padding:0"></div></td>					
								<td valign="top"><div id="commoninfodivsix" style="margin:0; padding:0"></div></td>
								<td valign="top"><div id="commoninfodivseven" style="margin:0; padding:0"></div></td>
							</tr>
							</table>
						</div>
					</div>
					</td>
					</tr>
				</table>
  		</form>
		</td>
  	</tr>
  	</table>

	
  <div style="display:none;">
  <!-- g data total ttt -->
</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
	</script>