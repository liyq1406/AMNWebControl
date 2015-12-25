package com.amani.service.SellReportControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.ProjectCostAnalysis;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC007Service extends AMN_ReportService{
	
	public List<ProjectCostAnalysis> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,int iBeforCount,double dPrjFromCostAmt,double dPrjToCostAmt,double dGoodsFromCostAmt,double dGoodsToCostAmt)
	{
		try
		{
			String strSql=" exec upg_prj_goods_consume_analysize '"+strCompId+"',"+iBeforCount+",'"+strDateFrom+"','"+strDateTo+"',"+dPrjFromCostAmt+","+dPrjToCostAmt+","+dGoodsFromCostAmt+","+dGoodsToCostAmt+" ";
			
			AnlyResultSet<List<ProjectCostAnalysis>> analysis = new AnlyResultSet<List<ProjectCostAnalysis>>() {
				public List<ProjectCostAnalysis> anlyResultSet(ResultSet rs) {
					List<ProjectCostAnalysis> returnValue = new ArrayList();
					ProjectCostAnalysis record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new ProjectCostAnalysis();
							record.setShopId(CommonTool.FormatString(rs.getString("compid")));
							record.setShopName(CommonTool.FormatString(rs.getString("compname")));
							record.setProjectno(CommonTool.FormatString(rs.getString("prjno")));
							record.setProjectname(CommonTool.FormatString(rs.getString("prjname")));
							record.setProjecttype(CommonTool.FormatString(rs.getString("prjtype")));
							record.setProjectcostcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("prjcnt")))));
							record.setProjectcostamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("prjamt")))));
							
							record.setGoodsno(CommonTool.FormatString(rs.getString("goodsno")));
							record.setGoodsname(CommonTool.FormatString(rs.getString("goodsname")));
							record.setGoodstype(CommonTool.FormatString(rs.getString("goodstype")));
							record.setGoodssalecount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("goodscnt")))));
							record.setGoodssaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("goodsamt")))));
							
							
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<ProjectCostAnalysis> ls= (List<ProjectCostAnalysis>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
