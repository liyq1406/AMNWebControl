package com.amani.bean;

import java.math.BigDecimal;

public class TradeBillCount {
	private String strCompId;
	private String strCompName;
	private BigDecimal  cashBillAmt;
	private int			cashBillCount;
	private	BigDecimal	cashBillPrice;
	
	private BigDecimal  cardBillAmt;
	private int			cardBillCount;
	private	BigDecimal	cardBillPrice;
	
	private BigDecimal  totalBillAmt;
	private int			totalBillCount;
	private	BigDecimal	totalBillPrice;
	   
	private int			mrPrjBillCount;
	private int			mfPrjBillCount;
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}
	public String getStrCompName() {
		return strCompName;
	}
	public void setStrCompName(String strCompName) {
		this.strCompName = strCompName;
	}
	public BigDecimal getCashBillAmt() {
		return cashBillAmt;
	}
	public void setCashBillAmt(BigDecimal cashBillAmt) {
		this.cashBillAmt = cashBillAmt;
	}
	public int getCashBillCount() {
		return cashBillCount;
	}
	public void setCashBillCount(int cashBillCount) {
		this.cashBillCount = cashBillCount;
	}
	public BigDecimal getCashBillPrice() {
		return cashBillPrice;
	}
	public void setCashBillPrice(BigDecimal cashBillPrice) {
		this.cashBillPrice = cashBillPrice;
	}
	public BigDecimal getCardBillAmt() {
		return cardBillAmt;
	}
	public void setCardBillAmt(BigDecimal cardBillAmt) {
		this.cardBillAmt = cardBillAmt;
	}
	public int getCardBillCount() {
		return cardBillCount;
	}
	public void setCardBillCount(int cardBillCount) {
		this.cardBillCount = cardBillCount;
	}
	public BigDecimal getCardBillPrice() {
		return cardBillPrice;
	}
	public void setCardBillPrice(BigDecimal cardBillPrice) {
		this.cardBillPrice = cardBillPrice;
	}
	public BigDecimal getTotalBillAmt() {
		return totalBillAmt;
	}
	public void setTotalBillAmt(BigDecimal totalBillAmt) {
		this.totalBillAmt = totalBillAmt;
	}
	public int getTotalBillCount() {
		return totalBillCount;
	}
	public void setTotalBillCount(int totalBillCount) {
		this.totalBillCount = totalBillCount;
	}
	public BigDecimal getTotalBillPrice() {
		return totalBillPrice;
	}
	public void setTotalBillPrice(BigDecimal totalBillPrice) {
		this.totalBillPrice = totalBillPrice;
	}
	public int getMrPrjBillCount() {
		return mrPrjBillCount;
	}
	public void setMrPrjBillCount(int mrPrjBillCount) {
		this.mrPrjBillCount = mrPrjBillCount;
	}
	public int getMfPrjBillCount() {
		return mfPrjBillCount;
	}
	public void setMfPrjBillCount(int mfPrjBillCount) {
		this.mfPrjBillCount = mfPrjBillCount;
	}  
}
