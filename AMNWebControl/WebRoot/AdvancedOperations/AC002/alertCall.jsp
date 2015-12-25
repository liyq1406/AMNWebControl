<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>电话呼叫系统</title>
     <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=ContextPath%>/AdvancedOperations/AC002/images/style.css" />
	<script type="text/javascript" src="<%=ContextPath %>/AdvancedOperations/AC002/ac002.js"></script>
	
	<style type="text/css">
		input{font-size:12px;font-size:100%;font-family:microsoft yahei;outline:none;line-height:normal;color:#444;}
		.ipt{border:solid 1px #d2d2d2;border-left-color:#ccc;border-top-color:#ccc;border-radius:2px;box-shadow:inset 0 1px 0 #f8f8f8;background-color:#fff;padding:4px 6px;height:21px;line-height:21px;color:#555;width:180px;vertical-align:baseline;}
		.ipt:focus{border-color:#95C8F1;box-shadow:0 0 4px #95C8F1;}
		.theme-buy{margin-top:7%;text-align:center;}
		.theme-signin{font-size:15px;}
		.theme-popover-mask{z-index:1;position:absolute;left:0;top:0;width:100%;height:100%;background:#000;opacity:0.5;filter:alpha(opacity=50);-moz-opacity:0.5;display:none;}
		.theme-popover{z-index:9;position:absolute;top:50%;left:60%;width:330px;height:260px;margin:-180px 0 0 -330px;border-radius:5px;border:solid 2px #e4e4e4;background-color:#fff; box-shadow:0 0 10px #666;background:#fff;}
		.theme-poptit{border-bottom:1px solid #ddd;position:relative;height:60px;text-align:center;margin-top: 20px }
		.theme-poptit .close{float:right;color:#999;   margin-right:5px;font:bold 14px/14px simsun;text-shadow:0 1px 0 #ddd}
		.theme-poptit .close:hover{color:#444;}
		.theme-popbod{padding:60px 15px;color:#444;height:148px;}
		.theme-signin{ margin-top:-50px;text-align:left;font-size: 14px;}
		.theme-signin h4{color:#999;font-weight:100;margin-bottom: 20px;font-size: 12px;}
		.theme-signin li{padding-left: 30px;margin-bottom: 15px;}
		.theme-signin li strong{float: left;margin-left: -80px;width: 80px;text-align: right;line-height: 32px;}
		.theme-signin .btn{margin-bottom: 10px;}
		.theme-signin p{font-size: 12px;color: #999;}
		.theme-popover-masks{z-index:1;position:absolute;left:0;top:0;width:100%;height:100%;background:#000;opacity:0.5;filter:alpha(opacity=50);-moz-opacity:0.5;display:none;}
		.theme-popovers{z-index:9;position:absolute;top:50%;left:60%;width:330px;height:80px;margin:-180px 0 0 -330px;border-radius:5px;border:solid 2px #e4e4e4;background-color:#fff;display:none;box-shadow:0 0 10px #666;background:#fff;}
		.theme-popovers-ms{z-index:9;position:absolute;top:50%;left:60%;width:500px;height:80px;margin:-180px 0 0 -330px;border-radius:5px;border:solid 2px #e4e4e4;background-color:#fff;display:none;box-shadow:0 0 10px #666;background:#fff;}
	 </style>
</head>
<body style="padding:6px; overflow:hidden;">
<div class="theme-popover-masks"></div>
	<div class="theme-popovers">
				<div class="theme-poptit">
					<a href="javascript:showhies();" title="关闭" class="close">×</a>
					<h3>新的预约消息（该消息来自微信）</h3>
				</div>
	</div>
	
	<div class="theme-popovers-ms">
				<div class="theme-poptit">
					<a href="javascript:showMS();" title="关闭" class="close">×</a>
					<h4><input name="call_nos_ms"  id="call_nos_ms" readonly="true" style="border:0px;background-color:transparent;"/></h4>
				</div>
	</div>
 <div id="online_service_bar">
	<div id="online_service_fullbar">
		<div class="service_bar_head"><span id="service_bar_close" onclick="javascript:$('#online_service_bar').hide(); " title="点击关闭" >点击关闭</span></div>
		<div class="service_bar_main">
			<ul class="service_menu">
				<li class="hover">
					<dl>
						<dd>
					 		 <input name="call_nos"  id="call_nos" readonly="true" style="border:0px;background-color:transparent;"/> 
							 <input id="membernames" readonly="true" style="border:0px;background-color:transparent;"/>  
							 <input id="membernotocards" readonly="true" style="border:0px;background-color:transparent;"/>  
							 <input id="cardtypes" readonly="true" style="border:0px;background-color:transparent;"/> 
						</dd>
					</dl>
				</li>
			</ul>
		</div>
	</div>
</div>


		<table width="100%" height="100%" border="0" style="font-size:12px;line-height:35px;">
	 	     <tr>
		 	     	<td width="27%" valign="top" >
			 	     	<table border="1" width="100%" height="100%" align="top">
			 	     		<tr>
			 	     				<td valign="top"  height="100px"><table width="100%" border="0" height="100%" >
			 	     				<tr >
									    <td valign="top"   colspan="3" style="font-size:12px;line-height:25px;"><div id="topburmee"></div></td>
									  </tr>
									  <tr>
									    <td  style="font-size:12px;line-height:20px;">&nbsp;工号:</td>
									    <td style="font-size:12px;line-height:20px;"><input id="calluserid" readonly="true" style="border:0px;background-color:transparent;"/></td>
									    <td width="80" rowspan="3"><img src="images/ima.gif" style="width: 40px; height: 40px;"/></td>
									  </tr>
									  <tr>
									    <td style="font-size:12px;line-height:20px;">&nbsp;姓名:</td>
									     <td  style="font-size:12px;line-height:20px;">
									     <input id="staffname" readonly="true" style="border:0px;background-color:transparent;"/></td>
									  </tr>
									  <tr>
									  <td style="font-size:12px;line-height:20px;">&nbsp;时间:</td>
									  <td colspan="2" style="font-size:12px;line-height:20px;" >
										<font color="red"><span  id="www_zzjs_net"></span></font>
										<script type="text/javascript">
										function showLocale(objD)
										{
										var str,colorhead,colorfoot;
										var yy = objD.getYear();
										if(yy<1900) yy = yy+1900;
										var MM = objD.getMonth()+1;
										if(MM<10) MM = '0' + MM;
										var dd = objD.getDate();
										if(dd<10) dd = '0' + dd;
										var hh = objD.getHours();
										if(hh<10) hh = '0' + hh;
										var mm = objD.getMinutes();
										if(mm<10) mm = '0' + mm;
										var ss = objD.getSeconds();
										if(ss<10) ss = '0' + ss;
										var ww = objD.getDay();
										if  ( ww==0 )  colorhead="<font color=\"#FF0000\">";
										if  ( ww > 0 && ww < 6 )  colorhead="<font color=\"#373737\">";
										if  ( ww==6 )  colorhead="<font color=\"#008000\">";
										if  (ww==0)  ww="星期日";
										if  (ww==1)  ww="星期一";
										if  (ww==2)  ww="星期二";
										if  (ww==3)  ww="星期三";
										if  (ww==4)  ww="星期四";
										if  (ww==5)  ww="星期五";
										if  (ww==6)  ww="星期六";
										colorfoot="</font>";
										str = colorhead + yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss + "  " + ww + colorfoot;
										return(str);
										}
										function tick()
										{
										var today;
										today = new Date();
										document.getElementById("www_zzjs_net").innerHTML = showLocale(today);
										window.setTimeout("tick()", 1000);
										}
										tick();
										</script></td>
									  </tr>
			 	     				</table></td>
			 	     		</tr>
			 	     		<tr style="margin-left: 2px">
			 	     				<td valign="top"  height="100px" style="font-size:12px;line-height:20px;"> 
			 	     				 <div id="navtab2" >
										<div tabid="home" style="height:430" title="当前坐席来电" lselected="true"  >
										<div id="receipts"></div>
										</div>
										<div  title="未结案单据" style="height:430">
										<div id="undisposedcall" ></div>
										<table style="border:1px solid #A3C0E8;font-size:12px;width:100%">
											<tr >
											<td colspan="4"><div id="selecttime"></div></td>
											</tr>
											<tr>
											<td style="font-size:12px;line-height:25px;">时间</td>
											<td><input type="text" id="timms"/></td>
											</tr>
											</table>
										</div>
										
										<div title="短信未处理单据" style="height:430">
										<div id="messageundisposedcall" ></div>
										</div>
										
					 	     		</div>
			 	     				  </td>
			 	     		</tr>
			 	     		<tr>
		 	     			<td valign="top">
										<table border="1" bordercolor="#b8d9fa" style="width: 100%;font-size:12px;">
											<tr style="background: #c4def9;height:25px" >
							                <td style="width:140;" align="center">名称</td>
							                <td style="width:60;" align="center">大小</td>
							            	</tr> 
							               <tbody id="luRecord"></tbody>
										</table></td>
		 	     		</tr>
			 	     	</table>
		 	     	</td>
		 	     	<td height="750px" valign="top">
		 	     	<div id="navtab1" style="overflow:hidden;width:745px; border:1px solid #A3C0E8; ">
						<div tabid="home" title="来电历史"  lselected="true"  style="height:741px"  >
								<div id="callhistory"></div>
								<table style="border:1px solid #A3C0E8;font-size:12px;width:100%">
								<tr >
								<td colspan="4"><div id="select"></div></td>
								</tr>
								<tr>
								<td style="font-size:12px;line-height:25px;width:50px">姓名</td>
								<td> <input type="text" name="name" id="memname" style="width:160;height:22px;"/></td>
								<td style="font-size:12px;line-height:25px;width:50px">工号</td>
								<td> <input type="text" name="name" id="userid" style="width:160;height:22px;"/></td>
								</tr>
								<tr>
								<td style="font-size:12px;line-height:25px;">处理状态</td>
								<td><select name="name" style="width:160;height:22px;" id="billstate">
								<option></option>
								<option value="1">总部受理</option>
								<option value="2">处理中</option>
								<option value="3">已结案</option>
								</select></td>
								<td style="font-size:12px;line-height:25px;">时间</td>
								<td><input type="text" id="times"/></td>
								</tr>
								<tr style="display: none">
								<td style="font-size:12px;line-height:25px;">类别</td>
								<td> <select name="name" style="width:160;height:22px;">
								<option  >***************</option>
								<option value="预约">预约</option><option value="咨询">咨询</option><option value="挂失">挂失</option>
								<option value="投诉">投诉</option><option value="退卡">退卡</option>
								</select></td>
								</tr>
								</table>
						</div>
						</div>
		 	     	 </td>
		 	     		<td width="20%"><table width="100%" border="0" height="100%" style="border:1px solid #A3C0E8;font-size:12px;height:750px">
			 	     				<tr>
									    <td height="17px" valign="top"  colspan="2" ><div id="topburcall"></div></td>
									  </tr>
									   <tr>
									     <td  height="25px"  style="font-size:12px;line-height:20px;"><font color="blue">&nbsp;用户编号： </font> <input id="calluserid_show" readonly="true" style="border:0px;background-color:transparent;"/></td>
									   </tr>
									  <tr>
									     <td  height="25px" style="font-size:12px;line-height:20px;"><font color="blue">&nbsp;单据编号： </font> <input id="callbillid" readonly="true" style="border:0px;background-color:transparent;"/></td>
									   </tr>
									   <tr>
									    <td height="25px" style="font-size:12px;line-height:20px;" class="l-table-edit-td">&nbsp;来电号码：     <input name="call_no"  id="call_no" readonly="true" style="border:0px;background-color:transparent;"/>
									    </td>
									  </tr>
									   <tr>
									    <td height="25px"   style="font-size:12px;line-height:20px;">&nbsp;会员姓名： <input id="membername" readonly="true" style="border:0px;background-color:transparent;"/></td>
									  </tr>
									  <tr>
									    <td height="25px"    style="font-size:12px;line-height:20px;">&nbsp;会员卡号： <input id="membernotocard" readonly="true" style="border:0px;background-color:transparent;"/></td>
									  </tr>
									  <tr>
									    <td height="25px"   style="font-size:12px;line-height:20px;">&nbsp;会员卡类型 ： <input id="cardtype" readonly="true" style="border:0px;background-color:transparent;"/></td>
									  </tr>
									   <tr>
									    <td height="25px"  style="font-size:12px;line-height:20px;">&nbsp;接听号码 ： <input id="agent_num" readonly="true" style="border:0px;background-color:transparent;"/></td>
									  </tr>
									  <tr>
									    <td height="25px"  style="font-size:12px;line-height:20px;">&nbsp;摘机时间：  <input id="offer_time" readonly="true" style="border:0px;background-color:transparent;"/></td>
									  </tr>
									   <tr>
									   <td height="17px" colspan="2" valign="top" ><div id="editCurMarkInfo"></div> </td> 
									   </tr>
									  <tr>
									   <td colspan="2" valign="top" height="80px"><textarea  id="callmark" name="callmark" style="height:70;width:100%"></textarea></td>
									 </tr>
									 <tr>
									  		<td colspan="2" valign="top" height="250px"><div id="reports"></div></td>
									  </tr>
			 	     				</table>
	 	     </tr>
 		 
	</table>
</body>
</html>
	<script language="JavaScript">
	  	 	var contextURL="<%=request.getContextPath()%>";
	  	 	// document.oncontextmenu=new Function("event.returnValue=false;"); 
	  	 	function check(){
				var r=new Array();
				var obj=document.getElementsByTagName("input")
				for(var i=0;i<obj.length;i++)
					if(obj[i].type=="radio")
						if(r[obj[i].name]==null && !obj[i].checked) r[obj[i].name]=obj[i].name
						else if(obj[i].checked) r[obj[i].name]=0
					for(var i in r)
					if(r[i]!=0){alert(r[i]+"没选择");
					return false;
				}
			}
	  	 	
	    //确认单据
	     function uporders(){
				 try
		         {
				     var    requestUrl ="ac002/uporders.action";
				     var    params="ordertimess="+document.getElementById("showtime").value;
				      		params=params+"&complydetails="+document.getElementById("complydetails").value;
				 	   		opener.sendRequestForParams(requestUrl,'',params );
				 	   		$.ligerDialog.success("预约信息保存成功！")
	      		}catch(e){alert(e.message);}
	    }
	</script>
