package com.amani.service.CardControl;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;



import com.amani.model.Mcardchangeinfo;
import com.amani.model.McardchangeinfoId;

import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
import com.amani.tools.SysSendMsg;
@Service
public class CC010Service  extends AMN_ModuleService{
	@Autowired
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
			String strSql=" select top 30 changecompid,changebillid,changedate,changetime,changebeforcardno,changeaftercardno,financedate,billflag " +
					" from Mcardchangeinfo " +
					" where changecompid='"+strCompId+"' and isnull(billflag,0)  in (0,1,3) and changetype in (4,5)  order by changedate desc ";
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
							record.setId(new McardchangeinfoId(rs.getString("changecompid"),rs.getString("changebillid"),4));
							record.setBchangecompid(rs.getString("changecompid"));
							record.setBchangebillid(rs.getString("changebillid"));
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
	
	
	public List<Mcardchangeinfo> loadMasterDateByCard(String strCompId,String strCardLoss,String strCardRevice)
	{
		try
		{
			String strSql=" select changecompid,changebillid,changedate,changetime,changebeforcardno,changeaftercardno,financedate,billflag " +
					" from Mcardchangeinfo  " +		
					//" from Mcardchangeinfo ,compchaininfo " +
					//" where changecompid=relationcomp and curcomp='"+strCompId+"' " +
					" where ( changebeforcardno='"+strCardLoss+"' or '"+strCardLoss+"'='')" +
					" and ( changeaftercardno='"+strCardRevice+"' or '"+strCardRevice+"'='')  and changetype in (4,5) ";
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
							record.setId(new McardchangeinfoId(rs.getString("changecompid"),rs.getString("changebillid"),4));
							record.setBchangecompid(rs.getString("changecompid"));
							record.setBchangebillid(rs.getString("changebillid"));
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
	
	public Mcardchangeinfo loadcurMaster(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Mcardchangeinfo mcardchangeinfo where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype=4 ";
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
	
	 //新增主档
	 public Mcardchangeinfo addMastRecord(String strCompId)
	 {
		 Mcardchangeinfo record=new Mcardchangeinfo();
		 record.setId(new McardchangeinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mcardchangeinfo", "changebillid", "SP010"),4));
		 record.setChangedate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setChangetime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setOperationer(CommonTool.getLoginInfo("USERID"));
		 record.setOperationdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setBchangebillid(record.getId().getChangebillid());
		 record.setBchangecompid(strCompId);
		 record.setBchangetype(4);
		 record.setBillflag(0);
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
	 
	 
	 public boolean handReceiveCard(String strCompId,String strBillId,String strCardNo)
	 {
		 try
		 {
			 String strSql=" update cardinfo set cardstate='5' where cardno='"+strCardNo+"' ";
			 strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3) " +
			 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC010','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','"+strCardNo+"','会员卡解挂')";
			 strSql=strSql+" update mcardchangeinfo set billflag=2 where changecompid='"+strCompId+"' and  changebillid='"+strBillId+"' ";
			 return  this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 
	 public boolean entryReceviceCard(String strCompId,String strBillId,String strCurNewCardNo,String strCurNewCardType,String strRemark,String strOldCardNo)
	 {
		 try
		 {
			 String strSql=" update cardinfo set cardstate='3' where cardno='"+strCurNewCardNo+"' ";
			 strSql=strSql+" update cardinfo set cardstate='3' where cardno='"+strOldCardNo+"' ";
			 strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3) " +
			 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC010','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','"+strCurNewCardNo+"','会员卡解挂')";
			 strSql=strSql+" update mcardchangeinfo set billflag=3,changeaftercardno='"+strCurNewCardNo+"',changeaftercardtype='"+strCurNewCardType+"',rechargeremark='"+strRemark+"',financedate='"+CommonTool.getCurrDate()+"' where changecompid='"+strCompId+"' and  changebillid='"+strBillId+"' ";
			 return  this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public boolean handRejectCard(String strCompId,String strBillId,String strCardNo,String strCurNewCardNo)
	 {
		 try
		 {
			 String strSql=" update cardinfo set cardstate=1 where cardno='"+strCurNewCardNo+"' ";
			 strSql=strSql+" update cardinfo set cardstate=4 where cardno='"+strCardNo+"' ";
			 strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
			 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC010','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','"+strCardNo+"','会员卡补卡驳回')";
			 strSql=strSql+" update mcardchangeinfo set billflag=5 where changecompid='"+strCompId+"' and  changebillid='"+strBillId+"' ";
			 return  this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 public boolean handConfirmCard(String strCompId,String strBillId,String strOldCardNo,String strNewCardNo)
	 {
		 try
		 {
			 String strSql="exec upg_Confirm_CardChangeCard '"+strCompId+"','"+strBillId+"','"+CommonTool.getCurrDate()+"','"+strOldCardNo+"','"+strNewCardNo+"',5 ";
			 this.amn_Dao.executeSql(strSql);
			 strSql=" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
			 		" values( '"+CommonTool.getLoginInfo("USERID")+"','CC010','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','"+strOldCardNo+"','会员卡补卡审核')";
			 strSql=strSql+" update mcardchangeinfo set billflag=4,operationer='"+CommonTool.getLoginInfo("USERID")+"'  where changecompid='"+strCompId+"' and  changebillid='"+strBillId+"' ";
			 return  this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public boolean validateMemberInfo(String strCardNo,String strMemberName,String strPhone,String strPcid)
	 {
			try
			{
				String strSql="  if exists(select 1 from memberinfo where memberno='"+strCardNo+"' and ISNULL(memberpaperworkno,'')='' and membername='"+strMemberName+"' ) " +
						" begin " +
						" update memberinfo set memberpaperworkno='"+strPcid+"' where memberno='"+strCardNo+"' " +
						" end ";
				this.amn_Dao.executeSql(strSql);
				strSql=" select 1 from memberinfo where memberno='"+strCardNo+"' and membername='"+strMemberName+"' and  (isnull(membermphone,'')='"+strPhone+"' or isnull(membertphone,'')='"+strPhone+"' ) and isnull(memberpaperworkno,'')='"+strPcid+"'  ";
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
	 
	 /**
	   * 批量发送短信
	   * @param destMobile
	   * @param msgText
	   * @return
	   * @throws Exception
	   */
	public String sendMsg(String destMobile,String msgText) throws Exception{
		return this.sysSendMsg.sendFastMsg(CommonTool.getLoginInfo("COMPID"), destMobile, msgText);
	}
}
