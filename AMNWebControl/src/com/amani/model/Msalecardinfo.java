package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Msalecardinfo  implements java.io.Serializable {


	private MsalecardinfoId id;
	private String saledate;
	private String saletime;
	private String salecardno;

	private String salecardtype;
	private String salecardtypename;
	private String membername;
    private String memberphone;
    private String memberbirthday;
	private Integer membersex;	
    private String memberpcid;
	private BigDecimal salekeepamt;	
	private BigDecimal saledebtamt;
	private BigDecimal saletotalamt;
	
	private String firstsalerid;
	private BigDecimal firstsaleamt;
	private String firstsalerinid;
	private String firstsalername;
	private String secondsalerid;
    private String secondsalerinid;
    private BigDecimal secondsaleamt;
    private String secondsalername;
    private String thirdsalerid;
    private String thirdsalerinid;
    private BigDecimal thirdsaleamt;
    private String thirdsalername;
    private String fourthsalerid;
    private String fourthsalerinid;
    private BigDecimal fourthsaleamt;
    private String fourthsalername;
    private String fifthsalerid;
    private String fifthsalerinid;
    private BigDecimal fifthsaleamt;
    private String fifthsalername;
    private String sixthsalerid;
    private String sixthsalerinid;
    private BigDecimal sixthsaleamt;
    private String sixthsalername;
    private String seventhsalerid;
    private String seventhsalerinid;
    private BigDecimal seventhsaleamt;
    private String seventhsalername;
    private String eighthsalerid;
    private String eighthsalerinid;
    private BigDecimal eighthsaleamt;
    private String eighthsalername;
    private String ninthsalerid;
    private String ninthsalerinid;
    private BigDecimal ninthsaleamt;
    private String ninthsalername;
    private String tenthsalerid;
    private String tenthsalerinid;
    private BigDecimal tenthsaleamt;
    private String tenthsalername;
    private String financedate;
    private String saleroperator;
    private String saleroperdate;
    private String cardappbillid;
    private String corpscardno;
    private String activtycardno;
    private String saleremark;
    private String backconfirmflag;
    
	private Integer salebakflag;
	private Integer backflag;
	private String bsalecompid;
	private String bsalebillid;
	
	private String firstpaymode;
	private BigDecimal firstpayamt;
	
	private String secondpaymode;
	private BigDecimal secondpayamt;
	
	private String thirdpaymode;
	private BigDecimal thirdpayamt;
	
	private String fourthpaymode;
	private BigDecimal fourthpayamt;
	private Integer sendpointflag;
	private Integer sendamtflag;
	
	private Integer billinsertype;
	
	private String bankcostno;
	
	//美容经理
	private String beautyManager;
	//顾问
	private String consultant;
	//顾问
	private String consultant1;
	private String consultant1inid;
	private BigDecimal consultant1Perf;
	private String beautyManagerinid;
	
	private String consultantinid;
	private Integer isxnj;
	
	public Integer getIsxnj() {
		return isxnj;
	}

	public void setIsxnj(Integer isxnj) {
		this.isxnj = isxnj;
	}

	//美容经理提成
	private BigDecimal beautyPerf;
		
	//美容经理提成
	private BigDecimal consultantPerf;
	
	/**
	 *  @BareFieldName : consultant1
	 *
	 *  @return  the consultant1
	 *
	 *
	 **/
	
	public String getConsultant1() {
		return consultant1;
	}

	/**
	 *  @BareFieldName : consultant1
	 *
	 *  @return  the consultant1
	 *
	 *  @param consultant1 the consultant1 to set
	 *
	 **/
	
	public void setConsultant1(String consultant1) {
		this.consultant1 = consultant1;
	}

	/**
	 *  @BareFieldName : consultant1inid
	 *
	 *  @return  the consultant1inid
	 *
	 *
	 **/
	
	public String getConsultant1inid() {
		return consultant1inid;
	}

	/**
	 *  @BareFieldName : consultant1inid
	 *
	 *  @return  the consultant1inid
	 *
	 *  @param consultant1inid the consultant1inid to set
	 *
	 **/
	
	public void setConsultant1inid(String consultant1inid) {
		this.consultant1inid = consultant1inid;
	}

	/**
	 *  @BareFieldName : consultant1Perf
	 *
	 *  @return  the consultant1Perf
	 *
	 *
	 **/
	
	public BigDecimal getConsultant1Perf() {
		return consultant1Perf;
	}

	/**
	 *  @BareFieldName : consultant1Perf
	 *
	 *  @return  the consultant1Perf
	 *
	 *  @param consultant1Perf the consultant1Perf to set
	 *
	 **/
	
	public void setConsultant1Perf(BigDecimal consultant1Perf) {
		this.consultant1Perf = consultant1Perf;
	}

	public String getBeautyManagerinid() {
		return beautyManagerinid;
	}

	public void setBeautyManagerinid(String beautyManagerinid) {
		this.beautyManagerinid = beautyManagerinid;
	}

	public String getConsultantinid() {
		return consultantinid;
	}

	public void setConsultantinid(String consultantinid) {
		this.consultantinid = consultantinid;
	}

	
	
	
	public String getBeautyManager() {
		return beautyManager;
	}

	public void setBeautyManager(String beautyManager) {
		this.beautyManager = beautyManager;
	}

	public String getConsultant() {
		return consultant;
	}

	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}

	public BigDecimal getBeautyPerf() {
		return beautyPerf;
	}

	public void setBeautyPerf(BigDecimal beautyPerf) {
		this.beautyPerf = beautyPerf;
	}

	public BigDecimal getConsultantPerf() {
		return consultantPerf;
	}

	public void setConsultantPerf(BigDecimal consultantPerf) {
		this.consultantPerf = consultantPerf;
	}

	private BigDecimal firstsalecashamt;
	private BigDecimal secondsalecashamt;
	private BigDecimal thirdsalecashamt;
	private BigDecimal fourthsalecashamt;
	private BigDecimal fifthsalecashamt;
	private BigDecimal sixthsalecashamt;
	private BigDecimal seventhsalecashamt;
	private BigDecimal eighthsalecashamt;
	private BigDecimal ninthsalecashamt;
	private BigDecimal tenthsalecashamt;
	
	
	public String getBankcostno() {
		return bankcostno;
	}

	public void setBankcostno(String bankcostno) {
		this.bankcostno = bankcostno;
	}

	public Integer getSendpointflag() {
		return sendpointflag;
	}

	public void setSendpointflag(Integer sendpointflag) {
		this.sendpointflag = sendpointflag;
	}

	public String getFirstpaymode() {
		return firstpaymode;
	}

	public void setFirstpaymode(String firstpaymode) {
		this.firstpaymode = firstpaymode;
	}

	public BigDecimal getFirstpayamt() {
		return firstpayamt;
	}

	public void setFirstpayamt(BigDecimal firstpayamt) {
		this.firstpayamt = firstpayamt;
	}

	public String getSecondpaymode() {
		return secondpaymode;
	}

	public void setSecondpaymode(String secondpaymode) {
		this.secondpaymode = secondpaymode;
	}

	public BigDecimal getSecondpayamt() {
		return secondpayamt;
	}

	public void setSecondpayamt(BigDecimal secondpayamt) {
		this.secondpayamt = secondpayamt;
	}

	public String getThirdpaymode() {
		return thirdpaymode;
	}

	public void setThirdpaymode(String thirdpaymode) {
		this.thirdpaymode = thirdpaymode;
	}

	public BigDecimal getThirdpayamt() {
		return thirdpayamt;
	}

	public void setThirdpayamt(BigDecimal thirdpayamt) {
		this.thirdpayamt = thirdpayamt;
	}

	public String getFourthpaymode() {
		return fourthpaymode;
	}

	public void setFourthpaymode(String fourthpaymode) {
		this.fourthpaymode = fourthpaymode;
	}

	public BigDecimal getFourthpayamt() {
		return fourthpayamt;
	}

	public void setFourthpayamt(BigDecimal fourthpayamt) {
		this.fourthpayamt = fourthpayamt;
	}

	public MsalecardinfoId getId() {
		return id;
	}

	public void setId(MsalecardinfoId id) {
		this.id = id;
	}

	public String getSaledate() {
		return saledate;
	}

	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}

	public String getSaletime() {
		return saletime;
	}

	public void setSaletime(String saletime) {
		this.saletime = saletime;
	}

	public String getSalecardno() {
		return salecardno;
	}

	public void setSalecardno(String salecardno) {
		this.salecardno = salecardno;
	}

	public String getSalecardtype() {
		return salecardtype;
	}

	public void setSalecardtype(String salecardtype) {
		this.salecardtype = salecardtype;
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

	public Integer getMembersex() {
		return membersex;
	}

	public void setMembersex(Integer membersex) {
		this.membersex = membersex;
	}

	public String getMemberpcid() {
		return memberpcid;
	}

	public void setMemberpcid(String memberpcid) {
		this.memberpcid = memberpcid;
	}

	public BigDecimal getSalekeepamt() {
		return salekeepamt;
	}

	public void setSalekeepamt(BigDecimal salekeepamt) {
		this.salekeepamt = salekeepamt;
	}

	public BigDecimal getSaledebtamt() {
		return saledebtamt;
	}

	public void setSaledebtamt(BigDecimal saledebtamt) {
		this.saledebtamt = saledebtamt;
	}

	public BigDecimal getSaletotalamt() {
		return saletotalamt;
	}

	public void setSaletotalamt(BigDecimal saletotalamt) {
		this.saletotalamt = saletotalamt;
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

	public String getSaleroperator() {
		return saleroperator;
	}

	public void setSaleroperator(String saleroperator) {
		this.saleroperator = saleroperator;
	}

	public String getSaleroperdate() {
		return saleroperdate;
	}

	public void setSaleroperdate(String saleroperdate) {
		this.saleroperdate = saleroperdate;
	}

	public String getCardappbillid() {
		return cardappbillid;
	}

	public void setCardappbillid(String cardappbillid) {
		this.cardappbillid = cardappbillid;
	}

	public String getCorpscardno() {
		return corpscardno;
	}

	public void setCorpscardno(String corpscardno) {
		this.corpscardno = corpscardno;
	}

	public String getSaleremark() {
		return saleremark;
	}

	public void setSaleremark(String saleremark) {
		this.saleremark = saleremark;
	}

	public String getBackconfirmflag() {
		return backconfirmflag;
	}

	public void setBackconfirmflag(String backconfirmflag) {
		this.backconfirmflag = backconfirmflag;
	}

	public Integer getSalebakflag() {
		return salebakflag;
	}

	public void setSalebakflag(Integer salebakflag) {
		this.salebakflag = salebakflag;
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

	public String getMemberbirthday() {
		return memberbirthday;
	}

	public void setMemberbirthday(String memberbirthday) {
		this.memberbirthday = memberbirthday;
	}

	public String getNinthsalerid() {
		return ninthsalerid;
	}

	public void setNinthsalerid(String ninthsalerid) {
		this.ninthsalerid = ninthsalerid;
	}

	public String getNinthsalerinid() {
		return ninthsalerinid;
	}

	public void setNinthsalerinid(String ninthsalerinid) {
		this.ninthsalerinid = ninthsalerinid;
	}

	public BigDecimal getNinthsaleamt() {
		return ninthsaleamt;
	}

	public void setNinthsaleamt(BigDecimal ninthsaleamt) {
		this.ninthsaleamt = ninthsaleamt;
	}

	public String getTenthsalerid() {
		return tenthsalerid;
	}

	public void setTenthsalerid(String tenthsalerid) {
		this.tenthsalerid = tenthsalerid;
	}

	public String getTenthsalerinid() {
		return tenthsalerinid;
	}

	public void setTenthsalerinid(String tenthsalerinid) {
		this.tenthsalerinid = tenthsalerinid;
	}

	public BigDecimal getTenthsaleamt() {
		return tenthsaleamt;
	}

	public void setTenthsaleamt(BigDecimal tenthsaleamt) {
		this.tenthsaleamt = tenthsaleamt;
	}

	public String getSalecardtypename() {
		return salecardtypename;
	}

	public void setSalecardtypename(String salecardtypename) {
		this.salecardtypename = salecardtypename;
	}

	public String getFirstsalername() {
		return firstsalername;
	}

	public void setFirstsalername(String firstsalername) {
		this.firstsalername = firstsalername;
	}

	public String getSecondsalername() {
		return secondsalername;
	}

	public void setSecondsalername(String secondsalername) {
		this.secondsalername = secondsalername;
	}

	public String getThirdsalername() {
		return thirdsalername;
	}

	public void setThirdsalername(String thirdsalername) {
		this.thirdsalername = thirdsalername;
	}

	public String getFourthsalername() {
		return fourthsalername;
	}

	public void setFourthsalername(String fourthsalername) {
		this.fourthsalername = fourthsalername;
	}

	public String getFifthsalername() {
		return fifthsalername;
	}

	public void setFifthsalername(String fifthsalername) {
		this.fifthsalername = fifthsalername;
	}

	public String getSixthsalername() {
		return sixthsalername;
	}

	public void setSixthsalername(String sixthsalername) {
		this.sixthsalername = sixthsalername;
	}

	public String getSeventhsalername() {
		return seventhsalername;
	}

	public void setSeventhsalername(String seventhsalername) {
		this.seventhsalername = seventhsalername;
	}

	public String getEighthsalername() {
		return eighthsalername;
	}

	public void setEighthsalername(String eighthsalername) {
		this.eighthsalername = eighthsalername;
	}

	public String getNinthsalername() {
		return ninthsalername;
	}

	public void setNinthsalername(String ninthsalername) {
		this.ninthsalername = ninthsalername;
	}

	public String getTenthsalername() {
		return tenthsalername;
	}

	public void setTenthsalername(String tenthsalername) {
		this.tenthsalername = tenthsalername;
	}

	public Integer getBackflag() {
		return backflag;
	}

	public void setBackflag(Integer backflag) {
		this.backflag = backflag;
	}

	public Integer getBillinsertype() {
		return billinsertype;
	}

	public void setBillinsertype(Integer billinsertype) {
		this.billinsertype = billinsertype;
	}

	public Integer getSendamtflag() {
		return sendamtflag;
	}

	public void setSendamtflag(Integer sendamtflag) {
		this.sendamtflag = sendamtflag;
	}

	public BigDecimal getFirstsalecashamt() {
		return firstsalecashamt;
	}

	public void setFirstsalecashamt(BigDecimal firstsalecashamt) {
		this.firstsalecashamt = firstsalecashamt;
	}

	public BigDecimal getSecondsalecashamt() {
		return secondsalecashamt;
	}

	public void setSecondsalecashamt(BigDecimal secondsalecashamt) {
		this.secondsalecashamt = secondsalecashamt;
	}

	public BigDecimal getThirdsalecashamt() {
		return thirdsalecashamt;
	}

	public void setThirdsalecashamt(BigDecimal thirdsalecashamt) {
		this.thirdsalecashamt = thirdsalecashamt;
	}

	public BigDecimal getFourthsalecashamt() {
		return fourthsalecashamt;
	}

	public void setFourthsalecashamt(BigDecimal fourthsalecashamt) {
		this.fourthsalecashamt = fourthsalecashamt;
	}

	public BigDecimal getFifthsalecashamt() {
		return fifthsalecashamt;
	}

	public void setFifthsalecashamt(BigDecimal fifthsalecashamt) {
		this.fifthsalecashamt = fifthsalecashamt;
	}

	public BigDecimal getSixthsalecashamt() {
		return sixthsalecashamt;
	}

	public void setSixthsalecashamt(BigDecimal sixthsalecashamt) {
		this.sixthsalecashamt = sixthsalecashamt;
	}

	public BigDecimal getSeventhsalecashamt() {
		return seventhsalecashamt;
	}

	public void setSeventhsalecashamt(BigDecimal seventhsalecashamt) {
		this.seventhsalecashamt = seventhsalecashamt;
	}

	public BigDecimal getEighthsalecashamt() {
		return eighthsalecashamt;
	}

	public void setEighthsalecashamt(BigDecimal eighthsalecashamt) {
		this.eighthsalecashamt = eighthsalecashamt;
	}

	public BigDecimal getNinthsalecashamt() {
		return ninthsalecashamt;
	}

	public void setNinthsalecashamt(BigDecimal ninthsalecashamt) {
		this.ninthsalecashamt = ninthsalecashamt;
	}

	public BigDecimal getTenthsalecashamt() {
		return tenthsalecashamt;
	}

	public void setTenthsalecashamt(BigDecimal tenthsalecashamt) {
		this.tenthsalecashamt = tenthsalecashamt;
	}

	public String getActivtycardno() {
		return activtycardno;
	}

	public void setActivtycardno(String activtycardno) {
		this.activtycardno = activtycardno;
	}	
}