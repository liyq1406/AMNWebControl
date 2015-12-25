<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
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
		 var dispatchStaffdiv=null;
		 var menu=null;
		 var curCompId="";
		 var curStaffInid="";
		$(function ()
   		{
   			
	     	dispatchStaffdiv=$("#dispatchStaffdiv").ligerGrid({
                columns: [
                { display: '门店编号', 	name: 'compid',  width: 80 },
                { display: '门店名称', 	name: 'compname',  width: 130 },
                { display: '底薪', 		name: 'sharesalary',  width: 80 }
                ],  pageSize:10, 
                data: null,        
                width: '100%',
                height:'95%',
                enabledEdit: false,checkbox: false,rownumbers:true,usePager:false,
                onContextmenu : function (parm,e)
                {
                	 curCompId=parm.data.compid;
    				 curStaffInid=parent.curRecord.manageno;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
			
			menu = $.ligerMenu({ width: 120, items:
	            [
	            	
	            	{ text: '删除本店底薪', click: deleteCompShare, icon: 'delete' }
	            ]
	            }); 
	            
			 $("#upload").ligerButton(
			 {
			             text: '录入', width: 100,
				         click: function ()
				         {
				             uploadStaffAbsence();
				         }
			 });
            document.getElementById("oldstaffno").value=parent.curRecord.bstaffno;
			document.getElementById("oldstaffinid").value=parent.curRecord.manageno;
			initDispatchStaffdiv();
	     });
	     
	     
	    
	     
	     function initDispatchStaffdiv()
	     {
	     	if(parent.lsManagerShare!=null && parent.lsManagerShare.length>0)
	     	{
	     		dispatchStaffdiv.options.data=$.extend(true, {},{Rows: parent.lsManagerShare,Total: parent.lsManagerShare.length});
            	dispatchStaffdiv.loadData(true);
	     	}
	     	else
	     	{
	     		var row = dispatchStaffdiv.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				dispatchStaffdiv.addRow({ 
				                absencedate: ""
				            }, row, false);
	     	}
	     }
	   
	     function uploadStaffAbsence()
	     {
	     	if(document.getElementById("inserShare").value*1==0)
	     	{
	     		$.ligerDialog.error("请确认门店底薪!");
	     		return ;
	     	}
	     
	     	var params = "strCurCompId="+document.getElementById("inserCompId").value;			
    	    params =params+ "&strStaffNo="+document.getElementById("oldstaffno").value;	
    	    params =params+ "&strStaffInNo="+document.getElementById("oldstaffinid").value;	
    		params =params+ "&sharesalary="+document.getElementById("inserShare").value;
    		var requestUrl ="bc012/postManageShare.action";
   			var responseMethod="postManagerShareInfoMessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	     
	     function deleteCompShare()
	     {
	    
	     	var params = "strCurCompId="+curCompId;			
    	    params =params+ "&strStaffInNo="+curStaffInid;	
    		var requestUrl ="bc012/deleteManageShare.action";
   			var responseMethod="deleteManageShareMessage";
   			dispatchStaffdiv.deleteSelectedRow();
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
   			
	     }
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;" >
	<tr>
		<td width="100">工号:<input type="text" id="oldstaffno" name="oldstaffno" style="width:60" readonly="true"/></td>
		<td rowspan="4"><div id="dispatchStaffdiv"></div></td>
	</tr>
	<tr>
		<td width="100">门店:<input type="text" id="inserCompId" name="inserCompId" style="width:60"/></td>
		
	</tr>
	<tr><td>底薪:<input type="text" id="inserShare" name="inserShare" style="width:60" onchange="validatePrice(this)"/></td></tr>
	<tr><td width="100"><div id="upload"></div></td></tr>
	</table>
		
		<input type="hidden" name="oldstaffinid" id="oldstaffinid"/>
		
	</div>

</body>
</html>
