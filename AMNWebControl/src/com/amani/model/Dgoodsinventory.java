package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dgoodsinventory  implements java.io.Serializable {


	private DgoodsinventoryId 	id;
	private String 				inventgoodsno;	
	private String 				inventgoodsname;
	private String 				inventunit;	
    private BigDecimal 			inventcount;
    private BigDecimal 			curstockcount;	
    private BigDecimal 			discount;	
    private String 				inventfrombarno;
    private String 				inventtobarno;
    private Integer 			inventcreateflag;
    private String 				inserunit;	
    private BigDecimal 			inserprice;	
    private String 				outerunit;	
    private BigDecimal 			outerprice;	
	public DgoodsinventoryId getId() {
		return id;
	}
	public void setId(DgoodsinventoryId id) {
		this.id = id;
	}
	public String getInventgoodsno() {
		return inventgoodsno;
	}
	public void setInventgoodsno(String inventgoodsno) {
		this.inventgoodsno = inventgoodsno;
	}
	public String getInventgoodsname() {
		return inventgoodsname;
	}
	public void setInventgoodsname(String inventgoodsname) {
		this.inventgoodsname = inventgoodsname;
	}
	public String getInventunit() {
		return inventunit;
	}
	public void setInventunit(String inventunit) {
		this.inventunit = inventunit;
	}
	public BigDecimal getInventcount() {
		return inventcount;
	}
	public void setInventcount(BigDecimal inventcount) {
		this.inventcount = inventcount;
	}
	public BigDecimal getCurstockcount() {
		return curstockcount;
	}
	public void setCurstockcount(BigDecimal curstockcount) {
		this.curstockcount = curstockcount;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public String getInventfrombarno() {
		return inventfrombarno;
	}
	public void setInventfrombarno(String inventfrombarno) {
		this.inventfrombarno = inventfrombarno;
	}
	public String getInventtobarno() {
		return inventtobarno;
	}
	public void setInventtobarno(String inventtobarno) {
		this.inventtobarno = inventtobarno;
	}
	public Integer getInventcreateflag() {
		return inventcreateflag;
	}
	public void setInventcreateflag(Integer inventcreateflag) {
		this.inventcreateflag = inventcreateflag;
	}
	public String getInserunit() {
		return inserunit;
	}
	public void setInserunit(String inserunit) {
		this.inserunit = inserunit;
	}
	public BigDecimal getInserprice() {
		return inserprice;
	}
	public void setInserprice(BigDecimal inserprice) {
		this.inserprice = inserprice;
	}
	public String getOuterunit() {
		return outerunit;
	}
	public void setOuterunit(String outerunit) {
		this.outerunit = outerunit;
	}
	public BigDecimal getOuterprice() {
		return outerprice;
	}
	public void setOuterprice(BigDecimal outerprice) {
		this.outerprice = outerprice;
	}	
}