package com.amani.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;


import com.amani.model.Useroverall;
import com.amani.service.InterfaceService;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemInformation;
import com.amani.tools.UserInformation;
import com.amani.tools.SystemFinal;
import com.amani.tools.WebResourceManager;

/**
 * 
 * @author LiuJie Jun 24, 2013 2:35:21 PM
 * @version: 1.0
 * @Copyright: AMN
 */
@ParentPackage("com.amani.action")
@Namespace("/")
@SuppressWarnings("unused")
public class InterfaceAction extends AMN_Action {

	private static final long serialVersionUID = -8777721967734666800L;

	@Autowired
	private InterfaceService interfaceService;


	private String favorite;

	private String strCompId;

	private String strUserId;
	private String strUserRols;
	private String strUserRolsName;
	private String strCompName;
	private String strCurrDate;
	private String strUserName;
	private String callcenterqueue;
	private String callcenterinterface;

	private String strEndDate;

	private String distinct;
	private int		bankPassFlag; 

	public int getBankPassFlag() {
		return bankPassFlag;
	}

	public void setBankPassFlag(int bankPassFlag) {
		this.bankPassFlag = bankPassFlag;
	}

	@Action(value = "interfaceAction", results = { @Result(name = "success", location = "/fullScreen2/index.jsp") })
	public String execute() throws Exception {

		try {
			UserInformation userInfo = (UserInformation) WebResourceManager.getUserInfo();
			if(userInfo!=null)
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
			}
			WebResourceManager.putCompId(userInfo.getCompId());
			WebResourceManager.putModuleInfo(userInfo.getLsSysrolemode());
			List<Useroverall> lsUseroverall=userInfo.getLsUseroverall();
			List<Useroverall> lsPutUseroverall=new ArrayList();
			if(lsUseroverall!=null && lsUseroverall.size()>0)
			{
				for(int i=0;i<lsUseroverall.size();i++)
				{
					if(CommonTool.FormatString(lsUseroverall.get(i).getId().getModetype()).equals("2"))
					{
						lsPutUseroverall.add(lsUseroverall.get(i));
					}
				}
			}
			WebResourceManager.putMozuInfo(lsPutUseroverall);
			lsUseroverall=null;
			lsPutUseroverall=null;
			userInfo=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SystemFinal.SUCCESS;
	}

	public String releaseResource() {

		return SystemFinal.SUCCESS;
	}

	public InterfaceService getInterfaceService() {
		return interfaceService;
	}

	public void setInterfaceService(InterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}

	
	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public String getStrCompId() {
		return CommonTool.getLoginInfo("COMPID");
	}

	public String getStrCompName() {
		return CommonTool.getLoginInfo("COMPNAME");
	}

	public String getStrCurrDate() {
		return CommonTool.getDateMask(CommonTool.getCurrDate());
	}

	public String getStrUserName() {
		return CommonTool.getLoginInfo("USERNAME");
	}

	public String getStrUserId() {
		return CommonTool.getLoginInfo("USERID");
	}

	public String getDistinct() {
		return distinct;
	}

	public void setDistinct(String distinct) {
		this.distinct = distinct;
	}

	public String getStrEndDate() {
		return strEndDate;
	}

	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}

	public void setStrUserId(String strUserId) {
		this.strUserId = strUserId;
	}

	public void setStrCompName(String strCompName) {
		this.strCompName = strCompName;
	}

	public void setStrCurrDate(String strCurrDate) {
		this.strCurrDate = strCurrDate;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
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

}
