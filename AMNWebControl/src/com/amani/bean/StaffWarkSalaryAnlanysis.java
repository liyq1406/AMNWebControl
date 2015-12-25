package com.amani.bean;

import java.math.BigDecimal;

public class StaffWarkSalaryAnlanysis {
	private 	String 		strCompId;				//门店编号
	private 	String 		strCompName;			//门店名称
	private 	String 		staffno;				//员工编号
	private 	String 		staffinid;				//员工编号
	private 	String 		staffname;				//员工名称
	private 	String 		staffposition;			//员工职位
	private 	String 		staffpositionname;		//员工职位
	private 	String 		staffsocialsource;		//员工社保
	private 	String 		staffpcid;				//员工身份证
	private 	String		strBackName;			//银行卡名称
	private 	String 		staffbankaccountno;		//员工银行账号
	private 	BigDecimal 	stafftotalyeji;			//员工总业绩
	private 	BigDecimal 	staffshopyeji;			//员工门店业绩
	private 	BigDecimal 	staffbasesalary;		//员工基本工资
	private 	BigDecimal 	beatysubsidy;			//美容补贴
	private 	BigDecimal 	leaveldebit;			//离职扣款
	private		BigDecimal  basestaffamt;			//关爱基金
	private		BigDecimal  basestaffpayamt;		//关爱基金支付
	private 	BigDecimal 	staffsubsidy;			//员工保底补贴
	private     BigDecimal  storesubsidy;			//饭贴+门店补贴
	private 	BigDecimal	stafftargetreward;		//指标奖励
	private 	BigDecimal	yearreward;				//年终奖
	private 	BigDecimal	oldcustomerreward;		//老客补贴
	private 	BigDecimal 	staffdebit;				//罚款
	private 	BigDecimal 	staffdaikou;			//代扣
	private 	BigDecimal 	latdebit;				//吃迟到
	private 	BigDecimal	staffamtchange;			//差异调整
	private 	BigDecimal 	zerenjinback;			//责任金返还
	private 	BigDecimal 	zerenjincost;			//责任金扣款
	private 	BigDecimal 	staffcost;				//成本
	private 	BigDecimal 	staffreward;			//奖励
	private 	BigDecimal 	otherdebit;				//其他扣款
	private 	BigDecimal  costtotal;				//扣款小计
	private 	BigDecimal	needpaybsalary;			//应付工资小计
	private 	BigDecimal	totalneedpay;			//小计
	private 	BigDecimal 	staffsocials;			//社保
	private 	BigDecimal 	needpaysalary;			//应付工资
	private 	BigDecimal 	staydebit;				//住宿
	private 	BigDecimal 	studydebit;				//学习费用
	private 	BigDecimal 	salarydebit;			//扣税
	private 	BigDecimal 	sumcost;				//扣税
	private 	BigDecimal 	factpaysalary;			//应付工资
	private 	BigDecimal 	comppaysalary;			//实发工资
	private 	BigDecimal 	excelsalaryA;			//导出税后工资
	private 	BigDecimal 	excelsalaryB;			//导出补充工资
	private  	String 		staffmark;
	private 	int 		workdays;				//工作天数
	private 	int 		markflag;				//标记状态
	private 	int 		staffcurstate;			//员工在职状态
	private 	BigDecimal 	sumHasSocialSaraly;		//在职有社保工资总额
	private 	BigDecimal 	sumNoSocialSaraly;		//在职无社保工资总额
	private 	BigDecimal 	sumReadSalary;			//待发总额
	private 	BigDecimal 	sumLeavelSalary;		//离职总额
	
