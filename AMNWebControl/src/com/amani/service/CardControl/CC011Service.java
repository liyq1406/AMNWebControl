package com.amani.service.CardControl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.SpadDconsumeInfo;
import com.amani.bean.SpadMconsumeInfo;
import com.amani.bean.TradeAnalysis;
import com.amani.model.ActivateCard;
import com.amani.model.Cardproaccount;
import com.amani.model.CardproaccountId;
import com.amani.model.Cardspecialcost;
import com.amani.model.Cardtransactionhistory;
import com.amani.model.Companyinfo;
import com.amani.model.Corpsbuyinfo;
import com.amani.model.Dconsumeinfo;
import com.amani.model.Dmedicalcare;
import com.amani.model.Dnointernalcardinfo;
import com.amani.model.Dpayinfo;
import com.amani.model.Mconsumeinfo;
import com.amani.model.MconsumeinfoId;
import com.amani.model.Memberinfo;
import com.amani.model.Mpackageinfo;
import com.amani.model.Nointernalcardinfo;
import com.amani.model.Orders;
import com.amani.model.OrdersEmpinfo;
import com.amani.model.OredersPrjinfo;
import com.amani.model.Wxbandcard;
import com.amani.model.Yearcarddetal;
import com.amani.model.Yearcardinof;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
import com.amani.tools.SysSendMsg;
@Service
public class CC011Service  extends AMN_ModuleService{
	@Autowired
	private DESPlus desPlus;
	public DESPlus getDesPlus() {
		return desPlus;
	}

	public void setDesPlus(DESPlus desPlus) {
		this.desPlus = desPlus;
	}

