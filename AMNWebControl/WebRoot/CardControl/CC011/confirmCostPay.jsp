<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="../../common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="../../common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
     <script src="../../common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="../../common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script type="text/javascript" src="../../common/common.js"></script>
</head>

<body>
<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:14px;  ">
	<table style="font: 14px;line-height: 20px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td colspan="2"><div id="otherPaytoolbar1"></div> </td></tr>
		<tr><td colspan="2"><div id="otherPaytoolbar2"></div> </td></tr>
		<tr>
			<td colspan="2">

				<div id="buttonContainer">
				    <a href="javascript:payOtheMode('1')"  class="button big gray">标准价&nbsp;<label id="lbstandprice"></label> </a>
				    <a href="javascript:payOtheMode('2')"  class="button big blue">单次价:&nbsp;<label id="lboneprice"></label></a>
				    
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">

				<div id="buttonContainer">
				    <a href="javascript:payOtheMode('3')" class="button big green">会员价:&nbsp;<label id="lbmemberprice"></label></a>
				    <a href="javascript:payOtheMode('4')" class="button big orange">体验价:&nbsp;<label id="lbtyprice"></label></a>
				    
				</div>
			</td>
		</tr>
	</table>	
	</div>
<script language="JavaScript">
	 	$(function ()
   		{
   			$("#otherPaytoolbar1").ligerToolBar({ items: [
      		    { text: '支付方式:&nbsp;<label id="lbneedItem">2343</label>'}
            ]
            });
            $("#otherPaytoolbar2").ligerToolBar({ items: [
      		     { text: '支付项目:&nbsp;<label id="lbOldPay"></label><input type="hidden" id="curlbPayMode" name="curlbPayMode"/>' },
      		     { text: '折扣系数&nbsp;<label id="lbRatePay"></label>' }
            ]
            });
      
        try{
           document.getElementById("lbneedItem").innerHTML=parent.costCurProjectinfo.id.prjno+"  ["+parent.costCurProjectinfo.prjname+"]";
            if(parent.document.getElementById("billflag").value=="0")
     		{
     			document.getElementById("lbOldPay").innerHTML=parent.parent.loadCommonControlValue("ZFFS",0,"1");
     			document.getElementById("curlbPayMode").value="1";
     			document.getElementById("lbmemberprice").innerHTML=0;
            }
     		else if(parent.document.getElementById("billflag").value=="1")
     		{
     			document.getElementById("lbOldPay").innerHTML=parent.parent.loadCommonControlValue("ZFFS",0,"4");
     		
     			document.getElementById("curlbPayMode").value="4";
     			document.getElementById("lbmemberprice").innerHTML=checkNull(parent.costCurProjectinfo.memberprice);
     		}
     		else
     		{
     			document.getElementById("lbOldPay").innerHTML=parent.parent.loadCommonControlValue("ZFFS",0,"A");
     			document.getElementById("curlbPayMode").value="A";
     			document.getElementById("lbmemberprice").innerHTML=0;
     		}
     		
     		if(checkNull(parent.costCurProjectinfo.onecountprice)*1>0
     		|| checkNull(parent.costCurProjectinfo.memberprice)*1>0
     		|| checkNull(parent.costCurProjectinfo.onepageprice)*1>0)
     			document.getElementById("lbRatePay").innerHTML=1;
     		else 
     			document.getElementById("lbRatePay").innerHTML=parent.document.getElementById("itemdiscount").value*1;
     		document.getElementById("lbstandprice").innerHTML=checkNull(parent.costCurProjectinfo.saleprice);
		    document.getElementById("lboneprice").innerHTML=checkNull(parent.costCurProjectinfo.onecountprice);
		    
		    document.getElementById("lbtyprice").innerHTML=checkNull(parent.costCurProjectinfo.onepageprice);
		    }catch(e){alert(e.message);}
		    
		      });
		    function payOtheMode(obj)
   			{
   				try
   				{
	   				var curPayMode=document.getElementById("curlbPayMode").value;
		   			var curRate=document.getElementById("lbRatePay").innerHTML*1;
		   			var curPrice=0;
		   			var csdisprice=checkNull(parent.costCurProjectinfo.saleprice)*1;
		   			var vcostpricetype=0;
		   			if(obj==1)
		   			{
		   				curPrice=document.getElementById("lbstandprice").innerHTML*1*curRate;
		   				
		   				var intMonth=parent.document.getElementById("csdate").value.substring(5,7)*1;
					    if(intMonth==3)
					    {
			   				if((parent.costCurProjectinfo.prjpricetype=="1"))
				     		{
				     			if(document.getElementById("curlbPayMode").value=="4" || document.getElementById("curlbPayMode").value=="1" ||
				     			   document.getElementById("curlbPayMode").value=="6" || document.getElementById("curlbPayMode").value=="17")
				     			{
				     				var reut=true;
				     				if(parent.lsString!=null && parent.lsString.length>0)
				     				{
					     				for(var i=0;i<parent.lsString.length;i++)
					     				{
					     					if(parent.lsString[i]==parent.costCurProjectinfo.id.prjno)
					     					{
					     						reut=false;
					     						break;
					     					}
					     				}
					     			}
				     				
				     				if(reut)
				     				{
				     					curPrice=document.getElementById("lbstandprice").innerHTML*1*0.38;
				     					curRate=0.38;
				     				}
				     			}
				     		}
				     	}
					}
		   			else if(obj==2)
		   			{
		   				curPrice=document.getElementById("lboneprice").innerHTML*1*curRate;
		   				csdisprice=curPrice;
		   			}
		   			else if(obj==3)
		   			{
		   				curPrice=document.getElementById("lbmemberprice").innerHTML*1*curRate;
		   				csdisprice=curPrice;
		   			}
		   			else if(obj==4)
		   			{
		   				curPrice=document.getElementById("lbtyprice").innerHTML*1*curRate;
		   				vcostpricetype=1;
		   				csdisprice=curPrice;
		   			}
		   			if(curPrice*1==0)
		   			{
		   				parent.$.ligerDialog.error("选择单价为0有误,请确认");
		   				//parent.shownewPayDialogmanager.close();
		   			}
		   			else
		   			{
		   				var curNewRecord=parent.commoninfodivdetial.updateRow(parent.curRecord,{bcsinfotype:1,
						 														 csitemno: parent.costCurProjectinfo.id.prjno,
																				 csitemname: checkNull(parent.costCurProjectinfo.prjname),
																				 csitemcount: 1,
				     															 csunitprice: checkNull(parent.costCurProjectinfo.saleprice)*1,
				     															 csdisprice: checkNull(curPrice)*1,
				     															 csitemamt:  ForDight(curPrice*1,0),
				     															 csdiscount: ForDight(curRate,2),
				     															 cspaymode: curPayMode,
				     															 csproseqno:0,shareflag:0,costpricetype: vcostpricetype*1});
			       		parent.handPayList();
			       		parent.document.getElementById("wCostItemNo").readOnly="readOnly";
					    parent.document.getElementById("wCostItemNo").style.background="#EDF1F8";
						parent.document.getElementById("wCostItemBarNo").readOnly="readOnly";
					    parent.document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
					    parent.document.getElementById("wgCostCount").readOnly="readOnly";
					    parent.document.getElementById("wgCostCount").style.background="#EDF1F8";
					    parent.document.getElementById("wCostFirstEmpNo").select();
					    parent.document.getElementById("wCostFirstEmpNo").focus();
		       		}
				    parent.shownewPayDialogmanager.close();
				 }
				 catch(e)
				 {
				 	alert(e.message);
				 }
	       	}
 </script>

<link rel="stylesheet" type="text/css" href="selfButton/buttons/buttons.css" />

</body>
</html>
