<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
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
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC021/cc021.js"></script>

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

  	 <div id="dataLayout" style="width:100%; margin:0 auto; margin-top:4px; "> 
  	 	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
	  			<ul id="companyTree" style="margin-top:3px;"></ul>
		  	</div>
	  	</div>
	  	 <div position="center"   id="designPanel"  style="width:100%;" > 
		 	<form name="detailForm" method="post"  id="detailForm">
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
				<tr>
				<td valign="top" >	
					<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
						<tr>
							<td width="500" valign="top">
							<div  style="width:100%;height:152px;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
							<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:30px" >
							<tr>
							<td colspan="4" align="right"><div id="saveBtn" style="margin-right: 10px;margin-top: 5px;"></div></td>
							</tr>
							<tr>
							<tr>
							<td>退款单号</td>
							<td ><input type="text" name="mconsumeinfo.csbillid" id="csbillid"  readonly="true" style="width:140;" validate="{required:true,minlength:1,maxlength:18,notnull:false}"/></td>
							<td>金额</td>
							<td> 
								<input type="text"  name="cspayamt" id="cspayamt"  style="width:140;" onchange="validatePrice(this);" />
							</td>
							</tr>
							<tr>
							<td>消费类型</td>
							<td>
								<select id="csinfotype" name="csinfotype" style="width:140">
									<option value="1">项目</option>
									<option value="2">产品</option>
								</select>
							</td>
							<td>绑定工号</td>
							<td>
								<select id="billinsertype" name="billinsertype" style="width:140">
									<option value="300">美发部</option>
									<option value="400">美容部</option>
								</select>
							</td>
							</tr>
							</table>
							</div>
							</td>	
							<td valign="top" ><div id="payGrid" style="margin:0; padding:0"></div></td>
						</tr>
					</table>
				</td>
				</tr>
				<tr>
					<td height="70%"  valign="top">
						<div id="masterGrid" style="margin:0; padding:0"></div>
						<input type="hidden" id="strCurCompId" name="strCurCompId" />
						<input type="hidden" id="strJsonParam" name="strJsonParam" />
						<input type="hidden" id="strMessage" name="strMessage" />
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