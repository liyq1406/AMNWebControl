<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.0.2
Version: 1.5.4
Author: KeenThemes
Website: http://www.keenthemes.com/
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
-->
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="zh-CN" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>阿玛尼供应商登录</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link href="${pageContext.request.contextPath}/login_3/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/login_3/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/login_3/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}login_3/assets/plugins/select2/select2_metro.css" />
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME STYLES --> 
	<link href="${pageContext.request.contextPath}/login_3/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/login_3/assets/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/login_3/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/login_3/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/login_3/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="${pageContext.request.contextPath}/login_3/assets/css/pages/login.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/login_3/assets/css/custom.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/login_3/favicon.ico" />
</head>
	<OBJECT id=locator height=0 width=0 classid=CLSID:76A64158-CB41-11D1-8B02-00600806D9B6 VIEWASTEXT></OBJECT>
	<OBJECT id=foo height=0 width=0  classid=CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223></OBJECT>

<!-- BEGIN BODY -->
<body class="login">
	<!-- BEGIN LOGO -->
	<div class="logo">
		<img src="${pageContext.request.contextPath}/login_3/assets/img/logo-big.png" alt="#" /> 
	</div>
	<!-- END LOGO -->
	<!-- BEGIN LOGIN -->
	<div class="content">
		<!-- BEGIN LOGIN FORM -->
		<form class="login-form" action="${pageContext.request.contextPath}/suc001/login.action"  method="post">
			<H3 class="form-title"></H3>
			<div class="alert alert-danger display-hide">
				<button class="close" data-close="alert"></button>
				<span>请输入您的用户名和密码</span>
			</div>
			<div class="form-group">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9">用户名</label>
				<div class="input-icon">
					<i class="fa fa-user"></i>
					<input class="form-control placeholder-no-fix" type="text" autocomplete="off"   placeholder="请输入用户名！" name="login" id="login"/>
				</div>
			</div>
            <div class="form-group">
				&nbsp;
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">密码</label>
				<div class="input-icon">
					<i class="fa fa-lock"></i>
					<input class="form-control placeholder-no-fix" type="password" autocomplete="off"  placeholder="请输入密码！" name="password" id="password"/>
				</div>
			</div>
			<div class="form-actions">
				<font color="red"> <s:actionerror /></font>
				<button type="submit" class="btn green pull-right">
				登录<i class="m-icon-swapright m-icon-white"></i>
				</button>            
			</div>
			
			 <input type=hidden name="logintype" value="0">
	  		<input id="licenseCode" name="licenseCode" type="hidden"/>
      		<input id="macAddr" name="macAddr" type="hidden"/>
      		<input id="ipAddr"  name="ipAddr"  type="hidden"/>
      
		</form>
	</div>
	<!-- END LOGIN -->
	<!-- BEGIN COPYRIGHT -->
	<div class="copyright">
		2013 &copy; Amani Information Department.
	</div>
	
		<SCRIPT type="text/javascript">
			/*if(navigator.userAgent.indexOf("MSIE 10.0")>0  || navigator.userAgent.indexOf("MSIE 11.0")>0 || navigator.userAgent.indexOf("MSIE 12.0")>0)
			{
				document.getElementById("compid").readOnly=true;
				document.getElementById("userid").readOnly=true;
				document.getElementById("pwd").readOnly=true;
			}
			else
			{
				document.getElementById("compid").readOnly=false;
				document.getElementById("userid").readOnly=false;
				document.getElementById("pwd").readOnly=false;
			}*/
   		var service = locator.ConnectServer();
   		var MACAddr ;
   		var IP ;
   		var IPA="" ;
   		var IPB="" ;
   		service.Security_.ImpersonationLevel=3;
   		service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');
	</SCRIPT>
	<script type="text/javascript" event=OnObjectReady(objObject,objAsyncContext) for=foo>
			
   			if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true)
   			{
    			if(objObject.MACAddress != null && objObject.MACAddress != "undefined")
    			{
    				MACAddr = objObject.MACAddress;
    				
    			}
    		
    			if(objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != "undefined")
    			{
    				IP = objObject.IPAddress(0);
    			}
    		
    		}	
		</script>
		<SCRIPT type="text/javascript" event="OnCompleted(hResult,pErrorObject, pAsyncContext)" for=foo>
			document.getElementById("macAddr").value = unescape(MACAddr);
    		document.getElementById("ipAddr").value = unescape(IP);
			//initLogin();
		</SCRIPT>
	<!-- END COPYRIGHT -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->   
	<!--[if lt IE 9]>
	<script src="${pageContext.request.contextPath}/assets/plugins/respond.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/plugins/excanvas.min.js"></script> 
	<![endif]-->   
	<script src="${pageContext.request.contextPath}/login_3/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/login_3/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/login_3/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/login_3/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript" ></script>
	<script src="${pageContext.request.contextPath}/login_3/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>  
	<script src="${pageContext.request.contextPath}/login_3/assets/plugins/jquery.cookie.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${pageContext.request.contextPath}/login_3/assets/plugins/jquery-validation/dist/jquery.validate.min.js" type="text/javascript"></script>	
	<script src="${pageContext.request.contextPath}/login_3/assets/plugins/jquery-validation/localization/messages_zh.js" type="text/javascript"></script>	
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${pageContext.request.contextPath}/login_3/assets/scripts/app.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/SupplierControl/login/entry.js" type="text/javascript"></script> 
	<!-- END PAGE LEVEL SCRIPTS --> 
	<script>
		jQuery(document).ready(function() {     
		  App.init();
		  Login.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>