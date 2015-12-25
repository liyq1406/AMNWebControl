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
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC016/bc016.js"></script>

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
 		 width:220px;
		 height:300px;
		 background:#EDF1F8; 
		 border: 1px solid #849BCA;
		 margin-top:2px;
		 margin-left:2px;
		 float:left;
		 overflow:hidden;
		 position:absolute;
		 left:975px;
		 top:45%;
		 cursor:move;
		 float:left;
		 /*filter:alpha(opacity=50);*/
		}
		
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="bc016layout" style="width:100%; margin:0 auto; margin-top:4px; ">
  	 <div position="center"   id="designPanel"  style="width:100%;"> 
  		 <div id="BC016Tab" style="width:100%; height:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
			<div title="门店指标信息审核" style=" ;font-size:12px;" >
				<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
					
						<tr>
				
						 <td width="540"><div ><%@include file="/common/search.frag"%></div></td>
						  <td width="80">查询日期：</td>
						 <td width="12">
								<input id="strFromDate" type="text" style="width:100" />
						 </td>
						 <td width="80">单据状态：</td>
						 <td width="100">
							<select id="billstate" type="text" style="width:100">
							<option value="4">全部</option>
							<option value="0">登记中</option>
							<option value="1">已审核</option>
							<option value="2">已驳回</option>
							<option value="3">已作废</option>
							</select>
							
						 </td>		
						 
						  <td ><div id="searchButton"></div></td>
					</tr>
					<tr><td style="border-bottom:1px #000000 dashed" colspan="6"></td></tr>
		
					</table>	
					<div id="commoninfodivTradeDate" style="margin:0; padding:0"></div>		
				</div>	
				<div title="门店指标信息登记" style=" ;font-size:12px;" >
				<div id="commoninfodivRecordDate" style="margin:0; padding:0"></div>	
						<div id="fd" style="filter:alpha(opacity=100);opacity:1;">
							<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="width:100%;border:solid 1px #aaa;font-size:12px;line-height:25px;" >
																 <tr>
									        						<td colspan="5" width="100%"  bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;" align="center">录入保底信息</td>
												       			 </tr>
																
																<tr>	
																	<td width="80"><font color="red">录入单号</font></td>
																	<td colspan="4"><input type="text" name="curStoretargetinfo.entrybillid" id="entrybillid"  readonly="true" style="width:120;"/>
																	<input type="hidden" name="curStoretargetinfo.entrycompid" id="entrycompid" />
																	</td>
																</tr>
																<tr>	
																	<td><font color="blue">登记门店</font></td>
																	
																	<td colspan="4"><input type="text" name="curStoretargetinfo.compid" id="compid" style="width:60;" onchange="validateShandcompid(this)"/>
																		<input type="text" name="curStoretargetinfo.compname" id="compname" style="width:60;" />
																	</td>
																</tr>
																<tr> 	
																 	 <td >指标月份：</td>
																	 <td width="120">
																			<input id="targetmonth" name="curStoretargetinfo.targetmonth" type="text" style="width:100" readonly="true"/>
																	 </td>
																</tr>
																<tr> 	
																 	 <td >总虚业绩：</td>
																	 <td width="120">
																			<input id="ttotalyeji" name="curStoretargetinfo.ttotalyeji" type="text" style="width:100"  onchange="validatePrice(this)"/>
																	 </td>
																</tr>
																<tr> 	
																 	 <td >总实业绩：</td>
																	 <td width="120">
																			<input id="trealtotalyeji" name="curStoretargetinfo.trealtotalyeji" type="text" style="width:100" onchange="validatePrice(this)" />
																	 </td>
																</tr>
																<tr> 	
																 	 <td>美容虚业绩：</td>
																	 <td width="120">
																			<input id="tbeatyyeji" name="curStoretargetinfo.tbeatyyeji" type="text" style="width:100" onchange="validatePrice(this)" />
																	 </td>
																</tr>
																<tr> 	
																 	 <td >烫染虚业绩：</td>
																	 <td width="120">
																			<input id="ttrhyeji" name="curStoretargetinfo.ttrhyeji" type="text" style="width:100" onchange="validatePrice(this)" />
																	 </td>
																</tr>
																<tr> 	
																 	 <td >门店流失数：</td>
																	 <td width="120">
																			<input id="tstaffleavelcount" name="curStoretargetinfo.tstaffleavelcount" type="text" style="width:100"  onchange="validatePrice(this)"/>
																	 </td>
																</tr>
																<tr> 	
																 	 <td >门店客单数：</td>
																	 <td width="120">
																			<input id="tcostcount" name="curStoretargetinfo.tcostcount" type="text" style="width:100" onchange="validatePrice(this)" />
																	 </td>
																</tr>
																<tr>
																	<td align="center"><div id="checkTargetButton" style="margin:0; padding:0"></div> </td>
																	<td align="center"><div id="postTargetButton" style="margin:0; padding:0"></div> </td>
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
  	
	</script>