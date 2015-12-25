<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script> 	
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/PersonnelControl/PC004/pc004.js"></script>
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
		
    </style>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
	</script>
</head>
<body onLoad="initMap()">
	<div class="l-loading" style="display:block;height:100%;" id="pageloading"></div> 
	 <div id="pc004layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
	    <div position="center"   id="designPanel"  style="width:100%;"> 
	    		<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
				<td width="460" valign="top">
				<table id="showTable" width="460"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
						<tr> <td>单号：</td>
							 <td >
									<input id="strSearchBillno" type="text" style="width:110"/>
							 </td>
							 <td>日期：</td>
							 <td >
									<input id="strSearchDate" type="text" style="width:100"/>
							 </td>
							 <td ><div id="searchButton"></div></td>
						</tr>
				</table>
				<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
				</td>
				<td  valign="top">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
					<tr height="735">
							<td valign="top">
							<div  style="width:565; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<form name="curRewardFrom" method="post"  id="curRewardFrom">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:32px;" >
										<tr>
											<td >&nbsp;&nbsp;<font color="red">录入门店</font></td>
											<td ><input type="text" name="curStaffrewardinfo.id.entrycompid" id="entrycompid"  readonly="true" style="width:140;"/>
											</td>
											<td >&nbsp;&nbsp;<font color="red">录入单号</font></td>
											<td ><input type="text" name="curStaffrewardinfo.id.entrybillid" id="entrybillid"  readonly="true" style="width:140;"/>
											</td>
											
											
										</tr>
										<tr>
											<td >&nbsp;&nbsp;单据状态</td>
											<td >
												<input type="radio" id="billflag0" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curStaffrewardinfo.billflag" value="0" disabled="true" />登记
												<input type="radio" id="billflag1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curStaffrewardinfo.billflag" value="1" disabled="true"/>门店审核
												<input type="radio" id="billflag2" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curStaffrewardinfo.billflag" value="2" disabled="true"/>总部审核
											</td>
											<td >&nbsp;&nbsp;奖罚日期</td>
											<td >
												<input type="text" name="curStaffrewardinfo.entrydate" id="entrydate"  readonly="true" style="width:140;"/>
											</td>
											
										</tr>
										<tr>
											<td width="15%">&nbsp;&nbsp;<font color="blue">奖罚方式</font></td>
											<td >
												<select id="entryflag" name="curStaffrewardinfo.entryflag" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333; width:140;" >
													<option value="0">员工奖励</option>
													<option value="1">员工处罚</option>
												</select>
											</td>
											<td width="15%">&nbsp;&nbsp;<font color="blue">奖罚类型</font></td>
											<td >
												<input type="text" name="entrytype" id="entrytype"  readonly="true" style="width:140;"/>
											</td>
										</tr>
										
										<tr>	
											<td width="15%">&nbsp;&nbsp;奖罚门店</td>
											<td colspan="3"><%@include file="search.frag"%></td>
										</tr>
										<tr>	
											<td width="15%">&nbsp;&nbsp;<font color="blue">奖罚员工</font></td>
											<td colspan="3">
												<input type="text" name="curStaffrewardinfo.handstaffid" id="handstaffid"  readonly="true" style="width:140;" onchange="validateHandstaffid(this)"/>
												<input type="text" name="curStaffrewardinfo.handstaffname" id="handstaffname"  readonly="true" style="width:140;"/>
												-<input type="text" name="curStaffrewardinfo.handcompid" id="handcompid" readonly="true" style="width:80;"/>店
											</td>
										</tr>
										<tr>	
											<td width="15%">&nbsp;&nbsp;奖罚原因</td>
											<td colspan="3">
												<textarea rows="5" cols="45" name="curStaffrewardinfo.entryreason" id="entryreason" ></textarea>
											</td>
										</tr>
										<tr><td style="border-bottom:1px #000000 dashed" colspan="4"><font color="red"></font></td></tr>
										<tr><td style="border-bottom:1px #000000 dashed" colspan="4"><font color="red">门店处理</font></td></tr>
										<tr>
											<td width="15%">&nbsp;&nbsp;奖罚员工</td>
											<td colspan="3">
												<input type="text" name="curStaffrewardinfo.checkrewardstaff" id="checkrewardstaff"  readonly="true" style="width:140;" onchange="validatecheckrewardstaff(this)"/>
												<input type="text" name="curStaffrewardinfo.checkrewardstaffname" id="checkrewardstaffname"  readonly="true" style="width:140;"/>
											</td>
										</tr>
										<tr>
											<td width="15%">&nbsp;&nbsp;审核结果</td>
											<td colspan="3">
												<textarea rows="5" cols="45" name="curStaffrewardinfo.checkrewardremark" id="checkrewardremark" readonly="true"></textarea>
											</td>
										</tr>
										<tr>
											<td width="15%">&nbsp;&nbsp;<font color="blue">奖罚金额</font></td>
											<td>
												<input type="text" name="curStaffrewardinfo.checkrewardamt" id="checkrewardamt"  readonly="true" style="width:140;" onchange="validatePrice(this)"/>
											</td>
											<td colspan="2" align="center"><div id="checkButton"></div> </td>
										</tr>
										<tr><td style="border-bottom:1px #000000 dashed" colspan="4"><font color="red"></font></td></tr>
										<tr><td style="border-bottom:1px #000000 dashed" colspan="4"><font color="red">总部处理</font></td></tr>
										<tr>
											<td width="15%">&nbsp;&nbsp;奖罚员工</td>
											<td colspan="3">
												<input type="text" name="curStaffrewardinfo.checkinheadrewardstaff" id="checkinheadrewardstaff"  readonly="true" style="width:140;" onchange="validatecheckinheadrewardstaff(this)"/>
												<input type="text" name="curStaffrewardinfo.checkinheadrewardstaffname" id="checkinheadrewardstaffname"  readonly="true" style="width:140;"/>
											</td>
										</tr>
										<tr>		
											<td width="15%">&nbsp;&nbsp;审核结果</td>
											<td colspan="3">
												<textarea rows="5" cols="45" name="curStaffrewardinfo.checkinheadrewardremark" id="checkinheadrewardremark" readonly="true"></textarea>
											</td>
										</tr>
										<tr>
											<td width="15%">&nbsp;&nbsp;<font color="blue">奖罚金额</font></td>
											<td>
												<input type="text" name="curStaffrewardinfo.checkinheadrewardamt" id="checkinheadrewardamt"  readonly="true" style="width:140;" onchange="validatePrice(this)"/>
											</td>
											<td colspan="2" align="center"><div id="confirmButton"></div> </td>
										</tr>
									</table>
									<input type="hidden" name="curStaffrewardinfo.entrytype" id="entrytypehid"/>
								
									<input type="hidden" name="curStaffrewardinfo.checkpersonid" id="checkpersonid"/>
									<input type="hidden" name="curStaffrewardinfo.checkflag" id="checkflag"/>
									<input type="hidden" name="curStaffrewardinfo.checkinheadflag" id="checkinheadflag"/>
									<input type="hidden" name="curStaffrewardinfo.checkinheadpersonid" id="checkinheadpersonid"/>
									<input type="hidden" name="curStaffrewardinfo.checkdate" id="checkdate"/>
									<input type="hidden" name="curStaffrewardinfo.checkinheaddate" id="checkinheaddate"/>
									</form>
								</div>
						
							</td>
					</tr>
				
					
					</table>
					
						
				
				</td>
				</tr>
				</table>
	    </div>
	    <div position="right"   id="designPane2"  style="width:100%;"  title="单据审核拍照预览"> 
	    </div>
	 </div>
		
			
  
  <div style="display:none;">
  <!-- g data total ttt -->
</div>

</body>
</html>

