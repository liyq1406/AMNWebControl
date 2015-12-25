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
import com.amani.model.Dreturnproinfo;
import com.amani.model.DreturnproinfoId;
import com.amani.model.Mreturnproinfo;
import com.amani.model.Projectinfo;
import com.amani.model.Staffinfo;
import com.amani.service.CardControl.CC017Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc017")
public class CC017Action extends AMN_ModuleAction{
	@Autowired
	private CC017Service cc017Service;
	private String strJsonParam;	
	private String strChangeJsonParam;	
	private String strJsonParamOldCard;	
	private String strCurPackageNo;
    private List<Mreturnproinfo> lsMreturnproinfo;
    private Mreturnproinfo curMreturnproinfo;
    private List<Dreturnproinfo>	lsDreturnproinfoA;
    private List<Dreturnproinfo>	lsDreturnproinfoB;
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
	private String strCurItemId;
	private Projectinfo curProjectinfo;
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
		this.lsMreturnproinfo=this.cc017Service.loadMasterDateByCompId(this.strCurCompId);
		if(this.lsMreturnproinfo==null || lsMreturnproinfo.size()==0)
		{
			this.lsMreturnproinfo=new ArrayList();
			
		}
		this.curMreturnproinfo=this.cc017Service.addMastRecord(this.strCurCompId);
		lsMreturnproinfo.add(0,curMreturnproinfo);
		this.lsStaffinfo=this.cc017Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadCurMcardchangeinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMcardchangeinfo()
	{
		this.curMreturnproinfo=this.cc017Service.loadcurMaster(this.strCurCompId,this.strCurBillId);
		if(curMreturnproinfo==null)
			curMreturnproinfo=this.cc017Service.addMastRecord(strCurCompId);
		if(!CommonTool.FormatString(curMreturnproinfo.getCardtype()).equals(""))
		{
			curMreturnproinfo.setCardtypename(this.cc017Service.getDataTool().loadCardTypeName(strCurCompId, CommonTool.FormatString(curMreturnproinfo.getCardtype()), new StringBuffer()));
		}
		if(!CommonTool.FormatString(curMreturnproinfo.getOpencardtype()).equals(""))
		{
			curMreturnproinfo.setOpencardtypename(this.cc017Service.getDataTool().loadCardTypeName(strCurCompId, CommonTool.FormatString(curMreturnproinfo.getOpencardtype()), new StringBuffer()));
		}
		this.lsDreturnproinfoA=this.cc017Service.loadDreturnproinfoByBill(strCurCompId,this.strCurBillId,1);
		this.lsDreturnproinfoB=this.cc017Service.loadDreturnproinfoByBill(strCurCompId,this.strCurBillId,2);
		return SystemFinal.LOAD_SUCCESS;
	}
	


	

