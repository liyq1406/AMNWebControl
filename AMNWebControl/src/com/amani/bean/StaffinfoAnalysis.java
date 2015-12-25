package com.amani.bean;

public class StaffinfoAnalysis {
	private String strCompId;
	private String strCompName;
	private String prjTypesAmt[][];
	private String totalCount;
	private String strDate;
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}
	public String getStrCompName() {
		return strCompName;
	}
	public void setStrCompName(String strCompName) {
		this.strCompName = strCompName;
	}
	public String[][] getPrjTypesAmt() {
		return prjTypesAmt;
	}
	public void setPrjTypesAmt(String[][] prjTypesAmt) {
		this.prjTypesAmt = prjTypesAmt;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
}
