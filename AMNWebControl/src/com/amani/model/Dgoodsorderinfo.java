package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dgoodsorderinfo  implements java.io.Serializable {


	private DgoodsorderinfoId id;
	private String ordergoodsno;	
	private String ordergoodsname;
	private String ordergoodsunit ;

	private String goodspricetype;
    private BigDecimal ordergoodscount;
    private BigDecimal ordergoodsprice;	
    private BigDecimal ordergoodsamt;	

    private BigDecimal headstockcount;
    private BigDecimal downordercount;
    private BigDecimal downorderamt;	
    private BigDecimal nodowncount;	
    private BigDecimal norevicecount;	
    private String supplierno;
    private String headwareno;
    private Integer goodssource;
    private Integer ordergoodstype;
    private String strSendBillId;
    private String ordermark;
    private String producenorm;
	public String getProducenorm() {
		return producenorm;
	}
	public void setProducenorm(String producenorm) {
		this.producenorm = producenorm;
	}
	public String getOrdermark() {
		return ordermark;
	}
	public void setOrdermark(String ordermark) {
		this.ordermark = ordermark;
	}
	public DgoodsorderinfoId getId() {
		return id;
	}
	public void setId(DgoodsorderinfoId id) {
		this.id = id;
	}
	public String getOrdergoodsno() {
		return ordergoodsno;
	}
	public void setOrdergoodsno(String ordergoodsno) {
		this.ordergoodsno = ordergoodsno;
	}
	public String getOrdergoodsname() {
		return ordergoodsname;
	}
	public void setOrdergoodsname(String ordergoodsname) {
		this.ordergoodsname = ordergoodsname;
	}
	public String getOrdergoodsunit() {
		return ordergoodsunit;
	}
	public void setOrdergoodsunit(String ordergoodsunit) {
		this.ordergoodsunit = ordergoodsunit;
	}
	public BigDecimal getOrdergoodscount() {
		return ordergoodscount;
	}
	public void setOrdergoodscount(BigDecimal ordergoodscount) {
		this.ordergoodscount = ordergoodscount;
	}
	public BigDecimal getOrdergoodsprice() {
		return ordergoodsprice;
	}
	public void setOrdergoodsprice(BigDecimal ordergoodsprice) {
		this.ordergoodsprice = ordergoodsprice;
	}
	public BigDecimal getOrdergoodsamt() {
		return ordergoodsamt;
	}
	public void setOrdergoodsamt(BigDecimal ordergoodsamt) {
		this.ordergoodsamt = ordergoodsamt;
	}
	public BigDecimal getHeadstockcount() {
		return headstockcount;
	}
	public void setHeadstockcount(BigDecimal headstockcount) {
		this.headstockcount = headstockcount;
	}
	public BigDecimal getDownordercount() {
		return downordercount;
	}
	public void setDownordercount(BigDecimal downordercount) {
		this.downordercount = downordercount;
	}
	public BigDecimal getDownorderamt() {
		return downorderamt;
	}
	public void setDownorderamt(BigDecimal downorderamt) {
		this.downorderamt = downorderamt;
	}
	public BigDecimal getNodowncount() {
		return nodowncount;
	}
	public void setNodowncount(BigDecimal nodowncount) {
		this.nodowncount = nodowncount;
	}
	public BigDecimal getNorevicecount() {
		return norevicecount;
	}
	public void setNorevicecount(BigDecimal norevicecount) {
		this.norevicecount = norevicecount;
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
	public Integer getOrdergoodstype() {
		return ordergoodstype;
	}
	public void setOrdergoodstype(Integer ordergoodstype) {
		this.ordergoodstype = ordergoodstype;
	}
	public String getStrSendBillId() {
		return strSendBillId;
	}
	public void setStrSendBillId(String strSendBillId) {
		this.strSendBillId = strSendBillId;
	}
	public String getGoodspricetype() {
		return goodspricetype;
	}
	public void setGoodspricetype(String goodspricetype) {
		this.goodspricetype = goodspricetype;
	}
    
}