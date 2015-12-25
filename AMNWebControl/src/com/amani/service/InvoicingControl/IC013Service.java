package com.amani.service.InvoicingControl;

import com.amani.action.AnlyResultSet;
import com.amani.bean.GoodsInserAnalysisBean;
import com.amani.bean.GoodsInserTypeAnalysisBean;
import com.amani.dao.AMN_DaoImp;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IC013Service extends AMN_ReportService
{
  public List<GoodsInserAnalysisBean> loadDateSetByCompId(String strCompId, String strFromDate, String strToDate, String strFromGoodsId, String strToGoodsId)
  {
    try
    {
      String strSql = "select b.changegoodsno,c.parentcodevalue,d.goodsname,b.changeunit,a.changedate,inserOption=g.parentcodevalue,a.changebillno,b.changecount,b.changeprice,b.changeamt " +
      		" from mgoodsstockinfo a,dgoodsstockinfo b,commoninfo c,commoninfo g,goodsnameinfo d  where a.changecompid=b.changecompid and a.changebillno=b.changebillno and a.changetype=b.changetype and a.changetype=1 " +
      		" and a.changecompid='"+strCompId + "' and c.infotype='WPTJ' and c.parentcodekey=d.goodspricetype " +
      		" and g.infotype='RKFS' and g.parentcodekey=a.changeoption" + 
      		" and b.changegoodsno=d.goodsno  and a.changedate between '" + strFromDate + "' and '" + strToDate + "' " + 
      		" and ( b.changegoodsno between '" + strFromGoodsId + "' and '" + strToGoodsId + "' or '" + strFromGoodsId + "'='' )" + 
      		" order by b.changegoodsno,c.parentcodekey";
	      AnlyResultSet<List<GoodsInserAnalysisBean>> analysis = new AnlyResultSet<List<GoodsInserAnalysisBean>>() {
	  		public List<GoodsInserAnalysisBean> anlyResultSet(ResultSet rs) {
	  			List<GoodsInserAnalysisBean> returnValue=new ArrayList();
	  			GoodsInserAnalysisBean record=null;
	  			try {
	  				while (rs != null && rs.next() == true) {
	  					record=new GoodsInserAnalysisBean();
	  					record.setStrGoodsId(CommonTool.FormatString(rs.getString("changegoodsno")));
	  					record.setStrGoodsName(CommonTool.FormatString(rs.getString("goodsname")));
	  					record.setStrGoodsUnit(CommonTool.FormatString(rs.getString("changeunit")));
	  					record.setStrInserDate(CommonTool.getDateMask(rs.getString("changedate")));
	  					record.setStrGoodsType(CommonTool.FormatString(rs.getString("parentcodevalue")));
	  					record.setStrInserBillId(CommonTool.FormatString(rs.getString("changebillno")));
	  					record.setStrInserOptionName(CommonTool.FormatString(rs.getString("inserOption")));
	  					record.setBInserCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changecount")))));
						record.setBInserPrice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeprice")))));
						record.setBInserAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeamt")))));
						returnValue.add(record);
	  				}
	  			} catch (Exception e) {
	  				e.printStackTrace();
	  				returnValue =null;
	  			}
	  			return returnValue;
	  		}
	  	};
	  List<GoodsInserAnalysisBean> ls = (List<GoodsInserAnalysisBean>)this.amn_Dao.executeQuery_ex(strSql, analysis);
      analysis = null;
      return ls;
    }
    catch (Exception ex)
    {
      ex.printStackTrace(); }
    return null;
  }

  public List<GoodsInserTypeAnalysisBean> loadTypeDateSetByCompId(String strCompId, String strDateFrom, String strDateTo, String strFromGoodsId, String strToGoodsId)
  {
    ResultSet rs = null;
    String querySql = " create table #goodsinsertype_resultx ( compid	varchar(10)	null,inserdate	varchar(10)	null,insertype	varchar(10)	null,   insergoodsamt	float	null ) insert #goodsinsertype_resultx(compid,inserdate,insertype,insergoodsamt )  select b.changecompid,a.changedate,c.parentcodekey+'AMT',SUM(ISNULL(b.changeamt,0))  from mgoodsstockinfo a,dgoodsstockinfo b,commoninfo c,goodsnameinfo d  where a.changecompid=b.changecompid and a.changebillno=b.changebillno and a.changetype=b.changetype and a.changetype=1   " +
    		" and a.changecompid='" +  strCompId + "' and infotype='WPTJ' and c.parentcodekey=d.goodspricetype  and b.changegoodsno=d.goodsno " + 
	      "  and a.changedate between '" + strDateFrom + "' and '" + strDateTo + "' " + 
	      "  and ( b.changegoodsno between '" + strFromGoodsId + "' and '" + strToGoodsId + "' or ''='' ) " + 
	      "  group by b.changecompid,changedate,parentcodekey" + 
	      "  order by b.changecompid,changedate,parentcodekey " + 
	      "  declare @sqltitle varchar(600)  " + 
	      "  select @sqltitle = isnull(@sqltitle + '],[' , '') + parentcodekey+'Amt' from commoninfo where infotype='WPTJ' " + 
	      "  set @sqltitle = '[' + @sqltitle + ']' " + 
	      "  exec ('select * from (select * from #goodsinsertype_resultx ) a pivot (max(insergoodsamt) for insertype in (' + @sqltitle + ')) b order by compid') " + 
	      "  drop table #goodsinsertype_resultx ";

    List<GoodsInserTypeAnalysisBean> dataSet = new ArrayList();
    try
    {
      rs = this.amn_Dao.executeQuery(querySql);
      List lsType = getPrjType();
      while ((rs != null) && (rs.next()))
      {
    	    GoodsInserTypeAnalysisBean record = new GoodsInserTypeAnalysisBean();
	        record.setStrCompId(rs.getString("compid"));
	        record.setStrInserDate(CommonTool.getDateMask(rs.getString("inserdate")));
	        if ((lsType != null) && (lsType.size() > 0))
	        {
	          double[][] prjtypes = new double[lsType.size()][1];
	          String strFile = "";
	          for (int i = 0; i < lsType.size(); ++i)
	          {
	            strFile = lsType.get(i) + "Amt";
	            prjtypes[i][0] = CommonTool.FormatDouble(Double.valueOf(rs.getDouble(strFile))).doubleValue();
	          }
	          record.setGoodsInserTypesAmt(prjtypes);
	        }
	        dataSet.add(record);
      }
      if ((dataSet == null) || (dataSet.size() < 1)) {
        return null;
      }
      return dataSet;
    }
    catch (Exception ex)
    {
    		ex.printStackTrace();
    		return null;
    }
    finally
    {
      try
      {
        rs.close();
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
}