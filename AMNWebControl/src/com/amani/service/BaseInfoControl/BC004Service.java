package com.amani.service.BaseInfoControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Staffinfo;
import com.amani.model.Sysuserinfo;
import com.amani.model.Usereditright;
import com.amani.model.UsereditrightId;
import com.amani.model.Useroverall;
import com.amani.model.UseroverallId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class BC004Service  extends AMN_ModuleService{
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

	public List<Sysuserinfo> loadUsersByCompId(String strCompId)
	{
		try
		{
			String strSql="select userno,staffno,staffname from sysuserinfo left join staffinfo on isnull(stafftype,0)=0 and  frominnerno=Manageno" +
					" where fromcompno='"+strCompId+"' ";
			AnlyResultSet<List<Sysuserinfo>> analysis = new AnlyResultSet<List<Sysuserinfo>>()
			{
				public List<Sysuserinfo> anlyResultSet(ResultSet rs)
				{
					List<Sysuserinfo> returnValue = new ArrayList();
					Sysuserinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Sysuserinfo();
							record.setUserno(CommonTool.FormatString(rs.getString("userno")));
							record.setUsername(CommonTool.FormatString(rs.getString("staffname")));
							record.setStrEmpId(CommonTool.FormatString(rs.getString("staffno")));
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
			List<Sysuserinfo> ls =(List<Sysuserinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<Usereditright> loadEditRightByUserId(String strUserId)
	{
		try
		{
			String strSql=" select userno,sysmodeno,functionno,modulename,browsepurview,editpurview,exportpurview,postpurview,confirmpurview,invalidpurview,disabledflag" +
					" from usereditright,sysmodeinfo where userno='"+strUserId+"' and modulevel=2 and curmoduleno=functionno ";
			AnlyResultSet<List<Usereditright>> analysis = new AnlyResultSet<List<Usereditright>>()
			{
				public List<Usereditright> anlyResultSet(ResultSet rs)
				{
					List<Usereditright> returnValue = new ArrayList();
					Usereditright record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Usereditright();
							record.setId(new UsereditrightId(CommonTool.FormatString(rs.getString("userno")),CommonTool.FormatString(rs.getString("sysmodeno")),CommonTool.FormatString(rs.getString("functionno"))));
							record.setBrowsepurview(CommonTool.FormatString(rs.getString("browsepurview")));
							record.setEditpurview(CommonTool.FormatString(rs.getString("editpurview")));
							record.setExportpurview(CommonTool.FormatString(rs.getString("exportpurview")));
							record.setPostpurview(CommonTool.FormatString(rs.getString("postpurview")));
							record.setConfirmpurview(CommonTool.FormatString(rs.getString("confirmpurview")));
							record.setInvalidpurview(CommonTool.FormatString(rs.getString("invalidpurview")));
							record.setBfunctionno(CommonTool.FormatString(rs.getString("functionno")));
							record.setBsysmodeno(CommonTool.FormatString(rs.getString("sysmodeno")));
							record.setBuserno(CommonTool.FormatString(rs.getString("userno")));
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
			List<Usereditright> ls= (List<Usereditright>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Sysuserinfo loadUserInfoByUserId(String strUserId)
	{
		try
		{
			String strSql=" select userno,userpwd,enableflag,userrole,frominnerno,fromcompno ,staffno,staffname,callcenterqueue,callcenterinterface " +
					" from Sysuserinfo left join staffinfo on  frominnerno=manageno   where userno='"+CommonTool.FormatString(strUserId)+"' ";
			AnlyResultSet<List<Sysuserinfo>> analysis = new AnlyResultSet<List<Sysuserinfo>>()
			{
				public List<Sysuserinfo> anlyResultSet(ResultSet rs)
				{
					List<Sysuserinfo> returnValue = new ArrayList();
					Sysuserinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Sysuserinfo();
							record.setUserno(CommonTool.FormatString(rs.getString("userno")));
							record.setUserpwd(desPlus.unEncrypt(CommonTool.FormatString(rs.getString("userpwd"))));
							record.setUserrole(CommonTool.FormatString(rs.getString("userrole")));
							record.setFrominnerno(CommonTool.FormatString(rs.getString("frominnerno")));
							record.setFromcompno(CommonTool.FormatString(rs.getString("fromcompno")));
							record.setStrEmpId(CommonTool.FormatString(rs.getString("staffno")));
							record.setUsername(CommonTool.FormatString(rs.getString("staffname")));	
							record.setCallcenterqueue(CommonTool.FormatString(rs.getString("callcenterqueue")));	
							record.setCallcenterinterface(CommonTool.FormatString(rs.getString("callcenterinterface")));	
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
			List<Sysuserinfo> ls= (List<Sysuserinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			if(ls!=null && ls.size()>0)
			{
				return ls.get(0);
			}
			return new Sysuserinfo();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean postUserInfos(Sysuserinfo curSysuserinfo,List<Usereditright> lsUsereditright,List<Useroverall>  lsUseroverall)
	{
		try
		{
			curSysuserinfo.setUserpwd(desPlus.encrypt(curSysuserinfo.getUserpwd()));
			this.amn_Dao.saveOrUpdate(curSysuserinfo);
			String strSql="";
			
			strSql=" delete usereditright where userno='"+curSysuserinfo.getUserno()+"' ";
			strSql=strSql+" delete useroverall where userno='"+curSysuserinfo.getUserno()+"'  ";
			strSql=strSql+" insert useroverall(userno,modetype,modevalue) values('"+curSysuserinfo.getUserno()+"','1','"+curSysuserinfo.getFromcompno()+"') ";
			this.amn_Dao.executeSql(strSql);
			if(lsUsereditright!=null && lsUsereditright.size()>0)
			{
				for(int i=0;i<lsUsereditright.size();i++)
				{
					if(lsUsereditright.get(i).getId()!=null 
					&& !CommonTool.FormatString(lsUsereditright.get(i).getId().getUserno()).equals("")
					&& !CommonTool.FormatString(lsUsereditright.get(i).getId().getSysmodeno()).equals("")
					&& !CommonTool.FormatString(lsUsereditright.get(i).getId().getFunctionno()).equals(""))
					{
						this.amn_Dao.saveOrUpdate(lsUsereditright.get(i));
					}
				}
			}
			if(lsUseroverall!=null && lsUseroverall.size()>0)
			{
				for(int i=0;i<lsUseroverall.size();i++)
				{
					if(lsUseroverall.get(i).getId()!=null 
					&& !CommonTool.FormatString(lsUseroverall.get(i).getId().getUserno()).equals("")
					&& !CommonTool.FormatString(lsUseroverall.get(i).getId().getModevalue()).equals(""))
					{
						this.amn_Dao.saveOrUpdate(lsUseroverall.get(i));
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
	
	public boolean deleteUserInfos(String strUserId)
	{
		try
		{
		
			String strSql="delete sysuserinfo where userno='"+strUserId+"' ";			
			strSql=strSql+" delete usereditright where userno='"+strUserId+"' ";
			strSql=strSql+" delete useroverall where userno='"+strUserId+"'  ";
			return this.amn_Dao.executeSql(strSql);
	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean checkUserIdExists(String strUserId)
	{
		try
		{
			String strSql=" select 1 from sysuserinfo where userno='"+strUserId+"' ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			{
				public Boolean anlyResultSet(ResultSet rs)
				{
					boolean returnValue = true;
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
						returnValue =  false;
					}
					return returnValue;
				}
			};
			return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public Staffinfo loadStaffinfoByEmpIs(String strCompId,String strEmpId)
	{
		try
		{
			String strSql=" from  Staffinfo staffinfo where compno='"+strCompId+"' and staffno='"+strEmpId+"' ";			
			List<Staffinfo> ls=this.amn_Dao.findByHql(strSql);
			if(ls!=null && ls.size()>0)
			{
				return ls.get(0);
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean syncUserInfoByCompId(String strCompId)
	{
		try
		{
			String strSql=" insert sysuserinfobak(userno,userpwd,enableflag,userrole,frominnerno,fromcompno,datefrom,dateto,operationerno,operationdate,operationtime)" +
					" select userno,userpwd,enableflag,userrole,frominnerno,fromcompno,datefrom,dateto,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"' from sysuserinfo where  fromcompno='"+strCompId+"' ";
			strSql=strSql+" delete a from useroverall a,sysuserinfo b where a.userno=b.userno and  fromcompno='"+strCompId+"'  ";
			strSql=strSql+" delete sysuserinfo where  fromcompno='"+strCompId+"'  ";
			this.amn_Dao.executeSql(strSql);
			strSql="select gaa01c,gaa02c,gab03c,haa34c,gaa04c from gam01,gam02,ham01" +
					" where gaa04c='"+strCompId+"' and gab01c=gaa01c and gab02c='3' and haa01c=gaa05c ";
			AnlyResultSet<List<Sysuserinfo>> analysis = new AnlyResultSet<List<Sysuserinfo>>()
			{
				public List<Sysuserinfo> anlyResultSet(ResultSet rs)
				{
					List<Sysuserinfo> returnValue = new ArrayList();
					Sysuserinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Sysuserinfo();
							record.setUserno(CommonTool.FormatString(rs.getString("gaa01c")));
							record.setUserpwd(desPlus.encrypt(desPlus.oldUncode(CommonTool.FormatString(rs.getString("gaa02c")))));
							record.setUserrole(CommonTool.FormatString(rs.getString("gab03c")));
							record.setFrominnerno(CommonTool.FormatString(rs.getString("haa34c")));
							record.setFromcompno(CommonTool.FormatString(rs.getString("gaa04c")));		
							record.setDatefrom("20130101");
							record.setDateto("20141231");
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
			List<Sysuserinfo> ls= (List<Sysuserinfo>)this.amn_PADDao.executeQuery_ex(strSql,analysis);
			List<Useroverall> lsUseroverall=new ArrayList();
			Useroverall record=null;
			analysis=null;
			if(ls!=null && ls.size()>0)
			{
				for(int i=0;i<ls.size();i++)
				{
					record=new Useroverall();
					record.setId(new UseroverallId(ls.get(i).getUserno(),"1",ls.get(i).getFromcompno()));
					lsUseroverall.add(record);
				}
				this.amn_Dao.saveOrUpdateAll(ls);
			}
			if(lsUseroverall!=null && lsUseroverall.size()>0)
			{
				this.amn_Dao.saveOrUpdateAll(lsUseroverall);
			}
			lsUseroverall=null;
			record=null;
			ls=null;
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	public List<Useroverall> loadUseroverallByType(String strUserId,String strType)
	{
		try
		{
			String strSql="";

			if(strType.equals("2"))//禁用模组
			{

				strSql="select modevalue=sysmodeno,descriptions=modulename,showflag=ISNULL(showflag,'N') from sysuserinfo,sysmodeinfo,sysrolemode" +
					" left join (select modevalue,showflag='Y' from useroverall where userno='"+strUserId+"' and modetype='2') as useroverall on sysmodeno=modevalue" +
					" where userno='"+strUserId+"' and userrole=roleno and sysmodeno=curmoduleno and modulevel=1" +
					" group by sysmodeno,modulename,showflag ";
			}
			else if(strType.equals("4"))//禁用功能
			{
				strSql=" select modevalue=functionno,descriptions=modulename,showflag=ISNULL(showflag,'N') from sysuserinfo,sysmodeinfo,sysrolemode" +
						" left join (select modevalue,showflag='Y' from useroverall where userno='"+strUserId+"' and modetype='4') as useroverall on functionno=modevalue" +
						" where userno='"+strUserId+"' and userrole=roleno and functionno=curmoduleno and modulevel=2" +
						" group by functionno,modulename,showflag";
			}
			else if(strType.equals("5"))//禁用特殊权限
			{
				strSql=" select modevalue=curpurviewno,descriptions=purviewname,showflag=ISNULL(showflag,'N') from sysuserinfo,sysmodepurview" +
						" left join (select modevalue,showflag='Y' from useroverall where userno='"+strUserId+"' and modetype='5') as useroverall on curpurviewno=modevalue" +
						" where userno='"+strUserId+"' and curRoleno=userrole " +
						" group by curpurviewno,purviewname,showflag";
			}
			else if(strType.equals("3"))//禁用门店
			{
				strSql=" select modevalue=relationcomp,descriptions=compname,showflag=ISNULL(showflag,'N') from sysuserinfo,companyinfo,compchaininfo" +
						" left join (select modevalue,showflag='Y' from useroverall where userno='"+strUserId+"' and modetype='3') as useroverall on relationcomp=modevalue" +
						" where userno='"+strUserId+"' and fromcompno=curcomp and relationcomp=compno" +
						" group by relationcomp,compname,showflag ";
			}
			AnlyResultSet<List<Useroverall>> analysis = new AnlyResultSet<List<Useroverall>>()
			{
				public List<Useroverall> anlyResultSet(ResultSet rs)
				{
					List<Useroverall> returnValue = new ArrayList();
					Useroverall record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Useroverall();
							record.setBmodevalue(CommonTool.FormatString(rs.getString("modevalue")));
							record.setDescriptions(CommonTool.FormatString(rs.getString("descriptions")));
							record.setShowflag(CommonTool.FormatString(rs.getString("showflag")));
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
			List<Useroverall>  ls= (List<Useroverall>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
}
