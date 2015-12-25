package com.amani.model;

import java.math.BigDecimal;

public class Activitycardinfo {
	private String cardvesting;						//公司编号
	private String cardvestingname;					//公司编号
	private String cardno;							//非内部卡号
	private String expericeitemno;					//体验项目
	private String expericeitemname;				//体验项目
	private BigDecimal expericeitemprice;			//体验价格	
	private int expericeitemnoflag;					//体验项目标识
	private String expericecompno;					//体验门店
	private String expericebillno;					//体验单号
	private String expericedate;					//体验日期
	private String voucherno;						//体验券号
	private int		useflag;						//使用标识
	private String salegoodstype;					//销售产品类型
	private String salegoodstypename;				//销售产品类型
	private BigDecimal salegoodsrate;				//销售产品折扣系数		
	private int salegoodsflag;						//销售产品标识
	private String salegoodscompno;					//销售门店
	private String salegoodsbillno;					//销售单号
	private String salecardtype;					//售卡卡类型
	private String salecardtypename;				//卡类型名称	
	private BigDecimal salecarddeductamt;			//售卡抵扣卡金	
	private int salecardflag;						//销售产品标识
	private String salecardcompno;					//销售门店
	private String salecardbillno;					//销售单号
	private String createdate;						//创建日期
	private String validatedate;					//有效日期
	private int	   sendquanflag;					//是否赠送电子券
	private String sendquanno;						//电子券号码
	public int getSendquanflag() {
		return sendquanflag;
	}
	public void setSendquanflag(int sendquanflag) {
		this.sendquanflag = sendquanflag;
	}
	public String getSendquanno() {
		return sendquanno;
	}
	public void setSendquanno(String sendquanno) {
		this.sendquanno = sendquanno;
	}
	public String getCardvesting() {
		return cardvesting;
	}
	public void setCardvesting(String cardvesting) {
		this.cardvesting = cardvesting;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getExpericeitemno() {
		return expericeitemno;
	}
	public void setExpericeitemno(String expericeitemno) {
		this.expericeitemno = expericeitemno;
	}
	public BigDecimal getExpericeitemprice() {
		return expericeitemprice;
	}
	public void setExpericeitemprice(BigDecimal expericeitemprice) {
		this.expericeitemprice = expericeitemprice;
	}
	public int getExpericeitemnoflag() {
		return expericeitemnoflag;
	}
	public void setExpericeitemnoflag(int expericeitemnoflag) {
		this.expericeitemnoflag = expericeitemnoflag;
	}
	public String getExpericecompno() {
		return expericecompno;
	}
	public void setExpericecompno(String expericecompno) {
		this.expericecompno = expericecompno;
	}
	public String getExpericebillno() {
		return expericebillno;
	}
	public void setExpericebillno(String expericebillno) {
		this.expericebillno = expericebillno;
	}
	public String getExpericedate() {
		return expericedate;
	}
	public void setExpericedate(String expericedate) {
		this.expericedate = expericedate;
	}
	public String getVoucherno() {
		return voucherno;
	}
	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}
	public int getUseflag() {
		return useflag;
	}
	public void setUseflag(int useflag) {
		this.useflag = useflag;
	}
	public String getSalegoodstype() {
		return salegoodstype;
	}
	public void setSalegoodstype(String salegoodstype) {
		this.salegoodstype = salegoodstype;
	}
	public BigDecimal getSalegoodsrate() {
		return salegoodsrate;
	}
	public void setSalegoodsrate(BigDecimal salegoodsrate) {
		this.salegoodsrate = salegoodsrate;
	}
	public int getSalegoodsflag() {
		return salegoodsflag;
	}
	public void setSalegoodsflag(int salegoodsflag) {
		this.salegoodsflag = salegoodsflag;
	}
	public String getSalegoodscompno() {
		return salegoodscompno;
	}
	public void setSalegoodscompno(String salegoodscompno) {
		this.salegoodscompno = salegoodscompno;
	}
	public String getSalegoodsbillno() {
		return salegoodsbillno;
	}
	public void setSalegoodsbillno(String salegoodsbillno) {
		this.salegoodsbillno = salegoodsbillno;
	}
	public BigDecimal getSalecarddeductamt() {
		return salecarddeductamt;
	}
	public void setSalecarddeductamt(BigDecimal salecarddeductamt) {
		this.salecarddeductamt = salecarddeductamt;
	}
	public int getSalecardflag() {
		return salecardflag;
	}
	public void setSalecardflag(int salecardflag) {
		this.salecardflag = salecardflag;
	}
	public String getSalecardcompno() {
		return salecardcompno;
	}
	public void setSalecardcompno(String salecardcompno) {
		this.salecardcompno = salecardcompno;
	}
	public String getSalecardbillno() {
		return salecardbillno;
	}
	public void setSalecardbillno(String salecardbillno) {
		this.salecardbillno = salecardbillno;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getValidatedate() {
		return validatedate;
	}
	public void setValidatedate(String validatedate) {
		this.validatedate = validatedate;
	}
	public String getCardvestingname() {
		return cardvestingname;
	}
	public void setCardvestingname(String cardvestingname) {
		this.cardvestingname = cardvestingname;
	}
	public String getExpericeitemname() {
		return expericeitemname;
	}
	public void setExpericeitemname(String expericeitemname) {
		this.expericeitemname = expericeitemname;
	}
	public String getSalegoodstypename() {
		return salegoodstypename;
	}
	public void setSalegoodstypename(String salegoodstypename) {
		this.salegoodstypename = salegoodstypename;
	}
	public String getSalecardtype() {
		return salecardtype;
	}
	public void setSalecardtype(String salecardtype) {
		this.salecardtype = salecardtype;
	}
	public String getSalecardtypename() {
		return salecardtypename;
	}
	public void setSalecardtypename(String salecardtypename) {
		this.salecardtypename = salecardtypename;
	}
}
