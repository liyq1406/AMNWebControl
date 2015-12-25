package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.Categoryinfo;
import com.amani.bean.CategoryinfoId;
import com.amani.model.Companyinfo;
import com.amani.model.Compchainstruct;
import com.amani.model.Compschedulinfo;
import com.amani.model.Staffinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class AC020Service  extends AMN_ModuleService{
	
	
	
    //加载排班类别
	public List<Categoryinfo> loadCategoryinfo(String strCurCompId)
	{
		try
		{
			String strSql="select * from categoryinfo where compno='"+strCurCompId+"'";
			AnlyResultSet<List<Categoryinfo>> analysis = new AnlyResultSet<List<Categoryinfo>>() {
				public List<Categoryinfo> anlyResultSet(ResultSet rs) {
					List<Categoryinfo> returnValue = new ArrayList();
					Categoryinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Categoryinfo();
							record.setCompno(CommonTool.FormatString(rs.getString("compno")));
							record.setCategoryno(CommonTool.FormatString(rs.getString("categoryno")));
							record.setCategoryname(CommonTool.FormatString(rs.getString("categoryname")));
							record.setCategorymark(CommonTool.FormatString(rs.getString("categorymark")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Categoryinfo> ls= (List<Categoryinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//根据门店号和类别编号查询类别信息
	public Categoryinfo getCategoryinfo(String compno,String categoryno)
	{
		try
		{
			String strSql="select * from categoryinfo where compno='"+compno+"' and categoryno ='"+categoryno+"'";
			ResultSet rs = this.amn_Dao.executeQuery(strSql);
			if(rs != null){
				while (rs != null && rs.next() == true) {
					Categoryinfo record=new Categoryinfo();
					record.setCompno(CommonTool.FormatString(rs.getString("compno")));
					record.setCategoryno(CommonTool.FormatString(rs.getString("categoryno")));
					record.setCategoryname(CommonTool.FormatString(rs.getString("categoryname")));
					record.setCategorymark(CommonTool.FormatString(rs.getString("categorymark")));
					return record;
				}
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	//删除类别信息
	public boolean deleteCategoryinfo(String compno,String categoryno)
	{
		boolean b = false;
		try
		{
			String strSql="delete from categoryinfo where compno = '"+compno+"' and categoryno = '"+categoryno+"'";
			b = this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return  b;
		}
		return b;
	}
	//删除类别与职位的关系
	public boolean deleteCategoryinfoid(String compno,String categoryno,String postationid)
	{
		boolean b = false;
		try
		{
			String strSql="delete from categoryinfoid where compno = '"+compno+"' and categoryno = '"+categoryno+"' and postationid = '"+postationid+"'";
			b = this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return  b;
		}
		return b;
	}
	//根据门店号和类别编号查询类别信息
	public boolean postCategoryinfo(Categoryinfo categoryinfo)
	{
		boolean b = false;
		try
		{
			String strSql="insert into Categoryinfo (compno,categoryno,categoryname,categorymark) values " +
					" ('"+categoryinfo.getCompno()+"','"+categoryinfo.getCategoryno()+"','"+categoryinfo.getCategoryname()+"','"+categoryinfo.getCategorymark()+"')";
			b = this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return  b;
		}
		return b;
	}

	public boolean addPostation(String strSql)
	{
		boolean b = false;
		try
		{		
			b = this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return  b;
		}
		return b;
	}
	
	//加载类别与职位关系
	public List<CategoryinfoId> loadCategoryinfoid(String strCurCompId,String categoryno)
	{
		try
		{
			String strSql="select b.* from categoryinfo a ,categoryinfoid b where a.compno = b.compno and a.categoryno=b.categoryno and a.compno='"+strCurCompId+"' and a.categoryno='"+categoryno+"' order by b.postationid";
			AnlyResultSet<List<CategoryinfoId>> analysis = new AnlyResultSet<List<CategoryinfoId>>() {
				public List<CategoryinfoId> anlyResultSet(ResultSet rs) {
					List<CategoryinfoId> returnValue = new ArrayList();
					CategoryinfoId record=null;
					try {	
						while (rs != null && rs.next() == true) {
							record=new CategoryinfoId();
							record.setCompno(CommonTool.FormatString(rs.getString("compno")));
							record.setCategoryno(CommonTool.FormatString(rs.getString("categoryno")));
							record.setPostationid(CommonTool.FormatString(rs.getString("postationid")));
							record.setPostationname(CommonTool.FormatString(rs.getString("postationname")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<CategoryinfoId> ls= (List<CategoryinfoId>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	//加载工作职位
	public List<CategoryinfoId> loadPostation()
	{
		try
		{
			String strSql="select parentcodekey,parentcodevalue From commoninfo where codesource='D' and infotype = 'GZZC' group by parentcodekey,parentcodevalue";
			AnlyResultSet<List<CategoryinfoId>> analysis = new AnlyResultSet<List<CategoryinfoId>>() {
				public List<CategoryinfoId> anlyResultSet(ResultSet rs) {
					List<CategoryinfoId> returnValue = new ArrayList();
					CategoryinfoId record=null;
					try {	
						while (rs != null && rs.next() == true) {
							record=new CategoryinfoId();
							//record.setCompno(CommonTool.FormatString(rs.getString("compno")));
							//record.setCategoryno(CommonTool.FormatString(rs.getString("categoryno")));
							record.setPostationid(CommonTool.FormatString(rs.getString("parentcodekey")));
							record.setPostationname(CommonTool.FormatString(rs.getString("parentcodevalue")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<CategoryinfoId> ls= (List<CategoryinfoId>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	//加载类别与职位关系是否存在
	
	public boolean loadCategoryinfoid(String strCurCompId,String categoryno,String postationid)
	{
		boolean b = false;
		try
		{
			
			String strSql="select b.* from categoryinfo a ,categoryinfoid b where a.compno = b.compno and a.categoryno=b.categoryno and a.compno='"+strCurCompId+"' and a.categoryno='"+categoryno+"' and b.postationid ='"+postationid+"' order by postationid";		
			ResultSet rs = this.amn_Dao.executeQuery(strSql);
			while (rs != null && rs.next() == true) {
				b =true;
				break;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return  b;
		}
		return b;
	}
	public  List<Companyinfo>  loadCompanyinfoByCurCompId(String strCurCompId)
	{
		try
		{
			String strSql=" select  companyinfo From Companyinfo companyinfo,Compchaininfo compchaininfo where  compno=relationcomp and curcomp='"+strCurCompId+"'  ";
			return (List<Companyinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	
	}
	
	
	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
