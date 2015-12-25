package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.model.Dpayinfo;
import com.amani.model.Evaluation;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;

@Service
public class AC003Service extends AMN_ModuleService{
	public List<Evaluation> loadEvaluations()
	{
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append(" select uuid,discount,cscompid,compname,billid,cscardno,membername,content,membermphone,amt,remarks  ");
		sqlBuffer.append(" from (select uuid,discount,cscompid,billid,cscardno,membername,content,membermphone,amt,remarks ");
		sqlBuffer.append(" from Evaluation,mconsumeinfo with(nolock) left join memberinfo on(memberno=cscardno) ");
		sqlBuffer.append(" where billid=csbillid and discount<>10 and isnull(states,0)=0) a,companyinfo ");
		sqlBuffer.append("  where a.cscompid=compno ");
		List<Evaluation> lsEvaluations=new ArrayList<Evaluation>();
		ResultSet rs=null;
		try {
			rs=amn_Dao.executeQuery(sqlBuffer.toString());
			if(rs!=null)
			{
				while(rs.next())
				{
					Evaluation evaluation=new Evaluation();
					evaluation.setBillid(rs.getString("billid"));
					evaluation.setStrCompName(rs.getString("compname"));
					evaluation.setStrCardNo(rs.getString("cscardno"));
					evaluation.setMembername(rs.getString("membername"));
					evaluation.setContent(rs.getString("content"));
					evaluation.setMembermphone(rs.getString("membermphone"));
					evaluation.setStrCompId(rs.getString("cscompid"));
					evaluation.setUuid(rs.getString("uuid"));
					evaluation.setDiscount(rs.getDouble("discount"));
					evaluation.setAmt(rs.getDouble("amt"));
					evaluation.setRemarks(rs.getString("remarks"));
					lsEvaluations.add(evaluation);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lsEvaluations;
	}
	
	public boolean afterPost(Evaluation curMaster)
	{
		List<Dpayinfo> lsDpayinfos=amn_Dao.findByHql(" From Dpayinfo where paybillid='"+curMaster.getBillid()+"' and paybilltype='SY' and paymode='4' ");
		double storeAmt=0;
		boolean bRet=true;
		if(lsDpayinfos!=null && lsDpayinfos.size()>0)
		{
			for(Dpayinfo dpayinfo:lsDpayinfos)
			{
				storeAmt=dpayinfo.getPayamt().doubleValue();
			}
		}
		if(CommonTool.FormatDouble(curMaster.getAmt())!=0)
		{
			String strBillId=this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mconsumeinfo", "csbillid", "SP007");
			String strSql="insert into mconsumeinfo(cscompid,csbillid,csdate,cscardno,csstarttime,csendtime,csopationerid,csopationdate,financedate)  " +
					" values('"+curMaster.getStrCompId()+"','"+strBillId+"'," +
							"'"+CommonTool.getCurrDate()+"','"+curMaster.getStrCardNo()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("USERID")+"'," +
									"'"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrDate()+"')";
			
			strSql+=" insert into dconsumeinfo(cscompid,csbillid,csinfotype,csseqno,csitemno,csitemunit,csitemcount,csunitprice,csdiscount,csdisprice,csitemamt,cspaymode) " +
					" values('"+curMaster.getStrCompId()+"','"+strBillId+"',1,1,'599999','',1,"+curMaster.getAmt()+",1,"+curMaster.getAmt()+",'4')";
			
			double maxSeq=loadSeq(curMaster.getStrCardNo());
			
			strSql=" insert into cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt,changemark) ";
			strSql+=" select '"+curMaster.getStrCompId()+"','"+curMaster.getStrCardNo()+"',2,"+maxSeq+",1,"+curMaster.getAmt()+",'SY','"+strBillId+"','"+CommonTool.getCurrDate()+"',accountbalance,'小费"+curMaster.getAmt()+"'  ";
			strSql+=" from cardaccount where cardno='"+curMaster.getStrCardNo()+"' and accounttype=2 ";
			strSql+=" update cardaccount set accountbalance=isnull(accountbalance,0)-"+curMaster.getAmt()+" where cardno='"+curMaster.getStrCardNo()+"' and accounttype=2 ";
			
			bRet=amn_Dao.executeSql(strSql);
		}
		if(storeAmt>0 && CommonTool.FormatDouble(curMaster.getDiscount())<10)
		{
			storeAmt=storeAmt*(curMaster.getDiscount()/10);
			double maxSeq=loadSeq(curMaster.getStrCardNo());
			String strSql=" insert into cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt,changemark) ";
			strSql+=" select '"+curMaster.getStrCompId()+"','"+curMaster.getStrCardNo()+"',2,"+maxSeq+",0,"+storeAmt+",'SY','"+curMaster.getBillid()+"','"+CommonTool.getCurrDate()+"',accountbalance,'客户评价"+curMaster.getDiscount()+"折，返还'  ";
			strSql+=" from cardaccount where cardno='"+curMaster.getStrCardNo()+"' and accounttype=2 ";
			strSql+=" update cardaccount set accountbalance=isnull(accountbalance,0)+"+storeAmt+" where cardno='"+curMaster.getStrCardNo()+"' and accounttype=2 ";
			bRet=amn_Dao.executeSql(strSql);
		}
		
		String strSql=" update Evaluation set remarks='"+curMaster.getRemarks()+"' where uuid='"+curMaster.getUuid()+"'";
		bRet=amn_Dao.executeSql(strSql);
		return bRet;
	}
	
	public double loadSeq(String strCardNo)
	{
		String strSql="select MAX(isnull(changeseqno,0))+1 from cardaccountchangehistory where changecardno='"+strCardNo+"' ";
		ResultSet rs=null;
		double seq=0;
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					seq=rs.getDouble(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return seq;
	}
	
	public boolean updBillState(Evaluation evaluation)
	{
		String strSql=" update evaluation set remarks='"+evaluation.getRemarks()+"',states=2 where uuid='"+evaluation.getUuid()+"' ";
		try {
			return amn_Dao.executeSql(strSql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkIsBack(String strUuid)
	{
		String strSql="select count(uuid) from Evaluation where uuid='"+strUuid+"' and isnull(states,0)!=0 ";
		if(amn_Dao.getRowsCount_Ex(strSql)>0)
		{
			return false;
		}
		return true;
	}

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		try {
			amn_Dao.saveOrUpdate(curMaster);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	protected boolean postDetail(Object details) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		return null;
	}
}
