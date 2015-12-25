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
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script language="JavaScript">
		 
		$(function ()
   		{
   			$("#dispatchStaff").ligerButton(
	         {
	             text: '确认赠送', width: 120,
		         click: function ()
		         {
		             confirmSend();
		         }
	     	});
	     	
	     });
	     	function hotKeyOfSelf(key)
			{
				if(key==13)//回车
				{
					window.event.keyCode=9; //tab
					window.event.returnValue=true;
				}
			}
		
	     function confirmSend()
	     {
	     	if(document.getElementById("sendgoodsNo").value=="")
	     	{
	     		$.ligerDialog.error("请确认赠送产品编号");
	     		return ;
	     	}
	     	if(document.getElementById("sendgoodsCount").value*1==0)
	     	{
	     		$.ligerDialog.error("请确认赠送产品数量");
	     		return ;
	     	}
	     	var params = "sendgoodsNo="+document.getElementById("sendgoodsNo").value;			
    		params =params+ "&sendgoodsCount="+document.getElementById("sendgoodsCount").value;	
    		params =params+ "&strIndexBar="+document.getElementById("sendIndexBar").value;	
	     	var requestUrl ="ic001/confirmSend.action";
   			var responseMethod="confirmSendMessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	     function validateGoodsNo(obj)
	     {
	     	if(obj.value=="")
	     	{
	     		document.getElementById("sendgoodsName").value="";
	     		document.getElementById("sendgoodsCount").value=0;
	     		document.getElementById("sendIndexBar").value="";
	     		return ;
	     	}
	     	var flag=0;
	     	if(parent.parent.lsGoodsinfo!=null && parent.parent.lsGoodsinfo.length>0)
			{
				for(var i=0;i<parent.parent.lsGoodsinfo.length;i++)
				{
					if( parent.parent.lsGoodsinfo[i].id.goodsno==obj.value
						|| checkNull(parent.parent.lsGoodsinfo[i].goodsabridge).toLowerCase()==obj.value.toLowerCase())
					{
							if(checkNull(parent.parent.lsGoodsinfo[i].useflag)==1)
							{
	        	 				$.ligerDialog.warn("产品!"+parent.lsGoodsinfo[i].goodsname+"已停用");
	        	 				obj.focus();
								obj.select();
	        	  				obj.value="";
	        	 				return ;
	        				}
							document.getElementById("sendgoodsName").value=checkNull(parent.parent.lsGoodsinfo[i].goodsname);
							document.getElementById("sendIndexBar").value=checkNull(parent.parent.lsGoodsinfo[i].goodsuniquebar);
							document.getElementById("sendgoodsCount").value=0;
							document.getElementById("sendgoodsCount").focus();
							document.getElementById("sendgoodsCount").select();
							flag=1;
							break;
					}
				}
			}
			if(flag==0)
			{
				$.ligerDialog.warn("产品编号不存在或已停用");
				obj.focus();
				obj.select();
	        	obj.value="";
			}
	     }
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td>产品编号:</td><td><input type="text" name="sendgoodsNo" id="sendgoodsNo"  style="width:200;"  onchange="validateGoodsNo(this)" /></td></tr>
		<tr><td>产品名称: </td><td><input type="text" name="sendgoodsName" id="sendgoodsName"  readonly="true" style="width:200;background:#EDF1F8;" /></td></tr>
		<tr><td>赠送数量:</td><td><input type="text" name="sendgoodsCount" id="sendgoodsCount"   style="width:60;" onchange="validatePrice(this)"/></td></tr>
		<tr><td colspan="2"> &nbsp;<input type="hidden" name="sendIndexBar" id="sendIndexBar"/></td></tr>
		<tr><td colspan="2" align=center><div id="dispatchStaff"></div></td></tr>
	</table>	
	</div>

</body>

</html>
