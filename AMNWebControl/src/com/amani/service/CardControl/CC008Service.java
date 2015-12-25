package com.amani.service.CardControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dpayinfo;
import com.amani.model.Dsalecardproinfo;
import com.amani.model.Mcardrechargeinfo;
import com.amani.model.McardrechargeinfoId;

import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class CC008Service  extends AMN_ModuleService{
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
	
	
	public List<Mcardrechargeinfo> loadMasterinfo(String strCompId)
	{
		try
		{
			String strSql="select rechargecompid,rechargebillid,rechargedate,rechargecardno from mcardrechargeinfo  where  rechargecompid='"+strCompId+"'  and rechargedate ='"+CommonTool.getCurrDate()+"' ";
			AnlyResultSet<List<Mcardrechargeinfo>> analysis = new AnlyResultSet<List<Mcardrechargeinfo>>()
			{
				public List<Mcardrechargeinfo> anlyResultSet(ResultSet rs)
				{
					List<Mcardrechargeinfo> returnValue = new ArrayList();
					Mcardrechargeinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mcardrechargeinfo();
							record.setId(new McardrechargeinfoId(rs.getString("rechargecompid"),rs.getString("rechargebillid")));
							record.setBrechargecompid(rs.getString("rechargecompid"));
							record.setBrechargebillid(rs.getString("rechargebillid"));
							record.setRechargecardno(rs.getString("rechargecardno"));
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
			List<Mcardrechargeinfo> ls= (List<Mcardrechargeinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Mcardrechargeinfo> searchMasterinfo(String strCompId,String strSearchKey)
	{
		try
		{
			String strSql="select rechargecompid,rechargebillid,rechargedate,rechargecardno from mcardrechargeinfo  " +
					      "	where  rechargecompid='"+strCompId+"' " +
					      " and (rechargedate ='"+CommonTool.setDateMask(strSearchKey)+"' " +
					      "    or rechargebillid='"+strSearchKey+"'  or rechargecardno='"+strSearchKey+"' )";
			AnlyResultSet<List<Mcardrechargeinfo>> analysis = new AnlyResultSet<List<Mcardrechargeinfo>>()
			{
				public List<Mcardrechargeinfo> anlyResultSet(ResultSet rs)
				{
					List<Mcardrechargeinfo> returnValue = new ArrayList();
					Mcardrechargeinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mcardrechargeinfo();
							record.setId(new McardrechargeinfoId(rs.getString("rechargecompid"),rs.getString("rechargebillid")));
							record.setBrechargecompid(rs.getString("rechargecompid"));
							record.setBrechargebillid(rs.getString("rechargebillid"));
							record.setRechargecardno(rs.getString("rechargecardno"));
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
			List<Mcardrechargeinfo> ls= (List<Mcardrechargeinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
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
	public Mcardrechargeinfo addMcardrechargeinfo(String strCompId)
	{
		try
		{
			Mcardrechargeinfo record=new Mcardrechargeinfo();
			record.setId(new McardrechargeinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mcardrechargeinfo", "rechargebillid", "SP009")));
			record.setBrechargebillid(record.getId().getRechargebillid());
			record.setBrechargecompid(record.getId().getRechargecompid());
			record.setRechargedate(CommonTool.getDateMask(CommonTool.getCurrDate()));
			record.setRechargetime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
			record.setIsxnj(0);
			record.setOperationer(CommonTool.getLoginInfo("USERID"));
			record.setOperationdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Mcardrechargeinfo loadMsalecardinfo(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Mcardrechargeinfo mcardrechargeinfo where  rechargecompid='"+strCompId+"' and rechargebillid='"+strBillId+"' ";
			List<Mcardrechargeinfo> ls=(List<Mcardrechargeinfo>)this.amn_Dao.findByHql(strSql);
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
					" where  salecompid='"+strCompId+"' and salebillid='"+strBillId+"'  and salebilltype=2 " +
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
			String strSql=" From Dpayinfo dpayinfo  where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='CZ' ";
			return (List<Dpayinfo>)this.amn_Dao.findByHql(strSql);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public boolean postSaleInfo(Mcardrechargeinfo curMcardrechargeinfo,List<Dpayinfo> lsDpayinfofos,List<Dsalecardproinfo> lsDsalecardproinfos)
	{
		try
		{
			this.amn_Dao.saveOrUpdate(curMcardrechargeinfo);
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
			String strSql=" exec upg_handrechargecardbill_card '"+strCompId+"','"+strBillId+"','"+strCardType+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public double loadSaleCardTypePromotions(String strCompId,String strCardType)
	{
		String strSql=" select promotionsvalue from promotionsinfo where promotionsstore='"+strCompId+"' and promotionstype=3 and promotionscode='"+strCardType+"' and startdate<='"+CommonTool.getCurrDate()+"'  and enddate>='"+CommonTool.getCurrDate()+"' and isnull(invalid,0)=0 "; 
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
		String strSql=" select promotionsvalue from promotionsinfo where promotionsstore='"+strCompId+"' and promotionstype=5 and promotionscode='"+strCardNo+"' and startdate<='"+CommonTool.getCurrDate()+"'  and enddate>='"+CommonTool.getCurrDate()+"' and isnull(invalid,0)=0 "; 
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
	
	
}
