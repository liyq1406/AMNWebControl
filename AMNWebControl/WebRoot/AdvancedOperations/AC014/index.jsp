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
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/AdvancedOperations/AC014/ac014.js"></script>

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
		 
		 #fdcon{ 
 		 width:500px;
		 height:180px;
		 background:#EDF1F8; 
		 border: 1px solid #849BCA;
		 margin-top:2px;
		 margin-left:2px;
		 float:left;
		 overflow:hidden;
		 position:absolute;
		 left:550px;
		 top:65%;
		 cursor:move;
		 float:left;
		 /*filter:alpha(opacity=50);*/
		}
		
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="ac014layout" style="width:100%; margin:0 auto; margin-top:4px; ">
	  	<div position="center"   id="designPanel"  style="width:100%;"> 
	  		<table id="showTable"  border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
			
			<tr>
		
				 <td width="600"><div ><%@include file="/common/search.frag"%></div></td>
				 <td width="80">查询日期：</td>
				 <td width="130">
						<input id="strFromDate" type="text" size="10" />
				 </td>
				 <td width="130">
					<input id="strToDate" type="text" size="10" />
				 </td>
				 <td ><div id="searchButton"></div></td>
			</tr>
			<tr><td style="border-bottom:1px #000000 dashed" colspan="5"></td></tr>

			</table>	
			<div id="AC014Tab" style="width:100%; height:100%;  float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
				<div title="未处理预约" style=" ;font-size:12px;" >
					<div id="commoninfodivTradeDate" style="margin:0; padding:0"></div>	
						<div id="fdcon" style="filter:alpha(opacity=100);opacity:1;">
							<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
								<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
									<tr>
										<td>手机号码:</td><td><input type="text" name="strMemberPhone" id="strMemberPhone"  readonly="true" style="width:120;background:#EDF1F8;" /></td>
										<td rowspan="6" valign="top">
											<textarea  rows="6"  cols="30" name="orderconfirmmsg" id="orderconfirmmsg" readonly="true"></textarea>
											<input type="hidden" id="ordersid" name="ordersid"/>
											<input type="hidden" id="strCompAddress" name="strCompAddress"/>
										</td>
									</tr>
									<tr><td>确认日期:</td><td><input type="text" name="orderfactdate" id="orderfactdate" style="width:120;" /></td></tr>
									<tr><td>确认时间:</td><td><select name="orderfacttime" id="orderfacttime" style="width:120;" ></select></td></tr>
									<tr><td>服务类型:</td><td><select  name="projecttype" id="projecttype" style="width:120;"  onchange="validateProjectType(this)"></select></td></tr>
									<tr><td>服务人员:</td><td><select  name="orderfactempid" id="orderfactempid" style="width:120;" ></select></td></tr>
									<tr><td>服务项目:</td><td colspan="2"><select  name="orderfactproject" id="orderfactproject" style="width:280;" ></select></td></tr>
									<tr><td>备注:</td><td colspan="2"><input type="text"  name="orderdetail" id="orderdetail" style="width:280;" /></td></tr>
									<tr><td colspan="2" align=center><div id="confirmOrder"></div></td>
										<td align=center><div id="confirmMsg"></div></td></tr>
								</table>	
							</div>
						</div>
				</div>
				<div title="已处理预约" style=" ;font-size:12px;" >
					<div id="commoninfodivFactOrderDate" style="margin:0; padding:0"></div>	
				</div>
			</div>
		</div>
	
	</div> 

	
  <div style="display:none;">
  <!-- g data total ttt -->
</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  	
	</script>