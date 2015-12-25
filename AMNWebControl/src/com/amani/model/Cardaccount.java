package com.amani.model;

import java.math.BigDecimal;

public class Cardaccount {
	private String cardvesting;
	private String cardno;
	private Integer accounttype;
	private String accounttypeText;
	private BigDecimal accountbalance;
	private BigDecimal accountdebts;
	private String accountdatefrom;
	private String accountdateend;
	private String accountremark;
	public String getCardvesting() {
		return cardvesting;
	}
	public void setCardvesting(String cardvesting) {
		this.cardvesting = cardvesting;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public Integer getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(Integer accounttype) {
		this.accounttype = accounttype;
	}
	public BigDecimal getAccountbalance() {
		return accountbalance;
	}
	public void setAccountbalance(BigDecimal accountbalance) {
		this.accountbalance = accountbalance;
	}
	public BigDecimal getAccountdebts() {
		return accountdebts;
	}
	public void setAccountdebts(BigDecimal accountdebts) {
		this.accountdebts = accountdebts;
	}
	public String getAccountdatefrom() {
		return accountdatefrom;
	}
	public void setAccountdatefrom(String accountdatefrom) {
		this.accountdatefrom = accountdatefrom;
	}
	public String getAccountdateend() {
		return accountdateend;
	}
	public void setAccountdateend(String accountdateend) {
		this.accountdateend = accountdateend;
	}
	public String getAccountremark() {
		return accountremark;
	}
	public void setAccountremark(String accountremark) {
		this.accountremark = accountremark;
	}
	public String getAccounttypeText() {
		return accounttypeText;
	}
	public void setAccounttypeText(String accounttypeText) {
		this.accounttypeText = accounttypeText;
	}
}
