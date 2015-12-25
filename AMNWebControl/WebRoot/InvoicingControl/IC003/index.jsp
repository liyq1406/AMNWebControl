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
	<script type="text/javascript" src="<%=ContextPath%>/InvoicingControl/IC003/ic003.js"></script>
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
	 <div id="ic003layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
	    <div position="center"   id="designPanel"  style="width:100%;"> 
	    	<form name="goodsInsertForm" method="post"  id="goodsInsertForm">
			
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
					<td   width="240" valign="top" rowspan="2" colspan="2">
					<table id="showTable" width="240"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
						<tr> <td>单号：</td>
							 <td >
									<input id="strSearchBillno" type="text" style="width:120"/>
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
							<div  style="width:940; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
										<tr>
											<td width="10%">&nbsp;&nbsp;<font color="blue">盘点门店</font></td>
											<td ><input type="text"name="curMgoodsinventory.id.inventcompid" id="inventcompid"   readonly="true" style="width:60;" />
											&nbsp;&nbsp;<input type="text"name="curMgoodsinventory.binventcompname" id="binventcompname"   readonly="true" style="width:100;" /></td>
										
											<td >&nbsp;&nbsp;<font color="blue">盘点单号</font></td>
											<td ><input type="text"name="curMgoodsinventory.id.inventbillid" id="inventbillid"   readonly="true" style="width:140;"/></td>

										</tr>
										<tr>
											<td width="10%">&nbsp;&nbsp;<font color="blue">盘点人员</font></td>
											<td ><input type="text"name="curMgoodsinventory.inventstaffid" id="inventstaffid"   readonly="true" style="width:140;" validate="{required:true,maxlength:20,notnull:false}" onchange="validateInserper(this)"/>
												&nbsp;&nbsp;<input type="text"name="curMgoodsinventory.inventstaffname" id="inventstaffname"   readonly="true" style="width:100;" /></td>
									
											
											<td width="10%">&nbsp;&nbsp;<font color="blue">盘点仓库</font></td>
											<td ><input type="text"name="inventwareid" id="inventwareid"   readonly="true" style="width:140;" validate="{required:true,maxlength:10,notnull:false}"/></td>
									
										</tr>
										<tr>	
											<td >&nbsp;&nbsp;盘点日期</td>
											<td ><input type="text"name="curMgoodsinventory.inventdate" id="inventdate"   readonly="true" style="width:140;" validate="{required:true,maxlength:10,notnull:false}"/>
											&nbsp;&nbsp;<input type="text"name="curMgoodsinventory.inventtime" id="inventtime"   readonly="true" style="width:100;" validate="{maxlength:10,notnull:false}"/></td>
										
											<td width="10%">&nbsp;&nbsp;调帐入库单</td>
											<td ><input type="text"name="curMgoodsinventory.inventinserbillid" id="inventinserbillid"   readonly="true" style="width:200;" validate="{maxlength:40,notnull:false}"/></td>
											<td>&nbsp;&nbsp;</td>
										</tr>
										
										
										<tr>	
											<td >&nbsp;&nbsp;审核人员</td>
											<td><input type="text"name="curMgoodsinventory.inventopationerid" id="inventopationerid"   readonly="true" style="width:140;" validate="{notnull:false}"/>
											&nbsp;&nbsp;<input type="text"name="curMgoodsinventory.inventopationdate" id="inventopationdate"   readonly="true" style="width:100;" validate="{notnull:false}"/></td>
											<td width="10%">&nbsp;&nbsp;调帐出库单</td>
											<td ><input type="text"name="curMgoodsinventory.inventouterbillid" id="inventouterbillid"   readonly="true" style="width:200;" validate="{maxlength:40,notnull:false}"/></td>
											<td>&nbsp;&nbsp;</td>
										</tr>
										<tr><td style="border-bottom:1px #000000 dashed" colspan="4"></td></tr>
										<tr height="35">
											<td colspan="5">&nbsp;&nbsp;<font color="red">盘点产品</font>
											&nbsp;&nbsp;
											<input type="text"name="winsergoodsno" id="winsergoodsno"   readonly="true" style="width:100;" onchange="validateinserno(this);"  onfocus="itemsearchbegin(this,2)"/>
											<img src="<%=ContextPath%>/common/funtionimage/opengrid.gif" />
											&nbsp;&nbsp;名称
											<input type="text"name="winsergoodsname" id="winsergoodsname"   readonly="true" style="width:160;" />
											&nbsp;&nbsp;单位
											<input type="text"name="winsergoodsunit" id="winsergoodsunit"   readonly="true" style="width:50;" />
											&nbsp;&nbsp;单价
											<input type="text"name="winsergoodsprice" id="winsergoodsprice"   readonly="true" style="width:50;" />
											&nbsp;&nbsp;<font color="red">盘点数</font>
											<input type="text"name="winsergoodscount" id="winsergoodscount"   readonly="true" style="width:40;" onchange="loadGoodsCount(this)"/>
											&nbsp;&nbsp;<font color="red">条码数</font>
											<input type="text"name="wbargoodscount" id="wbargoodscount"   readonly="true" style="width:30;" onchange="loadGoodsGrid(this)"/>
											
											<input type="hidden" name="wcurgoodsstock" id="wcurgoodsstock" />
											<input type="hidden" name="wcurdiscount" id="wcurdiscount" />
											<input type="hidden" id="wgoodsformat" name="wgoodsformat" /> 
											<input type="hidden" id="wfrombarcode" name="wfrombarcode" /> 
											<input type="hidden" id="wtobarcode" name="wtobarcode" /> 
											<input type="hidden" id="wgoodsuniquebar" name="wgoodsuniquebar" /> 
											<input type="hidden" id="wstandunit" name="wstandunit" /> 
											
											<input type="hidden" id="wstandprice" name="wstandprice" /> 
											<input type="hidden" id="winserunit" name="winserunit" /> 
											<input type="hidden" id="winserprice" name="winserprice" /> 
											<input type="hidden" id="wouterunit" name="wouterunit" /> 
											<input type="hidden" id="wouterprice" name="wouterprice" /> 
											<font color="red">[回车自动生成条码]</font>
											
											</td>
										
										</tr>
										<tr >
											<td colspan="4" align="right">
												<div id="editInventInfo"></div>
											</td>
											<td>&nbsp;&nbsp;</td>
										</tr>
									</table>
									<input type="hidden" id="inventwareid_h" name="curMgoodsinventory.inventwareid" />
									<input type="hidden" id="inventflag" name="curMgoodsinventory.inventflag" />
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
	    <div position="right"   id="designPane2"  style="width:100%;"  title="单据审核拍照预览"> 
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
