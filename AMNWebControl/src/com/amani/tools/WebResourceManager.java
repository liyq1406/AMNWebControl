package com.amani.tools;

import java.util.List;
import java.util.Map;

import com.amani.model.Sysrolemode;
import com.amani.model.Useroverall;
import com.opensymphony.xwork2.ActionContext;

public class WebResourceManager {
	public static void putIntoSession(Object key, Object value) {
		ActionContext.getContext().getSession().put(key.toString(), value);
	}
	public static void putIntoSession(Map map) {
		ActionContext.getContext().getSession().putAll(map);
	}
	public static Object getFromSession(Object key) {
		Map session = ActionContext.getContext().getSession();
		return session.get(key);
	}
	public static void putIntoApplication(Object key,Object value) {
		ActionContext.getContext()
			.getApplication().put(key.toString(), value);
	}
	public static Object getFromApplication(Object key) {
		return ActionContext.getContext().getSession().get(key);
	}
	public static String getCompId() {
		return (String)WebResourceManager
			.getFromSession(SystemFinal.SESSION_KEY_COMPID);
	}
	public static void putCompId(String compId) {
		WebResourceManager
			.putIntoSession(SystemFinal.SESSION_KEY_COMPID, compId);
	}
	
	public static void putMozuInfo(List<Useroverall> mb) {
		WebResourceManager
			.putIntoSession(SystemFinal.MOZU_BEAN,mb);
	}
	public static List<Useroverall> getMozuInfo() {
		
		return (List<Useroverall>) WebResourceManager
			.getFromSession(SystemFinal.MOZU_BEAN);
	}	
	public static void putModuleInfo(List<Sysrolemode> mb) {
		WebResourceManager
			.putIntoSession(SystemFinal.MODULE_BEAN,mb);
	}
	public static List<Sysrolemode> getModuleInfo() {
		
		return (List<Sysrolemode>) WebResourceManager
			.getFromSession(SystemFinal.MODULE_BEAN);
	}

	public static void putUserInfo(UserInformation userInfo){
		WebResourceManager
			.putIntoSession(SystemFinal.GLOABL_USER_INFO,userInfo);
	}
	public static UserInformation getUserInfo(){
		return (UserInformation)WebResourceManager
				.getFromSession(SystemFinal.GLOABL_USER_INFO);
	}
}
