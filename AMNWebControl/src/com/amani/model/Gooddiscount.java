package com.amani.model;

/**
 * Gooddiscount entity. @author MyEclipse Persistence Tools
 */

public class Gooddiscount implements java.io.Serializable {

	// Fields

	private GooddiscountId id;
	private Integer iscard;
	private String bprojecttypeid;
	public String getBprojecttypeid() {
		return bprojecttypeid;
	}

	public void setBprojecttypeid(String bprojecttypeid) {
		this.bprojecttypeid = bprojecttypeid;
	}

	private String bprojecttypename;

	// Constructors

	public String getBprojecttypename() {
		return bprojecttypename;
	}

	public void setBprojecttypename(String bprojecttypename) {
		this.bprojecttypename = bprojecttypename;
	}

	/** default constructor */
	public Gooddiscount() {
	}

	/** minimal constructor */
	public Gooddiscount(GooddiscountId id) {
		this.id = id;
	}

	/** full constructor */
	public Gooddiscount(GooddiscountId id, Integer iscard) {
		this.id = id;
		this.iscard = iscard;
	}

	// Property accessors

	public GooddiscountId getId() {
		return this.id;
	}

	public void setId(GooddiscountId id) {
		this.id = id;
	}

	public Integer getIscard() {
		return this.iscard;
	}

	public void setIscard(Integer iscard) {
		this.iscard = iscard;
	}

}