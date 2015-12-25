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
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>	
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC013/bc013.js"></script>

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
		.tr{background:#efefef;text-indent:4px;}
    </style>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="bc013layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  			 		<div position="left"   id="lsPanel" title="门店连锁"> 
		  			 		<div style="width: 270px; height: 650; overflow-y:auto;overflow-x:hidden;">
		  			 		 	<ul id="companyTree" style="margin-top:3px;">
		  			 		 	</ul>
		  			 		 </div>
	  			 		</div>
				        <div position="center"   id="designPanel"  style="width:100%;"> 
							<table width="100%" border="0" cellspacing="1" cellpadding="0" >
								<tr>
									<td valign="top"   >
										<form name="modeForm" method="post"  id="modeForm">
												<div id="commondetial" style="margin: 0; padding: 0;font-size:12px;">	</div>
												<div  style="width:100%; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
													<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:30px;" >
														<tr><td style="border-bottom:1px blue dashed"  colspan="4" ></td></tr>
														<tr>
															<td>&nbsp;&nbsp;<font color="red">模板性质:</font></td>
															<td colspan="3"> 
																<s:select label="模板性质" id="modetype" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:100;"
																				name="curInfoMode.id.modetype" list="#{1:\"项目模板\",2:\"产品模板\",3:\"卡种模板\"}"
																				listKey="key" theme="simple" listValue="value" disabled="true" onchange="validateModetype(this)"/>
															</td>
														</tr>
														<tr><td style="border-bottom:1px blue dashed"  colspan="4" ></td></tr>
														<tr>
															<td width="70px">&nbsp;&nbsp;<font color="red">模板编号:</font></td>
															<td><s:textfield name="curInfoMode.id.modeid" id="modeid" theme="simple"  readonly="true" style="width:100;"  validate="{required:true,minlength:5,maxlength:10,notnull:false}" onchange="validateModeId(this)"/></td>
															<td>&nbsp;&nbsp;模板名称</td>
															<td><s:textfield name="curInfoMode.modename" id="modename" theme="simple"  readonly="true" style="width:110;"  validate="{required:true,minlength:5,maxlength:10,notnull:false}" /></td>
														</tr>
														<tr><td style="border-bottom:1px blue dashed"  colspan="4" ></td></tr>
														<tr>
															<td>&nbsp;&nbsp;<font color="red">模板归属:</font></td>
															<td colspan="3"><s:textfield name="curInfoMode.modesource" id="modesource" theme="simple"  readonly="true" style="width:100;"  validate="{required:true,minlength:5,maxlength:10,notnull:false}" />
																<s:textfield name="curInfoMode.modesourceName" id="modesourceName" theme="simple"  readonly="true" style="width:150;" /></td>
														</tr>
														<tr><td style="border-bottom:1px blue dashed"  colspan="4" ></td></tr>
														<tr>
															<td>&nbsp;&nbsp;<font color="blue">父模板:</font></td>
															<td colspan="3"><s:textfield name="curInfoMode.parentmodeid" id="parentmodeid" theme="simple"  readonly="true" style="width:100;"  />
																    <s:textfield name="curInfoMode.parentmodeName" id="parentmodeName" theme="simple"  readonly="true" style="width:150;"  /></td>
														</tr>
														<tr><td style="border-bottom:1px blue dashed" colspan="4" ></td></tr>
														<tr>
															<td>&nbsp;&nbsp;建模日期:</td>
															<td><s:textfield name="curInfoMode.createdate" id="createdate" theme="simple"  readonly="true" style="width:100;"  validate="{required:true,minlength:5,maxlength:10,notnull:false}" /></td>
															<td>&nbsp;&nbsp;建模人:</td>
															<td><s:textfield name="curInfoMode.createemp" id="createemp" theme="simple"  readonly="true" style="width:110;"  validate="{required:true,minlength:5,maxlength:10,notnull:false}" /></td>
														</tr>
														<tr><td style="border-bottom:1px blue dashed" colspan="4" ></td></tr>
													</table>
								
										</div>
										</form>
									</td>
									<td valign="top"  > 
										<div id="BC013DetialTab" style="width:100%; height:95%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  "> 
										  	<div title="项目模板信息" style="font-size:12px;" style="width:100%; height:98%; ">
										  		<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
										  			<tr>
										  				<td >编号</td>
										  				<td  width="65" ><input type="text" id="searchPrjNo" name="searchPrjNo" style="width:60"/></td>
										  				<td>名称</td>
										  				<td  width="85" ><input type="text" id="searchPrjName" name="searchPrjName" style="width:80"/></td>
										  				
										  				<td>大类</td>
										  				<td  width="105"  ><input type="text" id="searchPrjType" name="searchPrjType" /></td>
										  				
										  				<td >分类</td>
										  				<td style="font-size:12px; " width="105"  ><input type="text" id="searchPrjReportType" name="searchPrjReportType" /></td>
										  				<td  ><select id="searchPrjState" name="searchPrjState" style="width:60">
										  					<option value="1">正常</option>
										  					<option value="2">禁用</option>
										  				</select></td>
										  			</tr>
										  				
										  			<tr><td style="border-bottom:1px blue dashed" colspan="9" ></td></tr>
										  			<tr>
										  					<td colspan="9"><div id="commoninfodivsecond" style="margin: 0; padding: 0;"></div></td>
										  					
										  			</tr>
										  		</table>
										  				
										  	</div>
											<div title="产品模板信息" style="font-size:12px;" style="width:100%; height:98%; ">	
													
													<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
										  			<tr>
										  				<td>编号</td>
										  				<td   ><input type="text" id="searchGoodsNo" name="searchGoodsNo" size="10"/></td>
										  				
										  				<td>大类</td>
										  				<td   ><input type="text" id="searchGoodsType" name="searchGoodsType" /></td>
										  				
										  				<td width="80">统计分类</td>
										  				<td style="font-size:12px; " >
										  				<select id="searchGoodsReportType"  name="searchGoodsReportType" style="width:140"></select></td>
										  			</tr>
										  				
										  			<tr><td style="border-bottom:1px blue dashed" colspan="6" ></td></tr>
										  			<tr>
										  					<td colspan="6"><div id="commoninfodivthirth" style="margin: 0; padding: 0;"></div></td>
										  					
										  			</tr>
										  		</table>
											</div>
											<div title="卡种模板信息" style="font-size:12px;" style="width:100%; height:98%; ">
												<table width="100%" border="1" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:25px;" >
										  			<tr>
										  				<td width="80">卡类型编号</td>
										  				<td   ><input type="text" id="factsearchCardTypeNo" name="factsearchCardTypeNo" size="10"/></td>
										  				
										  			</tr>
										  				
										  			<tr><td style="border-bottom:1px blue dashed" colspan="2" ></td></tr>
										  			<tr>
										  					<td colspan="2"><div id="commoninfodivfourth" style="margin: 0; padding: 0;"></div></td>
										  					
										  			</tr>
										  		</table>
										  			
										  	</div>
										  </div>
									</td>
								</tr>
									
						</table>
					</div>
					<div position="right"   id="designPane2"  style="width:100%;"  title="编辑模板信息" > 
						  <div  id="projectInfoDiv" style="width:100%; height:500; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
						   <form name="projectForm" method="post"  id="projectForm">
						   	<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px;" >
							   <tr><td style="border-bottom:1px blue dashed" colspan="4" ></td></tr>
							     <tr class="tr">
							    	<td colspan="2" align="center"><font size="4px" color="blue"><label id="prjShowText">项目属性</label></font></td>
							      </tr>
							      	<tr><td style="border-bottom:1px blue dashed" colspan="2" ></td></tr>
							       <tr class="tr">
							    	<td>项目编号</td>
							    	<td><s:textfield name="curProjectinfo.id.prjno" id="prjno" theme="simple"  readonly="true" style="width:140;"  maxlength="20" onchange="validateProjectId(this)"/></td>
							      </tr>
							    <tr class="tr">
							    	<td>项目名称</td>
							    	<td colspan="3"><s:textfield name="curProjectinfo.prjname" id="prjname" theme="simple"  readonly="true" style="width:140;"  maxlength="30"/></td>
							    </tr>
							     <tr class="tr">
							    	<td>项目名称缩写</td>
							    	<td colspan="3"><s:textfield name="curProjectinfo.prjabridge" id="prjabridge" theme="simple"  readonly="true" style="width:140;"  maxlength="10"/></td>
							    </tr>	
							    <tr class="tr">
							    	<td>IPAD显示名称</td>
							    	<td colspan="3"><s:textfield name="curProjectinfo.ipadname" id="ipadname" theme="simple"  readonly="true" style="width:140;"  maxlength="30"/></td>
							    </tr>	
							    <tr class="tr">
							   		<td>项目归属</td>
							    	<td><s:textfield name="curProjectinfo.id.prisource" id="prisource" theme="simple"  readonly="true" style="width:100;"  maxlength="10" /></td>
							    </tr>
							     <tr class="tr">
							   		<td>项目项别</td>
							    	<td><s:select id="prjpricetype" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curProjectinfo.prjpricetype" list="#{0:\"请选择\",1:\"大项\",2:\"小项\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
							    </tr>
							     <tr class="tr">
							   		<td>项目类别</td>
							    	<td><s:textfield name="prjtype" id="prjtype" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}" />
							    	    <s:hidden  name="curProjectinfo.prjtype" id="h_prjtype" theme="simple"/></td>
							    </tr>	
							     <tr class="tr">
							   		<td>统计分类</td>
							    	<td><select name="prjreporttype_s" id="prjreporttype_s"  disabled="true" style="width:100;"  ></select>
							    	<s:hidden  name="curProjectinfo.prjreporttype" id="h_prjreporttype" theme="simple"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>IPAD大类</td>
							    	<td><select name="ipaddl_s" id="ipaddl_s"  disabled="true" style="width:100;"  ></select>
							    	<s:hidden  name="curProjectinfo.ipddl" id="ipddl" theme="simple"/></td>
							    </tr>	
							     <tr class="tr">
							   		<td>标准价</td>
							    	<td><s:textfield name="curProjectinfo.saleprice" id="saleprice" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>疗程次数</td>
							    	<td><s:textfield name="curProjectinfo.ysalecount" id="ysalecount" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>疗程金额</td>
							    	<td><s:textfield name="curProjectinfo.ysaleprice" id="ysaleprice" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>单次价</td>
							    	<td><s:textfield name="curProjectinfo.onecountprice" id="onecountprice" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>会员价</td>
							    	<td><s:textfield name="curProjectinfo.memberprice" id="memberprice" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/>
							        <s:hidden name="curProjectinfo.salelowprice" id="salelowprice" theme="simple" /></td>
							    </tr>
							     <tr class="tr">
							   		<td>体验价</td>
							    	<td><s:textfield name="curProjectinfo.onepageprice" id="onepageprice" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>是否为疗程</td>
							   		<td><s:select id="prjsaletype" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curProjectinfo.prjsaletype" list="#{0:\"请选择\",1:\"是\",2:\"否\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
								 </tr>
							     <tr class="tr">
							   		<td>是否要洗头</td>
							   		<td><s:select id="needhairflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curProjectinfo.needhairflag" list="#{0:\"请选择\",1:\"是\",2:\"否\"}"
																				listKey="key" theme="simple" listValue="value"  disabled="true" />
									</td>
							     </tr>
							     <tr class="tr">
							   		<td>是否启用</td>
							    	<td><s:select id="useflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curProjectinfo.useflag" list="#{0:\"请选择\",1:\"是\",2:\"否\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
							    </tr>
							     <tr class="tr">
							   		<td>是否启售</td>
							    	<td><s:select id="saleflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curProjectinfo.saleflag" list="#{0:\"请选择\",1:\"是\",2:\"否\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
							    </tr>
							    <tr class="tr">
							   		<td>疗程是否打折</td>
							    	<td><s:select id="rateflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curProjectinfo.rateflag" list="#{0:\"请选择\",1:\"是\",2:\"否\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
							    </tr>
							    <tr class="tr">
							   		<td>是否定价</td>
							    	<td><s:select id="editflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curProjectinfo.editflag" list="#{0:\"请选择\",2:\"是\",1:\"否\"}"
																				listKey="key" theme="simple" listValue="value" disabled="true" />
									</td>
							    </tr>
							    <tr class="tr">
							   		<td>有无超长项</td>
							    	<td><s:select id="morelongflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curProjectinfo.morelongflag" list="#{1:\"有\",0:\"无\"}"
																				listKey="key" theme="simple" listValue="value" disabled="true" />
									</td>
							    </tr>
							      <tr class="tr">
							   		<td>业绩成本</td>
							    	<td><s:textfield name="curProjectinfo.costprice" id="costprice" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							      <tr class="tr">
							   		<td>普通业绩比率</td>
							    	<td><s:textfield name="curProjectinfo.kyjrate" id="kyjrate" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							      <tr class="tr">
							   		<td>普通工资比率</td>
							    	<td><s:textfield name="curProjectinfo.ktcrate" id="ktcrate" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							     <tr class="tr">
							   		<td>疗程业绩比率</td>
							    	<td><s:textfield name="curProjectinfo.lyjrate" id="lyjrate" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							     <tr class="tr">
							   		<td>疗程工资比率</td>
							    	<td><s:textfield name="curProjectinfo.ltcrate" id="ltcrate" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							     <tr class="tr">
							   		<td>新客固定提成</td>
							    	<td><s:textfield name="curProjectinfo.newcosttc" id="newcosttc" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							     <tr class="tr">
							   		<td>老客固定提成</td>
							    	<td><s:textfield name="curProjectinfo.oldcosttc" id="oldcosttc" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>是否需要备注</td>
							    	<td><s:select id="markflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curProjectinfo.markflag" list="#{0:\"不需要\",1:\"需要\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
							    </tr>
							    <tr>
							    <td colspan="2"> <div  id="downPrjInfo"></div> </td>
							    </tr>
						   	</table>
						   	</form>
						    </div>
						    <div  id="goodsInfoDiv" style="width:100%; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;display:none;  ">
						    	<form name="GoodsForm" method="post"  id="GoodsForm">
						   	<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px;" >
							   <tr><td style="border-bottom:1px blue dashed" colspan="4" ></td></tr>
							     <tr class="tr">
							    	<td colspan="2" align="center"><font size="4px" color="blue"><label id="goodsShowText">产品属性</label></font></td>
							      </tr>
							      	<tr><td style="border-bottom:1px blue dashed" colspan="2" ></td></tr>
							       <tr class="tr">
							    	<td><font color="red">产品编号</font></td>
							    	<td><s:textfield name="curGoodsinfo.id.goodsno" id="goodsno" theme="simple"  readonly="true" style="width:140;"  maxlength="20" onchange="validateGoodsId(this)"/></td>
							      </tr>
							    <tr class="tr">
							    	<td><font color="red">产品名称</font></td>
							    	<td colspan="3"><s:textfield name="curGoodsinfo.goodsname" id="goodsname" theme="simple"  readonly="true" style="width:140;"  maxlength="30"/></td>
							    </tr>
							    <tr class="tr">
							    	<td>条码前缀</td>
							    	<td colspan="3"><s:textfield name="curGoodsinfo.goodsuniquebar" id="goodsuniquebar" theme="simple"  readonly="true" style="width:140;"  maxlength="30"/></td>
							    </tr>		
							     <tr class="tr">
							    	<td>产品名称缩写</td>
							    	<td colspan="3"><s:textfield name="curGoodsinfo.goodsabridge" id="goodsabridge" theme="simple"  readonly="true" style="width:140;"  maxlength="30"/></td>
							    </tr>	
							      <%--<tr class="tr">
							    	<td>产品条码</td>
							    	<td colspan="3"><s:textfield name="curGoodsinfo.goodsbarno" id="goodsbarno" theme="simple"  readonly="true" style="width:140;"  maxlength="4"/></td>
							    </tr>	
							    --%><tr class="tr">
							   		<td>产品归属</td>
							    	<td><s:textfield name="curGoodsinfo.id.goodssource" id="goodssource" theme="simple"  readonly="true" style="width:100;"  maxlength="10" /></td>
							    </tr>
							    
							     <tr class="tr">
							   		<td><font color="blue">产品类别</font></td>
							    	<td><s:textfield name="goodstype" id="goodstype" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}" />
							    	    <s:hidden  name="curGoodsinfo.goodstype" id="h_goodstype" theme="simple"/></td>
							    </tr>
							    
							     <tr class="tr">
							   		<td><font color="blue">统计分类</font></td>
							    	<td>
							    	<select  name="curGoodsinfo.goodspricetype" id="h_goodspricetype" style="width:120;"></select></td>
							    </tr>	
							     <tr class="tr">
							   		<td><font color="blue">绑定供应商产品</font></td>
							    	<td><s:textfield name="curGoodsinfo.bindgoodsno" id="bindgoodsno" theme="simple"  readonly="true" style="width:100;"  maxlength="10" /></td>
							    </tr>	
							     <tr class="tr">
							   		<td><font color="blue">消耗单位</font></td>
							    	<td><select name="curGoodsinfo.costunit" id="h_costunit" style="width:100;"/></td>
							    </tr>
							     <tr class="tr">
							   		<td><font color="blue">销售单位</font></td>
							    	<td>
							    		<select name="curGoodsinfo.saleunit" id="h_saleunit" style="width:100;"/>
							    	</td>
							    </tr>
							     <tr class="tr">
							   		<td><font color="blue">进货单位</font></td>
							   		<td>
							    		<select name="curGoodsinfo.purchaseunit" id="h_purchaseunit" style="width:100;"/>
							    	</td>
							    
							    </tr>
							     <tr class="tr">
							   		<td>产品规格</td>
							    	<td><s:textfield name="curGoodsinfo.goodsformat" id="goodsformat" theme="simple"  readonly="true" style="width:100;"  maxlength="10" /></td>
							    </tr>
							    <tr class="tr">
							   		<td><font color="blue">最小订货量</font></td>
							    	<td><s:textfield name="curGoodsinfo.minordercount" id="minordercount" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td><font color="blue">最大订货量</font></td><!-- 由于加载资料问题，此字段存放在产品条码字段中 -->
							    	<td><s:textfield name="curGoodsinfo.goodsbarno" id="goodsbarno" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							     <tr class="tr">
							   		<td><font color="blue">总部进价</font></td>
							    	<td><s:textfield name="curGoodsinfo.purchaseprice" id="purchaseprice" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td><font color="blue">门店进价</font></td>
							    	<td><s:textfield name="curGoodsinfo.costamtbysale" id="costamtbysale" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>标准价格</td>
							    	<td><s:textfield name="curGoodsinfo.standprice" id="standprice" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td><font color="blue">门店售价</font></td>
							    	<td><s:textfield name="curGoodsinfo.storesalseprice" id="storesalseprice" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>产品来源</td>
							   		<td><s:select id="goodsappsource" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curGoodsinfo.goodsappsource" list="#{-1:\"请选择\",0:\"仓库\",1:\"供应商\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
								 </tr>
							    <tr class="tr">
							   		<td><font color="blue">中心仓库</font></td>
							    	<td><select name="curGoodsinfo.goodswarehouse" id="goodswarehouse" style="width:100;"  ></select></td>
							    </tr>
							     <tr class="tr">
							   		<td>供应商</td>
							    	<td><s:textfield name="curGoodsinfo.goodssupplier" id="goodssupplier" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}" /></td>
							    </tr>
							    <tr class="tr">
							   		<td>是否启用采购限制</td>
							   		<td><s:select id="enablecompany" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:100;"
																				name="curGoodsinfo.enablecompany" list="#{0:\"否\",1:\"是\"}"
																				listKey="key" theme="simple" listValue="value"/>
									</td>
								 </tr>
							     <tr class="tr">
							   		<td>允许采购门店</td>
							    	<td><s:textfield name="curGoodsinfo.goodscompany" id="goodscompany" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}" /></td>
							    </tr>
							    <tr class="tr">
							   		<td>是否启用条码校验</td>
							   		<td><s:select id="enablebarcode" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:100;"
																				name="curGoodsinfo.enablebarcode" list="#{0:\"否\",1:\"是\"}"
																				listKey="key" theme="simple" listValue="value"/>
									</td>
								 </tr>
							    <tr class="tr">
							   		<td>安全存量</td>
							    	<td><s:textfield name="curGoodsinfo.lowstock" id="lowstock" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>最高存量</td>
							    	<td><s:textfield name="curGoodsinfo.heightstock" id="heightstock" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>是否停用</td>
							   		<td><s:select id="goodsuseflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curGoodsinfo.useflag" list="#{0:\"否\",1:\"是\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
								 </tr>
							     <tr class="tr">
							   		<td><font color="blue">是否允许采购</font></td>
							   		<td><s:select id="appflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curGoodsinfo.appflag"  list="#{0:\"否\",1:\"是\"}"
																				listKey="key" theme="simple" listValue="value"  disabled="true" />
									</td>
							     </tr>
							       <tr class="tr">
							   		<td>产品用途</td>
							   		<td><s:select id="goodsusetype" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curGoodsinfo.goodsusetype"  list="#{1:\"客装\",2:\"院装\",3:\"通用\"}"
																				listKey="key" theme="simple" listValue="value"  disabled="true" />
									</td>
							     </tr>
							    <tr class="tr">
							   		<td>停购日期</td>
							    	<td><s:textfield name="curGoodsinfo.stopdate" id="stopdate" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"/></td>
							    </tr>
							      <tr class="tr">
							   		<td>停购原因</td>
							    	<td><s:textfield name="curGoodsinfo.stopmark" id="stopmark" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  /></td>
							    </tr>
							    <tr class="tr">
							    	<td>保质期</td>
							    	<td><s:textfield name="curGoodsinfo.proddate" id="proddate" theme="simple"  readonly="true" style="width:100;"/>月</td>
							    </tr>
							     
						   	</table>
						   	</form>
						    </div>
						    <div  id="cardTypeInfoDiv" style="width:100%; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;display:none;  ">
						    <form name="CardTypeForm" method="post"  id="CardTypeForm">
						   	<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px;" >
							   <tr><td style="border-bottom:1px blue dashed" colspan="4" ></td></tr>
							     <tr class="tr">
							    	<td colspan="2" align="center"><font size="4px" color="blue"><label id="cardtypeShowText">卡类型属性</label></font></td>
							      </tr>
							      	<tr><td style="border-bottom:1px blue dashed" colspan="2" ></td></tr>
							       <tr class="tr">
							    	<td width="85">卡种编号</td>
							    	<td><s:textfield name="curCardtypeinfo.id.cardtypeno" id="cardtypeno" theme="simple"  readonly="true" style="width:140;"  maxlength="20" onchange="validateCareTypeId(this)"/></td>
							      </tr>
							    <tr class="tr">
							    	<td>卡种名称</td>
							    	<td colspan="3"><s:textfield name="curCardtypeinfo.cardtypename" id="cardtypename" theme="simple"  readonly="true" style="width:140;"  maxlength="30"/></td>
							    </tr>	
							    <tr class="tr">
							   		<td>卡种归属</td>
							    	<td><s:textfield name="curCardtypeinfo.id.cardtypesource" id="cardtypesource" theme="simple"  readonly="true" style="width:100;"  maxlength="10" /></td>
							    </tr>
							    
							  
							    
							     <tr class="tr">
							   		<td>卡介质类型</td>
							    		<td><s:select id="cardchiptype" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curCardtypeinfo.cardchiptype" list="#{0:\"请选择\",1:\"磁条卡\",2:\"IC卡\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
							    </tr>	
							     <tr class="tr">
							   		<td>有效期限</td>
							    	<td><s:textfield name="curCardtypeinfo.carduselife" id="carduselife" theme="simple"  readonly="true" style="width:100;"  maxlength="10"  onchange="validatePrice(this)"/>&nbsp;&nbsp;月</td>
										
							    </tr>
							     <tr class="tr">
							   		<td>开卡额度</td>
							    	<td><s:textfield name="curCardtypeinfo.lowopenamt" id="lowopenamt" theme="simple"  readonly="true" style="width:100;"  maxlength="10"  onchange="validatePrice(this)"/>
							    	</td>
							    </tr>
							     <tr class="tr">
							   		<td>充值额度</td>
							    	<td><s:textfield name="curCardtypeinfo.lowfillamt" id="lowfillamt" theme="simple"  readonly="true" style="width:100;"  maxlength="10"  onchange="validatePrice(this)"/>
							    	</td>
							    </tr>
							     <tr class="tr">
							   		<td>售卡业绩比率</td>
							    	<td><s:textfield name="curCardtypeinfo.saleyjvalue" id="saleyjvalue" theme="simple"  readonly="true" style="width:100;"  maxlength="10" /></td>
							    </tr>
							     <tr class="tr">
							   		<td>售卡提成比率</td>
							    	<td><s:textfield name="curCardtypeinfo.saletcvalue" id="saletcvalue" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							        <tr class="tr">
							   		<td>充值业绩比率</td>
							    	<td><s:textfield name="curCardtypeinfo.fillyjvalue" id="fillyjvalue" theme="simple"  readonly="true" style="width:100;"  maxlength="10" /></td>
							    </tr>
							     <tr class="tr">
							   		<td>充值提成比率</td>
							    	<td><s:textfield name="curCardtypeinfo.filltcvalue" id="filltcvalue" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							   <tr class="tr">
							   		<td>购买疗程折扣</td>
							    	<td><s:textfield name="curCardtypeinfo.slaeproerate" id="slaeproerate" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							   <tr class="tr">
							   		<td>购买产品折扣</td>
							    	<td><s:textfield name="curCardtypeinfo.slaegoodsrate" id="slaegoodsrate" theme="simple"  readonly="true" style="width:100;"  validate="{minlength:5,maxlength:10,notnull:false}"  onchange="validatePrice(this)"/></td>
							    </tr>
							    <tr class="tr">
							   		<td>转卡规则</td>
							   		<td><s:select id="changerule" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curCardtypeinfo.changerule" list="#{0:\"余额转卡\",1:\"标准转卡\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
								 </tr>
							      <tr class="tr">
							   		<td>能否购买物品</td>
							   		<td><s:select id="salegoodsflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curCardtypeinfo.salegoodsflag" list="#{0:\"否\",1:\"是\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
								 </tr>
								 <tr class="tr">
							   		<td>能否开卡</td>
							   		<td><s:select id="openflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curCardtypeinfo.openflag" list="#{0:\"否\",1:\"是\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
								 </tr>
								 <tr class="tr">
							   		<td>能否充值</td>
							   		<td><s:select id="fillflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curCardtypeinfo.fillflag" list="#{0:\"否\",1:\"是\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
								 </tr>
							     <tr class="tr">
							   		<td>能否转卡</td>
							   		<td><s:select id="changeflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curCardtypeinfo.changeflag" list="#{0:\"否\",1:\"是\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
								 </tr>
								 <tr class="tr">
							   		<td>是否赠送卡金</td>
							   		<td><s:select id="sendamtflag" cssStyle="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;width:120;"
																				name="curCardtypeinfo.sendamtflag" list="#{0:\"否\",1:\"是\"}"
																				listKey="key" theme="simple" listValue="value"   disabled="true"/>
									</td>
								 </tr>
						   	</table>
						   	<div id="commoninfodivfive" style="margin: 0; padding: 0;"></div>
						   	</form>
						    </div>
					</div>
				      
	</div> 

	
  <div style="display:none;">

</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  	
	</script>