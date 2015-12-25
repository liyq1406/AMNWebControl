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
		
			$("#strFromDate").ligerDateEditor({ labelWidth: 0, labelAlign: 'right',width:'2' });
			commoninyearinfo=commoninfodivdetial_yearinfo = $("#commoninyearinfo")
					.ligerGrid({
						columns : [ {
							display : '起始条码编号',
							name : 'frombarcode',
							width : 150,
							align : 'left'
						}, {
							display : '结束条码编号',
							name : 'tobarcode',
							width : 120,
							align : 'center'
						},{
							display : '生产日期',
							name : 'producedate',
							width : 120,
							align : 'center'
						},{
							display : '过期日期',
							name : 'produceenddate',
							width : 120,
							align : 'center'
						}
						//{ display: '取用次数', 	name: 'costcount', 			width:60,align: 'center',editor: { type: 'int' }}	                
						],
						pageSize : 25,
						data :null,
						width : '750%',
						height : 450,
						onBeforeCheckRow : f_onCheckRow,
						enabledEdit : false,
						//checkbox : true,
						rownumbers : false,
						usePager : false
						//detail: { onShowDetail: showYearinfo}
					});
			$("#pageloading").hide();
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

	function checkBarInfo() {
		var fromBarNo="";
		var toBarNo="";
		if($("#barno").val()!="")
		{
			fromBarNo=$("#barno").val();
			toBarNo=$("#barno").val();
		}
		if($("#fromBarNo").val()!="" && $("#toBarNo").val()!="")
		{
			fromBarNo=$("#fromBarNo").val();
			toBarNo=$("#toBarNo").val();
		}		
		if($("#strFromDate").val()=="")
		{
			$.ligerDialog.error("请选择消费日期");
			return;
		}
		
		if(fromBarNo=="")
		{
			$.ligerDialog.error("请填写条码编号");
			return;
		}
		
		$.getJSON(requestURL+"/ic015/checkBarInfo.action",{"strGoodsBarNo":fromBarNo,"strToGoodsBarNo":toBarNo},function(data){
			if(data.bRet)
			{
				var row = commoninyearinfo.getSelectedRow();
				if($("#datetype").val()=="1")
				{
					commoninyearinfo.addRow({
					frombarcode:fromBarNo,
					tobarcode:toBarNo,
					producedate:$("#strFromDate").val()
					},row, false);
				}
				else
				{
					commoninyearinfo.addRow({
					frombarcode:fromBarNo,
					tobarcode:toBarNo,
					produceenddate:$("#strFromDate").val()
					},row, false);
				}
				
			}
			else
			{
				$.ligerDialog.error("你输入的条码不存在，请重新输入");
			}
		});
	}
	
	function post()
	{
		if(commoninyearinfo.rows.length<1)
		{
			$.ligerDialog.error("请先往列表里面添加然后在保存");
			return;
		}
		var curJSON="";
		var curjosnparam="";
		 var rows=commoninyearinfo.rows;
    	 $(rows).each(function()
    	 {
    		curjosnparam=JSON.stringify(this);
 		 	curjosnparam=curjosnparam.replace("%","");
 		 	curjosnparam=curjosnparam.replace("#","");
 		 	if(curJSON=="")
 		 		curJSON+=curjosnparam;
 		 	else
 		 		curJSON+=","+curjosnparam;
    	 });
    	 curJSON="["+curJSON+"]";
    	 $.getJSON(requestURL+"/ic015/post.action",{"strParams":curJSON},function(data){
    	 	if(data.bRet)
    	 	{
    	 		alert("保存成功");
    	 		window.location.reload();	
    	 	}
    	 	else
    	 	{
    	 		$.ligerDialog.error("保存失败");
    	 	}
    	 });
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
	
	function updYearCardInfo()
	{
		var rows=parent.commoninfodivdetial_yearinfo.getCheckedRows();
    	$(rows).each(function()
		{
			$.getJSON(requestURL + "/cc011/updYearCardInfo.action",{"strYearInid":this.iteminid},function(data){
				if(data.strMessage!="")
				{
					$.ligerDialog.error(data.strMessage);
				}
			});
		});
		parent.commoninfodivdetial_yearinfo.options.data=null;
		parent.commoninfodivdetial_yearinfo.loadData(true);
		loadPhone();
		//parent.commoninfodivdetial_yearinfo.options.data=null;
		//parent.commoninfodivdetial_yearinfo.loadData(true);
		//parent.commoninfodivdetial_yearinfo.reload();
	}
	
	
	function updYearCardJH()
	{
		var rows=parent.commoninfodivdetial_yearinfo.getCheckedRows();
    	$(rows).each(function()
		{
			$.getJSON(requestURL + "/cc011/updYearCardJH.action",{"strYearInid":this.iteminid},function(data){
				if(data.strMessage!="")
				{
					$.ligerDialog.error(data.strMessage);
				}
			});
		});
		parent.commoninfodivdetial_yearinfo.options.data=null;
		parent.commoninfodivdetial_yearinfo.loadData(true);
		loadPhone();
		
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
		<div style="left: 1px; top: 101px; width: 620px; height: 450px;"
			class="l-layout-center">
			<div style="height: 453px;" class="l-layout-content" title=""
				position="center">
				<div id="commoninyearinfo" style="margin:0; padding:0"></div>
			</div>
		</div>
		<div style="top: 0px; height: 100px;" class="l-layout-top">
			<div class="l-layout-content" position="top">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
					<tr>
						<td width="400">
							单个条码:<input name="barno" id="barno" /> 
						</td>
						<td width="500">
							日期类型:<select id="datetype" name="datetype">
									<option value="1">生产日期</option>
									<option value="2">过期日期</option>
								  </select>
						</td>
						<td>生产日期:<input id="strFromDate" type="text" size="10"/></td>
						<td width="100">
							<input id="Button1" class="l-button l-button" value="保存" type="button" onclick="post()">
						</td>
					</tr>
					<tr>
						<td width="400">
							连续条码:<input name="fromBarNo" id="fromBarNo" />
						</td>
						<td width="500"><input name="toBarNo" id="toBarNo" /></td>
						<td>&nbsp;</td>
						<td width="100"><input id="Button1" class="l-button l-button" value="添加" type="button" onclick="checkBarInfo()"></td>
					</tr>
				</table>
				
			</div>
		</div>

	</div>
</body>
</html>
<script language="JavaScript">
	var requestURL = "<%=request.getContextPath()%>";
	</script>
