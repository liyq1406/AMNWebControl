package com.amani.action.BaseInfoControl;

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

import com.amani.model.Staffinfo;
import com.amani.model.Sysuserinfo;
import com.amani.model.Usereditright;
import com.amani.model.UsereditrightId;
import com.amani.model.Useroverall;
import com.amani.model.UseroverallId;
import com.amani.service.BaseInfoControl.BC004Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc004")
public class BC004Action extends AMN_ModuleAction{
	@Autowired
	private BC004Service bc004Service;
	private String strJsonParam;
	private String strJsonParam_forth;
	private String strJsonParam_five;
	private String strJsonParam_six;
	private String strJsonParam_seven;
	private String strJsonParam_specilright;
    private List<Sysuserinfo> lsSysuserinfo;
    private List<Usereditright> lsUsereditright;
    private String strCurCompId;
    private String strCurUserId;
    private Sysuserinfo curUserInfo;
    private List<Staffinfo> lsStaffinfo;
    private String strCurEmpId;
    private Staffinfo curStaffinfo;
    private List<Useroverall> lsUseroverall_store;
    private List<Useroverall> lsUseroverall_mozu;
    private List<Useroverall> lsUseroverall_mokuai;
    private List<Useroverall> lsUseroverall_right;
	public String getStrCurEmpId() {
		return strCurEmpId;
	}
	public void setStrCurEmpId(String strCurEmpId) {
		this.strCurEmpId = strCurEmpId;
	}
	public Staffinfo getCurStaffinfo() {
		return curStaffinfo;
	}
	public void setCurStaffinfo(Staffinfo curStaffinfo) {
		this.curStaffinfo = curStaffinfo;
	}
	public Sysuserinfo getCurUserInfo() {
		return curUserInfo;
	}
	public void setCurUserInfo(Sysuserinfo curUserInfo) {
		this.curUserInfo = curUserInfo;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	public String getStrCurUserId() {
		return strCurUserId;
	}
	public void setStrCurUserId(String strCurUserId) {
		this.strCurUserId = strCurUserId;
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
		// TODO Auto-generated method stub
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
	
	public String getStrJsonParam_specilright() {
		return strJsonParam_specilright;
	}
	public void setStrJsonParam_specilright(String strJsonParam_specilright) {
		this.strJsonParam_specilright = strJsonParam_specilright;
	}
	@JSON(serialize=false)
	public BC004Service getBc004Service() {
		return bc004Service;
	}
	@JSON(serialize=false)
	public void setBc004Service(BC004Service bc004Service) {
		this.bc004Service = bc004Service;
	}
	public List<Sysuserinfo> getLsSysuserinfo() {
		return lsSysuserinfo;
	}
	public void setLsSysuserinfo(List<Sysuserinfo> lsSysuserinfo) {
		this.lsSysuserinfo = lsSysuserinfo;
	}
	
	@Action(value = "loadSysuserinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
        @Result(name = "load_failure", type = "json")	
     }) 
	public String loadSysuserinfo()
	{
		this.lsSysuserinfo=this.bc004Service.loadUsersByCompId(this.strCurCompId);
		this.lsStaffinfo=this.bc004Service.getDataTool().loadEmpsByCompId(this.strCurCompId,2);
		return SystemFinal.LOAD_SUCCESS;
	}
	@Action(value = "loadUsereditrightByUserId",  results = { 
			 @Result(name = "load_success", type = "json"),
       @Result(name = "load_failure", type = "json")	
    }) 
	public String loadUsereditrightByUserId()
	{
		if(CommonTool.FormatString(strCurUserId).equals(""))
		{
			this.lsUsereditright=new ArrayList();
		}
		else
		{
			this.lsUsereditright=this.bc004Service.loadEditRightByUserId(this.strCurUserId);
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	public List<Usereditright> getLsUsereditright() {
		return lsUsereditright;
	}
	public void setLsUsereditright(List<Usereditright> lsUsereditright) {
		this.lsUsereditright = lsUsereditright;
	}
	
	@Action(value = "loadCurUserInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
      @Result(name = "load_failure", type = "json")	
   }) 
	public String loadCurUserInfo()
	{
		try
		{
			this.curUserInfo=this.bc004Service.loadUserInfoByUserId(this.strCurUserId);
			this.lsUsereditright=this.bc004Service.loadEditRightByUserId(this.strCurUserId);
			this.lsUseroverall_mozu=this.bc004Service.loadUseroverallByType(this.strCurUserId, "2");
			this.lsUseroverall_store=this.bc004Service.loadUseroverallByType(this.strCurUserId, "3");
			this.lsUseroverall_mokuai=this.bc004Service.loadUseroverallByType(this.strCurUserId, "4");
			this.lsUseroverall_right=this.bc004Service.loadUseroverallByType(this.strCurUserId, "5");
	
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	@Action(value = "validateUserId",  results = { 
			 @Result(name = "load_success", type = "json"),
     @Result(name = "load_failure", type = "json")	
	}) 
	public String validateUserId()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.bc004Service.checkUserIdExists(this.strCurUserId);
			if(flag==false)
			{
				 this.strMessage="该用户编号已经存在,请重新输入用户编号";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "validateEmpId",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String validateEmpId()
	{
		try
		{
			this.strMessage="";
			this.curStaffinfo=this.bc004Service.loadStaffinfoByEmpIs(this.strCurCompId, this.strCurEmpId);
			if(curStaffinfo==null)
			{
				 this.strMessage="该员工编号不存在,请重新输入员工编号";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "syncUserInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String syncUserInfo()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.bc004Service.syncUserInfoByCompId(strCurCompId);
			if(flag==false)
			{
				 this.strMessage="同步操作失败!";
			}
			this.strMessage="同步操作成功!";
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
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
			if(this.bc004Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC004", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.lsUsereditright=this.bc004Service.getDataTool().loadDTOList(this.strJsonParam, Usereditright.class);
			if(lsUsereditright!=null && lsUsereditright.size()>0)
			{
				for(int i=0;i<lsUsereditright.size();i++)
				{
					lsUsereditright.get(i).setId(new UsereditrightId(curUserInfo.getUserno(),lsUsereditright.get(i).getBsysmodeno(),lsUsereditright.get(i).getBfunctionno()));
				}
			}
			this.lsUseroverall_store=this.bc004Service.getDataTool().loadDTOList(this.strJsonParam_forth, Useroverall.class);
			this.lsUseroverall_mozu=this.bc004Service.getDataTool().loadDTOList(this.strJsonParam_five, Useroverall.class);
			this.lsUseroverall_mokuai=this.bc004Service.getDataTool().loadDTOList(this.strJsonParam_six, Useroverall.class);
			this.lsUseroverall_right=this.bc004Service.getDataTool().loadDTOList(this.strJsonParam_seven, Useroverall.class);
			List<Useroverall> lsUseroverall=new ArrayList();
			//禁用门店
			if(lsUseroverall_store!=null && lsUseroverall_store.size()>0)
			{
				for(int i=0;i<lsUseroverall_store.size();i++)
				{
					if( !CommonTool.FormatString(lsUseroverall_store.get(i).getBmodevalue()).equals(""))
					{
						lsUseroverall_store.get(i).setId(new UseroverallId(curUserInfo.getUserno(),"3",CommonTool.FormatString(lsUseroverall_store.get(i).getBmodevalue())));
						lsUseroverall.add(lsUseroverall_store.get(i));
					}
				}				
			}
			//禁用模组
			if(lsUseroverall_mozu!=null && lsUseroverall_mozu.size()>0)
			{
				for(int i=0;i<lsUseroverall_mozu.size();i++)
				{
					if(!CommonTool.FormatString(lsUseroverall_mozu.get(i).getBmodevalue()).equals(""))
					{
						lsUseroverall_mozu.get(i).setId(new UseroverallId(curUserInfo.getUserno(),"2",CommonTool.FormatString(lsUseroverall_mozu.get(i).getBmodevalue())));
						lsUseroverall.add(lsUseroverall_mozu.get(i));
					}
				}				
			}
			//禁用模块
			if(lsUseroverall_mokuai!=null && lsUseroverall_mokuai.size()>0)
			{
				for(int i=0;i<lsUseroverall_mokuai.size();i++)
				{
					if(!CommonTool.FormatString(lsUseroverall_mokuai.get(i).getBmodevalue()).equals(""))
					{
						lsUseroverall_mokuai.get(i).setId(new UseroverallId(curUserInfo.getUserno(),"4",CommonTool.FormatString(lsUseroverall_mokuai.get(i).getBmodevalue())));
						lsUseroverall.add(lsUseroverall_mokuai.get(i));
					}
				}				
			}
			//禁用特殊权限
			if(lsUseroverall_right!=null && lsUseroverall_right.size()>0)
			{
				for(int i=0;i<lsUseroverall_right.size();i++)
				{
					if( !CommonTool.FormatString(lsUseroverall_right.get(i).getBmodevalue()).equals(""))
					{
						lsUseroverall_right.get(i).setId(new UseroverallId(curUserInfo.getUserno(),"5",CommonTool.FormatString(lsUseroverall_right.get(i).getBmodevalue())));
						lsUseroverall.add(lsUseroverall_right.get(i));
					}
				}				
			}
			this.lsUseroverall_mokuai=null;
			this.lsUseroverall_mozu=null;
			this.lsUseroverall_right=null;
			this.lsUseroverall_store=null;
			boolean flag=this.bc004Service.postUserInfos(this.curUserInfo, lsUsereditright,lsUseroverall);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
			}
			lsUseroverall=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
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
			if(this.bc004Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC004", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			boolean flag=this.bc004Service.deleteUserInfos(this.strCurUserId);
			if(flag==false)
			{
				this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
			}
			return SystemFinal.DELETE_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.DELETE_FAILURE;
		}
	}
	
	public List<Staffinfo> getLsStaffinfo() {
		return lsStaffinfo;
	}
	public void setLsStaffinfo(List<Staffinfo> lsStaffinfo) {
		this.lsStaffinfo = lsStaffinfo;
	}
	public List<Useroverall> getLsUseroverall_store() {
		return lsUseroverall_store;
	}
	public void setLsUseroverall_store(List<Useroverall> lsUseroverall_store) {
		this.lsUseroverall_store = lsUseroverall_store;
	}
	public List<Useroverall> getLsUseroverall_mozu() {
		return lsUseroverall_mozu;
	}
	public void setLsUseroverall_mozu(List<Useroverall> lsUseroverall_mozu) {
		this.lsUseroverall_mozu = lsUseroverall_mozu;
	}
	public List<Useroverall> getLsUseroverall_mokuai() {
		return lsUseroverall_mokuai;
	}
	public void setLsUseroverall_mokuai(List<Useroverall> lsUseroverall_mokuai) {
		this.lsUseroverall_mokuai = lsUseroverall_mokuai;
	}
	public List<Useroverall> getLsUseroverall_right() {
		return lsUseroverall_right;
	}
	public void setLsUseroverall_right(List<Useroverall> lsUseroverall_right) {
		this.lsUseroverall_right = lsUseroverall_right;
	}
	public String getStrJsonParam_forth() {
		return strJsonParam_forth;
	}
	public void setStrJsonParam_forth(String strJsonParam_forth) {
		this.strJsonParam_forth = strJsonParam_forth;
	}
	public String getStrJsonParam_five() {
		return strJsonParam_five;
	}
	public void setStrJsonParam_five(String strJsonParam_five) {
		this.strJsonParam_five = strJsonParam_five;
	}
	public String getStrJsonParam_six() {
		return strJsonParam_six;
	}
	public void setStrJsonParam_six(String strJsonParam_six) {
		this.strJsonParam_six = strJsonParam_six;
	}
	public String getStrJsonParam_seven() {
		return strJsonParam_seven;
	}
	public void setStrJsonParam_seven(String strJsonParam_seven) {
		this.strJsonParam_seven = strJsonParam_seven;
	} 
}
