package com.amani.service.SellReportControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CostAnalysisBean;


import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC003Service extends AMN_ReportService{
	
	public List<CostAnalysisBean> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,String strProjectNo,String strGoodsNo,String strCardNo,String strPayMode)
	{
		try
		{
			String strSql="select a.cscompid,a.csbillid,csdate,csendtime,cscardno,csname,csitemno,csitemname=prjname,csfirstsaler,csfirstinid,cssecondsaler,cssecondinid,csthirdsaler,csthirdinid,cspaymode,csdisprice,csitemcount,csitemamt" +
					"  from mconsumeinfo a with(nolock), dconsumeinfo b with(nolock) left join projectnameinfo on prjno=csitemno" +
					" where a.cscompid=b.cscompid and a.csbillid=b.csbillid and a.cscompid= '"+strCompId+"' " +
					" and financedate between '"+strDateFrom+"' and  '"+strDateTo+"' " +
					" and (cscardno='"+strCardNo+"' or '"+strCardNo+"'='') " +
					" and (cspaymode='"+strPayMode+"' or '"+strPayMode+"'='' )  " +
					" and (csitemno='"+strProjectNo+"' or '"+strProjectNo+"'='') and csinfotype=1 ";
			strSql=strSql+" union all";
			strSql=strSql+" select a.cscompid,a.csbillid,csdate,csendtime,cscardno,csname,csitemno,csitemname=goodsname,csfirstsaler,csfirstinid,cssecondsaler,cssecondinid,csthirdsaler,csthirdinid,cspaymode,csdisprice,csitemcount,csitemamt" +
			"  from mconsumeinfo a with(nolock), dconsumeinfo b with(nolock) left join goodsnameinfo on goodsno=csitemno" +
			" where a.cscompid=b.cscompid and a.csbillid=b.csbillid" +
			" and financedate between '"+strDateFrom+"' and  '"+strDateTo+"' and a.cscompid= '"+strCompId+"' " +
			" and (cscardno='"+strCardNo+"' or '"+strCardNo+"'='') " +
			" and (cspaymode='"+strPayMode+"' or '"+strPayMode+"'='' )  " +
			" and (csitemno='"+strGoodsNo+"' or '"+strGoodsNo+"'='') and csinfotype=2 ";
			
			AnlyResultSet<List<CostAnalysisBean>> analysis = new AnlyResultSet<List<CostAnalysisBean>>() {
				public List<CostAnalysisBean> anlyResultSet(ResultSet rs) {
					List<CostAnalysisBean> returnValue = new ArrayList();
					CostAnalysisBean record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new CostAnalysisBean();
							record.setCostCompId(CommonTool.FormatString(rs.getString("cscompid")));
							record.setCostBillId(CommonTool.FormatString(rs.getString("csbillid")));
							record.setCostDate(CommonTool.getDateMask(rs.getString("csdate")));
							record.setCostTime(CommonTool.getTimeMask(rs.getString("csendtime"), 1));
							record.setCostCardNo(CommonTool.FormatString(rs.getString("cscardno")));
							if(CommonTool.checkStr(rs.getString("csname")))
							{
								record.setCostMemberName(rs.getString("csname").substring(0,1)+"**");
							}
							else
							{
								record.setCostMemberName(rs.getString("csname"));
							}
							record.setCostItemNo(CommonTool.FormatString(rs.getString("csitemno")));
							record.setCostItemName(CommonTool.FormatString(rs.getString("csitemname")));
							record.setFirstStaffNo(CommonTool.FormatString(rs.getString("csfirstsaler"))+"-"+CommonTool.FormatString(rs.getString("csfirstinid")));
							record.setSecondStaffNo(CommonTool.FormatString(rs.getString("cssecondsaler"))+"-"+CommonTool.FormatString(rs.getString("cssecondinid")));
							record.setThirthStaffNo(CommonTool.FormatString(rs.getString("csthirdsaler"))+"-"+CommonTool.FormatString(rs.getString("csthirdinid")));
							record.setCostPayMode(CommonTool.FormatString(rs.getString("cspaymode")));
							record.setCostPrice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("csdisprice")))));
							record.setCostCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("csitemcount")))));
							record.setCostAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("csitemamt")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<CostAnalysisBean> ls= (List<CostAnalysisBean>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
