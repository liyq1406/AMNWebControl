package com.amani.action.BaseInfoControl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.bean.FaceId_EmployeeBean;
import com.amani.model.ManagerShare;
import com.amani.model.Mstaffsubsidyinfo;
import com.amani.model.Staffabsenceinfo;
import com.amani.model.Staffchangeinfo;
import com.amani.model.StaffchangeinfoId;
import com.amani.model.Staffhistory;
import com.amani.model.Staffinfo;
import com.amani.model.Staffinfomanger;
import com.amani.service.ICommonService;
import com.amani.service.BaseInfoControl.BC012Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc012")
public class BC012Action extends AMN_ModuleAction{
	
	@Autowired
	private BC012Service bc012Service;
	private String strCurCompId;
	private String strCurStaffId;
	private String strToCurCompId;
	private String strFingerId;
	private String strCurStaffName;
	private int strCurFingerId;
	private List<Staffinfo> lsStaffinfo;
	private Staffinfo curStaffinfo;
	private List<Staffhistory> lsStaffhistory;
	private Staffchangeinfo curStaffchangeinfo;
	private List<Staffabsenceinfo> lsStaffabsenceinfo;
	private List<ManagerShare> lsManagerShare;
	private String strStaffNo;
	private String strStaffInNo;
	private String strStaffName;
	private String strPCID;
	private String strAbsencedate;
	private BigDecimal sharesalary;
	private String strtoMonth;
	private String	strHairState;
	//派遣信息
	private String 		oldcompno;
	private String 		oldstaffno;
	private String 		oldstaffinid;
	private String 		olddepid;
	private String 		oldpostion;
	private int 		oldyjtype;
	private double 		oldyjrate;
	private double	 	oldyjamt;
	private double		oldSalary;
	private String 		newcompno;
	private String 		newstaffno;
	private int 		newyjtype;
	private double 		newyjrate;
	private double	 	newyjamt;
	private double		newSalary;
	private String 		oldstaffname;
	private String 		dispatchcompno;
	private String 		dispatchdate;
	private String 		tdispatchdate;
	private String 		sysParamSp103;//是否现在调动生效日期时间
	
	private String		strEntryCompId;
	private String 		strEntryBillId;
	
	private String		strChildrenStaffNo;
	private List<Mstaffsubsidyinfo> lsBdDataSet;
	private List<Staffinfomanger> lsStaffinfomanger;
	public List<Mstaffsubsidyinfo> getLsBdDataSet() {
		return lsBdDataSet;
	}

	public void setLsBdDataSet(List<Mstaffsubsidyinfo> lsBdDataSet) {
		this.lsBdDataSet = lsBdDataSet;
	}

	public String getStrEntryCompId() {
		return strEntryCompId;
	}

	public void setStrEntryCompId(String strEntryCompId) {
		this.strEntryCompId = strEntryCompId;
	}

	public String getStrEntryBillId() {
		return strEntryBillId;
	}

	public void setStrEntryBillId(String strEntryBillId) {
		this.strEntryBillId = strEntryBillId;
	}

	public String getSysParamSp103() {
		return sysParamSp103;
	}

	public void setSysParamSp103(String sysParamSp103) {
		this.sysParamSp103 = sysParamSp103;
	}

	public List<Staffinfo> getLsStaffinfo() {
		return lsStaffinfo;
	}

	public void setLsStaffinfo(List<Staffinfo> lsStaffinfo) {
		this.lsStaffinfo = lsStaffinfo;
	}

	public Staffinfo getCurStaffinfo() {
		return curStaffinfo;
	}

