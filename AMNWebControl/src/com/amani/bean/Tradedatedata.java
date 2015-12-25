package com.amani.bean;

import java.math.BigDecimal;

public class Tradedatedata {
	private String tradedate;
	private BigDecimal totalcashamt;
	private BigDecimal staffcashamt;
	private BigDecimal hezuocashamt;
	public String getTradedate() {
		return tradedate;
	}
	public void setTradedate(String tradedate) {
		this.tradedate = tradedate;
	}
	public BigDecimal getTotalcashamt() {
		return totalcashamt;
	}
	public void setTotalcashamt(BigDecimal totalcashamt) {
		this.totalcashamt = totalcashamt;
	}
	public BigDecimal getStaffcashamt() {
		return staffcashamt;
	}
	public void setStaffcashamt(BigDecimal staffcashamt) {
		this.staffcashamt = staffcashamt;
	}
	public BigDecimal getHezuocashamt() {
		return hezuocashamt;
	}
	public void setHezuocashamt(BigDecimal hezuocashamt) {
		this.hezuocashamt = hezuocashamt;
	}
}
