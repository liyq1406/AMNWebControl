package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Cardchangerule  implements java.io.Serializable {


	private CardchangeruleId id;
	private String tocardtypeno;	
	private BigDecimal changeamt; 
	private String cardtypesource;	
    private String brulemodeid;
    private String bcardtypeno;
    private String tocardtypename;	
	public CardchangeruleId getId() {
		return id;
	}
	public void setId(CardchangeruleId id) {
		this.id = id;
	}
	public String getTocardtypeno() {
		return tocardtypeno;
	}
	public void setTocardtypeno(String tocardtypeno) {
		this.tocardtypeno = tocardtypeno;
	}
	public BigDecimal getChangeamt() {
		return CommonTool.FormatBigDecimal(changeamt).setScale(2, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setChangeamt(BigDecimal changeamt) {
		this.changeamt = changeamt;
	}
	public String getCardtypesource() {
		return cardtypesource;
	}
	public void setCardtypesource(String cardtypesource) {
		this.cardtypesource = cardtypesource;
	}
	public String getBrulemodeid() {
		return brulemodeid;
	}
	public void setBrulemodeid(String brulemodeid) {
		this.brulemodeid = brulemodeid;
	}
	public String getBcardtypeno() {
		return bcardtypeno;
	}
	public void setBcardtypeno(String bcardtypeno) {
		this.bcardtypeno = bcardtypeno;
	}
	public String getTocardtypename() {
		return tocardtypename;
	}
	public void setTocardtypename(String tocardtypename) {
		this.tocardtypename = tocardtypename;
	}
	
	
}