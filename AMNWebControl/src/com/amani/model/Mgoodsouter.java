package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mgoodsouter  implements java.io.Serializable {


	private MgoodsouterId id;
	private String outerdate;	
	private String outertime;	
    private String outerwareid;
    private String outerstaffid;
    private String outerstaffname;
    private Integer outertype;
    private Integer orderbilltype;
    private Integer revicetype;
    private String sendbillid;	

    private Integer billflag;
    private String outeropationerid;
    private String outeropationername;
    private String outeropationdate;

    private String boutercompid;
    private String boutercompname;
    private String bouterbillid;
    private Integer invalid;
	public MgoodsouterId getId() {
		return id;
	}
	public void setId(MgoodsouterId id) {
		this.id = id;
	}
	public String getOuterdate() {
		return outerdate;
	}
	public void setOuterdate(String outerdate) {
		this.outerdate = outerdate;
	}
	public String getOutertime() {
		return outertime;
	}
	public void setOutertime(String outertime) {
		this.outertime = outertime;
	}
	public String getOuterwareid() {
		return outerwareid;
	}
	public void setOuterwareid(String outerwareid) {
		this.outerwareid = outerwareid;
	}
	public String getOuterstaffid() {
		return outerstaffid;
	}
	public void setOuterstaffid(String outerstaffid) {
		this.outerstaffid = outerstaffid;
	}
	public String getOuterstaffname() {
		return outerstaffname;
	}
	public void setOuterstaffname(String outerstaffname) {
		this.outerstaffname = outerstaffname;
	}
	public Integer getOutertype() {
		return outertype;
	}
	public void setOutertype(Integer outertype) {
		this.outertype = outertype;
	}
	public Integer getRevicetype() {
		return revicetype;
	}
	public void setRevicetype(Integer revicetype) {
		this.revicetype = revicetype;
	}
	public String getSendbillid() {
		return sendbillid;
	}
	public void setSendbillid(String sendbillid) {
		this.sendbillid = sendbillid;
	}
	public Integer getBillflag() {
		return billflag;
	}
	public void setBillflag(Integer billflag) {
		this.billflag = billflag;
	}
	public String getOuteropationerid() {
		return outeropationerid;
	}
	public void setOuteropationerid(String outeropationerid) {
		this.outeropationerid = outeropationerid;
	}
	public String getOuteropationdate() {
		return outeropationdate;
	}
	public void setOuteropationdate(String outeropationdate) {
		this.outeropationdate = outeropationdate;
	}
	public String getBoutercompid() {
		return boutercompid;
	}
	public void setBoutercompid(String boutercompid) {
		this.boutercompid = boutercompid;
	}
	public String getBoutercompname() {
		return boutercompname;
	}
	public void setBoutercompname(String boutercompname) {
		this.boutercompname = boutercompname;
	}
	public String getBouterbillid() {
		return bouterbillid;
	}
	public void setBouterbillid(String bouterbillid) {
		this.bouterbillid = bouterbillid;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	public Integer getOrderbilltype() {
		return orderbilltype;
	}
	public void setOrderbilltype(Integer orderbilltype) {
		this.orderbilltype = orderbilltype;
	}
	public String getOuteropationername() {
		return outeropationername;
	}
	public void setOuteropationername(String outeropationername) {
		this.outeropationername = outeropationername;
	}
	
}