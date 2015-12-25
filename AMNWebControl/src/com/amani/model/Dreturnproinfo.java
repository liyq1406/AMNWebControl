package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dreturnproinfo  implements java.io.Serializable {


	private 	DreturnproinfoId id;
	private 	String  		breturncompid;
	private 	String  		breturnbillid;
	private 	Double  		breturnseqno;
	private 	Integer 		breturnprotype;
    private 	String 			changeproid;
    private 	String 			changeproname;
    private 	BigDecimal 		lastcount;
    private 	BigDecimal 		lastamt;
    private 	BigDecimal 		changeprocount;
    private 	BigDecimal 		changeproamt;
    private 	BigDecimal 		changebyaccountamt;
    private 	String 			firstsalerid;
	private 	BigDecimal 		firstsaleamt;
	private 	String 			firstsalerinid;
	private	 	String 			secondsalerid;
    private 	String 			secondsalerinid;
    private 	BigDecimal 		secondsaleamt;
    private 	String 			changemark;
    private		Integer			changeflag ;
    private 	Boolean 		returnflag;
	
	public Integer getChangeflag() {
		return changeflag;
	}
	public void setChangeflag(Integer changeflag) {
		this.changeflag = changeflag;
	}
	public Boolean getReturnflag() {
		return returnflag;
	}
	public void setReturnflag(Boolean returnflag) {
		this.returnflag = returnflag;
	}
	public DreturnproinfoId getId() {
		return id;
	}
	public void setId(DreturnproinfoId id) {
		this.id = id;
	}
	public String getChangeproid() {
		return changeproid;
	}
	public void setChangeproid(String changeproid) {
		this.changeproid = changeproid;
	}
	public String getChangeproname() {
		return changeproname;
	}
	public void setChangeproname(String changeproname) {
		this.changeproname = changeproname;
	}
	public BigDecimal getChangeprocount() {
		return changeprocount;
	}
	public void setChangeprocount(BigDecimal changeprocount) {
		this.changeprocount = changeprocount;
	}
	public BigDecimal getChangeproamt() {
		return changeproamt;
	}
	public void setChangeproamt(BigDecimal changeproamt) {
		this.changeproamt = changeproamt;
	}
	public BigDecimal getChangebyaccountamt() {
		return changebyaccountamt;
	}
	public void setChangebyaccountamt(BigDecimal changebyaccountamt) {
		this.changebyaccountamt = changebyaccountamt;
	}
	public String getFirstsalerid() {
		return firstsalerid;
	}
	public void setFirstsalerid(String firstsalerid) {
		this.firstsalerid = firstsalerid;
	}
	public BigDecimal getFirstsaleamt() {
		return firstsaleamt;
	}
	public void setFirstsaleamt(BigDecimal firstsaleamt) {
		this.firstsaleamt = firstsaleamt;
	}
	public String getFirstsalerinid() {
		return firstsalerinid;
	}
	public void setFirstsalerinid(String firstsalerinid) {
		this.firstsalerinid = firstsalerinid;
	}
	public String getSecondsalerid() {
		return secondsalerid;
	}
	public void setSecondsalerid(String secondsalerid) {
		this.secondsalerid = secondsalerid;
	}
	public String getSecondsalerinid() {
		return secondsalerinid;
	}
	public void setSecondsalerinid(String secondsalerinid) {
		this.secondsalerinid = secondsalerinid;
	}
	public BigDecimal getSecondsaleamt() {
		return secondsaleamt;
	}
	public void setSecondsaleamt(BigDecimal secondsaleamt) {
		this.secondsaleamt = secondsaleamt;
	}
	public String getChangemark() {
		return changemark;
	}
	public void setChangemark(String changemark) {
		this.changemark = changemark;
	}
	public String getBreturncompid() {
		return breturncompid;
	}
	public void setBreturncompid(String breturncompid) {
		this.breturncompid = breturncompid;
	}
	public String getBreturnbillid() {
		return breturnbillid;
	}
	public void setBreturnbillid(String breturnbillid) {
		this.breturnbillid = breturnbillid;
	}
	public Double getBreturnseqno() {
		return breturnseqno;
	}
	public void setBreturnseqno(Double breturnseqno) {
		this.breturnseqno = breturnseqno;
	}
	public Integer getBreturnprotype() {
		return breturnprotype;
	}
	public void setBreturnprotype(Integer breturnprotype) {
		this.breturnprotype = breturnprotype;
	}
	public BigDecimal getLastcount() {
		return lastcount;
	}
	public void setLastcount(BigDecimal lastcount) {
		this.lastcount = lastcount;
	}
	public BigDecimal getLastamt() {
		return lastamt;
	}
	public void setLastamt(BigDecimal lastamt) {
		this.lastamt = lastamt;
	}
}