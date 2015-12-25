package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mcardrechargeinfo  implements java.io.Serializable {


	private McardrechargeinfoId id;
	private String rechargedate;
	private String rechargetime;
	private String rechargecardno;
	private String rechargecardtype;
	private String rechargecardtypeName;
	private String rechargeaccounttype;
	private Integer rechargetype;
	private String membername;
 
	private BigDecimal rechargekeepamt;	
	private BigDecimal rechargedebtamt;
	private BigDecimal curcardamt;
	private BigDecimal curcarddebtamt;
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
    
    
    private String ninthsalerid;
    private String ninthsalerinid;
    private BigDecimal ninthsaleamt;
    
    private String tenthsalerid;
    private String tenthsalerinid;
    private BigDecimal tenthsaleamt;
    
    private String financedate;
    private String operationer;
    private String operationdate;


    private String rechargeremark;
    private String backconfirmflag;
    private Integer salebakflag;
    
	private Integer sendpointflag;
	private Integer sendamtflag;
	
	private String firstpaymode;
	private BigDecimal firstpayamt;
	
	private String secondpaymode;
	private BigDecimal secondpayamt;
	
	private String thirdpaymode;
	private BigDecimal thirdpayamt;
	
	private String fourthpaymode;
	private BigDecimal fourthpayamt;
	
    private String brechargecompid;
    private String brechargebillid;
    
    private Integer billinsertype;
    
    private Integer backflag;
    private String bankcostno;
    
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
	
	//美容经理
	private String beautyManager;
	//顾问
	private String consultant;
	
	private String beautyManagerinid;
	
	private String consultantinid;
	
	//美容经理提成
	private BigDecimal beautyPerf;
		
	//美容经理提成
	private BigDecimal consultantPerf;
	private String consultant1;
	private String consultant1inid;
	private BigDecimal consultant1Perf;
	private Integer isxnj;
	private Integer scanpaytype ;//扫码支付类型，1.支付宝 2.微信
	private BigDecimal scanpayamt ;//扫码支付金额
	private String scantradeno;//扫码订单号
	public Integer getIsxnj() {
		return isxnj;
	}
	public void setIsxnj(Integer isxnj) {
		this.isxnj = isxnj;
	}
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
	/**
	 *  @BareFieldName : beautyManager
	 *
	 *  @return  the beautyManager
	 *
	 *
	 **/
	
	public String getBeautyManager() {
		return beautyManager;
	}
	/**
	 *  @BareFieldName : beautyManager
	 *
	 *  @return  the beautyManager
	 *
	 *  @param beautyManager the beautyManager to set
	 *
	 **/
	
	public void setBeautyManager(String beautyManager) {
		this.beautyManager = beautyManager;
	}
	/**
	 *  @BareFieldName : consultant
	 *
	 *  @return  the consultant
	 *
	 *
	 **/
	
	public String getConsultant() {
		return consultant;
	}
	/**
	 *  @BareFieldName : consultant
	 *
	 *  @return  the consultant
	 *
	 *  @param consultant the consultant to set
	 *
	 **/
	
	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}
	/**
	 *  @BareFieldName : beautyManagerinid
	 *
	 *  @return  the beautyManagerinid
	 *
	 *
	 **/
	
	public String getBeautyManagerinid() {
		return beautyManagerinid;
	}
	/**
	 *  @BareFieldName : beautyManagerinid
	 *
	 *  @return  the beautyManagerinid
	 *
	 *  @param beautyManagerinid the beautyManagerinid to set
	 *
	 **/
	
	public void setBeautyManagerinid(String beautyManagerinid) {
		this.beautyManagerinid = beautyManagerinid;
	}
	/**
	 *  @BareFieldName : consultantinid
	 *
	 *  @return  the consultantinid
	 *
	 *
	 **/
	
	public String getConsultantinid() {
		return consultantinid;
	}
	/**
	 *  @BareFieldName : consultantinid
	 *
	 *  @return  the consultantinid
	 *
	 *  @param consultantinid the consultantinid to set
	 *
	 **/
	
	public void setConsultantinid(String consultantinid) {
		this.consultantinid = consultantinid;
	}
	/**
	 *  @BareFieldName : beautyPerf
	 *
	 *  @return  the beautyPerf
	 *
	 *
	 **/
	
	public BigDecimal getBeautyPerf() {
		return beautyPerf;
	}
	/**
	 *  @BareFieldName : beautyPerf
	 *
	 *  @return  the beautyPerf
	 *
	 *  @param beautyPerf the beautyPerf to set
	 *
	 **/
	
	public void setBeautyPerf(BigDecimal beautyPerf) {
		this.beautyPerf = beautyPerf;
	}
	/**
	 *  @BareFieldName : consultantPerf
	 *
	 *  @return  the consultantPerf
	 *
	 *
	 **/
	
	public BigDecimal getConsultantPerf() {
		return consultantPerf;
	}
	/**
	 *  @BareFieldName : consultantPerf
	 *
	 *  @return  the consultantPerf
	 *
	 *  @param consultantPerf the consultantPerf to set
	 *
	 **/
	
	public void setConsultantPerf(BigDecimal consultantPerf) {
		this.consultantPerf = consultantPerf;
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
	public McardrechargeinfoId getId() {
		return id;
	}
	public void setId(McardrechargeinfoId id) {
		this.id = id;
	}
	public String getRechargedate() {
		return rechargedate;
	}
	public void setRechargedate(String rechargedate) {
		this.rechargedate = rechargedate;
	}
	public String getRechargetime() {
		return rechargetime;
	}
	public void setRechargetime(String rechargetime) {
		this.rechargetime = rechargetime;
	}
	public String getRechargecardno() {
		return rechargecardno;
	}
	public void setRechargecardno(String rechargecardno) {
		this.rechargecardno = rechargecardno;
	}
	public String getRechargecardtype() {
		return rechargecardtype;
	}
	public void setRechargecardtype(String rechargecardtype) {
		this.rechargecardtype = rechargecardtype;
	}
	public String getRechargeaccounttype() {
		return rechargeaccounttype;
	}
	public void setRechargeaccounttype(String rechargeaccounttype) {
		this.rechargeaccounttype = rechargeaccounttype;
	}
	public Integer getRechargetype() {
		return rechargetype;
	}
	public void setRechargetype(Integer rechargetype) {
		this.rechargetype = rechargetype;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public BigDecimal getRechargekeepamt() {
		return rechargekeepamt;
	}
	public void setRechargekeepamt(BigDecimal rechargekeepamt) {
		this.rechargekeepamt = rechargekeepamt;
	}
	public BigDecimal getRechargedebtamt() {
		return rechargedebtamt;
	}
	public void setRechargedebtamt(BigDecimal rechargedebtamt) {
		this.rechargedebtamt = rechargedebtamt;
	}
	public BigDecimal getCurcardamt() {
		return curcardamt;
	}
	public void setCurcardamt(BigDecimal curcardamt) {
		this.curcardamt = curcardamt;
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
	public String getRechargeremark() {
		return rechargeremark;
	}
	public void setRechargeremark(String rechargeremark) {
		this.rechargeremark = rechargeremark;
	}
	public Integer getSalebakflag() {
		return salebakflag;
	}
	public void setSalebakflag(Integer salebakflag) {
		this.salebakflag = salebakflag;
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
	public String getBrechargecompid() {
		return brechargecompid;
	}
	public void setBrechargecompid(String brechargecompid) {
		this.brechargecompid = brechargecompid;
	}
	public String getBrechargebillid() {
		return brechargebillid;
	}
	public void setBrechargebillid(String brechargebillid) {
		this.brechargebillid = brechargebillid;
	}
	public String getBackconfirmflag() {
		return backconfirmflag;
	}
	public void setBackconfirmflag(String backconfirmflag) {
		this.backconfirmflag = backconfirmflag;
	}
	public BigDecimal getCurcarddebtamt() {
		return curcarddebtamt;
	}
	public void setCurcarddebtamt(BigDecimal curcarddebtamt) {
		this.curcarddebtamt = curcarddebtamt;
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
	public String getRechargecardtypeName() {
		return rechargecardtypeName;
	}
	public void setRechargecardtypeName(String rechargecardtypeName) {
		this.rechargecardtypeName = rechargecardtypeName;
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
	public String getBankcostno() {
		return bankcostno;
	}
	public void setBankcostno(String bankcostno) {
		this.bankcostno = bankcostno;
	}
	public Integer getSendamtflag() {
		return sendamtflag;
	}
	public void setSendamtflag(Integer sendamtflag) {
		this.sendamtflag = sendamtflag;
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
	public String getScantradeno() {
		return scantradeno;
	}
	public void setScantradeno(String scantradeno) {
		this.scantradeno = scantradeno;
	}
	
	
}