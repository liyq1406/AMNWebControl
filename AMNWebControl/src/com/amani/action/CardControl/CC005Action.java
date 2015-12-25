package com.amani.action.CardControl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;


import com.amani.model.Cardaccount;
import com.amani.model.Cardaccountchangehistory;
import com.amani.model.Cardchangehistory;
import com.amani.model.Cardinfo;
import com.amani.model.Cardproaccount;
import com.amani.model.Cardsoninfo;
import com.amani.model.Cardspecialcost;
import com.amani.model.Cardtransactionhistory;
import com.amani.model.Memberinfo;
import com.amani.model.Sendpointcard;


import com.amani.service.CardControl.CC005Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc005")
public class CC005Action extends AMN_ModuleAction{
	@Autowired
	private CC005Service cc005Service;
	private String strJsonParam;	
	private String strCurCompId;
	
	private String strBillNo;
	private String strBillType;
	private String strCurCardNo;
	private String strAccountType;
	private BigDecimal	oldAccountBalance;
	private BigDecimal	newAccountBalance;
	private BigDecimal	oldAccountDebts;
	private BigDecimal	newAccountDebts;
	private String 		strChangeMark;
    private Cardinfo curCardinfo;
    private List<Cardinfo> lsCardinfos;
    private List<Cardaccount> lsCardaccount;
    private List<Cardproaccount> lsCardproaccount;
    private List<Cardchangehistory> lsCardchangehistory;
    private List<Cardaccountchangehistory> lsCardaccountchangehistory;
    private List<Cardtransactionhistory> lsCardtransactionhistory;
    private List<Sendpointcard> lsSendpointcard;
    private List<Cardsoninfo> lsCardsoninfo;
    private String searchMemberCompIdKey;
    private String searchMemberNoKey;
    private String searchMemberNameKey;
    private String searchMemberPhoneKey;
    private String searchMemberPCIDKey;
    private int searcharDataType;
    
    private String newHomeCompId;
    private String newHomeCompName;
    private String oldHomeCompId;
    
    private String newCardTypeId;
    private String newCardTypeName;
    private String oldCardTypeId;
    private Cardspecialcost curCardspecialcost;
    //洗剪吹特价
    private String cardvesting;
    private BigDecimal costxc1;
    private BigDecimal costxc2;
    private BigDecimal costxc3;
    private BigDecimal costxc4;
    private BigDecimal costxc5;
    private BigDecimal costxc6;
    private BigDecimal costxc7;
    private BigDecimal costxc8;
    private BigDecimal costxc9;
    
    //老疗程兑换
    private String oldProItemNo;
    private String oldpromark;
    private BigDecimal oldprocount;
    private BigDecimal oldproamt;
    
    //储值兑换
    private String curChangeProNo;
    private String curChangeProName;
    private String curChangeSaler1;
    private String curChangeSaler2;
    private double curChangeSeqnoNo;
    private BigDecimal curChangeLastCount;
    private BigDecimal curChangeLastAmt;
    private BigDecimal curChangeSaler1Amt;
    private BigDecimal curChangeSaler2Amt;
    
    private String newCardState;
    
    //加载十周年验证码
    private String code;
    
	public String getNewCardState() {
		return newCardState;
	}
	public void setNewCardState(String newCardState) {
		this.newCardState = newCardState;
	}
	public String getCurChangeProNo() {
		return curChangeProNo;
	}
	public void setCurChangeProNo(String curChangeProNo) {
		this.curChangeProNo = curChangeProNo;
	}
	public String getCurChangeSaler1() {
		return curChangeSaler1;
	}
	public void setCurChangeSaler1(String curChangeSaler1) {
		this.curChangeSaler1 = curChangeSaler1;
	}
	public String getCurChangeSaler2() {
		return curChangeSaler2;
	}
	public void setCurChangeSaler2(String curChangeSaler2) {
		this.curChangeSaler2 = curChangeSaler2;
	}
	public double getCurChangeSeqnoNo() {
		return curChangeSeqnoNo;
	}
	public void setCurChangeSeqnoNo(double curChangeSeqnoNo) {
		this.curChangeSeqnoNo = curChangeSeqnoNo;
	}
	public BigDecimal getCurChangeLastCount() {
		return curChangeLastCount;
	}
	public void setCurChangeLastCount(BigDecimal curChangeLastCount) {
		this.curChangeLastCount = curChangeLastCount;
	}
	
