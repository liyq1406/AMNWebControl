package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dsalebarcodecardinfo  implements java.io.Serializable {


	private DsalebarcodecardinfoId id;
	private String saleproid;
	private String saleproanme;
	private BigDecimal saleprocount;
	private BigDecimal saleproamt;
	private String saleremark;
	private String packageNo;
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public DsalebarcodecardinfoId getId() {
		return id;
	}
	public void setId(DsalebarcodecardinfoId id) {
		this.id = id;
	}
	public String getSaleproid() {
		return saleproid;
	}
	public void setSaleproid(String saleproid) {
		this.saleproid = saleproid;
	}
	public BigDecimal getSaleprocount() {
		return saleprocount;
	}
	public void setSaleprocount(BigDecimal saleprocount) {
		this.saleprocount = saleprocount;
	}
	public BigDecimal getSaleproamt() {
		return saleproamt;
	}
	public void setSaleproamt(BigDecimal saleproamt) {
		this.saleproamt = saleproamt;
	}
	public String getSaleremark() {
		return saleremark;
	}
	public void setSaleremark(String saleremark) {
		this.saleremark = saleremark;
	}
	public String getSaleproanme() {
		return saleproanme;
	}
	public void setSaleproanme(String saleproanme) {
		this.saleproanme = saleproanme;
	}
	
}