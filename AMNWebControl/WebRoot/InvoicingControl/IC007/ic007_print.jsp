<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>退货单</title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/InvoicingControl/IC001/ic001.js"></script>
		<style type="text/css">
			#scroll{height:160px;width:100%; overflow:scroll; font-size:12px; line-height:14px; color:#333333}
			#scroll table {background: #CCCCCC;}
			#scroll tr{background:#fff;text-indent:4px;}
			#scroll .biaoti{background: #88A243; color:#fff; padding:2px 0 0px 0; text-align:center;}
			#scroll .tr1{background:#fff;text-indent:4px;}
			#scroll .tr2{background:#efefef;}		
			@media print {
				.Noprint {
					display: none
				}
			}
			
			#text td {
				font-size: 50px;
				line-height: 15px;
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
				font-size: 14px;
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
				font-size: 14px;
			}
		</style>
	</head>

	<body onload="viewTicketReport()" style="margin: 0;">
		<center>
		<div class="Noprint" style="width: 100%; padding: 0px 0;"
			style=" ">
			<div id=scroll style="height: 520px; padding-top: 8px;">

				<table
					style="font-size: 18px; border: 0px solid #000; padding: 3px;"
					id="gridDetialDiv" width="100%" align="center">
					<tr>
							<td>
								<div id="operation"
				style="background-color: #efefef; height: 40px; padding-top: 8px;">
				<table width="100%">
					<tr>

						<td align="right">
							<input name="button" type="button" class="l-button l-btn2"
								onclick="window.print();" value="打　印" />
						</td>
						<td width="20%">
							&nbsp;
						</td>
						<td align="left">
							<input name="button" type="button"  class="l-button l-btn2"
								onclick="window.close();" value="退  出" />
						</td>
					</tr>
				</table>
			</div>
							</td>	
					</tr>
					<tr>
						<td height="30" colspan="2" align="center">
							<font size="5" style="padding-top: 2px;"><b>阿玛尼退货单</b>
							</font>
						</td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="1" width="98%">
					<tr>
						<td width="8%">
							&nbsp;
						</td>
						<td width="50%" align="left">
							 退货单号:
							<label id="bill_print"></label>
						</td>
						<td align="left">
							退货人:
							<label id="maker_print"></label>
						</td>
					</tr>
					<tr>
						<td width="8%">
							&nbsp;
						</td>
						<td align="left">
							退货日期:
							<label id="date_print"></label>
						</td>
						<td align="left">
							 退货门店:
							<label id="house_print"></label>
						</td>
					</tr>
					
				</table>
				<table id="goods_table" cellspacing="0" cellpadding="3" width="98%">
					<tbody id="print_goods">
						<tr style="font-weight: bold;" height="30px">
							<td align="center" width="10%">
								产品编号
							</td>
							<td align="center" width="35%">
								产品名称
							</td>
							<td align="center" width="10%">
								退货数量
							</td>
							<td align="center" width="6%">
								单位
							</td>
							<td align="center" width="8%">
								退货单价
							</td>
							<td align="center" width="10%">
								退货金额
							</td>
						</tr>
						<tr>
							<td colspan="6" align="center">
								&nbsp;
							</td>
						</tr>
					</tbody>

					<tr height="30px">
						<td align="center"  >
							总计
						</td>
							<td align="center"  >
							&nbsp;
						</td>
						<td align="right"  >
							<label id="sumCount">
								&nbsp;
							</label>
						</td>
						<td align="right"  >
							&nbsp;
						</td>
						<td align="right"  >
							&nbsp;
						</td>
						<td align="left"  >
							<label id="sumAmt">
								&nbsp;
							</label>
						</td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="1" width="98%">
					<tr height="30px">
						<td align="center">
							仓管员: ____________，_______年____月____日
						</td>
						<td align="right">
							退货人: ____________
						</td>
					</tr>
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
					<td height="30" colspan="2" align="center">
						<font size="5" style="padding-top: 2px;"><b>阿玛尼退货单</b>
						</font>
					</td>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="1" width="98%">
				<tr>
					<td width="8%">
						&nbsp;
					</td>
					<td width="50%" align="left">
						退货单号:
						<label id="bill_prints"></label>
					</td>
					<td align="left">
						退货人:
						<label id="maker_prints"></label>
					</td>
				</tr>
				<tr>
					<td width="8%">
						&nbsp;
					</td>
					<td align="left">
						退货日期:
						<label id="date_prints"></label>
					</td>
					<td align="left">
						退货门店:
						<label id="house_prints"></label>
					</td>
				</tr>
				
			</table>
			<table id="goods_table_p" cellspacing="0" cellpadding="3" width="98%">
				<tbody id="print_goodss">
					<tr style="font-weight: bold;" height="30px">
						<td align="center" width="10%">
								产品编号
							</td>
						<td align="center" width="35%">
								产品名称
						</td>
						<td align="center" width="10%">
							退货 数量
						</td>
						<td align="center" width="6%">
							单位
						</td>
						<td align="center" width="8%">
							退货单价
						</td>
						<td align="center" width="10%">
							退货金额
						</td>
					</tr>
					<tr>
						<td colspan="6" align="center">
							&nbsp;
						</td>
					</tr>
				</tbody>
					<tr height="30px">
						<td align="center"  >
							总计
						</td>
						<td align="center"  >
							&nbsp;
						</td>
						<td align="right"  >
							<label id="sumCountS">
								&nbsp;
							</label>
						</td>
						<td align="center"  >
							&nbsp;
						</td>
						<td align="right"  >
							&nbsp;
						</td>
						<td align="left"  >
							<label id="sumAmtS">
								&nbsp;
							</label>
						</td>
					</tr>
			</table>
			<table cellspacing="0" cellpadding="1" width="98%">
					<tr height="30px">
						<td align="center">
							仓管员: ____________，_______年____月____日
						</td>
						<td align="right">
							退货人: ____________
						</td>
					</tr>
				</table>
		</div>
		<input type="hidden" id="option_print" />
		<input type="hidden" id="time_print" />
		<input type="hidden" id="option_prints" />
		<input type="hidden" id="time_prints" />
		</center>
	</body>
