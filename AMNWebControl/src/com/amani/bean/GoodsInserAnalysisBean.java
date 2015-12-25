package com.amani.bean;

import java.math.BigDecimal;

public class GoodsInserAnalysisBean
{
  private String strGoodsId;
  private String strGoodsType;
  private String strGoodsName;
  private String strGoodsUnit;
  private String strInserDate;
  private String strInserBillId;
  private String strInserSuperName;
  private String strInserOptionName;
  private BigDecimal bInserCount;
  private BigDecimal bInserPrice;
  private BigDecimal bInserAmt;

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

  public String getStrInserDate() {
    return this.strInserDate; }

  public void setStrInserDate(String strInserDate) {
    this.strInserDate = strInserDate; }

  public String getStrInserBillId() {
    return this.strInserBillId; }

  public void setStrInserBillId(String strInserBillId) {
    this.strInserBillId = strInserBillId; }

  public BigDecimal getBInserCount() {
    return this.bInserCount; }

  public void setBInserCount(BigDecimal inserCount) {
    this.bInserCount = inserCount; }

  public BigDecimal getBInserPrice() {
    return this.bInserPrice; }

  public void setBInserPrice(BigDecimal inserPrice) {
    this.bInserPrice = inserPrice; }

  public BigDecimal getBInserAmt() {
    return this.bInserAmt; }

  public void setBInserAmt(BigDecimal inserAmt) {
    this.bInserAmt = inserAmt; }

  public String getStrInserSuperName() {
    return this.strInserSuperName; }

  public void setStrInserSuperName(String strInserSuperName) {
    this.strInserSuperName = strInserSuperName;
  }

public String getStrInserOptionName() {
	return strInserOptionName;
}

public void setStrInserOptionName(String strInserOptionName) {
	this.strInserOptionName = strInserOptionName;
}
}