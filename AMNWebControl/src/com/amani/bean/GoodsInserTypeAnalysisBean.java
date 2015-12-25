package com.amani.bean;

public class GoodsInserTypeAnalysisBean
{
  private String strCompId;
  private String strCompName;
  private String strInserDate;
  private double[][] goodsInserTypesAmt;

  public String getStrCompId()
  {
    return this.strCompId; }

  public void setStrCompId(String strCompId) {
    this.strCompId = strCompId; }

  public String getStrCompName() {
    return this.strCompName; }

  public void setStrCompName(String strCompName) {
    this.strCompName = strCompName; }

  public String getStrInserDate() {
    return this.strInserDate; }

  public void setStrInserDate(String strInserDate) {
    this.strInserDate = strInserDate; }

  public double[][] getGoodsInserTypesAmt() {
    return this.goodsInserTypesAmt; }

  public void setGoodsInserTypesAmt(double[][] goodsInserTypesAmt) {
    this.goodsInserTypesAmt = goodsInserTypesAmt;
  }
}