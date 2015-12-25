package com.amani.service.CardControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.Var;
import org.springframework.stereotype.Service;

import com.amani.bean.CardYears;
import com.amani.model.Yearcarddetal;
import com.amani.model.Yearcardinof;
import com.amani.model.Yearselldetal;
import com.amani.model.YearselldetalId;
import com.amani.model.Yearsellinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.sun.mail.iap.Literal;

@Service
public class CC019Service extends AMN_ModuleService{

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean postDetail(Object details) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Yearcarddetal> loadYears(List<Yearcarddetal> lsList)
	{
		StringBuffer buffer=new StringBuffer();
		buffer.append(" select c.cardno from cardinfo d,( ");
		buffer.append(" select cardno from yearsellinfo a,( ");
		buffer.append(" select compid,billid from yearselldetal where iteminid in( ");
		for(int i=0; i<lsList.size();i++)
		{
			if(i==0)
			{
				buffer.append(" '"+lsList.get(i).getIteminid()+"' ");
			}
			else 
			{
				buffer.append(" ,'"+lsList.get(i).getIteminid()+"' ");
			}
		}
		buffer.append(" ) ) b ");
		buffer.append(" where a.compid=b.compid ");
		buffer.append(" and a.billid=b.billid ");
		buffer.append(" group by cardno ");
		buffer.append(" ) c ");
		buffer.append(" where d.cardno=c.cardno ");
		buffer.append(" and cardstate=4 ");
		ResultSet rs=null;
		List<Yearcarddetal> lsYearcarddetals=new ArrayList<Yearcarddetal>();
		try {
			rs=amn_Dao.executeQuery(buffer.toString());
			if(rs!=null)
			{
				while(rs.next())
				{
					Yearcarddetal yearcarddetal=new Yearcarddetal();
					yearcarddetal.setPhone(rs.getString("cardno"));
					lsYearcarddetals.add(yearcarddetal);
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
		return lsYearcarddetals;
	}
	
	public List<Yearselldetal> changeYearsell(Yearsellinfo curMaster,List<Yearcarddetal> lsYearcarddetals)
	{
		
		List<Yearselldetal> lsYearselldetals=new ArrayList<Yearselldetal>();
		try {
			int index=0;
			for(Yearcarddetal yearcarddetal:lsYearcarddetals)
			{
				Yearselldetal yearselldetal=new Yearselldetal();
				YearselldetalId yeId=new YearselldetalId();
				yeId.setCompid(curMaster.getId().getCompid());
				yeId.setBillid(curMaster.getId().getBillid());
				yeId.setSeq(index);
				yearselldetal.setId(yeId);
				yearselldetal.setAmt(0-CommonTool.FormatDouble(yearcarddetal.getSyamt()));
				yearselldetal.setNum(0-CommonTool.FormatInteger(yearcarddetal.getSynum()));
				yearselldetal.setFirstempno(yearcarddetal.getFirstempno());
				yearselldetal.setFirstperf(0-CommonTool.FormatDouble(yearcarddetal.getFirstperf()));
				yearselldetal.setSendempno(yearcarddetal.getSendempno());
				yearselldetal.setSendperf(0-CommonTool.FormatDouble(yearcarddetal.getSendperf()));
				yearselldetal.setIteminid(yearcarddetal.getIteminid());
				yearselldetal.setItemno(yearcarddetal.getItemno());
				yearselldetal.setPackno(yearcarddetal.getPackno());
				yearselldetal.setThreeempno(yearcarddetal.getThreeempno());
				yearselldetal.setThreeperf(yearcarddetal.getThreeperf());
				yearselldetal.setFourempno(yearcarddetal.getFourempno());
				yearselldetal.setFourperf(yearcarddetal.getFourperf());
				lsYearselldetals.add(yearselldetal);
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsYearselldetals;
	}
	
	public boolean post(Yearsellinfo curMaster,List<Yearselldetal> lsYearselldetals,List<CardYears> lsCardYears)
	{
		try {
			curMaster.setCashamt(0-CommonTool.FormatDouble(curMaster.getCashamt()));
			curMaster.setStoredamt(0-CommonTool.FormatDouble(curMaster.getStoredamt()));
			amn_Dao.save(curMaster);
			amn_Dao.saveOrUpdateAll(lsYearselldetals);
			StringBuffer strSql= new StringBuffer();
			double syamt=0;
			syamt=Math.abs(CommonTool.FormatDouble(curMaster.getStoredamt()));
			if(lsYearselldetals!=null && lsYearselldetals.size()>0)
			{
				for(Yearselldetal yearselldetal:lsYearselldetals)
				{
					strSql.append(" update yearcarddetal set syamt=0,synum=0 where iteminid='"+yearselldetal.getIteminid()+"' ");
				}
			}
			if(lsCardYears!=null && lsCardYears.size()>0)
			{
				for(CardYears cardYears:lsCardYears)
				{
					syamt=syamt-CommonTool.FormatDouble(cardYears.getAmt());
					
					strSql.append(" declare @costaccountseqno float ");
					strSql.append(" select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno='"+cardYears.getPhone()+"' ");
					strSql.append(" insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt) ");
					strSql.append(" select '"+curMaster.getId().getCompid()+"','"+curMaster.getCardno()+"',2,@costaccountseqno,0,"+Math.abs(cardYears.getAmt())+",'NK','"+curMaster.getId().getBillid()+"',"+curMaster.getAccountdate()+",accountbalance ");
					strSql.append(" from cardaccount where cardno='"+cardYears.getPhone()+"' and accounttype=2 ");
					strSql.append(" update cardaccount set accountbalance=isnull(accountbalance,0)+"+Math.abs(cardYears.getAmt())+" where cardno='"+cardYears.getPhone()+"' and accounttype=2 ");
				}
			}
			if(syamt!=0)
			{
				strSql.append(" declare @seqno float ");
				strSql.append(" select @seqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno='"+curMaster.getCardno()+"' ");
				strSql.append(" insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt) ");
				strSql.append(" select '"+curMaster.getId().getCompid()+"','"+curMaster.getCardno()+"',2,@seqno,0,"+syamt+",'NK','"+curMaster.getId().getBillid()+"',"+curMaster.getAccountdate()+",accountbalance ");
				strSql.append(" from cardaccount where cardno='"+curMaster.getCardno()+"' and accounttype=2 ");
				strSql.append(" update cardaccount set accountbalance=isnull(accountbalance,0)+"+syamt+" where cardno='"+curMaster.getCardno()+"' and accounttype=2 ");
			}
			strSql.append(" update  yearselldetal set firstinid=manageno from yearselldetal,staffinfo  where firstempno=staffno and compid=compno and compid='"+curMaster.getId().getCompid()+"' and billid='"+curMaster.getId().getBillid()+"' ");
			strSql.append(" update  yearselldetal set sendinid=manageno from yearselldetal,staffinfo  where sendempno=staffno and compid=compno and compid='"+curMaster.getId().getCompid()+"' and billid='"+curMaster.getId().getBillid()+"' ");
			strSql.append(" update  yearselldetal set threeinid=manageno from yearselldetal,staffinfo  where threeempno=staffno and compid=compno and compid='"+curMaster.getId().getCompid()+"' and billid='"+curMaster.getId().getBillid()+"' ");
			strSql.append(" update  yearselldetal set fourinid=manageno from yearselldetal,staffinfo  where fourempno=staffno and compid=compno and compid='"+curMaster.getId().getCompid()+"' and billid='"+curMaster.getId().getBillid()+"' ");
			
			amn_Dao.executeSql(strSql.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
