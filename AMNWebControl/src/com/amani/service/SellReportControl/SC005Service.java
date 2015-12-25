package com.amani.service.SellReportControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.Compclasstraderesult;



import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC005Service extends AMN_ReportService{
	
	public List<Compclasstraderesult> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo)
	{
		try
		{
			
			String strSql=" select compid,compname," +
			" beautyeji=sum(isnull(beautyeji,0)),hairyeji=sum(isnull(hairyeji,0)), " +
			" footyeji=sum(isnull(footyeji,0)),fingeryeji=sum(isnull(fingeryeji,0))," +
			" totalyeji=sum(isnull(totalyeji,0)),realbeautyeji=sum(isnull(realbeautyeji,0))," +
			" realhairyeji=sum(isnull(realhairyeji,0)),realfootyeji=sum(isnull(realfootyeji,0))," +
			" realfingeryeji=sum(isnull(realfingeryeji,0)),realtotalyeji=sum(isnull(realtotalyeji,0))" +
			" from compclasstraderesult a,companyinfo  b,compchaininfo c" +
			" where curcomp='"+strCompId+"' and relationcomp=compid and compid=compno" +
			"   and ddate between '"+CommonTool.setDateMask(strDateFrom)+"' and  '"+CommonTool.setDateMask(strDateTo)+"' " +
			" group by compid,compname ";
	
			AnlyResultSet<List<Compclasstraderesult>> analysis = new AnlyResultSet<List<Compclasstraderesult>>() {
				public List<Compclasstraderesult> anlyResultSet(ResultSet rs) {
					List<Compclasstraderesult> returnValue = new ArrayList();
					Compclasstraderesult record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Compclasstraderesult();
							record.setCompid(CommonTool.FormatString(rs.getString("compid")));
							record.setCompname(CommonTool.FormatString(rs.getString("compname")));
							record.setBeautyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beautyeji")))));
							record.setHairyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("hairyeji")))));
							record.setFootyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("footyeji")))));
							record.setFingeryeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fingeryeji")))));
							record.setTotalyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalyeji")))));
							record.setRealbeautyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("realbeautyeji")))));
							record.setRealhairyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("realhairyeji")))));
							record.setRealfootyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("realfootyeji")))));
							record.setRealfingeryeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("realfingeryeji")))));
							record.setRealtotalyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("realtotalyeji")))));
							record.setTotalyejitext(record.getTotalyeji().toString());
							record.setRealtotalyejitext(record.getRealtotalyeji().toString());
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			
			List<Compclasstraderesult> ls= (List<Compclasstraderesult>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public Compclasstraderesult loadCurDateSetByCompId(String strCompId,String strDateFrom,String strDateTo)
	{
		try
		{
			
			String strSql=" select  beautyeji=sum(isnull(beautyeji,0)),hairyeji=sum(isnull(hairyeji,0)), " +
					" footyeji=sum(isnull(footyeji,0)),fingeryeji=sum(isnull(fingeryeji,0))," +
					" totalyeji=sum(isnull(totalyeji,0)),realbeautyeji=sum(isnull(realbeautyeji,0))," +
					" realhairyeji=sum(isnull(realhairyeji,0)),realfootyeji=sum(isnull(realfootyeji,0))," +
					" realfingeryeji=sum(isnull(realfingeryeji,0)),realtotalyeji=sum(isnull(realtotalyeji,0))" +
					" from compclasstraderesult a,companyinfo  b,compchaininfo c" +
					" where curcomp='"+strCompId+"' and relationcomp=compid and compid=compno" +
					"   and ddate between '"+CommonTool.setDateMask(strDateFrom)+"' and  '"+CommonTool.setDateMask(strDateTo)+"' " +
					"  ";
			
			AnlyResultSet<Compclasstraderesult> analysis = new AnlyResultSet<Compclasstraderesult>() {
				public Compclasstraderesult anlyResultSet(ResultSet rs) {
				
					Compclasstraderesult record=null;
					try {
						if (rs != null && rs.next() == true) {
							record=new Compclasstraderesult();
							record.setBeautyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beautyeji")))));
							record.setHairyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("hairyeji")))));
							record.setFootyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("footyeji")))));
							record.setFingeryeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fingeryeji")))));
							record.setTotalyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("totalyeji")))));
							record.setRealbeautyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("realbeautyeji")))));
							record.setRealhairyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("realhairyeji")))));
							record.setRealfootyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("realfootyeji")))));
							record.setRealfingeryeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("realfingeryeji")))));
							record.setRealtotalyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("realtotalyeji")))));
						
						}
					} catch (Exception e) {
						e.printStackTrace();
						record =null;
					}
					return record;
				}
			};
			
			Compclasstraderesult record= (Compclasstraderesult) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List loadSeqNoByComp(String strCompid,String strDateFrom,String strDateTo)
	{

		 String butySeqno="0";//美容排名
		 String hairSeqno="0";//美发排名
		 String trhSeqno="0";//烫染护排名
		 String fingerSeqno="0";//美甲排名
		 String totalSeqno="0";//总排名
		 List lsSeqno=new ArrayList();
	
		 String strSql=" select compid,beaut_yeji_seqno,hair_yeji_seqno,foot_yeji_seqno,finger_yeji_seqno,total_yeji_seqno from " +
		 		" (select compid,Row_Number() over(ORDER BY beaut_yeji desc) as beaut_yeji_seqno , " +
		 		" Row_Number() over(ORDER BY hair_yeji desc) as hair_yeji_seqno ," +
		 		"  Row_Number() over(ORDER BY foot_yeji desc) as foot_yeji_seqno ," +
		 		"  Row_Number() over(ORDER BY finger_yeji desc) as finger_yeji_seqno ," +
		 		"  Row_Number() over(ORDER BY total_yeji desc) as total_yeji_seqno " +
		 		"  from (select compid," +
		 		"	beaut_yeji=SUM(ISNULL(beautyeji,0))," +
		 		"	hair_yeji=SUM(ISNULL(hairyeji,0))," +
		 		"	foot_yeji=SUM(ISNULL(footyeji,0))," +
		 		"	finger_yeji=SUM(ISNULL(fingeryeji,0))," +
		 		"	total_yeji=SUM(ISNULL(totalyeji,0))" +
		 		" from compclasstraderesult " +
		 		" where ddate between '"+CommonTool.setDateMask(strDateFrom)+"' and '"+CommonTool.setDateMask(strDateTo)+"'" +
		 		" group by compid) as comp_sum ) as comp_seqno where compid='"+strCompid+"' " ;
		 ResultSet rs = null;
			
		try{
			rs = amn_Dao.executeQuery(strSql);	
			if(rs!=null && rs.next())
			{
				butySeqno=CommonTool.FormatInteger(rs.getInt("beaut_yeji_seqno"))+"";
				hairSeqno=CommonTool.FormatInteger(rs.getInt("hair_yeji_seqno"))+"";
				trhSeqno=CommonTool.FormatInteger(rs.getInt("foot_yeji_seqno"))+"";
				fingerSeqno=CommonTool.FormatInteger(rs.getInt("finger_yeji_seqno"))+"";
				totalSeqno=CommonTool.FormatInteger(rs.getInt("total_yeji_seqno"))+"";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally{
			try{
				if(rs!=null)
				{
					((CachedRowSet)rs).release();
					rs.close();
				}				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		 lsSeqno.add(butySeqno);
		 lsSeqno.add(hairSeqno);
		 lsSeqno.add(trhSeqno);
		 lsSeqno.add(fingerSeqno);
		 lsSeqno.add(totalSeqno);
		 return lsSeqno;
	}

}
