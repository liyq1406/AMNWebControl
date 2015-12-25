package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mgoodsreceipt  implements java.io.Serializable {


	private MgoodsreceiptId id;
	private String receiptdate;	
	private String receipttime;	
	private String receiptwareid;
	private String receiptwarename;
    private String receiptstaffid;
    private String receiptstaffname;
    private Integer receiptstate;
    private String receiptsendbillid;	
    private String receiptorderbillid;	
    private Integer orderbilltype;
    private String receiptopationerid;
    private String receiptopationdate;
    private String breceiptcompid;
    private String breceiptcompname;
    private String breceiptbillid;
	public MgoodsreceiptId getId() {
		return id;
	}
	public void setId(MgoodsreceiptId id) {
		this.id = id;
	}
	public String getReceiptdate() {
		return receiptdate;
	}
	public void setReceiptdate(String receiptdate) {
		this.receiptdate = receiptdate;
	}
	public String getReceipttime() {
		return receipttime;
	}
	public void setReceipttime(String receipttime) {
		this.receipttime = receipttime;
	}
	public String getReceiptwareid() {
		return receiptwareid;
	}
	public void setReceiptwareid(String receiptwareid) {
		this.receiptwareid = receiptwareid;
	}
	public String getReceiptwarename() {
		return receiptwarename;
	}
	public void setReceiptwarename(String receiptwarename) {
		this.receiptwarename = receiptwarename;
	}
	public String getReceiptstaffid() {
		return receiptstaffid;
	}
	public void setReceiptstaffid(String receiptstaffid) {
		this.receiptstaffid = receiptstaffid;
	}
	public String getReceiptstaffname() {
		return receiptstaffname;
	}
	public void setReceiptstaffname(String receiptstaffname) {
		this.receiptstaffname = receiptstaffname;
	}
	public Integer getReceiptstate() {
		return receiptstate;
	}
	public void setReceiptstate(Integer receiptstate) {
		this.receiptstate = receiptstate;
	}
	public String getReceiptsendbillid() {
		return receiptsendbillid;
	}
	public void setReceiptsendbillid(String receiptsendbillid) {
		this.receiptsendbillid = receiptsendbillid;
	}
	public String getReceiptorderbillid() {
		return receiptorderbillid;
	}
	public void setReceiptorderbillid(String receiptorderbillid) {
		this.receiptorderbillid = receiptorderbillid;
	}
	public String getReceiptopationerid() {
		return receiptopationerid;
	}
	public void setReceiptopationerid(String receiptopationerid) {
		this.receiptopationerid = receiptopationerid;
	}
	public String getReceiptopationdate() {
		return receiptopationdate;
	}
	public void setReceiptopationdate(String receiptopationdate) {
		this.receiptopationdate = receiptopationdate;
	}
	public String getBreceiptcompid() {
		return breceiptcompid;
	}
	public void setBreceiptcompid(String breceiptcompid) {
		this.breceiptcompid = breceiptcompid;
	}
	public String getBreceiptcompname() {
		return breceiptcompname;
	}
	public void setBreceiptcompname(String breceiptcompname) {
		this.breceiptcompname = breceiptcompname;
	}
	public String getBreceiptbillid() {
		return breceiptbillid;
	}
	public void setBreceiptbillid(String breceiptbillid) {
		this.breceiptbillid = breceiptbillid;
	}
	public Integer getOrderbilltype() {
		return orderbilltype;
	}
	public void setOrderbilltype(Integer orderbilltype) {
		this.orderbilltype = orderbilltype;
	}

}