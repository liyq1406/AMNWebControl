package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Compschedulinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class AC018Service  extends AMN_ModuleService{
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
		return true;
	}

	
	public List<Compschedulinfo> loadCurDataSet(String strCompId,String strMonth,String strEmpId)
	{
		try
		{
			String strSql=" exec upg_prepare_empscheduling_analysis '"+strCompId+"','"+strMonth+"','"+CommonTool.FormatString(strEmpId)+"' ";
			AnlyResultSet<List<Compschedulinfo>> analysis = new AnlyResultSet<List<Compschedulinfo>>() {
				public List<Compschedulinfo> anlyResultSet(ResultSet rs) {
					List<Compschedulinfo> returnValue = new ArrayList();
					Compschedulinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Compschedulinfo();
							record.setCompno(CommonTool.FormatString(rs.getString("compno")));
							record.setScheduldate(CommonTool.getDateMask(rs.getString("workdate")));
							record.setSchedulweek(CommonTool.FormatString(rs.getString("weekdays")));
							record.setTotalCount(CommonTool.FormatInteger(rs.getInt("ccount")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Compschedulinfo> ls= (List<Compschedulinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Compschedulinfo> loadCurDetialDataSetC(String strCompId,String strdate,String strEmpId)
	{
		try
		{
			String strSql="select ccompno='"+strCompId+"',scheduldate='"+strdate+"' ,a.schedulno,a.schedulname,a.fromtime,a.totime,ccount=COUNT(b.scheduldate) " +
					" from compscheduling a " +
					" left join compschedulinfo b  on  a.compno=b.compno and a.schedulno=b.schedulno and b.scheduldate='"+CommonTool.setDateMask(strdate)+"' and (isnull(schedulemp,'')='"+strEmpId+"' or '"+strEmpId+"'='') " +
					" where a.compno='"+strCompId+"' " +
					" group by a.schedulno,a.schedulname,a.fromtime,a.totime ";
			AnlyResultSet<List<Compschedulinfo>> analysis = new AnlyResultSet<List<Compschedulinfo>>() {
				public List<Compschedulinfo> anlyResultSet(ResultSet rs) {
					List<Compschedulinfo> returnValue = new ArrayList();
					Compschedulinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Compschedulinfo();
							record.setCompno(CommonTool.FormatString(rs.getString("ccompno")));
							record.setScheduldate(CommonTool.getDateMask(rs.getString("scheduldate")));
							record.setSchedulno(CommonTool.FormatString(rs.getString("schedulno")));
							record.setSchedulname(CommonTool.FormatString(rs.getString("schedulname")));
							record.setFromtime(CommonTool.getTimeMask(rs.getString("fromtime"), 2));
							record.setTotime(CommonTool.getTimeMask(rs.getString("totime"), 2));
							record.setTotalCount(CommonTool.FormatInteger(rs.getInt("ccount")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Compschedulinfo> ls= (List<Compschedulinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Compschedulinfo> loadCurDetialDataSet(String strCompId,String strDate,String strSchedulno,String strEmpId)
	{
		try
		{
			String strSql="select a.compno ,a.schedulno,a.scheduldate,b.schedulname,a.fromtime,a.totime,a.schedulemp,a.schedulempname,a.schedulempmanger,a.schedulmark " +
					" from compscheduling b,  compschedulinfo a " +
					" where  a.compno=b.compno and a.schedulno=b.schedulno  " +
					" and a.compno='"+strCompId+"' and a.scheduldate='"+CommonTool.setDateMask(strDate)+"' and a.schedulno='"+strSchedulno+"'  and (isnull(schedulemp,'')='"+strEmpId+"' or '"+strEmpId+"'='')  " +
					" order by a.schedulno ";
			AnlyResultSet<List<Compschedulinfo>> analysis = new AnlyResultSet<List<Compschedulinfo>>() {
				public List<Compschedulinfo> anlyResultSet(ResultSet rs) {
					List<Compschedulinfo> returnValue = new ArrayList();
					Compschedulinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Compschedulinfo();
							record.setCompno(CommonTool.FormatString(rs.getString("compno")));
							record.setSchedulno(CommonTool.FormatString(rs.getString("schedulno")));
							record.setSchedulname(CommonTool.FormatString(rs.getString("schedulname")));
							record.setScheduldate(CommonTool.getDateMask(rs.getString("scheduldate")));
							record.setSchedulemp(CommonTool.FormatString(rs.getString("schedulemp")));
							record.setSchedulempname(CommonTool.FormatString(rs.getString("schedulempname")));
							record.setSchedulempmanger(CommonTool.FormatString(rs.getString("schedulempmanger")));
							record.setFromtime(CommonTool.getTimeMask(rs.getString("fromtime"), 2));
							record.setTotime(CommonTool.getTimeMask(rs.getString("totime"), 2));
							record.setSchedulmark(CommonTool.FormatString(rs.getString("schedulmark")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Compschedulinfo> ls= (List<Compschedulinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public boolean postSchedulInfo(String strCompId,String strDate,String strSchedulno,List<Compschedulinfo> lsBean)
	{
		try
		{
			String strSql=" delete compschedulinfo where compno='"+strCompId+"' and schedulno='"+strSchedulno+"' and scheduldate='"+CommonTool.setDateMask(strDate)+"' ";
			if(lsBean!=null && lsBean.size()>0)
			{
				for(int i=0;i<lsBean.size();i++)
				{
					strSql=strSql+" insert compschedulinfo(compno,schedulno,scheduldate,schedulemp,schedulempname,schedulempmanger,fromtime,totime,schedulmark ) " +
							" values('"+strCompId+"','"+strSchedulno+"','"+CommonTool.setDateMask(strDate)+"','"+lsBean.get(i).getSchedulemp()+"','"+lsBean.get(i).getSchedulempname()+"','"+lsBean.get(i).getSchedulempmanger()+"','"+CommonTool.setTimeMask(lsBean.get(i).getFromtime(),2)+"','"+CommonTool.setTimeMask(lsBean.get(i).getTotime()+"','"+lsBean.get(i).getSchedulmark(),2)+"')";
				}
			}
			return this.amn_Dao.executeSql(strSql);
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
		// TODO Auto-generated method stub
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
	
	public boolean deleteStaffScheduling(String strCompId,String strDate,String strSchedulno,String strInid)
	{
		try
		{
			String strSql="delete compschedulinfo where compno='"+strCompId+"' and schedulno='"+strSchedulno+"' and scheduldate='"+CommonTool.setDateMask(strDate)+"' and schedulempmanger='"+strInid+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean downStaffSchelInfo(String strCompId,String strTargetDate,String strFromDate,String strToDate)
	{
		try
		{
			String strSql=" delete compschedulinfo where compno='"+strCompId+"' and scheduldate between '"+CommonTool.setDateMask(strFromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"'   and scheduldate<>'"+CommonTool.setDateMask(strTargetDate)+"' ";
			int subDate=CommonTool.dateSubDate(CommonTool.setDateMask(strFromDate),CommonTool.setDateMask(strToDate));
			int i=0;
			String strTargetDateXF=strFromDate;
			while( i<=subDate)
			{
			
				if(CommonTool.setDateMask(strTargetDateXF).equals(CommonTool.setDateMask(strTargetDate)))
				{
					i++;
					strTargetDateXF=CommonTool.datePlusDay(strTargetDateXF,1);
					continue;
				}
				System.out.println(strTargetDateXF);
				strSql=strSql+" insert compschedulinfo(compno,schedulno,scheduldate,schedulemp,schedulempname,schedulempmanger,fromtime,totime,schedulmark)" +
						" select compno,schedulno,'"+CommonTool.setDateMask(strTargetDateXF)+"',schedulemp,schedulempname,schedulempmanger,fromtime,totime,schedulmark from compschedulinfo where compno='"+strCompId+"' and scheduldate='"+CommonTool.setDateMask(strTargetDate)+"' ";
				i++;
				strTargetDateXF=CommonTool.datePlusDay(strTargetDateXF,1);
			}
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
}
