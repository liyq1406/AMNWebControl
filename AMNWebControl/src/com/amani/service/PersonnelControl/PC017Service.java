package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dstaffrewardinfo;
import com.amani.model.Dstaffsubsidyinfo;
import com.amani.model.Dstafftargetinfo;
import com.amani.model.Mstaffrewardinfo;
import com.amani.model.MstaffrewardinfoId;
import com.amani.model.Mstaffsubsidyinfo;
import com.amani.model.MstaffsubsidyinfoId;
import com.amani.model.Mstafftargetinfo;
import com.amani.model.MstafftargetinfoId;
import com.amani.model.Staffinfo;
import com.amani.model.Staffrewardinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class PC017Service extends AMN_ModuleService{

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
					" where curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=handcompid  " +
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
			String strSql="select handcompid,compname,handstaffid,staffname,entryreason,entrydate,entrytype,rewardamt,billflag,entryflag,operationer,operationdate " +
					"  From  mstaffrewardinfo a,dstaffrewardinfo b, compchaininfo, companyinfo c,staffinfo d " +
					" where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid  " +
					" and curcomp='"+strCompId+"' and relationcomp=handcompid " +
					" and isnull(invalid,0)=0 and (isnull(entrydate,'') between '"+strDateFrom+"' and '"+strDateTo+"' or '"+strDateFrom+"'='' )  " +
					" and handcompid=c.compno and  manageno=handstaffinid  order by handcompid,entrydate ";
			
			AnlyResultSet<List<Dstaffrewardinfo>> analysis = new AnlyResultSet<List<Dstaffrewardinfo>>() {
				public List<Dstaffrewardinfo> anlyResultSet(ResultSet rs) {
					List<Dstaffrewardinfo> returnValue = new ArrayList();
					Dstaffrewardinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Dstaffrewardinfo();
							record.setHandstaffid(CommonTool.FormatString(rs.getString("handstaffid")));
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
	
	//获取主档信息
	public List<Mstaffrewardinfo> loadStaffrewardinfos()
	{
		try
		{
			String strSql="select entrycompid,entrybillid,entryflag,handcompid,compname " +
					"  From  mstaffrewardinfo ,companyinfo" +
					" where entrycompid='"+CommonTool.getLoginInfo("COMPID")+"' and operationdate='"+CommonTool.getCurrDate()+"'   " +
					" and isnull(invalid,0)=0  and isnull(billflag,0)=0 and  entrycompid='001'  " +
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
			String strSql=" select handstaffid,handstaffinid,entryreason,entrydate,entrytype,rewardamt,staffname " +
					" from dstaffrewardinfo,staffinfo" +
					" where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' and  manageno=handstaffinid    and ISNULL(stafftype,0)=0 ";
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
		 record.setEntryflag(0);
		 return record;
	 }
	 
	 public Mstaffsubsidyinfo addSubsidyMastRecord()
	 {
		 Mstaffsubsidyinfo record=new Mstaffsubsidyinfo();
		 record.setId(new MstaffsubsidyinfoId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mstaffsubsidyinfo", "entrybillid", "SP011")));
		 record.setBentrybillid(record.getId().getEntrybillid());
		 record.setInvalid(0);
		 record.setBillflag(0);
		 return record;
	 }
	 
	 public Mstafftargetinfo addTargetMastRecord()
	 {
		 Mstafftargetinfo record=new Mstafftargetinfo();
		 record.setId(new MstafftargetinfoId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mstafftargetinfo", "entrybillid", "SP011")));
		 record.setBentrybillid(record.getId().getEntrybillid());
		 record.setInvalid(0);
		 record.setBillflag(0);
		 return record;
	 }
	 
	 public boolean postSubsidy(List<Mstaffsubsidyinfo> lsMaster,List<Dstaffsubsidyinfo> lsDetial)
	 {
		 try
		 {
			 if(lsMaster!=null && lsMaster.size()>0)
			 {
				 this.amn_Dao.saveOrUpdateAll(lsMaster);
			 }
			 if(lsDetial!=null && lsDetial.size()>0)
			 {
				 this.amn_Dao.saveOrUpdateAll(lsDetial);
			 }
			 return true;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public boolean postTarget(List<Mstafftargetinfo> lsMaster,List<Dstafftargetinfo> lsDetial)
	 {
		 try
		 {
			 if(lsMaster!=null && lsMaster.size()>0)
			 {
				 this.amn_Dao.saveOrUpdateAll(lsMaster);
			 }
			 if(lsDetial!=null && lsDetial.size()>0)
			 {
				 this.amn_Dao.saveOrUpdateAll(lsDetial);
			 }
			 return true;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public boolean validateStaffSubsidy(String strCompId,String strInid,String strDateFrom,String strDataTo)
	 {
		 String strSql=" select 1 from mstaffsubsidyinfo where handcompid='"+strCompId+"' and  handstaffinid='"+strInid+"'" +
		 		"  and ( (startdate<='"+strDateFrom+"' and enddate>='"+strDataTo+"' ) " +
		 		" or (startdate<='"+strDataTo+"' and enddate>='"+strDataTo+"' ) " +
		 	    " or (startdate>='"+strDateFrom+"' and enddate<='"+strDataTo+"' )" +
		 	    " or (startdate<='"+strDateFrom+"' and enddate>='"+strDataTo+"' ) )" +
		 	    " and  isnull(billflag,0) not in (2,3) ";
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
	 
	 public List<Staffinfo> loadStaffInfoByZerenjin(String strCompId)
	 {
		 try
		 {
			 String strSql="select compno,staffno,staffname,manageno," +
			 		" zerenjin=case when position in ('001','00101','00103','00105','0010502','0010503','00106','0010602','008') then 600 " +
			 		" when position in ('00104','003','004','00401','00402','006','007','00701','00702','00901','00902','00903','00904') then 300 end " +
			 		"  from staffinfo a" +
			 		"  where a.compno='"+strCompId+"' and curstate=2 and datediff(month,arrivaldate,'"+CommonTool.getCurrDate()+"')>=6 " +
			 		" and position in ('001','00101','00103','00105','0010502','0010503','00106','0010602','008','00104','003','004','00401','00402','006','007','00701','00702','00901','00902','00903','00904') " +
			 		" and isnull(arrivaldate,'20130101')>'20131101' and manageno not in (select handstaffinid from mstaffrewardinfo b,dstaffrewardinfo c " +
			 		" where b.entrycompid=c.entrycompid and b.entrybillid=c.entrybillid and b.handcompid='"+strCompId+"' and c.entrytype='15' ) ";
			 AnlyResultSet<List<Staffinfo>> analysis = new AnlyResultSet<List<Staffinfo>>() {
					public List<Staffinfo> anlyResultSet(ResultSet rs) {
						List<Staffinfo> returnValue = new ArrayList();
						Staffinfo record=null;
						try {
							while (rs != null && rs.next() == true) {
								record=new Staffinfo();
								record.setBcompno(rs.getString("compno"));
								record.setBstaffno(rs.getString("staffno"));
								record.setStaffname(rs.getString("staffname"));
								record.setManageno(rs.getString("manageno"));
								record.setZerenjin(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("zerenjin"))));
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
	 
	 public boolean  postStaffRewardInfo(String strCompId,String strDate,List<Staffinfo> lsStaffinfo)
	 {
		 try
		 {
			 String strBillId= this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mstaffrewardinfo", "entrybillid", "SP011");
			 String strSql=" insert mstaffrewardinfo(entrycompid,entrybillid,entryflag,handcompid,billflag,operationer,operationdate) " +
			 		       " values('"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"',1,'"+strCompId+"',1,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"') ";
			 if(lsStaffinfo!=null && lsStaffinfo.size()>0)
			 {
				 for(int i=0;i<lsStaffinfo.size();i++)
				 {
					 strSql=strSql+" insert dstaffrewardinfo(entrycompid,entrybillid,entryseqno,handstaffid,handstaffinid,entryreason,entrydate,entrytype,rewardamt,billflag) " +
					 		" values('"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"',"+i+",'"+lsStaffinfo.get(i).getBstaffno()+"','"+lsStaffinfo.get(i).getManageno()+"','门店责任金自动扣除','"+CommonTool.setDateMask(strDate)+"','15',"+lsStaffinfo.get(i).getZerenjin().doubleValue()+",1) ";
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

