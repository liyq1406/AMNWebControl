package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Staffchangeinfo;
import com.amani.model.Staffinfo;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC003Service extends AMN_ReportService{
	
	public List<Staffchangeinfo> loadDateSetByCompId(String strCompId,String strFromDate,String strToDate,int iSearchType,int iSearchState,String strCurStaffNo )
	{
		try
		{
			String strSql=" select changecompid,changebillid,changedate,changetype,billflag,appchangecompid,beforecompname=b.compname,changestaffno,staffname,staffpcid,staffphone," +
						  " beforedepartment,beforepostation,beforesalary,beforeyejitype,beforeyejirate,beforeyejiamt,aftercompid,aftercompname=d.compname,afterstaffno,afterdepartment,afterpostation,aftersalary,afteryejitype,afteryejirate,afteryejiamt," +
						  " arrivaldate,validatestartdate,validateenddate,a.remark ," +
						  " checkstaffid,checkdate,checkflag,checkinheadstaffid,checkinheaddate,checkinheadflag,comfirmstaffid,comfirmdate,comfirmflag " +
						  "  from staffinfo c,compchaininfo, staffchangeinfo a " +
						  " left join companyinfo b on   appchangecompid=b.compno "+
						  " left join companyinfo d on   aftercompid=d.compno "+
						  //" left join compchaininfo on   curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=appchangecompid "+
							"	where    changedate between '"+strFromDate+"' and '"+strToDate+"' " +
						"	and  a.staffmangerno=c.manageno and isnull(c.stafftype,0)=0  " +
						"   and (isnull(changetype,0)="+iSearchType+" or ("+iSearchType+"=7 and isnull(changetype,0) in (0,1,2,3,4) ))" +
						" 	and (isnull(billflag,0)="+iSearchState+" or "+iSearchState+"=(-1) ) " +
						"   and curcomp='"+strCompId+"' and (case when appchangecompid='99999' then changecompid else appchangecompid end )=relationcomp   "+
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
					  "  from staffinfo c ,compchaininfo,staffchangeinfo a " +
					  " left join companyinfo b on   appchangecompid=b.compno "+
					  " left join companyinfo d on   aftercompid=d.compno " +
					   // " left join compchaininfo on   curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=appchangecompid "+
						"	where isnull(billflag,0) not in (5,8) " +
						"	and a.staffmangerno=c.manageno and isnull(c.stafftype,0)=0  and isnull(changetype,0) in (0,1,2,3,4)  " +
						"   and curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and (case when appchangecompid='99999' then changecompid else appchangecompid end )=relationcomp   "+
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
	
	//1门店审核 2 人事审核 3 人事经理审核 5 人事经理驳回
	public boolean handBill(String strCompId,String strBillId,int iChangeType,int handtype)
	{
		try
		{
			String strSql="";
			if(handtype==1)
				strSql=" update staffchangeinfo set checkcompid='"+CommonTool.getLoginInfo("COMPID")+"', checkstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,checkdate='"+CommonTool.getCurrDate()+"',checkflag=1,billflag=1 where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype="+iChangeType+" ";
			else if(handtype==2)
				strSql=" update staffchangeinfo set checkinheadcompid='"+CommonTool.getLoginInfo("COMPID")+"', checkinheadstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,checkinheaddate='"+CommonTool.getCurrDate()+"',checkinheadflag=1,billflag=2 where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype="+iChangeType+" ";
			else if(handtype==3)
				strSql=" update staffchangeinfo set comfirmcompid='"+CommonTool.getLoginInfo("COMPID")+"', comfirmstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,comfirmdate='"+CommonTool.getCurrDate()+"',comfirmflag=1,billflag=3 where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype="+iChangeType+" ";
			else if(handtype==5)
			{
				strSql=" update staffchangeinfo set comfirmcompid='"+CommonTool.getLoginInfo("COMPID")+"', comfirmstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,comfirmdate='"+CommonTool.getCurrDate()+"',comfirmflag=1,billflag=5 where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype="+iChangeType+" ";
				if(iChangeType==1) //离职驳回后正常在职
				{
					strSql=strSql+" update staffinfo set curstate=2  from staffinfo,staffchangeinfo " +
					" where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype="+iChangeType+"  and manageno=staffmangerno ";
				}
				else if(iChangeType==2) //入职驳回后删除工号
				{
					strSql=strSql+" delete staffinfo  from staffinfo,staffchangeinfo " +
					" where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype="+iChangeType+"  and manageno=staffmangerno ";
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
