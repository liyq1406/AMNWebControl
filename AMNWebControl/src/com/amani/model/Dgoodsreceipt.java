package com.amani.model;

import java.math.BigDecimal;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dgoodsreceipt  implements java.io.Serializable {


	private DgoodsreceiptId id;
	private String receiptgoodsno;	
	private String receiptgoodsname;
	private String receiptgoodsunit ;
	
    private BigDecimal receiptgoodscount;
    private BigDecimal receiptprice;	
    private BigDecimal receiptgoodsamt;	

    private BigDecimal sendgoodscount;
    private BigDecimal damagegoodscount;
    private BigDecimal debegiidscount;	
    private BigDecimal ordergoodscount;
	public DgoodsreceiptId getId() {
		return id;
	}
	public void setId(DgoodsreceiptId id) {
		this.id = id;
	}
	public String getReceiptgoodsno() {
		return receiptgoodsno;
	}
	public void setReceiptgoodsno(String receiptgoodsno) {
		this.receiptgoodsno = receiptgoodsno;
	}
	public String getReceiptgoodsname() {
		return receiptgoodsname;
	}
	public void setReceiptgoodsname(String receiptgoodsname) {
		this.receiptgoodsname = receiptgoodsname;
	}
	public String getReceiptgoodsunit() {
		return receiptgoodsunit;
	}
	public void setReceiptgoodsunit(String receiptgoodsunit) {
		this.receiptgoodsunit = receiptgoodsunit;
	}
	public BigDecimal getReceiptgoodscount() {
		return receiptgoodscount;
	}
	public void setReceiptgoodscount(BigDecimal receiptgoodscount) {
		this.receiptgoodscount = receiptgoodscount;
	}
	public BigDecimal getReceiptprice() {
		return receiptprice;
	}
	public void setReceiptprice(BigDecimal receiptprice) {
		this.receiptprice = receiptprice;
	}
	public BigDecimal getReceiptgoodsamt() {
		return receiptgoodsamt;
	}
	public void setReceiptgoodsamt(BigDecimal receiptgoodsamt) {
		this.receiptgoodsamt = receiptgoodsamt;
	}
	public BigDecimal getSendgoodscount() {
		return sendgoodscount;
	}
	public void setSendgoodscount(BigDecimal sendgoodscount) {
		this.sendgoodscount = sendgoodscount;
	}
	public BigDecimal getDamagegoodscount() {
		return damagegoodscount;
	}
	public void setDamagegoodscount(BigDecimal damagegoodscount) {
		this.damagegoodscount = damagegoodscount;
	}
	public BigDecimal getDebegiidscount() {
		return debegiidscount;
	}
	public void setDebegiidscount(BigDecimal debegiidscount) {
		this.debegiidscount = debegiidscount;
	}
	public BigDecimal getOrdergoodscount() {
		return ordergoodscount;
	}
	public void setOrdergoodscount(BigDecimal ordergoodscount) {
		this.ordergoodscount = ordergoodscount;
	}	
}