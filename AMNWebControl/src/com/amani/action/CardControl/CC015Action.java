package com.amani.action.CardControl;

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
import com.amani.model.Dpayoutinfo;
import com.amani.model.DpayoutinfoId;
import com.amani.model.Mpayoutinfo;
import com.amani.service.CardControl.CC015Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc015")
public class CC015Action extends AMN_ModuleAction{
	@Autowired
	private CC015Service cc015Service;
	private String strJsonParam;	
	private String strCurPackageNo;
    private List<Mpayoutinfo> lsMpayoutinfo;
    private List<Dpayoutinfo> lsDpayoutinfo;
    private List<Dpayoutinfo> lsDataSet;
    private List<Dpayoutinfo> lsCDataSet;
    private Mpayoutinfo curMpayoutinfo;
    private String strCurCompId;
    private String strCurBillId;
    private String strFromDate;
    private String strToDate;
    private String strFromItemNo;
    private String strToItemNo;
    private int		iItemState;
    public String getStrFromDate() {
		return strFromDate;
	}
	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}
	public String getStrToDate() {
		return strToDate;
	}
	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}
	public String getStrFromItemNo() {
		return strFromItemNo;
	}
	public void setStrFromItemNo(String strFromItemNo) {
		this.strFromItemNo = strFromItemNo;
	}
	public String getStrToItemNo() {
		return strToItemNo;
	}
	public void setStrToItemNo(String strToItemNo) {
		this.strToItemNo = strToItemNo;
	}
	public int getIItemState() {
		return iItemState;
	}
	public void setIItemState(int itemState) {
		iItemState = itemState;
	}
	public String getStrCurPackageNo() {
		return strCurPackageNo;
	}
	public void setStrCurPackageNo(String strCurPackageNo) {
		this.strCurPackageNo = strCurPackageNo;
	}
	
	public List<Mpayoutinfo> getLsMpayoutinfo() {
		return lsMpayoutinfo;
	}
	public void setLsMpayoutinfo(List<Mpayoutinfo> lsMpayoutinfo) {
		this.lsMpayoutinfo = lsMpayoutinfo;
	}
	public List<Dpayoutinfo> getLsDpayoutinfo() {
		return lsDpayoutinfo;
	}
	public void setLsDpayoutinfo(List<Dpayoutinfo> lsDpayoutinfo) {
		this.lsDpayoutinfo = lsDpayoutinfo;
	}
	public Mpayoutinfo getCurMpayoutinfo() {
		return curMpayoutinfo;
	}
	public void setCurMpayoutinfo(Mpayoutinfo curMpayoutinfo) {
		this.curMpayoutinfo = curMpayoutinfo;
	}

	
	@Action(value = "loadMaster",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadMaster()
	{
		this.lsMpayoutinfo=this.cc015Service.loadMaster(strCurCompId);
		if(lsMpayoutinfo!=null && lsMpayoutinfo.size()>0)
		{
			for(int i=0;i<lsMpayoutinfo.size();i++)
			{
				lsMpayoutinfo.get(i).setBpayoutcompid(lsMpayoutinfo.get(i).getId().getPayoutcompid());
				lsMpayoutinfo.get(i).setBpayoutbillid(lsMpayoutinfo.get(i).getId().getPayoutbillid());
			}
		}
		if(lsMpayoutinfo==null || lsMpayoutinfo.size()==0)
		{
			lsMpayoutinfo=new ArrayList();
			lsMpayoutinfo.add(this.cc015Service.addMpayoutinfoinfo(strCurCompId));
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadCurDetialinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurDetialinfo()
	{
		this.lsDpayoutinfo=this.cc015Service.loadDetial(this.strCurCompId, this.strCurBillId);
		return SystemFinal.LOAD_SUCCESS;
	
	}
	
	
	@Action(value = "loadDataSet",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadDataSet()
	{
	    if (this.strFromDate.equals(""))
	        this.strFromDate = CommonTool.getCurrDate();
	      if (this.strToDate.equals(""))
	        this.strToDate = CommonTool.getCurrDate();
		this.lsDataSet=this.cc015Service.loadSearchDetial(this.strCurCompId, this.strFromDate, strToDate, strFromItemNo, strToItemNo, iItemState);
		return SystemFinal.LOAD_SUCCESS;
	
	}
	
	@Action(value = "loadCDataSet",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCDataSet()
	{
	    if (this.strFromDate.equals(""))
	        this.strFromDate = CommonTool.getCurrDate();
	      if (this.strToDate.equals(""))
	        this.strToDate = CommonTool.getCurrDate();
		this.lsCDataSet=this.cc015Service.loadConfirmDetial(this.strCurCompId, this.strFromDate, strToDate, iItemState);
		return SystemFinal.LOAD_SUCCESS;
	
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
			if(this.cc015Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "cc015", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.cc015Service.postInfo(this.curMpayoutinfo, this.lsDpayoutinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			
			curMpayoutinfo=null;
			lsDpayoutinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	@Action(value = "ConfirmDataSet",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	}) 
	public String ConfirmDataSet()
	{
		this.strMessage="";
		if(this.iItemState==1 && this.cc015Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX017")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		else if(this.iItemState==2 && this.cc015Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX018")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		else if(this.iItemState==3 && this.cc015Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX019")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		if(!strJsonParam.equals(""))
		{
			this.lsDpayoutinfo=this.cc015Service.getDataTool().loadDTOList(strJsonParam, Dpayoutinfo.class);
			boolean flag=this.cc015Service.PostComfirmBill(iItemState, lsDpayoutinfo);
			if(flag==false)
				this.strMessage="审核失败!";
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	
	@Action(value = "CallbackDataSet",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	}) 
	public String CallbackDataSet()
	{
		this.strMessage="";
		if(this.iItemState==1 && this.cc015Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX017")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		else if(this.iItemState==2 && this.cc015Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX018")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		else if(this.iItemState==3 && this.cc015Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX019")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		if(!strJsonParam.equals(""))
		{
			this.lsDpayoutinfo=this.cc015Service.getDataTool().loadDTOList(strJsonParam, Dpayoutinfo.class);
			boolean flag=this.cc015Service.PostCallbackBill(iItemState, lsDpayoutinfo);
			if(flag==false)
				this.strMessage="审核失败!";
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	
	@Action(value = "add",  results = { 
			 @Result(name = "add_success", type = "json"),
         @Result(name = "add_failure", type = "json")	
      }) 
	@Override
	public String add()
	{
		this.strMessage="";
		this.curMpayoutinfo=this.cc015Service.addMpayoutinfoinfo(this.strCurCompId);
		return SystemFinal.ADD_SUCCESS;
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
		// TODO Auto-generated method stub
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
		return true;
	}
	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforePost() {
		curMpayoutinfo.setPayoutdate(CommonTool.setDateMask(curMpayoutinfo.getPayoutdate()));
		curMpayoutinfo.setPayouttime(CommonTool.setTimeMask(curMpayoutinfo.getPayouttime(), 1));
		curMpayoutinfo.setPayoutopationdate(CommonTool.getCurrDate());
		curMpayoutinfo.setPayoutopationerid(CommonTool.getLoginInfo("USERID"));
		curMpayoutinfo.setBillstate(1);
		if(curMpayoutinfo.getBillstate()==0)
			curMpayoutinfo.getId().setPayoutbillid(this.cc015Service.getDataTool().loadBillIdByRule(curMpayoutinfo.getId().getPayoutcompid(),"mpayoutinfo", "payoutbillid", "SP008"));
		this.lsDpayoutinfo=this.cc015Service.getDataTool().loadDTOList(strJsonParam, Dpayoutinfo.class);
		if(lsDpayoutinfo!=null && lsDpayoutinfo.size()>0)
		{
			for(int i=0;i<lsDpayoutinfo.size();i++)
			{
				if(!CommonTool.FormatString(lsDpayoutinfo.get(i).getPayoutitemid()).equals(""))
				{
					lsDpayoutinfo.get(i).setId(new DpayoutinfoId(this.curMpayoutinfo.getId().getPayoutcompid(),curMpayoutinfo.getId().getPayoutbillid(),i));
					lsDpayoutinfo.get(i).setCheckedopationdate(CommonTool.setDateMask(lsDpayoutinfo.get(i).getCheckedopationdate()));
					lsDpayoutinfo.get(i).setConfirmopationdate(CommonTool.setDateMask(lsDpayoutinfo.get(i).getConfirmopationdate()));
					lsDpayoutinfo.get(i).setPayoutbillstate(0);
				}
				else
				{
					lsDpayoutinfo.remove(i);
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
	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean postActive() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}

	
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
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	
	@JSON(serialize=false)
	public CC015Service getCc015Service() {
		return cc015Service;
	}
	@JSON(serialize=false)
	public void setCc015Service(CC015Service cc015Service) {
		this.cc015Service = cc015Service;
	}
	public String getStrCurBillId() {
		return strCurBillId;
	}
	public void setStrCurBillId(String strCurBillId) {
		this.strCurBillId = strCurBillId;
	}
	public List<Dpayoutinfo> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<Dpayoutinfo> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public List<Dpayoutinfo> getLsCDataSet() {
		return lsCDataSet;
	}
	public void setLsCDataSet(List<Dpayoutinfo> lsCDataSet) {
		this.lsCDataSet = lsCDataSet;
	}
	
	
	
}
