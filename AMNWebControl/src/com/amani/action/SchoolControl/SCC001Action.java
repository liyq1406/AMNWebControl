package com.amani.action.SchoolControl;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.SchoolInfo;
import com.amani.service.SchoolControl.SCC001Service;
import com.amani.tools.SystemFinal;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 合作学校设定
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/scc001")
public class SCC001Action extends AMN_ModuleAction implements ModelDriven<SchoolInfo>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private SCC001Service scc001Service;
	private List<SchoolInfo> listSet;
	private SchoolInfo schoolInfo = new SchoolInfo();
	
	@Action(value="load", results={@Result(name="load_success", type="json")})
	public String load(){
		try{
			//HttpServletRequest request = ServletActionContext.getRequest();
			List<SchoolInfo> list= scc001Service.loadDataSet();
			listSet = list == null ? new ArrayList<SchoolInfo>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="query", results={@Result(name="load_success", type="json")})
	public String query(){
		try{
			List<SchoolInfo> list= scc001Service.queryDataSet(schoolInfo.getName());
			listSet = list == null ? new ArrayList<SchoolInfo>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="post", results={@Result(name="post_success", type="json")}) 
	@Override
	public String post() {
		try{
			sysStatus = scc001Service.save(schoolInfo);
		}catch(Exception ex){
			ex.printStackTrace();
			sysStatus = 0;
		}
		strMessage = sysStatus==0 ? SystemFinal.POST_FAILURE_MSG : SystemFinal.POST_SUCCESS_MSG;
		return SystemFinal.POST_SUCCESS;
	}
	
	@Override
	protected boolean beforePost() {
		return false;
	}
	@Override
	protected boolean afterPost() {
		return false;
	}
	@Override
	protected boolean postActive() {
		return false;
	}
	@Override
	protected boolean beforeDelete() {
		return false;
	}
	@Override
	protected boolean afterDelete() {
		return false;
	}
	@Override
	protected boolean deleteActive() {
		return false;
	}
	@Override
	protected boolean beforeLoad() {
		return false;
	}
	@Override
	protected boolean afterLoad() {
		return false;
	}
	@Override
	protected boolean loadActive() {
		return false;
	}
	@Override
	protected boolean beforeAdd() {
		return false;
	}
	@Override
	protected boolean afterAdd() {
		return false;
	}
	@Override
	protected boolean addActive() {
		return false;
	}
	
	@JSON(serialize=false)
	public SCC001Service getScc001Service() {
		return scc001Service;
	}
	@JSON(serialize=false)
	public void setScc001Service(SCC001Service scc001Service) {
		this.scc001Service = scc001Service;
	}

	public SchoolInfo getSchoolInfo() {
		return schoolInfo;
	}
	public void setSchoolInfo(SchoolInfo schoolInfo) {
		this.schoolInfo = schoolInfo;
	}

	public List<SchoolInfo> getListSet() {
		return listSet;
	}
	public void setListSet(List<SchoolInfo> listSet) {
		this.listSet = listSet;
	}
	
	@Override
	public String getStrMessage() {
		return super.getStrMessage();
	}
	@Override
	public void setStrMessage(String strMessage) {
		super.setStrMessage(strMessage);
	}
	
	@Override
	public int getSysStatus() {
		return super.getSysStatus();
	}
	@Override
	public void setSysStatus(int sysStatus) {
		super.setSysStatus(sysStatus);
	}

	public SchoolInfo getModel() {
		return schoolInfo;
	}
}
