package com.amani.service.SchoolControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CommonBean;
import com.amani.service.AMN_ReportService;
@Service
public class SCC007Service extends AMN_ReportService{
	
	public List<CommonBean> loadDataSet(String dateFrom, String dateTo, String creditNo, String positionNo){
		try{	
			StringBuffer sql = new StringBuffer();
			sql.append("select b.compname, d.staffname, d.position, c.name,")
				.append("CONVERT(varchar(10), a.operationdate, 23) operationdate from schoolemployee a ")
				.append("left join companyinfo b on a.salecompid=b.compno left join schoolcredit c on a.credit=c.no ")
				.append("left join staffinfo d on a.staffno=d.staffno where d.staffname<>'' and a.operationdate ")
				.append("between '"+ dateFrom +" 00:00:00' and '"+ dateTo +" 23:59:59' ");
			if(!StringUtils.equals("-1", creditNo)){
				sql.append("and a.credit='"+ creditNo +"' ");
			}
			if(!StringUtils.equals("-1", positionNo)){
				sql.append("and d.position='"+ positionNo +"'");
			}
			AnlyResultSet<List<CommonBean>> analysis = new AnlyResultSet<List<CommonBean>>() {
				public List<CommonBean> anlyResultSet(ResultSet rs) {
					List<CommonBean> returnValue = new ArrayList<CommonBean>();
					CommonBean record=null;
					try {
						while (rs.next()) {
							record=new CommonBean();
							record.setAttr1(rs.getString("compname"));
							record.setAttr2(rs.getString("staffname"));
							record.setAttr3(rs.getString("position"));
							record.setAttr4(rs.getString("name"));
							record.setAttr5(rs.getString("operationdate"));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			@SuppressWarnings("unchecked")
			List<CommonBean> ls= (List<CommonBean>) this.amn_Dao.executeQuery_ex(sql.toString(), analysis);
			analysis=null;
			return ls;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<CommonBean> loadDetailData(String dateFrom, String dateTo, String staffNo, String staffName){
		try{	
			StringBuffer sql = new StringBuffer();
			sql.append("select c.name,CONVERT(varchar(10), a.operationdate, 23) operationdate from schoolemployee a ")
			.append("left join schoolcredit c on a.credit=c.no left join staffinfo d on a.staffno=d.staffno ")
			.append("where a.operationdate between '"+ dateFrom +" 00:00:00' and '"+ dateTo +" 23:59:59' ");
			if(StringUtils.isNotBlank(staffNo)){
				sql.append("and a.staffno='"+ staffNo +"' ");
			}
			if(StringUtils.isNotBlank(staffName)){
				sql.append("and d.staffname='"+ staffName +"'");
			}
			AnlyResultSet<List<CommonBean>> analysis = new AnlyResultSet<List<CommonBean>>() {
				public List<CommonBean> anlyResultSet(ResultSet rs) {
					List<CommonBean> returnValue = new ArrayList<CommonBean>();
					CommonBean record=null;
					try {
						while (rs.next()) {
							record=new CommonBean();
							record.setAttr1(rs.getString("name"));
							record.setAttr2(rs.getString("operationdate"));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			@SuppressWarnings("unchecked")
			List<CommonBean> ls= (List<CommonBean>) this.amn_Dao.executeQuery_ex(sql.toString(), analysis);
			analysis=null;
			return ls;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
