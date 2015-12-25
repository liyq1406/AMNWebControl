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
	<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC012/ac012.js"></script>

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

  	 <div id="ac012layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  			 		<div position="left"   id="lsPanel" title="连锁结构"> 
		  			 		<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			 		 	<ul id="companyTree" style="margin-top:3px;">
		  			 		 	</ul>
		  			 		 </div>
	  			 		</div>
				        <div position="center"   id="designPanel"  style="width:100%;" title="流程申请列表"> 
					         
								<div id="commoninfodivsecond" style="margin:0; padding:0"></div>
				        </div>
				        <div position="right"   id="lsPanel" title="流程申请信息"> 
				        <form name="detailForm" method="post"  id="detailForm">
				     		  <table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;" >
								<tr>
									<td width="35%">&nbsp;&nbsp;操作门店</td>
									<td width="65%"><s:textfield name="curStoreflowinfo.id.compid" id="compid" theme="simple"  readonly="true" style="width:120;" readonly="true"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;操作单号</td>
									<td><s:textfield name="curStoreflowinfo.id.billid" id="billid" theme="simple" style="width:120" validate="{maxlength:40,notnull:false}" readonly="true"/></td>
								</tr>
							
								<tr>
									<td>&nbsp;&nbsp;申请类别</td>
									<td>
									<s:select id="appflowtype" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120"
																			name="curStoreflowinfo.appflowtype" list="#{1:\"换卡\",2:\"补卡\",3:\"退卡\",4:\"开卡额度\",5:\"充值额度\",6:\"修改会员资料\",7:\"退疗程\",8:\"项目变价\",9:\"产品变价\"}"
																			listKey="key" theme="simple" listValue="value"/>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;项目/卖品/卡号</td>
									<td><s:textfield name="curStoreflowinfo.appflowcode" id="appflowcode" theme="simple" style="width:120" validate="{required:true,maxlength:20,notnull:false}" onchange="validateItem(this)"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;名称</td>
									<td><s:textfield name="curStoreflowinfo.appflowname" id="appflowname" theme="simple" style="width:120" readonly="true" validate="{maxlength:12,notnull:false}"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;申请门店</td>
									<td ><s:textfield name="curStoreflowinfo.appflowstore" id="appflowstore" theme="simple" style="width:120" validate="{required:true,maxlength:15,notnull:false}" onchange="validateCompId(this)"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;门店名称</td>
									<td ><s:textfield name="curStoreflowinfo.appflowstorename" id="appflowstorename" theme="simple" style="width:120"  readonly="true" validate="{maxlength:15,notnull:false}"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;申请内容</td>
									<td><s:textfield name="curStoreflowinfo.appflowvalue" id="appflowvalue" theme="simple" style="width:120"   validate="{maxlength:20,notnull:false}"/>&nbsp;&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;开始日期</td>
									<td ><s:textfield name="curStoreflowinfo.startdate" id="startdate" theme="simple" style="width:120"  validate="{maxlength:30,notnull:false}"/>
									</td>
								</tr>
									<tr>
									<td>&nbsp;&nbsp;截止日期</td>
									<td ><s:textfield name="curStoreflowinfo.enddate" id="enddate" theme="simple" style="width:120"   validate="{maxlength:40,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;申请原因</td>
									<td ><s:textarea name="curStoreflowinfo.appflowreason" id="appflowreason" theme="simple" rows="3"  cols="13"/>
									</td>
								</tr>
								
							
								<tr>
									<td>&nbsp;&nbsp;是否审核</td>
									<td>
									<s:select id="appflowstate" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120"
																			name="curStoreflowinfo.appflowstate" list="#{0:\"未审核\",1:\"专员审核\",2:\"经理审核\"}"
																			listKey="key" theme="simple" listValue="value" disabled="true"/>
								</tr>
								<tr>
									<td colspan="2" align="center">
									 	<div id="confirmCurRootInfo"></div>
									 </td>
									
								</tr>
								</table>
								<s:hidden theme="simple" name="curStoreflowinfo.invalid" id="invalid"></s:hidden>
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