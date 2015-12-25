package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dgoodssendinfo  implements java.io.Serializable {


	private DgoodssendinfoId id;
	private String sendgoodsno;	
	private String sendgoodsname;
	private String sendgoodsunit ;
	private String goodspricetype;
    private BigDecimal ordergoodscount;
    private BigDecimal ordergoodsamt;	

    private BigDecimal downordercount;
    private BigDecimal nodowncount;	
    private BigDecimal sendgoodprice;	
    private BigDecimal sendgoodrate;	
    private BigDecimal sendgoodscount;	
    private BigDecimal sendgoodsamt;	
    private String frombarcode;
    private String tobarcode;
	public DgoodssendinfoId getId() {
		return id;
	}
	public void setId(DgoodssendinfoId id) {
		this.id = id;
	}
	public String getSendgoodsno() {
		return sendgoodsno;
	}
	public void setSendgoodsno(String sendgoodsno) {
		this.sendgoodsno = sendgoodsno;
	}
	public String getSendgoodsname() {
		return sendgoodsname;
	}
	public void setSendgoodsname(String sendgoodsname) {
		this.sendgoodsname = sendgoodsname;
	}
	public String getSendgoodsunit() {
		return sendgoodsunit;
	}
	public void setSendgoodsunit(String sendgoodsunit) {
		this.sendgoodsunit = sendgoodsunit;
	}
	public BigDecimal getOrdergoodscount() {
		return ordergoodscount;
	}
	
	public String getGoodspricetype() {
		return goodspricetype;
	}
	public void setGoodspricetype(String goodspricetype) {
		this.goodspricetype = goodspricetype;
	}
	public void setOrdergoodscount(BigDecimal ordergoodscount) {
		this.ordergoodscount = ordergoodscount;
	}
	public BigDecimal getOrdergoodsamt() {
		return ordergoodsamt;
	}
	public void setOrdergoodsamt(BigDecimal ordergoodsamt) {
		this.ordergoodsamt = ordergoodsamt;
	}
	public BigDecimal getDownordercount() {
		return downordercount;
	}
	public void setDownordercount(BigDecimal downordercount) {
		this.downordercount = downordercount;
	}
	public BigDecimal getNodowncount() {
		return nodowncount;
	}
	public void setNodowncount(BigDecimal nodowncount) {
		this.nodowncount = nodowncount;
	}
	public BigDecimal getSendgoodprice() {
		return sendgoodprice;
	}
	public void setSendgoodprice(BigDecimal sendgoodprice) {
		this.sendgoodprice = sendgoodprice;
	}
	public BigDecimal getSendgoodrate() {
		return sendgoodrate;
	}
	public void setSendgoodrate(BigDecimal sendgoodrate) {
		this.sendgoodrate = sendgoodrate;
	}
	public BigDecimal getSendgoodscount() {
		return sendgoodscount;
	}
	public void setSendgoodscount(BigDecimal sendgoodscount) {
		this.sendgoodscount = sendgoodscount;
	}
	public BigDecimal getSendgoodsamt() {
		return sendgoodsamt;
	}
	public void setSendgoodsamt(BigDecimal sendgoodsamt) {
		this.sendgoodsamt = sendgoodsamt;
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