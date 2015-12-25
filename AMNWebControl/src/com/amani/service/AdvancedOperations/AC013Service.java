package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.ReserveBean;

import com.amani.model.Mconsumeinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class AC013Service  extends AMN_ModuleService{
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
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		try
		{
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public List<Mconsumeinfo> loadMasterInfo(String strCompId,String searDate,int iSearchState,String strMemberNo ,String strStaffNo)
	{
		try
		{
			String strSql=" select a.cscompid,a.csbillid,financedate,cscardno,membermphone,csname,reservationflag,reserveStaffinfo " +
					" from compchaininfo , dconsumeinfo b with(nolock),projectnameinfo,mconsumeinfo a with(nolock)  " +
					" left join memberinfo on memberno=cscardno " +
					" where a.cscompid=b.cscompid and a.csbillid=b.csbillid " +
					" and curcomp='"+strCompId+"' and relationcomp=a.cscompid  " +
				    " and financedate='"+CommonTool.setDateMask(searDate)+"' " +
				    " and (isnull(cscardno,'')='"+strMemberNo+"' or '"+strMemberNo+"'='') "+
				    " and (isnull(csfirstsaler,'')='"+strStaffNo+"' or isnull(cssecondsaler,'')='"+strStaffNo+"' or isnull(csthirdsaler,'')='"+strStaffNo+"' or '"+strStaffNo+"'='') "+ 
				    " and (isnull(reservationflag,0)="+iSearchState+" or "+iSearchState+"=3) " +
				    " and  prjno=csitemno  and  isnull(csinfotype,1)=1 and isnull(prjpricetype,0)=1 " +
				    " group by a.cscompid,a.csbillid,financedate,cscardno,membermphone,csname,reservationflag,reserveStaffinfo ";
			AnlyResultSet<List<Mconsumeinfo>> analysis = new AnlyResultSet<List<Mconsumeinfo>>() {
				public List<Mconsumeinfo> anlyResultSet(ResultSet rs) {
					List<Mconsumeinfo> returnvalue=new ArrayList<Mconsumeinfo>();
					Mconsumeinfo bean=null;
					try {
							while(rs!=null&&rs.next()){
								bean=new Mconsumeinfo();
								bean.setBcscompid(rs.getString("cscompid"));
								bean.setBcsbillid(rs.getString("csbillid"));
								bean.setFinancedate(CommonTool.getDateMask(rs.getString("financedate")));
								bean.setCscardno(rs.getString("cscardno"));
								bean.setStrMemberPhone(rs.getString("membermphone"));
								bean.setCsname(rs.getString("csname"));
								bean.setReservationflag((rs.getInt("reservationflag")));
								bean.setReserveStaffinfo(rs.getString("reserveStaffinfo"));
								returnvalue.add(bean);
							}
						} 
						catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return returnvalue;
				}
			};
			List<Mconsumeinfo> ls  =(List<Mconsumeinfo> )this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<ReserveBean> loadDetialInfo(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" select csseqno,itemno=csitemno,itemname=prjname,stafftype=1,staffno=csfirstsaler,staffname=staffname,showflag=csfirstreserve " +
					" from  dconsumeinfo with(nolock),projectnameinfo,staffinfo with(nolock) " +
					"  where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(csfirstsaler,'')<>'' " +
					" and prjno=csitemno  and manageno=csfirstinid and  isnull(csinfotype,1)=1 and isnull(prjpricetype,0)=1 ";
			 strSql=strSql+" union ";
			 strSql=strSql+" select csseqno,itemno=csitemno,itemname=prjname,stafftype=2,staffno=cssecondsaler,staffname=staffname,showflag=cssecondreserve " +
				" from  dconsumeinfo with(nolock),projectnameinfo,staffinfo with(nolock) " +
				"  where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(cssecondsaler,'')<>'' " +
				" and prjno=csitemno  and manageno=cssecondinid and  isnull(csinfotype,1)=1 and isnull(prjpricetype,0)=1 ";
			 strSql=strSql+" union ";
			 strSql=strSql+" select csseqno,itemno=csitemno,itemname=prjname,stafftype=3,staffno=csthirdsaler,staffname=staffname,showflag=csthirdreserve " +
				" from  dconsumeinfo with(nolock),projectnameinfo,staffinfo with(nolock) " +
				"  where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and isnull(csthirdsaler,'')<>'' " +
				" and prjno=csitemno  and manageno=csthirdinid and  isnull(csinfotype,1)=1 and isnull(prjpricetype,0)=1 ";
			 AnlyResultSet<List<ReserveBean>> analysis = new AnlyResultSet<List<ReserveBean>>() {
					public List<ReserveBean> anlyResultSet(ResultSet rs) {
						List<ReserveBean> returnvalue=new ArrayList<ReserveBean>();
						ReserveBean bean=null;
						try {
								while(rs!=null&&rs.next()){
									bean=new ReserveBean();
									bean.setCsseqno(CommonTool.FormatDouble(rs.getDouble("csseqno")));
									bean.setItemno(CommonTool.FormatString(rs.getString("itemno")));
									bean.setItemname(CommonTool.FormatString(rs.getString("itemname")));
									bean.setStafftype(CommonTool.FormatInteger(rs.getInt("stafftype")));
									bean.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
									bean.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
									bean.setShowFlag(CommonTool.FormatInteger(rs.getInt("showflag")));
									returnvalue.add(bean);
								}
							} 
							catch (Exception e) {
							e.printStackTrace();
							return null;
						}
						return returnvalue;
					}
				};
				List<ReserveBean> ls  =(List<ReserveBean> )this.amn_Dao.executeQuery_ex(strSql, analysis);
				analysis=null;
				return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public boolean postReserveInfo(String strCompId,String strBillId,String reserveStaffinfo,List<ReserveBean> lsReserveBean)
	{
		try
		{
			String strSql=" update mconsumeinfo set reservationflag=1,reserveStaffinfo='"+reserveStaffinfo+"' where cscompid='"+strCompId+"' and csbillid='"+strBillId+"' "; 
			strSql=strSql+" update dconsumeinfo set csfirstreserve=0,cssecondreserve=0,csthirdreserve=0 where  cscompid='"+strCompId+"' and csbillid='"+strBillId+"' ";
			this.amn_Dao.executeSql(strSql);
			if(lsReserveBean!=null && lsReserveBean.size()>0)
			{
				strSql="";
				for(int i=0;i<lsReserveBean.size();i++)
				{
					if(lsReserveBean.get(i).getStafftype()==1)
					{
						strSql=strSql+" update dconsumeinfo set csfirstreserve=1 where  cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and csseqno="+lsReserveBean.get(i).getCsseqno()+" ";
					}
					else if(lsReserveBean.get(i).getStafftype()==2)
					{
						strSql=strSql+" update dconsumeinfo set cssecondreserve=1 where  cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and csseqno="+lsReserveBean.get(i).getCsseqno()+" ";
					}
					else if(lsReserveBean.get(i).getStafftype()==3)
					{
						strSql=strSql+" update dconsumeinfo set csthirdreserve=1 where  cscompid='"+strCompId+"' and csbillid='"+strBillId+"' and csseqno="+lsReserveBean.get(i).getCsseqno()+" ";
					}
				}
			}
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	
	
}
