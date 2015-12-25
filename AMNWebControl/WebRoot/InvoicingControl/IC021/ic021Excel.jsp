<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.bean.CommonBean"/>
<jsp:directive.page import="com.amani.action.InvoicingControl.IC021Action"/>
<jsp:directive.page import="com.amani.service.InvoicingControl.IC021Service"/>
<%
	try{
	
		IC021Action action=new IC021Action();
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setBeginDate((String)request.getAttribute("beginDate"));
		action.setEndDate((String)request.getAttribute("endDate"));
		action.setProjectKind((String)request.getAttribute("projectKind"));
		action.setComName((String)request.getAttribute("comName"));
		action.setIc021Service((IC021Service)request.getAttribute("ic021Service"));
		action.setDataSet((Map<String,List<CommonBean>>)request.getAttribute("dataSet"));
		String fname="供应商结账";
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
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<body>
	
	</body>
</html>
