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
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC016/cc016.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/mindsearchitem.js"></script>
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
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="cc016layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
  	 	<div position="left"   id="lsPanel" title="连锁结构"> 
  	 		<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
			
			<tr>
				<td valign="top" colspan="2">
					<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
			  			<ul id="companyTree" style="margin-top:3px;"></ul>
		  			</div>
				</td>
			</tr>
			</table>		
		  	
	  	</div>
	  	 <div position="center"   id="designPanel"  style="width:100%;" > 
		 	<form name="detailForm" method="post"  id="detailForm">
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
				<tr >
					<td width="140" valign="top" >
				
							<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
					</td>
					<td width="205" valign="top" >
						<div id="commoninfodivdetial" style="margin:0; padding:0"></div>
					</td>
					<td width="205" valign="top" >
						<div id="commoninfodivdetial" style="margin:0; padding:0"></div>
					</td>
					<td valign="top" width="100%">
						<div  style="width:100%;height:200  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
							
							<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
							<tr>
							<td width="100"><font color="blue">赠送门店</font></td>
							<td ><input type="text" name="curSendpointcard.id.sendcompid" id="sendcompid"  readonly="true" style="width:110;" onchange="validateSalecardno(this)"  /></td>
							
							
							<td width="100"><font color="blue">赠送单据</font></td>
							<td colspan="3"><input type="text" name="curSendpointcard.id.sendbillid" id="sendbillid"  readonly="true" style="width:110;" onchange="validateSalecardno(this)" />
								<input type="hidden" name="curSendpointcard.id.sendtype" id="sendtype"/>
								<input type="hidden" name="curSendpointcard.sourcedate" id="sourcedate"/>
								<input type="hidden" name="curSendpointcard.sendrateflag" id="sendrateflag"/>
								<input type="hidden" name="curSendpointcard.changeseqno" id="changeseqno"/>
							</td>
							</tr>
							<tr>
							<td width="100">原始单号</td>
							<td ><input type="text" name="curSendpointcard.sourcebillid" id="sourcebillid"  readonly="true" style="width:110;" onchange="validateSalecardno(this)" /></td>
							
							<td width="100">赠送卡号</td>
							<td colspan="3"><input type="text" name="curSendpointcard.sourcecardno" id="sourcecardno"  readonly="true" style="width:110;" onchange="validateSalecardno(this)"  /></td>
							</tr>
							<tr>
							<td>原单金额</td>
							<td ><input type="text" name="curSendpointcard.sourceamt" id="sourceamt"  readonly="true" style="width:110;" onchange="validateSalecardno(this)"  /></td>
							
							<td>会员姓名</td>
							<td colspan="3"><input type="text" name="curSendpointcard.membername" id="membername"  readonly="true" style="width:110;" onchange="validateSalecardno(this)" /></td>
							</tr>
							<tr>
							<td>备注</td>
							<td colspan="6" ><input type="text" name="curSendpointcard.sendmark" id="sendmark"  readonly="true" style="width:300;" onchange="validateSalecardno(this)"  /></td>
							</tr>
							<tr id="face_tr" style="display: none;">
							<td>充值金额</td>
							<td><input type="text" name="_faceamt" id="_faceamt"  readonly="true" style="width:110;"/></td>
							<td>疗程金额</td>
							<td colspan="3"><input type="text" name="_courseamt" id="_courseamt"  readonly="true" style="width:110;"/></td>
							</tr>
							<tr>
							<td colspan="6"></td>
							<td>
								<div id="butsendCardPoint"></div>
							</td>
							</tr>
							<tr>
							<td>赠送方式</td>
							<td >
								<select name="curSendpointcard.sendpicflag" id="sendpicflag" style="width:130" onchange="validateSendFlag(this)">
									<option value="1">赠送积分</option>
									<option value="5">赠送抵用券</option>
									<!--  <option value="6">2014大礼包</option>-->
									<!--<option value="9">2015年3.8节活动</option>-->
								</select>
								<input type="hidden"  name="curSendpointcard.createcardtype" id="createcardtype" />
									
								<input type="hidden" name="curSendpointcard.createcardcount" id="createcardcount"/>
							</td>
							<td>赠送金额</td>
							<td><input type="text"  name="curSendpointcard.sendamt" id="sendamt" maxlength="20"  style="width:120;"  onchange="validatePrice(this)" /></td>
							<td width="100">赠送券号</td>
							<td><input type="text"  name="curSendpointcard.picno" id="picno" maxlength="20"  style="width:120;"  onchange="validatePicno(this)" /><input type="hidden"  name="curSendpointcard.firstdateno" id="firstdateno" /></td>
							
							</tr>
							<tr id="zstr" style="display:none">
								<td>
									疗程编号:										
								</td>
								<td>
									<input style="width:60" id="prjid" onfocus="itemsearchbegin(this,1);" onchange="validateProjectNo(this)" readonly="readonly"/>
									<input style="width:60" id="prjname" readonly="true"/>
								</td>
								<td>
									数量:
								</td>
								<td>
									<input style="width:120;" id="prjnum" readonly="readonly" onchange="changenum(this)"/>
									<input style="width:120;" id="prjamt" type="hidden"/>
									
								</td>
								
								<td>
									自选疗程
								</td>
								<td>
									<input type="checkbox" id="check1" onclick="isCheck(this)"/>
								</td>
								<td><div id="butsendCardZS"></div></td>
							</tr>
							<tr>
							<td colspan="7">
								<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
								<tr>
									<td valign="top" ><div id="commoninfodivdetial_dyq"  ></div></td>
									<td  valign="top"><div id="autobardetial"></div>
									<div id="commoninfodivdetial_tmk"></div> </td>
								</tr>
								</table>
							</td>
							</tr>
							</table>
					
						</div>
					</td>
				</tr>
				</table>
			</form>
			<input type="hidden" name="sendPointRate1" id="sendPointRate1"/>
			<input type="hidden" name="sendPointRate2" id="sendPointRate2"/>
			<input type="hidden" name="paycount" id="paycount"/>
			<input type="hidden" name="bsendbillid" id="bsendbillid"/>
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