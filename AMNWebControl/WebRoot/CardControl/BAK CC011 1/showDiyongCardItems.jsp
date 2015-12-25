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
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
	var commoninfodivthirth=null;
	$(function ()
   	{
   		 try
	   	 {
	   	 	parent.commoninfodivfouth=$("#commoninfodivfouth").ligerGrid({
		            columns: [
	                { display: '疗程编号', 	name: 'ineritemno',  		width:100,align: 'left' },
	                { display: '疗程名称', 	name: 'ineritemname', 		width:150,align: 'left'},
		            { display: '剩余次数', 	name: 'lastcount', 			width:60,align: 'center'},
		            { display: '剩余金额', 	name: 'lastamt', 			width:60,align: 'center'},
		            { display: '备注', 		name: 'entryremark', 		width:130,align: 'left'},
		          	{ 	name: 'bseqno', 		hide:true,align: 'left'}
	                ],  pageSize:25, 
	                data: {Rows: parent.lsDnointernalcardinfo,Total: parent.lsDnointernalcardinfo.length},      
	                width: '600',
	                height:'400',
	                enabledEdit: true, checkbox:false,
	                rownumbers: false,usePager: false
	            });
            	$("#pageloading").hide();  	
          }catch(e){alert(e.message);} 	
    	});
    	
    	
    	
	</script>
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
		#dv_scroll_bar .Scrollbar-Handle{position:absolute;left:-7px;bottom:10;width:13px;height:29px;overflow:hidden;background:url('<%=ContextPath%>/common/ligerui/images/srcoll.gif') no-repeat;cursor:pointer;}
		#dv_scroll_text {position:absolute;}
    </style>
</head>
<body style="padding:6px; overflow:hidden;">
	<div class="l-loading" style="display:block" id="pageloading"></div> 
   	<div id="commoninfodivfouth" style="margin:0; padding:0"></div>
</body>
</html>
