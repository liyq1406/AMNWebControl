package com.amani.service.SellReportControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CompMoreTradeAnlysis;


import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC008Service extends AMN_ReportService{
	
	public List<CompMoreTradeAnlysis> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,int ordertype)
	{
		try
		{
			String strSql = "exec upg_compute_comp_avg_analysis_bytype '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"',"+ordertype+" ";
			AnlyResultSet<List<CompMoreTradeAnlysis>> analysis = new AnlyResultSet<List<CompMoreTradeAnlysis>>() {
				public List<CompMoreTradeAnlysis> anlyResultSet(ResultSet rs) {
					List<CompMoreTradeAnlysis> returnValue = new ArrayList();
					CompMoreTradeAnlysis bean=null;
					try {
						while (rs != null && rs.next() == true) {
							 bean=new CompMoreTradeAnlysis();
							 bean.setStrCompId(rs.getString("compid"));
							 bean.setStrCompName(rs.getString("compname"));
							 bean.setStrDate(rs.getString("seardate"));
							 bean.setTotalxuyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalxuyeji")))));
							 bean.setTotalshiyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalshiyeji")))));
							 bean.setCostcardrate(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costcardrate")))));
							 bean.setMemcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("memcount")))));
							 bean.setGoodmemcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("goodmemcount")))));
							 bean.setAddmemcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("addmemcount")))));
							 bean.setAddbeatypromems(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("addbeatypromems")))));
							 bean.setAddhairpromems(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("addhairpromems")))));
							 bean.setAddpromems(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("addpromems")))));
							 bean.setBeatycount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beatycount")))));
							 bean.setBeatygoodcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beatygoodcount")))));
							 bean.setBeatygoodrate(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beatygoodrate")))));
							 bean.setHaircount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("haircount")))));
							 bean.setHairgoodcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("hairgoodcount")))));
							 bean.setHairgoodrate(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("hairgoodrate")))));
							 bean.setLeavelcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("leavelcount")))));
							 bean.setLeavelcorecount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("leavelcorecount")))));
							 bean.setTotalxuyejizb(CommonTool.FormatString(rs.getString("totalxuyejizb")));
							 bean.setCostcardratezb(CommonTool.FormatString(rs.getString("costcardratezb")));
							 bean.setGoodmemcountzb(CommonTool.FormatString(rs.getString("goodmemcountzb")));
							 bean.setAddpromemszb(CommonTool.FormatString(rs.getString("addpromemszb")));
							 bean.setBeatygoodratezb(CommonTool.FormatString(rs.getString("beatygoodratezb")));
							 bean.setHairgoodratezb(CommonTool.FormatString(rs.getString("hairgoodratezb")));
							 bean.setLeavelcountzb(CommonTool.FormatString(rs.getString("leavelcountzb")));
							 
							 bean.setTotalxuyejizbF(CommonTool.FormatDouble(rs.getDouble("totalxuyeji")).toString()+"  ["+CommonTool.FormatString(rs.getString("totalxuyejizb"))+"]");
							 bean.setCostcardratezbF(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("costcardrate"))*100,2).toString()+"%  ["+CommonTool.FormatString(rs.getString("costcardratezb"))+"]");
							 bean.setGoodmemcountzbF(CommonTool.FormatDouble(rs.getDouble("goodmemcount")).toString()+"  ["+CommonTool.FormatString(rs.getString("goodmemcountzb"))+"]");
							 bean.setAddpromemszbF(CommonTool.FormatDouble(rs.getDouble("addpromems")).toString()+"  ["+CommonTool.FormatString(rs.getString("addpromemszb"))+"]");
							 bean.setBeatygoodratezbF(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("beatygoodrate"))*100,2).toString()+"%  ["+CommonTool.FormatString(rs.getString("beatygoodratezb"))+"]");
							 bean.setHairgoodratezbF(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("hairgoodrate"))*100,2).toString()+"%  ["+CommonTool.FormatString(rs.getString("hairgoodratezb"))+"]");
							 bean.setLeavelcountzbF(CommonTool.FormatDouble(rs.getDouble("leavelcorecount")).toString()+"  ["+CommonTool.FormatString(rs.getString("leavelcountzb"))+"]");
							 
							 returnValue.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<CompMoreTradeAnlysis> ls= (List<CompMoreTradeAnlysis>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
