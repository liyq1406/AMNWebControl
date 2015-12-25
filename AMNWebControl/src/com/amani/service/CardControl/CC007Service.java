package com.amani.service.CardControl;

import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Activitycardinfo;
import com.amani.model.Dpayinfo;
import com.amani.model.Dsalecardproinfo;
import com.amani.model.Msalecardinfo;
import com.amani.model.MsalecardinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
import com.amani.tools.SysSendMsg;
@Service
public class CC007Service  extends AMN_ModuleService{
	protected static SysSendMsg sysSendMsg;
	public SysSendMsg getSysSendMsg() {
		return sysSendMsg;
	}
	public void setSysSendMsg(SysSendMsg sysSendMsg) {
		this.sysSendMsg = sysSendMsg;
	}
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
	
	
	public List<Msalecardinfo> loadMasterinfo(String strCompId)
	{
		try
		{
			String strSql="select salecompid,salebillid,saledate,salecardno from msalecardinfo  where  salecompid='"+strCompId+"'  and saledate ='"+CommonTool.getCurrDate()+"' ";
			AnlyResultSet<List<Msalecardinfo>> analysis = new AnlyResultSet<List<Msalecardinfo>>()
			{
				public List<Msalecardinfo> anlyResultSet(ResultSet rs)
				{
					List<Msalecardinfo> returnValue = new ArrayList();
					Msalecardinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Msalecardinfo();
							record.setId(new MsalecardinfoId(rs.getString("salecompid"),rs.getString("salebillid")));
							record.setBsalecompid(rs.getString("salecompid"));
							record.setBsalebillid(rs.getString("salebillid"));
							record.setSalecardno(rs.getString("salecardno"));
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
			List<Msalecardinfo> ls= (List<Msalecardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Msalecardinfo> searchMasterinfo(String strCompId,String strSearchKey)
	{
		try
		{
			String strSql="select salecompid,salebillid,saledate,salecardno from msalecardinfo  " +
					      "	where  salecompid='"+strCompId+"' " +
					      " and (saledate ='"+CommonTool.setDateMask(strSearchKey)+"' " +
					      "    or salebillid='"+strSearchKey+"'  or salecardno='"+strSearchKey+"' )";
			AnlyResultSet<List<Msalecardinfo>> analysis = new AnlyResultSet<List<Msalecardinfo>>()
			{
				public List<Msalecardinfo> anlyResultSet(ResultSet rs)
				{
					List<Msalecardinfo> returnValue = new ArrayList();
					Msalecardinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Msalecardinfo();
							record.setId(new MsalecardinfoId(rs.getString("salecompid"),rs.getString("salebillid")));
							record.setBsalecompid(rs.getString("salecompid"));
							record.setBsalebillid(rs.getString("salebillid"));
							record.setSalecardno(rs.getString("salecardno"));
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
			List<Msalecardinfo> ls= (List<Msalecardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//新增开卡单 SP008
	public Msalecardinfo addMsalecardinfo(String strCompId)
	{
		try
		{
			Msalecardinfo record=new Msalecardinfo();
			record.setId(new MsalecardinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"msalecardinfo", "salebillid", "SP008")));
			record.setBsalebillid(record.getId().getSalebillid());
			record.setBsalecompid(record.getId().getSalecompid());
			record.setSaledate(CommonTool.getDateMask(CommonTool.getCurrDate()));
			record.setSaletime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
			record.setSaleroperator(CommonTool.getLoginInfo("USERID"));
			record.setIsxnj(0);
			record.setSaleroperdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Msalecardinfo loadMsalecardinfo(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Msalecardinfo msalecardinfo where  salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
			List<Msalecardinfo> ls=(List<Msalecardinfo>)this.amn_Dao.findByHql(strSql);
			if(ls!=null && ls.size()>0)
			{
				return ls.get(0);
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Dsalecardproinfo> loadDsalecardproinfo(String strCompId,String strBillId)
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
			 
			String strSql=" select saleproid,prjname,saleprotype,saleprocardcount,saleprocount,sendprocount,saleproamt,procutoffdate,saleproremark " +
					" from dsalecardproinfo,projectinfo " +
					" where  salecompid='"+strCompId+"' and salebillid='"+strBillId+"'  and salebilltype=1  " +
					" and prjmodeId  in ('"+strModeId_p+"','"+strFristModeId_p+"','"+strSecondModeId_p+"','"+strThirthModeId_p+"') and saleproid=prjno  ";
			AnlyResultSet<List<Dsalecardproinfo>> analysis = new AnlyResultSet<List<Dsalecardproinfo>>()
			{
				public List<Dsalecardproinfo> anlyResultSet(ResultSet rs)
				{
					List<Dsalecardproinfo> returnValue = new ArrayList();
					Dsalecardproinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dsalecardproinfo();
							record.setSaleproid(rs.getString("saleproid"));
							record.setSaleproname(rs.getString("prjname"));
							record.setSaleprotype(CommonTool.FormatInteger(rs.getInt("saleprotype")));
							record.setSaleprocardcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("saleprocardcount")))));
							record.setSaleprocount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("saleprocount")))));
							record.setSendprocount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sendprocount")))));
							record.setSaleproamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("saleproamt")))));
							record.setProcutoffdate(CommonTool.getDateMask(rs.getString("procutoffdate")));
							record.setSaleproremark(CommonTool.FormatString(rs.getString("saleproremark")));
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
			List<Dsalecardproinfo> ls= (List<Dsalecardproinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;				
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
			String strSql=" From Dpayinfo dpayinfo  where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='SK' ";
			return (List<Dpayinfo>)this.amn_Dao.findByHql(strSql);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public boolean postSaleInfo(Msalecardinfo curMsalecardinfo,List<Dpayinfo> lsDpayinfofos,List<Dsalecardproinfo> lsDsalecardproinfos)
	{
		try
		{
			this.amn_Dao.saveOrUpdate(curMsalecardinfo);
			if(lsDpayinfofos!=null && lsDpayinfofos.size()>0)
			{
				this.amn_Dao.saveOrUpdateAll(lsDpayinfofos);
			}
			if(lsDsalecardproinfos!=null && lsDsalecardproinfos.size()>0)
			{
				this.amn_Dao.saveOrUpdateAll(lsDsalecardproinfos);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean afterPost(String strCompId,String strBillId,String strCardType)
	{
		try
		{
			String strSql=" exec upg_handsalecardbill_card '"+strCompId+"','"+strBillId+"','"+strCardType+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean handSonCardByTargetCard(String strCompId,String strCardNo,String strCardType,String strName,String strPhone,double dOpenCardAmt,String strBillNo)
	{
		try
		{
			double dTargetAmt=dOpenCardAmt;
			int    iSonCount=100;
			String strTargetCardNo;
			String strSql="";
			if(dTargetAmt<5000)
			{
				return true;
			}
			else
			{
				while (dTargetAmt>=5000)
				{
					strTargetCardNo=strCardNo+"_"+iSonCount;
					dTargetAmt=dTargetAmt-5000;
					strSql=strSql+"  insert cardsoninfo (cardvesting,cardno,cardtype,salecarddate,parentcardno,membername,memberphone,salebillno,saleamt,songfalg) " +
							" values('"+strCompId+"','"+strTargetCardNo+"','"+strCardType+"','"+CommonTool.getCurrDate()+"','"+strCardNo+"','"+strName+"','"+strPhone+"','"+strBillNo+"',"+5000+",'"+iSonCount+"')" ;
					iSonCount=iSonCount+1;
				}
				strTargetCardNo=strCardNo+"_"+iSonCount;
				strSql=strSql+" insert cardsoninfo (cardvesting,cardno,cardtype,salecarddate,parentcardno,membername,memberphone,salebillno,saleamt,songfalg) " +
				" values('"+strCompId+"','"+strTargetCardNo+"','"+strCardType+"','"+CommonTool.getCurrDate()+"','"+strCardNo+"','"+strName+"','"+strPhone+"','"+strBillNo+"',"+dTargetAmt+",'"+iSonCount+"')" ;
				this.amn_Dao.executeStatement(strSql);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public Double loadCorpsbuyinfoById(String strCardNo,String strCardType)
	{
		try
		{
		
			String strSql=" select corpsamt from corpsbuyinfo where corpscardno='"+strCardNo+"' and corpstype=2 and corpspicno='"+strCardType+"' ";
			AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
			{
				public Double anlyResultSet(ResultSet rs)
				{
					Double returnValue = 0.0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatDouble(rs.getDouble("corpsamt"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			double dAmt=(Double)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return dAmt;		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0.0;
		}
	}
	
	public boolean loadDfgwCard(String strCardNo)
	{
		String strSql=" select count(1) from corpsbuyinfo where corpscardno='"+strCardNo+"' and corpstype=1 and corpssource='03' and corpssate=2 and useindate='"+CommonTool.getCurrDate()+"' ";
		if(amn_Dao.getRowsCount_Ex(strSql)>0)
		{
			return true;
		}
		return false;
	}
	
	public Activitycardinfo loadActifyCardinfo(String strCardNo,String strCardType)
	{
		try
		{
		
			String strSql=" select cardno,salecardflag,validatedate,salecarddeductamt from activitycardinfo " +
					" where cardno='"+strCardNo+"' and salecardtype='"+strCardType+"' ";
			AnlyResultSet<Activitycardinfo> analysis = new AnlyResultSet<Activitycardinfo>()
			{
				public Activitycardinfo anlyResultSet(ResultSet rs)
				{
					Activitycardinfo returnValue = new Activitycardinfo();
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue.setCardno(CommonTool.FormatString(rs.getString("cardno")));
							returnValue.setSalecardflag(CommonTool.FormatInteger(rs.getInt("salecardflag")));
							returnValue.setValidatedate(CommonTool.FormatString(rs.getString("validatedate")));
							returnValue.setSalecarddeductamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("salecarddeductamt"))) );
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			Activitycardinfo record=(Activitycardinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return record;		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public String loadDzqByActifyNo(String strCardNo)
	{
		try
		{
			String strSql=" select sendquanno from activitycardinfo where cardno='"+strCardNo+"' and isnull(sendquanflag,0)=1 ";
			AnlyResultSet<String> analysis = new AnlyResultSet<String>()
			{
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue = "";
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatString(rs.getString("sendquanno"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			String strDzqNo=(String)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return strDzqNo;		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "";
		}
	}
	
	
	public double loadSaleCardTypePromotions(String strCompId,String strCardType)
	{
		String strSql=" select promotionsvalue from promotionsinfo where promotionsstore='"+strCompId+"' and promotionstype=2 and promotionscode='"+strCardType+"' and startdate<='"+CommonTool.getCurrDate()+"'  and enddate>='"+CommonTool.getCurrDate()+"' and isnull(invalid,0)=0 "; 
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue = 0.0;
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
					returnValue =  0.0;
				}
				return returnValue;
			}
		};
		double promotionsvalue= (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		return promotionsvalue;			
	}
	public double loadSaleCardNoPromotions(String strCompId,String strCardNo)
	{
		String strSql=" select promotionsvalue from promotionsinfo where promotionsstore='"+strCompId+"' and promotionstype=4 and promotionscode='"+strCardNo+"' and startdate<='"+CommonTool.getCurrDate()+"'  and enddate>='"+CommonTool.getCurrDate()+"' and isnull(invalid,0)=0 "; 
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue = 0.0;
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
					returnValue =  0.0;
				}
				return returnValue;
			}
		};
		double promotionsvalue= (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		return promotionsvalue;			
	}
	
	 /**
	   * 批量发送短信
	   * @param destMobile
	   * @param msgText
	   * @return
	   * @throws Exception
	   */
	public String sendMsg(String destMobile,String msgText) throws Exception{
		return this.sysSendMsg.sendMsg(CommonTool.getLoginInfo("COMPID"), destMobile, msgText);
	}
	
}
