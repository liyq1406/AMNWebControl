package com.amani.service.BaseInfoControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.Storetargetinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class BC016Service extends AMN_ReportService{
	
	public List<Storetargetinfo> loadDateSetByCompId(String strCompId,String strDateFrom,int billstate)
	{
		try
		{
			String strSql=" select entrycompid,entrybillid, compid,compname,targetmonth,ttotalyeji,trealtotalyeji,tbeatyyeji,ttrhyeji,tcostcount,tstaffleavelcount,targetflag " +
					" from storetargetinfo,companyinfo,compchaininfo" +
					" where compid=compno and curcomp='"+strCompId+"' and relationcomp=compid " +
					"   and (targetmonth='"+strDateFrom.replace("-", "")+"' or '"+strDateFrom+"'='' ) " +
					"   and (isnull(targetflag,0)="+billstate+" or "+billstate+"=4 )";
			AnlyResultSet<List<Storetargetinfo>> analysis = new AnlyResultSet<List<Storetargetinfo>>() {
				public List<Storetargetinfo> anlyResultSet(ResultSet rs) {
					List<Storetargetinfo> returnValue = new ArrayList();
					Storetargetinfo bean=null;
					try {
						while (rs != null && rs.next() == true) {
							 bean=new Storetargetinfo();
							 bean.setEntrycompid(CommonTool.FormatString(rs.getString("entrycompid")));
							 bean.setEntrybillid(CommonTool.FormatString(rs.getString("entrybillid")));
							 bean.setCompid(CommonTool.FormatString(rs.getString("compid")));
							 bean.setCompname(CommonTool.FormatString(rs.getString("compname")));
							 bean.setTargetmonth(CommonTool.FormatString(rs.getString("targetmonth")));
							 bean.setTtotalyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ttotalyeji")))));
							 bean.setTrealtotalyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("trealtotalyeji")))));
							 bean.setTbeatyyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tbeatyyeji")))));
							 bean.setTtrhyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ttrhyeji")))));
							 bean.setTcostcount(CommonTool.FormatBigDecimalZ(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tcostcount")))));
							 bean.setTstaffleavelcount(CommonTool.FormatBigDecimalZ(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tstaffleavelcount")))));
							 bean.setTargetflag(CommonTool.FormatInteger(rs.getInt("targetflag")));
						     returnValue.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Storetargetinfo> ls= (List<Storetargetinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
				strSql=" update storetargetinfo set targetflag=1 where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
			
			}
			else if(ihandFlag==2)
			{
				strSql=" update storetargetinfo set targetflag=2 where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
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
	public boolean handLsBill(List<Storetargetinfo> lsbean,int ihandFlag)
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
					strSql=strSql+" update storetargetinfo set targetflag=1 where entrycompid='"+lsbean.get(i).getEntrycompid()+"' and entrybillid='"+lsbean.get(i).getEntrybillid()+"' ";
				}
				else if(ihandFlag==2)
				{
					strSql=strSql+" update storetargetinfo set targetflag=2 where entrycompid='"+lsbean.get(i).getEntrycompid()+"' and entrybillid='"+lsbean.get(i).getEntrybillid()+"'  ";
				}
				else if(ihandFlag==3)
				{
					strSql=strSql+" update storetargetinfo set targetflag=3 where entrycompid='"+lsbean.get(i).getEntrycompid()+"' and entrybillid='"+lsbean.get(i).getEntrybillid()+"'  ";
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
	
	 public Storetargetinfo addSubsidyMastRecord()
	 {
		 Storetargetinfo record=new Storetargetinfo();
		 record.setEntrybillid(this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"storetargetinfo", "entrybillid", "SP011"));
		 record.setEntrycompid(CommonTool.getLoginInfo("COMPID"));
		 record.setTargetflag(0);
		 return record;
	 }
	 public boolean postTarget(List<Storetargetinfo> lsMaster)
	 {
		 try
		 {
			 if(lsMaster!=null && lsMaster.size()>0)
			 {
				 String strSql="";
				 for(int i=0;i<lsMaster.size();i++)
				 {
					 strSql=strSql+" insert storetargetinfo(entrycompid,entrybillid,compid,targetmonth,ttotalyeji,trealtotalyeji,tbeatyyeji,ttrhyeji,tcostcount,tstaffleavelcount,targetflag) " +
				 		" values('"+lsMaster.get(i).getEntrycompid()+"','"+lsMaster.get(i).getEntrybillid()+"','"+lsMaster.get(i).getCompid()+"','"+lsMaster.get(i).getTargetmonth()+"'" +
				 				" ,"+lsMaster.get(i).getTtotalyeji()+","+lsMaster.get(i).getTrealtotalyeji()+","+lsMaster.get(i).getTbeatyyeji()+","+lsMaster.get(i).getTtrhyeji()+","+lsMaster.get(i).getTcostcount()+","+lsMaster.get(i).getTstaffleavelcount()+",0)";
				 }
				 return this.amn_Dao.executeSql(strSql);
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
