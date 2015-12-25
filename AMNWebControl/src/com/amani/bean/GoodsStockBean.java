package com.amani.bean;

import java.math.BigDecimal;

public class GoodsStockBean {
	private String strGoodsNo;
	private String strGoodsName;
	private String strGoodsTypeName;
	private String strGoodsTypeNo;
	private String strGoodsUnit;
	private BigDecimal bGoodsCount;
	private BigDecimal bGoodsPrice;
	private BigDecimal bGoodsAmt;
	public String getStrGoodsNo() {
		return strGoodsNo;
	}
	public void setStrGoodsNo(String strGoodsNo) {
		this.strGoodsNo = strGoodsNo;
	}
	public String getStrGoodsName() {
		return strGoodsName;
	}
	public void setStrGoodsName(String strGoodsName) {
		this.strGoodsName = strGoodsName;
	}
	public String getStrGoodsTypeName() {
		return strGoodsTypeName;
	}
	public void setStrGoodsTypeName(String strGoodsTypeName) {
		this.strGoodsTypeName = strGoodsTypeName;
	}
	public String getStrGoodsTypeNo() {
		return strGoodsTypeNo;
	}
	public void setStrGoodsTypeNo(String strGoodsTypeNo) {
		this.strGoodsTypeNo = strGoodsTypeNo;
	}
	public String getStrGoodsUnit() {
		return strGoodsUnit;
	}
	public void setStrGoodsUnit(String strGoodsUnit) {
		this.strGoodsUnit = strGoodsUnit;
	}
	public BigDecimal getBGoodsCount() {
		return bGoodsCount;
	}
	public void setBGoodsCount(BigDecimal goodsCount) {
		bGoodsCount = goodsCount;
	}
	public BigDecimal getBGoodsPrice() {
		return bGoodsPrice;
	}
	public void setBGoodsPrice(BigDecimal goodsPrice) {
		bGoodsPrice = goodsPrice;
	}
	public BigDecimal getBGoodsAmt() {
		return bGoodsAmt;
	}
	public void setBGoodsAmt(BigDecimal goodsAmt) {
		bGoodsAmt = goodsAmt;
	}
	
}
