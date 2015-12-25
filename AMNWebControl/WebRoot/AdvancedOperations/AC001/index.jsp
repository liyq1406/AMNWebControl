<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>短信</title>
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
	<script type="text/javascript" src="<%=ContextPath %>/AdvancedOperations/AC001/ac001.js"></script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div class="l-loading" style="display:block" id="pageloading" style="font-size:12px;line-height:35px;"></div> 
		<table width="100%" height="100%" border="0" style="font-size:12px;line-height:35px;">
 			 <tr>
   				 <td width="43%" valign="top" height="100%" >
    				<div style="width:99%; margin-left:7px" >
    					 <table border="0" style="width:100%;border:solid 1px #aaa;height:60%;" style="font-size:12px;line-height:25px;">
   			 				 <tr>
        						<td colspan="4" width="100%"  bgcolor="#d6e8f6" style="font-size:12px;line-height:30px;">查询条件</td>
			       			 </tr>
			    			 <tr>
                                <td  colspan="4"><div ><%@include file="/common/search.frag"%></div></td>
					         </tr>
					         <tr>
						         <td align="left" height="38px" >会员卡类型</td>
						         <td colspan="3"><input type="text" id="cardClass"  /></td>
						    	
					        </tr>
					      	<tr>
						        <td align="left">卡内疗程</td>
						        <td colspan="3"><input type="text" id="cardtreatment" style="width: 450"/></td>
					        </tr>
					        <tr>
						        <td align="left">会员活跃期</td>
						        <td colspan="3">
						        <select id="dataactivity" name="dataactivity" style="width: 150" >
						        <option value="0"></option>
						        <option value="1" >三个月 </option>
						        <option value="2" >六个月</option>
						        <option value="3">九个月</option>
						        <option value="4" >一年</option>
						         </select></td>
						        
					        </tr>
					       
					        <tr>
						        <td align="left" height="38px">卡余额</td>
						        <td ><input type="text" id="cardmonney"  style="width: 150"/></td>
						        <td><input type="text" id="cardmonneys" style="width: 150"/></td>
						        <td width="180">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						  
					        </tr>
					        <tr >
       							<td >会员生日</td>
								<td> <input type="text" id="birthday" /></td>
								<td><input type="text" id="birthdays" /></td>
								<td><div id="sdetail" ></div></td>
								
        					</tr>
        					 <tr>  
						       <td colspan="4" id="data" ><div  id="revokedetails"  style="margin:0; padding:0;height:230px" style="font-size:12px;line-height:35px;"> </div></td>
						    </tr>
   					 </table>
     			 </div>
   			  </td>
    		  <td width="50%" height="100%" valign="top"  align="center">
   				 <table height="96%" width="98%" border="0" style="font-size:12px;line-height:35px;">
				    <tr>
				  	  <td valign="top" width="100%" colspan="3"> <div style="font-size:12px;line-height:35px;" id="selectdetails" style="margin:0; padding:0;"></div></td>
				    </tr>
				    <tr>
					    <td width="40%"><div id="defaultphones" ></div></td>
					    <td colspan="2"  rowspan="2"  valign="top" width="100%" >
						                <div  id="topmenu"></div>
						    <textarea name=""  style="width:100%;height:200px" id="smgText" onkeyup="checkLength(this);" ></textarea> 
						     <div style="font-size:12px;line-height:25px"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						              <span style="color:#666;">发送字数：</span><span id="writecount" style="color:Red;">0</span> 
						              <span style="color:#666;">剩余字数：</span><span id="sy" style="color:Red;">300</span>
						              <span style="color:#666;">短信条数：</span><span id="infocount" style="color:Red;">0</span></div>
					    <center> <div id="btn1"  ></div>&nbsp;<div id="btn2" ></div></center></td>
 					</tr>
   				 </table>
    		</td> 
 		 </tr>
 		 
	</table>
		<div id="excelscroll" style="display:none">
  		  <table id="loadexcel" width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;" >
  		  <tr>
  		  <td>电话号码</td>
  		  <td>短信内容</td>
  		  <td>回复时间</td>
  		  </tr>
  		  <tbody id="exceldate">
  		  </tbody>
  		  </table>
  	
  		</div>
</body>
</html>
	<script language="JavaScript">
	  	 var contextURL="<%=request.getContextPath()%>";
	</script>