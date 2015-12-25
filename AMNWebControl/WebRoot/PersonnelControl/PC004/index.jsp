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
	    <div id="PC004Tab" style="width:100%; height:98%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
			<div title="员工处罚录入" style=" ;font-size:12px;" >
			<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
				<td width="320" valign="top">
					<table id="showTable" width="320"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
							<tr> 
								<td>单号：</td>
								 <td >
										<input id="strSearchBillno" type="text" style="width:110"/>
								 </td>
								 <td ><div id="searchButton"></div></td>
							</tr>
					</table>
					<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
				</td>
				<td  valign="top">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
						<tr height="690">
							<td valign="top">
							<div  style="width:800; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<form name="curRewardFrom" method="post"  id="curRewardFrom">
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:32px;" >
										<tr>
											<td >&nbsp;&nbsp;<font color="red">录入门店</font></td>
											<td ><input type="text" name="curStaffrewardinfo.id.entrycompid" id="entrycompid"  readonly="true" style="width:140;"/>
											</td>
											<td >&nbsp;&nbsp;<font color="red">录入单号</font></td>
											<td ><input type="text" name="curStaffrewardinfo.id.entrybillid" id="entrybillid"  readonly="true" style="width:140;"/>
											</td>
											<td><div id="checkButton" style="margin:0; padding:0"></div> </td>
											
										</tr>
										<tr>	
											<td width="10%">&nbsp;&nbsp;<font color="blue">奖罚门店</font></td>
											<td colspan="3"><%@include file="/common/search.frag"%></td>
											<td><font color="blue">异动员工:</font>&nbsp;&nbsp;<select name="otherStaffNo" id="otherStaffNo" style="width:120;" onchange="validateOtherStaffNo(this)" >请选择</select></td>
										</tr>
										<tr><td style="border-bottom:1px #000000 dashed" colspan="5"><font color="red"></font></td></tr>
									</table>
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:32px;" >
									<tr>
										<td>处罚员工:&nbsp;&nbsp;<input type="text" name="ihandstaffid" id="ihandstaffid" style="width:80;" onchange="validateStaffNo(this)"/>
										&nbsp;<input type="text" name="ihandstaffname" id="ihandstaffname" style="width:80;"/>
										<input type="hidden" name="ihandstaffinid" id="ihandstaffinid"/>
										</td>
										<td>处罚类型:&nbsp;&nbsp;	<select id="ientrytype"  name="ientrytype" style="width:100"></select></td>
										<td>处罚日期:</td><td><input type="text" name="ientrydate" id="ientrydate" style="width:100;"/></td>
										<td>处罚金额:&nbsp;&nbsp;<input type="text" name="irewardamt" id="irewardamt" style="width:40;" onchange="validatePrice(this)" /></td>
									</tr>
									<tr>
										<td colspan="5">处罚原因:&nbsp;&nbsp;<input type="text" name="ientryreason" id="ientryreason" style="width:380;" maxlength="200" onchange="loadGird(this)"/></td>
									</tr>
									<tr><td style="border-bottom:1px #000000 dashed" colspan="5"><font color="red"></font></td></tr>
									</table>
									<div id="commoninfodivdetial" style="margin:0; padding:0"></div>
									<input type="hidden" name="curStaffrewardinfo.entryflag" id="entryflag" value="1"/>
									<input type="hidden" name="curStaffrewardinfo.billflag" id="billflag"/>
									<input type="hidden" name="curStaffrewardinfo.handcompid" id="handcompid"/>
									</form>
								</div>
							</td>
						</tr>
					</table>
				</td>
				<td></td>
				</tr>
				</table>
				</div>
				<div title="员工处罚统计" style=" ;font-size:12px;" >
					<table id="confirmTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
						<tr>
							 <td width="540"><div ><%@include file="/common/search1.frag"%></div></td>	
							 <td width="80">查询日期：</td>
							 <td width="120">
									<input id="strTJFromDate" type="text" size="10" />
							 </td>
							 <td width="120">
									<input id="strTJToDate" type="text" size="10" />
							 </td>
							 <td ><div id="searchTJButton"></div></td>
							 <td><font color="red">(未审核不计入工资)</font></td>
						</tr>
						<tr><td style="border-bottom:1px #000000 dashed" colspan="8"></td></tr>
					</table>	
					<div id="commoninfodivdetialtj" style="margin:0; padding:0"></div>
				
				</div>
				</div>
	    </div>
	    
	 </div>
		
			
  
  <div style="display:none;">
  <!-- g data total ttt -->
</div>

</body>
</html>

