<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream,org.apache.commons.lang.StringUtils,org.apache.commons.lang.ObjectUtils"/>
<jsp:directive.page import="com.amani.bean.CommonBean"/>
<jsp:directive.page import="com.amani.action.SchoolControl.SCC007Action"/>
<jsp:directive.page import="com.amani.service.SchoolControl.SCC007Service"/>
<%
	try{
		SCC007Action action=new SCC007Action();
		action.setScc007Service((SCC007Service)request.getAttribute("scc007Service"));
		action.setDataSet((List<CommonBean>)request.getAttribute("dataSet"));
		boolean flag = StringUtils.isNotBlank(ObjectUtils.toString(request.getAttribute("creditNo")));
		String fname="员工培训经历"+(flag?"汇总":"明细");
		OutputStream os = response.getOutputStream();//取得输出流
		
		response.reset();//清空输出流
    	//下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");
    	fname = java.net.URLEncoder.encode(fname, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(fname.getBytes("UTF-8"), "GBK") + ".xls");
    	response.setContentType("application/msexcel");//定义输出类型
    	
    	//传入os
    	action.setOs(os);
    	if(flag){
			action.createExcel();
    	}else{
    		action.createDetailExcel();
    	}
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<body>
	
	</body>
</html>
