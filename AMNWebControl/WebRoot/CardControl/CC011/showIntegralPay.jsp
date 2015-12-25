<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8"/>
	
</head>

<body>
<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:14px;  ">
	<table style="font: 14px;line-height: 20px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td style="font-size: 17px;text-align: center;">请选择以下方式支付！</td></tr>
		<tr>
			<td>
				<div id="buttonContainer" style="white-space: nowrap;margin-top: 5px;text-align: center;">
				    <a href="javascript:integralPay('1')"  class="button blue" style="width: 60px;">积分支付 </a>
				    <a href="javascript:integralPay('2')"  class="button green" style="width: 90px;margin: 0 25px;">美容积分支付</a>
				    <%--<a href="javascript:payOtheMode('0')" class="button gray" style="width: 40px;">取消</a>--%>
				</div>
			</td>
		</tr>
	</table>	
	</div>
<script language="JavaScript">
    function integralPay(obj){
    	parent.integralPayMethod(obj);		
    }
</script>
<link rel="stylesheet" type="text/css" href="selfButton/buttons/buttons.css" />

</body>
</html>
