<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加默认信息</title>
    <link href="<%=ContextPath%>/AdvancedOperations/AC002/demo.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>


</head>
<body style="padding:6px; overflow:hidden;" style="font-size:12px;line-height:35px;"><center>
	   <!--  form  name="missions" id="missions" method="post" style="font-size:12px;line-height:35px;">-->
	     <table border="0" valign="top" width="90%" height="100%" style="font-size:12px;line-height:35px;">
                          
							<tr>
							  		 <td height="25px" >姓    名</td><td> <input type="text" id="name" width="160"/></td>
							</tr>
							<tr>
								 	<td  height="25px">手机号</td><td><input type="text" id="phone" width="160"/></td>
							</tr>
							<tr>
									<td colspan="2"  align="center"><div id="add"></div></td>
							</tr>
									</table>
		</center> 
</body>
</html>
	<script language="JavaScript">
		 var contextURL="<%=request.getContextPath()%>";
	  	 $("#add").ligerButton({text: '添加', width: 100, click: function () {adddefaultphone()}});
	  	 //添加默认号码
	    function adddefaultphone()
	    			 {
	    			   try
        				 { 
		        	  var row = parent.defaultphones.getSelectedRow();
					  			parent.defaultphones.addRow({ 
					                name: document.getElementById("name").value,
					                phone:document.getElementById("phone").value
					            }, row, false);
					var jsonParam="";
					var curjosnparam="";
					var needReplaceStr="";
					var data = parent.defaultphones.getData();
	            	jsonParam=JSON.stringify(data);
	            	if(jsonParam.indexOf("_id")>-1)
					{
					    needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
					    jsonParam=jsonParam.replace(needReplaceStr," ");
					}	
					var requestUrl ="ac001/addDefault.action";
					var params="strJsonParam=["+jsonParam+"]";
					params=params+"&name="+ document.getElementById("name").value;
					params=params+"&phone="+ document.getElementById("phone").value;
					var responseMethod="addMessage";	
				    parent.sendRequestForParams_p(requestUrl,responseMethod,params );
	      		}catch(e){alert(e.message);}
		   }
		
	    
	</script>
	
