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
import com.amani.model.Cardproaccount;
import com.amani.model.Dmpackageinfo;
import com.amani.model.Dproexchangeinfo;
import com.amani.model.DproexchangeinfoId;
import com.amani.model.Dproexchangeinfobypro;
import com.amani.model.DproexchangeinfobyproId;
import com.amani.model.Mconsumeinfo;
import com.amani.model.Mpackageinfo;
import com.amani.model.Mproexchangeinfo;
import com.amani.model.Projectinfo;
import com.amani.model.Staffinfo;
import com.amani.service.CardControl.CC012Service;
import com.amani.tools.CommonTool;
import com.amani.tools.HttpClientUtil;
import com.amani.tools.SystemFinal;
import com.opensymphony.xwork2.ActionContext;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc012")
public class CC012Action extends AMN_ModuleAction{
	@Autowired
	private CC012Service cc012Service;
	private String strJsonParam;	
	private String strProJsonParam;
    private String strCurCompId;
    private String strCurBillId;

	private List<Mproexchangeinfo> lsMproexchangeinfo;
	private List<Dproexchangeinfo> lsDproexchangeinfo;
	private List<Dproexchangeinfobypro> lsDproexchangeinfobypro;
	private String strCurPackageId;
	private List<Mpackageinfo> lsMpackageinfo;
    private List<Dmpackageinfo> lsDmpackageinfo;
    private int	packageRateFlag;
	private Mproexchangeinfo curMproexchangeinfo;
	private List<Staffinfo> lsStaffinfo;
	private List<Projectinfo> lsProjectinfo;
    private List<Cardproaccount> lsCardproaccount;
	private String strCardNo;
	private Cardinfo curCardinfo;
	private String strCurItemId;
	private Projectinfo curProjectinfo;
	private String strDiyqNo;  //现金抵用券编号
	private double diyqAmt;    //现金抵用券额度
	private double rateToRate;
	private String strCurManagerNo;
	private String strCurManagerPass;
	private double SP028Rate; //经理打折上限
	private int	   SP095Flag;//是否可以修改金额和次数
	private int	   SP099Flag;//疗程兑换优惠活动
	private int	   SP117Flag;//是否启用980疗程体验活动
	private String SP118Flag;//980疗程体验活动截止日期
	private List<Mconsumeinfo> lsMconsumeinfo;
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
    /***********经理卡验证*******************/
    private String mangerCardNo;
    public String getMangerCardNo() {
		return mangerCardNo;
	}

