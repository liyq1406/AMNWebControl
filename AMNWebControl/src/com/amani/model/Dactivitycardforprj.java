package com.amani.model;

import java.math.BigDecimal;

public class Dactivitycardforprj {
	private String cardvesting;					//公司编号
	private String cardvestingname;				//公司编号
	private String cardno;						//非内部卡号
	private String expericeitemno;				//体验项目
	private String expericeitemname;			//体验项目
	private BigDecimal expericeitemprice;		//体验价格
	public String getCardvesting() {
		return cardvesting;
	}
	public void setCardvesting(String cardvesting) {
		this.cardvesting = cardvesting;
	}
	public String getCardvestingname() {
		return cardvestingname;
	}
	public void setCardvestingname(String cardvestingname) {
		this.cardvestingname = cardvestingname;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
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
}
