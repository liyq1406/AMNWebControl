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

import com.amani.model.CardTypeAnlisys;
import com.amani.model.Dcardapponline;
import com.amani.model.Mcardapponline;
import com.amani.model.McardapponlineId;

import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class CC002Service  extends AMN_ModuleService{
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
			Mcardapponline record=(Mcardapponline)curMaster;
			if(record!=null && record.getId()!=null)
			{
				String strSql=" update mcardapponline set invalid=1 where cappcompid='"+record.getId().getCappcompid()+"' and cappcompbillid='"+record.getId().getCappcompbillid()+"' ";
				return this.amn_Dao.deleteBySql(strSql);
			}
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
		List<Dcardapponline> lsDcardapponline=(List<Dcardapponline>)details;
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
	
	
	public List<Mcardapponline> loadMasterDateById(String strCompId,String strBillId,int istate)
	{
		try
		{
			String strSql=" select cappcompid,cappcompbillid,cappdate,capptime,cappempid,cappbillflag,cappopationper,cappopationdate,cappconfirmper,cappconfirmdate,cappconfirmcompid " +
					" from mcardapponline,compchaininfo " +
					" where curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=cappcompid and (cappcompid='"+strCompId+"' or '"+strCompId+"'='') and (cappcompbillid='"+strBillId+"' or '"+strBillId+"'='')" +
					"  and  (isnull(cappbillflag,0)="+istate+" or  "+istate+"=3 ) and isnull(invalid,0)=0 order by cappdate desc ";
			AnlyResultSet<List<Mcardapponline>> analysis = new AnlyResultSet<List<Mcardapponline>>()
			{
				public List<Mcardapponline> anlyResultSet(ResultSet rs)
				{
					List<Mcardapponline> returnValue = new ArrayList();
					Mcardapponline record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mcardapponline();
							record.setBcappcompid(CommonTool.FormatString(rs.getString("cappcompid")));
							record.setBcappcompbillid(CommonTool.FormatString(rs.getString("cappcompbillid")));
							record.setCappdate(CommonTool.getDateMask(rs.getString("cappdate")));
							record.setCapptime(CommonTool.getTimeMask(rs.getString("capptime"), 1));
							record.setCappbillflag(CommonTool.FormatInteger(rs.getInt("cappbillflag")));
							if(CommonTool.FormatInteger(record.getCappbillflag())==0)
							{
								record.setCappbillflagText("申请中");
							}
							else if(CommonTool.FormatInteger(record.getCappbillflag())==1)
							{
								record.setCappbillflagText("已同意");
							}
							else
							{
								record.setCappbillflagText("已领用");
							}
							record.setCappempid(CommonTool.FormatString(rs.getString("cappempid")));
							record.setCappopationper(CommonTool.FormatString(rs.getString("cappopationper")));
							record.setCappopationdate(CommonTool.getDateMask(rs.getString("cappopationdate")));
							record.setCappconfirmper(CommonTool.FormatString(rs.getString("cappconfirmper")));
							record.setCappconfirmdate(CommonTool.getDateMask(rs.getString("cappconfirmdate")));
							record.setCappconfirmcompid(CommonTool.FormatString(rs.getString("cappconfirmcompid")));
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
			return (List<Mcardapponline>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	
	public List<Dcardapponline> loadDetialById(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Dcardapponline dcardapponline where cappcompid='"+strCompId+"' and cappcompbillid='"+strBillId+"' ";
			return (List<Dcardapponline>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	 //新增主档
	 public Mcardapponline addMastRecord(String strCompId)
	 {
		 Mcardapponline record=new Mcardapponline();
		 record.setId(new McardapponlineId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mcardapponline", "cappcompbillid", "SP012")));
		 record.setCappdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setCapptime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		
		 record.setCappempid(CommonTool.getLoginInfo("USERID"));
		 record.setCappempText(CommonTool.getLoginInfo("USERNAME"));
		// record.setCappopationper(CommonTool.getLoginInfo("USERID"));
		 //record.setCappopationdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setBcappcompbillid(record.getId().getCappcompbillid());
		 record.setBcappcompid(strCompId);
		 record.setCappbillflag(0);
		 return record;
	 }
	
	 //审核申请单
	 public boolean postConfirm(String strCompId,String strBillId)
	 {
		 try
		 {
			 String strSql=" update mcardapponline set cappbillflag=1,cappconfirmper='"+CommonTool.getLoginInfo("USERID")+"',cappconfirmdate='"+CommonTool.getCurrDate()+"', cappconfirmcompid='"+CommonTool.getLoginInfo("COMPID")+"' " +
			 		"where cappcompid='"+strCompId+"' and cappcompbillid='"+strBillId+"' ";
			 return this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	
	 public List<CardTypeAnlisys> loadCardTypeAnlisys(String strCompId)
	 {
			String strSql=" select b.cardtypeno,b.cardtypename,cardcount=COUNT(cardno) from cardinfo a,cardtypenameinfo b" +
					" where cardstate=1 and a.cardtype=b.cardtypeno and  cardvesting='"+strCompId+"' " +
					" group by b.cardtypeno,b.cardtypename ";
			AnlyResultSet<List<CardTypeAnlisys>> analysis = new AnlyResultSet<List<CardTypeAnlisys>>()
			{
				public List<CardTypeAnlisys> anlyResultSet(ResultSet rs)
				{
					List<CardTypeAnlisys> returnValue = new ArrayList();
					CardTypeAnlisys record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new CardTypeAnlisys();
							record.setStrCardTypeNo(CommonTool.FormatString(rs.getString("cardtypeno")));
							record.setStrCardTypeName(CommonTool.FormatString(rs.getString("cardtypename")));
							record.setNCardtypeCount(CommonTool.FormatInteger(rs.getInt("cardcount")));
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
			List<CardTypeAnlisys> ls= (List<CardTypeAnlisys>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
	 }
}
