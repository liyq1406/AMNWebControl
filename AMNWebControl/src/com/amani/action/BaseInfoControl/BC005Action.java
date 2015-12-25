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


import com.amani.model.Goodsinfo;
import com.amani.service.BaseInfoControl.BC005Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc005")
public class BC005Action extends AMN_ModuleAction{
	@Autowired
	private BC005Service bc005Service;
	private String strJsonParam;	
    private String strCurCompId;
    private String strCurProjectId;
    private List<Goodsinfo> lsGoodsinfoInfo;
    @JSON(serialize=false)
	public BC005Service getBc005Service() {
		return bc005Service;
	}
    @JSON(serialize=false)
	public void setBc005Service(BC005Service bc005Service) {
		this.bc005Service = bc005Service;
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
	
	
	public List<Goodsinfo> getLsGoodsinfoInfo() {
		return lsGoodsinfoInfo;
	}
	public void setLsGoodsinfoInfo(List<Goodsinfo> lsGoodsinfoInfo) {
		this.lsGoodsinfoInfo = lsGoodsinfoInfo;
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
	
	@Action(value = "loadGoodsInfos",  results = { 
			 @Result(name = "load_success", type = "json"),
      @Result(name = "load_failure", type = "json")	
   }) 
	public String loadGoodsInfos()
	{
		if(CommonTool.FormatString(this.strCurCompId).equals(""))
		{
			this.lsGoodsinfoInfo=new ArrayList();
		}
		else
		{
			this.lsGoodsinfoInfo=this.bc005Service.getDataTool().loadGoodsinfoByCompId(this.strCurCompId,0);
		}
		if(lsGoodsinfoInfo!=null && lsGoodsinfoInfo.size()>0)
		{
			for(int i=0;i<lsGoodsinfoInfo.size();i++)
			{
				lsGoodsinfoInfo.get(i).setBgoodsno(lsGoodsinfoInfo.get(i).getId().getGoodsno());
				lsGoodsinfoInfo.get(i).setBgoodsmodeid(lsGoodsinfoInfo.get(i).getId().getGoodsmodeid());
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
}
