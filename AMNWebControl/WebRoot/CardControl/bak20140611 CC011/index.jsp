<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>

     <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
   	 <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/datePicker.css" rel="stylesheet" type="text/css" /> 
 	 <link href="<%=ContextPath%>/common/pqgrid/jquery-ui.css" rel="stylesheet" type="text/css" /> 
 	 <link href="<%=ContextPath%>/common/gridview/gridview.css" rel="stylesheet" type="text/css" /> 
 	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.datePicker-min.js" type="text/javascript"></script>   
    <script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script> 	
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.blockUI.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/jquery/exporting.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/highcharts.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/mindsearchitem.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC011/cc011.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC011/ipad.js"></script>
	<script language="vbscript">
			function toAsc(str)
			toAsc = hex(asc(str))
			end function
	</script>
	
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
	#fd{ 
 		 width:600px;
		 height:200px;
		 background:#EDF1F8; 
		 border: 1px solid #849BCA;
		 margin-top:2px;
		 margin-left:2px;
		 float:left;
		 overflow:hidden;
		 position:absolute;
		 left:675px;
		 top:68%;
		 cursor:move;
		 float:left;
		 /*filter:alpha(opacity=50);*/
	}
	
	#specialCost{ 
 		 width:150px;
		 height:220px;
		 background:#EDF1F8; 
		 border: 1px solid #849BCA;
		 margin-top:2px;
		 margin-left:2px;
		 float:left;
		 overflow:hidden;
		 position:absolute;
		 display:none;
		 left:5px;
		 top:68%;
		 cursor:move;
		 float:left;
		 /*filter:alpha(opacity=50);*/
	}
 </style>
