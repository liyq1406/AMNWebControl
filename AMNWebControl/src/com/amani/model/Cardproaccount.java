package com.amani.model;

import java.math.BigDecimal;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Cardproaccount  implements java.io.Serializable {


	private CardproaccountId id;
	private Integer propricetype;	
    private BigDecimal salecount;
    private BigDecimal costcount;	
    private BigDecimal lastcount;	
    private BigDecimal saleamt;	
    private BigDecimal costamt;	
    private BigDecimal lastamt;	
	private String saledate;
	private String cutoffdate;
	private String proremark;
	private Integer prostopeflag;	
	private BigDecimal exchangeseqno;
	private String bcardvesting;
	private String bprojectno;
	private String bprojectname;
	private String changecompid;
	private String changebillid;
	private double bproseqno;
	private String createbilltype;
	private String createbillno;
	private double createseqno;
	private BigDecimal targetlastcount;	
	private Integer yearsz;		//来源类型 1：赠送，0：购买
	private String activinid;	 //赠送活动活动唯一编号
	public Integer getYearsz() {
		return yearsz;
	}
	public void setYearsz(Integer yearsz) {
		this.yearsz = yearsz;
	}
	public double getCreateseqno() {
		return createseqno;
	}
	public void setCreateseqno(double createseqno) {
		this.createseqno = createseqno;
	}
	public double getBproseqno() {
		return bproseqno;
	}
	public void setBproseqno(double bproseqno) {
		this.bproseqno = bproseqno;
	}
	public CardproaccountId getId() {
		return id;
	}
	public void setId(CardproaccountId id) {
		this.id = id;
	}
	public Integer getPropricetype() {
		return propricetype;
	}
	public void setPropricetype(Integer propricetype) {
		this.propricetype = propricetype;
	}
	public BigDecimal getSalecount() {
		return salecount;
	}
	public void setSalecount(BigDecimal salecount) {
		this.salecount = salecount;
	}
	public BigDecimal getCostcount() {
		return costcount;
	}
	public void setCostcount(BigDecimal costcount) {
		this.costcount = costcount;
	}
	public BigDecimal getLastcount() {
		return lastcount;
	}
	public void setLastcount(BigDecimal lastcount) {
		this.lastcount = lastcount;
	}
	public BigDecimal getSaleamt() {
		return saleamt;
	}
	public void setSaleamt(BigDecimal saleamt) {
		this.saleamt = saleamt;
	}
	public BigDecimal getCostamt() {
		return costamt;
	}
	public void setCostamt(BigDecimal costamt) {
		this.costamt = costamt;
	}
	public BigDecimal getLastamt() {
		return lastamt;
	}
	public void setLastamt(BigDecimal lastamt) {
		this.lastamt = lastamt;
	}
	public String getSaledate() {
		return saledate;
	}
	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}
	public String getCutoffdate() {
		return cutoffdate;
	}
	public void setCutoffdate(String cutoffdate) {
		this.cutoffdate = cutoffdate;
	}
	public String getProremark() {
		return proremark;
	}
	public void setProremark(String proremark) {
		this.proremark = proremark;
	}
	public Integer getProstopeflag() {
		return prostopeflag;
	}
	public void setProstopeflag(Integer prostopeflag) {
		this.prostopeflag = prostopeflag;
	}
	public BigDecimal getExchangeseqno() {
		return exchangeseqno;
	}
	public void setExchangeseqno(BigDecimal exchangeseqno) {
		this.exchangeseqno = exchangeseqno;
	}
	public String getBprojectno() {
		return bprojectno;
	}
	public void setBprojectno(String bprojectno) {
		this.bprojectno = bprojectno;
	}
	public String getBprojectname() {
		return bprojectname;
	}
	public void setBprojectname(String bprojectname) {
		this.bprojectname = bprojectname;
	}
	public String getChangecompid() {
		return changecompid;
	}
	public void setChangecompid(String changecompid) {
		this.changecompid = changecompid;
	}
	public String getCreatebilltype() {
		return createbilltype;
	}
	public void setCreatebilltype(String createbilltype) {
		this.createbilltype = createbilltype;
	}
	public String getCreatebillno() {
		return createbillno;
	}
	public void setCreatebillno(String createbillno) {
		this.createbillno = createbillno;
	}
	public String getChangebillid() {
		return changebillid;
	}
	public void setChangebillid(String changebillid) {
		this.changebillid = changebillid;
	}
	public String getBcardvesting() {
		return bcardvesting;
	}
	public void setBcardvesting(String bcardvesting) {
		this.bcardvesting = bcardvesting;
	}
	public BigDecimal getTargetlastcount() {
		return targetlastcount;
	}
	public void setTargetlastcount(BigDecimal targetlastcount) {
		this.targetlastcount = targetlastcount;
	}
	public String getActivinid() {
		return activinid;
	}
	public void setActivinid(String activinid) {
		this.activinid = activinid;
	}	
}