package com.amani.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 活动设定主档
 */
@Entity
@Table(name="mactivityinfo")
public class Mactivityinfo implements Serializable{

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false,updatable=false)
	private Integer id; 		  //活动数字编号
	@Id
	private String activinid;	 //活动唯一编号
	private String activcompid;  //门店编号
	private String activbillid;  //活动编号
	private String activname;	 //活动名称
	private Integer activtype;	 //活动类型1:卡异动、2:疗程、3:产品
	private BigDecimal activamt; //金额
	private Integer activorand;	 //活动逻辑1:或、2:且
	private Integer activcount;	 //套数		
	private String startdate;	 //开始日期
	private String enddate;		 //结束日期
	private Integer activstate=1;	 //活动状态0:停用、1:正常
	@Transient
	private String compname;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getActivinid() {
		return activinid;
	}
	public void setActivinid(String activinid) {
		this.activinid = activinid;
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
	public String getCompname() {
		return compname;
	}
	public void setCompname(String compname) {
		this.compname = compname;
	}
}
