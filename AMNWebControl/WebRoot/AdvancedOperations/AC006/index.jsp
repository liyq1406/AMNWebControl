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
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC006/ac006.js"></script>
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

  	 <div id="ac006layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
  	 	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
	  	<div position="center"   id="designPanel"  style="width:100%;" title="系统卡登记"> 
	  		<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:35px;" >
			<tr>
			<td valign="top">
					<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
					<table  border="0" cellspacing="1" cellpadding="0" style="width:100%;font-size:12px;line-height:35px;" >
					<tr>
        						<td colspan="2" width="100%" align="center" bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;">储值卡号段登记</td>
			       	</tr>
					<tr>
						<td width="60">归属门店</td>
						<td>
							<input id="strInerHomeCompid" name="strInerHomeCompid" type="text" size="6"  readonly="true"/>
							<input id="strInerHomeCompName" name="strInerHomeCompName" type="text" size="15" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td>开始卡号</td>
						<td><input id="strFromInerCardNo" name="strFromInerCardNo" type="text" size="20"/></td>
					</tr>
					<tr>
						<td>结束卡号</td>
						<td><input id="strToInerCardNo" name="strToInerCardNo" type="text" size="20"/></td>
					</tr>
					<tr>
						<td>卡类型</td>
						<td><select id="strInerCardType" name="strInerCardType"  style="width:200"></select></td>
						
					</tr>
					<tr>
						<td colspan="2"><div id="innerCardBarTool"></div></td>
					</tr>
					</table>
					</div>
			</td>
			<td valign="top" >
					<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
					<table border="0" cellspacing="1" cellpadding="0" style="width:100%; font-size:12px;line-height:35px;" >
					<tr>
        						<td colspan="2" width="100%" align="center" bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;">条码卡号段登记</td>
			       	</tr>
			       	
					<tr>
						<td width="60">归属门店</td>
						<td>
							<input id="strTmHomeCompid" name="strTmHomeCompid" type="text" size="6"  readonly="true"/>
							<input id="strTmHomeCompName" name="strTmHomeCompName" type="text" size="15" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td>卡前缀</td>
						<td><input id="strTmCardBef" name="strCardBef" type="text" size="20" maxlength="5"/></td>
					</tr>
					<tr>
						<td>开始卡号</td>
						<td><input id="strFromTmCardNo" name="strFromTmCardNo" type="text" size="20"/></td>
					</tr>
						<tr>
						<td>结束卡号</td>
						<td><input id="strToTmCardNo" name="strToTmCardNo" type="text" size="20"/></td>
					</tr>
					<tr>
						<td align="center"><div id="handTmOuterCardInfo"></div></td>
						<td align="center"><div id="handTmOuterChangeVesting"></div></td>
					</tr>
					</table>
					</div>
			</td>
			<td valign="top" >
					<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
					<table  border="0" cellspacing="1" cellpadding="0" style="width:100%;font-size:12px;line-height:35px;" >
					
					 <tr>
        						<td colspan="3" width="100%" align="center" bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;">团购卡号段登记</td>
			       	</tr>
			       	
					<tr>
						<td >团购号</td>
						<td><select id="corpstype" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"name="searchDyqStateKey" >
									<option value="1">团购项目</option>
									<option value="2">团购卡金</option>
									</select>
									
						</td>
						<td><input id=corpscardno name="corpscardno" type="text" size="15"/></td>
					</tr>
					<tr>
						<td >团购商家</td>
						<td colspan="2"><input id="corpssource" name="corpssource" type="text" size="20"/> </td>

					</tr>
				
					<tr>
						<td>项目编号/卡类型</td>
						<td  ><input id="corpspicno" name="corpspicno" type="text" size="10" onchange="validateCorpspicno(this)"/></td>
						<td  ><input id="corpspicname" name="corpspicname" type="text" size="15" readonly="true"/></td>
					</tr>
					<tr>
						<td>项目金额/卡金</td>
						<td colspan="2" ><input id="corpsamt" name="corpsamt" type="text" size="10"/></td>
					</tr>
					<tr>
						<td colspan="3" align="center"><div id="handTgOuterCardInfo"></div></td>
						
					</tr>
					</table>
					</div>
			</td>
			</tr>
			<tr>
			<td valign="top" >
				<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
					<table  border="0" cellspacing="1" cellpadding="0" style="width:100%; font-size:12px;line-height:35px;" >
					 <tr>
        						<td colspan="2" width="100%" align="center" bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;">收购卡号段登记</td>
			       	</tr>
					<tr>
						<td width="60">归属门店</td>
						<td>
							<input id="cardvesting" name="cardvesting" type="text" size="6"  readonly="true"/>
							<input id="cardvestingname" name="cardvestingname" type="text" size="15" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td>收购卡号</td>
						<td><input id="registercardno" name="registercardno" type="text" size="20"/></td>
					</tr>
					
					<tr>
						<td>卡类型</td>
						<td>
							<select id="registercardtype" name="registercardtype"  style="width:200" ></select>
						</td>
					</tr>
					<tr>
						<td>会员性别</td>
						<td>
							<select id="membersex" name="membersex"  style="width:100">
								<option value="0" selected="true">女</option>
								<option value="1">男</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>会员姓名</td>
						<td><input id="membername" name="membername" type="text" size="20"/></td>
					</tr>
					<tr>
						<td>手机号码</td>
						<td><input id="memberphone" name="memberphone" type="text" size="20"/></td>
					</tr>
					<tr>
						<td>余额</td>
						<td><input id="cardbalance" name="cardbalance" type="text" size="20" onchange="validatePrice(this)"/></td>
					</tr>
					
					<tr>
						<td>会员生日</td>
						<td><input id="memberbrithday" name="memberbrithday" type="text" size="20"/></td>
					</tr>
					<tr>
						<td>身份证号码</td>
						<td><input id="registerpcid" name="registerpcid" type="text" size="30"/></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><div id="handPurchasereCardInfo"></div></td>
					</tr>
					</table>
				</div>
			</td>
			<td colspan="2"  valign="top" >
				<div  style="width:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
					<table  border="0" cellspacing="1" cellpadding="0" style="width:100%; font-size:12px;line-height:35px;" >
					
					<tr>
        						<td colspan="3" width="100%" align="center" bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;">抵用券号段登记</td>
			       	</tr>
					<tr>
						<td width="60">归属门店</td>
						<td>
							<input id="strDyqHomeCompid" name="strDyqHomeCompid" type="text" size="6"  readonly="true"/>
							<input id="strDyqHomeCompName" name="strDyqHomeCompName" type="text" size="15" readonly="true"/>
						</td>
		
						<td rowspan="9" valign="top"><div id="commoninfodivdetial" style="margin:0; padding:0"></div></td>
					</tr>
					<tr>
						<td>卡前缀</td>
						<td><input id="strDyqCardBef" name="strDyqCardBef" type="text" size="20" maxlength="5"/></td>
					</tr>
					<tr>
						<td>开始卡号</td>
						<td><input id="strFromDyqCardNo" name="strFromDyqCardNo" type="text" size="20"/></td>
					</tr>
					<tr>
						<td>结束卡号</td>
						<td><input id="strToDyqCardNo" name="strToDyqCardNo" type="text" size="20"/></td>
					</tr>
					<tr>
						<td >介质类型</td>
						<td><select id="dyqType" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"name="dyqType" >
									<option value="1">项目抵用券</option>
									<option value="2">现金抵用券</option>
									</select>
									
						</td>
					</tr>
					<tr>
						<td>面值</td>
						<td><input id="strDyqCardAmt" name="strDyqCardAmt" type="text" size="20"/></td>
					</tr>
					<tr>
						<td>启用日期</td>
						<td><input id="enabledate" name="enabledate" type="text" size="20" readOnly="true"/></td>
					</tr>
					<tr>
						<td>有效期</td>
						<td><input id="validate" name="validate" type="text" size="20" readOnly="true"/></td>
					</tr>
					<tr>
						<td>薪资算法</td>
						<td>
							<select id="createtype" name="createtype"  style="width:140" >
								<option value="-1"> 请选择</option>
								<option value="0"> 老体系</option>
								<option value="1"> 新体系</option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center"><div id="handDyqOuterCardInfo"></div></td>
					</tr>
					</table>
				</div>
			</td>
			</tr>
			</table>			
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