<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="/include/sysfinal.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>欢迎登入 AMN企业信息管理平台</title>  
   <meta http-equiv="Content-Type" Content="text/html; charset=UTF-8" />
   <meta name="description" Content="SPG Land" />
   <meta name="keywords" Content=" AMN企业信息管理平台,Vanke webidea by brandesse">
   <meta name="author" Content="AMN信息管理" />
   <link type="image/x-icon" rel="shortcut icon" href="favicon.ico" />
   <script type='text/javascript' src='<%=ContextPath%>/login/images/jquery.js'></script>
   <script type="text/javascript" src="<%=ContextPath%>/login/images/jquery.cycle.all.js"></script>
   <link  rel="stylesheet" type="text/css" href="<%=ContextPath%>/login/images/style.css" />
				<!--------------------------show list begin----------------------->
				<STYLE TYPE="text/css">
					img{border:none;}
					.slideDivContinar { height: 250px; width: 950px; padding:0; margin:0; overflow: hidden }
					.slideDiv { height:300px; width: 1050px;top:0; left:0;}
				.STYLE1 {font-size: 36px}
                .STYLE2 {font-size: 18px; color: #006699; }
                .STYLE3 {color: #006699}
                </STYLE>
 
				<script type="text/javascript">
				$(function() {
 					
					var iconImg="<%=ContextPath%>/login/images/pointgray.png"
					var iconImg_over="<%=ContextPath%>/login/images/pintblue.png"
 
 
					$('#slideshow').cycle({
						fx:      'fade', /*
												各个效果
												*blindX
												* blindY
												* blindZ
												* cover
												* curtainX
												* curtainY
												* fade
												* fadeZoom
												* growX
												* growY
												* none
												* scrollUp
												* scrollDown
												* scrollLeft
												* scrollRight
												* scrollHorz
												* scrollVert
												* shuffle
												* slideX
												* slideY
												* toss
												* turnUp
												* turnDown
												* turnLeft
												* turnRight
												* uncover
												* wipe
												* zoom
												*/
						timeout:  6000,
						prev:    '#prev',
						next:    '#next', 
						pager:   '#nav',
						pagerAnchorBuilder: pagerFactory,
						before:        function(currSlideElement, nextSlideElement, options, forwardFlag) {	
							var curIndex=$(currSlideElement).attr("index");
							var curSlidnavtitle=$($("#slideDemo .slidnavtitle")[curIndex]);
							if(curSlidnavtitle.html()!=null){
								curSlidnavtitle.find("img").attr("src",iconImg);
								curSlidnavtitle.find("a").addClass("gray");
							}
 
							var nextIndex=$(nextSlideElement).attr("index");
							var nextSlidnavtitle=$($("#slideDemo .slidnavtitle")[nextIndex]);
							if(nextSlidnavtitle.html()!=null){
								nextSlidnavtitle.find("img").attr("src",iconImg_over);
								nextSlidnavtitle.find("a").removeClass("gray");
							}
						}
					    
					}); 
 
					
					function pagerFactory(idx, slide) {
						var s = idx > 20 ? ' style="display:none"' : '';
						 
						return '<span'+s+' class="m-r-20 slidnavtitle"><a href="#" class="'+(idx==0?"":"gray")+'"><img align="absmiddle" class="m-r-5" src='+(idx==0?iconImg_over:iconImg)+'>'+$(slide).attr("alt")+'</a></span>';
					};
				});
 				</script>


</head>
<body>
<OBJECT id=locator height=0 width=0 classid=CLSID:76A64158-CB41-11D1-8B02-00600806D9B6 VIEWASTEXT></OBJECT>
	<OBJECT id=foo height=0 width=0  classid=CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223></OBJECT>
	<SCRIPT type="text/javascript">
		
   		var service = locator.ConnectServer();
   		var MACAddr ;
   		var IP ;
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
<table width="950" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td><img src="<%=ContextPath%>/login/images/Login_top_tiao1.jpg" width="950" height="14" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><table width="950" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEEEEE">
      <tr>
        <td width="280" height="114" bgcolor="#FFFFFF"><img src="<%=ContextPath%>/login/images/Login_top_mz.jpg" width="200" height="110" /></td>
        <td width="512" valign="bottom" bgcolor="#FFFFFF">&nbsp;</td>
        <td width="198" valign="bottom" bgcolor="#FFFFFF"><div align="center"><span class="font18 STYLE3">连锁协同收银办公系统</span></div></td>
      </tr>
    </table></td>
  </tr>
  
  <tr>
    <td height="100" bgcolor="#FFFFFF"><table width="950" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><div id="slideDemo" style="width:950px;">
            <div id="slideshow" class="slideDivContinar grid2" style="margin:auto;clear:left;">
               <div class="slideDiv grid2  p-t-15 p-b-15 p-l-15 p-r-15" onclick="window.location.href=''" index="0" style="background:url('<%=ContextPath%>/login/images/Login_pro01.jpg') no-repeat;cursor:pointer; " alt="使命" > </div>
              <div class="slideDiv grid2  p-t-15 p-b-15 p-l-15 p-r-15" onclick="window.location.href=''" index="1" style="background:url('<%=ContextPath%>/login/images/Login_pro02.jpg') no-repeat;cursor:pointer; " alt="愿景" > </div>
              <div class="slideDiv grid2  p-t-15 p-b-15 p-l-15 p-r-15" onclick="window.location.href=''" index="2" style="background:url('<%=ContextPath%>/login/images/Login_pro03.jpg') no-repeat;cursor:pointer; " alt="价值观" > </div>
            </div>
          <div class="p-l-10 p-r-10" style="height:20px;">
              <div class="m-t-5">
                <div class="left"> <a href="#" id="prev"><img src="<%=ContextPath%>/login/images/prev.png" /></a> </div>
                <div class="center  left "  style="width:890px " id="nav" ></div>
                <div class="last " ><a href="#" id="next"><img src="<%=ContextPath%>/login/images/next.png" /></a></div>
              </div>
          </div>
        </div>
            <div class="clear"></div>
          <!--------------------------show list end----------------------->        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
  <tr>
    <td height= valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
        <td width="470" height="164" valign="top" class="contxt">欢迎使用AMN企业信息管理平台。请在右边输入用户名和密码登入系统。<br />
          如账号登入遇到问题，请联系管理员。<br /><br /><br /> <font color="#FF0000" ><s:actionerror/></font>         </td>
        <td width="50">&nbsp;</td>
        <td width="430"><table width="430" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="left"><img src="<%=ContextPath%>/login/images/Login_bt_welcome202.gif" width="195" height="40" /></td>
            </tr>
            <tr>
              <td height= valign="top" valign="top" class="LeftLine">
              <form id="frmLogin_10904_1" name="frmLogin_10904_1" method="post" action="VerifyLogin.action" onSubmit="return checkall('10904_1','compid','userid','pwd','0','1');">
			  <INPUT type=hidden name="logintype" value="1">
			  <input id="licenseCode" name="licenseCode" type="hidden"/>
              <input id="macAddr" name="macAddr" type="hidden"/>
              <input id="ipAddr"  name="ipAddr"  type="hidden"/>
			  <input name="gopage" value=""  type=hidden >
									<table width="265" border="0">
                                      <tr>
                                        <td width="67" align="right" valign="middle">公司别：</td>
                                        <td width="177"><input class="text" name="compid" id="compid" style="width:120px"/ value="001"></td>
                                        <td width="10"></td>
                                      </tr>
                                      
                                      <tr>
                                        <td width="67" align="right" valign="middle">用户名：</td>
                                        <td width="177"><input class="text" name="userid" id="userid" style="width:120px" value="amani"/></td>
                                        <td width="10">&nbsp;</td>
                                      </tr>
                                    
                                      <tr>
                                        <td align="right" width="67"  valign="middle">密&nbsp;&nbsp;&nbsp;码：</td>
                                        <td><input class=text type=password size=20 name=pwd id="pwd" style="width:120px" value="amn09admin09"/></td>
                                        <td>&nbsp;</td>
                                      </tr>
                                      <tr>
                                        <td height="45"><s:actionerror /></td>
                                        <td valign="middle"><button id="submitbutton_10904_1" type="submit" style="BORDER-RIGHT: #bdbdbd 1px solid; BORDER-TOP: #bdbdbd 1px solid; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#E7E7E7); BORDER-LEFT: #bdbdbd 1px solid; CURSOR: hand; BORDER-BOTTOM: #bdbdbd 1px solid;width:70px;height:25px;">
													 登 录 
					</button>
													&nbsp;
													<button id="resetbutton_10904_1" type="reset" style="BORDER-RIGHT: #bdbdbd 1px solid; BORDER-TOP: #bdbdbd 1px solid; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#E7E7E7); BORDER-LEFT: #bdbdbd 1px solid; CURSOR: hand; BORDER-BOTTOM: #bdbdbd 1px solid;width:70px;height:25px;">
									    重 置										</button></td>
                                        <td>&nbsp;</td>
                                      </tr>
                    </table>
                    		
									
              </form>      
               </td>
            </tr>
         
        </table></td>
      </tr>
    </table></td>
  </tr>
 

  <tr>
    <td><table width="950" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="<%=ContextPath%>/login/images/Login_page_di.jpg" width="950" height="20" /></td>
      </tr>
      <tr>
        <td align="center">Copyright  2013 AMN All  rights reserved. </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
						
</body>
</html>
<script language="JavaScript">
function checkall(eid,compid,userparamname,userparampass,needvalidate,usbType)
{
	var errMessage="";
	var frmLogin = document.getElementById("frmLogin_10904_1");	
	var loginid = document.getElementById(userparamname);
	var logincompid=document.getElementById(compid);
	var userpassword = document.getElementById(userparampass);
	if (logincompid&&logincompid.value=="") 
	{
		errMessage="请输入公司别！";
		alert(errMessage);
		logincompid.focus();
		return false;
	}
	if (loginid&&loginid.value=="") 
	{
		errMessage="请输入用户名！";
		alert(errMessage);
		loginid.focus();
		return false;
	}
	if (userpassword&&userpassword.value=="") 
	{
		errMessage="请输入密码！";
		alert(errMessage);
		userpassword.focus();
		return false;
	}
	
   frmLogin.submit();
}

		</script>
