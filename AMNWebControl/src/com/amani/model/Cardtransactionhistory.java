package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

public class Cardtransactionhistory {
	private String transactioncompid;
	private String transactionseqno;
	private String transactioncardno;
	private String transactiondate;
	private String transactiontype;
	private String transactiontypeText;
	private String codeno;
	private String codename;
	private BigDecimal ccount;
	private BigDecimal price;
	private String billtype;
	private String billno;
	private	String firstempid;
	private String secondempid;
	private String thirthempid;
	private String paymode;
	public String getTransactioncompid() {
		return transactioncompid;
	}
	public void setTransactioncompid(String transactioncompid) {
		this.transactioncompid = transactioncompid;
	}
	public String getTransactionseqno() {
		return transactionseqno;
	}
	public void setTransactionseqno(String transactionseqno) {
		this.transactionseqno = transactionseqno;
	}
	public String getTransactioncardno() {
		return transactioncardno;
	}
	public void setTransactioncardno(String transactioncardno) {
		this.transactioncardno = transactioncardno;
	}
	public String getTransactiondate() {
		return transactiondate;
	}
	public void setTransactiondate(String transactiondate) {
		this.transactiondate = transactiondate;
	}
	public String getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
	public String getCodeno() {
		return codeno;
	}
	public void setCodeno(String codeno) {
		this.codeno = codeno;
	}
	public String getCodename() {
		return codename;
	}
	public void setCodename(String codename) {
		this.codename = codename;
	}
	public BigDecimal getCcount() {
		return CommonTool.FormatBigDecimal(ccount).setScale(2, BigDecimal.ROUND_HALF_UP)  ; 
	}
	public void setCcount(BigDecimal ccount) {
		this.ccount = ccount;
	}
	public BigDecimal getPrice() {
		return CommonTool.FormatBigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP)  ; 
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getBilltype() {
		return billtype;
	}
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	public String getFirstempid() {
		return firstempid;
	}
	public void setFirstempid(String firstempid) {
		this.firstempid = firstempid;
	}
	public String getSecondempid() {
		return secondempid;
	}
	public void setSecondempid(String secondempid) {
		this.secondempid = secondempid;
	}
	public String getThirthempid() {
		return thirthempid;
	}
	public void setThirthempid(String thirthempid) {
		this.thirthempid = thirthempid;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	public String getTransactiontypeText() {
		return transactiontypeText;
	}
	public void setTransactiontypeText(String transactiontypeText) {
		this.transactiontypeText = transactiontypeText;
	}
}