	public void setMangerCardNo(String mangerCardNo) {
		this.mangerCardNo = mangerCardNo;
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
	/************小票打印***************/
	public double getSP028Rate() {
		return SP028Rate;
	}
	public void setSP028Rate(double rate) {
		SP028Rate = rate;
	}
	public String getStrCurManagerNo() {
		return strCurManagerNo;
	}
	public void setStrCurManagerNo(String strCurManagerNo) {
		this.strCurManagerNo = strCurManagerNo;
	}
	public String getStrCurManagerPass() {
		return strCurManagerPass;
	}
	public void setStrCurManagerPass(String strCurManagerPass) {
		this.strCurManagerPass = strCurManagerPass;
	}
	public String getStrDiyqNo() {
		return strDiyqNo;
	}
	public void setStrDiyqNo(String strDiyqNo) {
		this.strDiyqNo = strDiyqNo;
	}

	public double getDiyqAmt() {
		return diyqAmt;
	}
	public void setDiyqAmt(double diyqAmt) {
		this.diyqAmt = diyqAmt;
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
		curMproexchangeinfo.setChangecardno(CommonTool.FormatString(curMproexchangeinfo.getChangecardno()).trim());
		curMproexchangeinfo.setChangedate(CommonTool.setDateMask(curMproexchangeinfo.getChangedate()));
		curMproexchangeinfo.setChangeopationdate(CommonTool.getCurrDate());
		curMproexchangeinfo.setFinancedate(CommonTool.getCurrDate());
		curMproexchangeinfo.setChangetime(CommonTool.setTimeMask(curMproexchangeinfo.getChangetime(), 1));
		curMproexchangeinfo.getId().setChangebillid(this.cc012Service.getDataTool().loadBillIdByRule(curMproexchangeinfo.getId().getChangecompid(),"mproexchangeinfo", "changebillid", "SP008"));
		this.lsDproexchangeinfo=this.cc012Service.getDataTool().loadDTOList(strJsonParam, Dproexchangeinfo.class);
		if(lsDproexchangeinfo!=null && lsDproexchangeinfo.size()>0)
		{
			for(int i=0;i<lsDproexchangeinfo.size();i++)
			{
				if(lsDproexchangeinfo.get(i)!=null && !CommonTool.FormatString(lsDproexchangeinfo.get(i).getChangeproid()).equals(""))
				{
					lsDproexchangeinfo.get(i).setId(new DproexchangeinfoId(curMproexchangeinfo.getId().getChangecompid(),curMproexchangeinfo.getId().getChangebillid(),i*1.0));
				}
				else
				{
					lsDproexchangeinfo.remove(i);
					i--;
				}
			}
		}
		this.lsDproexchangeinfobypro=this.cc012Service.getDataTool().loadDTOList(strProJsonParam, Dproexchangeinfobypro.class);
		if(lsDproexchangeinfobypro!=null && lsDproexchangeinfobypro.size()>0)
		{
			for(int i=0;i<lsDproexchangeinfobypro.size();i++)
			{
				if(lsDproexchangeinfobypro.get(i)!=null && CommonTool.FormatBigDecimal(lsDproexchangeinfobypro.get(i).getChangeproamt()).doubleValue()>0)
				{
					lsDproexchangeinfobypro.get(i).setId(new DproexchangeinfobyproId(curMproexchangeinfo.getId().getChangecompid(),curMproexchangeinfo.getId().getChangebillid(),i*1.0));
				}
				else
				{
					lsDproexchangeinfobypro.remove(i);
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
	@JSON(serialize=false)
	public CC012Service getCc012Service() {
		return cc012Service;
	}
	@JSON(serialize=false)
	public void setCc012Service(CC012Service cc012Service) {
		this.cc012Service = cc012Service;
	}
	
	

	
	@Action(value = "loadMasterinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadMasterinfo()
	{
		
		this.lsMproexchangeinfo=this.cc012Service.loadMasterDateByCompId(this.strCurCompId);
		if(this.lsMproexchangeinfo==null )
		{
			this.lsMproexchangeinfo=new ArrayList();
		}
		this.curMproexchangeinfo=this.cc012Service.addMastRecord(this.strCurCompId);
		lsMproexchangeinfo.add(0,curMproexchangeinfo);
		this.lsStaffinfo=this.cc012Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
		this.lsProjectinfo=this.cc012Service.getDataTool().loadProjectinfoByCompId(strCurCompId,1);
		this.lsMpackageinfo=this.cc012Service.loadMpackageinfo(strCurCompId);
		String strSp095=CommonTool.FormatString(this.cc012Service.getDataTool().loadSysParam(strCurCompId, "SP095"));
		if(strSp095==null || strSp095.equals("") || strSp095.equals("0"))
		{
			this.SP095Flag=0;
		}
		else
		{
			this.SP095Flag=1;
		}
		String strSp099=CommonTool.FormatString(this.cc012Service.getDataTool().loadSysParam(strCurCompId, "SP099"));
		if(strSp099==null || strSp099.equals("") || strSp099.equals("0"))
		{
			this.SP099Flag=0;
		}
		else
		{
			this.SP099Flag=1;
		}
		String strSp117=CommonTool.FormatString(this.cc012Service.getDataTool().loadSysParam(strCurCompId, "SP117"));
		if(strSp117==null || strSp117.equals("") || strSp117.equals("0"))
		{
			this.SP117Flag=0;
		}
		else
		{
			this.SP117Flag=1;
		}
		this.SP118Flag=CommonTool.FormatString(this.cc012Service.getDataTool().loadSysParam(strCurCompId, "SP118"));
		return SystemFinal.LOAD_SUCCESS;
	}
	

	
	@Action(value = "loadCurMaster",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMaster()
	{
		
		this.curMproexchangeinfo=this.cc012Service.loadcurMaster(strCurCompId,this.strCurBillId);
		if(curMproexchangeinfo!=null)
		{
			curMproexchangeinfo.setChangedate(CommonTool.getDateMask(curMproexchangeinfo.getChangedate()));
			curMproexchangeinfo.setChangetime(CommonTool.getTimeMask(curMproexchangeinfo.getChangetime(), 1));
			curMproexchangeinfo.setChangeopationdate(CommonTool.getDateMask(curMproexchangeinfo.getChangeopationdate()));
		}
		else
		{
			this.curMproexchangeinfo=this.cc012Service.addMastRecord(this.strCurCompId);
		}
		
		this.lsDproexchangeinfobypro=this.cc012Service.loadDetialproById(strCurCompId,this.strCurBillId);
		this.lsDproexchangeinfo=this.cc012Service.loadDetialById(strCurCompId,this.strCurBillId);
		return SystemFinal.LOAD_SUCCESS;
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
			boolean canPostFlag=this.cc012Service.getDataTool().canSaveBill(this.curMproexchangeinfo.getId().getChangecompid(), CommonTool.getCurrDate(),errorMess);
			if(canPostFlag==false)
			{
				this.setStrMessage(errorMess.toString());
				return SystemFinal.POST_FAILURE; 
			}
			if(this.cc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC012", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.cc012Service.postChangeInfo(this.curMproexchangeinfo, this.lsDproexchangeinfo,this.lsDproexchangeinfobypro);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc012Service.afterPost(curMproexchangeinfo.getId().getChangecompid(), curMproexchangeinfo.getId().getChangebillid());
			if(flag==false)
			{
				this.strMessage="生成兑换信息有误,请到会员卡资料管理核实!";
				return SystemFinal.POST_FAILURE;
			}
			//会员卡购买疗程微信回执信息
			if(CommonTool.checkStr(strCardNo))
			{
				BigDecimal totalProamt = new BigDecimal(0);
				for (Dproexchangeinfo dproexchangeinfo : this.lsDproexchangeinfo) {
					totalProamt = totalProamt.add(dproexchangeinfo.getChangeproamt());
				}
				curMproexchangeinfo.setCurproaccountamt(totalProamt);//疗程消费金额临时存放
				new Thread(new Runnable(){
					public void run() {
						String openId = cc012Service.loadWechatOpenId(strCardNo);
						Map<String, String> mapParam=new HashMap<String, String>();
						mapParam.put("templateID", "o1fzQl4hZC0nFh8d4SOXQdclqNFgflsqkRETrJrbekI");
						mapParam.put("openid", openId);
						mapParam.put("first", "尊敬的会员，您好！您卡号 "+ strCardNo +" 的订单 "+ curMproexchangeinfo.getId().getChangebillid() +" 疗程购买成功！");
						mapParam.put("keyword1", cc012Service.getDataTool().loadCompNameById(curMproexchangeinfo.getId().getChangecompid()));
						mapParam.put("keyword2", String.valueOf(curMproexchangeinfo.getCurproaccountamt()) +"元。");
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Calendar nowTime = Calendar.getInstance();
						mapParam.put("keyword3", df.format(nowTime.getTime()));
						mapParam.put("remark", "祝您消费愉快！如有疑问请致电4006622818。");
						try {
							strMessage = HttpClientUtil.postMap("http://wechat.chinamani.com/AmaniWechatPlatform/ei/template", mapParam).replaceAll("\"", "");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
			//curMproexchangeinfo=null;
			lsDproexchangeinfo=null;
			lsDproexchangeinfobypro=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	

	@Action(value = "validatePackageNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validatePackageNo()
	{
		this.strMessage="";
		packageRateFlag=this.cc012Service.loadPackRate(strCurCompId,this.strCurPackageId);
		this.lsDmpackageinfo=this.cc012Service.getDataTool().loadDmpackageinfo(strCurCompId,this.strCurPackageId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "add",  results = { 
			 @Result(name = "add_success", type = "json"),
          @Result(name = "add_failure", type = "json")	
       }) 
	@Override
	public String add()
	{
		try
		{	
			this.curMproexchangeinfo=this.cc012Service.addMastRecord(this.strCurCompId);
			return SystemFinal.ADD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.ADD_FAILURE;
		}
	}
	
	@Action(value = "validateExchangecardno",  results = { 
			 @Result(name = "load_success", type = "json"),
         @Result(name = "load_failure", type = "json")	
      }) 
	public String validateExchangecardno()
	{
		try
		{
			this.strMessage="";
			this.curCardinfo=this.cc012Service.getDataTool().loadCardinfobyCardNo(strCurCompId, this.strCardNo);
			if(this.curCardinfo==null)
			{
				this.strMessage="该会员卡号在系统中不存在!";
				return SystemFinal.LOAD_FAILURE; 
			}
			else if(this.curCardinfo.getCardstate()!=4  && this.curCardinfo.getCardstate()!=5 )
			{
				this.strMessage="该会员卡为不可操作状态!";
				return SystemFinal.LOAD_FAILURE; 
			}
//			else if(!this.curCardinfo.getId().getCardvesting().equals(this.strCurCompId))
//			{
//				this.strMessage="对不起！此卡不属于本门店的卡!";
//				return SystemFinal.LOAD_FAILURE; 
//			}
//			else if(this.curCardinfo.getFillflag()!=1 )
//			{
//				this.strMessage="该会员卡为不允许续费!";
//				return SystemFinal.LOAD_FAILURE; 
//			}
			//验证是否是跨事业部转卡
			/*if(!strCurCompId.equals("001")&&!this.curCardinfo.getId().getCardvesting().equals("001")
				&&this.cc012Service.getDataTool().canOptionChangeCard_quyu(strCurCompId,strCardNo)==false)
			{
				this.setStrMessage("该卡不可跨事业部充值!");
				return SystemFinal.LOAD_FAILURE;
			}*/
			this.lsCardproaccount=this.cc012Service.getDataTool().loadProInfosByCardNo(strCurCompId, this.strCardNo);
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
			
			this.curProjectinfo=this.cc012Service.getDataTool().loadProjectInfo(strCurCompId, this.strCurItemId);
			if(curProjectinfo!=null && !CommonTool.FormatString(curProjectinfo.getPrjname()).equals(""))
				this.rateToRate=this.cc012Service.loadCardPrjCostRate(strCurCompId, CommonTool.FormatString(curProjectinfo.getPrjreporttype()));
			if(CommonTool.FormatDouble(rateToRate)==0)
			{
				this.rateToRate=1;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	@Action(value = "validateNointernalcardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateNointernalcardno()
	{
		try
		{
			this.strMessage="";
			this.diyqAmt=this.cc012Service.validateDiyqNo(this.strDiyqNo);
			if(CommonTool.FormatDouble(diyqAmt)==0)
			{
				this.strMessage="该抵用券不存在或不能使用,请查询确认!";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	@Action(value = "checkmanagerPass",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String checkmanagerPass()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.cc012Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
			if(flag==false)
			{
				this.strMessage="该经理密码不对,请核实";
				return SystemFinal.LOAD_SUCCESS;
			}
			//boolean flag=this.cc012Service.validateManagerPass(this.strCurManagerNo, this.strCurManagerPass);
			//if(flag==false)
				//this.strMessage="经理密码有误,请确认";
			//else
			String strSp028=this.cc012Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"), "SP028");
			if(CommonTool.FormatString(strSp028).equals(""))
				this.SP028Rate=100;
			this.SP028Rate=CommonTool.FormatDouble(Double.parseDouble(strSp028));
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
		Map values = this.cc012Service.getDataTool().getReportParams( this.strCurCompId, this.strCurBillId);
	    this.billDate = CommonTool.getDateMask(this.billDate);
	    this.companName = values.get("companName").toString();
	    this.companTel = values.get("companTel").toString();
	    this.companAddr = values.get("companAddr").toString();
	    this.cashMemberName=values.get("cashMemberId").toString();
	    this.printTime = CommonTool.getTimeMask(CommonTool.getCurrTime(), 1);
	    this.printDate = CommonTool.getDateMask(CommonTool.getCurrDate());
	    this.curCardinfo = this.cc012Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId, this.strCardNo);
	    this.lsDproexchangeinfo=this.cc012Service.loadDetialById(strCurCompId,this.strCurBillId);
	    values=null;
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadConsumeCtype",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadConsumeCtype(){
		try{
			lsMconsumeinfo=this.cc012Service.loadConsumeCtype();
			return SystemFinal.LOAD_SUCCESS;
		}catch(Exception ex){
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	@Action(value = "upCtypeState",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String upCtypeState(){
		try{
			boolean bool = this.cc012Service.upConsumeCtype(strCurBillId);
			strMessage = bool ? "" : "操作疗程折扣失败！";
			return SystemFinal.LOAD_SUCCESS;
		}catch(Exception ex){
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	//推送微信密码
	@Action( value="sendWechatPwd", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String sendWechatPwd() {
		String openId = this.cc012Service.loadWechatOpenId(strCardNo);
		if(StringUtils.isBlank(openId)){
			strMessage ="该卡号未绑定微信，请确认！";
			return SystemFinal.LOAD_SUCCESS;
		}
		Map<String, String> mapParam=new HashMap<String, String>();
		mapParam.put("templateID", "UI_t3XRO6KtDVUl5mYsR80a2P1ctkF0OI_AMP3-XwXQ");
		mapParam.put("openid", openId);
		String ramdom = String.valueOf(((int)((Math.random()*9+1)*100000)));
		mapParam.put("first", "尊敬的会员，您好！您本次购买疗程的验证码为：");
		mapParam.put("keyword1", CommonTool.getLoginInfo("COMPNAME") +"-消费");
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
	
	public List<Mconsumeinfo> getLsMconsumeinfo() {
		return lsMconsumeinfo;
	}
	public void setLsMconsumeinfo(List<Mconsumeinfo> lsMconsumeinfo) {
		this.lsMconsumeinfo = lsMconsumeinfo;
	}
	public List<Mproexchangeinfo> getLsMproexchangeinfo() {
		return lsMproexchangeinfo;
	}
	public void setLsMproexchangeinfo(List<Mproexchangeinfo> lsMproexchangeinfo) {
		this.lsMproexchangeinfo = lsMproexchangeinfo;
	}
	public List<Dproexchangeinfo> getLsDproexchangeinfo() {
		return lsDproexchangeinfo;
	}
	public void setLsDproexchangeinfo(List<Dproexchangeinfo> lsDproexchangeinfo) {
		this.lsDproexchangeinfo = lsDproexchangeinfo;
	}
	public List<Dproexchangeinfobypro> getLsDproexchangeinfobypro() {
		return lsDproexchangeinfobypro;
	}
	public void setLsDproexchangeinfobypro(
			List<Dproexchangeinfobypro> lsDproexchangeinfobypro) {
		this.lsDproexchangeinfobypro = lsDproexchangeinfobypro;
	}
	public Mproexchangeinfo getCurMproexchangeinfo() {
		return curMproexchangeinfo;
	}
	public void setCurMproexchangeinfo(Mproexchangeinfo curMproexchangeinfo) {
		this.curMproexchangeinfo = curMproexchangeinfo;
	}
	public List<Staffinfo> getLsStaffinfo() {
		return lsStaffinfo;
	}
	public void setLsStaffinfo(List<Staffinfo> lsStaffinfo) {
		this.lsStaffinfo = lsStaffinfo;
	}
	public List<Projectinfo> getLsProjectinfo() {
		return lsProjectinfo;
	}
	public void setLsProjectinfo(List<Projectinfo> lsProjectinfo) {
		this.lsProjectinfo = lsProjectinfo;
	}
	
	public Cardinfo getCurCardinfo() {
		return curCardinfo;
	}
	public void setCurCardinfo(Cardinfo curCardinfo) {
		this.curCardinfo = curCardinfo;
	}
	public String getStrCardNo() {
		return strCardNo;
	}
	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}
	public List<Cardproaccount> getLsCardproaccount() {
		return lsCardproaccount;
	}
	public void setLsCardproaccount(List<Cardproaccount> lsCardproaccount) {
		this.lsCardproaccount = lsCardproaccount;
	}
	public String getStrProJsonParam() {
		return strProJsonParam;
	}
	public void setStrProJsonParam(String strProJsonParam) {
		this.strProJsonParam = strProJsonParam;
	}
	public List<Mpackageinfo> getLsMpackageinfo() {
		return lsMpackageinfo;
	}
	public void setLsMpackageinfo(List<Mpackageinfo> lsMpackageinfo) {
		this.lsMpackageinfo = lsMpackageinfo;
	}
	public String getStrCurPackageId() {
		return strCurPackageId;
	}
	public void setStrCurPackageId(String strCurPackageId) {
		this.strCurPackageId = strCurPackageId;
	}
	public List<Dmpackageinfo> getLsDmpackageinfo() {
		return lsDmpackageinfo;
	}
	public void setLsDmpackageinfo(List<Dmpackageinfo> lsDmpackageinfo) {
		this.lsDmpackageinfo = lsDmpackageinfo;
	}
	public int getSP095Flag() {
		return SP095Flag;
	}
	public void setSP095Flag(int flag) {
		SP095Flag = flag;
	}
	public int getSP099Flag() {
		return SP099Flag;
	}
	public void setSP099Flag(int flag) {
		SP099Flag = flag;
	}
	public int getPackageRateFlag() {
		return packageRateFlag;
	}
	public void setPackageRateFlag(int packageRateFlag) {
		this.packageRateFlag = packageRateFlag;
	}

	public double getRateToRate() {
		return rateToRate;
	}

	public void setRateToRate(double rateToRate) {
		this.rateToRate = rateToRate;
	}

	public int getSP117Flag() {
		return SP117Flag;
	}
	public void setSP117Flag(int sP117Flag) {
		SP117Flag = sP117Flag;
	}
	public String getSP118Flag() {
		return SP118Flag;
	}
	public void setSP118Flag(String sP118Flag) {
		SP118Flag = sP118Flag;
	}
}
