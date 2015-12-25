package com.amani.tools;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.sql.rowset.CachedRowSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.amani.action.AnlyResultSet;
import com.amani.bean.PringBillBean;
import com.amani.bean.ReEditBillInfo;
import com.amani.dao.AMN_DaoImp;
import com.amani.dao.AMN_PADDaoImp;
import com.amani.model.A3area;
import com.amani.model.A3city;
import com.amani.model.A3province;
import com.amani.model.Cardaccount;
import com.amani.model.Cardaccountchangehistory;
import com.amani.model.Cardchangehistory;
import com.amani.model.Cardinfo;
import com.amani.model.CardinfoId;
import com.amani.model.Cardproaccount;
import com.amani.model.CardproaccountId;
import com.amani.model.Cardtransactionhistory;
import com.amani.model.Cardtypeinfo;
import com.amani.model.Commoninfo;
import com.amani.model.Companyinfo;
import com.amani.model.Compwarehouse;
import com.amani.model.Dmpackageinfo;
import com.amani.model.Dpayinfo;
import com.amani.model.Goodsinfo;
import com.amani.model.Mcardchangeinfo;
import com.amani.model.Mcardrechargeinfo;
import com.amani.model.Mcooperatesaleinfo;
import com.amani.model.Msalecardinfo;
import com.amani.model.Projectinfo;
import com.amani.model.Staffinfo;
import com.amani.model.StaffinfoSimple;
import com.amani.model.Sysparaminfo;
import com.amani.old.model.Gbm01;



@Repository("dataTool")
public class DataTool extends HibernateDaoSupport
{
	@Resource(name="amn_Dao")
	private AMN_DaoImp amn_Dao;
	
	@Resource(name="amn_PADDao")
	private AMN_PADDaoImp amn_PADDao;
	
	@Resource(name="sessionFactory")
    private SessionFactory sessionFactory ;

	
	public AMN_DaoImp getAmn_Dao() {
		return amn_Dao;
	}

	public void setAmn_Dao(AMN_DaoImp amn_Dao) {
		this.amn_Dao = amn_Dao;
	}
	
	//获得门店资料
	public String loadCompNameById(String strCompId)
	{
		String strSql = "select compname from companyinfo where compno = "+ CommonTool.quotedStr(strCompId);
		AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
			public String anlyResultSet(ResultSet rs) {
				String returnValue = "";
				try {
					if (rs != null && rs.next() == true) {
						returnValue = rs.getString("compname");
					} else {
						returnValue = "";
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue = "";
				}
				return returnValue;
			}
		};
		String strCompname= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return strCompname;
	}
	public String loadCompIPById(String strCompId)
	{
		String strSql = "select compipaddress from companyinfo where compno = "+ CommonTool.quotedStr(strCompId);
		AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
			public String anlyResultSet(ResultSet rs) {
				String returnValue = "";
				try {
					if (rs != null && rs.next() == true) {
						returnValue = rs.getString("compipaddress");
					} else {
						returnValue = "";
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue = "";
				}
				return returnValue;
			}
		};
		String strCompname= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return strCompname;
	}
	public String loadCompIPexById(String strCompId)
	{
		String strSql = "select compipaddressex from companyinfo where compno = "+ CommonTool.quotedStr(strCompId);
		AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
			public String anlyResultSet(ResultSet rs) {
				String returnValue = "";
				try {
					if (rs != null && rs.next() == true) {
						returnValue = rs.getString("compipaddressex");
					} else {
						returnValue = "";
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue = "";
				}
				return returnValue;
			}
		};
		String strCompname= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return strCompname;
	}
	
	//获得门店地址
	public String loadCompAddressById(String strCompId)
	{
		String strSql = "select compaddress from companyinfo where compno = "+ CommonTool.quotedStr(strCompId);
		AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
			public String anlyResultSet(ResultSet rs) {
				String returnValue = "";
				try {
					if (rs != null && rs.next() == true) {
						returnValue = rs.getString("compaddress");
					} else {
						returnValue = "";
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue = "";
				}
				return returnValue;
			}
		};
		String strAddtess= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return strAddtess;
	}
	
