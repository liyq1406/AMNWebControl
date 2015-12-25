package com.amani.action.BaseInfoControl;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ReportAction;
import com.amani.bean.BC021Bean;
import com.amani.model.Commoninfo;
import com.amani.model.Mactivityinfo;
import com.amani.model.Projectinfo;
import com.amani.service.BaseInfoControl.BC021Service;
import com.amani.tools.SystemFinal;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 门店活动设定
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc021")
public class BC021Action extends AMN_ReportAction implements ModelDriven<BC021Bean>{
	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private BC021Service bc021Service;
	private String strCurCompId;
	private String goodsname;
	private Projectinfo projectinfo;
	private Commoninfo commoninfo;
	private BC021Bean activity = new BC021Bean();
	private List<Mactivityinfo> dataSet;
	private List<Object> objSet;
	//加载活动列表
	@Action( value="load", results={ @Result(type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String load() {
		try{
			dataSet = bc021Service.loadSet(strCurCompId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//加载活动列表
	@Action( value="loadAll", results={ @Result(type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadAll() {
		try{
			objSet = bc021Service.loadAll(strCurCompId, activity.getActivinid());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//查询活动项目
	@Action( value="queryItem", results={@Result(type="json", name="load_success")})
	public String queryItem() {
		projectinfo = bc021Service.loadIteminfo(strCurCompId, activity.getActivno());
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//查询活动产品
	@Action( value="queryGoods", results={@Result(type="json", name="load_success")})
	public String queryGoods() {
		goodsname = bc021Service.getDataTool().loadGoodsName(strCurCompId, activity.getActivno(), new StringBuffer());
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//查询活动分类
	@Action( value="queryClassify", results={@Result(type="json", name="load_success")})
	public String queryClassify() {
		commoninfo = bc021Service.loadCommonInfo(activity.getStrCodeno(), activity.getStrCodekey());
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//新增活动
	@Action( value="post", results={ @Result(type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String post() {
		try{
			bc021Service.postData(activity);
		}catch(Exception ex){
			ex.printStackTrace();
			strMessage = "ERROR";
			return SystemFinal.POST_FAILURE;
		}
		strMessage = "SUCCESS";
		return SystemFinal.POST_SUCCESS;
	} 
	
	//更新时间
	@Action( value="updateDate", results={ @Result(type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String updateDate() {
		try{
			bc021Service.updateDate(activity);
		}catch(Exception ex){
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
		return SystemFinal.POST_SUCCESS;
	} 
	
	//作废
	@Action( value="updateState", results={ @Result(type="json", name="post_success"),
			@Result( type="json", name="post_failure")})
	public String updateState() {
		try{
			bc021Service.updateState(activity);
		}catch(Exception ex){
			ex.printStackTrace();
			strMessage = "ERROR";
			return SystemFinal.POST_FAILURE;
		}
		strMessage = "SUCCESS";
		return SystemFinal.POST_SUCCESS;
	} 
	
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}

	public Projectinfo getProjectinfo() {
		return projectinfo;
	}
	public void setProjectinfo(Projectinfo projectinfo) {
		this.projectinfo = projectinfo;
	}

	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public Commoninfo getCommoninfo() {
		return commoninfo;
	}
	public void setCommoninfo(Commoninfo commoninfo) {
		this.commoninfo = commoninfo;
	}

	public List<Mactivityinfo> getDataSet() {
		return dataSet;
	}
	public void setDataSet(List<Mactivityinfo> dataSet) {
		this.dataSet = dataSet;
	}
	public List<Object> getObjSet() {
		return objSet;
	}
	public void setObjSet(List<Object> objSet) {
		this.objSet = objSet;
	}

	@Override
	public String getStrMessage() {
		return super.getStrMessage();
	}
	@Override
	public void setStrMessage(String strMessage) {
		super.setStrMessage(strMessage);
	}
	
	@JSON(serialize=false)
	public BC021Service getBc021Service() {
		return bc021Service;
	}
	@JSON(serialize=false)
	public void setBc021Service(BC021Service bc021Service) {
		this.bc021Service = bc021Service;
	}

	public BC021Bean getModel() {
		return activity;
	}
}
