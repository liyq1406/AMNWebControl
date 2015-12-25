<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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

	$(function ()
   	{
   	 try
   	 {
		var chooseAmtData = [{ choose: 1, text: '美容' }, { choose: 2, text: '美发'}, { choose: 3, text: '综合'}];
   		var commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
	                columns: [
	                { display: '门店', 	name: 'strCompId', width: 60 },
	                { display: '单号', 	name: 'strBillId', 	width: 100 },
	                { display: '卡号', 	name: 'strCardNo',  	width: 90 },
	                { display: '第一销售', 	name: 'firstsalerid',  	width: 60 },
	                { display: '卡金', 		name: 'firstsaleamt',  	width: 50 },
	                { display: '业绩', 		name: 'firstsalecashamt',  	width: 50 },
	                { display: '第二销售', 	name: 'secondsalerid',  	width: 60 },
	                { display: '卡金', 		name: 'secondsaleamt',  	width: 50 },
	                { display: '业绩', 		name: 'secondsalecashamt',  	width: 50 },
	                { display: '第三销售', 	name: 'thirdsalerid',  	width: 60 },
	                { display: '卡金', 		name: 'thirdsaleamt',  	width: 50 },
	                { display: '业绩', 		name: 'thirdsalecashamt',  	width: 50 },
	                { display: '第四销售', 	name: 'fourthsalerid',  	width: 60 },
	                { display: '卡金', 		name: 'fourthsaleamt',  	width: 50 },
	                { display: '业绩', 		name: 'fourthsalecashamt',  	width: 50 },
	                { display: '第五销售', 	name: 'fifthsalerid',  	width: 60 },
	                { display: '卡金', 		name: 'fifthsaleamt',  	width: 50 },
	                { display: '业绩', 		name: 'fifthsalecashamt',  	width: 50 },
	                { display: '第六销售', 	name: 'sixthsalerid',  	width: 60 },
	                { display: '卡金', 		name: 'sixthsaleamt',  	width: 50 },
	                { display: '业绩', 		name: 'sixthsalecashamt',  	width: 50 },
	                { display: '第七销售', 	name: 'seventhsalerid',  	width: 60 },
	                { display: '卡金', 		name: 'seventhsaleamt',  	width: 50 },
	                { display: '业绩', 		name: 'seventhsalecashamt',  	width: 50 },
	                { display: '第八销售', 	name: 'eighthsalerid',  	width: 60 },
	                { display: '卡金', 		name: 'eighthsaleamt',  	width: 50 },
	                { display: '业绩', 		name: 'eighthsalecashamt',  	width: 50 },
	                { display: '第九销售', 	name: 'ninthsalerid',  	width: 60 },
	                { display: '卡金', 		name: 'ninthsaleamt',  	width: 50 },
	                { display: '业绩', 		name: 'ninthsalecashamt',  	width: 50 },
	                { display: '第十销售', 	name: 'tenthsalerid',  	width: 60 },
	                { display: '卡金', 		name: 'tenthsaleamt',  	width: 50 },
	                { display: '业绩', 		name: 'tenthsalecashamt',  	width: 50 },
	                { display: '卡金类型', 		name: 'billinsertype',  width: 50  , 
			                editor: { type: 'select', data: chooseAmtData, valueField: 'choose' },
			                render: function (item)
			                {
			                    if (item.billinsertype == 1) return '美容';
			                    else if (item.billinsertype == 2) return '美发';
			                    else if (item.billinsertype == 3) return '综合';
			                      return '无';
			                }
	                },
	                { display: '修改日期', 		name: 'optiondate',  width: 90 },
	                { display: '修改时间', 		name: 'optiontime',  width: 70 },
	                { display: '修改人', 			name: 'optionuserno', width: 50 }
	                ],  pageSize:10, 
	                data: {Rows: parent.lsBillHistoryInfo,Total: parent.lsBillHistoryInfo.length},      
	                width: '100%',
	                height:'100%',
	                enabledEdit: false,  
	                rownumbers:false,usePager:false
           			});
            		$("#pageloading").hide();  
            		}catch(e){alert(e.message);} 	
    	});
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
	<div class="l-loading" style="display:block" id="pageloading"></div> 
   	<div id="commoninfodivthirth" style="margin:0; padding:0"></div>
</body>
</html>
