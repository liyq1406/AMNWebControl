<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String stratype=request.getParameter("atype").toString();
String stritemtype=request.getParameter("itemtype").toString();
%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新增单据</title>
     <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script> 
	<script type="text/javascript" src="<%=ContextPath %>/AdvancedOperations/AC002/addcallbillid.js"></script>
</head>
<body style=" overflow:hidden;width: 790px;" style="font-size:12px;line-height:35px;" height="750px"><center>
	  <div  title="新增单据" >
							<div id="tab" style=" overflow:hidden; border:1px solid #A3C0E8; "> 
			                        <div title="预约" style="height:714px;width: 100%;">
			                         <div id="orders"  ></div> 
						              <p>&nbsp;</p>
			                         <table border="0" height="250px" style="border:1px solid #A3C0E8;font-size:12px;"   width='100%'>
				                         						<tr>
				                         						<td valign="top" colspan="2" height="20px">
				                         						   <div id="topburrs"></div>
				                         						</td>
				                         						</tr>
				                         						<tr>
															  		<td colspan="2" valign="top"  height="20px" ><%@include file="/common/search.frag"%></td>
															  </tr>
															  <tr>
											                 	 <td height="20px" > &nbsp;预约时间</td>
															     <td><input type="text" id="ordertime" style="width: 200"/>
															     <input type="hidden" id="orderphone"/>
															     </td> 
															  </tr>
															  <tr>
											                 	 <td height="20px" > &nbsp;预约美发[300]</td>
														     	 <td>
														     	 	<input type="checkbox" id="orderprojectmf" name="orderproject" style="border:0" value="300" onclick="changeMFPrjBook(this)"/>洗剪吹&nbsp;&nbsp;&nbsp;
															     	<select id="orderusermf" name="orderusermf" style="width:200">
											                 	 		<option value="">请选择</option>
											                 	 	</select>
															     	
															    	
															      </td> 
															  </tr>
															  <tr>
															  	<td height="20px" > &nbsp;预约烫染护[600]</td>
											                 	 <td > 
											                 		<input type="checkbox" id="orderprojecttrh" name="orderproject" style="border:0" value="600" onclick="changeTRHBook(this)" />烫染护&nbsp;&nbsp;&nbsp;
											                 			<select id="orderusertrh" name="orderusertrh" style="width:200">
											                 	 		<option value="">请选择</option>
											                 	 	</select>
											                 	 </td>
															    
															  </tr>
															   <tr>
															  	<td height="20px" > &nbsp;预约烫美容[400]</td>
											                 	 <td > 
											                 		 <input type="checkbox" id="orderprojectmr" name="orderproject" style="border:0" value="400" onclick="changeMRBook(this)" />美容&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											                 			<select id="orderusermr" name="orderusermr" style="width:200">
											                 	 		<option value="">请选择</option>
											                 	 	</select>
											                 	 </td>
															    
															  </tr>
															  <tr>
											                 	 <td  height="25px">&nbsp;备注</td>
															     <td><textarea id="orderdetail" style="width:300;height:60px" onpropertychange="if(this.scrollHeight>80) this.style.posHeight=this.scrollHeight+5" ></textarea></td>
															  </tr>
															 
			                         </table>
			                         <input type="hidden" id="orderconply" name="orderconply" style="width: 200"/>
					            	</div>
						            <div title="咨询"   style="height:714px">
						             <div id="refershow"  ></div> 
						              <p>&nbsp;</p>
						                 <table border="0" height="240"  style="border:1px solid #A3C0E8;font-size:12px;" width='100%'>
										  <tr><td colspan='6' valign="top"><div id="topbur"></div></td></tr>
										  <tr>
										    			<td height="25px" width="10%">项目咨询</td>
										    			<td  colspan='5'>
										    				<input type="checkbox" name="referproject" id="referproject" onclick="checkbox();" value="[项目及项目价格]" />&nbsp;项目及项目价格
										    			</td>
										 </tr>
										  <tr>
										    			<td height="25px">
										    				门店咨询
										    			</td>
										    			<td width="110px">
										    				<input type="checkbox" name="refercomply" id="refercomply1" onclick="checkbox();"   value="[门店营业时间]" />&nbsp;门店营业时间
										    			</td>
										    			<td width="110px">
										    				<input type="checkbox" name="refercomply" id="refercomply" onclick="checkbox();"  value="[门店活动]"/>&nbsp;门店活动
										    			</td>
										    			<td width="110px">
										    				<input type="checkbox" name="refercomply" id="refercomply2" onclick="checkbox();"  value="[门店地址]"/>&nbsp;门店地址
										    			</td>
										    			<td width="110px">
										    				<input type="checkbox" name="refercomply" id="refercomply3" onclick="checkbox();"   value="[门店电话]" />&nbsp;门店电话
										    			</td>
										   </tr>
										  <tr>
										    			<td  height="25px">
										    				会员卡咨询
										    			</td>
										    			<td  >
										    				<input type="checkbox" name="refercards" id="refercards" onclick="checkbox();"   value="[卡内余额]"/>&nbsp;卡内余额
										    			</td>
										    			<td>
										    				<input type="checkbox" name="refercards" id="refercards1" onclick="checkbox();"   value="[消费记录]" />&nbsp;消费记录
										    			</td>
										    			<td>
										    				<input type="checkbox" name="refercards" id="refercards2"  onclick="checkbox();"  value="[卡遗失]" />&nbsp;卡遗失
										    			</td>
										    			<td >
										    				<input type="checkbox" name="refercards" id="refercards3"  onclick="checkbox();"  value="[会员卡折扣]" />&nbsp;会员卡折扣
										    			</td>
										    			<td> <input type="checkbox" name="refercards" id="refercards4"  onclick="checkbox();"  value="[其他]" />&nbsp;其他</td>
										 </tr>
										 <tr>
										 	<td >备注</td>
										     <td colspan='5' ><textarea id="referdetails" style="width:70%;height:100px" ></textarea>
										     </td>
										 </tr>
										 </table>
										 <p>&nbsp;</p>
										 
										 <div id="parentDiv"></div>
										 
						            </div>
						            <div title="挂失"   style="height:714px">
						             		<table border="0" height="260"  style="border:1px solid #A3C0E8;font-size:12px; " width='100%'>
						             		<tr>
						             			<td colspan="4"><div id="gua"></div></td>
						             		</tr>
						             		<tr>
							             		<td height="30px" >卡号&nbsp;<input type="text" id="cardno" style="width:140;height:21px;"/></td>
							             		<td >姓名&nbsp;<input type="text" id="membername" style="width:140;height:21px;"/></td>
							             		<td >手机&nbsp;<input type="text" id="membermphone" style="width:140;height:21px;"/></td>
							             		<td>&nbsp;</td>
						             		</tr>
						             		<tr>
						             			<td colspan="4"><div id="report"></div></td>
						             		</tr>
						             		</table>
						            </div>
					                 <div title="投诉"   style="height:714px">
					              	  <div id="peiiershow" ></div>
					              	  <p>&nbsp;</p>
						                 <table border="0" height="170"  style="border:1px solid #A3C0E8;font-size:12px; " width='100%'>
										  <tr><td colspan='6' valign="top"><div id="topburr"></div></td></tr>
										  <tr>
										    			<td  height="25px" width="10%">
										    				类别
										    			</td>
										    			<td width="15%" >
										    				<input type="checkbox" name="peiiercontent" id="peiierids"  onclick="checkbox();"   value="[技术]"/>&nbsp;技术
										    			</td>
										    			<td width="15%"  >
										    				<input type="checkbox" name="peiiercontent" id="peiieridss"   onclick="checkbox();"   value="[服务]" />&nbsp;服务
										    			</td>
										    			<td width="15%"  >
										    				<input type="checkbox" name="peiiercontent" id="peiieridses"    onclick="checkbox();"  value="[管理]" />&nbsp;管理
										    			</td width="15%" >
										    			<td><input type="checkbox" name="peiiercontent" id="peiierbeizhu"  onclick="checkbox();"  value="[其他]" />&nbsp;其他</td>
										 </tr>
										 <tr>
										 	<td >备注</td>
										     <td colspan='6'><textarea id="peiierdetails" style="width:70%;height:100px" ></textarea></td>
										 </tr>
										 </table>
					                </div>
					                <div title="退卡"  style="height:714px">
					               		  <div id="cardreturnshow"  ></div>  <p>&nbsp;</p>
						                  <table border="0" height="170"  style="border:1px solid #A3C0E8;font-size:12px; " width='100%'>
										  <tr><td colspan='6' valign="top"><div id="cardbur"></div></td></tr>
										  <tr>
										    			<td  height="25px" width="10%">
										    				类别
										    			</td>
										    			<td  >
										    				<input type="checkbox" name="cardcontent" id="card1"  onclick="checkbox();"   value="[正常退卡]"/>&nbsp;正常退卡
										    			</td>
										    			<td  >
										    				<input type="checkbox" name="cardcontent" id="card2"   onclick="checkbox();"   value="[门店调整退卡]" />&nbsp;门店调整退卡
										    			</td>
										 </tr>
										 <tr>
										 	<td >备注 </td>
										     <td colspan='5'><textarea id="carddetails" style="width:70%;height:100px" ></textarea></td>
										 </tr>
										 </table>
				             	    </div>
		       				 </div>
		       				 <input  type="hidden" id="stratype" name="stratype" value="<%=stratype %>"/> 
		       				  <input  type="hidden" id="stritemtype" name="stritemtype" value="<%=stritemtype %>"/>
						</div>
</body>
</html>
	<script language="JavaScript">
    var contextURL="<%=request.getContextPath()%>";
	</script>
	
