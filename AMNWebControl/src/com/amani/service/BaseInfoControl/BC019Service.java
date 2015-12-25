package com.amani.service.BaseInfoControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.Storetargetinfo;
import com.amani.model.Activitycardinfo;
import com.amani.model.Activityrandcardinfo;
import com.amani.model.Dactivitycardforgoods;
import com.amani.model.Dactivitycardforprj;
import com.amani.model.Dgoodsinsert;
import com.amani.model.DgoodsinsertId;
import com.amani.model.Nointernalcardinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class BC019Service extends AMN_ReportService{

	public List<Activitycardinfo> loadMasterInfo(String strCompId,String strSearchDate,String strActivtyCardNo,String strSearchVoucherno)
	{
		try
		{
			String strSql="";
			if(CommonTool.FormatString(strActivtyCardNo).equals(""))
			{
			  strSql=" select cardvesting,b.compname,cardno,a.createdate,validatedate,expericeitemno,prjname,expericecompno,expericebillno," +
					" salegoodstype,e.parentcodevalue,salegoodscompno,salegoodsbillno,salecardtype,cardtypename,salecarddeductamt,salecardcompno,salecardbillno,voucherno,sendquanflag,sendquanno " +
					" from companyinfo b ,compchaininfo c,activitycardinfo a " +
					" left join projectnameinfo d on d.prjno=isnull(a.expericeitemno,'') " +
					" left join commoninfo e on e.infotype='WPTJ' and e.parentcodekey=ISNULL(salegoodstype,'') " +
					" left join cardtypenameinfo f on f.cardtypeno=isnull(a.salecardtype,'') " +
					" where cardvesting=compno  and a.cardvesting=c.relationcomp and c.curcomp='"+strCompId+"'" +
					" and ( isnull(a.createdate,'')='"+CommonTool.setDateMask(strSearchDate)+"' or cardno='"+strActivtyCardNo+"' or voucherno='"+strSearchVoucherno+"'  ) ";
			}
			else
			{
				strSql=" select cardvesting,b.compname,cardno,a.createdate,validatedate,expericeitemno,prjname,expericecompno,expericebillno," +
				" salegoodstype,e.parentcodevalue,salegoodscompno,salegoodsbillno,salecardtype,cardtypename,salecarddeductamt,salecardcompno,salecardbillno,voucherno,sendquanflag,sendquanno " +
				" from companyinfo b ,activitycardinfo a " +
				" left join projectnameinfo d on d.prjno=isnull(a.expericeitemno,'') " +
				" left join commoninfo e on e.infotype='WPTJ' and e.parentcodekey=ISNULL(salegoodstype,'') " +
				" left join cardtypenameinfo f on f.cardtypeno=isnull(a.salecardtype,'') " +
				" where cardvesting=compno  and cardno='"+strActivtyCardNo+"'  ";
	
			}
			AnlyResultSet<List<Activitycardinfo>> analysis = new AnlyResultSet<List<Activitycardinfo>>()
			{
				public List<Activitycardinfo> anlyResultSet(ResultSet rs)
				{
					List<Activitycardinfo> returnValue=new ArrayList();
					Activitycardinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Activitycardinfo();
							record.setCardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
							record.setCardvestingname(CommonTool.FormatString(rs.getString("compname")));
							record.setCardno(CommonTool.FormatString(rs.getString("cardno")));
							record.setCreatedate(CommonTool.getDateMask(rs.getString("createdate")));
							record.setValidatedate(CommonTool.getDateMask(rs.getString("validatedate")));
							record.setExpericeitemno(CommonTool.FormatString(rs.getString("expericeitemno")));
							record.setExpericeitemname(CommonTool.FormatString(rs.getString("prjname")));
							record.setExpericecompno(CommonTool.FormatString(rs.getString("expericecompno")));
							record.setExpericebillno(CommonTool.FormatString(rs.getString("expericebillno")));
							record.setSalegoodstype(CommonTool.FormatString(rs.getString("salegoodstype")));
							record.setSalegoodstypename(CommonTool.FormatString(rs.getString("parentcodevalue")));
							record.setSalegoodscompno(CommonTool.FormatString(rs.getString("salegoodscompno")));
							record.setSalegoodsbillno(CommonTool.FormatString(rs.getString("salegoodsbillno")));
							record.setSalecardcompno(CommonTool.FormatString(rs.getString("salecardcompno")));
							record.setSalecardbillno(CommonTool.FormatString(rs.getString("salecardbillno")));
							record.setVoucherno(CommonTool.FormatString(rs.getString("voucherno")));
							record.setSendquanflag(CommonTool.FormatInteger(rs.getInt("sendquanflag")));
							record.setSendquanno(CommonTool.FormatString(rs.getString("sendquanno")));
							record.setSalecardtype(CommonTool.FormatString(rs.getString("salecardtype")));
							record.setSalecardtypename(CommonTool.FormatString(rs.getString("cardtypename")));
							record.setSalecarddeductamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("salecarddeductamt"))));
							
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
			List<Activitycardinfo> ls=  (List<Activitycardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
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
			" where  cardno='"+strCardNo+"' and isnull(cardtype,1)='"+iCardType+"' and isnull(carduseflag,0)=1 and isnull(cardstate,0)<>2 ";
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
	 
	 

	 public boolean postActivityInfo(String strEntryCompId,String strCardPrefix,String strFromCardNo,String	strToCardNo,String strValidate,String strSaleCardType,
			     BigDecimal dCardDeductAmt,String strProQuanNo,String strPrjJsonParam,String strGoodsJsonParam,String strRandPrjJsonParam,
			     int sendquanflag,int createflag,int totalCount,StringBuffer strMsgBuf)
	 {
		 if(createflag==0)
		 {
			 return createActiveCardByEntry( strEntryCompId, strCardPrefix, strFromCardNo,	strToCardNo, strValidate, strSaleCardType,
			      dCardDeductAmt, strProQuanNo, strPrjJsonParam, strGoodsJsonParam, strRandPrjJsonParam,
			      sendquanflag, createflag, strMsgBuf);
		 }
		 else
		 {
			 return createActiveCardByAuto( strEntryCompId, strCardPrefix, strFromCardNo,	strToCardNo, strValidate, strSaleCardType,
				      dCardDeductAmt, strProQuanNo, strPrjJsonParam, strGoodsJsonParam, strRandPrjJsonParam,
				      sendquanflag, createflag,totalCount, strMsgBuf);
		 }
	 }
	 
	 //自动生成活动批次券
	 public boolean createActiveCardByAuto(String strEntryCompId,String strCardPrefix,String strFromCardNo,String	strToCardNo,String strValidate,String strSaleCardType,
		     BigDecimal dCardDeductAmt,String strProQuanNo,String strPrjJsonParam,String strGoodsJsonParam,String strRandPrjJsonParam,
		     int sendquanflag,int createflag,int totalCount,StringBuffer strMsgBuf)
	 {
		 String strTempCardNo="";
		 String strSql="";
		 List<Dactivitycardforprj>	lsDactivitycardforprj=null;
		 List<Dactivitycardforgoods>	lsDactivitycardforgoods=null;
		 List<Activityrandcardinfo>	lsActivityrandcardinfos=null;
		 if(!CommonTool.FormatString(strPrjJsonParam).equals(""))
		 {
			 	lsDactivitycardforprj=this.dataTool.loadDTOList(strPrjJsonParam, Dactivitycardforprj.class);
				if(lsDactivitycardforprj!=null && lsDactivitycardforprj.size()>0)
				{
					for(int i=0;i<lsDactivitycardforprj.size();i++)
					{
						if(CommonTool.FormatString(lsDactivitycardforprj.get(i).getExpericeitemno()).equals(""))
						{
							lsDactivitycardforprj.remove(i);
							i--;
						}
					}
				}
		 }
		 
		 if(!CommonTool.FormatString(strGoodsJsonParam).equals(""))
		 {
			 lsDactivitycardforgoods=this.dataTool.loadDTOList(strGoodsJsonParam, Dactivitycardforgoods.class);
				if(lsDactivitycardforgoods!=null && lsDactivitycardforgoods.size()>0)
				{
					for(int i=0;i<lsDactivitycardforgoods.size();i++)
					{
						if(CommonTool.FormatString(lsDactivitycardforgoods.get(i).getSalegoodstype()).equals(""))
						{
							lsDactivitycardforgoods.remove(i);
							i--;
						}
					}
				}
		 }
		 
		 if(!CommonTool.FormatString(strRandPrjJsonParam).equals(""))
		 {
			 lsActivityrandcardinfos=this.dataTool.loadDTOList(strRandPrjJsonParam, Activityrandcardinfo.class);
				if(lsActivityrandcardinfos!=null && lsActivityrandcardinfos.size()>0)
				{
					for(int i=0;i<lsActivityrandcardinfos.size();i++)
					{
						if(CommonTool.FormatString(lsActivityrandcardinfos.get(i).getExpericeitemno()).equals(""))
						{
							lsActivityrandcardinfos.remove(i);
							i--;
						}
					}
				}
		 }
		 if(totalCount>0)
		 {
			 while(totalCount>0)
			 {
				 strTempCardNo=CommonTool.loadFixLenthString(12);
				 while(checkRandCode(strTempCardNo)==false)
				 {
					 strTempCardNo=CommonTool.loadFixLenthString(12);
				 } 
				 String strNewRand="";
				 if(CommonTool.FormatInteger(sendquanflag)==1)  //赠送电子券
				 {
					 strNewRand=CommonTool.loadFixLenthString(12);
					 while(checkRandCode(strNewRand)==false)
					 {
						 strNewRand=CommonTool.loadFixLenthString(12);
					 }
				 }
				 strSql=strSql+" insert activitycardinfo(cardvesting,cardno,voucherno,salecardtype,salecarddeductamt,createdate,validatedate,sendquanflag,sendquanno ) " +
				 		" values('"+strEntryCompId+"','"+strTempCardNo+"','"+strCardPrefix+"','"+strSaleCardType+"',"+CommonTool.FormatBigDecimal(dCardDeductAmt).doubleValue()+",'"+CommonTool.getCurrDate()+"','"+CommonTool.setDateMask(strValidate)+"',"+sendquanflag+",'"+strNewRand+"') ";
				 strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardstate)" +
				 		"	values('"+strEntryCompId+"','"+strTempCardNo+"',3,1)  ";
				 if(lsDactivitycardforprj!=null && lsDactivitycardforprj.size()>0)
				 {
					 for(int j=0;j<lsDactivitycardforprj.size();j++)
					 {
						 strSql=strSql+" insert dactivitycardforprj(cardvesting,cardno,expericeitemno,expericeitemprice) " +
						 		" values('"+strEntryCompId+"','"+strTempCardNo+"','"+lsDactivitycardforprj.get(j).getExpericeitemno()+"',"+CommonTool.FormatBigDecimal(lsDactivitycardforprj.get(j).getExpericeitemprice()).doubleValue()+") ";
					 }
				 }
				 if(lsDactivitycardforgoods!=null && lsDactivitycardforgoods.size()>0)
				 {
					 for(int j=0;j<lsDactivitycardforgoods.size();j++)
					 {
						 strSql=strSql+" insert dactivitycardforgoods(cardvesting,cardno,salegoodstype,salegoodsrate) " +
						 		" values('"+strEntryCompId+"','"+strTempCardNo+"','"+lsDactivitycardforgoods.get(j).getSalegoodstype()+"',"+CommonTool.FormatBigDecimal(lsDactivitycardforgoods.get(j).getSalegoodsrate()).doubleValue()+") ";
					 }
				 }
				 
				 if(lsActivityrandcardinfos!=null && lsActivityrandcardinfos.size()>0 && !strNewRand.equals(""))
				 {
					 for(int j=0;j<lsActivityrandcardinfos.size();j++)
					 {
						 strSql=strSql+" insert activityrandcardinfo(randcode,expericeitemno,expericeitemprice,expericeitemcount) " +
						 		" values('"+strNewRand+"','"+lsActivityrandcardinfos.get(j).getExpericeitemno()+"',"+CommonTool.FormatBigDecimal(lsActivityrandcardinfos.get(j).getExpericeitemprice()).doubleValue()+","+CommonTool.FormatBigDecimal(lsActivityrandcardinfos.get(j).getExpericeitemcount()).doubleValue()+") ";
					 }
				 }
				 totalCount=totalCount-1;
			 }
		 }
		 if(!strSql.equals(""))
		 {
			 return this.amn_Dao.executeSql(strSql);
		 }
		 return true;
	 }
	 
	 //手动生成批次活动券
	 public boolean createActiveCardByEntry(String strEntryCompId,String strCardPrefix,String strFromCardNo,String	strToCardNo,String strValidate,String strSaleCardType,
		     BigDecimal dCardDeductAmt,String strProQuanNo,String strPrjJsonParam,String strGoodsJsonParam,String strRandPrjJsonParam,
		     int sendquanflag,int createflag,StringBuffer strMsgBuf)
	 {
		 try
		 {
			 long iFromCardNo=Long.parseLong(strFromCardNo);
			 long iToCardNo=Long.parseLong(strToCardNo);
			 long iTempCardNo=Long.parseLong("9"+strFromCardNo);
			 String strTempCardNo="";
			 String strSql="";
			 List<Dactivitycardforprj>	lsDactivitycardforprj=null;
			 List<Dactivitycardforgoods>	lsDactivitycardforgoods=null;
			 List<Activityrandcardinfo>	lsActivityrandcardinfos=null;
			 if(!CommonTool.FormatString(strPrjJsonParam).equals(""))
			 {
				 	lsDactivitycardforprj=this.dataTool.loadDTOList(strPrjJsonParam, Dactivitycardforprj.class);
					if(lsDactivitycardforprj!=null && lsDactivitycardforprj.size()>0)
					{
						for(int i=0;i<lsDactivitycardforprj.size();i++)
						{
							if(CommonTool.FormatString(lsDactivitycardforprj.get(i).getExpericeitemno()).equals(""))
							{
								lsDactivitycardforprj.remove(i);
								i--;
							}
						}
					}
			 }
			 
			 if(!CommonTool.FormatString(strGoodsJsonParam).equals(""))
			 {
				 lsDactivitycardforgoods=this.dataTool.loadDTOList(strGoodsJsonParam, Dactivitycardforgoods.class);
					if(lsDactivitycardforgoods!=null && lsDactivitycardforgoods.size()>0)
					{
						for(int i=0;i<lsDactivitycardforgoods.size();i++)
						{
							if(CommonTool.FormatString(lsDactivitycardforgoods.get(i).getSalegoodstype()).equals(""))
							{
								lsDactivitycardforgoods.remove(i);
								i--;
							}
						}
					}
			 }
			 
			 if(!CommonTool.FormatString(strRandPrjJsonParam).equals(""))
			 {
				 lsActivityrandcardinfos=this.dataTool.loadDTOList(strRandPrjJsonParam, Activityrandcardinfo.class);
					if(lsActivityrandcardinfos!=null && lsActivityrandcardinfos.size()>0)
					{
						for(int i=0;i<lsActivityrandcardinfos.size();i++)
						{
							if(CommonTool.FormatString(lsActivityrandcardinfos.get(i).getExpericeitemno()).equals(""))
							{
								lsActivityrandcardinfos.remove(i);
								i--;
							}
						}
					}
			 }
			 for(long i=iFromCardNo;i<=iToCardNo;i++)
			 {
				 strTempCardNo=strCardPrefix+(iTempCardNo+"").substring(1,(iTempCardNo+"").length());
				 if(this.checkActivityCardNo(strTempCardNo)==true)
				 {
					 strMsgBuf.append("活动券号"+strTempCardNo+"已经在系统中存在,请确认");
					 return false;
				 }
				 String strNewRand="";
				 if(CommonTool.FormatInteger(sendquanflag)==1)  //赠送电子券
				 {
					 strNewRand=CommonTool.loadFixLenthString(12);
					 while(checkRandCode(strNewRand)==false)
					 {
						 strNewRand=CommonTool.loadFixLenthString(12);
					 }
				 }
				 strSql=strSql+" insert activitycardinfo(cardvesting,cardno,voucherno,salecardtype,salecarddeductamt,createdate,validatedate,sendquanflag,sendquanno ) " +
				 		" values('"+strEntryCompId+"','"+strTempCardNo+"','"+strCardPrefix+"','"+strSaleCardType+"',"+CommonTool.FormatBigDecimal(dCardDeductAmt).doubleValue()+",'"+CommonTool.getCurrDate()+"','"+CommonTool.setDateMask(strValidate)+"',"+sendquanflag+",'"+strNewRand+"') ";
				 strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardstate)" +
			 		"	values('"+strEntryCompId+"','"+strTempCardNo+"',3,1)  ";
				 if(lsDactivitycardforprj!=null && lsDactivitycardforprj.size()>0)
				 {
					 for(int j=0;j<lsDactivitycardforprj.size();j++)
					 {
						 strSql=strSql+" insert dactivitycardforprj(cardvesting,cardno,expericeitemno,expericeitemprice) " +
						 		" values('"+strEntryCompId+"','"+strTempCardNo+"','"+lsDactivitycardforprj.get(j).getExpericeitemno()+"',"+CommonTool.FormatBigDecimal(lsDactivitycardforprj.get(j).getExpericeitemprice()).doubleValue()+") ";
					 }
				 }
				 if(lsDactivitycardforgoods!=null && lsDactivitycardforgoods.size()>0)
				 {
					 for(int j=0;j<lsDactivitycardforgoods.size();j++)
					 {
						 strSql=strSql+" insert dactivitycardforgoods(cardvesting,cardno,salegoodstype,salegoodsrate) " +
						 		" values('"+strEntryCompId+"','"+strTempCardNo+"','"+lsDactivitycardforgoods.get(j).getSalegoodstype()+"',"+CommonTool.FormatBigDecimal(lsDactivitycardforgoods.get(j).getSalegoodsrate()).doubleValue()+") ";
					 }
				 }
				 
				 if(lsActivityrandcardinfos!=null && lsActivityrandcardinfos.size()>0 && !strNewRand.equals(""))
				 {
					 for(int j=0;j<lsActivityrandcardinfos.size();j++)
					 {
						 strSql=strSql+" insert activityrandcardinfo(randcode,expericeitemno,expericeitemprice,expericeitemcount) " +
						 		" values('"+strNewRand+"','"+lsActivityrandcardinfos.get(j).getExpericeitemno()+"',"+CommonTool.FormatBigDecimal(lsActivityrandcardinfos.get(j).getExpericeitemprice()).doubleValue()+","+CommonTool.FormatBigDecimal(lsActivityrandcardinfos.get(j).getExpericeitemcount()).doubleValue()+") ";
					 }
				 }
				 iTempCardNo=iTempCardNo+1;
			 }
			 
			 if(!strSql.equals(""))
			 {
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
	 
	 public boolean checkRandCode(String strRandCode)
	 {
		 String strSql=" select 1 from activityrandcode where randcode='"+strRandCode+"' ";
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
						returnValue =  true;
					}
					return returnValue;
				}
			};
			boolean existsflag= (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			if(existsflag==true)  //已存在电子券 
			{
				return false;
			}
			else
			{
				strSql="insert activityrandcode(randcode,createdate,createperson) " +
						" values('"+strRandCode+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getLoginInfo("USERID")+"' ) ";
				return this.amn_Dao.executeSql(strSql);
			}
	 }
	 
	 public boolean checkActivityCardNo(String strCardNo)
	 {
		 	String strSql=" select 1 from activitycardinfo where cardno='"+strCardNo+"' ";
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
	 
	 
	 public List<Activitycardinfo> loadMasterByRandCard(String strCompId,String strFromCardNo,String strToCardNo)
		{
			try
			{
				String strSql=" select cardvesting,b.compname,cardno,validatedate,salecardtype,cardtypename,salecarddeductamt,sendquanflag,sendquanno " +
						" from companyinfo b ,compchaininfo c,activitycardinfo a " +
						" left join cardtypenameinfo d on d.cardtypeno=isnull(a.salecardtype,'') " +
						" where cardvesting=compno  and a.cardvesting=c.relationcomp and c.curcomp='"+strCompId+"'" +
						" and cardno between '"+strFromCardNo+"' and '"+strToCardNo+"' ";
				AnlyResultSet<List<Activitycardinfo>> analysis = new AnlyResultSet<List<Activitycardinfo>>()
				{
					public List<Activitycardinfo> anlyResultSet(ResultSet rs)
					{
						List<Activitycardinfo> returnValue=new ArrayList();
						Activitycardinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Activitycardinfo();
								record.setCardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
								record.setCardvestingname(CommonTool.FormatString(rs.getString("compname")));
								record.setCardno(CommonTool.FormatString(rs.getString("cardno")));
								record.setSalecardtype(CommonTool.FormatString(rs.getString("salecardtype")));
								record.setSalecardtypename(CommonTool.FormatString(rs.getString("cardtypename")));
								record.setSalecarddeductamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("salecarddeductamt"))));
								record.setValidatedate(CommonTool.getDateMask(rs.getString("validatedate")));
								record.setSendquanflag(CommonTool.FormatInteger(rs.getInt("sendquanflag")));
								record.setSendquanno(CommonTool.FormatString(rs.getString("sendquanno")));
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
				List<Activitycardinfo> ls=  (List<Activitycardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return ls;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		
}
