package com.amani.action.CardControl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.Cardinfo;
import com.amani.model.Cardtypeinfo;
import com.amani.model.Dpayinfo;
import com.amani.model.DpayinfoId;
import com.amani.model.Dsalecardproinfo;
import com.amani.model.DsalecardproinfoId;
import com.amani.model.Mcardrechargeinfo;
import com.amani.model.Projectinfo;
import com.amani.model.Staffinfo;
import com.amani.model.StaffinfoSimple;
import com.amani.service.CardControl.CC008Service;
import com.amani.tools.CommonTool;
import com.amani.tools.HttpClientUtil;
import com.amani.tools.SystemFinal;
import com.opensymphony.xwork2.ActionContext;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc008")
public class CC008Action extends AMN_ModuleAction{
	@Autowired
	private CC008Service cc008Service;
	private String strJsonParam;	
	private String strJsonParam_pay;
	private String strJsonParam_pro;
	private String strCurCompId;
	private String strCurBillId;
    private Mcardrechargeinfo curmcardrechargeinfo;
    private List<Mcardrechargeinfo> lsMcardrechargeinfo;
    private List<Dsalecardproinfo> lsDsalecardproinfos;
    private List<Dpayinfo> lsDpayinfo;
    private String searchMemberCompIdKey;
    private String searchMemberNoKey;
    private String searchMemberNameKey;
    private String searchMemberPhoneKey;
    private String searchMemberPCIDKey;
	private List<Cardtypeinfo> lsCardtypeinfos;
	private List<Staffinfo> lsStaffinfo;
	private String strSP114;
	public String getStrSP114() {
		return strSP114;
	}
	public void setStrSP114(String strSP114) {
		this.strSP114 = strSP114;
	}
	private List<Projectinfo> lsProjectinfo;
	
	private List<StaffinfoSimple> lsStaffinfoSimple;
	private String strCardNo;
    private Cardinfo curCardinfo;
    private String strSalePayMode;
    private String strShareCondition;
	private String strSearchBillId;
	private String strCardType;
	private String strCorpscardno;
    private BigDecimal cropsCardAmt;
    /************小票打印***************/
    private String cashMemberId;
    private String companName;
    private String companTel;
    private String companNet;
    private String companAddr;
    private String cashMemberName;
    private String printTime;
    private String printDate;
    private String billDate;
    private List<Dpayinfo> payTypeList;
    
