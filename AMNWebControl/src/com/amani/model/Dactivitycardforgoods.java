package com.amani.model;

import java.math.BigDecimal;

public class Dactivitycardforgoods {
	private String cardvesting;					//公司编号
	private String cardvestingname;					//公司编号
	private String cardno;						//非内部卡号
	private String salegoodstype;				//销售产品类型
	private String salegoodstypename;				//销售产品类型
	private BigDecimal salegoodsrate;			//销售产品折扣系数	
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
	public String getSalegoodstype() {
		return salegoodstype;
	}
	public void setSalegoodstype(String salegoodstype) {
		this.salegoodstype = salegoodstype;
	}
	public String getSalegoodstypename() {
		return salegoodstypename;
	}
	public void setSalegoodstypename(String salegoodstypename) {
		this.salegoodstypename = salegoodstypename;
	}
	public BigDecimal getSalegoodsrate() {
		return salegoodsrate;
	}
	public void setSalegoodsrate(BigDecimal salegoodsrate) {
		this.salegoodsrate = salegoodsrate;
	}
}
