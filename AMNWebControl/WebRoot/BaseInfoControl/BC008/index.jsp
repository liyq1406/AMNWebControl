<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC008/bc008.js"></script>
	
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
		.tr{background:#efefef;text-indent:4px;}
    </style>
</head>
<body >
		<div class="l-loading" style="display:block" id="pageloading"></div> 
		 	
		 	 <div id="bc008layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
			  	<div position="left"   id="lsPanel" title="连锁结构"> 
				  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
				  		<ul id="companyTree" style="margin-top:3px;"></ul>
				  	</div>
			  	</div>
				<div position="center"   id="designPanel"  style="width:100%;" > 
				<form id="detailForm" name="detailForm" method="post"	theme="simple">
				    <div id="toptoolbar"></div> 
					<div id="BC008Tab" style="width:100%; height:95%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
						
						<div title="系统格式化/物流设置" style="font-size:12px;" style="width:100%; height:98%; ">
							
								<table width="100%"  height="100%"  border="0"  cellspacing="1" cellpadding="1" style="font-size:12px;line-height:25px;" >
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;金额小数位数&nbsp;&nbsp;&nbsp;</td>
										<td width="30%">
										<s:radio label="金额小数位数" id="SP001" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP001" list="#{0:\"整数\",1:\"一位\",2:\"两位\",3:\"三位\",4:\"四位\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td width="20%">&nbsp;&nbsp;销售仓库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td width="30%">
											<s:textfield id="SP013" name="curSysparam.SP013" onchange="validateNan(this);StateManager('2')" size="15"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										
										
									</tr>
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;数量小数位数&nbsp;&nbsp;&nbsp;</td>
										<td width="30%">
										<s:radio label="数量小数位数" id="SP002" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP002" list="#{0:\"整数\",1:\"一位\",2:\"两位\",3:\"三位\",4:\"四位\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td width="10%">&nbsp;&nbsp;消耗仓库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td width="35%">
											<s:textfield id="SP014" name="curSysparam.SP014" onchange="validateNan(this);StateManager('2')" size="15"  maxlength="40" theme="simple" ></s:textfield>
										</td>
									</tr>
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;百分率小数位数</td>
										<td width="30%">
										<s:radio label="百分率小数位数" id="SP003" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP003" list="#{0:\"整数\",1:\"一位\",2:\"两位\",3:\"三位\",4:\"四位\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td width="10%">&nbsp;&nbsp;是否启用批次&nbsp;&nbsp;&nbsp;</td>
										<td width="35%">
										<s:radio label="是否启用批次" id="SP015" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP015" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
									
									</tr>
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;单价小数位数&nbsp;&nbsp;&nbsp;</td>
										<td width="30%">
										<s:radio label="单价率小数位数" id="SP004" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP004" list="#{0:\"整数\",1:\"一位\",2:\"两位\",3:\"三位\",4:\"四位\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td width="10%">&nbsp;&nbsp;是否启用条码&nbsp;&nbsp;&nbsp;</td>
										<td width="35%">
										<s:radio label="是否启用条码" id="SP016" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP016" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
									</tr>
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;是否启用移动终端&nbsp;&nbsp;&nbsp;</td>
										<td width="30%">
											<s:radio label="是否启用条码" id="SP005" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP005" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td width="10%">&nbsp;&nbsp;是否启用负库存&nbsp;&nbsp;&nbsp;</td>
										<td width="35%">
										<s:radio label="是否启用负库存" id="SP017" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP017" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
									</tr>
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;小票提示语&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td width="30%">
											<s:textfield id="SP006" name="curSysparam.SP006" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td width="10%">&nbsp;&nbsp;是否启用消耗扣库存&nbsp;&nbsp;&nbsp;</td>
										<td width="35%">
										<s:radio label="是否启用消耗扣库存" id="SP018" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP018" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
									</tr>
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;收银单规则&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td width="30%">
											<s:textfield id="SP007" name="curSysparam.SP007" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td width="10%">&nbsp;&nbsp;是否允许相同帐号同时在线&nbsp;&nbsp;&nbsp;</td>
										<td width="35%">
										<s:radio label="是否启用消耗扣库存" id="SP071" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP071" list="#{0:\"禁止\",1:\"允许\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										
									</tr>
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;开卡单规则&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td width="30%">
											<s:textfield id="SP008" name="curSysparam.SP008" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td width="10%">&nbsp;&nbsp;门店使用项目模板范围</td>
										<td width="35%"><s:textfield id="bSP059" name="bSP059" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield></td>
								
										</tr>
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;充值单规则&nbsp;&nbsp;&nbsp;</td>
										<td width="30%">
											<s:textfield id="SP009" name="curSysparam.SP009" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										</td>
									   <td width="10%">&nbsp;&nbsp;门店使用产品模板范围</td>
										<td width="35%"><s:textfield id="bSP061" name="bSP061" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield></td>
								
									</tr>
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;异动单规则</td>
										<td width="30%">
											<s:textfield id="SP010" name="curSysparam.SP010" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										 <td width="10%">&nbsp;&nbsp;门店使用卡类型模板范围</td>
										<td width="35%"><s:textfield id="bSP063" name="bSP063" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										
										<s:hidden id="bSP064" name="bSP064" theme="simple" ></s:hidden></td>
										
									</tr>
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;人事单规则</td>
										<td width="30%">
											<s:textfield id="SP011" name="curSysparam.SP011" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td >&nbsp;&nbsp;短信渠道账号</td>
										<td >
											<s:textfield id="SP069" name="curSysparam.SP069" onchange="StateManager('2')"   cssStyle="width:140"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										
									</tr>
									
									<tr class="tr">
										<td width="20%">&nbsp;&nbsp;物流单规则&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td width="30%">
											<s:textfield id="SP012" name="curSysparam.SP012" onchange="validateNan(this);StateManager('2')" size="35" maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td >&nbsp;&nbsp;短信渠道密码</td>
										<td >
											<s:password id="SP070" name="curSysparam.SP070" onchange="StateManager('2')"  cssStyle="width:140"  maxlength="40" theme="simple" ></s:password>
										</td>
										
									</tr>
									
									<tr class="tr">
									
										<td >&nbsp;&nbsp;门店指纹考勤机ID号</td>
										<td >
											<s:textfield id="SP072" name="curSysparam.SP072" onchange="StateManager('2')"  cssStyle="width:140"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td >&nbsp;&nbsp;读卡器端口号</td>
										<td >
											<s:select id="SP080" name="curSysparam.SP080" list="#{0:'COM1',1:'COM2',2:'COM3',3:'COM4',4:'COM5',5:'COM6'}" theme="simple"></s:select>
										</td>
									</tr>
									<tr class="tr">
										<td >&nbsp;&nbsp;门店指纹考勤机IP号</td>
										<td >
											<s:textfield id="SP073" name="curSysparam.SP073" onchange="StateManager('2')"  cssStyle="width:140"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td >&nbsp;&nbsp;读卡器波特率</td>
										<td >
											<s:select id="SP093" name="curSysparam.SP093" list="#{9600:'9600',115200:'115200'}" theme="simple"></s:select>
										</td>
									</tr>
									<tr class="tr">
										<td width="25%">&nbsp;&nbsp;开卡使用支付方式&nbsp;&nbsp;&nbsp;</td>
										<td >
											<s:textfield id="bSP065" name="bSP065" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td >&nbsp;&nbsp;门店考勤类型</td>
										<td >
											<s:select id="SP096" name="curSysparam.SP096" list="#{'0':'打卡考勤','1':'指纹考勤','2':'人脸考勤'}" theme="simple" style="width:100"></s:select>
										</td>
								</tr>
								<tr class="tr">
										<td width="25%">&nbsp;&nbsp;充值使用支付方式&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="bSP066" name="bSP066" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										
										<td width="10%">&nbsp;&nbsp;是否启用门店登录IP限制&nbsp;&nbsp;&nbsp;</td>
										<td width="35%">
										<s:radio label="是否启用消耗扣库存" id="SP107" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP107" list="#{0:\"不启用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										
								</tr>
								<tr class="tr">
										<td width="25%">&nbsp;&nbsp;消费使用支付方式&nbsp;&nbsp;&nbsp;</td>
										<td >
											<s:textfield id="bSP067" name="bSP067" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td >&nbsp;&nbsp;短信快速渠道账号</td>
										<td >
											<s:textfield id="SP110" name="curSysparam.SP110" onchange="StateManager('2')"   cssStyle="width:140"  maxlength="40" theme="simple" ></s:textfield>
										</td>
								</tr>
								<tr class="tr">
										<td width="25%">&nbsp;&nbsp;卡异动使用支付方式&nbsp;&nbsp;&nbsp;</td>
										<td >
											<s:textfield id="bSP068" name="bSP068" onchange="validateNan(this);StateManager('2')" size="35"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td >&nbsp;&nbsp;短信快速渠道密码</td>
										<td >
											<s:password id="SP111" name="curSysparam.SP111" onchange="StateManager('2')"  cssStyle="width:140"  maxlength="40" theme="simple" ></s:password>
										</td>
								</tr>
									<tr class="tr">
										<td>&nbsp;&nbsp;</td>
										<td>&nbsp;&nbsp;</td>
										<td>&nbsp;&nbsp;</td>
										<td>&nbsp;&nbsp;</td>
										
								
									</tr>
								</table>
						
						</div>
						<div title="会员卡设置/财务设置" style="font-size:12px;" style="width:100%; height:98%; ">
							<table width="100%"  height="100%"  border="0"  cellspacing="1" cellpadding="1" style="font-size:12px;line-height:25px;" >
								<tr class="tr">
										<td >&nbsp;&nbsp;会员卡序号长度&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td >
											<s:textfield id="SP019" name="curSysparam.SP019" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>
										</td>
										<td >&nbsp;&nbsp;是否启用自动建卡&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用自动建卡" id="SP041" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP041" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td>&nbsp;&nbsp;卡金老客分享限制&nbsp;&nbsp;&nbsp;</td>
										<td>
										<s:radio label="卡金老客分享限制" id="SP102" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP102" list="#{0:\"不执行\",1:\"执行\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;会员卡尾数过滤&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP020" name="curSysparam.SP020" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;过滤
										</td>
										<td >&nbsp;&nbsp;是否启用卡变更欠款累加&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用卡变更欠款累加" id="SP042" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP042" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td>&nbsp;&nbsp;疗程兑换优惠活动&nbsp;&nbsp;&nbsp;</td>
										<td>
										<s:radio label="疗程兑换优惠活动" id="SP099" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP099" list="#{0:\"不执行\",1:\"执行\"}"
										listKey="key" theme="simple" listValue="value"/></td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;竞争转卡最低额度&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP021" name="curSysparam.SP021" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										<td >&nbsp;&nbsp;是否启用转卡规则&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用转卡规则" id="SP043" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP043" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td>&nbsp;&nbsp;人事调动生效日期限制&nbsp;&nbsp;&nbsp;</td>
										<td>
										<s:radio label="人事调动生效日期限制" id="SP103" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP103" list="#{0:\"不执行\",1:\"执行\"}"
										listKey="key" theme="simple" listValue="value"/></td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;竞争转卡充值额度相对倍数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP022" name="curSysparam.SP022" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;倍
										</td>
										<td >&nbsp;&nbsp;欠款卡是否禁用转卡&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="欠款卡是否禁用转卡" id="SP044" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP044" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td >&nbsp;&nbsp;收银洗剪吹限制分享人员&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="收银洗剪吹限制分享人员" id="SP098" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP098" list="#{0:\"不限制\",1:\"限制\"}"
										listKey="key" theme="simple" listValue="value"/></td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;遗失补卡工本费&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP023" name="curSysparam.SP023" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										<td >&nbsp;&nbsp;收购卡是否禁用跨店消费&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="收购卡是否禁用跨店消费" id="SP045" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP045" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td >&nbsp;&nbsp;美容项目允许输入中工小工&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="美容项目允许输入中工小工" id="SP097" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP097" list="#{0:\"允许\",1:\"不允许\"}"
										listKey="key" theme="simple" listValue="value"/></td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;欠款卡允许消费金额比率&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP024" name="curSysparam.SP024" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;%
										</td>
										<td >&nbsp;&nbsp;是否启用项目消费定价&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="收购卡是否禁用跨店消费" id="SP046" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP046" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
											<td >&nbsp;&nbsp;是否允许修改疗程兑换金额&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否允许修改疗程兑换金额" id="SP095" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP095" list="#{0:\"禁用\",1:\"允许\"}"
										listKey="key" theme="simple" listValue="value"/></td>	
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;欠款卡允许消费次数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP025" name="curSysparam.SP025" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;次
										</td>
										<td >&nbsp;&nbsp;是否启用产品销售定价&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="收购卡是否禁用跨店消费" id="SP047" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP047" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td >&nbsp;&nbsp;会员卡消费额度限制&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="会员卡消费额度限制" id="SP104" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP104" list="#{0:\"不限制\",1:\"限制\"}"
										listKey="key" theme="simple" listValue="value"/></td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;门店经理签单额度&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP026" name="curSysparam.SP026" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										<td >&nbsp;&nbsp;是否允许门店查看跨店交易历史&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="卡销售是否需要输入初始密码" id="SP048" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP048" list="#{0:\"不允许\",1:\"允许\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td >&nbsp;&nbsp;是否启用推荐人&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用推荐人" id="SP105" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP105" list="#{0:\"不启用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;项目消费经理打折系数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP027" name="curSysparam.SP027" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;%
										</td>
										<td >&nbsp;&nbsp;经理签单是否需要经理确认&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="经理签单是否需要经理确认" id="SP049" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP049" list="#{0:\"不需要\",1:\"需要\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td >&nbsp;&nbsp;是否启用充值赠送金额&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用充值赠送金额" id="SP106" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP106" list="#{0:\"不启用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;疗程兑换经理打折系数 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP028" name="curSysparam.SP028" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;%
										</td>
										<td >&nbsp;&nbsp;是否启用封帐&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用封装" id="SP050" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP050" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td >&nbsp;&nbsp;收银是否需要读卡&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="收银是否需要读卡" id="SP108" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP108" list="#{0:\"需要\",1:\"不需要\"}"
										listKey="key" theme="simple" listValue="value"/></td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;员工购买产品上限  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP029" name="curSysparam.SP029" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										<td >&nbsp;&nbsp;是否启用跨区域卡异动&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用封装" id="SP051" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP051" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td >&nbsp;&nbsp;是否需要发送消费短信&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否需要发送消费短信" id="SP109" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP109" list="#{0:\"不需要\",1:\"需要\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										</td>
										
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;第一档赠送疗程金额   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP030" name="curSysparam.SP030" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										<td >&nbsp;&nbsp;是否启用跨区域卡消费&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用封装" id="SP052" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP052" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td>
											&nbsp;&nbsp;是否启用新老客系统自动计算
										</td>
										<td>
											<s:radio label="是否启用新老客系统自动计算" id="SP112" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
											name="curSysparam.SP112" list="#{1:\"不需要\",0:\"需要\"}"
											listKey="key" theme="simple" listValue="value"/>
										</td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;第二档赠送疗程金额   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP031" name="curSysparam.SP031" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										<td >&nbsp;&nbsp;是否启用跨区域卡充值&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用跨区域卡充值" id="SP053" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP053" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td>
											&nbsp;&nbsp;B/C美容师拓客活动
										</td>
										<td>
											<s:radio label="B/C美容师拓客活动" id="SP113" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
											name="curSysparam.SP113" list="#{1:\"不需要\",0:\"需要\"}"
											listKey="key" theme="simple" listValue="value"/>
										</td>
										
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;第三档赠送疗程金额   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP032" name="curSysparam.SP032" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										
										<td >&nbsp;&nbsp;卡销售是否需要输入初始密码&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="卡销售是否需要输入初始密码" id="SP054" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP054" list="#{0:\"不需要\",1:\"需要\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td>
											&nbsp;&nbsp;是否享受虚拟卡金
										</td>
										<td>
											<s:radio label="是否享受虚拟卡金" id="SP114" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
											name="curSysparam.SP114" list="#{0:\"不需要\",1:\"需要\"}"
											listKey="key" theme="simple" listValue="value"/>
										</td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;总虚业绩指标   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP033" name="curSysparam.SP033" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										
										<td >&nbsp;&nbsp;卡消费是否需要输入确认密码&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="卡消费是否需要输入确认密码" id="SP055" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP055" list="#{0:\"不需要\",1:\"需要\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td>
											&nbsp;&nbsp;虚拟卡金比率
										</td>
										<td>
											<s:textfield id="SP115" name="curSysparam.SP115" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;%
										</td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;耗卡率指标   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP034" name="curSysparam.SP034" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										
										<td >&nbsp;&nbsp;是否启用充值积分赠送&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用充值积分赠送" id="SP056" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP056" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td>美容工资提成模式</td>
										<td>
											<select id="SP116" name="curSysparam.SP116">
												<option value="0">无</option>
												<option value="1">1+2模式</option>
												<option value="2">1+1模式</option>
												<option value="3">1:1模式</option>
											</select>
										</td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;有效会员数指标    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP035" name="curSysparam.SP035" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										<td >&nbsp;&nbsp;是否启用消费积分赠送&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用消费积分赠送" id="SP057" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP057" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td>是否启用980疗程体验活动</td>
										<td >
										<s:radio label="是否启用980疗程体验活动" id="SP117" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP117" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;新增疗程数指标   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP036" name="curSysparam.SP036" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										<td >&nbsp;&nbsp;是否启用产品折扣设置&nbsp;&nbsp;&nbsp;</td>
										<td >
										<s:radio label="是否启用产品折扣设置" id="SP094" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"
										name="curSysparam.SP094" list="#{0:\"禁用\",1:\"启用\"}"
										listKey="key" theme="simple" listValue="value"/></td>
										<td>980疗程体验活动截止日期</td>
										<td>
											<s:textfield id="SP118" name="curSysparam.SP118" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>
										</td>
								</tr>
							   <tr class="tr">
										<td>&nbsp;&nbsp;美容疗程会员保有率指标   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP037" name="curSysparam.SP037" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										<td colspan="4">&nbsp;&nbsp;儿童现金洗剪吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP074" name="curSysparam.SP074" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
											&nbsp;&nbsp;儿童销卡洗剪吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP081" name="curSysparam.SP081" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
											&nbsp;&nbsp;设技师洗吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP087" name="curSysparam.SP087" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										
										</td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;美发疗程会员保有率指标   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP038" name="curSysparam.SP038" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;人
										</td>
										<td colspan="4">&nbsp;&nbsp;设技师特价洗剪吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP075" name="curSysparam.SP075" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										&nbsp;&nbsp;设技师原价洗剪吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP082" name="curSysparam.SP082" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										&nbsp;&nbsp;首席洗吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP088" name="curSysparam.SP088" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										
										</td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;离职人指标    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP039" name="curSysparam.SP039" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
										<td  colspan="4">&nbsp;&nbsp;首席特价洗剪吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP076" name="curSysparam.SP076" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										&nbsp;&nbsp;首席原价洗剪吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP083" name="curSysparam.SP083" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
											&nbsp;&nbsp;总监洗吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP089" name="curSysparam.SP089" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										
										</td>
								</tr>
								<tr class="tr">
										<td>&nbsp;&nbsp;报表显示天数&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP040" name="curSysparam.SP040" onchange="validateNan(this);StateManager('2')" size="10" theme="simple" ></s:textfield>&nbsp;&nbsp;天
										</td>
										<td  colspan="4">&nbsp;&nbsp;总监特价洗剪吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP077" name="curSysparam.SP077" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
											&nbsp;&nbsp;总监原价洗剪吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP084" name="curSysparam.SP084" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
											&nbsp;&nbsp;创意总洗吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP090" name="curSysparam.SP090" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
								</tr>
									<tr class="tr">
										<td>&nbsp;&nbsp;积分赠送第一档比率   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP100" name="curSysparam.SP100" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
										</td>
										
										<td colspan="4">&nbsp;&nbsp;创意总监特价洗剪吹&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP078" name="curSysparam.SP078" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
											&nbsp;&nbsp;创意总监原价洗剪吹&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP085" name="curSysparam.SP085" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										&nbsp;&nbsp;洗吹特价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP091" name="curSysparam.SP091" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
								</tr>
								
								<tr class="tr">
										<td>&nbsp;&nbsp;积分赠送第二档比率   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td>
											<s:textfield id="SP101" name="curSysparam.SP101" onchange="validateNan(this);StateManager('2')" size="10"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
										</td>
										 <td colspan="4">&nbsp;&nbsp;店长特价洗剪吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP079" name="curSysparam.SP079" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										&nbsp;&nbsp;店长原价洗剪吹&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP086" name="curSysparam.SP086" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										&nbsp;&nbsp;洗吹特价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<s:textfield id="SP092" name="curSysparam.SP092" onchange="validateNan(this);StateManager('2')" size="4" theme="simple" ></s:textfield>&nbsp;&nbsp;元
										</td>
								</tr>
								
							
								
							</table>
						</div>
						<div title="门店提成参数设定" style="font-size:12px;" style="width:100%; height:98%; ">
							<table width="100%"  height="100%"  border="0"  cellspacing="1" cellpadding="1" style="font-size:12px;line-height:25px;" >
								<tr class="tr">
									<td>美容 【产品】 成本系数</td>
									<td>
											<s:textfield id="cpcostratemr" name="curSalaryrateinfo.cpcostratemr" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									
									<td>美发 【产品】 成本系数</td>
									<td>
											<s:textfield id="cpcostratemf" name="curSalaryrateinfo.cpcostratemf" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>美甲 【产品】 成本系数</td>
									<td>
											<s:textfield id="cpcostratemj" name="curSalaryrateinfo.cpcostratemj" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>卡诗 【产品】 成本系数</td>
									<td>
											<s:textfield id="cpcostrateks" name="curSalaryrateinfo.cpcostrateks" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr class="tr">
									<td>美容 【产品】 提成系数</td>
									<td>
											<s:textfield id="cpsalaryratemr" name="curSalaryrateinfo.cpsalaryratemr" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>美发 【产品】 提成系数</td>
									<td>
											<s:textfield id="cpsalaryratemf" name="curSalaryrateinfo.cpsalaryratemf" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>美甲 【产品】 提成系数</td>
									<td>
											<s:textfield id="cpsalaryratemj" name="curSalaryrateinfo.cpsalaryratemj" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									
								
									<td>卡诗 【产品】 提成系数</td>
									<td>
											<s:textfield id="cpsalaryrateks" name="curSalaryrateinfo.cpsalaryrateks" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
								</tr>
								
								 <tr class="tr">
									
									<td>美容师A,B类 【卡金】 提成系数</td>
									<td>
											<s:textfield id="kjsalaryratemr" name="curSalaryrateinfo.kjsalaryratemr" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>美容师C 【卡金】 提成系数</td>
									<td>
											<s:textfield id="kjsalaryratemrc" name="curSalaryrateinfo.kjsalaryratemrc" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>美发师 【卡金】 提成系数</td>
									<td>
											<s:textfield id="kjsalaryratemf" name="curSalaryrateinfo.kjsalaryratemf" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									
								</tr>
							
								 <tr class="tr">
									<td>(一,二,三)级烫染师 【卡金】 提成系数</td>
									<td>
											<s:textfield id="kjsalaryratetrsa" name="curSalaryrateinfo.kjsalaryratetrsa" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>四级(督导)烫染师 【卡金】 提成系数</td>
									<td>
											<s:textfield id="kjsalaryratetrsb" name="curSalaryrateinfo.kjsalaryratetrsb" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr class="tr">
									<td>(一,二,三)级烫染师 【兑换】 成本系数</td>
									<td>
											<s:textfield id="dhcostratetrsa" name="curSalaryrateinfo.dhcostratetrsa" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									
									<td>四级(督导)烫染师 【兑换】成本系数</td>
									<td>
											<s:textfield id="dhcostratetrsb" name="curSalaryrateinfo.dhcostratetrsb" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>美发人员 【兑换】本系数</td>
									<td>
											<s:textfield id="dhcostratemf" name="curSalaryrateinfo.dhcostratemf" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>全韩私密合作提成比率</td>
									<td>
											<s:textfield id="hzsalaryrateqh" name="curSalaryrateinfo.hzsalaryrateqh" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr class="tr">
									<td>(一,二,三)级烫染师 【兑换】提成系数</td>
									<td>
											<s:textfield id="dhsalaryratetrsa" name="curSalaryrateinfo.dhsalaryratetrsa" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									
									<td>四级(督导)烫染师兑换提成系数</td>
									<td>
											<s:textfield id="dhsalaryratetrsb" name="curSalaryrateinfo.dhsalaryratetrsb" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>美发人员兑换提成系数</td>
									<td>
											<s:textfield id="dhsalaryratemf" name="curSalaryrateinfo.dhsalaryratemf" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>暨大合作提成比率</td>
									<td>
											<s:textfield id="hzsalaryratejd" name="curSalaryrateinfo.hzsalaryratejd" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									    <td colspan="10" color="#0000cc">
												<hr width="100%" />
										</td>
								 </tr>
								  <tr class="tr">
									<td>建发疗程兑换提成奖励</td>
									<td>
											<s:textfield id="jfdhsalaryreward" name="curSalaryrateinfo.jfdhsalaryreward" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									
									<td>肩颈疗程兑换卡金奖励</td>
									<td>
											<s:textfield id="jjdhyejireward" name="curSalaryrateinfo.jjdhyejireward" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>肩颈服务提成奖励</td>
									<td>
											<s:textfield id="jjfwsalaryreward" name="curSalaryrateinfo.jjfwsalaryreward" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								 <tr class="tr">
								
									<td>介绍人提成奖励</td>
									<td>
											<s:textfield id="jsrsalaryreward" name="curSalaryrateinfo.jsrsalaryreward" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>被介绍人扣除提成</td>
									<td>
											<s:textfield id="tjsrsalarycost" name="curSalaryrateinfo.tjsrsalarycost" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									    <td colspan="10" color="#0000cc">
												<hr width="100%" />
										</td>
								 </tr>
								 
								<tr class="tr">
								
									<td>美发入职5个月以上提成比率</td>
									<td>
											<s:textfield id="mfsalaryratefiveup" name="curSalaryrateinfo.mfsalaryratefiveup" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>美发入职5个月以下提成比率</td>
									<td>
											<s:textfield id="mfsalaryratefivedown" name="curSalaryrateinfo.mfsalaryratefivedown" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr class="tr">
									<td>老疗程卡固定业绩</td>
									<td>
											<s:textfield id="olccostyejifixed" name="curSalaryrateinfo.olccostyejifixed" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>老疗程卡固定提成</td>
									<td>
											<s:textfield id="olccostsalaryfixed" name="curSalaryrateinfo.olccostsalaryfixed" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								 <tr class="tr">
									<td>原价卡烫染师成本比率</td>
									<td>
											<s:textfield id="yjkcostratetr" name="curSalaryrateinfo.yjkcostratetr" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									
									<td>原价卡美容美发成本比率</td>
									<td>
											<s:textfield id="yjkcostratemrmf" name="curSalaryrateinfo.yjkcostratemrmf" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>原价卡美容美发提成比率</td>
									<td>
											<s:textfield id="yjksalaryratemrmf" name="curSalaryrateinfo.yjksalaryratemrmf" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr class="tr">
									<td>(一,二,三)级烫染师项目提成比率</td>
									<td>
											<s:textfield id="salaryratetra" name="curSalaryrateinfo.salaryratetra" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									
									<td>四级(督导)烫染师项目提成比率</td>
									<td>
											<s:textfield id="salaryratetrb" name="curSalaryrateinfo.salaryratetrb" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>烫染师疗程成本比率</td>
									<td>
											<s:textfield id="nlccostratetr" name="curSalaryrateinfo.nlccostratetr" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr class="tr">
									<td>设计师洗剪吹成本比率</td>
									<td>
											<s:textfield id="xjccostratesjs" name="curSalaryrateinfo.xjccostratesjs" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									
									<td>首席洗剪吹成本比率</td>
									<td>
											<s:textfield id="xjccostratesx" name="curSalaryrateinfo.xjccostratesx" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>总监洗剪吹成本比率</td>
									<td>
											<s:textfield id="xjccostratezj" name="curSalaryrateinfo.xjccostratezj" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr class="tr">
									<td>洗剪吹中工达标固定提成</td>
									<td>
											<s:textfield id="xjcsalaryfixeddb" name="curSalaryrateinfo.xjcsalaryfixeddb" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									
									<td>洗剪吹中工不达标固定提成</td>
									<td>
											<s:textfield id="xjcsalaryfixedndb" name="curSalaryrateinfo.xjcsalaryfixedndb" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>洗剪吹中工不合格固定提成</td>
									<td>
											<s:textfield id="xjcsalaryfixednhg" name="curSalaryrateinfo.xjcsalaryfixednhg" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr class="tr">
									
									<td>美容抵用券新项目固定提成</td>
									<td>
											<s:textfield id="mrsalaryfixedtmk" name="curSalaryrateinfo.mrsalaryfixedtmk" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td>美容体验新项目固定提成</td>
									<td>
											<s:textfield id="mrsalaryfixedty" name="curSalaryrateinfo.mrsalaryfixedty" onchange="validatePrice(this);" size="4"  maxlength="40" theme="simple" ></s:textfield>&nbsp;&nbsp;
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
						</div>
					</div>
					<s:hidden id="SP059" name="curSysparam.SP059" theme="simple" ></s:hidden>
					<s:hidden id="SP061" name="curSysparam.SP061" theme="simple" ></s:hidden>
					<s:hidden id="SP063" name="curSysparam.SP063" theme="simple" ></s:hidden>
					<s:hidden id="SP064" name="curSysparam.SP064" theme="simple" ></s:hidden>
					<s:hidden id="SP065" name="curSysparam.SP065" theme="simple" ></s:hidden>
					<s:hidden id="SP066" name="curSysparam.SP066" theme="simple" ></s:hidden>
					<s:hidden id="SP067" name="curSysparam.SP067" theme="simple" ></s:hidden>
					<s:hidden id="SP068" name="curSysparam.SP068" theme="simple" ></s:hidden>
					</form>
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