	protected static SysSendMsg sysSendMsg;
	public SysSendMsg getSysSendMsg() {
		return sysSendMsg;
	}
	public void setSysSendMsg(SysSendMsg sysSendMsg) {
		this.sysSendMsg = sysSendMsg;
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		try
		{
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	
	public boolean postConsum(Mconsumeinfo curMconsumeinfo,List<Dconsumeinfo> lsDconsumeinfos,List<Dpayinfo> lsDpayinfos,List<Dnointernalcardinfo> lsDnointernalcardinfo)
	{
		try
		{
			this.amn_Dao.saveOrUpdate(curMconsumeinfo);
			if(lsDconsumeinfos!=null && lsDconsumeinfos.size()>0)
			{
				this.amn_Dao.saveOrUpdateAll(lsDconsumeinfos);
				int handStockFlag=0;
				String strSqlHistory="";
				for(int i=0;i<lsDconsumeinfos.size();i++)
				{
					if(CommonTool.FormatInteger(lsDconsumeinfos.get(i).getId().getCsinfotype())==2)//销售产品
					{
						handStockFlag=1;
						strSqlHistory=strSqlHistory+" insert dgoodsstockinfo(changecompid,changetype,changebillno,changeseqno,changegoodsno,standunit,standcount,standprice,producedate,changeunit,changecount,changeprice,changeamt)" +
							 " values(	'"+CommonTool.FormatString(curMconsumeinfo.getId().getCscompid())+"','3'," +
							 			" '"+CommonTool.FormatString(curMconsumeinfo.getId().getCsbillid())+"'," +
					 				" "+CommonTool.FormatDouble(lsDconsumeinfos.get(i).getId().getCsseqno())+"," +
					 				" '"+CommonTool.FormatString(lsDconsumeinfos.get(i).getCsitemno())+"'," +
					 				" '"+CommonTool.FormatString(lsDconsumeinfos.get(i).getCsitemunit())+"'," +
					 				" "+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCsitemcount())+" ," +
					 				" "+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCsdisprice())+" ," +
					 				" '"+CommonTool.getCurrDate()+"'," +
					 				" '"+CommonTool.FormatString(lsDconsumeinfos.get(i).getCsitemunit())+"'," +
					 				" "+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCsitemcount())+" ," +
					 				" "+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCsdisprice())+" ," +
					 				" "+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCsitemamt()) +" ) ";
						if(CommonTool.FormatString(this.dataTool.loadSysParam(curMconsumeinfo.getId().getCscompid(), "SP016")).equals("1"))
						{
							if(!CommonTool.FormatString(lsDconsumeinfos.get(i).getGoodsbarno()).equals(""))
							{
								strSqlHistory=strSqlHistory+" update dgoodsbarinfo set barnostate=3 ,costdate='"+CommonTool.getCurrDate()+"',costbillo='"+curMconsumeinfo.getId().getCsbillid()+"',coststore='"+curMconsumeinfo.getId().getCscompid()+"' where goodsbarno='"+CommonTool.FormatString(lsDconsumeinfos.get(i).getGoodsbarno())+"' ";
							}
						}

					}else if(CommonTool.FormatInteger(lsDconsumeinfos.get(i).getCostpricetype())==7){//7 合作项目医疗消费
						strSqlHistory += "update dmedicalcare set lastamt=lastamt-"+ lsDconsumeinfos.get(i).getCsitemamt() +", lastcount=lastcount-"+ lsDconsumeinfos.get(i).getCsitemcount()
								+" where compno='"+ lsDconsumeinfos.get(i).getBcscompid()+"' and onlyno='"+ lsDconsumeinfos.get(i).getYearinid() +"' ";
					}
				}
				if(handStockFlag==1)
				{
					strSqlHistory=strSqlHistory+" insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)" +
					" values('"+CommonTool.FormatString(curMconsumeinfo.getId().getCscompid())+"','3', " +
							" '"+CommonTool.FormatString(curMconsumeinfo.getId().getCsbillid())+"'," +
							" '"+CommonTool.FormatString(curMconsumeinfo.getCsdate())+"'," +
							" '"+CommonTool.FormatString(curMconsumeinfo.getCsendtime())+"'," +
							" '"+CommonTool.FormatString(CommonTool.FormatString(this.getDataTool().loadSysParam(curMconsumeinfo.getId().getCscompid(), "SP013")))+"'," +
							" "+CommonTool.FormatInteger(1)+"," +
							" '"+CommonTool.FormatString(CommonTool.getLoginInfo("USERID"))+"',1) ";
			
				}
				if(!strSqlHistory.equals(""))
				{
					this.amn_Dao.executeSql(strSqlHistory);
				}
			}
			if(lsDpayinfos!=null && lsDpayinfos.size()>0)
			{
				this.amn_Dao.saveOrUpdateAll(lsDpayinfos);
			}
			if( !CommonTool.FormatString(curMconsumeinfo.getDiyongcardno()).equals("")
			 || !CommonTool.FormatString(curMconsumeinfo.getTiaomacardno()).equals("") )
			{
				if(lsDnointernalcardinfo!=null && lsDnointernalcardinfo.size()>0)
				{
					String strSql="";
					for(int i=0;i<lsDnointernalcardinfo.size();i++)
					{
						strSql=strSql+" update dnointernalcardinfo set costbillno='"+curMconsumeinfo.getId().getCsbillid()+"' ,usecount=isnull(usecount,0)+"+CommonTool.FormatBigDecimal(lsDnointernalcardinfo.get(i).getCostcount())+" ,lastcount=isnull(lastcount,0)-"+CommonTool.FormatBigDecimal(lsDnointernalcardinfo.get(i).getCostcount())+"," +
								" useamt=isnull(useamt,0)+"+CommonTool.FormatBigDecimal(lsDnointernalcardinfo.get(i).getCostamt())+" ,lastamt=isnull(lastamt,0)-"+CommonTool.FormatBigDecimal(lsDnointernalcardinfo.get(i).getCostamt())+"" +
								" where cardno='"+CommonTool.FormatString(lsDnointernalcardinfo.get(i).getBcardno())+"' and  ineritemno='"+CommonTool.FormatString(lsDnointernalcardinfo.get(i).getIneritemno())+"' and seqno="+CommonTool.FormatDouble(lsDnointernalcardinfo.get(i).getBseqno())+" ";
					}
					if(!strSql.equals(""))
					{
						this.amn_Dao.executeSql(strSql);
					}
				}
			}
			if(!CommonTool.FormatString(curMconsumeinfo.getDiyongcardno()).equals(""))
			{
				this.amn_Dao.executeSql("update nointernalcardinfo set cardstate=2 ,usedate='"+CommonTool.getCurrDate()+"' where cardno='"+curMconsumeinfo.getDiyongcardno()+"' ");
			}
			/*if(CommonTool.checkStr(curMconsumeinfo.getRandomno()))
			{
				this.amn_Dao.executeSql("insert into billstate(compid,billid,state,randomno,createdate) values('"+curMconsumeinfo.getId().getCscompid()+"','"+curMconsumeinfo.getId().getCsbillid()+"',0,'"+curMconsumeinfo.getRandomno()+"','"+CommonTool.getCurrDate()+"')");
			}*/
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public Mconsumeinfo addMastRecord()
	{
		try
		{
			Mconsumeinfo record=new Mconsumeinfo();
			record.setId(new MconsumeinfoId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mconsumeinfo", "csbillid", "SP007")));
			record.setBackcsflag(0);
			record.setBackcsbillid("");
			record.setCscardno("散客");
			record.setCskeyno("");
			record.setCsdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
			record.setCsstarttime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 2));
			record.setCsersex(0);
			record.setCsertype(0);
			record.setCsercount(1);
			record.setCscurkeepamt(new BigDecimal(0));
			record.setCscurdepamt(new BigDecimal(0));
			record.setCsopationdate(CommonTool.getCurrDate());
			record.setCsopationerid(CommonTool.getLoginInfo("USERID"));
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Dconsumeinfo addDconsumeinfo()
	{
		try
		{
			Dconsumeinfo record=new Dconsumeinfo();
			record.setBcsinfotype(1);
			record.setCsitemno("");
			record.setCsitemname("");
			record.setCsitemunit("");
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Dpayinfo addDpayinfo()
	{
		try
		{
			Dpayinfo record=new Dpayinfo();
			record.setPaymode("1");
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public Mconsumeinfo loadMconsumeinfoByBill(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" select mconsumeinfo From Mconsumeinfo mconsumeinfo,Compchaininfo compchaininfo " +
					"  where cscompid=relationcomp and curcomp='"+strCompId+"' and csbillid='"+strBillId+"' ";
			List<Mconsumeinfo> lsInfo=this.amn_Dao.findByHql(strSql);
			if(lsInfo!=null && lsInfo.size()>0)
			{
				return lsInfo.get(0);
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public TradeAnalysis loadTradeAnalysisByDate(String strCompId,String strDate)
	{
		try
		{
			String strSql=" exec upg_compute_comp_tradedata '"+strCompId+"','"+strDate+"','"+strDate+"' ";
			AnlyResultSet<TradeAnalysis> analysis = new AnlyResultSet<TradeAnalysis>()
			{
				public TradeAnalysis anlyResultSet(ResultSet rs)
				{
					TradeAnalysis record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new TradeAnalysis();
							record.setCompid(CommonTool.FormatString(rs.getString("compid")));
							record.setCompname(CommonTool.FormatString(rs.getString("compname")));
							record.setTradecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradecashamt")))));
							record.setTradebankamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradebankamt")))));
							record.setTradefingeramt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradefingeramt")))));
							record.setTradeokcardamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradeokcardamt")))));
							record.setTradetgcardamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradetgcardamt")))));
							record.setTradeczcardamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradeczcardamt")))));
							record.setTradelccardamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradelccardamt")))));
							record.setTradecashdyamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradecashdyamt")))));
							record.setTradeprojdyamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradeprojdyamt")))));
							record.setTradetmcardamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradetmcardamt")))));
							record.setTrademrfakeamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("trademrfakeamt")))));
							record.setTrademffakeamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("trademffakeamt")))));
							record.setTradetrfakeamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradetrfakeamt")))));
							record.setTradefgfakeamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradefgfakeamt")))));
							record.setTradetotalamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tradetotalamt")))));
							record.setTraderealtotalamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("traderealtotalamt")))));
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
			TradeAnalysis record=  (TradeAnalysis)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	 public boolean validateManagerPass(String strManagerNo,String strManagerPass)
	 {
		 String strSql=" select 1 from staffinfo where staffno='"+strManagerNo+"' and staffpassword='"+strManagerPass+"' and isnull(staffpassword,'')<>'' ";
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
						returnValue =  false;
					}
					return returnValue;
				}
			};
			return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
	 }
	 
	 public boolean validateTMCardPass(String strCardNo,String strManagerPass)
	 {
		 String strSql=" select 1 from nointernalcardinfo where cardno='"+strCardNo+"' and cardpassward='"+strManagerPass+"' and isnull(cardtype,0)=2 ";
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
						returnValue =  false;
					}
					return returnValue;
				}
			};
			return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
	 }
	 
	 
	 public boolean validateDyqCardPass(String strCardNo,String strManagerPass)
	 {
		 String strSql=" select 1 from nointernalcardinfo where cardno='"+strCardNo+"' and cardpassward='"+strManagerPass+"' and isnull(cardtype,0)=1 ";
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
						returnValue =  false;
					}
					return returnValue;
				}
			};
			return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
	 }
	 
	public List<Dconsumeinfo> loadDconsumeinfoByBillId(String strCompId,String strBillId,StringBuffer dProjectAmt,StringBuffer dGoodsAmt)
	{
		try
		{
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.dataTool.loadCompLvl(strCompId);
		   
			 String strModeId_p=this.dataTool.loadSysParam(strCompId,"SP059");
		     String strFristModeId_p="";//第一级模板Id
			 String strSecondModeId_p="";//第2级模板ID
			 String strThirthModeId_p="";//第三级模板Id
			 if(compLvl==2)
			 {
				strFristModeId_p=this.dataTool.loadparentModeId(strModeId_p);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId_p=this.dataTool.loadparentModeId(strModeId_p);
				if(!strSecondModeId_p.equals(""))
					strFristModeId_p=this.dataTool.loadparentModeId(strSecondModeId_p);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId_p=this.dataTool.loadparentModeId(strModeId_p);
				if(!strThirthModeId_p.equals(""))
					strSecondModeId_p=this.dataTool.loadparentModeId(strThirthModeId_p);
				if(!strSecondModeId_p.equals(""))
					strFristModeId_p=this.dataTool.loadparentModeId(strSecondModeId_p);
			 }
			 String strSql=" select csinfotype,csitemno,csitemname=prjname,csitemunit,csitemcount,csunitprice,csdiscount,csdisprice,csitemamt,cspaymode," +
			 		" csfirstsaler,csfirsttype,csfirstshare,cssecondsaler,cssecondtype,cssecondshare,csthirdsaler,csthirdtype,csthirdshare,csortherpayid,csitemstate,hairRecommendEmpId,hairRecommendEmpInid " +
			 		" From  dconsumeinfo,projectinfo where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(csinfotype,0)=1 " +
			 		" and prjmodeId  in ('"+strModeId_p+"','"+strFristModeId_p+"','"+strSecondModeId_p+"','"+strThirthModeId_p+"') and prjno=csitemno  ";
			 
			
			 String strModeId_g=this.dataTool.loadSysParam(strCompId,"SP061");
		     String strFristModeId_g="";//第一级模板Id
			 String strSecondModeId_g="";//第2级模板ID
			 String strThirthModeId_g="";//第三级模板Id
			
		     if(compLvl==2)
			 {
				strFristModeId_g=this.dataTool.loadparentModeId(strModeId_g);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId_g=this.dataTool.loadparentModeId(strModeId_g);
				if(!strSecondModeId_g.equals(""))
					strFristModeId_g=this.dataTool.loadparentModeId(strSecondModeId_g);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId_g=this.dataTool.loadparentModeId(strModeId_g);
				if(!strThirthModeId_g.equals(""))
					strSecondModeId_g=this.dataTool.loadparentModeId(strThirthModeId_g);
				if(!strSecondModeId_g.equals(""))
					strFristModeId_g=this.dataTool.loadparentModeId(strSecondModeId_g);
			 }
			 strSql=strSql+" Union all select csinfotype,csitemno,csitemname=goodsname,csitemunit,csitemcount,csunitprice,csdiscount,csdisprice,csitemamt,cspaymode," +
		 		" csfirstsaler,csfirsttype,csfirstshare,cssecondsaler,cssecondtype,cssecondshare,csthirdsaler,csthirdtype,csthirdshare,csortherpayid,csitemstate,hairRecommendEmpId,hairRecommendEmpInid " +
		 		" From  dconsumeinfo,goodsinfo where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(csinfotype,0)=2 " +
		 		" and goodsmodeid  in ('"+strModeId_g+"','"+strFristModeId_g+"','"+strSecondModeId_g+"','"+strThirthModeId_g+"') and goodsno=csitemno  ";
		 
			 	AnlyResultSet<List<Dconsumeinfo>> analysis = new AnlyResultSet<List<Dconsumeinfo>>()
				{
					public List<Dconsumeinfo> anlyResultSet(ResultSet rs)
					{
						List<Dconsumeinfo> returnValue = new ArrayList();
						Dconsumeinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dconsumeinfo();
								record.setBcsinfotype(CommonTool.FormatInteger(rs.getInt("csinfotype")));
								record.setCsitemno(CommonTool.FormatString(rs.getString("csitemno")));
								record.setCsitemname(CommonTool.FormatString(rs.getString("csitemname")));
								record.setCsitemunit(CommonTool.FormatString(rs.getString("csitemunit")));
								record.setCsitemcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemcount"))));
								record.setCsunitprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csunitprice"))));
								record.setCsdiscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csdiscount"))));
								record.setCsdisprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csdisprice"))));
								record.setCsitemamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemamt"))));
								record.setCspaymode(CommonTool.FormatString(rs.getString("cspaymode")));
								record.setCsfirstsaler(CommonTool.FormatString(rs.getString("csfirstsaler")));
								record.setCsfirsttype(CommonTool.FormatString(rs.getString("csfirsttype")));
								record.setCsfirstshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csfirstshare"))));
								record.setCssecondsaler(CommonTool.FormatString(rs.getString("cssecondsaler")));
								record.setCssecondtype(CommonTool.FormatString(rs.getString("cssecondtype")));
								record.setCssecondshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("cssecondshare"))));
								record.setCsthirdsaler(CommonTool.FormatString(rs.getString("csthirdsaler")));
								record.setCsthirdtype(CommonTool.FormatString(rs.getString("csthirdtype")));
								record.setCsthirdshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csthirdshare"))));
								record.setCsortherpayid(CommonTool.FormatString(rs.getString("csortherpayid")));
								record.setCsitemstate(CommonTool.FormatInteger(rs.getInt("csitemstate")));
								record.setHairRecommendEmpId(rs.getString("hairRecommendEmpId"));
								record.setHairRecommendEmpInid(rs.getString("hairRecommendEmpInid"));
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
				List<Dconsumeinfo> lsInfo=  (List<Dconsumeinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				double dPAmt=0.0; //项目总额
				double dGAmt=0;	 //产品总额
				if(lsInfo!=null && lsInfo.size()>0)
				{
					for(int i=0;i<lsInfo.size();i++)
					{
						if(lsInfo.get(i).getBcsinfotype()==1) //项目
						{
							dPAmt=dPAmt+lsInfo.get(i).getCsitemamt().doubleValue();
						}
						else
						{
							dGAmt=dGAmt+lsInfo.get(i).getCsitemamt().doubleValue();
						}
					}
				}
				dProjectAmt.append(dPAmt);
				dGoodsAmt.append(dGAmt);
				return lsInfo;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public List<Dpayinfo> loadDpayinfoByBill(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Dpayinfo dpayinfo  where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='SY' ";
			return (List<Dpayinfo>)this.amn_Dao.findByHql(strSql);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public boolean handConsumeByCard(String strCompId,String strBillId)
	{
		try
		{
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean afterPost(String strCompId,String strBillId,String strDate,int costType)
	{
		try
		{
			String strSql=" exec upg_handconsumbill_card '"+strCompId+"','"+strBillId+"','"+strDate+"',"+costType+" ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public Corpsbuyinfo loadCorpscardno(String strCardNo)
	{
		try
		{
			String strModeId=this.dataTool.loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP059");
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
			String strSql=" select corpscardno,corpspicno,prjname,corpsamt from corpsbuyinfo ,projectinfo, compchaininfo " +
					" where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')  and curcomp=  prisource and relationcomp='"+CommonTool.getLoginInfo("COMPID")+"' " +
					" and  prjno = corpspicno and corpscardno='"+strCardNo+"' and isnull(corpstype,1)=1 and isnull(corpssate,1)=1 ";
			AnlyResultSet<Corpsbuyinfo> analysis = new AnlyResultSet<Corpsbuyinfo>()
			{
				public Corpsbuyinfo anlyResultSet(ResultSet rs)
				{
					Corpsbuyinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Corpsbuyinfo();
							record.setCorpspicno(CommonTool.FormatString(rs.getString("corpspicno")));
							record.setCorpspicname(CommonTool.FormatString(rs.getString("prjname")));
							record.setCorpsamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("corpsamt")))));
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
			Corpsbuyinfo returnRecord=  (Corpsbuyinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return returnRecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Nointernalcardinfo loadNointernalcardinfo(String strCardNo,int iCardType)
	{
		
		String strSql=" select carduseflag,cardfaceamt,lastvalidate,uespassward,cardpassward from nointernalcardinfo " +
		" where  cardno='"+strCardNo+"' and isnull(cardtype,1)='"+iCardType+"' and isnull(cardstate,0)=1 ";
		
		//if(iCardType==1)//抵用券 涉及有效期问题
		//strSql=strSql+" and lastvalidate>='"+CommonTool.getCurrDate()+"' and  enabledate<='"+CommonTool.getCurrDate()+"' ";
		AnlyResultSet<Nointernalcardinfo> analysis = new AnlyResultSet<Nointernalcardinfo>()
		{
			public Nointernalcardinfo anlyResultSet(ResultSet rs)
			{
				Nointernalcardinfo record=null;
				try
				{
					while(rs != null && rs.next()==true)
					{
						record=new Nointernalcardinfo();
						record.setCarduseflag(CommonTool.FormatInteger(rs.getInt("carduseflag")));
						record.setCardfaceamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cardfaceamt")))));
						record.setLastvalidate(CommonTool.FormatString(rs.getString("lastvalidate")));
						record.setUespassward(CommonTool.FormatInteger(rs.getInt("uespassward")));
						record.setCardpassward(CommonTool.FormatString(rs.getString("cardpassward")));
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
		Nointernalcardinfo returnRecord=  (Nointernalcardinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return returnRecord;
	}
	
	public List<Dnointernalcardinfo> loadDnointernalcardinfo(String strCardNo )
	{
		String strModeId=this.dataTool.loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP059");
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
		String strSql=" select seqno,ineritemno,prjname,entrycount,usecount,lastcount,lastamt,entryremark,a.packageNo,ishz from dnointernalcardinfo a left join mpackageinfo b on(cardvesting=compid and a.packageNo=b.packageNo),projectinfo, compchaininfo " +
					" where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')  and curcomp=  prisource and relationcomp='"+CommonTool.getLoginInfo("COMPID")+"' " +
					" and  prjno = ineritemno and cardno='"+strCardNo+"' ";
		AnlyResultSet<List<Dnointernalcardinfo>> analysis = new AnlyResultSet<List<Dnointernalcardinfo>>()
		{
			public List<Dnointernalcardinfo> anlyResultSet(ResultSet rs)
			{
				List<Dnointernalcardinfo> returnvalue=new ArrayList();
				Dnointernalcardinfo record=null;
				try
				{
					while(rs != null && rs.next()==true)
					{
						record=new Dnointernalcardinfo();
						record.setIneritemno(CommonTool.FormatString(rs.getString("ineritemno")));
						record.setIneritemname(CommonTool.FormatString(rs.getString("prjname")));
						record.setEntrycount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("entrycount")))));
						record.setUsecount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("usecount")))));
						record.setLastcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("lastcount")))));
						record.setEntryremark(CommonTool.FormatString(rs.getString("entryremark")));
						record.setBseqno(CommonTool.FormatDouble(rs.getDouble("seqno")));
						record.setIshz(rs.getInt("ishz"));
						record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("lastamt")))));
						record.setPackageNo(rs.getString("packageNo"));
						returnvalue.add(record);
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					record =  null;
				}
				return returnvalue;
			}
		};
		List<Dnointernalcardinfo> returnRecord=  (List<Dnointernalcardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return returnRecord;
	}
	
	public BigDecimal loadCostRate(String strCompId,String strCardType,String strProject,int cardUseType)
	{
		
		String strSql = "select costrate from cardchangecostrate,projectnameinfo " +
				" where  prjno='"+strProject+"'   and  prjreporttype=projecttypeid " +
				"   and  compid ='"+strCompId+"'  and cardtypeno ='"+strCardType+"' " ;
		if(cardUseType==1) //储值账户
		{
			strSql=strSql+" and acounttypeno='2' ";
		}
		else if(cardUseType==2) //收购账户
		{
			strSql=strSql+" and acounttypeno='5' ";
		}
		else if(cardUseType==3) //积分账户
		{
			strSql=strSql+" and acounttypeno='3' ";
		}
		else if(cardUseType==4) //现金账户
		{
			strSql=strSql+" and acounttypeno='999' ";
		}
				//"  and startdate<='"+CommonTool.getCurrDate()+"' and enddate>='"+CommonTool.getCurrDate()+"' ";
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue =1;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("costrate"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  1;
				}
				if(returnValue==0)
					returnValue=1;
				return returnValue;
			}
		};
		BigDecimal costRate=new BigDecimal((Double)this.amn_Dao.executeQuery_ex(strSql,analysis));	
		analysis=null;
		return costRate;
	}
	
	public double loadCardPrjCostRate(String strCompId,String strPrjtype)
	{
		String strSql=" select costrate from cardratetocostrate where compid='"+strCompId+"' and projecttypeid='"+strPrjtype+"' and startdate<='"+CommonTool.getCurrDate()+"' and  enddate>='"+CommonTool.getCurrDate()+"' ";
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue =1;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("costrate"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  1;
				}
				if(returnValue==0)
					returnValue=1;
				return returnValue;
			}
		};
		Double costRate=(Double)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		return costRate;
	}
	
	
	
	public BigDecimal loadGoodsCostRate(String strCompId,String strCardType,String Goodsno,int cardUseType)
	{
		
		String strSql = "select costrate from cardcostgoodsrate,goodsnameinfo " +
				" where  goodsno='"+Goodsno+"'   and  goodspricetype=goodstypeid " +
				"   and  compid ='"+strCompId+"'  and cardtypeno ='"+strCardType+"' " ;
		if(cardUseType==1) //储值账户
		{
			strSql=strSql+" and acounttypeno='2' ";
		}
		else if(cardUseType==2) //收购账户
		{
			strSql=strSql+" and acounttypeno='5' ";
		}
		else if(cardUseType==3) //积分账户
		{
			strSql=strSql+" and acounttypeno='3' ";
		}
		else if(cardUseType==4) //现金账户
		{
			strSql=strSql+" and acounttypeno='999' ";
		}
				//"  and startdate<='"+CommonTool.getCurrDate()+"' and enddate>='"+CommonTool.getCurrDate()+"' ";
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue =1;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("costrate"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  1;
				}
				if(returnValue==0)
					returnValue=1;
				return returnValue;
			}
		};
		BigDecimal costRate=new BigDecimal((Double)this.amn_Dao.executeQuery_ex(strSql,analysis));	
		analysis=null;
		return costRate;
	}
	
	public BigDecimal loadCostRateByPayCode(String strCompId,String strCardTypeNo ,String strProject,String strPayCode)
	{
		
		String strSql = "select costrate from cardchangecostrate,projectnameinfo,sysaccountforpaymode " +
				" where  prjno='"+strProject+"'   and  prjreporttype=projecttypeid " +
				"   and  compid ='"+strCompId+"'  and cardtypeno ='"+strCardTypeNo+"' and acounttypeno=accounttype and paymode='"+strPayCode+"' " ;
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue =1;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("costrate"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  1;
				}
				if(returnValue==0)
					returnValue=1;
				return returnValue;
			}
		};
		BigDecimal costRate=new BigDecimal((Double)this.amn_Dao.executeQuery_ex(strSql,analysis));	
		analysis=null;
		return costRate;
	}
	//查看门店促销项目
	public BigDecimal loadCostPrice(String strCompId,String strCardType,String strProject)
	{
		
		String strSql = " select promotionsvalue from promotionsinfo,compchaininfo " +
				"	where curcomp=promotionsstore and relationcomp='"+strCompId+"' and promotionscode='"+strProject+"' " +
				" and isnull(promotionstype,0)=1 and isnull(promotionsstate,0)=1 " +
				" and startdate<='"+CommonTool.getCurrDate()+"' and enddate>='"+CommonTool.getCurrDate()+"' ";
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue =0;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("promotionsvalue"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  0;
				}
				if(returnValue==0)
					returnValue=0;
				return returnValue;
			}
		};
		BigDecimal costRate=new BigDecimal((Double)this.amn_Dao.executeQuery_ex(strSql,analysis));	
		analysis=null;
		return costRate;
	}
	
	public Cardspecialcost loadSpecialPrice(String strCardNo)
	{
		try
		{
			String strSql=" select cardno,costxc1,costxc2,costxc3,costxc4,costxc5,costxc6,costxc7,costxc8,costxc9" +
					"  from cardspecialcost where cardno='"+strCardNo+"' ";
			AnlyResultSet<Cardspecialcost> analysis = new AnlyResultSet<Cardspecialcost>()
			{
				public Cardspecialcost anlyResultSet(ResultSet rs)
				{
					Cardspecialcost returnValue = null;
					try
					{
					if(rs != null && rs.next()==true)
					{
						returnValue=new Cardspecialcost();
						returnValue.setCardno(CommonTool.FormatString(rs.getString("cardno")));
						returnValue.setCostxc1(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costxc1")))));
						returnValue.setCostxc2(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costxc2")))));
						returnValue.setCostxc3(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costxc3")))));
						returnValue.setCostxc4(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costxc4")))));
						returnValue.setCostxc5(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costxc5")))));
						returnValue.setCostxc6(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costxc6")))));
						returnValue.setCostxc7(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costxc7")))));
						returnValue.setCostxc8(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costxc8")))));
						returnValue.setCostxc9(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("costxc9")))));
					}
					else
					{
						returnValue =  null;
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
			Cardspecialcost record= (Cardspecialcost)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	//获取PAD开单主档信息
	public List<SpadMconsumeInfo> ladSpadMastInfo()
	{
		try
		{
			String strSql=" select brachcode,SMALL_NO,CUSTOM,STATUS,CDATE,SUMMARY  from spadMconsumeInfo " +
					" where brachcode='"+CommonTool.getLoginInfo("COMPID")+"' and  isnull(STATUS,0)=0 ";
			AnlyResultSet<List<SpadMconsumeInfo> > analysis = new AnlyResultSet<List<SpadMconsumeInfo> >()
			{
				public List<SpadMconsumeInfo>  anlyResultSet(ResultSet rs)
				{
					List<SpadMconsumeInfo>  returnValue = new ArrayList();
					SpadMconsumeInfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new SpadMconsumeInfo();
							record.setBrachcode(CommonTool.FormatString(rs.getString("brachcode")));
							record.setSmallno(CommonTool.FormatString(rs.getString("SMALL_NO")));
							record.setCustom(CommonTool.FormatString(rs.getString("CUSTOM")));
							record.setStatus(CommonTool.FormatInteger(rs.getInt("STATUS")));
							record.setCdate(CommonTool.FormatString(rs.getString("CDATE")));
							record.setSummary(CommonTool.FormatString(rs.getString("SUMMARY")));
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
			List<SpadMconsumeInfo>  lsrecord= (List<SpadMconsumeInfo> )this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return lsrecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取PAD开单明细信息
	public List<SpadDconsumeInfo> ladSpadDetialtInfo(String strBillId)
	{
		try
		{
			String strSql=" select brachcode,SMALL_NO,PRICE,AMOUNT,SORTID,CODE,PRODUCT,EMPLOYEE_NO,EMPLOYEE_NO2,EMPLOYEE_NO3,ISNEW,ISNEW2,ISNEW3" +
					"  from spadDconsumeInfo " +
					" where brachcode='"+CommonTool.getLoginInfo("COMPID")+"' and  SMALL_NO='"+strBillId+"'  and isnull(SORTID,0) in (0,1) and isnull(ISDELETED,0)=0 and isnull(CODE,'')<>'' ";
			AnlyResultSet<List<SpadDconsumeInfo> > analysis = new AnlyResultSet<List<SpadDconsumeInfo> >()
			{
				public List<SpadDconsumeInfo>  anlyResultSet(ResultSet rs)
				{
					List<SpadDconsumeInfo>  returnValue = new ArrayList();
					SpadDconsumeInfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new SpadDconsumeInfo();
							record.setBrachcode(CommonTool.FormatString(rs.getString("brachcode")));
							record.setSmallno(CommonTool.FormatString(rs.getString("SMALL_NO")));
							record.setSortid(CommonTool.FormatInteger(rs.getInt("SORTID")));
							record.setCode(CommonTool.FormatString(rs.getString("CODE")));
							record.setProduct(CommonTool.FormatString(rs.getString("PRODUCT")));
							record.setAmount(CommonTool.FormatString(rs.getString("AMOUNT")));
							record.setPrice(CommonTool.FormatString(rs.getString("PRICE")));
							if(!CommonTool.FormatString(rs.getString("EMPLOYEE_NO")).equals(""))
								record.setEmployeeno(CommonTool.getLoginInfo("COMPID")+CommonTool.FormatString(rs.getString("EMPLOYEE_NO")));
							if(!CommonTool.FormatString(rs.getString("EMPLOYEE_NO2")).equals(""))
								record.setEmployeeno2(CommonTool.getLoginInfo("COMPID")+CommonTool.FormatString(rs.getString("EMPLOYEE_NO2")));
							if(!CommonTool.FormatString(rs.getString("EMPLOYEE_NO3")).equals(""))
								record.setEmployeeno3(CommonTool.getLoginInfo("COMPID")+CommonTool.FormatString(rs.getString("EMPLOYEE_NO3")));
							record.setIsnew(CommonTool.FormatInteger(rs.getInt("ISNEW")));
							record.setIsnew2(CommonTool.FormatInteger(rs.getInt("ISNEW2")));
							record.setIsnew3(CommonTool.FormatInteger(rs.getInt("ISNEW3")));
							if(CommonTool.FormatInteger(rs.getInt("ISNEW"))==0)
								record.setIsnew(2);
							if(CommonTool.FormatInteger(rs.getInt("ISNEW2"))==0)
								record.setIsnew2(2);
							if(CommonTool.FormatInteger(rs.getInt("ISNEW3"))==0)
								record.setIsnew3(2);
							
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
			List<SpadDconsumeInfo>  lsrecord= (List<SpadDconsumeInfo> )this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return lsrecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public boolean handBackReviceCard(String strCardNo,String strCurBillId)
	{
		try
		{
			String strSql=" update cardinfo set cardstate=10 where  cardno='"+strCardNo+"' ";
			strSql=strSql+" declare @maxseqno float ";
			strSql=strSql+" select @maxseqno=max(changeseqno) from cardchangehistory where changecardno='"+strCardNo+"' ";
			strSql=strSql+" insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate)" +
					" values('"+CommonTool.getLoginInfo("COMPID")+"','"+strCardNo+"',isnull(@maxseqno,0)+1,5,'"+strCurBillId+"',4,10,'"+CommonTool.getCurrDate()+"') ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public double loadCurDateCostAmt(String strCardNo)
	{
		String strSql = " select tcsitemamt=sum(isnull(csitemamt,0)) " +
				" from  mconsumeinfo a with(nolock),dconsumeinfo b with(nolock)" +
				" where a.cscompid=b.cscompid and a.csbillid=b.csbillid" +
				" and a.financedate='"+CommonTool.getCurrDate()+"' and a.cscardno='"+strCardNo+"' and cspaymode='4' and  isnull(csinfotype,0)=1 ";
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue =0;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("tcsitemamt"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  0;
				}
				if(returnValue==0)
					returnValue=0;
				return returnValue;
			}
		};
		double cosAmt=(Double)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		return cosAmt;
	}
	
	
	public int loadCurDateCostCount(String strCardNo,String prjtype)
	{
		String strSql = " select tcsitemcount=sum(isnull(csitemcount,0))  " +
				" from  mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),projectnameinfo c with(nolock)" +
				" where a.cscompid=b.cscompid and a.csbillid=b.csbillid" +
				" and a.financedate='"+CommonTool.getCurrDate()+"' and a.cscardno='"+strCardNo+"' and cspaymode='9'" +
				"  and b.csitemno=c.prjno and c.prjtype='"+prjtype+"' ";
		AnlyResultSet<Integer> analysis = new AnlyResultSet<Integer>()
		{
			public Integer anlyResultSet(ResultSet rs)
			{
				int returnValue =0;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatInteger(rs.getInt("tcsitemcount"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  0;
				}
				if(returnValue==0)
					returnValue=0;
				return returnValue;
			}
		};
		int ccount=(Integer)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		return ccount;
	}
	
	public boolean validateMemberInfo(String strCardNo,String strMemberName,String strPhone,String strPcid)
	{
		try
		{
			String strSql="from Memberinfo where memberno='"+strCardNo+"' ";//and membername='"+strMemberName+"' and  (isnull(membermphone,'')='"+strPhone+"' or isnull(membertphone,'')='"+strPhone+"' or isnull(memberpaperworkno,'')='"+strPcid+"' ) ";
			List<Memberinfo> lsMember=amn_Dao.findByHql(strSql);
			if(lsMember==null || lsMember.size()<1)
			{
				return false;
			}
			else
			{
				Memberinfo memberinfo=lsMember.get(0);
				if(strMemberName.equals(memberinfo.getMembername()) && ((CommonTool.checkStr(memberinfo.getMembermphone()))==false && CommonTool.checkStr(memberinfo.getMemberpaperworkno())==false) || strPhone.equals(memberinfo.getMembermphone()) || strPcid.equals(memberinfo.getMemberpaperworkno()))
				{
					memberinfo.setMembermphone(strPhone);
					memberinfo.setMemberpaperworkno(strPcid);
					amn_Dao.executeSql("update memberinfo set membermphone='"+strPhone+"',memberpaperworkno='"+strPcid+"' where memberno='"+strCardNo+"'");
					return true;
				}
				else
				{
					return false;
				}
			}
			//boolean flag=(Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			//analysis=null;
			//return flag;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean handCardStateInfo(String strCardNo)
	{
		try
		{
			String strSql=" update cardinfo set cardstate=4 where cardno='"+strCardNo+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean validateActivityCardinfo(String strCardNo)
	{
		try
		{
			String strSql=" select 1 from activitycardinfo where cardno='"+strCardNo+"' and validatedate>='"+CommonTool.getCurrDate()+"' ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			{
				public Boolean anlyResultSet(ResultSet rs)
				{
					boolean returnValue =false;
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
						returnValue =  false;
					}
					return returnValue;
				}
			};
			boolean flag=(Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return flag;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean validateActivityCardState(String strCardNo)
	{
		try
		{
			String strSql=" select 1 from activitycardinfo where cardno='"+strCardNo+"' and isnull(expericeitemnoflag,0)=0 and isnull(salegoodsflag,0)=0 ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			{
				public Boolean anlyResultSet(ResultSet rs)
				{
					boolean returnValue =false;
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
						returnValue =  false;
					}
					return returnValue;
				}
			};
			boolean flag=(Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return flag;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean handActivtyCardState(String strCardNo,String strCsCompId,String strCsBillNo,String strCsPrjNo)
	{
		try
		{
			String strSql=" update activitycardinfo set expericeitemnoflag=1,expericecompno='"+strCsCompId+"',expericebillno='"+strCsBillNo+"',expericedate='"+CommonTool.getCurrDate()+"',expericeitemno='"+strCsPrjNo+"' " +
					" where cardno='"+strCardNo+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	
	public List<Dnointernalcardinfo> loadActivityPrj(String strCardNo )
	{
		String strModeId=this.dataTool.loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP059");
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
		String strSql=" select expericeitemno,prjname,expericeitemprice from dactivitycardforprj,projectinfo, compchaininfo " +
					" where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')  and curcomp=  prisource and relationcomp='"+CommonTool.getLoginInfo("COMPID")+"' " +
					" and  prjno = expericeitemno and cardno='"+strCardNo+"' ";
		AnlyResultSet<List<Dnointernalcardinfo>> analysis = new AnlyResultSet<List<Dnointernalcardinfo>>()
		{
			public List<Dnointernalcardinfo> anlyResultSet(ResultSet rs)
			{
				List<Dnointernalcardinfo> returnvalue=new ArrayList();
				Dnointernalcardinfo record=null;
				try
				{
					while(rs != null && rs.next()==true)
					{
						record=new Dnointernalcardinfo();
						record.setIneritemno(CommonTool.FormatString(rs.getString("expericeitemno")));
						record.setIneritemname(CommonTool.FormatString(rs.getString("prjname")));
						record.setLastcount(new BigDecimal(1));
						record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("expericeitemprice")))));
						returnvalue.add(record);
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					record =  null;
				}
				return returnvalue;
			}
		};
		List<Dnointernalcardinfo> returnRecord=  (List<Dnointernalcardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return returnRecord;
	}
	
	public double loadGoodsCostRate(String strCardNo,String strGoodsType)
	{
		String strSql = " select salegoodsrate from dactivitycardforgoods where cardno='"+strCardNo+"' and salegoodstype='"+strGoodsType+"' ";
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue =0;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("salegoodsrate"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  0;
				}
				if(returnValue==0)
					returnValue=0;
				return returnValue;
			}
		};
		double cosAmt=(Double)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		return cosAmt;
	}
	
	public double loadCardAccountBalance(String strCardNo,int accountType)
	{
		String strSql = " select accountbalance from cardaccount where cardno='"+strCardNo+"' and accounttype="+accountType+" ";
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue =0;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("accountbalance"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  0;
				}
				if(returnValue==0)
					returnValue=0;
				return returnValue;
			}
		};
		double cosAmt=(Double)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		return cosAmt;
	}
	
	
	public String loadMemberPhone(String strCardNo)
	{
		String strSql = " select membermphone from memberinfo where memberno='"+strCardNo+"' and isnull(issendmsg,0)=0";
		AnlyResultSet<String> analysis = new AnlyResultSet<String>()
		{
			public String anlyResultSet(ResultSet rs)
			{
				String returnValue ="";
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatString(rs.getString("membermphone"));
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
		String strMemberPhone=(String)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		return strMemberPhone;
	}
	
	public List<Cardproaccount> loadProInfosByCardNo(String strCompId,String strBillId,String strCardNo)
	{
		try
		{
			 String strModeId=this.dataTool.loadSysParam(strCompId,"SP059");
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
		     String strSql=" select cardvesting,cardno,projectno,proseqno,prjname,propricetype,a.saledate,cutoffdate,salecount,csitemcount,lastcount,saleamt,costamt,lastamt,proremark " +
		     		" from cardproaccount a,projectinfo b ,dconsumeinfo c" +
		     		" where cardno='"+strCardNo+"'  and isnull(lastcount,0)>0 and  projectno=prjno and  prjmodeId  in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')" +
		     		"   and c.cscompid='"+strCompId+"' and c.csbillid='"+strBillId+"' and c.cspaymode='9' and c.csproseqno=a.proseqno" +
		     		"   and a.projectno=c.csitemno ";
		     AnlyResultSet<List<Cardproaccount>> analysis = new AnlyResultSet<List<Cardproaccount>>()
				{
					public List<Cardproaccount> anlyResultSet(ResultSet rs)
					{
						List<Cardproaccount> returnValue=new ArrayList() ;
						Cardproaccount record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record= new Cardproaccount();
								record.setId(new CardproaccountId(rs.getString("cardvesting"),rs.getString("cardno"),rs.getString("projectno"),rs.getDouble("proseqno")));
								record.setBprojectno(CommonTool.FormatString(rs.getString("projectno")));
								record.setBproseqno(CommonTool.FormatDouble(rs.getDouble("proseqno")));
								record.setBprojectname(CommonTool.FormatString(rs.getString("prjname")));
								record.setPropricetype(CommonTool.FormatInteger(rs.getInt("propricetype")));
								record.setSalecount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("salecount"))));
								record.setCostcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemcount"))));
								record.setLastcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("lastcount"))));
								record.setTargetlastcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("lastcount"))));
								record.setSaleamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("saleamt"))));
								record.setCostamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("costamt"))));
								record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("lastamt"))));
								record.setSaledate(CommonTool.getDateMask(rs.getString("saledate")));
								record.setCutoffdate(CommonTool.getDateMask(rs.getString("cutoffdate")));
								record.setProremark(CommonTool.FormatString(rs.getString("proremark")));
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
				List<Cardproaccount> ls=(List<Cardproaccount>)this.amn_Dao.executeQuery_ex(strSql,analysis);
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
	   * 批量发送短信
	   * @param destMobile
	   * @param msgText
	   * @return
	   * @throws Exception
	   */
	public String sendMsg(String strCompId,String destMobile,String msgText) throws Exception{
		return this.sysSendMsg.sendFastMsg(strCompId, destMobile, msgText);
	}
	
	//美容
	public boolean checkBueatPrj(List<Dconsumeinfo> dconsumeinfos, int code)
	{
		
		int count=0;
		String strPayCode="";
		for(Dconsumeinfo dconsumeinfo:dconsumeinfos)
		{
			if("7".equals(dconsumeinfo.getCspaymode()) && dconsumeinfo.getIntegralcode()==code)//1积分支付
			{
				
				if(CommonTool.checkStr(strPayCode))
				{
					strPayCode+=",'"+dconsumeinfo.getCsitemno()+"'";
				}
				else
				{
					strPayCode="'"+dconsumeinfo.getCsitemno()+"'";
				}
				count++;
			}
		}
		if(CommonTool.checkStr(strPayCode))
		{
			StringBuffer buffer=new StringBuffer();
			buffer.append("select count(prjno) from "+ (code==1?"projectinfo":"projectnameinfo") +" where prjno in ("+strPayCode+") and prjtype='4'");
			int ccount=amn_Dao.getRowsCount_Ex(buffer.toString());
			if(code==1){
				return ccount>0 ? false:true;
			}else{
				return ccount!=count;
			}
		}
		else
		{
			return code==1;//积分默认true，美容积分默认false
		}
	}
	
	public boolean checkClientType(String strCardNo,String strEmpInid)
	{
		StringBuffer buffer=new StringBuffer();
		String strBeforDate=CommonTool.datePlusDay(CommonTool.getCurrDate(), -92);
		buffer.append(" with act as (select changebeforcardno,changeaftercardno from mcardchangeinfo where changeaftercardno='"+strCardNo+"' or changebeforcardno='"+strCardNo+"' ");
		buffer.append(" union all select b.changebeforcardno,b.changeaftercardno from act a,mcardchangeinfo  b where a.changebeforcardno=b.changeaftercardno");
		buffer.append(" )select count(cscardno) from act,mconsumeinfo d,dconsumeinfo c");
		buffer.append(" where (act.changebeforcardno=cscardno or act.changeaftercardno=cscardno)");
		buffer.append(" and financedate between '"+strBeforDate+"' and '"+CommonTool.getCurrDate()+"'");
		buffer.append(" and d.cscompid=c.cscompid");
		buffer.append(" and d.csbillid=c.csbillid and (csfirstinid='"+strEmpInid+"' or cssecondinid='"+strEmpInid+"' or csthirdinid='"+strEmpInid+"')");
		int ccount=amn_Dao.getRowsCount_Ex(buffer.toString());
		if(ccount>0)
		{
			return true;
		}
		else 
		{
			//上面没有找到
			buffer.setLength(0);
			buffer.append(" select count(cscardno) from mconsumeinfo c,dconsumeinfo d");
			buffer.append(" where financedate between '"+strBeforDate+"' and '"+CommonTool.getCurrDate()+"' ");
			buffer.append(" and d.cscompid=c.cscompid and d.csbillid=c.csbillid and cscardno='"+strCardNo+"'");
			buffer.append(" and d.csbillid=c.csbillid and (csfirstinid='"+strEmpInid+"' or cssecondinid='"+strEmpInid+"' or csthirdinid='"+strEmpInid+"')");
			ccount=amn_Dao.getRowsCount_Ex(buffer.toString());
			if(ccount>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	public boolean checkCardPay(String strGoodNo)
	{
		String strSql="select iscard from goodsinfo left join gooddiscount on(bprojecttypeid=goodspricetype and compid='"+CommonTool.getLoginInfo("COMPID")+"') where goodsno='"+strGoodNo+"' ";
		ResultSet rs=null;
		boolean bRet=true;
		try
		{
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					if(rs.getInt("iscard")==2)
					{
						bRet=false;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return bRet;
	}
	
	public Yearcardinof loadYearcardinof(String strPhone)
	{
		String strSql="From Yearcardinof where phone='"+strPhone+"'";
		Yearcardinof yearcardinof=null;
		try {
			List<Yearcardinof> lsYearcardinofs=amn_Dao.findByHql(strSql);
			if(lsYearcardinofs!=null && lsYearcardinofs.size()>0)
			{
				yearcardinof=lsYearcardinofs.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yearcardinof;
	}
	
	public List<Yearcarddetal> loadYearcarddetals(String strPhone)
	{
		String strSql=" select * from Yearcarddetal,projectnameinfo where itemno=prjno and phone='"+strPhone+"' and isnull(synum,0)>0 ";
		double xfCount=0;
		ResultSet rs=null;
		List<Yearcarddetal> lsyearList=new ArrayList<Yearcarddetal>();
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					Yearcarddetal yearcarddetal=new Yearcarddetal();
					yearcarddetal.setAmt(rs.getDouble("amt"));
					yearcarddetal.setCompid(rs.getString("compid"));
					yearcarddetal.setIstop(rs.getInt("istop"));
					yearcarddetal.setIteminid(rs.getString("iteminid"));
					yearcarddetal.setItemname(rs.getString("prjname"));
					yearcarddetal.setItemno(rs.getString("itemno"));
					yearcarddetal.setItemstate(rs.getInt("itemstate"));
					yearcarddetal.setNum(rs.getInt("num"));
					yearcarddetal.setPackno(rs.getString("packno"));
					yearcarddetal.setPhone(rs.getString("phone"));
					yearcarddetal.setRemarks(rs.getString("remarks"));
					yearcarddetal.setStopdate(rs.getString("stopdate"));
					yearcarddetal.setSyamt(rs.getDouble("syamt"));
					yearcarddetal.setSynum(rs.getInt("synum"));
					yearcarddetal.setValidate(rs.getString("validate"));
					yearcarddetal.setMonthnum(10000d);
					lsyearList.add(yearcarddetal);
				}
			}
			/*if(lsyearList!=null && lsyearList.size()>0)
			{
				for(Yearcarddetal yearcarddetal:lsyearList)
				{
					if("3".equals(yearcarddetal.getItemno().substring(0,1)))
					{
						xfCount=3;
					}
					else 
					{
						xfCount=6;
					}
					xfCount=xfCount-loadXFCount(yearcarddetal.getIteminid());
					
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsyearList;
	}
	
	public List<Dmedicalcare> loadMedical(String strPhone, String strName){
		StringBuffer strSql = new StringBuffer();
		strSql.append("with temp as(select a.name,a.onlyno bonlyno,b.* ")
			.append("from mmedicalcare a, dmedicalcare b, mcooperatesaleinfo c where a.compno=b.compno and a.salebillid=b.salebillid ")
			.append("and a.telephone=b.telephone and b.salebillid=c.salebillid and c.salebillflag=2 ")//需经过审核之后才能添加消费项目
			.append("and (a.telephone ='"+ strPhone +"' or '"+ strPhone +"'='') and (a.name like '%"+ strName +"%' or '"+ strName +"'='')")
			.append(")select c.*,d.packagename,d.packageprice from temp c, mpackageinfo d where c.packageno=d.packageno and c.compno=d.compid ");
		ResultSet rs=null;
		List<Dmedicalcare> list = new ArrayList<Dmedicalcare>();
		try {
			rs=amn_Dao.executeQuery(strSql.toString());
			if(rs!=null)
			{
				while(rs.next())
				{
					Dmedicalcare entity = new Dmedicalcare();
					entity.setName(rs.getString("name"));
					entity.setBonlyno(rs.getString("bonlyno"));
					entity.setId(rs.getInt("id"));
					entity.setCompno(rs.getString("compno"));
					entity.setSalebillid(rs.getString("salebillid"));
					entity.setTelephone(rs.getString("telephone"));
					entity.setPackageno(rs.getString("packageno"));
					entity.setItemno(rs.getString("itemno"));
					entity.setOnlyno(rs.getString("onlyno"));
					entity.setSalecount(rs.getInt("salecount"));
					entity.setSaleamt(rs.getFloat("saleamt"));
					entity.setLastcount(rs.getInt("lastcount"));
					entity.setLastamt(rs.getFloat("lastamt"));
					entity.setSaledate(CommonTool.getDateMask(rs.getString("saledate")));
					entity.setState(rs.getInt("state"));
					entity.setPackagename(rs.getString("packagename"));
					entity.setPackageprice(rs.getFloat("packageprice"));
					list.add(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public double loadXFCount(String yearInid)
	{
		String strMonth=CommonTool.getCurrDate().substring(0,6);
		StringBuffer buffer=new StringBuffer();
		buffer.append("select sum(csitemcount) from mconsumeinfo a,dconsumeinfo b where a.cscompid=b.cscompid and a.csbillid=b.csbillid and yearinid='"+yearInid+"' and financedate between '"+strMonth+"01' and '"+strMonth+"31' ");
		ResultSet rs=null;
		double xfCount=0;
		try {
			rs=amn_Dao.executeQuery(buffer.toString());
			if(rs!=null)
			{
				while(rs.next())
				{
					xfCount=rs.getDouble(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return xfCount;
	}
	
	public boolean checkDaysCount(List<Dconsumeinfo> lsDconsumeinfos)
	{
		StringBuffer buffer=new StringBuffer();
		double ccount=0;
		if(lsDconsumeinfos!=null && lsDconsumeinfos.size()>0)
		{
			for(Dconsumeinfo dconsumeinfo:lsDconsumeinfos)
			{
				if(CommonTool.checkStr(dconsumeinfo.getYearinid()))
				{
					if(CommonTool.checkStr(buffer.toString())==false)
					{
						buffer.append("select sum(csitemcount) from mconsumeinfo a,dconsumeinfo b where a.cscompid=b.cscompid and a.csbillid=b.csbillid and yearinid='"+dconsumeinfo.getYearinid()+"' and isnull(backcsflag,0)=0 and financedate='"+CommonTool.getCurrDate()+"'");
					}
					else 
					{
						buffer.append(" union all select sum(csitemcount) from mconsumeinfo a,dconsumeinfo b where a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(backcsflag,0)=0 and yearinid='"+dconsumeinfo.getYearinid()+"' and financedate='"+CommonTool.getCurrDate()+"'");
					}
				}
			}
		}
		if(CommonTool.checkStr(buffer.toString()))
		{
			ResultSet rs=null;
			try 
			{
				rs=amn_Dao.executeQuery(buffer.toString());
				if(rs!=null)
				{
					while(rs.next())
					{
						ccount=ccount+CommonTool.FormatDouble(rs.getDouble(1));
					}
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally
			{
				if(rs!=null)
				{
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(ccount>0)
		{
			return false;
		}
		else {
			return true;
		}
	}
	
	
	public boolean checkMonthCount(List<Dconsumeinfo> lsDconsumeinfos)
	{
		StringBuffer buffer=new StringBuffer();
		double ccount=0;
		String strMonth=CommonTool.getCurrDate().substring(0,6);
		if(lsDconsumeinfos!=null && lsDconsumeinfos.size()>0)
		{
			for(Dconsumeinfo dconsumeinfo:lsDconsumeinfos)
			{
				if(CommonTool.checkStr(dconsumeinfo.getYearinid()))
				{
					if(CommonTool.checkStr(buffer.toString())==false)
					{
						if("3".equalsIgnoreCase(dconsumeinfo.getCsitemno().substring(0,1)))
						{
							buffer.append("select sum(csitemcount) from mconsumeinfo a,dconsumeinfo b where a.cscompid=b.cscompid and a.csbillid=b.csbillid and yearinid='"+dconsumeinfo.getYearinid()+"' and financedate between '"+strMonth+"01' and '"+strMonth+"31' having sum(csitemcount)>3");
						}
						else 
						{
							buffer.append("select sum(csitemcount) from mconsumeinfo a,dconsumeinfo b where a.cscompid=b.cscompid and a.csbillid=b.csbillid and yearinid='"+dconsumeinfo.getYearinid()+"' and financedate between '"+strMonth+"01' and '"+strMonth+"31' having sum(csitemcount)>6");
						}
					}
					else 
					{
						if("3".equalsIgnoreCase(dconsumeinfo.getCsitemno().substring(0,1)))
						{
							buffer.append(" union all select sum(csitemcount) from mconsumeinfo a,dconsumeinfo b where a.cscompid=b.cscompid and a.csbillid=b.csbillid and yearinid='"+dconsumeinfo.getYearinid()+"' and financedate between '"+strMonth+"01' and '"+strMonth+"31' having sum(csitemcount)>3 ");
						}
						else 
						{
							buffer.append(" union all select sum(csitemcount) from mconsumeinfo a,dconsumeinfo b where a.cscompid=b.cscompid and a.csbillid=b.csbillid and yearinid='"+dconsumeinfo.getYearinid()+"' and financedate between '"+strMonth+"01' and '"+strMonth+"31' having sum(csitemcount)>6 ");
						}
					}
				}
			}
		}
		if(CommonTool.checkStr(buffer.toString()))
		{
			ResultSet rs=null;
			try 
			{
				rs=amn_Dao.executeQuery(buffer.toString());
				if(rs!=null)
				{
					while(rs.next())
					{
						ccount=ccount+CommonTool.FormatDouble(rs.getDouble(1));
					}
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally
			{
				if(rs!=null)
				{
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(ccount>0)
		{
			return false;
		}
		else {
			return true;
		}
	}
	
	public List<Yearcarddetal> loadYearcarddetals(String strCompId,String strBillId)
	{
		StringBuffer buffer=new StringBuffer();
		buffer.append("select synum,iteminid from Yearcarddetal a,dconsumeinfo b where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and yearinid=iteminid and isnull(synum,0)>0");
		ResultSet rs=null;
		List<Yearcarddetal> lsyYearcarddetals=new ArrayList<Yearcarddetal>();
		try {
			rs=amn_Dao.executeQuery(buffer.toString());
			if(rs!=null)
			{
				while(rs.next())
				{
					Yearcarddetal yearcarddetal=new Yearcarddetal();
					yearcarddetal.setIteminid(rs.getString("iteminid"));
					yearcarddetal.setSynum(rs.getInt("synum"));
					lsyYearcarddetals.add(yearcarddetal);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lsyYearcarddetals;
	}
	
	public boolean zsHDDYQ(String strPhone,List<Dconsumeinfo> lsDconsumeinfos)
	{
		String strSql="";
		String strHDDYJ=CommonTool.random(9);
		String strHDDYJ1="JF"+CommonTool.random(9);
		boolean bRet=true;
		final String tempPhoe=strPhone;
		final String strCompId=CommonTool.getLoginInfo("COMPID");
		for(Dconsumeinfo dconsumeinfo:lsDconsumeinfos)
		{
			if("4699999".equals(dconsumeinfo.getCsitemno()) && CommonTool.checkStr(strPhone) && ("1".equals(dconsumeinfo.getCspaymode()) || "6".equals(dconsumeinfo.getCspaymode())))
			{
				if(dconsumeinfo.getCsitemamt().intValue()==100)
				{
					strSql=" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate,uespassward,phone,billid) ";
					strSql+=" values('"+CommonTool.getLoginInfo("COMPID")+"','"+strHDDYJ+"',1,'"+dconsumeinfo.getCsitemamt()+"',2,0,1,'"+CommonTool.addMonth(3)+"','"+CommonTool.addMonth(3)+"','"+CommonTool.datePlusDay(CommonTool.getCurrDate(), -1)+"',0,'"+strPhone+"','"+dconsumeinfo.getId().getCsbillid()+"')";
					
					strSql+=" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate,uespassward,phone,billid) ";
					strSql+=" values('"+CommonTool.getLoginInfo("COMPID")+"','"+strHDDYJ1+"',1,'"+1000+"',2,0,1,'"+CommonTool.addMonth(3)+"','"+CommonTool.addMonth(3)+"','"+CommonTool.datePlusDay(CommonTool.getCurrDate(), -1)+"',0,'"+strPhone+"','"+dconsumeinfo.getId().getCsbillid()+"')";
					
					bRet=false;
				}
				else 
				{
					strSql=" insert into nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate,uespassward,phone,billid) ";
					strSql+=" values('"+CommonTool.getLoginInfo("COMPID")+"','"+strHDDYJ+"',1,1894,1,0,1,'"+CommonTool.addMonth(3)+"','"+CommonTool.addMonth(3)+"','"+CommonTool.datePlusDay(CommonTool.getCurrDate(), -1)+"',0,'"+strPhone+"','"+dconsumeinfo.getId().getCsbillid()+"')";
					strSql+=" insert into dnointernalcardinfo(cardvesting,cardno,seqno,ineritemno,entrycount,usecount,lastcount,entryamt,useamt,lastamt,billid) ";
					strSql+=" values('"+CommonTool.getLoginInfo("COMPID")+"','"+strHDDYJ+"',0,'4699999',1,0,1,1894,0,1894,'"+dconsumeinfo.getId().getCsbillid()+"')";
				}
				
			}
		}
		try 
		{
			if(CommonTool.checkStr(strSql))
			{
				if(amn_Dao.executeSql(strSql))
				{
					Companyinfo companyinfo=loadCompanyinfo(CommonTool.getLoginInfo("COMPID"));
					if(bRet==false)
					{
						final String strSendMessage="您好,您购买的阿玛尼体验卷,卷号:"+strHDDYJ+",金额:100,卷号:"+strHDDYJ1+",金额:1000,请于"+CommonTool.addMonth(3)+"前致电"+companyinfo.getCompphone()+"预约消费,地址:"+companyinfo.getCompaddress();
						//strSendMessage+=" "+"您好,您购买的阿玛尼体验卷,请于"+CommonTool.addMonth(3)+"前致电"+companyinfo.getCompphone()+"预约消费,地址:"+companyinfo.getCompaddress();
						new Thread(new Runnable() {
							
							public void run() {
								// TODO Auto-generated method stub
								try {
									sendMsg(strCompId, tempPhoe, strSendMessage);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}).start();
						
						//sendMsg(CommonTool.getLoginInfo("COMPID"), strPhone, );
					}
					else
					{
						final String strSendMessage="您好,您购买的阿玛尼体验卷,卷号:"+strHDDYJ+",项目:合作项目体验,请于"+CommonTool.addMonth(3)+"前致电"+companyinfo.getCompphone()+"预约消费,地址:"+companyinfo.getCompaddress()+"";
						new Thread(new Runnable() {
							
							public void run() {
								try {
									sendMsg(strCompId, tempPhoe, strSendMessage);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}).start();
						
					}
					
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Companyinfo loadCompanyinfo(String strCompId)
	{
		String strSql="select * from companyinfo where compno='"+strCompId+"'";
		Companyinfo companyinfo=null;
		ResultSet rs=null;
		try
		{
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					companyinfo=new Companyinfo();
					companyinfo.setCompphone(rs.getString("compphone"));
					companyinfo.setCompaddress(rs.getString("compaddress"));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return companyinfo;
	}
	
	public boolean checkGoodStock(String strCompId,List<Dconsumeinfo> lsDconsumeinfos)
	{
		boolean bRet=true;
		if(lsDconsumeinfos!=null && lsDconsumeinfos.size()>0)
		{
			for(Dconsumeinfo dconsumeinfo:lsDconsumeinfos)
			{
				if(dconsumeinfo.getId().getCsinfotype()==2)
				{
					if(checkGoods(strCompId,dconsumeinfo.getCsitemno(),dconsumeinfo.getCsitemno(),dconsumeinfo.getCsitemcount().doubleValue())==false)
					{
						bRet=false;
						break;
					}
				}
			}
		}
		return bRet;
	}
	
	public boolean checkGoods(String strCompId,String strFromGoods,String strToGoods,double count)
	{
		String strSql="exec upg_compute_goods_stock '"+strCompId+"','"+CommonTool.getCurrDate()+"','"+strFromGoods+"','"+strToGoods+"',''";
		ResultSet rs=null;
		boolean bRet=true;
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					System.out.println(rs.getDouble("quantity"));
					if(rs.getDouble("quantity")<count)
					{
						bRet=false;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return bRet;
	}
	
	public List<String> load38Dis(String strCardNo)
	{
		String strSql="select csitemno from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock) where a.cscompid=b.cscompid and a.csbillid=b.csbillid and csinfotype=1 " +
				" and cscardno='"+strCardNo+"' and financedate between '20150301' and '20150331' group by csitemno ";
		List<String> lsStrings=new ArrayList<String>();
		ResultSet rs=null;
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					lsStrings.add(rs.getString("csitemno"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lsStrings;
	}
	
	public Wxbandcard checkWXRandomno(String strRandomno)
	{
		String strSql=" From Wxbandcard where randomno='"+strRandomno+"' and validate>='"+CommonTool.getNowTime()+"' ";
		List<Wxbandcard> lsWxbandcards=amn_Dao.findByHql(strSql);
		Wxbandcard wxbandcard=null;
		if(lsWxbandcards!=null && lsWxbandcards.size()>0)
		{
			wxbandcard=lsWxbandcards.get(0);
		}
		return wxbandcard;
	}
	
	public Wxbandcard loadWXRandomno(String strRandomno)
	{
		String strSql=" From Wxbandcard where randomno='"+strRandomno+"' ";
		List<Wxbandcard> lsWxbandcards=amn_Dao.findByHql(strSql);
		Wxbandcard wxbandcard=null;
		if(lsWxbandcards!=null && lsWxbandcards.size()>0)
		{
			wxbandcard=lsWxbandcards.get(0);
		}
		return wxbandcard;
	}
	
	public List<Cardtransactionhistory> loadcCardtransactionhistories(String strYearInit)
	{
		String strSql="select financedate,a.cscompid,compname,csitemcount,csfirstsaler,cssecondsaler,csthirdsaler from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock) left join companyinfo on(b.cscompid=compno) where yearinid ='"+strYearInit+"' and a.csbillid=b.csbillid and a.cscompid=b.cscompid ";
		List<Cardtransactionhistory> lsCardtransactionhistories=new ArrayList<Cardtransactionhistory>();
		ResultSet rs=null;
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					Cardtransactionhistory cardtran=new Cardtransactionhistory();
					cardtran.setTransactioncompid(rs.getString("compname"));
					cardtran.setTransactiondate(CommonTool.getDateMask(rs.getString("financedate")));
					cardtran.setCcount(rs.getBigDecimal("csitemcount"));
					cardtran.setFirstempid(rs.getString("csfirstsaler"));
					cardtran.setSecondempid(rs.getString("cssecondsaler"));
					cardtran.setThirthempid(rs.getString("csthirdsaler"));
					lsCardtransactionhistories.add(cardtran);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lsCardtransactionhistories;
	}
	
	public boolean updYearCardInfo(String strInid)
	{
		String strSql=" update Yearcarddetal set itemstate=1,istop=1,stopdate='"+CommonTool.getCurrDate()+"' where iteminid='"+strInid+"' ";
		try {
			return amn_Dao.executeSql(strSql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkIsStop(String strInid)
	{
		String strSql=" select count(1) From Yearcarddetal where iteminid='"+strInid+"' and isnull(istop,0)=1 ";
		if(amn_Dao.getRowsCount_Ex(strSql)>0)
		{
			return false;
		}
		return true;
	}
	
	public boolean updYearCardJH(String strInid)
	{
		String strSql=" update Yearcarddetal set itemstate=0,validate=convert(varchar(20),dateadd(dd,datediff(dd,stopdate,validate),'"+CommonTool.getCurrDate()+"'),112) where iteminid='"+strInid+"' ";
		try {
			return amn_Dao.executeSql(strSql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkIsJH(String strInid)
	{
		String strSql=" select count(1) From Yearcarddetal where iteminid='"+strInid+"' and isnull(istop,0)=0 ";
		if(amn_Dao.getRowsCount_Ex(strSql)>0)
		{
			return false;
		}
		return true;
	}
	
	public boolean checkDelay(String strInid)
	{
		String strSql="select count(1) From Yearcarddetal where iteminid='"+strInid+"' and isnull(itemstate,0)=0 and validate<CONVERT(varchar(10), GETDATE(), 112) and LEFT(packno,3) in ('201','202') ";
		return amn_Dao.getRowsCount_Ex(strSql)==0;
	}
	
	public boolean checkIsDelay(String strInid)
	{
		String strSql="select count(1) From Yearcarddetal where iteminid='"+strInid+"' and isnull(isdelay,0)=1";
		return amn_Dao.getRowsCount_Ex(strSql)>0;
	}
	
	public boolean updYearCardDelay(String strInid)
	{//过期美容“4”年卡剩余4次以下可以延期一个月,4次及4次以上可以延期两个月;过期美发“3”年卡小于5次不能延期，5次以上延期一个月
		String strSql="update Yearcarddetal set isdelay=1,validate="+
				"case when left(itemno,1)='4' and synum<4 then CONVERT(varchar(10),dateadd(day,30,CONVERT(date,GETDATE(),120)),112) "+
				"when left(itemno,1)='4' and synum>=4 then CONVERT(varchar(10),dateadd(day,60,CONVERT(date,GETDATE(),120)),112) "+
				"when left(itemno,1)='3' and synum>=5 then CONVERT(varchar(10),dateadd(day,30,CONVERT(date,GETDATE(),120)),112) "+
				"end from yearcarddetal where iteminid='"+strInid+"'";
		try {
			return amn_Dao.executeSql(strSql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Mconsumeinfo> loadBillId(String strCompId)
	{
		//String strSql=" select billid,state from billstate where isnull(state,0)!=2 and compid='"+strCompId+"' and createdate='"+CommonTool.getCurrDate()+"' ";
		String strSql=" select billid,state from billstate where compid='"+strCompId+"' and createdate='"+CommonTool.getCurrDate()+"' ";
		ResultSet rs=null;
		List<Mconsumeinfo> lsMconsumeinfos=new ArrayList<Mconsumeinfo>();
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					Mconsumeinfo mconsumeinfo=new Mconsumeinfo();
					mconsumeinfo.setBcsbillid(rs.getString("billid"));
					if(rs.getInt("state")==1)
					{
						mconsumeinfo.setBcscompid("已结账");
					}
					else if(rs.getInt("state")==2) 
					{
						mconsumeinfo.setBcscompid("已取消");
					}
					else 
					{
						mconsumeinfo.setBcscompid("未结账");
					}
					lsMconsumeinfos.add(mconsumeinfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lsMconsumeinfos;
	}
	
	public List<Orders> loadOrders(String strCompId) {
		List<Orders> lsOrders=new ArrayList<Orders>();
		String strSql=" select orderfactdate,orderfacttime,compaddress,complydetail,cardno,callbillid,orderconply,compname from orders with(nolock),companyinfo with(nolock) where compno=orderconply and orderconply='"+strCompId+"' and confirmdate='"+CommonTool.getCurrDate()+"' ";
		ResultSet rs=null;
		try {
			rs=amn_Dao.executeQuery(strSql);
			while(rs.next())
			{
				Orders orders=new Orders();
				orders.setCallbillid(rs.getString("callbillid"));
				orders.setOrderconply(rs.getString("orderconply"));
				orders.setOrderfactdate(rs.getString("orderfactdate"));
				orders.setComplydetail(rs.getString("complydetail"));
				orders.setOrderfacttime(rs.getString("orderfacttime"));
				orders.setStrCompName(rs.getString("compname"));
				orders.setAddress(rs.getString("compaddress"));
				List<OrdersEmpinfo> lsEmpinfos=loadOrdersEmpinfo(rs.getString("orderconply"), rs.getString("callbillid"));
				if(lsEmpinfos!=null && lsEmpinfos.size()>0)
				{
					for(OrdersEmpinfo orEmpinfo:lsEmpinfos)
					{
						if(orders.getConfirmemp()==null)
						{
							orders.setConfirmemp(orEmpinfo.getEmpname());
						}
						else
						{
							orders.setConfirmemp(orders.getConfirmemp()+","+orEmpinfo.getEmpname());
						}		
					}
				}
				
				List<OredersPrjinfo> lsOredersPrjinfos=loadOredersPrjinfo(rs.getString("orderconply"), rs.getString("callbillid"));
				if(lsOredersPrjinfos!=null && lsOredersPrjinfos.size()>0)
				{
					for(OredersPrjinfo orPrjinfo:lsOredersPrjinfos)
					{
						if(orders.getOrderproject()==null)
						{
							orders.setOrderproject(orPrjinfo.getPrjname());
						}
						else
						{
							orders.setOrderproject(orders.getOrderproject()+","+orPrjinfo.getPrjname());
						}
					}
				}
				lsOrders.add(orders);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsOrders;
	}
	
	public List<OrdersEmpinfo> loadOrdersEmpinfo(String strCompId,String strBillId)
	{
		String strSql="select * from orders_empinfo where compid='"+strCompId+"' and billid='"+strBillId+"' ";
		ResultSet rs=null;
		List<OrdersEmpinfo> lsEmpinfos=new ArrayList<OrdersEmpinfo>();
		try {
			rs=amn_Dao.executeQuery(strSql);
			while(rs.next())
			{
				OrdersEmpinfo orEmpinfo=new OrdersEmpinfo();
				orEmpinfo.setBillid(rs.getString("billid"));
				orEmpinfo.setCompid(rs.getString("compid"));
				orEmpinfo.setEmpinid(rs.getString("empinid"));
				orEmpinfo.setEmpno(rs.getString("empno"));
				orEmpinfo.setEmpname(rs.getString("empname"));
				orEmpinfo.setUuid(rs.getString("uuid"));
				lsEmpinfos.add(orEmpinfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lsEmpinfos;
	}
	
	public List<OredersPrjinfo> loadOredersPrjinfo(String strCompId,String strBillId)
	{
		String strSql="select * from oreders_prjinfo where compid='"+strCompId+"' and billid='"+strBillId+"' ";
		ResultSet rs=null;
		List<OredersPrjinfo> lsOredersPrjinfos=new ArrayList<OredersPrjinfo>();
		try {
			rs=amn_Dao.executeQuery(strSql);
			while(rs.next())
			{
				OredersPrjinfo oredersPrjinfo=new OredersPrjinfo();
				oredersPrjinfo.setBillid(rs.getString("billid"));
				oredersPrjinfo.setCompid(rs.getString("compid"));
				oredersPrjinfo.setPrjno(rs.getString("prjno"));
				oredersPrjinfo.setPrjname(rs.getString("prjname"));
				oredersPrjinfo.setUuid(rs.getString("uuid"));
				lsOredersPrjinfos.add(oredersPrjinfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lsOredersPrjinfos;
	}
	
	public boolean checkOpenid(String strRandomno)
	{
		Wxbandcard wxbandcard=loadWXRandomno(strRandomno);
		if(wxbandcard==null)
		{
			return false;
		}
		if(amn_Dao.getRowsCount_Ex(" select count(compid) from billstate where randomno='"+strRandomno+"' and isnull(state,0)=0")>0)
		{
			return false;
		}
		return true;
	}
	
	public List<Yearcardinof> loadYearInfo(String strCardName)
	{
		String strSql=" From Yearcardinof where name like '%"+strCardName+"%' and compid='"+CommonTool.getLoginInfo("COMPID")+"' ";
		List<Yearcardinof> lsYearCard=null;
		try {
			lsYearCard=amn_Dao.findByHql(strSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsYearCard;
	}
	
	public String checkBillState(String strCompId,String strBillId)
	{
		String strSql=" select state from billstate where compid='"+strCompId+"' and billid='"+strBillId+"' and state in(1,2) ";
		ResultSet rs=null;
		String state="";
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					state=rs.getString("state");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return state;
	}
	
	public boolean updateBillState(String strCompId,String strBillId)
	{
		String strSql=" update billstate set state=2 where compid='"+strCompId+"' and billid='"+strBillId+"'";
		return amn_Dao.executeSql(strSql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Mpackageinfo> loadGroup(){
		try{
			String strSql="From Mpackageinfo mpackageinfo where compid='"+ CommonTool.getLoginInfo("COMPID") +"' and usetype=5";
			List<Mpackageinfo> list = (List<Mpackageinfo>)this.amn_Dao.findByHql(strSql);
			return list;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	//接发活动-获取美发师
	public List<Yearcardinof> loadHairStaff(String strCompId){
		List<Yearcardinof> list = new ArrayList<Yearcardinof>();
		String sql = "select staffno, staffname from staffinfo where position in ('003','006','007','00701') and compno='"+ strCompId +"'";
		ResultSet rs = null;
		try {
			rs = amn_Dao.executeQuery(sql);
			while (rs.next()) {
				Yearcardinof staff = new Yearcardinof();
				staff.setCid(rs.getString("staffno"));
				staff.setName(rs.getString("staffname"));
				list.add(staff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	//接发：保存其他店客人的情况：公司35%，当店32.5%，其他店32.5%-保存其他店32.5%的消费主档和消费明细
	public void postHairOther(Mconsumeinfo mconsumeinfo, Dconsumeinfo dconsumeinfo, Dpayinfo dpayinfo){
		try{
			this.amn_Dao.save(mconsumeinfo);
			this.amn_Dao.save(dconsumeinfo);
			this.amn_Dao.save(dpayinfo);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public List<Companyinfo> loadCompanyData(){
		String curcomp = CommonTool.getLoginInfo("COMPID");
		String strSql ="select a from Companyinfo a, Compchainstruct b " +
				"where a.compno=b.curcompno and a.compstate=1 and b.complevel=4 and a.compno <>'"+curcomp+"' order by a.compno";
		return this.amn_Dao.findByHql(strSql);
	}
	//获取微信绑定卡OpenId
	public String loadWechatOpenId(String memberno){
		String openId = "";
		String sql ="select openid from memberinfo where memberno='"+ memberno +"'";
		ResultSet rs = null;
		try {
			rs = amn_Dao.executeQuery(sql);
			if(rs.next()){
				openId = rs.getString("openid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return openId;
	}
	
	public ActivateCard loadAccount(String cardno) {
		ActivateCard activateCard = new ActivateCard();
		ResultSet rs = null;
		String sql = "select isnull(a.memberno,b.memberno) as cardno, isnull(a.bonus,0) as bonus,isnull(b.balance,0) as balance from "
						+"(select memberno,accountbalance as bonus	"
						+"from cardaccount,memberinfo  where  memberno = '"+cardno+"' and accounttype ='3' "
						+"and cardno = memberno) a "
						+"full join "
						+"(select memberno, accountbalance as balance "
						+"from cardaccount,memberinfo  where  memberno = '"+cardno+"' and accounttype ='2' "
						+"and cardno = memberno) b "
						+"on a.memberno=b.memberno ";
		try {
			rs = amn_Dao.executeQuery(sql);
			while(rs.next()) { 
				activateCard.setCard_id("prCjajnQ1iPwjJHEZ0i8DbHYZgdo");
				activateCard.setMembership_number(rs.getString("cardno"));
				activateCard.setCode(rs.getString("cardno"));
				activateCard.setInit_balance(rs.getFloat("balance"));
				activateCard.setInit_bonus(rs.getFloat("bonus"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return activateCard;
	}
	
	public Nointernalcardinfo loadNointernalcardByWXinfo(String strCardNo){
		String response = "";
		Nointernalcardinfo nointernalcardinfo = new Nointernalcardinfo();
		try {
			String reqUrl=String.format("http://wechat.chinamani.com/AmaniWechatPlatform/getCardId?getToken=true&code=%s",strCardNo);
			URL url = new URL(reqUrl);  
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
			conn.setDoOutput(true);  
			conn.setRequestMethod("GET");  
			conn.setRequestProperty("Content-Type", "text/json");  
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
			String line;  
			while ((line = rd.readLine()) != null) {  
				response = line;
			}  
			rd.close();
			JSONObject res=JSONObject.fromObject(response);
			if(0 == Integer.valueOf(res.getString("error"))){//正常返回
				if("true".equals(res.getString("time")) && "true".equals(res.getString("can_consume"))){
					StringBuffer sql = new StringBuffer();
					sql.append("select wc.type,wc.money from wxcardconfig wc where cardId ='" + res.getString("card_id") + "'");
					ResultSet rs = null;
					rs = amn_Dao.executeQuery(sql.toString());
					try {
						if (rs.next()) {
								//微信券(代金券或项目券)
								nointernalcardinfo.setUseinproject(rs.getString("type"));//0 代金券  1 项目券
								nointernalcardinfo.setCardfaceamt(new BigDecimal(rs.getString("money")));
								nointernalcardinfo.setCardId(res.getString("card_id"));
						}else{
							return null;
						}
					} catch (Exception e) {
						logger.error("CC011Service.java loadNointernalcardByWXinfo sql error:" + e);
						return null;
					} finally {
						if (rs != null) {
							try {
								rs.close();
							} catch (SQLException e) {
								logger.error("CC011Service.java loadNointernalcardByWXinfo sqlclose error:" + e);
							}
						}
					}
				}
			}else{
				logger.error("CC011Service.java loadNointernalcardByWXinfo wx error:" + res.getString("error"));
				return null;
			}
			
		} catch (Exception e) {
			logger.error("CC011Service.java loadNointernalcardByWXinfo error:" + e);
			return null;
		}
		strCardNo = null;
		
		return nointernalcardinfo;
	}
	
	/**
	 * 
	 * @param strCardNo 项目编号
	 * @return
	 */
	public List<Dnointernalcardinfo> loadDnointernalcardByWXinfo(String cardId, BigDecimal lastamt)
	{
		ResultSet rs = null;
		StringBuffer cardType= new StringBuffer();
		StringBuffer sqlt = new StringBuffer();
		sqlt.append(" select wd.prono from wxcardconfigdetail wd where cardId='" + cardId + "'");
		List<Dnointernalcardinfo> returnvalue=new ArrayList<Dnointernalcardinfo>();
		rs = amn_Dao.executeQuery(sqlt.toString());
		try {
			while (rs.next()) {
				cardType = StringUtils.isBlank(cardType.toString())?cardType.append("'"+rs.getString("prono")+"'"):cardType.append(",'"+rs.getString("prono")+"'");
			}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT PRJNO,PRJNAME FROM PROJECTINFO WHERE USEFLAG ='1' ");
		sql.append(" AND PRJSALETYPE='1' AND PRJNO IN ("+cardType+")");
		rs = amn_Dao.executeQuery(sql.toString());
		Dnointernalcardinfo record=null;
			if (rs != null) {
				while (rs.next()) {
					record=new Dnointernalcardinfo();
					record.setIneritemno(CommonTool.FormatString(rs.getString("prjno")));
					record.setIneritemname(CommonTool.FormatString(rs.getString("prjname")));
					record.setLastcount(CommonTool.FormatBigDecimal(new BigDecimal(1)));
					record.setLastamt(lastamt);
					record.setEntryremark("微信项目券");
					returnvalue.add(record);
				}
			}
		} catch (Exception e) {
			logger.error("CC011Service.java loadNointernalcardByWXinfo sql error:" + e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("CC011Service.java loadNointernalcardByWXinfo sqlclose error:" + e);
				}
			}
		}
		return returnvalue;
	}
	
	/**
	 * 判断是否赠送生日积分
	 * @param cardNo
	 * @return
	 */
	public boolean checkSendIntegral(String cardNo){
		ResultSet rs=null;
		StringBuffer sql= new StringBuffer();
		SimpleDateFormat sFormaty = new SimpleDateFormat("yyyy");
		SimpleDateFormat sFormatm = new SimpleDateFormat("MM");
		String checkDatey =  sFormaty.format(new Date());
		String checkDatem =  sFormatm.format(new Date());
		String memberbirthday = "";
		try {
			//0、判断今年是否送过生日积分
			sql.append("SELECT SENDYEAR FROM DBO.MEMBERINFO WHERE MEMBERNO='" + cardNo + "'");
			rs=amn_Dao.executeQuery(sql.toString());
			sql.setLength(0);
			if(rs!=null && rs.next()){
				memberbirthday = sFormaty.format(new SimpleDateFormat("yyyy").parse(StringUtils.isBlank(rs.getString("sendyear"))?"1991":rs.getString("sendyear")));
				if(checkDatey.equals(memberbirthday)){
					return false;
				}
			}
			sFormaty = null; checkDatey = null;
			//1、判断是不是生日
			sql.append("SELECT MEMBERBIRTHDAY FROM MEMBERINFO WHERE MEMBERNO='" + cardNo + "';");
			rs=amn_Dao.executeQuery(sql.toString());
			sql.setLength(0);
			if(rs!=null && rs.next()){
				if(StringUtils.isBlank(rs.getString("memberbirthday"))){
					return false;
				}
				memberbirthday = sFormatm.format(new SimpleDateFormat("yyyyMMdd").parse(rs.getString("memberbirthday")));
				if(!checkDatem.equals(memberbirthday)){
					return false;
				}
			}
			sFormatm = null; checkDatem = null; memberbirthday =null;
			//2、两年内有充值
			boolean twoYears = false; 
				//---a.卖卡
			sql.append(" SELECT SALEKEEPAMT FROM MSALECARDINFO WHERE FINANCEDATE BETWEEN ");
			sql.append(" CONVERT(VARCHAR(20),DATEADD(YY,-2,GETDATE()),112) AND CONVERT(");
			sql.append(" VARCHAR(20),GETDATE(),112) AND SALECARDNO ='" + cardNo + "'");
			rs=amn_Dao.executeQuery(sql.toString());
			sql.setLength(0);
			if(rs!=null && rs.next()){
				if(StringUtils.isNotBlank(rs.getString("salekeepamt")) && Double.valueOf(rs.getString("salekeepamt")) > 0D){
					twoYears = true;
				}
			}
				//--b.充值
			sql.append(" SELECT RECHARGEKEEPAMT FROM MCARDRECHARGEINFO  WHERE FINANCEDATE");
			sql.append(" BETWEEN CONVERT(VARCHAR(20),DATEADD(YY,-2,GETDATE()),112) AND");
			sql.append(" convert(varchar(20),GETDATE(),112) and rechargecardno ='" + cardNo + "'");
			rs=amn_Dao.executeQuery(sql.toString());
			sql.setLength(0);
			if(rs!=null && rs.next()){
				if(StringUtils.isNotBlank(rs.getString("rechargekeepamt")) && Double.valueOf(rs.getString("rechargekeepamt")) > 0D){
					twoYears = true;
				}
			}
				//--c.折扣转卡(因为包含了其他卡异动所以只要判断折扣转卡)
			sql.append(" SELECT CHANGEFILLAMT FROM MCARDCHANGEINFO  WHERE FINANCEDATE BETWEEN");
			sql.append(" CONVERT(VARCHAR(20),DATEADD(YY,-2,GETDATE()),112) AND CONVERT(VARCHAR");
			sql.append(" (20),GETDATE(),112) AND CHANGETYPE IN(0,1) AND CHANGEAFTERCARDNO ='" + cardNo + "'");
			rs=amn_Dao.executeQuery(sql.toString());
			sql.setLength(0);
			if(rs!=null && rs.next()){
				if(StringUtils.isNotBlank(rs.getString("changefillamt")) && Double.valueOf(rs.getString("changefillamt")) > 0D){
					twoYears = true;
				}
			}
			if(!twoYears){
				return false;
			}
			//3、是不是至尊卡
			sql.append(" SELECT * FROM CARDINFO  WHERE CARDTYPE IN('AK','AK2013')");
			sql.append(" AND CARDSTATE=4 AND CARDNO='" + cardNo + "'");
			rs=amn_Dao.executeQuery(sql.toString());
			sql.setLength(0);
			if(rs.next()==false){
				return false;
			}
		} catch (Exception e) {
			logger.error("CC011Service.java checkSendIntegral sql error:" + e);
			return false;
		}
		finally{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (Exception e) {
					logger.error("CC011Service.java checkSendIntegral close error:" + e);
				}
			}
		}
		sql = null; rs = null;
		return true;
	}
	
	/**
	 * 赠送生日积分
	 * @param cardNo
	 * @return
	 */
	public boolean SendIntegral(String cardNo){
		ResultSet rs=null;
		StringBuffer sql= new StringBuffer();
		String comno = "";
		Double integral = 0d;
		String sendYear = new SimpleDateFormat("yyyy").format(new Date());
		try {
			//查询门店编号
			sql.append("SELECT MEMBERVESTING FROM MEMBERINFO WHERE MEMBERNO='"+cardNo+"'");
			rs=amn_Dao.executeQuery(sql.toString());
			sql.setLength(0);
			if(rs!=null && rs.next()){
				comno = rs.getString("membervesting");
			}
			sql.append("SELECT ACCOUNTBALANCE FROM CARDACCOUNT WHERE ACCOUNTTYPE='6' AND CARDNO='"+cardNo+"'");
			rs=amn_Dao.executeQuery(sql.toString());
			sql.setLength(0);
			if(rs.next()){
				integral = Double.valueOf(StringUtils.isBlank(rs.getString("ACCOUNTBALANCE"))?"0":rs.getString("ACCOUNTBALANCE"));
				//更新积分
				sql.append("UPDATE CARDACCOUNT set ACCOUNTBALANCE='"+(integral+1000d)+"' where CARDNO='"+cardNo+"' and ACCOUNTTYPE='6'");
				if(amn_Dao.executeSql(sql.toString())){
					sql.setLength(0);
					sql.append("update memberinfo set sendyear='"+sendYear+"' where memberno='"+cardNo+"'");
					if(amn_Dao.executeSql(sql.toString())){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				//插入积分
				sql.append("INSERT INTO CARDACCOUNT(CARDVESTING,CARDNO,ACCOUNTTYPE,ACCOUNTBALANCE) VALUES('"+comno+"','"+cardNo+"','6','1000');");
				if(amn_Dao.executeSql(sql.toString())){
					sql.setLength(0);
					sql.append("update memberinfo set sendyear='"+sendYear+"' where memberno='"+cardNo+"'");
					if(amn_Dao.executeSql(sql.toString())){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
				
			}
		} catch (Exception e) {
			logger.error("CC011Service.java SendIntegral  error:" + e);
			return false;
		}
	}
}