package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Dconsumeinfo;
import com.amani.model.Projectcostinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class IC017Service extends AMN_ReportService{
	
	public List<Projectcostinfo> loadDateSetByCompId(String strCompId,String strFromDate,String strToDate,String strFromGoodsId,String strToGoodsId,String strGoodsType)
	{
		try
		{
			String strSql=" exec upg_projectcostgoods_analysis '"+strCompId+"','"+strFromDate+"','"+strToDate+"','"+strFromGoodsId+"','"+strToGoodsId+"','"+strGoodsType+"' ";
			AnlyResultSet<List<Projectcostinfo>> analysis = new AnlyResultSet<List<Projectcostinfo>>() {
				public List<Projectcostinfo> anlyResultSet(ResultSet rs) {
					List<Projectcostinfo> returnValue = new ArrayList();
					Projectcostinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Projectcostinfo();
							record.setStrCompId(CommonTool.FormatString(rs.getString("compid")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("compname")));
							record.setGoodsno(CommonTool.FormatString(rs.getString("goodsno")));
							record.setGoodsname(CommonTool.FormatString(rs.getString("goodsname")));
							record.setGoodstype(CommonTool.FormatString(rs.getString("goodstype")));
							record.setGoodsunit(CommonTool.FormatString(rs.getString("goodsunit")));
							record.setCostunitcount(CommonTool.FormatDouble(rs.getDouble("costunitcount")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Projectcostinfo> ls= (List<Projectcostinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

		
	public  List<Dconsumeinfo> loadDataSet(String strCompId,String strFromDate,String toDate,String strGoodsNo)
	{
		try
		{
			String strSql="select a.csbillid,e.financedate,csitemno,b.prjname,csitemcount=SUM(csitemcount),csdiscount=sum(ISNULL(costunitcount,0)),a.csfirstsaler,c.staffname " +
					" from mconsumeinfo e with(nolock),dconsumeinfo a with(nolock),projectnameinfo b with(nolock),staffinfo c with(nolock),projectcostinfo d with(nolock) " +
					" where e.cscompid=a.cscompid and e.csbillid=a.csbillid and e.financedate between '"+strFromDate+"' and '"+toDate+"' " +
					" and a.cscompid='"+strCompId+"' and a.csitemno=b.prjno and a.csfirstinid=c.manageno " +
					" and d.goodsno='"+strGoodsNo+"' and d.prjno=a.csitemno " +
					" group by e.financedate,a.csbillid,csitemno,b.prjname,a.csfirstsaler,c.staffname ";
			AnlyResultSet<List<Dconsumeinfo>> analysis = new AnlyResultSet<List<Dconsumeinfo>>() {
				public List<Dconsumeinfo> anlyResultSet(ResultSet rs) {
					List<Dconsumeinfo> returnValue = new ArrayList();
					Dconsumeinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Dconsumeinfo();
							record.setBcsbillid(CommonTool.FormatString(rs.getString("csbillid")));
							record.setFinancedate(CommonTool.getDateMask(rs.getString("financedate")));
							record.setCsitemno(CommonTool.FormatString(rs.getString("csitemno")));
							record.setCsitemname(CommonTool.FormatString(rs.getString("prjname")));
							record.setCsfirstsaler(CommonTool.FormatString(rs.getString("csfirstsaler")));
							record.setCsfirstinid(CommonTool.FormatString(rs.getString("staffname")));
							record.setCsitemcount(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("csitemcount"))));
							record.setCsdiscount(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("csdiscount"))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Dconsumeinfo> ls= (List<Dconsumeinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
