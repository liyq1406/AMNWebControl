package com.amani.model;

/**
 * Yearcardinof entity. @author MyEclipse Persistence Tools
 */

public class Yearcardinof implements java.io.Serializable {

	// Fields

	private String phone;
	private String compid;
	private String phoneinid;
	private String name;
	private String cid;
	private String img;
	private String remarks;

	// Constructors

	/** default constructor */
	public Yearcardinof() {
	}

	/** minimal constructor */
	public Yearcardinof(String phone, String compid, String phoneinid) {
		this.phone = phone;
		this.compid = compid;
		this.phoneinid = phoneinid;
	}

	/** full constructor */
	public Yearcardinof(String phone, String compid, String phoneinid,
			String name, String cid, String img, String remarks) {
		this.phone = phone;
		this.compid = compid;
		this.phoneinid = phoneinid;
		this.name = name;
		this.cid = cid;
		this.img = img;
		this.remarks = remarks;
	}

	// Property accessors

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompid() {
		return this.compid;
	}

	public void setCompid(String compid) {
		this.compid = compid;
	}

	public String getPhoneinid() {
		return this.phoneinid;
	}

	public void setPhoneinid(String phoneinid) {
		this.phoneinid = phoneinid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}