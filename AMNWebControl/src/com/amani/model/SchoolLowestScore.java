package com.amani.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="Schoollowestscore")
@IdClass(value=SchoollowestscorePK.class)
public class SchoolLowestScore {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	@Id
	private int syear;
	
	@Id
	private String postion_no;
	
	private float score;
	
	private int state = 1;

	public SchoolLowestScore(String postion_no, Object syear, Object score) throws RuntimeException {
		this.postion_no = postion_no;
		this.syear = Integer.parseInt(syear.toString().split("\\.")[0]);
		this.score = Float.parseFloat(score.toString());
	}
	
	public SchoolLowestScore() {
		super();
	}

	public int getSyear() {
		return syear;
	}

	public void setSyear(int syear) {
		this.syear = syear;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getPostion_no() {
		return postion_no;
	}

	public void setPostion_no(String postion_no) {
		this.postion_no = postion_no;
	}
	
}
