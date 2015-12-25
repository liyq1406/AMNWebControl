package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.PC013Bean;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC013Service extends AMN_ReportService{
	
	public List<PC013Bean> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,int targetcount)
	{
		try
		{
			String strSql=" exec upg_compute_costoldpersanlysis_by_date_comps '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"',"+targetcount+" ";
			AnlyResultSet<List<PC013Bean>> analysis = new AnlyResultSet<List<PC013Bean>>() {
				public List<PC013Bean> anlyResultSet(ResultSet rs) {
					List<PC013Bean> returnValue = new ArrayList();
					PC013Bean bean=null;
					try {
						while (rs != null && rs.next() == true) {
							bean=new PC013Bean();
							 bean.setStrCompid(rs.getString("compid"));
						     bean.setStrCompName(rs.getString("compname"));
						     bean.setStrEmpNo(rs.getString("empno"));
						     bean.setStrPostion(CommonTool.FormatString(rs.getString("position")));
						     bean.setStrPostionText(CommonTool.FormatString(rs.getString("positiontext")));
						     bean.setStrEmpName(rs.getString("staffname"));
						     bean.setCcount(rs.getDouble("ccount"));
						     bean.setStrEntrydata(CommonTool.getDateMask(rs.getString("strEntrydata")));
						     bean.setDifCount(rs.getDouble("difcount"));
						     returnValue.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<PC013Bean> ls= (List<PC013Bean>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
