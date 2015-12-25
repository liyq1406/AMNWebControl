package com.amani.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="schoolindex")
@IdClass(value=SchoolIndexUionPKID.class)
public class SchoolIndex {

	private String postion_no;
	private String time;
	private String type;
	private String num;

	@Id
	public String getPostion_no() {
		return postion_no;
	}

	public void setPostion_no(String postion_no) {
		this.postion_no = postion_no;
	}

	@Id
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Id
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public SchoolIndex(String postion_no, String time, String type, String num) {
		super();
		this.postion_no = postion_no;
		this.time = time;
		this.type = type;
		this.num = num;
	}

	public SchoolIndex() {
		super();
	}
	
}