	//获得员工资料
	public boolean loadEmpInidNameById(String strCompId,String strEmpId,StringBuffer validateMsg ,StringBuffer staffnamebuf ,StringBuffer managenobuf )
	{
		String strSql = "select staffname,manageno from staffinfo where compno = "+ CommonTool.quotedStr(strCompId)+" and staffno='"+strEmpId+"' ";
		AnlyResultSet<List> analysis = new AnlyResultSet<List>() {
			public List anlyResultSet(ResultSet rs) {
				List returnValue = new ArrayList();
				try {
					if (rs != null && rs.next() == true) {
						returnValue.add(rs.getString("staffname"));
						returnValue.add(rs.getString("manageno"));
					} else {
						returnValue.add("");
						returnValue.add("");
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue.add("");
					returnValue.add("");
				}
				return returnValue;
			}
		};
		List lReturnValue= (List) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		if(lReturnValue!=null && CommonTool.FormatString(lReturnValue.get(0)).equals(""))
		{
			validateMsg.append("员工编号不存在");
			staffnamebuf.append("");
			managenobuf.append("");
			return false;
		}
		staffnamebuf.append(lReturnValue.get(0));
		managenobuf.append(lReturnValue.get(1));
		validateMsg.append("");
		lReturnValue=null;
		return true;
	}
	//获得员工资料
	public String loadEmpNameById(String strCompId,String strEmpId,StringBuffer validateMsg )
	{
		String strSql = "select staffname from staffinfo where compno = "+ CommonTool.quotedStr(strCompId)+" and staffno='"+strEmpId+"' ";
		AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
			public String anlyResultSet(ResultSet rs) {
				String returnValue = "";
				try {
					if (rs != null && rs.next() == true) {
						returnValue = rs.getString("staffname");
					} else {
						returnValue = "";
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue = "";
				}
				return returnValue;
			}
		};
		String strReturnValue= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
		if(CommonTool.FormatString(strReturnValue).equals(""))
		{
			validateMsg.append("员工编号不存在");
			return "";
		}
		validateMsg.append("");
		return strReturnValue;
	}
	public  boolean hasFunctionRights(String strCurrUserId,String moduleName, final String right) {
		if (strCurrUserId.equalsIgnoreCase(SystemFinal.KING_USER))
			return true;
		boolean bReturnValue = false;
		String strSql="select 1 from usereditright where userno='"+strCurrUserId+"' and functionno='"+moduleName+"' ";
		AnlyResultSet<Boolean> analysisflag = new AnlyResultSet<Boolean>() 
		{
			public Boolean anlyResultSet(ResultSet rs) 
			{
				boolean bReturnValue = false;
				try 
				{
					if( rs != null && rs.next())
					{
						bReturnValue =  true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					bReturnValue = false;
				}
				return bReturnValue;
			}
		};
		boolean existsflag= (Boolean)this.amn_Dao.executeQuery_ex(strSql, analysisflag);
		analysisflag=null;
		if(existsflag==false)
		{
			strSql = "select browsepurview,editpurview,exportpurview,postpurview,confirmpurview,Invalidpurview" +
				        "  from  sysrolemode,sysuserinfo where userno = "+CommonTool.quotedStr(strCurrUserId) 
						+" and roleno=userrole and functionno = "+CommonTool.quotedStr(moduleName);
		}
		else
		{
			strSql = "select browsepurview,editpurview,exportpurview,postpurview,confirmpurview,Invalidpurview " +
				"  from  usereditright where userno = "+CommonTool.quotedStr(strCurrUserId) 
				+" and functionno = "+CommonTool.quotedStr(moduleName);
		}
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() 
		{
			public Boolean anlyResultSet(ResultSet rs) 
			{
				boolean bReturnValue = false;
				try {
						
						if( rs != null && rs.next())
						{
							if (right.equalsIgnoreCase(SystemFinal.UR_MODIFY)) {
								if (rs.getString("editpurview").equalsIgnoreCase("Y")) {
									bReturnValue =  true;
								}
							} else if (right.equalsIgnoreCase(SystemFinal.UR_DELETE)) {
								if (rs.getString("Invalidpurview").equalsIgnoreCase("Y")) {
									bReturnValue =  true;
								}
							} else if (right.equalsIgnoreCase(SystemFinal.UR_QUERY)) {
								if (rs.getString("browsepurview").equalsIgnoreCase("Y")) {
									bReturnValue =  true;
								}
							} else if (right.equalsIgnoreCase(SystemFinal.UR_PRINT)) {
								if (rs.getString("exportpurview").equalsIgnoreCase("Y")) {
									bReturnValue =  true;
								}
							} else if (right.equalsIgnoreCase(SystemFinal.UR_POST)) {
								if (rs.getString("postpurview").equalsIgnoreCase("Y")) {
									bReturnValue =  true;
								}
							} else if (right.equalsIgnoreCase(SystemFinal.UR_SPECIAL_CHECK)) {
								if (rs.getString("confirmpurview").equalsIgnoreCase("Y")) {
									bReturnValue =  true;
								}
							}				
						}
				} catch (Exception e) {
					e.printStackTrace();
					bReturnValue = false;
				}
				return bReturnValue;
			}
		};
		return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
	}
	
	public  boolean hasSpecialRights(String strCurrUserId,  String rightSeqno) {
		if (strCurrUserId.equalsIgnoreCase(SystemFinal.KING_USER))
			return true;
		String strSql = "select 1 from sysuserinfo,sysmodepurview where userno = "+CommonTool.quotedStr(strCurrUserId) 
		+" and curRoleno=userrole and curpurviewno = "+CommonTool.quotedStr(rightSeqno);
	    strSql =strSql+ " and curpurviewno not in (select modevalue from  useroverall where userno = "+CommonTool.quotedStr(strCurrUserId)+" and  modetype='5' )";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() 
		{
			public Boolean anlyResultSet(ResultSet rs) 
			{
				boolean bReturnValue = false;
				try 
				{
					if( rs != null && rs.next())
					{
						bReturnValue =  true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					bReturnValue = false;
				}
				return bReturnValue;
			}
		};
		return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
	}

	public AMN_PADDaoImp getAmn_PADDao() {
		return amn_PADDao;
	}

	public void setAmn_PADDao(AMN_PADDaoImp amn_PADDao) {
		this.amn_PADDao = amn_PADDao;
	}
	
	
	//
	//	获得系统参数的值 
	//
	//String strValue   要获得参数值 传值格式 "SP001-2"
	//返回值:   如果存在就返回参数值，否则返回的"";
	public String loadSysParam(String strCompId,String strValue)
	{
		
		String strField = "";
		String strSql = "";
		String strReturnValue = "";
		if(strValue == null || strValue.equals(""))
		{
			return strReturnValue;
		}
		else
		{
			strSql = "select paramid,paramvalue from sysparaminfo where compid = '"+strCompId+"' and paramid = "+CommonTool.quotedStr(strValue);
			AnlyResultSet<String> analysis = new AnlyResultSet<String>() 
			{
				public String anlyResultSet(ResultSet rs) 
				{
					String bReturnValue = "";
					try 
					{
						if( rs != null && rs.next())
						{
							bReturnValue =  CommonTool.FormatString(rs.getString("paramvalue"));
						}
					} catch (Exception e) {
						e.printStackTrace();
						bReturnValue = "";
					}
					return bReturnValue;
				}
			};
			return (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
	
	}
	
	public boolean AutoKey(String Date,
			String TblName,
            String FieldName,
            String FormatStr,
            String CompId,
            String InputStr,
            StringBuffer RNo,
            String CompIdFieldName)
		{
		String sql;
		RNo.reverse();
		RNo.append("");
		CompId = CompId.trim();
		if(!InputStr.equals(""))
		{
		    sql="SELECT " +  FieldName+ " FROM " +TblName
		        + " WHERE "+FieldName+ "="+ CommonTool.quotedStr(InputStr);
		    if((!CompIdFieldName.equals(""))&&(!CompId.equals("")))// type
		        sql+=" AND "+CompIdFieldName +" = "+ CommonTool.quotedStr(CompId);
		}
		char[] out= new char[100];
		if(CommonTool.ifAutoKeyByComp(FormatStr) && CompId.equals(""))
		    return false;
		
		if(CommonTool.ifAutoKeyByDate(FormatStr) && Date.equals(""))
		    return false;
		
		//inputstr
		int nXXX=CommonTool.checkAutoKey(FormatStr);
		if(nXXX==-1)
		    return false;
		else if(nXXX>0)
		{
		    if(InputStr.length()<nXXX)
		        return false;
		    else
		        InputStr=InputStr.substring(InputStr.indexOf("x"),nXXX);
		}
		char[] ord= new char[50];
		int cc=0,c9=0,cy=0,cm=0,cx=0,cd=0,ci=0;
		int ordpos=0;
		int i=0;
		char[] szFormatStr= new char[100];
		char[] buf= new char[100];
		
		
		System.arraycopy(FormatStr.toCharArray(),0,szFormatStr,0,FormatStr.length());
		System.arraycopy(FormatStr.toCharArray(),0,buf,0,FormatStr.length());		
		FormatStr=FormatStr.toLowerCase();
		while(buf[i] !='\0')
		{
		    if(buf[i]=='C')
		    {
		        if(cc==0)
		            ord[ordpos++]='C';
		        cc++;
		    }
		    else if(buf[i]=='Y')
		    {
		        if(cy==0)
		            ord[ordpos++]='Y';
		        cy++;
		    }
		    else if(buf[i]=='M')
		    {
		        if(cm==0)
		            ord[ordpos++]='M';
		        cm++;
		    }
		    else if(buf[i]=='D')
		    {
		        if(cd==0)
		            ord[ordpos++]='D';
		        cd++;
		    }
		    else if(buf[i]=='9')
		    {
		        if(c9==0)
		            ord[ordpos++]='9';
		        c9++;
		    }
		    else
		    {
		        ord[ordpos++]=szFormatStr[i];
		    }
		    i++;
		}
		//
		for(int j=i;j<50;j++)
		{
			ord[j] ='\0';
			j++;
		}
		//compid
		if(cc>0 && CompId.length()==0)
		    return false;
		if(cy>4 || cm>2 || cd>2)
		    return false;
		
		char inputstr[] = new char[100];
		System.arraycopy(InputStr.toCharArray(), 0, inputstr, 0, InputStr.length());
		String strCurrDate;
		char year[] = new char[5];
		char month[] = new char[3];
		char day[] = new char[3];
		
		if(Date=="")
		    strCurrDate = CommonTool.getCurrDate();
		else
		    strCurrDate=Date;
		int nDateType = 1;
		String strTmpYear = strCurrDate.substring(4-cy,4);
		String strTmpMonth = strCurrDate.substring(4,6);
		String strTmpDay = strCurrDate.substring(6,8);
		
		
		System.arraycopy(strTmpYear.toCharArray(), 0, year, 0, strTmpYear.length());
		System.arraycopy(strTmpMonth.toCharArray(), 0, month, 0, strTmpMonth.length());
		System.arraycopy(strTmpDay.toCharArray(), 0, day, 0, strTmpDay.length());
		ord[ordpos]=0;
		//write string
		int outpos=0;
		ordpos=0;
		while(ord[ordpos]!='\0')
		{
		    switch(ord[ordpos])
		    {
		        case 'C':
		            for(i=0;i<CompId.length();i++)
		            	out[outpos++] = CompId.charAt(i);
		            break;
		
		        case 'X':
		            for(i=0;i<cx;i++)
		                if(inputstr[i] !='\0')
		                    out[outpos++]=inputstr[i];
		                else
		                    out[outpos++]=inputstr[0];
		            break;
		
		        case 'Y':
		            for(i=0;i<cy;i++)
		                if(year[i] !='\0')
		                    out[outpos++]=year[i];
		                else
		                    out[outpos++]=year[0];
		            break;
		        case 'M':
		            switch(cm)
		            {
		                case 1:
		                    if(month[0]=='0')
		                        out[outpos++]=month[1];
		                    else 
		                    	out[outpos++]= 'A';
		                        //    out[outpos++]=month[1]-'0'+'A';
		                    break;
		                case 2:
		                    for(i=0;i<2;i++)
		                        out[outpos++]=month[i];
		            }
		            break;
		        case 'D':
		            for(i=0;i<cd;i++)
		                if(day[i] !='\0')
		                    out[outpos++]=day[i];
		                else
		                    out[outpos++]=day[0];
		            break;
		        case '9':
		            break;
		        default :
		            out[outpos++]=ord[ordpos];
		            break;
		    }
		    ordpos++;
		}
		
		out[outpos]=0;
		
		//seq no
		Query qryTmp;
		sql="SELECT mn=MAX("+FieldName+") FROM "+TblName;
		sql+=" WHERE "+FieldName+ " LIKE "+ CommonTool.quotedStr( new String(out).trim()+"%");
		if((!CompIdFieldName.equals(""))&&(!CompId.equals("")))// type
		    sql+=" AND "+CompIdFieldName +" = "+ CommonTool.quotedStr(CompId);
		ResultSet rs = null;
		try
		{
			rs = this.amn_Dao.executeSqlNoTran(sql);
			if(rs != null && rs.next())
			{
				
			    char maxno[] = new char[50];
			    int l1,l2,l3;
			    if(rs.getString("mn") != null && !rs.getString("mn").equals(""))
			    {
			    	int tmplenght = new String(out).trim().length();   
			    	String strEmpBill =  "";
			    	strEmpBill =  rs.getString("mn");
			    	strEmpBill =  strEmpBill.substring(0, tmplenght+c9);	
			    	System.arraycopy(strEmpBill.toCharArray(), 0, maxno, 0, strEmpBill.length());
			    
			    	l1 = new String(out).trim().length();
			    	l2 = new String(maxno).trim().length();
			    	if(l2<=l1)
			    	{
			    		return false;
			    	}
			    	int tmp = Integer.parseInt(new String(maxno).trim().substring(l1, new String(maxno).trim().length()));
			    	if(tmp==0)
			    	{
			    		return false;
			    	}
			    	tmp++;//自动增加
			    	char buffer[] = new char[20];
			    	System.arraycopy(String.valueOf(tmp).toCharArray(), 0, buffer, 0, String.valueOf(tmp).length());
			    	if( new String(buffer).trim().length() > c9)//have 999
			    	{
			    		return false;
			    	}
			    	l3=c9-new String(buffer).trim().length();
			    	int j1=0,j2=0;
			    	for(j1=l1;j1<l3+l1;j1++)
			    		out[j1]='0';
			    	while(buffer[j2]!='\0')
			    	{
			    		out[j1]=buffer[j2];
			    		j1++;
			    		j2++;
			    	}
			    	out[j1]=0;
			    }
			    else
				{
				    int l5;
				    l5 = new String(out).trim().length();
				    int j1;
				    for(j1=l5;j1<l5+c9-1;j1++)
				        out[j1]='0';
				    out[j1++]='1';
				    out[j1]=0;
				}
			}
			else
			{
			    int l5;
			    l5 = new String(out).trim().length();
			    int j1;
			    for(j1=l5;j1<l5+c9-1;j1++)
			        out[j1]='0';
			    out[j1++]='1';
			    out[j1]=0;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				((CachedRowSet)rs).release();
				rs.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		RNo.append(new String(out).trim());
		return true;
	}
	
	
	public String loadBillIdByRule(String CompId,String TblName,String FieldName,String strRuleParam)
	{
		String Date=CommonTool.getCurrDate();
		String FormatStr=CommonTool.FormatString(this.loadSysParam(CompId,strRuleParam));
		String InputStr="";
		StringBuffer RNo=new StringBuffer();
		String CompIdFieldName="";
		this.AutoKey(Date, TblName, FieldName, FormatStr, CompId, InputStr, RNo, CompIdFieldName);
		return RNo.toString();
	}
   //JSONArray.fromObject(lsDataSet).toString()
	public  List loadDTOList(String jsonString ,Class obj){ 
		if(jsonString==null || jsonString.equals(""))
			return null;
		JSONArray array = JSONArray.fromObject(jsonString); 
		List list = new ArrayList(); 
		for(
				Iterator iter = array.iterator();
				iter.hasNext();){ 
				JSONObject jsonObject = (JSONObject)iter.next(); 
				if(jsonObject!=null)
				{
					jsonObject.remove("__id");
					jsonObject.remove("__previd");
					jsonObject.remove("__index");
					jsonObject.remove("__nextid");
					jsonObject.remove("__status");
				}
				list.add(JSONObject.toBean(jsonObject, obj)); 
		} 
	
		return list; 
		}
	
	public String loadCompName(String strCurBillCompId,String stCompId,int vleef,StringBuffer validateMsg)
	{
		validateMsg.append("");
		String strField = "";
		String strSql = "";
		String strReturnValue = "";
		if(stCompId == null || stCompId.equals(""))
		{
			
			return strReturnValue;
		}
		else
		{
			if(vleef==1)//验证连锁
			{
				strSql = "select compname from companyinfo,compchaininfo where compno=relationcomp and  curcomp= '"+strCurBillCompId+"' and relationcomp='"+stCompId+"' ";
			}
			else
			{
				strSql = "select compname from companyinfo where compno = '"+stCompId+"' ";
			}
			AnlyResultSet<String> analysis = new AnlyResultSet<String>() 
			{
				public String anlyResultSet(ResultSet rs) 
				{
					String bReturnValue = "";
					try 
					{
						if( rs != null && rs.next())
						{
							bReturnValue =  CommonTool.FormatString(rs.getString("compname"));
						}
					} catch (Exception e) {
						e.printStackTrace();
						bReturnValue = "";
					}
					return bReturnValue;
				}
			};
			strReturnValue= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
			if(CommonTool.FormatString(strReturnValue).equals(""))
			{
				validateMsg.append("门店编号不存在或不在本公司的归属下级门店");
				return "";
			}
			return strReturnValue;
		}
	
	}
	
	public Projectinfo loadProjectInfo(String strCompId,String strProjectId)
	{
		String strField = "";
		String strSql = "";
		if(strProjectId == null || strProjectId.equals(""))
		{
			
			return null;
		}
		else
		{
			String strModeId=this.loadSysParam(strCompId,"SP059");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			strSql = "select projectinfo from Projectinfo projectinfo,Compchaininfo compchaininfo " +
					"where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and prjno = '"+strProjectId+"' and  curcomp=prisource and relationcomp='"+strCompId+"' ";
			List<Projectinfo> lsProjectinfo=(List<Projectinfo>)this.amn_Dao.findByHql(strSql);
			if(lsProjectinfo!=null && lsProjectinfo.size()>0)
			{
				return lsProjectinfo.get(0);
			}
			else
			{
				return null;
			}
		}
	
	}
	
	
	
	public List<Projectinfo> loadProjectinfoByCompId(String strCompId , int useflag)
	{
		try
		{
			String strModeId=this.loadSysParam(strCompId,"SP059");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			String strSql=" select projectinfo From Projectinfo projectinfo,Compchaininfo compchaininfo where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')  and curcomp=  prisource and relationcomp='"+strCompId+"' and (isnull(useflag,1)="+useflag+" or "+useflag+"=3 ) ";
			return this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获得疗程项目
	public List<Projectinfo> loadProinfoByCompId(String strCompId)
	{
		try
		{
			String strModeId=this.loadSysParam(strCompId,"SP059");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			String strSql=" select projectinfo From Projectinfo projectinfo,Compchaininfo compchaininfo where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')  and curcomp=  prisource and relationcomp='"+strCompId+"' and isnull(prjsaletype,0)=1 ";
			return this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Goodsinfo> loadGoodsinfoByCompId(String strCompId ,int userflag)
	{
		try
		{
			String strModeId=this.loadSysParam(strCompId,"SP061");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			String strSql=" select goodsinfo From Goodsinfo goodsinfo,Compchaininfo compchaininfo where goodsmodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')  and curcomp=goodssource and relationcomp='"+strCompId+"'  and ( isnull(useflag,0) ="+userflag+" or "+userflag+"=2)  ";
			return this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Goodsinfo loadGoodsInfo(String strCompId,String strGoodsId)
	{
	
		String strField = "";
		String strSql = "";
		String strReturnValue = "";
		if(strGoodsId == null || strGoodsId.equals(""))
		{
			
			return null;
		}
		else
		{
			String strModeId=this.loadSysParam(strCompId,"SP061");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			strSql = "select goodsinfo from Goodsinfo goodsinfo,Compchaininfo compchaininfo " +
					"where goodsmodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') " +
					" and (goodsno = '"+strGoodsId+"' or  goodsbarno = '"+strGoodsId+"' or goodsabridge = '"+strGoodsId+"') " +
					" and  curcomp=goodssource and relationcomp='"+strCompId+"' ";
			List<Goodsinfo> lsGoodsinfo=(List<Goodsinfo>)this.amn_Dao.findByHql(strSql);
			if(lsGoodsinfo!=null && lsGoodsinfo.size()>0)
			{
				return lsGoodsinfo.get(0);
			}
			else
			{
				return null;
			}
		}
	
	}
	
	public Goodsinfo loadGoodsInfoBybar(String strCompId,String strGoodsId)
	{
	
		String strField = "";
		String strSql = "";
		String strReturnValue = "";
		if(strGoodsId == null || strGoodsId.equals(""))
		{
			
			return null;
		}
		else
		{
			String strModeId=this.loadSysParam(strCompId,"SP061");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			strSql = "select goodsinfo from Goodsinfo goodsinfo,Compchaininfo compchaininfo,Dgoodsbarinfo dgoodsbarinfo " +
					"where goodsmodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') " +
					" and dgoodsbarinfo.id.goodsbarno = '"+strGoodsId+"' and goodsinfo.id.goodsno=dgoodsbarinfo.id.goodsno  and barnostate=2 and receivestore='"+strCompId+"' " +
					" and  curcomp=goodssource and relationcomp='"+strCompId+"' ";
			List<Goodsinfo> lsGoodsinfo=(List<Goodsinfo>)this.amn_Dao.findByHql(strSql);
			if(lsGoodsinfo!=null && lsGoodsinfo.size()>0)
			{
				return lsGoodsinfo.get(0);
			}
			else
			{
				return null;
			}
		}
	
	}
	
	public String loadProjectName(String strCompId,String strProjectId,StringBuffer validateMsg)
	{
		validateMsg.append("");
		String strField = "";
		String strSql = "";
		String strReturnValue = "";
		if(strProjectId == null || strProjectId.equals(""))
		{
			
			return strReturnValue;
		}
		else
		{
			String strModeId=this.loadSysParam(strCompId,"SP059");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			strSql = "select prjname from projectinfo,compchaininfo where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')  and prjno = '"+strProjectId+"' and  curcomp=prisource and relationcomp='"+strCompId+"' ";
			AnlyResultSet<String> analysis = new AnlyResultSet<String>() 
			{
				public String anlyResultSet(ResultSet rs) 
				{
					String bReturnValue = "";
					try 
					{
						if( rs != null && rs.next())
						{
							bReturnValue =  CommonTool.FormatString(rs.getString("prjname"));
						}
					} catch (Exception e) {
						e.printStackTrace();
						bReturnValue = "";
					}
					return bReturnValue;
				}
			};
			strReturnValue= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
			if(CommonTool.FormatString(strReturnValue).equals(""))
			{
				validateMsg.append("项目编号不存在");
				return "";
			}
			return strReturnValue;
		}
	
	}
	
	public String loadGoodsName(String strCompId,String strGoodsId,StringBuffer validateMsg)
	{
		validateMsg.append("");
		String strField = "";
		String strSql = "";
		String strReturnValue = "";
		if(strGoodsId == null || strGoodsId.equals(""))
		{
			
			return strReturnValue;
		}
		else
		{
			String strModeId=this.loadSysParam(strCompId,"SP061");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			strSql = "select goodsname from goodsinfo,compchaininfo where goodsmodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and goodsno = '"+strGoodsId+"' and  curcomp=goodssource and relationcomp='"+strCompId+"' ";
			AnlyResultSet<String> analysis = new AnlyResultSet<String>() 
			{
				public String anlyResultSet(ResultSet rs) 
				{
					String bReturnValue = "";
					try 
					{
						if( rs != null && rs.next())
						{
							bReturnValue =  CommonTool.FormatString(rs.getString("goodsname"));
						}
					} catch (Exception e) {
						e.printStackTrace();
						bReturnValue = "";
					}
					return bReturnValue;
				}
			};
			strReturnValue= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
			if(CommonTool.FormatString(strReturnValue).equals(""))
			{
				validateMsg.append("产品编号不存在");
				return "";
			}
			return strReturnValue;
		}
	
	}
	
	public String loadCardTypeName(String strCompId,String strCardTypeId,StringBuffer validateMsg)
	{
		validateMsg.append("");
		String strField = "";
		String strSql = "";
		String strReturnValue = "";
		if(strCardTypeId == null || strCardTypeId.equals(""))
		{
			
			return strReturnValue;
		}
		else
		{
			String strModeId=this.loadSysParam(strCompId,"SP063");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			strSql = "select cardtypename from cardtypeinfo,compchaininfo where cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and cardtypeno = '"+strCardTypeId+"' and  curcomp=cardtypesource and relationcomp='"+strCompId+"' ";
			AnlyResultSet<String> analysis = new AnlyResultSet<String>() 
			{
				public String anlyResultSet(ResultSet rs) 
				{
					String bReturnValue = "";
					try 
					{
						if( rs != null && rs.next())
						{
							bReturnValue =  CommonTool.FormatString(rs.getString("cardtypename"));
						}
					} catch (Exception e) {
						e.printStackTrace();
						bReturnValue = "";
					}
					return bReturnValue;
				}
			};
			strReturnValue= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
			if(CommonTool.FormatString(strReturnValue).equals(""))
			{
				validateMsg.append("卡类别编号不存在");
				return "";
			}
			return strReturnValue;
		}
	
	}
	
	
	public BigDecimal loadGoodsRateByCardType(String strCompId,String strCardTypeId)
	{
		String strSql = "";
		if(strCardTypeId == null || strCardTypeId.equals(""))
		{
			return new BigDecimal(1);
		}
		else
		{
			String strModeId=this.loadSysParam(strCompId,"SP063");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			strSql = "select slaegoodsrate from cardtypeinfo,compchaininfo where cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and cardtypeno = '"+strCardTypeId+"' and  curcomp=cardtypesource and relationcomp='"+strCompId+"' ";
			AnlyResultSet<BigDecimal> analysis = new AnlyResultSet<BigDecimal>() 
			{
				public BigDecimal anlyResultSet(ResultSet rs) 
				{
					BigDecimal bReturnValue =new BigDecimal(1);
					try 
					{
						if( rs != null && rs.next())
						{
							bReturnValue =  CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("slaegoodsrate"))));
						}
					} catch (Exception e) {
						e.printStackTrace();
						bReturnValue = new BigDecimal(1);
					}
					return bReturnValue;
				}
			};
			return (BigDecimal) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
	
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
	
	
	
	public int loadCompLvl(String  strCompId)
	{
		String strSql=" select complevel from compchainstruct where curcompno='"+strCompId+"' ";
		AnlyResultSet<Integer> analysis = new AnlyResultSet<Integer>() 
		{
			public Integer anlyResultSet(ResultSet rs) 
			{
				int bReturnValue = 0;
				try 
				{
					if( rs != null && rs.next())
					{
						bReturnValue =  CommonTool.FormatInteger(rs.getInt("complevel"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					bReturnValue = 0;
				}
				return bReturnValue;
			}
		};
		return (Integer) this.amn_Dao.executeQuery_ex(strSql, analysis);
	}
	
    /**********获得卡类别******/
    public List<Cardtypeinfo> loadCardType(String strCompId)
	{
	    try
	    {
	    	String strModeId=this.loadSysParam(strCompId,"SP063");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			
	    	String strSql=" select cardtypeinfo From Cardtypeinfo cardtypeinfo,Compchaininfo compchaininfo  where cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and curcomp=  cardtypesource and relationcomp='"+strCompId+"'  order by cardtypeno";
	    	return (List<Cardtypeinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
    
    
    public List<Cardtypeinfo> loadCardTypeApp(String strCompId)
	{
	    try
	    {
	    	String strModeId=this.loadSysParam(strCompId,"SP063");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=loadparentModeId(strSecondModeId);
			}
			
	    	String strSql=" select cardtypeinfo From Cardtypeinfo cardtypeinfo,Compchaininfo compchaininfo  where cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and curcomp=  cardtypesource and relationcomp='"+strCompId+"' and isnull(openflag,1)=1  order by cardtypeno";
	    	return (List<Cardtypeinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
    
    public String loadparentModeId(String strCurModeId)
	{
		String strSql="  select parentmodeid from syscommoninfomode where  isnull(modeid,'')='"+strCurModeId+"' ";
		try
		{
			AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
				public String anlyResultSet(ResultSet rs) {
					String returnValue = "";
					try {
						if (rs != null && rs.next() == true) {
							returnValue=CommonTool.FormatString(rs.getString("parentmodeid"));
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue ="";
					}
					return returnValue;
				}
			};
			String parentmodeid= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return parentmodeid;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
    
    public List<Commoninfo> loadCommonInfoByCode(String strCodeno)
	{
		String strSql="  From Commoninfo commoninfo where  isnull(infotype,'')='"+strCodeno+"'   ";
		try
		{
			return this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
    
  //获得门店仓库资料
	public List<Compwarehouse> loadCompWareById(String strCompId)
	{
		try
	    {
			String strSql = " From Compwarehouse compwarehouse  where compno = "+ CommonTool.quotedStr(strCompId);
			return (List<Compwarehouse>)this.amn_Dao.findByHql(strSql);
	    }
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
    
	public String loadCompWareNameById(String strCompId,String strWareId)
	{
		try
	    {
			String strSql = "select warehousename From  compwarehouse  where compno = "+ CommonTool.quotedStr(strCompId)+" and warehouseno='"+strWareId+"' ";
			AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
				public String anlyResultSet(ResultSet rs) {
					String returnValue = "";
					try {
						if (rs != null && rs.next() == true) {
							returnValue=CommonTool.FormatString(rs.getString("warehousename"));
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue ="";
					}
					return returnValue;
				}
			};
			String compwarename= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return compwarename;
	    }
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
    /**********获得门店******/
    public Map loadCompLvlInfo(String strCompId)
	{
    	String strSql=" select compno,compname from companyinfo,compchaininfo where curcomp='"+strCompId+"' relationcomp=compno order by cardtypeno";
    	AnlyResultSet<Map> analysis = new AnlyResultSet<Map>()
		{
			public Map anlyResultSet(ResultSet rs)
			{
				Map returnValue=new TreeMap();
				try
				{
					while(rs != null&&rs.next())
					{
						returnValue.put(rs.getString(1), rs.getString(2));
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				return returnValue;
			}
		};
		return (Map)this.amn_Dao.executeQuery_ex(strSql,analysis);
	}
    
    public List<Companyinfo> loadCompanyInfo()
    {
    	String strSql=" From Companyinfo companyinfo ";
    	return (List<Companyinfo>)this.amn_Dao.findByHql(strSql);
    }
    
    public Companyinfo loadCurCompanyInfo(String strCurCompId)
    {
    	String strSql=" From Companyinfo companyinfo where compno='"+strCurCompId+"' ";
    	List<Companyinfo> lsCompanyinfo=(List<Companyinfo>)this.amn_Dao.findByHql(strSql);
    	if(lsCompanyinfo!=null && lsCompanyinfo.size()>0)
    		return lsCompanyinfo.get(0);
    	else
    		return null;
    }
    
    public List<Staffinfo> loadEmpsByCompId(String strCompId,int empType)
	{
		try
		{
			String strSql="select staffno,staffname,position,department,fingerno,curstate,manageno,hairqualified from staffinfo where compno='"+strCompId+"' and (isnull(businessflag,0)="+empType+" or "+empType+"=2) ";
			AnlyResultSet<List<Staffinfo>> analysis = new AnlyResultSet<List<Staffinfo>>()
			{
				public List<Staffinfo> anlyResultSet(ResultSet rs)
				{
					List<Staffinfo> returnValue = new ArrayList();
					Staffinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Staffinfo();
							record.setBstaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setPosition(CommonTool.FormatString(rs.getString("position")));
							record.setDepartment(CommonTool.FormatString(rs.getString("department")));
							record.setFingerno(CommonTool.FormatInteger(rs.getInt("fingerno")));
							record.setManageno(CommonTool.FormatString(rs.getString("manageno")));
							record.setCurstate(CommonTool.FormatString(rs.getString("curstate")));
							record.setHairqualified(CommonTool.FormatInteger(rs.getInt("hairqualified")));
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
			List<Staffinfo> ls= (List<Staffinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
    
	
	public String loadEmpInidById(String strCompId,String strEmpId)
	{
		String strSql = "select manageno from staffinfo where compno = "+ CommonTool.quotedStr(strCompId)+" and staffno='"+strEmpId+"' ";
		AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
			public String anlyResultSet(ResultSet rs) {
				String returnValue = "";
				try {
					if (rs != null && rs.next() == true) {
						returnValue = rs.getString("manageno");
					} else {
						returnValue = "";
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue = "";
				}
				return returnValue;
			}
		};
		String strReturnValue= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return strReturnValue;
	}
	
	public boolean bandcardinfo(String strCardNo)
	{
		try
		{
			String strSql=" select count(1) from oldcardinfo where cardno='"+strCardNo+"' ";
			int ccount=this.amn_Dao.getRowsCount_Ex(strSql);
			if(ccount>0)
			{
				strSql=" insert cardinfo(cardvesting,cardno,cardtype,membernotocard,salecarddate,cutoffdate,cardstate,salebillno,costpassword,searchpassword,cardremark,cardsource,costcountbydebts,costamtbydebts,costamt)" +
						" select cardvesting,cardno,cardtype,membernotocard,salecarddate,cutoffdate,cardstate,salebillno,costpassword,searchpassword,cardremark,cardsource,costcountbydebts,costamtbydebts,costamt " +
						" from oldcardinfo where cardno='"+strCardNo+"' ";
				strSql=strSql+" delete oldcardinfo where cardno='"+strCardNo+"' ";
				return this.amn_Dao.executeSql(strSql);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
    public Cardinfo loadCardinfobyCardNo(String strCompId, String strCardNo)
    {
    	 boolean flag=this.bandcardinfo(strCardNo);
    	 if(flag==false)
    		 return null;
    	 String strModeId=this.loadSysParam(strCompId,"SP063");
	     String strFristModeId="";//第一级模板Id
		 String strSecondModeId="";//第2级模板ID
		 String strThirthModeId="";//第三级模板Id
		 //先定位模板门店的连锁级别(暂时支持4级连锁)
		 int compLvl=this.loadCompLvl(strCompId);
	     if(compLvl==2)
		 {
			strFristModeId=this.loadparentModeId(strModeId);
		 }
		 else if(compLvl==3)
		 {
			strSecondModeId=this.loadparentModeId(strModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=this.loadparentModeId(strSecondModeId);
		 }
		 else if(compLvl==4)
		 {
			strThirthModeId=this.loadparentModeId(strModeId);
			if(!strThirthModeId.equals(""))
				strSecondModeId=this.loadparentModeId(strThirthModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=this.loadparentModeId(strSecondModeId);
		 }
    	String strSql="select a.cardvesting,a.cardno,a.cardstate,a.salecarddate,a.cutoffdate,a.cardsource,membersex,a.cardtype,cardtypename,membername,membermphone,memberpaperworkno,lowopenamt,lowfillamt,openflag,fillflag,changeflag,sendamtflag,slaeproerate,changerule, " +
    			" accountbalance2=sum(case when isnull(accounttype,0)=2 then accountbalance else 0 end ),accountdebts2=sum(case when isnull(accounttype,0)=2 then accountdebts else 0 end ), " +
    			" accountbalance3=sum(case when isnull(accounttype,0)=3 then accountbalance else 0 end ),accountdebts3=sum(case when isnull(accounttype,0)=3 then accountdebts else 0 end ), " +
    			" accountbalance4=sum(case when isnull(accounttype,0)=4 then accountbalance else 0 end ),accountdebts4=sum(case when isnull(accounttype,0)=4 then accountdebts else 0 end ), " +
    			" accountbalance5=sum(case when isnull(accounttype,0)=5 then accountbalance else 0 end ),accountdebts5=sum(case when isnull(accounttype,0)=5 then accountdebts else 0 end ), " +
    			" accountbalance6=sum(case when isnull(accounttype,0)=6 then accountbalance else 0 end ),accountdebts6=sum(case when isnull(accounttype,0)=6 then accountdebts else 0 end ), " +
    			" accountbalance7=sum(case when isnull(accounttype,0)=7 then accountbalance else 0 end ),accountdebts7=sum(case when isnull(accounttype,0)=7 then accountdebts else 0 end ) " +
    			" from cardaccount c with(nolock),cardtypeinfo d with(nolock), cardinfo a with(nolock) left join memberinfo b with(nolock) on  a.cardno=b.memberno  " +
    			" where a.cardno=c.cardno and a.cardno='"+strCardNo+"' " +
    			" and a.cardtype=d.cardtypeno and  cardtypemodeid  in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') " +
    			" group by   a.cardvesting,a.cardno,a.cardstate,a.salecarddate,a.cutoffdate,a.cardsource,membersex,a.cardtype,cardtypename,membername,membermphone,memberpaperworkno,lowopenamt,lowfillamt,openflag,fillflag,changeflag,sendamtflag,slaeproerate,changerule ";
    	AnlyResultSet<Cardinfo> analysis = new AnlyResultSet<Cardinfo>()
		{
			public Cardinfo anlyResultSet(ResultSet rs)
			{
				Cardinfo returnValue=null ;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue= new Cardinfo();
						returnValue.setId(new CardinfoId(rs.getString("cardvesting"),rs.getString("cardno")));
						returnValue.setCardstate(CommonTool.FormatInteger(rs.getInt("cardstate")));
						returnValue.setSalecarddate(CommonTool.getDateMask(rs.getString("salecarddate")));
						returnValue.setCutoffdate(CommonTool.getDateMask(rs.getString("cutoffdate")));
						returnValue.setCardtype(CommonTool.FormatString(rs.getString("cardtype")));
						returnValue.setCardsource(CommonTool.FormatInteger(rs.getInt("cardsource")));
						returnValue.setCardtypeName(CommonTool.FormatString(rs.getString("cardtypename")));
						returnValue.setMembername(CommonTool.FormatString(rs.getString("membername")));
						returnValue.setMembersex(CommonTool.FormatInteger(rs.getInt("membersex")));
						returnValue.setMemberphone(CommonTool.FormatString(rs.getString("membermphone")));
						returnValue.setMemberpaperworkno(CommonTool.FormatString(rs.getString("memberpaperworkno")));
						returnValue.setAccount2Amt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance2"))));
						returnValue.setAccount3Amt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance3"))));
						returnValue.setAccount4Amt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance4"))));
						returnValue.setAccount5Amt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance5"))));
						returnValue.setAccount6Amt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance6"))));
						returnValue.setAccount7Amt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance7"))));
						returnValue.setAccount2debtAmt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts2"))));
						returnValue.setAccount3debtAmt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts3"))));
						returnValue.setAccount4debtAmt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts4"))));
						returnValue.setAccount5debtAmt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts5"))));
						returnValue.setAccount6debtAmt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts6"))));
						returnValue.setAccount7debtAmt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts7"))));
						returnValue.setSlaeproerate(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("slaeproerate"))));
						
						returnValue.setDSaleLowAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("lowopenamt")))));
						
						returnValue.setDFillLowAmt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("lowfillamt")))));
						returnValue.setOpenflag(CommonTool.FormatInteger(rs.getInt("openflag")));
						returnValue.setFillflag(CommonTool.FormatInteger(rs.getInt("fillflag")));
						returnValue.setChangeflag(CommonTool.FormatInteger(rs.getInt("changeflag")));
						returnValue.setSendamtflag(CommonTool.FormatInteger(rs.getInt("sendamtflag")));
						returnValue.setChangerule(CommonTool.FormatInteger(rs.getInt("changerule")));
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
		Cardinfo record= (Cardinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		double saleamt=this.loadSaleCardNoPromotions(strCompId, strCardNo);
		if(CommonTool.FormatDouble(saleamt)!=0)
		{
			record.setDSaleLowAmt(new BigDecimal(saleamt));
		}
		saleamt=this.loadFillCardNoPromotions(strCompId, strCardNo);
		if(CommonTool.FormatDouble(saleamt)!=0)
		{
			record.setDFillLowAmt(new BigDecimal(saleamt));
		}
		return record;
    }
    
  //--------------------------根据会员卡号获得会员疗程信息
	public List<Cardproaccount> loadProInfosByCardNo(String strCompId,String strCardNo)
	{
		try
		{
			 String strModeId=this.loadSysParam(strCompId,"SP059");
		     String strFristModeId="";//第一级模板Id
			 String strSecondModeId="";//第2级模板ID
			 String strThirthModeId="";//第三级模板Id
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.loadCompLvl(strCompId);
		     if(compLvl==2)
			 {
				strFristModeId=this.loadparentModeId(strModeId);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId=this.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.loadparentModeId(strSecondModeId);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId=this.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.loadparentModeId(strSecondModeId);
			 }
		     String strSql=" select cardvesting,cardno,projectno,proseqno,prjname,propricetype,saledate,cutoffdate,salecount,costcount,lastcount,saleamt,costamt,lastamt,proremark,yearsz,createseqno,activinid " +
		     		" from cardproaccount,projectinfo " +
		     		" where cardno='"+strCardNo+"'  and isnull(lastcount,0)>0 and  projectno=prjno and  prjmodeId  in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') ";
		     AnlyResultSet<List<Cardproaccount>> analysis = new AnlyResultSet<List<Cardproaccount>>()
				{
					public List<Cardproaccount> anlyResultSet(ResultSet rs)
					{
						List<Cardproaccount> returnValue=new ArrayList() ;
						Cardproaccount record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record= new Cardproaccount();
								record.setId(new CardproaccountId(rs.getString("cardvesting"),rs.getString("cardno"),rs.getString("projectno"),rs.getDouble("proseqno")));
								record.setBprojectno(CommonTool.FormatString(rs.getString("projectno")));
								record.setBproseqno(CommonTool.FormatDouble(rs.getDouble("proseqno")));
								record.setBprojectname(CommonTool.FormatString(rs.getString("prjname")));
								record.setPropricetype(CommonTool.FormatInteger(rs.getInt("propricetype")));
								record.setSalecount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("salecount"))));
								record.setCostcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("costcount"))));
								record.setLastcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("lastcount"))));
								record.setTargetlastcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("lastcount"))));
								record.setSaleamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("saleamt"))));
								record.setCostamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("costamt"))));
								record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("lastamt"))));
								record.setSaledate(CommonTool.getDateMask(rs.getString("saledate")));
								record.setCutoffdate(CommonTool.getDateMask(rs.getString("cutoffdate")));
								record.setProremark(CommonTool.FormatString(rs.getString("proremark")));
								record.setYearsz(rs.getInt("yearsz"));
								record.setCreateseqno(CommonTool.FormatDouble(rs.getDouble("createseqno")));
								record.setActivinid(CommonTool.FormatString(rs.getString("activinid")));
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
				List<Cardproaccount> ls=(List<Cardproaccount>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Cardproaccount> loadAllProInfosByCardNo(String strCompId,String strCardNo)
	{
		try
		{
			 String strModeId=this.loadSysParam(strCompId,"SP059");
		     String strFristModeId="";//第一级模板Id
			 String strSecondModeId="";//第2级模板ID
			 String strThirthModeId="";//第三级模板Id
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.loadCompLvl(strCompId);
		     if(compLvl==2)
			 {
				strFristModeId=this.loadparentModeId(strModeId);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId=this.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.loadparentModeId(strSecondModeId);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId=this.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.loadparentModeId(strSecondModeId);
			 }
		     String strSql=" select cardvesting,cardno,projectno,proseqno,prjname,propricetype,saledate,cutoffdate,salecount,costcount,lastcount,saleamt,costamt,lastamt,yearsz,proremark, " +
		     		" case when lastcount>0 then '使用中' else '使用完' end userflag from cardproaccount,projectinfo " +
		     		" where cardno='"+strCardNo+"' " +
		     		" and  projectno=prjno and  prjmodeId  in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')" +
		     		" order by projectno,isnull(lastcount,0) desc ";
		     AnlyResultSet<List<Cardproaccount>> analysis = new AnlyResultSet<List<Cardproaccount>>()
				{
					public List<Cardproaccount> anlyResultSet(ResultSet rs)
					{
						List<Cardproaccount> returnValue=new ArrayList() ;
						Cardproaccount record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record= new Cardproaccount();
								record.setId(new CardproaccountId(rs.getString("cardvesting"),rs.getString("cardno"),rs.getString("projectno"),rs.getDouble("proseqno")));
								record.setBcardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
								record.setBprojectno(CommonTool.FormatString(rs.getString("projectno")));
								record.setBproseqno(CommonTool.FormatDouble(rs.getDouble("proseqno")));
								record.setBprojectname(CommonTool.FormatString(rs.getString("prjname")));
								record.setPropricetype(CommonTool.FormatInteger(rs.getInt("propricetype")));
								record.setSalecount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("salecount"))));
								record.setCostcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("costcount"))));
								record.setLastcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("lastcount"))));
								record.setSaleamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("saleamt"))));
								record.setCostamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("costamt"))));
								record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("lastamt"))));
								record.setSaledate(CommonTool.getDateMask(rs.getString("saledate")));
								record.setCutoffdate(CommonTool.getDateMask(rs.getString("cutoffdate")));
								record.setProremark(CommonTool.FormatString(rs.getString("proremark")));
								record.setYearsz(rs.getInt("yearsz"));
								record.setCreatebilltype(CommonTool.FormatString(rs.getString("userflag")));
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
				List<Cardproaccount> ls=(List<Cardproaccount>)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Dmpackageinfo> loadDmpackageinfo(String strCurCompId ,String strCurPackageNo)
	{
		try
		{
			 String strModeId=this.loadSysParam(strCurCompId,"SP059");
		     String strFristModeId="";//第一级模板Id
			 String strSecondModeId="";//第2级模板ID
			 String strThirthModeId="";//第三级模板Id
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.loadCompLvl(strCurCompId);
		     if(compLvl==2)
			 {
				strFristModeId=this.loadparentModeId(strModeId);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId=this.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.loadparentModeId(strSecondModeId);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId=this.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.loadparentModeId(strSecondModeId);
			 }
		     
			String strSql="select compid,packageno,packageprono,prjname,packageprocount,packageproamt" +
					" from  dmpackageinfo ,projectinfo,compchaininfo " +
					" where compid='"+strCurCompId+"'  and packageno='"+strCurPackageNo+"' and prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and packageno='"+strCurPackageNo+"' and prjno = packageprono and  curcomp=prisource and relationcomp=compid  ";
			AnlyResultSet<List<Dmpackageinfo>> analysis = new AnlyResultSet<List<Dmpackageinfo>>()
			{
				public List<Dmpackageinfo> anlyResultSet(ResultSet rs)
				{
					List<Dmpackageinfo> returnValue = new ArrayList();
					Dmpackageinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dmpackageinfo();
							record.setBcompid(CommonTool.FormatString(rs.getString("compid")));
							record.setBpackageno(CommonTool.FormatString(rs.getString("packageno")));
							record.setBpackageprono(CommonTool.FormatString(rs.getString("packageprono")));
							record.setBpackageproname(CommonTool.FormatString(rs.getString("prjname")));
							record.setPackageprocount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("packageprocount")))));
							record.setPackageproamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("packageproamt")))));
							record.setPackageproprice(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("packageproamt"))/CommonTool.FormatDouble(rs.getDouble("packageprocount")))));
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
			List<Dmpackageinfo> ls= (List<Dmpackageinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
//	事业部
	public boolean canOptionChangeCard_shiye(String strCompId,String strCard)
	{
		try
		{
			if(CommonTool.FormatString(this.loadSysParam(strCompId,"SP053")).equals("0"))
			{
				return true;
			}
			String strSql=" select count(cardvesting) from cardinfo with(nolock),compchaininfo " 
				   +" where cardno='"+strCard+"' and curcomp=cardvesting and '"+strCompId+"'=relationcomp ";
			int count=this.amn_Dao.getRowsCount_Ex(strSql);
			if(count>0)
				return true;
			String strProveCompid="";
			if(!this.loadParentShopByCurShop(strCompId).equals("001"))
			{
				strCompId=this.loadParentShopByCurShop(strCompId);
				if(!this.loadParentShopByCurShop(strCompId).equals("001"))
				{
					strCompId=this.loadParentShopByCurShop(strCompId);
					if(!this.loadParentShopByCurShop(strCompId).equals("001"))
					{
						strCompId=this.loadParentShopByCurShop(strCompId);
						strProveCompid=strCompId;
					}
					else
					{
						strProveCompid=strCompId;
					}
				}
				else
				{
					strProveCompid=strCompId;
				}
			}			
			else
			{
				strProveCompid=strCompId;
			}
			strSql=" select count(cardvesting) from cardinfo with(nolock),compchaininfo " 
					   +" where cardno='"+strCard+"' and curcomp='"+strProveCompid+"' and cardvesting=relationcomp ";
			count=this.amn_Dao.getRowsCount_Ex(strSql);
			if(count==0)
				return false;
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
//	区域
	public boolean canOptionChangeCard_quyu(String strCompId,String strCard)
	{
		try
		{
			if(CommonTool.FormatString(this.loadSysParam(strCompId,"SP053")).equals("0"))
			{
				return true;
			}
			String strSql=" select count(cardvesting) from cardinfo with(nolock),compchaininfo " 
				   +" where cardno='"+strCard+"' and curcomp=cardvesting and '"+strCompId+"'=relationcomp ";
			int count=this.amn_Dao.getRowsCount_Ex(strSql);
			if(count>0)
				return true;
			String strProveCompid="";
			strProveCompid=this.loadParentShopByCurShop(strCompId);
			strSql=" select count(cardvesting) from cardinfo with(nolock),compchaininfo " 
				   +" where cardno='"+strCard+"' and curcomp='"+strProveCompid+"' and cardvesting=relationcomp ";
			 count=this.amn_Dao.getRowsCount_Ex(strSql);
			if(count==0)
				return false;
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public String loadParentShopByCurShop(String strCurShop)
	{
		String strSql=" select parentcompno from compchainstruct where curcompno='"+strCurShop+"' ";
		AnlyResultSet<String> analysis = new AnlyResultSet<String>()
		{
			public String anlyResultSet(ResultSet rs)
			{
				String returnValue ="";
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatString(rs.getString("parentcompno"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  "";
				}
				return returnValue;
			}
		};
		return (String)this.amn_Dao.executeQuery_ex(strSql,analysis);		
	}
    
	public List<Cardinfo> searchCardinfo(String searchMemberCompIdKey,String searchMemberNoKey, String searchMemberNameKey,String searchMemberPhoneKey,String searchMemberPCIDKey)
	{
	
		if(searchMemberNoKey!=null && !CommonTool.FormatString(searchMemberNoKey).equals(""))
		{
			boolean flag=this.bandcardinfo(searchMemberNoKey);
			if(flag==false)
				return null;
    	 }
		try
		{
			String strSql="select top 25 a.cardvesting,a.cardno,a.cardtype, b.membername,b.membermphone " +
					" from  cardinfo a left join memberinfo b on  a.cardno=b.memberno" +
					" where   " +
					"   (cardno='"+searchMemberNoKey+"' or '"+searchMemberNoKey+"' ='' ) "+
					" and  (membername like '%"+searchMemberNameKey+"%' or '"+searchMemberNameKey+"' ='' ) "+
					" and  (membermphone='"+searchMemberPhoneKey+"' or '"+searchMemberPhoneKey+"' ='' ) "+
					" and  (memberpaperworkno='"+searchMemberPCIDKey+"' or '"+searchMemberPCIDKey+"' ='' )" +
					" group by a.cardvesting,a.cardno,a.cardtype, b.membername,b.membermphone   ";
			AnlyResultSet<List<Cardinfo>> analysis = new AnlyResultSet<List<Cardinfo>>()
			{
				public List<Cardinfo> anlyResultSet(ResultSet rs)
				{
					List<Cardinfo> returnValue = new ArrayList();
					Cardinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardinfo();
							record.setBcardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
							record.setBcardno(CommonTool.FormatString(rs.getString("cardno")));
							record.setCardtype(CommonTool.FormatString(rs.getString("cardtype")));
							record.setMembername(CommonTool.FormatString(rs.getString("membername")));
							record.setMemberphone(CommonTool.FormatString(rs.getString("membermphone")));
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
			List<Cardinfo> ls= (List<Cardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public List<Cardinfo> searchCardAllinfo(String searchMemberCompIdKey,String searchMemberNoKey, String searchMemberNameKey,String searchMemberPhoneKey,String searchMemberPCIDKey)
	{
	
		 String strModeId=this.loadSysParam(searchMemberCompIdKey,"SP063");
	     String strFristModeId="";//第一级模板Id
		 String strSecondModeId="";//第2级模板ID
		 String strThirthModeId="";//第三级模板Id
		 //先定位模板门店的连锁级别(暂时支持4级连锁)
		 int compLvl=this.loadCompLvl(searchMemberCompIdKey);
	     if(compLvl==2)
		 {
			strFristModeId=this.loadparentModeId(strModeId);
		 }
		 else if(compLvl==3)
		 {
			strSecondModeId=this.loadparentModeId(strModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=this.loadparentModeId(strSecondModeId);
		 }
		 else if(compLvl==4)
		 {
			strThirthModeId=this.loadparentModeId(strModeId);
			if(!strThirthModeId.equals(""))
				strSecondModeId=this.loadparentModeId(strThirthModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=this.loadparentModeId(strSecondModeId);
		 }
		try
		{
			String strSql="select top 10 a.cardvesting,a.cardno,a.cardstate,a.salecarddate,a.cutoffdate,a.cardsource,membersex,a.cardtype,membername,membermphone,memberpaperworkno, " +
			" accountbalance2=sum(case when isnull(accounttype,0)=2 then accountbalance else 0 end ),accountdebts2=sum(case when isnull(accounttype,0)=2 then accountdebts else 0 end ), " +
			" accountbalance3=sum(case when isnull(accounttype,0)=3 then accountbalance else 0 end ),accountdebts3=sum(case when isnull(accounttype,0)=3 then accountdebts else 0 end ), " +
			" accountbalance4=sum(case when isnull(accounttype,0)=4 then accountbalance else 0 end ),accountdebts4=sum(case when isnull(accounttype,0)=4 then accountdebts else 0 end ), " +
			" accountbalance5=sum(case when isnull(accounttype,0)=5 then accountbalance else 0 end ),accountdebts5=sum(case when isnull(accounttype,0)=5 then accountdebts else 0 end ) " +
			" from cardaccount c with(nolock), cardinfo a with(nolock) left join memberinfo b with(nolock) on  a.cardno=b.memberno  " +
			" where a.cardno=c.cardno " +
			" and  (a.cardno='"+searchMemberNoKey+"' or '"+searchMemberNoKey+"' ='' ) "+
			" and  (membername like '%"+searchMemberNameKey+"%' or '"+searchMemberNameKey+"' ='' ) "+
			" and  (membermphone='"+searchMemberPhoneKey+"' or '"+searchMemberPhoneKey+"' ='' ) "+
			" and  (memberpaperworkno='"+searchMemberPCIDKey+"' or '"+searchMemberPCIDKey+"' ='' ) "+
			" group by   a.cardvesting,a.cardno,a.cardstate,a.salecarddate,a.cutoffdate,a.cardsource,membersex,a.cardtype,membername,membermphone,memberpaperworkno ";
			
			AnlyResultSet<List<Cardinfo>> analysis = new AnlyResultSet<List<Cardinfo>>()
			{
				public List<Cardinfo> anlyResultSet(ResultSet rs)
				{
					List<Cardinfo> returnValue = new ArrayList();
					Cardinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record= new Cardinfo();
							record.setId(new CardinfoId(rs.getString("cardvesting"),rs.getString("cardno")));
							record.setBcardno(rs.getString("cardno"));
							record.setBcardvesting(rs.getString("cardvesting"));
							record.setCardstate(CommonTool.FormatInteger(rs.getInt("cardstate")));
							record.setSalecarddate(CommonTool.getDateMask(rs.getString("salecarddate")));
							record.setCutoffdate(CommonTool.getDateMask(rs.getString("cutoffdate")));
							record.setCardtype(CommonTool.FormatString(rs.getString("cardtype")));
							record.setCardsource(CommonTool.FormatInteger(rs.getInt("cardsource")));
							record.setMembername(CommonTool.FormatString(rs.getString("membername")));
							record.setMembersex(CommonTool.FormatInteger(rs.getInt("membersex")));
							record.setMemberphone(CommonTool.FormatString(rs.getString("membermphone")));
							record.setMemberpaperworkno(CommonTool.FormatString(rs.getString("memberpaperworkno")));
							record.setAccount2Amt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance2"))));
							record.setAccount3Amt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance3"))));
							record.setAccount4Amt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance4"))));
							record.setAccount5Amt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance5"))));
							record.setAccount2debtAmt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts2"))));
							record.setAccount3debtAmt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts3"))));
							record.setAccount4debtAmt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts4"))));
							record.setAccount5debtAmt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountdebts5"))));
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
			List<Cardinfo> ls= (List<Cardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	public boolean checkCardClassCanChange(String strCompId,String strFromCardClass)
	{
		String strModeId=this.loadSysParam(strCompId,"SP063");
    	String strFristModeId="";//第一级模板Id
		String strSecondModeId="";//第2级模板ID
		String strThirthModeId="";//第三级模板Id
		//先定位模板门店的连锁级别(暂时支持4级连锁)
		int compLvl=this.loadCompLvl(strCompId);
		if(compLvl==2)
		{
			strFristModeId=this.loadparentModeId(strModeId);
		}
		else if(compLvl==3)
		{
			strSecondModeId=loadparentModeId(strModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=loadparentModeId(strSecondModeId);
		}
		else if(compLvl==4)
		{
			strThirthModeId=loadparentModeId(strModeId);
			if(!strThirthModeId.equals(""))
				strSecondModeId=loadparentModeId(strThirthModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=loadparentModeId(strSecondModeId);
		}
		
		String strSql = "select 1 from cardchangerule where rulemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and cardtypeno = " +CommonTool.quotedStr(strFromCardClass);
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
		boolean flag=(Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		return flag;
	}
	
	public Double getChangeCardClass(String strCompId,String strFromCardClass,String strToCardClass,StringBuffer strExists)
	{
		String strModeId=this.loadSysParam(strCompId,"SP063");
    	String strFristModeId="";//第一级模板Id
		String strSecondModeId="";//第2级模板ID
		String strThirthModeId="";//第三级模板Id
		//先定位模板门店的连锁级别(暂时支持4级连锁)
		int compLvl=this.loadCompLvl(strCompId);
		if(compLvl==2)
		{
			strFristModeId=this.loadparentModeId(strModeId);
		}
		else if(compLvl==3)
		{
			strSecondModeId=loadparentModeId(strModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=loadparentModeId(strSecondModeId);
		}
		else if(compLvl==4)
		{
			strThirthModeId=loadparentModeId(strModeId);
			if(!strThirthModeId.equals(""))
				strSecondModeId=loadparentModeId(strThirthModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=loadparentModeId(strSecondModeId);
		}
		String strSql = " select isnull(changeamt,0) as amt from cardchangerule where rulemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') "
					   +" and cardtypeno = " +CommonTool.quotedStr(strFromCardClass)
					   +" and tocardtypeno = " +CommonTool.quotedStr(strToCardClass);
		ResultSet rs = null;
		try
		{
			rs = this.amn_Dao.executeQuery(strSql);
			if( rs != null && rs.next())
			{
				strExists.append("Y");
				return rs.getDouble("amt");
			}
			else
			{
				strExists.append("N");
				return 0d;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0d;
		}
		finally{//added by wjg
			try{
				((CachedRowSet)rs).release();
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public List<Dpayinfo> loadPayInfoByBillId(String strCompId,String strBillId,String strBillType)
	{		
		try
		{
			String strSql = " select paymode,parentcodevalue,payamt from dpayinfo,commoninfo" +
					"   where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='"+strBillType+"' " +
					"  and infotype='ZFFS' and  parentcodekey=paymode and paymode not in ('9','18') ";
			
			AnlyResultSet<List<Dpayinfo>> analysis = new AnlyResultSet<List<Dpayinfo>>()
			{
				public List<Dpayinfo> anlyResultSet(ResultSet rs)
				{
					List<Dpayinfo> returnValue = new ArrayList();
					Dpayinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record= new Dpayinfo();
							record.setPaymode(CommonTool.FormatString(rs.getString("paymode")));
							record.setPaymodename(CommonTool.FormatString(rs.getString("parentcodevalue")));
							record.setPayamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("payamt"))));
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
			List<Dpayinfo> ls= (List<Dpayinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Cardaccount> loadAccountInfoByCard(String strCompId,String strCardNo)
	{		
		try
		{
			String strSql = " select accounttype,parentcodevalue,accountbalance from cardaccount,commoninfo" +
					"   where  cardno='"+strCardNo+"' " +
					"  and infotype='ZHLX' and  parentcodekey=accounttype and  accounttype <>'4' ";
			
			AnlyResultSet<List<Cardaccount>> analysis = new AnlyResultSet<List<Cardaccount>>()
			{
				public List<Cardaccount> anlyResultSet(ResultSet rs)
				{
					List<Cardaccount> returnValue = new ArrayList();
					Cardaccount record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record= new Cardaccount();
							record.setAccounttype(CommonTool.FormatInteger(rs.getInt("accounttype")));
							record.setAccounttypeText(CommonTool.FormatString(rs.getString("parentcodevalue")));
							record.setAccountbalance(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("accountbalance"))));
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
			List<Cardaccount> ls= (List<Cardaccount>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<PringBillBean> loadCostInfoByBillId(String strCompId,String strBillId,int costType)
	{
		try
		{
			String strSql="";
			if(costType==1)
				strSql="select csitemno,itemname=prjname,csitemcount,csitemamt,csfirstsaler,csproseqno,cspaymode,yearinid from dconsumeinfo,projectnameinfo " +
						" where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and csinfotype=1 and csitemno=prjno "; 
			else if(costType==2)
				strSql="select csitemno,itemname=goodsname,csitemcount,csitemamt,csfirstsaler,csproseqno,cspaymode,yearinid from dconsumeinfo,goodsnameinfo " +
						" where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and csinfotype=2 and csitemno=goodsno ";
			if(!strSql.equals(""))
			{
				AnlyResultSet<List<PringBillBean>> analysis = new AnlyResultSet<List<PringBillBean>>()
				{
					public List<PringBillBean> anlyResultSet(ResultSet rs)
					{
						List<PringBillBean> returnValue = new ArrayList();
						PringBillBean record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record= new PringBillBean();
								record.setProjectNo(CommonTool.FormatString(rs.getString("csitemno")));
								record.setProjectName(CommonTool.FormatString(rs.getString("itemname")));
								record.setPaymode(CommonTool.FormatString(rs.getString("cspaymode")));
								record.setStrFristStaffNo(CommonTool.FormatString(rs.getString("csfirstsaler")));
								record.setCsproseqno(CommonTool.FormatDouble(rs.getDouble("csproseqno")));
								record.setCostNumber(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemcount"))));
								record.setCostMoney(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("csitemamt"))));
								record.setYearinid(rs.getString("yearinid"));
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
				List<PringBillBean> ls= (List<PringBillBean>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
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
	
	
	public List<A3area> genGlobalInfoForA3Area()
	{		
		List<A3area> lsReturn = new ArrayList<A3area>();
		try
		{
			String strSql = "from A3area as a3area  order by code ";
			
			lsReturn = this.amn_Dao.findByHql(strSql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return lsReturn;
		}	
	}
	public List<A3city> genGlobalInfoForA3City()
	{
		List<A3city> lsReturn = new ArrayList<A3city>();
		try
		{
			String strSql = "from A3city as a3city  order by code";
			
			lsReturn = this.amn_Dao.findByHql(strSql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return lsReturn;
		}	
	}
	public List<A3province> genGlobalInfoForA3Province()
	{
		List<A3province> lsReturn = new ArrayList<A3province>();
		try
		{
			String strSql = "from A3province as a3province order by code ";
			
			lsReturn = this.amn_Dao.findByHql(strSql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return lsReturn;
		}	
	}
	
	public String autoCreateInlineStaffNo()
	{
		String strSql = "select isnull(max(manageno),'') as staffinid  from staffinfo where manageno like 'AMN%'";
		AnlyResultSet<String> analysis = new AnlyResultSet<String>()
		{
			public String anlyResultSet(ResultSet rs)
			{
				String returnValue ="";
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatString(rs.getString("staffinid"));
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  "";
				}
				return returnValue;
			}
		};
		String strInlineStaffNo = "";
		Integer afterValue = 0;
		String  strafterValue = "";
		strInlineStaffNo=(String)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		if(strInlineStaffNo.equals(""))
		{
			strInlineStaffNo = "AMN00000102";
		}
		else
		{
			afterValue = Integer.parseInt(strInlineStaffNo.substring(5, strInlineStaffNo.length()));
			afterValue = afterValue+1;
			int lenth = afterValue.toString().length();
			strafterValue = afterValue.toString();
			for(int i =lenth;i<8;i++)
			{
				strafterValue= "0"+strafterValue;
			}
			strInlineStaffNo = "AMN"+strafterValue;
		}
		return strInlineStaffNo;
	}
	
	public int autoCreateFingerStaffNo()
	{
		String strSql = "select max(fingerno)+1 as fingerno  from staffinfo  ";
		AnlyResultSet<Integer> analysis = new AnlyResultSet<Integer>()
		{
			public Integer anlyResultSet(ResultSet rs)
			{
				int returnValue =0;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatInteger(rs.getInt("fingerno"));
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
		int fingerno=(Integer)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		
		return fingerno;
	}
	
	
	public String  autoCreateFileNoStaffNo()
	{
		String strSql = "select needfid=MIN(needfid) from (select cast(SUBSTRING(fillno,4,6) as int ) as curfid, row_number() over (order by fillno) as needfid " +
				" from staffinfo where ISNULL(fillno,'') like 'FN%' ) as curresult where curfid<>needfid  ";
		AnlyResultSet<Integer> analysis = new AnlyResultSet<Integer>()
		{
			public Integer anlyResultSet(ResultSet rs)
			{
				int returnValue =0;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatInteger(rs.getInt("needfid"));
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
		int fillno=(Integer)this.amn_Dao.executeQuery_ex(strSql,analysis);	
		analysis=null;
		if(fillno==0)
		{
			strSql = "select needfid=max(cast(SUBSTRING(fillno,4,6) as int))+1 from staffinfo where ISNULL(fillno,'') like 'FN%'   ";
			analysis = new AnlyResultSet<Integer>()
			{
				public Integer anlyResultSet(ResultSet rs)
				{
					int returnValue =0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatInteger(rs.getInt("needfid"));
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
			fillno=(Integer)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
		}
		if(fillno<10)
			return "FN10000"+fillno;
		if(fillno<100 && fillno>=10)
			return "FN1000"+fillno;
		if(fillno<1000 && fillno>=100)
			return "FN100"+fillno;
		if(fillno<10000 && fillno>=1000)
			return "FN10"+fillno;
		return "";
	}
	
	
	  public BigDecimal loadCurStock(String strCompId,String strDate,String strGoodsId,String strWareId)
		 {
			 String strSql=" exec upg_compute_goods_stock '"+strCompId+"','"+CommonTool.setDateMask(strDate)+"','"+CommonTool.FormatString(strGoodsId)+"','"+CommonTool.FormatString(strGoodsId)+"','"+CommonTool.FormatString(strWareId)+"' ";
			 AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
				{
					public Double anlyResultSet(ResultSet rs)
					{
						double returnValue =0;
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue=rs.getInt("quantity");
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							returnValue = 0;
						}
						return returnValue;
					}
				};
				double goodscount= (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);
				analysis=null;
				return CommonTool.FormatBigDecimal(new BigDecimal(goodscount));
		 }
	  
	  public Map<String, Object> getReportParams(String strCompId , String strBillId)
	  {
	    try
	    {
	    	Map parmas = new HashMap();
	    	String memberId = "";
	 	    String payType = "";
	 	    String cashMemberId = "";
	 	    String cashMemberName = "";
	 		cashMemberId=CommonTool.getLoginInfo("USERID");
	 		cashMemberName=CommonTool.getLoginInfo("USERNAME");
	 	    Companyinfo companyinfo=this.loadCurCompanyInfo(strCompId);
		    parmas.put("cashMemberId", cashMemberId);
		    parmas.put("cashMemberName", cashMemberName);
		    if(companyinfo!=null)
		    {
		    	 parmas.put("companName", CommonTool.FormatString(companyinfo.getCompname()));
				 parmas.put("companTel", CommonTool.FormatString(companyinfo.getCompphone()));
				 parmas.put("companAddr", CommonTool.FormatString(companyinfo.getCompaddress()));
		    }
		    else
		    {
		    	 parmas.put("companName", "");
				 parmas.put("companTel", "");
				 parmas.put("companAddr", "");
		    }
		    return parmas;
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return null;
	    }
	  
	  }
	  
	  
//		根据门店compid与日期date判断当日是否可以保存单据
		public boolean canSaveBill(String compid,String date,StringBuffer errorMsg)
		{
			if(this.isLeafStore(compid)==false)
			{
				return true;
			}
			boolean bRet = true;
			String SP050 = CommonTool.FormatString(this.loadSysParam(compid, "SP050"));
			if(SP050.equalsIgnoreCase("0"))
				return bRet;
			String strSql="";
			strSql=" select count(*) from accountclosureinfo with (nolock) where closecompid='"+compid+"' ";
			int recordCount=this.amn_Dao.getRowsCount_Ex(strSql);
			if(recordCount==0)
			{
				return true;
			}		
			strSql = "select count(*) from accountclosureinfo with (nolock) where closecompid="+CommonTool.quotedStr(compid)+"and closedate="+CommonTool.quotedStr(date);
			//ResultSet rs = null;
			int rowcount=0;
			try{
				rowcount = this.amn_Dao.getRowsCount_Ex(strSql);
				if(rowcount>0)
					bRet = false;
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(!bRet)
			{
				errorMsg.append("门店当日（"+CommonTool.getDateMask(date)+"）已经封账，该日不可再做单据！");
				return bRet;
			}

			String predate = CommonTool.datePlusDay(date, -1);
			strSql = "select 1 from accountclosureinfo with (nolock) where closecompid="+CommonTool.quotedStr(compid)+"and closedate="+CommonTool.quotedStr(predate);
			//rs = null;
			try{
				rowcount = this.amn_Dao.getRowsCount_Ex(strSql);
				if(rowcount==0)
					bRet = false;
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(!bRet)
			{
				errorMsg.append("门店前一日（"+CommonTool.getDateMask(predate)+")未封帐，该日不可做单据！\n请先对前一日进行封帐！");
				return bRet;
			}
			return bRet;
		}
		
		//******************判断公司是否是子门店×××××××××××××××××××××
		public boolean isLeafStore(String strCompId)
		{
			try
			{
				String strSql=" select count(*) from compchainstruct where parentcompno='"+strCompId+"' ";
				int ccount=this.amn_Dao.getRowsCount_Ex(strSql);
			    if(ccount==0)
				    return true;
				else
					return false;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
		
		public double loadSaleCardNoPromotions(String strCompId,String strCardNo)
		{
			String strSql=" select promotionsvalue from promotionsinfo where promotionsstore='"+strCompId+"' and promotionstype=4 and promotionscode='"+strCardNo+"' and startdate<='"+CommonTool.getCurrDate()+"'  and enddate>='"+CommonTool.getCurrDate()+"' and isnull(invalid,0)=0 "; 
			AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
			{
				public Double anlyResultSet(ResultSet rs)
				{
					double returnValue = 0.0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatDouble(rs.getDouble("promotionsvalue"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  0.0;
					}
					return returnValue;
				}
			};
			double promotionsvalue= (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return promotionsvalue;			
		}
		
		public double loadFillCardNoPromotions(String strCompId,String strCardNo)
		{
			String strSql=" select promotionsvalue from promotionsinfo where promotionsstore='"+strCompId+"' and promotionstype=5 and promotionscode='"+strCardNo+"' and startdate<='"+CommonTool.getCurrDate()+"'  and enddate>='"+CommonTool.getCurrDate()+"' and isnull(invalid,0)=0 "; 
			AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
			{
				public Double anlyResultSet(ResultSet rs)
				{
					double returnValue = 0.0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatDouble(rs.getDouble("promotionsvalue"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  0.0;
					}
					return returnValue;
				}
			};
			double promotionsvalue= (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return promotionsvalue;			
		}
		
		/*******************查询老系统卡信息*****************/
		
//		public List<Cardinfo> searchCardinfo(String searchMemberCompIdKey,String searchMemberNoKey, String searchMemberNameKey,String searchMemberPhoneKey,String searchMemberPCIDKey)
//		{
//		
//			if(searchMemberNoKey!=null && !CommonTool.FormatString(searchMemberNoKey).equals(""))
//			{
//				boolean flag=this.bandcardinfo(searchMemberNoKey);
//				if(flag==false)
//					return null;
//	    	 }
//			try
//			{
//				String strSql="select top 25 a.cardvesting,a.cardno,a.cardtype, b.membername,b.membermphone " +
//						" from  cardinfo a left join memberinfo b on  a.cardno=b.memberno" +
//						" where   " +
//						"   (cardno='"+searchMemberNoKey+"' or '"+searchMemberNoKey+"' ='' ) "+
//						" and  (membername like '%"+searchMemberNameKey+"%' or '"+searchMemberNameKey+"' ='' ) "+
//						" and  (membermphone='"+searchMemberPhoneKey+"' or '"+searchMemberPhoneKey+"' ='' ) "+
//						" and  (memberpaperworkno='"+searchMemberPCIDKey+"' or '"+searchMemberPCIDKey+"' ='' )" +
//						" group by a.cardvesting,a.cardno,a.cardtype, b.membername,b.membermphone   ";
//				AnlyResultSet<List<Cardinfo>> analysis = new AnlyResultSet<List<Cardinfo>>()
//				{
//					public List<Cardinfo> anlyResultSet(ResultSet rs)
//					{
//						List<Cardinfo> returnValue = new ArrayList();
//						Cardinfo record=null;
//						try
//						{
//							while(rs != null && rs.next()==true)
//							{
//								record=new Cardinfo();
//								record.setBcardvesting(CommonTool.FormatString(rs.getString("cardvesting")));
//								record.setBcardno(CommonTool.FormatString(rs.getString("cardno")));
//								record.setCardtype(CommonTool.FormatString(rs.getString("cardtype")));
//								record.setMembername(CommonTool.FormatString(rs.getString("membername")));
//								record.setMemberphone(CommonTool.FormatString(rs.getString("membermphone")));
//								returnValue.add(record);
//							}				
//						}
//						catch(Exception e)
//						{
//							e.printStackTrace();
//							returnValue =  null;
//						}
//						return returnValue;
//					}
//				};
//				List<Cardinfo> ls= (List<Cardinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);	
//				analysis=null;
//				return ls;
//			}
//			catch(Exception ex)
//			{
//				ex.printStackTrace();
//				return null;
//			}
//			
//		}
		
		public List<Cardinfo> searchCard(String searchMemberCompIdKey,String searchMemberNoKey, String searchMemberNameKey,String searchMemberPhoneKey,String searchMemberPCIDKey)
		{
			
			String strSql="select top 25 a.gca00c,a.gca01c,a.gca02c, b.gba03c,b.gba08c " +
			" from  gcm01 a left join gbm01 b on  a.gca01c=b.gba01c and a.gca00c=b.gba00c " +
			" where   " +
			" gca00c=gca13d and   (gca01c='"+searchMemberNoKey+"' or '"+searchMemberNoKey+"' ='' ) "+
			" and  (gba03c like '%"+searchMemberNameKey+"%' or '"+searchMemberNameKey+"' ='' ) "+
			" and  (gba08c='"+searchMemberPhoneKey+"' or '"+searchMemberPhoneKey+"' ='' ) "+
			" and  (gba16c='"+searchMemberPCIDKey+"' or '"+searchMemberPCIDKey+"' ='' )" +
			" group by a.gca00c,a.gca01c,a.gca02c, b.gba03c,b.gba08c   ";
			AnlyResultSet<List<Cardinfo>> analysis = new AnlyResultSet<List<Cardinfo>>()
			{
				public List<Cardinfo> anlyResultSet(ResultSet rs)
				{
					List<Cardinfo> returnValue = new ArrayList();
					Cardinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardinfo();
							record.setBcardvesting(CommonTool.FormatString(rs.getString("gca00c")));
							record.setBcardno(CommonTool.FormatString(rs.getString("gca01c")));
							record.setCardtype(CommonTool.FormatString(rs.getString("gca02c")));
							record.setMembername(CommonTool.FormatString(rs.getString("gba03c")));
							record.setMemberphone(CommonTool.FormatString(rs.getString("gba08c")));
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
			List<Cardinfo> ls= (List<Cardinfo>)this.amn_PADDao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return ls;
		}
		
		public Cardinfo loadCardinfoByGcm01(String strCompId,String strCardNo)
		{
			try
			{
				
				String strSql="select a.gca00c,a.gca01c,a.gca02c, c.gak02c,b.gba03c,b.gba08c,b.gba16c,a.gca32i,a.gca06d,a.gca07d,a.gca08i " +
						"from gam10 c, gcm01 a left join gbm01 b on a.gca01c=b.gba01c and a.gca00c=b.gba00c   " +
						" where gca00c=gca13d and  gca00c='"+strCompId+"'  and gca01c='"+strCardNo+"' " +
						"  and gca00c =gak00c and gca02c=gak01c ";
				AnlyResultSet<Cardinfo> analysis = new AnlyResultSet<Cardinfo>()
				{
					public Cardinfo anlyResultSet(ResultSet rs)
					{
					
						Cardinfo record=null;
						try
						{
							if(rs != null && rs.next()==true)
							{
								record=new Cardinfo();
								record.setBcardvesting(CommonTool.FormatString(rs.getString("gca00c")));
								record.setBcardno(CommonTool.FormatString(rs.getString("gca01c")));
								record.setCardtype(CommonTool.FormatString(rs.getString("gca02c")));
								record.setMembername(CommonTool.FormatString(rs.getString("gba03c")));
								record.setMemberphone(CommonTool.FormatString(rs.getString("gba08c")));
								record.setMemberpcid(CommonTool.FormatString(rs.getString("gba16c")));
								record.setCardtypeName(CommonTool.FormatString(rs.getString("gak02c")));
								record.setCardsource(CommonTool.FormatInteger(rs.getInt("gca32i")));
								record.setSalecarddate(CommonTool.getDateMask(rs.getString("gca06d")));
								record.setCutoffdate(CommonTool.getDateMask(rs.getString("gca07d")));
								record.setCardstate(CommonTool.FormatInteger(rs.getInt("gca08i")));
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
				Cardinfo record= (Cardinfo)this.amn_PADDao.executeQuery_ex(strSql,analysis);	
				analysis=null;
				return record;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		
		public List<Cardaccount> loadGcm03(String strComp,String strCardNo)
		{
			
			String strSql="select gcc00c,gcc01c,gcc03i,gsb03c,gcc06f,gcc10f  " +
			" from gcm03,gcm01 ,gsm02 " +
			" where gcc00c=gca00c and gcc01c=gca01c and gca00c=gca13d and gcc01c='"+strCardNo+"' and gsb00c=gcc00c and  'O'=gsb01c and gsb02c=gcc03i ";
			AnlyResultSet<List<Cardaccount>> analysis = new AnlyResultSet<List<Cardaccount>>()
			{
				public List<Cardaccount> anlyResultSet(ResultSet rs)
				{
					List<Cardaccount> returnValue = new ArrayList();
					Cardaccount record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardaccount();
							record.setCardvesting(rs.getString("gcc00c"));
							record.setCardno(rs.getString("gcc01c"));
							record.setAccounttype(CommonTool.FormatInteger(rs.getInt("gcc03i")));
							record.setAccounttypeText(CommonTool.FormatString(rs.getString("gsb03c")));
							record.setAccountbalance(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gcc06f"))));
							record.setAccountdebts(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gcc10f"))));
			
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
			List<Cardaccount> ls= (List<Cardaccount>)this.amn_PADDao.executeQuery_ex(strSql,analysis);		
			analysis=null;
			return ls;
	
			
		}
		
		public List<Cardaccountchangehistory> loadGcm04(String strCardNo)
		{
			String strSql=" select gcd00c,gae03c,gcd02i,gcd04f,gcd05i,gcd06f,gcd07c,gcd08c,gcd09d,gcd10f " +
			"from gcm04,gam05 where gcd01c='"+strCardNo+"' and gae01c=gcd00c order by gcd13i desc  ";
			AnlyResultSet<List<Cardaccountchangehistory>> analysis = new AnlyResultSet<List<Cardaccountchangehistory>>()
			{
				public List<Cardaccountchangehistory> anlyResultSet(ResultSet rs)
				{
					List<Cardaccountchangehistory> returnValue = new ArrayList();
					Cardaccountchangehistory record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardaccountchangehistory();
							record.setChangecompname(CommonTool.FormatString(rs.getString("gae03c")));
							record.setChangecompid(CommonTool.FormatString(rs.getString("gcd00c")));
							record.setChangeaccounttype(CommonTool.FormatInteger(rs.getInt("gcd02i")));
							record.setChangeseqno(CommonTool.FormatInteger(rs.getInt("gcd04f")));
							record.setChangetype(CommonTool.FormatInteger(rs.getInt("gcd05i")));
							record.setChangebilltype(CommonTool.FormatString(rs.getString("gcd07c")));
							record.setChangebillno(CommonTool.FormatString(rs.getString("gcd08c")));
							record.setChagedate(CommonTool.getDateMask(rs.getString("gcd09d")));
							record.setChangeamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gcd06f"))));
							record.setChangebeforeamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gcd10f"))));
							if(rs.getInt("gcd05i") == 0 || rs.getInt("gcd05i") == 6
									|| rs.getInt("gcd05i") == 7 || rs.getInt("gcd05i") == 8 
									|| rs.getInt("gcd05i") == 9 || rs.getInt("gcd05i") == 10 || rs.getInt("gcd05i") == 11  )
							{
								record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("gcd10f"))+ CommonTool.FormatDouble(rs.getDouble("gcd06f")))));
							}
							else
							{
								record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("gcd10f"))- CommonTool.FormatDouble(rs.getDouble("gcd06f")))));
							}
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
			List<Cardaccountchangehistory> ls= (List<Cardaccountchangehistory>)this.amn_PADDao.executeQuery_ex(strSql,analysis);		
			analysis=null;
			return ls;
		}
		
		
		public List<Cardchangehistory>  loadGcm02(String strCardNo)
		{
			String strSql=" select gcb03i,gcb04c,gcb05i,gcb06i,gcb07d,gcb08c from  gcm02 where gcb01c='"+strCardNo+"'  order by gcb02f desc  ";
			AnlyResultSet<List<Cardchangehistory>> analysis = new AnlyResultSet<List<Cardchangehistory>>()
			{
				public List<Cardchangehistory> anlyResultSet(ResultSet rs)
				{
					List<Cardchangehistory> returnValue = new ArrayList();
					Cardchangehistory record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardchangehistory();
							record.setChangetype(CommonTool.FormatInteger(rs.getInt("gcb03i")));
							record.setChangebillid(CommonTool.FormatString(rs.getString("gcb04c")));
							record.setBeforestate(CommonTool.FormatInteger(rs.getInt("gcb05i")));
							record.setAfterstate(CommonTool.FormatInteger(rs.getInt("gcb06i")));
							record.setChagedate(CommonTool.getDateMask(rs.getString("gcb07d")));
							record.setTargetcardno(CommonTool.FormatString(rs.getString("gcb08c")));
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
			List<Cardchangehistory> ls= (List<Cardchangehistory>)this.amn_PADDao.executeQuery_ex(strSql,analysis);		
			analysis=null;
			return ls;
		}
		
		public  List<Cardproaccount> loadGcm06(String strCompId,String strCardNo)
		{
			try
			{
				
			     String strSql=" select gcf00c,gcf01c,gcf02c,gcf23i,gda03c,gcf17i,gcf11d,gcf12d,gcf05f,gcf06f,gcf07f,gcf08f,gcf09f,gcf10f,gcf15c " +
			     		" from gcm01,gcm06,gdm01 " +
			     		" where gca00c=gcf00c and gca01c=gcf01c and gca00c=gca13d and  gca01c='"+strCardNo+"' " +
			     		" and  gcf02c=gda01c and gda00c=gca00c   " +
			     		" order by gcf02c,isnull(gcf07f,0) desc ";
			     AnlyResultSet<List<Cardproaccount>> analysis = new AnlyResultSet<List<Cardproaccount>>()
					{
						public List<Cardproaccount> anlyResultSet(ResultSet rs)
						{
							List<Cardproaccount> returnValue=new ArrayList() ;
							Cardproaccount record=null;
							try
							{
								while(rs != null && rs.next()==true)
								{
									record= new Cardproaccount();
									record.setId(new CardproaccountId(rs.getString("gcf00c"),rs.getString("gcf01c"),rs.getString("gcf02c"),rs.getDouble("gcf23i")));
									record.setBcardvesting(CommonTool.FormatString(rs.getString("gcf00c")));
									record.setBprojectno(CommonTool.FormatString(rs.getString("gcf02c")));
									record.setBproseqno(CommonTool.FormatDouble(rs.getDouble("gcf23i")));
									record.setBprojectname(CommonTool.FormatString(rs.getString("gda03c")));
									record.setPropricetype(CommonTool.FormatInteger(rs.getInt("gcf17i")));
									record.setSalecount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gcf05f"))));
									record.setCostcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gcf06f"))));
									record.setLastcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gcf07f"))));
									record.setSaleamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gcf08f"))));
									record.setCostamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gcf09f"))));
									record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gcf10f"))));
									record.setSaledate(CommonTool.getDateMask(rs.getString("gcf11d")));
									record.setCutoffdate(CommonTool.getDateMask(rs.getString("gcf12d")));
									record.setProremark(CommonTool.FormatString(rs.getString("gcf15c")));
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
					List<Cardproaccount> ls=(List<Cardproaccount>)this.amn_PADDao.executeQuery_ex(strSql,analysis);
					analysis=null;
					return ls;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		
		public List<Cardtransactionhistory> loadGcm20(String strCardNo,String strBillType,String strBillId,String strAccountType)
		{
			try
			{
				String strSql=" select gct02c,gct04d,gct06c,gct07c,gct08c,gct11f,gct12f,gct15c,gct16c,gct17c,paymode='' from  gcm20 a " +
						" where gct02c='"+strCardNo+"' and gct13c='"+strBillType+"' and gct14c='"+strBillId+"' " ;
						
				AnlyResultSet<List<Cardtransactionhistory>> analysis = new AnlyResultSet<List<Cardtransactionhistory>>()
				{
					public List<Cardtransactionhistory> anlyResultSet(ResultSet rs)
					{
						List<Cardtransactionhistory> returnValue = new ArrayList();
						Cardtransactionhistory record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Cardtransactionhistory();
								record.setTransactioncardno(CommonTool.FormatString(rs.getString("gct02c")));
								record.setTransactiondate(CommonTool.getDateMask(rs.getString("gct04d")));
								record.setTransactiontype(CommonTool.FormatString(rs.getString("gct06c")));
								if(CommonTool.FormatString(rs.getString("gct06c")).equals("1"))
								{
									record.setTransactiontypeText("卡销售");
								}
								else if(CommonTool.FormatString(rs.getString("gct06c")).equals("3"))
								{
									record.setTransactiontypeText("项目消耗");
								}
								else if(CommonTool.FormatString(rs.getString("gct06c")).equals("4"))
								{
									record.setTransactiontypeText("产品销售");
								}
								else if(CommonTool.FormatString(rs.getString("gct06c")).equals("5"))
								{
									record.setTransactiontypeText("卡充值");
								}
								else if(CommonTool.FormatString(rs.getString("gct06c")).equals("6"))
								{
									record.setTransactiontypeText("疗程销售");
								}
								record.setCodeno(CommonTool.FormatString(rs.getString("gct07c")));
								record.setCodename(CommonTool.FormatString(rs.getString("gct08c")));
								record.setFirstempid(CommonTool.FormatString(rs.getString("gct15c")));
								record.setSecondempid(CommonTool.FormatString(rs.getString("gct16c")));
								record.setThirthempid(CommonTool.FormatString(rs.getString("gct17c")));
								record.setCcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gct11f"))));
								record.setPrice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("gct12f"))));
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
				List<Cardtransactionhistory> ls= (List<Cardtransactionhistory>)this.amn_PADDao.executeQuery_ex(strSql,analysis);		
				analysis=null;
				return ls;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		
		
		public Gbm01 loadGbm01(String strCardNo,StringBuffer strBufferVaule)
		{
			String strHql = "From Gbm01 as gbm01 where gbm01.id.gba01c ="+CommonTool.quotedStr(strCardNo); 
			try
			{
				ArrayList findByHql = (ArrayList) this.amn_PADDao.findByHql(strHql);
				ArrayList<Gbm01> ListGbm01  = findByHql;
				if(ListGbm01 == null || ListGbm01.size()==0)
				{
					return null;
				}
				else
				{
					Gbm01 objGbm01 =  ListGbm01.get(0);
					return objGbm01;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				strBufferVaule.append(SystemFinal.LOAD_FAILURE_MSG);
				return null;
			}
		}
		
		public List<StaffinfoSimple> loadOldCustomerInfo(String strCompId,String strCardNo)
		{
			try
			{
				String strBeforDate=CommonTool.datePlusDay(CommonTool.getCurrDate(), -92);
				String strSql="select staffno,staffname ,ccount=COUNT(distinct b.csbillid)" +
						" from staffinfo a with(nolock),mconsumeinfo b with(nolock),dconsumeinfo c with(nolock)" +
						" where b.cscompid=c.cscompid and b.csbillid=c.csbillid" +
						" and b.financedate between '"+strBeforDate+"' and '"+CommonTool.getCurrDate()+"' " +
						" and b.cscardno='"+strCardNo+"' " +
						" and ((c.csfirstinid=a.manageno and csfirsttype=1 ) " +
						" or (c.cssecondinid=a.manageno and cssecondtype=1 )" +
						" or (c.csthirdinid=a.manageno and csthirdtype=1)) " +
						" and (a.department='004') and  a.compno='"+strCompId+"' " +
						" group by staffno,staffname ";
				
				strSql+=" union all  select staffno,staffname ,ccount=COUNT(distinct b.csbillid)" +
						" from staffinfo a with(nolock),mconsumeinfo b with(nolock),dconsumeinfo c with(nolock)" +
						" where b.cscompid=c.cscompid and b.csbillid=c.csbillid" +
						" and b.financedate between '"+strBeforDate+"' and '"+CommonTool.getCurrDate()+"' " +
						" and b.cscardno='"+strCardNo+"' " +
						" and ((c.csfirstinid=a.manageno) " +
						" or (c.cssecondinid=a.manageno)" +
						" or (c.csthirdinid=a.manageno)) " +
						" and (a.department='006') " +
						" group by staffno,staffname ";
				
				System.out.println(strSql);
				
				AnlyResultSet<List<StaffinfoSimple>> analysis = new AnlyResultSet<List<StaffinfoSimple>>()
				{
					public List<StaffinfoSimple> anlyResultSet(ResultSet rs)
					{
						List<StaffinfoSimple> returnValue = new ArrayList();
						StaffinfoSimple record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new StaffinfoSimple();
								record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
								record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
								record.setCostCount(CommonTool.FormatInteger(rs.getInt("ccount")));
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
				List<StaffinfoSimple> ls= (List<StaffinfoSimple>)this.amn_Dao.executeQuery_ex(strSql,analysis);		
				analysis=null;
				return ls;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		
		public List<StaffinfoSimple> loadOldCustomerInfo(String strCompId,String strOldCardNo,String strNewCardNo)
		{
			try
			{
				String strBeforDate=CommonTool.datePlusDay(CommonTool.getCurrDate(), -92);
				String strSql="select staffno,staffname ,ccount=COUNT(distinct b.csbillid)" +
						" from staffinfo a with(nolock),mconsumeinfo b with(nolock),dconsumeinfo c with(nolock)" +
						" where b.cscompid=c.cscompid and b.csbillid=c.csbillid" +
						" and b.financedate between '"+strBeforDate+"' and '"+CommonTool.getCurrDate()+"' " +
						" and (b.cscardno='"+strOldCardNo+"' or b.cscardno='"+strNewCardNo+"' ) " +
						" and ((c.csfirstinid=a.manageno and csfirsttype=1 ) " +
						" or (c.cssecondinid=a.manageno and cssecondtype=1 )" +
						" or (c.csthirdinid=a.manageno and csthirdtype=1)) " +
						" and (a.department='004') and a.compno='"+strCompId+"' " +
						" group by staffno,staffname ";
				
				strSql+=" union all select staffno,staffname ,ccount=COUNT(distinct b.csbillid)" +
						" from staffinfo a with(nolock),mconsumeinfo b with(nolock),dconsumeinfo c with(nolock)" +
						" where b.cscompid=c.cscompid and b.csbillid=c.csbillid" +
						" and b.financedate between '"+strBeforDate+"' and '"+CommonTool.getCurrDate()+"' " +
						" and (b.cscardno='"+strOldCardNo+"' or b.cscardno='"+strNewCardNo+"' ) " +
						" and ((c.csfirstinid=a.manageno) " +
						" or (c.cssecondinid=a.manageno)" +
						" or (c.csthirdinid=a.manageno)) " +
						" and (a.department='006') " +
						" group by staffno,staffname ";
				AnlyResultSet<List<StaffinfoSimple>> analysis = new AnlyResultSet<List<StaffinfoSimple>>()
				{
					public List<StaffinfoSimple> anlyResultSet(ResultSet rs)
					{
						List<StaffinfoSimple> returnValue = new ArrayList();
						StaffinfoSimple record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new StaffinfoSimple();
								record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
								record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
								record.setCostCount(CommonTool.FormatInteger(rs.getInt("ccount")));
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
				List<StaffinfoSimple> ls= (List<StaffinfoSimple>)this.amn_Dao.executeQuery_ex(strSql,analysis);		
				analysis=null;
				return ls;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		
		public List<StaffinfoSimple> loadCombinCustomerInfo(String strCompId,String strOldCardNo,String strBillId)
		{
			try
			{
				String strBeforDate=CommonTool.datePlusDay(CommonTool.getCurrDate(), -92);
				String strSql="select staffno,staffname ,ccount=COUNT(distinct b.csbillid)" +
						" from staffinfo a with(nolock),mconsumeinfo b with(nolock),dconsumeinfo c with(nolock)" +
						" where b.cscompid=c.cscompid and b.csbillid=c.csbillid" +
						" and b.financedate between '"+strBeforDate+"' and '"+CommonTool.getCurrDate()+"' " +
						" and b.cscardno='"+strOldCardNo+"' " +
						" and ((c.csfirstinid=a.manageno and csfirsttype=1 ) " +
						" or (c.cssecondinid=a.manageno and cssecondtype=1 )" +
						" or (c.csthirdinid=a.manageno and csthirdtype=1)) " +
						" and (a.department='004') and a.compno='"+strCompId+"' " +
						" group by staffno,staffname ";
				
				strSql=strSql+" union all select staffno,staffname ,ccount=COUNT(distinct b.csbillid)" +
				" from staffinfo a with(nolock),mconsumeinfo b with(nolock),dconsumeinfo c with(nolock)" +
				" where b.cscompid=c.cscompid and b.csbillid=c.csbillid" +
				" and b.financedate between '"+strBeforDate+"' and '"+CommonTool.getCurrDate()+"' " +
				" and b.cscardno in (select oldcardno from dcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' )  " +
				" and ((c.csfirstinid=a.manageno and csfirsttype=1 ) " +
				" or (c.cssecondinid=a.manageno and cssecondtype=1 )" +
				" or (c.csthirdinid=a.manageno and csthirdtype=1)) " +
				" and (a.department='004') and a.compno='"+strCompId+"' " +
				" group by staffno,staffname ";
				
				strSql+=" union all select staffno,staffname ,ccount=COUNT(distinct b.csbillid)" +
						" from staffinfo a with(nolock),mconsumeinfo b with(nolock),dconsumeinfo c with(nolock)" +
						" where b.cscompid=c.cscompid and b.csbillid=c.csbillid" +
						" and b.financedate between '"+strBeforDate+"' and '"+CommonTool.getCurrDate()+"' " +
						" and b.cscardno='"+strOldCardNo+"' " +
						" and ((c.csfirstinid=a.manageno ) " +
						" or (c.cssecondinid=a.manageno )" +
						" or (c.csthirdinid=a.manageno )) " +
						" and (a.department='006') " +
						" group by staffno,staffname ";
				strSql+=" union all select staffno,staffname ,ccount=COUNT(distinct b.csbillid)" +
						" from staffinfo a with(nolock),mconsumeinfo b with(nolock),dconsumeinfo c with(nolock)" +
						" where b.cscompid=c.cscompid and b.csbillid=c.csbillid" +
						" and b.financedate between '"+strBeforDate+"' and '"+CommonTool.getCurrDate()+"' " +
						" and b.cscardno in (select oldcardno from dcardchangeinfo with(nolock) where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' )  " +
						" and ((c.csfirstinid=a.manageno ) " +
						" or (c.cssecondinid=a.manageno )" +
						" or (c.csthirdinid=a.manageno)) " +
						" and (a.department='006') " +
						" group by staffno,staffname ";
				AnlyResultSet<List<StaffinfoSimple>> analysis = new AnlyResultSet<List<StaffinfoSimple>>()
				{
					public List<StaffinfoSimple> anlyResultSet(ResultSet rs)
					{
						List<StaffinfoSimple> returnValue = new ArrayList();
						StaffinfoSimple record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new StaffinfoSimple();
								record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
								record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
								record.setCostCount(CommonTool.FormatInteger(rs.getInt("ccount")));
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
				List<StaffinfoSimple> ls= (List<StaffinfoSimple>)this.amn_Dao.executeQuery_ex(strSql,analysis);		
				analysis=null;
				return ls;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		
		public boolean validateMangerPass(String strCompId,String strMangerNo)
		{
			String strSql="select 1 from companyinfo where compno='"+strCompId+"' and mangerPassword='"+strMangerNo+"' ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() 
			{
				public Boolean anlyResultSet(ResultSet rs) 
				{
					boolean bReturnValue = false;
					try 
					{
						if( rs != null && rs.next())
						{
							bReturnValue =  true;
						}
					} catch (Exception e) {
						e.printStackTrace();
						bReturnValue = false;
					}
					return bReturnValue;
				}
			};
			return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
		//获得烫染师
		public boolean chekIsHotDyeDivision(Object object){
			boolean b = false;
			StringBuffer buffer = new StringBuffer();
			//会员卡销售
			if(object instanceof Msalecardinfo) {
				//第一销售工号
				buffer.append("'"+((Msalecardinfo) object).getFirstsalerid()+"'");
				if(CommonTool.isEmpty(((Msalecardinfo) object).getSecondsalerid())){
					buffer.append(",'"+((Msalecardinfo) object).getSecondsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getThirdsalerid())){
					buffer.append(",'"+((Msalecardinfo) object).getThirdsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getFourthsalerid())){
					buffer.append(",'"+((Msalecardinfo) object).getFourthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getFifthsalerid())){
					buffer.append(",'"+((Msalecardinfo) object).getFifthsalerid()+"'");
				}
				
				if(CommonTool.isEmpty(((Msalecardinfo) object).getSixthsalerid())){
					buffer.append(",'"+((Msalecardinfo) object).getSixthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getSeventhsalerid())){
					buffer.append(",'"+((Msalecardinfo) object).getSeventhsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getEighthsalerid())){
					buffer.append(",'"+((Msalecardinfo) object).getEighthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getNinthsalerid())){
					buffer.append(",'"+((Msalecardinfo) object).getNinthsalerid()+"'");
				}
				//第十销售工号
				if(CommonTool.isEmpty(((Msalecardinfo) object).getTenthsalerid())){
					buffer.append(",'"+((Msalecardinfo) object).getTenthsalerid()+"'");
				}
				//帐户异动单
			}else if(object instanceof Mcardrechargeinfo){
				//第一销售工号
				buffer.append("'"+((Mcardrechargeinfo) object).getFirstsalerid()+"'");
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getSecondsalerid())){
					buffer.append(",'"+((Mcardrechargeinfo) object).getSecondsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getThirdsalerid())){
					buffer.append(",'"+((Mcardrechargeinfo) object).getThirdsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getFourthsalerid())){
					buffer.append(",'"+((Mcardrechargeinfo) object).getFourthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getFifthsalerid())){
					buffer.append(",'"+((Mcardrechargeinfo) object).getFifthsalerid()+"'");
				}
				
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getSixthsalerid())){
					buffer.append(",'"+((Mcardrechargeinfo) object).getSixthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getSeventhsalerid())){
					buffer.append(",'"+((Mcardrechargeinfo) object).getSeventhsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getEighthsalerid())){
					buffer.append(",'"+((Mcardrechargeinfo) object).getEighthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getNinthsalerid())){
					buffer.append(",'"+((Mcardrechargeinfo) object).getNinthsalerid()+"'");
				}
				//第十销售工号
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getTenthsalerid())){
					buffer.append(",'"+((Mcardrechargeinfo) object).getTenthsalerid()+"'");
				}
				//会员卡异动
			}else if(object instanceof Mcardchangeinfo){
				buffer.append("'"+((Mcardchangeinfo) object).getFirstsalerid()+"'");
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getSecondsalerid())){
					buffer.append(",'"+((Mcardchangeinfo) object).getSecondsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getThirdsalerid())){
					buffer.append(",'"+((Mcardchangeinfo) object).getThirdsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getFourthsalerid())){
					buffer.append(",'"+((Mcardchangeinfo) object).getFourthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getFifthsalerid())){
					buffer.append(",'"+((Mcardchangeinfo) object).getFifthsalerid()+"'");
				}
				
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getSixthsalerid())){
					buffer.append(",'"+((Mcardchangeinfo) object).getSixthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getSeventhsalerid())){
					buffer.append(",'"+((Mcardchangeinfo) object).getSeventhsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getEighthsalerid())){
					buffer.append(",'"+((Mcardchangeinfo) object).getEighthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getNinthsalerid())){
					buffer.append(",'"+((Mcardchangeinfo) object).getNinthsalerid()+"'");
				}
				//第十销售工号
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getTenthsalerid())){
					buffer.append(",'"+((Mcardchangeinfo) object).getTenthsalerid()+"'");
				}
			}else if(object instanceof ReEditBillInfo){
				buffer.append("'"+((ReEditBillInfo) object).getFirstsalerid()+"'");
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getSecondsalerid())){
					buffer.append(",'"+((ReEditBillInfo) object).getSecondsalerid()+"'");
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getThirdsalerid())){
					buffer.append(",'"+((ReEditBillInfo) object).getThirdsalerid()+"'");
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getFourthsalerid())){
					buffer.append(",'"+((ReEditBillInfo) object).getFourthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getFifthsalerid())){
					buffer.append(",'"+((ReEditBillInfo) object).getFifthsalerid()+"'");
				}
				
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getSixthsalerid())){
					buffer.append(",'"+((ReEditBillInfo) object).getSixthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getSeventhsalerid())){
					buffer.append(",'"+((ReEditBillInfo) object).getSeventhsalerid()+"'");
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getEighthsalerid())){
					buffer.append(",'"+((ReEditBillInfo) object).getEighthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getNinthsalerid())){
					buffer.append(",'"+((ReEditBillInfo) object).getNinthsalerid()+"'");
				}
				//第十销售工号
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getTenthsalerid())){
					buffer.append(",'"+((ReEditBillInfo) object).getTenthsalerid()+"'");
				}
			}else if(object instanceof Mcooperatesaleinfo){
				buffer.append("'"+((Mcooperatesaleinfo) object).getFirstsalerid()+"'");
				if(CommonTool.isEmpty(((Mcooperatesaleinfo) object).getSecondsalerid())){
					buffer.append(",'"+((Mcooperatesaleinfo) object).getSecondsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcooperatesaleinfo) object).getThirdsalerid())){
					buffer.append(",'"+((Mcooperatesaleinfo) object).getThirdsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcooperatesaleinfo) object).getFourthsalerid())){
					buffer.append(",'"+((Mcooperatesaleinfo) object).getFourthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcooperatesaleinfo) object).getFifthsalerid())){
					buffer.append(",'"+((Mcooperatesaleinfo) object).getFifthsalerid()+"'");
				}
				
				if(CommonTool.isEmpty(((Mcooperatesaleinfo) object).getSixthsalerid())){
					buffer.append(",'"+((Mcooperatesaleinfo) object).getSixthsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcooperatesaleinfo) object).getSeventhsalerid())){
					buffer.append(",'"+((Mcooperatesaleinfo) object).getSeventhsalerid()+"'");
				}
				if(CommonTool.isEmpty(((Mcooperatesaleinfo) object).getEighthsalerid())){
					buffer.append(",'"+((Mcooperatesaleinfo) object).getEighthsalerid()+"'");
				}
//				if(CommonTool.isEmpty(((Mcooperatesaleinfo) object).getNinthsalerid())){
//					buffer.append(",'"+((Mcooperatesaleinfo) object).getNinthsalerid()+"'");
//				}
//				//第十销售工号
//				if(CommonTool.isEmpty(((Mcooperatesaleinfo) object).getTenthsalerid())){
//					buffer.append(",'"+((Mcooperatesaleinfo) object).getTenthsalerid()+"'");
//				}
			}
			
			String strSql ="select staffno from staffinfo where staffno in ("+buffer.toString()+ ")" 
			+"and position in('008','00901','00902','00903','00904')";
			System.out.println(strSql);
			int i= this.amn_Dao.getRowsCount_Ex(strSql);
			if(i>0){
				b = true;
			}
			return b;
		}
		//获得美容部单张单据业绩
		public float getBeautyDepartmentPerformance(Object object){
			float count =0;
			StringBuffer buffer = new StringBuffer();
			//会员卡销售
			if(object instanceof Msalecardinfo) {
				//第一销售工号
				if(CommonTool.isEmpty(((Msalecardinfo) object).getFirstsalerid())){
					String firstsalerid=((Msalecardinfo) object).getFirstsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+firstsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					if(((Msalecardinfo) object).getBillinsertype().equals("3")){
						s = ((Msalecardinfo) object).getFirstsalecashamt();
						
					}else{
						s = ((Msalecardinfo) object).getFirstsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}	
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getSecondsalerid())){
					String secondsalerid=((Msalecardinfo) object).getSecondsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+secondsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Msalecardinfo) object).getBillinsertype().equals("3")){
						s = ((Msalecardinfo) object).getSecondsalecashamt();
					}else{
						s = ((Msalecardinfo) object).getSecondsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getThirdsalerid())){
					String thirdsalerid=((Msalecardinfo) object).getThirdsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+thirdsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Msalecardinfo) object).getBillinsertype().equals("3")){
						s = ((Msalecardinfo) object).getThirdsalecashamt();
					}else{
						s = ((Msalecardinfo) object).getThirdsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getFourthsalerid())){
					String fourthsalerid=((Msalecardinfo) object).getFourthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+fourthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Msalecardinfo) object).getBillinsertype().equals("3")){
						s = ((Msalecardinfo) object).getFourthsalecashamt();
					}else{
						s = ((Msalecardinfo) object).getFourthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getFifthsalerid())){
					String fifthsalerid=((Msalecardinfo) object).getFifthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+fifthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Msalecardinfo) object).getBillinsertype().equals("3")){
						s = ((Msalecardinfo) object).getFifthsalecashamt();
					}else{
						s = ((Msalecardinfo) object).getFifthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				
				if(CommonTool.isEmpty(((Msalecardinfo) object).getSixthsalerid())){
					String sixthsalerid=((Msalecardinfo) object).getSixthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+sixthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Msalecardinfo) object).getBillinsertype().equals("3")){
						s = ((Msalecardinfo) object).getSixthsalecashamt();
					}else{
						s = ((Msalecardinfo) object).getSixthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getSeventhsalerid())){
					String seventhsalerid =((Msalecardinfo) object).getSeventhsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+seventhsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Msalecardinfo) object).getBillinsertype().equals("3")){
						s = ((Msalecardinfo) object).getSeventhsalecashamt();	
					}else{
						s = ((Msalecardinfo) object).getSeventhsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getEighthsalerid())){
					String eighthsalerid = ((Msalecardinfo) object).getEighthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+eighthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Msalecardinfo) object).getBillinsertype().equals("3")){
						s = ((Msalecardinfo) object).getEighthsalecashamt();
					}else{
						s = ((Msalecardinfo) object).getEighthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Msalecardinfo) object).getNinthsalerid())){
					String ninthsalerid=((Msalecardinfo) object).getNinthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+ninthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Msalecardinfo) object).getBillinsertype().equals("3")){
						s = ((Msalecardinfo) object).getNinthsalecashamt();
					}else{
						s = ((Msalecardinfo) object).getNinthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				//第十销售工号
				if(CommonTool.isEmpty(((Msalecardinfo) object).getTenthsalerid())){
					String tenthsalerid=((Msalecardinfo) object).getTenthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+tenthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Msalecardinfo) object).getBillinsertype().equals("3")){
						s = ((Msalecardinfo) object).getTenthsalecashamt();
					}else{
						s = ((Msalecardinfo) object).getTenthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
			}else if(object instanceof Mcardrechargeinfo){
				//第一销售工号
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getFirstsalerid())){
					String firstsalerid=((Mcardrechargeinfo) object).getFirstsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+firstsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					if(((Mcardrechargeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardrechargeinfo) object).getFirstsalecashamt();
						
					}else{
						s = ((Mcardrechargeinfo) object).getFirstsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}	
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getSecondsalerid())){
					String secondsalerid=((Mcardrechargeinfo) object).getSecondsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+secondsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardrechargeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardrechargeinfo) object).getSecondsalecashamt();
					}else{
						s = ((Mcardrechargeinfo) object).getSecondsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getThirdsalerid())){
					String thirdsalerid=((Mcardrechargeinfo) object).getThirdsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+thirdsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardrechargeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardrechargeinfo) object).getThirdsalecashamt();
					}else{
						s = ((Mcardrechargeinfo) object).getThirdsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getFourthsalerid())){
					String fourthsalerid=((Mcardrechargeinfo) object).getFourthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+fourthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardrechargeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardrechargeinfo) object).getFourthsalecashamt();
					}else{
						s = ((Mcardrechargeinfo) object).getFourthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getFifthsalerid())){
					String fifthsalerid=((Mcardrechargeinfo) object).getFifthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+fifthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardrechargeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardrechargeinfo) object).getFifthsalecashamt();
					}else{
						s = ((Mcardrechargeinfo) object).getFifthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getSixthsalerid())){
					String sixthsalerid=((Mcardrechargeinfo) object).getSixthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+sixthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardrechargeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardrechargeinfo) object).getSixthsalecashamt();
					}else{
						s = ((Mcardrechargeinfo) object).getSixthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getSeventhsalerid())){
					String seventhsalerid =((Mcardrechargeinfo) object).getSeventhsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+seventhsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardrechargeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardrechargeinfo) object).getSeventhsalecashamt();	
					}else{
						s = ((Mcardrechargeinfo) object).getSeventhsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getEighthsalerid())){
					String eighthsalerid = ((Mcardrechargeinfo) object).getEighthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+eighthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardrechargeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardrechargeinfo) object).getEighthsalecashamt();
					}else{
						s = ((Mcardrechargeinfo) object).getEighthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getNinthsalerid())){
					String ninthsalerid=((Mcardrechargeinfo) object).getNinthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+ninthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardrechargeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardrechargeinfo) object).getNinthsalecashamt();
					}else{
						s = ((Mcardrechargeinfo) object).getNinthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				//第十销售工号
				if(CommonTool.isEmpty(((Mcardrechargeinfo) object).getTenthsalerid())){
					String tenthsalerid=((Mcardrechargeinfo) object).getTenthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+tenthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardrechargeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardrechargeinfo) object).getTenthsalecashamt();
					}else{
						s = ((Mcardrechargeinfo) object).getTenthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
			}else if(object instanceof Mcardchangeinfo){
				//第一销售工号
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getFirstsalerid())){
					String firstsalerid=((Mcardchangeinfo) object).getFirstsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+firstsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					if(((Mcardchangeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardchangeinfo) object).getFirstsalecashamt();
						
					}else{
						s = ((Mcardchangeinfo) object).getFirstsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}	
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getSecondsalerid())){
					String secondsalerid=((Mcardchangeinfo) object).getSecondsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+secondsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardchangeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardchangeinfo) object).getSecondsalecashamt();
					}else{
						s = ((Mcardchangeinfo) object).getSecondsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getThirdsalerid())){
					String thirdsalerid=((Mcardchangeinfo) object).getThirdsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+thirdsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardchangeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardchangeinfo) object).getThirdsalecashamt();
					}else{
						s = ((Mcardchangeinfo) object).getThirdsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getFourthsalerid())){
					String fourthsalerid=((Mcardchangeinfo) object).getFourthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+fourthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardchangeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardchangeinfo) object).getFourthsalecashamt();
					}else{
						s = ((Mcardchangeinfo) object).getFourthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getFifthsalerid())){
					String fifthsalerid=((Mcardchangeinfo) object).getFifthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+fifthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardchangeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardchangeinfo) object).getFifthsalecashamt();
					}else{
						s = ((Mcardchangeinfo) object).getFifthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getSixthsalerid())){
					String sixthsalerid=((Mcardchangeinfo) object).getSixthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+sixthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardchangeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardchangeinfo) object).getSixthsalecashamt();
					}else{
						s = ((Mcardchangeinfo) object).getSixthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getSeventhsalerid())){
					String seventhsalerid =((Mcardchangeinfo) object).getSeventhsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+seventhsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardchangeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardchangeinfo) object).getSeventhsalecashamt();	
					}else{
						s = ((Mcardchangeinfo) object).getSeventhsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getEighthsalerid())){
					String eighthsalerid = ((Mcardchangeinfo) object).getEighthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+eighthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardchangeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardchangeinfo) object).getEighthsalecashamt();
					}else{
						s = ((Mcardchangeinfo) object).getEighthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getNinthsalerid())){
					String ninthsalerid=((Mcardchangeinfo) object).getNinthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+ninthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardchangeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardchangeinfo) object).getNinthsalecashamt();
					}else{
						s = ((Mcardchangeinfo) object).getNinthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				//第十销售工号
				if(CommonTool.isEmpty(((Mcardchangeinfo) object).getTenthsalerid())){
					String tenthsalerid=((Mcardchangeinfo) object).getTenthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+tenthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((Mcardchangeinfo) object).getBillinsertype().equals("3")){
						s = ((Mcardchangeinfo) object).getTenthsalecashamt();
					}else{
						s = ((Mcardchangeinfo) object).getTenthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
			}
			else if(object instanceof ReEditBillInfo){
				//第一销售工号
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getFirstsalerid())){
					String firstsalerid=((ReEditBillInfo) object).getFirstsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+firstsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					if(((ReEditBillInfo) object).getBillinsertype()==3){
						s = ((ReEditBillInfo) object).getFirstsalecashamt();
						
					}else{
						s = ((ReEditBillInfo) object).getFirstsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}	
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getSecondsalerid())){
					String secondsalerid=((ReEditBillInfo) object).getSecondsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+secondsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((ReEditBillInfo) object).getBillinsertype()==3){
						s = ((ReEditBillInfo) object).getSecondsalecashamt();
					}else{
						s = ((ReEditBillInfo) object).getSecondsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getThirdsalerid())){
					String thirdsalerid=((ReEditBillInfo) object).getThirdsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+thirdsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((ReEditBillInfo) object).getBillinsertype()==3){
						s = ((ReEditBillInfo) object).getThirdsalecashamt();
					}else{
						s = ((ReEditBillInfo) object).getThirdsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getFourthsalerid())){
					String fourthsalerid=((ReEditBillInfo) object).getFourthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+fourthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((ReEditBillInfo) object).getBillinsertype()==3){
						s = ((ReEditBillInfo) object).getFourthsalecashamt();
					}else{
						s = ((ReEditBillInfo) object).getFourthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getFifthsalerid())){
					String fifthsalerid=((ReEditBillInfo) object).getFifthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+fifthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((ReEditBillInfo) object).getBillinsertype()==3){
						s = ((ReEditBillInfo) object).getFifthsalecashamt();
					}else{
						s = ((ReEditBillInfo) object).getFifthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getSixthsalerid())){
					String sixthsalerid=((ReEditBillInfo) object).getSixthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+sixthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((ReEditBillInfo) object).getBillinsertype()==3){
						s = ((ReEditBillInfo) object).getSixthsalecashamt();
					}else{
						s = ((ReEditBillInfo) object).getSixthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getSeventhsalerid())){
					String seventhsalerid =((ReEditBillInfo) object).getSeventhsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+seventhsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((ReEditBillInfo) object).getBillinsertype()==3){
						s = ((ReEditBillInfo) object).getSeventhsalecashamt();	
					}else{
						s = ((ReEditBillInfo) object).getSeventhsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getEighthsalerid())){
					String eighthsalerid = ((ReEditBillInfo) object).getEighthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+eighthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((ReEditBillInfo) object).getBillinsertype()==3){
						s = ((ReEditBillInfo) object).getEighthsalecashamt();
					}else{
						s = ((ReEditBillInfo) object).getEighthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getNinthsalerid())){
					String ninthsalerid=((ReEditBillInfo) object).getNinthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+ninthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((ReEditBillInfo) object).getBillinsertype()==3){
						s = ((ReEditBillInfo) object).getNinthsalecashamt();
					}else{
						s = ((ReEditBillInfo) object).getNinthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
				//第十销售工号
				if(CommonTool.isEmpty(((ReEditBillInfo) object).getTenthsalerid())){
					String tenthsalerid=((ReEditBillInfo) object).getTenthsalerid();
					String strSql ="select staffno from staffinfo where staffno='"+tenthsalerid+"'"+"and department='003'";
					int i= this.amn_Dao.getRowsCount_Ex(strSql);
					BigDecimal s = null;
					//合作
					if(((ReEditBillInfo) object).getBillinsertype()==3){
						s = ((ReEditBillInfo) object).getTenthsalecashamt();
					}else{
						s = ((ReEditBillInfo) object).getTenthsaleamt();
					}
					if( s != null && i>0){
						float f= s.floatValue();
						count = count+f;
					}
				}
			}
			return count;
		}
}