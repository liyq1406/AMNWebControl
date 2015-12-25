
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>功能区</title>
<script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.plugins.js" type="text/javascript"></script>

<script type="text/javascript">
$(window).load(function(){
	try
	{
	$(".mozhu").lavaLamp({
		fx: "backout", 
		speed: 700,
		click: function(event, menuItem) {
			return true;
		}
	});
	if(parent.Sysmodeinfo!=null && parent.Sysmodeinfo.length>0)
	{		

		showFunctionDetial("",parent.Sysmodeinfo[0].id.upmoduleno)
	}
	}catch(e){alert(e.message);}
});

function urDivActive()
{
	//设置和获取一些变量
	var thumbnail = {
		imgIncrease : 100, /* 增加图像像素（变焦） */
		effectDuration : 400, /* 效果的持续时间（变焦和标题） */
		/* 
		获取的图像的宽度和高度。要使用这些
		2件事:
		列表项大小相同
		得到的图像缩放后恢复正常
		*/
		imgWidth : $('.thumbnailWrapper ul li').find('img').width(), 
		imgHeight : $('.thumbnailWrapper ul li').find('img').height()
	};

	//列表项相同的大小作为图像
	$('.thumbnailWrapper ul li').css({ 
		'width' : thumbnail.imgWidth, 
		'height' : thumbnail.imgHeight 
	});

	//当鼠标移到列表项...
	$('.thumbnailWrapper ul li').hover(function(){
		$(this).find('img').stop().animate({
			/* 变焦效果，提高图像的宽度 */
			width: parseInt(thumbnail.imgWidth) + thumbnail.imgIncrease,
			/* 我们需要改变的左侧和顶部的位置，才能有放大效应，因此我们将它们移动到一个负占据一半的img增加 */
			left: thumbnail.imgIncrease/2*(-1),
			top: thumbnail.imgIncrease/2*(-1)
		},{ 
			"duration": thumbnail.effectDuration,
			"queue": false
		});

		//使用slideDown事件显示的标题
		$(this).find('.caption:not(:animated)').slideDown(thumbnail.effectDuration);
		
		//当鼠标离开...
	}, function(){
	
		//发现图像和动画...
		$(this).find('img').animate({
			/* 回原来的尺寸（缩小） */
			width: thumbnail.imgWidth,
			/* 左侧和顶部位置恢复正常 */
			left: 0,
			top: 0

		}, thumbnail.effectDuration);

		//隐藏使用滑块事件的标题
		$(this).find('.caption').slideUp(thumbnail.effectDuration);

	});
}
//---------------------------------------------功能显示
function showFunction(functionNo ,name,mianShowUrl)
{
	try
	{
		var curtab =parent.tab
		curtab.addTabItem({ tabid : functionNo,text: name, url: mianShowUrl });
	}catch(e){alert(e.message);}
}
function showFunctionDetial(curpage,curGroupType)
{
	try
	{
		var ccount=0;
		$('#functionUL').children().filter('li').remove();
		for( var i=0;i<parent.Sysmodeinfo.length;i++)
		{
			if(parent.Sysmodeinfo[i].id.upmoduleno==curGroupType)
			{
				ccount=ccount+1;
				createFunction(parent.Sysmodeinfo[i].moduleurl,parent.Sysmodeinfo[i].id.curmoduleno,parent.Sysmodeinfo[i].modulename,ccount+".jpg");
			}
		}
		urDivActive();
	}catch(e){alert(e.message)}
}

