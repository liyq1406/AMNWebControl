<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8"/>
	<link href="../../common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="../../common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
     <script src="../../common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="../../common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="../../common/common.js"></script>
</head>

<body>
<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:14px;  ">
	<table style="font: 14px;line-height: 20px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td colspan="2">

				<div id="buttonContainer">
				    <a href="javascript:payOtheMode('1')"  class="button big gray">动力补水 </a>
				    <a href="javascript:payOtheMode('2')"  class="button big blue">肩颈理疗</a>
				    <!--  <a href="javascript:payOtheMode('3')" class="button big green">头疗</a>-->
				</div>
			</td>
		</tr>
		
	</table>	
	<input type="hidden" id="curlbPayMode" name="curlbPayMode"/>
	</div>
<script language="JavaScript">
		var type=parent.document.getElementById("type").value;
	 	$(function()
   		{
   			if(parent.document.getElementById("billflag").value=="0")
     		{
     			document.getElementById("curlbPayMode").value="1";
            }
     		else if(parent.document.getElementById("billflag").value=="1")
     		{
     			document.getElementById("curlbPayMode").value="4"
     		}
     		else
     		{
     			document.getElementById("curlbPayMode").value="A"
     		}
   		});
   			
    
		    function payOtheMode(obj)
   			{
   				var curPayMode=document.getElementById("curlbPayMode").value;
	   			var curRate=1;
	   			var curPrice=0;
	   			var csdisprice=98;
	   			var vcostpricetype=0;
	   			var curitemNo="";
	   			var curitemname="";
	   			if(obj==1)
	   			{
	   				curitemNo="4600001";
	   				curitemname="动力补水";
	   			}
	   			else if(obj==2)
	   			{
	   				curitemNo="4600014";
	   				curitemname="肩颈理疗";
	   			}
	   			/*else if(obj==3)
	   			{
	   				curitemNo="4600015";
	   				curitemname="头疗";
	   			}*/
	   			parent.addRecord();
	   			if(type==1)
	   			{
	   				var curNewRecord=parent.commoninfodivdetial.updateRow(parent.curRecord,{bcsinfotype:1,
					 														 csitemno: curitemNo,
																			 csitemname: curitemname,
																			 csitemcount: 1,
			     															 csunitprice: 88,
			     															 csdisprice: 88,
			     															 csitemamt:  88,
			     															 csdiscount: 1,
			     															 cspaymode: curPayMode,
			     															 csproseqno:0,shareflag:0,costpricetype:2 });
			     }
			     else
			     {
			     	var curNewRecord=parent.commoninfodivdetial.updateRow(parent.curRecord,{bcsinfotype:1,
					 														 csitemno: curitemNo,
																			 csitemname: curitemname,
																			 csitemcount: 1,
			     															 csunitprice: 128,
			     															 csdisprice: 128,
			     															 csitemamt:  128,
			     															 csdiscount: 1,
			     															 cspaymode: curPayMode,
			     															 csproseqno:0,shareflag:0,costpricetype:3 });
			    }
			    //parent.document.getElementById("wCostSecondEmpNo").readOnly=true;
			    //parent.document.getElementById("wCostthirthdEmpNo").readOnly=true;
		       	parent.handPayList();
		       	parent.document.getElementById("wCostItemNo").readOnly="readOnly";
			    parent.document.getElementById("wCostItemNo").style.background="#EDF1F8";
				parent.document.getElementById("wCostItemBarNo").readOnly="readOnly";
			    parent.document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
			    parent.document.getElementById("wgCostCount").readOnly="readOnly";
			    parent.document.getElementById("wgCostCount").style.background="#EDF1F8";
			    parent.document.getElementById("wCostFirstEmpNo").select();
			    parent.document.getElementById("wCostFirstEmpNo").focus();
			    parent.shownewPayDialogmanager.close();
	       	}
 </script>

<link rel="stylesheet" type="text/css" href="selfButton/buttons/buttons.css" />

</body>
</html>
