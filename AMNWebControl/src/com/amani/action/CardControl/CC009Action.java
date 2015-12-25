package com.amani.action.CardControl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;

import com.amani.model.Cardinfo;
import com.amani.model.Dcardchangeinfo;
import com.amani.model.DcardchangeinfoId;
import com.amani.model.Dpayinfo;
import com.amani.model.DpayinfoId;
import com.amani.model.Dsalecardproinfo;
import com.amani.model.DsalecardproinfoId;
import com.amani.model.Mcardchangeinfo;
import com.amani.model.Staffinfo;
import com.amani.model.StaffinfoSimple;
import com.amani.service.CardControl.CC009Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc009")
public class CC009Action extends AMN_ModuleAction{
	@Autowired
	private CC009Service cc009Service;
	private String strJsonParam;	
	private String strJsonParamOldCard;	
	private String strCurPackageNo;
    private List<Mcardchangeinfo> lsMcardchangeinfo;
    private List<Dcardchangeinfo> lsDcardchangeinfo;
    private Mcardchangeinfo curMcardchangeinfo;
    private List<Dpayinfo> lsDpayinfo;
    private String strCurCompId;
    private String strCurBillId;
    private String strCurCardNo;
    private String strCurCardType;
    private String strCurCardTypeName;
    private int    iCurChangeType=0;
    private String strCurNewCardNo;
    private String strCurNewCardType;
    private String strCurNewCardTypeName;
    private String strRemark;
	private Cardinfo curCardinfo;
	private String searchCardNoKey;
	private String searchMemberNameKey;
	private String searchMemberPhoneKey;
	private String searchMemberPcidKey;
    private List<Cardinfo> lsCardinfos;
    private String searchLossCardNo;
    private String searchRecevieCardNo;
    private String strSearchContent;
	private List<Staffinfo> lsStaffinfo;
	private String strSalePayMode;
	 private String strShareCondition;
	private double curCardSaleCardAmt;
	private double lowestAmt;
	private List<StaffinfoSimple> lsStaffinfoSimple;
	

    /************小票打印***************/
	private String strCardNo;
    private String cashMemberId;
    private String companName;
    private String companTel;
    private String companNet;
    private String companAddr;
    private String cashMemberName;
    private String printTime;
    private String printDate;
    private String billDate;
    private String strSP114;
    public String getStrSP114() {
		return strSP114;
	}

	public void setStrSP114(String strSP114) {
		this.strSP114 = strSP114;
	}

	private List<Dpayinfo> payTypeList;
    
    /***********经理卡验证*******************/
    private String mangerCardNo;
    public String getMangerCardNo() {
		return mangerCardNo;
	}

	public void setMangerCardNo(String mangerCardNo) {
		this.mangerCardNo = mangerCardNo;
	}

	/************小票打印***************/
	public double getLowestAmt() {
		return lowestAmt;
	}

	public void setLowestAmt(double lowestAmt) {
		this.lowestAmt = lowestAmt;
	}

	public String getSearchLossCardNo() {
		return searchLossCardNo;
	}

	public void setSearchLossCardNo(String searchLossCardNo) {
		this.searchLossCardNo = searchLossCardNo;
	}

	public String getSearchRecevieCardNo() {
		return searchRecevieCardNo;
	}

	public void setSearchRecevieCardNo(String searchRecevieCardNo) {
		this.searchRecevieCardNo = searchRecevieCardNo;
	}

	public List<Cardinfo> getLsCardinfos() {
		return lsCardinfos;
	}

	public void setLsCardinfos(List<Cardinfo> lsCardinfos) {
		this.lsCardinfos = lsCardinfos;
	}

	public String getSearchCardNoKey() {
		return searchCardNoKey;
	}

