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
import com.amani.model.Activitycardinfo;
import com.amani.model.Cardtypeinfo;
import com.amani.model.Nointernalcardinfo;
import com.amani.model.Projectinfo;
import com.amani.service.BaseInfoControl.BC019Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc019")
public class BC019Action {
	@Autowired
	private BC019Service bc019Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strSearchDate;
	private String strSearchCardNo;
	private String strSearchVoucherno;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private String strCompId;
	private String strBillId;
	private String strJsonParam;
	private String strSearchStaffNo;
	private List<Storetargetinfo> lsDataSet;
	private Storetargetinfo curStoretargetinfo;
	private List<Cardtypeinfo> lsCardtypeinfos;
	private List<Activitycardinfo> lsMaster;
	private int billstate;
	private String strDiYongCardNo;
	private String strDiYongValidate;
	private Projectinfo curProjectinfo;
	
	private OutputStream os;
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	
	private String 			strEntryCompId;
	private String 			strCurItemId;
	private String 			strCardPrefix;
	private String 			strFromCardNo;
	private String			strToCardNo;
	private String 			strValidate;
	private String 			strSaleCardType;
	private BigDecimal 		dCardDeductAmt;
	private String 			strProQuanNo;
	private int				sendquanflag;
	private int				createflag;
	private int				totalCount;

	private String strPrjJsonParam;
	private String strGoodsJsonParam;
	private String strRandPrjJsonParam;

