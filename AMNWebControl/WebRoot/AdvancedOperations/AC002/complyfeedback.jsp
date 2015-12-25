<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>门店确认信息</title>
     <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script> 
</head>
<body style="padding:6px; overflow:hidden;" style="font-size:12px;line-height:35px;"><center>
	    <table border="0"  style="border:1px solid #A3C0E8;font-size:12px;margin:7" width='88%'>
	   				 <tr>
							<td colspan="2" style="font-size:15px"><font color="red">客户预约信息</font></td>
					</tr>
					<tr>
							<td width="7%">预约门店</td>
							<td><input type="text" id="orderconplys" style="width:200;"/></td>
					</tr>
					<tr>
							<td >预约时间</td>
							<td><input type="text" id="ordertimes"  style="width: 200"/></td> 
					 </tr>
					 <tr>
							<td >预约项目</td>
					        <td><input type="text" id="orderproject" style="width: 200"/></td> 
					</tr>
					<tr>
							 <td >预约人</td>
					         <td><input type="text" id="orderuser" style="width: 200"/></td> 
					 </tr>
					 <tr>
							<td valign="top" >备注</td>
							<td width="380px"><textarea id="orderdetail" style="width:300;height:120px" ></textarea></td>
					 </tr>
				</table>
				 <table border="0"  style="border:1px solid #A3C0E8;font-size:12px;margin:7" width='88%'>
	   				 <tr>
							<td colspan="2" style="font-size:15px"><font color="red">门店确认信息（友情提示:如果与上述条件冲突，请写好备注。给出三个可以的时间）</font></td>
					</tr>
					 <tr>
							<td valign="top" width="5%">备注</td>
							<td width="380px"><textarea id="complydetails" style="width:300;height:120px" ></textarea></td>
					 </tr>
					 <tr>
							 <td ></td>
						<td ><input type="submit" value="确认" onclick="uporders();" class="l-button l-button-submit" />
						</td>
					</tr>
				</table>
				
		</center> 
</body>
</html>
	<script language="JavaScript">
		 var contextURL="<%=request.getContextPath()%>";
		 document.getElementById("orderconplys").value=parent.document.getElementById("orderconply").value;
		 document.getElementById("ordertimes").value=parent.document.getElementById("ordertime").value;
		  //确认单据
	     function uporders(){
				 try
		         {
				     var    requestUrl ="ac002/upordersd.action";
				     var    params="complydetails="+document.getElementById("complydetails").value;
				 	   		opener.sendRequestForParams(requestUrl,'',params );
				 	   		alert("门店信息保存成功！")
	      		}catch(e){alert(e.message);}
	    }
	</script>
	
