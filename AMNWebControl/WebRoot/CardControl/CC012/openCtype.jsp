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
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		$(function(){
			parent.ctypelist=$("#ctypelist");
	    	var url = "<%=ContextPath%>/cc012/loadConsumeCtype.action";
	    	var params = {"_random":Math.random()};
	    	$.getJSON(url, params, function(data){
	    		clearOption("ctypelist");
	    		addOption("-1","--------------请选择--------------",document.getElementById("ctypelist"));
	    		var list = data.lsMconsumeinfo;
	    		if(list!=null && list.length>0){
	    			$(list).each(function(){
	    				var len = this.bcsbillid.length;
	    				var title = this.bcsbillid.substring(len-3, len)+"-"+this.cscardno+(checkNull(this.csname)==""?"":"-")+this.csname;
		    			addOption(this.bcsbillid,title,document.getElementById("ctypelist"));
	    			});
	    		}else{
	    			$.ligerDialog.warn("无C类美容师体验消费客户信息！");
	    		}
	    	}).error(function(e){$.ligerDialog.error("获取信息失败，请重试！");});
		});
	</script>
</head>
<body style="padding:6px; overflow:hidden;">
	<div style="height: 30px;" id="layout1" class="l-layout" ligeruiid="layout1">
		<div style="top: 0px; height: 143px;" class="l-layout-top">
			<div class="l-layout-content" position="top" style="text-align: center;padding-top: 5px;">
				消费客户列表:&nbsp;&nbsp;<select name="ctypelist" id="ctypelist" style="width: 180px;"></select>
			</div>
		</div>
	</div>
</body>
</html>