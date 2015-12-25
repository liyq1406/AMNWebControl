package com.amani.service.CardControl;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;



import com.amani.model.Dcardchangeinfo;
import com.amani.model.Dpayinfo;
import com.amani.model.Mcardchangeinfo;
import com.amani.model.McardchangeinfoId;

import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class CC009Service  extends AMN_ModuleService{
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
	

	
	public List<Mcardchangeinfo> loadMasterDateByCompId(String strCompId)
	{
		try
		{
			String strSql=" select changecompid,changebillid,changetype,changedate,changetime,changebeforcardno,changeaftercardno,financedate,billflag " +
					" from Mcardchangeinfo " +
					" where changecompid='"+strCompId+"' and changedate='"+CommonTool.getCurrDate()+"' and changetype in (0,1,2,3,6,7) ";
			AnlyResultSet<List<Mcardchangeinfo>> analysis = new AnlyResultSet<List<Mcardchangeinfo>>()
			{
				public List<Mcardchangeinfo> anlyResultSet(ResultSet rs)
				{
					List<Mcardchangeinfo> returnValue = new ArrayList();
					Mcardchangeinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mcardchangeinfo();
							record.setId(new McardchangeinfoId(rs.getString("changecompid"),rs.getString("changebillid"),rs.getInt("changetype")));
							record.setBchangecompid(rs.getString("changecompid"));
							record.setBchangebillid(rs.getString("changebillid"));
							record.setBchangetype(rs.getInt("changetype"));
							record.setChangedate(CommonTool.getDateMask(rs.getString("changedate")));
							record.setFinancedate(CommonTool.getDateMask(rs.getString("financedate")));
							record.setChangetime(CommonTool.getTimeMask(rs.getString("changetime"), 1));
							record.setChangebeforcardno(rs.getString("changebeforcardno"));
							record.setChangeaftercardno(rs.getString("changeaftercardno"));
							record.setBillflag(CommonTool.FormatInteger(rs.getInt("billflag")));
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
			return (List<Mcardchangeinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Mcardchangeinfo> loadMasterDateByCard(String strCompId,String strSearchContent )
	{
		try
		{
			String strSql=" select changecompid,changebillid,changetype,changedate,changetime,changebeforcardno,changeaftercardno,financedate " +
					" from Mcardchangeinfo ,compchaininfo " +
					" where changecompid=relationcomp and curcomp='"+strCompId+"' " +
					//" and ( changebeforcardno='"+strCardLoss+"' or '"+strCardLoss+"'='')" +
					" and (  changebeforcardno='"+strSearchContent+"' or  changeaftercardno='"+strSearchContent+"' or '"+strSearchContent+"'=changebillid )  and changetype in (0,1,2,3,6,7) ";
			AnlyResultSet<List<Mcardchangeinfo>> analysis = new AnlyResultSet<List<Mcardchangeinfo>>()
			{
				public List<Mcardchangeinfo> anlyResultSet(ResultSet rs)
				{
					List<Mcardchangeinfo> returnValue = new ArrayList();
					Mcardchangeinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mcardchangeinfo();
							record.setId(new McardchangeinfoId(rs.getString("changecompid"),rs.getString("changebillid"),rs.getInt("changetype")));
							record.setBchangecompid(rs.getString("changecompid"));
							record.setBchangebillid(rs.getString("changebillid"));
							record.setBchangetype(rs.getInt("changetype"));
							record.setChangedate(CommonTool.getDateMask(rs.getString("changedate")));
							record.setFinancedate(CommonTool.getDateMask(rs.getString("financedate")));
							record.setChangetime(CommonTool.getTimeMask(rs.getString("changetime"), 1));
							record.setChangebeforcardno(rs.getString("changebeforcardno"));
							record.setChangeaftercardno(rs.getString("changeaftercardno"));
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
			return (List<Mcardchangeinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Mcardchangeinfo loadcurMaster(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Mcardchangeinfo mcardchangeinfo where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' ";
			List<Mcardchangeinfo> ls=(List<Mcardchangeinfo>)this.amn_Dao.findByHql(strSql);
			if(ls!=null && ls.size()>0)
				return ls.get(0);
			else
				return null;
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
			String strSql=" From Dpayinfo dpayinfo  where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='ZK' ";
			return (List<Dpayinfo>)this.amn_Dao.findByHql(strSql);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	public List<Dcardchangeinfo> loadDcardchangeinfoByBill(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Dcardchangeinfo dcardchangeinfo  where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' ";
			return (List<Dcardchangeinfo>)this.amn_Dao.findByHql(strSql);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	 //新增主档
	 public Mcardchangeinfo addMastRecord(String strCompId)
	 {
		 Mcardchangeinfo record=new Mcardchangeinfo();
		 record.setId(new McardchangeinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mcardchangeinfo", "changebillid", "SP010"),0));
		 record.setChangedate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setChangetime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setOperationer(CommonTool.getLoginInfo("USERID"));
		 record.setOperationdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setBchangebillid(record.getId().getChangebillid());
		 record.setBchangecompid(strCompId);
		 record.setBchangetype(0);
		 record.setBillflag(0);
		 record.setIsxnj(0);
		 return record;
	 }
	
	 
	 public boolean handlossCard(Mcardchangeinfo mcardchangeinfo )
	 {
		 try
		 {
			 mcardchangeinfo.setBillflag(1);
			 mcardchangeinfo.setChangedate(CommonTool.setDateMask(mcardchangeinfo.getChangedate()));
			 mcardchangeinfo.setChangetime(CommonTool.setTimeMask(mcardchangeinfo.getChangetime(), 1));
			 mcardchangeinfo.setOperationdate(CommonTool.getCurrDate());
			 this.amn_Dao.save(mcardchangeinfo);
			 String strSql=" update cardinfo set cardstate='9' where cardno='"+mcardchangeinfo.getChangebeforcardno()+"' ";
			 strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
			 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC010','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+mcardchangeinfo.getId().getChangebillid()+"','"+mcardchangeinfo.getChangebeforcardno()+"','会员卡挂失')";
			 return  this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 

	 
	 public boolean handConfirmCard(String strCompId,String strBillId,int changetype,String strOldCardNo,String strNewCardNo)
	 {
		 try
		 {
			 String strSql="exec upg_Confirm_CardChangeCard '"+strCompId+"','"+strBillId+"','"+CommonTool.getCurrDate()+"','"+strOldCardNo+"','"+strNewCardNo+"',"+changetype+" ";
			 this.amn_Dao.executeSql(strSql);
			 strSql=" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
			 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC010','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','"+strOldCardNo+"','会员卡补卡审核')";
			 strSql=strSql+" update mcardchangeinfo set billflag=1 where changecompid='"+strCompId+"' and  changebillid='"+strBillId+"' ";
			 return  this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
		public boolean postChangeInfo(Mcardchangeinfo curMcardchangeinfo,List<Dpayinfo> lsDpayinfofos,List<Dcardchangeinfo> lsDcardchangeinfo)
		{
			try
			{
				this.amn_Dao.saveOrUpdate(curMcardchangeinfo);
				if(lsDpayinfofos!=null && lsDpayinfofos.size()>0)
				{
					this.amn_Dao.saveOrUpdateAll(lsDpayinfofos);
				}
				if(lsDcardchangeinfo!=null && lsDcardchangeinfo.size()>0)
				{
					this.amn_Dao.saveOrUpdateAll(lsDcardchangeinfo);
				}
				return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
}
