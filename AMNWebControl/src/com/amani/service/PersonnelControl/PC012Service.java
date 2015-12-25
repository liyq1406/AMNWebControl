package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.PC012Bean;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC012Service extends AMN_ReportService{
	
	public List<PC012Bean> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,int searchType)
	{
		try
		{
			String strSql=" exec upg_compute_costanlysis_by_date_comps '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"',"+searchType+" ";
			AnlyResultSet<List<PC012Bean>> analysis = new AnlyResultSet<List<PC012Bean>>() {
				public List<PC012Bean> anlyResultSet(ResultSet rs) {
					List<PC012Bean> returnValue = new ArrayList();
					PC012Bean bean=null;
					try {
						while (rs != null && rs.next() == true) {
							bean=new PC012Bean();
					        bean.setStrEmpNo(rs.getString("empno"));
					        bean.setStrEmpName(rs.getString("empname"));
					        bean.setStrInit(rs.getString("inid"));
					        bean.setXfcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("xfcount")))));
					        bean.setOldcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("oldcount")))));
					        bean.setOlditem(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("olditem")))));
					        bean.setOldxf(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("oldxf")))));
					        bean.setItemcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("itemcount")))));
					        bean.setMrcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("mrcount")))));
					        bean.setHlcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("hlcount")))));
					        bean.setTfcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tfcount")))));
					        bean.setRfcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("rfcount")))));
					        bean.setRecount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("recount")))));
					        bean.setGmitem(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("gmitem")))));
							returnValue.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<PC012Bean> ls= (List<PC012Bean>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
