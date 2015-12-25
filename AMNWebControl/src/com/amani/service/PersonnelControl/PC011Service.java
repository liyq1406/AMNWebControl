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
public class PC011Service extends AMN_ReportService{
	
	public List<Staffinfo> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo)
	{
		try
		{
			String strSql=" select a.compno,compname,a.staffno,a.staffname,a.department,a.position,a.manageno " +
					" from staffinfo a,companyinfo b, compchaininfo c, noperformanceemp d " +
					" where a.compno=b.compno and a.compno=c.relationcomp and c.curcomp='"+strCompId+"'" +
					" and isnull(a.businessflag,0)=1 and a.manageno=d.empinnerno and d.ddate ='"+strDateFrom+"' ";
			
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
							record.setDepartment(CommonTool.FormatString(rs.getString("department")));
							record.setPosition(CommonTool.FormatString(rs.getString("position")));
							record.setManageno(CommonTool.FormatString(rs.getString("manageno")));
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
