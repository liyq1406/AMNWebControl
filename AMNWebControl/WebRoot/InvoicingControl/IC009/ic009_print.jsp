<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>订货单</title>

<script type="text/javascript"
	src="<%=ContextPath%>/common/amnreport.js"></script>
<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
<script type="text/javascript"
	src="<%=ContextPath%>/common/standprint.js"></script>
<object id="PrintSend"
	classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>

</object>
<style type="text/css">
#scroll {
	height: 160px;
	width: 100%;
	overflow: scroll;
	font-size: 12px;
	line-height: 13px;
	color: #333333
}

#scroll table {
	background: #CCCCCC;
}

#scroll tr {
	background: #fff;
	text-indent: 4px;
}

#scroll .biaoti {
	background: #88A243;
	color: #fff;
	padding: 2px 0 0px 0;
	text-align: center;
}

#scroll .tr1 {
	background: #fff;
	text-indent: 4px;
}

#scroll .tr2 {
	background: #efefef;
}

@media print {
	.Noprint {
		display: none
	}
}

#text td {
	font-size: 50px;
	line-height: 13px;
}

#goods_table {
	border: #000000 solid;
	border-width: 2 1 1 2
}

#goods_table td {
	border: #000000 solid;
	border-width: 0 1 1 0
}

td {
	font-size: 12px;
}

#goods_table_p {
	border: #000000 solid;
	border-width: 2 1 1 2
}

#goods_table_p td {
	border: #000000 solid;
	border-width: 0 1 1 0
}

td {
	font-size: 12px;
}
</style>
</head>

