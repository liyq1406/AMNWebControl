package com.amani.service.BaseInfoControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Cardcostgoodsrate;
import com.amani.model.Gooddiscount;
import com.amani.model.GooddiscountId;


import com.amani.service.AMN_ModuleService;

import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class BC015Service  extends AMN_ModuleService{
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
			this.amn_Dao.saveOrUpdate(curMaster);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean postCurMaster(List<Cardcostgoodsrate> masters) {
		try
		{
			this.amn_Dao.saveOrUpdateAll(masters);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postGoodIsCard(List<Gooddiscount> lsGood)
	{
		try
		{
			this.amn_Dao.saveOrUpdateAll(lsGood);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public List<Gooddiscount> loadGooddisInfo(String strCompId)
	{
		List<Gooddiscount> lsGood=new ArrayList<Gooddiscount>();
		
		String strSql="select * from commoninfo left join gooddiscount on(parentcodekey=bprojecttypeid and compid='"+strCompId+"') where infotype='WPTJ' ";
		ResultSet rs=null;
		try
		{
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					Gooddiscount good=new Gooddiscount();
					good.setBprojecttypename(rs.getString("parentcodevalue"));
					good.setId(new GooddiscountId(rs.getString("compid"),rs.getString("parentcodekey")));
					good.setIscard(rs.getInt("iscard"));
					good.setBprojecttypeid(rs.getString("parentcodekey"));
					lsGood.add(good);	
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lsGood;
	}

	public List<Cardcostgoodsrate> loadMaster(String strCurCompId,String strCurGoodstypeid,String accountType)
	{
		try
		{
			String strModeId=this.dataTool.loadSysParam(strCurCompId,"SP063");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.dataTool.loadCompLvl(strCurCompId);
			if(compLvl==2)
			{
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			
			String strSql="select a.cardtypeno,a.cardtypename,b.startdate,b.enddate,b.costrate,acounttypeno='"+accountType+"'" +
					     " from compchaininfo,cardtypeinfo a" +
					     " left join Cardcostgoodsrate  b on a.cardtypeno=b.cardtypeno and b.compid='"+strCurCompId+"' " +
					     " and goodstypeid='"+strCurGoodstypeid+"' and acounttypeno='"+accountType+"'" +
					     " where cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and curcomp=  cardtypesource and relationcomp='"+strCurCompId+"'   ";
			AnlyResultSet<List<Cardcostgoodsrate>> analysis = new AnlyResultSet<List<Cardcostgoodsrate>>()
			{
				public List<Cardcostgoodsrate> anlyResultSet(ResultSet rs)
				{
					List<Cardcostgoodsrate> returnValue = new ArrayList();
					Cardcostgoodsrate record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardcostgoodsrate();
							record.setBcardtypeno(CommonTool.FormatString(rs.getString("cardtypeno")));
							record.setCardtypename(CommonTool.FormatString(rs.getString("cardtypename")));
							record.setStartdate(CommonTool.getDateMask(rs.getString("startdate")));
							record.setEnddate(CommonTool.getDateMask(rs.getString("enddate")));
							record.setBacounttypeno(CommonTool.FormatString(rs.getString("acounttypeno")));
							if(CommonTool.FormatString(rs.getString("acounttypeno")).equals("999"))
								record.setBacounttypename("现金账户");
							else if(CommonTool.FormatString(rs.getString("acounttypeno")).equals("2"))
							{
								record.setBacounttypename("储值账户");
							}
							else if(CommonTool.FormatString(rs.getString("acounttypeno")).equals("3"))
							{
								record.setBacounttypename("积分账户");
							}
							else if(CommonTool.FormatString(rs.getString("acounttypeno")).equals("5"))
							{
								record.setBacounttypename("收购账户");
							}
							record.setCostrate(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costrate")))));
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
			List<Cardcostgoodsrate>  ls= (List<Cardcostgoodsrate>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean postDownLoadAllPrjType(String strCompId,String strPrjType,String acounttypeno)
	{
		try
		{
			String strSql=" insert sysoperationlog(userid,program,operation,operationdate,operationtime,origatedate,compid,keyvalue1)";
			strSql=strSql+" values('"+CommonTool.getLoginInfo("USERID")+"','BC014','L','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getCurrDate()+"','"+strCompId+"','')";
			strSql=strSql+" delete cardcostgoodsrate where compid='"+strCompId+"' and goodstypeid<>'"+strPrjType+"' and acounttypeno='"+acounttypeno+"' ";
			strSql=strSql+" insert cardcostgoodsrate(compid,goodstypeid,acounttypeno,cardtypeno,startdate,enddate,costrate) ";
			strSql=strSql+" select compid,parentcodekey,acounttypeno,cardtypeno,startdate,enddate,costrate " +
					" from cardcostgoodsrate, commoninfo " +
					"  where compid='"+strCompId+"' and goodstypeid="+strPrjType+" and acounttypeno='"+acounttypeno+"' " +
					" and infotype='WPTJ' and parentcodekey<>'"+strPrjType+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean downLoadByPrjType(String strCompId,String strPrjType,String strDownCurProjecttypeid,String acounttypeno)
	{
		try
		{
			String strSql=" insert sysoperationlog(userid,program,operation,operationdate,operationtime,origatedate,compid,keyvalue1)";
			strSql=strSql+" values('"+CommonTool.getLoginInfo("USERID")+"','BC014','L','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getCurrDate()+"','"+strCompId+"','"+strDownCurProjecttypeid+"')";
			strSql=strSql+" delete cardcostgoodsrate where compid='"+strCompId+"' and goodstypeid='"+strDownCurProjecttypeid+"' and acounttypeno='"+acounttypeno+"' ";
			strSql=strSql+" insert cardcostgoodsrate(compid,goodstypeid,acounttypeno,cardtypeno,startdate,enddate,costrate) ";
			strSql=strSql+" select compid,'"+strDownCurProjecttypeid+"',acounttypeno,cardtypeno,startdate,enddate,costrate from cardcostgoodsrate where compid='"+strCompId+"' and goodstypeid="+strPrjType+" and acounttypeno='"+acounttypeno+"'  ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	public boolean downLoadByCompNo(String strCompId,String strDownCurCompId)
	{
		try
		{
			String strSql=" insert sysoperationlog(userid,program,operation,operationdate,operationtime,origatedate,compid,keyvalue1)";
			strSql=strSql+" values('"+CommonTool.getLoginInfo("USERID")+"','BC014','L','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getCurrDate()+"','"+strCompId+"','')";
			strSql=strSql+" delete cardcostgoodsrate where compid='"+strDownCurCompId+"' ";
			strSql=strSql+" insert cardcostgoodsrate(compid,goodstypeid,acounttypeno,cardtypeno,startdate,enddate,costrate) ";
			strSql=strSql+" select '"+strDownCurCompId+"',goodstypeid,acounttypeno,cardtypeno,startdate,enddate,costrate from cardcostgoodsrate where compid='"+strCompId+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean copyGoodIsCard(String strCompId)
	{
		
		String strSql="";
		String strSql2="";
		boolean bRet=true;
		
		strSql="select * from compchaininfo where curcomp='"+strCompId+"' and curcomp<> relationcomp ";
		ResultSet rs=null;
		try
		{
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					strSql+=" delete gooddiscount where compid='"+rs.getString("relationcomp")+"'";
					
					strSql2+="insert into gooddiscount(compid,bprojecttypeid,iscard) select '"+rs.getString("relationcomp")+"',bprojecttypeid,iscard from gooddiscount where compid='"+strCompId+"'";
				}
			}
		
			//strSql+=" insert into gooddiscount(compid,bprojecttypeid,iscard) " +
				//" select relationcomp,bprojecttypeid,iscard from gooddiscount a, compchaininfo b where a.compid=b.relationcomp and curcomp='"+strCompId+"' and curcomp<> relationcomp ";
		
			bRet=this.amn_Dao.executeSql(strSql);
			bRet=this.amn_Dao.executeSql(strSql2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			bRet=false;
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return bRet;
		
	}
	
}
