package com.amani.service.BaseInfoControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dmpackageinfo;
import com.amani.model.Mpackageinfo;
import com.amani.model.Projectinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class BC010Service  extends AMN_ModuleService{
	@Autowired
	private DESPlus desPlus;
	public DESPlus getDesPlus() {
		return desPlus;
	}

	public void setDesPlus(DESPlus desPlus) {
		this.desPlus = desPlus;
	}

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		try
		{
			this.amn_Dao.delete(curMaster);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		List<Dmpackageinfo> lsDmpackageinfo=(List<Dmpackageinfo>)details;
		if(lsDmpackageinfo!=null && lsDmpackageinfo.size()>0)
		{
			 this.amn_Dao.saveOrUpdateAll(lsDmpackageinfo);
		}
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		try
		{
			this.amn_Dao.saveOrUpdate(curMaster);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	

	public List<Mpackageinfo> loadMaster(String strCurCompId)
	{
		try
		{
			String strSql=" From Mpackageinfo  mpackageinfo where compid='"+strCurCompId+"' ";
			return (List<Mpackageinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public Mpackageinfo loadCurMaster(String strCurCompId,String strCurPackageNo)
	{
		try
		{
			String strSql=" From Mpackageinfo mpackageinfo where compid='"+strCurCompId+"' and packageno='"+strCurPackageNo+"'  ";
			List<Mpackageinfo> ls= (List<Mpackageinfo>)this.amn_Dao.findByHql(strSql);
			if(ls!=null && ls.size()>0)
			{
				ls.get(0).setUsedate(CommonTool.getDateMask(ls.get(0).getUsedate()));
				return ls.get(0);
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public boolean validatePackage(String strCurCompId,String strCurPackageNo)
	{
		try
		{
			String strSql=" select 1 from  mpackageinfo where compid='"+strCurCompId+"' and packageno='"+strCurPackageNo+"'  ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			{
				public Boolean anlyResultSet(ResultSet rs)
				{
					boolean returnValue = true;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=false;
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  false;
					}
					return returnValue;
				}
			};
			boolean isexistsFlag=(Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return isexistsFlag;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean validateProject(String strCurCompId,String strCurProjectNo,StringBuffer nameBuffer,StringBuffer priceBuffer)
	{
		try
		{
			Projectinfo record=this.dataTool.loadProjectInfo(strCurCompId,strCurProjectNo);
			if(record==null)
			{
				return false;
			}
			else
			{
				nameBuffer.append(record.getPrjname());
				priceBuffer.append(record.getSaleprice().doubleValue());
				return true;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postDownByCompid(String strCompId,String strDownCurCompId,String strCurPackageNo)
	{
		try
		{
			String strSql="delete mpackageinfo from mpackageinfo,compchaininfo where curcomp='"+strDownCurCompId+"' and relationcomp=compid and compid<>'"+strCompId+"' and packageno='"+strCurPackageNo+"' ";
			strSql=strSql+"	delete dmpackageinfo from dmpackageinfo,compchaininfo where curcomp='"+strDownCurCompId+"' and relationcomp=compid and compid<>'"+strCompId+"' and packageno='"+strCurPackageNo+"' ";
			strSql=strSql+" insert mpackageinfo(compid,packageno,packagename,packageprice,paceageremark,usedate,usetype,ratetype,usemonths,wage)  ";
			strSql=strSql+"	select  relationcomp,packageno,packagename,packageprice,paceageremark,usedate,usetype,ratetype,usemonths,wage ";
			strSql=strSql+"	from mpackageinfo,compchaininfo where curcomp='"+strDownCurCompId+"' and compid='"+strCompId+"' and packageno='"+strCurPackageNo+"'   and relationcomp<>'"+strCompId+"' ";
            strSql=strSql+"	insert dmpackageinfo(compid,packageno,packageprono,packageprocount,packageproamt) ";
            strSql=strSql+"	select relationcomp,packageno,packageprono,packageprocount,packageproamt ";
            strSql=strSql+"	from  dmpackageinfo,compchaininfo where curcomp='"+strDownCurCompId+"' and  compid='"+strCompId+"' and packageno='"+strCurPackageNo+"'   and relationcomp<>'"+strCompId+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	
	 
}
