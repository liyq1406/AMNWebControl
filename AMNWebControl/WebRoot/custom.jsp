 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String call_no=request.getParameter("call_no");
String called_no=request.getParameter("called_no");
String agent_num=request.getParameter("agent_num");	//表示振铃坐席
String offer_time=request.getParameter("offer_time");
%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html>
<html >
<head>
     <title></title>
	<meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="msapplication-tap-highlight" content="no">
	<meta name="format-detection" content="telephone=no"> 
	<script type="text/javascript" src="<%=ContextPath%>/common/prototype.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	
</head>
<body ><center>
<table width="40%" height="40%">
    <tr>
    <td>来电号码</td>
	<td><input name="call_no"  id="call_no" value="<%=call_no %>"/></td>
	</tr>
	<tr>
    <td>被接号码</td>
	<td><input name="called_no"  id="called_no" value="<%=called_no %>"/></td>
	</tr>
	<tr>
    <td>振铃坐席</td>
	<td><input name="agent_num"  id="agent_num" value="<%=agent_num %>"/></td>
	</tr>
	<tr>
    <td>来电时间</td>
	<td><input name="offer_time"  id="offer_time" value="<%=offer_time %>"/></td>
	</tr>
	</table></center>
</body>
</html>

	<script language="JavaScript">
    	 	try{
    	 			var jsonParam="";
	    		    var params=" ";
					var params="strJsonParam="+jsonParam+"";
					 params=params+"&callno="+document.getElementById("call_no").value;
					 params=params+"&calledno="+document.getElementById("called_no").value;
					 params=params+"&agentnum="+document.getElementById("agent_num").value;
					 params=params+"&offertime="+document.getElementById("offer_time").value;
				//	 params=params+"&callstate="+document.getElementById("offer_time").value;
				     var responseMethod="callwatingMessage";
				 
				    var requestUrl ="phoneCallTool/addcallwating.action";
					sendRequestForParams(requestUrl,responseMethod,params )
					
    	 	 }catch(e){alert(e.message);}
      
         function callwatingMessage(request){

	        	 	var responsetext = eval( "("+request.responseText+")");
					var strMessage=responsetext.strMessage;
					alert(strMessage);
	        				  
	           }
	
	</script>