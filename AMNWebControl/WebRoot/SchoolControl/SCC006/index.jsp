<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/datePicker.css" rel="stylesheet" type="text/css" /> 
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.tablescroll.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script> 	
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/mindsearchitem.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/SchoolControl/SCC006/scc006.js"></script>

	
	<script language="vbscript">
			function toAsc(str)
			toAsc = hex(asc(str))
			end function
	</script>
	
	
	
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
		/* tablescroll */
		.tablescroll{font:12px normal Tahoma, Geneva, "Helvetica Neue", Helvetica, Arial, sans-serif;background-color:#fff;}
		.tablescroll td,.tablescroll_wrapper,.tablescroll_head,.tablescroll_foot{border:1px solid #ccc;}
		.tablescroll td{padding:5px;}
		.tablescroll_wrapper{border-left:0;}
		.tablescroll_head{font-size:12px;font-weight:bold;background-color:#eee;border-left:0;border-top:0;margin-bottom:3px;}
		.tablescroll thead td{border-right:0;border-bottom:0;}
		.tablescroll tbody td{border-right:0;border-bottom:0;}
		.tablescroll tbody tr.first td{border-top:0;}
		.tablescroll_foot{font-weight:bold;background-color:#eee;border-left:0;border-top:0;margin-top:3px;}
		.tablescroll tfoot td{border-right:0;border-bottom:0;}
    </style>

</head>
<body>
	<div class="l-loading" style="display:block;height:100%;" id="pageloading"></div> 
	 <div id="scc006layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
	    <div position="center"   id="designPanel"  style="width:100%;"> 
	    	<form name="goodsInsertForm" method="post"  id="goodsInsertForm">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
				<td width="340" valign="top">
				<div id="commoninfodivmaster" style="margin:0; padding:0;"></div>
				</td>
				<td  valign="top">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
					<tr height="100">
							<td valign="top">
							<div  style="width:900;float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;margin-bottom: 5px;">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
										<tr>	
											<td>&nbsp;&nbsp;编号</td>
											<td><input readonly="true" type="text"name="postion_no" id="postion_no" style="width:160;" readyOnly/></td>
											<td>&nbsp;&nbsp;职位</td>
											<td><input readonly="true" type="text"name="postion" id="postion" style="width:160;" /></td>
											<td><div id="saveBtn" style="float: right;margin-right: 100px;" readyOnly></div></td>
										</tr>
										<tr>	
											<td>&nbsp;&nbsp;月份</td>
											<td><input readonly="true" type="text"name="date" id="date" style="width:160;" readyOnly/></td>
											<td>&nbsp;&nbsp;指标类型</td>
											<td><input readonly="true" type="text" id="indexSelected"></input></td>
										</tr>
										<tr>	
											<td>&nbsp;&nbsp;金额/数量</td>
											<td><input readonly="true" type="text"name="num" id="num" style="width:160;" /></td>
											<td>&nbsp;&nbsp;</td>
											<td><div id="addBtn" style="float: right;margin-right: 50px;"></div></td>
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
			<input type="hidden" id="needPost" name="needPost" value="1"/>
	    </div>
	 </div>
  <div style="display:none;">
  <!-- g data total ttt -->
</div>

</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>"; 
  		document.write("<div id=\"keysList\" style=\"z-index:2;width:350px;position:absolute;display:none;background:#FFFFFF;border: 2px solid #a4a6a1;font-size:13px;cursor: default;\" onblur> </div>");
        document.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
	</script>
