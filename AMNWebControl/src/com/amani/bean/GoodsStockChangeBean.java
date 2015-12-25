package com.amani.bean;

import java.math.BigDecimal;

public class GoodsStockChangeBean {
	private String strGoodsNo;
	private String strGoodsName;
	private String strWareId;
	private String strWareName;
	private String strChangeDate;
	private String strChangeType;
	private String strChangeBillNo;
	private BigDecimal bInserGoodsCount;
	private BigDecimal bOuterGoodsCount;
	private BigDecimal bCurGoodsCount;
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
	public String getStrWareId() {
		return strWareId;
	}
	public void setStrWareId(String strWareId) {
		this.strWareId = strWareId;
	}
	public String getStrWareName() {
		return strWareName;
	}
	public void setStrWareName(String strWareName) {
		this.strWareName = strWareName;
	}
	public String getStrChangeDate() {
		return strChangeDate;
	}
	public void setStrChangeDate(String strChangeDate) {
		this.strChangeDate = strChangeDate;
	}
	public String getStrChangeType() {
		return strChangeType;
	}
	public void setStrChangeType(String strChangeType) {
		this.strChangeType = strChangeType;
	}
	public String getStrChangeBillNo() {
		return strChangeBillNo;
	}
	public void setStrChangeBillNo(String strChangeBillNo) {
		this.strChangeBillNo = strChangeBillNo;
	}
	public BigDecimal getBInserGoodsCount() {
		return bInserGoodsCount;
	}
	public void setBInserGoodsCount(BigDecimal inserGoodsCount) {
		bInserGoodsCount = inserGoodsCount;
	}
	public BigDecimal getBOuterGoodsCount() {
		return bOuterGoodsCount;
	}
	public void setBOuterGoodsCount(BigDecimal outerGoodsCount) {
		bOuterGoodsCount = outerGoodsCount;
	}
	public BigDecimal getBCurGoodsCount() {
		return bCurGoodsCount;
	}
	public void setBCurGoodsCount(BigDecimal curGoodsCount) {
		bCurGoodsCount = curGoodsCount;
	}
	
}
