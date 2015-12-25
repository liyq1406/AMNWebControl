package com.amani.service.PersonnelControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.FaceId_RecordBean;
import com.amani.model.StaffMachineinfo;
import com.amani.model.Personinfo;
import com.amani.model.Sysuserinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class PC001Service  extends AMN_ModuleService{
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}
	//按钮查询
	public List<StaffMachineinfo>  selectStaffMachineinfo(String strPersonId,String strDdate,String strDdatelast){ 
		
		try
		{
			String strFaceId="";
			String strSql="select fingerno from sysuserinfo a ,staffinfo b where a.frominnerno=b.manageno and a.userno='"+strPersonId+"'   ";
			AnlyResultSet<String> analysisx = new AnlyResultSet<String>()
			{
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue = "";
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatString(rs.getString("fingerno"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  "";
					}
					return returnValue;
				}
			};
			strFaceId=  (String)this.amn_Dao.executeQuery_ex(strSql,analysisx);
			analysisx=null;
			if(CommonTool.FormatString(strFaceId).equals("") && CommonTool.isDigit(strPersonId)==true)
				strFaceId=strPersonId;
			if(CommonTool.FormatString(strFaceId).equals(""))
				return null;
			strSql=" exec upg_prepare_empface_analysis '"+CommonTool.getLoginInfo("COMPID")+"','"+CommonTool.setDateMask(strDdate)+"','"+CommonTool.setDateMask(strDdatelast)+"',"+strFaceId+",1 ";
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
							record.setCheckuseid(CommonTool.FormatString(rs.getString("checkuseid")));
							record.setCheckdate(CommonTool.getDateMask(rs.getString("checkdate")));
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
	
	
	public List<Sysuserinfo>  loadMangerPersByUserId(String strPersonId){ 
		
		try
		{
			String strFaceId="";
			String strSql="select cstaffno,staffname from staffinfomanger  where curstaffno='"+strPersonId+"'   ";
			AnlyResultSet<List<Sysuserinfo>> analysisx = new AnlyResultSet<List<Sysuserinfo>>()
			{
				public List<Sysuserinfo> anlyResultSet(ResultSet rs)
				{
					List<Sysuserinfo> returnValue = new ArrayList();
					try
					{
						Sysuserinfo record=null;
						while(rs != null && rs.next()==true)
						{
							record=new Sysuserinfo();
							record.setUserno(CommonTool.FormatString(rs.getString("cstaffno")));
							record.setUsername(CommonTool.FormatString(rs.getString("staffname")));
							returnValue.add(record);
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue = null;
					}
					return returnValue;
				}
			};
			List<Sysuserinfo> ls=  (List<Sysuserinfo>)this.amn_Dao.executeQuery_ex(strSql,analysisx);
			analysisx=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	//列表查询
	public List<StaffMachineinfo>  selectListStaffMachineinfo(int strPersonid,String strDdate,String strDdatelast){
		String queryStr ="select machineid,compid,staffno,staffname,ddate,attime=min(case when ttime>'130000' then '无' else ttime end ),ptime=MAX(case when ttime>'130000' then ttime else '' end)" +
				"  from staffinfo ,staffkqrecordinfo left join sysparaminfo on paramvalue=machineid " +
				"  where fingerno=personid and paramid='SP072'  and fingerno="+strPersonid+"   " +
				"  and (ddate between '"+CommonTool.setDateMask(strDdate)+"' and '"+CommonTool.setDateMask(strDdatelast)+"'  or  '"+CommonTool.setDateMask(strDdate)+"'='' ) " +
				"  group by machineid,compid,staffno,staffname,ddate " +
				"  order by staffno,ddate ";
		AnlyResultSet<List<StaffMachineinfo>> analysis = new AnlyResultSet<List<StaffMachineinfo>>() {
			List<StaffMachineinfo> ls=new ArrayList<StaffMachineinfo>();
			public List<StaffMachineinfo> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						StaffMachineinfo staffmachineinfo = new StaffMachineinfo();
						staffmachineinfo.setMachineid(CommonTool.FormatString(rs.getString("machineid")));
						staffmachineinfo.setCompno(CommonTool.FormatString(rs.getString("compid")));
						staffmachineinfo.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
						staffmachineinfo.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
						staffmachineinfo.setDdate(CommonTool.getDateMask(rs.getString("ddate")));
						if(CommonTool.FormatString(rs.getString("attime")).equals("无"))
						{
							staffmachineinfo.setAttime("无");
						}
						else
						{
							staffmachineinfo.setAttime(CommonTool.getTimeMask(rs.getString("attime"),1));
						}
						staffmachineinfo.setPttime(CommonTool.getTimeMask(rs.getString("ptime"),1));
						ls.add(staffmachineinfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<StaffMachineinfo> ls  =(List<StaffMachineinfo>)this.amn_Dao.executeQuery_ex(queryStr, analysis);
		analysis=null;
		return ls;
	}
	
	public List<StaffMachineinfo>  selectListFaceStaffMachineinfo(String strCurCompId,int strPersonid,String strDdate,String strDdatelast){
		String queryStr ="select machineid,compid,staffno,staffname,ddate,attime=min(case when ttime>'130000' then '无' else ttime end ),ptime=MAX(case when ttime>'130000' then ttime else '' end)" +
		"  from staffinfo ,staffkqrecordinfo left join sysparaminfo on paramvalue=machineid " +
		"  where fingerno=personid and paramid='SP072'  and fingerno="+strPersonid+" " +
		"  and (ddate between '"+CommonTool.setDateMask(strDdate)+"' and '"+CommonTool.setDateMask(strDdatelast)+"'  or  '"+CommonTool.setDateMask(strDdate)+"'='' ) " +
		"  group by machineid,compid,staffno,staffname,ddate " +
		"  order by staffno,ddate ";
		
		AnlyResultSet<List<StaffMachineinfo>> analysis = new AnlyResultSet<List<StaffMachineinfo>>() {
			List<StaffMachineinfo> ls=new ArrayList<StaffMachineinfo>();
			public List<StaffMachineinfo> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						StaffMachineinfo staffmachineinfo = new StaffMachineinfo();
						staffmachineinfo.setMachineid(CommonTool.FormatString(rs.getString("machineid")));
						staffmachineinfo.setCompno(CommonTool.FormatString(rs.getString("compid")));
						staffmachineinfo.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
						staffmachineinfo.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
						staffmachineinfo.setDdate(CommonTool.getDateMask(rs.getString("ddate")));
						if(CommonTool.FormatString(rs.getString("attime")).equals("无"))
						{
							staffmachineinfo.setAttime("无");
						}
						else
						{
							staffmachineinfo.setAttime(CommonTool.getTimeMask(rs.getString("attime"),1));
						}
						staffmachineinfo.setPttime(CommonTool.getTimeMask(rs.getString("ptime"),1));
						ls.add(staffmachineinfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<StaffMachineinfo> ls  =(List<StaffMachineinfo>)this.amn_Dao.executeQuery_ex(queryStr, analysis);
		analysis=null;
		return ls;
	}
	
	public boolean handStaffRecord(int strFingerMacineId,String strInfo,String strdate)//下载考勤
	{
		try
		{
			String strSql="delete staffkqrecordinfo where machineid="+strFingerMacineId+" and ddate='"+CommonTool.setDateMask(strdate)+"' ";
			
			List<Personinfo> lsPersonRecord=this.dataTool.loadDTOList(strInfo, Personinfo.class);
			if(lsPersonRecord!=null)
			{
				for(int i=0;i<lsPersonRecord.size();i++)
				{
					lsPersonRecord.get(i).setDdate(CommonTool.setDateMask(lsPersonRecord.get(i).getTime().substring(0,10)));
					lsPersonRecord.get(i).setTtime(lsPersonRecord.get(i).getTime().substring(11,19));
					strSql=strSql+" insert staffkqrecordinfo(machineid,personid,stat,ddate,ttime,worktype,operationer,operationdate,invalid)" +
							" values("+CommonTool.FormatInteger(lsPersonRecord.get(i).getId())+","+CommonTool.FormatInteger(lsPersonRecord.get(i).getPersonID())+","+CommonTool.FormatInteger(lsPersonRecord.get(i).getStat())+"," +
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
	
	public boolean postStaffRecord(int strFingerMacineId,String FromDate,String strToDate,List<FaceId_RecordBean> lsRecord)
	{
		try
		{
			String strSql="delete staffkqrecordinfo where machineid="+strFingerMacineId+" and ddate between '"+CommonTool.setDateMask(FromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"' ";
			if(lsRecord!=null && lsRecord.size()>0)
			{
				for(int i=0;i<lsRecord.size();i++)
				{
					strSql=strSql+" insert staffkqrecordinfo(machineid,personid,stat,ddate,ttime,worktype,operationer,operationdate,invalid)" +
					" values("+strFingerMacineId+","+Integer.parseInt(lsRecord.get(i).getId())+",1, '"+CommonTool.setDateMask(lsRecord.get(i).getKqDate())+"','"+CommonTool.setTimeMask(lsRecord.get(i).getKqTime(),1)+"'," +
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
	public boolean uploadStoreFingerId(int strPersonID,String strInfo){	//上传指纹
		
		try 
		{
			List<Personinfo> lsPersonRecord=this.dataTool.loadDTOList(strInfo, Personinfo.class);
			if(lsPersonRecord!=null)
			{
				for(int i=0;i<lsPersonRecord.size();i++)
				{
					lsPersonRecord.get(i).setPersonID(CommonTool.FormatInteger(lsPersonRecord.get(i).getPersonID()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;			
		}
		return false;

	}
	
	public boolean comfrimBillByDate(String strStaffNo,String strConfirmDate)
	{
		try
		{
			String strFaceId="";
			String strSql="select b.staffno from sysuserinfo a ,staffinfo b where a.frominnerno=b.manageno and a.userno='"+strStaffNo+"'   ";
			AnlyResultSet<String> analysisx = new AnlyResultSet<String>()
			{
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue = "";
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatString(rs.getString("staffno"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  "";
					}
					return returnValue;
				}
			};
			strFaceId=  (String)this.amn_Dao.executeQuery_ex(strSql,analysisx);
			analysisx=null;
			if(CommonTool.FormatString(strFaceId).equals("") && CommonTool.isDigit(strStaffNo)==true)
				strFaceId=strStaffNo;
			if(CommonTool.FormatString(strFaceId).equals(""))
				return true;
			strSql="select manageno from staffinfo b where staffno='"+strFaceId+"'   ";
			analysisx = new AnlyResultSet<String>()
			{
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue = "";
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatString(rs.getString("manageno"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  "";
					}
					return returnValue;
				}
			};
			String strMangerno=  (String)this.amn_Dao.executeQuery_ex(strSql,analysisx);
			analysisx=null;
			String[] lsDate=strConfirmDate.split(",");
			strSql=" update  staffleaveinfo set  checkdate='"+CommonTool.getCurrDate()+"',checkuseid='"+CommonTool.getLoginInfo("USERID")+"', checkflag=1 " +
					" where staffinid='"+strMangerno+"' and  leavedate in ( '' ";
			if(lsDate!=null && lsDate.length>0)
			{
				for(int i=0;i<lsDate.length;i++)
				{
					strSql=strSql+",'"+CommonTool.setDateMask(lsDate[i])+"' ";
				}
			}
			strSql=strSql+" )";
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
			strSql=strSql+" insert staffleaveinfo(staffinid,leavedate,leavetype,fromtime,totime,schedulmark)" +
					" select manageno,'"+CommonTool.setDateMask(leavedate)+"',"+leavetype+",'"+strFromTime+"','"+strToTime+"','"+leavemark+"' from staffinfo where staffno='"+leavestaffno+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
}
//	public boolean handStaffRecord(int strFingerMacineId,String strInfo)
//	{
//			boolean bte=true;
////			String strSql=" update staffkqrecordinfo set invalid=1 where machineid="+strFingerMacineId+" and substring(ddate,1,6)="+CommonTool.getCurrDate().substring(0,6);
////			this.amn_Dao.executeSql(strSql);
////			strSql="";
//			String strSql="";
//			List<Personinfo> IsPersoninfo=this.dataTool.loadDTOList(strInfo, Personinfo.class);
//			if(IsPersoninfo!=null)
//			{
//				for(int i=0;i<IsPersoninfo.size();i++)
//				{
//					IsPersoninfo.get(i).setDdate(CommonTool.setDateMask(IsPersoninfo.get(i).getDdate().substring(0,10)));
//					IsPersoninfo.get(i).setTtime(IsPersoninfo.get(i).getTtime().substring(11,18));
//					strSql="insert staffkqrecordinfo(machineid,personid,stat,ddate,ttime,worktype,operationer,operationdate,invalid)" +
//							" values("+CommonTool.FormatInteger(IsPersoninfo.get(i).getFingerMacineId())+","+CommonTool.FormatInteger(IsPersoninfo.get(i).getPersonID())+","+CommonTool.FormatInteger(IsPersoninfo.get(i).getStat())+"," +
//								   " '"+CommonTool.FormatString(IsPersoninfo.get(i).getDdate())+"','"+CommonTool.FormatString(IsPersoninfo.get(i).getTtime())+"'," +
//								   " "+CommonTool.FormatInteger(IsPersoninfo.get(i).getWorktype())+" ,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"',0 ) ";
//				}
//				System.out.println(strSql);
//			}
//			try
//			{
//			if(bte==true)
//				return this.amn_Dao.executeSql(strSql);
//			}
//		catch(Exception ex)
//	 	{
//			ex.printStackTrace();
//		}
//			return bte;
//	}
//
//}


