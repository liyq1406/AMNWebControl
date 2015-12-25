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
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/SellReportControl/SC002/sc002.js"></script>

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

  	 <div id="sc002layout" style="width:100%; margin:0 auto; margin-top:4px; ">
	  	<div position="center"   id="designPanel"  style="height:100%,width:100%;" title="门店日记帐-客单统计"> 
	  		<table  width="800px" id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
			<tr>
		
				 <td ><div><%@include file="/common/search.frag"%></div></td>
				 <td >
						<input id="strFromDate" type="text" size="10" />
				 </td>
				 
				 <td align="right" ><div id="searchButton"></div></td>
				 <td align="right" ><div id="printButton"></div></td>  
			</tr>
			<tr><td style="border-bottom:1px #000000 dashed" colspan="3"></td></tr>
			
			</table>	
			
			<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="width:100%; height:100%;font-size:12px;line-height:25px;" >
			<tr valign="top" align="center">
			<td><div id="commoninfodivAllDate" style="margin:0; padding:0"></td>
			<%--
			注释中间明细表格
			<td  valign="top">
					
					<div id="commoninfodivBillDate" style="margin:0; padding:0">
			</td>--%>
			<td valign="top"><!-- 第三方支付 -->
				<div id="threePayment" style="margin:0; padding:0"></div>
			</td>
			</tr>
			</table>	
				
		<div id="printContent" style="position:absolute;left:1px; top:60px;z-index:-2;display:none">
		<table width="230px" border="0" cellspacing="0" cellpadding="0" id="text">
		<tr><td colspan="3"  align="center" style="font-size:20px">日记账</td></tr>
		 <tr><td colspan="3"  align="center">-----------------</td></tr>
		<tr><td colspan="3"  align="left">打印日期:<label id="printDate"/></td></tr>
		<tr><td colspan="3"  align="left">查日期:<label id="dateFromReport"></label>---<label id="dateToReport"></label></td></tr>
	 	<tr><td colspan="3">===========================</td></tr>
		<tr><td colspan="3"  align="left">总营业额:<label id="factTotal"/></td></tr>
		<tr><td colspan="3"  align="left">员工购买产品:<label id="staffProd"/></td></tr>
		 <tr><td colspan="3">==========================</td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;现金服务:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="cashService"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;现金产品:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="cashProd"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;现金(退卡):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="cashBackCardView"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;现金(卡异动):&nbsp;&nbsp;&nbsp;<label id="cashCardTrans"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;现金(条码卡):&nbsp;&nbsp;&nbsp;<label id="strCashByTMCard"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;现金兑换:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="cashChange"/></td></tr>
	    <tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;现金合作项目:&nbsp;&nbsp;&nbsp;<label id="cashHzPrj"/></td></tr>
	    
	    <tr><td colspan="3">===========================</td></tr>
		<tr><td colspan="3" >&nbsp;现金合计:&nbsp;&nbsp;&nbsp;<label id="cashTotal"/></td></tr>
		<tr><td colspan="3">===========================</td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;银行卡服务:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="creditService"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;银行卡产品:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="creditProd"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;银行卡(退卡):&nbsp;&nbsp;&nbsp;<label id="creditBackCardView"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;银行卡(卡异动):<label id="creditTrans"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;银行卡(条码卡):<label id="strBankByTMCard"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;银行卡兑换:<label id="creditChange"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;银行卡合作项目:<label id="bankHzPrj"/></td></tr>
		
		<tr><td colspan="3">===========================</td></tr>
		<tr><td colspan="3" >&nbsp;银行卡合计:&nbsp;&nbsp;&nbsp;<label id="creditTotal"/></td></tr>
		<tr><td colspan="3">===========================</td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;支票服务:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="checkService"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;支票产品:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="checkProd"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;支票(退卡):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="checkBackCardView"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;支票(卡异动):&nbsp;&nbsp;&nbsp;<label id="checkTrans"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;支票(条码卡):&nbsp;&nbsp;&nbsp;<label id="strcheckByTMCard"/></td></tr>
		
		<tr><td colspan="3">===========================</td></tr>
		<tr><td colspan="3">&nbsp;支票合计:&nbsp;&nbsp;&nbsp;<label id="checkTotal"/></td></tr>
		 <tr><td colspan="3">===========================</td></tr>
		 <tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;指付通服务:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="zftService"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;指付通产品:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="zftProd"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;指付通(退卡):&nbsp;&nbsp;&nbsp;<label id="zftBackCardView"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;指付通(卡异动):<label id="strFingerByTMCard"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;指付通(条码卡):<label id="zftTrans"/></td></tr>
		
		<tr><td colspan="3">===========================</td></tr>
		<tr><td colspan="3">&nbsp;指付通合计:&nbsp;&nbsp;&nbsp;<label id="zftTotal"/></td></tr>
		 <tr><td colspan="3">===========================</td></tr>
		 <tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp; 第三方支付服务:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="okcService"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp; 第三方支付产品:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="okcProd"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp; 第三方支付(卡异动):&nbsp;&nbsp;&nbsp;<label id="okcTrans"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp; 第三方支付(条码卡):&nbsp;&nbsp;&nbsp;<label id="strOKPqypwyByTMCard"/></td></tr>
		
		<tr><td colspan="3">===========================</td></tr>
		<tr><td colspan="3">&nbsp; 第三方支付合计:&nbsp;&nbsp;&nbsp;<label id="okcTotal"/></td></tr>
		<tr><td colspan="3">===========================</td></tr> 
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;团购卡服务:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="tgkService"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;团购卡产品:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="tgkProd"/></td></tr>
		<tr><td colspan="3"  align="left">&nbsp;&nbsp;&nbsp;团购卡(卡异动):&nbsp;&nbsp;&nbsp;<label id="tgkTrans"/></td></tr>
		<tr><td colspan="3">===========================</td></tr>
		<tr><td colspan="3">&nbsp;团购卡合计:&nbsp;&nbsp;&nbsp;<label id="tgkTotal"/></td></tr>
		 <tr><td colspan="3">===========================</td></tr>
		
		
	 	<tr><td colspan="3"  align="left">卡异动:<label id="totalCardTrans"/></td></tr>
		 <tr><td colspan="3">===========================</td></tr>
		<tr><td colspan="3">&nbsp;&nbsp;&nbsp;销卡服务:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="cardSalesServices"/></td></tr>
		<tr><td colspan="3">&nbsp;&nbsp;&nbsp;销卡产品:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="cardSalesprod"/></td></tr>
		<tr><td colspan="3">&nbsp;&nbsp;&nbsp;收购卡服务:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="acquisitionCardServices"/></td></tr>
		<tr><td colspan="3">&nbsp;&nbsp;&nbsp;经理签单:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="manageSigning"/></td></tr>
		<tr><td colspan="3">&nbsp;&nbsp;&nbsp;支出登记:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="payOutRegister"/></td></tr>
		<tr><td colspan="3">&nbsp;&nbsp;&nbsp;积分消费:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="integralPay"/></td></tr>
		<tr><td colspan="3">&nbsp;&nbsp;&nbsp;疗程消费:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="proPay"/></td></tr>
		<tr><td colspan="3">&nbsp;&nbsp;&nbsp;现金抵用券:&nbsp;&nbsp;&nbsp;<label id="cashDyPay"/></td></tr>
		<tr><td colspan="3">&nbsp;&nbsp;&nbsp;项目抵用券:&nbsp;&nbsp;&nbsp;<label id="prjDyPay"/></td></tr>
		<tr><td colspan="3">&nbsp;&nbsp;&nbsp;条码卡消费:&nbsp;&nbsp;&nbsp;<label id="tmkPay"/></td></tr>
		<tr><td colspan="3">===========================</td></tr>
 	</table> 
 	</div>
		</div>
		<div position="right" >
        	<div id="commoninfodivCashDate" style="margin:0; padding:0">
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