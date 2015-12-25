package com.amani.service.SellReportControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.bean.SC015Bean;
import com.amani.model.Yearcardinof;
import com.amani.model.Yearselldetal;
import com.amani.model.Yearsellinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;

@Service
public class SC015Service extends AMN_ReportService{
	
	public List<SC015Bean> loadData(String strCompId,String strFromDate,String strToDate,int type)
	{
		StringBuffer buffer=new StringBuffer();
		if(type==1)
		{
			buffer.append(" select compid,compname,sum(isnull(yearcount_mf,0)-isnull(yearcount_mf_f,0)) yearcount_mf,sum(isnull(bncount_mf,0)-isnull(bncount_mf_f,0)) bncount_mf,sum(isnull(jkcount_mf,0)-isnull(jkcount_mf_f,0)) jkcount_mf,sum(isnull(monthcount_mf,0)-isnull(monthcount_mf_f,0)) monthcount_mf, ");
			buffer.append(" sum(isnull(yearcount_mr,0)-isnull(yearcount_mr_f,0)) yearcount_mr,sum(isnull(bncount_mr,0)-isnull(bncount_mr_f,0)) bncount_mr,sum(isnull(jkcount_mr,0)-isnull(jkcount_mr_f,0)) jkcount_mr,sum(isnull(monthcount_mr,0)-isnull(monthcount_mr_f,0)) monthcount_mr ");
			buffer.append(" from( select a.compid,compname,yearcount_mf=case when substring(packno,1,3)='201' and substring(itemno,1,1)='3' and isnull(num,0)>0 then count(a.billid) else 0 end,   ");
			buffer.append(" bncount_mf=case when substring(packno,1,3)='202' and substring(itemno,1,1)='3' and isnull(num,0)>0 then count(a.billid) else 0 end, ");
			buffer.append(" jkcount_mf=case when substring(packno,1,3)='203' and substring(itemno,1,1)='3' and isnull(num,0)>0 then count(a.billid) else 0 end, ");
			buffer.append(" monthcount_mf=case when substring(packno,1,3)='204' and substring(itemno,1,1)='3' and isnull(num,0)>0 then count(a.billid) else 0 end, ");
			buffer.append(" yearcount_mr=case when substring(packno,1,3)='201' and substring(itemno,1,1)='4' and isnull(num,0)>0 then count(a.billid) else 0 end,   ");
			buffer.append(" bncount_mr=case when substring(packno,1,3)='202' and substring(itemno,1,1)='4'  and isnull(num,0)>0 then count(a.billid) else 0 end, ");
			buffer.append(" jkcount_mr=case when substring(packno,1,3)='203' and substring(itemno,1,1)='4' and isnull(num,0)>0 then count(a.billid) else 0 end, ");
			buffer.append(" monthcount_mr=case when substring(packno,1,3)='204' and substring(itemno,1,1)='4' and isnull(num,0)>0 then count(a.billid) else 0 end, ");
			buffer.append(" yearcount_mf_f=case when substring(packno,1,3)='201' and substring(itemno,1,1)='3' and isnull(num,0)<0 then count(a.billid) else 0 end, ");
			buffer.append(" bncount_mf_f=case when substring(packno,1,3)='202' and substring(itemno,1,1)='3' and isnull(num,0)<0 then count(a.billid) else 0 end, ");
			buffer.append(" jkcount_mf_f=case when substring(packno,1,3)='203' and substring(itemno,1,1)='3' and isnull(num,0)<0 then count(a.billid) else 0 end, ");
			buffer.append(" monthcount_mf_f=case when substring(packno,1,3)='204' and substring(itemno,1,1)='3' and isnull(num,0)<0 then count(a.billid) else 0 end, ");
			buffer.append(" yearcount_mr_f=case when substring(packno,1,3)='201' and substring(itemno,1,1)='4' and isnull(num,0)<0 then count(a.billid) else 0 end, ");
			buffer.append(" bncount_mr_f=case when substring(packno,1,3)='202' and substring(itemno,1,1)='4'  and isnull(num,0)<0 then count(a.billid) else 0 end, ");
			buffer.append(" jkcount_mr_f=case when substring(packno,1,3)='203' and substring(itemno,1,1)='4' and isnull(num,0)<0 then count(a.billid) else 0 end, ");
			buffer.append(" monthcount_mr_f=case when substring(packno,1,3)='204' and substring(itemno,1,1)='4' and isnull(num,0)<0 then count(a.billid) else 0 end ");
			buffer.append(" from yearsellinfo a,yearselldetal b,compchaininfo,companyinfo  where a.compid=b.compid  and a.billid=b.billid ");
			buffer.append(" and a.accountdate between '"+CommonTool.setDateMask(strFromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"' ");
			buffer.append(" and a.compid=relationcomp  and curcomp='"+strCompId+"'  and a.compid=compno and isnull(backcsflag,0)=0 ");
			buffer.append(" group by a.compid,substring(packno,1,3),compname,itemno,num) b ");
			buffer.append(" group by compid,compname  ");
			buffer.append(" order by compid ");
			/*buffer.append(" select compid code ,compname name,sum(beturynum) beturynum,sum(beturyamt) beturyamt,sum(hairnum) hairnum,sum(hairamt) hairamt from( ");
			buffer.append(" select a.compid,compname,beturynum=case when prjtype='4' then sum(isnull(num,0)/isnull(packageprocount,0)) else 0 end, ");
			buffer.append(" beturyamt=case when prjtype='4' then sum(amt) else 0 end, ");
			buffer.append(" hairnum=case when prjtype='3' then sum(isnull(num,0)/isnull(packageprocount,0)) else 0 end, ");
			buffer.append(" hairamt=case when prjtype='3' then sum(amt) else 0 end ");
			buffer.append(" from yearsellinfo a with(nolock) ,yearselldetal b with(nolock) ,compchaininfo c with(nolock) ,  ");
			buffer.append(" mpackageinfo e with(nolock),companyinfo with(nolock),dmpackageinfo d with(nolock),projectnameinfo with(nolock) ");
			buffer.append(" where a.compid=b.compid and b.packno=d.packageno and b.itemno=prjno ");
			buffer.append(" and a.billid=b.billid and b.compid=e.compid and b.packno=e.packageno and a.accountdate between '"+CommonTool.setDateMask(strFromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"'");
			buffer.append(" and a.compid=relationcomp and curcomp='"+strCompId+"' and usetype='4' and compno=a.compid and b.compid=d.compid group by a.compid,compname,prjtype");
			buffer.append(" ) b  group by compid,compname order by compid ");*/
			
		}
		else
		{
			/*buffer.append(" select packageno code,packagename name,sum(beturynum) beturynum,sum(beturyamt) beturyamt,sum(hairnum) hairnum,sum(hairamt) hairamt from( ");
			buffer.append(" select e.packageno,packagename,beturynum=case when prjtype='4' then sum(isnull(num,0)/isnull(packageprocount,0)) else 0 end, ");
			buffer.append(" beturyamt=case when prjtype='4' then sum(amt) else 0 end, ");
			buffer.append(" hairnum=case when prjtype='3' then sum(isnull(num,0)/isnull(packageprocount,0)) else 0 end, ");
			buffer.append(" hairamt=case when prjtype='3' then sum(amt) else 0 end ");
			buffer.append(" from yearsellinfo a with(nolock) ,yearselldetal b with(nolock) ,compchaininfo c with(nolock) ,   ");
			buffer.append(" mpackageinfo e with(nolock),companyinfo with(nolock),dmpackageinfo d with(nolock),projectnameinfo with(nolock) ");
			buffer.append(" where a.compid=b.compid and a.billid=b.billid and a.compid=relationcomp and curcomp='"+strCompId+"' ");
			buffer.append(" and usetype='4' and compno=a.compid and b.compid=d.compid and b.packno=d.packageno and b.itemno=prjno ");
			buffer.append(" and b.compid=e.compid and b.packno=e.packageno and a.accountdate between '"+CommonTool.setDateMask(strFromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"' group by e.packageno,packagename,prjtype) b");
			buffer.append(" group by packageno,packagename order by packageno ");*/
		}
		
		ResultSet rs=null;
		List<SC015Bean> lsBeans =new ArrayList<SC015Bean>();
		try {
			rs=amn_Dao.executeQuery(buffer.toString());
			if(rs!=null)
			{
				while(rs.next())
				{
					SC015Bean bean=new SC015Bean();
					bean.setStrCode(rs.getString("compid"));
					bean.setStrName(rs.getString("compname"));
					bean.setYearcount_mf(rs.getDouble("yearcount_mf"));
					bean.setYearcount_mr(rs.getDouble("yearcount_mr"));
					bean.setBncount_mf(rs.getDouble("bncount_mf"));
					bean.setBncount_mr(rs.getDouble("bncount_mr"));
					bean.setJkcount_mf(rs.getDouble("jkcount_mf"));
					bean.setJkcount_mr(rs.getDouble("jkcount_mr"));
					bean.setMonthcount_mf(rs.getDouble("monthcount_mf"));
					bean.setMonthcount_mr(rs.getDouble("monthcount_mr"));
					lsBeans.add(bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lsBeans;
	}
}
