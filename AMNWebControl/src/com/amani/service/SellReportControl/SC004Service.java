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
public class SC004Service extends AMN_ReportService{
	
	public List<CostAnalysisBean> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,String strSearType)
	{
		try
		{
			String strSql="";
			if(strSearType.equals("1")) //其他店的卡在本店消费
			{
				 // --本店卡在其他店消费
				strSql=" select cscompid,cscompname=a.compname,cscardno,cscardtype,cardtypename,cardvesting,cardcompname=b.compname,payamt=sum(isnull(payamt,0))" +
						"   from mconsumeinfo with (NOLOCK),dpayinfo with (NOLOCK),cardinfo with (NOLOCK),cardtypenameinfo,companyinfo a ,companyinfo b  " +
						"   where cscompid=paycompid " +
						"   and csbillid=paybillid  " +
						"   and cscardno=cardno    " +
						"   and financedate between '"+strDateFrom+"' and '"+strDateTo+"' " +
						"   and paybilltype='SY'" +
						"   and paymode in ('4','9','17')  " +
						" 	and cscompid<>'"+strCompId+"' " +
						"   and cardvesting='"+strCompId+"' and  cardtypeno=cscardtype" +
						"   and cscompid=a.compno and cardvesting=b.compno" +
						" group by cscompid,a.compname,cscardno,cscardtype,cardtypename,cardvesting,b.compname ";
				      
			}
			else if(strSearType.equals("2")) //本店的卡在其他店消费
			{
				strSql=" select cscompid,cscompname=a.compname,cscardno,cscardtype,cardtypename,cardvesting,cardcompname=b.compname,payamt=sum(isnull(payamt,0))" +
				"   from mconsumeinfo with (NOLOCK),dpayinfo with (NOLOCK),cardinfo with (NOLOCK),cardtypenameinfo,companyinfo a ,companyinfo b  " +
				"   where cscompid=paycompid " +
				"   and csbillid=paybillid  " +
				"   and cscardno=cardno    " +
				"   and financedate between '"+strDateFrom+"' and '"+strDateTo+"' " +
				"   and paybilltype='SY'" +
				"   and paymode in ('4','9','17')  " +
				" 	and cscompid='"+strCompId+"' " +
				"   and cardvesting<>'"+strCompId+"' and  cardtypeno=cscardtype" +
				"   and cscompid=a.compno and cardvesting=b.compno" +
				" group by cscompid,a.compname,cscardno,cscardtype,cardtypename,cardvesting,b.compname ";
	 	   	}
			else if(strSearType.equals("3")) //本店的卡在本店消费
			{
				strSql=" select cscompid,cscompname=a.compname,cscardno,cscardtype,cardtypename,cardvesting,cardcompname=b.compname,payamt=sum(isnull(payamt,0))" +
				"   from mconsumeinfo with (NOLOCK),dpayinfo with (NOLOCK),cardinfo with (NOLOCK),cardtypenameinfo,companyinfo a ,companyinfo b  " +
				"   where cscompid=paycompid " +
				"   and csbillid=paybillid  " +
				"   and cscardno=cardno    " +
				"   and financedate between '"+strDateFrom+"' and '"+strDateTo+"' " +
				"   and paybilltype='SY'" +
				"   and paymode in ('4','9','17')  " +
				" 	and cscompid='"+strCompId+"' " +
				"   and cardvesting='"+strCompId+"' and  cardtypeno=cscardtype" +
				"   and cscompid=a.compno and cardvesting=b.compno" +
				" group by cscompid,a.compname,cscardno,cscardtype,cardtypename,cardvesting,b.compname ";
	
			}
			AnlyResultSet<List<CostAnalysisBean>> analysis = new AnlyResultSet<List<CostAnalysisBean>>() {
				public List<CostAnalysisBean> anlyResultSet(ResultSet rs) {
					List<CostAnalysisBean> returnValue = new ArrayList();
					CostAnalysisBean record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new CostAnalysisBean();
							record.setCostCompId(CommonTool.FormatString(rs.getString("cscompid"))+"-"+CommonTool.FormatString(rs.getString("cscompname")));
							record.setCostCardNo(CommonTool.FormatString(rs.getString("cscardno")));
							record.setCostCardType(CommonTool.FormatString(rs.getString("cscardtype"))+"-"+CommonTool.FormatString(rs.getString("cardtypename")));
							record.setHomeCompId(CommonTool.FormatString(rs.getString("cardvesting"))+"-"+CommonTool.FormatString(rs.getString("cardcompname")));
							record.setCostAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("payamt")))));
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
