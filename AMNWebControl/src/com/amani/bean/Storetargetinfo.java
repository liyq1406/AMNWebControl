package com.amani.bean;

import java.math.BigDecimal;

public class Storetargetinfo {
	private 	String			entrycompid;
	private 	String			entrybillid;
	private 	String 			compid;
	private 	String 			compname;
	private 	String 			targetmonth;
	private		BigDecimal	 	ttotalyeji;
	private		BigDecimal	 	trealtotalyeji;
	private		BigDecimal	 	tbeatyyeji;
	private		BigDecimal	 	ttrhyeji;
	private		BigDecimal	 	tcostcount;
	private		BigDecimal	 	tstaffleavelcount;
	private 	int				targetflag;
	private		String			operationer;
	private 	String			operationdate;
	private 	String			checkempid;
	private		String			checkdate;

	public String getEntrycompid() {
		return entrycompid;
	}
	public void setEntrycompid(String entrycompid) {
		this.entrycompid = entrycompid;
	}
	public String getEntrybillid() {
		return entrybillid;
	}
	public void setEntrybillid(String entrybillid) {
		this.entrybillid = entrybillid;
	}
	public String getCompid() {
		return compid;
	}
	public void setCompid(String compid) {
		this.compid = compid;
	}
	public String getTargetmonth() {
		return targetmonth;
	}
	public void setTargetmonth(String targetmonth) {
		this.targetmonth = targetmonth;
	}
	public BigDecimal getTtotalyeji() {
		return ttotalyeji;
	}
	public void setTtotalyeji(BigDecimal ttotalyeji) {
		this.ttotalyeji = ttotalyeji;
	}
	public BigDecimal getTrealtotalyeji() {
		return trealtotalyeji;
	}
	public void setTrealtotalyeji(BigDecimal trealtotalyeji) {
		this.trealtotalyeji = trealtotalyeji;
	}
	public BigDecimal getTtrhyeji() {
		return ttrhyeji;
	}
	public void setTtrhyeji(BigDecimal ttrhyeji) {
		this.ttrhyeji = ttrhyeji;
	}
	public BigDecimal getTcostcount() {
		return tcostcount;
	}
	public void setTcostcount(BigDecimal tcostcount) {
		this.tcostcount = tcostcount;
	}
	public BigDecimal getTstaffleavelcount() {
		return tstaffleavelcount;
	}
	public void setTstaffleavelcount(BigDecimal tstaffleavelcount) {
		this.tstaffleavelcount = tstaffleavelcount;
	}
	public int getTargetflag() {
		return targetflag;
	}
	public void setTargetflag(int targetflag) {
		this.targetflag = targetflag;
	}
	public String getOperationer() {
		return operationer;
	}
	public void setOperationer(String operationer) {
		this.operationer = operationer;
	}
	public String getOperationdate() {
		return operationdate;
	}
	public void setOperationdate(String operationdate) {
		this.operationdate = operationdate;
	}
	public String getCheckempid() {
		return checkempid;
	}
	public void setCheckempid(String checkempid) {
		this.checkempid = checkempid;
	}
	public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public String getCompname() {
		return compname;
	}
	public void setCompname(String compname) {
		this.compname = compname;
	}
	public BigDecimal getTbeatyyeji() {
		return tbeatyyeji;
	}
	public void setTbeatyyeji(BigDecimal tbeatyyeji) {
		this.tbeatyyeji = tbeatyyeji;
	}
}
