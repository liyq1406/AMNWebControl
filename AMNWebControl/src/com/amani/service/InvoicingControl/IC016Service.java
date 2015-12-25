package com.amani.service.InvoicingControl;

import com.amani.action.AnlyResultSet;
import com.amani.bean.GoodsInserAnalysisBean;
import com.amani.bean.GoodsInserTypeAnalysisBean;
import com.amani.bean.GoodsOuterAnalysisBean;
import com.amani.dao.AMN_DaoImp;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IC016Service extends AMN_ReportService
{
  public List<GoodsOuterAnalysisBean> loadDateSetByCompId(String strCompId, String strFromDate, String strToDate, String strFromGoodsId, String strToGoodsId,String searchGoodsType)
  {
    try
    {
      String strSql = "select b.changegoodsno,c.parentcodevalue,d.goodsname,b.changeunit,a.changedate,outOptionName=g.parentcodevalue,a.changestaffid,e.compname,a.changebillno,b.changecount,b.changeprice,b.changeamt,d.purchaseprice,costamt=isnull(purchaseprice,0)*isnull(changecount,0) " +
      		" from mgoodsstockinfo a,dgoodsstockinfo b,commoninfo c,goodsinfo d,companyinfo e,compchaininfo f, commoninfo g  " +
      		" where a.changecompid=b.changecompid and a.changebillno=b.changebillno and a.changetype=b.changetype " +
      		" and a.changetype=2 and changeflag=2 and a.changecompid='"+strCompId+"' " +
      		" and c.infotype='WPTJ' and c.parentcodekey=d.goodspricetype " +
      		" and g.infotype='CKFS' and g.parentcodekey=a.changeoption " +
      		" and b.changegoodsno=d.goodsno  " +
      		" and a.changestaffid=f.relationcomp and f.curcomp='"+strCompId+"' "+
      		" and a.changedate between '" +    strFromDate + "' and '" + strToDate + "'" + 
      		" and (d.goodspricetype='"+searchGoodsType+"' or '"+searchGoodsType+"'='' )"+
	        " and ( b.changegoodsno between '" + strFromGoodsId + "' and '" + strToGoodsId + "' or '"+strFromGoodsId+"'='' )" + 
	        " and a.changestaffid=e.compno " + 
	        " order by b.changegoodsno,c.parentcodekey";
      AnlyResultSet<List<GoodsOuterAnalysisBean>> analysis = new AnlyResultSet<List<GoodsOuterAnalysisBean>>() {
	  		public List<GoodsOuterAnalysisBean> anlyResultSet(ResultSet rs) {
	  			List<GoodsOuterAnalysisBean> returnValue=new ArrayList();
	  			GoodsOuterAnalysisBean record=null;
	  			try {
	  				while (rs != null && rs.next() == true) {
	  					record=new GoodsOuterAnalysisBean();
	  					record.setStrGoodsId(CommonTool.FormatString(rs.getString("changegoodsno")));
	  					record.setStrGoodsName(CommonTool.FormatString(rs.getString("goodsname")));
	  					record.setStrGoodsUnit(CommonTool.FormatString(rs.getString("changeunit")));
	  					record.setStrOuterDate(CommonTool.getDateMask(rs.getString("changedate")));
	  					record.setStrOutOptionName(CommonTool.FormatString(rs.getString("outOptionName")));
	  					record.setStrGoodsType(CommonTool.FormatString(rs.getString("parentcodevalue")));
	  					record.setStrOuterBillId(CommonTool.FormatString(rs.getString("changebillno")));
	  					record.setStrReciverCompId(CommonTool.FormatString(rs.getString("changestaffid")));
	  					record.setStrReciverCompName(CommonTool.FormatString(rs.getString("compname")));
	  					record.setBOuterCount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changecount")))));
						record.setBOuterPrice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeprice")))));
						record.setBOuterCostPrice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("purchaseprice")))));
						record.setBOuterAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeamt")))));
						record.setBOuterCostAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costamt")))));
						returnValue.add(record);
	  				}
	  			} catch (Exception e) {
	  				e.printStackTrace();
	  				returnValue =null;
	  			}
	  			return returnValue;
	  		}
	  	};
	  List<GoodsOuterAnalysisBean> ls = (List<GoodsOuterAnalysisBean>)this.amn_Dao.executeQuery_ex(strSql, analysis);
  
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
    String querySql = " create table #goodsoutertype_resultx ( compid	varchar(10)	null, compname	varchar(30)	null,insertype	varchar(10)	null,   insergoodsamt	float	null ) " +
    		" insert #goodsoutertype_resultx(compid,compname,insertype,insergoodsamt )  " +
    		" select a.changestaffid,e.compname,c.parentcodekey+'AMT',SUM(ISNULL(b.changeamt,0))  " +
    		" from mgoodsstockinfo a,dgoodsstockinfo b,commoninfo c,goodsnameinfo d , compchaininfo f ,companyinfo e " +
    		" where a.changecompid=b.changecompid and a.changebillno=b.changebillno and a.changetype=b.changetype " +
    		"  and a.changetype=2 and changeflag=2 and a.changestaffid=f.relationcomp and f.curcomp='" + strCompId + "'" +
    		"  and infotype='WPTJ' and c.parentcodekey=d.goodspricetype  and b.changegoodsno=d.goodsno " + 
		      "  and a.changedate between '" + strDateFrom + "' and '" + strDateTo + "' " + 
		      "  and ( b.changegoodsno between '" + strFromGoodsId + "' and '" + strToGoodsId + "' or '"+strFromGoodsId+"'='' ) and a.changestaffid=e.compno " + 
		      "  group by a.changestaffid,e.compname,parentcodekey" + 
		      "  order by a.changestaffid,e.compname,parentcodekey " + 
		      "  declare @sqltitle varchar(600)  " + 
		      "  select @sqltitle = isnull(@sqltitle + '],[' , '') + parentcodekey+'Amt' from commoninfo where infotype='WPTJ' " + 
		      "  set @sqltitle = '[' + @sqltitle + ']' " + 
		      "  exec ('select * from (select * from #goodsoutertype_resultx ) a pivot (max(insergoodsamt) for insertype in (' + @sqltitle + ')) b order by compid') " + 
		      "  drop table #goodsoutertype_resultx ";

    List<GoodsInserTypeAnalysisBean> dataSet = new ArrayList();
    try
    {
      rs = this.amn_Dao.executeQuery(querySql);
      List lsType = getPrjType();
      while ((rs != null) && (rs.next()))
      {
	        GoodsInserTypeAnalysisBean record = new GoodsInserTypeAnalysisBean();
	        record.setStrCompId(rs.getString("compid"));
	        record.setStrCompName(rs.getString("compname"));
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
	}; List ls = (List)this.amn_Dao.executeQuery_ex(strSql, analysis);
    analysis = null;
    return ls;
  }
}