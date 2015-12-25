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
import com.amani.model.Dgoodsbarinfo;
import com.amani.model.Compwarehouse;
import com.amani.model.Dgoodsinventory;
import com.amani.model.DgoodsinventoryId;
import com.amani.model.Goodsinfo;
import com.amani.model.Mgoodsinventory;
import com.amani.model.MgoodsinventoryId;
import com.amani.model.Staffinfo;
import com.amani.service.InvoicingControl.IC003Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic003")
public class IC003Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IC003Service ic003Service;
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
	private String strCurGoodsId;
	private int barcodeCount;
	private String strIndexBar;
	private String strCurCompName;
	private String strCurEmpId;
	private String strCurEmpName;
	private List<Staffinfo> lsStaffinfo;
	private List<Mgoodsinventory> lsMgoodsinventory;
	private List<Dgoodsinventory> lsDgoodsinventory;
	private List<Dgoodsbarinfo> lsDgoodsbarinfo;
	private Mgoodsinventory curMgoodsinventory;
	private List<Compwarehouse> lsCompwarehouses;
	private String factcInventware;//仓库下拉框
	private String strCardfrom;
	private String strCardend;
	private String strWareId;
	private String strCardType;
	private int cardIdLength;//系统参数中设定的会员卡序号长度
    private String  numberOfCardFilter;//卡需要过滤的数字
    private String strStartBarNo;
    private String strEndBarNo;
    
    private int    goodsbarcount;		//起始条码间的数量
    private Goodsinfo curGoodsinfo;		//当前产品信息
    private BigDecimal curgoodsstock;  //当前库存量
	private String strCurDate;
    private int  	useBarFlag;			//是否启用条码
    
    private String strCurInserBillNo;
    private String strCurOuterBillNo;
		public String getStrCurInserBillNo() {
		return strCurInserBillNo;
	}
	public void setStrCurInserBillNo(String strCurInserBillNo) {
		this.strCurInserBillNo = strCurInserBillNo;
	}
	public String getStrCurOuterBillNo() {
		return strCurOuterBillNo;
	}
	public void setStrCurOuterBillNo(String strCurOuterBillNo) {
		this.strCurOuterBillNo = strCurOuterBillNo;
	}
		public String getFactcInventware() {
		return factcInventware;
	}
	public void setFactcInventware(String factcInventware) {
		this.factcInventware = factcInventware;
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
		if(!CommonTool.FormatString(this.factcInventware).equals(""))
		{
			this.curMgoodsinventory.setInventwareid(factcInventware);
		}
		curMgoodsinventory.setInventdate(CommonTool.setDateMask(curMgoodsinventory.getInventdate()));
		curMgoodsinventory.setInventtime(CommonTool.setTimeMask(curMgoodsinventory.getInventtime(), 1));
		curMgoodsinventory.setInventopationdate(CommonTool.getCurrDate());
		curMgoodsinventory.setInventopationerid(CommonTool.getLoginInfo("USERID"));
		curMgoodsinventory.setInventflag(1);
		curMgoodsinventory.getId().setInventbillid(this.ic003Service.getDataTool().loadBillIdByRule(curMgoodsinventory.getId().getInventcompid(),"mgoodsinventory", "inventbillid", "SP012"));
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDgoodsinventory=this.ic003Service.getDataTool().loadDTOList(strJsonParam, Dgoodsinventory.class);
			if(lsDgoodsinventory!=null && lsDgoodsinventory.size()>0)
			{
				for(int i=0;i<lsDgoodsinventory.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodsinventory.get(i).getInventgoodsno()).equals(""))
					{
						lsDgoodsinventory.get(i).setId(new DgoodsinventoryId(curMgoodsinventory.getId().getInventcompid(),curMgoodsinventory.getId().getInventbillid(),i*1.0));
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
			if(this.ic003Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC003", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			Mgoodsinventory obj=new Mgoodsinventory();
			obj.setId(new MgoodsinventoryId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.ic003Service.delete(obj);
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
			if(this.ic003Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC003", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.ic003Service.postInfo(this.curMgoodsinventory, this.lsDgoodsinventory);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curMgoodsinventory=null;
			lsDgoodsinventory=null;
			lsDgoodsbarinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	
	@Action(value = "editInventInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
           @Result(name = "post_failure", type = "json")	
        }) 
	public String editInventInfo()
	{
		try
		{
			this.strMessage="";
			if(this.ic003Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC003", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(!CommonTool.FormatString(strJsonParam).equals(""))
			{
				this.lsDgoodsinventory=this.ic003Service.getDataTool().loadDTOList(strJsonParam, Dgoodsinventory.class);
				if(lsDgoodsinventory!=null && lsDgoodsinventory.size()>0)
				{
					for(int i=0;i<lsDgoodsinventory.size();i++)
					{
						if(!CommonTool.FormatString(lsDgoodsinventory.get(i).getInventgoodsno()).equals(""))
						{
							lsDgoodsinventory.get(i).setId(new DgoodsinventoryId(strCurCompId,strCurBillId,i*1.0));
						}
					}
				}
			}
			StringBuffer inserBuf=new StringBuffer();
			StringBuffer outerBuf=new StringBuffer();
			boolean flag=this.ic003Service.postInventInfo(this.strCurCompId,this.strCurBillId,this.strCurEmpId,this.strWareId,this.lsDgoodsinventory,inserBuf,outerBuf);
			this.strCurInserBillNo=inserBuf.toString();
			this.strCurOuterBillNo=outerBuf.toString();
			inserBuf=null;
			outerBuf=null;
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			lsDgoodsbarinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	@Action(value = "loadCardInventInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadCardInventInfo()
	{
		try
		{
			this.lsMgoodsinventory=this.ic003Service.loadMasterDataSet(30, 0);
			if(lsMgoodsinventory!=null && lsMgoodsinventory.size()>0)
			{
				for(int i=0;i<lsMgoodsinventory.size();i++)
				{
					lsMgoodsinventory.get(i).setBinventcompid(lsMgoodsinventory.get(i).getId().getInventcompid());
					lsMgoodsinventory.get(i).setBinventbillid(lsMgoodsinventory.get(i).getId().getInventbillid());
					lsMgoodsinventory.get(i).setInventdate(CommonTool.getDateMask(lsMgoodsinventory.get(i).getInventdate()));
					lsMgoodsinventory.get(i).setInventopationdate(CommonTool.getDateMask(lsMgoodsinventory.get(i).getInventopationdate()));
					lsMgoodsinventory.get(i).setInventtime(CommonTool.getTimeMask(lsMgoodsinventory.get(i).getInventtime(),1));
				}
				
			}
			else
			{
				this.curMgoodsinventory=this.ic003Service.addMastRecord();
				curMgoodsinventory.setBinventcompname(this.ic003Service.getDataTool().loadCompNameById(curMgoodsinventory.getBinventcompid()));
				this.lsMgoodsinventory=new ArrayList();
				this.lsMgoodsinventory.add(curMgoodsinventory);
			}
			this.lsCompwarehouses=this.ic003Service.getDataTool().loadCompWareById(CommonTool.getLoginInfo("COMPID"));
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
			lsMgoodsinventory=new ArrayList();
			Mgoodsinventory record=this.ic003Service.loadMgoodsinventoryById(this.strCurCompId,this.strCurBillId);
			if(record!=null && record.getId()!=null)
				lsMgoodsinventory.add(record);
			record=null;
			if(lsMgoodsinventory!=null && lsMgoodsinventory.size()>0)
			{
				for(int i=0;i<lsMgoodsinventory.size();i++)
				{
					lsMgoodsinventory.get(i).setBinventcompid(lsMgoodsinventory.get(i).getId().getInventcompid());
					lsMgoodsinventory.get(i).setBinventbillid(lsMgoodsinventory.get(i).getId().getInventbillid());
					lsMgoodsinventory.get(i).setInventdate(CommonTool.getDateMask(lsMgoodsinventory.get(i).getInventdate()));
					lsMgoodsinventory.get(i).setInventopationdate(CommonTool.getDateMask(lsMgoodsinventory.get(i).getInventopationdate()));
					lsMgoodsinventory.get(i).setInventtime(CommonTool.getTimeMask(lsMgoodsinventory.get(i).getInventtime(),1));
				}
				
			}
			else
			{
				this.curMgoodsinventory=this.ic003Service.addMastRecord();
				curMgoodsinventory.setBinventcompname(this.ic003Service.getDataTool().loadCompNameById(curMgoodsinventory.getBinventcompid()));
				this.lsMgoodsinventory=new ArrayList();
				this.lsMgoodsinventory.add(curMgoodsinventory);
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		} 
	}
	
	
	@Action(value = "loadDcardnoinvent",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadDcardnoinvent()
	{
		try
		{
			this.curMgoodsinventory=this.ic003Service.loadMgoodsinventoryById(this.strCurCompId, this.strCurBillId);
			if(curMgoodsinventory==null || curMgoodsinventory.getId()==null)
				curMgoodsinventory=this.ic003Service.addMastRecord();
			curMgoodsinventory.setBinventcompname(this.ic003Service.getDataTool().loadCompNameById(curMgoodsinventory.getBinventcompid()));
			StringBuffer validatemsg=new StringBuffer();
			curMgoodsinventory.setInventstaffname(this.ic003Service.getDataTool().loadEmpNameById(this.curMgoodsinventory.getBinventcompid(), this.curMgoodsinventory.getInventstaffid(),validatemsg));
			validatemsg=null;
			this.lsDgoodsinventory=this.ic003Service.loadDgoodsinventorys(this.strCurCompId, this.strCurBillId);
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
			this.curMgoodsinventory=this.ic003Service.addMastRecord();
			curMgoodsinventory.setBinventcompname(this.ic003Service.getDataTool().loadCompNameById(curMgoodsinventory.getBinventcompid()));
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
			this.strCurEmpName=this.ic003Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
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
			boolean flag=this.ic003Service.loadNewBarCodeBySeqno(this.strIndexBar,this.strCurGoodsId, this.barcodeCount, startNoBuf, endNoBuf);
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
	
	@Action(value = "validateGoodsNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateGoodsNo()
	{
		try
		{
			this.strMessage="";
	
		
			this.curGoodsinfo=this.ic003Service.getDataTool().loadGoodsInfo(this.strCurCompId, this.strCurGoodsId);
			if(curGoodsinfo==null || curGoodsinfo.getId()==null)
			{
				this.strMessage="该产品编号不存在,请确认";
				return SystemFinal.LOAD_SUCCESS;
			}
			this.curgoodsstock=this.ic003Service.getDataTool().loadCurStock(this.strCurCompId, this.strCurDate, strCurGoodsId, strWareId);
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
	public IC003Service getIc003Service() {
		return ic003Service;
	}
	@JSON(serialize=false)
	public void setIc003Service(IC003Service ic003Service) {
		this.ic003Service = ic003Service;
	}
	

	

	public List<Mgoodsinventory> getLsMgoodsinventory() {
		return lsMgoodsinventory;
	}
	public void setLsMgoodsinventory(List<Mgoodsinventory> lsMgoodsinventory) {
		this.lsMgoodsinventory = lsMgoodsinventory;
	}
	public List<Dgoodsinventory> getLsDgoodsinventory() {
		return lsDgoodsinventory;
	}
	public void setLsDgoodsinventory(List<Dgoodsinventory> lsDgoodsinventory) {
		this.lsDgoodsinventory = lsDgoodsinventory;
	}
	public Mgoodsinventory getCurMgoodsinventory() {
		return curMgoodsinventory;
	}
	public void setCurMgoodsinventory(Mgoodsinventory curMgoodsinventory) {
		this.curMgoodsinventory = curMgoodsinventory;
	}
	public List<Dgoodsbarinfo> getLsDgoodsbarinfo() {
		return lsDgoodsbarinfo;
	}
	public void setLsDgoodsbarinfo(List<Dgoodsbarinfo> lsDgoodsbarinfo) {
		this.lsDgoodsbarinfo = lsDgoodsbarinfo;
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
	public int getUseBarFlag() {
		return useBarFlag;
	}
	public void setUseBarFlag(int useBarFlag) {
		this.useBarFlag = useBarFlag;
	}


}
