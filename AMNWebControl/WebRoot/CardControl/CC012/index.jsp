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
	<script type="text/javascript" src="<%=ContextPath%>/common/mindsearchitem.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC012/cc012.js"></script>
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

  	 <div id="cc012layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
		<div position="center"   id="designPanel"  style="width:100%;" > 
					  
	    <form name="cardChangeForm" method="post"  id="cardChangeForm">
		<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
		<tr>
			
			<td  valign="top">
			<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;">
				<tr height="355">
					<td valign="top" >
						<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
					</td>
					<td valign="top" >
						<div  style=" height:98%; float:left; clear:both; border:1px solid #ccc; overflow:hidden;font-size:12px;  ">
							<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
								<tr>
									<td width="70">&nbsp;&nbsp;微信扫码</td>
									<td colspan="3"><input name="randomno" id="randomno" size="37" onchange="checkRandomno()"></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;<font color="blue">兑换门店</font></td>
									<td  ><input type="text" name="curMproexchangeinfo.id.changecompid" id="changecompid" theme="simple"  readonly="true" style="width:120;" /></td>
								
									<td width="70">&nbsp;&nbsp;<font color="blue">兑换单号</font></td>
									<td ><input type="text" name="curMproexchangeinfo.id.changebillid" id="changebillid" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
								<tr>
									<td  ><div id="readCurCardInfo"></div></td>
									<td ><input type="text" name="curMproexchangeinfo.changecardno" id="changecardno" theme="simple"  readonly="true" style="width:120;" onchange="validateExchangecardno(this)"/></td>
									<td width="70">&nbsp;&nbsp;<font color="blue">兑换账户</font></td>
									<td >
									<select id="changeaccounttype" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"name="curMproexchangeinfo.changeaccounttype"  >
									<option value="2" checked="true">储值账户</option>
									</select>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;卡类型</td>
									<td ><input type="text" name="curMproexchangeinfo.changecardtype" id="changecardtype" theme="simple"  readonly="true" style="width:120;" /></td>
									<td width="70">&nbsp;&nbsp;类型名称</td>
									<td ><input type="text" name="changecardtypename" id="changecardtypename" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;会员姓名</td>
									<td ><input type="text" name="lbmembername" id="lbmembername" theme="simple"  readonly="true" style="width:120;" /> <input type="hidden"name="curMproexchangeinfo.membername" id="membername"   /></td>
									<td width="70">&nbsp;&nbsp;手机号码</td>
									<td ><input type="text" name="lbmemberphone" id="lbmemberphone" theme="simple"  readonly="true" style="width:120;" /><input type="hidden"name="curMproexchangeinfo.memberphone" id="memberphone" /></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;储值账户</td>
									<td ><input type="text" name="curMproexchangeinfo.curaccountkeepamt" id="curaccountkeepamt" readonly="true" style="width:120;" />
									<input type="hidden" name="curMproexchangeinfo.curaccountdebtamt" id="curaccountdebtamt" />
								</td>
									<td width="70">&nbsp;&nbsp;赠送账户</td>
									<td ><input type="text" name="curMproexchangeinfo.cursendaccountkeepamt" id="cursendaccountkeepamt" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;疗程余额</td>
									<td ><input type="text" name="curMproexchangeinfo.curproaccountamt" id="curproaccountamt" theme="simple"  readonly="true" style="width:120;" /></td>
									<td width="70">&nbsp;&nbsp;疗程欠款</td>
									<td ><input type="text" name="curMproexchangeinfo.curproaccountdebtamt" id="curproaccountdebtamt" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;兑换日期</td>
									<td ><input type="text" name="curMproexchangeinfo.changedate" id="changedate" theme="simple"  readonly="true" style="width:120;" /></td>
									<td width="70">&nbsp;&nbsp;兑换时间</td>
									<td ><input type="text" name="curMproexchangeinfo.changetime" id="changetime" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;操作员工</td>
									<td ><input type="text" name="curMproexchangeinfo.changeopationerid" id="changeopationerid" theme="simple"  readonly="true" style="width:120;" /></td>
									<td width="70">&nbsp;&nbsp;操作日期</td>
									<td ><input type="text" name="curMproexchangeinfo.changeopationdate" id="changeopationdate" theme="simple"  readonly="true" style="width:120;" /></td>
								</tr>
									<tr>
									<td width="70">&nbsp;&nbsp;兑换套餐</td>
									<td colspan="3">
										<select name="changePackageNo" id="changePackageNo" style="width:250px" onchange="validatePackageNo(this)" >
											<option value="">请选择</option>
										</select>
									</td>
								</tr>
								<tr>
									<td width="70">&nbsp;&nbsp;疗程折扣</td>
									<td >
										<input type="text" name="dSaleProRate" id="dSaleProRate" theme="simple"  readonly="true" style="width:60;" />
									</td>
									<%--<td align="center"> <div id="managerRate"></div></td>--%>
									<%--<td><div id="manager38Rate"></div></td>--%>
									<%--<td><div id="manager85Rate" style="margin-left: 20px;"></div></td>--%>
									<td>&nbsp;&nbsp;美容积分</td>
									<td >
										<input type="text" name="curMproexchangeinfo.curfaceaccountamt" id="curfaceaccountamt" theme="simple"  readonly="true" style="width:120;" />
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div id="managerRate" style="display: inline;margin-right: 10px;"></div>
									   	<div id="manager85Rate" style="display: inline;"></div>
										<input type="hidden" name="SP028Rate" id="SP028Rate" />
										<input type="hidden" name="curMproexchangeinfo.backcsflag" id="backcsflag" />
									</td>
									<td colspan="2">
										<div id="dis2015" style="display: inline;margin-right: 10px;"></div>
										<div id="editCurRootInfo" style="display: inline;margin-right: 10px;"></div>
									   	<div id="printCurRootInfo" style="display: inline;"></div>
									</td>
								</tr>	
								
							
							</table>
						</div>
					</td>
					<td valign="top"><div id="commoninfodivdetialpro" style="margin:0; padding:0"></div></td>
				</tr>
				<tr >
					<td colspan="3" style="border-bottom:1px #000000 dashed">	</td>
				</tr>
				<tr >
					<td colspan="3" width="100%">
						<div id="toptoolbardetial"></div> 
						<div id="commoninfodivdetial" style="margin:0; padding:0"></div>
					</td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
			</form>
			<input type="hidden" id="SP095Flag" name="SP095Flag"/>
			<input type="hidden" id="SP099Flag" name="SP099Flag"/>
			<input type="hidden" id="SP117Flag" name="SP117Flag"/>
			<input type="hidden" id="SP118Flag" name="SP118Flag"/>
			<input type="hidden" id="rateToRate" name="rateToRate"/>
			<div id="printContent" style="position:absolute;left:10px; top:60px; z-index:-2;display:none">
				<table width="260px" border="0" cellspacing="0" cellpadding="2" id="text">
				<tr>
					<td align="left" width="90">
						日期:&nbsp;
					</td>
					<td>
						<label id="currdate_print">
							&nbsp;
						</label>
					</td>
				</tr>
				<tr>
					<td align="left" width="90">
						会员卡号:&nbsp;
					</td>
					<td>
						<label id="memberCardId_print">
							&nbsp;
						</label>
					</td>
				</tr>
				
				<tr>
					<td align="left" width="90">
						储值余额:&nbsp;
					</td>
					<td>
						<label id="keepAmount_print">
							&nbsp;
						</label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<hr width="100%"/>
					</td>
				</tr>
			</table>
			<table width="260px" border="0" cellspacing="0" cellpadding="2" id="text">
				<tr>
					<td  width="180px" style="font-size:12px">
						兑换疗程名称
					</td>
					<td  width="40px"  style="font-size:12px">
						金额
					</td>
					<td  width="40px"  style="font-size:12px">
						次数
					</td>
					
				</tr>
				<tbody id="changeDetail" style="width:180px;">
				</tbody>
			</table>
			<table width="260px" border="0" cellspacing="0" cellpadding="2" id="text">
				<tr>
					<td colspan="2" >
						<hr width="100%"/>
					</td>
				</tr>
				<tr>
					<td align="left" width="90">
						交易号:&nbsp;
					</td>
					<td>
						<label id="tradebillId_print">
							&nbsp;
						</label>
					</td>
				</tr>
				<tr>
					<td align="left" width="90">
						收银员:&nbsp;
					</td>
					<td>
						<label id="clerkName_print">
							&nbsp;
						</label>
					</td>
				</tr>
				<tr>
					<td align="left" width="90">
						打印时间:&nbsp;
					</td>
					<td>
						<label id="printTime_print">
							&nbsp;
						</label>
					</td>
				</tr>
				<tr>
					<td align="left" width="90">
						客户签名:&nbsp;
					</td>
					<td>&nbsp;
						
					</td>
				</tr>
				<tr>
					<td align="left">&nbsp;
						
					</td>
					<td>&nbsp;
						
					</td>
				</tr>
				<tr>
					<td align="left">
						门店电话:&nbsp;
					</td>
					<td>
						<label id="telephone_print">
							&nbsp;
						</label>
					</td>
				</tr>
				
				<tr>
					
					<td align="left" colspan="2">&nbsp;
						<label id="address_print">
							&nbsp;
						</label>
					</td>
				</tr>
				
			</table>
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
  	 	var currentdate=new Date("<%=new java.text.SimpleDateFormat("yyyy/MM/dd").format(new Date())%>");
  		document.write("<div id=\"keysList\" style=\"z-index:2;width:350px;position:absolute;display:none;background:#FFFFFF;border: 2px solid #a4a6a1;font-size:13px;cursor: default;\" onblur> </div>");
        document.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
	</script>