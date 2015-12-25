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
import com.amani.model.SchoolEmployee;
import com.amani.model.Staffinfo;
import com.amani.service.SchoolControl.SCC004Service;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 课程学分输入
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/scc004")
public class SCC004Action extends AMN_ModuleAction implements ModelDriven<SchoolEmployee>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private SCC004Service scc004Service;
	private SchoolEmployee bill;
	private Staffinfo staffinfo;
	private List<SchoolEmployee> billSet;
	private List<SchoolEmployee> listSet;
	private SchoolEmployee schoolEmployee = new SchoolEmployee();
	
	@Action(value="load", results={@Result(name="load_success", type="json")})
	public String load(){
		try{
			List<SchoolEmployee> list= scc004Service.loadBillSet(schoolEmployee.getSalecompid());
			billSet = list == null ? new ArrayList<SchoolEmployee>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="query", results={@Result(name="load_success", type="json")})
	public String query(){
		try{
			String salecompid = schoolEmployee.getSalecompid();
			String salebillid = schoolEmployee.getSalebillid();
			List<SchoolEmployee> list= scc004Service.loadDataSet(salecompid, salebillid);
			listSet = list == null ? new ArrayList<SchoolEmployee>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="add", results={@Result(name="add_success", type="json")})
	public String add(){
		try{
			bill = scc004Service.addBillRecord(schoolEmployee.getSalecompid());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.ADD_SUCCESS;
	}
	
	@Action(value="post", results={@Result(name="post_success", type="json")}) 
	@Override
	public String post(){
		try{
			String dataJson = schoolEmployee.getCredit();
			if(StringUtils.isNotBlank(dataJson)){
				Gson gson = new Gson();
				listSet = gson.fromJson(dataJson, new TypeToken<List<SchoolEmployee>>(){}.getType());
				sysStatus = scc004Service.save(listSet);
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
	
	@Action(value="search", results={@Result(name="load_success", type="json")})
	public String search(){
		try{
			String staffno = schoolEmployee.getStaffno();
			List<Staffinfo> list= scc004Service.searchUser(staffno);
			staffinfo = list == null || list.size()==0 ? new Staffinfo() : list.get(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
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
	public SCC004Service getScc004Service() {
		return scc004Service;
	}
	@JSON(serialize=false)
	public void setScc004Service(SCC004Service scc004Service) {
		this.scc004Service = scc004Service;
	}
	
	public SchoolEmployee getBill() {
		return bill;
	}
	public void setBill(SchoolEmployee bill) {
		this.bill = bill;
	}
	
	public Staffinfo getStaffinfo() {
		return staffinfo;
	}
	public void setStaffinfo(Staffinfo staffinfo) {
		this.staffinfo = staffinfo;
	}

	public List<SchoolEmployee> getBillSet() {
		return billSet;
	}
	public void setBillSet(List<SchoolEmployee> billSet) {
		this.billSet = billSet;
	}

	public List<SchoolEmployee> getListSet() {
		return listSet;
	}
	public void setListSet(List<SchoolEmployee> listSet) {
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

	public SchoolEmployee getModel() {
		return schoolEmployee;
	}
}