function createFunction(moduleUrl,moduleId,moduleName,imageurl)
{
	var ul=	$('#functionUL')
	var lihtml="<li><a href=\"javascript:showFunction('"+moduleId+"','"+moduleName+"','<%=ContextPath%>/"+moduleUrl+"')\">";
	lihtml=lihtml+"<img src=\"<%=ContextPath%>/fullScreen2/images/"+imageurl+"\" /></a>";
	lihtml=lihtml+"<div class=\"caption\"><p class=\"captionInside\">"+moduleName+"</p>	</div></li>";
	ul.append(lihtml); 
	
}
</script>
<style type="text/css">
/* mokuai */
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
a{color:#FFCC00;}
.clear{clear:both;}
body{color:#ffffff;text-align:center;font-family:'georgia';font-size: 12px;}
/* thumbnailWrapper */
.thumbnailWrapper{width:800px;margin:40px auto 0 auto;}
.thumbnailWrapper ul li{float:left;position:relative;overflow:hidden;}
.thumbnailWrapper ul li a img{width:200px;position:relative;border:none;}
.caption{position:absolute;bottom:0px;left:0px;width:100%;display:none;background:#0c4b62;color:#ffffff;opacity:0.9;}
.caption .captionInside{padding:10px;margin:0px;}
/* mozhu */
.mozhu{position:absolute;bottom:0px;left:80px;height:68px;width:967px;padding-left:13px;background:url('<%=ContextPath%>/common/ligerui/images/meun_bg.png') no-repeat 0 8px;overflow: hidden;margin:40px auto;}
.mozhu_bg{ position:absolute;top:0px;left:0px;background:url('<%=ContextPath%>/common/ligerui/images/image87.png') no-repeat;height:8px;width:980px;overflow:hidden;}
.mozhu li{float:left;}
.mozhu li.back{background:url('<%=ContextPath%>/common/ligerui/images/meun_tab.png') no-repeat;padding-left:8px;height:68px;overflow:hidden;z-index:8;position:absolute;}
.mozhu li.back .left{background:url('<%=ContextPath%>/common/ligerui/images/meun_tab.png') no-repeat right 0;height:68px;float:right;width:8px;}
.mozhu li.back .arrow{float:left;width:92%;height:68px;position:relative;}
.mozhu li.back .arrow .icon{position:absolute;top:56px;left:45%;background:url('<%=ContextPath%>/common/ligerui/images/arrow.gif') no-repeat;height:5px;width:9px;overflow:hidden;}
.mozhu li a{ font-family:"微软雅黑","黑体";text-decoration:none;color:#fff;font-size:18px;z-index:10;display:block;float:left;position:relative;overflow:hidden;padding:25px 33px 0;height:43px;}
.mozhu li a span{cursor:pointer;}
</style>
</head>
<body >
	<div class='thumbnailWrapper'>
		<ul id="functionUL">
			
		</ul>
   	</div>
   	<div class='clear'></div><!-- clear the float -->
	<div class="mozhu">
		<div class="mozhu_bg"></div>
		<ul>
			<li><a href="http://www.17sucai.com/"><span>首页</span></a></li>
			<li><a href="http://www.17sucai.com/"><span>jquery 特效</span></a></li>
			<li><a href="http://www.17sucai.com/"><span>javascript特效</span></a></li>
			<li class="current"><a href="http://www.17sucai.com/"><span>flash特效</span></a></li>
			<li><a href="http://www.17sucai.com/"><span>div+css教程</span></a></li>
			<li><a href="http://www.17sucai.com/"><span>html5教程</span></a></li>
		</ul>
	</div>
	     
</body>
</html>

	<tr  height="13px">
	     	<td valign="middle"  align="center"><a  class="l-link3" href="javascript:showFunctionDetial('基本资料','BC')">基本资料</a></td>
	     	<td  valign="middle" align="center"><a  class="l-link3" href="javascript:showFunctionDetial('运营管理','CC')">运营管理</a></td>
	     	<td  valign="middle" align="center"><a 	class="l-link3" href="javascript:showFunctionDetial('人事管理','PC')">人事管理</a></td>
	     	<td  valign="middle" align="center"><a 	class="l-link3" href="javascript:showFunctionDetial('物流管理','IC')">物流管理</a></td>
	     	<td  valign="middle" align="center"><a 	class="l-link3" href="javascript:showFunctionDetial('销售分析','SC')">销售分析</a></td>
	     	<td  valign="middle" align="center"><a 	class="l-link3" href="javascript:showFunctionDetial('高级设定','AC')">高级设定</a></td>
	     	</tr>