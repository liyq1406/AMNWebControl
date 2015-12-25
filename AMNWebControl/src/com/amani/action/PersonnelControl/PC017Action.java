package com.amani.action.PersonnelControl;

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

import com.amani.model.DstaffrewardinfoId;
import com.amani.model.Dstaffsubsidyinfo;
import com.amani.model.DstaffsubsidyinfoId;
import com.amani.model.Dstafftargetinfo;
import com.amani.model.DstafftargetinfoId;
import com.amani.model.Mstaffrewardinfo;
import com.amani.model.MstaffrewardinfoId;
import com.amani.model.Dstaffrewardinfo;
import com.amani.model.Mstaffsubsidyinfo;
import com.amani.model.MstaffsubsidyinfoId;
import com.amani.model.Mstafftargetinfo;
import com.amani.model.MstafftargetinfoId;
import com.amani.model.Staffinfo;
import com.amani.model.StaffrewardinfoId;
import com.amani.model.Useroverall;
import com.amani.service.PersonnelControl.PC017Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc017")
public class PC017Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PC017Service pc017Service;
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
	private String strSubsidyJsonParam;
	private String strTargetJsonParam;
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
	private Mstaffsubsidyinfo curMstaffsubsidyinfo;
	private List<Dstaffsubsidyinfo> lsDstaffsubsidyinfo;
	
	private Mstafftargetinfo curMstafftargetinfo;
	private List<Dstafftargetinfo> lsDstafftargetinfo;
	
	private String strCurManagerNo;
	private String strCurManagerPass;
	
	private String strTJFromDate;
	private String strTJToDate;
	private List<Staffinfo> lsStaffinfo;
	private List<Staffinfo> lsOldStaffInfo;
	private List<Staffinfo> lsStaffinfoDutyAmt;
	public List<Staffinfo> getLsOldStaffInfo() {
		return lsOldStaffInfo;
	}
	public void setLsOldStaffInfo(List<Staffinfo> lsOldStaffInfo) {
		this.lsOldStaffInfo = lsOldStaffInfo;
	}
	public List<Staffinfo> getLsStaffinfo() {
		return lsStaffinfo;
	}
	public void setLsStaffinfo(List<Staffinfo> lsStaffinfo) {
		this.lsStaffinfo = lsStaffinfo;
	}
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
		curStaffrewardinfo.getId().setEntrybillid(this.pc017Service.getDataTool().loadBillIdByRule(curStaffrewardinfo.getId().getEntrycompid(),"mstaffrewardinfo", "entrybillid", "SP011"));
		if(!CommonTool.FormatString(strJsonParam).equals("") && strJsonParam.indexOf("[")>-1)
		{
			this.lsDmstaffrewardinfo=this.pc017Service.getDataTool().loadDTOList(this.strJsonParam, Dstaffrewardinfo.class);
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
			if(this.pc017Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "PC017", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			Mstaffrewardinfo obj=new Mstaffrewardinfo();
			obj.setId(new MstaffrewardinfoId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.pc017Service.delete(obj);
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
			if(this.pc017Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "PC017", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.pc017Service.postRecord(this.curStaffrewardinfo,this.lsDmstaffrewardinfo);
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
	
	@Action(value = "postSubsidyInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
           @Result(name = "post_failure", type = "json")	
        }) 
	public String postSubsidyInfo()
	{
		try
		{
			this.strMessage="";
			List<Mstaffsubsidyinfo> lsMstaffsubsidyinfo=null;
			List<Dstaffsubsidyinfo> lsDstaffsubsidyinfox=new ArrayList();
			String[] lsSubsidy=null;
			String[] lsSubsidyAmt=null;
			Dstaffsubsidyinfo record=null;
			if(!CommonTool.FormatString(this.strSubsidyJsonParam).equals("") && strSubsidyJsonParam.indexOf("[")>-1)
			{
				lsMstaffsubsidyinfo=this.pc017Service.getDataTool().loadDTOList(this.strSubsidyJsonParam, Mstaffsubsidyinfo.class);
				if(lsMstaffsubsidyinfo!=null && lsMstaffsubsidyinfo.size()>0)
				{
					for(int i=0;i<lsMstaffsubsidyinfo.size();i++)
					{
						lsMstaffsubsidyinfo.get(i).setId(new MstaffsubsidyinfoId(CommonTool.getLoginInfo("COMPID"),lsMstaffsubsidyinfo.get(i).getBentrybillid()));
						lsMstaffsubsidyinfo.get(i).setStartdate(CommonTool.FormatString(lsMstaffsubsidyinfo.get(i).getStartdate()).replace("-", ""));
						lsMstaffsubsidyinfo.get(i).setEnddate(CommonTool.FormatString(lsMstaffsubsidyinfo.get(i).getEnddate()).replace("-", ""));
						lsMstaffsubsidyinfo.get(i).setBillflag(0);
						lsMstaffsubsidyinfo.get(i).setOperationer(CommonTool.getLoginInfo("USERID"));
						lsMstaffsubsidyinfo.get(i).setOperationdate(CommonTool.getCurrDate());
						if(this.pc017Service.validateStaffSubsidy(lsMstaffsubsidyinfo.get(i).getHandcompid(),lsMstaffsubsidyinfo.get(i).getHandstaffinid(),lsMstaffsubsidyinfo.get(i).getStartdate(),lsMstaffsubsidyinfo.get(i).getEnddate())==true)
						{
							this.strMessage=lsMstaffsubsidyinfo.get(i).getHandstaffname()+"本次保底范围内已在系统中存在!";
							return SystemFinal.POST_SUCCESS;
						}
						if(!CommonTool.FormatString(lsMstaffsubsidyinfo.get(i).getSubsidycondition()).equals("") )
						{
							lsSubsidy=lsMstaffsubsidyinfo.get(i).getSubsidycondition().split(";");
							if(lsSubsidy!=null && lsSubsidy.length>0)
							{
								for(int j=0;j<lsSubsidy.length;j++)
								{
									record=new Dstaffsubsidyinfo();
									record.setId(new DstaffsubsidyinfoId(CommonTool.getLoginInfo("COMPID"),lsMstaffsubsidyinfo.get(i).getBentrybillid(),j));
									record.setHandstaffinid(lsMstaffsubsidyinfo.get(i).getHandstaffinid());
									String subinfo=lsSubsidy[j].toString();
									lsSubsidyAmt=subinfo.split(",");
									record.setSubsidytype(Integer.parseInt(lsSubsidyAmt[0].toString()));
									record.setSubsidyamt(CommonTool.FormatBigDecimalT(new BigDecimal(Double.parseDouble(lsSubsidyAmt[1].toString()))));
									lsDstaffsubsidyinfox.add(record);
								}
							}
						}
					}
				}
			}
			this.pc017Service.postSubsidy(lsMstaffsubsidyinfo,lsDstaffsubsidyinfox);
			lsMstaffsubsidyinfo=null;
			lsDstaffsubsidyinfox=null;
			lsSubsidy=null;
			lsSubsidyAmt=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	
	@Action(value = "postTargetInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String postTargetInfo()
	{
		try
		{
			this.strMessage="";
			List<Mstafftargetinfo> lsMstafftargetinfo=null;
			List<Dstafftargetinfo> lsDstafftargetinfox=new ArrayList();
			String[] lsSubsidy=null;
			String[] lsSubsidyAmt=null;
			Dstafftargetinfo record=null;
			if(!CommonTool.FormatString(this.strTargetJsonParam).equals("") && strTargetJsonParam.indexOf("[")>-1)
			{
				lsMstafftargetinfo=this.pc017Service.getDataTool().loadDTOList(this.strTargetJsonParam, Mstafftargetinfo.class);
				if(lsMstafftargetinfo!=null && lsMstafftargetinfo.size()>0)
				{
					for(int i=0;i<lsMstafftargetinfo.size();i++)
					{
						lsMstafftargetinfo.get(i).setId(new MstafftargetinfoId(CommonTool.getLoginInfo("COMPID"),lsMstafftargetinfo.get(i).getBentrybillid()));
						lsMstafftargetinfo.get(i).setStartdate(CommonTool.FormatString(lsMstafftargetinfo.get(i).getStartdate()).replace("-", ""));
						lsMstafftargetinfo.get(i).setEnddate(CommonTool.FormatString(lsMstafftargetinfo.get(i).getEnddate()).replace("-", ""));
						lsMstafftargetinfo.get(i).setBillflag(0);
						lsMstafftargetinfo.get(i).setOperationer(CommonTool.getLoginInfo("USERID"));
						lsMstafftargetinfo.get(i).setOperationdate(CommonTool.getCurrDate());
						
						
						if(!CommonTool.FormatString(lsMstafftargetinfo.get(i).getSubsidycondition()).equals("") )
						{
							lsSubsidy=lsMstafftargetinfo.get(i).getSubsidycondition().split(";");
							if(lsSubsidy!=null && lsSubsidy.length>0)
							{
								for(int j=0;j<lsSubsidy.length;j++)
								{
									record=new Dstafftargetinfo();
									record.setId(new DstafftargetinfoId(CommonTool.getLoginInfo("COMPID"),lsMstafftargetinfo.get(i).getBentrybillid(),j));
									record.setHandstaffinid(lsMstafftargetinfo.get(i).getHandstaffinid());
									String subinfo=lsSubsidy[j].toString();
									lsSubsidyAmt=subinfo.split(",");
									record.setTargettype(Integer.parseInt(lsSubsidyAmt[0].toString()));
									record.setTargetamt(CommonTool.FormatBigDecimalT(new BigDecimal(Double.parseDouble(lsSubsidyAmt[1].toString()))));
									//record.setStartdate(CommonTool.setDateMask(lsSubsidyAmt[2].toString()));
									//record.setEnddate(CommonTool.setDateMask(lsSubsidyAmt[3].toString()));
									lsDstafftargetinfox.add(record);
								}
							}
						}
					}
				}
			}
			this.pc017Service.postTarget(lsMstafftargetinfo,lsDstafftargetinfox);
			lsMstafftargetinfo=null;
			lsDstafftargetinfox=null;
			lsSubsidy=null;
			lsSubsidyAmt=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	@Action(value = "postStaffDutyAmt",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
      }) 
	public String postStaffDutyAmt()
	{
		try
		{
			this.strMessage="";
			if(!CommonTool.FormatString(this.strJsonParam).equals("") && strJsonParam.indexOf("[")>-1)
			{
				this.lsStaffinfoDutyAmt=this.pc017Service.getDataTool().loadDTOList(this.strJsonParam, Staffinfo.class);
				if(lsStaffinfoDutyAmt!=null && lsStaffinfoDutyAmt.size()>0)
				{
					boolean flag=this.pc017Service.postStaffRewardInfo(strCurCompId, this.strSearchDate, lsStaffinfoDutyAmt);
					if(flag==false)
					{
						this.strMessage="操作失败,请核实后重新操作!";
					}
				}
			}
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
			this.lsStaffrewardinfo=this.pc017Service. loadStaffrewardinfos();
			if(lsStaffrewardinfo==null || lsStaffrewardinfo.size()==0)
			{
				this.lsStaffrewardinfo=new ArrayList();
				lsStaffrewardinfo.add(this.pc017Service.addMastRecord());
			}
			curMstaffsubsidyinfo=this.pc017Service.addSubsidyMastRecord();
			curMstafftargetinfo=this.pc017Service.addTargetMastRecord();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		} 
	}
	@Action(value = "loadOldStaffInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadOldStaffInfo()
	{
		try
		{
			this.lsOldStaffInfo=this.pc017Service.loadOldStaffInfo(this.strCurCompId);
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
			this.lsStaffrewardinfo=this.pc017Service.loadStaffrewardinfos(this.strSearchBillno);
			if(lsStaffrewardinfo==null || lsStaffrewardinfo.size()==0)
			{
				lsStaffrewardinfo=new ArrayList();
				lsStaffrewardinfo.add(this.pc017Service.addMastRecord());
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
			this.lsDmstaffrewardinfo=this.pc017Service.loadDStaffrewardinfos(this.strCurCompId, CommonTool.setDateMask(this.strTJFromDate), CommonTool.setDateMask(this.strTJToDate));
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
			boolean flag=this.pc017Service.postCheckbill(this.strCurCompId,this.strCurBillId);
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
	
	@Action(value = "checkmanagerPass",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String checkmanagerPass()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.pc017Service.validateManagerPass(this.strCurManagerNo, this.strCurManagerPass);
			if(flag==false)
				this.strMessage="经理密码有误,请确认";
			else
				this.strMessage="";
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
			this.curStaffrewardinfo=this.pc017Service.loadStaffrewardinfoById(this.strCurCompId, this.strCurBillId);
			if(curStaffrewardinfo==null)
			{
				curStaffrewardinfo=this.pc017Service.addMastRecord();
				lsOldStaffInfo=this.pc017Service.loadOldStaffInfo(CommonTool.getLoginInfo("COMPID"));
			}
			else
			{
				curStaffrewardinfo.setHandcompname(this.pc017Service.getDataTool().loadCompNameById(CommonTool.FormatString(this.curStaffrewardinfo.getHandcompid())));
			}
			this.lsDmstaffrewardinfo=this.pc017Service.loadDstaffrewardinfoById(this.strCurCompId, this.strCurBillId);
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
			this.curStaffrewardinfo=this.pc017Service.addMastRecord();
			lsOldStaffInfo=this.pc017Service.loadOldStaffInfo(CommonTool.getLoginInfo("COMPID"));
			return SystemFinal.ADD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.ADD_FAILURE;
		}
	}
	
	
	@Action(value = "addSubsidy",  results = { 
			 @Result(name = "add_success", type = "json"),
          @Result(name = "add_failure", type = "json")	
     }) 
	public String addSubsidy()
	{
		try
		{
			this.curMstaffsubsidyinfo=this.pc017Service.addSubsidyMastRecord();
			return SystemFinal.ADD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.ADD_FAILURE;
		}
	}
	
	
	@Action(value = "addTarget",  results = { 
			 @Result(name = "add_success", type = "json"),
         @Result(name = "add_failure", type = "json")	
    }) 
	public String addTarget()
	{
		try
		{
			this.curMstafftargetinfo=this.pc017Service.addTargetMastRecord();
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
			boolean flag=this.pc017Service.getDataTool().loadEmpInidNameById(this.strCurCompId, this.strCurEmpId, validateMsg,staffnamebuf,staffmanagerno);
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
	
	@Action(value = "validateShandcompid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateShandcompid()
	{
		try
		{
			this.strMessage="";
			this.strCurCompName=this.pc017Service.getDataTool().loadCompNameById(this.strCurCompId);
			if(CommonTool.FormatString(this.strCurCompName).equals(""))
			{
				this.strMessage="门店编号不存在!";
				return SystemFinal.LOAD_SUCCESS;
			}
			this.lsStaffinfo=this.pc017Service.getDataTool().loadEmpsByCompId(strCurCompId,1);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
    

	@Action(value = "loadStaffDutyAmt",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadStaffDutyAmt()
	{
		try
		{
			lsStaffinfoDutyAmt=this.pc017Service.loadStaffInfoByZerenjin(strCurCompId);
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
	public PC017Service getPc017Service() {
		return pc017Service;
	}
	@JSON(serialize=false)
	public void setPc017Service(PC017Service pc017Service) {
		this.pc017Service = pc017Service;
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
	public Mstaffsubsidyinfo getCurMstaffsubsidyinfo() {
		return curMstaffsubsidyinfo;
	}
	public void setCurMstaffsubsidyinfo(Mstaffsubsidyinfo curMstaffsubsidyinfo) {
		this.curMstaffsubsidyinfo = curMstaffsubsidyinfo;
	}
	public List<Dstaffsubsidyinfo> getLsDstaffsubsidyinfo() {
		return lsDstaffsubsidyinfo;
	}
	public void setLsDstaffsubsidyinfo(List<Dstaffsubsidyinfo> lsDstaffsubsidyinfo) {
		this.lsDstaffsubsidyinfo = lsDstaffsubsidyinfo;
	}
	public String getStrSubsidyJsonParam() {
		return strSubsidyJsonParam;
	}
	public void setStrSubsidyJsonParam(String strSubsidyJsonParam) {
		this.strSubsidyJsonParam = strSubsidyJsonParam;
	}
	public Mstafftargetinfo getCurMstafftargetinfo() {
		return curMstafftargetinfo;
	}
	public void setCurMstafftargetinfo(Mstafftargetinfo curMstafftargetinfo) {
		this.curMstafftargetinfo = curMstafftargetinfo;
	}
	public List<Dstafftargetinfo> getLsDstafftargetinfo() {
		return lsDstafftargetinfo;
	}
	public void setLsDstafftargetinfo(List<Dstafftargetinfo> lsDstafftargetinfo) {
		this.lsDstafftargetinfo = lsDstafftargetinfo;
	}
	public String getStrTargetJsonParam() {
		return strTargetJsonParam;
	}
	public void setStrTargetJsonParam(String strTargetJsonParam) {
		this.strTargetJsonParam = strTargetJsonParam;
	}
	public List<Staffinfo> getLsStaffinfoDutyAmt() {
		return lsStaffinfoDutyAmt;
	}
	public void setLsStaffinfoDutyAmt(List<Staffinfo> lsStaffinfoDutyAmt) {
		this.lsStaffinfoDutyAmt = lsStaffinfoDutyAmt;
	}


}
