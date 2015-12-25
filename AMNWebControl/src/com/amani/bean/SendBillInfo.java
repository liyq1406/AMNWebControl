package com.amani.bean;

public class SendBillInfo {
		
	private String strCompId;			//门店
	private String strCompName;			//门店名称
	private String strBillId;			//单据编号
	private String strGoodsId;			//产品编号
	private String strGoodsName;		//产品名称
	private double orderGoodsCount;		//订单数量
	private String strGoodsUnit;		//产品单位
	private double orderGoodsPrice;		//产品单价
	private double orderRebate;			//折扣
	private double orderGoodsAmt;		//产品金额
	private double orderAppCount;		//申请发货数量
	private double orderSendCount; 		//实际发货数量
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
	public String getStrGoodsId() {
		return strGoodsId;
	}
	public void setStrGoodsId(String strGoodsId) {
		this.strGoodsId = strGoodsId;
	}
	public String getStrGoodsName() {
		return strGoodsName;
	}
	public void setStrGoodsName(String strGoodsName) {
		this.strGoodsName = strGoodsName;
	}
	public double getOrderGoodsCount() {
		return orderGoodsCount;
	}
	public void setOrderGoodsCount(double orderGoodsCount) {
		this.orderGoodsCount = orderGoodsCount;
	}
	public String getStrGoodsUnit() {
		return strGoodsUnit;
	}
	public void setStrGoodsUnit(String strGoodsUnit) {
		this.strGoodsUnit = strGoodsUnit;
	}
	public double getOrderGoodsPrice() {
		return orderGoodsPrice;
	}
	public void setOrderGoodsPrice(double orderGoodsPrice) {
		this.orderGoodsPrice = orderGoodsPrice;
	}
	public double getOrderRebate() {
		return orderRebate;
	}
	public void setOrderRebate(double orderRebate) {
		this.orderRebate = orderRebate;
	}
	public double getOrderGoodsAmt() {
		return orderGoodsAmt;
	}
	public void setOrderGoodsAmt(double orderGoodsAmt) {
		this.orderGoodsAmt = orderGoodsAmt;
	}
	public double getOrderAppCount() {
		return orderAppCount;
	}
	public void setOrderAppCount(double orderAppCount) {
		this.orderAppCount = orderAppCount;
	}
	public double getOrderSendCount() {
		return orderSendCount;
	}
	public void setOrderSendCount(double orderSendCount) {
		this.orderSendCount = orderSendCount;
	}
	public String getStrBillId() {
		return strBillId;
	}
	public void setStrBillId(String strBillId) {
		this.strBillId = strBillId;
	}
}
