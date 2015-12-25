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
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC009/bc009.js"></script>

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

  	 <div id="bc009layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  			 	<div position="center"   id="designPanel"  style="width:100%;" title="供应商列表"> 
					           <div id="searchbar">
			    				关键字：<input id="txtSearchKey" type="text" />
			    				<input id="btnOK" type="button" value="查询供应商信息" onclick="f_searchSupperInfo()" />
								</div>
								<div id="commoninfodivsecond" style="margin:0; padding:0"></div>
				        </div>
				        <div position="right"   id="lsPanel" title="供应商基础信息"> 
				        	<form name="detailForm" method="post"  id="detailForm">
				     		  <table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;" >
								<tr>
									<td width="30%">&nbsp;&nbsp;供应商ID</td>
									<td width="70%"><s:textfield name="curSupplier.supplierid" id="supplierid" theme="simple"  readonly="true" style="width:120;" validate="{required:true,maxlength:10,notnull:false}"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;供应商名称</td>
									<td><s:textfield name="curSupplier.suppliername" id="suppliername" theme="simple" style="width:120" validate="{maxlength:40,notnull:false}"/></td>
								</tr>
							
								<tr>
									<td>&nbsp;&nbsp;供应商简称</td>
									<td><s:textfield name="curSupplier.suppliersname" id="suppliersname" theme="simple" style="width:120" validate="{maxlength:20,notnull:false}"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;电话</td>
									<td><s:textfield name="curSupplier.supplierphone" id="supplierphone" theme="simple" style="width:120" validate="{maxlength:12,notnull:false}"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;传真</td>
									<td ><s:textfield name="curSupplier.supplierfex" id="supplierfex" theme="simple" style="width:120" validate="{maxlength:15,notnull:false}"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;电子邮件</td>
									<td><s:textfield name="curSupplier.supplieremail" id="supplieremail" theme="simple" style="width:120"  validate="{maxlength:20,notnull:false}"/>&nbsp;&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;电子主页</td>
									<td ><s:textfield name="curSupplier.supplierurl" id="supplierurl" theme="simple" style="width:140" validate="{maxlength:30,notnull:false}"/>
									</td>
								</tr>
									<tr>
									<td>&nbsp;&nbsp;发票地址</td>
									<td ><s:textfield name="curSupplier.supplieraddress" id="supplieraddress" theme="simple" style="width:140" validate="{maxlength:40,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;邮政编码</td>
									<td ><s:textfield name="curSupplier.supplierpos" id="supplierpos" theme="simple" style="width:140" validate="{maxlength:8,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;备注</td>
									<td ><s:textfield name="curSupplier.supplierremark" id="supplierremark" theme="simple" style="width:140" validate="{maxlength:20,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;联系人姓名</td>
									<td ><s:textfield name="curSupplier.miantoucher" id="miantoucher" theme="simple" style="width:140" validate="{maxlength:20,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;手机号码</td>
									<td ><s:textfield name="curSupplier.suppliermobilephone" id="suppliermobilephone" theme="simple" style="width:140" validate="{maxlength:12,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;供应商密码</td>
									<td ><s:password name="curSupplier.supplierpassword" id="supplierpassword" theme="simple" style="width:140" validate="{maxlength:15,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;是否合作</td>
									<td>
									<s:radio label="是否启用批次" id="supplierstate" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSupplier.supplierstate" list="#{0:\"没有合作\",1:\"正在合作\"}"
										listKey="key" theme="simple" listValue="value"/></td>
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