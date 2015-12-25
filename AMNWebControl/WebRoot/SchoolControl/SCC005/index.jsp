<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
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
	<script type="text/javascript" src="<%=ContextPath%>/SchoolControl/SCC005/scc005.js"></script>
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
	 <div id="scc005layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
	 	<div position="left" id="lsPanel" title="连锁结构"> 
		  	<div style="width: 220px; height: 650; overflow-y:auto;overflow-x:hidden;">
	  			<ul id="companyTree" style="margin-top:3px;"></ul>
		  	</div>
	  	</div>
	    <div position="center"   id="designPanel"  style="width:100%;"> 
			<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;">
				<tr>
				<td width="280" valign="top">
					<div id="commoninfodivmaster" style="margin:0; padding:0;"></div>
				</td>
				<td width="120" valign="top">
					<div id="billGrid" style="margin:0; padding:0;"></div>
				</td>
				<td  valign="top">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
					<tr height="145">
						<td valign="top">
						<div  style="width:600;float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;margin-bottom: 5px;">
								<table id="dataTable" width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;line-height:28px;" >
									<tr>	
										<td colspan="3" style="padding-bottom: 5px;">
											<div id="addBtn" style="float: left;margin: 0 50px 0 42px;"></div>
											<div id="editBtn" style="float: left;margin-right: 50px;"></div>
											<div id="saveBtn" style="float: left;margin-right: 50px;"></div>
											<div id="refBtn" style="float: left;"></div>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap">&nbsp;&nbsp;工号</td>
										<td nowrap="nowrap"><input type="text" name="curStaffinfo.id.staffno" id="staffno" style="width:100;" readonly="readonly" />
											<span style="margin-left: 30px;">姓名</span><input type="text" name="curStaffinfo.staffname" id="staffname" style="width:110;margin-left: 15px;" readonly="readonly" />
											<span style="margin-left: 30px;">职位</span><select name="curStaffinfo.position" id="position" style="width:120;margin-left: 5px;" disabled="true"></select></td>
									</tr>
									<tr>	
										<td nowrap="nowrap">&nbsp;&nbsp;身份证号码&nbsp;</td>
										<td nowrap="nowrap"><input type="text" name="curStaffinfo.pccid" id="pccid" style="width:150;" readonly="readonly" />
											<span style="margin-left: 60px;">手机</span>&nbsp;&nbsp;<input type="text" name="curStaffinfo.mobilephone" id="mobilephone" style="width:150;" readonly="readonly" /></td>
									</tr>
									<tr>	
										<td>&nbsp;&nbsp;备注</td>
										<td nowrap="nowrap"><input type="text" name="remark" id="remark" style="width:465;" /></td>
									</tr>
									<tr>	
										<td nowrap="nowrap">&nbsp;&nbsp;活动名称</td>
										<td>
											<select name="actno" id="actno" style="width:200;" disabled="disabled"></select>
											<span style="margin-left: 10px;">活动类型<select name="acttype" id="acttype" style="width:120;margin-left: 5px;" disabled="disabled"></select>
											&nbsp;&nbsp;分数&nbsp;<input type="text" name="score" id="score" style="width:45;" readonly="readonly" />
										</td>
									</tr>
									<tr>	
										<td colspan="2"><div style="float: left;margin-left: 40px;">&nbsp;</div>
											<div id="actBtn" style="float: left;"></div>
											<div id="listBtn" style="margin-right: 40px;float: right;"></div>
										</td>
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