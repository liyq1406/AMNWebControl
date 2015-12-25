package com.amani.action.SellReportControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;



import com.amani.bean.ProjectTypeAnalysis;
import com.amani.service.SellReportControl.SC009Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc009")
public class SC009Action {
	@Autowired
	private SC009Service sc009Service;
	private String strCurCompId;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<ProjectTypeAnalysis> lsDataSet;

	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.sc009Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
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
	public SC009Service getSc009Service() {
		return sc009Service;
	}
	@JSON(serialize=false)
	public void setSc009Service(SC009Service sc009Service) {
		this.sc009Service = sc009Service;
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

	public List<ProjectTypeAnalysis> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<ProjectTypeAnalysis> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}

	
	
}
