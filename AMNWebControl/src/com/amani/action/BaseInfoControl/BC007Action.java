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

import com.amani.model.Cardtypeinfo;
import com.amani.model.Cardchangerule;
import com.amani.service.BaseInfoControl.BC007Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc007")
public class BC007Action extends AMN_ModuleAction{
	@Autowired
	private BC007Service bc007Service;
	private String strJsonParam;	
    private String strCurCompId;
    private String strCurCardTypeNo;
    private List<Cardtypeinfo> lsCardtypeInfo;
    private List<Cardchangerule> lsCardchangeruleInfo;
    @JSON(serialize=false)
	public BC007Service getBc007Service() {
		return bc007Service;
	}
    @JSON(serialize=false)
	public void setBc007Service(BC007Service bc007Service) {
		this.bc007Service = bc007Service;
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
	
	@Action(value = "loadCardchangerule",  results = { 
			 @Result(name = "load_success", type = "json"),
      @Result(name = "load_failure", type = "json")	
   }) 
	public String loadCardchangerule()
	{
		if(CommonTool.FormatString(this.strCurCompId).equals(""))
		{
			this.lsCardchangeruleInfo=new ArrayList();
		}
		else
		{
			this.lsCardchangeruleInfo=this.bc007Service.loadCardchangeruleByCompId(this.strCurCompId,this.strCurCardTypeNo);
		}
		if(lsCardchangeruleInfo!=null && lsCardchangeruleInfo.size()>0)
		{
			for(int i=0;i<lsCardchangeruleInfo.size();i++)
			{
				lsCardchangeruleInfo.get(i).setBcardtypeno(lsCardchangeruleInfo.get(i).getId().getCardtypeno());
				lsCardchangeruleInfo.get(i).setBrulemodeid(lsCardchangeruleInfo.get(i).getId().getRulemodeid());
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadCardTyeInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
     @Result(name = "load_failure", type = "json")	
  }) 
	public String loadCardTyeInfo()
	{
		if(CommonTool.FormatString(this.strCurCompId).equals(""))
		{
			this.lsCardtypeInfo=new ArrayList();
		}
		else
		{
			this.lsCardtypeInfo=this.bc007Service.loadCardtypeinfoByCompId(this.strCurCompId);
		}
		if(lsCardtypeInfo!=null && lsCardtypeInfo.size()>0)
		{
			for(int i=0;i<lsCardtypeInfo.size();i++)
			{
				lsCardtypeInfo.get(i).setBcardtypeno(lsCardtypeInfo.get(i).getId().getCardtypeno());
				lsCardtypeInfo.get(i).setBcardtypemodeid(lsCardtypeInfo.get(i).getId().getCardtypemodeid());
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	public String getStrCurCardTypeNo() {
		return strCurCardTypeNo;
	}
	public void setStrCurCardTypeNo(String strCurCardTypeNo) {
		this.strCurCardTypeNo = strCurCardTypeNo;
	}
	public List<Cardtypeinfo> getLsCardtypeInfo() {
		return lsCardtypeInfo;
	}
	public void setLsCardtypeInfo(List<Cardtypeinfo> lsCardtypeInfo) {
		this.lsCardtypeInfo = lsCardtypeInfo;
	}
	public List<Cardchangerule> getLsCardchangeruleInfo() {
		return lsCardchangeruleInfo;
	}
	public void setLsCardchangeruleInfo(List<Cardchangerule> lsCardchangeruleInfo) {
		this.lsCardchangeruleInfo = lsCardchangeruleInfo;
	}
	
}
