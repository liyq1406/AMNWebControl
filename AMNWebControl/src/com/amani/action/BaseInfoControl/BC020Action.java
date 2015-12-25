package com.amani.action.BaseInfoControl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.amani.service.BaseInfoControl.BC020Service;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 供应商设定
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc020")
public class BC020Action extends AMN_ModuleAction implements ModelDriven<Suppliermode>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private BC020Service bc020Service;
	private List<Suppliermode> modeSet;
	private List<Supplierlink> listSet;
	private List<Supplierprice> priceSet;
	private Goodsinfo goods;
	private Suppliermode suppliermode = new Suppliermode();
	
	@Action(value="loadSet", results={@Result(name="load_success", type="json")})
	public String loadSet(){
		try{
			List<Suppliermode> list = bc020Service.loadSet();
			modeSet = list == null ? new ArrayList<Suppliermode>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="check", results={@Result(name="post_success", type="json")}) 
	public String check() {
		try{
			sysStatus = bc020Service.validate(suppliermode.getLogin(), suppliermode.getNo()) ? 1 : 0;
		}catch(Exception ex){
			ex.printStackTrace();
			sysStatus = 0;
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action(value="post", results={@Result(name="post_success", type="json")}) 
	@Override
	public String post() {
		try{
			sysStatus = bc020Service.save(suppliermode);
		}catch(Exception ex){
			ex.printStackTrace();
			sysStatus = 0;
		}
		strMessage = sysStatus==0 ? SystemFinal.POST_FAILURE_MSG : SystemFinal.POST_SUCCESS_MSG;
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action(value="load", results={@Result(name="load_success", type="json")})
	public String load(){
		try{
			List<Supplierlink> list = bc020Service.loadLinkSet(suppliermode.getNo());
			listSet = list == null ? new ArrayList<Supplierlink>() : list;
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
				sysStatus = bc020Service.saveLink(listSet);
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
			List<Supplierprice> list = bc020Service.loadPriceSet(suppliermode.getNo());
			priceSet = list == null ? new ArrayList<Supplierprice>() : list;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="search", results={@Result(name="load_success", type="json")})
	public String search(){
		try{
			List<Goodsinfo> list = bc020Service.searchGoodsInfo(suppliermode.getNo());
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
				sysStatus = bc020Service.savePrice(supplierprice);
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
	public BC020Service getBc020Service() {
		return bc020Service;
	}
	@JSON(serialize=false)
	public void setBc020Service(BC020Service bc020Service) {
		this.bc020Service = bc020Service;
	}

	public List<Suppliermode> getModeSet() {
		return modeSet;
	}
	public void setModeSet(List<Suppliermode> modeSet) {
		this.modeSet = modeSet;
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
