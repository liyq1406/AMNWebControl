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
import com.amani.model.Commoninfo;
import com.amani.model.CommoninfoId;
import com.amani.service.BaseInfoControl.BC002Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc002")
public class BC002Action extends AMN_ModuleAction{
	@Autowired
	private BC002Service bc002Service;
	private String strJsonParam;
	private String curDivid;  //commoninfodivsecond 二级明细  commoninfodivthirth 三级明细
	private String strInfotype; 
	private String strInfoname;
	private String strParentcodekey;
	private String strParentcodevalue;
	private List<Commoninfo> lsCommonData=new ArrayList();
	private List<Commoninfo> lsCommonDataByGroup=new ArrayList();
	public List<Commoninfo> getLsCommonDataByGroup() {
		return lsCommonDataByGroup;
	}

	public void setLsCommonDataByGroup(List<Commoninfo> lsCommonDataByGroup) {
		this.lsCommonDataByGroup = lsCommonDataByGroup;
	}

	public List<Commoninfo> getLsCommonData() {
		return lsCommonData;
	}

	public void setLsCommonData(List<Commoninfo> lsCommonData) {
		this.lsCommonData = lsCommonData;
	}

	public String getCurDivid() {
		return curDivid;
	}

	public void setCurDivid(String curDivid) {
		this.curDivid = curDivid;
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
		return true;
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
		return true;
	}

	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return false;
	}

	@Action(value = "delete",  results = { 
			 @Result(name = "delete_success", type = "json"),
            @Result(name = "delete_failure", type = "json")	
         }) 
	@Override
	public String delete()
	{
		this.strMessage="";
		if(this.bc002Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC002", SystemFinal.UR_DELETE)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.DELETE_FAILURE;
		}
		List<Commoninfo>  lsCommoninfo=this.bc002Service.getDataTool().loadDTOList(strJsonParam,Commoninfo.class);
	
		if(lsCommoninfo!=null && lsCommoninfo.size()>0)
		{
			for(int i=0;i<lsCommoninfo.size();i++)
			{
				lsCommoninfo.get(i).setId(new CommoninfoId( CommonTool.FormatString(lsCommoninfo.get(i).getBinfotype()),
															CommonTool.FormatString(lsCommoninfo.get(i).getBparentcodekey()),
															CommonTool.FormatString(lsCommoninfo.get(i).getBcodekey())));
			}
		}
		if(this.bc002Service.deleteCommonInfo(lsCommoninfo,this.curDivid,this.strInfotype,this.strInfoname,this.strParentcodekey,this.strParentcodevalue)==false)
		{
			this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
			return SystemFinal.DELETE_FAILURE;
		}
		if(this.curDivid.equals("commoninfodivthirth"))//commoninfodivsecond 二级明细  commoninfodivthirth 三级明细
		{
			this.lsCommonData=this.bc002Service.loadCommoninfos();
			if(lsCommonData!=null && lsCommonData.size()>0)
			{
				for(int i=0;i<lsCommonData.size();i++)
				{
					lsCommonData.get(i).setBcodekey(lsCommonData.get(i).getId().getCodekey());
					lsCommonData.get(i).setBinfotype(lsCommonData.get(i).getId().getInfotype());
					lsCommonData.get(i).setBparentcodekey(lsCommonData.get(i).getId().getParentcodekey());
				}
			}
		}
		else
		{
			this.lsCommonDataByGroup=this.bc002Service.loadCommoninfosParentByGroup();
		}
		lsCommoninfo=null;
		return SystemFinal.DELETE_SUCCESS;
	}
	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean postActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Action(value = "load", results = { @Result(name = "load_success", location = "/BaseInfoControl/BC002/index.jsp"),
			@Result(name = "load_failure", location = "/BaseInfoControl/BC002/index.jsp")}) 
	@Override
	public String load()
	{
		return SystemFinal.LOAD_SUCCESS;
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
			if(this.bc002Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC002", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			List<Commoninfo>  lsCommoninfo=this.bc002Service.getDataTool().loadDTOList(strJsonParam,Commoninfo.class);
		
			if(lsCommoninfo!=null && lsCommoninfo.size()>0)
			{
				for(int i=0;i<lsCommoninfo.size();i++)
				{
					lsCommoninfo.get(i).setId(new CommoninfoId( CommonTool.FormatString(lsCommoninfo.get(i).getBinfotype()),
																CommonTool.FormatString(lsCommoninfo.get(i).getBparentcodekey()),
																CommonTool.FormatString(lsCommoninfo.get(i).getBcodekey())));
				}
			}
			if(this.bc002Service.postCommonInfo(lsCommoninfo,this.curDivid,this.strInfotype,this.strInfoname,this.strParentcodekey,this.strParentcodevalue)==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			if(this.curDivid.equals("commoninfodivthirth"))//commoninfodivsecond 二级明细  commoninfodivthirth 三级明细
			{
				this.lsCommonData=this.bc002Service.loadCommoninfos();
				if(lsCommonData!=null && lsCommonData.size()>0)
				{
					for(int i=0;i<lsCommonData.size();i++)
					{
						lsCommonData.get(i).setBcodekey(lsCommonData.get(i).getId().getCodekey());
						lsCommonData.get(i).setBinfotype(lsCommonData.get(i).getId().getInfotype());
						lsCommonData.get(i).setBparentcodekey(lsCommonData.get(i).getId().getParentcodekey());
					}
				}
			}
			else
			{
				this.lsCommonDataByGroup=this.bc002Service.loadCommoninfosParentByGroup();
			}
			lsCommoninfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	


	@JSON(serialize=false)
	public BC002Service getBc002Service() {
		return bc002Service;
	}
	@JSON(serialize=false)
	public void setBc002Service(BC002Service bc002Service) {
		this.bc002Service = bc002Service;
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

	public String getStrInfotype() {
		return strInfotype;
	}

	public void setStrInfotype(String strInfotype) {
		this.strInfotype = strInfotype;
	}

	public String getStrInfoname() {
		return strInfoname;
	}

	public void setStrInfoname(String strInfoname) {
		this.strInfoname = strInfoname;
	}

	public String getStrParentcodekey() {
		return strParentcodekey;
	}

	public void setStrParentcodekey(String strParentcodekey) {
		this.strParentcodekey = strParentcodekey;
	}

	public String getStrParentcodevalue() {
		return strParentcodevalue;
	}

	public void setStrParentcodevalue(String strParentcodevalue) {
		this.strParentcodevalue = strParentcodevalue;
	}
}
