package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;


import com.amani.model.Dstafftargetinfo;
import com.amani.model.Mstafftargetinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC019Service extends AMN_ReportService{
	
	public List<Mstafftargetinfo> loadDateSetByCompId(String strCompId,String strDateFrom,int billstate)
	{
		try
		{
			String strSql=" select entrycompid,entrybillid,handcompid,compname,handstaffid,handstaffinid,staffname,targeamt,targeflag,conditionnum,billflag,startdate,enddate,subsidycondition,subsidyconditiontext,targemode,targeyejitype " +
					" from mstafftargetinfo a,staffinfo b,companyinfo c,compchaininfo d " +
					" where a.handstaffinid=manageno and c.compno=a.handcompid  " +
					"   and a.handcompid=d.relationcomp and curcomp='"+strCompId+"' " +
					" and startdate<='"+strDateFrom.replace("-","")+"' and enddate>='"+strDateFrom.replace("-","")+"' and (isnull(billflag,0)="+billstate+" or "+billstate+"=4) ";
			AnlyResultSet<List<Mstafftargetinfo>> analysis = new AnlyResultSet<List<Mstafftargetinfo>>() {
				public List<Mstafftargetinfo> anlyResultSet(ResultSet rs) {
					List<Mstafftargetinfo> returnValue = new ArrayList();
					Mstafftargetinfo bean=null;
					try {
						while (rs != null && rs.next() == true) {
							 bean=new Mstafftargetinfo();
							 bean.setBentrycompid(CommonTool.FormatString(rs.getString("entrycompid")));
							 bean.setBentrybillid(CommonTool.FormatString(rs.getString("entrybillid")));
							 bean.setHandcompid(CommonTool.FormatString(rs.getString("handcompid")));
							 bean.setHandcompname(CommonTool.FormatString(rs.getString("compname")));
							 bean.setHandstaffid(CommonTool.FormatString(rs.getString("handstaffid")));
							 bean.setHandstaffinid(CommonTool.FormatString(rs.getString("handstaffinid")));
							 bean.setHandstaffname(CommonTool.FormatString(rs.getString("staffname")));
							 bean.setTargeamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("targeamt")))));
							 bean.setTargeflag(CommonTool.FormatInteger(rs.getInt("targeflag")));
							 bean.setTargemode(CommonTool.FormatInteger(rs.getInt("targemode")));
							 bean.setTargeyejitype(CommonTool.FormatInteger(rs.getInt("targeyejitype")));
							 bean.setConditionnum(CommonTool.FormatInteger(rs.getInt("conditionnum")));
							 bean.setBillflag(CommonTool.FormatInteger(rs.getInt("billflag")));
							 bean.setStartdate(CommonTool.FormatString(rs.getString("startdate")));
							 bean.setEnddate(CommonTool.FormatString(rs.getString("enddate")));
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
			List<Mstafftargetinfo> ls= (List<Mstafftargetinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Dstafftargetinfo> loadDetialByCompId(String strCompId,String strBillId)
	{
		try
		{
			String strSql="select targettype,targetamt,startdate,enddate from dstafftargetinfo where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
			AnlyResultSet<List<Dstafftargetinfo>> analysis = new AnlyResultSet<List<Dstafftargetinfo>>() {
				public List<Dstafftargetinfo> anlyResultSet(ResultSet rs) {
					List<Dstafftargetinfo> returnValue = new ArrayList();
					Dstafftargetinfo bean=null;
					try {
						while (rs != null && rs.next() == true) {
							 bean=new Dstafftargetinfo();
							 bean.setTargettype(CommonTool.FormatInteger(rs.getInt("targettype")));
							 bean.setTargetamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("targetamt"))))); 
							 bean.setStartdate(CommonTool.getDateMask(rs.getString("startdate")));
							 bean.setEnddate(CommonTool.getDateMask(rs.getString("enddate")));
							 returnValue.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Dstafftargetinfo> ls= (List<Dstafftargetinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
				strSql=" update mstafftargetinfo set billflag=1 where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
			
			}
			else if(ihandFlag==2)
			{
				strSql=" update mstafftargetinfo set billflag=2 where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
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
	public boolean handLsBill(List<Mstafftargetinfo> lsbean,int ihandFlag)
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
					strSql=strSql+" update mstafftargetinfo set billflag=1 where entrycompid='"+lsbean.get(i).getBentrycompid()+"' and entrybillid='"+lsbean.get(i).getBentrybillid()+"' ";
				}
				else if(ihandFlag==2)
				{
					strSql=strSql+" update mstafftargetinfo set billflag=2 where entrycompid='"+lsbean.get(i).getBentrycompid()+"' and entrybillid='"+lsbean.get(i).getBentrybillid()+"'  ";
				}
				else if(ihandFlag==3)
				{
					strSql=strSql+" update mstafftargetinfo set billflag=3 where entrycompid='"+lsbean.get(i).getBentrycompid()+"' and entrybillid='"+lsbean.get(i).getBentrybillid()+"'  ";
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
