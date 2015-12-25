package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dgoodsinsert  implements java.io.Serializable {


	private String changeoption;
	private DgoodsinsertId id;
	private String insergoodsno;	
	private String insergoodsname;
	private String inserunit;	
    private BigDecimal insercount;
    private BigDecimal goodsprice;	
    private BigDecimal goodsamt;	
    private String standunit;
    private BigDecimal standcount;
    private BigDecimal standprice;	
    private String producedate;
    private String frombarcode;
    private String tobarcode;
    private String strBarSeqno;
    private String binsercompid;
    private String binserbillid;
    private String producenorm; 
    private String produceenddate;//过期日期
    
	public String getProduceenddate() {
		return produceenddate;
	}
	public void setProduceenddate(String produceenddate) {
		this.produceenddate = produceenddate;
	}
	public String getChangeoption() {
		return changeoption;
	}
	public void setChangeoption(String changeoption) {
		this.changeoption = changeoption;
	}
	public String getProducenorm() {
		return producenorm;
	}
	public void setProducenorm(String producenorm) {
		this.producenorm = producenorm;
	}
	public DgoodsinsertId getId() {
		return id;
	}
	public void setId(DgoodsinsertId id) {
		this.id = id;
	}
	public String getInsergoodsno() {
		return insergoodsno;
	}
	public void setInsergoodsno(String insergoodsno) {
		this.insergoodsno = insergoodsno;
	}
	public String getInserunit() {
		return inserunit;
	}
	public void setInserunit(String inserunit) {
		this.inserunit = inserunit;
	}
	public BigDecimal getInsercount() {
		return insercount;
	}
	public void setInsercount(BigDecimal insercount) {
		this.insercount = insercount;
	}
	public BigDecimal getGoodsprice() {
		return goodsprice;
	}
	public void setGoodsprice(BigDecimal goodsprice) {
		this.goodsprice = goodsprice;
	}
	public BigDecimal getGoodsamt() {
		return goodsamt;
	}
	public void setGoodsamt(BigDecimal goodsamt) {
		this.goodsamt = goodsamt;
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
	public String getFrombarcode() {
		return frombarcode;
	}
	public void setFrombarcode(String frombarcode) {
		this.frombarcode = frombarcode;
	}
	public String getTobarcode() {
		return tobarcode;
	}
	public void setTobarcode(String tobarcode) {
		this.tobarcode = tobarcode;
	}
	public String getBinsercompid() {
		return binsercompid;
	}
	public void setBinsercompid(String binsercompid) {
		this.binsercompid = binsercompid;
	}
	public String getBinserbillid() {
		return binserbillid;
	}
	public void setBinserbillid(String binserbillid) {
		this.binserbillid = binserbillid;
	}
	public String getInsergoodsname() {
		return insergoodsname;
	}
	public void setInsergoodsname(String insergoodsname) {
		this.insergoodsname = insergoodsname;
	}
	public String getStrBarSeqno() {
		return strBarSeqno;
	}
	public void setStrBarSeqno(String strBarSeqno) {
		this.strBarSeqno = strBarSeqno;
	}
}