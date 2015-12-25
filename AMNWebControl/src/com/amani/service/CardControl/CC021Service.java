package com.amani.service.CardControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amani.action.AnlyResultSet;
import com.amani.model.Dconsumeinfo;
import com.amani.model.Dpayinfo;
import com.amani.model.Goodsinfo;
import com.amani.model.Mconsumeinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class CC021Service extends AMN_ReportService{
	/**
	 * 加载订单编号
	 * @param strCompId
	 * @return
	 */
	public String loadBill(String strCompId){
		return this.dataTool.loadBillIdByRule(strCompId,"mconsumeinfo", "csbillid", "SP007");
	}
	
	/**
	 * 提交订单
	 * @param mconsumeinfo
	 * @param dconsumeinfo
	 * @param dpayinfo
	 * @param csinfotype
	 */
	@Transactional
	public void post(Mconsumeinfo mconsumeinfo, List<Dconsumeinfo> dconsumeinfo, List<Dpayinfo> dpayinfo, int csinfotype){
		try{
			if(csinfotype==2){//产品
				String sql="";
				for (Dconsumeinfo dconsume : dconsumeinfo) {
					sql+=" insert dgoodsstockinfo(changecompid,changetype,changebillno,changeseqno,changegoodsno," +
							"standunit,standcount,standprice,producedate,changeunit,changecount,changeprice,changeamt)" +
							" values('"+CommonTool.FormatString(mconsumeinfo.getId().getCscompid())+"','2'," +
							" '"+CommonTool.FormatString(mconsumeinfo.getId().getCsbillid())+"'," +
							" "+CommonTool.FormatDouble(dconsume.getId().getCsseqno())+"," +
							" '"+CommonTool.FormatString(dconsume.getCsitemno())+"'," +
							" '"+CommonTool.FormatString(dconsume.getCsitemunit())+"'," +
							" "+CommonTool.FormatBigDecimal(dconsume.getCsitemcount())+" ," +
							" "+CommonTool.FormatBigDecimal(dconsume.getCsdisprice())+" ," +
							" '"+CommonTool.getCurrDate()+"'," +
							" '"+CommonTool.FormatString(dconsume.getCsitemunit())+"'," +
							" "+CommonTool.FormatBigDecimal(dconsume.getCsitemcount())+" ," +
							" "+CommonTool.FormatBigDecimal(dconsume.getCsdisprice())+" ," +
							" "+CommonTool.FormatBigDecimal(dconsume.getCsitemamt()) +" ) "+
							" update dgoodsbarinfo set barnostate=2 ,costdate='"+CommonTool.getCurrDate()+"',costbillo='"+
							mconsumeinfo.getId().getCsbillid()+"',coststore='"+mconsumeinfo.getId().getCscompid()+
							"' where goodsbarno='"+CommonTool.FormatString(dconsume.getGoodsbarno())+"' ";
				}
				sql+=" insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)" +
						" values('"+CommonTool.FormatString(mconsumeinfo.getId().getCscompid())+"','2', " +
						" '"+CommonTool.FormatString(mconsumeinfo.getId().getCsbillid())+"'," +
						" '"+CommonTool.FormatString(mconsumeinfo.getCsdate())+"'," +
						" '"+CommonTool.FormatString(mconsumeinfo.getCsendtime())+"'," +
						" '"+CommonTool.FormatString(CommonTool.FormatString(this.getDataTool().loadSysParam(mconsumeinfo.getId().getCscompid(), "SP013")))+"'," +
						" "+CommonTool.FormatInteger(1)+"," +
						" '"+CommonTool.FormatString(CommonTool.getLoginInfo("USERID"))+"',1) ";
				this.amn_Dao.executeSql(sql);
			}
			this.amn_Dao.save(mconsumeinfo);
			this.amn_Dao.saveOrUpdateAll(dconsumeinfo);
			this.amn_Dao.saveOrUpdateAll(dpayinfo);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * 根据条码加载产品信息
	 * @param goodsbarno
	 * @return
	 */
	public Goodsinfo loadBarByGoods(String goodsbarno){
		try{	
			String sql = "select a.goodsno, a.goodsbarno,a.barnostate,b.goodsname,b.storesalseprice,b.goodsusetype " +
					"from dgoodsbarinfo a, goodsinfo b where a.goodsno=b.goodsno and a.goodsbarno='"+goodsbarno+"'";
			AnlyResultSet<Goodsinfo> analysis = new AnlyResultSet<Goodsinfo>() {
				public Goodsinfo anlyResultSet(ResultSet rs) {
					Goodsinfo record=null;
					try {
						if (rs.next()) {
							record=new Goodsinfo();
							record.setBgoodsno(ObjectUtils.toString(rs.getString("goodsno")));
							record.setGoodsbarno(ObjectUtils.toString(rs.getString("goodsbarno")));
							record.setUseflag(CommonTool.FormatInteger(rs.getInt("barnostate")));
							record.setGoodsname(ObjectUtils.toString(rs.getString("goodsname")));
							record.setStoresalseprice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("storesalseprice")))));
							record.setGoodsusetype(CommonTool.FormatInteger(rs.getInt("goodsusetype")));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return record;
				}
			};
			Goodsinfo ls=  (Goodsinfo) this.amn_Dao.executeQuery_ex(sql, analysis);
			analysis=null;
			return ls;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
