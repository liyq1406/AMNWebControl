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
import com.amani.model.Cardratetocostrate;

import com.amani.service.AMN_ModuleService;

import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class BC014Service  extends AMN_ModuleService{
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
	
	
	public boolean postCurMaster(List<Cardchangecostrate> masters) {
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
	
	public List<Cardratetocostrate> loadRateCostRate(String strCurCompId)
	{
		String strSql="select parentcodekey,parentcodevalue,startdate,enddate,costrate,changerate " +
				" from commoninfo  a left join cardratetocostrate on compid='"+strCurCompId+"' and projecttypeid=parentcodekey " +
				" where infotype='XMTJ'";
		AnlyResultSet<List<Cardratetocostrate>> analysis = new AnlyResultSet<List<Cardratetocostrate>>()
		{
			public List<Cardratetocostrate> anlyResultSet(ResultSet rs)
			{
				List<Cardratetocostrate> returnValue = new ArrayList();
				Cardratetocostrate record=null;
				try
				{
					while(rs != null && rs.next()==true)
					{
						record=new Cardratetocostrate();
						record.setBprojecttypeid(CommonTool.FormatString(rs.getString("parentcodekey")));
						record.setBprojecttypename(CommonTool.FormatString(rs.getString("parentcodevalue")));
						record.setStartdate(CommonTool.getDateMask(rs.getString("startdate")));
						record.setEnddate(CommonTool.getDateMask(rs.getString("enddate")));
						record.setCostrate(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costrate")))));
						record.setChangerate(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changerate")))));
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
		List<Cardratetocostrate>  ls= (List<Cardratetocostrate>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return ls;
	}
	public List<Cardchangecostrate> loadMaster(String strCurCompId,String strCurProjecttypeid,String accountType)
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
					     " left join cardchangecostrate  b on a.cardtypeno=b.cardtypeno and b.compid='"+strCurCompId+"' " +
					     " and projecttypeid='"+strCurProjecttypeid+"' and acounttypeno='"+accountType+"'" +
					     " where cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and curcomp=  cardtypesource and relationcomp='"+strCurCompId+"'   ";
			AnlyResultSet<List<Cardchangecostrate>> analysis = new AnlyResultSet<List<Cardchangecostrate>>()
			{
				public List<Cardchangecostrate> anlyResultSet(ResultSet rs)
				{
					List<Cardchangecostrate> returnValue = new ArrayList();
					Cardchangecostrate record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardchangecostrate();
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
			List<Cardchangecostrate>  ls= (List<Cardchangecostrate>)this.amn_Dao.executeQuery_ex(strSql,analysis);
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
			strSql=strSql+" delete cardchangecostrate where compid='"+strCompId+"' and projecttypeid<>'"+strPrjType+"' and acounttypeno='"+acounttypeno+"' ";
			strSql=strSql+" insert cardchangecostrate(compid,projecttypeid,acounttypeno,cardtypeno,startdate,enddate,costrate) ";
			strSql=strSql+" select compid,parentcodekey,acounttypeno,cardtypeno,startdate,enddate,costrate " +
					" from cardchangecostrate, commoninfo " +
					"  where compid='"+strCompId+"' and projecttypeid="+strPrjType+" and acounttypeno='"+acounttypeno+"' " +
					" and infotype='XMTJ' and parentcodekey<>'"+strPrjType+"' ";
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
			strSql=strSql+" delete cardchangecostrate where compid='"+strCompId+"' and projecttypeid='"+strDownCurProjecttypeid+"' and acounttypeno='"+acounttypeno+"' ";
			strSql=strSql+" insert cardchangecostrate(compid,projecttypeid,acounttypeno,cardtypeno,startdate,enddate,costrate) ";
			strSql=strSql+" select compid,'"+strDownCurProjecttypeid+"',acounttypeno,cardtypeno,startdate,enddate,costrate from cardchangecostrate where compid='"+strCompId+"' and projecttypeid="+strPrjType+" and acounttypeno='"+acounttypeno+"'  ";
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
			strSql=strSql+" delete cardchangecostrate where compid='"+strDownCurCompId+"' ";
			strSql=strSql+" insert cardchangecostrate(compid,projecttypeid,acounttypeno,cardtypeno,startdate,enddate,costrate) ";
			strSql=strSql+" select '"+strDownCurCompId+"',projecttypeid,acounttypeno,cardtypeno,startdate,enddate,costrate from cardchangecostrate where compid='"+strCompId+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean updateChangeRate(String strCompId,String strPrjType,String strStartDate,String strEndDate,double costrate,double changerate)
	{
		try
		{
			String strSql=" delete cardratetocostrate where compid='"+strCompId+"' and projecttypeid='"+strPrjType+"' ";
			strSql=strSql+" insert cardratetocostrate(compid,projecttypeid,startdate,enddate,costrate,changerate ) " +
					" values('"+strCompId+"','"+strPrjType+"','"+CommonTool.setDateMask(strStartDate)+"','"+CommonTool.setDateMask(strEndDate)+"',"+costrate+","+changerate+" ) ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean  handCopyInfo(String strCompId,String strCopyInfo)
	{
		try
		{
			String strCurCompId="";
			String strSql="";
			String[] lsComps=strCopyInfo.split(";");
			if(lsComps!=null && lsComps.length>0)
			{
				for(int i=0;i<lsComps.length;i++)
				{
					if(!lsComps[i].equals("") && !lsComps[i].equals(strCompId))
					{
						strCurCompId=lsComps[i];
						strSql=strSql+" delete cardratetocostrate where compid='"+strCurCompId+"'  ";
						strSql=strSql+" insert cardratetocostrate(compid,projecttypeid,startdate,enddate,costrate,changerate ) " +
								" select '"+strCurCompId+"',projecttypeid,startdate,enddate,costrate,changerate " +
								" from cardratetocostrate where compid='"+strCompId+"' ";
					}
				}
			}
			if(!strSql.equals(""))
				return this.amn_Dao.executeSql(strSql);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
}
