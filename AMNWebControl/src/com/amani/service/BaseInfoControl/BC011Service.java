package com.amani.service.BaseInfoControl;

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

import com.amani.model.Promotionsinfo;
import com.amani.model.PromotionsinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class BC011Service  extends AMN_ModuleService{
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
			Promotionsinfo currecord=(Promotionsinfo)curMaster;
			String strSql=" update promotionsinfo set invalid =1 where compid='"+currecord.getId().getCompid()+"' and  billid='"+currecord.getId().getBillid()+"' ";
			this.amn_Dao.executeSql(strSql);
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
	
	
	public Promotionsinfo addPromotionsinfo(String strCompId)
	{
		try
		{
			Promotionsinfo newRecord=new Promotionsinfo();
			newRecord.setId(new PromotionsinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"promotionsinfo", "billid", "SP007")));
			newRecord.setPromotionsstate(0);
			return newRecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return new Promotionsinfo();
		}
	}

	public List<Promotionsinfo> loadPromotionsinfos(String strCompId)
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
			//项目促销
			String strSql=" select compid,compname,billid,promotionstype,promotionscode,promotionsname=prjname,promotionsstore,promotionsvalue,startdate,enddate,promotionsreason,promotionsstate,promotionsempid,promotionsdate" +
					" from promotionsinfo,companyinfo ,projectinfo,compchaininfo" +
					" where promotionsstore='"+strCompId+"'  and compno= promotionsstore and promotionstype=1 and isnull(invalid,0)=0 " +
					"   and prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and prjno=promotionscode and prisource=curcomp and relationcomp=promotionsstore ";
			strSql=strSql+" union all ";
			
			strSql=strSql+" select compid,compname,billid,promotionstype,promotionscode,promotionsname=cardtypename,promotionsstore,promotionsvalue,startdate,enddate,promotionsreason,promotionsstate,promotionsempid,promotionsdate " +
			" from promotionsinfo,companyinfo ,cardtypenameinfo " +
			" where promotionsstore='"+strCompId+"'  and compno= promotionsstore and promotionstype in (2,3) and isnull(invalid,0)=0 " +
			"   and  cardtypeno=promotionscode   ";
			
			
			strSql=strSql+" union all ";
			strSql=strSql+" select compid,compname,billid,promotionstype,promotionscode,promotionsname=cardtypename,promotionsstore,promotionsvalue,startdate,enddate,promotionsreason,promotionsstate,promotionsempid,promotionsdate " +
			" from promotionsinfo,companyinfo ,cardinfo,cardtypenameinfo" +
			" where compid='"+strCompId+"' and compno= promotionsstore and promotionstype in (4,5) and isnull(invalid,0)=0 " +
			"  and cardno=promotionscode and cardtype=cardtypeno  ";
			
			
			strSql=strSql+" union all ";
			strSql=strSql+" select compid,compname,billid,promotionstype,promotionscode,promotionsname=parentcodevalue,promotionsstore,promotionsvalue,startdate,enddate,promotionsreason,promotionsstate,promotionsempid,promotionsdate " +
			" from promotionsinfo,companyinfo ,commoninfo " +
			" where compid='"+strCompId+"' and compno= promotionsstore and promotionstype in (6,7) and isnull(invalid,0)=0 " +
			"  and infotype='XMLB' and parentcodekey=promotionscode  order by startdate desc ";
			
			AnlyResultSet<List<Promotionsinfo>> analysis = new AnlyResultSet<List<Promotionsinfo>>()
			{
				public List<Promotionsinfo> anlyResultSet(ResultSet rs)
				{
					List<Promotionsinfo> returnValue = new ArrayList();
					Promotionsinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Promotionsinfo();
							record.setId(new PromotionsinfoId(CommonTool.FormatString(rs.getString("compid")),CommonTool.FormatString(rs.getString("billid"))));
							record.setBcompid(CommonTool.FormatString(rs.getString("compid")));
						
							record.setBbillid(CommonTool.FormatString(rs.getString("billid")));
							record.setPromotionstype(CommonTool.FormatInteger(rs.getInt("promotionstype")));
							record.setPromotionscode(CommonTool.FormatString(rs.getString("promotionscode")));
							record.setPromotionsname(CommonTool.FormatString(rs.getString("promotionsname")));
							record.setBpromotionsstore(CommonTool.FormatString(rs.getString("promotionsstore")));
							record.setPromotionsstore(CommonTool.FormatString(rs.getString("promotionsstore"))+"["+CommonTool.FormatString(rs.getString("compname"))+"]");
							record.setPromotionsstorename(CommonTool.FormatString(rs.getString("compname")));
							record.setPromotionsvalue(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("promotionsvalue"))));
							record.setStartdate(CommonTool.getDateMask(rs.getString("startdate")));
							record.setEnddate(CommonTool.getDateMask(rs.getString("enddate")));
							record.setPromotionsreason(CommonTool.FormatString(rs.getString("promotionsreason")));
							record.setPromotionsstate(CommonTool.FormatInteger(rs.getInt("promotionsstate")));
							record.setPromotionsempid(CommonTool.FormatString(rs.getString("promotionsempid")));
							record.setPromotionsdate(CommonTool.FormatString(rs.getString("promotionsdate")));
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
			return (List<Promotionsinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Promotionsinfo loadPromotionsinfosByBillId(String strCompId,String strBillId,String strPromotionCompId)
	{
		try
		{
			String strModeId=this.getDataTool().loadSysParam(strPromotionCompId,"SP059");
		     String strFristModeId="";//第一级模板Id
			 String strSecondModeId="";//第2级模板ID
			 String strThirthModeId="";//第三级模板Id
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.getDataTool().loadCompLvl(strPromotionCompId);
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
			//项目促销
			String strSql=" select compid,compname,billid,promotionstype,promotionscode,promotionsname=prjname,promotionsstore,promotionsvalue,startdate,enddate,promotionsreason,promotionsstate,promotionsempid,promotionsdate,invalid" +
					" from promotionsinfo,companyinfo ,projectinfo,compchaininfo" +
					" where compid='"+strCompId+"' and billid='"+strBillId+"' and compno= promotionsstore and promotionstype=1 and isnull(invalid,0)=0 " +
					"  and prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and prjno=promotionscode and prisource=curcomp and relationcomp=promotionsstore ";
			strSql=strSql+" union all ";
			
			strSql=strSql+" select compid,compname,billid,promotionstype,promotionscode,promotionsname=cardtypename,promotionsstore,promotionsvalue,startdate,enddate,promotionsreason,promotionsstate,promotionsempid,promotionsdate,invalid" +
			" from promotionsinfo,companyinfo ,cardtypenameinfo" +
			" where compid='"+strCompId+"' and billid='"+strBillId+"' and compno= promotionsstore and promotionstype in (2,3) and isnull(invalid,0)=0 " +
			"    and  cardtypeno=promotionscode  ";
			
			strSql=strSql+" union all ";
			strSql=strSql+" select compid,compname,billid,promotionstype,promotionscode,promotionsname=cardtypename,promotionsstore,promotionsvalue,startdate,enddate,promotionsreason,promotionsstate,promotionsempid,promotionsdate,invalid" +
			" from promotionsinfo,companyinfo ,cardinfo,cardtypenameinfo" +
			" where compid='"+strCompId+"' and billid='"+strBillId+"' and compno= promotionsstore and promotionstype in (4,5) and isnull(invalid,0)=0 " +
			"  and cardno=promotionscode and cardtype=cardtypeno ";
			
			strSql=strSql+" union all ";
			strSql=strSql+" select compid,compname,billid,promotionstype,promotionscode,promotionsname=parentcodevalue,promotionsstore,promotionsvalue,startdate,enddate,promotionsreason,promotionsstate,promotionsempid,promotionsdate,invalid" +
			" from promotionsinfo,companyinfo ,commoninfo " +
			" where compid='"+strCompId+"' and billid='"+strBillId+"' and compno= promotionsstore and promotionstype in (6,7) and isnull(invalid,0)=0 " +
			" and infotype='XMLB' and parentcodekey=promotionscode ";
			
			AnlyResultSet<Promotionsinfo> analysis = new AnlyResultSet<Promotionsinfo>()
			{
				public Promotionsinfo anlyResultSet(ResultSet rs)
				{
					Promotionsinfo record = new Promotionsinfo();
					try
					{
						if(rs != null && rs.next()==true)
						{
							record.setId(new PromotionsinfoId(CommonTool.FormatString(rs.getString("compid")),CommonTool.FormatString(rs.getString("billid"))));
							record.setBcompid(CommonTool.FormatString(rs.getString("compid")));
						
							record.setBbillid(CommonTool.FormatString(rs.getString("billid")));
							record.setPromotionstype(CommonTool.FormatInteger(rs.getInt("promotionstype")));
							record.setPromotionscode(CommonTool.FormatString(rs.getString("promotionscode")));
							record.setPromotionsname(CommonTool.FormatString(rs.getString("promotionsname")));
							record.setPromotionsstore(CommonTool.FormatString(rs.getString("promotionsstore")));
							record.setPromotionsstorename(CommonTool.FormatString(rs.getString("compname")));
							record.setPromotionsvalue(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("promotionsvalue"))));
							record.setStartdate(CommonTool.getDateMask(rs.getString("startdate")));
							record.setEnddate(CommonTool.getDateMask(rs.getString("enddate")));
							record.setPromotionsreason(CommonTool.FormatString(rs.getString("promotionsreason")));
							record.setPromotionsstate(CommonTool.FormatInteger(rs.getInt("promotionsstate")));
							record.setPromotionsempid(CommonTool.FormatString(rs.getString("promotionsempid")));
							record.setPromotionsdate(CommonTool.FormatString(rs.getString("promotionsdate")));
							record.setInvalid(CommonTool.FormatInteger(rs.getInt("invalid")));
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
			return (Promotionsinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	public boolean postConfirmInfo(String strCompId,String strBillId)
	{
		try
		{
			String strSql="update  promotionsinfo set promotionsstate=1 where compid='"+strCompId+"' and billid='"+strBillId+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean  handCopyInfo(String strCompId,String strBillId,String strCopyInfo)
	{
		try
		{
			String strCurCompId="";
			String strCurBillId="";
			String strSql="";
			String[] lsComps=strCopyInfo.split(";");
			if(lsComps!=null && lsComps.length>0)
			{
				for(int i=0;i<lsComps.length;i++)
				{
					if(!lsComps[i].equals("") && !lsComps[i].equals(strCompId))
					{
						strCurCompId=lsComps[i];
						strCurBillId=this.dataTool.loadBillIdByRule(strCurCompId,"promotionsinfo", "billid", "SP007");
						strSql=strSql+" insert promotionsinfo(compid,billid,promotionstype,promotionscode,promotionsstore,promotionsvalue,startdate,enddate,promotionsreason,promotionsstate,invalid ) " +
								" select '"+strCurCompId+"','"+strCurBillId+"',promotionstype,promotionscode,'"+strCurCompId+"',promotionsvalue,startdate,enddate,promotionsreason,promotionsstate,invalid " +
								" from promotionsinfo where compid='"+strCompId+"' and billid='"+strBillId+"' ";
					}
				}
			}
			if(!strSql.equals(""))
				return this.amn_Dao.executeSql(strSql);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
}
