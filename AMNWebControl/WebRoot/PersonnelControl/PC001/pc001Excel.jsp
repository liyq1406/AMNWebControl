<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.model.StaffMachineinfo"/>
<jsp:directive.page import="com.amani.action.PersonnelControl.PC001Action"/>
<jsp:directive.page import="com.amani.service.PersonnelControl.PC001Service"/>
<%
String path = request.getContextPath();
%>
<%
	try{
	
		String fname="员工考勤记录统计";
		OutputStream os = response.getOutputStream();//取得输出流
		response.reset();//清空输出流

    	//下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");
    	fname = java.net.URLEncoder.encode(fname, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(fname.getBytes("UTF-8"), "GBK") + ".xls");
    	response.setContentType("application/msexcel");//定义输出类型
		PC001Action action=new PC001Action();
		action.setStrPersonid(Integer.parseInt(request.getAttribute("strPersonid").toString()));
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setStrDdate((String)request.getAttribute("strDdate"));
		action.setStrDdatelast((String)request.getAttribute("strDdatelast"));
		action.setPc001Service((PC001Service)request.getAttribute("pc001Service"));
		action.setLsStaffMachineinfo((List<StaffMachineinfo>)request.getAttribute("lsStaffMachineinfo"));
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
