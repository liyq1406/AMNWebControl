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
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>	
	<script type="text/javascript" src="<%=ContextPath%>/InvoicingControl/IC012/ic012.js"></script>
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
	 <div id="ic012layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
	 	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			 		 	<ul id="companyTree" style="margin-top:3px;">
		  			 		 	</ul>
		  	</div>
	  	</div>
	    <div position="center"   id="designPanel"  style="width:100%;"> 
	    	<form name="goodsInsertForm" method="post"  id="goodsInsertForm">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
				<td  valign="top">
					<table id="showTable" width="540"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
						<tr> <td>收货单号：</td>
							 <td >
									<input id="strSearchSendBillno" type="text" style="width:140"/>
							 </td>
							 <td>申请单号：</td>
							 <td >
									<input id="strSearchOrderBillno" type="text" style="width:140"/>
							 </td>
							 <td ><div id="searchButton"></div></td>
						</tr>
					</table>
					<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
				</td>
				<td  valign="top">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
					<tr height="200">
							<td valign="top">
							<div  style="width:750; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
										<tr>
											<td width="10%">&nbsp;&nbsp;<font color="blue">收货门店</font></td>
											<td ><input type="text"name="curMgoodsreceipt.id.receiptcompid" id="receiptcompid"   readonly="true" style="width:60;" />
											&nbsp;&nbsp;<input type="text"name="curMgoodsreceipt.breceiptcompname" id="breceiptcompname"   readonly="true" style="width:100;" /></td>
										
											<td >&nbsp;&nbsp;<font color="blue">收货单号</font></td>
											<td ><input type="text"name="curMgoodsreceipt.id.receiptbillid" id="receiptbillid"   readonly="true" style="width:140;"/></td>
											<td><div id="confirmInfo"></div></td>
										</tr>
										<tr>
											
											<td width="10%">&nbsp;&nbsp;<font color="blue">采购方式</font></td>
											<td >
												<select name="curMgoodsreceipt.orderbilltype" id="orderbilltype"  style="width:140;"  disabled="true">
												<option value="1"> 门店采购</option>
												<option value="2"> 员工采购</option>
												</select>
											</td>
											<td width="10%">&nbsp;&nbsp;<font color="blue">单据状态</font></td>
											<td>
												<select name="curMgoodsreceipt.receiptstate" id="receiptstate"  style="width:140;" disabled="true" >
												<option value="0"> 未处理</option>
												<option value="1"> 已收货</option>
												</select>
											</td>
											<td><div id="printButton"></div></td>
										</tr>
										
										<tr>
											
									
											
											<td width="10%">&nbsp;&nbsp;<font color="blue">收货人员</font></td>
											<td ><input type="text"name="curMgoodsreceipt.receiptstaffid" id="receiptstaffid"   readonly="true" style="width:140;" validate="{required:true,maxlength:20,notnull:false}" onchange="validateInserper(this)"/>
												&nbsp;&nbsp;<input type="text"name="curMgoodsreceipt.receiptstaffname" id="receiptstaffname"   readonly="true" style="width:100;" /></td>
											<td width="10%">&nbsp;&nbsp;<font color="blue">入库仓库</font></td>
											<td  colspan="2">
											<input type="text"name="curMgoodsreceipt.receiptwareid" id="receiptwareid"   readonly="true" style="width:60;" validate="{required:true,maxlength:10,notnull:false}"/>
											<input type="text"name="curMgoodsreceipt.receiptwarename" id="receiptwarename"   readonly="true" style="width:100;" validate="{required:true,maxlength:10,notnull:false}"/>
											</td>
											
										</tr>
										<tr>	
											<td >&nbsp;&nbsp;收货日期</td>
											<td ><input type="text"name="curMgoodsreceipt.receiptdate" id="receiptdate"   readonly="true" style="width:140;" validate="{required:true,maxlength:10,notnull:false}"/>
											&nbsp;&nbsp;<input type="text"name="curMgoodsreceipt.receipttime" id="receipttime"   readonly="true" style="width:100;" validate="{maxlength:10,notnull:false}"/></td>
											<td width="10%">&nbsp;&nbsp;发货单号</td>
											<td colspan="2"><input type="text"name="curMgoodsreceipt.receiptsendbillid" id="receiptsendbillid"   readonly="true" style="width:160;" validate="{maxlength:40,notnull:false}"/></td>
											
										</tr>
										
										<tr>	
											<td >&nbsp;&nbsp;审核人员</td>
											<td ><input type="text"name="curMgoodsreceipt.receiptopationerid" id="receiptopationerid"   readonly="true" style="width:140;" validate="{notnull:false}"/>
											&nbsp;&nbsp;<input type="text"name="curMgoodsreceipt.receiptopationdate" id="receiptopationdate"   readonly="true" style="width:100;" validate="{notnull:false}"/></td>
											<td width="10%">&nbsp;&nbsp;订单单号</td>
											<td colspan="2"><input type="text"name="curMgoodsreceipt.receiptorderbillid" id="receiptorderbillid"   readonly="true" style="width:160;" validate="{maxlength:40,notnull:false}"/></td>
											
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
	 	<div id="printContent" style="position:absolute;left:1px; top:60px;z-index:-2;display:none;">
			<table width="230px" border="0" cellspacing="0" cellpadding="0" id="text">
				<tr><td colspan="2" align="center" style="font-size:20px">阿玛尼门店收货</td></tr>
				<tr><td colspan="2" align="center">-----------------------------</td></tr>
				<tr><td colspan="2" align="left" style="padding-left: 5px;">打印日期:<label id="printDate"/></td></tr>
			 	<tr><td colspan="2">===========================</td></tr>
			 	<tr><td colspan="2" align="left" style="padding-left: 5px;">收货单号:<label id="p_receiptbillid"></label></td></tr>
			 	<tr><td colspan="2" align="left" style="padding-left: 5px;">收货门店:<label id="p_receiptcompid"></label>&nbsp;<label id="p_breceiptcompname"></label></td></tr>
			 	<tr><td colspan="2" align="left" style="padding-left: 5px;">收货人:<label id="p_receiptstaffid"></label>&nbsp;<label id="p_receiptstaffname"></label></td></tr>
				<tr><td colspan="2" align="left" style="padding-left: 5px;">收货日期:<label id="p_receiptdate"></label>&nbsp;<label id="p_receipttime"></label></td></tr>
				<tr><td colspan="2">===========================</td></tr>
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
  		document.write("<div id=\"keysList\" style=\"z-index:2;width:250px;position:absolute;display:none;background:#FFFFFF;border: 2px solid #a4a6a1;font-size:13px;cursor: default;\" onblur> </div>");
        document.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
	</script>
