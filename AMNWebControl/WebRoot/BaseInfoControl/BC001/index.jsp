<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script> 	
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/standprint.js"></script>
	<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC001/bc001.js"></script>

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
<body onLoad="initMap()">
	<div class="l-loading" style="display:block;height:100%;" id="pageloading"></div> 
	 <div id="bc001layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
		<div position="left"   id="lsPanel" title="连锁结构"> 
		  	<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			<ul id="companyTree" style="margin-top:3px;">
		  			</ul>
		  	</div>
	  	</div>
	  	<div position="center"   id="designPanel"  style="width:100%;" > 
  		<table width="100%" border="0" cellspacing="1" cellpadding="0" align="left" style="height:100%;">
  		<tr>
  		<td valign="top" >
  				   <div id="BC001Tab" style="width:98%;margin:10px; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
				     	<div title="门店信息" style="height:820px ;font-size:12px;" >
				     		<form name="curCompanyFrom" method="post"  id="curCompanyFrom">
				     		  <table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:26px;" >
								<tr>
								<td width="70">&nbsp;&nbsp;门店编号</td>
								<td><s:textfield name="curCompanyinfo.compno" id="compno" theme="simple"  readonly="true" style="width:120;"/></td>
								<td width="70">&nbsp;&nbsp;<font color="blue">门店名称</font></td>
								<td><s:textfield name="curCompanyinfo.compname" id="compname" theme="simple" style="width:120"  validate="{required:true,minlength:3,maxlength:30,notnull:false}"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;<font color="blue">门店状态</font></td>
									<td>  <input type="text" id="compstate" name="curCompanyinfo.compstate" /> </td>
								
								<td>&nbsp;&nbsp;<font color="blue">门店模式</font> </td>
									<td> <input type="text" id="compmode" name="curCompanyinfo.compmode" /></td>
								</tr>
								<tr>
								<td>&nbsp;&nbsp;店责任人</td>
									<td><s:textfield name="curCompanyinfo.compresponsible" id="compresponsible" theme="simple" style="width:120" validate="{required:true,maxlength:15,notnull:false}"/></td>
									
								<td>&nbsp;&nbsp;门店电话</td>
								<td><s:textfield name="curCompanyinfo.compphone" id="compphone" theme="simple" size="20" style="width:120" validate="{maxlength:12,notnull:false}"/></td>
								</tr>
							
								<tr>
									<td>&nbsp;&nbsp;门店传真</td>
									<td><s:textfield name="curCompanyinfo.compfex" id="compfex" theme="simple" style="width:120" validate="{maxlength:15,notnull:false}"/></td>
									<td>&nbsp;&nbsp;门店IP地址</td>
									<td><s:textfield name="curCompanyinfo.compipaddress" id="compipaddress" theme="simple" style="width:120" validate="{maxlength:15,notnull:false}"/>
									&nbsp;<s:textfield name="curCompanyinfo.compipaddressex" id="compipaddressex" theme="simple" style="width:120" validate="{maxlength:15,notnull:false}"/>
									<s:hidden name="curCompanyinfo.compzipcode" id="compzipcode" /></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;ADSL账号</td>
									<td><s:textfield name="curCompanyinfo.compadslno" id="compadslno" theme="simple" style="width:120" validate="{maxlength:15,notnull:false}"/></td>
									<td>&nbsp;&nbsp;ADSL密码</td>
									<td><s:password name="curCompanyinfo.compadslpassword" id="compadslpassword" theme="simple" style="width:120" validate="{maxlength:15,notnull:false}"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;营业执照</td>
									<td ><s:textfield name="curCompanyinfo.comptradelicense" id="comptradelicense" theme="simple" style="width:120" validate="{maxlength:40,notnull:false}"/></td>
									<td>&nbsp;&nbsp;创建日期</td>
									<td ><s:textfield name="curCompanyinfo.createdate" id="createdate" theme="simple" style="width:120" readonly="true"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;门店镜台</td>
									<td><s:textfield name="curCompanyinfo.mirrornumber" id="mirrornumber" theme="simple" style="width:50px" ligerui="{type:'int'}" validate="{maxlength:15,notnull:false}"/>&nbsp;&nbsp;<font size="1" >(台)</font></td>
									<td>&nbsp;&nbsp;IPAD密码</td>
									<td><s:password name="curCompanyinfo.ipadpwd" id="ipadpwd" theme="simple" style="width:120px" validate="{maxlength:100,notnull:false}"/>&nbsp;&nbsp;<font size="1" ></font></td>
								</tr>
								<tr style="display:none">
									<td>&nbsp;&nbsp;美容提成模式</td>
									<td>
										<select name="curCompanyinfo.model" id="model">
											<option value="0">无</option>
											<option value="1">美容薪资提成</option>
										</select>
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr style="display: none;">
									<td>&nbsp;&nbsp;门店面积</td>
									<td><s:textfield name="curCompanyinfo.comparea" id="comparea" theme="simple" style="width:50px" ligerui="{type:'int'}" validate="{maxlength:15,notnull:false}"/>&nbsp;&nbsp;<font size="1" >(平)</font></td>
									<td>&nbsp;&nbsp;门店租金 </td>
									<td><s:textfield name="curCompanyinfo.comprent" id="comprent" theme="simple"  style="width:50px" ligerui="{type:'int'}" validate="{maxlength:6,notnull:false}"/>&nbsp;&nbsp;<font size="1" >(元)</font></td>
								</tr>
								<tr style="display: none;">
									<td colspan="4">&nbsp;<%@include file="/common/city.frag" %></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;门店地址</td>
									<td ><s:textfield name="curCompanyinfo.compaddress" id="compaddress" theme="simple" style="width:240" validate="{maxlength:30,notnull:false}"/>
									</td>
									<td><div id="getAddress"></div> </td><td>
									<s:textfield name="curCompanyinfo.xcoordinate" id="xcoordinate" theme="simple" style="width:100" validate="{maxlength:30,notnull:false}"/>
									<s:textfield name="curCompanyinfo.ycoordinate" id="ycoordinate" theme="simple" style="width:100" validate="{maxlength:30,notnull:false}"/>
									</td>
								</tr>
								<tr>
									<td ><div id="editCurRecord"></div>  
									</td>
										<td>
									 <div id="editCurRootInfo"></div></td>
								</tr>
								<tr>
								<td>&nbsp;&nbsp;经理卡密码</td>
								<td ><s:password name="curCompanyinfo.mangerPassword" id="mangerPassword" theme="simple" style="width:140" readonly="true"/></td>
								<td colspan="2"><div id="createMangerPass"></div></td>
								</tr> 
	
							    <tr>
							    	<td>&nbsp;&nbsp;开店流程1:</td>
							    	<td><s:textfield name="curCompanyinfo.shopwf1" id="shopwf1" theme="simple" style="width:240" validate="{maxlength:100,notnull:false}"/></td>
							    	<td>&nbsp;&nbsp;开店流程2:</td>
							    	<td><s:textfield name="curCompanyinfo.shopwf2" id="shopwf2" theme="simple" style="width:240" validate="{maxlength:100,notnull:false}"/></td>
							    </tr>
							    <tr>
							    	<td>&nbsp;&nbsp;开店流程3:</td>
							    	<td><s:textfield name="curCompanyinfo.shopwf3" id="shopwf3" theme="simple" style="width:240" validate="{maxlength:100,notnull:false}"/></td>
							    	<td>&nbsp;&nbsp;开店流程4:</td>
							    	<td><s:textfield name="curCompanyinfo.shopwf4" id="shopwf4" theme="simple" style="width:240" validate="{maxlength:100,notnull:false}"/></td>
							    </tr>
							    <tr>
							    	<td>&nbsp;&nbsp;开店流程5:</td>
							    	<td><s:textfield name="curCompanyinfo.shopwf5" id="shopwf5" theme="simple" style="width:240" validate="{maxlength:100,notnull:false}"/></td>
							    	<td>&nbsp;&nbsp;开店流程6:</td>
							    	<td><s:textfield name="curCompanyinfo.shopwf6" id="shopwf6" theme="simple" style="width:240" validate="{maxlength:100,notnull:false}"/></td>
							    </tr>
							    <tr>
							    	<td>&nbsp;&nbsp;开店流程7:</td>
							    	<td><s:textfield name="curCompanyinfo.shopwf7" id="shopwf7" theme="simple" style="width:240" validate="{maxlength:100,notnull:false}"/></td>
							    	<td>&nbsp;&nbsp;开店流程8:</td>
							    	<td><s:textfield name="curCompanyinfo.shopwf8" id="shopwf8" theme="simple" style="width:240" validate="{maxlength:100,notnull:false}"/></td>
							    </tr>
							     <tr>
							    	<td>&nbsp;&nbsp;开店流程9:</td>
							    	<td><s:textfield name="curCompanyinfo.shopwf9" id="shopwf9" theme="simple" style="width:240" validate="{maxlength:100,notnull:false}"/></td>
							    	<td>&nbsp;&nbsp;开店流程10:</td>
							    	<td><s:textfield name="curCompanyinfo.shopwf10" id="shopwf10" theme="simple" style="width:240" validate="{maxlength:100,notnull:false}"/></td>
							    </tr>
							    <tr></tr>
							    <tr>
							    	<td>
							    		<div id="uploadStaffImg"></div>
							    		<input id="imgUrl" name="curCompanyinfo.imgUrl" value=""/>
								    </td>	
							    </tr>
								</table>
								<s:hidden name="curCompanyinfo.region" id="region"></s:hidden>
								</form>
				       	</div>
				       	<div title="仓库信息" style="height:220px ;font-size:12px;">
				       		 <div style="width:600px; height:200px; margin:10px; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<div id="commoninfodivWareHouse" style="margin:0; padding:0"></div>
   			  				 </div>   			   
				       	</div>
				   		<div title="班次信息" style="height:220px ;font-size:12px;">
				       		 <div style="width:600px; height:200px; margin:10px; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<div id="commoninfodivScheduling" style="margin:0; padding:0"></div>
   			  				 </div>   			   
				       	</div>
				       	<div title="房间信息" style="height:220px ;font-size:12px;">
				       		 <div style="width:600px; height:200px; margin:10px; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
								<div id="roomDetail" style="margin:0; padding:0"></div>
   			  				 </div>   			   
				       	</div>
	   		    </div> <input id="res" style="display:none;"> </input>
  			   <!--  <div style="width:100%; height:400px; margin:10px; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
				 <div style="width: 100%; height: 100%; border: 1px solid gray;" id="container">
   			   </div>
   		-->
  		</td>
  	</tr>
  	</table>
  </div>
  </div>
  <div style="display:none;">
  <!-- g data total ttt -->
</div>

</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  	   function uploadStaffImg(){
       	showModeDialog = $.ligerDialog.open({ height: 400, url: contextURL+'/BaseInfoControl/BC001/showUploadImg.jsp', 
       		width: 600, showMax: false, showToggle: false,allowClose:true, showMin: false, isResize: false,
       		buttons: [{ text: '关闭', onclick: function (item, dialog) {dialog.close(); } } ] });
       }
	</script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?&v=1.2"></script>