package com.amani.action.BaseInfoControl;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.bean.Storetargetinfo;
import com.amani.service.BaseInfoControl.BC016Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc016")
public class BC016Action {
	@Autowired
	private BC016Service bc016Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private String strCompId;
	private String strBillId;
	private String strJsonParam;
	private String strSearchStaffNo;
	private List<Storetargetinfo> lsDataSet;
	private Storetargetinfo curStoretargetinfo;
	private int billstate;
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
		
		this.lsDataSet=this.bc016Service.loadDateSetByCompId(strCurCompId, CommonTool.FormatString(strFromDate),billstate);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action( value="loadCurData", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadCurData() {
		
		this.curStoretargetinfo=this.bc016Service.addSubsidyMastRecord();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="comfrimBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimBill()
	{
		this.strMessage="";
		if(this.bc016Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDataSet=this.bc016Service.getDataTool().loadDTOList(strJsonParam, Storetargetinfo.class);
			boolean flag=this.bc016Service.handLsBill(lsDataSet,1);
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
		if(this.bc016Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDataSet=this.bc016Service.getDataTool().loadDTOList(strJsonParam, Storetargetinfo.class);
			boolean flag=this.bc016Service.handLsBill(lsDataSet,2);
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
		if(this.bc016Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX025")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDataSet=this.bc016Service.getDataTool().loadDTOList(strJsonParam, Storetargetinfo.class);
			boolean flag=this.bc016Service.handLsBill(lsDataSet,3);
			if(flag==false)
				this.strMessage="操作失败!";
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	
	@Action(value = "validateShandcompid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateShandcompid()
	{
		try
		{
			this.strMessage="";
			this.strCurCompName=this.bc016Service.getDataTool().loadCompNameById(this.strCurCompId);
			if(CommonTool.FormatString(this.strCurCompName).equals(""))
			{
				this.strMessage="门店编号不存在!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "postTargetInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
         @Result(name = "post_failure", type = "json")	
      }) 
	public String postTargetInfo()
	{
		try
		{
			this.strMessage="";
			List<Storetargetinfo> lsStoretargetinfo=null;
			Storetargetinfo record=null;
			if(!CommonTool.FormatString(this.strJsonParam).equals("") && strJsonParam.indexOf("[")>-1)
			{
				lsStoretargetinfo=this.bc016Service.getDataTool().loadDTOList(this.strJsonParam, Storetargetinfo.class);
				if(lsStoretargetinfo!=null && lsStoretargetinfo.size()>0)
				{
					for(int i=0;i<lsStoretargetinfo.size();i++)
					{
						lsStoretargetinfo.get(i).setEntrycompid(CommonTool.getLoginInfo("COMPID"));
						lsStoretargetinfo.get(i).setTargetflag(0);
						lsStoretargetinfo.get(i).setTargetmonth(CommonTool.FormatString(lsStoretargetinfo.get(i).getTargetmonth().replace("-", "")));
						lsStoretargetinfo.get(i).setOperationer(CommonTool.getLoginInfo("USERID"));
						lsStoretargetinfo.get(i).setOperationdate(CommonTool.getCurrDate());
					}
				}
			}
			this.bc016Service.postTarget(lsStoretargetinfo);
			lsStoretargetinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	@Action(value = "addTarget",  results = { 
			 @Result(name = "add_success", type = "json"),
        @Result(name = "add_failure", type = "json")	
   }) 
	public String addTarget()
	{
		try
		{
			this.curStoretargetinfo=this.bc016Service.addSubsidyMastRecord();
			return SystemFinal.ADD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.ADD_FAILURE;
		}
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
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}
	public String getStrSearchStaffNo() {
		return strSearchStaffNo;
	}
	public void setStrSearchStaffNo(String strSearchStaffNo) {
		this.strSearchStaffNo = strSearchStaffNo;
	}

	@JSON(serialize=false)
	public BC016Service getBc016Service() {
		return bc016Service;
	}

	@JSON(serialize=false)
	public void setBc016Service(BC016Service bc016Service) {
		this.bc016Service = bc016Service;
	}
	public List<Storetargetinfo> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<Storetargetinfo> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public Storetargetinfo getCurStoretargetinfo() {
		return curStoretargetinfo;
	}
	public void setCurStoretargetinfo(Storetargetinfo curStoretargetinfo) {
		this.curStoretargetinfo = curStoretargetinfo;
	}
	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
	}

	
	
}
