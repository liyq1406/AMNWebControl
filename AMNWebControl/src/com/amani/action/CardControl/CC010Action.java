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
import com.amani.model.Mcardchangeinfo;
import com.amani.service.CardControl.CC010Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc010")
public class CC010Action extends AMN_ModuleAction{
	@Autowired
	private CC010Service cc010Service;
	private String strJsonParam;	
	private String strCurPackageNo;
    private List<Mcardchangeinfo> lsMcardchangeinfo;
    private Mcardchangeinfo curMcardchangeinfo;
    private String strCurCompId;
    private String strCurBillId;
    private String strCurCardNo;
    private String strCurNewCardNo;
    private String strCurNewCardType;
    private String strRemark;
	private Cardinfo curCardinfo;
	private String searchCardNoKey;
	private String searchMemberNameKey;
	private String searchMemberPhoneKey;
	private String searchMemberPcidKey;
    private List<Cardinfo> lsCardinfos;
    private String searchLossCardNo;
    private String searchRecevieCardNo;
    private String strCardNo;
    private String handMemberName;
	private String handPhone;
	private String handPcid;
    private String mangerCardNo;
    private int	needLossFlag;
	public int getNeedLossFlag() {
		return needLossFlag;
	}

	public void setNeedLossFlag(int needLossFlag) {
		this.needLossFlag = needLossFlag;
	}

	public String getStrCardNo() {
		return strCardNo;
	}

	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
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

	public String getMangerCardNo() {
		return mangerCardNo;
	}

