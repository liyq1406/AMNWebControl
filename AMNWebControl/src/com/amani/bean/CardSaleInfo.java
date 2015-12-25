package com.amani.bean;

public class CardSaleInfo {
	/**
	 * 会员卡号
	 */
	private String strCardNo;
	/**
	 * 会员名称
	 */
	private String strCardName;
	/**
	 * 会员卡类别
	 */
	private String strCardClass;
	/**
	 * 会员手机号码
	 */
	private String strCardPhone;
	/**
	 * 消费金额
	 */
	private double amt;
	public String getStrCardNo() {
		return strCardNo;
	}
	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}
	public String getStrCardName() {
		return strCardName;
	}
	public void setStrCardName(String strCardName) {
		this.strCardName = strCardName;
	}
	public String getStrCardClass() {
		return strCardClass;
	}
	public void setStrCardClass(String strCardClass) {
		this.strCardClass = strCardClass;
	}
	public String getStrCardPhone() {
		return strCardPhone;
	}
	public void setStrCardPhone(String strCardPhone) {
		this.strCardPhone = strCardPhone;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
}
