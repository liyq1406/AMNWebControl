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
	<script type="text/javascript" src="<%=ContextPath%>/PersonnelControl/PC017/pc017.js"></script>
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
		#fd{ 
 		 width:300px;
		 height:240px;
		 background:#EDF1F8; 
		 border: 1px solid #849BCA;
		 margin-top:2px;
		 margin-left:2px;
		 float:left;
		 overflow:hidden;
		 position:absolute;
		 left:275px;
		 top:60%;
		 cursor:move;
		 float:left;
		 /*filter:alpha(opacity=50);*/
		}
		#fdcon{ 
 		 width:200px;
		 height:240px;
		 background:#EDF1F8; 
		 border: 1px solid #849BCA;
		 margin-top:2px;
		 margin-left:2px;
		 float:left;
		 overflow:hidden;
		 position:absolute;
		 left:580px;
		 top:60%;
		 cursor:move;
		 float:left;
		 /*filter:alpha(opacity=50);*/
		}
		#fdstoncon{ 
 		 width:230px;
		 height:240px;
		 background:#EDF1F8; 
		 border: 1px solid #849BCA;
		 margin-top:2px;
		 margin-left:2px;
		 float:left;
		 overflow:hidden;
		 position:absolute;
		 left:780px;
		 top:60%;
		 cursor:move;
		 float:left;
		 /*filter:alpha(opacity=50);*/
		}
		
		#tfd{ 
 		 width:300px;
		 height:240px;
		 background:#EDF1F8; 
		 border: 1px solid #849BCA;
		 margin-top:2px;
		 margin-left:2px;
		 float:left;
		 overflow:hidden;
		 position:absolute;
		 left:275px;
		 top:60%;
		 cursor:move;
		 float:left;
		 /*filter:alpha(opacity=50);*/
		}
		#tfdcon{ 
 		 width:400px;
		 height:240px;
		 background:#EDF1F8; 
		 border: 1px solid #849BCA;
		 margin-top:2px;
		 margin-left:2px;
		 float:left;
		 overflow:hidden;
		 position:absolute;
		 left:580px;
		 top:60%;
		 cursor:move;
		 float:left;
		 /*filter:alpha(opacity=50);*/
		}
    </style>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
	</script>
