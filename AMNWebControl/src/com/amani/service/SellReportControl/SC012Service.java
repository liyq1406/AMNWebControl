package com.amani.service.SellReportControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.SC012Bean;


import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC012Service extends AMN_ReportService{
	
	


	public List<SC012Bean> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,int searchType)
	{
		String strSql = "exec upg_analysis_comp_changebill '"+strCompId+"','"+strDateFrom+"','"+strDateTo+"',"+searchType+" ";
		AnlyResultSet<List<SC012Bean>> analysis = new AnlyResultSet<List<SC012Bean>>() {
			public List<SC012Bean> anlyResultSet(ResultSet rs) {
				List<SC012Bean> returnValue = new ArrayList();
				SC012Bean record=null;
				try {
					while (rs != null && rs.next() == true) {
						record=new SC012Bean();
						record.setStrCompId(CommonTool.FormatString(rs.getString("compno")));
						record.setStrCompName(CommonTool.FormatString(rs.getString("compname")));
						record.setChangeType(CommonTool.FormatInteger(rs.getInt("changetype")));
						record.setChangeTypename(CommonTool.FormatString(rs.getString("changetypename")));
						record.setChangeDate(CommonTool.getDateMask(rs.getString("changedate")));
						record.setChangeBillno(CommonTool.FormatString(rs.getString("changebillno")));
						record.setOldCardNo(CommonTool.FormatString(rs.getString("oldcardno")));
						record.setOldCardType(CommonTool.FormatString(rs.getString("oldcardtype")));
						record.setOldCardTypename(CommonTool.FormatString(rs.getString("oldcardtypename")));
						record.setNewCardNo(CommonTool.FormatString(rs.getString("newcardno")));
						record.setNewCardType(CommonTool.FormatString(rs.getString("newcardtype")));
						record.setNewCardTypeName(CommonTool.FormatString(rs.getString("newcardtypename")));
						record.setMembername(CommonTool.FormatString(rs.getString("membername")));
						record.setChangeamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeamt")))));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		List<SC012Bean> ls= (List<SC012Bean>) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return ls;
	}

	
}
