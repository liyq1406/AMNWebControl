package com.amani.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 消费记录表
 */
@Entity
@Table(name="consumepayment")
public class Consumepayment implements java.io.Serializable{
	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; //流水编号
	private String cscompid;		//--公司编号
	private String csbillid;	//--消费单号
	private String scantradeno;  //--扫码订单号
	private String paydate;		//	--操作日期
	private String paytime;		//	--操作时间
	private Integer scanpaytype; //  --扫码支付类型1.支付宝 2.微信
	private BigDecimal payamt;		//	--扫码支付金额
	private Integer paytype;     //	--操作类型 1,支付，2撤销，3退款
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCscompid() {
		return cscompid;
	}
	public void setCscompid(String cscompid) {
		this.cscompid = cscompid;
	}
	public String getCsbillid() {
		return csbillid;
	}
	public void setCsbillid(String csbillid) {
		this.csbillid = csbillid;
	}
	public String getScantradeno() {
		return scantradeno;
	}
	public void setScantradeno(String scantradeno) {
		this.scantradeno = scantradeno;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public Integer getScanpaytype() {
		return scanpaytype;
	}
	public void setScanpaytype(Integer scanpaytype) {
		this.scanpaytype = scanpaytype;
	}
	public BigDecimal getPayamt() {
		return payamt;
	}
	public void setPayamt(BigDecimal payamt) {
		this.payamt = payamt;
	}
	public Integer getPaytype() {
		return paytype;
	}
	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}
}
