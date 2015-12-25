package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Goodsinfo  implements java.io.Serializable {


	private GoodsinfoId id;
	private String goodsuniquebar;
	private String goodsbarno;
	private String goodsname;
	private String createdate;
	private String goodstype; 
	private String goodspricetype; 
	private Integer goodsappsource; 
	private String goodswarehouse; 
	private String goodssupplier; 
	private String costunit; 
	private String saleunit; 
	private String purchaseunit; 
	private String goodsformat; 
	private BigDecimal saletocostcount; 
    private BigDecimal purtocostcount;
    private BigDecimal msaleprice;
    private BigDecimal purchaseprice; 
    private BigDecimal costamtbysale;
    private BigDecimal standprice; 
    private BigDecimal storesalseprice; 
    private Integer shelflife; 
    private BigDecimal lowstock; 
    private BigDecimal heightstock; 
    private Integer appflag;
    private Integer useflag;
    private Integer goodsusetype;
    private String stopdate;
    private String stopmark;  
    private Integer pointtype; 
    private BigDecimal pointvalue; 
    private Integer yetype; 
    private BigDecimal yevalue; 
    private Integer tctype; 
    private BigDecimal tcvalue; 
    private String bgoodssource;
    private Integer finaltype;
    private String bgoodsmodeid;
    private String bgoodsno;
    private String goodsabridge;
    private BigDecimal minordercount; 
    private int selfFlag;//是否输入自己的附属部分
    private String proddate;
    private String bindgoodsno;//绑定供应商产品编码
    private String enablecompany;//是否启用采购门店限制
    private String goodscompany;//允许采购门店
    private String enablebarcode;//是否启用条码校验
    
	public String getEnablebarcode() {
		return enablebarcode;
	}
	public void setEnablebarcode(String enablebarcode) {
		this.enablebarcode = enablebarcode;
	}
	public String getEnablecompany() {
		return enablecompany;
	}
	public void setEnablecompany(String enablecompany) {
		this.enablecompany = enablecompany;
	}
	public String getGoodscompany() {
		return goodscompany;
	}
	public void setGoodscompany(String goodscompany) {
		this.goodscompany = goodscompany;
	}
	public String getBindgoodsno() {
		return bindgoodsno;
	}
	public void setBindgoodsno(String bindgoodsno) {
		this.bindgoodsno = bindgoodsno;
	}
	public int getSelfFlag() {
		return selfFlag;
	}
	public void setSelfFlag(int selfFlag) {
		this.selfFlag = selfFlag;
	}
	public GoodsinfoId getId() {
		return id;
	}
	public String getProddate() {
		return proddate;
	}
	public void setProddate(String proddate) {
		this.proddate = proddate;
	}
	public void setId(GoodsinfoId id) {
		this.id = id;
	}
	public String getGoodsuniquebar() {
		return goodsuniquebar;
	}
	public void setGoodsuniquebar(String goodsuniquebar) {
		this.goodsuniquebar = goodsuniquebar;
	}
	public String getGoodsbarno() {
		return goodsbarno;
	}
	public void setGoodsbarno(String goodsbarno) {
		this.goodsbarno = goodsbarno;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}
	public String getGoodspricetype() {
		return goodspricetype;
	}
	public void setGoodspricetype(String goodspricetype) {
		this.goodspricetype = goodspricetype;
	}
	public Integer getGoodsappsource() {
		return goodsappsource;
	}
	public void setGoodsappsource(Integer goodsappsource) {
		this.goodsappsource = goodsappsource;
	}
	public String getGoodswarehouse() {
		return goodswarehouse;
	}
	public void setGoodswarehouse(String goodswarehouse) {
		this.goodswarehouse = goodswarehouse;
	}
	public String getGoodssupplier() {
		return goodssupplier;
	}
	public void setGoodssupplier(String goodssupplier) {
		this.goodssupplier = goodssupplier;
	}
	public String getCostunit() {
		return costunit;
	}
	public void setCostunit(String costunit) {
		this.costunit = costunit;
	}
	public String getSaleunit() {
		return saleunit;
	}
	public void setSaleunit(String saleunit) {
		this.saleunit = saleunit;
	}
	public String getPurchaseunit() {
		return purchaseunit;
	}
	public void setPurchaseunit(String purchaseunit) {
		this.purchaseunit = purchaseunit;
	}
	public String getGoodsformat() {
		return goodsformat;
	}
	public void setGoodsformat(String goodsformat) {
		this.goodsformat = goodsformat;
	}
	public BigDecimal getSaletocostcount() {
		return CommonTool.FormatBigDecimal(saletocostcount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setSaletocostcount(BigDecimal saletocostcount) {
		this.saletocostcount = saletocostcount;
	}
	public BigDecimal getPurtocostcount() {
		return   CommonTool.FormatBigDecimal(purtocostcount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setPurtocostcount(BigDecimal purtocostcount) {
		this.purtocostcount = purtocostcount;
	}
	public BigDecimal getMsaleprice() {
		return  CommonTool.FormatBigDecimal(msaleprice).setScale(2, BigDecimal.ROUND_HALF_UP); 
	}
	public void setMsaleprice(BigDecimal msaleprice) {
		this.msaleprice = msaleprice;
	}
	public BigDecimal getPurchaseprice() {
		return CommonTool.FormatBigDecimal(purchaseprice).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setPurchaseprice(BigDecimal purchaseprice) {
		this.purchaseprice = purchaseprice;
	}
	public BigDecimal getCostamtbysale() {
		return CommonTool.FormatBigDecimal(costamtbysale).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setCostamtbysale(BigDecimal costamtbysale) {
		this.costamtbysale = costamtbysale;
	}
	public BigDecimal getStandprice() {
		return  CommonTool.FormatBigDecimal(standprice).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setStandprice(BigDecimal standprice) {
		this.standprice = standprice;
	}
	public BigDecimal getStoresalseprice() {
		return CommonTool.FormatBigDecimal(storesalseprice).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setStoresalseprice(BigDecimal storesalseprice) {
		this.storesalseprice = storesalseprice;
	}
	public Integer getShelflife() {
		return shelflife;
	}
	public void setShelflife(Integer shelflife) {
		this.shelflife = shelflife;
	}
	public BigDecimal getLowstock() {
		return CommonTool.FormatBigDecimal(lowstock).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setLowstock(BigDecimal lowstock) {
		this.lowstock = lowstock;
	}
	public BigDecimal getHeightstock() {
		return CommonTool.FormatBigDecimal(heightstock).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setHeightstock(BigDecimal heightstock) {
		this.heightstock = heightstock;
	}
	public Integer getAppflag() {
		return appflag;
	}
	public void setAppflag(Integer appflag) {
		this.appflag = appflag;
	}
	public Integer getUseflag() {
		return useflag;
	}
	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}
	public String getStopdate() {
		return stopdate;
	}
	public void setStopdate(String stopdate) {
		this.stopdate = stopdate;
	}
	public String getStopmark() {
		return stopmark;
	}
	public void setStopmark(String stopmark) {
		this.stopmark = stopmark;
	}
	public Integer getPointtype() {
		return pointtype;
	}
	public void setPointtype(Integer pointtype) {
		this.pointtype = pointtype;
	}
	public BigDecimal getPointvalue() {
		return CommonTool.FormatBigDecimal(pointvalue).setScale(2, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setPointvalue(BigDecimal pointvalue) {
		this.pointvalue = pointvalue;
	}
	public Integer getYetype() {
		return yetype;
	}
	public void setYetype(Integer yetype) {
		this.yetype = yetype;
	}
	public BigDecimal getYevalue() {
		return CommonTool.FormatBigDecimal(yevalue).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setYevalue(BigDecimal yevalue) {
		this.yevalue = yevalue;
	}
	public Integer getTctype() {
		return tctype;
	}
	public void setTctype(Integer tctype) {
		this.tctype = tctype;
	}
	public BigDecimal getTcvalue() {
		return CommonTool.FormatBigDecimal(tcvalue).setScale(2, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setTcvalue(BigDecimal tcvalue) {
		this.tcvalue = tcvalue;
	}
	
	public String getBgoodssource() {
		return bgoodssource;
	}
	public void setBgoodssource(String bgoodssource) {
		this.bgoodssource = bgoodssource;
	}
	public String getBgoodsmodeid() {
		return bgoodsmodeid;
	}
	public void setBgoodsmodeid(String bgoodsmodeid) {
		this.bgoodsmodeid = bgoodsmodeid;
	}
	public String getBgoodsno() {
		return bgoodsno;
	}
	public void setBgoodsno(String bgoodsno) {
		this.bgoodsno = bgoodsno;
	}
	public Integer getFinaltype() {
		return finaltype;
	}
	public void setFinaltype(Integer finaltype) {
		this.finaltype = finaltype;
	}
	public String getGoodsabridge() {
		return goodsabridge;
	}
	public void setGoodsabridge(String goodsabridge) {
		this.goodsabridge = goodsabridge;
	}
	public Integer getGoodsusetype() {
		return goodsusetype;
	}
	public void setGoodsusetype(Integer goodsusetype) {
		this.goodsusetype = goodsusetype;
	}
	public BigDecimal getMinordercount() {
		return minordercount;
	}
	public void setMinordercount(BigDecimal minordercount) {
		this.minordercount = minordercount;
	}

}