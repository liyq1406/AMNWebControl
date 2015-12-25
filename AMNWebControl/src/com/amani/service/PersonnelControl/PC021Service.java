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
public class PC021Service extends AMN_ReportService{
	
	public List<Staffinfo> loadDateSetByCompId(String strCompId,String strFromDate)
	{
		try
		{
			String strSql="";
				strSql="select count(sf.leavetype) leavetype ,a.compno,b.compname,staffno,staffname,positiontitlename=f.parentcodevalue ,staffsex,mobilephone,department,departmentname=c.parentcodevalue,position,positiontitle,positionname=d.parentcodevalue,arrivaldate,fillno,contractdate,pccid" +
						" from companyinfo b,compchaininfo ,commoninfo c,commoninfo d ,staffinfo a" +
						" left join staffleaveinfo sf on sf.staffinid = a.manageno"+
						" left join commoninfo f on (f.infotype ='GZZC' and positiontitle=f.parentcodekey)"+
						" where a.compno=relationcomp and curcomp='"+strCompId+"' and a.compno =b.compno and isnull(a.stafftype,0)=0 " +
						" and c.infotype='BMZL' and c.parentcodekey=a.department and d.infotype='GZGW' and d.parentcodekey=a.position " +
						" and sf.leavetype = 5"+
						" and sf.leavedate like '%"+strFromDate+"%'"+
						" and staffno not in (LTRIM(a.compno)+'300',LTRIM(a.compno)+'400',LTRIM(a.compno)+'500',LTRIM(a.compno)+'600')";
			strSql=strSql +"group by a.compno,b.compname,staffno,staffname,f.parentcodevalue,staffsex,mobilephone,department,c.parentcodevalue,position,positiontitle,d.parentcodevalue,arrivaldate,fillno,contractdate,pccid";	
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
							record.setMobilephone(CommonTool.FormatString(rs.getString("mobilephone")));
							record.setDepartment(CommonTool.FormatString(rs.getString("department")));
							record.setDepartmentText(CommonTool.FormatString(rs.getString("departmentname")));
							record.setPosition(CommonTool.FormatString(rs.getString("position")));
							record.setPositionText(CommonTool.FormatString(rs.getString("positionname")));
							record.setPositiontitle(CommonTool.FormatString(rs.getString("positiontitle")));
							record.setPositiontitleText(CommonTool.FormatString(rs.getString("positiontitlename")));
							record.setArrivaldate(CommonTool.getDateMask(rs.getString("arrivaldate")));
							record.setContractdate(CommonTool.getDateMask(rs.getString("contractdate")));
							record.setFillno(CommonTool.FormatString(rs.getString("fillno")));
							record.setPccid(CommonTool.FormatString(rs.getString("pccid")));
							record.setYears(CommonTool.FormatString(rs.getString("leavetype")));
							//record.setRemark(CommonTool.FormatString(rs.getString("remark")));
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
