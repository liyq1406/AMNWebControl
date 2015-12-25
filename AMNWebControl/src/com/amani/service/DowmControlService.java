package com.amani.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.dao.AMN_DaoImp;
import com.amani.model.Commoninfo;
import com.amani.model.CommoninfoId;
import com.amani.model.Companyinfo;
import com.amani.model.Compchainstruct;
import com.amani.model.Sysmodeinfo;
import com.amani.model.Sysrolemode;
import com.amani.model.Useroverall;
import com.amani.tools.CommonTool;
import com.amani.tools.UserInformation;
import com.opensymphony.xwork2.ActionContext;

@Service
public class DowmControlService  extends AMN_ModuleService{

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
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Commoninfo> loadCommoninfos()
	{
		String strSql=" From Commoninfo where codesource='D' ";
		return (List<Commoninfo>)this.amn_Dao.findByHql(strSql);
	}
	
	public List<Commoninfo> loadCommoninfosByGroup()
	{
		String strSql=" select infotype,infoname From commoninfo where codesource='D'  group by infotype,infoname ";
		AnlyResultSet<List<Commoninfo>> analysis = new AnlyResultSet<List<Commoninfo>>()
		{
			public List<Commoninfo> anlyResultSet(ResultSet rs)
			{
				List<Commoninfo> returnValue = new ArrayList();
				Commoninfo record=new Commoninfo();
				CommoninfoId recordId=new CommoninfoId();
				try
				{
					while(rs != null && rs.next()==true)
					{
						record=new Commoninfo();
						record.setInfoname(rs.getString("infoname"));
						recordId.setInfotype(rs.getString("infotype"));
						record.setId(recordId);
						record.setBinfotype(rs.getString("infotype"));
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
		return (List<Commoninfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
	}
	
	public List<Commoninfo> loadCommoninfosParentByGroup()
	{
		String strSql=" select infotype,infoname,parentcodekey,parentcodevalue,useflag From commoninfo where codesource='D'  group by infotype,infoname,parentcodekey,parentcodevalue,useflag order by infotype, parentcodekey ";
		AnlyResultSet<List<Commoninfo>> analysis = new AnlyResultSet<List<Commoninfo>>()
		{
			public List<Commoninfo> anlyResultSet(ResultSet rs)
			{
				List<Commoninfo> returnValue = new ArrayList();
				Commoninfo record=new Commoninfo();
				CommoninfoId recordId=new CommoninfoId();
				try
				{
					while(rs != null && rs.next()==true)
					{
						record=new Commoninfo();
						record.setInfoname(rs.getString("infoname"));
						recordId.setInfotype(rs.getString("infotype"));
						recordId.setParentcodekey(rs.getString("parentcodekey"));
						record.setId(recordId);
						record.setBinfotype(rs.getString("infotype"));
						record.setBparentcodekey(rs.getString("parentcodekey"));
						record.setParentcodevalue(rs.getString("parentcodevalue"));
						record.setUseflag(CommonTool.FormatInteger(rs.getInt("useflag")));
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
		return (List<Commoninfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
	}
	
	public List<Sysrolemode> loadSysrolemodes()
	{
		
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		UserInformation useinfo=(UserInformation)session.get("userInfo");
		if(useinfo!=null)
			return useinfo.getLsSysrolemode();
		return null;
	}
	
	public List<Sysmodeinfo> loadSysmodeinfos()
	{
		
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		UserInformation useinfo=(UserInformation)session.get("userInfo");
		if(useinfo!=null)
			return useinfo.getLsSysmodeinfo();
		return null;
	}
	
	
	public List<Useroverall> loadUseroveralls()
	{
		
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		UserInformation useinfo=(UserInformation)session.get("userInfo");
		if(useinfo!=null)
			return useinfo.getLsUseroverall();
		return null;
	}
	
	public  List<Companyinfo>  loadCompanyinfoByCurCompId()
	{
		try
		{
			String strSql=" select  companyinfo From Companyinfo companyinfo,Compchaininfo compchaininfo where  compno=relationcomp and curcomp='"+CommonTool.getLoginInfo("COMPID")+"'  ";
			return (List<Companyinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	
	}
	
	public  List<Compchainstruct>  loadCompchainstructByCurCompId()
	{
		String strSql=" select curcompno,compname,parentcompno,complevel,b.createdate From companyinfo a,compchainstruct b ,compchaininfo c " +
				" where compno=curcompno and c.curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and compno=relationcomp " +
						" order by complevel, curcompno ";
		AnlyResultSet<List<Compchainstruct>> analysis = new AnlyResultSet<List<Compchainstruct>>()
		{
			public List<Compchainstruct> anlyResultSet(ResultSet rs)
			{
				List<Compchainstruct> returnValue = new ArrayList();
				Compchainstruct record=new Compchainstruct();
				try
				{
					while(rs != null && rs.next()==true)
					{
						record=new Compchainstruct();
						record.setCurcompno(rs.getString("curcompno"));
						record.setCurcompname(rs.getString("compname"));
						record.setParentcompno(rs.getString("parentcompno"));
						record.setComplevel(rs.getInt("complevel"));
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
		return (List<Compchainstruct>)this.amn_Dao.executeQuery_ex(strSql,analysis);
	
	}
	
}
