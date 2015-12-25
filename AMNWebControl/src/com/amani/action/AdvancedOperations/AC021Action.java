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
import com.amani.model.StaffHairdesignerinfo;
import com.amani.service.AdvancedOperations.AC021Service;
import com.amani.tools.SystemFinal;

@SuppressWarnings("serial")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac021")
public class AC021Action extends AMN_ModuleAction{
	
	@Autowired
	private AC021Service ac021Service;
	private String strStaffNo;
	private String strStaffName;
	private String auditState;
	private String strId;
	private boolean res;
	private List<StaffHairdesignerinfo> images;
	
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		this.images=this.ac021Service.loadImages(strStaffNo,auditState);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="updateSelect", results={ @Result( type="json", name="load_success"),
			@Result( type="json", name="load_success")})
	public String updateSelect() {
		
		 res = this.ac021Service.updateSelect(strId,auditState);
		return SystemFinal.LOAD_SUCCESS;
	}

	@Override
	protected boolean addActive() {
		return false;
	}

	@Override
	protected boolean afterAdd() {
		return false;
	}

	@Override
	protected boolean afterDelete() {
		return false;
	}

	@Override
	protected boolean afterLoad() {
		return false;
	}

	@Override
	protected boolean afterPost() {
		
		return true;
	}

	@Override
	protected boolean beforeAdd() {
		return false;
	}

	@Override
	protected boolean beforeDelete() {
		return false;
	}

	@Override
	protected boolean beforeLoad() {
		return true;
	}

	@Override
	protected boolean beforePost() {
		
		return true;
	}

	@Override
	protected boolean deleteActive() {
		return true;
	}
	
	@Override
	protected boolean loadActive() {
		return true;
	}
    
	@Override
	protected boolean postActive() {
	
		return true;
	}
	
	@JSON(serialize=false)
	public AC021Service getac021Service() {
		return ac021Service;
	}
	@JSON(serialize=false)
	public void setac021Service(AC021Service ac021Service) {
		this.ac021Service = ac021Service;
	}

	@Override
	public String getStrMessage() {
		return super.getStrMessage();
	}

	@Override
	public void setStrMessage(String strMessage) {
		super.setStrMessage(strMessage);
	}

	public String getStrStaffNo() {
		return strStaffNo;
	}

	public void setStrStaffNo(String strStaffNo) {
		this.strStaffNo = strStaffNo;
	}

	public String getStrStaffName() {
		return strStaffName;
	}

	public void setStrStaffName(String strStaffName) {
		this.strStaffName = strStaffName;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public List<StaffHairdesignerinfo> getImages() {
		return images;
	}

	public void setImages(List<StaffHairdesignerinfo> images) {
		this.images = images;
	}

	public String getStrId() {
		return strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}

	public boolean isRes() {
		return res;
	}

	public void setRes(boolean res) {
		this.res = res;
	}
}
