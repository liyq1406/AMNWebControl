package com.amani.model;

import java.math.BigDecimal;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dconsumeinfo  implements java.io.Serializable {


	private DconsumeinfoId id;
	private String csitemno;
	private String financedate;
	private String csitemunit;
	private BigDecimal csitemcount;
	private BigDecimal csunitprice;
	private BigDecimal csdiscount;
	private BigDecimal csdisprice;
	private BigDecimal csitemamt;
	private String cspaymode;
	private String csfirstsaler;
	private String csfirsttype;
	private String csfirstinid;
	private BigDecimal csfirstshare;
	
	private String cssecondsaler;
	private String cssecondtype;
	private String cssecondinid;
	private BigDecimal cssecondshare;
	
	private String csthirdsaler;
	private String csthirdtype;
	private String csthirdinid;
	private BigDecimal csthirdshare;
	
	private String goodsbarno;
	private String csortherpayid;
	private String csitemname;
	private Integer bcsinfotype;
	private Double csproseqno;
	private Double bcsseqno;
    private String bcscompid;
    private String bcsbillid;
	private Integer  saletype;
	private Integer  shareflag;
	
	private Integer  csfirstreserve;
	private Integer  cssecondreserve;
	private Integer  csthirdreserve;
	private Integer  fscsitemstate;
	private Integer  csitemstate;
	private Integer  costpricetype;
	private String optionuserno;
	private String optiondate;
	private String optiontime;
	private String hairRecommendEmpId;//总监首席介绍人ID
	private String hairRecommendEmpName;//总监首席介绍人名称
	private String hairRecommendEmpInid;//总监首席介绍人内部工号
	private String yearinid;
	private String beautyMangerNo;
	private String beautyGw;
	private String beautyMangerNoinid;
	private String beautyGwinid;
	private Integer scanpaytype;//1.支付宝 2.微信
	private BigDecimal scanpayamt;//支付金额
	private BigDecimal haircompamt;//接发-公司业绩金额
	private String hairstaff;//接发-其他店介绍-推荐人
	private Integer integralcode;//积分支付方式的标识字段
	private String saledate;//疗程账户开立日
	private String activinid;//活动唯一编号
	public String getBeautyMangerNo() {
		return beautyMangerNo;
	}

	public void setBeautyMangerNo(String beautyMangerNo) {
		this.beautyMangerNo = beautyMangerNo;
	}

	public String getBeautyGw() {
		return beautyGw;
	}

	public void setBeautyGw(String beautyGw) {
		this.beautyGw = beautyGw;
	}

	public String getBeautyMangerNoinid() {
		return beautyMangerNoinid;
	}

	public void setBeautyMangerNoinid(String beautyMangerNoinid) {
		this.beautyMangerNoinid = beautyMangerNoinid;
	}

	public String getBeautyGwinid() {
		return beautyGwinid;
	}

	public void setBeautyGwinid(String beautyGwinid) {
		this.beautyGwinid = beautyGwinid;
	}

	public String getYearinid() {
		return yearinid;
	}

	public void setYearinid(String yearinid) {
		this.yearinid = yearinid;
	}

	public String getHairRecommendEmpId() {
		return hairRecommendEmpId;
	}

	public void setHairRecommendEmpId(String hairRecommendEmpId) {
		this.hairRecommendEmpId = hairRecommendEmpId;
	}

	public String getHairRecommendEmpName() {
		return hairRecommendEmpName;
	}

	public void setHairRecommendEmpName(String hairRecommendEmpName) {
		this.hairRecommendEmpName = hairRecommendEmpName;
	}

	public String getHairRecommendEmpInid() {
		return hairRecommendEmpInid;
	}

	public void setHairRecommendEmpInid(String hairRecommendEmpInid) {
		this.hairRecommendEmpInid = hairRecommendEmpInid;
	}

	public String getOptionuserno() {
		return optionuserno;
	}

	public void setOptionuserno(String optionuserno) {
		this.optionuserno = optionuserno;
	}

	public String getOptiondate() {
		return optiondate;
	}

	public void setOptiondate(String optiondate) {
		this.optiondate = optiondate;
	}

	public String getOptiontime() {
		return optiontime;
	}

	public void setOptiontime(String optiontime) {
		this.optiontime = optiontime;
	}

	public Integer getCostpricetype() {
		return costpricetype;
	}

	public void setCostpricetype(Integer costpricetype) {
		this.costpricetype = costpricetype;
	}

	public Integer getSaletype() {
		return saletype;
	}

	public void setSaletype(Integer saletype) {
		this.saletype = saletype;
	}

	public DconsumeinfoId getId() {
		return id;
	}

	public void setId(DconsumeinfoId id) {
		this.id = id;
	}

	public String getCsitemno() {
		return csitemno;
	}

	public void setCsitemno(String csitemno) {
		this.csitemno = csitemno;
	}

	public String getCsitemunit() {
		return csitemunit;
	}

	public void setCsitemunit(String csitemunit) {
		this.csitemunit = csitemunit;
	}

	public BigDecimal getCsitemcount() {
		return csitemcount;
	}

	public void setCsitemcount(BigDecimal csitemcount) {
		this.csitemcount = csitemcount;
	}

	public BigDecimal getCsunitprice() {
		return csunitprice;
	}

	public void setCsunitprice(BigDecimal csunitprice) {
		this.csunitprice = csunitprice;
	}

	public BigDecimal getCsdiscount() {
		return csdiscount;
	}

	public void setCsdiscount(BigDecimal csdiscount) {
		this.csdiscount = csdiscount;
	}

	public BigDecimal getCsdisprice() {
		return csdisprice;
	}

	public void setCsdisprice(BigDecimal csdisprice) {
		this.csdisprice = csdisprice;
	}

	public BigDecimal getCsitemamt() {
		return csitemamt;
	}

	public void setCsitemamt(BigDecimal csitemamt) {
		this.csitemamt = csitemamt;
	}

	public String getCsfirstsaler() {
		return csfirstsaler;
	}

	public void setCsfirstsaler(String csfirstsaler) {
		this.csfirstsaler = csfirstsaler;
	}

	public String getCsfirsttype() {
		return csfirsttype;
	}

	public void setCsfirsttype(String csfirsttype) {
		this.csfirsttype = csfirsttype;
	}

	public String getCsfirstinid() {
		return csfirstinid;
	}

	public void setCsfirstinid(String csfirstinid) {
		this.csfirstinid = csfirstinid;
	}

	public BigDecimal getCsfirstshare() {
		return csfirstshare;
	}

	public void setCsfirstshare(BigDecimal csfirstshare) {
		this.csfirstshare = csfirstshare;
	}

	public String getCssecondsaler() {
		return cssecondsaler;
	}

	public void setCssecondsaler(String cssecondsaler) {
		this.cssecondsaler = cssecondsaler;
	}

	public String getCssecondtype() {
		return cssecondtype;
	}

	public void setCssecondtype(String cssecondtype) {
		this.cssecondtype = cssecondtype;
	}

	public String getCssecondinid() {
		return cssecondinid;
	}

	public void setCssecondinid(String cssecondinid) {
		this.cssecondinid = cssecondinid;
	}

	public BigDecimal getCssecondshare() {
		return cssecondshare;
	}

	public void setCssecondshare(BigDecimal cssecondshare) {
		this.cssecondshare = cssecondshare;
	}

	public String getCsthirdsaler() {
		return csthirdsaler;
	}

	public void setCsthirdsaler(String csthirdsaler) {
		this.csthirdsaler = csthirdsaler;
	}

	public String getCsthirdtype() {
		return csthirdtype;
	}

	public void setCsthirdtype(String csthirdtype) {
		this.csthirdtype = csthirdtype;
	}

	public String getCsthirdinid() {
		return csthirdinid;
	}

	public void setCsthirdinid(String csthirdinid) {
		this.csthirdinid = csthirdinid;
	}

	public BigDecimal getCsthirdshare() {
		return csthirdshare;
	}

	public void setCsthirdshare(BigDecimal csthirdshare) {
		this.csthirdshare = csthirdshare;
	}

	public String getCsortherpayid() {
		return csortherpayid;
	}

	public void setCsortherpayid(String csortherpayid) {
		this.csortherpayid = csortherpayid;
	}

	public String getCsitemname() {
		return csitemname;
	}

	public void setCsitemname(String csitemname) {
		this.csitemname = csitemname;
	}

	public Integer getBcsinfotype() {
		return bcsinfotype;
	}

	public void setBcsinfotype(Integer bcsinfotype) {
		this.bcsinfotype = bcsinfotype;
	}

	public String getCspaymode() {
		return cspaymode;
	}

	public void setCspaymode(String cspaymode) {
		this.cspaymode = cspaymode;
	}

	public Double getCsproseqno() {
		return csproseqno;
	}

	public void setCsproseqno(Double csproseqno) {
		this.csproseqno = csproseqno;
	}

	public String getGoodsbarno() {
		return goodsbarno;
	}

	public void setGoodsbarno(String goodsbarno) {
		this.goodsbarno = goodsbarno;
	}

	public Double getBcsseqno() {
		return bcsseqno;
	}

	public void setBcsseqno(Double bcsseqno) {
		this.bcsseqno = bcsseqno;
	}

	public Integer getShareflag() {
		return shareflag;
	}

	public void setShareflag(Integer shareflag) {
		this.shareflag = shareflag;
	}

	public Integer getCsfirstreserve() {
		return csfirstreserve;
	}

	public void setCsfirstreserve(Integer csfirstreserve) {
		this.csfirstreserve = csfirstreserve;
	}

	public Integer getCssecondreserve() {
		return cssecondreserve;
	}

	public void setCssecondreserve(Integer cssecondreserve) {
		this.cssecondreserve = cssecondreserve;
	}

	public Integer getCsthirdreserve() {
		return csthirdreserve;
	}

	public void setCsthirdreserve(Integer csthirdreserve) {
		this.csthirdreserve = csthirdreserve;
	}

	public Integer getCsitemstate() {
		return csitemstate;
	}

	public void setCsitemstate(Integer csitemstate) {
		this.csitemstate = csitemstate;
	}

	public String getBcscompid() {
		return bcscompid;
	}

	public void setBcscompid(String bcscompid) {
		this.bcscompid = bcscompid;
	}

	public String getBcsbillid() {
		return bcsbillid;
	}

	public void setBcsbillid(String bcsbillid) {
		this.bcsbillid = bcsbillid;
	}

	public String getFinancedate() {
		return financedate;
	}

	public void setFinancedate(String financedate) {
		this.financedate = financedate;
	}

	public Integer getFscsitemstate() {
		return fscsitemstate;
	}

	public void setFscsitemstate(Integer fscsitemstate) {
		this.fscsitemstate = fscsitemstate;
	}

	public Integer getScanpaytype() {
		return scanpaytype;
	}

	public void setScanpaytype(Integer scanpaytype) {
		this.scanpaytype = scanpaytype;
	}

	public BigDecimal getScanpayamt() {
		return scanpayamt;
	}

	public void setScanpayamt(BigDecimal scanpayamt) {
		this.scanpayamt = scanpayamt;
	}

	public BigDecimal getHaircompamt() {
		return haircompamt;
	}

	public void setHaircompamt(BigDecimal haircompamt) {
		this.haircompamt = haircompamt;
	}

	public String getHairstaff() {
		return hairstaff;
	}

	public void setHairstaff(String hairstaff) {
		this.hairstaff = hairstaff;
	}

	public Integer getIntegralcode() {
		return integralcode;
	}

	public void setIntegralcode(Integer integralcode) {
		this.integralcode = integralcode;
	}

	public String getSaledate() {
		return saledate;
	}

	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}

	public String getActivinid() {
		return activinid;
	}

	public void setActivinid(String activinid) {
		this.activinid = activinid;
	}
}