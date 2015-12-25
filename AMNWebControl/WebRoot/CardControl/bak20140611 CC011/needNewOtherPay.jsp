<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
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
      		    { text: '分单项目:&nbsp;<label id="lbneedItem"></label>'}
            ]
            });	
            $("#otherPaytoolbar2").ligerToolBar({ items: [
                { text: '原支付:&nbsp;<label id="lbOldPay"></label>' },
                { text: '总支付金额:&nbsp;<label id="lbCostAmt"></label>' }
            ]
            });	
            $("#otherPaytoolbar3").ligerToolBar({ items: [
      		    { text: '分单支付:&nbsp;<select name="otherPay" id="otherPay" style="width:120"></select> &nbsp;&nbsp;金额:&nbsp;<input type="text" id="needpayamt" name="needpayamt" style="width:40" onchange="validatePrice(this)"/> '}
            ]
            });	
            $("#payOtheMode").ligerButton(
	         {
	             text: '确认支付', width: 140,
		         click: function ()
		         {
		             payOtheMode();
		         }
	         });
            document.getElementById("lbneedItem").innerHTML=parent.curRecord.csitemname;
            document.getElementById("lbOldPay").innerHTML=parent.curRecord.cspaymode+"("+parent.parent.loadCommonControlValue("ZFFS",0,parent.curRecord.cspaymode)+")";
            document.getElementById("lbCostAmt").innerHTML=parent.curRecord.csitemamt;
		    if(parent.strSalePayMode!="")
	   		{
	   			var strpaymode= new Array();
   				var paymode="";
   				var paymodename="";
	   			strpaymode=parent.strSalePayMode.split(";");
	   			
	   			for(var i=0;i<strpaymode.length;i++)
	   			{
	   				paymode=strpaymode[i];
	   				if(paymode!="1" && paymode!="6" && paymode!="14" && paymode!="15")
	   					continue;
	   				paymodename=parent.parent.loadCommonControlValue("ZFFS",0,paymode);
	   				parent.addOption(paymode,paymode+"."+paymodename,document.getElementById("otherPay"));
		   		}
		   		
	   		}
   		});
   		
   		
   		function payOtheMode()
   		{
   			if(document.getElementById("needpayamt").value*1==0)
   			{
   				$.ligerDialog.warn("请输入分单金额!");
   				return ;
   			}
   			if(document.getElementById("needpayamt").value*1> document.getElementById("lbCostAmt").innerHTML*1)
   			{
   				$.ligerDialog.warn("分单金额不能超过原金额!");
   				return ;
   			}
   			var csdisprice=parent.curRecord.csdisprice;
   			var csitemamt=parent.curRecord.csitemamt;
   			if(parent.curRecord.csitemcount*1>=1)
   				csdisprice=csdisprice*1*parent.curRecord.csitemcount*1;
   			var curnewRecord= parent.commoninfodivdetial.updateRow(parent.curRecord,{  csitemcount: ForDight(parent.curRecord.csitemcount*1/2,2),
   																	 csdisprice: ForDight(parent.curRecord.csdisprice*1-ForDight(document.getElementById("needpayamt").value*1/parent.curRecord.csdiscount*1,1),0),
	     															 csitemamt:  ForDight((csitemamt*1-document.getElementById("needpayamt").value*1),0)
	     														  });  
	     	parent.addRecord();
	     	parent.commoninfodivdetial.updateRow(parent.curRecord,{ bcsinfotype: curnewRecord.bcsinfotype,
	     														 	 csitemno:   curnewRecord.csitemno,
																	 csitemname: curnewRecord.csitemname,
																	 csitemcount: ForDight(curnewRecord.csitemcount,2),
	     															 csunitprice: ForDight(curnewRecord.csunitprice,1),
	     															 csdisprice:  ForDight(document.getElementById("needpayamt").value*1,0),
	     															 csitemamt:   ForDight(document.getElementById("needpayamt").value*1,0),
	     															 csdiscount:  ForDight(curnewRecord.csdiscount,2),
	     															 cspaymode: document.getElementById("otherPay").value});
	     	  parent.document.getElementById("wCostItemNo").readOnly="readOnly";
		      parent.document.getElementById("wCostItemNo").style.background="#EDF1F8";
	     	  parent.document.getElementById("wCostItemBarNo").readOnly="readOnly";
		      parent.document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
		      parent.document.getElementById("wgCostCount").readOnly="readOnly";
		      parent.document.getElementById("wgCostCount").style.background="#EDF1F8"; 
		      parent.document.getElementById("wCostPCount").readOnly="readOnly";
		      parent.document.getElementById("wCostPCount").style.background="#EDF1F8";   
		      parent.document.getElementById("wCostFirstEmpNo").select();
		      parent.document.getElementById("wCostFirstEmpNo").focus();
		      parent.handPayList();
	          //设置储值分单标致
	          if(curnewRecord.cspaymode=="4")
	          {
	          		parent.checkOtherPayFlag=1;
	          }
	          parent.shownewPayDialogmanager.close();
   		}
		
	</script>

</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td><div id="otherPaytoolbar1"></div> </td></tr>
		<tr><td><div id="otherPaytoolbar2"></div> </td></tr>
		<tr><td><div id="otherPaytoolbar3"></div>	</td></tr>
		<tr><td>&nbsp;&nbsp;	</td></tr>
		<tr><td align="right"><div id="payOtheMode"></div>		</td></tr>
	</table>	
	</div>

</body>
</html>
