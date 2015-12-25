<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>入库单</title>
  
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
							<font size="5" style="padding-top: 2px;"><b>阿玛尼入库单</b>
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
							 单号:
							<label id="bill_print"></label>
						</td>
						<td align="left">
							制单人:
							<label id="maker_print"></label>
						</td>
					</tr>
					<tr>
						<td width="8%">
							&nbsp;
						</td>
						<td align="left">
							入库日期:
							<label id="date_print"></label>
						</td>
						<td align="left">
							 仓库:
							<label id="house_print"></label>
						</td>
					</tr>
					<tr>
						<td width="8%">
							&nbsp;
						</td>
						<td align="left">
							入库方式:
							<label id="shopName_print"></label>
						</td>
						<td align="left">
							采购人:
							<label id="shopAddr_print"></label>
						</td>
					</tr>
				</table>
				<table id="goods_table" cellspacing="0" cellpadding="3" width="98%">
					<tbody id="print_goods">
						<tr style="font-weight: bold;" height="30px">
							<td align="center" width="15%">
								产品大类
							</td>
							<td align="center" width="15%">
								产品编码
							</td>
							<td align="center" width="30%">
								产品名称
							</td>
							<td align="center" width="10%">
								数量
							</td>
							<td align="center" width="6%">
								单位
							</td>
							<td align="center" width="8%">
								单价
							</td>
							<td align="center" width="10%">
								金额
							</td>
						</tr>
						<tr>
							<td colspan="7" align="center">
								&nbsp;
							</td>
						</tr>
					</tbody>

					<tr height="30px">
						<td align="center"  >
							总计
						</td>
						<td align="left"  >
							&nbsp;
						</td>
						<td align="left"  >
							&nbsp;
						</td>
						<td align="center"  >
							<label id="sumCount">
								&nbsp;
							</label>
						</td>
						<td align="center"  >
							&nbsp;
						</td>
						<td align="center"  >
							&nbsp;
						</td>
						<td align="center"  >
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
						<font size="5" style="padding-top: 2px;"><b>阿玛尼入库单</b>
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
						发货单号:
						<label id="bill_prints"></label>
					</td>
					<td align="left">
						制单人:
						<label id="maker_prints"></label>
					</td>
				</tr>
				<tr>
					<td width="8%">
						&nbsp;
					</td>
					<td align="left">
						入库日期:
						<label id="date_prints"></label>
					</td>
					<td align="left">
						仓库:
						<label id="house_prints"></label>
					</td>
				</tr>
				<tr>
					<td width="8%">
						&nbsp;
					</td>
					<td align="left">
						入库方式
						<label id="shopName_prints"></label>
					</td>
					<td align="left">
						采购人:
						<label id="shopAddr_prints"></label>
					</td>
				</tr>
			</table>
			<table id="goods_table_p" cellspacing="0" cellpadding="3" width="98%">
				<tbody id="print_goodss">
					<tr style="font-weight: bold;" height="30px">
						<td align="center" width="15%">
								产品大类
						</td>
						<td align="center" width="15%">
								产品编码
						</td>
						<td align="center" width="30%">
								产品名称
						</td>
						<td align="center" width="10%">
							 数量
						</td>
						<td align="center" width="6%">
							单位
						</td>
						<td align="center" width="8%">
							单价
						</td>
						<td align="center" width="10%">
							金额
						</td>
					</tr>
					<tr>
						<td colspan="7" align="center">
							&nbsp;
						</td>
					</tr>
				</tbody>
					<tr height="30px">
						<td align="center"  >
							总计
						</td>
						<td align="left"  >
							&nbsp;
						</td>
						<td align="left"  >
							&nbsp;
						</td>
						<td align="center"  >
							<label id="sumCountS">
								&nbsp;
							</label>
						</td>
						<td align="center"  >
							&nbsp;
						</td>
						<td align="center"  >
							&nbsp;
						</td>
						<td align="center"  >
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
		var sumGtb08f=0;
	    var sumGtb06f=0;
	   document.getElementById("bill_print").innerHTML=opener.document.getElementById("inserbillid").value;
 	   document.getElementById("maker_print").innerHTML=opener.document.getElementById("inserstaffid").value+" "+opener.document.getElementById("inserstaffname").value;
	   document.getElementById("house_print").innerHTML=opener.document.getElementById("inserwareid").value;
	   document.getElementById("date_print").innerHTML=opener.document.getElementById("inserdate").value+" "+opener.document.getElementById("insertime").value;
	   document.getElementById("shopAddr_print").innerHTML=opener.document.getElementById("inserstaffname").value;
	   document.getElementById("shopName_print").innerHTML=opener.document.getElementById("insertype").value ;
	   /***************打印start*****************/
	   document.getElementById("bill_prints").innerHTML=opener.document.getElementById("inserbillid").value;
 	   document.getElementById("maker_prints").innerHTML=opener.document.getElementById("inserstaffid").value+" "+opener.document.getElementById("inserstaffname").value;
	   document.getElementById("date_prints").innerHTML=opener.document.getElementById("inserdate").value+" "+opener.document.getElementById("insertime").value;
	   document.getElementById("house_prints").innerHTML=opener.document.getElementById("inserwareid").value;
	   document.getElementById("shopAddr_prints").innerHTML=opener.document.getElementById("inserstaffname").value;
	   document.getElementById("shopName_prints").innerHTML=opener.document.getElementById("insertype").value ; 
	   clearPreviousResult_report();
	   var goodsClass = "";
	   var sumClassGtb06f=0;
	   var sumClassGtb08f=0;
	   for(var i=0;i<opener.lsDgoodsinserts.length;i++)
	   { 
	  	   if(goodsClass!="" && goodsClass!=opener.lsDgoodsinserts[i].changeoption)
	  	   {
	  	   		
	  	   		addRow_print(goodsClass,"小计","--",sumClassGtb06f,"--", "--",sumClassGtb08f,"--");
	  	   		sumClassGtb06f=0;
	  	   		sumClassGtb08f=0;
	  	   }
	  	   goodsClass=opener.lsDgoodsinserts[i].changeoption;
	       sumClassGtb06f=sumClassGtb06f*1+opener.lsDgoodsinserts[i].insercount*1;
		   sumClassGtb08f=sumClassGtb08f*1+opener.lsDgoodsinserts[i].goodsamt*1;
	       addRow_print(goodsClass, 
	       opener.parent.loadCommonControlValue("WPTJ",0,opener.lsDgoodsinserts[i].changeoption),
	       opener.lsDgoodsinserts[i].insergoodsname,opener.lsDgoodsinserts[i].insercount,
	       opener.lsDgoodsinserts[i].inserunit,opener.lsDgoodsinserts[i].goodsprice,opener.lsDgoodsinserts[i].goodsamt,opener.lsDgoodsinserts[i].insergoodsno);
	       sumGtb06f+=opener.lsDgoodsinserts[i].goodsamt*1;
	       sumGtb08f+=opener.lsDgoodsinserts[i].insercount*1;
	   }
	  if(goodsClass!="")
	   {
	   		addRow_print(goodsClass,"小计","--",sumClassGtb06f,"--", "--",sumClassGtb08f,"--");
	   }
	  
	   document.getElementById("sumCountS").innerHTML=sumGtb08f;
	   document.getElementById("sumAmtS").innerHTML=maskAmt(sumGtb06f,2);
	   document.getElementById("sumCount").innerHTML=sumGtb08f;
	   document.getElementById("sumAmt").innerHTML=maskAmt(sumGtb06f,2); 
	  // alert(document.getElementById("printContent").innerHTML);
	  }catch(e){alert(e.message);}
	  //window.print();
	}
	function addRow_print(goodsClass,changeoption,insergoodsname,insercount,inserunit,goodsprice,goodsamt,goodsno)
    {
       var result = new Array(7);
       result[0]=changeoption;
       result[1]=goodsno;
       result[2]=insergoodsname;
       result[3]=insercount;
       result[4]=inserunit;
       result[5]=goodsprice;
       result[6]=goodsamt;
	   addRow_print_report(result,"print_goods");
	   addRow_print_report(result,"print_goodss");
	   adjustAlignCenter("print_goods",0);
	  //adjustAlignCenter("print_goods",1);
	
	   
	   adjustAlignCenter("print_goods",3);
	   adjustAlignCenter("print_goods",5);
	   adjustAlignCenter("print_goods",6);
	   
	   //adjustAlignCenter("print_goodss",1);
	   adjustAlignCenter("print_goodss",0);
	
	   adjustAlignCenter("print_goodss",3);
	   adjustAlignCenter("print_goodss",5);
	   adjustAlignCenter("print_goodss",6);
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
