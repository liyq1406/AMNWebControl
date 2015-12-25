package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mgoodsinsert  implements java.io.Serializable {


	private MgoodsinsertId id;
	private String inserdate;	
	private String insertime;	
    private String inserwareid;
    private String inserstaffid;
    private String inserstaffname;
    private Integer insertype;
    private Integer checkbillflag;	
    private String checkbillno;	
    private String storesendbill;	
    private String exitstoreno;	
    private String exidbillno;
    private Integer billflag;
    private String inseropationerid;
    private String inseropationdate;

    private String binsercompid;
    private String binsercompname;
    private String binserbillid;
    private Integer invalid;
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	public String getBinsercompid() {
		return binsercompid;
	}
	public void setBinsercompid(String binsercompid) {
		this.binsercompid = binsercompid;
	}
	public String getBinserbillid() {
		return binserbillid;
	}
	public void setBinserbillid(String binserbillid) {
		this.binserbillid = binserbillid;
	}
	public MgoodsinsertId getId() {
		return id;
	}
	public void setId(MgoodsinsertId id) {
		this.id = id;
	}
	public String getInserdate() {
		return inserdate;
	}
	public void setInserdate(String inserdate) {
		this.inserdate = inserdate;
	}
	public String getInsertime() {
		return insertime;
	}
	public void setInsertime(String insertime) {
		this.insertime = insertime;
	}
	public String getInserwareid() {
		return inserwareid;
	}
	public void setInserwareid(String inserwareid) {
		this.inserwareid = inserwareid;
	}
	public String getInserstaffid() {
		return inserstaffid;
	}
	public void setInserstaffid(String inserstaffid) {
		this.inserstaffid = inserstaffid;
	}
	public Integer getInsertype() {
		return insertype;
	}
	public void setInsertype(Integer insertype) {
		this.insertype = insertype;
	}
	public Integer getCheckbillflag() {
		return checkbillflag;
	}
	public void setCheckbillflag(Integer checkbillflag) {
		this.checkbillflag = checkbillflag;
	}
	public String getCheckbillno() {
		return checkbillno;
	}
	public void setCheckbillno(String checkbillno) {
		this.checkbillno = checkbillno;
	}
	public String getStoresendbill() {
		return storesendbill;
	}
	public void setStoresendbill(String storesendbill) {
		this.storesendbill = storesendbill;
	}
	public String getExitstoreno() {
		return exitstoreno;
	}
	public void setExitstoreno(String exitstoreno) {
		this.exitstoreno = exitstoreno;
	}
	public String getExidbillno() {
		return exidbillno;
	}
	public void setExidbillno(String exidbillno) {
		this.exidbillno = exidbillno;
	}
	public Integer getBillflag() {
		return billflag;
	}
	public void setBillflag(Integer billflag) {
		this.billflag = billflag;
	}
	public String getInseropationerid() {
		return inseropationerid;
	}
	public void setInseropationerid(String inseropationerid) {
		this.inseropationerid = inseropationerid;
	}
	public String getInseropationdate() {
		return inseropationdate;
	}
	public void setInseropationdate(String inseropationdate) {
		this.inseropationdate = inseropationdate;
	}
	public String getBinsercompname() {
		return binsercompname;
	}
	public void setBinsercompname(String binsercompname) {
		this.binsercompname = binsercompname;
	}
	public String getInserstaffname() {
		return inserstaffname;
	}
	public void setInserstaffname(String inserstaffname) {
		this.inserstaffname = inserstaffname;
	}
}