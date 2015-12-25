<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.bean.CommonBean"/>
<jsp:directive.page import="com.amani.action.SellReportControl.SC020Action"/>
<jsp:directive.page import="com.amani.service.SellReportControl.SC020Service"/>
<%
	try{
	
		SC020Action action=new SC020Action();
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setBeginDate((String)request.getAttribute("beginDate"));
		action.setEndDate((String)request.getAttribute("endDate"));
		action.setSc020Service((SC020Service)request.getAttribute("sc020Service"));
		action.setDataSet((List<CommonBean>)request.getAttribute("dataSet"));
		String fname="客单量报表";
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
