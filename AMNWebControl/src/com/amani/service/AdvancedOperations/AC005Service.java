package com.amani.service.AdvancedOperations;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CompAmtAnlysisBean;
import com.amani.bean.CompLogAnlysisBean;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class AC005Service extends AMN_ReportService{
	
	public CompAmtAnlysisBean loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo)
	{
			String strSql=" exec upg_diarialBill_byday_analysis '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"' ";
			AnlyResultSet<CompAmtAnlysisBean> analysis = new AnlyResultSet<CompAmtAnlysisBean>() {
				public CompAmtAnlysisBean anlyResultSet(ResultSet rs) {
					CompAmtAnlysisBean record=new CompAmtAnlysisBean();
					try {
						while (rs != null && rs.next() == true) {
							
							// SY_P 收银项目  SY_G1 SY_G2 产品销售  SK  售卡  CZ 充值 ZK  转卡 HZ 合作项目 TK 退卡 TMK 条码卡  LXDH 疗程兑换
							String item = CommonTool.FormatString(rs.getString("paybilltype"));
							String paymode = CommonTool.FormatString(rs.getString("paymode"));
							double payamt =CommonTool.FormatDouble(rs.getDouble("payamt"));
							if(item.equals("SY_P")&&paymode.equalsIgnoreCase("1")) record.setPrj_cash(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("6")) record.setPrj_bank(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("2")) record.setPrj_check(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("4")) record.setPrj_card(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("A")) record.setPrj_sgcard(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("8")) record.setPrj_jlqd(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("7")) record.setPrj_point(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("5")) record.setPrj_qdgz(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("11")) record.setPrj_prjdy(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("12")) record.setPrj_cashdy(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("13")) record.setPrj_carddy(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("14")) record.setPrj_finger(payamt);
							else if(item.equals("SY_P")&&paymode.equalsIgnoreCase("15")) record.setPrj_okcard(payamt);
							
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("1")) record.setGoods_cash(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("6")) record.setGoods_bank(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("2")) record.setGoods_check(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("4")) record.setGoods_card(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("A")) record.setGoods_sgcard(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("8")) record.setGoods_jlqd(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("7")) record.setGoods_point(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("5")) record.setGoods_qdgz(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("11")) record.setGoods_prjdy(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("12")) record.setGoods_cashdy(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("13")) record.setGoods_carddy(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("14")) record.setGoods_finger(payamt);
							else if((item.equals("SY_G1") || item.equals("SY_G2"))&&paymode.equalsIgnoreCase("15")) record.setGoods_okcard(payamt);
							
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("1")) record.setKk_cash(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("6")) record.setKk_bank(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("2")) record.setKk_check(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("4")) record.setKk_card(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("A")) record.setKk_sgcard(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("8")) record.setKk_jlqd(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("7")) record.setKk_point(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("5")) record.setKk_qdgz(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("11")) record.setKk_prjdy(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("12")) record.setKk_cashdy(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("13")) record.setKk_carddy(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("14")) record.setKk_finger(payamt);
							else if(item.equals("SK")&&paymode.equalsIgnoreCase("15")) record.setKk_okcard(payamt);
							
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("1")) record.setKcz_cash(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("6")) record.setKcz_bank(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("2")) record.setKcz_check(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("4")) record.setKcz_card(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("A")) record.setKcz_sgcard(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("8")) record.setKcz_jlqd(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("7")) record.setKcz_point(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("5")) record.setKcz_qdgz(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("11")) record.setKcz_prjdy(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("12")) record.setKcz_cashdy(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("13")) record.setKcz_carddy(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("14")) record.setKcz_finger(payamt);
							else if(item.equals("CZ")&&paymode.equalsIgnoreCase("15")) record.setKcz_okcard(payamt);
							
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("1")) record.setZkzk_cash(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("6")) record.setZkzk_bank(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("2")) record.setZkzk_check(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("4")) record.setZkzk_card(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("A")) record.setZkzk_sgcard(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("8")) record.setZkzk_jlqd(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("7")) record.setZkzk_point(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("5")) record.setZkzk_qdgz(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("11")) record.setZkzk_prjdy(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("12")) record.setZkzk_cashdy(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("13")) record.setZkzk_carddy(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("14")) record.setZkzk_finger(payamt);
							else if(item.equals("ZK")&&paymode.equalsIgnoreCase("15")) record.setZkzk_okcard(payamt);
							
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("1")) record.setTk_cash(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("6")) record.setTk_bank(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("2")) record.setTk_check(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("4")) record.setTk_card(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("A")) record.setTk_sgcard(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("8")) record.setTk_jlqd(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("7")) record.setTk_point(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("5")) record.setTk_qdgz(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("11")) record.setTk_prjdy(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("12")) record.setTk_cashdy(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("13")) record.setTk_carddy(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("14")) record.setTk_finger(payamt);
							else if(item.equals("TK")&&paymode.equalsIgnoreCase("15")) record.setTk_okcard(payamt);
							
							else if(item.equals("LXDH")&&paymode.equalsIgnoreCase("1")) record.setLcdh_cash(payamt);
							else if(item.equals("LXDH")&&paymode.equalsIgnoreCase("6")) record.setLcdh_bank(payamt);
							else if(item.equals("TMK")&&paymode.equalsIgnoreCase("1")) record.setTmk_cash(payamt);
							else if(item.equals("TMK")&&paymode.equalsIgnoreCase("6")) record.setTmk_bank(payamt);
							else if(item.equals("TMK")&&paymode.equalsIgnoreCase("2")) record.setTmk_check(payamt);
							else if(item.equals("TMK")&&paymode.equalsIgnoreCase("11")) record.setTmk_prjdy(payamt);
							else if(item.equals("TMK")&&paymode.equalsIgnoreCase("12")) record.setTmk_cashdy(payamt);
							else if(item.equals("TMK")&&paymode.equalsIgnoreCase("13")) record.setTmk_carddy(payamt);
							else if(item.equals("TMK")&&paymode.equalsIgnoreCase("14")) record.setTmk_finger(payamt);
							else if(item.equals("TMK")&&paymode.equalsIgnoreCase("15")) record.setTmk_okcard(payamt);

						}	
						//项目合计
						record.setPrj_total(record.getPrj_cash()+record.getPrj_bank()+record.getPrj_check()+record.getPrj_card()+record.getPrj_sgcard()
											+record.getPrj_jlqd()+record.getPrj_point()+record.getPrj_qdgz()+record.getPrj_prjdy()
											+record.getPrj_cashdy()+record.getPrj_carddy()+record.getPrj_finger()+record.getPrj_okcard());
						//产品合计
						record.setGoods_total(record.getGoods_cash()+record.getGoods_bank()+record.getGoods_check()+record.getGoods_card()
										   +record.getGoods_sgcard()+record.getGoods_jlqd()+record.getGoods_point()+record.getGoods_qdgz()
										   +record.getGoods_prjdy()+record.getGoods_cashdy()+record.getGoods_carddy()+record.getGoods_finger()+record.getGoods_okcard());
						//小计
						record.setPrj_goods_cash(record.getPrj_cash()+record.getGoods_cash());
						record.setPrj_goods_bank(record.getPrj_bank()+record.getGoods_bank());
						record.setPrj_goods_check(record.getPrj_check()+record.getGoods_check());
						record.setPrj_goods_card(record.getPrj_card()+record.getGoods_card());
						record.setPrj_goods_sgcard(record.getPrj_sgcard()+record.getGoods_sgcard());
						record.setPrj_goods_jlqd(record.getPrj_jlqd()+record.getGoods_jlqd());
						record.setPrj_goods_point(record.getPrj_point()+record.getGoods_point());
						record.setPrj_goods_qdgz(record.getPrj_qdgz()+record.getGoods_qdgz());
						
						record.setPrj_goods_prjdy(record.getPrj_prjdy()+record.getGoods_prjdy());
						record.setPrj_goods_cashdy(record.getPrj_cashdy()+record.getGoods_cashdy());
						record.setPrj_goods_carddy(record.getPrj_carddy()+record.getGoods_carddy());
						record.setPrj_goods_finger(record.getPrj_finger()+record.getGoods_finger());
						record.setPrj_goods_okcard(record.getPrj_okcard()+record.getGoods_okcard());
						
						record.setPrj_goods_total(record.getPrj_total()+record.getGoods_total());
						
						//开卡合计
						record.setKk_total(record.getKk_cash()+record.getKk_bank()+record.getKk_check()+record.getKk_card()
								   +record.getKk_sgcard()+record.getKk_jlqd()+record.getKk_point()+record.getKk_qdgz()
								   +record.getKk_prjdy()+record.getKk_cashdy()+record.getKk_carddy()+record.getKk_finger()+record.getKk_okcard());
						record.setKcz_total(record.getKcz_cash()+record.getKcz_bank()+record.getKcz_check()+record.getKcz_card()
								   +record.getKcz_sgcard()+record.getKcz_jlqd()+record.getKcz_point()+record.getKcz_qdgz()
								   +record.getKcz_prjdy()+record.getKcz_cashdy()+record.getKcz_carddy()+record.getKcz_finger()+record.getKcz_okcard());
						record.setZkzk_total(record.getZkzk_cash()+record.getZkzk_bank()+record.getZkzk_check()+record.getZkzk_card()
								   +record.getZkzk_sgcard()+record.getZkzk_jlqd()+record.getZkzk_point()+record.getZkzk_qdgz()
								   +record.getZkzk_prjdy()+record.getZkzk_cashdy()+record.getZkzk_carddy()+record.getZkzk_finger()+record.getZkzk_okcard());

						record.setCard_change_cash(record.getKk_cash()+record.getKcz_cash()+record.getZkzk_cash());
						record.setCard_change_bank(record.getKk_bank()+record.getKcz_bank()+record.getZkzk_bank());
						record.setCard_change_check(record.getKk_check()+record.getKcz_check()+record.getZkzk_check());
						record.setCard_change_card(record.getKk_card()+record.getKcz_card()+record.getZkzk_card());
						record.setCard_change_sgcard(record.getKk_sgcard()+record.getKcz_sgcard()+record.getZkzk_sgcard());
						record.setCard_change_jlqd(record.getKk_jlqd()+record.getKcz_jlqd()+record.getZkzk_jlqd());
						record.setCard_change_point(record.getKk_point()+record.getKcz_point()+record.getZkzk_point());
						record.setCard_change_qdgz(record.getKk_qdgz()+record.getKcz_qdgz()+record.getZkzk_qdgz());
						
						
						record.setCard_change_prjdy(record.getKk_prjdy()+record.getKcz_prjdy()+record.getZkzk_prjdy());
						record.setCard_change_cashdy(record.getKk_cashdy()+record.getKcz_cashdy()+record.getZkzk_cashdy());
						record.setCard_change_carddy(record.getKk_carddy()+record.getKcz_carddy()+record.getZkzk_carddy());
						record.setCard_change_finger(record.getKk_finger()+record.getKcz_finger()+record.getZkzk_finger());
						record.setCard_change_okcard(record.getKk_okcard()+record.getKcz_okcard()+record.getZkzk_okcard());
						record.setCard_change_total(record.getKk_total()+record.getKcz_total()+record.getZkzk_total());
						
						
						record.setTk_total(record.getTk_cash()+record.getTk_bank()+record.getTk_check()+record.getTk_card()
								   +record.getTk_sgcard()+record.getTk_jlqd()+record.getTk_point()+record.getTk_qdgz()
								   +record.getTk_prjdy()+record.getTk_cashdy()+record.getTk_carddy()+record.getTk_finger()+record.getTk_okcard());
						
							
						record.setLcdh_total(record.getLcdh_cash()+record.getLcdh_bank());
						
						record.setTmk_total(record.getTmk_cash()+record.getTmk_bank()+record.getTmk_check()
								   +record.getTmk_prjdy()+record.getTmk_cashdy()+record.getTmk_carddy()+record.getTmk_finger()+record.getTmk_okcard());
						
						
						
						record.setTotalIncome(record.getPrj_goods_bank()+record.getPrj_goods_cash()+record.getPrj_goods_check()
										  +record.getPrj_goods_finger()+record.getPrj_goods_okcard()
										  +record.getCard_change_bank()+record.getCard_change_cash()+record.getCard_change_check()
										  +record.getCard_change_finger()+record.getCard_change_okcard()
										  +record.getTk_bank()+record.getTk_cash()+record.getTk_check()
										  +record.getTk_finger()+record.getTk_okcard());
										
						record.setTotalDebt(record.getPrj_goods_qdgz()+record.getCard_change_qdgz());
						record.setRealPerformance(record.getPrj_goods_cash()+record.getPrj_goods_bank()+record.getPrj_goods_check()
										  +record.getPrj_goods_card()+record.getPrj_goods_sgcard()+record.getPrj_goods_jlqd()
										  +record.getPrj_goods_point()
										  +record.getPrj_goods_prjdy()
										  +record.getPrj_goods_prjdy()
										  +record.getPrj_goods_carddy()
										  +record.getPrj_goods_finger()
										  +record.getPrj_goods_okcard());
						record.setVirtualPerformance(record.getPrj_goods_bank()+record.getPrj_goods_cash()
										  +record.getPrj_goods_check()+record.getCard_change_bank()+record.getCard_change_cash()+record.getCard_change_check()
										  +record.getTk_bank()+record.getTk_cash()+record.getTk_check()
										  +record.getPrj_goods_prjdy()
										  +record.getPrj_goods_prjdy()
										  +record.getPrj_goods_carddy()
										  +record.getPrj_goods_finger()
										  +record.getPrj_goods_okcard()						
										  +record.getCard_change_prjdy()
										  +record.getCard_change_prjdy()
										  +record.getCard_change_carddy()
										  +record.getCard_change_finger()
										  +record.getCard_change_okcard()						
										  +record.getTk_prjdy()
										  +record.getTk_prjdy()
										  +record.getTk_carddy()
										  +record.getTk_finger()
										  +record.getTk_okcard()+record.getTmk_total()+record.getLcdh_total());
					} catch (Exception e) {
						e.printStackTrace();
						record =null;
					}
					return record;
				}
			};
			CompAmtAnlysisBean retRecord= (CompAmtAnlysisBean) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return retRecord;
	}
	
	public ArrayList<CompLogAnlysisBean> analysisExceptionBills(String strCompId,String strDateFrom,String strDateTo)
	{	
		String strSql = "exec upg_analysis_system_error_new '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"' " ;
		AnlyResultSet<ArrayList<CompLogAnlysisBean>> analysis = new AnlyResultSet<ArrayList<CompLogAnlysisBean>>() {
			public ArrayList<CompLogAnlysisBean> anlyResultSet(ResultSet rs) {
				ArrayList<CompLogAnlysisBean> dataSet = new ArrayList<CompLogAnlysisBean>();
				CompLogAnlysisBean record=null;
				try 
				{
					while (rs != null && rs.next() == true)
					{
						record = new CompLogAnlysisBean();
						record.setCompId(CommonTool.FormatString(rs.getString("compid")));
						record.setBillType(CommonTool.FormatString(rs.getString("billtype")));
						record.setErrorId(CommonTool.FormatString(rs.getString("errid")));
						record.setErrorDescription(CommonTool.FormatString(rs.getString("errdescript")));
						record.setRemark(CommonTool.FormatString(rs.getString("remark")));
						dataSet.add(record);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					dataSet =null;
				}
				return dataSet;
			}
		};
		ArrayList<CompLogAnlysisBean> retRecord= (ArrayList<CompLogAnlysisBean>) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return retRecord;
	}
	
	
	public String  closeAccountLog(String strCompId,String strDate)
	{
		try
		{
			String strSql=" select 1 from accountclosureinfo where closecompid='"+strCompId+"' and closedate='"+strDate+"' ";
			int count=this.amn_Dao.getRowsCount_Ex(strSql);
			if(count>0)
				return "FAILURE";
			else
			{
				strSql=" insert accountclosureinfo(closecompid,closedate,closeoptioner,closeoptiondate,closeoptiontime) " +
						" values('"+strCompId+"','"+strDate+"','"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"' )";
				this.amn_Dao.executeSql(strSql);
				return "SUCCESS";
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "EXCEPTION";
		}
	}
	
	public boolean  uncloseAccountLog(String strCompId,String strDate)
	{
		try
		{
			String strSql =" delete accountclosureinfo where closecompid='"+strCompId+"' and closedate='"+strDate+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean checkMgoodsreceipt(String strCompId)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(formatter.parse(CommonTool.getCurrDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int week=cal.get(Calendar.DAY_OF_WEEK)-1;
		
		String strCurDate=formatter.format(cal.getTime());
		strCurDate=CommonTool.datePlusDay(strCurDate, -(week+9));
		//System.out.println(strCurDate);
		try {
			cal.setTime(formatter.parse(strCurDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.WEDNESDAY, 1);
		String strFromDate=CommonTool.datePlusDay(formatter.format(cal.getTime()), -1);
		String strSql="select count(receiptbillid) from mgoodsreceipt b, mgoodssendinfo a where  isnull(receiptstate,0)=0 and receiptsendbillid=sendbillid and senddate between '"+strCurDate+"' and '"+strFromDate+"' and receiptcompid='"+strCompId+"'";
		if(amn_Dao.getRowsCount_Ex(strSql)>0)
		{
			return false;
		}
		else 
		{
			return true;
		}
	}
	
	public static void main(String[] args) {
		//AC005Service ac005Service=new AC005Service();
		//ac005Service.checkMgoodsreceipt(CommonTool.getCurrDate());
	}
	
}
