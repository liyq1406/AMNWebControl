package com.amani.service.SellReportControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.bean.SC017Bean;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;

@Service
public class SC017Service extends AMN_ReportService{
	public List<SC017Bean> loadSC017Data(String strCompId,String strFromDate,String strToDate)
	{
		String strSql=" exec upg_year_target '"+strCompId+"','"+strFromDate+"','"+strToDate+"' ";
		ResultSet rs=null;
		List<SC017Bean> lsBeans=new ArrayList<SC017Bean>();
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					SC017Bean sc017Bean=new SC017Bean();
					sc017Bean.setStrType(rs.getString("ntype"));
					sc017Bean.setStrTarget(rs.getString("targets"));
					sc017Bean.setJan(rs.getDouble("jan"));
					sc017Bean.setFeb(CommonTool.FormatDouble(rs.getDouble("targets"))-CommonTool.FormatDouble(rs.getDouble("jan")));
					sc017Bean.setMar(rs.getDouble("mar"));
					sc017Bean.setApr(rs.getDouble("apr"));
					sc017Bean.setMay(rs.getDouble("may"));
					sc017Bean.setJun(rs.getDouble("jun"));
					sc017Bean.setJul(rs.getDouble("jul"));
					sc017Bean.setAug(rs.getDouble("aug"));
					sc017Bean.setSep(rs.getDouble("sep"));
					sc017Bean.setOct(rs.getDouble("oct"));
					sc017Bean.setNov(rs.getDouble("nov"));
					sc017Bean.setDec(rs.getDouble("dec"));
					lsBeans.add(sc017Bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lsBeans;
	}
}
