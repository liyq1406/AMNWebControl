package com.amani.model;

import javax.persistence.Embeddable;

/**
 * 课程学分联合主键
 */
@Embeddable
public class SchoolScorePK implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	//课程编号
	private String credit_no;
	//职位
	private String postion;
	
	public String getCredit_no() {
		return credit_no;
	}
	public void setCredit_no(String credit_no) {
		this.credit_no = credit_no;
	}
	
	public String getPostion() {
		return postion;
	}
	public void setPostion(String postion) {
		this.postion = postion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		return prime * result + (credit_no + postion).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SchoolScorePK)){
			return false;
		}
		if (obj.equals(null)){
			return false;
		}
		SchoolScorePK pk = (SchoolScorePK) obj;
		return pk.credit_no.equals(credit_no) && pk.postion.equals(postion);
	}
}
