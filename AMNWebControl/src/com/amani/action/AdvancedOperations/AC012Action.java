package com.amani.action.AdvancedOperations;


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
import com.amani.model.Storeflowinfo;
import com.amani.model.StoreflowinfoId;


import com.amani.service.AdvancedOperations.AC012Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac012")
public class AC012Action extends AMN_ModuleAction{
	@Autowired
	private AC012Service ac012Service;
	private String strJsonParam;	
	private List<Storeflowinfo> lsStoreflowinfos;
	private Storeflowinfo  curStoreflowinfo;
	private Storeflowinfo newStoreflowinfo;
    private String strCurCompId;
    private String strCurCompName;
    private String strCurBillCompId;
    private String strCurBillId;
    private int iCurItemType;
    private String strCurItemId;
    private String strCurItemName;
    private Cardinfo curCardinfo;
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
	
		
		public List<Storeflowinfo> getLsStoreflowinfos() {
			return lsStoreflowinfos;
		}
		public void setLsStoreflowinfos(List<Storeflowinfo> lsStoreflowinfos) {
			this.lsStoreflowinfos = lsStoreflowinfos;
		}
		public Storeflowinfo getCurStoreflowinfo() {
			return curStoreflowinfo;
		}
		public void setCurStoreflowinfo(Storeflowinfo curStoreflowinfo) {
			this.curStoreflowinfo = curStoreflowinfo;
		}
		public Storeflowinfo getNewStoreflowinfo() {
			return newStoreflowinfo;
		}
		public void setNewStoreflowinfo(Storeflowinfo newStoreflowinfo) {
			this.newStoreflowinfo = newStoreflowinfo;
		}
		@Action(value = "loadappflow",  results = { 
				 @Result(name = "load_success", type = "json"),
	     @Result(name = "load_failure", type = "json")	
		}) 
		public String loadappflow()
		{
			
			this.lsStoreflowinfos=this.ac012Service.loadStoreflowinfos(this.strCurCompId);
			this.newStoreflowinfo=this.ac012Service.addStoreflowinfo(this.strCurCompId);
			return SystemFinal.LOAD_SUCCESS;
		}
		@Action(value = "loadStoreflowinfo",  results = { 
				 @Result(name = "load_success", type = "json"),
	    @Result(name = "load_failure", type = "json")	
		}) 
		public String loadStoreflowinfo()
		{
			this.curStoreflowinfo=this.ac012Service.loadStoreflowinfosByBillId(this.strCurCompId,this.strCurBillId);
			if(curStoreflowinfo==null  || curStoreflowinfo.getId()==null  || CommonTool.FormatString(curStoreflowinfo.getId().getBillid()).equals(""))
			{
				curStoreflowinfo=this.ac012Service.addStoreflowinfo(strCurCompId);
			}
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
			if(this.ac012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC012", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			//curStoreflowinfo.getId().setBillid(this.ac012Service.getDataTool().loadBillIdByRule(curStoreflowinfo.getId().getCompid(),"Storeflowinfo", "billid", "SP007"));
			curStoreflowinfo.setStartdate(CommonTool.setDateMask(curStoreflowinfo.getStartdate()));
			curStoreflowinfo.setEnddate(CommonTool.setDateMask(curStoreflowinfo.getEnddate()));
			curStoreflowinfo.setAppflowstate(0);
			boolean flag=this.ac012Service.post(this.curStoreflowinfo, null);
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
			if(this.ac012Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC012", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			this.curStoreflowinfo=new Storeflowinfo();
			this.curStoreflowinfo.setId(new StoreflowinfoId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.ac012Service.delete(curStoreflowinfo);
			curStoreflowinfo=null;
			if(flag==false)
			{
				this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
				return SystemFinal.DELETE_FAILURE;
			}
			this.newStoreflowinfo=this.ac012Service.addStoreflowinfo(this.strCurCompId);
			return SystemFinal.DELETE_SUCCESS;
		}
		
		
		
		
		@Action(value = "add",  results = { 
				 @Result(name = "add_success", type = "json"),
	           @Result(name = "add_failure", type = "json")	
	        }) 
		@Override
		public String add()
		{
			this.strMessage="";
			
			this.newStoreflowinfo=this.ac012Service.addStoreflowinfo(this.strCurCompId);			
			return SystemFinal.ADD_SUCCESS;
		}
		
		@Action(value = "validateCompId",  results = { 
				 @Result(name = "load_success", type = "json"),
	     @Result(name = "load_failure", type = "json")	
		}) 
		public String validateCompId()
		{
			StringBuffer vildatemsg=new StringBuffer();
			this.strCurCompName=this.ac012Service.getDataTool().loadCompName(this.strCurBillCompId,this.strCurCompId, 1,vildatemsg);
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
			if(CommonTool.FormatInteger(iCurItemType)==8)
			{
				this.strCurItemName=this.ac012Service.getDataTool().loadProjectName(this.strCurCompId, this.strCurItemId, vildatemsg);
				this.strMessage=vildatemsg.toString();
			}
			if(CommonTool.FormatInteger(iCurItemType)==9)
			{
				this.strCurItemName=this.ac012Service.getDataTool().loadGoodsName(this.strCurCompId, this.strCurItemId, vildatemsg);
				this.strMessage=vildatemsg.toString();
			}
			else 
			{
				this.curCardinfo=this.ac012Service.getDataTool().loadCardinfobyCardNo(strCurCompId, strCurItemId);
				if(this.curCardinfo==null || CommonTool.FormatString(curCardinfo.getId().getCardno()).equals(""))
				{
					this.strMessage="卡号不存在!";
					return SystemFinal.LOAD_SUCCESS;
				}
				if(CommonTool.FormatInteger(iCurItemType)==4 && this.curCardinfo.getCardstate()!=1)
				{
					this.strMessage="该卡不在未销售状态!";
					return SystemFinal.LOAD_SUCCESS;
				}
				else if(CommonTool.FormatInteger(iCurItemType)==2 && this.curCardinfo.getCardstate()!=9)
				{
					this.strMessage="该卡不在挂失状态!";
					return SystemFinal.LOAD_SUCCESS;
				}
				else if((CommonTool.FormatInteger(iCurItemType)==1  
					   || CommonTool.FormatInteger(iCurItemType)==3  
					   || CommonTool.FormatInteger(iCurItemType)==5  
					   || CommonTool.FormatInteger(iCurItemType)==6  
					   || CommonTool.FormatInteger(iCurItemType)==7  )
						&& this.curCardinfo.getCardstate()!=4)
				{
					this.strMessage="该卡不在正常使用状态!";
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
		@JSON(serialize=false)
		public AC012Service getAc012Service() {
			return ac012Service;
		}
		@JSON(serialize=false)
		public void setAc012Service(AC012Service ac012Service) {
			this.ac012Service = ac012Service;
		}
	
}
