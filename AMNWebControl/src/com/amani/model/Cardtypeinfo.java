package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Cardtypeinfo  implements java.io.Serializable {


	private CardtypeinfoId id;
	private String cardtypename;
	private Integer cardusetype;
	private Integer cardchiptype;

	private BigDecimal carduselife; 
	private BigDecimal cardsaleprice; 
	private BigDecimal cardcost; 
	private BigDecimal saletcvalue; 
	private BigDecimal saleyjvalue; 
	private BigDecimal fillyjvalue;
	private BigDecimal filltcvalue; 
	private BigDecimal prjpointvalue; 
	private BigDecimal goodspointvalue; 
	private BigDecimal lowfillamt; 
	private BigDecimal lowopenamt; 
	private BigDecimal slaeproerate; 
	private BigDecimal slaegoodsrate; 
	private Integer saletctype;
	private Integer saleyjtype;
	private Integer fillyjtype;
	private Integer filltctype;
	private Integer pointtype;
	private Integer salegoodsflag;
	private Integer changerule;
	private Integer openflag;
	private Integer sendamtflag;
	private Integer fillflag;
	private Integer changeflag;
	private Integer finaltype;
	private String bcardtypesource;	
    private String bcardtypemodeid;
    private String bcardtypeno;
    private int selfFlag;//是否输入自己的附属部分
	public int getSelfFlag() {
		return selfFlag;
	}
	public void setSelfFlag(int selfFlag) {
		this.selfFlag = selfFlag;
	}
	public CardtypeinfoId getId() {
		return id;
	}
	public void setId(CardtypeinfoId id) {
		this.id = id;
	}
	public String getCardtypename() {
		return cardtypename;
	}
	public void setCardtypename(String cardtypename) {
		this.cardtypename = cardtypename;
	}
	public Integer getCardusetype() {
		return cardusetype;
	}
	public void setCardusetype(Integer cardusetype) {
		this.cardusetype = cardusetype;
	}
	public Integer getCardchiptype() {
		return cardchiptype;
	}
	public void setCardchiptype(Integer cardchiptype) {
		this.cardchiptype = cardchiptype;
	}
	public BigDecimal getCarduselife() {
		return CommonTool.FormatBigDecimal(carduselife).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setCarduselife(BigDecimal carduselife) {
		this.carduselife = carduselife;
	}
	public BigDecimal getCardsaleprice() {
		return CommonTool.FormatBigDecimal(cardsaleprice).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setCardsaleprice(BigDecimal cardsaleprice) {
		this.cardsaleprice = cardsaleprice;
	}
	public BigDecimal getCardcost() {
		return CommonTool.FormatBigDecimal(cardcost).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setCardcost(BigDecimal cardcost) {
		this.cardcost = cardcost;
	}
	public BigDecimal getSaletcvalue() {
		return CommonTool.FormatBigDecimal(saletcvalue).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setSaletcvalue(BigDecimal saletcvalue) {
		this.saletcvalue = saletcvalue;
	}
	public BigDecimal getSaleyjvalue() {
		return CommonTool.FormatBigDecimal(saleyjvalue).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setSaleyjvalue(BigDecimal saleyjvalue) {
		this.saleyjvalue = saleyjvalue;
	}
	public BigDecimal getFillyjvalue() {
		return CommonTool.FormatBigDecimal(fillyjvalue).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setFillyjvalue(BigDecimal fillyjvalue) {
		this.fillyjvalue = fillyjvalue;
	}
	public BigDecimal getFilltcvalue() {
		return  CommonTool.FormatBigDecimal(filltcvalue).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setFilltcvalue(BigDecimal filltcvalue) {
		this.filltcvalue = filltcvalue;
	}
	public BigDecimal getPrjpointvalue() {
		return CommonTool.FormatBigDecimal(prjpointvalue).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setPrjpointvalue(BigDecimal prjpointvalue) {
		this.prjpointvalue = prjpointvalue;
	}
	public BigDecimal getGoodspointvalue() {
		return CommonTool.FormatBigDecimal(goodspointvalue).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setGoodspointvalue(BigDecimal goodspointvalue) {
		this.goodspointvalue = goodspointvalue;
	}
	public BigDecimal getLowfillamt() {
		return  CommonTool.FormatBigDecimal(lowfillamt).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setLowfillamt(BigDecimal lowfillamt) {
		this.lowfillamt = lowfillamt;
	}
	public BigDecimal getLowopenamt() {
		return CommonTool.FormatBigDecimal(lowopenamt).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setLowopenamt(BigDecimal lowopenamt) {
		this.lowopenamt = lowopenamt;
	}
	public BigDecimal getSlaeproerate() {
		return CommonTool.FormatBigDecimal(slaeproerate).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setSlaeproerate(BigDecimal slaeproerate) {
		this.slaeproerate = slaeproerate;
	}
	public Integer getSaletctype() {
		return saletctype;
	}
	public void setSaletctype(Integer saletctype) {
		this.saletctype = saletctype;
	}
	public Integer getSaleyjtype() {
		return saleyjtype;
	}
	public void setSaleyjtype(Integer saleyjtype) {
		this.saleyjtype = saleyjtype;
	}
	public Integer getFillyjtype() {
		return fillyjtype;
	}
	public void setFillyjtype(Integer fillyjtype) {
		this.fillyjtype = fillyjtype;
	}
	public Integer getFilltctype() {
		return filltctype;
	}
	public void setFilltctype(Integer filltctype) {
		this.filltctype = filltctype;
	}
	public Integer getPointtype() {
		return pointtype;
	}
	public void setPointtype(Integer pointtype) {
		this.pointtype = pointtype;
	}
	public Integer getSalegoodsflag() {
		return salegoodsflag;
	}
	public void setSalegoodsflag(Integer salegoodsflag) {
		this.salegoodsflag = salegoodsflag;
	}
	public Integer getChangerule() {
		return changerule;
	}
	public void setChangerule(Integer changerule) {
		this.changerule = changerule;
	}
	public Integer getOpenflag() {
		return openflag;
	}
	public void setOpenflag(Integer openflag) {
		this.openflag = openflag;
	}
	public Integer getFillflag() {
		return fillflag;
	}
	public void setFillflag(Integer fillflag) {
		this.fillflag = fillflag;
	}
	public String getBcardtypemodeid() {
		return bcardtypemodeid;
	}
	public void setBcardtypemodeid(String bcardtypemodeid) {
		this.bcardtypemodeid = bcardtypemodeid;
	}
	public String getBcardtypeno() {
		return bcardtypeno;
	}
	public void setBcardtypeno(String bcardtypeno) {
		this.bcardtypeno = bcardtypeno;
	}
	public String getBcardtypesource() {
		return bcardtypesource;
	}
	public void setBcardtypesource(String bcardtypesource) {
		this.bcardtypesource = bcardtypesource;
	}
	public Integer getFinaltype() {
		return finaltype;
	}
	public void setFinaltype(Integer finaltype) {
		this.finaltype = finaltype;
	}
	public BigDecimal getSlaegoodsrate() {
		return slaegoodsrate;
	}
	public void setSlaegoodsrate(BigDecimal slaegoodsrate) {
		this.slaegoodsrate = slaegoodsrate;
	}
	public Integer getChangeflag() {
		return changeflag;
	}
	public void setChangeflag(Integer changeflag) {
		this.changeflag = changeflag;
	}
	public Integer getSendamtflag() {
		return sendamtflag;
	}
	public void setSendamtflag(Integer sendamtflag) {
		this.sendamtflag = sendamtflag;
	}

}