package com.amani.model;

public class Projectcostinfo {
	private	String 	strCompId				;//门店编号
	private String  strCompName				;// 门店名称
	private String 	prjno					;// 项目编号 
	private String 	prjname					;// 项目名称 
	private String 	goodsno					;// 产品编号  
	private String 	goodsname				;// 产品名称
	private String 	goodstype				;// 产品类型  
	private String 	goodstypename			;// 类型名称  
	private String 	goodsunit				;// 产品单位  
	private double	costunitcount			;// 标准消耗数量  
	public String getPrjno() {
		return prjno;
	}
	public void setPrjno(String prjno) {
		this.prjno = prjno;
	}
	public String getPrjname() {
		return prjname;
	}
	public void setPrjname(String prjname) {
		this.prjname = prjname;
	}
	public String getGoodsno() {
		return goodsno;
	}
	public void setGoodsno(String goodsno) {
		this.goodsno = goodsno;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}
	public String getGoodstypename() {
		return goodstypename;
	}
	public void setGoodstypename(String goodstypename) {
		this.goodstypename = goodstypename;
	}
	public String getGoodsunit() {
		return goodsunit;
	}
	public void setGoodsunit(String goodsunit) {
		this.goodsunit = goodsunit;
	}
	public double getCostunitcount() {
		return costunitcount;
	}
	public void setCostunitcount(double costunitcount) {
		this.costunitcount = costunitcount;
	}
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
}
