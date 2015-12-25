package com.amani.action.AdvancedOperations;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.Evaluation;
import com.amani.service.AdvancedOperations.AC003Service;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac003")
public class AC003Action extends AMN_ModuleAction{

	private String strMessage;
	private List<Evaluation> lsEvaluations;
	@Autowired
	private AC003Service ac003Service;
	public String getStrMessage() {
		return strMessage;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	public List<Evaluation> getLsEvaluations() {
		return lsEvaluations;
	}

	public void setLsEvaluations(List<Evaluation> lsEvaluations) {
		this.lsEvaluations = lsEvaluations;
	}

	public Evaluation getCurMaster() {
		return curMaster;
	}

	public void setCurMaster(Evaluation curMaster) {
		this.curMaster = curMaster;
	}

	public void setAc003Service(AC003Service ac003Service) {
		this.ac003Service = ac003Service;
	}
	
	@Action( value="load", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String load()
	{
		lsEvaluations=ac003Service.loadEvaluations();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="post", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String post()
	{
		this.strMessage="";
		if(ac003Service.checkIsBack(curMaster.getUuid())==false)
		{
			this.strMessage="单据已经处理过了。请不要重复返销";
			return SystemFinal.LOAD_FAILURE;
		}
		curMaster.setStates(1);
		if(ac003Service.post(curMaster, ""))
		{
			if(ac003Service.afterPost(curMaster)==false)
			{
				this.strMessage="处理单据错误，请重新操作";
				return SystemFinal.LOAD_FAILURE;
			}
		}
		else 
		{
			this.strMessage="保存失败";
			return SystemFinal.LOAD_FAILURE;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="updBillState", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String updBillState()
	{
		this.strMessage="";
		if(ac003Service.checkIsBack(curMaster.getUuid())==false)
		{
			this.strMessage="单据已经处理过了。请不要重复返销";
			return SystemFinal.LOAD_FAILURE;
		}
		if(ac003Service.updBillState(curMaster)==false)
		{
			this.strMessage="驳回失败，请重新操作";
		}
		return SystemFinal.LOAD_SUCCESS;
	}

	private Evaluation curMaster;
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean afterPost() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean postActive() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean afterLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean afterAdd() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean addActive() {
		// TODO Auto-generated method stub
		return true;
	}

}
