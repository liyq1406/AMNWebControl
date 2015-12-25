package com.amani.service.InvoicingControl;

import com.amani.action.AnlyResultSet;
import com.amani.model.Dreturngoodsinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IC018Service extends AMN_ReportService
{
  public List<Dreturngoodsinfo> loadDateSetByCompId(String strCompId, String strFromDate, String strToDate, String strFromGoodsId, String strToGoodsId)
  {
    try
    {
      String strSql = "select returngoodsno,d.goodsname,returngoodsunit,c.parentcodevalue,returndate,returntype,revicestoreno,warehousename,b.returnbillid,b.returncompid,e.compname," +
      		"  factreturncount, factreturnprice,factreturnamt,d.purchaseprice,costamt=isnull(purchaseprice,0)*isnull(factreturncount,0)  " +
      		"  from mreturngoodsinfo a,commoninfo c,goodsinfo d,companyinfo e,compchaininfo f ,dreturngoodsinfo b " +
      		"   left join compwarehouse x on x.compno='001' and revicestoreno=warehouseno " +
      		"   where a.returncompid=b.returncompid and a.returnbillid=b.returnbillid  " +
      		"	and a.returncompid=f.relationcomp and f.curcomp='"+strCompId+"' " +
      		" 	and a.returndate between '"+strFromDate+"' and '"+strToDate+"'  " +
      		" 	and ( b.returngoodsno between '"+strFromGoodsId+"' and '"+strToGoodsId+"' or '"+strToGoodsId+"'='' )  " +
      		" 	and a.returncompid=e.compno and isnull(returnstate,0)=2  " +
      		" 	and c.infotype='WPTJ' and c.parentcodekey=d.goodspricetype  " +
      		" 	and b.returngoodsno=d.goodsno   " +
      		" 	order by a.returncompid,b.returngoodsno,c.parentcodekey ";
      AnlyResultSet<List<Dreturngoodsinfo>> analysis = new AnlyResultSet<List<Dreturngoodsinfo>>() {
	  		public List<Dreturngoodsinfo> anlyResultSet(ResultSet rs) {
	  			List<Dreturngoodsinfo> returnValue=new ArrayList();
	  			Dreturngoodsinfo record=null;
	  			try {
	  				while (rs != null && rs.next() == true) {
	  					record=new Dreturngoodsinfo();
	  					record.setReturngoodsno(CommonTool.FormatString(rs.getString("returngoodsno")));
	  					record.setReturngoodsname(CommonTool.FormatString(rs.getString("goodsname")));
	  					record.setReturngoodsunit(CommonTool.FormatString(rs.getString("returngoodsunit")));
	  					record.setReturndate(CommonTool.getDateMask(rs.getString("returndate")));
	  					record.setStrGoodsType(CommonTool.FormatString(rs.getString("parentcodevalue")));
	  					if(CommonTool.FormatInteger(rs.getInt("returntype"))==1)
	  						record.setReturntypename("总部仓库");
	  					else if(CommonTool.FormatInteger(rs.getInt("returntype"))==2)
	  						record.setReturntypename("供应商");
	  					else 
	  						record.setReturntypename("未选择");
	  					record.setRevicestoreno(CommonTool.FormatString(rs.getString("warehousename")));
	  					record.setBreturnbillid(CommonTool.FormatString(rs.getString("returnbillid")));
	  					record.setBreturncompid(CommonTool.FormatString(rs.getString("returncompid")));
	  					record.setBreturncompname(CommonTool.FormatString(rs.getString("compname")));
	  					record.setFactreturncount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("factreturncount")))));
						record.setFactreturnprice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("factreturnprice")))));
						record.setFactreturnamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("factreturnamt")))));
						record.setCostreturnprice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("purchaseprice")))));
						record.setCostreturnamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costamt")))));
						returnValue.add(record);
	  				}
	  			} catch (Exception e) {
	  				e.printStackTrace();
	  				returnValue =null;
	  			}
	  			return returnValue;
	  		}
	  	};
	  List<Dreturngoodsinfo> ls = (List<Dreturngoodsinfo>)this.amn_Dao.executeQuery_ex(strSql, analysis);
  
      analysis = null;
      return ls;
    }
    catch (Exception ex)
    {
      ex.printStackTrace(); }
    return null;
  }
}