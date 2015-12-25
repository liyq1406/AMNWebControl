package com.amani.action.BaseInfoControl;

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


import com.amani.model.Salaryrateinfo;
import com.amani.model.Syscommoninfomode;
import com.amani.model.Sysparaminfo;
import com.amani.service.BaseInfoControl.BC008Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc008")
public class BC008Action extends AMN_ModuleAction{
	@Autowired
	private BC008Service bc008Service;
	private String strJsonParam;	
    private String strCurCompId;
    private Sysparaminfo curSysparam;
    private List<Syscommoninfomode> lsinfomodes; //门店系统参数设置
    private Salaryrateinfo	curSalaryrateinfo;  //门店提成系数设置
    private String strBandComps; //分发门店
    @JSON(serialize=false)
	public BC008Service getBc008Service() {
		return bc008Service;
	}
    @JSON(serialize=false)
	public void setBc008Service(BC008Service bc008Service) {
		this.bc008Service = bc008Service;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
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

	public Sysparaminfo getCurSysparam() {
		return curSysparam;
	}
	public void setCurSysparam(Sysparaminfo curSysparam) {
		this.curSysparam = curSysparam;
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
	@Action(value = "loadparamsInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
     @Result(name = "load_failure", type = "json")	
	}) 
	public String loadparamsInfo()
	{
		if(CommonTool.FormatString(this.strCurCompId).equals(""))
		{
			this.curSysparam=new Sysparaminfo();
		}
		else
		{
			this.curSysparam=this.bc008Service.getDataTool().loadSysparamInfoByCompId(this.strCurCompId);
			lsinfomodes=this.bc008Service.loadInfoModesByCompId(strCurCompId);
			curSalaryrateinfo=this.bc008Service.loadRateInfoByCompId(strCurCompId);
			if(curSalaryrateinfo==null )
				curSalaryrateinfo=new Salaryrateinfo();
		}
		
		return SystemFinal.LOAD_SUCCESS;
	}
	@Action(value = "postparamsInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
    @Result(name = "post_failure", type = "json")	
	}) 
	public String postparamsInfo()
	{
		this.strMessage="";
		if(this.bc008Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC008", SystemFinal.UR_POST)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		boolean flag=this.bc008Service.postparamsInfoByCompId(this.curSysparam,this.strCurCompId,this.curSalaryrateinfo);
		return SystemFinal.POST_SUCCESS;
	}
	
	
	@Action(value = "copyShopRateSetInfos",  results = { 
			 @Result(name = "load_success", type = "json"),
         @Result(name = "load_failure", type = "json")	
      }) 
	public String copyShopRateSetInfos()
	{
		this.strMessage="";
		if(this.bc008Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC008", SystemFinal.UR_SPECIAL_CHECK)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.LOAD_FAILURE;
		}
		boolean flag=this.bc008Service.handCopyInfo(this.strCurCompId,this.strBandComps);
		if(flag==false)
		{
			this.strMessage="拷贝失败";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public List<Syscommoninfomode> getLsinfomodes() {
		return lsinfomodes;
	}
	public void setLsinfomodes(List<Syscommoninfomode> lsinfomodes) {
		this.lsinfomodes = lsinfomodes;
	}
	public Salaryrateinfo getCurSalaryrateinfo() {
		return curSalaryrateinfo;
	}
	public void setCurSalaryrateinfo(Salaryrateinfo curSalaryrateinfo) {
		this.curSalaryrateinfo = curSalaryrateinfo;
	}
	public String getStrBandComps() {
		return strBandComps;
	}
	public void setStrBandComps(String strBandComps) {
		this.strBandComps = strBandComps;
	}
	
}
