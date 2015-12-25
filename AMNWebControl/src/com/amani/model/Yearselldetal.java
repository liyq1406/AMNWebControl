package com.amani.model;

/**
 * Yearselldetal entity. @author MyEclipse Persistence Tools
 */

public class Yearselldetal implements java.io.Serializable {

	// Fields

	private YearselldetalId id;
	private String packno;
	private String itemno;
	private String iteminid;
	private Integer num;
	private Double amt;
	private String firstempno;
	private String firstinid;
	private String firstempname;
	private String sendempno;
	private String sendinid;
	private String sendempname;
	private String threeempno;
	private String threeinid;
	private String threeempname;
	private String fourempno;
	private String fourinid;
	private String fourempname;
	private String probz;
	private String cashdyq;
	private Integer sendpointflag;
	public String getCashdyq() {
		return cashdyq;
	}

	public void setCashdyq(String cashdyq) {
		this.cashdyq = cashdyq;
	}

	public String getProbz() {
		return probz;
	}

	public void setProbz(String probz) {
		this.probz = probz;
	}

	public String getFirstempname() {
		return firstempname;
	}

	public void setFirstempname(String firstempname) {
		this.firstempname = firstempname;
	}

	public String getSendempname() {
		return sendempname;
	}

	public void setSendempname(String sendempname) {
		this.sendempname = sendempname;
	}

	public String getThreeempname() {
		return threeempname;
	}

	public void setThreeempname(String threeempname) {
		this.threeempname = threeempname;
	}

	public String getFourempname() {
		return fourempname;
	}

	public void setFourempname(String fourempname) {
		this.fourempname = fourempname;
	}

	private Double firstperf;
	private Double sendperf;
	private Double threeperf;
	private Double fourperf;
	private String itemName;

	// Constructors

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/** default constructor */
	public Yearselldetal() {
	}

	/** minimal constructor */
	public Yearselldetal(YearselldetalId id, String packno, String itemno,
			Integer num) {
		this.id = id;
		this.packno = packno;
		this.itemno = itemno;
		this.num = num;
	}

	/** full constructor */
	public Yearselldetal(YearselldetalId id, String packno, String itemno,
			String iteminid, Integer num, Double amt, String firstempno,
			String firstinid, String sendempno, String sendinid,
			String threeempno, String threeinid, String fourempno,
			String fourinid, Double firstperf, Double sendperf,
			Double threeperf, Double fourperf) {
		this.id = id;
		this.packno = packno;
		this.itemno = itemno;
		this.iteminid = iteminid;
		this.num = num;
		this.amt = amt;
		this.firstempno = firstempno;
		this.firstinid = firstinid;
		this.sendempno = sendempno;
		this.sendinid = sendinid;
		this.threeempno = threeempno;
		this.threeinid = threeinid;
		this.fourempno = fourempno;
		this.fourinid = fourinid;
		this.firstperf = firstperf;
		this.sendperf = sendperf;
		this.threeperf = threeperf;
		this.fourperf = fourperf;
	}

	// Property accessors

	public YearselldetalId getId() {
		return this.id;
	}

	public void setId(YearselldetalId id) {
		this.id = id;
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

	public String getIteminid() {
		return this.iteminid;
	}

	public void setIteminid(String iteminid) {
		this.iteminid = iteminid;
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

	public String getFirstempno() {
		return this.firstempno;
	}

	public void setFirstempno(String firstempno) {
		this.firstempno = firstempno;
	}

	public String getFirstinid() {
		return this.firstinid;
	}

	public void setFirstinid(String firstinid) {
		this.firstinid = firstinid;
	}

	public String getSendempno() {
		return this.sendempno;
	}

	public void setSendempno(String sendempno) {
		this.sendempno = sendempno;
	}

	public String getSendinid() {
		return this.sendinid;
	}

	public void setSendinid(String sendinid) {
		this.sendinid = sendinid;
	}

	public String getThreeempno() {
		return this.threeempno;
	}

	public void setThreeempno(String threeempno) {
		this.threeempno = threeempno;
	}

	public String getThreeinid() {
		return this.threeinid;
	}

	public void setThreeinid(String threeinid) {
		this.threeinid = threeinid;
	}

	public String getFourempno() {
		return this.fourempno;
	}

	public void setFourempno(String fourempno) {
		this.fourempno = fourempno;
	}

	public String getFourinid() {
		return this.fourinid;
	}

	public void setFourinid(String fourinid) {
		this.fourinid = fourinid;
	}

	public Double getFirstperf() {
		return this.firstperf;
	}

	public void setFirstperf(Double firstperf) {
		this.firstperf = firstperf;
	}

	public Double getSendperf() {
		return this.sendperf;
	}

	public void setSendperf(Double sendperf) {
		this.sendperf = sendperf;
	}

	public Double getThreeperf() {
		return this.threeperf;
	}

	public void setThreeperf(Double threeperf) {
		this.threeperf = threeperf;
	}

	public Double getFourperf() {
		return this.fourperf;
	}

	public void setFourperf(Double fourperf) {
		this.fourperf = fourperf;
	}

	public Integer getSendpointflag() {
		return sendpointflag;
	}

	public void setSendpointflag(Integer sendpointflag) {
		this.sendpointflag = sendpointflag;
	}
	
}