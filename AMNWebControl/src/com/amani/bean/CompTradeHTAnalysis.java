package com.amani.bean;

import java.math.BigDecimal;

public class CompTradeHTAnalysis {
	private String strCompId;    //门店编号
	private String strCompName;  //门店名称
	private String strMonth;
	private BigDecimal totalAmt;   //本月总营业额
	private BigDecimal totalAmtA;   //去年同月总营业额
	private BigDecimal totalAmtB;   //上月总营业额
	private BigDecimal totalAmtRateA;   //营业额同比
	private BigDecimal totalAmtRateB;   //营业额环比
	
	private BigDecimal totalFactAmt;   //本月总实业绩
	private BigDecimal totalFactAmtA;   //去年同月总实业绩
	private BigDecimal totalFactAmtB;   //上月总实业绩
	private BigDecimal totalFactAmtRateA;   //实业绩同比
	private BigDecimal totalFactAmtRateB;   //实业绩环比
	
	private BigDecimal saleCardAmt;   //本月总售卡
	private BigDecimal saleCardAmtA;   //去年同月总售卡
	private BigDecimal saleCardAmtB;   //上月总售卡
	private BigDecimal saleCardAmtRateA;   //售卡同比
	private BigDecimal saleCardAmtRateB;   //售卡环比
	
	private BigDecimal pinCardAmt;   //本月总销卡
	private BigDecimal pinCardAmtA;   //去年同月总销卡
	private BigDecimal pinCardAmtB;   //上月总销卡
	private BigDecimal pinCardAmtRateA;   //销卡同比
	private BigDecimal pinCardAmtRateB;   //销卡环比
	
	private BigDecimal buyGoodsAmt;   //本月总买产品
	private BigDecimal buyGoodsAmtA;   //去年同月总买产品
	private BigDecimal buyGoodsAmtB;   //上月买产品业额
	private BigDecimal buyGoodsAmtRateA;   //买产品同比
	private BigDecimal buyGoodsAmtRateB;   //买产品环比
	
