package com.amani.action.CardControl;

import java.math.BigDecimal;
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
import com.amani.bean.ReEditBillInfo;
import com.amani.model.Dactivitygive;
import com.amani.model.Dactivityinfo;
import com.amani.model.Dmpackageinfo;
import com.amani.model.Dpayinfo;
import com.amani.model.Mactivityinfo;
import com.amani.model.Mpackageinfo;
import com.amani.model.NointernalcardinfoId;
import com.amani.model.Sendpointcard;
import com.amani.model.Staffinfo;
import com.amani.service.CardControl.CC016Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc016")
public class CC016Action extends AMN_ModuleAction{
	@Autowired
	private CC016Service cc016Service;
	private String strJsonParam;
	private String strJsonDyqParam;
    private String strSearchDate;
	private String strCurCompId;
	private String strCurBillId;
	private String strCurCardNo;
	private double changeseqno;
	private int 	nBillType;
	private Sendpointcard curSendpointcard;
    private ReEditBillInfo curReEditBillInfo;
    private List<ReEditBillInfo> lsReEditBillInfo;

    private List<ReEditBillInfo> lsBillEdits;
    
    private List<Dpayinfo> lsDpayinfo;
	private List<Staffinfo> lsStaffinfo;
	private List<Mpackageinfo> lsMpackageinfo;
    private List<Dmpackageinfo> lsDmpackageinfo;
    private String strCurDyCardNo;
    private NointernalcardinfoId curNointernalcardinfo;
    private String strCurPackageId;
	private double 	sendPointRate1;
	private double	sendPointRate2;
	 
    /***********经理卡验证*******************/
    private String mangerCardNo;
    public String getMangerCardNo() {
		return mangerCardNo;
	}

	public void setMangerCardNo(String mangerCardNo) {
		this.mangerCardNo = mangerCardNo;
	}
    
