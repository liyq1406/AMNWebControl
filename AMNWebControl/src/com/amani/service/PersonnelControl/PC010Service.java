package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Staffinfo;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC010Service extends AMN_ReportService{
	
	public List<Staffinfo> loadDateSetByCompId(String strCompId,String strdepartment,String strpostion,int socialsourceflag)
	{
		try
		{
			String strSql="";
			
				strSql="select a.compno,b.compname,staffno,staffname,staffsex,department,departmentname=c.parentcodevalue,position,positiontitle,positionname=d.parentcodevalue,socialsource=e.parentcodevalue,arrivaldate,fillno,contractdate,pccid,aaddress,mobilephone,businessflag,resulttye,basesalary,socialsecurity,remark" +
						" from companyinfo b,compchaininfo ,commoninfo c,commoninfo d ,staffinfo a " +
						" left join commoninfo e on  e.infotype='SBGS' and e.parentcodekey=a.socialsource " +
						" where a.compno=relationcomp and curcomp='"+strCompId+"' and a.compno =b.compno and isnull(a.stafftype,0)=0 " +
						 "  and (isnull(department,'')='"+strdepartment+"' or '"+strdepartment+"'='')" +
						 "  and (isnull(position,'')='"+strpostion+"' or '"+strpostion+"'='')" +
						 " and c.infotype='BMZL' and c.parentcodekey=a.department and d.infotype='GZGW' and d.parentcodekey=a.position " +
						 "  and staffno not in (LTRIM(a.compno)+'300',LTRIM(a.compno)+'400',LTRIM(a.compno)+'500',LTRIM(a.compno)+'600')  ";
				 if(CommonTool.FormatInteger(socialsourceflag)==1)
				 {
					 strSql=strSql+" and isnull(socialsecurity,0)>0 ";
				 }
				 else if(CommonTool.FormatInteger(socialsourceflag)==2)
				 {
					 strSql=strSql+" and isnull(socialsecurity,0)=0 ";
				 }
				 
			if(CommonTool.FormatInteger(socialsourceflag)==1)  //只查有社保的人 需要增加离职的人
			{
				String strMonth=CommonTool.datePlusDay(CommonTool.getCurrDate(),-30).substring(0,6);
				strSql=strSql+" union all ";
				strSql=strSql+" select a.compno,compname='离职',staffno,staffname,staffsex,department,departmentname=c.parentcodevalue,position,positiontitle,positionname=d.parentcodevalue,socialsource=e.parentcodevalue,fillno,arrivaldate,contractdate,pccid,aaddress,mobilephone,businessflag,resulttye,basesalary,socialsecurity,remark" +
				" from commoninfo c,commoninfo d ,staffinfo a " +
				" left join commoninfo e on  e.infotype='SBGS' and e.parentcodekey=a.socialsource " +
				" where a.compno='99999' and leavedate between '"+strMonth+"'+'01'  and  '"+strMonth+"'+'31'  and isnull(a.stafftype,0)=0 " +
				 "  and (isnull(department,'')='"+strdepartment+"' or '"+strdepartment+"'='')" +
				 "  and (isnull(position,'')='"+strpostion+"' or '"+strpostion+"'='')" +
				 "  and c.infotype='BMZL' and c.parentcodekey=a.department and d.infotype='GZGW' and d.parentcodekey=a.position " +
				 "   and isnull(socialsecurity,0)>0   ";
			}
			strSql=strSql+ " order by a.compno,department,staffno ";
			AnlyResultSet<List<Staffinfo>> analysis = new AnlyResultSet<List<Staffinfo>>() {
				public List<Staffinfo> anlyResultSet(ResultSet rs) {
					List<Staffinfo> returnValue = new ArrayList();
					Staffinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Staffinfo();
							record.setBcompno(CommonTool.FormatString(rs.getString("compno")));
							record.setBcompname(CommonTool.FormatString(rs.getString("compname")));
							record.setBstaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setStaffsex(CommonTool.FormatInteger(rs.getInt("staffsex")));
							record.setDepartment(CommonTool.FormatString(rs.getString("department")));
							record.setDepartmentText(CommonTool.FormatString(rs.getString("departmentname")));
							record.setPosition(CommonTool.FormatString(rs.getString("position")));
							record.setPositionText(CommonTool.FormatString(rs.getString("positionname")));
							record.setPositiontitle(CommonTool.FormatString(rs.getString("positiontitle")));
							record.setArrivaldate(CommonTool.getDateMask(rs.getString("arrivaldate")));
							record.setContractdate(CommonTool.getDateMask(rs.getString("contractdate")));
							record.setFillno(CommonTool.FormatString(rs.getString("fillno")));
							record.setPccid(CommonTool.FormatString(rs.getString("pccid")));
							record.setAaddress(CommonTool.FormatString(rs.getString("aaddress")));
							record.setMobilephone(CommonTool.FormatString(rs.getString("mobilephone")));
							record.setBusinessflag(CommonTool.FormatInteger(rs.getInt("businessflag")));
							record.setResulttye(CommonTool.FormatString(rs.getString("resulttye")));
							record.setSocialsource(CommonTool.FormatString(rs.getString("socialsource"))); 
							record.setBasesalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("basesalary")))));
							record.setSocialsecurity(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("socialsecurity")))));
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
