package com.amani.service.PersonnelControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Commoninfo;
import com.amani.model.Staffchangeinfo;

import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC006Service extends AMN_ReportService{
	
	public List<Staffchangeinfo> loadDateSetByCompId(String strCompId,String strMonth)
	{
		try
		{
			String strSql="select changetype,appchangecompid,beforecompname=b.compname,changestaffno,staffname,staffpcid,staffphone,c.leveltype,c.fillno," +
					" beforedepartment,beforepostation,aftercompid,aftercompname=d.compname,afterstaffno,afterdepartment,afterpostation,arrivaldate,validatestartdate,validateenddate,a.remark,c.staffmark,socialsecurity " +
						"  from compchaininfo,staffinfo c ,staffchangeinfo a " +
						"   left join companyinfo b on   appchangecompid=b.compno  "+
						"   left join companyinfo d on   aftercompid=d.compno "+
						"	where  ISNULL(billflag,0)=8  and SUBSTRING(validatestartdate,1,6)='"+strMonth+"' " +
						"	and curcomp='"+strCompId+"' and (relationcomp=appchangecompid or relationcomp=aftercompid) and a.staffmangerno=c.manageno and isnull(c.stafftype,0)=0 " ;
			System.out.println(strSql);
			AnlyResultSet<List<Staffchangeinfo>> analysis = new AnlyResultSet<List<Staffchangeinfo>>() {
				public List<Staffchangeinfo> anlyResultSet(ResultSet rs) {
					List<Staffchangeinfo> returnValue = new ArrayList();
					Staffchangeinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Staffchangeinfo();
							record.setBchangetype(CommonTool.FormatInteger(rs.getInt("changetype")));
							record.setAppchangecompid(CommonTool.FormatString(rs.getString("appchangecompid")));
							record.setAppchangecompname(CommonTool.FormatString(rs.getString("beforecompname")));
							record.setChangestaffno(CommonTool.FormatString(rs.getString("changestaffno")));
							record.setChangestaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setStaffpcid(CommonTool.FormatString(rs.getString("staffpcid")));
							record.setStaffphone(CommonTool.FormatString(rs.getString("staffphone")));
							record.setBeforedepartment(CommonTool.FormatString(rs.getString("beforedepartment")));
							record.setBeforepostation(CommonTool.FormatString(rs.getString("beforepostation")));
							record.setAftercompid(CommonTool.FormatString(rs.getString("aftercompid")));
							record.setAftercompname(CommonTool.FormatString(rs.getString("aftercompname")));
							record.setAfterstaffno(CommonTool.FormatString(rs.getString("afterstaffno")));
							record.setLeaveltype(CommonTool.FormatInteger(rs.getInt("leveltype")));
							record.setRemark(CommonTool.FormatString(rs.getString("remark")));
							record.setStaffmark(CommonTool.FormatString(rs.getString("staffmark")));
							record.setFillno(rs.getString("fillno"));
							if(CommonTool.FormatInteger(rs.getInt("changetype"))==1)
							{
								record.setAfterdepartment(CommonTool.FormatString(rs.getString("beforedepartment")));
								record.setAfterpostation(CommonTool.FormatString(rs.getString("beforepostation")));
							}
							else
							{

								record.setAfterdepartment(CommonTool.FormatString(rs.getString("afterdepartment")));
								record.setAfterpostation(CommonTool.FormatString(rs.getString("afterpostation")));
							}
							if(CommonTool.FormatInteger(rs.getInt("leveltype"))==2)
							{
								record.setLeaveltypeText("自动离职");
							}
							else
							{
								record.setLeaveltypeText("正常离职");
							}
							record.setArrivaldate(CommonTool.getDateMask(rs.getString("arrivaldate")));
							record.setValidatestartdate(CommonTool.getDateMask(rs.getString("validatestartdate")));
							record.setValidateenddate(CommonTool.getDateMask(rs.getString("validateenddate")));
							record.setSocialsecurity(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("socialsecurity")))));
							
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<Staffchangeinfo> ls= (List<Staffchangeinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			List<Commoninfo> lsCommA=null;
			List<Commoninfo> lsCommB=null;
			if(ls!=null && ls.size()>0)
			{
				lsCommA=(List<Commoninfo>)this.getDataTool().loadCommonInfoByCode("GZGW");
				lsCommB=(List<Commoninfo>)this.getDataTool().loadCommonInfoByCode("BMZL");
				for(int i=0;i<ls.size();i++)
				{
					if(!CommonTool.FormatString(ls.get(i).getBeforedepartment()).equals(""))
					{
						for(int j=0;j<lsCommB.size();j++)
						{
							if(ls.get(i).getBeforedepartment().equals(lsCommB.get(j).getId().getParentcodekey()))
							{
								ls.get(i).setBeforedepartmentText(lsCommB.get(j).getParentcodevalue());
								break;
							}
						}
					}
					
					if(!CommonTool.FormatString(ls.get(i).getAfterdepartment()).equals(""))
					{
						for(int j=0;j<lsCommB.size();j++)
						{
							if(ls.get(i).getAfterdepartment().equals(lsCommB.get(j).getId().getParentcodekey()))
							{
								ls.get(i).setAfterdepartmentText(lsCommB.get(j).getParentcodevalue());
								break;
							}
						}
					}
					
					if(!CommonTool.FormatString(ls.get(i).getBeforepostation()).equals(""))
					{
						for(int j=0;j<lsCommA.size();j++)
						{
							if(ls.get(i).getBeforepostation().equals(lsCommA.get(j).getId().getParentcodekey()))
							{
								ls.get(i).setBeforepostationText(lsCommA.get(j).getParentcodevalue());
								break;
							}
						}
					}
					
					if(!CommonTool.FormatString(ls.get(i).getAfterpostation()).equals(""))
					{
						for(int j=0;j<lsCommA.size();j++)
						{
							if(ls.get(i).getAfterpostation().equals(lsCommA.get(j).getId().getParentcodekey()))
							{
								ls.get(i).setAfterpostationText(lsCommA.get(j).getParentcodevalue());
								break;
							}
						}
					}
				}
			}
			analysis=null;
			lsCommA=null;
			lsCommB=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
}
