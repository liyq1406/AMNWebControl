package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dcompallotmentinfo;
import com.amani.model.DcompallotmentinfoId;
import com.amani.model.Dgoodsallotmentinfo;
import com.amani.model.DgoodsallotmentinfoId;
import com.amani.model.Mgoodsallotmentinfo;
import com.amani.model.MgoodsallotmentinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class IC019Service extends AMN_ModuleService{

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
			List<Dgoodsallotmentinfo> lsDgoodsallotmentinfos=(List<Dgoodsallotmentinfo>)details;
			if(lsDgoodsallotmentinfos!=null && lsDgoodsallotmentinfos.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsallotmentinfos);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postInfo(Mgoodsallotmentinfo curMgoodsallotmentinfo,List<Dgoodsallotmentinfo> lsDgoodsallotmentinfo,List<Dcompallotmentinfo> lsDcompallotmentinfo)
	{
		try
		{
			this.amn_Dao.saveOrUpdate(curMgoodsallotmentinfo);
			String strSql=" delete dgoodsallotmentinfo where entrycompid='"+curMgoodsallotmentinfo.getId().getEntrycompid()+"' and entrybillid='"+curMgoodsallotmentinfo.getId().getEntrybillid()+"'  ";
			this.amn_Dao.executeSql(strSql);
			if(lsDgoodsallotmentinfo!=null && lsDgoodsallotmentinfo.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsallotmentinfo);
			}
			if(lsDcompallotmentinfo!=null && lsDcompallotmentinfo.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDcompallotmentinfo);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean afterPost(String strCompId,String strBillId,String strAllotmentCompId,String strReceviceId,int allotmenttype,List<Dcompallotmentinfo> lsDcompallotmentinfo)
	{
		try
		{
			if(CommonTool.FormatInteger(allotmenttype)==1)
			{
				String strOrderBillId=this.dataTool.loadBillIdByRule(strAllotmentCompId,"mgoodsorderinfo", "orderbillid", "SP012");
				String strSql=" insert mgoodsorderinfo(ordercompid,orderbillid,orderdate,ordertime,orderstaffid,orderstate,orderbilltype,storewareid)" +
						" values( '"+strAllotmentCompId+"','"+strOrderBillId+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+strReceviceId+"',1,1,'"+strAllotmentCompId+"001' )";
				strSql=strSql+" insert dgoodsorderinfo(ordercompid,orderbillid,orderseqno,ordergoodsno,ordergoodscount,ordergoodsunit,ordergoodsprice,ordergoodsamt,headstockcount,supplierno,headwareno,goodssource,ordergoodstype)" +
						"select '"+strAllotmentCompId+"','"+strOrderBillId+"',allotmentseqno,allotmentgoodsno,allotmentgoodscount,allotmentgoodsunit,allotmentgoodsprice,allotmentgoodsamt,headstockcount,supplierno,headwareno,goodssource,1 " +
								" from dgoodsallotmentinfo where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
				strSql=strSql+" update mgoodsallotmentinfo set apporderbillno ='"+strOrderBillId+"' where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"'   ";
				return this.amn_Dao.executeSql(strSql);
			}
			else
			{
				if(lsDcompallotmentinfo!=null && lsDcompallotmentinfo.size()>0)
				{
					String strSql="";
					String strOrderBillId="";
					String strTargetCompNo="";
					for(int i=0;i<lsDcompallotmentinfo.size();i++)
					{
						strTargetCompNo=CommonTool.FormatString(lsDcompallotmentinfo.get(i).getAllotmentcompno());
						if(!strTargetCompNo.equals(""))
						{
							strOrderBillId=this.dataTool.loadBillIdByRule(strTargetCompNo,"mgoodsorderinfo", "orderbillid", "SP012");
							strSql=strSql+" insert mgoodsorderinfo(ordercompid,orderbillid,orderdate,ordertime,orderstaffid,orderstate,orderbilltype,storewareid)" +
								" values( '"+strTargetCompNo+"','"+strOrderBillId+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+strTargetCompNo+"300',1,1,'"+strTargetCompNo+"001' )";
							strSql=strSql+" insert dgoodsorderinfo(ordercompid,orderbillid,orderseqno,ordergoodsno,ordergoodscount,ordergoodsunit,ordergoodsprice,ordergoodsamt,headstockcount,supplierno,headwareno,goodssource,ordergoodstype)" +
								"select '"+strTargetCompNo+"','"+strOrderBillId+"',allotmentseqno,allotmentgoodsno,allotmentgoodscount,allotmentgoodsunit,allotmentgoodsprice,allotmentgoodsamt,headstockcount,supplierno,headwareno,goodssource,1 " +
										" from dgoodsallotmentinfo where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
							strSql=strSql+" update dcompallotmentinfo set apporderbillno='"+strOrderBillId+"' where  entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' and allotmentcompno='"+strTargetCompNo+"' ";
						}
					}
					if(!strSql.equals(""))
						return this.amn_Dao.executeSql(strSql);
				}
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
	public List<Mgoodsallotmentinfo> loadMgoodsorderinfo(String strCompId,String strDate,String strBillId,int pageSize, int startRow)
	{
		try
		{
			String strSql="select  mgoodsallotmentinfo From Mgoodsallotmentinfo mgoodsallotmentinfo    " +
					" where entrycompid='"+strCompId+"' and (isnull(allotmentdate,'')='"+strDate+"' or '"+strDate+"'='' ) " +
					" and (isnull(entrybillid,'')='"+strBillId+"' or '"+strBillId+"'='' )   order by allotmentdate desc ";
			return (List<Mgoodsallotmentinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public Mgoodsallotmentinfo loadMgoodsorderinfoById(String strCompId,String strBillId)
	{
		try
		{
			String strSql="select  entrycompid,entrybillid,allotmentdate,allotmenttime,allotmenttaffid, " +
					"  allotmentcompid,recevicestaffid,apporderbillno,orderopationerid,orderopationdate,allotmenttype " +
					"  From  mgoodsallotmentinfo " +
					" where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"'  ";
			 AnlyResultSet<Mgoodsallotmentinfo> analysis = new AnlyResultSet<Mgoodsallotmentinfo>()
				{
					public Mgoodsallotmentinfo anlyResultSet(ResultSet rs)
					{
						Mgoodsallotmentinfo returnValue = new Mgoodsallotmentinfo();
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue.setId(new MgoodsallotmentinfoId(CommonTool.FormatString(rs.getString("entrycompid")),CommonTool.FormatString(rs.getString("entrybillid"))));
								returnValue.setBentrycompid(CommonTool.FormatString(rs.getString("entrycompid")));
								returnValue.setBentrybillid(CommonTool.FormatString(rs.getString("entrybillid")));
								returnValue.setAllotmenttype(CommonTool.FormatInteger(rs.getInt("allotmenttype")));
								returnValue.setAllotmentdate(CommonTool.getDateMask(rs.getString("allotmentdate")));
								returnValue.setAllotmenttime(CommonTool.getTimeMask(rs.getString("allotmenttime"),1));
								returnValue.setAllotmenttaffid(CommonTool.FormatString(rs.getString("allotmenttaffid")));
								returnValue.setAllotmentcompid(CommonTool.FormatString(rs.getString("allotmentcompid")));
								returnValue.setRecevicestaffid(CommonTool.FormatString(rs.getString("recevicestaffid")));
								returnValue.setApporderbillno(CommonTool.FormatString(rs.getString("apporderbillno")));
								returnValue.setOrderopationerid(CommonTool.FormatString(rs.getString("orderopationerid")));
								returnValue.setOrderopationdate(CommonTool.getDateMask(rs.getString("orderopationdate")));
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
				Mgoodsallotmentinfo returnRecord= (Mgoodsallotmentinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
				StringBuffer validatemsg=null;
				returnRecord.setBentrycompname(this.dataTool.loadCompNameById(CommonTool.FormatString(returnRecord.getBentrycompid())));
				if(CommonTool.FormatInteger(returnRecord.getAllotmenttype())==1)
				{
					returnRecord.setAllotmentcompname(this.dataTool.loadCompNameById(returnRecord.getAllotmentcompid()));
					validatemsg=new StringBuffer();
					returnRecord.setAllotmenttaffanme(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getBentrycompid()), CommonTool.FormatString(returnRecord.getAllotmenttaffid()),validatemsg));
					validatemsg=null;
				}
				validatemsg=new StringBuffer();
				returnRecord.setRecevicestaffname(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getAllotmentcompid()), CommonTool.FormatString(returnRecord.getRecevicestaffid()),validatemsg));
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
	 public List<Dgoodsallotmentinfo> loadDgoodsorderinfo(String strCompId,String strBillId)
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
			 String strSql=" select entrycompid,entrybillid,allotmentgoodsno,goodsname,allotmentgoodscount,allotmentgoodsunit,allotmentgoodsprice,allotmentgoodsamt,headstockcount,headwareno " +
			 		" From  dgoodsallotmentinfo,goodsnameinfo where entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' " +
			 		" and  allotmentgoodsno=goodsno  ";
			 AnlyResultSet<List<Dgoodsallotmentinfo>> analysis = new AnlyResultSet<List<Dgoodsallotmentinfo>>()
				{
					public List<Dgoodsallotmentinfo> anlyResultSet(ResultSet rs)
					{
						List<Dgoodsallotmentinfo> returnValue = new ArrayList();
						Dgoodsallotmentinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodsallotmentinfo();
								record.setId(new DgoodsallotmentinfoId(CommonTool.FormatString(rs.getString("entrycompid")),CommonTool.FormatString(rs.getString("entrybillid")),0));
								record.setAllotmentgoodsno(CommonTool.FormatString(rs.getString("allotmentgoodsno")));
								record.setAllotmentgoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setAllotmentgoodsunit(CommonTool.FormatString(rs.getString("allotmentgoodsunit")));
								record.setAllotmentgoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("allotmentgoodscount"))));
								record.setAllotmentgoodsprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("allotmentgoodsprice"))));
								record.setAllotmentgoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("allotmentgoodsamt"))));
								record.setHeadstockcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("headstockcount"))));
								record.setHeadwareno(CommonTool.FormatString(rs.getString("headwareno")));
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
				List<Dgoodsallotmentinfo> ls= (List<Dgoodsallotmentinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return ls;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 public List<Dcompallotmentinfo> loadDcompallotmentinfo(String strCompId,String strBillId)
	 {
		 try
		 {
			 String strSql=" select entrycompid,entrybillid,allotmentcompno,apporderbillno,compname from dcompallotmentinfo a,companyinfo b " +
			 		" where a.allotmentcompno=b.compno and entrycompid='"+strCompId+"' and entrybillid='"+strBillId+"' ";
		 AnlyResultSet<List<Dcompallotmentinfo>> analysis = new AnlyResultSet<List<Dcompallotmentinfo>>()
			{
				public List<Dcompallotmentinfo> anlyResultSet(ResultSet rs)
				{
					List<Dcompallotmentinfo> returnValue = new ArrayList();
					Dcompallotmentinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dcompallotmentinfo();
							record.setId(new DcompallotmentinfoId(CommonTool.FormatString(rs.getString("entrycompid")),CommonTool.FormatString(rs.getString("entrybillid")),0));
							record.setAllotmentcompno(CommonTool.FormatString(rs.getString("allotmentcompno")));
							record.setAllotmentcompname(CommonTool.FormatString(rs.getString("compname")));
							record.setApporderbillno(CommonTool.FormatString(rs.getString("apporderbillno")));
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
			List<Dcompallotmentinfo> ls= (List<Dcompallotmentinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
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
	 public Mgoodsallotmentinfo addMastRecord(String strCompId)
	 {
		 Mgoodsallotmentinfo record=new Mgoodsallotmentinfo();
		 StringBuffer entrybillid = new StringBuffer(this.dataTool.loadBillIdByRule(strCompId,"mgoodsallotmentinfo", "entrybillid", "SP012"));
		 record.setId(new MgoodsallotmentinfoId(strCompId, entrybillid.replace(11, 12, "1").toString()));//配发的单号生成规则后三位001改为101开始
		 record.setAllotmentdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setAllotmenttime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setBentrycompid(strCompId);
		 record.setAllotmenttaffid(CommonTool.getLoginInfo("USERID"));
		 record.setAllotmenttaffanme(CommonTool.getLoginInfo("USERNAME"));
		 record.setAllotmenttype(1);
		 record.setBentrybillid(record.getId().getEntrybillid());
		 return record;
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
}

