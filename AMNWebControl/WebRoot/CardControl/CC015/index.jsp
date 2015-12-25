<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC015/cc015.js"></script>

		<style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
  
		    body,html{height:100%;}
	    body{ padding:0px; margin:0;   overflow:hidden;}  
	    .l-link{ display:block; height:16px; line-height:16px; padding-left:10px; text-decoration:underline; color:#333;}
	    .l-link2{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
	    .l-link3{text-decoration:underline;  margin-left:2px;margin-right:2px;}
	    .l-layout-top{background:#102A49; color:White;}
	    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
		 #dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;}
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="cc015layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
		<div position="center"   id="designPanel"  style="width:100%;" title=""> 
		<div id="CC015Tab" style="width:100%; height:98%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
		 	<div title="支出登记审核" style=" ;font-size:12px;" >
				<table id="confirmTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
				<tr>
					 <td width="540"><div ><%@include file="/common/search.frag"%></div></td>	
					 <td width="80">查询日期：</td>
					 <td width="120">
							<input id="strCFromDate" type="text" size="10" />
					 </td>
					 <td width="120">
							<input id="strCToDate" type="text" size="10" />
					 </td>
					 <td >状态：</td>
					 <td>
					 	<select id="iCItemState" name="iCItemState">
					 		<option value="1">需门店经理审核</option>
					 		<option value="2">需财务专员审核</option>
					 		<option value="3">需财务经理审核</option>
					 	</select>
					 </td>
					 <td ><div id="searchCButton"></div></td>
				</tr>
				<tr><td style="border-bottom:1px #000000 dashed" colspan="7"></td></tr>
	
				</table>	
				<div id="commoninfodivdetialconfirm" style="margin:0; padding:0"></div>
			</div>
			<div title="支出登记查询" style=" ;font-size:12px;" >
				<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
				<tr>
			
					 <td width="80">查询日期：</td>
					 <td width="120">
							<input id="strFromDate" type="text" size="10" />
					 </td>
					 <td width="120">
							<input id="strToDate" type="text" size="10" />
					 </td>
					 <td >支出科目：</td>
					 <td >
							<input id="strFromItemNo" type="text" style="width:120"/>
					</td>
					<td >-</td>
					 <td >
							<input id="strToItemNo" type="text" style="width:120"/>
					 </td>
					 <td >支出状态：</td>
					 <td>
					 	<select id="iItemState" name="iItemState">
					 	    <option value="-1">所有</option>
					 		<option value="0">门店登记</option>
					 		<option value="1">门店经理审核</option>
					 		<option value="11">门店经理驳回</option>
					 		<option value="2">财务专员审核</option>
					 		<option value="22">财务专员驳回</option>
					 		<option value="3">财务经理审核</option>
					 		<option value="33">财务经理驳回</option>
					 		
					 	</select>
					 </td>
					 <td ><div id="searchButton"></div></td>
				</tr>
				<tr><td style="border-bottom:1px #000000 dashed" colspan="10"></td></tr>
	
				</table>	
				<div id="commoninfodivdetialsearch" style="margin:0; padding:0"></div>
			</div>
			
		 	<div title="支出登记录入" style=" ;font-size:12px;" >
				
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
						<tr>
							<td valign="top" width="320">
								<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
							</td>
							<td valign="top">
								<form name="packageForm" method="post"  id="packageForm">
								<div  style=" height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
									<div id="commoninfodivdetial" style="margin:0; padding:0"></div>
								</div>
								<input type="hidden" id="payoutcompid" name="curMpayoutinfo.id.payoutcompid"/>	
								<input type="hidden" id="payoutbillid" name="curMpayoutinfo.id.payoutbillid"/>
								<input type="hidden" id="billstate" name="curMpayoutinfo.billstate"/>		
								<input type="hidden" id="payoutdate" name="curMpayoutinfo.payoutdate"/>	
								<input type="hidden" id="payouttime" name="curMpayoutinfo.payouttime"/>	
								<input type="hidden" id="payoutopationerid" name="curMpayoutinfo.payoutopationerid"/>	
								<input type="hidden" id="payoutopationdate" name="curMpayoutinfo.payoutopationdate"/>	
								</form>
							</td>
						</tr>
					</table>
			</div>
			
		</div>
				        
	</div> 

	
  <div style="display:none;">

</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  	
	</script>