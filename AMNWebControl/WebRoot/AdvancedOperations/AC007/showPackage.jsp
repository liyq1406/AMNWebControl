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
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" /> 
    <link rel="stylesheet" type="text/css" href="<%=ContextPath%>/CardControl/CC011/selfButton/buttons/buttons.css" /> 
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" /> 
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script> 
    <script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script> 
    <script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script> 
    <script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
    <script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script> 
    <script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC007/webuploader.min.js"></script>
<script language="JavaScript">
	var curRecord = null, curRowIndex="";
	var curProRecord = parent.curProRecord;
	$(function() {
		try {
			parent.detialGrid=$("#detialGrid").ligerGrid({
	            columns: [
	            { display: '项目编号', name: 'productid', width:120, align: 'left',editor:{type:'int', onChanged:loadProject}},
	            { display: '项目名称', name: 'name', width:250, align: 'left'},
	            { display: '项目数量', name: 'count', width:120, align: 'left',editor:{type:'int'}},
	            {name:'parentId', width:1, hide:'true'},
	            {name:'id', width:1, hide:'true'}
	            ],  pageSize:20, 
	            data:{Rows: null,Total:0},      
	            width: 700,
	            height: 260,
	            enabledEdit:true, usePager: false,
	            toolbar: { items: [
		            { text: '添加', click: addRow, img: requestURL+'/common/ligerui/ligerUI/skins/icons/add.gif'}
	          	]}
	        });
			// 初始化Web Uploader
	   		var uploader = WebUploader.create({
		   	   // 选完文件后，是否自动上传。
		   	   //auto: true,
		       // swf文件路径
		       swf: requestURL +'/AdvancedOperations/AC007/Uploader.swf',
		       // 文件接收服务端。
		       server: requestURL +'/ac007/upload.action',
		       // 选择文件的按钮。可选。
		       // 内部根据当前运行是创建，可能是input元素，也可能是flash.
		       pick: {id:'#filePicker', multiple:false},
		       // 只允许选择图片文件。
		       accept: {
		           title: 'Images',
		           extensions: 'gif,jpg,jpeg,bmp,png',
		           mimeTypes: 'image/*'
		       },
		       sendAsBinary: true
		   	});
		 	// 当有文件添加进来的时候
		   	uploader.on( 'fileQueued', function( file ) {
		   	    var $li = $('<div id="' + file.id + '" class="file-item thumbnail"><img></div>'),
		   	        $img = $li.find('img');
		   	    // $list为容器jQuery实例
		   	    $("#previewImg").html($li);
		   	    // 创建缩略图
		   	    // 如果为非图片文件，可以不用调用此方法。
		   	    // thumbnailWidth x thumbnailHeight 为 100 x 100
		   	    uploader.makeThumb( file, function( error, src ) {
		   	        if ( error ) {
		   	            $img.replaceWith('<span>不能预览</span>');
		   	            return;
		   	        }
		   	        $img.attr( 'src', src);
			   	 	if(checkNull(src)==""){
			 			$.ligerDialog.warn("获取图片失败，请刷新页面重试！");
			 			return;
			 		}
			   	 	var nameImg = "_"+"main_image."+((file.name.split('.'))[1]);
			   	 	$("#mainimagename").val(nameImg);
			   	 	$("#mainimageurl").val(src);
		   	    }, 350, 210);
		   	});
		 	$(parent.numData).each(function(i, row){
	       		addOption(row.choose, row.text, document.getElementById("num"));
			});
		 	if(parent.cardData != null){
			 	$(parent.cardData).each(function(i, row){
					addOption(row.card_id, row.title, document.getElementById("card_id"));
				});
		 	}
		 	loadData();
		 	//校验套餐编号是否重复信息
		 	$("#cid").change(function(){
		 		var cid = $('#cid').val();
		 		if(checkNull(cid) == "" || (curProRecord!=null && checkNull(cid)==curProRecord.cid)){
		 			return;
		 		}
		 		var url = requestURL+"/ac007/checkPackageNo.action"; 
		 		$.post(url, {cid:cid}, function(data){
		 			if(checkNull(data.strMessage)!=""){
		 				$('#cid').val("");
		 				$.ligerDialog.warn(data.strMessage);
		 			}
		 		}).error(function(e){$.ligerDialog.error("系统异常！请刷新重试。");});
		 	});
		 	$("#isopenbox").click(function(){
		 		$("#isopen").val($("#isopenbox").attr("checked")?1:0);
		 	});
		 	$("#ishotbox").click(function(){
		 		$("#ishot").val($("#ishotbox").attr("checked")?1:0);
		 	});
		 	$("#pageloading").hide();
		} catch (e) {
			alert(e.message);
		}
	});
	
	//加载产品步骤
	function loadData(){
		if(curProRecord==null || checkNull(curProRecord.id)==""){
	 		return;
	 	}
		$("#id").val(checkNull(curProRecord.id));
		$("#cid").val(checkNull(curProRecord.cid));
		$("#name").val(checkNull(curProRecord.name));
		$("#price").val(checkNull(curProRecord.price));
		$("#num").find("option[value='"+ curProRecord.num +"']").attr("selected",true);
		$("#originalcost").val(checkNull(curProRecord.originalcost));
		$("#sumnum").val(checkNull(curProRecord.sumnum));
		$("#sort").val(checkNull(curProRecord.sort));
		$("#isopen").val(curProRecord.isopen).attr("checked", checkNull(curProRecord.isopen)=="1");
		$("#ishot").val(curProRecord.ishot).attr("checked", checkNull(curProRecord.ishot)=="1");
		$("#card_id").find("option[value='"+ curProRecord.card_id +"']").attr("selected",true);
		$("#intro").val(checkNull(curProRecord.intro));
		$("#content").val(checkNull(curProRecord.content));
		$("#mainimageurl").val(checkNull(curProRecord.mainimageurl));
        $("#previewImg").html('<img src="'+ checkNull(curProRecord.mainimageurl) +'" style="width:350px;height:210px;">');
        var url = requestURL+"/ac007/loadPackPro.action"; 
		var params ={id: checkNull(curProRecord.id)};
		$.post(url, params, function(data){
			var list = data.productPackages;
			if(list!=null && list.length>0){
				parent.detialGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
			}else{
				parent.detialGrid.options.data=$.extend(true, {}, null);
			}
			parent.detialGrid.loadData(true);
		}).error(function(e){$.ligerDialog.error("系统异常！请刷新重试。");});
	}
	function addRow(item){
		parent.detialGrid.addRow({parentId:(curProRecord==null||checkNull(curProRecord.id)==""?0:curProRecord.id)});
	}
	//加载套餐项目
	function loadProject(obj){
		if(checkNull(obj.value) == ""){
			return;
		}
		//校验项目或产品是否重复
		var count = 0;
		var dataSet = parent.detialGrid.getData();
		$(dataSet).each(function(i, row){
			if(row.productid==obj.value){
				count++;
			}
		});
		if(count<2){
			var params = {cid:obj.value};
			var url = requestURL+"/ac007/queryProject.action"; 
			$.post(url, params, function(data){
				var product = data.product;
				if(product != null){
					parent.detialGrid.updateCell('name', product.name, obj.record);
					if(curProRecord!=null&&checkNull(curProRecord.id)==""){
						parent.detialGrid.updateCell('parentId',curProRecord.id, obj.record); 
				 	}
			    }else{
				 	$.ligerDialog.warn("该项目编号不存在！");
				 	parent.detialGrid.updateRow(obj.record, {productid:'',name:''});
				}
			}).error(function(e){$.ligerDialog.error("系统异常，请刷新重试！");});
		}else{
			$.ligerDialog.error("添加的产品已经存在！请重新输入。");
			parent.detialGrid.updateCell('productid', '', obj.record); 	
		}
	}
