package com.amani.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 活动赠送明细
 */
@Entity
@Table(name="dactivitygive")
public class Dactivitygive  implements Serializable{
	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 				//流水编号
	private String activcompid;  	//门店编号
	private String activinid;		//活动唯一编号
	private Integer activtype;		//赠送类型1:套餐A、2:套餐B、3:产品C
	private String activno;			//项目\产品编号
	private String activname;		//项目\产品名称
	private BigDecimal onecountprice;//单次金额 
	private Integer givecount;		//赠送次数
	private BigDecimal givetotal;	//赠送合计
	private Integer takeway;			//提成方式1:正常提成、2：设定提成、3：原价卡提
	private BigDecimal takeamt;		//提成金额
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
	public String getActivno() {
		return activno;
	}
	public void setActivno(String activno) {
		this.activno = activno;
	}
	public String getActivname() {
		return activname;
	}
	public void setActivname(String activname) {
		this.activname = activname;
	}
	public BigDecimal getOnecountprice() {
		return onecountprice;
	}
	public void setOnecountprice(BigDecimal onecountprice) {
		this.onecountprice = onecountprice;
	}
	public Integer getGivecount() {
		return givecount;
	}
	public void setGivecount(Integer givecount) {
		this.givecount = givecount;
	}
	public BigDecimal getGivetotal() {
		return givetotal;
	}
	public void setGivetotal(BigDecimal givetotal) {
		this.givetotal = givetotal;
	}
	public Integer getTakeway() {
		return takeway;
	}
	public void setTakeway(Integer takeway) {
		this.takeway = takeway;
	}
	public BigDecimal getTakeamt() {
		return takeamt;
	}
	public void setTakeamt(BigDecimal takeamt) {
		this.takeamt = takeamt;
	}
}
