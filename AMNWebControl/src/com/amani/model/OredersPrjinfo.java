package com.amani.model;

/**
 * OredersPrjinfo entity. @author MyEclipse Persistence Tools
 */

public class OredersPrjinfo implements java.io.Serializable {

	// Fields

	private String uuid;
	private String compid;
	private String billid;
	private String prjno;
	private String prjname;

	// Constructors

	/** default constructor */
	public OredersPrjinfo() {
	}

	/** minimal constructor */
	public OredersPrjinfo(String uuid, String compid, String billid) {
		this.uuid = uuid;
		this.compid = compid;
		this.billid = billid;
	}

	/** full constructor */
	public OredersPrjinfo(String uuid, String compid, String billid,
			String prjno, String prjname) {
		this.uuid = uuid;
		this.compid = compid;
		this.billid = billid;
		this.prjno = prjno;
		this.prjname = prjname;
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

	public String getPrjno() {
		return this.prjno;
	}

	public void setPrjno(String prjno) {
		this.prjno = prjno;
	}

	public String getPrjname() {
		return this.prjname;
	}

	public void setPrjname(String prjname) {
		this.prjname = prjname;
	}

}