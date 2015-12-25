<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>出库单</title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/InvoicingControl/IC001/ic004.js"></script>
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
							<input name="button" type="button"  class="l-button l-btn2"
								onclick="printCurPage();" value="打　印" />
						</td>
						<td width="20%">
							&nbsp;
						</td>
						<td align="left">
							<input name="button" type="button" class="l-button l-btn2"
								onclick="window.close();" value="退  出" />
						</td>
					</tr>
				</table>
			</div>
						</td>
					</tr>
					<tr>
						<td height="30" colspan="2" align="center">
							<font size="5" style="padding-top: 2px;"><b>阿玛尼出库单</b>
							</font>
						</td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="1" width="98%">
					<tr>
						<td width="4%" align="left">
							 &nbsp;
						</td>
						<td width="30%" align="left">
							   单号:
							<label id="bill_print"></label>
						</td>
						<td width="30%" align="left">
							制单人:
							<label id="maker_print"></label>
						</td>
						<td width="40%">
							出库日期:
							<label id="date_print"></label>
						</td>
					</tr>
					<tr>
						<td width="10%" align="left">
							 &nbsp;
						</td>
						<td  >
							仓库:
							<label id="house_print"></label>
						</td>
						<td align="left">
							出库方式:
							<label id="houses_print"></label>
						</td>
						<td align="left">
							领取人:
							<label id="user_print"></label>
						</td>
					</tr>
					<tr>
					<td width="10%" align="left">
							 &nbsp;
						</td>
						<td align="left">
							客户名称:
							<label id="shopName_print"></label>
						</td>
						<td width="8%">
							联系人：
							<label id="users_print"></label>
						</td>
						<td align="left">
							电话:
							<label id="telphone_print"></label>
						</td>
					</tr>
					<tr>
					<td width="10%" align="left">
							 &nbsp;
						</td>
						<td align="left">
							类型:
							<label id="shop_print"></label>
						</td>
						<td align="left" colspan="2">
							门店地址:
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
								发货数量
							</td>
							<td align="center" width="6%">
								单位
							</td>
							<td align="center" width="8%">
								单价
							</td>
							<td align="center" width="6%">
								折扣
							</td>
							<td align="center" width="10%">
								金额
							</td>
						</tr>
						<tr>
							<td colspan="8" align="center">
								&nbsp;
							</td>
						</tr>
					</tbody>

					<tr height="30px">
						<td align="center" width="10%">
							总计
						</td>
						<td align="left"  >
							&nbsp;
						</td>
						<td align="left"  >
							&nbsp;
						</td>
						<td align="right" >
							<label id="sumCount">
								&nbsp;
							</label>
						</td>
						<td align="center"  >
							&nbsp;
						</td>
						<td align="right"  >
							&nbsp;
						</td>
						<td align="right"  >
							&nbsp;
						</td>
						<td align="right"  >
							<label id="sumAmt">
								&nbsp;
							</label>
						</td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="1" width="98%">
					<tr height="30px">
						<td align="center">
							仓管员: ____________
						</td>
						<td align="center">
							财务: ____________
						</td>
						<td align="center">
							签收人: ____________
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
						<font size="5" style="padding-top: 2px;"><b>阿玛尼出库单</b>
						</font>
					</td>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="1" width="98%">
					<tr>
						<td width="4%" align="left">
							 &nbsp;
						</td>
						<td width="30%" align="left">
							   单号:
							<label id="bill_prints"></label>
						</td>
						<td width="30%" align="left">
							制单人:
							<label id="maker_prints"></label>
						</td>
						<td width="40%">
							出库日期:
							<label id="date_prints"></label>
						</td>
					</tr>
					<tr>
						<td width="10%" align="left">
							 &nbsp;
						</td>
						<td  >
							仓库:
							<label id="house_prints"></label>
						</td>
						<td align="left">
							出库方式:
							<label id="houses_prints"></label>
						</td>
						<td align="left">
							领取人:
							<label id="user_prints"></label>
						</td>
					</tr>
					<tr>
					<td width="10%" align="left">
							 &nbsp;
						</td>
						<td align="left">
							客户名称:
							<label id="shopName_prints"></label>
						</td>
						<td width="8%">
							联系人：
							<label id="users_prints"></label>
						</td>
						<td align="left">
							电话:
							<label id="telphone_prints"></label>
						</td>
					</tr>
					<tr>
					<td width="10%" align="left">
							 &nbsp;
						</td>
						<td align="left">
							类型:
							<label id="shop_prints"></label>
						</td>
						<td align="left" colspan="2">
							门店地址:
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
								产品编号
							</td>
							<td align="center" width="30%">
								产品名称
							</td>
						<td align="center" width="10%">
							发货数量
						</td>
						<td align="center" width="6%">
							单位
						</td>
						<td align="center" width="8%">
							单价
						</td>
						<td align="center" width="6%">
							折扣
						</td>
						<td align="center" width="10%">
							金额
						</td>
					</tr>
					<tr>
						<td colspan="8" align="center">
							&nbsp;
						</td>
					</tr>
				</tbody>
				<tr height="30px">
						<td align="center" width="10%">
							总计
						</td>
						<td align="left"  >
							&nbsp;
						</td> 
						<td align="left"  >
							&nbsp;
						</td>
						<td align="right" >
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
						<td align="right"  >
							&nbsp;
						</td>
						<td align="right"  >
							<label id="sumAmtS">
								&nbsp;
							</label>
						</td>
						</tr>
			</table>
			<table cellspacing="0" cellpadding="0" width="98%">
				<tr height="30px">
					<td align="center">
						仓管员: ____________
					</td>
					<td align="center">
						财务: ____________
					</td>
					<td align="center">
						签收人: ____________
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
	   document.getElementById("maker_print").innerHTML=opener.document.getElementById("outeropationerid").value;
 	   document.getElementById("bill_print").innerHTML=opener.document.getElementById("outerbillid").value;
	   document.getElementById("house_print").innerHTML=opener.document.getElementById("outerwareid").value;
	   document.getElementById("date_print").innerHTML=opener.document.getElementById("outerdate").value+" "+opener.document.getElementById("outertime").value;
	   document.getElementById("houses_print").innerHTML=opener.document.getElementById("outertype").value;
	   document.getElementById("user_print").innerHTML=opener.document.getElementById("outerstaffname").value ;
	   document.getElementById("shopName_print").innerHTML=opener.document.getElementById("outerstaffname").value;
	   if(opener.document.getElementById("revicetype1").checked==true){
	   		document.getElementById("shop_print").innerHTML="员工";
	   }
	   if(opener.document.getElementById("revicetype2").checked==true)
	   {
	   		if(opener.document.getElementById("orderbilltype").value==2)
	   		{
	   			document.getElementById("shop_print").innerHTML="员工申请";
	   			document.getElementById("user_print").innerHTML=document.getElementById("user_print").innerHTML+"("+opener.document.getElementById("outeropationerid").value+"-"+opener.document.getElementById("outeropationername").value+")";
	   		}
	   		else
	   		{
	   			document.getElementById("shop_print").innerHTML="门店申请";
	   		}
	     	
	   	    document.getElementById("telphone_print").innerHTML=opener.companyinfos.compphone;
	      	document.getElementById("users_print").innerHTML=opener.companyinfos.compname;
	      	document.getElementById("shopAddr_print").innerHTML=opener.companyinfos.compaddress;
	   }
	  
	   /***************打印start*****************/
	   document.getElementById("maker_prints").innerHTML=opener.document.getElementById("outeropationerid").value;
 	   document.getElementById("bill_prints").innerHTML=opener.document.getElementById("outerbillid").value;
	   document.getElementById("house_prints").innerHTML=opener.document.getElementById("outerwareid").value;
	   document.getElementById("date_prints").innerHTML=opener.document.getElementById("outerdate").value+" "+opener.document.getElementById("outertime").value;
	   document.getElementById("houses_prints").innerHTML=opener.document.getElementById("outertype").value;
	   document.getElementById("user_prints").innerHTML=opener.document.getElementById("outerstaffname").value ;
	   document.getElementById("shopName_prints").innerHTML=opener.document.getElementById("outerstaffname").value;
	    if(opener.document.getElementById("revicetype1").checked==true){
	   	document.getElementById("shop_prints").innerHTML="员工";
	   	document.getElementById("telphone_prints").innerHTML="";
	    document.getElementById("shopAddr_prints").innerHTML=""; 
	   }
	   if(opener.document.getElementById("revicetype2").checked==true){
	   
	   		if(opener.document.getElementById("orderbilltype").value==2)
	   		{
	   			document.getElementById("shop_prints").innerHTML="员工申请";
	   			document.getElementById("user_prints").innerHTML=document.getElementById("user_prints").innerHTML+"("+opener.document.getElementById("outeropationerid").value+"-"+opener.document.getElementById("outeropationername").value+")";
	   		}
	   		else
	   		{
	   			document.getElementById("shop_prints").innerHTML="门店申请";
	   		}
	   		document.getElementById("telphone_prints").innerHTML=opener.companyinfos.compphone;
	  	 	document.getElementById("users_prints").innerHTML=opener.companyinfos.compname;
	   		document.getElementById("shopAddr_prints").innerHTML=opener.companyinfos.compaddress; 
	   }
	   
	   
	   clearPreviousResult_report();
	   var goodsClass = "";
	   var sumClassGtb06f=0;
	   var sumClassGtb08f=0;
	   for(var i=0;i<opener.lsDgoodsouters.length;i++)
	   { 
	   	
	   	   if(goodsClass!="" && goodsClass!=opener.lsDgoodsouters[i].goodspricetype)
	   	   {	
	   	   		addRow_print(goodsClass,"小计" ,"--",ForDight(sumClassGtb06f,2) ,"--", "--","--",ForDight(sumClassGtb08f,2),"--");
	   	   		sumClassGtb06f=0;
	   	   		sumClassGtb08f=0;
	   	   }
	   	   goodsClass=opener.lsDgoodsouters[i].goodspricetype;
	       sumClassGtb06f=sumClassGtb06f*1+opener.lsDgoodsouters[i].outercount*1;
		   sumClassGtb08f=sumClassGtb08f*1+opener.lsDgoodsouters[i].outeramt*1;
	       addRow_print(goodsClass,
	       opener.parent.loadCommonControlValue("WPTJ",0,opener.lsDgoodsouters[i].goodspricetype),
	       opener.lsDgoodsouters[i].outergoodsname,
	       opener.lsDgoodsouters[i].outercount,
	       opener.lsDgoodsouters[i].outerunit,
	       opener.lsDgoodsouters[i].outerprice,
	       opener.lsDgoodsouters[i].outerrate,
	       opener.lsDgoodsouters[i].outeramt,opener.lsDgoodsouters[i].outergoodsno);
	       sumGtb06f+=opener.lsDgoodsouters[i].outeramt*1;
	       sumGtb08f+=opener.lsDgoodsouters[i].outercount*1;
	   }
	   if(goodsClass!="")
	   {
	   		addRow_print(goodsClass,"小计" ,"--",ForDight(sumClassGtb06f,2) ,"--", "--","--",ForDight(sumClassGtb08f,2),"--");
	   }
	  
	   document.getElementById("sumCountS").innerHTML=ForDight(sumGtb08f,2);
	   document.getElementById("sumAmtS").innerHTML=maskAmt(sumGtb06f,2);
	   document.getElementById("sumCount").innerHTML=ForDight(sumGtb08f,2);
	   document.getElementById("sumAmt").innerHTML=maskAmt(sumGtb06f,2); 
	  }catch(e){alert(e.message);}
	}
	
	function addRow_print(goodsClass,goodspricetype,outergoodsname,outercount,outerunit,outerprice,outerrate,outeramt,outgoodsno)
    {
       var result = new Array(8);
       result[0]=goodspricetype;
       result[1]=outgoodsno;
       result[2]=outergoodsname;
       result[3]=outercount;
       result[4]=outerunit;
       result[5]=outerprice;
       result[6]=outerrate;
       result[7]=outeramt;
	   addRow_print_report(result,"print_goods");
	   addRow_print_report(result,"print_goodss");
	   adjustAlignCenter("print_goods",0);
	 
	   adjustAlign("print_goods",3);

	   
	   adjustAlign("print_goods",5);
	   adjustAlign("print_goods",6);
	   adjustAlign("print_goods",7);
	   

	   adjustAlignCenter("print_goodss",0);

	   adjustAlign("print_goodss",3);
	   adjustAlign("print_goodss",5);
	   adjustAlign("print_goodss",6);
	   adjustAlign("print_goodss",7);
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
	
	function printCurPage()
	{
		 	Stand_CheckPrintControl();//检查是否有打印控件
			Stand_InitPrint("总部发货_小票打印作业");
			Stand_SetPrintStyle("Alignment",2);
			Stand_SetPrintStyle("HOrient",2);
			Stand_SetPrintStyle("Bold",1);
			var printContent = document.getElementById("printContent").innerHTML;
		    Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_HTML,10,10,842,2000,printContent);
		    Stand_Print();
	}
  	 	
	</script>
