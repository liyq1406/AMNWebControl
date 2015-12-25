package com.amani.action.SellReportControl;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ReportAction;
import com.amani.bean.CardSaleInfo;
import com.amani.model.Cardtypeinfo;
import com.amani.service.SellReportControl.SC016Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc016")
public class SC016Action extends AMN_ReportAction{
	private String strCompId;
	private String strFromDate;
	private String strToDate;
	private String strCardClass;
	private double fromamt;
	private double toamt;
	private List<CardSaleInfo> lsCardSaleInfos;
	public List<Cardtypeinfo> getLsCardtypeinfos() {
		return lsCardtypeinfos;
	}
	public void setLsCardtypeinfos(List<Cardtypeinfo> lsCardtypeinfos) {
		this.lsCardtypeinfos = lsCardtypeinfos;
	}
	private String strMessage;
	private List<Cardtypeinfo> lsCardtypeinfos;
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
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
	public String getStrCardClass() {
		return strCardClass;
	}
	public void setStrCardClass(String strCardClass) {
		this.strCardClass = strCardClass;
	}
	public double getFromamt() {
		return fromamt;
	}
	public void setFromamt(double fromamt) {
		this.fromamt = fromamt;
	}
	public double getToamt() {
		return toamt;
	}
	public void setToamt(double toamt) {
		this.toamt = toamt;
	}
	public List<CardSaleInfo> getLsCardSaleInfos() {
		return lsCardSaleInfos;
	}
	public void setLsCardSaleInfos(List<CardSaleInfo> lsCardSaleInfos) {
		this.lsCardSaleInfos = lsCardSaleInfos;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	public void setSc016Service(SC016Service sc016Service) {
		this.sc016Service = sc016Service;
	}
	@Autowired
	private SC016Service sc016Service;
	
	@Action( value="query", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String query()
	{
		strMessage="";
		lsCardSaleInfos=sc016Service.loadData(strCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate), strCardClass, fromamt, toamt);
		if(lsCardSaleInfos==null || lsCardSaleInfos.size()<1)
		{
			strMessage="没有查询到数据";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="load",
			results={
			@Result(name="load_success",location="/SellReportControl/SC016/index.jsp"),
			@Result(name="load_failure",location="/SellReportControl/SC016/index.jsp")
	})
	public String load()
	{
		lsCardtypeinfos=this.sc016Service.getDataTool().loadCardType(CommonTool.getLoginInfo("COMPID"));
		return SystemFinal.LOAD_SUCCESS;
	}
}
