package com.amani.model;

/**
 * OrdersEmpinfo entity. @author MyEclipse Persistence Tools
 */

public class OrdersEmpinfo implements java.io.Serializable {

	// Fields

	private String uuid;
	private String compid;
	private String billid;
	private String empno;
	private String empname;
	private String empinid;

	// Constructors

	/** default constructor */
	public OrdersEmpinfo() {
	}

	/** minimal constructor */
	public OrdersEmpinfo(String uuid, String compid, String billid) {
		this.uuid = uuid;
		this.compid = compid;
		this.billid = billid;
	}

	/** full constructor */
	public OrdersEmpinfo(String uuid, String compid, String billid,
			String empno, String empname, String empinid) {
		this.uuid = uuid;
		this.compid = compid;
		this.billid = billid;
		this.empno = empno;
		this.empname = empname;
		this.empinid = empinid;
	}

	// Property accessors

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCompid() {
		return this.compid;
	}

	public void setCompid(String compid) {
		this.compid = compid;
	}

	public String getBillid() {
		return this.billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public String getEmpno() {
		return this.empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getEmpname() {
		return this.empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getEmpinid() {
		return this.empinid;
	}

	public void setEmpinid(String empinid) {
		this.empinid = empinid;
	}

}