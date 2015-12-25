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


import com.amani.model.Activitycardinfo;
import com.amani.model.Cardinfo;
import com.amani.model.Cardtypeinfo;
import com.amani.model.Corpsbuyinfo;
import com.amani.model.Dconsumeinfo;
import com.amani.model.DconsumeinfoId;
import com.amani.model.Dpayinfo;
import com.amani.model.DpayinfoId;
import com.amani.model.Dsalecardproinfo;
import com.amani.model.DsalecardproinfoId;
import com.amani.model.Memberinfo;
import com.amani.model.Msalecardinfo;
import com.amani.model.Projectinfo;
import com.amani.model.Staffinfo;


import com.amani.service.CardControl.CC007Service;
import com.amani.tools.CommonTool;
import com.amani.tools.DataTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc007")
public class CC007Action extends AMN_ModuleAction{
	@Autowired
	private CC007Service cc007Service;
	private String strJsonParam;	
	private String strJsonParam_pay;
	private String strJsonParam_pro;
	private String strCurCompId;
	private String strCurBillId;
    private Msalecardinfo curMsalecardinfo;
    private List<Msalecardinfo> lsMsalecardinfos;
    private List<Dsalecardproinfo> lsDsalecardproinfos;
    private List<Dpayinfo> lsDpayinfo;
    private String searchMemberCompIdKey;
    private String searchMemberNoKey;
    private String searchMemberNameKey;
    private String searchMemberPhoneKey;
    private String searchMemberPCIDKey;
	private List<Cardtypeinfo> lsCardtypeinfos;
	private List<Staffinfo> lsStaffinfo;
	private List<Projectinfo> lsProjectinfo;
	private String strCardNo;
    private Cardinfo curCardinfo;
    private String strSalePayMode;
	private String strSearchBillId;
	private String strCardType;
	private String strCorpscardno;
	private String strActivtyCardno;
    private BigDecimal cropsCardAmt;
    private BigDecimal activtyCardAmt;
    private String strShareCondition;
    private String strSP114;
    
