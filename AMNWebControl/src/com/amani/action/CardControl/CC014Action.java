package com.amani.action.CardControl;

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

import com.amani.action.AMN_ModuleAction;

import com.amani.model.Cardinfo;
import com.amani.model.Dcardchangeinfo;
import com.amani.model.DcardchangeinfoId;
import com.amani.model.Dcardchangetocardinfo;
import com.amani.model.Dpayinfo;
import com.amani.model.DpayinfoId;
import com.amani.model.Dsalecardproinfo;
import com.amani.model.DsalecardproinfoId;
import com.amani.model.Mcardchangeinfo;
import com.amani.model.Staffinfo;
import com.amani.service.CardControl.CC014Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc014")
public class CC014Action extends AMN_ModuleAction{
	@Autowired
	private CC014Service cc014Service;
	private String strJsonParam;	
	private String strJsonParamOldCard;	
	private String strCurPackageNo;
    private List<Mcardchangeinfo> lsMcardchangeinfo;
    private Mcardchangeinfo curMcardchangeinfo;
    private List<Dpayinfo> lsDpayinfo;
    private List<Dcardchangetocardinfo>	lsDcardchangetocardinfo;
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
	private double curCardSaleCardAmt;
	private double lowestAmt;
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
		this.lsMcardchangeinfo=this.cc014Service.loadMasterDateByCompId(this.strCurCompId);
		if(this.lsMcardchangeinfo==null || lsMcardchangeinfo.size()==0)
		{
			this.lsMcardchangeinfo=new ArrayList();
			
		}
		this.curMcardchangeinfo=this.cc014Service.addMastRecord(this.strCurCompId);
		lsMcardchangeinfo.add(0,curMcardchangeinfo);
		this.lsStaffinfo=this.cc014Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
		strSalePayMode=this.cc014Service.getDataTool().loadSysParam(strCurCompId,"SP068");
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadCurMcardchangeinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMcardchangeinfo()
	{
		this.curMcardchangeinfo=this.cc014Service.loadcurMaster(this.strCurCompId,this.strCurBillId);
		if(curMcardchangeinfo==null)
			curMcardchangeinfo=this.cc014Service.addMastRecord(strCurCompId);
		if(!CommonTool.FormatString(curMcardchangeinfo.getChangebeforcardtype()).equals(""))
		{
			curMcardchangeinfo.setChangebeforcardtypename(this.cc014Service.getDataTool().loadCardTypeName(strCurCompId, CommonTool.FormatString(curMcardchangeinfo.getChangebeforcardtype()), new StringBuffer()));
		}
		if(!CommonTool.FormatString(curMcardchangeinfo.getChangeaftercardtype()).equals(""))
		{
			curMcardchangeinfo.setChangeaftercardtypename(this.cc014Service.getDataTool().loadCardTypeName(strCurCompId, CommonTool.FormatString(curMcardchangeinfo.getChangeaftercardtype()), new StringBuffer()));
		}
		//this.lsDpayinfo=this.cc014Service.loadDpayinfoByBill(strCurCompId,this.strCurBillId);
		lsDcardchangetocardinfo=this.cc014Service.loadDcardchangetocardinfoByBill(strCurCompId,this.strCurBillId);
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
		this.lsCardinfos=this.cc014Service.getDataTool().searchCardAllinfo(strCurCompId, searchCardNoKey, searchMemberNameKey, searchMemberPhoneKey, searchMemberPcidKey);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	

	@Action(value = "validateTkCardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateTkCardno()
	{
		this.curCardinfo= this.cc014Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId,this.strCurCardNo);
		if(curCardinfo == null || curCardinfo.getId()==null || CommonTool.FormatString(curCardinfo.getCardtype()).equals(""))
		{
			this.setStrMessage("该会员卡不存在!");
			return SystemFinal.LOAD_FAILURE;
		}
		if(iCurChangeType!=1 && CommonTool.FormatInteger(curCardinfo.getCardsource())!= 0)
		{
			this.setStrMessage("非内部卡不可以退卡!");
			return SystemFinal.LOAD_FAILURE;
		}
		
		if(curCardinfo.getCardstate() != 4 && curCardinfo.getCardstate() != 5)
		{
			StringBuffer strTmpDataValue = new StringBuffer();
			this.setStrMessage("对不起，会员卡当前状态不可退卡!");
			return SystemFinal.LOAD_FAILURE;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "validateNewCardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateNewCardno()
	{
		this.curCardinfo= this.cc014Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId,this.strCurCardNo);
		if(curCardinfo == null || curCardinfo.getId()==null || CommonTool.FormatString(curCardinfo.getCardtype()).equals(""))
		{
			this.setStrMessage("该会员卡不存在!");
			return SystemFinal.LOAD_FAILURE;
		}
		if(curCardinfo.getCardstate() != 1 )
		{
			StringBuffer strTmpDataValue = new StringBuffer();
			this.setStrMessage("对不起，会员卡当前状态不是未销售卡状态,请确认!");
			return SystemFinal.LOAD_FAILURE;
		}
		if(!CommonTool.FormatString(curCardinfo.getId().getCardvesting()).equals(this.strCurCompId) )
		{
			StringBuffer strTmpDataValue = new StringBuffer();
			this.setStrMessage("对不起，会员卡归属门店不是"+this.strCurCompId+",请确认!");
			return SystemFinal.LOAD_FAILURE;
		}
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
			this.lsMcardchangeinfo=this.cc014Service.loadMasterDateByCard(this.strCurCompId, strSearchContent);//this.searchLossCardNo, this.searchRecevieCardNo);
			return SystemFinal.LOAD_SUCCESS; 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	
	@JSON(serialize=false)
	public CC014Service getCc014Service() {
		return cc014Service;
	}
    @JSON(serialize=false)
	public void setCc014Service(CC014Service cc014Service) {
		this.cc014Service = cc014Service;
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
		curMcardchangeinfo.setBillflag(4);
		curMcardchangeinfo.setChangefillamt(new BigDecimal(CommonTool.FormatBigDecimal(this.curMcardchangeinfo.getCashfillamt()).doubleValue()+CommonTool.FormatBigDecimal(this.curMcardchangeinfo.getBankfillamt()).doubleValue()));
		curMcardchangeinfo.getId().setChangebillid(this.cc014Service.getDataTool().loadBillIdByRule(curMcardchangeinfo.getId().getChangecompid(),"mcardchangeinfo", "changebillid", "SP010"));
		this.lsDcardchangetocardinfo=this.cc014Service.getDataTool().loadDTOList(strJsonParam, Dcardchangetocardinfo.class);
		if(lsDcardchangetocardinfo!=null && lsDcardchangetocardinfo.size()>0)
		{
			for(int i=0;i<lsDcardchangetocardinfo.size();i++)
			{
				if(lsDcardchangetocardinfo.get(i)!=null && !CommonTool.FormatString(lsDcardchangetocardinfo.get(i).getCardno()).equals(""))
				{
					lsDcardchangetocardinfo.get(i).setChangecompid(curMcardchangeinfo.getId().getChangecompid());
					lsDcardchangetocardinfo.get(i).setChangebillid(curMcardchangeinfo.getId().getChangebillid());
				}
				else
				{
					lsDcardchangetocardinfo.remove(i);
					i--;
				}
			}
		}
		lsDpayinfo=new ArrayList();
		Dpayinfo payRecord=null;
		if( CommonTool.FormatBigDecimal(this.curMcardchangeinfo.getCashfillamt()).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(curMcardchangeinfo.getId().getChangecompid(),curMcardchangeinfo.getId().getChangebillid(),"TK",0));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.curMcardchangeinfo.getCashfillamt()));
			payRecord.setPaymode("1");
			lsDpayinfo.add(payRecord);
		}
		if( CommonTool.FormatBigDecimal(this.curMcardchangeinfo.getBankfillamt()).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(curMcardchangeinfo.getId().getChangecompid(),curMcardchangeinfo.getId().getChangebillid(),"TK",1));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.curMcardchangeinfo.getBankfillamt()));
			payRecord.setPaymode("6");
			lsDpayinfo.add(payRecord);
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
			//来源是美容的,烫染师不能分享业绩!
			if(curMcardchangeinfo.getBillinsertype().equals("1")){
				boolean b = this.getCc014Service().getDataTool().chekIsHotDyeDivision(curMcardchangeinfo);
				if(b){
					this.strMessage="来源是美容的,烫染师不能分享业绩!";
					return SystemFinal.POST_FAILURE;
				}
			}
			if(this.cc014Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC014", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.cc014Service.postChangeInfo(this.curMcardchangeinfo, this.lsDpayinfo,this.lsDcardchangetocardinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc014Service.postConfirmInfo(this.curMcardchangeinfo.getId().getChangecompid(), this.curMcardchangeinfo.getId().getChangebillid(), this.curMcardchangeinfo.getChangebeforcardno(), 2);
			if(flag==false)
			{
				this.strMessage="操作退卡失败!";
				return SystemFinal.POST_FAILURE;
			}
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
	@Action(value = "confirmBackCard",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
    public String confirmBackCard()
	{
		try
		{
		
			this.strMessage="";
			if(this.cc014Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX007")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.cc014Service.postConfirmInfo(this.strCurCompId, this.strCurBillId, this.strCurCardNo, 0);
			if(flag==false)
			{
				this.strMessage="操作失败!";
				return SystemFinal.POST_FAILURE;
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "rejectBackCard",  results = { 
			 @Result(name = "post_success", type = "json"),
         @Result(name = "post_failure", type = "json")	
      }) 
   public String rejectBackCard()
	{
		try
		{
		
			this.strMessage="";
			if(this.cc014Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX008")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.cc014Service.postConfirmInfo(this.strCurCompId, this.strCurBillId, this.strCurCardNo, 1);
			if(flag==false)
			{
				this.strMessage="操作失败!";
				return SystemFinal.POST_FAILURE;
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "effectiveBackCard",  results = { 
			 @Result(name = "post_success", type = "json"),
         @Result(name = "post_failure", type = "json")	
      }) 
   public String effectiveBackCard()
	{
		try
		{
		
			this.strMessage="";
			if(this.cc014Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX009")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.cc014Service.postConfirmInfo(this.strCurCompId, this.strCurBillId, this.strCurCardNo, 2);
			if(flag==false)
			{
				this.strMessage="操作失败!";
				return SystemFinal.POST_FAILURE;
			}
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


	public String getStrSearchContent() {
		return strSearchContent;
	}

	public void setStrSearchContent(String strSearchContent) {
		this.strSearchContent = strSearchContent;
	}

	public List<Dcardchangetocardinfo> getLsDcardchangetocardinfo() {
		return lsDcardchangetocardinfo;
	}

	public void setLsDcardchangetocardinfo(
			List<Dcardchangetocardinfo> lsDcardchangetocardinfo) {
		this.lsDcardchangetocardinfo = lsDcardchangetocardinfo;
	}

	
	
}
