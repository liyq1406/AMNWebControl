package com.amani.action.AdvancedOperations;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.bean.Categoryinfo;
import com.amani.bean.CategoryinfoId;
import com.amani.model.Companyinfo;
import com.amani.model.Compchainstruct;
import com.amani.service.AdvancedOperations.AC020Service;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac020")
public class AC020Action extends AMN_ModuleAction{
	@Autowired
	private AC020Service ac020Service;
    public List<Categoryinfo> lsCategoryinfo;
    public List<CategoryinfoId> lsCategoryinfoid;
    public Categoryinfo categoryinfo;
    private String strCurCompId;//门店编号
    public String categoryno;//类别编号
    public String postationid;//职位编号
    public String strMessage;
    public String postations ;//选中的职位

   
    
	
    //得到门店中所有排班类别信息
	@Action(value = "loadCategoryinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCategoryinfo()
	{
		this.lsCategoryinfo=this.ac020Service.loadCategoryinfo(strCurCompId);
		return SystemFinal.LOAD_SUCCESS;
	}
	//添加类别信息
	@Action(value = "postCategoryinfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	}) 
	public String postCategoryinfo()
	{
		try
		{	boolean b = true;
			this.strMessage="";
			//根据门店号和类别编号查询类别信息
			Categoryinfo c = this.ac020Service.getCategoryinfo(categoryinfo.getCompno(),categoryinfo.getCategoryno());
			if(c != null){
				this.strMessage=categoryinfo.getCompno()+"门店中存在"+categoryinfo.getCategoryno()+"编号的类别!";
			}else {
				b = this.ac020Service.postCategoryinfo(categoryinfo);
				if(!b){
					this.strMessage="添加失败!";
				}else{
					this.lsCategoryinfo=this.ac020Service.loadCategoryinfo(categoryinfo.getCompno());
				}
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	//删除类别
	@Action(value = "deleteCategoryinfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	}) 
	public String deleteCategoryinfo()
	{
		try
		{	boolean b = true;
			this.strMessage="";
			//根据门店号和类别编号查询所关联的职位信息
			this.lsCategoryinfoid=this.ac020Service.loadCategoryinfoid(strCurCompId,categoryno);
			if(lsCategoryinfoid.size()>0){
				this.strMessage = strCurCompId+"门店,"+categoryno+"类别编号下存在关联的职位,不允许删除!";
			}else{
				b = this.ac020Service.deleteCategoryinfo(strCurCompId,categoryno);
				if(!b){
					this.strMessage="删除失败!";
				}else{
					this.lsCategoryinfo=this.ac020Service.loadCategoryinfo(strCurCompId);
				}
			}

			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	//根据门店号和类别编号查询所关联的职位信息
	@Action(value = "loadCategoryinfoid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCategoryinfoid()
	{
		this.lsCategoryinfoid=this.ac020Service.loadCategoryinfoid(strCurCompId,categoryno);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//移除排班类别与职位的关系
	@Action(value = "deleteCategoryinfoid",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
	}) 
	public String deleteCategoryinfoid()
	{
		try
		{	boolean b = true;
			this.strMessage="";
			b = this.ac020Service.deleteCategoryinfoid(strCurCompId,categoryno,postationid);
			if(!b){
				this.strMessage="删除失败!";
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	//加载工作职位
	@Action(value = "loadPostation",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadPostation()
	{
		this.lsCategoryinfoid=this.ac020Service.loadPostation();
		return SystemFinal.LOAD_SUCCESS;
	}
	//添加工作职位
	@Action(value = "addPostation",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String addPostation()
	{
		String str [] = postations.split(",");
		StringBuffer strSql = new StringBuffer();
		this.strMessage="";
		this.lsCategoryinfoid=this.ac020Service.loadPostation();
		for(int i=0;i<str.length;i++){
			String postationid = str[i];
			//根据门店号和类别编号查询所关联的职位信息
			boolean fal =this.ac020Service.loadCategoryinfoid(strCurCompId,categoryno,postationid);
			if(fal)continue;
			
			String postationname="";
			for(int j=0;j<lsCategoryinfoid.size();j++){
				String s = lsCategoryinfoid.get(j).getPostationid();
				if(postationid.equals(s)){
					postationname = lsCategoryinfoid.get(j).getPostationname();
					break;
				}
			}
			strSql.append("insert into Categoryinfoid(compno,categoryno,postationid,postationname) values(" +
					"'"+strCurCompId+"','"+categoryno+"','"+postationid+"','"+postationname+"')");
		}
		if(strSql.toString().indexOf("insert")>-1){
			boolean b =this.ac020Service.addPostation(strSql.toString());
			if(!b){
				this.strMessage="添加失败!";
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//将类别信息下发到门店
	@Action(value = "downDetailInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String downDetailInfo()
	{
		//根据门店编号获得关联的所有子门店
		List<Companyinfo> list = this.ac020Service.loadCompanyinfoByCurCompId(strCurCompId);
		this.lsCategoryinfoid = this.ac020Service.loadCategoryinfoid(strCurCompId,categoryno);
		this.categoryinfo = this.ac020Service.getCategoryinfo(strCurCompId,categoryno);
		StringBuffer strSql = new StringBuffer();
		this.strMessage="";
		for(int i=0;i<list.size();i++){
			String compno = list.get(i).getCompno()+"";
			Categoryinfo c= this.ac020Service.getCategoryinfo(compno,categoryno);;
			if(c == null){
				strSql.append("insert into Categoryinfo(compno,categoryno,categoryname,categorymark) values(" +
				"'"+compno+"','"+categoryinfo.getCategoryno()+"','"+categoryinfo.getCategoryname()+"','"+categoryinfo.getCategorymark()+"')");
				for(int j=0;j<lsCategoryinfoid.size();j++){
					//String compno = lsCategoryinfoid.get(j).getCompno();//当前门店
					String categoryno = lsCategoryinfoid.get(j).getCategoryno();//类别编号
					String postationid = lsCategoryinfoid.get(j).getPostationid();//职位编号
					String postationname = lsCategoryinfoid.get(j).getPostationname();//职位名称
					strSql.append("insert into Categoryinfoid(compno,categoryno,postationid,postationname) values(" +
					"'"+compno+"','"+categoryno+"','"+postationid+"','"+postationname+"')");
				}
			}else{
				for(int j=0;j<lsCategoryinfoid.size();j++){
					//String compno = lsCategoryinfoid.get(j).getCompno();//当前门店
					String categoryno = lsCategoryinfoid.get(j).getCategoryno();//类别编号
					String postationid = lsCategoryinfoid.get(j).getPostationid();//职位编号
					String postationname = lsCategoryinfoid.get(j).getPostationname();//职位名称
					boolean b = this.ac020Service.loadCategoryinfoid(compno,categoryno,postationid);
					if(!b){
						strSql.append("insert into Categoryinfoid(compno,categoryno,postationid,postationname) values(" +
						"'"+compno+"','"+categoryno+"','"+postationid+"','"+postationname+"')");
					}
				}
			}
		}
		if(strSql.toString().indexOf("insert")>-1){
			boolean b =this.ac020Service.addPostation(strSql.toString());
			if(!b){
				this.strMessage="添加失败!";
			}
		}
		return SystemFinal.LOAD_SUCCESS;
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
		return false;
	}

	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return false;
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

	public void setAc020Service(AC020Service ac020Service) {
		this.ac020Service = ac020Service;
	}

	
	public List<Categoryinfo> getLsCategoryinfo() {
		return lsCategoryinfo;
	}

	
	public void setLsCategoryinfo(List<Categoryinfo> lsCategoryinfo) {
		this.lsCategoryinfo = lsCategoryinfo;
	}

	
	public String getStrCurCompId() {
		return strCurCompId;
	}

	
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}

	
	public Categoryinfo getCategoryinfo() {
		return categoryinfo;
	}

	
	public void setCategoryinfo(Categoryinfo categoryinfo) {
		this.categoryinfo = categoryinfo;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	public String getCategoryno() {
		return categoryno;
	}
	public void setCategoryno(String categoryno) {
		this.categoryno = categoryno;
	}
	public List<CategoryinfoId> getLsCategoryinfoid() {
		return lsCategoryinfoid;
	}
	public void setLsCategoryinfoid(List<CategoryinfoId> lsCategoryinfoid) {
		this.lsCategoryinfoid = lsCategoryinfoid;
	}
	public String getPostationid() {
		return postationid;
	}
	public String getPostations() {
		return postations;
	}
	public void setPostations(String postations) {
		this.postations = postations;
	}
	public void setPostationid(String postationid) {
		this.postationid = postationid;
	}
	

}
