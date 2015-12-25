package com.amani.service;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.amani.action.AnlyResultSet;
import com.amani.dao.AMN_DaoImp;
import com.amani.model.Sysmodeinfo;
import com.amani.model.SysmodeinfoId;
import com.amani.model.Sysparaminfo;
import com.amani.model.Sysrolemode;
import com.amani.model.SysrolemodeId;
import com.amani.model.Usereditright;
import com.amani.model.Useroverall;
import com.amani.model.UseroverallId;
import com.amani.tools.DESPlus;
import com.amani.tools.DataTool;
import com.amani.tools.CommonTool;
import com.amani.tools.OrgManager;
import com.amani.tools.SystemFinal;
import com.amani.tools.UserInformation;

@Service
public class LoginServiceImp
{
	@Autowired
	private AMN_DaoImp amn_Dao;
	@Autowired
	private DataTool dataTool;
	@Autowired
	private DESPlus desPlus;
	
	@Resource(name="orgManager")
	private OrgManager orgManager;


	public boolean processUserLogin(String compId, String userId, String pwd,
			String customDogCode, String macAddr, String IP, StringBuffer retMsg)
			throws Exception {
		try {
			
			if (this.isCompExist(compId)) {
				int returnValue = this.validateLogin(compId, userId, pwd);
				if (returnValue == 1) {
					this.addLoginLog(userId, compId, macAddr, IP);
				} else if (returnValue == 2) {
					retMsg.append(SystemFinal.LOGIN_OUTDATE);
					return false;
				} else if (returnValue == 3) {
					retMsg.append(SystemFinal.LOGIN_OUTCOMPID);
					return false;
				} else {
						
					retMsg.append(SystemFinal.LOGIN_VERTIFY_FAIL);
					return false;
				}
			} else {
				retMsg.append(SystemFinal.COMP_NOT_EXIST);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMsg.append(SystemFinal.LOGIN_VERTIFY_FAIL);
			return false;
		}
		retMsg.append(SystemFinal.LOGIN_SUCCESS);
		return true;
	}

	public boolean isCompExist(String compId) throws Exception {
		String strSql = "select 1 from compchainstruct where curcompno = "+ CommonTool.quotedStr(compId);
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() {
			public Boolean anlyResultSet(ResultSet rs) {
				boolean returnValue = false;
				try {
					if (rs != null && rs.next() == true) {
						returnValue = true;
					} else {
						returnValue = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue = false;
				}
				return returnValue;
			}
		};
		return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);

	}

	public int validateLogin(String compId, String userId, String pwd)
			throws Exception {
	
		String pwdAfterEncode =desPlus.encrypt(pwd);
		if(userId.toUpperCase().equals(SystemFinal.KING_USER))
		{
			String curDate="";
			curDate = CommonTool.getCurrDate();
			String strAMNPwd="";
			if(curDate != null||!curDate.equals(""))
			{
				strAMNPwd = CommonTool.getAMNPassWord(curDate);
			}
			if(strAMNPwd==null&&strAMNPwd.equals(""))
				return 0;
			if(pwd.equals(strAMNPwd)) 
			{
				return 1;
			}
			return 0;
		}
		else {
			String strSql ="select 1 from useroverall where userno='"+userId+"' and modetype=3 and '"+compId+"'=modevalue ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() {
				public Boolean anlyResultSet(ResultSet rs) {
					boolean returnValue = true;
					try {
						if (rs != null && rs.next() == true) {
							returnValue = true;
						} else {
							returnValue = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue = true;
					}
					return returnValue;
				}
			};
			boolean flag= (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			if(flag==true)
			{
				return 3;
			}
			strSql = "select datefrom,dateto from sysuserinfo a,useroverall b,compchaininfo c " +
					" where a.userno = b.userno  and a.userno='"+userId+"'  " +
//					" where a.userno = b.userno  and isnull(a.userpwd,'') = '"+pwdAfterEncode+"' and a.userno='"+userId+"'  " +
					" and b.modetype='1'  and b.modevalue=c.curcomp and c.relationcomp= '"+compId+"' " ;				
			AnlyResultSet<Integer> analysis1 = new AnlyResultSet<Integer>() {
				public Integer anlyResultSet(ResultSet rs) {
					try {
						if (rs != null && rs.next()) {
							//---------------判断账户有效期,暂不用
							/*if (CommonTool.FormatString(rs.getString("datefrom")).compareToIgnoreCase(CommonTool.getCurrDate()) > 1) {
								return 2;
							}
							if (CommonTool.FormatString(rs.getString("dateto")).compareToIgnoreCase(CommonTool.getCurrDate()) < 0) {
								return 2;
							}*/
							return 1;
						} else {
							return 0;
						}
					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					}
				}
			};
			return ((Integer) this.amn_Dao.executeSqlNoTran_ex(strSql, analysis1)).intValue();
		}
	}
	// log user's login
	public boolean addLoginLog(String userId, String compId, String macAddr,
			String IP) {
		boolean bRet = true;
		if (CommonTool.isNullForString(IP).equalsIgnoreCase("")) {
			IP = CommonTool.getRemoteIP();
		}
		String strSql = "insert  sysuserlogininfo(logindate,logintime,loginipno,loginmacno,loginuserid,loginusername,logincompid,logincompname) values("
				+ CommonTool.quotedStr(CommonTool.getCurrDate())
				+ ","
				+ CommonTool.quotedStr(CommonTool.getCurrTime())
				+ ","
				+ CommonTool.quotedStr(IP)
				+ ","
				+ CommonTool.quotedStr(macAddr)
				+ ","
				+ CommonTool.quotedStr(userId)
				+ ","
				+ CommonTool.quotedStr(getUserName(userId))
				+ ","
				+ CommonTool.quotedStr(compId)
				+ ","
				+ CommonTool.quotedStr(getCompName(compId)) + ")";
		try {
			bRet = this.amn_Dao.executeSql(strSql);

		} catch (Exception e) {
			e.printStackTrace();
			bRet = false;
		}
		return bRet;
	}
	
	public UserInformation loadUserInformation(String strCompId,String  strUserId)
	{
		String strSql="";
		UserInformation userInfo=new UserInformation();
		userInfo.setCompId(CommonTool.FormatString(strCompId));
		userInfo.setCompName(CommonTool.FormatString(this.dataTool.loadCompNameById(strCompId)));
		userInfo.setUserId(CommonTool.FormatString(strUserId));
		List<Useroverall> lsUseroverall=new ArrayList();  //系统模组
		List<Sysmodeinfo> lssysmodeinfosys=new ArrayList(); //系统模块
		if (!strUserId.toUpperCase().equals("AMANI") && !strUserId.toUpperCase().equals("ADMIN")) 
		{
			strSql=" select userno,frominnerno,staffname,department,userrole=a.userrole,userrolename=parentcodevalue,callcenterqueue,callcenterinterface " +
					" from sysuserinfo a,staffinfo b,commoninfo c " +
					" where  a.frominnerno=b.manageno and a.userno='"+strUserId+"'" +
					"  and c.infotype='XTJS' and c.parentcodekey=a.userrole ";
			
			AnlyResultSet<List> analysis = new AnlyResultSet<List>() {
				public List anlyResultSet(ResultSet rs) {
					List returnValue = new ArrayList();
					try {
						if (rs != null && rs.next() == true) {
							returnValue.add(rs.getString("frominnerno")) ;
							returnValue.add(rs.getString("staffname")) ;
							returnValue.add(rs.getString("department")) ;
							returnValue.add(rs.getString("userrole")) ;
							returnValue.add(rs.getString("userrolename")) ;
							returnValue.add(rs.getString("callcenterqueue")) ;
							returnValue.add(rs.getString("callcenterinterface")) ;
						} else {
							returnValue.add("") ;
							returnValue.add("") ;
							returnValue.add("") ;
							returnValue.add("") ;
							returnValue.add("") ;
							returnValue.add("") ;
							returnValue.add("") ;
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue.add("") ;
						returnValue.add("") ;
						returnValue.add("") ;
						returnValue.add("") ;
						returnValue.add("") ;
						returnValue.add("") ;
						returnValue.add("") ;
					}
					return returnValue;
				}
			};
			List lsStaffInfo= (List) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			userInfo.setUserName(CommonTool.FormatString(lsStaffInfo.get(1)));
			userInfo.setUserinnerId(CommonTool.FormatString(lsStaffInfo.get(0)));
			userInfo.setDepart(CommonTool.FormatString(lsStaffInfo.get(2)));
			userInfo.setUserrole(CommonTool.FormatString(lsStaffInfo.get(3)));
			userInfo.setUserrolename(CommonTool.FormatString(lsStaffInfo.get(4)));
			userInfo.setCallcenterqueue(CommonTool.FormatString(lsStaffInfo.get(5)));
			userInfo.setCallcenterinterface(CommonTool.FormatString(lsStaffInfo.get(6)));
			userInfo.setMangerFlag(0);
			lsStaffInfo=null;
			//---------------------获得角色启用的模块(去除个性屏蔽的模块) Start----------------------------
			strSql=" select upmoduleno,curmoduleno,modulename,modulevel,moduletype,remark,moduletitle,moduleurl,modulewidth,moduleheight" +
					" from sysmodeinfo,sysuserinfo,sysrolemode " +
					" where userno='"+strUserId+"' and  userrole=roleno and  functionno=curmoduleno and  modulevel='2' " +
					" and curmoduleno  not in (select modevalue from useroverall where userno='"+strUserId+"' and modetype='4' ) ";
			AnlyResultSet<List<Sysmodeinfo>> analysis2 = new AnlyResultSet<List<Sysmodeinfo>>() {
				public List<Sysmodeinfo> anlyResultSet(ResultSet rs) {
					List<Sysmodeinfo> returnValue = new ArrayList();
					try {
							Sysmodeinfo rolemode=null;
							while (rs != null && rs.next() == true) {						
								rolemode=new Sysmodeinfo();
								rolemode.setId(new SysmodeinfoId("",CommonTool.FormatString(rs.getString("upmoduleno")),CommonTool.FormatString(rs.getString("curmoduleno"))));
								rolemode.setModulename(CommonTool.FormatString(rs.getString("modulename")));
								rolemode.setModulevel(CommonTool.FormatInteger(rs.getInt("modulevel")));
								rolemode.setModuletype(CommonTool.FormatString(rs.getString("moduletype")));
								rolemode.setRemark(CommonTool.FormatString(rs.getString("remark")));
								rolemode.setModuletitle(CommonTool.FormatString(rs.getString("moduletitle")));
								rolemode.setModuleurl(CommonTool.FormatString(rs.getString("moduleurl")));
								rolemode.setModulewidth(CommonTool.FormatInteger(rs.getInt("modulewidth")));
								rolemode.setModuleheight(CommonTool.FormatInteger(rs.getInt("moduleheight")));
								returnValue.add(rolemode);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return returnValue;
				}
			};
			userInfo.setLsSysmodeinfo((List<Sysmodeinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis2));
			analysis2=null;
			if(userInfo.getLsSysmodeinfo()!=null && userInfo.getLsSysmodeinfo().size()>0)
			{
				strSql=" select  upmoduleno,curmoduleno,modulename,modulevel,moduletype,remark,moduletitle,moduleurl,modulewidth,moduleheight " +
						" From  usereditright a,sysmodeinfo b where a.userno='"+strUserId+"' and a.functionno=b.curmoduleno and b.modulevel='2'" +
						" and a.functionno not in (select functionno from  sysrolemode ,sysuserinfo where userno='"+strUserId+"'  and  userrole=roleno )";
				AnlyResultSet<List<Sysmodeinfo>> analysis4 = new AnlyResultSet<List<Sysmodeinfo>>() {
					public List<Sysmodeinfo> anlyResultSet(ResultSet rs) {
						List<Sysmodeinfo> returnValue = new ArrayList();
						try {
								Sysmodeinfo rolemode=null;
								while (rs != null && rs.next() == true) {						
									rolemode=new Sysmodeinfo();
									rolemode.setId(new SysmodeinfoId("",CommonTool.FormatString(rs.getString("upmoduleno")),CommonTool.FormatString(rs.getString("curmoduleno"))));
									rolemode.setModulename(CommonTool.FormatString(rs.getString("modulename")));
									rolemode.setModulevel(CommonTool.FormatInteger(rs.getInt("modulevel")));
									rolemode.setModuletype(CommonTool.FormatString(rs.getString("moduletype")));
									rolemode.setRemark(CommonTool.FormatString(rs.getString("remark")));
									rolemode.setModuletitle(CommonTool.FormatString(rs.getString("moduletitle")));
									rolemode.setModuleurl(CommonTool.FormatString(rs.getString("moduleurl")));
									rolemode.setModulewidth(CommonTool.FormatInteger(rs.getInt("modulewidth")));
									rolemode.setModuleheight(CommonTool.FormatInteger(rs.getInt("moduleheight")));
									returnValue.add(rolemode);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							return returnValue;						
					}
				};
				List<Sysmodeinfo> lsSysmodeinfo= (List<Sysmodeinfo> )this.amn_Dao.executeQuery_ex(strSql, analysis4);
				if(lsSysmodeinfo!=null && lsSysmodeinfo.size()>0)
				{
					for(int i=0;i<lsSysmodeinfo.size();i++)
					{
						userInfo.getLsSysmodeinfo().add(lsSysmodeinfo.get(i));
					}
					
				}
				lsSysmodeinfo=null;
				analysis4=null;
			}
			//---------------------获得角色启用的模块(去除个性屏蔽的模块) end------------------------------
			
			//---------------------获得角色启用的模组(去除个性屏蔽的模组) Start----------------------------
			strSql=" select userno,curmoduleno,modulename,showtype  " +
			" from sysmodeinfo,sysuserinfo,sysrolemode " +
			" where userno='"+strUserId+"' and  userrole=roleno and  sysmodeno=curmoduleno and  modulevel='1' " +
			" and curmoduleno  not in (select modevalue from useroverall where userno='"+strUserId+"' and modetype='2' )" +
			" group by userno,curmoduleno,modulename,showtype  order by showtype ";
			AnlyResultSet<List<Useroverall>> analysis3 = new AnlyResultSet<List<Useroverall>>() {
				public List<Useroverall> anlyResultSet(ResultSet rs) {
					List<Useroverall> returnValue = new ArrayList();
					try {
						Useroverall useroverall=null;
							while (rs != null && rs.next() == true) {						
								useroverall=new Useroverall();
								useroverall.setId(new UseroverallId(CommonTool.FormatString(rs.getString("userno")),"2",CommonTool.FormatString(rs.getString("curmoduleno"))));

								useroverall.setDescriptions(CommonTool.FormatString(rs.getString("modulename")));
								returnValue.add(useroverall);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return returnValue;
				}
			};
			userInfo.setLsUseroverall((List<Useroverall>) this.amn_Dao.executeQuery_ex(strSql, analysis3));
			analysis3=null;
			//---------------------获得角色启用的模组(去除个性屏蔽的模组) end------------------------------
		
			
			strSql=" select a.* From  sysrolemode a ,sysuserinfo b " +
					" where a.roleno=b.userrole and b.userno='"+strUserId+"' " +
					"   and functionno not in (select modevalue from useroverall c where c.userno='"+strUserId+"' and c.modetype='4'  ) "+
					"   and functionno not in (select functionno from  usereditright where userno='"+strUserId+"'  )";
					
			AnlyResultSet<List<Sysrolemode>> analysis1 = new AnlyResultSet<List<Sysrolemode>>() {
				public List<Sysrolemode> anlyResultSet(ResultSet rs) {
					List<Sysrolemode> returnValue = new ArrayList();
					try {
						while (rs != null && rs.next() == true) {
							Sysrolemode rolemode=new Sysrolemode();
							rolemode.setId(new SysrolemodeId(CommonTool.FormatString(rs.getString("roleno")),CommonTool.FormatString(rs.getString("sysmodeno")),CommonTool.FormatString(rs.getString("functionno"))));
							rolemode.setBrowsepurview(CommonTool.FormatString(rs.getString("browsepurview")));
							rolemode.setEditpurview(CommonTool.FormatString(rs.getString("editpurview")));
							rolemode.setExportpurview(CommonTool.FormatString(rs.getString("exportpurview")));
							rolemode.setPostpurview(CommonTool.FormatString(rs.getString("postpurview")));
							rolemode.setConfirmpurview(CommonTool.FormatString(rs.getString("confirmpurview")));
							rolemode.setInvalidpurview(CommonTool.FormatString(rs.getString("invalidpurview")));
							rolemode.setDisabledflag(CommonTool.FormatInteger(rs.getInt("disabledflag")));
							returnValue.add(rolemode);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			userInfo.setLsSysrolemode((List<Sysrolemode>) this.amn_Dao.executeQuery_ex(strSql, analysis1));
			if(userInfo.getLsSysrolemode()!=null && userInfo.getLsSysrolemode().size()>0)
			{
				strSql=" select  sysmodeno,functionno,browsepurview,editpurview,exportpurview,postpurview,confirmpurview,invalidpurview " +
						" From  usereditright a,sysmodeinfo b where a.userno='"+strUserId+"' and a.functionno=b.curmoduleno and b.modulevel='2'" ;
						//" and a.functionno not in (select functionno from  sysrolemode ,sysuserinfo where userno='"+strUserId+"'  and  userrole=roleno )";
				AnlyResultSet<List<Sysrolemode>> analysis5 = new AnlyResultSet<List<Sysrolemode>>() {
					public List<Sysrolemode> anlyResultSet(ResultSet rs) {
						List<Sysrolemode> returnValue = new ArrayList();
						try {
								Sysrolemode roleSpecilmode=null;
								while (rs != null && rs.next() == true) {						
									roleSpecilmode=new Sysrolemode();
									roleSpecilmode.setId(new SysrolemodeId("",CommonTool.FormatString(rs.getString("sysmodeno")),CommonTool.FormatString(rs.getString("functionno"))));
									roleSpecilmode.setBrowsepurview(CommonTool.FormatString(rs.getString("browsepurview")));
									roleSpecilmode.setEditpurview(CommonTool.FormatString(rs.getString("editpurview")));
									roleSpecilmode.setExportpurview(CommonTool.FormatString(rs.getString("exportpurview")));
									roleSpecilmode.setPostpurview(CommonTool.FormatString(rs.getString("postpurview")));
									roleSpecilmode.setConfirmpurview(CommonTool.FormatString(rs.getString("confirmpurview")));
									roleSpecilmode.setInvalidpurview(CommonTool.FormatString(rs.getString("invalidpurview")));
									returnValue.add(roleSpecilmode);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							return returnValue;						
					}
				};
				List<Sysrolemode> lsSysrolemode= (List<Sysrolemode> )this.amn_Dao.executeQuery_ex(strSql, analysis5);
				if(lsSysrolemode!=null && lsSysrolemode.size()>0)
				{
					for(int i=0;i<lsSysrolemode.size();i++)
					{
						userInfo.getLsSysrolemode().add(lsSysrolemode.get(i));
					}
					
				}
			}
			analysis1=null;
		}
		else
		{
			userInfo.setUserName("管理员");
			userInfo.setMangerFlag(1);
			strSql="  From  Sysmodeinfo sysmodeinfo where modulevel in (1,2)   "; 
			List<Sysmodeinfo> lssysmodeinfo=this.amn_Dao.findByHql(strSql);
			
			Useroverall useroverall=null;
			if(lssysmodeinfo!=null && lssysmodeinfo.size()>0)
			{
				for(int i=0;i<lssysmodeinfo.size();i++)
				{
					if(CommonTool.FormatInteger(lssysmodeinfo.get(i).getModulevel())==1)
					{
						useroverall=new Useroverall();
						useroverall.setId(new UseroverallId(strUserId,"2",lssysmodeinfo.get(i).getId().getCurmoduleno()));
				
						useroverall.setDescriptions(lssysmodeinfo.get(i).getModulename());
						lsUseroverall.add(useroverall);
					}
					else
					{
						lssysmodeinfosys.add(lssysmodeinfo.get(i));
					}
				}
			}
			userInfo.setLsUseroverall(lsUseroverall);
			userInfo.setLsSysmodeinfo(lssysmodeinfosys);
			lsUseroverall=null;
			useroverall=null;
			lssysmodeinfo=null;
			lssysmodeinfosys=null;
			strSql="  From  Sysrolemode sysrolemode where roleno='1'  ) "; 
			userInfo.setLsSysrolemode((List<Sysrolemode>) this.amn_Dao.findByHql(strSql));	
		}
		return userInfo;


//		private List<Useroverall> lsUseroverall;
//		private List<Sysrolemode> lsSysrolemode;
	}
	
	public Sysparaminfo loadSysparamInfoByCompId(String strCompId)
	{
		try
		{
			Sysparaminfo returnValue=new Sysparaminfo();
			String columnValue="";
			Method [] methods=null;
			String strSql=" From Sysparaminfo sysparaminfo where compid='"+strCompId+"' ";
			List<Sysparaminfo> lsSysparaminfo=(List<Sysparaminfo> )this.amn_Dao.findByHql(strSql);
			if(lsSysparaminfo!=null && lsSysparaminfo.size()>0)
			{
				methods = returnValue.getClass().getMethods();
				for (Sysparaminfo sysparaminfo : lsSysparaminfo) 
				{
					columnValue = sysparaminfo.getId().getParamid();
					for(int i=0;i<methods.length;i++)
					{
						if(methods[i].getName().startsWith("set"))
						{
							if(methods[i].getName().length()==8 && columnValue.equalsIgnoreCase(methods[i].getName().substring(3,8)))
							{
								methods[i].invoke(returnValue, methods[i].getParameterTypes()[0].cast(sysparaminfo.getParamvalue()));
								break;
							}
						}
					}
				}
			}
			return returnValue;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean changeUserPassword(String strUserNo,String strNewPassword)
	{
		try
		{
			String strSql=" update sysuserinfo set userpwd='"+desPlus.encrypt(strNewPassword)+"' where userno='"+strUserNo+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public String getUserName(String userId) {
		String strRet = "";
		

		return strRet;
	}

	public String getCompName(String compId) {
		String strRet = "";
		
		return strRet;
	}

	public DataTool getDataTool() {
		return dataTool;
	}

	public void setDataTool(DataTool dataTool) {
		this.dataTool = dataTool;
	}

	public OrgManager getOrgManager() {
		return orgManager;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}


}
