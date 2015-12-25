package com.amani.action.CardControl;

import java.text.SimpleDateFormat;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.Companyinfo;
import com.amani.model.Dmpackageinfo;
import com.amani.model.Mpackageinfo;
import com.amani.model.Yearcardinof;
import com.amani.model.Yearselldetal;
import com.amani.model.Yearsellinfo;
import com.amani.service.CardControl.CC018Service;
import com.amani.tools.CommonTool;
import com.amani.tools.HttpClientUtil;
import com.amani.tools.SystemFinal;
import com.opensymphony.xwork2.ActionContext;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc018")
public class CC018Action extends AMN_ModuleAction{

	@Autowired
	public CC018Service cc018Service;
	public List<Mpackageinfo> lsPacks;
	public Yearsellinfo curMaster;
	public List<Dmpackageinfo> lsDkageinfos;
	//public Yearcardinof 
	public String strPackNo;
	public List<Yearselldetal> lsyeaList;
	public double diyqAmt;
	public String strMessage;
	public Yearcardinof yearcardinof;
	public String strDiyqNo;
	public String randomno;//微信扫码卡号
	
	public String getRandomno() {
		return randomno;
	}
	public void setRandomno(String randomno) {
		this.randomno = randomno;
	}

	public double getDiyqAmt() {
		return diyqAmt;
	}

	public void setDiyqAmt(double diyqAmt) {
		this.diyqAmt = diyqAmt;
	}

	public String getStrDiyqNo() {
		return strDiyqNo;
	}

	public void setStrDiyqNo(String strDiyqNo) {
		this.strDiyqNo = strDiyqNo;
	}

	public Mpackageinfo mapMpackageinfo;
	public Mpackageinfo getMapMpackageinfo() {
		return mapMpackageinfo;
	}

	public void setMapMpackageinfo(Mpackageinfo mapMpackageinfo) {
		this.mapMpackageinfo = mapMpackageinfo;
	}

	public String strPhone;
	public String billid;
	public Companyinfo companyinfo;
	public double keepAmount_print;
	public String printTime;
	public String printDate;

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

	public Companyinfo getCompanyinfo() {
		return companyinfo;
	}

	public void setCompanyinfo(Companyinfo companyinfo) {
		this.companyinfo = companyinfo;
	}

