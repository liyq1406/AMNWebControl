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

import com.amani.model.DstaffrewardinfoId;
import com.amani.model.Mstaffrewardinfo;
import com.amani.model.MstaffrewardinfoId;
import com.amani.model.Dstaffrewardinfo;
import com.amani.model.Staffinfo;
import com.amani.model.Staffrewardinfo;
import com.amani.model.StaffrewardinfoId;
import com.amani.model.Useroverall;
import com.amani.service.PersonnelControl.PC004Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc004")
public class PC004Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PC004Service pc004Service;
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
	private String strCurEmpInid;
	private String strSearchBillno;
	private String strSearchDate;
	private List<Mstaffrewardinfo> lsStaffrewardinfo;
	private List<Dstaffrewardinfo> lsDmstaffrewardinfo;
	private Mstaffrewardinfo curStaffrewardinfo;
	private String strCurManagerNo;
	private String strCurManagerPass;
	
	private List<Staffinfo> lsOldStaffInfo;
	 /***********经理卡验证*******************/
    private String mangerCardNo;
    public String getMangerCardNo() {
		return mangerCardNo;
	}

	public void setMangerCardNo(String mangerCardNo) {
		this.mangerCardNo = mangerCardNo;
	}
	private String strTJFromDate;
	private String strTJToDate;
	public String getStrCurManagerNo() {
		return strCurManagerNo;
	}
	public void setStrCurManagerNo(String strCurManagerNo) {
		this.strCurManagerNo = strCurManagerNo;
	}
	public String getStrCurManagerPass() {
		return strCurManagerPass;
	}
	public void setStrCurManagerPass(String strCurManagerPass) {
		this.strCurManagerPass = strCurManagerPass;
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
		this.curStaffrewardinfo.setOperationdate(CommonTool.getCurrDate());
		this.curStaffrewardinfo.setOperationer(CommonTool.getLoginInfo("USERID"));
		if(!CommonTool.FormatString(strJsonParam).equals("") && strJsonParam.indexOf("[")>-1)
		{
			this.lsDmstaffrewardinfo=this.pc004Service.getDataTool().loadDTOList(this.strJsonParam, Dstaffrewardinfo.class);
			if(lsDmstaffrewardinfo!=null && lsDmstaffrewardinfo.size()>0)
			{
				for(int i=0;i<lsDmstaffrewardinfo.size();i++)
				{
					lsDmstaffrewardinfo.get(i).setId(new DstaffrewardinfoId(this.curStaffrewardinfo.getId().getEntrycompid(),this.curStaffrewardinfo.getId().getEntrybillid(),i));
					lsDmstaffrewardinfo.get(i).setEntrydate(CommonTool.setDateMask(lsDmstaffrewardinfo.get(i).getEntrydate()));
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
			if(this.pc004Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "PC004", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			Mstaffrewardinfo obj=new Mstaffrewardinfo();
			obj.setId(new MstaffrewardinfoId(this.strCurCompId,this.strCurBillId));
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
			boolean bRet=true;
			String strTempDate="";
			String strCurDate=CommonTool.getCurrDate();
			if(lsDmstaffrewardinfo!=null && lsDmstaffrewardinfo.size()>0)
			{
				for(Dstaffrewardinfo dstaffrewardinfo:lsDmstaffrewardinfo)
				{
					//获取处罚日期
					strTempDate=dstaffrewardinfo.getEntrydate();
					if(Integer.parseInt(strTempDate)>Integer.parseInt(strCurDate))
					{
						bRet=false;
						break;
					}
					//获取日期输入范围
					if(Integer.parseInt(strCurDate.substring(6,8))>10)
					{
						if(Integer.parseInt(CommonTool.setDateMask(strTempDate))<Integer.parseInt(strCurDate.substring(0,6)+"01"))
						{
							bRet=false;
							break;
						}
					}
					else
					{
						if(Integer.parseInt(CommonTool.setDateMask(strTempDate))<Integer.parseInt(CommonTool.addMonth(Integer.parseInt(strCurDate.substring(0,6))+"01",-1)))
						{
							bRet=false;
							break;
						}
					}
					
				}
			}
			if(!bRet)
			{
				this.strMessage="日期有问题，如果今天大于10号就只能做本月奖罚。小于10号可以做上个月的奖罚。";
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.pc004Service.postRecord(this.curStaffrewardinfo,this.lsDmstaffrewardinfo);
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
			this.lsStaffrewardinfo=this.pc004Service. loadStaffrewardinfos();
			if(lsStaffrewardinfo==null || lsStaffrewardinfo.size()==0)
			{
				this.lsStaffrewardinfo=new ArrayList();
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
			this.lsStaffrewardinfo=this.pc004Service.loadStaffrewardinfos(this.strSearchBillno);
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
	
	
	@Action(value = "searchTjInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String searchTjInfo()
	{
		try
		{
			this.lsDmstaffrewardinfo=this.pc004Service.loadDStaffrewardinfos(this.strCurCompId, CommonTool.setDateMask(this.strTJFromDate), CommonTool.setDateMask(this.strTJToDate));
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
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
			boolean flag=this.pc004Service.postCheckbill(this.strCurCompId,this.strCurBillId);
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
	
	//经理签单审核
	@Action(value = "checkmanagerPass",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String checkmanagerPass()
	{
		try
		{
//			this.strMessage="";
//			boolean flag=this.pc004Service.validateManagerPass(this.strCurManagerNo, this.strCurManagerPass);
//			if(flag==false)
//				this.strMessage="经理密码有误,请确认";
//			else
//				this.strMessage="";
//			return SystemFinal.LOAD_SUCCESS;
			
			this.strMessage="";
			boolean flag=this.pc004Service.getDataTool().validateMangerPass(CommonTool.getLoginInfo("COMPID"), this.mangerCardNo);
			if(flag==false)
			{
				this.strMessage="该经理密码不对,请核实";
				return SystemFinal.LOAD_SUCCESS;
			}
			flag=this.pc004Service.postCheckbill(this.strCurCompId,this.strCurBillId);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
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
				lsOldStaffInfo=this.pc004Service.loadOldStaffInfo(CommonTool.getLoginInfo("COMPID"));
			}
			else
			{
				curStaffrewardinfo.setHandcompname(this.pc004Service.getDataTool().loadCompNameById(CommonTool.FormatString(this.curStaffrewardinfo.getHandcompid())));
			}
			this.lsDmstaffrewardinfo=this.pc004Service.loadDstaffrewardinfoById(this.strCurCompId, this.strCurBillId);
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
			lsOldStaffInfo=this.pc004Service.loadOldStaffInfo(CommonTool.getLoginInfo("COMPID"));
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
			this.strMessage="";
			StringBuffer validateMsg=new StringBuffer();
			StringBuffer staffnamebuf=new StringBuffer();
			StringBuffer staffmanagerno=new StringBuffer();
			boolean flag=this.pc004Service.getDataTool().loadEmpInidNameById(this.strCurCompId, this.strCurEmpId, validateMsg,staffnamebuf,staffmanagerno);
			if(flag==false)
				this.strMessage=validateMsg.toString();
			else
			{
				this.strCurEmpName=staffnamebuf.toString();
				strCurEmpInid=staffmanagerno.toString();
			}
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
	public PC004Service getPc004Service() {
		return pc004Service;
	}
	@JSON(serialize=false)
	public void setPc004Service(PC004Service pc004Service) {
		this.pc004Service = pc004Service;
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
	public String getStrCurEmpInid() {
		return strCurEmpInid;
	}
	public void setStrCurEmpInid(String strCurEmpInid) {
		this.strCurEmpInid = strCurEmpInid;
	}
	public List<Dstaffrewardinfo> getLsDmstaffrewardinfo() {
		return lsDmstaffrewardinfo;
	}
	public void setLsDmstaffrewardinfo(List<Dstaffrewardinfo> lsDmstaffrewardinfo) {
		this.lsDmstaffrewardinfo = lsDmstaffrewardinfo;
	}
	public void setLsStaffrewardinfo(List<Mstaffrewardinfo> lsStaffrewardinfo) {
		this.lsStaffrewardinfo = lsStaffrewardinfo;
	}
	public void setCurStaffrewardinfo(Mstaffrewardinfo curStaffrewardinfo) {
		this.curStaffrewardinfo = curStaffrewardinfo;
	}
	public List<Mstaffrewardinfo> getLsStaffrewardinfo() {
		return lsStaffrewardinfo;
	}
	public Mstaffrewardinfo getCurStaffrewardinfo() {
		return curStaffrewardinfo;
	}
	public String getStrTJFromDate() {
		return strTJFromDate;
	}
	public void setStrTJFromDate(String strTJFromDate) {
		this.strTJFromDate = strTJFromDate;
	}
	public String getStrTJToDate() {
		return strTJToDate;
	}
	public void setStrTJToDate(String strTJToDate) {
		this.strTJToDate = strTJToDate;
	}
	public List<Staffinfo> getLsOldStaffInfo() {
		return lsOldStaffInfo;
	}
	public void setLsOldStaffInfo(List<Staffinfo> lsOldStaffInfo) {
		this.lsOldStaffInfo = lsOldStaffInfo;
	}


}
