package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mpayoutinfo  implements java.io.Serializable {


	private MpayoutinfoId id;
	private String payoutdate;	
	private String payouttime;	
    private String payoutopationerid;
    private String payoutopationdate;
	private String bpayoutcompid;	
	private String bpayoutcompname;	
	private String bpayoutbillid;
	private Integer billstate;
	public Integer getBillstate() {
		return billstate;
	}
	public void setBillstate(Integer billstate) {
		this.billstate = billstate;
	}
	public MpayoutinfoId getId() {
		return id;
	}
	public void setId(MpayoutinfoId id) {
		this.id = id;
	}
	public String getPayoutdate() {
		return payoutdate;
	}
	public void setPayoutdate(String payoutdate) {
		this.payoutdate = payoutdate;
	}
	public String getPayouttime() {
		return payouttime;
	}
	public void setPayouttime(String payouttime) {
		this.payouttime = payouttime;
	}
	public String getPayoutopationerid() {
		return payoutopationerid;
	}
	public void setPayoutopationerid(String payoutopationerid) {
		this.payoutopationerid = payoutopationerid;
	}
	public String getPayoutopationdate() {
		return payoutopationdate;
	}
	public void setPayoutopationdate(String payoutopationdate) {
		this.payoutopationdate = payoutopationdate;
	}
	public String getBpayoutcompid() {
		return bpayoutcompid;
	}
	public void setBpayoutcompid(String bpayoutcompid) {
		this.bpayoutcompid = bpayoutcompid;
	}
	public String getBpayoutcompname() {
		return bpayoutcompname;
	}
	public void setBpayoutcompname(String bpayoutcompname) {
		this.bpayoutcompname = bpayoutcompname;
	}
	public String getBpayoutbillid() {
		return bpayoutbillid;
	}
	public void setBpayoutbillid(String bpayoutbillid) {
		this.bpayoutbillid = bpayoutbillid;
	}
}