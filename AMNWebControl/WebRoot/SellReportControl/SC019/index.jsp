<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>门店运营报表</title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/SellReportControl/SC019/sc019.js"></script>
	<style type="text/css">
       .l-table-edit-td{ padding:4px;}
       .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
       .l-verify-tip{ left:230px; top:120px;}
	    body,html{height:100%;}
    	body{ padding:0px; margin:0;overflow:hidden;font-size:12px;}  
    	.l-link{ display:block; height:16px; line-height:16px; padding-left:10px; text-decoration:underline; color:#333;}
    	.l-link2{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
    	.l-link3{text-decoration:underline;  margin-left:2px;margin-right:2px;}
    	.l-layout-top{background:#102A49; color:White;}
    	.l-layout-bottom{ background:#E5EDEF; text-align:center;}
	 	#dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;}
	 	.table-line{font-size: 12px;line-height: 20px;margin-left: 5px;}
	 	.table-line td {border: 1px solid #a3c0e8;padding: 0 5px;}
   </style>
</head>
<body>
	<div class="l-loading" style="display:block" id="pageloading"></div> 
	<div id="sc019layout" style="width:100%; margin:0 auto; margin-top:4px;overflow: auto;">
	  	<div id="detialTab" style="width:100%; height:95%; float:left; clear:both;overflow:auto;font-size:12px;"> 
			 	<div title="门店总表" style="font-size:12px;width:100%; height:98%;">
			 		<div style="width:100%;clear:both;overflow:auto;">
						<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
							<tr>
								<td width="200" align="right">门店&nbsp;&nbsp;</td>
							 	<td width="300"><select id="h_company" style="width: 150px;"></select></td>
							 	<td width="30">月份&nbsp;&nbsp;</td>
							 	<td width="300"><input id="h_date" type="text" size="10" /></td>
							  	<td><div id="h_search" style="margin-right: 30px;"></div></td>
							   	<td><div id="h_excel"></div></td>
							</tr>
							<tr><td style="border-bottom:1px #000000 dashed" colspan="7"></td></tr>
						</table>
					</div>
					<div style="width:100%;clear:both;overflow:auto;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size: 12px;line-height: 15px;">
							<tr><td style="padding: 5px 15px;font-size: 14px;">门店基础信息</td></tr>
							<tr>
								<table width="80%" cellspacing="0" cellpadding="0" class="table-line">
									<tr>
										<td width="9%">编号店名</td>
										<td width="11%" id="h_comp"></td>
										<td width="11%">美容房间数</td>
										<td width="9%" id="h_room"></td>
										<td width="11%">顾问人数</td>
										<td width="9%" id="h_cons"></td>
										<td width="11%">总面积</td>
										<td width="9%" id="h_area"></td>
										<td width="11%">美发师</td>
										<td width="9%" id="h_haird"></td>
									</tr>
									<tr>
										<td>开业时间</td>
										<td id="h_time"></td>
										<td>美容经理人数</td>
										<td id="h_pmp"></td>
										<td>美容师人数</td>
										<td id="h_face"></td>
										<td>烫染师</td>
										<td id="h_perm"></td>
										<td>大堂经理</td>
										<td id="h_lobby"></td>
									</tr>
								</table>
							</tr>
						</table>
					</div>
					<div id="homeGrid1" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="homeGrid2" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="homeGrid3" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="homeGrid4" style="margin:0; padding:0;margin-top: 10px;"></div>
				</div>	
				<div title="美容部" style="font-size:12px;width:100%; height:98%;">
					<div style="width:100%;clear:both;overflow:auto;">
						<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
							<tr>
								<td width="200" align="right">门店&nbsp;&nbsp;</td>
							 	<td width="300"><select id="f_company" style="width: 150px;"></select></td>
							 	<td width="30">月份&nbsp;&nbsp;</td>
							 	<td width="300"><input id="f_date" type="text" size="10" /></td>
							  	<td><div id="f_search" style="margin-right: 30px;"></div></td>
							   	<td><div id="f_excel"></div></td>
							</tr>
							<tr><td style="border-bottom:1px #000000 dashed" colspan="6"></td></tr>
						</table>
					</div>
					<div style="width:100%; clear:both;overflow:auto;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size: 12px;line-height: 15px;">
							<tr><td style="padding: 5px 15px;font-size: 14px;">门店基础信息</td></tr>
							<tr>
								<table width="70%" cellspacing="0" cellpadding="0" class="table-line">
									<tr>
										<td width="15%">编号店名</td>
										<td width="25%" id="f_comp"></td>
										<td width="15%">美容房间数</td>
										<td width="15%" id="f_room"></td>
										<td width="15%">顾问人数</td>
										<td width="15%" id="f_cons"></td>
									</tr>
									<tr>
										<td>开业时间</td>
										<td id="f_time"></td>
										<td>美容经理人数</td>
										<td id="f_pmp"></td>
										<td>美容师人数</td>
										<td id="f_face"></td>
									</tr>
								</table>
							</tr>
						</table>
					</div>
					<div id="faceGrid1" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="faceGrid2" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="faceGrid3" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="faceGrid4" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="faceGrid5" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="faceGrid6" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="faceGrid7" style="margin:0; padding:0;margin-top: 10px;"></div>
				</div>	
				<div title="美发部" style="font-size:12px;width:100%; height:98%;">
					<div style="width:100%;clear:both;overflow:auto;">
						<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
							<tr>
								<td width="200" align="right">门店&nbsp;&nbsp;</td>
							 	<td width="300"><select id="m_company" style="width: 150px;"></select></td>
							 	<td width="30">月份&nbsp;&nbsp;</td>
							 	<td width="300"><input id="m_date" type="text" size="10" /></td>
							  	<td><div id="m_search" style="margin-right: 30px;"></div></td>
							   	<td><div id="m_excel"></div></td>
							</tr>
							<tr><td style="border-bottom:1px #000000 dashed" colspan="6"></td></tr>
						</table>
					</div>
					<div style="width:100%;float:left; clear:both;overflow:auto;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size: 12px;line-height: 15px;">
							<tr><td style="padding: 5px 15px;font-size: 14px;">门店基础信息</td></tr>
							<tr>
								<table width="70%" cellspacing="0" cellpadding="0" class="table-line">
									<tr>
										<td width="15%">编号店名</td>
										<td width="25%" id="m_comp"></td>
										<td width="15%">总面积</td>
										<td width="15%" id="m_area"></td>
										<td width="15%">美发师</td>
										<td width="15%" id="m_haird"></td>
									</tr>
									<tr>
										<td>开业时间</td>
										<td id="m_time"></td>
										<td>烫染师</td>
										<td id="m_perm"></td>
										<td>大堂经理</td>
										<td id="m_lobby"></td>
									</tr>
								</table>
							</tr>
						</table>
					</div>
					<div id="hairGrid1" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="hairGrid2" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="hairGrid3" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="hairGrid4" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="hairGrid5" style="margin:0; padding:0;margin-top: 10px;"></div>
					<div id="hairGrid6" style="margin:0; padding:0;margin-top: 10px;"></div>
				</div>
				<div title="会员分层" style="font-size:12px;width:100%; height:98%;">
			 		<div style="width:100%;clear:both;overflow:auto;">
						<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
							<tr>
								<td width="200" align="right">门店&nbsp;&nbsp;</td>
							 	<td width="300"><select id="v_company" style="width: 150px;"></select></td>
							 	<td width="30">月份&nbsp;&nbsp;</td>
							 	<td width="300"><input id="v_date" type="text" size="10" /></td>
							  	<td><div id="v_search" style="margin-right: 30px;"></div></td>
							   	<td><div id="v_excel"></div></td>
							</tr>
							<tr><td style="border-bottom:1px #000000 dashed" colspan="6"></td></tr>
						</table>
					</div>
					<div id="vipGrid" style="margin:0; padding:0;"></div>
				</div>					
				<div title="会员明细" style="font-size:12px;width:100%; height:98%;">
			 		<div style="width:100%;clear:both;overflow:auto;">
						<table id="showTable" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
							<tr>
								<td width="200" align="right">门店&nbsp;&nbsp;</td>
							 	<td width="300"><select id="d_company" style="width: 150px;"></select></td>
							 	<td width="30">月份&nbsp;&nbsp;</td>
							 	<td width="300"><input id="d_date" type="text" size="10" /></td>
							  	<td><div id="d_search" style="margin-right: 30px;"></div></td>
							   	<td><div id="d_excel"></div></td>
							</tr>
							<tr><td style="border-bottom:1px #000000 dashed" colspan="6"></td></tr>
						</table>
					</div>
					<div id="detailGrid" style="margin:0; padding:0;"></div>
				</div>					
		 </div>	
	</div> 
  	<div style="display:none;"></div>
</body>
<script type="text/javascript">
 	var contextURL="<%=request.getContextPath()%>";
</script>
</html>