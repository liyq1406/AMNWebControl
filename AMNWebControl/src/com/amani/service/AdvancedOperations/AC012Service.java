package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Storeflowinfo;
import com.amani.model.StoreflowinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class AC012Service  extends AMN_ModuleService{
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
			Storeflowinfo currecord=(Storeflowinfo)curMaster;
			String strSql=" update storeflowinfo set invalid =1 where compid='"+currecord.getId().getCompid()+"' and  billid='"+currecord.getId().getBillid()+"' ";
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
	
	
	public Storeflowinfo addStoreflowinfo(String strCompId)
	{
		try
		{
			Storeflowinfo newRecord=new Storeflowinfo();
			newRecord.setId(new StoreflowinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"storeflowinfo", "billid", "SP007")));
			newRecord.setAppflowstate(0);
			return newRecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return new Storeflowinfo();
		}
	}

	public List<Storeflowinfo> loadStoreflowinfos(String strCompId)
	{
		try
		{
			 
			//项目申请
			String strSql=" select compid,compname,billid,appflowtype,appflowcode,appflowname=prjname,appflowstore,appflowvalue,startdate,enddate,appflowreason,appflowstate " +
					" from storeflowinfo,companyinfo ,projectnameinfo,compchaininfo " +
					" where appflowstore=relationcomp and curcomp='"+strCompId+"'  and compno= appflowstore and appflowtype=8 and isnull(invalid,0)=0 and  prjno=appflowcode and isnull(appflowstate,0)=0 ";
	
			strSql=strSql+" union ";
			
			//产品申请
			 strSql=strSql+" select compid,compname,billid,appflowtype,appflowcode,appflowname=goodsname,appflowstore,appflowvalue,startdate,enddate,appflowreason,appflowstate " +
					" from storeflowinfo,companyinfo ,goodsnameinfo,compchaininfo " +
					" where appflowstore=relationcomp and curcomp='"+strCompId+"'  and compno= appflowstore and appflowtype=9 and isnull(invalid,0)=0 and  goodsno=appflowcode and isnull(appflowstate,0)=0 ";
			
			 strSql=strSql+" union ";
			
			 //卡申请
			strSql=strSql+" select compid,compname,billid,appflowtype,appflowcode,appflowname=cardtypename,appflowstore,appflowvalue,startdate,enddate,appflowreason,appflowstate " +
			" from storeflowinfo,companyinfo ,cardinfo,cardtypenameinfo,compchaininfo" +
			" where appflowstore=relationcomp and curcomp='"+strCompId+"' and compno= appflowstore and appflowtype not in (8,9) and isnull(invalid,0)=0 and isnull(appflowstate,0)=0 " +
			"  and cardno=appflowcode and cardtype=cardtypeno  order by startdate desc ";
			
			AnlyResultSet<List<Storeflowinfo>> analysis = new AnlyResultSet<List<Storeflowinfo>>()
			{
				public List<Storeflowinfo> anlyResultSet(ResultSet rs)
				{
					List<Storeflowinfo> returnValue = new ArrayList();
					Storeflowinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Storeflowinfo();
							record.setId(new StoreflowinfoId(CommonTool.FormatString(rs.getString("compid")),CommonTool.FormatString(rs.getString("billid"))));
							record.setBcompid(CommonTool.FormatString(rs.getString("compid")));
							record.setBbillid(CommonTool.FormatString(rs.getString("billid")));
							record.setAppflowtype(CommonTool.FormatInteger(rs.getInt("appflowtype")));
							record.setAppflowcode(CommonTool.FormatString(rs.getString("appflowcode")));
							record.setAppflowname(CommonTool.FormatString(rs.getString("appflowname")));
							record.setAppflowstore(CommonTool.FormatString(rs.getString("appflowstore"))+"["+CommonTool.FormatString(rs.getString("compname"))+"]");
							record.setAppflowstorename(CommonTool.FormatString(rs.getString("compname")));
							record.setAppflowvalue(CommonTool.FormatString(rs.getString("appflowvalue")));
							record.setStartdate(CommonTool.getDateMask(rs.getString("startdate")));
							record.setEnddate(CommonTool.getDateMask(rs.getString("enddate")));
							record.setAppflowreason(CommonTool.FormatString(rs.getString("appflowreason")));
							record.setAppflowstate(CommonTool.FormatInteger(rs.getInt("appflowstate")));
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
			return (List<Storeflowinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Storeflowinfo loadStoreflowinfosByBillId(String strCompId,String strBillId)
	{
		try
		{
			
			//项目申请
			String strSql=" select compid,compname,billid,appflowtype,appflowcode,appflowname=prjname,appflowstore,appflowvalue,startdate,enddate,appflowreason,appflowstate,appflowconfirmempid,appflowconfirmdate,appflowcheckempid,appflowcheckdate " +
					" from storeflowinfo,companyinfo ,projectnameinfo " +
					" where compid='"+strCompId+"' and billid='"+strBillId+"'  and compno= appflowstore and appflowtype=8 and isnull(invalid,0)=0 and  prjno=appflowcode ";
	
			strSql=strSql+" union ";
			
			//产品申请
			 strSql=strSql+" select compid,compname,billid,appflowtype,appflowcode,appflowname=goodsname,appflowstore,appflowvalue,startdate,enddate,appflowreason,appflowstate,appflowconfirmempid,appflowconfirmdate,appflowcheckempid,appflowcheckdate  " +
					" from storeflowinfo,companyinfo ,goodsnameinfo " +
					" where compid='"+strCompId+"'  and billid='"+strBillId+"'  and compno= appflowstore and appflowtype=9 and isnull(invalid,0)=0 and  goodsno=appflowcode ";
			
			 strSql=strSql+" union ";
			
			 //卡申请
			strSql=strSql+" select compid,compname,billid,appflowtype,appflowcode,appflowname=cardtypename,appflowstore,appflowvalue,startdate,enddate,appflowreason,appflowstate,appflowconfirmempid,appflowconfirmdate,appflowcheckempid,appflowcheckdate  " +
			" from storeflowinfo,companyinfo ,cardinfo,cardtypenameinfo" +
			" where compid='"+strCompId+"'   and billid='"+strBillId+"' and compno= appflowstore and appflowtype not in (8,9) and isnull(invalid,0)=0 " +
			"  and cardno=appflowcode and cardtype=cardtypeno  order by startdate desc ";
			
			AnlyResultSet<Storeflowinfo> analysis = new AnlyResultSet<Storeflowinfo>()
			{
				public Storeflowinfo anlyResultSet(ResultSet rs)
				{
					Storeflowinfo record = new Storeflowinfo();
					try
					{
						if(rs != null && rs.next()==true)
						{
							record.setId(new StoreflowinfoId(CommonTool.FormatString(rs.getString("compid")),CommonTool.FormatString(rs.getString("billid"))));
							record.setBcompid(CommonTool.FormatString(rs.getString("compid")));
							record.setBbillid(CommonTool.FormatString(rs.getString("billid")));
							record.setAppflowtype(CommonTool.FormatInteger(rs.getInt("appflowtype")));
							record.setAppflowcode(CommonTool.FormatString(rs.getString("appflowcode")));
							record.setAppflowname(CommonTool.FormatString(rs.getString("appflowname")));
							record.setAppflowstore(CommonTool.FormatString(rs.getString("appflowstore")));
							record.setAppflowstorename(CommonTool.FormatString(rs.getString("compname")));
							record.setAppflowvalue(CommonTool.FormatString(rs.getString("appflowvalue")));
							record.setStartdate(CommonTool.getDateMask(rs.getString("startdate")));
							record.setEnddate(CommonTool.getDateMask(rs.getString("enddate")));
							record.setAppflowreason(CommonTool.FormatString(rs.getString("appflowreason")));
							record.setAppflowstate(CommonTool.FormatInteger(rs.getInt("appflowstate")));
							record.setAppflowconfirmempid(CommonTool.FormatString(rs.getString("appflowconfirmempid")));
							record.setAppflowconfirmdate(CommonTool.FormatString(rs.getString("appflowconfirmdate")));
							record.setAppflowcheckempid(CommonTool.FormatString(rs.getString("appflowcheckempid")));
							record.setAppflowcheckdate(CommonTool.FormatString(rs.getString("appflowcheckdate")));
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
			return (Storeflowinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
}
