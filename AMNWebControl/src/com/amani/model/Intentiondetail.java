package com.amani.model;

/**
 * Intentiondetail entity. @author MyEclipse Persistence Tools
 */

public class Intentiondetail implements java.io.Serializable {

	// Fields

	private Integer intdid;
	private String intdcomplyno;
	private String intdcomplyname;
	private Integer intdproject;
	private Integer intdstage;
	private String intdstarttime;
	private String intdendtime;
	private String intdbillid;
	private String intdwaite;
	private String intstuno;
	private String incardno;
	private String instaffno;
	private String instaffname;
	private String intposition;
	private String intbirthday;
	private Integer intdscore;
	private Integer intpositions;
	private String intdproname;
	private String intdpunish;
	private String intdremark;

	// Constructors

	/** default constructor */
	public Intentiondetail() {
	}

	/** minimal constructor */
	public Intentiondetail(String intdcomplyno, String intdbillid) {
		this.intdcomplyno = intdcomplyno;
		this.intdbillid = intdbillid;
	}

	/** full constructor */
	public Intentiondetail(String intdcomplyno, String intdbillid,
			String intdwaite, String intstuno, String incardno,
			String instaffno, String instaffname, String intposition,
			String intbirthday, Integer intdscore, Integer intpositions,
			String intdproname, String intdpunish, String intdremark) {
		this.intdcomplyno = intdcomplyno;
		this.intdbillid = intdbillid;
		this.intdwaite = intdwaite;
		this.intstuno = intstuno;
		this.incardno = incardno;
		this.instaffno = instaffno;
		this.instaffname = instaffname;
		this.intposition = intposition;
		this.intbirthday = intbirthday;
		this.intdscore = intdscore;
		this.intpositions = intpositions;
		this.intdproname = intdproname;
		this.intdpunish = intdpunish;
		this.intdremark = intdremark;
	}

	// Property accessors

	public Integer getIntdid() {
		return this.intdid;
	}

	public void setIntdid(Integer intdid) {
		this.intdid = intdid;
	}

	public String getIntdcomplyno() {
		return this.intdcomplyno;
	}

	public void setIntdcomplyno(String intdcomplyno) {
		this.intdcomplyno = intdcomplyno;
	}

	public String getIntdbillid() {
		return this.intdbillid;
	}

	public void setIntdbillid(String intdbillid) {
		this.intdbillid = intdbillid;
	}

	public String getIntdwaite() {
		return this.intdwaite;
	}

	public void setIntdwaite(String intdwaite) {
		this.intdwaite = intdwaite;
	}

	public String getIntstuno() {
		return this.intstuno;
	}

	public void setIntstuno(String intstuno) {
		this.intstuno = intstuno;
	}

	public String getIncardno() {
		return this.incardno;
	}

	public void setIncardno(String incardno) {
		this.incardno = incardno;
	}

	public String getInstaffno() {
		return this.instaffno;
	}

	public void setInstaffno(String instaffno) {
		this.instaffno = instaffno;
	}

	public String getInstaffname() {
		return this.instaffname;
	}

	public void setInstaffname(String instaffname) {
		this.instaffname = instaffname;
	}

	public String getIntposition() {
		return this.intposition;
	}

	public void setIntposition(String intposition) {
		this.intposition = intposition;
	}

	public String getIntbirthday() {
		return this.intbirthday;
	}

	public void setIntbirthday(String intbirthday) {
		this.intbirthday = intbirthday;
	}

	public Integer getIntdscore() {
		return this.intdscore;
	}

	public void setIntdscore(Integer intdscore) {
		this.intdscore = intdscore;
	}

	public Integer getIntpositions() {
		return this.intpositions;
	}

	public void setIntpositions(Integer intpositions) {
		this.intpositions = intpositions;
	}

	public String getIntdproname() {
		return this.intdproname;
	}

	public void setIntdproname(String intdproname) {
		this.intdproname = intdproname;
	}

	public String getIntdpunish() {
		return this.intdpunish;
	}

	public void setIntdpunish(String intdpunish) {
		this.intdpunish = intdpunish;
	}

	public String getIntdremark() {
		return this.intdremark;
	}

	public void setIntdremark(String intdremark) {
		this.intdremark = intdremark;
	}

	public String getIntdcomplyname() {
		return intdcomplyname;
	}

	public void setIntdcomplyname(String intdcomplyname) {
		this.intdcomplyname = intdcomplyname;
	}

	public Integer getIntdproject() {
		return intdproject;
	}

	public void setIntdproject(Integer intdproject) {
		this.intdproject = intdproject;
	}

	public Integer getIntdstage() {
		return intdstage;
	}

	public void setIntdstage(Integer intdstage) {
		this.intdstage = intdstage;
	}

	public String getIntdstarttime() {
		return intdstarttime;
	}

	public void setIntdstarttime(String intdstarttime) {
		this.intdstarttime = intdstarttime;
	}

	public String getIntdendtime() {
		return intdendtime;
	}

	public void setIntdendtime(String intdendtime) {
		this.intdendtime = intdendtime;
	}

}