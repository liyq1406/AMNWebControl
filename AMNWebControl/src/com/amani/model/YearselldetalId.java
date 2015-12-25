package com.amani.model;

/**
 * YearselldetalId entity. @author MyEclipse Persistence Tools
 */

public class YearselldetalId implements java.io.Serializable {

	// Fields

	private String compid;
	private String billid;
	private Integer seq;

	// Constructors

	/** default constructor */
	public YearselldetalId() {
	}

	/** full constructor */
	public YearselldetalId(String compid, String billid, Integer seq) {
		this.compid = compid;
		this.billid = billid;
		this.seq = seq;
	}

	// Property accessors

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

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof YearselldetalId))
			return false;
		YearselldetalId castOther = (YearselldetalId) other;

		return ((this.getCompid() == castOther.getCompid()) || (this
				.getCompid() != null && castOther.getCompid() != null && this
				.getCompid().equals(castOther.getCompid())))
				&& ((this.getBillid() == castOther.getBillid()) || (this
						.getBillid() != null && castOther.getBillid() != null && this
						.getBillid().equals(castOther.getBillid())))
				&& ((this.getSeq() == castOther.getSeq()) || (this.getSeq() != null
						&& castOther.getSeq() != null && this.getSeq().equals(
						castOther.getSeq())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCompid() == null ? 0 : this.getCompid().hashCode());
		result = 37 * result
				+ (getBillid() == null ? 0 : this.getBillid().hashCode());
		result = 37 * result
				+ (getSeq() == null ? 0 : this.getSeq().hashCode());
		return result;
	}

}