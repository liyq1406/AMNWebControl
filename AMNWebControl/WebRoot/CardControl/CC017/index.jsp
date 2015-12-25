<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*,com.amani.tools.CommonTool"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/lab.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/mindsearchitem.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC017/cc017.js"></script>
	<script language="vbscript">
			function toAsc(str)
			toAsc = hex(asc(str))
			end function
	</script>
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
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="cc017layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
		<div position="center"   id="designPanel"  style="width:100%;" title=""> 
			<table  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
				<tr>
					<td valign="top" >
						<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
					</td>
					<td valign="top">	
					<table  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:30px" >
					<tr>
						<td  valign="top">
							<div  style="width:1000;float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<table  id="mainToolTable" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:30px" >
									<tr>
										<td width="700"><font color="bule" size="4">退卡单号:&nbsp;&nbsp;&nbsp;<label id="lbBillId"></label>		
										</font></td>
										<td  align="right">
										    <a onclick="editCurRecord()"><img src="<%=ContextPath%>/common/funtionimage/post.png" 	style="width:25;height:25" alt="保存"></img></a>
											<a><img src="<%=ContextPath%>/common/funtionimage/print.png" 	style="width:25;height:25" alt="打印"></img></a>
											
											<input type="text" name="strSearchContent" id="strSearchContent"  
										onblur="if(this.value==''){this.value='输入单号或卡号后查询';};" 
										onfocus="if(this.value=='输入单号或卡号后查询'){this.value='';}" 
										value="输入单号或卡号后查询"  
										onMouseOver="this.style.border='1px solid #6C0'"   
										onMouseOut="this.style.border='1px solid #ccc' "  style="width:160px;height:30"/>
										 <a  onclick="searchCurRecord()"><img src="<%=ContextPath%>/common/funtionimage/search.png" 	style="width:25;height:25" alt="查询"></img></a>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<td  valign="top">
								<div  style=" height:100%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<form name="detailForm" method="post"  id="detailForm">
								
								<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:13px;line-height:20px;" >
							
								<tr>
									<td >
										<div id="readCurCardInfo"></div>
									</td>
									<td>
										<input type="text" name="curMreturnproinfo.returncardno" id="returncardno"  readonly="true" style="width:100;background:#EDF1F8;"  onchange="validateChangebeforcardno(this)"/>
									</td>
									<td width="80">
										归属门店
									</td>
									<td>
										<input type="text" name="curMreturnproinfo.cardvesting" id="cardvesting"  readonly="true" style="width:100;background:#EDF1F8;"  onchange="validateChangebeforcardno(this)"/>
									</td>
									<td width="80">
										<label id="lboldcardtype">卡类型</label>
									</td>
									<td >
										
										<input type="text" name="curMreturnproinfo.cardtypename" id="cardtypename"  readonly="true" style="width:160;background:#EDF1F8;" />
										<input type="hidden" name="curMreturnproinfo.cardtype" id="cardtype"   />
									</td>
									<td width="80">
										<font color="red">储值余额</font>
									</td>
									<td>
										<input type="text" name="curMreturnproinfo.curkeepamt" id="curkeepamt"  readonly="true" style="width:100;background:#EDF1F8;"  />
									</td>
									
									<td width="80">
										赠送余额
									</td>
									<td >
										<input type="text" name="curMreturnproinfo.cursendamt" id="cursendamt"  readonly="true" style="width:100;background:#EDF1F8;" />
										
									</td>
								</tr>
								
								<tr>
									<td>
										<font color="blue">会员姓名</font>
									</td>
									<td>
										<input type="text" name="curMreturnproinfo.membername" id="membername"  readonly="true" style="width:100;background:#EDF1F8;"  />
									</td>
							
									<td>
										手机号码
									</td>
									<td >
										<input type="text" name="curMreturnproinfo.memberphone" id="memberphone"  readonly="true" style="width:100;background:#EDF1F8;" />
									</td>
										<td>
										<font color="blue">退换日期</font>
									</td>
									<td>
										<input type="text" name="curMreturnproinfo.returndate" id="returndate"  readonly="true" style="width:80;background:#EDF1F8;" />
										<input type="text" name="curMreturnproinfo.returntime" id="returntime"  readonly="true" style="width:80;background:#EDF1F8;" />
									</td>
									<td>
										<font color="red">疗程余额</font>
									</td>
									<td>
										<input type="text" name="curMreturnproinfo.curkeepproamt" id="curkeepproamt" readonly="true" style="width:100;background:#EDF1F8;"/>
									</td>
									<td>
										积分余额
									</td>
									<td >
										<input type="text" name="curMreturnproinfo.curpointamt" id="curpointamt"  readonly="true" style="width:100;background:#EDF1F8;" />
									</td>
								</tr>
								
								<tr>
											<td colspan="10"><div id="commoninfodivdetialpro"></div></td>
								</tr>
								<tr >
											<td colspan="10" color="#0000cc">
												<hr width="100%" />
											</td>
								</tr>
								<tr>
									<td colspan="10" valign="top">
											<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:13px;line-height:25px;" >
											
											<tr>
												<td width="50%">新开卡号:<input type="text" name="curMreturnproinfo.changecardno" id="changecardno"  style="width:100;" onchange="validateNewCardNo(this)" />
													&nbsp;&nbsp;&nbsp;类型:<input type="text" name="curMreturnproinfo.opencardtypename" id="opencardtypename"  readonly="true" style="width:140;background:#EDF1F8;" />
																			<input type="hidden" name="curMreturnproinfo.opencardtype" id="opencardtype"   />
																	
													&nbsp;&nbsp;&nbsp;疗程总额:<input type="text" name="curMreturnproinfo.opentotalamt" id="opentotalamt"   readonly="true" style="width:70;background:#EDF1F8;" onchange="validatePrice(this);"/></td>
					
												<td><div id="insCardInfo"></div></td>
											</tr>
											
											</table>
									</td>
								</tr>
									<tr >
											<td colspan="10" color="#0000cc">
												<hr width="100%" />
											</td>
								</tr>
								
								<tr>
											<td colspan="10">
											<div id="toptoolbardetial"></div>
											<div id="commoninfodivchangepro"></div>
											</td>
								</tr>
								<tr>
									<td colspan="10" >
										<font color="blue">储值返还:</font>
										<input type="text" name="curMreturnproinfo.returnkeeptotalamt" id="returnkeeptotalamt"   style="width:70;background:#EDF1F8;"  onchange="validatePrice(this);validateRechargeAmt(this)"/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<font color="blue">储值兑换:</font>
										<input type="text" name="curMreturnproinfo.changetotalamt" id="changetotalamt"  readonly="true" style="width:70;background:#EDF1F8;"  onchange="validatePrice(this);validateRechargeAmt(this)"/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<font color="red">成本抵扣:</font>
										<input type="text" name="curMreturnproinfo.costproamt" id="costproamt"  style="width:70;"  onchange="validatePrice(this);validateTCostAmt(this)"/>
										
									</td>
								</tr>
								
								
								</table>
								<input type="hidden" name="curMreturnproinfo.id.returncompid" id="returncompid"   />
								<input type="hidden" name="curMreturnproinfo.id.returnbillid" id="returnbillid"   />
								</form>
								
								</div>
						</td>
					</tr>
					</table>
					</td>
				</tr>
			</table>		
		</div>
				        
	</div> 

	
  <div style="display:none;">

</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  		document.write("<div id=\"keysList\" style=\"z-index:2;width:350px;position:absolute;display:none;background:#FFFFFF;border: 2px solid #a4a6a1;font-size:13px;cursor: default;\" onblur> </div>");
        document.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
	</script>