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

import com.amani.model.Cardinfo;
import com.amani.model.Promotionsinfo;
import com.amani.model.PromotionsinfoId;

import com.amani.service.BaseInfoControl.BC011Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc011")
public class BC011Action extends AMN_ModuleAction{
	@Autowired
	private BC011Service bc011Service;
	private String strJsonParam;	
	private List<Promotionsinfo> lsPromotionsinfos;
	private Promotionsinfo  curPromotionsinfo;
	private Promotionsinfo newPromotionsInfo;
    private String strCurCompId;
    private String strCurCompName;
    private String strCurBillCompId;
    private String strCurBillId;
    private String strPromotionCompId;
    private int iCurItemType;
    private String strCurItemId;
    private String strCurItemName;
    private Cardinfo curCardinfo;
    private String strBandComps;
	public String getStrBandComps() {
		return strBandComps;
	}
	public void setStrBandComps(String strBandComps) {
		this.strBandComps = strBandComps;
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
	
	 public List<Promotionsinfo> getLsPromotionsinfos() {
			return lsPromotionsinfos;
		}
		public void setLsPromotionsinfos(List<Promotionsinfo> lsPromotionsinfos) {
			this.lsPromotionsinfos = lsPromotionsinfos;
		}
		public Promotionsinfo getCurPromotionsinfo() {
			return curPromotionsinfo;
		}
		public void setCurPromotionsinfo(Promotionsinfo curPromotionsinfo) {
			this.curPromotionsinfo = curPromotionsinfo;
		}
		public String getStrCurCompId() {
			return strCurCompId;
		}
		public void setStrCurCompId(String strCurCompId) {
			this.strCurCompId = strCurCompId;
		}
		public String getStrCurBillId() {
			return strCurBillId;
		}
		public void setStrCurBillId(String strCurBillId) {
			this.strCurBillId = strCurBillId;
		}
		@JSON(serialize=false)
		public BC011Service getBc011Service() {
			return bc011Service;
		}
	    @JSON(serialize=false)
		public void setBc011Service(BC011Service bc011Service) {
			this.bc011Service = bc011Service;
		}
		public Promotionsinfo getNewPromotionsInfo() {
			return newPromotionsInfo;
		}
		public void setNewPromotionsInfo(Promotionsinfo newPromotionsInfo) {
			this.newPromotionsInfo = newPromotionsInfo;
		}
		
		@Action(value = "loadPromotions",  results = { 
				 @Result(name = "load_success", type = "json"),
	     @Result(name = "load_failure", type = "json")	
		}) 
		public String loadPromotions()
		{
			
			this.lsPromotionsinfos=this.bc011Service.loadPromotionsinfos(this.strCurCompId);
			this.newPromotionsInfo=this.bc011Service.addPromotionsinfo(this.strCurCompId);
			return SystemFinal.LOAD_SUCCESS;
		}
		@Action(value = "loadPromotionsInfo",  results = { 
				 @Result(name = "load_success", type = "json"),
	    @Result(name = "load_failure", type = "json")	
		}) 
		public String loadPromotionsInfo()
		{
			this.curPromotionsinfo=this.bc011Service.loadPromotionsinfosByBillId(this.strCurCompId,this.strCurBillId,strPromotionCompId);
			return SystemFinal.LOAD_SUCCESS;
		}
		@Action(value = "post",  results = { 
				 @Result(name = "post_success", type = "json"),
	            @Result(name = "post_failure", type = "json")	
	         }) 
		@Override
		public String post()
		{
			this.strMessage="";
			if(this.bc011Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC011", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			//curPromotionsinfo.getId().setBillid(this.bc011Service.getDataTool().loadBillIdByRule(curPromotionsinfo.getId().getCompid(),"promotionsinfo", "billid", "SP007"));
			curPromotionsinfo.setStartdate(CommonTool.setDateMask(curPromotionsinfo.getStartdate()));
			curPromotionsinfo.setEnddate(CommonTool.setDateMask(curPromotionsinfo.getEnddate()));
			curPromotionsinfo.setPromotionsstate(1);
			boolean flag=this.bc011Service.post(this.curPromotionsinfo, null);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			
			return SystemFinal.POST_SUCCESS;
		}
		
		@Action(value = "delete",  results = { 
				 @Result(name = "delete_success", type = "json"),
	           @Result(name = "delete_failure", type = "json")	
	        }) 
		@Override
		public String delete()
		{
			this.strMessage="";
			if(this.bc011Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC011", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			this.curPromotionsinfo=new Promotionsinfo();
			this.curPromotionsinfo.setId(new PromotionsinfoId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.bc011Service.delete(curPromotionsinfo);
			curPromotionsinfo=null;
			if(flag==false)
			{
				this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
				return SystemFinal.DELETE_FAILURE;
			}
			this.newPromotionsInfo=this.bc011Service.addPromotionsinfo(this.strCurCompId);
			return SystemFinal.DELETE_SUCCESS;
		}
		
		
		@Action(value = "confirmCurRootInfo",  results = { 
				 @Result(name = "load_success", type = "json"),
	           @Result(name = "load_failure", type = "json")	
	        }) 
		public String confirmCurRootInfo()
		{
			this.strMessage="";
			if(this.bc011Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC011", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.LOAD_FAILURE;
			}
			boolean flag=this.bc011Service.postConfirmInfo(this.strCurCompId, this.strCurBillId);
			if(flag==false)
			{
				this.strMessage="审核失败";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		
		@Action(value = "copyPromotionsInfos",  results = { 
				 @Result(name = "load_success", type = "json"),
	           @Result(name = "load_failure", type = "json")	
	        }) 
		public String copyPromotionsInfos()
		{
			this.strMessage="";
			if(this.bc011Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC011", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.LOAD_FAILURE;
			}
			boolean flag=this.bc011Service.handCopyInfo(this.strCurCompId, this.strCurBillId,this.strBandComps);
			if(flag==false)
			{
				this.strMessage="拷贝失败";
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		
		
		
		@Action(value = "add",  results = { 
				 @Result(name = "add_success", type = "json"),
	           @Result(name = "add_failure", type = "json")	
	        }) 
		@Override
		public String add()
		{
			this.strMessage="";
			
			this.newPromotionsInfo=this.bc011Service.addPromotionsinfo(this.strCurCompId);			
			return SystemFinal.ADD_SUCCESS;
		}
		
		@Action(value = "validateCompId",  results = { 
				 @Result(name = "load_success", type = "json"),
	     @Result(name = "load_failure", type = "json")	
		}) 
		public String validateCompId()
		{
			StringBuffer vildatemsg=new StringBuffer();
			this.strCurCompName=this.bc011Service.getDataTool().loadCompName(this.strCurBillCompId,this.strCurCompId, 1,vildatemsg);
			this.strMessage=vildatemsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		@Action(value = "validateItemId",  results = { 
				 @Result(name = "load_success", type = "json"),
	     @Result(name = "load_failure", type = "json")	
		}) 
		public String validateItemId()
		{
			this.strMessage="";
			StringBuffer vildatemsg=new StringBuffer();
			if(CommonTool.FormatInteger(iCurItemType)==1)
			{
				this.strCurItemName=this.bc011Service.getDataTool().loadProjectName(this.strCurCompId, this.strCurItemId, vildatemsg);
				this.strMessage=vildatemsg.toString();
			}
			else if(CommonTool.FormatInteger(iCurItemType)==2 || CommonTool.FormatInteger(iCurItemType)==3)
			{
				this.strCurItemName=this.bc011Service.getDataTool().loadCardTypeName(this.strCurCompId, this.strCurItemId, vildatemsg);
				this.strMessage=vildatemsg.toString();
			}
			else if(CommonTool.FormatInteger(iCurItemType)==4)
			{
				this.curCardinfo=this.bc011Service.getDataTool().loadCardinfobyCardNo(strCurCompId, strCurItemId);
				if(this.curCardinfo==null || CommonTool.FormatString(curCardinfo.getId().getCardno()).equals(""))
				{
					this.strMessage="卡号不存在!";
					return SystemFinal.LOAD_SUCCESS;
				}
				if(this.curCardinfo.getCardstate()!=1)
				{
					this.strMessage="该卡不在未销售状态!";
					return SystemFinal.LOAD_SUCCESS;
				}
				else
				{
					this.strCurItemName=this.curCardinfo.getCardtypeName();
				}
			}
			else if(CommonTool.FormatInteger(iCurItemType)==5)
			{
				this.curCardinfo=this.bc011Service.getDataTool().loadCardinfobyCardNo(strCurCompId, strCurItemId);
				if(this.curCardinfo==null || CommonTool.FormatString(curCardinfo.getId().getCardno()).equals(""))
				{
					this.strMessage="卡号不存在!";
					return SystemFinal.LOAD_SUCCESS;
				}
				if(this.curCardinfo.getCardstate()!=4 && this.curCardinfo.getCardstate()!=5 )
				{
					this.strMessage="不在开卡状态!";
					return SystemFinal.LOAD_SUCCESS;
				}
				else
				{
					this.strCurItemName=this.curCardinfo.getCardtypeName();
				}
			}
			
			return SystemFinal.LOAD_SUCCESS;
		}
		
		public String getStrCurCompName() {
			return strCurCompName;
		}
		public void setStrCurCompName(String strCurCompName) {
			this.strCurCompName = strCurCompName;
		}
		public String getStrCurItemId() {
			return strCurItemId;
		}
		public void setStrCurItemId(String strCurItemId) {
			this.strCurItemId = strCurItemId;
		}
		public String getStrCurItemName() {
			return strCurItemName;
		}
		public void setStrCurItemName(String strCurItemName) {
			this.strCurItemName = strCurItemName;
		}
		public int getICurItemType() {
			return iCurItemType;
		}
		public void setICurItemType(int curItemType) {
			iCurItemType = curItemType;
		}
		public String getStrCurBillCompId() {
			return strCurBillCompId;
		}
		public void setStrCurBillCompId(String strCurBillCompId) {
			this.strCurBillCompId = strCurBillCompId;
		}
		public Cardinfo getCurCardinfo() {
			return curCardinfo;
		}
		public void setCurCardinfo(Cardinfo curCardinfo) {
			this.curCardinfo = curCardinfo;
		}
		public String getStrPromotionCompId() {
			return strPromotionCompId;
		}
		public void setStrPromotionCompId(String strPromotionCompId) {
			this.strPromotionCompId = strPromotionCompId;
		}
	
}
