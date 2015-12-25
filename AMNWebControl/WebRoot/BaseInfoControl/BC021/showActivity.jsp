<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		var contextURL="${pageContext.request.contextPath}";
		var useData=[{choose:1, text:'正常提成'}, {choose:2, text:'设定提成'}, {choose:3, text:'原价卡提'}];//提成方式
		$(function() {
			try {
				$("#flowdiv").css("height", parent.showligerDialog.get("height")-75);
				var itemGrid = $("#itemGrid").ligerGrid({
					columns: [ 
					   {display:'项目编号', name:'activno', width : 120, align:'left'},
					   {display:'项目名称', name:'activname', width : 150,align:'left'},
					   {display:'套数', name:'activcount', width : 80,align:'left'}
					],
					pageSize:20, data:null, width:400, height:150,usePager:false
				});
				var itemGridClassify = $("#itemGridClassify").ligerGrid({
					columns: [ 
					   {display:'分类编号', name:'activno', width : 150, align:'left'},
					   {display:'分类名称', name:'activname', width : 200,align:'left'}
					],
					pageSize:20, data:null, width:'400', height:100,usePager:false
				});
				var productGrid = $("#productGrid").ligerGrid({
					columns: [ 
					   {display:'产品编号', name:'activno', width : 120, align:'left'},
					   {display:'产品名称', name:'activname', width : 150,align:'left'},
					   {display:'数量', name:'activcount', width : 80,align:'left'}
					],
					pageSize:20, data:null, width:400, height:150,usePager:false
				});
				var productGridClassify = $("#productGridClassify").ligerGrid({
					columns: [ 
					   {display:'分类编号', name:'activno', width : 150, align:'left'},
					   {display:'分类名称', name:'activname', width : 200,align:'left'}
					],
					pageSize:20, data:null, width:'400', height:100,usePager:false
				});
				var giveItemGrid = $("#giveItemGrid").ligerGrid({
					columns: [ 
					   {display:'项目编码', name:'activno', width : 120, align:'left'},
					   {display:'项目名称', name:'activname', width : 150,align:'left'},
					   {display:'单次金额', name:'onecountprice', width : 80,align:'left'},
					   {display:'次数', name:'givecount', width : 80,align:'left'},
					   {display:'合计', name:'givetotal', width : 80,align:'left'},
					   {display:'提成方式', name:'takeway', width : 80,align:'left',
						   render: function (item){
							   var title="";
							   $(useData).each(function(){
								   if (this.choose == item.takeway){
			                        	title = this.text;
			                        	return false;
			                       }
							   });
		                	   return title;
			                }
					   },
					   {display:'提成金额', name:'takeamt', width : 80,align:'left'}
					],
					pageSize:20, data:null, width:700, height:200,usePager:false
				});
				var giveItemOneGrid = $("#giveItemOneGrid").ligerGrid({
					columns: [ 
						{display:'项目编码', name:'activno', width : 120, align:'left'},
						{display:'项目名称', name:'activname', width : 150,align:'left'},
						{display:'单次金额', name:'onecountprice', width : 80,align:'left'},
						{display:'次数', name:'givecount', width : 80,align:'left'},
						{display:'合计', name:'givetotal', width : 80,align:'left'},
						{display:'提成方式', name:'takeway', width : 80,align:'left',
						   editor:{type:'select', data:useData, valueField:'choose'},
						   render: function (item){
							   var title="";
							   $(useData).each(function(){
								   if (this.choose == item.takeway){
			                        	title = this.text;
			                        	return false;
			                       }
							   });
		                	   return title;
		                	}
						},
						{display:'提成金额', name:'takeamt', width : 80,align:'left'}
					],
					pageSize:20, data:null, width:700, height:200,usePager:false
				});
				var giveProductGrid = $("#giveProductGrid").ligerGrid({
					columns: [ 
					   {display:'产品编号', name:'activno', width : 120, align:'left'},
					   {display:'产品名称', name:'activname', width : 150,align:'left'},
					   {display:'数量', name:'givecount', width : 80,align:'left'}
					],
					pageSize:20, data:null, width:400, height:150,usePager:false
				});
				var params = {strCurCompId:parent.strCurCompId, _random:Math.random(),
								activinid:parent.showligerDialog.get('_activinid')};
				var url = contextURL+"/bc021/loadAll.action"; 
				$.post(url, params, function(data){
					var list = data.objSet;
					if(list != null && list.length>0){
						var mactivity = list[0];
						var activtype = mactivity.activtype;
						$("#activityname").val(checkNull(mactivity.activname));
						$("#typebox"+ activtype).attr('checked',true);
						if(activtype=="1"){
							$("#cardamt").val(mactivity.activamt);
						}else if(activtype=="2"){
							$("#itemorand"+ mactivity.activorand).attr('checked',true);
							$("#itemamt").val(mactivity.activamt);
							$("#itemcount").val(mactivity.activcount);
							$(list[1]).each(function(i, row){
								if(row.activtype="1"){
									itemGrid.addRow(row);
								}else{
									itemGridClassify.addRow(row);
								}
							});
						}else if(activtype=="3"){
							$("#proorand"+ mactivity.activorand).attr('checked',true);
							$("#proamt").val(mactivity.activamt);
							$("#procount").val(mactivity.activcount);
							$(list[1]).each(function(i, row){
								if(row.activtype="1"){
									productGrid.addRow(row);
								}else{
									productGridClassify.addRow(row);
								}
							});
						}
						$("#giveorand"+ mactivity.activstate).attr('checked',true);
						if(list[2]!=null && list[2].length>0){
							$(list[2]).each(function(i, row){
								if(row.activtype=="1"){
									giveItemGrid.addRow(row);
								}else if(row.activtype=="2"){
									giveItemOneGrid.addRow(row);
								}else{
									giveProductGrid.addRow(row);
								}
							});
						}
						$("#pageloading").hide();
					}
				}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
			} catch (e) {
				alert(e.message);
			}
		});
	</script>
</head>
<body style="padding:4px; overflow:hidden;">
	<div style="height: 100%;" id="layout1" class="l-layout" ligeruiid="layout1">
		<div style="top: 0px;" class="l-layout-top">
			<div class="l-layout-content" position="top" style="text-align: center;padding-top: 5px;">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
					<tr><td valign="top">
						<div id="flowdiv" style="width:100%;clear:both;overflow:auto;overflow-x: hidden;font-size:12px;">
							<form name="dataForm" method="post" id="dataForm">
								<table id="dataTable" width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height: 30px;">
									<tr>
										<td>&nbsp;&nbsp;活动名称&nbsp;&nbsp;<input type="text" name="activityname" id="activityname" style="width:260;"/></td>
									</tr>
									<tr style="height: 30px;line-height: 30px;">	
										<td style="width: 40%;"><input type="radio" name="typebox" id="typebox1"/>&nbsp;&nbsp;卡异动</td>
										<td valign="top" rowspan="6">
											<div id="giveItemGrid" style="margin:0 0 0 10px; padding:0;"></div>
											<div id="giveItemOneGrid" style="margin:5px 0 0 10px; padding:0;"></div>
											<div style="width: 100%;padding-left: 10px;margin: 20px 0;text-align: center;">
												<input type="radio" name="giveorand" id="giveorand1"/>&nbsp;或
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="radio" name="giveorand" id="giveorand2"/>&nbsp;且
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="radio" name="giveorand" id="giveorand3"/>&nbsp;只送产品
											</div>
											<div id="giveProductGrid" style="margin:0 0 0 10px; padding:0;"></div>
										</td>
									</tr>
									<tr style="text-align: center;">
										<td>金额&nbsp;&nbsp;<input type="text" name="cardamt" id="cardamt" style="width:200;"/></td>
									</tr>
									<tr style="height: 30px;line-height: 30px;">		
										<td><input type="radio" name="typebox" id="typebox2"/>&nbsp;&nbsp;购买疗程</td>
									</tr>
									<tr><td>
										<table id="itemTable" width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
											<tr>
												<td><div id="itemGrid" style="margin:0; padding:0"></div></td>
												<td rowspan="2">
													<table cellspacing="1" cellpadding="0" style="font-size:12px;width: 65px;
														border: 1px solid #bed5f3;height:260px;text-align: center;">
														<tr style="vertical-align: bottom;"><td>金额</td></tr>
														<tr><td><input type="text" name="itemamt" id="itemamt" style="width:60;"/></td></tr>
														<tr><td><input type="radio" name="itemorand" id="itemorand1"/>&nbsp;或</td></tr>
														<tr><td><input type="radio" name="itemorand" id="itemorand2"/>&nbsp;且</td></tr>
														<tr style="vertical-align: bottom;"><td>套数</td></tr>
														<tr><td><input type="text" name="itemcount" id="itemcount" style="width:60;"/></td></tr>
													</table>
												</td>
											</tr>
											<tr><td><div id="itemGridClassify" style="margin:0; padding:0"></div></td></tr>
										</table>
									</td></tr>
									<tr style="height: 30px;line-height: 30px;">		
										<td><input type="radio" name="typebox" id="typebox3"/>&nbsp;&nbsp;购买产品</td>
									</tr>
									<tr><td>
										<table id="productTable" width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
											<tr>
												<td><div id="productGrid" style="margin:0; padding:0"></div></td>
												<td rowspan="2">
													<table cellspacing="1" cellpadding="0" style="font-size:12px;width: 65px;
														border: 1px solid #bed5f3;height:260px;text-align: center;">
														<tr style="vertical-align: bottom;"><td>金额</td></tr>
														<tr><td><input type="text" name="proamt" id="proamt" style="width:60;"/></td></tr>
														<tr><td><input type="radio" name="proorand" id="proorand1"/>&nbsp;或</td></tr>
														<tr><td><input type="radio" name="proorand" id="proorand2"/>&nbsp;且</td></tr>
														<tr style="vertical-align: bottom;"><td>数量</td></tr>
														<tr><td><input type="text" name="procount" id="procount" style="width:60;"/></td></tr>
													</table>
												</td>
											</tr>
											<tr><td><div id="productGridClassify" style="margin:0; padding:0"></div></td></tr>
										</table>
									</td></tr>
								</table>
							</form>
						</div>
					</td></tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>