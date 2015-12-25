<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">  
<%
	response.setHeader("Pragma","No-cache"); 
 
	response.setHeader("Cache-Control","no-cache"); 
 
	response.setDateHeader("Expires", 0);  
	
 %>
<title></title>
<link
	href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js"
	type="text/javascript"></script>
<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
<script src="<%=ContextPath%>/common/ligerui/json2.js"
	type="text/javascript"></script>
<script
	src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js"
	type="text/javascript"></script>
<script
	src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script
	src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js"
	type="text/javascript"></script>
<script language="JavaScript">
	var commoninyearinfo = null;
	$(function() {
		$("#pageloading").hide();
		$("#phone").val(parent.document.getElementById("temphone").value);
		document.getElementById("cap").SwitchWatchOnly();
		document.getElementById("cap").start();
		setTimeout(
				"document.getElementById('cap').selectRect(0.3,0.1,0.7,0.9);",
				1000);
	});
	
	function checkPhone()
	{
		if($("#phone").val()=="")
		{
			return;
		}
		if(isMobil($("#phone").val())==false)
		{
			$.ligerDialog.error("手机号码格式不正确");
			$("#phone").val("");
			return;
		}
	}

	function loadPhone() {
		if ($("#curPhone").val() == "") {
			return;
		} else {
			$.getJSON(requestURL + "/cc011/loadPhone.action", {
				"strPhone" : $("#curPhone").val()
			}, function(data) {
				if (data.strMessage != "") {
					$.ligerDialog.error(data.strMessage);
					$("#curPhone").val("");
				} else {

					document.getElementById("cap").clear();
					document.getElementById("cap").loadBase64String(
							data.yearcardinof.img);
					parent.commoninfodivdetial_yearinfo.options.data = $
							.extend(true, {}, {
								Rows : data.lsYearcarddetals,
								Total : data.lsYearcarddetals.length
							});
					parent.commoninfodivdetial_yearinfo.loadData(true);
				}
			});
		}
	}

	function Camp(obj) {
		if (obj.value == "拍照") {
			document.getElementById("cap").cap();
			document.getElementById("cap").cutSelected();
			$("#strImage").val(document.getElementById("cap").jpegBase64Data);
			//document.getElementById("cap").savetoFile("d://test.jpg");
			obj.value = "重拍";
		} else {
			//document.getElementById("cap").stop();
			document.getElementById("cap").clear();
			document.getElementById("cap").start();
			$("#strImage").val("");
			//document.getElementById("cap").resizePicture(1024,768);
			setTimeout(
				"document.getElementById('cap').selectRect(0.3,0.1,0.7,0.9);",
				1000);
			obj.value = "拍照";
		}
	}

	function f_onCheckRow(checked, data, rowid) {
		if (data.monthnum <= 0) {
			$.ligerDialog.error("选择的疗程本月没有消费次数了，请下个月在消费");
			return false;
		}
		return true;
		/*if (checked) 
			addIdx(rowid);
		else 
		   	removeIdx(rowid);*/
	}
	
	function saveYearCardInfo()
	{
		if($("#phone").val()=="")
		{
			$.ligerDialog.error("请填写手机号码");
			return;
		}
		if($("#name").val()=="")
		{
			$.ligerDialog.error("请填写顾客名字");
			return;
		}
		if($("#strImage").val()=="")
		{
			$.ligerDialog.error("请先拍照");
			return;
		}
		$("#save").attr("disabled",true);
		$.post(requestURL+"/cc018/saveYearCardInfo.action",$("#yearfrm").serialize(),function(data){
		if(data.strMessage!="")
		{
			$.ligerDialog.error(data.strMessage);
			$("#save").attr("disabled",false);
		}
		else
		{
			//printBill();
			alert("保存成功");
			parent.document.getElementById("temphone").onchange();
			parent.addYardDialog.close();
			//location.reload();
		}
	},"json");
	}
	
	function isMobil(s) {
	var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
	if (!patrn.exec(s))
		return false;
	return true;
	}
	
	function hotKeyOfSelf(key) {
		if (key == 13)// 回车
		{
			window.event.keyCode = 9; // tab
			window.event.returnValue = true;
		}
	}
	function destory_activex(){
		var active_object_id='cap'; //activex的控件id
		var activex_obj=document.getElementById(active_object_id);
		var parent_element=activex_obj.parentElement; //找到控件的父元素
		//删除activex父元素的所有子元素 
		while (parent_element.children.length>0){
			parent_element.removeChild(parent_element.children[0]);
		}
	}
