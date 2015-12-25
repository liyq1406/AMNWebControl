package com.amani.model;

import java.math.BigDecimal;

public class ManagerShare {
	private String manageno;
	private String compid;
	private String compname;
	private String empid;
	private BigDecimal sharesalary;
	public String getManageno() {
		return manageno;
	}
	public void setManageno(String manageno) {
		this.manageno = manageno;
	}
	public String getCompid() {
		return compid;
	}
	public void setCompid(String compid) {
		this.compid = compid;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public BigDecimal getSharesalary() {
		return sharesalary;
	}
	public void setSharesalary(BigDecimal sharesalary) {
		this.sharesalary = sharesalary;
	}
	public String getCompname() {
		return compname;
	}
	public void setCompname(String compname) {
		this.compname = compname;
	}
}
