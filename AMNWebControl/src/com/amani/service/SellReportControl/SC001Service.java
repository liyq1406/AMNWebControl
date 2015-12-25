package com.amani.service.SellReportControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.DetialTradeTyday;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC001Service extends AMN_ReportService{
	
	public List<DetialTradeTyday> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,String strSearType)
	{
		try
		{
			String strSql="";
			if(strSearType.equals("2")) //按日期查询
			{
				strSql=" select shopId,shopName,dateReport,total=sum(isnull(total,0)),cashService=sum(isnull(cashService,0)),cashProd=sum(isnull(cashProd,0)),cashCardTrans=sum(isnull(cashCardTrans,0)),cashTotal=sum(isnull(cashTotal,0)),cashBackCard=sum(isnull(cashBackCard,0))," +
					" creditService=sum(isnull(creditService,0)),creditProd=sum(isnull(creditProd,0)),creditTrans=sum(isnull(creditTrans,0)),creditTotal=sum(isnull(creditTotal,0)),creditBackCard=sum(isnull(creditBackCard,0)),checkService=sum(isnull(checkService,0)),checkProd=sum(isnull(checkProd,0)),checkTrans=sum(isnull(checkTrans,0)),checkTotal=sum(isnull(checkTotal,0)),checkBackCard=sum(isnull(checkBackCard,0))," +
					" zftService=sum(isnull(zftService,0)),zftProd=sum(isnull(zftProd,0)),zftTrans=sum(isnull(zftTrans,0)), zftTotal=sum(isnull(zftTotal,0)),zftBackCard=sum(isnull(zftBackCard,0)),ockService=sum(isnull(ockService,0)),ockkProd=sum(isnull(ockkProd,0)),ockTrans=sum(isnull(ockTrans,0)), ockTotal=sum(isnull(ockTotal,0)),tgkService=sum(isnull(tgkService,0)), tgkkProd=sum(isnull(tgkkProd,0)),tgkTrans=sum(isnull(tgkTrans,0)),tgkTotal=sum(isnull(tgkTotal,0))," +
					" totalCardTrans=sum(isnull(totalCardTrans,0)), cashchangesale=sum(isnull(cashchangesale,0)),bankchangesale=sum(isnull(bankchangesale,0)),cashbytmkSale=sum(isnull(cashbytmkSale,0)),bankbytmkSale=sum(isnull(bankbytmkSale,0)),checkbytmkSale=sum(isnull(checkbytmkSale,0)),fingerbytmkSale=sum(isnull(fingerbytmkSale,0)),okpqypwybytmkSale=sum(isnull(okpqypwybytmkSale,0))," +
					" cashhezprj=sum(isnull(cashhezprj,0)),bankhezprj=sum(isnull(bankhezprj,0)),sumcashhezprj=sum(isnull(sumcashhezprj,0)),cardSalesServices=sum(isnull(cardSalesServices,0)), cardSalesprod=sum(isnull(cardSalesprod,0)),staffsallprod=sum(isnull(staffsallprod,0)),acquisitionCardServices=sum(isnull(acquisitionCardServices,0)),costpointTotal=sum(isnull(costpointTotal,0)),cashdyService=sum(isnull(cashdyService,0)),prjdyService=sum(isnull(prjdyService,0)),tmkService=sum(isnull(tmkService,0)),tmksendservice=sum(isnull(tmksendservice,0))," +
					" manageSigning=sum(isnull(manageSigning,0)), payOutRegister=sum(isnull(payOutRegister,0)),cardsalestotal=sum(isnull(cardsalesservices,0)+isnull(cardsalesprod,0)+isnull(acquisitioncardservices,0)+isnull(cashYearCard,0)+isnull(creditYearCard,0))  " +
					" ,cashYearCard=sum(cashYearCard),creditYearCard=sum(creditYearCard),yearsaleservice=sum(yearsaleservice),jfysk_xj=sum(isnull(jfysk_xj,0)),jfysk_yhk=sum(isnull(jfysk_yhk,0)),jfysk_qt=sum(isnull(jfysk_qt,0)),jfysk=sum(isnull(jfysk,0))"+
					" from detial_trade_byday_fromshops  " +
					" where shopId='"+strCompId+"' and dateReport between '"+strDateFrom+"' and '"+strDateTo+"'" +
					" group by shopId,shopName,dateReport";
			}
			else
			{
				strSql=" select shopId,shopName,dateReport='"+strDateFrom+"-"+strDateTo+"',total=sum(isnull(total,0)),cashService=sum(isnull(cashService,0)),cashProd=sum(isnull(cashProd,0)),cashCardTrans=sum(isnull(cashCardTrans,0)),cashTotal=sum(isnull(cashTotal,0)),cashBackCard=sum(isnull(cashBackCard,0))," +
					" creditService=sum(isnull(creditService,0)),creditProd=sum(isnull(creditProd,0)),creditTrans=sum(isnull(creditTrans,0)),creditTotal=sum(isnull(creditTotal,0)),creditBackCard=sum(isnull(creditBackCard,0)),checkService=sum(isnull(checkService,0)),checkProd=sum(isnull(checkProd,0)),checkTrans=sum(isnull(checkTrans,0)),checkTotal=sum(isnull(checkTotal,0)),checkBackCard=sum(isnull(checkBackCard,0))," +
					" zftService=sum(isnull(zftService,0)),zftProd=sum(isnull(zftProd,0)),zftTrans=sum(isnull(zftTrans,0)), zftTotal=sum(isnull(zftTotal,0)),zftBackCard=sum(isnull(zftBackCard,0)),ockService=sum(isnull(ockService,0)),ockkProd=sum(isnull(ockkProd,0)),ockTrans=sum(isnull(ockTrans,0)), ockTotal=sum(isnull(ockTotal,0)),tgkService=sum(isnull(tgkService,0)), tgkkProd=sum(isnull(tgkkProd,0)),tgkTrans=sum(isnull(tgkTrans,0)),tgkTotal=sum(isnull(tgkTotal,0))," +
					" totalCardTrans=sum(isnull(totalCardTrans,0)), cashchangesale=sum(isnull(cashchangesale,0)),bankchangesale=sum(isnull(bankchangesale,0)),cashbytmkSale=sum(isnull(cashbytmkSale,0)),bankbytmkSale=sum(isnull(bankbytmkSale,0)),checkbytmkSale=sum(isnull(checkbytmkSale,0)),fingerbytmkSale=sum(isnull(fingerbytmkSale,0)),okpqypwybytmkSale=sum(isnull(okpqypwybytmkSale,0))," +
					" cashhezprj=sum(isnull(cashhezprj,0)),bankhezprj=sum(isnull(bankhezprj,0)),sumcashhezprj=sum(isnull(sumcashhezprj,0)),cardSalesServices=sum(isnull(cardSalesServices,0)), cardSalesprod=sum(isnull(cardSalesprod,0)),staffsallprod=sum(isnull(staffsallprod,0)),acquisitionCardServices=sum(isnull(acquisitionCardServices,0)),costpointTotal=sum(isnull(costpointTotal,0)),cashdyService=sum(isnull(cashdyService,0)),prjdyService=sum(isnull(prjdyService,0)),tmkService=sum(isnull(tmkService,0)),tmksendservice=sum(isnull(tmksendservice,0))," +
					" manageSigning=sum(isnull(manageSigning,0)), payOutRegister=sum(isnull(payOutRegister,0)),cardsalestotal=sum(isnull(cardsalesservices,0)+isnull(cardsalesprod,0)+isnull(acquisitioncardservices,0)+isnull(cashYearCard,0)+isnull(creditYearCard,0)) " +
					" ,cashYearCard=sum(cashYearCard),creditYearCard=sum(creditYearCard),yearsaleservice=sum(yearsaleservice),jfysk_xj=sum(isnull(jfysk_xj,0)),jfysk_yhk=sum(isnull(jfysk_yhk,0)),jfysk_qt=sum(isnull(jfysk_qt,0)),jfysk=sum(isnull(jfysk,0))"+
					" from detial_trade_byday_fromshops,compchaininfo,compchainstruct " +
					" where shopId=curcompno and complevel=4 and curcomp='"+strCompId+"' and shopId=relationcomp  and dateReport between '"+strDateFrom+"' and '"+strDateTo+"'" +
					" group by shopId,shopName order by shopId ";
			}
			AnlyResultSet<List<DetialTradeTyday>> analysis = new AnlyResultSet<List<DetialTradeTyday>>() {
				public List<DetialTradeTyday> anlyResultSet(ResultSet rs) {
					List<DetialTradeTyday> returnValue = new ArrayList();
					DetialTradeTyday record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new DetialTradeTyday();
							record.setShopId(CommonTool.FormatString(rs.getString("shopId")));
							record.setShopName(CommonTool.FormatString(rs.getString("shopName")));
							record.setDateReport(rs.getString("dateReport"));
							record.setStrtotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("total")))).toString());
							record.setTotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("total")))));
							record.setCashservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashService")))));
							record.setCashprod(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashProd")))));
							record.setCashcardtrans(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashcardtrans")))));
							record.setCashbackcard(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashbackcard")))));
							record.setCashtotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashtotal")))));
							record.setCreditservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("creditservice")))));
							record.setCreditprod(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("creditprod")))));
							record.setCredittrans(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("credittrans")))));
							record.setCreditbackcard(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("creditbackcard")))));
							record.setCredittotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("credittotal")))));
							record.setCheckservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("checkservice")))));
							record.setCheckprod(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("checkprod")))));
							record.setChecktrans(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("checktrans")))));
							record.setCheckbackcard(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("checkbackcard")))));
							record.setChecktotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("checktotal")))));
							record.setZftservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("zftservice")))));
							record.setZftprod(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("zftprod")))));
							record.setZfttrans(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("zfttrans")))));
							record.setZftbackcard(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("zftbackcard")))));
							record.setZfttotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("zfttotal")))));
							record.setOckservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ockservice")))));
							record.setOckkprod(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ockkprod")))));
							record.setOcktrans(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ocktrans")))));
							record.setOcktotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ocktotal")))));
							record.setTgkservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tgkservice")))));
							record.setTgkkprod(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tgkkprod")))));
							record.setTgktrans(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tgktrans")))));
							record.setTgktotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tgktotal")))));
							record.setCashYearCard(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashYearCard")))));
							record.setCreditYeardcard(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("creditYearCard")))));
							record.setYearsaleservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("yearsaleservice")))));
							record.setTotalcardtrans(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalcardtrans")))));
							record.setCashchangesale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashchangesale")))));
							record.setBankchangesale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("bankchangesale")))));
							record.setCashbytmksale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashbytmksale")))));
							record.setBankbytmksale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("bankbytmksale")))));
							record.setCheckbytmksale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("checkbytmksale")))));
							record.setFingerbytmksale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fingerbytmksale")))));
							record.setOkpqypwybytmksale(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("okpqypwybytmksale")))));
							record.setCashhezprj(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashhezprj")))));
							record.setBankhezprj(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("bankhezprj")))));
							record.setSumcashhezprj(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sumcashhezprj")))));
							record.setCardsalesservices(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardsalesservices")))));
							record.setCardsalesprod(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardsalesprod")))));
							record.setCardsalestotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardsalestotal")))));
							record.setStaffsallprod(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffsallprod")))));
							record.setAcquisitioncardservices(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("acquisitioncardservices")))));
							record.setCostpointtotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costpointtotal")))));
							record.setCashdyservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cashdyservice")))));
							record.setPrjdyservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("prjdyservice")))));
							record.setTmkservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tmkservice")))));
							record.setTmksendservice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tmksendservice")))));
							record.setManagesigning(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("managesigning")))));
							record.setPayoutRegister(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("payoutRegister")))));
							record.setJfysk_xj(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("jfysk_xj")))));
							record.setJfysk_yhk(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("jfysk_yhk")))));
							record.setJfysk_qt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("jfysk_qt")))));
							record.setJfysk(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("jfysk")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<DetialTradeTyday> ls= (List<DetialTradeTyday>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
