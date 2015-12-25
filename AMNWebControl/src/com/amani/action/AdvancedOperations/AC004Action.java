package com.amani.action.AdvancedOperations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;


import com.amani.model.Storeconfirmflow;
import com.amani.service.AdvancedOperations.AC004Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac004")
public class AC004Action extends AMN_ModuleAction{
	@Autowired
	private AC004Service ac004Service;
	private String strJsonParam;	
    private String strCurCompId;
    private String strCurProjectId;
    private List<Storeconfirmflow> lsStoreconfirmflow;
    private String appitemno;
    private String checkcommissioner;
    private String checkmanager;
    private String checkcommissionertext;
    private String checkmanagertext;
	public String getAppitemno() {
		return appitemno;
	}
	public void setAppitemno(String appitemno) {
		this.appitemno = appitemno;
	}
	public String getCheckcommissioner() {
		return checkcommissioner;
	}
	public void setCheckcommissioner(String checkcommissioner) {
		this.checkcommissioner = checkcommissioner;
	}
	public String getCheckmanager() {
		return checkmanager;
	}
	public void setCheckmanager(String checkmanager) {
		this.checkmanager = checkmanager;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	public String getStrCurProjectId() {
		return strCurProjectId;
	}
	public void setStrCurProjectId(String strCurProjectId) {
		this.strCurProjectId = strCurProjectId;
	}
	
	
	@Override
	protected boolean addActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterPost() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean postActive() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}

	@Override
	public String getStrMessage() {
		// TODO Auto-generated method stub
		return super.getStrMessage();
	}
	@Override
	public void setStrMessage(String strMessage) {
		// TODO Auto-generated method stub
		super.setStrMessage(strMessage);
	}
	
	@Action(value = "loadDataInfos",  results = { 
			 @Result(name = "load_success", type = "json"),
      @Result(name = "load_failure", type = "json")	
   }) 
	public String loadDataInfos()
	{
		this.lsStoreconfirmflow=this.ac004Service.loadMaster();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//返销收银单
	@Action(value = "updateMastInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	}) 
	public String updateMastInfo()
	{
		this.strMessage="";
		if(this.ac004Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC004", SystemFinal.UR_POST)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		boolean flag=this.ac004Service.postMasterInfo(appitemno, checkcommissioner, checkmanager, checkcommissionertext, checkmanagertext);
		if(flag==false)
		{
			this.strMessage="保存失败！";
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	@JSON(serialize=false)
	public AC004Service getAc004Service() {
		return ac004Service;
	}
	@JSON(serialize=false)
	public void setAc004Service(AC004Service ac004Service) {
		this.ac004Service = ac004Service;
	}
	public List<Storeconfirmflow> getLsStoreconfirmflow() {
		return lsStoreconfirmflow;
	}
	public void setLsStoreconfirmflow(List<Storeconfirmflow> lsStoreconfirmflow) {
		this.lsStoreconfirmflow = lsStoreconfirmflow;
	}
	public String getCheckcommissionertext() {
		return checkcommissionertext;
	}
	public void setCheckcommissionertext(String checkcommissionertext) {
		this.checkcommissionertext = checkcommissionertext;
	}
	public String getCheckmanagertext() {
		return checkmanagertext;
	}
	public void setCheckmanagertext(String checkmanagertext) {
		this.checkmanagertext = checkmanagertext;
	}
	
}
