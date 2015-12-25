package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;


import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xvolks.jnative.misc.basicStructures.ITEMID;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CostAnalysisBean;
import com.amani.bean.ReEditBillInfo;

import com.amani.model.Dconsumeinfo;
import com.amani.model.Dpayinfo;
import com.amani.model.DpayinfoId;
import com.amani.model.Dproexchangeinfo;
import com.amani.model.DproexchangeinfoId;
import com.amani.model.Mconsumeinfo;
import com.amani.model.MsalebarcodecardinfoId;
import com.amani.model.Yearselldetal;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
import com.amani.tools.DataTool;
@Service
public class AC008Service  extends AMN_ModuleService{

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
	
	public List<ReEditBillInfo> loadBillTypeDateInfo(String strCompId,String strDate)
	{
		String strSql="select ttype=0,ttypename='开卡',billcount=COUNT(salebillid) from msalecardinfo with(nolock) where salecompid='"+strCompId+"' and financedate='"+strDate+"'" +
						"	union " +
						"	select  ttype=1,ttypename='充值/还款',billcount=COUNT(rechargebillid) from mcardrechargeinfo with(nolock) where rechargecompid='"+strCompId+"' and financedate='"+strDate+"' " +
						"	union " +
						"	select  ttype=2,ttypename='折扣转卡',billcount=COUNT(changebillid) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strDate+"' and changetype =0" +
						"	union " +
						"	select  ttype=3,ttypename='收购转卡',billcount=COUNT(changebillid) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strDate+"' and changetype =1" +
						"	union " +
						"	select  ttype=4,ttypename='竞争转卡',billcount=COUNT(changebillid) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strDate+"' and changetype =2" +
						"	union " +
						"	select  ttype=5,ttypename='并卡',billcount=COUNT(changebillid) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strDate+"' and changetype in (6,7) " +
						"	union " +
						"	select  ttype=6,ttypename='退卡',billcount=COUNT(changebillid) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strDate+"' and changetype=8 " +
						"	union " +
						"	select  ttype=7,ttypename='合作项目',billcount=COUNT(salebillid) from mcooperatesaleinfo with(nolock) where salecompid='"+strCompId+"' and financedate='"+strDate+"' " +
						"	union " +
						"	select  ttype=8,ttypename='条码卡',billcount=COUNT(salebillid) from msalebarcodecardinfo with(nolock) where salecompid='"+strCompId+"' and saledate='"+strDate+"' "+
						"	union " +
						"	select  ttype=9,ttypename='疗程兑换',billcount=COUNT(changebillid) from mproexchangeinfo with(nolock) where changecompid='"+strCompId+"' and changedate='"+strDate+"' "+
						"	union " +
						"	select  ttype=10,ttypename='收银(项目)',billcount=COUNT(distinct a.csbillid) from mconsumeinfo a with(nolock) ,dconsumeinfo b with(nolock)  where a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,0)=1 and  a.cscompid='"+strCompId+"' and financedate='"+strDate+"' "+
						"	union " +
						"	select  ttype=11,ttypename='收银(产品)',billcount=COUNT(distinct a.csbillid) from mconsumeinfo a with(nolock) ,dconsumeinfo b with(nolock)   where a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,0)=2 and  a.cscompid='"+strCompId+"' and financedate='"+strDate+"' "+
							
						"	union select  ttype=12,ttypename='年卡销售',billcount=COUNT(distinct a.billid) from yearsellinfo a with(nolock) ,yearselldetal b with(nolock)   where a.compid=b.compid and a.billid=b.billid and  a.compid='"+strCompId+"' and accountdate='"+strDate+"' ";
						

		AnlyResultSet<List<ReEditBillInfo> > analysis = new AnlyResultSet<List<ReEditBillInfo> >()
	    	{
	    			public List<ReEditBillInfo>  anlyResultSet(ResultSet rs)
	    			{
	    				List<ReEditBillInfo>  returnValue = new ArrayList();
	    				ReEditBillInfo record=null;
	    				try
	    				{
		    				while(rs != null && rs.next()==true)
		    				{
		    					record=new ReEditBillInfo();
		    					record.setBillType(CommonTool.FormatInteger(rs.getInt("ttype")));
		    					record.setBillTypeName(CommonTool.FormatString(rs.getString("ttypename")));
		    					record.setBillCount(CommonTool.FormatInteger(rs.getInt("billcount")));
		    					returnValue.add(record);
		    				}
		    				
	    				}
	    				catch(Exception e)
	    				{
	    					e.printStackTrace();
	    					returnValue =  null;
	    				}
	    				record=null;
	    				return returnValue;
	    			}
	    	};
	    	List<ReEditBillInfo> ls =(List<ReEditBillInfo> )this.amn_Dao.executeQuery_ex(strSql,analysis);
	    	analysis=null;
	    	return ls;
		
	}
	
