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
import com.amani.model.Dgoodsinsertpc;
import com.amani.model.Dgoodssendbarinfo;
import com.amani.model.Dgoodssendinfo;
import com.amani.model.DgoodssendinfoId;
import com.amani.model.Goodsinfo;
import com.amani.model.Mgoodssendinfo;
import com.amani.model.Staffinfo;
import com.amani.service.InvoicingControl.IC010Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic010")
public class IC010Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IC010Service ic010Service;
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
	private List<Mgoodssendinfo> lsMgoodssendinfo;
	private List<Dgoodssendinfo> lsDgoodssendinfo;
	private Mgoodssendinfo curMgoodssendinfo;
	private List<Compwarehouse> lsCompwarehouses;
	private String factcInsertware;//仓库下拉框
	private String strCardfrom;
	private String strCardend;
	private String strWareId;
	private String strCardType;
	private int cardIdLength;//系统参数中设定的会员卡序号长度
    private String  numberOfCardFilter;//卡需要过滤的数字
    private String strStartBarNo;		//起始条码
    private String strEndBarNo;			//结束条码
    private int    goodsbarcount;		//起始条码间的数量
    private Goodsinfo curGoodsinfo;		//当前产品信息
    private String strSearchSendBillno;
    private String strSearchOrderBillno;
    private int handtype;  // 1 复合  2	取消复合
    private List<Dgoodssendbarinfo> lsDgoodssendbarinfo;
    private List<Dgoodsinsertpc> lsDgoodsinsertpc;
    private int isOneBarFlag=0;// 是否是单独的条码
	public int getIsOneBarFlag() {
		return isOneBarFlag;
	}
	public void setIsOneBarFlag(int isOneBarFlag) {
		this.isOneBarFlag = isOneBarFlag;
	}
	public List<Dgoodsinsertpc> getLsDgoodsinsertpc() {
		return lsDgoodsinsertpc;
	}
	public void setLsDgoodsinsertpc(List<Dgoodsinsertpc> lsDgoodsinsertpc) {
		this.lsDgoodsinsertpc = lsDgoodsinsertpc;
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

	@Action(value = "loadDorderInfos",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadDorderInfos()
	{
		try
		{
			this.lsDgoodssendinfo=this.ic010Service.loadDgoodssendinfos(this.strCurCompId, this.strCurBillId);
			//lsDgoodssendbarinfo=this.ic010Service.loadDgoodssendbarinfos(this.strCurCompId, this.strCurBillId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "loadSelecBarDetialData",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadSelecBarDetialData()
	{
		try
		{
			lsDgoodssendbarinfo=this.ic010Service.loadDgoodssendbarinfos(this.strCurCompId, this.strCurBillId,this.strCurGoodsId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
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
		curMgoodssendinfo.setOrderdate(CommonTool.setDateMask(curMgoodssendinfo.getOrderdate()));
		curMgoodssendinfo.setOrdertime(CommonTool.setTimeMask(curMgoodssendinfo.getOrdertime(), 1));
		curMgoodssendinfo.setSenddate(CommonTool.getCurrDate());
		curMgoodssendinfo.setSendtime(CommonTool.getCurrTime());
		curMgoodssendinfo.setSendopationerid(CommonTool.getLoginInfo("USERID"));
		curMgoodssendinfo.setSendopationdate(CommonTool.getCurrDate());
		curMgoodssendinfo.setSendstate(1);
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDgoodssendinfo=this.ic010Service.getDataTool().loadDTOList(strJsonParam, Dgoodssendinfo.class);
			if(lsDgoodssendinfo!=null && lsDgoodssendinfo.size()>0)
			{
				for(int i=0;i<lsDgoodssendinfo.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodssendinfo.get(i).getSendgoodsno()).equals(""))
					{
						lsDgoodssendinfo.get(i).setId(new DgoodssendinfoId(curMgoodssendinfo.getId().getSendcompid(),curMgoodssendinfo.getId().getSendbillid(),i*1.0));
					}
				}
				
			}
		}
		if(!CommonTool.FormatString(strJsonPcParam).equals(""))
		{
			this.lsDgoodsinsertpc=this.ic010Service.getDataTool().loadDTOList(strJsonPcParam, Dgoodsinsertpc.class);
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
		if(!CommonTool.FormatString(strJsonBarParam).equals(""))
		{
			this.lsDgoodssendbarinfo=this.ic010Service.getDataTool().loadDTOList(strJsonBarParam, Dgoodssendbarinfo.class);
			if(lsDgoodssendbarinfo!=null && lsDgoodssendbarinfo.size()>0)
			{
				for(int i=0;i<lsDgoodssendbarinfo.size();i++)
				{
					if(CommonTool.FormatString(lsDgoodssendbarinfo.get(i).getSendgoodsno()).equals(""))
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
			if(this.ic010Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC010", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.beforeComfirm();
			if(flag==false)
			{
				return SystemFinal.POST_FAILURE;
			}
			flag=this.ic010Service.postInfo(this.curMgoodssendinfo, this.lsDgoodssendinfo,this.lsDgoodsinsertpc,this.lsDgoodssendbarinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curMgoodssendinfo=null;
			lsDgoodssendinfo=null;
			lsDgoodsinsertpc=null;
			lsDgoodssendbarinfo=null;
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
			if(this.ic010Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC010", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.ic010Service.handcancelInfo(this.strCurCompId, this.strCurBillId,this.strOrderCompId,this.strOrderBillId);
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
			this.lsMgoodssendinfo=this.ic010Service.loadMgoodsorderinfo();
			if(lsMgoodssendinfo!=null && lsMgoodssendinfo.size()>0)
			{
				for(int i=0;i<lsMgoodssendinfo.size();i++)
				{
					lsMgoodssendinfo.get(i).setBsendcompid(lsMgoodssendinfo.get(i).getId().getSendcompid());
					lsMgoodssendinfo.get(i).setOrdercompname(this.ic010Service.getDataTool().loadCompNameById(lsMgoodssendinfo.get(i).getOrdercompid()));
					lsMgoodssendinfo.get(i).setBsendbillid(lsMgoodssendinfo.get(i).getId().getSendbillid());
					lsMgoodssendinfo.get(i).setSenddate(CommonTool.getDateMask(lsMgoodssendinfo.get(i).getSenddate()));
					lsMgoodssendinfo.get(i).setSendtime(CommonTool.getTimeMask(lsMgoodssendinfo.get(i).getSendtime(),1));
					lsMgoodssendinfo.get(i).setHeadwarename(this.ic010Service.getDataTool().loadCompWareNameById(lsMgoodssendinfo.get(i).getId().getSendcompid(),lsMgoodssendinfo.get(i).getHeadwareid()));
				}
			}
			else
			{
				lsMgoodssendinfo=new ArrayList();
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
			lsMgoodssendinfo=this.ic010Service.loadlsMgoodssendinfoById(CommonTool.getLoginInfo("COMPID"),this.strSearchSendBillno,this.strSearchOrderBillno);
			if(lsMgoodssendinfo!=null && lsMgoodssendinfo.size()>0)
			{
				for(int i=0;i<lsMgoodssendinfo.size();i++)
				{
					lsMgoodssendinfo.get(i).setBsendcompid(lsMgoodssendinfo.get(i).getId().getSendcompid());
					lsMgoodssendinfo.get(i).setBsendbillid(lsMgoodssendinfo.get(i).getId().getSendbillid());
					lsMgoodssendinfo.get(i).setSenddate(CommonTool.getDateMask(lsMgoodssendinfo.get(i).getSenddate()));
					lsMgoodssendinfo.get(i).setSendtime(CommonTool.getTimeMask(lsMgoodssendinfo.get(i).getSendtime(),1));
					lsMgoodssendinfo.get(i).setOrderdate(CommonTool.getDateMask(lsMgoodssendinfo.get(i).getOrderdate()));
					lsMgoodssendinfo.get(i).setOrdertime(CommonTool.getTimeMask(lsMgoodssendinfo.get(i).getOrdertime(),1));
					lsMgoodssendinfo.get(i).setHeadwarename(this.ic010Service.getDataTool().loadCompWareNameById(lsMgoodssendinfo.get(i).getId().getSendcompid(),lsMgoodssendinfo.get(i).getHeadwareid()));
				}
			}
			else
			{
				lsMgoodssendinfo=new ArrayList();
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
			this.curMgoodssendinfo=this.ic010Service.loadMgoodssendinfoById(this.strCurCompId, this.strCurBillId,"");
			if(curMgoodssendinfo!=null && curMgoodssendinfo.getId()!=null)
			{
				curMgoodssendinfo.setBbsendcompname(this.ic010Service.getDataTool().loadCompNameById(curMgoodssendinfo.getBsendcompid()));
				curMgoodssendinfo.setOrdercompname(this.ic010Service.getDataTool().loadCompNameById(curMgoodssendinfo.getOrdercompid()));
				if(CommonTool.FormatString(curMgoodssendinfo.getSendstaffid()).equals(""))
				{
					curMgoodssendinfo.setSendstaffid(CommonTool.getLoginInfo("USERID"));
				}
				StringBuffer validatemsg=new StringBuffer();
				curMgoodssendinfo.setSendstaffname(this.ic010Service.getDataTool().loadEmpNameById(this.curMgoodssendinfo.getBsendcompid(), this.curMgoodssendinfo.getSendstaffid(),validatemsg));
				curMgoodssendinfo.setStorestaffname(this.ic010Service.getDataTool().loadEmpNameById(this.curMgoodssendinfo.getOrdercompid(), this.curMgoodssendinfo.getStorestaffid(),validatemsg));
				curMgoodssendinfo.setStorewarename(this.ic010Service.getDataTool().loadCompWareNameById(curMgoodssendinfo.getOrdercompid(),curMgoodssendinfo.getStorewareid()));
				curMgoodssendinfo.setHeadwarename(this.ic010Service.getDataTool().loadCompWareNameById(curMgoodssendinfo.getBsendcompid(),curMgoodssendinfo.getHeadwareid()));
				curMgoodssendinfo.setStoreaddress(this.ic010Service.getDataTool().loadCompAddressById(curMgoodssendinfo.getOrdercompid()));
				validatemsg=null;
			}
			else
			{
				curMgoodssendinfo=new Mgoodssendinfo(); 
			}
		
			
			//this.lsDgoodssendinfo=this.ic010Service.loadDgoodssendinfo(this.strCurCompId, this.strCurBillId);
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
			String strGoodsNo=this.ic010Service.validatFromBarCode(this.strStartBarNo);
			if(CommonTool.FormatString(strGoodsNo).equals(""))
			{
				this.strMessage="该条码编号不存在或状态不正确,请确认";
				return SystemFinal.LOAD_SUCCESS;
			}
			this.curGoodsinfo=this.ic010Service.getDataTool().loadGoodsInfo(this.strCurCompId, strGoodsNo);
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
			if(CommonTool.FormatInteger(isOneBarFlag)==0)
			{
				boolean flag=this.ic010Service.validatEndBarCode(this.strCurGoodsId, strEndBarNo);
				if(flag==false)
				{
					this.strMessage="该条码对应的产品代码不是"+strCurGoodsId+",请确认!";
					return SystemFinal.LOAD_SUCCESS;
				}
			}
			else
			{
				strCurGoodsId=this.ic010Service.validatFromBarCode(this.strStartBarNo);
			}
			StringBuffer msgBuf=new StringBuffer();
			this.goodsbarcount=this.ic010Service.validatBarCode(this.strStartBarNo,this.strEndBarNo,msgBuf);
			this.strMessage=msgBuf.toString();
			this.lsDgoodsinsertpc=this.ic010Service.loadGoodsPc(this.strStartBarNo,this.strEndBarNo);
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
			this.strCurEmpName=this.ic010Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "validatedowner",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validatedowner()
	{
		try
		{
			StringBuffer validateMsg=new StringBuffer();
			this.strCurEmpName=this.ic010Service.getDataTool().loadEmpNameById(CommonTool.getLoginInfo("COMPID"), this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
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
			this.lsDgoodsinsertpc=this.ic010Service.loadDgoodsinsertpc(strCurCompId, strCurGoodsId, strWareId);
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
	public IC010Service getIc010Service() {
		return ic010Service;
	}
	@JSON(serialize=false)
	public void setIc010Service(IC010Service ic010Service) {
		this.ic010Service = ic010Service;
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
	public List<Mgoodssendinfo> getLsMgoodssendinfo() {
		return lsMgoodssendinfo;
	}
	public void setLsMgoodssendinfo(List<Mgoodssendinfo> lsMgoodssendinfo) {
		this.lsMgoodssendinfo = lsMgoodssendinfo;
	}
	public List<Dgoodssendinfo> getLsDgoodssendinfo() {
		return lsDgoodssendinfo;
	}
	public void setLsDgoodssendinfo(List<Dgoodssendinfo> lsDgoodssendinfo) {
		this.lsDgoodssendinfo = lsDgoodssendinfo;
	}
	public Mgoodssendinfo getCurMgoodssendinfo() {
		return curMgoodssendinfo;
	}
	public void setCurMgoodssendinfo(Mgoodssendinfo curMgoodssendinfo) {
		this.curMgoodssendinfo = curMgoodssendinfo;
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
	public int getGoodsbarcount() {
		return goodsbarcount;
	}
	public void setGoodsbarcount(int goodsbarcount) {
		this.goodsbarcount = goodsbarcount;
	}
	public Goodsinfo getCurGoodsinfo() {
		return curGoodsinfo;
	}
	public void setCurGoodsinfo(Goodsinfo curGoodsinfo) {
		this.curGoodsinfo = curGoodsinfo;
	}
	public String getStrJsonBarParam() {
		return strJsonBarParam;
	}
	public void setStrJsonBarParam(String strJsonBarParam) {
		this.strJsonBarParam = strJsonBarParam;
	}
	public List<Dgoodssendbarinfo> getLsDgoodssendbarinfo() {
		return lsDgoodssendbarinfo;
	}
	public void setLsDgoodssendbarinfo(List<Dgoodssendbarinfo> lsDgoodssendbarinfo) {
		this.lsDgoodssendbarinfo = lsDgoodssendbarinfo;
	}
	

}
