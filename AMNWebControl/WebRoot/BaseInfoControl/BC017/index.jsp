<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC017/bc017.js"></script>

		<style type="text/css">
		  body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
  
		    body,html{height:100%;}
	    body{ padding:0px; margin:0;   overflow:hidden;}  
	    .l-link{ display:block; height:16px; line-height:16px; padding-left:10px; text-decoration:underline; color:#333;}
	    .l-link2{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
	    .l-link3{text-decoration:underline;  margin-left:2px;margin-right:2px;}
	    .l-layout-top{background:#102A49; color:White;}
	    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
		 #dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;}
		 
		 
          #fd01{ 
 		 width:210px; height:360px;background:#EDF1F8;  border: 1px solid #849BCA; margin-top:2px; margin-left:2px;float:left;
		 overflow:hidden;position:absolute;left:20px;top:41;cursor:move;float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/1.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		 /*filter:alpha(opacity=50);*/
		}
		
		#fd02{ 
 		 width:210px;height:360px;background:#EDF1F8; border: 1px solid #849BCA;margin-top:2px;margin-left:2px;float:left;
		 overflow:hidden; position:absolute;left:240px;top:41;cursor:move;float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/2.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		
		 /*filter:alpha(opacity=50);*/
		}
		
		#fd03{ 
 		 width:210px; height:360px; background:#EDF1F8;  border: 1px solid #849BCA; margin-top:2px;margin-left:2px;float:left;
		 overflow:hidden; position:absolute;left:460px;top:41; cursor:move; float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/3.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		/*filter:alpha(opacity=50);*/
		}
		
		#fd04{ 
 		 width:210px; height:360px; background:#EDF1F8; border: 1px solid #849BCA; margin-top:2px;margin-left:2px; float:left;
		 overflow:hidden;position:absolute;left:680px; top:41;cursor:move; float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/4.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		/*filter:alpha(opacity=50);*/
		}
		#fd05{ 
 		 width:210px; height:360px;background:#EDF1F8;  border: 1px solid #849BCA; margin-top:2px;margin-left:2px; float:left;
		 overflow:hidden; position:absolute;left:900px;top:41;cursor:move;float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/5.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		/*filter:alpha(opacity=50);*/
		}
		#fd06{ 
 		 width:210px; height:360px; background:#EDF1F8;  border: 1px solid #849BCA;margin-top:2px;margin-left:2px; float:left;
		 overflow:hidden; position:absolute; left:1120px;top:41;cursor:move; float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/6.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		/*filter:alpha(opacity=50);*/
		}
		
		   #fd07{ 
 		 width:210px; height:360px;background:#EDF1F8;  border: 1px solid #849BCA; margin-top:2px; margin-left:2px;float:left;
		 overflow:hidden;position:absolute;left:20px;top:401;cursor:move;float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/7.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		 /*filter:alpha(opacity=50);*/
		}
		
		#fd08{ 
 		 width:210px;height:360px;background:#EDF1F8; border: 1px solid #849BCA;margin-top:2px;margin-left:2px;float:left;
		 overflow:hidden; position:absolute;left:240px;top:401;cursor:move;float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/8.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		/*filter:alpha(opacity=50);*/
		}
		
		#fd09{ 
 		 width:210px; height:360px; background:#EDF1F8;  border: 1px solid #849BCA; margin-top:2px;margin-left:2px;float:left;
		 overflow:hidden; position:absolute;left:460px;top:401; cursor:move; float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/9.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		 /*filter:alpha(opacity=50);*/
		}
		
		#fd10{ 
 		 width:210px; height:360px; background:#EDF1F8; border: 1px solid #849BCA; margin-top:2px;margin-left:2px; float:left;
		 overflow:hidden;position:absolute;left:680px; top:401;cursor:move; float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/10.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		/*filter:alpha(opacity=50);*/
		}
		#fd11{ 
 		 width:210px; height:360px;background:#EDF1F8;  border: 1px solid #849BCA; margin-top:2px;margin-left:2px; float:left;
		 overflow:hidden; position:absolute;left:900px;top:401;cursor:move;float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/11.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		/*filter:alpha(opacity=50);*/
		}
		#fd12{ 
 		 width:210px; height:360px; background:#EDF1F8;  border: 1px solid #849BCA;margin-top:2px;margin-left:2px; float:left;
		 overflow:hidden; position:absolute; left:1120px;top:401;cursor:move; float:left;
		 background-image:url(<%=ContextPath%>/BaseInfoControl/BC017/image/12.jpg);background-repeat:no-repeat;　　/*背景图片不重复显示*/　
		 /*filter:alpha(opacity=50);*/
		}
		#fdoption{ 
 		 width:1270px; height:35px; background:#EFEFEF;  border: 1px solid #849BCA;margin-top:2px;margin-left:2px; float:left;
		 overflow:hidden; position:absolute; left:20px;top:1;cursor:move; float:left; /*filter:alpha(opacity=50);*/
		}
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

 <div id="bc017layout" style="width:100%; margin:0 auto; margin-top:1px; "> 
		<div position="center"   id="designPanel"  style="width:100%;"> 
			<div id="fd01" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
						<div id="commoninfodiv01"></div>
				</div>
			</div>	
			<div id="fd02" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv02"></div> 
				</div>
			</div>	
			<div id="fd03" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv03"></div>  
				</div>
			</div>	
			<div id="fd04" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv04"></div> 
				</div>
			</div>	
			<div id="fd05" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv05"></div> 
				</div>
			</div>	
			<div id="fd06" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv06"></div> 
				</div>
			</div>	
			<div id="fd07" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv07"></div>  
				</div>
			</div>	
			<div id="fd08" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv08"></div> 
				</div>
			</div>	
			<div id="fd09" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv09"></div> 
				</div>
			</div>	
			<div id="fd10" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv10"></div> 
				</div>
			</div>	
			<div id="fd11" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv11"></div> 
				</div>
			</div>	
			<div id="fd12" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<div id="commoninfodiv12"></div> 
				</div>
			</div>	
			
			<div id="fdoption" style="filter:alpha(opacity=100);opacity:1;">
				<div  style="width:900; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="width:100%;border:solid 1px #aaa;font-size:12px;line-height:30px;" >
					<tr>
						<td width="40" ><font color="blue" >月份</font></td>
						<td width="110"><input id="smmonth" name="smmonth" type="text" style="width:100" readonly="true"/></td>
						<td><font color="blue" >门店</font></td>
						<td width="240">
							<input type="text" name="compid" id="compid" style="width:60;" onchange="validateShandcompid(this)"/>
							&nbsp;<input type="text" name="compname" id="compname" style="width:160;" />
						</td>
						<td><font color="blue" >店长</font></td>
						<td  width="180">
							<input type="text" name="staffid" id="staffid" style="width:60;" onchange="validateShandStoreWoner(this)"/>
							&nbsp;<input type="text" name="staffname" id="staffname" style="width:100;" />
							<input type="hidden" name="staffinid" id="staffinid"/>
						</td>
						<td><font color="blue" >类型</font></td>
						<td >
							<select id="stafftype" name="stafftype" style="width:100">
								<option value="">请选择</option>
								<option value="1">做业绩</option>
								<option value="2">不做业绩</option>
							</select>
						</td>
						<td><div id="createStoreWonerInfo"></div></td>
					</tr>
					</table>
				</div>
			</div>				      
		</div>
				        
</div> 

	
  <div style="display:none;">
  <!-- g data total ttt -->
</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  	
	</script>