package com.amani.action.InvoicingControl;

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

import com.amani.model.Compwarehouse;
import com.amani.model.Dcompallotmentinfo;
import com.amani.model.DcompallotmentinfoId;
import com.amani.model.Dgoodsallotmentinfo;
import com.amani.model.DgoodsallotmentinfoId;
import com.amani.model.Mgoodsallotmentinfo;
import com.amani.model.Staffinfo;
import com.amani.service.InvoicingControl.IC019Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic019")
public class IC019Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IC019Service ic019Service;
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
	private String strCompJsonParam;
	private String strCurCompId;
	private String strCurBillId;
	private String strCurDate;
	private String strCurGoodsId;
	private int barcodeCount;
	private String strIndexBar;
	private String strCurCompName;
	private String strCurEmpId;
	private String strCurEmpName;
	private List<Staffinfo> lsStaffinfo;
	private List<Mgoodsallotmentinfo> lsMgoodsallotmentinfo;
	private List<Dgoodsallotmentinfo> lsDgoodsallotmentinfo;
	private List<Dcompallotmentinfo> lsDcompallotmentinfo;
	private Mgoodsallotmentinfo curMgoodsallotmentinfo;
	private List<Compwarehouse> lsCompwarehouses;
	private String factcInsertware;//仓库下拉框
	private String strCardfrom;
	private String strCardend;
	private String strWareId;
	private String strCardType;
	private int cardIdLength;//系统参数中设定的会员卡序号长度
    private String  numberOfCardFilter;//卡需要过滤的数字
    private String strStartBarNo;
    private String strEndBarNo;
    private int handtype;  // 1 复合  2	取消复合
    private BigDecimal curgoodsstock;  //当前库存量
	public BigDecimal getCurgoodsstock() {
		return curgoodsstock;
	}
	public void setCurgoodsstock(BigDecimal curgoodsstock) {
		this.curgoodsstock = curgoodsstock;
	}
	public int getHandtype() {
		return handtype;
	}
	public void setHandtype(int handtype) {
		this.handtype = handtype;
	}
	public String getFactcInsertware() {
		return factcInsertware;
	}
	public void setFactcInsertware(String factcInsertware) {
		this.factcInsertware = factcInsertware;
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
		
		curMgoodsallotmentinfo.setAllotmentdate(CommonTool.setDateMask(curMgoodsallotmentinfo.getAllotmentdate()));
		curMgoodsallotmentinfo.setAllotmenttime(CommonTool.setTimeMask(curMgoodsallotmentinfo.getAllotmenttime(), 1));
		curMgoodsallotmentinfo.setOrderopationdate(CommonTool.getCurrDate());
		curMgoodsallotmentinfo.setOrderopationerid(CommonTool.getLoginInfo("USERID"));
		curMgoodsallotmentinfo.getId().setEntrybillid(this.ic019Service.getDataTool().loadBillIdByRule(curMgoodsallotmentinfo.getId().getEntrycompid(),"mgoodsallotmentinfo", "entrybillid", "SP012"));
		if(!CommonTool.FormatString(strJsonParam).equals("") && CommonTool.FormatString(strJsonParam).indexOf("]")>-1)
		{
			this.lsDgoodsallotmentinfo=this.ic019Service.getDataTool().loadDTOList(strJsonParam, Dgoodsallotmentinfo.class);
			if(lsDgoodsallotmentinfo!=null && lsDgoodsallotmentinfo.size()>0)
			{
				for(int i=0;i<lsDgoodsallotmentinfo.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodsallotmentinfo.get(i).getAllotmentgoodsno()).equals(""))
					{
						lsDgoodsallotmentinfo.get(i).setId(new DgoodsallotmentinfoId(curMgoodsallotmentinfo.getId().getEntrycompid(),curMgoodsallotmentinfo.getId().getEntrybillid(),i*1.0));
					}
					if(!CommonTool.FormatString(lsDgoodsallotmentinfo.get(i).getAllotmentgoodsno()).equals("")
					 && CommonTool.FormatString(lsDgoodsallotmentinfo.get(i).getHeadwareno()).equals(""))
					{
						lsDgoodsallotmentinfo.get(i).setHeadwareno(this.ic019Service.loadGoodsHeadWareNo(CommonTool.FormatString(lsDgoodsallotmentinfo.get(i).getAllotmentgoodsno())));
						
					}
				}
			}
		}
		if(!CommonTool.FormatString(strCompJsonParam).equals("") && CommonTool.FormatString(strCompJsonParam).indexOf("]")>-1 )
		{
			this.lsDcompallotmentinfo=this.ic019Service.getDataTool().loadDTOList(strCompJsonParam, Dcompallotmentinfo.class);
			if(lsDcompallotmentinfo!=null && lsDcompallotmentinfo.size()>0)
			{
				for(int i=0;i<lsDcompallotmentinfo.size();i++)
				{
					if(lsDcompallotmentinfo.get(i).getCheckFlag()==true)
					{
						lsDcompallotmentinfo.get(i).setId(new DcompallotmentinfoId(curMgoodsallotmentinfo.getId().getEntrycompid(),curMgoodsallotmentinfo.getId().getEntrybillid(),i*1.0));
					}
					else
					{
						lsDcompallotmentinfo.remove(i);
						i--;
					}
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
		
			return SystemFinal.DELETE_FAILURE;
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
			if(this.ic019Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC019", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.beforePost();
			if(flag==false)
			{
				return SystemFinal.POST_FAILURE;
			}
			if(CommonTool.FormatInteger(this.curMgoodsallotmentinfo.getAllotmenttype())==2 && (lsDcompallotmentinfo==null || lsDcompallotmentinfo.size()==0))
			{
				this.strMessage="请选择配发门店列表";
				return SystemFinal.POST_FAILURE;
			}
			flag=this.ic019Service.postInfo(this.curMgoodsallotmentinfo, this.lsDgoodsallotmentinfo,lsDcompallotmentinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.ic019Service.afterPost(this.curMgoodsallotmentinfo.getId().getEntrycompid(), this.curMgoodsallotmentinfo.getId().getEntrybillid(),this.curMgoodsallotmentinfo.getAllotmentcompid(),this.curMgoodsallotmentinfo.getRecevicestaffid(),this.curMgoodsallotmentinfo.getAllotmenttype(),lsDcompallotmentinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curMgoodsallotmentinfo=null;
			lsDgoodsallotmentinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}

	
	@Action(value = "searchOrderBill",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String searchOrderBill()
	{
		try
		{
			this.lsMgoodsallotmentinfo=this.ic019Service.loadMgoodsorderinfo(CommonTool.getLoginInfo("COMPID"),CommonTool.setDateMask(this.strCurDate),this.strCurBillId,30, 0);
			if(lsMgoodsallotmentinfo!=null && lsMgoodsallotmentinfo.size()>0)
			{
				for(int i=0;i<lsMgoodsallotmentinfo.size();i++)
				{
					lsMgoodsallotmentinfo.get(i).setBentrycompid(lsMgoodsallotmentinfo.get(i).getId().getEntrycompid());
					lsMgoodsallotmentinfo.get(i).setBentrybillid(lsMgoodsallotmentinfo.get(i).getId().getEntrybillid());
					lsMgoodsallotmentinfo.get(i).setAllotmentdate(CommonTool.getDateMask(lsMgoodsallotmentinfo.get(i).getAllotmentdate()));
					lsMgoodsallotmentinfo.get(i).setAllotmenttime(CommonTool.getTimeMask(lsMgoodsallotmentinfo.get(i).getAllotmenttime(),1));
				}
				
			}
			else
			{
				this.curMgoodsallotmentinfo=this.ic019Service.addMastRecord(CommonTool.getLoginInfo("COMPID"));
				curMgoodsallotmentinfo.setBentrycompname(this.ic019Service.getDataTool().loadCompNameById(curMgoodsallotmentinfo.getBentrycompid()));
				this.lsMgoodsallotmentinfo=new ArrayList();
				this.lsMgoodsallotmentinfo.add(curMgoodsallotmentinfo);
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		} 
	}
	
	
	@Action(value = "loadDorderInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadDorderInfo()
	{
		try
		{
			StringBuffer validatemsg=null;
			this.curMgoodsallotmentinfo=this.ic019Service.loadMgoodsorderinfoById(this.strCurCompId, this.strCurBillId);
			if(curMgoodsallotmentinfo==null || curMgoodsallotmentinfo.getId()==null)
				curMgoodsallotmentinfo=this.ic019Service.addMastRecord(this.strCurCompId);
			else
			{
				curMgoodsallotmentinfo.setBentrycompname(this.ic019Service.getDataTool().loadCompNameById(curMgoodsallotmentinfo.getBentrycompid()));
				if(CommonTool.FormatInteger(curMgoodsallotmentinfo.getAllotmenttype())==1)
				{
					curMgoodsallotmentinfo.setAllotmentcompname(this.ic019Service.getDataTool().loadCompNameById(curMgoodsallotmentinfo.getAllotmentcompid()));
					validatemsg=new StringBuffer();
					curMgoodsallotmentinfo.setRecevicestaffname(this.ic019Service.getDataTool().loadEmpNameById(this.curMgoodsallotmentinfo.getAllotmentcompid(), this.curMgoodsallotmentinfo.getRecevicestaffid(),validatemsg));
					validatemsg=null;
				}
			}
			validatemsg=new StringBuffer();
			curMgoodsallotmentinfo.setAllotmenttaffanme(this.ic019Service.getDataTool().loadEmpNameById(this.curMgoodsallotmentinfo.getBentrycompid(), this.curMgoodsallotmentinfo.getAllotmenttaffid(),validatemsg));
			validatemsg=null;
			this.lsDgoodsallotmentinfo=this.ic019Service.loadDgoodsorderinfo(this.strCurCompId, this.strCurBillId);
			this.lsDcompallotmentinfo=this.ic019Service.loadDcompallotmentinfo(this.strCurCompId, this.strCurBillId);
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
			this.curMgoodsallotmentinfo=this.ic019Service.addMastRecord(CommonTool.getLoginInfo("COMPID"));
			curMgoodsallotmentinfo.setBentrycompname(this.ic019Service.getDataTool().loadCompNameById(CommonTool.getLoginInfo("COMPID")));
			return SystemFinal.ADD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.ADD_FAILURE;
		}
	}
	
	@Action(value = "validateInserper",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateInserper()
	{
		try
		{
			StringBuffer validateMsg=new StringBuffer();
			this.strCurEmpName=this.ic019Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	@Action(value = "validateComp",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateComp()
	{
		try
		{
			this.strCurCompName=this.ic019Service.getDataTool().loadCompNameById(strCurCompId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	
	@Action(value = "loadCurStock",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadCurStock()
	{
		try
		{
			double curOnlineStock=this.ic019Service.loadOnloadGoodstock(strCurCompId,strCurGoodsId);
			this.curgoodsstock=this.ic019Service.loadCurStock(this.strCurCompId, this.strCurDate, strCurGoodsId, strWareId);
			curgoodsstock=CommonTool.FormatBigDecimal(new BigDecimal(curgoodsstock.doubleValue()-curOnlineStock));
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
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
	@JSON(serialize=false)
	public IC019Service getIc019Service() {
		return ic019Service;
	}
	@JSON(serialize=false)
	public void setIc019Service(IC019Service ic019Service) {
		this.ic019Service = ic019Service;
	}
	

	public String getStrCurGoodsId() {
		return strCurGoodsId;
	}
	public void setStrCurGoodsId(String strCurGoodsId) {
		this.strCurGoodsId = strCurGoodsId;
	}

	public int getBarcodeCount() {
		return barcodeCount;
	}
	public void setBarcodeCount(int barcodeCount) {
		this.barcodeCount = barcodeCount;
	}
	public String getStrStartBarNo() {
		return strStartBarNo;
	}
	public void setStrStartBarNo(String strStartBarNo) {
		this.strStartBarNo = strStartBarNo;
	}
	public String getStrEndBarNo() {
		return strEndBarNo;
	}
	public void setStrEndBarNo(String strEndBarNo) {
		this.strEndBarNo = strEndBarNo;
	}
	public String getStrIndexBar() {
		return strIndexBar;
	}
	public void setStrIndexBar(String strIndexBar) {
		this.strIndexBar = strIndexBar;
	}
	public String getStrCurDate() {
		return strCurDate;
	}
	public void setStrCurDate(String strCurDate) {
		this.strCurDate = strCurDate;
	}
	public List<Mgoodsallotmentinfo> getLsMgoodsallotmentinfo() {
		return lsMgoodsallotmentinfo;
	}
	public void setLsMgoodsallotmentinfo(
			List<Mgoodsallotmentinfo> lsMgoodsallotmentinfo) {
		this.lsMgoodsallotmentinfo = lsMgoodsallotmentinfo;
	}
	public List<Dgoodsallotmentinfo> getLsDgoodsallotmentinfo() {
		return lsDgoodsallotmentinfo;
	}
	public void setLsDgoodsallotmentinfo(
			List<Dgoodsallotmentinfo> lsDgoodsallotmentinfo) {
		this.lsDgoodsallotmentinfo = lsDgoodsallotmentinfo;
	}
	public Mgoodsallotmentinfo getCurMgoodsallotmentinfo() {
		return curMgoodsallotmentinfo;
	}
	public void setCurMgoodsallotmentinfo(Mgoodsallotmentinfo curMgoodsallotmentinfo) {
		this.curMgoodsallotmentinfo = curMgoodsallotmentinfo;
	}
	public String getStrCompJsonParam() {
		return strCompJsonParam;
	}
	public void setStrCompJsonParam(String strCompJsonParam) {
		this.strCompJsonParam = strCompJsonParam;
	}
	public List<Dcompallotmentinfo> getLsDcompallotmentinfo() {
		return lsDcompallotmentinfo;
	}
	public void setLsDcompallotmentinfo(
			List<Dcompallotmentinfo> lsDcompallotmentinfo) {
		this.lsDcompallotmentinfo = lsDcompallotmentinfo;
	}


}
