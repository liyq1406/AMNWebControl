package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.StaffWarkYejiAnlanysis;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC009Service extends AMN_ReportService{
	
	public List<StaffWarkYejiAnlanysis> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo)
	{
		try
		{
			String strSql="  exec upg_all_personal_comm_paymode '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"',3  ";
			
			AnlyResultSet<List<StaffWarkYejiAnlanysis>> analysis = new AnlyResultSet<List<StaffWarkYejiAnlanysis>>() {
				public List<StaffWarkYejiAnlanysis> anlyResultSet(ResultSet rs) {
					List<StaffWarkYejiAnlanysis> returnValue = new ArrayList();
					StaffWarkYejiAnlanysis record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new StaffWarkYejiAnlanysis();
							record.setStaffinid(CommonTool.FormatString(rs.getString("person_inid")));
							record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setStaffposition(CommonTool.FormatString(rs.getString("staffposition")));
							record.setOldcostcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("oldcostcount")))));
							record.setNewcostcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("newcostcount")))));
							record.setTrcostcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("trcostcount")))));
							record.setCashbigcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashbigcost")))));
							record.setCashsmallcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashsmallcost")))));
							record.setCashhulicost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashhulicost")))));
							record.setCardbigcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardbigcost")))));
							record.setCardsmallcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardsmallcost")))));
							record.setCardhulicost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardhulicost")))));
							record.setCardprocost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardprocost")))));
							record.setCardsgcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardsgcost")))));
							record.setCardpointcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardpointcost")))));
							record.setProjectdycost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("projectdycost")))));
							record.setCashdycost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashdycost")))));
							record.setTmcardcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tmcardcost")))));
							record.setSalegoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("salegoodsamt")))));
							record.setSalecardsamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("salecardsamt")))));
							record.setProchangeamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("prochangeamt")))));
							record.setSaletmkamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("saletmkamt")))));
							record.setQhpayinner(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("qhpayinner")))));
							record.setQhpayouter(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("qhpayouter")))));
							record.setJdpayinner(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("jdpayinner")))));
							record.setSmpayinner(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("smpayinner")))));
							record.setStaffyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffyeji")))));
							record.setTotalcashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffcashyeji")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<StaffWarkYejiAnlanysis> ls= (List<StaffWarkYejiAnlanysis>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
