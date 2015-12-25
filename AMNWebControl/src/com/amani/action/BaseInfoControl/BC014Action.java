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

import com.amani.model.Cardchangecostrate;
import com.amani.model.CardchangecostrateId;
import com.amani.model.Cardratetocostrate;
import com.amani.service.BaseInfoControl.BC014Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc014")
public class BC014Action extends AMN_ModuleAction{
	@Autowired
	private BC014Service bc014Service;
	private String strJsonParam;	
    private String strCurCompId;
    private String strCurProjecttypeid;
    private String strCurAccounttypeid;
    private String strDownCurCompId;
    private String strDownCurProjecttypeid;
    
    private List<Cardratetocostrate> lsRateMaster;
    
    private List<Cardchangecostrate> lsMaster;
	
    private String strPrjType;
    private String strFromDate;
    private String strToDate;
    private double costRate;
    private double changeRate;
	private String strBandComps;
	public String getStrBandComps() {
		return strBandComps;
	}

	public void setStrBandComps(String strBandComps) {
		this.strBandComps = strBandComps;
	}

	public String getStrPrjType() {
		return strPrjType;
	}

	public void setStrPrjType(String strPrjType) {
		this.strPrjType = strPrjType;
	}

	public String getStrFromDate() {
		return strFromDate;
	}

	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}

	public String getStrToDate() {
		return strToDate;
	}

	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	public double getCostRate() {
		return costRate;
	}

	public void setCostRate(double costRate) {
		this.costRate = costRate;
	}

	public double getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(double changeRate) {
		this.changeRate = changeRate;
	}

	@Action(value = "loadRateMaster",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadRateMaster()
	{
		this.lsRateMaster=this.bc014Service.loadRateCostRate(strCurCompId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadCurProjectRateinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurProjectRateinfo()
	{
		this.lsMaster=this.bc014Service.loadMaster(strCurCompId,this.strCurProjecttypeid,strCurAccounttypeid);
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
			if(this.bc014Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC014", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.bc014Service.postCurMaster(this.lsMaster);
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
	
	@Action(value = "downLoadByPrjType",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String downLoadByPrjType()
	{
		try
		{
			this.strMessage="";
			if(this.bc014Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC014", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc014Service.downLoadByPrjType(this.strCurCompId,this.strCurProjecttypeid,this.strDownCurProjecttypeid,strCurAccounttypeid);
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
			if(this.bc014Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC014", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc014Service.postDownLoadAllPrjType(this.strCurCompId,this.strCurProjecttypeid,strCurAccounttypeid);
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
	
	
	@Action(value = "handCostrate",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
     }) 
	public String handCostrate()
	{
		try
		{
			this.strMessage="";
			if(this.bc014Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC014", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc014Service.updateChangeRate(strCurCompId, strPrjType, this.strFromDate, this.strToDate, this.costRate, this.changeRate);
			if(flag==false)
			{
				this.strMessage="更新失败,请重新查询!";
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
			if(this.bc014Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC014", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc014Service.downLoadByCompNo(this.strCurCompId,this.strDownCurCompId);
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
	
	
	@Action(value = "copyPromotionsInfos",  results = { 
			 @Result(name = "load_success", type = "json"),
          @Result(name = "load_failure", type = "json")	
       }) 
	public String copyPromotionsInfos()
	{
		this.strMessage="";
		if(this.bc014Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC014", SystemFinal.UR_SPECIAL_CHECK)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.LOAD_FAILURE;
		}
		boolean flag=this.bc014Service.handCopyInfo(this.strCurCompId,this.strBandComps);
		if(flag==false)
		{
			this.strMessage="拷贝失败";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	@JSON(serialize=false)
	public BC014Service getBc014Service() {
		return bc014Service;
	}
    @JSON(serialize=false)
	public void setBc014Service(BC014Service bc014Service) {
		this.bc014Service = bc014Service;
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
		this.lsMaster=this.bc014Service.getDataTool().loadDTOList(strJsonParam,Cardchangecostrate.class);
		if(this.lsMaster!=null && this.lsMaster.size()>0)
		{
			for(int i=0;i<lsMaster.size();i++)
			{
				this.lsMaster.get(i).setId(new CardchangecostrateId(this.strCurCompId,this.strCurProjecttypeid,this.lsMaster.get(i).getBcardtypeno(),this.lsMaster.get(i).getBacounttypeno()));
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




	public List<Cardchangecostrate> getLsMaster() {
		return lsMaster;
	}




	public void setLsMaster(List<Cardchangecostrate> lsMaster) {
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




	public List<Cardratetocostrate> getLsRateMaster() {
		return lsRateMaster;
	}




	public void setLsRateMaster(List<Cardratetocostrate> lsRateMaster) {
		this.lsRateMaster = lsRateMaster;
	}
	
	
	
	
}