</head>
<body onload="initload()">
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="cc011layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
		 <div position="center"   id="designPanel"  style="width:100%;" > 
		 	<form name="consumCenterForm" method="post"  id="consumCenterForm">
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
				<tr>
					<td > <div id="toptoolbarCard"></div> </td>
					<td > <div id="toptoolbar"></div> </td>
					<td  > <div id="toptoolbarserch"></div> </td>
				</tr>
			
				<tr >
				<td valign="top" colspan="3" >	
					<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
						<tr>
							<td valign="top" height="200"  width="570"><div id="commoninfodivdetial_Pro" style="margin:0; padding:0"></div></td>
							<td valign="top" height="200" ><div id="commoninfodivdetial_cardInfo" style="margin:0; padding:0"></div></td>
							<td valign="top" height="200"  ><div id="commoninfodivdetial_payInfo" style="margin:0; padding:0"></div></td>
							<td valign="top" height="200"  ><div id="commoninfodivdetial_padInfo" style="margin:0; padding:0"></div></td>
						</tr>
					</table>
					
				</td>
				</tr>
				<tr >
					<td   valign="top"  colspan="3">
						<div id="toptoolbardetial"></div> 
					</td>
				</tr>
				
				<tr >
					<td   valign="top"  colspan="3">
						<div id="commoninfodivdetial" style="margin:0; padding:0" ></div>
					</td>
				</tr>
				
				
				</table>
				<input type="hidden" name="curMconsumeinfo.id.csbillid" id="csbillid" />
				<input type="hidden"  name="curMconsumeinfo.id.cscompid" id="cscompid" />
				<input type="hidden" id="billflag" name="billflag" value="0"/>
				<input type="hidden" name="curMconsumeinfo.cscardno" id="cscardno"  />
				<input type="hidden"  name="curMconsumeinfo.cardsource" id="cardsource"   />
				<input type="hidden"  name="curMconsumeinfo.cscardtypeName" id="cscardtypeName" />
				<input type="hidden" name="curMconsumeinfo.cscardtype" id="cscardtype"   />
				<input type="hidden"  name="cardHomeSource" id="cardHomeSource"  />
				<input type="hidden" name="curMconsumeinfo.csname" id="csname"  />
				<input type="hidden"  name="curMconsumeinfo.cscurkeepamt" id="cscurkeepamt"  />
				<input type="hidden"  name="curMconsumeinfo.csdate" id="csdate"  />
				<input type="hidden"  name="curMconsumeinfo.csstarttime" id="csstarttime" />
				<input type="hidden"  name="curMconsumeinfo.cscurdepamt" id="cscurdepamt"  />
				<input type="hidden"  name="curMconsumeinfo.csmanualno" id="csmanualno" />
				<input type="hidden"  name="cardpointamt" id="cardpointamt"   />
				<input type="hidden"  name="cardsendamt" id="cardsendamt"   />
				<input type="hidden"  name="diyongcardnoamt" id="diyongcardnoamt"  />
				<input type="hidden"  id="strPayMode1" name="strPayMode1"/>
				<input type="hidden"  id="dPayAmt1" name="dPayAmt1"/>
				<input type="hidden"  id="strPayMode2" name="strPayMode2"/>
				<input type="hidden"  id="dPayAmt2" name="dPayAmt2"/>
				<input type="hidden"  id="strPayMode3" name="strPayMode3"/>
				<input type="hidden"  id="dPayAmt3" name="dPayAmt3"/>
				<input type="hidden"  id="strPayMode4" name="strPayMode4"/>
				<input type="hidden"  id="dPayAmt4" name="dPayAmt4"/>
				<input type="hidden"  id="strPayMode5" name="strPayMode5"/>
				<input type="hidden"  id="dPayAmt5" name="dPayAmt5"/>
				<input type="hidden"  id="strPayMode6" name="strPayMode6"/>
				<input type="hidden"  id="dPayAmt6" name="dPayAmt6"/>
			
				</form>
				
				<div id="printContent" style="position: absolute; margin: 0; z-index:-2;width: 100%;display: none;">
					<table id="texts" style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td colspan="4">日期：<label id="billDat"></label>&nbsp;&nbsp;流水号：<label id="billnop"></label><br /><br /></td>
					</tr>
					<tr>
						<td colspan="4">会员：<label id="memberCardId"></label><br /><br /></td>
					</tr>
					<tbody id="info">
						
					</tbody>
					
					</table>
					<table id="line1" cellspacing="0" cellpadding="0" style="font: 12px;line-height: 6px;text-align: inherit;" width="100%">
					<tr>
						<td colspan="4">
							&nbsp;
						<br /><br /></td>
					</tr>
					</table>
					<table id="text2" cellspacing="0" cellpadding="0" style="font: 12px;line-height: 16px;text-align: inherit;" width="100%">
					</table>
					<table id="line2" cellspacing="0" cellpadding="0" style="font: 12px;line-height: 6px;text-align: inherit;" width="100%">
					<tr>
						<td colspan="4">&nbsp; 
						<br /><br /></td>
					</tr>
					</table>
					<table id="text3" cellspacing="0" cellpadding="0" style="font: 12px;line-height: 16px; text-align: inherit;" width="100%">
					
					</table>
					
					<table id="text4" cellspacing="0" cellpadding="0" style="font: 12px;line-height: 20px; text-align: inherit;" width="100%">
					</table>
					<table id="text5" cellspacing="0" cellpadding="0" style="font: 12px;line-height: 20px; text-align: inherit;" width="100%">
						
					</table>
				</div>   
				 <div id="specialCost" style="filter:alpha(opacity=100);opacity:1;">
				 	<div id="autoxcCost1"></div> 
				 	<div id="autoxcCost2"></div> 
				 	<div id="autoxcCost3"></div> 
				 	<div id="autoxcCost4"></div> 
				 	<div id="autoxcCost5"></div> 
				 	<div id="autoxcCost6"></div> 
				 	<div id="autoxcCost7"></div> 
				 	<div id="autoxcCost8"></div> 
				 	<div id="autoxcCost9"></div> 
				 </div>
				 <div id="fd" style="filter:alpha(opacity=100);opacity:1;">
		 		<div id="autobardetialyjxc"></div> 
 				<div id="autobardetialyj"></div> 
				<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
					<table id="texts" style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
							<tr>
										<td >
											&nbsp;&nbsp;项目编号:&nbsp;&nbsp;
											<input type="text" name="wCostItemNo" id="wCostItemNo"    style="width:100;"   onfocus="itemsearchbegin(this,4);"/>
											&nbsp;&nbsp;
											数量:&nbsp;&nbsp;
											<input type="text"name="wCostPCount" id="wCostPCount" onchange="validatePrice(this);validateFastCostCount(this)"  style="width:70;" />
											&nbsp;&nbsp;<font color="red" size="2">[消费疗程请在序号前加*]</font>
											<input type="hidden" id="itemprice" name="itemprice"/>
											<input type="hidden" id="wCostItemName" name="wCostItemName"/>
											<input type="hidden" id="itemdiscount" name="itemdiscount"/>
										</td>
							</tr>
							<tr>
										<td >
											&nbsp;&nbsp;产品条码:&nbsp;&nbsp;
											<input type="text"name="wCostItemBarNo" id="wCostItemBarNo"    style="width:100;"   onfocus="itemsearchbegin(this,2)"/>
											&nbsp;&nbsp;
											数量:&nbsp;&nbsp;
											<input type="text"name="wCostCount" id="wgCostCount" onchange="validatePrice(this);validateFastCostCount(this)"  style="width:70;" />
											&nbsp;&nbsp;支付方式:&nbsp;&nbsp;
											<select name="itempay" id="itempay" onchange="validateFastCostpay(this)" style="width:100" >
											</select>
											
										</td>
							</tr>
							<tr>
										<td >
											&nbsp;&nbsp;大工工号:&nbsp;&nbsp;
											<input type="text"name="wCostFirstEmpNo" id="wCostFirstEmpNo"  onchange="validateFristEmpNo(this);"    style="width:100;" />
											&nbsp;&nbsp;
											类型:&nbsp;&nbsp;
											<select name="wCostFirstEmptype" id="wCostFirstEmptype" onchange="wCostFirstEmptype(this)"  style="width:70;">
											</select>
											&nbsp;&nbsp;分享金额:&nbsp;&nbsp;
											<input type="text"name="wCostFirstEmpshare" id="wCostFirstEmpshare"  onchange="validatePrice(this);validateCostFirstEmpshare(this)"    style="width:60;" />
											
										</td>
										
							</tr>
							<tr>
										<td >
											&nbsp;&nbsp;中工工号:&nbsp;&nbsp;
											<input type="text"name="wCostSecondEmpNo" id="wCostSecondEmpNo"   onchange="validateSecondEmpNo(this);"    style="width:100;" />
											&nbsp;&nbsp;
											类型:&nbsp;&nbsp;
											<select name="wCostSecondEmptype" id="wCostSecondEmptype" onchange="wCostSecondEmptype(this)"  style="width:70;">
											</select>
											&nbsp;&nbsp;分享金额:&nbsp;&nbsp;
											<input type="text"name="wCostSecondEmpshare" id="wCostSecondEmpshare"  onchange="validatePrice(this);validateCostSecondEmpshare(this)"    style="width:60;" />
											
										</td>
										
							</tr>
							<tr>
										<td>
											&nbsp;&nbsp;小工工号:&nbsp;&nbsp;
											<input type="text"name="wCostthirthdEmpNo" id="wCostthirthdEmpNo"   onchange="validateThirthdEmpNo(this);"   style="width:100;" />
											&nbsp;&nbsp;
											类型:&nbsp;&nbsp;
											<select name="wCostthirthEmptype" id="wCostthirthEmptype" onchange="wCostThirthEmptype(this)"  style="width:70;" >
											</select>
											&nbsp;&nbsp;分享金额:&nbsp;&nbsp;
											<input type="text"name="wCostthirthEmpshare" id="wCostthirthEmpshare"  onchange="validatePrice(this);validateCostThirthEmpshare(this)"    style="width:60;" />
											
										</td>
										
							 </tr>
							
						</table>
						<div id="autobardetial"></div> 
 				
						<input type="hidden"  id="dXjcDiscount" name="dXjcDiscount" value="1"/>
						<input type="hidden" name="SP027Rate" id="SP027Rate" />
						<input type="hidden" name="sumKeepBalance" id="sumKeepBalance" />
						<input type="hidden" name="paramSp097" id="paramSp097" />
						<input type="hidden" name="paramSp098" id="paramSp098" />
						<input type="hidden" name="paramSp104" id="paramSp104" />
						<input type="hidden" name="paramSp105" id="paramSp105" />
						<input type="hidden" name="curCostAmt" id="curCostAmt" />
						<input type="hidden" name="curMfCostCount" id="curMfCostCount" />
						<input type="hidden" name="curMrCostCount" id="curMrCostCount" />
	        	 	</div>
	        	 			
		 </div>
		 </div>  
	
     </div> 
  <div style="display:none;"></div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  		document.write("<div id=\"keysList\" style=\"z-index:2;width:350px;position:absolute;display:none;background:#FFFFFF;border: 2px solid #a4a6a1;font-size:13px;cursor: default;\" onblur> </div>");
        document.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
	</script>