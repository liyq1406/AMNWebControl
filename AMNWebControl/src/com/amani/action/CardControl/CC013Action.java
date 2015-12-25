package com.amani.action.CardControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.Cardinfo;
import com.amani.model.Dmedicalcare;
import com.amani.model.Dpayinfo;
import com.amani.model.DpayinfoId;
import com.amani.model.Mcooperatesaleinfo;
import com.amani.model.Mmedicalcare;
import com.amani.model.Mpackageinfo;
import com.amani.model.Staffinfo;
import com.amani.service.CardControl.CC013Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc013")
public class CC013Action extends AMN_ModuleAction{
	@Autowired
	private CC013Service cc013Service;
	private String strJsonParam;	
	private String strJsonParamFact;	
	private String strCurPackageNo;
    private List<Mcooperatesaleinfo> lsMcooperatesaleinfo;
    private Mcooperatesaleinfo curMcooperatesaleinfo;
    private List<Dpayinfo> lsDpayinfo;
    private List<Dpayinfo> lsDpayinfoFact;
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
	private List<Mpackageinfo> lsMpackageinfo;
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
		this.lsMcooperatesaleinfo=this.cc013Service.loadMasterDateByCompId(this.strCurCompId);
		this.lsMpackageinfo=this.cc013Service.loadMaster(strCurCompId);
		if(this.lsMcooperatesaleinfo==null || lsMcooperatesaleinfo.size()==0)
		{
			this.lsMcooperatesaleinfo=new ArrayList();
			
		}
		this.curMcooperatesaleinfo=this.cc013Service.addMastRecord(this.strCurCompId);
		lsMcooperatesaleinfo.add(0,curMcooperatesaleinfo);
		this.lsStaffinfo=this.cc013Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
		strSalePayMode=this.cc013Service.getDataTool().loadSysParam(strCurCompId,"SP068");
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadCurMcooperatesaleinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMcooperatesaleinfo()
	{
		this.curMcooperatesaleinfo=this.cc013Service.loadcurMaster(this.strCurCompId,this.strCurBillId);
		if(curMcooperatesaleinfo==null)
			curMcooperatesaleinfo=this.cc013Service.addMastRecord(strCurCompId);
		if(!CommonTool.FormatString(curMcooperatesaleinfo.getSalecostcardtype()).equals(""))
		{
			curMcooperatesaleinfo.setSalecostcardtypename(this.cc013Service.getDataTool().loadCardTypeName(strCurCompId,CommonTool.FormatString(curMcooperatesaleinfo.getSalecostcardtype()), new StringBuffer()));
		}
		this.lsDpayinfo=this.cc013Service.loadDpayinfoByBill(strCurCompId,this.strCurBillId,0);
		this.lsDpayinfoFact=this.cc013Service.loadDpayinfoByBill(strCurCompId,this.strCurBillId,1);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public List<Dpayinfo> getLsDpayinfo() {
		return lsDpayinfo;
	}

	public void setLsDpayinfo(List<Dpayinfo> lsDpayinfo) {
		this.lsDpayinfo = lsDpayinfo;
	}

	

	@Action(value = "validateCostCardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateCostCardno()
	{
		this.curCardinfo= this.cc013Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId,this.strCurCardNo);
		if(curCardinfo == null || curCardinfo.getId()==null || CommonTool.FormatString(curCardinfo.getCardtype()).equals(""))
		{
			this.setStrMessage("该会员卡不存在!");
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
			this.lsMcooperatesaleinfo=this.cc013Service.loadMasterDateByCard(this.strCurCompId, strSearchContent);//this.searchLossCardNo, this.searchRecevieCardNo);
			return SystemFinal.LOAD_SUCCESS; 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	@Action(value = "queryMedical",  results = { 
			@Result(name = "load_success", type = "json"),
			@Result(name = "load_failure", type = "json")	})
	public String queryMedical()
	{
		try
		{
			lsMpackageinfo=this.cc013Service.loadMedicalDetail(this.strCurCompId, this.strCurBillId);
			return SystemFinal.LOAD_SUCCESS; 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	
	@JSON(serialize=false)
	public CC013Service getCc013Service() {
		return cc013Service;
	}
    @JSON(serialize=false)
	public void setCc013Service(CC013Service cc013Service) {
		this.cc013Service = cc013Service;
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
		curMcooperatesaleinfo.setSaledate(CommonTool.setDateMask(curMcooperatesaleinfo.getSaledate()));
		curMcooperatesaleinfo.setSaletime(CommonTool.setTimeMask(curMcooperatesaleinfo.getSaletime(), 1));
		curMcooperatesaleinfo.setFinancedate(CommonTool.getCurrDate());
		curMcooperatesaleinfo.setOperationdate(CommonTool.getCurrDate());
		curMcooperatesaleinfo.setOperationer(CommonTool.getLoginInfo("COMPID"));
		curMcooperatesaleinfo.setSalebillflag(1);
		curMcooperatesaleinfo.getId().setSalebillid(this.cc013Service.getDataTool().loadBillIdByRule(curMcooperatesaleinfo.getId().getSalecompid(),"mcooperatesaleinfo", "salebillid", "SP010"));
		this.lsDpayinfo=this.cc013Service.getDataTool().loadDTOList(strJsonParam, Dpayinfo.class);
		if(lsDpayinfo!=null && lsDpayinfo.size()>0)
		{
			for(int i=0;i<lsDpayinfo.size();i++)
			{
				if(lsDpayinfo.get(i)!=null && CommonTool.FormatBigDecimal(lsDpayinfo.get(i).getPayamt()).doubleValue()>0)
				{
					lsDpayinfo.get(i).setId(new DpayinfoId(curMcooperatesaleinfo.getId().getSalecompid(),curMcooperatesaleinfo.getId().getSalebillid(),"HZW",i*1.0));
				}
				else
				{
					lsDpayinfo.remove(i);
					i--;
				}
			}
		}
		
		return true;
	}
	
	@Action(value = "confirm",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String confirm()
	{
		this.lsDpayinfoFact=this.cc013Service.getDataTool().loadDTOList(strJsonParamFact, Dpayinfo.class);
		if(lsDpayinfoFact!=null && lsDpayinfoFact.size()>0)
		{
			for(int i=0;i<lsDpayinfoFact.size();i++)
			{
				if(lsDpayinfoFact.get(i)!=null && CommonTool.FormatBigDecimal(lsDpayinfoFact.get(i).getPayamt()).doubleValue()>0)
				{
					lsDpayinfoFact.get(i).setId(new DpayinfoId(this.strCurCompId,this.strCurBillId,"HZ",i*1.0));
				}
				else
				{
					lsDpayinfoFact.remove(i);
					i--;
				}
			}
		}
		boolean flag=this.cc013Service.postConFirmInfo(this.strCurCompId,this.strCurBillId, this.lsDpayinfoFact);
		if(flag==false)
		{
			this.strMessage="审核失败,请重新审核!";
			return SystemFinal.POST_FAILURE;
		}
		return SystemFinal.POST_SUCCESS;
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
			boolean canPostFlag=this.cc013Service.getDataTool().canSaveBill(this.curMcooperatesaleinfo.getId().getSalecompid(), CommonTool.getCurrDate(),errorMess);
			if(canPostFlag==false)
			{
				this.setStrMessage(errorMess.toString());
				return SystemFinal.POST_FAILURE; 
			}
			if(this.cc013Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC013", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			if(this.curMcooperatesaleinfo.getSalecooperid().equals("009")||this.curMcooperatesaleinfo.getSalecooperid().equals("010")){
				this.curMcooperatesaleinfo.setAccounttype(2);
			}
			boolean flag=this.cc013Service.postChangeInfo(this.curMcooperatesaleinfo, this.lsDpayinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc013Service.postSaleInid(this.curMcooperatesaleinfo.getId().getSalecompid(),this.curMcooperatesaleinfo.getId().getSalebillid());
			if(flag==false)
			{
				this.strMessage="绑定销售人员内部工号有误,请到业务单据修改核实!";
				return SystemFinal.POST_FAILURE;
			}
			if(this.curMcooperatesaleinfo.getSalecooperid().equals("009")||this.curMcooperatesaleinfo.getSalecooperid().equals("010")){
					//&&(this.curMcooperatesaleinfo.getSalescardpayment()!=null&&this.curMcooperatesaleinfo.getSalescardpayment().doubleValue()>0)){
				String compno = this.curMcooperatesaleinfo.getId().getSalecompid();
				String salebillid = this.curMcooperatesaleinfo.getId().getSalebillid();
				/*flag=this.cc013Service.updateCardaccount(compno, salebillid);
				if(flag==false)
				{
					this.strMessage="生成消费历史有误,请到会员卡资料管理核实!";
					return SystemFinal.POST_FAILURE;
				}*/
				if(StringUtils.isNotBlank(strJsonParamFact)){
					//合作医疗套餐主档
					Mmedicalcare mmedical = new Mmedicalcare();
					mmedical.setCompno(compno);
					mmedical.setSalebillid(salebillid);
					String telephone = this.curMcooperatesaleinfo.getMemberphone();
					mmedical.setTelephone(telephone);
					mmedical.setOnlyno(UUID.randomUUID().toString());
					mmedical.setName(this.curMcooperatesaleinfo.getMembername());
					//合作医疗套餐明细
					List<Dmedicalcare> dmedical = new ArrayList<Dmedicalcare>();
					Gson gson = new Gson();
					List<Map<String, String>> list = gson.fromJson(strJsonParamFact, 
								new TypeToken<List<Map<String, String>>>(){}.getType());
					for (Map<String, String> map : list) {
						Dmedicalcare dmedicalcare = new Dmedicalcare();
						dmedicalcare.setCompno(compno);
						dmedicalcare.setSalebillid(salebillid);
						dmedicalcare.setTelephone(telephone);
						dmedicalcare.setPackageno(map.get("packageno"));
						dmedicalcare.setItemno(map.get("itemno"));
						dmedicalcare.setOnlyno(UUID.randomUUID().toString());
						String strSalecount = map.get("salecount");
						String strSaleamt = map.get("saleamt");
						dmedicalcare.setSaledate(this.curMcooperatesaleinfo.getSaledate());
						if(StringUtils.isNotBlank(strSalecount) && StringUtils.isNotBlank(strSaleamt)){
							int salecount = Integer.parseInt(strSalecount);
							float saleamt = Float.parseFloat(strSaleamt);
							dmedicalcare.setSalecount(salecount);
							dmedicalcare.setSaleamt(saleamt);
							dmedicalcare.setLastcount(salecount);
							dmedicalcare.setLastamt(saleamt);
							dmedical.add(dmedicalcare);
						}
					}
					this.cc013Service.saveMedicalInfo(mmedical, dmedical);
				}
			}
			curMcooperatesaleinfo=null;
			lsDpayinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	/**修改添加 2015/6/9*/
	@Action(value = "validateCostCardnoByHZXK",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateCostCardnoByHZXK()
	{
		this.curCardinfo= this.cc013Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId,this.strCurCardNo);
		if(curCardinfo == null || curCardinfo.getId()==null || CommonTool.FormatString(curCardinfo.getCardtype()).equals(""))
		{
			this.setStrMessage("该会员卡不存在!");
			return SystemFinal.LOAD_FAILURE;
		}
	
		return SystemFinal.LOAD_SUCCESS;
	}
	
	/**修改添加 2015/6/9*/
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



	public String getStrJsonParamFact() {
		return strJsonParamFact;
	}

	public void setStrJsonParamFact(String strJsonParamFact) {
		this.strJsonParamFact = strJsonParamFact;
	}

	public List<Mcooperatesaleinfo> getLsMcooperatesaleinfo() {
		return lsMcooperatesaleinfo;
	}

	public void setLsMcooperatesaleinfo(
			List<Mcooperatesaleinfo> lsMcooperatesaleinfo) {
		this.lsMcooperatesaleinfo = lsMcooperatesaleinfo;
	}

	public Mcooperatesaleinfo getCurMcooperatesaleinfo() {
		return curMcooperatesaleinfo;
	}

	public void setCurMcooperatesaleinfo(Mcooperatesaleinfo curMcooperatesaleinfo) {
		this.curMcooperatesaleinfo = curMcooperatesaleinfo;
	}

	public String getStrSearchContent() {
		return strSearchContent;
	}

	public void setStrSearchContent(String strSearchContent) {
		this.strSearchContent = strSearchContent;
	}

	public List<Dpayinfo> getLsDpayinfoFact() {
		return lsDpayinfoFact;
	}

	public void setLsDpayinfoFact(List<Dpayinfo> lsDpayinfoFact) {
		this.lsDpayinfoFact = lsDpayinfoFact;
	}

	public List<Mpackageinfo> getLsMpackageinfo() {
		return lsMpackageinfo;
	}

	public void setLsMpackageinfo(List<Mpackageinfo> lsMpackageinfo) {
		this.lsMpackageinfo = lsMpackageinfo;
	}

	
	
}
