package com.amani.action.PersonnelControl;

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

import com.amani.model.Staffrewardinfo;
import com.amani.model.StaffrewardinfoId;

import com.amani.service.PersonnelControl.CopyOfPC004Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc004bak")
public class CopyOfPC004Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CopyOfPC004Service pc004Service;
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
	private String strCurCompName;
	private String strCurEmpId;
	private String strCurEmpName;
	
	private String strSearchBillno;
	private String strSearchDate;
	private List<Staffrewardinfo> lsStaffrewardinfo;
	private Staffrewardinfo curStaffrewardinfo;

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
		curStaffrewardinfo.setEntrydate(CommonTool.setDateMask(curStaffrewardinfo.getEntrydate()));
		curStaffrewardinfo.setCheckdate(CommonTool.setDateMask(curStaffrewardinfo.getCheckdate()));
		curStaffrewardinfo.setCheckinheaddate(CommonTool.setDateMask(curStaffrewardinfo.getCheckinheaddate()));
		curStaffrewardinfo.setHandstaffinid(this.pc004Service.getDataTool().loadEmpInidById(curStaffrewardinfo.getHandcompid(), curStaffrewardinfo.getHandstaffid()));
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
			if(this.pc004Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "PC004", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			Staffrewardinfo obj=new Staffrewardinfo();
			obj.setId(new StaffrewardinfoId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.pc004Service.delete(obj);
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
			if(this.pc004Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "PC004", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.pc004Service.postRecord(this.curStaffrewardinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			
			curStaffrewardinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	@Action(value = "checkbill",  results = { 
			 @Result(name = "post_success", type = "json"),
           @Result(name = "post_failure", type = "json")	
        }) 
	public String checkbill()
	{
		try
		{
			this.strMessage="";
			if(this.pc004Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "PC004", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.curStaffrewardinfo.setCheckflag(1);
			this.curStaffrewardinfo.setBillflag(1);
			this.curStaffrewardinfo.setId(new StaffrewardinfoId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.pc004Service.postCheckbill(this.curStaffrewardinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curStaffrewardinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	@Action(value = "comfirmkbill",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String comfirmkbill()
	{
		try
		{
			this.strMessage="";
			if(this.pc004Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "PC004", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.curStaffrewardinfo.setCheckinheadflag(1);
			this.curStaffrewardinfo.setBillflag(2);
			this.curStaffrewardinfo.setId(new StaffrewardinfoId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.pc004Service.postComfirmkbill(this.curStaffrewardinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			curStaffrewardinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "loadStaffrewardinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadStaffrewardinfo()
	{
		try
		{
			this.lsStaffrewardinfo=this.pc004Service.loadStaffrewardinfos();
			if(lsStaffrewardinfo==null || lsStaffrewardinfo.size()==0)
			{
				lsStaffrewardinfo=new ArrayList();
				lsStaffrewardinfo.add(this.pc004Service.addMastRecord());
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		} 
	}
	
	@Action(value = "searchInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String searchInfo()
	{
		try
		{
			this.lsStaffrewardinfo=this.pc004Service.loadStaffrewardinfos(this.strSearchBillno,CommonTool.setDateMask(this.strSearchDate));
			if(lsStaffrewardinfo==null || lsStaffrewardinfo.size()==0)
			{
				lsStaffrewardinfo=new ArrayList();
				lsStaffrewardinfo.add(this.pc004Service.addMastRecord());
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		} 
	}
	
	
	@Action(value = "loadCurStaffrewardinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadCurStaffrewardinfo()
	{
		try
		{
			this.curStaffrewardinfo=this.pc004Service.loadStaffrewardinfoById(this.strCurCompId, this.strCurBillId);
			if(curStaffrewardinfo==null)
			{
				curStaffrewardinfo=this.pc004Service.addMastRecord();
			}
			if(!CommonTool.FormatString(curStaffrewardinfo.getHandcompid()).equals(""))
				curStaffrewardinfo.setHandstaffname(this.pc004Service.getDataTool().loadEmpNameById(curStaffrewardinfo.getHandcompid(), curStaffrewardinfo.getHandstaffid(), new StringBuffer()));
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
			this.curStaffrewardinfo=this.pc004Service.addMastRecord();
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
			this.strCurEmpName=this.pc004Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
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
	
	@JSON(serialize=false)
	public CopyOfPC004Service getPc004Service() {
		return pc004Service;
	}
	@JSON(serialize=false)
	public void setPc004Service(CopyOfPC004Service pc004Service) {
		this.pc004Service = pc004Service;
	}
	public List<Staffrewardinfo> getLsStaffrewardinfo() {
		return lsStaffrewardinfo;
	}
	public void setLsStaffrewardinfo(List<Staffrewardinfo> lsStaffrewardinfo) {
		this.lsStaffrewardinfo = lsStaffrewardinfo;
	}
	public Staffrewardinfo getCurStaffrewardinfo() {
		return curStaffrewardinfo;
	}
	public void setCurStaffrewardinfo(Staffrewardinfo curStaffrewardinfo) {
		this.curStaffrewardinfo = curStaffrewardinfo;
	}
	public String getStrSearchBillno() {
		return strSearchBillno;
	}
	public void setStrSearchBillno(String strSearchBillno) {
		this.strSearchBillno = strSearchBillno;
	}
	public String getStrSearchDate() {
		return strSearchDate;
	}
	public void setStrSearchDate(String strSearchDate) {
		this.strSearchDate = strSearchDate;
	}


}
