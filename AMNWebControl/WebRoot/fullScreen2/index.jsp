<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>AMN收银管理系统</title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" /> 
   
    <link href="<%=ContextPath%>/fullScreen2/cssx/reset.css" rel="stylesheet" type="text/css" />
	<link href="<%=ContextPath%>/fullScreen2/cssx/styles.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
    <script type="text/javascript" src="<%=ContextPath%>/common/prototype.js"></script>
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.4.2.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js" type="text/javascript"></script> 
	<script type="text/javascript" src="<%=ContextPath%>/fullScreen2/function.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery.messager.js" type="text/javascript"></script>
	
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/commoninfo.js"></script>
	<object id="PrintControl" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
		
	</object>
										
	<object id="CardCtrl" classid="clsid:7B6EF111-7EF2-4B8B-B9F6-79D75A484C2F"
  		   codebase="<%=AMN_OCX_PATH%>ICCard.CAB"style="visibility:hidden;height:0px" VIEWASTEXT ></object>
<style type="text/css"> 
    body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background:url('<%=ContextPath%>/fullScreen2/images/background.png') repeat;
    }
    
  	.l-link{ display:block; height:16px; line-height:16px; padding-left:10px; color:#333;}
    .l-link2{color:white; margin-left:2px;margin-right:2px;text-decoration:none;}
    .l-link3{text-decoration:underline;  margin-left:2px;margin-right:2px;}
    .l-layout-top{background:#102A49; color:White;}
    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
    #pageloading{position:absolute; left:0px; top:0px; background:white url('loading.gif') no-repeat center; width:100%; height:100%;z-index:99999;}
    .l-link{ display:block; line-height:13px; height:13px; padding-left:16px;border:1px solid white; margin:4px;}
    .l-link-over{ background:#FFEEAC; border:1px solid #DB9F00;} 
    .l-winbar{ background:#2B5A76; height:30px; position:absolute; left:0px; bottom:0px; width:100%; z-index:99999;}
    .space{ color:#E7E7E7;}
    /* 顶部 */ 
    

    
    .l-topmenu{ margin:0; padding:0; height:40px; line-height:40px; background:url('<%=ContextPath%>/fullScreen2/images/backgrounds/top.jpg') repeat-x bottom;  position:relative; border-top:1px solid #1D438B;  }
    .l-topmenu-logo{ color:#E7E7E7; padding-left:10px; line-height:26px;background:url('<%=ContextPath%>/common/ligerui/images/topicon.gif') no-repeat 10px 5px;}
    .l-topmenu-welcome{  position:absolute; height:24px; line-height:24px;  right:30px; top:10px;color:#070A0C;}
    .l-topmenu-welcome a{ color:#E7E7E7; text-decoration:underline} 
    /*日历*/
    .mh_date{width:249px; height:20px; line-height:20px; padding:5px; border:2px #D6D6D6 solid; cursor:pointer; background:url('<%=ContextPath%>/common/ligerui/images/dateIco.png') no-repeat right center;}
 </style>
</head>
<body >  
<div id="pageloading"></div>  
<div id="topmenu" class="l-topmenu">
    <div id="l-topmenu" >
    	<div class="wrapper">
        	<a  title="" class="logo"><img src="<%=ContextPath%>/fullScreen2/images/logo.png" alt="" /></a>
    	</div>
	</div>
	<div class="l-topmenu-welcome">
   	 	<a class="l-link2">门店:&nbsp;&nbsp;<s:property value="strCompName"/></a>  
        <a class="l-link2">用户编号:&nbsp;&nbsp;<s:property value="strUserId"/></a>  
        <a class="l-link2">用户名称:&nbsp;&nbsp;<s:property value="strUserName"/></a>   
        <a class="l-link2">用户角色:&nbsp;&nbsp;<s:property value="strUserRolsName"/></a>    
        <a class="l-link2" href="javascript:searchFunction()">修改密码</a>  
     </div> 
</div>
  		<div id="layout1" style="width:99.5%; margin:0 auto; margin-top:0px; "> 
	        <div position="center" id="framecenter"> 
	        			<div tabid="home" title="系统功能区" style="height:300px" >
	         				<iframe frameborder="0" name="mainShowFram" id="mainShowFram" >
	            			</iframe> 
	            		</div> 
	            				
	        </div> 
       </div>
      	<s:hidden id="bankPassFlag" name="bankPassFlag" theme="simple" ></s:hidden>
       	<s:hidden id="strUserId" name="strUserId" theme="simple" ></s:hidden>
  	  	<s:hidden id="strUserName" name="strUserName" theme="simple" ></s:hidden>
  	  	<s:hidden id="strCompName" name="strCompName" theme="simple"></s:hidden>
  	  	<s:hidden id="strCompId" name="strCompId" theme="simple"></s:hidden>
  	 	<s:hidden id="callcenterqueue" name="callcenterqueue" theme="simple"></s:hidden>
  		<s:hidden id="callcenterinterface" name="callcenterinterface" theme="simple"></s:hidden>
  
    <script type="text/javascript">
    	//$.messager.show("1212",10000);
    	//$.messager.show("1212121",10001);
    	var showDialogmanager = $.ligerDialog.waitting('正在展示中,请稍候...');
    	var contextURL="<%=request.getContextPath()%>";
    	function openFunction()
    	{
    		try
			{
				var callcenterqueue=document.getElementById("callcenterqueue").value;
    			var callcenterinterface=document.getElementById("callcenterinterface").value;
    			var requestUrl ="http://10.1.1.4/perl/queue?action=QueuePause&pbx=127.0.0.1&uniqueId=6ebeb9d8-3202-11dd-bd9e-0017a4e399ed&extenType=gateway&queue="+callcenterqueue+"&interface=SIP/"+callcenterinterface+"&paused=false";
				$.ligerDialog.open({ url: requestUrl, height: 200,width: 200 });
			}
			catch(e)
			{
				alert(e.message);
			}
    	}
    	function closeFunction()
    	{
    		var callcenterqueue=document.getElementById("callcenterqueue").value;
    		var callcenterinterface=document.getElementById("callcenterinterface").value;
    		var requestUrl ="http://10.1.1.4/perl/queue?action=QueuePause&pbx=127.0.0.1&uniqueId=6ebeb9d8-3202-11dd-bd9e-0017a4e399ed&extenType=gateway&queue="+callcenterqueue+"&interface=SIP/"+callcenterinterface+"&paused=true";
			$.ligerDialog.open({ url: requestUrl, height: 200,width: 200 });
    		
    	}
    	function searchFunction()
    	{
    		retDialogmanager=$.ligerDialog.open({ url: "<%=request.getContextPath()%>"+'/fullScreen2/retpassword.jsp', width:300,allowClose:false, title: '设置密码' });
    	}
    	window.setInterval('checkUserValidate()',60000);
    	function checkUserValidate()
    	{
    		try
    		{
    			var requestUrl ="checkUserValidate.action"; 
				var responseMethod="checkUserValidateMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,"" );
			}catch(e){alert(e.message);}
    	}
    	function checkUserValidateMessage(request)
   		{
   			var responsetext = eval("(" + request.responseText + ")");
       		var strCheckFlag=responsetext.strCheckFlag;
       		if(strCheckFlag=="N")
       		{
       			$.ligerDialog.confirm('当前系统链接已丢失,是否重新登录？', function (type) { Close(type);  });
       		}
       	}
       	
       	//window.setInterval('checkUserSession()',300000);
    	function checkUserSession()
    	{
    		try
    		{
    			var requestUrl ="checkUserSession.action"; 
				var responseMethod="checkUserSessionMessage";	
				var param="strLoginCompid="+document.getElementById("strCompId").value;	
				param=param+"&strLoginUserId="+document.getElementById("strUserId").value;	
				sendRequestForParams_p(requestUrl,responseMethod,param );
			}catch(e){alert(e.message);}
    	}
    	function checkUserSessionMessage(request)
   		{
   			
       	}
       	
       	
       	function Close(type)
       	{alert(type);
       		if(type==true)
       		{
       			window.open("<%=request.getContextPath()%>"+"/Login_3.jsp");
       			//window.close();
       		}
       		else
       		{
       			//window.close();
       		}
       	}
       	var retDialogmanager=null;
       	function loadHomeUI()
		{
			showDialogmanager.close();
			document.getElementById("mainShowFram").src=contextURL+"/fullScreen2/sysfunction.jsp"
			if(document.getElementById("bankPassFlag").value==0)
			{
				retDialogmanager=$.ligerDialog.open({ url: "<%=request.getContextPath()%>"+'/fullScreen2/retpassword.jsp', width:300,allowClose:false, title: '设置密码' });
			}
		}
		
		function retPasswordMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
       		var strMessage=responsetext.strMessage;
       		if(checkNull(strMessage)=="")
       		{
       			$.ligerDialog.success("设置成功");
       			retDialogmanager.close();
       		}
       		else
       		{
       			$.ligerDialog.success("设置失败");
       		}
		}
   	/*********来电弹屏*************************/
    
    </script>
</body>
</html>