<body onload="viewTicketReport()" style="margin: 0;">
	<center>
		<div class="Noprint" style="width: 100%; padding: 0px 0;" style=" ">
				<table
					style="font-size: 18px; border: 0px solid #000; padding: 3px;"
					id="printTitle" width="100%" align="center">
					<tr>
						<td><div
								style="background-color: #efefef; height: 40px; padding-top: 8px;">
								<table width="100%">
									<tr>
										<td align="right"><input name="button" type="button"
											class="l-button l-btn2" onclick="printCurPage();" value="打　印" />
										</td>
										<td width="20%">&nbsp;</td>
										<td align="left"><input name="button" type="button"
											class="l-button l-btn2" onclick="window.close();"
											value="退  出" /></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					
				</table>
				<div id=scroll style="padding-top: 8px;display:none" >
				<table cellspacing="0" cellpadding="1" width="98%" 
					id="printSendBill">
					
				</table>
				</div>
				<div id="goodDiv" style="height: 520px;">
				<table id="goods_table" border=1 cellSpacing=0 cellPadding=1 width="98%" style="border-collapse:collapse" bordercolor="#333333">
					<thead>
						<tr>
						<td height="30" colspan="8" align="center"><font size="5"
							style="padding-top: 2px;"><b>阿玛尼发货单</b> </font></td>
					</tr>
					<tr style="font-weight: bold;">
						<td></td>
						<td align="left" colspan=3">定货单号:<label id="bill_print"></label>
						</td>
						<td align="left" colspan="4">定货人: <label id="maker_print"></label></td>
					</tr>
					<tr style="font-weight: bold;">
						<td></td>
						<td align="left" colspan="3">定货日期: <label id="date_print"></label></td>
						<td align="left" colspan="4">定货仓库: <label id="house_print"></label></td>
					</tr>
					<tr style="font-weight: bold;">
						<td></td>
						<td align="left" colspan="3">客户名称: <label id="shopName_print"></label></td>
						<td align="left" colspan="4">门店地址: <label id="shopAddr_print"></label></td>
					</tr>
					<tr style="font-weight: bold;">
						<td></td>
						<td align="left" colspan="7">定货类型: <label id="sendType">
					</tr>
					<tr>
						<td colspan="8">&nbsp;</td>
					</tr>
						<tr style="font-weight: bold;" height="30px">
							<td align="center" width="15%">产品大类</td>
							<td align="center" width="10%">产品编号</td>
							<td align="center" width="35%">产品名称</td>
							<td align="center" width="10%">发货数量</td>
							<td align="center" width="6%">单位</td>
							<td align="center" width="8%">单价</td>
							<td align="center" width="6%">折扣</td>
							<td align="center" width="10%">金额</td>
						</tr>
					</thead>
					<tbody id="print_goods">

					</tbody>
					<tr height="30px" style="font-weight: bold;">
						<td align="center">总计</td>
						<td align="left">&nbsp;</td>
						<td align="right">&nbsp;</td>
						<td align="center"><label id="sumCount"> &nbsp; </label></td>
						<td align="center">&nbsp;</td>
						<td align="right">&nbsp;</td>
						<td align="right">&nbsp;</td>

						<td align="center"><label id="sumAmt"> &nbsp; </label></td>
					</tr>
					<tr height="30px" style="font-weight: bold;">
						<td align="center" colspan="2">仓管员: ____________</td>
						<td align="center" colspan="2">财务: ____________</td>
						<td align="center" colspan="4">签收人: ____________</td>
					</tr>
					<tfoot>
					<!--  <tr>
						<TD></TD>
						<TD></TD>
						<TD></TD>
						<TD tdata="SubSum" format="#" align="center">
							<font color="#0000FF">#</font>
						</TD>
						<td>
						</td>
						<td>
						</td>
						<td>
						</td>
						<td tdata="SubSum" fromat="#" align="center">
							<font color="#0000FF">#</font>
						</td>
					</tr>-->
					<tr>
						<TD tdata="pageNO" format="#" align="right" colspan="8" >
						  	第<font color="#0000FF">#</font>页
						</TD>
					</tr>
					</tfoot>
				</table>
				</div>
				<div id="footdiv" style="height: 520px; padding-top: 8px;">
				<table cellspacing="0" cellpadding="1" width="98%">
					
				</table>
				</div>
		</div>


		<div class="Noprint" id="cur_master"
			style="position: absolute; left: 10px; background-color: White; top: 60px; z-index: -1; width: 100%; height: 750px;">
		</div>
		<div id="printContent"
			style="position: absolute; left: 10px; top: 60px; z-index: -2">
			<table style="font-size: 13px; border: 0px solid #000; padding: 3px;"
				id="gridDetialDiv" width="100%" align="center">
				<tr>
					<td height="30" colspan="2" align="center"><font size="5"
						style="padding-top: 2px;"><b>阿玛尼订货单</b> </font></td>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="1" width="98%">
				<thead>
					<tr style="font-weight: bold;">
						<td width="8%">&nbsp;</td>
						<td width="56%" align="left">定货单号: <label id="bill_prints"></label>
						</td>
						<td align="left">定货人: <label id="maker_prints"></label></td>
					</tr>
					<tr style="font-weight: bold;">
						<td width="8%">&nbsp;</td>
						<td align="left">定货日期: <label id="date_prints"></label></td>
						<td align="left">定货仓库: <label id="house_prints"></label></td>
					</tr>
					<tr style="font-weight: bold;">
						<td width="8%">&nbsp;</td>
						<td align="left">客户名称: <label id="shopName_prints"></label></td>
						<td align="left">门店地址: <label id="shopAddr_prints"></label></td>
					</tr>
					<tr style="font-weight: bold;">
						<td width="8%">&nbsp;</td>
						<td align="left">定货类型: <label id="sendTypes"></label></td>
						<td align="left"></td>
					</tr>
			</table>
			<TABLE border=1 cellSpacing=0 cellPadding=1 width="100%"
				style="border-collapse:collapse" bordercolor="#333333">
				<thead>
					<tr style="font-weight: bold;" height="30px">
						<td align="center" width="15%">产品大类</td>
						<td align="center" width="10%">产品编号</td>
						<td align="center" width="35%">产品名称</td>
						<td align="center" width="10%">发货数量</td>
						<td align="center" width="6%">单位</td>
						<td align="center" width="8%">单价</td>
						<td align="center" width="6%">折扣</td>
						<td align="center" width="10%">金额</td>
					</tr>
				</thead>
				<tbody id="print_goodss">
				</tbody>
				<tr height="30px" style="font-weight: bold;">
					<td align="center">总计</td>
					<td align="left">&nbsp;</td>
					<td align="right">&nbsp;</td>
					<td align="center"><label id="sumCountS"> &nbsp; </label></td>
					<td align="center">&nbsp;</td>
					<td align="right">&nbsp;</td>
					<td align="right">&nbsp;</td>
					<td align="left"><label id="sumAmtS"> &nbsp; </label></td>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="0" width="98%">
				<tr height="30px">
					<td align="center">仓管员: ____________</td>
					<td align="center">财务: ____________</td>
					<td align="center">签收人: ____________</td>
				</tr>
			</table>
		</div>
		<input type="hidden" id="option_print" /> <input type="hidden"
			id="time_print" /> <input type="hidden" id="option_prints" /> <input
			type="hidden" id="time_prints" />
	</center>
