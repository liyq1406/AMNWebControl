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
import com.amani.service.SellReportControl.SC003Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc003")
public class SC003Action {
	@Autowired
	private SC003Service sc003Service;
	private String strCurCompId;
	private String strFromDate; 
	private String strToDate;
	private String strProjectNo;
	private String strGoodsNo;
	private String strCardNo;
	private String strPayMode;
	private String strMessage;
	private List<CostAnalysisBean> lsDataSet;
	
	public List<CostAnalysisBean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<CostAnalysisBean> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(CommonTool.setDateMask(strFromDate).equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(CommonTool.setDateMask(strToDate).equals(""))
			strToDate=CommonTool.getCurrDate();
		lsDataSet=this.sc003Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate), strProjectNo, strGoodsNo, strCardNo, strPayMode);
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

	
	public String getStrFromDate() {
		return strFromDate;
	}
	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}
	
	@JSON(serialize=false)
	public SC003Service getSc003Service() {
		return sc003Service;
	}
	@JSON(serialize=false)
	public void setSc003Service(SC003Service sc003Service) {
		this.sc003Service = sc003Service;
	}
	public String getStrToDate() {
		return strToDate;
	}
	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}
	public String getStrProjectNo() {
		return strProjectNo;
	}
	public void setStrProjectNo(String strProjectNo) {
		this.strProjectNo = strProjectNo;
	}
	public String getStrGoodsNo() {
		return strGoodsNo;
	}
	public void setStrGoodsNo(String strGoodsNo) {
		this.strGoodsNo = strGoodsNo;
	}
	public String getStrCardNo() {
		return strCardNo;
	}
	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}
	public String getStrPayMode() {
		return strPayMode;
	}
	public void setStrPayMode(String strPayMode) {
		this.strPayMode = strPayMode;
	}


	
	
}
