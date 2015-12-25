package com.amani.service.PersonnelControl;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.IntentionBean;
import com.amani.model.Intention;
import com.amani.model.Intentiondetail;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;

@Service
public class PC014Service extends AMN_ModuleService{
	public Intention addintetionBean(){
		Intention bean=new Intention();
		 bean.setIntcomplyno(CommonTool.getLoginInfo("COMPID"));
		 bean.setIntbillid(this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"intention", "intbillid", "SP012"));
		 bean.setIntuser(CommonTool.getLoginInfo("USERID"));
		 bean.setIntdata(getCurrDate());
		 bean.setIntetionstate(0);
		 return bean;
	}
	public boolean addintention(String intcomplyno,String intbillid,int intdproject,int intdstage,String intdstarttime,String intdendtime,String intdata,List<Intentiondetail> intentiondetail)
	{
		boolean bte=true;
		String strsql=" insert into intention(intcomplyno,intbillid,intdproject,intdstage,intdstarttime,intdendtime,intuser,intdata,intetionstate)values("
					+CommonTool.quotedStr(intcomplyno)
					+","
					+CommonTool.quotedStr(intbillid)
					+","
					+CommonTool.FormatInteger(intdproject)
					+","
					+CommonTool.FormatInteger(intdstage)
					+","
					+CommonTool.quotedStr(intdstarttime)
					+","
					+CommonTool.quotedStr(intdendtime)
					+","
					+CommonTool.quotedStr(CommonTool.getLoginInfo("USERID"))
					+","
					+CommonTool.quotedStr(intdata)
					+","
					+CommonTool.FormatInteger(0)+ ")";
				try {
					bte=this.amn_Dao.executeSql(strsql);
					strsql="";
					for (int i = 0; i < intentiondetail.size(); i++) {
						strsql=strsql+" insert into intentiondetail(intdcomplyno,intdbillid,intdwaite,intstuno,incardno,instaffno,instaffname,intposition,intbirthday,intdscore,intpositions,intdproname,intdpunish,intdremark)  values("
						+CommonTool.quotedStr(intentiondetail.get(i).getIntdcomplyno())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getIntdbillid())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getIntdwaite())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getIntstuno())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getIncardno())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getInstaffno())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getInstaffname())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getIntposition())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getIntbirthday())
						+","
						+CommonTool.FormatInteger(intentiondetail.get(i).getIntdscore())
						+","
						+CommonTool.FormatInteger(intentiondetail.get(i).getIntpositions())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getIntdproname())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getIntdpunish())
						+","
						+CommonTool.quotedStr(intentiondetail.get(i).getIntdremark())+ ")";
						
					}
					this.amn_Dao.executeSql(strsql);
				} catch (Exception e) {
					e.printStackTrace();
					bte=false;
				}
			return bte;
	}
	
	/**
	 * 根据条件查询
	 * @return
	 */
	public List<IntentionBean> selectbyparams(String intcomplyno,String intstuno,String incardno,String instaffno){
		String strSql;
		strSql="select * from intention left join intentiondetail on intdbillid=intbillid  where   (intcomplyno='"+intcomplyno+"' or '"+intcomplyno+"'='') and (intstuno='"+intstuno+"' or '"+intstuno+"'='') and (incardno='"+incardno+"' or '"+incardno+"'='') and (instaffno='"+instaffno+"' or '"+instaffno+"'='')";
		strSql=strSql+" select * from intentiondetail left join intention on intdbillid=intbillid where (intdcomplyno='"+intcomplyno+"' or '"+intcomplyno+"'='')  and (intstuno='"+intstuno+"' or '"+intstuno+"'='') and (incardno='"+incardno+"' or '"+incardno+"'='') and (instaffno='"+instaffno+"' or '"+instaffno+"'='')";
		AnlyResultSet<List<IntentionBean>> analysis = new AnlyResultSet<List<IntentionBean>>() {
			List<IntentionBean> ls=new ArrayList<IntentionBean>();
			public List<IntentionBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next())
						{
						IntentionBean bean=new IntentionBean();
						bean.setIntcomplyno(CommonTool.FormatString(rs.getString("intcomplyno")));
						bean.setIntbillid(CommonTool.FormatString(rs.getString("intbillid")));
						bean.setIntdproject(CommonTool.FormatInteger(rs.getInt("intdproject")));
						bean.setIntdstage(CommonTool.FormatInteger(rs.getInt("intdstage")));
						bean.setIntdstarttime(CommonTool.FormatString(rs.getString("intdstarttime")));
						bean.setIntdendtime(CommonTool.FormatString(rs.getString("intdendtime")));
						bean.setIntuser(CommonTool.FormatString(rs.getString("intuser")));
						bean.setIntdata(CommonTool.FormatString(rs.getString("intdata")));
						bean.setIntdid(CommonTool.FormatInteger(rs.getInt("intdid")));
						bean.setIntdcomplyno(CommonTool.FormatString(rs.getString("intdcomplyno")));
						bean.setIntdbillid(CommonTool.FormatString(rs.getString("intdbillid")));
						bean.setIntdwaite(CommonTool.FormatString(rs.getString("intdwaite")));
						bean.setIntstuno(CommonTool.FormatString(rs.getString("intstuno")));
						bean.setIncardno(CommonTool.FormatString(rs.getString("incardno")));
						bean.setInstaffno(CommonTool.FormatString(rs.getString("instaffno")));
						bean.setInstaffname(CommonTool.FormatString(rs.getString("instaffname")));
						bean.setIntposition(CommonTool.FormatString(rs.getString("intposition")));
						bean.setIntbirthday(CommonTool.FormatString(rs.getString("intbirthday")));
						bean.setIntdscore(CommonTool.FormatInteger(rs.getInt("intdscore")));
						bean.setIntpositions(CommonTool.FormatInteger(rs.getInt("intpositions")));
						bean.setIntdproname(CommonTool.FormatString(rs.getString("intdproname")));
						bean.setIntdpunish(CommonTool.FormatString(rs.getString("intdpunish")));
						bean.setIntdremark(CommonTool.FormatString(rs.getString("intdremark")));
						ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<IntentionBean> ls  =(List<IntentionBean> )this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
	/**
	 * 查询所有学员基本信息
	 * @return
	 */
	public List<Intention> selectintents(){
		String strSql;
		strSql="select * from intention,compchaininfo where curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=intcomplyno and replace(substring(intdata,1,10),'-','')='"+CommonTool.getCurrDate()+"'  ";
		AnlyResultSet<List<Intention>> analysis = new AnlyResultSet<List<Intention>>() {
			List<Intention> ls=new ArrayList<Intention>();
			public List<Intention> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next())
						{
						Intention bean=new Intention();
						bean.setIntcomplyno(CommonTool.FormatString(rs.getString("intcomplyno")));
						bean.setIntbillid(CommonTool.FormatString(rs.getString("intbillid")));
						bean.setIntdproject(CommonTool.FormatString(rs.getInt("intdproject")));
						bean.setIntdstage(CommonTool.FormatInteger(rs.getInt("intdstage")));
						bean.setIntdstarttime(CommonTool.FormatString(rs.getString("intdstarttime")));
						bean.setIntdendtime(CommonTool.FormatString(rs.getString("intdendtime")));
						bean.setIntetionstate(CommonTool.FormatInteger(rs.getInt("intetionstate")));
						bean.setIntuser(CommonTool.FormatString(rs.getString("intuser")));
						bean.setIntdata(CommonTool.FormatString(rs.getString("intdata")));
						ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<Intention> ls  =(List<Intention> )this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
	/**
	 * 查询所有培训信息
	 * @return
	 */
	public List<Intentiondetail> selectintentdetails(String intbillid){
		String strSql;
		strSql="select * from intentiondetail where intdbillid='"+intbillid+"'";
		AnlyResultSet<List<Intentiondetail>> analysis = new AnlyResultSet<List<Intentiondetail>>() {
			List<Intentiondetail> ls=new ArrayList<Intentiondetail>();
			public List<Intentiondetail> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						Intentiondetail bean=new Intentiondetail();
						bean.setIntdid(CommonTool.FormatInteger(rs.getInt("intdid")));
						bean.setIntdcomplyno(CommonTool.FormatString(rs.getString("intdcomplyno")));
						bean.setIntdbillid(CommonTool.FormatString(rs.getString("intdbillid")));
						bean.setIntdwaite(CommonTool.FormatString(rs.getString("intdwaite")));
						bean.setIntstuno(CommonTool.FormatString(rs.getString("intstuno")));
						bean.setIncardno(CommonTool.FormatString(rs.getString("incardno")));
						bean.setInstaffno(CommonTool.FormatString(rs.getString("instaffno")));
						bean.setInstaffname(CommonTool.FormatString(rs.getString("instaffname")));
						bean.setIntposition(CommonTool.FormatString(rs.getString("intposition")));
						bean.setIntbirthday(CommonTool.FormatString(rs.getString("intbirthday")));
						bean.setIntdscore(CommonTool.FormatInteger(rs.getInt("intdscore")));
						bean.setIntpositions(CommonTool.FormatInteger(rs.getInt("intpositions")));
						bean.setIntdproname(CommonTool.FormatString(rs.getString("intdproname")));
						bean.setIntdpunish(CommonTool.FormatString(rs.getString("intdpunish")));
						bean.setIntdremark(CommonTool.FormatString(rs.getString("intdremark")));
						ls.add(bean);
 					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<Intentiondetail> ls  =(List<Intentiondetail> )this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
	
	public List<Intentiondetail> loadsearchInfo(String strComp,String strEmpId,int strProject ){
		String strSql;
		strSql="	select b.intdcomplyno,compname,instaffno,instaffname,intstuno,incardno,intbirthday,intposition,intdproject,intdstage,intdstarttime,intdendtime,intdscore,intpositions,intdproname,intdpunish,intdremark " +
				"  from intention a,intentiondetail b,companyinfo c,compchaininfo " +
				"  where a.intbillid=b.intdbillid   " +
				" and curcomp='"+strComp+"' and relationcomp=intdcomplyno " +
				" and (isnull(instaffno,'')='"+strEmpId+"' or '"+strEmpId+"'='' ) " +
				" and (isnull(intdproject,'')="+strProject+" or "+strProject+"='99' )" +
				" and c.compno=b.intdcomplyno " +
				" order by intdcomplyno,instaffno ";
		AnlyResultSet<List<Intentiondetail>> analysis = new AnlyResultSet<List<Intentiondetail>>() {
			List<Intentiondetail> ls=new ArrayList<Intentiondetail>();
			public List<Intentiondetail> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						Intentiondetail bean=new Intentiondetail();
						bean.setIntdcomplyno(CommonTool.FormatString(rs.getString("intdcomplyno")));
						bean.setIntdcomplyname(CommonTool.FormatString(rs.getString("compname")));
						bean.setIntstuno(CommonTool.FormatString(rs.getString("intstuno")));
						bean.setIncardno(CommonTool.FormatString(rs.getString("incardno")));
						bean.setInstaffno(CommonTool.FormatString(rs.getString("instaffno")));
						bean.setInstaffname(CommonTool.FormatString(rs.getString("instaffname")));
						bean.setIntposition(CommonTool.FormatString(rs.getString("intposition")));
						bean.setIntbirthday(CommonTool.FormatString(rs.getString("intbirthday")));
						bean.setIntdscore(CommonTool.FormatInteger(rs.getInt("intdscore")));
						bean.setIntpositions(CommonTool.FormatInteger(rs.getInt("intpositions")));
						bean.setIntdproname(CommonTool.FormatString(rs.getString("intdproname")));
						bean.setIntdpunish(CommonTool.FormatString(rs.getString("intdpunish")));
						bean.setIntdproject(CommonTool.FormatInteger(rs.getInt("intdproject")));
						bean.setIntdstage(CommonTool.FormatInteger(rs.getInt("intdstage")));
						bean.setIntdstarttime(CommonTool.getDateMask(rs.getString("intdstarttime")));
						bean.setIntdendtime(CommonTool.getDateMask(rs.getString("intdendtime")));
						bean.setIntdremark(CommonTool.FormatString(rs.getString("intdremark")));
						ls.add(bean);
 					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<Intentiondetail> ls  =(List<Intentiondetail> )this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}

	public Intention loadintention(String intbillid){
    	String strSql="select * from intention where intbillid='"+intbillid+"' ";
    	AnlyResultSet<Intention> analysis = new AnlyResultSet<Intention>() {
			public Intention anlyResultSet(ResultSet rs) {
				Intention bean=new Intention();
				try {
					
					if(rs!=null&&rs.next()){
						bean.setIntcomplyno(CommonTool.FormatString(rs.getString("intcomplyno")));
						bean.setIntbillid(CommonTool.FormatString(rs.getString("intbillid")));
						bean.setIntdproject(CommonTool.FormatString(rs.getInt("intdproject")));
						bean.setIntdstage(CommonTool.FormatInteger(rs.getInt("intdstage")));
						bean.setIntdstarttime(CommonTool.FormatString(rs.getString("intdstarttime")));
						bean.setIntdendtime(CommonTool.FormatString(rs.getString("intdendtime")));
						bean.setIntetionstate(CommonTool.FormatInteger(rs.getInt("intetionstate")));
						bean.setIntuser(CommonTool.FormatString(rs.getString("intuser")));
						bean.setIntdata(CommonTool.FormatString(rs.getString("intdata")));
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return bean;
			}
		};
		Intention result  =(Intention)this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return result;
}
	
	//获得员工资料
	public String loadEmpNameById(String strCompId,String strEmpId,StringBuffer validateMsg )
	{
		String strSql = "select * from staffinfo where compno = "+ CommonTool.quotedStr(strCompId)+" and staffno='"+strEmpId+"' ";
		AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
			public String anlyResultSet(ResultSet rs) {
				String returnValue = "";
				try {
					if (rs != null && rs.next() == true) {
						returnValue = rs.getString("pccid");
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
			return "";
		}
		validateMsg.append("");
		return strReturnValue;
	}
	
	//获得员工资料
	public String loadEmpNameByIds(String strCompId,String strEmpId,StringBuffer validateMsg )
	{
		String strSql = "select * from staffinfo where compno = "+ CommonTool.quotedStr(strCompId)+" and staffno='"+strEmpId+"' ";
		AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
			public String anlyResultSet(ResultSet rs) {
				String returnValue = "";
				try {
					if (rs != null && rs.next() == true) {
						returnValue = rs.getString("position");
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
			return "";
		}
		validateMsg.append("");
		return strReturnValue;
	}
public static String getCurrDate() {
		SimpleDateFormat objStdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date CurrDate = new Date(System.currentTimeMillis());
		return objStdFormat.format(CurrDate);
	}
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

}
