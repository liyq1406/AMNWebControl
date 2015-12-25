package com.amani.action.CardControl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.bean.PringBillBean;
import com.amani.bean.SpadDconsumeInfo;
import com.amani.bean.SpadMconsumeInfo;
import com.amani.bean.TradeAnalysis;
import com.amani.model.ActivateCard;
import com.amani.model.Cardaccount;
import com.amani.model.Cardinfo;
import com.amani.model.Cardproaccount;
import com.amani.model.Cardspecialcost;
import com.amani.model.Cardtransactionhistory;
import com.amani.model.Companyinfo;
import com.amani.model.Corpsbuyinfo;
import com.amani.model.Dconsumeinfo;
import com.amani.model.DconsumeinfoId;
import com.amani.model.Dmedicalcare;
import com.amani.model.Dmpackageinfo;
import com.amani.model.Dnointernalcardinfo;
import com.amani.model.Dpayinfo;
import com.amani.model.DpayinfoId;
import com.amani.model.Goodsinfo;
import com.amani.model.Mconsumeinfo;
import com.amani.model.Mpackageinfo;
import com.amani.model.MpackageinfoId;
import com.amani.model.Nointernalcardinfo;
import com.amani.model.Projectinfo;
import com.amani.model.Wxbandcard;
import com.amani.model.Yearcarddetal;
import com.amani.model.Yearcardinof;
import com.amani.service.AliPayService;
import com.amani.service.WechatPayService;
import com.amani.service.CardControl.CC011Service;
import com.amani.tools.CommonTool;
import com.amani.tools.HttpClientUtil;
import com.amani.tools.SystemFinal;
import com.opensymphony.xwork2.ActionContext;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc011")
public class CC011Action extends AMN_ModuleAction{
	private static final long serialVersionUID = 3567241663005830782L;
	@Autowired
	private CC011Service cc011Service;
	@Autowired
	private WechatPayService wechatPayService;
	@Autowired
	private AliPayService aliPayService;
	private String strJsonParam;	
	private String strCardNo;
	private String strRandomno;
	private String strYearInid;
	private List<Yearcardinof> lsYearCard;
	private List<Dmedicalcare> medicalSet;
	private List<Mpackageinfo> lsMpackageinfo;
    private List<Dmpackageinfo> lsDmpackageinfo;
    private boolean checkSendIntegral;
    private String wxflag;
    
    private Logger logger = Logger.getLogger(this.getClass().getName());
	public List<Yearcardinof> getLsYearCard() {
		return lsYearCard;
	}

	public void setLsYearCard(List<Yearcardinof> lsYearCard) {
		this.lsYearCard = lsYearCard;
	}

	private List<Mconsumeinfo> lsBillState;
	public List<Mconsumeinfo> getLsBillState() {
		return lsBillState;
	}

	public void setLsBillState(List<Mconsumeinfo> lsBillState) {
		this.lsBillState = lsBillState;
	}

	private List<Cardtransactionhistory> lsCardtransactionhistories;
	public List<Cardtransactionhistory> getLsCardtransactionhistories() {
		return lsCardtransactionhistories;
	}

	public void setLsCardtransactionhistories(
			List<Cardtransactionhistory> lsCardtransactionhistories) {
		this.lsCardtransactionhistories = lsCardtransactionhistories;
	}

	public String getStrYearInid() {
		return strYearInid;
	}

	public void setStrYearInid(String strYearInid) {
		this.strYearInid = strYearInid;
	}

	public String getStrRandomno() {
		return strRandomno;
	}

	public void setStrRandomno(String strRandomno) {
		this.strRandomno = strRandomno;
	}

	public Wxbandcard getWxbandcard() {
		return wxbandcard;
	}

	public void setWxbandcard(Wxbandcard wxbandcard) {
		this.wxbandcard = wxbandcard;
	}

	private String strCardType;
	private String strCurCompId;
	private String strCurBillId;
	private String strCurItemId;
	private Wxbandcard wxbandcard;
	private String strCurPayMode;
	private String strCurItemName;
	private String HDPhone;
	private List<String> lsStrings;
	public List<String> getLsStrings() {
		return lsStrings;
	}

	public void setLsStrings(List<String> lsStrings) {
		this.lsStrings = lsStrings;
	}

	public String getHDPhone() {
		return HDPhone;
	}

	public void setHDPhone(String hDPhone) {
		HDPhone = hDPhone;
	}

	private String handMemberName;
	private String handPhone;
	private Yearcardinof yearcardinof;
	private List<Yearcarddetal> lsYearcarddetals;
	public Yearcardinof getYearcardinof() {
		return yearcardinof;
	}

	public void setYearcardinof(Yearcardinof yearcardinof) {
		this.yearcardinof = yearcardinof;
	}

	public List<Yearcarddetal> getLsYearcarddetals() {
		return lsYearcarddetals;
	}

	public void setLsYearcarddetals(List<Yearcarddetal> lsYearcarddetals) {
		this.lsYearcarddetals = lsYearcarddetals;
	}

	private String handPcid;
	private BigDecimal dProjectAmt;
	private BigDecimal dGoodsAmt;
	private String strCompId;
	private String strPhone;
	private String strName;

	public String getStrPhone() {
		return strPhone;
	}

