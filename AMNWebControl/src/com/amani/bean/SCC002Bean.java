package com.amani.bean;

/**
 * 职位学分信息
 */
public class SCC002Bean {
	//课程编号
	private String credit_no;
	//职位
	private String postion;
	//学分
	private Integer score;
	
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
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
