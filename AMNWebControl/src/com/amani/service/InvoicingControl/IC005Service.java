package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.GoodsInserorOuterBean;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class IC005Service extends AMN_ReportService{
	
	public List<GoodsInserorOuterBean> loadDateSetByCompId(String strCompId,String strFromDate,String strToDate,String strFromGoodsId,String strToGoodsId,String searchGoodsType)
	{
		try
		{
			String strSql=" exec upg_inoutstock_analysis_other '"+strCompId+"','"+strFromDate+"','"+strToDate+"','"+strFromGoodsId+"','"+strToGoodsId+"','"+searchGoodsType+"' ";
			AnlyResultSet<List<GoodsInserorOuterBean>> analysis = new AnlyResultSet<List<GoodsInserorOuterBean>>() {
				public List<GoodsInserorOuterBean> anlyResultSet(ResultSet rs) {
					List<GoodsInserorOuterBean> returnValue = new ArrayList();
					GoodsInserorOuterBean record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new GoodsInserorOuterBean();
							record.setStrGoodsNo(CommonTool.FormatString(rs.getString("goodsno")));
							record.setStrGoodsName(CommonTool.FormatString(rs.getString("goodsname")));
							record.setStrGoodsUnit(CommonTool.FormatString(rs.getString("unit")));
							record.setStrGoodsClassName(CommonTool.FormatString(rs.getString("goodsclassname")));
							record.setBBeginGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("pquan")))));
							record.setBBeginGoodsAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("pamt")))));
							record.setBInserGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("inquan")))));
							record.setBInserGoodsAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("inamt")))));
							record.setBHandSelGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("hinquan")))));
							record.setBHandSelGoodsAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("hinamt")))));
							record.setBQuitGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("qquan")))));
							record.setBQuitGoodsAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("qamt")))));
							record.setBOuterGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("outquan")))));
							record.setBOuterGoodsAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("outamt")))));
							record.setBEndGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("equan")))));
							record.setBEndGoodsAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("eamt")))));
							record.setBSendGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("squan")))));
							record.setBSendGoodsAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("samt")))));
							record.setBCostGoodsAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("pamt"))+CommonTool.FormatDouble(rs.getDouble("inamt"))+CommonTool.FormatDouble(rs.getDouble("qamt"))-CommonTool.FormatDouble(rs.getDouble("eamt")))));
							
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<GoodsInserorOuterBean> ls= (List<GoodsInserorOuterBean>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	
	
	
	public boolean reloadStockByGoodsNo(String strCompId,String strFromDate,String strToDate,String strGoodsNo)
	{
		try
		{
			String strSql="	declare @datefrom varchar(10) " +
					"	declare @dateto   varchar(10) " +
					"	set @datefrom='"+strFromDate+"' " +
					"	set @dateto='"+strToDate+"' " +
					"	declare @tmpdate varchar(8)   " +
					"   declare @tmpenddate varchar(8)  " +
					"	set @tmpenddate = @datefrom     " +
					"   set @tmpdate = @datefrom    " +
					"   while (@tmpenddate <= @dateto)  " +
					"  begin   " +
					"   exec upg_goods_begin_result_daybyday_goodno '"+strCompId+"','"+strGoodsNo+"','"+strGoodsNo+"',@tmpenddate " +
					"	execute upg_date_plus @tmpdate,1,@tmpenddate output  " +
					"  set @tmpdate = @tmpenddate    end   ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
}
