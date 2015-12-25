package com.amani.action.SellReportControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


import com.amani.bean.ProjectCostAnalysis;
import com.amani.service.SellReportControl.SC007Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc007")
public class SC007Action {
	@Autowired
	private SC007Service sc007Service;
	private String strCurCompId;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private int iBeforCount;
	private double dPrjFromCostAmt;
	private double dPrjToCostAmt;
	private double dGoodsFromCostAmt;
	private double dGoodsToCostAmt;
	private String strMessage;
	private List<ProjectCostAnalysis> lsDataSet;

	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		
		this.lsDataSet=this.sc007Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate),CommonTool.setDateMask(strToDate),
				iBeforCount,dPrjFromCostAmt,dPrjToCostAmt,dGoodsFromCostAmt,dGoodsToCostAmt);
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
	public SC007Service getSc007Service() {
		return sc007Service;
	}
	@JSON(serialize=false)
	public void setSc007Service(SC007Service sc007Service) {
		this.sc007Service = sc007Service;
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
	
	public List<ProjectCostAnalysis> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<ProjectCostAnalysis> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}
	public int getIBeforCount() {
		return iBeforCount;
	}
	public void setIBeforCount(int beforCount) {
		iBeforCount = beforCount;
	}
	public double getDPrjFromCostAmt() {
		return dPrjFromCostAmt;
	}
	public void setDPrjFromCostAmt(double prjFromCostAmt) {
		dPrjFromCostAmt = prjFromCostAmt;
	}
	public double getDPrjToCostAmt() {
		return dPrjToCostAmt;
	}
	public void setDPrjToCostAmt(double prjToCostAmt) {
		dPrjToCostAmt = prjToCostAmt;
	}
	public double getDGoodsFromCostAmt() {
		return dGoodsFromCostAmt;
	}
	public void setDGoodsFromCostAmt(double goodsFromCostAmt) {
		dGoodsFromCostAmt = goodsFromCostAmt;
	}
	public double getDGoodsToCostAmt() {
		return dGoodsToCostAmt;
	}
	public void setDGoodsToCostAmt(double goodsToCostAmt) {
		dGoodsToCostAmt = goodsToCostAmt;
	}

	
	
}
