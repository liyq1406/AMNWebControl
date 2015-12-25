<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.OutputStream"/>
<jsp:directive.page import="com.amani.action.SellReportControl.SC019Action"/>
<jsp:directive.page import="com.amani.service.SellReportControl.SC019Service"/>
<%
String path = request.getContextPath();
%>
<%
	try{
		SC019Action action=new SC019Action();
		action.setStrCurCompId((String)request.getAttribute("strCurCompId"));
		action.setStrDate((String)request.getAttribute("strDate"));
		Integer sysStatus = (Integer)request.getAttribute("sysStatus");
		action.setSysStatus(sysStatus);
		action.setSc019Service((SC019Service)request.getAttribute("sc019Service"));
		String fname=null;
		switch(sysStatus){
			case 1:
				fname="阿玛尼门店经营（月）报表";
				break;
			case 2:
				fname="阿玛尼门店美容经营（月）报表";
				break;
			case 3:
				fname="阿玛尼门店美发经营（月）报表";
				break;
			case 4:
				fname="会员分层报表";
				break;
			case 5:
				fname="会员明细报表";
				break;
			default:
				fname="门店运营报表";
		}
		OutputStream os = response.getOutputStream();//取得输出流
		response.reset();//清空输出流
    	//下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");
    	fname = java.net.URLEncoder.encode(fname, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(fname.getBytes("UTF-8"), "GBK") + ".xls");
    	response.setContentType("application/msexcel");//定义输出类型
		
		//传入os
		action.setOs(os);
    	if(sysStatus==4){
    		action.createVipExcel();
    	}else if(sysStatus==5){
    		action.detailExcel();
    	}else{
			action.createExcel();
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
