package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mgoodsorderinfo  implements java.io.Serializable {


	private MgoodsorderinfoId id;
	private String orderdate;	
	private String ordertime;	
    private String orderstaffid;
    private String orderstaffname;
    private Integer orderstate;
    private String downordercompid;
    private String downorderstaffid;
    private String downorderstaffname;
    private String downorderdate;
    private String downordertime;	
    private String sendbillno;	
    private String revicebillno;	
    private Integer ordersource;
    private String storewareid;
    private String storewarename;
    private String headwareid;
    private String headwarename;
    private Integer orderbilltype;
    private String orderopationerid;
    private String orderopationdate;
    private String bordercompid;
    private String bordercompname;
    private String borderbillid;
    private Integer invalid;
    private String arrivaldate;
	public MgoodsorderinfoId getId() {
		return id;
	}
	public void setId(MgoodsorderinfoId id) {
		this.id = id;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public String getOrderstaffid() {
		return orderstaffid;
	}
	public void setOrderstaffid(String orderstaffid) {
		this.orderstaffid = orderstaffid;
	}

	public Integer getOrderstate() {
		return orderstate;
	}
	public void setOrderstate(Integer orderstate) {
		this.orderstate = orderstate;
	}
	public String getDownorderstaffid() {
		return downorderstaffid;
	}
	public void setDownorderstaffid(String downorderstaffid) {
		this.downorderstaffid = downorderstaffid;
	}
	public String getDownorderdate() {
		return downorderdate;
	}
	public void setDownorderdate(String downorderdate) {
		this.downorderdate = downorderdate;
	}
	public String getDownordertime() {
		return downordertime;
	}
	public void setDownordertime(String downordertime) {
		this.downordertime = downordertime;
	}
	public String getSendbillno() {
		return sendbillno;
	}
	public void setSendbillno(String sendbillno) {
		this.sendbillno = sendbillno;
	}
	public String getRevicebillno() {
		return revicebillno;
	}
	public void setRevicebillno(String revicebillno) {
		this.revicebillno = revicebillno;
	}
	public Integer getOrdersource() {
		return ordersource;
	}
	public void setOrdersource(Integer ordersource) {
		this.ordersource = ordersource;
	}
	public String getStorewareid() {
		return storewareid;
	}
	public void setStorewareid(String storewareid) {
		this.storewareid = storewareid;
	}
	public String getHeadwareid() {
		return headwareid;
	}
	public void setHeadwareid(String headwareid) {
		this.headwareid = headwareid;
	}
	public Integer getOrderbilltype() {
		return orderbilltype;
	}
	public void setOrderbilltype(Integer orderbilltype) {
		this.orderbilltype = orderbilltype;
	}
	public String getOrderopationerid() {
		return orderopationerid;
	}
	public void setOrderopationerid(String orderopationerid) {
		this.orderopationerid = orderopationerid;
	}
	public String getOrderopationdate() {
		return orderopationdate;
	}
	public void setOrderopationdate(String orderopationdate) {
		this.orderopationdate = orderopationdate;
	}
	public String getBordercompid() {
		return bordercompid;
	}
	public void setBordercompid(String bordercompid) {
		this.bordercompid = bordercompid;
	}
	public String getBordercompname() {
		return bordercompname;
	}
	public void setBordercompname(String bordercompname) {
		this.bordercompname = bordercompname;
	}
	public String getBorderbillid() {
		return borderbillid;
	}
	public void setBorderbillid(String borderbillid) {
		this.borderbillid = borderbillid;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	public String getOrderstaffname() {
		return orderstaffname;
	}
	public void setOrderstaffname(String orderstaffname) {
		this.orderstaffname = orderstaffname;
	}
	public String getStorewarename() {
		return storewarename;
	}
	public void setStorewarename(String storewarename) {
		this.storewarename = storewarename;
	}
	public String getDownordercompid() {
		return downordercompid;
	}
	public void setDownordercompid(String downordercompid) {
		this.downordercompid = downordercompid;
	}
	public String getDownorderstaffname() {
		return downorderstaffname;
	}
	public void setDownorderstaffname(String downorderstaffname) {
		this.downorderstaffname = downorderstaffname;
	}
	public String getHeadwarename() {
		return headwarename;
	}
	public void setHeadwarename(String headwarename) {
		this.headwarename = headwarename;
	}
	public String getArrivaldate() {
		return arrivaldate;
	}
	public void setArrivaldate(String arrivaldate) {
		this.arrivaldate = arrivaldate;
	}
}