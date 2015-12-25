package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dgoodsreturnbarinfo;
import com.amani.model.Dreturngoodsinfo;
import com.amani.model.DreturngoodsinfoId;
import com.amani.model.Mreturngoodsinfo;
import com.amani.model.MreturngoodsinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class IC007Service extends AMN_ModuleService{

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
		try
		{
			List<Dreturngoodsinfo> lsDreturngoodsinfo=(List<Dreturngoodsinfo>)details;
			if(lsDreturngoodsinfo!=null && lsDreturngoodsinfo.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDreturngoodsinfo);
			}
			lsDreturngoodsinfo=null;
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postInfo(Mreturngoodsinfo curMreturngoodsinfo,List<Dreturngoodsinfo> lsDreturngoodsinfo,List<Dgoodsreturnbarinfo> lsDgoodsreturnbarinfo)
	{
		try
		{
			this.amn_Dao.saveOrUpdate(curMreturngoodsinfo);
			if(lsDreturngoodsinfo!=null && lsDreturngoodsinfo.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDreturngoodsinfo);
			}
			String strSql="";
			if(lsDgoodsreturnbarinfo!=null && lsDgoodsreturnbarinfo.size()>0)
			{
				for(int i=0;i<lsDgoodsreturnbarinfo.size();i++)
				{
					strSql=strSql+"insert dgoodsreturnbarinfo(returncompid,returnbillid,returnseqno,returngoodsno,frombarcode,tobarcode,returncount)" +
					" values('"+curMreturngoodsinfo.getId().getReturncompid()+"','"+curMreturngoodsinfo.getId().getReturnbillid()+"',"+i+"," +
							" '"+lsDgoodsreturnbarinfo.get(i).getReturngoodsno()+"', '"+lsDgoodsreturnbarinfo.get(i).getFrombarcode()+"','"+lsDgoodsreturnbarinfo.get(i).getFrombarcode()+"',1 ) ";
					strSql=strSql+" update dgoodsbarinfo set barnostate=6 where goodsbarno= '"+lsDgoodsreturnbarinfo.get(i).getFrombarcode()+"'";
				}
				if(!strSql.equals(""))
					this.amn_Dao.executeSql(strSql);
			}
			lsDreturngoodsinfo=null;
			lsDgoodsreturnbarinfo=null;
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean postComfirmInfo(Mreturngoodsinfo curMreturngoodsinfo,List<Dreturngoodsinfo> lsDreturngoodsinfo,List<Dgoodsreturnbarinfo> lsDgoodsreturnbarinfo)
	{
		try
		{
			this.amn_Dao.saveOrUpdate(curMreturngoodsinfo);
			String strSqlHistory=" insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)" +
			" values('"+CommonTool.FormatString(curMreturngoodsinfo.getId().getReturncompid())+"','2', " +
					" '"+CommonTool.FormatString(curMreturngoodsinfo.getId().getReturnbillid())+"_e"+"'," +
					" '"+CommonTool.FormatString(curMreturngoodsinfo.getReturndate())+"'," +
					" '"+CommonTool.FormatString(curMreturngoodsinfo.getReturntime())+"'," +
					" '"+CommonTool.FormatString(curMreturngoodsinfo.getReturnwareid())+"'," +
					" 1," +
					" '"+CommonTool.FormatString(curMreturngoodsinfo.getReturnstaffid())+"',1) ";
			if(lsDreturngoodsinfo!=null && lsDreturngoodsinfo.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDreturngoodsinfo);
				 for(int i=0;i<lsDreturngoodsinfo.size();i++)
				 {
					 strSqlHistory=strSqlHistory+" insert dgoodsstockinfo(changecompid,changetype,changebillno,changeseqno,changegoodsno,standunit,standcount,standprice,producedate,changeunit,changecount,changeprice,changeamt)" +
					 " values(	'"+CommonTool.FormatString(curMreturngoodsinfo.getId().getReturncompid())+"','2'," +
					 			" '"+CommonTool.FormatString(curMreturngoodsinfo.getId().getReturnbillid())+"_e"+"'," +
			 					" "+i+"," +
					 			" '"+CommonTool.FormatString(lsDreturngoodsinfo.get(i).getReturngoodsno())+"'," +
					 			" '"+CommonTool.FormatString(lsDreturngoodsinfo.get(i).getReturngoodsunit())+"'," +
					 			" "+CommonTool.FormatBigDecimal(lsDreturngoodsinfo.get(i).getFactreturncount())+" ," +
					 			" "+CommonTool.FormatBigDecimal(lsDreturngoodsinfo.get(i).getFactreturnprice())+" ," +
					 			" '"+CommonTool.getCurrDate()+"'," +
					 			" '"+CommonTool.FormatString(lsDreturngoodsinfo.get(i).getReturngoodsunit())+"'," +
					 			" "+CommonTool.FormatBigDecimal(lsDreturngoodsinfo.get(i).getFactreturncount())+" ," +
					 			" "+CommonTool.FormatBigDecimal(lsDreturngoodsinfo.get(i).getFactreturnprice())+" ," +
					 			" "+CommonTool.FormatBigDecimal(lsDreturngoodsinfo.get(i).getFactreturnamt()) +" ) ";
				 }
			}
			
			if(!strSqlHistory.equals(""))
			{
				this.amn_Dao.executeSql(strSqlHistory);
			}
			String strSql=" delete dgoodsreturnbarinfo where returncompid='"+curMreturngoodsinfo.getId().getReturncompid()+"' and returnbillid='"+curMreturngoodsinfo.getId().getReturnbillid()+"' ";
			if(lsDgoodsreturnbarinfo!=null && lsDgoodsreturnbarinfo.size()>0)
			{
				for(int i=0;i<lsDgoodsreturnbarinfo.size();i++)
				{
					strSql=strSql+"insert dgoodsreturnbarinfo(returncompid,returnbillid,returnseqno,returngoodsno,frombarcode,tobarcode,returncount)" +
					" values('"+curMreturngoodsinfo.getId().getReturncompid()+"','"+curMreturngoodsinfo.getId().getReturnbillid()+"',"+i+"," +
							" '"+lsDgoodsreturnbarinfo.get(i).getReturngoodsno()+"', '"+lsDgoodsreturnbarinfo.get(i).getFrombarcode()+"','"+lsDgoodsreturnbarinfo.get(i).getFrombarcode()+"',1 ) ";
					strSql=strSql+" update dgoodsbarinfo set barnostate=1 ,outerdate='',outerbill='',receivestore='' where goodsbarno= '"+lsDgoodsreturnbarinfo.get(i).getFrombarcode()+"'";
					
				}
				if(!strSql.equals(""))
					this.amn_Dao.executeSql(strSql);
			}
			
			lsDreturngoodsinfo=null;
			lsDgoodsreturnbarinfo=null;
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postStoreInser(String strReturnCompId,String strRetuenBillId)
	{
		try
		{
			String strSqlHistory=" insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)" +
					" select a.confirmcompid,1,a.returnbillid+'_e'+revicestoreno,'"+CommonTool.getCurrDate()+"',returntime,revicestoreno,1,confirmopationerid,1 from mreturngoodsinfo a,Dreturngoodsinfo  b" +
					"  where a.returncompid=b.returncompid  and a.returnbillid=b.returnbillid " +
					" and a.returncompid='"+strReturnCompId+"' and a.returnbillid='"+strRetuenBillId+"' and returntype=1 " +
					" group by a.confirmcompid,a.returnbillid,returndate,returntime,revicestoreno,confirmopationerid ";
			strSqlHistory=strSqlHistory+"insert dgoodsstockinfo(changecompid,changetype,changebillno,changeseqno,changegoodsno,standunit,standcount,standprice,producedate,changeunit,changecount,changeprice,changeamt)" +
					"  select a.confirmcompid,1,a.returnbillid+'_e'+revicestoreno,returnseqno,returngoodsno,returngoodsunit,factreturncount,factreturnprice,'',returngoodsunit,factreturncount,factreturnprice,factreturnamt" +
					" from mreturngoodsinfo a,Dreturngoodsinfo  b " +
					" where a.returncompid=b.returncompid  and a.returnbillid=b.returnbillid " +
					" and a.returncompid='"+strReturnCompId+"' and a.returnbillid='"+strRetuenBillId+"' and returntype=1 and isnull(factreturncount,0)>0 " +
					" group by a.confirmcompid,a.returnbillid,revicestoreno,returnseqno,returngoodsno,returngoodsunit,factreturncount,factreturnprice,factreturnamt ";
			
			return this.amn_Dao.executeSql(strSqlHistory);
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
	public List<Mreturngoodsinfo> loadMreturngoodsinfo(String strCompId,String strDate,int pageSize, int startRow)
	{
		try
		{
			String strSql="select  mreturngoodsinfo From Mreturngoodsinfo mreturngoodsinfo  ,Compchaininfo compchaininfo   " +
					" where returncompid=relationcomp and curcomp='"+strCompId+"' and isnull(returnstate,0)=1 and (isnull(returndate,'')='"+strDate+"' or '"+strDate+"'='' ) " +
					" order by returndate desc ";
			return (List<Mreturngoodsinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public List<Mreturngoodsinfo> loadSearchinfo(String strCompId,String strBillId )
	{
		try
		{
			String strSql="select  mreturngoodsinfo From Mreturngoodsinfo mreturngoodsinfo  ,Compchaininfo compchaininfo   " +
					" where returncompid=relationcomp and curcomp='"+strCompId+"' and (isnull(returnbillid,'')='"+strBillId+"' or '"+strBillId+"'='' ) " +
					" order by returndate desc ";
			return (List<Mreturngoodsinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public Mreturngoodsinfo loadMreturngoodsinfoById(String strCompId,String strBillId)
	{
		try
		{
			String strSql="select  returncompid,returnbillid,returndate,returntime,returnstate,returnstaffid, " +
					"  returnwareid,returnopationerid,returnopationdate,confirmopationerid,confirmopationdate " +
					"  From  mreturngoodsinfo " +
					" where returncompid='"+strCompId+"' and returnbillid='"+strBillId+"'  ";
			 AnlyResultSet<Mreturngoodsinfo> analysis = new AnlyResultSet<Mreturngoodsinfo>()
				{
					public Mreturngoodsinfo anlyResultSet(ResultSet rs)
					{
						Mreturngoodsinfo returnValue = new Mreturngoodsinfo();
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue.setId(new MreturngoodsinfoId(CommonTool.FormatString(rs.getString("returncompid")),CommonTool.FormatString(rs.getString("returnbillid"))));
								returnValue.setBreturncompid(CommonTool.FormatString(rs.getString("returncompid")));
								returnValue.setBreturnbillid(CommonTool.FormatString(rs.getString("returnbillid")));
								returnValue.setReturndate(CommonTool.getDateMask(rs.getString("returndate")));
								returnValue.setReturntime(CommonTool.getTimeMask(rs.getString("returntime"),1));
								returnValue.setReturnstate(CommonTool.FormatInteger(rs.getInt("returnstate")));
								returnValue.setReturnstaffid(CommonTool.FormatString(rs.getString("returnstaffid")));
								returnValue.setReturnwareid(CommonTool.FormatString(rs.getString("returnwareid")));
								returnValue.setReturnopationerid(CommonTool.FormatString(rs.getString("returnopationerid")));
								returnValue.setReturnopationdate(CommonTool.getDateMask(rs.getString("returnopationdate")));
								returnValue.setConfirmopationerid(CommonTool.FormatString(rs.getString("confirmopationerid")));
								returnValue.setConfirmopationdate(CommonTool.getDateMask(rs.getString("confirmopationdate")));
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
				Mreturngoodsinfo returnRecord= (Mreturngoodsinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
				returnRecord.setBreturncompname(this.dataTool.loadCompNameById(CommonTool.FormatString(returnRecord.getBreturncompid())));
				StringBuffer validatemsg=new StringBuffer();
				returnRecord.setReturnstaffname(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getBreturncompid()), CommonTool.FormatString(returnRecord.getReturnstaffid()),validatemsg));
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
	 public List<Dreturngoodsinfo> loadDreturngoodsinfo(String strCompId,String strBillId)
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
			 String strSql=" select returncompid,returnbillid,returngoodsno,goodsname,returncount,returngoodsunit,returntype,revicestoreno,factreturncount,factreturnprice,factreturnamt  " +
			 		" From  dreturngoodsinfo,goodsnameinfo where returncompid='"+strCompId+"' and returnbillid='"+strBillId+"' " +
			 		" and  returngoodsno=goodsno  ";
			 AnlyResultSet<List<Dreturngoodsinfo>> analysis = new AnlyResultSet<List<Dreturngoodsinfo>>()
				{
					public List<Dreturngoodsinfo> anlyResultSet(ResultSet rs)
					{
						List<Dreturngoodsinfo> returnValue = new ArrayList();
						Dreturngoodsinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dreturngoodsinfo();
								record.setId(new DreturngoodsinfoId(CommonTool.FormatString(rs.getString("returncompid")),CommonTool.FormatString(rs.getString("returnbillid")),0));
								record.setReturngoodsno(CommonTool.FormatString(rs.getString("returngoodsno")));
								record.setReturngoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setReturngoodsunit(CommonTool.FormatString(rs.getString("returngoodsunit")));
								record.setReturntype(CommonTool.FormatInteger(rs.getInt("returntype")));
								record.setRevicestoreno(CommonTool.FormatString(rs.getString("revicestoreno")));
								record.setReturncount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("returncount"))));
								record.setFactreturncount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("factreturncount"))));
								record.setFactreturnprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("factreturnprice"))));
								record.setFactreturnamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("factreturnamt"))));

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
				List<Dreturngoodsinfo> ls= (List<Dreturngoodsinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return ls;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 public List<Dgoodsreturnbarinfo> loadDgoodsreturnbarinfo(String strCompId,String strBillId)
	 {
		 try
		 {
			 String strSql=" select returngoodsno,frombarcode,tobarcode,returncount from dgoodsreturnbarinfo where  returncompid='"+strCompId+"' and returnbillid='"+strBillId+"'  ";
			 AnlyResultSet<List<Dgoodsreturnbarinfo>> analysis = new AnlyResultSet<List<Dgoodsreturnbarinfo>>()
				{
					public List<Dgoodsreturnbarinfo> anlyResultSet(ResultSet rs)
					{
						List<Dgoodsreturnbarinfo> returnValue = new ArrayList();
						Dgoodsreturnbarinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodsreturnbarinfo();
								record.setReturngoodsno(CommonTool.FormatString(rs.getString("returngoodsno")));
								record.setFrombarcode(CommonTool.FormatString(rs.getString("frombarcode")));
								record.setTobarcode(CommonTool.FormatString(rs.getString("tobarcode")));
								record.setReturncount(CommonTool.FormatDouble(rs.getDouble("returncount")));
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
				List<Dgoodsreturnbarinfo> ls= (List<Dgoodsreturnbarinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
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
	 public Mreturngoodsinfo addMastRecord(String strCompId)
	 {
		 Mreturngoodsinfo record=new Mreturngoodsinfo();
		 record.setId(new MreturngoodsinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mreturngoodsinfo", "returnbillid", "SP012")));
		 record.setReturndate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setReturntime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setBreturncompid(strCompId);
		 record.setBreturnbillid(record.getId().getReturnbillid());
		 record.setReturnstate(0);

		 return record;
	 }
	 
	
	 
	 //取消复合入库单
	 public boolean handcancelInfo(String strCompId,String strBillId)
	 {
		 try
		 {
			 String strSql="update mreturngoodsinfo set returnstate=3 ,confirmopationerid='"+CommonTool.getLoginInfo("USERID")+"',confirmopationdate='"+CommonTool.getCurrDate()+"' where returncompid='"+strCompId+"' and returnbillid='"+strBillId+"' ";
			 strSql=strSql+" update dgoodsbarinfo set barnostate=2 from dgoodsbarinfo ,dgoodsreturnbarinfo where goodsbarno=frombarcode and  returncompid='"+strCompId+"' and returnbillid='"+strBillId+"' ";
			 return this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 public String validatFromBarCode(String strCurCompId,String strGoodsBarNo)
	 {
		 try
		 {
			 String strSql="";
			 if(strCurCompId.equals("001"))
			 {
				 strSql=" select goodsno from dgoodsbarinfo where goodsbarno='"+strGoodsBarNo+"' and receivestore='"+strCurCompId+"'  and  isnull(barnostate,0)=1 ";
			 }
			 else
			 {
				 strSql=" select goodsno from dgoodsbarinfo where goodsbarno='"+strGoodsBarNo+"' and receivestore='"+strCurCompId+"'  and  isnull(barnostate,0)=2 ";
			 }
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
	 
}

