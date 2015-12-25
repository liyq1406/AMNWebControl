package com.amani.model;

import java.io.Serializable;

import javax.persistence.Column;

public class SchoolIndexUionPKID implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	private String postion_no;
	@Column
	private String time;
	@Column
	private String type;
	public String getPostion_no() {
		return postion_no;
	}
	public void setPostion_no(String postion_no) {
		this.postion_no = postion_no;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
