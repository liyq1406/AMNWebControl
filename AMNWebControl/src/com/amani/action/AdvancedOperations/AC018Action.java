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

import com.amani.model.Compschedulinfo;
import com.amani.model.Staffinfo;


import com.amani.service.AdvancedOperations.AC018Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac018")
public class AC018Action extends AMN_ModuleAction{
	@Autowired
	private AC018Service ac018Service;
	private String strJsonParam;	
    private String strCurCompId;
    private	String strCurMonth;
    private String strCurDate;
    private String strFromDate;
    private String strToDate;
    private String strCurCompName;
    private String strCurSchedulno;
    private String strCurInid;
    private String strEmpId;
    public List<Compschedulinfo> lsCompschedulinfo;
    public List<Compschedulinfo> lsDCompschedulinfo;
	private List<Staffinfo> lsStaffinfo;
	public List<Compschedulinfo> getLsCompschedulinfo() {
		return lsCompschedulinfo;
	}
	public void setLsCompschedulinfo(List<Compschedulinfo> lsCompschedulinfo) {
		this.lsCompschedulinfo = lsCompschedulinfo;
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
	
		public String getStrCurCompId() {
			return strCurCompId;
		}
		public void setStrCurCompId(String strCurCompId) {
			this.strCurCompId = strCurCompId;
		}
		
		@Action(value = "loadStoreSchedulInfo",  results = { 
				 @Result(name = "load_success", type = "json"),
				 @Result(name = "load_failure", type = "json")	
		}) 
		public String loadStoreSchedulInfo()
		{
			this.lsCompschedulinfo=this.ac018Service.loadCurDataSet(strCurCompId, strCurMonth,strEmpId);
			return SystemFinal.LOAD_SUCCESS;
		}
		
		@Action(value = "loadSchedulDetialinfo",  results = { 
				 @Result(name = "load_success", type = "json"),
				 @Result(name = "load_failure", type = "json")	
		}) 
		public String loadSchedulDetialinfo()
		{
			this.lsDCompschedulinfo=this.ac018Service.loadCurDetialDataSetC(strCurCompId, strCurDate,strEmpId);
			return SystemFinal.LOAD_SUCCESS;
		}
		
		
		@Action(value = "loadStaffSchedul",  results = { 
				 @Result(name = "load_success", type = "json"),
				 @Result(name = "load_failure", type = "json")	
		}) 
		public String loadStaffSchedul()
		{
			this.lsDCompschedulinfo=this.ac018Service.loadCurDetialDataSet(strCurCompId, this.strCurDate, this.strCurSchedulno,strEmpId);
			return SystemFinal.LOAD_SUCCESS;
		}
		
		@Action(value = "post",  results = { 
				 @Result(name = "post_success", type = "json"),
	            @Result(name = "post_failure", type = "json")	
	         }) 
		@Override
		public String post()
		{
			
			return SystemFinal.POST_SUCCESS;
		}
		
		@Action(value = "postStaffSchedul",  results = { 
				 @Result(name = "post_success", type = "json"),
	            @Result(name = "post_failure", type = "json")	
	    }) 
		public String postStaffSchedul()
		{
			strMessage="";
			if(this.ac018Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC018", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(!CommonTool.FormatString(strJsonParam).equals("") && strJsonParam.indexOf("[")>-1)
			{
				this.lsDCompschedulinfo=this.ac018Service.getDataTool().loadDTOList(strJsonParam,Compschedulinfo.class);
			}
			this.strMessage="";
			boolean flag=this.ac018Service.postSchedulInfo(strCurCompId, this.strCurDate, this.strCurSchedulno, lsDCompschedulinfo);
			if(flag==false)
			{
				this.strMessage="保存失败,请核实后重新操作";
			}
			return SystemFinal.POST_SUCCESS;
		}
		
		
		@Action(value = "downStaffschedule",  results = { 
				 @Result(name = "post_success", type = "json"),
	            @Result(name = "post_failure", type = "json")	
	    }) 
		public String downStaffschedule()
		{
			strMessage="";
			if(this.ac018Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC018", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			strFromDate=CommonTool.setDateMask(strFromDate);
		    strToDate=CommonTool.setDateMask(strToDate);
		    if(Integer.parseInt(strToDate)<Integer.parseInt(strFromDate))
		    {
		    	this.strMessage="输入起始日期格式不正确";
		    	return SystemFinal.POST_SUCCESS;
		    }
		    int subDate=CommonTool.dateSubDate(CommonTool.setDateMask(strFromDate),CommonTool.setDateMask(strToDate));
			if(subDate>31)
			{
				strMessage="输入日期范围不能超过一个月";
				return SystemFinal.POST_SUCCESS;
			}
			boolean flag=this.ac018Service.downStaffSchelInfo(strCurCompId, strCurDate, strFromDate, strToDate);
			if(flag==false)
			{
				strMessage="下发失败,请核实后重新操作!";
				return SystemFinal.POST_SUCCESS;
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
			
			return SystemFinal.DELETE_SUCCESS;
		}
		
		@Action(value = "deleteStaffSchedul",  results = { 
				 	@Result(name = "delete_success", type = "json"),
				 	@Result(name = "delete_failure", type = "json")	
	        }) 
		public String deleteStaffSchedul()
		{
			try
			{
				this.strMessage="";
				if(this.ac018Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "AC018", SystemFinal.UR_DELETE)==false)
				{
					this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
					return SystemFinal.DELETE_FAILURE;
				}
				boolean flag=this.ac018Service.deleteStaffScheduling(strCurCompId, this.strCurDate, this.strCurSchedulno,this.strCurInid);
				if(flag==false)
				{
					this.strMessage="删除失败,请核实后重新操作";
				}
				return SystemFinal.DELETE_SUCCESS;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return SystemFinal.DELETE_FAILURE;
			}
		}
		
		
		
		@Action(value = "add",  results = { 
				 @Result(name = "add_success", type = "json"),
	           @Result(name = "add_failure", type = "json")	
	        }) 
		@Override
		public String add()
		{
			this.strMessage="";
				
			return SystemFinal.ADD_SUCCESS;
		}
		
		@Action(value = "loadSchedulStaff",  results = { 
				 @Result(name = "load_success", type = "json"),
				 @Result(name = "load_failure", type = "json")	
		}) 
		public String loadSchedulStaff()
		{
			try
			{
				this.lsStaffinfo=this.ac018Service.getDataTool().loadEmpsByCompId(strCurCompId, 2);
				return SystemFinal.LOAD_SUCCESS;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return SystemFinal.LOAD_FAILURE;
			}
		}
		
		public String getStrCurCompName() {
			return strCurCompName;
		}
		public void setStrCurCompName(String strCurCompName) {
			this.strCurCompName = strCurCompName;
		}
	
		
		@JSON(serialize=false)
		public AC018Service getAc018Service() {
			return ac018Service;
		}
		@JSON(serialize=false)
		public void setAc018Service(AC018Service ac018Service) {
			this.ac018Service = ac018Service;
		}
		public String getStrCurMonth() {
			return strCurMonth;
		}
		public void setStrCurMonth(String strCurMonth) {
			this.strCurMonth = strCurMonth;
		}
		public String getStrCurDate() {
			return strCurDate;
		}
		public void setStrCurDate(String strCurDate) {
			this.strCurDate = strCurDate;
		}
		public List<Compschedulinfo> getLsDCompschedulinfo() {
			return lsDCompschedulinfo;
		}
		public void setLsDCompschedulinfo(List<Compschedulinfo> lsDCompschedulinfo) {
			this.lsDCompschedulinfo = lsDCompschedulinfo;
		}
		public List<Staffinfo> getLsStaffinfo() {
			return lsStaffinfo;
		}
		public void setLsStaffinfo(List<Staffinfo> lsStaffinfo) {
			this.lsStaffinfo = lsStaffinfo;
		}
		public String getStrCurSchedulno() {
			return strCurSchedulno;
		}
		public void setStrCurSchedulno(String strCurSchedulno) {
			this.strCurSchedulno = strCurSchedulno;
		}
		public String getStrCurInid() {
			return strCurInid;
		}
		public void setStrCurInid(String strCurInid) {
			this.strCurInid = strCurInid;
		}
		public String getStrEmpId() {
			return strEmpId;
		}
		public void setStrEmpId(String strEmpId) {
			this.strEmpId = strEmpId;
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
	
}
