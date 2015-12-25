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

    <script src="<%=ContextPath%>/common/ligerui/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
    <script src="<%=ContextPath%>/common/ligerui/ligerUI/js/plugins/ligerMenuBar.js" type="text/javascript"></script>
    <script src="<%=ContextPath%>/common/ligerui/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC001/cc001.js"></script>
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
		#menu1,.l-menu-shadow{top:30px; left:50px;}
        #menu1{  width:200px;}
    </style>

</head>
<body onLoad="initMap()">
	<div class="l-loading" style="display:block;height:100%;" id="pageloading"></div> 
	 <div id="cc001layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
	    <div position="center"   id="designPanel"  style="width:100%;"> 
	    	<form name="cardInsertForm" method="post"  id="cardInsertForm">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
				<td  valign="top"><div id="commoninfodivmaster" style="margin:0; padding:0"></div></td>
				<td  valign="top" width="615">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
					<tr height="300">
							<td valign="top">
							<div  style="width:615; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
										<tr>
											<td width="10%">&nbsp;&nbsp;<font color="blue">门店编号</font></td>
											<td ><s:textfield name="curMcardnoinsert.id.cinsertcompid" id="cinsertcompid" theme="simple"  readonly="true" style="width:140;" />
											&nbsp;&nbsp;<s:textfield name="curMcardnoinsert.bcinsertcompName" id="bcinsertcompName" theme="simple"  readonly="true" style="width:160;" /></td>
										</tr>
										<tr>		
											<td >&nbsp;&nbsp;<font color="blue">单据编号</font></td>
											<td ><s:textfield name="curMcardnoinsert.id.cinsertbillid" id="cinsertbillid" theme="simple"  readonly="true" style="width:140;"/></td>
										</tr>
										<tr>
											<td width="10%">&nbsp;&nbsp;<font color="blue">入库仓库</font></td>
											<td ><s:textfield name="curMcardnoinsert.cinsertware" id="cinsertware" theme="simple"  readonly="true" style="width:140;" validate="{required:true,maxlength:10,notnull:false}"/></td>
											
										</tr>
										<tr>	
											<td >&nbsp;&nbsp;入库日期</td>
											<td colspan="3"><s:textfield name="curMcardnoinsert.cinsertdate" id="cinsertdate" theme="simple"  readonly="true" style="width:140;" validate="{required:true,maxlength:10,notnull:false}"/>
											&nbsp;&nbsp;<s:textfield name="curMcardnoinsert.cinserttime" id="cinserttime" theme="simple"  readonly="true" style="width:100;" validate="{maxlength:10,notnull:false}"/></td>
										</tr>
										<tr>
											<td width="10%">&nbsp;&nbsp;验货人员</td>
											<td ><s:textfield name="curMcardnoinsert.cinsertper" id="cinsertper" theme="simple"  readonly="true" style="width:140;" validate="{required:true,maxlength:20,notnull:false}" onchange="validateInserper(this)"/>
												&nbsp;&nbsp;<s:textfield name="curMcardnoinsert.cinsertperName" id="cinsertperName" theme="simple"  readonly="true" style="width:100;" /></td>
									
											
										</tr>
										<tr>
											<td width="10%">&nbsp;&nbsp;制卡公司</td>
											<td ><s:textfield name="curMcardnoinsert.createcompname" id="createcompname" theme="simple"  readonly="true" style="width:200;" validate="{required:true,maxlength:20,notnull:false}"/></td>
										</tr>
										<tr>
										<td colspan="2">
										&nbsp;&nbsp;是否结账	<s:radio label="是否启用批次" id="checkoutflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curMcardnoinsert.checkoutflag" list="#{0:\"没有结帐\",1:\"已经结帐\"}"
										listKey="key" theme="simple" listValue="value"/> 
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
										&nbsp;&nbsp;是否开票<s:radio label="是否启用批次" id="billflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curMcardnoinsert.billflag" list="#{0:\"不需开票\",1:\"已经开票\"}"
										listKey="key" theme="simple" listValue="value" />
											</td>
										</tr>
										<tr>
											<td width="10%">&nbsp;&nbsp;发票编号</td>
											<td ><s:textfield name="curMcardnoinsert.billno" id="billno" theme="simple"  readonly="true" style="width:200;" validate="{maxlength:40,notnull:false}"/></td>
										</tr>
										<tr>
											<td width="10%">&nbsp;&nbsp;结账备注</td>
											<td ><s:textfield name="curMcardnoinsert.checkoutmark" id="checkoutmark" theme="simple"  readonly="true" style="width:350;" validate="{maxlength:100,notnull:false}"/></td>
										</tr>
										<tr>	
											<td >&nbsp;&nbsp;审核人员</td>
											<td colspan="3"><s:textfield name="curMcardnoinsert.optionconfrimper" id="optionconfrimper" theme="simple"  readonly="true" style="width:140;" validate="{notnull:false}"/>
											&nbsp;&nbsp;<s:textfield name="curMcardnoinsert.optionconfrimdate" id="optionconfrimdate" theme="simple"  readonly="true" style="width:100;" validate="{notnull:false}"/></td>
										</tr>
									</table>
									<s:hidden theme="simple" id="cardIdLength" name="cardIdLength"></s:hidden>
									<s:hidden theme="simple" id="numberOfCardFilter" name="numberOfCardFilter"></s:hidden>
								</div>
						
							</td>
					</tr>
					<tr >
							<td valign="top">
								<div  style="width:615; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
											<tr>
												<td width="10%">&nbsp;&nbsp;<font color="blue">卡类型</font></td>
												<td ><select name="dcardType" id="dcardType" style="width:120;" ></select></td>
												<td width="10%">&nbsp;&nbsp;<font color="blue">卡号段</font></td>
												<td >
												<s:textfield name="dcardFromNo" id="dcardFromNo" theme="simple" readonly="true" style="width:120;" onchange="checkExitsFrom(this);checkRangeExits()" />
												-
												<s:textfield name="dcardToNo" id="dcardToNo" theme="simple"  readonly="true" style="width:120;"  onchange="checkExitsEnd(this);checkRangeExits()"/>
												<s:textfield name="dcardNum" id="dcardNum" theme="simple"  readonly="true" style="width:50;" />
												</td>
											</tr>
											<tr>
												<td width="10%">&nbsp;&nbsp;单价-总额</td>
												<td ><s:textfield name="dcardPrice" id="dcardPrice" theme="simple"  readonly="true" style="width:50;" onchange="validatePrice(this);companyPrice()" />
												-<s:textfield name="dcardAmt" id="dcardAmt" theme="simple"  readonly="true" style="width:60;" /></td>
												<td colspan="2"><div id="editCurDetailInfo"></div></td>
											</tr>
									</table>
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
	    <div position="right"   id="designPane2"  style="width:100%;"  title="单据审核拍照预览"> 
	         <div style="text-align:center;" >
					<object classid="clsid:454C18E2-8B7D-43C6-8C17-B1825B49D7DE" id="captrue"  width="400" height="300" ></object>
   			 </div>
			<object ID='WebBrowser' WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></object> 
			<object ID='WebBrowser' WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></object> 
            <form action="/AMNWebControl/uploadServlet" method="post" enctype="Multipart/form-data" name="form1">
					<div id="toptoolbar"></div> 
			</form> 
			<div>
				<img id="billImage" width="400" height="300" src=""/>
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