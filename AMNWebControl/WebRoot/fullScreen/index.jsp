<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>AMN收银管理桌面</title>
<script type="text/javascript" src="<%=ContextPath%>/common/prototype.js"></script>
<script type="text/javascript" src="<%=ContextPath%>/fullScreen/jsLib/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=ContextPath%>/fullScreen/jsLib/myLib.js"></script>
<script type="text/javascript" src="<%=ContextPath%>/fullScreen/jsLib/main.js"></script>
<script type="text/javascript" src="<%=ContextPath%>/fullScreen/jsLib/desktop.js"></script>
<script type="text/javascript" src="<%=ContextPath%>/common/commoninfo.js"></script>
<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
<script type="text/javascript">
$(function(){
		   myLib.progressBar();
		   });
$.include(['<%=ContextPath%>/fullScreen/themes/default/css/desktop.css',
 			'<%=ContextPath%>/fullScreen/jsLib/jquery-ui-1.8.18.custom.css',
  			'<%=ContextPath%>/fullScreen/jsLib/jquery-smartMenu/css/smartMenu.css' ,
   			'<%=ContextPath%>/fullScreen/jsLib/jquery-ui-1.8.18.custom.min.js',
   	 		'<%=ContextPath%>/fullScreen/jsLib/jquery.winResize.js', 
    		'<%=ContextPath%>/fullScreen/jsLib/jquery-smartMenu/js/mini/jquery-smartMenu-min.js']);
$(window).load(function(){
		   myLib.stopProgress();
		   
		   //这里本应从数据库读取json数据，这里直接将数据写在页面上
		   var lrBarIconData={
			  
				
					   };
		//这里本应从数据库读取json数据，这里直接将数据写在页面上			   
		  var deskIconData={
		  					        
				'compInfo':{
				'title':'本店资料',
				'url':'<%=ContextPath%>/BaseInfoControl/BC001/index.jsp',
				'winWidth':900,
				'winHeight':600
				},
				'commonInfo':{
				'title':'常用资料',
				'url':'<%=ContextPath%>/BaseInfoControl/BC002/index.jsp',
				'winWidth':900,
				'winHeight':600
				}	
			  };			   
 		   
		  //存储桌面布局元素的jquery对象
		   myLib.desktop.desktopPanel();
 		   
		   //初始化桌面背景
		   myLib.desktop.wallpaper.init("<%=ContextPath%>/fullScreen/themes/default/images/replace.jpg");
		   
		   //初始化任务栏
		   myLib.desktop.taskBar.init();
		   
		   //初始化桌面图标
		   myLib.desktop.deskIcon.init(deskIconData);
		   
		   //初始化桌面导航栏
		   myLib.desktop.navBar.init();
		   
		   //初始化侧边栏
		   myLib.desktop.lrBar.init(lrBarIconData);
		   
		   //欢迎窗口
		   myLib.desktop.win.newWin({
													 WindowTitle:'系统帮助文档',
													 iframSrc:"<%=ContextPath%>/fullScreen/welcome.html",
													 WindowsId:"welcome",
													 WindowAnimation:'none', 
													 WindowWidth:740,
													 WindowHeight:520
 													 });
  		  
		  });		

//添加应用函数
function addIcon(data){
	 myLib.desktop.deskIcon.addIcon(data);
	}
</script>
</head>
<body>

<div id="wallpapers"></div>

<div id="desktopPanel">
<div id="desktopInnerPanel">
<ul class="deskIcon currDesktop">
  <li class="desktop_icon" id="compInfo"> <span class="icon"><img src="<%=ContextPath%>/fullScreen/icon/BC001.png"/></span> <div class="text">本店资料<s></s></div> </li>
  <li class="desktop_icon" id="commonInfo"> <span class="icon"><img src="<%=ContextPath%>/fullScreen/icon/BC002.png"/></span> <div class="text">常用资料<s></s></div> </li>
 
  <li class="desktop_icon add_icon" id="addIcon0"> <span class="icon"><img src="<%=ContextPath%>/fullScreen/themes/default/images/add_icon.png"/></span> <div class="text">添加 <s></s></div> </li>
</ul>
<ul class="deskIcon">
  <li class="desktop_icon" id="zfMeishi"> <span class="icon"><img src="<%=ContextPath%>/fullScreen/icon/icon13.png"/></span> <div class="text">主妇美食<s></s></div> </li>
  
</ul>
<ul class="deskIcon">
  <li class="desktop_icon" id="win35"> <span class="icon"><img src="<%=ContextPath%>/fullScreen/icon/icon18.png"/></span> <div class="text">搜狐汽车<s></s></div> </li>
 
</ul>
<ul class="deskIcon">
  <li class="desktop_icon" id="win38"> <span class="icon"><img src="<%=ContextPath%>/fullScreen/icon/icon20.png"/></span> <div class="text">火影忍者漫画动画<s></s></div> </li>
 
</ul>
<ul class="deskIcon">
   <li class="desktop_icon" id="qianqianMusic"> <span class="icon"><img src="icon/icon5.png"/></span> <div class="text">千千音乐 <s></s></div> </li>
 
 </ul>
<ul class="deskIcon">
    <li class="desktop_icon" id="qidian"> <span class="icon"><img src="icon/icon9.png"/></span> <div class="text">起点中文 <s></s></div> </li>
 </ul>
</div>
</div>

<div id="taskBarWrap">
<div id="taskBar">
  <div id="leftBtn"><a href="#" class="upBtn"></a></div>
  <div id="rightBtn"><a href="#" class="downBtn"></a> </div>
  <div id="task_lb_wrap"><div id="task_lb"></div></div>
</div>
</div>

<div id="lr_bar">
  <ul id="default_app">
   	<li id="showMain1"><a  onclick="showMainDesk(0)"> <span><img src="<%=ContextPath%>/fullScreen/icon/F1.png" title="基本设定"/></span></a><div class="text">基本设定<s></s></div></li>
   	<li id="showMain2"><a  onclick="showMainDesk(1)"> <span><img src="<%=ContextPath%>/fullScreen/icon/F2.png" title="运营管理"/></span></a><div class="text">运营管理<s></s></div></li>
  	<li id="showMain3"><a  onclick="showMainDesk(2)"> <span><img src="<%=ContextPath%>/fullScreen/icon/F3.png" title="人事管理"/></span></a><div class="text">人事管理<s></s></div></li>
  	<li id="showMain4"><a  onclick="showMainDesk(3)"> <span><img src="<%=ContextPath%>/fullScreen/icon/F4.png" title="销售管理"/></span></a><div class="text">销售管理<s></s></div></li>
   	<li id="showMain5"><a  onclick="showMainDesk(4)"> <span><img src="<%=ContextPath%>/fullScreen/icon/F5.png" title="物流管理"/></span></a><div class="text">物流管理<s></s></div></li>
   	<li id="showMain"><a   onclick="showMainDesk(5)"> <span><img src="<%=ContextPath%>/fullScreen/icon/F6.png" title="高级操作"/></span></a><div class="text">高级操作<s></s></div></li>
  </ul>

  <div id="default_tools"><a title="桌面" id="showDesk_btna" onclick="showDesk()"> <span id="showDesk_btn" title="桌面"></span></a></div>
  <div id="default_tools"><a title="退出" id="showQuit_btna" onclick="showQuit()"> <span id="showQuit_btn" title="退出"></span></a></div>
 
</div>

</body>
</html>
