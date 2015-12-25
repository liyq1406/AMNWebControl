package com.amani.service.AdvancedOperations;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.StaffWarkSalaryAnlanysis;

import com.amani.model.Staffabsenceinfo;
import com.amani.model.Staffchangeinfo;
import com.amani.model.StaffchangeinfoId;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class AC017Service extends AMN_ReportService{
	
	public List<StaffWarkSalaryAnlanysis> loadDateSetByCompId(String strCompId,String strDateFrom,int notPrintCash)
	{
		try
		{
			String strSql="select strCompId,strCompName," +
					" sumHasSocialSaraly=SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=2 and ISNULL(staffsocials,0)>0  and ISNULL(staffsocialsource,'')<>'南昌分公司' then ISNULL(factpaysalary,0) else 0 end ))," +
					" sumNoSocialSaraly=SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=2 and ISNULL(staffsocials,0)=0 and strCompId not in   ('301','302','303') then ISNULL(factpaysalary,0) else 0 end ))," +
					" sumReadSalary=SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=4  and ISNULL(staffsocialsource,'')<>'南昌分公司' and strCompId not in   ('301','302','303') then ISNULL(factpaysalary,0) else 0 end ))," +
					" sumLeavelSalary=SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=3  and ISNULL(staffsocialsource,'')<>'南昌分公司' and strCompId not in   ('301','302','303') then ISNULL(factpaysalary,0) else 0 end ))" +
					" from compute_staff_work_salary_byday a,compchaininfo b " +
					" where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' " +
					" group by  strCompId,strCompName	 order by strCompId ";
			AnlyResultSet<List<StaffWarkSalaryAnlanysis>> analysis = new AnlyResultSet<List<StaffWarkSalaryAnlanysis>>() {
				public List<StaffWarkSalaryAnlanysis> anlyResultSet(ResultSet rs) {
					List<StaffWarkSalaryAnlanysis> returnValue = new ArrayList();
					StaffWarkSalaryAnlanysis record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new StaffWarkSalaryAnlanysis();
							record.setStrCompId(CommonTool.FormatString(rs.getString("strCompId")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("strCompName")));
							record.setSumHasSocialSaraly(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sumHasSocialSaraly")))));
							record.setSumNoSocialSaraly(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sumNoSocialSaraly")))));
							record.setSumReadSalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sumReadSalary")))));
							record.setSumLeavelSalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sumLeavelSalary")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<StaffWarkSalaryAnlanysis> ls= (List<StaffWarkSalaryAnlanysis>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<StaffWarkSalaryAnlanysis> loadDateSetByCompIdTA(String strCompId,String strDateFrom,int notPrintCash)
	{
		try
		{
			String strSql="select strCompId,strCompName," +
					" sumHasSocialSaraly=SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=2 and  ISNULL(staffsocials,0)>0  and ISNULL(staffsocialsource,'')<>'南昌分公司'  then ISNULL(factpaysalary,0) else 0 end ))," +
					" sumNoSocialSaraly=SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=2 and ( ISNULL(staffsocials,0)=0 or ( ISNULL(staffsocials,0)>0 and ISNULL(staffsocialsource,'')='南昌分公司'))  then ISNULL(factpaysalary,0) else 0 end ))," +
					" sumReadSalary=SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=4   then ISNULL(factpaysalary,0) else 0 end ))," +
					" sumLeavelSalary=SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=3   then ISNULL(factpaysalary,0) else 0 end ))" +
					" from compute_staff_work_salary_byday a,compchaininfo b " +
					" where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' " +
					" group by  strCompId,strCompName	 order by strCompId ";
			AnlyResultSet<List<StaffWarkSalaryAnlanysis>> analysis = new AnlyResultSet<List<StaffWarkSalaryAnlanysis>>() {
				public List<StaffWarkSalaryAnlanysis> anlyResultSet(ResultSet rs) {
					List<StaffWarkSalaryAnlanysis> returnValue = new ArrayList();
					StaffWarkSalaryAnlanysis record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new StaffWarkSalaryAnlanysis();
							record.setStrCompId(CommonTool.FormatString(rs.getString("strCompId")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("strCompName")));
							record.setSumHasSocialSaraly(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sumHasSocialSaraly")))));
							record.setSumNoSocialSaraly(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sumNoSocialSaraly")))));
							record.setSumReadSalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sumReadSalary")))));
							record.setSumLeavelSalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("sumLeavelSalary")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<StaffWarkSalaryAnlanysis> ls= (List<StaffWarkSalaryAnlanysis>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public  List<StaffWarkSalaryAnlanysis> loadDetailDateSetByCompId(String strCompId,String strDateFrom,int detialtype)
	{
		try
		{
			String strSql=" select strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,factpaysalary where strCompId='"+strCompId+"'  and  computeday='"+strDateFrom+"'  ";
			if(detialtype==1)  //在职有卡有社保
			{
				strSql="  select strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
					"	 factpaysalary= convert(numeric(20,0),case when isnull(needpaysalary,0)>6500 then   convert(numeric(20,0),isnull(factpaysalary,0))-convert(numeric(20,0),(isnull(needpaysalary,0)-6500)) else isnull(factpaysalary,0) end) " +
					"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
					"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=2   and isnull(factpaysalary,0)>0 and ISNULL(staffsocialsource,'')<>'南昌分公司'" +
					" 	 order by staffsocialsource,strCompId,strCompName ";
			}
			else if(detialtype==2)  //在职有卡有社保(补)
			{
				strSql="  select strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),(isnull(needpaysalary,0)-6500)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"'  and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=2 and  isnull(needpaysalary,0)>6500  and ISNULL(staffsocialsource,'')<>'南昌分公司' " +
				" 	 order by staffsocialsource,strCompId,strCompName ";
			}
			else if(detialtype==3)  //在职有卡无社保
			{
				strSql="  select strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),isnull(factpaysalary,0)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and  ISNULL(staffsocials,0)=0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=2  and isnull(factpaysalary,0)>0 and strCompId not in   ('301','302','303') " +
				" 	 order by staffsocialsource,strCompId,strCompName ";
			}
			else if(detialtype==4)  //在职无卡
			{
				strSql="  select strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),isnull(factpaysalary,0)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and len(ISNULL(staffbankaccountno,''))<10 and ISNULL(staffcurstate,2)=2   and isnull(factpaysalary,0)>0 	 and strCompId not in   ('301','302','303') " +
				" 	 order by staffsocialsource,strCompId,strCompName ";
			}
			else if(detialtype==5)  //离职有卡有社保
			{
				strSql="  select strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),case when isnull(needpaysalary,0)>6500 then   convert(numeric(20,0),isnull(factpaysalary,0))-convert(numeric(20,0),(isnull(needpaysalary,0)-6500)) else isnull(factpaysalary,0) end)  " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,0)=3   and isnull(factpaysalary,0)>0   and ISNULL(staffsocialsource,'')<>'南昌分公司' " +
				" 	 order by staffsocialsource,strCompId,strCompName ";
			}
			else if(detialtype==6)  //离职有卡有社保(补)
			{
				strSql="  select strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),(isnull(needpaysalary,0)-6500))  " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,0)=3 and  isnull(needpaysalary,0)>6500  and ISNULL(staffsocialsource,'')<>'南昌分公司'" +
				" 	 order by staffsocialsource,strCompId,strCompName ";
			}
			else if(detialtype==7)  //离职有卡无社保
			{
				strSql="  select strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),isnull(factpaysalary,0)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and  ISNULL(staffsocials,0)=0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=3  and isnull(factpaysalary,0)>0 and strCompId not in   ('301','302','303') " +
				" 	 order by staffsocialsource,strCompId,strCompName ";
			}
			else if(detialtype==8)  //离职无卡
			{
				strSql="  select strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),isnull(factpaysalary,0)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and len(ISNULL(staffbankaccountno,''))<10 and ISNULL(staffcurstate,2)=3   and isnull(factpaysalary,0)>0 	 and strCompId not in   ('301','302','303') " +
				" 	 order by staffsocialsource,strCompId,strCompName ";
			}
			else if(detialtype==9)  //待发
			{
				strSql="  select strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary=convert(numeric(20,0),isnull(factpaysalary,0)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"'  and  ISNULL(staffcurstate,0)=4  and isnull(factpaysalary,0)>0	and strCompId not in   ('301','302','303')  " +
				" 	 order by staffsocialsource,strCompId,strCompName ";
			}
			System.out.println(strSql);
			AnlyResultSet<List<StaffWarkSalaryAnlanysis>> analysis = new AnlyResultSet<List<StaffWarkSalaryAnlanysis>>() {
				public List<StaffWarkSalaryAnlanysis> anlyResultSet(ResultSet rs) {
					List<StaffWarkSalaryAnlanysis> returnValue = new ArrayList();
					StaffWarkSalaryAnlanysis record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new StaffWarkSalaryAnlanysis();
							record.setStrCompId(CommonTool.FormatString(rs.getString("strCompId")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("strCompName")));
							record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setStaffpositionname(CommonTool.FormatString(rs.getString("staffpositionname")));
							record.setStaffsocialsource(CommonTool.FormatString(rs.getString("staffsocialsource")));
							record.setStaffpcid(CommonTool.FormatString(rs.getString("staffpcid")));
							record.setStaffbankaccountno(CommonTool.FormatString(rs.getString("staffbankaccountno")));
							record.setFactpaysalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("factpaysalary")))));
							record.setStrBackName(rs.getString("parentcodevalue"));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<StaffWarkSalaryAnlanysis> ls= (List<StaffWarkSalaryAnlanysis>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	
	public  List<StaffWarkSalaryAnlanysis> loadDetailDateSetByCompId(String strCompId,String strDateFrom)
	{
		try
		{
			String strSql="  select pageType=1,strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
					"	 factpaysalary= convert(numeric(20,0),case when isnull(needpaysalary,0)>6500 then   convert(numeric(20,0),isnull(factpaysalary,0))-convert(numeric(20,0),(isnull(needpaysalary,0)-6500)) else isnull(factpaysalary,0) end) " +
					"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
					"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=2   and isnull(factpaysalary,0)>0 and ISNULL(staffsocialsource,'')<>'南昌分公司' ";
				
				strSql=strSql+" union all select pageType=2, strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),(isnull(needpaysalary,0)-6500)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"'  and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=2 and  isnull(needpaysalary,0)>6500  and ISNULL(staffsocialsource,'')<>'南昌分公司' ";
				
				strSql=strSql+" union all  select pageType=3,strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),isnull(factpaysalary,0)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and  ISNULL(staffsocials,0)=0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=2  and isnull(factpaysalary,0)>0 and strCompId not in   ('301','302','303') " ;
				
				strSql=strSql+" union all select pageType=4,strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),isnull(factpaysalary,0)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and len(ISNULL(staffbankaccountno,''))<10 and ISNULL(staffcurstate,2)=2   and isnull(factpaysalary,0)>0 	 and strCompId not in   ('301','302','303') " ;
				
				strSql=strSql+" union all  select pageType=5,strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),case when isnull(needpaysalary,0)>6500 then   convert(numeric(20,0),isnull(factpaysalary,0))-convert(numeric(20,0),(isnull(needpaysalary,0)-6500)) else isnull(factpaysalary,0) end)  " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,0)=3   and isnull(factpaysalary,0)>0   and ISNULL(staffsocialsource,'')<>'南昌分公司' ";
				
				strSql=strSql+" union all  select pageType=6,strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),(isnull(needpaysalary,0)-6500))  " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,0)=3 and  isnull(needpaysalary,0)>6500  and ISNULL(staffsocialsource,'')<>'南昌分公司'";
				
				strSql=strSql+" union all select pageType=7,strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),isnull(factpaysalary,0)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"' and  ISNULL(staffsocials,0)=0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=3  and isnull(factpaysalary,0)>0 and strCompId not in   ('301','302','303') " ;
				
				strSql=strSql+" union all  select pageType=8,strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue, " +
				"	 factpaysalary= convert(numeric(20,0),isnull(factpaysalary,0)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and computeday='"+strDateFrom+"' and len(ISNULL(staffbankaccountno,''))<10 and ISNULL(staffcurstate,2)=3   and isnull(factpaysalary,0)>0 	 and strCompId not in   ('301','302','303') " ;
				
				strSql=strSql+" union all  select pageType=9,strCompId,strCompName,a.staffno,a.staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,parentcodevalue," +
				"	 factpaysalary=convert(numeric(20,0),isnull(factpaysalary,0)) " +
				"	 from compute_staff_work_salary_byday a left join staffinfo on(staffinid=manageno) left join commoninfo on(infotype='YHKL' and parentcodekey=banktype),compchaininfo b " +
				"	 where a.strCompId=b.relationcomp and b.curcomp='"+strCompId+"' and  computeday='"+strDateFrom+"'  and  ISNULL(staffcurstate,0)=4  and isnull(factpaysalary,0)>0	and strCompId not in   ('301','302','303')  " +
				" 	 order by parentcodevalue,staffsocialsource,strCompId,strCompName ";
			
			AnlyResultSet<List<StaffWarkSalaryAnlanysis>> analysis = new AnlyResultSet<List<StaffWarkSalaryAnlanysis>>() {
				public List<StaffWarkSalaryAnlanysis> anlyResultSet(ResultSet rs) {
					List<StaffWarkSalaryAnlanysis> returnValue = new ArrayList();
					StaffWarkSalaryAnlanysis record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new StaffWarkSalaryAnlanysis();
							record.setPageType(CommonTool.FormatInteger(rs.getInt("pageType")));
							record.setStrCompId(CommonTool.FormatString(rs.getString("strCompId")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("strCompName")));
							record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setStaffpositionname(CommonTool.FormatString(rs.getString("staffpositionname")));
							record.setStaffsocialsource(CommonTool.FormatString(rs.getString("staffsocialsource")));
							record.setStaffpcid(CommonTool.FormatString(rs.getString("staffpcid")));
							record.setStaffbankaccountno(CommonTool.FormatString(rs.getString("staffbankaccountno")));
							record.setStrBackName(CommonTool.FormatString(rs.getString("parentcodevalue")));
							record.setFactpaysalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("factpaysalary")))));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<StaffWarkSalaryAnlanysis> ls= (List<StaffWarkSalaryAnlanysis>) this.amn_Dao.executeQuery_ex(strSql, analysis);
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
