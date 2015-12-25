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
import com.amani.model.Goodsinfo;
import com.amani.model.Supplierlink;
import com.amani.model.Suppliermode;
import com.amani.model.Supplierprice;
import com.amani.service.SupplierControl.SUC001Service;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 合作学校设定
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/suc001")
public class SUC001Action extends AMN_ModuleAction implements ModelDriven<Suppliermode>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private SUC001Service suc001Service;
	private List<Supplierlink> listSet;
	private List<Supplierprice> priceSet;
	private Goodsinfo goods;
	private Suppliermode suppliermode = new Suppliermode();
	
	@Action(value="login", results={@Result(name="load_success", location = "/SupplierControl/login/fullScreen.html"),
			@Result(name="load_failure", location = "/entry.jsp")})
	public String login(){
		try{
			//用户名为空，直接返回
			if(StringUtils.isBlank(suppliermode.getLogin())){
				addActionError("用户名或密码错误，登录失败！");
				return SystemFinal.LOAD_FAILURE;
			}
			List<Suppliermode> list= suc001Service.validate(suppliermode);
			if(list == null || list.size()==0){
				addActionError("用户名或密码错误，登录失败！");
			}else if(list.get(0).getState() == 0){
				addActionError("该用户登录信息已失效，禁止登录！");
			}else{
				HttpSession session = ServletActionContext.getRequest().getSession();
				session.removeAttribute("supplier");
				session.setAttribute("supplier", list.get(0));
				session.removeAttribute("sucModule");
				Gson gson = new Gson();
				session.setAttribute("sucModule", gson.toJson(suc001Service.getModule()));
				return SystemFinal.LOAD_SUCCESS;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			addActionError("登录失败，请刷新重试！");
		}
		return SystemFinal.LOAD_FAILURE;
	}
	
	@Action(value="post", results={@Result(name="post_success", type="json")}) 
	@Override
	public String post() {
		try{
			sysStatus = suc001Service.save(suppliermode);
			if(sysStatus==1){
				HttpSession session = ServletActionContext.getRequest().getSession();
				session.removeAttribute("supplier");
				session.setAttribute("supplier", suppliermode);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			sysStatus = 0;
		}
		strMessage = sysStatus==0 ? SystemFinal.POST_FAILURE_MSG : SystemFinal.POST_SUCCESS_MSG;
		return SystemFinal.POST_SUCCESS;
	}
	
	private Suppliermode getSupplier(){
		Suppliermode supplier = null;
		HttpSession session = ServletActionContext.getRequest().getSession();
		Object attr = session.getAttribute("supplier");
		if(attr instanceof Suppliermode){
			supplier = (Suppliermode) attr;
		}
		return supplier;
	}
	
	@Action(value="load", results={@Result(name="load_success", type="json")})
	public String load(){
		try{
			Suppliermode supplier = getSupplier();
			if(supplier != null){
				List<Supplierlink> list = suc001Service.loadLinkSet(supplier.getNo());
				listSet = list == null ? new ArrayList<Supplierlink>() : list;
			}else{
				listSet = new ArrayList<Supplierlink>(); 
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="add", results={@Result(name="add_success", type="json")}) 
	@Override
	public String add(){
		try{
			String addJson = suppliermode.getNo();
			String updateJson = suppliermode.getName();
			Gson gson = new Gson();
			if(StringUtils.isNotBlank(addJson)){
				listSet = gson.fromJson(addJson, new TypeToken<List<Supplierlink>>(){}.getType());
			}
			if(StringUtils.isNotBlank(updateJson)){
				List<Supplierlink> updateSet = gson.fromJson(updateJson, new TypeToken<List<Supplierlink>>(){}.getType());
				if(listSet != null && listSet.size()>0){
					for (Supplierlink supplierlink : updateSet) {
						listSet.add(supplierlink);
					}
				}else{
					listSet = updateSet;
				}
			}
			if(listSet != null && listSet.size()>0){
				sysStatus = suc001Service.saveLink(listSet);
			}else{
				sysStatus=0;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			sysStatus = 0;
		}
		strMessage = sysStatus==0 ? SystemFinal.POST_FAILURE_MSG : SystemFinal.POST_SUCCESS_MSG;
		return SystemFinal.ADD_SUCCESS;
	}
	
	@Action(value="query", results={@Result(name="load_success", type="json")})
	public String query(){
		try{
			Suppliermode supplier = getSupplier();
			if(supplier != null){
				List<Supplierprice> list = suc001Service.loadPriceSet(supplier.getNo());
				priceSet = list == null ? new ArrayList<Supplierprice>() : list;
			}else{
				priceSet = new ArrayList<Supplierprice>(); 
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="search", results={@Result(name="load_success", type="json")})
	public String search(){
		try{
			List<Goodsinfo> list = suc001Service.searchGoodsInfo(suppliermode.getNo());
			goods = list == null || list.size()==0 ? new Goodsinfo() : list.get(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="addto", results={@Result(name="add_success", type="json")}) 
	public String addto() {
		try{
			String dataJson = suppliermode.getName();
			if(StringUtils.isNotBlank(dataJson)){
				Gson gson = new Gson();
				Supplierprice supplierprice = gson.fromJson(dataJson, new TypeToken<Supplierprice>(){}.getType());
				sysStatus = suc001Service.savePrice(supplierprice);
			}else{
				sysStatus=0;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			sysStatus = 0;
		}
		strMessage = sysStatus==0 ? SystemFinal.POST_FAILURE_MSG : SystemFinal.POST_SUCCESS_MSG;
		return SystemFinal.ADD_SUCCESS;
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
	public SUC001Service getSuc001Service() {
		return suc001Service;
	}
	@JSON(serialize=false)
	public void setSuc001Service(SUC001Service suc001Service) {
		this.suc001Service = suc001Service;
	}

	public Suppliermode getSchoolInfo() {
		return suppliermode;
	}
	public void setSchoolInfo(Suppliermode schoolInfo) {
		this.suppliermode = schoolInfo;
	}

	public List<Supplierlink> getListSet() {
		return listSet;
	}
	public void setListSet(List<Supplierlink> listSet) {
		this.listSet = listSet;
	}
	
	public List<Supplierprice> getPriceSet() {
		return priceSet;
	}
	public void setPriceSet(List<Supplierprice> priceSet) {
		this.priceSet = priceSet;
	}

	public Goodsinfo getGoods() {
		return goods;
	}
	public void setGoods(Goodsinfo goods) {
		this.goods = goods;
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

	public Suppliermode getModel() {
		return suppliermode;
	}
}