	private String totalAmtRateAText;   //营业额同比
	private String totalAmtRateBText;   //营业额环比
	private String totalFactAmtRateAText;   //实业绩同比
	private String totalFactAmtRateBText;   //实业绩环比
	private String saleCardAmtRateAText;   //售卡同比
	private String saleCardAmtRateBText;   //售卡环比
	private String pinCardAmtRateAText;   //销卡同比
	private String pinCardAmtRateBText;   //销卡环比
	private String buyGoodsAmtRateAText;   //买产品同比
	private String buyGoodsAmtRateBText;   //买产品环比
	
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
	public String getStrMonth() {
		return strMonth;
	}
	public void setStrMonth(String strMonth) {
		this.strMonth = strMonth;
	}
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public BigDecimal getTotalAmtA() {
		return totalAmtA;
	}
	public void setTotalAmtA(BigDecimal totalAmtA) {
		this.totalAmtA = totalAmtA;
	}
	public BigDecimal getTotalAmtB() {
		return totalAmtB;
	}
	public void setTotalAmtB(BigDecimal totalAmtB) {
		this.totalAmtB = totalAmtB;
	}
	public BigDecimal getTotalAmtRateA() {
		return totalAmtRateA;
	}
	public void setTotalAmtRateA(BigDecimal totalAmtRateA) {
		this.totalAmtRateA = totalAmtRateA;
	}
	public BigDecimal getTotalAmtRateB() {
		return totalAmtRateB;
	}
	public void setTotalAmtRateB(BigDecimal totalAmtRateB) {
		this.totalAmtRateB = totalAmtRateB;
	}
	public BigDecimal getTotalFactAmt() {
		return totalFactAmt;
	}
	public void setTotalFactAmt(BigDecimal totalFactAmt) {
		this.totalFactAmt = totalFactAmt;
	}
	public BigDecimal getTotalFactAmtA() {
		return totalFactAmtA;
	}
	public void setTotalFactAmtA(BigDecimal totalFactAmtA) {
		this.totalFactAmtA = totalFactAmtA;
	}
	public BigDecimal getTotalFactAmtB() {
		return totalFactAmtB;
	}
	public void setTotalFactAmtB(BigDecimal totalFactAmtB) {
		this.totalFactAmtB = totalFactAmtB;
	}
	public BigDecimal getTotalFactAmtRateA() {
		return totalFactAmtRateA;
	}
	public void setTotalFactAmtRateA(BigDecimal totalFactAmtRateA) {
		this.totalFactAmtRateA = totalFactAmtRateA;
	}
	public BigDecimal getTotalFactAmtRateB() {
		return totalFactAmtRateB;
	}
	public void setTotalFactAmtRateB(BigDecimal totalFactAmtRateB) {
		this.totalFactAmtRateB = totalFactAmtRateB;
	}
	public BigDecimal getSaleCardAmt() {
		return saleCardAmt;
	}
	public void setSaleCardAmt(BigDecimal saleCardAmt) {
		this.saleCardAmt = saleCardAmt;
	}
	public BigDecimal getSaleCardAmtA() {
		return saleCardAmtA;
	}
	public void setSaleCardAmtA(BigDecimal saleCardAmtA) {
		this.saleCardAmtA = saleCardAmtA;
	}
	public BigDecimal getSaleCardAmtB() {
		return saleCardAmtB;
	}
	public void setSaleCardAmtB(BigDecimal saleCardAmtB) {
		this.saleCardAmtB = saleCardAmtB;
	}
	public BigDecimal getSaleCardAmtRateA() {
		return saleCardAmtRateA;
	}
	public void setSaleCardAmtRateA(BigDecimal saleCardAmtRateA) {
		this.saleCardAmtRateA = saleCardAmtRateA;
	}
	public BigDecimal getSaleCardAmtRateB() {
		return saleCardAmtRateB;
	}
	public void setSaleCardAmtRateB(BigDecimal saleCardAmtRateB) {
		this.saleCardAmtRateB = saleCardAmtRateB;
	}
	public BigDecimal getPinCardAmt() {
		return pinCardAmt;
	}
	public void setPinCardAmt(BigDecimal pinCardAmt) {
		this.pinCardAmt = pinCardAmt;
	}
	public BigDecimal getPinCardAmtA() {
		return pinCardAmtA;
	}
	public void setPinCardAmtA(BigDecimal pinCardAmtA) {
		this.pinCardAmtA = pinCardAmtA;
	}
	public BigDecimal getPinCardAmtB() {
		return pinCardAmtB;
	}
	public void setPinCardAmtB(BigDecimal pinCardAmtB) {
		this.pinCardAmtB = pinCardAmtB;
	}
	public BigDecimal getPinCardAmtRateA() {
		return pinCardAmtRateA;
	}
	public void setPinCardAmtRateA(BigDecimal pinCardAmtRateA) {
		this.pinCardAmtRateA = pinCardAmtRateA;
	}
	public BigDecimal getPinCardAmtRateB() {
		return pinCardAmtRateB;
	}
	public void setPinCardAmtRateB(BigDecimal pinCardAmtRateB) {
		this.pinCardAmtRateB = pinCardAmtRateB;
	}
	public BigDecimal getBuyGoodsAmt() {
		return buyGoodsAmt;
	}
	public void setBuyGoodsAmt(BigDecimal buyGoodsAmt) {
		this.buyGoodsAmt = buyGoodsAmt;
	}
	public BigDecimal getBuyGoodsAmtA() {
		return buyGoodsAmtA;
	}
	public void setBuyGoodsAmtA(BigDecimal buyGoodsAmtA) {
		this.buyGoodsAmtA = buyGoodsAmtA;
	}
	public BigDecimal getBuyGoodsAmtB() {
		return buyGoodsAmtB;
	}
	public void setBuyGoodsAmtB(BigDecimal buyGoodsAmtB) {
		this.buyGoodsAmtB = buyGoodsAmtB;
	}
	public BigDecimal getBuyGoodsAmtRateA() {
		return buyGoodsAmtRateA;
	}
	public void setBuyGoodsAmtRateA(BigDecimal buyGoodsAmtRateA) {
		this.buyGoodsAmtRateA = buyGoodsAmtRateA;
	}
	public BigDecimal getBuyGoodsAmtRateB() {
		return buyGoodsAmtRateB;
	}
	public void setBuyGoodsAmtRateB(BigDecimal buyGoodsAmtRateB) {
		this.buyGoodsAmtRateB = buyGoodsAmtRateB;
	}
	public String getTotalAmtRateAText() {
		return totalAmtRateAText;
	}
	public void setTotalAmtRateAText(String totalAmtRateAText) {
		this.totalAmtRateAText = totalAmtRateAText;
	}
	public String getTotalAmtRateBText() {
		return totalAmtRateBText;
	}
	public void setTotalAmtRateBText(String totalAmtRateBText) {
		this.totalAmtRateBText = totalAmtRateBText;
	}
	public String getTotalFactAmtRateAText() {
		return totalFactAmtRateAText;
	}
	public void setTotalFactAmtRateAText(String totalFactAmtRateAText) {
		this.totalFactAmtRateAText = totalFactAmtRateAText;
	}
	public String getTotalFactAmtRateBText() {
		return totalFactAmtRateBText;
	}
	public void setTotalFactAmtRateBText(String totalFactAmtRateBText) {
		this.totalFactAmtRateBText = totalFactAmtRateBText;
	}
	public String getSaleCardAmtRateAText() {
		return saleCardAmtRateAText;
	}
	public void setSaleCardAmtRateAText(String saleCardAmtRateAText) {
		this.saleCardAmtRateAText = saleCardAmtRateAText;
	}
	public String getSaleCardAmtRateBText() {
		return saleCardAmtRateBText;
	}
	public void setSaleCardAmtRateBText(String saleCardAmtRateBText) {
		this.saleCardAmtRateBText = saleCardAmtRateBText;
	}
	public String getPinCardAmtRateAText() {
		return pinCardAmtRateAText;
	}
	public void setPinCardAmtRateAText(String pinCardAmtRateAText) {
		this.pinCardAmtRateAText = pinCardAmtRateAText;
	}
	public String getPinCardAmtRateBText() {
		return pinCardAmtRateBText;
	}
	public void setPinCardAmtRateBText(String pinCardAmtRateBText) {
		this.pinCardAmtRateBText = pinCardAmtRateBText;
	}
	public String getBuyGoodsAmtRateAText() {
		return buyGoodsAmtRateAText;
	}
	public void setBuyGoodsAmtRateAText(String buyGoodsAmtRateAText) {
		this.buyGoodsAmtRateAText = buyGoodsAmtRateAText;
	}
	public String getBuyGoodsAmtRateBText() {
		return buyGoodsAmtRateBText;
	}
	public void setBuyGoodsAmtRateBText(String buyGoodsAmtRateBText) {
		this.buyGoodsAmtRateBText = buyGoodsAmtRateBText;
	}
	
}
