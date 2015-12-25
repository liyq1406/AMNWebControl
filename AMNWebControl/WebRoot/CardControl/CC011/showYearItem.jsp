<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
			<%
				response.setHeader("Pragma", "No-cache");

				response.setHeader("Cache-Control", "no-cache");

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
	var f_detailPanel=null;
	var f_callback=null;
	var dialogxf=null;
	$(function() {
		try {
			parent.commoninfodivdetial_yearinfo = $("#commoninyearinfo")
					.ligerGrid({
						columns : [ {
							display : '名称',
							name : 'itemname',
							width : 150,
							align : 'left'
						}, {
							display : '有效时间',
							name : 'validate',
							width : 120,
							align : 'center',
							render: function (item){
								if(item.num*1==item.synum*1){
									return "未使用";
								}else if(item.itemstate==0 && new Date(item.validate.replace(/\-/g, "\/")+" 23:59:59") < new Date()){
									return "已过期";
								}
								return item.validate;
							}
						}, {
							hide : true,
							display : '本月可消费次数',
							name : 'monthnum',
							width : 100,
							align : 'center'
						}, {
							display : '总剩余次数',
							name : 'synum',
							width : 60,
							align : 'center'
						},
						{
							display : '备注',
							name : 'remarks',
							width : 180,
							align : 'center'
						}, 
						{
							hide : true,
							name : 'num',
							width : 1
						}, {
							hide : true,
							name : 'itemno',
							width : 1
						}, {
							hide : true,
							name : 'iteminid',
							width : 1
						}, {
							hide : true,
							name : 'phone',
							width : 1
						}, 
						{
							display:'状态',
							name : 'itemstate',
							width : 50,
							render: function (item)
			              	{
			              		if(item.itemstate==1)
			              		{
			              			return '停用';
			              		}
			              		else
			              		{
			              			return '正常';
			              		}
			                } 
						},
						{
							hide : true,
							name : 'amt',
							width : 1
						}
						//{ display: '取用次数', 	name: 'costcount', 			width:60,align: 'center',editor: { type: 'int' }}	                
						],
						pageSize : 25,
						data :null,
						width : '750%',
						height : 450,
						onBeforeCheckRow : f_onCheckRow,
						enabledEdit : false,
						checkbox : true,
						rownumbers : false,
						usePager : false,
						detail: { onShowDetail: showYearinfo}
					});
			$("#pageloading").hide();
			document.getElementById("cap").SwitchWatchOnly();
		} catch (e) {
			alert(e.message);
		}
	});
	
	function showYearinfo(row, detailPanel,callback)
	{
	
		dialogxf=$.ligerDialog.open({height:600,width: 800,title : '年卡消费记录',url: 'showYearItemXF.jsp',showMax: false,showToggle: false,showMin: 
			false,isResize: true,slide: false,data: {strYearInid: row.iteminid}});
		parent.commoninfodivdetial_yearinfo.loadData(true);
		/*f_detailPanel=detailPanel;
		f_callback=callback;
		try
		{
			$.getJSON(requestURL + "/cc011/lsCardtransactionhistories.action",{"strYearInid":row.iteminid},function(lsdata){
				if(lsdata.lsCardtransactionhistories!=null && lsdata.lsCardtransactionhistories.length>0)
				{
					var detailGrid = document.createElement('div'); 
					  $(f_detailPanel).append(detailGrid);
			            $(detailGrid).css('margin',10).ligerGrid({
			                columns:
			                            [
			                            { display: '公司', name: 'transactioncompid',width: 70},
			                            { display: '日期', name: 'transactiondate',width: 100 },
			                            { display: '消费次数', name: 'ccount' ,width: 100 },
			                            { display: '第一销售', name: 'firstempid' ,width: 130 },
			                            { display: '第二销售', name: 'secondempid' ,width: 40 },
			                            { display: '第三销售', name: 'thirthempid' ,width: 60 }
			                            ], isScroll: false, showToggleColBtn: false, width: '500', 
			                 data:{Rows: lsdata.lsCardtransactionhistories,Total: lsdata.lsCardtransactionhistories.length}
			                 , showTitle: true, rownumbers:true,columnWidth: 100
			                 , onAfterShowData: f_callback,frozen:false,usePager:false
			            });
				}
			});
		}
		catch(e)
		{
			alert(e.message);
		}*/
	}

	function loadPhone() {
		if ($("#curPhone").val() == "") {
			return;
		} else {
			$.getJSON(requestURL + "/cc011/loadPhone.action", {
				"strPhone" : $("#curPhone").val(),"_random": Math.random()
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

	function f_onCheckRow(checked, data, rowid) {
		if (data.monthnum <= 0) {
			$.ligerDialog.error("选择的疗程本月没有消费次数了，请下个月在消费");
			return false;
		}
		if(checked && data.itemstate==1){
			$.ligerDialog.error("您的年卡已停用，请先激活！");
			return true;
		}
		/*else{
			if(data.num*1!=data.synum*1 && new Date(data.validate.replace(/\-/g, "\/")) < new Date()){
				$.ligerDialog.error("您的年卡已过期，不能消费！");
				return false;
			}
		}*/
		return true;
		/*if (checked) 
			addIdx(rowid);
		else 
		   	removeIdx(rowid);*/
	}
	
	function updYearCardInfo()
	{
		var rows=parent.commoninfodivdetial_yearinfo.getCheckedRows();
		var i=0,count=rows.length;
    	$(rows).each(function()
		{
    		i++;
			$.getJSON(requestURL + "/cc011/updYearCardInfo.action",{"strYearInid":this.iteminid,"_random": Math.random()},function(data){
				if(data.strMessage!="")
				{
					$.ligerDialog.error(data.strMessage);
				}
				if(i==count){//更新后重新加载
					parent.commoninfodivdetial_yearinfo.options.data=null;
					parent.commoninfodivdetial_yearinfo.loadData(true);
					loadPhone();
				}
			});
		});
		//parent.commoninfodivdetial_yearinfo.options.data=null;
		//parent.commoninfodivdetial_yearinfo.loadData(true);
		//parent.commoninfodivdetial_yearinfo.reload();
	}
	
	
	function updYearCardJH()
	{
		var rows=parent.commoninfodivdetial_yearinfo.getCheckedRows();
		var i=0,count=rows.length;
    	$(rows).each(function()
		{
    		i++;
			$.getJSON(requestURL + "/cc011/updYearCardJH.action",{"strYearInid":this.iteminid,"_random": Math.random()},function(data){
				if(data.strMessage!="")
				{
					$.ligerDialog.error(data.strMessage);
				}
				if(i==count){//更新后重新加载
					parent.commoninfodivdetial_yearinfo.options.data=null;
					parent.commoninfodivdetial_yearinfo.loadData(true);
					loadPhone();
				}
			});
		});
		
	}
	//过期美容年卡剩余4次以下可以延期一个月,4次及4次以上可以延期两个月
	//过期美发年卡小于5次不能延期，5次以上延期一个月
	function delayYearCard()
	{
		var rows=parent.commoninfodivdetial_yearinfo.getCheckedRows();
		var i=0,count=rows.length,flag=false;
		$(rows).each(function(){
			if(this.itemno.substring(0,1)=="3" && this.synum*1<5){
    			flag=true;
    			return false;
    		}
		});
		if(flag){
			$.ligerDialog.warn("美发年卡剩余次数小于5次不能延期！");
			return;
		}
    	$(rows).each(function()
		{
    		i++;
			$.getJSON(requestURL + "/cc011/delayYearCard.action",{"strYearInid":this.iteminid,"_random": Math.random()},function(data){
				if(data.strMessage!="")
				{
					$.ligerDialog.error(data.strMessage);
				}
				if(i==count){//更新后重新加载
					parent.commoninfodivdetial_yearinfo.options.data=null;
					parent.commoninfodivdetial_yearinfo.loadData(true);
					loadPhone();
				}
			});
		});
	}
	
	function openByName()
	{
		$.ligerDialog.open({ height: 500, url: requestURL+'/CardControl/CC011/showYearItemByName.jsp', width: 500, showMax: false, showToggle: false,allowClose:true, showMin: false, isResize: false, title: '年卡按姓名查询' });
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
<body style="padding:6px; overflow:hidden;">
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<div style="height: 30px;" id="layout1" class="l-layout"
		ligeruiid="layout1">
		<div class="l-layout-left"
			style="left: 0px; width: 200px; top: 35px; height: 288px;">
			<div position="left" class="l-layout-content">
				<object classid="clsid:34681DB3-58E6-4512-86F2-9477F1A9F3D8"
					id="cap" width="100%" height="270"
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

		<div style="left: 205px; top: 35px; width: 620px; height: 450px;"
			class="l-layout-center">
			<div style="height: 453px;" class="l-layout-content" title=""
				position="center">
				<div id="commoninyearinfo" style="margin:0; padding:0"></div>
			</div>
		</div>
		<div style="top: 0px; height: 30px;" class="l-layout-top">
			<div class="l-layout-content" position="top">
				手机号码:<input name="curPhone" id="curPhone" /> <input id="Button1"
					class="l-button l-button" value="查询" type="button"
					onclick="loadPhone()">
					
					<input id="Button2" style="margin-left:25px"
					class="l-button l-button" value="停用" type="button"
					onclick="updYearCardInfo()">
					
					<input id="Button3"  style="margin-left:25px"
					class="l-button l-button" value="激活" type="button"
					onclick="updYearCardJH()">
					<font color="red">年卡只能暂停一次，请慎用</font>
					
					<input id="Button2" style="margin-left:25px"
					class="l-button l-button" value="延期" type="button"
					onclick="delayYearCard()">
					
					<input id="Button2" style="margin-left:30px;width:100px"
					class="l-button l-button" value="按姓名查找年卡" type="button"
					onclick="openByName()">
			</div>
		</div>

	</div>
</body>
</html>
<script language="JavaScript">
	var requestURL = "<%=request.getContextPath()%>";
	</script>
