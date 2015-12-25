package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Promotionsinfo  implements java.io.Serializable {


	private PromotionsinfoId id;
	private Integer promotionstype;
	private String promotionscode;
	private String promotionsstore;
	
	private BigDecimal promotionsvalue;
	private String startdate; 
    private String enddate; 
    private String promotionsreason; 
    private Integer promotionsstate;
    private String promotionsempid; 
    private String promotionsdate; 
    private String promotionsempname; 
    private String promotionsstateText;
    private String bcompid;
    private String bbillid;
    private String bcompname;
    private String bpromotionsstore;
	private String promotionsname;
	private String promotionsstorename;
	private Integer invalid;
	public PromotionsinfoId getId() {
		return id;
	}
	public void setId(PromotionsinfoId id) {
		this.id = id;
	}
	public Integer getPromotionstype() {
		return promotionstype;
	}
	public void setPromotionstype(Integer promotionstype) {
		this.promotionstype = promotionstype;
	}
	public String getPromotionscode() {
		return promotionscode;
	}
	public void setPromotionscode(String promotionscode) {
		this.promotionscode = promotionscode;
	}
	public String getPromotionsstore() {
		return promotionsstore;
	}
	public void setPromotionsstore(String promotionsstore) {
		this.promotionsstore = promotionsstore;
	}
	public BigDecimal getPromotionsvalue() {
		return CommonTool.FormatBigDecimal(promotionsvalue).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setPromotionsvalue(BigDecimal promotionsvalue) {
		this.promotionsvalue = promotionsvalue;
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
	public String getPromotionsreason() {
		return promotionsreason;
	}
	public void setPromotionsreason(String promotionsreason) {
		this.promotionsreason = promotionsreason;
	}
	public Integer getPromotionsstate() {
		return promotionsstate;
	}
	public void setPromotionsstate(Integer promotionsstate) {
		this.promotionsstate = promotionsstate;
	}
	public String getPromotionsempid() {
		return promotionsempid;
	}
	public void setPromotionsempid(String promotionsempid) {
		this.promotionsempid = promotionsempid;
	}
	public String getPromotionsdate() {
		return promotionsdate;
	}
	public void setPromotionsdate(String promotionsdate) {
		this.promotionsdate = promotionsdate;
	}
	public String getPromotionsempname() {
		return promotionsempname;
	}
	public void setPromotionsempname(String promotionsempname) {
		this.promotionsempname = promotionsempname;
	}
	public String getPromotionsstateText() {
		return promotionsstateText;
	}
	public void setPromotionsstateText(String promotionsstateText) {
		this.promotionsstateText = promotionsstateText;
	}
	public String getBcompid() {
		return bcompid;
	}
	public void setBcompid(String bcompid) {
		this.bcompid = bcompid;
	}
	public String getBbillid() {
		return bbillid;
	}
	public void setBbillid(String bbillid) {
		this.bbillid = bbillid;
	}
	public String getBcompname() {
		return bcompname;
	}
	public void setBcompname(String bcompname) {
		this.bcompname = bcompname;
	}
	public String getPromotionsname() {
		return promotionsname;
	}
	public void setPromotionsname(String promotionsname) {
		this.promotionsname = promotionsname;
	}
	public String getPromotionsstorename() {
		return promotionsstorename;
	}
	public void setPromotionsstorename(String promotionsstorename) {
		this.promotionsstorename = promotionsstorename;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	public String getBpromotionsstore() {
		return bpromotionsstore;
	}
	public void setBpromotionsstore(String bpromotionsstore) {
		this.bpromotionsstore = bpromotionsstore;
	}
}