package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Dgoodsbarinfo;
import com.amani.model.Dgoodsinsert;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class IC015Service extends AMN_ReportService{
	
	public List<Dgoodsbarinfo> loadDateSetByCompId(String strCompId,String strGoodsNo,String strGoodsBarNo,int iBarState)
	{
		try
		{
			String strSql=" select a.goodsno,a.goodsbarno,b.goodsname,barnostate,createdate,createstaffno,inserdate,inserbillno,outerdate,outerbill,receivestore,costdate,costbillo,coststore,proddate " +
		      " from  dgoodsbarinfo a,goodsnameinfo b where a.goodsno=b.goodsno and (receivestore='"+strCompId+"' or '"+strCompId+"'='001')" +
		      		" and (a.goodsno='"+strGoodsNo+"' or '"+strGoodsNo+"'='' ) " +
		      		" and (a.barnostate="+iBarState+" or "+iBarState+"=(-1) ) " +
		      		" and (a.goodsbarno='"+strGoodsBarNo+"' or '"+strGoodsBarNo+"'='' ) ";

			AnlyResultSet<List<Dgoodsbarinfo>> analysis = new AnlyResultSet<List<Dgoodsbarinfo>>() {
				public List<Dgoodsbarinfo> anlyResultSet(ResultSet rs) {
					List<Dgoodsbarinfo> returnValue = new ArrayList();
					Dgoodsbarinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Dgoodsbarinfo();
							record.setBgoodsno(CommonTool.FormatString(rs.getString("goodsno")));
							record.setGoodsText(CommonTool.FormatString(rs.getString("goodsname")));
							record.setBgoodsbarno(CommonTool.FormatString(rs.getString("goodsbarno")));
							int barnostate=CommonTool.FormatInteger(rs.getInt("barnostate"));
							//0:生成 1:入库 2:发货/出库 3:销售 4:消耗 5:损坏
							if(barnostate==0)
								record.setBarnostateText("已生成");
							else if(barnostate==1)
								record.setBarnostateText("已入库");
							else if(barnostate==2)
								record.setBarnostateText("已发货/出库");
							else if(barnostate==3)
								record.setBarnostateText("已销售");
							else if(barnostate==4)
								record.setBarnostateText("已消耗");
							else if(barnostate==5)
								record.setBarnostateText("损坏");
							else if(barnostate==6)
								record.setBarnostateText("退货中");
							record.setCreatedate(CommonTool.getDateMask(rs.getString("createdate")));
							record.setCreatestaffno(CommonTool.FormatString(rs.getString("createstaffno")));
							record.setInserdate(CommonTool.getDateMask(rs.getString("inserdate")));
							record.setInserbillno(CommonTool.FormatString(rs.getString("inserbillno")));
							record.setOuterdate(CommonTool.getDateMask(rs.getString("outerdate")));
							record.setOuterbill(CommonTool.FormatString(rs.getString("outerbill")));
							record.setReceivestore(CommonTool.FormatString(rs.getString("receivestore")));
							record.setCostdate(CommonTool.getDateMask(rs.getString("costdate")));
							record.setCostbillo(CommonTool.FormatString(rs.getString("costbillo")));
							record.setCoststore(CommonTool.FormatString(rs.getString("coststore")));
							record.setProddate(CommonTool.getDateMask(rs.getString("proddate")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Dgoodsbarinfo> ls= (List<Dgoodsbarinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean checkBarInfo(String strFromBarId,String strToBarId)
	{
		String strSql="select count(goodsno) from dgoodsbarinfo where goodsbarno between '"+strFromBarId+"' and '"+strToBarId+"' ";
		if(amn_Dao.getRowsCount_Ex(strSql)>0)
		{
			return true;
		}
		return false;
	}
	
	public boolean updProdDate(List<Dgoodsinsert> lsDgoodsinserts)
	{
		StringBuffer strSql=new StringBuffer();
		if(lsDgoodsinserts!=null && lsDgoodsinserts.size()>0)
		{
			for(Dgoodsinsert dgoodsinsert:lsDgoodsinserts)
			{
				if(CommonTool.checkStr(dgoodsinsert.getProducedate()))
				{
					strSql.append(" update dgoodsbarinfo set proddate='"+CommonTool.setDateMask(dgoodsinsert.getProducedate())+"' where goodsbarno between '"+dgoodsinsert.getFrombarcode()+"' and '"+dgoodsinsert.getTobarcode()+"' and barnostate in(1,2) ");
				}
				else 
				{
					strSql.append(" update dgoodsbarinfo set enddate='"+CommonTool.setDateMask(dgoodsinsert.getProduceenddate())+"' where goodsbarno between '"+dgoodsinsert.getFrombarcode()+"' and '"+dgoodsinsert.getTobarcode()+"' and barnostate in(1,2) ");
				}
			}
		}
		if(CommonTool.checkStr(strSql.toString()))
		{
			return amn_Dao.executeSql(strSql.toString());
		}
		return true;
	}
	
	
	
	
}
