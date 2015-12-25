package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dcompallotmentinfo  implements java.io.Serializable {


	private DcompallotmentinfoId id;
	private String allotmentcompno;	
	private String allotmentcompname;
    private String apporderbillno;
    private Boolean checkFlag;
	public Boolean getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(Boolean checkFlag) {
		this.checkFlag = checkFlag;
	}
	public DcompallotmentinfoId getId() {
		return id;
	}
	public void setId(DcompallotmentinfoId id) {
		this.id = id;
	}
	public String getAllotmentcompno() {
		return allotmentcompno;
	}
	public void setAllotmentcompno(String allotmentcompno) {
		this.allotmentcompno = allotmentcompno;
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
	
}