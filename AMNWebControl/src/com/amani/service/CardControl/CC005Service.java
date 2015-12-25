package com.amani.service.CardControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Cardaccount;
import com.amani.model.Cardaccountchangehistory;
import com.amani.model.Cardchangehistory;
import com.amani.model.Cardinfo;
import com.amani.model.Cardsoninfo;
import com.amani.model.Cardspecialcost;
import com.amani.model.Cardtransactionhistory;
import com.amani.model.Memberinfo;
import com.amani.model.Sendpointcard;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class CC005Service  extends AMN_ModuleService{
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
			this.amn_Dao.delete(curMaster);
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
			this.amn_Dao.saveOrUpdate(curMaster);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public Memberinfo loadMemberinfoByCompId(String strCompId,String strMemberId)
	{
		try
		{
			String strSql=" From Memberinfo memberinfo where membervesting='"+strCompId+"' and memberno='"+strMemberId+"' ";
			List<Memberinfo> lsMemberinfo=this.amn_Dao.findByHql(strSql);
			if(lsMemberinfo!=null && lsMemberinfo.size()>0)
			{
				return lsMemberinfo.get(0);
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Cardinfo> loadCardinfo(String strCompId)
	{
		try
		{
			String strSql="select a.cardvesting,a.cardno,a.cardtype, b.membername,b.membermphone from  cardinfo a,memberinfo b " +
					" where a.cardno=b.cardnotomemberno and  cardvesting='"+strCompId+"' " +
					" and substring(memberbirthday,5,4) = substring('"+CommonTool.getCurrDate()+"',5,4) and a.cardstate in (4,5)";
			AnlyResultSet<List<Cardinfo>> analysis = new AnlyResultSet<List<Cardinfo>>()
			{
				public List<Cardinfo> anlyResultSet(ResultSet rs)
				{
					List<Cardinfo> returnValue = new ArrayList();
					Cardinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardinfo();
							record.setBcardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
							record.setBcardno(CommonTool.FormatString(rs.getString("cardno")));
							record.setCardtype(CommonTool.FormatString(rs.getString("cardtype")));
							record.setMembername(CommonTool.FormatString(rs.getString("membername")));
							record.setMemberphone(CommonTool.FormatString(rs.getString("membermphone")));
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
			List<Cardinfo> ls= (List<Cardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public Cardinfo loadCardinfoByCardNo(String strCompId,String strCardNo)
	{
		try
		{
			String strModeId=this.getDataTool().loadSysParam(strCompId,"SP063");
			String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
		    String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.getDataTool().loadCompLvl(strCompId);
			if(compLvl==2)
			 {
				strFristModeId=this.getDataTool().loadparentModeId(strModeId);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId=this.getDataTool().loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.getDataTool().loadparentModeId(strSecondModeId);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId=this.getDataTool().loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.getDataTool().loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.getDataTool().loadparentModeId(strSecondModeId);
			 }
			String strSql="select a.cardvesting,a.cardno,a.cardtype, c.cardtypename,b.membername,b.membermphone,b.memberpaperworkno,a.cardsource,a.salecarddate,a.cutoffdate,a.cardstate " +
					"from cardtypeinfo c,compchaininfo, cardinfo a left join memberinfo b on a.cardno=b.memberno   " +
					" where  cardvesting='"+strCompId+"'  and cardno='"+strCardNo+"' " +
					"  and cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and c.cardtypeno = a.cardtype and  curcomp=cardtypesource and relationcomp=cardvesting ";
			AnlyResultSet<Cardinfo> analysis = new AnlyResultSet<Cardinfo>()
			{
				public Cardinfo anlyResultSet(ResultSet rs)
				{
				
					Cardinfo record=null;
					try
					{
						if(rs != null && rs.next()==true)
						{
							record=new Cardinfo();
							record.setBcardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
							record.setBcardno(CommonTool.FormatString(rs.getString("cardno")));
							record.setCardtype(CommonTool.FormatString(rs.getString("cardtype")));
							record.setMembername(CommonTool.FormatString(rs.getString("membername")));
							record.setMemberphone(CommonTool.FormatString(rs.getString("membermphone")));
							record.setMemberpcid(CommonTool.FormatString(rs.getString("memberpaperworkno")));
							record.setCardtypeName(CommonTool.FormatString(rs.getString("cardtypename")));
							record.setCardsource(CommonTool.FormatInteger(rs.getInt("cardsource")));
							record.setSalecarddate(CommonTool.getDateMask(rs.getString("salecarddate")));
							record.setCutoffdate(CommonTool.getDateMask(rs.getString("cutoffdate")));
							record.setCardstate(CommonTool.FormatInteger(rs.getInt("cardstate")));
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
			Cardinfo record= (Cardinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Cardaccount> loadCardaccountInfo(String strCardNo)
	{
		try
		{
			String strSql="select cardvesting,cardno,accounttype,parentcodevalue,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark " +
					" from cardaccount ,commoninfo " +
					" where cardno='"+strCardNo+"' and 'ZHLX'=infotype and parentcodekey=accounttype ";
			AnlyResultSet<List<Cardaccount>> analysis = new AnlyResultSet<List<Cardaccount>>()
			{
				public List<Cardaccount> anlyResultSet(ResultSet rs)
				{
					List<Cardaccount> returnValue = new ArrayList();
					Cardaccount record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardaccount();
							record.setCardvesting(rs.getString("cardvesting"));
							record.setCardno(rs.getString("cardno"));
							record.setAccounttype(CommonTool.FormatInteger(rs.getInt("accounttype")));
							record.setAccounttypeText(CommonTool.FormatString(rs.getString("parentcodevalue")));
							record.setAccountbalance(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance"))));
							record.setAccountdebts(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts"))));
							record.setAccountdatefrom(CommonTool.getDateMask(rs.getString("accountdatefrom")));
							record.setAccountdateend(CommonTool.getDateMask(rs.getString("accountdateend")));
							record.setAccountremark(rs.getString("accountremark"));
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
			List<Cardaccount> ls= (List<Cardaccount>)this.amn_Dao.executeQuery_ex(strSql,analysis);		
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	

	
	
	public List<Cardchangehistory> loadCardchangehistory(String strCardNo)
	{
		try
		{
			String strSql=" select changetype,changebillid,beforestate,afterstate,chagedate,targetcardno from  cardchangehistory where changecardno='"+strCardNo+"'  order by changeseqno desc  ";
			AnlyResultSet<List<Cardchangehistory>> analysis = new AnlyResultSet<List<Cardchangehistory>>()
			{
				public List<Cardchangehistory> anlyResultSet(ResultSet rs)
				{
					List<Cardchangehistory> returnValue = new ArrayList();
					Cardchangehistory record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardchangehistory();
							record.setChangetype(CommonTool.FormatInteger(rs.getInt("changetype")));
							record.setChangebillid(CommonTool.FormatString(rs.getString("changebillid")));
							record.setBeforestate(CommonTool.FormatInteger(rs.getInt("beforestate")));
							record.setAfterstate(CommonTool.FormatInteger(rs.getInt("afterstate")));
							record.setChagedate(CommonTool.getDateMask(rs.getString("chagedate")));
							record.setTargetcardno(CommonTool.FormatString(rs.getString("targetcardno")));
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
			List<Cardchangehistory> ls= (List<Cardchangehistory>)this.amn_Dao.executeQuery_ex(strSql,analysis);		
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Cardaccountchangehistory> loadCardaccountchangehistory(String strCardNo)
	{
		try
		{
			String strSql=" select changecompid,compname,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt,changemark " +
					"from cardaccountchangehistory,companyinfo where changecardno='"+strCardNo+"' and compno=changecompid order by changeaccounttype,changeseqno desc  ";
			AnlyResultSet<List<Cardaccountchangehistory>> analysis = new AnlyResultSet<List<Cardaccountchangehistory>>()
			{
				public List<Cardaccountchangehistory> anlyResultSet(ResultSet rs)
				{
					List<Cardaccountchangehistory> returnValue = new ArrayList();
					Cardaccountchangehistory record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardaccountchangehistory();
							record.setChangecompname(CommonTool.FormatString(rs.getString("compname")));
							record.setChangecompid(CommonTool.FormatString(rs.getString("changecompid")));
							record.setChangeaccounttype(CommonTool.FormatInteger(rs.getInt("changeaccounttype")));
							record.setChangeseqno(CommonTool.FormatInteger(rs.getInt("changeseqno")));
							record.setChangetype(CommonTool.FormatInteger(rs.getInt("changetype")));
							record.setChangebilltype(CommonTool.FormatString(rs.getString("changebilltype")));
							record.setChangebillno(CommonTool.FormatString(rs.getString("changebillno")));
							record.setChagedate(CommonTool.getDateMask(rs.getString("chagedate")));
							record.setChangeamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("changeamt"))));
							record.setChangebeforeamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("changebeforeamt"))));
							if(rs.getInt("changetype") == 0 || rs.getInt("changetype") == 6
									|| rs.getInt("changetype") == 7 || rs.getInt("changetype") == 8 
									|| rs.getInt("changetype") == 9 || rs.getInt("changetype") == 10 || rs.getInt("changetype") == 11  )
							{
								record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changebeforeamt"))+ CommonTool.FormatDouble(rs.getDouble("changeamt")))));
							}
							else
							{
								record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changebeforeamt"))- CommonTool.FormatDouble(rs.getDouble("changeamt")))));
							}
							if(CommonTool.FormatInteger(rs.getInt("changeaccounttype"))==2 && rs.getInt("changetype") == 11)
							{
								record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changebeforeamt"))- CommonTool.FormatDouble(rs.getDouble("changeamt")))));
							}
							record.setChangemark(CommonTool.FormatString(rs.getString("changemark")));
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
			List<Cardaccountchangehistory> ls= (List<Cardaccountchangehistory>)this.amn_Dao.executeQuery_ex(strSql,analysis);		
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Cardtransactionhistory> loadTransHistoryByCardNo(String strCardNo,String strBillType,String strBillId,String strAccountType)
	{
		try
		{
			String strSql=" select transactioncardno,transactiondate,transactiontype,codeno,codename,ccount,price,firstempid,secondempid,thirthempid,a.paymode from  cardtransactionhistory a, sysaccountforpaymode b" +
					" where transactioncardno='"+strCardNo+"' and billtype='"+strBillType+"' and billno='"+strBillId+"' " +
					"  and a.paymode=b.paymode and b.accounttype='"+strAccountType+"' ";
			AnlyResultSet<List<Cardtransactionhistory>> analysis = new AnlyResultSet<List<Cardtransactionhistory>>()
			{
				public List<Cardtransactionhistory> anlyResultSet(ResultSet rs)
				{
					List<Cardtransactionhistory> returnValue = new ArrayList();
					Cardtransactionhistory record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardtransactionhistory();
							record.setTransactioncardno(CommonTool.FormatString(rs.getString("transactioncardno")));
							record.setTransactiondate(CommonTool.getDateMask(rs.getString("transactiondate")));
							record.setTransactiontype(CommonTool.FormatString(rs.getString("transactiontype")));
							if(CommonTool.FormatString(rs.getString("transactiontype")).equals("1"))
							{
								record.setTransactiontypeText("卡销售");
							}
							else if(CommonTool.FormatString(rs.getString("transactiontype")).equals("3"))
							{
								record.setTransactiontypeText("项目消耗");
							}
							else if(CommonTool.FormatString(rs.getString("transactiontype")).equals("4"))
							{
								record.setTransactiontypeText("产品销售");
							}
							else if(CommonTool.FormatString(rs.getString("transactiontype")).equals("5"))
							{
								record.setTransactiontypeText("卡充值");
							}
							else if(CommonTool.FormatString(rs.getString("transactiontype")).equals("6"))
							{
								record.setTransactiontypeText("疗程销售");
							}
							record.setCodeno(CommonTool.FormatString(rs.getString("codeno")));
							record.setCodename(CommonTool.FormatString(rs.getString("codename")));
							record.setFirstempid(CommonTool.FormatString(rs.getString("firstempid")));
							record.setSecondempid(CommonTool.FormatString(rs.getString("secondempid")));
							record.setThirthempid(CommonTool.FormatString(rs.getString("thirthempid")));
							record.setPaymode(CommonTool.FormatString(rs.getString("paymode")));
							record.setCcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ccount"))));
							record.setPrice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("price"))));
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
			List<Cardtransactionhistory> ls= (List<Cardtransactionhistory>)this.amn_Dao.executeQuery_ex(strSql,analysis);		
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Sendpointcard> loadSendInfo(String strCardNo)
	{
		
			String strSql = " select sendcompid,sendbillid,sendtype,senddate,sendempid,sourcebillid,sourcecardno,sourcedate,sourceamt," +
							" sendcardno, sendamt,sendmark,operation,picno,firstdateno "+
							" from sendpointcard with(NOLOCK) where sourcecardno ="+CommonTool.quotedStr(strCardNo)+"" +
							" order by sendcompid,sendbillid,senddate desc ";
			AnlyResultSet<List<Sendpointcard>> analysis = new AnlyResultSet<List<Sendpointcard>>()
			{
				public List<Sendpointcard> anlyResultSet(ResultSet rs)
				{
					List<Sendpointcard> returnValue = new ArrayList();
					Sendpointcard record=null;
					try
					{
						while(rs != null && rs.next())
						{
							
							record = new Sendpointcard();
							record.setBsendcompid(CommonTool.FormatString(rs.getString("sendcompid")));
							record.setBsendbillid(CommonTool.FormatString(rs.getString("sendbillid")));
							record.setBsendtype(CommonTool.FormatInteger(rs.getInt("sendtype")));
							record.setSenddate(CommonTool.getDateMask(rs.getString("senddate")));
							record.setSendempid(CommonTool.FormatString(rs.getString("sendempid")));
							record.setSourcebillid(CommonTool.FormatString(rs.getString("sourcebillid")));
							record.setSourcecardno(CommonTool.FormatString(rs.getString("sourcecardno")));
							record.setSourcedate(CommonTool.getDateMask(rs.getString("sourcedate")));
							record.setSendcardno(CommonTool.FormatString(rs.getString("sendcardno")));
							record.setSendmark(CommonTool.FormatString(rs.getString("sendmark")));
							record.setOperation(CommonTool.FormatString(rs.getString("operation")));
							record.setSourceamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sourceamt"))));
							record.setSendamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("sendamt"))));
							record.setPicno(CommonTool.FormatString(rs.getString("picno")));
							record.setFirstdateno(CommonTool.FormatString(rs.getString("firstdateno")));
							if(CommonTool.FormatInteger(rs.getInt("sendtype"))==0)
							{
								record.setSendtypeText("开卡");
							}
							else if(CommonTool.FormatInteger(rs.getInt("sendtype"))==1)
							{
								record.setSendtypeText("充值");
							}
							else if(CommonTool.FormatInteger(rs.getInt("sendtype"))==2)
							{
								record.setSendtypeText("还款");
							}
							else if(CommonTool.FormatInteger(rs.getInt("sendtype"))==3)
							{
								record.setSendtypeText("反充");
							}
							else if(CommonTool.FormatInteger(rs.getInt("sendtype"))==4)
							{
								record.setSendtypeText("折扣转卡");
							}
							else if(CommonTool.FormatInteger(rs.getInt("sendtype"))==5)
							{
								record.setSendtypeText("收购转卡");
							}
							else if(CommonTool.FormatInteger(rs.getInt("sendtype"))==6)
							{
								record.setSendtypeText("竞争转卡");
							}
							else if(CommonTool.FormatInteger(rs.getInt("sendtype"))==7)
							{
								record.setSendtypeText("并卡");
							}
							else if(CommonTool.FormatInteger(rs.getInt("sendtype"))==8)
							{
								record.setSendtypeText("条码卡赠送");
							}
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
		List<Sendpointcard> ls= (List<Sendpointcard>)this.amn_Dao.executeQuery_ex(strSql,analysis);		
		analysis=null;
		return ls;
	}
	
	
	public List<Cardsoninfo> loadCardsoninfoInfoByCard(String strCardNo)
	{
		try
		{
			String strSql="select cardvesting,cardno,cardtype,salecarddate,parentcardno,membername,memberphone,salebillno,saleamt,songfalg from cardsoninfo where parentcardno='"+strCardNo+"' ";
			AnlyResultSet<List<Cardsoninfo>> analysis = new AnlyResultSet<List<Cardsoninfo>>()
			{
				public List<Cardsoninfo> anlyResultSet(ResultSet rs)
				{
					List<Cardsoninfo> returnValue = new ArrayList();
					Cardsoninfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardsoninfo();
							record.setCardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
							record.setCardno(CommonTool.FormatString(rs.getString("cardno")));
							record.setCardtype(CommonTool.FormatString(rs.getString("cardtype")));
							record.setSalecarddate(CommonTool.getDateMask(rs.getString("salecarddate")));
							record.setParentcardno(CommonTool.FormatString(rs.getString("parentcardno")));
							record.setMembername(CommonTool.FormatString(rs.getString("membername")));
							record.setMemberphone(CommonTool.FormatString(rs.getString("memberphone")));
							record.setSalebillno(CommonTool.FormatString(rs.getString("salebillno")));
							record.setSongfalg(CommonTool.FormatString(rs.getString("songfalg")));
							record.setSaleamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("saleamt"))));
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
			List<Cardsoninfo> ls= (List<Cardsoninfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);		
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean changeCompId(String strCardNo,String strOldCompId,String strNewCompId)
	{
		try
		{
			String strSql="";
			if(!strCardNo.equals("") && !strOldCompId.equals(""))
			{
				//卡资料
				strSql=strSql+" delete cardinfo where cardno='"+strCardNo+"' and cardvesting<>'"+strOldCompId+"' ";
				strSql=strSql+" update cardinfo set cardvesting='"+strNewCompId+"' where cardno='"+strCardNo+"' ";
				
				//会员资料
				strSql=strSql+" if exists(select 1 from memberinfo where membervesting='"+strOldCompId+"' and memberno='"+strCardNo+"' )" +
								" begin  " +
								"	delete memberinfo where memberno='"+strCardNo+"' and membervesting<>'"+strOldCompId+"'" +
								"   update memberinfo set membervesting='"+strNewCompId+"' where  memberno='"+strCardNo+"'" +
								" end " +
								" else " +
								" begin" +
								"  update memberinfo set membervesting='"+strNewCompId+"' where  memberno='"+strCardNo+"'" +
								" end ";
				//账户余额
				strSql=strSql+" if exists(select 1 from cardaccount where cardvesting='"+strOldCompId+"' and cardno='"+strCardNo+"' )" +
								" begin  " +
								"	delete cardaccount where cardno='"+strCardNo+"' and cardvesting<>'"+strOldCompId+"'" +
								"   update cardaccount set cardvesting='"+strNewCompId+"' where  cardno='"+strCardNo+"'" +
								" end " +
								" else " +
								" begin" +
								"  update cardaccount set cardvesting='"+strNewCompId+"' where  cardno='"+strCardNo+"'" +
								" end ";
				//疗程账户
				strSql=strSql+" update cardproaccount set cardvesting='"+strNewCompId+"' where  cardno='"+strCardNo+"' ";
				
				strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
		 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC005','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strCardNo+"','','修改卡归属'+'"+strOldCompId+"至"+strNewCompId+"')";
				
				return this.amn_Dao.executeSql(strSql);
			}
			
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean changeCardType(String strCardNo,String strCardType)
	{
		try
		{
			String strSql=" update cardinfo set cardtype='"+strCardType+"' where cardno='"+strCardNo+"' ";
			strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
	 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC005','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strCardNo+"','','修改卡类型'+'"+strCardType+"')";
			
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	public boolean changeCardState(String strCardNo,String strCardState)
	{
		try
		{
			
			String strSql=" update cardinfo set cardstate="+strCardState+" where cardno='"+strCardNo+"' ";
			
			strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
	 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC005','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strCardNo+"','','修改卡状态'+'"+strCardState+"')";
			
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean validatePrice(String strCardNo)
	{
		try
		{
			String strSql=" select 1 from cardspecialcost where cardno='"+strCardNo+"' ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			{
				public Boolean anlyResultSet(ResultSet rs)
				{
					boolean returnValue = false;
					try
					{
					if(rs != null && rs.next()==true)
					{
						returnValue = false;
					}
					else
					{
						returnValue =  true;
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
			return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postSpecialCost(String strCardNo,BigDecimal costcx1,BigDecimal costcx2,BigDecimal costcx3,BigDecimal costcx4,BigDecimal costcx5,BigDecimal costcx6,BigDecimal costcx7,BigDecimal costcx8,BigDecimal costcx9)
	{
		try
		{
			String strSql=" delete cardspecialcost where cardno= '"+strCardNo+"' ";
			strSql=strSql+" insert cardspecialcost(cardno,costxc1,costxc2,costxc3,costxc4,costxc5,costxc6,costxc7,costxc8,costxc9)" +
					" values('"+strCardNo+"',"+costcx1+","+costcx2+","+costcx3+","+costcx4+","+costcx5+","+costcx6+","+costcx7+","+costcx8+","+costcx9+")  ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
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
			return (Cardspecialcost)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean postChangeOldProAmt(String strCompId,String strCardNo,String oldProItemNo ,BigDecimal oldprocount,BigDecimal oldproamt,String oldpromark,StringBuffer strBufMsg)
	{
		try
		{
			strBufMsg.append("");
			String strSql=" select 1 from  cardaccount where  cardno='"+strCardNo+"' and accounttype=2 and isnull(accountbalance,0)>="+oldproamt+" ";
			int ccount=this.amn_Dao.getRowsCount_Ex(strSql);
			if(ccount==0)
			{
				strBufMsg.append("该卡储值账户不足,不能兑换老疗程");
				return false;
			}
			strSql=" 	declare @proseqno float ";
			strSql=strSql+" select @proseqno=MAX(proseqno)+1 from cardproaccount with(nolock) where cardno='"+strCardNo+"' ";
			strSql=strSql+" insert cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,saledate,cutoffdate,proremark) ";
			strSql=strSql+" values('"+strCompId+"','"+strCardNo+"','"+oldProItemNo+"',isnull( @proseqno,1),0,"+CommonTool.FormatBigDecimal(oldprocount)+",0,"+CommonTool.FormatBigDecimal(oldprocount)+","+CommonTool.FormatBigDecimal(oldproamt)+",0,"+CommonTool.FormatBigDecimal(oldproamt)+",'20240101','"+CommonTool.getCurrDate()+"','"+oldpromark+"') ";
			strSql=strSql+" update cardaccount set   accountbalance=isnull(accountbalance,0)-"+oldproamt+" where  cardno='"+strCardNo+"' and accounttype=2 ";
			strSql=strSql+" if not exists(select 1 from cardaccount where   cardno='"+strCardNo+"' and accounttype=4)  ";
			strSql=strSql+" begin ";
			strSql=strSql+" insert  cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts)  ";
			strSql=strSql+" values('"+strCompId+"','"+strCardNo+"',4,"+oldproamt+" ,0)  ";
			strSql=strSql+" end "; 
			strSql=strSql+" else "; 
			strSql=strSql+" begin ";
			strSql=strSql+" update cardaccount set   accountbalance=isnull(accountbalance,0)+"+oldproamt+" where cardno='"+strCardNo+"' and accounttype=4 ";
			strSql=strSql+" end "; 
			
			strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
	 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC005','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strCardNo+"','','"+oldProItemNo+"')";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	    
	    public boolean postChangeProInfo(String strCompId,String strCardNo,String curChangeProNo,double curChangeSeqnoNo,BigDecimal curChangeLastCount,
	    		BigDecimal curChangeLastAmt,String  curChangeSaler1,BigDecimal curChangeSaler1Amt,String  curChangeSaler2,BigDecimal curChangeSaler2Amt)
	    {
	    	try
	    	{
	    		String strSql="";
	    		String curChangeSaler1inid="";
	    		String curChangeSaler2inid="";
	    		if(!curChangeSaler1.equals(""))
				{
	    			curChangeSaler1inid=this.dataTool.loadEmpInidById(CommonTool.getLoginInfo("COMPID"), curChangeSaler1);
				}
				else
				{
					curChangeSaler1inid="";
				}
	    		if(!curChangeSaler2.equals(""))
				{
	    			curChangeSaler2inid=this.dataTool.loadEmpInidById(CommonTool.getLoginInfo("COMPID"), curChangeSaler2);
				}
				else
				{
					curChangeSaler2inid="";
				}
	    		String strBillNo=this.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mproexchangeinfo", "changebillid", "SP008");
	    		strSql=strSql+" insert mproexchangeinfo(changecompid,changebillid,changedate,changetime,changecardno,changeaccounttype,changeopationerid,changeopationdate,financedate)" +
	    				"values('"+CommonTool.getLoginInfo("COMPID")+"','"+strBillNo+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+strCardNo+"','2','"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrDate()+"')  ";
	    		strSql=strSql+" insert dproexchangeinfo(changecompid,changebillid,changeseqno,changeproid,procount,changeprocount,changeprorate,changeproamt,changebyproaccountamt,changebyaccountamt,changepaymode,changebycashamt,nointernalcardno,changebydyqamt," +
	    				" firstsalerid,firstsalerinid,firstsaleamt,thirdsalerid,thirdsalerinid,thirdsaleamt)" +
	    				" values('"+CommonTool.getLoginInfo("COMPID")+"','"+strBillNo+"',0,'"+curChangeProNo+"',1,"+CommonTool.FormatBigDecimal(new BigDecimal(curChangeLastCount.doubleValue()*(-1)))+",1,"+CommonTool.FormatBigDecimal(new BigDecimal(curChangeLastAmt.doubleValue()*(-1)))+",0,"+CommonTool.FormatBigDecimal(new BigDecimal(curChangeLastAmt.doubleValue()*(-1)))+",'',0,'',0," +
	    				" '"+curChangeSaler1+"','"+curChangeSaler1inid+"',"+CommonTool.FormatBigDecimal(new BigDecimal(curChangeSaler1Amt.doubleValue()*(-1)))+"," +
	    				" '"+curChangeSaler2+"','"+curChangeSaler2inid+"',"+CommonTool.FormatBigDecimal(new BigDecimal(curChangeSaler2Amt.doubleValue()*(-1)))+" ) ";
	    		//减少库存
	    		strSql=strSql+" update cardproaccount set costcount=isnull(costcount,0)+"+CommonTool.FormatBigDecimal(curChangeLastCount)+" ," +
	    				" costamt=isnull(costamt,0)+"+CommonTool.FormatBigDecimal(curChangeLastAmt)+"," +
	    				" lastcount=isnull(lastcount,0)-"+CommonTool.FormatBigDecimal(curChangeLastCount)+"," +
	    				" lastamt=isnull(lastamt,0)-"+CommonTool.FormatBigDecimal(curChangeLastAmt)+" " +
	    				" where  cardno='"+strCardNo+"' and projectno='"+curChangeProNo+"' and proseqno="+curChangeSeqnoNo+" ";
	    		//兑换储值
	    		strSql=strSql+" update cardaccount set   accountbalance=isnull(accountbalance,0)-"+CommonTool.FormatBigDecimal(curChangeLastAmt)+" where  cardno='"+strCardNo+"' and accounttype=4 ";
				strSql=strSql+" if not exists(select 1 from cardaccount where    cardno='"+strCardNo+"' and accounttype=2)  ";
				strSql=strSql+" begin ";
	    		strSql=strSql+" insert  cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts)  ";
				strSql=strSql+" values('"+strCompId+"','"+strCardNo+"',2,"+CommonTool.FormatBigDecimal(curChangeLastAmt)+" ,0)  ";
				strSql=strSql+" end "; 
				strSql=strSql+" else "; 
				strSql=strSql+" begin ";
				strSql=strSql+" update cardaccount set   accountbalance=isnull(accountbalance,0)+"+CommonTool.FormatBigDecimal(curChangeLastAmt)+" where cardno='"+strCardNo+"' and accounttype=2 ";
				strSql=strSql+" end ";
				strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
		 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC005','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strCardNo+"','','兑换储值'+'"+curChangeProNo+"')";
				
				 this.amn_Dao.executeSql(strSql);
				 strSql=" exec upg_handaccountinser_card '"+strCompId+"','"+strCardNo+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getLoginInfo("USERID")+"',"+curChangeLastAmt+" ";
				 return this.amn_Dao.executeSql(strSql);
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    		return false;
	    	}
	    }
	    
		
	    public boolean handModifyCardInfo(String strCardNo,String strAccountType,BigDecimal	oldAccountBalance,BigDecimal	newAccountBalance,BigDecimal	oldAccountDebts,BigDecimal	newAccountDebts,String strChangeMark)
	    {
	    	try
	    	{
	    		String strSql=" select changeseqno=max(changeseqno) from cardaccountchangehistory where changecardno='"+strCardNo+"' and changeaccounttype="+strAccountType+" ";
				AnlyResultSet<Integer> analysis = new AnlyResultSet<Integer>()
				{
					public Integer anlyResultSet(ResultSet rs)
					{
						int returnValue = 0;
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue = CommonTool.FormatInteger(rs.getInt("changeseqno"));
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
				int changeseqno= (Integer)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				strSql=" update cardaccount set accountbalance="+newAccountBalance+" ,accountdebts="+newAccountDebts+" where cardno='"+strCardNo+"' and  accounttype="+strAccountType+" ";
				strSql=strSql+" insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,chagedate,changebeforeamt,changebillno,changemark) " +
						" values('"+CommonTool.getLoginInfo("COMPID")+"','"+strCardNo+"',"+strAccountType+","+(CommonTool.FormatInteger(changeseqno)+1)+",0,"+(newAccountBalance.doubleValue()-oldAccountBalance.doubleValue())+",'"+CommonTool.getCurrDate()+"',"+oldAccountBalance.doubleValue()+",'余额修改','"+strChangeMark+"') ";
				return this.amn_Dao.executeSql(strSql);
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    		return false;
	    	}
	    }
	    
	    //加载十周年验证码
	    public String loadCode()
		{
			try
			{
				StringBuilder sql =  new StringBuilder();
				sql.append("SELECT TOP 1 CIO.CODE FROM COMPANYINFO CIO");
				
				AnlyResultSet<String> analysis = new AnlyResultSet<String>()
				{
					public String anlyResultSet(ResultSet rs)
					{
						String  code = "";
						try
						{
							if(rs != null && rs.next()==true)
							{
								code = CommonTool.FormatString(rs.getString("CODE"));
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
							code = "";
						}
						return code;
					}
				};
				String code= (String)this.amn_Dao.executeQuery_ex(sql.toString(),analysis);
				return code;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
}
