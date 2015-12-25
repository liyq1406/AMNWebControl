package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dsalecardproinfo  implements java.io.Serializable {


	private DsalecardproinfoId id;
	private String saleproid;
	private String saleproname;
	private Integer saleprotype;
	private BigDecimal saleprocardcount;
	private BigDecimal saleprocount;
	private BigDecimal sendprocount;
	private BigDecimal saleproamt;
	private BigDecimal saleprodebtamt;
	private String procutoffdate;
	private String saleproremark;
	
	private String firthpaymode;
	private BigDecimal firthpayamt;
	
	private String secondpaymode;
	private BigDecimal secondpayamt;
	
	private String thirdpaymode;
	private BigDecimal thirdpayamt;
	
	private String fourthpaymode;
	private BigDecimal fourthpayamt;
	public DsalecardproinfoId getId() {
		return id;
	}
	public void setId(DsalecardproinfoId id) {
		this.id = id;
	}
	public String getSaleproid() {
		return saleproid;
	}
	public void setSaleproid(String saleproid) {
		this.saleproid = saleproid;
	}
	public Integer getSaleprotype() {
		return saleprotype;
	}
	public void setSaleprotype(Integer saleprotype) {
		this.saleprotype = saleprotype;
	}
	public BigDecimal getSaleprocount() {
		return saleprocount;
	}
	public void setSaleprocount(BigDecimal saleprocount) {
		this.saleprocount = saleprocount;
	}
	public BigDecimal getSendprocount() {
		return sendprocount;
	}
	public void setSendprocount(BigDecimal sendprocount) {
		this.sendprocount = sendprocount;
	}
	public BigDecimal getSaleproamt() {
		return saleproamt;
	}
	public void setSaleproamt(BigDecimal saleproamt) {
		this.saleproamt = saleproamt;
	}
	public String getProcutoffdate() {
		return procutoffdate;
	}
	public void setProcutoffdate(String procutoffdate) {
		this.procutoffdate = procutoffdate;
	}
	public String getSaleproremark() {
		return saleproremark;
	}
	public void setSaleproremark(String saleproremark) {
		this.saleproremark = saleproremark;
	}
	public String getFirthpaymode() {
		return firthpaymode;
	}
	public void setFirthpaymode(String firthpaymode) {
		this.firthpaymode = firthpaymode;
	}
	public BigDecimal getFirthpayamt() {
		return firthpayamt;
	}
	public void setFirthpayamt(BigDecimal firthpayamt) {
		this.firthpayamt = firthpayamt;
	}
	public String getSecondpaymode() {
		return secondpaymode;
	}
	public void setSecondpaymode(String secondpaymode) {
		this.secondpaymode = secondpaymode;
	}
	public BigDecimal getSecondpayamt() {
		return secondpayamt;
	}
	public void setSecondpayamt(BigDecimal secondpayamt) {
		this.secondpayamt = secondpayamt;
	}
	public String getThirdpaymode() {
		return thirdpaymode;
	}
	public void setThirdpaymode(String thirdpaymode) {
		this.thirdpaymode = thirdpaymode;
	}
	public BigDecimal getThirdpayamt() {
		return thirdpayamt;
	}
	public void setThirdpayamt(BigDecimal thirdpayamt) {
		this.thirdpayamt = thirdpayamt;
	}
	public String getFourthpaymode() {
		return fourthpaymode;
	}
	public void setFourthpaymode(String fourthpaymode) {
		this.fourthpaymode = fourthpaymode;
	}
	public BigDecimal getFourthpayamt() {
		return fourthpayamt;
	}
	public void setFourthpayamt(BigDecimal fourthpayamt) {
		this.fourthpayamt = fourthpayamt;
	}
	public String getSaleproname() {
		return saleproname;
	}
	public void setSaleproname(String saleproname) {
		this.saleproname = saleproname;
	}
	public BigDecimal getSaleprocardcount() {
		return saleprocardcount;
	}
	public void setSaleprocardcount(BigDecimal saleprocardcount) {
		this.saleprocardcount = saleprocardcount;
	}
	public BigDecimal getSaleprodebtamt() {
		return saleprodebtamt;
	}
	public void setSaleprodebtamt(BigDecimal saleprodebtamt) {
		this.saleprodebtamt = saleprodebtamt;
	}
	
}