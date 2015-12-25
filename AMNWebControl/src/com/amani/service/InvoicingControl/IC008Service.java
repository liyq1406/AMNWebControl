package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dgoodsorderinfo;
import com.amani.model.DgoodsorderinfoId;
import com.amani.model.Mgoodsorderinfo;
import com.amani.model.MgoodsorderinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class IC008Service extends AMN_ModuleService{

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		try
		{
			Mgoodsorderinfo record=(Mgoodsorderinfo)curMaster;
			if(record!=null && record.getId()!=null)
			{
				String strSql=" update mgoodsorderinfo set invalid=1 where ordercompid='"+record.getId().getOrdercompid()+"' and orderbillid='"+record.getId().getOrderbillid()+"' ";
				return this.amn_Dao.deleteBySql(strSql);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		try
		{
			List<Dgoodsorderinfo> lsDgoodsorderinfo=(List<Dgoodsorderinfo>)details;
			if(lsDgoodsorderinfo!=null && lsDgoodsorderinfo.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsorderinfo);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postInfo(Mgoodsorderinfo curMgoodsorderinfo,List<Dgoodsorderinfo> lsDgoodsorderinfo)
	{
		try
		{
			this.amn_Dao.saveOrUpdate(curMgoodsorderinfo);
			String strSql=" delete dgoodsorderinfo where ordercompid='"+curMgoodsorderinfo.getId().getOrdercompid()+"' and orderbillid='"+curMgoodsorderinfo.getId().getOrderbillid()+"'  ";
			this.amn_Dao.executeSql(strSql);
			if(lsDgoodsorderinfo!=null && lsDgoodsorderinfo.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsorderinfo);
			}
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	

	@Override
	protected boolean postMaster(Object curMaster) {
		this.amn_Dao.saveOrUpdate(curMaster);
		return true;
	}
	
	//获取主档信息
	public List<Mgoodsorderinfo> loadMgoodsorderinfo(String strCompId,String strDate,String strBillId,int pageSize, int startRow)
	{
		try
		{
			String strSql="select  mgoodsorderinfo From Mgoodsorderinfo mgoodsorderinfo    " +
					" where ordercompid='"+strCompId+"' and (isnull(orderdate,'')='"+strDate+"' or '"+strDate+"'='' ) " +
					" and (isnull(orderbillid,'')='"+strBillId+"' or '"+strBillId+"'='' ) and isnull(invalid,0)=0 order by orderdate desc ";
			return (List<Mgoodsorderinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public Mgoodsorderinfo loadMgoodsorderinfoById(String strCompId,String strBillId)
	{
		try
		{
			String strSql="select  ordercompid,orderbillid,orderdate,ordertime,orderstate,orderstaffid, " +
					"  downorderstaffid,downorderdate,downordertime,sendbillno,revicebillno,ordersource," +
					"  storewareid,headwareid,orderbilltype,orderopationerid,orderopationdate,arrivaldate " +
					"  From  mgoodsorderinfo " +
					" where ordercompid='"+strCompId+"' and orderbillid='"+strBillId+"'  ";
			 AnlyResultSet<Mgoodsorderinfo> analysis = new AnlyResultSet<Mgoodsorderinfo>()
				{
					public Mgoodsorderinfo anlyResultSet(ResultSet rs)
					{
						Mgoodsorderinfo returnValue = new Mgoodsorderinfo();
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue.setId(new MgoodsorderinfoId(CommonTool.FormatString(rs.getString("ordercompid")),CommonTool.FormatString(rs.getString("orderbillid"))));
								returnValue.setBordercompid(CommonTool.FormatString(rs.getString("ordercompid")));
								returnValue.setBorderbillid(CommonTool.FormatString(rs.getString("orderbillid")));
								returnValue.setOrderdate(CommonTool.getDateMask(rs.getString("orderdate")));
								returnValue.setOrdertime(CommonTool.getTimeMask(rs.getString("ordertime"),1));
								returnValue.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
								returnValue.setOrderstaffid(CommonTool.FormatString(rs.getString("orderstaffid")));
								returnValue.setDownorderstaffid(CommonTool.FormatString(rs.getString("downorderstaffid")));
								returnValue.setDownorderdate(CommonTool.getDateMask(rs.getString("downorderdate")));
								returnValue.setDownordertime(CommonTool.getTimeMask(rs.getString("downordertime"),1));
								returnValue.setSendbillno(CommonTool.FormatString(rs.getString("sendbillno")));
								returnValue.setRevicebillno(CommonTool.getDateMask(rs.getString("revicebillno")));
								returnValue.setOrdersource(CommonTool.FormatInteger(rs.getInt("ordersource")));
								returnValue.setStorewareid(CommonTool.FormatString(rs.getString("storewareid")));
								returnValue.setHeadwareid(CommonTool.FormatString(rs.getString("headwareid")));
								returnValue.setOrderbilltype(CommonTool.FormatInteger(rs.getInt("orderbilltype")));
								returnValue.setOrderopationerid(CommonTool.FormatString(rs.getString("orderopationerid")));
								returnValue.setOrderopationdate(CommonTool.getDateMask(rs.getString("orderopationdate")));
								returnValue.setArrivaldate(CommonTool.getDateMask(rs.getString("arrivaldate")));
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
				Mgoodsorderinfo returnRecord= (Mgoodsorderinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
				returnRecord.setBordercompname(this.dataTool.loadCompNameById(CommonTool.FormatString(returnRecord.getBordercompid())));
				StringBuffer validatemsg=new StringBuffer();
				returnRecord.setOrderstaffname(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getBordercompid()), CommonTool.FormatString(returnRecord.getOrderstaffid()),validatemsg));
				validatemsg=null;
				analysis=null;
				return returnRecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取明细信息
	 public List<Dgoodsorderinfo> loadDgoodsorderinfo(String strCompId,String strBillId)
	 {
		 try
		 {
			 
			 String strModeId=this.dataTool.loadSysParam(strCompId,"SP061");
		     String strFristModeId="";//第一级模板Id
			 String strSecondModeId="";//第2级模板ID
			 String strThirthModeId="";//第三级模板Id
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.dataTool.loadCompLvl(strCompId);
		     if(compLvl==2)
			 {
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			 }
			 String strSql=" select ordercompid,orderbillid,ordergoodsno,goodsname,ordergoodscount,ordergoodsunit,ordergoodsprice,ordergoodsamt,downordercount,nodowncount,norevicecount,ordergoodstype,ordermark,headwareno,goodssource " +
			 		" From  dgoodsorderinfo,goodsnameinfo where ordercompid='"+strCompId+"' and orderbillid='"+strBillId+"' " +
			 		" and  ordergoodsno=goodsno  ";
			 AnlyResultSet<List<Dgoodsorderinfo>> analysis = new AnlyResultSet<List<Dgoodsorderinfo>>()
				{
					public List<Dgoodsorderinfo> anlyResultSet(ResultSet rs)
					{
						List<Dgoodsorderinfo> returnValue = new ArrayList();
						Dgoodsorderinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodsorderinfo();
								record.setId(new DgoodsorderinfoId(CommonTool.FormatString(rs.getString("ordercompid")),CommonTool.FormatString(rs.getString("orderbillid")),0));
								record.setOrdergoodsno(CommonTool.FormatString(rs.getString("ordergoodsno")));
								record.setOrdergoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setOrdergoodsunit(CommonTool.FormatString(rs.getString("ordergoodsunit")));
								record.setOrdergoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodscount"))));
								record.setOrdergoodsprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodsprice"))));
								record.setOrdergoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodsamt"))));
								record.setDownordercount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("downordercount"))));
								record.setNodowncount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("nodowncount"))));
								record.setNorevicecount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("norevicecount"))));
								record.setOrdergoodstype(CommonTool.FormatInteger(rs.getInt("ordergoodstype")));
								record.setOrdermark(CommonTool.FormatString(rs.getString("ordermark")));
								record.setHeadwareno(CommonTool.FormatString(rs.getString("headwareno")));
								record.setGoodssource(CommonTool.FormatInteger(rs.getInt("goodssource")));
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
				List<Dgoodsorderinfo> ls= (List<Dgoodsorderinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return ls;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 //新增主档
	 public Mgoodsorderinfo addMastRecord(String strCompId)
	 {
		 Mgoodsorderinfo record=new Mgoodsorderinfo();
		 record.setId(new MgoodsorderinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mgoodsorderinfo", "orderbillid", "SP012")));
		 record.setOrderdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setOrdertime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setBordercompid(strCompId);
		 record.setOrderstaffid(CommonTool.getLoginInfo("USERID"));
		 record.setOrderstaffname(CommonTool.getLoginInfo("USERNAME"));
		 record.setStorewareid(CommonTool.FormatString(this.dataTool.loadSysParam(strCompId, "SP013")));
		 record.setBorderbillid(record.getId().getOrderbillid());
		 record.setOrderstate(0);
		 record.setInvalid(0);
		 return record;
	 }
	 
	
	 
	 //取消复合入库单
	 public boolean handcancelInfo(String strCompId,String strBillId)
	 {
		 try
		 {
			 String strSql="update mgoodsorderinfo set orderstate=0 ,orderopationerid='',orderopationdate='' where ordercompid='"+strCompId+"' and orderbillid='"+strBillId+"' ";
			 strSql=strSql+" update dgoodsorderinfo set ordergoodstype=0  where ordercompid='"+strCompId+"' and orderbillid='"+strBillId+"' ";
			 return this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 //获得所有当月所有员工的订购产品金额
	 public double loadStaffGoodsAmt(String strCompId)
	 {
		 String strSql=" select ordergoodsamt=sum(ordergoodsamt) from mgoodsorderinfo a,dgoodsorderinfo b" +
		 		" where a.ordercompid=b.ordercompid and a.orderbillid=b.orderbillid " +
		 		" and a.ordercompid='"+strCompId+"' and a.orderbilltype=2 and  substring(orderdate,1,6)='"+CommonTool.getCurrDate().substring(0,6)+"' and  isnull(orderstate,0)=1 ";
		 AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
			{
				public Double anlyResultSet(ResultSet rs)
				{
					double returnValue = 0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatDouble(rs.getDouble("ordergoodsamt"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  0;
					}
					return returnValue;
				}
			};
			double returnOrderamt= (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return returnOrderamt;
	 }
	 public double loadOnloadGoodstock(String strCompId,String strGoodNo)
	 {
		 String strSql=" select downordercount=sum(downordercount) from mgoodsorderinfo a,dgoodsorderinfo b" +
	 		" where a.ordercompid=b.ordercompid and a.orderbillid=b.orderbillid and isnull(b.goodssource,0)=0 " +
	 		" and a.downordercompid='"+strCompId+"'  and isnull(b.ordergoodstype,0)=2 and  ordergoodsno='"+strGoodNo+"' ";
		 AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue = 0;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("downordercount"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  0;
				}
				return returnValue;
			}
		};
		double returnGoodsStock= (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return returnGoodsStock;
	 }
	 public BigDecimal loadCurStock(String strCompId,String strDate,String strGoodsId,String strWareId)
	 {
		 String strSql=" exec upg_compute_goods_stock '"+strCompId+"','"+CommonTool.setDateMask(strDate)+"','"+CommonTool.FormatString(strGoodsId)+"','"+CommonTool.FormatString(strGoodsId)+"','"+CommonTool.FormatString(strWareId)+"' ";
		 AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
			{
				public Double anlyResultSet(ResultSet rs)
				{
					double returnValue =0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=rs.getInt("quantity");
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue = 0;
					}
					return returnValue;
				}
			};
			double goodscount= (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return CommonTool.FormatBigDecimal(new BigDecimal(goodscount));
	 }
	 
	 public String loadGoodsHeadWareNo(String strGoodsno)
	 {
		 String strSql=" select goodswarehouse from goodsinfo where goodsmodeid='SGM' and goodsno='"+strGoodsno+"' ";
		 AnlyResultSet<String> analysis = new AnlyResultSet<String>()
			{
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue ="";
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=rs.getString("goodswarehouse");
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue = "";
					}
					return returnValue;
				}
			};
			String headWareNo= (String)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return headWareNo;
	 }
	 /**
	  * 根据产品编号查询供应商编号
	  * @param goodsno
	  * @return
	  */
	 public String loadSupplierNo(String goodsno){
		 String strSql=" select supplierno from supplierprice where goodsno='"+ goodsno +"' ";
		 AnlyResultSet<String> analysis = new AnlyResultSet<String>()
			{
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue ="";
					try{
						if(rs != null && rs.next()==true){
							returnValue=rs.getString("supplierno");
						}				
					}catch(Exception e){
						e.printStackTrace();
						returnValue = "";
					}
					return returnValue;
				}
			};
			String supplierno = (String)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return supplierno;
	 }
}

