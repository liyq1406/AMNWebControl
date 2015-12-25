package com.amani.service.CardControl;

import java.math.BigDecimal;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;


import com.amani.model.Cardproaccount;
import com.amani.model.CardproaccountId;
import com.amani.model.Dreturnproinfo;
import com.amani.model.Mreturnproinfo;
import com.amani.model.MreturnproinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class CC017Service  extends AMN_ModuleService{
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
	

	
	public List<Mreturnproinfo> loadMasterDateByCompId(String strCompId)
	{
		try
		{
			String strSql=" select returncompid,returnbillid,returndate,returntime,returncardno,cardvesting " +
					" from Mreturnproinfo " +
					" where returncompid='"+strCompId+"' and returndate='"+CommonTool.getCurrDate()+"' ";
			AnlyResultSet<List<Mreturnproinfo>> analysis = new AnlyResultSet<List<Mreturnproinfo>>()
			{
				public List<Mreturnproinfo> anlyResultSet(ResultSet rs)
				{
					List<Mreturnproinfo> returnValue = new ArrayList();
					Mreturnproinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mreturnproinfo();
							record.setId(new MreturnproinfoId(rs.getString("returncompid"),rs.getString("returnbillid")));
							record.setBreturncompid(rs.getString("returncompid"));
							record.setBreturnbillid(rs.getString("returnbillid"));
							record.setReturndate(CommonTool.getDateMask(rs.getString("returndate")));
							record.setReturntime(CommonTool.getDateMask(rs.getString("returntime")));
							record.setReturncardno(rs.getString("returncardno"));
							record.setCardvesting(rs.getString("cardvesting"));
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
			List<Mreturnproinfo> ls= (List<Mreturnproinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Mreturnproinfo> loadMasterDateByCard(String strCompId,String strSearchContent )
	{
		try
		{
			String strSql=" select returncompid,returnbillid,returndate,returntime,returncardno,cardvesting " +
					" from mreturnproinfo ,compchaininfo " +
					" where returncompid=relationcomp and curcomp='"+strCompId+"' " +
					" and ( returncardno='"+strSearchContent+"'  or '"+strSearchContent+"'=returnbillid )  ";
			AnlyResultSet<List<Mreturnproinfo>> analysis = new AnlyResultSet<List<Mreturnproinfo>>()
			{
				public List<Mreturnproinfo> anlyResultSet(ResultSet rs)
				{
					List<Mreturnproinfo> returnValue = new ArrayList();
					Mreturnproinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mreturnproinfo();
							record.setId(new MreturnproinfoId(rs.getString("returncompid"),rs.getString("returnbillid")));
							record.setBreturncompid(rs.getString("returncompid"));
							record.setBreturnbillid(rs.getString("returnbillid"));
							record.setReturndate(CommonTool.getDateMask(rs.getString("returndate")));
							record.setReturntime(CommonTool.getTimeMask(rs.getString("returntime"), 1));
							record.setReturncardno(rs.getString("returncardno"));
							record.setCardvesting(rs.getString("cardvesting"));
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
			List<Mreturnproinfo> ls= (List<Mreturnproinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Mreturnproinfo loadcurMaster(String strCompId,String strBillId)
	{
		try
		{
			String strSql="select returncompid,returnbillid,returndate,returntime,returncardno,cardvesting," +
					" cardtype,membername,memberphone,curkeepamt,curkeepproamt,cursendamt,curpointamt,changecardno," +
					" opencardtype,opentotalamt,returnkeeptotalamt,changetotalamt,costproamt " +
					" From mreturnproinfo where returncompid='"+strCompId+"' and returnbillid='"+strBillId+"' ";
			AnlyResultSet<Mreturnproinfo> analysis = new AnlyResultSet<Mreturnproinfo>()
			{
				public Mreturnproinfo anlyResultSet(ResultSet rs)
				{
					Mreturnproinfo record=null ;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mreturnproinfo();
							record.setId(new MreturnproinfoId(rs.getString("returncompid"),rs.getString("returnbillid")));
							record.setBreturncompid(rs.getString("returncompid"));
							record.setBreturnbillid(rs.getString("returnbillid"));
							record.setReturndate(CommonTool.getDateMask(rs.getString("returndate")));
							record.setReturntime(CommonTool.getTimeMask(rs.getString("returntime"), 1));
							record.setReturncardno(rs.getString("returncardno"));
							record.setCardvesting(rs.getString("cardvesting"));
							record.setCardtype(rs.getString("cardtype"));
							record.setMembername(rs.getString("membername"));
							record.setMemberphone(rs.getString("memberphone"));
							record.setCurkeepamt(new BigDecimal(rs.getDouble("curkeepamt")));
							record.setCurkeepproamt(new BigDecimal(rs.getDouble("curkeepproamt")));
							record.setCursendamt(new BigDecimal(rs.getDouble("cursendamt")));
							record.setCurpointamt(new BigDecimal(rs.getDouble("curpointamt")));
							record.setChangecardno(rs.getString("changecardno"));
							record.setOpencardtype(rs.getString("opencardtype"));
							record.setOpentotalamt(new BigDecimal(rs.getDouble("opentotalamt")));
							record.setReturnkeeptotalamt(new BigDecimal(rs.getDouble("returnkeeptotalamt")));
							record.setChangetotalamt(new BigDecimal(rs.getDouble("changetotalamt")));
							record.setCostproamt(new BigDecimal(rs.getDouble("costproamt")));
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
			Mreturnproinfo returnvalue= (Mreturnproinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return returnvalue;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Dreturnproinfo> loadDreturnproinfoByBill(String strCompId,String strBillId,int changetype)
	{
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
		String strSql="select returncompid,returnbillid,returnprotype,changeproid,prjname,lastcount,lastamt,changeprocount,changeproamt,changebyaccountamt,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,changemark,changeflag " +
				"  from dreturnproinfo a,projectinfo " +
				"	where a.returnprotype="+changetype+" and  returncompid='"+strCompId+"' and returnbillid='"+strBillId+"'" +
				" and prjmodeId  in ('"+strModeId_p+"','"+strFristModeId_p+"','"+strSecondModeId_p+"','"+strThirthModeId_p+"') and prjno=changeproid  ";
		AnlyResultSet<List<Dreturnproinfo>> analysis = new AnlyResultSet<List<Dreturnproinfo>>()
			{
				public List<Dreturnproinfo> anlyResultSet(ResultSet rs)
				{
					List<Dreturnproinfo> returnValue = new ArrayList();
					Dreturnproinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dreturnproinfo();
							record.setBreturncompid(rs.getString("returncompid"));
							record.setBreturnbillid(rs.getString("returnbillid"));
							record.setBreturnprotype(rs.getInt("returnprotype"));
							record.setChangeproid(rs.getString("changeproid"));
							record.setChangeproname(rs.getString("prjname"));
							record.setLastcount(new BigDecimal(rs.getDouble("lastcount")));
							record.setLastamt(new BigDecimal(rs.getDouble("lastamt")));
							record.setChangeprocount(new BigDecimal(rs.getDouble("changeprocount")));
							record.setChangeproamt(new BigDecimal(rs.getDouble("changeproamt")));
							record.setChangebyaccountamt(new BigDecimal(rs.getDouble("changebyaccountamt")));
							
							record.setFirstsalerid(rs.getString("firstsalerid"));
							record.setFirstsalerinid(rs.getString("firstsalerinid"));
							record.setFirstsaleamt(new BigDecimal(rs.getDouble("firstsaleamt")));
							
							record.setSecondsalerid(rs.getString("secondsalerid"));
							record.setSecondsalerinid(rs.getString("secondsalerinid"));
							record.setSecondsaleamt(new BigDecimal(rs.getDouble("secondsaleamt")));
							
							record.setChangemark(rs.getString("changemark"));
							record.setChangeflag(rs.getInt("changeflag"));
							if(CommonTool.FormatInteger(rs.getInt("changeflag"))==1)
							{
								record.setReturnflag(true);
							}
							else
							{
								record.setReturnflag(false);
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
			List<Dreturnproinfo> ls= (List<Dreturnproinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		
	}
	
	 //新增主档
	 public Mreturnproinfo addMastRecord(String strCompId)
	 {
		 Mreturnproinfo record=new Mreturnproinfo();
		 record.setId(new MreturnproinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mreturnproinfo", "returnbillid", "SP010")));
		 record.setReturndate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setBreturncompid(strCompId);
		 record.setBreturnbillid(record.getId().getReturnbillid());
		 record.setReturntime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 return record;
	 }
	
	 
	 
		public boolean postChangeInfo(Mreturnproinfo curMcardchangeinfo,List<Dreturnproinfo> lsDreturnproinfosA,List<Dreturnproinfo> lsDreturnproinfosB)
		{
			try
			{
				this.amn_Dao.saveOrUpdate(curMcardchangeinfo);
				if(lsDreturnproinfosA!=null && lsDreturnproinfosA.size()>0)
				{
					this.amn_Dao.saveOrUpdateAll(lsDreturnproinfosA);
				}
				if(lsDreturnproinfosB!=null && lsDreturnproinfosB.size()>0)
				{
					this.amn_Dao.saveOrUpdateAll(lsDreturnproinfosB);
				}
				return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
		
		public boolean afterPost(String strCompId,String strBillId)
		{
			String strSql=" exec upg_handChangeProToNewCardInfo '"+strCompId+"','"+strBillId+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		
		
		public List<Dreturnproinfo> loadProInfosByCardNo(String strCompId,String strCardNo)
		{
			try
			{
				 String strModeId=this.getDataTool().loadSysParam(strCompId,"SP059");
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
			     String strSql=" select projectno,proseqno,prjname,lastcount,lastamt,proremark " +
			     		" from cardproaccount,projectinfo " +
			     		" where cardno='"+strCardNo+"'  and isnull(lastcount,0)>0 and  projectno=prjno and  prjmodeId  in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') ";
			     AnlyResultSet<List<Dreturnproinfo>> analysis = new AnlyResultSet<List<Dreturnproinfo>>()
					{
						public List<Dreturnproinfo> anlyResultSet(ResultSet rs)
						{
							List<Dreturnproinfo> returnValue=new ArrayList() ;
							Dreturnproinfo record=null;
							try
							{
								while(rs != null && rs.next()==true)
								{
									record= new Dreturnproinfo();
									record.setChangeproid(CommonTool.FormatString(rs.getString("projectno")));
									record.setBreturnseqno(CommonTool.FormatDouble(rs.getDouble("proseqno")));
									record.setChangeproname(CommonTool.FormatString(rs.getString("prjname")));
									record.setLastcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("lastcount"))));
									record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("lastamt"))));
									record.setChangemark(CommonTool.FormatString(rs.getString("proremark")));
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
					List<Dreturnproinfo> ls=(List<Dreturnproinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
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