	public double getSendPointRate1() {
		return sendPointRate1;
	}
	public void setSendPointRate1(double sendPointRate1) {
		this.sendPointRate1 = sendPointRate1;
	}
	public double getSendPointRate2() {
		return sendPointRate2;
	}
	public void setSendPointRate2(double sendPointRate2) {
		this.sendPointRate2 = sendPointRate2;
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
	@Action(value = "loadMasterBillType",  results = { 
			 @Result(name = "load_success", type = "json"),
     @Result(name = "load_failure", type = "json")	
	}) 
	public String loadMasterBillType()
	{
		this.lsBillEdits=this.cc016Service.loadBillTypeDateInfo(strCurCompId, CommonTool.setDateMask(strSearchDate));
		this.lsStaffinfo=this.cc016Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
		this.sendPointRate1=CommonTool.FormatDouble(Double.parseDouble(this.cc016Service.getDataTool().loadSysParam(strCurCompId, "SP100")));
		this.sendPointRate2=CommonTool.FormatDouble(Double.parseDouble(this.cc016Service.getDataTool().loadSysParam(strCurCompId, "SP101")));
		this.lsMpackageinfo=this.cc016Service.loadMpackageinfo(strCurCompId);
		
		return SystemFinal.LOAD_SUCCESS;
	}
	

	@Action(value = "validatePackageNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validatePackageNo()
	{
		this.strMessage="";
		this.lsDmpackageinfo=this.cc016Service.getDataTool().loadDmpackageinfo(strCurCompId,this.strCurPackageId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadMasterinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadMasterinfo()
	{
		
		lsReEditBillInfo=this.cc016Service.loadBillDateInfoByType(this.strCurCompId, this.nBillType, CommonTool.setDateMask(strSearchDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	
	@Action(value = "loadDetialinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String loadDetialinfo()
	{
		
		this.curSendpointcard=this.cc016Service.loadSendpointcard(strCurCompId, this.strCurBillId,strCurCardNo,strSearchDate,nBillType,changeseqno);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "validatePicno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validatePicno()
	{
		this.strMessage="";
		this.curNointernalcardinfo=this.cc016Service.loadDyCardInfoByNo(strCurCompId,this.strCurDyCardNo);
		if(this.curNointernalcardinfo==null || CommonTool.FormatString(curNointernalcardinfo.getCardno()).equals(""))
		{
			this.strMessage="该赠送抵用券不存在或不属于赠送的范围";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	
	
//	@Action(value = "postCurRootInfo",  results = { 
//			 @Result(name = "post_success", type = "json"),
//			 @Result(name = "post_failure", type = "json")	
//	}) 
//	public String postCurRootInfo()
//	{
//		this.strMessage="";
//		boolean flag=this.cc016Service.postCurDateInfo(this.strCurCompId, this.strCurBillId,changeseqno,this.nBillType, this.curReEditBillInfo);
//		if(flag==false)
//		{
//			strMessage="更新失败,刷新后重试!";
//		}
//		return SystemFinal.POST_SUCCESS;
//	}
//	
	

	
	
	@Action(value = "add",  results = { 
			 @Result(name = "add_success", type = "json"),
          @Result(name = "add_failure", type = "json")	
       }) 
	@Override
	public String add()
	{
	
		return SystemFinal.ADD_SUCCESS;
	}
	
	@Action(value = "sendCardPoint",  results = { 
			 @Result(name = "post_success", type = "json"),
           @Result(name = "post_failure", type = "json")	
    }) 
	public String sendCardPoint()
	{
		try
		{
			this.strMessage="";
			
			boolean flag=this.cc016Service.validateBill(curSendpointcard.getId().getSendcompid(), curSendpointcard.getSourcebillid(), curSendpointcard.getId().getSendtype(),curSendpointcard.getChangeseqno(),curSendpointcard.getSourcecardno(),curSendpointcard.getSourcedate());
			if(flag==false)
			{
				this.strMessage="该单据已经赠送过积分,请确认";
				return SystemFinal.POST_SUCCESS;
			}
			//赠送条码卡
			if(CommonTool.FormatInteger(curSendpointcard.getSendpicflag())==2)
			{
				if(this.strJsonParam==null || this.strJsonParam.equals("") || this.strJsonParam.indexOf("{")==-1)
				{
					this.strMessage="请确认赠送条码卡项目";
					return SystemFinal.POST_SUCCESS;
				}
				flag=this.cc016Service.validateTmkCardNo(CommonTool.FormatString(curSendpointcard.getPicno()));
				if(flag==false)
				{
					this.strMessage="该条码卡不在登记状态或不存在,请确认";
					return SystemFinal.POST_SUCCESS;
				}
			}
			else if(CommonTool.FormatInteger(curSendpointcard.getSendpicflag())==4) //赠送项目抵用券
			{
				
				if(CommonTool.FormatInteger(curSendpointcard.getId().getSendtype())==6) //疗程兑换
				{
					if(this.strJsonDyqParam==null || this.strJsonDyqParam.equals("") || this.strJsonDyqParam.indexOf("{")==-1)
					{
						this.strMessage="请确认赠送抵用券编号";
						return SystemFinal.POST_SUCCESS;
					}
					if(strJsonDyqParam!= null && !strJsonDyqParam.equals(""))
					{
						List<NointernalcardinfoId> lsdnointernalcardinfo=this.cc016Service.getDataTool().loadDTOList(strJsonDyqParam, NointernalcardinfoId.class);
						if(lsdnointernalcardinfo!=null && lsdnointernalcardinfo.size()>0)
						{
							for(int i=0;i<lsdnointernalcardinfo.size();i++)
							{
								if(!CommonTool.FormatString(lsdnointernalcardinfo.get(i).getCardno()).equals(""))
								{
									flag=this.cc016Service.validateXmDyqCardNo(CommonTool.FormatString(lsdnointernalcardinfo.get(i).getCardno()),curSendpointcard.getCreatecardtype());
									if(flag==false)
									{
										this.strMessage="该抵用券"+CommonTool.FormatString(lsdnointernalcardinfo.get(i).getCardno())+"不在登记状态或该类型的券不存在,请确认";
										return SystemFinal.POST_SUCCESS;
									}
								}
							}
						}
					}
				}
				else if(CommonTool.FormatInteger(curSendpointcard.getId().getSendtype())==7) //美发疗程兑换
				{
					if(this.strJsonDyqParam==null || this.strJsonDyqParam.equals("") || this.strJsonDyqParam.indexOf("{")==-1)
					{
						this.strMessage="请确认赠送抵用券编号";
						return SystemFinal.POST_SUCCESS;
					}
					if(strJsonDyqParam!= null && !strJsonDyqParam.equals(""))
					{
						List<NointernalcardinfoId> lsdnointernalcardinfo=this.cc016Service.getDataTool().loadDTOList(strJsonDyqParam, NointernalcardinfoId.class);
						if(lsdnointernalcardinfo!=null && lsdnointernalcardinfo.size()>0)
						{
							for(int i=0;i<lsdnointernalcardinfo.size();i++)
							{
								if(!CommonTool.FormatString(lsdnointernalcardinfo.get(i).getCardno()).equals(""))
								{
									flag=this.cc016Service.validateXmDyqCardNo(CommonTool.FormatString(lsdnointernalcardinfo.get(i).getCardno()),curSendpointcard.getCreatecardtype());
									if(flag==false)
									{
										this.strMessage="该抵用券"+CommonTool.FormatString(lsdnointernalcardinfo.get(i).getCardno())+"不在登记状态或该类型的券不存在,请确认";
										return SystemFinal.POST_SUCCESS;
									}
								}
							}
						}
					}
				}
				else
				{
					if(this.strJsonParam==null || this.strJsonParam.equals("") || this.strJsonParam.indexOf("{")==-1)
					{
						this.strMessage="请确认赠送抵用券项目";
						return SystemFinal.POST_SUCCESS;
					}
					flag=this.cc016Service.validateXmDyqCardNo(CommonTool.FormatString(curSendpointcard.getPicno()),curSendpointcard.getCreatecardtype());
					if(flag==false)
					{
						this.strMessage="该抵用券不在登记状态或该类型的券不存在,请确认";
						return SystemFinal.POST_SUCCESS;
					}
				}
				
			}
			else if(CommonTool.FormatInteger(curSendpointcard.getSendpicflag())==5) //无条件赠送项目抵用券
			{
				if(this.strJsonDyqParam==null || this.strJsonDyqParam.equals("") || this.strJsonDyqParam.indexOf("{")==-1)
				{
					this.strMessage="请确认赠送抵用券编号";
					return SystemFinal.POST_SUCCESS;
				}
				/*if(strJsonDyqParam!= null && !strJsonDyqParam.equals(""))
				{
					List<NointernalcardinfoId> lsdnointernalcardinfo=this.cc016Service.getDataTool().loadDTOList(strJsonDyqParam, NointernalcardinfoId.class);
					if(lsdnointernalcardinfo!=null && lsdnointernalcardinfo.size()>0)
					{
						for(int i=0;i<lsdnointernalcardinfo.size();i++)
						{
							if(!CommonTool.FormatString(lsdnointernalcardinfo.get(i).getCardno()).equals(""))
							{
								flag=this.cc016Service.validateXmDyqCardNo(CommonTool.FormatString(lsdnointernalcardinfo.get(i).getCardno()),curSendpointcard.getCreatecardtype());
								if(flag==false)
								{
									this.strMessage="该抵用券"+CommonTool.FormatString(lsdnointernalcardinfo.get(i).getCardno())+"不在登记状态或该类型的券不存在,请确认";
									return SystemFinal.POST_SUCCESS;
								}
							}
						}
					}
				}*/
			}
			else if(CommonTool.FormatInteger(curSendpointcard.getSendpicflag())==6)
			{
				if(Integer.parseInt(CommonTool.setDateMask(strSearchDate))<=20141130 || Integer.parseInt(CommonTool.setDateMask(strSearchDate))>20150218)
				{
					this.strMessage="2014大礼包活动开始日期为2014-12-15到2015-02-18,如有疑问请致电运营部!";
					return SystemFinal.POST_SUCCESS;
				}
			}
			else if(CommonTool.FormatInteger(curSendpointcard.getSendpicflag())==9)
			{
				if(Integer.parseInt(CommonTool.setDateMask(strSearchDate))<=20150301 || Integer.parseInt(CommonTool.setDateMask(strSearchDate))>20150331)
				{
					this.strMessage="2014大礼包活动开始日期为2015-03-01到2015-03-31,如有疑问请致电运营部!";
					return SystemFinal.POST_SUCCESS;
				}
				if(curSendpointcard.getSourceamt().intValue()>=6800 && curSendpointcard.getSourceamt().intValue()<16800)
				{
					curSendpointcard.setSendamt(BigDecimal.valueOf(680));
				}
				if(curSendpointcard.getSourceamt().intValue()>=16800)
				{
					curSendpointcard.setSendamt(BigDecimal.valueOf(1680));
				}
				String strHDDYJ="NS"+CommonTool.random(9);
				curSendpointcard.setFirstdateno(strHDDYJ);
				/*String billType="";
				if(curSendpointcard.getId().getSendtype()==0)
				{
					billType="";
				}*/
				//cc016Service.sendCashDyq(curSendpointcard.getSourcecardno(), strPhone, curSendpointcard.getSourcebillid(), billType, amt)
			}
			else if(CommonTool.FormatInteger(curSendpointcard.getSendpicflag())==3) //赠送日历
			{
				if(strSearchDate.equals(""))
				{
					strSearchDate=CommonTool.getCurrDate();
				}
				if(Integer.parseInt(CommonTool.setDateMask(strSearchDate))>20140118)
				{
					this.strMessage="赠送日历活动已在2014年1月18号停止,如有疑问请致电运营部!";
					return SystemFinal.POST_SUCCESS;
				}
				if(CommonTool.FormatBigDecimal(curSendpointcard.getSourceamt()).doubleValue()<10000)
				{
					this.strMessage="赠送台历金额不达标,请确认";
					return SystemFinal.POST_SUCCESS;
				}
				if(CommonTool.FormatString(curSendpointcard.getFirstdateno()).length()!=8)
				{
					this.strMessage="输入的一月份的券号不正确,请确认";
					return SystemFinal.POST_SUCCESS;
				}
				if(CommonTool.isNumeric(CommonTool.FormatString(curSendpointcard.getFirstdateno()))==false)
				{
					this.strMessage="输入的编码格式不正确,请确认";
					return SystemFinal.POST_SUCCESS;
				}
				if((!CommonTool.FormatString(curSendpointcard.getFirstdateno()).substring(0,5).equals("01201")
				&& !CommonTool.FormatString(curSendpointcard.getFirstdateno()).substring(0,5).equals("01401")
				&& !CommonTool.FormatString(curSendpointcard.getFirstdateno()).substring(0,5).equals("91201"))
				|| Integer.parseInt(CommonTool.FormatString(curSendpointcard.getFirstdateno()).substring(3,5))>12
				|| Integer.parseInt(CommonTool.FormatString(curSendpointcard.getFirstdateno()).substring(5,8))>999)
				{
					this.strMessage="输入的编码格式不正确,请确认";
					return SystemFinal.POST_SUCCESS;
				}
				if(CommonTool.FormatBigDecimal(curSendpointcard.getSourceamt()).doubleValue()>=10000
				&& CommonTool.FormatBigDecimal(curSendpointcard.getSourceamt()).doubleValue()<30000
				&& !CommonTool.FormatString(curSendpointcard.getFirstdateno()).substring(0,3).equals("012")
				&& !CommonTool.FormatString(curSendpointcard.getFirstdateno()).substring(0,3).equals("912"))
				{
					this.strMessage="输入的编码格式不正确,请确认";
					return SystemFinal.POST_SUCCESS;
				}
				flag=this.cc016Service.validateDyqCardNo(CommonTool.FormatString(curSendpointcard.getFirstdateno()));
				if(flag==false)
				{
					this.strMessage="该券号已经在系统中存在,请确认";
					return SystemFinal.POST_SUCCESS;
				}
			}else if(CommonTool.FormatInteger(curSendpointcard.getSendpicflag())==10) //余姚开业活动
			{
				if(Integer.parseInt(CommonTool.setDateMask(strSearchDate))<20150710 || Integer.parseInt(CommonTool.setDateMask(strSearchDate))>20150831)
				{
					this.strMessage="余姚开业活动日期为2015-07-10到2015-08-31,如有疑问请致电运营部!";
					return SystemFinal.POST_SUCCESS;
				}
			}else if(CommonTool.FormatInteger(curSendpointcard.getSendpicflag())==0){ //9月胸部活动
				if(Integer.parseInt(CommonTool.setDateMask(strSearchDate))<20150901 || Integer.parseInt(CommonTool.setDateMask(strSearchDate))>20151031){
					this.strMessage="9月胸部活动日期为2015-09-01到2015-10-31,如有疑问请致电运营部!";
					return SystemFinal.POST_SUCCESS;
				}
			}
			flag=this.cc016Service.handCardPointSend(this.curSendpointcard, this.strJsonParam,strJsonDyqParam);
			if(flag==false)
				this.strMessage="赠送失败";
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
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
	
	
	
	@Action(value = "handInsertShare",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String handInsertShare()
	{
		this.strMessage="";
		boolean flag=this.cc016Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
		if(flag==false)
		{
			this.strMessage="该经理密码不对,请核实";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	private String activinid;
	private int activtype;
	private int activorand;
	private Mactivityinfo mactivityinfo;
	private List<Mactivityinfo> activitySet;
	private List<Dactivityinfo> dactivSet;
	private List<Dactivitygive> dactivGive;
	//加载活动列表
	@Action( value="loadActivity", results={ @Result(type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadActivity() {
		try{
			activitySet = cc016Service.loadActivity(strCurCompId, nBillType, strSearchDate);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	//加载活动明细
	@Action( value="loadActivDetail", results={ @Result(type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadActivDetail() {
		try{
			mactivityinfo = cc016Service.loadActivity(strCurCompId, activinid);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//加载活动赠送明细
	@Action( value="loadGiveDetail", results={ @Result(type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadGiveDetail() {
		try{
			 dactivGive = cc016Service.loadGiveInfo(strCurCompId, activinid, activtype, activorand);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//校验疗程项目列表赠送条件
	@Action( value="validateItems", results={ @Result(type="json", name="load_success"),
			@Result( type="json", name="load_failure")})
	public String validateItems() {
		try{
			boolean bool = cc016Service.validateItems(strCurCompId, strCurBillId, activinid);
			strMessage = bool ? "OK" : "ERROR";
		}catch(Exception ex){
			ex.printStackTrace();
			strMessage = "ERROR";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	//校验产品列表赠送条件
	@Action( value="validateGoods", results={ @Result(type="json", name="load_success"),
			@Result( type="json", name="load_failure")})
	public String validateGoods() {
		try{
			boolean bool = cc016Service.validateGoods(strCurCompId, strCurBillId, activinid);
			strMessage = bool ? "OK" : "ERROR";
		}catch(Exception ex){
			ex.printStackTrace();
			strMessage = "ERROR";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//获取胸部和香遇疗程赠送条件
	@Action( value="loadFaceamt", results={ @Result(type="json", name="load_success"),
			@Result( type="json", name="load_failure")})
	public String loadFaceamt() {
		try{
			Gson gson = new Gson();
			Map<String, String> result = cc016Service.loadFaceamt(strCurCompId, strCurCardNo, CommonTool.setDateMask(strSearchDate));
			strMessage = gson.toJson(result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
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
	public List<Dpayinfo> getLsDpayinfo() {
		return lsDpayinfo;
	}
	public void setLsDpayinfo(List<Dpayinfo> lsDpayinfo) {
		this.lsDpayinfo = lsDpayinfo;
	}
	

	public String getStrSearchDate() {
		return strSearchDate;
	}
	public void setStrSearchDate(String strSearchDate) {
		this.strSearchDate = strSearchDate;
	}
	public int getNBillType() {
		return nBillType;
	}
	public void setNBillType(int billType) {
		nBillType = billType;
	}
	public ReEditBillInfo getCurReEditBillInfo() {
		return curReEditBillInfo;
	}
	public void setCurReEditBillInfo(ReEditBillInfo curReEditBillInfo) {
		this.curReEditBillInfo = curReEditBillInfo;
	}
	public List<ReEditBillInfo> getLsReEditBillInfo() {
		return lsReEditBillInfo;
	}
	public void setLsReEditBillInfo(List<ReEditBillInfo> lsReEditBillInfo) {
		this.lsReEditBillInfo = lsReEditBillInfo;
	}
	public List<ReEditBillInfo> getLsBillEdits() {
		return lsBillEdits;
	}
	public void setLsBillEdits(List<ReEditBillInfo> lsBillEdits) {
		this.lsBillEdits = lsBillEdits;
	}
	public double getChangeseqno() {
		return changeseqno;
	}
	public void setChangeseqno(double changeseqno) {
		this.changeseqno = changeseqno;
	}
	@JSON(serialize=false)
	public CC016Service getCc016Service() {
		return cc016Service;
	}
	@JSON(serialize=false)
	public void setCc016Service(CC016Service cc016Service) {
		this.cc016Service = cc016Service;
	}
	public Sendpointcard getCurSendpointcard() {
		return curSendpointcard;
	}
	public void setCurSendpointcard(Sendpointcard curSendpointcard) {
		this.curSendpointcard = curSendpointcard;
	}
	public String getStrJsonDyqParam() {
		return strJsonDyqParam;
	}
	public void setStrJsonDyqParam(String strJsonDyqParam) {
		this.strJsonDyqParam = strJsonDyqParam;
	}
	public String getStrCurCardNo() {
		return strCurCardNo;
	}
	public void setStrCurCardNo(String strCurCardNo) {
		this.strCurCardNo = strCurCardNo;
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
	public String getStrCurPackageId() {
		return strCurPackageId;
	}
	public void setStrCurPackageId(String strCurPackageId) {
		this.strCurPackageId = strCurPackageId;
	}
	public String getStrCurDyCardNo() {
		return strCurDyCardNo;
	}
	public void setStrCurDyCardNo(String strCurDyCardNo) {
		this.strCurDyCardNo = strCurDyCardNo;
	}
	public NointernalcardinfoId getCurNointernalcardinfo() {
		return curNointernalcardinfo;
	}
	public void setCurNointernalcardinfo(NointernalcardinfoId curNointernalcardinfo) {
		this.curNointernalcardinfo = curNointernalcardinfo;
	}
	public List<Mactivityinfo> getActivitySet() {
		return activitySet;
	}
	public void setActivitySet(List<Mactivityinfo> activitySet) {
		this.activitySet = activitySet;
	}
	public String getActivinid() {
		return activinid;
	}
	public void setActivinid(String activinid) {
		this.activinid = activinid;
	}
	public int getActivtype() {
		return activtype;
	}
	public void setActivtype(int activtype) {
		this.activtype = activtype;
	}
	public int getActivorand() {
		return activorand;
	}
	public void setActivorand(int activorand) {
		this.activorand = activorand;
	}

	public Mactivityinfo getMactivityinfo() {
		return mactivityinfo;
	}
	public void setMactivityinfo(Mactivityinfo mactivityinfo) {
		this.mactivityinfo = mactivityinfo;
	}
	public List<Dactivityinfo> getDactivSet() {
		return dactivSet;
	}
	public void setDactivSet(List<Dactivityinfo> dactivSet) {
		this.dactivSet = dactivSet;
	}
	public List<Dactivitygive> getDactivGive() {
		return dactivGive;
	}
	public void setDactivGive(List<Dactivitygive> dactivGive) {
		this.dactivGive = dactivGive;
	}
}
