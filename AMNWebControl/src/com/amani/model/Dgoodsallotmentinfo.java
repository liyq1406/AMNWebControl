package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dgoodsallotmentinfo  implements java.io.Serializable {


	private DgoodsallotmentinfoId id;
	private String allotmentgoodsno;	
	private String allotmentgoodsname;
	private String allotmentgoodsunit ;

    private BigDecimal allotmentgoodscount;
    private BigDecimal allotmentgoodsprice;	
    private BigDecimal allotmentgoodsamt;	

    private BigDecimal headstockcount;
    private String supplierno;
    private String headwareno;
    private Integer goodssource;
	public DgoodsallotmentinfoId getId() {
		return id;
	}
	public void setId(DgoodsallotmentinfoId id) {
		this.id = id;
	}
	public String getAllotmentgoodsno() {
		return allotmentgoodsno;
	}
	public void setAllotmentgoodsno(String allotmentgoodsno) {
		this.allotmentgoodsno = allotmentgoodsno;
	}
	public String getAllotmentgoodsname() {
		return allotmentgoodsname;
	}
	public void setAllotmentgoodsname(String allotmentgoodsname) {
		this.allotmentgoodsname = allotmentgoodsname;
	}
	public String getAllotmentgoodsunit() {
		return allotmentgoodsunit;
	}
	public void setAllotmentgoodsunit(String allotmentgoodsunit) {
		this.allotmentgoodsunit = allotmentgoodsunit;
	}
	public BigDecimal getAllotmentgoodscount() {
		return allotmentgoodscount;
	}
	public void setAllotmentgoodscount(BigDecimal allotmentgoodscount) {
		this.allotmentgoodscount = allotmentgoodscount;
	}
	public BigDecimal getAllotmentgoodsprice() {
		return allotmentgoodsprice;
	}
	public void setAllotmentgoodsprice(BigDecimal allotmentgoodsprice) {
		this.allotmentgoodsprice = allotmentgoodsprice;
	}
	public BigDecimal getAllotmentgoodsamt() {
		return allotmentgoodsamt;
	}
	public void setAllotmentgoodsamt(BigDecimal allotmentgoodsamt) {
		this.allotmentgoodsamt = allotmentgoodsamt;
	}
	public BigDecimal getHeadstockcount() {
		return headstockcount;
	}
	public void setHeadstockcount(BigDecimal headstockcount) {
		this.headstockcount = headstockcount;
	}
	public String getSupplierno() {
		return supplierno;
	}
	public void setSupplierno(String supplierno) {
		this.supplierno = supplierno;
	}
	public String getHeadwareno() {
		return headwareno;
	}
	public void setHeadwareno(String headwareno) {
		this.headwareno = headwareno;
	}
	public Integer getGoodssource() {
		return goodssource;
	}
	public void setGoodssource(Integer goodssource) {
		this.goodssource = goodssource;
	}
    
}