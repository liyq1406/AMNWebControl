package com.amani.action.CardControl;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.bean.CardYears;
import com.amani.model.Dnointernalcardinfo;
import com.amani.model.Staffinfo;
import com.amani.model.Yearcarddetal;
import com.amani.model.Yearselldetal;
import com.amani.model.Yearsellinfo;
import com.amani.model.YearsellinfoId;
import com.amani.service.CardControl.CC019Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc019")
public class CC019Action extends AMN_ModuleAction {
	
	private List<Yearcarddetal> lsYearcarddetals;
	private List<Yearcarddetal> lsPamars;
	private List<Staffinfo> lsStaffinfo;
	private List<CardYears> lsCardYears=null;
	@Autowired
	private CC019Service cc019Service;
	private String curjosnparam;
	private String strCurCompId;
	public String getStrMessage() {
		return strMessage;
	}


	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	private String strMessage="";
	private Yearsellinfo curMaster;
	
	public String getCurjosnparam() {
		return curjosnparam;
	}


	public void setCurjosnparam(String curjosnparam) {
		this.curjosnparam = curjosnparam;
	}
	
	public Yearsellinfo getCurMaster() {
		return curMaster;
	}


	public void setCurMaster(Yearsellinfo curMaster) {
		this.curMaster = curMaster;
	}


	public List<Yearcarddetal> getLsYearcarddetals() {
		return lsYearcarddetals;
	}


	public void setLsYearcarddetals(List<Yearcarddetal> lsYearcarddetals) {
		this.lsYearcarddetals = lsYearcarddetals;
	}


	@Action(value = "load",  results = { 
			 @Result(name = "load_success", location="/CardControl/CC019/index.jsp"),
			 @Result(name = "load_failure", location = "/CardControl/CC019/index.jsp")	   })
	public String load()
	{
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadEmpNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	})
	public String loadEmpNo()
	{
		this.lsStaffinfo=this.cc019Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadYears",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	})
	public String loadYears()
	{
		lsYearcarddetals=cc019Service.loadYears(lsPamars);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public List<Yearcarddetal> getLsPamars() {
		return lsPamars;
	}


	public void setLsPamars(List<Yearcarddetal> lsPamars) {
		this.lsPamars = lsPamars;
	}


	public List<Staffinfo> getLsStaffinfo() {
		return lsStaffinfo;
	}


	public void setLsStaffinfo(List<Staffinfo> lsStaffinfo) {
		this.lsStaffinfo = lsStaffinfo;
	}


	public String getStrCurCompId() {
		return strCurCompId;
	}


	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}


	public void setCc019Service(CC019Service cc019Service) {
		this.cc019Service = cc019Service;
	}
	
	
	@Action(value = "post",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	})
	public String post()
	{
		strMessage="";
		YearsellinfoId yearinfoId=new YearsellinfoId();
		yearinfoId.setCompid(strCurCompId);
		
		yearinfoId.setBillid(this.cc019Service.getDataTool().loadBillIdByRule(strCurCompId,"yearsellinfo", "billid", "SP007"));
		curMaster.setId(yearinfoId);
		curMaster.setAccountdate(CommonTool.getCurrDate());
		List<Yearcarddetal> lsYearCards=this.cc019Service.getDataTool().loadDTOList(this.curjosnparam, Yearcarddetal.class);
		
		List<Yearselldetal> lsYearselldetals=this.cc019Service.changeYearsell(curMaster, lsYearCards);
		
		if(cc019Service.post(curMaster, lsYearselldetals, lsCardYears)==false)
		{
			strMessage="保存失败。请重新保存";
		}
		
		return SystemFinal.POST_SUCCESS;
	}


	public List<CardYears> getLsCardYears() {
		return lsCardYears;
	}


	public void setLsCardYears(List<CardYears> lsCardYears) {
		this.lsCardYears = lsCardYears;
	}


	@Override
	protected boolean beforePost() {
		return false;
	}

	@Override
	protected boolean afterPost() {
		return false;
	}

	@Override
	protected boolean postActive() {
		return false;
	}

	@Override
	protected boolean beforeDelete() {
		return false;
	}

	@Override
	protected boolean afterDelete() {
		return false;
	}

	@Override
	protected boolean deleteActive() {
		return false;
	}

	@Override
	protected boolean beforeLoad() {
		return false;
	}

	@Override
	protected boolean afterLoad() {
		return false;
	}

	@Override
	protected boolean loadActive() {
		return false;
	}

	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean afterAdd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean addActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
