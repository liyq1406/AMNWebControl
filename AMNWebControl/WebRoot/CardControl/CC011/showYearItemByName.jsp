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
			commoninyearinfo = $("#commoninyearinfo")
					.ligerGrid({
						columns : [ {
							display : '会员姓名',
							name : 'name',
							width : 150,
							align : 'left'
						}, {
							display : '手机号码',
							name : 'phone',
							width : 120,
							align : 'center'
						}
						//{ display: '取用次数', 	name: 'costcount', 			width:60,align: 'center',editor: { type: 'int' }}	                
						],
						pageSize : 25,
						data :null,
						width : '750%',
						height : 450,
						//onBeforeCheckRow : f_onCheckRow,
						enabledEdit : false,
						checkbox : false,
						rownumbers : false,
						usePager : false
						//detail: { onShowDetail: showYearinfo}
					});
			$("#pageloading").hide();
			//document.getElementById("cap").SwitchWatchOnly();
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

	function loadCardName() {
		if ($("#cardName").val() == "") {
			return;
		} else {
			$.post(requestURL + "/cc011/loadYearInfo.action", {
				"strCurItemName" : $("#cardName").val()
			}, function(data) {
				if (data.strMessage != "") {
					$.ligerDialog.error(data.strMessage);
					$("#cardName").val("");
					return;
				}
				commoninyearinfo.options.data = $
							.extend(true, {}, {
								Rows : data.lsYearCard,
								Total : data.lsYearCard.length
							});
				commoninyearinfo.loadData(true);	
				
			});
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
<body style="padding:6px; overflow:hidden;">
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<div style="height: 30px;" id="layout1" class="l-layout"
		ligeruiid="layout1">
		<div class="l-layout-left"
			style="left: 0px; width: 0; top: 0px; height: 0px;">
			<div position="left" class="l-layout-content">
				
			</div>
		</div>

		<div style="left: 0px; top: 35px; width: 450px; height: 450px;"
			class="l-layout-center">
			<div style="height: 453px;" class="l-layout-content" title=""
				position="center">
				<div id="commoninyearinfo" style="margin:0; padding:0"></div>
			</div>
		</div>
		<div style="top: 0px; height: 30px;" class="l-layout-top">
			<div class="l-layout-content" position="top">
				会员姓名:<input name="cardName" id="cardName" /> <input id="Button1"
					class="l-button l-button" value="查询" type="button"
					onclick="loadCardName()">
			</div>
		</div>

	</div>
</body>
</html>
<script language="JavaScript">
	var requestURL = "<%=request.getContextPath()%>";
	</script>
