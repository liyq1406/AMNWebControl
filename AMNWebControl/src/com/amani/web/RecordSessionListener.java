package com.amani.web;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.amani.bean.SessionAndUser;
import com.amani.tools.CommonTool;
public class RecordSessionListener implements HttpSessionAttributeListener,    HttpSessionListener 
{
		private static List<SessionAndUser> sessions;
		public static String	loginFlag = "userInfo";
		static
		{
			if (sessions == null) 
			{      
				sessions = Collections.synchronizedList(new ArrayList<SessionAndUser>());
			}  
		}
		public  void attributeAdded(HttpSessionBindingEvent e)
		{    
			HttpSession session = e.getSession();
			String attrName = e.getName();
			// 登录    
			if (attrName.equals(loginFlag) ) 
			{      
				// 遍历所有session      
				for (int i = sessions.size()-1; i >= 0; i--) {        
					SessionAndUser tem = sessions.get(i);
					if (tem.getUserId().equals(CommonTool.getLoginUserId())) {          
						tem.getSession().invalidate();
						//自动调用remove          
						break;        
						}      
					}      
				SessionAndUser sau = new SessionAndUser();      
				sau.setUserId(CommonTool.getLoginUserId());      
				sau.setSession(session);      
				sau.setSessionId(session.getId());      
				sessions.add(sau);    
			}  
		}
		public void attributeRemoved(HttpSessionBindingEvent e) {    
			HttpSession session = e.getSession();    
			String attrName = e.getName();
			// 登录    
			if (attrName.equals(loginFlag)) {      
				// 遍历所有session      
				for (int i = sessions.size()-1; i >= 0; i--) {        
					SessionAndUser tem = sessions.get(i);
					if (tem.getUserId().equals(CommonTool.getLoginUserId())) {                   
						sessions.remove(i);break;        
						}      
					}       
				}      
		}
		public  void attributeReplaced(HttpSessionBindingEvent e) 
		{    
			HttpSession session = e.getSession();    
			String attrName = e.getName();int delS=-1;
			// 登录    
			if (attrName.equals(loginFlag)) 
			{
				//当前session中的user
				// 遍历所有session      
				for (int i = sessions.size()-1; i >= 0; i--) 
				{        
					SessionAndUser tem = sessions.get(i);
					if (tem.getUserId().equals(CommonTool.getLoginUserId())&&!tem.getSessionId().equals(session.getId())) {          
						System.out.println("Remove:invalidate 1!");          
						delS=i;        
					}
					else if(tem.getSessionId().equals(session.getId())){          
						tem.setUserId(CommonTool.getLoginUserId());        
					}      
				}
				if (delS!=-1) {        
					sessions.get(delS).getSession().invalidate();
					//失效时自动调用了remove方法。也就会把它从sessions中移除了     
				}         
			}      
		}
		
		public void sessionCreated(HttpSessionEvent e) {    }
		public void sessionDestroyed(HttpSessionEvent e) {   }
		
	}
		

