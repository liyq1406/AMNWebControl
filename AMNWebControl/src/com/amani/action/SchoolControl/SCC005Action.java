package com.amani.action.SchoolControl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.SchoolActinfo;
import com.amani.model.SchoolActivity;
import com.amani.service.SchoolControl.SCC005Service;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 涉外活动学分输入
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/scc005")
public class SCC005Action extends AMN_ModuleAction implements ModelDriven<SchoolActivity>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private SCC005Service scc005Service;
	private SchoolActivity bill;
	private List<SchoolActivity> billSet;
	private List<SchoolActivity> listSet;
	private List<SchoolActinfo> actSet;
	private SchoolActivity schoolActivity = new SchoolActivity();
	
	@Action(value="load", results={@Result(name="load_success", type="json")})
	public String load(){
		try{
			List<SchoolActivity> list= scc005Service.loadBillSet(schoolActivity.getSalecompid());
			billSet = list == null ? new ArrayList<SchoolActivity>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="query", results={@Result(name="load_success", type="json")})
	public String query(){
		try{
			String salecompid = schoolActivity.getSalecompid();
			String salebillid = schoolActivity.getSalebillid();
			List<SchoolActivity> list= scc005Service.loadDataSet(salecompid, salebillid);
			listSet = list == null ? new ArrayList<SchoolActivity>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="add", results={@Result(name="add_success", type="json")})
	public String add(){
		try{
			bill = scc005Service.addBillRecord(schoolActivity.getSalecompid());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.ADD_SUCCESS;
	}
	
	@Action(value="post", results={@Result(name="post_success", type="json")}) 
	@Override
	public String post(){
		try{
			String dataJson = schoolActivity.getRemark();
			if(StringUtils.isNotBlank(dataJson)){
				Gson gson = new Gson();
				listSet = gson.fromJson(dataJson, new TypeToken<List<SchoolActivity>>(){}.getType());
				sysStatus = scc005Service.save(listSet);
			}else{
				sysStatus = 0;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			sysStatus = 0;
		}
		strMessage = sysStatus==0 ? SystemFinal.POST_FAILURE_MSG : SystemFinal.POST_SUCCESS_MSG;
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action(value="loadAct", results={@Result(name="load_success", type="json")})
	public String loadAct(){
		try{
			List<SchoolActinfo> list= scc005Service.loadDataSet();
			actSet = list == null ? new ArrayList<SchoolActinfo>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="postAct", results={@Result(name="post_success", type="json")}) 
	public String postAct(){
		try{
			Gson gson = new Gson();
			String addJson = schoolActivity.getRemark();
			String updateJson = schoolActivity.getStaffname();
			if(StringUtils.isNotBlank(addJson)){
				actSet = gson.fromJson(addJson, new TypeToken<List<SchoolActinfo>>(){}.getType());
			}
			if(StringUtils.isNotBlank(updateJson)){
				List<SchoolActinfo> updateSet = gson.fromJson(updateJson, new TypeToken<List<SchoolActinfo>>(){}.getType());
				if(actSet != null && actSet.size()>0){
					for (SchoolActinfo schoolActinfo : updateSet) {
						actSet.add(schoolActinfo);
					}
				}else{
					actSet = updateSet;
				}
			}
			if(actSet != null && actSet.size()>0){
				sysStatus = scc005Service.saveAct(actSet);
			}else{
				sysStatus = 0;
			}
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
	public SCC005Service getScc005Service() {
		return scc005Service;
	}
	@JSON(serialize=false)
	public void setScc005Service(SCC005Service scc005Service) {
		this.scc005Service = scc005Service;
	}
	
	public SchoolActivity getBill() {
		return bill;
	}
	public void setBill(SchoolActivity bill) {
		this.bill = bill;
	}

	public List<SchoolActivity> getBillSet() {
		return billSet;
	}
	public void setBillSet(List<SchoolActivity> billSet) {
		this.billSet = billSet;
	}

	public List<SchoolActivity> getListSet() {
		return listSet;
	}
	public void setListSet(List<SchoolActivity> listSet) {
		this.listSet = listSet;
	}
	
	public List<SchoolActinfo> getActSet() {
		return actSet;
	}
	public void setActSet(List<SchoolActinfo> actSet) {
		this.actSet = actSet;
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

	public SchoolActivity getModel() {
		return schoolActivity;
	}
}
