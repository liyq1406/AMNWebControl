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
            $("#otherPaytoolbar4").ligerToolBar({ items: [
      		    { text: '<input type="radio" id="integralPay1" name="integralPay" value="1" checked="checked" />&nbsp;积分支付&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="integralPay2" name="integralPay" value="2" />&nbsp;美容积分支付'}
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
            if(parent.curRecord.csitemcount*1>=1)
            	document.getElementById("lbCostAmt").innerHTML=parent.curRecord.csdisprice*1*parent.curRecord.csitemcount*1;
           	else
           		document.getElementById("lbCostAmt").innerHTML=parent.curRecord.csdisprice*1;
		    if(parent.strSalePayMode!="")
	   		{
	   			var strpaymode= new Array();
   				var paymode="";
   				var paymodename="";
	   			strpaymode=parent.strSalePayMode.split(";");
	   			
	   			for(var i=0;i<strpaymode.length;i++)
	   			{
	   				paymode=strpaymode[i];
	   				if(paymode!="7" && paymode!="1" && paymode!="6" && paymode!="12")
	   					continue;
	   				paymodename=parent.parent.loadCommonControlValue("ZFFS",0,paymode);
	   				parent.addOption(paymode,paymode+"."+paymodename,document.getElementById("otherPay"));
		   		}
		   		
	   		}
		    $("#otherPay").change(function(){//积分支付：普通积分和美容积分
		    	if($(this).val()=="7"){
		    		$("#otherPaytoolbar4").show();
		    	}else{
		    		$("#otherPaytoolbar4").hide();
		    	}
		    });
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
   			if(parent.curRecord.csitemcount*1>=1)
   				csdisprice=csdisprice*1*parent.curRecord.csitemcount*1;
   			var curnewRecord= parent.commoninfodivdetial.updateRow(parent.curRecord,{  csitemcount: ForDight(parent.curRecord.csitemcount*1/2,1),
   																	 csdisprice: ForDight(parent.curRecord.csdisprice*1-document.getElementById("needpayamt").value*1,0),
	     															 csitemamt:  ForDight((csdisprice-document.getElementById("needpayamt").value*1)*parent.curRecord.csdiscount*1,0)
	     														  });  
	     	parent.addRecord();
	     	var dataRecord = { bcsinfotype: curnewRecord.bcsinfotype,
						 	 csitemno:   curnewRecord.csitemno,
							 csitemname: curnewRecord.csitemname,
							 csitemcount: ForDight(curnewRecord.csitemcount,1),
							 csunitprice: ForDight(curnewRecord.csunitprice,1),
							 csdisprice:  ForDight(document.getElementById("needpayamt").value*1,0),
							 csitemamt:  ForDight(document.getElementById("needpayamt").value*1,0),
							 csdiscount: 1,
							 cspaymode: document.getElementById("otherPay").value,
							 csfirstsaler: checkNull(curnewRecord.csfirstsaler),
							 csfirsttype: checkNull(curnewRecord.csfirsttype),
							 cssecondsaler: checkNull(curnewRecord.cssecondsaler),
							 cssecondtype: checkNull(curnewRecord.cssecondtype),
							 csthirdsaler: checkNull(curnewRecord.csthirdsaler),
							 csthirdtype: checkNull(curnewRecord.csthirdtype),
							 costpricetype: checkNull(curnewRecord.costpricetype),
							 csitemstate: checkNull(curnewRecord.csitemstate)};
	     	if($("#otherPay").val()=="7"){//判断是否为积分支付
	     		dataRecord.yearinid = $("input:radio[name='integralPay']:checked").val();
	     	}else{
	     		dataRecord.yearinid = "";
	     	}
	     	parent.commoninfodivdetial.updateRow(parent.curRecord, dataRecord);
	     	  parent.document.getElementById("wCostItemNo").readOnly="readOnly";
		      parent.document.getElementById("wCostItemNo").style.background="#EDF1F8";
	     	  parent.document.getElementById("wCostItemBarNo").readOnly="readOnly";
		      parent.document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
		      parent.document.getElementById("wgCostCount").readOnly="readOnly";
		      parent.document.getElementById("wgCostCount").style.background="#EDF1F8"; 
		      parent.document.getElementById("wCostPCount").readOnly="readOnly";
		      parent.document.getElementById("wCostPCount").style.background="#EDF1F8";   
		      
		      parent.document.getElementById("wCostFirstEmpNo").style.background="#EDF1F8";   
		      parent.document.getElementById("wCostFirstEmpNo").readOnly="readOnly";
		      parent.document.getElementById("wCostSecondEmpNo").style.background="#EDF1F8"; 
		      parent.document.getElementById("wCostSecondEmpNo").readOnly="readOnly";
		      parent.document.getElementById("wCostthirthdEmpNo").style.background="#EDF1F8"; 
		      parent.document.getElementById("wCostthirthdEmpNo").readOnly="readOnly";
		      
		      parent.document.getElementById("wCostFirstEmpNo").select();
		      parent.document.getElementById("wCostFirstEmpNo").focus();
		      parent.handPayList();
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
		<tr><td><div id="otherPaytoolbar4" style="display: none;"></div>	</td></tr>
		<tr><td>&nbsp;&nbsp;	</td></tr>
		<tr><td align="right"><div id="payOtheMode"></div>		</td></tr>
	</table>	
	</div>

</body>
</html>
