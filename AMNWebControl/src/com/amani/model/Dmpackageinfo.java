package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dmpackageinfo  implements java.io.Serializable {


	private DmpackageinfoId id;
	private BigDecimal packageprocount;
	private BigDecimal packageproamt;
	private BigDecimal packageproprice;
    private String bcompid;
    private String bpackageno;
    private String bpackageprono;
    private String bpackageproname;
	public DmpackageinfoId getId() {
		return id;
	}
	public void setId(DmpackageinfoId id) {
		this.id = id;
	}
	public BigDecimal getPackageprocount() {
		return packageprocount;
	}
	public void setPackageprocount(BigDecimal packageprocount) {
		this.packageprocount = packageprocount;
	}
	public BigDecimal getPackageproamt() {
		return packageproamt;
	}
	public void setPackageproamt(BigDecimal packageproamt) {
		this.packageproamt = packageproamt;
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
	public String getBpackageprono() {
		return bpackageprono;
	}
	public void setBpackageprono(String bpackageprono) {
		this.bpackageprono = bpackageprono;
	}
	public String getBpackageproname() {
		return bpackageproname;
	}
	public void setBpackageproname(String bpackageproname) {
		this.bpackageproname = bpackageproname;
	}
	public BigDecimal getPackageproprice() {
		return packageproprice;
	}
	public void setPackageproprice(BigDecimal packageproprice) {
		this.packageproprice = packageproprice;
	}
	
}