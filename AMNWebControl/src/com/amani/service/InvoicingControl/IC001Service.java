package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dgoodsbarinfo;
import com.amani.model.Dgoodsinsert;
import com.amani.model.DgoodsinsertId;
import com.amani.model.Mgoodsinsert;
import com.amani.model.MgoodsinsertId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class IC001Service extends AMN_ModuleService{

	 public List<Dgoodsinsert> loadDgoodsinsertss(String strCompId,String strCurBillId)
	 {
		 try
		 {
			 String strSql=" select goodspricetype,insercompid,inserbillid,insergoodsno,goodsname,inserunit,insercount,goodsprice,goodsamt,producedate,producenorm,frombarcode,tobarcode " +
			 		" From  dgoodsinsert,goodsnameinfo where insercompid='"+strCompId+"' and inserbillid='"+strCurBillId+"' " +
			 		" and  insergoodsno=goodsno order by goodspricetype  ";
			 AnlyResultSet<List<Dgoodsinsert>> analysis = new AnlyResultSet<List<Dgoodsinsert>>()
				{
					public List<Dgoodsinsert> anlyResultSet(ResultSet rs)
					{
						List<Dgoodsinsert> returnValue = new ArrayList();
						Dgoodsinsert record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodsinsert();
								record.setId(new DgoodsinsertId(CommonTool.FormatString(rs.getString("insercompid")),CommonTool.FormatString(rs.getString("inserbillid")),0));
								record.setInsergoodsno(CommonTool.FormatString(rs.getString("insergoodsno")));
								record.setInsergoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setInserunit(CommonTool.FormatString(rs.getString("inserunit")));
								record.setInsercount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("insercount"))));
								record.setGoodsprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("goodsprice"))));
								record.setGoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("goodsamt"))));
								record.setProducedate(CommonTool.getDateMask(rs.getString("producedate")));
								record.setProducenorm(CommonTool.FormatString(rs.getString("producenorm")));
								record.setFrombarcode(CommonTool.FormatString(rs.getString("frombarcode")));
								record.setTobarcode(CommonTool.FormatString(rs.getString("tobarcode")));
								record.setChangeoption(CommonTool.FormatString(rs.getString("goodspricetype")));
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
				return (List<Dgoodsinsert>)this.amn_Dao.executeQuery_ex(strSql,analysis);
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
			Mgoodsinsert record=(Mgoodsinsert)curMaster;
			if(record!=null && record.getId()!=null)
			{
				String strSql=" update mgoodsinsert set invalid=1 where insercompid='"+record.getId().getInsercompid()+"' and inserbillid='"+record.getId().getInserbillid()+"' ";
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
		return loadMgoodsinsert(CommonTool.getCurrDate(),"",pageSize,startRow);
	}

	@Override
	protected boolean postDetail(Object details) {
		try
		{
			List<Dgoodsinsert> lsDgoodsinsert=(List<Dgoodsinsert>)details;
			if(lsDgoodsinsert!=null && lsDgoodsinsert.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsinsert);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postInfo(Mgoodsinsert curMgoodsinsert,List<Dgoodsinsert> lsDgoodsinsert)
	{
		try
		{
			String strSql="";
			String strSqlHistory="";
			String strbarSql="";
			StringBuffer bufBarSql=new StringBuffer();
			this.amn_Dao.saveOrUpdate(curMgoodsinsert);
			strSqlHistory=" insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)" +
					" values('"+CommonTool.FormatString(curMgoodsinsert.getId().getInsercompid())+"','1', " +
							" '"+CommonTool.FormatString(curMgoodsinsert.getId().getInserbillid())+"'," +
							" '"+CommonTool.FormatString(curMgoodsinsert.getInserdate())+"'," +
							" '"+CommonTool.FormatString(curMgoodsinsert.getInsertime())+"'," +
							" '"+CommonTool.FormatString(curMgoodsinsert.getInserwareid())+"'," +
							" "+CommonTool.FormatInteger(curMgoodsinsert.getInsertype())+"," +
							" '"+CommonTool.FormatString(curMgoodsinsert.getInserstaffid())+"',1) ";
			if(lsDgoodsinsert!=null && lsDgoodsinsert.size()>0)
			{

				 this.amn_Dao.executeSql(" delete dgoodsinsert where insercompid='"+curMgoodsinsert.getId().getInsercompid()+"' and inserbillid='"+curMgoodsinsert.getId().getInserbillid()+"' " );
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsinsert);
				 for(int i=0;i<lsDgoodsinsert.size();i++)
				 {
					 strSql=strSql+" insert dgoodsinsertpc(insercompid,inserbillid,insergoodsno,inserseqno,producedate,expireddate,curlavecount,inserwareid,inserdate) " +
					 		" values('"+CommonTool.FormatString(lsDgoodsinsert.get(i).getId().getInsercompid())+"'," +
					 				" '"+CommonTool.FormatString(lsDgoodsinsert.get(i).getId().getInserbillid())+"'," +
					 				" '"+CommonTool.FormatString(lsDgoodsinsert.get(i).getInsergoodsno())+"'," +
					 				" "+CommonTool.FormatDouble(lsDgoodsinsert.get(i).getId().getInserseqno())+"," +
					 				" '"+CommonTool.FormatString(lsDgoodsinsert.get(i).getProducedate())+"',''," +
					 				" "+CommonTool.FormatBigDecimal(lsDgoodsinsert.get(i).getInsercount())+" ," +
					 				" '"+CommonTool.FormatString(curMgoodsinsert.getInserwareid())+"'," +
					 				" '"+CommonTool.FormatString(curMgoodsinsert.getInserdate())+"' )  ";
					 strSqlHistory=strSqlHistory+" insert dgoodsstockinfo(changecompid,changetype,changebillno,changeseqno,changegoodsno,standunit,standcount,standprice,producedate,changeunit,changecount,changeprice,changeamt)" +
					 							 " values(	'"+CommonTool.FormatString(lsDgoodsinsert.get(i).getId().getInsercompid())+"','1'," +
					 							 			" '"+CommonTool.FormatString(lsDgoodsinsert.get(i).getId().getInserbillid())+"'," +
											 				" "+CommonTool.FormatDouble(lsDgoodsinsert.get(i).getId().getInserseqno())+"," +
											 				" '"+CommonTool.FormatString(lsDgoodsinsert.get(i).getInsergoodsno())+"'," +
											 				" '"+CommonTool.FormatString(lsDgoodsinsert.get(i).getStandunit())+"'," +
											 				" "+CommonTool.FormatBigDecimal(lsDgoodsinsert.get(i).getInsercount())+" ," +
											 				" "+CommonTool.FormatBigDecimal(lsDgoodsinsert.get(i).getStandprice())+" ," +
											 				" '"+CommonTool.FormatString(lsDgoodsinsert.get(i).getProducedate())+"'," +
											 				" '"+CommonTool.FormatString(lsDgoodsinsert.get(i).getInserunit())+"'," +
											 				" "+CommonTool.FormatBigDecimal(lsDgoodsinsert.get(i).getInsercount())+" ," +
											 				" "+CommonTool.FormatBigDecimal(lsDgoodsinsert.get(i).getGoodsprice())+" ," +
											 				" "+CommonTool.FormatBigDecimal(lsDgoodsinsert.get(i).getGoodsamt()) +" ) "; 
					 //自用品仓库 行政仓库不用生成条码
					 if(!CommonTool.FormatString(curMgoodsinsert.getInserwareid()).equals("03")
					 && !CommonTool.FormatString(curMgoodsinsert.getInserwareid()).equals("05")
					 && !CommonTool.FormatString(curMgoodsinsert.getInserwareid()).equals("06"))
					 {
					
						 this.buildBarByFactNo(CommonTool.FormatString(lsDgoodsinsert.get(i).getStrBarSeqno()), CommonTool.FormatString(lsDgoodsinsert.get(i).getInsergoodsno()), 
							               CommonTool.FormatString(lsDgoodsinsert.get(i).getFrombarcode()), CommonTool.FormatString(lsDgoodsinsert.get(i).getTobarcode()), 
							               CommonTool.FormatString(curMgoodsinsert.getInserdate()), CommonTool.FormatString(lsDgoodsinsert.get(i).getId().getInserbillid()), bufBarSql,lsDgoodsinsert.get(i).getProducedate(),lsDgoodsinsert.get(i).getProduceenddate());
				
					 
					 }
					 
				 }
				 if(!strSql.equals(""))
					 this.amn_Dao.executeSql(strSql);
				 if(!strSqlHistory.equals(""))
					 this.amn_Dao.executeSql(strSqlHistory);
				 strbarSql=bufBarSql.toString();
				 System.out.println(strbarSql);
				 if(!CommonTool.FormatString(strbarSql).equals(""))
					 this.amn_Dao.executeSql(strbarSql);
				
				 bufBarSql=null;
				 strbarSql=null;
				 strSql=null;
				 strSqlHistory=null;
			}
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean buildBarByFactNo(String strBarSeqno,String strBuildGoodsId,String strStartBarCodeNo,String strEndBarCode,String strInserdate,String strInserbillno ,StringBuffer barcodeSqlBuf,String prodDate,String prodEndDate)
	{
		try
		{
			if(CommonTool.FormatString(strStartBarCodeNo).equals("") || CommonTool.FormatString(strEndBarCode).equals(""))
				return true;
			long startCode=Integer.parseInt(strStartBarCodeNo.substring(4,strStartBarCodeNo.length()));
			long endCode=Integer.parseInt(strEndBarCode.substring(4,strEndBarCode.length()));
			String strStargCordNo="";
			String strSql="";
			if(CommonTool.FormatString(strBarSeqno).equals(""))
			{
				strBarSeqno=strStartBarCodeNo.substring(0,4);
			}
			while(startCode<=endCode)
			{
				if(startCode<10)
				{
					strStargCordNo=strBarSeqno+"0000000"+startCode;
				}
				else if(startCode>=10 && startCode<100)
				{
					strStargCordNo=strBarSeqno+"000000"+startCode;
				}
				else if(startCode>=100 && startCode<1000)
				{
					strStargCordNo=strBarSeqno+"00000"+startCode;
				}
				else if(startCode>=1000 && startCode<10000)
				{
					strStargCordNo=strBarSeqno+"0000"+startCode;
				}
				else if(startCode>=10000 && startCode<100000)
				{
					strStargCordNo=strBarSeqno+"000"+startCode;
				}
				else if(startCode>=100000 && startCode<1000000)
				{
					strStargCordNo=strBarSeqno+"00"+startCode;
				}
				else if(startCode>=1000000 && startCode<10000000)
				{
					strStargCordNo=strBarSeqno+"0"+startCode;
				}
				else
				{
					strStargCordNo=strBarSeqno+startCode;
				}
				barcodeSqlBuf.append(" insert dgoodsbarinfo(goodsno,goodsbarno,barnostate,createdate,createstaffno,inserdate,inserbillno,proddate,produceenddate) " +
						" values('"+strBuildGoodsId+"','"+strStargCordNo+"',1,'"+CommonTool.getCurrDate()+"','"+CommonTool.getLoginInfo("USERID")+"','"+strInserdate+"','"+strInserbillno+"','"+CommonTool.setDateMask(prodDate)+"','"+CommonTool.setDateMask(prodEndDate)+"') ");
				startCode=startCode+1;
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
	public List<Mgoodsinsert> loadMgoodsinsert(String strDate,String strBillId,int pageSize, int startRow)
	{
		try
		{
			String strSql="select  mgoodsinsert From Mgoodsinsert mgoodsinsert  " +
					" where insercompid='"+CommonTool.getLoginInfo("COMPID")+"' and (isnull(inserdate,'')='"+strDate+"' or '"+strDate+"'='' ) " +
					" and (isnull(inserbillid,'')='"+strBillId+"' or '"+strBillId+"'='' ) and isnull(invalid,0)=0 order by inserdate desc ";
			return (List<Mgoodsinsert>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public Mgoodsinsert loadMgoodsinsertById(String strCompId,String strBillId)
	{
		try
		{
			String strSql="select  insercompid,inserbillid,inserdate,insertime,inserwareid,inserstaffid" +
					" ,insertype,checkbillflag,checkbillno,storesendbill,exitstoreno,exidbillno,billflag,inseropationerid,inseropationdate" +
					"  From  mgoodsinsert " +
					" where insercompid='"+strCompId+"' and inserbillid='"+strBillId+"'  ";
			 AnlyResultSet<Mgoodsinsert> analysis = new AnlyResultSet<Mgoodsinsert>()
				{
					public Mgoodsinsert anlyResultSet(ResultSet rs)
					{
						Mgoodsinsert returnValue = new Mgoodsinsert();
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue.setId(new MgoodsinsertId(CommonTool.FormatString(rs.getString("insercompid")),CommonTool.FormatString(rs.getString("inserbillid"))));
								returnValue.setBinsercompid(CommonTool.FormatString(rs.getString("insercompid")));
								returnValue.setBinserbillid(CommonTool.FormatString(rs.getString("inserbillid")));
								returnValue.setInserdate(CommonTool.getDateMask(rs.getString("inserdate")));
								returnValue.setInsertime(CommonTool.getTimeMask(rs.getString("insertime"),1));
								returnValue.setInserwareid(CommonTool.FormatString(rs.getString("inserwareid")));
								returnValue.setInserstaffid(CommonTool.FormatString(rs.getString("inserstaffid")));
								returnValue.setInsertype(CommonTool.FormatInteger(rs.getInt("insertype")));
								returnValue.setCheckbillflag(CommonTool.FormatInteger(rs.getInt("checkbillflag")));
								returnValue.setCheckbillno(CommonTool.FormatString(rs.getString("checkbillno")));
								returnValue.setStoresendbill(CommonTool.FormatString(rs.getString("storesendbill")));
								returnValue.setExitstoreno(CommonTool.FormatString(rs.getString("exitstoreno")));
								returnValue.setExidbillno(CommonTool.FormatString(rs.getString("exidbillno")));
								returnValue.setBillflag(CommonTool.FormatInteger(rs.getInt("billflag")));
								returnValue.setInseropationdate(CommonTool.getDateMask(rs.getString("inseropationdate")));
								returnValue.setInseropationerid(CommonTool.FormatString(rs.getString("inseropationerid")));
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
				Mgoodsinsert returnRecord= (Mgoodsinsert)this.amn_Dao.executeQuery_ex(strSql,analysis);
				returnRecord.setBinsercompname(this.dataTool.loadCompNameById(CommonTool.FormatString(returnRecord.getBinsercompid())));
				StringBuffer validatemsg=new StringBuffer();
				returnRecord.setInserstaffname(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getBinsercompid()), CommonTool.FormatString(returnRecord.getInserstaffid()),validatemsg));
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
	 public List<Dgoodsinsert> loadDgoodsinserts(String strCompId,String strBillId)
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
			 String strSql=" select insercompid,inserbillid,insergoodsno,goodsname,inserunit,insercount,goodsprice,goodsamt,producedate,producenorm,frombarcode,tobarcode " +
			 		" From  dgoodsinsert,goodsnameinfo where insercompid='"+strCompId+"' and inserbillid='"+strBillId+"' " +
			 		" and  insergoodsno=goodsno  ";
			 AnlyResultSet<List<Dgoodsinsert>> analysis = new AnlyResultSet<List<Dgoodsinsert>>()
				{
					public List<Dgoodsinsert> anlyResultSet(ResultSet rs)
					{
						List<Dgoodsinsert> returnValue = new ArrayList();
						Dgoodsinsert record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodsinsert();
								record.setId(new DgoodsinsertId(CommonTool.FormatString(rs.getString("insercompid")),CommonTool.FormatString(rs.getString("inserbillid")),0));
								record.setInsergoodsno(CommonTool.FormatString(rs.getString("insergoodsno")));
								record.setInsergoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setInserunit(CommonTool.FormatString(rs.getString("inserunit")));
								record.setInsercount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("insercount"))));
								record.setGoodsprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("goodsprice"))));
								record.setGoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("goodsamt"))));
								record.setProducedate(CommonTool.getDateMask(rs.getString("producedate")));
								record.setProducenorm(CommonTool.FormatString(rs.getString("producenorm")));
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
				return (List<Dgoodsinsert>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 //新增主档
	 public Mgoodsinsert addMastRecord()
	 {
		 Mgoodsinsert record=new Mgoodsinsert();
		 record.setId(new MgoodsinsertId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mgoodsinsert", "inserbillid", "SP012")));
		 record.setInserdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setInsertime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setBinsercompid(CommonTool.getLoginInfo("COMPID"));
		 record.setBinserbillid(record.getId().getInserbillid());
		 record.setInvalid(0);
		 return record;
	 }
	 
	 public boolean loadNewBarCodeBySeqno(String strBarSeqno,String strGoodsNo,int iBarNum,StringBuffer startBuf,StringBuffer endBuf)
		{
				String strMaxBarCode="";
				String strStartbarCode="";
				String strendbarCode="";
				iBarNum=iBarNum-1;
				String strSql="select maxbarcode=max(maxbarcode) from goodsnameinfo where substring(maxbarcode,1,4)='"+strBarSeqno+"' ";
				ResultSet rs=null;
				try
				{
						rs=this.amn_Dao.executeQuery(strSql);
						if(rs.next() && null!=rs)
						{
							strMaxBarCode=rs.getString("maxbarcode");
						}
						if(CommonTool.FormatString(strMaxBarCode).equals(""))
						{
							strStartbarCode=strBarSeqno+"00000000";
							if(iBarNum<10)
							{
								strendbarCode=strBarSeqno+"0000000"+iBarNum;
							}
							else if(10<=iBarNum && iBarNum<100)
							{
								strendbarCode=strBarSeqno+"000000"+iBarNum;
							}
							else if(100<=iBarNum && iBarNum<1000)
							{
								strendbarCode=strBarSeqno+"00000"+iBarNum;
							}
							else if(1000<=iBarNum && iBarNum<10000)
							{
								strendbarCode=strBarSeqno+"0000"+iBarNum;
							}
							else if(10000<=iBarNum && iBarNum<100000)
							{
								strendbarCode=strBarSeqno+"000"+iBarNum;
							}
							else if(100000<=iBarNum && iBarNum<1000000)
							{
								strendbarCode=strBarSeqno+"00"+iBarNum;
							}
							else if(1000000<=iBarNum && iBarNum<10000000)
							{
								strendbarCode=strBarSeqno+"0"+iBarNum;
							}
							else
							{
								strendbarCode=strBarSeqno+""+iBarNum;
							}
						}
						else
						{

							int iMaxBarCode=Integer.parseInt(strMaxBarCode.substring(4, strMaxBarCode.length()))+1;
							if(iMaxBarCode<10)
							{
								strStartbarCode=strBarSeqno+"0000000"+iMaxBarCode;
							}
							else if(10<=iMaxBarCode && iMaxBarCode<100)
							{
								strStartbarCode=strBarSeqno+"000000"+iMaxBarCode;
							}
							else if(100<=iMaxBarCode && iMaxBarCode<1000)
							{
								strStartbarCode=strBarSeqno+"00000"+iMaxBarCode;
							}
							else if(1000<=iMaxBarCode && iMaxBarCode<10000)
							{
								strStartbarCode=strBarSeqno+"0000"+iMaxBarCode;
							}
							else if(10000<=iMaxBarCode && iMaxBarCode<100000)
							{
								strStartbarCode=strBarSeqno+"000"+iMaxBarCode;
							}
							else if(10000<=iMaxBarCode && iMaxBarCode<1000000)
							{
								strStartbarCode=strBarSeqno+"00"+iMaxBarCode;
							}
							else if(10000<=iMaxBarCode && iMaxBarCode<10000000)
							{
								strStartbarCode=strBarSeqno+"0"+iMaxBarCode;
							}
							else
							{
								strStartbarCode=strBarSeqno+""+iMaxBarCode;
							}
							
							if(iMaxBarCode+iBarNum<10)
							{
								strendbarCode=strBarSeqno+"0000000"+(iMaxBarCode+iBarNum);
							}
							else if(10<=iMaxBarCode+iBarNum && iMaxBarCode+iBarNum<100)
							{
								strendbarCode=strBarSeqno+"000000"+(iMaxBarCode+iBarNum);
							}
							else if(100<=iMaxBarCode+iBarNum && iMaxBarCode+iBarNum<1000)
							{
								strendbarCode=strBarSeqno+"00000"+(iMaxBarCode+iBarNum);
							}
							else if(1000<=iMaxBarCode+iBarNum && iMaxBarCode+iBarNum<10000)
							{
								strendbarCode=strBarSeqno+"0000"+(iMaxBarCode+iBarNum);
							}
							else if(10000<=iMaxBarCode+iBarNum && iMaxBarCode+iBarNum<100000)
							{
								strendbarCode=strBarSeqno+"000"+(iMaxBarCode+iBarNum);
							}
							else if(10000<=iMaxBarCode+iBarNum && iMaxBarCode+iBarNum<1000000)
							{
								strendbarCode=strBarSeqno+"00"+(iMaxBarCode+iBarNum);
							}
							else if(10000<=iMaxBarCode+iBarNum && iMaxBarCode+iBarNum<10000000)
							{
								strendbarCode=strBarSeqno+"0"+(iMaxBarCode+iBarNum);
							}
							else
							{
								strendbarCode=strBarSeqno+(iMaxBarCode+iBarNum);
							}
						}
						startBuf.append(strStartbarCode);
						endBuf.append(strendbarCode);
				}
				catch (SQLException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					try {
						if(rs!=null)
						{
							
							rs.close();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				strSql=" update goodsnameinfo set maxbarcode='"+strendbarCode+"' where goodsno='"+strGoodsNo+"' ";
				return this.amn_Dao.executeSql(strSql);
		}
	 
	 //取消复合入库单
	 public boolean handcancelInfo(String strCompId,String strBillId)
	 {
		 try
		 {
			 String strSql="";
			 //更新入库单状态
			 strSql=strSql+" update mgoodsinsert set inseropationerid='',inseropationdate='',billflag=0 where insercompid='"+strCompId+"' and inserbillid='"+strBillId+"' ";
			 //删除库存历史
			 strSql=strSql+" delete mgoodsstockinfo where changecompid='"+strCompId+"' and changetype=1 and changebillno='"+strBillId+"' ";
			 strSql=strSql+" delete dgoodsstockinfo where changecompid='"+strCompId+"' and changetype=1 and changebillno='"+strBillId+"' ";
			 //删除批次信息
			 strSql=strSql+" delete dgoodsinsertpc where insercompid='"+strCompId+"' and inserbillid='"+strBillId+"' ";
			 //删除条码信息
			 strSql=strSql+" delete dgoodsbarinfo where  inserbillno='"+strBillId+"' ";
			 
			 return this.amn_Dao.deleteBySql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 //查询编码状态
	 public boolean isBarState(String strBillId)
	 {
		 try
		 {
			 String strSql="select count(1) from dgoodsbarinfo where inserbillno='"+strBillId+"' and barnostate<>1";
			 return this.amn_Dao.getRowsCount_Ex(strSql)>0;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
}

