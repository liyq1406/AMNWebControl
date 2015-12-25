package com.amani.model;

import java.math.BigDecimal;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Cardchangecostrate  implements java.io.Serializable {


	private CardchangecostrateId id;
	private String startdate;
	private String enddate;
	private BigDecimal costrate;
	private String bcompid;
	private String bprojecttypeid;
	private String bcardtypeno;
	private String cardtypename;
	private String bacounttypeno;
	private String bacounttypename;
	public String getBacounttypeno() {
		return bacounttypeno;
	}
	public void setBacounttypeno(String bacounttypeno) {
		this.bacounttypeno = bacounttypeno;
	}
	public String getCardtypename() {
		return cardtypename;
	}
	public void setCardtypename(String cardtypename) {
		this.cardtypename = cardtypename;
	}
	public CardchangecostrateId getId() {
		return id;
	}
	public void setId(CardchangecostrateId id) {
		this.id = id;
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
	public BigDecimal getCostrate() {
		return costrate;
	}
	public void setCostrate(BigDecimal costrate) {
		this.costrate = costrate;
	}
	public String getBcompid() {
		return bcompid;
	}
	public void setBcompid(String bcompid) {
		this.bcompid = bcompid;
	}
	public String getBprojecttypeid() {
		return bprojecttypeid;
	}
	public void setBprojecttypeid(String bprojecttypeid) {
		this.bprojecttypeid = bprojecttypeid;
	}
	public String getBcardtypeno() {
		return bcardtypeno;
	}
	public void setBcardtypeno(String bcardtypeno) {
		this.bcardtypeno = bcardtypeno;
	}
	public String getBacounttypename() {
		return bacounttypename;
	}
	public void setBacounttypename(String bacounttypename) {
		this.bacounttypename = bacounttypename;
	}
	
}