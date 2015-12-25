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
import com.amani.bean.ReserveBean;

import com.amani.model.Mconsumeinfo;

import com.amani.service.AdvancedOperations.AC013Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac013")
public class AC013Action extends AMN_ModuleAction{
	@Autowired
	private AC013Service ac013Service;
	private String strJsonParam;	
	private List<Mconsumeinfo> lsMconsumeinfo;
	private Mconsumeinfo  curMconsumeinfo;
	private List<ReserveBean> lsDetialInfo;
    private String strCurCompId;
    private String strCurCompName;
    private String strCurBillCompId;
    private String strCurBillId;

    private String strSearchDate;
    private String reserveStaffinfo;
    private int iSearchState;
    private String strMemberNo;
    private String strStaffNo;
	public String getStrStaffNo() {
		return strStaffNo;
	}
	public void setStrStaffNo(String strStaffNo) {
		this.strStaffNo = strStaffNo;
	}
	public String getStrMemberNo() {
		return strMemberNo;
	}
	public void setStrMemberNo(String strMemberNo) {
		this.strMemberNo = strMemberNo;
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
		public String getStrCurBillId() {
			return strCurBillId;
		}
		public void setStrCurBillId(String strCurBillId) {
			this.strCurBillId = strCurBillId;
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
		
		@Action(value = "delete",  results = { 
				 @Result(name = "delete_success", type = "json"),
	           @Result(name = "delete_failure", type = "json")	
	        }) 
		@Override
		public String delete()
		{
			return SystemFinal.DELETE_SUCCESS;
		}
		
		@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
				 @Result( type="json", name="load_failure")})
		public String loadDataSet() {
			if(strSearchDate.equals(""))
				strSearchDate=CommonTool.getCurrDate();
			this.lsMconsumeinfo=this.ac013Service.loadMasterInfo(strCurCompId, CommonTool.setDateMask(strSearchDate), this.iSearchState,strMemberNo,strStaffNo);
			return SystemFinal.LOAD_SUCCESS;
		}
		
		
		@Action( value="loadDetialInfo", results={ @Result( type="json", name="load_success"),
				 @Result( type="json", name="load_failure")})
		public String loadDetialInfo() {
			this.lsDetialInfo=this.ac013Service.loadDetialInfo(this.strCurCompId, this.strCurBillId);
			return SystemFinal.LOAD_SUCCESS;
		}
		
		@Action(value = "add",  results = { 
				 @Result(name = "add_success", type = "json"),
	           @Result(name = "add_failure", type = "json")	
	        }) 
		@Override
		public String add()
		{			
			return SystemFinal.ADD_SUCCESS;
		}
		
		
		@Action(value = "postDetial",  results = { 
				 @Result(name = "post_success", type = "json"),
	            @Result(name = "post_failure", type = "json")	
	         })
		public String postDetial()
		{
		
			this.strMessage="";
			if(!CommonTool.FormatString(strJsonParam).equals(""))
			{
				this.lsDetialInfo=this.ac013Service.getDataTool().loadDTOList(strJsonParam, ReserveBean.class);
			}
			boolean flag=this.ac013Service.postReserveInfo(this.strCurCompId, this.strCurBillId, this.reserveStaffinfo, this.lsDetialInfo);
			if(flag==false)
			{
				
				this.strMessage="标识失败";
			}
			return SystemFinal.POST_SUCCESS;
		}
	
		public String getStrCurCompName() {
			return strCurCompName;
		}
		public void setStrCurCompName(String strCurCompName) {
			this.strCurCompName = strCurCompName;
		}
		
		public String getStrCurBillCompId() {
			return strCurBillCompId;
		}
		public void setStrCurBillCompId(String strCurBillCompId) {
			this.strCurBillCompId = strCurBillCompId;
		}
		@JSON(serialize=false)
		public AC013Service getAc013Service() {
			return ac013Service;
		}
		@JSON(serialize=false)
		public void setAc013Service(AC013Service ac013Service) {
			this.ac013Service = ac013Service;
		}
		public List<Mconsumeinfo> getLsMconsumeinfo() {
			return lsMconsumeinfo;
		}
		public void setLsMconsumeinfo(List<Mconsumeinfo> lsMconsumeinfo) {
			this.lsMconsumeinfo = lsMconsumeinfo;
		}
		public Mconsumeinfo getCurMconsumeinfo() {
			return curMconsumeinfo;
		}
		public void setCurMconsumeinfo(Mconsumeinfo curMconsumeinfo) {
			this.curMconsumeinfo = curMconsumeinfo;
		}
		public String getStrSearchDate() {
			return strSearchDate;
		}
		public void setStrSearchDate(String strSearchDate) {
			this.strSearchDate = strSearchDate;
		}
		public int getISearchState() {
			return iSearchState;
		}
		public void setISearchState(int searchState) {
			iSearchState = searchState;
		}
		public List<ReserveBean> getLsDetialInfo() {
			return lsDetialInfo;
		}
		public void setLsDetialInfo(List<ReserveBean> lsDetialInfo) {
			this.lsDetialInfo = lsDetialInfo;
		}
		public String getReserveStaffinfo() {
			return reserveStaffinfo;
		}
		public void setReserveStaffinfo(String reserveStaffinfo) {
			this.reserveStaffinfo = reserveStaffinfo;
		}
	
}