	public void setStrPhone(String strPhone) {
		this.strPhone = strPhone;
	}
	
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}

	private List<Mconsumeinfo> lsMconsumeinfos;
    private List<Dconsumeinfo> lsDconsumeinfos;
    //------------------------前台对应六个支付
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
    private String strTuanGouCardNo;
    private Corpsbuyinfo curCorpsbuyinfo;
    private String strTiaoMaCardNo;
    private String strTiaoMaCardPassword;
    private String strDiYongCardNo;
    private BigDecimal dDiyongAmt;
    private List<Dnointernalcardinfo> lsDnointernalcardinfo;
    private List<Dpayinfo> lsDpayinfos;
    private List<Cardproaccount> lsCardproaccount;
    private Mconsumeinfo curMconsumeinfo;
    private Cardinfo curCardinfo;
    private Goodsinfo curGoodsinfo;
    private Projectinfo curProjectinfo;
    public int itemType;
    private String strSalePayMode;
    private String strSearchBillId;
    private String paramtotiaomacardinfo;
    private String strSearchDate;
    private TradeAnalysis curTradeAnalysis;
    private boolean bRet;
    private String strEmpInid;
    
    public String getStrEmpInid() {
		return strEmpInid;
	}

	public void setStrEmpInid(String strEmpInid) {
		this.strEmpInid = strEmpInid;
	}

	public boolean isbRet() {
		return bRet;
	}

	public void setbRet(boolean bRet) {
		this.bRet = bRet;
	}

	private int paramSp094 ;//是否启用产品折扣设定
    private int paramSp105 ;//是否启用产品折扣设定
    private BigDecimal dCostGoodsRate; 
    
    private BigDecimal dCostProjectRate;
    private int cardUseType		;    // 1 内部卡  2 收购卡 3 积分账户 4 现金账户
    private BigDecimal dstandprice;		//单次价
    private BigDecimal donecountprice;		//单次价
    private BigDecimal donepageprice;		//体验价格(散客价)
	private BigDecimal dmemberprice;	   //体验价格(会员价)
	private BigDecimal dcuxiaoprice;		//促销价格
	private String strCurManagerNo;
	private String strCurManagerPass;
    
	private  double	sumCostAmt;
	private  int 	cardStateFlag;
    /************小票打印***************/
    private String memberCardId;
    private String payType;
    private String totalNumber;
    private String totalMoney;
    private String shopId;
    private String ticketId;
    private String cashMemberId;
    private String strMemberPhone;
    private String companName;
    private String companTel;
    private String companNet;
    private String companAddr;
    private String cashMemberName;
    private String printTime;
    private String printDate;
    private String billDate;
    private List<PringBillBean> costListProj;
    private List<PringBillBean> costListProd;
    private List<Dpayinfo> payTypeList;
    private List<Cardaccount> lsPrintCardaccount;
    private List<Cardproaccount> lsPrintCardproaccount;
    
    private List<SpadMconsumeInfo> lsSpadMconsumeInfo;
    private List<SpadDconsumeInfo> lsSpadDconsumeInfo;
    private Cardspecialcost curCardspecialcost;
    private String strTMCardpassword;
    private double SP027Rate; //经理打折上限
    private int paramSp097 ;//是否允许输入中工
    private int paramSp098 ;//是否允许输入中工
    private int paramSp104 ;//是否限制卡消费额度
    private int paramSp108 ;//是否需要读卡  0 需要 1 不需要
    private int paramSp112 ;//是否开启新老客系统自动结算
    private int paramSp113 ;//是否开启新老客系统自动结算
    private int paramSp016;//是否开启新老客系统自动结算
    public int getParamSp016() {
		return paramSp016;
	}

	public void setParamSp016(int paramSp016) {
		this.paramSp016 = paramSp016;
	}

	public int getParamSp113() {
		return paramSp113;
	}

	public void setParamSp113(int paramSp113) {
		this.paramSp113 = paramSp113;
	}

	public int getParamSp112() {
		return paramSp112;
	}

	public void setParamSp112(int paramSp112) {
		this.paramSp112 = paramSp112;
	}

	private double curCostAmt; //当日消费总储值金额
    private double curMfCostCount; //当日消费总储值金额
    private double curMrCostCount; //当日消费总储值金额
    /************小票打印***************/
    
    /************PAD 开单**************/
    private String strPadBillId;  //pad开单单号
    private String strPadKeyId;	  //pad开单钥匙牌
    private int	activityCardFlag;
    /***********经理卡验证*******************/
    private String mangerCardNo;
    public String getMangerCardNo() {
		return mangerCardNo;
	}

	public void setMangerCardNo(String mangerCardNo) {
		this.mangerCardNo = mangerCardNo;
	}
    
	public String getStrPadBillId() {
		return strPadBillId;
	}
	public void setStrPadBillId(String strPadBillId) {
		this.strPadBillId = strPadBillId;
	}
	public String getStrPadKeyId() {
		return strPadKeyId;
	}
	public void setStrPadKeyId(String strPadKeyId) {
		this.strPadKeyId = strPadKeyId;
	}
	public String getStrSearchDate() {
		return strSearchDate;
	}
	public void setStrSearchDate(String strSearchDate) {
		this.strSearchDate = strSearchDate;
	}
	public String getParamtotiaomacardinfo() {
		return paramtotiaomacardinfo;
	}
	public void setParamtotiaomacardinfo(String paramtotiaomacardinfo) {
		this.paramtotiaomacardinfo = paramtotiaomacardinfo;
	}
	public String getStrSearchBillId() {
		return strSearchBillId;
	}
	public void setStrSearchBillId(String strSearchBillId) {
		this.strSearchBillId = strSearchBillId;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected boolean beforePost() {
		curMconsumeinfo.setCsopationdate(CommonTool.setDateMask(curMconsumeinfo.getCsopationdate()));
		curMconsumeinfo.setCsdate(CommonTool.setDateMask(curMconsumeinfo.getCsdate()));
		curMconsumeinfo.setFinancedate(CommonTool.setDateMask(curMconsumeinfo.getCsdate()));
		curMconsumeinfo.setCsstarttime(CommonTool.setTimeMask(curMconsumeinfo.getCsstarttime(), 1));
		curMconsumeinfo.setCsendtime(CommonTool.setTimeMask(CommonTool.getCurrTime(), 1));
		curMconsumeinfo.setCscardno(CommonTool.FormatString(curMconsumeinfo.getCscardno()).trim());
		curMconsumeinfo.getId().setCsbillid(this.cc011Service.getDataTool().loadBillIdByRule(curMconsumeinfo.getId().getCscompid(),"mconsumeinfo", "csbillid", "SP007"));
		if(scanpaytype==1||scanpaytype==2||scanpaytype==3){//为第三方支付方式时：支付宝或微信支付、OK卡
			curMconsumeinfo.setScanpaytype(scanpaytype);
			if(scanpaytype==1||scanpaytype==2){
				curMconsumeinfo.setScanbarcode(auth_code);
				curMconsumeinfo.setScantradeno(outTradeNo);
			}
		}
		this.lsDconsumeinfos=this.cc011Service.getDataTool().loadDTOList(strJsonParam, Dconsumeinfo.class);
		if(lsDconsumeinfos!=null && lsDconsumeinfos.size()>0)
		{
			for(int i=0;i<lsDconsumeinfos.size();i++)
			{
				Dconsumeinfo dconsume = lsDconsumeinfos.get(i);
				if(dconsume!=null && !CommonTool.FormatString(dconsume.getCsitemno()).equals(""))
				{
					dconsume.setId(new DconsumeinfoId(curMconsumeinfo.getId().getCscompid(),curMconsumeinfo.getId().getCsbillid(),dconsume.getBcsinfotype(),i*1.0));
				}
				else
				{
					lsDconsumeinfos.remove(i);
					i--;
					continue;
				}
				if(StringUtils.equals("15", dconsume.getCspaymode()) && (scanpaytype==1||scanpaytype==2||scanpaytype==3)){//为第三方支付方式时：支付宝或微信支付、OK卡
					dconsume.setScanpaytype(scanpaytype);
					dconsume.setScanpayamt(dconsume.getCsitemamt());
				}
				Integer costpricetype = dconsume.getCostpricetype();//接发活动
				if(costpricetype != null){
					BigDecimal csitemamt = dconsume.getCsitemamt();
					if(costpricetype==101){//本店客人：公司35%，当店65%
						BigDecimal ratio = new BigDecimal("0.35");
						BigDecimal haircompamt = csitemamt.multiply(ratio).setScale(2, BigDecimal.ROUND_HALF_UP);
						dconsume.setHaircompamt(haircompamt);
					}else if(costpricetype==102 || costpricetype==103){//合作方客人-本店和合作方操作：公司75%，当店25%
						BigDecimal ratio = new BigDecimal("0.75");
						BigDecimal haircompamt = csitemamt.multiply(ratio).setScale(2, BigDecimal.ROUND_HALF_UP);
						dconsume.setHaircompamt(haircompamt);
					}else if(costpricetype==104){//其他店客人：公司35%，当店26%，其他店39%
						BigDecimal ratio = new BigDecimal("0.35");
						BigDecimal haircompamt = csitemamt.multiply(ratio).setScale(2, BigDecimal.ROUND_HALF_UP);
						dconsume.setHaircompamt(haircompamt);
						dconsume.setHairstaff("0");//hairstaff改成标识位 0是本店 1是推荐店
						try {
							String[] array = StringUtils.split(dconsume.getYearinid(), "-");
							dconsume.setYearinid(null);
							Mconsumeinfo mconsumeinfo = (Mconsumeinfo) deepCopy(curMconsumeinfo);
							mconsumeinfo.getId().setCscompid(array[0]);
							String csbillid = mconsumeinfo.getId().getCsbillid()+"_JF";
							mconsumeinfo.getId().setCsbillid(csbillid);
							//消费明细
							Dconsumeinfo dconsumeinfo = (Dconsumeinfo) deepCopy(dconsume);
							dconsumeinfo.getId().setCscompid(array[0]);
							dconsumeinfo.getId().setCsbillid(csbillid);
							BigDecimal _ratio = new BigDecimal("0.39");
							BigDecimal payamt = csitemamt.multiply(_ratio).setScale(2, BigDecimal.ROUND_HALF_UP);
							dconsumeinfo.setCsunitprice(payamt);
							dconsumeinfo.setCsdisprice(payamt);
							dconsumeinfo.setCsitemamt(payamt);
							dconsumeinfo.setHaircompamt(payamt);
							dconsumeinfo.setHairstaff("1");
							dconsumeinfo.setCsfirstsaler(array[1]);//推荐门店员工放到大工位置，大工内部工号
							dconsumeinfo.setCsfirsttype("2");//新客类型
							dconsumeinfo.setCsfirstinid(this.cc011Service.getDataTool().loadEmpInidById(array[0], array[1]));
							//其他推荐店的支付明细
							Dpayinfo dpayinfo=new Dpayinfo();
							dpayinfo.setId(new DpayinfoId(array[0],csbillid,"SY",0));
							dpayinfo.setPayamt(payamt);
							dpayinfo.setPaymode(dconsumeinfo.getCspaymode());
							dpayinfo.setPayremark(curMconsumeinfo.getId().getCscompid() +"店接发推荐提成");
							cc011Service.postHairOther(mconsumeinfo, dconsumeinfo, dpayinfo);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				//积分支付方式（积分和美容积分）
				if(StringUtils.equals("7", dconsume.getCspaymode())){
					dconsume.setIntegralcode(Integer.parseInt(dconsume.getYearinid()));
					dconsume.setYearinid(null);
				}else{
					dconsume.setIntegralcode(null);
				}
				dconsume.setSaledate(CommonTool.setDateMask(dconsume.getSaledate()));
			}
		}
		this.lsDnointernalcardinfo=this.cc011Service.getDataTool().loadDTOList(this.paramtotiaomacardinfo, Dnointernalcardinfo.class);
		if(lsDnointernalcardinfo!=null && lsDnointernalcardinfo.size()>0)
		{
			for(int i=0;i<lsDnointernalcardinfo.size();i++)
			{
				if(lsDnointernalcardinfo.get(i)!=null && !CommonTool.FormatString(lsDnointernalcardinfo.get(i).getIneritemno()).equals(""))
				{
					lsDnointernalcardinfo.get(i).setBcardno(CommonTool.FormatString(curMconsumeinfo.getTiaomacardno()));
				}
				else
				{
					lsDnointernalcardinfo.remove(i);
					i--;
				}
			}
		}
		int isDiyongFlag=0;
		int isTiaoMaFlag=0;
		lsDpayinfos=new ArrayList();
		Dpayinfo payRecord=null;
		if(!CommonTool.FormatString(this.strPayMode1).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt1).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(curMconsumeinfo.getId().getCscompid(),curMconsumeinfo.getId().getCsbillid(),"SY",0));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt1));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode1));
			lsDpayinfos.add(payRecord);
			if(strPayMode1.equals("11") || strPayMode1.equals("12"))
				isDiyongFlag=1;
			if(strPayMode1.equals("13") )
				isTiaoMaFlag=1;
			if(strPayMode1.equals("4") || strPayMode1.equals("A") || strPayMode1.equals("9"))
				sumCostAmt=sumCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt1).doubleValue();
//			if(strPayMode1.equals("4"))
//					curCostAmt=curCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt1).doubleValue();
		}
		if(!CommonTool.FormatString(this.strPayMode2).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt2).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(curMconsumeinfo.getId().getCscompid(),curMconsumeinfo.getId().getCsbillid(),"SY",1));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt2));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode2));
			lsDpayinfos.add(payRecord);
			if(strPayMode2.equals("11") || strPayMode2.equals("12"))
				isDiyongFlag=1;
			if(strPayMode2.equals("13") )
				isTiaoMaFlag=1;
			if(strPayMode2.equals("4") || strPayMode2.equals("A") || strPayMode2.equals("9"))
				sumCostAmt=sumCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt2).doubleValue();
//			if(strPayMode2.equals("4"))
//				curCostAmt=curCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt2).doubleValue();
			
		}
		if(!CommonTool.FormatString(this.strPayMode3).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt3).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(curMconsumeinfo.getId().getCscompid(),curMconsumeinfo.getId().getCsbillid(),"SY",2));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt3));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode3));
			lsDpayinfos.add(payRecord);
			if(strPayMode3.equals("11") || strPayMode3.equals("12"))
				isDiyongFlag=1;
			if(strPayMode3.equals("13") )
				isTiaoMaFlag=1;
			if(strPayMode3.equals("4") || strPayMode3.equals("A") || strPayMode3.equals("9"))
				sumCostAmt=sumCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt3).doubleValue();
//			if(strPayMode3.equals("4"))
//				curCostAmt=curCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt3).doubleValue();
		}
		if(!CommonTool.FormatString(this.strPayMode4).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt4).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(curMconsumeinfo.getId().getCscompid(),curMconsumeinfo.getId().getCsbillid(),"SY",3));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt4));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode4));
			lsDpayinfos.add(payRecord);
			if(strPayMode4.equals("11") || strPayMode4.equals("12"))
				isDiyongFlag=1;
			if(strPayMode4.equals("13") )
				isTiaoMaFlag=1;
			if(strPayMode4.equals("4") || strPayMode4.equals("A") || strPayMode4.equals("9"))
				sumCostAmt=sumCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt4).doubleValue();
//			if(strPayMode4.equals("4"))
//				curCostAmt=curCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt4).doubleValue();
		}
		if(!CommonTool.FormatString(this.strPayMode5).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt5).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(curMconsumeinfo.getId().getCscompid(),curMconsumeinfo.getId().getCsbillid(),"SY",4));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt5));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode5));
			lsDpayinfos.add(payRecord);
			if(strPayMode5.equals("11") || strPayMode5.equals("12"))
				isDiyongFlag=1;
			if(strPayMode5.equals("13") )
				isTiaoMaFlag=1;
			if(strPayMode5.equals("4") || strPayMode5.equals("A") || strPayMode5.equals("9"))
				sumCostAmt=sumCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt5).doubleValue();
