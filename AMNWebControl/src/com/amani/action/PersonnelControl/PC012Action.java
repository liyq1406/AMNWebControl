package com.amani.action.PersonnelControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


import com.amani.bean.PC012Bean;
import com.amani.service.PersonnelControl.PC012Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc012")
public class PC012Action {
	@Autowired
	private PC012Service pc012Service;
	private String strCurCompId;
	private int strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<PC012Bean> lsDataSet;

	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.pc012Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),strSearchType);
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
	public String getStrToDate() {
		return strToDate;
	}
	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}
	public List<PC012Bean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<PC012Bean> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	
	public int getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(int strSearchType) {
		this.strSearchType = strSearchType;
	}
	@JSON(serialize=false)
	public PC012Service getPc012Service() {
		return pc012Service;
	}
	@JSON(serialize=false)
	public void setPc012Service(PC012Service pc012Service) {
		this.pc012Service = pc012Service;
	}

	
	
}
