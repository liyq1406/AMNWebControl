<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>

     <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
   	 <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/datePicker.css" rel="stylesheet" type="text/css" /> 
   	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
   	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.datePicker-min.js" type="text/javascript"></script>    
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script> 	
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.blockUI.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/jquery/exporting.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/highcharts.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC011/cc011.js"></script>

	
<style type="text/css"> 
  
  	  body,html{height:100%;}
	    body{ padding:0px; margin:0;   overflow:hidden;}  
	    .l-link{ display:block; height:16px; line-height:16px; padding-left:10px; text-decoration:underline; color:#333;}
	    .l-link2{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
	    .l-link3{text-decoration:underline;  margin-left:2px;margin-right:2px;}
	    .l-layout-top{background:#102A49; color:White;}
	    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
		 #dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;}
    /*日历*/
    .mh_date{width:249px; height:20px; line-height:20px; padding:5px; border:2px #D6D6D6 solid; cursor:pointer; background:url('<%=ContextPath%>/common/ligerui/images/dateIco.png') no-repeat right center;}
 </style>
</head>
<body onload="initload()">
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="cc011layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
		 <div position="center"   id="designPanel"  style="width:100%;" > 
		 	<form name="consumCenterForm" method="post"  id="consumCenterForm">
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
				<tr >
				<td height="30px" colspan="3">	
					<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
					<table width="99%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:30px" >
						<tr>
							<td width="50%"><font color="bule" size="4">消费单号:&nbsp;&nbsp;&nbsp;<label id="lbBillId"></label>		
							<s:hidden name="curMconsumeinfo.id.csbillid" id="csbillid" theme="simple" />
							<s:hidden name="curMconsumeinfo.id.cscompid" id="cscompid" theme="simple" />
							</font></td>
							<td width="40%" align="right"><a><img src="<%=ContextPath%>/common/funtionimage/refresh.png" 	style="width:25;height:25" alt="刷新"></img></a>
								<a><img src="<%=ContextPath%>/common/funtionimage/post.png" 	style="width:25;height:25" alt="保存"></img></a>
								<a><img src="<%=ContextPath%>/common/funtionimage/print.png" 	style="width:25;height:25" alt="打印"></img></a>
								
								<s:textfield name="strSearchBillId" id="strSearchBillId" theme="simple" 
							onblur="if(this.value==''){this.value='输入收银单号或小单后查询';};" 
							onfocus="if(this.value=='输入收银单号或小单后查询'){this.value='';}" 
							value="输入收银单号或小单后查询"  
							onMouseOver="this.style.border='1px solid #6C0'"   
							onMouseOut="this.style.border='1px solid #ccc' "  style="width:160px;height:30"/> <a><img src="<%=ContextPath%>/common/funtionimage/search.png" 	style="width:25;height:25" alt="查询"></img></a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						
						</tr>
					</table>
					</div>
				</td>
				</tr>
				<tr >
				<td valign="top" >	
					<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
						<tr>
							<td width="400" valign="top">
							<div  style="width:100%;height:200  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
							<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
							<tr>
							<td width="80"><s:select id="billflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="billflag" list="#{0:\"散客\",1:\"内部卡\",2:\"收购卡\"}"
										listKey="key" theme="simple" listValue="value" />
							</td>
							<td >
								<div id="readCurCardInfo"></div>
							</td>
							<td></td>
							<td ><div id="ratebutton"></div></td>
							</tr>
							<tr>
							<td><font color="blue">会员卡号</font></td>
							<td><s:textfield name="curMconsumeinfo.cscardno" id="cscardno" theme="simple" readonly="true" style="width:140;" onchange="validateCscardno(this)" /></td>
							<td>来源</td>
							<td><s:textfield name="curMconsumeinfo.cardsource" id="cardsource" theme="simple" readonly="true" style="width:70;" /></td>
							
							</tr>
							<tr>
							<td><font color="blue">卡类型</font></td>
							<td><s:textfield name="curMconsumeinfo.cscardtypeName" id="cscardtypeName" theme="simple" readonly="true" style="width:140;"  />
								<s:hidden name="curMconsumeinfo.cscardtype" id="cscardtype" theme="simple"  /></td>
							<td>归属</td>
							<td><s:textfield name="cardHomeSource" id="cardHomeSource" theme="simple" readonly="true" style="width:70;" /></td>
							</tr>
							<tr>
							<td><font color="blue">会员姓名</font></td>
							<td><s:textfield name="curMconsumeinfo.csname" id="csname" theme="simple" readonly="true" style="width:140;"/></td>
							<td>余额</td>
							<td><s:textfield name="curMconsumeinfo.cscurkeepamt" id="cscurkeepamt" theme="simple" readonly="true" style="width:70;" /></td>
							</tr>
							<tr>
							</tr>
							<tr>
							<td>消费日期</td>
							<td><s:textfield name="curMconsumeinfo.csdate" id="csdate" theme="simple" readonly="true" style="width:140;" /></td>
							<td>欠款</td>
							<td><s:textfield name="curMconsumeinfo.cscurdepamt" id="cscurdepamt" theme="simple" readonly="true" style="width:70;" /></td>
							</tr>
							<tr>
							<td>手工小单</td>
							<td><s:textfield name="curMconsumeinfo.csmanualno" id="csmanualno" theme="simple" readonly="true" style="width:140;"  /></td>
							<td>积分</td>
							<td><s:textfield name="cardpointamt" id="cardpointamt" theme="simple" readonly="true" style="width:70;"  /></td>
							</tr>
							<tr>
							<td>顾客类型</td>
							<td><s:radio id="csertype" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curMconsumeinfo.csertype" list="#{0:\"新客\",1:\"老客\",2:\"嘉宾\"}"
										listKey="key" theme="simple" listValue="value" /></td>
							</tr>
							<tr>
							<td>顾客性别</td>
							<td><s:radio id="csersex" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curMconsumeinfo.csersex" list="#{0:\"女客\",1:\"男客\"}"
										listKey="key" theme="simple" listValue="value" /></td>
							</tr>
							</table>
							</div>
							</td>	
							<td valign="top" width="210">
								<table id="payTable" width="100%"  border="1" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
								<tr>
									<td align="center" width="70" height="65">
											<img src="<%=ContextPath%>/common/funtionimage/paycode2.png"		style="width:28;height:28" alt="现金支付"></img>
											<br/>￥：3000.0
											
									</td>
									<td align="center" width="70" height="65">
										
									</td>
									<td align="center" width="70" height="65">
										
									</td>
								</tr>
								<tr>
								<td align="center" width="70" height="65">
									
								</td>
								<td align="center" width="70" height="65">
									
								</td>
									<td align="center" width="70" height="65">
										
									</td>
								</tr>
								<tr >
								 	<td  height="40" colspan="3"><font color="blue">项目:</font>&nbsp;1000.0
								 	<br/><font color="blue">产品:</font>&nbsp;2000.0	
								 	<br/><font color="red">总额:</font>&nbsp;3000.0	</td>
								</tr>
								
								</table>
							</td>
							<td valign="top" height="200" ><div id="commoninfodivdetial_Pro" style="margin:0; padding:0"></div></td>
							
						</tr>
					</table>
					
				</td>
				</tr>
				<tr >
					<td height="70%"  valign="top">
						<div id="commoninfodivdetial" style="margin:0; padding:0"></div>
					</td>
				</tr>
				
				</table>
				</form>
		 </div>     

	 	<div position="right">
        		<table width="100%" height="100%" border="1" cellspacing="1" cellpadding="0" id="functionGrid">
       			 	<tr height="30%">
	        			<td valign="top" align="left">
	        	 		<div class="datepicker"></div>
	 					</td>
       				 </tr>
        			<tr height="50%">
	        			<td valign="top" align="left">
	        	  		<div id="dataAnalysis" style="width: 270px;overflow:hidden; border:1px solid #A3C0E8; "> 
				            <div title="营业分析" style="height:380px">
								  <div id="pic_show_div" >
									<div id='pic_div' style="width:270px; height:380px; margin: 0 auto;"></div> 
								</div>
				            </div>
	        			</div>
	        			</td>
        			</tr>
        		</table>
       </div>
       
     </div> 
  <div style="display:none;"></div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  	
	</script>