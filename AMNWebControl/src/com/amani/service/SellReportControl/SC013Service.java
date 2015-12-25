package com.amani.service.SellReportControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.SC013Bean;


import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC013Service extends AMN_ReportService{
	
	


	public List<SC013Bean> loadDateSetByCompId(String strCompId,String strDateFrom)
	{
		String strSql = "exec upg_analysis_system_shop '"+strCompId+"','"+strDateFrom+"0101','"+strDateFrom+"1231'";
		AnlyResultSet<List<SC013Bean>> analysis = new AnlyResultSet<List<SC013Bean>>() {
			public List<SC013Bean> anlyResultSet(ResultSet rs) {
				List<SC013Bean> returnValue = new ArrayList();
				SC013Bean record=null;
				try {
					int resusttyep=0;
					while (rs != null && rs.next() == true) {
						record=new SC013Bean();
						record.setStrCompId(CommonTool.FormatString(rs.getString("compno")));
						record.setStrCompName(CommonTool.FormatString(rs.getString("compname")));
						record.setStrPrjName(CommonTool.FormatString(rs.getString("resusttyeptext")));
						record.setResusttyep(CommonTool.FormatInteger(rs.getInt("resusttyep")));
						record.setShowtype(CommonTool.FormatInteger(rs.getInt("showtype")));
						resusttyep=CommonTool.FormatInteger(rs.getInt("resusttyep"));
						if(resusttyep==4 || resusttyep==6 || resusttyep==7 || resusttyep==15
						|| resusttyep==16 || resusttyep==17 || resusttyep==18 || resusttyep==19
						|| resusttyep==26 || resusttyep==27 || resusttyep==28 || resusttyep==29
						|| resusttyep==30 || resusttyep==41 || resusttyep==48 || resusttyep==49
						|| resusttyep==50 || resusttyep==51 || resusttyep==52 || resusttyep==59
						|| resusttyep==60 || resusttyep==61 || resusttyep==62 || resusttyep==63
						|| resusttyep==74)
						{
							record.setMonth1num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month1r"))*100,4)+"%");
							record.setMonth2num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month2r"))*100,4)+"%");
							record.setMonth3num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month3r"))*100,4)+"%");
							record.setMonth4num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month4r"))*100,4)+"%");
							record.setMonth5num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month5r"))*100,4)+"%");
							record.setMonth6num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month6r"))*100,4)+"%");
							record.setMonth7num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month7r"))*100,4)+"%");
							record.setMonth8num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month8r"))*100,4)+"%");
							record.setMonth9num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month9r"))*100,4)+"%");
							record.setMonth10num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month10r"))*100,4)+"%");
							record.setMonth11num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month11r"))*100,4)+"%");
							record.setMonth12num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month12r"))*100,4)+"%");
							record.setSummonthnum(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("months_12r"))*100,4)+"%");
							record.setTotalavgbefor(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("monthf_5r"))*100,4)+"%");
							record.setCompavgbymonth(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("montha_12r"))*100,4)+"%");
							record.setTotalavgafter(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("montha_5r"))*100,4)+"%");
						}
						else
						{
							record.setMonth1num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month1r")),0)+"");
							record.setMonth2num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month2r")),0)+"");
							record.setMonth3num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month3r")),0)+"");
							record.setMonth4num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month4r")),0)+"");
							record.setMonth5num(CommonTool.FormatDouble(rs.getDouble("month5r"))+"");
							record.setMonth6num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month6r")),0)+"");
							record.setMonth7num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month7r")),0)+"");
							record.setMonth8num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month8r")),0)+"");
							record.setMonth9num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month9r")),0)+"");
							record.setMonth10num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month10r")),0)+"");
							record.setMonth11num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month11r")),0)+"");
							record.setMonth12num(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("month12r")),0)+"");
							record.setSummonthnum(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("months_12r")),0)+"");
							record.setTotalavgbefor(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("monthf_5r")),0)+"");
							record.setCompavgbymonth(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("montha_12r")),0)+"");
							record.setTotalavgafter(CommonTool.GetGymAmt(CommonTool.FormatDouble(rs.getDouble("montha_5r")),0)+"");
						}
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		List<SC013Bean> ls= (List<SC013Bean>) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return ls;
	}

	
}
