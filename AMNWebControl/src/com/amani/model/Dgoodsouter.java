package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dgoodsouter  implements java.io.Serializable {


	private String goodspricetype;
	private DgoodsouterId id;
	private String outergoodsno;	
	private String outergoodsname;
	
    private String standunit;
    private BigDecimal standprice;	
    private BigDecimal curgoodsstock;
    
	private String outerunit;	
    private BigDecimal outercount;
    private BigDecimal outerprice;	
    private BigDecimal outeramt;
    private BigDecimal outerrate;
    private String frombarcode;
    private String tobarcode;
    
	public String getGoodspricetype() {
		return goodspricetype;
	}
	public void setGoodspricetype(String goodspricetype) {
		this.goodspricetype = goodspricetype;
	}
	public DgoodsouterId getId() {
		return id;
	}
	public void setId(DgoodsouterId id) {
		this.id = id;
	}
	public String getOutergoodsno() {
		return outergoodsno;
	}
	public void setOutergoodsno(String outergoodsno) {
		this.outergoodsno = outergoodsno;
	}
	public String getOutergoodsname() {
		return outergoodsname;
	}
	public void setOutergoodsname(String outergoodsname) {
		this.outergoodsname = outergoodsname;
	}
	public String getStandunit() {
		return standunit;
	}
	public void setStandunit(String standunit) {
		this.standunit = standunit;
	}
	public BigDecimal getStandprice() {
		return standprice;
	}
	public void setStandprice(BigDecimal standprice) {
		this.standprice = standprice;
	}
	public BigDecimal getCurgoodsstock() {
		return curgoodsstock;
	}
	public void setCurgoodsstock(BigDecimal curgoodsstock) {
		this.curgoodsstock = curgoodsstock;
	}
	public String getOuterunit() {
		return outerunit;
	}
	public void setOuterunit(String outerunit) {
		this.outerunit = outerunit;
	}
	public BigDecimal getOutercount() {
		return outercount;
	}
	public void setOutercount(BigDecimal outercount) {
		this.outercount = outercount;
	}
	public BigDecimal getOuterprice() {
		return outerprice;
	}
	public void setOuterprice(BigDecimal outerprice) {
		this.outerprice = outerprice;
	}
	public BigDecimal getOuteramt() {
		return outeramt;
	}
	public void setOuteramt(BigDecimal outeramt) {
		this.outeramt = outeramt;
	}
	public BigDecimal getOuterrate() {
		return outerrate;
	}
	public void setOuterrate(BigDecimal outerrate) {
		this.outerrate = outerrate;
	}
	public String getFrombarcode() {
		return frombarcode;
	}
	public void setFrombarcode(String frombarcode) {
		this.frombarcode = frombarcode;
	}
	public String getTobarcode() {
		return tobarcode;
	}
	public void setTobarcode(String tobarcode) {
		this.tobarcode = tobarcode;
	}
	
}