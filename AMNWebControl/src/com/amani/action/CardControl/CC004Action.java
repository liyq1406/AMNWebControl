package com.amani.action.CardControl;

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

import com.amani.model.Cardinfo;
import com.amani.model.Corpsbuyinfo;
import com.amani.model.Dcardapponline;
import com.amani.model.DcardapponlineId;
import com.amani.model.Dmpackageinfo;
import com.amani.model.Dnointernalcardinfo;
import com.amani.model.Dsalebarcodecardinfo;
import com.amani.model.DsalebarcodecardinfoId;
import com.amani.model.Mcardapponline;
import com.amani.model.McardapponlineId;
import com.amani.model.Mpackageinfo;
import com.amani.model.Msalebarcodecardinfo;
import com.amani.model.Nointernalcardinfo;
import com.amani.model.Staffinfo;
import com.amani.service.CardControl.CC004Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc004")
public class CC004Action extends AMN_ModuleAction{
	@Autowired
	private CC004Service cc004Service;
	private String strJsonParam;	
	private String strCurCompId;
	private int searchCorpsTypeKey;
	private String searchCorpsNoKey;
	private int searchCorpsStateKey;
	private String searchFromDateKey;
	private String searchToDateDateKey;
	private Mpackageinfo mpackageinfo;
	public Mpackageinfo getMpackageinfo() {
		return mpackageinfo;
	}
	public void setMpackageinfo(Mpackageinfo mpackageinfo) {
		this.mpackageinfo = mpackageinfo;
	}
	private String searchDyqNoKey;
	private int searchDyqStateKey; 
	private String strDyqCardNo;
    private List<Corpsbuyinfo> lsCorpsbuyinfo;
    private List<Nointernalcardinfo> lsNointernalcardinfo;
    private List<Dnointernalcardinfo> lsDnointernalcardinfo;
    private List<Staffinfo> lsStaffinfo;
    private Msalebarcodecardinfo curMaster;
    private List<Dsalebarcodecardinfo> lsDetial;
	private List<Mpackageinfo> lsMpackageinfo;
	private String searchTmkNoKey;
	private int searchTmkStateKey; 
	private int searchTmkTypeKey; 
	private String strTmkCardNo;
	private String strTmkEntryType;
	private String strCurPackageNo;
	private String strCurPackageName;
	private List<Dmpackageinfo> lsDmpackageinfo;
	private String strCardNo;
	private Cardinfo curCardinfo;
	private String retCardNo;
	private String newvalidate;
	private int	curUseMonths;
	private String searchBillId;
	
