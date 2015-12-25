package com.amani.bean;

public class ReserveBean {
	private String itemno;
	private String itemname;
	private String staffno;
	private String staffname;
	private int stafftype;
	private double csseqno;
	private int showFlag;
	public int getShowFlag() {
		return showFlag;
	}
	public void setShowFlag(int showFlag) {
		this.showFlag = showFlag;
	}
	public String getItemno() {
		return itemno;
	}
	public void setItemno(String itemno) {
		this.itemno = itemno;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
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
	public int getStafftype() {
		return stafftype;
	}
	public void setStafftype(int stafftype) {
		this.stafftype = stafftype;
	}
	public double getCsseqno() {
		return csseqno;
	}
	public void setCsseqno(double csseqno) {
		this.csseqno = csseqno;
	}
}
