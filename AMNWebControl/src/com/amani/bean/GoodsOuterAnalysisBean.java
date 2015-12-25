package com.amani.bean;

import java.math.BigDecimal;

public class GoodsOuterAnalysisBean
{
  private String strGoodsId;
  private String strGoodsType;
  private String strGoodsName;
  private String strGoodsUnit;
  private String strOuterDate;
  private String strOuterBillId;
  private String strReciverCompId;
  private String strReciverCompName;
  private String strOutOptionName;
  private BigDecimal bOuterCount;
  private BigDecimal bOuterPrice;
  private BigDecimal bOuterAmt;

  private BigDecimal bOuterCostPrice;
  private BigDecimal bOuterCostAmt;
  
  public String getStrGoodsId()
  {
    return this.strGoodsId; }

  public void setStrGoodsId(String strGoodsId) {
    this.strGoodsId = strGoodsId; }

  public String getStrGoodsType() {
    return this.strGoodsType; }

  public void setStrGoodsType(String strGoodsType) {
    this.strGoodsType = strGoodsType; }

  public String getStrGoodsName() {
    return this.strGoodsName; }

  public void setStrGoodsName(String strGoodsName) {
    this.strGoodsName = strGoodsName; }

  public String getStrGoodsUnit() {
    return this.strGoodsUnit; }

  public void setStrGoodsUnit(String strGoodsUnit) {
    this.strGoodsUnit = strGoodsUnit; }

  public String getStrOuterDate() {
    return this.strOuterDate; }

  public void setStrOuterDate(String strOuterDate) {
    this.strOuterDate = strOuterDate; }

  public String getStrOuterBillId() {
    return this.strOuterBillId; }

  public void setStrOuterBillId(String strOuterBillId) {
    this.strOuterBillId = strOuterBillId; }

  public String getStrReciverCompId() {
    return this.strReciverCompId; }

  public void setStrReciverCompId(String strReciverCompId) {
    this.strReciverCompId = strReciverCompId; }

  public String getStrReciverCompName() {
    return this.strReciverCompName; }

  public void setStrReciverCompName(String strReciverCompName) {
    this.strReciverCompName = strReciverCompName; }

  public BigDecimal getBOuterCount() {
    return this.bOuterCount; }

  public void setBOuterCount(BigDecimal outerCount) {
    this.bOuterCount = outerCount; }

  public BigDecimal getBOuterPrice() {
    return this.bOuterPrice; }

  public void setBOuterPrice(BigDecimal outerPrice) {
    this.bOuterPrice = outerPrice; }

  public BigDecimal getBOuterAmt() {
    return this.bOuterAmt; }

  public void setBOuterAmt(BigDecimal outerAmt) {
    this.bOuterAmt = outerAmt;
  }

public String getStrOutOptionName() {
	return strOutOptionName;
}

public void setStrOutOptionName(String strOutOptionName) {
	this.strOutOptionName = strOutOptionName;
}

public BigDecimal getBOuterCostPrice() {
	return bOuterCostPrice;
}

public void setBOuterCostPrice(BigDecimal outerCostPrice) {
	bOuterCostPrice = outerCostPrice;
}

public BigDecimal getBOuterCostAmt() {
	return bOuterCostAmt;
}

public void setBOuterCostAmt(BigDecimal outerCostAmt) {
	bOuterCostAmt = outerCostAmt;
}
}