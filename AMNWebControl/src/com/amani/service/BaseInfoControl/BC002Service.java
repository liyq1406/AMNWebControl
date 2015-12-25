package com.amani.service.BaseInfoControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Commoninfo;
import com.amani.model.CommoninfoId;
import com.amani.model.Compchaininfo;
import com.amani.model.CompchaininfoId;
import com.amani.model.Compchainstruct;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class BC002Service  extends AMN_ModuleService{

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteCommonInfo(List<Commoninfo> lsCommoninfo,String strDivId,String strInfotype,String strInfoname,String strParentcodekey,String strParentcodevalue )
	{
		String strSql="";
		if(strDivId.equals("commoninfodivsecond"))
		{
			if(lsCommoninfo!=null && lsCommoninfo.size()>0)
			{
				for(int i=0;i<lsCommoninfo.size();i++)
				{
					if(lsCommoninfo.get(i).getId()!=null 
					   && lsCommoninfo.get(i).getId().getParentcodekey()!=null
					   && !lsCommoninfo.get(i).getId().getParentcodekey().equals(""))
					{
						strSql=" delete commoninfo where infotype='"+strInfotype+"' " +
							    " and parentcodekey='"+CommonTool.FormatString(lsCommoninfo.get(i).getBparentcodekey())+"'  ";
					    this.amn_Dao.executeSql(strSql);
					}
				}
			}
		}
		else
		{
			if(lsCommoninfo!=null && lsCommoninfo.size()>0)
			{
				for(int i=0;i<lsCommoninfo.size();i++)
				{
					if(lsCommoninfo.get(i).getId()!=null 
					   && lsCommoninfo.get(i).getId().getCodekey()!=null
					   && !lsCommoninfo.get(i).getId().getCodekey().equals(""))
					{
						strSql=" delete commoninfo where infotype='"+strInfotype+"' and parentcodekey='"+strParentcodekey+"' " +
							    " and codekey='"+CommonTool.FormatString(lsCommoninfo.get(i).getBcodekey())+"' ";
					    this.amn_Dao.executeSql(strSql);
					}
				}
			}
		}
		return true;
	}

	@Override
	protected boolean postDetail(Object details) {
		
		return true;
	}
	
	public boolean postCommonInfo(List<Commoninfo> lsCommoninfo,String strDivId,String strInfotype,String strInfoname,String strParentcodekey,String strParentcodevalue )
	{
		
//		if(lsCommoninfo!=null && lsCommoninfo.size()>0)
//		{
//			for(int i=0;i<lsCommoninfo.size();i++)
//			{
//				if(lsCommoninfo.get(i).getId()!=null 
//				   && lsCommoninfo.get(i).getId().getCodekey()!=null
//				   && !lsCommoninfo.get(i).getId().getParentcodekey().equals("")
//				   && !lsCommoninfo.get(i).getId().getCodekey().equals(CommonTool.FormatString(lsCommoninfo.get(i).getBcodekey())))
//				{
//					lsCommoninfo.get(i).getId().setCodekey(CommonTool.FormatString(lsCommoninfo.get(i).getBcodekey()));
//					this.amn_Dao.saveOrUpdate(lsCommoninfo.get(i));
//				}
//			}
//		}
		 //commoninfodivsecond 二级明细  commoninfodivthirth 三级明细
		String strSql="";
		if(strDivId.equals("commoninfodivsecond"))
		{
			if(lsCommoninfo!=null && lsCommoninfo.size()>0)
			{
				for(int i=0;i<lsCommoninfo.size();i++)
				{
					if(lsCommoninfo.get(i).getId()!=null 
					   && lsCommoninfo.get(i).getId().getParentcodekey()!=null
					   && !lsCommoninfo.get(i).getId().getParentcodekey().equals(""))
					{
						strSql="if exists(select 1 from commoninfo where infotype='"+strInfotype+"' " +
							    " and parentcodekey='"+CommonTool.FormatString(lsCommoninfo.get(i).getBparentcodekey())+"' ) begin ";
						strSql=strSql+" update commoninfo set parentcodevalue='"+CommonTool.FormatString(lsCommoninfo.get(i).getParentcodevalue())+"',useflag="+CommonTool.FormatInteger(lsCommoninfo.get(i).getUseflag())+" " +
								" where infotype='"+strInfotype+"' " +
							    " and parentcodekey='"+CommonTool.FormatString(lsCommoninfo.get(i).getBparentcodekey())+"'  end" ;
					    strSql=strSql+"  else begin  insert commoninfo(infotype,infoname,parentcodekey,parentcodevalue,codekey,codevalue,codesource,useflag)" +
							" values('"+strInfotype+"','"+strInfoname+"','"+CommonTool.FormatString(lsCommoninfo.get(i).getBparentcodekey())+"'," +
									" '"+CommonTool.FormatString(lsCommoninfo.get(i).getParentcodevalue())+"','','','D',"+CommonTool.FormatInteger(lsCommoninfo.get(i).getUseflag())+") end ";
					    this.amn_Dao.executeSql(strSql);
					}
				}
			}
		}
		else
		{
			if(lsCommoninfo!=null && lsCommoninfo.size()>0)
			{
				for(int i=0;i<lsCommoninfo.size();i++)
				{
					if(lsCommoninfo.get(i).getId()!=null 
					   && lsCommoninfo.get(i).getId().getCodekey()!=null
					   && !lsCommoninfo.get(i).getId().getCodekey().equals(""))
					{
						strSql="if exists(select 1 from commoninfo where infotype='"+strInfotype+"' and parentcodekey='"+strParentcodekey+"' " +
							    " and codekey='"+CommonTool.FormatString(lsCommoninfo.get(i).getBcodekey())+"' ) begin ";
						strSql=strSql+" update commoninfo set codevalue='"+CommonTool.FormatString(lsCommoninfo.get(i).getCodevalue())+"' " +
								" where infotype='"+strInfotype+"'  and parentcodekey='"+strParentcodekey+"' " +
							    " and codekey='"+CommonTool.FormatString(lsCommoninfo.get(i).getBcodekey())+"'  end" ;
					    strSql=strSql+"  else begin  insert commoninfo(infotype,infoname,parentcodekey,parentcodevalue,codekey,codevalue,codesource)" +
							" values('"+strInfotype+"','"+strInfoname+"','"+strParentcodekey+"','"+strParentcodevalue+"'," +
									" '"+CommonTool.FormatString(lsCommoninfo.get(i).getBcodekey())+"','"+CommonTool.FormatString(lsCommoninfo.get(i).getCodevalue())+"','D') end ";
					    this.amn_Dao.executeSql(strSql);
					}
				}
			}
		}
		return true;
	}

	public List<Commoninfo> loadCommoninfos()
	{
		String strSql=" From Commoninfo where codesource='D' ";
		return (List<Commoninfo>)this.amn_Dao.findByHql(strSql);
	}
	public List<Commoninfo> loadCommoninfosParentByGroup()
	{
		String strSql=" select infotype,infoname,parentcodekey,parentcodevalue,useflag From commoninfo where codesource='D'  group by infotype,infoname,parentcodekey,parentcodevalue,useflag ";
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
	
	
	@Override
	protected boolean postMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return true;
	}

}
