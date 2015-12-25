package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dgoodsstockinfo  implements java.io.Serializable {


	private DgoodsstockinfoId id;
	private String changegoodsno;	
	private String standunit;	
	private BigDecimal standcount;	
	private BigDecimal standprice;	
	private String producedate;	
	private String changeunit;	
	private BigDecimal changecount;	
	private BigDecimal changeamt;
	public DgoodsstockinfoId getId() {
		return id;
	}
	public void setId(DgoodsstockinfoId id) {
		this.id = id;
	}
	public String getChangegoodsno() {
		return changegoodsno;
	}
	public void setChangegoodsno(String changegoodsno) {
		this.changegoodsno = changegoodsno;
	}
	public String getStandunit() {
		return standunit;
	}
	public void setStandunit(String standunit) {
		this.standunit = standunit;
	}
	public BigDecimal getStandcount() {
		return standcount;
	}
	public void setStandcount(BigDecimal standcount) {
		this.standcount = standcount;
	}
	public BigDecimal getStandprice() {
		return standprice;
	}
	public void setStandprice(BigDecimal standprice) {
		this.standprice = standprice;
	}
	public String getProducedate() {
		return producedate;
	}
	public void setProducedate(String producedate) {
		this.producedate = producedate;
	}
	public String getChangeunit() {
		return changeunit;
	}
	public void setChangeunit(String changeunit) {
		this.changeunit = changeunit;
	}
	public BigDecimal getChangecount() {
		return changecount;
	}
	public void setChangecount(BigDecimal changecount) {
		this.changecount = changecount;
	}
	public BigDecimal getChangeamt() {
		return changeamt;
	}
	public void setChangeamt(BigDecimal changeamt) {
		this.changeamt = changeamt;
	}	
}