	public BigDecimal getCurChangeSaler1Amt() {
		return curChangeSaler1Amt;
	}
	public void setCurChangeSaler1Amt(BigDecimal curChangeSaler1Amt) {
		this.curChangeSaler1Amt = curChangeSaler1Amt;
	}
	public BigDecimal getCurChangeSaler2Amt() {
		return curChangeSaler2Amt;
	}
	public void setCurChangeSaler2Amt(BigDecimal curChangeSaler2Amt) {
		this.curChangeSaler2Amt = curChangeSaler2Amt;
	}
	public String getNewCardTypeId() {
		return newCardTypeId;
	}
	public void setNewCardTypeId(String newCardTypeId) {
		this.newCardTypeId = newCardTypeId;
	}
	public String getNewCardTypeName() {
		return newCardTypeName;
	}
	public void setNewCardTypeName(String newCardTypeName) {
		this.newCardTypeName = newCardTypeName;
	}
	public String getOldCardTypeId() {
		return oldCardTypeId;
	}
	public void setOldCardTypeId(String oldCardTypeId) {
		this.oldCardTypeId = oldCardTypeId;
	}
	public String getOldHomeCompId() {
		return oldHomeCompId;
	}
	public void setOldHomeCompId(String oldHomeCompId) {
		this.oldHomeCompId = oldHomeCompId;
	}
	public String getNewHomeCompId() {
		return newHomeCompId;
	}
	public void setNewHomeCompId(String newHomeCompId) {
		this.newHomeCompId = newHomeCompId;
	}
	public String getNewHomeCompName() {
		return newHomeCompName;
	}
	public void setNewHomeCompName(String newHomeCompName) {
		this.newHomeCompName = newHomeCompName;
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
		return true;
	}
	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return true;
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
	
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}

	
	@Override
	public String getStrMessage() {
		// TODO Auto-generated method stub
		return super.getStrMessage();
	}
	@Override
	public void setStrMessage(String strMessage) {
		// TODO Auto-generated method stub
		super.setStrMessage(strMessage);
	}
	@Action(value = "loadCardinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
     @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCardinfo()
	{
		this.lsCardinfos=this.cc005Service.loadCardinfo(CommonTool.getLoginInfo("COMPID"));
		if(this.cc005Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC005", SystemFinal.UR_SPECIAL_CHECK)==false)
		{
			if(this.lsCardinfos!=null && this.lsCardinfos.size()>0)
			{
				for(int i=0;i<this.lsCardinfos.size();i++)
				{
					if(!CommonTool.FormatString(lsCardinfos.get(i).getMemberphone()).equals("") && CommonTool.FormatString(lsCardinfos.get(i).getMemberphone()).length()>7)
					{
						lsCardinfos.get(i).setMemberphone(lsCardinfos.get(i).getMemberphone().substring(0,3)+"****"+lsCardinfos.get(i).getMemberphone().substring(lsCardinfos.get(i).getMemberphone().length()-4,lsCardinfos.get(i).getMemberphone().length()));
					}
					if(!CommonTool.FormatString(lsCardinfos.get(i).getMembername()).equals(""))
					{
						lsCardinfos.get(i).setMembername(lsCardinfos.get(i).getMembername().substring(0,1)+"**");
					}
				}
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	@Action(value = "loadCurCardInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurCardInfo()
	{
		if(CommonTool.FormatInteger(searcharDataType)!=2)
		{
			this.curCardinfo=this.cc005Service.loadCardinfoByCardNo(this.strCurCompId, this.strCurCardNo);
			this.lsCardaccount=this.cc005Service.loadCardaccountInfo(strCurCardNo);
			this.lsCardproaccount=this.cc005Service.getDataTool().loadAllProInfosByCardNo(this.strCurCompId, this.strCurCardNo);
			lsCardchangehistory=this.cc005Service.loadCardchangehistory(strCurCardNo);
			lsCardaccountchangehistory=this.cc005Service.loadCardaccountchangehistory(strCurCardNo);
			lsSendpointcard=this.cc005Service.loadSendInfo(strCurCardNo);
			lsCardsoninfo=this.cc005Service.loadCardsoninfoInfoByCard(strCurCardNo);
			this.curCardspecialcost=this.cc005Service.loadSpecialPrice(strCurCardNo);
		}
		else
		{
			this.curCardinfo=this.cc005Service.getDataTool().loadCardinfoByGcm01(this.strCurCompId, this.strCurCardNo);
			this.lsCardaccount=this.cc005Service.getDataTool().loadGcm03(this.strCurCompId, this.strCurCardNo);
			this.lsCardproaccount=this.cc005Service.getDataTool().loadGcm06(this.strCurCompId, this.strCurCardNo);
			lsCardchangehistory=this.cc005Service.getDataTool().loadGcm02(strCurCardNo);
			lsCardaccountchangehistory=this.cc005Service.getDataTool().loadGcm04(strCurCardNo);
		}
		if(this.cc005Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC005", SystemFinal.UR_SPECIAL_CHECK)==false)
		{
			if(curCardinfo!=null)
			{
				if(!CommonTool.FormatString(curCardinfo.getMembername()).equals(""))
				{
					curCardinfo.setMembername(curCardinfo.getMembername().substring(0,1)+"**");
				}
				if(!CommonTool.FormatString(curCardinfo.getMemberphone()).equals("") && CommonTool.FormatString(curCardinfo.getMemberphone()).length()>7)
				{
					curCardinfo.setMemberphone(curCardinfo.getMemberphone().substring(0,3)+"****"+curCardinfo.getMemberphone().substring(curCardinfo.getMemberphone().length()-4,curCardinfo.getMemberphone().length()));
				}
				if(!CommonTool.FormatString(curCardinfo.getMemberpcid()).equals("") && CommonTool.FormatString(curCardinfo.getMemberpcid()).length()>7)
				{
					curCardinfo.setMemberpcid(curCardinfo.getMemberpcid().substring(0,4)+"****"+curCardinfo.getMemberpcid().substring(curCardinfo.getMemberpcid().length()-4,curCardinfo.getMemberpcid().length()));
				}
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	@Action(value = "loadTransactionhistory",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String loadTransactionhistory()
	{
		if(CommonTool.FormatInteger(searcharDataType)!=2)
		{
			lsCardtransactionhistory=this.cc005Service.loadTransHistoryByCardNo(this.strCurCardNo, this.strBillType, this.strBillNo, this.strAccountType);
		}
		else
		{
			lsCardtransactionhistory=this.cc005Service.getDataTool().loadGcm20(this.strCurCardNo, this.strBillType, this.strBillNo, this.strAccountType);
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "searchCardinfos",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String searchCardinfos()
	{
		if(CommonTool.FormatInteger(searcharDataType)!=2)
		{
			this.lsCardinfos=this.cc005Service.getDataTool().searchCardinfo(searchMemberCompIdKey, searchMemberNoKey, searchMemberNameKey, searchMemberPhoneKey, searchMemberPCIDKey);
		}
		else
		{
			this.lsCardinfos=this.cc005Service.getDataTool().searchCard(searchMemberCompIdKey, searchMemberNoKey, searchMemberNameKey, searchMemberPhoneKey, searchMemberPCIDKey);
		}
		if(this.cc005Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC005", SystemFinal.UR_SPECIAL_CHECK)==false)
		{
			if(lsCardinfos!=null && lsCardinfos.size()>0)
			{
				for(int i=0;i<lsCardinfos.size();i++)
				{
					if(!CommonTool.FormatString(lsCardinfos.get(i).getMembername()).equals(""))
					{
						lsCardinfos.get(i).setMembername(lsCardinfos.get(i).getMembername().substring(0,1)+"**");
					}
					if(!CommonTool.FormatString(lsCardinfos.get(i).getMemberphone()).equals("") && CommonTool.FormatString(lsCardinfos.get(i).getMemberphone()).length()>7)
					{
						lsCardinfos.get(i).setMemberphone(lsCardinfos.get(i).getMemberphone().substring(0,3)+"****"+lsCardinfos.get(i).getMemberphone().substring(lsCardinfos.get(i).getMemberphone().length()-4,lsCardinfos.get(i).getMemberphone().length()));
					}
				}
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "changeOldProInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String changeOldProInfo()
	{
		this.strMessage="";
		StringBuffer strMsbBuf=new StringBuffer();
		boolean flag=this.cc005Service.postChangeOldProAmt(this.cardvesting, this.strCurCardNo, this.oldProItemNo,this.oldprocount,this.oldproamt,this.oldpromark, strMsbBuf);
		if(flag==false)
			this.strMessage=strMsbBuf.toString();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//验证归属门店
	@Action(value = "validateCompId",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validateCompId()
	{
		StringBuffer strMsbBuf=new StringBuffer();
		this.newHomeCompName=this.cc005Service.getDataTool().loadCompName("", this.newHomeCompId, 0, strMsbBuf);
		this.strMessage=strMsbBuf.toString();
		return SystemFinal.LOAD_SUCCESS;
	}
	

	    
	    //储值兑换
	    @Action(value = "changeProAccountinfo",  results = { 
				 @Result(name = "load_success", type = "json"),
				 @Result(name = "load_failure", type = "json")	
		}) 
		public String changeProAccountinfo()
		{
			this.strMessage="";
			if(this.cc005Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC005","UR_POST")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.cc005Service.postChangeProInfo(cardvesting, strCurCardNo, curChangeProNo, curChangeSeqnoNo, curChangeLastCount, curChangeLastAmt, curChangeSaler1, curChangeSaler1Amt, curChangeSaler2, curChangeSaler2Amt);
			if(flag==false)
			{
				this.strMessage="储值兑换失败!";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
	    
	    
	    @Action(value = "validateProInfo",  results = { 
				 @Result(name = "load_success", type = "json"),
				 @Result(name = "load_failure", type = "json")	
		}) 
		public String validateProInfo()
		{
			this.strMessage="";
			StringBuffer validateMsg=new StringBuffer();
			this.curChangeProName=this.cc005Service.getDataTool().loadProjectName(cardvesting, curChangeProNo, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
	    
	//修改归属门店
	@Action(value = "editCardHomeComp",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String editCardHomeComp()
	{
		this.strMessage="";
		if(this.cc005Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX022")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		boolean flag=this.cc005Service.changeCompId(this.strCurCardNo, this.oldHomeCompId, this.newHomeCompId);
		if(flag==false)
		{
			this.strMessage="修改归属门店失败!";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//修改卡状态
	@Action(value = "editCardState",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String editCardState()
	{
		this.strMessage="";
		if(this.cc005Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX022")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		boolean flag=this.cc005Service.changeCardState(this.strCurCardNo, this.newCardState);
		if(flag==false)
		{
			this.strMessage="修改卡状态失败!";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//验证卡类型
	@Action(value = "validateCardTypeId",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validateCardTypeId()
	{
		StringBuffer strMsbBuf=new StringBuffer();
		this.newCardTypeName=this.cc005Service.getDataTool().loadCardTypeName(CommonTool.getLoginInfo("COMPID"), newCardTypeId, strMsbBuf);
		this.strMessage=strMsbBuf.toString();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//初始化卡类型
	@Action(value = "initProjectInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String initProjectInfo()
	{
		this.strMessage="";
		boolean flag=false;
		if(!CommonTool.getLoginInfo("COMPID").equals("001") &&  !cardvesting.equals("001") && !cardvesting.equals(CommonTool.getLoginInfo("COMPID")))
		{
			this.strMessage="非归属门店的卡不允许设置特价项目";
			return SystemFinal.LOAD_SUCCESS;
		}
		if(this.cc005Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX024")==false)
		{
			flag=this.cc005Service.validatePrice(this.strCurCardNo);
			if(flag==false)
			{
				this.strMessage="该会员卡已经设置过特价项目";
				return SystemFinal.LOAD_SUCCESS;
			}
		}
		flag=this.cc005Service.postSpecialCost(this.strCurCardNo, costxc1, costxc2, costxc3, costxc4, costxc5, costxc6, costxc7, costxc8, costxc9);
		if(flag==false)
		{
			this.strMessage="修改卡特价失败!";
			return SystemFinal.LOAD_SUCCESS;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//修改卡类型
	@Action(value = "editCardType",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String editCardType()
	{
		this.strMessage="";
		if(this.cc005Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX023")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		boolean flag=this.cc005Service.changeCardType(this.strCurCardNo, this.newCardTypeId);
		if(flag==false)
		{
			this.strMessage="修改卡类型失败!";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	//修改卡余额
	@Action(value = "confirmModifyAccount",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String confirmModifyAccount()
	{
		this.strMessage="";
		if(this.cc005Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX022")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		boolean flag=this.cc005Service.handModifyCardInfo(this.strCurCardNo,this.strAccountType,this.oldAccountBalance,this.newAccountBalance,this.oldAccountDebts,this.newAccountDebts,strChangeMark);
		if(flag==false)
		{
			this.strMessage="修改余额失败!";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//加载十周年验证码
	@Action(value = "loadCode",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCode()
	{
		this.code=this.cc005Service.loadCode();
		
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public String getStrCurCardNo() {
		return strCurCardNo;
	}
	public void setStrCurCardNo(String strCurCardNo) {
		this.strCurCardNo = strCurCardNo;
	}
	public Cardinfo getCurCardinfo() {
		return curCardinfo;
	}
	public void setCurCardinfo(Cardinfo curCardinfo) {
		this.curCardinfo = curCardinfo;
	}
	public String getSearchMemberCompIdKey() {
		return searchMemberCompIdKey;
	}
	public void setSearchMemberCompIdKey(String searchMemberCompIdKey) {
		this.searchMemberCompIdKey = searchMemberCompIdKey;
	}
	public String getSearchMemberNoKey() {
		return searchMemberNoKey;
	}
	public void setSearchMemberNoKey(String searchMemberNoKey) {
		this.searchMemberNoKey = searchMemberNoKey;
	}
	public String getSearchMemberNameKey() {
		return searchMemberNameKey;
	}
	public void setSearchMemberNameKey(String searchMemberNameKey) {
		this.searchMemberNameKey = searchMemberNameKey;
	}
	public String getSearchMemberPhoneKey() {
		return searchMemberPhoneKey;
	}
	public void setSearchMemberPhoneKey(String searchMemberPhoneKey) {
		this.searchMemberPhoneKey = searchMemberPhoneKey;
	}
	public String getSearchMemberPCIDKey() {
		return searchMemberPCIDKey;
	}
	public void setSearchMemberPCIDKey(String searchMemberPCIDKey) {
		this.searchMemberPCIDKey = searchMemberPCIDKey;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	@JSON(serialize=false)
	public CC005Service getCc005Service() {
		return cc005Service;
	}
	@JSON(serialize=false)
	public void setCc005Service(CC005Service cc005Service) {
		this.cc005Service = cc005Service;
	}
	public List<Cardinfo> getLsCardinfos() {
		return lsCardinfos;
	}
	public void setLsCardinfos(List<Cardinfo> lsCardinfos) {
		this.lsCardinfos = lsCardinfos;
	}
	public List<Cardaccount> getLsCardaccount() {
		return lsCardaccount;
	}
	public void setLsCardaccount(List<Cardaccount> lsCardaccount) {
		this.lsCardaccount = lsCardaccount;
	}
	public List<Cardproaccount> getLsCardproaccount() {
		return lsCardproaccount;
	}
	public void setLsCardproaccount(List<Cardproaccount> lsCardproaccount) {
		this.lsCardproaccount = lsCardproaccount;
	}
	public List<Cardchangehistory> getLsCardchangehistory() {
		return lsCardchangehistory;
	}
	public void setLsCardchangehistory(List<Cardchangehistory> lsCardchangehistory) {
		this.lsCardchangehistory = lsCardchangehistory;
	}
	public List<Cardaccountchangehistory> getLsCardaccountchangehistory() {
		return lsCardaccountchangehistory;
	}
	public void setLsCardaccountchangehistory(
			List<Cardaccountchangehistory> lsCardaccountchangehistory) {
		this.lsCardaccountchangehistory = lsCardaccountchangehistory;
	}
	public List<Cardtransactionhistory> getLsCardtransactionhistory() {
		return lsCardtransactionhistory;
	}
	public void setLsCardtransactionhistory(
			List<Cardtransactionhistory> lsCardtransactionhistory) {
		this.lsCardtransactionhistory = lsCardtransactionhistory;
	}
	public String getStrBillNo() {
		return strBillNo;
	}
	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}
	public String getStrBillType() {
		return strBillType;
	}
	public void setStrBillType(String strBillType) {
		this.strBillType = strBillType;
	}
	public String getStrAccountType() {
		return strAccountType;
	}
	public void setStrAccountType(String strAccountType) {
		this.strAccountType = strAccountType;
	}
	public List<Sendpointcard> getLsSendpointcard() {
		return lsSendpointcard;
	}
	public void setLsSendpointcard(List<Sendpointcard> lsSendpointcard) {
		this.lsSendpointcard = lsSendpointcard;
	}
	public List<Cardsoninfo> getLsCardsoninfo() {
		return lsCardsoninfo;
	}
	public void setLsCardsoninfo(List<Cardsoninfo> lsCardsoninfo) {
		this.lsCardsoninfo = lsCardsoninfo;
	}
	public BigDecimal getCostxc1() {
		return costxc1;
	}
	public void setCostxc1(BigDecimal costxc1) {
		this.costxc1 = costxc1;
	}
	public BigDecimal getCostxc2() {
		return costxc2;
	}
	public void setCostxc2(BigDecimal costxc2) {
		this.costxc2 = costxc2;
	}
	public BigDecimal getCostxc3() {
		return costxc3;
	}
	public void setCostxc3(BigDecimal costxc3) {
		this.costxc3 = costxc3;
	}
	public BigDecimal getCostxc4() {
		return costxc4;
	}
	public void setCostxc4(BigDecimal costxc4) {
		this.costxc4 = costxc4;
	}
	public BigDecimal getCostxc5() {
		return costxc5;
	}
	public void setCostxc5(BigDecimal costxc5) {
		this.costxc5 = costxc5;
	}
	public BigDecimal getCostxc6() {
		return costxc6;
	}
	public void setCostxc6(BigDecimal costxc6) {
		this.costxc6 = costxc6;
	}
	public BigDecimal getCostxc7() {
		return costxc7;
	}
	public void setCostxc7(BigDecimal costxc7) {
		this.costxc7 = costxc7;
	}
	public BigDecimal getCostxc8() {
		return costxc8;
	}
	public void setCostxc8(BigDecimal costxc8) {
		this.costxc8 = costxc8;
	}
	public BigDecimal getCostxc9() {
		return costxc9;
	}
	public void setCostxc9(BigDecimal costxc9) {
		this.costxc9 = costxc9;
	}
	public Cardspecialcost getCurCardspecialcost() {
		return curCardspecialcost;
	}
	public void setCurCardspecialcost(Cardspecialcost curCardspecialcost) {
		this.curCardspecialcost = curCardspecialcost;
	}
	public String getCardvesting() {
		return cardvesting;
	}
	public void setCardvesting(String cardvesting) {
		this.cardvesting = cardvesting;
	}
	public String getOldProItemNo() {
		return oldProItemNo;
	}
	public void setOldProItemNo(String oldProItemNo) {
		this.oldProItemNo = oldProItemNo;
	}
	public String getOldpromark() {
		return oldpromark;
	}
	public void setOldpromark(String oldpromark) {
		this.oldpromark = oldpromark;
	}
	public BigDecimal getOldprocount() {
		return oldprocount;
	}
	public void setOldprocount(BigDecimal oldprocount) {
		this.oldprocount = oldprocount;
	}
	public BigDecimal getOldproamt() {
		return oldproamt;
	}
	public void setOldproamt(BigDecimal oldproamt) {
		this.oldproamt = oldproamt;
	}
	public BigDecimal getCurChangeLastAmt() {
		return curChangeLastAmt;
	}
	public void setCurChangeLastAmt(BigDecimal curChangeLastAmt) {
		this.curChangeLastAmt = curChangeLastAmt;
	}
	public int getSearcharDataType() {
		return searcharDataType;
	}
	public void setSearcharDataType(int searcharDataType) {
		this.searcharDataType = searcharDataType;
	}
	public String getCurChangeProName() {
		return curChangeProName;
	}
	public void setCurChangeProName(String curChangeProName) {
		this.curChangeProName = curChangeProName;
	}
	public BigDecimal getOldAccountBalance() {
		return oldAccountBalance;
	}
	public void setOldAccountBalance(BigDecimal oldAccountBalance) {
		this.oldAccountBalance = oldAccountBalance;
	}
	public BigDecimal getNewAccountBalance() {
		return newAccountBalance;
	}
	public void setNewAccountBalance(BigDecimal newAccountBalance) {
		this.newAccountBalance = newAccountBalance;
	}
	public BigDecimal getOldAccountDebts() {
		return oldAccountDebts;
	}
	public void setOldAccountDebts(BigDecimal oldAccountDebts) {
		this.oldAccountDebts = oldAccountDebts;
	}
	public BigDecimal getNewAccountDebts() {
		return newAccountDebts;
	}
	public void setNewAccountDebts(BigDecimal newAccountDebts) {
		this.newAccountDebts = newAccountDebts;
	}
	public String getStrChangeMark() {
		return strChangeMark;
	}
	public void setStrChangeMark(String strChangeMark) {
		this.strChangeMark = strChangeMark;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
