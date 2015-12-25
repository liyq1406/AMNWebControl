package com.amani.model;

public class MgoodInfo {
	private String ordercompid;
	private String orderbillid;
	private String compName;
	private String orderdate;
	private String senddate;
	
	private String compaddress;
	private String compphone;
	private String orderstaffid;
	private String orderstaffname;
	private String billinginfo;
	
	
	
	public MgoodInfo(String ordercompid, String orderbillid, String compName,
			String orderdate,String senddate, String compaddress, String compphone,
			String orderstaffid, String orderstaffname, String billinginfo) {
		super();
		this.ordercompid = ordercompid;
		this.orderbillid = orderbillid;
		this.compName = compName;
		this.orderdate = orderdate;
		this.senddate = senddate;
		this.compaddress = compaddress;
		this.compphone = compphone;
		this.orderstaffid = orderstaffid;
		this.orderstaffname = orderstaffname;
		this.billinginfo = billinginfo;
	}

	public MgoodInfo() {
		super();
	}
	
	public String getOrdercompid() {
		return ordercompid;
	}
	public void setOrdercompid(String ordercompid) {
		this.ordercompid = ordercompid;
	}
	public String getOrderbillid() {
		return orderbillid;
	}
	public void setOrderbillid(String orderbillid) {
		this.orderbillid = orderbillid;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getCompaddress() {
		return compaddress;
	}
	public void setCompaddress(String compaddress) {
		this.compaddress = compaddress;
	}
	public String getCompphone() {
		return compphone;
	}
	public void setCompphone(String compphone) {
		this.compphone = compphone;
	}
	public String getOrderstaffid() {
		return orderstaffid;
	}
	public void setOrderstaffid(String orderstaffid) {
		this.orderstaffid = orderstaffid;
	}
	public String getOrderstaffname() {
		return orderstaffname;
	}
	public void setOrderstaffname(String orderstaffname) {
		this.orderstaffname = orderstaffname;
	}
	public String getBillinginfo() {
		return billinginfo;
	}
	public void setBillinginfo(String billinginfo) {
		this.billinginfo = billinginfo;
	}
	public String getSenddate() {
		return senddate;
	}
	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}
	
}