	private 	int			pageType;				//导出类型 
	
	
	public String getStrBackName() {
		return strBackName;
	}
	public void setStrBackName(String strBackName) {
		this.strBackName = strBackName;
	}
	public int getPageType() {
		return pageType;
	}
	public void setPageType(int pageType) {
		this.pageType = pageType;
	}
	public BigDecimal getSumHasSocialSaraly() {
		return sumHasSocialSaraly;
	}
	public void setSumHasSocialSaraly(BigDecimal sumHasSocialSaraly) {
		this.sumHasSocialSaraly = sumHasSocialSaraly;
	}
	public BigDecimal getSumNoSocialSaraly() {
		return sumNoSocialSaraly;
	}
	public void setSumNoSocialSaraly(BigDecimal sumNoSocialSaraly) {
		this.sumNoSocialSaraly = sumNoSocialSaraly;
	}
	public BigDecimal getSumReadSalary() {
		return sumReadSalary;
	}
	public void setSumReadSalary(BigDecimal sumReadSalary) {
		this.sumReadSalary = sumReadSalary;
	}
	public BigDecimal getSumLeavelSalary() {
		return sumLeavelSalary;
	}
	public void setSumLeavelSalary(BigDecimal sumLeavelSalary) {
		this.sumLeavelSalary = sumLeavelSalary;
	}
	public int getStaffcurstate() {
		return staffcurstate;
	}
	public void setStaffcurstate(int staffcurstate) {
		this.staffcurstate = staffcurstate;
	}
	public int getMarkflag() {
		return markflag;
	}
	public void setMarkflag(int markflag) {
		this.markflag = markflag;
	}
	public int getWorkdays() {
		return workdays;
	}
	public void setWorkdays(int workdays) {
		this.workdays = workdays;
	}
	public String getStaffmark() {
		return staffmark;
	}
	public void setStaffmark(String staffmark) {
		this.staffmark = staffmark;
	}
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}
	public String getStaffno() {
		return staffno;
	}
	public void setStaffno(String staffno) {
		this.staffno = staffno;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getStaffposition() {
		return staffposition;
	}
	public void setStaffposition(String staffposition) {
		this.staffposition = staffposition;
	}
	public String getStaffpcid() {
		return staffpcid;
	}
	public void setStaffpcid(String staffpcid) {
		this.staffpcid = staffpcid;
	}
	public String getStaffbankaccountno() {
		return staffbankaccountno;
	}
	public void setStaffbankaccountno(String staffbankaccountno) {
		this.staffbankaccountno = staffbankaccountno;
	}
	public BigDecimal getStafftotalyeji() {
		return stafftotalyeji;
	}
	public void setStafftotalyeji(BigDecimal stafftotalyeji) {
		this.stafftotalyeji = stafftotalyeji;
	}
	public BigDecimal getStaffshopyeji() {
		return staffshopyeji;
	}
	public void setStaffshopyeji(BigDecimal staffshopyeji) {
		this.staffshopyeji = staffshopyeji;
	}
	public BigDecimal getStaffbasesalary() {
		return staffbasesalary;
	}
	public void setStaffbasesalary(BigDecimal staffbasesalary) {
		this.staffbasesalary = staffbasesalary;
	}
	public BigDecimal getBeatysubsidy() {
		return beatysubsidy;
	}
	public void setBeatysubsidy(BigDecimal beatysubsidy) {
		this.beatysubsidy = beatysubsidy;
	}
	public BigDecimal getLeaveldebit() {
		return leaveldebit;
	}
	public void setLeaveldebit(BigDecimal leaveldebit) {
		this.leaveldebit = leaveldebit;
	}
	public BigDecimal getStaffsubsidy() {
		return staffsubsidy;
	}
	public void setStaffsubsidy(BigDecimal staffsubsidy) {
		this.staffsubsidy = staffsubsidy;
	}
	public BigDecimal getStaffdebit() {
		return staffdebit;
	}
	public void setStaffdebit(BigDecimal staffdebit) {
		this.staffdebit = staffdebit;
	}
	public BigDecimal getLatdebit() {
		return latdebit;
	}
	public void setLatdebit(BigDecimal latdebit) {
		this.latdebit = latdebit;
	}
	public BigDecimal getStaffcost() {
		return staffcost;
	}
	public void setStaffcost(BigDecimal staffcost) {
		this.staffcost = staffcost;
	}
	public BigDecimal getStaffreward() {
		return staffreward;
	}
	public void setStaffreward(BigDecimal staffreward) {
		this.staffreward = staffreward;
	}
	public BigDecimal getOtherdebit() {
		return otherdebit;
	}
	public void setOtherdebit(BigDecimal otherdebit) {
		this.otherdebit = otherdebit;
	}
	public BigDecimal getStaffsocials() {
		return staffsocials;
	}
	public void setStaffsocials(BigDecimal staffsocials) {
		this.staffsocials = staffsocials;
	}
	public BigDecimal getNeedpaysalary() {
		return needpaysalary;
	}
	public void setNeedpaysalary(BigDecimal needpaysalary) {
		this.needpaysalary = needpaysalary;
	}
	public BigDecimal getStaydebit() {
		return staydebit;
	}
	public void setStaydebit(BigDecimal staydebit) {
		this.staydebit = staydebit;
	}
	public BigDecimal getStudydebit() {
		return studydebit;
	}
	public void setStudydebit(BigDecimal studydebit) {
		this.studydebit = studydebit;
	}
	public BigDecimal getSalarydebit() {
		return salarydebit;
	}
	public void setSalarydebit(BigDecimal salarydebit) {
		this.salarydebit = salarydebit;
	}
	public BigDecimal getFactpaysalary() {
		return factpaysalary;
	}
	public void setFactpaysalary(BigDecimal factpaysalary) {
		this.factpaysalary = factpaysalary;
	}
	public BigDecimal getSumcost() {
		return sumcost;
	}
	public void setSumcost(BigDecimal sumcost) {
		this.sumcost = sumcost;
	}
	public BigDecimal getComppaysalary() {
		return comppaysalary;
	}
	public void setComppaysalary(BigDecimal comppaysalary) {
		this.comppaysalary = comppaysalary;
	}
	public String getStaffpositionname() {
		return staffpositionname;
	}
	public void setStaffpositionname(String staffpositionname) {
		this.staffpositionname = staffpositionname;
	}
	public String getStrCompName() {
		return strCompName;
	}
	public void setStrCompName(String strCompName) {
		this.strCompName = strCompName;
	}
	public String getStaffsocialsource() {
		return staffsocialsource;
	}
	public void setStaffsocialsource(String staffsocialsource) {
		this.staffsocialsource = staffsocialsource;
	}
	public BigDecimal getBasestaffamt() {
		return basestaffamt;
	}
	public void setBasestaffamt(BigDecimal basestaffamt) {
		this.basestaffamt = basestaffamt;
	}
	public BigDecimal getStaffamtchange() {
		return staffamtchange;
	}
	public void setStaffamtchange(BigDecimal staffamtchange) {
		this.staffamtchange = staffamtchange;
	}
	public BigDecimal getStafftargetreward() {
		return stafftargetreward;
	}
	public void setStafftargetreward(BigDecimal stafftargetreward) {
		this.stafftargetreward = stafftargetreward;
	}
	public BigDecimal getCosttotal() {
		return costtotal;
	}
	public void setCosttotal(BigDecimal costtotal) {
		this.costtotal = costtotal;
	}
	public BigDecimal getNeedpaybsalary() {
		return needpaybsalary;
	}
	public void setNeedpaybsalary(BigDecimal needpaybsalary) {
		this.needpaybsalary = needpaybsalary;
	}
	public BigDecimal getTotalneedpay() {
		return totalneedpay;
	}
	public void setTotalneedpay(BigDecimal totalneedpay) {
		this.totalneedpay = totalneedpay;
	}
	public BigDecimal getBasestaffpayamt() {
		return basestaffpayamt;
	}
	public void setBasestaffpayamt(BigDecimal basestaffpayamt) {
		this.basestaffpayamt = basestaffpayamt;
	}
	public String getStaffinid() {
		return staffinid;
	}
	public void setStaffinid(String staffinid) {
		this.staffinid = staffinid;
	}
	public BigDecimal getStoresubsidy() {
		return storesubsidy;
	}
	public void setStoresubsidy(BigDecimal storesubsidy) {
		this.storesubsidy = storesubsidy;
	}
	public BigDecimal getStaffdaikou() {
		return staffdaikou;
	}
	public void setStaffdaikou(BigDecimal staffdaikou) {
		this.staffdaikou = staffdaikou;
	}
	public BigDecimal getYearreward() {
		return yearreward;
	}
	public void setYearreward(BigDecimal yearreward) {
		this.yearreward = yearreward;
	}
	public BigDecimal getExcelsalaryA() {
		return excelsalaryA;
	}
	public void setExcelsalaryA(BigDecimal excelsalaryA) {
		this.excelsalaryA = excelsalaryA;
	}
	public BigDecimal getExcelsalaryB() {
		return excelsalaryB;
	}
	public void setExcelsalaryB(BigDecimal excelsalaryB) {
		this.excelsalaryB = excelsalaryB;
	}
	public BigDecimal getZerenjinback() {
		return zerenjinback;
	}
	public void setZerenjinback(BigDecimal zerenjinback) {
		this.zerenjinback = zerenjinback;
	}
	public BigDecimal getZerenjincost() {
		return zerenjincost;
	}
	public void setZerenjincost(BigDecimal zerenjincost) {
		this.zerenjincost = zerenjincost;
	}
	public BigDecimal getOldcustomerreward() {
		return oldcustomerreward;
	}
	public void setOldcustomerreward(BigDecimal oldcustomerreward) {
		this.oldcustomerreward = oldcustomerreward;
	}
}
