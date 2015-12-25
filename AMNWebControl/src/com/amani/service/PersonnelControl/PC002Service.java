package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.dll.FingerTool;
import com.amani.model.Staffchangeinfo;
import com.amani.model.Staffinfo;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Service
public class PC002Service extends AMN_ReportService{
	
	public List<Staffchangeinfo> loadDateSetByCompId(String strCompId,String strFromDate,String strToDate,int iSearchType,int iSearchState ,String strCurStaffNo)
	{
		try
		{
			String strSql=" select changecompid,changebillid,changedate,changetype,billflag,appchangecompid,beforecompname=b.compname,changestaffno,staffname,staffpcid,staffphone," +
						  " beforedepartment,beforepostation,beforesalary,beforeyejitype,beforeyejirate,beforeyejiamt,aftercompid,aftercompname=d.compname,afterstaffno,afterdepartment,afterpostation,aftersalary,afteryejitype,afteryejirate,afteryejiamt," +
						  " arrivaldate,validatestartdate,validateenddate,a.remark ," +
						  " checkstaffid,checkdate,checkflag,checkinheadstaffid,checkinheaddate,checkinheadflag,comfirmstaffid,comfirmdate,comfirmflag " +
						  "  from companyinfo b,compchaininfo,staffinfo c ,staffchangeinfo a left join companyinfo d on   aftercompid=d.compno "+
						"	where  appchangecompid=b.compno and changedate between '"+strFromDate+"' and '"+strToDate+"' " +
						"	and curcomp='"+strCompId+"' and relationcomp=appchangecompid and a.staffmangerno=c.manageno and isnull(c.stafftype,0)=0 " +
						"   and (isnull(changetype,0)="+iSearchType+" or ("+iSearchType+"=7 and isnull(changetype,0) in (5,6) ))" +
						" 	and (isnull(billflag,0)="+iSearchState+" or "+iSearchState+"=(-1) ) " +
						" 	and (isnull(changestaffno,'')='"+strCurStaffNo+"'  or isnull(afterstaffno,'')='"+strCurStaffNo+"' or '"+strCurStaffNo+"'='' ) " +
						"   order by changecompid,changetype,changedate  desc " ;
			
			AnlyResultSet<List<Staffchangeinfo>> analysis = new AnlyResultSet<List<Staffchangeinfo>>() {
				public List<Staffchangeinfo> anlyResultSet(ResultSet rs) {
					List<Staffchangeinfo> returnValue = new ArrayList();
					Staffchangeinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Staffchangeinfo();
							record.setBchangecompid(CommonTool.FormatString(rs.getString("changecompid")));
							record.setBchangebillid(CommonTool.FormatString(rs.getString("changebillid")));
							record.setBchangetype(CommonTool.FormatInteger(rs.getInt("changetype")));
							record.setBillflag(CommonTool.FormatInteger(rs.getInt("billflag")));
							record.setChangedate(CommonTool.getDateMask(rs.getString("changedate")));
							record.setAppchangecompid(CommonTool.FormatString(rs.getString("appchangecompid")));
							record.setAppchangecompname(CommonTool.FormatString(rs.getString("beforecompname")));
							record.setChangestaffno(CommonTool.FormatString(rs.getString("changestaffno")));
							record.setChangestaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setStaffpcid(CommonTool.FormatString(rs.getString("staffpcid")));
							record.setStaffphone(CommonTool.FormatString(rs.getString("staffphone")));
							record.setBeforedepartment(CommonTool.FormatString(rs.getString("beforedepartment")));
							record.setBeforepostation(CommonTool.FormatString(rs.getString("beforepostation")));
							record.setBeforesalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beforesalary")))));
							record.setBeforeyejitype(CommonTool.FormatString(rs.getString("beforeyejitype")));
							record.setBeforeyejirate(CommonTool.FormatBigDecimalT(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beforeyejirate")))));
							record.setBeforeyejiamt(CommonTool.FormatBigDecimalT(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beforeyejiamt")))));
							record.setAftercompid(CommonTool.FormatString(rs.getString("aftercompid")));
							record.setAftercompname(CommonTool.FormatString(rs.getString("aftercompname")));
							record.setAfterstaffno(CommonTool.FormatString(rs.getString("afterstaffno")));
							record.setAfterdepartment(CommonTool.FormatString(rs.getString("afterdepartment")));
							record.setAfterpostation(CommonTool.FormatString(rs.getString("afterpostation")));
							record.setAftersalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("aftersalary")))));
							record.setAfteryejitype(CommonTool.FormatString(rs.getString("afteryejitype")));
							record.setAfteryejirate(CommonTool.FormatBigDecimalT(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("afteryejirate")))));
							record.setAfteryejiamt(CommonTool.FormatBigDecimalT(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("afteryejiamt")))));
							record.setArrivaldate(CommonTool.getDateMask(rs.getString("arrivaldate")));
							record.setValidatestartdate(CommonTool.getDateMask(rs.getString("validatestartdate")));
							record.setValidateenddate(CommonTool.getDateMask(rs.getString("validateenddate")));
							
