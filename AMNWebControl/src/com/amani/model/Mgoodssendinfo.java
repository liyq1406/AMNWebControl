package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mgoodssendinfo  implements java.io.Serializable {


	private MgoodssendinfoId id;
	private String senddate;	
	private String sendtime;	
    private String sendstaffid;
    private String sendstaffname;
    private Integer sendstate;
    private String storewareid;
    private String storewarename;
    private String headwareid;
    private String headwarename;
    private String storestaffid;
    private String storestaffname;
    private String storeaddress;
	private String orderdate;	
	private String ordertime;	
    private String ordercompid;
    private String ordercompname;
    private String orderbill;
    private Integer orderbilltype;
    private String sendopationerid;
    private String sendopationdate;
    private String bsendcompid;
    private String bbsendcompname;
    private String bsendbillid;
    private Integer invalid;
	public MgoodssendinfoId getId() {
		return id;
	}
	public void setId(MgoodssendinfoId id) {
		this.id = id;
	}
	public String getSenddate() {
		return senddate;
	}
	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getSendstaffid() {
		return sendstaffid;
	}
	public void setSendstaffid(String sendstaffid) {
		this.sendstaffid = sendstaffid;
	}
	public String getSendstaffname() {
		return sendstaffname;
	}
	public void setSendstaffname(String sendstaffname) {
		this.sendstaffname = sendstaffname;
	}
	public Integer getSendstate() {
		return sendstate;
	}
	public void setSendstate(Integer sendstate) {
		this.sendstate = sendstate;
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
	public String getOrderbill() {
		return orderbill;
	}
	public void setOrderbill(String orderbill) {
		this.orderbill = orderbill;
	}
	public Integer getOrderbilltype() {
		return orderbilltype;
	}
	public void setOrderbilltype(Integer orderbilltype) {
		this.orderbilltype = orderbilltype;
	}
	public String getSendopationerid() {
		return sendopationerid;
	}
	public void setSendopationerid(String sendopationerid) {
		this.sendopationerid = sendopationerid;
	}
	public String getSendopationdate() {
		return sendopationdate;
	}
	public void setSendopationdate(String sendopationdate) {
		this.sendopationdate = sendopationdate;
	}
	public String getBsendcompid() {
		return bsendcompid;
	}
	public void setBsendcompid(String bsendcompid) {
		this.bsendcompid = bsendcompid;
	}
	public String getBbsendcompname() {
		return bbsendcompname;
	}
	public void setBbsendcompname(String bbsendcompname) {
		this.bbsendcompname = bbsendcompname;
	}
	public String getBsendbillid() {
		return bsendbillid;
	}
	public void setBsendbillid(String bsendbillid) {
		this.bsendbillid = bsendbillid;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	public String getStorestaffid() {
		return storestaffid;
	}
	public void setStorestaffid(String storestaffid) {
		this.storestaffid = storestaffid;
	}
	public String getStoreaddress() {
		return storeaddress;
	}
	public void setStoreaddress(String storeaddress) {
		this.storeaddress = storeaddress;
	}
	public String getOrdercompid() {
		return ordercompid;
	}
	public void setOrdercompid(String ordercompid) {
		this.ordercompid = ordercompid;
	}
	public String getStorewarename() {
		return storewarename;
	}
	public void setStorewarename(String storewarename) {
		this.storewarename = storewarename;
	}
	public String getHeadwarename() {
		return headwarename;
	}
	public void setHeadwarename(String headwarename) {
		this.headwarename = headwarename;
	}
	public String getOrdercompname() {
		return ordercompname;
	}
	public void setOrdercompname(String ordercompname) {
		this.ordercompname = ordercompname;
	}
	public String getStorestaffname() {
		return storestaffname;
	}
	public void setStorestaffname(String storestaffname) {
		this.storestaffname = storestaffname;
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
	
}