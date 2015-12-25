package com.amani.model;

/**
 * Intention entity. @author MyEclipse Persistence Tools
 */

public class Intention implements java.io.Serializable {

	// Fields

	private Integer intid;
	private String intcomplyno;
	private String intbillid;
	private String intdproject;
	private Integer intdstage;
	private String intdstarttime;
	private String intdendtime;
	private String intuser;
	private String intdata;
	private Integer intetionstate;

	// Constructors

	/** default constructor */
	public Intention() {
	}

	/** minimal constructor */
	public Intention(String intcomplyno, String intbillid) {
		this.intcomplyno = intcomplyno;
		this.intbillid = intbillid;
	}

	/** full constructor */
	public Intention(String intcomplyno, String intbillid, String intdproject,
			Integer intdstage, String intdstarttime, String intdendtime,
			String intuser, String intdata, Integer intetionstate) {
		this.intcomplyno = intcomplyno;
		this.intbillid = intbillid;
		this.intdproject = intdproject;
		this.intdstage = intdstage;
		this.intdstarttime = intdstarttime;
		this.intdendtime = intdendtime;
		this.intuser = intuser;
		this.intdata = intdata;
		this.intetionstate = intetionstate;
	}

	// Property accessors

	public Integer getIntid() {
		return this.intid;
	}

	public void setIntid(Integer intid) {
		this.intid = intid;
	}

	public String getIntcomplyno() {
		return this.intcomplyno;
	}

	public void setIntcomplyno(String intcomplyno) {
		this.intcomplyno = intcomplyno;
	}

	public String getIntbillid() {
		return this.intbillid;
	}

	public void setIntbillid(String intbillid) {
		this.intbillid = intbillid;
	}

	

	public String getIntdproject() {
		return intdproject;
	}

	public void setIntdproject(String intdproject) {
		this.intdproject = intdproject;
	}

	public Integer getIntdstage() {
		return this.intdstage;
	}

	public void setIntdstage(Integer intdstage) {
		this.intdstage = intdstage;
	}

	public String getIntdstarttime() {
		return this.intdstarttime;
	}

	public void setIntdstarttime(String intdstarttime) {
		this.intdstarttime = intdstarttime;
	}

	public String getIntdendtime() {
		return this.intdendtime;
	}

	public void setIntdendtime(String intdendtime) {
		this.intdendtime = intdendtime;
	}

	public String getIntuser() {
		return this.intuser;
	}

	public void setIntuser(String intuser) {
		this.intuser = intuser;
	}

	public String getIntdata() {
		return this.intdata;
	}

	public void setIntdata(String intdata) {
		this.intdata = intdata;
	}

	public Integer getIntetionstate() {
		return this.intetionstate;
	}

	public void setIntetionstate(Integer intetionstate) {
		this.intetionstate = intetionstate;
	}

}