package com.amani.model;

import java.math.BigDecimal;

public class Cardratetocostrate {
	private String bprojecttypeid;
	private String bprojecttypename;
	private String 	startdate;
	private String 	enddate;
	private BigDecimal costrate;
	private BigDecimal changerate;
	public String getBprojecttypeid() {
		return bprojecttypeid;
	}
	public void setBprojecttypeid(String bprojecttypeid) {
		this.bprojecttypeid = bprojecttypeid;
	}
	public String getBprojecttypename() {
		return bprojecttypename;
	}
	public void setBprojecttypename(String bprojecttypename) {
		this.bprojecttypename = bprojecttypename;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public BigDecimal getCostrate() {
		return costrate;
	}
	public void setCostrate(BigDecimal costrate) {
		this.costrate = costrate;
	}
	public BigDecimal getChangerate() {
		return changerate;
	}
	public void setChangerate(BigDecimal changerate) {
		this.changerate = changerate;
	}
}
