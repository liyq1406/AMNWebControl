package com.amani.service.PersonnelControl;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.ObjectUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.StaffinfoAnalysis;
import com.amani.service.AMN_ReportService;
@Service
public class PC015Service extends AMN_ReportService{
	
	public List<StaffinfoAnalysis> loadDateSetByCompId(String proc, String strCompId,int searchType,String strDateFrom,String strDateTo,int procType)
	{
			ResultSet rs=null;
			DecimalFormat ndf = new DecimalFormat("#");
			DecimalFormat df = new DecimalFormat("#.##%");
			String querySql="exec "+ proc +" '"+strCompId+"',"+searchType+",'"+strDateFrom+"','"+strDateTo+"' ";
			List<StaffinfoAnalysis> dataSet=new ArrayList<StaffinfoAnalysis>(); 
			try
			{
				rs=this.amn_Dao.executeQuery(querySql);
				List lsType=this.loadPrjType();
				while(rs!=null && rs.next())
				{
					StaffinfoAnalysis record=new StaffinfoAnalysis();
					record.setStrCompId(rs.getString("compid"));
					record.setStrCompName(rs.getString("compname"));
					if(lsType != null && lsType.size()>0)
					{
						String prjtypes[][]=new String[lsType.size()][1];
						String strFile="";
						for(int i=0;i<lsType.size();i++)
						{
							strFile=lsType.get(i)+"Count";
							double count = rs.getDouble(strFile);
							prjtypes[i][0]=(procType==0?ndf.format(count):df.format(count/100));
						}
						record.setPrjTypesAmt(prjtypes);
					}
					double totalCount = rs.getDouble("totalcount");
					record.setTotalCount(procType==0?ndf.format(totalCount):df.format(totalCount/100));
					if(procType==1){
						record.setStrDate(rs.getString("dqny"));
					}else{
						record.setStrDate("");
					}
					dataSet.add(record);
				}
				if(dataSet==null || dataSet.size()<1)
				{
					return null;
				}
				
				return dataSet;
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			finally
			{
				try
				{
					if(rs!=null)
						rs.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		
	}
	
	public List loadPrjType()
	{
		String strSql="select parentcodekey from commoninfo with(NOLOCK) where  infotype='GZGW' and useflag=1 order by parentcodekey ";
		AnlyResultSet<List> analysis = new AnlyResultSet<List>() {
			public List anlyResultSet(ResultSet rs) {
				List returnValue=new ArrayList();
				try {
					while (rs != null && rs.next() == true) {
						
						returnValue.add(rs.getString("parentcodekey"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		List ls= (List) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return ls;		
	}
	
	public List  loadPrjTypeName()
	{
		String strSql="select parentcodevalue from commoninfo with(NOLOCK) where  infotype='GZGW' and useflag=1 order by parentcodekey ";
		AnlyResultSet<List> analysis = new AnlyResultSet<List>() {
			public List anlyResultSet(ResultSet rs) {
				List returnValue=new ArrayList();
				try {
					while (rs != null && rs.next() == true) {
						
						returnValue.add(rs.getString("parentcodevalue"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		List ls= (List) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return ls;		
	}
}
