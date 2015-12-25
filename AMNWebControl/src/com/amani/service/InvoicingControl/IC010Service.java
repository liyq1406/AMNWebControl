package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dgoodsinsertpc;
import com.amani.model.Dgoodssendbarinfo;
import com.amani.model.Dgoodssendinfo;
import com.amani.model.DgoodssendinfoId;
import com.amani.model.Mgoodssendinfo;
import com.amani.model.MgoodssendinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class IC010Service extends AMN_ModuleService{

	 public List<Dgoodssendinfo> loadDgoodssendinfos(String strCompId,String strBillId)
	 {
		 try
		 {
			 String strSql=" select goodspricetype,sendcompid,sendbillid,sendgoodsno,goodsname,ordergoodscount,sendgoodsunit,ordergoodsamt,downordercount,nodowncount," +
			 		" sendgoodprice,sendgoodrate ,sendgoodscount,sendgoodsamt,frombarcode,tobarcode " +
			 		" From  dgoodssendinfo,goodsnameinfo where sendcompid='"+strCompId+"' and sendbillid='"+strBillId+"' " +
			 		" and  sendgoodsno=goodsno   order by   goodspricetype ";
			 AnlyResultSet<List<Dgoodssendinfo>> analysis = new AnlyResultSet<List<Dgoodssendinfo>>()
				{
					public List<Dgoodssendinfo> anlyResultSet(ResultSet rs)
					{
						List<Dgoodssendinfo> returnValue = new ArrayList();
						Dgoodssendinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodssendinfo();
								record.setId(new DgoodssendinfoId(CommonTool.FormatString(rs.getString("sendcompid")),CommonTool.FormatString(rs.getString("sendbillid")),0));
								record.setSendgoodsno(CommonTool.FormatString(rs.getString("sendgoodsno")));
								record.setSendgoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setSendgoodsunit(CommonTool.FormatString(rs.getString("sendgoodsunit")));
								record.setOrdergoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodscount"))));
								record.setOrdergoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodsamt"))));
								record.setDownordercount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("downordercount"))));
								record.setNodowncount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("nodowncount"))));
								record.setSendgoodprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodprice"))));
								record.setSendgoodrate(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodrate"))));
								record.setSendgoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodscount"))));
								record.setSendgoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodsamt"))));
								record.setFrombarcode(CommonTool.FormatString(rs.getString("frombarcode")));
								record.setTobarcode(CommonTool.FormatString(rs.getString("tobarcode")));
								record.setGoodspricetype(CommonTool.FormatString(rs.getString("goodspricetype")));
								
								
								
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
	 
	 
	 public List<Dgoodssendbarinfo> loadDgoodssendbarinfos(String strCompId,String strBillId,String strCurGoodsId)
	 {
		 try
		 {
			 String strSql=" select sendcompid,sendbillid,sendseqno,sendgoodsno,frombarcode,tobarcode,sendbarcount " +
			 		" From  dgoodssendbarinfo  where sendcompid='"+strCompId+"' and sendbillid='"+strBillId+"' and sendgoodsno='"+strCurGoodsId+"' " ;
			 		
			 AnlyResultSet<List<Dgoodssendbarinfo>> analysis = new AnlyResultSet<List<Dgoodssendbarinfo>>()
				{
					public List<Dgoodssendbarinfo> anlyResultSet(ResultSet rs)
					{
						List<Dgoodssendbarinfo> returnValue = new ArrayList();
						Dgoodssendbarinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodssendbarinfo();
								record.setSendcompid(CommonTool.FormatString(rs.getString("sendcompid")));
								record.setSendbillid(CommonTool.FormatString(rs.getString("sendbillid")));
								record.setSendseqno(CommonTool.FormatDouble(rs.getDouble("sendseqno")));
								record.setSendgoodsno(CommonTool.FormatString(rs.getString("sendgoodsno")));
								record.setFrombarcode(CommonTool.FormatString(rs.getString("frombarcode")));
								record.setTobarcode(CommonTool.FormatString(rs.getString("tobarcode")));
								record.setSendbarcount(CommonTool.FormatDouble(rs.getDouble("sendbarcount")));
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
				List<Dgoodssendbarinfo> ls= (List<Dgoodssendbarinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return ls;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	
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
	
	public boolean postInfo(Mgoodssendinfo curMgoodssendinfo,List<Dgoodssendinfo> lsDgoodssendinfo ,List<Dgoodsinsertpc>  lsDgoodsinsertpc,List<Dgoodssendbarinfo>  lsDgoodssendbarinfo)
	{
		try
		{
			String strSqlHistory="";
			this.amn_Dao.saveOrUpdate(curMgoodssendinfo);
			strSqlHistory=" insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)" +
			" values('"+CommonTool.FormatString(curMgoodssendinfo.getId().getSendcompid())+"','2', " +
					" '"+CommonTool.FormatString(curMgoodssendinfo.getId().getSendbillid())+"'," +
					" '"+CommonTool.FormatString(curMgoodssendinfo.getSenddate())+"'," +
					" '"+CommonTool.FormatString(curMgoodssendinfo.getSendtime())+"'," +
					" '"+CommonTool.FormatString(curMgoodssendinfo.getHeadwareid())+"'," +
					" 1," +
					" '"+CommonTool.FormatString(curMgoodssendinfo.getOrdercompid())+"',2) ";
			if(CommonTool.FormatInteger(curMgoodssendinfo.getOrderbilltype())==1)
			{
				strSqlHistory=strSqlHistory+"insert mgoodsreceipt(receiptcompid,receiptbillid,receiptwareid,receiptsendbillid,receiptorderbillid,orderbilltype,receiptstate)" +
									" values('"+CommonTool.FormatString(curMgoodssendinfo.getOrdercompid())+"', " +
									" '"+CommonTool.FormatString(curMgoodssendinfo.getId().getSendbillid()+"R")+"'," +
									" '"+CommonTool.FormatString(curMgoodssendinfo.getStorewareid())+"'," +
									" '"+CommonTool.FormatString(curMgoodssendinfo.getId().getSendbillid())+"'," +
									" '"+CommonTool.FormatString(curMgoodssendinfo.getOrderbill())+"'," +
									" "+CommonTool.FormatInteger(curMgoodssendinfo.getOrderbilltype())+"," +
									" 0 ) ";
			}
			String strSql=" update mgoodsorderinfo set orderstate=3 where ordercompid='"+curMgoodssendinfo.getOrdercompid()+"' and orderbillid='"+curMgoodssendinfo.getOrderbill()+"' ";
			if(lsDgoodssendinfo!=null && lsDgoodssendinfo.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodssendinfo);
				 for(int i=0;i<lsDgoodssendinfo.size();i++)
				 {
					 strSqlHistory=strSqlHistory+" insert dgoodsstockinfo(changecompid,changetype,changebillno,changeseqno,changegoodsno,standunit,standcount,standprice,producedate,changeunit,changecount,changeprice,changeamt)" +
					 " values(	'"+CommonTool.FormatString(lsDgoodssendinfo.get(i).getId().getSendcompid())+"','2'," +
					 			" '"+CommonTool.FormatString(lsDgoodssendinfo.get(i).getId().getSendbillid())+"'," +
					 			" "+i+"," +
					 			" '"+CommonTool.FormatString(lsDgoodssendinfo.get(i).getSendgoodsno())+"'," +
					 			" '"+CommonTool.FormatString(lsDgoodssendinfo.get(i).getSendgoodsunit())+"'," +
					 			" "+CommonTool.FormatBigDecimal(lsDgoodssendinfo.get(i).getSendgoodscount())+" ," +
					 			" "+CommonTool.FormatBigDecimal(lsDgoodssendinfo.get(i).getSendgoodprice())+" ," +
					 			" '"+CommonTool.getCurrDate()+"'," +
					 			" '"+CommonTool.FormatString(lsDgoodssendinfo.get(i).getSendgoodsunit())+"'," +
					 			" "+CommonTool.FormatBigDecimal(lsDgoodssendinfo.get(i).getSendgoodscount())+" ," +
					 			" "+CommonTool.FormatBigDecimal(lsDgoodssendinfo.get(i).getSendgoodprice())+" ," +
					 			" "+CommonTool.FormatBigDecimal(lsDgoodssendinfo.get(i).getSendgoodsamt()) +" ) "; 
					if(CommonTool.FormatInteger(curMgoodssendinfo.getOrderbilltype())==1)
					{
							 strSqlHistory=strSqlHistory+" insert dgoodsreceipt(receiptcompid,receiptbillid,receiptseqno,receiptgoodsno,receiptgoodsunit,receiptprice,sendgoodscount,ordergoodscount)" +
							 			" values('"+CommonTool.FormatString(curMgoodssendinfo.getOrdercompid())+"', " +
										" '"+CommonTool.FormatString(curMgoodssendinfo.getId().getSendbillid()+"R")+"'," +
							 			" "+i+"," +
							 			" '"+CommonTool.FormatString(lsDgoodssendinfo.get(i).getSendgoodsno())+"'," +
							 			" '"+CommonTool.FormatString(lsDgoodssendinfo.get(i).getSendgoodsunit())+"'," +
							 			" "+CommonTool.FormatBigDecimal(lsDgoodssendinfo.get(i).getSendgoodprice())+" ," +
							 			" "+CommonTool.FormatBigDecimal(lsDgoodssendinfo.get(i).getSendgoodscount())+" ," +
							 			" '"+CommonTool.FormatBigDecimal(lsDgoodssendinfo.get(i).getOrdergoodscount())+"' ) "; 
					}
					strSql=strSql+" update dgoodsorderinfo set ordergoodstype=3 where ordercompid='"+curMgoodssendinfo.getOrdercompid()+"' and orderbillid='"+curMgoodssendinfo.getOrderbill()+"' and ordergoodsno='"+lsDgoodssendinfo.get(i).getSendgoodsno()+"' ";
				 }
			}
			if(strSqlHistory!="")
				 this.amn_Dao.executeSql(strSqlHistory);
			if(lsDgoodssendbarinfo!=null && lsDgoodssendbarinfo.size()>0 && CommonTool.FormatInteger(curMgoodssendinfo.getOrderbilltype())==1)
			{
				 String strSqlBarCode="";
				 for(int i=0;i<lsDgoodssendbarinfo.size();i++)
				 {
					 strSqlBarCode=strSqlBarCode+" update dgoodsbarinfo set barnostate=2,outerdate='"+curMgoodssendinfo.getSenddate()+"',outerbill='"+curMgoodssendinfo.getId().getSendbillid()+"',receivestore='"+curMgoodssendinfo.getOrdercompid()+"' where goodsbarno between '"+CommonTool.FormatString(lsDgoodssendbarinfo.get(i).getFrombarcode())+"' and '"+CommonTool.FormatString(lsDgoodssendbarinfo.get(i).getTobarcode())+"' ";
					 /*strSqlBarCode=strSqlBarCode+" insert dgoodssendbarinfo(sendcompid,sendbillid,sendseqno,sendgoodsno,frombarcode,tobarcode,sendbarcount) " +
					 		                     " values( '"+CommonTool.FormatString(curMgoodssendinfo.getId().getSendcompid())+"', " +
					 		                     			" '"+CommonTool.FormatString(curMgoodssendinfo.getId().getSendbillid())+"'," +
					 		                     			" "+i+"," +
					 		                     			" '"+CommonTool.FormatString(lsDgoodssendbarinfo.get(i).getSendgoodsno())+"'," +
					 		                     			" '"+CommonTool.FormatString(lsDgoodssendbarinfo.get(i).getFrombarcode())+"'," +
					 		                     			" '"+CommonTool.FormatString(lsDgoodssendbarinfo.get(i).getTobarcode())+"'," +
					 		                     			" "+CommonTool.FormatDouble(lsDgoodssendbarinfo.get(i).getSendbarcount())+" ) " ;*/
				 }
				 if(strSqlBarCode!="")
					 this.amn_Dao.executeSql(strSqlBarCode);
			}
			if(lsDgoodsinsertpc!=null && lsDgoodsinsertpc.size()>0)
			{
				 String strSqlpc="";
				 for(int i=0;i<lsDgoodsinsertpc.size();i++)
				 {
					 strSqlpc=strSqlpc+" update dgoodsinsertpc set curlavecount=isnull(curlavecount,0)- "+lsDgoodsinsertpc.get(i).getOutercount()+" " +
					 		" where  insercompid='"+curMgoodssendinfo.getId().getSendcompid()+"'" +
					 	    "    and insergoodsno='"+lsDgoodsinsertpc.get(i).getInsergoodsno()+"' and inserbillid='"+lsDgoodsinsertpc.get(i).getInserbillid()+"'  and inserseqno="+lsDgoodsinsertpc.get(i).getInserseqno()+" ";
					 strSqlpc=strSqlpc+" insert dgoodsouterpc(outercompid,outerbillid,outergoodsno,outerseqno,inserbillid,outercount,outerprice) " +
					 		" values('"+curMgoodssendinfo.getId().getSendcompid()+"','"+curMgoodssendinfo.getId().getSendbillid()+"'," +
					 		" '"+lsDgoodsinsertpc.get(i).getInsergoodsno()+"',"+i+",'"+lsDgoodsinsertpc.get(i).getInserbillid()+"',"+lsDgoodsinsertpc.get(i).getOutercount()+","+lsDgoodsinsertpc.get(i).getOuterprice()+" )";
						
				 }
				 if(strSqlpc!="")
					 this.amn_Dao.executeSql(strSqlpc);
			 }
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
	public List<Mgoodssendinfo> loadMgoodsorderinfo()
	{
		try
		{
			String strSql="select  mgoodssendinfo From Mgoodssendinfo mgoodssendinfo   " +
					" where sendcompid='"+CommonTool.getLoginInfo("COMPID")+"'  " +
					" and isnull(invalid,0)=0 and isnull(sendstate,0)=0 and headwareid not in(select no from Suppliermode) ";
			return (List<Mgoodssendinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public Mgoodssendinfo loadMgoodssendinfoById(String strCompId,String strSendBillId ,String strOrderBillId)
	{
		try
		{
			String strSql="select  sendcompid,sendbillid,senddate,sendtime,sendstaffid,sendstate,orderdate,ordertime ," +
					"  storewareid,headwareid,ordercompid,orderbill,orderbilltype,storestaffid,storeaddress,sendopationerid, sendopationdate " +
					"  From  mgoodssendinfo  " +
					" where sendcompid='"+strCompId+"' " +
					" and (sendbillid='"+strSendBillId+"' or '"+strSendBillId+"'='')" +
					" and (orderbill='"+strOrderBillId+"' or '"+strOrderBillId+"'='')  ";
			 AnlyResultSet<Mgoodssendinfo> analysis = new AnlyResultSet<Mgoodssendinfo>()
			{
					public Mgoodssendinfo anlyResultSet(ResultSet rs)
					{
						Mgoodssendinfo returnValue = new Mgoodssendinfo();
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue.setId(new MgoodssendinfoId(CommonTool.FormatString(rs.getString("sendcompid")),CommonTool.FormatString(rs.getString("sendbillid"))));
								returnValue.setBsendcompid(CommonTool.FormatString(rs.getString("sendcompid")));
								returnValue.setBsendbillid(CommonTool.FormatString(rs.getString("sendbillid")));
								returnValue.setSenddate(CommonTool.getDateMask(rs.getString("senddate")));
								returnValue.setSendtime(CommonTool.getTimeMask(rs.getString("sendtime"),1));
								returnValue.setOrderdate(CommonTool.getDateMask(rs.getString("orderdate")));
//								returnValue.setOrdertime(CommonTool.getTimeMask(rs.getString("ordertime"),1));
								returnValue.setSendstate(CommonTool.FormatInteger(rs.getInt("sendstate")));
								returnValue.setSendstaffid(CommonTool.FormatString(rs.getString("sendstaffid")));
								returnValue.setStorewareid(CommonTool.FormatString(rs.getString("storewareid")));
								returnValue.setHeadwareid(CommonTool.FormatString(rs.getString("headwareid")));
								returnValue.setOrdercompid(CommonTool.FormatString(rs.getString("ordercompid")));
								returnValue.setOrderbill(CommonTool.FormatString(rs.getString("orderbill")));
								returnValue.setOrderbilltype(CommonTool.FormatInteger(rs.getInt("orderbilltype")));
								returnValue.setStorestaffid(CommonTool.FormatString(rs.getString("storestaffid")));
								returnValue.setStoreaddress(CommonTool.FormatString(rs.getString("storeaddress")));
								returnValue.setSendopationerid(CommonTool.FormatString(rs.getString("sendopationerid")));
								returnValue.setSendopationdate(CommonTool.getDateMask(rs.getString("sendopationdate")));
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
				Mgoodssendinfo returnRecord= (Mgoodssendinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
				returnRecord.setBbsendcompname(this.dataTool.loadCompNameById(CommonTool.FormatString(returnRecord.getBbsendcompname())));
				StringBuffer validatemsg=new StringBuffer();
				returnRecord.setSendstaffname(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getBbsendcompname()), CommonTool.FormatString(returnRecord.getSendstaffid()),validatemsg));
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
	
	
	public List<Mgoodssendinfo> loadlsMgoodssendinfoById(String strCompId,String strSendBillId ,String strOrderBillId)
	{
		try
		{
			String strSql="select  sendcompid,sendbillid,senddate,sendtime,sendstaffid,sendstate,orderdate,ordertime ," +
					"  storewareid,headwareid,ordercompid,orderbill,orderbilltype,storestaffid,storeaddress,sendopationerid, sendopationdate " +
					"  From  mgoodssendinfo  " +
					" where sendcompid='"+strCompId+"' " +
					" and (sendbillid='"+strSendBillId+"' or '"+strSendBillId+"'='')" +
					" and (orderbill='"+strOrderBillId+"' or '"+strOrderBillId+"'='')  ";
			 AnlyResultSet<List<Mgoodssendinfo>> analysis = new AnlyResultSet<List<Mgoodssendinfo>>()
			{
					public List<Mgoodssendinfo> anlyResultSet(ResultSet rs)
					{
						List<Mgoodssendinfo> lsvalue = new ArrayList();
						Mgoodssendinfo returnValue=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								returnValue=new Mgoodssendinfo();
								returnValue.setId(new MgoodssendinfoId(CommonTool.FormatString(rs.getString("sendcompid")),CommonTool.FormatString(rs.getString("sendbillid"))));
								returnValue.setBsendcompid(CommonTool.FormatString(rs.getString("sendcompid")));
								returnValue.setBsendbillid(CommonTool.FormatString(rs.getString("sendbillid")));
								returnValue.setSenddate(CommonTool.getDateMask(rs.getString("senddate")));
								returnValue.setSendtime(CommonTool.getTimeMask(rs.getString("sendtime"),1));
								returnValue.setOrderdate(CommonTool.getDateMask(rs.getString("orderdate")));
								returnValue.setOrdertime(CommonTool.getTimeMask(rs.getString("ordertime"),1));
								returnValue.setSendstate(CommonTool.FormatInteger(rs.getInt("sendstate")));
								returnValue.setSendstaffid(CommonTool.FormatString(rs.getString("sendstaffid")));
								returnValue.setStorewareid(CommonTool.FormatString(rs.getString("storewareid")));
								returnValue.setHeadwareid(CommonTool.FormatString(rs.getString("headwareid")));
								returnValue.setOrdercompid(CommonTool.FormatString(rs.getString("ordercompid")));
								returnValue.setOrderbill(CommonTool.getDateMask(rs.getString("orderbill")));
								returnValue.setOrderbilltype(CommonTool.FormatInteger(rs.getInt("orderbilltype")));
								returnValue.setStorestaffid(CommonTool.FormatString(rs.getString("storestaffid")));
								returnValue.setStoreaddress(CommonTool.FormatString(rs.getString("storeaddress")));
								returnValue.setSendopationerid(CommonTool.FormatString(rs.getString("sendopationerid")));
								returnValue.setSendopationdate(CommonTool.getDateMask(rs.getString("sendopationdate")));
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
				List<Mgoodssendinfo> returnRecord= (List<Mgoodssendinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
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
	 public List<Dgoodssendinfo> loadDgoodssendinfo(String strCompId,String strBillId)
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
			 String strSql=" select sendcompid,sendbillid,sendgoodsno,goodsname,ordergoodscount,sendgoodsunit,ordergoodsamt,downordercount,nodowncount," +
			 		" sendgoodprice,sendgoodrate ,sendgoodscount,sendgoodsamt,frombarcode,tobarcode " +
			 		" From  dgoodssendinfo,goodsnameinfo where sendcompid='"+strCompId+"' and sendbillid='"+strBillId+"' " +
			 		" and  sendgoodsno=goodsno  ";
			 AnlyResultSet<List<Dgoodssendinfo>> analysis = new AnlyResultSet<List<Dgoodssendinfo>>()
				{
					public List<Dgoodssendinfo> anlyResultSet(ResultSet rs)
					{
						List<Dgoodssendinfo> returnValue = new ArrayList();
						Dgoodssendinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodssendinfo();
								record.setId(new DgoodssendinfoId(CommonTool.FormatString(rs.getString("sendcompid")),CommonTool.FormatString(rs.getString("sendbillid")),0));
								record.setSendgoodsno(CommonTool.FormatString(rs.getString("sendgoodsno")));
								record.setSendgoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setSendgoodsunit(CommonTool.FormatString(rs.getString("sendgoodsunit")));
								record.setOrdergoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodscount"))));
								record.setOrdergoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodsamt"))));
								record.setDownordercount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("downordercount"))));
								record.setNodowncount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("nodowncount"))));
								
								record.setSendgoodprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodprice"))));
								record.setSendgoodrate(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodrate"))));
								record.setSendgoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodscount"))));
								record.setSendgoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendgoodsamt"))));
								record.setFrombarcode(CommonTool.FormatString(rs.getString("frombarcode")));
								record.setTobarcode(CommonTool.FormatString(rs.getString("tobarcode")));
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
	 

	 
	
	 
	 //取消复合入库单
	 public boolean handcancelInfo(String strCompId,String strBillId,String strOrderCompid,String strOrderBillId)
	 {
		 try
		 {
			 String strSql="update mgoodssendinfo set sendstate=2 where sendcompid='"+strCompId+"' and sendbillid='"+strBillId+"' ";
			 String sql="select COUNT(*) from suppliermode where no in (select headwareid from mgoodssendinfo where orderbill='"+strOrderBillId+"')";
			 if(this.amn_Dao.getRowsCount_Ex(sql)==0){//有供应商的订单,更新状态-只更新发货单状态；没有供应商的订单,可以更新发货单和订单主档
				 strSql=strSql+" update mgoodsorderinfo set orderstate=5 where ordercompid='"+strOrderCompid+"' and orderbillid='"+strOrderBillId+"' ";
			 }
			 strSql=strSql+" update b set ordergoodstype=5 " +
			 		" from dgoodssendinfo a,dgoodsorderinfo b " +
			 		" where b.ordercompid='"+strOrderCompid+"' and b.orderbillid='"+strOrderBillId+"' " +
			 		" and   a.sendcompid='"+strCompId+"' and a.sendbillid='"+strBillId+"' and a.sendgoodsno=b.ordergoodsno ";
			 return this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 

	 
	 public String loadStoreByOrders(String strCompId,String strBillId)
	 {
		 String strSql=" select headwareno from dgoodsorderinfo where ordercompid='"+strCompId+"' and orderbillid='"+strBillId+"' group by headwareno ";
		 AnlyResultSet<String> analysis = new AnlyResultSet<String>()
		 {
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue = "";
					try
					{
						while(rs != null && rs.next()==true)
						{
							if(!returnValue.equals(""))
								returnValue=returnValue+";";
							returnValue=returnValue+CommonTool.FormatString(rs.getString("headwareno"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  "";
					}
					return returnValue;
				}
		};
		String returnOrderware= (String)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return returnOrderware;
	 }
	 
	 public double loadOnloadGoodstock(String strCompId,String strGoodNo)
	 {
		 String strSql=" select downordercount=sum(downordercount) from mgoodsorderinfo a,dgoodsorderinfo b" +
	 		" where a.ordercompid=b.ordercompid and a.orderbillid=b.orderbillid " +
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
	 
	 public List<Dgoodsinsertpc> loadDgoodsinsertpc(String strCompId,String strGoodsId,String strWareId)
	 {
		 String strSql=" select insergoodsno,inserbillid,inserseqno,inserdate,curlavecount from dgoodsinsertpc" +
		 		" where insercompid='"+strCompId+"' and insergoodsno='"+strGoodsId+"' and  inserwareid='"+strWareId+"' order by  inserdate ";
		 AnlyResultSet<List<Dgoodsinsertpc>> analysis = new AnlyResultSet<List<Dgoodsinsertpc>>()
			{
				public List<Dgoodsinsertpc> anlyResultSet(ResultSet rs)
				{
					List<Dgoodsinsertpc> returnValue =new ArrayList();
					Dgoodsinsertpc record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dgoodsinsertpc();
							record.setInsergoodsno(CommonTool.FormatString(rs.getString("insergoodsno")));
							record.setInserbillid(CommonTool.FormatString(rs.getString("inserbillid")));
							record.setInserseqno(CommonTool.FormatDouble(rs.getDouble("inserseqno")));
							record.setInserdate(CommonTool.getDateMask(rs.getString("inserdate")));
							record.setCurlavecount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("curlavecount")))));
							returnValue.add(record);
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue = null;
					}
					record=null;
					return returnValue;
				}
			};
			List<Dgoodsinsertpc> ls= (List<Dgoodsinsertpc>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
	 }
	 
	 public String validatFromBarCode(String strGoodsBarNo)
	 {
		 try
		 {
			 String strSql=" select goodsno from dgoodsbarinfo where goodsbarno='"+strGoodsBarNo+"' and  isnull(barnostate,0)=1";
			 AnlyResultSet<String> analysis = new AnlyResultSet<String>()
				{
					public String anlyResultSet(ResultSet rs)
					{
						String returnValue = "";
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue=rs.getString("goodsno");
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							returnValue =  "";
						}
						return returnValue;
					}
				};
				String strGoodsno= (String)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return strGoodsno;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return "";
		 }
	 }
	 
	 public boolean validatEndBarCode(String strGoodsNo,String strGoodsBarNo)
	 {
		 try
		 {
			 String strSql=" select goodsno from dgoodsbarinfo where goodsno='"+strGoodsNo+"' and goodsbarno='"+strGoodsBarNo+"' and  isnull(barnostate,0)=1";
			 AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
				{
					public Boolean anlyResultSet(ResultSet rs)
					{
						boolean returnValue = false;
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue=true;
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							returnValue = false;
						}
						return returnValue;
					}
				};
				boolean flag= (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return flag;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 public int validatBarCode(String strGoodsFromBarNo,String strGoodsToBarNo,StringBuffer strMsgBuf)
	 {
		 try
		 {
			 String strSql=" select 1 from dgoodsbarinfo where goodsbarno between  '"+strGoodsFromBarNo+"' and '"+strGoodsToBarNo+"' and  isnull(barnostate,0)<>1 ";
			 AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
				{
					public Boolean anlyResultSet(ResultSet rs)
					{
						boolean returnValue =true;
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue=false;
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							returnValue = false;
						}
						return returnValue;
					}
				};
			 boolean existsflag= (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			 analysis=null;
			 if(existsflag==false)
			 {
				 strMsgBuf.append("条码"+strGoodsFromBarNo +"至" +strGoodsToBarNo +"之间有不在出库状态的条码,请确认!");
				 return 0;
			 }
			 strMsgBuf.append("");
			 strSql=" select goodsnocount=count(goodsno) from dgoodsbarinfo where goodsbarno between  '"+strGoodsFromBarNo+"' and '"+strGoodsToBarNo+"' and  isnull(barnostate,0)=1";
			 AnlyResultSet<Integer> analysis1 = new AnlyResultSet<Integer>()
				{
					public Integer anlyResultSet(ResultSet rs)
					{
						int returnValue =0;
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue=rs.getInt("goodsnocount");
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
				int goodscount= (Integer)this.amn_Dao.executeQuery_ex(strSql,analysis1);
				if(goodscount==0)
				{
					strMsgBuf.append("条码"+strGoodsFromBarNo +"至" +strGoodsToBarNo +"不是有效的条码范围,请确认!");
					 
				}
				analysis1=null;
				return goodscount;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return 0;
		 }
	 }
	 
	 public List<Dgoodsinsertpc> loadGoodsPc(String strGoodsFromBarNo,String strGoodsToBarNo)
	 {
		 String strSql=" select a.inserbillid,a.insergoodsno,a.inserdate,a.inserseqno ,barcount=count(goodsbarno) from dgoodsinsertpc a,dgoodsbarinfo b" +
		 		" where a.insercompid='"+CommonTool.getLoginInfo("COMPID")+"' and a.inserbillid=b.inserbillno and b.goodsbarno between '"+strGoodsFromBarNo+"' and '"+strGoodsToBarNo+"' " +
		 		" group by a.inserbillid,a.insergoodsno,a.inserdate,a.inserseqno  ";
		 AnlyResultSet<List<Dgoodsinsertpc>> analysis = new AnlyResultSet<List<Dgoodsinsertpc>>()
			{
				public List<Dgoodsinsertpc> anlyResultSet(ResultSet rs)
				{
					List<Dgoodsinsertpc> returnValue =new ArrayList();
					Dgoodsinsertpc record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dgoodsinsertpc();
							record.setInserbillid(CommonTool.FormatString(rs.getString("inserbillid")));
							record.setInsergoodsno(CommonTool.FormatString(rs.getString("insergoodsno")));
							record.setInserdate(CommonTool.getDateMask(rs.getString("inserdate")));
							record.setInserseqno(CommonTool.FormatDouble(rs.getDouble("inserseqno")));
							record.setOutercount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("barcount")))));
							returnValue.add(record);
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue = null;
					}
					return returnValue;
				}
			};
			List<Dgoodsinsertpc>  ls= (List<Dgoodsinsertpc>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
		 	return ls;			
	 }
}