	public String getStrSP114() {
		return strSP114;
	}
	public void setStrSP114(String strSP114) {
		this.strSP114 = strSP114;
	}
	/***********经理卡验证*******************/
    private String mangerCardNo;
	public String getMangerCardNo() {
		return mangerCardNo;
	}
	public void setMangerCardNo(String mangerCardNo) {
		this.mangerCardNo = mangerCardNo;
	}
	public String getStrShareCondition() {
		return strShareCondition;
	}
	public void setStrShareCondition(String strShareCondition) {
		this.strShareCondition = strShareCondition;
	}
	public BigDecimal getCropsCardAmt() {
		return cropsCardAmt;
	}
	public void setCropsCardAmt(BigDecimal cropsCardAmt) {
		this.cropsCardAmt = cropsCardAmt;
	}
	public String getStrSearchBillId() {
		return strSearchBillId;
	}
	public void setStrSearchBillId(String strSearchBillId) {
		this.strSearchBillId = strSearchBillId;
	}
	public Cardinfo getCurCardinfo() {
		return curCardinfo;
	}
	public void setCurCardinfo(Cardinfo curCardinfo) {
		this.curCardinfo = curCardinfo;
	}
	public List<Cardtypeinfo> getLsCardtypeinfos() {
		return lsCardtypeinfos;
	}
	public void setLsCardtypeinfos(List<Cardtypeinfo> lsCardtypeinfos) {
		this.lsCardtypeinfos = lsCardtypeinfos;
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
	@Override
	protected boolean beforePost() {
		curMsalecardinfo.setSaledate(CommonTool.setDateMask(curMsalecardinfo.getSaledate()));
		curMsalecardinfo.setSaletime(CommonTool.setTimeMask(curMsalecardinfo.getSaletime(), 1));
		curMsalecardinfo.setSaleroperdate(CommonTool.getCurrDate());
		curMsalecardinfo.setSaleroperator(CommonTool.getLoginInfo("USERID"));
		curMsalecardinfo.setFinancedate(CommonTool.getCurrDate());
		curMsalecardinfo.setSalekeepamt(CommonTool.FormatBigDecimal(curMsalecardinfo.getSaletotalamt()));
		curMsalecardinfo.setMemberbirthday(CommonTool.setDateMask(curMsalecardinfo.getMemberbirthday()));
		curMsalecardinfo.setSalecardno(curMsalecardinfo.getSalecardno().trim());
		curMsalecardinfo.getId().setSalebillid(this.cc007Service.getDataTool().loadBillIdByRule(curMsalecardinfo.getId().getSalecompid(),"msalecardinfo", "salebillid", "SP008"));
		this.lsDpayinfo=this.cc007Service.getDataTool().loadDTOList(strJsonParam_pay, Dpayinfo.class);
		if(lsDpayinfo!=null && lsDpayinfo.size()>0)
		{
			for(int i=0;i<lsDpayinfo.size();i++)
			{
				if(lsDpayinfo.get(i)!=null && CommonTool.FormatBigDecimal(lsDpayinfo.get(i).getPayamt()).doubleValue()>0)
				{
					lsDpayinfo.get(i).setId(new DpayinfoId(curMsalecardinfo.getId().getSalecompid(),curMsalecardinfo.getId().getSalebillid(),"SK",i*1.0));
				}
				else
				{
					lsDpayinfo.remove(i);
					i--;
				}
			}
		}
		this.lsDsalecardproinfos=this.cc007Service.getDataTool().loadDTOList(strJsonParam_pro, Dsalecardproinfo.class);
		if(lsDsalecardproinfos!=null && lsDsalecardproinfos.size()>0)
		{
			for(int i=0;i<lsDsalecardproinfos.size();i++)
			{
				if(lsDsalecardproinfos.get(i)!=null && !CommonTool.FormatString(lsDsalecardproinfos.get(i).getSaleproid()).equals(""))
				{
					lsDsalecardproinfos.get(i).setId(new DsalecardproinfoId(curMsalecardinfo.getId().getSalecompid(),curMsalecardinfo.getId().getSalebillid(),1,i*1.0));
				}
				else
				{
					lsDsalecardproinfos.remove(i);
					i--;
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
	@Action(value = "loadMasterinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
     @Result(name = "load_failure", type = "json")	
	}) 
	public String loadMasterinfo()
	{
		
		this.lsMsalecardinfos=this.cc007Service.loadMasterinfo(this.strCurCompId);
		if(this.lsMsalecardinfos==null )
		{
			this.lsMsalecardinfos=new ArrayList();
		}
		this.curMsalecardinfo=this.cc007Service.addMsalecardinfo(this.strCurCompId);
		lsMsalecardinfos.add(0,curMsalecardinfo);
	
		this.lsCardtypeinfos=this.cc007Service.getDataTool().loadCardType(strCurCompId);
		this.lsStaffinfo=this.cc007Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
		this.lsProjectinfo=this.cc007Service.getDataTool().loadProjectinfoByCompId(strCurCompId,1);
		strSalePayMode=this.cc007Service.getDataTool().loadSysParam(strCurCompId,"SP065");
		strShareCondition=this.cc007Service.getDataTool().loadSysParam(strCurCompId,"SP102");
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "searchCurRecord",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String searchCurRecord()
	{
		
		this.lsMsalecardinfos=this.cc007Service.searchMasterinfo(this.strCurCompId,this.strSearchBillId);
		if(this.lsMsalecardinfos==null )
		{
			this.lsMsalecardinfos=new ArrayList();
		}
	
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadCurMaster",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMaster()
	{
		
		this.curMsalecardinfo=this.cc007Service.loadMsalecardinfo(strCurCompId,this.strCurBillId);
		if(curMsalecardinfo!=null)
		{
			curMsalecardinfo.setSaledate(CommonTool.getDateMask(curMsalecardinfo.getSaledate()));
			curMsalecardinfo.setSaletime(CommonTool.getTimeMask(curMsalecardinfo.getSaletime(), 1));
		}
		else
		{
			this.curMsalecardinfo=this.cc007Service.addMsalecardinfo(this.strCurCompId);
		}
		if(!CommonTool.FormatString(this.curMsalecardinfo.getSalecardtype()).equals(""))
		{
			this.curMsalecardinfo.setSalecardtypename(this.cc007Service.getDataTool().loadCardTypeName(strCurCompId, this.curMsalecardinfo.getSalecardtype(), new StringBuffer()));
		}
		this.lsDpayinfo=this.cc007Service.loadDpayinfoByBill(strCurCompId,this.strCurBillId);
		strSP114=this.cc007Service.getDataTool().loadSysParam(strCurCompId,"SP114");
		this.lsDsalecardproinfos=this.cc007Service.loadDsalecardproinfo(strCurCompId,this.strCurBillId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "validateCorpscardno",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String validateCorpscardno()
	{
		try
		{
			this.strMessage="";
			double damt=this.cc007Service.loadCorpsbuyinfoById(strCorpscardno, strCardType);
			if(CommonTool.FormatDouble(damt)==0)
			{
				this.strMessage="对应该卡类别的团购编号不存在，请核对后再查询";
				return SystemFinal.LOAD_FAILURE; 
			}
			else
			{
				this.cropsCardAmt=CommonTool.FormatBigDecimal(new BigDecimal(damt));
			}
			
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	
	@Action(value = "dfgwCardNo",  results = { 
			 @Result(name = "load_success", type = "json"),
  @Result(name = "load_failure", type = "json")	
	}) 
	public String dfgwCardNo()
	{
		if(cc007Service.loadDfgwCard(strCorpscardno)==false)
		{
			this.strMessage="团购编号不存在或者不是当天消费的。或者不是东方购物券";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	
	@Action(value = "validateActivtycardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validateActivtycardno()
	{
		try
		{
			this.strMessage="";
			Activitycardinfo record=this.cc007Service.loadActifyCardinfo(this.strActivtyCardno, strCardType);
			if(record==null)
			{
				this.strMessage="对应该卡类别的活动券号不存在，请核对后再查询";
				return SystemFinal.LOAD_FAILURE; 
			}
			if(CommonTool.FormatInteger(record.getSalecardflag())==1)
			{
				this.strMessage="对活动券已经使用，请核对后再查询";
				return SystemFinal.LOAD_FAILURE; 
			}
			if(!CommonTool.FormatString(record.getValidatedate()).equals("")
				&& Integer.parseInt(record.getValidatedate())< Integer.parseInt(CommonTool.getCurrDate()))
			{
				this.strMessage="对活动券已经过期，请核对后再查询";
				return SystemFinal.LOAD_FAILURE; 
			}
			//double damt=this.cc007Service.loadActifyCardinfoById(this.strActivtyCardno, strCardType);
			if(CommonTool.FormatBigDecimal(record.getSalecarddeductamt()).doubleValue()==0)
			{
				this.strMessage="对应该卡类别的活动券号不存在，请核对后再查询";
				return SystemFinal.LOAD_FAILURE; 
			}
			else
			{
				this.activtyCardAmt=CommonTool.FormatBigDecimal(record.getSalecarddeductamt());
			}
			record=null;
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	@Action(value = "add",  results = { 
			 @Result(name = "add_success", type = "json"),
          @Result(name = "add_failure", type = "json")	
       }) 
	@Override
	public String add()
	{
		this.strMessage="";
		this.curMsalecardinfo=this.cc007Service.addMsalecardinfo(this.strCurCompId);
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
			this.strMessage="";
			StringBuffer errorMess=new StringBuffer();
			//来源是美容的,烫染师不能分享业绩!
			if(curMsalecardinfo.getBillinsertype() ==1){
				boolean b = this.getCc007Service().getDataTool().chekIsHotDyeDivision(curMsalecardinfo);
				if(b){
					this.strMessage="来源是美容的,烫染师不能分享业绩!";
					return SystemFinal.POST_FAILURE;
				}
			}
			
			boolean canPostFlag=this.cc007Service.getDataTool().canSaveBill(this.curMsalecardinfo.getId().getSalecompid(), CommonTool.getCurrDate(),errorMess);
			if(canPostFlag==false)
			{
				this.setStrMessage(errorMess.toString());
				return SystemFinal.POST_FAILURE; 
			}
			if(this.cc007Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC007", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			if(this.curMsalecardinfo.getId()==null || CommonTool.FormatString(this.curMsalecardinfo.getId().getSalebillid()).equals(""))
			{
				this.strMessage="生成销售单号有误,请刷新后重新保存此销售单据!";
				return SystemFinal.POST_FAILURE;
			}
			//如果美容部业绩大于30000 美容经理和顾问提成0.02
			float comm=this.cc007Service.getDataTool().getBeautyDepartmentPerformance(curMsalecardinfo);
			if(comm>30000)
			{
				int index=0;
				comm=(float) ((comm-30000));
				if(CommonTool.isEmpty(curMsalecardinfo.getBeautyManager()))
				{
					index++;
				}
				if(CommonTool.isEmpty(curMsalecardinfo.getConsultant()))
				{
					index++;
				}
				if(CommonTool.isEmpty(curMsalecardinfo.getConsultant1()))
				{
					index++;
				}
				comm=comm/index;
				if(CommonTool.isEmpty(curMsalecardinfo.getBeautyManager()))
				{
					//index++;
					curMsalecardinfo.setBeautyPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curMsalecardinfo.getConsultant()))
				{
					//index++;
					curMsalecardinfo.setConsultantPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curMsalecardinfo.getConsultant1()))
				{
					//index++;
					curMsalecardinfo.setConsultant1Perf(BigDecimal.valueOf(comm));
				}
			}
			boolean flag=this.cc007Service.postSaleInfo(this.curMsalecardinfo, this.lsDpayinfo,this.lsDsalecardproinfos);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc007Service.afterPost(curMsalecardinfo.getId().getSalecompid(), curMsalecardinfo.getId().getSalebillid(),curMsalecardinfo.getSalecardtype());
			if(flag==false)
			{
				this.strMessage="生成开卡信息有误,请到会员卡资料管理核实!";
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc007Service.handSonCardByTargetCard(curMsalecardinfo.getId().getSalecompid(), curMsalecardinfo.getSalecardno(), curMsalecardinfo.getSalecardtype(), curMsalecardinfo.getMembername(), curMsalecardinfo.getMemberphone(), CommonTool.FormatDouble(curMsalecardinfo.getSaletotalamt().doubleValue()), curMsalecardinfo.getId().getSalebillid());
			if(flag==false)
			{
				this.strMessage="生成开卡子卡信息有误,请到会员卡资料管理核实!";
				return SystemFinal.POST_FAILURE;
			}
			if(!CommonTool.FormatString(curMsalecardinfo.getActivtycardno()).equals(""))
			{
				String strActivtyDzqNo=this.cc007Service.loadDzqByActifyNo(curMsalecardinfo.getActivtycardno());
				if(!CommonTool.FormatString(strActivtyDzqNo).equals(""))
				{
					String strSendMsg="阿玛尼电子消费卷:"+strActivtyDzqNo+","+CommonTool.getDateMask(CommonTool.getCurrDate())+"至"+CommonTool.getDateMask(CommonTool.datePlusDay(CommonTool.getCurrDate(), 61))+"有效,请消费时出示此短信.";
					if(CommonTool.FormatString(curMsalecardinfo.getMemberphone()).length()==11)
						this.cc007Service.sendMsg(curMsalecardinfo.getMemberphone(), strSendMsg);
				}
			}
			curMsalecardinfo=null;
			lsDpayinfo=null;
			lsDsalecardproinfos=null;
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
	
	@Action(value = "validateSalecardno",  results = { 
			 @Result(name = "load_success", type = "json"),
          @Result(name = "load_failure", type = "json")	
       }) 
	public String validateSalecardno()
	{
		try
		{
			this.strMessage="";
			this.curCardinfo=this.cc007Service.getDataTool().loadCardinfobyCardNo(strCurCompId, this.strCardNo);
			if(this.curCardinfo==null)
			{
				this.strMessage="该会员卡号在系统中不存在!";
				return SystemFinal.LOAD_FAILURE; 
			}
			else if(this.curCardinfo.getCardstate()!=1 )
			{
				this.strMessage="该会员卡为不可销售状态!";
				return SystemFinal.LOAD_FAILURE; 
			}
			else if(!this.curCardinfo.getId().getCardvesting().equals(this.strCurCompId))
			{
				this.strMessage="对不起！此卡不属于本门店的卡!";
				return SystemFinal.LOAD_FAILURE; 
			}
			
			double promotionsvalue=this.cc007Service.loadSaleCardNoPromotions(strCurCompId, strCardNo);
			if(CommonTool.FormatDouble(promotionsvalue)==0)
			{
				promotionsvalue=this.cc007Service.loadSaleCardTypePromotions(strCurCompId, curCardinfo.getCardtype());
			}
			if(CommonTool.FormatDouble(promotionsvalue)!=0)
			{
				curCardinfo.setDSaleLowAmt(CommonTool.FormatBigDecimal(new BigDecimal(promotionsvalue)));
			}
			else 
			{
				if(this.curCardinfo.getOpenflag()!=1 )
				{
					this.strMessage="该会员卡为不允许开卡!";
					return SystemFinal.LOAD_FAILURE; 
				}
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	

	@Action(value = "handInsertShare",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String handInsertShare()
	{
		this.strMessage="";
		boolean flag=this.cc007Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
		if(flag==false)
		{
			this.strMessage="该经理密码不对,请核实";
		}
		return SystemFinal.LOAD_SUCCESS;
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
	public CC007Service getCc007Service() {
		return cc007Service;
	}
	@JSON(serialize=false)
	public void setCc007Service(CC007Service cc007Service) {
		this.cc007Service = cc007Service;
	}
	public Msalecardinfo getCurMsalecardinfo() {
		return curMsalecardinfo;
	}
	public void setCurMsalecardinfo(Msalecardinfo curMsalecardinfo) {
		this.curMsalecardinfo = curMsalecardinfo;
	}
	public List<Msalecardinfo> getLsMsalecardinfos() {
		return lsMsalecardinfos;
	}
	public void setLsMsalecardinfos(List<Msalecardinfo> lsMsalecardinfos) {
		this.lsMsalecardinfos = lsMsalecardinfos;
	}
	public List<Dsalecardproinfo> getLsDsalecardproinfos() {
		return lsDsalecardproinfos;
	}
	public void setLsDsalecardproinfos(List<Dsalecardproinfo> lsDsalecardproinfos) {
		this.lsDsalecardproinfos = lsDsalecardproinfos;
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
	public String getStrCardNo() {
		return strCardNo;
	}
	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}
	public List<Projectinfo> getLsProjectinfo() {
		return lsProjectinfo;
	}
	public void setLsProjectinfo(List<Projectinfo> lsProjectinfo) {
		this.lsProjectinfo = lsProjectinfo;
	}
	public String getStrJsonParam_pay() {
		return strJsonParam_pay;
	}
	public void setStrJsonParam_pay(String strJsonParam_pay) {
		this.strJsonParam_pay = strJsonParam_pay;
	}
	public String getStrJsonParam_pro() {
		return strJsonParam_pro;
	}
	public void setStrJsonParam_pro(String strJsonParam_pro) {
		this.strJsonParam_pro = strJsonParam_pro;
	}
	public String getStrSalePayMode() {
		return strSalePayMode;
	}
	public void setStrSalePayMode(String strSalePayMode) {
		this.strSalePayMode = strSalePayMode;
	}
	public String getStrCardType() {
		return strCardType;
	}
	public void setStrCardType(String strCardType) {
		this.strCardType = strCardType;
	}
	public String getStrCorpscardno() {
		return strCorpscardno;
	}
	public void setStrCorpscardno(String strCorpscardno) {
		this.strCorpscardno = strCorpscardno;
	}
	public String getStrActivtyCardno() {
		return strActivtyCardno;
	}
	public void setStrActivtyCardno(String strActivtyCardno) {
		this.strActivtyCardno = strActivtyCardno;
	}
	public BigDecimal getActivtyCardAmt() {
		return activtyCardAmt;
	}
	public void setActivtyCardAmt(BigDecimal activtyCardAmt) {
		this.activtyCardAmt = activtyCardAmt;
	}

}
