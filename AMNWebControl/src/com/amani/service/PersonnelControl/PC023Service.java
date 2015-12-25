package com.amani.service.PersonnelControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.BlackList;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC023Service extends AMN_ReportService{
	
	public List<BlackList> loadDateSetByStartFromDateAndEndFromDate(String startFromDate,String endFromDate)
	{
		try
		{
			String strSql="select id,mobilephone,acceptdate,content,operdate from blacklist where operdate BETWEEN '"+CommonTool.setDateMask(startFromDate)+"' and '"+CommonTool.setDateMask(endFromDate)+"'";
			AnlyResultSet<List<BlackList>> analysis = new AnlyResultSet<List<BlackList>>() {
				public List<BlackList> anlyResultSet(ResultSet rs) {
					List<BlackList> returnValue = new ArrayList();
					BlackList record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new BlackList();
							record.setId(CommonTool.FormatString(rs.getString("id")));
							record.setMobilephone(CommonTool.FormatString(rs.getString("mobilephone")));
							record.setAcceptdate(CommonTool.getDateMask(rs.getString("acceptdate")));
							String content = CommonTool.FormatString(rs.getString("content"));
							if("1".equals(content)){
								content = "满意";
							}else if("2".equals(content)){
								content = "投诉";
							}else if("3".equals(content)){
								content = "退订";
							}
							record.setContent(content);
							record.setOperdate(CommonTool.getDateMask(rs.getString("operdate")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<BlackList> ls= (List<BlackList>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