</script>
<style type="text/css">
body{font-size: 12px;}
.l-table-edit{}
.l-table-edit-td{padding: 4px;}
.l-button-submit,.l-button-test{width: 80px;float: left;margin-left: 10px;padding-bottom: 2px;}
.l-verify-tip{left: 230px;top: 120px;}
.scr_con{position: relative;width: 298px;height: 98%;border: solid 1px #ddd;margin: 0px auto;font-size: 12px;}
#dv_scroll{position: absolute;height: 98%;overflow: hidden;width: 298px;}
#dv_scroll .Scroller-Container{width: 100%;}
#dv_scroll_bar{position: absolute;right: 0;bottom: 30px;width: 14px;height: 150px;border-left: 1px solid #B5B5B5;}
#dv_scroll_bar .Scrollbar-Track{position: absolute;left: 0;top: 5px;width: 14px;height: 150px;}
#dv_scroll_bar .Scrollbar-Handle{position: absolute;left: -7px;bottom: 10;width: 13px;height: 29px;overflow: hidden;background: url('<%=ContextPath%>/common/ligerui/images/srcoll.gif')no-repeat;cursor: pointer;}
#dv_scroll_text{position: absolute;}
.webuploader-pick{position: relative;display: inline-block;cursor: pointer;background: #00b7ee;padding: 10px 15px;color: #fff;text-align: center;border-radius: 3px;overflow: hidden;}
.webuploader-pick-hover{background: #00a2d4;}
.webuploader-pick-disable{opacity: 0.6;pointer-events:none;}
</style>
</head>
<body style="padding:6px; overflow:hidden;">
	<div class="l-loading" style="display:block;" id="pageloading"></div>
	<div style="height: 30px;" id="layout1" class="l-layout" ligeruiid="layout1">
		<div style="left: 0px; top: 0px; width: 100%; height: 490px;" class="l-layout-center">
			<div style="height: 500px;" class="l-layout-content" title="" position="center">
				<div style="margin:5px; padding:1px;width: 354px;height:213px; border: 1px solid #ccc;float: left;">
					<div id="uploader-demo">
					    <!--用来存放item-->
						<div id="previewImg" style="position: absolute;"></div>
					    <div id="fileList" class="uploader-list"></div>
					    <div id="filePicker" style="width: 80px;">选择图片</div>
					</div>
				</div>
				<div style="float:right; clear:both; border:1px solid #ccc; font-size:12px;margin: 5px;margin-left: 0;padding-bottom: 6px;">
					<form name="dataForm" method="post"  id="dataForm">
						<table id="dataTable" width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;line-height:27px;" >
							<tr>
								<td width="70">&nbsp;&nbsp;商品编号</td>
								<td width="120"><input type="text" name="cid" id="cid" style="width:150;"/></td>
								<td width="300" colspan="4">&nbsp;</td>	
							</tr>
							<tr>	
								<td>&nbsp;&nbsp;商品名称</td>
								<td colspan="5"><input type="text" name="name" id="name" style="width:390;"/></td>
							</tr>
							<tr>	
								<td>&nbsp;&nbsp;价格</td>
								<td><input type="text" name="price" id="price" onchange="validatePrice(this);" style="width:150;"/></td>
								<td>市场价格</td>
								<td><input type="text" name="originalcost" id="originalcost" onchange="validatePrice(this);" style="width:150;"/></td>
								<td colspan="2"><input type="checkbox" name="isopenbox" id="isopenbox"/>是否上架&nbsp;
									<input type="checkbox" name="ishotbox" id="ishotbox"/>是否推荐
									<input type="hidden" name="isopen" id="isopen" value="0"/><input type="hidden" name="ishot" id="ishot" value="0"/></td>
							</tr>
							<tr>	
								<td>&nbsp;&nbsp;绑定卡券</td>
								<td><select id="card_id" name="card_id" style="width: 150px;">
									<option value="-1">------------请选择------------</option>
								</select></td>
								<td>购买限制</td>
								<td><select id="num" name="num" style="width: 150px;"></select></td>
								<td>显示顺序</td>
								<td><input type="text" name="sort" id="sort" onchange="validatePrice(this);" style="width:50px;" value="1"/></td>
							</tr>
							<tr>	
								<td>&nbsp;&nbsp;商品特点</td>
								<td colspan="3"><input type="text" name="intro" id="intro" style="width:390;"/></td>
							</tr>
							<tr>	
								<td>&nbsp;&nbsp;商品介绍</td>
								<td colspan="3"><textarea id="content" name="content" rows="4" cols="46"></textarea>
								<input type="hidden" name="catgory" id="catgory" value="1"/>
								<input type="hidden" name="type" id="type" value="3"/>
								<input type="hidden" id="id" name="id" />
								<input type="hidden" id="strJson" name="strJson" />
								<input type="hidden" id="mainimageurl" name="mainimageurl" />
					    		<input type="hidden" id="mainimagename" name="mainimagename" /></td>
							</tr>
						</table>
					</form>
				</div>
				<div style="width:100%;clear:both; border:1px solid #ccc; font-size:12px;margin: 5px;padding: 2px;margin-top: 0px;">套餐包含项目</div>
				<div id="detialGrid" style="margin:0; padding:0;float: left;margin-left: 5px;"></div>
			</div>
		</div>
	</div>
</body>
</html>
<script language="JavaScript">var requestURL = "<%=request.getContextPath()%>";</script>