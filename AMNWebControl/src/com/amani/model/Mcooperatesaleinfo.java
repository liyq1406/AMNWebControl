package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mcooperatesaleinfo  implements java.io.Serializable {


	private McooperatesaleinfoId id;
	private String saledate;
	private String saletime;
	private String salecooperid;
	private String slaepaymode;
	private BigDecimal salecostproamt;	
	private String salecostcardno;
	private String salecostcardtype;
	private String salecostcardtypename;
	private String changecardfrom;
	private String membername;
	private String memberphone;	
	private String salemark;
	private String salefactpaycode;
	private BigDecimal slaefactpayamt;
	private Integer salebillflag;
	private Integer accounttype;
	private BigDecimal salescardpayment;
	private String paymentmethod;
	private BigDecimal paymentamount;
	
	 
	private String firstsalerid;
	private BigDecimal firstsaleamt;
	private String firstsalerinid;
   
	private String secondsalerid;
    private String secondsalerinid;
    private BigDecimal secondsaleamt;
    
    private String thirdsalerid;
    private String thirdsalerinid;
    private BigDecimal thirdsaleamt;
    
    private String fourthsalerid;
    private String fourthsalerinid;
    private BigDecimal fourthsaleamt;
    
    private String fifthsalerid;
    private String fifthsalerinid;
    private BigDecimal fifthsaleamt;
    
    private String sixthsalerid;
    private String sixthsalerinid;
    private BigDecimal sixthsaleamt;
    
    private String seventhsalerid;
    private String seventhsalerinid;
    private BigDecimal seventhsaleamt;
    
    private String eighthsalerid;
    private String eighthsalerinid;
    private BigDecimal eighthsaleamt;
    
    private String financedate;
    private String operationer;
    private String operationdate;
    
    private String bsalecompid;
    private String bsalebillid;
    
    private Integer salebakflag;
    
    
	public McooperatesaleinfoId getId() {
		return id;
	}
	public void setId(McooperatesaleinfoId id) {
		this.id = id;
	}
	public String getSaledate() {
		return saledate;
	}
	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}
	public String getSalecooperid() {
		return salecooperid;
	}
	public void setSalecooperid(String salecooperid) {
		this.salecooperid = salecooperid;
	}
	public String getSlaepaymode() {
		return slaepaymode;
	}
	public void setSlaepaymode(String slaepaymode) {
		this.slaepaymode = slaepaymode;
	}
	public BigDecimal getSalecostproamt() {
		return salecostproamt;
	}
	public void setSalecostproamt(BigDecimal salecostproamt) {
		this.salecostproamt = salecostproamt;
	}
	public String getSalecostcardno() {
		return salecostcardno;
	}
	public void setSalecostcardno(String salecostcardno) {
		this.salecostcardno = salecostcardno;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getMemberphone() {
		return memberphone;
	}
	public void setMemberphone(String memberphone) {
		this.memberphone = memberphone;
	}
	public String getSalemark() {
		return salemark;
	}
	public void setSalemark(String salemark) {
		this.salemark = salemark;
	}
	public String getSalefactpaycode() {
		return salefactpaycode;
	}
	public void setSalefactpaycode(String salefactpaycode) {
		this.salefactpaycode = salefactpaycode;
	}
	public BigDecimal getSlaefactpayamt() {
		return slaefactpayamt;
	}
	public void setSlaefactpayamt(BigDecimal slaefactpayamt) {
		this.slaefactpayamt = slaefactpayamt;
	}
	public Integer getSalebillflag() {
		return salebillflag;
	}
	public void setSalebillflag(Integer salebillflag) {
		this.salebillflag = salebillflag;
	}
	public String getFirstsalerid() {
		return firstsalerid;
	}
	public void setFirstsalerid(String firstsalerid) {
		this.firstsalerid = firstsalerid;
	}
	public BigDecimal getFirstsaleamt() {
		return firstsaleamt;
	}
	public void setFirstsaleamt(BigDecimal firstsaleamt) {
		this.firstsaleamt = firstsaleamt;
	}
	public String getFirstsalerinid() {
		return firstsalerinid;
	}
	public void setFirstsalerinid(String firstsalerinid) {
		this.firstsalerinid = firstsalerinid;
	}
	public String getSecondsalerid() {
		return secondsalerid;
	}
	public void setSecondsalerid(String secondsalerid) {
		this.secondsalerid = secondsalerid;
	}
	public String getSecondsalerinid() {
		return secondsalerinid;
	}
	public void setSecondsalerinid(String secondsalerinid) {
		this.secondsalerinid = secondsalerinid;
	}
	public BigDecimal getSecondsaleamt() {
		return secondsaleamt;
	}
	public void setSecondsaleamt(BigDecimal secondsaleamt) {
		this.secondsaleamt = secondsaleamt;
	}
	public String getThirdsalerid() {
		return thirdsalerid;
	}
	public void setThirdsalerid(String thirdsalerid) {
		this.thirdsalerid = thirdsalerid;
	}
	public String getThirdsalerinid() {
		return thirdsalerinid;
	}
	public void setThirdsalerinid(String thirdsalerinid) {
		this.thirdsalerinid = thirdsalerinid;
	}
	public BigDecimal getThirdsaleamt() {
		return thirdsaleamt;
	}
	public void setThirdsaleamt(BigDecimal thirdsaleamt) {
		this.thirdsaleamt = thirdsaleamt;
	}
	public String getFourthsalerid() {
		return fourthsalerid;
	}
	public void setFourthsalerid(String fourthsalerid) {
		this.fourthsalerid = fourthsalerid;
	}
	public String getFourthsalerinid() {
		return fourthsalerinid;
	}
	public void setFourthsalerinid(String fourthsalerinid) {
		this.fourthsalerinid = fourthsalerinid;
	}
	public BigDecimal getFourthsaleamt() {
		return fourthsaleamt;
	}
	public void setFourthsaleamt(BigDecimal fourthsaleamt) {
		this.fourthsaleamt = fourthsaleamt;
	}
	public String getFifthsalerid() {
		return fifthsalerid;
	}
	public void setFifthsalerid(String fifthsalerid) {
		this.fifthsalerid = fifthsalerid;
	}
	public String getFifthsalerinid() {
		return fifthsalerinid;
	}
	public void setFifthsalerinid(String fifthsalerinid) {
		this.fifthsalerinid = fifthsalerinid;
	}
	public BigDecimal getFifthsaleamt() {
		return fifthsaleamt;
	}
	public void setFifthsaleamt(BigDecimal fifthsaleamt) {
		this.fifthsaleamt = fifthsaleamt;
	}
	public String getSixthsalerid() {
		return sixthsalerid;
	}
	public void setSixthsalerid(String sixthsalerid) {
		this.sixthsalerid = sixthsalerid;
	}
	public String getSixthsalerinid() {
		return sixthsalerinid;
	}
	public void setSixthsalerinid(String sixthsalerinid) {
		this.sixthsalerinid = sixthsalerinid;
	}
	public BigDecimal getSixthsaleamt() {
		return sixthsaleamt;
	}
	public void setSixthsaleamt(BigDecimal sixthsaleamt) {
		this.sixthsaleamt = sixthsaleamt;
	}
	public String getSeventhsalerid() {
		return seventhsalerid;
	}
	public void setSeventhsalerid(String seventhsalerid) {
		this.seventhsalerid = seventhsalerid;
	}
	public String getSeventhsalerinid() {
		return seventhsalerinid;
	}
	public void setSeventhsalerinid(String seventhsalerinid) {
		this.seventhsalerinid = seventhsalerinid;
	}
	public BigDecimal getSeventhsaleamt() {
		return seventhsaleamt;
	}
	public void setSeventhsaleamt(BigDecimal seventhsaleamt) {
		this.seventhsaleamt = seventhsaleamt;
	}
	public String getEighthsalerid() {
		return eighthsalerid;
	}
	public void setEighthsalerid(String eighthsalerid) {
		this.eighthsalerid = eighthsalerid;
	}
	public String getEighthsalerinid() {
		return eighthsalerinid;
	}
	public void setEighthsalerinid(String eighthsalerinid) {
		this.eighthsalerinid = eighthsalerinid;
	}
	public BigDecimal getEighthsaleamt() {
		return eighthsaleamt;
	}
	public void setEighthsaleamt(BigDecimal eighthsaleamt) {
		this.eighthsaleamt = eighthsaleamt;
	}
	public String getFinancedate() {
		return financedate;
	}
	public void setFinancedate(String financedate) {
		this.financedate = financedate;
	}
	public String getOperationer() {
		return operationer;
	}
	public void setOperationer(String operationer) {
		this.operationer = operationer;
	}
	public String getOperationdate() {
		return operationdate;
	}
	public void setOperationdate(String operationdate) {
		this.operationdate = operationdate;
	}
	public String getSaletime() {
		return saletime;
	}
	public void setSaletime(String saletime) {
		this.saletime = saletime;
	}
	public String getBsalecompid() {
		return bsalecompid;
	}
	public void setBsalecompid(String bsalecompid) {
		this.bsalecompid = bsalecompid;
	}
	public String getBsalebillid() {
		return bsalebillid;
	}
	public void setBsalebillid(String bsalebillid) {
		this.bsalebillid = bsalebillid;
	}
	public String getSalecostcardtype() {
		return salecostcardtype;
	}
	public void setSalecostcardtype(String salecostcardtype) {
		this.salecostcardtype = salecostcardtype;
	}
	public String getSalecostcardtypename() {
		return salecostcardtypename;
	}
	public void setSalecostcardtypename(String salecostcardtypename) {
		this.salecostcardtypename = salecostcardtypename;
	}
	public String getChangecardfrom() {
		return changecardfrom;
	}
	public void setChangecardfrom(String changecardfrom) {
		this.changecardfrom = changecardfrom;
	}
	public Integer getSalebakflag() {
		return salebakflag;
	}
	public void setSalebakflag(Integer salebakflag) {
		this.salebakflag = salebakflag;
	}
	public BigDecimal getSalescardpayment() {
		return salescardpayment;
	}
	public void setSalescardpayment(BigDecimal salescardpayment) {
		this.salescardpayment = salescardpayment;
	}
	public String getPaymentmethod() {
		return paymentmethod;
	}
	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}
	public BigDecimal getPaymentamount() {
		return paymentamount;
	}
	public void setPaymentamount(BigDecimal paymentamount) {
		this.paymentamount = paymentamount;
	}
	public Integer getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(Integer accounttype) {
		this.accounttype = accounttype;
	}
	
    }