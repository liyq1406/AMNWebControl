package com.amani.action.BaseInfoControl;

import java.util.List;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;

import com.amani.model.Cardchangecostrate;
import com.amani.model.CardchangecostrateId;
import com.amani.model.Projectcostinfo;
import com.amani.model.Projectinfo;
import com.amani.service.BaseInfoControl.BC018Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc018")
public class BC018Action extends AMN_ModuleAction{
	@Autowired
	private BC018Service bc018Service;
	private String strJsonParam;	
    private String strCurCompId;
    private String strCurProjecttypeid;
    private String strDownCurCompId;
    private String strDownCurProjecttypeid;
    private List<Projectinfo> lsPrjInfos;
    private List<Projectcostinfo> lsProjectcostinfos;
	private String curStrProjectNo;
	private String curStrGoodsNo;
	private double curGoodsCount;
	
	
	public String getCurStrProjectNo() {
		return curStrProjectNo;
	}




	public void setCurStrProjectNo(String curStrProjectNo) {
		this.curStrProjectNo = curStrProjectNo;
	}




	public String getCurStrGoodsNo() {
		return curStrGoodsNo;
	}




	public void setCurStrGoodsNo(String curStrGoodsNo) {
		this.curStrGoodsNo = curStrGoodsNo;
	}




	public double getCurGoodsCount() {
		return curGoodsCount;
	}




	public void setCurGoodsCount(double curGoodsCount) {
		this.curGoodsCount = curGoodsCount;
	}




	@Action(value = "loadCurProjectinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurProjectinfo()
	{
		this.lsPrjInfos=this.bc018Service.loadCurProjectinfoByType(this.strCurProjecttypeid);
		return SystemFinal.LOAD_SUCCESS;
	}
	

	@Action(value = "loadCurGoodsinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurGoodsinfo()
	{
		this.lsProjectcostinfos=this.bc018Service.loadCurProjectcostinfo(this.curStrProjectNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	
	@Action(value = "postCurProjectinfo",  results = { 
			 @Result(name = "post_success", type = "json"),
           @Result(name = "post_failure", type = "json")	
        }) 
	public String postCurProjectinfo()
	{
			this.strMessage="";
			boolean flag=this.bc018Service.postPrjCostInfo(this.curStrProjectNo, this.curStrGoodsNo, this.curGoodsCount);
			if(flag==false)
				this.strMessage="保存失败,请确认!";
			return SystemFinal.POST_SUCCESS;
		
	}

	@Action(value = "deleteCurProjectinfo",  results = { 
			 @Result(name = "post_success", type = "json"),
           @Result(name = "post_failure", type = "json")	
        }) 
	public String deleteCurProjectinfo()
	{
			this.strMessage="";
			boolean flag=this.bc018Service.deletePrjCostInfo(this.curStrProjectNo, this.curStrGoodsNo);
			if(flag==false)
				this.strMessage="删除失败,请确认!";
			return SystemFinal.POST_SUCCESS;
		
	}
	
	
	@Action(value = "post",  results = { 
			 @Result(name = "post_success", type = "json"),
           @Result(name = "post_failure", type = "json")	
        }) 
	@Override
	public String post()
	{
		
			return SystemFinal.POST_SUCCESS;
		
	}
	
	
	
	@JSON(serialize=false)
	public BC018Service getBc018Service() {
		return bc018Service;
	}
    @JSON(serialize=false)
	public void setBc018Service(BC018Service bc018Service) {
		this.bc018Service = bc018Service;
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
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}




	@Override
	protected boolean beforePost() {
		
		return true;
	}




	public String getStrCurProjecttypeid() {
		return strCurProjecttypeid;
	}
	public void setStrCurProjecttypeid(String strCurProjecttypeid) {
		this.strCurProjecttypeid = strCurProjecttypeid;
	}

	public String getStrDownCurCompId() {
		return strDownCurCompId;
	}
	public void setStrDownCurCompId(String strDownCurCompId) {
		this.strDownCurCompId = strDownCurCompId;
	}
	public String getStrDownCurProjecttypeid() {
		return strDownCurProjecttypeid;
	}
	public void setStrDownCurProjecttypeid(String strDownCurProjecttypeid) {
		this.strDownCurProjecttypeid = strDownCurProjecttypeid;
	}




	public List<Projectinfo> getLsPrjInfos() {
		return lsPrjInfos;
	}




	public void setLsPrjInfos(List<Projectinfo> lsPrjInfos) {
		this.lsPrjInfos = lsPrjInfos;
	}




	public List<Projectcostinfo> getLsProjectcostinfos() {
		return lsProjectcostinfos;
	}




	public void setLsProjectcostinfos(List<Projectcostinfo> lsProjectcostinfos) {
		this.lsProjectcostinfos = lsProjectcostinfos;
	}
	
}
