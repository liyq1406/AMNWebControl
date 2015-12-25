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
	<script type="text/javascript" src="<%=ContextPath%>/InvoicingControl/IC007/ic007.js"></script>
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
	<script language="vbscript">
			function toAsc(str)
			toAsc = hex(asc(str))
			end function
	</script>
</head>
<body onLoad="initMap()">
	<div class="l-loading" style="display:block;height:100%;" id="pageloading"></div> 
	 <div id="ic007layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
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
				<td  valign="top"  width="300">
					<table id="showTable" width="300"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
						<tr> <td>退货单：</td>
							 <td >
									<input id="strSearchBillId" type="text" style="width:140"/>
							 </td>
							 <td ><div id="searchButton"></div></td>
						</tr>
					</table>
					<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
				</td>
				<td  valign="top">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
					<tr height="190">
							<td valign="top">
							<div  style="width:800; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
										<tr>
											<td width="10%">&nbsp;&nbsp;<font color="blue">退货门店</font></td>
											<td ><input type="text"name="curMreturngoodsinfo.id.returncompid" id="returncompid"   readonly="true" style="width:60;" />
											&nbsp;&nbsp;<input type="text"name="curMreturngoodsinfo.breturncompname" id="breturncompname"   readonly="true" style="width:100;" /></td>
										
											<td >&nbsp;&nbsp;<font color="blue">退货单号</font></td>
											<td ><input type="text"name="curMreturngoodsinfo.id.returnbillid" id="returnbillid"   readonly="true" style="width:140;"/></td>
											<td><div id="confirmInfo"></div> </td>
										</tr>
										<tr>
											
											<td >&nbsp;&nbsp;退货日期</td>
											<td ><input type="text"name="curMreturngoodsinfo.returndate" id="returndate"   readonly="true" style="width:140;" validate="{required:true,maxlength:10,notnull:false}"/>
											&nbsp;&nbsp;<input type="text"name="curMreturngoodsinfo.returntime" id="returntime"   readonly="true" style="width:100;" validate="{maxlength:10,notnull:false}"/></td>
											<td width="10%">&nbsp;&nbsp;<font color="blue">单据状态</font></td>
											<td>
												<select name="curMreturngoodsinfo.returnstate" id="returnstate"  style="width:140;" disabled="true" >
												<option value="0"> 登记</option>
												<option value="1"> 申请待批</option>
												<option value="2"> 总部审核</option>
												<option value="3"> 总部驳回</option>
												</select>
											</td>
											<td><div id="cancelInfo"></div> </td>
										
										</tr>
										
										<tr>
											
											<td width="10%">&nbsp;&nbsp;<font color="blue">退货人员</font></td>
											<td ><input type="text"name="curMreturngoodsinfo.returnstaffid" id="returnstaffid"   readonly="true" style="width:140;" validate="{required:true,maxlength:20,notnull:false}" onchange="validateInserper(this)"/>
												&nbsp;&nbsp;<input type="text"name="curMreturngoodsinfo.returnstaffname" id="returnstaffname"   readonly="true" style="width:100;" /></td>
											<td width="10%">&nbsp;&nbsp;<font color="blue">退货仓库</font></td>
											<td colspan="2"><input type="text"name="returnwareid" id="returnwareid"   readonly="true" style="width:140;" validate="{required:true,maxlength:10,notnull:false}"/></td>
											
											
										</tr>
										
										<tr>	
											<td >&nbsp;&nbsp;操作人员</td>
											<td ><input type="text"name="curMreturngoodsinfo.returnopationerid" id="returnopationerid"   readonly="true" style="width:140;" validate="{notnull:false}"/>
											&nbsp;&nbsp;<input type="text"name="curMreturngoodsinfo.returnopationdate" id="returnopationdate"   readonly="true" style="width:100;" validate="{notnull:false}"/></td>
											<td >&nbsp;&nbsp;审核人员</td>
											<td colspan="2"><input type="text"name="curMreturngoodsinfo.confirmopationerid" id="confirmopationerid"   readonly="true" style="width:140;" validate="{notnull:false}"/>
											&nbsp;&nbsp;<input type="text"name="curMreturngoodsinfo.confirmopationdate" id="confirmopationdate"   readonly="true" style="width:100;" validate="{notnull:false}"/></td>
										</tr>
										
										<tr><td style="border-bottom:1px #000000 dashed" colspan="5"></td></tr>
										<tr height="40">
											<td colspan="5">&nbsp;&nbsp;<font color="red">退货产品</font>
											&nbsp;&nbsp;
											<input type="text"name="winsergoodsno" id="winsergoodsno"  style="width:140;"  onfocus="itemsearchbegin(this,2)" />
											&nbsp;&nbsp;名称
											<input type="text"name="winsergoodsname" id="winsergoodsname"   readonly="true" style="width:230;" />
											&nbsp;&nbsp;单位
											<input type="text"name="winsergoodsunit" id="winsergoodsunit"   readonly="true" style="width:50;" />
											&nbsp;&nbsp;单价
											<input type="text"name="winsergoodsprice" id="winsergoodsprice"   readonly="true" style="width:50;" />
											&nbsp;&nbsp;<font color="red">数量</font>
											<input type="text"name="winsergoodscount" id="winsergoodscount"   style="width:40;" onchange="loadGoodsGrid(this)"/>
											<input type="hidden"name="winsergoodsno" id="winsergoodsno"  />
											<input type="hidden"name="wrevicestoreno" id="wrevicestoreno"  />
											
											</td>
							
										</tr>
									</table>
									<input type="hidden" id="returnwareid_h" name="curMreturngoodsinfo.returnwareid" />
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
				<td  valign="top"  width="200">
					<div id="toptoolbardetial" style="margin:0; padding:0"></div>
					<div id="commoninfodivdetial_barcode" style="margin:0; padding:0"></div>
				
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
