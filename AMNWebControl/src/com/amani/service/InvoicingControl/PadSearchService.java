package com.amani.service.InvoicingControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.SendBillInfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;

@Service
public class PadSearchService extends AMN_ModuleService  {

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
	
	public Map  loadSendBillComps()
	{
		try
		{
			String strSql=" select gta03c,gae03c from gtm01,gam05 where gta07i=0 and gae01c=gta03c ";
			AnlyResultSet<Map> analysis = new AnlyResultSet<Map >()
			{
				public Map  anlyResultSet(ResultSet rs)
				{
					Map  returnValue = new TreeMap() ;
					try
					{
					while(rs != null && rs.next()==true)
					{
						returnValue.put(CommonTool.FormatString(rs.getString("gta03c")),CommonTool.FormatString(rs.getString("gta03c"))+"-"+CommonTool.FormatString(rs.getString("gae03c")));
					}
					
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			return (Map )this.amn_PADDao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Map loadSendBillsByComp(String strCompId)
	{
		try
		{
			String strSql=" select gta14d,gta01c,gta18c from gtm01 where gta07i=0 and gta03c='"+strCompId+"'  ";
			AnlyResultSet<Map> analysis = new AnlyResultSet<Map>()
			{
				public Map anlyResultSet(ResultSet rs)
				{
					Map returnValue = new TreeMap() ;
					try
					{
						while(rs != null && rs.next()==true)
						{
							returnValue.put(CommonTool.FormatString(rs.getString("gta01c")),CommonTool.FormatString(rs.getString("gta01c"))+"["+CommonTool.getDateMask(rs.getString("gta14d"))+"]"+"["+CommonTool.FormatString(rs.getString("gta18c"))+"]");
						}					
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			return (Map)this.amn_PADDao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<SendBillInfo> loadDetialByBillId(String strBillid)
	{
		String strSql="select gta03c,gtb01c,gae03c,gtb03c,gfa03c,gtb07f,gtb05c from gtm01,gtm02,gfm01,gam05" +
				" where gae01c=gta03c and gtb00c=gfa00c and gfa01c=gtb03c " +
				"  and  gtb00c='001' and gtb01c='"+strBillid+"' and gta00c=gtb00c and gta01c=gtb01c ";
		AnlyResultSet<List<SendBillInfo>> analysis = new AnlyResultSet<List<SendBillInfo>>()
		{
			public List<SendBillInfo> anlyResultSet(ResultSet rs)
			{
				List<SendBillInfo> returnValue = new ArrayList() ;
				SendBillInfo bean=null;
				try
				{
					while(rs != null && rs.next()==true)
					{
						bean=new SendBillInfo();
						bean.setStrCompName(rs.getString("gae03c"));
						bean.setStrBillId(rs.getString("gtb01c"));
						bean.setStrGoodsId(rs.getString("gtb03c"));
						bean.setStrGoodsName(rs.getString("gfa03c"));
						bean.setStrGoodsUnit(rs.getString("gtb05c"));
						bean.setOrderAppCount(CommonTool.FormatDouble(rs.getDouble("gtb07f")));
						returnValue.add(bean);
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				return returnValue;
			}
		};
		return (List<SendBillInfo>)this.amn_PADDao.executeQuery_ex(strSql,analysis);
	}
	
	public Map loadSendBillsByStore(String strSearchStoreId)
	{
		try
		{
			String strSql=" select gta14d,gta01c from gtm01 where gta07i=0 and (gta18c='"+strSearchStoreId+"' or '*'='"+strSearchStoreId+" ')  ";
			AnlyResultSet<Map> analysis = new AnlyResultSet<Map>()
			{
				public Map anlyResultSet(ResultSet rs)
				{
					Map returnValue = new TreeMap() ;
					try
					{
					while(rs != null && rs.next()==true)
					{
						returnValue.put(CommonTool.FormatString(rs.getString("gta01c")),CommonTool.FormatString(rs.getString("gta01c"))+"["+CommonTool.getDateMask(rs.getString("gta14d"))+"]");
					}
					
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			return (Map)this.amn_PADDao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean handSendBillState(String strBillId)
	{
		try
		{
			String strSql="update gtm01 set gta07i=5 where gta00c='001' and gta01c='"+strBillId+"' ";
			return this.amn_PADDao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

}
