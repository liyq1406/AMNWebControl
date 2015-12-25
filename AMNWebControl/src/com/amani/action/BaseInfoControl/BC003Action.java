package com.amani.action.BaseInfoControl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.amani.bean.roleSetInfo;
import com.amani.model.Sysmodepurview;
import com.amani.model.SysmodepurviewId;
import com.amani.model.Sysrolemode;
import com.amani.model.SysrolemodeId;
import com.amani.model.Sysuserinfo;
import com.amani.service.BaseInfoControl.BC003Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc003")
public class BC003Action extends AMN_ModuleAction{
	@Autowired
	private BC003Service bc003Service;
	private String strJsonParam;
	private String strJsonParam_specilright;
	private List	roleComps;
	private List	roleSpecilrights;
	private List<roleSetInfo> 	roleInfos;
	private List<roleSetInfo> 	rolePosInfos;
	private List<roleSetInfo>	roleFunctionInfos;
	private List<Sysuserinfo>   roleUserInfos;
	private List<Sysrolemode>   roleMozuInfos;
	private List<Sysrolemode>   roleMokuaiInfos;
	private List<Sysmodepurview>   roleSysmodepurviews;
	private String strCurRoleId;
	private String strCurMozuId;
	private String strCurPostion;
	public String getStrCurPostion() {
		return strCurPostion;
	}
	public void setStrCurPostion(String strCurPostion) {
		this.strCurPostion = strCurPostion;
	}
	public String getStrCurMozuId() {
		return strCurMozuId;
	}
	public void setStrCurMozuId(String strCurMozuId) {
		this.strCurMozuId = strCurMozuId;
	}
	public List<Sysrolemode> getRoleMozuInfos() {
		return roleMozuInfos;
	}
	public void setRoleMozuInfos(List<Sysrolemode> roleMozuInfos) {
		this.roleMozuInfos = roleMozuInfos;
	}
	public String getStrCurRoleId() {
		return strCurRoleId;
	}
	public void setStrCurRoleId(String strCurRoleId) {
		this.strCurRoleId = strCurRoleId;
	}
	public List getRoleComps() {
		return roleComps;
	}
	public void setRoleComps(List roleComps) {
		this.roleComps = roleComps;
	}
	public List getRoleSpecilrights() {
		return roleSpecilrights;
	}
	public void setRoleSpecilrights(List roleSpecilrights) {
		this.roleSpecilrights = roleSpecilrights;
	}
	public List<roleSetInfo> getRoleInfos() {
		return roleInfos;
	}
	public void setRoleInfos(List<roleSetInfo> roleInfos) {
		this.roleInfos = roleInfos;
	}
	public List<roleSetInfo> getRoleFunctionInfos() {
		return roleFunctionInfos;
	}
	public void setRoleFunctionInfos(List<roleSetInfo> roleFunctionInfos) {
		this.roleFunctionInfos = roleFunctionInfos;
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
		return false;
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
		return false;
	}
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return false;
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
	@JSON(serialize=false)
	public BC003Service getBc003Service() {
		return bc003Service;
	}
	@JSON(serialize=false)
	public void setBc003Service(BC003Service bc003Service) {
		this.bc003Service = bc003Service;
	}
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}
	public List<Sysuserinfo> getRoleUserInfos() {
		return roleUserInfos;
	}
	public void setRoleUserInfos(List<Sysuserinfo> roleUserInfos) {
		this.roleUserInfos = roleUserInfos;
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
	
	@Action(value = "loadCurRolsInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
         @Result(name = "load_failure", type = "json")	
      }) 
    public String loadCurRolsInfo()
	{
		this.roleInfos=this.bc003Service.loadRoleSetInfo();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadRolsUserInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
        @Result(name = "load_failure", type = "json")	
     }) 
    public String loadRolsUserInfo()
	{
		this.roleUserInfos=this.bc003Service.loadSysuserinfoByRoleId(this.strCurRoleId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadRolsMoZuInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
       @Result(name = "load_failure", type = "json")	
    }) 
    public String loadRolsMoZuInfo()
	{
		this.roleMozuInfos=this.bc003Service.loadSysmozuinfoByRoleId(this.strCurRoleId);
		this.roleSysmodepurviews=this.bc003Service.loadSysmodepurview(this.strCurRoleId);
		this.rolePosInfos=this.bc003Service.loadPositionByRoleId(this.strCurRoleId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadRolsMokuaiInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
      @Result(name = "load_failure", type = "json")	
    }) 
    public String loadRolsMokuaiInfo()
	{
		this.roleMokuaiInfos=this.bc003Service.loadSysmokuaiinfoByRoleId(this.strCurRoleId,this.strCurMozuId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "handBandPos",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
   }) 
   public String handBandPos()
	{
		this.strMessage="";
		boolean flag=false;
		String[] strPostions =this.strCurPostion.split(";");
		if(strPostions!=null && strPostions.length>0)
		{
			for(int i=0;i<strPostions.length;i++)
			{
				if(!strPostions[i].equals(""))
				{
					flag=this.bc003Service.validatePosition(strPostions[i]);
					if(flag==true)
					{
						this.strMessage=strPostions[i]+"该职位已经绑定过角色,请确认!";
						return SystemFinal.LOAD_SUCCESS;
					}
				}
			}
		}
		if(strPostions!=null && strPostions.length>0)
		{
			for(int i=0;i<strPostions.length;i++)
			{
				if(!strPostions[i].equals(""))
				{
					flag=this.bc003Service.postBandRolePosition(strCurRoleId, strPostions[i]);
					if(flag==false)
					{
						this.strMessage="绑定失败,请确认!";
						return SystemFinal.LOAD_SUCCESS;
					}	
				}
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "handdeleteBandPos",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String handdeleteBandPos()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.bc003Service.deleteBandRolePostion(strCurRoleId, strCurPostion);
			if(flag==false)
			{
				this.strMessage="取消绑定失败,请确认!";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_SUCCESS; 
		}
	}
	
	
	@Action(value = "postMoKuai",  results = { 
			 @Result(name = "post_success", type = "json"),
            @Result(name = "post_failure", type = "json")	
         }) 
	public String postMoKuai()
	{
		try
		{
			this.strMessage="";
			if(this.bc003Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC002", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			
			List<Sysrolemode>  lsSysrolemode=new ArrayList();
			if(!CommonTool.FormatString(strJsonParam).equals(""))
			{
				lsSysrolemode=this.bc003Service.getDataTool().loadDTOList(strJsonParam,Sysrolemode.class);
				if(lsSysrolemode!=null && lsSysrolemode.size()>0)
				{
					for(int i=0;i<lsSysrolemode.size();i++)
					{
						lsSysrolemode.get(i).setId(new SysrolemodeId( CommonTool.FormatString(this.strCurRoleId),
																	CommonTool.FormatString(this.strCurMozuId),
																	CommonTool.FormatString(lsSysrolemode.get(i).getBfunctionno())));
					}
				}
			}
			List<Sysmodepurview> lsSysmodepurviews=new ArrayList();
			if(!CommonTool.FormatString(strJsonParam_specilright).equals(""))
			{
				lsSysmodepurviews=this.bc003Service.getDataTool().loadDTOList(strJsonParam_specilright,Sysmodepurview.class);
				if(lsSysmodepurviews!=null && lsSysmodepurviews.size()>0)
				{
					for(int i=0;i<lsSysmodepurviews.size();i++)
					{
						lsSysmodepurviews.get(i).setId(new SysmodepurviewId( CommonTool.FormatString(this.strCurRoleId),
																	CommonTool.FormatString(lsSysmodepurviews.get(i).getBcurpurviewno())));
					}
				}
			}
			
			boolean flag=this.bc003Service.postSysrolemode( CommonTool.FormatString(this.strCurRoleId), CommonTool.FormatString(this.strCurMozuId), lsSysrolemode,lsSysmodepurviews);
			lsSysmodepurviews=null;
			lsSysrolemode=null;
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
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
	
	
	public List<Sysrolemode> getRoleMokuaiInfos() {
		return roleMokuaiInfos;
	}
	public void setRoleMokuaiInfos(List<Sysrolemode> roleMokuaiInfos) {
		this.roleMokuaiInfos = roleMokuaiInfos;
	}
	public String getStrJsonParam_specilright() {
		return strJsonParam_specilright;
	}
	public void setStrJsonParam_specilright(String strJsonParam_specilright) {
		this.strJsonParam_specilright = strJsonParam_specilright;
	}
	public List<Sysmodepurview> getRoleSysmodepurviews() {
		return roleSysmodepurviews;
	}
	public void setRoleSysmodepurviews(List<Sysmodepurview> roleSysmodepurviews) {
		this.roleSysmodepurviews = roleSysmodepurviews;
	}
	public List<roleSetInfo> getRolePosInfos() {
		return rolePosInfos;
	}
	public void setRolePosInfos(List<roleSetInfo> rolePosInfos) {
		this.rolePosInfos = rolePosInfos;
	}
	
}
