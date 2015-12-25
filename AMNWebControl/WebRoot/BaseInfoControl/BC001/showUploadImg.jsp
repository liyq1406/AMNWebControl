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
    <script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script> 
    <script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC001/webuploader.min.js"></script>
	<script language="JavaScript">
	var setpGrid = null, curRecord=null;
	var parentWin=window.dialogArguments;
	$(function() {
		try {
			// 初始化Web Uploader
		   	var uploader = WebUploader.create({
		   	   // 选完文件后，是否自动上传。
		   	   //auto: true,
		       // swf文件路径
		       swf: requestURL +'/BaseInfoControl/BC001/Uploader.swf',
		       // 文件接收服务端。
		       server: requestURL +'bc001/uploadImg.action',
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
		   	    var $li = $('<div id="' + file.id + '" class="file-item thumbnail">' +
		   	            '<img></div>'),
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
		   	        srcImg = src;
		   	        $img.attr( 'src', src );
		   	    }, 900, 500 );
		   	});
		   	var srcImg="";
		 	$("#ctlBtn").click(function(){
		 		if(checkNull(srcImg)==""){
		 			$.ligerDialog.warn("请先选择图片再上传^_-！");
		 			return;
		 		}
// 			   	var url = requestURL+"/ac007/upload.action"; 
			   	var url = requestURL+"/bc001/upload.action";
			   	var file = uploader.getFiles()[0];
		 		var params = {name: file.name, mainimageurl:srcImg, _stamp:Math.random()};
		 		var waitting = $.ligerDialog.waitting('正在上传中,请稍候...');
				$.post(url, params, function(data){
					waitting.close();
					if(checkNull(data.strMessage)!=""){
						srcImg="";uploader.reset();//重置图片上传队列
						$.ligerDialog.success("上传成功！");
						var a = $("#imgUrl",window.parent.document).val(data.strMessage);
					}else{
						$.ligerDialog.error("上传失败，请重试！");
					}
				}).error(function(e){$.ligerDialog.error(e.message);});
		 	});
		 	$("#pageloading").hide();
		} catch (e) {
			alert(e.message);
		}
	});
</script>
<style type="text/css">
body{font-size: 12px;TEXT-ALIGN: center;}
#center { MARGIN-RIGHT: auto; MARGIN-LEFT: auto; }
.webuploader-pick{position: relative;display: inline-block;cursor: pointer;background: #00b7ee;padding: 10px 15px;color: #fff;text-align: center;border-radius: 3px;overflow: hidden;}
.webuploader-pick-hover{background: #00a2d4;}
.webuploader-pick-disable{opacity: 0.6;pointer-events:none;}
</style>
</head>
<body style="padding:6px; overflow:hidden;">
	<div style="height: 30px;" id="layout1" class="l-layout" ligeruiid="layout1">
		<div style="left: 0px; top: 0px; width: 100%; height: 490px;" class="l-layout-center">
			<div style="margin:0; padding:0;width: 400px;">
				<div id="uploader-demo">
				    <!--用来存放item-->
				    <div id="fileList" class="uploader-list"></div>
				    <div id="filePicker" style="width: 100px;top:0px; float: left;margin: 0 50px;">选择图片</div>
				     <button id="ctlBtn"
				     	class="button blue" style="width: 100px;margin: 0 25px;padding: 8px 20px;font-size: 13px;">开始上传 </button>
				</div>
				<div id="previewImg" style="margin-top: 5px;"></div>
				<input type="hidden" id="mainimageurl" name="mainimageurl" />
			</div>
		</div>
	</div>
</body>
</html>
<script language="JavaScript">var requestURL = "<%=request.getContextPath()%>";</script>