package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dcardchangeinfo  implements java.io.Serializable {


	private DcardchangeinfoId id;
	private String oldcardtype;
	private String oldcardname;
    private String boldcardno;
    private String oldcardtypename;
	private BigDecimal curaccountkeepamt;	
	private BigDecimal curaccountdebtamt;
	private BigDecimal proaccountkeepamt;
	private BigDecimal proaccountdebtamt;
	private BigDecimal totalaccountdebtamt;
	private BigDecimal totalaccountkeepamt;

	public DcardchangeinfoId getId() {
		return id;
	}
	public void setId(DcardchangeinfoId id) {
		this.id = id;
	}
	public String getOldcardtype() {
		return oldcardtype;
	}
	public void setOldcardtype(String oldcardtype) {
		this.oldcardtype = oldcardtype;
	}
	public String getOldcardname() {
		return oldcardname;
	}
	public void setOldcardname(String oldcardname) {
		this.oldcardname = oldcardname;
	}
	public BigDecimal getCuraccountkeepamt() {
		return curaccountkeepamt;
	}
	public void setCuraccountkeepamt(BigDecimal curaccountkeepamt) {
		this.curaccountkeepamt = curaccountkeepamt;
	}
	public BigDecimal getCuraccountdebtamt() {
		return curaccountdebtamt;
	}
	public void setCuraccountdebtamt(BigDecimal curaccountdebtamt) {
		this.curaccountdebtamt = curaccountdebtamt;
	}
	public BigDecimal getProaccountkeepamt() {
		return proaccountkeepamt;
	}
	public void setProaccountkeepamt(BigDecimal proaccountkeepamt) {
		this.proaccountkeepamt = proaccountkeepamt;
	}
	public BigDecimal getProaccountdebtamt() {
		return proaccountdebtamt;
	}
	public void setProaccountdebtamt(BigDecimal proaccountdebtamt) {
		this.proaccountdebtamt = proaccountdebtamt;
	}
	public String getBoldcardno() {
		return boldcardno;
	}
	public void setBoldcardno(String boldcardno) {
		this.boldcardno = boldcardno;
	}
	public String getOldcardtypename() {
		return oldcardtypename;
	}
	public void setOldcardtypename(String oldcardtypename) {
		this.oldcardtypename = oldcardtypename;
	}
	public BigDecimal getTotalaccountdebtamt() {
		return totalaccountdebtamt;
	}
	public void setTotalaccountdebtamt(BigDecimal totalaccountdebtamt) {
		this.totalaccountdebtamt = totalaccountdebtamt;
	}
	public BigDecimal getTotalaccountkeepamt() {
		return totalaccountkeepamt;
	}
	public void setTotalaccountkeepamt(BigDecimal totalaccountkeepamt) {
		this.totalaccountkeepamt = totalaccountkeepamt;
	}
	

    }