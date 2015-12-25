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
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC008/ac008.js"></script>

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

  	 <div id="ac008layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
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
				
  	 	<div position="left"   id="lsPanel" title="连锁结构"> 
  	 		<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
			<tr>
				<td valign="top" colspan="2">
					<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
			  			<ul id="companyTree" style="margin-top:3px;"></ul>
		  			</div>
				</td>
			</tr>
			</table>		
		  	
	  	</div>
	  	 <div position="center"   id="designPanel"  style="width:100%;" > 
		 	<form name="detailForm" method="post"  id="detailForm">
				<table border="0" cellspacing="1" cellpadding="0" style="font-size:12px;">
				<tr >
				<td  valign="top" width="150">
					<div id="topdatabarCard" style="margin:0; padding:0"></div>
					<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
					
				</td>
				<td valign="top" ><div id="commoninfodivdetial" style="margin:0; padding:0"></div></td>
				
				<td valign="top" >	
					 <div id="AC008Tab" style="width:700px; height:95%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
				  	 <div title="卡销售" style="height:95%; font-size:12px;" >
				  	 <div id="toptoolbarSaleCard" style="margin:0; padding:0"></div>
				  	 <table width="100%"  border="1" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
						<tr>
							<td width="700" valign="top">
							<div  style="width:100%;height:200  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
							<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
							<tr>
							<td><font color="red">业务类型</font></td>
								<td colspan="5">
									<input type="radio" id="billType0" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="0" disabled="true"/>售卡
									<input type="radio" id="billType1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="1" disabled="true"/>充值/还款
									<input type="radio" id="billType2" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="2" disabled="true"/>折扣转卡
									<input type="radio" id="billType3" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="3" disabled="true"/>收购转卡
									<input type="radio" id="billType4" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="4" disabled="true"/>竞争转卡
									<input type="radio" id="billType5" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="5" disabled="true"/>并卡
									<input type="radio" id="billType6" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="6" disabled="true"/>退卡
									<input type="radio" id="billType7" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="7" disabled="true"/>合作项目
									<input type="radio" id="billType8" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="8" disabled="true"/>条码卡
									<input type="radio" id="billType8" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="9" disabled="true"/>兑换
									<input type="radio" id="billType9" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.billType" value="12" disabled="true"/>年卡销售
								</td>
							</tr>
							<tr>
							<td><font color="red">门店编号</font></td>
							<td ><input type="text" name="curReEditBillInfo.strCompId" id="strCompId"  readonly="true" style="width:120;" onchange="validateSalecardno(this)"  validate="{required:true,minlength:1,maxlength:18,notnull:false}"/></td>
							<td><font color="red">单据编号</font></td>
							<td ><input type="text" name="curReEditBillInfo.strBillId" id="strBillId"  readonly="true" style="width:120;" onchange="validateSalecardno(this)"  validate="{required:true,minlength:1,maxlength:18,notnull:false}"/></td>
							<td><font color="red">卡来源</font></td>
							<td>
								<select id="billinsertype" name="curReEditBillInfo.billinsertype" style="width:100" onchange="changebillinsertype(this)">
									<option value="0">--</option>
									<option value="1">美容</option>
									<option value="2">美发</option>
									<option value="3">合作</option>
								</select>
							</td>
							</tr>
							<tr>
							<td><font color="red">会员卡号</font></td>
							<td ><input type="text" name="curReEditBillInfo.strCardNo" id="strCardNo"  readonly="true" style="width:120;" onchange="validateSalecardno(this)"  validate="{required:true,minlength:1,maxlength:18,notnull:false}"/></td>
							<td><font color="red">会员姓名</font></td>
							<td><input type="text" name="curReEditBillInfo.strMemberName" id="strMemberName"  readonly="true" style="width:120;" onchange="validateSalecardno(this)"  validate="{required:true,minlength:1,maxlength:18,notnull:false}"/></td>
							<td >是否享受虚拟卡金</td>
							<td>
								<select id="isxnj" name="curReEditBillInfo.isxnj" style="width:140" onchange="xnchange(this)" disabled="true">
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</td>
							</tr>
							<tr>
							<td>卡类型</td>
							<td  > <input type="hidden"  name="curReEditBillInfo.strCardType" id="strCardType"  readonly="true" style="width:120;"  />
												<input type="text"  name="strCardTypeName" id="strCardTypeName"  readonly="true" style="width:120;"  />
												<input type="hidden"  name="changeseqno" id="changeseqno"    />
							<td id="dhprjname">兑换项目</td>
							<td><input type="text"  name="strPrjName" id="strPrjName"  readonly="true" style="width:200;"  /></td>
							<td id="id12">金额</td>
							<td id="amt12"><input type="text"  name="strPrjamt" id="strPrjamt"  readonly="true" style="width:120;"  /></td>
							
							</tr>
							
							</table>
							</div>
							<div id="commoninfodivdetial_pay" style="margin:0; padding:0"></div>
					    	<div  id="saleshare" style="width:700;height:200  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
							<table width="100%"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px" >
							<tr>
							<td>	<input type="checkbox" id="editType1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType1" value="1" onclick="editShare(this)"/>编辑</td>
							<td>第一销售</td>
							<td><input type="text" name="curReEditBillInfo.firstsalerid" id="firstsalerid"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)" />
							<input type="text" name="firstsalername" id="firstsalername"  readonly="true" style="width:80;background:#EDF1F8;" /></td>
							<td>分享金额</td>
							<td><input type="text"  name="curReEditBillInfo.firstsaleamt" id="firstsaleamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							<td>分享业绩</td>
							<td><input type="text"  name="curReEditBillInfo.firstsalecashamt" id="firstsalecashamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							<td rowspan="10"><div id="commoninfodivdetial_oldcustomer" style="margin:0; padding:0"></div></td>
							</tr>
							
							<tr>
							<td>	<input type="checkbox" id="editType2" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType2" value="2" onclick="editShare(this)"/>编辑</td>
							<td>第二销售</td>
							<td><input type="text" name="curReEditBillInfo.secondsalerid" id="secondsalerid"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
							<input type="text" name="secondsalername" id="secondsalername"  readonly="true" style="width:80;background:#EDF1F8;"  /></td>
							<td>分享金额</td>
							<td><input type="text"  name="curReEditBillInfo.secondsaleamt" id="secondsaleamt"  style="width:50;" onchange="validatePrice(this)"/></td>
							<td>分享业绩</td>
							<td><input type="text"  name="curReEditBillInfo.secondsalecashamt" id="secondsalecashamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							
							<tr>
							<td>	<input type="checkbox" id="editType3" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType3" value="3" onclick="editShare(this)"/>编辑</td>
							<td>第三销售</td>
							<td><input type="text" name="curReEditBillInfo.thirdsalerid" id="thirdsalerid"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
							<input type="text" name="thirdsalername" id="thirdsalername"  readonly="true" style="width:80;background:#EDF1F8;"  /></td>
							<td>分享金额</td>
							<td><input type="text"  name="curReEditBillInfo.thirdsaleamt" id="thirdsaleamt"  style="width:50;" onchange="validatePrice(this)"/></td>
							<td>分享业绩</td>
							<td><input type="text"  name="curReEditBillInfo.thirdsalecashamt" id="thirdsalecashamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							
							<tr>
							<td>	<input type="checkbox" id="editType4" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType4" value="4" onclick="editShare(this)"/>编辑</td>
							<td>第四销售</td>
							<td><input type="text" name="curReEditBillInfo.fourthsalerid" id="fourthsalerid"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
							<input type="text" name="fourthsalername" id="fourthsalername"  readonly="true" style="width:80;background:#EDF1F8;" /></td>
							<td>分享金额</td>
							<td><input type="text"  name="curReEditBillInfo.fourthsaleamt" id="fourthsaleamt"  style="width:50;" onchange="validatePrice(this)"/></td>
							<td>分享业绩</td>
							<td><input type="text"  name="curReEditBillInfo.fourthsalecashamt" id="fourthsalecashamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							<tr>
							<td>	<input type="checkbox" id="editType5" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType5" value="5" onclick="editShare(this)"/>编辑</td>
							<td>第五销售</td>
							<td><input type="text" name="curReEditBillInfo.fifthsalerid" id="fifthsalerid"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
							<input type="text" name="fifthsalername" id="fifthsalername"  readonly="true" style="width:80;background:#EDF1F8;" /></td>
							<td>分享金额</td>
							<td><input type="text"  name="curReEditBillInfo.fifthsaleamt" id="fifthsaleamt"  style="width:50;" onchange="validatePrice(this)"/></td>
							<td>分享业绩</td>
							<td><input type="text"  name="curReEditBillInfo.fifthsalecashamt" id="fifthsalecashamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							<tr>
							<td>	<input type="checkbox" id="editType6" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType6" value="6" onclick="editShare(this)"/>编辑</td>
							<td>第六销售</td>
							<td><input type="text" name="curReEditBillInfo.sixthsalerid" id="sixthsalerid"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
							<input type="text" name="sixthsalername" id="sixthsalername"  readonly="true" style="width:80;background:#EDF1F8;" /></td>
							<td>分享金额</td>
							<td><input type="text"  name="curReEditBillInfo.sixthsaleamt" id="sixthsaleamt"  style="width:50;" onchange="validatePrice(this)"/></td>
							<td>分享业绩</td>
							<td><input type="text"  name="curReEditBillInfo.sixthsalecashamt" id="sixthsalecashamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							<tr>
							<td>	<input type="checkbox" id="editType7" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType7" value="7" onclick="editShare(this)"/>编辑</td>
							<td>第七销售</td>
							<td><input type="text" name="curReEditBillInfo.seventhsalerid" id="seventhsalerid"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
							<input type="text" name="seventhsalername" id="seventhsalername"  readonly="true" style="width:80;background:#EDF1F8;" /></td>
							<td>分享金额</td>
							<td><input type="text"  name="curReEditBillInfo.seventhsaleamt" id="seventhsaleamt"  style="width:50;" onchange="validatePrice(this)"/></td>
							<td>分享业绩</td>
							<td><input type="text"  name="curReEditBillInfo.seventhsalecashamt" id="seventhsalecashamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							<tr>
							<td>	<input type="checkbox" id="editType8" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType8" value="8" onclick="editShare(this)"/>编辑</td>
							<td>第八销售</td>
							<td><input type="text" name="curReEditBillInfo.eighthsalerid" id="eighthsalerid"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
							<input type="text" name="eighthsalername" id="eighthsalername"  readonly="true" style="width:80;background:#EDF1F8;"  /></td>
							<td>分享金额</td>
							<td><input type="text"  name="curReEditBillInfo.eighthsaleamt" id="eighthsaleamt"  style="width:50;" onchange="validatePrice(this)"/></td>
							<td>分享业绩</td>
							<td><input type="text"  name="curReEditBillInfo.eighthsalecashamt" id="eighthsalecashamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							<tr>
							<td>	<input type="checkbox" id="editType9" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType9" value="9" onclick="editShare(this)"/>编辑</td>
							<td>第九销售</td>
							<td><input type="text" name="curReEditBillInfo.ninthsalerid" id="ninthsalerid"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
							<input type="text" name="ninthsalername" id="ninthsalername"  readonly="true" style="width:80;background:#EDF1F8;"  /></td>
							<td>分享金额</td>
							<td><input type="text"  name="curReEditBillInfo.ninthsaleamt" id="ninthsaleamt"  style="width:50;" onchange="validatePrice(this)"/></td>
							<td>分享业绩</td>
							<td><input type="text"  name="curReEditBillInfo.ninthsalecashamt" id="ninthsalecashamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
							<tr>
							<td><input type="checkbox" id="editType10" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType10" value="10" onclick="editShare(this)"/>编辑</td>
							<td>第十销售</td>
							<td>
								<input type="text" name="curReEditBillInfo.tenthsalerid" id="tenthsalerid"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
								<input type="text" name="tenthsalername" id="tenthsalername"  readonly="true" style="width:80;background:#EDF1F8;" />
							</td>
							<td>分享金额</td>
							<td><input type="text"  name="curReEditBillInfo.tenthsaleamt" id="tenthsaleamt"  style="width:50;" onchange="validatePrice(this)"/></td>
							<td>分享业绩</td>
							<td><input type="text"  name="curReEditBillInfo.tenthsalecashamt" id="tenthsalecashamt"   style="width:50;" onchange="validatePrice(this)"/></td>
							</tr>
				
							<tr>
							<!-- type="checkbox" -->
								<td><input type="hidden" id="editType11" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType11" value="11" onclick="editShare(this)"/></td>
								<!-- <td>经理/顾问</td> -->
								<td><input type="hidden" name="curReEditBillInfo.beautyManager" id="beautyManager"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
								<input type="hidden" name="beautyManagerName" id="beautyManagerName"  readonly="true" style="width:80;background:#EDF1F8;" /></td>
							</tr>
							<tr>
								<td><input type="hidden" id="editType12" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;" name="curReEditBillInfo.editType12" value="12" onclick="editShare(this)"/></td>
								<!-- <td>经理/顾问</td> -->
								<td><input type="hidden" name="curReEditBillInfo.consultant" id="consultant"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
								<input type="hidden" name="consultantName" id="consultantName"  readonly="true" style="width:80;background:#EDF1F8;" /></td>
							</tr>
							<tr>
								<td><input type="hidden" id="editType13" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;" name="curReEditBillInfo.editType13" value="13" onclick="editShare(this)"/></td>
								<!-- <td>经理/顾问</td> -->
								<td><input type="hidden" name="curReEditBillInfo.consultant1" id="consultant1"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
								<input type="hidden" name="consultant1Name" id="consultant1Name"  readonly="true" style="width:80;background:#EDF1F8;" /></td>
							</tr>
							<tr id="trBeauty" style="display:none">
								<td>
									<input type="checkbox" id="editType14" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType14" value="14" onclick="editShare(this)"/>编辑
								</td>
								<td>经理(主)</td>
								<td>
									<input name="curReEditBillInfo.beautyMangerNo" id="beautyMangerNo"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
									<input name="curReEditBillInfo.beautyMangerName" id="beautyMangerName"  readonly="true" style="width:80;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr id="trGw" style="display:none">
								<td>
									<input id="editType15" type="checkbox" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curReEditBillInfo.editType15" value="15" onclick="editShare(this)"/>编辑
								</td>
								<td>经理(副)</td>
								<td>
									<input name="curReEditBillInfo.beautyGw" id="beautyGw"  readonly="true" style="width:60;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
									<input name="curReEditBillInfo.beautyGwName" id="beautyGwName"  readonly="true" style="width:80;background:#EDF1F8;" onchange="checkStaffValuex(this)"/>
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							
							<tr><td colspan="7" align="center"> <div id="editCurRootInfo"></div></td></tr>
							</table>
							</div>
						</td>
					</tr>
					</table>
						<input type="hidden" name="curReEditBillInfo.id.salebillid" id="salebillid" />
						<input type="hidden" name="curReEditBillInfo.iteminid" id="iteminid"/>
						
					</div>
					 <div title="项目服务" style="height:95%;font-size:12px;">
					 	<div id="toptoolmanager" style="margin:0; padding:0"></div>
					 	<div id="toptoolbarCard" style="margin:0; padding:0"></div>
					 	
					 	<div id="commoninfodivdetial_service" style="margin:0; padding:0"></div>
					 </div>
					 <div title="产品销售" style="height:95%;font-size:12px;">
					 	<div id="toptoolbarCard_Goods" style="margin:0; padding:0"></div>
					 	<div id="commoninfodivdetial_goods" style="margin:0; padding:0"></div>
					 </div>
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
						<input type="hidden"  id="strServiceCompId" name="strServiceCompId"/>
						<input type="hidden"  id="strServiceBillId" name="strServiceBillId"/>
						<input type="hidden"  id="strCurCardNo" name="strCurCardNo"/>
						<input type="hidden"  id="strCurDate" name="strCurDate"/>
						<input type="hidden"  id="backflag" name="backflag"/>
						<input type="hidden"  id="billType" name="billType"/>
						
						<input type="hidden"  id="paramSp112" value="${paramSp112 }" name="paramSp112"/>
				</td>
				</tr>
				
				
				</table>
			</form>
			<input type="hidden"  id="paramSp097" name="paramSp097"/>
			<input type="hidden"  id="strShareCondition" name="strShareCondition"/>
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