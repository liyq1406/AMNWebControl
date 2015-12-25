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

import com.amani.model.Cardchangecostrate;
import com.amani.model.Cardchangerule;
import com.amani.model.CardchangeruleId;
import com.amani.model.Projectcostinfo;
import com.amani.model.Projectinfo;

import com.amani.service.AMN_ModuleService;

import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class BC018Service  extends AMN_ModuleService{
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
	
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		try
		{
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public List<Projectinfo> loadCurProjectinfoByType(String strPrjType)
	{
		try
		{
			String strSql=" select prjno,prjname,prjtype from projectnameinfo where prjreporttype='"+strPrjType+"' ";
			 AnlyResultSet<List<Projectinfo>> analysis = new AnlyResultSet<List<Projectinfo>>()
				{
					public List<Projectinfo> anlyResultSet(ResultSet rs)
					{
						List<Projectinfo> returnValue = new ArrayList();
						Projectinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Projectinfo();
								record.setBprjno(CommonTool.FormatString(rs.getString("prjno")));
								record.setPrjname(CommonTool.FormatString(rs.getString("prjname")));
								record.setPrjtype(CommonTool.FormatString(rs.getString("prjtype")));
								returnValue.add(record);
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						return returnValue;
					}
				};
			List<Projectinfo> ls=(List<Projectinfo> )this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Projectcostinfo> loadCurProjectcostinfo(String strPrjNo)
	{
		try
		{
			String strSql=" select a.goodsno,b.goodsname,b.saleunit,b.goodspricetype,a.costunitcount from projectcostinfo a,goodsinfo  b " +
					" where a.goodsno=b.goodsno and a.prjno='"+strPrjNo+"' ";
			 AnlyResultSet<List<Projectcostinfo>> analysis = new AnlyResultSet<List<Projectcostinfo>>()
				{
					public List<Projectcostinfo> anlyResultSet(ResultSet rs)
					{
						List<Projectcostinfo> returnValue = new ArrayList();
						Projectcostinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Projectcostinfo();
								record.setGoodsno(CommonTool.FormatString(rs.getString("goodsno")));
								record.setGoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setGoodsunit(CommonTool.FormatString(rs.getString("saleunit")));
								record.setGoodstype(CommonTool.FormatString(rs.getString("goodspricetype")));
								record.setCostunitcount(CommonTool.FormatDouble(rs.getDouble("costunitcount")));
								returnValue.add(record);
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						return returnValue;
					}
				};
			List<Projectcostinfo> ls=(List<Projectcostinfo> )this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean postPrjCostInfo(String strPrjNo,String strGoodsNo,double costCount)
	{
		try
		{
			String strSql=" insert projectcostinfo(prjno,goodsno,costunitcount)" +
					" values('"+strPrjNo+"','"+strGoodsNo+"',"+costCount+") ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean deletePrjCostInfo(String strPrjNo,String strGoodsNo)
	{
		try
		{
			String strSql=" delete  projectcostinfo where prjno='"+strPrjNo+"' and goodsno='"+strGoodsNo+"'  ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
}
