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
	<script type="text/javascript" src="<%=ContextPath%>/InvoicingControl/IC010/ic010.js"></script>
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
<body onLoad="initMap()">
	<div class="l-loading" style="display:block;height:100%;" id="pageloading"></div> 
	 <div id="ic010layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
	
	    <div position="center"   id="designPanel"  style="width:100%;"> 
	    	<form name="goodsInsertForm" method="post"  id="goodsInsertForm" >
				<table  style="font-size:12px;line-height:25px;">
				<tr>
				<td valign="top">
					<div id="toptoolbarCard" style="margin:2; padding:0"></div>
					<div id="commoninfodivmaster" style="margin:2; padding:0"></div>
				</td>
				<td valign="top">
					<table  style="font-size:12px;line-height:35px;">
					<tr height="170">
							<td valign="top" >
							<div  style="width:700; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;margin-top:3px  ">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px;" >
										<tr>
											
											<td >&nbsp;&nbsp;申请门店</td>
											<td ><input type="text"name="curMgoodssendinfo.ordercompid" id="ordercompid"   readonly="true" style="width:50;" />
											&nbsp;<input type="text"name="curMgoodssendinfo.ordercompname" id="ordercompname"   readonly="true" style="width:70;" /></td>
											<td >&nbsp;&nbsp;订单号</td>
											<td ><input type="text"name="curMgoodssendinfo.orderbill" id="orderbill"   readonly="true" style="width:130;" validate="{maxlength:40,notnull:false}"/></td>
											
											
											
											<td >&nbsp;&nbsp;订货日期</td>
											<td ><input type="text"name="curMgoodssendinfo.orderdate" id="orderdate"   readonly="true" style="width:80;" validate="{required:true,maxlength:10,notnull:false}"/>
											&nbsp;&nbsp;<input type="text"name="curMgoodssendinfo.ordertime" id="ordertime"   readonly="true" style="width:60;" validate="{maxlength:10,notnull:false}"/></td>
											
										</tr>
										
										<tr>
											<td>&nbsp;&nbsp;<font color="red">采购方式</font></td>
											<td >
												<select name="orderbilltype_h" id="orderbilltype_h"  style="width:50;"  disabled="true">
												<option value="1"> 门店</option>
												<option value="2"> 员工</option>
												</select>
												<input type="hidden"name="curMgoodssendinfo.orderbilltype" id="orderbilltype"  />
												<select name="curMgoodssendinfo.sendstate" id="sendstate"  style="width:70;" disabled="true" >
												<option value="0"> 未处理</option>
												<option value="1"> 已发货</option>
												<option value="2"> 已作废</option>
												</select>
											</td>
												
											<td >&nbsp;&nbsp;发货单</td>
											<td ><input type="text"name="curMgoodssendinfo.id.sendbillid" id="sendbillid"   readonly="true" style="width:130;"/></td>
											
											<td >&nbsp;&nbsp;发货日期</td>
											<td ><input type="text"name="curMgoodssendinfo.senddate" id="senddate"   readonly="true" style="width:80;" validate="{required:true,maxlength:10,notnull:false}"/>
											&nbsp;&nbsp;<input type="text"name="curMgoodssendinfo.sendtime" id="sendtime"   readonly="true" style="width:60;" validate="{maxlength:10,notnull:false}"/></td>
											
											
											
										</tr>
										<tr >
											<td colspan="6" color="#0000cc">
												<hr width="100%" />
											</td>
										</tr>
										
										<tr height="30">
											<td colspan="6">
											&nbsp;&nbsp;<font color="blue">批量条码发货:</font>&nbsp;&nbsp;<input type="text"name="wfrombarcode" id="wfrombarcode"   readonly="true" style="width:140" onchange="validateFromBarNo(this);"/>
											~<input type="text"name="wtobarcode" id="wtobarcode"   readonly="true" style="width:140" onchange="validateToBarNo(this);"/>
											&nbsp;&nbsp;数量
											<input type="text"name="winsergoodscount" id="winsergoodscount"   readonly="true" style="width:60;background:#EDF1F8;" onchange="loadGoodsGrid(this)"/>
											&nbsp;&nbsp;<font color="blue">使用条码</font>
											&nbsp;&nbsp;<input type="checkbox" id="useBarno" name="useBarno" value="1" checked="true" onclick="changeOutType(this)"/>
											</td>
										</tr>	
										<tr height="30">
											<td colspan="6">&nbsp;&nbsp;发货产品编号:&nbsp;&nbsp;<input type="text"name="winsergoodsno" id="winsergoodsno"   readonly="true" style="width:140" onchange="validateinserno(this);"  onfocus="itemsearchbegin(this,2)"/>
											&nbsp;&nbsp;产品名称
											&nbsp;&nbsp;<input type="text"name="winsergoodsname" id="winsergoodsname"   readonly="true" style="width:120;background:#EDF1F8;" />
											&nbsp;&nbsp;单位
											<input type="text"name="winsergoodsunit" id="winsergoodsunit"   readonly="true" style="width:60;background:#EDF1F8;" />
											&nbsp;&nbsp;单价
											<input type="text"name="winsergoodsprice" id="winsergoodsprice"   readonly="true" style="width:60;background:#EDF1F8;" />
											<input type="hidden" name="wgoodsuniquebar" id="wgoodsuniquebar"   />
											</td>
										</tr>
										<tr >
											<td colspan="6">
											&nbsp;&nbsp;<font color="blue">单个条码发货:</font>&nbsp;&nbsp;<input type="text" id="onebarNo" name="onebarNo" style="width:140" onchange="validateOneBar(this)"/>
											</td>
										</tr>
									</table>
					
								</div>
							</td>
							<td valign="top">
							<div  style="width:100%; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px; margin-top:3px ">
								<table width="90%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
									<tr>	
											<td colspan="4">&nbsp;&nbsp;发货仓库&nbsp;<input type="text"name="curMgoodssendinfo.bbsendcompname" id="bbsendcompname"   readonly="true" style="width:80;" />
											<input type="hidden"name="curMgoodssendinfo.id.sendcompid" id="sendcompid" />
											<input type="text"name="curMgoodssendinfo.headwarename" id="headwarename"   readonly="true" style="width:60;" validate="{required:true,maxlength:10,notnull:false}"/>
											&nbsp;&nbsp;<input type="hidden"name="curMgoodssendinfo.sendstaffid" id="sendstaffid"  />
												<font color="blue">发货人</font><input type="text"name="curMgoodssendinfo.sendstaffname" id="sendstaffname"   readonly="true" style="width:60;" />
											
											<input type="hidden" name="curMgoodssendinfo.headwareid" id="headwareid"  />
											
											<input type="hidden"name="curMgoodssendinfo.sendopationerid" id="sendopationerid"  />
											<input type="hidden"name="curMgoodssendinfo.sendopationdate" id="sendopationdate" />
											</td>
											
											
									
											
									</tr>
									<tr>
											<td colspan="4">&nbsp;&nbsp;收货仓库&nbsp;<input type="text"name="curMgoodssendinfo.storewarename" id="storewarename"   readonly="true" style="width:80;" validate="{required:true,maxlength:10,notnull:false}"/>
											<input type="hidden"name="curMgoodssendinfo.storewareid" id="storewareid"   readonly="true" style="width:60;" validate="{required:true,maxlength:10,notnull:false}"/>
											</td>
											
									</tr>
									<tr>
											<td colspan="4">&nbsp;&nbsp;联系人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"name="curMgoodssendinfo.storestaffname" id="storestaffname"   readonly="true" style="width:80;" />
												<input type="hidden"name="curMgoodssendinfo.storestaffid" id="storestaffid"   /></td>
											
									</tr>
									<tr>
									<td colspan="4">&nbsp;&nbsp;门店地址&nbsp;<input type="text"name="curMgoodssendinfo.storeaddress" id="storeaddress"   readonly="true" style="width:250;"/></td>
									</tr>
									<tr>
										<td colspan="4"><hr width="100%" /></td>
									</tr>
									<tr>
											<td width="80" algin="center"><div id="confirmInfo"></div></td>
											<td width="80"algin="center" ><div id="cancelInfo"></div></td>
											<td width="80"algin="center" ><div id="prePrint"></div></td>
											<td width="80" algin="center"><div id="curPrint"></div></td>
									</tr>
								</table>
								</div>
							</td>
					</tr>
					<tr>
							<td valign="top">
								<div id="commoninfodivdetial" style="margin:0; padding:0"></div>
							</td>
							<td valign="top">
								<div id="commoninfodivdetial_barcode" style="margin:0; padding:0"></div>
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
  		document.write("<div id=\"keysList\" style=\"z-index:2;width:250px;position:absolute;display:none;background:#FFFFFF;border: 2px solid #a4a6a1;font-size:13px;cursor: default;\" onblur> </div>");
        document.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
	</script>
