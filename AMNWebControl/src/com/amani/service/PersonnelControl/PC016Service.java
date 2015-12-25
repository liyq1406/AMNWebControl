package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Staffinfodispatch;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC016Service extends AMN_ReportService{
	
	public List<Staffinfodispatch> loadDateSetByCompId(String strCompId,String strdepartment,String strFromDate,String strToDate)
	{
		try
		{
			String strSql="";
			
				strSql="select c.oldcompid,oldcompname=b.compname,c.oldempid,c.olddepid,c.oldpostion,c.oldyjtype,c.oldyjrate,c.oldyjamt,c.newcompid,newcompname=d.compname,a.staffname,a.staffsex,a.pccid,c.effectivedate,c.teffectivedate,dispatchstate  " +
						" from staffinfo a,companyinfo b,compchaininfo, staffinfodispatch c,companyinfo d " +
						" where c.newcompid=relationcomp and curcomp='"+strCompId+"' and c.oldcompid =b.compno and c.newcompid=d.compno and isnull(a.stafftype,0)=0 " +
						" and (isnull(olddepid,'')='"+strdepartment+"' or '"+strdepartment+"'='')  and a.manageno=c.manageno " +
						" and ( ( isnull(effectivedate,'')<='"+strFromDate+"' and isnull(teffectivedate,'')>='"+strFromDate+"' ) or ( isnull(effectivedate,'')<='"+strToDate+"' and isnull(teffectivedate,'')>='"+strToDate+"' ) or '"+strFromDate+"'='' )" +
						" order by a.compno,department,staffno ";
			
			AnlyResultSet<List<Staffinfodispatch>> analysis = new AnlyResultSet<List<Staffinfodispatch>>() {
				public List<Staffinfodispatch> anlyResultSet(ResultSet rs) {
					List<Staffinfodispatch> returnValue = new ArrayList();
					Staffinfodispatch record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Staffinfodispatch();
							record.setOldcompid(CommonTool.FormatString(rs.getString("oldcompid")));
							record.setOldcompname(CommonTool.FormatString(rs.getString("oldcompname")));
							record.setOldempid(CommonTool.FormatString(rs.getString("oldempid")));
							record.setOlddepid(CommonTool.FormatString(rs.getString("olddepid")));
							record.setOldpostion(CommonTool.FormatString(rs.getString("oldpostion")));
							record.setOldyjtype(CommonTool.FormatString(rs.getString("oldyjtype")));
							record.setOldyjrate(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("oldyjrate")))));
							record.setOldyjamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("oldyjamt")))));
							record.setNewcompid(CommonTool.FormatString(rs.getString("newcompid")));
							record.setNewcompname(CommonTool.FormatString(rs.getString("newcompname")));
							record.setOldempname(CommonTool.FormatString(rs.getString("staffname")));
							record.setStrpcid(CommonTool.FormatString(rs.getString("pccid")));
							record.setStaffsex(CommonTool.FormatInteger(rs.getInt("staffsex")));
							record.setEffectivedate(CommonTool.getDateMask(rs.getString("effectivedate")));
							record.setTeffectivedate(CommonTool.getDateMask(rs.getString("teffectivedate")));
							record.setDispatchstate(CommonTool.FormatInteger(rs.getInt("dispatchstate")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Staffinfodispatch> ls= (List<Staffinfodispatch>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	//1人事专员审核 2 人事经理审核 3 人事经理驳回
	public boolean handBill(int handtype, String strCompId,String strEmpid,String newcompid,String strFromDate,String strToDate)
	{
		try
		{
			String strSql="";
			if(handtype==1)
			{
				strSql=" update staffinfodispatch set checkinheadcompid='"+CommonTool.getLoginInfo("COMPID")+"', checkinheadstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,checkinheaddate='"+CommonTool.getCurrDate()+"',dispatchstate=1 where oldcompid='"+strCompId+"' and oldempid='"+strEmpid+"' and effectivedate='"+CommonTool.setDateMask(strFromDate)+"' and teffectivedate='"+CommonTool.setDateMask(strToDate)+"' and newcompid='"+newcompid+"'   ";
				
			}
			else if(handtype==2)
			{
				strSql=" update staffinfodispatch set checkinheadcompid='"+CommonTool.getLoginInfo("COMPID")+"', checkinheadstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,checkinheaddate='"+CommonTool.getCurrDate()+"',dispatchstate=2 where oldcompid='"+strCompId+"' and oldempid='"+strEmpid+"' and effectivedate='"+CommonTool.setDateMask(strFromDate)+"' and teffectivedate='"+CommonTool.setDateMask(strToDate)+"' and newcompid='"+newcompid+"' ";
				strSql=strSql+" if  exists (select 1 from staffinfo where  compno='"+strCompId+"' and staffno='9'+'"+strEmpid+"' )" +
				" begin delete staffinfo where  compno='"+strCompId+"' and staffno='9'+'"+strEmpid+"' end ";
				strSql=strSql+"   insert staffinfo(compno,staffno,staffname,staffsex,department,arrivaldate,pccid,aaddress,mobilephone,position,curstate,manageno,businessflag,banktype,bankno,stafftype,fingerno)  " +
				" select '"+newcompid+"','9'+staffno,staffname,staffsex,department,arrivaldate,pccid,aaddress,mobilephone,position,curstate,manageno,businessflag,banktype,bankno,1,fingerno " +
				" from staffinfo  where compno='"+strCompId+"' and staffno='"+strEmpid+"' " ;
			}
			else if(handtype==3)
			{
				strSql=" update staffinfodispatch set comfirmcompid='"+CommonTool.getLoginInfo("COMPID")+"', comfirmstaffid='"+CommonTool.getLoginInfo("USERID")+"' ,comfirmdate='"+CommonTool.getCurrDate()+"',dispatchstate=3 where oldcompid='"+strCompId+"' and oldempid='"+strEmpid+"' and effectivedate='"+CommonTool.setDateMask(strFromDate)+"' and teffectivedate='"+CommonTool.setDateMask(strToDate)+"' and newcompid='"+newcompid+"' ";
		
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
