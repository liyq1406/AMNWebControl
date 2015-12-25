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


import com.amani.model.Projectinfo;
import com.amani.service.BaseInfoControl.BC006Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc006")
public class BC006Action extends AMN_ModuleAction{
	@Autowired
	private BC006Service bc006Service;
	private String strJsonParam;	
    private String strCurCompId;
    private String strCurProjectId;
    private List<Projectinfo> lsProjectinfoInfo;
    @JSON(serialize=false)
	public BC006Service getBc006Service() {
		return bc006Service;
	}
    @JSON(serialize=false)
	public void setBc006Service(BC006Service bc006Service) {
		this.bc006Service = bc006Service;
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
	
	public List<Projectinfo> getLsProjectinfoInfo() {
		return lsProjectinfoInfo;
	}
	public void setLsProjectinfoInfo(List<Projectinfo> lsProjectinfoInfo) {
		this.lsProjectinfoInfo = lsProjectinfoInfo;
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
	
	@Action(value = "loadPrjInfos",  results = { 
			 @Result(name = "load_success", type = "json"),
      @Result(name = "load_failure", type = "json")	
   }) 
	public String loadPrjInfos()
	{
		if(CommonTool.FormatString(this.strCurCompId).equals(""))
		{
			this.lsProjectinfoInfo=new ArrayList();
		}
		else
		{
			this.lsProjectinfoInfo=this.bc006Service.getDataTool().loadProjectinfoByCompId(this.strCurCompId,1);
		}
		if(lsProjectinfoInfo!=null && lsProjectinfoInfo.size()>0)
		{
			for(int i=0;i<lsProjectinfoInfo.size();i++)
			{
				lsProjectinfoInfo.get(i).setBprjno(lsProjectinfoInfo.get(i).getId().getPrjno());
				lsProjectinfoInfo.get(i).setBprjmodeId(lsProjectinfoInfo.get(i).getId().getPrjmodeId());
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
}
