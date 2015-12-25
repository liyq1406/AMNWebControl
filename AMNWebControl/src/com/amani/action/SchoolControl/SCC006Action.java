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
import com.amani.model.SchoolCredit;
import com.amani.model.SchoolIndex;
import com.amani.model.SchoolInfo;
import com.amani.service.SchoolControl.SCC006Service;
import com.amani.tools.SystemFinal;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 学分指标设定
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/scc006")
public class SCC006Action extends AMN_ModuleAction implements ModelDriven<SchoolIndex>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private SCC006Service scc006Service;
	
	private SchoolIndex aSchoolIndex = new SchoolIndex();
	private List<SchoolIndex> listSet;
	
	@Action(value="save", results={@Result(name="post_success", type="json")})
	@Override
	public String post(){
		try{
			if(aSchoolIndex!=null)
				scc006Service.save(aSchoolIndex);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	
	@Action(value="load", results={@Result(name="post_success", type="json")})
	public String load(){
		try{
			listSet = scc006Service.load(aSchoolIndex);
		}catch(Exception ex){
			ex.printStackTrace();
		}
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
	public SCC006Service getScc006Service() {
		return scc006Service;
	}
	@JSON(serialize=false)
	public void setScc006Service(SCC006Service scc006Service) {
		this.scc006Service = scc006Service;
	}

	public List<SchoolIndex> getListSet() {
		return listSet;
	}

	public void setListSet(List<SchoolIndex> listSet) {
		this.listSet = listSet;
	}

	public SchoolIndex getModel() {
		if(aSchoolIndex==null){
			aSchoolIndex = new SchoolIndex();
		}
		return aSchoolIndex;
	}
}
