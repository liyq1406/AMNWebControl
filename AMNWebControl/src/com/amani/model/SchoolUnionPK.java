package com.amani.model;

import javax.persistence.Embeddable;

/**
 * 联合主键
 */
@Embeddable
public class SchoolUnionPK implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	//录入部门-联合主键1
	private String salecompid;
	//录入单号-联合主键2
	private String salebillid;
	//员工工号
	private String staffno;
	//课程
	private String credit;
	
	public String getSalecompid() {
		return salecompid;
	}
	public void setSalecompid(String salecompid) {
		this.salecompid = salecompid;
	}

	public String getSalebillid() {
		return salebillid;
	}
	public void setSalebillid(String salebillid) {
		this.salebillid = salebillid;
	}
	
	public String getStaffno() {
		return staffno;
	}
	public void setStaffno(String staffno) {
		this.staffno = staffno;
	}
	
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		return prime * result + (salecompid + salebillid + staffno + credit).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SchoolUnionPK)){
			return false;
		}
		if (obj.equals(null)){
			return false;
		}
		SchoolUnionPK pk = (SchoolUnionPK) obj;
		return pk.salecompid.equals(salecompid) && pk.salebillid.equals(salebillid) 
				&& pk.staffno.equals(staffno) && pk.credit.equals(credit);
	}
}
