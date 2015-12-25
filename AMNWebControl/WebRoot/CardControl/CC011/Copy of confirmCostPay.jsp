<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    
    <link rel="stylesheet" type="text/css" href="<%=ContextPath%>/common/selfButton/css/page.css" />
    <link rel="stylesheet" type="text/css" href="<%=ContextPath%>/common/selfButton/buttons/buttons.css" />

    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
	 	$(function ()
   		{
   			$("#otherPaytoolbar1").ligerToolBar({ items: [
      		    { text: '支付项目:&nbsp;<label id="lbneedItem"></label>'}
            ]
            });
            $("#otherPaytoolbar2").ligerToolBar({ items: [
      		     { text: '支付方式:&nbsp;<label id="lbOldPay"></label><input type="hidden" id="curlbPayMode" name="curlbPayMode"/>' },
      		     { text: '折扣系数&nbsp;<label id="lbRatePay"></label>' }
            ]
            });
            
		/*
            $("#divstandprice").ligerToolBar({ items: [
      		    { text: '标准价:&nbsp;<label id="lbstandprice"></label>'}
            ]
            });
             $("#divoneprice").ligerToolBar({ items: [
      		    { text: '单次价:&nbsp;<label id="lboneprice"></label>'}
            ]
            });
             $("#divmemberprice").ligerToolBar({ items: [
      		    { text: '会员价:&nbsp;<label id="lbmemberprice"></label>'}
            ]
            });
            $("#divtyprice").ligerToolBar({ items: [
      		    { text: '体验价:&nbsp;<label id="lbtyprice"></label>'}
            ]
            });
            $("#payStandMode").ligerButton(
	         {
	             text: '确认支付', width: 180,
		         click: function ()
		         {
		             payOtheMode(1);
		         }
	         });
	         $("#payOneMode").ligerButton(
	         {
	             text: '确认支付', width: 180,
		         click: function ()
		         {
		             payOtheMode(2);
		         }
	         });
	          $("#payMemberMode").ligerButton(
	         {
	             text: '确认支付', width: 180,
		         click: function ()
		         {
		             payOtheMode(3);
		         }
	         });
	          $("#payTyMode").ligerButton(
	         {
	             text: '确认支付', width: 180,
		         click: function ()
		         {
		             payOtheMode(4);
		         }
	         });
	         try
	         {
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
     		
     			document.getElementById("curlbPayMode").value="4"
     			document.getElementById("lbmemberprice").innerHTML=checkNull(parent.costCurProjectinfo.memberprice);
     		}
     		else
     		{
     			document.getElementById("lbOldPay").innerHTML=parent.parent.loadCommonControlValue("ZFFS",0,"A");
     			document.getElementById("curlbPayMode").value="A"
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
		    */
   		});
   		
   		
   		function payOtheMode(obj)
   		{
   			var curPayMode=document.getElementById("curlbPayMode").value;
   			var curRate=document.getElementById("lbRatePay").innerHTML*1;
   			var curPrice=0;
   			var csdisprice=checkNull(parent.costCurProjectinfo.saleprice)*1;
   			var vcostpricetype=0;
   			if(obj==1)
   			{
   				curPrice=document.getElementById("lbstandprice").innerHTML*1*curRate;
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
   				parent.$.ligerDialog.error("选择单价为0有误,请确认!");
   				parent.shownewPayDialogmanager.close();
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
		     															 csproseqno:0,shareflag:0,costpricetype:0,costpricetype: vcostpricetype*1});
	       	
	       	parent.shownewPayDialogmanager.close();
	       	}
	       	
   		}
		
	</script>

</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 20px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td colspan="2"><div id="otherPaytoolbar1"></div> </td></tr>
		<tr><td colspan="2"><div id="otherPaytoolbar2"></div> </td></tr>
		<tr>
			<td colspan="2">
				<div id="buttonContainer">
				    <a href="#" class="button blue medium">Big Button</a>
				    <a href="#" class="button green medium">Big Button</a>
				    <a href="#" class="button orange medium">Big Button</a>
				    <a href="#" class="button gray medium">Big Button</a>
				</div>
			</td>
		</tr>
	</table>	
	</div>

</body>
</html>