	public void setCurStaffinfo(Staffinfo curStaffinfo) {
		this.curStaffinfo = curStaffinfo;
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
			
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	@Action(value = "postStaffRZ",  results = { 
			 @Result(name = "post_success", type = "json"),
           @Result(name = "post_failure", type = "json")	
        }) 
	public String postStaffRZ()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC012", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean checkflag=this.bc012Service.checkStaffno(this.curStaffinfo.getId().getCompno(), this.curStaffinfo.getId().getStaffno());
			if(checkflag==false)
			{
				strMessage="该员工编号已经在员工资料中存在,请更改入职工号!";
				return SystemFinal.POST_SUCCESS;
			}
			checkflag=this.bc012Service.checkStaffchangeinfo(this.curStaffinfo.getId().getCompno(), this.curStaffinfo.getId().getStaffno());
			if(checkflag==false)
			{
				strMessage="该员工编号已经在未生效的人事单中存在,请更改入职工号!";
				return SystemFinal.POST_SUCCESS;
			}
			
			checkflag=this.bc012Service.checkStaffPcid(this.curStaffinfo.getPccid());
			if(checkflag==false)
			{
				  this.setStrMessage("该身份证已经在系统中使用，不能再次使用，请核对后重新保存！");
				  return SystemFinal.POST_FAILURE;
			}
			this.curStaffinfo.setManageno(this.bc012Service.getDataTool().autoCreateInlineStaffNo());
			this.curStaffinfo.setFingerno(this.bc012Service.getDataTool().autoCreateFingerStaffNo());
			this.curStaffinfo.setFillno(this.bc012Service.getDataTool().autoCreateFileNoStaffNo());
			this.curStaffinfo.setCurstate("1");
			this.curStaffinfo.setArrivaldate(CommonTool.setDateMask(curStaffinfo.getArrivaldate()));
			this.curStaffinfo.setBirthdate(CommonTool.setDateMask(curStaffinfo.getBirthdate()));
			this.curStaffinfo.setContractdate(CommonTool.setDateMask(curStaffinfo.getContractdate()));
			this.curStaffinfo.setLeavedate(CommonTool.setDateMask(this.curStaffinfo.getLeavedate()));
			this.curStaffinfo.setHealthdate(CommonTool.setDateMask(this.curStaffinfo.getHealthdate()));
			this.curStaffinfo.setContractdate(CommonTool.setDateMask(curStaffinfo.getContractdate()));
			StringBuffer msgBuffer=new StringBuffer();
			checkflag=this.bc012Service.handStaffNoRz(curStaffinfo, msgBuffer);
			if(checkflag==false)
			{
				this.strMessage=msgBuffer.toString();
				return SystemFinal.POST_FAILURE;
			}
//			checkflag=this.bc012Service.postRolePositionAccount(this.curStaffinfo.getId().getCompno(), this.curStaffinfo.getId().getStaffno(), this.curStaffinfo.getManageno(), this.curStaffinfo.getPosition());
//			if(checkflag==false)
//			{
//				this.strMessage="创建系统账户失败,请确认!";
//				return SystemFinal.POST_FAILURE;
//			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}

	@Action(value = "postStaffLZ",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String postStaffLZ()
	{
		try
		{
			this.strMessage="";
			boolean checkflag=this.bc012Service.checkStaffchangeinfoByInid(this.curStaffchangeinfo.getStaffmangerno());
			if(checkflag==false)
			{
				strMessage="该员工编号已经在未生效的人事单中存在,请查实!";
				return SystemFinal.POST_SUCCESS;
			}
			if(Integer.parseInt(CommonTool.setDateMask(curStaffchangeinfo.getValidatestartdate()))<=Integer.parseInt(CommonTool.setDateMask(CommonTool.getCurrDate())))
			{
				strMessage="生效日期必须是今天以后";
				return SystemFinal.POST_SUCCESS;
			}
			curStaffchangeinfo.setChangedate(CommonTool.getCurrDate());
			curStaffchangeinfo.setValidateenddate(CommonTool.setDateMask(curStaffchangeinfo.getValidatestartdate()));
			curStaffchangeinfo.setValidatestartdate(CommonTool.setDateMask(curStaffchangeinfo.getValidatestartdate()));
			curStaffchangeinfo.setId(new StaffchangeinfoId(CommonTool.getLoginInfo("COMPID"),this.bc012Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"staffchangeinfo", "changebillid", "SP011"),1));
			StringBuffer msgBuffer=new StringBuffer();
			checkflag=this.bc012Service.handStaffNoLz(curStaffchangeinfo, msgBuffer);
			if(checkflag==false)
			{
				this.strMessage=msgBuffer.toString();
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
	
	@Action(value = "postStaffDDA",  results = { 
			 @Result(name = "post_success", type = "json"),
         @Result(name = "post_failure", type = "json")	
      }) 
	public String postStaffDDA()
	{
		try
		{
			this.strMessage="";
			boolean checkflag=this.bc012Service.checkStaffchangeinfoByInid(this.curStaffchangeinfo.getStaffmangerno());
			if(checkflag==false)
			{
				strMessage="该员工编号已经在未生效的人事单中存在,请查实!";
				return SystemFinal.POST_SUCCESS;
			}
			if(!CommonTool.FormatString(curStaffchangeinfo.getChangestaffno()).equals(CommonTool.FormatString(curStaffchangeinfo.getAfterstaffno())))
			{
				checkflag=this.bc012Service.checkStaffno(this.curStaffchangeinfo.getAftercompid(), this.curStaffchangeinfo.getAfterstaffno());
				if(checkflag==false)
				{
					strMessage="该新员工编号已经在员工资料中存在,请更改入职工号!";
					return SystemFinal.POST_SUCCESS;
				}
				checkflag=this.bc012Service.checkStaffchangeinfo(this.curStaffchangeinfo.getAftercompid(), this.curStaffchangeinfo.getAfterstaffno());
				if(checkflag==false)
				{
					strMessage="该新员工编号已经在未生效人事单中存在,请查实!";
					return SystemFinal.POST_SUCCESS;
				}
			}
			curStaffchangeinfo.setChangedate(CommonTool.getCurrDate());
			curStaffchangeinfo.setValidateenddate(CommonTool.setDateMask(curStaffchangeinfo.getValidatestartdate()));
			curStaffchangeinfo.setValidatestartdate(CommonTool.setDateMask(curStaffchangeinfo.getValidatestartdate()));
			curStaffchangeinfo.setId(new StaffchangeinfoId(CommonTool.getLoginInfo("COMPID"),this.bc012Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"staffchangeinfo", "changebillid", "SP011"),5));
			StringBuffer msgBuffer=new StringBuffer();
			checkflag=this.bc012Service.handStaffNoDD(curStaffchangeinfo, msgBuffer);
			if(checkflag==false)
			{
				this.strMessage=msgBuffer.toString();
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
	@Action(value = "postStaffDDB",  results = { 
			 @Result(name = "post_success", type = "json"),
        @Result(name = "post_failure", type = "json")	
     }) 
	public String postStaffDDB()
	{
		try
		{
			this.strMessage="";
			boolean checkflag=this.bc012Service.checkStaffchangeinfoByInid(this.curStaffchangeinfo.getStaffmangerno());
			if(checkflag==false)
			{
				strMessage="该员工编号已经在未生效的人事单中存在,请查实!";
				return SystemFinal.POST_SUCCESS;
			}
			if(!CommonTool.FormatString(curStaffchangeinfo.getChangestaffno()).equals(CommonTool.FormatString(curStaffchangeinfo.getAfterstaffno())))
			{
				checkflag=this.bc012Service.checkStaffno(this.curStaffchangeinfo.getAftercompid(), this.curStaffchangeinfo.getAfterstaffno());
				if(checkflag==false)
				{
					strMessage="该新员工编号已经在员工资料中存在,请更改入职工号!";
					return SystemFinal.POST_SUCCESS;
				}
				checkflag=this.bc012Service.checkStaffchangeinfo(this.curStaffchangeinfo.getAftercompid(), this.curStaffchangeinfo.getAfterstaffno());
				if(checkflag==false)
				{
					strMessage="该新员工编号已经在未生效人事单中存在,请查实!";
					return SystemFinal.POST_SUCCESS;
				}
			}
			curStaffchangeinfo.setChangedate(CommonTool.getCurrDate());
			curStaffchangeinfo.setValidateenddate(CommonTool.setDateMask(curStaffchangeinfo.getValidatestartdate()));
			curStaffchangeinfo.setValidatestartdate(CommonTool.setDateMask(curStaffchangeinfo.getValidatestartdate()));
			curStaffchangeinfo.setId(new StaffchangeinfoId(CommonTool.getLoginInfo("COMPID"),this.bc012Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"staffchangeinfo", "changebillid", "SP011"),6));
			StringBuffer msgBuffer=new StringBuffer();
			checkflag=this.bc012Service.handStaffNoDD(curStaffchangeinfo, msgBuffer);
			if(checkflag==false)
			{
				this.strMessage=msgBuffer.toString();
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
	
	
	@Action(value = "changeBySameLevel",  results = { 
			 @Result(name = "post_success", type = "json"),
       @Result(name = "post_failure", type = "json")	
    }) 
	public String changeBySameLevel()
	{
		try
		{
			this.strMessage="";
			boolean checkflag=this.bc012Service.checkStaffchangeinfoByInid(this.oldstaffinid);
			if(checkflag==false)
			{
				strMessage="该员工编号已经在未生效的人事单中存在,请查实!";
				return SystemFinal.POST_SUCCESS;
			}
			checkflag=this.bc012Service.checkStaffno(this.newcompno, this.newstaffno);
			if(checkflag==false)
			{
				strMessage="该新员工编号已经在员工资料中存在,请更改入职工号!";
				return SystemFinal.POST_SUCCESS;
			}
			checkflag=this.bc012Service.checkStaffchangeinfo(this.newcompno, this.newstaffno);
			if(checkflag==false)
			{
				strMessage="该新员工编号已经在未生效人事单中存在,请查实!";
				return SystemFinal.POST_SUCCESS;
			}
			curStaffchangeinfo=new Staffchangeinfo();
			curStaffchangeinfo.setId(new StaffchangeinfoId(CommonTool.getLoginInfo("COMPID"),this.bc012Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"staffchangeinfo", "changebillid", "SP011"),6));
			curStaffchangeinfo.setChangestaffno(this.oldstaffno);
			curStaffchangeinfo.setAppchangecompid(this.oldcompno);
			curStaffchangeinfo.setStaffmangerno(this.oldstaffinid);
			curStaffchangeinfo.setChangedate(CommonTool.getCurrDate());
			curStaffchangeinfo.setValidateenddate(CommonTool.setDateMask(dispatchdate));
			curStaffchangeinfo.setValidatestartdate(CommonTool.setDateMask(dispatchdate));
			curStaffchangeinfo.setBeforedepartment(this.olddepid);
			curStaffchangeinfo.setBeforepostation(this.oldpostion);
			curStaffchangeinfo.setBeforesalary(new BigDecimal(this.oldSalary));
			curStaffchangeinfo.setBeforesalarytype(0);
			curStaffchangeinfo.setBeforeyejitype(this.oldyjtype+"");
			curStaffchangeinfo.setBeforeyejirate(new BigDecimal(this.oldyjrate));
			curStaffchangeinfo.setBeforeyejiamt(new BigDecimal(this.oldyjamt));
			curStaffchangeinfo.setAftercompid(this.newcompno);
			curStaffchangeinfo.setAfterstaffno(this.newstaffno);
			curStaffchangeinfo.setAfterdepartment(this.olddepid);
			curStaffchangeinfo.setAfterpostation(this.oldpostion);
			curStaffchangeinfo.setAftersalary(new BigDecimal(this.oldSalary));
			curStaffchangeinfo.setAftersalarytype(0);
			curStaffchangeinfo.setAfteryejitype(this.oldyjtype+"");
			curStaffchangeinfo.setAfteryejirate(new BigDecimal(this.oldyjrate));
			curStaffchangeinfo.setAfteryejiamt(new BigDecimal(this.oldyjamt));
			StringBuffer msgBuffer=new StringBuffer();
			checkflag=this.bc012Service.handStaffNoDD(curStaffchangeinfo, msgBuffer);
			if(checkflag==false)
			{
				this.strMessage=msgBuffer.toString();
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
	
	
	
	@Action(value = "postStaffCH",  results = { 
			 @Result(name = "post_success", type = "json"),
       @Result(name = "post_failure", type = "json")	
    }) 
	public String postStaffCH()
	{
		try
		{
			this.strMessage="";
			boolean checkflag=this.bc012Service.checkStaffchangeinfoByInid(this.curStaffchangeinfo.getStaffmangerno());
			if(checkflag==false)
			{
				strMessage="该员工编号已经在未生效的人事单中存在,请查实!";
				return SystemFinal.POST_SUCCESS;
			}
			if(!CommonTool.FormatString(curStaffchangeinfo.getChangestaffno()).equals(CommonTool.FormatString(curStaffchangeinfo.getAfterstaffno())))
			{
				checkflag=this.bc012Service.checkStaffno(this.curStaffchangeinfo.getAftercompid(), this.curStaffchangeinfo.getAfterstaffno());
				if(checkflag==false)
				{
					strMessage="该新员工编号已经在员工资料中存在,请更改入职工号!";
					return SystemFinal.POST_SUCCESS;
				}
			}
			curStaffchangeinfo.setChangedate(CommonTool.getCurrDate());
			curStaffchangeinfo.setValidateenddate(CommonTool.setDateMask(curStaffchangeinfo.getValidatestartdate()));
			curStaffchangeinfo.setValidatestartdate(CommonTool.setDateMask(curStaffchangeinfo.getValidatestartdate()));
			curStaffchangeinfo.setId(new StaffchangeinfoId(CommonTool.getLoginInfo("COMPID"),this.bc012Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"staffchangeinfo", "changebillid", "SP011"),3));
			StringBuffer msgBuffer=new StringBuffer();
			checkflag=this.bc012Service.handStaffNoDD(curStaffchangeinfo, msgBuffer);
			if(checkflag==false)
			{
				this.strMessage=msgBuffer.toString();
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
	
	
	@Action(value = "dispatchSalaryUp",  results = { 
			 @Result(name = "post_success", type = "json"),
        @Result(name = "post_failure", type = "json")	
     }) 
	public String dispatchSalaryUp()
	{
		try
		{
			this.strMessage="";
			boolean checkflag=this.bc012Service.checkStaffchangeinfoByInid(oldstaffinid);
			if(checkflag==false)
			{
				strMessage="该员工编号已经在未生效的人事单中存在,请查实!";
				return SystemFinal.POST_SUCCESS;
			}
			
			boolean flag=this.bc012Service.postStaffSalayrUp(oldcompno, oldstaffno, oldstaffinid, olddepid, oldpostion, oldyjtype, oldyjrate, oldyjamt, oldSalary, newyjtype, newyjrate, newyjamt, newSalary, dispatchdate);
			if(flag==false)
			{
				this.strMessage="设置失败,请重试!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	
	private static void setDataFormat2JAVA(){ 
//		设定日期转换格式 
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"})); 
		} 
	
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		
		this.lsStaffinfo=this.bc012Service.loadDateSetByCompId(this.strStaffNo,this.strStaffName,this.strStaffInNo,this.strPCID,strFingerId);
		if(lsStaffinfo!=null && lsStaffinfo.size()>0)
		{
			for(int i=0;i<lsStaffinfo.size();i++)
			{
				lsStaffinfo.get(i).setBstaffno(lsStaffinfo.get(i).getId().getStaffno());
				lsStaffinfo.get(i).setArrivaldate(CommonTool.getDateMask(lsStaffinfo.get(i).getArrivaldate()));
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="dispatchStaff", results={ @Result( type="json", name="load_success"),
											   @Result( type="json", name="load_failure")})
	public String dispatchStaff()
	{
		this.strMessage="";
		if(CommonTool.setDateMask(dispatchdate).equals("") || CommonTool.setDateMask(tdispatchdate).equals("")
		 || Integer.parseInt(CommonTool.setDateMask(dispatchdate))>Integer.parseInt(CommonTool.setDateMask(tdispatchdate)) )
		{
			this.strMessage="派遣日期格式不正确,请确认!";
			return SystemFinal.LOAD_SUCCESS;
		}
		boolean flag=this.bc012Service.validatedispatch(this.dispatchcompno,this.oldstaffinid, this.dispatchdate,this.tdispatchdate);
		if(flag==false)
		{
			this.strMessage="该员工已在该区间做了派遣,不能继续派遣,请确认!";
			return SystemFinal.LOAD_SUCCESS;
		}
		flag=this.bc012Service.postStaffDispatch(oldcompno, oldstaffno, oldstaffinid, olddepid, oldpostion, oldyjtype, oldyjrate, oldyjamt, dispatchcompno, dispatchdate,this.tdispatchdate);
		if(flag==false)
		{
			this.strMessage="派遣失败,请重试!";
			return SystemFinal.LOAD_SUCCESS;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@JSON(serialize=false)
	public BC012Service getBc012Service() {
		return bc012Service;
	}
	@JSON(serialize=false)
	public void setBc012Service(BC012Service bc012Service) {
		this.bc012Service = bc012Service;
	}

	public String getStrCurCompId() {
		return strCurCompId;
	}

	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}

	public String getStrCurStaffId() {
		return strCurStaffId;
	}

	public void setStrCurStaffId(String strCurStaffId) {
		this.strCurStaffId = strCurStaffId;
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

	public List<Staffhistory> getLsStaffhistory() {
		return lsStaffhistory;
	}

	public void setLsStaffhistory(List<Staffhistory> lsStaffhistory) {
		this.lsStaffhistory = lsStaffhistory;
	}
	
	@Action(value = "loadCurStaffInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
     @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurStaffInfo()
	{
		try
		{
			this.lsStaffinfo=this.bc012Service.loadStaffinfosByCompId(this.strCurCompId);
			if(this.lsStaffinfo!=null && this.lsStaffinfo.size()>0)
			{
				for(int i=0;i<lsStaffinfo.size();i++)
				{
					this.lsStaffinfo.get(i).setBstaffno(this.lsStaffinfo.get(i).getId().getStaffno());
					lsStaffinfo.get(i).setArrivaldate(CommonTool.getDateMask(lsStaffinfo.get(i).getArrivaldate()));
				}
				this.lsStaffhistory=this.bc012Service.loadStaffhistorys(this.strCurCompId, this.lsStaffinfo.get(0).getId().getStaffno());
				
			}
			else
			{
				this.lsStaffinfo=new ArrayList();
				this.lsStaffhistory=new ArrayList();
			}
			
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	
	@Action(value = "loadStaffAbsence",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadStaffAbsence()
	{
		try
		{
			this.lsStaffabsenceinfo=this.bc012Service.loadAbsenceInfos(this.strCurCompId, this.strStaffInNo, CommonTool.getCurrDate());
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "loadManageShare",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String loadManageShare()
	{
		try
		{
			this.lsManagerShare=this.bc012Service.loadManagerShareInfos(this.strStaffInNo);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "postManageShare",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String postManageShare()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			
			boolean flag=this.bc012Service.validateManagerShare(strStaffInNo, this.strCurCompId);
			if(flag==true)
			{
				this.strMessage="该员工在当店已设置底薪!";
				return SystemFinal.LOAD_SUCCESS;
			}
			flag=this.bc012Service.postManagerShareInfo(this.strCurCompId, strStaffNo, strStaffInNo, sharesalary);
			if(flag==false)
			{
				this.strMessage="录入失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "deleteManageShare",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String deleteManageShare()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc012Service.deleteManagerShareInfo(this.strCurCompId,  strStaffInNo);
			if(flag==false)
			{
				this.strMessage="删除失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	
	@Action(value = "comfrimBillBd",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String comfrimBillBd()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc012Service.handLsBill(this.strEntryCompId,  this.strEntryBillId,1);
			if(flag==false)
			{
				this.strMessage="删除失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "comfrimbackBillBd",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String comfrimbackBillBd()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc012Service.handLsBill(this.strEntryCompId,  strEntryBillId,2);
			if(flag==false)
			{
				this.strMessage="删除失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	
	@Action(value = "addChildrenStaffInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String addChildrenStaffInfo()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.curStaffinfo=this.bc012Service.loadStaffinfosByStaffNo(this.strCurCompId, this.strChildrenStaffNo);
			if(curStaffinfo==null || curStaffinfo.getId()==null || CommonTool.FormatString(curStaffinfo.getId().getStaffno()).equals(""))
			{
				this.strMessage="该员工编号不存在,请重新输入!";
				return SystemFinal.LOAD_SUCCESS;
			}
			boolean flag=this.bc012Service.validateChildrenInfo(this.strCurCompId, this.strCurStaffId,this.curStaffinfo.getManageno());
			if(flag==true)
			{
				this.strMessage="该员工编号已经存在于管理列表中,请确认!";
				return SystemFinal.LOAD_SUCCESS;
			}
			flag=this.bc012Service.handAddChildrenInfo(strCurCompId, strCurStaffId, curStaffinfo.getId().getStaffno(), curStaffinfo.getStaffname(), curStaffinfo.getDepartment(), curStaffinfo.getManageno());
			if(flag==false)
			{
				this.strMessage="添加员工失败,请确认!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "comfrimstopBillBd",  results = { 
			 @Result(name = "load_success", type = "json"),
  @Result(name = "load_failure", type = "json")	
	}) 
	public String comfrimstopBillBd()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc012Service.handLsBill(this.strEntryCompId,  strEntryBillId,3);
			if(flag==false)
			{
				this.strMessage="删除失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	
	
	@Action(value = "postAbsenceStaff",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String postAbsenceStaff()
	{
		try
		{
			this.strMessage="";
			if(CommonTool.setDateMask(strAbsencedate).equals(""))
			{
				this.strMessage="请注意缺勤日期格式不正确!";
				return SystemFinal.LOAD_SUCCESS;
			}
			boolean flag=this.bc012Service.validateStaffAbsence(strStaffInNo, this.strAbsencedate);
			if(flag==true)
			{
				this.strMessage="该员工当天的缺勤记录已录入系统!";
				return SystemFinal.LOAD_SUCCESS;
			}
			flag=this.bc012Service.postStaffabsenceinfo(this.strCurCompId, strStaffNo, strStaffInNo, strAbsencedate);
			if(flag==false)
			{
				this.strMessage="录入失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "postAllAbsenceStaff",  results = { 
			 @Result(name = "load_success", type = "json"),
  @Result(name = "load_failure", type = "json")	
	}) 
	public String postAllAbsenceStaff()
	{
		try
		{
			this.strMessage="";
			if(CommonTool.FormatString(this.strtoMonth).equals(""))
			{
				this.strMessage="请注意必须选择缺勤日期!";
				return SystemFinal.LOAD_SUCCESS;
			}
			String[] toDays=strtoMonth.split(",");
			boolean flag=this.bc012Service.postAllStaffabsenceinfo(this.strCurCompId, strStaffNo, strStaffInNo, toDays);
			toDays=null;
			if(flag==false)
			{
				this.strMessage="录入失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "loadCurStaff",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurStaff()
	{
		try
		{
			this.curStaffinfo=this.bc012Service.loadStaffinfosByStaffNo(this.strCurCompId, this.strCurStaffId);
			this.curStaffinfo.setBcompname(this.bc012Service.getDataTool().loadCompNameById(strCurCompId));
			this.curStaffinfo.setContractdate(CommonTool.getDateMask(this.curStaffinfo.getContractdate()));
			this.curStaffinfo.setArrivaldate(CommonTool.getDateMask(this.curStaffinfo.getArrivaldate()));
			this.lsStaffhistory=this.bc012Service.loadStaffhistorys(this.strCurCompId,this.strCurStaffId);
			this.lsStaffinfomanger=this.bc012Service.loadStaffinfomanger(this.strCurCompId,this.strCurStaffId);
			this.sysParamSp103=this.bc012Service.getDataTool().loadSysParam(strCurCompId,"SP103");
			if(!curStaffinfo.getManageno().equals(""))
				this.lsBdDataSet=this.bc012Service.loadBdDateSetByCompId(strCurCompId,curStaffinfo.getManageno());
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "editCurRecord",  results = { 
			 @Result(name = "post_success", type = "json"),
   @Result(name = "post_failure", type = "json")	
	}) 
	public String editCurRecord()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc012Service.postStaffInfo(this.curStaffinfo);
			if(flag==false)
			{
				this.strMessage="更新失败";
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	
	@Action(value = "loadCurStaffHistoryInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurStaffHistoryInfo()
	{
		try
		{
			
			this.lsStaffhistory=this.bc012Service.loadStaffhistorys(this.strCurCompId,this.strCurStaffId);
			if(lsStaffhistory==null)
				lsStaffhistory=new ArrayList();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	@Action(value = "loadFingerDataSet",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadFingerDataSet()
	{
//		try
//		{
//			Service service = new ObjectServiceFactory().create(ICommonService.class);  
//			XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
//			String url = "http://222.73.31.10:7001/AMNCommonService/services/CommonService";
//			ICommonService commonService = (ICommonService) factory.create(service,url);
//			
//			this.strMessage="";
//			if(this.bc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC012", SystemFinal.UR_SPECIAL_CHECK)==false)
//			{
//				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
//				return SystemFinal.LOAD_SUCCESS;
//			}
//			int strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.bc012Service.getDataTool().loadSysParam(strCurCompId,"SP072")));
//			String strFingerMacineIp=this.bc012Service.getDataTool().loadSysParam(this.strCurCompId,"SP073");
//			int result=commonService.CKT_RegisterNet_local(strFingerMacineId,strFingerMacineIp);
//			if(result==0)
//			{
//				strMessage="连接门店考勤机失败,请核实系统指纹信息设置";
//				return SystemFinal.LOAD_SUCCESS;
//			}
//			result=commonService.CKT_NetDaemon_local();
//			String strRecord=commonService.GetClockingRecordProgress_local(strFingerMacineId);
//			if(!CommonTool.FormatString(strRecord).equals(""))
//			{
//				this.bc012Service.handStaffRecord(strFingerMacineId,strRecord);
//			}
//			
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			return SystemFinal.LOAD_FAILURE;
//		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	
	
	@Action(value = "handEmpInfoFinger",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String handEmpInfoFinger()
	{
		try
		{
			Service service = new ObjectServiceFactory().create(ICommonService.class);  
			XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
			String url = "http://222.73.31.10:7001/AMNCommonService/services/CommonService";
			ICommonService commonService = (ICommonService) factory.create(service,url);
			
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC012", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.LOAD_SUCCESS;
			}
			
			int strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.bc012Service.getDataTool().loadSysParam(strCurCompId,"SP072")));
			String strFingerMacineIp=this.bc012Service.getDataTool().loadSysParam(this.strCurCompId,"SP073");
			int result=commonService.CKT_RegisterNet_local(strFingerMacineId,strFingerMacineIp);
			if(result==0)
			{
				strMessage="连接门店考勤机失败,请核实系统指纹信息设置";
				return SystemFinal.LOAD_SUCCESS;
			}
			result=commonService.CKT_NetDaemon_local();
			if(strCurStaffName.length()>3)
				strCurStaffName=strCurStaffName.substring(0,3);
			result=commonService.CKT_ModifyPersonInfo_local(strFingerMacineId,strCurFingerId,"",0,this.strCurStaffName,0,0,0,0,0);
			if(result==0)
			{
				strMessage="创建员工失败";
			}
			String strUploadFilename="D:/finger/"+strCurFingerId+"_0.anv";
			result=commonService.CKT_GetFPTemplateSaveFile_local(strFingerMacineId,strCurFingerId,0,strUploadFilename);
			if(result==0)
			{
				strMessage="备份员工第一个指纹失败";
				return SystemFinal.LOAD_SUCCESS;
			}
			strUploadFilename="D:/finger/"+strCurFingerId+"_1.anv";
			result=commonService.CKT_GetFPTemplateSaveFile_local(strFingerMacineId,strCurFingerId,1,strUploadFilename);
			if(result==0)
			{
				strMessage="备份员工第二个指纹失败";
				return SystemFinal.LOAD_SUCCESS;
			}
			commonService.CKT_Disconnect_local();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "downFingerForPerson",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String downFingerForPerson()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC012", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.LOAD_SUCCESS;
			}
			handFingerInfo(this.strCurCompId,this.strCurFingerId,this.strCurStaffName);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "downFaceForPerson",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String downFaceForPerson()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC012", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.LOAD_SUCCESS;
			}
			handFaceInfo(this.strCurCompId,this.strCurFingerId,this.strCurStaffName);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "backupFactForPerson",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String backupFactForPerson()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC012", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.LOAD_SUCCESS;
			}
			boolean flag=handBackFaceInfo(this.strCurCompId,this.strCurFingerId,this.strStaffNo,this.strToCurCompId);
			if(flag==false)
			{
				this.strMessage="拷贝失败";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "downFingerForComp",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String downFingerForComp()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC012", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.LOAD_SUCCESS;
			}
			List<Staffinfo> lsstaff=this.bc012Service.getDataTool().loadEmpsByCompId(strCurCompId, 2);
			if(lsstaff!=null && lsstaff.size()>0)
			{
				for(int i=0;i<lsstaff.size();i++)
				{
					if(CommonTool.FormatInteger(lsstaff.get(i).getFingerno())!=0)
					{
						handFingerInfo(this.strCurCompId,CommonTool.FormatInteger(lsstaff.get(i).getFingerno()),CommonTool.FormatString(lsstaff.get(i).getStaffname()));
					}
				}
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	public boolean handFaceInfo(String strStoreId,int strFingerId,String strStaffName)
	{
		try
		{
			Service service = new ObjectServiceFactory().create(ICommonService.class);  
			XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
			String url = "http://10.0.0.243:7003/AMNFaceWebService/services/CommonService";
			ICommonService commonService = (ICommonService) factory.create(service,url);
		
			this.strMessage="";
			//先从总部机器备份指纹
			//boolean flag=commonService.postEmpFaceInfo("10.1.2.200", strFingerId+"");
			String strZbMacineIp=this.bc012Service.getDataTool().loadSysParam("001","SP073");
			boolean flag=commonService.postEmpFaceInfo(strZbMacineIp, strFingerId+"");
			if(flag==false)
			{
				strMessage="总部人脸考勤机上无此工号的人脸数据,请确认";
				return false;
			}
			//转移人脸数据
			if(strStoreId.equals(CommonTool.getLoginInfo("COMPID")))
			{
				strMessage="该门店已经存在此人脸数据,请确认";
				return false;
			}
			String strFingerMacineIp=this.bc012Service.getDataTool().loadSysParam(strStoreId,"SP073");
			if(CommonTool.FormatString(strFingerMacineIp).equals(""))
			{
				strMessage="该门店未设置人脸考勤机的IP,请确认";
				return false;
			}
			//flag=commonService.uploadEmpFaceInfo("10.1.2.200", strFingerId+"",strFingerMacineIp, strFingerId+"",strStaffName);

			flag=commonService.uploadEmpFaceInfo(strZbMacineIp, strFingerId+"",strFingerMacineIp, strFingerId+"",strStaffName);
			if(flag==false)
			{
				strMessage="转移人脸数据失败,请重试";
				return false;
			}
			flag=commonService.updateFaceOpenRight(strZbMacineIp, strFingerId+"", strFingerId+"",strZbMacineIp);
			System.out.println(flag);
		return true;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	//还原备份人脸数据
	public boolean handBackFaceInfo(String strStoreId,int strFingerId,String strStaffNo,String strToCurCompId)
	{
		try
		{
			String strFingerMacineIp=this.bc012Service.getDataTool().loadSysParam(strStoreId,"SP072");
			/*if(strStoreId.equals("001"))
			{
				strFingerId=Integer.parseInt(strStaffNo);
				//strFingerMacineIp="10.1.2.200";
				strFingerMacineIp="10.0.1.44";
			}*/
			FaceId_EmployeeBean record=this.bc012Service.loadBackFaceInfoById(strFingerId);
			if(record!=null)
			{
				String[] strComps=strToCurCompId.split(";");
				if(strComps!=null && strComps.length>0)
				{
					for(int i=0;i<strComps.length;i++)
					{
						if(!strComps[i].equals(""))
						{
							strFingerMacineIp=this.bc012Service.getDataTool().loadSysParam(strComps[i],"SP073");
							
							if(!CommonTool.FormatString(strFingerMacineIp).equals(""))
							{
								System.out.println(strFingerMacineIp);
								Service service = new ObjectServiceFactory().create(ICommonService.class);  
								XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
								String url = "http://10.0.0.243:7003/AMNFaceWebService/services/CommonService";
								System.out.println(strFingerMacineIp);
								ICommonService commonService = (ICommonService) factory.create(service,url);
								commonService.uploadEmpFaceInfoAllInfo(strFingerMacineIp, strFingerId+"",record.getName(),record.getCard_num(),record.getCalid(),record.getFace_data());
								System.out.println(strFingerMacineIp);
							}
						}
					}
				}
			}
			else
			{
				this.strMessage="系统不存在该员工的备份数据!";
			}
			return true;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	//拷贝人脸数据
	public boolean handFingerInfo(String strStoreId,int strFingerId,String strStaffName)
	{
		try
		{
		Service service = new ObjectServiceFactory().create(ICommonService.class);  
		XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
		String url = "http://222.73.31.10:7001/AMNCommonService/services/CommonService";
		ICommonService commonService = (ICommonService) factory.create(service,url);
		
		//先从总部机器上下载员工指纹
		int strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.bc012Service.getDataTool().loadSysParam("001","SP072")));
		String strFingerMacineIp=this.bc012Service.getDataTool().loadSysParam("001","SP073");
		int result=commonService.CKT_RegisterNet_local(strFingerMacineId,strFingerMacineIp);
		if(result==0)
		{
			strMessage="连接总部考勤机失败,请核实系统指纹信息设置";
			return false;
		}
		result=commonService.CKT_NetDaemon_local();
		if(strStaffName.length()>3)
			strStaffName=strStaffName.substring(0,3);
		result=commonService.CKT_ModifyPersonInfo_local(strFingerMacineId,strFingerId,"",0,strStaffName,0,0,0,3,0);
		if(result==0)
		{
			strMessage="创建员工失败";
			return false;
		}
		String strUploadFilenameone="D:/finger/"+strFingerId+"_0.anv";
		result=commonService.CKT_GetFPTemplateSaveFile_local(strFingerMacineId,strFingerId,0,strUploadFilenameone);
		if(result==0)
		{
			strMessage=strMessage+"备份员工第一个指纹失败";
			//return SystemFinal.LOAD_SUCCESS;
		}
		String strUploadFilenametwo="D:/finger/"+strFingerId+"_1.anv";
		result=commonService.CKT_GetFPTemplateSaveFile_local(strFingerMacineId,strFingerId,1,strUploadFilenametwo);
		if(result==0)
		{
			strMessage=strMessage+"备份员工第二个指纹失败";
			//return SystemFinal.LOAD_SUCCESS;
		}
		//上传到门店的指纹机器中
		 strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.bc012Service.getDataTool().loadSysParam(strStoreId,"SP072")));
		 strFingerMacineIp=this.bc012Service.getDataTool().loadSysParam(strStoreId,"SP073");
		 result=commonService.CKT_RegisterNet_local(strFingerMacineId,strFingerMacineIp);
		 if(result==0)
		 {
				strMessage="连接门店考勤机失败,请核实系统指纹信息设置";
				return false;
		 }
		 result=commonService.CKT_NetDaemon_local();
		 result=commonService.CKT_ModifyPersonInfo_local(strFingerMacineId,strFingerId,"",0,strStaffName,0,0,0,0,0);
		 if(result==0)
		 {
				strMessage=strMessage+"创建员工失败";
				return false;
		 }
		 //上传第一个指纹
		 result=commonService.CKT_PutFPTemplateLoadFile_local(strFingerMacineId,strFingerId,0,strUploadFilenameone);
		 if(result==0)
		 {
				strMessage=strMessage+"备份员工第一个指纹失败";
				//return SystemFinal.LOAD_SUCCESS;
		 }
		 //上传第二个指纹
		 result=commonService.CKT_PutFPTemplateLoadFile_local(strFingerMacineId,strFingerId,1,strUploadFilenametwo);
		 if(result==0)
		 {
				strMessage=strMessage+"上传员工第二个指纹失败";
				//return SystemFinal.LOAD_SUCCESS;
		 }
		 commonService.CKT_Disconnect_local();
		
		return true;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	@Action(value = "resetCasherRoleAccount",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String resetCasherRoleAccount()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC012", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.LOAD_SUCCESS;
			}
			boolean flag=this.bc012Service.postCashAccount(this.strCurCompId, this.strCurStaffId,this.strStaffInNo);
			if(flag==false)
			{
				this.strMessage="设置失败";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	//设置员工洗头是否合格
	@Action(value = "resetStaffHiarState",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String resetStaffHiarState()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC012", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.LOAD_SUCCESS;
			}
			boolean flag=this.bc012Service.handStaffHairState(this.strCurCompId, this.strCurStaffId,this.strHairState);
			if(flag==false)
			{
				this.strMessage="设置失败";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}

	
	//设置烫染课程是否合格
	@Action(value = "resetStaffTrkcState",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String resetStaffTrkcState()
	{
		try
		{
			this.strMessage="";
			if(this.bc012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC012", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.LOAD_SUCCESS;
			}
			boolean flag=this.bc012Service.handStaffTrkcState(this.strCurCompId, this.strCurStaffId,this.strHairState);
			if(flag==false)
			{
				this.strMessage="设置失败";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	public Staffchangeinfo getCurStaffchangeinfo() {
		return curStaffchangeinfo;
	}

	public void setCurStaffchangeinfo(Staffchangeinfo curStaffchangeinfo) {
		this.curStaffchangeinfo = curStaffchangeinfo;
	}

	public String getStrPCID() {
		return strPCID;
	}

	public void setStrPCID(String strPCID) {
		this.strPCID = strPCID;
	}

	public String getStrStaffNo() {
		return strStaffNo;
	}

	public void setStrStaffNo(String strStaffNo) {
		this.strStaffNo = strStaffNo;
	}

	public String getStrStaffInNo() {
		return strStaffInNo;
	}

	public void setStrStaffInNo(String strStaffInNo) {
		this.strStaffInNo = strStaffInNo;
	}

	public int getStrCurFingerId() {
		return strCurFingerId;
	}

	public void setStrCurFingerId(int strCurFingerId) {
		this.strCurFingerId = strCurFingerId;
	}

	public String getStrCurStaffName() {
		return strCurStaffName;
	}

	public void setStrCurStaffName(String strCurStaffName) {
		this.strCurStaffName = strCurStaffName;
	}

	public String getOldcompno() {
		return oldcompno;
	}

	public void setOldcompno(String oldcompno) {
		this.oldcompno = oldcompno;
	}

	public String getOldstaffno() {
		return oldstaffno;
	}

	public void setOldstaffno(String oldstaffno) {
		this.oldstaffno = oldstaffno;
	}

	public String getOldstaffinid() {
		return oldstaffinid;
	}

	public void setOldstaffinid(String oldstaffinid) {
		this.oldstaffinid = oldstaffinid;
	}

	public String getOlddepid() {
		return olddepid;
	}

	public void setOlddepid(String olddepid) {
		this.olddepid = olddepid;
	}

	public String getOldpostion() {
		return oldpostion;
	}

	public void setOldpostion(String oldpostion) {
		this.oldpostion = oldpostion;
	}

	public int getOldyjtype() {
		return oldyjtype;
	}

	public void setOldyjtype(int oldyjtype) {
		this.oldyjtype = oldyjtype;
	}

	public double getOldyjrate() {
		return oldyjrate;
	}

	public void setOldyjrate(double oldyjrate) {
		this.oldyjrate = oldyjrate;
	}

	public double getOldyjamt() {
		return oldyjamt;
	}

	public void setOldyjamt(double oldyjamt) {
		this.oldyjamt = oldyjamt;
	}

	public String getOldstaffname() {
		return oldstaffname;
	}

	public void setOldstaffname(String oldstaffname) {
		this.oldstaffname = oldstaffname;
	}

	public String getDispatchcompno() {
		return dispatchcompno;
	}

	public void setDispatchcompno(String dispatchcompno) {
		this.dispatchcompno = dispatchcompno;
	}

	public String getDispatchdate() {
		return dispatchdate;
	}

	public void setDispatchdate(String dispatchdate) {
		this.dispatchdate = dispatchdate;
	}

	public String getStrStaffName() {
		return strStaffName;
	}

	public void setStrStaffName(String strStaffName) {
		this.strStaffName = strStaffName;
	}

	public double getOldSalary() {
		return oldSalary;
	}

	public void setOldSalary(double oldSalary) {
		this.oldSalary = oldSalary;
	}

	public int getNewyjtype() {
		return newyjtype;
	}

	public void setNewyjtype(int newyjtype) {
		this.newyjtype = newyjtype;
	}

	public double getNewyjrate() {
		return newyjrate;
	}

	public void setNewyjrate(double newyjrate) {
		this.newyjrate = newyjrate;
	}

	public double getNewyjamt() {
		return newyjamt;
	}

	public void setNewyjamt(double newyjamt) {
		this.newyjamt = newyjamt;
	}

	public double getNewSalary() {
		return newSalary;
	}

	public void setNewSalary(double newSalary) {
		this.newSalary = newSalary;
	}

	public String getTdispatchdate() {
		return tdispatchdate;
	}

	public void setTdispatchdate(String tdispatchdate) {
		this.tdispatchdate = tdispatchdate;
	}

	public List<Staffabsenceinfo> getLsStaffabsenceinfo() {
		return lsStaffabsenceinfo;
	}

	public void setLsStaffabsenceinfo(List<Staffabsenceinfo> lsStaffabsenceinfo) {
		this.lsStaffabsenceinfo = lsStaffabsenceinfo;
	}

	public String getStrAbsencedate() {
		return strAbsencedate;
	}

	public void setStrAbsencedate(String strAbsencedate) {
		this.strAbsencedate = strAbsencedate;
	}

	public String getStrtoMonth() {
		return strtoMonth;
	}

	public void setStrtoMonth(String strtoMonth) {
		this.strtoMonth = strtoMonth;
	}

	public List<ManagerShare> getLsManagerShare() {
		return lsManagerShare;
	}

	public void setLsManagerShare(List<ManagerShare> lsManagerShare) {
		this.lsManagerShare = lsManagerShare;
	}

	public BigDecimal getSharesalary() {
		return sharesalary;
	}

	public void setSharesalary(BigDecimal sharesalary) {
		this.sharesalary = sharesalary;
	}

	public String getNewcompno() {
		return newcompno;
	}

	public void setNewcompno(String newcompno) {
		this.newcompno = newcompno;
	}

	public String getNewstaffno() {
		return newstaffno;
	}

	public void setNewstaffno(String newstaffno) {
		this.newstaffno = newstaffno;
	}

	public String getStrFingerId() {
		return strFingerId;
	}

	public void setStrFingerId(String strFingerId) {
		this.strFingerId = strFingerId;
	}

	public String getStrToCurCompId() {
		return strToCurCompId;
	}

	public void setStrToCurCompId(String strToCurCompId) {
		this.strToCurCompId = strToCurCompId;
	}

	public String getStrHairState() {
		return strHairState;
	}

	public void setStrHairState(String strHairState) {
		this.strHairState = strHairState;
	}

	public String getStrChildrenStaffNo() {
		return strChildrenStaffNo;
	}

	public void setStrChildrenStaffNo(String strChildrenStaffNo) {
		this.strChildrenStaffNo = strChildrenStaffNo;
	}

	public List<Staffinfomanger> getLsStaffinfomanger() {
		return lsStaffinfomanger;
	}

	public void setLsStaffinfomanger(List<Staffinfomanger> lsStaffinfomanger) {
		this.lsStaffinfomanger = lsStaffinfomanger;
	}

}
