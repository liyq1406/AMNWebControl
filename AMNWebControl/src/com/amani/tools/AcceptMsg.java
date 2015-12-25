package com.amani.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.amani.dao.AMN_Dao;
import com.amani.dao.AMN_DaoImp;
import com.amani.model.Sysparaminfo;
import com.amani.service.AMN_ReportService;
import com.amani.service.AdvancedOperations.AC002Service;


public class AcceptMsg extends TimerTask implements ServletContextAware{
	private ServletContext servletContext;
	private  AC002Service ac002Service;
	@JSON(serialize=false)
	public AC002Service getAc002Service() {
		return ac002Service;
	}
	@JSON(serialize=false)
	public void setAc002Service(AC002Service ac002Service) {
		this.ac002Service = ac002Service;
	}
	

	@Override
	public void run() {
		List calluseridList = new ArrayList();
		calluseridList.add("0010303");//员工编号
		calluseridList.add("0010215");
		try {
			SysSendMsg msg=new SysSendMsg();
			String strUserId="";
			String strPwd="";
			WebApplicationContext webContent=WebApplicationContextUtils.getWebApplicationContext(servletContext);
			AMN_DaoImp amn_DaoImp=(AMN_DaoImp) webContent.getBean("amn_Dao");
			List<Sysparaminfo> lsSysparamin=amn_DaoImp.findByHql("From Sysparaminfo where compid='001' and paramid='SP110'");
			if(lsSysparamin!=null && lsSysparamin.size()>0)
			{
				strUserId=lsSysparamin.get(0).getParamvalue();
			}
			
			lsSysparamin=amn_DaoImp.findByHql("From Sysparaminfo where compid='001' and paramid='SP111'");
			if(lsSysparamin!=null && lsSysparamin.size()>0)
			{
				strPwd=lsSysparamin.get(0).getParamvalue();
			}			
			String strContent=msg.getReceivedMsg(strUserId,strPwd);
			strContent=CommonTool.decodeUnicode(strContent);
			System.out.println(strContent+"12121212");
			String strSql="";
			if(strContent!=null && "".equals(strContent)==false)
			{
				String strArrays[]=strContent.split("\\|");
				String strMemInfo[]=null;
				if(strArrays!=null || strArrays.length>0)
				{
					for(int i=0;i<strArrays.length;i++)
					{
						strMemInfo=strArrays[i].split(",");
						if(strMemInfo!=null && strMemInfo.length>0)
						{
							strSql="insert into blacklist(id,mobilephone,acceptdate,content,operdate)" +
									" values('"+UUID.randomUUID()+"','"+strMemInfo[0]+"','"+strMemInfo[2]+"','"+strMemInfo[1]+"','"+CommonTool.getCurrDate()+"')";
							
							System.out.println(strMemInfo[1]);
							
							if("3".equals(strMemInfo[1]))
							{
								strSql+=" update memberinfo set issendmsg=1 where membermphone='"+strMemInfo[0]+"'";
								//不满意的时候
							}else if ("2".equals(strMemInfo[1])){
								String acceptdate = strMemInfo[2];
								String s ="";
								if(acceptdate.length()==14){
									//2013_11_11_09_11_55
							    	String s1 = acceptdate.substring(0,4);
							    	String s2 = acceptdate.substring(4,6);
							    	String s3 = acceptdate.substring(6,8);
							    	String s4 = acceptdate.substring(8,10);
							    	String s5 = acceptdate.substring(10,12);
							    	String s6 = acceptdate.substring(12,14);
							    	s = s1+"_"+s2+"_"+s3+"_"+s4+"_"+s5+"_"+s6;
								} 	
								int number = new Random().nextInt(2);
								String calluserid =calluseridList.get(number)+"";
								strSql +="insert callwaiting(callbillid,calluserid,callon,agentnum,offertime,callstate)" +
								" values ('','"+calluserid+"','"+strMemInfo[0]+"','短信','"+s+"',2)";
							}							
							amn_DaoImp.executeSql(strSql);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setServletContext(ServletContext arg0) {
		servletContext=arg0;
	}
	

}
