package com.amani.model;

import java.math.BigDecimal;

public class Activityrandcardinfo {
	private String randcode;						//非内部卡号
	private String expericeitemno;				//体验项目
	private String expericeitemname;			//体验项目
	private BigDecimal expericeitemprice;		//体验价格
	private BigDecimal expericeitemcount;		//体验价格
	public String getRandcode() {
		return randcode;
	}
	public void setRandcode(String randcode) {
		this.randcode = randcode;
	}
	public String getExpericeitemno() {
		return expericeitemno;
	}
	public void setExpericeitemno(String expericeitemno) {
		this.expericeitemno = expericeitemno;
	}
	public String getExpericeitemname() {
		return expericeitemname;
	}
	public void setExpericeitemname(String expericeitemname) {
		this.expericeitemname = expericeitemname;
	}
	public BigDecimal getExpericeitemprice() {
		return expericeitemprice;
	}
	public void setExpericeitemprice(BigDecimal expericeitemprice) {
		this.expericeitemprice = expericeitemprice;
	}
	public BigDecimal getExpericeitemcount() {
		return expericeitemcount;
	}
	public void setExpericeitemcount(BigDecimal expericeitemcount) {
		this.expericeitemcount = expericeitemcount;
	}
}
