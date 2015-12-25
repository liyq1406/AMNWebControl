package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;


import com.amani.model.Dstaffrewardinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC020Service extends AMN_ReportService{
	
	public List<Dstaffrewardinfo> loadDStaffrewardinfos(String strCompId,String strDateFrom,String strDateTo,int billstate,String strSearchType,String strSearchStaffNo)
	{
		try
		{
			String strSql="select b.entrycompid, b.entrybillid, b.entryseqno,handcompid,compname,handstaffid,handstaffinid,staffname,entryreason,entrydate,entrytype,rewardamt,b.billflag,entryflag,operationer,operationdate " +
					"  From  mstaffrewardinfo a,dstaffrewardinfo b, compchaininfo, companyinfo c,staffinfo d " +
					" where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid  " +
					" and curcomp='"+strCompId+"' and relationcomp=handcompid " +
					" and isnull(invalid,0)=0 and (isnull(entrydate,'') between '"+strDateFrom+"' and '"+strDateTo+"' or '"+strDateFrom+"'='' )  " +
					" and handcompid=c.compno and  manageno=handstaffinid and  isnull(stafftype,0)=0  " +
					" and (isnull(b.billflag,0)="+billstate+" or "+billstate+"=4 ) " +
					" and (isnull(b.handstaffid,'')='"+strSearchStaffNo+"' or '"+strSearchStaffNo+"'='' ) " +
					" and (isnull(b.entrytype,'')='"+strSearchType+"' or '"+strSearchType+"'='' ) " +
					" order by handcompid,entrydate ";
			
			AnlyResultSet<List<Dstaffrewardinfo>> analysis = new AnlyResultSet<List<Dstaffrewardinfo>>() {
				public List<Dstaffrewardinfo> anlyResultSet(ResultSet rs) {
					List<Dstaffrewardinfo> returnValue = new ArrayList();
					Dstaffrewardinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Dstaffrewardinfo();
							record.setBentrycompid(CommonTool.FormatString(rs.getString("entrycompid")));
							record.setBentrybillid(CommonTool.FormatString(rs.getString("entrybillid")));
							record.setBentryseqno(CommonTool.FormatDouble(rs.getDouble("entryseqno")));
							record.setHandstaffid(CommonTool.FormatString(rs.getString("handstaffid")));
							record.setHandstaffinid(CommonTool.FormatString(rs.getString("handstaffinid")));
							record.setEntryreason(CommonTool.FormatString(rs.getString("entryreason")));
							record.setEntrydate(CommonTool.getDateMask(rs.getString("entrydate")));
							record.setEntrytype(CommonTool.FormatString(rs.getString("entrytype")));
							record.setOperationer(CommonTool.FormatString(rs.getString("operationer")));
							record.setOperationdate(CommonTool.getDateMask(rs.getString("operationdate")));
							record.setEntryflag(CommonTool.FormatInteger(rs.getInt("entryflag")));
							record.setRewardamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("rewardamt")))));
							record.setHandstaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setHandcompid(CommonTool.FormatString(rs.getString("handcompid")));
							record.setHandcompname(CommonTool.FormatString(rs.getString("compname")));
							record.setBillflag(CommonTool.FormatInteger(rs.getInt("billflag")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Dstaffrewardinfo> ls= (List<Dstaffrewardinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
	public boolean handBill(String strCompId,String strBillId,double entryseqno,int ihandFlag)
	{
		try
		{
			String strSql="";
			if(ihandFlag==1)
			{
				strSql=" update dstaffrewardinfo set billflag=1 where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' and entryseqno="+entryseqno+" ";
			
			}
			else if(ihandFlag==2)
			{
				strSql=" update dstaffrewardinfo set billflag=2 where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' and entryseqno="+entryseqno+"  ";
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
	public boolean handLsBill(List<Dstaffrewardinfo> lsbean,int ihandFlag)
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
					strSql=strSql+" update dstaffrewardinfo set billflag=1 where entrycompid='"+lsbean.get(i).getBentrycompid()+"' and entrybillid='"+lsbean.get(i).getBentrybillid()+"' and entryseqno="+lsbean.get(i).getBentryseqno()+" ";
				}
				else if(ihandFlag==2)
				{
					strSql=strSql+" update dstaffrewardinfo set billflag=2 where entrycompid='"+lsbean.get(i).getBentrycompid()+"' and entrybillid='"+lsbean.get(i).getBentrybillid()+"' and entryseqno="+lsbean.get(i).getBentryseqno()+" ";
				}
				else if(ihandFlag==3)
				{
					strSql=strSql+" update dstaffrewardinfo set billflag=3 where entrycompid='"+lsbean.get(i).getBentrycompid()+"' and entrybillid='"+lsbean.get(i).getBentrybillid()+"' and entryseqno="+lsbean.get(i).getBentryseqno()+" ";
				}
			}
			System.out.println(strSql);
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
				ex.printStackTrace();
				return false;
		}
	}
	
	public boolean validateStaffDeb(String strCompId,String strStaffInid,String handdate,double handAmt)
	{
		try
		{
			String strSql=" select staffyeji=sum(isnull(staffyeji,0)) from staff_work_salary " +
					" where compid='"+strCompId+"' and person_inid='"+strStaffInid+"' and substring(salary_date,1,6)='"+CommonTool.setDateMask(handdate).substring(0,6)+"' ";
			AnlyResultSet<Double> analysis = new AnlyResultSet<Double>() {
				public Double anlyResultSet(ResultSet rs) {
					double returnValue=0;
					try {
							
							if (rs != null && rs.next() == true) {
								returnValue=CommonTool.FormatDouble(rs.getDouble("staffyeji"));
							}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =0;
					}
					return returnValue;
				}
			};
			//总提成
			double staffyeji= (Double) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			
			
			strSql="  select rewardamt=sum( case when isnull(entrytype,'') in ('01','02','04','05','07','08','09','12','15') then  isnull(rewardamt,0)*(-1) " +
					"  when isnull(entrytype,'')='10' and isnull(a.entryflag,0)=1 then 	isnull(rewardamt,0)*(-1) else isnull(rewardamt,0) end )" +
					" from mstaffrewardinfo a,dstaffrewardinfo b " +
					" where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid " +
					" and handcompid='"+strCompId+"' and isnull(b.billflag,0)=1" +
					" and substring(entrydate,1,6)='"+CommonTool.setDateMask(handdate).substring(0,6)+"'   and handstaffinid='"+strStaffInid+"'  ";
			analysis = new AnlyResultSet<Double>() {
				public Double anlyResultSet(ResultSet rs) {
				double returnValue=0;
				try {
						
						if (rs != null && rs.next() == true) {
							returnValue=CommonTool.FormatDouble(rs.getDouble("rewardamt"));
						}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =0;
				}
				return returnValue;
			}
			};
			//总奖罚
			double rewardamt= (Double) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			
			strSql="  select basesalary=isnull(basesalary,0) from staffinfo a  where manageno='"+strStaffInid+"'   and ISNULL(stafftype,0)=0 ";
			analysis = new AnlyResultSet<Double>() {
				public Double anlyResultSet(ResultSet rs) {
				double returnValue=0;
				try {
						
						if (rs != null && rs.next() == true) {
							returnValue=CommonTool.FormatDouble(rs.getDouble("basesalary"));
						}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =0;
				}
				return returnValue;
			}
			};
			//总奖罚
			double basysalary= (Double) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
	
			
			if(CommonTool.FormatDouble(staffyeji)+CommonTool.FormatDouble(rewardamt)+CommonTool.FormatDouble(basysalary)<handAmt)
			{
				return false;
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
}