</html>
<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>"; 
  	 function viewTicketReport()	{
		try
		{
	
	   document.getElementById("bill_print").innerHTML=opener.document.getElementById("returnbillid").value;
 	   document.getElementById("maker_print").innerHTML=opener.document.getElementById("returnstaffid").value+" "+opener.document.getElementById("returnstaffname").value;
	   document.getElementById("house_print").innerHTML=opener.document.getElementById("breturncompname").value;
	   document.getElementById("date_print").innerHTML=opener.document.getElementById("returndate").value+" "+opener.document.getElementById("returntime").value;
	    /***************打印start*****************/
	   document.getElementById("bill_prints").innerHTML=opener.document.getElementById("returnbillid").value;
 	   document.getElementById("maker_prints").innerHTML=opener.document.getElementById("returnstaffid").value+" "+opener.document.getElementById("returnstaffname").value;
	   document.getElementById("date_prints").innerHTML=opener.document.getElementById("returndate").value+" "+opener.document.getElementById("returntime").value;
	   document.getElementById("house_prints").innerHTML=opener.document.getElementById("breturncompname").value;
	   clearPreviousResult_report();
	   var goodsno = "";
	   var sumClassGtb06f=0;
	   var sumClassGtb08f=0;
	   var sumGtb08f=0;
	   var sumGtb06f=0;
	   for (var rowid in opener.commoninfodivdetial.records)
	   {
			var row =opener.commoninfodivdetial.records[rowid];
			goodsno=row.returngoodsno;
			addRow_print(goodsno,
	        row.returngoodsname,row.factreturncount,
	        row.returngoodsunit,row.factreturnprice,row.factreturnamt);
	      	sumGtb06f=sumGtb06f*1+row.factreturncount*1;
		    sumGtb08f=sumGtb08f*1+row.factreturnamt*1;
	   }
					
	   document.getElementById("sumCountS").innerHTML=sumGtb06f;
	   document.getElementById("sumAmtS").innerHTML=maskAmt(sumGtb08f,2);
	   document.getElementById("sumCount").innerHTML=sumGtb06f;
	   document.getElementById("sumAmt").innerHTML=maskAmt(sumGtb08f,2);
	 
	  }catch(e){alert(e.message);}
	  //window.print();
	}
	function addRow_print(goodsno,insergoodsname,insercount,inserunit,goodsprice,goodsamt)
    {
       var result = new Array(6);
       result[0]=goodsno;
       result[1]=insergoodsname;
       result[2]=insercount;
       result[3]=inserunit;
       result[4]=goodsprice;
       result[5]=goodsamt;
	   addRow_print_report(result,"print_goods");
	   addRow_print_report(result,"print_goodss");
	   adjustAlignCenter("print_goods",0);
	  //adjustAlignCenter("print_goods",1);
	   adjustAlign("print_goods",2);
	   adjustAlignCenter("print_goods",3);
	   adjustAlign("print_goods",4);
	   
	   
	   //adjustAlignCenter("print_goodss",1);
	   adjustAlignCenter("print_goodss",0);
	   adjustAlign("print_goodss",2);
	   adjustAlignCenter("print_goodss",3);
	   adjustAlign("print_goodss",4);
    }
 function addRow_print_report(result,gridBodyId)
	{
		try{
		var cell;
		var row = document.createElement("tr");
		row.height="30px";
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
			cell.setAttribute("align","right");
		}
	}
    
    function adjustAlignCenter(tableId,columnIndex)
	{
		var grid = document.getElementById(tableId);
		for(var i=1;i<grid.childNodes.length;i++)
		{
			var cell = grid.childNodes[i].cells[columnIndex];
			cell.setAttribute("align","center");
		}
	} 	 
	</script>
