package com.amani.action.SchoolControl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.bean.SCC002Bean;
import com.amani.model.SchoolCredit;
import com.amani.model.SchoolInfo;
import com.amani.model.SchoolScore;
import com.amani.model.SchoolScorePK;
import com.amani.service.SchoolControl.SCC002Service;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 课程学分设定
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/scc002")
public class SCC002Action extends AMN_ModuleAction implements ModelDriven<SchoolCredit>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private SCC002Service scc002Service;
	private List<SchoolCredit> listSet;
	private List<SCC002Bean>  scoreList;
	private SchoolCredit schoolCredit = new SchoolCredit();
	
	@Action(value="init", results={@Result(name="load_success", location = "/SchoolControl/SCC002/index.jsp")})
	public String init(){
		try{
			String schoolSet = "";
			HttpServletRequest request = ServletActionContext.getRequest();
			List<SchoolInfo> list= scc002Service.initDataSet();
			if(list != null){
				Gson gson = new Gson();
				schoolSet = gson.toJson(list);
			}
			request.setAttribute("schoolSet", schoolSet);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="load", results={@Result(name="load_success", type="json")})
	public String load(){
		try{
			List<SchoolCredit> list= scc002Service.loadDataSet();
			listSet = list == null ? new ArrayList<SchoolCredit>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="query", results={@Result(name="load_success", type="json")})
	public String query(){
		try{
			List<SchoolCredit> list= scc002Service.queryDataSet(schoolCredit.getName());
			listSet = list == null ? new ArrayList<SchoolCredit>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="loadScore", results={@Result(name="load_success", type="json")})
	public String loadScore(){
		try{
			List<SchoolScore> list= scc002Service.getPostionScore(schoolCredit.getNo());
			scoreList = new ArrayList<SCC002Bean>();
			if(list != null){
				for (SchoolScore schoolScore : list) {
					SCC002Bean bean = new SCC002Bean();
					SchoolScorePK pk = schoolScore.getPk();
					bean.setCredit_no(pk.getCredit_no());
					bean.setPostion(pk.getPostion());
					bean.setScore(schoolScore.getScore());
					scoreList.add(bean);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="post", results={@Result(name="post_success", type="json")}) 
	@Override
	public String post() {
		try{
			sysStatus = scc002Service.save(schoolCredit);
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
	public SCC002Service getScc002Service() {
		return scc002Service;
	}
	@JSON(serialize=false)
	public void setScc002Service(SCC002Service scc002Service) {
		this.scc002Service = scc002Service;
	}

	public SchoolCredit getSchoolCredit() {
		return schoolCredit;
	}
	public void setSchoolCredit(SchoolCredit schoolCredit) {
		this.schoolCredit = schoolCredit;
	}

	public List<SchoolCredit> getListSet() {
		return listSet;
	}
	public void setListSet(List<SchoolCredit> listSet) {
		this.listSet = listSet;
	}
	
	public List<SCC002Bean> getScoreList() {
		return scoreList;
	}
	public void setScoreList(List<SCC002Bean> scoreList) {
		this.scoreList = scoreList;
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

	public SchoolCredit getModel() {
		return schoolCredit;
	}
}
