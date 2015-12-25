package com.amani.action.AdvancedOperations;
import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.bean.CompAmtAnlysisBean;
import com.amani.bean.CompLogAnlysisBean;
import com.amani.service.AdvancedOperations.AC005Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac005")
public class AC005Action {
	@Autowired
	private AC005Service ac005Service;
	private String strCurCompId;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private String analysisFlag;
	private String closeAccountFlag;
	private CompAmtAnlysisBean compAmtAnlysisBean;
    private List<CompLogAnlysisBean> lsLogDate;
	//查询默认发送短信
	@Action( value="handCompAnlysis", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String handCompAnlysis() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		this.compAmtAnlysisBean=this.ac005Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strFromDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	
	//单据分析
	@Action( value="analysisExceptionBills", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String analysisExceptionBills() {

		this.lsLogDate = this.ac005Service.analysisExceptionBills(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strFromDate));
		if(this.lsLogDate == null)
		{
			this.analysisFlag = "EXCEPTION";
			return SystemFinal.LOAD_FAILURE;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="handOverDayAnlysis", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String handOverDayAnlysis()
	{
		this.strMessage="";
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(Integer.parseInt(this.ac005Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP016"))==1)
		{
			if(ac005Service.checkMgoodsreceipt(strCurCompId)==false)
			{
				strMessage="你上个星期还有收货单还没有处理。请处理了在来结算";
				return SystemFinal.LOAD_FAILURE;
			}
		}
		closeAccountFlag=this.ac005Service.closeAccountLog(strCurCompId, CommonTool.setDateMask(strFromDate));
		return SystemFinal.LOAD_FAILURE;
	}
	
	@Action( value="closeAccountForcibly", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	
	public String closeAccountForcibly()
	{
		this.strMessage="";
		if(this.ac005Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC005", SystemFinal.UR_SPECIAL_CHECK)==false)
		{
			this.setStrMessage("您没有强行封帐结算的权限！");
			return SystemFinal.LOAD_FAILURE;
		}
		/*if(ac005Service.checkMgoodsreceipt(strCurCompId)==false)
		{
			strMessage="你上个星期还有收货单还没有处理。请处理了在来结算";
			return SystemFinal.LOAD_FAILURE;
		}*/
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		closeAccountFlag=this.ac005Service.closeAccountLog(strCurCompId, CommonTool.setDateMask(strFromDate));
		return SystemFinal.LOAD_FAILURE;
	}
	
	
	@Action( value="uncloseAccountForcibly", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	
	public String uncloseAccountForcibly()
	{
		this.strMessage="";
		if(this.ac005Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC005", SystemFinal.UR_SPECIAL_CHECK)==false)
		{
			this.setStrMessage("您没有强行解除封帐结算的权限！");
			return SystemFinal.LOAD_FAILURE;
		}
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		boolean flag=this.ac005Service.uncloseAccountLog(strCurCompId, CommonTool.setDateMask(strFromDate));
		if(flag==false)
			this.strMessage="解除封帐失败";
		return SystemFinal.LOAD_FAILURE;
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
	
	public CompAmtAnlysisBean getCompAmtAnlysisBean() {
		return compAmtAnlysisBean;
	}
	public void setCompAmtAnlysisBean(CompAmtAnlysisBean compAmtAnlysisBean) {
		this.compAmtAnlysisBean = compAmtAnlysisBean;
	}
	@JSON(serialize=false)
	public AC005Service getAc005Service() {
		return ac005Service;
	}
	@JSON(serialize=false)
	public void setAc005Service(AC005Service ac005Service) {
		this.ac005Service = ac005Service;
	}



	public String getAnalysisFlag() {
		return analysisFlag;
	}



	public void setAnalysisFlag(String analysisFlag) {
		this.analysisFlag = analysisFlag;
	}



	public List<CompLogAnlysisBean> getLsLogDate() {
		return lsLogDate;
	}



	public void setLsLogDate(List<CompLogAnlysisBean> lsLogDate) {
		this.lsLogDate = lsLogDate;
	}



	public String getCloseAccountFlag() {
		return closeAccountFlag;
	}



	public void setCloseAccountFlag(String closeAccountFlag) {
		this.closeAccountFlag = closeAccountFlag;
	}
	
	
	
}
