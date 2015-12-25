package com.amani.service.SellReportControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CompTradeHTAnalysis;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC006Service extends AMN_ReportService{
	
	public List<CompTradeHTAnalysis> loadDateSetByCompId(String strCompId,String strDateFrom)
	{
		try
		{
			String strSql=" exec upg_yejiAnalysis_byMonth_HT '"+strCompId+"','"+strDateFrom+"'";
			
			AnlyResultSet<List<CompTradeHTAnalysis>> analysis = new AnlyResultSet<List<CompTradeHTAnalysis>>() {
				public List<CompTradeHTAnalysis> anlyResultSet(ResultSet rs) {
					List<CompTradeHTAnalysis> returnValue = new ArrayList();
					CompTradeHTAnalysis record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new CompTradeHTAnalysis();
							record.setStrCompId(CommonTool.FormatString(rs.getString("compid")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("compname")));
							record.setStrMonth(rs.getString("mmonth"));
							record.setTotalAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalAmt")))));
							record.setTotalAmtA(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalAmtA")))));
							record.setTotalAmtB(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalAmtB")))));
							record.setTotalAmtRateAText(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalAmtRateA")*100))).toString()+"%");
							record.setTotalAmtRateBText(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalAmtRateB")*100))).toString()+"%");
							
							
							record.setTotalFactAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalFactAmt")))));
							record.setTotalFactAmtA(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalFactAmtA")))));
							record.setTotalFactAmtB(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalFactAmtB")))));
							record.setTotalFactAmtRateAText(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalFactAmtRateA")*100))).toString()+"%");
							record.setTotalFactAmtRateBText(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalFactAmtRateB")*100))).toString()+"%");
							
							
							record.setSaleCardAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("saleCardAmt")))));
							record.setSaleCardAmtA(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("saleCardAmtA")))));
							record.setSaleCardAmtB(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("saleCardAmtB")))));
							record.setSaleCardAmtRateAText(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("saleCardAmtRateA")*100))).toString()+"%");
							record.setSaleCardAmtRateBText(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("saleCardAmtRateB")*100))).toString()+"%");
							
							record.setPinCardAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("pinCardAmt")))));
							record.setPinCardAmtA(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("pinCardAmtA")))));
							record.setPinCardAmtB(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("pinCardAmtB")))));
							record.setPinCardAmtRateAText(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("pinCardAmtRateA")*100))).toString()+"%");
							record.setPinCardAmtRateBText(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("pinCardAmtRateB")*100))).toString()+"%");
							
							
							record.setBuyGoodsAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("buyGoodsAmt")))));
							record.setBuyGoodsAmtA(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("buyGoodsAmtA")))));
							record.setBuyGoodsAmtB(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("buyGoodsAmtB")))));
							record.setBuyGoodsAmtRateAText(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("buyGoodsAmtRateA")*100))).toString()+"%");
							record.setBuyGoodsAmtRateBText(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("buyGoodsAmtRateB")*100))).toString()+"%");
							
							
							
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<CompTradeHTAnalysis> ls= (List<CompTradeHTAnalysis>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
