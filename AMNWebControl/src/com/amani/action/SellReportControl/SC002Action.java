package com.amani.action.SellReportControl;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.bean.TradeBillCount;
import com.amani.bean.Tradedailydata;
import com.amani.bean.Tradedatedata;
import com.amani.service.SellReportControl.SC002Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc002")
public class SC002Action {
	@Autowired
	private SC002Service sc002Service;
	private String strCurCompId;
	private String strSearchType;
	private String strFromDate; 
	private String strPrintDate;
	private String strMessage;
	private List<Tradedailydata> 	lsTradedailydata;
	private List<Tradedatedata>  	lsTradedatedata;
	private List<TradeBillCount>	lsTradeBillCount;
	private List<Map<String,Object>> lsThreePayment;
	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(CommonTool.setDateMask(strFromDate).equals(""))
			strFromDate=CommonTool.getCurrDate();
		strPrintDate=CommonTool.getDateMask(CommonTool.getCurrDate());
		this.lsTradedailydata=this.sc002Service.loadTradedailydatas(strCurCompId, CommonTool.setDateMask(strFromDate));
		this.lsThreePayment=this.sc002Service.loadThreePayment(strCurCompId, CommonTool.setDateMask(strFromDate));
		String strFromDate_start=CommonTool.setDateMask(strFromDate).substring(0,6)+"01";
		this.lsTradedatedata=this.sc002Service.loadTradedatedatas(strCurCompId,CommonTool.setDateMask(strFromDate_start), CommonTool.setDateMask(strFromDate));
		//this.lsTradeBillCount=this.sc002Service.loadTradeBillCount(strCurCompId,CommonTool.setDateMask(strFromDate_start), CommonTool.setDateMask(strFromDate));
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
	
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}
	@JSON(serialize=false)
	public SC002Service getSc002Service() {
		return sc002Service;
	}
	@JSON(serialize=false)
	public void setSc002Service(SC002Service sc002Service) {
		this.sc002Service = sc002Service;
	}
	public List<Tradedailydata> getLsTradedailydata() {
		return lsTradedailydata;
	}
	public void setLsTradedailydata(List<Tradedailydata> lsTradedailydata) {
		this.lsTradedailydata = lsTradedailydata;
	}
	
	public List<Tradedatedata> getLsTradedatedata() {
		return lsTradedatedata;
	}
	public void setLsTradedatedata(List<Tradedatedata> lsTradedatedata) {
		this.lsTradedatedata = lsTradedatedata;
	}
	public List<TradeBillCount> getLsTradeBillCount() {
		return lsTradeBillCount;
	}
	public void setLsTradeBillCount(List<TradeBillCount> lsTradeBillCount) {
		this.lsTradeBillCount = lsTradeBillCount;
	}
	public String getStrPrintDate() {
		return strPrintDate;
	}
	public void setStrPrintDate(String strPrintDate) {
		this.strPrintDate = strPrintDate;
	}
	public List<Map<String, Object>> getLsThreePayment() {
		return lsThreePayment;
	}
	public void setLsThreePayment(List<Map<String, Object>> lsThreePayment) {
		this.lsThreePayment = lsThreePayment;
	}

	
	
}