	public List<ReEditBillInfo> loadBillDateInfoByType(String strCompId,int ntype,String strSearchDate)
	{
		String strSql="";
		if(ntype==0)  //开卡
		{
			strSql="select ttype=0,strCompId=salecompid,strBillId=salebillid,billdate=financedate,changeseqno=0,strCardNo=salecardno,strCardType=salecardtype,strMemberName=membername," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,backflag=isnull(backflag,0),beautyManager,beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid,isxnj " +
					"  from msalecardinfo with(nolock) where salecompid='"+strCompId+"' and financedate='"+strSearchDate+"' order by strBillId ";
		}
		else if(ntype==1)  //充值
		{
			strSql="select ttype=1,strCompId=rechargecompid,strBillId=rechargebillid,billdate=financedate,changeseqno=0,strCardNo=rechargecardno,strCardType=rechargecardtype,strMemberName=membername," +
				"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
				"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
				"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
				"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,backflag=isnull(backflag,0),beautyManager,beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid,isxnj " +
				"  from mcardrechargeinfo with(nolock) where rechargecompid='"+strCompId+"' and financedate='"+strSearchDate+"' order by strBillId  ";
		}
		else if(ntype==2  )  //转卡
		{
			strSql="select ttype=2,strCompId=changecompid,strBillId=changebillid,billdate=financedate,changeseqno=0,strCardNo=changeaftercardno,strCardType=changeaftercardtype,strMemberName=membername," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,backflag=isnull(backflag,0),beautyManager,beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid,isxnj " +
					"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strSearchDate+"' and changetype=0  order by strBillId  ";
		}
		else if(ntype==3 )  //转卡
		{
			strSql="select ttype=3,strCompId=changecompid,strBillId=changebillid,billdate=financedate,changeseqno=0,strCardNo=changeaftercardno,strCardType=changeaftercardtype,strMemberName=membername," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,backflag=isnull(backflag,0),beautyManager,beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid,isxnj " +
					"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strSearchDate+"' and changetype=1 order by strBillId  ";
		}
		else if(ntype==4   )  //转卡
		{
			strSql="select ttype=4,strCompId=changecompid,strBillId=changebillid,billdate=financedate,changeseqno=0,strCardNo=changeaftercardno,strCardType=changeaftercardtype,strMemberName=membername," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,backflag=isnull(backflag,0),beautyManager,beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid,isxnj " +
					"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strSearchDate+"' and changetype=2  order by strBillId  ";
		}
		else if(ntype==5  )  //并卡
		{
			strSql="select ttype=5,strCompId=changecompid,strBillId=changebillid,billdate=financedate,changeseqno=0,strCardNo=changebeforcardno,strCardType=changebeforcardtype,strMemberName=membername," +
				"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
				"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
				"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
				"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,backflag=isnull(backflag,0),beautyManager,beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid,isxnj " +
				"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strSearchDate+"' and changetype in (6,7) order by strBillId  ";
		}
		else if(ntype==6  )  //退卡
		{
			strSql="select ttype=6,strCompId=changecompid,strBillId=changebillid,billdate=financedate,changeseqno=0,strCardNo=changebeforcardno,strCardType=changebeforcardtype,strMemberName=membername," +
				"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
				"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
				"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
				"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,backflag=isnull(backflag,0)  " +
				"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strSearchDate+"' and changetype = 8  order by strBillId  ";
		}
		else if(ntype==7  )  //合作项目
		{
			strSql=	"select ttype=7,strCompId=salecompid,strBillId=salebillid,billdate=financedate,changeseqno=0,strCardNo=salecostcardno,strCardType=salecostcardtype,strMemberName=membername," +
					" firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid='',seventhsalerinid='',seventhsaleamt=0,eighthsalerid='',eighthsalerinid='',eighthsaleamt=0," +
					"  ninthsalerid='',ninthsalerinid='',ninthsaleamt=0,tenthsalerid='',tenthsalerinid='',tenthsaleamt=0 ,backflag=0 " +
					"  from mcooperatesaleinfo with(nolock) where salecompid='"+strCompId+"' and saledate='"+strSearchDate+"'  order by strBillId  ";
		}
		else if(ntype==8  )  //条码卡
		{
			strSql=	"select ttype=8,strCompId=salecompid,strBillId=salebillid,billdate=saledate,changeseqno=0,strCardNo=barcodecardno,strCardType='',strMemberName=''," +
					"  firstsalerid=firstsaleempid,firstsalerinid=firstsaleempinid,firstsaleamt=firstsaleamt,secondsalerid=secondsaleempid,secondsalerinid=secondsaleempinid,secondsaleamt=secondsaleamt," +
					"  thirdsalerid=thirdsaleempid,thirdsalerinid=thirdsaleempinid,thirdsaleamt=thirdsaleamt,fourthsalerid='',fourthsalerinid='',fourthsaleamt=0,fifthsalerid='',fifthsalerinid='',fifthsaleamt=0," +
					"  sixthsalerid='',sixthsalerinid='',sixthsaleamt=0,seventhsalerid='',seventhsalerinid='',seventhsaleamt=0,eighthsalerid='',eighthsalerinid='',eighthsaleamt=0," +
					"  ninthsalerid='',ninthsalerinid='',ninthsaleamt=0,tenthsalerid='',tenthsalerinid='',tenthsaleamt=0 ,backflag=isnull(salebakflag,0) " +
					"  from  msalebarcodecardinfo with(nolock) where salecompid='"+strCompId+"' and saledate='"+strSearchDate+"'  order by strBillId  ";
		}
		else if(ntype==9  )  //疗程兑换
		{
			strSql=	"select ttype=9,strCompId=a.changecompid,strBillId=a.changebillid,billdate=changedate,changeseqno=changeseqno,strCardNo=changecardno,strCardType=changecardtype,strMemberName=membername," +
					"  firstsalerid=firstsalerid,firstsalerinid=firstsalerinid,firstsaleamt=firstsaleamt,secondsalerid='',secondsalerinid='',secondsaleamt=0," +
					"  thirdsalerid=thirdsalerid,thirdsalerinid=thirdsalerinid,thirdsaleamt=thirdsaleamt,fourthsalerid=fourthsalerid,fourthsalerinid=fourthsalerinid,fourthsaleamt=fourthsaleamt,fifthsalerid=secondsalerid,fifthsalerinid=secondsalerinid,fifthsaleamt=secondsaleamt," +
					"  sixthsalerid='',sixthsalerinid='',sixthsaleamt=0,seventhsalerid='',seventhsalerinid='',seventhsaleamt=0,eighthsalerid='',eighthsalerinid='',eighthsaleamt=0," +
					"  ninthsalerid='',ninthsalerinid='',ninthsaleamt=0,tenthsalerid='',tenthsalerinid='',tenthsaleamt=0,backflag=isnull(b.salebakflag,0) " +
					"  from mproexchangeinfo a with(nolock), dproexchangeinfo b with(nolock) where a.changecompid='"+strCompId+"' and changedate='"+strSearchDate+"'" +
					"   and a.changecompid=b.changecompid and a.changebillid=b.changebillid   order by strBillId  ";
		}
		else if(ntype==10)  //收银(项目)
		{
			strSql=" select ttype=10,strCompId=a.cscompid,strBillId=a.csbillid,billdate=financedate,changeseqno=0,strCardNo=cscardno,strCardType=cscardtype,strMemberName=csname,backflag=isnull(backcsflag,0)," +
					"  scanpaytype=max(isnull(a.scanpaytype,0)),scantradeno=max(isnull(a.scantradeno,0)),scanpayamt=max(isnull(b.scanpayamt,0)),cspaymode=max(case when b.cspaymode='15' then 15 else 0 end) "+
					"  from mconsumeinfo a with(nolock) ,dconsumeinfo b with(nolock) where a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,0)=1 and a.cscompid='"+strCompId+"' and financedate='"+strSearchDate+"'" +
					"  group by  a.cscompid, a.csbillid,financedate,cscardno,cscardtype,csname,backcsflag order by strBillId ";
		}
		else if(ntype==11)  //收银(产品)
		{
			strSql=" select ttype=11,strCompId=a.cscompid,strBillId=a.csbillid,billdate=financedate,changeseqno=0,strCardNo=cscardno,strCardType=cscardtype,strMemberName=csname,backflag=isnull(backcsflag,0), "+
					"  scanpaytype=max(isnull(a.scanpaytype,0)),scantradeno=max(isnull(a.scantradeno,0)),scanpayamt=max(isnull(b.scanpayamt,0)),cspaymode=max(case when b.cspaymode='15' then 15 else 0 end) "+
					"  from mconsumeinfo a with(nolock) ,dconsumeinfo b  with(nolock)where a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,0)=2 and a.cscompid='"+strCompId+"' and financedate='"+strSearchDate+"' " +
					"  group by a.cscompid, a.csbillid,financedate,cscardno,cscardtype,csname,backcsflag  order by strBillId  ";
		}
		else if(ntype==12)
		{
			strSql=" select ttype=12,strCompId=a.compid,strBillId=a.billid,billdate=accountdate,changeseqno=0,iteminid,strCardNo=cardno,strCardType='',strMemberName=name,backflag=isnull(backcsflag,0) " +
					"from yearsellinfo a with(nolock),yearselldetal b with(nolock) where a.compid=b.compid and a.billid=b.billid and a.compid='"+strCompId+"' and accountdate='"+strSearchDate+"' order by a.billid ";
		}
		AnlyResultSet<List<ReEditBillInfo> > analysis = new AnlyResultSet<List<ReEditBillInfo> >()
    	{
    			public List<ReEditBillInfo>  anlyResultSet(ResultSet rs)
    			{
    				List<ReEditBillInfo>  returnValue = new ArrayList();
    				ReEditBillInfo record=null;
    				try
    				{
	    				while(rs != null && rs.next()==true)
	    				{
	    					record=new ReEditBillInfo();
	    					record.setBillType(CommonTool.FormatInteger(rs.getInt("ttype")));
	    					record.setStrCompId(CommonTool.FormatString(rs.getString("strCompId")));
	    					record.setStrBillId(CommonTool.FormatString(rs.getString("strBillId")));
	    					record.setChangeseqno(CommonTool.FormatDouble(rs.getDouble("changeseqno")));
	    					record.setStrCardNo(CommonTool.FormatString(rs.getString("strCardNo")));
	    					record.setStrCardType(CommonTool.FormatString(rs.getString("strCardType")));
	    					record.setStrMemberName(CommonTool.FormatString(rs.getString("strMemberName")));
	    					if(!CommonTool.FormatString(rs.getString("strMemberName")).equals(""))
	    					{
	    						record.setStrMemberName(CommonTool.FormatString(rs.getString("strMemberName")).substring(0,1)+"**");
	    					}
	    					record.setBackflag(CommonTool.FormatInteger(rs.getInt("backflag")));
	    					record.setBilldate(CommonTool.FormatString(rs.getString("billdate")));
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))==12)
	    					{
	    						record.setItemInid(rs.getString("iteminid"));
	    					}
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))==10 || CommonTool.FormatInteger(rs.getInt("ttype"))==11)
	    					{//微信和支付宝退款
	    						record.setBillinsertype(CommonTool.FormatInteger(rs.getInt("scanpaytype")));
	    						record.setFirstsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("scanpayamt")))));
	    						record.setStrPrjNo(CommonTool.FormatString(rs.getString("scantradeno")));
	    						record.setStrPrjName(CommonTool.FormatString(rs.getString("cspaymode")));
	    					}
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))==0 || CommonTool.FormatInteger(rs.getInt("ttype"))==1 || CommonTool.FormatInteger(rs.getInt("ttype"))==2 
	    							|| CommonTool.FormatInteger(rs.getInt("ttype"))==3 || CommonTool.FormatInteger(rs.getInt("ttype"))==4 || CommonTool.FormatInteger(rs.getInt("ttype"))==5)
	    					{
	    						record.setIsxnj(rs.getInt("isxnj"));
	    					}
	    					
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))!=10 && CommonTool.FormatInteger(rs.getInt("ttype"))!=11 && CommonTool.FormatInteger(rs.getInt("ttype"))!=12) 
	    					{
	    						record.setFirstsalerid(CommonTool.FormatString(rs.getString("firstsalerid")));
	    						record.setSecondsalerid(CommonTool.FormatString(rs.getString("secondsalerid")));
	    						record.setThirdsalerid(CommonTool.FormatString(rs.getString("thirdsalerid")));
	    						record.setFourthsalerid(CommonTool.FormatString(rs.getString("fourthsalerid")));
	    						record.setFifthsalerid(CommonTool.FormatString(rs.getString("fifthsalerid")));
		    					record.setSixthsalerid(CommonTool.FormatString(rs.getString("sixthsalerid")));
		    					record.setSeventhsalerid(CommonTool.FormatString(rs.getString("seventhsalerid")));
		    					record.setEighthsalerid(CommonTool.FormatString(rs.getString("eighthsalerid")));
		    					record.setNinthsalerid(CommonTool.FormatString(rs.getString("ninthsalerid")));
		    					record.setTenthsalerid(CommonTool.FormatString(rs.getString("tenthsalerid")));
		    					
		    					record.setFirstsalerinid(CommonTool.FormatString(rs.getString("firstsalerinid")));
		    					record.setSecondsalerinid(CommonTool.FormatString(rs.getString("secondsalerinid")));
		    					record.setThirdsalerinid(CommonTool.FormatString(rs.getString("thirdsalerinid")));
		    					record.setFourthsalerinid(CommonTool.FormatString(rs.getString("fourthsalerinid")));
		    					record.setFifthsalerinid(CommonTool.FormatString(rs.getString("fifthsalerinid")));
		    					record.setSixthsalerinid(CommonTool.FormatString(rs.getString("sixthsalerinid")));
		    					record.setSeventhsalerinid(CommonTool.FormatString(rs.getString("seventhsalerinid")));
		    					record.setEighthsalerinid(CommonTool.FormatString(rs.getString("eighthsalerinid")));
		    					record.setNinthsalerinid(CommonTool.FormatString(rs.getString("ninthsalerinid")));
		    					record.setTenthsalerinid(CommonTool.FormatString(rs.getString("tenthsalerinid")));
		    					
		    					record.setFirstsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("firstsaleamt")))));
		    					record.setSecondsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("secondsaleamt")))));
		    					record.setThirdsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("thirdsaleamt")))));
		    					record.setFourthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fourthsaleamt")))));
		    					record.setFifthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fifthsaleamt")))));
		    					record.setSixthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sixthsaleamt")))));
		    					record.setSeventhsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("seventhsaleamt")))));
		    					record.setEighthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("eighthsaleamt")))));
		    					record.setNinthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ninthsaleamt")))));
		    					record.setTenthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tenthsaleamt")))));
								
	    					}
	    					
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))!=10 && CommonTool.FormatInteger(rs.getInt("ttype"))!=11 && CommonTool.FormatInteger(rs.getInt("ttype"))!=12
	    						&& CommonTool.FormatInteger(rs.getInt("ttype"))!=6 && CommonTool.FormatInteger(rs.getInt("ttype"))!=7
	    						&& CommonTool.FormatInteger(rs.getInt("ttype"))!=8 && CommonTool.FormatInteger(rs.getInt("ttype"))!=9
	    							)
	    					{
	    						record.setBeautyManager(CommonTool.FormatString(rs.getString("beautyManager")));
	    						record.setBeautyManagerinid(CommonTool.FormatString(rs.getString("beautyManagerinid")));
	    						record.setConsultant(CommonTool.FormatString(rs.getString("consultant")));
	    						record.setConsultantinid(CommonTool.FormatString(rs.getString("consultantinid")));
	    						record.setConsultant1(CommonTool.FormatString(rs.getString("consultant1")));
	    						record.setTenthsalerid(CommonTool.FormatString(rs.getString("consultant1inid")));
	    					}
	    					returnValue.add(record);
	    				}
	    				
    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    					returnValue =  null;
    				}
    				record=null;
    				return returnValue;
    			}
    	};
    	List<ReEditBillInfo> ls =(List<ReEditBillInfo> )this.amn_Dao.executeQuery_ex(strSql,analysis);
    	if(ls!=null && ls.size()>0)
    	{
    		for(int i=0;i<ls.size();i++)
    		{
    			if(!CommonTool.FormatString(ls.get(i).getStrCardType()).equals(""))
    			{
    				ls.get(i).setStrCardTypeName(this.dataTool.loadCardTypeName(ls.get(i).getStrCompId(), CommonTool.FormatString(ls.get(i).getStrCardType()), new StringBuffer()));
    			}
    		}
    	}
    	analysis=null;
    
    	return ls;
	}
	
	
	public ReEditBillInfo loadCurBillDateInfo(String strCompId,int ntype,String strBillId,double changeseqno,String itemInid)
	{
		String strSql="";
		if(ntype==0)  //开卡
		{
			strSql="select ttype=0,strCompId=salecompid,strBillId=salebillid,billdate=financedate,changeseqno=0,strCardNo=salecardno,strCardType=salecardtype,strMemberName=membername," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
					"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
					"  backflag=isnull(backflag,0),billinsertype=isnull(billinsertype,0),beautyManager,beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid,isxnj," +
					"  beautymangerno,beautymangerinid,beautygw,beaytygwinid " +
					"  from msalecardinfo with(nolock) where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
		}
		else if(ntype==1)  //充值
		{
			strSql="select ttype=1,strCompId=rechargecompid,strBillId=rechargebillid,billdate=financedate,changeseqno=0,strCardNo=rechargecardno,strCardType=rechargecardtype,strMemberName=membername," +
				"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
				"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
				"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
				"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
				"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
				"  backflag=isnull(backflag,0),billinsertype=isnull(billinsertype,0),beautyManager,beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid,isxnj," +
				"  beautymangerno,beautymangerinid,beautygw,beaytygwinid " +
				"  from mcardrechargeinfo with(nolock) where rechargecompid='"+strCompId+"' and rechargebillid='"+strBillId+"' ";
		}
		else if(ntype==2  )  //转卡
		{
			strSql="select ttype=2,strCompId=changecompid,strBillId=changebillid,billdate=financedate,changeseqno=0,changebeforcardno,strCardNo=changeaftercardno,strCardType=changeaftercardtype,strMemberName=membername," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
					"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
					"  backflag=isnull(backflag,0),billinsertype=isnull(billinsertype,0),beautyManager,beautyManagerinid," +
					"  consultant,consultantinid,consultant1,consultant1inid,isxnj,beautymangerno,beautymangerinid,beautygw,beaytygwinid  " +
					"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype=0 ";
		}
		else if(ntype==3 )  //转卡
		{
			strSql="select ttype=3,strCompId=changecompid,strBillId=changebillid,billdate=financedate,changeseqno=0,changebeforcardno,strCardNo=changeaftercardno,strCardType=changeaftercardtype,strMemberName=membername," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
					"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
					"  backflag=isnull(backflag,0),billinsertype=isnull(billinsertype,0),beautyManager," +
					"  beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid,isxnj," +
					"  beautymangerno,beautymangerinid,beautygw,beaytygwinid " +
					"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype=1 ";
		}
		else if(ntype==4   )  //转卡
		{
			strSql="select ttype=4,strCompId=changecompid,strBillId=changebillid,billdate=financedate,changeseqno=0,changebeforcardno,strCardNo=changeaftercardno,strCardType=changeaftercardtype,strMemberName=membername," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
					"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
					"  backflag=isnull(backflag,0),billinsertype=isnull(billinsertype,0),beautyManager,beautyManagerinid," +
					"  consultant,consultantinid,consultant1,consultant1inid,isxnj," +
					"  beautymangerno,beautymangerinid,beautygw,beaytygwinid " +
					"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype=2 ";
		}
		else if(ntype==5  )  //并卡
		{
			strSql="select ttype=5,strCompId=changecompid,strBillId=changebillid,billdate=financedate,changeseqno=0,changebeforcardno,strCardNo=changeaftercardno,strCardType=changeaftercardtype,strMemberName=membername," +
				"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
				"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
				"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
				"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
				"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
				"  backflag=isnull(backflag,0),billinsertype=isnull(billinsertype,0),beautyManager,beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid,isxnj," +
				"  beautymangerno,beautymangerinid,beautygw,beaytygwinid " +
				"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype in (6,7) ";
		}
		else if(ntype==6  )  //退卡
		{
			strSql="select ttype=6,strCompId=changecompid,strBillId=changebillid,billdate=financedate,changeseqno=0,strCardNo=changebeforcardno,strCardType=changebeforcardtype,strMemberName=membername," +
				"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
				"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
				"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
				"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
				"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
				"  backflag=isnull(backflag,0),billinsertype=isnull(billinsertype,0)  " +
				"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype = 8 ";
		}
		else if(ntype==7  )  //合作项目
		{
			strSql=	"select ttype=7,strCompId=salecompid,strBillId=salebillid,billdate=financedate,changeseqno=0,strCardNo=salecostcardno,strCardType=salecostcardtype,strMemberName=membername," +
					" firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid='',seventhsalerinid='',seventhsaleamt=0,eighthsalerid='',eighthsalerinid='',eighthsaleamt=0," +
					"  ninthsalerid='',ninthsalerinid='',ninthsaleamt=0,tenthsalerid='',tenthsalerinid='',tenthsaleamt=0 ,backflag=0,billinsertype=0  " +
					"  from mcooperatesaleinfo with(nolock) where salecompid='"+strCompId+"' and salebillid='"+strBillId+"'  ";
		}
		else if(ntype==8  )  //条码卡
		{
			strSql=	"select ttype=8,strCompId=salecompid,strBillId=salebillid,billdate=saledate,changeseqno=0,strCardNo=barcodecardno,strCardType='',strMemberName=''," +
					"  firstsalerid=firstsaleempid,firstsalerinid=firstsaleempinid,firstsaleamt=firstsaleamt,secondsalerid=secondsaleempid,secondsalerinid=secondsaleempinid,secondsaleamt=secondsaleamt," +
					"  thirdsalerid=thirdsaleempid,thirdsalerinid=thirdsaleempinid,thirdsaleamt=thirdsaleamt,fourthsalerid='',fourthsalerinid='',fourthsaleamt=0,fifthsalerid='',fifthsalerinid='',fifthsaleamt=0," +
					"  sixthsalerid='',sixthsalerinid='',sixthsaleamt=0,seventhsalerid='',seventhsalerinid='',seventhsaleamt=0,eighthsalerid='',eighthsalerinid='',eighthsaleamt=0," +
					"  ninthsalerid='',ninthsalerinid='',ninthsaleamt=0,tenthsalerid='',tenthsalerinid='',tenthsaleamt=0 ,backflag=isnull(salebakflag,0),billinsertype=0  " +
					"  from msalebarcodecardinfo with(nolock) where salecompid='"+strCompId+"' and salebillid='"+strBillId+"'  ";
		}
		else if(ntype==9  )  //疗程兑换
		{
			strSql=	"select ttype=9,strCompId=a.changecompid,strBillId=a.changebillid,billdate=changedate,changeseqno=changeseqno,strCardNo=changecardno,strCardType=changecardtype,strMemberName=membername," +
					"  firstsalerid=firstsalerid,firstsalerinid=firstsalerinid,firstsaleamt=firstsaleamt,secondsalerid=secondsalerid,secondsalerinid=secondsalerinid,secondsaleamt=secondsaleamt," +
					"  thirdsalerid=thirdsalerid,thirdsalerinid=thirdsalerinid,thirdsaleamt=thirdsaleamt,fourthsalerid=fourthsalerid,fourthsalerinid=fourthsalerinid,fourthsaleamt=fourthsaleamt,fifthsalerid='',fifthsalerinid='',fifthsaleamt=0," +
					"  sixthsalerid='',sixthsalerinid='',sixthsaleamt=0,seventhsalerid='',seventhsalerinid='',seventhsaleamt=0,eighthsalerid='',eighthsalerinid='',eighthsaleamt=0," +
					"  ninthsalerid='',ninthsalerinid='',ninthsaleamt=0,tenthsalerid='',tenthsalerinid='',tenthsaleamt=0,backflag=isnull(b.salebakflag,0),prjno,prjname,billinsertype=0,beautymangerno,beautymangerinid,beautygw,beautygwinid  " +
					"  from mproexchangeinfo a with(nolock), dproexchangeinfo b with(nolock),projectnameinfo c with(nolock) " +
					"  where a.changecompid='"+strCompId+"' and a.changebillid='"+strBillId+"' and changeseqno="+changeseqno+" " +
					"   and a.changecompid=b.changecompid and a.changebillid=b.changebillid " +
					"  and changeproid=prjno  ";
		}
		else if(ntype==12)
		{
			strSql=	"select ttype=12,strCompId=a.compid,itemno,strBillId=a.billid,billdate=accountdate,changeseqno=0,strCardNo=cardno,strCardType=cardtype,iteminid,strMemberName=name," +
					"  firstsalerid=firstempno,firstsalerinid=firstinid,firstsaleamt=firstperf,secondsalerid=sendempno,secondsalerinid=sendinid,secondsaleamt=sendperf," +
					"  thirdsalerid=threeempno,thirdsalerinid=threeinid,thirdsaleamt=threeperf,fourthsalerid=fourempno,fourthsalerinid=fourinid,fourthsaleamt=fourperf,fifthsalerid='',fifthsalerinid='',fifthsaleamt=0," +
					"  sixthsalerid='',sixthsalerinid='',sixthsaleamt=0,seventhsalerid='',seventhsalerinid='',seventhsaleamt=0,eighthsalerid='',eighthsalerinid='',eighthsaleamt=0," +
					"  ninthsalerid='',ninthsalerinid='',ninthsaleamt=0,tenthsalerid='',tenthsalerinid='',tenthsaleamt=0,backflag=isnull(a.backcsflag,0),prjno=itemno,prjname,amt " +
					"  from yearsellinfo a with(nolock), yearselldetal b with(nolock),projectnameinfo c with(nolock) " +
					"  where a.compid='"+strCompId+"' and a.billid='"+strBillId+"' and iteminid='"+itemInid+"' " +
					"   and a.compid=b.compid and a.billid=b.billid " +
					"  and itemno=prjno  ";
		}
		AnlyResultSet<ReEditBillInfo > analysis = new AnlyResultSet<ReEditBillInfo >()
    	{
    			public ReEditBillInfo  anlyResultSet(ResultSet rs)
    			{
    				ReEditBillInfo record=null;
    				try
    				{
	    				while(rs != null && rs.next()==true)
	    				{
	    					record=new ReEditBillInfo();
	    					record.setBillType(CommonTool.FormatInteger(rs.getInt("ttype")));
	    					record.setStrCompId(CommonTool.FormatString(rs.getString("strCompId")));
	    					record.setStrBillId(CommonTool.FormatString(rs.getString("strBillId")));
	    					record.setChangeseqno(CommonTool.FormatDouble(rs.getDouble("changeseqno")));
	    					record.setStrCardNo(CommonTool.FormatString(rs.getString("strCardNo")));
	    					record.setStrCardType(CommonTool.FormatString(rs.getString("strCardType")));
	    					record.setStrMemberName(CommonTool.FormatString(rs.getString("strMemberName")));
	    					if(!CommonTool.FormatString(rs.getString("strMemberName")).equals(""))
	    					{
	    						record.setStrMemberName(CommonTool.FormatString(rs.getString("strMemberName")).substring(0,1)+"**");
	    					}
	    					record.setBackflag(CommonTool.FormatInteger(rs.getInt("backflag")));
	    					record.setBilldate(CommonTool.FormatString(rs.getString("billdate")));
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))==12)
	    					{
	    						record.setItemInid(rs.getString("iteminid"));
	    						record.setFirstsalerid(CommonTool.FormatString(rs.getString("firstsalerid")));
	    						record.setSecondsalerid(CommonTool.FormatString(rs.getString("secondsalerid")));
	    						record.setThirdsalerid(CommonTool.FormatString(rs.getString("thirdsalerid")));
	    						record.setFourthsalerid(CommonTool.FormatString(rs.getString("fourthsalerid")));
	    						record.setYearAmt(rs.getDouble("amt"));
	    						record.setStrCardTypeName(rs.getString("strCardType"));
	    						record.setFirstsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("firstsaleamt")))));
		    					record.setSecondsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("secondsaleamt")))));
		    					record.setThirdsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("thirdsaleamt")))));
		    					record.setFourthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fourthsaleamt")))));
		    					record.setStrPrjName(CommonTool.FormatString(rs.getString("prjname")));
		    					record.setStrPrjNo(rs.getString("itemno"));
	    					}
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))==0 || CommonTool.FormatInteger(rs.getInt("ttype"))==1 || CommonTool.FormatInteger(rs.getInt("ttype"))==2 
	    							|| CommonTool.FormatInteger(rs.getInt("ttype"))==3 || CommonTool.FormatInteger(rs.getInt("ttype"))==4 || CommonTool.FormatInteger(rs.getInt("ttype"))==5)
	    					{
	    						record.setIsxnj(rs.getInt("isxnj"));
	    					}
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))!=10 && CommonTool.FormatInteger(rs.getInt("ttype"))!=11&& CommonTool.FormatInteger(rs.getInt("ttype"))!=12)
	    					{
	    						record.setFirstsalerid(CommonTool.FormatString(rs.getString("firstsalerid")));
	    						record.setSecondsalerid(CommonTool.FormatString(rs.getString("secondsalerid")));
	    						record.setThirdsalerid(CommonTool.FormatString(rs.getString("thirdsalerid")));
	    						record.setFourthsalerid(CommonTool.FormatString(rs.getString("fourthsalerid")));
	    						record.setFifthsalerid(CommonTool.FormatString(rs.getString("fifthsalerid")));
		    					record.setSixthsalerid(CommonTool.FormatString(rs.getString("sixthsalerid")));
		    					record.setSeventhsalerid(CommonTool.FormatString(rs.getString("seventhsalerid")));
		    					record.setEighthsalerid(CommonTool.FormatString(rs.getString("eighthsalerid")));
		    					record.setNinthsalerid(CommonTool.FormatString(rs.getString("ninthsalerid")));
		    					record.setTenthsalerid(CommonTool.FormatString(rs.getString("tenthsalerid")));
		    					
		    					record.setFirstsalerinid(CommonTool.FormatString(rs.getString("firstsalerinid")));
		    					record.setSecondsalerinid(CommonTool.FormatString(rs.getString("secondsalerinid")));
		    					record.setThirdsalerinid(CommonTool.FormatString(rs.getString("thirdsalerinid")));
		    					record.setFourthsalerinid(CommonTool.FormatString(rs.getString("fourthsalerinid")));
		    					record.setFifthsalerinid(CommonTool.FormatString(rs.getString("fifthsalerinid")));
		    					record.setSixthsalerinid(CommonTool.FormatString(rs.getString("sixthsalerinid")));
		    					record.setSeventhsalerinid(CommonTool.FormatString(rs.getString("seventhsalerinid")));
		    					record.setEighthsalerinid(CommonTool.FormatString(rs.getString("eighthsalerinid")));
		    					record.setNinthsalerinid(CommonTool.FormatString(rs.getString("ninthsalerinid")));
		    					record.setTenthsalerinid(CommonTool.FormatString(rs.getString("tenthsalerinid")));
		    					
		    					record.setFirstsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("firstsaleamt")))));
		    					record.setSecondsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("secondsaleamt")))));
		    					record.setThirdsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("thirdsaleamt")))));
		    					record.setFourthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fourthsaleamt")))));
		    					record.setFifthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fifthsaleamt")))));
		    					record.setSixthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sixthsaleamt")))));
		    					record.setSeventhsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("seventhsaleamt")))));
		    					record.setEighthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("eighthsaleamt")))));
		    					record.setNinthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ninthsaleamt")))));
		    					record.setTenthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tenthsaleamt")))));
		    					record.setBillinsertype(CommonTool.FormatInteger(rs.getInt("billinsertype")));
	    					}
	    					
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))==0 || CommonTool.FormatInteger(rs.getInt("ttype"))==1 || CommonTool.FormatInteger(rs.getInt("ttype"))==2 ||
	    							CommonTool.FormatInteger(rs.getInt("ttype"))==3 || CommonTool.FormatInteger(rs.getInt("ttype"))==4 || CommonTool.FormatInteger(rs.getInt("ttype"))==5)
	    					{
	    						record.setBeautyManager(CommonTool.FormatString(rs.getString("beautyManager")));
	    						record.setBeautyManagerinid(CommonTool.FormatString(rs.getString("beautyManagerinid")));
	    						record.setConsultant(CommonTool.FormatString(rs.getString("consultant")));
	    						record.setConsultantinid(CommonTool.FormatString(rs.getString("consultantinid")));
	    						record.setConsultant1(CommonTool.FormatString(rs.getString("consultant1")));
	    						record.setConsultant1inid(CommonTool.FormatString(rs.getString("consultant1inid")));
	    						record.setBeautyMangerNo(CommonTool.FormatString(rs.getString("beautymangerno")));
	    						record.setBeautyMangerNoInid(CommonTool.FormatString(rs.getString("beautymangerinid")));
	    						record.setBeautyGw(CommonTool.FormatString(rs.getString("beautygw")));
	    						record.setBeautyGwInId(CommonTool.FormatString(rs.getString("beaytygwinid")));
	    					}
	    					//疗程兑换
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))==9)
	    					{
	    						record.setStrPrjNo(CommonTool.FormatString(rs.getString("prjno")));
	    						record.setStrPrjName(CommonTool.FormatString(rs.getString("prjname")));
	    						record.setBeautyMangerNo(CommonTool.FormatString(rs.getString("beautymangerno")));
	    						record.setBeautyMangerNoInid(CommonTool.FormatString(rs.getString("beautymangerinid")));
	    						record.setBeautyGw(CommonTool.FormatString(rs.getString("beautygw")));
	    						record.setBeautyGwInId(CommonTool.FormatString(rs.getString("beautygwinid")));
	    					}
	    					//转卡 老卡
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))==2
	    					|| CommonTool.FormatInteger(rs.getInt("ttype"))==3
	    					|| CommonTool.FormatInteger(rs.getInt("ttype"))==4
	    					|| CommonTool.FormatInteger(rs.getInt("ttype"))==5)
	    					{
	    						record.setStrOldCardNo(CommonTool.FormatString(rs.getString("changebeforcardno")));
	    					}
	    					
	    					if(CommonTool.FormatInteger(rs.getInt("ttype"))==0
	    					|| CommonTool.FormatInteger(rs.getInt("ttype"))==1
	    					|| CommonTool.FormatInteger(rs.getInt("ttype"))==2
	    	    			|| CommonTool.FormatInteger(rs.getInt("ttype"))==3
	    	    			|| CommonTool.FormatInteger(rs.getInt("ttype"))==4
	    	    			|| CommonTool.FormatInteger(rs.getInt("ttype"))==5
	    	    			|| CommonTool.FormatInteger(rs.getInt("ttype"))==6)
	    					{
	    						record.setFirstsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("firstsalecashamt")))));
		    					record.setSecondsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("secondsalecashamt")))));
		    					record.setThirdsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("thirdsalecashamt")))));
		    					record.setFourthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fourthsalecashamt")))));
		    					record.setFifthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fifthsalecashamt")))));
		    					record.setSixthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sixthsalecashamt")))));
		    					record.setSeventhsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("seventhsalecashamt")))));
		    					record.setEighthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("eighthsalecashamt")))));
		    					record.setNinthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ninthsalecashamt")))));
		    					record.setTenthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tenthsalecashamt")))));
		    					
	    					}
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
    	ReEditBillInfo returnvalue =(ReEditBillInfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
    	if(ntype!=12)
    	{
    		returnvalue.setStrCardTypeName(this.dataTool.loadCardTypeName(returnvalue.getStrCompId(), CommonTool.FormatString(returnvalue.getStrCardType()), new StringBuffer()));
    	}
    	analysis=null;
    
    	return returnvalue;
	}
	
	public List<ReEditBillInfo> loadBillModifyHistory(String strCompId,String strBillId,int ntype)
	{
		try
		{
			String strSql="";
			if(ntype==0)  //开卡
			{
				strSql="select ttype=0, strCompId=salecompid,strBillId=salebillid,billdate=saledate,strCardNo=salecardno," +
						"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
						"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
						"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
						"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
						"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
						"  optionuserno,optiondate,optiontime,billinsertype=isnull(billinsertype,0),beautyManager,beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid " +
						"  from msalecardinfolog with(nolock) where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
			}
			else if(ntype==1)  //充值
			{
				strSql=" select ttype=1,strCompId=rechargecompid,strBillId=rechargebillid,billdate=rechargedate,strCardNo=rechargecardno," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
					"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
					"  optionuserno,optiondate,optiontime,billinsertype=isnull(billinsertype,0),beautyManager," +
					"  beautyManagerinid,consultant,consultantinid,consultant1,consultant1inid " +
					"  from mcardrechargeinfolog with(nolock) where rechargecompid='"+strCompId+"' and rechargebillid='"+strBillId+"' ";
			}
			else if(ntype==2  )  //转卡
			{
				strSql="select ttype=2,strCompId=changecompid,strBillId=changebillid,billdate=changedate,strCardNo=changebeforcardno," +
						"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
						"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
						"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
						"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
						"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
						"  optionuserno,optiondate,optiontime,billinsertype=isnull(billinsertype,0),beautyManager,beautyManagerinid,consultant," +
						"  consultantinid,consultant1,consultant1inid " +
						"  from mcardchangeinfolog with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype=0 ";
			}
			else if(ntype==3 )  //转卡
			{
				strSql="select ttype=3,strCompId=changecompid,strBillId=changebillid,billdate=changedate,strCardNo=changebeforcardno," +
						"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
						"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
						"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
						"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
						"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
						"  optionuserno,optiondate,optiontime,billinsertype=isnull(billinsertype,0),beautyManager,beautyManagerinid," +
						"  consultant,consultantinid,consultant1,consultant1inid " +
						"  from mcardchangeinfolog with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype=1 ";
			}
			else if(ntype==4   )  //转卡
			{
				strSql="select ttype=4,strCompId=changecompid,strBillId=changebillid,billdate=changedate,strCardNo=changebeforcardno," +
						"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
						"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
						"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
						"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
						"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
						"  optionuserno,optiondate,optiontime,billinsertype=isnull(billinsertype,0),beautyManager,beautyManagerinid," +
						"  consultant,consultantinid,consultant1,consultant1inid " +
						"  from mcardchangeinfolog with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype=2 ";
			}
			else if(ntype==5  )  //并卡
			{
				strSql="select ttype=5,strCompId=changecompid,strBillId=changebillid,billdate=changedate,strCardNo=changebeforcardno," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
					"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
					"  optionuserno,optiondate,optiontime,billinsertype=isnull(billinsertype,0),beautyManager,beautyManagerinid,consultant," +
					"  consultantinid,consultant1,consultant1inid " +
					"  from mcardchangeinfolog with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype in (6,7) ";
			}
			else if(ntype==6  )  //退卡
			{
				strSql="select ttype=6,strCompId=changecompid,strBillId=changebillid,billdate=changedate,strCardNo=changebeforcardno," +
					"  firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
					"  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
					"  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
					"  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
					"  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
					"  optionuserno,optiondate,optiontime,billinsertype=isnull(billinsertype,0)  " +
					"  from mcardchangeinfolog with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype = 8 ";
			}
			else
			{
				return null;
			}
			AnlyResultSet<List<ReEditBillInfo>> analysis = new AnlyResultSet<List<ReEditBillInfo> >()
	    	{
	    			public List<ReEditBillInfo>  anlyResultSet(ResultSet rs)
	    			{
	    				List<ReEditBillInfo> returnvalue=new ArrayList();
	    				ReEditBillInfo record=null;
	    				try
	    				{
		    				while(rs != null && rs.next()==true)
		    				{
		    					record=new ReEditBillInfo();
		    					record.setStrCompId(CommonTool.FormatString(rs.getString("strCompId")));
		    					record.setStrBillId(CommonTool.FormatString(rs.getString("strBillId")));
		    					record.setStrCardNo(CommonTool.FormatString(rs.getString("strCardNo")));
		    					record.setBilldate(CommonTool.FormatString(rs.getString("billdate")));
		    					record.setOptiondate(CommonTool.getDateMask(rs.getString("optiondate")));
		    					record.setOptiontime(CommonTool.getTimeMask(rs.getString("optiontime"),1));
		    					record.setOptionuserno(CommonTool.FormatString(rs.getString("optionuserno")));
		    					if(CommonTool.FormatInteger(rs.getInt("ttype"))!=10 && CommonTool.FormatInteger(rs.getInt("ttype"))!=11)
		    					{
		    						record.setFirstsalerid(CommonTool.FormatString(rs.getString("firstsalerid")));
		    						record.setSecondsalerid(CommonTool.FormatString(rs.getString("secondsalerid")));
		    						record.setThirdsalerid(CommonTool.FormatString(rs.getString("thirdsalerid")));
		    						record.setFourthsalerid(CommonTool.FormatString(rs.getString("fourthsalerid")));
		    						record.setFifthsalerid(CommonTool.FormatString(rs.getString("fifthsalerid")));
			    					record.setSixthsalerid(CommonTool.FormatString(rs.getString("sixthsalerid")));
			    					record.setSeventhsalerid(CommonTool.FormatString(rs.getString("seventhsalerid")));
			    					record.setEighthsalerid(CommonTool.FormatString(rs.getString("eighthsalerid")));
			    					record.setNinthsalerid(CommonTool.FormatString(rs.getString("ninthsalerid")));
			    					record.setTenthsalerid(CommonTool.FormatString(rs.getString("tenthsalerid")));
			    					
			    					record.setFirstsalerinid(CommonTool.FormatString(rs.getString("firstsalerinid")));
			    					record.setSecondsalerinid(CommonTool.FormatString(rs.getString("secondsalerinid")));
			    					record.setThirdsalerinid(CommonTool.FormatString(rs.getString("thirdsalerinid")));
			    					record.setFourthsalerinid(CommonTool.FormatString(rs.getString("fourthsalerinid")));
			    					record.setFifthsalerinid(CommonTool.FormatString(rs.getString("fifthsalerinid")));
			    					record.setSixthsalerinid(CommonTool.FormatString(rs.getString("sixthsalerinid")));
			    					record.setSeventhsalerinid(CommonTool.FormatString(rs.getString("seventhsalerinid")));
			    					record.setEighthsalerinid(CommonTool.FormatString(rs.getString("eighthsalerinid")));
			    					record.setNinthsalerinid(CommonTool.FormatString(rs.getString("ninthsalerinid")));
			    					record.setTenthsalerinid(CommonTool.FormatString(rs.getString("tenthsalerinid")));
			    					
			    					record.setFirstsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("firstsaleamt")))));
			    					record.setSecondsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("secondsaleamt")))));
			    					record.setThirdsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("thirdsaleamt")))));
			    					record.setFourthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fourthsaleamt")))));
			    					record.setFifthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fifthsaleamt")))));
			    					record.setSixthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sixthsaleamt")))));
			    					record.setSeventhsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("seventhsaleamt")))));
			    					record.setEighthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("eighthsaleamt")))));
			    					record.setNinthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ninthsaleamt")))));
			    					record.setTenthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tenthsaleamt")))));
			    					record.setBillinsertype(CommonTool.FormatInteger(rs.getInt("billinsertype")));
		    					
		    						record.setFirstsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("firstsalecashamt")))));
			    					record.setSecondsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("secondsalecashamt")))));
			    					record.setThirdsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("thirdsalecashamt")))));
			    					record.setFourthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fourthsalecashamt")))));
			    					record.setFifthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fifthsalecashamt")))));
			    					record.setSixthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sixthsalecashamt")))));
			    					record.setSeventhsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("seventhsalecashamt")))));
			    					record.setEighthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("eighthsalecashamt")))));
			    					record.setNinthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("ninthsalecashamt")))));
			    					record.setTenthsalecashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("tenthsalecashamt")))));
			    				}
		    					
		    					if(CommonTool.FormatInteger(rs.getInt("ttype"))==0 || CommonTool.FormatInteger(rs.getInt("ttype"))==1 || CommonTool.FormatInteger(rs.getInt("ttype"))==2 ||
		    							CommonTool.FormatInteger(rs.getInt("ttype"))==3 || CommonTool.FormatInteger(rs.getInt("ttype"))==4 || CommonTool.FormatInteger(rs.getInt("ttype"))==5)
		    					{
		    						record.setBeautyManager(CommonTool.FormatString(rs.getString("beautyManager")));
		    						record.setBeautyManagerinid(CommonTool.FormatString(rs.getString("beautyManagerinid")));
		    						record.setConsultant(CommonTool.FormatString(rs.getString("consultant")));
		    						record.setConsultantinid(CommonTool.FormatString(rs.getString("consultantinid")));
		    						record.setConsultant1(CommonTool.FormatString(rs.getString("consultant1")));
		    						record.setConsultant1inid(CommonTool.FormatString(rs.getString("consultant1inid")));
		    					}
		    					returnvalue.add(record);
		    				}
		    				
	    				}
	    				catch(Exception e)
	    				{
	    					e.printStackTrace();
	    					record =  null;
	    				}
	    				return returnvalue;
	    			}
	    	};
	    	List<ReEditBillInfo> ls =(List<ReEditBillInfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
	    	analysis=null;
	    	return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Dconsumeinfo> loadCostHistory(String strCompId,String strBillId,int ntype)
	{
		try
		{
			String strSql="";
			if(ntype==10)
			{
				strSql=" select b.cscompid,b.csbillid,csitemno,csitemname=prjname," +
						" csfirstsaler,csfirsttype,csfirstshare,cssecondsaler,cssecondtype,cssecondshare,csthirdsaler,csthirdtype,csthirdshare," +
						" cspaymode,csdisprice,csitemcount,csitemamt,optionuserno,optiondate,optiontime " +
				"  from dconsumeinfolog b with(nolock) left join projectnameinfo on prjno=csitemno " +
				"  where b.cscompid='"+strCompId+"' and b.cscompid= '"+strCompId+"' and csinfotype=1 ";
			}
			else if(ntype==11)
			{
				strSql=" select b.cscompid,b.csbillid,csitemno,csitemname=goodsname," +
				" csfirstsaler,csfirsttype,csfirstshare,cssecondsaler,cssecondtype,cssecondshare,csthirdsaler,csthirdtype,csthirdshare," +
				" cspaymode,csdisprice,csitemcount,csitemamt,optionuserno,optiondate,optiontime " +
				"  from dconsumeinfolog b with(nolock)  left join goodsnameinfo on goodsno=csitemno  " +
				"  where b.cscompid='"+strCompId+"' and b.cscompid= '"+strCompId+"' and csinfotype=2 ";
			}
			
			AnlyResultSet<List<Dconsumeinfo>> analysis = new AnlyResultSet<List<Dconsumeinfo>>() {
				public List<Dconsumeinfo> anlyResultSet(ResultSet rs) {
					List<Dconsumeinfo> returnValue = new ArrayList();
					Dconsumeinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Dconsumeinfo();
							record.setBcscompid(CommonTool.FormatString(rs.getString("cscompid")));
							record.setBcsbillid(CommonTool.FormatString(rs.getString("csbillid")));
							record.setCsitemno(CommonTool.FormatString(rs.getString("csitemno")));
							record.setCsitemname(CommonTool.FormatString(rs.getString("csitemname")));
							record.setCsfirstsaler(CommonTool.FormatString(rs.getString("csfirstsaler")));
							record.setCssecondsaler(CommonTool.FormatString(rs.getString("cssecondsaler")));
							record.setCsthirdsaler(CommonTool.FormatString(rs.getString("csthirdsaler")));
							
							record.setCsfirsttype(CommonTool.FormatString(rs.getString("csfirsttype")));
							record.setCssecondtype(CommonTool.FormatString(rs.getString("cssecondtype")));
							record.setCsthirdtype(CommonTool.FormatString(rs.getString("csthirdtype")));
							
							record.setCsfirstshare(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("csfirstshare")))));
							record.setCssecondshare(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("cssecondshare")))));
							record.setCsthirdshare(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("csthirdshare")))));
							
							record.setCspaymode(CommonTool.FormatString(rs.getString("cspaymode")));
							record.setCsdisprice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("csdisprice")))));
							record.setCsitemcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("csitemcount")))));
							record.setCsitemamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("csitemamt")))));
							record.setOptiondate(CommonTool.getDateMask(rs.getString("optiondate")));
	    					record.setOptiontime(CommonTool.getTimeMask(rs.getString("optiontime"),1));
	    					record.setOptionuserno(CommonTool.FormatString(rs.getString("optionuserno")));
	    					returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Dconsumeinfo> ls= (List<Dconsumeinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Dproexchangeinfo> loadProHistory(String strCompId,String strBillId,double changeseqno)
	{
		try
		{
			String strSql="select changecompid,changebillid,changeproid,changeproname=prjname,changeprocount,changeprorate,changeproamt," +
					" firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt," +
					" optionuserno,optiondate,optiontime " +
					" from dproexchangeinfolog left join projectnameinfo on prjno=changeproid " +
					" where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changeseqno="+changeseqno+" ";
			
			AnlyResultSet<List<Dproexchangeinfo>> analysis = new AnlyResultSet<List<Dproexchangeinfo>>() {
				public List<Dproexchangeinfo> anlyResultSet(ResultSet rs) {
					List<Dproexchangeinfo> returnValue = new ArrayList();
					Dproexchangeinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Dproexchangeinfo();
							record.setId(new DproexchangeinfoId(CommonTool.FormatString(rs.getString("changebillid")),CommonTool.FormatString(rs.getString("changebillid")),0));
							record.setChangeproid(CommonTool.FormatString(rs.getString("changeproid")));
							record.setChangeproname(CommonTool.FormatString(rs.getString("changeproname")));
							
							record.setFirstsalerid(CommonTool.FormatString(rs.getString("firstsalerid")));
							record.setSecondsalerid(CommonTool.FormatString(rs.getString("secondsalerid")));
							record.setThirdsalerid(CommonTool.FormatString(rs.getString("thirdsalerid")));
							
							
							record.setFirstsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("firstsaleamt")))));
							record.setSecondsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("secondsaleamt")))));
							record.setThirdsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("thirdsaleamt")))));
							
							
							record.setChangeprocount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeprocount")))));
							record.setChangeproamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeproamt")))));
							record.setOptiondate(CommonTool.getDateMask(rs.getString("optiondate")));
	    					record.setOptiontime(CommonTool.getTimeMask(rs.getString("optiontime"),1));
	    					record.setOptionuserno(CommonTool.FormatString(rs.getString("optionuserno")));
	    					returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Dproexchangeinfo> ls= (List<Dproexchangeinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Dpayinfo> loadDpayinfoByBill(String strCompId,String strBillId,int billtype,double changeseqno)
	{
		try
		{
			String strSql="";
			String strPayType="";
			if(billtype==0)
				strPayType="SK";
			else if(billtype==1)
				strPayType="CZ";
			else if(billtype==2 ||billtype==3 ||billtype==4 || billtype==5)
				strPayType="ZK";
			else if(billtype==6)
				strPayType="TK";
			else if(billtype==7)
				strPayType="HZW";
			if(billtype!=8 && billtype!=9)
			{
				strSql=" From Dpayinfo dpayinfo  where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='"+strPayType+"' ";
				return (List<Dpayinfo>)this.amn_Dao.findByHql(strSql);
			}
			else if(billtype==8)
			{
				strSql=" select salecompid,salebillid,firstpaymode,firstpayamt,secondpaymode,secondpayamt,usecardpayamt from msalebarcodecardinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
				AnlyResultSet<List<Dpayinfo> > analysis = new AnlyResultSet<List<Dpayinfo> >()
		    	{
		    			public List<Dpayinfo>  anlyResultSet(ResultSet rs)
		    			{
		    				List<Dpayinfo>  returnValue = new ArrayList();
		    				Dpayinfo record=null;
		    				try
		    				{
			    				if(rs != null && rs.next()==true)
			    				{
			    					if(!CommonTool.FormatString(rs.getString("firstpaymode")).equals("")
			    					&& CommonTool.FormatDouble(rs.getDouble("firstpayamt"))!=0 )
			    					{
			    						record=new Dpayinfo();
			    						record.setId(new DpayinfoId(rs.getString("salecompid"),rs.getString("salebillid"),"TMK",0));
			    						record.setPaymode(rs.getString("firstpaymode"));
			    						record.setPayamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("firstpayamt"))));
			    						returnValue.add(record);
			    					}
			    					if(!CommonTool.FormatString(rs.getString("secondpaymode")).equals("")
					    					&& CommonTool.FormatDouble(rs.getDouble("secondpayamt"))!=0 )
					    			{
					    						record=new Dpayinfo();
					    						record.setId(new DpayinfoId(rs.getString("salecompid"),rs.getString("salebillid"),"TMK",0));
					    						record.setPaymode(rs.getString("secondpaymode"));
					    						record.setPayamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("secondpayamt"))));
					    						returnValue.add(record);
					    			}
			    					if(CommonTool.FormatDouble(rs.getDouble("usecardpayamt"))!=0 )
					    			{
					    						record=new Dpayinfo();
					    						record.setId(new DpayinfoId(rs.getString("salecompid"),rs.getString("salebillid"),"TMK",0));
					    						record.setPaymode("4");
					    						record.setPayamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("usecardpayamt"))));
					    						returnValue.add(record);
					    			}
			    				}
			    				
		    				}
		    				catch(Exception e)
		    				{
		    					e.printStackTrace();
		    					returnValue =  null;
		    				}
		    				record=null;
		    				return returnValue;
		    			}
		    	};
		    	List<Dpayinfo> ls =(List<Dpayinfo> )this.amn_Dao.executeQuery_ex(strSql,analysis);
		    	analysis=null;
		    	return ls;
			
			}
			else if(billtype==9)
			{
				strSql=" select changecompid,changebillid,changepaymode,changebycashamt=isnull(changebycashamt,0),changebyaccountamt=isnull(changebyaccountamt,0),changebysendaccountamt=isnull(changebysendaccountamt,0) " +
						" from dproexchangeinfo where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' " +
						" and changeseqno="+changeseqno+" ";
				AnlyResultSet<List<Dpayinfo> > analysis = new AnlyResultSet<List<Dpayinfo> >()
		    	{
		    			public List<Dpayinfo>  anlyResultSet(ResultSet rs)
		    			{
		    				List<Dpayinfo>  returnValue = new ArrayList();
		    				Dpayinfo record=null;
		    				try
		    				{
			    				if(rs != null && rs.next()==true)
			    				{
			    					if(!CommonTool.FormatString(rs.getString("changepaymode")).equals("")
			    					&& CommonTool.FormatDouble(rs.getDouble("changebycashamt"))!=0 )
			    					{
			    						record=new Dpayinfo();
			    						record.setId(new DpayinfoId(rs.getString("changecompid"),rs.getString("changebillid"),"LCDH",0));
			    						record.setPaymode(rs.getString("changepaymode"));
			    						record.setPayamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("changebycashamt"))));
			    						returnValue.add(record);
			    					}
			    					if(CommonTool.FormatDouble(rs.getDouble("changebyaccountamt"))!=0 )
					    			{
					    						record=new Dpayinfo();
					    						record.setId(new DpayinfoId(rs.getString("changecompid"),rs.getString("changebillid"),"LCDH",0));
					    						record.setPaymode("4");
					    						record.setPayamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("changebyaccountamt"))));
					    						returnValue.add(record);
					    			}
			    					if(CommonTool.FormatDouble(rs.getDouble("changebysendaccountamt"))!=0 )
					    			{
					    						record=new Dpayinfo();
					    						record.setId(new DpayinfoId(rs.getString("changecompid"),rs.getString("changebillid"),"LCDH",1));
					    						record.setPaymode("17");
					    						record.setPayamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("changebysendaccountamt"))));
					    						returnValue.add(record);
					    			}
			    					
			    				}
			    				
		    				}
		    				catch(Exception e)
		    				{
		    					e.printStackTrace();
		    					returnValue =  null;
		    				}
		    				record=null;
		    				return returnValue;
		    			}
		    	};
		    	List<Dpayinfo> ls =(List<Dpayinfo> )this.amn_Dao.executeQuery_ex(strSql,analysis);
		    	analysis=null;
		    	return ls;
			
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public boolean postCurDateInfo(String strCompId,String strBillId,double changeseqno,int nBillType,ReEditBillInfo curRecord,List<Dpayinfo> lsDpayinfo)
	{
		try
		{
			String strSql="";
			if(nBillType==0)  //开卡
			{	//mgx20140904
				strSql=strSql+" insert msalecardinfolog(salecompid,salebillid,saledate,saletime,salecardno,saletotalamt,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
						      "  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
						      "  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
						      "  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
						      " firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
						      " billinsertype,optionuserno,optiondate,optiontime,beautyManager,consultant,beautyPerf,consultantPerf,beautyManagerinid,consultantinid,consultant1,consultant1inid,consultant1Perf " +
						      " )" +
						      "  select salecompid,salebillid,saledate,saletime,salecardno,saletotalamt,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
						      "  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
						      "  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
						      "  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
						      " firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
						      " billinsertype,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"',beautyManager,consultant,beautyPerf,consultantPerf,beautyManagerinid,consultantinid,consultant1,consultant1inid,consultant1Perf from msalecardinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
				
				strSql=strSql+"update msalecardinfo set  saleremark=saleremark,billinsertype="+CommonTool.FormatInteger(curRecord.getBillinsertype())+",isxnj="+CommonTool.FormatInteger(curRecord.getIsxnj())+" ";
			}
			else if(nBillType==1)  //充值
			{	//mgx20140904
				strSql=strSql+" insert mcardrechargeinfolog(rechargecompid,rechargebillid,rechargedate,rechargecardno,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
			      "  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
			      "  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
			      "  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
			      " firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
			      " billinsertype,optionuserno,optiondate,optiontime,beautyManager,consultant,beautyPerf,consultantPerf,beautyManagerinid,consultantinid,consultant1,consultant1inid,consultant1Perf " +
			      " )" +
			      "  select rechargecompid,rechargebillid,rechargedate,rechargecardno,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
			      "  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
			      "  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
			      "  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
			      " firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
			      " billinsertype,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"',beautyManager,consultant,beautyPerf,consultantPerf,beautyManagerinid,consultantinid,consultant1,consultant1inid,consultant1Perf from mcardrechargeinfo where rechargecompid='"+strCompId+"' and rechargebillid='"+strBillId+"' ";
	
				strSql=strSql+" update mcardrechargeinfo set  rechargeremark=rechargeremark,billinsertype="+CommonTool.FormatInteger(curRecord.getBillinsertype())+",isxnj="+CommonTool.FormatInteger(curRecord.getIsxnj())+" ";
			}
			else if(nBillType==2 || nBillType==3 ||nBillType==4 ||nBillType==5 ||nBillType==6 )  //转卡
			{	//mgx20140904
				strSql=strSql+" insert mcardchangeinfolog(changecompid,changebillid,changetype,changedate,changebeforcardno,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
			      "  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
			      "  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
			      "  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
			      " firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
			      " billinsertype,optionuserno,optiondate,optiontime,beautyManager,consultant,beautyPerf,consultantPerf,beautyManagerinid,consultantinid,consultant1,consultant1inid,consultant1Perf)" +
			      "  select changecompid,changebillid,changetype,changedate,changebeforcardno,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt," +
			      "  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt," +
			      "  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt," +
			      "  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
			      " firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt," +
			      " billinsertype,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"',beautyManager,consultant,beautyPerf,consultantPerf,beautyManagerinid,consultantinid,consultant1,consultant1inid,consultant1Perf from mcardchangeinfo where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' ";
	
				strSql=strSql+"update mcardchangeinfo set  rechargeremark=rechargeremark,billinsertype="+CommonTool.FormatInteger(curRecord.getBillinsertype())+",isxnj="+CommonTool.FormatInteger(curRecord.getIsxnj())+" ";
			}
			else if(nBillType==7)  //合作项目
			{
				strSql=strSql+" update mcooperatesaleinfo set  salemark=salemark ";
			}
			else if(nBillType==8)  //条码卡
				strSql="update msalebarcodecardinfo set  saletime=saletime ";
			else if(nBillType==9)  //疗程兑换
			{
				strSql=strSql+" insert dproexchangeinfolog(changecompid,changebillid,changeseqno,changeproid,changeprocount,changeprorate,changeproamt," +
				" firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt, " +
				" optionuserno,optiondate,optiontime) " +
				" select changecompid,changebillid,changeseqno,changeproid,changeprocount,changeprorate,changeproamt," +
				" firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt, " +
				" '"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"' from dproexchangeinfo" +
				" where changecompid='"+strCompId+"' and changebillid='"+strBillId+"'  and changeseqno="+changeseqno+" ";
				strSql=strSql+"update dproexchangeinfo set  nointernalcardno=nointernalcardno ";
			}
			else if(nBillType==12)
			{
				//strSql="insert into yearsellinfo(compid,billid,cardno,phone,name,)";
				strSql="update yearselldetal set itemno=itemno";
			}
			if(curRecord.getEditType1()==true)
			{
				if(!curRecord.getFirstsalerid().equals(""))
				{
					curRecord.setFirstsalerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getFirstsalerid()));
				}
				else
				{
					curRecord.setFirstsalerinid("");
				}
				if(nBillType==12)
				{
					strSql=strSql+" ,firstempno='"+curRecord.getFirstsalerid()+"',firstinid='"+curRecord.getFirstsalerinid()+"',firstperf="+curRecord.getFirstsaleamt()+" ";
				}
				else if(nBillType!=8)
					strSql=strSql+",firstsalerid='"+curRecord.getFirstsalerid()+"',firstsalerinid='"+curRecord.getFirstsalerinid()+"',firstsaleamt="+curRecord.getFirstsaleamt()+",firstsalecashamt="+curRecord.getFirstsalecashamt()+" ";
				else
					strSql=strSql+",firstsaleempid='"+curRecord.getFirstsalerid()+"',firstsaleempinid='"+curRecord.getFirstsalerinid()+"',firstsaleamt="+curRecord.getFirstsaleamt()+" ";
			}
			if(curRecord.getEditType2()==true)
			{
				if(!curRecord.getSecondsalerid().equals(""))
				{
					curRecord.setSecondsalerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getSecondsalerid()));
				}
				else
				{
					curRecord.setSecondsalerinid("");
				}
				
				if(nBillType==12)
				{
					strSql=strSql+" ,sendempno='"+curRecord.getSecondsalerid()+"',sendinid='"+curRecord.getSecondsalerinid()+"',sendperf="+curRecord.getSecondsaleamt()+" ";
				}
				else if(nBillType!=8 )
					strSql=strSql+",secondsalerid='"+curRecord.getSecondsalerid()+"',secondsalerinid='"+curRecord.getSecondsalerinid()+"',secondsaleamt="+curRecord.getSecondsaleamt()+",secondsalecashamt="+curRecord.getSecondsalecashamt()+" ";
				else if (nBillType==8)
					strSql=strSql+",secondsaleempid='"+curRecord.getSecondsalerid()+"',secondsaleempinid='"+curRecord.getSecondsalerinid()+"',secondsaleamt="+curRecord.getSecondsaleamt()+" ";
			
			}
			if(curRecord.getEditType3()==true)
			{
				if(!curRecord.getThirdsalerid().equals(""))
				{
					curRecord.setThirdsalerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getThirdsalerid()));
				}
				else
				{
					curRecord.setThirdsalerinid("");
				}
				if(nBillType==12)
				{
					strSql=strSql+" ,threeempno='"+curRecord.getThirdsalerid()+"',threeinid='"+curRecord.getThirdsalerinid()+"',threeperf="+curRecord.getThirdsaleamt()+" ";
				}
				else if(nBillType!=8 )
					strSql=strSql+",thirdsalerid='"+curRecord.getThirdsalerid()+"',thirdsalerinid='"+curRecord.getThirdsalerinid()+"',thirdsaleamt="+curRecord.getThirdsaleamt()+",thirdsalecashamt="+curRecord.getThirdsalecashamt()+" ";
				else if(nBillType==8)
					strSql=strSql+",thirdsaleempid='"+curRecord.getThirdsalerid()+"',thirdsaleempinid='"+curRecord.getThirdsalerinid()+"',thirdsaleamt="+curRecord.getThirdsaleamt()+" ";
			
			}
			
			if(curRecord.getEditType4()==true)
			{
				if(!curRecord.getFourthsalerid().equals(""))
				{
					curRecord.setFourthsalerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getFourthsalerid()));
				}
				else
				{
					curRecord.setFourthsalerinid("");
				}
				if(nBillType==12)
				{
					strSql=strSql+" ,fourempno='"+curRecord.getFourthsalerid()+"',fourinid='"+curRecord.getFourthsalerinid()+"',fourperf="+curRecord.getFourthsaleamt()+" ";
				}
				else if(nBillType!=8)
					strSql=strSql+",fourthsalerid='"+curRecord.getFourthsalerid()+"',fourthsalerinid='"+curRecord.getFourthsalerinid()+"',fourthsaleamt="+curRecord.getFourthsaleamt()+" ,fourthsalecashamt="+curRecord.getFourthsalecashamt()+" ";
			}
			
			if(curRecord.getEditType5()==true)
			{
				if(!curRecord.getFifthsalerid().equals(""))
				{
					curRecord.setFifthsalerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getFifthsalerid()));
				}
				else
				{
					curRecord.setFifthsalerinid("");
				}
				if(nBillType!=8 && nBillType!=9  )
					strSql=strSql+",fifthsalerid='"+curRecord.getFifthsalerid()+"',  fifthsalerinid ='"+curRecord.getFifthsalerinid()+"',fifthsaleamt ="+curRecord.getFifthsaleamt()+",fifthsalecashamt ="+curRecord.getFifthsalecashamt()+" ";
				//if(nBillType==9 )
					//strSql=strSql+",secondsalerid='"+curRecord.getFifthsalerid()+"',secondsalerinid='"+curRecord.getFifthsalerinid()+"',secondsaleamt="+curRecord.getFifthsaleamt()+" ";
				
			}
			
			if(curRecord.getEditType6()==true)
			{
				if(!curRecord.getSixthsalerid().equals(""))
				{
					curRecord.setSixthsalerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getSixthsalerid()));
				}
				else
				{
					curRecord.setSixthsalerinid("");
				}
				if(nBillType!=8 && nBillType!=9)
					strSql=strSql+",sixthsalerid='"+curRecord.getSixthsalerid()+"',sixthsalerinid='"+curRecord.getSixthsalerinid()+"',sixthsaleamt="+curRecord.getSixthsaleamt()+",sixthsalecashamt="+curRecord.getSixthsalecashamt()+" ";
			}
			
			if(curRecord.getEditType7()==true)
			{
				if(!curRecord.getSeventhsalerid().equals(""))
				{
					curRecord.setSeventhsalerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getSeventhsalerid()));
				}
				else
				{
					curRecord.setSeventhsalerinid("");
				}
				if(nBillType!=8 && nBillType!=9)
					strSql=strSql+",seventhsalerid='"+curRecord.getSeventhsalerid()+"',seventhsalerinid='"+curRecord.getSeventhsalerinid()+"',seventhsaleamt="+curRecord.getSeventhsaleamt()+",seventhsalecashamt="+curRecord.getSeventhsalecashamt()+" ";
			}

			if(curRecord.getEditType8()==true)
			{
				if(!curRecord.getEighthsalerid().equals(""))
				{
					curRecord.setEighthsalerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getEighthsalerid()));
				}
				else
				{
					curRecord.setEighthsalerinid("");
				}
				if(nBillType!=8 && nBillType!=9)
					strSql=strSql+",eighthsalerid='"+curRecord.getEighthsalerid()+"',eighthsalerinid='"+curRecord.getEighthsalerinid()+"',eighthsaleamt="+curRecord.getEighthsaleamt()+",eighthsalecashamt="+curRecord.getEighthsalecashamt()+" ";
			}
			
			if(curRecord.getEditType9()==true)
			{
				if(!curRecord.getNinthsalerid().equals(""))
				{
					curRecord.setNinthsalerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getNinthsalerid()));
				}
				else
				{
					curRecord.setNinthsalerinid("");
				}
				if(nBillType!=8 && nBillType!=9)
					strSql=strSql+",ninthsalerid='"+curRecord.getNinthsalerid()+"',ninthsalerinid='"+curRecord.getNinthsalerinid()+"',ninthsaleamt="+curRecord.getNinthsaleamt()+",ninthsalecashamt="+curRecord.getNinthsalecashamt()+" ";
			}
			
			if(curRecord.getEditType10()==true)
			{
				if(!curRecord.getTenthsalerid().equals(""))
				{
					curRecord.setTenthsalerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getTenthsalerid()));
				}
				else
				{
					curRecord.setTenthsalerinid("");
				}
				if(nBillType!=8 && nBillType!=9 )
				strSql=strSql+",tenthsalerid='"+curRecord.getTenthsalerid()+"',tenthsalerinid='"+curRecord.getTenthsalerinid()+"',tenthsaleamt="+curRecord.getTenthsaleamt()+",tenthsalecashamt="+curRecord.getTenthsalecashamt()+" ";
			}
			
			
				if(CommonTool.isEmpty(curRecord.getBeautyManager()))
				{
					curRecord.setBeautyManagerinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getBeautyManager()));
				}
				else
				{
					curRecord.setBeautyManagerinid("");
				}
				
				if(CommonTool.isEmpty(curRecord.getBeautyMangerNo()))
				{
					curRecord.setBeautyMangerNoInid(this.dataTool.loadEmpInidById(strCompId, curRecord.getBeautyMangerNo()));
				}
				else 
				{
					curRecord.setBeautyMangerNoInid("");
				}
				
				if(CommonTool.isEmpty(curRecord.getBeautyGw()))
				{
					curRecord.setBeautyGwInId(this.dataTool.loadEmpInidById(strCompId, curRecord.getBeautyGw()));
				}
				else 
				{
					curRecord.setBeautyGwInId("");
				}
				
				if(nBillType!=8 && nBillType!=9 && nBillType!=6 && nBillType!=7 && nBillType!=12)
				{
					strSql=strSql+",beautyManager='"+curRecord.getBeautyManager()+"',beautyManagerinid='"+curRecord.getBeautyManagerinid()+"',beautyPerf="+curRecord.getBeautyPerf()+" ";
					strSql=strSql+",beautymangerno='"+curRecord.getBeautyMangerNo()+"',beautymangerinid='"+curRecord.getBeautyMangerNoInid()+"' ";
					strSql=strSql+",beautygw='"+curRecord.getBeautyGw()+"',beaytygwinid='"+curRecord.getBeautyGwInId()+"' ";
				}
				
				if(nBillType==9)
				{
					strSql=strSql+",beautymangerno='"+curRecord.getBeautyMangerNo()+"',beautymangerinid='"+curRecord.getBeautyMangerNoInid()+"' ";
					strSql=strSql+",beautygw='"+curRecord.getBeautyGw()+"',beautygwinid='"+curRecord.getBeautyGwInId()+"' ";
				}
			
		
				if(CommonTool.isEmpty(curRecord.getConsultant()))
				{
					curRecord.setConsultantinid(this.dataTool.loadEmpInidById(strCompId, curRecord.getConsultant()));
				}
				else
				{
					curRecord.setConsultantinid("");
				}
				if(nBillType!=8 && nBillType!=9 && nBillType!=6 && nBillType!=7 && nBillType!=12)
					strSql=strSql+",consultant='"+curRecord.getConsultant()+"',consultantinid='"+curRecord.getConsultantinid()+"',consultantPerf="+curRecord.getConsultantPerf()+" ";
			
			
			
				if(CommonTool.isEmpty(curRecord.getConsultant1()))
				{
					curRecord.setConsultant1inid(this.dataTool.loadEmpInidById(strCompId, curRecord.getConsultant1()));
				}
				else
				{
					curRecord.setConsultant1inid("");
				}
				if(nBillType!=8 && nBillType!=9 && nBillType!=6 && nBillType!=7 && nBillType!=12)
				strSql=strSql+",consultant1='"+curRecord.getConsultant1()+"',consultant1inid='"+curRecord.getConsultant1inid()+"',consultant1Perf="+curRecord.getConsultant1Perf()+" ";
			
			
			if(nBillType==0)  //开卡
				strSql=strSql+" where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
			else if(nBillType==1)  //充值
				strSql=strSql+" where rechargecompid='"+strCompId+"' and rechargebillid='"+strBillId+"' ";
			else if(nBillType==2 || nBillType==3 ||nBillType==4 ||nBillType==5 ||nBillType==6 )  //转卡
				strSql=strSql+" where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' ";
			else if(nBillType==7)  //合作项目
				strSql=strSql+" where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
			else if(nBillType==8)  //条码卡
			{	
				if(lsDpayinfo!=null && lsDpayinfo.size()>0)
				{
					if(lsDpayinfo.size()==1)
					{
						strSql=strSql+" ,firstpaymode='"+lsDpayinfo.get(0).getPaymode()+"' ";
					}
					else if(lsDpayinfo.size()==2)
					{
						strSql=strSql+" ,firstpaymode='"+lsDpayinfo.get(0).getPaymode()+"',secondpaymode='"+lsDpayinfo.get(1).getPaymode()+"' ";
					}
				}
				strSql=strSql+" where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
			}
			else if(nBillType==9)  //疗程兑换
			{
				/*if(lsDpayinfo!=null && lsDpayinfo.size()>0)
				{
					strSql=strSql+" ,changepaymode='"+lsDpayinfo.get(0).getPaymode()+"' ";
				}*/
				strSql=strSql+" where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changeseqno="+changeseqno+" ";
			}
			else if(nBillType==12)
			{
				strSql=strSql+" where compid='"+strCompId+"' and billid='"+strBillId+"' and iteminid='"+curRecord.getItemInid()+"' ";
			}
			if(nBillType==0  )
			{
				strSql=strSql+" delete dpayinfo where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='SK' ";
			}
			else if(nBillType==1)
			{
				strSql=strSql+" delete dpayinfo where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='CZ' ";
			}
			else if( nBillType==2 || nBillType==3 ||nBillType==4 ||nBillType==5)
			{
				strSql=strSql+" delete dpayinfo where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='ZK' ";
			}
			else if( nBillType==6 )
			{
				strSql=strSql+" delete dpayinfo where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='TK' ";
			}
			this.amn_Dao.executeSql(strSql);
			if(nBillType==0 || nBillType==1 || nBillType==2 || nBillType==3 ||nBillType==4 ||nBillType==5 ||nBillType==6 || nBillType==7 )
			{
				if(lsDpayinfo!=null && lsDpayinfo.size()>0)
				{
					this.amn_Dao.saveOrUpdateAll(lsDpayinfo);
				}
			}
			
			strSql=" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
	 		" values( '"+CommonTool.getLoginInfo("USERID")+"','AC008','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','','卡异动业务单据修改')";
	 
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	
	public boolean postServiceInfo(String strCompId,String strBillId,List<Dconsumeinfo> lsDconsumeinfos,List<Dpayinfo> lsDpayinfo,String recommendempid,String recommendempinid,String managerz,String managerzinid,String managerf,String managerfinid)
	{
		try
		{
			if(lsDconsumeinfos==null || lsDconsumeinfos.size()==0
			 || lsDpayinfo==null || lsDpayinfo.size()==0)
			{
				return false;
			}
			String strSql=" insert dconsumeinfolog(cscompid,csbillid,csinfotype,csitemno,csitemcount,csdisprice,csitemamt,cspaymode," +
					" csfirstsaler,csfirsttype,csfirstinid,csfirstshare,cssecondsaler,cssecondtype,cssecondinid,cssecondshare," +
					" csthirdsaler,csthirdtype,csthirdinid,csthirdshare,optionuserno,optiondate,optiontime) " +
					" select cscompid,csbillid,csinfotype,csitemno,csitemcount,csdisprice,csitemamt,cspaymode," +
					" csfirstsaler,csfirsttype,csfirstinid,csfirstshare,cssecondsaler,cssecondtype,cssecondinid,cssecondshare," +
					" csthirdsaler,csthirdtype,csthirdinid,csthirdshare,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"' " +
					" from dconsumeinfo where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' ";
			strSql=strSql+" update mconsumeinfo set recommendempid='"+recommendempid+"' , recommendempinid='"+recommendempinid+"' where cscompid='"+strCompId+"' and csbillid='"+strBillId+"'  ";
			String csfirstinid="";
			String cssecondinid="";
			String csthirdinid="";
			String cshairinid="";
			for(int i=0;i<lsDconsumeinfos.size();i++)
			{
				 csfirstinid="";
				 cssecondinid="";
				 csthirdinid="";
				 cshairinid="";
				if(!CommonTool.FormatString(lsDconsumeinfos.get(i).getCsfirstsaler()).equals(""))
				{
					csfirstinid=this.getDataTool().loadEmpInidById(strCompId, CommonTool.FormatString(lsDconsumeinfos.get(i).getCsfirstsaler()));
				}
				if(!CommonTool.FormatString(lsDconsumeinfos.get(i).getCssecondsaler()).equals(""))
				{
					cssecondinid=this.getDataTool().loadEmpInidById(strCompId, CommonTool.FormatString(lsDconsumeinfos.get(i).getCssecondsaler()));
				}
				if(!CommonTool.FormatString(lsDconsumeinfos.get(i).getCsthirdsaler()).equals(""))
				{
					csthirdinid=this.getDataTool().loadEmpInidById(strCompId, CommonTool.FormatString(lsDconsumeinfos.get(i).getCsthirdsaler()));
				}
				if(!CommonTool.FormatString(lsDconsumeinfos.get(i).getHairRecommendEmpId()).equals(""))
				{
					cshairinid=this.getDataTool().loadEmpInidById(strCompId, CommonTool.FormatString(lsDconsumeinfos.get(i).getHairRecommendEmpId()));
				}
				strSql=strSql+" update dconsumeinfo set cspaymode='"+lsDconsumeinfos.get(i).getCspaymode()+"'," +
						    " csfirstsaler='"+lsDconsumeinfos.get(i).getCsfirstsaler()+"',csfirstinid='"+csfirstinid+"',csfirsttype='"+CommonTool.FormatString(lsDconsumeinfos.get(i).getCsfirsttype())+"',csfirstshare="+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCsfirstshare())+", " +
							" cssecondsaler='"+lsDconsumeinfos.get(i).getCssecondsaler()+"',cssecondinid='"+cssecondinid+"',cssecondtype='"+CommonTool.FormatString(lsDconsumeinfos.get(i).getCssecondtype())+"',cssecondshare="+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCssecondshare())+"," +
							" csthirdsaler='"+lsDconsumeinfos.get(i).getCsthirdsaler()+"',csthirdinid='"+csthirdinid+"',csthirdtype='"+CommonTool.FormatString(lsDconsumeinfos.get(i).getCsthirdtype())+"',csthirdshare="+CommonTool.FormatBigDecimal(lsDconsumeinfos.get(i).getCsthirdshare())+", " +
							" beautyMangerNo='"+managerz+"',beautyGw='"+managerf+"',beautyMangerNoinid='"+managerzinid+"',beautyGwinid='"+managerfinid+"', "+
							" hairRecommendEmpId='"+CommonTool.FormatString(lsDconsumeinfos.get(i).getHairRecommendEmpId())+"',hairRecommendEmpInid='"+cshairinid+"' "+
							
							"  where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and csinfotype="+lsDconsumeinfos.get(i).getId().getCsinfotype()+" and csseqno="+lsDconsumeinfos.get(i).getId().getCsseqno()+" " ;
			}
			strSql=strSql+" delete dpayinfo where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='SY'";
			for(int i=0;i<lsDpayinfo.size();i++)
			{
				strSql=strSql+" insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt)" +
						" values('"+strCompId+"','"+strBillId+"','SY',"+i+",'"+lsDpayinfo.get(i).getPaymode()+"',"+lsDpayinfo.get(i).getPayamt()+") ";
			}
			strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
	 		" values( '"+CommonTool.getLoginInfo("USERID")+"','AC008','M','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','','业务单据修改')";
	 
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean postBackServiceInfo(String strCompId,String strBillId,String strNewBillId, String outTradeNo)
	{
		try
		{
			String scantradeno = StringUtils.isNotBlank(outTradeNo)?("'"+outTradeNo+"'"):"NULL";
			String strSql="insert mconsumeinfo(cscompid,csbillid,csmanualno,csdate,csstarttime,csendtime,cscardno,csname,cscardtype,csersex,csertype," +
					" csercount,csopationerid,csopationdate,financedate,backcsflag,backcsbillid,tuangoucardno,tiaomacardno,diyongcardno,cardphone,scanpaytype,scantradeno)";
			strSql=strSql+" select cscompid,'"+strNewBillId+"',csmanualno,csdate,csstarttime,csendtime,cscardno,csname,cscardtype,csersex,csertype," +
					" csercount,csopationerid,csopationdate,'"+CommonTool.getCurrDate()+"',1,backcsbillid,tuangoucardno,tiaomacardno,diyongcardno,cardphone,scanpaytype,"+ scantradeno +
					" from mconsumeinfo where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' ";
			strSql=strSql+" insert dconsumeinfo(cscompid,csbillid,csinfotype,csseqno,csitemno,csitemunit,csitemcount,csunitprice,csdiscount,csdisprice,csitemamt,cspaymode," +
					" csfirstsaler,csfirsttype,csfirstinid,cssecondsaler,cssecondtype,cssecondinid,csthirdsaler,csthirdtype,csthirdinid,csortherpayid,csproseqno,csfirstshare,cssecondshare,csthirdshare," +
					" csitemstate,costpricetype,hairRecommendEmpId,hairRecommendEmpInid,yearinid,beautyMangerNo,beautyGw,beautyMangerNoinid,beautyGwinid,scanpaytype,scanpayamt) ";
			strSql=strSql+" select cscompid,'"+strNewBillId+"',csinfotype,csseqno,csitemno,csitemunit,isnull(csitemcount,1)*(-1),csunitprice,csdiscount,csdisprice,isnull(csitemamt,0)*(-1),cspaymode," +
					" csfirstsaler,csfirsttype,csfirstinid,cssecondsaler,cssecondtype,cssecondinid,csthirdsaler,csthirdtype,csthirdinid,csortherpayid,csproseqno,isnull(csfirstshare,0)*(-1),isnull(cssecondshare,0)*(-1)," +
					" isnull(csthirdshare,0)*(-1),csitemstate,costpricetype,hairRecommendEmpId,hairRecommendEmpInid,yearinid,beautyMangerNo,beautyGw,beautyMangerNoinid,beautyGwinid,scanpaytype,isnull(scanpayamt,0)*(-1)" +
					" from dconsumeinfo where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' ";
			strSql=strSql+" insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt,payremark) ";
			strSql=strSql+" select paycompid,'"+strNewBillId+"',paybilltype,payseqno,paymode,isnull(payamt,0)*(-1),payremark" +
					" from dpayinfo where paycompid='"+strCompId+"' and  paybillid='"+strBillId+"' and paybilltype='SY' ";
			strSql=strSql+" update mconsumeinfo set backcsflag=1,backcsbillid='"+strNewBillId+"' where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' ";
			strSql=strSql+" update nointernalcardinfo set cardstate=2 where billid='"+strBillId+"' and cardvesting='"+strCompId+"'";
			strSql=strSql+" delete dnointernalcardinfo where billid='"+strBillId+"' and cardvesting='"+strCompId+"' ";
			
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean afterPost(String strCompId,String strBillId,String strDate,int costType,String strOldBillId)
	{
		try
		{
			String strSql=" exec upg_handconsumbill_card_back '"+strCompId+"','"+strBillId+"','"+strDate+"',"+costType+",'"+strOldBillId+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	

	
	public List<Dconsumeinfo> loadDconsumeinfoByBillId(String strCompId,String strBillId)
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
			 String strSql=" select csseqno,csinfotype,csitemno,csitemname=prjname,csitemunit,csitemcount,csunitprice,csdiscount,csdisprice,csitemamt,cspaymode," +
			 		" csfirstsaler,csfirsttype,csfirstshare,cssecondsaler,cssecondtype,cssecondshare,csthirdsaler,csthirdtype,csthirdshare,csortherpayid " +
			 		" From  dconsumeinfo,projectinfo where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(csinfotype,0)=1 " +
			 		" and prjmodeId  in ('"+strModeId_p+"','"+strFristModeId_p+"','"+strSecondModeId_p+"','"+strThirthModeId_p+"') and prjno=csitemno  ";
			 
			
			 String strModeId_g=this.dataTool.loadSysParam(strCompId,"SP061");
		     String strFristModeId_g="";//第一级模板Id
			 String strSecondModeId_g="";//第2级模板ID
			 String strThirthModeId_g="";//第三级模板Id
			
		     if(compLvl==2)
			 {
				strFristModeId_g=this.dataTool.loadparentModeId(strModeId_g);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId_g=this.dataTool.loadparentModeId(strModeId_g);
				if(!strSecondModeId_g.equals(""))
					strFristModeId_g=this.dataTool.loadparentModeId(strSecondModeId_g);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId_g=this.dataTool.loadparentModeId(strModeId_g);
				if(!strThirthModeId_g.equals(""))
					strSecondModeId_g=this.dataTool.loadparentModeId(strThirthModeId_g);
				if(!strSecondModeId_g.equals(""))
					strFristModeId_g=this.dataTool.loadparentModeId(strSecondModeId_g);
			 }
			 strSql=strSql+"union select csseqno,csinfotype,csitemno,csitemname=goodsname,csitemunit,csitemcount,csunitprice,csdiscount,csdisprice,csitemamt,cspaymode," +
		 		" csfirstsaler,csfirsttype,csfirstshare,cssecondsaler,cssecondtype,cssecondshare,csthirdsaler,csthirdtype,csthirdshare,csortherpayid " +
		 		" From  dconsumeinfo,goodsinfo where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(csinfotype,0)=2 " +
		 		" and goodsmodeid  in ('"+strModeId_g+"','"+strFristModeId_g+"','"+strSecondModeId_g+"','"+strThirthModeId_g+"') and goodsno=csitemno  ";
		 
			 	AnlyResultSet<List<Dconsumeinfo>> analysis = new AnlyResultSet<List<Dconsumeinfo>>()
				{
					public List<Dconsumeinfo> anlyResultSet(ResultSet rs)
					{
						List<Dconsumeinfo> returnValue = new ArrayList();
						Dconsumeinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dconsumeinfo();
								record.setBcsseqno(CommonTool.FormatDouble(rs.getDouble("csseqno")));
								record.setBcsinfotype(CommonTool.FormatInteger(rs.getInt("csinfotype")));
								record.setCsitemno(CommonTool.FormatString(rs.getString("csitemno")));
								record.setCsitemname(CommonTool.FormatString(rs.getString("csitemname")));
								record.setCsitemunit(CommonTool.FormatString(rs.getString("csitemunit")));
								record.setCsitemcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemcount"))));
								record.setCsunitprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csunitprice"))));
								record.setCsdiscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csdiscount"))));
								record.setCsdisprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csdisprice"))));
								record.setCsitemamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemamt"))));
								record.setCspaymode(CommonTool.FormatString(rs.getString("cspaymode")));
								record.setCsfirstsaler(CommonTool.FormatString(rs.getString("csfirstsaler")));
								record.setCsfirsttype(CommonTool.FormatString(rs.getString("csfirsttype")));
								record.setCsfirstshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csfirstshare"))));
								record.setCssecondsaler(CommonTool.FormatString(rs.getString("cssecondsaler")));
								record.setCssecondtype(CommonTool.FormatString(rs.getString("cssecondtype")));
								record.setCssecondshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("cssecondshare"))));
								record.setCsthirdsaler(CommonTool.FormatString(rs.getString("csthirdsaler")));
								record.setCsthirdtype(CommonTool.FormatString(rs.getString("csthirdtype")));
								record.setCsthirdshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csthirdshare"))));
								record.setCsortherpayid(CommonTool.FormatString(rs.getString("csortherpayid")));
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
				List<Dconsumeinfo> lsInfo=  (List<Dconsumeinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				
				return lsInfo;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public Mconsumeinfo loadMconsumeinfoByBill(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" select mconsumeinfo From Mconsumeinfo mconsumeinfo,Compchaininfo compchaininfo " +
					"  where cscompid=relationcomp and curcomp='"+strCompId+"' and csbillid='"+strBillId+"' ";
			List<Mconsumeinfo> lsInfo=this.amn_Dao.findByHql(strSql);
			if(lsInfo!=null && lsInfo.size()>0)
			{
				return lsInfo.get(0);
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public List<Dconsumeinfo> loadDconsumeinfoByBillId_prj(String strCompId,String strBillId)
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
			 String strSql=" select csseqno,csinfotype,csitemno,csitemname=prjname,csitemunit,csitemcount,csunitprice,csdiscount,csdisprice,csitemamt,cspaymode," +
			 		" csfirstsaler,csfirsttype,csfirstshare,cssecondsaler,cssecondtype,cssecondshare,csthirdsaler,csthirdtype,csthirdshare,csortherpayid,hairRecommendEmpId,costpricetype,beautyMangerNo,beautyGw,beautyMangerNoinid,beautyGwinid" +
			 		" From  dconsumeinfo,projectinfo where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(csinfotype,0)=1 " +
			 		" and prjmodeId  in ('"+strModeId_p+"','"+strFristModeId_p+"','"+strSecondModeId_p+"','"+strThirthModeId_p+"') and prjno=csitemno  ";
			 
			
			 	AnlyResultSet<List<Dconsumeinfo>> analysis = new AnlyResultSet<List<Dconsumeinfo>>()
				{
					public List<Dconsumeinfo> anlyResultSet(ResultSet rs)
					{
						List<Dconsumeinfo> returnValue = new ArrayList();
						Dconsumeinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dconsumeinfo();
								record.setBcsseqno(CommonTool.FormatDouble(rs.getDouble("csseqno")));
								record.setBcsinfotype(CommonTool.FormatInteger(rs.getInt("csinfotype")));
								record.setCsitemno(CommonTool.FormatString(rs.getString("csitemno")));
								record.setCsitemname(CommonTool.FormatString(rs.getString("csitemname")));
								record.setCsitemunit(CommonTool.FormatString(rs.getString("csitemunit")));
								record.setCsitemcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemcount"))));
								record.setCsunitprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csunitprice"))));
								record.setCsdiscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csdiscount"))));
								record.setCsdisprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csdisprice"))));
								record.setCsitemamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemamt"))));
								record.setCspaymode(CommonTool.FormatString(rs.getString("cspaymode")));
								record.setCsfirstsaler(CommonTool.FormatString(rs.getString("csfirstsaler")));
								record.setCsfirsttype(CommonTool.FormatString(rs.getString("csfirsttype")));
								record.setCsfirstshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csfirstshare"))));
								record.setCssecondsaler(CommonTool.FormatString(rs.getString("cssecondsaler")));
								record.setCssecondtype(CommonTool.FormatString(rs.getString("cssecondtype")));
								record.setCssecondshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("cssecondshare"))));
								record.setCsthirdsaler(CommonTool.FormatString(rs.getString("csthirdsaler")));
								record.setCsthirdtype(CommonTool.FormatString(rs.getString("csthirdtype")));
								record.setCsthirdshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csthirdshare"))));
								record.setCsortherpayid(CommonTool.FormatString(rs.getString("csortherpayid")));
								record.setHairRecommendEmpId(rs.getString("hairRecommendEmpId"));
								record.setCostpricetype(rs.getInt("costpricetype"));
								record.setBeautyMangerNo(rs.getString("beautyMangerNo"));
								record.setBeautyGw(rs.getString("beautyGw"));
								record.setBeautyMangerNoinid(rs.getString("beautyMangerNoinid"));
								record.setBeautyGwinid(rs.getString("beautyGwinid"));
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
				List<Dconsumeinfo> lsInfo=  (List<Dconsumeinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				
				return lsInfo;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Dconsumeinfo> loadDconsumeinfoByBillId_Goods(String strCompId,String strBillId)
	{
		try
		{
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.dataTool.loadCompLvl(strCompId);
		   
			
			 String strModeId_g=this.dataTool.loadSysParam(strCompId,"SP061");
		     String strFristModeId_g="";//第一级模板Id
			 String strSecondModeId_g="";//第2级模板ID
			 String strThirthModeId_g="";//第三级模板Id
			
		     if(compLvl==2)
			 {
				strFristModeId_g=this.dataTool.loadparentModeId(strModeId_g);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId_g=this.dataTool.loadparentModeId(strModeId_g);
				if(!strSecondModeId_g.equals(""))
					strFristModeId_g=this.dataTool.loadparentModeId(strSecondModeId_g);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId_g=this.dataTool.loadparentModeId(strModeId_g);
				if(!strThirthModeId_g.equals(""))
					strSecondModeId_g=this.dataTool.loadparentModeId(strThirthModeId_g);
				if(!strSecondModeId_g.equals(""))
					strFristModeId_g=this.dataTool.loadparentModeId(strSecondModeId_g);
			 }
			 String strSql=" select csseqno,csinfotype,csitemno,csitemname=goodsname,csitemunit,csitemcount,csunitprice,csdiscount,csdisprice,csitemamt,cspaymode," +
		 		" csfirstsaler,csfirsttype,csfirstshare,cssecondsaler,cssecondtype,cssecondshare,csthirdsaler,csthirdtype,csthirdshare,csortherpayid,beautyMangerNo,beautyGw,beautyMangerNoinid,beautyGwinid " +
		 		" From  dconsumeinfo,goodsinfo where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(csinfotype,0)=2 " +
		 		" and goodsmodeid  in ('"+strModeId_g+"','"+strFristModeId_g+"','"+strSecondModeId_g+"','"+strThirthModeId_g+"') and goodsno=csitemno  ";
		 
			 	AnlyResultSet<List<Dconsumeinfo>> analysis = new AnlyResultSet<List<Dconsumeinfo>>()
				{
					public List<Dconsumeinfo> anlyResultSet(ResultSet rs)
					{
						List<Dconsumeinfo> returnValue = new ArrayList();
						Dconsumeinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dconsumeinfo();
								record.setBcsseqno(CommonTool.FormatDouble(rs.getDouble("csseqno")));
								record.setBcsinfotype(CommonTool.FormatInteger(rs.getInt("csinfotype")));
								record.setCsitemno(CommonTool.FormatString(rs.getString("csitemno")));
								record.setCsitemname(CommonTool.FormatString(rs.getString("csitemname")));
								record.setCsitemunit(CommonTool.FormatString(rs.getString("csitemunit")));
								record.setCsitemcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemcount"))));
								record.setCsunitprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csunitprice"))));
								record.setCsdiscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csdiscount"))));
								record.setCsdisprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csdisprice"))));
								record.setCsitemamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemamt"))));
								record.setCspaymode(CommonTool.FormatString(rs.getString("cspaymode")));
								record.setCsfirstsaler(CommonTool.FormatString(rs.getString("csfirstsaler")));
								record.setCsfirsttype(CommonTool.FormatString(rs.getString("csfirsttype")));
								record.setCsfirstshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csfirstshare"))));
								record.setCssecondsaler(CommonTool.FormatString(rs.getString("cssecondsaler")));
								record.setCssecondtype(CommonTool.FormatString(rs.getString("cssecondtype")));
								record.setCssecondshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("cssecondshare"))));
								record.setCsthirdsaler(CommonTool.FormatString(rs.getString("csthirdsaler")));
								record.setCsthirdtype(CommonTool.FormatString(rs.getString("csthirdtype")));
								record.setCsthirdshare(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csthirdshare"))));
								record.setCsortherpayid(CommonTool.FormatString(rs.getString("csortherpayid")));
								record.setBeautyMangerNo(rs.getString("beautyMangerNo"));
								record.setBeautyGw(rs.getString("beautyGw"));
								record.setBeautyMangerNoinid(rs.getString("beautyMangerNoinid"));
								record.setBeautyGwinid(rs.getString("beautyGwinid"));
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
				List<Dconsumeinfo> lsInfo=  (List<Dconsumeinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				
				return lsInfo;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	//反冲单据
	//nBillType   0 售卡  1 充值还款 2 转卡 5 并卡 6退卡 7 合作项目 8 条码卡 9兑换
	public boolean postBackCardSaleInfo(String strCompId,String strBillId,String strCardNo,int nBillType,double changeseqno,StringBuffer strMsgBuf,String iteminid)
	{
		try
		{
			String strSql="";
			strMsgBuf.append("");
			BigDecimal curkeepamt=loadCurkeepAmt(strCardNo,2);
			BigDecimal curzsamt=loadCurkeepAmt(strCardNo,6);
			String strNewbackBill="";
			
			if(nBillType==0)  //开卡反冲
			{
				boolean flag=this.validateMsaleCardSale( strCompId, strBillId, curkeepamt);
				if(flag==false)
				{
					strMsgBuf.append("卡类余额与开卡额度不一致,不可反冲");
					return false;
				}
				else
				{
					
					strNewbackBill=this.getDataTool().loadBillIdByRule(strCompId,"msalecardinfo", "salebillid", "SP008");
					strSql=" insert msalecardinfo(salecompid,salebillid,saledate,saletime,salecardno,salecardtype,membername,memberphone,membersex,memberpcid,memberbirthday, "+
		                   "  salekeepamt,saledebtamt,saletotalamt,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt, "+
		                   "  thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt, "+
		                   "  sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt, "+
		                   "  ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt," +
		                   "  firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt, "+
		                   "  financedate,saleroperator,saleroperdate,cardappbillid,corpscardno,backflag,billinsertype,sendamtflag,beautyManager,consultant,beautyPerf,consultantPerf,beautyManagerinid,consultantinid,isxnj," +
		                   "  beautymangerno,beautymangerinid,beautygw,beaytygwinid)";
					strSql=strSql+ " select salecompid,'"+strNewbackBill+"',saledate,saletime,salecardno,salecardtype,membername,memberphone,membersex,memberpcid,memberbirthday, "+
		                   "  isnull(salekeepamt,0)*(-1),isnull(saledebtamt,0)*(-1),isnull(saletotalamt,0)*(-1),firstsalerid,firstsalerinid,isnull(firstsaleamt,0)*(-1),secondsalerid,secondsalerinid,isnull(secondsaleamt,0)*(-1), "+
		                   "  thirdsalerid,thirdsalerinid,isnull(thirdsaleamt,0)*(-1),fourthsalerid,fourthsalerinid,isnull(fourthsaleamt,0)*(-1),fifthsalerid,fifthsalerinid,isnull(fifthsaleamt,0)*(-1), "+
		                   "  sixthsalerid,sixthsalerinid,isnull(sixthsaleamt,0)*(-1),seventhsalerid,seventhsalerinid,isnull(seventhsaleamt,0)*(-1),eighthsalerid,eighthsalerinid,isnull(eighthsaleamt,0)*(-1), "+
		                   "  ninthsalerid,ninthsalerinid,isnull(ninthsaleamt,0)*(-1),tenthsalerid,tenthsalerinid,isnull(tenthsaleamt,0)*(-1)," +
		                   "  isnull(firstsalecashamt,0)*(-1),isnull(secondsalecashamt,0)*(-1),isnull(thirdsalecashamt,0)*(-1),isnull(fourthsalecashamt,0)*(-1),isnull(fifthsalecashamt,0)*(-1),isnull(sixthsalecashamt,0)*(-1),isnull(seventhsalecashamt,0)*(-1),isnull(eighthsalecashamt,0)*(-1),isnull(ninthsalecashamt,0)*(-1),isnull(tenthsalecashamt,0)*(-1) , "+
		                   "  '"+CommonTool.getCurrDate()+"','"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"',cardappbillid,corpscardno,1,billinsertype,sendamtflag,beautyManager,consultant,isnull(beautyPerf,0)*(-1),isnull(consultantPerf,0)*(-1),beautyManagerinid,consultantinid,isxnj," +
		                   "  beautymangerno,beautymangerinid,beautygw,beaytygwinid" +
		                   "  from msalecardinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
					strSql=strSql+" insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt)" +
							" select paycompid,'"+strNewbackBill+"',paybilltype,payseqno,paymode,isnull(payamt,0)*(-1) from dpayinfo where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='SK' ";
					
					strSql=strSql+" update msalecardinfo set backflag=1 where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' "; 
					strSql=strSql+" update cardinfo set salecarddate='',cardstate=1 where cardno='"+strCardNo+"' ";
					strSql=strSql+" delete cardsoninfo where cardno='"+strCardNo+"' ";
					strSql=strSql+" delete memberinfo where memberno='"+strCardNo+"' ";
					strSql=strSql+" update cardaccount set accountbalance=0 where cardno='"+strCardNo+"' ";
					strSql=strSql+" delete cardproaccount where cardno='"+strCardNo+"' ";
					strSql=strSql+" delete cardchangehistory where changecardno='"+strCardNo+"' ";
					strSql=strSql+" delete cardaccountchangehistory where changecardno='"+strCardNo+"' ";
					strSql=strSql+" delete nointernalcardinfo where billid='"+strBillId+"' and billtype='0'";
					strSql=strSql+" delete cardtransactionhistory where transactioncardno='"+strCardNo+"' ";
					//strSql=strSql+" update cardproaccount set salecount=0,costcount=0,lastcount=0,saleamt=0,costamt=0,lastamt=0 where createbillno='"+strBillId+"' and createbilltype='"+strBillId+"' and proremark='0' ";
					strSql=strSql+" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
				 		" values( '"+CommonTool.getLoginInfo("USERID")+"','AC008','D','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','"+strCardNo+"','开卡单反冲')";
					return this.amn_Dao.executeSql(strSql);
				}
			}
			else if(nBillType==1)  //充值还款反冲
			{
				boolean flag=this.validateMrechareCardSale( strCompId, strBillId, curkeepamt);
				if(flag==false)
				{
					strMsgBuf.append("卡内余额与充值额度不一致,不可反充");
					return false;
				}
				else
				{
					 strNewbackBill=this.getDataTool().loadBillIdByRule(strCompId,"mcardrechargeinfo", "rechargebillid", "SP009");
					 strSql=" exec upg_handrechargecardbill_card_back_createNewBill '"+strCompId+"','"+strBillId+"','"+strNewbackBill+"' ";
					  this.amn_Dao.executeSql(strSql);
					 strSql=" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
				 		" values( '"+CommonTool.getLoginInfo("USERID")+"','AC008','D','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','"+strCardNo+"','充值单反冲')";
					 return this.amn_Dao.executeSql(strSql);
				}
			}
			else if(nBillType==2 || nBillType==3  || nBillType==4 )  //转卡反冲
			{
				boolean flag=this.validateChangeCardSale( strCompId, strBillId, curkeepamt);
				if(flag==false)
				{
					strMsgBuf.append("卡内余额与转卡额度不一致,不可反充");
					return false;
				}
				else
				{
					 strNewbackBill=this.getDataTool().loadBillIdByRule(strCompId,"mcardchangeinfo", "changebillid", "SP010");
					 strSql=" exec upg_handcardchangebill_back_createNewBill '"+strCompId+"','"+strBillId+"','"+strNewbackBill+"' ";
					 this.amn_Dao.executeSql(strSql);
					  strSql=" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
				 		" values( '"+CommonTool.getLoginInfo("USERID")+"','AC008','D','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','"+strCardNo+"','转卡单反冲')";
					 return this.amn_Dao.executeSql(strSql);
				}
			}
			else if(nBillType==8)  //条码卡反冲
			{
				boolean completeFlag=this.validateCompleteTMCard(strCompId,strBillId);
				if(completeFlag==false)
				{
					strMsgBuf.append("条码卡内疗程不完整,若已消费过此项目请先返销该条码卡的消费单.");
					return false;
				}
				strSql=" update msalebarcodecardinfo set salebakflag=1 where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
				String newbillid=this.dataTool.loadBillIdByRule(strCompId,"msalebarcodecardinfo", "salebillid", "SP008");
				strSql=strSql+" insert msalebarcodecardinfo(salecompid,salebillid,saledate,saletime,operationer,barcodecardno,firstpaymode,firstpayamt,secondpaymode,secondpayamt," +
						" saleamt,firstsaleempid,firstsaleempinid,firstsaleamt,secondsaleempid,secondsaleempinid,secondsaleamt,thirdsaleempid,thirdsaleempinid,thirdsaleamt,salebakflag,usecardno,usecardtype,usecardpayamt,ishz)  " +
						" select salecompid,'"+newbillid+"',saledate,saletime,operationer,barcodecardno,firstpaymode,isnull(firstpayamt,0)*(-1),secondpaymode,isnull(secondpayamt,0)*(-1)," +
						" isnull(saleamt,0)*(-1),firstsaleempid,firstsaleempinid,isnull(firstsaleamt,0)*(-1),secondsaleempid,secondsaleempinid,isnull(secondsaleamt,0)*(-1),thirdsaleempid,thirdsaleempinid " +
						" ,isnull(thirdsaleamt,0)*(-1),1,usecardno,usecardtype,isnull(usecardpayamt,0)*(-1),ishz from msalebarcodecardinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
				strSql=strSql+" update nointernalcardinfo set cardstate=0  from nointernalcardinfo,msalebarcodecardinfo" +
						" where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and cardno=barcodecardno and cardtype=2";
				strSql=strSql+" delete dnointernalcardinfo from dnointernalcardinfo,msalebarcodecardinfo" +
						" 	where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and cardno=barcodecardno ";
				
				strSql=strSql+"  declare @usecardno varchar(20) ,@usecardpayamt float ";
				strSql=strSql+"  select @usecardno=usecardno,@usecardpayamt=isnull(usecardpayamt,0)*(-1) from msalebarcodecardinfo where  salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
				strSql=strSql+"  if( isnull(@usecardno,'')<>'' and isnull(@usecardpayamt,0)<>0 ) begin ";
				strSql=strSql+"  declare @costaccountseqno float";     
				strSql=strSql+"  select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@usecardno ";
				strSql=strSql+"  declare @costaccount2lastamt float ";  
				strSql=strSql+"   select top 1 @costaccount2lastamt=(case when changetype in (0,6,7,8,9,10) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@usecardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc  ";         
				strSql=strSql+"   if(ISNULL(@costaccount2lastamt,0)=0) ";         
				strSql=strSql+"   select @costaccount2lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@usecardno and accounttype=2  ";        
				strSql=strSql+"   insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  ";        
			    strSql=strSql+"   values('"+strCompId+"',@usecardno,2,ISNULL(@costaccountseqno,0),2,@usecardpayamt,'SK','"+strBillId+"','"+CommonTool.getCurrDate()+"',@costaccount2lastamt) ";         
				strSql=strSql+"   update cardaccount set accountbalance=isnull(accountbalance,0)-isnull(@usecardpayamt,0) where cardno=@usecardno and accounttype='2'  end ";
				
				return this.amn_Dao.executeSql(strSql);
			}
			else if(nBillType==9)  //疗程兑换
			{
				int flag=this.validateChangeProType(strCompId, strBillId, changeseqno);
				if(flag==1) //完整兑换
				{
					 String strNewBillId=this.dataTool.loadBillIdByRule(strCompId,"mproexchangeinfo", "changebillid", "SP008");
					 strSql=" exec upg_handaccountChangebill_card_back '"+strCompId+"','"+strBillId+"',"+changeseqno+",'"+CommonTool.getCurrDate()+"','"+strNewBillId+"' ";
					 this.amn_Dao.executeSql(strSql);
					  strSql=" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
				 		" values( '"+CommonTool.getLoginInfo("USERID")+"','AC008','D','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','"+strBillId+"','"+strCardNo+"','疗程兑换单反冲')";
					 return this.amn_Dao.executeSql(strSql);
				}
				else
				{
					strMsgBuf.append("卡内疗程不完整,若已消费过此疗程请先返销该卡疗程的消费单.");
					return false;
				}
			}
			else if(nBillType==12)
			{
				if(checkYearXF(strCompId,strBillId))
				{
					String strNewBillId=this.dataTool.loadBillIdByRule(strCompId,"yearsellinfo", "billid", "SP008");
					strSql=" insert into yearsellinfo(compid,billid,cardno,phone,name,cardtype,cid,idtphone,userid,accountdate,cashpaycode,cashamt,storedpay,storedamt,backbillid,backcsflag,zsamt) " +
						   " select compid,'"+strNewBillId+"',cardno,phone,name,cardtype,cid,idtphone,userid,'"+CommonTool.getCurrDate()+"',cashpaycode,0-isnull(cashamt,0),storedpay,0-isnull(storedamt,0),'"+strBillId+"',1,0-isnull(zsamt,0) " +
						   	" from yearsellinfo where compid='"+strCompId+"' and billid='"+strBillId+"' ";
					strSql+=" insert into yearselldetal(compid,billid,seq,packno,itemno,iteminid,num,amt,firstempno,firstinid,sendempno,sendinid,threeempno,threeinid,fourempno,fourinid,firstperf,sendperf,threeperf,fourperf,probz)" +
							" select compid,'"+strNewBillId+"',seq,packno,itemno,iteminid,0-isnull(num,0),0-isnull(amt,0),firstempno,firstinid,sendempno,sendinid,threeempno,threeinid,fourempno,fourinid,0-isnull(firstperf,0),0-isnull(sendperf,0),0-isnull(threeperf,0),0-isnull(fourperf,0),probz " +
							" from yearselldetal where compid='"+strCompId+"' and billid='"+strBillId+"' ";
					
					strSql+=" update a set num=0,amt=0,synum=0,syamt=0 from yearcarddetal a,(select iteminid from yearselldetal where billid='"+strBillId+"' and compid='"+strCompId+"') b where a.iteminid=b.iteminid";
					
					strSql+=" update cardaccount set accountbalance=(isnull(accountbalance,0)+(select storedamt from yearsellinfo where compid='"+strCompId+"' and billid='"+strBillId+"')) where cardno='"+strCardNo+"' and accounttype=2  ";
					
					strSql+=" update cardaccount set accountbalance=(isnull(accountbalance,0)+(select zsamt from yearsellinfo where compid='"+strCompId+"' and billid='"+strBillId+"')) where cardno='"+strCardNo+"' and accounttype=6  ";
					
					strSql+=" update yearsellinfo set backcsflag=1 where compid='"+strCompId+"' and billid='"+strBillId+"' ";
					
					strSql+=" update a set cardstate=1 " +
							" from  nointernalcardinfo a,(select cashdyq from yearselldetal where compid='"+strCompId+"' and billid='"+strBillId+"' ) b " +
							" where a.cardno=b.cashdyq ";
					
					double seq=loadSeq(strCardNo);
					
					strSql+=" insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt) " +
							" select compid,cardno,2,"+seq+",0,storedamt,'NK','"+strNewBillId+"','"+CommonTool.getCurrDate()+"',"+curkeepamt+" " +
							" from yearsellinfo where compid='"+strCompId+"' and billid='"+strBillId+"' ";
					
					strSql+=" insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt) " +
							" select compid,cardno,6,"+seq+1+",0,zsamt,'NK','"+strNewBillId+"','"+CommonTool.getCurrDate()+"',"+curzsamt+" " +
							" from yearsellinfo where compid='"+strCompId+"' and billid='"+strBillId+"' ";
					
					return amn_Dao.executeSql(strSql);
					//buffer.append("  ");
					//buffer.append(" values('"+yearsellinfo.getId().getCompid()+"','"+yearsellinfo.getCardno()+"',2,"+seq+",2,"+yearsellinfo.getStoredamt()+",'NK','"+yearsellinfo.getId().getBillid()+"','"+yearsellinfo.getAccountdate()+"',"+amt+") ");
					//buffer.append(" update cardaccount set accountbalance=accountbalance-"+CommonTool.FormatDouble(yearsellinfo.getStoredamt())+" where cardno='"+yearsellinfo.getCardno()+"' and accounttype=2 ");
					
					
				}
				else {
					strMsgBuf.append("该疗程已经消费过，请先返销消费单");
				}
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
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
	
	public boolean checkYearXF(String strCompid,String strBillId)
	{
		String strSql="";
		ResultSet rs=null;
		double xCount=0;
		try {
			List<Yearselldetal> lsYearselldetals=amn_Dao.findByHql("From Yearselldetal where compid='"+strCompid+"' and billid='"+strBillId+"'");
			if(lsYearselldetals!=null && lsYearselldetals.size()>0)
			{
				for(Yearselldetal yearselldetal:lsYearselldetals)
				{
					if(!CommonTool.checkStr(strSql))
					{
						strSql="select sum(csitemcount) from dconsumeinfo  where csinfotype=1 and yearinid='"+yearselldetal.getIteminid()+"' and isnull(yearinid,'')<>'' ";
					}
					else {
						strSql+=" union all select sum(csitemcount) from dconsumeinfo  where csinfotype=1 and yearinid='"+yearselldetal.getIteminid()+"' and isnull(yearinid,'')<>'' ";
					}
				}
				if(CommonTool.checkStr(strSql))
				{
					rs=amn_Dao.executeQuery(strSql);
					if(rs!=null)
					{
						while(rs.next())
						{
							xCount+=CommonTool.FormatDouble(rs.getDouble(1));
						}
					}
				}
				if(xCount>0)
				{
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
		
	}
	
	
	//验证条码卡是否是完整
	public boolean	validateCompleteTMCard(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" select 1 from msalebarcodecardinfo a,dnointernalcardinfo b  " +
					" where b.cardno=a.barcodecardno and isnull(b.lastcount,0)<>isnull(b.entrycount,0)" +
					" and a.salecompid='"+strCompId+"' and a.salebillid='"+strBillId+"'  ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			{
				public Boolean anlyResultSet(ResultSet rs)
				{
					boolean returnValue =true;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=false;
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue = false;
					}
					
					return returnValue;
				}
			};
			boolean falg=(Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return falg;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	//验证是完整兑换还是部分兑换
	public int validateChangeProType(String strCompId,String strBillId,double changeseqno)
	{
		try
		{
			String strSql=" select 1 from mproexchangeinfo a,dproexchangeinfo b,cardproaccount c " +
					" where a.changecompid=b.changecompid and a.changebillid=b.changebillid and b.changecompid='"+strCompId+"' and b.changebillid='"+strBillId+"' and b.changeseqno="+changeseqno+"" +
					" and a.changecardno=c.cardno and b.changeproid=c.projectno " +
					" and a.changebillid=c.createbillno and c.createbilltype='LCDH' and c.lastcount=b.changeprocount  " +
					" and isnull(changebyproaccountamt,0)=0 "; 
			AnlyResultSet<Integer> analysis = new AnlyResultSet<Integer>()
			{
				public Integer anlyResultSet(ResultSet rs)
				{
					int returnValue =0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=1;
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
			int falg=(Integer)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return falg;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}
	//验证开卡前金额
	public boolean validateMsaleCardSale(String strCompId,String strBillId,BigDecimal curkeepamt)
	{
		try
		{
			String strSql=" select 1 from msalecardinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and convert(numeric(20,1),isnull(salekeepamt,0))=convert(numeric(20,1),"+curkeepamt+") "; 
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
			boolean falg=(Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return falg;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	//验证充值前金额
	public boolean validateMrechareCardSale(String strCompId,String strBillId,BigDecimal curkeepamt)
	{
		try
		{
			String strSql=" select 1 from mcardrechargeinfo where rechargecompid='"+strCompId+"' and rechargebillid='"+strBillId+"' and convert(numeric(20,1),isnull(curcardamt,0)+isnull(rechargekeepamt,0))=convert(numeric(20,1),"+curkeepamt+") "; 
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
			boolean falg=(Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return falg;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	//验证充值前金额
	public boolean validateChangeCardSale(String strCompId,String strBillId,BigDecimal curkeepamt)
	{
		try
		{
			String strSql=" select 1 from mcardchangeinfo where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and convert(numeric(20,1),isnull(curaccountkeepamt,0)+isnull(changefillamt,0))=convert(numeric(20,1),"+curkeepamt+") "; 
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
			boolean falg=(Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return falg;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public BigDecimal loadCurkeepAmt(String strCardNo,int acttype)
	{
		String strSql=" select accountbalance  from cardaccount where  cardno='"+strCardNo+"'  and accounttype="+acttype+" ";
		AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue =1;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("accountbalance"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  1;
				}
				if(returnValue==0)
					returnValue=1;
				return returnValue;
			}
		};
		BigDecimal curkeepamt=CommonTool.FormatBigDecimal(new BigDecimal((Double)this.amn_Dao.executeQuery_ex(strSql,analysis)));
		analysis=null;
		return curkeepamt;
	}


	public boolean validateConsoleFlag(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" select count(1) from mconsumeinfo where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(backcsflag,0)=0 ";
			int count=this.amn_Dao.getRowsCount_Ex(strSql);
			if(count>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean validateCardState(String strCardNo)
	{
		try
		{
			if(strCardNo.equals("散客"))
				return true;
			String strSql=" select count(1) from cardinfo with(nolock) where cardno='"+strCardNo+"' and isnull(cardstate,0) in (4,5) ";
			int count=this.amn_Dao.getRowsCount_Ex(strSql);
			if(count>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean validateSaleFlag(String strCompId,String strBillId,int ntype)
	{
		String strSql="";
		if(ntype==0)  //开卡
		{
			strSql="select count(1) from msalecardinfo with(nolock) where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and isnull(backflag,0)=0 ";
		}
		else if(ntype==1)  //充值
		{
			strSql="select count(1) from mcardrechargeinfo with(nolock) where rechargecompid='"+strCompId+"' and rechargebillid='"+strBillId+"' and isnull(backflag,0)=0 ";
		}
		else if(ntype==2  )  //转卡
		{
			strSql="select count(1)  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype=0  and isnull(backflag,0)=0";
		}
		else if(ntype==3 )  //转卡
		{
			strSql="select count(1) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype=1 and isnull(backflag,0)=0 ";
		}
		else if(ntype==4   )  //转卡
		{
			strSql="select count(1) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype=2  and isnull(backflag,0)=0";
		}
		else if(ntype==5  )  //并卡
		{
			strSql="select count(1) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype in (6,7) and isnull(backflag,0)=0";
		}
		else if(ntype==6  )  //退卡
		{
			strSql="select count(1) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype = 8 and isnull(backflag,0)=0 ";
		}
		else if(ntype==7  )  //合作项目
		{
			strSql=	"select count(1) from mcooperatesaleinfo with(nolock) where salecompid='"+strCompId+"' and salebillid='"+strBillId+"'  and isnull(salebakflag,0)=0 ";
		}
		else if(ntype==8  )  //条码卡
		{
			strSql=	"select count(1) from msalebarcodecardinfo with(nolock) where salecompid='"+strCompId+"' and salebillid='"+strBillId+"'  and isnull(salebakflag,0)=0 ";
		}
		else if(ntype==9  )  //疗程兑换
		{
			strSql=	"select count(1) from mproexchangeinfo a with(nolock), dproexchangeinfo b with(nolock) where a.changecompid='"+strCompId+"' and a.changebillid='"+strBillId+"' and isnull(salebakflag,0)=0" +
					"   and a.changecompid=b.changecompid and a.changebillid=b.changebillid  ";
		}
		else if(ntype==12)
		{
			strSql=" select count(1) from yearsellinfo a with(nolock),yearselldetal b with(nolock) where a.compid='"+strCompId+"' and a.billid='"+strBillId+"' and isnull(backcsflag,0)=0" +
					" and a.compid=b.compid and a.billid=b.billid";
		}
		int count=this.amn_Dao.getRowsCount_Ex(strSql);
		if(count>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
