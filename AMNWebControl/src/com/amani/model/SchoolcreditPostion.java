package com.amani.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Schoolcreditpostion")
public class SchoolcreditPostion {
	
	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String postion;
	
	private String schoolcreditno;
	
	private int type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPostion() {
		return postion;
	}

	public void setPostion(String postion) {
		this.postion = postion;
	}

	public String getSchoolcreditno() {
		return schoolcreditno;
	}

	public void setSchoolcreditno(String schoolcreditno) {
		this.schoolcreditno = schoolcreditno;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public SchoolcreditPostion(String postion, String schoolcreditno, int type) {
		super();
		this.postion = postion;
		this.schoolcreditno = schoolcreditno;
		this.type = type;
	}

	public SchoolcreditPostion() {
		super();
	}
	
}
