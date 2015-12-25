<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.model.Dreturngoodsinfo"/>
<jsp:directive.page import="com.amani.action.InvoicingControl.IC018Action"/>
<jsp:directive.page import="com.amani.service.InvoicingControl.IC018Service"/>
<%
String path = request.getContextPath();
%>
<%
	try{
	
		IC018Action action=new IC018Action();
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setStrFromDate((String)request.getAttribute("strFromDate"));
		action.setStrToDate((String)request.getAttribute("strToDate"));
		action.setStrFromGoodsNo((String)request.getAttribute("strFromGoodsNo"));
		action.setStrToGoodsNo((String)request.getAttribute("strToGoodsNo"));
		action.setSearchGoodsType((String)request.getAttribute("searchGoodsType"));
		action.setIc018Service((IC018Service)request.getAttribute("ic018Service"));
		action.setLsDataSet((List<Dreturngoodsinfo>)request.getAttribute("lsDataSet"));
		String fname="门店退货分析";
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
