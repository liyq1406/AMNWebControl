package com.amani.model;

public class StaffinfoSimple {
	 
	private String compno;
    private String staffno;
	private String staffname;
	private String department; 
	private String position;
	private String manageno;
	private int    costCount;
	public int getCostCount() {
		return costCount;
	}
	public void setCostCount(int costCount) {
		this.costCount = costCount;
	}
	public String getManageno() {
		return manageno;
	}
	public void setManageno(String manageno) {
		this.manageno = manageno;
	}
	public String getCompno() {
		return compno;
	}
	public void setCompno(String compno) {
		this.compno = compno;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
