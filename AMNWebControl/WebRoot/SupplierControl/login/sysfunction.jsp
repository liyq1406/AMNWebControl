<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>功能区</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=ContextPath%>/fullScreen2/css/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=ContextPath%>/fullScreen2/css/styles.css" rel="stylesheet" type="text/css" />
<script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.plugins.js" type="text/javascript"></script>
<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script> 
<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
<style type="text/css">
/* mokuai */
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
a{color:#FFCC00;}
.clear{clear:both;}

/* thumbnailWrapper */
.thumbnailWrapper{width:900px;margin:10px auto 0 auto;color:#ffffff;text-align:center;font-family:'georgia';font-size: 12px;}
.thumbnailWrapper ul li{float:left;position:relative;overflow:hidden;margin:10px 10px 0 auto;}
.thumbnailWrapper ul li a img{width:200px;height:150px;position:relative;border:none;}
.caption{position:absolute;bottom:0px;left:0px;width:100%;display:block;background:#cfd0db;color:#0c4b62;opacity:0.9;}
.caption .captionInside{padding:10px;margin:0px;}
/* mozhu */
.navigation{position:absolute;left:300px;bottom:0px;width:800px;margin:40px auto 0 auto;}
ul.menu{list-style:none;font-family:"Verdana",sans-serif;border-top:1px solid #bebebe;margin:0px;padding:0px; float:left;}
ul.menu li{float:left;}
ul.menu li a{text-decoration:none;background:#7E7E7E url(../images/bgMenu.png) repeat-x top left;padding:15px 0px; width:128px;
    color:#333333; float:left;text-shadow: 0 1px 1px #fff;text-align:center;border-right:1px solid #a1a1a1;
    border-left:1px solid #e8e8e8;font-weight:bold;font-size:13px;-moz-box-shadow: 0 1px 3px #555;-webkit-box-shadow: 0 1px 3px #555;
}
ul.menu li a.hover{background-image:none;color:#fff;text-shadow: 0 -1px 1px #000;}
ul.menu li a.first{-moz-border-radius:0px 0px 0px 10px;-webkit-border-bottom-left-radius: 10px;border-left:none;}
ul.menu li a.last{-moz-border-radius:0px 0px 10px 0px;-webkit-border-bottom-right-radius: 10px;}
ul.menu li span{width:64px;height:64px;background-repeat:no-repeat;background-color:transparent;position:absolute;z-index:-1;bottom:80px;cursor:pointer;}
</style>
<script type="text/javascript">
$(window).load(function(){
	try
	{
		
		var d=1000;
      
        $('#menu > li').hover(
          function () {
          var $this = $(this);
          $('a',$this).addClass('hover');
	     // $('span',$this).stop().animate({'top':'40px'},300).css({'zIndex':'10'});
          },
          function () {
            var $this = $(this);
            $('a',$this).removeClass('hover');
           // $('span',$this).stop().animate({'top':'-17px'},800).css({'zIndex':'-1'});
           }
        );
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
			left: thumbnail.imgIncrease/3.5*(-1),
			top: thumbnail.imgIncrease/3.5*(-1)
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
		//$(this).find('.caption').slideUp(thumbnail.effectDuration);

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
var sysmodel = JSON.parse('${sessionScope.sucModule}');
function showFunctionDetial(curGroupType)
{
	try
	{
		$('#functionUL').children().filter('li').remove();
		$.each(sysmodel, function(i, mode){
			createFunction(mode.moduleurl,mode.id.curmoduleno,mode.modulename,"2.jpg");
		});
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
</head>
<body >
	<div id="sidebar">
      <div class="mainNav">
			<div class="user">
				<a title="" class="leftUserDrop" ><img src="<%=ContextPath%>/fullScreen2/images/user.png" alt="" /></a>
			</div>
			<ul class="nav">
				<li>
					<a title="" href="javascript:showFunctionDetial('SupplierControl')"  ><img src="<%=ContextPath%>/fullScreen2/images/icons/mainnav/ui.png" alt="" /><span>供应商管理</span></a>
				</li>
			</ul>
      </div>
       <div class="secNav"  >
   			<div class='thumbnailWrapper'>
				<ul id="functionUL"></ul>
   			</div>
	   </div>
    </div>
    <script type="text/javascript">
    	showFunctionDetial("SupplierControl");
    </script>
</body>
</html>
