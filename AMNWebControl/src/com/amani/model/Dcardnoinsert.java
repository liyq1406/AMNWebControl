package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dcardnoinsert  implements java.io.Serializable {


	private DcardnoinsertId id;
	private String cardtypeid;	
	private String cardnofrom;	
    private String cardnoto;
    private BigDecimal cardnum;
    private BigDecimal cardprice;	
    private BigDecimal cardamt;	
    private String bcinsertcompid;
    private String bcinsertbillid;
    private String cardtypeName;	
	public DcardnoinsertId getId() {
		return id;
	}
	public void setId(DcardnoinsertId id) {
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
	public BigDecimal getCardnum() {
		return  CommonTool.FormatBigDecimal(cardnum).setScale(2, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setCardnum(BigDecimal cardnum) {
		this.cardnum = cardnum;
	}
	public BigDecimal getCardprice() {
		return CommonTool.FormatBigDecimal(cardprice).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setCardprice(BigDecimal cardprice) {
		this.cardprice = cardprice;
	}
	public BigDecimal getCardamt() {
		return CommonTool.FormatBigDecimal(cardamt).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setCardamt(BigDecimal cardamt) {
		this.cardamt = cardamt;
	}
	public String getBcinsertcompid() {
		return bcinsertcompid;
	}
	public void setBcinsertcompid(String bcinsertcompid) {
		this.bcinsertcompid = bcinsertcompid;
	}
	public String getBcinsertbillid() {
		return bcinsertbillid;
	}
	public void setBcinsertbillid(String bcinsertbillid) {
		this.bcinsertbillid = bcinsertbillid;
	}
	public String getCardtypeName() {
		return cardtypeName;
	}
	public void setCardtypeName(String cardtypeName) {
		this.cardtypeName = cardtypeName;
	}
	
	
}