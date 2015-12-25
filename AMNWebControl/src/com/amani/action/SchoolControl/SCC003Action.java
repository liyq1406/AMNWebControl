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
import com.amani.model.SchoolcreditPostion;
import com.amani.model.SchoolCredit;
import com.amani.service.SchoolControl.SCC003Service;
import com.amani.tools.SystemFinal;

/**
 * 职位选修课必修课设定
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/scc003")
public class SCC003Action extends AMN_ModuleAction{
	private static final long serialVersionUID = 1L;
	@Autowired
	private SCC003Service scc003Service;
	
	private List listSet;
	
	private List listSet2;
	
	private String listScroe;
	
	private String credit_no;
	
	@Action(value="load", results={@Result(name="load_success", type="json")})
	public String load(){
		try{
			List<SchoolCredit> list= scc003Service.loadDataSet();
			List<SchoolcreditPostion> list1= scc003Service.loadPostionSchoolcredit(1,credit_no);
			List<SchoolcreditPostion> list2= scc003Service.loadPostionSchoolcredit(2,credit_no);
			listSet = new ArrayList<List>();
			listSet.add(list);
			listSet.add(list1);
			listSet.add(list2);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="loadScore", results={@Result(name="load_success", type="json")})
	public String loadScore(){
		try{
			listSet = scc003Service.loadScore(credit_no);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="save", results={@Result(name="load_success", type="json")})
	public String save(){
		try{
			String res = scc003Service.save(credit_no,listSet,listSet2,listScroe);
			listSet = new ArrayList<List>();
			listSet.add(res);
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
	
	public List getListSet() {
		return listSet;
	}
	public void setListSet(List listSet) {
		this.listSet = listSet;
	}
	
	public List getListSet2() {
		return listSet2;
	}

	public void setListSet2(List listSet2) {
		this.listSet2 = listSet2;
	}

	public String getCredit_no() {
		return credit_no;
	}
	public void setCredit_no(String credit_no) {
		this.credit_no = credit_no;
	}
	@JSON(serialize=false)
	public SCC003Service getScc003Service() {
		return scc003Service;
	}
	@JSON(serialize=false)
	public void setScc003Service(SCC003Service scc003Service) {
		this.scc003Service = scc003Service;
	}

	public String getListScroe() {
		return listScroe;
	}

	public void setListScroe(String listScroe) {
		this.listScroe = listScroe;
	}
	
}
