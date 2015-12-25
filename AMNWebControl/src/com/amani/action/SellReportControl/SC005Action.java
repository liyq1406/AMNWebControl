package com.amani.action.SellReportControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


import com.amani.bean.Compclasstraderesult;
import com.amani.service.SellReportControl.SC005Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc005")
public class SC005Action {
	@Autowired
	private SC005Service sc005Service;
	private String strCurCompId;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private Compclasstraderesult curDateSet;
	private List<Compclasstraderesult> lsDataSet;
	private String butySeqno;//美容排名
	private String hairSeqno;//美发排名
	private String trhSeqno;//烫染护排名
	private String fingerSeqno;//美甲排名
	private String totalSeqno;//总排名

	public String getButySeqno() {
		return butySeqno;
	}
	public void setButySeqno(String butySeqno) {
		this.butySeqno = butySeqno;
	}
	public String getHairSeqno() {
		return hairSeqno;
	}
	public void setHairSeqno(String hairSeqno) {
		this.hairSeqno = hairSeqno;
	}
	public String getTrhSeqno() {
		return trhSeqno;
	}
	public void setTrhSeqno(String trhSeqno) {
		this.trhSeqno = trhSeqno;
	}
	public String getFingerSeqno() {
		return fingerSeqno;
	}
	public void setFingerSeqno(String fingerSeqno) {
		this.fingerSeqno = fingerSeqno;
	}
	public String getTotalSeqno() {
		return totalSeqno;
	}
	public void setTotalSeqno(String totalSeqno) {
		this.totalSeqno = totalSeqno;
	}
	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.sc005Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		this.curDateSet=this.sc005Service.loadCurDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		List ls=this.sc005Service.loadSeqNoByComp(this.strCurCompId, CommonTool.setDateMask(this.strFromDate), CommonTool.setDateMask(this.strToDate));
		if(ls==null)
		{

			  butySeqno="0";//美容排名
			  hairSeqno="0";//美发排名
			  trhSeqno="0";//烫染护排名
			  fingerSeqno="0";//美甲排名
			  totalSeqno="0";//总排名
		}
		else
		{
			butySeqno=CommonTool.FormatString(ls.get(0).toString());//美容排名
			hairSeqno=CommonTool.FormatString(ls.get(1).toString());;//美发排名
		    trhSeqno=CommonTool.FormatString(ls.get(2).toString());;//烫染护排名
			fingerSeqno=CommonTool.FormatString(ls.get(3).toString());;//美甲排名
			totalSeqno=CommonTool.FormatString(ls.get(4).toString());;//总排名
		}
		ls=null;
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
	public SC005Service getSc005Service() {
		return sc005Service;
	}
	@JSON(serialize=false)
	public void setSc005Service(SC005Service sc005Service) {
		this.sc005Service = sc005Service;
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

	public Compclasstraderesult getCurDateSet() {
		return curDateSet;
	}
	public void setCurDateSet(Compclasstraderesult curDateSet) {
		this.curDateSet = curDateSet;
	}
	public List<Compclasstraderesult> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<Compclasstraderesult> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}

	
	
}