	public void setMangerCardNo(String mangerCardNo) {
		this.mangerCardNo = mangerCardNo;
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
		this.lsMcardchangeinfo=this.cc010Service.loadMasterDateByCompId(this.strCurCompId);
		if(this.lsMcardchangeinfo==null || lsMcardchangeinfo.size()==0)
		{
			this.lsMcardchangeinfo=new ArrayList();
			
		}
		this.curMcardchangeinfo=this.cc010Service.addMastRecord(this.strCurCompId);
		//lsMcardchangeinfo.add(0,curMcardchangeinfo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadCurMcardchangeinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMcardchangeinfo()
	{
		this.curMcardchangeinfo=this.cc010Service.loadcurMaster(this.strCurCompId,this.strCurBillId);
		if(curMcardchangeinfo==null)
			curMcardchangeinfo=this.cc010Service.addMastRecord(strCurCompId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "searchCardinfos",  results = { 
			 @Result(name = "load_success", type = "json"),
  @Result(name = "load_failure", type = "json")	
	}) 
	public String searchCardinfos()
	{
		this.lsCardinfos=this.cc010Service.getDataTool().searchCardAllinfo(strCurCompId, searchCardNoKey, searchMemberNameKey, searchMemberPhoneKey, searchMemberPcidKey);
		if(this.cc010Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC010", SystemFinal.UR_SPECIAL_CHECK)==true)
		{
			if(lsCardinfos!=null && lsCardinfos.size()>0)
			{
				for(int i=0;i<lsCardinfos.size();i++)
				{
					if(!CommonTool.FormatString(lsCardinfos.get(i).getMembername()).equals(""))
					{
						lsCardinfos.get(i).setMembername(lsCardinfos.get(i).getMembername().substring(0,1)+"**");
					}
					if(!CommonTool.FormatString(lsCardinfos.get(i).getMemberphone()).equals("") && CommonTool.FormatString(lsCardinfos.get(i).getMemberphone()).length()>=8)
					{
						lsCardinfos.get(i).setShowmemberphone(lsCardinfos.get(i).getMemberphone().substring(0,7)+"****");
					}
				}
			}
		}
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
			this.curCardinfo=this.cc010Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId, this.strCurCardNo);
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
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
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
	//挂失卡号
	@Action(value = "handlossCard",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String handlossCard()
	{
		try
		{
			this.strMessage="";
			if(this.cc010Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX001")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.cc010Service.handlossCard(this.curMcardchangeinfo);
			if(flag==false)
				this.strMessage="会员卡挂失失败";
			else
			{
				if(!CommonTool.FormatString(this.curMcardchangeinfo.getMemberphone()).equals("") && CommonTool.FormatString(this.curMcardchangeinfo.getMemberphone()).length()==11)
					this.cc010Service.sendMsg(this.curMcardchangeinfo.getMemberphone(), "您好,您的尾号为"+curMcardchangeinfo.getChangebeforcardno().substring(curMcardchangeinfo.getChangebeforcardno().length()-4,curMcardchangeinfo.getChangebeforcardno().length())+"的IC卡已于"+CommonTool.getCurrDate().substring(0,4)+"年"+CommonTool.getCurrDate().substring(4,6)+"月"+CommonTool.getCurrDate().substring(6,8)+"号在阿玛尼["+CommonTool.getLoginInfo("COMPNAME")+"]被挂失(单号:"+this.curMcardchangeinfo.getId().getChangebillid()+"),如非本人操作,请致电4006622818核实，谢谢您的支持！");
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	//解挂卡号
	@Action(value = "handReceiveCard",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       })
	public String handReceiveCard()
	{
		try
		{
			this.strMessage="";
			if(this.cc010Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX002")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.cc010Service.handReceiveCard(this.strCurCompId,this.strCurBillId,this.strCurCardNo);
			if(flag==false)
				this.strMessage="会员卡解挂失败";
			else
			{
				if(!CommonTool.FormatString(this.handPhone).equals("") && CommonTool.FormatString(handPhone).length()==11)
						this.cc010Service.sendMsg(this.handPhone, "您好,您的尾号为"+strCurCardNo.substring(strCurCardNo.length()-4,strCurCardNo.length())+"的IC卡已于"+CommonTool.getCurrDate().substring(0,4)+"年"+CommonTool.getCurrDate().substring(4,6)+"月"+CommonTool.getCurrDate().substring(6,8)+"号在阿玛尼["+this.cc010Service.getDataTool().loadCompNameById(strCurCompId)+"]被解挂(单号:"+this.strCurBillId+"),如非本人操作,请致电4006622818核实，谢谢您的支持！");
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	//补卡登记
	@Action(value = "entryReceviceCard",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String entryReceviceCard()
	{
		try
		{
			this.strMessage="";

			boolean flag=this.cc010Service.entryReceviceCard(this.strCurCompId,this.strCurBillId,this.strCurNewCardNo,this.strCurNewCardType, strRemark,strCurCardNo);
			if(flag==false)
				this.strMessage="会员卡补卡登记失败";
			flag=this.cc010Service.handConfirmCard(this.strCurCompId,this.strCurBillId,this.strCurCardNo,strCurNewCardNo);
			if(flag==false)
				this.strMessage="会员卡补卡审核失败";
			/*else
			{
				if(!CommonTool.FormatString(this.handPhone).equals("") && CommonTool.FormatString(handPhone).length()==11)
						this.cc010Service.sendMsg(this.handPhone, "您好,您的尾号为"+strCurCardNo.substring(strCurCardNo.length()-4,strCurCardNo.length())+"的IC卡已于"+CommonTool.getCurrDate().substring(0,4)+"年"+CommonTool.getCurrDate().substring(4,6)+"月"+CommonTool.getCurrDate().substring(6,8)+"号在阿玛尼["+this.cc010Service.getDataTool().loadCompNameById(strCurCompId)+"]补卡成功(单号:"+this.strCurBillId+"),如非本人操作,请致电4006622818核实，谢谢您的支持！");
			}*/
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			strMessage=ex.getMessage();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	//发送短信
	@Action(value = "sendMsg",  results = { 
			 @Result(name = "post_success", type = "json"),
         @Result(name = "post_failure", type = "json")	
      })
	public String sendMsg()
	{
		try {
				if(!CommonTool.FormatString(this.handPhone).equals("") && CommonTool.FormatString(handPhone).length()==11)	
						this.cc010Service.sendMsg(this.handPhone, "您好,您的尾号为"+strCurCardNo.substring(strCurCardNo.length()-4,strCurCardNo.length())+"的IC卡已于"+CommonTool.getCurrDate().substring(0,4)+"年"+CommonTool.getCurrDate().substring(4,6)+"月"+CommonTool.getCurrDate().substring(6,8)+"号在阿玛尼["+this.cc010Service.getDataTool().loadCompNameById(strCurCompId)+"]补卡成功(单号:"+this.strCurBillId+"),如非本人操作,请致电4006622818核实，谢谢您的支持！");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return SystemFinal.POST_FAILURE;
	}
	
	//驳回补卡
	@Action(value = "handRejectCard",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String handRejectCard()
	{
		try
		{
			this.strMessage="";
			if(this.cc010Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX004")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.cc010Service.handRejectCard(this.strCurCompId,this.strCurBillId,this.strCurCardNo,strCurNewCardNo);
			if(flag==false)
				this.strMessage="会员卡补卡驳回失败";
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	//同意补卡
	@Action(value = "handConfirmCard",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String handConfirmCard()
	{
		try
		{
			this.strMessage="";
			if(this.cc010Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX003")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.cc010Service.handConfirmCard(this.strCurCompId,this.strCurBillId,this.strCurCardNo,strCurNewCardNo);
			if(flag==false)
				this.strMessage="会员卡补卡审核失败";
			else
			{
				if(!CommonTool.FormatString(this.handPhone).equals("") && CommonTool.FormatString(handPhone).length()==11)
						this.cc010Service.sendMsg(this.handPhone, "您好,您的尾号为"+strCurCardNo.substring(strCurCardNo.length()-4,strCurCardNo.length())+"的IC卡已于"+CommonTool.getCurrDate().substring(0,4)+"年"+CommonTool.getCurrDate().substring(4,6)+"月"+CommonTool.getCurrDate().substring(6,8)+"号在阿玛尼["+this.cc010Service.getDataTool().loadCompNameById(strCurCompId)+"]补卡成功(单号:"+this.strCurBillId+"),如非本人操作,请致电4006622818核实，谢谢您的支持！");
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	@Action(value = "searchBill",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	})
	public String searchBill()
	{
		try
		{
			if(!this.searchRecevieCardNo.equals(""))
			{
				needLossFlag=0;
				this.lsMcardchangeinfo=this.cc010Service.loadMasterDateByCard(this.strCurCompId, this.searchLossCardNo, this.searchRecevieCardNo);
			}
			else
			{
				this.curCardinfo=this.cc010Service.getDataTool().loadCardinfobyCardNo(this.strCurCompId, this.searchLossCardNo);
				if(this.curCardinfo==null || CommonTool.FormatString(this.curCardinfo.getId().getCardno()).equals(""))
				{
					needLossFlag=1;  //卡不存在
					this.lsMcardchangeinfo=null;
				}
				else
				{
					this.lsMcardchangeinfo=this.cc010Service.loadMasterDateByCard(this.strCurCompId, this.searchLossCardNo, this.searchRecevieCardNo);
					if(CommonTool.FormatInteger(curCardinfo.getCardstate())==4 || CommonTool.FormatInteger(curCardinfo.getCardstate())==5)
					{
						needLossFlag=2; //卡存在并且卡正常
						curMcardchangeinfo=this.cc010Service.addMastRecord(strCurCompId);
					}
					else
					{
						needLossFlag=3;
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
	@Action(value = "handCardState",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String handCardState()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.cc010Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
			if(flag==false)
			{
				this.strMessage="该经理密码不对,请核实";
				return SystemFinal.LOAD_SUCCESS;
			}
			flag=this.cc010Service.validateMemberInfo(this.strCardNo, this.handMemberName, this.handPhone, this.handPcid);
			if(flag==false)
			{
				this.strMessage="该会员信息与系统核实有误,不能解冻,请确认";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	@JSON(serialize=false)
	public CC010Service getCc010Service() {
		return cc010Service;
	}
    @JSON(serialize=false)
	public void setCc010Service(CC010Service cc010Service) {
		this.cc010Service = cc010Service;
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

	
	
}
