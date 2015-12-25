package com.amani.service.SellReportControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.TradeBillCount;
import com.amani.bean.Tradedailydata;
import com.amani.bean.Tradedatedata;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC002Service extends AMN_ReportService{
	
	public List<Tradedailydata> loadTradedailydatas(String strCompId,String strDateFrom)
	{
		try
		{
			String strSql=" exec upg_create_trade_dailydata '"+strCompId+"','"+CommonTool.setDateMask(strDateFrom)+"','"+CommonTool.setDateMask(strDateFrom)+"',1";
			
			AnlyResultSet<List<Tradedailydata>> analysis = new AnlyResultSet<List<Tradedailydata>>() {
				public List<Tradedailydata> anlyResultSet(ResultSet rs) {
					List<Tradedailydata> returnValue = new ArrayList();
					Tradedailydata record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Tradedailydata();
							record.setTradeTitle(CommonTool.FormatString(rs.getString("tradeTitle")));
							record.setTradeAmt(CommonTool.FormatString(rs.getString("tradeAmt")));
							record.setValueflag(CommonTool.FormatInteger(rs.getInt("valueflag")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Tradedailydata> ls= (List<Tradedailydata>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Tradedatedata> loadTradedatedatas(String strCompId,String strDateFrom_Start,String strDateFrom)
	{
		try
		{
			String strSql=" exec upg_create_trade_dailydata '"+strCompId+"','"+CommonTool.setDateMask(strDateFrom_Start)+"','"+CommonTool.setDateMask(strDateFrom)+"',2";
			
			AnlyResultSet<List<Tradedatedata>> analysis = new AnlyResultSet<List<Tradedatedata>>() {
				public List<Tradedatedata> anlyResultSet(ResultSet rs) {
					List<Tradedatedata> returnValue = new ArrayList();
					Tradedatedata record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Tradedatedata();
							record.setTradedate(CommonTool.getDateMask(rs.getString("tradedate")));
							record.setTotalcashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalcashamt")))));
							record.setStaffcashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffcashamt")))));
							record.setHezuocashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("hezuocashamt")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Tradedatedata> ls= (List<Tradedatedata>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<TradeBillCount> loadTradeBillCount(String strCompId,String strDateFrom_Start,String strDateFrom)
	{
		try
		{
			String strSql=" exec upg_create_trade_dailybillcount '"+strCompId+"','"+CommonTool.setDateMask(strDateFrom_Start)+"','"+CommonTool.setDateMask(strDateFrom)+"' ";
			
			AnlyResultSet<List<TradeBillCount>> analysis = new AnlyResultSet<List<TradeBillCount>>() {
				public List<TradeBillCount> anlyResultSet(ResultSet rs) {
					List<TradeBillCount> returnValue = new ArrayList();
					TradeBillCount record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new TradeBillCount();
							record.setStrCompId(CommonTool.FormatString(rs.getString("compid")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("compidname")));
							record.setCashBillAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cash_bill_amt")))));
							record.setCashBillPrice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cash_bill_price")))));
							record.setCardBillAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("card_bill_amt")))));
							record.setCardBillPrice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("card_bill_price")))));
							record.setTotalBillAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("bill_amt")))));
							record.setTotalBillPrice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("bill_price")))));
							record.setCashBillCount(CommonTool.FormatInteger(rs.getInt("cash_bill_num")));
							record.setCardBillCount(CommonTool.FormatInteger(rs.getInt("card_bill_num")));
							record.setTotalBillCount(CommonTool.FormatInteger(rs.getInt("bill_num")));
							record.setMrPrjBillCount(CommonTool.FormatInteger(rs.getInt("mrbigprj_num")));
							record.setMfPrjBillCount(CommonTool.FormatInteger(rs.getInt("mfbigprj_num")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<TradeBillCount> ls= (List<TradeBillCount>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//第三方支付日记账金额汇总
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> loadThreePayment(String strCompId,String strDateFrom)
	{
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append(" WITH temp AS (SELECT b.scanpaytype, SUM(b.scanpayamt) payamt FROM mconsumeinfo a, dconsumeinfo b ");
			sql.append(" WHERE a.cscompid=b.cscompid AND a.csbillid=b.csbillid AND b.cspaymode='15'AND ");
			sql.append(" a.cscompid='"+strCompId+"'AND a.financedate='"+strDateFrom+"'GROUP BY b.scanpaytype HAVING b.scanpaytype IS ");
			sql.append(" NOT NULL UNION ALL SELECT c.scanpaytype, SUM(c.scanpayamt) payamt FROM mcardrechargeinfo c,");
			sql.append(" dpayinfo d WHERE c.rechargecompid = '"+strCompId+"'AND c.financedate ='"+strDateFrom+"'AND d.paybillid = ");
			sql.append(" c.rechargebillid AND d.paybilltype = 'CZ'AND paymode = '15'GROUP BY c.scanpaytype HAVING c.scanpaytype ");
			sql.append(" IS NOT NULL ) SELECT b.parentcodekey, b.parentcodevalue, ISNULL(a.payamt, 0) payamt FROM ");
			sql.append(" temp a RIGHT JOIN commoninfo b ON a.scanpaytype=b.parentcodekey WHERE b.infotype='DSFZF'");
			/*sql.append("with temp as (")
			.append("select b.scanpaytype, sum(b.scanpayamt) payamt from mconsumeinfo a, dconsumeinfo b ") 
			.append("where a.cscompid=b.cscompid and a.csbillid=b.csbillid and b.cspaymode='15' ")
			.append("and a.cscompid='"+ strCompId +"' and a.financedate='"+ strDateFrom +"' ")
			.append("group by b.scanpaytype having b.scanpaytype is not null)")
			.append("select b.parentcodekey, b.parentcodevalue, isnull(a.payamt, 0) payamt from temp a right join commoninfo b ") 
			.append("on a.scanpaytype=b.parentcodekey where b.infotype='DSFZF'");*/
			AnlyResultSet<List<Map<String, Object>>> analysis = new AnlyResultSet<List<Map<String, Object>>>() {
				public List<Map<String, Object>> anlyResultSet(ResultSet rs) {
					DecimalFormat df = new DecimalFormat("#0.0");
					List<Map<String, Object>> returnValue = new ArrayList<Map<String, Object>>();
					Map<String, Object> record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new HashMap<String, Object>();
							record.put("paymethod", CommonTool.FormatString(rs.getString("parentcodevalue")));
							record.put("payamt", df.format(CommonTool.FormatDouble(rs.getDouble("payamt"))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Map<String, Object>> ls= (List<Map<String, Object>>) this.amn_Dao.executeQuery_ex(sql.toString(), analysis);
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
