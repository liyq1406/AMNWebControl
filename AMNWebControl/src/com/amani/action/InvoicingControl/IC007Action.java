package com.amani.action.InvoicingControl;

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
import com.amani.model.Dgoodsreturnbarinfo;
import com.amani.model.Dreturngoodsinfo;
import com.amani.model.DreturngoodsinfoId;
import com.amani.model.Goodsinfo;
import com.amani.model.Mreturngoodsinfo;

import com.amani.model.Staffinfo;
import com.amani.service.InvoicingControl.IC007Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic007")
public class IC007Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IC007Service ic007Service;
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
	private String strBarJsonParam;
	private String strJsonParam;
	private String strCurCompId;
	private String strCurBillId;
	private String strSearchBillId;
	private String strCurDate;
	private String strCurGoodsId;
	private int barcodeCount;
	private String strIndexBar;
	private String strCurCompName;
	private String strCurEmpId;
	private String strCurEmpName;
	private List<Staffinfo> lsStaffinfo;
	private List<Mreturngoodsinfo> lsMreturngoodsinfo;
	private List<Dreturngoodsinfo> lsDreturngoodsinfo;
	private Mreturngoodsinfo curMreturngoodsinfo;
	private List<Dgoodsreturnbarinfo> lsDgoodsreturnbarinfo;
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
    private Goodsinfo curGoodsinfo;		//当前产品信息
	public Goodsinfo getCurGoodsinfo() {
		return curGoodsinfo;
	}
	public void setCurGoodsinfo(Goodsinfo curGoodsinfo) {
		this.curGoodsinfo = curGoodsinfo;
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
		
		curMreturngoodsinfo.setReturndate(CommonTool.setDateMask(curMreturngoodsinfo.getReturndate()));
		curMreturngoodsinfo.setReturntime(CommonTool.setTimeMask(curMreturngoodsinfo.getReturntime(), 1));
		curMreturngoodsinfo.setReturnstate(1);
		curMreturngoodsinfo.setReturnopationerid(CommonTool.getLoginInfo("USERID"));
		curMreturngoodsinfo.setReturnopationdate(CommonTool.getCurrDate());
		curMreturngoodsinfo.getId().setReturnbillid(this.ic007Service.getDataTool().loadBillIdByRule(curMreturngoodsinfo.getId().getReturncompid(),"mreturngoodsinfo", "returnbillid", "SP012"));
		double totalOrderAmt=0;
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDreturngoodsinfo=this.ic007Service.getDataTool().loadDTOList(strJsonParam, Dreturngoodsinfo.class);
			if(lsDreturngoodsinfo!=null && lsDreturngoodsinfo.size()>0)
			{
				for(int i=0;i<lsDreturngoodsinfo.size();i++)
				{
					if(!CommonTool.FormatString(lsDreturngoodsinfo.get(i).getReturngoodsno()).equals(""))
					{
						lsDreturngoodsinfo.get(i).setId(new DreturngoodsinfoId(curMreturngoodsinfo.getId().getReturncompid(),curMreturngoodsinfo.getId().getReturnbillid(),i*1.0));
					}
					else
					{
						lsDreturngoodsinfo.remove(i);
						i--;
					}
				}
				
			}
		}
		if(!CommonTool.FormatString(strBarJsonParam).equals(""))
		{
			this.lsDgoodsreturnbarinfo=this.ic007Service.getDataTool().loadDTOList(strBarJsonParam, Dgoodsreturnbarinfo.class);
			if(lsDgoodsreturnbarinfo!=null && lsDgoodsreturnbarinfo.size()>0)
			{
				for(int i=0;i<lsDgoodsreturnbarinfo.size();i++)
				{
					if(CommonTool.FormatString(lsDgoodsreturnbarinfo.get(i).getReturngoodsno()).equals(""))
					{
						lsDgoodsreturnbarinfo.remove(i);
						i--;
					}
				}
			}
		}
		return true;
	}

	
	protected boolean beforeConfirm() {
		
		curMreturngoodsinfo.setReturndate(CommonTool.setDateMask(curMreturngoodsinfo.getReturndate()));
		curMreturngoodsinfo.setReturntime(CommonTool.setTimeMask(curMreturngoodsinfo.getReturntime(), 1));
		curMreturngoodsinfo.setReturnstate(2);
		curMreturngoodsinfo.setConfirmcompid(CommonTool.getLoginInfo("COMPID"));
		curMreturngoodsinfo.setConfirmopationerid(CommonTool.getLoginInfo("USERID"));
		curMreturngoodsinfo.setConfirmopationdate(CommonTool.getCurrDate());
		double totalOrderAmt=0;
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDreturngoodsinfo=this.ic007Service.getDataTool().loadDTOList(strJsonParam, Dreturngoodsinfo.class);
			if(lsDreturngoodsinfo!=null && lsDreturngoodsinfo.size()>0)
			{
				for(int i=0;i<lsDreturngoodsinfo.size();i++)
				{
					if(!CommonTool.FormatString(lsDreturngoodsinfo.get(i).getReturngoodsno()).equals(""))
					{
						lsDreturngoodsinfo.get(i).setId(new DreturngoodsinfoId(curMreturngoodsinfo.getId().getReturncompid(),curMreturngoodsinfo.getId().getReturnbillid(),i*1.0));
					}
					else
					{
						lsDreturngoodsinfo.remove(i);
						i--;
					}
				}
				
			}
		}
		if(!CommonTool.FormatString(strBarJsonParam).equals(""))
		{
			this.lsDgoodsreturnbarinfo=this.ic007Service.getDataTool().loadDTOList(strBarJsonParam, Dgoodsreturnbarinfo.class);
			if(lsDgoodsreturnbarinfo!=null && lsDgoodsreturnbarinfo.size()>0)
			{
				for(int i=0;i<lsDgoodsreturnbarinfo.size();i++)
				{
					if(CommonTool.FormatString(lsDgoodsreturnbarinfo.get(i).getReturngoodsno()).equals(""))
					{
						lsDgoodsreturnbarinfo.remove(i);
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
		return SystemFinal.DELETE_SUCCESS;
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
			if(this.ic007Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC007", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.beforePost();
			if(flag==false)
			{
				return SystemFinal.POST_FAILURE;
			}
			flag=this.ic007Service.postInfo(this.curMreturngoodsinfo, this.lsDreturngoodsinfo,this.lsDgoodsreturnbarinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curMreturngoodsinfo=null;
			lsDreturngoodsinfo=null;
			lsDgoodsreturnbarinfo=null;
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
			this.lsMreturngoodsinfo=this.ic007Service.loadMreturngoodsinfo(this.strCurCompId,"",30, 0);
			if(lsMreturngoodsinfo!=null && lsMreturngoodsinfo.size()>0)
			{
				for(int i=0;i<lsMreturngoodsinfo.size();i++)
				{
					lsMreturngoodsinfo.get(i).setBreturncompid(lsMreturngoodsinfo.get(i).getId().getReturncompid());
					lsMreturngoodsinfo.get(i).setBreturnbillid(lsMreturngoodsinfo.get(i).getId().getReturnbillid());
					lsMreturngoodsinfo.get(i).setReturndate(CommonTool.getDateMask(lsMreturngoodsinfo.get(i).getReturndate()));
					lsMreturngoodsinfo.get(i).setReturntime(CommonTool.getTimeMask(lsMreturngoodsinfo.get(i).getReturntime(),1));
				}
				
			}
			else
			{
				this.curMreturngoodsinfo=this.ic007Service.addMastRecord(this.strCurCompId);
				curMreturngoodsinfo.setBreturncompname(this.ic007Service.getDataTool().loadCompNameById(curMreturngoodsinfo.getBreturncompid()));
				this.lsMreturngoodsinfo=new ArrayList();
				this.lsMreturngoodsinfo.add(curMreturngoodsinfo);
			}
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
			this.lsMreturngoodsinfo=this.ic007Service.loadSearchinfo(this.strCurCompId,this.strSearchBillId);
			if(lsMreturngoodsinfo!=null && lsMreturngoodsinfo.size()>0)
			{
				for(int i=0;i<lsMreturngoodsinfo.size();i++)
				{
					lsMreturngoodsinfo.get(i).setBreturncompid(lsMreturngoodsinfo.get(i).getId().getReturncompid());
					lsMreturngoodsinfo.get(i).setBreturnbillid(lsMreturngoodsinfo.get(i).getId().getReturnbillid());
					lsMreturngoodsinfo.get(i).setReturndate(CommonTool.getDateMask(lsMreturngoodsinfo.get(i).getReturndate()));
					lsMreturngoodsinfo.get(i).setReturntime(CommonTool.getTimeMask(lsMreturngoodsinfo.get(i).getReturntime(),1));
				}
				
			}
			else
			{
				this.curMreturngoodsinfo=this.ic007Service.addMastRecord(this.strCurCompId);
				curMreturngoodsinfo.setBreturncompname(this.ic007Service.getDataTool().loadCompNameById(curMreturngoodsinfo.getBreturncompid()));
				this.lsMreturngoodsinfo=new ArrayList();
				this.lsMreturngoodsinfo.add(curMreturngoodsinfo);
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
			this.curMreturngoodsinfo=this.ic007Service.loadMreturngoodsinfoById(this.strCurCompId, this.strCurBillId);
			if(curMreturngoodsinfo==null || curMreturngoodsinfo.getId()==null)
				curMreturngoodsinfo=this.ic007Service.addMastRecord(this.strCurCompId);
			curMreturngoodsinfo.setBreturncompname(this.ic007Service.getDataTool().loadCompNameById(curMreturngoodsinfo.getBreturncompid()));
			StringBuffer validatemsg=new StringBuffer();
			curMreturngoodsinfo.setReturnstaffname(this.ic007Service.getDataTool().loadEmpNameById(this.curMreturngoodsinfo.getBreturncompid(), this.curMreturngoodsinfo.getReturnstaffid(),validatemsg));
			validatemsg=null;
			this.lsDreturngoodsinfo=this.ic007Service.loadDreturngoodsinfo(this.strCurCompId, this.strCurBillId);
			this.lsDgoodsreturnbarinfo=this.ic007Service.loadDgoodsreturnbarinfo(this.strCurCompId, this.strCurBillId);
			this.lsCompwarehouses=this.ic007Service.getDataTool().loadCompWareById(this.strCurCompId);
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
			this.curMreturngoodsinfo=this.ic007Service.addMastRecord(this.strCurCompId);
			curMreturngoodsinfo.setBreturncompname(this.ic007Service.getDataTool().loadCompNameById(curMreturngoodsinfo.getBreturncompid()));
			this.lsCompwarehouses=this.ic007Service.getDataTool().loadCompWareById(this.strCurCompId);
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
			this.strCurEmpName=this.ic007Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	@Action(value = "validateFromBarNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateFromBarNo()
	{
		try
		{
			this.strMessage="";
			String strGoodsNo=this.ic007Service.validatFromBarCode(this.strCurCompId,this.strStartBarNo);
			if(CommonTool.FormatString(strGoodsNo).equals(""))
			{
				this.strMessage="该条码编号不存在或状态不正确,请确认";
				return SystemFinal.LOAD_SUCCESS;
			}
			this.curGoodsinfo=this.ic007Service.getDataTool().loadGoodsInfo(this.strCurCompId, strGoodsNo);
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
			if(this.ic007Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC007", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			beforeConfirm() ;
			boolean flag=this.ic007Service.postComfirmInfo(this.curMreturngoodsinfo, this.lsDreturngoodsinfo,this.lsDgoodsreturnbarinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.ic007Service.postStoreInser(this.curMreturngoodsinfo.getId().getReturncompid(),this.curMreturngoodsinfo.getId().getReturnbillid());
			if(flag==false)
			{
				this.strMessage="总部生成入库历史失败!";
				return SystemFinal.POST_FAILURE;
			}
			curMreturngoodsinfo=null;
			lsDreturngoodsinfo=null;
			lsDgoodsreturnbarinfo=null;
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
			if(this.ic007Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC007", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.ic007Service.handcancelInfo(this.strCurCompId, this.strCurBillId);
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
	public IC007Service getIc007Service() {
		return ic007Service;
	}
	@JSON(serialize=false)
	public void setIc007Service(IC007Service ic007Service) {
		this.ic007Service = ic007Service;
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
	public List<Mreturngoodsinfo> getLsMreturngoodsinfo() {
		return lsMreturngoodsinfo;
	}
	public void setLsMreturngoodsinfo(List<Mreturngoodsinfo> lsMreturngoodsinfo) {
		this.lsMreturngoodsinfo = lsMreturngoodsinfo;
	}
	public List<Dreturngoodsinfo> getLsDreturngoodsinfo() {
		return lsDreturngoodsinfo;
	}
	public void setLsDreturngoodsinfo(List<Dreturngoodsinfo> lsDreturngoodsinfo) {
		this.lsDreturngoodsinfo = lsDreturngoodsinfo;
	}
	public Mreturngoodsinfo getCurMreturngoodsinfo() {
		return curMreturngoodsinfo;
	}
	public void setCurMreturngoodsinfo(Mreturngoodsinfo curMreturngoodsinfo) {
		this.curMreturngoodsinfo = curMreturngoodsinfo;
	}
	public List<Dgoodsreturnbarinfo> getLsDgoodsreturnbarinfo() {
		return lsDgoodsreturnbarinfo;
	}
	public void setLsDgoodsreturnbarinfo(
			List<Dgoodsreturnbarinfo> lsDgoodsreturnbarinfo) {
		this.lsDgoodsreturnbarinfo = lsDgoodsreturnbarinfo;
	}
	public String getStrSearchBillId() {
		return strSearchBillId;
	}
	public void setStrSearchBillId(String strSearchBillId) {
		this.strSearchBillId = strSearchBillId;
	}
	public String getStrBarJsonParam() {
		return strBarJsonParam;
	}
	public void setStrBarJsonParam(String strBarJsonParam) {
		this.strBarJsonParam = strBarJsonParam;
	}


}