</body>
</html>
<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>"; 
	function viewTicketReport()	{	  
	try
	{
	
		var sumGtb08f=0;
	    var sumGtb06f=0;
	 
	   //单号
	   document.getElementById("bill_print").innerHTML=opener.document.getElementById("orderbillid").value;
	   //发货人
 	   document.getElementById("maker_print").innerHTML=opener.document.getElementById("orderstaffid").value+" "+opener.document.getElementById("orderstaffname").value;
	   //发货仓库
	   document.getElementById("house_print").innerHTML=opener.document.getElementById("storewareid").value+" "+opener.document.getElementById("storewarename").value;
	   //发货日期
	   document.getElementById("date_print").innerHTML=opener.document.getElementById("orderdate").value+" "+opener.document.getElementById("ordertime").value;
	   //客户名称
	   document.getElementById("shopName_print").innerHTML=opener.document.getElementById("ordercompid").value+" "+opener.document.getElementById("bordercompname").value ;
	   document.getElementById("shopAddr_print").innerHTML="";
	
	   if(opener.document.getElementById("orderbilltype_h").value==1){
	  	 document.getElementById("sendType").innerHTML="门店产品";
	  	}
	   if(opener.document.getElementById("orderbilltype_h").value==2){
	    document.getElementById("sendType").innerHTML="员工产品";
	   }
	   /***************打印start*****************/
	     //单号
	   document.getElementById("bill_prints").innerHTML=opener.document.getElementById("orderbillid").value;
 	   document.getElementById("maker_prints").innerHTML=opener.document.getElementById("orderstaffid").value+" "+opener.document.getElementById("orderstaffname").value;
	   //发货仓库
	   document.getElementById("house_prints").innerHTML=opener.document.getElementById("storewareid").value+" "+opener.document.getElementById("storewarename").value;
	   //发货日期
	   document.getElementById("date_prints").innerHTML=opener.document.getElementById("orderdate").value+" "+opener.document.getElementById("ordertime").value;
	   //客户名称
	   document.getElementById("shopName_prints").innerHTML=opener.document.getElementById("ordercompid").value+" "+opener.document.getElementById("bordercompname").value;
	   //门店地址 
	   document.getElementById("shopAddr_prints").innerHTML="";
	   
	   if(opener.document.getElementById("orderbilltype_h").value==1){
	  	 document.getElementById("sendTypes").innerHTML="门店产品";
	   }
	   if(opener.document.getElementById("orderbilltype_h").value==2){
	     document.getElementById("sendTypes").innerHTML="员工产品";
	   }
	    clearPreviousResult_report();
	   var goodsClass = "";
	   var sumClassGtb06f=0;
	   var sumClassGtb08f=0;
	   if(opener.lsDgoodsorderinfo != null)
	   {
		   for(var i=0;i<opener.lsDgoodsorderinfo.length;i++)
		   { 
				if(goodsClass!="" && goodsClass!=opener.lsDgoodsorderinfo[i].goodspricetype)
				{
					addRow_print(goodsClass,"小计", "--",sumClassGtb06f,"--", "--","--",sumClassGtb08f,"--");
		      		sumClassGtb06f=0;
					sumClassGtb08f=0;
				}
			   sumClassGtb06f=sumClassGtb06f*1+opener.lsDgoodsorderinfo[i].ordergoodscount*1;
			   sumClassGtb08f=ForDight(sumClassGtb08f*1+opener.lsDgoodsorderinfo[i].ordergoodsamt,1);
		       goodsClass=opener.lsDgoodsorderinfo[i].goodspricetype;
		       addRow_print(goodsClass, 
		       opener.parent.loadCommonControlValue("WPTJ",0,opener.lsDgoodsorderinfo[i].goodspricetype),
		       opener.lsDgoodsorderinfo[i].ordergoodsname,
		       opener.lsDgoodsorderinfo[i].ordergoodscount,
		       opener.lsDgoodsorderinfo[i].ordergoodsunit,
		       opener.lsDgoodsorderinfo[i].ordergoodsprice,1,
		       ForDight(opener.lsDgoodsorderinfo[i].ordergoodsamt,1),
		       opener.lsDgoodsorderinfo[i].ordergoodsno);
		       sumGtb06f+=ForDight(opener.lsDgoodsorderinfo[i].ordergoodsamt,1);
		       sumGtb08f+=ForDight(opener.lsDgoodsorderinfo[i].ordergoodscount*1,1);
		   }
		}
	   if(goodsClass!="")
	   {
	   		addRow_print(goodsClass,"小计", "--",sumClassGtb06f ,"--", "--","--",sumClassGtb08f,"--");
	   }
	  
	   document.getElementById("sumCountS").innerHTML=sumGtb08f;
	   document.getElementById("sumAmtS").innerHTML=maskAmt(sumGtb06f,2);
	   document.getElementById("sumCount").innerHTML=sumGtb08f;
	   document.getElementById("sumAmt").innerHTML=maskAmt(sumGtb06f,2); 
	  // alert(document.getElementById("printContent").innerHTML);
	  }catch(e){alert(e.message);}
	  //window.print();
	}
	function addRow_print(goodsClass,goodspricetype,sendgoodsname,sendgoodscount,sendgoodsunit,sendgoodprice,sendgoodrate,sendgoodsamt,snedgoodsno)
    {
       var result = new Array(7);
       result[0]=goodspricetype;
       result[1]=snedgoodsno;
       result[2]=sendgoodsname;
       result[3]=sendgoodscount;
       result[4]=sendgoodsunit;
       result[5]=sendgoodprice;
       result[6]=sendgoodrate;
       result[7]=sendgoodsamt;
	   addRow_print_report(result,"print_goods");
	   addRow_print_report(result,"print_goodss");
	   	   
	   //adjustAlignCenter("print_goods",0);
	   //adjustAlignCenter("print_goods",1);
	   //adjustAlignCenter("print_goods",2);
	   adjustAlignCenter("print_goods",3);
	   //adjustAlignCenter("print_goods",4);
	   adjustAlignCenter("print_goods",5);
	   adjustAlignCenter("print_goods",6);
	   adjustAlignCenter("print_goods",7);
	   
	   
	   //adjustAlignCenter("print_goodss",1);
	   adjustAlignCenter("print_goodss",0);
	   //adjustAlign("print_goodss",2);
	   adjustAlignCenter("print_goodss",3);
	   adjustAlign("print_goodss",5);
	   adjustAlign("print_goodss",6);
	   adjustAlign("print_goodss",4);
    }
 function addRow_print_report(result,gridBodyId)
	{
		try{
		var cell;
		var row = document.createElement("tr");
		row.height="30px";
		row.style.fontWeight="bold";
		for(var i=0;i<result.length;i++)
		{
			cell = createCellWithText(result[i]);
			row.appendChild(cell);
		}
		document.getElementById(gridBodyId).appendChild(row);
		}catch(e){alert(alert(e.message))}
	}