	public void setSearchCardNoKey(String searchCardNoKey) {
		this.searchCardNoKey = searchCardNoKey;
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

	public String getSearchMemberPcidKey() {
		return searchMemberPcidKey;
	}

	public void setSearchMemberPcidKey(String searchMemberPcidKey) {
		this.searchMemberPcidKey = searchMemberPcidKey;
	}

	public String getStrCurPackageNo() {
		return strCurPackageNo;
	}

	public void setStrCurPackageNo(String strCurPackageNo) {
		this.strCurPackageNo = strCurPackageNo;
	}

	public List<Mcardchangeinfo> getLsMcardchangeinfo() {
		return lsMcardchangeinfo;
	}

	public void setLsMcardchangeinfo(List<Mcardchangeinfo> lsMcardchangeinfo) {
		this.lsMcardchangeinfo = lsMcardchangeinfo;
	}

	public Mcardchangeinfo getCurMcardchangeinfo() {
		return curMcardchangeinfo;
	}

	public void setCurMcardchangeinfo(Mcardchangeinfo curMcardchangeinfo) {
		this.curMcardchangeinfo = curMcardchangeinfo;
	}

	public String getStrCurBillId() {
		return strCurBillId;
	}

	public void setStrCurBillId(String strCurBillId) {
		this.strCurBillId = strCurBillId;
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

	@Action(value = "loadMasterinfos",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadMasterinfos()
	{
		this.lsMcardchangeinfo=this.cc009Service.loadMasterDateByCompId(this.strCurCompId);
		if(this.lsMcardchangeinfo==null || lsMcardchangeinfo.size()==0)
		{
			this.lsMcardchangeinfo=new ArrayList();
			
		}
		this.curMcardchangeinfo=this.cc009Service.addMastRecord(this.strCurCompId);
		lsMcardchangeinfo.add(0,curMcardchangeinfo);
		this.lsStaffinfo=this.cc009Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
		strSalePayMode=this.cc009Service.getDataTool().loadSysParam(strCurCompId,"SP068");
		strShareCondition=this.cc009Service.getDataTool().loadSysParam(strCurCompId,"SP102");
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadCurMcardchangeinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMcardchangeinfo()
	{
		this.curMcardchangeinfo=this.cc009Service.loadcurMaster(this.strCurCompId,this.strCurBillId);
		if(curMcardchangeinfo==null)
			curMcardchangeinfo=this.cc009Service.addMastRecord(strCurCompId);
		if(!CommonTool.FormatString(curMcardchangeinfo.getChangebeforcardtype()).equals(""))
		{
			curMcardchangeinfo.setChangebeforcardtypename(this.cc009Service.getDataTool().loadCardTypeName(strCurCompId, CommonTool.FormatString(curMcardchangeinfo.getChangebeforcardtype()), new StringBuffer()));
		}
		if(!CommonTool.FormatString(curMcardchangeinfo.getChangeaftercardtype()).equals(""))
		{
			curMcardchangeinfo.setChangeaftercardtypename(this.cc009Service.getDataTool().loadCardTypeName(strCurCompId, CommonTool.FormatString(curMcardchangeinfo.getChangeaftercardtype()), new StringBuffer()));
		}
		strSP114=this.cc009Service.getDataTool().loadSysParam(strCurCompId,"SP114");
		this.lsDpayinfo=this.cc009Service.loadDpayinfoByBill(strCurCompId,this.strCurBillId);
		this.lsDcardchangeinfo=this.cc009Service.loadDcardchangeinfoByBill(strCurCompId,this.strCurBillId);
		if(lsDcardchangeinfo!=null && lsDcardchangeinfo.size()>0)
		{
			for(int i=0;i<lsDcardchangeinfo.size();i++)
			{
				lsDcardchangeinfo.get(i).setBoldcardno(CommonTool.FormatString(lsDcardchangeinfo.get(i).getId().getOldcardno()));
				lsDcardchangeinfo.get(i).setTotalaccountkeepamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatBigDecimal(lsDcardchangeinfo.get(i).getCuraccountkeepamt()).doubleValue()+CommonTool.FormatBigDecimal(lsDcardchangeinfo.get(i).getProaccountkeepamt()).doubleValue())));
				lsDcardchangeinfo.get(i).setTotalaccountdebtamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatBigDecimal(lsDcardchangeinfo.get(i).getCuraccountdebtamt()).doubleValue()+CommonTool.FormatBigDecimal(lsDcardchangeinfo.get(i).getProaccountdebtamt()).doubleValue())));
				if(!CommonTool.FormatString(lsDcardchangeinfo.get(i).getOldcardtype()).equals(""))
				{
					lsDcardchangeinfo.get(i).setOldcardtypename(this.cc009Service.getDataTool().loadCardTypeName(strCurCompId, CommonTool.FormatString(lsDcardchangeinfo.get(i).getOldcardtype()), new StringBuffer()));
				}
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public List<Dpayinfo> getLsDpayinfo() {
		return lsDpayinfo;
	}

	public void setLsDpayinfo(List<Dpayinfo> lsDpayinfo) {
		this.lsDpayinfo = lsDpayinfo;
	}

	@Action(value = "searchCardinfos",  results = { 
			 @Result(name = "load_success", type = "json"),
  @Result(name = "load_failure", type = "json")	
	}) 
	public String searchCardinfos()
	{
		this.lsCardinfos=this.cc009Service.getDataTool().searchCardAllinfo(strCurCompId, searchCardNoKey, searchMemberNameKey, searchMemberPhoneKey, searchMemberPcidKey);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "validateGsCardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateGsCardno()
	{
		try
		{
			this.strMessage="";
			this.curCardinfo=this.cc009Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId, this.strCurCardNo);
			if(this.curCardinfo==null)
			{
				this.strMessage="该会员卡号在系统中不存在!";
				return SystemFinal.LOAD_FAILURE; 
			}
			else if(this.curCardinfo.getCardstate()!=1 )
			{
				this.strMessage="该会员卡为不可作为补卡新卡!";
				return SystemFinal.LOAD_FAILURE; 
			}
			
			if(!CommonTool.FormatString(this.curCardinfo.getId().getCardvesting()).equals(strCurCompId))
			{
				this.strMessage="该会员卡不是本门店的卡,请确认!";
				return SystemFinal.LOAD_FAILURE;
			}
			if(iCurChangeType==0 || iCurChangeType==1  || iCurChangeType==2 ) //转卡
			{
				if(CommonTool.FormatInteger(this.curCardinfo.getChangeflag())!=1 )
				{
					this.strMessage="该会员卡为不可作为转卡新卡!";
					return SystemFinal.LOAD_FAILURE; 
				}
			}
			if(iCurChangeType==7 ) //老卡并新卡卡
			{
				if(CommonTool.FormatInteger(this.curCardinfo.getChangeflag())!=1 )
				{
					this.strMessage="该会员卡为不可作为并卡新卡!";
					return SystemFinal.LOAD_FAILURE; 
				}
			}
			this.setLowestAmt(0d);
			if(iCurChangeType==0 || iCurChangeType==1 ) //折扣转卡
			{
				//if(CommonTool.FormatString(this.cc009Service.getDataTool().loadSysParam(strCurCompId, "SP043")).equals("1"))
				//{
					StringBuffer strValue = new StringBuffer();
					if(CommonTool.FormatInteger(curCardinfo.getChangerule())==1)//标准转卡
					{
						this.setLowestAmt(CommonTool.FormatDouble(this.cc009Service.getDataTool().getChangeCardClass(strCurCompId,
								this.strCurCardType,curCardinfo.getCardtype(),strValue)));
						if(CommonTool.FormatString(strValue.toString()).equals("Y")==false)
						{
							this.setStrMessage(this.strCurCardTypeName+"类型的卡不可以转到"+curCardinfo.getCardtypeName()+"卡");
						}
					}
					else //余额转卡
					{
						//this.setLowestAmt(GymTool.FormatDouble(cardinfo.getDOpenCardLowerAmt())-GymTool.FormatDouble(this.curMaster.getGea24f()));
						this.setLowestAmt(CommonTool.FormatBigDecimal(curCardinfo.getDSaleLowAmt()).doubleValue()-CommonTool.FormatDouble(curCardSaleCardAmt).doubleValue());
						
					}
					/*//老3折金卡转新（A2）3.5金卡充值4000就可以转卡，A2金卡转AZ钻石卡充值2万就可以转卡，其它转按余额转卡
					if(CommonTool.FormatString(this.strCurCardType).equals("E")&&CommonTool.FormatString(curCardinfo.getCardtype()).equals("A2"))
					{
						this.setLowestAmt(4000d);
					}
					if(CommonTool.FormatString(this.strCurCardType).equals("A2")&&CommonTool.FormatString(curCardinfo.getCardtype()).equals("AZ"))
					{
						this.setLowestAmt(20000d);
					}*/
				//}
			}
			this.lsStaffinfoSimple=this.cc009Service.getDataTool().loadOldCustomerInfo(strCurCompId,strCurCardNo);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}

	@Action(value = "validateOldCardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateOldCardno()
	{
		this.curCardinfo= this.cc009Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId,this.strCurCardNo);
		if(curCardinfo == null || curCardinfo.getId()==null || CommonTool.FormatString(curCardinfo.getCardtype()).equals(""))
		{
			this.setStrMessage("该会员卡不存在!");
			return SystemFinal.LOAD_FAILURE;
		}
		if(iCurChangeType!=1 && CommonTool.FormatInteger(curCardinfo.getCardsource())!= 0)
		{
			this.setStrMessage("非内部卡不可以转卡!");
			return SystemFinal.LOAD_FAILURE;
		}
		if(iCurChangeType==1 && CommonTool.FormatInteger(curCardinfo.getCardsource())== 0)
		{
			this.setStrMessage("内部卡不可以做收购转卡!");
			return SystemFinal.LOAD_FAILURE;
		}
		if(curCardinfo.getCardstate() != 4 && curCardinfo.getCardstate() != 5)
		{
			StringBuffer strTmpDataValue = new StringBuffer();
			this.setStrMessage("对不起，会员卡当前状态不可转卡!");
			return SystemFinal.LOAD_FAILURE;
		}
		if(CommonTool.FormatString(this.cc009Service.getDataTool().loadSysParam(strCurCompId, "SP044")).equals("0")
				&& CommonTool.FormatBigDecimal(curCardinfo.getAccount2debtAmt()).doubleValue()<0)
		{
			this.setStrMessage("有欠款的卡不可以折扣转卡!");
			return SystemFinal.LOAD_FAILURE; 
		}
	   
		if(iCurChangeType==1)
		{
			/*if(CommonTool.FormatString(this.cc009Service.getDataTool().loadSysParam(strCurCompId, "SP043")).equals("1"))
			{
				if(this.cc009Service.getDataTool().checkCardClassCanChange(strCurCompId,curCardinfo.getCardtype())==false)
				{
					this.setStrMessage("此类型的卡不可以转卡！");
					return SystemFinal.LOAD_FAILURE;
					
				}
			}*/
		}
		
		//验证是否是跨事业部转卡
		if(!curCardinfo.getId().getCardvesting().equals("001")&&this.cc009Service.getDataTool().canOptionChangeCard_shiye(strCurCompId,strCurCardNo)==false)
		{
			this.setStrMessage("该卡不可跨事业部转卡!");
			return SystemFinal.LOAD_FAILURE;
		}
		this.lsStaffinfoSimple=this.cc009Service.getDataTool().loadOldCustomerInfo(strCurCompId,strCurCardNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "delete",  results = { 
			 @Result(name = "delete_success", type = "json"),
          @Result(name = "delete_failure", type = "json")	
       }) 
	@Override
	public String delete()
	{
		try
		{
			return SystemFinal.DELETE_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.DELETE_FAILURE;
		}
	}

	
	@Action(value = "searchBill",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	})
	public String searchBill()
	{
		try
		{
			this.lsMcardchangeinfo=this.cc009Service.loadMasterDateByCard(this.strCurCompId, strSearchContent);//this.searchLossCardNo, this.searchRecevieCardNo);
			return SystemFinal.LOAD_SUCCESS; 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	
	@Action(value = "viewTicketReport",  results = { 
			 @Result(name = "load_success", type = "json"),
        @Result(name = "load_failure", type = "json")	
   }) 
	public String viewTicketReport()
	{
		Map values = this.cc009Service.getDataTool().getReportParams( this.strCurCompId, this.strCurBillId);
	    this.billDate = CommonTool.getDateMask(this.billDate);
	    this.companName = values.get("companName").toString();
	    this.companTel = values.get("companTel").toString();
	    this.companAddr = values.get("companAddr").toString();
	    this.cashMemberName=values.get("cashMemberId").toString();
	    this.printTime = CommonTool.getTimeMask(CommonTool.getCurrTime(), 1);
	    this.printDate = CommonTool.getDateMask(CommonTool.getCurrDate());
	    this.payTypeList = this.cc009Service.getDataTool().loadPayInfoByBillId(this.strCurCompId, this.strCurBillId,"ZK");
	    this.curCardinfo = this.cc009Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId, this.strCardNo);
	    values=null;
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "handInsertShare",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String handInsertShare()
	{
		this.strMessage="";
		boolean flag=this.cc009Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
		if(flag==false)
		{
			this.strMessage="该经理密码不对,请核实";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@JSON(serialize=false)
	public CC009Service getCc009Service() {
		return cc009Service;
	}
    @JSON(serialize=false)
	public void setCc009Service(CC009Service cc009Service) {
		this.cc009Service = cc009Service;
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
		curMcardchangeinfo.setChangedate(CommonTool.setDateMask(curMcardchangeinfo.getChangedate()));
		curMcardchangeinfo.setChangetime(CommonTool.setTimeMask(curMcardchangeinfo.getChangetime(), 1));
		curMcardchangeinfo.setFinancedate(CommonTool.getCurrDate());
		curMcardchangeinfo.setOperationdate(CommonTool.getCurrDate());
		curMcardchangeinfo.setOperationer(CommonTool.getLoginInfo("COMPID"));
		curMcardchangeinfo.setBillflag(1);
		curMcardchangeinfo.setChangeaftercardno(curMcardchangeinfo.getChangeaftercardno().trim());
		curMcardchangeinfo.setChangebeforcardno(curMcardchangeinfo.getChangebeforcardno().trim());
		curMcardchangeinfo.getId().setChangebillid(this.cc009Service.getDataTool().loadBillIdByRule(curMcardchangeinfo.getId().getChangecompid(),"mcardchangeinfo", "changebillid", "SP010"));
		this.lsDpayinfo=this.cc009Service.getDataTool().loadDTOList(strJsonParam, Dpayinfo.class);
		if(lsDpayinfo!=null && lsDpayinfo.size()>0)
		{
			for(int i=0;i<lsDpayinfo.size();i++)
			{
				if(lsDpayinfo.get(i)!=null && CommonTool.FormatBigDecimal(lsDpayinfo.get(i).getPayamt()).doubleValue()>0)
				{
					lsDpayinfo.get(i).setId(new DpayinfoId(curMcardchangeinfo.getId().getChangecompid(),curMcardchangeinfo.getId().getChangebillid(),"ZK",i*1.0));
				}
				else
				{
					lsDpayinfo.remove(i);
					i--;
				}
			}
		}
		this.lsDcardchangeinfo=this.cc009Service.getDataTool().loadDTOList(strJsonParamOldCard, Dcardchangeinfo.class);
		if(lsDcardchangeinfo!=null && lsDcardchangeinfo.size()>0)
		{
			for(int i=0;i<lsDcardchangeinfo.size();i++)
			{
				if(lsDcardchangeinfo.get(i)!=null && !CommonTool.FormatString(lsDcardchangeinfo.get(i).getBoldcardno()).equals(""))
				{
					lsDcardchangeinfo.get(i).setId(new DcardchangeinfoId(curMcardchangeinfo.getId().getChangecompid(),curMcardchangeinfo.getId().getChangebillid(),lsDcardchangeinfo.get(i).getBoldcardno()));
				}
				else
				{
					lsDcardchangeinfo.remove(i);
					i--;
				}
			}
		}
		
		return true;
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
		
			this.strMessage="";
			StringBuffer errorMess=new StringBuffer();
			//来源是美容的,烫染师不能分享业绩!
			if(curMcardchangeinfo.getBillinsertype()== 1 ){
				boolean b = this.getCc009Service().getDataTool().chekIsHotDyeDivision(curMcardchangeinfo);
				if(b){
					this.strMessage="来源是美容的,烫染师不能分享业绩!";
					return SystemFinal.POST_FAILURE;
				}
			}
			boolean canPostFlag=this.cc009Service.getDataTool().canSaveBill(this.curMcardchangeinfo.getId().getChangecompid(), CommonTool.getCurrDate(),errorMess);
			if(canPostFlag==false)
			{
				this.setStrMessage(errorMess.toString());
				return SystemFinal.POST_FAILURE; 
			}
			if(this.cc009Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC009", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			//如果美容部业绩大于30000 美容经理和顾问提成
			float comm=this.cc009Service.getDataTool().getBeautyDepartmentPerformance(curMcardchangeinfo);
			if(comm>30000)
			{
				int index=0;
				comm=(float) ((comm-30000));
				if(CommonTool.isEmpty(curMcardchangeinfo.getBeautyManager()))
				{
					index++;
					//curReEditBillInfo.setBeautyPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curMcardchangeinfo.getConsultant()))
				{
					index++;
					//curReEditBillInfo.setConsultantPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curMcardchangeinfo.getConsultant1()))
				{
					index++;
					//curReEditBillInfo.setConsultant1Perf(BigDecimal.valueOf(comm));
				}
				comm=comm/index;
				if(CommonTool.isEmpty(curMcardchangeinfo.getBeautyManager()))
				{
					//index++;
					curMcardchangeinfo.setBeautyPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curMcardchangeinfo.getConsultant()))
				{
					//index++;
					curMcardchangeinfo.setConsultantPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curMcardchangeinfo.getConsultant1()))
				{
					//index++;
					curMcardchangeinfo.setConsultant1Perf(BigDecimal.valueOf(comm));
				}
			}
			boolean flag=this.cc009Service.postChangeInfo(this.curMcardchangeinfo, this.lsDpayinfo,this.lsDcardchangeinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc009Service.handConfirmCard(this.curMcardchangeinfo.getId().getChangecompid(),this.curMcardchangeinfo.getId().getChangebillid(),this.curMcardchangeinfo.getId().getChangetype(),this.curMcardchangeinfo.getChangebeforcardno(),curMcardchangeinfo.getChangeaftercardno());
			if(flag==false)
				this.strMessage="会员卡转卡失败";
			curMcardchangeinfo=null;
			lsDpayinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
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
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}

	public String getStrCurNewCardNo() {
		return strCurNewCardNo;
	}

	public void setStrCurNewCardNo(String strCurNewCardNo) {
		this.strCurNewCardNo = strCurNewCardNo;
	}

	public String getStrCurNewCardType() {
		return strCurNewCardType;
	}

	public void setStrCurNewCardType(String strCurNewCardType) {
		this.strCurNewCardType = strCurNewCardType;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}

	public List<Staffinfo> getLsStaffinfo() {
		return lsStaffinfo;
	}

	public void setLsStaffinfo(List<Staffinfo> lsStaffinfo) {
		this.lsStaffinfo = lsStaffinfo;
	}

	public String getStrSalePayMode() {
		return strSalePayMode;
	}

	public void setStrSalePayMode(String strSalePayMode) {
		this.strSalePayMode = strSalePayMode;
	}

	public int getICurChangeType() {
		return iCurChangeType;
	}

	public void setICurChangeType(int curChangeType) {
		iCurChangeType = curChangeType;
	}

	public String getStrCurCardType() {
		return strCurCardType;
	}

	public void setStrCurCardType(String strCurCardType) {
		this.strCurCardType = strCurCardType;
	}

	public String getStrCurCardTypeName() {
		return strCurCardTypeName;
	}

	public void setStrCurCardTypeName(String strCurCardTypeName) {
		this.strCurCardTypeName = strCurCardTypeName;
	}

	public String getStrCurNewCardTypeName() {
		return strCurNewCardTypeName;
	}

	public void setStrCurNewCardTypeName(String strCurNewCardTypeName) {
		this.strCurNewCardTypeName = strCurNewCardTypeName;
	}

	public double getCurCardSaleCardAmt() {
		return curCardSaleCardAmt;
	}

	public void setCurCardSaleCardAmt(double curCardSaleCardAmt) {
		this.curCardSaleCardAmt = curCardSaleCardAmt;
	}

	public String getStrJsonParamOldCard() {
		return strJsonParamOldCard;
	}

	public void setStrJsonParamOldCard(String strJsonParamOldCard) {
		this.strJsonParamOldCard = strJsonParamOldCard;
	}

	public List<Dcardchangeinfo> getLsDcardchangeinfo() {
		return lsDcardchangeinfo;
	}

	public void setLsDcardchangeinfo(List<Dcardchangeinfo> lsDcardchangeinfo) {
		this.lsDcardchangeinfo = lsDcardchangeinfo;
	}

	public String getStrSearchContent() {
		return strSearchContent;
	}

	public void setStrSearchContent(String strSearchContent) {
		this.strSearchContent = strSearchContent;
	}

	public String getStrCardNo() {
		return strCardNo;
	}

	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}

	public String getCashMemberId() {
		return cashMemberId;
	}

	public void setCashMemberId(String cashMemberId) {
		this.cashMemberId = cashMemberId;
	}

	public String getCompanName() {
		return companName;
	}

	public void setCompanName(String companName) {
		this.companName = companName;
	}

	public String getCompanTel() {
		return companTel;
	}

	public void setCompanTel(String companTel) {
		this.companTel = companTel;
	}

	public String getCompanNet() {
		return companNet;
	}

	public void setCompanNet(String companNet) {
		this.companNet = companNet;
	}

	public String getCompanAddr() {
		return companAddr;
	}

	public void setCompanAddr(String companAddr) {
		this.companAddr = companAddr;
	}

	public String getCashMemberName() {
		return cashMemberName;
	}

	public void setCashMemberName(String cashMemberName) {
		this.cashMemberName = cashMemberName;
	}

	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}

	public String getPrintDate() {
		return printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public List<Dpayinfo> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<Dpayinfo> payTypeList) {
		this.payTypeList = payTypeList;
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

	
	
}
