package com.amani.model;

import javax.persistence.Embeddable;

/**
 * 职位年度最低学分联合主键
 */
@Embeddable
public class SchoollowestscorePK implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;

	private String postion_no;
	
	private int syear;

	public String getPostion_no() {
		return postion_no;
	}

	public void setPostion_no(String postion_no) {
		this.postion_no = postion_no;
	}

	public int getSyear() {
		return syear;
	}

	public void setSyear(int syear) {
		this.syear = syear;
	}
	
	
}
