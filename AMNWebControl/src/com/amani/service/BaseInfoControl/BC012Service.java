package com.amani.service.BaseInfoControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.FaceId_EmployeeBean;
import com.amani.bean.PersonRecord;

import com.amani.model.ManagerShare;
import com.amani.model.Mstaffsubsidyinfo;
import com.amani.model.Staffabsenceinfo;
import com.amani.model.Staffchangeinfo;
import com.amani.model.StaffchangeinfoId;
import com.amani.model.Staffhistory;
import com.amani.model.Staffinfo;
import com.amani.model.Staffinfomanger;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class BC012Service extends AMN_ModuleService{
	@Autowired
	private DESPlus desPlus;
	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
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
		this.amn_Dao.update(curMaster);
		curMaster=null;
		return true;
	}
	
	public List<Staffinfo> loadStaffinfosByCompId(String strCompId)
	{
		try
		{
			String strSql=" From Staffinfo where compno='"+strCompId+"' ";
			return (List<Staffinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}		
	}
	
	public Staffinfo loadStaffinfosByStaffNo(String strCompId,String strStaffNo)
	{
		try
		{
			String strSql=" From Staffinfo where compno='"+strCompId+"' and staffno='"+strStaffNo+"' ";
			List<Staffinfo> ls =(List<Staffinfo>)this.amn_Dao.findByHql(strSql);
			if(ls!=null && ls.size()>0)
			{
				return ls.get(0);
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}		
	}
	
	public boolean postStaffInfo(Staffinfo record)
	{
		try
		{
			if(record!=null)
			{
				String strSql=" update staffinfo set aaddress='"+record.getAaddress()+"',mobilephone='"+record.getMobilephone()+"'," +
						" reservecontect='"+record.getReservecontect()+"', reservephone='"+record.getReservephone()+"',businessflag="+CommonTool.FormatInteger(record.getBusinessflag())+", " +
						" introductioner='"+record.getIntroductioner()+"',healthno='"+record.getHealthno()+"',healthdate='"+CommonTool.setDateMask(record.getHealthdate())+"',banktype='"+record.getBanktype()+"',pccid='"+record.getPccid()+"',socialsecurity="+CommonTool.FormatBigDecimal(record.getSocialsecurity())+", " +
						" bankno='"+record.getBankno()+"',fillno='"+record.getFillno()+"',staffmark='"+record.getStaffmark()+"',remark='"+record.getRemark()+"',contractdate='"+CommonTool.setDateMask(record.getContractdate())+"'," +
						" positiontitle='"+CommonTool.FormatString(record.getPositiontitle())+"',socialsource='"+record.getSocialsource()+"',staffsex="+record.getStaffsex()+",mangerflag="+record.getMangerflag()+",hairqualified="+record.getHairqualified()+",absencesalary="+record.getAbsencesalary()+",tichengmode="+record.getTichengmode()+",displayname='"+record.getDisplayname() +"',staffintroduction='"+record.getStaffintroduction() +"' "+
						" ,iscurr="+record.getIscurr()+",ismoney="+record.getIsmoney()+
						" where compno='"+record.getId().getCompno()+"' and staffno='"+record.getId().getStaffno()+"' ";
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
	
	public List<Staffhistory> loadStaffhistorys(String strCompId,String strStaffNo)
	{
		try
		{
			String strSql=" select staffhistory From Staffhistory staffhistory,Staffinfo staffinfo " +
			" where compno='"+strCompId+"' and staffno='"+strStaffNo+"' and  staffhistory.manageno=staffinfo.manageno order by seqno desc ";
			return (List<Staffhistory>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Staffinfomanger>  loadStaffinfomanger(String strCompId,String strCurStaffNo)
	{
		try
		{
			String strSql=" select compno,curstaffno,cstaffno,staffname,department,manageno" +
					"  from staffinfomanger where compno='"+strCompId+"' and  curstaffno='"+strCurStaffNo+"' ";
			AnlyResultSet<List<Staffinfomanger>> analysis = new AnlyResultSet<List<Staffinfomanger>>()
			{
				public List<Staffinfomanger> anlyResultSet(ResultSet rs)
				{
					List<Staffinfomanger> returnValue = new ArrayList();
					Staffinfomanger bean=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							bean=new Staffinfomanger();
							bean.setCompno(CommonTool.FormatString(rs.getString("compno")));
							bean.setCurstaffno(CommonTool.FormatString(rs.getString("curstaffno")));
							bean.setCstaffno(CommonTool.FormatString(rs.getString("cstaffno")));
							bean.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							bean.setDepartment(CommonTool.FormatString(rs.getString("department")));
							bean.setManageno(CommonTool.FormatString(rs.getString("manageno")));
							returnValue.add(bean);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			List<Staffinfomanger> ls= (List<Staffinfomanger>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Mstaffsubsidyinfo> loadBdDateSetByCompId(String strCompId,String strInid)
	{
		try
		{
			String strSql=" select entrycompid,entrybillid,handcompid,compname,handstaffid,handstaffinid,staffname,subsidyamt,subsidyflag,conditionnum,billflag,startdate,enddate,subsidycondition,subsidyconditiontext,appstaffname,checkstaffname " +
					" from mstaffsubsidyinfo a,staffinfo b,companyinfo c,compchaininfo d " +
					" where a.handstaffinid=manageno and  isnull(stafftype,0)=0  and c.compno=a.handcompid  " +
					"  and a.handcompid=d.relationcomp and curcomp='"+strCompId+"'" +
					"  and (handstaffinid='"+strInid+"' or '"+strInid+"'='') " +
					"  and startdate<='"+CommonTool.getCurrDate().substring(0,6)+"' and enddate>='"+CommonTool.getCurrDate().substring(0,6)+"' and isnull(billflag,0) in (0,1) ";
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
	
	public boolean checkStaffno(String strCompId,String strStaffNo)
	{
		String strSql=" select 1 from staffinfo where compno='"+strCompId+"' and staffno='"+strStaffNo+"'";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = true;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue =  false;
					}
					else
					{
						returnValue =  true;
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
	
	
	public boolean checkStaffPcid(String strStaffPCID)
	{
		String strSql=" select 1 from staffinfo where pccid='"+strStaffPCID+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = true;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue =  false;
					}
					else
					{
						returnValue =  true;
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
	
	public boolean checkStaffchangeinfo(String strCompId,String strStaffNo)
	{
		String strSql=" select 1 from staffchangeinfo where aftercompid='"+strCompId+"' and afterstaffno='"+strStaffNo+"' and  isnull(billflag,0) not in (5,8) ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = true;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue =  false;
					}
					else
					{
						returnValue =  true;
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
	
	public boolean checkStaffchangeinfoByInid(String strInid)
	{
		String strSql=" select 1 from staffchangeinfo where staffmangerno='"+strInid+"' and  isnull(billflag,0) not in (5,8) ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = true;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue =  false;
					}
					else
					{
						returnValue =  true;
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
	
	public boolean handStaffNoRz(Staffinfo staffRecord,StringBuffer msgBuffer)
	{
		this.amn_Dao.save(staffRecord);
		msgBuffer.append("");
		Staffchangeinfo staffchangeinfo=new Staffchangeinfo();
		staffchangeinfo.setId(new StaffchangeinfoId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"staffchangeinfo", "changebillid", "SP011"),2));
		staffchangeinfo.setChangestaffno(staffRecord.getId().getStaffno());
		staffchangeinfo.setAppchangecompid(staffRecord.getId().getCompno());
		staffchangeinfo.setStaffpcid(staffRecord.getPccid());
		staffchangeinfo.setStaffphone(staffRecord.getMobilephone());
		staffchangeinfo.setStaffmangerno(staffRecord.getManageno());
		staffchangeinfo.setChangedate(CommonTool.getCurrDate());
		staffchangeinfo.setValidatestartdate(CommonTool.setDateMask(staffRecord.getApparrivaldate()));
		staffchangeinfo.setValidateenddate(CommonTool.setDateMask(staffRecord.getApparrivaldate()));
		staffchangeinfo.setBeforedepartment(staffRecord.getDepartment());
		staffchangeinfo.setBeforepostation(staffRecord.getPosition());
		staffchangeinfo.setBeforesalary(staffRecord.getBasesalary());
		staffchangeinfo.setBeforeyejitype(staffRecord.getResulttye());
		staffchangeinfo.setBeforeyejirate(staffRecord.getResultrate());
		staffchangeinfo.setBeforeyejiamt(staffRecord.getBaseresult());
		
		staffchangeinfo.setAftercompid(staffRecord.getId().getCompno());
		staffchangeinfo.setAfterstaffno(staffRecord.getId().getStaffno());
		staffchangeinfo.setAfterdepartment(staffRecord.getDepartment());
		staffchangeinfo.setAfterpostation(staffRecord.getPosition());
		staffchangeinfo.setAftersalary(staffRecord.getBasesalary());
		staffchangeinfo.setAfteryejitype(staffRecord.getResulttye());
		staffchangeinfo.setAfteryejirate(staffRecord.getResultrate());
		staffchangeinfo.setAfteryejiamt(staffRecord.getBaseresult());
		this.amn_Dao.save(staffchangeinfo);
		return true;
	}
	
	public boolean handStaffNoLz(Staffchangeinfo changeinfoRecord,StringBuffer msgBuffer)
	{
		this.amn_Dao.save(changeinfoRecord);
		msgBuffer.append("");
//		boolean flag= this.amn_Dao.executeSql(" update staffinfo set curstate=3 where manageno='"+changeinfoRecord.getStaffmangerno()+"' ");
//		if(flag==false)
//			msgBuffer.append("离职更新员工状态失败!");
		return true;
	}
	
	public boolean handStaffNoDD(Staffchangeinfo changeinfoRecord,StringBuffer msgBuffer)
	{
		this.amn_Dao.save(changeinfoRecord);
		msgBuffer.append("");
		return true;
	}
	
	public List<Staffinfo> loadDateSetByCompId(String strStaffNo,String strStaffName,String strStaffInno,String strPCID,String strFingerId)
	{
		String strSql="";
		if(strFingerId.equals(""))
			strFingerId="0";
		strSql=" select staffinfo From Staffinfo staffinfo, Compchaininfo compchaininfo " +
				" where (manageno='"+strStaffInno+"' or '"+strStaffInno+"'='') and (staffno='"+strStaffNo+"' or '"+strStaffNo+"'='') " +
				" and (staffname like '%"+strStaffName+"%' or '"+strStaffName+"'='') " +
				" and (pccid='"+strPCID+"' or '"+strPCID+"'='')" +
				" and (fingerno="+strFingerId+" or '"+strFingerId+"'='0')" +
				"  and ((curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=compno) or ( curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp='"+CommonTool.getLoginInfo("COMPID")+"' and compno='99999' ))";
		if(strStaffNo.equals("") && strStaffInno.equals("") && strPCID.equals("") && strStaffName.equals(""))
		{
			strSql=strSql+" and compno='"+CommonTool.getLoginInfo("COMPID")+"' ";
		}
		return (List<Staffinfo>)this.amn_Dao.findByHql(strSql);
	}
	
	public boolean handStaffRecord(String strFingerMacineId,String strInfo)
	{
		try
		{
			String strSql="";
//			String strSql=" update staffkqrecordinfo set invalid=1 where machineid="+strFingerMacineId+" and substring(ddate,1,6)="+CommonTool.getCurrDate().substring(0,6);
//			this.amn_Dao.executeSql(strSql);
//			strSql="";
			List<PersonRecord> lsPersonRecord=this.dataTool.loadDTOList(strInfo, PersonRecord.class);
			if(lsPersonRecord!=null)
			{
				for(int i=0;i<lsPersonRecord.size();i++)
				{
					lsPersonRecord.get(i).setDdaterecord(CommonTool.setDateMask(lsPersonRecord.get(i).getTime().substring(0,10)));
					lsPersonRecord.get(i).setTtimerecord(lsPersonRecord.get(i).getTime().substring(11,18));
					strSql=strSql+" insert staffkqrecordinfo(machineid,personid,stat,ddate,ttime,worktype,operationer,operationdate,invalid)" +
							" values('"+CommonTool.FormatString(lsPersonRecord.get(i).getId())+"',"+CommonTool.FormatInteger(lsPersonRecord.get(i).getPersonID())+","+CommonTool.FormatInteger(lsPersonRecord.get(i).getStat())+"," +
								   " '"+CommonTool.FormatString(lsPersonRecord.get(i).getDdaterecord())+"','"+CommonTool.FormatString(lsPersonRecord.get(i).getTtimerecord())+"'," +
								   " "+CommonTool.FormatInteger(lsPersonRecord.get(i).getWorkTyte())+" ,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"',0 ) ";
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
	
	
	public boolean validatedispatch(String dispatchcompno,String strInid,String strFDate,String strTDate)
	{
		try
		{
			String strSql=" select 1 from staffinfodispatch where newcompid='"+dispatchcompno+"' and manageno='"+strInid+"' " +
					"and (  (isnull(effectivedate,'')>='"+CommonTool.setDateMask(strFDate)+"' and isnull(teffectivedate,'')<='"+CommonTool.setDateMask(strTDate)+"') " +
						" or (isnull(effectivedate,'')<='"+CommonTool.setDateMask(strFDate)+"' and isnull(teffectivedate,'')>='"+CommonTool.setDateMask(strTDate)+"')" +
						" or (isnull(effectivedate,'')<='"+CommonTool.setDateMask(strTDate)+"' and isnull(teffectivedate,'')>='"+CommonTool.setDateMask(strTDate)+"') " +
						" or (isnull(effectivedate,'')<='"+CommonTool.setDateMask(strFDate)+"' and isnull(teffectivedate,'')>='"+CommonTool.setDateMask(strFDate)+"') ) ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			{
				public Boolean anlyResultSet(ResultSet rs)
				{
					boolean returnValue = false;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue =  false;
						}
						else
						{
							returnValue =  true;
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
			boolean flag= (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return flag;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public List<Staffabsenceinfo>  loadAbsenceInfos(String strCompId,String strManagerno,String strDate)
	{
		try
		{
			String strLastMonthDay=CommonTool.datePlusDay(CommonTool.setDateMask(strDate).substring(0,6)+"01",-31);
			String strSql=" select manageno,compid,empid,absencedate from staffabsenceinfo where compid='"+strCompId+"' and  manageno='"+strManagerno+"' and absencedate between '"+strLastMonthDay+"' and '"+strDate+"'  ";
			AnlyResultSet<List<Staffabsenceinfo>> analysis = new AnlyResultSet<List<Staffabsenceinfo>>()
			{
				public List<Staffabsenceinfo> anlyResultSet(ResultSet rs)
				{
					List<Staffabsenceinfo> returnValue = new ArrayList();
					Staffabsenceinfo bean=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							bean=new Staffabsenceinfo();
							bean.setManageno(rs.getString("manageno"));
							bean.setCompid(rs.getString("compid"));
							bean.setEmpid(rs.getString("empid"));
							bean.setAbsencedate(CommonTool.getDateMask(rs.getString("absencedate")));
							returnValue.add(bean);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			List<Staffabsenceinfo> ls= (List<Staffabsenceinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<ManagerShare>  loadManagerShareInfos(String strManagerno)
	{
		try
		{
			String strSql=" select a.manageno,empid,compid,compname,sharesalary from managershareinfo a,companyinfo b where compid=compno and a.manageno='"+strManagerno+"' ";
			AnlyResultSet<List<ManagerShare>> analysis = new AnlyResultSet<List<ManagerShare>>()
			{
				public List<ManagerShare> anlyResultSet(ResultSet rs)
				{
					List<ManagerShare> returnValue = new ArrayList();
					ManagerShare bean=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							bean=new ManagerShare();
							bean.setManageno(rs.getString("manageno"));
							bean.setCompid(rs.getString("compid"));
							bean.setEmpid(rs.getString("empid"));
							bean.setCompname(rs.getString("compname"));
							bean.setSharesalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sharesalary")))));
							returnValue.add(bean);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			List<ManagerShare> ls= (List<ManagerShare>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public boolean validateManagerShare(String strInId,String strCompId)
	{
		String strSql="select 1 from managershareinfo where manageno='"+strInId+"' and compid='"+strCompId+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = false;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue =  true;
					}
					else
					{
						returnValue =  false;
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
		boolean flag= (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return flag;
	}
	
	
	public boolean postManagerShareInfo(String strCompId,String strStaffNo,String strInId,BigDecimal sharesalary)
	{
		try
		{
			String strSql=" insert managershareinfo(compid,empid,manageno,sharesalary)" +
					" values('"+strCompId+"','"+strStaffNo+"','"+strInId+"',"+CommonTool.FormatBigDecimal(sharesalary)+") ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean deleteManagerShareInfo(String strCompId,String strStaffNo)
	{
		try
		{
			String strSql=" delete  managershareinfo where compid='"+strCompId+"' and  manageno='"+strStaffNo+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postStaffabsenceinfo(String strCompId,String strStaffNo,String strInId,String strDate)
	{
		try
		{
			String strSql=" insert staffabsenceinfo(compid,empid,manageno,absencedate)" +
					" values('"+strCompId+"','"+strStaffNo+"','"+strInId+"','"+CommonTool.setDateMask(strDate)+"') ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean postAllStaffabsenceinfo(String strCompId,String strStaffNo,String strInId,String[] lsDate)
	{
		try
		{
			String strSql="";
			for(int i=0;i<lsDate.length;i++)
			{
				strSql=strSql+" delete staffabsenceinfo where compid='"+strCompId+"' and manageno='"+strInId+"' and  absencedate='"+CommonTool.setDateMask(lsDate[i])+"' ";
				strSql=strSql+" insert staffabsenceinfo(compid,empid,manageno,absencedate)" +
				" values('"+strCompId+"','"+strStaffNo+"','"+strInId+"','"+CommonTool.setDateMask(lsDate[i])+"') ";
			}
			if(!strSql.equals(""))
			{
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
	
	public boolean validateStaffAbsence(String strInId,String strDate)
	{
		String strSql="select 1 from staffabsenceinfo where manageno='"+strInId+"' and absencedate='"+CommonTool.setDateMask(strDate)+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = false;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue =  true;
					}
					else
					{
						returnValue =  false;
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
		boolean flag= (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return flag;
	}

	public boolean postStaffDispatch(String oldcompno,String oldstaffno,String oldstaffinid,String olddepid,String oldpostion,
			int oldyjtype,double oldyjrate,double oldyjamt,String dispatchcompno,String dispatchdate,String tdispatchdate)
	{
		try
		{
			String strSql="insert staffinfodispatch(manageno,oldcompid,oldempid,olddepid,oldpostion,oldyjtype,oldyjrate,oldyjamt,newcompid,effectivedate,teffectivedate) " +
					" values('"+oldstaffinid+"','"+oldcompno+"','"+oldstaffno+"','"+olddepid+"','"+oldpostion+"','"+oldyjtype+"'," +
					" "+oldyjrate+","+oldyjamt+",'"+dispatchcompno+"','"+CommonTool.setDateMask(dispatchdate)+"','"+CommonTool.setDateMask(tdispatchdate)+"') ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postStaffSalayrUp(String oldcompno,String oldstaffno,String oldstaffinid,String olddepid,String oldpostion,
			int oldyjtype,double oldyjrate,double oldyjamt,double oldSalary,
			int newyjtype,double newyjrate,double newyjamt,double newSalary,String dispatchdate)
	{
		try
		{
			
			Staffchangeinfo staffchangeinfo=new Staffchangeinfo();
			staffchangeinfo.setId(new StaffchangeinfoId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"staffchangeinfo", "changebillid", "SP011"),5));
			staffchangeinfo.setChangestaffno(oldstaffno);
			staffchangeinfo.setAppchangecompid(oldcompno);
			staffchangeinfo.setStaffpcid("");
			staffchangeinfo.setStaffphone("");
			staffchangeinfo.setStaffmangerno(oldstaffinid);
			staffchangeinfo.setChangedate(CommonTool.getCurrDate());
			staffchangeinfo.setValidatestartdate(CommonTool.setDateMask(dispatchdate));
			staffchangeinfo.setValidateenddate(CommonTool.setDateMask(dispatchdate));
			staffchangeinfo.setBeforedepartment(olddepid);
			staffchangeinfo.setBeforepostation(oldpostion);
			staffchangeinfo.setBeforesalary(CommonTool.FormatBigDecimal(new BigDecimal(oldSalary)));
			staffchangeinfo.setBeforeyejitype(oldyjtype+"");
			staffchangeinfo.setBeforeyejirate(CommonTool.FormatBigDecimalT(new BigDecimal(oldyjrate)));
			staffchangeinfo.setBeforeyejiamt(CommonTool.FormatBigDecimalT(new BigDecimal(oldyjamt)));
			
			staffchangeinfo.setAftercompid(oldcompno);
			staffchangeinfo.setAfterstaffno(oldstaffno);
			staffchangeinfo.setAfterdepartment(olddepid);
			staffchangeinfo.setAfterpostation(oldpostion);
			staffchangeinfo.setAftersalary(CommonTool.FormatBigDecimal(new BigDecimal(newSalary)));
			staffchangeinfo.setAfteryejitype(newyjtype+"");
			staffchangeinfo.setAfteryejirate(CommonTool.FormatBigDecimalT(new BigDecimal(newyjrate)));
			staffchangeinfo.setAfteryejiamt(CommonTool.FormatBigDecimalT(new BigDecimal(newyjamt)));
			this.amn_Dao.save(staffchangeinfo);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postCashAccount(String strCompId,String strStaffId,String strStaffInNo)
	{
		try
		{
			String strSql=" delete sysuserinfo where userno='"+strStaffId+"' ";
			strSql=strSql+" insert sysuserinfo(userno,userpwd,enableflag,userrole,frominnerno,fromcompno)" +
					      " values('"+strStaffId+"','"+desPlus.encrypt("")+"',1,'19','"+strStaffInNo+"','"+strCompId+"') ";
			strSql=strSql+" delete usereditright where userno='"+strStaffId+"' ";
			strSql=strSql+" delete useroverall where userno='"+strStaffId+"'  ";
			strSql=strSql+" insert useroverall(userno,modetype,modevalue) values('"+strStaffId+"','1','"+strCompId+"') ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean handStaffHairState(String strCompId,String strStaffId,String strHairState)
	{
		try
		{
			String strSql=" update staffinfo set hairqualified="+strHairState+" where compno='"+strCompId+"' and staffno='"+strStaffId+"'  ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean handStaffTrkcState(String strCompId,String strStaffId,String strHairState)
	{
		try
		{
			String strSql=" update staffinfo set trkcqualified="+strHairState+" where compno='"+strCompId+"' and staffno='"+strStaffId+"'  ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public FaceId_EmployeeBean loadBackFaceInfo(String strIp,int strEmpId)
	{
		try
		{
			String strSql="select faceip,emplooyid,emplooyname,emplooycalid,emplooycardnum," +
					" faceimageA,faceimageB,faceimageC,faceimageD,faceimageE,faceimageF, " +
					" faceimageG,faceimageH,faceimageI,faceimageJ,faceimageK,faceimageL, " +
					" faceimageM,faceimageN,faceimageO,faceimageP,faceimageQ,faceimageR  " +
					" from amnfaceinfo where faceip='"+strIp+"' and emplooyid='"+strEmpId+"' ";
			AnlyResultSet<FaceId_EmployeeBean> analysis = new AnlyResultSet<FaceId_EmployeeBean>()
			{
				public FaceId_EmployeeBean anlyResultSet(ResultSet rs)
				{
					FaceId_EmployeeBean  returnValue = null;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=new FaceId_EmployeeBean();
							returnValue.setId(CommonTool.FormatString(rs.getString("emplooyid")));
							returnValue.setName(CommonTool.FormatString(rs.getString("emplooyname")));
							returnValue.setCalid(CommonTool.FormatString(rs.getString("emplooycalid")));
							returnValue.setCard_num(CommonTool.FormatString(rs.getString("emplooycardnum")));
							String[] strFaceDate=new String[18];
							strFaceDate[0]=CommonTool.FormatString(rs.getString("faceimageA"));
							strFaceDate[1]=CommonTool.FormatString(rs.getString("faceimageB"));
							strFaceDate[2]=CommonTool.FormatString(rs.getString("faceimageC"));
							strFaceDate[3]=CommonTool.FormatString(rs.getString("faceimageD"));
							strFaceDate[4]=CommonTool.FormatString(rs.getString("faceimageE"));
							strFaceDate[5]=CommonTool.FormatString(rs.getString("faceimageF"));
							strFaceDate[6]=CommonTool.FormatString(rs.getString("faceimageG"));
							strFaceDate[7]=CommonTool.FormatString(rs.getString("faceimageH"));
							strFaceDate[8]=CommonTool.FormatString(rs.getString("faceimageI"));
							strFaceDate[9]=CommonTool.FormatString(rs.getString("faceimageJ"));
							strFaceDate[10]=CommonTool.FormatString(rs.getString("faceimageK"));
							strFaceDate[11]=CommonTool.FormatString(rs.getString("faceimageL"));
							strFaceDate[12]=CommonTool.FormatString(rs.getString("faceimageM"));
							strFaceDate[13]=CommonTool.FormatString(rs.getString("faceimageN"));
							strFaceDate[14]=CommonTool.FormatString(rs.getString("faceimageO"));
							strFaceDate[15]=CommonTool.FormatString(rs.getString("faceimageP"));
							strFaceDate[16]=CommonTool.FormatString(rs.getString("faceimageQ"));
							strFaceDate[17]=CommonTool.FormatString(rs.getString("faceimageR"));
							returnValue.setFace_data(strFaceDate);
							strFaceDate=null;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			FaceId_EmployeeBean returnbean= (FaceId_EmployeeBean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return returnbean;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public FaceId_EmployeeBean loadBackFaceInfoById(int strEmpId)
	{
		try
		{
			String strSql="select faceip,emplooyid,emplooyname,emplooycalid,emplooycardnum," +
					" faceimageA,faceimageB,faceimageC,faceimageD,faceimageE,faceimageF, " +
					" faceimageG,faceimageH,faceimageI,faceimageJ,faceimageK,faceimageL, " +
					" faceimageM,faceimageN,faceimageO,faceimageP,faceimageQ,faceimageR  " +
					" from amnfaceinfo where  emplooyid='"+strEmpId+"' ";
			AnlyResultSet<FaceId_EmployeeBean> analysis = new AnlyResultSet<FaceId_EmployeeBean>()
			{
				public FaceId_EmployeeBean anlyResultSet(ResultSet rs)
				{
					FaceId_EmployeeBean  returnValue = null;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=new FaceId_EmployeeBean();
							returnValue.setId(CommonTool.FormatString(rs.getString("emplooyid")));
							returnValue.setName(CommonTool.FormatString(rs.getString("emplooyname")));
							returnValue.setCalid(CommonTool.FormatString(rs.getString("emplooycalid")));
							returnValue.setCard_num(CommonTool.FormatString(rs.getString("emplooycardnum")));
							String[] strFaceDate=new String[18];
							strFaceDate[0]=CommonTool.FormatString(rs.getString("faceimageA"));
							strFaceDate[1]=CommonTool.FormatString(rs.getString("faceimageB"));
							strFaceDate[2]=CommonTool.FormatString(rs.getString("faceimageC"));
							strFaceDate[3]=CommonTool.FormatString(rs.getString("faceimageD"));
							strFaceDate[4]=CommonTool.FormatString(rs.getString("faceimageE"));
							strFaceDate[5]=CommonTool.FormatString(rs.getString("faceimageF"));
							strFaceDate[6]=CommonTool.FormatString(rs.getString("faceimageG"));
							strFaceDate[7]=CommonTool.FormatString(rs.getString("faceimageH"));
							strFaceDate[8]=CommonTool.FormatString(rs.getString("faceimageI"));
							strFaceDate[9]=CommonTool.FormatString(rs.getString("faceimageJ"));
							strFaceDate[10]=CommonTool.FormatString(rs.getString("faceimageK"));
							strFaceDate[11]=CommonTool.FormatString(rs.getString("faceimageL"));
							strFaceDate[12]=CommonTool.FormatString(rs.getString("faceimageM"));
							strFaceDate[13]=CommonTool.FormatString(rs.getString("faceimageN"));
							strFaceDate[14]=CommonTool.FormatString(rs.getString("faceimageO"));
							strFaceDate[15]=CommonTool.FormatString(rs.getString("faceimageP"));
							strFaceDate[16]=CommonTool.FormatString(rs.getString("faceimageQ"));
							strFaceDate[17]=CommonTool.FormatString(rs.getString("faceimageR"));
							returnValue.setFace_data(strFaceDate);
							strFaceDate=null;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			FaceId_EmployeeBean returnbean= (FaceId_EmployeeBean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return returnbean;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//1审核 2 人事驳回
	public boolean handLsBill(String strCompId,String strBillId,int ihandFlag)
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
				strSql=" update mstaffsubsidyinfo set billflag=2 where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"'  ";
			}
			else if(ihandFlag==3)
			{
				strSql=" update mstaffsubsidyinfo set billflag=3 where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"'  ";
			}
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
				ex.printStackTrace();
				return false;
		}
	}
	
	
	public boolean postRolePositionAccount(String strCompId,String strStaffId,String strStaffInNo,String strPosition)
	{
		try
		{
			String strRoleNo="";
			String strSql="select roleno from sysroletoposition where position='"+strPosition+"'  ";
			AnlyResultSet<String> analysis = new AnlyResultSet<String>()
			{
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue = "";
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue =  CommonTool.FormatString(rs.getString("roleno"));
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			strRoleNo= (String)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			if(!CommonTool.FormatString(strRoleNo).equals(""))
			{
				strSql=" delete sysuserinfo where userno='"+strStaffId+"' ";
				strSql=strSql+" insert sysuserinfo(userno,userpwd,enableflag,userrole,frominnerno,fromcompno)" +
						      " values('"+strStaffId+"','"+desPlus.encrypt("")+"',1,'"+strRoleNo+"','"+strStaffInNo+"','"+strCompId+"') ";
				strSql=strSql+" delete usereditright where userno='"+strStaffId+"' ";
				strSql=strSql+" delete useroverall where userno='"+strStaffId+"'  ";
				strSql=strSql+" insert useroverall(userno,modetype,modevalue) values('"+strStaffId+"','1','"+strCompId+"') ";
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
	
	
	public boolean validateChildrenInfo(String strCompId,String strCurStaffNo,String strCmangerNo)
	{
		String strSql="select 1 from staffinfomanger where compno='"+strCompId+"' and curstaffno='"+strCurStaffNo+"' and manageno='"+strCmangerNo+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = false;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue =  true;
					}
					else
					{
						returnValue =  false;
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
		boolean flag= (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return flag;
	}
	
	public boolean handAddChildrenInfo(String strCompId,String strCurStaffId,String strChildStaffno,String strStaffName,String strDepartment,String strMangerNo)
	{
		try
		{
			String strSql="insert staffinfomanger(compno,curstaffno,cstaffno,staffname,department,manageno) " +
					" values('"+strCompId+"','"+strCurStaffId+"','"+strChildStaffno+"','"+strStaffName+"','"+strDepartment+"','"+strMangerNo+"')";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

}
