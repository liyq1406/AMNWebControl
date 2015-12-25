package com.amani.action.AdvancedOperations;

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


import com.amani.model.Cardtypeinfo;
import com.amani.model.Dnointernalcardinfo;
import com.amani.model.DsalebarcodecardinfoId;
import com.amani.model.Nointernalcardinfo;
import com.amani.model.Projectinfo;
import com.amani.model.Purchaseregister;
import com.amani.service.AdvancedOperations.AC006Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac006")
public class AC006Action extends AMN_ModuleAction{
	@Autowired
	private AC006Service ac006Service;
	private String strCurCompId;
	private String strFromInerCardNo;
	private String strToInerCardNo;
	private String strFromOuterCardNo;
	private String strInerCardType;
	
	private String strFromTmCardNo;
	private String strToTmCardNo;
	private String strTmCardBef;
	private String strToOuterCardNo;
	
	//团购卡
	private String corpscardno;
	private int corpstype;
	private String corpssource;
	private String corpspicno;
	private double corpsamt;
	private String corpspicname;
	
	//抵用券
	private String strJsonParam;
	private String strDyqCardBef;
	private String strFromDyqCardNo;
	private String strToDyqCardNo;
	private int dyqType;
	private double dyqCardAmt;
	private String validate;
	private String enabledate;
	private int createtype;
	private List<Dnointernalcardinfo> lsDnointernalcardinfo;
	private List<Projectinfo> lsProjectinfo;
	//收购卡登记
	private		String 		cardvesting;			//归属门店
	private 	String 		registercardno;			//登记门店
	private 	String 		registercardtype;		//登记卡类型
	private 	String 		membername;				//会员姓名
	private 	String 		memberphone;			//会员电话
	private 	String 		memberbrithday;			//会员生日
	private 	int	   		membersex;				//会员性别
	private 	double 		cardbalance;			//卡余额
	private 	String 		registerpcid;			//登记身份证号
	
	private List<Cardtypeinfo> lsCardtypeinfos;
	@JSON(serialize=false)
	public AC006Service getAc006Service() {
		return ac006Service;
	}
	@JSON(serialize=false)
	public void setAc006Service(AC006Service ac006Service) {
		this.ac006Service = ac006Service;
	}
	public String getStrFromInerCardNo() {
		return strFromInerCardNo;
	}
	public void setStrFromInerCardNo(String strFromInerCardNo) {
		this.strFromInerCardNo = strFromInerCardNo;
	}
	public String getStrToInerCardNo() {
		return strToInerCardNo;
	}
	public void setStrToInerCardNo(String strToInerCardNo) {
		this.strToInerCardNo = strToInerCardNo;
	}
	public String getStrFromOuterCardNo() {
		return strFromOuterCardNo;
	}
	public void setStrFromOuterCardNo(String strFromOuterCardNo) {
		this.strFromOuterCardNo = strFromOuterCardNo;
	}
	public String getStrToOuterCardNo() {
		return strToOuterCardNo;
	}
	public void setStrToOuterCardNo(String strToOuterCardNo) {
		this.strToOuterCardNo = strToOuterCardNo;
	}
	
