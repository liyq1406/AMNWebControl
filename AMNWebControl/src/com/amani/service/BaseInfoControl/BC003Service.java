package com.amani.service.BaseInfoControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.roleSetInfo;
import com.amani.model.Sysmodepurview;
import com.amani.model.SysmodepurviewId;
import com.amani.model.Sysrolemode;
import com.amani.model.Sysuserinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class BC003Service  extends AMN_ModuleService{

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

	public List<roleSetInfo> loadRoleSetInfo()
	{
		try
		{
			String strSql="select parentcodekey,parentcodevalue,rolenum=isnull(rolenum,0) from commoninfo " +
					" left join (select userrole,rolenum=COUNT(userrole) from sysuserinfo group by userrole) rolecount on parentcodekey=userrole " +
					" where infotype='XTJS' and ISNULL(parentcodekey,'') <>''";
			AnlyResultSet<List<roleSetInfo>> analysis = new AnlyResultSet<List<roleSetInfo>>()
			{
				public List<roleSetInfo> anlyResultSet(ResultSet rs)
				{
					List<roleSetInfo> returnValue = new ArrayList();
					roleSetInfo record=new roleSetInfo();
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new roleSetInfo();
							record.setStrRoleId(CommonTool.FormatString(rs.getString("parentcodekey")));
							record.setStrRoleName(CommonTool.FormatString(rs.getString("parentcodevalue")));
							record.setRoleUserNum(CommonTool.FormatInteger(rs.getInt("rolenum")));
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
			return (List<roleSetInfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Sysuserinfo> loadSysuserinfoByRoleId(String strRoleId)
	{
		try
		{
			String strSql="select compno,staffno,staffname from staffinfo,sysuserinfo where frominnerno=Manageno and userrole='"+strRoleId+"' ";
			AnlyResultSet<List<Sysuserinfo>> analysis = new AnlyResultSet<List<Sysuserinfo>>()
			{
				public List<Sysuserinfo> anlyResultSet(ResultSet rs)
				{
					List<Sysuserinfo> returnValue = new ArrayList();
					Sysuserinfo record=new Sysuserinfo();
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Sysuserinfo();
							record.setFromcompno(CommonTool.FormatString(rs.getString("compno")));
							record.setUserno(CommonTool.FormatString(rs.getString("staffno")));
							record.setUsername(CommonTool.FormatString(rs.getString("staffname")));
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
			return (List<Sysuserinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Sysrolemode> loadSysmozuinfoByRoleId(String strRoleId)
	{
		try
		{
			String strSql="select curmoduleno,modulename,showflag=ISNULL(showflag,'N') from sysmodeinfo" +
					" left join (select sysmodeno,showflag='Y' from sysrolemode where   roleno='"+strRoleId+"') as temprolemode on curmoduleno=sysmodeno" +
					"  where modulevel=1  group by curmoduleno,modulename,showflag ";
			AnlyResultSet<List<Sysrolemode>> analysis = new AnlyResultSet<List<Sysrolemode>>()
			{
				public List<Sysrolemode> anlyResultSet(ResultSet rs)
				{
					List<Sysrolemode> returnValue = new ArrayList();
					Sysrolemode record=new Sysrolemode();
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Sysrolemode();
							record.setStrShowFlag(CommonTool.FormatString(rs.getString("showflag")));
							record.setBsysmodeno(CommonTool.FormatString(rs.getString("curmoduleno")));
							record.setBsysmodename(CommonTool.FormatString(rs.getString("modulename")));
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
			return (List<Sysrolemode>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<roleSetInfo> loadPositionByRoleId(String strRoleId)
	{
		try
		{
			String strSql=" 	select roleno,position,parentcodevalue from sysroletoposition a,commoninfo b where roleno='"+strRoleId+"' and  a.position=b.parentcodekey and b.infotype='GZGW' ";
			AnlyResultSet<List<roleSetInfo>> analysis = new AnlyResultSet<List<roleSetInfo>>()
			{
				public List<roleSetInfo> anlyResultSet(ResultSet rs)
				{
					List<roleSetInfo> returnValue = new ArrayList();
					roleSetInfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new roleSetInfo();
							record.setStrRoleId(CommonTool.FormatString(rs.getString("roleno")));
							record.setPosition(CommonTool.FormatString(rs.getString("position")));
							record.setPositionname(CommonTool.FormatString(rs.getString("parentcodevalue")));
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
			return (List<roleSetInfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}



	public List<Sysrolemode> loadSysmokuaiinfoByRoleId(String strRoleId,String strMoZuId)
	{
		try
		{
			String strSql="select upmoduleno,curmoduleno,modulename,showflag=ISNULL(showflag,'N')," +
					" browsepurview=ISNULL(browsepurview,'N'),editpurview=ISNULL(editpurview,'N'),exportpurview=ISNULL(exportpurview,'N')" +
					",postpurview=ISNULL(postpurview,'N'),confirmpurview=ISNULL(confirmpurview,'N'),invalidpurview=ISNULL(invalidpurview,'N') from sysmodeinfo" +
					" left join (select sysmodeno,functionno,showflag='Y'," +
					" browsepurview=ISNULL(browsepurview,'N'),editpurview=ISNULL(editpurview,'N'),exportpurview=ISNULL(exportpurview,'N'), " +
					" postpurview=ISNULL(postpurview,'N'),confirmpurview=ISNULL(confirmpurview,'N'),invalidpurview=ISNULL(invalidpurview,'N')" +
					" from sysrolemode where   roleno='"+strRoleId+"'  ) as temprolemode on curmoduleno=functionno " +
					" where modulevel=2 and upmoduleno='"+strMoZuId+"'  ";
			AnlyResultSet<List<Sysrolemode>> analysis = new AnlyResultSet<List<Sysrolemode>>()
			{
				public List<Sysrolemode> anlyResultSet(ResultSet rs)
				{
					List<Sysrolemode> returnValue = new ArrayList();
					Sysrolemode record=new Sysrolemode();
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Sysrolemode();
							record.setStrShowFlag(CommonTool.FormatString(rs.getString("showflag")));
							
							record.setBfunctionno(CommonTool.FormatString(rs.getString("curmoduleno")));
							record.setBfunctioname(CommonTool.FormatString(rs.getString("modulename")));
							record.setBrowsepurview(CommonTool.FormatString(rs.getString("browsepurview")));
							if(CommonTool.FormatString(rs.getString("browsepurview")).equals("Y"))
								record.setBrowsepurviewflag(true);
							else
								record.setBrowsepurviewflag(false);
							
							record.setEditpurview(CommonTool.FormatString(rs.getString("editpurview")));
							if(CommonTool.FormatString(rs.getString("editpurview")).equals("Y"))
								record.setEditpurviewflag(true);
							else
								record.setEditpurviewflag(false);
							
							record.setExportpurview(CommonTool.FormatString(rs.getString("exportpurview")));
							if(CommonTool.FormatString(rs.getString("exportpurview")).equals("Y"))
								record.setExportpurviewflag(true);
							else
								record.setExportpurviewflag(false);
							
							record.setPostpurview(CommonTool.FormatString(rs.getString("postpurview")));
							if(CommonTool.FormatString(rs.getString("postpurview")).equals("Y"))
								record.setPostpurviewflag(true);
							else
								record.setPostpurviewflag(false);
							
							record.setConfirmpurview(CommonTool.FormatString(rs.getString("confirmpurview")));
							if(CommonTool.FormatString(rs.getString("confirmpurview")).equals("Y"))
								record.setConfirmpurviewflag(true);
							else
								record.setConfirmpurviewflag(false);
							
							record.setInvalidpurview(CommonTool.FormatString(rs.getString("invalidpurview")));
							if(CommonTool.FormatString(rs.getString("invalidpurview")).equals("Y"))
								record.setInvalidpurviewflag(true);
							else
								record.setInvalidpurviewflag(false);
							
							record.setTestflag(true);
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
			return (List<Sysrolemode>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Sysmodepurview> loadSysmodepurview(String strRoleId)
	{
		try
		{
			String strSql="select parentcodekey,parentcodevalue,showflag=ISNULL(showflag,'N') from commoninfo " +
					" left join (select curpurviewno,showflag='Y' from sysmodepurview where curRoleno='"+strRoleId+"') as sysmodeshow  on parentcodekey=curpurviewno" +
					" where infotype='XTQX' ";
			AnlyResultSet<List<Sysmodepurview>> analysis = new AnlyResultSet<List<Sysmodepurview>>()
			{
				public List<Sysmodepurview> anlyResultSet(ResultSet rs)
				{
					List<Sysmodepurview> returnValue = new ArrayList();
					Sysmodepurview record=new Sysmodepurview();
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Sysmodepurview();
							record.setId(new SysmodepurviewId("",CommonTool.FormatString(rs.getString("parentcodekey"))));
							record.setPurviewname(CommonTool.FormatString(rs.getString("parentcodevalue")));
							record.setShowflag(CommonTool.FormatString(rs.getString("showflag")));
							record.setBcurpurviewno(CommonTool.FormatString(rs.getString("parentcodekey")));
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
			return (List<Sysmodepurview>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean postSysrolemode(String strRoleId,String strMozuId,List<Sysrolemode> lsSysrolemodes,List<Sysmodepurview> lsSysmodepurviews)
	{
		try
		{
			String strSql=" delete sysrolemode where roleno='"+strRoleId+"' and sysmodeno='"+strMozuId+"' "; 
			this.amn_Dao.deleteBySql(strSql);
			if(lsSysrolemodes!=null && lsSysrolemodes.size()>0)
			{
				for(int i=0;i<lsSysrolemodes.size();i++)
				{
					if(lsSysrolemodes.get(i).getId()!=null && !CommonTool.FormatString(lsSysrolemodes.get(i).getId().getFunctionno()).equals(""))
					{
						
						if(lsSysrolemodes.get(i).getBrowsepurviewflag()!=null && lsSysrolemodes.get(i).getBrowsepurviewflag()==true)
							lsSysrolemodes.get(i).setBrowsepurview("Y");
						else
							lsSysrolemodes.get(i).setBrowsepurview("N");
						
						if(lsSysrolemodes.get(i).getEditpurviewflag()!=null && lsSysrolemodes.get(i).getEditpurviewflag()==true)
							lsSysrolemodes.get(i).setEditpurview("Y");
						else
							lsSysrolemodes.get(i).setEditpurview("N");
						
						if(lsSysrolemodes.get(i).getExportpurviewflag()!=null && lsSysrolemodes.get(i).getExportpurviewflag()==true)
							lsSysrolemodes.get(i).setExportpurview("Y");
						else
							lsSysrolemodes.get(i).setExportpurview("N");
						
						if(lsSysrolemodes.get(i).getPostpurviewflag()!=null && lsSysrolemodes.get(i).getPostpurviewflag()==true)
							lsSysrolemodes.get(i).setPostpurview("Y");
						else
							lsSysrolemodes.get(i).setPostpurview("N");
						
						if(lsSysrolemodes.get(i).getConfirmpurviewflag()!=null && lsSysrolemodes.get(i).getConfirmpurviewflag()==true)
							lsSysrolemodes.get(i).setConfirmpurview("Y");
						else
							lsSysrolemodes.get(i).setConfirmpurview("N");
						
						if(lsSysrolemodes.get(i).getInvalidpurviewflag()!=null && lsSysrolemodes.get(i).getInvalidpurviewflag()==true)
							lsSysrolemodes.get(i).setInvalidpurview("Y");
						else
							lsSysrolemodes.get(i).setInvalidpurview("N");
						
						this.amn_Dao.save(lsSysrolemodes.get(i));
					}
				}
			}
			strSql=" delete sysmodepurview where curRoleno='"+strRoleId+"' "; 
			this.amn_Dao.deleteBySql(strSql);
			if(lsSysmodepurviews!=null && lsSysmodepurviews.size()>0)
			{
				for(int i=0;i<lsSysmodepurviews.size();i++)
				{
					if(lsSysmodepurviews.get(i).getId()!=null && !CommonTool.FormatString(lsSysmodepurviews.get(i).getId().getCurpurviewno()).equals(""))
					{
						this.amn_Dao.save(lsSysmodepurviews.get(i));
					}
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
	
	public boolean validatePosition(String strPosition)
	{
		 String strSql=" select 1 from sysroletoposition where position='"+strPosition+"'";
		 AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			{
				public Boolean anlyResultSet(ResultSet rs)
				{
					boolean returnValue = false;
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
						returnValue =  true;
					}
					return returnValue;
				}
			};
			return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
	}
	
	public boolean postBandRolePosition(String strRoleId,String strPosition)
	{
		try
		{
			String strSql=" insert sysroletoposition(roleno,position)  values('"+strRoleId+"','"+strPosition+"') ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteBandRolePostion(String strRoleId,String strPosition)
	{
		try
		{
			String strSql=" delete  sysroletoposition where roleno= '"+strRoleId+"' and position='"+strPosition+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
}
