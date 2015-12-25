package com.amani.model;

import java.math.BigDecimal;

public class Dgoodsinsertpc {
	private String insercompid;
	private String insergoodsno;
	private double inserseqno;
	private String inserbillid;
	private String producedate;
	private String expireddate;
	private BigDecimal  curlavecount;
	private String 	exidbillno;
	private String 	inserdate;
	private BigDecimal 	outercount;
	private BigDecimal 	outerprice;
	public String getInsercompid() {
		return insercompid;
	}
	public void setInsercompid(String insercompid) {
		this.insercompid = insercompid;
	}

	public String getInserbillid() {
		return inserbillid;
	}
	public void setInserbillid(String inserbillid) {
		this.inserbillid = inserbillid;
	}
	public String getProducedate() {
		return producedate;
	}
	public void setProducedate(String producedate) {
		this.producedate = producedate;
	}
	public String getExpireddate() {
		return expireddate;
	}
	public void setExpireddate(String expireddate) {
		this.expireddate = expireddate;
	}
	public BigDecimal getCurlavecount() {
		return curlavecount;
	}
	public void setCurlavecount(BigDecimal curlavecount) {
		this.curlavecount = curlavecount;
	}
	public String getExidbillno() {
		return exidbillno;
	}
	public void setExidbillno(String exidbillno) {
		this.exidbillno = exidbillno;
	}
	public String getInserdate() {
		return inserdate;
	}
	public void setInserdate(String inserdate) {
		this.inserdate = inserdate;
	}

	public String getInsergoodsno() {
		return insergoodsno;
	}
	public void setInsergoodsno(String insergoodsno) {
		this.insergoodsno = insergoodsno;
	}
	public BigDecimal getOutercount() {
		return outercount;
	}
	public void setOutercount(BigDecimal outercount) {
		this.outercount = outercount;
	}
	public double getInserseqno() {
		return inserseqno;
	}
	public void setInserseqno(double inserseqno) {
		this.inserseqno = inserseqno;
	}
	public BigDecimal getOuterprice() {
		return outerprice;
	}
	public void setOuterprice(BigDecimal outerprice) {
		this.outerprice = outerprice;
	}
}
