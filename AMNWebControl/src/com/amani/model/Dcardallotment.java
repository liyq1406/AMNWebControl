package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dcardallotment  implements java.io.Serializable {


	private DcardallotmentId id;
	private String cardtypeid;
	private String cardnofrom;
	private String cardnoto;
	private BigDecimal ccount;
	private BigDecimal allotcount;
	private String cardtypeName;
	private String appCompId;
	public String getAppCompId() {
		return appCompId;
	}
	public void setAppCompId(String appCompId) {
		this.appCompId = appCompId;
	}
	public DcardallotmentId getId() {
		return id;
	}
	public void setId(DcardallotmentId id) {
		this.id = id;
	}
	public String getCardtypeid() {
		return cardtypeid;
	}
	public void setCardtypeid(String cardtypeid) {
		this.cardtypeid = cardtypeid;
	}
	public String getCardnofrom() {
		return cardnofrom;
	}
	public void setCardnofrom(String cardnofrom) {
		this.cardnofrom = cardnofrom;
	}
	public String getCardnoto() {
		return cardnoto;
	}
	public void setCardnoto(String cardnoto) {
		this.cardnoto = cardnoto;
	}
	public BigDecimal getCcount() {
		return CommonTool.FormatBigDecimal(ccount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setCcount(BigDecimal ccount) {
		this.ccount = ccount;
	}
	public String getCardtypeName() {
		return cardtypeName;
	}
	public void setCardtypeName(String cardtypeName) {
		this.cardtypeName = cardtypeName;
	}
	public BigDecimal getAllotcount() {
		return  CommonTool.FormatBigDecimal(allotcount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setAllotcount(BigDecimal allotcount) {
		this.allotcount = allotcount;
	}
	
}