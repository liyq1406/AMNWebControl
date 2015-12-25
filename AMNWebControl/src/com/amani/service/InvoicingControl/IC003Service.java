package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dgoodsinsert;
import com.amani.model.DgoodsinsertId;
import com.amani.model.Dgoodsinventory;
import com.amani.model.DgoodsinventoryId;
import com.amani.model.Dgoodsouter;
import com.amani.model.DgoodsouterId;
import com.amani.model.Goodsinfo;
import com.amani.model.Mgoodsinsert;
import com.amani.model.MgoodsinsertId;
import com.amani.model.Mgoodsinventory;
import com.amani.model.MgoodsinventoryId;
import com.amani.model.Mgoodsouter;
import com.amani.model.MgoodsouterId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class IC003Service extends AMN_ModuleService{

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		try
		{
			Mgoodsinventory record=(Mgoodsinventory)curMaster;
			if(record!=null && record.getId()!=null)
			{
				String strSql=" update mgoodsinventory set invalid=1 where inventcompid='"+record.getId().getInventcompid()+"' and inventbillid='"+record.getId().getInventbillid()+"' ";
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
		return loadMgoodsinventory(CommonTool.getCurrDate(),"",pageSize,startRow);
	}

	@Override
	protected boolean postDetail(Object details) {
		try
		{
			List<Dgoodsinventory> lsDgoodsinventory=(List<Dgoodsinventory>)details;
			if(lsDgoodsinventory!=null && lsDgoodsinventory.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsinventory);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postInfo(Mgoodsinventory curMgoodsinventory,List<Dgoodsinventory> lsDgoodsinventory)
	{
		try
		{
			
			this.amn_Dao.saveOrUpdate(curMgoodsinventory);
			String strSql=" delete dgoodsinventory where inventcompid='"+curMgoodsinventory.getId().getInventcompid()+"' and inventbillid='"+curMgoodsinventory.getId().getInventbillid()+"'  ";
			this.amn_Dao.executeSql(strSql);
			if(lsDgoodsinventory!=null && lsDgoodsinventory.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsinventory);
			}
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postInventInfo(String strCompId,String strBillId,String strCurEmpId,String strWareId,List<Dgoodsinventory> lsDgoodsinventory,StringBuffer inserBillNo,StringBuffer outerBillNo)
	{
		try
		{
			if(lsDgoodsinventory!=null && lsDgoodsinventory.size()>0)
			{
				boolean needinserFlag=false;
				boolean needouterFlag=false;
				String strInserBillNo=this.dataTool.loadBillIdByRule(strCompId,"mgoodsinsert", "inserbillid", "SP012");
				String strOuterBillNo=this.dataTool.loadBillIdByRule(strCompId,"mgoodsouter", "outerbillid", "SP012");
				Mgoodsinsert inserRecord=null;
				Mgoodsouter outerRecord=null;
				List<Dgoodsinsert> lsDgoodsinsert =new ArrayList();
				Dgoodsinsert dinser=null;	
				List<Dgoodsouter>  lsDgoodsouter=new ArrayList();
				Dgoodsouter douter=null;
				for(int i=0;i<lsDgoodsinventory.size();i++)
				{
					if(CommonTool.FormatBigDecimal(lsDgoodsinventory.get(i).getDiscount()).doubleValue()>0)
					{
						needinserFlag=true;
						dinser=new Dgoodsinsert();
						dinser.setId(new DgoodsinsertId(strCompId,strInserBillNo,i));
						dinser.setInsergoodsno(lsDgoodsinventory.get(i).getInventgoodsno());
						dinser.setInserunit(lsDgoodsinventory.get(i).getInserunit());
						dinser.setInsercount(lsDgoodsinventory.get(i).getDiscount());
						dinser.setGoodsprice(lsDgoodsinventory.get(i).getInserprice());
						dinser.setGoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(lsDgoodsinventory.get(i).getDiscount().doubleValue()*CommonTool.FormatBigDecimal(lsDgoodsinventory.get(i).getInserprice()).doubleValue())));
						dinser.setStandunit(lsDgoodsinventory.get(i).getInserunit());
						dinser.setStandprice(lsDgoodsinventory.get(i).getInserprice());
						dinser.setFrombarcode(lsDgoodsinventory.get(i).getInventfrombarno());
						dinser.setTobarcode(lsDgoodsinventory.get(i).getInventtobarno());
						lsDgoodsinsert.add(dinser);
					}
					if(CommonTool.FormatBigDecimal(lsDgoodsinventory.get(i).getDiscount()).doubleValue()<0)
					{
						needouterFlag=true;
						douter=new Dgoodsouter();
						douter.setId(new DgoodsouterId(strCompId,strOuterBillNo,i));
						douter.setOutergoodsno(lsDgoodsinventory.get(i).getInventgoodsno());
						douter.setStandunit(lsDgoodsinventory.get(i).getOuterunit());
						douter.setStandprice(lsDgoodsinventory.get(i).getOuterprice());
						douter.setCurgoodsstock(lsDgoodsinventory.get(i).getCurstockcount());
						douter.setOuterunit(lsDgoodsinventory.get(i).getOuterunit());
						douter.setOuterprice(lsDgoodsinventory.get(i).getOuterprice());
						douter.setOutercount(new BigDecimal(lsDgoodsinventory.get(i).getDiscount().doubleValue()*(-1)));
						douter.setOuteramt(CommonTool.FormatBigDecimal(new BigDecimal(lsDgoodsinventory.get(i).getDiscount().doubleValue()*(-1)*CommonTool.FormatBigDecimal(lsDgoodsinventory.get(i).getOuterprice()).doubleValue())));
						douter.setOuterrate(new BigDecimal(1));
						lsDgoodsouter.add(douter);
					}
				}
				
				
	
			    
				if(needinserFlag==true)
				{
					 inserRecord=new Mgoodsinsert();
					 inserRecord.setId(new MgoodsinsertId(strCompId,strInserBillNo));
					 inserRecord.setInserdate(CommonTool.getCurrDate());
					 inserRecord.setInsertime(CommonTool.getCurrTime());
					 inserRecord.setInserstaffid(strCurEmpId);
					 inserRecord.setInsertype(2);
					 inserRecord.setInserwareid(strWareId);
					 this.amn_Dao.save(inserRecord);
					 if(lsDgoodsinsert!=null)
						 this.amn_Dao.saveOrUpdateAll(lsDgoodsinsert);
				}
				if(needouterFlag==true)
				{
					 outerRecord=new Mgoodsouter();
					 outerRecord.setId(new MgoodsouterId(strCompId,strOuterBillNo));
					 outerRecord.setOuterdate(CommonTool.getCurrDate());
					 outerRecord.setOutertime(CommonTool.getCurrTime());
					 outerRecord.setOuterstaffid(strCurEmpId);
					 outerRecord.setOutertype(2);
					 outerRecord.setRevicetype(1);
					 outerRecord.setOuterwareid(strWareId);
					 this.amn_Dao.save(outerRecord);
					 if(lsDgoodsouter!=null)
						 this.amn_Dao.saveOrUpdateAll(lsDgoodsouter);
				}
				inserRecord=null;
				outerRecord=null;
				lsDgoodsinsert=null;
				lsDgoodsouter=null;
				inserBillNo.append(strInserBillNo);
				outerBillNo.append(strOuterBillNo);
				String strSql=" update  mgoodsinventory set inventflag=2,inventinserbillid='"+strInserBillNo+"',inventouterbillid='"+strOuterBillNo+"' where inventcompid='"+strCompId+"' and inventbillid='"+strBillId+"' ";
				this.amn_Dao.executeSql(strSql);
				
			}
			inserBillNo.append("");
			outerBillNo.append("");
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
	public List<Mgoodsinventory> loadMgoodsinventory(String strDate,String strBillId,int pageSize, int startRow)
	{
		try
		{
			String strSql="select mgoodsinventory From Mgoodsinventory mgoodsinventory  " +
					" where inventcompid='"+CommonTool.getLoginInfo("COMPID")+"' and (isnull(inventdate,'')='"+strDate+"' or '"+strDate+"'='' ) " +
					" and (isnull(inventbillid,'')='"+strBillId+"' or '"+strBillId+"'='' ) and isnull(invalid,0)=0 order by inventdate desc ";
			return (List<Mgoodsinventory>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public Mgoodsinventory loadMgoodsinventoryById(String strCompId,String strBillId)
	{
		try
		{
			String strSql="select  inventcompid,inventbillid,inventdate,inventtime,inventwareid,inventstaffid" +
					" ,inventopationerid,inventopationdate,inventouterbillid,inventinserbillid ,inventflag " +
					"  From  mgoodsinventory " +
					" where inventcompid='"+strCompId+"' and inventbillid='"+strBillId+"'  ";
			 AnlyResultSet<Mgoodsinventory> analysis = new AnlyResultSet<Mgoodsinventory>()
				{
					public Mgoodsinventory anlyResultSet(ResultSet rs)
					{
						Mgoodsinventory returnValue = new Mgoodsinventory();
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue.setId(new MgoodsinventoryId(CommonTool.FormatString(rs.getString("inventcompid")),CommonTool.FormatString(rs.getString("inventbillid"))));
								returnValue.setBinventcompid(CommonTool.FormatString(rs.getString("inventcompid")));
								returnValue.setBinventbillid(CommonTool.FormatString(rs.getString("inventbillid")));
								returnValue.setInventdate(CommonTool.getDateMask(rs.getString("inventdate")));
								returnValue.setInventtime(CommonTool.getTimeMask(rs.getString("inventtime"),1));
								returnValue.setInventwareid(CommonTool.FormatString(rs.getString("inventwareid")));
								returnValue.setInventstaffid(CommonTool.FormatString(rs.getString("inventstaffid")));
								returnValue.setInventopationdate(CommonTool.getDateMask(rs.getString("inventopationdate")));
								returnValue.setInventopationdate(CommonTool.FormatString(rs.getString("inventopationdate")));
								returnValue.setInventinserbillid(CommonTool.FormatString(rs.getString("inventinserbillid")));
								returnValue.setInventouterbillid(CommonTool.FormatString(rs.getString("inventouterbillid")));
								returnValue.setInventflag(CommonTool.FormatInteger(rs.getInt("inventflag")));
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
				Mgoodsinventory returnRecord= (Mgoodsinventory)this.amn_Dao.executeQuery_ex(strSql,analysis);
				returnRecord.setBinventcompname(this.dataTool.loadCompNameById(CommonTool.FormatString(returnRecord.getBinventcompid())));
				StringBuffer validatemsg=new StringBuffer();
				returnRecord.setInventstaffname(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getBinventcompid()), CommonTool.FormatString(returnRecord.getInventstaffid()),validatemsg));
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
	 public List<Dgoodsinventory> loadDgoodsinventorys(String strCompId,String strBillId)
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
			 String strSql=" select inventcompid,inventbillid,inventgoodsno,goodsname,inventunit,inventcount,curstockcount,discount,inventfrombarno,inventtobarno,inventcreateflag," +
			 		" inserunit,inserprice,outerunit,outerprice  " +
			 		" From  dgoodsinventory,goodsnameinfo where inventcompid='"+strCompId+"' and inventbillid='"+strBillId+"' " +
			 		" and  inventgoodsno=goodsno  ";
			 AnlyResultSet<List<Dgoodsinventory>> analysis = new AnlyResultSet<List<Dgoodsinventory>>()
				{
					public List<Dgoodsinventory> anlyResultSet(ResultSet rs)
					{
						List<Dgoodsinventory> returnValue = new ArrayList();
						Dgoodsinventory record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodsinventory();
								record.setId(new DgoodsinventoryId(CommonTool.FormatString(rs.getString("inventcompid")),CommonTool.FormatString(rs.getString("inventbillid")),0));
								record.setInventgoodsno(CommonTool.FormatString(rs.getString("inventgoodsno")));
								record.setInventgoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setInventunit(CommonTool.FormatString(rs.getString("inventunit")));
								record.setInventcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("inventcount"))));
								record.setCurstockcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("curstockcount"))));
								record.setDiscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("discount"))));
								record.setInventfrombarno(CommonTool.FormatString(rs.getString("inventfrombarno")));
								record.setInventtobarno(CommonTool.FormatString(rs.getString("inventtobarno")));
								record.setInventcreateflag(CommonTool.FormatInteger(rs.getInt("inventcreateflag")));
								record.setInserunit(CommonTool.FormatString(rs.getString("inserunit")));
								record.setOuterunit(CommonTool.FormatString(rs.getString("outerunit")));
								record.setInserprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("inserprice"))));
								record.setOuterprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("outerprice"))));
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
				return (List<Dgoodsinventory>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 //新增主档
	 public Mgoodsinventory addMastRecord()
	 {
		 Mgoodsinventory record=new Mgoodsinventory();
		 record.setId(new MgoodsinventoryId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mgoodsinventory", "inventbillid", "SP012")));
		 record.setInventdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setInventtime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setBinventcompid(CommonTool.getLoginInfo("COMPID"));
		 record.setBinventbillid(record.getId().getInventbillid());
		 record.setInvalid(0);
		 record.setInventflag(0);
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
								strendbarCode=strBarSeqno+""+(iMaxBarCode+iBarNum);
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
	
	
	
}

