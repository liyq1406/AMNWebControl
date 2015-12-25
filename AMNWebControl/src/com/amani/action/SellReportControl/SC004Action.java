package com.amani.action.SellReportControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


import com.amani.bean.CostAnalysisBean;
import com.amani.service.SellReportControl.SC004Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc004")
public class SC004Action {
	@Autowired
	private SC004Service sc004Service;
	private String strCurCompId;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<CostAnalysisBean> lsDataSetA;
	private List<CostAnalysisBean> lsDataSetB;
	private List<CostAnalysisBean> lsDataSetC;
	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSetA=this.sc004Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate), "1");
		this.lsDataSetB=this.sc004Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate), "2");
		this.lsDataSetC=this.sc004Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate), "3");
		return SystemFinal.LOAD_SUCCESS;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	@JSON(serialize=false)
	public SC004Service getSc004Service() {
		return sc004Service;
	}
	@JSON(serialize=false)
	public void setSc004Service(SC004Service sc004Service) {
		this.sc004Service = sc004Service;
	}
	public String getStrFromDate() {
		return strFromDate;
	}
	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}
	public String getStrToDate() {
		return strToDate;
	}
	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}
	public List<CostAnalysisBean> getLsDataSetA() {
		return lsDataSetA;
	}
	public void setLsDataSetA(List<CostAnalysisBean> lsDataSetA) {
		this.lsDataSetA = lsDataSetA;
	}
	public List<CostAnalysisBean> getLsDataSetB() {
		return lsDataSetB;
	}
	public void setLsDataSetB(List<CostAnalysisBean> lsDataSetB) {
		this.lsDataSetB = lsDataSetB;
	}
	public List<CostAnalysisBean> getLsDataSetC() {
		return lsDataSetC;
	}
	public void setLsDataSetC(List<CostAnalysisBean> lsDataSetC) {
		this.lsDataSetC = lsDataSetC;
	}

	
	
}