</head>
<body onLoad="initMap()">
	<div class="l-loading" style="display:block;height:100%;" id="pageloading"></div> 
	 <div id="pc017layout" style="width:100%;margin:0 auto; margin-top:0px;  "> 
	    <div position="center"   id="designPanel"  style="width:100%;"> 
	    <div id="PC017Tab" style="width:100%; height:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
			
					<div title="门店员工保底登记" style=" ;font-size:12px;" >
			
										<div id="commoninfodivsubsidy" style="margin:0; padding:0"></div>
		
									
									 <div id="fd" style="filter:alpha(opacity=100);opacity:1;">
										<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
											<table width="100%" border="0" cellspacing="1" cellpadding="0" style="width:100%;border:solid 1px #aaa;font-size:12px;line-height:25px;" >
														 <tr>
							        						<td colspan="5" width="100%"  bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;" align="center">录入保底信息</td>
										       			 </tr>
														
														<tr>	
															<td width="80"><font color="red">录入单号</font></td>
															<td colspan="4"><input type="text" name="curMstaffsubsidyinfo.id.entrybillid" id="sentrybillid"  readonly="true" style="width:140;"/>
															<input type="hidden" name="curMstaffsubsidyinfo.id.entrycompid" id="sentrycompid" />
															</td>
														</tr>
														<tr>	
															<td><font color="blue">登记门店</font></td>
															
															<td colspan="4"><input type="text" name="curMstaffsubsidyinfo.handcompid" id="shandcompid" style="width:60;" onchange="validateShandcompid(this)"/>
																&nbsp;<input type="text" name="curMstaffsubsidyinfo.handcompname" id="shandcompname" style="width:60;" />
																<select id="strSearchPostion" name="strSearchPostion" style="width:60" onchange="searchStaffInfo()"></select>
															</td>
														</tr>
														<tr>	
															<td><font color="blue">保底员工</font></td>
															<td colspan="4"><input type="text" name="curMstaffsubsidyinfo.handstaffid" id="shandstaffid" style="width:60;" onchange="validateShandstaffid(this)"/>
																&nbsp;<input type="text" name="curMstaffsubsidyinfo.handstaffname" id="shandstaffname" style="width:80;"/>
																<input type="hidden" name="curMstaffsubsidyinfo.handstaffinid" id="shandstaffinid"/>
															</td>
														
														</tr>
														<tr>	
															<td>保底额度</td>
															<td colspan="4"><input type="text" name="curMstaffsubsidyinfo.subsidyamt" id="ssubsidyamt" style="width:60;" onchange="validatePrice(this);" /></td>
														</tr>
														<tr> 	
														 	<td >保底日期：</td>
															 <td width="120">
																	<input id="sstartdate" name="curMstaffsubsidyinfo.startdate" type="text" size="10" />
															 </td>
															 <td width="120">
																	<input id="senddate" name="curMstaffsubsidyinfo.enddate" type="text" size="10" />
															 </td>
														</tr>
														<tr>
															<td>
															保底方式
															</td>
															<td colspan="4"><select style="width:80" name="curMstaffsubsidyinfo.subsidyflag" id="ssubsidyflag" onchange="loadConditioninfo(this)"> 
																		<option value="1">全部满足</option>
																		<option value="2">部分满足</option>
																		</select>
															&nbsp;<select style="width:40" name="curMstaffsubsidyinfo.conditionnum" id="sconditionnum" disabled="true">
																		<option value="0"></option>
																		<option value="1">1</option>
																		<option value="2">2</option>
																		<option value="3">3</option>
																		<option value="4">4</option>
																		<option value="5">5</option>
																		</select>
															</td>
														</tr>
														<tr> 	
														 	<td ><font color="blue">申请人：</font></td>
														 	<td colspan="4">
																	<input id="appstaffname" name="curMstaffsubsidyinfo.appstaffname" type="text" style="width:80" />
															 </td>
														</tr>
														<tr> 
															 <td ><font color="blue">批准人:</font></td>
															<td colspan="4"><input id="checkstaffname" name="curMstaffsubsidyinfo.checkstaffname" type="text" style="width:80"  />
															 </td>
														</tr>
													</table>
										</div>
									 </div>
									 <div id="fdcon" style="filter:alpha(opacity=100);opacity:1;">
										<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
											<table width="100%" border="0" cellspacing="1" cellpadding="0" style="width:100%;border:solid 1px #aaa;font-size:12px;line-height:25px;" >
												<tr>
							        						<td colspan="2" width="100%"  bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;" align="center">录入保底条件</td>
										       	</tr>
												<tr>	
															<td>
															个人虚业绩
															</td>
															<td ><input type="text" name="subsidyamtA" id="subsidyamtA" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														
														</tr>
														<tr>
															<td>
															个人实业绩
															</td>
															<td ><input type="text" name="subsidyamtB" id="subsidyamtB" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														
														</tr>
														<tr>
															<td>
															个人老客数
															</td>
															<td ><input type="text" name="subsidyamtC" id="subsidyamtC" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														
														</tr>
														<tr>
															<td>
															美容大项数
															</td>
															<td ><input type="text" name="subsidyamtD" id="subsidyamtD" style="width:60;" onchange="validatePrice(this);" />
																&nbsp;</td>
														
														</tr>
														<tr>
															<td>
															烫染疗程数
															</td>
															<td ><input type="text" name="subsidyamtE" id="subsidyamtE" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														</tr>
														<tr>
															<td>
															美发大项数
															</td>
															<td ><input type="text" name="subsidyamtF" id="subsidyamtF" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														</tr>
														<tr>
															<td colspan="2" align="center"><div id="checkSubsidyButton" style="margin:0; padding:0"></div> </td>
														</tr>
											</table>
										</div>
									 </div>
									  <div id="fdstoncon" style="filter:alpha(opacity=100);opacity:1;">
										<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
											<div id="subsidystaffinfo"><div id="staffSubsidyDiv" style="margin:0; padding:0"></div></div>
										</div>
									 </div>
									 
									
				</div>
				<div title="门店员工指标登记" style=" ;font-size:12px;" >
				
					<div id="commoninfodivtarget" style="margin:0; padding:0"></div>
							<div id="tfd" style="filter:alpha(opacity=100);opacity:1;">
										<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
											<table width="100%" border="0" cellspacing="1" cellpadding="0" style="width:100%;border:solid 1px #aaa;font-size:12px;line-height:25px;" >
														 <tr>
							        						<td colspan="5" width="100%"  bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;" align="center">录入指标信息</td>
										       			 </tr>
														
														<tr>	
															<td width="80"><font color="red">录入单号</font></td>
															<td colspan="4"><input type="text" name="curMstafftargetinfo.id.entrybillid" id="tentrybillid"  readonly="true" style="width:140;"/>
															<input type="hidden" name="curMstafftargetinfo.id.entrycompid" id="tentrycompid" />
															</td>
														</tr>
														<tr>	
															<td><font color="blue">登记门店</font></td>
															
															<td colspan="4"><input type="text" name="curMstafftargetinfo.handcompid" id="thandcompid" style="width:60;" onchange="validateThandcompid(this)"/>
																&nbsp;<input type="text" name="curMstafftargetinfo.handcompname" id="thandcompname" style="width:80;" />
															</td>
														</tr>
														<tr>	
															<td><font color="blue">指标员工</font></td>
															<td colspan="4"><input type="text" name="curMstafftargetinfo.handstaffid" id="thandstaffid" style="width:60;" onchange="validateThandstaffid(this)"/>
																&nbsp;<input type="text" name="curMstafftargetinfo.handstaffname" id="thandstaffname" style="width:80;"/>
																<input type="hidden" name="curMstafftargetinfo.handstaffinid" id="thandstaffinid"/>
															</td>
														
														</tr>
														<tr>	
															<td>指标额度</td>
															<td colspan="4">
															<select style="width:60" name="curMstafftargetinfo.targemode" id="targemode" onchange="loadTargemodeChange(this)"> 
																		<option value="1">额度</option>
																		<option value="2">比率</option>
															</select>
															&nbsp;<input type="text" name="curMstafftargetinfo.targeamt" id="ttargeamt" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;<font color="red">(整数)</font>
															</td>
														</tr>
														<tr>	
															<td>比率基数</td>
															<td colspan="4">
															<select style="width:125" name="curMstafftargetinfo.targeyejitype" id="targeyejitype" disabled="true"> 
																		<option value="0"></option>
																		<option value="1">个人现金业绩</option>
																		<option value="2">个人实业绩</option>
																		<option value="3">个人总业绩</option>
																		<option value="4">门店美容虚业绩</option>
																		<option value="5">门店美发虚业绩</option>
																		<option value="6">门店总虚业绩</option>
																		<option value="7">门店美容实业绩</option>
																		<option value="8">门店美发实业绩</option>
																		<option value="9">门店总实业绩</option>
															</select>
															</td>
														</tr>
														
														<tr> 	
														 	<td >保底日期：</td>
															 <td width="120">
																	<input id="tstartdate" name="curMstafftargetinfo.startdate" type="text" size="10" />
															 </td>
															 <td width="120">
																	<input id="tenddate" name="curMstafftargetinfo.enddate" type="text" size="10" />
															 </td>
														</tr>
														<tr>
															<td>
															指标方式
															</td>
															<td colspan="4"><select style="width:80" name="curMstafftargetinfo.targeflag" id="ttargeflag" onchange="loadConditioninfo(this)"> 
																		<option value="1">全部满足</option>
																		<option value="2">部分满足</option>
																		</select>
															&nbsp;<select style="width:40" name="curMstafftargetinfo.conditionnum" id="tconditionnum" disabled="true">
																		<option value="0"></option>
																		<option value="1">1</option>
																		<option value="2">2</option>
																		<option value="3">3</option>
																		<option value="4">4</option>
																		<option value="5">5</option>
																		</select>
															</td>
														</tr>
													</table>
										</div>
									 </div>
									 <div id="tfdcon" style="filter:alpha(opacity=100);opacity:1;">
										<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
											<table width="100%" border="0" cellspacing="1" cellpadding="0" style="width:100%;border:solid 1px #aaa;font-size:12px;line-height:25px;" >
												<tr>
							        						<td colspan="4" width="100%"  bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;" align="center">录入指标条件</td>
										       	</tr>
												<tr>	
															<td>
															个人虚业绩
															</td>
															<td ><input type="text" name="targetamtA" id="targetamtA" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
															 <td>
															门店美容虚业绩
															</td>
															<td ><input type="text" name="targetamtF" id="targetamtF" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														</tr>
														<tr>
															<td>
															个人实业绩
															</td>
															<td ><input type="text" name="targetamtB" id="targetamtB" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														 		 <td>
															门店美发虚业绩
															</td>
															<td ><input type="text" name="targetamtG" id="targetamtG" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														</tr>
														<tr>
															<td>
															个人老客数
															</td>
															<td ><input type="text" name="targetamtC" id="targetamtC" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
															<td>
															门店总虚业绩
															</td>
															<td ><input type="text" name="targetamtH" id="targetamtH" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														</tr>
														<tr>
															<td>
															美容大项数
															</td>
															<td ><input type="text" name="targetamtD" id="targetamtD" style="width:60;" onchange="validatePrice(this);" />
																&nbsp;</td>
															 <td>
															门店美容实业绩
															</td>
															<td ><input type="text" name="targetamtI" id="targetamtI" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														</tr>
														<tr>
															<td>
															烫染疗程数
															</td>
															<td ><input type="text" name="targetamtE" id="targetamtE" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
															 <td>
															门店美发实业绩
															</td>
															<td ><input type="text" name="targetamtJ" id="targetamtJ" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														</tr>
														<tr>
															<td>
															
															</td>
															<td ></td>
															 <td>
															门店总实业绩
															</td>
															<td ><input type="text" name="targetamtK" id="targetamtK" style="width:60;" onchange="validatePrice(this);" />
															&nbsp;</td>
														</tr>
														<tr>
															<td colspan="4" align="center"><div id="checkTargetButton" style="margin:0; padding:0"></div> </td>
														</tr>
											</table>
										</div>
									 </div>
									
				</div>
			<div title="门店奖罚录入" style=" ;font-size:12px;" >
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
						<tr height="700">
							<td valign="top">
							<div  style="width:1000; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<form name="curRewardFrom" method="post"  id="curRewardFrom">
									<table width="800" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:32px;" >
										<tr>
											<td >&nbsp;&nbsp;<font color="red">录入门店</font></td>
											<td ><input type="text" name="curStaffrewardinfo.id.entrycompid" id="entrycompid"  readonly="true" style="width:140;"/>
											</td>
											<td >&nbsp;&nbsp;<font color="red">录入单号</font></td>
											<td ><input type="text" name="curStaffrewardinfo.id.entrybillid" id="entrybillid"  readonly="true" style="width:140;"/>
											</td>
											<td>类型:&nbsp;&nbsp;
											<select id="entryflag"  name="curStaffrewardinfo.entryflag" style="width:100">
												<option value="0">员工奖励</option>
												<option value="1">员工处罚</option>
											</select></td>
											<td> </td>
											
										</tr>
										<tr>	
											<td width="10%">&nbsp;&nbsp;<font color="blue">奖罚门店</font></td>
											<td colspan="4"><%@include file="/common/search.frag"%></td>
											<td><font color="blue">异动员工:</font>&nbsp;&nbsp;<select name="otherStaffNo" id="otherStaffNo" style="width:120;" onchange="validateOtherStaffNo(this)" >请选择</select></td>
										</tr>
										<tr><td style="border-bottom:1px #000000 dashed" colspan="6"></td></tr>
									</table>
									<table width="800" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:32px;" >
									<tr>
										<td>奖罚员工:&nbsp;&nbsp;<input type="text" name="ihandstaffid" id="ihandstaffid" style="width:80;" onchange="validateStaffNo(this)"/>
										&nbsp;<input type="text" name="ihandstaffname" id="ihandstaffname" style="width:80;"/>
										<input type="hidden" name="ihandstaffinid" id="ihandstaffinid"/>
										</td>
										
										<td>奖罚项目:&nbsp;&nbsp;	<select id="ientrytype"  name="ientrytype" style="width:100"></select></td>
										<td>奖罚日期:</td>
										<td><input type="text" name="ientrydate" id="ientrydate" style="width:100;"/></td>
										<td>奖罚金额:&nbsp;&nbsp;<input type="text" name="irewardamt" id="irewardamt" style="width:40;" onchange="validatePrice(this);" /></td>
									</tr>
									<tr>
										<td colspan="5">奖罚原因:&nbsp;&nbsp;<input type="text" name="ientryreason" id="ientryreason" style="width:380;" maxlength="200" onchange="loadGird(this)" /></td>
										
									</tr>
									
									</table>
									<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:32px;" >
									<tr><td style="border-bottom:1px #000000 dashed" colspan="2"></td></tr>
									<tr>
											<td><div id="commoninfodivdetial" style="margin:0; padding:0"></div></td>
											<td><div id="commoninfodivdetial_zrj" style="margin:0; padding:0"></div></td>
									</tr>
									</table>
									<input type="hidden" name="curStaffrewardinfo.billflag" id="billflag"/>
									<input type="hidden" name="curStaffrewardinfo.handcompid" id="handcompid"/>
									</form>
									
								</div>
							</td>
						</tr>
					</table>
				</td>
				</tr>
				</table>
				</div>
				
				
			</div>
	    </div>
	    
	 </div>
		
			
  
  <div style="display:none;">
  <!-- g data total ttt -->
</div>

</body>
</html>

