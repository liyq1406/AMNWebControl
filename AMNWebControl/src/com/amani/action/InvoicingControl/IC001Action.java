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
import com.amani.model.Dgoodsbarinfo;
import com.amani.model.Compwarehouse;
import com.amani.model.Dgoodsinsert;
import com.amani.model.DgoodsinsertId;
import com.amani.model.Goodsinfo;
import com.amani.model.Mgoodsinsert;
import com.amani.model.MgoodsinsertId;
import com.amani.model.Staffinfo;
import com.amani.service.InvoicingControl.IC001Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic001")
public class IC001Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IC001Service ic001Service;
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
	 
	@Action(value = "loadDgoodsinsertss",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String loadDgoodsinsertss()
	{
		 this.lsDgoodsinsert=this.ic001Service.loadDgoodsinsertss(strCurCompId,strCurBillId);
		return SystemFinal.POST_SUCCESS ;
	}
	private String strJsonParam;
	private String strCurCompId;
	private String strCurBillId;
	private String strCurGoodsId;
	private int barcodeCount;
	private String strIndexBar;
	private String strCurCompName;
	private String strCurEmpId;
	private String strCurEmpName;
	private List<Staffinfo> lsStaffinfo;
	private List<Mgoodsinsert> lsMgoodsinsert;
	private List<Dgoodsinsert> lsDgoodsinsert;
	private List<Dgoodsbarinfo> lsDgoodsbarinfo;
	private Mgoodsinsert curMgoodsinsert;
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
    private String sendgoodsNo;
    private int sendgoodsCount;
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
		if(!CommonTool.FormatString(this.factcInsertware).equals(""))
		{
			this.curMgoodsinsert.setInserwareid(factcInsertware);
		}
		curMgoodsinsert.setInserdate(CommonTool.setDateMask(curMgoodsinsert.getInserdate()));
		curMgoodsinsert.setInsertime(CommonTool.setTimeMask(curMgoodsinsert.getInsertime(), 1));
		curMgoodsinsert.setInseropationdate(CommonTool.getCurrDate());
		curMgoodsinsert.setBillflag(2);
		curMgoodsinsert.setInseropationerid(CommonTool.getLoginInfo("USERID"));
		curMgoodsinsert.getId().setInserbillid(this.ic001Service.getDataTool().loadBillIdByRule(curMgoodsinsert.getId().getInsercompid(),"mgoodsinsert", "inserbillid", "SP012"));
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDgoodsinsert=this.ic001Service.getDataTool().loadDTOList(strJsonParam, Dgoodsinsert.class);
			if(lsDgoodsinsert!=null && lsDgoodsinsert.size()>0)
			{
				for(int i=0;i<lsDgoodsinsert.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodsinsert.get(i).getInsergoodsno()).equals(""))
					{
						lsDgoodsinsert.get(i).setId(new DgoodsinsertId(curMgoodsinsert.getId().getInsercompid(),curMgoodsinsert.getId().getInserbillid(),i*1.0));
						if(CommonTool.FormatString(lsDgoodsinsert.get(i).getProducedate()).length()>8)
						{
							lsDgoodsinsert.get(i).setProducedate(CommonTool.setDateMask(lsDgoodsinsert.get(i).getProducedate().substring(0, 10)));
						}
						else
						{
							lsDgoodsinsert.get(i).setProducedate(CommonTool.setDateMask(lsDgoodsinsert.get(i).getProducedate()));
						}
						
					}
				}
			}
		}
		return true;
	}

	protected boolean beforeComfirm() {
		if(!CommonTool.FormatString(this.factcInsertware).equals(""))
		{
			this.curMgoodsinsert.setInserwareid(factcInsertware);
		}
		curMgoodsinsert.setInserdate(CommonTool.setDateMask(curMgoodsinsert.getInserdate()));
		curMgoodsinsert.setInsertime(CommonTool.setTimeMask(curMgoodsinsert.getInsertime(), 1));
		curMgoodsinsert.setInseropationdate(CommonTool.getCurrDate());
		curMgoodsinsert.setBillflag(2);
		curMgoodsinsert.setInseropationerid(CommonTool.getLoginInfo("USERID"));
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDgoodsinsert=this.ic001Service.getDataTool().loadDTOList(strJsonParam, Dgoodsinsert.class);
			if(lsDgoodsinsert!=null && lsDgoodsinsert.size()>0)
			{
				for(int i=0;i<lsDgoodsinsert.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodsinsert.get(i).getInsergoodsno()).equals(""))
					{
						lsDgoodsinsert.get(i).setId(new DgoodsinsertId(curMgoodsinsert.getId().getInsercompid(),curMgoodsinsert.getId().getInserbillid(),i*1.0));
						if(CommonTool.FormatString(lsDgoodsinsert.get(i).getProducedate()).length()>8)
						{
							lsDgoodsinsert.get(i).setProducedate(CommonTool.setDateMask(lsDgoodsinsert.get(i).getProducedate().substring(0, 10)));
						}
						else
						{
							lsDgoodsinsert.get(i).setProducedate(CommonTool.setDateMask(lsDgoodsinsert.get(i).getProducedate()));
						}
						
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
			if(this.ic001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC001", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			Mgoodsinsert obj=new Mgoodsinsert();
			obj.setId(new MgoodsinsertId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.ic001Service.delete(obj);
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
			if(this.ic001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC001", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.ic001Service.postInfo(this.curMgoodsinsert, this.lsDgoodsinsert);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curMgoodsinsert=null;
			lsDgoodsinsert=null;
			lsDgoodsbarinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "loadCardInsertInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadCardInsertInfo()
	{
		try
		{
			this.lsMgoodsinsert=this.ic001Service.loadMasterDataSet(30, 0);
			if(lsMgoodsinsert!=null && lsMgoodsinsert.size()>0)
			{
				for(int i=0;i<lsMgoodsinsert.size();i++)
				{
					lsMgoodsinsert.get(i).setBinsercompid(lsMgoodsinsert.get(i).getId().getInsercompid());
					lsMgoodsinsert.get(i).setBinserbillid(lsMgoodsinsert.get(i).getId().getInserbillid());
					lsMgoodsinsert.get(i).setInserdate(CommonTool.getDateMask(lsMgoodsinsert.get(i).getInserdate()));
					lsMgoodsinsert.get(i).setInseropationdate(CommonTool.getDateMask(lsMgoodsinsert.get(i).getInseropationdate()));
					lsMgoodsinsert.get(i).setInsertime(CommonTool.getTimeMask(lsMgoodsinsert.get(i).getInsertime(),1));
				}
				/*this.lsDgoodsinsert=this.ic001Service.loadDgoodsinserts(lsMgoodsinsert.get(0).getId().getInsercompid(), lsMgoodsinsert.get(0).getId().getInserbillid());
				if(lsDgoodsinsert!=null && lsDgoodsinsert.size()>0)
				{
					for(int i=0;i<lsDgoodsinsert.size();i++)
					{
						lsDgoodsinsert.get(i).setBinsercompid(lsDgoodsinsert.get(i).getId().getInsercompid());
						lsDgoodsinsert.get(i).setBinserbillid(lsDgoodsinsert.get(i).getId().getInserbillid());
					}
				}
				curMgoodsinsert=lsMgoodsinsert.get(0);
				curMgoodsinsert.setBinsercompname(this.ic001Service.getDataTool().loadCompNameById(curMgoodsinsert.getBinsercompid()));
				StringBuffer validatemsg=new StringBuffer();
				curMgoodsinsert.setInserstaffname(this.ic001Service.getDataTool().loadEmpNameById(this.curMgoodsinsert.getBinsercompid(), this.curMgoodsinsert.getInserstaffid(),validatemsg));
				validatemsg=null;*/
			}
			else
			{
				this.curMgoodsinsert=this.ic001Service.addMastRecord();
				curMgoodsinsert.setBinsercompname(this.ic001Service.getDataTool().loadCompNameById(curMgoodsinsert.getBinsercompid()));
				this.lsMgoodsinsert=new ArrayList();
				this.lsMgoodsinsert.add(curMgoodsinsert);
			}
			this.lsCompwarehouses=this.ic001Service.getDataTool().loadCompWareById(CommonTool.getLoginInfo("COMPID"));
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
			lsMgoodsinsert=new ArrayList();
			Mgoodsinsert record=this.ic001Service.loadMgoodsinsertById(this.strCurCompId, this.strCurBillId);
			if(record!=null && record.getId()!=null)
				lsMgoodsinsert.add(record);
			record=null;
			if(lsMgoodsinsert!=null && lsMgoodsinsert.size()>0)
			{
				for(int i=0;i<lsMgoodsinsert.size();i++)
				{
					lsMgoodsinsert.get(i).setBinsercompid(lsMgoodsinsert.get(i).getId().getInsercompid());
					lsMgoodsinsert.get(i).setBinserbillid(lsMgoodsinsert.get(i).getId().getInserbillid());
					lsMgoodsinsert.get(i).setInserdate(CommonTool.getDateMask(lsMgoodsinsert.get(i).getInserdate()));
					lsMgoodsinsert.get(i).setInseropationdate(CommonTool.getDateMask(lsMgoodsinsert.get(i).getInseropationdate()));
					lsMgoodsinsert.get(i).setInsertime(CommonTool.getTimeMask(lsMgoodsinsert.get(i).getInsertime(),1));
				}
				
			}
			else
			{
				this.curMgoodsinsert=this.ic001Service.addMastRecord();
				curMgoodsinsert.setBinsercompname(this.ic001Service.getDataTool().loadCompNameById(curMgoodsinsert.getBinsercompid()));
				this.lsMgoodsinsert=new ArrayList();
				this.lsMgoodsinsert.add(curMgoodsinsert);
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		} 
	}
	
	@Action(value = "loadDcardnoinsert",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadDcardnoinsert()
	{
		try
		{
			this.curMgoodsinsert=this.ic001Service.loadMgoodsinsertById(this.strCurCompId, this.strCurBillId);
			if(curMgoodsinsert==null || curMgoodsinsert.getId()==null)
				curMgoodsinsert=this.ic001Service.addMastRecord();
			curMgoodsinsert.setBinsercompname(this.ic001Service.getDataTool().loadCompNameById(curMgoodsinsert.getBinsercompid()));
			StringBuffer validatemsg=new StringBuffer();
			curMgoodsinsert.setInserstaffname(this.ic001Service.getDataTool().loadEmpNameById(this.curMgoodsinsert.getBinsercompid(), this.curMgoodsinsert.getInserstaffid(),validatemsg));
			validatemsg=null;
			this.lsDgoodsinsert=this.ic001Service.loadDgoodsinserts(this.strCurCompId, this.strCurBillId);
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
			this.curMgoodsinsert=this.ic001Service.addMastRecord();
			curMgoodsinsert.setBinsercompname(this.ic001Service.getDataTool().loadCompNameById(curMgoodsinsert.getBinsercompid()));
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
			this.strCurEmpName=this.ic001Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "loadGoodsBarCord",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadGoodsBarCord()
	{
		try
		{
			this.strMessage="";
			StringBuffer startNoBuf=new StringBuffer();
			StringBuffer endNoBuf=new StringBuffer();
			boolean flag=this.ic001Service.loadNewBarCodeBySeqno(this.strIndexBar,this.strCurGoodsId, this.barcodeCount, startNoBuf, endNoBuf);
			if(flag==true)
			{
				this.strStartBarNo=startNoBuf.toString();
				this.strEndBarNo=endNoBuf.toString();
			}
			else
			{
				this.strMessage="生成产品条码失败!";
			}
			return SystemFinal.LOAD_SUCCESS ;
		   }
		catch(Exception ex)
		{
		     ex.printStackTrace();
		     return SystemFinal.LOAD_SUCCESS ;
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
			if(this.ic001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC001", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforeComfirm();
			boolean flag=this.ic001Service.postInfo(this.curMgoodsinsert, this.lsDgoodsinsert);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curMgoodsinsert=null;
			lsDgoodsinsert=null;
			lsDgoodsbarinfo=null;
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
			if(this.ic001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC001", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			//查询编码状态不为1的数据，如果有则无法取消复合
			if(this.ic001Service.isBarState(this.strCurBillId)){
				this.strMessage="该单据的产品已发货到门店，不能取消复合！";
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.ic001Service.handcancelInfo(this.strCurCompId, this.strCurBillId);
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
	
	@Action(value = "validateGoodsNo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String validateGoodsNo()
	{
		try
		{
			this.strMessage="";
			this.curGoodsinfo=this.ic001Service.getDataTool().loadGoodsInfo(CommonTool.getLoginInfo("COMPID"), this.strCurGoodsId);
			if(this.curGoodsinfo==null || CommonTool.FormatString(curGoodsinfo.getGoodsname()).equals(""))
			{
				this.strMessage="该产品编码不存在!";
			}
			return SystemFinal.POST_SUCCESS ;
		}
		catch(Exception ex)
		{
		     ex.printStackTrace();
		     return SystemFinal.POST_SUCCESS ;
		}
	}

	@Action(value = "confirmSend",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String confirmSend()
	{
		this.strMessage="";
		StringBuffer startNoBuf=new StringBuffer();
		StringBuffer endNoBuf=new StringBuffer();
		boolean flag=this.ic001Service.loadNewBarCodeBySeqno(this.strIndexBar,this.sendgoodsNo, this.sendgoodsCount, startNoBuf, endNoBuf);
		if(flag==true)
		{
			this.curGoodsinfo=this.ic001Service.getDataTool().loadGoodsInfo(CommonTool.getLoginInfo("COMPID"), this.sendgoodsNo);
			this.strStartBarNo=startNoBuf.toString();
			this.strEndBarNo=endNoBuf.toString();
		}
		else
		{
			this.strMessage="生成产品条码失败!";
		}
		return SystemFinal.LOAD_SUCCESS ;
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
	public IC001Service getIc001Service() {
		return ic001Service;
	}
	@JSON(serialize=false)
	public void setIc001Service(IC001Service ic001Service) {
		this.ic001Service = ic001Service;
	}
	

	
	public List<Mgoodsinsert> getLsMgoodsinsert() {
		return lsMgoodsinsert;
	}
	public void setLsMgoodsinsert(List<Mgoodsinsert> lsMgoodsinsert) {
		this.lsMgoodsinsert = lsMgoodsinsert;
	}
	public List<Dgoodsinsert> getLsDgoodsinsert() {
		return lsDgoodsinsert;
	}
	public void setLsDgoodsinsert(List<Dgoodsinsert> lsDgoodsinsert) {
		this.lsDgoodsinsert = lsDgoodsinsert;
	}
	public List<Dgoodsbarinfo> getLsDgoodsbarinfo() {
		return lsDgoodsbarinfo;
	}
	public void setLsDgoodsbarinfo(List<Dgoodsbarinfo> lsDgoodsbarinfo) {
		this.lsDgoodsbarinfo = lsDgoodsbarinfo;
	}
	public Mgoodsinsert getCurMgoodsinsert() {
		return curMgoodsinsert;
	}
	public void setCurMgoodsinsert(Mgoodsinsert curMgoodsinsert) {
		this.curMgoodsinsert = curMgoodsinsert;
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
	public String getSendgoodsNo() {
		return sendgoodsNo;
	}
	public void setSendgoodsNo(String sendgoodsNo) {
		this.sendgoodsNo = sendgoodsNo;
	}
	public int getSendgoodsCount() {
		return sendgoodsCount;
	}
	public void setSendgoodsCount(int sendgoodsCount) {
		this.sendgoodsCount = sendgoodsCount;
	}


}
