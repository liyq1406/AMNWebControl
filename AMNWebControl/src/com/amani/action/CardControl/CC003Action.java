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
import com.amani.model.Cardstock;
import com.amani.model.Cardtypeinfo;
import com.amani.model.Compwarehouse;
import com.amani.model.Dcardallotment;
import com.amani.model.DcardallotmentId;
import com.amani.model.Mcardapponline;

import com.amani.model.Mcardallotment;
import com.amani.model.McardallotmentId;

import com.amani.model.Staffinfo;
import com.amani.service.CardControl.CC003Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc003")
public class CC003Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CC003Service cc003Service;
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
	private String strJsonParam;
	private String strCurCompId;
	private String strCurBillId;
	private String strCurCompName;
	private String strCurEmpId;
	private String strCurEmpName;
	private List<Staffinfo> lsStaffinfo;
	private List<Mcardallotment> lsMcardallotments;
	private List<Dcardallotment> lsDcardallotments;
	private Mcardallotment curMcardallotment;
	private List<Compwarehouse> lsCompwarehouses;
	private List<Cardtypeinfo> lsCardtypeinfos;
	private List<Mcardapponline> lsStoreAppBills;
	public List<Cardstock> lsStoreCanAllotCards;
	private String factCallotwareid;//仓库下拉框
	private String strCardfrom;
	private String strCardend;
	private String strWareId;
	private String strCardType;
	private int cardIdLength;//系统参数中设定的会员卡序号长度
    private String  numberOfCardFilter;//卡需要过滤的数字
	
    private String strAllotCompId;
    private String strAllotCardType;
	public String getStrAllotCompId() {
		return strAllotCompId;
	}
	public void setStrAllotCompId(String strAllotCompId) {
		this.strAllotCompId = strAllotCompId;
	}
	public String getStrAllotCardType() {
		return strAllotCardType;
	}
	public void setStrAllotCardType(String strAllotCardType) {
		this.strAllotCardType = strAllotCardType;
	}
	public String getFactCallotwareid() {
		return factCallotwareid;
	}
	public void setFactCallotwareid(String factCallotwareid) {
		this.factCallotwareid = factCallotwareid;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}

	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
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
		return false;
	}

	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean beforePost() {
		if(!CommonTool.FormatString(this.factCallotwareid).equals(""))
		{
			this.curMcardallotment.setCallotwareid(factCallotwareid);
		}
		curMcardallotment.setCallotdate(CommonTool.setDateMask(curMcardallotment.getCallotdate()));
		curMcardallotment.setCallotopationdate(CommonTool.setDateMask(curMcardallotment.getCallotopationdate()));
		curMcardallotment.setCheckoutdate(CommonTool.setDateMask(curMcardallotment.getCheckoutdate()));
		curMcardallotment.setCallottime(CommonTool.setTimeMask(curMcardallotment.getCallottime(), 1));
		curMcardallotment.getId().setCallotbillid(this.cc003Service.getDataTool().loadBillIdByRule(curMcardallotment.getId().getCallotcompid(),"mcardallotment", "callotbillid", "SP012"));
		this.lsDcardallotments=this.cc003Service.getDataTool().loadDTOList(strJsonParam, Dcardallotment.class);
		if(lsDcardallotments!=null && lsDcardallotments.size()>0)
		{
			for(int i=0;i<lsDcardallotments.size();i++)
			{
				if(!CommonTool.FormatString(lsDcardallotments.get(i).getCardtypeid()).equals(""))
				{
					lsDcardallotments.get(i).setId(new DcardallotmentId(curMcardallotment.getId().getCallotcompid(),curMcardallotment.getId().getCallotbillid(),i*1.0));
				}
				else
				{
					lsDcardallotments.remove(i);
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
	
	@Action(value = "delete",  results = { 
			 @Result(name = "delete_success", type = "json"),
           @Result(name = "delete_failure", type = "json")	
        }) 
	@Override
	public String delete()
	{
		try
		{
			this.strMessage="";
			if(this.cc003Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC003", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			this.curMcardallotment = new Mcardallotment();
			this.curMcardallotment.setId(new McardallotmentId(this.strCurCompId,this.strCurBillId));
			this.cc003Service.delete(curMcardallotment);
			curMcardallotment=null;
			return SystemFinal.DELETE_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.DELETE_FAILURE;
		}
	}

	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return true;
	}
    
	@Override
	protected boolean postActive() {
	
		return true;
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
			if(this.cc003Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC003", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.cc003Service.post(this.curMcardallotment, this.lsDcardallotments);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc003Service.postCardIntoStore(curMcardallotment.getId().getCallotcompid(), curMcardallotment.getId().getCallotbillid(), curMcardallotment.getCappcompid(), curMcardallotment.getCappbillid(), 1);
			if(flag==false)
			{
				this.strMessage="系统生成卡配发历史有误,请确认!";
				return SystemFinal.POST_FAILURE;
			}
			curMcardallotment=null;
			lsDcardallotments=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
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
			this.curMcardallotment=this.cc003Service.addMastRecord(CommonTool.getLoginInfo("COMPID"));
			return SystemFinal.ADD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.ADD_FAILURE;
		}
	}
	
	
	
	@Action(value = "loadCardAllotInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadCardAllotInfo()
	{
		try
		{
			this.lsMcardallotments=this.cc003Service.loadMasterDateById("", "");
			if(lsMcardallotments!=null && lsMcardallotments.size()>0)
			{
				this.curMcardallotment=this.lsMcardallotments.get(0);
				curMcardallotment.setCappcompidText(this.cc003Service.getDataTool().loadCompNameById(curMcardallotment.getCappcompid()));
				StringBuffer validatemsg=new StringBuffer();
				curMcardallotment.setCallotempText(this.cc003Service.getDataTool().loadEmpNameById(this.curMcardallotment.getBcallotcompid(), this.curMcardallotment.getCallotempid(),validatemsg));
				validatemsg=new StringBuffer();
				curMcardallotment.setRecevieempText(this.cc003Service.getDataTool().loadEmpNameById(this.curMcardallotment.getCappcompid(), this.curMcardallotment.getRecevieempid(),validatemsg));
				validatemsg=null;
			}
			else
			{
				this.curMcardallotment=this.cc003Service.addMastRecord(CommonTool.getLoginInfo("COMPID"));
				this.lsMcardallotments=new ArrayList();
				this.lsMcardallotments.add(curMcardallotment);
			}
			this.lsDcardallotments=this.cc003Service.loadDetialById(this.curMcardallotment.getBcallotcompid(), this.curMcardallotment.getBcallotbillid());
			this.lsCompwarehouses=this.cc003Service.getDataTool().loadCompWareById(this.curMcardallotment.getBcallotcompid());
			this.lsCardtypeinfos=this.cc003Service.getDataTool().loadCardType(this.curMcardallotment.getBcallotcompid());
			double cardlen=Double.parseDouble(CommonTool.FormatString(this.cc003Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP019")));
			if(cardlen==0)
			{
				this.cardIdLength=0;
			}
			else
			{
				this.cardIdLength=((Double)cardlen).intValue();
			}
			//获得系统中设定的要过滤的卡号尾数
			this.numberOfCardFilter=CommonTool.FormatString(this.cc003Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP020"));
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "loadDcardallotment",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadDcardallotment()
	{
		try
		{
			this.curMcardallotment=this.cc003Service.loadMcardallotmentByBillId(this.strCurCompId, this.strCurBillId);
			curMcardallotment.setCappcompidText(this.cc003Service.getDataTool().loadCompNameById(curMcardallotment.getCappcompid()));
			StringBuffer validatemsg=new StringBuffer();
			curMcardallotment.setCallotempText(this.cc003Service.getDataTool().loadEmpNameById(this.curMcardallotment.getBcallotcompid(), this.curMcardallotment.getCallotempid(),validatemsg));
			validatemsg=new StringBuffer();
			curMcardallotment.setRecevieempText(this.cc003Service.getDataTool().loadEmpNameById(this.curMcardallotment.getCappcompid(), this.curMcardallotment.getRecevieempid(),validatemsg));
			validatemsg=null;
			this.lsDcardallotments=this.cc003Service.loadDetialById(this.strCurCompId, this.strCurBillId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "validateCallotempid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateCallotempid()
	{
		try
		{
			StringBuffer validateMsg=new StringBuffer();
			this.strCurEmpName=this.cc003Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "validateRecevieempid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateRecevieempid()
	{
		try
		{
			StringBuffer validateMsg=new StringBuffer();
			this.strCurEmpName=this.cc003Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "validateCappcompid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateCappcompid()
	{
		try
		{
			StringBuffer validateMsg=new StringBuffer();
			this.strCurCompName=this.cc003Service.getDataTool().loadCompName(CommonTool.getLoginInfo("COMPID"), strCurCompId, 1, validateMsg);
			lsStoreAppBills=this.cc003Service.loadMcardapponlineByCompId(strCurCompId);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "validateCappbillid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateCappbillid()
	{
		try
		{
			this.lsDcardallotments=this.cc003Service.loadDetialByIdByAppBill(this.strCurCompId, this.strCurBillId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	
	@Action(value = "showCanAllotInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String showCanAllotInfo()
	{
		try
		{
			this.lsStoreCanAllotCards=this.cc003Service.loadCanAllotCardInfoBill(this.strCurCompId, this.strCardType,this.strWareId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "loadCardstockInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadCardstockInfo()
	{
		try
		{
			this.lsStoreCanAllotCards=this.cc003Service.loadCardstockInfo();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "searchCardAllotInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String searchCardAllotInfo()
	{
		try
		{
			this.lsDcardallotments=this.cc003Service.loadDetialBySearch(this.strAllotCompId,this.strAllotCardType);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@JSON(serialize=false)
	public CC003Service getCc003Service() {
		return cc003Service;
	}
	@JSON(serialize=false)
	public void setCc003Service(CC003Service cc003Service) {
		this.cc003Service = cc003Service;
	}
	public String getStrCurBillId() {
		return strCurBillId;
	}
	public void setStrCurBillId(String strCurBillId) {
		this.strCurBillId = strCurBillId;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
	}
	public String getStrCurEmpId() {
		return strCurEmpId;
	}
	public void setStrCurEmpId(String strCurEmpId) {
		this.strCurEmpId = strCurEmpId;
	}
	public String getStrCurEmpName() {
		return strCurEmpName;
	}
	public void setStrCurEmpName(String strCurEmpName) {
		this.strCurEmpName = strCurEmpName;
	}
	
	public List<Compwarehouse> getLsCompwarehouses() {
		return lsCompwarehouses;
	}
	public void setLsCompwarehouses(List<Compwarehouse> lsCompwarehouses) {
		this.lsCompwarehouses = lsCompwarehouses;
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
	public String getStrCardfrom() {
		return strCardfrom;
	}
	public void setStrCardfrom(String strCardfrom) {
		this.strCardfrom = strCardfrom;
	}
	public String getStrCardend() {
		return strCardend;
	}
	public void setStrCardend(String strCardend) {
		this.strCardend = strCardend;
	}
	public String getStrWareId() {
		return strWareId;
	}
	public void setStrWareId(String strWareId) {
		this.strWareId = strWareId;
	}
	public String getStrCardType() {
		return strCardType;
	}
	public void setStrCardType(String strCardType) {
		this.strCardType = strCardType;
	}
	public int getCardIdLength() {
		return cardIdLength;
	}
	public void setCardIdLength(int cardIdLength) {
		this.cardIdLength = cardIdLength;
	}
	public String getNumberOfCardFilter() {
		return numberOfCardFilter;
	}
	public void setNumberOfCardFilter(String numberOfCardFilter) {
		this.numberOfCardFilter = numberOfCardFilter;
	}
	public List<Mcardallotment> getLsMcardallotments() {
		return lsMcardallotments;
	}
	public void setLsMcardallotments(List<Mcardallotment> lsMcardallotments) {
		this.lsMcardallotments = lsMcardallotments;
	}
	public List<Dcardallotment> getLsDcardallotments() {
		return lsDcardallotments;
	}
	public void setLsDcardallotments(List<Dcardallotment> lsDcardallotments) {
		this.lsDcardallotments = lsDcardallotments;
	}
	public Mcardallotment getCurMcardallotment() {
		return curMcardallotment;
	}
	public void setCurMcardallotment(Mcardallotment curMcardallotment) {
		this.curMcardallotment = curMcardallotment;
	}
	public List<Mcardapponline> getLsStoreAppBills() {
		return lsStoreAppBills;
	}
	public void setLsStoreAppBills(List<Mcardapponline> lsStoreAppBills) {
		this.lsStoreAppBills = lsStoreAppBills;
	}
	public List<Cardstock> getLsStoreCanAllotCards() {
		return lsStoreCanAllotCards;
	}
	public void setLsStoreCanAllotCards(List<Cardstock> lsStoreCanAllotCards) {
		this.lsStoreCanAllotCards = lsStoreCanAllotCards;
	}


}
