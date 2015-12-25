package com.amani.action.AdvancedOperations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.amani.action.AMN_ModuleAction;
import com.amani.bean.ReEditBillInfo;

import com.amani.model.Dconsumeinfo;
import com.amani.model.DconsumeinfoId;
import com.amani.model.Dpayinfo;
import com.amani.model.DpayinfoId;
import com.amani.model.Dproexchangeinfo;
import com.amani.model.Mconsumeinfo;
import com.amani.model.Staffinfo;
import com.amani.model.StaffinfoSimple;


import com.amani.service.AliPayService;
import com.amani.service.WechatPayService;
import com.amani.service.AdvancedOperations.AC008Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac008")
public class AC008Action extends AMN_ModuleAction{
	@Autowired
	private AC008Service ac008Service;
	@Autowired
	private WechatPayService wechatPayService;
	@Autowired
	private AliPayService aliPayService;
	private String strJsonParam;	
	private String strJsonParam_pay;
    private String strSearchDate;
	private String strCurCompId;
	private String strCurBillId;
	private String strCurCardNo;
	private double changeseqno;
	private int paramSP114;
	public int getParamSP114() {
		return paramSP114;
	}
	public void setParamSP114(int paramSP114) {
		this.paramSP114 = paramSP114;
	}
	private String itemInid;
	public String getItemInid() {
		return itemInid;
	}
	public void setItemInid(String itemInid) {
		this.itemInid = itemInid;
	}
	private int 	nBillType;
    private ReEditBillInfo curReEditBillInfo;
    private List<ReEditBillInfo> lsReEditBillInfo;

    private List<ReEditBillInfo> lsBillEdits;
    
    private List<Dpayinfo> lsDpayinfo;
	private List<Staffinfo> lsStaffinfo;
	private String recommendempid;
	private String recommendempinid;
	private String recommendempname;
    private Mconsumeinfo curMconsumeinfo;
    private List<Dconsumeinfo> lsDconsumeinfos;
    private List<Dproexchangeinfo> lsDproexchangeinfos;
    private String strCurDate;
    private String strNewBillId;
    //收银单明细
    private String strPayMode1;
    private BigDecimal dPayAmt1;
    private String strPayMode2;
    private BigDecimal dPayAmt2;
    private String strPayMode3;
    private BigDecimal dPayAmt3;
    private String strPayMode4;
    private BigDecimal dPayAmt4;
    private String strPayMode5;
    private BigDecimal dPayAmt5;
    private String strPayMode6;
    private BigDecimal dPayAmt6;
    private String strJsonParam_service;
    private int paramSp097 ;//是否允许输入中工
    private int paramSp105 ;
    private int paramSp112;
    private String managerz;
    private String managerzinid;
    private String managerf;
    private String managerfinid;
    private String managerzf;
    private String managerzfinid;
    private String managerff;
    private String managerffinid;
    
