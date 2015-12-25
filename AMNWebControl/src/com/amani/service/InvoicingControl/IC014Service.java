package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.GoodsStockBean;
import com.amani.model.Dgoodsinsertpc;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class IC014Service extends AMN_ReportService{
	
	public List<GoodsStockBean> loadDateSetByCompId(String strCompId,String strDateFrom,String strWareId,String strFromGoodsNo,String strToGoodsNo)
	{
		try
		{
			String strSql=" exec upg_compute_goods_stock '"+strCompId+"','"+strDateFrom+"','"+strFromGoodsNo+"','"+strToGoodsNo+"','"+strWareId+"' ";
			
			AnlyResultSet<List<GoodsStockBean>> analysis = new AnlyResultSet<List<GoodsStockBean>>() {
				public List<GoodsStockBean> anlyResultSet(ResultSet rs) {
					List<GoodsStockBean> returnValue = new ArrayList();
					GoodsStockBean record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new GoodsStockBean();
							record.setStrGoodsNo(CommonTool.FormatString(rs.getString("goodsno")));
							record.setStrGoodsName(CommonTool.FormatString(rs.getString("goodsname")));
							record.setStrGoodsTypeName(CommonTool.FormatString(rs.getString("goodstypename")));
							record.setStrGoodsUnit(CommonTool.FormatString(rs.getString("unit")));
							record.setBGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("quantity")))));
							record.setBGoodsPrice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("price")))));
							record.setBGoodsAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("amt")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<GoodsStockBean> ls= (List<GoodsStockBean>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Dgoodsinsertpc> loadPcDateSetByCompId(String strCompId,String strWareId,String strFromGoodsNo,String strToGoodsNo)
	{
		try
		{
			String strSql="select inserbillid,producedate,expireddate,curlavecount from dgoodsinsertpc " +
					" where insercompid ='"+strCompId+"' and insergoodsno between '"+strFromGoodsNo+"' and '"+strToGoodsNo+"'" +
							" and (inserwareid='"+strWareId+"' or '"+strWareId+"'='') and isnull(curlavecount,0)>0 ";
			
			AnlyResultSet<List<Dgoodsinsertpc>> analysis = new AnlyResultSet<List<Dgoodsinsertpc>>() {
				public List<Dgoodsinsertpc> anlyResultSet(ResultSet rs) {
					List<Dgoodsinsertpc> returnValue = new ArrayList();
					Dgoodsinsertpc record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Dgoodsinsertpc();
							record.setInserbillid(CommonTool.FormatString(rs.getString("inserbillid")));
							record.setProducedate(CommonTool.getDateMask(rs.getString("producedate")));
							record.setExpireddate(CommonTool.getDateMask(rs.getString("expireddate")));
							record.setCurlavecount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("curlavecount")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Dgoodsinsertpc> ls= (List<Dgoodsinsertpc>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