	public String getBillid() {
		return billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public String getStrPhone() {
		return strPhone;
	}

	public void setStrPhone(String strPhone) {
		this.strPhone = strPhone;
	}

	public String getStrMessage() {
		return strMessage;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	public List<Yearselldetal> getLsyeaList() {
		return lsyeaList;
	}

	public void setLsyeaList(List<Yearselldetal> lsyeaList) {
		this.lsyeaList = lsyeaList;
	}

	public String getStrPackNo() {
		return strPackNo;
	}

	public void setStrPackNo(String strPackNo) {
		this.strPackNo = strPackNo;
	}

	public List<Dmpackageinfo> getLsDkageinfos() {
		return lsDkageinfos;
	}

	public void setLsDkageinfos(List<Dmpackageinfo> lsDkageinfos) {
		this.lsDkageinfos = lsDkageinfos;
	}

	public List<Mpackageinfo> getLsPacks() {
		return lsPacks;
	}

	public Yearsellinfo getCurMaster() {
		return curMaster;
	}

	public void setCurMaster(Yearsellinfo curMaster) {
		this.curMaster = curMaster;
	}

	public void setLsPacks(List<Mpackageinfo> lsPacks) {
		this.lsPacks = lsPacks;
	}

	public void setCc018Service(CC018Service cc018Service) {
		this.cc018Service = cc018Service;
	}

	@Action(value = "load",  results = { 
			 @Result(name = "load_success", location="/CardControl/CC018/index.jsp"),
			 @Result(name = "load_failure", location = "/CardControl/CC018/index.jsp")	   })
	public String load()
	{
		//lsPacks=cc018Service.loadMpackageinfos();
		curMaster=cc018Service.add();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadPack",  results = { 
			 @Result(name = "load_success", type="json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadPack()
	{
		lsPacks=cc018Service.loadMpackageinfos();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "saveYearCardInfo",  results = { 
			 @Result(name = "load_success", type="json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String saveYearCardInfo()
	{
		strMessage="";
		if(cc018Service.saveYearCardInfo(yearcardinof)==false)
		{
			strMessage="保存年卡资料失败";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "printBill",  results = { 
			 @Result(name = "load_success", type="json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String printBill()
	{
		strMessage="";
		companyinfo=cc018Service.getDataTool().loadCurCompanyInfo(CommonTool.getLoginInfo("COMPID"));
		curMaster=cc018Service.loadYearsellinfo(billid);
		lsyeaList=cc018Service.loadYearselldetals(billid);
		if(curMaster==null)
		{
			strMessage="没有找搞该单据";
		}
		else 
		{
			keepAmount_print=cc018Service.loadStoreAmt(curMaster.getCardno(),2);
		}
		this.printTime = CommonTool.getTimeMask(CommonTool.getCurrTime(), 1);
	    this.printDate = CommonTool.getDateMask(CommonTool.getCurrDate());
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public double getKeepAmount_print() {
		return keepAmount_print;
	}

	public void setKeepAmount_print(double keepAmount_print) {
		this.keepAmount_print = keepAmount_print;
	}

	@Action(value = "loadYearCard",  results = { 
			 @Result(name = "load_success", type="json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadYearCard()
	{
		strMessage="";
		yearcardinof=cc018Service.loadYearCard(strPhone);
		if(yearcardinof==null)
		{
			strMessage="该手机号码不存在";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public Yearcardinof getYearcardinof() {
		return yearcardinof;
	}

	public void setYearcardinof(Yearcardinof yearcardinof) {
		this.yearcardinof = yearcardinof;
	}
	
	
	@Action(value = "validateNointernalcardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateNointernalcardno()
	{
		try
		{
			this.strMessage="";
			this.diyqAmt=this.cc018Service.validateDiyqNo(this.strDiyqNo);
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

	@Action(value = "loadDmpack",  results = { 
			 @Result(name = "load_success", type="json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadDmpack()
	{
		mapMpackageinfo=cc018Service.checkPack(strPackNo);
		lsDkageinfos=cc018Service.loadDmpack(strPackNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "checkPack",  results = { 
			 @Result(name = "load_success", type="json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String checkPack()
	{
		strMessage="";
		mapMpackageinfo=cc018Service.checkPack(strPackNo);
		if(mapMpackageinfo==null)
		{
			strMessage="该套餐不存在";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "post",  results = { 
			 @Result(name = "post_success", type="json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String post()
	{
		double dstoreAmt=0;
		strMessage="";
		if(CommonTool.FormatDouble(curMaster.getStoredamt())>0)
		{
			dstoreAmt=cc018Service.loadStoreAmt(curMaster.getCardno(),2);
			if(dstoreAmt<CommonTool.FormatDouble(curMaster.getStoredamt()))
			{
				strMessage="储值金额不足，请充值后来购买，或者用现金付款";
				return SystemFinal.POST_FAILURE;
			}
		}
		if(CommonTool.FormatDouble(curMaster.getZsamt())>0)
		{
			dstoreAmt=cc018Service.loadStoreAmt(curMaster.getCardno(),6);
			if(dstoreAmt<CommonTool.FormatDouble(curMaster.getZsamt()))
			{
				strMessage="赠送账户余额不足，请充值后来购买，或者用现金付款";
				return SystemFinal.POST_FAILURE;
			}
		}
		if(cc018Service.post(curMaster, lsyeaList,dstoreAmt)==false)
		{
			strMessage="保存失败";
		}
		//会员卡购买疗程微信回执信息
		if(CommonTool.checkStr(randomno))
		{
			new Thread(new Runnable(){
				public void run() {
					String openId = cc018Service.loadWechatOpenId(randomno);
					Map<String, String> mapParam=new HashMap<String, String>();
					mapParam.put("templateID", "o1fzQl4hZC0nFh8d4SOXQdclqNFgflsqkRETrJrbekI");
					mapParam.put("openid", openId);
					mapParam.put("first", "尊敬的会员，您好！您卡号 "+ randomno +" 的订单 "+ curMaster.getId().getBillid() +" 疗程购买成功！");
					mapParam.put("keyword1", cc018Service.getDataTool().loadCompNameById(curMaster.getId().getCompid()));
					mapParam.put("keyword2", String.valueOf(curMaster.getStoredamt()) +"元。");
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
		return SystemFinal.POST_SUCCESS;
	}
	
	
	@Action(value = "checkPhone",  results = { 
			 @Result(name = "load_success", type="json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String checkPhone()
	{
		strMessage="";
		yearcardinof=cc018Service.loadYearCard(curMaster.getPhone());
		if(yearcardinof==null)
		{
			strMessage="没找到该手机号码,请重新拍照";
			return SystemFinal.LOAD_FAILURE;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "sechBill",  results = { 
			 @Result(name = "load_success", type="json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String sechBill()
	{
		this.strMessage="";
		curMaster=cc018Service.loadYearsellinfo(billid);
		lsyeaList=cc018Service.loadYearselldetals(billid);
		if(curMaster==null)
		{
			strMessage="没有找搞该单据";
		}
		else 
		{
			yearcardinof=cc018Service.loadYearCard(curMaster.getIdtphone());
			if(yearcardinof!=null)
			{
				curMaster.setIdtname(yearcardinof.getName());
			}
			yearcardinof=cc018Service.loadYearCard(curMaster.getPhone());
			if(yearcardinof!=null)
			{
				curMaster.setImg(yearcardinof.getImg());
			}
		}
		
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "checkYearPackNo",  results = { 
			@Result(name = "load_success", type="json"),
			@Result(name = "load_failure", type = "json")	   })
	public String checkYearPackNo()
	{
		if(StringUtils.isBlank(strPackNo)){
			strMessage="套餐编号不能为空！";
			return SystemFinal.LOAD_FAILURE;
		}
		//查询年卡用户是否购买过套餐'201047','201048','201049'
		if(StringUtils.equals("201047", strPackNo) || StringUtils.equals("201048", strPackNo) || StringUtils.equals("201049", strPackNo)){
			boolean flag = cc018Service.validateYearPackNo(curMaster.getPhone());
			if(flag){
				strMessage="已经购买过相关的套餐，不能重复购买！";
				return SystemFinal.LOAD_FAILURE;
			}
		}
		boolean _flag = cc018Service.validateIsPackPro(CommonTool.getLoginInfo("COMPID"), curMaster.getPhone(), strPackNo);
		if(!_flag){
			if(StringUtils.equals("201022", strPackNo)){//同类的老年卡可以续费
				_flag = cc018Service.validateIsPackPro(CommonTool.getLoginInfo("COMPID"), curMaster.getPhone(), "201001");
			}else if(StringUtils.equals("201023", strPackNo)){
				_flag = cc018Service.validateIsPackPro(CommonTool.getLoginInfo("COMPID"), curMaster.getPhone(), "201002");
			}else if(StringUtils.equals("201034", strPackNo)){
				_flag = cc018Service.validateIsPackPro(CommonTool.getLoginInfo("COMPID"), curMaster.getPhone(), "201003");
			}else if(StringUtils.equals("201030", strPackNo)){
				_flag = cc018Service.validateIsPackPro(CommonTool.getLoginInfo("COMPID"), curMaster.getPhone(), "201004");
			}else if(StringUtils.equals("201029", strPackNo)){
				_flag = cc018Service.validateIsPackPro(CommonTool.getLoginInfo("COMPID"), curMaster.getPhone(), "201005");
			}else if(StringUtils.equals("201028", strPackNo)){
				_flag = cc018Service.validateIsPackPro(CommonTool.getLoginInfo("COMPID"), curMaster.getPhone(), "201006");
			}
			if(!_flag){
				strMessage="未购买过此套餐，不能添加此套餐！";
				return SystemFinal.LOAD_FAILURE;
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//推送微信密码
	@Action( value="sendWechatPwd", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String sendWechatPwd() {
		String openId = this.cc018Service.loadWechatOpenId(randomno);
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
		strMessage = StringUtils.equals(_ramdomCode, randomno)?"":"验证码不正确，请重新输入！";
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean afterPost() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean postActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean afterLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean loadActive() {
		return false;
	}

	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean afterAdd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean addActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
