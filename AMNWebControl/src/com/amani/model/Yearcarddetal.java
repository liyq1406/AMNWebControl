package com.amani.model;

/**
 * Yearcarddetal entity. @author MyEclipse Persistence Tools
 */

public class Yearcarddetal implements java.io.Serializable {

	// Fields

	private String iteminid;
	private String compid;
	private String phone;
	private String packno;
	private String itemno;
	private Integer num;
	private Double amt;
	private Integer synum;
	private Double syamt;
	private String validate;
	private Double monthnum;
	private String itemname;
	private String stopdate;
	private Integer itemstate;
	private Integer istop;
	private String firstempno;
	private String firstinid;
	private String sendempno;
	private String sendinid;
	private Double firstperf;
	private Double sendperf;
	private String threeempno;
	private String threeinid;
	private String fourempno;
	private String fourinid;
	private Double threeperf;
	private Double fourperf;
	private String mkdate;
	
	public String getThreeempno() {
		return threeempno;
	}

	public void setThreeempno(String threeempno) {
		this.threeempno = threeempno;
	}

	public String getThreeinid() {
		return threeinid;
	}

	public void setThreeinid(String threeinid) {
		this.threeinid = threeinid;
	}

	public String getFourempno() {
		return fourempno;
	}

	public void setFourempno(String fourempno) {
		this.fourempno = fourempno;
	}

	public String getFourinid() {
		return fourinid;
	}

	public void setFourinid(String fourinid) {
		this.fourinid = fourinid;
	}

	public Double getThreeperf() {
		return threeperf;
	}

	public void setThreeperf(Double threeperf) {
		this.threeperf = threeperf;
	}

	public Double getFourperf() {
		return fourperf;
	}

	public void setFourperf(Double fourperf) {
		this.fourperf = fourperf;
	}

	public Double getFirstperf() {
		return firstperf;
	}

	public void setFirstperf(Double firstperf) {
		this.firstperf = firstperf;
	}

	public Double getSendperf() {
		return sendperf;
	}

	public void setSendperf(Double sendperf) {
		this.sendperf = sendperf;
	}

	public String getFirstempno() {
		return firstempno;
	}

	public void setFirstempno(String firstempno) {
		this.firstempno = firstempno;
	}

	public String getFirstinid() {
		return firstinid;
	}

	public void setFirstinid(String firstinid) {
		this.firstinid = firstinid;
	}


	public String getSendempno() {
		return sendempno;
	}

	public void setSendempno(String sendempno) {
		this.sendempno = sendempno;
	}

	public String getSendinid() {
		return sendinid;
	}

	public void setSendinid(String sendinid) {
		this.sendinid = sendinid;
	}

	public String getStopdate() {
		return stopdate;
	}

	public void setStopdate(String stopdate) {
		this.stopdate = stopdate;
	}

	public Integer getItemstate() {
		return itemstate;
	}

	public void setItemstate(Integer itemstate) {
		this.itemstate = itemstate;
	}

	public Integer getIstop() {
		return istop;
	}

	public void setIstop(Integer istop) {
		this.istop = istop;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public Double getMonthnum() {
		return monthnum;
	}

	public void setMonthnum(Double monthnum) {
		this.monthnum = monthnum;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public Integer getSynum() {
		return synum;
	}

	public void setSynum(Integer synum) {
		this.synum = synum;
	}

	public Double getSyamt() {
		return syamt;
	}

	public void setSyamt(Double syamt) {
		this.syamt = syamt;
	}

	private String remarks;

	// Constructors

	/** default constructor */
	public Yearcarddetal() {
	}

	/** minimal constructor */
	public Yearcarddetal(String iteminid, String compid, String phone,
			String packno, String itemno) {
		this.iteminid = iteminid;
		this.compid = compid;
		this.phone = phone;
		this.packno = packno;
		this.itemno = itemno;
	}

	/** full constructor */
	public Yearcarddetal(String iteminid, String compid, String phone,
			String packno, String itemno, Integer num, Double amt,
			String remarks) {
		this.iteminid = iteminid;
		this.compid = compid;
		this.phone = phone;
		this.packno = packno;
		this.itemno = itemno;
		this.num = num;
		this.amt = amt;
		this.remarks = remarks;
	}

	// Property accessors

	public String getIteminid() {
		return this.iteminid;
	}

	public void setIteminid(String iteminid) {
		this.iteminid = iteminid;
	}

	public String getCompid() {
		return this.compid;
	}

	public void setCompid(String compid) {
		this.compid = compid;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPackno() {
		return this.packno;
	}

	public void setPackno(String packno) {
		this.packno = packno;
	}

	public String getItemno() {
		return this.itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getAmt() {
		return this.amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMkdate() {
		return mkdate;
	}

	public void setMkdate(String mkdate) {
		this.mkdate = mkdate;
	}

}