    public String getManagerff() {
		return managerff;
	}
	public void setManagerff(String managerff) {
		this.managerff = managerff;
	}
	public int getnBillType() {
		return nBillType;
	}
	public void setnBillType(int nBillType) {
		this.nBillType = nBillType;
	}
	public String getManagerzf() {
		return managerzf;
	}
	public void setManagerzf(String managerzf) {
		this.managerzf = managerzf;
	}
	public String getManagerzfinid() {
		return managerzfinid;
	}
	public void setManagerzfinid(String managerzfinid) {
		this.managerzfinid = managerzfinid;
	}
	public String getManagerffinid() {
		return managerffinid;
	}
	public void setManagerffinid(String managerffinid) {
		this.managerffinid = managerffinid;
	}
	public String getManagerz() {
		return managerz;
	}
	public void setManagerz(String managerz) {
		this.managerz = managerz;
	}
	public String getManagerzinid() {
		return managerzinid;
	}
	public void setManagerzinid(String managerzinid) {
		this.managerzinid = managerzinid;
	}
	public String getManagerf() {
		return managerf;
	}
	public void setManagerf(String managerf) {
		this.managerf = managerf;
	}
	public String getManagerfinid() {
		return managerfinid;
	}
	public void setManagerfinid(String managerfinid) {
		this.managerfinid = managerfinid;
	}
	
    
    
    
    public int getParamSp112() {
		return paramSp112;
	}
	public void setParamSp112(int paramSp112) {
		this.paramSp112 = paramSp112;
	}
	private List<StaffinfoSimple> lsStaffinfoSimple;
    private String strShareCondition;
    /***********经理卡验证*******************/
    private String mangerCardNo;
    private int errorflag;
    private int mangerflag;
	public int getErrorflag() {
		return errorflag;
	}
	public void setErrorflag(int errorflag) {
		this.errorflag = errorflag;
	}
	public int getMangerflag() {
		return mangerflag;
	}
	public void setMangerflag(int mangerflag) {
		this.mangerflag = mangerflag;
	}
	public List<StaffinfoSimple> getLsStaffinfoSimple() {
		return lsStaffinfoSimple;
	}
	public void setLsStaffinfoSimple(List<StaffinfoSimple> lsStaffinfoSimple) {
		this.lsStaffinfoSimple = lsStaffinfoSimple;
	}
	public String getStrShareCondition() {
		return strShareCondition;
	}
	public void setStrShareCondition(String strShareCondition) {
		this.strShareCondition = strShareCondition;
	}
	public String getMangerCardNo() {
		return mangerCardNo;
	}
	public void setMangerCardNo(String mangerCardNo) {
		this.mangerCardNo = mangerCardNo;
	}
	public int getParamSp097() {
		return paramSp097;
	}
	public void setParamSp097(int paramSp097) {
		this.paramSp097 = paramSp097;
	}
	public String getStrPayMode1() {
		return strPayMode1;
	}
	public void setStrPayMode1(String strPayMode1) {
		this.strPayMode1 = strPayMode1;
	}
	public BigDecimal getDPayAmt1() {
		return dPayAmt1;
	}
	public void setDPayAmt1(BigDecimal payAmt1) {
		dPayAmt1 = payAmt1;
	}
	public String getStrPayMode2() {
		return strPayMode2;
	}
	public void setStrPayMode2(String strPayMode2) {
		this.strPayMode2 = strPayMode2;
	}
	public BigDecimal getDPayAmt2() {
		return dPayAmt2;
	}
	public void setDPayAmt2(BigDecimal payAmt2) {
		dPayAmt2 = payAmt2;
	}
	public String getStrPayMode3() {
		return strPayMode3;
	}
	public void setStrPayMode3(String strPayMode3) {
		this.strPayMode3 = strPayMode3;
	}
	public BigDecimal getDPayAmt3() {
		return dPayAmt3;
	}
	public void setDPayAmt3(BigDecimal payAmt3) {
		dPayAmt3 = payAmt3;
	}
	public String getStrPayMode4() {
		return strPayMode4;
	}
	public void setStrPayMode4(String strPayMode4) {
		this.strPayMode4 = strPayMode4;
	}
	public BigDecimal getDPayAmt4() {
		return dPayAmt4;
	}
	public void setDPayAmt4(BigDecimal payAmt4) {
		dPayAmt4 = payAmt4;
	}
	public String getStrPayMode5() {
		return strPayMode5;
	}
	public void setStrPayMode5(String strPayMode5) {
		this.strPayMode5 = strPayMode5;
	}
	public BigDecimal getDPayAmt5() {
		return dPayAmt5;
	}
	public void setDPayAmt5(BigDecimal payAmt5) {
		dPayAmt5 = payAmt5;
	}
	public String getStrPayMode6() {
		return strPayMode6;
	}
	public void setStrPayMode6(String strPayMode6) {
		this.strPayMode6 = strPayMode6;
	}
	public BigDecimal getDPayAmt6() {
		return dPayAmt6;
	}
	public void setDPayAmt6(BigDecimal payAmt6) {
		dPayAmt6 = payAmt6;
	}
	public String getStrJsonParam_service() {
		return strJsonParam_service;
	}
	public void setStrJsonParam_service(String strJsonParam_service) {
		this.strJsonParam_service = strJsonParam_service;
	}
	public List<Staffinfo> getLsStaffinfo() {
		return lsStaffinfo;
	}
	public void setLsStaffinfo(List<Staffinfo> lsStaffinfo) {
		this.lsStaffinfo = lsStaffinfo;
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
	
	@Action(value = "load",  results = { 
			 @Result(name = "load_success", location = "/AdvancedOperations/AC008/index.jsp"),
    @Result(name = "load_failure", location = "/AdvancedOperations/AC008/index.jsp")	
	}) 
	public String load()
	{
		paramSp112=Integer.parseInt(this.ac008Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP112"));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Override
	protected boolean beforePost() {
		if(!strJsonParam_pay.equals("") && strJsonParam_pay.indexOf("{")>-1)
		{
			this.lsDpayinfo=this.ac008Service.getDataTool().loadDTOList(strJsonParam_pay, Dpayinfo.class);
			if(lsDpayinfo!=null && lsDpayinfo.size()>0)
			{
				for(int i=0;i<lsDpayinfo.size();i++)
				{
					if(lsDpayinfo.get(i)!=null && CommonTool.FormatBigDecimal(lsDpayinfo.get(i).getPayamt()).doubleValue()>0)
					{
						if(CommonTool.FormatInteger(nBillType)==0)
							lsDpayinfo.get(i).setId(new DpayinfoId(strCurCompId,strCurBillId,"SK",i*1.0));
						if(CommonTool.FormatInteger(nBillType)==1)
							lsDpayinfo.get(i).setId(new DpayinfoId(strCurCompId,strCurBillId,"CZ",i*1.0));
						if(CommonTool.FormatInteger(nBillType)==2
						|| CommonTool.FormatInteger(nBillType)==5)
							lsDpayinfo.get(i).setId(new DpayinfoId(strCurCompId,strCurBillId,"ZK",i*1.0));
						if(CommonTool.FormatInteger(nBillType)==6)
							lsDpayinfo.get(i).setId(new DpayinfoId(strCurCompId,strCurBillId,"TK",i*1.0));
						if(CommonTool.FormatInteger(nBillType)==7)
							lsDpayinfo.get(i).setId(new DpayinfoId(strCurCompId,strCurBillId,"HZ",i*1.0));
					}
					else
					{
						lsDpayinfo.remove(i);
						i--;
					}
				}
			}
		}
		
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
	@Action(value = "loadMasterBillType",  results = { 
			 @Result(name = "load_success", type = "json"),
     @Result(name = "load_failure", type = "json")	
	}) 
	public String loadMasterBillType()
	{
		this.lsBillEdits=this.ac008Service.loadBillTypeDateInfo(strCurCompId, CommonTool.setDateMask(strSearchDate));
		this.lsStaffinfo=this.ac008Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
		paramSp097=Integer.parseInt(this.ac008Service.getDataTool().loadSysParam(strCurCompId,"SP097"));
		paramSp112=Integer.parseInt(this.ac008Service.getDataTool().loadSysParam(strCurCompId,"SP112"));
		paramSP114=Integer.parseInt(this.ac008Service.getDataTool().loadSysParam(strCurCompId,"SP114"));
		strShareCondition=this.ac008Service.getDataTool().loadSysParam(strCurCompId,"SP102");
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadServiceDetial",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadServiceDetial()
	{
		this.curMconsumeinfo=this.ac008Service.loadMconsumeinfoByBill(this.strCurCompId, this.strCurBillId);
		if(curMconsumeinfo!=null)
		{
			if(!CommonTool.FormatString(curMconsumeinfo.getRecommendempid()).equals(""))
			{
				StringBuffer nameBuf=new StringBuffer();
				curMconsumeinfo.setRecommendempname(this.ac008Service.getDataTool().loadEmpNameById(strCurCompId, CommonTool.FormatString(curMconsumeinfo.getRecommendempid()), nameBuf));
				nameBuf=null;
			}
		}
		String strSp105=CommonTool.FormatString(this.ac008Service.getDataTool().loadSysParam(strCurCompId, "SP105"));
		if(strSp105.equals("1"))
			paramSp105=1;
		else
			paramSp105=0;
		if(this.nBillType==10)
			this.lsDconsumeinfos=this.ac008Service.loadDconsumeinfoByBillId_prj(this.strCurCompId, this.strCurBillId);
		else if (this.nBillType==11)
			this.lsDconsumeinfos=this.ac008Service.loadDconsumeinfoByBillId_Goods(this.strCurCompId, this.strCurBillId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadCardSaleDetial",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCardSaleDetial()
	{
		this.curReEditBillInfo=this.ac008Service.loadCurBillDateInfo(this.strCurCompId,this.nBillType, this.strCurBillId,this.changeseqno,itemInid);
		this.lsDpayinfo=this.ac008Service.loadDpayinfoByBill(this.strCurCompId, this.strCurBillId, this.nBillType,this.changeseqno);
		if(this.nBillType==1 )
			lsStaffinfoSimple=this.ac008Service.getDataTool().loadOldCustomerInfo(this.strCurCompId,this.curReEditBillInfo.getStrCardNo());
		else if( this.nBillType==2 || this.nBillType==3 || this.nBillType==4 )
		{
			lsStaffinfoSimple=this.ac008Service.getDataTool().loadOldCustomerInfo(this.strCurCompId,this.curReEditBillInfo.getStrOldCardNo(),this.curReEditBillInfo.getStrCardNo());
		}
		else if (this.nBillType==5)
		{
			lsStaffinfoSimple=this.ac008Service.getDataTool().loadCombinCustomerInfo(this.strCurCompId,this.curReEditBillInfo.getStrOldCardNo(),this.curReEditBillInfo.getStrBillId());
		}
		else 
		{
			lsStaffinfoSimple=null;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	@Action(value = "loadMasterinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadMasterinfo()
	{
		
		lsReEditBillInfo=this.ac008Service.loadBillDateInfoByType(this.strCurCompId, this.nBillType, CommonTool.setDateMask(strSearchDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadpaymodeinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadpaymodeinfo()
	{
		
		this.lsDpayinfo=this.ac008Service.loadDpayinfoByBill(this.strCurCompId, this.strCurBillId, this.nBillType,this.changeseqno);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadBillHistory",  results = { 
				@Result(name = "load_success", type = "json"),
			 	@Result(name = "load_failure", type = "json")	
	}) 
	public String loadBillHistory()
	{
		this.strMessage="";
		this.lsReEditBillInfo=this.ac008Service.loadBillModifyHistory(this.strCurCompId, this.strCurBillId, this.nBillType);
		if(this.lsReEditBillInfo==null || this.lsReEditBillInfo.size()==0)
		{
			this.strMessage="无修改记录";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	@Action(value = "loadBillCosHistory",  results = { 
			@Result(name = "load_success", type = "json"),
		 	@Result(name = "load_failure", type = "json")	
	}) 
	public String loadBillCosHistory()
	{
		this.strMessage="";
		this.lsDconsumeinfos=this.ac008Service.loadCostHistory(this.strCurCompId, this.strCurBillId, this.nBillType);
		if(this.lsDconsumeinfos==null || this.lsDconsumeinfos.size()==0)
		{
			this.strMessage="无修改记录";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadBilProHistory",  results = { 
			@Result(name = "load_success", type = "json"),
		 	@Result(name = "load_failure", type = "json")	
	}) 
	public String loadBilProHistory()
	{
		this.strMessage="";
		this.lsDproexchangeinfos=this.ac008Service.loadProHistory(this.strCurCompId, this.strCurBillId, this.changeseqno);
		if(this.lsDproexchangeinfos==null || this.lsDproexchangeinfos.size()==0)
		{
			this.strMessage="无修改记录";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	@Action(value = "postCurRootInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	}) 
	public String postCurRootInfo()
	{
		this.strMessage="";
		if(this.ac008Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX021")==false && CommonTool.FormatInteger(this.mangerflag)==0)
		{
			if(!CommonTool.setDateMask(this.strCurDate).equals(CommonTool.getCurrDate())
			&& !CommonTool.setDateMask(this.strCurDate).equals(CommonTool.datePlusDay(CommonTool.getCurrDate(), -1)))
			{
				this.errorflag=1;
				if(!CommonTool.setDateMask(this.strCurDate).substring(4,6).equals(CommonTool.getCurrDate().substring(4,6))
						&& Integer.parseInt(CommonTool.getCurrDate().substring(6,8))>3)
				{
						this.errorflag=2;
				}
				strMessage="隔月单据不能修改!";
				return SystemFinal.POST_FAILURE;
			}
		}
		this.beforePost();
		/*if(nBillType==0 || nBillType==1 || nBillType==2 || nBillType==3 ||nBillType==4 ||nBillType==5)
		{
			//来源是美容的,烫染师不能分享业绩!
			if(curReEditBillInfo.getBillinsertype()==1 ){
				boolean b = this.ac008Service.getDataTool().chekIsHotDyeDivision(curReEditBillInfo);
				if(b){
					this.strMessage="来源是美容的,烫染师不能分享业绩!";
					return SystemFinal.POST_FAILURE;
				}
			}
			
		}*/
		//如果美容部业绩大于30000 美容经理和顾问提成
		if(nBillType==0 || nBillType==1 || nBillType==2 || nBillType==3 ||nBillType==4 ||nBillType==5)
		{
			float comm=this.ac008Service.getDataTool().getBeautyDepartmentPerformance(curReEditBillInfo);
			if(comm>30000)
			{
				int index=0;
				comm=(float) ((comm-30000));
				if(CommonTool.isEmpty(curReEditBillInfo.getBeautyManager()))
				{
					index++;
					//curReEditBillInfo.setBeautyPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curReEditBillInfo.getConsultant()))
				{
					index++;
					//curReEditBillInfo.setConsultantPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curReEditBillInfo.getConsultant1()))
				{
					index++;
					//curReEditBillInfo.setConsultant1Perf(BigDecimal.valueOf(comm));
				}
				comm=comm/index;
				if(CommonTool.isEmpty(curReEditBillInfo.getBeautyManager()))
				{
					//index++;
					curReEditBillInfo.setBeautyPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curReEditBillInfo.getConsultant()))
				{
					//index++;
					curReEditBillInfo.setConsultantPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curReEditBillInfo.getConsultant1()))
				{
					//index++;
					curReEditBillInfo.setConsultant1Perf(BigDecimal.valueOf(comm));
				}
			}
		}
		boolean flag=this.ac008Service.postCurDateInfo(this.strCurCompId, this.strCurBillId,changeseqno,this.nBillType, this.curReEditBillInfo,this.lsDpayinfo);
		if(flag==false)
		{
			this.errorflag=2;
			strMessage="更新失败,刷新后重试!";
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	
	protected boolean beforePostService() {
		if(!strJsonParam_service.equals("") && strJsonParam_service.indexOf("{")>-1)
		{
			this.lsDconsumeinfos=this.ac008Service.getDataTool().loadDTOList(strJsonParam_service, Dconsumeinfo.class);
			if(lsDconsumeinfos!=null && lsDconsumeinfos.size()>0)
			{
				for(int i=0;i<lsDconsumeinfos.size();i++)
				{
					if(lsDconsumeinfos.get(i)!=null && !CommonTool.FormatString(lsDconsumeinfos.get(i).getCsitemno()).equals(""))
					{
						lsDconsumeinfos.get(i).setId(new DconsumeinfoId(strCurCompId,strCurBillId,lsDconsumeinfos.get(i).getBcsinfotype(),lsDconsumeinfos.get(i).getBcsseqno()));
					}
					else
					{
						lsDconsumeinfos.remove(i);
						i--;
					}
				}
			}
		}
		lsDpayinfo=new ArrayList();
		Dpayinfo payRecord=null;
		if(!CommonTool.FormatString(this.strPayMode1).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt1).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(strCurCompId,strCurBillId,"SY",0));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt1));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode1));
			lsDpayinfo.add(payRecord);
		}
		if(!CommonTool.FormatString(this.strPayMode2).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt2).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(strCurCompId,strCurBillId,"SY",1));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt2));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode2));
			lsDpayinfo.add(payRecord);
		}
		if(!CommonTool.FormatString(this.strPayMode3).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt3).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(strCurCompId,strCurBillId,"SY",2));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt3));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode3));
			lsDpayinfo.add(payRecord);
		}
		if(!CommonTool.FormatString(this.strPayMode4).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt4).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(strCurCompId,strCurBillId,"SY",3));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt4));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode4));
			lsDpayinfo.add(payRecord);
		}
		if(!CommonTool.FormatString(this.strPayMode5).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt5).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(strCurCompId,strCurBillId,"SY",4));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt5));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode5));
			lsDpayinfo.add(payRecord);
		}
		if(!CommonTool.FormatString(this.strPayMode6).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt6).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(strCurCompId,strCurBillId,"SY",5));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt6));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode6));
			lsDpayinfo.add(payRecord);
		}
		
		return true;
	}
	
	@Action(value = "postSerivceInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	}) 
	public String postSerivceInfo()
	{
		this.strMessage="";
		if(this.ac008Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX020")==false && CommonTool.FormatInteger(this.mangerflag)==0)
		{
			if(!CommonTool.setDateMask(this.strCurDate).equals(CommonTool.getCurrDate())
			&& !CommonTool.setDateMask(this.strCurDate).equals(CommonTool.datePlusDay(CommonTool.getCurrDate(), -1))) 
			{
				this.errorflag=1;
				//不同月 ,并且大于3号
				if(!CommonTool.setDateMask(this.strCurDate).substring(4,6).equals(CommonTool.getCurrDate().substring(4,6))
					&& Integer.parseInt(CommonTool.getCurrDate().substring(6,8))>3)
				{
					this.errorflag=2;
				}
				strMessage="隔月单据不能修改!";
				return SystemFinal.POST_FAILURE;
			}
		}
		this.beforePostService();
		boolean flag=this.ac008Service.postServiceInfo(this.strCurCompId, this.strCurBillId,this.lsDconsumeinfos,this.lsDpayinfo,this.recommendempid,this.recommendempinid,this.managerz,this.managerzinid,this.managerf,this.managerfinid);
		if(flag==false)
		{
			this.errorflag=2;
			strMessage="更新失败,刷新后重试!";
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	//返销收银单
	@Action(value = "postBackSerivceInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	}) 
	public String postBackSerivceInfo()
	{
		this.strMessage="";
		if(this.ac008Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX020")==false)
		{
			if(strCurCardNo.equals("散客") && !CommonTool.setDateMask(this.strCurDate).equals(CommonTool.getCurrDate()))
			{
				strMessage="隔天不能返销散客单单据!";
				return SystemFinal.POST_FAILURE;
			}
			if(!strCurCardNo.equals("散客") && !CommonTool.FormatString(this.strCurDate).substring(0, 6).equals(CommonTool.getCurrDate().substring(0, 6)))
			{
				strMessage="隔月不能返销销卡单单据!";
				return SystemFinal.POST_FAILURE;
			}
		}
		boolean flag=this.ac008Service.validateConsoleFlag(this.strCurCompId, this.strCurBillId);
		if(flag==false)
		{
			strMessage="该单据已经返销,请确认!";
			return SystemFinal.POST_FAILURE;
		}
		flag=this.ac008Service.validateCardState(strCurCardNo);
		if(flag==false)
		{
			strMessage="该会员卡状态非正常使用状态,不能返销,请确认!";
			return SystemFinal.POST_FAILURE;
		}
		strNewBillId=this.ac008Service.getDataTool().loadBillIdByRule(strCurCompId,"mconsumeinfo", "csbillid", "SP007");
		flag=this.ac008Service.postBackServiceInfo(this.strCurCompId, this.strCurBillId,strNewBillId, outTradeNo);
		if(flag==false)
		{
			strMessage="更新失败,刷新后重试!";
			return SystemFinal.POST_FAILURE;
		}
		int costtype=1;
		if(!CommonTool.FormatString(strCurCardNo).equals("散客"))
		{
			costtype=2;
		}
		flag=this.ac008Service.afterPost(strCurCompId,strNewBillId, CommonTool.getCurrDate(), costtype,this.strCurBillId);
		if(flag==false)
		{
			this.strMessage="生成消费历史有误,请到会员卡资料管理核实!";
			return SystemFinal.POST_FAILURE;
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	
	//反冲卡单
	@Action(value = "postBackCardSaleInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	})
	public String postBackCardSaleInfo()
	{
		this.strMessage="";
		if(this.ac008Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX021")==false)
		{
			if(!CommonTool.setDateMask(this.strCurDate).equals(CommonTool.getCurrDate()))
			{
				strMessage="隔天单据不能返充!";
				return SystemFinal.POST_FAILURE;
			}
		}
		boolean flag=this.ac008Service.validateSaleFlag(this.strCurCompId, this.strCurBillId,this.nBillType);
		if(flag==false)
		{
			strMessage="该单据已经返充,请确认!";
			return SystemFinal.POST_FAILURE;
		}
		StringBuffer strMsgBuf=new StringBuffer();
		flag=this.ac008Service.postBackCardSaleInfo(this.strCurCompId, this.strCurBillId,this.strCurCardNo,this.nBillType,changeseqno,strMsgBuf,itemInid);
		if(flag==false)
		{
			strMessage=strMsgBuf.toString();
			return SystemFinal.POST_FAILURE;
		}
		
		return SystemFinal.POST_SUCCESS;
	}
	@Action(value = "handInsertShare",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String handInsertShare()
	{
		this.strMessage="";
		boolean flag=this.ac008Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
		if(flag==false)
		{
			this.strMessage="该经理密码不对,请核实";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "add",  results = { 
			 @Result(name = "add_success", type = "json"),
          @Result(name = "add_failure", type = "json")	
       }) 
	@Override
	public String add()
	{
	
		return SystemFinal.ADD_SUCCESS;
	}
	
	@Action(value = "post",  results = { 
			 @Result(name = "post_success", type = "json"),
            @Result(name = "post_failure", type = "json")	
         }) 
	@Override
	public String post()
	{
		try
		{
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "delete",  results = { 
			 @Result(name = "delete_success", type = "json"),
           @Result(name = "delete_failure", type = "json")	
        }) 
	@Override
	public String delete()
	{
		this.strMessage="";
		
		return SystemFinal.DELETE_SUCCESS;
	}
	
	private String outTradeNo;//扫码订单号
	private BigDecimal scanpayamt;//扫码支付金额
	//支付宝订单退款
	@Action(value="aliRefund", results={@Result(name="operation_success", type="json"),
			 @Result(name="operation_failure", type="json")}) 
	public String aliRefund(){
		try {
			if(StringUtils.isNotBlank(outTradeNo)){
				strMessage = aliPayService.refundPaypalBillStatus(CommonTool.getLoginInfo("COMPID"), strCurBillId, outTradeNo, scanpayamt);
			}else{
				strMessage ="获取支付宝单据号失败，请重试！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = "ERROR";
		}
		return SystemFinal.OPERATION_SUCCESS;
	}
	//微信订单退款
	@Action(value="wechatRefund", results={@Result(name="operation_success", type="json"),
			 @Result(name="operation_failure", type="json")}) 
	public String wechatRefund(){
		try {
			if(StringUtils.isNotBlank(outTradeNo)){
				strMessage = wechatPayService.webChatRefundPaypalBillStatus(CommonTool.getLoginInfo("COMPID"), strCurBillId, outTradeNo, scanpayamt);
			}else{
				strMessage ="获取微信单据号失败，请重试！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = "ERROR";
		}
		return SystemFinal.OPERATION_SUCCESS;
	}
	
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public BigDecimal getScanpayamt() {
		return scanpayamt;
	}
	public void setScanpayamt(BigDecimal scanpayamt) {
		this.scanpayamt = scanpayamt;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	public String getStrCurBillId() {
		return strCurBillId;
	}
	public void setStrCurBillId(String strCurBillId) {
		this.strCurBillId = strCurBillId;
	}
	public List<Dpayinfo> getLsDpayinfo() {
		return lsDpayinfo;
	}
	public void setLsDpayinfo(List<Dpayinfo> lsDpayinfo) {
		this.lsDpayinfo = lsDpayinfo;
	}
	
	public String getStrJsonParam_pay() {
		return strJsonParam_pay;
	}
	public void setStrJsonParam_pay(String strJsonParam_pay) {
		this.strJsonParam_pay = strJsonParam_pay;
	}
	
	@JSON(serialize=false)
	public AC008Service getAc008Service() {
		return ac008Service;
	}
	@JSON(serialize=false)
	public void setAc008Service(AC008Service ac008Service) {
		this.ac008Service = ac008Service;
	}
	public String getStrSearchDate() {
		return strSearchDate;
	}
	public void setStrSearchDate(String strSearchDate) {
		this.strSearchDate = strSearchDate;
	}
	public int getNBillType() {
		return nBillType;
	}
	public void setNBillType(int billType) {
		nBillType = billType;
	}
	public ReEditBillInfo getCurReEditBillInfo() {
		return curReEditBillInfo;
	}
	public void setCurReEditBillInfo(ReEditBillInfo curReEditBillInfo) {
		this.curReEditBillInfo = curReEditBillInfo;
	}
	public List<ReEditBillInfo> getLsReEditBillInfo() {
		return lsReEditBillInfo;
	}
	public void setLsReEditBillInfo(List<ReEditBillInfo> lsReEditBillInfo) {
		this.lsReEditBillInfo = lsReEditBillInfo;
	}
	public List<ReEditBillInfo> getLsBillEdits() {
		return lsBillEdits;
	}
	public void setLsBillEdits(List<ReEditBillInfo> lsBillEdits) {
		this.lsBillEdits = lsBillEdits;
	}
	public double getChangeseqno() {
		return changeseqno;
	}
	public void setChangeseqno(double changeseqno) {
		this.changeseqno = changeseqno;
	}
	public List<Dconsumeinfo> getLsDconsumeinfos() {
		return lsDconsumeinfos;
	}
	public void setLsDconsumeinfos(List<Dconsumeinfo> lsDconsumeinfos) {
		this.lsDconsumeinfos = lsDconsumeinfos;
	}
	public String getStrCurCardNo() {
		return strCurCardNo;
	}
	public void setStrCurCardNo(String strCurCardNo) {
		this.strCurCardNo = strCurCardNo;
	}
	public String getStrCurDate() {
		return strCurDate;
	}
	public void setStrCurDate(String strCurDate) {
		this.strCurDate = strCurDate;
	}
	public String getStrNewBillId() {
		return strNewBillId;
	}
	public void setStrNewBillId(String strNewBillId) {
		this.strNewBillId = strNewBillId;
	}
	public String getRecommendempid() {
		return recommendempid;
	}
	public void setRecommendempid(String recommendempid) {
		this.recommendempid = recommendempid;
	}
	public String getRecommendempname() {
		return recommendempname;
	}
	public void setRecommendempname(String recommendempname) {
		this.recommendempname = recommendempname;
	}
	public String getRecommendempinid() {
		return recommendempinid;
	}
	public void setRecommendempinid(String recommendempinid) {
		this.recommendempinid = recommendempinid;
	}
	public Mconsumeinfo getCurMconsumeinfo() {
		return curMconsumeinfo;
	}
	public void setCurMconsumeinfo(Mconsumeinfo curMconsumeinfo) {
		this.curMconsumeinfo = curMconsumeinfo;
	}
	public int getParamSp105() {
		return paramSp105;
	}
	public void setParamSp105(int paramSp105) {
		this.paramSp105 = paramSp105;
	}
	public List<Dproexchangeinfo> getLsDproexchangeinfos() {
		return lsDproexchangeinfos;
	}
	public void setLsDproexchangeinfos(List<Dproexchangeinfo> lsDproexchangeinfos) {
		this.lsDproexchangeinfos = lsDproexchangeinfos;
	}

}
