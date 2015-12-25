package com.amani.service.PersonnelControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.PC024Bean;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC024Service extends AMN_ReportService{
	
	public List<PC024Bean> loadDateSetByCompId(String strCompId)
	{
		try
		{	//只查询门店员工积分
			String sql="select compno, compname, staffno, staffname, positionname, credit from credit where compno in("+
					"select relationcomp from compchaininfo where curcomp='"+ strCompId +"' and "+
					"relationcomp in(select curcompno from compchainstruct where complevel=4)) order by staffno";
			AnlyResultSet<List<PC024Bean>> analysis = new AnlyResultSet<List<PC024Bean>>() {
				public List<PC024Bean> anlyResultSet(ResultSet rs) {
					List<PC024Bean> returnValue = new ArrayList<PC024Bean>();
					PC024Bean record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new PC024Bean();
							record.setCompno(CommonTool.FormatString(rs.getString("compno")));
							record.setCompname(CommonTool.FormatString(rs.getString("compname")));
							record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setPosition(CommonTool.FormatString(rs.getString("positionname")));
							record.setCredit(CommonTool.FormatInteger(rs.getInt("credit")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			@SuppressWarnings("unchecked")
			List<PC024Bean> ls= (List<PC024Bean>) this.amn_Dao.executeQuery_ex(sql, analysis);
			analysis=null;
			return ls;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<PC024Bean> loadDetail(String staffno)
	{
		try
		{	//只查询员工积分详情
			String sql="select staffno, classify, number, unit, credit from creditdetail where staffno='"+staffno+"'";
			AnlyResultSet<List<PC024Bean>> analysis = new AnlyResultSet<List<PC024Bean>>() {
				public List<PC024Bean> anlyResultSet(ResultSet rs) {
					List<PC024Bean> returnValue = new ArrayList<PC024Bean>();
					PC024Bean record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new PC024Bean();
							record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setClassify(CommonTool.FormatString(rs.getString("classify")));
							record.setNumber(CommonTool.FormatInteger(rs.getInt("number")));
							record.setUnit(CommonTool.FormatString(rs.getString("unit")));
							record.setCredit(CommonTool.FormatInteger(rs.getInt("credit")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			@SuppressWarnings("unchecked")
			List<PC024Bean> ls= (List<PC024Bean>) this.amn_Dao.executeQuery_ex(sql, analysis);
			analysis=null;
			return ls;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
