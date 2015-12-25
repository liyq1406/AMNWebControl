package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dpayinfo  implements java.io.Serializable {


	private DpayinfoId id;
	private String paymode;
	private String paymodename;
	private String payremark;
	private BigDecimal payamt;
	public DpayinfoId getId() {
		return id;
	}
	public void setId(DpayinfoId id) {
		this.id = id;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	public String getPayremark() {
		return payremark;
	}
	public void setPayremark(String payremark) {
		this.payremark = payremark;
	}
	public BigDecimal getPayamt() {
		return payamt;
	}
	public void setPayamt(BigDecimal payamt) {
		this.payamt = payamt;
	}
	public String getPaymodename() {
		return paymodename;
	}
	public void setPaymodename(String paymodename) {
		this.paymodename = paymodename;
	}
	
}