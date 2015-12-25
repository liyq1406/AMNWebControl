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
import com.amani.model.Dgoodsreceipt;
import com.amani.model.DgoodsreceiptId;
import com.amani.model.Mgoodsreceipt;
import com.amani.model.Staffinfo;
import com.amani.service.InvoicingControl.IC012Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic012")
public class IC012Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IC012Service ic012Service;
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
	private String strJsonPcParam;
	private String strJsonBarParam;
	private String strJsonParam;
	private String strCurCompId;
	private String strCurBillId;
	private String strOrderCompId;
	private String strOrderBillId;
	private String strCurDate;
	private String strCurGoodsId;
	private int barcodeCount;
	private String strIndexBar;
	private String strCurCompName;
	private String strCurEmpId;
	private String strCurEmpName;
	private List<Staffinfo> lsStaffinfo;
	private List<Mgoodsreceipt> lsMgoodsreceipt;
	private List<Dgoodsreceipt> lsDgoodsreceipt;
	private Mgoodsreceipt curMgoodsreceipt;
	private String factcInsertware;//仓库下拉框
	private String strCardfrom;
	private String strCardend;
	private String strWareId;
    private String strSearchSendBillno;
    private String strSearchOrderBillno;
    private int handtype;  // 1 复合  2	取消复合
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
	protected boolean beforeComfirm() {
		curMgoodsreceipt.setReceiptdate(CommonTool.setDateMask(curMgoodsreceipt.getReceiptdate()));
		curMgoodsreceipt.setReceipttime(CommonTool.setTimeMask(curMgoodsreceipt.getReceipttime(), 1));
		curMgoodsreceipt.setReceiptopationerid(CommonTool.getLoginInfo("USERID"));
		curMgoodsreceipt.setReceiptopationdate(CommonTool.getCurrDate());
		curMgoodsreceipt.setReceiptstate(1);
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDgoodsreceipt=this.ic012Service.getDataTool().loadDTOList(strJsonParam, Dgoodsreceipt.class);
			if(lsDgoodsreceipt!=null && lsDgoodsreceipt.size()>0)
			{
				for(int i=0;i<lsDgoodsreceipt.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodsreceipt.get(i).getReceiptgoodsno()).equals(""))
					{
						lsDgoodsreceipt.get(i).setId(new DgoodsreceiptId(curMgoodsreceipt.getId().getReceiptcompid(),curMgoodsreceipt.getId().getReceiptbillid(),i*1.0));
					}
				}
				
			}
		}
		return true;
	}
	
	
	@Action(value = "handconfirmInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String handconfirmInfo()
	{
		try
		{
			this.strMessage="";
			if(this.ic012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC012", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.beforeComfirm();
			if(flag==false)
			{
				return SystemFinal.POST_FAILURE;
			}
			flag=this.ic012Service.postInfo(this.curMgoodsreceipt, this.lsDgoodsreceipt);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curMgoodsreceipt=null;
			lsDgoodsreceipt=null;
			return SystemFinal.POST_SUCCESS ;
		   }
		catch(Exception ex)
		{
		     ex.printStackTrace();
		     return SystemFinal.POST_SUCCESS ;
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
			this.strMessage="";
			
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
			this.lsMgoodsreceipt=this.ic012Service.loadMgoodsreceipt(this.strCurCompId);
			if(lsMgoodsreceipt!=null && lsMgoodsreceipt.size()>0)
			{
				for(int i=0;i<lsMgoodsreceipt.size();i++)
				{
					lsMgoodsreceipt.get(i).setBreceiptcompid(lsMgoodsreceipt.get(i).getId().getReceiptcompid());
					lsMgoodsreceipt.get(i).setBreceiptbillid(lsMgoodsreceipt.get(i).getId().getReceiptbillid());
					lsMgoodsreceipt.get(i).setReceiptdate(CommonTool.getDateMask(lsMgoodsreceipt.get(i).getReceiptdate()));
					lsMgoodsreceipt.get(i).setReceipttime(CommonTool.getTimeMask(lsMgoodsreceipt.get(i).getReceipttime(),1));
				}
			}
			else
			{
				lsMgoodsreceipt=new ArrayList();
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
			lsMgoodsreceipt=this.ic012Service.loadlsMgoodsreceiptById(this.strCurCompId,this.strSearchSendBillno,this.strSearchOrderBillno);
			if(lsMgoodsreceipt!=null && lsMgoodsreceipt.size()>0)
			{
				for(int i=0;i<lsMgoodsreceipt.size();i++)
				{
					lsMgoodsreceipt.get(i).setBreceiptcompid(lsMgoodsreceipt.get(i).getId().getReceiptcompid());
					lsMgoodsreceipt.get(i).setBreceiptbillid(lsMgoodsreceipt.get(i).getId().getReceiptbillid());
					lsMgoodsreceipt.get(i).setReceiptdate(CommonTool.getDateMask(lsMgoodsreceipt.get(i).getReceiptdate()));
					lsMgoodsreceipt.get(i).setReceipttime(CommonTool.getTimeMask(lsMgoodsreceipt.get(i).getReceipttime(),1));
				}
			}
			else
			{
				lsMgoodsreceipt=new ArrayList();
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
			this.curMgoodsreceipt=this.ic012Service.loadcurMgoodsreceiptById(this.strCurCompId, this.strCurBillId);
			if(curMgoodsreceipt!=null && curMgoodsreceipt.getId()!=null)
			{
				curMgoodsreceipt.setBreceiptcompname(this.ic012Service.getDataTool().loadCompNameById(curMgoodsreceipt.getBreceiptcompid()));
				StringBuffer validatemsg=new StringBuffer();
				curMgoodsreceipt.setReceiptstaffname(this.ic012Service.getDataTool().loadEmpNameById(this.curMgoodsreceipt.getBreceiptcompid(), this.curMgoodsreceipt.getReceiptstaffid(),validatemsg));
				curMgoodsreceipt.setReceiptwarename(this.ic012Service.getDataTool().loadCompWareNameById(curMgoodsreceipt.getBreceiptcompid(),curMgoodsreceipt.getReceiptwareid()));

				validatemsg=null;
			}
			else
			{
				curMgoodsreceipt=new Mgoodsreceipt(); 
			}
			if(CommonTool.FormatInteger(curMgoodsreceipt.getReceiptstate())==0)
			{
				this.curMgoodsreceipt.setReceiptdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
				this.curMgoodsreceipt.setReceipttime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
			}
			this.lsDgoodsreceipt=this.ic012Service.loadDgoodsreceipt(this.strCurCompId, this.strCurBillId);
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
		return SystemFinal.ADD_SUCCESS;
	}
	
	@Action(value = "validateInserper",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateInserper()
	{
		try
		{
			StringBuffer validateMsg=new StringBuffer();
			this.strCurEmpName=this.ic012Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
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

	@JSON(serialize=false)
	public IC012Service getIc012Service() {
		return ic012Service;
	}
	@JSON(serialize=false)
	public void setIc012Service(IC012Service ic012Service) {
		this.ic012Service = ic012Service;
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
	public String getStrSearchSendBillno() {
		return strSearchSendBillno;
	}
	public void setStrSearchSendBillno(String strSearchSendBillno) {
		this.strSearchSendBillno = strSearchSendBillno;
	}
	public String getStrSearchOrderBillno() {
		return strSearchOrderBillno;
	}
	public void setStrSearchOrderBillno(String strSearchOrderBillno) {
		this.strSearchOrderBillno = strSearchOrderBillno;
	}
	public String getStrJsonPcParam() {
		return strJsonPcParam;
	}
	public void setStrJsonPcParam(String strJsonPcParam) {
		this.strJsonPcParam = strJsonPcParam;
	}
	public String getStrOrderBillId() {
		return strOrderBillId;
	}
	public void setStrOrderBillId(String strOrderBillId) {
		this.strOrderBillId = strOrderBillId;
	}
	public String getStrOrderCompId() {
		return strOrderCompId;
	}
	public void setStrOrderCompId(String strOrderCompId) {
		this.strOrderCompId = strOrderCompId;
	}
	public String getStrJsonBarParam() {
		return strJsonBarParam;
	}
	public void setStrJsonBarParam(String strJsonBarParam) {
		this.strJsonBarParam = strJsonBarParam;
	}
	public List<Mgoodsreceipt> getLsMgoodsreceipt() {
		return lsMgoodsreceipt;
	}
	public void setLsMgoodsreceipt(List<Mgoodsreceipt> lsMgoodsreceipt) {
		this.lsMgoodsreceipt = lsMgoodsreceipt;
	}
	public List<Dgoodsreceipt> getLsDgoodsreceipt() {
		return lsDgoodsreceipt;
	}
	public void setLsDgoodsreceipt(List<Dgoodsreceipt> lsDgoodsreceipt) {
		this.lsDgoodsreceipt = lsDgoodsreceipt;
	}
	public Mgoodsreceipt getCurMgoodsreceipt() {
		return curMgoodsreceipt;
	}
	public void setCurMgoodsreceipt(Mgoodsreceipt curMgoodsreceipt) {
		this.curMgoodsreceipt = curMgoodsreceipt;
	}
	

}
