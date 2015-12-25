package com.amani.bean;

import java.math.BigDecimal;

public class PringBillBean {
	  private String projectType;
	  private String projectNo;
	  private String projectName;
	  private BigDecimal costNumber;
	  private BigDecimal costMoney;
	  private String projectTime;
	  private String memberCardId;
	  private String strFristStaffNo;
	  private String strSecondStaffNo;
	  private String strThirdStaffNo;
	  private BigDecimal standardPrice;
	  private BigDecimal discountPrice;
	  private String paymode;
	  private double csproseqno;
	  private String yearinid;
	public String getYearinid() {
		return yearinid;
	}
	public void setYearinid(String yearinid) {
		this.yearinid = yearinid;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public BigDecimal getCostNumber() {
		return costNumber;
	}
	public void setCostNumber(BigDecimal costNumber) {
		this.costNumber = costNumber;
	}
	public BigDecimal getCostMoney() {
		return costMoney;
	}
	public void setCostMoney(BigDecimal costMoney) {
		this.costMoney = costMoney;
	}
	public String getProjectTime() {
		return projectTime;
	}
	public void setProjectTime(String projectTime) {
		this.projectTime = projectTime;
	}
	public String getMemberCardId() {
		return memberCardId;
	}
	public void setMemberCardId(String memberCardId) {
		this.memberCardId = memberCardId;
	}
	public String getStrFristStaffNo() {
		return strFristStaffNo;
	}
	public void setStrFristStaffNo(String strFristStaffNo) {
		this.strFristStaffNo = strFristStaffNo;
	}
	public String getStrSecondStaffNo() {
		return strSecondStaffNo;
	}
	public void setStrSecondStaffNo(String strSecondStaffNo) {
		this.strSecondStaffNo = strSecondStaffNo;
	}
	public String getStrThirdStaffNo() {
		return strThirdStaffNo;
	}
	public void setStrThirdStaffNo(String strThirdStaffNo) {
		this.strThirdStaffNo = strThirdStaffNo;
	}
	public BigDecimal getStandardPrice() {
		return standardPrice;
	}
	public void setStandardPrice(BigDecimal standardPrice) {
		this.standardPrice = standardPrice;
	}
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public double getCsproseqno() {
		return csproseqno;
	}
	public void setCsproseqno(double csproseqno) {
		this.csproseqno = csproseqno;
	}
}
