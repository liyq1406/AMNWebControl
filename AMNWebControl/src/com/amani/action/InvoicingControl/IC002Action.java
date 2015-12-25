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

import com.amani.model.Companyinfo;
import com.amani.model.Compwarehouse;
import com.amani.model.Dgoodsinsertpc;
import com.amani.model.Dgoodsouter;
import com.amani.model.DgoodsouterId;
import com.amani.model.Goodsinfo;
import com.amani.model.Mgoodsinsert;
import com.amani.model.Mgoodsouter;
import com.amani.model.MgoodsouterId;

import com.amani.model.Staffinfo;
import com.amani.service.InvoicingControl.IC002Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic002")
public class IC002Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IC002Service ic002Service;
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
	private String strJsonPcParam;
	private String strCurCompId;
	private String strCurBillId;
	private String strCurGoodsId;
	private int barcodeCount;
	private String strIndexBar;
	private String strCurCompName;
	private String strCurEmpId;
	private String strCurEmpName;
	private List<Staffinfo> lsStaffinfo;
	private List<Mgoodsouter> lsMgoodsouter;
	private List<Dgoodsouter> lsDgoodsouter;
	private Mgoodsouter curMgoodsouter;
	private List<Compwarehouse> lsCompwarehouses;
	private String factcInsertware;//仓库下拉框
	private String strCardfrom;
	private String strCardend;
	private String strWareId;
	private String strCurDate;
	private String strCardType;
	private int cardIdLength;//系统参数中设定的会员卡序号长度
    private String  numberOfCardFilter;//卡需要过滤的数字
    private String strStartBarNo;		//起始条码
    private String strEndBarNo;			//结束条码
    private int    goodsbarcount;		//起始条码间的数量
    private Goodsinfo curGoodsinfo;		//当前产品信息
    private BigDecimal curgoodsstock;  //当前库存量
    private int  	useBarFlag;			//是否启用条码
    private int 	revicetype;			//接受人类型 1 员工 2 门店
    private int isOneBarFlag=0;// 是否是单独的条码
    private List<Dgoodsinsertpc> lsDgoodsinsertpc;
    private Companyinfo companyinfo;
    private String strSendCurCompId;
	@Action(value = "loadDgoodsouterss",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	   })
	public String loadDgoodsouterss()
	{
			this.lsDgoodsouter=this.ic002Service.loadDgoodsouterss(strCurCompId, strCurBillId);
			this.companyinfo=this.ic002Service.userinfo(strSendCurCompId);
		     return SystemFinal.POST_SUCCESS ;
	}
    
	public List<Dgoodsinsertpc> getLsDgoodsinsertpc() {
		return lsDgoodsinsertpc;
	}
	public void setLsDgoodsinsertpc(List<Dgoodsinsertpc> lsDgoodsinsertpc) {
		this.lsDgoodsinsertpc = lsDgoodsinsertpc;
	}
	public int getRevicetype() {
		return revicetype;
	}
	public void setRevicetype(int revicetype) {
		this.revicetype = revicetype;
	}
	public int getUseBarFlag() {
		return useBarFlag;
	}
	public void setUseBarFlag(int useBarFlag) {
		this.useBarFlag = useBarFlag;
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
			this.curMgoodsouter.setOuterwareid(factcInsertware);
		}
		curMgoodsouter.setOuterdate(CommonTool.setDateMask(curMgoodsouter.getOuterdate()));
		curMgoodsouter.setOutertime(CommonTool.setTimeMask(curMgoodsouter.getOutertime(), 1));
		curMgoodsouter.setOuteropationdate(CommonTool.getCurrDate());
		curMgoodsouter.setOuteropationerid(CommonTool.getLoginInfo("USERID"));
		curMgoodsouter.setBillflag(2);
		curMgoodsouter.getId().setOuterbillid(this.ic002Service.getDataTool().loadBillIdByRule(curMgoodsouter.getId().getOutercompid(),"mgoodsouter", "outerbillid", "SP012"));
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDgoodsouter=this.ic002Service.getDataTool().loadDTOList(strJsonParam, Dgoodsouter.class);
			if(lsDgoodsouter!=null && lsDgoodsouter.size()>0)
			{
				for(int i=0;i<lsDgoodsouter.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodsouter.get(i).getOutergoodsno()).equals(""))
					{
						lsDgoodsouter.get(i).setId(new DgoodsouterId(curMgoodsouter.getId().getOutercompid(),curMgoodsouter.getId().getOuterbillid(),i*1.0));
						
					}
				}
			}
		}
		if(!CommonTool.FormatString(strJsonPcParam).equals(""))
		{
			this.lsDgoodsinsertpc=this.ic002Service.getDataTool().loadDTOList(strJsonPcParam, Dgoodsinsertpc.class);
			if(lsDgoodsinsertpc!=null && lsDgoodsinsertpc.size()>0)
			{
				for(int i=0;i<lsDgoodsinsertpc.size();i++)
				{
					if(CommonTool.FormatString(lsDgoodsinsertpc.get(i).getInsergoodsno()).equals(""))
					{
						lsDgoodsinsertpc.remove(i);
						i--;
					}
				}
			}
		}
		return true;
	}

	protected boolean beforeComfirm() {
		if(!CommonTool.FormatString(this.factcInsertware).equals(""))
		{
			this.curMgoodsouter.setOuterwareid(factcInsertware);
		}
		curMgoodsouter.setOuterdate(CommonTool.setDateMask(curMgoodsouter.getOuterdate()));
		curMgoodsouter.setOutertime(CommonTool.setTimeMask(curMgoodsouter.getOutertime(), 1));
		curMgoodsouter.setOuteropationdate(CommonTool.getCurrDate());
		curMgoodsouter.setOuteropationerid(CommonTool.getLoginInfo("USERID"));
		curMgoodsouter.setBillflag(2);
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDgoodsouter=this.ic002Service.getDataTool().loadDTOList(strJsonParam, Dgoodsouter.class);
			if(lsDgoodsouter!=null && lsDgoodsouter.size()>0)
			{
				for(int i=0;i<lsDgoodsouter.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodsouter.get(i).getOutergoodsno()).equals(""))
					{
						lsDgoodsouter.get(i).setId(new DgoodsouterId(curMgoodsouter.getId().getOutercompid(),curMgoodsouter.getId().getOuterbillid(),i*1.0));
						
					}
				}
			}
		}
		if(!CommonTool.FormatString(strJsonPcParam).equals(""))
		{
			this.lsDgoodsinsertpc=this.ic002Service.getDataTool().loadDTOList(strJsonPcParam, Dgoodsinsertpc.class);
			if(lsDgoodsinsertpc!=null && lsDgoodsinsertpc.size()>0)
			{
				for(int i=0;i<lsDgoodsinsertpc.size();i++)
				{
					if(CommonTool.FormatString(lsDgoodsinsertpc.get(i).getInsergoodsno()).equals(""))
					{
						lsDgoodsinsertpc.remove(i);
						i--;
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
			if(this.ic002Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC001", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforeComfirm();
			
			if(Integer.parseInt(this.ic002Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP017"))==1)
			{
				if(this.ic002Service.checkGoodStock(strCurCompId, lsDgoodsouter)==false)
				{
					this.strMessage="产品库存不足";
					return SystemFinal.POST_FAILURE;
				}
			}
			
			boolean flag=this.ic002Service.postInfo(this.curMgoodsouter, this.lsDgoodsouter,lsDgoodsinsertpc);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			if(curMgoodsouter.getRevicetype()==2)// 出库到门店
			{
				flag=this.ic002Service.handInserBillByOutBill(this.curMgoodsouter, this.lsDgoodsouter);
				if(flag==false)
				{
					this.strMessage="生成门店入库单失败!";
					return SystemFinal.POST_FAILURE;
				}
			}
			curMgoodsouter=null;
			lsDgoodsouter=null;
			lsDgoodsinsertpc=null;
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
			if(this.ic002Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC001", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.ic002Service.handcancelInfo(this.strCurCompId, this.strCurBillId);
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
			if(this.ic002Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC002", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			Mgoodsouter obj=new Mgoodsouter();
			obj.setId(new MgoodsouterId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.ic002Service.delete(obj);
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
			if(this.ic002Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC002", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			if(Integer.parseInt(this.ic002Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP017"))==1)
			{
				if(this.ic002Service.checkGoodStock(strCurCompId, lsDgoodsouter)==false)
				{
					this.strMessage="产品库存不足";
					return SystemFinal.POST_FAILURE;
				}
			}
			boolean flag=this.ic002Service.postInfo(this.curMgoodsouter, this.lsDgoodsouter,lsDgoodsinsertpc);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			if(curMgoodsouter.getRevicetype()==2)// 出库到门店
			{
				flag=this.ic002Service.handInserBillByOutBill(this.curMgoodsouter, this.lsDgoodsouter);
				if(flag==false)
				{
					this.strMessage="生成门店入库单失败!";
					return SystemFinal.POST_FAILURE;
				}
			}
			curMgoodsouter=null;
			lsDgoodsouter=null;
			lsDgoodsinsertpc=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "loadGoodsOuterInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadGoodsOuterInfo()
	{
		try
		{
			this.lsMgoodsouter=this.ic002Service.loadMasterDataSet(30, 0);
			if(lsMgoodsouter!=null && lsMgoodsouter.size()>0)
			{
				for(int i=0;i<lsMgoodsouter.size();i++)
				{
					lsMgoodsouter.get(i).setBoutercompid(lsMgoodsouter.get(i).getId().getOutercompid());
					lsMgoodsouter.get(i).setBouterbillid(lsMgoodsouter.get(i).getId().getOuterbillid());
					lsMgoodsouter.get(i).setOuterdate(CommonTool.getDateMask(lsMgoodsouter.get(i).getOuterdate()));
					lsMgoodsouter.get(i).setOuteropationdate(CommonTool.getDateMask(lsMgoodsouter.get(i).getOuteropationdate()));
					lsMgoodsouter.get(i).setOutertime(CommonTool.getTimeMask(lsMgoodsouter.get(i).getOutertime(),1));
				}
			}
			else
			{
				this.curMgoodsouter=this.ic002Service.addMastRecord();
				curMgoodsouter.setBoutercompname(this.ic002Service.getDataTool().loadCompNameById(curMgoodsouter.getBoutercompid()));
				this.lsMgoodsouter=new ArrayList();
				this.lsMgoodsouter.add(curMgoodsouter);
			}
			this.lsCompwarehouses=this.ic002Service.getDataTool().loadCompWareById(CommonTool.getLoginInfo("COMPID"));
			useBarFlag=CommonTool.FormatInteger(Integer.parseInt(this.ic002Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"), "SP016")));
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
			try
			{
				lsMgoodsouter=new ArrayList();
				Mgoodsouter record=this.ic002Service.loadMgoodsouterById(this.strCurCompId, this.strCurBillId);
				if(record!=null && record.getId()!=null)
					lsMgoodsouter.add(record);
				record=null;
				if(lsMgoodsouter!=null && lsMgoodsouter.size()>0)
				{
					for(int i=0;i<lsMgoodsouter.size();i++)
					{
						lsMgoodsouter.get(i).setBoutercompid(lsMgoodsouter.get(i).getId().getOutercompid());
						lsMgoodsouter.get(i).setBouterbillid(lsMgoodsouter.get(i).getId().getOuterbillid());
						lsMgoodsouter.get(i).setOuterdate(CommonTool.getDateMask(lsMgoodsouter.get(i).getOuterdate()));
						lsMgoodsouter.get(i).setOuteropationdate(CommonTool.getDateMask(lsMgoodsouter.get(i).getOuteropationdate()));
						lsMgoodsouter.get(i).setOutertime(CommonTool.getTimeMask(lsMgoodsouter.get(i).getOutertime(),1));
					}
				}
				else
				{
					this.curMgoodsouter=this.ic002Service.addMastRecord();
					curMgoodsouter.setBoutercompname(this.ic002Service.getDataTool().loadCompNameById(curMgoodsouter.getBoutercompid()));
					this.lsMgoodsouter=new ArrayList();
					this.lsMgoodsouter.add(curMgoodsouter);
				}
				return SystemFinal.LOAD_SUCCESS;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return SystemFinal.LOAD_FAILURE;
			} 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		} 
	}
	
	@Action(value = "loadDgoodsouter",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadDgoodsouter()
	{
		try
		{
			this.curMgoodsouter=this.ic002Service.loadMgoodsouterById(this.strCurCompId, this.strCurBillId);
			if(curMgoodsouter==null || curMgoodsouter.getId()==null)
				curMgoodsouter=this.ic002Service.addMastRecord();
			curMgoodsouter.setBoutercompname(this.ic002Service.getDataTool().loadCompNameById(curMgoodsouter.getBoutercompid()));
			if(CommonTool.FormatInteger(curMgoodsouter.getRevicetype())==1)
			{
				StringBuffer validatemsg=new StringBuffer();
				curMgoodsouter.setOuterstaffname(this.ic002Service.getDataTool().loadEmpNameById(this.curMgoodsouter.getBoutercompid(), this.curMgoodsouter.getOuterstaffid(),validatemsg));
				validatemsg=null;
			}
			else
			{
				curMgoodsouter.setOuterstaffname(this.ic002Service.getDataTool().loadCompNameById(this.curMgoodsouter.getOuterstaffid()));
			}
			this.lsDgoodsouter=this.ic002Service.loadDgoodsouters(this.strCurCompId, this.strCurBillId);
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
			this.curMgoodsouter=this.ic002Service.addMastRecord();
			curMgoodsouter.setBoutercompname(this.ic002Service.getDataTool().loadCompNameById(curMgoodsouter.getBoutercompid()));
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
			if(this.revicetype==1)
				this.strCurEmpName=this.ic002Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			else
				this.strCurEmpName=this.ic002Service.getDataTool().loadCompNameById(strCurEmpId);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "mangerSendOutGoodsNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String mangerSendOutGoodsNo()
	{
		try
		{
			this.strMessage="";
			if(this.ic002Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX026")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
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
	
	@Action(value = "validateFromBarNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateFromBarNo()
	{
		try
		{
			this.strMessage="";
			String strGoodsNo=this.ic002Service.validatFromBarCode(this.strStartBarNo);
			if(CommonTool.FormatString(strGoodsNo).equals(""))
			{
				this.strMessage="该条码编号不存在或状态不正确,请确认";
				return SystemFinal.LOAD_SUCCESS;
			}
			//外卖品不可以出库
			//产品用途=“客装”，的时候为外卖品
			String goodsusetype = this.ic002Service.loadGoodsusetype(strGoodsNo);
			if("1".equals(goodsusetype)){
				this.strMessage="外卖品不可以出库";
				return SystemFinal.LOAD_SUCCESS;
			}
			this.curGoodsinfo=this.ic002Service.getDataTool().loadGoodsInfo(this.strCurCompId, strGoodsNo);
			if(CommonTool.FormatInteger(curGoodsinfo.getGoodsusetype())==1 && !strCurCompId.equals("001"))
			{
				this.strMessage="该产品为客装产品,不能消耗出库";
				return SystemFinal.LOAD_SUCCESS;
			}
			this.curgoodsstock=this.ic002Service.loadCurStock(this.strCurCompId, this.strCurDate, strGoodsNo, strWareId);
			//this.lsDgoodsinsertpc=this.ic002Service.loadDgoodsinsertpc(strCurCompId, strGoodsNo, strWareId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "validateGoodsNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateGoodsNo()
	{
		try
		{
			this.strMessage="";
	
		
			this.curGoodsinfo=this.ic002Service.getDataTool().loadGoodsInfo(this.strCurCompId, this.strCurGoodsId);
			if(curGoodsinfo==null || curGoodsinfo.getId()==null)
			{
				this.strMessage="该产品编号不存在,请确认";
				return SystemFinal.LOAD_SUCCESS;
			}
			if(CommonTool.FormatInteger(curGoodsinfo.getGoodsusetype())==1 && !strCurCompId.equals("001"))
			{
				this.strMessage="该产品为客装产品,不能消耗出库";
				return SystemFinal.LOAD_SUCCESS;
			}
			this.curgoodsstock=this.ic002Service.loadCurStock(this.strCurCompId, this.strCurDate, strCurGoodsId, strWareId);
			//this.lsDgoodsinsertpc=this.ic002Service.loadDgoodsinsertpc(strCurCompId, strCurGoodsId, strWareId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "validateGoodsNoCompany",  results = { 
			@Result(name = "load_success", type = "json"),
			@Result(name = "load_failure", type = "json")	   })
	public String validateGoodsNoCompany()
	{
		try
		{
			this.strMessage="";
			
			
			this.curGoodsinfo=this.ic002Service.queryGoodsInfo(this.strCurGoodsId);
			if(curGoodsinfo==null || curGoodsinfo.getId()==null)
			{
				this.strMessage="该产品编号不存在或必须检验条码,请确认";
				return SystemFinal.LOAD_SUCCESS;
			}
			if(CommonTool.FormatInteger(curGoodsinfo.getGoodsusetype())==1)
			{
				this.strMessage="该产品为客装产品,不能消耗出库";
				return SystemFinal.LOAD_SUCCESS;
			}
			this.curgoodsstock=this.ic002Service.loadCurStock(this.strCurCompId, this.strCurDate, strCurGoodsId, strWareId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "loadGoodsPc",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadGoodsPc()
	{
		try
		{
			this.strMessage="";
			this.lsDgoodsinsertpc=this.ic002Service.loadDgoodsinsertpc(strCurCompId, strCurGoodsId, strWareId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "validateToBarNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateToBarNo()
	{
		try
		{
			String strGoodsNo=this.ic002Service.validatFromBarCode(this.strStartBarNo);
			if(CommonTool.FormatString(strGoodsNo).equals(""))
			{
				this.strMessage="该条码编号不存在或状态不正确,请确认";
				return SystemFinal.LOAD_SUCCESS;
			}
			/*if(CommonTool.FormatInteger(isOneBarFlag)==0)
			{
				boolean flag=this.ic002Service.validatEndBarCode(this.strCurGoodsId, strStartBarNo);
				if(flag==false)
				{
					this.strMessage="该条码对应的产品代码不是"+strCurGoodsId+",请确认!";
					return SystemFinal.LOAD_SUCCESS;
				}
			}*/
			String goodsusetype = this.ic002Service.loadGoodsusetype(strGoodsNo);
			if("1".equals(goodsusetype)){
				this.strMessage="外卖品不可以出库";
				return SystemFinal.LOAD_SUCCESS;
			}
			System.out.println(this.strCurCompId+","+strGoodsNo);
			this.curGoodsinfo=this.ic002Service.getDataTool().loadGoodsInfo(this.strCurCompId, strGoodsNo);
			if(curGoodsinfo==null || curGoodsinfo.getId()==null)
			{
				this.strMessage="该产品编号不存在,请确认";
				return SystemFinal.LOAD_SUCCESS;
			}
			if(CommonTool.FormatInteger(curGoodsinfo.getGoodsusetype())==1 && !strCurCompId.equals("001"))
			{
				this.strMessage="该产品为客装产品,不能消耗出库";
				return SystemFinal.LOAD_SUCCESS;
			}
			strCurGoodsId=this.ic002Service.validatFromBarCode(this.strStartBarNo);
			this.curGoodsinfo=this.ic002Service.getDataTool().loadGoodsInfo(CommonTool.getLoginInfo("COMPID"), this.strCurGoodsId);
			this.curgoodsstock=this.ic002Service.loadCurStock(CommonTool.getLoginInfo("COMPID"), this.strCurDate, strCurGoodsId, strWareId);
			
			StringBuffer msgBuf=new StringBuffer();
			this.goodsbarcount=this.ic002Service.validatBarCode(this.strStartBarNo,this.strEndBarNo,msgBuf);
			this.lsDgoodsinsertpc=this.ic002Service.loadGoodsPc(this.strStartBarNo,this.strEndBarNo);
			this.strMessage=msgBuf.toString();
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
	public IC002Service getIc002Service() {
		return ic002Service;
	}
	@JSON(serialize=false)
	public void setIc002Service(IC002Service ic002Service) {
		this.ic002Service = ic002Service;
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
	public List<Mgoodsouter> getLsMgoodsouter() {
		return lsMgoodsouter;
	}
	public void setLsMgoodsouter(List<Mgoodsouter> lsMgoodsouter) {
		this.lsMgoodsouter = lsMgoodsouter;
	}
	public List<Dgoodsouter> getLsDgoodsouter() {
		return lsDgoodsouter;
	}
	public void setLsDgoodsouter(List<Dgoodsouter> lsDgoodsouter) {
		this.lsDgoodsouter = lsDgoodsouter;
	}
	public Mgoodsouter getCurMgoodsouter() {
		return curMgoodsouter;
	}
	public void setCurMgoodsouter(Mgoodsouter curMgoodsouter) {
		this.curMgoodsouter = curMgoodsouter;
	}
	public Goodsinfo getCurGoodsinfo() {
		return curGoodsinfo;
	}
	public void setCurGoodsinfo(Goodsinfo curGoodsinfo) {
		this.curGoodsinfo = curGoodsinfo;
	}
	public int getGoodsbarcount() {
		return goodsbarcount;
	}
	public void setGoodsbarcount(int goodsbarcount) {
		this.goodsbarcount = goodsbarcount;
	}
	public BigDecimal getCurgoodsstock() {
		return curgoodsstock;
	}
	public void setCurgoodsstock(BigDecimal curgoodsstock) {
		this.curgoodsstock = curgoodsstock;
	}
	public String getStrCurDate() {
		return strCurDate;
	}
	public void setStrCurDate(String strCurDate) {
		this.strCurDate = strCurDate;
	}
	public String getStrJsonPcParam() {
		return strJsonPcParam;
	}
	public void setStrJsonPcParam(String strJsonPcParam) {
		this.strJsonPcParam = strJsonPcParam;
	}
	public Companyinfo getCompanyinfo() {
		return companyinfo;
	}
	public void setCompanyinfo(Companyinfo companyinfo) {
		this.companyinfo = companyinfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getIsOneBarFlag() {
		return isOneBarFlag;
	}
	public void setIsOneBarFlag(int isOneBarFlag) {
		this.isOneBarFlag = isOneBarFlag;
	}
	public String getStrSendCurCompId() {
		return strSendCurCompId;
	}
	public void setStrSendCurCompId(String strSendCurCompId) {
		this.strSendCurCompId = strSendCurCompId;
	}


}
