package com.amani.model;

/**
 * Orders entity. @author MyEclipse Persistence Tools
 */

public class Orders implements java.io.Serializable {

	// Fields

	private String ordersid;
	private String calluserid;
	private String callbillid;
	private String orderphone;
	private String orderconply;
	private String orderusermf;
	private String orderusertrh;
	private String orderusermr;
	private String orderproject;
	private String ordertime;
	private String ordertimes;
	private String orderdetail;
	private String complydetail;
	private Integer orderstate;
	private String orderfactdate;
	private String orderfacttime;
	private String orderconfirmmsg;
	private String confirmdate;
	private String confirmemp;
	private String orderfactproject;
	private String orderfactempid;
	private String openid;
	private String cardno;
	private Integer billtype;
	private String strCompName;
	private String address;

	// Constructors

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStrCompName() {
		return strCompName;
	}

	public void setStrCompName(String strCompName) {
		this.strCompName = strCompName;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public Integer getBilltype() {
		return billtype;
	}

	public void setBilltype(Integer billtype) {
		this.billtype = billtype;
	}

	/** default constructor */
	public Orders() {
	}

	/** minimal constructor */
	public Orders(String ordersid) {
		this.ordersid = ordersid;
	}

	/** full constructor */
	public Orders(String ordersid, String calluserid, String callbillid,
			String orderphone, String orderconply, String orderusermf,
			String orderusertrh, String orderusermr, String orderproject,
			String ordertime, String ordertimes, String orderdetail,
			String complydetail, Integer orderstate, String orderfactdate,
			String orderfacttime, String orderconfirmmsg, String confirmdate,
			String confirmemp, String orderfactproject, String orderfactempid,
			String openid) {
		this.ordersid = ordersid;
		this.calluserid = calluserid;
		this.callbillid = callbillid;
		this.orderphone = orderphone;
		this.orderconply = orderconply;
		this.orderusermf = orderusermf;
		this.orderusertrh = orderusertrh;
		this.orderusermr = orderusermr;
		this.orderproject = orderproject;
		this.ordertime = ordertime;
		this.ordertimes = ordertimes;
		this.orderdetail = orderdetail;
		this.complydetail = complydetail;
		this.orderstate = orderstate;
		this.orderfactdate = orderfactdate;
		this.orderfacttime = orderfacttime;
		this.orderconfirmmsg = orderconfirmmsg;
		this.confirmdate = confirmdate;
		this.confirmemp = confirmemp;
		this.orderfactproject = orderfactproject;
		this.orderfactempid = orderfactempid;
		this.openid = openid;
	}

	// Property accessors

	public String getOrdersid() {
		return this.ordersid;
	}

	public void setOrdersid(String ordersid) {
		this.ordersid = ordersid;
	}

	public String getCalluserid() {
		return this.calluserid;
	}

	public void setCalluserid(String calluserid) {
		this.calluserid = calluserid;
	}

	public String getCallbillid() {
		return this.callbillid;
	}

	public void setCallbillid(String callbillid) {
		this.callbillid = callbillid;
	}

	public String getOrderphone() {
		return this.orderphone;
	}

	public void setOrderphone(String orderphone) {
		this.orderphone = orderphone;
	}

	public String getOrderconply() {
		return this.orderconply;
	}

	public void setOrderconply(String orderconply) {
		this.orderconply = orderconply;
	}

	public String getOrderusermf() {
		return this.orderusermf;
	}

	public void setOrderusermf(String orderusermf) {
		this.orderusermf = orderusermf;
	}

	public String getOrderusertrh() {
		return this.orderusertrh;
	}

	public void setOrderusertrh(String orderusertrh) {
		this.orderusertrh = orderusertrh;
	}

	public String getOrderusermr() {
		return this.orderusermr;
	}

	public void setOrderusermr(String orderusermr) {
		this.orderusermr = orderusermr;
	}

	public String getOrderproject() {
		return this.orderproject;
	}

	public void setOrderproject(String orderproject) {
		this.orderproject = orderproject;
	}

	public String getOrdertime() {
		return this.ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public String getOrdertimes() {
		return this.ordertimes;
	}

	public void setOrdertimes(String ordertimes) {
		this.ordertimes = ordertimes;
	}

	public String getOrderdetail() {
		return this.orderdetail;
	}

	public void setOrderdetail(String orderdetail) {
		this.orderdetail = orderdetail;
	}

	public String getComplydetail() {
		return this.complydetail;
	}

	public void setComplydetail(String complydetail) {
		this.complydetail = complydetail;
	}

	public Integer getOrderstate() {
		return this.orderstate;
	}

	public void setOrderstate(Integer orderstate) {
		this.orderstate = orderstate;
	}

	public String getOrderfactdate() {
		return this.orderfactdate;
	}

	public void setOrderfactdate(String orderfactdate) {
		this.orderfactdate = orderfactdate;
	}

	public String getOrderfacttime() {
		return this.orderfacttime;
	}

	public void setOrderfacttime(String orderfacttime) {
		this.orderfacttime = orderfacttime;
	}

	public String getOrderconfirmmsg() {
		return this.orderconfirmmsg;
	}

	public void setOrderconfirmmsg(String orderconfirmmsg) {
		this.orderconfirmmsg = orderconfirmmsg;
	}

	public String getConfirmdate() {
		return this.confirmdate;
	}

	public void setConfirmdate(String confirmdate) {
		this.confirmdate = confirmdate;
	}

	public String getConfirmemp() {
		return this.confirmemp;
	}

	public void setConfirmemp(String confirmemp) {
		this.confirmemp = confirmemp;
	}

	public String getOrderfactproject() {
		return this.orderfactproject;
	}

	public void setOrderfactproject(String orderfactproject) {
		this.orderfactproject = orderfactproject;
	}

	public String getOrderfactempid() {
		return this.orderfactempid;
	}

	public void setOrderfactempid(String orderfactempid) {
		this.orderfactempid = orderfactempid;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}