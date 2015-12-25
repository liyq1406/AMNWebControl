<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>	
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/mindsearchitem.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/pinyin.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/BaseInfoControl/BC018/bc018.js"></script>

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
    
    <script language="vbscript">
			function toAsc(str)
			toAsc = hex(asc(str))
			end function
	</script>
</head>
<body >
<div class="l-loading" style="display:block" id="pageloading"></div> 

  	 <div id="bc018layout" style="width:100%; margin:0 auto; margin-top:4px; "> 
	  	<div position="center"   id="designPanel"  style="width:100%;" title=""> 
			<table style="font-size:12px;line-height:25px;" >
				<tr>
					<td valign="top">
						<div id="commoninfodivmaster" style="margin:0; padding:0"></div>
					</td>
					<td valign="top">
						<div id="commoninfodivproject" style="margin:0; padding:0"></div>
					</td>
					<td valign="top">
						<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:20px;" >
						<tr>	
								<td >
								&nbsp;&nbsp;<font color="red">消耗产品</font>
											&nbsp;&nbsp;
											<input type="text"name="winsergoodsno" id="winsergoodsno"    style="width:120;" onchange="validateinserno(this);"  onfocus="itemsearchbegin(this,2)"/>
											&nbsp;&nbsp;名称
											<input type="text"name="winsergoodsname" id="winsergoodsname"   readonly="true" style="width:160;" />
											&nbsp;&nbsp;单位
											<input type="text"name="winsergoodsunit" id="winsergoodsunit"   readonly="true" style="width:60;" />
											&nbsp;&nbsp;<font color="red">数量</font>
											<input type="text"name="winsergoodscount" id="winsergoodscount"   style="width:60;" onchange="validatePrice(this)"/>
											<input type="hidden" name="winsergoodstype" id="winsergoodstype" />
								</td>
								<td><div id="postCosInfo"></div></td>
						</tr>
						<tr >
								<td colspan="2" color="#0000cc">
												<hr width="100%" />
								</td>
						</tr>
						<tr><td colspan="2" valign="top"><div id="commoninfodivdetial" style="margin:0; padding:0"></div></td> </tr>
						</table>
					</td>
				</tr>
			</table>		
		</div>
				        
	</div> 

	
  <div style="display:none;">

</div>
 
</body>
</html>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
  		document.write("<div id=\"keysList\" style=\"z-index:2;width:250px;position:absolute;display:none;background:#FFFFFF;border: 2px solid #a4a6a1;font-size:13px;cursor: default;\" onblur> </div>");
        document.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
	</script>