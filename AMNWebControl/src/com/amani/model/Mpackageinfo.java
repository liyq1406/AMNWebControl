package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mpackageinfo implements java.io.Serializable {


	private MpackageinfoId id;
	private String packagename;
	private String paceageremark;
	private BigDecimal packageprice;
    private String bcompid;
    private String bpackageno;
    private String usedate;
    private Integer usetype;
    private Integer ratetype;
    private Integer usemonths;
    private Double wage;
    private Integer ishz;
    private Integer salecount;
    
	public Integer getIshz() {
		return ishz;
	}
	public void setIshz(Integer ishz) {
		this.ishz = ishz;
	}
	public Double getWage() {
		return wage;
	}
	public void setWage(Double wage) {
		this.wage = wage;
	}
	public Integer getUsemonths() {
		return usemonths;
	}
	public void setUsemonths(Integer usemonths) {
		this.usemonths = usemonths;
	}
	public Integer getUsetype() {
		return usetype;
	}
	public void setUsetype(Integer usetype) {
		this.usetype = usetype;
	}
	public String getUsedate() {
		return usedate;
	}
	public void setUsedate(String usedate) {
		this.usedate = usedate;
	}
	public String getBcompid() {
		return bcompid;
	}
	public void setBcompid(String bcompid) {
		this.bcompid = bcompid;
	}
	public String getBpackageno() {
		return bpackageno;
	}
	public void setBpackageno(String bpackageno) {
		this.bpackageno = bpackageno;
	}
	public MpackageinfoId getId() {
		return id;
	}
	public void setId(MpackageinfoId id) {
		this.id = id;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public String getPaceageremark() {
		return paceageremark;
	}
	public void setPaceageremark(String paceageremark) {
		this.paceageremark = paceageremark;
	}
	public BigDecimal getPackageprice() {
		return packageprice;
	}
	public void setPackageprice(BigDecimal packageprice) {
		this.packageprice = packageprice;
	}
	public Integer getRatetype() {
		return ratetype;
	}
	public void setRatetype(Integer ratetype) {
		this.ratetype = ratetype;
	}
	public Integer getSalecount() {
		return salecount;
	}
	public void setSalecount(Integer salecount) {
		this.salecount = salecount;
	}
}