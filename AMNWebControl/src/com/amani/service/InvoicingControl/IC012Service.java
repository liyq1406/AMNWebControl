package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;


import com.amani.model.Dgoodsreceipt;
import com.amani.model.DgoodsreceiptId;
import com.amani.model.Mgoodsreceipt;
import com.amani.model.MgoodsreceiptId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class IC012Service extends AMN_ModuleService{

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		return true;
		
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		
			return true;
		
	}
	
	public boolean postInfo(Mgoodsreceipt curMgoodsreceipt,List<Dgoodsreceipt> lsDgoodsreceipt  )
	{
		try
		{
			this.amn_Dao.executeSql("update mgoodsreceipt set receiptdate='"+curMgoodsreceipt.getReceiptdate()+"',receipttime='"+curMgoodsreceipt.getReceipttime()+"',receiptstaffid='"+curMgoodsreceipt.getReceiptstaffid()+"',receiptopationerid='"+curMgoodsreceipt.getReceiptopationerid()+"',receiptopationdate='"+curMgoodsreceipt.getReceiptopationdate()+"',receiptstate=1 where receiptcompid='"+CommonTool.FormatString(curMgoodsreceipt.getId().getReceiptcompid())+"' and receiptbillid='"+CommonTool.FormatString(curMgoodsreceipt.getId().getReceiptbillid())+"' ");
			String strSqlHistory=" insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)" +
			" values('"+CommonTool.FormatString(curMgoodsreceipt.getId().getReceiptcompid())+"','1', " +
					" '"+CommonTool.FormatString(curMgoodsreceipt.getId().getReceiptbillid())+"'," +
					" '"+CommonTool.FormatString(curMgoodsreceipt.getReceiptdate())+"'," +
					" '"+CommonTool.FormatString(curMgoodsreceipt.getReceipttime())+"'," +
					" '"+CommonTool.FormatString(curMgoodsreceipt.getReceiptwareid())+"'," +
					" 1," +
					" '"+CommonTool.FormatString(curMgoodsreceipt.getReceiptstaffid())+"',1 ) ";
	
			if(lsDgoodsreceipt!=null && lsDgoodsreceipt.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsreceipt);
				 for(int i=0;i<lsDgoodsreceipt.size();i++)
				 {
					 strSqlHistory=strSqlHistory+" insert dgoodsstockinfo(changecompid,changetype,changebillno,changeseqno,changegoodsno,standunit,standcount,standprice,producedate,changeunit,changecount,changeprice,changeamt)" +
						 " values(	'"+CommonTool.FormatString(lsDgoodsreceipt.get(i).getId().getReceiptcompid())+"','1'," +
						 			" '"+CommonTool.FormatString(lsDgoodsreceipt.get(i).getId().getReceiptbillid())+"'," +
				 				" "+CommonTool.FormatDouble(lsDgoodsreceipt.get(i).getId().getReceiptseqno())+"," +
				 				" '"+CommonTool.FormatString(lsDgoodsreceipt.get(i).getReceiptgoodsno())+"'," +
				 				" '"+CommonTool.FormatString(lsDgoodsreceipt.get(i).getReceiptgoodsunit())+"'," +
				 				" "+CommonTool.FormatBigDecimal(lsDgoodsreceipt.get(i).getReceiptgoodscount())+" ," +
				 				" "+CommonTool.FormatBigDecimal(lsDgoodsreceipt.get(i).getReceiptprice())+" ," +
				 				" ''," +
				 				" '"+CommonTool.FormatString(lsDgoodsreceipt.get(i).getReceiptgoodsunit())+"'," +
				 				" "+CommonTool.FormatBigDecimal(lsDgoodsreceipt.get(i).getReceiptgoodscount())+" ," +
				 				" "+CommonTool.FormatBigDecimal(lsDgoodsreceipt.get(i).getReceiptprice())+" ," +
				 				" "+CommonTool.FormatBigDecimal(lsDgoodsreceipt.get(i).getReceiptgoodsamt()) +" ) "; 
				 }
			}
			if(strSqlHistory!="")
				 this.amn_Dao.executeSql(strSqlHistory);
			String strSql=" update mgoodsorderinfo set orderstate=4,revicebillno='"+curMgoodsreceipt.getId().getReceiptbillid()+"' where ordercompid='"+curMgoodsreceipt.getId().getReceiptcompid()+"' and orderbillid='"+curMgoodsreceipt.getReceiptorderbillid()+"' ";
			strSql=strSql+" update dgoodsorderinfo set ordergoodstype=4 where ordercompid='"+curMgoodsreceipt.getId().getReceiptcompid()+"' and orderbillid='"+curMgoodsreceipt.getReceiptorderbillid()+"' ";
			return this.amn_Dao.executeSql(strSql);
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
	public List<Mgoodsreceipt> loadMgoodsreceipt(String strCurCompId)
	{
		try
		{
			String strSql="select  mgoodsreceipt From Mgoodsreceipt mgoodsreceipt   " +
					" where receiptcompid='"+strCurCompId+"' and isnull(receiptstate,0)=0  ";
			return (List<Mgoodsreceipt>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public Mgoodsreceipt loadcurMgoodsreceiptById(String strCompId,String strSendBillId)
	{
		try
		{
			String strSql="select  receiptcompid,receiptbillid,receiptdate,receipttime,receiptwareid,receiptstaffid,receiptsendbillid,receiptorderbillid ,receiptstate ,orderbilltype,receiptopationerid,receiptopationdate " +
					"  From  mgoodsreceipt ,compchaininfo  " +
					" where receiptcompid=relationcomp and curcomp='"+strCompId+"' " +
					" and receiptbillid='"+strSendBillId+"'  ";
			 AnlyResultSet<Mgoodsreceipt> analysis = new AnlyResultSet<Mgoodsreceipt>()
			{
					public Mgoodsreceipt anlyResultSet(ResultSet rs)
					{
						Mgoodsreceipt returnValue = new Mgoodsreceipt();
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue.setId(new MgoodsreceiptId(CommonTool.FormatString(rs.getString("receiptcompid")),CommonTool.FormatString(rs.getString("receiptbillid"))));
								returnValue.setBreceiptcompid(CommonTool.FormatString(rs.getString("receiptcompid")));
								returnValue.setBreceiptbillid(CommonTool.FormatString(rs.getString("receiptbillid")));
								returnValue.setReceiptdate(CommonTool.getDateMask(rs.getString("receiptdate")));
								returnValue.setReceipttime(CommonTool.getTimeMask(rs.getString("receipttime"),1));
								returnValue.setReceiptwareid(CommonTool.FormatString(rs.getString("receiptwareid")));
								returnValue.setReceiptstaffid(CommonTool.FormatString(rs.getString("receiptstaffid")));
								returnValue.setReceiptstate(CommonTool.FormatInteger(rs.getInt("receiptstate")));
								returnValue.setOrderbilltype(CommonTool.FormatInteger(rs.getInt("orderbilltype")));
								returnValue.setReceiptopationdate(CommonTool.getDateMask(rs.getString("receiptopationdate")));
								returnValue.setReceiptopationerid(CommonTool.FormatString(rs.getString("receiptopationerid")));
								returnValue.setReceiptsendbillid(CommonTool.FormatString(rs.getString("receiptsendbillid")));
								returnValue.setReceiptorderbillid(CommonTool.FormatString(rs.getString("receiptorderbillid")));
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
				Mgoodsreceipt returnRecord= (Mgoodsreceipt)this.amn_Dao.executeQuery_ex(strSql,analysis);
				returnRecord.setBreceiptcompname(this.dataTool.loadCompNameById(CommonTool.FormatString(returnRecord.getBreceiptcompid())));
				StringBuffer validatemsg=new StringBuffer();
				returnRecord.setReceiptstaffname(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getBreceiptcompid()), CommonTool.FormatString(returnRecord.getReceiptstaffid()),validatemsg));
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
	
	
	public List<Mgoodsreceipt> loadlsMgoodsreceiptById(String strCompId,String strSendBillId ,String strOrderBillId)
	{
		try
		{
			String strSql="select  receiptcompid,receiptbillid,receiptdate,receipttime,receiptwareid,receiptstaffid,receiptsendbillid,receiptorderbillid ,receiptstate " +
					"  From  mgoodsreceipt ,compchaininfo  " +
					"  where receiptcompid=relationcomp and curcomp='"+strCompId+"' " +
					" and (receiptbillid='"+strSendBillId+"' or '"+strSendBillId+"'='')" +
					" and (receiptorderbillid='"+strOrderBillId+"' or '"+strOrderBillId+"'='')  ";
			 AnlyResultSet<List<Mgoodsreceipt>> analysis = new AnlyResultSet<List<Mgoodsreceipt>>()
			{
					public List<Mgoodsreceipt> anlyResultSet(ResultSet rs)
					{
						List<Mgoodsreceipt> lsvalue = new ArrayList();
						Mgoodsreceipt returnValue=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								returnValue=new Mgoodsreceipt();
								returnValue.setId(new MgoodsreceiptId(CommonTool.FormatString(rs.getString("receiptcompid")),CommonTool.FormatString(rs.getString("receiptbillid"))));
								returnValue.setBreceiptcompid(CommonTool.FormatString(rs.getString("receiptcompid")));
								returnValue.setBreceiptbillid(CommonTool.FormatString(rs.getString("receiptbillid")));
								returnValue.setReceiptdate(CommonTool.getDateMask(rs.getString("receiptdate")));
								returnValue.setReceipttime(CommonTool.getTimeMask(rs.getString("receipttime"),1));
								returnValue.setReceiptwareid(CommonTool.FormatString(rs.getString("receiptwareid")));
								returnValue.setReceiptstaffid(CommonTool.FormatString(rs.getString("receiptstaffid")));
								returnValue.setReceiptstate(CommonTool.FormatInteger(rs.getInt("receiptstate")));
								returnValue.setReceiptsendbillid(CommonTool.FormatString(rs.getString("receiptsendbillid")));
								returnValue.setReceiptorderbillid(CommonTool.FormatString(rs.getString("receiptorderbillid")));
								lsvalue.add(returnValue);
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							lsvalue =  null;
						}
						return lsvalue;
					}
				};
				List<Mgoodsreceipt> returnRecord= (List<Mgoodsreceipt>)this.amn_Dao.executeQuery_ex(strSql,analysis);
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
	 public List<Dgoodsreceipt> loadDgoodsreceipt(String strCompId,String strBillId)
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
			 String strSql=" select receiptcompid,receiptbillid,receiptgoodsno,goodsname,receiptgoodsunit," +
			 		" receiptprice,receiptgoodscount,receiptgoodsamt,sendgoodscount," +
			 		" damagegoodscount,debegiidscount ,ordergoodscount " +
			 		" From  dgoodsreceipt,goodsnameinfo where receiptcompid='"+strCompId+"' and receiptbillid='"+strBillId+"' " +
			 		" and  receiptgoodsno=goodsno  ";
			 AnlyResultSet<List<Dgoodsreceipt>> analysis = new AnlyResultSet<List<Dgoodsreceipt>>()
				{
					public List<Dgoodsreceipt> anlyResultSet(ResultSet rs)
					{
						List<Dgoodsreceipt> returnValue = new ArrayList();
						Dgoodsreceipt record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodsreceipt();
								record.setId(new DgoodsreceiptId(CommonTool.FormatString(rs.getString("receiptcompid")),CommonTool.FormatString(rs.getString("receiptbillid")),0));
								record.setReceiptgoodsno(CommonTool.FormatString(rs.getString("receiptgoodsno")));
								record.setReceiptgoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setReceiptgoodsunit(CommonTool.FormatString(rs.getString("receiptgoodsunit")));
								record.setReceiptprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("receiptprice"))));
								record.setReceiptgoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("receiptgoodscount"))));
								record.setReceiptgoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("receiptgoodsamt"))));
								record.setSendgoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodscount"))));
								
								record.setDamagegoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("damagegoodscount"))));
								record.setDebegiidscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("debegiidscount"))));
								record.setOrdergoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodscount"))));
							
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
				List<Dgoodsreceipt> ls= (List<Dgoodsreceipt>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return ls;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 

	 
}

