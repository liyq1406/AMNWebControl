package com.amani.model;

/**
 * YearcarddetalId entity. @author MyEclipse Persistence Tools
 */

public class YearcarddetalId implements java.io.Serializable {

	// Fields

	private String compid;
	private String phone;
	private String packno;
	private String itemno;
	private Integer num;
	private Double amt;
	private String remarks;

	// Constructors

	/** default constructor */
	public YearcarddetalId() {
	}

	/** minimal constructor */
	public YearcarddetalId(String compid, String phone, String packno,
			String itemno) {
		this.compid = compid;
		this.phone = phone;
		this.packno = packno;
		this.itemno = itemno;
	}

	/** full constructor */
	public YearcarddetalId(String compid, String phone, String packno,
			String itemno, Integer num, Double amt, String remarks) {
		this.compid = compid;
		this.phone = phone;
		this.packno = packno;
		this.itemno = itemno;
		this.num = num;
		this.amt = amt;
		this.remarks = remarks;
	}

	// Property accessors

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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof YearcarddetalId))
			return false;
		YearcarddetalId castOther = (YearcarddetalId) other;

		return ((this.getCompid() == castOther.getCompid()) || (this
				.getCompid() != null && castOther.getCompid() != null && this
				.getCompid().equals(castOther.getCompid())))
				&& ((this.getPhone() == castOther.getPhone()) || (this
						.getPhone() != null && castOther.getPhone() != null && this
						.getPhone().equals(castOther.getPhone())))
				&& ((this.getPackno() == castOther.getPackno()) || (this
						.getPackno() != null && castOther.getPackno() != null && this
						.getPackno().equals(castOther.getPackno())))
				&& ((this.getItemno() == castOther.getItemno()) || (this
						.getItemno() != null && castOther.getItemno() != null && this
						.getItemno().equals(castOther.getItemno())))
				&& ((this.getNum() == castOther.getNum()) || (this.getNum() != null
						&& castOther.getNum() != null && this.getNum().equals(
						castOther.getNum())))
				&& ((this.getAmt() == castOther.getAmt()) || (this.getAmt() != null
						&& castOther.getAmt() != null && this.getAmt().equals(
						castOther.getAmt())))
				&& ((this.getRemarks() == castOther.getRemarks()) || (this
						.getRemarks() != null && castOther.getRemarks() != null && this
						.getRemarks().equals(castOther.getRemarks())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCompid() == null ? 0 : this.getCompid().hashCode());
		result = 37 * result
				+ (getPhone() == null ? 0 : this.getPhone().hashCode());
		result = 37 * result
				+ (getPackno() == null ? 0 : this.getPackno().hashCode());
		result = 37 * result
				+ (getItemno() == null ? 0 : this.getItemno().hashCode());
		result = 37 * result
				+ (getNum() == null ? 0 : this.getNum().hashCode());
		result = 37 * result
				+ (getAmt() == null ? 0 : this.getAmt().hashCode());
		result = 37 * result
				+ (getRemarks() == null ? 0 : this.getRemarks().hashCode());
		return result;
	}

}