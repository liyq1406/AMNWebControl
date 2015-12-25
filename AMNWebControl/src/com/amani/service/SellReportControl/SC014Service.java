package com.amani.service.SellReportControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.GoodsInserTypeAnalysisBean;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC014Service extends AMN_ReportService{
	
	


	public List<GoodsInserTypeAnalysisBean> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo)
	{
			ResultSet rs=null;
			ResultSet rsResultSet=null;
			String querySql="select a.cscompid,goodspricetype,sum(csitemamt) as csitemamt from dconsumeinfo a,mconsumeinfo b,goodsinfo,compchaininfo where " +
					" csinfotype=2 " +
					" and csitemno=goodsno " +
					" and relationcomp=a.cscompid" +
					" and curcomp='"+strCompId+"' " +
					" and a.csbillid=b.csbillid " +
					" and a.cscompid=b.cscompid " +
					" and financedate between '"+strDateFrom+"' and '"+strDateTo+"'" +
					" group by a.cscompid,goodspricetype " +
					" order by cscompid,goodspricetype";
			List<GoodsInserTypeAnalysisBean> dataSet=new ArrayList<GoodsInserTypeAnalysisBean>(); 
			try
			{
				rs=this.amn_Dao.executeQuery(querySql);
				List lsType=this.getPrjType();
				GoodsInserTypeAnalysisBean record = null;
				Map compidMap = new HashMap();
				while(rs!=null && rs.next()){
					String compid = CommonTool.FormatString(rs.getString("cscompid"));//门店编号
					String goodspricetype = CommonTool.FormatString(rs.getString("goodspricetype"));//产品类别
					double csitemamt = CommonTool.FormatDouble(rs.getDouble("csitemamt"));//金额
					if(compidMap.containsKey(compid)){
						Map goodspricetypeMap = (Map) compidMap.get(compid);
						goodspricetypeMap.put(goodspricetype, csitemamt);
						compidMap.put(compid, goodspricetypeMap);
					}else{
						Map goodspricetypeMap = new HashMap();
						goodspricetypeMap.put(goodspricetype, csitemamt);
						compidMap.put(compid, goodspricetypeMap);
					}
				}
				
			    Iterator iterator=compidMap.keySet().iterator();
			    while(iterator.hasNext()){
			    	record=new GoodsInserTypeAnalysisBean();
			    	String compid=iterator.next().toString();
			    	Map goodspricetypeMap=(Map) compidMap.get(compid);
					if(lsType != null && lsType.size()>0)
					{
						double goodsInserTypesAmt[][]=new double[lsType.size()][1];
						//此处如果放到联合查询中慢，所以单独查
						String compname = "";
						String sql = "select compname from companyinfo where compno="+compid;
						rsResultSet=this.amn_Dao.executeQuery(sql);
						while(rsResultSet!=null && rsResultSet.next()){
							compname = CommonTool.FormatString(rsResultSet.getString("compname"));//门店名称
						}
						record.setStrCompId(compid);
						record.setStrCompName(compname);
						String strFile="";
						for(int i=0;i<lsType.size();i++)
						{
							strFile=lsType.get(i)+"";
							double csitemamt = CommonTool.FormatDouble(goodspricetypeMap.get(strFile));//金额
							goodsInserTypesAmt[i][0]=(csitemamt);
						}
						record.setGoodsInserTypesAmt(goodsInserTypesAmt);
						dataSet.add(record);
					}
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
					rs.close();
					if(rsResultSet != null){
						rsResultSet.close();
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		
	}
	  public List getPrjType()
	  {
	    String strSql = "select parentcodekey from commoninfo with(NOLOCK) where  infotype='WPTJ' order by parentcodekey ";
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
	    List ls = (List)this.amn_Dao.executeQuery_ex(strSql, analysis);
	    analysis = null;
	    return ls;
	  }
	
	  public List   loadPrjType()
		{
			String strSql="select parentcodekey from commoninfo with(NOLOCK) where  infotype='WPTJ' order by parentcodekey ";
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
			String strSql="select parentcodevalue from commoninfo with(NOLOCK) where  infotype='WPTJ' order by parentcodekey ";
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