	@Action(value = "validateTkCardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateTkCardno()
	{
		this.curCardinfo= this.cc017Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId,this.strCurCardNo);
		if(curCardinfo == null || curCardinfo.getId()==null || CommonTool.FormatString(curCardinfo.getCardtype()).equals(""))
		{
			this.setStrMessage("该会员卡不存在!");
			return SystemFinal.LOAD_FAILURE;
		}
		if(iCurChangeType!=1 && CommonTool.FormatInteger(curCardinfo.getCardsource())!= 0)
		{
			this.setStrMessage("非内部卡不可以退疗程!");
			return SystemFinal.LOAD_FAILURE;
		}
		
		if(curCardinfo.getCardstate() != 4 && curCardinfo.getCardstate() != 5)
		{
			StringBuffer strTmpDataValue = new StringBuffer();
			this.setStrMessage("对不起，会员卡当前状态不可退疗程!");
			return SystemFinal.LOAD_FAILURE;
		}
		this.lsDreturnproinfoA=this.cc017Service.loadProInfosByCardNo(this.strCurCompId,this.strCurCardNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "validateNewCardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateNewCardno()
	{
		this.curCardinfo= this.cc017Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId,this.strCurCardNo);
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
	
	@Action(value = "validateItem",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateItem()
	{
		try
		{
			
			this.curProjectinfo=this.cc017Service.getDataTool().loadProjectInfo(strCurCompId, this.strCurItemId);
		
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}

	
	@Action(value = "searchBill",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	})
	public String searchBill()
	{
		try
		{
			this.lsMreturnproinfo=this.cc017Service.loadMasterDateByCard(this.strCurCompId, strSearchContent);//this.searchLossCardNo, this.searchRecevieCardNo);
			return SystemFinal.LOAD_SUCCESS; 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	
	@JSON(serialize=false)
	public CC017Service getCc017Service() {
		return cc017Service;
	}
    @JSON(serialize=false)
	public void setCc017Service(CC017Service cc017Service) {
		this.cc017Service = cc017Service;
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
		curMreturnproinfo.setReturndate(CommonTool.setDateMask(curMreturnproinfo.getReturndate()));
		curMreturnproinfo.setReturntime(CommonTool.setTimeMask(curMreturnproinfo.getReturntime(), 1));
		curMreturnproinfo.getId().setReturnbillid(this.cc017Service.getDataTool().loadBillIdByRule(curMreturnproinfo.getId().getReturncompid(),"mreturnproinfo", "returnbillid", "SP010"));
		this.lsDreturnproinfoA=this.cc017Service.getDataTool().loadDTOList(strJsonParam, Dreturnproinfo.class);
		if(lsDreturnproinfoA!=null && lsDreturnproinfoA.size()>0)
		{
			for(int i=0;i<lsDreturnproinfoA.size();i++)
			{
				if(lsDreturnproinfoA.get(i)!=null && !CommonTool.FormatString(lsDreturnproinfoA.get(i).getChangeproid()).equals(""))
				{
					lsDreturnproinfoA.get(i).setId(new DreturnproinfoId(curMreturnproinfo.getId().getReturncompid(),curMreturnproinfo.getId().getReturnbillid(),1,lsDreturnproinfoA.get(i).getBreturnseqno()));
					if(lsDreturnproinfoA.get(i).getReturnflag()==null || lsDreturnproinfoA.get(i).getReturnflag()==false)
					{
						lsDreturnproinfoA.get(i).setChangeflag(0);
					}
					else
					{
						lsDreturnproinfoA.get(i).setChangeflag(1);
					}
				}
				else
				{
					lsDreturnproinfoA.remove(i);
					i--;
				}
			}
		}
		
		this.lsDreturnproinfoB=this.cc017Service.getDataTool().loadDTOList(strChangeJsonParam, Dreturnproinfo.class);
		if(lsDreturnproinfoB!=null && lsDreturnproinfoB.size()>0)
		{
			for(int i=0;i<lsDreturnproinfoB.size();i++)
			{
				if(lsDreturnproinfoB.get(i)!=null && !CommonTool.FormatString(lsDreturnproinfoB.get(i).getChangeproid()).equals(""))
				{
					lsDreturnproinfoB.get(i).setId(new DreturnproinfoId(curMreturnproinfo.getId().getReturncompid(),curMreturnproinfo.getId().getReturnbillid(),2,i));
				}
				else
				{
					lsDreturnproinfoB.remove(i);
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
			if(this.cc017Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC017", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.cc017Service.postChangeInfo(this.curMreturnproinfo, this.lsDreturnproinfoA,this.lsDreturnproinfoB);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc017Service.afterPost(this.curMreturnproinfo.getId().getReturncompid(), this.curMreturnproinfo.getId().getReturnbillid());
			if(flag==false)
			{
				this.strMessage="退换疗程生成历史错误,请至会员卡资料查询核实!";
				return SystemFinal.POST_FAILURE;
			}
			curMreturnproinfo=null;
			lsDreturnproinfoA=null;
			lsDreturnproinfoB=null;
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

	public List<Mreturnproinfo> getLsMreturnproinfo() {
		return lsMreturnproinfo;
	}

	public void setLsMreturnproinfo(List<Mreturnproinfo> lsMreturnproinfo) {
		this.lsMreturnproinfo = lsMreturnproinfo;
	}

	public Mreturnproinfo getCurMreturnproinfo() {
		return curMreturnproinfo;
	}

	public void setCurMreturnproinfo(Mreturnproinfo curMreturnproinfo) {
		this.curMreturnproinfo = curMreturnproinfo;
	}


	public List<Dreturnproinfo> getLsDreturnproinfoA() {
		return lsDreturnproinfoA;
	}

	public void setLsDreturnproinfoA(List<Dreturnproinfo> lsDreturnproinfoA) {
		this.lsDreturnproinfoA = lsDreturnproinfoA;
	}

	public List<Dreturnproinfo> getLsDreturnproinfoB() {
		return lsDreturnproinfoB;
	}

	public void setLsDreturnproinfoB(List<Dreturnproinfo> lsDreturnproinfoB) {
		this.lsDreturnproinfoB = lsDreturnproinfoB;
	}

	public String getStrChangeJsonParam() {
		return strChangeJsonParam;
	}

	public void setStrChangeJsonParam(String strChangeJsonParam) {
		this.strChangeJsonParam = strChangeJsonParam;
	}

	
}
