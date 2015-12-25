package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Companyinfo;
import com.amani.model.Dgoodsinsert;
import com.amani.model.DgoodsinsertId;
import com.amani.model.Dgoodsinsertpc;
import com.amani.model.Dgoodsouter;
import com.amani.model.DgoodsouterId;
import com.amani.model.Mgoodsinsert;
import com.amani.model.MgoodsinsertId;
import com.amani.model.Mgoodsouter;
import com.amani.model.MgoodsouterId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class IC004Service extends AMN_ModuleService{
	 public  Companyinfo  userinfo(String strCurCompId)
	 {
		 try
		 {
			 String strSql=" select * from companyinfo where compno='"+strCurCompId+"'";
			 AnlyResultSet<Companyinfo> analysis = new AnlyResultSet<Companyinfo>()
				{
					public Companyinfo anlyResultSet(ResultSet rs)
					{
						Companyinfo returnValue = new Companyinfo();
						try
						{
							if(rs != null && rs.next()==true)
							{ 
								returnValue.setCompno(CommonTool.FormatString(rs.getString("compno")));
								returnValue.setCompname(CommonTool.FormatString(rs.getString("compname")));
								returnValue.setCompstate(CommonTool.FormatString(rs.getString("compstate")));
								returnValue.setCompphone(CommonTool.FormatString(rs.getString("compphone")));
								returnValue.setCompaddress(CommonTool.FormatString(rs.getString("compaddress")));
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
				return (Companyinfo)this.amn_Dao.executeQuery_ex(strSql,analysis); 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	 public List<Dgoodsouter> loadDgoodsouterss(String strCurCompId,String strCurBillId)
	 {
		 try
		 {
			 String strSql=" select goodspricetype,outerrate,outercompid,outerbillid,outergoodsno,goodsname,outerunit,outercount,outerprice,outeramt,frombarcode,tobarcode ,curgoodsstock " +
			 		" From  dgoodsouter,goodsnameinfo where outercompid='"+strCurCompId+"' and outerbillid='"+strCurBillId+"'  " +
			 		" and  outergoodsno=goodsno order by  goodspricetype ";
			 AnlyResultSet<List<Dgoodsouter>> analysis = new AnlyResultSet<List<Dgoodsouter>>()
				{
					public List<Dgoodsouter> anlyResultSet(ResultSet rs)
					{
						List<Dgoodsouter> returnValue = new ArrayList();
						Dgoodsouter record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodsouter();
								record.setId(new DgoodsouterId(CommonTool.FormatString(rs.getString("outercompid")),CommonTool.FormatString(rs.getString("outerbillid")),0));
								record.setOutergoodsno(CommonTool.FormatString(rs.getString("outergoodsno")));
								record.setOutergoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setOuterunit(CommonTool.FormatString(rs.getString("outerunit")));
								record.setOutercount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("outercount"))));
								record.setOuterprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("outerprice"))));
								record.setOuteramt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("outeramt"))));
								record.setCurgoodsstock(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("curgoodsstock"))));
								record.setFrombarcode(CommonTool.FormatString(rs.getString("frombarcode")));
								record.setTobarcode(CommonTool.FormatString(rs.getString("tobarcode")));
								record.setGoodspricetype(CommonTool.FormatString(rs.getString("goodspricetype")));
								record.setOuterrate(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("outerrate"))));
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
				return (List<Dgoodsouter>)this.amn_Dao.executeQuery_ex(strSql,analysis);
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
		try
		{
			Mgoodsouter record=(Mgoodsouter)curMaster;
			if(record!=null && record.getId()!=null)
			{
				String strSql=" update mgoodsouter set invalid=1 where outercompid='"+record.getId().getOutercompid()+"' and outerbillid='"+record.getId().getOuterbillid()+"' ";
				record=null;
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
		return loadMgoodsouter("","",pageSize,startRow);
	}

	@Override
	protected boolean postDetail(Object details) {
		try
		{
			List<Dgoodsouter> lsDgoodsouter=(List<Dgoodsouter>)details;
			if(lsDgoodsouter!=null && lsDgoodsouter.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsouter);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postInfo(Mgoodsouter curMgoodsouter,List<Dgoodsouter> lsDgoodsouter,List<Dgoodsinsertpc> lsDgoodsinsertpc)
	{
		try
		{
		
			String strSqlHistory="";
			String strSqlBarCode="";
		
			this.amn_Dao.saveOrUpdate(curMgoodsouter);
			strSqlHistory=" insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)" +
					" values('"+CommonTool.FormatString(curMgoodsouter.getId().getOutercompid())+"','2', " +
							" '"+CommonTool.FormatString(curMgoodsouter.getId().getOuterbillid())+"'," +
							" '"+CommonTool.FormatString(curMgoodsouter.getOuterdate())+"'," +
							" '"+CommonTool.FormatString(curMgoodsouter.getOutertime())+"'," +
							" '"+CommonTool.FormatString(curMgoodsouter.getOuterwareid())+"'," +
							" "+CommonTool.FormatInteger(curMgoodsouter.getOutertype())+"," +
							" '"+CommonTool.FormatString(curMgoodsouter.getOuterstaffid())+"',2) ";
			if(lsDgoodsouter!=null && lsDgoodsouter.size()>0)
			{
				String strSql=" delete dgoodsouter where outercompid='"+curMgoodsouter.getId().getOutercompid()+"' and outerbillid='"+curMgoodsouter.getId().getOuterbillid()+"'  ";
				this.amn_Dao.executeSql(strSql);
				this.amn_Dao.saveOrUpdateAll(lsDgoodsouter);
				for(int i=0;i<lsDgoodsouter.size();i++)
				{
						 strSqlHistory=strSqlHistory+" insert dgoodsstockinfo(changecompid,changetype,changebillno,changeseqno,changegoodsno,standunit,standcount,standprice,producedate,changeunit,changecount,changeprice,changeamt)" +
					 							 " values(	'"+CommonTool.FormatString(lsDgoodsouter.get(i).getId().getOutercompid())+"','2'," +
					 							 			" '"+CommonTool.FormatString(lsDgoodsouter.get(i).getId().getOuterbillid())+"'," +
											 				" "+CommonTool.FormatDouble(lsDgoodsouter.get(i).getId().getOuterseqno())+"," +
											 				" '"+CommonTool.FormatString(lsDgoodsouter.get(i).getOutergoodsno())+"'," +
											 				" '"+CommonTool.FormatString(lsDgoodsouter.get(i).getStandunit())+"'," +
											 				" "+CommonTool.FormatBigDecimal(lsDgoodsouter.get(i).getOutercount())+" ," +
											 				" "+CommonTool.FormatBigDecimal(lsDgoodsouter.get(i).getStandprice())+" ," +
											 				" '"+CommonTool.getCurrDate()+"'," +
											 				" '"+CommonTool.FormatString(lsDgoodsouter.get(i).getOuterunit())+"'," +
											 				" "+CommonTool.FormatBigDecimal(lsDgoodsouter.get(i).getOutercount())+" ," +
											 				" "+CommonTool.FormatBigDecimal(lsDgoodsouter.get(i).getOuterprice())+" ," +
											 				" "+CommonTool.FormatBigDecimal(lsDgoodsouter.get(i).getOuteramt()) +" ) "; 
							
						 if(CommonTool.getLoginInfo("COMPID").equals("001"))
							 strSqlBarCode=strSqlBarCode+" update dgoodsbarinfo set barnostate=2,outerdate='"+curMgoodsouter.getOuterdate()+"',outerbill='"+curMgoodsouter.getId().getOuterbillid()+"',receivestore='"+curMgoodsouter.getOuterstaffid()+"' where goodsbarno between '"+CommonTool.FormatString(lsDgoodsouter.get(i).getFrombarcode())+"' and '"+CommonTool.FormatString(lsDgoodsouter.get(i).getTobarcode())+"' ";
						 else
							 strSqlBarCode=strSqlBarCode+" update dgoodsbarinfo set barnostate=3,outerdate='"+curMgoodsouter.getOuterdate()+"',outerbill='"+curMgoodsouter.getId().getOuterbillid()+"',receivestore='"+curMgoodsouter.getOuterstaffid()+"' where goodsbarno between '"+CommonTool.FormatString(lsDgoodsouter.get(i).getFrombarcode())+"' and '"+CommonTool.FormatString(lsDgoodsouter.get(i).getTobarcode())+"' ";
								 
				}

				 if(!strSqlHistory.equals(""))
					 this.amn_Dao.executeSql(strSqlHistory);
				 if(!strSqlBarCode.equals(""))
					 this.amn_Dao.executeSql(strSqlBarCode);
				 strSqlBarCode=null;
				 strSqlHistory=null;
			}
			 if(lsDgoodsinsertpc!=null && lsDgoodsinsertpc.size()>0)
			 {
				 String strSqlpc="";
				 for(int i=0;i<lsDgoodsinsertpc.size();i++)
				 {
					 strSqlpc=strSqlpc+" update dgoodsinsertpc set curlavecount=isnull(curlavecount,0)- "+lsDgoodsinsertpc.get(i).getOutercount()+" " +
					 		" where  insercompid='"+curMgoodsouter.getId().getOutercompid()+"'" +
					 	    "    and insergoodsno='"+lsDgoodsinsertpc.get(i).getInsergoodsno()+"' and inserbillid='"+lsDgoodsinsertpc.get(i).getInserbillid()+"'  and inserseqno="+lsDgoodsinsertpc.get(i).getInserseqno()+" ";
					 strSqlpc=strSqlpc+" insert dgoodsouterpc(outercompid,outerbillid,outergoodsno,outerseqno,inserbillid,outercount,outerprice) " +
					 		" values('"+curMgoodsouter.getId().getOutercompid()+"','"+curMgoodsouter.getId().getOuterbillid()+"'," +
					 		" '"+lsDgoodsinsertpc.get(i).getInsergoodsno()+"',"+i+",'"+lsDgoodsinsertpc.get(i).getInserbillid()+"',"+lsDgoodsinsertpc.get(i).getOutercount()+","+lsDgoodsinsertpc.get(i).getOuterprice()+" )";
				 }
				 if(strSqlpc!="")
					 this.amn_Dao.executeSql(strSqlpc);
			 }
			 String strSqlOrder=" update b set orderstate=3  from mgoodsorderinfo b,mgoodsouter c " +
			 		" where substring(outerbillid,1,14)=orderbillid  and  LEN(outerbillid)>15 " +
			 		" and b.ordercompid=c.outerstaffid " +
			 		" and c.outerbillid='"+curMgoodsouter.getId().getOuterbillid()+"' and c.outercompid='"+curMgoodsouter.getId().getOutercompid()+"' ";
			 
			 strSqlOrder=strSqlOrder+" update b set ordergoodstype=3  from dgoodsorderinfo b,mgoodsouter c,dgoodsouter d " +
		 		" where substring(c.outerbillid,1,14)=orderbillid  and  LEN(c.outerbillid)>15 " +
		 		" and b.ordercompid=c.outerstaffid " +
		 		" and c.outerbillid='"+curMgoodsouter.getId().getOuterbillid()+"' and c.outercompid='"+curMgoodsouter.getId().getOutercompid()+"' " +
		 		" and c.outerbillid=d.outerbillid and c.outercompid=d.outercompid" +
		 		" and b.ordergoodsno=d.outergoodsno ";
		 
			 this.amn_Dao.executeSql(strSqlOrder);	
			 curMgoodsouter=null;
			 lsDgoodsinsertpc=null;
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	
	public boolean handInserBillByOutBill(Mgoodsouter curMgoodsouter,List<Dgoodsouter> lsDgoodsouter)
	{
		try
		{
			
			 
			String strSqlreceipt="insert mgoodsreceipt(receiptcompid,receiptbillid,receiptwareid,receiptsendbillid,receiptorderbillid,orderbilltype,receiptstate)" +
				" values('"+CommonTool.FormatString(curMgoodsouter.getOuterstaffid())+"', " +
				" '"+CommonTool.FormatString(curMgoodsouter.getId().getOuterbillid()+"R")+"'," +
				" '"+CommonTool.FormatString(curMgoodsouter.getOuterstaffid().trim()+"001")+"'," +
				" '"+CommonTool.FormatString(curMgoodsouter.getId().getOuterbillid())+"'," +
				" '"+CommonTool.FormatString(curMgoodsouter.getId().getOuterbillid())+"'," +
				" "+1+"," +
				" 0 ) ";
			 
			 if(lsDgoodsouter!=null && lsDgoodsouter.size()>0)
			 {
				  for(int i=0;i<lsDgoodsouter.size();i++)
				 {
					
					 strSqlreceipt=strSqlreceipt+" insert dgoodsreceipt(receiptcompid,receiptbillid,receiptseqno,receiptgoodsno,receiptgoodsunit,receiptprice,sendgoodscount,ordergoodscount)" +
			 			" values('"+CommonTool.FormatString(curMgoodsouter.getOuterstaffid())+"', " +
						" '"+CommonTool.FormatString(curMgoodsouter.getId().getOuterbillid()+"R")+"'," +
			 			" "+i+"," +
			 			" '"+CommonTool.FormatString(lsDgoodsouter.get(i).getOutergoodsno())+"'," +
			 			" '"+CommonTool.FormatString(lsDgoodsouter.get(i).getOuterunit())+"'," +
			 			" "+CommonTool.FormatBigDecimal(lsDgoodsouter.get(i).getOuterprice())+" ," +
			 			" "+CommonTool.FormatBigDecimal(lsDgoodsouter.get(i).getOutercount())+" ," +
			 			" '"+CommonTool.FormatBigDecimal(lsDgoodsouter.get(i).getOutercount())+"' ) "; 
				 }
			 }
			 if(!strSqlreceipt.equals(""))
				 return this.amn_Dao.executeSql(strSqlreceipt);
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
	public List<Mgoodsouter> loadMgoodsouter(String strDate,String strBillId,int pageSize, int startRow)
	{
		try
		{
			String strSql="select  mgoodsouter From Mgoodsouter mgoodsouter  " +
					" where outercompid='"+CommonTool.getLoginInfo("COMPID")+"' and (isnull(outerdate,'')='"+strDate+"' or '"+strDate+"'='' ) " +
					" and (isnull(outerbillid,'')='"+strBillId+"' or '"+strBillId+"'='' ) and isnull(invalid,0)=0 and isnull(billflag,0)=0 " +
					" and isnull(outerwareid,'') in ('03','05','06') order by outerdate desc ";
			System.out.println(strSql);
			return (List<Mgoodsouter>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public Mgoodsouter loadMgoodsouterById(String strCompId,String strBillId)
	{
		try
		{
			String strSql="select  outercompid,outerbillid,outerdate,outertime,outerwareid,outerstaffid " +
					" ,outertype,sendbillid,billflag,orderbilltype,outeropationerid,outeropationdate,revicetype " +
					"  From  mgoodsouter " +
					" where outercompid='"+strCompId+"' and outerbillid='"+strBillId+"'  ";
			 AnlyResultSet<Mgoodsouter> analysis = new AnlyResultSet<Mgoodsouter>()
				{
					public Mgoodsouter anlyResultSet(ResultSet rs)
					{
						Mgoodsouter returnValue = new Mgoodsouter();
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue.setId(new MgoodsouterId(CommonTool.FormatString(rs.getString("outercompid")),CommonTool.FormatString(rs.getString("outerbillid"))));
								returnValue.setBoutercompid(CommonTool.FormatString(rs.getString("outercompid")));
								returnValue.setBouterbillid(CommonTool.FormatString(rs.getString("outerbillid")));
								returnValue.setOuterdate(CommonTool.getDateMask(rs.getString("outerdate")));
								returnValue.setOutertime(CommonTool.getTimeMask(rs.getString("outertime"),1));
								returnValue.setOuterwareid(CommonTool.FormatString(rs.getString("outerwareid")));
								returnValue.setOuterstaffid(CommonTool.FormatString(rs.getString("outerstaffid")));
								returnValue.setOutertype(CommonTool.FormatInteger(rs.getInt("outertype")));
								returnValue.setSendbillid(CommonTool.FormatString(rs.getString("sendbillid")));
								returnValue.setBillflag(CommonTool.FormatInteger(rs.getInt("billflag")));
								returnValue.setRevicetype(CommonTool.FormatInteger(rs.getInt("revicetype")));
								returnValue.setOrderbilltype(CommonTool.FormatInteger(rs.getInt("orderbilltype")));
								returnValue.setOuteropationdate(CommonTool.getDateMask(rs.getString("outeropationdate")));
								returnValue.setOuteropationerid(CommonTool.FormatString(rs.getString("outeropationerid")));
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
				Mgoodsouter returnRecord= (Mgoodsouter)this.amn_Dao.executeQuery_ex(strSql,analysis);
				returnRecord.setBoutercompname(this.dataTool.loadCompNameById(CommonTool.FormatString(returnRecord.getBoutercompid())));
				StringBuffer validatemsg=new StringBuffer();
				returnRecord.setOuterstaffname(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getBoutercompid()), CommonTool.FormatString(returnRecord.getOuterstaffid()),validatemsg));
				validatemsg=null;
				return returnRecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取明细信息
	 public List<Dgoodsouter> loadDgoodsouters(String strCompId,String strBillId)
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
			 String strSql=" select outercompid,outerbillid,outergoodsno,goodsname,outerunit,outercount,outerprice,outeramt,frombarcode,tobarcode ,curgoodsstock " +
			 		" From  dgoodsouter,goodsnameinfo where outercompid='"+strCompId+"' and outerbillid='"+strBillId+"' " +
			 		" and  outergoodsno=goodsno  ";
			 AnlyResultSet<List<Dgoodsouter>> analysis = new AnlyResultSet<List<Dgoodsouter>>()
				{
					public List<Dgoodsouter> anlyResultSet(ResultSet rs)
					{
						List<Dgoodsouter> returnValue = new ArrayList();
						Dgoodsouter record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodsouter();
								record.setId(new DgoodsouterId(CommonTool.FormatString(rs.getString("outercompid")),CommonTool.FormatString(rs.getString("outerbillid")),0));
								record.setOutergoodsno(CommonTool.FormatString(rs.getString("outergoodsno")));
								record.setOutergoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setOuterunit(CommonTool.FormatString(rs.getString("outerunit")));
								record.setOutercount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("outercount"))));
								record.setOuterprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("outerprice"))));
								record.setOuteramt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("outeramt"))));
								record.setCurgoodsstock(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("curgoodsstock"))));
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
				return (List<Dgoodsouter>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 //新增主档
	 public Mgoodsouter addMastRecord()
	 {
		 Mgoodsouter record=new Mgoodsouter();
		 record.setId(new MgoodsouterId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mgoodsouter", "outerbillid", "SP012")));
		 record.setOuterdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setOutertime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setBoutercompid(CommonTool.getLoginInfo("COMPID"));
		 record.setBouterbillid(record.getId().getOuterbillid());
		 record.setInvalid(0);
		 return record;
	 }
	 
	 public String validatFromBarCode(String strGoodsBarNo)
	 {
		 try
		 {
			 String strSql="";
			 if(CommonTool.getLoginInfo("COMPID").equals("001"))
				 strSql=" select goodsno from dgoodsbarinfo where goodsbarno='"+strGoodsBarNo+"' and  isnull(barnostate,0)=1";
			 else
				 strSql=" select goodsno from dgoodsbarinfo where goodsbarno='"+strGoodsBarNo+"' and  isnull(barnostate,0)=2";
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
			 String strSql="";
			 if(CommonTool.getLoginInfo("COMPID").equals("001"))
				 strSql=" select goodsno from dgoodsbarinfo where goodsno='"+strGoodsNo+"' and goodsbarno='"+strGoodsBarNo+"' and  isnull(barnostate,0)=1";
			 else
				 strSql=" select goodsno from dgoodsbarinfo where goodsno='"+strGoodsNo+"' and goodsbarno='"+strGoodsBarNo+"' and  isnull(barnostate,0)=2";
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
			 String strSql="";
			 if(CommonTool.getLoginInfo("COMPID").equals("001"))
				 strSql=" select 1 from dgoodsbarinfo where goodsbarno between  '"+strGoodsFromBarNo+"' and '"+strGoodsToBarNo+"' and  isnull(barnostate,0)<>1 ";
			 else
				 strSql=" select 1 from dgoodsbarinfo where goodsbarno between  '"+strGoodsFromBarNo+"' and '"+strGoodsToBarNo+"' and  isnull(barnostate,0)<>2 ";
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
			 if(CommonTool.getLoginInfo("COMPID").equals("001"))
				 strSql=" select goodsnocount=count(goodsno) from dgoodsbarinfo where goodsbarno between  '"+strGoodsFromBarNo+"' and '"+strGoodsToBarNo+"' and  isnull(barnostate,0)=1";
			 else
				 strSql=" select goodsnocount=count(goodsno) from dgoodsbarinfo where goodsbarno between  '"+strGoodsFromBarNo+"' and '"+strGoodsToBarNo+"' and  isnull(barnostate,0)=2";
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
	 
	 
	 //取消复合入库单
	 public boolean handcancelInfo(String strCompId,String strBillId)
	 {
		 try
		 {
			 String strSql="";
			 //更新入库单状态
			 strSql=strSql+" update mgoodsouter set outeropationerid='',outeropationdate='',billflag=0 where outercompid='"+strCompId+"' and outerbillid='"+strBillId+"' ";
			 //删除库存历史
			 strSql=strSql+" delete mgoodsstockinfo where changecompid='"+strCompId+"' and changetype=2 and changebillno='"+strBillId+"' ";
			 strSql=strSql+" delete dgoodsstockinfo where changecompid='"+strCompId+"' and changetype=2 and changebillno='"+strBillId+"' ";
			 //删除批次信息
			 strSql=strSql+" update a set a.curlavecount=isnull(a.curlavecount,0)+isnull(b.outercount,0)  from dgoodsinsertpc a, dgoodsouterpc b where a.insercompid=b.outercompid" +
			 		" and a.inserbillid=b.inserbillid and outercompid='"+strCompId+"' and outerbillid='"+strBillId+"'  ";
			 strSql=strSql+" delete dgoodsouterpc where outercompid='"+strCompId+"' and outerbillid='"+strBillId+"' ";
			 //删除条码信息
			 if(CommonTool.getLoginInfo("COMPID").equals("001"))
				 strSql=strSql+" update  dgoodsbarinfo set barnostate=1 ,outerdate='',outerbill='' where  outerbill='"+strBillId+"' and barnostate=2 ";
			 else
				 strSql=strSql+" update  dgoodsbarinfo set barnostate=2 ,outerdate='',outerbill='' where  outerbill='"+strBillId+"' and barnostate=3 ";
				 
			 //删除生成的入库历史

			 strSql=strSql+" delete b from mgoodsinsert a,dgoodsinsert b,mgoodsouter c  where  c.outercompid='001' and outercompid='"+strCompId+"' and outerbillid='"+strBillId+"' and c.outerbillid= storesendbill and c.revicetype=2 and c.outerstaffid=a.insercompid and a.insercompid=b.insercompid and a.inserbillid=b.inserbillid ";
			 strSql=strSql+" delete b from mgoodsinsert a,mgoodsstockinfo b,mgoodsouter c  where c.outercompid='001' and outercompid='"+strCompId+"' and outerbillid='"+strBillId+"' and c.outerbillid= storesendbill and c.revicetype=2 and c.outerstaffid=a.insercompid and a.insercompid=b.changecompid and a.inserbillid=b.changebillno and b.changetype=1 ";
			 strSql=strSql+" delete b from mgoodsinsert a,dgoodsstockinfo b,mgoodsouter c  where c.outercompid='001' and outercompid='"+strCompId+"' and outerbillid='"+strBillId+"' and c.outerbillid= storesendbill and c.revicetype=2 and c.outerstaffid=a.insercompid and a.insercompid=b.changecompid and a.inserbillid=b.changebillno and b.changetype=1 ";
			 strSql=strSql+" delete a from   mgoodsinsert a,mgoodsouter c where  c.outercompid='001' and outercompid='"+strCompId+"' and outerbillid='"+strBillId+"' and c.outerbillid= storesendbill and c.revicetype=2 and c.outerstaffid=a.insercompid ";

			 return this.amn_Dao.deleteBySql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
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

