<%@ page language="java" import="com.amani.tools.UserInformation"  pageEncoding="UTF-8"%>
<%
	//UserInformation userinfo=(UserInformation)session.getAttribute("userInfo");
	//if(userinfo==null)
	//{
		%>
			<script language="JavaScript">
			/*function Close()
			{  
			   //parent.window.open('','_parent','');
			   parent.window.close(); 
			}

			alert("当前登录用户已在其他地方登录，请确认！");
			window.open("<%=request.getContextPath()%>"+"/Login_1.html");
			Close();*/
			</script>
		<%
	//}
	//userinfo=null;
	final String ContextPath = request.getContextPath();
	final String AMN_JS_PATH = ContextPath + "/common/js/";
	final String AMN_JSP_PATH = ContextPath + "/common/jsp/";
	final String AMN_CSS_PATH = ContextPath + "/css/style1/";
	final String AMN_IMG_PATH = ContextPath + "/images/style1/";
	final String AMN_MAINIMG_PATH = ContextPath + "/images/main/";
	final String AMN_MAINCSS_PATH = ContextPath + "/css/main/";
	final String AMN_ICON_PATH = ContextPath + "/images/icon/";
	final String AMN_TOOL_PATH = ContextPath + "/images/toolbar/";
	final String AMN_OCX_PATH = ContextPath + "/CardControl/";
	final String AMN_CAPTION_PATH = ContextPath+ "/images/style1/module_caption";
	final String AMN_CONTROLS_PATH = ContextPath + "/controls/";
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
	response.setDateHeader("Expires", 0);
%>  

	<script language="javascript" for="document" event="onkeydown">
	
	try{
	var key=window.event.keyCode;
	}catch(e){var key = 1;}
	 if(key == 65 && event.altKey)
	{
		try
		{
			addMaster();
		}
		catch(e)
		{
		}
	}
	else if(key == 46 && event.altKey) //delete
	{
		try
		{
			deleteMaster();
		}
		catch(e)
		{
		
		}
		window.event.keyCode   =   505; 
		window.event.returnValue=false;
	}
	else if(key==122)//F11
	{
		window.event.keyCode=18;
		window.event.returnValue=false;
	}
	else if(key==116)//F5
	{
		window.event.keyCode=-1;
		window.event.returnValue=false;
	}
	else if( key == 82 &&  event.altKey) //Alt+R//
	{
		window.event.keyCode=-1;
		window.event.returnValue=false;
	}
	else
	{
		try
		{
			hotKeyOfSelf(key);
		}
		catch(e)
		{
		}
	}
	</script>