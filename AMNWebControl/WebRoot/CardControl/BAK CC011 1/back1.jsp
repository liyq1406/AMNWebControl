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
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
				<tr >
				<td height="50px" colspan="3">	
					<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
					<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:50px" >
						<tr>
						
							<td width="70%">&nbsp;</td>
							<td width="35"><a><img src="<%=ContextPath%>/common/funtionimage/add.png" 		style="width:30;height:30" alt="新增"></img></a></td>
							<td width="35"><a><img src="<%=ContextPath%>/common/funtionimage/refresh.png" 	style="width:30;height:30" alt="刷新"></img></a></td>
							<td width="35"><a><img src="<%=ContextPath%>/common/funtionimage/post.png" 	style="width:30;height:30" alt="保存"></img></a></td>
							<td width="35"><a><img src="<%=ContextPath%>/common/funtionimage/print.png" 	style="width:30;height:30" alt="打印"></img></a></td>
							<td width="200">
							<s:textfield name="strSearchBillId" id="strSearchBillId" theme="simple" 
							onblur="if(this.value==''){this.value='输入收银单号或小单后查询';};" 
							onfocus="if(this.value=='输入收银单号或小单后查询'){this.value='';}" 
							value="输入收银单号或小单后查询"  
							onMouseOver="this.style.border='1px solid #6C0'"   
							onMouseOut="this.style.border='1px solid #ccc' "  style="width:160px;height:30"/> <a><img src="<%=ContextPath%>/common/funtionimage/search.png" 	style="width:30;height:30" alt="查询"></img></a></td>
					
						</tr>
					</table>
					</div>
				</td>
				</tr>
				<tr >
				<td height="390" colspan="3">
					<div id="commoninfodivdetial" style="margin:0; padding:0"></div>
				</td>
				</tr>
				<tr >
					<td valign="top" height="240"><div id="commoninfodivdetial_Pro" style="margin:0; padding:0"></div></td>
					<td width="20%"> &nbsp;</td>
					<td valign="top" width="80%" height="240">
						<table width="100%"  border="1" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
						<tr>
						
							<td colspan="2" height="20">现金支付:</td>
						</tr>
						<tr>
							<td align="center" height="35" >
							<img src="<%=ContextPath%>/common/funtionimage/paycode2.png"		style="width:30;height:30" alt="现金支付"></img>
							现金支付&nbsp;￥：
						</td>
						
						<td align="center">
							<img src="<%=ContextPath%>/common/funtionimage/paycode2.png"		style="width:30;height:30" alt="银行卡支付"></img>
							POS支付&nbsp;￥：
						</td>
					
						</tr>
						<tr>
						<td align="center"   >
							<img src="<%=ContextPath%>/common/funtionimage/paycode2.png"		style="width:30;height:30" alt="OK卡支付"></img>
							OK卡支付&nbsp;￥：
						</td>
					
						<td align="center">
							<img src="<%=ContextPath%>/common/funtionimage/paycode2.png"		style="width:30;height:30" alt="指付通支付"></img>
					    	指付通￥：&nbsp;&nbsp;&nbsp;
						</td>
						</tr>
						<tr><td colspan="5" height="20">账户支付:</td></tr>
						<tr>
						<td align="center" height="35" >
							<img src="<%=ContextPath%>/common/funtionimage/paycode1.png"		style="width:30;height:30" alt="现金支付"></img>
							储值金额&nbsp;￥：
						</td>
						
						<td align="center">
							<img src="<%=ContextPath%>/common/funtionimage/paycode1.png"		style="width:30;height:30" alt="银行卡支付"></img>
							疗程金额&nbsp;￥：
						</td>
						</tr>
						<tr>
						<td align="center" height="35" >
							<img src="<%=ContextPath%>/common/funtionimage/paycode1.png"		style="width:30;height:30" alt="现金支付"></img>
							现金抵用&nbsp;￥：
						</td>
						
						<td align="center">
							<img src="<%=ContextPath%>/common/funtionimage/paycode1.png"		style="width:30;height:30" alt="银行卡支付"></img>
							项目抵用&nbsp;￥：
						</td>
						</tr>
						<tr>
						<td align="center" height="35" >
							<img src="<%=ContextPath%>/common/funtionimage/paycode1.png"		style="width:30;height:30" alt="现金支付"></img>
							条码卡&nbsp;&nbsp;&nbsp;&nbsp;￥：
						</td>
						<td>&nbsp;</td>
						</tr>
						</table>
					</td>
				
				</tr>
				</table>
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