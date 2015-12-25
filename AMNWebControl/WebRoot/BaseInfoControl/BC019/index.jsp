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
	<script type="text/javascript" src="<%=ContextPath%>/common/mindsearchitem.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC019/bc019.js"></script>
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
  
		    body,html{height:100%;}
	    body{ padding:0px; margin:0;   overflow:hidden;}  
	    .l-link{ display:block; height:16px; line-height:16px; padding-left:10px; text-decoration:underline; color:#333;}
	    .l-link2{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
	    .l-link3{text-decoration:underline;  margin-left:2px;margin-right:2px;}
	    .l-layout-top{background:#102A49; color:White;}
	    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
		 #dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;}
		 
		 #fd{ 
 		 width:520px;
		 height:540px;
		 background:#EDF1F8; 
		 border: 1px solid #849BCA;
		 margin-top:2px;
		 margin-left:2px;
		 float:left;
		 overflow:hidden;
		 position:absolute;
		 left:800;
		 top:50;
		 cursor:move;
		 float:left;
		 /*filter:alpha(opacity=50);*/
		}
		
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="bc019layout" style="width:100%; margin:0 auto; margin-top:4px; ">
  	 <div position="center"   id="designPanel"  style="width:100%;"> 
  		 <div id="BC019Tab" style="width:100%; height:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
				<div title="门店活动券信息查询" style=" ;font-size:12px;" >
					<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
					
						<tr>
				
						 <td width="540"><div ><%@include file="/common/search.frag"%></div></td>
						 <td width="80">卡前缀：  </td>
						 <td width="110">
								<input id="strSearchVoucherno" type="text" style="width:100" />
						 </td>
						 <td width="80">登记日期：  </td>
						 <td width="110">
								<input id="strSearchDate" type="text" style="width:100" />
						 </td>
						 <td width="200">登记券号：&nbsp;
								<input id="strSearchCardNo" type="text" style="width:120" />
						 </td>
						  <td ><div id="searchButton"></div></td>
					</tr>
					<tr><td style="border-bottom:1px #000000 dashed" colspan="7"></td></tr>
		
					</table>
					<div id="commoninfodivRecordDate" style="margin:0; padding:0"></div>
				</div>
				<div title="门店活动券信息登记" style=" font-size:12px;"   >
				<div id="commoninfodivEntryDate" style="margin:0; padding:0"></div>
						<div id="fd" style="filter:alpha(opacity=100);opacity:1;display:none" >
							<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="width:100%;border:solid 1px #aaa;font-size:12px;line-height:25px;" >
																 <tr>
									        						<td colspan="4" width="100%"  bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;" align="center">录入活动券信息</td>
												       			 </tr>
																<tr>	
																	<td width="80">登记门店</td>
																	<td colspan="2"><input type="text" name="strEntryCompId" id="strEntryCompId" style="width:60;" onchange="validateShandcompid(this)"/>
																		<input type="text" name="strEntryCompName" id="strEntryCompName" style="width:60;" />
																	</td>
																	<td rowspan="6" valign="top" width="280">
																	<div id="onebartoolbar"></div>
																	<div id="commoninfodivPrjDate" style="margin:0; padding:0"></div>
																	</td>
																	
																</tr>
																<tr> 	
																 	 <td >券前缀：</td>
																	 <td colspan="2">
																			<input id="strCardPrefix" name="strCardPrefix" type="text" style="width:60" />
																			<input id="dtotalCount" name="dtotalCount" type="text" style="width:30;" />&nbsp;&nbsp;张
																	 </td>
																</tr>
																<tr> 	
																 	 <td >生成规则：</td>
																	 <td colspan="2">
																	 	<select id="createflag" name="createflag" style="width:120" onchange="validateCreateFlag(this)">
																	 		<option value="0">手动填写</option>
																	 		<option value="1">自动生成</option>
																	 	</select>
																	</td>
																</tr>
																<tr> 	
																 	 <td >起始券号：</td>
																	 <td colspan="2">
																			<input id="strFromCardNo" name="strFromCardNo" type="text" style="width:140" onchange="validatePrice(this);validateFromCardNo(this)"/>
																	 </td>
																</tr>
																<tr> 	
																 	 <td >结束券号：</td>
																	 <td colspan="2">
																			<input id="strToCardNo" name="strToCardNo" type="text" style="width:140;background:#EDF1F8;"  readonly="true"/>
																	 </td>
																</tr>
																<tr> 	
																 	 <td >有效期：</td>
																	 <td >
																			<input id="strValidate" name="strValidate" type="text" style="width:90" readonly="true"/>
																	 </td >
																	 
																</tr>
																<tr >
																	<td colspan="4" color="#0000cc">
																		<hr width="100%" />
																	</td>
																</tr>
																<tr> 	
																 	 <td >售卡类型：</td>
																	 <td colspan="2">
																			<select id="strSaleCardType" name="strSaleCardType"  style="width:140" >
																			</select>
																	 </td>
																	 <td rowspan="3" valign="top">
																	 <div id="twobartoolbar"></div>
																	 <div id="commoninfodivGoodsDate" style="margin:0; padding:0"></div>
																	 </td>
																</tr>
																<tr> 	
																 	 <td >起充调整：</td>
																	 <td colspan="2">
																			<input id="dCardDeductAmt" name="dCardDeductAmt" type="text" style="width:60" onchange="validatePrice(this)"/>
																	 
																			<input id="strProQuanNo" name="strProQuanNo" type="hidden" style="width:140" />
																
																			<input id="strQuanValidate" name="strQuanValidate" type="hidden" />
																	 </td>
																</tr>
																<tr> 	
																 	 <td >赠电子券：</td>
																	 <td colspan="2">
																	 	<select id="sendquanflag" name="sendquanflag" style="width:120" onchange="validateSendFlag(this)">
																	 		<option value="0">不赠送</option>
																	 		<option value="1">赠送</option>
																	 	</select>
																	 </td>
																</tr>
																<tr >
																	<td colspan="4" color="#0000cc">
																		<hr width="100%" />
																	</td>
																</tr>
																 <tr>
									        						<td colspan="4" width="100%"  bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;" align="center">电子券信息</td>
												       			 </tr>
												       			  <tr>
									        						<td colspan="4">
									        						<div id="threebartoolbar"></div>
									        						<div id="commoninfodivrandcardDate" style="margin:0; padding:0"></div></td>
												       			 </tr>
																<tr> 	
																 	 <td colspan="4" align="center"><div id="postTargetButton"></div> </td>
																</tr>
															</table>
												</div>
											 </div>						 			
				</div>
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
  		document.write("<div id=\"keysList\" style=\"z-index:2;width:350px;position:absolute;display:none;background:#FFFFFF;border: 2px solid #a4a6a1;font-size:13px;cursor: default;\" onblur> </div>");
        document.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
	</script>