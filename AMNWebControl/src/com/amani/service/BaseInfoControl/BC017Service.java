package com.amani.service.BaseInfoControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.Shopownerinfo;

import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class BC017Service  extends AMN_ModuleService{
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
		// TODO Auto-generated method stub
		return true;
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
	
	public boolean postStoreSetInfo(String strCompId,String strStaffId,String strStaffname,String strStaffinid,String strMonth,int  stafftype)
	{
		try
		{
			String strSql=" insert shopownerinfo(compid,staffid,staffname,staffinid,mmonth,stafftype) " +
					" values('"+strCompId+"','"+strStaffId+"','"+strStaffname+"','"+strStaffinid+"','"+strMonth.replace("-", "")+"',"+stafftype+") ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean deleteStoreSetInfo(String strCompId,String strStaffinid,String strMonth)
	{
		try
		{
			String strSql="delete shopownerinfo where compid='"+strCompId+"' and  staffinid='"+strStaffinid+"' and mmonth='"+strMonth.replace("-", "")+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean checkWoner(String strCompId,String strStaffinid,String strMonth)
	{
		try
		{
			String strSql=" select 1 from shopownerinfo where compid='"+strCompId+"' and  staffinid='"+strStaffinid+"' and mmonth='"+strMonth.replace("-", "")+"' ";
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
			return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	
	public List<Shopownerinfo> loadShopownerinfoByMonth(String strMonth)
	{
		String strSql="select compid,compname,staffid,staffname,staffinid,mmonth,stafftype " +
				" from shopownerinfo,companyinfo  where mmonth='"+strMonth+"' and compid=compno order by compid ";
		AnlyResultSet<List<Shopownerinfo>> analysis = new AnlyResultSet<List<Shopownerinfo>>()
		{
			public List<Shopownerinfo> anlyResultSet(ResultSet rs)
			{
				List<Shopownerinfo> returnValue = new ArrayList();
				Shopownerinfo record=null;
				try
				{
					while(rs != null && rs.next()==true)
					{
						record=new Shopownerinfo();
						record.setCompid(CommonTool.FormatString(rs.getString("compid")));
						record.setCompname(CommonTool.FormatString(rs.getString("compname")));
						record.setStaffid(CommonTool.FormatString(rs.getString("staffid")));
						record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
						record.setStaffinid(CommonTool.FormatString(rs.getString("staffinid")));
						record.setMmonth(CommonTool.FormatString(rs.getString("mmonth")));
						record.setStafftype(CommonTool.FormatInteger(rs.getInt("stafftype")));
						returnValue.add(record);
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  null;
				}
				return returnValue;
			}
		};
		List<Shopownerinfo> ls =(List<Shopownerinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return ls;
	}
	

	
}
