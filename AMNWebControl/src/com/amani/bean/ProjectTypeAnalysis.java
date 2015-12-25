package com.amani.bean;

import java.math.BigDecimal;

public class ProjectTypeAnalysis {
	private String strCompId;
	private String strCompName;
	private BigDecimal beautPrjYeji;
	private BigDecimal hairPrjYeji;
	private BigDecimal fingerPrjYeji;
	private BigDecimal beautGoodsYeji;
	private BigDecimal trhPrjYeji;
	private BigDecimal hairGoodsYeji;
	private BigDecimal fingerGoodsYeji;
	private double prjTypesAmt[][];
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}
	public String getStrCompName() {
		return strCompName;
	}
	public void setStrCompName(String strCompName) {
		this.strCompName = strCompName;
	}
	public BigDecimal getBeautPrjYeji() {
		return beautPrjYeji;
	}
	public void setBeautPrjYeji(BigDecimal beautPrjYeji) {
		this.beautPrjYeji = beautPrjYeji;
	}
	public BigDecimal getHairPrjYeji() {
		return hairPrjYeji;
	}
	public void setHairPrjYeji(BigDecimal hairPrjYeji) {
		this.hairPrjYeji = hairPrjYeji;
	}
	public BigDecimal getFingerPrjYeji() {
		return fingerPrjYeji;
	}
	public void setFingerPrjYeji(BigDecimal fingerPrjYeji) {
		this.fingerPrjYeji = fingerPrjYeji;
	}
	public BigDecimal getBeautGoodsYeji() {
		return beautGoodsYeji;
	}
	public void setBeautGoodsYeji(BigDecimal beautGoodsYeji) {
		this.beautGoodsYeji = beautGoodsYeji;
	}
	public BigDecimal getHairGoodsYeji() {
		return hairGoodsYeji;
	}
	public void setHairGoodsYeji(BigDecimal hairGoodsYeji) {
		this.hairGoodsYeji = hairGoodsYeji;
	}
	public BigDecimal getFingerGoodsYeji() {
		return fingerGoodsYeji;
	}
	public void setFingerGoodsYeji(BigDecimal fingerGoodsYeji) {
		this.fingerGoodsYeji = fingerGoodsYeji;
	}
	public double[][] getPrjTypesAmt() {
		return prjTypesAmt;
	}
	public void setPrjTypesAmt(double[][] prjTypesAmt) {
		this.prjTypesAmt = prjTypesAmt;
	}
	public BigDecimal getTrhPrjYeji() {
		return trhPrjYeji;
	}
	public void setTrhPrjYeji(BigDecimal trhPrjYeji) {
		this.trhPrjYeji = trhPrjYeji;
	}

}
