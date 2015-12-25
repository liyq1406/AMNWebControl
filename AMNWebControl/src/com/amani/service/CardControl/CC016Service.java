package com.amani.service.CardControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.ReEditBillInfo;
import com.amani.model.Companyinfo;
import com.amani.model.Dactivitygive;
import com.amani.model.Dactivityinfo;
import com.amani.model.Dconsumeinfo;
import com.amani.model.Dnointernalcardinfo;
import com.amani.model.Dproexchangeinfo;
import com.amani.model.Mactivitygive;
import com.amani.model.Mactivityinfo;
import com.amani.model.Mpackageinfo;
import com.amani.model.NointernalcardinfoId;
import com.amani.model.Sendpointcard;
import com.amani.model.SendpointcardId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
import com.amani.tools.SysSendMsg;
@Service
public class CC016Service  extends AMN_ModuleService{

	@Autowired
	private DESPlus desPlus;
	
	protected static SysSendMsg sysSendMsg;
	public SysSendMsg getSysSendMsg() {
		return sysSendMsg;
	}
	public void setSysSendMsg(SysSendMsg sysSendMsg) {
		this.sysSendMsg = sysSendMsg;
	}
	
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
		String strSql=	//"	WITH cardamt as(SELECT strCardNo=salecardno FROM msalecardinfo WITH(nolock) WHERE salecompid='"+strCompId+"' AND financedate='"+strDate+"' AND isnull(sendpointflag,0)=0 UNION SELECT strCardNo=rechargecardno FROM mcardrechargeinfo WITH(nolock) WHERE rechargecompid='"+strCompId+
						//"'  AND financedate='"+strDate+"' AND isnull(sendpointflag,0)=0 UNION SELECT strCardNo=changeaftercardno FROM mcardchangeinfo WITH(nolock) WHERE changecompid='"+strCompId+"' AND financedate='"+strDate+"' AND changetype in(0,1,2,6,7) AND isnull(sendpointflag,0)=0) " +
						//"	UNION ALL select strCardNo=a.cardno from yearsellinfo a with(nolock), yearselldetal b with(nolock) where a.compid=b.compid and a.billid=b.billid and a.compid='"+strCompId+"' and a.accountdate='"+strDate+"' and b.packno='201048' "+
						" select ttype=0,ttypename='开卡',billcount=COUNT(salebillid) from msalecardinfo with(nolock) where salecompid='"+strCompId+"' and financedate='"+strDate+"' and isnull(sendpointflag,0)=0" +
						"	union all " +
						"	select  ttype=1,ttypename='充值/还款',billcount=COUNT(rechargebillid) from mcardrechargeinfo with(nolock) where rechargecompid='"+strCompId+"' and financedate='"+strDate+"' and isnull(sendpointflag,0)=0" +
						"	union all " +
						"	select  ttype=2,ttypename='折扣转卡',billcount=COUNT(changebillid) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strDate+"' and changetype =0 and isnull(sendpointflag,0)=0 " +
						"	union all " +
						"	select  ttype=3,ttypename='收购转卡',billcount=COUNT(changebillid) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strDate+"' and changetype =1 and isnull(sendpointflag,0)=0 " +
						"	union all " +
						"	select  ttype=4,ttypename='竞争转卡',billcount=COUNT(changebillid) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strDate+"' and changetype =2 and isnull(sendpointflag,0)=0 " +
						"	union all " +
						"	select  ttype=5,ttypename='并卡',billcount=COUNT(changebillid) from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strDate+"' and changetype in (6,7) and isnull(sendpointflag,0)=0 " +
						//"	union all " +
						//"	select  ttype=6,ttypename='氧动力兑换',billcount=COUNT(changeseqno) from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock) where  a.changecompid=b.changecompid and a.changebillid=b.changebillid and  a.changecompid='"+strCompId+"' and a.financedate='"+strDate+"' and a.financedate>='20140501' and b.changeproid='49800005' and isnull(sendpointflag,0)=0 "+
						"	union all " +
						"	select  ttype=7,ttypename='疗程兑换',billcount=COUNT(distinct changecardno) from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock),projectnameinfo c  where  a.changecompid=b.changecompid and a.changebillid=b.changebillid and  a.changecompid='"+strCompId+"' and a.financedate='"+strDate+"' and isnull(sendpointflag,0)=0  and b.changeproid=c.prjno " +
						"	union all " +
						"	select  ttype=8,ttypename='会员消费',billcount=COUNT(csbillid) from mconsumeinfo with(nolock) where cscompid='"+strCompId+"' and financedate='"+strDate+"' and isnull(backcsflag,0)=0 and isnull(backcsbillid,'')='' and isnull(sendpointflag,0)=0 " +
						"	union all " +
						"   select ttype=9,ttypename='9月胸部活动',billcount=COUNT(distinct changecardno) FROM mproexchangeinfo a with(nolock), dproexchangeinfo b with(nolock), projectnameinfo c WHERE a.changecompid=b.changecompid AND a.changebillid=b.changebillid AND a.changecompid='"+strCompId+"' AND a.financedate='"+strDate+"' AND isnull(sendpointflag,0)=0 AND b.changeproid=c.prjno AND c.prjreporttype in(18, 32)";
		
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
			strSql="select ttype=0,strCompId=salecompid,strBillId=salebillid,changeseqno=0,strCardNo=salecardno,strCardType=salecardtype,strMemberName=membername" +
					"  from msalecardinfo with(nolock) where salecompid='"+strCompId+"' and financedate='"+strSearchDate+"' and isnull(sendpointflag,0)=0 ";
		}
		else if(ntype==1)  //充值
		{
			strSql="select ttype=1,strCompId=rechargecompid,strBillId=rechargebillid,changeseqno=0,strCardNo=rechargecardno,strCardType=rechargecardtype,strMemberName=membername" +
				"  from mcardrechargeinfo with(nolock) where rechargecompid='"+strCompId+"' and financedate='"+strSearchDate+"' and isnull(sendpointflag,0)=0 ";
		}
		else if(ntype==2 || ntype==3  || ntype==4  )  //转卡
		{
			strSql="select ttype=2,strCompId=changecompid,strBillId=changebillid,changeseqno=0,strCardNo=changeaftercardno,strCardType=changeaftercardtype,strMemberName=membername " +
					"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strSearchDate+"' and changetype in (0,1,2) and isnull(sendpointflag,0)=0 ";
		}
		else if(ntype==5  )  //并卡
		{
			strSql="select ttype=5,strCompId=changecompid,strBillId=changebillid,changeseqno=0,strCardNo=changeaftercardno,strCardType=changeaftercardtype,strMemberName=membername " +
				"  from mcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and financedate='"+strSearchDate+"' and changetype in (6,7) and isnull(sendpointflag,0)=0 ";
		}
		else if(ntype==6  )  //氧动力疗程兑换
		{
			strSql="select ttype=6,strCompId=a.changecompid,strBillId=a.changebillid,changeseqno=changeseqno,strCardNo=changecardno,strCardType=changecardtype,strMemberName=membername " +
				"  from mproexchangeinfo a with(nolock),dproexchangeinfo  b with(nolock) " +
				"  where a.changecompid=b.changecompid and a.changebillid=b.changebillid" +
				" and  a.changecompid='"+strCompId+"' and a.financedate='"+strSearchDate+"' " +
				" and b.changeproid='49800005' and isnull(sendpointflag,0)=0 ";
		}
		else if(ntype==7  )  //疗程兑换
		{
			strSql="select ttype=7,strCompId=a.changecompid,strBillId=changecardno+'_'+substring(a.changebillid,4,8),changeseqno=0,strCardNo=changecardno,strCardType=changecardtype,strMemberName=membername " +
				"  from mproexchangeinfo a with(nolock),dproexchangeinfo  b with(nolock) ,projectnameinfo c  " +
				"  where a.changecompid=b.changecompid and a.changebillid=b.changebillid" +
				" and  a.changecompid='"+strCompId+"' and a.financedate='"+strSearchDate+"' " +
				" and isnull(sendpointflag,0)=0  and b.changeproid=c.prjno " +
				" group by a.changecompid,changecardno+'_'+substring(a.changebillid,4,8),changecardno,changecardtype,membername ";
		}
		else if(ntype==8)  //消费
		{
			strSql="select ttype=8,strCompId=cscompid,strBillId=csbillid,changeseqno=0,strCardNo=cscardno,strCardType=cscardtype,strMemberName=csname " +
					"  from mconsumeinfo with(nolock) where cscompid='"+strCompId+"' and financedate='"+strSearchDate+"' and isnull(sendpointflag,0)=0 ";
		}
		else if(ntype==9){ //美容积分
			StringBuffer sql = new StringBuffer();
			 sql.append("SELECT ttype=9,strCompId=a.changecompid,strBillId=max(a.changebillid),changeseqno=0,strCardNo=changecardno,strCardType=max(changecardtype),strMemberName=max(membername) ")
				.append("FROM mproexchangeinfo a with(nolock), dproexchangeinfo b with(nolock), projectnameinfo c ")//胸部和香遇疗程
				.append("WHERE a.changecompid=b.changecompid AND a.changebillid=b.changebillid AND a.changecompid='"+strCompId+"' AND a.financedate='"+strSearchDate+"' ")
				.append("AND isnull(sendpointflag,0)=0 AND b.changeproid=c.prjno AND c.prjreporttype in(18, 32) ")
				.append("group by a.changecompid,changecardno ")
				.append("UNION ALL ")//201048胸部年卡
				.append("select ttype=9,strCompId=a.compid,strBillId=max(a.billid),changeseqno=0,strCardNo=a.cardno,strCardType=NULL,strMemberName=max(a.name) ") 
				.append("from yearsellinfo a with(nolock), yearselldetal b with(nolock) ")
				.append("where a.compid=b.compid and a.billid=b.billid and a.compid='"+strCompId+"' and b.packno in('201028','201029','201030','201048','201004','201005','201006','202009','202010','202011') and a.accountdate='"+strSearchDate+"'")
				.append("group by a.compid,cardno");
			 strSql = sql.toString();
		}
		AnlyResultSet<List<ReEditBillInfo> > analysis = new AnlyResultSet<List<ReEditBillInfo> >()
    	{
    			public List<ReEditBillInfo>  anlyResultSet(ResultSet rs)
    			{
    				List<ReEditBillInfo>  returnValue = new ArrayList();
    				ReEditBillInfo record=null;
    				try
    				{
    					if(rs != null){
    						while(rs.next()==true)
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
	

	
	public Sendpointcard loadSendpointcard(String strCompId,String strBillId,String strCardNo,String strDate,int ntype,double changeseqno)
	{
		try
		{
			String strSql="";
			if(ntype==0)  //开卡
			{
				strSql="select strCompId=salecompid,strBillId=salebillid,strdate=saledate,strCardNo=salecardno,strCardType=salecardtype,strMemberName=membername,payamt=sum(isnull(payamt,0)) ,changeseqno=0,paycount=0,bsendbillid='' " +
						"  from msalecardinfo with(nolock),dpayinfo with(nolock) " +
						"   where salecompid='"+strCompId+"' and salebillid='"+strBillId+"'" +
						"   and salecompid=paycompid and salebillid=paybillid and paybilltype='SK' " +
						"   group by salecompid,salebillid,saledate,salecardno,salecardtype,membername";
			}
			else if(ntype==1)  //充值
			{
				strSql="select strCompId=rechargecompid,strBillId=rechargebillid,strdate=rechargedate,strCardNo=rechargecardno,strCardType=rechargecardtype,strMemberName=membername,payamt=sum(isnull(payamt,0)) ,changeseqno=0,paycount=0,bsendbillid='' " +
					"  from mcardrechargeinfo with(nolock),dpayinfo with(nolock) " +
					"  where rechargecompid='"+strCompId+"' and rechargebillid='"+strBillId+"' "+
					"   and rechargecompid=paycompid and rechargebillid=paybillid and paybilltype='CZ' " +
					"   group by rechargecompid,rechargebillid,rechargedate,rechargecardno,rechargecardtype,membername";
			}
			else if(ntype==2 || ntype==3  || ntype==4  )  //转卡
			{
				strSql="select strCompId=changecompid,strBillId=changebillid,strdate=changedate,strCardNo=changeaftercardno,strCardType=changeaftercardtype,strMemberName=membername,payamt=sum(isnull(payamt,0)) ,changeseqno=0,paycount=0,bsendbillid='' " +
						"  from mcardchangeinfo with(nolock),dpayinfo with(nolock) " +
						"  where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype in (0,1,2,6,7) "+
						"   and changecompid=paycompid and changebillid=paybillid and paybilltype='ZK' " +
						"   group by changecompid,changebillid,changedate,changeaftercardno,changeaftercardtype,membername";
			}
			else if(ntype==5 )  //转卡
			{
				strSql="select strCompId=changecompid,strBillId=changebillid,strdate=changedate,strCardNo=changebeforcardno,strCardType=changebeforcardtype,strMemberName=membername,payamt=sum(isnull(payamt,0)),changeseqno=0,paycount=0,bsendbillid='' " +
						"  from mcardchangeinfo with(nolock),dpayinfo with(nolock) " +
						"  where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changetype in (0,1,2,6,7) "+
						"   and changecompid=paycompid and changebillid=paybillid and paybilltype='ZK' " +
						"   group by changecompid,changebillid,changedate,changebeforcardno,changebeforcardtype,membername";
			}
			else if(ntype==6 )  //疗程兑换（停用）
			{
				strSql="select strCompId=a.changecompid,strBillId=a.changebillid,strdate=changedate,strCardNo=changecardno,strCardType=changecardtype,strMemberName=membername,payamt=isnull(procount,0),changeseqno=changeseqno,paycount=0,bsendbillid='' " +
						"  from mproexchangeinfo a with(nolock),dproexchangeinfo  b with(nolock) " +
						"  where a.changecompid=b.changecompid and a.changebillid=b.changebillid " +
						  " and  a.changecompid='"+strCompId+"' and a.changebillid='"+strBillId+"' " +
						  " and b.changeseqno="+changeseqno+" ";
			}
			else if(ntype==7)  //疗程兑换
			{
				strSql="select strCompId=a.changecompid,strBillId=changecardno+'_'+substring(a.changebillid,4,8),strdate=financedate,strCardNo=changecardno,strCardType=changecardtype,strMemberName=membername,payamt=sum(isnull(procount,0)),changeseqno=0,paycount=SUM(isnull(changeproamt,0)),bsendbillid=max(a.changebillid) " +
						" from mproexchangeinfo a with(nolock),dproexchangeinfo  b with(nolock) " +
						" where a.changecompid=b.changecompid and a.changebillid=b.changebillid " +
						" and  a.changecompid='"+strCompId+"' and a.changecardno='"+strCardNo+"'  and financedate='"+CommonTool.setDateMask(strDate)+"' " +
						" group by a.changecompid,changecardno+'_'+substring(a.changebillid,4,8),financedate,changecardno,changecardtype,membername ";
			}
			else if(ntype==8)  //消费
			{
				strSql="select strCompId=a.cscompid,strBillId=a.csbillid,strdate=csdate,strCardNo=cscardno,strCardType=cscardtype,strMemberName=csname,payamt=SUM(isnull(csitemamt, 0)) ,changeseqno=0,paycount=SUM(isnull(csitemcount, 0)),bsendbillid=''" +
						"  from mconsumeinfo a with(nolock),dpayinfo with(nolock), dconsumeinfo b with(nolock)" +
						"   where a.cscompid='"+strCompId+"' and a.csbillid='"+strBillId+"'" +
						"   and a.cscompid=paycompid and a.csbillid=paybillid  and a.cscompid=b.cscompid and a.csbillid=b.csbillid and paybilltype='SY' " +
						"   group by a.cscompid,a.csbillid,csdate,cscardno,cscardtype,csname";
			}else if(ntype==9){//9月胸部活动
				strSql="select strCompId=a.changecompid,strBillId=max(a.changebillid),changeseqno=0,strdate=financedate,strCardNo=changecardno,strCardType=max(changecardtype),strMemberName=max(membername),payamt=sum(isnull(procount,0)),changeseqno=0,paycount=SUM(isnull(changeproamt,0)),bsendbillid='' "+
						"FROM mproexchangeinfo a with(nolock), dproexchangeinfo b with(nolock), projectnameinfo c "+//胸部和香遇疗程
						"WHERE a.changecompid=b.changecompid AND a.changebillid=b.changebillid AND a.changecompid='"+strCompId+"'  and a.changecardno='"+strCardNo+"' and a.financedate='"+CommonTool.setDateMask(strDate)+"' "+
						"AND b.changeproid=c.prjno AND c.prjreporttype in(18, 32) group by a.changecompid,changecardno,financedate";
			}
			AnlyResultSet<Sendpointcard > analysis = new AnlyResultSet<Sendpointcard >()
	    	{
	    			public Sendpointcard  anlyResultSet(ResultSet rs)
	    			{
	    				Sendpointcard returnValue = new Sendpointcard();
	    				try
	    				{
		    				if(rs != null && rs.next()==true)
		    				{
		    					returnValue.setSourcebillid(CommonTool.FormatString(rs.getString("strBillId")));
		    					returnValue.setSourcecardno(CommonTool.FormatString(rs.getString("strCardNo")));
		    					returnValue.setSourcecardtype(CommonTool.FormatString(rs.getString("strCardType")));
		    					returnValue.setSourcedate(CommonTool.FormatString(rs.getString("strdate")));
		    					returnValue.setMembername(CommonTool.FormatString(rs.getString("strMemberName")));
		    					if(!CommonTool.FormatString(rs.getString("strMemberName")).equals(""))
		    					{
		    						returnValue.setMembername(CommonTool.FormatString(rs.getString("strMemberName")).substring(0,1)+"**");
		    					}
		    					returnValue.setChangeseqno(CommonTool.FormatDouble(rs.getDouble("changeseqno")));
		    					returnValue.setSourceamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("payamt")))));
		    					returnValue.setSendamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("paycount")))));
		    					returnValue.setBsendbillid(CommonTool.FormatString(rs.getString("bsendbillid")));
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
	    	Sendpointcard record =(Sendpointcard )this.amn_Dao.executeQuery_ex(strSql,analysis);
	    	if(record!=null)
	    	{
	    		record.setId(new SendpointcardId(strCompId,this.getDataTool().loadBillIdByRule(strCompId,"sendpointcard", "sendbillid", "SP007"),ntype));
	    		record.setSendrateflag(this.loadSendRate(strCompId, record.getSourcedate()));
	    	}
	    	else
	    	{
	    		record= new Sendpointcard();
	    		record.setId(new SendpointcardId(strCompId,this.getDataTool().loadBillIdByRule(strCompId,"sendpointcard", "sendbillid", "SP007"),ntype));
		    }
	    	analysis=null;
	    	return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
//	public boolean postCurDateInfo(String strCompId,String strBillId,double changeseqno,int nBillType,ReEditBillInfo curRecord)
//	{
//		try
//		{
//			String strSql="";
//			if(nBillType==0)  //开卡
//				strSql="update msalecardinfo set  sendpointflag=1 ";
//			else if(nBillType==1)  //充值
//				strSql="update mcardrechargeinfo set  sendpointflag=1 ";
//			else if(nBillType==2 || nBillType==3 ||nBillType==4 ||nBillType==5 )  //转卡
//				strSql="update mcardchangeinfo set  sendpointflag=1 ";
//			else if(nBillType==6)  //疗程兑换
//				strSql="update dproexchangeinfo set  sendpointflag=1 ";
//			if(nBillType==0)  //开卡
//				strSql=strSql+" where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
//			else if(nBillType==1)  //充值
//				strSql=strSql+" where rechargecompid='"+strCompId+"' and rechargebillid='"+strBillId+"' ";
//			else if(nBillType==2 || nBillType==3 ||nBillType==4 ||nBillType==5 )  //转卡
//				strSql=strSql+" where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' ";
//			else if(nBillType==6 )  //疗程兑换
//				strSql=strSql+" where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changeseqno="+changeseqno+" ";
//			
//			this.amn_Dao.executeSql(strSql);
//			return true;
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			return false;
//		}
//	}
//	
	public int loadSendRate(String strCompid,String strDate)
	{
		try
		{
			double sendPointRate1=CommonTool.FormatDouble(Double.parseDouble(this.getDataTool().loadSysParam(strCompid, "SP100")));
			String strSql="select COUNT(sendbillid) from sendpointcard where sendcompid='"+strCompid+"' and isnull(sendpicflag,0)=1 and isnull(sendamt,0)=isnull(sourceamt,0)*"+sendPointRate1+"  and substring(senddate,1,6)=substring('"+strDate+"',1,6) " ;
			int ccount=this.amn_Dao.getRowsCount_Ex(strSql);
			if(ccount>=3)
				return 1;
			else
				return 2;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 10;
		}
	}
	
	public boolean validateBill(String strCompId,String strBillId,int nBillType,double changeseqno,String strCardNo,String strDate)
	{
		String strSql="";
		if(nBillType==0)
		{
			strSql=" select 1 from  msalecardinfo  where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and isnull(sendpointflag,0)=0 ";
		}
		else if(nBillType==1)
		{
			strSql=" select 1 from  mcardrechargeinfo  where rechargecompid='"+strCompId+"' and rechargebillid='"+strBillId+"' and isnull(sendpointflag,0)=0  ";
		}
		else if(nBillType==2 || nBillType==3 ||nBillType==4 ||nBillType==5 )
		{
			strSql=" select 1 from  mcardchangeinfo  where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and isnull(sendpointflag,0)=0  ";
		}
		else if(nBillType==6)
		{
			strSql=" select 1 from  dproexchangeinfo  where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' and changeseqno="+changeseqno+" and isnull(sendpointflag,0)=0  ";
		}
		else if(nBillType==7)
		{
			strSql=" select 1 from  mproexchangeinfo a, dproexchangeinfo b  where a.changecompid=b.changecompid and a.changebillid=b.changebillid " +
						" and  a.changecompid='"+strCompId+"' and a.changecardno='"+strCardNo+"'  and financedate='"+CommonTool.setDateMask(strDate)+"'  and isnull(sendpointflag,0)=0  ";
		}
		else if(nBillType==9)
		{
			strSql=" select 1 from  mproexchangeinfo a, dproexchangeinfo b  where a.changecompid=b.changecompid and a.changebillid=b.changebillid " +
					" and  a.changecompid='"+strCompId+"' and a.changecardno='"+strCardNo+"'  and financedate='"+CommonTool.setDateMask(strDate)+"'  and isnull(sendpointflag,0)=0  ";
			strSql+=" union all ";
			strSql+=" select 1 from yearsellinfo where cardno='"+strCardNo+"' and accountdate='"+CommonTool.setDateMask(strDate)+"' and compid='"+strCompId+"' and isnull(sendpointflag,0)=0 ";
			
		}
		else if(nBillType==8)
		{
			strSql=" select 1 from  mconsumeinfo  where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(sendpointflag,0)=0 ";
		}
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = false;
				try
				{
				if(rs != null && rs.next()==true)
				{
					returnValue =  true;
				}
				else
				{
					returnValue =  false;
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
	
	
	public boolean validateTmkCardNo(String strCardNo)
	{
		String strSql=" select 1 from nointernalcardinfo where cardtype=2 and cardstate=0 and carduseflag=1  and cardno='"+strCardNo+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = false;
				try
				{
				if(rs != null && rs.next()==true)
				{
					returnValue =  true;
				}
				else
				{
					returnValue =  false;
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
	
	public boolean validateXmDyqCardNo(String strCardNo,int createcardtype)
	{
		String strSql=" select 1 from nointernalcardinfo where cardtype=1 and cardstate=0 and carduseflag=1  and cardno='"+strCardNo+"' and isnull(createcardtype,0)="+createcardtype+" ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = false;
				try
				{
				if(rs != null && rs.next()==true)
				{
					returnValue =  true;
				}
				else
				{
					returnValue =  false;
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
	
	public boolean validateDyqCardNo(String strCardNo)
	{
		String strSql=" select 1 from nointernalcardinfo where cardno='"+strCardNo+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = true;
				try
				{
				if(rs != null && rs.next()==true)
				{
					returnValue =  false;
				}
				else
				{
					returnValue =  true;
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
	public boolean handCardPointSend(Sendpointcard curSendpointcard,String strTmkJson,String strJsonDyqParam)
	{
		try
		{
			curSendpointcard.getId().setSendbillid(this.getDataTool().loadBillIdByRule(curSendpointcard.getId().getSendcompid(),"sendpointcard", "sendbillid", "SP007"));
			curSendpointcard.setSenddate(CommonTool.getCurrDate());
			curSendpointcard.setSendempid(CommonTool.getLoginInfo("USERID"));
			curSendpointcard.setOperation(CommonTool.getLoginInfo("USERID"));
			int nBillType=curSendpointcard.getId().getSendtype();
			if(nBillType==7)
			{
				curSendpointcard.setSourcebillid("");
			}
			this.amn_Dao.saveOrUpdate(curSendpointcard);
			String strSql="";
			
			if(nBillType==0)
			{
				strSql=strSql+" update msalecardinfo set sendpointflag=1 where salecompid='"+curSendpointcard.getId().getSendcompid()+"' and salebillid='"+curSendpointcard.getSourcebillid()+"'  ";
			}
			else if(nBillType==1)
			{
				strSql=strSql+" update mcardrechargeinfo set sendpointflag=1 where rechargecompid='"+curSendpointcard.getId().getSendcompid()+"' and rechargebillid='"+curSendpointcard.getSourcebillid()+"'  ";
			}
			else if(nBillType==2 || nBillType==3 ||nBillType==4 ||nBillType==5 )
			{
				strSql=strSql+" update mcardchangeinfo set sendpointflag=1 where changecompid='"+curSendpointcard.getId().getSendcompid()+"' and changebillid='"+curSendpointcard.getSourcebillid()+"'  ";
			}
			else if(nBillType==6)
			{
				strSql=strSql+" update dproexchangeinfo set sendpointflag=1 where changecompid='"+curSendpointcard.getId().getSendcompid()+"' and changebillid='"+curSendpointcard.getSourcebillid()+"' and  changeseqno="+curSendpointcard.getChangeseqno()+" ";
			}
			else if(nBillType==7)
			{
				strSql=strSql+" update b set sendpointflag=1 from mproexchangeinfo a ,dproexchangeinfo  b " +
						" where a.changecompid=b.changecompid and a.changebillid=b.changebillid " +
						" and  a.changecompid='"+curSendpointcard.getId().getSendcompid()+"' and a.changecardno='"+curSendpointcard.getSourcecardno()+"'  and financedate='"+CommonTool.setDateMask(curSendpointcard.getSourcedate())+"' ";
			}
			else if(nBillType==8)
			{
				strSql=strSql+" update mconsumeinfo set sendpointflag=1 where cscompid='"+curSendpointcard.getId().getSendcompid()+"' and csbillid='"+curSendpointcard.getSourcebillid()+"'  ";
			}else if(nBillType==9){
				String compid = curSendpointcard.getId().getSendcompid();
				String sourcedate = CommonTool.setDateMask(curSendpointcard.getSourcedate());
				String cardno = curSendpointcard.getSourcecardno();
				strSql=" update msalecardinfo set sendpointflag=1 where salecompid='"+compid+"' and financedate='"+sourcedate+"' and salecardno='"+cardno+"' "+//开卡
					   " update mcardrechargeinfo set sendpointflag=1 where rechargecompid='"+compid+"' and financedate='"+sourcedate+"' and rechargecardno='"+cardno+"' "+//充值
					   " update mcardchangeinfo set sendpointflag=1 where changecompid='"+compid+"' and financedate='"+sourcedate+"' and changeaftercardno='"+cardno+"' "+//转卡、并卡
					   " update b set sendpointflag=1 from mproexchangeinfo a,dproexchangeinfo b,projectnameinfo c where a.changecompid=b.changecompid and a.changebillid=b.changebillid " +
					   " and b.changeproid=c.prjno and c.prjreporttype in(18, 32) and a.changecompid='"+compid+"' and a.changecardno='"+cardno+"' and financedate='"+sourcedate+"' "+//胸部香遇疗程
					   " update b set sendpointflag=1 from yearsellinfo a, yearselldetal b where a.compid=b.compid and a.billid=b.billid and b.packno in('201028','201029','201030','201048','201004','201005','201006','202009','202010','202011') "+//胸部年卡
					   " and a.compid='"+compid+"' and a.accountdate='"+sourcedate+"' and a.cardno='"+ cardno +"' ";
			}
			if(curSendpointcard.getSendpicflag()==0)//赠送美容积分
			{
				if(CommonTool.FormatBigDecimal(curSendpointcard.getSendamt()).doubleValue()!=0)
				{
					strSql=strSql+" declare @costaccount7lastamt	float  ";
					strSql=strSql+" declare @costaccountseqno	float  ";
					strSql=strSql+" select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno='"+curSendpointcard.getSourcecardno()+"' ";
					strSql=strSql+" select top 1 @costaccount7lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno='"+curSendpointcard.getSourcecardno()+"'  and changeaccounttype=7 order by changeseqno desc ";
					strSql=strSql+" if(ISNULL(@costaccount7lastamt,0)=0) ";
					strSql=strSql+" select @costaccount7lastamt=ISNULL(accountbalance,0) from cardaccount where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype=7 ";
					strSql=strSql+" insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  ";
					strSql=strSql+" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getSourcecardno()+"',7,ISNULL(@costaccountseqno,0),0,"+CommonTool.FormatBigDecimal(curSendpointcard.getSendamt())+",'ZS','"+curSendpointcard.getId().getSendbillid()+"','"+CommonTool.getCurrDate()+"',@costaccount7lastamt)   ";
					strSql=strSql+" if not exists( select 1 from cardaccount where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype='7' ) ";
					strSql=strSql+" begin insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend)" +
							"    select cardvesting,cardno,7,"+CommonTool.FormatBigDecimal(curSendpointcard.getSendamt())+",0,'"+CommonTool.getCurrDate()+"','20330101' from cardinfo where cardno='"+curSendpointcard.getSourcecardno()+"'  end ";
					strSql=strSql+" else update cardaccount set accountbalance=ISNULL(accountbalance,0)+"+CommonTool.FormatBigDecimal(curSendpointcard.getSendamt())+" where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype='7'   ";
					return this.amn_Dao.executeSql(strSql);
				}
			}
			else if(curSendpointcard.getSendpicflag()==1)//赠送积分
			{
				if(CommonTool.FormatBigDecimal(curSendpointcard.getSendamt()).doubleValue()!=0)
				{
					strSql=strSql+" declare @costaccount3lastamt	float  ";
					strSql=strSql+" declare @costaccountseqno	float  ";
					strSql=strSql+" select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno='"+curSendpointcard.getSourcecardno()+"' ";
					strSql=strSql+" select top 1 @costaccount3lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno='"+curSendpointcard.getSourcecardno()+"'  and changeaccounttype=3 order by changeseqno desc ";
					strSql=strSql+" if(ISNULL(@costaccount3lastamt,0)=0) ";
					strSql=strSql+" select @costaccount3lastamt=ISNULL(accountbalance,0) from cardaccount where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype=3 ";
					strSql=strSql+" insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  ";
					strSql=strSql+" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getSourcecardno()+"',3,ISNULL(@costaccountseqno,0),0,"+CommonTool.FormatBigDecimal(curSendpointcard.getSendamt())+",'ZS','"+curSendpointcard.getId().getSendbillid()+"','"+CommonTool.getCurrDate()+"',@costaccount3lastamt)   ";
					strSql=strSql+" if not exists( select 1 from cardaccount where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype='3' ) ";
					strSql=strSql+" begin insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend)" +
							"    select cardvesting,cardno,3,"+CommonTool.FormatBigDecimal(curSendpointcard.getSendamt())+",0,'"+CommonTool.getCurrDate()+"','20330101' from cardinfo where cardno='"+curSendpointcard.getSourcecardno()+"'  end ";
					strSql=strSql+" else update cardaccount set accountbalance=ISNULL(accountbalance,0)+"+CommonTool.FormatBigDecimal(curSendpointcard.getSendamt())+" where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype='3'   ";
					return this.amn_Dao.executeSql(strSql);
				}
			}
			else if(curSendpointcard.getSendpicflag()==2)//赠送条码卡
			{
				if(strTmkJson!= null && !strTmkJson.equals(""))
				{
					List<Dnointernalcardinfo> lsdnointernalcardinfo=this.getDataTool().loadDTOList(strTmkJson, Dnointernalcardinfo.class);
					if(lsdnointernalcardinfo!=null && lsdnointernalcardinfo.size()>0)
					{
						strSql=strSql+" update nointernalcardinfo set cardstate=1 where cardno='"+CommonTool.FormatString(curSendpointcard.getPicno())+"' ";
						for(int i=0;i<lsdnointernalcardinfo.size();i++)
						{
							strSql=strSql+" insert dnointernalcardinfo(cardvesting,cardno,seqno,ineritemno,entrycount,usecount,lastcount,entryamt,useamt,lastamt,entryremark) " +
									" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getPicno()+"',"+i+"," +
											" '"+lsdnointernalcardinfo.get(i).getIneritemno()+"',"+lsdnointernalcardinfo.get(i).getEntrycount()+",0,"+lsdnointernalcardinfo.get(i).getEntrycount()+"," +
											" "+lsdnointernalcardinfo.get(i).getEntryamt()+",0,"+lsdnointernalcardinfo.get(i).getEntryamt()+",'"+lsdnointernalcardinfo.get(i).getEntryremark()+"' ) ";
						}
						return this.amn_Dao.executeSql(strSql);
					}
				}
			}
			else if(curSendpointcard.getSendpicflag()==4)//项目抵用券
			{
				 if(nBillType==6 || nBillType==7 )
				 {
					 if(strJsonDyqParam!= null && !strJsonDyqParam.equals("")  && strTmkJson!= null && !strTmkJson.equals(""))
					 {
						 List<NointernalcardinfoId> lsCardno=this.getDataTool().loadDTOList(strJsonDyqParam, NointernalcardinfoId.class);
						 if(lsCardno!=null && lsCardno.size()>0)
						 {
								for(int i=0;i<lsCardno.size();i++)
								{
									if(!CommonTool.FormatString(lsCardno.get(i).getCardno()).equals(""))
									{
										List<Dnointernalcardinfo> lsdnointernalcardinfo=this.getDataTool().loadDTOList(strTmkJson, Dnointernalcardinfo.class);
										if(lsdnointernalcardinfo!=null && lsdnointernalcardinfo.size()>0)
										{
											strSql=strSql+" update nointernalcardinfo set cardvesting='"+CommonTool.getLoginInfo("COMPID")+"' , cardstate=1 where cardno='"+CommonTool.FormatString(lsCardno.get(i).getCardno())+"' ";
											for(int j=0;j<lsdnointernalcardinfo.size();j++)
											{
												strSql=strSql+" insert dnointernalcardinfo(cardvesting,cardno,seqno,ineritemno,entrycount,usecount,lastcount,entryamt,useamt,lastamt,entryremark) " +
														" values('"+CommonTool.getLoginInfo("COMPID")+"','"+CommonTool.FormatString(lsCardno.get(i).getCardno())+"',"+j+"," +
																" '"+lsdnointernalcardinfo.get(j).getIneritemno()+"',"+lsdnointernalcardinfo.get(j).getEntrycount()+",0,"+lsdnointernalcardinfo.get(j).getEntrycount()+"," +
																" "+lsdnointernalcardinfo.get(j).getEntryamt()+",0,"+lsdnointernalcardinfo.get(j).getEntryamt()+",'"+lsdnointernalcardinfo.get(j).getEntryremark()+"' ) ";
											}
										
										}
									}
								}
						}
						 if(!strSql.equals(""))
							 return this.amn_Dao.executeSql(strSql);
						 else
							 return true;
					 }
				 }
				 else
				 {
					 if(strTmkJson!= null && !strTmkJson.equals(""))
					 {
							List<Dnointernalcardinfo> lsdnointernalcardinfo=this.getDataTool().loadDTOList(strTmkJson, Dnointernalcardinfo.class);
							if(lsdnointernalcardinfo!=null && lsdnointernalcardinfo.size()>0)
							{
								strSql=strSql+" update nointernalcardinfo set cardvesting='"+CommonTool.getLoginInfo("COMPID")+"' ,cardstate=1,createtype=1 where cardno='"+CommonTool.FormatString(curSendpointcard.getPicno())+"' ";
								for(int i=0;i<lsdnointernalcardinfo.size();i++)
								{
									strSql=strSql+" insert dnointernalcardinfo(cardvesting,cardno,seqno,ineritemno,entrycount,usecount,lastcount,entryamt,useamt,lastamt,entryremark) " +
											" values('"+CommonTool.getLoginInfo("COMPID")+"','"+curSendpointcard.getPicno()+"',"+i+"," +
													" '"+lsdnointernalcardinfo.get(i).getIneritemno()+"',"+lsdnointernalcardinfo.get(i).getEntrycount()+",0,"+lsdnointernalcardinfo.get(i).getEntrycount()+"," +
													" "+lsdnointernalcardinfo.get(i).getEntryamt()+",0,"+lsdnointernalcardinfo.get(i).getEntryamt()+",'"+lsdnointernalcardinfo.get(i).getEntryremark()+"' ) ";
								}
								return this.amn_Dao.executeSql(strSql);
							}
						}
				 }
				
			}
			else if(curSendpointcard.getSendpicflag()==5)//无条件赠送项目抵用券
			{
				 if(strJsonDyqParam!= null && !strJsonDyqParam.equals("")  && strTmkJson!= null && !strTmkJson.equals(""))
				 {
					 List<NointernalcardinfoId> lsCardno=this.getDataTool().loadDTOList(strJsonDyqParam, NointernalcardinfoId.class);
					 if(lsCardno!=null && lsCardno.size()>0)
					 {
							for(int i=0;i<lsCardno.size();i++)
							{
								if(!CommonTool.FormatString(lsCardno.get(i).getCardno()).equals(""))
								{
									//List<Dnointernalcardinfo> lsdnointernalcardinfo=this.getDataTool().loadDTOList(strTmkJson, Dnointernalcardinfo.class);
									//if(lsdnointernalcardinfo!=null && lsdnointernalcardinfo.size()>0)
									//{
									
										strSql=strSql+" update nointernalcardinfo set cardvesting='"+CommonTool.getLoginInfo("COMPID")+"' , cardstate=1,createtype=1,lastvalidate='"+CommonTool.datePlusDay(CommonTool.getCurrDate(), 90)+"' where cardno='"+CommonTool.FormatString(lsCardno.get(i).getCardno())+"' ";
										//for(int j=0;j<lsdnointernalcardinfo.size();j++)
										//{
											strSql=strSql+" insert dnointernalcardinfo(cardvesting,cardno,seqno,ineritemno,entrycount,usecount,lastcount,entryamt,useamt,lastamt,entryremark) " +
											" select '"+CommonTool.getLoginInfo("COMPID")+"','"+CommonTool.FormatString(lsCardno.get(i).getCardno())+"',row_number() over(order by packageprono desc)," +
											" packageprono,packageprocount,0,packageprocount," +
											" packageproamt,0,packageproamt,''  from dmpackageinfo b where b.packageno='"+lsCardno.get(i).getCreatecardtype()+"'  and compid='"+CommonTool.getLoginInfo("COMPID")+"' ";		
											//" values('"+CommonTool.getLoginInfo("COMPID")+"','"+CommonTool.FormatString(lsCardno.get(i).getCardno())+"',"+j+"," +
														//	" '"+lsdnointernalcardinfo.get(j).getIneritemno()+"',"+lsdnointernalcardinfo.get(j).getEntrycount()+",0,"+lsdnointernalcardinfo.get(j).getEntrycount()+"," +
														//	" "+lsdnointernalcardinfo.get(j).getEntryamt()+",0,"+lsdnointernalcardinfo.get(j).getEntryamt()+",'"+lsdnointernalcardinfo.get(j).getEntryremark()+"' ) ";
										//}
									
									//}
								}
							}
					}
					 if(!strSql.equals(""))
						 return this.amn_Dao.executeSql(strSql);
					 else
						 return true;
				 }
			}
			else if(curSendpointcard.getSendpicflag()==6 || curSendpointcard.getSendpicflag()==7 || curSendpointcard.getSendpicflag()==8|| curSendpointcard.getSendpicflag()==10)//2014年大礼包活动
			 {
				 if(strTmkJson!= null && !strTmkJson.equals(""))
				 {
					 String cardvesting=loadGS(curSendpointcard.getSourcecardno());
					 double maxSeq=loadMaxSeq(curSendpointcard.getSourcecardno());
					 double maxAccountSeq=this.loadAccountSeq(curSendpointcard.getSourcecardno());
					 double sumamt=0;
						List<Dnointernalcardinfo> lsdnointernalcardinfo=this.getDataTool().loadDTOList(strTmkJson, Dnointernalcardinfo.class);
						if(lsdnointernalcardinfo!=null && lsdnointernalcardinfo.size()>0)
						{
							for(Dnointernalcardinfo dnointernalcardinfo:lsdnointernalcardinfo)
							{
								strSql+=" insert into cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,yearsz,createbilltype,createbillno,createseqno)" +
										"values('"+cardvesting+"','"+curSendpointcard.getSourcecardno()+"','"+dnointernalcardinfo.getIneritemno()+"',"+maxSeq+",0,"+dnointernalcardinfo.getEntrycount()+",0,"+dnointernalcardinfo.getEntrycount()+"," +
												""+dnointernalcardinfo.getEntryamt()+",0,"+dnointernalcardinfo.getEntryamt()+",1,'"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getSourcebillid()+"',"+nBillType+")";
								maxSeq++;
								
								sumamt+=CommonTool.FormatBigDecimal(dnointernalcardinfo.getEntryamt()).doubleValue();
							}
							
							if(sumamt>0)
							{
								strSql+=" insert into cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)" +
										" select '"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getSourcecardno()+"',4,"+maxAccountSeq+",0,"+sumamt+",'ZS','"+curSendpointcard.getId().getSendbillid()+"','"+CommonTool.getCurrDate()+"',accountbalance " +
												" from cardaccount where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype=4 ";
								
								
								strSql+=" update cardaccount set accountbalance=isnull(accountbalance,0)+"+sumamt+" where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype=4";
								
								
							}
						}
				}
				if(CommonTool.checkStr(strSql))
				{
					
					return amn_Dao.executeSql(strSql);
				}
				else 
				{
					return false;
				}
			 }
			else if(curSendpointcard.getSendpicflag()==9)
			{
				//double maxAccountSeq=this.loadAccountSeq(curSendpointcard.getSourcecardno());
				int amt=0;
				if(curSendpointcard.getSourceamt().intValue()>=6800 && curSendpointcard.getSourceamt().intValue()<16800)
				{
					amt=680;
				}
				else if(curSendpointcard.getSourceamt().intValue()>=16800)
				{
					amt=1680;
				}
				String phone=loadCardPhone(curSendpointcard.getSourcecardno());
				strSql+=" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate,uespassward,phone,billid,billtype) ";
				strSql+=" values('"+CommonTool.getLoginInfo("COMPID")+"','"+curSendpointcard.getFirstdateno()+"',1,'"+amt+"',2,0,1,'20150301','20150331','20150301',0,'"+phone+"','"+curSendpointcard.getSourcebillid()+"','"+nBillType+"')";
				/*strSql+=" insert into cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)" +
						" select '"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getSourcecardno()+"',4,"+maxAccountSeq+",0,"+sumamt+",'ZS','"+curSendpointcard.getId().getSendbillid()+"','"+CommonTool.getCurrDate()+"',accountbalance " +
								" from cardaccount where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype=4 ";*/
				if(CommonTool.checkStr(phone))
				{
					Companyinfo companyinfo=loadCompanyinfo(CommonTool.getLoginInfo("COMPID"));
					this.sysSendMsg.sendFastMsg(CommonTool.getLoginInfo("COMPID"), phone, "您好,阿玛尼赠送的原价抵用券,券号:"+curSendpointcard.getFirstdateno()+",金额:"+amt+",请于20150331前致电"+companyinfo.getCompphone()+"预约消费,地址:"+companyinfo.getCompaddress());
				}
				try {
					return amn_Dao.executeSql(strSql);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
			else if(curSendpointcard.getSendpicflag()==3)//赠送现金抵用券
			{
				double faceamt=200;
				if(CommonTool.FormatString(curSendpointcard.getFirstdateno()).substring(0,3).equals("014"))
					faceamt=400;
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
						" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"01"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20140101",31)+"','"+CommonTool.datePlusDay("20140101",31)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"02"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20140201",28)+"','"+CommonTool.datePlusDay("20140201",28)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"03"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20140301",31)+"','"+CommonTool.datePlusDay("20140301",31)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"04"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20140401",30)+"','"+CommonTool.datePlusDay("20140401",30)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"05"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20140501",31)+"','"+CommonTool.datePlusDay("20140501",31)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"06"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20140601",30)+"','"+CommonTool.datePlusDay("20140601",30)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"07"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20140701",31)+"','"+CommonTool.datePlusDay("20140701",31)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"08"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20140801",31)+"','"+CommonTool.datePlusDay("20140801",31)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"09"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20140901",30)+"','"+CommonTool.datePlusDay("20140901",30)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"10"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20141001",31)+"','"+CommonTool.datePlusDay("20141001",31)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"11"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20141101",30)+"','"+CommonTool.datePlusDay("20141101",30)+"','20140101')";
				strSql=strSql+" insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate)" +
				" values('"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getFirstdateno().substring(0,3)+"12"+curSendpointcard.getFirstdateno().substring(5,8)+"',1,"+faceamt+",2,1,1,'"+CommonTool.datePlusDay("20141201",31)+"','"+CommonTool.datePlusDay("20141201",31)+"','20140101')";
				return this.amn_Dao.executeSql(strSql);
			}else{
				 if(strTmkJson!= null && !strTmkJson.equals("")){
					 String cardvesting=CommonTool.getLoginInfo("COMPID");//loadGS(curSendpointcard.getSourcecardno());
					 double maxSeq=loadMaxSeq(curSendpointcard.getSourcecardno());
					 double maxAccountSeq=this.loadAccountSeq(curSendpointcard.getSourcecardno());
					 double sumamt=0;
						List<Dnointernalcardinfo> lsdnointernalcardinfo=this.getDataTool().loadDTOList(strTmkJson, Dnointernalcardinfo.class);
						if(lsdnointernalcardinfo!=null && lsdnointernalcardinfo.size()>0)
						{
							int i=0;
							for(Dnointernalcardinfo dnointernalcardinfo:lsdnointernalcardinfo)
							{
								String[] give = StringUtils.split(dnointernalcardinfo.getPackageNo(), "@");//赠送活动唯一标识&赠送类型1:套餐A、2:套餐B、3:产品C
								if(StringUtils.equals("3", give[1])){//赠送的产品插入出库记录
									strSql+=" insert dgoodsstockinfo(changecompid,changetype,changebillno,changeseqno,changegoodsno,standcount,producedate,changecount)" +
											 " values(	'"+cardvesting+"','3'," +
											 			" '"+CommonTool.FormatString(curSendpointcard.getId().getSendbillid())+"'," +
									 				" "+ (++i) +"," +
									 				" '"+dnointernalcardinfo.getIneritemno()+"'," +
									 				" "+CommonTool.FormatBigDecimal(dnointernalcardinfo.getEntrycount())+" ," +
									 				" '"+CommonTool.getCurrDate()+"'," +
									 				" "+CommonTool.FormatBigDecimal(dnointernalcardinfo.getEntrycount());
								}else{
									strSql+=" insert into cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,yearsz,createbilltype,createbillno,createseqno,activinid)" +
											"values('"+cardvesting+"','"+curSendpointcard.getSourcecardno()+"','"+dnointernalcardinfo.getIneritemno()+"',"+maxSeq+",0,"+dnointernalcardinfo.getEntrycount()+",0,"+dnointernalcardinfo.getEntrycount()+"," +//72:赠送套餐A+设定提成、73:套餐A+原价卡提；82:套餐B+设定提成、83:套餐B+原价卡提
											""+dnointernalcardinfo.getEntryamt()+",0,"+dnointernalcardinfo.getEntryamt()+",1,'"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getSourcebillid()+"',"+ (StringUtils.equals("1", give[1])?("7"+give[2]):("8"+give[2])) +", '"+ give[0] +"')";
									maxSeq++;
									
									sumamt+=CommonTool.FormatBigDecimal(dnointernalcardinfo.getEntryamt()).doubleValue();
								}
							}
							
							if(sumamt>0)
							{
								strSql+=" insert into cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)" +
										" select '"+curSendpointcard.getId().getSendcompid()+"','"+curSendpointcard.getSourcecardno()+"',4,"+maxAccountSeq+",0,"+sumamt+",'ZS','"+curSendpointcard.getId().getSendbillid()+"','"+CommonTool.getCurrDate()+"',accountbalance " +
												" from cardaccount where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype=4 ";
								strSql+=" update cardaccount set accountbalance=isnull(accountbalance,0)+"+sumamt+" where cardno='"+curSendpointcard.getSourcecardno()+"' and accounttype=4";
							}
							if(i>0){//入出库主档
								strSql+=" insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)" +
								" values('"+CommonTool.FormatString(curSendpointcard.getId().getSendcompid())+"','3', " +
										" '"+CommonTool.FormatString(curSendpointcard.getId().getSendbillid())+"'," +
										" '"+CommonTool.getCurrDate()+"'," +
										" '"+CommonTool.getCurrTime()+"'," +
										" '"+CommonTool.FormatString(CommonTool.FormatString(this.getDataTool().loadSysParam(curSendpointcard.getId().getSendcompid(), "SP013")))+"'," +
										" "+CommonTool.FormatInteger(1)+"," +
										" '"+CommonTool.FormatString(CommonTool.getLoginInfo("USERID"))+"',1) ";
							}
						}
				}
				if(CommonTool.checkStr(strSql)){
					return amn_Dao.executeSql(strSql);
				}
				else{
					return false;
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
	
	public double loadAccountSeq(String strCardNo)
	{
		String strSql="select max(changeseqno)+1 from cardaccountchangehistory where changecardno='"+strCardNo+"' and changeaccounttype=4";
		double maxSeq=0;
		ResultSet rs=null;
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					maxSeq=rs.getDouble(1);
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
		return maxSeq;
	}
	
	public double loadMaxSeq(String strCardNo)
	{
		String strSql="select max(proseqno)+1 from cardproaccount where cardno='"+strCardNo+"'";
		double maxSeq=0;
		ResultSet rs=null;
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					maxSeq=rs.getDouble(1);
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
		return maxSeq;
	}
	
	public String loadGS(String strCardNo)
	{
		String strSql="select cardvesting from cardinfo where cardno='"+strCardNo+"'";
		String cardvesting="";
		ResultSet rs=null;
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					cardvesting=rs.getString("cardvesting");
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
		return cardvesting;
	}

	
	public List<Mpackageinfo> loadMpackageinfo(String strCurCompId)
	{
			try
			{
				String strSql=" From Mpackageinfo  mpackageinfo where compid='"+strCurCompId+"' and isnull(usedate,'')>'"+CommonTool.getCurrDate()+"' and isnull(usetype,1)=3 ";
				return (List<Mpackageinfo>)this.amn_Dao.findByHql(strSql);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	public NointernalcardinfoId loadDyCardInfoByNo(String strCompid,String strCardNo)
	{
		try
		{
			String strSql="select cardno,packageno,packagename from nointernalcardinfo a,mpackageinfo b " +
					" where a.cardno='"+strCardNo+"' and ISNULL(a.createcardtype,'')=b.packageno and ISNULL(b.compid,'')='"+strCompid+"' and cardtype=1 and cardstate=0 and carduseflag=1";
			
			AnlyResultSet<NointernalcardinfoId > analysis = new AnlyResultSet<NointernalcardinfoId >()
	    	{
	    			public NointernalcardinfoId  anlyResultSet(ResultSet rs)
	    			{
	    				NointernalcardinfoId returnValue = new NointernalcardinfoId();
	    				try
	    				{
		    				if(rs != null && rs.next()==true)
		    				{
		    					returnValue.setCardno(rs.getString("cardno"));
		    					returnValue.setCreatecardtype(rs.getString("packageno"));
		    					returnValue.setCreatecardtypename(rs.getString("packagename"));
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
	    	NointernalcardinfoId record =(NointernalcardinfoId )this.amn_Dao.executeQuery_ex(strSql,analysis);
	    	analysis=null;
	    	return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public String loadCardPhone(String strCardNo)
	{
		String strSql="select membermphone from memberinfo where memberno='"+strCardNo+"'";
		String phone="";
		ResultSet rs=null;
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					phone=rs.getString("membermphone");
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
		return phone;
	}
	
	public Companyinfo loadCompanyinfo(String strCompId)
	{
		String strSql="select * from companyinfo where compno='"+strCompId+"'";
		Companyinfo companyinfo=null;
		ResultSet rs=null;
		try
		{
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					companyinfo=new Companyinfo();
					companyinfo.setCompphone(rs.getString("compphone"));
					companyinfo.setCompaddress(rs.getString("compaddress"));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return companyinfo;
	}
	
	/**
	 * 获取活动1:卡异动、2:疗程、3:产品
	 * @param strCompId
	 * @param nBillType
	 * @param strSearchDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mactivityinfo> loadActivity(String strCompId, int nBillType, String strSearchDate){
		String hql="from Mactivityinfo where CONVERT(varchar(100), '"+ strSearchDate +"', 112) between startdate and enddate and activstate=1 "+
				"and activcompid='"+strCompId+"' and activtype="+(nBillType==7?"2":(nBillType==8?"3":"1"));
		return this.amn_Dao.findByHql(hql);
	}
	
	/**
	 * 获取活动明细
	 * @param strCompId
	 * @param activinid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Mactivityinfo loadActivity(String strCompId, String activinid){
		String hql="from Mactivityinfo where activcompid='"+ strCompId +"' and activinid='"+ activinid +"'";
		List<Mactivityinfo> list = this.amn_Dao.findByHql(hql);
		Mactivityinfo mactivityinfo = list==null||list.size()==0 ? new Mactivityinfo() : list.get(0);
		//活动赠送主档
		hql = "from Mactivitygive where activcompid='"+ strCompId +"' and activinid='"+ activinid +"'";
		List<Mactivitygive> mgive = this.amn_Dao.findByHql(hql);
		int activorand = list==null||list.size()==0 ? 1 : mgive.get(0).getActivorand();
		mactivityinfo.setActivstate(activorand);//赠送主档逻辑字段临时使用活动主档状态字段代替
		return mactivityinfo;
	}
	
	/**
	 * 获取活动赠送明细
	 * @param activinid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Dactivitygive> loadGiveInfo(String strCompId, String activinid, int activtype, int activorand){
		String hql="from Dactivitygive where activcompid='"+ strCompId +"' and activinid='"+ activinid +"' and activtype="+ activtype;
		List<Dactivitygive> list = this.amn_Dao.findByHql(hql);
		if(activorand==2){//如果逻辑为且，则将产品附加到项目中显示
			hql="from Dactivitygive where activcompid='"+ strCompId +"' and activinid='"+ activinid +"' and activtype=3";
			List<Dactivitygive> goods = this.amn_Dao.findByHql(hql);
			list.addAll(goods);
		}
		return list;
	}
	
	/**
	 * 校验疗程项目列表赠送条件
	 * @param activinid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean validateItems(String strCompId, String strCurBillId, String activinid){
		String hql="from Dproexchangeinfo a where a.id.changecompid='"+ strCompId +"' and a.id.changebillid='"+ strCurBillId +"'";
		List<Dproexchangeinfo> list = this.amn_Dao.findByHql(hql);
		//活动明细表
		hql = "from Dactivityinfo where activinid='"+ activinid +"'";
		List<Dactivityinfo> dactivity = this.amn_Dao.findByHql(hql);
		int count=0;
		for (Dactivityinfo dactivityinfo : dactivity) {
			String activno = dactivityinfo.getActivno();
			for (Dproexchangeinfo dproexchangeinfo : list) {
				if(StringUtils.equals(activno, dproexchangeinfo.getChangeproid())){
					if(dproexchangeinfo.getProcount().intValue()>= dactivityinfo.getActivcount()){
						count++;
					}
				}
			}
		}
		return count==dactivity.size();
	}
	
	/**
	 * 校验产品列表赠送条件
	 * @param activinid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean validateGoods(String strCompId, String strCurBillId, String activinid){
		String hql="from Dconsumeinfo a where a.id.cscompid='"+ strCompId +"' and a.id.csbillid='"+ strCurBillId +"'";
		List<Dconsumeinfo> list = this.amn_Dao.findByHql(hql);
		//活动明细表
		hql = "from Dactivityinfo where activinid='"+ activinid +"'";
		List<Dactivityinfo> dactivity = this.amn_Dao.findByHql(hql);
		int count=0;
		for (Dactivityinfo dactivityinfo : dactivity) {
			String activno = dactivityinfo.getActivno();
			for (Dconsumeinfo dconsumeinfo : list) {
				if(StringUtils.equals(activno, dconsumeinfo.getCsitemno())){
					if(dconsumeinfo.getCsitemcount().intValue()>= dactivityinfo.getActivcount()){
						count++;
					}
				}
			}
		}
		return count==dactivity.size();
	}
	
	//美容积分类型时，获取充值金额
	public Map<String, String> loadFaceamt(String strCompId, String strCurCardNo, String strSearchDate){
		StringBuffer sql = new StringBuffer();
		Map<String, String> result = new HashMap<String, String>();
		String payamt="0";
		sql.append("WITH cardamt as( ")//充值，转卡，并卡金额
		.append("SELECT payamt=sum(isnull(payamt,0)) FROM msalecardinfo WITH(nolock), dpayinfo with(nolock) ") 
		.append("WHERE salecompid='"+strCompId+"' AND financedate='"+strSearchDate+"' AND salecardno='"+ strCurCardNo +"'") 
		.append("AND isnull(sendpointflag,0)=0 and salecompid=paycompid and salebillid=paybillid and paybilltype='SK' ")
		.append("group by salecardno UNION ALL ") 
		.append("SELECT payamt=sum(isnull(payamt,0)) FROM mcardrechargeinfo WITH(nolock), dpayinfo with(nolock) ") 
		.append("WHERE rechargecompid='"+strCompId+"' AND financedate='"+strSearchDate+"' AND rechargecardno='"+ strCurCardNo +"'") 
		.append("AND isnull(sendpointflag,0)=0 and rechargecompid=paycompid and rechargebillid=paybillid and paybilltype='CZ' ") 
		.append("group by rechargecardno UNION ALL ") 
		.append("SELECT payamt=sum(isnull(payamt,0)) FROM mcardchangeinfo WITH(nolock), dpayinfo with(nolock) ") 
		.append("WHERE changecompid='"+strCompId+"' AND financedate='"+strSearchDate+"' AND changeaftercardno='"+ strCurCardNo +"'")
		.append("AND changetype in(0,1,2,6,7) AND isnull(sendpointflag,0)=0 and changecompid=paycompid and changebillid=paybillid and paybilltype='ZK' ")
		.append("group by changeaftercardno) ")
		.append("select payamt=sum(isnull(payamt,0)) from cardamt");
		ResultSet rs=null;
		try{
			rs=amn_Dao.executeQuery(sql.toString());
			if(rs.next()){
				payamt = ObjectUtils.toString(rs.getString("payamt"), "0");
			}
			result.put("cardamt", payamt);
			result.put("yearamt", "0");
			if(Float.parseFloat(payamt)>0){
				sql = new StringBuffer();//201048胸部年卡金额
				sql.append("select payamt=sum(isnull(amt,0)) from yearsellinfo a with(nolock), yearselldetal b with(nolock) where a.compid=b.compid and a.billid=b.billid ")
				.append("and b.packno in('201028','201029','201030','201048','201004','201005','201006','202009','202010','202011') and a.compid='"+strCompId+"' and a.accountdate='"+strSearchDate+"' and a.cardno='"+ strCurCardNo +"'")
				.append("group by a.compid,cardno");
				rs=amn_Dao.executeQuery(sql.toString());
				if(rs.next()){
					result.put("yearamt", ObjectUtils.toString(rs.getString("payamt"), "0"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
}
