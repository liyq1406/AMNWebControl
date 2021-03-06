package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;



/**
 * Ham12 generated by MyEclipse - Hibernate Tools
 */

public class Cardstock  implements java.io.Serializable {


    // Fields    

     private Integer 		rid;
     private String 		cardclass;
     private String 		cardfrom;
     private String 		cardto;
     private BigDecimal 	ccount;     
     private String 		storage;
     private String 		compid;
     private String 		cardclassname;
     private String 		storagename;
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public String getCardclass() {
		return cardclass;
	}
	public void setCardclass(String cardclass) {
		this.cardclass = cardclass;
	}
	public String getCardfrom() {
		return cardfrom;
	}
	public void setCardfrom(String cardfrom) {
		this.cardfrom = cardfrom;
	}
	public String getCardto() {
		return cardto;
	}
	public void setCardto(String cardto) {
		this.cardto = cardto;
	}
	public BigDecimal getCcount() {
		return  CommonTool.FormatBigDecimal(ccount).setScale(0, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setCcount(BigDecimal ccount) {
		this.ccount = ccount;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public String getCompid() {
		return compid;
	}
	public void setCompid(String compid) {
		this.compid = compid;
	}
	public String getCardclassname() {
		return cardclassname;
	}
	public void setCardclassname(String cardclassname) {
		this.cardclassname = cardclassname;
	}
	public String getStoragename() {
		return storagename;
	}
	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}
	
}