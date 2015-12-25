package com.amani.model;

/**
 * YearsellinfoId entity. @author MyEclipse Persistence Tools
 */

public class YearsellinfoId implements java.io.Serializable {

	// Fields

	private String compid;
	private String billid;

	// Constructors

	/** default constructor */
	public YearsellinfoId() {
	}

	/** full constructor */
	public YearsellinfoId(String compid, String billid) {
		this.compid = compid;
		this.billid = billid;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof YearsellinfoId))
			return false;
		YearsellinfoId castOther = (YearsellinfoId) other;

		return ((this.getCompid() == castOther.getCompid()) || (this
				.getCompid() != null && castOther.getCompid() != null && this
				.getCompid().equals(castOther.getCompid())))
				&& ((this.getBillid() == castOther.getBillid()) || (this
						.getBillid() != null && castOther.getBillid() != null && this
						.getBillid().equals(castOther.getBillid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCompid() == null ? 0 : this.getCompid().hashCode());
		result = 37 * result
				+ (getBillid() == null ? 0 : this.getBillid().hashCode());
		return result;
	}

}