package com.amani.model;

/**
 * Evaluation entity. @author MyEclipse Persistence Tools
 */

public class Evaluation implements java.io.Serializable {

	// Fields

	private String uuid;
	private String billid;
	private String operid;
	private Double discount;
	private String content;
	private Integer states;
	private Double amt;
	private String strCardNo;
	private String strCompName;
	private String membername;
	private String membermphone;
	private String strCompId;
	private String remarks;

	// Constructors

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStrCardNo() {
		return strCardNo;
	}

	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}

	public String getStrCompName() {
		return strCompName;
	}

	public void setStrCompName(String strCompName) {
		this.strCompName = strCompName;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getMembermphone() {
		return membermphone;
	}

	public void setMembermphone(String membermphone) {
		this.membermphone = membermphone;
	}

	public String getStrCompId() {
		return strCompId;
	}

	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}

	public Double getAmt() {
		return amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	/** default constructor */
	public Evaluation() {
	}

	/** minimal constructor */
	public Evaluation(String uuid, String billid) {
		this.uuid = uuid;
		this.billid = billid;
	}

	/** full constructor */
	public Evaluation(String uuid, String billid, String operid,
			Double discount, String content, Integer states) {
		this.uuid = uuid;
		this.billid = billid;
		this.operid = operid;
		this.discount = discount;
		this.content = content;
		this.states = states;
	}

	// Property accessors

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBillid() {
		return this.billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public String getOperid() {
		return this.operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStates() {
		return this.states;
	}

	public void setStates(Integer states) {
		this.states = states;
	}

}