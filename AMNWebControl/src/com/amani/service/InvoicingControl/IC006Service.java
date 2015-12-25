package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.GoodsStockChangeBean;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class IC006Service extends AMN_ReportService{
	
	public List<GoodsStockChangeBean> loadDateSetByCompId(String strCompId,String strFromDate,String strToDate,String strFromGoodsId,String strToGoodsId,String strWareId)
	{
		try
		{
			String strSql=" exec upg_changestock_analysis '"+strCompId+"','"+strFromDate+"','"+strToDate+"','"+strFromGoodsId+"','"+strToGoodsId+"','"+strWareId+"' ";
			AnlyResultSet<List<GoodsStockChangeBean>> analysis = new AnlyResultSet<List<GoodsStockChangeBean>>() {
				public List<GoodsStockChangeBean> anlyResultSet(ResultSet rs) {
					List<GoodsStockChangeBean> returnValue = new ArrayList();
					GoodsStockChangeBean record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new GoodsStockChangeBean();
							record.setStrGoodsNo(CommonTool.FormatString(rs.getString("itemno")));
							record.setStrGoodsName(CommonTool.FormatString(rs.getString("itemname")));
							record.setStrWareId(CommonTool.FormatString(rs.getString("storage")));
							record.setStrWareName(CommonTool.FormatString(rs.getString("storagename")));
							record.setStrChangeDate(CommonTool.getDateMask(rs.getString("ddate")));
							record.setStrChangeType(CommonTool.FormatString(rs.getString("changetype")));
							record.setStrChangeBillNo(CommonTool.FormatString(rs.getString("billno")));
							record.setBInserGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("insercount")))));
							record.setBOuterGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("outrecount")))));
							record.setBCurGoodsCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("stock")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<GoodsStockChangeBean> ls= (List<GoodsStockChangeBean>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
