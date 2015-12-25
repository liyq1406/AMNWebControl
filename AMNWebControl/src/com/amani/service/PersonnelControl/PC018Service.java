package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;


import com.amani.model.Mstaffsubsidyinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC018Service extends AMN_ReportService{
	
	public List<Mstaffsubsidyinfo> loadDateSetByCompId(String strCompId,String strDateFrom,int billstate,String strSearchStaffNo)
	{
		try
		{
			String strSql=" select entrycompid,entrybillid,handcompid,compname,handstaffid,handstaffinid,staffname,subsidyamt,subsidyflag,conditionnum,billflag,startdate,enddate,subsidycondition,subsidyconditiontext,appstaffname,checkstaffname " +
					" from mstaffsubsidyinfo a,staffinfo b,companyinfo c,compchaininfo d " +
					" where a.handstaffinid=manageno and  isnull(stafftype,0)=0  and c.compno=a.handcompid  " +
					"   and a.handcompid=d.relationcomp and curcomp='"+strCompId+"'" +
					"  and (handstaffid='"+strSearchStaffNo+"' or '"+strSearchStaffNo+"'='') " +
					" and startdate<='"+strDateFrom.replace("-","")+"' and enddate>='"+strDateFrom.replace("-","")+"' and (isnull(billflag,0)="+billstate+" or "+billstate+"=4) ";
			AnlyResultSet<List<Mstaffsubsidyinfo>> analysis = new AnlyResultSet<List<Mstaffsubsidyinfo>>() {
				public List<Mstaffsubsidyinfo> anlyResultSet(ResultSet rs) {
					List<Mstaffsubsidyinfo> returnValue = new ArrayList();
					Mstaffsubsidyinfo bean=null;
					try {
						while (rs != null && rs.next() == true) {
							 bean=new Mstaffsubsidyinfo();
							 bean.setBentrycompid(CommonTool.FormatString(rs.getString("entrycompid")));
							 bean.setBentrybillid(CommonTool.FormatString(rs.getString("entrybillid")));
							 bean.setHandcompid(CommonTool.FormatString(rs.getString("handcompid")));
							 bean.setHandcompname(CommonTool.FormatString(rs.getString("compname")));
							 bean.setHandstaffid(CommonTool.FormatString(rs.getString("handstaffid")));
							 bean.setHandstaffinid(CommonTool.FormatString(rs.getString("handstaffinid")));
							 bean.setHandstaffname(CommonTool.FormatString(rs.getString("staffname")));
							 bean.setSubsidyamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("subsidyamt")))));
							 bean.setSubsidyflag(CommonTool.FormatInteger(rs.getInt("subsidyflag")));
							 bean.setConditionnum(CommonTool.FormatInteger(rs.getInt("conditionnum")));
							 bean.setBillflag(CommonTool.FormatInteger(rs.getInt("billflag")));
							 bean.setStartdate(CommonTool.FormatString(rs.getString("startdate")));
							 bean.setEnddate(CommonTool.FormatString(rs.getString("enddate")));
							 bean.setAppstaffname(CommonTool.FormatString(rs.getString("appstaffname")));
							 bean.setCheckstaffname(CommonTool.FormatString(rs.getString("checkstaffname")));
							 bean.setSubsidycondition(CommonTool.FormatString(rs.getString("subsidycondition")));
							 bean.setSubsidyconditiontext(CommonTool.FormatString(rs.getString("subsidyconditiontext")));
						     returnValue.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Mstaffsubsidyinfo> ls= (List<Mstaffsubsidyinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//1审核 2 人事驳回
	public boolean handBill(String strCompId,String strBillId,int ihandFlag)
	{
		try
		{
			String strSql="";
			if(ihandFlag==1)
			{
				strSql=" update mstaffsubsidyinfo set billflag=1 where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
			
			}
			else if(ihandFlag==2)
			{
				strSql=" update mstaffsubsidyinfo set billflag=2 where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
			}
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
				ex.printStackTrace();
				return false;
		}
	}
	
	//1审核 2 人事驳回
	public boolean handLsBill(List<Mstaffsubsidyinfo> lsbean,int ihandFlag)
	{
		try
		{
			String strSql="";
			if(lsbean==null || lsbean.size()==0)
			{
				return true;
			}
			for(int i=0;i<lsbean.size();i++)
			{
				if(ihandFlag==1)
				{
					strSql=strSql+" update mstaffsubsidyinfo set billflag=1 where entrycompid='"+lsbean.get(i).getBentrycompid()+"' and entrybillid='"+lsbean.get(i).getBentrybillid()+"' ";
				}
				else if(ihandFlag==2)
				{
					strSql=strSql+" update mstaffsubsidyinfo set billflag=2 where entrycompid='"+lsbean.get(i).getBentrycompid()+"' and entrybillid='"+lsbean.get(i).getBentrybillid()+"'  ";
				}
				else if(ihandFlag==3)
				{
					strSql=strSql+" update mstaffsubsidyinfo set billflag=3 where entrycompid='"+lsbean.get(i).getBentrycompid()+"' and entrybillid='"+lsbean.get(i).getBentrybillid()+"'  ";
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
	
}
