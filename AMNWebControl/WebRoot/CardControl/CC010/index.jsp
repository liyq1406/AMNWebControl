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
	<script type="text/javascript" src="<%=ContextPath%>/CardControl/CC010/cc010.js"></script>

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

  	 <div id="cc010layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  	<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
		<div position="center"   id="designPanel"  style="width:100%;" title=""> 
			<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;" >
				<tr>
					<td valign="top" width="500">
						<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;">
							<tr>
										<td >
										&nbsp;挂失卡号：<input id="searchLossCardNo" type="text" size="12"/> &nbsp;
										&nbsp;补卡卡号：<input id="searchRecevieCardNo" type="text" size="12"/> &nbsp;
										</td>
										<td ><div id="searchBill"></div></td>	
							</tr>
						</table>
						<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
					</td>
					<td valign="top" height="400">
						<div  style=" height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
						<form name="detailForm" method="post"  id="detailForm">
						
						<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:13px;line-height:28px;" >
						<tr>
							<td width="80">
								<font color="blue">挂失门店</font>
							</td>
							<td>
								&nbsp;<input type="text" name="curMcardchangeinfo.id.changecompid" id="changecompid"  readonly="true" style="width:120;" onchange="validatePackageno(this)" />
							</td>
					
							<td width="80">
								<font color="blue">挂失单号</font>
							</td>
							<td>
								<input type="text" name="curMcardchangeinfo.id.changebillid" id="changebillid"  readonly="true" style="width:120;" />
							</td>
							<td rowspan="9" width="300" valign="middle">
								<div id="changeoutCard"></div>
								<br/>
								<div id="changeinCard"></div>
								<br/>
								<div id="entryReceviceCard"></div>
								
								
							</td>
							
						</tr>
						<tr>
							<td>
								挂失日期
							</td>
							<td>
								&nbsp;<input type="text" name="curMcardchangeinfo.changedate" id="changedate"  readonly="true" style="width:120;" />
							</td>
							<td>
								挂失时间
							</td>
							<td><input type="text" name="curMcardchangeinfo.changetime" id="changetime"  readonly="true" style="width:120;" />
							</td>
						</tr>
						<tr>
							<td>
								<font color="blue">挂失卡号</font>
							</td>
							<td>
								&nbsp;<input type="text" name="curMcardchangeinfo.changebeforcardno" id="changebeforcardno"  readonly="true" style="width:120;"  />
							</td>
					
							<td>
								卡类型
							</td>
							<td >
								<input type="text" name="curMcardchangeinfo.changebeforcardtype" id="changebeforcardtype"  readonly="true" style="width:120;" />
							</td>
						</tr>
						<tr>
							<td>
								会员姓名
							</td>
							<td>
								&nbsp;<input type="text" name="curMcardchangeinfo.membername" id="membername"  readonly="true" style="width:120;"  />
							</td>
					
							<td>
								手机号码
							</td>
							<td >
								<input type="text" name="showmemberphone" id="showmemberphone"  readonly="true" style="width:120;" />
								<input type="hidden" name="curMcardchangeinfo.memberphone" id="memberphone"  />
							</td>
						</tr>
						<tr>
							<td>
								储值余额
							</td>
							<td>
								&nbsp;<input type="text" name="curMcardchangeinfo.curaccountkeepamt" id="curaccountkeepamt"  readonly="true" style="width:120;"  />
							</td>
					
							<td>
								储值欠款
							</td>
							<td >
								<input type="text" name="curMcardchangeinfo.curaccountdebtamt" id="curaccountdebtamt"  readonly="true" style="width:120;" />
							</td>
						</tr>
						<tr>
							<td>
								疗程余额
							</td>
							<td>
								&nbsp;<input type="text" name="curMcardchangeinfo.curproaccountkeepamt" id="curproaccountkeepamt"  readonly="true" style="width:120;" />
							</td>
					
							<td>
								疗程欠款
							</td>
							<td >
								<input type="text" name="curMcardchangeinfo.curproaccountdebtamt" id="curproaccountdebtamt"  readonly="true" style="width:120;" />
							</td>
						</tr>
						
						<tr>
							<td>
								<font color="blue">补卡卡号</font>
							</td>
							<td>
								&nbsp;<input type="text" name="curMcardchangeinfo.changeaftercardno" id="changeaftercardno"  readonly="true" style="width:120;" onchange="validateChangeaftercardno(this)" />
							</td>
					
							<td>
								补卡类型
							</td>
							<td >
								<input type="text" name="curMcardchangeinfo.changeaftercardtype" id="changeaftercardtype"  readonly="true" style="width:120;" />
							</td>
						</tr>
						<tr>
							<td>
								补卡备注
							</td>
							<td colspan="3">
								&nbsp;<input type="text" name="curMcardchangeinfo.rechargeremark" id="rechargeremark"  readonly="true" style="width:320;" />
							</td>
					
						</tr>
						<tr><td><font color="red">单据状态</font></td>
									<td  colspan="3">
									<input type="radio" id="billflag0" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="billflag" value="0"   disabled="true"/>登记挂失
									<input type="radio" id="billflag1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="billflag" value="1"   disabled="true"/>已挂失
									<input type="radio" id="billflag2" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="billflag" value="2"   disabled="true"/>已解挂
									<input type="radio" id="billflag3" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="billflag" value="3"   disabled="true"/>登记补卡
									<input type="radio" id="billflag4" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="billflag" value="4"   disabled="true"/>已补卡
									<input type="radio" id="billflag5" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="billflag" value="5"   disabled="true"/>已驳回补卡
									</td>
						</tr>
						</table>
						<input type="hidden" name="curMcardchangeinfo.id.changetype" id="changetype"   />
						</form>
						</div>
						
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