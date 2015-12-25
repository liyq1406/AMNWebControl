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

import com.amani.model.Corpsbuyinfo;

import com.amani.model.Dnointernalcardinfo;
import com.amani.model.Dsalebarcodecardinfo;
import com.amani.model.Mpackageinfo;
import com.amani.model.Msalebarcodecardinfo;
import com.amani.model.MsalebarcodecardinfoId;
import com.amani.model.Nointernalcardinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class CC004Service  extends AMN_ModuleService{
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
		List<Dsalebarcodecardinfo> lsDcardapponline=(List<Dsalebarcodecardinfo>)details;
		if(lsDcardapponline!=null && lsDcardapponline.size()>0)
		{
			 this.amn_Dao.saveOrUpdateAll(lsDcardapponline);
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
	
	public List<Corpsbuyinfo> searchCorpsInfo(String strCurCompId,int searchCorpsTypeKey,String searchCorpsNoKey,int searchCorpsStateKey,String searchFromDateKey,String searchToDateDateKey)
	{
		try
		{
			String strModeId=this.getDataTool().loadSysParam(strCurCompId,"SP059");
		     String strFristModeId="";//第一级模板Id
			 String strSecondModeId="";//第2级模板ID
			 String strThirthModeId="";//第三级模板Id
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.getDataTool().loadCompLvl(strCurCompId);
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
			String strSql=" select top 100 corpscardno,corpstype,corpssource,corpspicno,corpspicname=prjname,corpsamt,operationer,operationdate,corpssate,useincompid,useinbillno,useindate " +
					" from corpsbuyinfo,projectinfo,compchaininfo where ("+searchCorpsTypeKey+"=3 or corpstype="+searchCorpsTypeKey+") " +
							" and ('"+searchCorpsNoKey+"'='' or '"+searchCorpsNoKey+"'=corpscardno ) " +
							" and "+searchCorpsStateKey+"=corpssate  " +
							" and ('"+searchFromDateKey+"'='' or useindate between '"+searchFromDateKey+"' and '"+searchToDateDateKey+"' )" +
							" and corpstype=1 and prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and  prjno = corpspicno and  curcomp=prisource and relationcomp='"+strCurCompId+"'";
			
			strModeId=this.getDataTool().loadSysParam(strCurCompId,"SP063");
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
			strSql=strSql+" union all select top 100 corpscardno,corpstype,corpssource,corpspicno,corpspicname=cardtypename,corpsamt,operationer,operationdate,corpssate,useincompid,useinbillno,useindate " +
			" from corpsbuyinfo,cardtypeinfo,compchaininfo where ("+searchCorpsTypeKey+"=3 or corpstype="+searchCorpsTypeKey+") " +
					" and ('"+searchCorpsNoKey+"'='' or '"+searchCorpsNoKey+"'=corpscardno ) " +
					" and "+searchCorpsStateKey+"=corpssate  " +
					" and ('"+searchFromDateKey+"'='' or useindate between '"+searchFromDateKey+"' and '"+searchToDateDateKey+"' )" +
					" and corpstype=2 and cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')  and  cardtypeno = corpspicno and  curcomp=cardtypesource and relationcomp='"+strCurCompId+"'";
			AnlyResultSet<List<Corpsbuyinfo>> analysis = new AnlyResultSet<List<Corpsbuyinfo>>()
			{
				public List<Corpsbuyinfo> anlyResultSet(ResultSet rs)
				{
					List<Corpsbuyinfo> returnValue = new ArrayList();
					Corpsbuyinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Corpsbuyinfo();
							record.setCorpscardno(CommonTool.FormatString(rs.getString("corpscardno")));
							record.setCorpssource(CommonTool.FormatString(rs.getString("corpssource")));
							record.setCorpspicno(CommonTool.FormatString(rs.getString("corpspicno")));
							record.setCorpspicname(CommonTool.FormatString(rs.getString("corpspicname")));
							record.setOperationer(CommonTool.FormatString(rs.getString("operationer")));
							record.setOperationdate(CommonTool.FormatString(rs.getString("operationdate")));
							record.setUseincompid(CommonTool.FormatString(rs.getString("useincompid")));
							record.setUseinbillno(CommonTool.FormatString(rs.getString("useinbillno")));
							record.setUseindate(CommonTool.getDateMask(rs.getString("useindate")));
							record.setCorpstype(CommonTool.FormatInteger(rs.getInt("corpstype")));
							if(CommonTool.FormatInteger(rs.getInt("corpstype"))==1)
								record.setCorpstypeText("项目团购卡");
							else
								record.setCorpstypeText("会员卡团购卡");
							record.setCorpssate(CommonTool.FormatInteger(rs.getInt("corpssate")));
							if(CommonTool.FormatInteger(rs.getInt("corpssate"))==1)
								record.setCorpssateText("未使用");
							else
								record.setCorpssateText("已使用");
							record.setCorpsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("corpsamt"))));
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
			List<Corpsbuyinfo> ls= (List<Corpsbuyinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Nointernalcardinfo> searchDyqInfos(String searchDyqNoKey,int searchDyqStateKey,String searchBillId, String searchFromDateKey,String searchToDateDateKey)
	{
		try
		{
			String strSql=" select top 200 cardvesting,cardno,cardfaceamt,carduseflag,cardstate,a.usedate,useinproject,lastvalidate,enabledate,packagename " +
					" from  nointernalcardinfo a left join mpackageinfo b on compid=cardvesting and packageno=createcardtype " +
					" where cardtype=1 and  ('"+searchDyqNoKey+"'='' or  cardno like  '%"+searchDyqNoKey+"%')" +
					" and ('"+searchBillId+"'='' or  billid = '"+searchBillId+"')"+
					"   and ("+searchDyqStateKey+"=3 or "+searchDyqStateKey+"=cardstate  )" +
					"  and ('"+searchFromDateKey+"'='' or a.usedate between '"+searchFromDateKey+"' and '"+searchToDateDateKey+"' )" ;
			AnlyResultSet<List<Nointernalcardinfo>> analysis = new AnlyResultSet<List<Nointernalcardinfo>>()
			{
				public List<Nointernalcardinfo> anlyResultSet(ResultSet rs)
				{
					List<Nointernalcardinfo> returnValue = new ArrayList();
					Nointernalcardinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Nointernalcardinfo();
							record.setBcardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
							record.setBcardno(CommonTool.FormatString(rs.getString("cardno")));
							record.setCardfaceamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("cardfaceamt"))));
							record.setCarduseflag(CommonTool.FormatInteger(rs.getInt("carduseflag")));
							if(CommonTool.FormatInteger(rs.getInt("carduseflag"))==1)
								record.setCarduseflagText("项目抵用券");
							else
								record.setCarduseflagText("现金抵用券");
							record.setCardstate(CommonTool.FormatInteger(rs.getInt("cardstate")));
							if(CommonTool.FormatInteger(rs.getInt("cardstate"))==0)
								record.setCardstateText("已登记");
							else if(CommonTool.FormatInteger(rs.getInt("cardstate"))==1)
								record.setCardstateText("可正常使用");
							else
								record.setCardstateText("已使用");	
							record.setUsedate(CommonTool.getDateMask(rs.getString("usedate")));
							record.setUseinproject(CommonTool.FormatString(rs.getString("useinproject")));
							record.setLastvalidate(CommonTool.getDateMask(rs.getString("lastvalidate")));
							record.setEnabledate(CommonTool.getDateMask(rs.getString("enabledate")));
							record.setCreatecardtypename(CommonTool.FormatString(rs.getString("packagename")));
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
			List<Nointernalcardinfo> ls= (List<Nointernalcardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Nointernalcardinfo> searchTmkInfos(String searchTmkNoKey,int searchTmkStateKey)
	{
		try
		{
			String strSql=" select top 50 cardvesting,cardno,cardfaceamt,entrytype,cardstate,usedate,useinproject,lastvalidate,uespassward " +
					" from nointernalcardinfo " +
					" where cardtype=2 and  ('"+searchTmkNoKey+"'='' or cardno like '%"+searchTmkNoKey+"%')" +
					"   and ("+searchTmkStateKey+"=3 or "+searchTmkStateKey+"=cardstate  )" ;
			AnlyResultSet<List<Nointernalcardinfo>> analysis = new AnlyResultSet<List<Nointernalcardinfo>>()
			{
				public List<Nointernalcardinfo> anlyResultSet(ResultSet rs)
				{
					List<Nointernalcardinfo> returnValue = new ArrayList();
					Nointernalcardinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Nointernalcardinfo();
							record.setBcardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
							record.setBcardno(CommonTool.FormatString(rs.getString("cardno")));
							record.setCardfaceamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("cardfaceamt"))));
							record.setEntrytype(CommonTool.FormatInteger(rs.getInt("entrytype")));
							if(CommonTool.FormatInteger(rs.getInt("entrytype"))==0)
								record.setEntrytypeText("正常登记");
							else
								record.setEntrytypeText("赠送");
							record.setCardstate(CommonTool.FormatInteger(rs.getInt("cardstate")));
							if(CommonTool.FormatInteger(rs.getInt("cardstate"))==0)
								record.setCardstateText("已登记");
							else if(CommonTool.FormatInteger(rs.getInt("cardstate"))==1)
								record.setCardstateText("可正常使用");
							else
								record.setCardstateText("已使用");	
							record.setUsedate(CommonTool.getDateMask(rs.getString("usedate")));
							record.setUseinproject(CommonTool.FormatString(rs.getString("useinproject")));
							record.setLastvalidate(CommonTool.getDateMask(rs.getString("lastvalidate")));
							record.setUespassward(CommonTool.FormatInteger(rs.getInt("uespassward")));
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
			List<Nointernalcardinfo> ls= (List<Nointernalcardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Dnointernalcardinfo> searchDyqDetialInfos(String strCurCompId,String strCardNo)
	{
		try
		{
			 String strModeId=this.getDataTool().loadSysParam(strCurCompId,"SP059");
		     String strFristModeId="";//第一级模板Id
			 String strSecondModeId="";//第2级模板ID
			 String strThirthModeId="";//第三级模板Id
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.getDataTool().loadCompLvl(strCurCompId);
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
			String strSql=" select cardvesting,cardno,seqno,ineritemno,ineritemname=prjname,entrycount,usecount,lastcount,entryamt,useamt,lastamt,entryremark,costbillno  " +
					" from dnointernalcardinfo ,projectinfo,compchaininfo where cardno='"+strCardNo+"' " +
				    "  and prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and  prjno = ineritemno and  curcomp=prisource and relationcomp='"+strCurCompId+"'";
			
			AnlyResultSet<List<Dnointernalcardinfo>> analysis = new AnlyResultSet<List<Dnointernalcardinfo>>()
			{
				public List<Dnointernalcardinfo> anlyResultSet(ResultSet rs)
				{
					List<Dnointernalcardinfo> returnValue = new ArrayList();
					Dnointernalcardinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dnointernalcardinfo();
							record.setBcardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
							record.setBcardno(CommonTool.FormatString(rs.getString("cardno")));
							record.setIneritemno(CommonTool.FormatString(rs.getString("ineritemno")));
							record.setIneritemname(CommonTool.FormatString(rs.getString("ineritemname")));
							record.setEntrycount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("entrycount")))));
							record.setUsecount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("usecount")))));
							record.setLastcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("lastcount")))));
							record.setEntryamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("entryamt")))));
							record.setUseamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("useamt")))));
							record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("lastamt")))));
							record.setEntryremark(CommonTool.FormatString(rs.getString("entryremark")));
							record.setCostbillno(CommonTool.FormatString(rs.getString("costbillno")));
							//record.setPackageNo(rs.getString("packageNo"));
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
			List<Dnointernalcardinfo> ls= (List<Dnointernalcardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public Msalebarcodecardinfo addMastRecord(String strCompId)
	{
		try
		{
			Msalebarcodecardinfo record=new Msalebarcodecardinfo();
			record.setId(new MsalebarcodecardinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"msalebarcodecardinfo", "salebillid", "SP008")));
			record.setSaledate(CommonTool.getDateMask(CommonTool.getCurrDate()));
			record.setSaletime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
			record.setOperationer(CommonTool.getLoginInfo("USERID"));
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public List<Mpackageinfo> loadMpackageinfo(String strCurCompId)
	{
			try
			{
				String strSql=" From Mpackageinfo  mpackageinfo where compid='"+strCurCompId+"' and isnull(usedate,'')>'"+CommonTool.getCurrDate()+"' and isnull(usetype,1)=1 ";
				return (List<Mpackageinfo>)this.amn_Dao.findByHql(strSql);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	public boolean validateTmCardNo(String strCompId,String strCardNo,StringBuffer buffcardSource)
	{
		try
		{
			String strSql=" select entrytype from nointernalcardinfo where cardvesting='"+strCompId+"' and cardno='"+strCardNo+"' and cardtype=2 and  isnull(cardstate,0)=0 ";
			AnlyResultSet<String> analysis = new AnlyResultSet<String>()
			{
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue = "";
					try
					{
						if(rs != null && rs.next()==true)
						{
							if(rs.getInt("entrytype")==0)
								returnValue="正常登记卡";
							else
								returnValue="赠送卡";
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
			String entrytype= (String)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			buffcardSource.append(CommonTool.FormatString(entrytype));
			if(CommonTool.FormatString(entrytype).equals(""))
				return false;
			else
				return true;
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public Mpackageinfo validatePackageNo(String strCompid,String strPackageno)
	{
		String strSql="From Mpackageinfo where compid='"+strCompid+"' and packageno='"+strPackageno+"' ";
		Mpackageinfo mpackageinfo=null;
		try {
			List<Mpackageinfo> lsMpackageinfos=amn_Dao.findByHql(strSql);
			if(lsMpackageinfos!=null && lsMpackageinfos.size()>0)
			{
				mpackageinfo=lsMpackageinfos.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mpackageinfo;
	}
	
	public boolean validatePackageNo(String strCompid,String strPackageno,StringBuffer buffPackageName)
	{
		try
		{
			String strSql=" select usemonths from mpackageinfo where compid='"+strCompid+"' and packageno='"+strPackageno+"' and isnull(usedate,'')>'"+CommonTool.getCurrDate()+"' and isnull(usetype,1)=1 ";
			AnlyResultSet<Integer> analysis = new AnlyResultSet<Integer>()
			{
				public Integer anlyResultSet(ResultSet rs)
				{
					int returnValue = -1;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatInteger(rs.getInt("usemonths"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  -1;
					}
					return returnValue;
				}
			};
			int useMonths= (Integer)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			buffPackageName.append(useMonths);
			if(useMonths==-1)
				return false;
			else
				return true;
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean post_after(String strCompId,String strBillId,String strCardNo,String packageno,String strUseCardNo,double costKeepAmt,int curMonths)
	{
		try
		{
			if(curMonths==0)
				curMonths=24;
			if(packageno.equals(""))
				packageno="0";
			String strSql=" update msalebarcodecardinfo set firstsaleempinid=Manageno from msalebarcodecardinfo,staffinfo where salecompid=compno and firstsaleempid=staffno and  salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
			strSql=strSql+" update msalebarcodecardinfo set secondsaleempinid=Manageno from msalebarcodecardinfo,staffinfo where salecompid=compno and secondsaleempid=staffno and  salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
			strSql=strSql+" update nointernalcardinfo set cardstate=1 ,enabledate='"+CommonTool.getCurrDate()+"',oldvalidate='"+CommonTool.datePlusDay(CommonTool.getCurrDate(),curMonths*30)+"',lastvalidate='"+CommonTool.datePlusDay(CommonTool.getCurrDate(),curMonths*30)+"',createcardtype="+Integer.parseInt(packageno)+" where cardno='"+strCardNo+"' ";
			strSql=strSql+" insert dnointernalcardinfo(cardvesting,cardno,seqno,ineritemno,entrycount,usecount,lastcount,entryamt,useamt,lastamt,entryremark,packageNo) " +
						  "	select a.salecompid,a.barcodecardno,b.saleseqno+5,saleproid,saleprocount,0,saleprocount,saleproamt,0,saleproamt,saleremark,packageNo" +
						  "	  from msalebarcodecardinfo a,dsalebarcodecardinfo b where a.salecompid=b.salecompid and a.salebillid =b.salebillid and  a.salecompid='"+strCompId+"' and a.salebillid='"+strBillId+"' ";
			if(costKeepAmt>0)
			{
				 strSql=strSql+"  declare @costaccountseqno float";     
				 strSql=strSql+" select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno='"+strUseCardNo+"' ";
				 strSql=strSql+"  declare @costaccount2lastamt float ";  
				 strSql=strSql+"   select top 1 @costaccount2lastamt=(case when changetype in (0,6,7,8,9,10) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno='"+strUseCardNo+"'  and changeaccounttype=2 order by chagedate desc,changeseqno desc  ";         
				 strSql=strSql+"   if(ISNULL(@costaccount2lastamt,0)=0) ";         
				 strSql=strSql+"   select @costaccount2lastamt=ISNULL(accountbalance,0) from cardaccount where cardno='"+strUseCardNo+"' and accounttype=2  ";        
				 strSql=strSql+"   insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  ";        
				 strSql=strSql+"   values('"+strCompId+"','"+strUseCardNo+"',2,ISNULL(@costaccountseqno,0),2,"+costKeepAmt+",'SK','"+strBillId+"','"+CommonTool.getCurrDate()+"',@costaccount2lastamt) ";         
				 strSql=strSql+" update cardaccount set accountbalance=isnull(accountbalance,0)-"+costKeepAmt+" where cardno='"+strUseCardNo+"' and accounttype='2' ";
					  
			}
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postNewValidate(String strCardNo,String strNewDate)
	{
		try
		{
			if(CommonTool.setDateMask(strNewDate).equals(""))
			{
				return true;
			}
			String strSql=" update nointernalcardinfo set lastvalidate='"+CommonTool.setDateMask(strNewDate)+"' where cardno='"+strCardNo+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
}
