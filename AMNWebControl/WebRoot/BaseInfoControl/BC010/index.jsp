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
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC010/bc010.js"></script>

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

  	 <div id="bc010layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
		<div position="center"   id="designPanel"  style="width:100%;" title=""> 
			<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
				<tr>
					<td valign="top" width="320">
						<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
					</td>
					<td valign="top">
						<form name="packageForm" method="post"  id="packageForm">
						<div  style=" height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
						<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:13px;line-height:28px;" >
						<tr>
						<td><font color="blue">套餐编号</font></td><td colspan="2">
						<input type="text" name="curMpackageinfo.id.packageno" id="packageno"  readonly="true" style="width:120;" onchange="validatePackageno(this)" />
						<font color="red" >(抵用券套餐和条码卡套餐编号必须为数字)</font>
						</td>
						
						</tr>
						<tr>
						<td>套餐名称</td><td colspan="2">
						<input type="text" name="curMpackageinfo.packagename" id="packagename"  readonly="true" style="width:120;" /></td>
						
						</tr>
						<tr>
						<td>套餐金额</td><td colspan="2">
						<input type="text" name="curMpackageinfo.packageprice" id="packageprice"  readonly="true" style="width:120;" /></td>
						
						</tr>
						<tr>
						<td>使用范围</td><td colspan="2">
							<select name="curMpackageinfo.usetype" id="usetype" style="width:120;">
								<option value="0">请选择</option>
							</select>
						</td>
						</tr>
						<tr>
						<td>是否打折</td><td colspan="2">
							<select name="curMpackageinfo.ratetype" id="ratetype"  style="width:120;" >
								<option value="0"> 打折</option>
								<option value="1"> 不打折</option>
							</select>
						</td>
						</tr>
						<tr>
						<td>截止日期</td><td colspan="2">
							<input type="text" name="curMpackageinfo.usedate" id="usedate"  readonly="true" style="width:120;" />
						</td>
						</tr>
						<tr>
						<td>项目有效期</td><td colspan="2">
							<input type="text" name="curMpackageinfo.usemonths" id="usemonths"  readonly="true" style="width:60;" onchange="validatePrice(this);"/> 月
						</td>
						</tr>
						<tr>
							<td>工资</td>
							<td colspan="2"><input type="text" name="curMpackageinfo.wage" id="wage" style="width:60;" onchange="checkNum(this)"/>元</td>
						</tr>
						<tr>
							<td>是否合作项目</td>
							<td colspan="2">
								<select type="text" name="curMpackageinfo.ishz" id="ishz" style="width:60;">
									<option value="1">否</option>
									<option value="2">是</option>
								</select>
							</td>
						</tr>
						<tr>
						<td>套餐说明</td><td colspan="2">
						<input type="text" name="curMpackageinfo.paceageremark" id="paceageremark"  readonly="true" style="width:240;" /></td>
					
						</tr>
						<tr>
						<td colspan="3"><div id="commoninfodivdetial" style="margin:0; padding:0"></div></td>
						</tr>
						<tr>
						<td>下发门店</td>
						<td>
						<div ><%@include file="/common/search.frag"%></div>
						</td>
						<td>
							<div id="downByCompid"></div>
						</td>
						</tr>
						</table>
						</div>
						</form>
					</td>
				</tr>
			</table>		
		</div>
				        
	</div> 

	
  <div style="display:none;">

</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  	
	</script>