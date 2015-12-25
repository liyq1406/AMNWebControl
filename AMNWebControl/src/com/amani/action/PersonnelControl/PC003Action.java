package com.amani.action.PersonnelControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.Staffchangeinfo;

import com.amani.service.PersonnelControl.PC003Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc003")
public class PC003Action extends AMN_ModuleAction{
	@Autowired
	private PC003Service pc003Service;
	private String strCurCompId;
	private String strFromDate;
	private String strToDate;
	private int iSearchType;
	private int iSearchState;
	private String strCompId;
	private String strBillId;
	private int	   iChangeType;
	private String strMessage;
	private List<Staffchangeinfo> lsDataSet;
	private Staffchangeinfo curMaster; 
	private String checkEmpId;
	private String checkDate;
	private String strCurStaffNo;
	public String getStrCurStaffNo() {
		return strCurStaffNo;
	}
	public void setStrCurStaffNo(String strCurStaffNo) {
		this.strCurStaffNo = strCurStaffNo;
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
	public Staffchangeinfo getCurMaster() {
		return curMaster;
	}
	public void setCurMaster(Staffchangeinfo curMaster) {
		this.curMaster = curMaster;
	}
	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.pc003Service.loadDateSetByCompId(strCurCompId,CommonTool.setDateMask(strFromDate),CommonTool.setDateMask(strToDate),iSearchType,iSearchState,strCurStaffNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="checkBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String checkBill()
	{
		this.strMessage="";
		if(this.pc003Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX014")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		this.checkEmpId=CommonTool.getLoginInfo("USERID");
		this.checkDate=CommonTool.getCurrDate();
		boolean flag=this.pc003Service.handBill(strCompId, strBillId, iChangeType, 1);
		if(flag==false)
			this.strMessage="操作失败!";
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action( value="checkinheadBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String checkinheadBill()
	{
		this.strMessage="";
		if(this.pc003Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		this.checkEmpId=CommonTool.getLoginInfo("USERID");
		this.checkDate=CommonTool.getCurrDate();
		boolean flag=this.pc003Service.handBill(strCompId, strBillId, iChangeType, 2);
		if(flag==false)
			this.strMessage="操作失败!";
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action( value="comfrimBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimBill()
	{
		this.strMessage="";
		if(this.pc003Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		this.checkEmpId=CommonTool.getLoginInfo("USERID");
		this.checkDate=CommonTool.getCurrDate();
		boolean flag=this.pc003Service.handBill(strCompId, strBillId, iChangeType, 3);
		if(flag==false)
			this.strMessage="操作失败!";
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action( value="comfrimbackBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimbackBill()
	{
		this.strMessage="";
		if(this.pc003Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false
		 && this.pc003Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		this.checkEmpId=CommonTool.getLoginInfo("USERID");
		this.checkDate=CommonTool.getCurrDate();
		boolean flag=this.pc003Service.handBill(strCompId, strBillId, iChangeType, 5);
		if(flag==false)
			this.strMessage="操作失败!";
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action( value="comfirmValodateButton", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfirmValodateButton()
	{
		this.strMessage="";
		if(this.pc003Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		boolean flag=this.pc003Service.handValidateBill(strCompId, strBillId);
		if(flag==false)
			this.strMessage="操作失败!";
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action( value="loadMaster", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadMaster()
	{
		this.lsDataSet=this.pc003Service.loadMasterByCompId();
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

	public List<Staffchangeinfo> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<Staffchangeinfo> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}

	@JSON(serialize=false)
	public PC003Service getPc003Service() {
		return pc003Service;
	}
	@JSON(serialize=false)
	public void setPc003Service(PC003Service pc003Service) {
		this.pc003Service = pc003Service;
	}

	@Override
	protected boolean addActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterPost() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean postActive() {
		// TODO Auto-generated method stub
		return false;
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
	public int getISearchType() {
		return iSearchType;
	}
	public void setISearchType(int searchType) {
		iSearchType = searchType;
	}
	public int getISearchState() {
		return iSearchState;
	}
	public void setISearchState(int searchState) {
		iSearchState = searchState;
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
	public int getIChangeType() {
		return iChangeType;
	}
	public void setIChangeType(int changeType) {
		iChangeType = changeType;
	}

	
	
}
