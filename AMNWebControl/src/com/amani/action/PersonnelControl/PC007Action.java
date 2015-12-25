package com.amani.action.PersonnelControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.bean.StaffWorkDetial;
import com.amani.model.Staffinfo;
import com.amani.service.PersonnelControl.PC007Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc007")
public class PC007Action {
	@Autowired
	private PC007Service pc007Service;
	private String strCurCompId;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private String strFromInid;
	private String strToInid;
	private List<Staffinfo> lsStaffDataSet;
	private List<StaffWorkDetial> lsDateSet;
	//查询默认发送短信
	@Action( value="loadStaffDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadStaffDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsStaffDataSet=this.pc007Service.loadStaffDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDateSet=this.pc007Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),strFromInid,strToInid);
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

	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}
	@JSON(serialize=false)
	public PC007Service getPc007Service() {
		return pc007Service;
	}
	@JSON(serialize=false)
	public void setPc007Service(PC007Service pc007Service) {
		this.pc007Service = pc007Service;
	}
	public List<Staffinfo> getLsStaffDataSet() {
		return lsStaffDataSet;
	}
	public void setLsStaffDataSet(List<Staffinfo> lsStaffDataSet) {
		this.lsStaffDataSet = lsStaffDataSet;
	}

	public String getStrFromInid() {
		return strFromInid;
	}

	public void setStrFromInid(String strFromInid) {
		this.strFromInid = strFromInid;
	}

	public String getStrToInid() {
		return strToInid;
	}

	public void setStrToInid(String strToInid) {
		this.strToInid = strToInid;
	}

	public List<StaffWorkDetial> getLsDateSet() {
		return lsDateSet;
	}

	public void setLsDateSet(List<StaffWorkDetial> lsDateSet) {
		this.lsDateSet = lsDateSet;
	}

	
	
}
