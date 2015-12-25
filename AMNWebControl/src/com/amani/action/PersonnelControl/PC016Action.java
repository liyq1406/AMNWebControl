package com.amani.action.PersonnelControl;

import java.io.OutputStream;
import java.util.List;



import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.model.Staffinfodispatch;

import com.amani.service.PersonnelControl.PC016Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc016")
public class PC016Action {
	@Autowired
	private PC016Service pc016Service;
	private String strCurCompId;
	private String strCurCompName;
	private String department;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<Staffinfodispatch> lsDataSet;
	private String oldcompid;
	private String oldempid;
	private String newcompid;
	private String effectivedate;
	private String teffectivedate;
    private OutputStream os;
    private String checkEmpId;
    private String checkDate;
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.pc016Service.loadDateSetByCompId(strCurCompId,department,CommonTool.setDateMask(strFromDate),CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="checkinheadBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String checkinheadBill()
	{
		this.strMessage="";
		if(this.pc016Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		this.checkEmpId=CommonTool.getLoginInfo("USERID");
		this.checkDate=CommonTool.getCurrDate();
		boolean flag=this.pc016Service.handBill(1,this.oldcompid,this.oldempid,newcompid,this.effectivedate,this.teffectivedate);
		if(flag==false)
			this.strMessage="操作失败!";
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action( value="comfrimBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimBill()
	{
		this.strMessage="";
		if(this.pc016Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		this.checkEmpId=CommonTool.getLoginInfo("USERID");
		this.checkDate=CommonTool.getCurrDate();
		boolean flag=this.pc016Service.handBill(2,this.oldcompid,this.oldempid,newcompid,this.effectivedate,this.teffectivedate);
		if(flag==false)
			this.strMessage="操作失败!";
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action( value="comfrimbackBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimbackBill()
	{
		this.strMessage="";
		if(this.pc016Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		this.checkEmpId=CommonTool.getLoginInfo("USERID");
		this.checkDate=CommonTool.getCurrDate();
		boolean flag=this.pc016Service.handBill(3,this.oldcompid,this.oldempid,newcompid,this.effectivedate,this.teffectivedate);
		if(flag==false)
			this.strMessage="操作失败!";
		return SystemFinal.POST_SUCCESS;
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

	public List<Staffinfodispatch> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<Staffinfodispatch> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}

	@JSON(serialize=false)
	public PC016Service getPc016Service() {
		return pc016Service;
	}
	@JSON(serialize=false)
	public void setPc016Service(PC016Service pc016Service) {
		this.pc016Service = pc016Service;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
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
	public String getOldcompid() {
		return oldcompid;
	}
	public void setOldcompid(String oldcompid) {
		this.oldcompid = oldcompid;
	}
	public String getOldempid() {
		return oldempid;
	}
	public void setOldempid(String oldempid) {
		this.oldempid = oldempid;
	}
	public String getEffectivedate() {
		return effectivedate;
	}
	public void setEffectivedate(String effectivedate) {
		this.effectivedate = effectivedate;
	}
	public String getTeffectivedate() {
		return teffectivedate;
	}
	public void setTeffectivedate(String teffectivedate) {
		this.teffectivedate = teffectivedate;
	}
	public String getCheckEmpId() {
		return checkEmpId;
	}
	public void setCheckEmpId(String checkEmpId) {
		this.checkEmpId = checkEmpId;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getNewcompid() {
		return newcompid;
	}
	public void setNewcompid(String newcompid) {
		this.newcompid = newcompid;
	}

	
	
}
