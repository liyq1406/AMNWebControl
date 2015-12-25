<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>封帐作业</title>
		 <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
		<script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
		<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script> 	
 		<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
		<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
		<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC005/ac005.js"></script>
	</head>

	<body style="margin:0;">
	<div class="l-loading" style="display:block" id="pageloading"></div>
  	 <div id="ac005layout" style="width:100%; margin:0 auto; margin-top:4px; ">
	  	<div position="center"   id="designPanel"  style="width:100%;"> 
    	<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
			<tr>
				 <td width="560"><div ><%@include file="/common/search.frag"%></div></td>
				 <td width="60">查询日期：</td>
				 <td width="130">
						<input id="strFromDate" type="text" size="10" />
				 </td>
				 <td ><div id="compAnlysis"></div></td>
				 <td ><div id="billAnlysis"></div></td>
				 <td ><div id="overDayAnlysis"></div></td>
				 <td ><div id="closeForcibly"></div></td>
				 <td ><div id="uncloseForcibly"></div></td>
			</tr>
			<tr><td style="border-bottom:1px #000000 dashed" colspan="7"></td></tr>
		</table>
		<div style="width:100%;heigh:500;float:left;margin-top:0;margin-left:0px">
			<div id="gridContainer" style="margin-top:0;heigh:550;background:#fafafa; margin-bottom:0px;" >
			  <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #999;">
			  <tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#84A0C4" >
				<thead  class="gridHeaderx" >   
    				<tr align="center"  style="background:#A3C0E8;font-size:12px;">
					<td colspan="2" width="16%"><strong>类别</strong></td>
					<td width="6%"><strong>现金</strong></td>
					<td width="6%"><strong>银行卡</strong></td>
					<td width="6%"><strong>支票</strong></td>
					<td width="6%"><strong>储值</strong></td>
					<td width="6%"><strong>收购卡</strong></td>
					<td width="6%"><strong>经理签单</strong></td>
					<td width="6%"><strong>积分</strong></td>
					<td width="6%"><strong>签单挂帐</strong></td>
					<td width="6%"><strong>项目抵用卷</strong></td>
					<td width="6%"><strong>现金抵用卷</strong></td>
					<td width="6%"><strong>条码卡</strong></td>
					<td width="6%"><strong>指付通</strong></td>
					<td width="6%"><strong>ok卡</strong></td>
					<td width="6%"><strong>合计</strong></td>
					
				  </tr>  
  				</thead>  
				<tbody id = "dataSet" name="dataSet">
				  <tr bgcolor="#FFFFFF" style="font-size:12px;" >
				  	<td align="center" rowspan="3" bgcolor="#c3c3c3">项目/产品</td>
				  	<td align="center" bgcolor="#d3d3d3">项目消费</td>
				  	<td align="right" id="prj_cash"></td>
				  	<td align="right" id="prj_bank"></td>
				  	<td align="right" id="prj_check"></td>
				  	<td align="right" id="prj_card"></td>
				  	<td align="right" id="prj_sgcard"></td>
				  	<td align="right" id="prj_jlqd"></td>
				  	<td align="right" id="prj_point"></td>
				  	<td align="right" id="prj_qdgz"></td>
				  	<td align="right" id="prj_prjdy"></td>
				  	<td align="right" id="prj_cashdy"></td>
				  	<td align="right" id="prj_carddy"></td>
				  	<td align="right" id="prj_finger"></td>
				  	<td align="right" id="prj_okcard"></td>
				  	<td align="right" id="prj_total" bgcolor="Honeydew" style="color:red"></td>
				  	
				  </tr>
				  <tr bgcolor="#FFFFFF" style="font-size:12px;" >
				  	<td align="center" bgcolor="#d3d3d3">产品销售</td>
				  	<td align="right" id="goods_cash"></td>
				  	<td align="right" id="goods_bank"></td>
				  	<td align="right" id="goods_check"></td>
				  	<td align="right" id="goods_card"></td>
				  	<td align="right" id="goods_sgcard"></td>
				  	<td align="right" id="goods_jlqd"></td>
				  	<td align="right" id="goods_point"></td>
				  	<td align="right" id="goods_qdgz"></td>
				  	<td align="right" id="goods_prjdy"></td>
				  	<td align="right" id="goods_cashdy"></td>
				  	<td align="right" id="goods_carddy"></td>
				  	<td align="right" id="goods_finger"></td>
				  	<td align="right" id="goods_okcard"></td>
				  	<td align="right" id="goods_total"bgcolor="Honeydew" style="color:red"></td>
				  	
				  </tr>
				  <tr bgcolor="#EFEFEF" style="font-size:12px;" >
				  	<td align="center">小计</td>
				  	<td align="right" id="prj_goods_cash" style="color:blue"></td>
				  	<td align="right" id="prj_goods_bank" style="color:blue"></td>
				  	<td align="right" id="prj_goods_check" style="color:blue"></td>
				  	<td align="right" id="prj_goods_card" style="color:blue"></td>
				  	<td align="right" id="prj_goods_sgcard" style="color:blue"></td>
				  	<td align="right" id="prj_goods_jlqd" style="color:blue"></td>
				  	<td align="right" id="prj_goods_point" style="color:blue"></td>
				  	<td align="right" id="prj_goods_qdgz" style="color:blue"></td>
				  	<td align="right" id="prj_goods_prjdy" style="color:blue"></td>
				  	<td align="right" id="prj_goods_cashdy" style="color:blue"></td>
				  	<td align="right" id="prj_goods_carddy" style="color:blue"></td>
				  	<td align="right" id="prj_goods_finger" style="color:blue"></td>
				  	<td align="right" id="prj_goods_okcard" style="color:blue"></td>
				  	<td align="right" id="prj_goods_total" style="color:green;font-weight:bold;text-decoration:underline"></td>
				  	<td align="right"></td>
				  </tr>
				  <tr bgcolor="#FFFFFF" style="font-size:12px;" >
				  	<td align="center" rowspan="4" bgcolor="#c3c3c3">卡异动(充值)</td>
				  	<td align="center" bgcolor="#d3d3d3">开卡</td>
				  	<td align="right" id="kk_cash"></td>
				  	<td align="right" id="kk_bank"></td>
				  	<td align="right" id="kk_check"></td>
				  	<td align="right" id="kk_card"></td>
				  	<td align="right" id="kk_sgcard"></td>
				  	<td align="right" id="kk_jlqd"></td>
				  	<td align="right" id="kk_point"></td>
				  	<td align="right" id="kk_qdgz"></td>
				  	<td align="right" id="kk_prjdy"></td>
				  	<td align="right" id="kk_cashdy"></td>
				  	<td align="right" id="kk_carddy"></td>
				  	<td align="right" id="kk_finger"></td>
				  	<td align="right" id="kk_okcard"></td>
				  	<td align="right" id="kk_total"bgcolor="Honeydew" style="color:red"></td>
				  	
				  </tr>
				  <tr bgcolor="#FFFFFF" style="font-size:12px;" >
				  	<td align="center" bgcolor="#d3d3d3">充值/还款</td>
				  	<td align="right" id="kcz_cash"></td>
				  	<td align="right" id="kcz_bank"></td>
				  	<td align="right" id="kcz_check"></td>
				  	<td align="right" id="kcz_card"></td>
				  	<td align="right" id="kcz_sgcard"></td>
				  	<td align="right" id="kcz_jlqd"></td>
				  	<td align="right" id="kcz_point"></td>
				  	<td align="right" id="kcz_qdgz"></td>
				  	<td align="right" id="kcz_prjdy"></td>
				  	<td align="right" id="kcz_cashdy"></td>
				  	<td align="right" id="kcz_carddy"></td>
				  	<td align="right" id="kcz_finger"></td>
				  	<td align="right" id="kcz_okcard"></td>
				  	<td align="right" id="kcz_total"bgcolor="Honeydew" style="color:red"></td>
				  	
				  </tr>
				  <tr bgcolor="#FFFFFF" style="font-size:12px;" >
				  	<td align="center" bgcolor="#d3d3d3">卡异动</td>
				  	<td align="right" id="zkzk_cash"></td>
				  	<td align="right" id="zkzk_bank"></td>
				  	<td align="right" id="zkzk_check"></td>
				  	<td align="right" id="zkzk_card"></td>
				  	<td align="right" id="zkzk_sgcard"></td>
				  	<td align="right" id="zkzk_jlqd"></td>
				  	<td align="right" id="zkzk_point"></td>
				  	<td align="right" id="zkzk_qdgz"></td>
				  	<td align="right" id="zkzk_prjdy"></td>
				  	<td align="right" id="zkzk_cashdy"></td>
				  	<td align="right" id="zkzk_carddy"></td>
				  	<td align="right" id="zkzk_finger"></td>
				  	<td align="right" id="zkzk_okcard"></td>
				  	<td align="right" id="zkzk_total"bgcolor="Honeydew" style="color:red"></td>
				  	
				  </tr>
				
				 
				  <tr bgcolor="#EFEFEF" style="font-size:12px;" >
				  	<td align="center">小计</td>
				  	<td align="right" id="card_change_cash" style="color:blue"></td>
				  	<td align="right" id="card_change_bank" style="color:blue"></td>
				  	<td align="right" id="card_change_check" style="color:blue"></td>
				  	<td align="right" id="card_change_card" style="color:blue"></td>
				  	<td align="right" id="card_change_sgcard" style="color:blue"></td>
				  	<td align="right" id="card_change_jlqd" style="color:blue"></td>
				  	<td align="right" id="card_change_point" style="color:blue"></td>
				  	<td align="right" id="card_change_qdgz" style="color:blue"></td>
				  	<td align="right" id="card_change_prjdy" style="color:blue"></td>
				  	<td align="right" id="card_change_cashdy" style="color:blue"></td>
				  	<td align="right" id="card_change_carddy" style="color:blue"></td>
				  	<td align="right" id="card_change_finger" style="color:blue"></td>
				  	<td align="right" id="card_change_okcard" style="color:blue"></td>
				  	<td align="right" id="card_change_total" style="color:green;font-weight:bold;text-decoration:underline"></td>
				  	<td align="right"></td>
				  </tr>
				  
				  <tr bgcolor="#FFFFFF" style="font-size:12px;" >
				  	<td align="center" rowspan="3" bgcolor="#c3c3c3">其他</td>
				  	<td align="center" bgcolor="#d3d3d3">会员退卡</td>
				  	<td align="right" id="tk_cash"></td>
				  	<td align="right" id="tk_bank"></td>
				  	<td align="right" id="tk_check"></td>
				  	<td align="right" id="tk_card"></td>
				  	<td align="right" id="tk_sgcard"></td>
				  	<td align="right" id="tk_jlqd"></td>
				  	<td align="right" id="tk_point"></td>
				  	<td align="right" id="tk_qdgz"></td>
				  	<td align="right" id="tk_prjdy"></td>
				  	<td align="right" id="tk_cashdy"></td>
				  	<td align="right" id="tk_carddy"></td>
				  	<td align="right" id="tk_finger"></td>
				  	<td align="right" id="tk_okcard"></td>
				  	<td align="right" id="tk_total"bgcolor="Honeydew" style="color:red"></td>
				  	
				  </tr>
				   <tr bgcolor="#FFFFFF" style="font-size:12px;" >
				  	<td align="center" bgcolor="#d3d3d3">疗程兑换</td>
				  	<td align="right" id="lcdh_cash"></td>
				  	<td align="right" id="lcdh_bank"></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" id="lcdh_total"bgcolor="Honeydew" style="color:red"></td>
				  </tr>
				  <tr bgcolor="#FFFFFF" style="font-size:12px;" >
				  	<td align="center" bgcolor="#d3d3d3">条码卡开卡</td>
				  	<td align="right" id="tmk_cash"></td>
				  	<td align="right" id="tmk_bank"></td>
				  	<td align="right" id="tmk_check"></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" ></td>
				  	<td align="right" id="tmk_prjdy"></td>
				  	<td align="right" id="tmk_cashdy"></td>
				  	<td align="right" id="tmk_carddy"></td>
				  	<td align="right" id="tmk_finger"></td>
				  	<td align="right" id="tmk_okcard"></td>
				  	<td align="right" id="tmk_total" bgcolor="Honeydew" style="color:red"></td>
				  </tr>
				  <tr style="height:3px;"><td colspan="11"></td></tr>
				  <tr bgcolor="#c3c3c3" style="font-size:12px;">
				  	<td align="center" rowspan="4" bgcolor="#a3a3a3">营业汇总</td>
				  	<td align="center" bgcolor="#b3b3b3">总收入</td>
				  	<td align="right" id="totalIncome" style="color:MediumOrchid"></td>
				  	<td colspan="13">
				  		{=(项目/产品、卡异动/充值、退卡)现金合计+(项目/产品、卡异动/充值、退卡)银行卡合计+(项目/产品、卡异动/充值、退卡)支票合计}
				  	</td>
				  </tr>
				  <tr bgcolor="#c3c3c3" style="font-size:12px;">
				  	<td align="center" bgcolor="#b3b3b3">欠款</td>
				  	<td align="right" id="totalDebt" style="color:MediumOrchid"></td>
				  	<td colspan="13">
				  		{=(项目/产品)签单挂帐合计+(卡异动/充值)签单挂帐合计}
				  	</td>
				  </tr>
				  <tr bgcolor="#c3c3c3" style="font-size:12px;">
				  	<td align="center" bgcolor="#b3b3b3">实业绩</td>
				  	<td align="right" id="realPerformance" style="color:MediumOrchid"></td>
				  	<td colspan="13">
				  		{=(项目/产品)现金合计+(项目/产品)银行卡合计+(项目/产品)支票合计+(项目/产品)会员卡合计+(项目/产品)收购卡合计<br/>
				  		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;+(项目/产品)经理签单合计+(项目/产品)积分支付合计*0.15}
				  	</td>
				  </tr>
				  <tr bgcolor="#c3c3c3" style="font-size:12px;">
				  	<td align="center" bgcolor="#b3b3b3" >虚业绩</td>
				  	<td align="right" id="virtualPerformance" style="color:MediumOrchid"></td>
				  	<td colspan="13">
				  		{=(项目/产品、卡异动/充值、退卡)现金合计+(项目/产品、卡异动/充值、退卡)银行卡合计+(项目/产品、卡异动/充值、退卡)支票合计}
				  	</td>
				  </tr>
          		</tbody>
				</table>
				</td>
			  </tr>
			</table>
			</div>
			<div style="margin-top:3px">
				<fieldset >
				<legend style="color:green;font-size:13px"><strong>当日单据分析</strong></legend>
				<textarea id="analysisInfo" rows="12" cols="1" readonly="true" style="line-height:1.5;width:100%;background:#d3d3d3;color:blue;text-align:left">

				</textarea>
				</fieldset>
			</div>
		</div>
	</div>
		
		</div>
	<script type="text/javascript">  	 	var contextURL="<%=request.getContextPath()%>";
</script>
</body>
</html>
