<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		function addRows()
		{
			var type=parent.document.getElementById("type").value;
			if($("#jcsdPhone").val()=="")
			{
				$.ligerDialog.error("请填写手机号码");
				return;
			}
			var amt=0;
			if(type=="1")
			{
				amt=980;
			}
			else if(type=="2")
			{
				amt=380;
			}
			else if(type=="3")
			{
				amt=100;
			}
			var bflog=1;
	    	var rows=parent.commoninfodivdetial.getCheckedRows();
	    	for (var rowid in parent.commoninfodivdetial.records)
			{
				var row=parent.commoninfodivdetial.records[rowid]; 
				if(checkNull(row['csitemno'])=="4699999")
				{
					$.ligerDialog.error("你选择的项目在明细里面已经有了,请重新选择");
					bflog=0;
					return;
				}				     		 
			}
	    	if(bflog==0)
	    	{
	    		return;
	    	}
			parent.addRecord();
			parent.document.getElementById("HDPhone").value=$("#jcsdPhone").val();
			var curNewRecord=parent.commoninfodivdetial.updateRow(parent.curRecord,{bcsinfotype:1,
					 														 csitemno: '4699999',
																			 csitemname: '合作项目体验',
																			 csitemcount: 1,
			     															 csunitprice: amt,
			     															 csdisprice: amt,
			     															 csitemamt:  amt,
			     															 csdiscount: 1,
			     															 cspaymode: $("#paycode").val(),
			     															 csproseqno:0,shareflag:0,costpricetype:0});
			
			parent.document.getElementById("wCostItemNo").readOnly="readOnly";
			parent.document.getElementById("wCostItemNo").style.background="#EDF1F8";
			parent.document.getElementById("wCostItemBarNo").readOnly="readOnly";
			parent.document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
			parent.document.getElementById("wgCostCount").readOnly="readOnly";
			parent.document.getElementById("wgCostCount").style.background="#EDF1F8";
			parent.document.getElementById("wCostFirstEmpNo").select();
			parent.document.getElementById("wCostFirstEmpNo").focus();
			parent.handPayList();
			parent.shownewPayDialogmanager.close();
		}
	</script>
		<style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
        .scr_con {position:relative;width:298px; height:98%;border:solid 1px #ddd;margin:0px auto;font-size:12px;}
        #dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;}
		#dv_scroll .Scroller-Container{width:100%;}
		#dv_scroll_bar {position:absolute;right:0;bottom:30px;width:14px;height:150px;border-left:1px solid #B5B5B5;}
		#dv_scroll_bar .Scrollbar-Track{position:absolute;left:0;top:5px;width:14px;height:150px;}
		#dv_scroll_bar .Scrollbar-Handle{position:absolute;left:-7px;bottom:10;width:13px;height:29px;overflow:hidden;background:url('<%=ContextPath%>/common/ligerui/images/srcoll.gif') no-repeat;cursor:pointer;}
		#dv_scroll_text {position:absolute;}
    </style>
</head>
<body style="padding:6px; overflow:hidden;">
	<div style="height: 30px;" id="layout1" class="l-layout" ligeruiid="layout1">
		<div style="top: 0px; height: 30px;" class="l-layout-top">
			<div class="l-layout-content" position="top">
				手机号码:<input name="jcsdPhone" id="jcsdPhone"/>
				支付方式:<select name="paycode" id="paycode">
					<option value="1">现金</option>
					<option value="6">银行卡</option>
				</select>
				<input id="Button1"class="l-button l-button" value="确认" type="button" onclick="addRows()"> 
			</div>
		</div>
	</div>
</body>
</html>
<script language="JavaScript">
	var requestURL = "<%=request.getContextPath()%>";
	</script>
