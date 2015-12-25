package com.amani.service.CardControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Cardstock;
import com.amani.model.Dcardallotment;
import com.amani.model.DcardallotmentId;
import com.amani.model.Mcardallotment;
import com.amani.model.McardallotmentId;
import com.amani.model.Mcardapponline;
import com.amani.model.McardapponlineId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
import com.amani.tools.SystemFinal;
@Service
public class CC003Service  extends AMN_ModuleService{
	@Autowired
	private DESPlus desPlus;
	public DESPlus getDesPlus() {
		return desPlus;
	}

	public void setDesPlus(DESPlus desPlus) {
		this.desPlus = desPlus;
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
			Mcardallotment record=(Mcardallotment)curMaster;
			if(record!=null && record.getId()!=null)
			{
				String strSql=" update mcardallotment set invalid=1 where callotcompid='"+record.getId().getCallotcompid()+"' and callotbillid='"+record.getId().getCallotbillid()+"' ";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		List<Dcardallotment> lsDcardallotment=(List<Dcardallotment>)details;
		if(lsDcardallotment!=null && lsDcardallotment.size()>0)
		{
			 this.amn_Dao.saveOrUpdateAll(lsDcardallotment);
		}
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		try
		{
			this.amn_Dao.saveOrUpdate(curMaster);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public List<Mcardallotment> loadMasterDateById(String strAppCompId,String strAppBillId)
	{
		try
		{
			String strSql=" select callotcompid,callotbillid,callotdate,callottime,callotempid,recevieempid,callotopationempid,callotopationdate,checkoutflag,checkoutdate,checkoutemp,cappbillid,cappcompid,callotwareid " +
					" from mcardallotment,compchaininfo " +
					" where curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=callotcompid and (cappcompid='"+strAppCompId+"' or '"+strAppCompId+"'='') and (cappbillid='"+strAppBillId+"' or '"+strAppBillId+"'='')" +
					"  and isnull(invalid,0)=0 order by callotdate desc ";
			AnlyResultSet<List<Mcardallotment>> analysis = new AnlyResultSet<List<Mcardallotment>>()
			{
				public List<Mcardallotment> anlyResultSet(ResultSet rs)
				{
					List<Mcardallotment> returnValue = new ArrayList();
					Mcardallotment record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mcardallotment();
							record.setId(new McardallotmentId(CommonTool.FormatString(rs.getString("callotcompid")),CommonTool.FormatString(rs.getString("callotbillid"))));
							record.setBcallotcompid(CommonTool.FormatString(rs.getString("callotcompid")));
							record.setBcallotbillid(CommonTool.FormatString(rs.getString("callotbillid")));
							record.setCallotdate(CommonTool.getDateMask(rs.getString("callotdate")));
							record.setCallottime(CommonTool.getTimeMask(rs.getString("callottime"), 1));
							record.setCallotempid(CommonTool.FormatString(rs.getString("callotempid")));
							record.setRecevieempid(CommonTool.FormatString(rs.getString("recevieempid")));
							record.setCallotopationempid(CommonTool.FormatString(rs.getString("callotopationempid")));
							record.setCallotopationdate(CommonTool.FormatString(rs.getString("callotopationdate")));
							record.setCheckoutflag(CommonTool.FormatInteger(rs.getInt("checkoutflag")));
							record.setCheckoutdate(CommonTool.getDateMask(rs.getString("checkoutdate")));
							record.setCheckoutemp(CommonTool.FormatString(rs.getString("checkoutemp")));
							record.setCappcompid(CommonTool.FormatString(rs.getString("cappcompid")));
							record.setCappbillid(CommonTool.FormatString(rs.getString("cappbillid")));
							record.setCallotwareid(CommonTool.FormatString(rs.getString("callotwareid")));
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
			return (List<Mcardallotment>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public Mcardallotment loadMcardallotmentByBillId(String strCompId,String strBillId)
	{
		String strSql=" select callotcompid,callotbillid,callotdate,callottime,callotempid,recevieempid,callotopationempid,callotopationdate,checkoutflag,checkoutdate,checkoutemp,cappbillid,cappcompid,callotwareid " +
		" from mcardallotment  where callotcompid='"+strCompId+"' and callotbillid='"+strBillId+"' and isnull(invalid,0)=0  ";

		AnlyResultSet<Mcardallotment> analysis = new AnlyResultSet<Mcardallotment>()
		{
			public Mcardallotment anlyResultSet(ResultSet rs)
			{
				Mcardallotment record=null;
				try
				{
					if(rs != null && rs.next()==true)
					{
						record=new Mcardallotment();
						record.setId(new McardallotmentId(CommonTool.FormatString(rs.getString("callotcompid")),CommonTool.FormatString(rs.getString("callotbillid"))));
						record.setBcallotcompid(CommonTool.FormatString(rs.getString("callotcompid")));
						record.setBcallotbillid(CommonTool.FormatString(rs.getString("callotbillid")));
						record.setCallotdate(CommonTool.getDateMask(rs.getString("callotdate")));
						record.setCallottime(CommonTool.getTimeMask(rs.getString("callottime"), 1));
						record.setCallotempid(CommonTool.FormatString(rs.getString("callotempid")));
						record.setCallotopationempid(CommonTool.FormatString(rs.getString("callotopationempid")));
						record.setRecevieempid(CommonTool.FormatString(rs.getString("recevieempid")));
						record.setCallotopationdate(CommonTool.FormatString(rs.getString("callotopationdate")));
						record.setCheckoutflag(CommonTool.FormatInteger(rs.getInt("checkoutflag")));
						record.setCheckoutdate(CommonTool.getDateMask(rs.getString("checkoutdate")));
						record.setCheckoutemp(CommonTool.FormatString(rs.getString("checkoutemp")));
						record.setCappcompid(CommonTool.FormatString(rs.getString("cappcompid")));
						record.setCappbillid(CommonTool.FormatString(rs.getString("cappbillid")));
						record.setCallotwareid(CommonTool.FormatString(rs.getString("callotwareid")));
						
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					record =  null;
				}
				return record;
			}
		};
		return (Mcardallotment)this.amn_Dao.executeQuery_ex(strSql,analysis);
	}
	
	public List<Dcardallotment> loadDetialById(String strCompId,String strBillId)
	{
		try
		{
			 String strModeId=this.dataTool.loadSysParam(strCompId,"SP063");
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
		     
			String strSql="select callotcompid,callotbillid,callotseqno,cardtypeid,cardtypename,cardnofrom,cardnoto,ccount,allotcount " +
					"  from  dcardallotment  left join cardtypeinfo on   cardtypeno=cardtypeid     and cardtypemodeid  in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')   " +
					"  where callotcompid='"+strCompId+"' and callotbillid='"+strBillId+"'" ;
			AnlyResultSet<List<Dcardallotment>> analysis = new AnlyResultSet<List<Dcardallotment>>()
			{
				public List<Dcardallotment> anlyResultSet(ResultSet rs)
				{
					List<Dcardallotment> returnValue=new ArrayList() ;
					Dcardallotment record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dcardallotment();
							record.setId(new DcardallotmentId(rs.getString("callotcompid"),rs.getString("callotbillid"),rs.getDouble("callotseqno")));
							record.setCardtypeid(rs.getString("cardtypeid"));
							record.setCardtypeName(rs.getString("cardtypename"));
				
							record.setCardnofrom(rs.getString("cardnofrom"));
							record.setCardnoto(rs.getString("cardnoto"));
							record.setCcount(new BigDecimal(rs.getDouble("ccount")));
							record.setAllotcount(new BigDecimal(rs.getDouble("allotcount")));
							returnValue.add(record);
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						record =  null;
					}
					return returnValue;
				}
			};
			return (List<Dcardallotment>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Dcardallotment> loadDetialByIdByAppBill(String strCompId,String strBillId)
	{
		try
		{
			 String strModeId=this.dataTool.loadSysParam(strCompId,"SP063");
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
		     
			String strSql="select cappcardtypeid,cardtypename,cappcount " +
					"  from  dcardapponline left join cardtypeinfo on   cardtypeno=cappcardtypeid     and cardtypemodeid  in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')   " +
					"  where cappcompid='"+strCompId+"' and cappcompbillid='"+strBillId+"'";
			AnlyResultSet<List<Dcardallotment>> analysis = new AnlyResultSet<List<Dcardallotment>>()
			{
				public List<Dcardallotment> anlyResultSet(ResultSet rs)
				{
					List<Dcardallotment> returnValue=new ArrayList() ;
					Dcardallotment record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dcardallotment();
							record.setCardtypeid(rs.getString("cappcardtypeid"));
							record.setCardtypeName(rs.getString("cardtypename"));
							record.setCcount(new BigDecimal(rs.getDouble("cappcount")));
							returnValue.add(record);
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						record =  null;
					}
					return returnValue;
				}
			};
			return (List<Dcardallotment>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	 //新增主档
	 public Mcardallotment addMastRecord(String strCompId)
	 {
		 Mcardallotment record=new Mcardallotment();
		 record.setId(new McardallotmentId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mcardallotment", "callotbillid", "SP012")));
		 record.setCallotdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setCallottime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setCallotopationempid(CommonTool.getLoginInfo("USERID"));
		 record.setCallotopationdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setBcallotbillid(record.getId().getCallotbillid());
		 record.setCallotwareid("09");
		 record.setBcallotcompid(strCompId);
		 return record;
	 }
	
	 public List<Mcardapponline> loadMcardapponlineByCompId(String strCompId)
	 {
		 try
		 {
			 String strSql=" select cappcompid,cappcompbillid,cappdate,capptime,cappempid from mcardapponline where cappcompid='"+strCompId+"' and  isnull(cappbillflag,0)=1 ";
			 AnlyResultSet<List<Mcardapponline>> analysis = new AnlyResultSet<List<Mcardapponline>>()
				{
					public List<Mcardapponline> anlyResultSet(ResultSet rs)
					{
						List<Mcardapponline> returnValue=new ArrayList() ;
						Mcardapponline record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Mcardapponline();
								record.setId(new McardapponlineId(rs.getString("cappcompid"),rs.getString("cappcompbillid")));
								record.setCappdate(CommonTool.getDateMask(rs.getString("cappdate")));
								record.setCapptime(CommonTool.getTimeMask(rs.getString("capptime"), 1));
								record.setCappempid(CommonTool.FormatString(rs.getString("cappempid")));
								record.setBcappcompid(rs.getString("cappcompid"));
								record.setBcappcompbillid(rs.getString("cappcompbillid"));
								returnValue.add(record);
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							record =  null;
						}
						return returnValue;
					}
				};
				return (List<Mcardapponline>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	
	 public List<Cardstock> loadCanAllotCardInfoBill(String strCompId,String strCardTypeId,String strWareId)
	 {
		 try
		 {
			 String strSql=" select rid,cardclass,cardfrom,cardto,ccount from cardstock where compid='"+strCompId+"' and  isnull(cardclass,'')='"+strCardTypeId+"' and storage='"+strWareId+"' ";
			 AnlyResultSet<List<Cardstock>> analysis = new AnlyResultSet<List<Cardstock>>()
				{
					public List<Cardstock> anlyResultSet(ResultSet rs)
					{
						List<Cardstock> returnValue=new ArrayList() ;
						Cardstock record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Cardstock();
								record.setRid(CommonTool.FormatInteger(rs.getInt("rid")));
								record.setCardclass(CommonTool.FormatString(rs.getString("cardclass")));
								record.setCardfrom(CommonTool.FormatString(rs.getString("cardfrom")));
								record.setCardto(CommonTool.FormatString(rs.getString("cardto")));
								record.setCcount(new BigDecimal(rs.getDouble("ccount")));
								returnValue.add(record);
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							record =  null;
						}
						return returnValue;
					}
				};
				return (List<Cardstock>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 
	 public List<Cardstock> loadCardstockInfo()
	 {
		 try
		 {
			 String strModeId=this.dataTool.loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP063");
		     String strFristModeId="";//第一级模板Id
			 String strSecondModeId="";//第2级模板ID
			 String strThirthModeId="";//第三级模板Id
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.dataTool.loadCompLvl(CommonTool.getLoginInfo("COMPID"));
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
		     
			 String strSql=" select compid,cardclass,cardtypename,warehousename,ccount=sum(ccount) from cardstock a,cardtypeinfo b,compwarehouse c " +
			 		"  where a.compid='"+CommonTool.getLoginInfo("COMPID")+"' " +
			 		" and b.cardtypemodeid  in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and b.cardtypeno=a.cardclass " +
			 		" and a.compid=c.compno and a.storage=c.warehouseno  group by compid,cardclass,cardtypename,warehousename ";
			 AnlyResultSet<List<Cardstock>> analysis = new AnlyResultSet<List<Cardstock>>()
				{
					public List<Cardstock> anlyResultSet(ResultSet rs)
					{
						List<Cardstock> returnValue=new ArrayList() ;
						Cardstock record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Cardstock();
								record.setCompid(CommonTool.FormatString(rs.getString("compid")));
								record.setCardclass(CommonTool.FormatString(rs.getString("cardclass")));
								record.setCardclassname(CommonTool.FormatString(rs.getString("cardtypename")));
								record.setStoragename(CommonTool.FormatString(rs.getString("warehousename")));
								record.setCcount(new BigDecimal(rs.getDouble("ccount")));
								returnValue.add(record);
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							record =  null;
						}
						return returnValue;
					}
				};
				return (List<Cardstock>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 public boolean postCardIntoStore(String strCompId,String strBillId,String strAppCompId,String strAppBillId,int iConfirm)
	 {
			String strSql="";
			if(iConfirm==1)
			{
				strSql="    insert into cardstockchange(changecompid,changetype,changebill,changeseqno,cardtype,changecardfromno,changecardtono,changecount,changeprice,changeamt,changedate,changeware) " +
						"  select b.callotcompid,2,b.callotbillid,callotseqno,cardtypeid,cardnofrom,cardnoto,allotcount, 0,0,'"+CommonTool.getCurrDate()+"',callotwareid  " +
						"   from mcardallotment a,dcardallotment b" +
						"  where a.callotcompid = b.callotcompid   and a.callotbillid = b.callotbillid and a.callotcompid = '"+strCompId+"' and a.callotbillid = '"+strBillId+"'   ";
				strSql=strSql+"  update mcardapponline set cappbillflag=2 where cappcompid='"+strAppCompId+"' and cappcompbillid='"+strAppBillId+"' ";
				strSql=strSql+"exec upg_handle_card_allot '"+strCompId+"','"+strBillId+"'";
				strSql=strSql+ "execute upg_allotcard_createcardno "+CommonTool.quotedStr(strCompId)+" , "+CommonTool.quotedStr(strAppCompId)+" , "+CommonTool.quotedStr(strBillId);
			}
			else
			{
				strSql=" delete cardstockchange where changecompid='"+strCompId+"' and fdc02c = '"+strBillId+"' and changetype='2'  ";
				strSql=strSql+"  update mcardapponline set cappbillflag=1 where cappcompid='"+strAppCompId+"' and cappcompbillid='"+strAppBillId+"' ";
			}
			try 
			{
				this.amn_Dao.executeSql(strSql);
				return true;
			} 
			catch(Exception ex)
			{
				ex.printStackTrace();
		    	return false;
			}
	 }
	  
	  
	 public List<Dcardallotment> loadDetialBySearch(String strCompId,String strCardType)
		{
			try
			{
				 String strModeId=this.dataTool.loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP063");
			     String strFristModeId="";//第一级模板Id
				 String strSecondModeId="";//第2级模板ID
				 String strThirthModeId="";//第三级模板Id
				 //先定位模板门店的连锁级别(暂时支持4级连锁)
				 int compLvl=this.dataTool.loadCompLvl(CommonTool.getLoginInfo("COMPID"));
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
			     
				String strSql="select cappcompid,cardtypeid,cardtypename,cardnofrom,cardnoto,ccount,allotcount " +
						"  from  mcardallotment a,dcardallotment b,cardtypeinfo" +
						"  where a.callotcompid=b.callotcompid and a.callotbillid=b.callotbillid " +
						"  and (a.cappcompid='"+strCompId+"' or '"+strCompId+"'='')" +
						"  and (b.cardtypeid='"+strCardType+"' or '"+strCardType+"'='') " +
						"  and cardtypemodeid  in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and cardtypeno=cardtypeid  ";
				AnlyResultSet<List<Dcardallotment>> analysis = new AnlyResultSet<List<Dcardallotment>>()
				{
					public List<Dcardallotment> anlyResultSet(ResultSet rs)
					{
						List<Dcardallotment> returnValue=new ArrayList() ;
						Dcardallotment record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dcardallotment();
								record.setAppCompId(rs.getString("cappcompid"));
								record.setCardtypeid(rs.getString("cardtypeid"));
								record.setCardtypeName(rs.getString("cardtypename"));
								record.setCardnofrom(rs.getString("cardnofrom"));
								record.setCardnoto(rs.getString("cardnoto"));
								record.setCcount(new BigDecimal(rs.getDouble("ccount")));
								record.setAllotcount(new BigDecimal(rs.getDouble("allotcount")));
								returnValue.add(record);
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							record =  null;
						}
						return returnValue;
					}
				};
				return (List<Dcardallotment>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}	  
	    
}