    /***********经理卡验证*******************/
    private String mangerCardNo;
    /************第三方支付**************/
    private String outTradeNo;//扫码订单号
    private Integer scanpaytype;//扫码方式1支付宝‘2微信
    private Double totalBank;//扫码支付金额
	public String getMangerCardNo() {
		return mangerCardNo;
	}
	public void setMangerCardNo(String mangerCardNo) {
		this.mangerCardNo = mangerCardNo;
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
		curmcardrechargeinfo.setRechargedate(CommonTool.setDateMask(curmcardrechargeinfo.getRechargedate()));
		curmcardrechargeinfo.setRechargetime(CommonTool.setTimeMask(curmcardrechargeinfo.getRechargetime(), 1));
		curmcardrechargeinfo.setOperationdate(CommonTool.getCurrDate());
		curmcardrechargeinfo.setOperationer(CommonTool.getLoginInfo("USERID"));
		curmcardrechargeinfo.setFinancedate(CommonTool.getCurrDate());
		curmcardrechargeinfo.setRechargecardno(curmcardrechargeinfo.getRechargecardno().trim());
		curmcardrechargeinfo.getId().setRechargebillid(this.cc008Service.getDataTool().loadBillIdByRule(curmcardrechargeinfo.getId().getRechargecompid(),"mcardrechargeinfo", "rechargebillid", "SP009"));
		this.lsDpayinfo=this.cc008Service.getDataTool().loadDTOList(strJsonParam_pay, Dpayinfo.class);
		if(lsDpayinfo!=null && lsDpayinfo.size()>0)
		{
			for(int i=0;i<lsDpayinfo.size();i++)
			{
				if(lsDpayinfo.get(i)!=null && CommonTool.FormatBigDecimal(lsDpayinfo.get(i).getPayamt()).doubleValue()>0)
				{
					lsDpayinfo.get(i).setId(new DpayinfoId(curmcardrechargeinfo.getId().getRechargecompid(),curmcardrechargeinfo.getId().getRechargebillid(),"CZ",i*1.0));
				}
				else
				{
					lsDpayinfo.remove(i);
					i--;
				}
			}
		}
		this.lsDsalecardproinfos=this.cc008Service.getDataTool().loadDTOList(strJsonParam_pro, Dsalecardproinfo.class);
		if(lsDsalecardproinfos!=null && lsDsalecardproinfos.size()>0)
		{
			for(int i=0;i<lsDsalecardproinfos.size();i++)
			{
				if(lsDsalecardproinfos.get(i)!=null && !CommonTool.FormatString(lsDsalecardproinfos.get(i).getSaleproid()).equals(""))
				{
					lsDsalecardproinfos.get(i).setId(new DsalecardproinfoId(curmcardrechargeinfo.getId().getRechargecompid(),curmcardrechargeinfo.getId().getRechargebillid(),2,i*1.0));
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
		
		this.lsMcardrechargeinfo=this.cc008Service.loadMasterinfo(this.strCurCompId);
		if(this.lsMcardrechargeinfo==null )
		{
			this.lsMcardrechargeinfo=new ArrayList();
		}
		this.curmcardrechargeinfo=this.cc008Service.addMcardrechargeinfo(this.strCurCompId);
		lsMcardrechargeinfo.add(0,curmcardrechargeinfo);
		this.lsCardtypeinfos=this.cc008Service.getDataTool().loadCardType(strCurCompId);
		this.lsStaffinfo=this.cc008Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
		this.lsProjectinfo=this.cc008Service.getDataTool().loadProjectinfoByCompId(strCurCompId,1);
		strSalePayMode=this.cc008Service.getDataTool().loadSysParam(strCurCompId,"SP066");
		strShareCondition=this.cc008Service.getDataTool().loadSysParam(strCurCompId,"SP102");
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "searchCurRecord",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String searchCurRecord()
	{
	
		this.lsMcardrechargeinfo=this.cc008Service.searchMasterinfo(this.strCurCompId,this.strSearchBillId);
		if(this.lsMcardrechargeinfo==null )
		{
			this.lsMcardrechargeinfo=new ArrayList();
		}
	
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadCurMaster",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMaster()
	{
		
		this.curmcardrechargeinfo=this.cc008Service.loadMsalecardinfo(strCurCompId,this.strCurBillId);
		if(curmcardrechargeinfo!=null)
		{
			curmcardrechargeinfo.setRechargedate(CommonTool.getDateMask(curmcardrechargeinfo.getRechargedate()));
			curmcardrechargeinfo.setRechargetime(CommonTool.getTimeMask(curmcardrechargeinfo.getRechargetime(), 1));
		}
		else
		{
			this.curmcardrechargeinfo=this.cc008Service.addMcardrechargeinfo(this.strCurCompId);
		}
		if(!CommonTool.FormatString(curmcardrechargeinfo.getRechargecardtype()).equals(""))
		{
			curmcardrechargeinfo.setRechargecardtypeName(this.cc008Service.getDataTool().loadCardTypeName(strCurCompId, CommonTool.FormatString(curmcardrechargeinfo.getRechargecardtype()), new StringBuffer()));
		}
		strSP114=this.cc008Service.getDataTool().loadSysParam(strCurCompId,"SP114");
		this.lsDpayinfo=this.cc008Service.loadDpayinfoByBill(strCurCompId,this.strCurBillId);
		this.lsDsalecardproinfos=this.cc008Service.loadDsalecardproinfo(strCurCompId,this.strCurBillId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "add",  results = { 
			 @Result(name = "add_success", type = "json"),
          @Result(name = "add_failure", type = "json")	
       }) 
	@Override
	public String add()
	{
		this.strMessage="";
		this.curmcardrechargeinfo=this.cc008Service.addMcardrechargeinfo(this.strCurCompId);
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
			if(curmcardrechargeinfo.getBillinsertype()==1){
				boolean b = this.getCc008Service().getDataTool().chekIsHotDyeDivision(curmcardrechargeinfo);
				if(b){
					this.strMessage="来源是美容的,烫染师不能分享业绩!";
					return SystemFinal.POST_FAILURE;
				}
			}
			boolean canPostFlag=this.cc008Service.getDataTool().canSaveBill(this.curmcardrechargeinfo.getId().getRechargecompid(), CommonTool.getCurrDate(),errorMess);
			if(canPostFlag==false)
			{
				this.setStrMessage(errorMess.toString());
				return SystemFinal.POST_FAILURE; 
			}
			if(this.cc008Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC008", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			//如果美容部业绩大于30000 美容经理和顾问提成0.02
			float comm=this.cc008Service.getDataTool().getBeautyDepartmentPerformance(curmcardrechargeinfo);
			if(comm>30000)
			{
				int index=0;
				comm=(float) ((comm-30000));
				if(CommonTool.isEmpty(curmcardrechargeinfo.getBeautyManager()))
				{
					index++;
					//curReEditBillInfo.setBeautyPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curmcardrechargeinfo.getConsultant()))
				{
					index++;
					//curReEditBillInfo.setConsultantPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curmcardrechargeinfo.getConsultant1()))
				{
					index++;
					//curReEditBillInfo.setConsultant1Perf(BigDecimal.valueOf(comm));
				}
				comm=comm/index;
				if(CommonTool.isEmpty(curmcardrechargeinfo.getBeautyManager()))
				{
					//index++;
					curmcardrechargeinfo.setBeautyPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curmcardrechargeinfo.getConsultant()))
				{
					//index++;
					curmcardrechargeinfo.setConsultantPerf(BigDecimal.valueOf(comm));
				}
				if(CommonTool.isEmpty(curmcardrechargeinfo.getConsultant1()))
				{
					//index++;
					curmcardrechargeinfo.setConsultant1Perf(BigDecimal.valueOf(comm));
				}
			}
			//第三方支付需要插入的字段
			if(scanpaytype!=null){
				curmcardrechargeinfo.setScanpayamt(BigDecimal.valueOf(totalBank));
				curmcardrechargeinfo.setScanpaytype(scanpaytype);
				curmcardrechargeinfo.setScantradeno(outTradeNo);
			}
			boolean flag=this.cc008Service.postSaleInfo(this.curmcardrechargeinfo, this.lsDpayinfo,this.lsDsalecardproinfos);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc008Service.afterPost(curmcardrechargeinfo.getId().getRechargecompid(), curmcardrechargeinfo.getId().getRechargebillid(),curmcardrechargeinfo.getRechargecardtype());
			if(flag==false)
			{
				this.strMessage="生成开卡信息有误,请到会员卡资料管理核实!";
				return SystemFinal.POST_FAILURE;
			}
			//会员卡续费回执微信充值信息
			if(CommonTool.checkStr(strCardNo))
			{
				new Thread(new Runnable(){
					public void run() {
						double payAmt=0;
						List<Dpayinfo> lsDpayinfos=cc008Service.loadDpayinfoByBill(curmcardrechargeinfo.getId().getRechargecompid(), curmcardrechargeinfo.getId().getRechargebillid());
						if(lsDpayinfos!=null && lsDpayinfos.size()>0)
						{
							for(Dpayinfo dpayinfo:lsDpayinfos)
							{
								payAmt=dpayinfo.getPayamt().doubleValue();
							}
						}
						lsDpayinfos=null;
						String openId = cc008Service.loadWechatOpenId(strCardNo);
						Map<String, String> mapParam=new HashMap<String, String>();
						mapParam.put("templateID", "uJPNjG4t4vervvU2kYeymaeXCe5tcdkjKefKkKcBm78");
						mapParam.put("openid", openId);
						mapParam.put("first", "尊敬的会员，您好！您卡号 "+ strCardNo +" 的订单 "+ curmcardrechargeinfo.getId().getRechargebillid() +" 已续费成功！");
						mapParam.put("keyword1", String.valueOf(payAmt) +"元。");
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Calendar nowTime = Calendar.getInstance();
						mapParam.put("keyword2", df.format(nowTime.getTime()));
						mapParam.put("remark", "祝您消费愉快！如有疑问请致电4006622818。");
						try {
							strMessage = HttpClientUtil.postMap("http://wechat.chinamani.com/AmaniWechatPlatform/ei/template", mapParam).replaceAll("\"", "");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
			//curmcardrechargeinfo=null;
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
			this.curCardinfo=this.cc008Service.getDataTool().loadCardinfobyCardNo(strCurCompId, this.strCardNo);
			if(this.curCardinfo==null)
			{
				this.strMessage="该会员卡号在系统中不存在!";
				return SystemFinal.LOAD_FAILURE; 
			}
			else if(this.curCardinfo.getCardstate()!=4  && this.curCardinfo.getCardstate()!=5 )
			{
				this.strMessage="该会员卡为不可续费状态!";
				return SystemFinal.LOAD_FAILURE; 
			}
//			else if(!this.curCardinfo.getId().getCardvesting().equals(this.strCurCompId))
//			{
//				this.strMessage="对不起！此卡不属于本门店的卡!";
//				return SystemFinal.LOAD_FAILURE; 
//			}
			
			//验证是否是跨事业部转卡
			if(!strCurCompId.equals("001")&&!this.curCardinfo.getId().getCardvesting().equals("001")
				&&this.cc008Service.getDataTool().canOptionChangeCard_shiye(strCurCompId,strCardNo)==false)
			{
				this.setStrMessage("该卡不可跨事业部充值!");
				return SystemFinal.LOAD_FAILURE;
			}
			double promotionsvalue=this.cc008Service.loadSaleCardNoPromotions(strCurCompId, strCardNo);
			if(CommonTool.FormatDouble(promotionsvalue)==0)
			{
				promotionsvalue=this.cc008Service.loadSaleCardTypePromotions(strCurCompId, curCardinfo.getCardtype());
			}
			if(CommonTool.FormatDouble(promotionsvalue)!=0)
			{
				curCardinfo.setDFillLowAmt(CommonTool.FormatBigDecimal(new BigDecimal(promotionsvalue)));
			}
			else
			{
				if(this.curCardinfo.getFillflag()!=1 )
				{
					this.strMessage="该会员卡为不允许续费!";
					return SystemFinal.LOAD_FAILURE; 
				}
			}
			this.lsStaffinfoSimple=this.cc008Service.getDataTool().loadOldCustomerInfo(strCurCompId,strCardNo);
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
		Map values = this.cc008Service.getDataTool().getReportParams( this.strCurCompId, this.strCurBillId);
	    this.billDate = CommonTool.getDateMask(this.billDate);
	    this.companName = values.get("companName").toString();
	    this.companTel = values.get("companTel").toString();
	    this.companAddr = values.get("companAddr").toString();
	    this.cashMemberName=values.get("cashMemberId").toString();
	    this.printTime = CommonTool.getTimeMask(CommonTool.getCurrTime(), 1);
	    this.printDate = CommonTool.getDateMask(CommonTool.getCurrDate());
	    this.payTypeList = this.cc008Service.getDataTool().loadPayInfoByBillId(this.strCurCompId, this.strCurBillId,"CZ");
	    this.curCardinfo = this.cc008Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId, this.strCardNo);
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
		boolean flag=this.cc008Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
		if(flag==false)
		{
			this.strMessage="该经理密码不对,请核实";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//推送微信密码
	@Action( value="sendWechatPwd", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String sendWechatPwd() {
		String openId = this.cc008Service.loadWechatOpenId(strCardNo);
		if(StringUtils.isBlank(openId)){
			strMessage ="该卡号未绑定微信，请确认！";
			return SystemFinal.LOAD_SUCCESS;
		}
		Map<String, String> mapParam=new HashMap<String, String>();
		mapParam.put("templateID", "UI_t3XRO6KtDVUl5mYsR80a2P1ctkF0OI_AMP3-XwXQ");
		mapParam.put("openid", openId);
		String ramdom = String.valueOf(((int)((Math.random()*9+1)*100000)));
		mapParam.put("first", "尊敬的会员，您好！您本次的续费验证码为：");
		mapParam.put("keyword1", CommonTool.getLoginInfo("COMPNAME") +"-续费");
		mapParam.put("keyword2", ramdom);
		Map<String, Object> session=ActionContext.getContext().getSession();
		session.put("_ramdomCode", ramdom);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, 5);
		mapParam.put("keyword3", df.format(nowTime.getTime()));
		mapParam.put("remark", "请您把此验证码告知本店大堂经理,请在有效期时间内使用。");
		try {
			strMessage = HttpClientUtil.postMap("http://wechat.chinamani.com/AmaniWechatPlatform/ei/template", mapParam).replaceAll("\"", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	//验证验证码
	@Action( value="validateWechatPwd", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String validateWechatPwd() {
		Map<String, Object> session=ActionContext.getContext().getSession();
		String _ramdomCode = ObjectUtils.toString(session.get("_ramdomCode"));
		strMessage = StringUtils.equals(_ramdomCode, strCardNo)?"":"验证码不正确，请重新输入！";
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
	public CC008Service getCc008Service() {
		return cc008Service;
	}
	@JSON(serialize=false)
	public void setCc008Service(CC008Service cc008Service) {
		this.cc008Service = cc008Service;
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
	public Mcardrechargeinfo getCurmcardrechargeinfo() {
		return curmcardrechargeinfo;
	}
	public void setCurmcardrechargeinfo(Mcardrechargeinfo curmcardrechargeinfo) {
		this.curmcardrechargeinfo = curmcardrechargeinfo;
	}
	public List<Mcardrechargeinfo> getLsMcardrechargeinfo() {
		return lsMcardrechargeinfo;
	}
	public void setLsMcardrechargeinfo(List<Mcardrechargeinfo> lsMcardrechargeinfo) {
		this.lsMcardrechargeinfo = lsMcardrechargeinfo;
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
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Integer getScanpaytype() {
		return scanpaytype;
	}
	public void setScanpaytype(Integer scanpaytype) {
		this.scanpaytype = scanpaytype;
	}
	public Double getTotalBank() {
		return totalBank;
	}
	public void setTotalBank(Double totalBank) {
		this.totalBank = totalBank;
	}
	
	
}
