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

import com.amani.model.Dstaffrewardinfo;
import com.amani.service.PersonnelControl.PC020Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc020")
public class PC020Action {
	@Autowired
	private PC020Service pc020Service;
	private String strJsonParam;
	private String strCurCompId;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private String strCompId;
	private String strBillId;
	private double entryseqno;
	private String strSearchStaffNo;
	private List<Dstaffrewardinfo> lsDataSet;
	private int billstate;
	private String strSearchType;
	private OutputStream os;
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}

	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		this.lsDataSet=this.pc020Service.loadDStaffrewardinfos(this.strCurCompId, CommonTool.setDateMask(this.strFromDate), CommonTool.setDateMask(this.strToDate),billstate,strSearchType,strSearchStaffNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action( value="comfrimBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimBill()
	{
		this.strMessage="";
		if(this.pc020Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDataSet=this.pc020Service.getDataTool().loadDTOList(strJsonParam, Dstaffrewardinfo.class);
			if(this.lsDataSet!=null && this.lsDataSet.size()>0)
		    for(int i=0;i<this.lsDataSet.size();i++)
		    {
		    	//员工处罚
		    	if(CommonTool.FormatString(this.lsDataSet.get(i).getEntrytype()).equals("01")
		    	|| CommonTool.FormatString(this.lsDataSet.get(i).getEntrytype()).equals("02")
		    	|| CommonTool.FormatString(this.lsDataSet.get(i).getEntrytype()).equals("04")
		    	|| CommonTool.FormatString(this.lsDataSet.get(i).getEntrytype()).equals("05")
		    	|| CommonTool.FormatString(this.lsDataSet.get(i).getEntrytype()).equals("07")
		    	|| CommonTool.FormatString(this.lsDataSet.get(i).getEntrytype()).equals("08")
		    	|| CommonTool.FormatString(this.lsDataSet.get(i).getEntrytype()).equals("09")
		    	|| CommonTool.FormatString(this.lsDataSet.get(i).getEntrytype()).equals("12")
		    	|| CommonTool.FormatString(this.lsDataSet.get(i).getEntrytype()).equals("15")
		    	|| ( CommonTool.FormatString(this.lsDataSet.get(i).getEntrytype()).equals("10")
		    	  && CommonTool.FormatInteger(this.lsDataSet.get(i).getEntryflag())==1) )
		    	{
		    		boolean flag=this.pc020Service.validateStaffDeb(this.lsDataSet.get(i).getHandcompid(), this.lsDataSet.get(i).getHandstaffinid(), this.lsDataSet.get(i).getEntrydate(), CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getRewardamt()).doubleValue());
		    		if(flag==false)
		    		{
		    			strMessage=strMessage+" 门店 "+this.lsDataSet.get(i).getHandcompid()+" 员工 "+this.lsDataSet.get(i).getHandstaffname()+" 罚款金额超过当月总提成;";
		    			this.lsDataSet.remove(i);
		    			i--;
		    		}
		    	}
		    }
			boolean flag=this.pc020Service.handLsBill(lsDataSet,1);
			if(flag==false)
				this.strMessage="操作失败!";
		}
		
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action( value="comfrimbackBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimbackBill()
	{
		this.strMessage="";
		if(this.pc020Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDataSet=this.pc020Service.getDataTool().loadDTOList(strJsonParam, Dstaffrewardinfo.class);
			boolean flag=this.pc020Service.handLsBill(lsDataSet,2);
			if(flag==false)
				this.strMessage="操作失败!";
		}
		return SystemFinal.POST_SUCCESS;
	}
	

	@Action( value="comfrimstopBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimstopBill()
	{
		this.strMessage="";
		if(this.pc020Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX025")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
//		boolean flag=this.pc018Service.handBill(strCompId, strBillId, 2);
//		if(flag==false)
//			this.strMessage="操作失败!";
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDataSet=this.pc020Service.getDataTool().loadDTOList(strJsonParam, Dstaffrewardinfo.class);
			boolean flag=this.pc020Service.handLsBill(lsDataSet,3);
			if(flag==false)
				this.strMessage="操作失败!";
		}
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
	

	@JSON(serialize=false)
	public PC020Service getPc020Service() {
		return pc020Service;
	}
	@JSON(serialize=false)
	public void setPc020Service(PC020Service pc020Service) {
		this.pc020Service = pc020Service;
	}
	
	public int getBillstate() {
		return billstate;
	}
	public void setBillstate(int billstate) {
		this.billstate = billstate;
	}
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}
	public String getStrBillId() {
		return strBillId;
	}
	public void setStrBillId(String strBillId) {
		this.strBillId = strBillId;
	}
	public List<Dstaffrewardinfo> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<Dstaffrewardinfo> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public double getEntryseqno() {
		return entryseqno;
	}
	public void setEntryseqno(double entryseqno) {
		this.entryseqno = entryseqno;
	}
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}
	public String getStrSearchStaffNo() {
		return strSearchStaffNo;
	}
	public void setStrSearchStaffNo(String strSearchStaffNo) {
		this.strSearchStaffNo = strSearchStaffNo;
	}

	
	
}
