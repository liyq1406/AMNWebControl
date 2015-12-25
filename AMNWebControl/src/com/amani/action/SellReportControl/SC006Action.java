package com.amani.action.SellReportControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


import com.amani.bean.CompTradeHTAnalysis;
import com.amani.service.SellReportControl.SC006Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc006")
public class SC006Action {
	@Autowired
	private SC006Service sc006Service;
	private String strCurCompId;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<CompTradeHTAnalysis> lsDataSet;

	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate().substring(0,6);
		else
			strFromDate=strFromDate.substring(0,4)+strFromDate.substring(5,7);
		this.lsDataSet=this.sc006Service.loadDateSetByCompId(strCurCompId, strFromDate);
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
	public SC006Service getSc006Service() {
		return sc006Service;
	}
	@JSON(serialize=false)
	public void setSc006Service(SC006Service sc006Service) {
		this.sc006Service = sc006Service;
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
	
	public List<CompTradeHTAnalysis> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<CompTradeHTAnalysis> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}

	
	
}
