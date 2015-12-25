package com.amani.action.BaseInfoControl;

import java.util.List;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;

import com.amani.model.Cardcostgoodsrate;
import com.amani.model.CardcostgoodsrateId;
import com.amani.model.Gooddiscount;
import com.amani.model.GooddiscountId;
import com.amani.service.BaseInfoControl.BC015Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc015")
public class BC015Action extends AMN_ModuleAction{
	@Autowired
	private BC015Service bc015Service;
	private String strJsonParam;	
    private String strCurCompId;
    private String strCurProjecttypeid;
    private String strCurAccounttypeid;
    private String strDownCurCompId;
    private String strDownCurProjecttypeid;
    private List<Gooddiscount> lsGood;
    
    public List<Gooddiscount> getLsGood() {
		return lsGood;
	}

	public void setLsGood(List<Gooddiscount> lsGood) {
		this.lsGood = lsGood;
	}

	private List<Cardcostgoodsrate> lsMaster;
	
	
	
	@Action(value = "loadCurProjectRateinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurProjectRateinfo()
	{
		this.lsMaster=this.bc015Service.loadMaster(strCurCompId,this.strCurProjecttypeid,strCurAccounttypeid);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadGooddisInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadGooddisInfo()
	{
		lsGood=bc015Service.loadGooddisInfo(strCurCompId);
		return SystemFinal.LOAD_SUCCESS; 
	}
	
	@Action(value = "postGoodIsCard",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String postGoodIsCard()
	{
		this.strMessage="";
		this.lsGood=this.bc015Service.getDataTool().loadDTOList(strJsonParam,Gooddiscount.class);
		if(lsGood!=null && lsGood.size()>0)
		{
			for(int i=0;i<lsGood.size();i++)
			{
				lsGood.get(i).setId(new GooddiscountId(strCurCompId,lsGood.get(i).getBprojecttypeid()));
			}
		}
		if(this.bc015Service.postGoodIsCard(lsGood)==false)
		{
			this.strMessage=SystemFinal.POST_FAILURE_MSG;
		}
		return SystemFinal.POST_SUCCESS;
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
			if(this.bc015Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC015", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.bc015Service.postCurMaster(this.lsMaster);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			
			lsMaster=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "copyGoodIsCard",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String copyGoodIsCard()
	{
		this.strMessage="";
		if(bc015Service.copyGoodIsCard(strCurCompId)==false)
		{
			this.strMessage="下发失败";
		}
		return SystemFinal.LOAD_SUCCESS;  
	}
	
	@Action(value = "downLoadByPrjType",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String downLoadByPrjType()
	{
		try
		{
			this.strMessage="";
			if(this.bc015Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC015", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc015Service.downLoadByPrjType(this.strCurCompId,this.strCurProjecttypeid,this.strDownCurProjecttypeid,strCurAccounttypeid);
			if(flag==false)
			{
				this.strMessage="下发失败";
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
	
	@Action(value = "downLoadAllPrjType",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
      }) 
	public String downLoadAllPrjType()
	{
		try
		{
			this.strMessage="";
			if(this.bc015Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC015", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc015Service.postDownLoadAllPrjType(this.strCurCompId,this.strCurProjecttypeid,strCurAccounttypeid);
			if(flag==false)
			{
				this.strMessage="下发失败";
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
	
	
	@Action(value = "downLoadByCompNo",  results = { 
			 @Result(name = "post_success", type = "json"),
         @Result(name = "post_failure", type = "json")	
      }) 
	public String downLoadByCompNo()
	{
		try
		{
			this.strMessage="";
			if(this.bc015Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC015", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc015Service.downLoadByCompNo(this.strCurCompId,this.strDownCurCompId);
			if(flag==false)
			{
				this.strMessage="下发失败";
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
	@JSON(serialize=false)
	public BC015Service getBc015Service() {
		return bc015Service;
	}
    @JSON(serialize=false)
	public void setBc015Service(BC015Service bc015Service) {
		this.bc015Service = bc015Service;
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




	@Override
	protected boolean beforePost() {
		this.lsMaster=this.bc015Service.getDataTool().loadDTOList(strJsonParam,Cardcostgoodsrate.class);
		if(this.lsMaster!=null && this.lsMaster.size()>0)
		{
			for(int i=0;i<lsMaster.size();i++)
			{
				this.lsMaster.get(i).setId(new CardcostgoodsrateId(this.strCurCompId,this.strCurProjecttypeid,this.lsMaster.get(i).getBcardtypeno(),this.lsMaster.get(i).getBacounttypeno()));
				if(CommonTool.FormatString(this.lsMaster.get(i).getStartdate()).length()>10)
				{
					this.lsMaster.get(i).setStartdate(CommonTool.setDateMask(this.lsMaster.get(i).getStartdate().substring(0, 10).replaceAll("-", "")));
				}
				else
				{
					this.lsMaster.get(i).setStartdate(CommonTool.setDateMask(this.lsMaster.get(i).getStartdate()));
				}
				if(CommonTool.FormatString(this.lsMaster.get(i).getEnddate()).length()>10)
				{
					this.lsMaster.get(i).setEnddate(CommonTool.setDateMask(this.lsMaster.get(i).getEnddate().substring(0, 10).replaceAll("-", "")));
				}
				else
				{
					this.lsMaster.get(i).setEnddate(CommonTool.setDateMask(this.lsMaster.get(i).getEnddate()));
				}
			}
		}
		return true;
	}




	public String getStrCurProjecttypeid() {
		return strCurProjecttypeid;
	}




	public void setStrCurProjecttypeid(String strCurProjecttypeid) {
		this.strCurProjecttypeid = strCurProjecttypeid;
	}




	public List<Cardcostgoodsrate> getLsMaster() {
		return lsMaster;
	}




	public void setLsMaster(List<Cardcostgoodsrate> lsMaster) {
		this.lsMaster = lsMaster;
	}




	public String getStrDownCurCompId() {
		return strDownCurCompId;
	}




	public void setStrDownCurCompId(String strDownCurCompId) {
		this.strDownCurCompId = strDownCurCompId;
	}




	public String getStrDownCurProjecttypeid() {
		return strDownCurProjecttypeid;
	}




	public void setStrDownCurProjecttypeid(String strDownCurProjecttypeid) {
		this.strDownCurProjecttypeid = strDownCurProjecttypeid;
	}




	public String getStrCurAccounttypeid() {
		return strCurAccounttypeid;
	}




	public void setStrCurAccounttypeid(String strCurAccounttypeid) {
		this.strCurAccounttypeid = strCurAccounttypeid;
	}
	
	
	
	
}
