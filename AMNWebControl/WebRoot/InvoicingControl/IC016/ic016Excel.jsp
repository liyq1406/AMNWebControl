<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.bean.GoodsOuterAnalysisBean"/>
<jsp:directive.page import="com.amani.action.InvoicingControl.IC016Action"/>
<jsp:directive.page import="com.amani.service.InvoicingControl.IC016Service"/>
<%
String path = request.getContextPath();
%>
<%
	try{
	
		IC016Action action=new IC016Action();
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setStrFromDate((String)request.getAttribute("strFromDate"));
		action.setStrToDate((String)request.getAttribute("strToDate"));
		action.setStrFromGoodsNo((String)request.getAttribute("strFromGoodsNo"));
		action.setStrToGoodsNo((String)request.getAttribute("strToGoodsNo"));
		action.setSearchGoodsType((String)request.getAttribute("searchGoodsType"));
		action.setIc016Service((IC016Service)request.getAttribute("ic016Service"));
		action.setLsDataSet((List<GoodsOuterAnalysisBean>)request.getAttribute("lsDataSet"));
		String fname="出库明细表";
		OutputStream os = response.getOutputStream();//取得输出流
		response.reset();//清空输出流
    	//下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");
    	fname = java.net.URLEncoder.encode(fname, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(fname.getBytes("UTF-8"), "GBK") + ".xls");
    	response.setContentType("application/msexcel");//定义输出类型
		
		//传入os
		action.setOs(os);
		action.createExcel();
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<body>
	
	</body>
</html>
