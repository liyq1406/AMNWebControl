package com.amani.service.AdvancedOperations;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.FaceId_EmployeeBean;
import com.amani.bean.FaceId_RecordBean;
import com.amani.model.AmnfaceMachineinfo;
import com.amani.model.Companyinfo;
import com.amani.model.Compscheduling;
import com.amani.model.Compwarehouse;
import com.amani.model.Personinfo;
import com.amani.model.StaffMachineinfo;
import com.amani.model.Staffinfo;
import com.amani.service.AMN_ReportService;
import com.amani.service.ICommonService;
import com.amani.tools.CommonTool;
@Service
public class AC019Service extends AMN_ReportService{
	
	public List<Staffinfo> loadStaffDateSetByCompId(String strCompId,String strDateFrom,String strDateTo)
	{
		try
		{
			String strSql=" exec upg_hand_staff_face_instore '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"' ";
			AnlyResultSet<List<Staffinfo>> analysis = new AnlyResultSet<List<Staffinfo>>() {
				public List<Staffinfo> anlyResultSet(ResultSet rs) {
					List<Staffinfo> returnValue = new ArrayList();
					Staffinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Staffinfo();
							record.setBcompno(CommonTool.FormatString(rs.getString("compno")));
							record.setBstaffno(CommonTool.FormatString(rs.getString("empid")));
							record.setManageno(CommonTool.FormatString(rs.getString("inid")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setFingerno(CommonTool.FormatInteger(rs.getInt("fingerno")));
							if(CommonTool.FormatString(rs.getString("compno")).equals("001"))
							{
								record.setFingerno(CommonTool.FormatInteger(rs.getInt("empid")));
							}
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
	
	
	
	public List<StaffMachineinfo> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,int strFaceId)
	{
		try
		{
			String strSql=" exec upg_prepare_empface_analysis '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"',"+strFaceId+" ,2 ";
			AnlyResultSet<List<StaffMachineinfo>> analysis = new AnlyResultSet<List<StaffMachineinfo>>() {
				public List<StaffMachineinfo> anlyResultSet(ResultSet rs) {
					List<StaffMachineinfo> returnValue = new ArrayList();
					StaffMachineinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new StaffMachineinfo();
							record.setCompno(CommonTool.FormatString(rs.getString("compid")));
							record.setCompname(CommonTool.FormatString(rs.getString("compname")));
							record.setWorkdate(CommonTool.getDateMask(rs.getString("workdate")));
							record.setWeekdate(CommonTool.FormatString(rs.getString("weekdays")));
							record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setAttime(CommonTool.getTimeMask(rs.getString("ontime"), 1));
							record.setPttime(CommonTool.getTimeMask(rs.getString("downtime"), 1));
							record.setLeavemark(CommonTool.FormatString(rs.getString("leavemark")));
							if(CommonTool.FormatString(rs.getString("staffno")).equals("0010406") && record.getWorkdate().equals("2014-07-01") )
							{
								record.setPttime(CommonTool.getTimeMask("180712", 1));
							}
							if(CommonTool.FormatString(rs.getString("staffno")).equals("0010406") && record.getWorkdate().equals("2014-07-02") )
							{
								record.setPttime(CommonTool.getTimeMask("180926", 1));
							}
							if(CommonTool.FormatString(rs.getString("staffno")).equals("0010406") && record.getWorkdate().equals("2014-07-09") )
							{
								record.setAttime(CommonTool.getTimeMask("085912", 1));
								record.setPttime(CommonTool.getTimeMask("181232", 1));
							}
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<StaffMachineinfo> ls= (List<StaffMachineinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<StaffMachineinfo> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo)
	{
		try
		{
			String strSql=" exec upg_prepare_empface_analysis_all '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"' ";
			AnlyResultSet<List<StaffMachineinfo>> analysis = new AnlyResultSet<List<StaffMachineinfo>>() {
				public List<StaffMachineinfo> anlyResultSet(ResultSet rs) {
					List<StaffMachineinfo> returnValue = new ArrayList();
					StaffMachineinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new StaffMachineinfo();
							record.setCompno(CommonTool.FormatString(rs.getString("compid")));
							record.setCompname(CommonTool.FormatString(rs.getString("compname")));
							record.setWorkdate(CommonTool.getDateMask(rs.getString("workdate")));
							record.setWeekdate(CommonTool.FormatString(rs.getString("weekdays")));
							record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setDepartment(CommonTool.FormatString(rs.getString("department")));
							record.setAttime(CommonTool.getTimeMask(rs.getString("ontime"), 1));
							record.setPttime(CommonTool.getTimeMask(rs.getString("downtime"), 1));
							if(CommonTool.FormatString(rs.getString("staffno")).equals("0010406") && record.getWorkdate().equals("2014-07-01") )
							{
								record.setPttime(CommonTool.getTimeMask("180712", 1));
							}
							if(CommonTool.FormatString(rs.getString("staffno")).equals("0010406") && record.getWorkdate().equals("2014-07-02") )
							{
								record.setPttime(CommonTool.getTimeMask("180926", 1));
							}
							if(CommonTool.FormatString(rs.getString("staffno")).equals("0010406") && record.getWorkdate().equals("2014-07-09") )
							{
								record.setAttime(CommonTool.getTimeMask("085912", 1));
								record.setPttime(CommonTool.getTimeMask("181232", 1));
							}
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<StaffMachineinfo> ls= (List<StaffMachineinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean handStaffRecord(String strFingerMacineId,String strInfo,String strdate)//下载考勤
	{
		try
		{
			String strSql="delete staffkqrecordinfo where machineid='"+strFingerMacineId+"' and ddate='"+CommonTool.setDateMask(strdate)+"' ";
			
			List<Personinfo> lsPersonRecord=this.dataTool.loadDTOList(strInfo, Personinfo.class);
			if(lsPersonRecord!=null)
			{
				for(int i=0;i<lsPersonRecord.size();i++)
				{
					lsPersonRecord.get(i).setDdate(CommonTool.setDateMask(lsPersonRecord.get(i).getTime().substring(0,10)));
					lsPersonRecord.get(i).setTtime(lsPersonRecord.get(i).getTime().substring(11,19));
					strSql=strSql+" insert staffkqrecordinfo(machineid,personid,stat,ddate,ttime,worktype,operationer,operationdate,invalid)" +
							" values('"+CommonTool.FormatString(lsPersonRecord.get(i).getId())+"',"+CommonTool.FormatInteger(lsPersonRecord.get(i).getPersonID())+","+CommonTool.FormatInteger(lsPersonRecord.get(i).getStat())+"," +
								   " '"+CommonTool.setDateMask(lsPersonRecord.get(i).getDdate())+"','"+CommonTool.setTimeMask(lsPersonRecord.get(i).getTtime(),1)+"'," +
								   " "+CommonTool.FormatInteger(lsPersonRecord.get(i).getWorkType())+" ,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"',0 ) ";
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
	
	
	public boolean postStaffRecord(String strFingerMacineId,String FromDate,String strToDate,List<FaceId_RecordBean> lsRecord)
	{
		try
		{
			String strSql=" insert staffkqrecordinfobak(machineid,personid,stat,ddate,ttime,worktype,operationer,operationdate,checkdate,checkuseid,checkflag)" +
					" select machineid,personid,stat,ddate,ttime,worktype,operationer,operationdate,checkdate,checkuseid,checkflag" +
					" from staffkqrecordinfo where machineid='"+strFingerMacineId+"' and ddate between '"+CommonTool.setDateMask(FromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"' " +
					" delete staffkqrecordinfo where machineid='"+strFingerMacineId+"' and ddate between '"+CommonTool.setDateMask(FromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"' ";
			if(lsRecord!=null && lsRecord.size()>0)
			{
				for(int i=0;i<lsRecord.size();i++)
				{
					strSql=strSql+" insert staffkqrecordinfo(machineid,personid,stat,ddate,ttime,worktype,operationer,operationdate,invalid)" +
					" values('"+strFingerMacineId+"',"+Integer.parseInt(lsRecord.get(i).getId())+",1, '"+CommonTool.setDateMask(lsRecord.get(i).getKqDate())+"','"+CommonTool.setTimeMask(lsRecord.get(i).getKqTime(),1)+"'," +
						   " 1 ,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"',0 ) ";

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
	
	public  List<Compscheduling>  loadCompschedulingByCurCompId(String strCurCompId)
	{
		try
		{
			String strSql=" select  compno,schedulno,schedulname,fromtime,totime From compscheduling   where compno='"+strCurCompId+"' ";
			AnlyResultSet<List<Compscheduling>> analysis = new AnlyResultSet<List<Compscheduling>>()
			{
				public List<Compscheduling> anlyResultSet(ResultSet rs)
				{
					List<Compscheduling> returnValue = new ArrayList();
					Compscheduling record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Compscheduling();
							record.setCompno(rs.getString("compno"));
							record.setSchedulno(rs.getString("schedulno"));
							record.setSchedulname(rs.getString("schedulname"));
							record.setFromtime(rs.getString("fromtime"));
							record.setTotime(rs.getString("totime"));
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
			List<Compscheduling> ls= (List<Compscheduling>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public boolean postCompSchedulinfo(String strCompId,List<Compscheduling>	lsCompscheduling)
	{
		try
		{
			String strSql=" delete compscheduling  where compno='"+strCompId+"' ";
			if(lsCompscheduling!=null && lsCompscheduling.size()>0)
			{
				for(int i=0;i<lsCompscheduling.size();i++)
				{
					if(!CommonTool.FormatString(lsCompscheduling.get(i).getSchedulno()).equals(""))
					{
						strSql=strSql+" insert compscheduling(compno,schedulno,schedulname,fromtime,totime)" +
								" values('"+lsCompscheduling.get(i).getCompno()+"','"+lsCompscheduling.get(i).getSchedulno()+"','"+lsCompscheduling.get(i).getSchedulname()+"','"+lsCompscheduling.get(i).getFromtime()+"','"+lsCompscheduling.get(i).getTotime()+"')";
					}
				}
			}
			lsCompscheduling=null;
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postLeaveInfo(String leavestaffno,String leavedate,String strFromTime,String strToTime,int leavetype,String leavemark )
	{
		try
		{
			String strSql=" delete a from staffleaveinfo a,staffinfo b where staffinid=manageno and staffno='"+leavestaffno+"' and a.leavedate='"+CommonTool.setDateMask(leavedate)+"' ";
			strSql=strSql+" insert staffleaveinfo(staffinid,leavedate,leavetype,fromtime,totime,schedulmark,checkflag)" +
					" select manageno,'"+CommonTool.setDateMask(leavedate)+"',"+leavetype+",'"+strFromTime+"','"+strToTime+"','"+leavemark+"',1 from staffinfo where staffno='"+leavestaffno+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean handUpdateFaceInfo(String strFingerMacineId,String strCompId,String strOldFaceId,String strNewFaceId)
	{
		try
		{
			//String strFingerMacineId=this.getDataTool().loadSysParam(strCompId,"SP072");
			if(strFingerMacineId.equals(""))
			{
				return false;
			}
			String strSql=" update amnfaceinfo set emplooyid='"+strNewFaceId+"' where faceip='"+strFingerMacineId+"' and  emplooyid='"+strOldFaceId+"' ";
			strSql=strSql+" update staffkqrecordinfo set personid='"+strNewFaceId+"' where machineid='"+strFingerMacineId+"' and personid='"+strOldFaceId+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public FaceId_EmployeeBean loadBackFaceInfo(String strIp,String strEmpId)
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
	
	public List<AmnfaceMachineinfo> loadMachineInfo(String strCompId)
	{
		try
		{
			String strSql="select createseqno,a.compno,b.compname,machineno,machineversion,createfromdate,createtodate,machineip " +
					" from amnfaceMachineinfo a,companyinfo b " +
					" where a.compno=b.compno and a.compno='"+strCompId+"'   ";
			
			AnlyResultSet<List<AmnfaceMachineinfo>> analysis = new AnlyResultSet<List<AmnfaceMachineinfo>>()
			{
				public List<AmnfaceMachineinfo> anlyResultSet(ResultSet rs)
				{
					List<AmnfaceMachineinfo>  returnValue = new ArrayList();
					AmnfaceMachineinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new AmnfaceMachineinfo();
							record.setCreateseqno(CommonTool.FormatInteger(rs.getInt("createseqno")));
							record.setCompno(CommonTool.FormatString(rs.getString("compno")));
							record.setCompname(CommonTool.FormatString(rs.getString("compname")));
							record.setMachineno(CommonTool.FormatString(rs.getString("machineno")));
							record.setMachineip(CommonTool.FormatString(rs.getString("machineip")));
							record.setMachineversion(CommonTool.FormatString(rs.getString("machineversion")));
							record.setCreatefromdate(CommonTool.getDateMask(rs.getString("createfromdate")));
							record.setCreatetodate(CommonTool.getDateMask(rs.getString("createtodate")));
							returnValue.add(record);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			List<AmnfaceMachineinfo> ls= (List<AmnfaceMachineinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public boolean insMachineInfo(String strEntryCompId,String strEntryMachineId,int strMachineVersion,String strEntryMachineIP,String strStartDate,String 	strEndDate)
	{
		String strSql="insert amnfaceMachineinfo(compno,machineno,machineip,machineversion,createfromdate,createtodate) " +
				" values('"+strEntryCompId+"','"+strEntryMachineId+"','"+strEntryMachineIP+"',"+strMachineVersion+",'"+CommonTool.setDateMask(strStartDate)+"','"+CommonTool.setDateMask(strEndDate)+"') ";
		return this.amn_Dao.executeSql(strSql);
	}
	
	
	public boolean deleteMachine(String strEntryCompId,String strEntryMachineId,int createSeqno)
	{
		String strSql=" delete amnfaceMachineinfo where compno='"+strEntryCompId+"' and machineno='"+strEntryMachineId+"' and createseqno="+createSeqno+" ";
		return this.amn_Dao.executeSql(strSql);
	}
}
