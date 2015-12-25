package com.amani.service.SupplierControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Dgoodssendinfo;
import com.amani.model.Mgoodssendinfo;
import com.amani.model.MgoodssendinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;

/**
 * 供应商发货
 */
@Service
public class SUC002Service extends AMN_ModuleService{
	
	

	@Override
	protected boolean deleteDetail(Object curMaster) {
		return false;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		return false;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		return false;
	}

	@Override
	protected boolean postDetail(Object details) {
		return false;
	}

	//发货单主档信息
	@Override
	public List<?> loadMasterDataSet(int pageSize, int startRow) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Mgoodssendinfo> loadDataSet(String supplierno) {
		String strSql="select sendcompid,sendbillid,headwareid,orderdate,ordertime,"
				+ "ordercompid,orderbill,orderbilltype,storestaffid,compaddress,bilinginfo,compname,compphone " +
				"from mgoodssendinfo,companyinfo where ordercompid = compno and sendstate=0 and headwareid='"+ supplierno +"'";
		try {
			AnlyResultSet<List<Mgoodssendinfo>> analysis = new AnlyResultSet<List<Mgoodssendinfo>>()
			{
				public List<Mgoodssendinfo> anlyResultSet(ResultSet rs)
				{
					List<Mgoodssendinfo> returnValue = new ArrayList<Mgoodssendinfo>();
					try
					{
						while(rs != null && rs.next())
						{
							Mgoodssendinfo record = new Mgoodssendinfo();
							record.setId(new MgoodssendinfoId(CommonTool.FormatString(rs.getString("sendcompid")),CommonTool.FormatString(rs.getString("sendbillid"))));
							record.setBsendcompid(CommonTool.FormatString(rs.getString("sendcompid")));
							record.setBsendbillid(CommonTool.FormatString(rs.getString("sendbillid")));
							record.setOrderdate(CommonTool.getDateMask(rs.getString("orderdate")));
							record.setOrdertime(CommonTool.getTimeMask(rs.getString("ordertime"),1));
							record.setHeadwareid(CommonTool.FormatString(rs.getString("headwareid")));
							record.setOrdercompid(CommonTool.FormatString(rs.getString("ordercompid")));
							record.setOrderbill(CommonTool.FormatString(rs.getString("orderbill")));
							record.setOrderbilltype(CommonTool.FormatInteger(rs.getInt("orderbilltype")));
							record.setStorestaffid(CommonTool.FormatString(rs.getString("storestaffid")));
							record.setStoreaddress(CommonTool.FormatString(rs.getString("compaddress")));
							record.setOrdercompname(CommonTool.FormatString(rs.getString("bilinginfo")));
							record.setBbsendcompname(CommonTool.FormatString(rs.getString("compname")));
							record.setStorewarename(CommonTool.FormatString(rs.getString("compphone")));
							returnValue.add(record);
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  null;
					}
					return returnValue;
				}
			};
			List<Mgoodssendinfo> list= (List<Mgoodssendinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//发货单明细
	@SuppressWarnings("unchecked")
	public List<Dgoodssendinfo> loadDetailSet(String sendcompid, String sendbillid) {
		try
		 {
			 String strSql="select sendgoodsno,goodsname,sendgoodsunit,ordergoodscount,downordercount,sendgoodscount,sendgoodprice from dgoodssendinfo,goodsnameinfo "
			 		+ "where sendgoodsno=goodsno and sendcompid='"+ sendcompid +"' and sendbillid = '"+sendbillid+"' order by sendseqno";
			 AnlyResultSet<List<Dgoodssendinfo>> analysis = new AnlyResultSet<List<Dgoodssendinfo>>()
				{
					public List<Dgoodssendinfo> anlyResultSet(ResultSet rs)
					{
						List<Dgoodssendinfo> returnValue = new ArrayList<Dgoodssendinfo>();
						Dgoodssendinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodssendinfo();
								record.setSendgoodsno(CommonTool.FormatString(rs.getString("sendgoodsno")));
								record.setSendgoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setSendgoodsunit(CommonTool.FormatString(rs.getString("sendgoodsunit")));
								record.setOrdergoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodscount"))));
								record.setDownordercount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("downordercount"))));
								Object object = rs.getObject("sendgoodscount");
								if(object!=null)
									record.setSendgoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodscount"))));
								record.setSendgoodprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodprice"))));
								returnValue.add(record);
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							returnValue =  null;
						}
						return returnValue;
					}
				};
				List<Dgoodssendinfo> ls= (List<Dgoodssendinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return ls;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	}

	/**
	 * 发货
	 * @param mgoodssendinfo
	 * @param dgoods 
	 * @return
	 */
	public boolean delivery(Mgoodssendinfo mgoodssendinfo, List<Dgoodssendinfo> dgoods) {
		try{
			StringBuffer sql = new StringBuffer();
			//生成收货主档
			sql.append("insert into mgoodsreceipt(receiptcompid,receiptbillid,receiptwareid,receiptsendbillid,receiptorderbillid,orderbilltype,receiptstate)")
			.append(" values('"+CommonTool.FormatString(mgoodssendinfo.getBsendcompid())+"',")
			.append(" '"+CommonTool.FormatString(mgoodssendinfo.getBsendbillid()+"R")+"',")
			.append(" '"+CommonTool.FormatString(mgoodssendinfo.getStorewareid())+"',")
			.append(" '"+CommonTool.FormatString(mgoodssendinfo.getBsendbillid())+"',")
			.append(" '"+CommonTool.FormatString(mgoodssendinfo.getOrderbill())+"',")
			.append(" "+CommonTool.FormatInteger(mgoodssendinfo.getOrderbilltype())+",0)")
			//更新订单主档
			.append(" update mgoodsorderinfo set orderstate=3 where")
			.append(" ordercompid='"+mgoodssendinfo.getOrdercompid()+"' and orderbillid='"+mgoodssendinfo.getOrderbill()+"' ")
			//更新发货主档表
			.append(" update mgoodssendinfo set senddate='"+ mgoodssendinfo.getSenddate() +"', sendtime='"+ mgoodssendinfo.getSendtime() +"', ")
			.append(" sendstaffid='"+ mgoodssendinfo.getSendstaffid() +"', sendstate=1, sendopationerid='"+ mgoodssendinfo.getSendopationerid() +"', ")
			.append(" sendopationdate='"+ mgoodssendinfo.getSendopationdate() +"'")
			.append(" where sendcompid ='"+ mgoodssendinfo.getBsendcompid() +"' and sendbillid = '"+mgoodssendinfo.getBsendbillid()+"'");
			int i = 0;
			//生成收货明细和更新订单明细
			for (Dgoodssendinfo dgoodssendinfo : dgoods) {
				sql.append("insert into dgoodsreceipt(receiptcompid,receiptbillid,receiptseqno,receiptgoodsno,receiptgoodsunit,receiptprice,sendgoodscount,ordergoodscount)")
				.append(" values('"+CommonTool.FormatString(mgoodssendinfo.getBsendcompid())+"',")
				.append(" '"+CommonTool.FormatString(mgoodssendinfo.getBsendbillid()+"R")+"',")
				.append(" "+(i++)+",")
				.append(" '"+CommonTool.FormatString(dgoodssendinfo.getSendgoodsno())+"',")
				.append(" '"+CommonTool.FormatString(dgoodssendinfo.getSendgoodsunit())+"',")
				.append(" "+CommonTool.FormatBigDecimal(dgoodssendinfo.getSendgoodprice())+" ,")
				.append(" "+CommonTool.FormatBigDecimal(dgoodssendinfo.getSendgoodscount())+" ,")
				.append(" '"+CommonTool.FormatBigDecimal(dgoodssendinfo.getOrdergoodscount())+"' )")
				.append(" update dgoodsorderinfo set ordergoodstype=3 where ordercompid='"+mgoodssendinfo.getOrdercompid()+"'")
				.append(" and orderbillid='"+mgoodssendinfo.getOrderbill()+"' and ordergoodsno='"+ dgoodssendinfo.getSendgoodsno()+"' ");
			}
			return amn_Dao.executeSql(sql.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 作废
	 * @param ordercompid
	 * @param orderbillid
	 * @return
	 */
	public boolean cancel(String sendcompid, String sendbillid) {
		String sql = "update mgoodssendinfo set sendstate = 2 where sendcompid ='"+sendcompid+"' and sendbillid = '"+sendbillid+"'";
		return amn_Dao.executeSql(sql);
	}
	
}