	public String getSearchBillId() {
		return searchBillId;
	}
	public void setSearchBillId(String searchBillId) {
		this.searchBillId = searchBillId;
	}
	public int getCurUseMonths() {
		return curUseMonths;
	}
	public void setCurUseMonths(int curUseMonths) {
		this.curUseMonths = curUseMonths;
	}
	public String getRetCardNo() {
		return retCardNo;
	}
	public void setRetCardNo(String retCardNo) {
		this.retCardNo = retCardNo;
	}
	public String getNewvalidate() {
		return newvalidate;
	}
	public void setNewvalidate(String newvalidate) {
		this.newvalidate = newvalidate;
	}
	public String getStrCurPackageNo() {
		return strCurPackageNo;
	}
	public void setStrCurPackageNo(String strCurPackageNo) {
		this.strCurPackageNo = strCurPackageNo;
	}
	public String getStrCurPackageName() {
		return strCurPackageName;
	}
	public void setStrCurPackageName(String strCurPackageName) {
		this.strCurPackageName = strCurPackageName;
	}
	public String getStrTmkEntryType() {
		return strTmkEntryType;
	}
	public void setStrTmkEntryType(String strTmkEntryType) {
		this.strTmkEntryType = strTmkEntryType;
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
		curMaster.getId().setSalecompid(this.strCurCompId);
		curMaster.getId().setSalebillid(this.cc004Service.getDataTool().loadBillIdByRule(strCurCompId,"msalebarcodecardinfo", "salebillid", "SP008"));
		curMaster.setSaledate(CommonTool.setDateMask(curMaster.getSaledate()));
		curMaster.setSaletime(CommonTool.getTimeMask(curMaster.getSaletime(), 1));
		this.lsDetial=this.cc004Service.getDataTool().loadDTOList(strJsonParam, Dsalebarcodecardinfo.class);
		if(lsDetial!=null && lsDetial.size()>0)
		{
			for(int i=0;i<lsDetial.size();i++)
			{
				if(lsDetial.get(i)!=null && !CommonTool.FormatString(lsDetial.get(i).getSaleproid()).equals(""))
				{
					lsDetial.get(i).setId(new DsalebarcodecardinfoId(curMaster.getId().getSalecompid(),curMaster.getId().getSalebillid(),i*1.0));
					lsDetial.get(i).setPackageNo(curMaster.getPackageNo());
				}
				else
				{
					lsDetial.remove(i);
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
	
	@Action(value = "searchCorpsinfos",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String searchCorpsinfos()
	{
		this.lsCorpsbuyinfo=this.cc004Service.searchCorpsInfo(strCurCompId, searchCorpsTypeKey, searchCorpsNoKey, searchCorpsStateKey, searchFromDateKey, searchToDateDateKey);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "retNewDate",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String retNewDate()
	{
		this.strMessage="";
		boolean flag=this.cc004Service.postNewValidate(this.retCardNo, this.newvalidate);
		if(flag==false)
		{
			this.strMessage="设置失败!";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "searchDyqinfos",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String searchDyqinfos()
	{
		this.lsNointernalcardinfo=this.cc004Service.searchDyqInfos(searchDyqNoKey, searchDyqStateKey, searchBillId, searchFromDateKey, searchToDateDateKey);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "searchDyqDetialinfos",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String searchDyqDetialinfos()
	{
		this.lsDnointernalcardinfo=this.cc004Service.searchDyqDetialInfos(strCurCompId, strDyqCardNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "searchTmkinfos",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String searchTmkinfos()
	{
		this.lsNointernalcardinfo=this.cc004Service.searchTmkInfos(searchTmkNoKey, searchTmkStateKey);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "searchTmkDetialinfos",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String searchTmkDetialinfos()
	{
		this.lsDnointernalcardinfo=this.cc004Service.searchDyqDetialInfos(strCurCompId, this.strTmkCardNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "validateBarcodecardno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validateBarcodecardno()
	{
		this.strMessage="";
		StringBuffer strTmkEntryTypeBuf=new StringBuffer();
		boolean validateflag=this.cc004Service.validateTmCardNo(strCurCompId,this.strTmkCardNo, strTmkEntryTypeBuf);
		if(validateflag==false)
			strMessage="该条码卡号不属于本门店或已被销售!";
		else
			this.strTmkEntryType=strTmkEntryTypeBuf.toString();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "validatePackageNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validatePackageNo()
	{
		this.strMessage="";
		//StringBuffer curUseMonthsBuf=new StringBuffer();
		//boolean validateflag=this.cc004Service.validatePackageNo(this.strCurCompId, this.strCurPackageNo, curUseMonthsBuf);
		mpackageinfo=this.cc004Service.validatePackageNo(strCurCompId, strCurPackageNo);
		if(mpackageinfo==null)
			strMessage="该套餐资料在系统中不存在!";
		else
		{
			if(mpackageinfo.getUsemonths()==null)
			{
				this.curUseMonths=24;
			}
			else
			{
				this.curUseMonths=Integer.parseInt(mpackageinfo.getUsemonths()+"");
			}
			lsDmpackageinfo=this.cc004Service.getDataTool().loadDmpackageinfo(strCurCompId,strCurPackageNo);
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
			if(this.cc004Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC004", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.cc004Service.post(this.curMaster, this.lsDetial);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc004Service.post_after(curMaster.getId().getSalecompid(), curMaster.getId().getSalebillid(),curMaster.getBarcodecardno(),CommonTool.FormatString(curMaster.getPackageNo()),CommonTool.FormatString(this.curMaster.getUsecardno()),CommonTool.FormatBigDecimal(this.curMaster.getUsecardpayamt()).doubleValue(),CommonTool.FormatInteger(curMaster.getSaleMonths()));
			curMaster=null;
			lsDetial=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	@Action(value = "validateUsecardno",  results = { 
			 @Result(name = "load_success", type = "json"),
        @Result(name = "load_failure", type = "json")	
     }) 
	public String validateUsecardno()
	{
		try
		{
			this.strMessage="";
			this.curCardinfo=this.cc004Service.getDataTool().loadCardinfobyCardNo(strCurCompId, this.strCardNo);
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
		try
		{
			this.curMaster=this.cc004Service.addMastRecord(this.strCurCompId);
			this.lsStaffinfo=this.cc004Service.getDataTool().loadEmpsByCompId(this.strCurCompId,2);
			this.lsMpackageinfo=this.cc004Service.loadMpackageinfo(strCurCompId);
			return SystemFinal.ADD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.ADD_FAILURE;
		}
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
	public CC004Service getCc004Service() {
		return cc004Service;
	}
    @JSON(serialize=false)
	public void setCc004Service(CC004Service cc004Service) {
		this.cc004Service = cc004Service;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	public int getSearchCorpsTypeKey() {
		return searchCorpsTypeKey;
	}
	public void setSearchCorpsTypeKey(int searchCorpsTypeKey) {
		this.searchCorpsTypeKey = searchCorpsTypeKey;
	}
	public String getSearchCorpsNoKey() {
		return searchCorpsNoKey;
	}
	public void setSearchCorpsNoKey(String searchCorpsNoKey) {
		this.searchCorpsNoKey = searchCorpsNoKey;
	}
	public int getSearchCorpsStateKey() {
		return searchCorpsStateKey;
	}
	public void setSearchCorpsStateKey(int searchCorpsStateKey) {
		this.searchCorpsStateKey = searchCorpsStateKey;
	}
	public String getSearchFromDateKey() {
		return searchFromDateKey;
	}
	public void setSearchFromDateKey(String searchFromDateKey) {
		this.searchFromDateKey = searchFromDateKey;
	}
	public String getSearchToDateDateKey() {
		return searchToDateDateKey;
	}
	public void setSearchToDateDateKey(String searchToDateDateKey) {
		this.searchToDateDateKey = searchToDateDateKey;
	}
	public List<Corpsbuyinfo> getLsCorpsbuyinfo() {
		return lsCorpsbuyinfo;
	}
	public void setLsCorpsbuyinfo(List<Corpsbuyinfo> lsCorpsbuyinfo) {
		this.lsCorpsbuyinfo = lsCorpsbuyinfo;
	}
	public List<Nointernalcardinfo> getLsNointernalcardinfo() {
		return lsNointernalcardinfo;
	}
	public void setLsNointernalcardinfo(
			List<Nointernalcardinfo> lsNointernalcardinfo) {
		this.lsNointernalcardinfo = lsNointernalcardinfo;
	}
	public List<Dnointernalcardinfo> getLsDnointernalcardinfo() {
		return lsDnointernalcardinfo;
	}
	public void setLsDnointernalcardinfo(
			List<Dnointernalcardinfo> lsDnointernalcardinfo) {
		this.lsDnointernalcardinfo = lsDnointernalcardinfo;
	}
	public String getSearchDyqNoKey() {
		return searchDyqNoKey;
	}
	public void setSearchDyqNoKey(String searchDyqNoKey) {
		this.searchDyqNoKey = searchDyqNoKey;
	}
	public int getSearchDyqStateKey() {
		return searchDyqStateKey;
	}
	public void setSearchDyqStateKey(int searchDyqStateKey) {
		this.searchDyqStateKey = searchDyqStateKey;
	}
	public String getStrDyqCardNo() {
		return strDyqCardNo;
	}
	public void setStrDyqCardNo(String strDyqCardNo) {
		this.strDyqCardNo = strDyqCardNo;
	}
	public int getSearchTmkTypeKey() {
		return searchTmkTypeKey;
	}
	public void setSearchTmkTypeKey(int searchTmkTypeKey) {
		this.searchTmkTypeKey = searchTmkTypeKey;
	}
	public String getSearchTmkNoKey() {
		return searchTmkNoKey;
	}
	public void setSearchTmkNoKey(String searchTmkNoKey) {
		this.searchTmkNoKey = searchTmkNoKey;
	}
	public int getSearchTmkStateKey() {
		return searchTmkStateKey;
	}
	public void setSearchTmkStateKey(int searchTmkStateKey) {
		this.searchTmkStateKey = searchTmkStateKey;
	}
	public String getStrTmkCardNo() {
		return strTmkCardNo;
	}
	public void setStrTmkCardNo(String strTmkCardNo) {
		this.strTmkCardNo = strTmkCardNo;
	}
	public Msalebarcodecardinfo getCurMaster() {
		return curMaster;
	}
	public void setCurMaster(Msalebarcodecardinfo curMaster) {
		this.curMaster = curMaster;
	}
	public List<Dsalebarcodecardinfo> getLsDetial() {
		return lsDetial;
	}
	public void setLsDetial(List<Dsalebarcodecardinfo> lsDetial) {
		this.lsDetial = lsDetial;
	}
	public List<Staffinfo> getLsStaffinfo() {
		return lsStaffinfo;
	}
	public void setLsStaffinfo(List<Staffinfo> lsStaffinfo) {
		this.lsStaffinfo = lsStaffinfo;
	}
	public List<Dmpackageinfo> getLsDmpackageinfo() {
		return lsDmpackageinfo;
	}
	public void setLsDmpackageinfo(List<Dmpackageinfo> lsDmpackageinfo) {
		this.lsDmpackageinfo = lsDmpackageinfo;
	}
	public List<Mpackageinfo> getLsMpackageinfo() {
		return lsMpackageinfo;
	}
	public void setLsMpackageinfo(List<Mpackageinfo> lsMpackageinfo) {
		this.lsMpackageinfo = lsMpackageinfo;
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
	
	
	
}
