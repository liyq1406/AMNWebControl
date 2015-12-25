package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dcardapponline  implements java.io.Serializable {


	private DcardapponlineId id;
	private String cappcardtypeid;
	private BigDecimal cappcount;
	public DcardapponlineId getId() {
		return id;
	}
	public void setId(DcardapponlineId id) {
		this.id = id;
	}
	public String getCappcardtypeid() {
		return cappcardtypeid;
	}
	public void setCappcardtypeid(String cappcardtypeid) {
		this.cappcardtypeid = cappcardtypeid;
	}
	public BigDecimal getCappcount() {
		return  CommonTool.FormatBigDecimal(cappcount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setCappcount(BigDecimal cappcount) {
		this.cappcount = cappcount;
	}	
}