/*******************printReportEnd*****************************************/
//创建一个带有文本的表格cell
	function createCellWithText(text)
	{
		var cell = document.createElement("td");
		var textNode = document.createTextNode(text);
		cell.appendChild(textNode);	
		return cell;
	}
//清除行	
    function clearPreviousResult_report()
    {
	   var tblPrjs = document.getElementById("print_goods");
	   while(tblPrjs.childNodes.length>1)
	   {
		  tblPrjs.removeChild(tblPrjs.childNodes[1]);
	   }
	   var tblPrjss = document.getElementById("print_goodss");
	   while(tblPrjss.childNodes.length>1)
	   {
		  tblPrjss.removeChild(tblPrjss.childNodes[1]);
	   }
    }
//
    function adjustAlign(tableId,columnIndex)
	{
		var grid = document.getElementById(tableId);
		for(var i=1;i<grid.childNodes.length;i++)
		{
			var cell = grid.childNodes[i].cells[columnIndex];
			cell.setAttribute("align","center");
		}
	}
    
    function adjustAlignCenter(tableId,columnIndex)
	{
		var grid = document.getElementById(tableId);
		for(var i=0;i<grid.childNodes.length;i++)
		{
			var cell = grid.childNodes[i].cells[columnIndex];
			cell.setAttribute("align","center");
		}
	}
	
	function printCurPage()
	{
		try
		{
		    var PrintSend=document.getElementById("PrintSend");
		    //alert(PrintSend.Version);
		 	//Stand_CheckPrintControl();//检查是否有打印控件
		 	//PrintSend.PRINT_INIT("总部下单_小票打印作业");
		 	//alert(2);
			//Stand_InitPrint("总部下单_小票打印作业");
			//Stand_SetPrintStyle("Alignment",2);
			//Stand_SetPrintStyle("HOrient",2);
			//Stand_SetPrintStyle("Bold",1);
			
			var printTitle = document.getElementById("printTitle").innerHTML;
			var printSendBill=document.getElementById("scroll").innerHTML;
			
			//
			//PrintSend.ADD_PRINT_TABLE(10,10,300,300,printTitle);
			PrintSend.SET_LICENSES("成都泰林科贸有限公司","649686669748688748719056235623","","");
			PrintSend.SET_PRINT_STYLE("FontSize","7");
			PrintSend.SET_PRINT_PAGESIZE(1,"","","A4");
			//PrintSend.ADD_PRINT_TABLE("10mm","2.5mm","210mm","297mm",printSendBill);
			PrintSend.ADD_PRINT_TABLE("10mm",0,"200mm","277mm",document.getElementById("gooddiv").innerHTML);
			//PrintSend.ADD_PRINT_TABLE("200mm","2.5mm","210mm","297mm",document.getElementById("footdiv").innerHTML);
			//PrintSend.ADD_PRINT_TABLE(10,10,2100,2900,printSendBill);
		    //Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TABLE,10,10,"210mm","297mm",printContent);
		    //PrintSend.PRINT();
		    PrintSend.PREVIEW();
		 }
		 catch(e)
		 {
		 	alert(e.message);
		 }
		    //Stand_Preview();
		    //Stand_Print();
	}
  	 	
	</script>
