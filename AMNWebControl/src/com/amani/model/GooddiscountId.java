package com.amani.model;

/**
 * GooddiscountId entity. @author MyEclipse Persistence Tools
 */

public class GooddiscountId implements java.io.Serializable {

	// Fields

	private String compid;
	private String bprojecttypeid;

	// Constructors

	/** default constructor */
	public GooddiscountId() {
	}

	/** full constructor */
	public GooddiscountId(String compid, String bprojecttypeid) {
		this.compid = compid;
		this.bprojecttypeid = bprojecttypeid;
	}

	// Property accessors

	public String getCompid() {
		return this.compid;
	}

	public void setCompid(String compid) {
		this.compid = compid;
	}

	public String getBprojecttypeid() {
		return this.bprojecttypeid;
	}

	public void setBprojecttypeid(String bprojecttypeid) {
		this.bprojecttypeid = bprojecttypeid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GooddiscountId))
			return false;
		GooddiscountId castOther = (GooddiscountId) other;

		return ((this.getCompid() == castOther.getCompid()) || (this
				.getCompid() != null && castOther.getCompid() != null && this
				.getCompid().equals(castOther.getCompid())))
				&& ((this.getBprojecttypeid() == castOther.getBprojecttypeid()) || (this
						.getBprojecttypeid() != null
						&& castOther.getBprojecttypeid() != null && this
						.getBprojecttypeid().equals(
								castOther.getBprojecttypeid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCompid() == null ? 0 : this.getCompid().hashCode());
		result = 37
				* result
				+ (getBprojecttypeid() == null ? 0 : this.getBprojecttypeid()
						.hashCode());
		return result;
	}

}