							record.setCheckstaffid(CommonTool.FormatString(rs.getString("checkstaffid")));
							record.setCheckdate(CommonTool.getDateMask(rs.getString("checkdate")));
							record.setCheckflag(CommonTool.FormatInteger(rs.getInt("checkflag")));
							
							record.setCheckinheadstaffid(CommonTool.FormatString(rs.getString("checkinheadstaffid")));
							record.setCheckinheaddate(CommonTool.getDateMask(rs.getString("checkinheaddate")));
							record.setCheckinheadflag(CommonTool.FormatInteger(rs.getInt("checkinheadflag")));
							
							record.setComfirmstaffid(CommonTool.FormatString(rs.getString("comfirmstaffid")));
							record.setComfirmdate(CommonTool.getDateMask(rs.getString("comfirmdate")));
							record.setComfirmflag(CommonTool.FormatInteger(rs.getInt("comfirmflag")));
							
							
							record.setRemark(CommonTool.FormatString(rs.getString("remark")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Staffchangeinfo> ls= (List<Staffchangeinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Staffchangeinfo> loadMasterByCompId()
	{
		try
		{
			String strSql="select changecompid,changebillid,changedate,changetype,billflag,appchangecompid,beforecompname=b.compname,changestaffno,staffname,staffpcid,staffphone," +
					" beforedepartment,beforepostation,beforesalary,beforeyejitype,beforeyejirate,beforeyejiamt,aftercompid,aftercompname=d.compname,afterstaffno,afterdepartment,afterpostation,aftersalary,afteryejitype,afteryejirate,afteryejiamt," +
					 " arrivaldate,validatestartdate,validateenddate,a.remark ," +
					  " checkstaffid,checkdate,checkflag,checkinheadstaffid,checkinheaddate,checkinheadflag,comfirmstaffid,comfirmdate,comfirmflag " +
					  "  from companyinfo b,compchaininfo,staffinfo c ,staffchangeinfo a left join companyinfo d on   aftercompid=d.compno "+
						"	where appchangecompid=b.compno and isnull(billflag,0) not in (5,8) " +
						"	and curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=appchangecompid and a.staffmangerno=c.manageno and isnull(c.stafftype,0)=0  and isnull(changetype,0) in (5,6)  " +
						"   order by changecompid,changetype,changedate  desc " ;
			
			AnlyResultSet<List<Staffchangeinfo>> analysis = new AnlyResultSet<List<Staffchangeinfo>>() {
				public List<Staffchangeinfo> anlyResultSet(ResultSet rs) {
					List<Staffchangeinfo> returnValue = new ArrayList();
					Staffchangeinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Staffchangeinfo();
							record.setBchangecompid(CommonTool.FormatString(rs.getString("changecompid")));
							record.setBchangebillid(CommonTool.FormatString(rs.getString("changebillid")));
							record.setBchangetype(CommonTool.FormatInteger(rs.getInt("changetype")));
							record.setBillflag(CommonTool.FormatInteger(rs.getInt("billflag")));
							record.setChangedate(CommonTool.getDateMask(rs.getString("changedate")));
							record.setAppchangecompid(CommonTool.FormatString(rs.getString("appchangecompid")));
							record.setAppchangecompname(CommonTool.FormatString(rs.getString("beforecompname")));
							record.setChangestaffno(CommonTool.FormatString(rs.getString("changestaffno")));
							record.setChangestaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setStaffpcid(CommonTool.FormatString(rs.getString("staffpcid")));
							record.setStaffphone(CommonTool.FormatString(rs.getString("staffphone")));
							record.setBeforedepartment(CommonTool.FormatString(rs.getString("beforedepartment")));
							record.setBeforepostation(CommonTool.FormatString(rs.getString("beforepostation")));
							record.setBeforesalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beforesalary")))));
							record.setBeforeyejitype(CommonTool.FormatString(rs.getString("beforeyejitype")));
							record.setBeforeyejirate(CommonTool.FormatBigDecimalT(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beforeyejirate")))));
							record.setBeforeyejiamt(CommonTool.FormatBigDecimalT(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beforeyejiamt")))));
							record.setAftercompid(CommonTool.FormatString(rs.getString("aftercompid")));
							record.setAftercompname(CommonTool.FormatString(rs.getString("aftercompname")));
							record.setAfterstaffno(CommonTool.FormatString(rs.getString("afterstaffno")));
							record.setAfterdepartment(CommonTool.FormatString(rs.getString("afterdepartment")));
							record.setAfterpostation(CommonTool.FormatString(rs.getString("afterpostation")));
							record.setAftersalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("aftersalary")))));
							record.setAfteryejitype(CommonTool.FormatString(rs.getString("afteryejitype")));
							record.setAfteryejirate(CommonTool.FormatBigDecimalT(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("afteryejirate")))));
							record.setAfteryejiamt(CommonTool.FormatBigDecimalT(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("afteryejiamt")))));
							record.setArrivaldate(CommonTool.getDateMask(rs.getString("arrivaldate")));
							record.setValidatestartdate(CommonTool.getDateMask(rs.getString("validatestartdate")));
							record.setValidateenddate(CommonTool.getDateMask(rs.getString("validateenddate")));
							record.setCheckstaffid(CommonTool.FormatString(rs.getString("checkstaffid")));
							record.setCheckdate(CommonTool.getDateMask(rs.getString("checkdate")));
							record.setCheckflag(CommonTool.FormatInteger(rs.getInt("checkflag")));
							
							record.setCheckinheadstaffid(CommonTool.FormatString(rs.getString("checkinheadstaffid")));
							record.setCheckinheaddate(CommonTool.getDateMask(rs.getString("checkinheaddate")));
							record.setCheckinheadflag(CommonTool.FormatInteger(rs.getInt("checkinheadflag")));
							
							record.setComfirmstaffid(CommonTool.FormatString(rs.getString("comfirmstaffid")));
							record.setComfirmdate(CommonTool.getDateMask(rs.getString("comfirmdate")));
							record.setComfirmflag(CommonTool.FormatInteger(rs.getInt("comfirmflag")));
							record.setRemark(CommonTool.FormatString(rs.getString("remark")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Staffchangeinfo> ls= (List<Staffchangeinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//1门店审核 2 人事审核 3 人事经理审核 5 人事经理
	public boolean handBill(String strCompId,String strBillId,int iChangeType,int handtype,StringBuffer strBufferMsg)
	{
		try
		{
			String strSql="";
			if(handtype==1)
			{
				strSql=" update staffchangeinfo set checkcompid='"+CommonTool.getLoginInfo("COMPID")+"', checkstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,checkdate='"+CommonTool.getCurrDate()+"',checkflag=1,billflag=1 where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype="+iChangeType+" ";
				strBufferMsg.append("");
			}
			else if(handtype==2)
			{
				strSql=" update staffchangeinfo set checkinheadcompid='"+CommonTool.getLoginInfo("COMPID")+"', checkinheadstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,checkinheaddate='"+CommonTool.getCurrDate()+"',checkinheadflag=1,billflag=2 where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype="+iChangeType+" ";
				strBufferMsg.append("");
			}
			else if(handtype==3)
			{
				strSql=" update staffchangeinfo set comfirmcompid='"+CommonTool.getLoginInfo("COMPID")+"', comfirmstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,comfirmdate='"+CommonTool.getCurrDate()+"',comfirmflag=1,billflag=3 where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype="+iChangeType+" ";
				//strBufferMsg.append("");
				//strBufferMsg.append(handStafFinger(strCompId,strBillId));
			}
			else if(handtype==5)
			{
				strSql=" update staffchangeinfo set comfirmcompid='"+CommonTool.getLoginInfo("COMPID")+"', comfirmstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,comfirmdate='"+CommonTool.getCurrDate()+"',comfirmflag=1,billflag=5 where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype="+iChangeType+" ";
				strBufferMsg.append("");
			}
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean handValidateBill(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" exec upg_hand_personManger_anon '"+strCompId+"','"+strBillId+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	public String handStafFinger(String strCompId,String strBillId)
	{
		ResultSet rs=null;
		try
		{
			String strSql=" select appchangecompid,aftercompid,fingerno,staffname from staffchangeinfo ,staffinfo" +
					" where  changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and staffmangerno=manageno ";
			rs=this.amn_Dao.executeQuery(strSql);
			if(rs!=null && rs.next())
			{
				String stOldCompId=CommonTool.FormatString(rs.getString("appchangecompid"));
				String strNewCompId=CommonTool.FormatString(rs.getString("aftercompid"));
				String strStaffName=CommonTool.FormatString(rs.getString("staffname"));
				int strFingerno=CommonTool.FormatInteger(rs.getInt("fingerno"));
				int strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.getDataTool().loadSysParam(strNewCompId,"SP072")));
				
				String strFingerMacineIp=this.getDataTool().loadSysParam(strNewCompId,"SP073");
				if(CommonTool.FormatString(strFingerMacineIp).equals("") || CommonTool.FormatInteger(strFingerno)==0)
				{
					return "";
				}
				FingerTool fingerTool=new FingerTool();
				int result=fingerTool.CKT_RegisterNet_local(strFingerMacineId,strFingerMacineIp);
				if(result==0)
				{
					return "连接门店考勤机失败,请核实系统指纹信息设置";
					 
				}
				result=fingerTool.CKT_NetDaemon_local();
				result=fingerTool.CKT_ModifyPersonInfo_local(strFingerMacineId,strFingerno,"",0,strStaffName,0,0,0,0,0);
				if(result==0)
				{
					return "创建员工失败";
				}
				String strUploadFilename="D:/finger/"+strFingerno+"_0.anv";
				result=fingerTool.CKT_PutFPTemplateLoadFile_local(strFingerMacineId,strFingerno,0,strUploadFilename);
				if(result==0)
				{
					return "备份员工第一个指纹失败";
					
				}
				strUploadFilename="D:/finger/"+strFingerno+"_1.anv";
				result=fingerTool.CKT_PutFPTemplateLoadFile_local(strFingerMacineId,strFingerno,1,strUploadFilename);
				if(result==0)
				{
					return "备份员工第二个指纹失败";
				
				}
				/*if(!stOldCompId.equals("001"))
				{
					strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.getDataTool().loadSysParam(stOldCompId,"SP072")));
					result=fingerTool.CKT_DeletePersonInfo_local(strFingerMacineId, strFingerno);
					if(result==0)
					{
						return "删除原门店指纹失败";
					
					}
				}*/
				fingerTool.CKT_Disconnect_local();
				fingerTool=null;
			}
			return "";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "操作异常";
		}
		finally
		{
			try
			{
				if(rs!=null)
					rs.close();
				rs=null;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
		}
	}
	
}
