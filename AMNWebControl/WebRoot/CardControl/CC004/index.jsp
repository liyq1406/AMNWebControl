<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
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
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC004/cc004.js"></script>

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

  <div style="display:none;">
  <!-- g data total ttt -->
</div>
 <div id="cc004layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  <div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
	  	<div position="center"   id="designPanel"  style="width:100%;" > 
			 <div id="CC004DetialTab" style="width:100%; height:95%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
				 	<div title="条码卡销售" style="font-size:12px;" style="width:100%; height:98%; ">
						<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
						<tr>
						
						<td width="480" valign="top">
							<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
								<tr>
									<td  width="510">
									&nbsp;条码卡编号：<input id="searchTmkNoKey" type="text" size="10"/> &nbsp;
									&nbsp;条码卡状态：
									<select id="searchTmkStateKey" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"name="searchTmkStateKey" >
									<option value="3">所有</option>
									<option value="0">登记</option>
									<option value="1">正常使用</option>
									</select>
									</td>
									
									<td ><div id="searchTmkInfo"></div></td>
								</tr>
								</table>
							</div>
							<div id="commoninfodivtmk" style="margin:0; padding:0"></div>
						</td>
						<td  valign="top">
						<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
						 <form name="tmkForm" method="post"  id="tmkForm">
						  
						<table width="520" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;">
							<tr>
							<td width="80"><font color="blue">销售单号</font></td>
							<td width="140"><input type="text" name="curMaster.id.salebillid" id="salebillid"  readonly="true" style="width:120;" validate="{maxlength:10,notnull:false}"/></td>
							<td width="60">&nbsp;</td>
							<td width="120">&nbsp;</td>
							<td width="140">&nbsp;</td>
							</tr>
							<tr>
							<td width="80"><font color="blue">条码卡号</font></td>
							<td width="140"><input type="text" name="curMaster.barcodecardno" id="barcodecardno"   style="width:120;" validate="{maxlength:20,notnull:false}" onchange="validateBarcodecardno(this)"/></td>
							<td colspan="2"><font color="red"><label id="barcodecardsource"></label></font></td>
							<td width="140">&nbsp;</td>
							</tr>
							<tr>
							<td width="80">销售日期</td>
							<td width="140"><input type="text" name="curMaster.saledate" id="saledate"  readonly="true" style="width:120;" validate="{maxlength:10,notnull:false}"/></td>
							<td width="80">销售时间</td>
							<td width="120"><input type="text" name="saletime" id="saletime"  readonly="true" style="width:80;" validate="{maxlength:10,notnull:false}"/></td>
							<td width="140">&nbsp;</td>
							</tr>
							<tr>
							<td>条码套餐</td>
							<td colspan="4">

								<select name="curMaster.packageNo" id="packageNo" style="width:200px" onchange="validatePackageNo(this)" >
											<option value="">请选择</option>
										</select>
										
							<input type="hidden" name="packageName" id="packageName" />
							&nbsp;总额&nbsp;&nbsp;<input type="text" name="curMaster.saleamt" id="saleamt" readonly="true" style="width:40;"/>
							&nbsp;有效&nbsp;&nbsp;<input type="text" name="curMaster.saleMonths" id="saleMonths" readonly="true" style="width:40;"/>月
							</td>
							</tr>
							
							<tr>
									<td  ><div id="readCurCardInfo"></div></td>
									<td ><input type="text"name="curMaster.usecardno" id="usecardno" theme="simple"  readonly="true" style="width:120;" /></td>
									<td >卡类型</td>
									<td colspan="2"><input type="text"name="usecardtypename" id="usecardtypename" theme="simple"  readonly="true" style="width:160;" /><input type="hidden" name="curMaster.usecardtype" id="usecardtype"  /></td>
								
							</tr>
							<tr>
									<td >会员姓名</td>
									<td ><input type="text"name="lbmembername" id="lbmembername" theme="simple"  readonly="true" style="width:120;" /> </td>
									<td >手机号码</td>
									<td ><input type="text"name="lbmemberphone" id="lbmemberphone" theme="simple"  readonly="true" style="width:120;" /></td>
									<td>&nbsp;</td>
							</tr>
							<tr>
								<td>储值金额</td>
								<td ><input type="text"name="curkeepAmt" id="curkeepAmt" theme="simple"  readonly="true" style="width:120;" /> </td>
								<td ><font color="blue">兑换金额</font></td>
								<td ><input type="text"name="curMaster.usecardpayamt" id="usecardpayamt" theme="simple"  style="width:120;"  onchange="validatePrice(this)" /></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
							<td>支付方式1</td>
							<td><input type="text" name="firstpaymode" id="firstpaymode"  style="width:120;" validate="{maxlength:10,notnull:false}" onchange="paychange(this)"/></td>
							<td>支付金额</td>
							<td><input type="text" name="curMaster.firstpayamt" id="firstpayamt"   style="width:120;"  onchange="validatePrice(this)" ligerui="{type:'int'}"  validate="{maxlength:10,notnull:false}"/></td>
							<td>&nbsp;</td>
							</tr>
							<tr>
							<td>支付方式2</td>
							<td><input type="text" name="secondpaymode" id="secondpaymode"  style="width:120;" validate="{maxlength:10,notnull:false}" onchange="paychange(this)"/></td>
							<td>支付金额</td>
							<td><input type="text" name="curMaster.secondpayamt" id="secondpayamt"  style="width:120;"  onchange="validatePrice(this)" ligerui="{type:'int'}"  validate="{maxlength:10,notnull:false}"/></td>
							<td>&nbsp;</td>
							</tr>
							<tr>
							<td>第一销售</td>
							<td><input type="text" name="firstsaleempid" id="firstsaleempid"  style="width:120;" validate="{maxlength:20,notnull:false}" onchange="validateEmpIdFirst(this)"/></td>
							<td>分享金额</td>
							<td><input type="text" name="curMaster.firstsaleamt" id="firstsaleamt"  style="width:120;"  onchange="validatePrice(this)"  ligerui="{type:'int'}"  validate="{maxlength:10,notnull:false}"/></td>
							</tr>
							<tr>
							<td>第二销售</td>
							<td><input type="text" name="secondsaleempid" id="secondsaleempid" style="width:120;" validate="{maxlength:20,notnull:false}" onchange="validateEmpIdSecond(this)"/></td>
							<td>分享金额</td>
							<td><input type="text" name="curMaster.secondsaleamt" id="secondsaleamt"  style="width:120;"  onchange="validatePrice(this)"  ligerui="{type:'int'}"  validate="{maxlength:10,notnull:false}"/></td>
							<td ><div id="slaeTmkInfo"></div></td>
							</tr>
							
							<tr>
							<td colspan="5" >
								<div id="commoninfodivsaletmk" style="margin:0; padding:0"></div>
							</td>
							</tr>
							
						</table>
							<input type="hidden" id="firstpaymode_h" name="curMaster.firstpaymode" />
							<input type="hidden" id="secondpaymode_h" name="curMaster.secondpaymode" />
							<input type="hidden" id="firstsaleempid_h" name="curMaster.firstsaleempid" />
							<input type="hidden" id="secondsaleempid_h" name="curMaster.secondsaleempid" />
							<input type="hidden" id="ishz" name="curMaster.ishz" />
							<input type="hidden" id="operationer" name="curMaster.operationer" />
							</form>
						</div>
						</td>
						</tr>
						</table>
					
					</div>	
					<div title="抵用券查询" style="font-size:12px;" style="width:100%; height:98%; ">
						<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
								<tr>
									<td  width="550">
									&nbsp;抵用券编号：<input id="searchDyqNoKey" type="text" size="10"/> &nbsp;
									&nbsp;抵用券状态：<select id="searchDyqStateKey" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"name="searchDyqStateKey" >
									<option value="3">所有</option>
									<option value="1">正常使用</option>
									<option value="2">已使用</option>
									</select>
									&nbsp;定位券单号：<input id="searchBillId" type="text" size="14"/> &nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td width="60">使用日期：</td>
									<td width="130">
									<input id="searchFromDateKeyDYQ" type="text" size="10"/> 
									</td>
									<td width="130">
									<input id="searchToDateDateKeyDYQ" type="text" size="10"/>
									</td>
									<td ><div id="searchDyqInfo"></div></td>
								</tr>
								</table>
						</div>
						<div id="commoninfodivdiyq" style="margin:0; padding:0"></div>
					</div>	
					<div title="团购卡查询" style="font-size:12px;" style="width:100%; height:98%; ">
						<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
								<tr>
									<td  width="510">
									&nbsp;团购类型：
										<select id="searchCorpsTypeKey" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"name="searchCorpsTypeKey" >
											<option value="3">所有团购</option>
											<option value="1">项目团购</option>
											<option value="2">会员卡团购</option>
										</select>
									&nbsp;团购编号：<input id="searchCorpsNoKey" type="text" size="10"/> &nbsp;
									&nbsp;团购状态：<select id="searchCorpsStateKey" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"name="searchCorpsStateKey" >
											<option value="1">未使用</option>
											<option value="2">已使用</option>
										</select>
									&nbsp;</td>
									
									<td width="60">使用日期：</td>
									<td width="130">
									<input id="searchFromDateKey" type="text" size="10"/>
									</td>
									<td width="130">
									<input id="searchToDateDateKey" type="text" size="10"/>
									</td>
									<td ><div id="searchCorpsInfo"></div></td>
								</tr>
								</table>
						</div>
						<div id="commoninfodivCorps" style="margin:0; padding:0"></div>
					</div>					
			 </div>	
		 </div>		
		  </div>							
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  	
	</script>