	@Action( value="loadInitDate", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadInitDate() {
		this.lsCardtypeinfos=this.bc019Service.getDataTool().loadCardType(CommonTool.getLoginInfo("COMPID"));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "validateDiyongcardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateDiyongcardno()
	{
		try
		{
			this.strMessage="";
			Nointernalcardinfo record=this.bc019Service.loadNointernalcardinfo(this.strDiYongCardNo, 1);
			if(record==null )
			{
				this.strMessage="该抵用券不存在或还没有到启用期,请到系统号段查询确认!";
				return SystemFinal.LOAD_SUCCESS;
			}
			strDiYongValidate=CommonTool.getDateMask(record.getLastvalidate());
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	
	
	@Action(value = "searchData",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String searchData()
	{
		try
		{
			this.lsMaster=this.bc019Service.loadMasterInfo(strCurCompId, strSearchDate, strSearchCardNo,strSearchVoucherno);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	

	
	@Action( value="postActivitycardinfo", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String postActivitycardinfo()
	{
		this.strMessage="";
		if(this.bc019Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC019", SystemFinal.UR_POST)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		StringBuffer msgbuf=new StringBuffer();
		boolean flag=this.bc019Service.postActivityInfo(strEntryCompId, strCardPrefix, strFromCardNo, strToCardNo, strValidate, strSaleCardType, dCardDeductAmt, strProQuanNo, strPrjJsonParam, strGoodsJsonParam,strRandPrjJsonParam,sendquanflag,createflag,totalCount,msgbuf);
		if(flag==false)
		{
			this.strMessage="保存失败,请查询确认!";
			this.strMessage=this.strMessage+msgbuf.toString();
		}
		else
		{
			if(createflag==0)
				this.lsMaster=this.bc019Service.loadMasterByRandCard(strEntryCompId, strCardPrefix+strFromCardNo, strCardPrefix+strToCardNo);
			else
				this.lsMaster=this.bc019Service.loadMasterInfo(strEntryCompId, "", "",strCardPrefix);
			
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
			this.strCurCompName=this.bc019Service.getDataTool().loadCompNameById(this.strCurCompId);
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
	
	
	
	
	@Action(value = "validateItem",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateItem()
	{
		try
		{
			if(strEntryCompId.equals("001"))
				strEntryCompId="0010102";
			this.curProjectinfo=this.bc019Service.getDataTool().loadProjectInfo(strEntryCompId, this.strCurItemId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
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
	public BC019Service getBc019Service() {
		return bc019Service;
	}

	@JSON(serialize=false)
	public void setBc019Service(BC019Service bc019Service) {
		this.bc019Service = bc019Service;
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
	public List<Cardtypeinfo> getLsCardtypeinfos() {
		return lsCardtypeinfos;
	}
	public void setLsCardtypeinfos(List<Cardtypeinfo> lsCardtypeinfos) {
		this.lsCardtypeinfos = lsCardtypeinfos;
	}
	public String getStrDiYongCardNo() {
		return strDiYongCardNo;
	}
	public void setStrDiYongCardNo(String strDiYongCardNo) {
		this.strDiYongCardNo = strDiYongCardNo;
	}
	public String getStrDiYongValidate() {
		return strDiYongValidate;
	}
	public void setStrDiYongValidate(String strDiYongValidate) {
		this.strDiYongValidate = strDiYongValidate;
	}
	public String getStrPrjJsonParam() {
		return strPrjJsonParam;
	}
	public void setStrPrjJsonParam(String strPrjJsonParam) {
		this.strPrjJsonParam = strPrjJsonParam;
	}
	public String getStrGoodsJsonParam() {
		return strGoodsJsonParam;
	}
	public void setStrGoodsJsonParam(String strGoodsJsonParam) {
		this.strGoodsJsonParam = strGoodsJsonParam;
	}
	public String getStrEntryCompId() {
		return strEntryCompId;
	}
	public void setStrEntryCompId(String strEntryCompId) {
		this.strEntryCompId = strEntryCompId;
	}
	public String getStrFromCardNo() {
		return strFromCardNo;
	}
	public void setStrFromCardNo(String strFromCardNo) {
		this.strFromCardNo = strFromCardNo;
	}
	public String getStrToCardNo() {
		return strToCardNo;
	}
	public void setStrToCardNo(String strToCardNo) {
		this.strToCardNo = strToCardNo;
	}
	public String getStrValidate() {
		return strValidate;
	}
	public void setStrValidate(String strValidate) {
		this.strValidate = strValidate;
	}
	public String getStrSaleCardType() {
		return strSaleCardType;
	}
	public void setStrSaleCardType(String strSaleCardType) {
		this.strSaleCardType = strSaleCardType;
	}
	public BigDecimal getDCardDeductAmt() {
		return dCardDeductAmt;
	}
	public void setDCardDeductAmt(BigDecimal cardDeductAmt) {
		dCardDeductAmt = cardDeductAmt;
	}
	public String getStrProQuanNo() {
		return strProQuanNo;
	}
	public void setStrProQuanNo(String strProQuanNo) {
		this.strProQuanNo = strProQuanNo;
	}
	public String getStrCardPrefix() {
		return strCardPrefix;
	}
	public void setStrCardPrefix(String strCardPrefix) {
		this.strCardPrefix = strCardPrefix;
	}
	public List<Activitycardinfo> getLsMaster() {
		return lsMaster;
	}
	public void setLsMaster(List<Activitycardinfo> lsMaster) {
		this.lsMaster = lsMaster;
	}
	public String getStrSearchDate() {
		return strSearchDate;
	}
	public void setStrSearchDate(String strSearchDate) {
		this.strSearchDate = strSearchDate;
	}
	public String getStrSearchCardNo() {
		return strSearchCardNo;
	}
	public void setStrSearchCardNo(String strSearchCardNo) {
		this.strSearchCardNo = strSearchCardNo;
	}
	public int getSendquanflag() {
		return sendquanflag;
	}
	public void setSendquanflag(int sendquanflag) {
		this.sendquanflag = sendquanflag;
	}
	public String getStrCurItemId() {
		return strCurItemId;
	}
	public void setStrCurItemId(String strCurItemId) {
		this.strCurItemId = strCurItemId;
	}
	public Projectinfo getCurProjectinfo() {
		return curProjectinfo;
	}
	public void setCurProjectinfo(Projectinfo curProjectinfo) {
		this.curProjectinfo = curProjectinfo;
	}
	public String getStrRandPrjJsonParam() {
		return strRandPrjJsonParam;
	}
	public void setStrRandPrjJsonParam(String strRandPrjJsonParam) {
		this.strRandPrjJsonParam = strRandPrjJsonParam;
	}
	public String getStrSearchVoucherno() {
		return strSearchVoucherno;
	}
	public void setStrSearchVoucherno(String strSearchVoucherno) {
		this.strSearchVoucherno = strSearchVoucherno;
	}
	public int getCreateflag() {
		return createflag;
	}
	public void setCreateflag(int createflag) {
		this.createflag = createflag;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
		
	
}
