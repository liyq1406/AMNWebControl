package com.amani.model;

/**
 * CompchaininfoId entity. @author MyEclipse Persistence Tools
 */

public class CompchaininfoId implements java.io.Serializable {

	// Fields

	private String curcomp;
	private String relationcomp;

	// Constructors

	/** default constructor */
	public CompchaininfoId() {
	}

	/** full constructor */
	public CompchaininfoId(String curcomp, String relationcomp) {
		this.curcomp = curcomp;
		this.relationcomp = relationcomp;
	}

	// Property accessors

	public String getCurcomp() {
		return this.curcomp;
	}

	public void setCurcomp(String curcomp) {
		this.curcomp = curcomp;
	}

	public String getRelationcomp() {
		return this.relationcomp;
	}

	public void setRelationcomp(String relationcomp) {
		this.relationcomp = relationcomp;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CompchaininfoId))
			return false;
		CompchaininfoId castOther = (CompchaininfoId) other;

		return ((this.getCurcomp() == castOther.getCurcomp()) || (this
				.getCurcomp() != null && castOther.getCurcomp() != null && this
				.getCurcomp().equals(castOther.getCurcomp())))
				&& ((this.getRelationcomp() == castOther.getRelationcomp()) || (this
						.getRelationcomp() != null
						&& castOther.getRelationcomp() != null && this
						.getRelationcomp().equals(castOther.getRelationcomp())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCurcomp() == null ? 0 : this.getCurcomp().hashCode());
		result = 37
				* result
				+ (getRelationcomp() == null ? 0 : this.getRelationcomp()
						.hashCode());
		return result;
	}

}