package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mgoodsallotmentinfo  implements java.io.Serializable {


	private MgoodsallotmentinfoId id;
	private String allotmentdate;	
	private String allotmenttime;	
    private String allotmenttaffid;
    private String allotmenttaffanme;
    private String allotmentcompid;
    private String allotmentcompname;
    private String recevicestaffid;
    private String recevicestaffname;
    private String apporderbillno;
    private String orderopationerid;
    private String orderopationdate;
    private String bentrycompid;
    private String bentrycompname;
    private String bentrybillid;
    private Integer allotmenttype;
	public Integer getAllotmenttype() {
		return allotmenttype;
	}
	public void setAllotmenttype(Integer allotmenttype) {
		this.allotmenttype = allotmenttype;
	}
	public MgoodsallotmentinfoId getId() {
		return id;
	}
	public void setId(MgoodsallotmentinfoId id) {
		this.id = id;
	}
	public String getAllotmentdate() {
		return allotmentdate;
	}
	public void setAllotmentdate(String allotmentdate) {
		this.allotmentdate = allotmentdate;
	}
	public String getAllotmenttime() {
		return allotmenttime;
	}
	public void setAllotmenttime(String allotmenttime) {
		this.allotmenttime = allotmenttime;
	}
	public String getAllotmenttaffid() {
		return allotmenttaffid;
	}
	public void setAllotmenttaffid(String allotmenttaffid) {
		this.allotmenttaffid = allotmenttaffid;
	}
	public String getAllotmenttaffanme() {
		return allotmenttaffanme;
	}
	public void setAllotmenttaffanme(String allotmenttaffanme) {
		this.allotmenttaffanme = allotmenttaffanme;
	}
	public String getAllotmentcompid() {
		return allotmentcompid;
	}
	public void setAllotmentcompid(String allotmentcompid) {
		this.allotmentcompid = allotmentcompid;
	}
	public String getAllotmentcompname() {
		return allotmentcompname;
	}
	public void setAllotmentcompname(String allotmentcompname) {
		this.allotmentcompname = allotmentcompname;
	}
	public String getApporderbillno() {
		return apporderbillno;
	}
	public void setApporderbillno(String apporderbillno) {
		this.apporderbillno = apporderbillno;
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
	public String getBentrycompid() {
		return bentrycompid;
	}
	public void setBentrycompid(String bentrycompid) {
		this.bentrycompid = bentrycompid;
	}
	public String getBentrybillid() {
		return bentrybillid;
	}
	public void setBentrybillid(String bentrybillid) {
		this.bentrybillid = bentrybillid;
	}
	public String getBentrycompname() {
		return bentrycompname;
	}
	public void setBentrycompname(String bentrycompname) {
		this.bentrycompname = bentrycompname;
	}
	public String getRecevicestaffid() {
		return recevicestaffid;
	}
	public void setRecevicestaffid(String recevicestaffid) {
		this.recevicestaffid = recevicestaffid;
	}
	public String getRecevicestaffname() {
		return recevicestaffname;
	}
	public void setRecevicestaffname(String recevicestaffname) {
		this.recevicestaffname = recevicestaffname;
	}
}