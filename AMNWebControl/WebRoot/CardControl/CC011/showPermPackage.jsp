<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8"/>
	
</head>

<body>
<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:14px;  ">
	<table style="font: 14px;line-height: 20px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td>
				<div id="buttonContainer" style="white-space: nowrap;margin-top: 5px;text-align: center;">
				    <a href="javascript:permPackage('1')"  class="button blue" style="width: 60px;">弹力热烫 </a>
				    <a href="javascript:permPackage('2')"  class="button green" style="width: 80px;margin: 0 25px;">弹力生化烫</a>
				</div>
			</td>
		</tr>
	</table>	
	</div>
<script language="JavaScript">
    function permPackage(obj){
    	parent.addPermPackage(obj);		
    }
</script>
<link rel="stylesheet" type="text/css" href="selfButton/buttons/buttons.css" />

</body>
</html>
