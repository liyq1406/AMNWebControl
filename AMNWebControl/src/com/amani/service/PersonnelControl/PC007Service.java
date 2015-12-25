package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.StaffWorkDetial;
import com.amani.model.Staffinfo;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC007Service extends AMN_ReportService{
	
	public List<Staffinfo> loadStaffDateSetByCompId(String strCompId,String strDateFrom,String strDateTo)
	{
		try
		{
			String strSql=" exec upg_hand_staff_instore '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"' ";
			AnlyResultSet<List<Staffinfo>> analysis = new AnlyResultSet<List<Staffinfo>>() {
				public List<Staffinfo> anlyResultSet(ResultSet rs) {
					List<Staffinfo> returnValue = new ArrayList();
					Staffinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Staffinfo();
							record.setBstaffno(CommonTool.FormatString(rs.getString("empid")));
							record.setManageno(CommonTool.FormatString(rs.getString("inid")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
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
	
	public List<StaffWorkDetial> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,String strFromInid,String strToInid)
	{
		try
		{
			String strSql=" exec upg_personal_comm_paymode '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"','"+strFromInid+"','"+strToInid+"' ";
			AnlyResultSet<List<StaffWorkDetial>> analysis = new AnlyResultSet<List<StaffWorkDetial>>() {
				public List<StaffWorkDetial> anlyResultSet(ResultSet rs) {
					List<StaffWorkDetial> returnValue = new ArrayList();
					StaffWorkDetial record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new StaffWorkDetial();
							record.setPersoninid(CommonTool.FormatString(rs.getString("person_inid")));
							record.setActionid(CommonTool.FormatInteger(rs.getInt("action_id")));
							record.setSrvdate(CommonTool.FormatString(rs.getString("srvdate")));
							record.setCode(CommonTool.FormatString(rs.getString("code")));
							record.setName(CommonTool.FormatString(rs.getString("name")));
							record.setPayway(CommonTool.FormatString(rs.getString("payway")));
							record.setPaycode(CommonTool.FormatString(rs.getString("paycode")));
							record.setBillamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("billamt")))));
							record.setCcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ccount")))));
							record.setStaffticheng(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffticheng")))));
							record.setStaffyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffyeji")))));
							record.setStaffshareyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffshareyeji")))));
							record.setBillid(CommonTool.FormatString(rs.getString("billid")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<StaffWorkDetial> ls= (List<StaffWorkDetial>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
