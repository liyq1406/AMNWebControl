package com.amani.service.CardControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.validator.Var;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Dmpackageinfo;
import com.amani.model.Mpackageinfo;
import com.amani.model.Projectinfo;
import com.amani.model.Yearcarddetal;
import com.amani.model.Yearcardinof;
import com.amani.model.Yearselldetal;
import com.amani.model.YearselldetalId;
import com.amani.model.Yearsellinfo;
import com.amani.model.YearsellinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;

@Service
public class CC018Service extends AMN_ModuleService{

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
	
	public List<Mpackageinfo> loadMpackageinfos()
	{
		String strSql="From Mpackageinfo where usetype='4' and compid='"+CommonTool.getLoginInfo("COMPID")+"' and usedate>='"+CommonTool.getCurrDate()+"'";
		List<Mpackageinfo> lsList=null;
		try {
			lsList=amn_Dao.findByHql(strSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsList;
	}
	
	public Yearsellinfo add()
	{
		Yearsellinfo yearsellinfo=new Yearsellinfo();
		yearsellinfo.setId(new YearsellinfoId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"yearsellinfo", "billid", "SP007")));
		yearsellinfo.setAccountdate(CommonTool.getCurrDate());
		return yearsellinfo;
	}
	
	public List<Dmpackageinfo> loadDmpack(String strPackNo)
	{
		String strSql="From Dmpackageinfo where packageno='"+strPackNo+"' and compid='"+CommonTool.getLoginInfo("COMPID")+"'";
		List<Dmpackageinfo> lsDmpackageinfos=null;
		try {
			lsDmpackageinfos=amn_Dao.findByHql(strSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsDmpackageinfos;
	}
	
	public boolean post(Yearsellinfo yearsellinfo,List<Yearselldetal> lsyList,double amt)
	{
		int loadMonth=0;
		String strDyqSql="";
		try {
			yearsellinfo.getId().setCompid(CommonTool.getLoginInfo("COMPID"));
			yearsellinfo.setAccountdate(CommonTool.getCurrDate());
			yearsellinfo.setUserid(CommonTool.getLoginInfo("USERID"));
			if(checkPhone(yearsellinfo.getPhone()))
			{
				Yearcardinof yearcardinof=new Yearcardinof();
				yearcardinof.setPhone(yearsellinfo.getPhone());
				yearcardinof.setCid(yearsellinfo.getCid());
				yearcardinof.setCompid(CommonTool.getLoginInfo("COMPID"));
				yearcardinof.setImg(yearsellinfo.getStrImage());
				yearcardinof.setName(yearsellinfo.getName());
				yearcardinof.setPhoneinid(UUID.randomUUID().toString());
				amn_Dao.save(yearcardinof);
			}
			List<Yearcarddetal> lsYearsell=new ArrayList<Yearcarddetal>();
			for(int i=0;i<lsyList.size();i++)
			{
				lsyList.get(i).setId(new YearselldetalId(yearsellinfo.getId().getCompid(),yearsellinfo.getId().getBillid(),i));
				//lsyList.get(i).getId().setCompid(CommonTool.getLoginInfo("COMPID"));
				Yearselldetal yearselldetal=lsyList.get(i);
				Yearcarddetal yearcarddetal=new Yearcarddetal();
				yearcarddetal.setIteminid(UUID.randomUUID().toString());
				yearselldetal.setIteminid(yearcarddetal.getIteminid());
				yearcarddetal.setItemno(yearselldetal.getItemno());
				yearcarddetal.setCompid(yearselldetal.getId().getCompid());
				yearcarddetal.setAmt(yearselldetal.getAmt());
				yearcarddetal.setNum(yearselldetal.getNum());
				yearcarddetal.setPackno(yearselldetal.getPackno());
				yearcarddetal.setPhone(yearsellinfo.getPhone());
				yearcarddetal.setSyamt(yearselldetal.getAmt());
				yearcarddetal.setSynum(yearselldetal.getNum());
				yearcarddetal.setRemarks(yearselldetal.getProbz());
				loadMonth=loadMonth(yearselldetal.getPackno());
				yearcarddetal.setValidate(CommonTool.addMonth(loadMonth));
				yearcarddetal.setMkdate(CommonTool.getCurrDate());
				if(CommonTool.checkStr(lsyList.get(i).getCashdyq()))
				{
					strDyqSql+=" update nointernalcardinfo set cardstate=2 where cardno='"+lsyList.get(i).getCashdyq()+"' ";
				}
				lsYearsell.add(yearcarddetal);
				//解决记录内部管理号丢失的问题
				if(StringUtils.isNotBlank(yearselldetal.getFirstempno()) && StringUtils.isBlank(yearselldetal.getFirstinid())){
					yearselldetal.setFirstinid(this.getDataTool().loadEmpInidById(yearselldetal.getId().getCompid(), yearselldetal.getFirstempno()));
				}
				if(StringUtils.isNotBlank(yearselldetal.getSendempno()) && StringUtils.isBlank(yearselldetal.getSendinid())){
					yearselldetal.setSendinid(this.getDataTool().loadEmpInidById(yearselldetal.getId().getCompid(), yearselldetal.getSendempno()));
				}
				if(StringUtils.isNotBlank(yearselldetal.getThreeempno()) && StringUtils.isBlank(yearselldetal.getThreeinid())){
					yearselldetal.setThreeinid(this.getDataTool().loadEmpInidById(yearselldetal.getId().getCompid(), yearselldetal.getThreeempno()));
				}
				if(StringUtils.isNotBlank(yearselldetal.getFourempno()) && StringUtils.isBlank(yearselldetal.getFourinid())){
					yearselldetal.setFourinid(this.getDataTool().loadEmpInidById(yearselldetal.getId().getCompid(), yearselldetal.getFourempno()));
				}
			}
			amn_Dao.save(yearsellinfo);
			amn_Dao.saveOrUpdateAll(lsyList);
			amn_Dao.saveOrUpdateAll(lsYearsell);
			if(CommonTool.checkStr(strDyqSql))
			{
				amn_Dao.executeStatement(strDyqSql);
			}
			if(CommonTool.checkStr(yearsellinfo.getIdtphone()))
			{
				String strSql="update yearcarddetal set validate=convert(varchar(10),dateadd(mm,1,validate),112) where phone='"+yearsellinfo.getIdtphone()+"'";
				amn_Dao.executeStatement(strSql);
			}
			if(CommonTool.FormatDouble(yearsellinfo.getStoredamt())>0)
			{
				StringBuffer buffer=new StringBuffer();
				double seq=loadSeq(yearsellinfo.getCardno());
				double curZSAmt=loadStoreAmt(yearsellinfo.getCardno(), 6);
				buffer.append(" insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt) ");
				buffer.append(" values('"+yearsellinfo.getId().getCompid()+"','"+yearsellinfo.getCardno()+"',2,"+seq+",2,"+yearsellinfo.getStoredamt()+",'NK','"+yearsellinfo.getId().getBillid()+"','"+yearsellinfo.getAccountdate()+"',"+amt+") ");
				buffer.append(" insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt) ");
				buffer.append(" values('"+yearsellinfo.getId().getCompid()+"','"+yearsellinfo.getCardno()+"',6,"+seq+",2,"+yearsellinfo.getZsamt()+",'NK','"+yearsellinfo.getId().getBillid()+"','"+yearsellinfo.getAccountdate()+"',"+curZSAmt+") ");
				buffer.append(" update cardaccount set accountbalance=accountbalance-"+CommonTool.FormatDouble(yearsellinfo.getStoredamt())+" where cardno='"+yearsellinfo.getCardno()+"' and accounttype=2 ");
				buffer.append(" update cardaccount set accountbalance=accountbalance-"+CommonTool.FormatDouble(yearsellinfo.getZsamt())+" where cardno='"+yearsellinfo.getCardno()+"' and accounttype=6 ");
				amn_Dao.executeStatement(buffer.toString());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkPhone(String strPhone)
	{
		String strSql="select 1 from yearcardinof where phone='"+strPhone+"'";
		try {
			if(amn_Dao.getRowsCount_Ex(strSql)>0)
			{
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public int loadMonth(String strPackNo)
	{
		String strSql="select usemonths from mpackageinfo where packageno='"+strPackNo+"' and compid='"+CommonTool.getLoginInfo("COMPID")+"' ";
		int usermonth=0;
		try {
			usermonth=amn_Dao.getRowsCount_Ex(strSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usermonth;
	}
	
	
	public Yearcardinof loadYearCard(String strPhone)
	{
		String strSql="From Yearcardinof where phone='"+strPhone+"'";
		Yearcardinof yearcardinof=null;
		try {
			List<Yearcardinof> lsYearcardinofs=amn_Dao.findByHql(strSql);
			if(lsYearcardinofs!=null && lsYearcardinofs.size()>0)
			{
				yearcardinof=lsYearcardinofs.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yearcardinof;
	}
	
	public Yearsellinfo loadYearsellinfo(String strBillId)
	{
		String strSql="select a From Yearsellinfo a,Compchaininfo where relationcomp=compid and curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and billid='"+strBillId+"' ";
		Yearsellinfo yearsellinfo=null;
		try {
			List<Yearsellinfo> lsYearsellinfos=amn_Dao.findByHql(strSql);
			if(lsYearsellinfos!=null && lsYearsellinfos.size()>0)
			{
				yearsellinfo=lsYearsellinfos.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yearsellinfo;
	}
	
	public List<Yearselldetal> loadYearselldetals(String strBillId)
	{
		
		String strSql="select a From Yearselldetal a,Compchaininfo where relationcomp=compid and curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and billid='"+strBillId+"'";
		List<Yearselldetal> lsYearselldetals=null;
		try {
			lsYearselldetals=amn_Dao.findByHql(strSql);
			if(lsYearselldetals!=null && lsYearselldetals.size()>0)
			{
				for(Yearselldetal yearselldetal:lsYearselldetals)
				{
					Projectinfo project=this.getDataTool().loadProjectInfo(yearselldetal.getId().getCompid(),yearselldetal.getItemno());
					String strEmpName=this.getDataTool().loadEmpNameById(yearselldetal.getId().getCompid(), yearselldetal.getFirstempno(), new StringBuffer());
					yearselldetal.setFirstempname(strEmpName);
					strEmpName=this.getDataTool().loadEmpNameById(yearselldetal.getId().getCompid(), yearselldetal.getSendempno(), new StringBuffer());
					yearselldetal.setSendempname(strEmpName);
					strEmpName=this.getDataTool().loadEmpNameById(yearselldetal.getId().getCompid(), yearselldetal.getThreeempno(), new StringBuffer());
					yearselldetal.setThreeempname(strEmpName);
					strEmpName=this.getDataTool().loadEmpNameById(yearselldetal.getId().getCompid(), yearselldetal.getFourempno(), new StringBuffer());
					yearselldetal.setFourempname(strEmpName);
					yearselldetal.setItemName(project.getPrjname());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsYearselldetals;
	}
	
	public double loadStoreAmt(String strCardNo,int acttype)
	{
		String strSql="select accountbalance from cardaccount where cardno='"+strCardNo+"' and accounttype="+acttype+"";
		double storeAmt=0;
		ResultSet rs=null;
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					storeAmt=rs.getDouble("accountbalance");
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
		return storeAmt;
	}
	
	public double loadSeq(String strCardNo)
	{
		String strSql="select MAX(changeseqno)+1 from cardaccountchangehistory where changecardno='"+strCardNo+"' ";
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
	
	public Mpackageinfo checkPack(String strPackNo)
	{
		String strSql="From Mpackageinfo where compid='"+CommonTool.getLoginInfo("COMPID")+"' and usedate>='"+CommonTool.getCurrDate()+"' and packageno='"+strPackNo+"' ";
		Mpackageinfo mpackageinfo=null;
		try {
			List<Mpackageinfo> lsMpackageinfos=amn_Dao.findByHql(strSql);
			if(lsMpackageinfos!=null && lsMpackageinfos.size()>0)
			{
				mpackageinfo=lsMpackageinfos.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mpackageinfo;
	}
	
	public boolean saveYearCardInfo(Yearcardinof yearcardinof)
	{
		try {
			if(checkPhone(yearcardinof.getPhone()))
			{
				//yearcardinof.setPhone(yearsellinfo.getPhone());
				//yearcardinof.setCid(yearsellinfo.getCid());
				yearcardinof.setCompid(CommonTool.getLoginInfo("COMPID"));
				//yearcardinof.setImg(yearsellinfo.getStrImage());
				//yearcardinof.setName(yearsellinfo.getName());
				yearcardinof.setPhoneinid(UUID.randomUUID().toString());
				amn_Dao.save(yearcardinof);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public double validateDiyqNo(String strDiyqNo)
	 {
		 String strSql=" select cardfaceamt from nointernalcardinfo where cardno='"+strDiyqNo+"' and cardtype=1 and cardstate=1 and carduseflag=2 ";
		 AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
			{
				public Double anlyResultSet(ResultSet rs)
				{
					double returnValue = 0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=rs.getDouble("cardfaceamt");
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  0;
					}
					return returnValue;
				}
			};
			return (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);
	 }
	
	//查询年卡用户是否购买套餐'201047','201048','201049'
	public boolean validateYearPackNo(String phone){
		String strSql=" select count(*) from yearcarddetal with(nolock) where packno in ('201047','201048','201049') and synum<>0 "+ 
					  " and (validate>=CONVERT(varchar(100), GETDATE(), 112) or itemstate=1) and phone='"+ phone +"'";
		return amn_Dao.getRowsCount_Ex(strSql)>0;
	}
	
	//判断年卡用户是否有购买美容项目的套餐
	public boolean validateIsPackPro(String compid, String phone, String packageno){
		String strSql="select count(*) from dmpackageinfo a, projectnameinfo b where a.packageprono=prjno " +
				"and packageno='"+ packageno +"' and compid='"+ compid +"' and prjtype='4' and packageno not in('201047','201048','201049')";
		if(amn_Dao.getRowsCount_Ex(strSql)>0){//套餐包含美容项目的,则查询用户是否有购买过此套餐
			strSql="select count(*) from yearcarddetal with(nolock) where phone='"+ phone +"' and packno='"+ packageno +"'";
			return amn_Dao.getRowsCount_Ex(strSql)>0;
		}
		return true;
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
