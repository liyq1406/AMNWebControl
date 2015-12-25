package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Staffrewardinfo;
import com.amani.model.StaffrewardinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class CopyOfPC004Service extends AMN_ModuleService{

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		try
		{
			Staffrewardinfo record=(Staffrewardinfo)curMaster;
			if(record!=null && record.getId()!=null)
			{
				String strSql=" update staffrewardinfo set invalid=1 where entrycompid='"+record.getId().getEntrycompid()+"' and entrybillid='"+record.getId().getEntrybillid()+"' ";
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
	
	public boolean postRecord(Staffrewardinfo curMaster)
	{
		this.amn_Dao.saveOrUpdate(curMaster);
		return true;
	}
	
	public boolean postCheckbill(Staffrewardinfo curMaster)
	{
		String strSql=" update staffrewardinfo set checkrewardstaff='"+curMaster.getCheckrewardstaff()+"'," +
				" checkrewardstaffname='"+curMaster.getCheckrewardstaffname()+"',checkrewardremark='"+curMaster.getCheckrewardremark()+"'," +
				" checkrewardamt="+curMaster.getCheckrewardamt()+",checkpersonid='"+CommonTool.getLoginInfo("COMPID")+"',checkdate='"+CommonTool.getCurrDate()+"',checkflag=1,billflag=1" +
				" where entrycompid='"+curMaster.getId().getEntrycompid()+"' and entrybillid='"+curMaster.getId().getEntrybillid()+"' ";
		return this.amn_Dao.executeSql(strSql);
		 
	}
	
	public boolean postComfirmkbill(Staffrewardinfo curMaster)
	{
		String strSql=" update staffrewardinfo set checkinheadrewardstaff='"+curMaster.getCheckinheadrewardstaff()+"'," +
		" checkinheadrewardstaffname='"+curMaster.getCheckinheadrewardstaffname()+"',checkinheadrewardremark='"+curMaster.getCheckinheadrewardremark()+"'," +
		" checkinheadrewardamt="+curMaster.getCheckinheadrewardamt()+",checkinheadpersonid='"+CommonTool.getLoginInfo("COMPID")+"',checkinheaddate='"+CommonTool.getCurrDate()+"',checkinheadflag=1,billflag=2" +
		" where entrycompid='"+curMaster.getId().getEntrycompid()+"' and entrybillid='"+curMaster.getId().getEntrybillid()+"' ";
		return this.amn_Dao.executeSql(strSql)	;
	}
	
	//获取主档信息
	public List<Staffrewardinfo> loadStaffrewardinfos()
	{
		try
		{
			String strSql="select entrycompid,entrybillid,entryflag,entrydate,handcompid,handstaffid,handstaffinid,staffname " +
					"  From  staffrewardinfo , compchaininfo, staffinfo" +
					" where curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=handcompid " +
					" and (isnull(entrydate,'')='"+CommonTool.getCurrDate()+"' ) " +
					" and isnull(invalid,0)=0 " +
					" and handstaffinid=manageno ";
			AnlyResultSet<List<Staffrewardinfo>> analysis = new AnlyResultSet<List<Staffrewardinfo>>() {
				public List<Staffrewardinfo> anlyResultSet(ResultSet rs) {
					List<Staffrewardinfo> returnValue = new ArrayList();
					Staffrewardinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Staffrewardinfo();
							record.setBentrycompid(CommonTool.FormatString(rs.getString("entrycompid")));
							record.setBentrybillid(CommonTool.FormatString(rs.getString("entrybillid")));
							record.setEntryflag(CommonTool.FormatInteger(rs.getInt("entryflag")));
							record.setHandcompid(CommonTool.FormatString(rs.getString("handcompid")));
							record.setEntrydate(CommonTool.getDateMask(rs.getString("entrydate")));
							record.setHandstaffid(CommonTool.FormatString(rs.getString("handstaffid")));
							record.setHandstaffinid(CommonTool.FormatString(rs.getString("handstaffinid")));
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
			List<Staffrewardinfo> ls= (List<Staffrewardinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
	public List<Staffrewardinfo> loadStaffrewardinfos(String strBillNo,String strDate)
	{
		try
		{
			String strSql="select entrycompid,entrybillid,entryflag,entrydate,handcompid,handstaffid,handstaffinid,staffname " +
					"  From  staffrewardinfo , compchaininfo, staffinfo" +
					" where curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=handcompid " +
					" and isnull(invalid,0)=0 and (isnull(entrybillid,'')='"+strBillNo+"' or '"+strBillNo+"'='' ) and (isnull(entrydate,'')='"+strDate+"' or '"+strDate+"'='' ) " +
					" and handstaffinid=manageno ";
			if(CommonTool.FormatString(strBillNo).equals("") && CommonTool.FormatString(strDate).equals(""))
			{
				strSql=strSql+" and (isnull(entrydate,'')='"+CommonTool.getCurrDate()+"' ) " ;
			}
			AnlyResultSet<List<Staffrewardinfo>> analysis = new AnlyResultSet<List<Staffrewardinfo>>() {
				public List<Staffrewardinfo> anlyResultSet(ResultSet rs) {
					List<Staffrewardinfo> returnValue = new ArrayList();
					Staffrewardinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Staffrewardinfo();
							record.setBentrycompid(CommonTool.FormatString(rs.getString("entrycompid")));
							record.setBentrybillid(CommonTool.FormatString(rs.getString("entrybillid")));
							record.setEntryflag(CommonTool.FormatInteger(rs.getInt("entryflag")));
							record.setHandcompid(CommonTool.FormatString(rs.getString("handcompid")));
							record.setEntrydate(CommonTool.getDateMask(rs.getString("entrydate")));
							record.setHandstaffid(CommonTool.FormatString(rs.getString("handstaffid")));
							record.setHandstaffinid(CommonTool.FormatString(rs.getString("handstaffinid")));
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
			List<Staffrewardinfo> ls= (List<Staffrewardinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
	public Staffrewardinfo loadStaffrewardinfoById(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Staffrewardinfo staffrewardinfo where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
			List<Staffrewardinfo> lsStaffrewardinfo=this.amn_Dao.findByHql(strSql);
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
	
	 
	 //新增主档
	 public Staffrewardinfo addMastRecord()
	 {
		 Staffrewardinfo record=new Staffrewardinfo();
		 record.setId(new StaffrewardinfoId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"staffrewardinfo", "entrybillid", "SP011")));
		 record.setEntrydate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setBentrybillid(record.getId().getEntrybillid());
		 record.setInvalid(0);
		 record.setBillflag(0);
		 record.setEntryflag(0);
		 return record;
	 }
	
}

