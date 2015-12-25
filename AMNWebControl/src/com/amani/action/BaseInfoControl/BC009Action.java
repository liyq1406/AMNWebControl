package com.amani.action.BaseInfoControl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;


import com.amani.model.Supplierinfo;
import com.amani.service.BaseInfoControl.BC009Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc009")
public class BC009Action extends AMN_ModuleAction{
	@Autowired
	private BC009Service bc009Service;
	private String strJsonParam;	
	private String strCurSupperId;
    private Supplierinfo curSupplier;
    private List<Supplierinfo> lsSuppliers;
    @JSON(serialize=false)
	public BC009Service getBc009Service() {
		return bc009Service;
	}
    @JSON(serialize=false)
	public void setBc009Service(BC009Service bc009Service) {
		this.bc009Service = bc009Service;
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
	@Action(value = "loadSupplierinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
     @Result(name = "load_failure", type = "json")	
	}) 
	public String loadSupplierinfo()
	{
		
		this.lsSuppliers=this.bc009Service.loadSupplierinfo();
		return SystemFinal.LOAD_SUCCESS;
	}
	@Action(value = "loadCurSupplier",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurSupplier()
	{
		this.curSupplier=this.bc009Service.loadSupplierinfoByCompId(this.strCurSupperId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "post",  results = { 
			 @Result(name = "post_success", type = "json"),
            @Result(name = "post_failure", type = "json")	
         }) 
	@Override
	public String post()
	{
		this.strMessage="";
		if(this.bc009Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC009", SystemFinal.UR_POST)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		boolean flag=this.bc009Service.post(this.curSupplier, null);
		if(flag==false)
		{
			this.strMessage=SystemFinal.POST_FAILURE_MSG;
			return SystemFinal.POST_FAILURE;
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
		this.strMessage="";
		if(this.bc009Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC009", SystemFinal.UR_DELETE)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.DELETE_FAILURE;
		}
		Supplierinfo obj=new Supplierinfo();
		obj.setSupplierid(this.strCurSupperId);
		boolean flag=this.bc009Service.delete(obj);
		obj=null;
		if(flag==false)
		{
			this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
			return SystemFinal.DELETE_FAILURE;
		}
		return SystemFinal.DELETE_SUCCESS;
	}
	
	public String getStrCurSupperId() {
		return strCurSupperId;
	}
	public void setStrCurSupperId(String strCurSupperId) {
		this.strCurSupperId = strCurSupperId;
	}
	public Supplierinfo getCurSupplier() {
		return curSupplier;
	}
	public void setCurSupplier(Supplierinfo curSupplier) {
		this.curSupplier = curSupplier;
	}
	public List<Supplierinfo> getLsSuppliers() {
		return lsSuppliers;
	}
	public void setLsSuppliers(List<Supplierinfo> lsSuppliers) {
		this.lsSuppliers = lsSuppliers;
	}
	
}
