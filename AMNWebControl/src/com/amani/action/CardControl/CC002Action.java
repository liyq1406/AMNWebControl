package com.amani.action.CardControl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;

import com.amani.model.CardTypeAnlisys;
import com.amani.model.Cardtypeinfo;
import com.amani.model.Dcardapponline;
import com.amani.model.DcardapponlineId;

import com.amani.model.Mcardapponline;
import com.amani.model.McardapponlineId;
import com.amani.service.CardControl.CC002Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc002")
public class CC002Action extends AMN_ModuleAction{
	@Autowired
	private CC002Service cc002Service;
	private String strJsonParam;	
	
    private String strCurCompId;
    private String strCurBillId;
    private int    iState;
	private String strCardType;
	private String strCurEmpId;
	private String strCurEmpName;
	private List<Mcardapponline> lsMcardapponline;
	private List<Dcardapponline> lsDcardapponline;
	private List<CardTypeAnlisys> lsCardTypeAnlisys;
	private Mcardapponline curMcardappInfo;
	private List<Cardtypeinfo> lsCardtypeinfos;
	public List<Cardtypeinfo> getLsCardtypeinfos() {
		return lsCardtypeinfos;
	}
	public void setLsCardtypeinfos(List<Cardtypeinfo> lsCardtypeinfos) {
		this.lsCardtypeinfos = lsCardtypeinfos;
	}
	public Mcardapponline getCurMcardappInfo() {
		return curMcardappInfo;
	}
	public void setCurMcardappInfo(Mcardapponline curMcardappInfo) {
		this.curMcardappInfo = curMcardappInfo;
	}
	public List<Mcardapponline> getLsMcardapponline() {
		return lsMcardapponline;
	}
	public void setLsMcardapponline(List<Mcardapponline> lsMcardapponline) {
		this.lsMcardapponline = lsMcardapponline;
	}
	public List<Dcardapponline> getLsDcardapponline() {
		return lsDcardapponline;
	}
	public void setLsDcardapponline(List<Dcardapponline> lsDcardapponline) {
		this.lsDcardapponline = lsDcardapponline;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	public String getStrCurBillId() {
		return strCurBillId;
	}
	public void setStrCurBillId(String strCurBillId) {
		this.strCurBillId = strCurBillId;
	}
	public int getIState() {
		return iState;
	}
	public void setIState(int state) {
		iState = state;
	}
	public String getStrCardType() {
		return strCardType;
	}
	public void setStrCardType(String strCardType) {
		this.strCardType = strCardType;
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
		
		curMcardappInfo.setCappopationper(CommonTool.getLoginInfo("USERID"));
		curMcardappInfo.setCappopationdate(CommonTool.getCurrDate());
		curMcardappInfo.setCappdate(CommonTool.setDateMask(curMcardappInfo.getCappdate()));
		curMcardappInfo.setCapptime(CommonTool.setTimeMask(curMcardappInfo.getCapptime(),1));
		curMcardappInfo.getId().setCappcompbillid(this.cc002Service.getDataTool().loadBillIdByRule(curMcardappInfo.getId().getCappcompid(),"mcardapponline", "cappcompbillid", "SP012"));
		this.lsDcardapponline=this.cc002Service.getDataTool().loadDTOList(strJsonParam, Dcardapponline.class);
		if(lsDcardapponline!=null && lsDcardapponline.size()>0)
		{
			for(int i=0;i<lsDcardapponline.size();i++)
			{
				if(!CommonTool.FormatString(lsDcardapponline.get(i).getCappcardtypeid()).equals(""))
				{
					lsDcardapponline.get(i).setId(new DcardapponlineId(curMcardappInfo.getId().getCappcompid(),curMcardappInfo.getId().getCappcompbillid(),i*1.0));
				}
				else
				{
					lsDcardapponline.remove(i);
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
	@JSON(serialize=false)
	public CC002Service getCc002Service() {
		return cc002Service;
	}
	@JSON(serialize=false)
	public void setCc002Service(CC002Service cc002Service) {
		this.cc002Service = cc002Service;
	}
	
	
	@Action(value = "loadMcardappInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadMcardappInfo()
	{
		try
		{
			this.lsMcardapponline=this.cc002Service.loadMasterDateById(this.strCurCompId, this.strCurBillId, this.iState);
			if(lsMcardapponline!=null && lsMcardapponline.size()>0)
			{
				this.curMcardappInfo=lsMcardapponline.get(0);
				StringBuffer validatemsg=new StringBuffer();
				this.curMcardappInfo.setCappempText(this.cc002Service.getDataTool().loadEmpNameById(this.strCurCompId,curMcardappInfo.getCappempid(),validatemsg));
				validatemsg=null;
				curMcardappInfo.setBcappcompidText(this.cc002Service.getDataTool().loadCompNameById(curMcardappInfo.getBcappcompid()));
			}
			else
			{
				this.curMcardappInfo=this.cc002Service.addMastRecord(strCurCompId);
				curMcardappInfo.setBcappcompidText(this.cc002Service.getDataTool().loadCompNameById(curMcardappInfo.getBcappcompid()));
				this.lsMcardapponline=new ArrayList();
				this.lsMcardapponline.add(curMcardappInfo);
			}
			this.lsDcardapponline=this.cc002Service.loadDetialById(curMcardappInfo.getBcappcompid(), curMcardappInfo.getBcappcompbillid());
			this.lsCardtypeinfos=this.cc002Service.getDataTool().loadCardTypeApp(this.curMcardappInfo.getBcappcompid());
			this.lsCardTypeAnlisys=this.cc002Service.loadCardTypeAnlisys(strCurCompId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "loadDcardappInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadDcardappInfo()
	{
		try
		{	
			List<Mcardapponline> lsRecord=this.cc002Service.loadMasterDateById(this.strCurCompId, this.strCurBillId,3);
			if(lsRecord!=null && lsRecord.size()>0)
			{
				this.curMcardappInfo=lsRecord.get(0);
			}
			else
			{
				this.curMcardappInfo=this.cc002Service.addMastRecord(this.strCurCompId);
			}
			curMcardappInfo.setBcappcompidText(this.cc002Service.getDataTool().loadCompNameById(curMcardappInfo.getBcappcompid()));
			StringBuffer validatemsg=new StringBuffer();
			this.curMcardappInfo.setCappempText(this.cc002Service.getDataTool().loadEmpNameById(curMcardappInfo.getBcappcompid(),curMcardappInfo.getCappempid(),validatemsg));
			validatemsg=null;
			this.lsDcardapponline=this.cc002Service.loadDetialById(this.strCurCompId, this.strCurBillId);
			lsRecord=null;
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
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
			if(this.cc002Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC002", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			Mcardapponline obj=new Mcardapponline();
			obj.setId(new McardapponlineId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.cc002Service.delete(obj);
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
			if(this.cc002Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC002", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.cc002Service.post(this.curMcardappInfo, this.lsDcardapponline);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curMcardappInfo=null;
			lsDcardapponline=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	@Action(value = "confirm",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String confirm()
	{
		try
		{
			this.strMessage="";
			if(this.cc002Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC002", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
	
			boolean flag=this.cc002Service.postConfirm(this.strCurCompId, this.strCurBillId);
			if(flag==false)
			{
				this.strMessage=SystemFinal.OPERATION_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
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
			this.curMcardappInfo=this.cc002Service.addMastRecord(this.strCurCompId);
			curMcardappInfo.setBcappcompidText(this.cc002Service.getDataTool().loadCompNameById(curMcardappInfo.getBcappcompid()));
			return SystemFinal.ADD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.ADD_FAILURE;
		}
	}
	
	@Action(value = "validateAppempid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateAppempid()
	{
		try
		{
			StringBuffer validateMsg=new StringBuffer();
			this.strCurEmpName=this.cc002Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	public List<CardTypeAnlisys> getLsCardTypeAnlisys() {
		return lsCardTypeAnlisys;
	}
	public void setLsCardTypeAnlisys(List<CardTypeAnlisys> lsCardTypeAnlisys) {
		this.lsCardTypeAnlisys = lsCardTypeAnlisys;
	}
}
