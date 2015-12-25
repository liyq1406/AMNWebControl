<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>

     <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
   	 <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/datePicker.css" rel="stylesheet" type="text/css" /> 
 	 <link href="<%=ContextPath%>/common/pqgrid/jquery-ui.css" rel="stylesheet" type="text/css" /> 
 	 <link href="<%=ContextPath%>/common/pqgrid/pqgrid.css" rel="stylesheet" type="text/css" /> 
 	 <link href="<%=ContextPath%>/common/pqgrid/pqgrid.min.css" rel="stylesheet" type="text/css" /> 
 	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>


    <script src="<%=ContextPath%>/common/pqgrid/jquery.min.js"  type="text/javascript"></script>
    <script src="<%=ContextPath%>/common/pqgrid/jquery-ui.min.js"  type="text/javascript"></script>
       	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.datePicker-min.js" type="text/javascript"></script>   
        <script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/pqgrid/pqgrid.min.js"  type="text/javascript"></script>
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script> 	
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/jquery.blockUI.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/jquery/exporting.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/highcharts.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/mindsearchitem.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
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
						<div id="commoninfodivdetial" style="margin:0; padding:0,display:none" ></div>
						<div id="commoninfodivdetial_pq" style="margin:0; padding:0" ></div>
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
						<td colspan="4">1l&nbsp; 
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
				
		 </div>     

	 	<div position="right">
        		<table width="100%" height="100%" border="1" cellspacing="1" cellpadding="0" id="functionGrid">
       			 	<tr height="30%">
	        			<td valign="top" align="left">
	        				<div id="fastTab" style="width: 270px;overflow:hidden; border:1px solid #A3C0E8;font: 12px; "> 
	        					<div title="营业日期"  >
	        	 					<div class="datepicker"></div>
	        	 				</div>
	        					<div title="快速录单" >
		        					<div  style="width:100%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
											<table id="texts" style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">

												<tr>
													<td>
														<input  type="checkbox" id="fastItem3" name="fastItem" value="302" onclick="validateFastItemCheck(this,2,'dSp076Price')"/>&nbsp;&nbsp; 洗剪吹(<label id="lbfastItem3"></label>)
													</td>
												
													<td>
														<input  type="checkbox" id="fastItem4" name="fastItem" value="302" onclick="validateFastItemCheck(this,2,'dSp077Price')"/>&nbsp;&nbsp; 洗剪吹(<label id="lbfastItem4"></label>)
													</td>
												</tr>
												<tr>
													<td>
														<input  type="checkbox" id="fastItem5" name="fastItem" value="302" onclick="validateFastItemCheck(this,2,'dSp078Price')"/>&nbsp;&nbsp; 洗剪吹(<label id="lbfastItem5"></label>)
													</td>
												
													<td>
														<input  type="checkbox" id="fastItem6" name="fastItem" value="302" onclick="validateFastItemCheck(this,2,'dSp079Price')"/>&nbsp;&nbsp; 洗剪吹(<label id="lbfastItem6"></label>)
													</td>
												</tr>
											</table>
										</div>
									<div  style="width:100%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
									<table id="texts" style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
								
									<tr>
										<td>
											项目编号
										</td>
										<td>
											<input type="text"name="wCostItemNo" id="wCostItemNo"    style="width:140;" onchange="validateProjectNo(this);"  onfocus="itemsearchbegin(this,1)"/>
											<img src="<%=ContextPath%>/common/funtionimage/opengrid.gif" onclick="loadProjectInfo('wCostItemNo')" />
											&nbsp;&nbsp;
										</td>
									</tr>
									<tr>
										<td>
											项目名称
										</td>
										<td>
											<input type="text"name="wCostItemName"  id="wCostItemName"    readonly="true" style="width:180;"  />
										</td>
									</tr>
									<tr>
										<td>
											大工工号
										</td>
										<td>
											<input type="text"name="wCostFirstEmpNo" id="wCostFirstEmpNo"  onchange="validateFristEmpNo(this);"    style="width:60;" />
												<img src="<%=ContextPath%>/common/funtionimage/opengrid.gif"  onclick="loadStaffInfo('wCostFirstEmpNo')"/>
											<input type="text"name="wCostFirstEmpName" id="wCostFirstEmpName"  readonly="true"  style="width:80;" />
										</td>
									</tr>
									<tr>
										<td>
											中工工号
										</td>
										<td>
											<input type="text"name="wCostSecondEmpNo" id="wCostSecondEmpNo"   onchange="validateSecondEmpNo(this);"   style="width:60;" />
												<img src="<%=ContextPath%>/common/funtionimage/opengrid.gif"  onclick="loadStaffInfo('wCostSecondEmpNo')"/>
											<input type="text"name="wCostSecondEmpName" id="wCostSecondEmpName" readonly="true"   style="width:80;" />
										</td>
									</tr>
									<tr>
										<td>
											小工工号
										</td>
										<td>
											<input type="text"name="wCostthirthdEmpNo" id="wCostthirthdEmpNo"   onchange="validateThirthdEmpNo(this);"   style="width:60;" />
												<img src="<%=ContextPath%>/common/funtionimage/opengrid.gif"  onclick="loadStaffInfo('wCostthirthdEmpNo')"/>
											<input type="text"name="wCostthirthdEmpName" id="wCostthirthdEmpName" readonly="true"   style="width:80;" />
										</td>
									</tr>
									<tr>
										<td>
											项目数量
										</td>
										<td>
											<input type="text"name="wCostCount" id="wCostCount" onchange="validatePrice(this);validateFastCostCount(this)"  style="width:60;" />
											<font color="blue">[输入数量后回车]</font>
											
											<input type="hidden" id="itemprice" name="itemprice"/>
											<input type="hidden" id="itempay"   name="itempay"/>
											<input type="hidden" id="itemdiscount" name="itemdiscount"/>
											
										</td>
									</tr>
									</table>
	        	 					</div>
	        	 				</div>
	        	 			
	        	 			</div>
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
	        			<br /></td>
        			</tr>
        		</table>
       </div>
       
     </div> 
  <div style="display:none;"></div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  		document.write("<div id=\"keysList\" style=\"z-index:2;width:250px;position:absolute;display:none;background:#FFFFFF;border: 2px solid #669900;font-size:13px;cursor: default;\" onblur> </div>");
        document.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
	</script>