package com.amani.action.BaseInfoControl;

import java.math.BigDecimal;
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

import com.amani.model.Dcardapponline;
import com.amani.model.DcardapponlineId;
import com.amani.model.Dmpackageinfo;
import com.amani.model.DmpackageinfoId;
import com.amani.model.MpackageinfoId;

import com.amani.model.Mpackageinfo;
import com.amani.service.BaseInfoControl.BC010Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc010")
public class BC010Action extends AMN_ModuleAction{
	@Autowired
	private BC010Service bc010Service;
	private String strJsonParam;	
	private String strCurPackageNo;
    private List<Mpackageinfo> lsMpackageinfo;
    private List<Dmpackageinfo> lsDmpackageinfo;
    private Mpackageinfo curMpackageinfo;
    private String strCurCompId;
    private String strDownCurCompId;
    private String strProjectNo;
    private BigDecimal projectPrice;
    private String strProjectName;
    public String getStrCurPackageNo() {
		return strCurPackageNo;
	}
	public void setStrCurPackageNo(String strCurPackageNo) {
		this.strCurPackageNo = strCurPackageNo;
	}
	public List<Mpackageinfo> getLsMpackageinfo() {
		return lsMpackageinfo;
	}
	public void setLsMpackageinfo(List<Mpackageinfo> lsMpackageinfo) {
		this.lsMpackageinfo = lsMpackageinfo;
	}
	public List<Dmpackageinfo> getLsDmpackageinfo() {
		return lsDmpackageinfo;
	}
	public void setLsDmpackageinfo(List<Dmpackageinfo> lsDmpackageinfo) {
		this.lsDmpackageinfo = lsDmpackageinfo;
	}
	public Mpackageinfo getCurMpackageinfo() {
		return curMpackageinfo;
	}
	public void setCurMpackageinfo(Mpackageinfo curMpackageinfo) {
		this.curMpackageinfo = curMpackageinfo;
	}
	public String getStrProjectNo() {
		return strProjectNo;
	}
	public void setStrProjectNo(String strProjectNo) {
		this.strProjectNo = strProjectNo;
	}
	public String getStrProjectName() {
		return strProjectName;
	}
	public void setStrProjectName(String strProjectName) {
		this.strProjectName = strProjectName;
	}
	
	
	@Action(value = "loadMpackageinfos",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadMpackageinfos()
	{
		this.lsMpackageinfo=this.bc010Service.loadMaster(strCurCompId);
		if(lsMpackageinfo!=null && lsMpackageinfo.size()>0)
		{
			for(int i=0;i<lsMpackageinfo.size();i++)
			{
				lsMpackageinfo.get(i).setBcompid(lsMpackageinfo.get(i).getId().getCompid());
				lsMpackageinfo.get(i).setBpackageno(lsMpackageinfo.get(i).getId().getPackageno());
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadCurMpackageinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMpackageinfo()
	{
		this.curMpackageinfo=this.bc010Service.loadCurMaster(strCurCompId,this.strCurPackageNo);
		this.lsDmpackageinfo=this.bc010Service.getDataTool().loadDmpackageinfo(strCurCompId,strCurPackageNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	
	@Action(value = "validatePackageno",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validatePackageno()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.bc010Service.validatePackage(strCurCompId,strCurPackageNo);
			if(flag==false)
			{
				this.strMessage="该套餐编号已经存在!";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	@Action(value = "downByCompid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String downByCompid()
	{
		try
		{
			this.strMessage="";
			boolean flag=this.bc010Service.postDownByCompid(strCurCompId,strDownCurCompId,strCurPackageNo);
			if(flag==false)
			{
				this.strMessage="下发失败!";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	
	
	@Action(value = "validateProjectNo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validateProjectNo()
	{
		try
		{
			this.strMessage="";
			StringBuffer strProjectName=new StringBuffer();
			StringBuffer Projectprice=new StringBuffer();
			boolean flag=this.bc010Service.validateProject(strCurCompId,strProjectNo,strProjectName,Projectprice);
			if(flag==false)
			{
				this.strMessage="该项目编号不存在!";
				this.strProjectName="";
				this.projectPrice=new BigDecimal(0);
			}
			else
			{
				this.strProjectName=strProjectName.toString();
				this.projectPrice=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(Projectprice.toString())));
			}
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
			if(this.bc010Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "bc010", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			Mpackageinfo obj=new Mpackageinfo();
			obj.setId(new MpackageinfoId(this.strCurCompId,this.strCurPackageNo));
			boolean flag=this.bc010Service.delete(obj);
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
			if(this.bc010Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "bc010", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.bc010Service.post(this.curMpackageinfo, this.lsDmpackageinfo);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			
			curMpackageinfo=null;
			lsDmpackageinfo=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	@JSON(serialize=false)
	public BC010Service getBc010Service() {
		return bc010Service;
	}
    @JSON(serialize=false)
	public void setBc010Service(BC010Service bc010Service) {
		this.bc010Service = bc010Service;
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
		this.curMpackageinfo.getId().setCompid(this.strCurCompId);
		this.curMpackageinfo.setUsedate(CommonTool.setDateMask(this.curMpackageinfo.getUsedate()));
		this.lsDmpackageinfo=this.bc010Service.getDataTool().loadDTOList(strJsonParam, Dmpackageinfo.class);
		if(lsDmpackageinfo!=null && lsDmpackageinfo.size()>0)
		{
			for(int i=0;i<lsDmpackageinfo.size();i++)
			{
				if(!CommonTool.FormatString(lsDmpackageinfo.get(i).getBpackageprono()).equals(""))
				{
					lsDmpackageinfo.get(i).setId(new DmpackageinfoId(curMpackageinfo.getId().getCompid(),curMpackageinfo.getId().getPackageno(),lsDmpackageinfo.get(i).getBpackageprono()));
				}
				else
				{
					lsDmpackageinfo.remove(i);
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
	public BigDecimal getProjectPrice() {
		return projectPrice;
	}
	public void setProjectPrice(BigDecimal projectPrice) {
		this.projectPrice = projectPrice;
	}
	public String getStrDownCurCompId() {
		return strDownCurCompId;
	}
	public void setStrDownCurCompId(String strDownCurCompId) {
		this.strDownCurCompId = strDownCurCompId;
	}
	
	
	
}
