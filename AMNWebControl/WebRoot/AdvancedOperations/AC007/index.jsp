<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="<%=ContextPath%>/CardControl/CC011/selfButton/buttons/buttons.css" /> 
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.tablescroll.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script> 	
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/mindsearchitem.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC007/ac007.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC007/webuploader.min.js"></script>
	
	<style type="text/css">
		.webuploader-pick{position: relative;display: inline-block;cursor: pointer;background: #00b7ee;padding: 10px 15px;color: #fff;text-align: center;border-radius: 3px;overflow: hidden;}
		.webuploader-pick-hover{background: #00a2d4;}
		.webuploader-pick-disable{opacity: 0.6;pointer-events:none;}
	</style>
</head>
<body>
	<div class="l-loading" style="display:block;height:100%;" id="pageloading"></div> 
	 <div id="dataLayout" style="width:100%;margin:0 auto; margin-top:0px;"> 
	    <div position="center" id="designPanel"  style="width:100%;height: 100%;"> 
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr>
				<td valign="top">
				<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
					<tr>
						<td valign="top">
							<div style="float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;margin-bottom: 5px;">
								<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;" >
									<tr>	
										<td style="width: 140px;">&nbsp;&nbsp;商品类型&nbsp;&nbsp;
										<select id="stype" name="stype" style="width: 70px;">
											<option value="-1">请选择</option>
										</select></td>
										<td style="width: 180px;white-space: nowrap;">&nbsp;&nbsp;编号&nbsp;&nbsp;
										<input type="text" name="sid" id="sid" style="width:120;"/></td>
										<td style="width: 200px;white-space: nowrap;">&nbsp;&nbsp;名称&nbsp;&nbsp;
										<input type="text" name="sname" id="sname" style="width:150;"/></td>
										<td style="width: 300px;white-space: nowrap;">&nbsp;&nbsp;优惠券/卡券&nbsp;&nbsp;
										<select id="scard_id" name="scard_id" style="width: 150px;">
											<option value="-1">请选择</option>
										</select></td>
										<td><div id="queryBtn"></div></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<td valign="top"><div id="masterGrid" style="margin:0; padding:0;"></div></td>
					</tr>
					</table>
				</td>
				</tr>
			</table>
	    </div>
	 </div>
  <div style="display:none;">
  <!-- g data total ttt -->
</div>

<script language="JavaScript">
 	 	var contextURL="${pageContext.request.contextPath}";
</script>
</body>
</html>
