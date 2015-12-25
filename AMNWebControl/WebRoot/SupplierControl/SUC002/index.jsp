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
	<script type="text/javascript" src="<%=ContextPath%>/SupplierControl/SUC002/suc002.js"></script>
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
	 <div id="suc002layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
	    <div position="center"   id="designPanel"  style="width:100%;"> 
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
					<td width="450" valign="top">
						<!-- 
						<div>
							单号：
							<input id="txtKey" type="text">
  						 		<input id="btnOK" type="button" value="查找" onclick="f_search()">
  						 	</div>
  						 	 -->
						<div id="orderList" style="margin:0; padding:0;"></div>
					</td>
					<td valign="top">
						<table width="98%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td valign="top">
									<div  style="width:100%;float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;margin-bottom: 5px;">
										<form id="dataForm" name="dataForm" method="post" >
											<table width="100%" border="0" cellspacing="0" cellpadding="0"  style="font-size:12px;line-height:28px;">
												<tr>
													<td>店名:</td>
													<td><input readonly="true" type="text" id="bbsendcompname" name="bbsendcompname"/></td>
													<td>单号:</td>
													<td><input readonly="true" type="text" id="orderbill" name="orderbill"/></td>
													<td>订货日期:</td>
													<td><input readonly="true" type="text" id="orderdate" name="orderdate"/></td>
												</tr>
												<tr>
													<td>联系人:</td>
													<td><input readonly="true" type="text" id="storestaffid" name="storestaffid"/></td>
													<td>门店电话:</td>
													<td><input readonly="true" type="text" id="storewarename" name="storewarename"/></td>
													<td>发货日期:</td>
													<td><input readonly="true" type="text" id="senddate" name="senddate" /></td>
												</tr>
												<tr>
													<td>门店地址:</td>
													<td colspan="5"><input readonly="true" type="text" id="storeaddress" name="storeaddress" style="width: 405px;"/></td>
												</tr>
												<tr>
													<td>开票信息:</td>
													<td colspan="5"><input readonly="true" type="text" id="ordercompname" name="ordercompname" style="width: 405px;"/></td>
												</tr>
												<tr>
													<td colspan="3">
														<input type="hidden" id="bsendcompid" name="bsendcompid" />
														<input type="hidden" id="bsendbillid" name="bsendbillid" />
														<input type="hidden" id="ordertime" name="ordertime" />
														<input type="hidden" id="headwareid" name="headwareid" />
														<input type="hidden" id="ordercompid" name="ordercompid" />
														<input type="hidden" id="orderbilltype" name="orderbilltype" />
														<input type="hidden" id="storestaffname" name="storestaffname" />
													</td>
													<td><div id="lost"></div></td>
													<td><div id="print"></div></td>
													<td><div id="send"></div></td>
												</tr>
											</table>
										</form>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div id="orderDetailList" style="margin:0; padding:0;"></div>
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