</script>
<style type="text/css">
body {
	font-size: 12px;
}

.l-table-edit {
	
}

.l-table-edit-td {
	padding: 4px;
}

.l-button-submit,.l-button-test {
	width: 80px;
	float: left;
	margin-left: 10px;
	padding-bottom: 2px;
}

.l-verify-tip {
	left: 230px;
	top: 120px;
}

.scr_con {
	position: relative;
	width: 298px;
	height: 98%;
	border: solid 1px #ddd;
	margin: 0px auto;
	font-size: 12px;
}

#dv_scroll {
	position: absolute;
	height: 98%;
	overflow: hidden;
	width: 298px;
}

#dv_scroll .Scroller-Container {
	width: 100%;
}

#dv_scroll_bar {
	position: absolute;
	right: 0;
	bottom: 30px;
	width: 14px;
	height: 150px;
	border-left: 1px solid #B5B5B5;
}

#dv_scroll_bar .Scrollbar-Track {
	position: absolute;
	left: 0;
	top: 5px;
	width: 14px;
	height: 150px;
}

#dv_scroll_bar .Scrollbar-Handle {
	position: absolute;
	left: -7px;
	bottom: 10;
	width: 13px;
	height: 29px;
	overflow: hidden;
	background: url('<%=ContextPath%>/common/ligerui/images/srcoll.gif')
		no-repeat;
	cursor: pointer;
}

#dv_scroll_text {
	position: absolute;
}
</style>
</head>
<body onunload="destory_activex()" style="padding:6px; overflow:hidden;">
	<form id="yearfrm">
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<div style="height: 30px;" id="layout1" class="l-layout"
		ligeruiid="layout1">
		<div class="l-layout-left"
			style="left: 0px; width: 384px; top: 35px; height: 288px;">
			<div position="left" class="l-layout-content">
				<object classid="clsid:34681DB3-58E6-4512-86F2-9477F1A9F3D8"
					id="cap" width="384" height="288"
					codebase="../cabs/ImageCapOnWeb.cab#version=2,0,0,0">
					<param name="Font" value="宋体">
						<param name="licenseMode" value="3">
							<param name="key1"
								value="pR8GeRxKoiPWLbq/jUZyDCGRe+YyReKgT9i8Ho4PBkyGMl5fz3MSMg3gceSvovo+RyQ0Qrg5RAhcdcENmCBu7xgT0BwDAvQ3DBS3rokAfuBq9g==">
								<param name="key2"
									value="IY0dXhRqR5PQYBW68UEkeNAS609sHmtcGFhwu8zRUpJwz1XqUArZtRY0QLctXk2IUkQ+Fk9UHT78XPpUNnuqlny8O0lpfnP1R6hgc6BbrbGpbCjQAsTWvgt92L2qK/0cNRiQ1furHp2ZcyYJfqLVitJ7u18VLmEjtYAF+b0ekJSIjYoABvuxbhYixYKu8sdQLV+LctQFUkY=">
				</object>
			</div>
		</div>

		<div style="left: 390px; top: 35px; width: 148px; height: 288px;"
			class="l-layout-center">
			<div style="height: 453px;" class="l-layout-content" title=""
				position="center">
				<table width="100%" height="288px" border="0" cellspacing="1"
					cellpadding="0" style="font-size:12px;line-height:20px;">
					<tr align="center">
						<td><input id="readCard" class="l-button l-button" value="拍照"
							type="button" onclick="Camp(this)">
						</td>
					</tr>
					<tr align="center">
						<td><input id="save" class="l-button l-button" style="width:100px"
							value="保存年卡资料" type="button" onclick="saveYearCardInfo()">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div style="top: 0px; height: 30px;" class="l-layout-top">
			<div class="l-layout-content" position="top">
				<table width="100%" border="0" cellspacing="1" cellpadding="0"
					style="font-size:12px;line-height:20px;">
					<tr>
						<td>姓名:</td>
						<td>
							<input name="yearcardinof.name" id="name" size="10" />
							<input name="yearcardinof.img" id="strImage" type="hidden"/>
						</td>
						<td>手机号码:</td>
						<td><input name="yearcardinof.phone" id="phone" size="10" maxlength="11" onchange="checkPhone()"/>
						</td>
						<td>身份证号码:</td>
						<td><input name="yearcardinof.cid" id="cid" size="10" />
						</td>
					</tr>
				</table>
			</div>
		</div>

	</div>
	</form>
</body>
</html>
<script language="JavaScript">
	var requestURL = "<%=request.getContextPath()%>";
	</script>
