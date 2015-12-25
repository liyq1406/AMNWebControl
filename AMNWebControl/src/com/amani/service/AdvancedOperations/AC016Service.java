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
public class AC016Service extends AMN_ReportService{
	
	public List<StaffWarkSalaryAnlanysis> loadDateSetByCompId(String strCompId,String strDateFrom,String strDateTo,int socialsourceflag)
	{
		try
		{
			String strSql="";
			if(Integer.parseInt(CommonTool.getCurrDate())<Integer.parseInt(CommonTool.datePlusDay(CommonTool.setDateMask(strDateFrom),51)))
			{
					strSql= " exec upg_compute_staff_salary_byday '"+strCompId+"','"+CommonTool.setDateMask(strDateFrom)+"','"+CommonTool.setDateMask(strDateTo)+"' ";
					this.amn_Dao.executeSql(strSql);
					strSql=" insert sysoperationlog(userid,program,operation,operationdate,operationtime,compid,keyvalue1,keyvalue2,keyvalue3)" +
			 		" values( '"+CommonTool.getLoginInfo("USERID")+"','AC016','S','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"','"+CommonTool.getLoginInfo("COMPID")+"','','','工资汇总重新结算')";
					this.amn_Dao.executeSql(strSql);	
			}
			strSql=" select computeday,strCompId,strCompName,staffno,staffinid,staffname,staffposition,staffpositionname,staffpcid,staffbankaccountno,workdays,computedays, " +
					"   stafftotalyeji,staffshopyeji,staffbasesalary,beatysubsidy,leaveldebit,staffsubsidy,staffdebit,staffdaikou,latdebit,staffcost, staffreward,otherdebit,staffsocials, " +
					"   needpaysalary,staydebit,studydebit,salarydebit,factpaysalary,staffmark ,   basestaffamt,staffsocialsource,staffamtchange,stafftargetreward,basestaffpayamt,storesubsidy," +
					"  yearreward ,markflag,staffcurstate=isnull(staffcurstate,2),zerenjinback,zerenjincost,oldcustomerreward " +
					"   from compute_staff_work_salary_byday,compchaininfo  where  strCompId=relationcomp and computeday='"+CommonTool.setDateMask(strDateFrom)+"' and  curcomp ='"+strCompId+"' ";
			if(socialsourceflag==1)
			{
				strSql=strSql+" and isnull(staffsocials,0)>0 and isnull(staffsocialsource,'')<>'南昌分公司' and isnull(staffsocialsource,'')='维沙' order by  staffsocialsource ";
			}
			else if(socialsourceflag==3)
			{
				strSql=strSql+" and isnull(staffsocials,0)>0 and isnull(staffsocialsource,'')<>'南昌分公司' and isnull(staffsocialsource,'')<>'维沙' order by  staffsocialsource ";
			}
			else if(socialsourceflag==2)
			{
				strSql=strSql+" and ( isnull(staffsocials,0)=0 or isnull(staffsocialsource,'')='南昌分公司') order by strCompId ";
			}
			AnlyResultSet<List<StaffWarkSalaryAnlanysis>> analysis = new AnlyResultSet<List<StaffWarkSalaryAnlanysis>>() {
				public List<StaffWarkSalaryAnlanysis> anlyResultSet(ResultSet rs) {
					List<StaffWarkSalaryAnlanysis> returnValue = new ArrayList();
					StaffWarkSalaryAnlanysis record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new StaffWarkSalaryAnlanysis();
							record.setStrCompId(CommonTool.FormatString(rs.getString("strCompId")));
							record.setStaffinid(CommonTool.FormatString(rs.getString("staffinid")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("strCompName")));
							record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setStaffposition(CommonTool.FormatString(rs.getString("staffposition")));
							record.setStaffsocialsource(CommonTool.FormatString(rs.getString("staffsocialsource")));
							record.setStaffpositionname(CommonTool.FormatString(rs.getString("staffpositionname")));
							record.setStaffpcid(CommonTool.FormatString(rs.getString("staffpcid")));
							record.setStaffbankaccountno(CommonTool.FormatString(rs.getString("staffbankaccountno")));
							//----------------应付工资Start 
							record.setStafftotalyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("stafftotalyeji")))));
							record.setStaffshopyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffshopyeji")))));
							record.setStaffbasesalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffbasesalary")))));
							record.setBeatysubsidy(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beatysubsidy")))));
							record.setOldcustomerreward(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("oldcustomerreward")))));
							//员工指标奖励
							record.setStafftargetreward(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("stafftargetreward")))));
							//员工业绩调整
							record.setStaffamtchange(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffamtchange")))));
							//员工补贴
							record.setStaffsubsidy(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffsubsidy")))));
							//门店补贴
							
							record.setStoresubsidy(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("storesubsidy")))));
							
							//员工奖励
							record.setStaffreward(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffreward")))));
							record.setYearreward(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("yearreward")))));
							
							//----------------应付工资end
							//应付工资（扣税）
							record.setNeedpaybsalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("stafftotalyeji"))+
									 CommonTool.FormatDouble(rs.getDouble("staffshopyeji"))+
									 CommonTool.FormatDouble(rs.getDouble("staffbasesalary"))+
									 CommonTool.FormatDouble(rs.getDouble("beatysubsidy"))+
									 CommonTool.FormatDouble(rs.getDouble("oldcustomerreward"))+
									 CommonTool.FormatDouble(rs.getDouble("stafftargetreward"))+
									 CommonTool.FormatDouble(rs.getDouble("staffamtchange"))+
									 CommonTool.FormatDouble(rs.getDouble("staffsubsidy"))+
									 CommonTool.FormatDouble(rs.getDouble("storesubsidy"))+
									 CommonTool.FormatDouble(rs.getDouble("staffreward"))
									 )));
							//关爱基金
							record.setBasestaffpayamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("basestaffpayamt")))));
							//小计
							record.setTotalneedpay(CommonTool.FormatBigDecimal(new BigDecimal(record.getNeedpaybsalary().doubleValue()+CommonTool.FormatDouble(rs.getDouble("basestaffpayamt")))));
							
							//关爱基金扣款 Start 
							//迟到
							record.setLatdebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("latdebit")))));
							//离职扣款
							record.setLeaveldebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("leaveldebit")))));
							//关爱基金
							record.setBasestaffamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("basestaffamt")))));
							//罚款
							record.setStaffdebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffdebit")))));
							//代扣
							record.setStaffdaikou(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffdaikou")))));
							//责任金抵押
							record.setZerenjincost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("zerenjincost")))));
							//责任金返还
							record.setZerenjinback(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("zerenjinback")))));
							
							//扣税
							record.setSalarydebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("salarydebit")))));
							
							
							
							record.setCosttotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("latdebit"))+
									 CommonTool.FormatDouble(rs.getDouble("leaveldebit"))+
									 CommonTool.FormatDouble(rs.getDouble("basestaffamt"))+
									 CommonTool.FormatDouble(rs.getDouble("otherdebit"))+
									 CommonTool.FormatDouble(rs.getDouble("staffdebit")))));
							//请假扣款
							record.setOtherdebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("otherdebit")))));
							//社保
							record.setStaffsocials(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffsocials")))));
							//税前工资
							record.setNeedpaysalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("needpaysalary")))));
							//扣成本
							record.setStaffcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffcost")))));
							//住宿费
							record.setStaydebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staydebit")))));
							//学习费
							record.setStudydebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("studydebit")))));
							
							//税后扣款小计
							record.setSumcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffcost"))+
																						 CommonTool.FormatDouble(rs.getDouble("staydebit"))-
																						 CommonTool.FormatDouble(rs.getDouble("zerenjinback"))+
																						 CommonTool.FormatDouble(rs.getDouble("zerenjincost"))+
																						 CommonTool.FormatDouble(rs.getDouble("studydebit")))));
							record.setFactpaysalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("factpaysalary")))));
							record.setStaffmark(CommonTool.FormatString(rs.getString("staffmark")));
							record.setWorkdays(CommonTool.FormatInteger(rs.getInt("workdays")));
							record.setMarkflag(CommonTool.FormatInteger(rs.getInt("markflag")));
							record.setStaffcurstate(CommonTool.FormatInteger(rs.getInt("staffcurstate")));
							
							if(record.getNeedpaysalary().doubleValue()>6500 && record.getStaffsocials().doubleValue()>0)
							{
								
								record.setExcelsalaryB(new BigDecimal(record.getNeedpaysalary().doubleValue()-6500));
								record.setExcelsalaryA(new BigDecimal(record.getFactpaysalary().doubleValue()-(record.getNeedpaysalary().doubleValue()-6500)));
								
							}
							else
							{
								record.setExcelsalaryA(record.getFactpaysalary());
								record.setExcelsalaryB(new BigDecimal(0));
							}
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

	
	
	public List<StaffWarkSalaryAnlanysis> loadDateSetByCompIdNoCompute(String strCompId,String strDateFrom,String strDateTo,int socialsourceflag)
	{
		try
		{
			String strSql=" select computeday,strCompId,strCompName,staffno,staffinid,staffname,staffposition,staffpositionname,staffpcid,staffbankaccountno,workdays,computedays, " +
					"   stafftotalyeji,staffshopyeji,staffbasesalary,beatysubsidy,leaveldebit,staffsubsidy,staffdebit,staffdaikou,latdebit,staffcost, staffreward,otherdebit,staffsocials, " +
					"   needpaysalary,staydebit,studydebit,salarydebit,factpaysalary,staffmark ,   basestaffamt,staffsocialsource,staffamtchange,stafftargetreward,basestaffpayamt,storesubsidy,yearreward ,markflag,staffcurstate=isnull(staffcurstate,2),zerenjinback,zerenjincost,oldcustomerreward " +
					"   from compute_staff_work_salary_byday,compchaininfo  " +
					" where  strCompId=relationcomp and computeday='"+CommonTool.setDateMask(strDateFrom)+"' and  curcomp ='"+strCompId+"' ";
			if(socialsourceflag==1)
			{
				strSql=strSql+" and isnull(staffsocials,0)>0 and isnull(staffsocialsource,'')<>'南昌分公司' and isnull(staffsocialsource,'')='维沙' order by  staffsocialsource ";
			}
			else if(socialsourceflag==3)
			{
				strSql=strSql+" and isnull(staffsocials,0)>0 and isnull(staffsocialsource,'')<>'南昌分公司' and isnull(staffsocialsource,'')<>'维沙' order by  staffsocialsource ";
			}
			else if(socialsourceflag==2)
			{
				strSql=strSql+" and ( isnull(staffsocials,0)=0 or isnull(staffsocialsource,'')='南昌分公司') order by strCompId ";
			}
			AnlyResultSet<List<StaffWarkSalaryAnlanysis>> analysis = new AnlyResultSet<List<StaffWarkSalaryAnlanysis>>() {
				public List<StaffWarkSalaryAnlanysis> anlyResultSet(ResultSet rs) {
					List<StaffWarkSalaryAnlanysis> returnValue = new ArrayList();
					StaffWarkSalaryAnlanysis record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new StaffWarkSalaryAnlanysis();
							record.setStrCompId(CommonTool.FormatString(rs.getString("strCompId")));
							record.setStaffinid(CommonTool.FormatString(rs.getString("staffinid")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("strCompName")));
							record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
							record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							record.setStaffposition(CommonTool.FormatString(rs.getString("staffposition")));
							record.setStaffsocialsource(CommonTool.FormatString(rs.getString("staffsocialsource")));
							record.setStaffpositionname(CommonTool.FormatString(rs.getString("staffpositionname")));
							record.setStaffpcid(CommonTool.FormatString(rs.getString("staffpcid")));
							record.setStaffbankaccountno(CommonTool.FormatString(rs.getString("staffbankaccountno")));
							//----------------应付工资Start 
							record.setStafftotalyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("stafftotalyeji")))));
							record.setStaffshopyeji(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffshopyeji")))));
							record.setStaffbasesalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffbasesalary")))));
							record.setBeatysubsidy(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("beatysubsidy")))));
							record.setOldcustomerreward(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("oldcustomerreward")))));
							//员工指标奖励
							record.setStafftargetreward(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("stafftargetreward")))));
							//员工业绩调整
							record.setStaffamtchange(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffamtchange")))));
							//员工补贴
							record.setStaffsubsidy(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffsubsidy")))));
							//门店补贴
							
							record.setStoresubsidy(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("storesubsidy")))));
							
							//员工奖励
							record.setStaffreward(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffreward")))));
							record.setYearreward(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("yearreward")))));
							
							//----------------应付工资end
							//应付工资（扣税）
							record.setNeedpaybsalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("stafftotalyeji"))+
									 CommonTool.FormatDouble(rs.getDouble("staffshopyeji"))+
									 CommonTool.FormatDouble(rs.getDouble("staffbasesalary"))+
									 CommonTool.FormatDouble(rs.getDouble("beatysubsidy"))+
									 CommonTool.FormatDouble(rs.getDouble("oldcustomerreward"))+
									 CommonTool.FormatDouble(rs.getDouble("stafftargetreward"))+
									 CommonTool.FormatDouble(rs.getDouble("staffamtchange"))+
									 CommonTool.FormatDouble(rs.getDouble("staffsubsidy"))+
									 CommonTool.FormatDouble(rs.getDouble("storesubsidy"))+
									 CommonTool.FormatDouble(rs.getDouble("staffreward"))
									 )));
							//关爱基金
							record.setBasestaffpayamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("basestaffpayamt")))));
							//小计
							record.setTotalneedpay(CommonTool.FormatBigDecimal(new BigDecimal(record.getNeedpaybsalary().doubleValue()+CommonTool.FormatDouble(rs.getDouble("basestaffpayamt")))));
							
							//关爱基金扣款 Start 
							//迟到
							record.setLatdebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("latdebit")))));
							//离职扣款
							record.setLeaveldebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("leaveldebit")))));
							//关爱基金
							record.setBasestaffamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("basestaffamt")))));
							//罚款
							record.setStaffdebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffdebit")))));
							//代扣
							record.setStaffdaikou(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffdaikou")))));
							//责任金抵押
							record.setZerenjincost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("zerenjincost")))));
							//责任金返还
							record.setZerenjinback(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("zerenjinback")))));
							
							//扣税
							record.setSalarydebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("salarydebit")))));
							
							
							
							record.setCosttotal(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("latdebit"))+
									 CommonTool.FormatDouble(rs.getDouble("leaveldebit"))+
									 CommonTool.FormatDouble(rs.getDouble("basestaffamt"))+
									 CommonTool.FormatDouble(rs.getDouble("otherdebit"))+
									 CommonTool.FormatDouble(rs.getDouble("staffdebit")))));
							//请假扣款
							record.setOtherdebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("otherdebit")))));
							//社保
							record.setStaffsocials(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffsocials")))));
							//税前工资
							record.setNeedpaysalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("needpaysalary")))));
							//扣成本
							record.setStaffcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffcost")))));
							//住宿费
							record.setStaydebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staydebit")))));
							//学习费
							record.setStudydebit(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("studydebit")))));
							
							//税后扣款小计
							record.setSumcost(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("staffcost"))+
																						 CommonTool.FormatDouble(rs.getDouble("staydebit"))-
																						 CommonTool.FormatDouble(rs.getDouble("zerenjinback"))+
																						 CommonTool.FormatDouble(rs.getDouble("zerenjincost"))+
																						 CommonTool.FormatDouble(rs.getDouble("studydebit")))));
							record.setFactpaysalary(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("factpaysalary")))));
							record.setStaffmark(CommonTool.FormatString(rs.getString("staffmark")));
							record.setWorkdays(CommonTool.FormatInteger(rs.getInt("workdays")));
							record.setMarkflag(CommonTool.FormatInteger(rs.getInt("markflag")));
							record.setStaffcurstate(CommonTool.FormatInteger(rs.getInt("staffcurstate")));
							
							if(record.getNeedpaysalary().doubleValue()>6500 && record.getStaffsocials().doubleValue()>0)
							{
								
								record.setExcelsalaryB(new BigDecimal(record.getNeedpaysalary().doubleValue()-6500));
								record.setExcelsalaryA(new BigDecimal(record.getFactpaysalary().doubleValue()-(record.getNeedpaysalary().doubleValue()-6500)));
								
							}
							else
							{
								record.setExcelsalaryA(record.getFactpaysalary());
								record.setExcelsalaryB(new BigDecimal(0));
							}
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
			if(ls==null)
			{
				ls=new ArrayList();
			}
			StaffWarkSalaryAnlanysis curRecord=new StaffWarkSalaryAnlanysis();
			curRecord.setStrCompId(strCompId);
			curRecord.setStaffname("运营部工资");
			curRecord.setStaffcurstate(4);
			curRecord.setStaffbankaccountno("");
			curRecord.setStaffpcid("");
			curRecord.setStaffno("");
			curRecord.setStaffposition("");
			curRecord.setStaffpositionname("运营部");
			curRecord.setExcelsalaryA(new BigDecimal(1000));
			curRecord.setNeedpaysalary(new BigDecimal(1000));
			curRecord.setFactpaysalary(new BigDecimal(1000));
			ls.add(curRecord);
			curRecord=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Staffabsenceinfo>  loadAbsenceInfos(String strCompId,String strManagerno,String strFromDate,String strToDate)
	{
		try
		{
			//String strLastMonthDay=CommonTool.datePlusDay(CommonTool.setDateMask(strDate).substring(0,6)+"01",-31);
			String strSql=" select manageno,compid,empid,absencedate from staffabsenceinfo where compid='"+strCompId+"' and  manageno='"+strManagerno+"' and absencedate between '"+strFromDate+"' and '"+strToDate+"'  ";
			AnlyResultSet<List<Staffabsenceinfo>> analysis = new AnlyResultSet<List<Staffabsenceinfo>>()
			{
				public List<Staffabsenceinfo> anlyResultSet(ResultSet rs)
				{
					List<Staffabsenceinfo> returnValue = new ArrayList();
					Staffabsenceinfo bean=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							bean=new Staffabsenceinfo();
							bean.setManageno(rs.getString("manageno"));
							bean.setCompid(rs.getString("compid"));
							bean.setEmpid(rs.getString("empid"));
							bean.setAbsencedate(CommonTool.getDateMask(rs.getString("absencedate")));
							returnValue.add(bean);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					return returnValue;
				}
			};
			List<Staffabsenceinfo> ls= (List<Staffabsenceinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean postStaffabsenceinfo(String strCompId,String strStaffNo,String strInId,String strDate)
	{
		try
		{
			String strSql=" insert staffabsenceinfo(compid,empid,manageno,absencedate)" +
					" values('"+strCompId+"','"+strStaffNo+"','"+strInId+"','"+CommonTool.setDateMask(strDate)+"') ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean postStaffMarkInfo(String strCompId,String strInId,String strMonth)
	{
		try
		{
			String strSql=" insert staffasarymark(compid,manageno,absencedate)" +
					" values('"+strCompId+"','"+strInId+"','"+strMonth+"') ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean deleteSalaryMark(String strCompId,String strInId,String strMonth)
	{
		try
		{
			String strSql=" delete  staffasarymark where compid='"+strCompId+"' and manageno='"+strInId+"' and absencedate='"+strMonth+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteStaffAbsence(String strCompId,String strInId,String strDate)
	{
		try
		{
			String strSql=" delete  staffabsenceinfo where compid='"+strCompId+"' and manageno='"+strInId+"' and absencedate='"+CommonTool.setDateMask(strDate)+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean postAllStaffabsenceinfo(String strCompId,String strStaffNo,String strInId,String[] lsDate)
	{
		try
		{
			String strSql="";
			for(int i=0;i<lsDate.length;i++)
			{
				strSql=strSql+" delete staffabsenceinfo where compid='"+strCompId+"' and manageno='"+strInId+"' and  absencedate='"+CommonTool.setDateMask(lsDate[i])+"' ";
				strSql=strSql+" insert staffabsenceinfo(compid,empid,manageno,absencedate)" +
				" values('"+strCompId+"','"+strStaffNo+"','"+strInId+"','"+CommonTool.setDateMask(lsDate[i])+"') ";
			}
			if(!strSql.equals(""))
			{
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
	
	public boolean validateStaffAbsence(String strInId,String strDate)
	{
		String strSql="select 1 from staffabsenceinfo where manageno='"+strInId+"' and absencedate='"+CommonTool.setDateMask(strDate)+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = false;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue =  true;
					}
					else
					{
						returnValue =  false;
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
		boolean flag= (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return flag;
	}

	public boolean postStaffDispatch(String oldcompno,String oldstaffno,String oldstaffinid,String olddepid,String oldpostion,
			int oldyjtype,double oldyjrate,double oldyjamt,String dispatchcompno,String dispatchdate,String tdispatchdate)
	{
		try
		{
			String strSql="insert staffinfodispatch(manageno,oldcompid,oldempid,olddepid,oldpostion,oldyjtype,oldyjrate,oldyjamt,newcompid,effectivedate,teffectivedate) " +
					" values('"+oldstaffinid+"','"+oldcompno+"','"+oldstaffno+"','"+olddepid+"','"+oldpostion+"','"+oldyjtype+"'," +
					" "+oldyjrate+","+oldyjamt+",'"+dispatchcompno+"','"+CommonTool.setDateMask(dispatchdate)+"','"+CommonTool.setDateMask(tdispatchdate)+"') ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean validateStaffmark(String strCompId,String strInId,String strMonth)
	{
		String strSql="select 1 from staffasarymark where compid='"+strCompId+"' and  manageno='"+strInId+"' and absencedate='"+strMonth+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
		{
			public Boolean anlyResultSet(ResultSet rs)
			{
				boolean returnValue = false;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue =  true;
					}
					else
					{
						returnValue =  false;
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
		boolean flag= (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return flag;
	}
	
	
	public boolean postStaffSalayrUp(String oldcompno,String oldstaffno,String oldstaffinid,String olddepid,String oldpostion,
			int oldyjtype,double oldyjrate,double oldyjamt,double oldSalary,
			int newyjtype,double newyjrate,double newyjamt,double newSalary,String dispatchdate)
	{
		try
		{
			
			Staffchangeinfo staffchangeinfo=new Staffchangeinfo();
			staffchangeinfo.setId(new StaffchangeinfoId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"staffchangeinfo", "changebillid", "SP011"),5));
			staffchangeinfo.setChangestaffno(oldstaffno);
			staffchangeinfo.setAppchangecompid(oldcompno);
			staffchangeinfo.setStaffpcid("");
			staffchangeinfo.setStaffphone("");
			staffchangeinfo.setStaffmangerno(oldstaffinid);
			staffchangeinfo.setChangedate(CommonTool.getCurrDate());
			staffchangeinfo.setValidatestartdate(CommonTool.setDateMask(dispatchdate));
			staffchangeinfo.setValidateenddate(CommonTool.setDateMask(dispatchdate));
			staffchangeinfo.setBeforedepartment(olddepid);
			staffchangeinfo.setBeforepostation(oldpostion);
			staffchangeinfo.setBeforesalary(CommonTool.FormatBigDecimal(new BigDecimal(oldSalary)));
			staffchangeinfo.setBeforeyejitype(oldyjtype+"");
			staffchangeinfo.setBeforeyejirate(CommonTool.FormatBigDecimalT(new BigDecimal(oldyjrate)));
			staffchangeinfo.setBeforeyejiamt(CommonTool.FormatBigDecimalT(new BigDecimal(oldyjamt)));
			
			staffchangeinfo.setAftercompid(oldcompno);
			staffchangeinfo.setAfterstaffno(oldstaffno);
			staffchangeinfo.setAfterdepartment(olddepid);
			staffchangeinfo.setAfterpostation(oldpostion);
			staffchangeinfo.setAftersalary(CommonTool.FormatBigDecimal(new BigDecimal(newSalary)));
			staffchangeinfo.setAfteryejitype(newyjtype+"");
			staffchangeinfo.setAfteryejirate(CommonTool.FormatBigDecimalT(new BigDecimal(newyjrate)));
			staffchangeinfo.setAfteryejiamt(CommonTool.FormatBigDecimalT(new BigDecimal(newyjamt)));
			this.amn_Dao.save(staffchangeinfo);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	

	
}
