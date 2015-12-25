package com.amani.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import com.amani.model.Compchainstruct;
import com.amani.model.Sysparaminfo;
import com.amani.service.*;
import com.amani.tools.*;
/**
 * 
 * @author LiuJie Jun 24, 2013 10:26:53 AM
 * @version: 1.0
 * @Copyright: AMN
 */
@ParentPackage("com.amani.action") 

@Namespace("/")
public class LoginAction extends AMN_Action
{

	private static final long serialVersionUID = 1918061666278287155L;

	private String compid;

	private String userid;

	private String pwd;

	private String vercode;

	private String amnNews;

	private String licenseCode;

	private String macAddr;

	private String ipAddr;
	private int logintype;

	private String strMessage;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private boolean trial = false;
	private String strCheckFlag="Y";//当前用户存在  N 当前用户已经被替代
	
	private String retUserNo;
	private String retPassword;
	
	private String strLoginCompid;
	private String strLoginUserId;
	
	/*****************/
	private String strCompId;
	private String strUserId;
	private String strUserRols;
	private String strUserRolsName;
	private String strCompName;
	private String strCurrDate;
	private String strUserName;
	private String callcenterqueue;
	private String callcenterinterface;
	private int		bankPassFlag; 
	private final String errormsg1="该门店不能在此IP网络下登录,请确认";
	private final String errormsg2="账户有误登录失败,请确认";
	@Autowired
	private LoginServiceImp loginService;

	/*public void setLoginService(LoginServiceImp loginService) {
		this.loginService = loginService;
	}*/

	public String getCompid() {
		return compid;
	}

