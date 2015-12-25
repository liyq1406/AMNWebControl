package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dreturngoodsinfo  implements java.io.Serializable {


	private DreturngoodsinfoId id;
    private String 		breturncompid;
    private String 		breturncompname;
    private String 		breturnbillid;
    private String 		returndate;
	private String 		returngoodsno;	
	private String 		returngoodsname;
	private String 		returngoodsunit ;
	
    private BigDecimal 	returncount;
    private Integer 	returntype;
    private String	 	returntypename;
    private String 		revicestoreno;
    private BigDecimal 	factreturncount;	
    private BigDecimal 	factreturnprice;	
    private BigDecimal 	factreturnamt;	
    private BigDecimal 	costreturnprice;	
    private BigDecimal 	costreturnamt;
    private String 		strGoodsType;
	public String getStrGoodsType() {
		return strGoodsType;
	}
	public void setStrGoodsType(String strGoodsType) {
		this.strGoodsType = strGoodsType;
	}
	public DreturngoodsinfoId getId() {
		return id;
	}
	public void setId(DreturngoodsinfoId id) {
		this.id = id;
	}
	public String getReturngoodsno() {
		return returngoodsno;
	}
	public void setReturngoodsno(String returngoodsno) {
		this.returngoodsno = returngoodsno;
	}
	public String getReturngoodsname() {
		return returngoodsname;
	}
	public void setReturngoodsname(String returngoodsname) {
		this.returngoodsname = returngoodsname;
	}
	public String getReturngoodsunit() {
		return returngoodsunit;
	}
	public void setReturngoodsunit(String returngoodsunit) {
		this.returngoodsunit = returngoodsunit;
	}
	public BigDecimal getReturncount() {
		return returncount;
	}
	public void setReturncount(BigDecimal returncount) {
		this.returncount = returncount;
	}
	public Integer getReturntype() {
		return returntype;
	}
	public void setReturntype(Integer returntype) {
		this.returntype = returntype;
	}
	public String getRevicestoreno() {
		return revicestoreno;
	}
	public void setRevicestoreno(String revicestoreno) {
		this.revicestoreno = revicestoreno;
	}
	public BigDecimal getFactreturncount() {
		return factreturncount;
	}
	public void setFactreturncount(BigDecimal factreturncount) {
		this.factreturncount = factreturncount;
	}
	public BigDecimal getFactreturnprice() {
		return factreturnprice;
	}
	public void setFactreturnprice(BigDecimal factreturnprice) {
		this.factreturnprice = factreturnprice;
	}
	public BigDecimal getFactreturnamt() {
		return factreturnamt;
	}
	public void setFactreturnamt(BigDecimal factreturnamt) {
		this.factreturnamt = factreturnamt;
	}

	public BigDecimal getCostreturnprice() {
		return costreturnprice;
	}
	public void setCostreturnprice(BigDecimal costreturnprice) {
		this.costreturnprice = costreturnprice;
	}
	public BigDecimal getCostreturnamt() {
		return costreturnamt;
	}
	public void setCostreturnamt(BigDecimal costreturnamt) {
		this.costreturnamt = costreturnamt;
	}
	public String getBreturncompid() {
		return breturncompid;
	}
	public void setBreturncompid(String breturncompid) {
		this.breturncompid = breturncompid;
	}
	public String getBreturnbillid() {
		return breturnbillid;
	}
	public void setBreturnbillid(String breturnbillid) {
		this.breturnbillid = breturnbillid;
	}
	public String getReturndate() {
		return returndate;
	}
	public void setReturndate(String returndate) {
		this.returndate = returndate;
	}
	public String getBreturncompname() {
		return breturncompname;
	}
	public void setBreturncompname(String breturncompname) {
		this.breturncompname = breturncompname;
	}
	public String getReturntypename() {
		return returntypename;
	}
	public void setReturntypename(String returntypename) {
		this.returntypename = returntypename;
	}
    
}