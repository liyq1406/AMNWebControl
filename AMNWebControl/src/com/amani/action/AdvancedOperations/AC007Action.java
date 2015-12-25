package com.amani.action.AdvancedOperations;

import java.util.List;

import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ReportAction;
import com.amani.model.Goodsinfo;
import com.amani.model.Projectinfo;
import com.amani.model.WxProduct;
import com.amani.model.WxProductDetail;
import com.amani.model.WxProductPackage;
import com.amani.service.AdvancedOperations.AC007Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 微信
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac007")
public class AC007Action extends AMN_ReportAction implements ModelDriven<WxProduct>{
	private static final long serialVersionUID = 1L;
	@Autowired
	private AC007Service ac007Service;
	
	private WxProduct wxProduct = new WxProduct();
	private List<Object> dataSet;
	private List<WxProduct> proSet;
	private Goodsinfo goodsinfo;
	private Projectinfo projectinfo;
	private WxProduct product;
	private List<WxProductPackage> productPackages;
	private List<WxProductDetail> stepSet;
	//初始化加载数据
	@Action(value="init", results={@Result(name="load_success", type="json")})
	public String init(){
		try {
			dataSet = ac007Service.loadInitData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="load", results={@Result(name="load_success", type="json")})
	public String load(){
		proSet = ac007Service.loadDataSet(wxProduct);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="loadPackPro", results={@Result(name="load_success", type="json")})
	public String loadPackPro(){
		productPackages = ac007Service.loadPackage(wxProduct.getId());
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//绑定项目
	@Action( value="bindItem", results={@Result(type="json", name="load_success")})
	public String bindItem() {
		String itemId = ObjectUtils.toString(wxProduct.getCard_id());
		String compId = CommonTool.getLoginInfo("COMPID");
		projectinfo = ac007Service.getDataTool().loadProjectInfo(compId, itemId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//绑定产品
	@Action( value="bindGoods", results={@Result(type="json", name="load_success")})
	public String bindGoods() {
		String goodsId = ObjectUtils.toString(wxProduct.getCard_id());
		String compId = CommonTool.getLoginInfo("COMPID");
		goodsinfo = ac007Service.getDataTool().loadGoodsInfo(compId, goodsId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//查询套餐包含的项目
	@Action( value="queryProject", results={@Result(type="json", name="load_success")})
	public String queryProject() {
		product = ac007Service.checkProject(wxProduct.getCid());
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//查询套餐编号是否存在
	@Action( value="checkPackageNo", results={@Result(type="json", name="load_success")})
	public String checkPackageNo() {
		boolean bool = ac007Service.checkPackageNo(wxProduct.getCid());
		if(bool){
			strMessage = "套餐编号已存在！";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value="post", results={@Result(name="post_success", type="json")}) 
	public String post() {
		try {
			ac007Service.post(wxProduct);
		} catch (Exception e) {
			e.printStackTrace();
			this.strMessage=SystemFinal.POST_FAILURE_MSG;
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action(value="loadStep", results={@Result(name="load_success", type="json")})
	public String loadStep(){
		try {
			stepSet = ac007Service.loadStep(wxProduct.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@JSON(serialize=false)
	public AC007Service getAc007Service() {
		return ac007Service;
	}
	@JSON(serialize=false)
	public void setAc007Service(AC007Service ac007Service) {
		this.ac007Service = ac007Service;
	}

	@Override
	public String getStrMessage() {
		return super.getStrMessage();
	}
	@Override
	public void setStrMessage(String strMessage) {
		super.setStrMessage(strMessage);
	}
	
	public WxProduct getProduct() {
		return product;
	}
	public void setProduct(WxProduct product) {
		this.product = product;
	}

	public Projectinfo getProjectinfo() {
		return projectinfo;
	}
	public void setProjectinfo(Projectinfo projectinfo) {
		this.projectinfo = projectinfo;
	}

	public Goodsinfo getGoodsinfo() {
		return goodsinfo;
	}
	public void setGoodsinfo(Goodsinfo goodsinfo) {
		this.goodsinfo = goodsinfo;
	}

	public List<WxProductPackage> getProductPackages() {
		return productPackages;
	}
	public void setProductPackages(List<WxProductPackage> productPackages) {
		this.productPackages = productPackages;
	}

	public List<Object> getDataSet() {
		return dataSet;
	}
	public void setDataSet(List<Object> dataSet) {
		this.dataSet = dataSet;
	}

	public WxProduct getWxProduct() {
		return wxProduct;
	}
	public void setWxProduct(WxProduct wxProduct) {
		this.wxProduct = wxProduct;
	}

	public List<WxProduct> getProSet() {
		return proSet;
	}
	public void setProSet(List<WxProduct> proSet) {
		this.proSet = proSet;
	}

	public List<WxProductDetail> getStepSet() {
		return stepSet;
	}
	public void setStepSet(List<WxProductDetail> stepSet) {
		this.stepSet = stepSet;
	}

	public WxProduct getModel() {
		return wxProduct;
	}
}