	public void setCompid(String compid) {
		this.compid = compid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getVercode() {
		return vercode;
	}

	public void setVercode(String vercode) {
		this.vercode = vercode;
	}

	public String softwareRegist() {
		return super.SUCCESS;
	}

	@Action(value = "VerifyLogin", results = { @Result(name = "login_success", type="redirect", location = "/fullPadScreen.html"),
												@Result(name = "login_successpad", location = "/fullScreenpad/index.jsp"),
												@Result(name = "login_failure", location = "/Login_3.jsp")}) 
	public String execute() throws Exception {
		//add by lyj 如果公司别或者员工为空时候应该是登录错误
		compid=replaceBlank(compid);
		compid=compid.replace("　", "");
		compid=compid.replace("'", "");
		userid=userid.replace("'", "");
		compid=compid.trim();
		if(StringUtils.isEmpty(compid)==true || StringUtils.isEmpty(userid)==true)
		{
			return "login_failure"; 
		}
		
		
		
		StringBuffer 	retMsg = new StringBuffer();
		StringBuffer	bankBuf = new StringBuffer();
		loginService.processUserLogin(compid, userid, pwd,licenseCode,macAddr,ipAddr,retMsg);
		String processResult = retMsg.toString();
		if(processResult.equals(SystemFinal.COMP_NOT_EXIST))
		{
			addActionError("登录公司不存在");
		}
		else if(processResult.equals(SystemFinal.LOGIN_VERTIFY_FAIL))
		{
			addActionError("账户有误,登录失败");
		}
		else if(processResult.equals(SystemFinal.LOGIN_OUTDATE))
		{
			addActionError("登录账户已不再有效期内");
		}
		else if(processResult.equals(SystemFinal.LOGIN_OUTCOMPID))
		{
			addActionError("该账户登陆的门店已被禁止登陆");
		}
		else if(processResult.equals(SystemFinal.LOGIN_SUCCESS))
		{
			String strSpg107=this.loginService.getDataTool().loadSysParam(compid, "SP107");
			if(CommonTool.FormatString(strSpg107).equals("1")) //启用IP限制
			{
				if(!CommonTool.FormatString(ipAddr).equals("") && CommonTool.FormatString(ipAddr).indexOf(".")>-1)
				{
					String targetIpAddress =this.loginService.getDataTool().loadCompIPById(compid);
					String targetIpAddressex =this.loginService.getDataTool().loadCompIPexById(compid);
					int validateflag=0;
					if(!CommonTool.FormatString(targetIpAddress).equals("") && ipAddr.indexOf(targetIpAddress)>-1)
					{
						validateflag=1;
					}
					if(!CommonTool.FormatString(targetIpAddressex).equals("") && ipAddr.indexOf(targetIpAddressex)>-1)
					{
						validateflag=1;
					}
					if(CommonTool.FormatString(targetIpAddress).equals("") && CommonTool.FormatString(targetIpAddressex).equals(""))
					{
						validateflag=1;
					}
					if(validateflag==0)
					{
						ipAddr=this.getRemortIP();
						if(!CommonTool.FormatString(targetIpAddress).equals("") && ipAddr.indexOf(targetIpAddress)>-1)
						{
							validateflag=1;
						}
						if(!CommonTool.FormatString(targetIpAddressex).equals("") && ipAddr.indexOf(targetIpAddressex)>-1)
						{
							validateflag=1;
						}
						System.out.println("真实IP地址"+ipAddr);
						if(validateflag==0)
						{
							if(compid.equals("001"))
							{
								addActionError("门店不能使用总部工号登录系统");
							}
							else
							{
								addActionError("本门店不能使用其他门店工号登录系统");
							}
							//addActionError(compid+"门店("+ipAddr+")不能在此IP("+targetIpAddress+")网络下登录,请确认!");
							return "login_failure";
						}
						
					}
				}
			}
			try{
				ActionContext ctx = ActionContext.getContext();
				Map session = ctx.getSession();
				Map application = ctx.getApplication();
				UserInformation userInfo = this.loginService.loadUserInformation(compid, userid); //用户信息
				userInfo.setBankPassFlag(0);
				if(!CommonTool.FormatString(pwd).equals(""))
				{
					userInfo.setBankPassFlag(1);
				}

				session.put("strCurCompId", compid);
				session.put("StrCurUserid", userid);
				session.put("userInfo", userInfo);
				if(application.get("chainLevels")==null)//连锁组织的级别
				{
					HashMap chainLevels = this.loginService.getOrgManager().getChainLevelNames();
					application.put("chainLevels", chainLevels);
					chainLevels=null;
				}
				if(application.get("orgList") == null)//组织（公司）列表
				{
					HashMap orgList = this.loginService.getOrgManager().getOrgList();
					application.put("orgList", orgList);
					orgList=null;
				}
				
				if(application.get("chain") == null)//连锁组织结构
				{
					List<Compchainstruct> chain = this.loginService.getOrgManager().getChainMetaData();
					application.put("chain", chain);
					chain=null;
				}
				String paramkey = compid + "_levelOrgs";
				if(application.get(paramkey) == null)//连锁组织结构
				{
					HashMap levelOrgs = this.loginService.getOrgManager().getLevelOrgs();
					application.put(paramkey, levelOrgs);
					levelOrgs=null;
				}
				session=null;
				ctx=null;
				if(CommonTool.FormatInteger(logintype)==1)
				{
					strUserId=userInfo.getUserId();
					strUserName=userInfo.getUserName();
					callcenterqueue=userInfo.getCallcenterqueue();
					callcenterinterface=userInfo.getCallcenterinterface();
					strUserRols=userInfo.getUserrole();
					strUserRolsName=userInfo.getUserrolename();
					strCompId=userInfo.getCompId();
					strCompName=userInfo.getCompName();
					bankPassFlag=userInfo.getBankPassFlag();
					return "login_successpad";
				}
				//session.put("paramInfo", paramInfo);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			return "login_success";
		}
		return "login_failure";
	}
	
	public String getRemortIP()
	{   
		ActionContext ac = ActionContext.getContext(); 
		HttpServletRequest request =(HttpServletRequest)ac.get(ServletActionContext.HTTP_REQUEST);
		if (request.getHeader("x-forwarded-for") == null) 
		{    
			return request.getRemoteAddr();   
		}   
		return request.getHeader("x-forwarded-for");  
	} 
	
	@Action(value = "retPassword",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String retPassword()
	{
		this.strMessage="";
		boolean flag=this.loginService.changeUserPassword(this.retUserNo, this.retPassword);
		if(flag==false)
		{
			this.strMessage="设置失败";
		}
		return "load_success";
	}
	
	//现在不需要取，但是这个函数为了，不停的向后台发送请求，来保持Session
	@Action(value = "checkUserSession",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	
	public String checkUserSession()
	{
		
		try
		{
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		Map application = ctx.getApplication();
		UserInformation userInfo=(UserInformation) session.get("userInfo");
		session.put("strCurCompId", strLoginCompid);
		session.put("StrCurUserid", strLoginUserId);
		if(userInfo==null)
		{
			 userInfo = this.loginService.loadUserInformation(strLoginCompid, strLoginUserId); //用户信息
			 userInfo.setBankPassFlag(0);
			 session.put("userInfo", userInfo);
		}
		
		
		if(application.get("chainLevels")==null)//连锁组织的级别
		{
			HashMap chainLevels = this.loginService.getOrgManager().getChainLevelNames();
			application.put("chainLevels", chainLevels);
			chainLevels=null;
		}
		if(application.get("orgList") == null)//组织（公司）列表
		{
			HashMap orgList = this.loginService.getOrgManager().getOrgList();
			application.put("orgList", orgList);
			orgList=null;
		}
		if(application.get("chain") == null)//连锁组织结构
		{
			List<Compchainstruct> chain = this.loginService.getOrgManager().getChainMetaData();
			application.put("chain", chain);
			chain=null;
		}
		String paramkey = strLoginCompid + "_levelOrgs";
		if(application.get(paramkey) == null)//连锁组织结构
		{
			HashMap levelOrgs = this.loginService.getOrgManager().getLevelOrgs();
			application.put(paramkey, levelOrgs);
			levelOrgs=null;
		}
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public  String replaceBlank(String str) {  

        String dest = "";  

        if (str!=null) {  

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
                /*
         * 注：\n 回车(\u000a) \t 水平制表符(\u0009) \s 空格(\u0008)\r 换行(\u000d)
         */

             Matcher m = p.matcher(str);  

           dest = m.replaceAll("");  

        	}  

         return dest;  
     } 
	
	@Action(value = "checkUserValidate",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String checkUserValidate()
	{
		this.strCheckFlag="Y";
		ActionContext ctx = ActionContext.getContext();
		if(ctx.getSession().get("userInfo")==null)
		{
			this.strCheckFlag="N";
		}
		ctx=null;
		return "load_success";
	}
	
	@Action( value = "exitAMN", 
			 results = {@Result(name = "success", type = "json")})
	public String exitAMN() 
	{
		return super.SUCCESS;
	}

	@Action( value = "initLogin", 
			 results = {@Result(name = "success", type = "json")})
	public String initLogin() 
	{
		this.amnNews = "";
		// trial = loginService.isTrail(macAddr)?true:false;
		return super.SUCCESS;
	}

	

	public String getAmnNews() {
		return amnNews;
	}

	public void setAmnNews(String amnNews) {
		this.amnNews = amnNews;
	}


	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public boolean isTrial() {
		return trial;
	}

	public void setTrial(boolean trial) {
		this.trial = trial;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getLicenseCode() {
		return licenseCode;
	}

	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}

	public String getStrCheckFlag() {
		return strCheckFlag;
	}

	public void setStrCheckFlag(String strCheckFlag) {
		this.strCheckFlag = strCheckFlag;
	}

	public String getStrMessage() {
		return strMessage;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	public String getRetUserNo() {
		return retUserNo;
	}

	public void setRetUserNo(String retUserNo) {
		this.retUserNo = retUserNo;
	}

	public String getRetPassword() {
		return retPassword;
	}

	public void setRetPassword(String retPassword) {
		this.retPassword = retPassword;
	}

	public String getStrLoginCompid() {
		return strLoginCompid;
	}

	public void setStrLoginCompid(String strLoginCompid) {
		this.strLoginCompid = strLoginCompid;
	}

	public String getStrLoginUserId() {
		return strLoginUserId;
	}

	public void setStrLoginUserId(String strLoginUserId) {
		this.strLoginUserId = strLoginUserId;
	}

	public int getLogintype() {
		return logintype;
	}

	public void setLogintype(int logintype) {
		this.logintype = logintype;
	}

	public String getStrCompId() {
		return strCompId;
	}

	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}

	public String getStrUserId() {
		return strUserId;
	}

	public void setStrUserId(String strUserId) {
		this.strUserId = strUserId;
	}

	public String getStrUserRols() {
		return strUserRols;
	}

	public void setStrUserRols(String strUserRols) {
		this.strUserRols = strUserRols;
	}

	public String getStrUserRolsName() {
		return strUserRolsName;
	}

	public void setStrUserRolsName(String strUserRolsName) {
		this.strUserRolsName = strUserRolsName;
	}

	public String getStrCompName() {
		return strCompName;
	}

	public void setStrCompName(String strCompName) {
		this.strCompName = strCompName;
	}

	public String getStrCurrDate() {
		return strCurrDate;
	}

	public void setStrCurrDate(String strCurrDate) {
		this.strCurrDate = strCurrDate;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public String getCallcenterqueue() {
		return callcenterqueue;
	}

	public void setCallcenterqueue(String callcenterqueue) {
		this.callcenterqueue = callcenterqueue;
	}

	public String getCallcenterinterface() {
		return callcenterinterface;
	}

	public void setCallcenterinterface(String callcenterinterface) {
		this.callcenterinterface = callcenterinterface;
	}

	public int getBankPassFlag() {
		return bankPassFlag;
	}

	public void setBankPassFlag(int bankPassFlag) {
		this.bankPassFlag = bankPassFlag;
	}


}
