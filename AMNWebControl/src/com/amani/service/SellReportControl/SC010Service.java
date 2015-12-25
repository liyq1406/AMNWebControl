package com.amani.service.SellReportControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.TradeDateCheckBean;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC010Service extends AMN_ReportService{
	
	public List<TradeDateCheckBean> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo)
	{
		try
		{
			String strSql="exec upg_compute_check_trade_bydate '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"' ";
			AnlyResultSet<List<TradeDateCheckBean>> analysis = new AnlyResultSet<List<TradeDateCheckBean>>() {
				public List<TradeDateCheckBean> anlyResultSet(ResultSet rs) {
					List<TradeDateCheckBean> returnValue = new ArrayList();
					TradeDateCheckBean record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new TradeDateCheckBean();
							record.setShopId(CommonTool.FormatString(rs.getString("shopId")));
							record.setShopName(CommonTool.FormatString(rs.getString("shopName")));
							record.setDateReport(CommonTool.getDateMask(rs.getString("dateReport")));
							record.setTotalCardtrans(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardyd_total")))));
							record.setTotalMrSale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beaut_yeji")))));
							record.setTotalMfSale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("hair_yeji")))));
							record.setTotalMjSale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("finger_yeji")))));
							record.setTotalOther(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("other_yeji")))));
							record.setTotalcardSales(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("total_yeji")))));
							record.setTotalcash(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cash_total")))));
							record.setTotalcredit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("bank_total")))));
							record.setTotalock(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ok_total")))));
							record.setTotalDztgk(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("dztgcost")))));
							record.setTotalMttgk(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("mttgcost")))));
							record.setTotalzft(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("zft_total")))));
							record.setTotalCardsCost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("card_total")))));
							record.setCashdyservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashdyjcost")))));
							record.setPrjdyservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("prjdyjcost")))));
							record.setTmkservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tmkcost")))));
							record.setTmkASale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tmkbuy_total")))));
							record.setTmkBSale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tmkzs_total")))));
							record.setTotalCostpoint(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("jifen_total")))));
							record.setTotalHzSale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("hzitem_total")))));
							record.setTotalBackCard(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tk_total")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<TradeDateCheckBean> ls= (List<TradeDateCheckBean>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