//			if(strPayMode5.equals("4"))
//				curCostAmt=curCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt5).doubleValue();
		}
		if(!CommonTool.FormatString(this.strPayMode6).equals("") && CommonTool.FormatBigDecimal(this.dPayAmt6).doubleValue()!=0)
		{
			payRecord=new Dpayinfo();
			payRecord.setId(new DpayinfoId(curMconsumeinfo.getId().getCscompid(),curMconsumeinfo.getId().getCsbillid(),"SY",5));
			payRecord.setPayamt( CommonTool.FormatBigDecimal(this.dPayAmt6));
			payRecord.setPaymode(CommonTool.FormatString(this.strPayMode6));
			lsDpayinfos.add(payRecord);
			if(strPayMode6.equals("11") || strPayMode6.equals("12"))
				isDiyongFlag=1;
			if(strPayMode6.equals("13") )
				isTiaoMaFlag=1;
			if(strPayMode6.equals("4") || strPayMode6.equals("A") || strPayMode6.equals("9"))
				sumCostAmt=sumCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt6).doubleValue();
//			if(strPayMode6.equals("4"))
//				curCostAmt=curCostAmt+CommonTool.FormatBigDecimal(this.dPayAmt6).doubleValue();
		}
		if(isDiyongFlag==0)
		{
			this.curMconsumeinfo.setDiyongcardno("");
		}
		if(isTiaoMaFlag==0)
		{
			this.curMconsumeinfo.setTiaomacardno("");
		}
		return true;
	}
	
	@Action(value = "loadPhone",  results = { 
			 @Result(name = "load_success", type = "json"),
         @Result(name = "load_failure", type = "json")	
     })
	public String loadPhone()
	{
		this.setStrMessage("");
		yearcardinof=cc011Service.loadYearcardinof(strPhone);
		if(yearcardinof==null)
		{
			this.setStrMessage("你输入的手机号码没找到,请重新输入");
			return SystemFinal.LOAD_FAILURE;
		}
		lsYearcarddetals=cc011Service.loadYearcarddetals(strPhone);
		StringBuffer buffer=new StringBuffer();
		if(lsYearcarddetals!=null && lsYearcarddetals.size()>0)
		{
			for(int i=0;i<lsYearcarddetals.size();i++)
			{
				lsYearcarddetals.get(i).setValidate(CommonTool.getDateMask(lsYearcarddetals.get(i).getValidate()));
				lsYearcarddetals.get(i).setItemname(cc011Service.getDataTool().loadProjectName(CommonTool.getLoginInfo("COMPID"),lsYearcarddetals.get(i).getItemno(),buffer));
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadMedical",  results = { 
			 @Result(name = "load_success", type = "json"),
        @Result(name = "load_failure", type = "json")	
    })
	public String loadMedical(){
		medicalSet=cc011Service.loadMedical(strPhone, strName);
		if(medicalSet == null || medicalSet.size()==0){
			this.setStrMessage("没有查询到项目疗程信息！");
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadYearInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
         @Result(name = "load_success", type = "json")	
     }) 
	public String loadYearInfo()
	{
		strMessage="";
		lsYearCard=cc011Service.loadYearInfo(strCurItemName);
		if(lsYearCard==null || lsYearCard.size()<1)
		{
			strMessage="没有找到年卡信息";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "checkClientType",  results = { 
			 @Result(name = "load_success", type = "json"),
          @Result(name = "load_success", type = "json")	
      }) 
	public String checkClientType()
	{
		if(strCardNo!=null && strCardNo.indexOf("散客")==0)
		{
			bRet=cc011Service.checkClientType(strCardNo, strEmpInid);
		}
		else
		{
			bRet=false;
		}
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
			strCompId=CommonTool.getLoginInfo("COMPID");
			StringBuffer errorMess=new StringBuffer();
			boolean canPostFlag=this.cc011Service.getDataTool().canSaveBill(this.curMconsumeinfo.getId().getCscompid(), CommonTool.getCurrDate(),errorMess);
			if(canPostFlag==false)
			{
				this.setStrMessage(errorMess.toString());
				return SystemFinal.POST_FAILURE; 
			}
			if(this.cc011Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC011", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			
			this.beforePost();
			if(cc011Service.checkDaysCount(lsDconsumeinfos)==false)
			{
				strMessage="年卡疗程每天只能消费一次";
				return SystemFinal.POST_FAILURE;
			}
			/*if(cc011Service.checkMonthCount(lsDconsumeinfos)==false)
			{
				strMessage="年卡美容疗程每月只能消费6次美发项目每月只能消费3次";
				return SystemFinal.POST_FAILURE;
			}*/
			if(lsDconsumeinfos!=null && lsDconsumeinfos.size()>0)
			{
				if(cc011Service.checkBueatPrj(lsDconsumeinfos, 1)==false)
				{
					strMessage="积分不能购买美容项目";
					return SystemFinal.POST_FAILURE;
				}
				if(cc011Service.checkBueatPrj(lsDconsumeinfos, 2))
				{
					strMessage="美容积分只能购买美容项目";
					return SystemFinal.POST_FAILURE;
				}
			}
			
			/*if(!CommonTool.FormatString(curMconsumeinfo.getCscardno()).equals("散客"))
			{
				//判断卡的消费额度
				double curDateCost=this.cc011Service.loadCurDateCostAmt(CommonTool.FormatString(curMconsumeinfo.getCscardno()));
				if(CommonTool.FormatDouble(curDateCost)+CommonTool.FormatDouble(curCostAmt)>2500)
				{
					this.strMessage="会员卡"+curMconsumeinfo.getCscardno()+"今天已累计消费"+CommonTool.FormatDouble(curDateCost)+",加上本次消费"+CommonTool.FormatDouble(curCostAmt)+",已超出2500额定范围,不能保存!";
					return SystemFinal.POST_FAILURE;
				}
			}*/
			
			if(this.curMconsumeinfo.getId()==null || CommonTool.FormatString(this.curMconsumeinfo.getId().getCsbillid()).equals(""))
			{
				this.strMessage="生成消费单号有误,请刷新后重新保存此单据!";
				return SystemFinal.POST_FAILURE;
			}
			
			if(Integer.parseInt(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP017"))==1)
			{
				if(this.cc011Service.checkGoodStock(curMconsumeinfo.getId().getCscompid(), lsDconsumeinfos)==false)
				{
					this.strMessage="产品库存不足，检查一下是否有产品没有收货";
					return SystemFinal.POST_FAILURE;
				}
			}
			
			/*if(CommonTool.checkStr(curMconsumeinfo.getRandomno()))
			{
				if(cc011Service.checkOpenid(curMconsumeinfo.getRandomno())==false)
				{
					this.strMessage="微信条码不存在，或者该会员还有未结账的单据";
					return SystemFinal.POST_FAILURE;
				}
				curMconsumeinfo.setBackcsflag(1);
			}*/
			//条码消费产品 (不考虑负库存)
			boolean flag=this.cc011Service.postConsum(this.curMconsumeinfo, this.lsDconsumeinfos,this.lsDpayinfos,this.lsDnointernalcardinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			
			if("true".equals(wxflag)){
				//微信核销卡券
				String response = "";
				String reqUrl=String.format("http://wechat.chinamani.com/AmaniWechatPlatform/consume?getToken=true&code=%s",curMconsumeinfo.getDiyongcardno());
				URL url = new URL(reqUrl);  
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
				conn.setDoOutput(true);  
				conn.setRequestMethod("GET");  
				conn.setRequestProperty("Content-Type", "text/json");  
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
				String line;  
				while ((line = rd.readLine()) != null) {  
					response = line;
				}  
				rd.close();
				JSONObject res=JSONObject.fromObject(response);
				if(0 != Integer.valueOf(res.getString("error"))){
					logger.error("CC011Action.java post wx error:" + res.getString("error"));
					this.strMessage="请出示代金券！";
					return SystemFinal.POST_FAILURE;
				}
			}
			
			int costtype=1;
			if(!CommonTool.FormatString(curMconsumeinfo.getCscardno()).equals("散客"))
			{
				costtype=2;
			}
			//if(CommonTool.checkStr(curMconsumeinfo.getRandomno())==false)
			//{
				flag=this.cc011Service.afterPost(curMconsumeinfo.getId().getCscompid(), curMconsumeinfo.getId().getCsbillid(), curMconsumeinfo.getCsdate(), costtype);
				if(flag==false)
				{
					this.strMessage="生成消费历史有误,请到会员卡资料管理核实!";
					return SystemFinal.POST_FAILURE;
				}
				if(cc011Service.zsHDDYQ(HDPhone, lsDconsumeinfos)==false)
				{
					this.strMessage="合作项目体验生成错误!";
					return SystemFinal.POST_FAILURE;
				}
				else 
				{
					
				}
				//更新活动期的消费内容
				if(!CommonTool.FormatString(curMconsumeinfo.getDiyongcardno()).equals(""))
				{
					flag=this.cc011Service.validateActivityCardState(curMconsumeinfo.getDiyongcardno());
					if(flag==true)
					{
						String strDyPrjNo="";
						if(lsDconsumeinfos!=null && lsDconsumeinfos.size()>0 )
						{
							for(int i=0;i<lsDconsumeinfos.size();i++)
							{
								if(CommonTool.FormatString(lsDconsumeinfos.get(i).getCspaymode()).equals("11"))
								{
									strDyPrjNo=lsDconsumeinfos.get(i).getCsitemno();
								}
							}
						}
						flag=this.cc011Service.handActivtyCardState(CommonTool.FormatString(curMconsumeinfo.getDiyongcardno()), curMconsumeinfo.getId().getCscompid(), curMconsumeinfo.getId().getCsbillid(), strDyPrjNo);
						if(flag==false)
						{
							this.strMessage="消费单保存成功,生成活动券信息有误!";
							return SystemFinal.POST_FAILURE;
						}
					}
				}
				if(CommonTool.checkStr(curMconsumeinfo.getRandomno())==false)
				{
					new Thread(new Runnable()
					{
						public void run() {
							//发送短信
							sendMconsumeMsg();
						}
					}).start();
				}
			//}
			
			new Thread(new Runnable()
			{
				public void run() {
					if(CommonTool.checkStr(curMconsumeinfo.getRandomno()))
					{
						double payAmt=0;
						lsDpayinfos=cc011Service.loadDpayinfoByBill(curMconsumeinfo.getId().getCscompid(), curMconsumeinfo.getId().getCsbillid());
						if(lsDpayinfos!=null && lsDpayinfos.size()>0)
						{
							for(Dpayinfo dpayinfo:lsDpayinfos)
							{
								if("4".equals(dpayinfo.getPaymode()))
								{
									payAmt=dpayinfo.getPayamt().doubleValue();
								}
							}
						}
						String openId = cc011Service.loadWechatOpenId(curMconsumeinfo.getCscardno());
						Map<String, String> mapParam=new HashMap<String, String>();
						mapParam.put("templateID", "o1fzQl4hZC0nFh8d4SOXQdclqNFgflsqkRETrJrbekI");
						mapParam.put("openid", openId);
						mapParam.put("first", "尊敬的会员，您好！您卡号 "+ curMconsumeinfo.getCscardno() +" 的订单 "+ curMconsumeinfo.getId().getCsbillid() +" 已消费成功！");
						mapParam.put("keyword1", cc011Service.getDataTool().loadCompNameById(curMconsumeinfo.getId().getCscompid()));
						mapParam.put("keyword2", String.valueOf(payAmt));
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Calendar nowTime = Calendar.getInstance();
						mapParam.put("keyword3", df.format(nowTime.getTime()));
						mapParam.put("remark", "祝您消费愉快！如有疑问请致电4006622818。");
						try {
							strMessage = HttpClientUtil.postMap("http://wechat.chinamani.com/AmaniWechatPlatform/ei/template", mapParam).replaceAll("\"", "");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//更新会员卡信息
						String res = "";  
						try {
							ActivateCard activateCard = cc011Service.loadAccount(curMconsumeinfo.getCscardno());
							String reqUrl=String.format("http://wechat.chinamani.com/AmaniWechatPlatform/updateCardInfo?getToken=true&code=%s&card_id=%s&bonus=%s&balance=%s",
									activateCard.getCode(),activateCard.getCard_id(),activateCard.getInit_bonus(),activateCard.getInit_balance()*100);
							URL url = new URL(reqUrl);  
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
							conn.setDoOutput(true);  
							conn.setRequestMethod("GET");  
							conn.setRequestProperty("Content-Type", "text/json");  
							BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
							String line;  
							while ((line = rd.readLine()) != null) {  
							    res = line;
							}  
							rd.close();
						} catch (Exception e) {
							logger.error("===============^^^^^^###@@@@@@更新会员卡信息：" + e);
						}
						if(org.apache.commons.lang.StringUtils.isNotBlank(res)){
							logger.error("^^^^^^###@@@@@@更新会员卡信息：" + res);
						}
						//wxbandcard=cc011Service.loadWXRandomno(curMconsumeinfo.getRandomno());
						/*Map<String, String> mapParam=new HashMap<String, String>();
						mapParam.put("openid", wxbandcard.getOpenid());
						mapParam.put("cardcode", curMconsumeinfo.getCscardno());
						mapParam.put("uuid", curMconsumeinfo.getRandomno());
						mapParam.put("orderid", curMconsumeinfo.getId().getCsbillid());
						mapParam.put("price", String.valueOf(payAmt));
						mapParam.put("scancode", curMconsumeinfo.getId().getCscompid());
						String strReps="";
						try {
							strReps = HttpClientUtil.postMap("http://amani.chinamani.com/api/scancode", mapParam);
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println(strReps);*/
					}
				}
			}).start();
			
			//curMconsumeinfo=null;
			lsDconsumeinfos=null;
			lsDpayinfos=null;
			lsDnointernalcardinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			strMessage="系统异常";
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "checkWXRandomno",  results = { 
	 @Result(name = "load_success", type = "json"),
	 @Result(name = "load_failure", type = "json")	   })
	public String checkWXRandomno()
	{
		strMessage="";
		try {
			wxbandcard=cc011Service.checkWXRandomno(strRandomno);
			if(wxbandcard==null)
			{
				strMessage="没有找到改编号，或者编号已经过期";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SystemFinal.LOAD_SUCCESS;
	}
	
	/*@Action(value = "sendMconsumeMsg",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })*/
	public String sendMconsumeMsg()
	{
		try
		{
			strMemberPhone="";
			
			strCardNo=curMconsumeinfo.getCscardno();
			strCurCompId=strCompId;
			strCurBillId=curMconsumeinfo.getId().getCsbillid();
			int costtype=1;
			if(!CommonTool.FormatString(strCardNo).equals("散客"))
			{
				costtype=2;
			}
			if(costtype==2)
			{
				strMemberPhone=this.cc011Service.loadMemberPhone(strCardNo);
				//strMemberPhone=this.cc011Service.loadMemberPhone(curMconsumeinfo.getCscardno());
			}
			//会员消费发送短信
	    	if(CommonTool.FormatString(this.cc011Service.getDataTool().loadSysParam(strCompId, "SP109")).equals("1")
	    	&& !CommonTool.FormatString(strMemberPhone).equals("") && CommonTool.FormatString(strMemberPhone).length()==11)
	    	{
	    		companName=this.cc011Service.getDataTool().loadCompNameById(strCompId);
	    		double dCostKeepAmtBalance=0;
		    	double dKeepAmtBalance=0;
		    	double dPointAmtBalance=0;
		    	double dYearAmt=0;
		    	StringBuffer bufProjectAmt=new StringBuffer();
				StringBuffer bufGoodstAmt=new StringBuffer();
				this.lsDconsumeinfos=this.cc011Service.loadDconsumeinfoByBillId(this.strCurCompId, this.strCurBillId,bufProjectAmt,bufGoodstAmt);
				if(lsDconsumeinfos!=null && lsDconsumeinfos.size()>0)
		    	{
		    		for(int i=0;i<lsDconsumeinfos.size();i++)
		    		{
		    			if( CommonTool.FormatString(lsDconsumeinfos.get(i).getCspaymode()).equals("4")
		    			 || CommonTool.FormatString(lsDconsumeinfos.get(i).getCspaymode()).equals("A")
		    			 || CommonTool.FormatString(lsDconsumeinfos.get(i).getCspaymode()).equals("17"))
		    			{
		    				dCostKeepAmtBalance=dCostKeepAmtBalance+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCsitemamt()).doubleValue();
		    			}
		    			else if( CommonTool.FormatString(lsDconsumeinfos.get(i).getCspaymode()).equals("7"))
		    			{
		    				dPointAmtBalance=dPointAmtBalance+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCsitemamt()).doubleValue();
		    			}
		    			else if(CommonTool.FormatString(lsDconsumeinfos.get(i).getCspaymode()).equals("18"))
		    			{
		    				dYearAmt=dYearAmt+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCsitemcount()).intValue();
		    			}
		    		}
		    	}
		    	this.lsPrintCardaccount = this.cc011Service.getDataTool().loadAccountInfoByCard(strCurCompId,strCardNo);
		    	if(lsPrintCardaccount!=null && lsPrintCardaccount.size()>0)
		    	{
		    		for(int i=0;i<lsPrintCardaccount.size();i++)
		    		{
		    			if(CommonTool.FormatInteger(lsPrintCardaccount.get(i).getAccounttype())==2 
		    			|| CommonTool.FormatInteger(lsPrintCardaccount.get(i).getAccounttype())==5
		    			|| CommonTool.FormatInteger(lsPrintCardaccount.get(i).getAccounttype())==6)
		    			{
		    				dKeepAmtBalance=dKeepAmtBalance+CommonTool.FormatBigDecimal(lsPrintCardaccount.get(i).getAccountbalance()).doubleValue();
		    			}
		    			else if(CommonTool.FormatInteger(lsPrintCardaccount.get(i).getAccounttype())==3)
		    			{
		    				dPointAmtBalance=dPointAmtBalance+CommonTool.FormatBigDecimal(lsPrintCardaccount.get(i).getAccountbalance()).doubleValue();
		    			}
		    		}
		    	}
				String strSendMsg="尊敬的顾客,您尾号为"+strCardNo.substring(strCardNo.length()-4,strCardNo.length()) +"的卡于" +
			 		" "+CommonTool.getDateMask(CommonTool.getCurrDate())+" "+CommonTool.getTimeMask(CommonTool.getCurrTime(),2) +
			 		"在"+companName+"消费:"+CommonTool.GetGymAmt(dCostKeepAmtBalance,0)+";";
				
				List<Cardproaccount>  lsSendPros=this.cc011Service.loadProInfosByCardNo(strCurCompId, strCurBillId, strCardNo);
				if(lsSendPros!=null && lsSendPros.size()>0)
				{					
					double totalcostCount=0.0;
					for(int i=0;i<lsSendPros.size();i++)
					{
						
						totalcostCount=totalcostCount+CommonTool.FormatBigDecimal(lsSendPros.get(i).getCostcount()).doubleValue();
					}
					strSendMsg=strSendMsg+"疗程:"+CommonTool.GetGymAmt(totalcostCount,0).intValue()+"次.";
				}
				if(strMemberPhone.equals(curMconsumeinfo.getCardphone()))
				{
					if(dYearAmt>0)
					{
						strSendMsg=strSendMsg+"年卡:"+CommonTool.GetGymAmt(dYearAmt,0)+"次.";
					}
				}
				else 
				{
					if(CommonTool.checkStr(curMconsumeinfo.getCardphone()))
					{
						String strYearMsg="尊敬的顾客,您于"+CommonTool.getDateMask(CommonTool.getCurrDate())+" "+CommonTool.getTimeMask(CommonTool.getCurrTime(),2)+"" +
							"在"+companName+"年卡消费:"+CommonTool.GetGymAmt(dYearAmt,0)+"次.祝您消费愉快!如有疑问请致电4006622818。满意请回复1,不满意请回复2,回复3退订。";
						this.cc011Service.sendMsg(strCurCompId,curMconsumeinfo.getCardphone(), strYearMsg);
					}
				}
				strSendMsg=strSendMsg+"祝您消费愉快!如有疑问请致电4006622818。满意请回复1,不满意请回复2,回复3退订。";
				this.cc011Service.sendMsg(strCurCompId,strMemberPhone, strSendMsg);
				lsSendPros=null;
				lsPrintCardaccount=null;
				bufProjectAmt=null;
				bufGoodstAmt=null;
				lsDconsumeinfos=null;
	    	}
	    	return SystemFinal.POST_SUCCESS;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_SUCCESS; 
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
		return true;
	}
	
	
	@Action(value = "loadMconsumeinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadMconsumeinfo()
	{
		try
		{
			this.curMconsumeinfo=this.cc011Service.loadMconsumeinfoByBill(this.strCurCompId, this.strCurBillId);
			if(curMconsumeinfo!=null)
			{
				this.curMconsumeinfo.setCscardtypeName(this.cc011Service.getDataTool().loadCardTypeName(strCompId, CommonTool.FormatString(curMconsumeinfo.getCscardtype()), new StringBuffer()));
			}
			if(!CommonTool.FormatString(curMconsumeinfo.getRecommendempid()).equals(""))
			{
				StringBuffer nameBuf=new StringBuffer();
				curMconsumeinfo.setRecommendempname(this.cc011Service.getDataTool().loadEmpNameById(strCompId, CommonTool.FormatString(curMconsumeinfo.getRecommendempid()), nameBuf));
				nameBuf=null;
			}
			StringBuffer bufProjectAmt=new StringBuffer();
			StringBuffer bufGoodstAmt=new StringBuffer();
			this.lsDconsumeinfos=this.cc011Service.loadDconsumeinfoByBillId(this.strCurCompId, this.strCurBillId,bufProjectAmt,bufGoodstAmt);
			this.dProjectAmt=new BigDecimal(bufProjectAmt.toString());
			this.dGoodsAmt=new BigDecimal(bufGoodstAmt.toString());
			this.lsDpayinfos=this.cc011Service.loadDpayinfoByBill(this.strCurCompId, this.strCurBillId);
			
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value = "add",  results = { 
			 @Result(name = "add_success", type = "json"),
          @Result(name = "add_failure", type = "json")	
       }) 
	@Override
	public String add()
	{
		try
		{
			this.curMconsumeinfo=this.cc011Service.addMastRecord();
			this.lsDconsumeinfos=new ArrayList();
			this.lsDconsumeinfos.add(this.cc011Service.addDconsumeinfo());
			this.lsDpayinfos=new ArrayList();
			this.lsDpayinfos.add(this.cc011Service.addDpayinfo());
			//lsBillState=cc011Service.loadBillId(CommonTool.getLoginInfo("COMPID"));
			strSalePayMode=this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP067");
			paramSp097=Integer.parseInt(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP097"));
			paramSp098=Integer.parseInt(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP098"));
			paramSp104=Integer.parseInt(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP104"));
			paramSp108=Integer.parseInt(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP108"));
			paramSp112=Integer.parseInt(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP112"));
			paramSp113=Integer.parseInt(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP113"));
			paramSp016=Integer.parseInt(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP016"));
			String strSp105=CommonTool.FormatString(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"), "SP105"));
			if(strSp105.equals("1"))
				paramSp105=1;
			else
				paramSp105=0;
			return SystemFinal.ADD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.ADD_FAILURE;
		}
	}
	
	@Action(value = "loadBillId",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadBillId()
	{
		lsBillState=this.cc011Service.loadBillId(CommonTool.getLoginInfo("COMPID"));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "validateCscardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateCscardno()
	{
		try
		{
			cardStateFlag=0;
			this.strMessage="";
			this.curCardinfo=this.cc011Service.getDataTool().loadCardinfobyCardNo(CommonTool.getLoginInfo("COMPID"), this.strCardNo);
			if(this.curCardinfo==null)
			{
				this.strMessage="该会员卡号在系统中不存在!";
				return SystemFinal.LOAD_FAILURE; 
			}
			else if(this.curCardinfo.getCardstate()!=4 && this.curCardinfo.getCardstate()!=5)
			{
				if(this.curCardinfo.getCardstate()==10)
				{
					cardStateFlag=1;
				}
				this.strMessage="该会员卡为不可消费状态!";
				return SystemFinal.LOAD_FAILURE; 
			}
			//----收购卡是否可以跨店消费 SP045
			if( CommonTool.FormatString(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP045")).equals("0") 
				&& this.curCardinfo.getCardsource() == 1 
				&& this.curCardinfo.getId().getCardvesting().equals(CommonTool.getLoginInfo("COMPID")) == false)
			{
					this.setStrMessage("非内部卡不可以进行跨店消费.");
					return SystemFinal.LOAD_FAILURE;
			}
			dCostProjectRate=CommonTool.FormatBigDecimal(this.cc011Service.loadCostRate(CommonTool.getLoginInfo("COMPID"),this.curCardinfo.getCardtype(), "300",this.cardUseType));
			//--欠款可销
			if(CommonTool.FormatBigDecimal(curCardinfo.getAccount2debtAmt()).doubleValue()>0)
			{
				double debtRate=Double.parseDouble(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP024"));
				curCardinfo.setDCanCostAmt(new BigDecimal(curCardinfo.getAccount2debtAmt().doubleValue()*debtRate));
			}
			this.lsCardproaccount=this.cc011Service.getDataTool().loadProInfosByCardNo(CommonTool.getLoginInfo("COMPID"), this.strCardNo);
			curCardspecialcost=this.cc011Service.loadSpecialPrice(strCardNo);
			curCostAmt=this.cc011Service.loadCurDateCostAmt(strCardNo);
		    curMfCostCount=this.cc011Service.loadCurDateCostCount(strCardNo, "3"); //美发疗程消耗次数
		    curMrCostCount=this.cc011Service.loadCurDateCostCount(strCardNo, "4"); //美容疗程消耗次数
		    lsStrings=this.cc011Service.load38Dis(strCardNo);
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
			if(CommonTool.FormatInteger(this.itemType)==2)//产品
			{
				if(CommonTool.FormatString(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"), "SP016")).equals("1"))
				{
					this.curGoodsinfo=this.cc011Service.getDataTool().loadGoodsInfoBybar(CommonTool.getLoginInfo("COMPID"), this.strCurItemId);
				}
				else
				{
					this.curGoodsinfo=this.cc011Service.getDataTool().loadGoodsInfo(CommonTool.getLoginInfo("COMPID"), this.strCurItemId);
				}
				
				bRet=true;
				if(curGoodsinfo!=null)
				{
					bRet=cc011Service.checkCardPay(curGoodsinfo.getId().getGoodsno());
				}
				
			}
			else
			{
				this.curProjectinfo=this.cc011Service.getDataTool().loadProjectInfo(CommonTool.getLoginInfo("COMPID"), this.strCurItemId);
				if(curProjectinfo!=null)
				{
					
						dstandprice=CommonTool.FormatBigDecimal(curProjectinfo.getSaleprice());
						donecountprice=CommonTool.FormatBigDecimal(curProjectinfo.getOnecountprice());
						donepageprice=CommonTool.FormatBigDecimal(curProjectinfo.getOnepageprice());
						dmemberprice=CommonTool.FormatBigDecimal(curProjectinfo.getMemberprice());
						dcuxiaoprice=this.cc011Service.loadCostPrice(CommonTool.getLoginInfo("COMPID"),this.strCardType, this.strCurItemId);
				}
				else
				{
					return SystemFinal.LOAD_SUCCESS;
				}
			}
			if(CommonTool.FormatString(strCardType).equals(""))
			{
				dCostProjectRate=new BigDecimal(1);
				//产品折扣系数
				if( CommonTool.FormatInteger(this.itemType)==2
				&& !CommonTool.FormatString(strDiYongCardNo).equals("")
				&& this.curGoodsinfo!=null 
				&& this.curGoodsinfo.getId()!=null)
				{
					double goodsrateForQuan=this.cc011Service.loadGoodsCostRate(strDiYongCardNo, this.curGoodsinfo.getGoodspricetype());
					if(CommonTool.FormatDouble(goodsrateForQuan)==0)
					{
						goodsrateForQuan=1;
					}
					this.dCostProjectRate=CommonTool.FormatBigDecimal(new BigDecimal(goodsrateForQuan));
				}
			}
			else
			{
				if(CommonTool.FormatInteger(this.itemType)==2)//产品
				{
					
					this.dCostProjectRate=CommonTool.FormatBigDecimal(this.cc011Service.getDataTool().loadGoodsRateByCardType(CommonTool.getLoginInfo("COMPID"),this.strCardType));
					String strSp094=CommonTool.FormatString(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"), "SP094"));
					if(strSp094.equals("1"))
					{
						this.paramSp094=1;
						this.dCostProjectRate=CommonTool.FormatBigDecimal(this.cc011Service.loadGoodsCostRate(CommonTool.getLoginInfo("COMPID"),this.strCardType, this.curGoodsinfo.getId().getGoodsno(),this.cardUseType));
					}
					else
					{
						this.paramSp094=0;
					}
					
					 if( !CommonTool.FormatString(strDiYongCardNo).equals("")
					  && this.curGoodsinfo!=null 
					  && this.curGoodsinfo.getId()!=null)
					 {
								double goodsrateForQuan=this.cc011Service.loadGoodsCostRate(strDiYongCardNo, this.curGoodsinfo.getGoodspricetype());
								if(CommonTool.FormatDouble(goodsrateForQuan)==0)
								{
									goodsrateForQuan=1;
								}
								if(goodsrateForQuan!=1)
									this.dCostProjectRate=CommonTool.FormatBigDecimal(new BigDecimal(goodsrateForQuan));
					 }
					
				}
				else
				{
					if(CommonTool.FormatInteger(curProjectinfo.getRateflag())==1)
					{
						dCostProjectRate=CommonTool.FormatBigDecimal(this.cc011Service.loadCostRate(CommonTool.getLoginInfo("COMPID"),this.strCardType, this.strCurItemId,this.cardUseType));
						if(cardUseType==1 || cardUseType==2)  //会员卡消费
						{
							double rateToRate=this.cc011Service.loadCardPrjCostRate(CommonTool.getLoginInfo("COMPID"), CommonTool.FormatString(curProjectinfo.getPrjreporttype()));
							if(rateToRate==0)
								rateToRate=1;
							dCostProjectRate=new BigDecimal(dCostProjectRate.doubleValue()*rateToRate);
						}
					}
					else
					{
						dCostProjectRate=new BigDecimal(1);
					}
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
	
	@Action(value = "loadCostRate",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
			 
	public String loadCostRate()
	{
		try
		{
			dCostProjectRate=CommonTool.FormatBigDecimal(this.cc011Service.loadCostRateByPayCode(CommonTool.getLoginInfo("COMPID"),this.strCardType, this.strCurItemId,this.strCurPayMode));
			this.curProjectinfo=this.cc011Service.getDataTool().loadProjectInfo(CommonTool.getLoginInfo("COMPID"), this.strCurItemId);
			if(curProjectinfo!=null)
			{
				dstandprice=CommonTool.FormatBigDecimal(curProjectinfo.getSaleprice());
				donecountprice=CommonTool.FormatBigDecimal(curProjectinfo.getOnecountprice());
				donepageprice=CommonTool.FormatBigDecimal(curProjectinfo.getOnepageprice());
				dmemberprice=CommonTool.FormatBigDecimal(curProjectinfo.getMemberprice());
				dcuxiaoprice=this.cc011Service.loadCostPrice(CommonTool.getLoginInfo("COMPID"),this.strCardType, this.strCurItemId);
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	@Action(value = "validateTuanGouCardNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateTuanGouCardNo()
	{
		try
		{
			this.strMessage="";
			this.curCorpsbuyinfo=this.cc011Service.loadCorpscardno(strTuanGouCardNo);
			if(curCorpsbuyinfo==null || CommonTool.FormatString(curCorpsbuyinfo.getCorpspicno()).equals(""))
			{
				this.strMessage="该团购卡不存在,请确认!";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	
	
	@Action(value = "validateTiaoMaCardNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateTiaoMaCardNo()
	{
		try
		{
			this.strMessage="";
			Nointernalcardinfo record=this.cc011Service.loadNointernalcardinfo(this.strTiaoMaCardNo, 2);
			if(record==null )
			{
				this.strMessage="该条码卡不存在,请确认!";
				return SystemFinal.LOAD_SUCCESS;
			}
			if(!CommonTool.setDateMask(record.getLastvalidate()).equals(""))
			{
				if(Integer.parseInt(CommonTool.setDateMask(record.getLastvalidate()))<Integer.parseInt(CommonTool.getCurrDate()))
				{
					this.strMessage="该条码卡已过期,请确认!";
					return SystemFinal.LOAD_SUCCESS;
				}
			}
			if(CommonTool.FormatInteger(record.getUespassward())==1)
			{
				this.strTMCardpassword=CommonTool.FormatString(record.getCardpassward());
			}
			record=null;
			this.lsDnointernalcardinfo=this.cc011Service.loadDnointernalcardinfo(this.strTiaoMaCardNo);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	@Action(value = "validateDiyongcardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateDiyongcardno()
	{
		try
		{
			activityCardFlag=0;
			this.strMessage="";
			if("050".equals(CommonTool.getLoginInfo("COMPID")) && Integer.parseInt(CommonTool.getCurrDate())<Integer.parseInt("20150630"))
			{
				if("0500001".equals(strDiYongCardNo))
				{
					dDiyongAmt=new BigDecimal(100);
					return SystemFinal.LOAD_SUCCESS;
				}
			}
			Nointernalcardinfo record=this.cc011Service.loadNointernalcardinfo(this.strDiYongCardNo, 1);// 1 抵用券，2条码卡 3活动券(项目+产品) 
			if(record==null )
			{
				record=this.cc011Service.loadNointernalcardinfo(this.strDiYongCardNo, 3);// 1 抵用券，2条码卡 3活动券(项目+产品) 
				if(record==null )
				{
					//发送strDiYongCardNo到微信根据返回值cardId判断是代金券还是项目券
					record=this.cc011Service.loadNointernalcardByWXinfo(this.strDiYongCardNo);//微信券
					if(record==null ){
						this.strMessage="该抵用券不存在或还没有到启用期,请到系统号段查询确认!";
						return SystemFinal.LOAD_SUCCESS;
					}else{
						if("0".equals(record.getUseinproject())){//微信0为代金券  非0为项目券编号 
							this.dDiyongAmt=CommonTool.FormatBigDecimal(record.getCardfaceamt());
							this.wxflag = "true";
						}else{//微信项目券
							List<Dnointernalcardinfo> res = this.cc011Service.loadDnointernalcardByWXinfo(record.getCardId(),record.getCardfaceamt());
							if(res.size() == 0){
								this.strMessage="该抵用券没有配置可用项目!";
								return SystemFinal.LOAD_SUCCESS;
							}else{
								this.lsDnointernalcardinfo=this.cc011Service.loadDnointernalcardByWXinfo(record.getCardId(),record.getCardfaceamt());
								this.wxflag = "true";
							}
					}
						record=null;
					}
				}
				else
				{//当为活动券的代金券或者项目券
					activityCardFlag=1;
					this.lsDnointernalcardinfo=this.cc011Service.loadActivityPrj(this.strDiYongCardNo);
				}
			}
			else
			{//当为抵用券的代金券或者项目券
				if(CommonTool.FormatInteger(record.getUespassward())==1)
				{
					this.strTMCardpassword=CommonTool.FormatString(record.getCardpassward());
				}
				this.dDiyongAmt=CommonTool.FormatBigDecimal(record.getCardfaceamt());
				record=null;
				this.lsDnointernalcardinfo=this.cc011Service.loadDnointernalcardinfo(this.strDiYongCardNo);
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	
	
	
	@Action(value = "searchCurRecord",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String searchCurRecord()
	{
		try
		{
			this.curMconsumeinfo=this.cc011Service.loadMconsumeinfoByBill(CommonTool.getLoginInfo("COMPID"), this.strSearchBillId);
			if(this.curMconsumeinfo!=null && this.curMconsumeinfo.getId()!=null)
			{
				StringBuffer bufProjectAmt=new StringBuffer();
				StringBuffer bufGoodstAmt=new StringBuffer();
				this.lsDconsumeinfos=this.cc011Service.loadDconsumeinfoByBillId(curMconsumeinfo.getId().getCscompid(), this.strSearchBillId,bufProjectAmt,bufGoodstAmt);
				this.dProjectAmt=new BigDecimal(bufProjectAmt.toString());
				this.dGoodsAmt=new BigDecimal(bufGoodstAmt.toString());
				this.lsDpayinfos=this.cc011Service.loadDpayinfoByBill(curMconsumeinfo.getId().getCscompid(), this.strSearchBillId);
				if(!CommonTool.FormatString(curMconsumeinfo.getRecommendempid()).equals(""))
				{
					StringBuffer nameBuf=new StringBuffer();
					curMconsumeinfo.setRecommendempname(this.cc011Service.getDataTool().loadEmpNameById(CommonTool.getLoginInfo("COMPID"), CommonTool.FormatString(curMconsumeinfo.getRecommendempid()), nameBuf));
					nameBuf=null;
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
	
	@SuppressWarnings("rawtypes")
	@Action(value = "viewTicketReport",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String viewTicketReport()
	{
		try
		{
			memberCardId=memberCardId.trim();
			Map values = this.cc011Service.getDataTool().getReportParams( this.shopId, this.ticketId);
		    this.billDate = CommonTool.getDateMask(this.billDate);
		    this.companName = values.get("companName").toString();
		    this.companTel = values.get("companTel").toString();
		    this.companAddr = values.get("companAddr").toString();
		    this.cashMemberName=values.get("cashMemberId").toString();
		    this.printTime = CommonTool.getTimeMask(CommonTool.getCurrTime(), 1);
		    this.printDate = CommonTool.getDateMask(CommonTool.getCurrDate());
		    this.payTypeList = this.cc011Service.getDataTool().loadPayInfoByBillId(this.shopId, this.ticketId,"SY");
		    this.costListProj = this.cc011Service.getDataTool().loadCostInfoByBillId(this.shopId ,this.ticketId,1 );
		    this.costListProd = this.cc011Service.getDataTool().loadCostInfoByBillId(this.shopId ,this.ticketId,2 );
		    this.totalMoney = this.sumTotalMoney();//消费总额
		    if(!CommonTool.FormatString(memberCardId).equals("散客") 
		     && CommonTool.FormatString(memberCardId).length()>2)
		    {
		    	this.lsPrintCardproaccount = this.cc011Service.getDataTool().loadProInfosByCardNo(this.shopId, this.memberCardId);
		    	this.lsPrintCardaccount = this.cc011Service.getDataTool().loadAccountInfoByCard(this.shopId, this.memberCardId);
		    }
		    this.lsYearcarddetals=cc011Service.loadYearcarddetals(shopId, ticketId);
		    values=null;
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	@Action(value = "lsCardtransactionhistories",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadYearcarddetal()
	{
		lsCardtransactionhistories=cc011Service.loadcCardtransactionhistories(strYearInid);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public String sumTotalMoney() {
		double dtotalMoney=0;
		if ((costListProj != null) && (costListProj.size() > 0))
	    {
			for (int i=0;i<costListProj.size();i++) 
			{
				dtotalMoney =dtotalMoney+ CommonTool.FormatBigDecimal(costListProj.get(i).getCostMoney()).doubleValue();
			}
	    }
		if ((costListProd != null) && (costListProd.size() > 0))
	    {
			for (int i=0;i<costListProd.size();i++) 
			{
				dtotalMoney =dtotalMoney+ CommonTool.FormatBigDecimal(costListProd.get(i).getCostMoney()).doubleValue();
			}
	    }
	    return CommonTool.GetGymAmt(dtotalMoney, 2)+"";
	}
	
	public String sumTotalAccountMoney() {
		double dtotalMoney=0;
		if ((costListProj != null) && (costListProj.size() > 0))
	    {
			for (int i=0;i<costListProj.size();i++) 
			{
				dtotalMoney =dtotalMoney+ CommonTool.FormatBigDecimal(costListProj.get(i).getCostMoney()).doubleValue();
			}
	    }
		if ((costListProd != null) && (costListProd.size() > 0))
	    {
			for (int i=0;i<costListProd.size();i++) 
			{
				dtotalMoney =dtotalMoney+ CommonTool.FormatBigDecimal(costListProd.get(i).getCostMoney()).doubleValue();
			}
	    }
	    return CommonTool.GetGymAmt(dtotalMoney, 2)+"";
	}
	
	
	@Action(value = "loadTradeAmt",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadTradeAmt()
	{
		try
		{
			this.curTradeAnalysis=this.cc011Service.loadTradeAnalysisByDate(CommonTool.getLoginInfo("COMPID"), this.strSearchDate);
			
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
			boolean flag=this.cc011Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
			if(flag==false)
			{
				this.strMessage="该经理密码不对,请核实";
				return SystemFinal.LOAD_SUCCESS;
			}
			/*boolean flag=this.cc011Service.validateManagerPass(this.strCurManagerNo, this.strCurManagerPass);
			if(flag==false)
				this.strMessage="经理密码有误,请确认";
			else*/ 
			this.SP027Rate=CommonTool.FormatDouble(Double.parseDouble(this.cc011Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"), "SP027")));
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	@Action(value = "handCardState",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String handCardState()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.cc011Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
			if(flag==false)
			{
				this.strMessage="该经理密码不对,请核实";
				return SystemFinal.LOAD_SUCCESS;
			}
			flag=this.cc011Service.validateMemberInfo(this.strCardNo, this.handMemberName, this.handPhone, this.handPcid);
			if(flag==false)
			{
				this.strMessage="该会员信息与系统核实有误,不能解冻,请确认";
				return SystemFinal.LOAD_SUCCESS;
			}
			this.cc011Service.handCardStateInfo(this.strCardNo);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	
	@Action(value = "validateTMCardPassword",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateTMCardPassword()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.cc011Service.validateTMCardPass(this.strTiaoMaCardNo, this.strTiaoMaCardPassword);
			if(flag==false)
				this.strMessage="条码卡密码有误,请确认";
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	@Action(value = "validateDyqCardPassword",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateDyqCardPassword()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.cc011Service.validateDyqCardPass(this.strTiaoMaCardNo, this.strTiaoMaCardPassword);
			if(flag==false)
				this.strMessage="抵用券密码有误,请确认";
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
		boolean flag=this.cc011Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
		if(flag==false)
		{
			this.strMessage="该经理密码不对,请核实";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadPadBillMaster",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadPadBillMaster()
	{
		try
		{
			this.strMessage="";
			this.lsSpadMconsumeInfo=this.cc011Service.ladSpadMastInfo();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	@Action(value = "checkBillState",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String checkBillState()
	{
		strMessage=cc011Service.checkBillState(strCurCompId, strCurBillId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadPadBillDetial",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadPadBillDetial()
	{
		try
		{
			this.strMessage="";
			this.lsSpadDconsumeInfo=this.cc011Service.ladSpadDetialtInfo(this.strPadBillId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	
	@Action(value = "handBackReviceCard",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String handBackReviceCard()
	{
		try
		{
			this.strMessage="";
			boolean falg=this.cc011Service.handBackReviceCard(this.strCardNo,strCurBillId);
			if(falg==false)
			{
				this.strMessage="操作失败!";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	
	@Action(value = "updYearCardInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String updYearCardInfo()
	{
		this.strMessage="";
		if(cc011Service.checkIsStop(strYearInid)==false)
		{
			strMessage="这个疗程已经停用过了，不能在停用了";
			return SystemFinal.LOAD_FAILURE;
		}
		if(cc011Service.updYearCardInfo(strYearInid)==false)
		{
			return SystemFinal.LOAD_FAILURE;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "updYearCardJH",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String updYearCardJH()
	{
		this.strMessage="";
		if(cc011Service.checkIsJH(strYearInid)==false)
		{
			strMessage="不能激活，因为现在已经是激活状态";
			return SystemFinal.LOAD_FAILURE;
		}
		if(cc011Service.updYearCardJH(strYearInid)==false)
		{
			return SystemFinal.LOAD_FAILURE;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "delayYearCard",  results = { 
			@Result(name = "load_success", type = "json"),
			@Result(name = "load_failure", type = "json")	   })
	public String delayYearCard()
	{
		this.strMessage="";
		if(cc011Service.checkDelay(strYearInid)){
			strMessage="只有半年卡和年卡并且未停用的过期年卡才能延期！";
			return SystemFinal.LOAD_FAILURE;
		}
		if(cc011Service.checkIsDelay(strYearInid)){
			strMessage="该年卡已经延期，不能再延期！";
			return SystemFinal.LOAD_FAILURE;
		}
		if(cc011Service.updYearCardDelay(strYearInid)==false)
		{
			return SystemFinal.LOAD_FAILURE;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="changeVoid", results={@Result(name="operation_success", type="json")}) 
	public String changeVoid() {
		boolean bool=cc011Service.updateBillState(strCurCompId, strCurBillId);
		strMessage=bool ? SystemFinal.OPERATION_SUCCESS_MSG :"";
		return SystemFinal.OPERATION_SUCCESS;
	}
	
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}
	
	@Action(value="loadGroup", results={@Result(name="load_success", type="json"),
			 @Result(name="load_failure", type="json")}) 
	public String loadGroup(){
		this.lsMpackageinfo=this.cc011Service.loadGroup();
		if(lsMpackageinfo == null || lsMpackageinfo.size()==0){
			this.setStrMessage("没有查询到团购套餐信息！");
		}else{
			for (Mpackageinfo mpackageinfo : lsMpackageinfo) {
				MpackageinfoId id = mpackageinfo.getId();
				mpackageinfo.setBcompid(id.getCompid());
				mpackageinfo.setBpackageno(id.getPackageno());
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="loadGroupPro", results={@Result(name="load_success", type="json"),
			 @Result(name="load_failure", type="json")}) 
	public String loadGroupPro(){
		this.lsDmpackageinfo=this.cc011Service.getDataTool().loadDmpackageinfo(strCurCompId,strCardNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	private int scanpaytype;//扫码支付类型1.支付宝 2.微信
	private String auth_code;//扫码支付条码
	private String outTradeNo;//扫码订单号
	private BigDecimal totalBank;//扫码支付金额
	//提交微信支付订单
	@Action(value="wechatPay", results={@Result(name="operation_success", type="json"),
			 @Result(name="operation_failure", type="json")}) 
	public String wechatPay(){
		try {
			Map<String, String> result = wechatPayService.requestWebChatPaypalBill(strCurBillId, auth_code, totalBank, 
						CommonTool.getLoginInfo("COMPID"), CommonTool.getLoginInfo("COMPNAME"));
			strMessage=result.get("state");
			outTradeNo=result.get("outTradeNo");
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = "ERROR";
		}
		return SystemFinal.OPERATION_SUCCESS;
	}
	//查询微信单据状态
	@Action(value="wechatState", results={@Result(name="operation_success", type="json"),
			 @Result(name="operation_failure", type="json")}) 
	public String wechatState(){
		try {
			strMessage = wechatPayService.queryWebChatBillStatusReturnString(outTradeNo);
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = "ERROR";
		}
		return SystemFinal.OPERATION_SUCCESS;
	}
	//撤销微信支付订单
	@Action(value="wechatReverse", results={@Result(name="operation_success", type="json"),
			 @Result(name="operation_failure", type="json")}) 
	public String wechatReverse(){
		try {
			if(StringUtils.isNotBlank(outTradeNo)){
				strMessage = wechatPayService.webChatReverse(CommonTool.getLoginInfo("COMPID"), strCurBillId, outTradeNo, totalBank);
			}else{
				strMessage ="获取微信单据号失败，请重试！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = "ERROR";
		}
		return SystemFinal.OPERATION_SUCCESS;
	}
	//提交支付宝支付订单
	@Action(value="aliPay", results={@Result(name="operation_success", type="json"),
			 @Result(name="operation_failure", type="json")}) 
	public String aliPay(){
		try {
			Map<String, String> result = aliPayService.requestPaypal(strCurBillId, auth_code, totalBank, 
					CommonTool.getLoginInfo("COMPID"), CommonTool.getLoginInfo("COMPNAME"));
			strMessage=result.get("state");
			outTradeNo=result.get("outTradeNo");
		} catch (Exception e) {
			logger.error("提交支付宝支付订单   aliPay:" + e);
			strMessage = "ERROR";
		}
		return SystemFinal.OPERATION_SUCCESS;
	}
	//查询支付宝支付订单状态
	@Action(value="aliState", results={@Result(name="operation_success", type="json"),
			 @Result(name="operation_failure", type="json")}) 
	public String aliState(){
		try {
			strMessage = aliPayService.queryPaypalBillStatusReturnString(outTradeNo);
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = "ERROR";
		}
		return SystemFinal.OPERATION_SUCCESS;
	}
	//撤销支付宝支付订单
	@Action(value="aliReverse", results={@Result(name="operation_success", type="json"),
			 @Result(name="operation_failure", type="json")}) 
	public String aliReverse(){
		try {
			if(StringUtils.isNotBlank(outTradeNo)){
				strMessage = aliPayService.canclePaypalBillStatus(CommonTool.getLoginInfo("COMPID"), strCurBillId, outTradeNo, totalBank);
			}else{
				strMessage ="获取支付宝单据号失败，请重试！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = "ERROR";
		}
		return SystemFinal.OPERATION_SUCCESS;
	}
	//接发活动查询美发师
	@Action(value="loadHairStaff", results={@Result(name="load_success", type="json"),
			 @Result(name="load_failure", type="json")}) 
	public String loadHairStaff(){
		this.lsYearCard =this.cc011Service.loadHairStaff(strCompId);
		return SystemFinal.LOAD_SUCCESS;
	}
	// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
	public Object deepCopy(Object obj) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		// 将流序列化成对象
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}
	//接发活动读取门店
	private List<Companyinfo> compSet;
	@Action( value="loadCompanySet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadCompanySet() {
		this.compSet=this.cc011Service.loadCompanyData();
		return SystemFinal.LOAD_SUCCESS;
	}
	//推送微信密码
	@Action( value="sendWechatPwd", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String sendWechatPwd() {
		String openId = this.cc011Service.loadWechatOpenId(strCardNo);
		if(StringUtils.isBlank(openId)){
			strMessage ="该卡号未绑定微信，请确认！";
			return SystemFinal.LOAD_SUCCESS;
		}
		Map<String, String> mapParam=new HashMap<String, String>();
		mapParam.put("templateID", "UI_t3XRO6KtDVUl5mYsR80a2P1ctkF0OI_AMP3-XwXQ");
		mapParam.put("openid", openId);
		String ramdom = String.valueOf(((int)((Math.random()*9+1)*100000)));
		mapParam.put("first", "尊敬的会员，您好！您本次的消费验证码为：");
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
		strMessage = StringUtils.equals(_ramdomCode, strRandomno)?"":"验证码不正确，请重新输入！";
		return SystemFinal.LOAD_SUCCESS;
	}
	public int getScanpaytype() {
		return scanpaytype;
	}
	public void setScanpaytype(int scanpaytype) {
		this.scanpaytype = scanpaytype;
	}

	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	public BigDecimal getTotalBank() {
		return totalBank;
	}
	public void setTotalBank(BigDecimal totalBank) {
		this.totalBank = totalBank;
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
	public CC011Service getCc011Service() {
		return cc011Service;
	}
	@JSON(serialize=false)
	public void setCc011Service(CC011Service cc011Service) {
		this.cc011Service = cc011Service;
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
	public String getStrCurItemId() {
		return strCurItemId;
	}
	public void setStrCurItemId(String strCurItemId) {
		this.strCurItemId = strCurItemId;
	}
	public String getStrCurItemName() {
		return strCurItemName;
	}
	public void setStrCurItemName(String strCurItemName) {
		this.strCurItemName = strCurItemName;
	}
	public List<Mconsumeinfo> getLsMconsumeinfos() {
		return lsMconsumeinfos;
	}
	public void setLsMconsumeinfos(List<Mconsumeinfo> lsMconsumeinfos) {
		this.lsMconsumeinfos = lsMconsumeinfos;
	}
	public List<Dconsumeinfo> getLsDconsumeinfos() {
		return lsDconsumeinfos;
	}
	public void setLsDconsumeinfos(List<Dconsumeinfo> lsDconsumeinfos) {
		this.lsDconsumeinfos = lsDconsumeinfos;
	}
	public List<Dpayinfo> getLsDpayinfos() {
		return lsDpayinfos;
	}
	public void setLsDpayinfos(List<Dpayinfo> lsDpayinfos) {
		this.lsDpayinfos = lsDpayinfos;
	}
	public Mconsumeinfo getCurMconsumeinfo() {
		return curMconsumeinfo;
	}
	public void setCurMconsumeinfo(Mconsumeinfo curMconsumeinfo) {
		this.curMconsumeinfo = curMconsumeinfo;
	}
	public BigDecimal getDProjectAmt() {
		return dProjectAmt;
	}
	public void setDProjectAmt(BigDecimal projectAmt) {
		dProjectAmt = projectAmt;
	}
	public BigDecimal getDGoodsAmt() {
		return dGoodsAmt;
	}
	public void setDGoodsAmt(BigDecimal goodsAmt) {
		dGoodsAmt = goodsAmt;
	}
	public String getStrCardNo() {
		return strCardNo;
	}
	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}
	public Cardinfo getCurCardinfo() {
		return curCardinfo;
	}
	public void setCurCardinfo(Cardinfo curCardinfo) {
		this.curCardinfo = curCardinfo;
	}
	public List<Cardproaccount> getLsCardproaccount() {
		return lsCardproaccount;
	}
	public void setLsCardproaccount(List<Cardproaccount> lsCardproaccount) {
		this.lsCardproaccount = lsCardproaccount;
	}
	public Goodsinfo getCurGoodsinfo() {
		return curGoodsinfo;
	}
	public void setCurGoodsinfo(Goodsinfo curGoodsinfo) {
		this.curGoodsinfo = curGoodsinfo;
	}
	public Projectinfo getCurProjectinfo() {
		return curProjectinfo;
	}
	public void setCurProjectinfo(Projectinfo curProjectinfo) {
		this.curProjectinfo = curProjectinfo;
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
	public Corpsbuyinfo getCurCorpsbuyinfo() {
		return curCorpsbuyinfo;
	}
	public void setCurCorpsbuyinfo(Corpsbuyinfo curCorpsbuyinfo) {
		this.curCorpsbuyinfo = curCorpsbuyinfo;
	}
	public String getStrTuanGouCardNo() {
		return strTuanGouCardNo;
	}
	public void setStrTuanGouCardNo(String strTuanGouCardNo) {
		this.strTuanGouCardNo = strTuanGouCardNo;
	}
	public String getStrSalePayMode() {
		return strSalePayMode;
	}
	public void setStrSalePayMode(String strSalePayMode) {
		this.strSalePayMode = strSalePayMode;
	}
	public String getStrTiaoMaCardNo() {
		return strTiaoMaCardNo;
	}
	public void setStrTiaoMaCardNo(String strTiaoMaCardNo) {
		this.strTiaoMaCardNo = strTiaoMaCardNo;
	}
	public String getStrDiYongCardNo() {
		return strDiYongCardNo;
	}
	public void setStrDiYongCardNo(String strDiYongCardNo) {
		this.strDiYongCardNo = strDiYongCardNo;
	}
	public BigDecimal getDDiyongAmt() {
		return dDiyongAmt;
	}
	public void setDDiyongAmt(BigDecimal diyongAmt) {
		dDiyongAmt = diyongAmt;
	}
	public List<Dnointernalcardinfo> getLsDnointernalcardinfo() {
		return lsDnointernalcardinfo;
	}
	public void setLsDnointernalcardinfo(
			List<Dnointernalcardinfo> lsDnointernalcardinfo) {
		this.lsDnointernalcardinfo = lsDnointernalcardinfo;
	}
	public TradeAnalysis getCurTradeAnalysis() {
		return curTradeAnalysis;
	}
	public void setCurTradeAnalysis(TradeAnalysis curTradeAnalysis) {
		this.curTradeAnalysis = curTradeAnalysis;
	}
	public BigDecimal getDCostProjectRate() {
		return dCostProjectRate;
	}
	public void setDCostProjectRate(BigDecimal costProjectRate) {
		dCostProjectRate = costProjectRate;
	}
	public String getStrCardType() {
		return strCardType;
	}
	public void setStrCardType(String strCardType) {
		this.strCardType = strCardType;
	}
	public String getMemberCardId() {
		return memberCardId;
	}
	public void setMemberCardId(String memberCardId) {
		this.memberCardId = memberCardId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
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

	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public List<Cardaccount> getLsPrintCardaccount() {
		return lsPrintCardaccount;
	}
	public void setLsPrintCardaccount(List<Cardaccount> lsPrintCardaccount) {
		this.lsPrintCardaccount = lsPrintCardaccount;
	}
	public List<Cardproaccount> getLsPrintCardproaccount() {
		return lsPrintCardproaccount;
	}
	public void setLsPrintCardproaccount(List<Cardproaccount> lsPrintCardproaccount) {
		this.lsPrintCardproaccount = lsPrintCardproaccount;
	}
	public List<PringBillBean> getCostListProj() {
		return costListProj;
	}
	public void setCostListProj(List<PringBillBean> costListProj) {
		this.costListProj = costListProj;
	}
	public List<PringBillBean> getCostListProd() {
		return costListProd;
	}
	public void setCostListProd(List<PringBillBean> costListProd) {
		this.costListProd = costListProd;
	}
	public BigDecimal getDstandprice() {
		return dstandprice;
	}
	public void setDstandprice(BigDecimal dstandprice) {
		this.dstandprice = dstandprice;
	}
	public BigDecimal getDonecountprice() {
		return donecountprice;
	}
	public void setDonecountprice(BigDecimal donecountprice) {
		this.donecountprice = donecountprice;
	}
	public BigDecimal getDonepageprice() {
		return donepageprice;
	}
	public void setDonepageprice(BigDecimal donepageprice) {
		this.donepageprice = donepageprice;
	}
	public BigDecimal getDmemberprice() {
		return dmemberprice;
	}
	public void setDmemberprice(BigDecimal dmemberprice) {
		this.dmemberprice = dmemberprice;
	}
	public BigDecimal getDcuxiaoprice() {
		return dcuxiaoprice;
	}
	public void setDcuxiaoprice(BigDecimal dcuxiaoprice) {
		this.dcuxiaoprice = dcuxiaoprice;
	}
	public int getCardUseType() {
		return cardUseType;
	}
	public void setCardUseType(int cardUseType) {
		this.cardUseType = cardUseType;
	}
	public String getStrCurPayMode() {
		return strCurPayMode;
	}
	public void setStrCurPayMode(String strCurPayMode) {
		this.strCurPayMode = strCurPayMode;
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
	public double getSP027Rate() {
		return SP027Rate;
	}
	public void setSP027Rate(double rate) {
		SP027Rate = rate;
	}
	public Cardspecialcost getCurCardspecialcost() {
		return curCardspecialcost;
	}
	public void setCurCardspecialcost(Cardspecialcost curCardspecialcost) {
		this.curCardspecialcost = curCardspecialcost;
	}
	public int getParamSp094() {
		return paramSp094;
	}
	public void setParamSp094(int paramSp094) {
		this.paramSp094 = paramSp094;
	}
	public BigDecimal getDCostGoodsRate() {
		return dCostGoodsRate;
	}
	public void setDCostGoodsRate(BigDecimal costGoodsRate) {
		dCostGoodsRate = costGoodsRate;
	}
	public String getStrTMCardpassword() {
		return strTMCardpassword;
	}
	public void setStrTMCardpassword(String strTMCardpassword) {
		this.strTMCardpassword = strTMCardpassword;
	}
	public String getStrTiaoMaCardPassword() {
		return strTiaoMaCardPassword;
	}
	public void setStrTiaoMaCardPassword(String strTiaoMaCardPassword) {
		this.strTiaoMaCardPassword = strTiaoMaCardPassword;
	}
	public List<SpadMconsumeInfo> getLsSpadMconsumeInfo() {
		return lsSpadMconsumeInfo;
	}
	public void setLsSpadMconsumeInfo(List<SpadMconsumeInfo> lsSpadMconsumeInfo) {
		this.lsSpadMconsumeInfo = lsSpadMconsumeInfo;
	}
	public List<SpadDconsumeInfo> getLsSpadDconsumeInfo() {
		return lsSpadDconsumeInfo;
	}
	public void setLsSpadDconsumeInfo(List<SpadDconsumeInfo> lsSpadDconsumeInfo) {
		this.lsSpadDconsumeInfo = lsSpadDconsumeInfo;
	}
	public double getSumCostAmt() {
		return sumCostAmt;
	}
	public void setSumCostAmt(double sumCostAmt) {
		this.sumCostAmt = sumCostAmt;
	}
	public int getParamSp097() {
		return paramSp097;
	}
	public void setParamSp097(int paramSp097) {
		this.paramSp097 = paramSp097;
	}
	public int getParamSp098() {
		return paramSp098;
	}
	public void setParamSp098(int paramSp098) {
		this.paramSp098 = paramSp098;
	}
	public double getCurCostAmt() {
		return curCostAmt;
	}
	public void setCurCostAmt(double curCostAmt) {
		this.curCostAmt = curCostAmt;
	}
	public int getParamSp104() {
		return paramSp104;
	}
	public void setParamSp104(int paramSp104) {
		this.paramSp104 = paramSp104;
	}

	public double getCurMfCostCount() {
		return curMfCostCount;
	}

	public void setCurMfCostCount(double curMfCostCount) {
		this.curMfCostCount = curMfCostCount;
	}

	public double getCurMrCostCount() {
		return curMrCostCount;
	}

	public void setCurMrCostCount(double curMrCostCount) {
		this.curMrCostCount = curMrCostCount;
	}

	public int getParamSp105() {
		return paramSp105;
	}

	public void setParamSp105(int paramSp105) {
		this.paramSp105 = paramSp105;
	}

	public int getCardStateFlag() {
		return cardStateFlag;
	}

	public void setCardStateFlag(int cardStateFlag) {
		this.cardStateFlag = cardStateFlag;
	}

	public String getHandMemberName() {
		return handMemberName;
	}

	public void setHandMemberName(String handMemberName) {
		this.handMemberName = handMemberName;
	}

	public String getHandPhone() {
		return handPhone;
	}

	public void setHandPhone(String handPhone) {
		this.handPhone = handPhone;
	}

	public String getHandPcid() {
		return handPcid;
	}

	public void setHandPcid(String handPcid) {
		this.handPcid = handPcid;
	}

	public int getParamSp108() {
		return paramSp108;
	}

	public void setParamSp108(int paramSp108) {
		this.paramSp108 = paramSp108;
	}

	public int getActivityCardFlag() {
		return activityCardFlag;
	}

	public void setActivityCardFlag(int activityCardFlag) {
		this.activityCardFlag = activityCardFlag;
	}

	public String getStrMemberPhone() {
		return strMemberPhone;
	}

	public void setStrMemberPhone(String strMemberPhone) {
		this.strMemberPhone = strMemberPhone;
	}

	public String getStrCompId() {
		return strCompId;
	}

	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}

	public List<Dmedicalcare> getMedicalSet() {
		return medicalSet;
	}

	public void setMedicalSet(List<Dmedicalcare> medicalSet) {
		this.medicalSet = medicalSet;
	}

	public List<Mpackageinfo> getLsMpackageinfo() {
		return lsMpackageinfo;
	}
	public void setLsMpackageinfo(List<Mpackageinfo> lsMpackageinfo) {
		this.lsMpackageinfo = lsMpackageinfo;
	}
	
	public List<Dmpackageinfo> getLsDmpackageinfo() {
		return lsDmpackageinfo;
	}
	public void setLsDmpackageinfo(List<Dmpackageinfo> lsDmpackageinfo) {
		this.lsDmpackageinfo = lsDmpackageinfo;
	}
	
	public List<Companyinfo> getCompSet() {
		return compSet;
	}
	public void setCompSet(List<Companyinfo> compSet) {
		this.compSet = compSet;
	}

	public boolean isCheckSendIntegral() {
		return checkSendIntegral;
	}

	public void setCheckSendIntegral(boolean checkSendIntegral) {
		this.checkSendIntegral = checkSendIntegral;
	}

	/**
	 * 增加赠送生日积分
	 * @return
	 */
	@Action(value = "checkSendIntegral",  results = { 
	@Result(name = "load_success", type = "json"),
	@Result(name = "load_failure", type = "json")	   })
	public String checkSendIntegral()
	{
		this.checkSendIntegral=cc011Service.checkSendIntegral(strCardNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "SendIntegral",  results = { 
			@Result(name = "load_success", type = "json"),
			@Result(name = "load_failure", type = "json")	   })
	public String SendIntegral()
	{
		this.checkSendIntegral=cc011Service.SendIntegral(strCardNo);
		return SystemFinal.LOAD_SUCCESS;
	}

	public String getWxflag() {
		return wxflag;
	}

	public void setWxflag(String wxflag) {
		this.wxflag = wxflag;
	}
}
