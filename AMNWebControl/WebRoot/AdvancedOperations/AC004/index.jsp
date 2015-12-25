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
	<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC004/ac004.js"></script>

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

  	 <div id="ac004layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  	 	<div position="center"   id="designPanel"  style="width:100%;"> 
				<div id="commoninfodivsecond" style="margin:0; padding:0;"></div>
				<div  style="width:700; height:350; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:30px;" >
					<tr>
						<td  align="center">专员审核</td>
						<td colspan="2" ></td>
						<td align="center">经理审核</td>
						<td colspan="2" ></td>
					</tr>
					<tr>
						<td><select id="labCommissioner" name="labCommissioner" size="4" style="height:300px;width:120px;" multiple="multiple" onChange="chooeseCommissioner(this)"></select></td>
						<td>
						<input type="button" id="CommBtn" value=">>" style="width:40" onclick="loadComonInfo()"/>
						<br/>
						<input type="button" id="CommBtn" value="<<" style="width:40" onclick="rloadComonInfo()"/>
						</td>
						<td valign="top"><select id="strcommissioner" name="strcommissioner" size="4" style="height:200px;width:120px;" multiple="multiple" ></select></td>
						<td><select id="labManager" name=""labManager"" size="4" style="height:300px;width:120px;" multiple="multiple" onChange="chooeseManger(this)"></select></td>
						<td>
						<input type="button" id="CommBtn" value=">>" style="width:40" onclick="loadManager()"/>
						<br/>
						<input type="button" id="CommBtn" value="<<" style="width:40" onclick="rloadManager()"/>
						</td>
						<td valign="top"><select id="strmanager" name="strmanager" size="4" style="height:200px;width:120px;" multiple="multiple" ></select></td>
					</tr>
					</table>
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