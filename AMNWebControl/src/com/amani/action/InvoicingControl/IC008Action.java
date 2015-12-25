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
import com.amani.model.Dgoodsorderinfo;
import com.amani.model.DgoodsorderinfoId;
import com.amani.model.Goodsinfo;
import com.amani.model.Mgoodsorderinfo;
import com.amani.model.MgoodsorderinfoId;
import com.amani.model.Staffinfo;
import com.amani.service.InvoicingControl.IC008Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic008")
public class IC008Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IC008Service ic008Service;
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
	private String strCurDate;
	private String strCurGoodsId;
	private int barcodeCount;
	private String strIndexBar;
	private String strCurCompName;
	private String strCurEmpId;
	private String strCurEmpName;
	private List<Staffinfo> lsStaffinfo;
	private List<Mgoodsorderinfo> lsMgoodsorderinfo;
	private List<Dgoodsorderinfo> lsDgoodsorderinfo;
	private Mgoodsorderinfo curMgoodsorderinfo;
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
		if(!CommonTool.FormatString(this.factcInsertware).equals(""))
		{
			this.curMgoodsorderinfo.setStorewareid(factcInsertware);
		}
		curMgoodsorderinfo.setOrderdate(CommonTool.setDateMask(curMgoodsorderinfo.getOrderdate()));
		curMgoodsorderinfo.setDownorderdate(CommonTool.setDateMask(curMgoodsorderinfo.getDownorderdate()));
		curMgoodsorderinfo.setOrdertime(CommonTool.setTimeMask(curMgoodsorderinfo.getOrdertime(), 1));
		curMgoodsorderinfo.setDownordertime(CommonTool.setTimeMask(curMgoodsorderinfo.getDownordertime(), 1));
		curMgoodsorderinfo.setOrderopationdate(CommonTool.getCurrDate());
		curMgoodsorderinfo.setOrderstate(1);
		curMgoodsorderinfo.setOrderopationerid(CommonTool.getLoginInfo("USERID"));
		curMgoodsorderinfo.getId().setOrderbillid(this.ic008Service.getDataTool().loadBillIdByRule(curMgoodsorderinfo.getId().getOrdercompid(),"mgoodsorderinfo", "orderbillid", "SP012"));
		double totalOrderAmt=0;
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDgoodsorderinfo=this.ic008Service.getDataTool().loadDTOList(strJsonParam, Dgoodsorderinfo.class);
			if(lsDgoodsorderinfo!=null && lsDgoodsorderinfo.size()>0)
			{
				for(int i=0;i<lsDgoodsorderinfo.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodsorderinfo.get(i).getOrdergoodsno()).equals(""))
					{
						lsDgoodsorderinfo.get(i).setId(new DgoodsorderinfoId(curMgoodsorderinfo.getId().getOrdercompid(),curMgoodsorderinfo.getId().getOrderbillid(),i*1.0));
						totalOrderAmt=totalOrderAmt+CommonTool.FormatBigDecimal(lsDgoodsorderinfo.get(i).getOrdergoodsamt()).doubleValue();
					}
					//如果在产品资料表中（goodsappsource）产品来源为 1 供应商时，则将中心仓库编号更新供应商编号（headwareno）
					if(lsDgoodsorderinfo.get(i).getGoodssource() != null && lsDgoodsorderinfo.get(i).getGoodssource() == 1){
						Goodsinfo goods = this.ic008Service.getDataTool().loadGoodsInfo(lsDgoodsorderinfo.get(i).getId().getOrdercompid(), lsDgoodsorderinfo.get(i).getOrdergoodsno());
						lsDgoodsorderinfo.get(i).setSupplierno(goods.getGoodssupplier());
						lsDgoodsorderinfo.get(i).setHeadwareno(goods.getGoodssupplier());
					}
					if(!CommonTool.FormatString(lsDgoodsorderinfo.get(i).getOrdergoodsno()).equals("")
					 && CommonTool.FormatString(lsDgoodsorderinfo.get(i).getHeadwareno()).equals(""))
					{
						lsDgoodsorderinfo.get(i).setHeadwareno(this.ic008Service.loadGoodsHeadWareNo(CommonTool.FormatString(lsDgoodsorderinfo.get(i).getOrdergoodsno())));
						
					}
					lsDgoodsorderinfo.get(i).setOrdergoodstype(1);
				}
				if(curMgoodsorderinfo.getOrderbilltype()==2)
				{
					double standOrderamt=Double.parseDouble(this.ic008Service.getDataTool().loadSysParam(curMgoodsorderinfo.getId().getOrdercompid(), "SP029"));
					double beforeOrderAmt=this.ic008Service.loadStaffGoodsAmt(curMgoodsorderinfo.getId().getOrdercompid());
					if(totalOrderAmt+beforeOrderAmt>standOrderamt)
					{
						this.strMessage=" 员工购买产品已经超过系统设定上限,请确认!";
						return false;
					}
				}
			}
		}
		return true;
	}

	protected boolean beforeComfirm() {
		if(!CommonTool.FormatString(this.factcInsertware).equals(""))
		{
			this.curMgoodsorderinfo.setStorewareid(factcInsertware);
		}
		curMgoodsorderinfo.setOrderdate(CommonTool.setDateMask(curMgoodsorderinfo.getOrderdate()));
		curMgoodsorderinfo.setDownorderdate(CommonTool.setDateMask(curMgoodsorderinfo.getDownorderdate()));
		curMgoodsorderinfo.setOrdertime(CommonTool.setTimeMask(curMgoodsorderinfo.getOrdertime(), 1));
		curMgoodsorderinfo.setDownordertime(CommonTool.setTimeMask(curMgoodsorderinfo.getDownordertime(), 1));
		curMgoodsorderinfo.setOrderopationdate(CommonTool.getCurrDate());
		curMgoodsorderinfo.setOrderstate(1);
		curMgoodsorderinfo.setOrderopationerid(CommonTool.getLoginInfo("USERID"));
		
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			double totalOrderAmt=0;
			this.lsDgoodsorderinfo=this.ic008Service.getDataTool().loadDTOList(strJsonParam, Dgoodsorderinfo.class);
			if(lsDgoodsorderinfo!=null && lsDgoodsorderinfo.size()>0)
			{
				for(int i=0;i<lsDgoodsorderinfo.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodsorderinfo.get(i).getOrdergoodsno()).equals(""))
					{
						lsDgoodsorderinfo.get(i).setId(new DgoodsorderinfoId(curMgoodsorderinfo.getId().getOrdercompid(),curMgoodsorderinfo.getId().getOrderbillid(),i*1.0));
						totalOrderAmt=totalOrderAmt+CommonTool.FormatBigDecimal(lsDgoodsorderinfo.get(i).getOrdergoodsamt()).doubleValue();
					}
					//如果在产品资料表中（goodsappsource）产品来源为 1 供应商时，则将中心仓库编号更新供应商编号（headwareno）
					if(lsDgoodsorderinfo.get(i).getGoodssource() != null && lsDgoodsorderinfo.get(i).getGoodssource() == 1){
						Goodsinfo goods = this.ic008Service.getDataTool().loadGoodsInfo(lsDgoodsorderinfo.get(i).getId().getOrdercompid(), lsDgoodsorderinfo.get(i).getOrdergoodsno());
						lsDgoodsorderinfo.get(i).setSupplierno(goods.getGoodssupplier());
						lsDgoodsorderinfo.get(i).setHeadwareno(goods.getGoodssupplier());
					}
					if(!CommonTool.FormatString(lsDgoodsorderinfo.get(i).getOrdergoodsno()).equals("")
							 && CommonTool.FormatString(lsDgoodsorderinfo.get(i).getHeadwareno()).equals(""))
					{
						lsDgoodsorderinfo.get(i).setHeadwareno(this.ic008Service.loadGoodsHeadWareNo(CommonTool.FormatString(lsDgoodsorderinfo.get(i).getOrdergoodsno())));
						
					}
					lsDgoodsorderinfo.get(i).setOrdergoodstype(1);
				}
				if(curMgoodsorderinfo.getOrderbilltype()==2)
				{
					double standOrderamt=Double.parseDouble(this.ic008Service.getDataTool().loadSysParam(curMgoodsorderinfo.getId().getOrdercompid(), "SP029"));
					double beforeOrderAmt=this.ic008Service.loadStaffGoodsAmt(curMgoodsorderinfo.getId().getOrdercompid());
					if(totalOrderAmt+beforeOrderAmt>standOrderamt)
					{
						this.strMessage=" 员工购买产品已经超过系统设定上限,请确认!";
						return false;
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
		try
		{
			this.strMessage="";
			if(this.ic008Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC008", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			Mgoodsorderinfo obj=new Mgoodsorderinfo();
			obj.setId(new MgoodsorderinfoId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.ic008Service.delete(obj);
			obj=null;
			if(flag==false)
			{
				this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
				return SystemFinal.DELETE_FAILURE;
			}
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
			if(this.ic008Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC008", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.beforePost();
			if(flag==false)
			{
				return SystemFinal.POST_FAILURE;
			}
			flag=this.ic008Service.postInfo(this.curMgoodsorderinfo, this.lsDgoodsorderinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curMgoodsorderinfo=null;
			lsDgoodsorderinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "loadGoodsOrderInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadGoodsOrderInfo()
	{
		try
		{
			this.lsMgoodsorderinfo=this.ic008Service.loadMgoodsorderinfo(this.strCurCompId,CommonTool.getCurrDate(),"",30, 0);
			if(lsMgoodsorderinfo!=null && lsMgoodsorderinfo.size()>0)
			{
				for(int i=0;i<lsMgoodsorderinfo.size();i++)
				{
					lsMgoodsorderinfo.get(i).setBordercompid(lsMgoodsorderinfo.get(i).getId().getOrdercompid());
					lsMgoodsorderinfo.get(i).setBorderbillid(lsMgoodsorderinfo.get(i).getId().getOrderbillid());
					lsMgoodsorderinfo.get(i).setOrderdate(CommonTool.getDateMask(lsMgoodsorderinfo.get(i).getOrderdate()));
					lsMgoodsorderinfo.get(i).setDownorderdate(CommonTool.getDateMask(lsMgoodsorderinfo.get(i).getDownorderdate()));
					lsMgoodsorderinfo.get(i).setDownordertime(CommonTool.getTimeMask(lsMgoodsorderinfo.get(i).getDownordertime(),1));
					lsMgoodsorderinfo.get(i).setOrdertime(CommonTool.getTimeMask(lsMgoodsorderinfo.get(i).getOrdertime(),1));
				}
				
			}
			else
			{
				this.curMgoodsorderinfo=this.ic008Service.addMastRecord(this.strCurCompId);
				curMgoodsorderinfo.setBordercompname(this.ic008Service.getDataTool().loadCompNameById(curMgoodsorderinfo.getBordercompid()));
				this.lsMgoodsorderinfo=new ArrayList();
				this.lsMgoodsorderinfo.add(curMgoodsorderinfo);
			}
			this.lsCompwarehouses=this.ic008Service.getDataTool().loadCompWareById(this.strCurCompId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		} 
	}
	
	@Action(value = "searchOrderBill",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String searchOrderBill()
	{
		try
		{
			this.lsMgoodsorderinfo=this.ic008Service.loadMgoodsorderinfo(this.strCurCompId,CommonTool.setDateMask(this.strCurDate),this.strCurBillId,30, 0);
			if(lsMgoodsorderinfo!=null && lsMgoodsorderinfo.size()>0)
			{
				for(int i=0;i<lsMgoodsorderinfo.size();i++)
				{
					lsMgoodsorderinfo.get(i).setBordercompid(lsMgoodsorderinfo.get(i).getId().getOrdercompid());
					lsMgoodsorderinfo.get(i).setBorderbillid(lsMgoodsorderinfo.get(i).getId().getOrderbillid());
					lsMgoodsorderinfo.get(i).setOrderdate(CommonTool.getDateMask(lsMgoodsorderinfo.get(i).getOrderdate()));
					lsMgoodsorderinfo.get(i).setDownorderdate(CommonTool.getDateMask(lsMgoodsorderinfo.get(i).getDownorderdate()));
					lsMgoodsorderinfo.get(i).setDownordertime(CommonTool.getTimeMask(lsMgoodsorderinfo.get(i).getDownordertime(),1));
					lsMgoodsorderinfo.get(i).setOrdertime(CommonTool.getTimeMask(lsMgoodsorderinfo.get(i).getOrdertime(),1));
				}
				
			}
			else
			{
				this.curMgoodsorderinfo=this.ic008Service.addMastRecord(this.strCurCompId);
				curMgoodsorderinfo.setBordercompname(this.ic008Service.getDataTool().loadCompNameById(curMgoodsorderinfo.getBordercompid()));
				this.lsMgoodsorderinfo=new ArrayList();
				this.lsMgoodsorderinfo.add(curMgoodsorderinfo);
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
			this.curMgoodsorderinfo=this.ic008Service.loadMgoodsorderinfoById(this.strCurCompId, this.strCurBillId);
			if(curMgoodsorderinfo==null || curMgoodsorderinfo.getId()==null)
				curMgoodsorderinfo=this.ic008Service.addMastRecord(this.strCurCompId);
			curMgoodsorderinfo.setBordercompname(this.ic008Service.getDataTool().loadCompNameById(curMgoodsorderinfo.getBordercompid()));
			StringBuffer validatemsg=new StringBuffer();
			curMgoodsorderinfo.setOrderstaffname(this.ic008Service.getDataTool().loadEmpNameById(this.curMgoodsorderinfo.getBordercompid(), this.curMgoodsorderinfo.getOrderstaffid(),validatemsg));
			validatemsg=null;
			this.lsDgoodsorderinfo=this.ic008Service.loadDgoodsorderinfo(this.strCurCompId, this.strCurBillId);
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
			this.curMgoodsorderinfo=this.ic008Service.addMastRecord(this.strCurCompId);
			curMgoodsorderinfo.setBordercompname(this.ic008Service.getDataTool().loadCompNameById(curMgoodsorderinfo.getBordercompid()));
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
			this.strCurEmpName=this.ic008Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
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
			double curOnlineStock=this.ic008Service.loadOnloadGoodstock(strCurCompId,strCurGoodsId);
			this.curgoodsstock=this.ic008Service.loadCurStock(this.strCurCompId, this.strCurDate, strCurGoodsId, strWareId);
			curgoodsstock=CommonTool.FormatBigDecimal(new BigDecimal(curgoodsstock.doubleValue()-curOnlineStock));
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	
	@Action(value = "handconfirmInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String handconfirmInfo()
	{
		try
		{
			this.strMessage="";
			if(this.ic008Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC008", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.beforeComfirm();
			if(flag==false)
			{
				return SystemFinal.POST_FAILURE;
			}
			flag=this.ic008Service.postInfo(this.curMgoodsorderinfo, this.lsDgoodsorderinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curMgoodsorderinfo=null;
			lsDgoodsorderinfo=null;
			return SystemFinal.POST_SUCCESS ;
		   }
		catch(Exception ex)
		{
		     ex.printStackTrace();
		     return SystemFinal.POST_SUCCESS ;
		}
	}
	
	@Action(value = "handcancelInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String handcancelInfo()
	{
		try
		{
			this.strMessage="";
			if(this.ic008Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC008", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			Mgoodsorderinfo mgoodsorderinfo=this.ic008Service.loadMgoodsorderinfoById(this.strCurCompId, this.strCurBillId);
			if(mgoodsorderinfo.getOrderstate()>=2){//判断是否已总部下单，不能取消复合
				this.strMessage="总部已下单，不能取消复合！";
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.ic008Service.handcancelInfo(this.strCurCompId, this.strCurBillId);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
	
			return SystemFinal.POST_SUCCESS ;
		   }
		catch(Exception ex)
		{
		     ex.printStackTrace();
		     return SystemFinal.POST_SUCCESS ;
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
	public IC008Service getIc008Service() {
		return ic008Service;
	}
	@JSON(serialize=false)
	public void setIc008Service(IC008Service ic008Service) {
		this.ic008Service = ic008Service;
	}
	

	
	public List<Mgoodsorderinfo> getLsMgoodsorderinfo() {
		return lsMgoodsorderinfo;
	}
	public void setLsMgoodsorderinfo(List<Mgoodsorderinfo> lsMgoodsorderinfo) {
		this.lsMgoodsorderinfo = lsMgoodsorderinfo;
	}
	public List<Dgoodsorderinfo> getLsDgoodsorderinfo() {
		return lsDgoodsorderinfo;
	}
	public void setLsDgoodsorderinfo(List<Dgoodsorderinfo> lsDgoodsorderinfo) {
		this.lsDgoodsorderinfo = lsDgoodsorderinfo;
	}
	public Mgoodsorderinfo getCurMgoodsorderinfo() {
		return curMgoodsorderinfo;
	}
	public void setCurMgoodsorderinfo(Mgoodsorderinfo curMgoodsorderinfo) {
		this.curMgoodsorderinfo = curMgoodsorderinfo;
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


}