	@Action(value = "loadInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadInfo()
	{
		try{
			this.lsCardtypeinfos=this.ac006Service.getDataTool().loadCardType(strCurCompId);
			this.lsProjectinfo=this.ac006Service.getDataTool().loadProjectinfoByCompId(strCurCompId,1);
			List<Projectinfo> temp = new ArrayList<Projectinfo>();
			for (Projectinfo project : lsProjectinfo) {//过滤不是疗程的项目
				if(CommonTool.FormatInteger(project.getPrjsaletype())==2){
					temp.add(project);
				}
			}
			lsProjectinfo = temp;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "handInerCard",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String handInerCard()
	{
		try
		{
			this.strMessage="";
			if(this.ac006Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC006", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(CommonTool.FormatString(this.strFromInerCardNo).equals("")
			 || CommonTool.FormatString(this.strToInerCardNo).equals("")
			 || CommonTool.FormatString(this.strInerCardType).equals(""))
			 {
				this.strMessage="录入信息不完整,请确认!";
				return SystemFinal.POST_FAILURE;
			 }
			boolean checkFlag=this.ac006Service.validateCardRange(strFromInerCardNo, strToInerCardNo,strInerCardType);
			if(checkFlag==false)
			{
				this.strMessage="录入卡号段已经在系统中存在,请确认!";
				return SystemFinal.POST_FAILURE;
			}
			checkFlag=this.ac006Service.postInerCardindfos(strCurCompId,strFromInerCardNo, strToInerCardNo,strInerCardType);
			if(checkFlag==false)
			{
				this.strMessage="操作失败,请确认!";
				return SystemFinal.POST_FAILURE;
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	@Action(value = "handPurchasereCardInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String handPurchasereCardInfo()
	{
		try
		{
			this.strMessage="";
			if(this.ac006Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC006", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(CommonTool.FormatString(this.cardvesting).equals("")
			 || CommonTool.FormatString(this.registercardno).equals("")
			 || CommonTool.FormatDouble(this.cardbalance)==0)
			 {
				this.strMessage="录入信息不完整,请确认!";
				return SystemFinal.POST_FAILURE;
			 }
			boolean checkFlag=this.ac006Service.validateCardRange(registercardno, registercardno,"");
			if(checkFlag==false)
			{
				this.strMessage="录入卡号段已经在系统中存在,请确认!";
				return SystemFinal.POST_FAILURE;
			}

			checkFlag=this.ac006Service.postPurchasereCardInfo(cardvesting,registercardno, registercardtype,membername,memberphone,memberbrithday,membersex,cardbalance,registerpcid);
			if(checkFlag==false)
			{
				this.strMessage="操作失败,请确认!";
				return SystemFinal.POST_FAILURE;
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	@Action(value = "handTmCard",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String handTmCard()
	{
		try
		{
			this.strMessage="";
			if(this.ac006Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC006", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(CommonTool.FormatString(this.strFromTmCardNo).equals("")
			 || CommonTool.FormatString(this.strToTmCardNo).equals("")
			 || CommonTool.FormatString(this.strTmCardBef).equals(""))
			 {
				this.strMessage="录入信息不完整,请确认!";
				return SystemFinal.POST_FAILURE;
			 }
			boolean checkFlag=this.ac006Service.validateTmCardRange(strTmCardBef+strFromTmCardNo, strTmCardBef+strToTmCardNo,2);
			if(checkFlag==false)
			{
				this.strMessage="录入条码卡号段已经在系统中存在,请确认!";
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.ac006Service.postRecordCard(this.strCurCompId, this.strFromTmCardNo, this.strToTmCardNo, 2, 1,strTmCardBef);
			if(flag==false)
			{
				this.strMessage="操作失败,请确认!";
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "handTmOuterChange",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String handTmOuterChange()
	{
		try
		{
			this.strMessage="";
			if(this.ac006Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC006", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(CommonTool.FormatString(this.strFromTmCardNo).equals("")
			 || CommonTool.FormatString(this.strToTmCardNo).equals("")
			 || CommonTool.FormatString(this.strTmCardBef).equals(""))
			 {
				this.strMessage="录入信息不完整,请确认!";
				return SystemFinal.POST_FAILURE;
			 }
			boolean checkFlag=this.ac006Service.validateUseTmCardRange(strTmCardBef+strFromTmCardNo, strTmCardBef+strToTmCardNo,2);
			if(checkFlag==false)
			{
				this.strMessage="录入条码卡号段存在正常使用的卡,请确认!";
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.ac006Service.updateRecordCard(this.strCurCompId, strTmCardBef+strFromTmCardNo, strTmCardBef+strToTmCardNo,2);
			if(flag==false)
			{
				this.strMessage="操作失败,请确认!";
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	@Action(value = "handInerChangeVesting",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String handInerChangeVesting()
	{
		try
		{
			this.strMessage="";
			if(this.ac006Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC006", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(CommonTool.FormatString(this.strFromInerCardNo).equals("")
			 || CommonTool.FormatString(this.strToInerCardNo).equals(""))
			 {
				this.strMessage="录入信息不完整,请确认!";
				return SystemFinal.POST_FAILURE;
			 }
			boolean checkFlag=this.ac006Service.validateCardRange(strFromInerCardNo, strToInerCardNo,"");
			if(checkFlag==true)
			{
				this.strMessage="输入的卡号段不存在,请确认!";
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.ac006Service.updateRecordVesting(this.strCurCompId, strFromInerCardNo, strToInerCardNo);
			if(flag==false)
			{
				this.strMessage="操作失败,请确认!";
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "validateCorpspicno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateCorpspicno()
	{
		this.strMessage="";
		StringBuffer bufcorpspicno=new StringBuffer();
		boolean valigFlag=this.ac006Service.loadCorpsPicname(this.strCurCompId,this.corpstype,this.corpspicno,bufcorpspicno);
		if(valigFlag==false)
		{
			if(corpstype==1)
				strMessage="该项目编号不存在";
			else
				strMessage="该卡类型不存在";
		}
		else
		{
			this.corpspicname=bufcorpspicno.toString();
		}
		return SystemFinal.LOAD_SUCCESS;
	}

	@Action(value = "handTgCard",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String handTgCard()
	{
		try
		{
			this.strMessage="";
			if(this.ac006Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC006", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(CommonTool.FormatString(this.corpscardno).equals("")
			 || CommonTool.FormatString(this.corpspicno).equals(""))
			 {
				this.strMessage="录入信息不完整,请确认!";
				return SystemFinal.POST_FAILURE;
			 }
			boolean checkFlag=this.ac006Service.validateTgCardRange(corpscardno);
			if(checkFlag==false)
			{
				this.strMessage="录入团购卡号段已经在系统中存在,请确认!";
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.ac006Service.postTgCard(corpscardno, corpstype,corpssource, corpspicno, corpsamt);
			if(flag==false)
			{
				this.strMessage="操作失败,请确认!";
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	@Action(value = "handDyqCard",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String handDyqCard()
	{
		try
		{
			this.strMessage="";
			if(this.ac006Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC006", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.lsDnointernalcardinfo=this.ac006Service.getDataTool().loadDTOList(strJsonParam, Dnointernalcardinfo.class);
			if(lsDnointernalcardinfo!=null && lsDnointernalcardinfo.size()>0)
			{
				for(int i=0;i<lsDnointernalcardinfo.size();i++)
				{
					if(CommonTool.FormatString(lsDnointernalcardinfo.get(i).getIneritemno()).equals(""))
					{
						lsDnointernalcardinfo.remove(i);
						i--;
					}
				}
			}
			boolean checkFlag=this.ac006Service.validateTmCardRange(strDyqCardBef+strFromDyqCardNo, strDyqCardBef+strToDyqCardNo,1);
			if(checkFlag==false)
			{
				this.strMessage="录入抵用券号段已经在系统中存在,请确认!";
				return SystemFinal.POST_FAILURE;
			}
			checkFlag=this.ac006Service.saveRecordCard_DY(strCurCompId, strFromDyqCardNo, strToDyqCardNo, 1, this.dyqType, this.dyqCardAmt, lsDnointernalcardinfo, this.strDyqCardBef, this.validate,this.enabledate,createtype);
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	

	
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
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
	public String getStrInerCardType() {
		return strInerCardType;
	}
	public void setStrInerCardType(String strInerCardType) {
		this.strInerCardType = strInerCardType;
	}
	public List<Cardtypeinfo> getLsCardtypeinfos() {
		return lsCardtypeinfos;
	}
	public void setLsCardtypeinfos(List<Cardtypeinfo> lsCardtypeinfos) {
		this.lsCardtypeinfos = lsCardtypeinfos;
	}
	public String getStrFromTmCardNo() {
		return strFromTmCardNo;
	}
	public void setStrFromTmCardNo(String strFromTmCardNo) {
		this.strFromTmCardNo = strFromTmCardNo;
	}
	public String getStrToTmCardNo() {
		return strToTmCardNo;
	}
	public void setStrToTmCardNo(String strToTmCardNo) {
		this.strToTmCardNo = strToTmCardNo;
	}
	public String getStrTmCardBef() {
		return strTmCardBef;
	}
	public void setStrTmCardBef(String strTmCardBef) {
		this.strTmCardBef = strTmCardBef;
	}
	public String getCorpscardno() {
		return corpscardno;
	}
	public void setCorpscardno(String corpscardno) {
		this.corpscardno = corpscardno;
	}
	public int getCorpstype() {
		return corpstype;
	}
	public void setCorpstype(int corpstype) {
		this.corpstype = corpstype;
	}
	public String getCorpssource() {
		return corpssource;
	}
	public void setCorpssource(String corpssource) {
		this.corpssource = corpssource;
	}
	public String getCorpspicno() {
		return corpspicno;
	}
	public void setCorpspicno(String corpspicno) {
		this.corpspicno = corpspicno;
	}
	public double getCorpsamt() {
		return corpsamt;
	}
	public void setCorpsamt(double corpsamt) {
		this.corpsamt = corpsamt;
	}
	public String getCorpspicname() {
		return corpspicname;
	}
	public void setCorpspicname(String corpspicname) {
		this.corpspicname = corpspicname;
	}
	public String getStrDyqCardBef() {
		return strDyqCardBef;
	}
	public void setStrDyqCardBef(String strDyqCardBef) {
		this.strDyqCardBef = strDyqCardBef;
	}
	public String getStrFromDyqCardNo() {
		return strFromDyqCardNo;
	}
	public void setStrFromDyqCardNo(String strFromDyqCardNo) {
		this.strFromDyqCardNo = strFromDyqCardNo;
	}
	public String getStrToDyqCardNo() {
		return strToDyqCardNo;
	}
	public void setStrToDyqCardNo(String strToDyqCardNo) {
		this.strToDyqCardNo = strToDyqCardNo;
	}
	public int getDyqType() {
		return dyqType;
	}
	public void setDyqType(int dyqType) {
		this.dyqType = dyqType;
	}
	public double getDyqCardAmt() {
		return dyqCardAmt;
	}
	public void setDyqCardAmt(double dyqCardAmt) {
		this.dyqCardAmt = dyqCardAmt;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public List<Dnointernalcardinfo> getLsDnointernalcardinfo() {
		return lsDnointernalcardinfo;
	}
	public void setLsDnointernalcardinfo(
			List<Dnointernalcardinfo> lsDnointernalcardinfo) {
		this.lsDnointernalcardinfo = lsDnointernalcardinfo;
	}
	public String getEnabledate() {
		return enabledate;
	}
	public void setEnabledate(String enabledate) {
		this.enabledate = enabledate;
	}

	public String getCardvesting() {
		return cardvesting;
	}
	public void setCardvesting(String cardvesting) {
		this.cardvesting = cardvesting;
	}
	public String getRegistercardno() {
		return registercardno;
	}
	public void setRegistercardno(String registercardno) {
		this.registercardno = registercardno;
	}
	public String getRegistercardtype() {
		return registercardtype;
	}
	public void setRegistercardtype(String registercardtype) {
		this.registercardtype = registercardtype;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getMemberphone() {
		return memberphone;
	}
	public void setMemberphone(String memberphone) {
		this.memberphone = memberphone;
	}
	public String getMemberbrithday() {
		return memberbrithday;
	}
	public void setMemberbrithday(String memberbrithday) {
		this.memberbrithday = memberbrithday;
	}
	public int getMembersex() {
		return membersex;
	}
	public void setMembersex(int membersex) {
		this.membersex = membersex;
	}
	public double getCardbalance() {
		return cardbalance;
	}
	public void setCardbalance(double cardbalance) {
		this.cardbalance = cardbalance;
	}
	public String getRegisterpcid() {
		return registerpcid;
	}
	public void setRegisterpcid(String registerpcid) {
		this.registerpcid = registerpcid;
	}
	public List<Projectinfo> getLsProjectinfo() {
		return lsProjectinfo;
	}
	public void setLsProjectinfo(List<Projectinfo> lsProjectinfo) {
		this.lsProjectinfo = lsProjectinfo;
	}
	public int getCreatetype() {
		return createtype;
	}
	public void setCreatetype(int createtype) {
		this.createtype = createtype;
	}
	
	
}
