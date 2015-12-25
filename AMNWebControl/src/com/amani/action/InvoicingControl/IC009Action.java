package com.amani.action.InvoicingControl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
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
import com.amani.model.Mgoodsorderinfo;
import com.amani.model.Staffinfo;
import com.amani.service.InvoicingControl.IC009Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SysSendMsg;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic009")
public class IC009Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IC009Service ic009Service;
	@Autowired
	private SysSendMsg sysSendMsg;
	
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
		if(!CommonTool.FormatString(this.factcInsertware).equals(""))
		{
			this.curMgoodsorderinfo.setStorewareid(factcInsertware);
		}
		curMgoodsorderinfo.setOrderdate(CommonTool.setDateMask(curMgoodsorderinfo.getOrderdate()));
		curMgoodsorderinfo.setOrdertime(CommonTool.setTimeMask(curMgoodsorderinfo.getOrdertime(), 1));
		curMgoodsorderinfo.setDownordercompid(CommonTool.getLoginInfo("COMPID"));
		curMgoodsorderinfo.setDownorderdate(CommonTool.getCurrDate());
		curMgoodsorderinfo.setDownordertime(CommonTool.getCurrTime());
		curMgoodsorderinfo.setOrderopationdate(CommonTool.getCurrDate());
		curMgoodsorderinfo.setOrderstate(2);
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDgoodsorderinfo=this.ic009Service.getDataTool().loadDTOList(strJsonParam, Dgoodsorderinfo.class);
			if(lsDgoodsorderinfo!=null && lsDgoodsorderinfo.size()>0)
			{
				for(int i=0;i<lsDgoodsorderinfo.size();i++)
				{
					if(!CommonTool.FormatString(lsDgoodsorderinfo.get(i).getOrdergoodsno()).equals(""))
					{
						lsDgoodsorderinfo.get(i).setId(new DgoodsorderinfoId(curMgoodsorderinfo.getId().getOrdercompid(),curMgoodsorderinfo.getId().getOrderbillid(),i*1.0));
						lsDgoodsorderinfo.get(i).setOrdergoodstype(2);
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
			if(this.ic009Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC009", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.beforeComfirm();
			if(flag==false)
			{
				return SystemFinal.POST_FAILURE;
			}
			String message=this.ic009Service.postInfo(this.curMgoodsorderinfo, this.lsDgoodsorderinfo);
			if(StringUtils.isNotBlank(message))
			{
				this.strMessage=message;
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
			if(this.ic009Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC009", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.ic009Service.handcancelInfo(this.strCurCompId, this.strCurBillId);
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
			this.lsMgoodsorderinfo=this.ic009Service.loadMgoodsorderinfo();
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
				this.curMgoodsorderinfo=this.ic009Service.addMastRecord();
				curMgoodsorderinfo.setBordercompname(this.ic009Service.getDataTool().loadCompNameById(curMgoodsorderinfo.getBordercompid()));
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
	
	@Action(value = "searchOrderBill",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String searchOrderBill()
	{
		try
		{
			lsMgoodsorderinfo=new ArrayList();
			Mgoodsorderinfo record=this.ic009Service.loadMgoodsorderinfoById(CommonTool.getLoginInfo("COMPID"),this.strCurBillId);
			if(record!=null && record.getId()!=null)
				lsMgoodsorderinfo.add(record);
			record=null;
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
				this.curMgoodsorderinfo=this.ic009Service.addMastRecord();
				curMgoodsorderinfo.setBordercompname(this.ic009Service.getDataTool().loadCompNameById(curMgoodsorderinfo.getBordercompid()));
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
			this.curMgoodsorderinfo=this.ic009Service.loadMgoodsorderinfoById(this.strCurCompId, this.strCurBillId);
			if(curMgoodsorderinfo==null || curMgoodsorderinfo.getId()==null)
				curMgoodsorderinfo=this.ic009Service.addMastRecord();
			curMgoodsorderinfo.setBordercompname(this.ic009Service.getDataTool().loadCompNameById(curMgoodsorderinfo.getBordercompid()));
			if(CommonTool.FormatString(curMgoodsorderinfo.getDownordercompid()).equals(""))
			{
				curMgoodsorderinfo.setDownordercompid(CommonTool.getLoginInfo("COMPID"));
				curMgoodsorderinfo.setDownorderstaffid(CommonTool.getLoginInfo("USERID"));
				curMgoodsorderinfo.setDownorderdate(CommonTool.getCurrDate());
				curMgoodsorderinfo.setDownordertime(CommonTool.getCurrTime());
			}
			StringBuffer validatemsg=new StringBuffer();
			curMgoodsorderinfo.setOrderstaffname(this.ic009Service.getDataTool().loadEmpNameById(this.curMgoodsorderinfo.getBordercompid(), this.curMgoodsorderinfo.getOrderstaffid(),validatemsg));
			curMgoodsorderinfo.setDownorderstaffname(this.ic009Service.getDataTool().loadEmpNameById(this.curMgoodsorderinfo.getDownordercompid(), this.curMgoodsorderinfo.getDownorderstaffid(),validatemsg));
			curMgoodsorderinfo.setStorewarename(this.ic009Service.getDataTool().loadCompWareNameById(curMgoodsorderinfo.getBordercompid(),curMgoodsorderinfo.getStorewareid()));
			
			
			validatemsg=null;
			
			this.lsDgoodsorderinfo=this.ic009Service.loadDgoodsorderinfo(this.strCurCompId, this.strCurBillId);
			if(curMgoodsorderinfo.getOrderstate()==1)
			{
				String strWareId=this.ic009Service.loadStoreByOrders(this.strCurCompId, this.strCurBillId);
				String[] strWareIds=strWareId.split(";");
				String strWareNames="";
				String strSendNos="";
				strWareId="";
				HttpSession session = ServletActionContext.getRequest().getSession();
				session.removeAttribute("callMsg");
				Map<String, String> callMsg = new HashMap<String, String>();
				if(strWareIds!=null && strWareIds.length>0)
				{
					for(int i=0;i< strWareIds.length;i++)
					{
						String[] ware = StringUtils.split(strWareIds[i], "#");
						String billId=this.strCurBillId+"_"+ware[0];
						if(ware.length==2 && StringUtils.equals("1", ware[1])){//产品来源为供应商的，则获取供应商名称
							strWareNames+="["+ this.ic009Service.loadSupplierName(ware[0]) +"]";
							String msg = "您好,阿玛尼 "+curMgoodsorderinfo.getBordercompname()+" 订购您公司的产品单号为 "+ billId +"，请及时发货。";
							callMsg.put(ware[0], msg);
						}else{
							strWareNames=strWareNames+"["+this.ic009Service.getDataTool().loadCompWareNameById(CommonTool.getLoginInfo("COMPID"),ware[0])+"]";
						}
						strSendNos=strSendNos+"["+ billId +"]";
						strWareId += ";"+ware[0];
					}
					if(callMsg.size()>0){
						session.setAttribute("callMsg", callMsg);
					}
				}
				curMgoodsorderinfo.setHeadwareid(StringUtils.isNotBlank(strWareId)?strWareId.substring(1):"");
				curMgoodsorderinfo.setHeadwarename(strWareNames);
				curMgoodsorderinfo.setSendbillno(strSendNos);
				if(lsDgoodsorderinfo!=null && lsDgoodsorderinfo.size()>0)
				{
					for(int i=0;i< lsDgoodsorderinfo.size();i++)
					{
						double curOnlineStock=this.ic009Service.loadOnloadGoodstock(CommonTool.getLoginInfo("COMPID"), lsDgoodsorderinfo.get(i).getOrdergoodsno());
						lsDgoodsorderinfo.get(i).setHeadstockcount(CommonTool.FormatBigDecimal(new BigDecimal(this.ic009Service.getDataTool().loadCurStock(CommonTool.getLoginInfo("COMPID"), CommonTool.getCurrDate(), lsDgoodsorderinfo.get(i).getOrdergoodsno(), lsDgoodsorderinfo.get(i).getHeadwareno()).doubleValue()-curOnlineStock)));
						lsDgoodsorderinfo.get(i).setNodowncount(lsDgoodsorderinfo.get(i).getOrdergoodscount());
					}
				}
				strWareIds=null;
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
			this.strCurEmpName=this.ic009Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
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
			this.strCurEmpName=this.ic009Service.getDataTool().loadEmpNameById(CommonTool.getLoginInfo("COMPID"), this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Action(value="send", results={@Result(name="operation_success", type="json")}) 
	public String send(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Object obj = session.getAttribute("callMsg");
		if(obj != null && obj instanceof Map){
			Map<String, String> callMsg = (Map<String, String>) obj;
			if(callMsg.size()>0){
				class CallThread extends Thread{
					private String strCompId;
					private String destMobile;
					private String msgText;
					public CallThread(String strCompId, String destMobile, String msgText) {
						super();
						this.strCompId = strCompId;
						this.destMobile = destMobile;
						this.msgText = msgText;
					}
					public void run() {
						try {
							String result = sysSendMsg.sendFastMsg(strCompId, destMobile, msgText);
							System.out.println(destMobile +"发送结果："+ result);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				for (Map.Entry<String, String> msg : callMsg.entrySet()) {
					String mobile = this.ic009Service.loadSupplierLink(msg.getKey());
					String msginfo = msg.getValue();
					if(StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(msginfo)){//发送供应商送货短信
						Thread thread = new CallThread(CommonTool.getLoginInfo("COMPID"), mobile, msginfo);
						thread.start();
					}
				}
			}
		}
		return SystemFinal.OPERATION_SUCCESS;
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
	public IC009Service getIc009Service() {
		return ic009Service;
	}
	@JSON(serialize=false)
	public void setIc009Service(IC009Service ic009Service) {
		this.ic009Service = ic009Service;
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
