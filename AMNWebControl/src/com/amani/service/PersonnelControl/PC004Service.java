package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dstaffrewardinfo;
import com.amani.model.Mstaffrewardinfo;
import com.amani.model.MstaffrewardinfoId;
import com.amani.model.Staffinfo;
import com.amani.model.Staffrewardinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class PC004Service extends AMN_ModuleService{

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		try
		{
			Mstaffrewardinfo record=(Mstaffrewardinfo)curMaster;
			if(record!=null && record.getId()!=null)
			{
				String strSql=" update mstaffrewardinfo set invalid=1 where entrycompid='"+record.getId().getEntrycompid()+"' and entrybillid='"+record.getId().getEntrybillid()+"' ";
				return this.amn_Dao.deleteBySql(strSql);
			}
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
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		this.amn_Dao.saveOrUpdate(curMaster);
		return true;
	}
	
	public boolean postRecord(Mstaffrewardinfo curMaster,List<Dstaffrewardinfo> lsDmstaffrewardinfo)
	{
		this.amn_Dao.saveOrUpdate(curMaster);
		if(lsDmstaffrewardinfo!=null && lsDmstaffrewardinfo.size()>0)
		{
			this.amn_Dao.saveOrUpdateAll(lsDmstaffrewardinfo);
		}
		return true;
	}
	

	public boolean postCheckbill(String strCompId,String strBillId)
	{
		String strSql=" update mstaffrewardinfo set billflag=1" +
				" where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
		strSql=strSql+" update dstaffrewardinfo set billflag=1" +
		" where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
		return this.amn_Dao.executeSql(strSql);
		 
	}
	
	 public boolean validateManagerPass(String strManagerNo,String strManagerPass)
	 {
		 String strSql=" select 1 from staffinfo where staffno='"+strManagerNo+"' and staffpassword='"+strManagerPass+"' and isnull(staffpassword,'')<>'' ";
		 AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			{
				public Boolean anlyResultSet(ResultSet rs)
				{
					boolean returnValue = false;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=true;
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
	 
	//获取主档信息
	public List<Mstaffrewardinfo> loadStaffrewardinfos(String strBillNo)
	{
		try
		{
			String strSql="select entrycompid,entrybillid,entryflag,handcompid,compname " +
					"  From  mstaffrewardinfo , compchaininfo, companyinfo" +
					" where curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=handcompid and isnull(entryflag,0)=1 " +
					" and isnull(invalid,0)=0 and (isnull(entrybillid,'')='"+strBillNo+"' or '"+strBillNo+"'='' )  " +
					" and handcompid=compno ";
			
			AnlyResultSet<List<Mstaffrewardinfo>> analysis = new AnlyResultSet<List<Mstaffrewardinfo>>() {
				public List<Mstaffrewardinfo> anlyResultSet(ResultSet rs) {
					List<Mstaffrewardinfo> returnValue = new ArrayList();
					Mstaffrewardinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Mstaffrewardinfo();
							record.setBentrycompid(CommonTool.FormatString(rs.getString("entrycompid")));
							record.setBentrybillid(CommonTool.FormatString(rs.getString("entrybillid")));
							record.setEntryflag(CommonTool.FormatInteger(rs.getInt("entryflag")));
							record.setHandcompid(CommonTool.FormatString(rs.getString("handcompid")));
							record.setHandcompname(CommonTool.FormatString(rs.getString("compname")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Mstaffrewardinfo> ls= (List<Mstaffrewardinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	//获取主档信息
	public List<Dstaffrewardinfo> loadDStaffrewardinfos(String strCompId,String strDateFrom,String strDateTo)
	{
		try
		{
			String strSql="select a.entrycompid,handcompid,compname,handstaffid,staffname,entryreason,entrydate,entrytype,rewardamt,b.billflag " +
					"  From  mstaffrewardinfo a,dstaffrewardinfo b, compchaininfo, companyinfo c,staffinfo d " +
					" where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and isnull(entryflag,0)=1 " +
					" and curcomp='"+strCompId+"' and relationcomp=handcompid " +
					" and isnull(invalid,0)=0 and (isnull(entrydate,'') between '"+strDateFrom+"' and '"+strDateTo+"' or '"+strDateFrom+"'='' )  " +
					" and handcompid=c.compno and  manageno=handstaffinid and handcompid='"+strCompId+"' and isnull(stafftype,0)=0  order by handcompid,entrydate ";
			
			AnlyResultSet<List<Dstaffrewardinfo>> analysis = new AnlyResultSet<List<Dstaffrewardinfo>>() {
				public List<Dstaffrewardinfo> anlyResultSet(ResultSet rs) {
					List<Dstaffrewardinfo> returnValue = new ArrayList();
					Dstaffrewardinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Dstaffrewardinfo();
							record.setBentrycompid(CommonTool.FormatString(rs.getString("entrycompid")) );
							record.setHandstaffid(CommonTool.FormatString(rs.getString("handstaffid")));
							record.setEntryreason(CommonTool.FormatString(rs.getString("entryreason")));
							record.setEntrydate(CommonTool.getDateMask(rs.getString("entrydate")));
							record.setEntrytype(CommonTool.FormatString(rs.getString("entrytype")));
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
	
	//获取主档信息
	public List<Mstaffrewardinfo> loadStaffrewardinfos()
	{
		try
		{
			String strSql="select entrycompid,entrybillid,entryflag,handcompid,compname " +
					"  From  mstaffrewardinfo a, compchaininfo, companyinfo" +
					" where curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=entrycompid " +
					" and isnull(invalid,0)=0  and isnull(a.billflag,0)=0  and isnull(entryflag,0)=1 " +
					" and handcompid=compno ";
			
			AnlyResultSet<List<Mstaffrewardinfo>> analysis = new AnlyResultSet<List<Mstaffrewardinfo>>() {
				public List<Mstaffrewardinfo> anlyResultSet(ResultSet rs) {
					List<Mstaffrewardinfo> returnValue = new ArrayList();
					Mstaffrewardinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Mstaffrewardinfo();
							record.setBentrycompid(CommonTool.FormatString(rs.getString("entrycompid")));
							record.setBentrybillid(CommonTool.FormatString(rs.getString("entrybillid")));
							record.setEntryflag(CommonTool.FormatInteger(rs.getInt("entryflag")));
							record.setHandcompid(CommonTool.FormatString(rs.getString("handcompid")));
							record.setHandcompname(CommonTool.FormatString(rs.getString("compname")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Mstaffrewardinfo> ls= (List<Mstaffrewardinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Mstaffrewardinfo loadStaffrewardinfoById(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Mstaffrewardinfo mstaffrewardinfo where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
			List<Mstaffrewardinfo> lsStaffrewardinfo=this.amn_Dao.findByHql(strSql);
			if(lsStaffrewardinfo!=null && lsStaffrewardinfo.size()>0)
				return lsStaffrewardinfo.get(0);
			else
				return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public List<Dstaffrewardinfo> loadDstaffrewardinfoById(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" select handstaffid,handstaffinid,entryreason,entrydate,entrytype,rewardamt,staffname" +
					" from dstaffrewardinfo,staffinfo" +
					" where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' and  manageno=handstaffinid and isnull(stafftype,0)=0   ";
			AnlyResultSet<List<Dstaffrewardinfo>> analysis = new AnlyResultSet<List<Dstaffrewardinfo>>() {
				public List<Dstaffrewardinfo> anlyResultSet(ResultSet rs) {
					List<Dstaffrewardinfo> returnValue = new ArrayList();
					Dstaffrewardinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Dstaffrewardinfo();
							record.setHandstaffid(CommonTool.FormatString(rs.getString("handstaffid")));
							record.setHandstaffinid(CommonTool.FormatString(rs.getString("handstaffinid")));
							record.setEntryreason(CommonTool.FormatString(rs.getString("entryreason")));
							record.setEntrydate(CommonTool.getDateMask(rs.getString("entrydate")));
							record.setEntrytype(CommonTool.FormatString(rs.getString("entrytype")));
							record.setRewardamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("rewardamt")))));
							record.setHandstaffname(CommonTool.FormatString(rs.getString("staffname")));
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
	 //新增主档
	 public Mstaffrewardinfo addMastRecord()
	 {
		 Mstaffrewardinfo record=new Mstaffrewardinfo();
		 record.setId(new MstaffrewardinfoId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mstaffrewardinfo", "entrybillid", "SP011")));
		 record.setBentrybillid(record.getId().getEntrybillid());
		 record.setInvalid(0);
		 record.setBillflag(0);
		 record.setEntryflag(1);
		 return record;
	 }
	 
	 public List<Staffinfo> loadOldStaffInfo(String strCompId)
	 {
		 try
		 {
			 String strLastMonthDay=CommonTool.datePlusDay(CommonTool.getCurrDate().substring(0,6)+"01",-31);
			 String strSql="select  oldcompid,oldempid,a.manageno,staffname from staffhistory a,staffinfo b " +
			 		" where a.manageno=b.manageno  and oldcompid='"+strCompId+"' " +
			 	     " and changetype in (1,3) and effectivedate  between '"+strLastMonthDay+"' and '"+CommonTool.getCurrDate()+"'" +
			 	    "  group by  oldcompid,oldempid,a.manageno,staffname ";
			 
			 AnlyResultSet<List<Staffinfo>> analysis = new AnlyResultSet<List<Staffinfo>>() {
					public List<Staffinfo> anlyResultSet(ResultSet rs) {
						List<Staffinfo> returnValue = new ArrayList();
						Staffinfo record=null;
						try {
							while (rs != null && rs.next() == true) {
								record=new Staffinfo();
								record.setBcompno(rs.getString("oldcompid"));
								record.setBstaffno(rs.getString("oldempid"));
								record.setStaffname(rs.getString("staffname"));
								record.setManageno(rs.getString("manageno"));
								returnValue.add(record);
							}
						} catch (Exception e) {
							e.printStackTrace();
							returnValue =null;
						}
						return returnValue;
					}
				};
				List<Staffinfo> ls= (List<Staffinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
				analysis=null;
				return ls;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	
}

