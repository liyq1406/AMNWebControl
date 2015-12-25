package com.amani.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class BC021Bean implements Serializable{
	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	private String activinid;	 //活动唯一编号
	private String activcompid;  //门店编号
	private String activbillid;  //活动编号
	private String activname;	 //活动名称
	private Integer activtype;	 //活动类型1:卡异动、2:疗程、3:产品--赠送类型1:套餐A、2:套餐B、3:产品C
	private BigDecimal activamt; //金额
	private Integer activorand;	 //活动逻辑1:或、2:且
	private Integer activcount;	 //套数
	private String startdate;	 //开始日期
	private String enddate;		 //结束日期
	private Integer activstate;	 //活动状态0:停用、1:正常
	private String activno;			//项目\产品编号
	private String strCodeno;		//产品查询类型
	private String strCodekey;		//产品查询编号
	private String activJson;		//活动设定疗程或产品JSON
	private String giveAJson;		//赠送A项目
	private String giveBJson;		//赠送B项目
	private String giveCJson;		//赠送C产品
	private Integer giveorand;	 //赠送逻辑1:或、2:且、3:只送产品
	public String getActivinid() {
		return activinid;
	}
	public void setActivinid(String activinid) {
		this.activinid = activinid;
	}
	public String getActivcompid() {
		return activcompid;
	}
	public void setActivcompid(String activcompid) {
		this.activcompid = activcompid;
	}
	public String getActivbillid() {
		return activbillid;
	}
	public void setActivbillid(String activbillid) {
		this.activbillid = activbillid;
	}
	public String getActivname() {
		return activname;
	}
	public void setActivname(String activname) {
		this.activname = activname;
	}
	public Integer getActivtype() {
		return activtype;
	}
	public void setActivtype(Integer activtype) {
		this.activtype = activtype;
	}
	public BigDecimal getActivamt() {
		return activamt;
	}
	public void setActivamt(BigDecimal activamt) {
		this.activamt = activamt;
	}
	public Integer getActivorand() {
		return activorand;
	}
	public void setActivorand(Integer activorand) {
		this.activorand = activorand;
	}
	public Integer getActivcount() {
		return activcount;
	}
	public void setActivcount(Integer activcount) {
		this.activcount = activcount;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public Integer getActivstate() {
		return activstate;
	}
	public void setActivstate(Integer activstate) {
		this.activstate = activstate;
	}
	public String getActivno() {
		return activno;
	}
	public void setActivno(String activno) {
		this.activno = activno;
	}
	public String getStrCodeno() {
		return strCodeno;
	}
	public void setStrCodeno(String strCodeno) {
		this.strCodeno = strCodeno;
	}
	public String getStrCodekey() {
		return strCodekey;
	}
	public void setStrCodekey(String strCodekey) {
		this.strCodekey = strCodekey;
	}
	public String getActivJson() {
		return activJson;
	}
	public void setActivJson(String activJson) {
		this.activJson = activJson;
	}
	public String getGiveAJson() {
		return giveAJson;
	}
	public void setGiveAJson(String giveAJson) {
		this.giveAJson = giveAJson;
	}
	public String getGiveBJson() {
		return giveBJson;
	}
	public void setGiveBJson(String giveBJson) {
		this.giveBJson = giveBJson;
	}
	public String getGiveCJson() {
		return giveCJson;
	}
	public void setGiveCJson(String giveCJson) {
		this.giveCJson = giveCJson;
	}
	public Integer getGiveorand() {
		return giveorand;
	}
	public void setGiveorand(Integer giveorand) {
		this.giveorand = giveorand;
	}
}
