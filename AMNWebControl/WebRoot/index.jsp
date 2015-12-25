<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  		<title>发货对账表</title>
		<link rel="stylesheet" href="<%=ContextPath%>/fullScreen/css/desktop.css" />
		<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
		<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
		<script type="text/javascript" src="<%=ContextPath%>/common/prototype.js"></script>
		<STYLE TYPE="text/css">
		.float_header{
			 position: relative; 
			 top: expression(eval(this.parentElement.parentElement.parentElement.scrollTop-2)); 
			 background-color: #5D7B9D; 
			 color: White; font-weight: bold; 
			 height: 20px; 
			 z-index: 2;
		 }
		
		</STYLE>
		
  </head>
  <body background="<%=ContextPath%>/fullScreen/images/testback1.jpg">

  		
		<div style="float: right;right:20px">
		<table width="100%" border="0" cellspacing="1" cellpadding="0" id="storeGrid">
			<tr>
				<td align="left" width="31%" valign="top">
				 <fieldset style="height:620px;width:90%">
				<legend>发货单列表</legend>
					
					<table width="100%" align="left" id="tbChiefer"  border="1" cellspacing="0" cellpadding="0">
					<tr class="float_header" align="center">
						<td >
							仓库：
							<s:select  name="strSearchStoreId"   id="strSearchStoreId" list="#{'01':'美容仓库','02':'美发仓库','*':'所有'}"  theme="simple" style="height: 40px;width: 80px;" />
						 	<input type="button" value="查询" onclick="padInterfaceChangeStore()" style="height: 40px;width: 100px;" >
						</td>
					</tr>
					</table>
					<div style="overflow-y: auto; overflow-x: visible; width: 100%; height: 560px; text-align: center">
					<table width="100%" height="100%" align="left" id="tbChiefer"  border="1" cellspacing="0" cellpadding="0">
					<tbody id="billnos">
					<tr style="height:30px">
						<td align="left">
							&nbsp;
						</td>
					
					</tr>		
					</tbody>		
					</table>
					</div>
				</td>
				<td  valign="top" align="left">
				 	<fieldset style="height:620px;width:95%">
						<legend>发货单明细[<font color="blue"><label id="curbillNo"></label></font>]共[<font color="blue"><label id="curbillCount"></label></font>]条</legend>
						
						<table width="100%" align="left" id="tbChiefer"  border="1" cellspacing="0" cellpadding="0">
							<tr class="float_header" align="center">
							<td align="center" width="120px">门店名称</td>
							<td align="center" width="100px">产品编号</td>
							<td align="center" width="280px">产品名称</td>
							<td align="center" width="100px">申请数量</td>
							<td align="center" width="100px">操作</td>
							</tr>
							</table>
							<div style="overflow-y: auto; overflow-x: visible; width: 100%; height: 563px;">
							<table width="100%" align="left"   border="1" cellspacing="0" cellpadding="0" id="tbdetail">
							<tbody id="detials">
							<tr style="height:40px">
								
								<td width="120px">&nbsp;</td>
								<td width="100px">&nbsp;</td>
								<td width="280px">&nbsp;</td>
								<td width="100px">&nbsp;</td>
								<td width="100px">&nbsp;</td>
							</tr>
							</tbody>
							</table>
							</div>
					</fieldset>
				</td>
			</tr>
			
		</table>
       
		
		</div>
		
		<script type="text/javascript">
		var searchBillState="Y";
		function padInterfaceChangeStore()
		{
		try
		{
			var requestUrl ="padSearch/padInterfaceChangeStore.action";
			var params="strSearchStoreId="+document.getElementById("strSearchStoreId").value;
			var responseMethod="padInterfaceChangeStoreMessage";
		
			sendRequestForParams(requestUrl,responseMethod,params ); 
			}catch(e){alert(e.message);}
			
		}
		function padInterfaceChangeStoreMessage(request)
		{
			try
			{
				var responsetext = eval("(" + request.responseText + ")");
				var lsSearchBills=responsetext.lsSearchBills;	
				clearPreviousResult_report("billnos");
				
				showDataValidate(lsSearchBills);
				//clearOption("selectBillId");
				//for (var key in lsSearchBills) {  
					//addOption(key,lsSearchBills[key],document.getElementById("selectBillId"));
        		//}  
			}catch(e){alert(e.message);}
		}
    	
    	function showDataValidate(dataSet)
		{
			
			try{
	
		    clearPreviousResult_report("billnos");
			for (var key in dataSet) {  
				addRow_master(checkNull(dataSet[key]),key,"billnos");	
        	}
        	}catch(e){alert(e.message);}
		}
	
	    function clearPreviousResult_report(tableId)
	    {
			 var tblPrjs = document.getElementById(tableId);
			 while(tblPrjs.childNodes.length>0)
		     {
			    tblPrjs.removeChild(tblPrjs.childNodes[0]);
		     }
	    } 
	    function addRow_master(value1,value2,gridBodyId)
	    {
	    	
			var cell;
			var row = document.createElement("tr");
			cell = createCellWithText("");
			cell.innerHTML="<a onclick=\"padInterfaceShowDetial('"+value2+"')\" >"+value1+"</a>";
			row.appendChild(cell);
			row.style.height="40px";
			document.getElementById(gridBodyId).appendChild(row);
		}
    	function checkNull(strValue)
		{
			if(strValue == null)
			{
				return "";
			}
			else
			{
				return strValue;
			}
		}
		function padInterfaceShowDetial(billid)
		{
			if(searchBillState=="N")
			{
				alert("当前单号未完成配货,请确认后完成配货");
				return ;
			}
			document.getElementById("curbillNo").innerHTML=billid;
			var requestUrl ="padSearch/padInterfaceShowDetial.action";
			var params="strSearchBillId="+billid;
			var responseMethod="padInterfaceShowDetialMessage";
		
			sendRequestForParams(requestUrl,responseMethod,params ); 
		}
		function padInterfaceShowDetialMessage(request)
		{
			try
			{
				var responsetext = eval("(" + request.responseText + ")");
				var lsSendBillInfo=responsetext.lsSendBillInfo;	
				clearPreviousResult_report("detials");
				document.getElementById("curbillCount").innerHTML=lsSendBillInfo.size();
				showDataDetial(lsSendBillInfo); 
			}catch(e){alert(e.message);}
		}
		
		function showDataDetial(dataSet)
		{
			try{
	
			if(dataSet!=null)
			{
			
				for (var i=0; i< dataSet.size();i++)
				{  
					
					addRow_detial(checkNull(dataSet[i].strCompName),checkNull(dataSet[i].strGoodsId),
					checkNull(dataSet[i].strGoodsName),checkNull(dataSet[i].orderAppCount)+"   ["+checkNull(dataSet[i].strGoodsUnit)+"]",
					checkNull(dataSet[i].strBillId),i*1,"detials");	
	        	}
	        		
	        	var row = document.createElement("tr");
	        	cell = createCellWithText("");
	        	cell.colSpan=5;
	        	cell.innerHTML="<input type=\"button\" value=\"完成配货\" onclick=\"padInterfacehHandTagert('"+document.getElementById("curbillNo").innerHTML+"')\" style=\"height: 55px;width:120px;background:green;\" >";
				cell.align="center";
				row.appendChild(cell);
				row.style.height="60px";
				document.getElementById("detials").appendChild(row);
			
        	}
        	}catch(e){alert(e.message);}
		}
		function addRow_detial(value1,value2,value3,value4,billno,seqno,gridBodyId)
	    {
			var cell;
			var row = document.createElement("tr");
			
			cell = createCellWithText(value1);
			cell.style.width="120px";
			row.appendChild(cell);
			cell.align="center";			
			cell = createCellWithText(value2);
			cell.style.width="90px";
			row.appendChild(cell);
						
			cell = createCellWithText(value3);
			cell.style.width="273px";
			row.appendChild(cell);
			
			cell = createCellWithText(value4);
			cell.style.width="100px";
			row.appendChild(cell);
			
			cell.align="center";
			cell = createCellWithText("");
			cell.innerHTML="<input type=\"hidden\" id=\""+seqno+"_handstate\" name=\""+seqno+"_handstate\" value=\"N\"/><input type=\"button\" value=\"标记\" id=\""+seqno+"\" onclick=\"padInterfacehHandDetial('"+value2+"','"+billno+"',this)\" style=\"height: 35px;width:60px;\" >";
		
			cell.align="center";
			cell.style.width="90px";
			row.appendChild(cell);
			row.style.height="40px";
			document.getElementById(gridBodyId).appendChild(row);
		}
		function padInterfacehHandDetial(goodsid,billid,obj)
		{
			var index=obj.id+"";		
			document.getElementById(index+"_handstate").value="Y";
			obj.style.background="red";
			searchBillState="N";
		}
		
		function padInterfacehHandTagert(curbillno)
		{

			var tbdetail = document.getElementById("tbdetail");
			var maxrow = tbdetail.rows.length*1;
			for(var i=0;i<maxrow*1-1;i++)
			{
				if(document.getElementById(i+"_handstate").value=="N")
				{
					alert("还有产品未完成配发,请确认!");
					return;
				}
			}			
			var requestUrl ="padSearch/padInterfacehHandTagert.action";
			var params="strSearchBillId="+curbillno;
			var responseMethod="padInterfacehHandTagertMessage";
		
			sendRequestForParams(requestUrl,responseMethod,params ); 
		}
		
		function padInterfacehHandTagertMessage(request)
		{
			try
			{
				var responsetext = eval("(" + request.responseText + ")");
				var message=responsetext.validateMessage;	
				if(	checkNull(message)!="" )
				{
					alert(message);
				}
				else
				{
					alert("操作成功!");
					clearPreviousResult_report("detials");
					padInterfaceChangeStore();
					searchBillState="Y";
				}
			}catch(e){alert(e.message);}
		}
	
		</script>
  </body>
</html>
