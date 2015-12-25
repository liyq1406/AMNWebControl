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
	<script type="text/javascript" src="<%=ContextPath%>/InvoicingControl/IC014/ic014.js"></script>

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

  	 <div id="ic014layout" style="width:100%; margin:0 auto; margin-top:4px; ">
	  	<div position="center"   id="designPanel"  style="width:100%;"> 
	  		<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
			<tr>
		
				 <td width="540"><div ><%@include file="/common/search.frag"%></div></td>
				 <td width="60">日期：</td>
				 <td >
						<input id="strFromDate" type="text" size="10" />
				 </td>
				 <td >
				 	仓库
					<input id="strToDate" type="hidden" style="width:40"/>
				 </td>
				 <td width="130">
						<input id="strWareId" type="text" size="10" />
				 </td>
				 <td width="60">产品：</td>
				 <td >
						<input id="strFromGoodsNo" type="text"  style="width:80" />
						－
						<input id="strToGoodsNo" type="text" style="width:80" />
				 </td>
				 <td ><div id="searchButton"></div></td>
				  <td ><div id="excelButton"></div></td>
			</tr>
			<tr><td style="border-bottom:1px #000000 dashed" colspan="9"></td></tr>

			</table>
			<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
			<tr>
				<td valign="top"><div id="commoninfodivGoodsStockDate" style="margin:0; padding:0"></div>		</td>
				<td valign="top"><div id="commoninfodivGoodsPcDate" style="margin:0; padding:0"></div>		</td>
			</tr>
			</table>	
			
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