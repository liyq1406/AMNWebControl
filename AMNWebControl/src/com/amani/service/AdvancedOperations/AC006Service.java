package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dnointernalcardinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class AC006Service  extends AMN_ModuleService{
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
	
	public boolean postInerCardindfos(String strCompId,String strFromCard,String strToCard,String strCardType)
	{
		try
		{
			String strSql="exec upg_amn_createcardno '"+strCompId+"','"+strFromCard+"','"+strToCard+"','"+strCardType+"'";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean validateCardRange(String strFromCard,String strToCard,String strCardType)
	{
		try
		{
			String strSql=" select 1 from cardinfo where  cardno between '"+strFromCard+"'  and '"+strToCard+"' ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
	    	{
	    			public Boolean anlyResultSet(ResultSet rs)
	    			{
	    				boolean returnValue = false;
	    				try
	    				{
	    				if(rs != null && rs.next()==true)
	    				{
	    					returnValue =  false;
	    				}
	    				else
	    				{
	    					returnValue =  true;
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
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean validateTmCardRange(String strFromCard,String strToCard,int strCardType)
	{
		try
		{
			String strSql=" select 1 from nointernalcardinfo where cardtype="+strCardType+" and cardno between '"+strFromCard+"'  and '"+strToCard+"' ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
	    	{
	    			public Boolean anlyResultSet(ResultSet rs)
	    			{
	    				boolean returnValue = false;
	    				try
	    				{
	    				if(rs != null && rs.next()==true)
	    				{
	    					returnValue =  false;
	    				}
	    				else
	    				{
	    					returnValue =  true;
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
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	
	
	public boolean validateUseTmCardRange(String strFromCard,String strToCard,int strCardType)
	{
		try
		{
			String strSql=" select 1 from nointernalcardinfo where cardtype="+strCardType+" and cardno between '"+strFromCard+"'  and '"+strToCard+"' and isnull(cardstate,0)<>0 ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
	    	{
	    			public Boolean anlyResultSet(ResultSet rs)
	    			{
	    				boolean returnValue = false;
	    				try
	    				{
	    				if(rs != null && rs.next()==true)
	    				{
	    					returnValue =  false;
	    				}
	    				else
	    				{
	    					returnValue =  true;
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
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean updateRecordCard(String strCompId,String strFromCard,String strToCard,int strCardType)
	{
		try
		{
			String strSql=" update nointernalcardinfo set cardvesting='"+strCompId+"' where cardtype="+strCardType+" and cardno between '"+strFromCard+"'  and '"+strToCard+"' ";
			return this.amn_Dao.executeSql(strSql);

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	

	public boolean updateRecordVesting(String strCompId,String strFromCard,String strToCard)
	{
		try
		{
			String strSql=" update cardinfo set cardvesting='"+strCompId+"' where  cardno between '"+strFromCard+"'  and '"+strToCard+"'  ";
			strSql=strSql+" update memberinfo set membervesting='"+strCompId+"' where memberno between '"+strFromCard+"'  and '"+strToCard+"' ";
			strSql=strSql+" update cardaccount set cardvesting='"+strCompId+"' where cardno between '"+strFromCard+"'  and '"+strToCard+"' ";
			strSql=strSql+" update cardproaccount set cardvesting='"+strCompId+"' where cardno between '"+strFromCard+"'  and '"+strToCard+"' ";
			return this.amn_Dao.executeSql(strSql);

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean validateTgCardRange(String strtgCard)
	{
		try
		{
			String strSql=" select 1 from corpsbuyinfo where  corpscardno='"+strtgCard+"' ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
	    	{
	    			public Boolean anlyResultSet(ResultSet rs)
	    			{
	    				boolean returnValue = false;
	    				try
	    				{
	    				if(rs != null && rs.next()==true)
	    				{
	    					returnValue =  false;
	    				}
	    				else
	    				{
	    					returnValue =  true;
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
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public boolean postRecordCard(String shopId,String startCardNo,String endCardNo,int cardType,int cardUserType,String strCardBef)
	{
		boolean flag=false;
		String strSql="exec upg_amn_createcardno_quan '"+shopId+"','"+startCardNo+"','"+endCardNo+"',"+cardType+","+cardUserType+",0,'"+strCardBef+"','','','',1,0 ";
		try 
		{
			flag=this.amn_Dao.executeSql(strSql);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	public boolean postTgCard(String strTgNo,int iTgType,String corpssource,String strTgContentNo,double dTgAmt)
	{
		String strSql=" insert corpsbuyinfo(corpscardno,corpstype,corpssource,corpspicno,corpsamt,operationer,operationdate,corpssate)" +
		" values('"+strTgNo+"',"+iTgType+",'"+corpssource+"','"+strTgContentNo+"',"+dTgAmt+",'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"',1 ) ";
		
		return this.amn_Dao.executeSql(strSql);
		
		
	}
	
	public boolean loadCorpsPicname(String strCompId,int corpstype,String corpspicno,StringBuffer bufcorpspicname)
	{
		try
		{
			String corpspicname="";
			StringBuffer validateMsg=new StringBuffer();
			if(corpstype==1)
			{
				corpspicname=this.getDataTool().loadProjectName(strCompId, corpspicno, validateMsg);
				bufcorpspicname.append(corpspicname);
				if(corpspicname.equals(""))
				{
					return false;
				}
			}
			else
			{
				corpspicname=this.getDataTool().loadCardTypeName(strCompId, corpspicno, validateMsg);
				bufcorpspicname.append(corpspicname);
				if(corpspicname.equals(""))
				{
					return false;
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
	
	public boolean saveRecordCard_DY(String shopId,String startCardNo,String endCardNo,int cardType,int cardUserType,double dFacePrice,List<Dnointernalcardinfo> lsDnointernalcardinfo,String strCardBef,String strEndDate,String enabledate,int createcardtype)
	{
		boolean flag=false;
		String contionSql="";
		if(lsDnointernalcardinfo!=null && lsDnointernalcardinfo.size()>0)
		{
			for(int i=0;i<lsDnointernalcardinfo.size();i++)
			{
				if(!CommonTool.FormatString(lsDnointernalcardinfo.get(i).getIneritemno()).equals(""))
				{
					if(i==0)
					{
						contionSql=" select s="+i+",p=''"+lsDnointernalcardinfo.get(i).getIneritemno()+"'',c="+lsDnointernalcardinfo.get(i).getEntrycount()+",a="+lsDnointernalcardinfo.get(i).getEntryamt()+" ";
					}
					else
					{
						contionSql=contionSql+" union select s="+i+",p=''"+lsDnointernalcardinfo.get(i).getIneritemno()+"'',c="+lsDnointernalcardinfo.get(i).getEntrycount()+",a="+lsDnointernalcardinfo.get(i).getEntryamt()+" ";
					}
				}
			}			
		}
		
		String strSql="exec upg_amn_createcardno_quan '"+shopId+"','"+startCardNo+"','"+endCardNo+"',"+cardType+","+cardUserType+","+dFacePrice+",'"+strCardBef+"','"+contionSql+"' ,'"+CommonTool.setDateMask(strEndDate)+"','"+CommonTool.setDateMask(enabledate)+"' ,2 , "+createcardtype+" ";
		try 
		{
			flag=this.amn_Dao.executeSql(strSql);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	public boolean postPurchasereCardInfo(String cardvesting,String registercardno, String registercardtype, String membername, String memberphone, String memberbrithday,int membersex,double cardbalance,String registerpcid)
	{
		try
		{
			String strSql=" insert purchaseregister(registercompid,registercardno,registercardtype,membername,memberphone,memberbrithday,membersex,cardbalance,registerperson,registerdate,registertime,cardvesting,registerpcid) " +
					" values('"+CommonTool.getLoginInfo("COMPID")+"','"+registercardno+"','"+registercardtype+"','"+membername+"','"+memberphone+"','"+CommonTool.setDateMask(memberbrithday)+"',"+membersex+","+cardbalance+",'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+cardvesting+"','"+registerpcid+"') ";
			strSql=strSql+" insert cardinfo(cardvesting,cardno,cardtype,membernotocard,salecarddate,cutoffdate,cardstate,cardsource) " +
					" values('"+cardvesting+"','"+registercardno+"','"+registercardtype+"','"+registercardno+"','"+CommonTool.getCurrDate()+"','20340101',4,1 ) ";
			strSql=strSql+"insert memberinfo(membervesting,memberno,cardnotomemberno,membercreatedate,membername,membermphone,membersex,memberbirthday) " +
					" values('"+cardvesting+"','"+registercardno+"','"+registercardno+"','"+CommonTool.getCurrDate()+"','"+membername+"','"+memberphone+"',"+membersex+",'"+CommonTool.setDateMask(memberbrithday)+"')";
			strSql=strSql+" insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdatefrom,accountdateend)" +
					" values('"+cardvesting+"','"+registercardno+"',2,0,'"+CommonTool.getCurrDate()+"','20330101' ) ";
			strSql=strSql+" insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdatefrom,accountdateend)" +
					" values('"+cardvesting+"','"+registercardno+"',5,"+cardbalance+",'"+CommonTool.getCurrDate()+"','20330101' ) ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
}
