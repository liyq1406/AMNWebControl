package com.amani.action.SupplierControl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.Dgoodssendinfo;
import com.amani.model.Mgoodssendinfo;
import com.amani.model.Suppliermode;
import com.amani.service.SupplierControl.SUC002Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 供应商
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/suc002")
public class SUC002Action extends AMN_ModuleAction implements ModelDriven<Mgoodssendinfo>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private SUC002Service suc002Service;
	private List<?> listSet;
	private Mgoodssendinfo mgoodssendinfo = new Mgoodssendinfo();
	
	@Action(value="load", results={@Result(name="load_success", type="json")})
	public String load(){
		try{
			//HttpServletRequest request = ServletActionContext.getRequest();
			Suppliermode supplier = getSupplier();
			List<Mgoodssendinfo> list= suc002Service.loadDataSet(supplier.getNo());
			listSet = list == null ? new ArrayList<Mgoodssendinfo>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value="loadDetail", results={@Result(name="post_success", type="json")})
	public String post(){
		try{
			List<Dgoodssendinfo> list= suc002Service.loadDetailSet(mgoodssendinfo.getBsendcompid(), mgoodssendinfo.getBsendbillid());
			listSet = list == null ? new ArrayList<Dgoodssendinfo>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	//发货
	@Action(value="delivery", results={@Result(name="post_success", type="json")})
	public String delivery(){
		try{
			if(mgoodssendinfo != null && StringUtils.isNotBlank(mgoodssendinfo.getBsendbillid()) 
					&& StringUtils.isNotBlank(mgoodssendinfo.getStorestaffname())){//storestaffname存储发货产品明细JSON
				Suppliermode supplier = getSupplier();
				mgoodssendinfo.setSenddate(CommonTool.getCurrDate());
				mgoodssendinfo.setSendtime(CommonTool.getCurrTime());
				mgoodssendinfo.setSendstaffid(supplier.getNo());
				mgoodssendinfo.setSendopationerid(supplier.getNo());
				mgoodssendinfo.setSendopationdate(CommonTool.getCurrDate());
				mgoodssendinfo.setSendstate(1);
				Gson gson = new Gson();
				List<Dgoodssendinfo> dgoods = gson.fromJson(mgoodssendinfo.getStorestaffname(), 
							new TypeToken<List<Dgoodssendinfo>>(){}.getType());
				boolean bool = suc002Service.delivery(mgoodssendinfo, dgoods);
				strMessage = bool ? SystemFinal.OPERATION_SUCCESS_MSG : "";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	//作废
	@Action(value="cancel", results={@Result(name="post_success", type="json")})
	public String cancel(){
		try{
			boolean bool = suc002Service.cancel(mgoodssendinfo.getBsendcompid(), mgoodssendinfo.getBsendbillid());
			strMessage = bool ? SystemFinal.OPERATION_SUCCESS_MSG : "";
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	private Suppliermode getSupplier(){
		Suppliermode supplier = null;
		HttpSession session = ServletActionContext.getRequest().getSession();
		Object attr = session.getAttribute("supplier");
		if(attr != null && attr instanceof Suppliermode){
			supplier = (Suppliermode) attr;
		}
		return supplier;
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

	public List<?> getListSet() {
		return listSet;
	}
	public void setListSet(List<?> listSet) {
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

	@JSON(serialize=false)
	public SUC002Service getSuc002Service() {
		return suc002Service;
	}
	@JSON(serialize=false)
	public void setSuc002Service(SUC002Service suc002Service) {
		this.suc002Service = suc002Service;
	}

	public Mgoodssendinfo getModel() {
		return mgoodssendinfo;
	}
}
