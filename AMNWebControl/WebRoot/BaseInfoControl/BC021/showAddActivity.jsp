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
				$("#flowdiv").css("height", parent.showAddDialog.get("height")-75);
				parent.itemGrid = $("#itemGrid").ligerGrid({
					columns: [ 
					   {display:'项目编号', name:'activno', width : 120, align:'left', editor:{type:'text',onChanged:loadItem}},
					   {display:'项目名称', name:'activname', width : 150,align:'left'},
					   {display:'套数', name:'activcount', width : 80,align:'left', editor:{type:'int',onChanged:checkItemNum}},
					   {name:'activtype', width:1, hide:'true'}
					],
					enabledEdit:true, pageSize:20, data:null, width:400, height:150,usePager:false,
					toolbar: { items: [
		   	            {text:'添加', click:addNewRow, img:contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif'},
		   	            {text:'刷新', click:refreshGrid, img:contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif'}
		   	       	]}
				});
				parent.itemGrid.addRow({activtype:1});
				parent.itemGridClassify = $("#itemGridClassify").ligerGrid({
					columns: [ 
					   {display:'分类编号', name:'activno', width : 150, align:'left', editor:{type:'text',onChanged:loadClassifyItem}},
					   {display:'分类名称', name:'activname', width : 200,align:'left'},
					   {name:'activtype', width:1, hide:'true'}
					],
					enabledEdit:true, pageSize:20, data:null, width:'400', height:100,usePager:false
				});
				parent.itemGridClassify.addRow({activtype:2});
				parent.productGrid = $("#productGrid").ligerGrid({
					columns: [ 
					   {display:'产品编号', name:'activno', width : 120, align:'left', editor:{type:'text', onChanged:loadGoods}},
					   {display:'产品名称', name:'activname', width : 150,align:'left'},
					   {display:'数量', name:'activcount', width : 80,align:'left', editor:{type:'int', onChanged:checkGoodsNum}},
					   {name:'activtype', width:1, hide:'true'}
					],
					enabledEdit:true, pageSize:20, data:null, width:400, height:150,usePager:false,
					toolbar: { items: [
		   	            {text:'添加', click:addProductRow, img:contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif'},
		   	            {text:'刷新', click:refreshGoodsGrid, img:contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif'}
		   	       	]}
				});
				parent.productGrid.addRow({activtype:1});
				parent.productGridClassify = $("#productGridClassify").ligerGrid({
					columns: [ 
					   {display:'分类编号', name:'activno', width : 150, align:'left', editor:{type:'text',onChanged:loadClassifyGoods}},
					   {display:'分类名称', name:'activname', width : 200,align:'left'},
					   {name:'activtype', width:1, hide:'true'}
					],
					enabledEdit:true, pageSize:20, data:null, width:'400', height:100,usePager:false
				});
				parent.productGridClassify.addRow({activtype:2});
				parent.giveItemGrid = $("#giveItemGrid").ligerGrid({
					columns: [ 
					   {display:'项目编码', name:'activno', width : 120, align:'left', editor:{type:'text',onChanged:loadGiveItem}},
					   {display:'项目名称', name:'activname', width : 150,align:'left'},
					   {display:'单次金额', name:'onecountprice', width : 80,align:'left', editor:{type:'float', onChanged:totalItemAmt}},
					   {display:'次数', name:'givecount', width : 80,align:'left', editor:{type:'int', onChanged:totalItemAmt}},
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
					   {display:'提成金额', name:'takeamt', width : 80,align:'left', editor:{type:'float'}}
					],
					enabledEdit:true, pageSize:20, data:null, width:700, height:200,usePager:false,
					toolbar: { items:[
		   	            {text:'添加', click:addItemRow, img:contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif'},
		   	         	{text:'刷新', click:refreshItemGrid, img:contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif'}
		   	       	]}
				});
				parent.giveItemGrid.addRow();
				parent.giveItemOneGrid = $("#giveItemOneGrid").ligerGrid({
					columns: [ 
						{display:'项目编码', name:'activno', width : 120, align:'left', editor:{type:'text',onChanged:loadGiveItemOne}},
						{display:'项目名称', name:'activname', width : 150,align:'left'},
						{display:'单次金额', name:'onecountprice', width : 80,align:'left', editor:{type:'float', onChanged:totalItemOneAmt}},
						{display:'次数', name:'givecount', width : 80,align:'left', editor:{type:'int', onChanged:totalItemOneAmt}},
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
						{display:'提成金额', name:'takeamt', width : 80,align:'left', editor:{type:'float'}}
					],
					enabledEdit:true, pageSize:20, data:null, width:700, height:200,usePager:false,
					toolbar: { items:[
		   	            {text:'添加', click:addItemOneRow, img:contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif'},
		   	         	{text:'刷新', click:refreshItemOneGrid, img:contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif'}
		   	       	]}
				});
				parent.giveItemOneGrid.addRow();
				parent.giveProductGrid = $("#giveProductGrid").ligerGrid({
					columns: [ 
					   {display:'产品编号', name:'activno', width : 120, align:'left', editor:{type:'text',onChanged:loadGiveGoods}},
					   {display:'产品名称', name:'activname', width : 150,align:'left'},
					   {display:'数量', name:'givecount', width : 80,align:'left', editor:{type:'int'}}
					],
					enabledEdit:true, pageSize:20, data:null, width:400, height:150,usePager:false,
					toolbar: { items:[
		   	            {text:'添加', click:addGiveRow, img:contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif'},
		   	         	{text:'刷新', click:refreshGiveGrid, img:contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif'}
		   	       	]}
				});
				parent.giveProductGrid.addRow();
				$("#pageloading").hide();
			} catch (e) {
				alert(e.message);
			}
		});
		/******************************公共方法****************************************/
		//校验项目或产品是否重复
		function checkExist(obj, dataSet){
			var count = 0;
			$(dataSet).each(function(i, row){
				if(row.activno==obj.value){
					count++;
				}
			});
			return count<2;
		}
		//查询项目
		function findItems(obj, grid){
			if(checkNull(obj.value) == ""){
				return;
			}
			var params = {strCurCompId:parent.strCurCompId, activno:obj.value, _random:Math.random()};
			var url = contextURL+"/bc021/queryItem.action"; 
			$.post(url, params, function(data){
				var item = data.projectinfo;
				if(item!=null && checkNull(item.prjname)!=""){
					grid.updateCell('activname', item.prjname, obj.record); 
			    }else{
				 	$.ligerDialog.warn("该疗程项目编号不存在！");
				 	grid.updateRow(obj.record, {activno:'',activname:''});
				}
			}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
		}
		//查询产品
		function findGoods(obj, grid){
			if(checkNull(obj.value) == ""){
				return;
			}
			var params = {strCurCompId:parent.strCurCompId, activno:obj.value, _random:Math.random()};
			var url = contextURL+"/bc021/queryGoods.action"; 
			$.post(url, params, function(data){
				if(checkNull(data.goodsname) != ""){
					grid.updateCell('activname', data.goodsname, obj.record); 
			    }else{
				 	$.ligerDialog.warn("该产品编号不存在！");
				 	grid.updateRow(obj.record, {activno:'',activname:''});
				}
			}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
		}
		//查询分类
		function findClassify(codeno, obj, grid){
			if(checkNull(obj.value) == ""){
				return;
			}
			var params = {strCodeno:codeno, strCodekey:obj.value, _random:Math.random()};
			var url = contextURL+"/bc021/queryClassify.action"; 
			$.post(url, params, function(data){
				var commoninfo = data.commoninfo;
				if(commoninfo!=null && checkNull(commoninfo.parentcodevalue) != ""){
					grid.updateCell('activname', commoninfo.parentcodevalue, obj.record); 
			    }else{
				 	$.ligerDialog.warn("该分类编号不存在！");
				 	grid.updateRow(obj.record, {activno:'',activname:''});
				}
			}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
		}
		/******************************购买疗程****************************************/
		//疗程项目
		function loadItem(obj){
			if(checkNull(parent.itemGridClassify.getRow(0).activno)==""){
				var flag = checkExist(obj, parent.itemGrid.getData());
				if(flag){
					findItems(obj, parent.itemGrid);
				}else{
					$.ligerDialog.error("添加的疗程项目已经存在！请重新输入。");
					parent.itemGrid.updateCell('activno', '', obj.record); 	
				}
			}else{
				$.ligerDialog.warn("购买疗程只能填写项目或者分类，重新填写请点击刷新!");
				parent.itemGrid.updateCell('activno', '', obj.record); 
			}
		}
		//疗程项目套数
		function checkItemNum(obj){
			if(obj.value*1>0){
				$("#itemamt,#itemcount").val("");
				$("#itemamt,#itemcount,#itemand,#itemor").attr("disabled", true);
				$("#itemor").attr("checked", true);
			}else{
				$("#itemamt,#itemcount,#itemand,#itemor").attr("disabled", false);
			}
		}
		//疗程分类
		function loadClassifyItem(obj){
			if(checkNull(parent.itemGrid.getRow(0).activno)==""){
				findClassify('XMTJ', obj, parent.itemGridClassify);
			}else{
				$.ligerDialog.warn("购买疗程只能填写项目或者分类，重新填写请点击刷新!");
				parent.itemGridClassify.updateCell('activno', '', obj.record); 
			}
		}
		/******************************购买产品****************************************/
		//产品
		function loadGoods(obj){
			if(checkNull(parent.productGridClassify.getRow(0).activno)==""){
				var flag = checkExist(obj, parent.productGrid.getData());
				if(flag){
					findGoods(obj, parent.productGrid);
				}else{
					$.ligerDialog.error("添加的产品已经存在！请重新输入。");
					parent.productGrid.updateCell('activno', '', obj.record); 	
				}
			}else{
				$.ligerDialog.warn("购买产品只能填写项目或者分类，重新填写请点击刷新!");
				parent.productGrid.updateCell('activno', '', obj.record); 
			}
		}
		//产品套数
		function checkGoodsNum(obj){
			if(obj.value*1>0){
				$("#proamt,#procount").val("");
				$("#proamt,#procount,#proand,#proor").attr("disabled", true);
				$("#proor").attr("checked", true);
			}else{
				$("#proamt,#procount,#proand,#proor").attr("disabled", false);
			}
		}
		//产品分类
		function loadClassifyGoods(obj){
			if(checkNull(parent.productGrid.getRow(0).activno)==""){
				findClassify('WPTJ', obj, parent.productGridClassify);
			}else{
				$.ligerDialog.warn("购买产品只能填写项目或者分类，重新填写请点击刷新!");
				parent.productGridClassify.updateCell('activno', '', obj.record); 
			}
		}
		/******************************项目、产品赠送****************************************/
		//项目A
		function loadGiveItem(obj){
			var flag = checkExist(obj, parent.giveItemGrid.getData());
			if(flag){
				findItems(obj, parent.giveItemGrid);
			}else{
				$.ligerDialog.error("添加的疗程项目已经存在！请重新输入。");
				parent.giveItemGrid.updateCell('activno', '', obj.record); 	
			}
		}
		//项目B
		function loadGiveItemOne(obj){
			var flag = checkExist(obj, parent.giveItemOneGrid.getData());
			if(flag){
				findItems(obj, parent.giveItemOneGrid);
			}else{
				$.ligerDialog.error("添加的疗程项目已经存在！请重新输入。");
				parent.giveItemOneGrid.updateCell('activno', '', obj.record); 	
			}
		}
		//产品C
		function loadGiveGoods(obj){
			var flag = checkExist(obj, parent.giveProductGrid.getData());
			if(flag){
				findGoods(obj, parent.giveProductGrid);
			}else{
				$.ligerDialog.error("添加的产品已经存在！请重新输入。");
				parent.giveProductGrid.updateCell('activno', '', obj.record); 	
			}
		}
		//合计
		function totalItemAmt(obj){
			var givecount = obj.record.givecount*1;
			givecount = isNaN(givecount)? 0 : givecount*1;
			var onecountprice = obj.record.onecountprice;
			onecountprice = isNaN(onecountprice)? 0 : onecountprice*1;
			parent.giveItemGrid.updateCell('givetotal', givecount*onecountprice, obj.record);
		}
		function totalItemOneAmt(obj){
			var givecount = obj.record.givecount*1;
			givecount = isNaN(givecount)? 0 : givecount*1;
			var onecountprice = obj.record.onecountprice;
			onecountprice = isNaN(onecountprice)? 0 : onecountprice*1;
			parent.giveItemOneGrid.updateCell('givetotal', givecount*onecountprice, obj.record);
		}
		/******************************表格刷新****************************************/
		//各表格添加行
		function addNewRow(){
			parent.itemGrid.addRow({activtype:1});
		}
		function refreshGrid(){
			parent.itemGrid.options.data=$.extend(true, {}, null);
			parent.itemGrid.reload();
			addNewRow();
			parent.itemGridClassify.options.data=$.extend(true, {}, null);
			parent.itemGridClassify.reload();
			parent.itemGridClassify.addRow({activtype:2});
			$("#itemamt,#itemcount").val("");
			$("#itemamt,#itemcount,#itemand,#itemor").attr("disabled", false);
			$("#itemor").attr("checked", true);
		}
		//产品
		function addProductRow(){
			parent.productGrid.addRow({activtype:1});
		}
		function refreshGoodsGrid(){
			parent.productGrid.options.data=$.extend(true, {}, null);
			parent.productGrid.reload();
			addProductRow();
			parent.productGridClassify.options.data=$.extend(true, {}, null);
			parent.productGridClassify.reload();
			parent.productGridClassify.addRow({activtype:2});
			$("#proamt,#procount").val("");
			$("#proamt,#procount,#proand,#proor").attr("disabled", false);
			$("#proor").attr("checked", true);
		}
		//赠送项目
		function addItemRow(){
			parent.giveItemGrid.addRow();
		}
		function refreshItemGrid(){
			parent.giveItemGrid.options.data=$.extend(true, {}, null);
			parent.giveItemGrid.reload();
			addItemRow();
		}
		function addItemOneRow(){
			parent.giveItemOneGrid.addRow();
		}
		function refreshItemOneGrid(){
			parent.giveItemOneGrid.options.data=$.extend(true, {}, null);
			parent.giveItemOneGrid.reload();
			addItemOneRow();
		}
		//赠送产品
		function addGiveRow(){
			parent.giveProductGrid.addRow();
		}
		function refreshGiveGrid(){
			parent.giveProductGrid.options.data=$.extend(true, {}, null);
			parent.giveProductGrid.reload();
			addGiveRow();
		}
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
										<td style="width: 40%;"><input type="radio" name="typebox" id="typebox1" value="1" checked="checked"/>&nbsp;&nbsp;卡异动</td>
										<td valign="top" rowspan="6">
											<div id="giveItemGrid" style="margin:0 0 0 10px; padding:0;"></div>
											<div id="giveItemOneGrid" style="margin:5px 0 0 10px; padding:0;"></div>
											<div style="width: 100%;padding-left: 10px;margin: 20px 0;text-align: center;">
												<input type="radio" name="giveorand" id="giveor" value="1" checked="checked"/>&nbsp;或
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="radio" name="giveorand" id="giveand" value="2"/>&nbsp;且
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="radio" name="giveorand" id="givegoods" value="3"/>&nbsp;只送产品
											</div>
											<div id="giveProductGrid" style="margin:0 0 0 10px; padding:0;"></div>
										</td>
									</tr>
									<tr style="text-align: center;">
										<td>金额&nbsp;&nbsp;<input type="text" name="cardamt" id="cardamt" style="width:200;"/></td>
									</tr>
									<tr style="height: 30px;line-height: 30px;">		
										<td><input type="radio" name="typebox" id="typebox2" value="2"/>&nbsp;&nbsp;购买疗程</td>
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
														<tr><td><input type="radio" name="itemorand" id="itemor" value="1" checked="checked" />&nbsp;或</td></tr>
														<tr><td><input type="radio" name="itemorand" id="itemand" value="2"/>&nbsp;且</td></tr>
														<tr style="vertical-align: bottom;"><td>套数</td></tr>
														<tr><td><input type="text" name="itemcount" id="itemcount" style="width:60;"/></td></tr>
													</table>
												</td>
											</tr>
											<tr><td><div id="itemGridClassify" style="margin:0; padding:0"></div></td></tr>
										</table>
									</td></tr>
									<tr style="height: 30px;line-height: 30px;">		
										<td><input type="radio" name="typebox" id="typebox3" value="3"/>&nbsp;&nbsp;购买产品</td>
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
														<tr><td><input type="radio" name="proorand" id="proor" value="1" checked="checked"/>&nbsp;或</td></tr>
														<tr><td><input type="radio" name="proorand" id="proand" value="2"/>&nbsp;且</td></tr>
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