package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mcardchangeinfo  implements java.io.Serializable {


	private McardchangeinfoId id;
	private String changedate;
	private String changetime;
	private String changebeforcardno;
	private Integer changebeforcardstate;	
	private String changebeforcardtype;
	private String changebeforcardtypename;
	private String changecardfrom;
	private String membername;
    private String memberphone;
    
	private BigDecimal curaccountkeepamt;	
	private BigDecimal curaccountdebtamt;
	private BigDecimal curproaccountkeepamt;
	private BigDecimal curproaccountdebtamt;
	
	private BigDecimal curpointaccountkeepamt;
	private BigDecimal cursendaccountkeepamt;
	
	private String changeaftercardno;
	private Integer changeaftercardstate;	
	private String changeaftercardtype;
	private String changeaftercardtypename;
	
	private Integer salarytype;
    
	private BigDecimal changelowamt;
	private BigDecimal changefillamt;
	private BigDecimal changdebtamt;
	
	private BigDecimal cashfillamt;
	private BigDecimal bankfillamt;
	private BigDecimal keepamtfillamt;
	private BigDecimal deductamt;
	
	
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
    
    private String rechargeremark;
    private String financedate;
    private String operationer;
    private String operationdate;
    private String confirmer;
    private String confirmdate;
    
    private String backconfirmflag;
    private Integer salebakflag;
    private String bchangecompid;
    private String bchangebillid;
    private Integer bchangetype;
    private Integer billflag;
    private Integer sendpointflag;
    private Integer sendamtflag;
    private Integer backflag;
    private String bankcostno;
    private Integer billinsertype;
    
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
	private Integer isxnj;
	
	public Integer getIsxnj() {
		return isxnj;
	}
	public void setIsxnj(Integer isxnj) {
		this.isxnj = isxnj;
	}
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
	public Integer getBillinsertype() {
		return billinsertype;
	}
	public void setBillinsertype(Integer billinsertype) {
		this.billinsertype = billinsertype;
	}
	public McardchangeinfoId getId() {
		return id;
	}
	public void setId(McardchangeinfoId id) {
		this.id = id;
	}
	public String getChangedate() {
		return changedate;
	}
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}
	public String getChangetime() {
		return changetime;
	}
	public void setChangetime(String changetime) {
		this.changetime = changetime;
	}
	public String getChangebeforcardno() {
		return changebeforcardno;
	}
	public void setChangebeforcardno(String changebeforcardno) {
		this.changebeforcardno = changebeforcardno;
	}
	public Integer getChangebeforcardstate() {
		return changebeforcardstate;
	}
	public void setChangebeforcardstate(Integer changebeforcardstate) {
		this.changebeforcardstate = changebeforcardstate;
	}
	public String getChangebeforcardtype() {
		return changebeforcardtype;
	}
	public void setChangebeforcardtype(String changebeforcardtype) {
		this.changebeforcardtype = changebeforcardtype;
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
	public BigDecimal getCuraccountkeepamt() {
		return curaccountkeepamt;
	}
	public void setCuraccountkeepamt(BigDecimal curaccountkeepamt) {
		this.curaccountkeepamt = curaccountkeepamt;
	}
	public BigDecimal getCuraccountdebtamt() {
		return curaccountdebtamt;
	}
	public void setCuraccountdebtamt(BigDecimal curaccountdebtamt) {
		this.curaccountdebtamt = curaccountdebtamt;
	}
	public BigDecimal getCurproaccountkeepamt() {
		return curproaccountkeepamt;
	}
	public void setCurproaccountkeepamt(BigDecimal curproaccountkeepamt) {
		this.curproaccountkeepamt = curproaccountkeepamt;
	}
	public BigDecimal getCurproaccountdebtamt() {
		return curproaccountdebtamt;
	}
	public void setCurproaccountdebtamt(BigDecimal curproaccountdebtamt) {
		this.curproaccountdebtamt = curproaccountdebtamt;
	}
	public String getChangeaftercardno() {
		return changeaftercardno;
	}
	public void setChangeaftercardno(String changeaftercardno) {
		this.changeaftercardno = changeaftercardno;
	}
	public Integer getChangeaftercardstate() {
		return changeaftercardstate;
	}
	public void setChangeaftercardstate(Integer changeaftercardstate) {
		this.changeaftercardstate = changeaftercardstate;
	}
	public String getChangeaftercardtype() {
		return changeaftercardtype;
	}
	public void setChangeaftercardtype(String changeaftercardtype) {
		this.changeaftercardtype = changeaftercardtype;
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
	public String getRechargeremark() {
		return rechargeremark;
	}
	public void setRechargeremark(String rechargeremark) {
		this.rechargeremark = rechargeremark;
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

	public Integer getSalebakflag() {
		return salebakflag;
	}
	public void setSalebakflag(Integer salebakflag) {
		this.salebakflag = salebakflag;
	}
	public String getBackconfirmflag() {
		return backconfirmflag;
	}
	public void setBackconfirmflag(String backconfirmflag) {
		this.backconfirmflag = backconfirmflag;
	}
	public String getBchangecompid() {
		return bchangecompid;
	}
	public void setBchangecompid(String bchangecompid) {
		this.bchangecompid = bchangecompid;
	}
	public String getBchangebillid() {
		return bchangebillid;
	}
	public void setBchangebillid(String bchangebillid) {
		this.bchangebillid = bchangebillid;
	}
	public Integer getBchangetype() {
		return bchangetype;
	}
	public void setBchangetype(Integer bchangetype) {
		this.bchangetype = bchangetype;
	}
	public String getChangebeforcardtypename() {
		return changebeforcardtypename;
	}
	public void setChangebeforcardtypename(String changebeforcardtypename) {
		this.changebeforcardtypename = changebeforcardtypename;
	}
	public String getChangeaftercardtypename() {
		return changeaftercardtypename;
	}
	public void setChangeaftercardtypename(String changeaftercardtypename) {
		this.changeaftercardtypename = changeaftercardtypename;
	}
	public Integer getBillflag() {
		return billflag;
	}
	public void setBillflag(Integer billflag) {
		this.billflag = billflag;
	}
	public BigDecimal getChangelowamt() {
		return changelowamt;
	}
	public void setChangelowamt(BigDecimal changelowamt) {
		this.changelowamt = changelowamt;
	}
	public BigDecimal getChangefillamt() {
		return changefillamt;
	}
	public void setChangefillamt(BigDecimal changefillamt) {
		this.changefillamt = changefillamt;
	}
	public BigDecimal getChangdebtamt() {
		return changdebtamt;
	}
	public void setChangdebtamt(BigDecimal changdebtamt) {
		this.changdebtamt = changdebtamt;
	}
	public String getChangecardfrom() {
		return changecardfrom;
	}
	public void setChangecardfrom(String changecardfrom) {
		this.changecardfrom = changecardfrom;
	}
	public String getConfirmer() {
		return confirmer;
	}
	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
	}
	public String getConfirmdate() {
		return confirmdate;
	}
	public void setConfirmdate(String confirmdate) {
		this.confirmdate = confirmdate;
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
	public Integer getSendpointflag() {
		return sendpointflag;
	}
	public void setSendpointflag(Integer sendpointflag) {
		this.sendpointflag = sendpointflag;
	}
	public Integer getBackflag() {
		return backflag;
	}
	public void setBackflag(Integer backflag) {
		this.backflag = backflag;
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
	public BigDecimal getCurpointaccountkeepamt() {
		return curpointaccountkeepamt;
	}
	public void setCurpointaccountkeepamt(BigDecimal curpointaccountkeepamt) {
		this.curpointaccountkeepamt = curpointaccountkeepamt;
	}
	public BigDecimal getCursendaccountkeepamt() {
		return cursendaccountkeepamt;
	}
	public void setCursendaccountkeepamt(BigDecimal cursendaccountkeepamt) {
		this.cursendaccountkeepamt = cursendaccountkeepamt;
	}
	public BigDecimal getCashfillamt() {
		return cashfillamt;
	}
	public void setCashfillamt(BigDecimal cashfillamt) {
		this.cashfillamt = cashfillamt;
	}
	public BigDecimal getBankfillamt() {
		return bankfillamt;
	}
	public void setBankfillamt(BigDecimal bankfillamt) {
		this.bankfillamt = bankfillamt;
	}
	public BigDecimal getKeepamtfillamt() {
		return keepamtfillamt;
	}
	public void setKeepamtfillamt(BigDecimal keepamtfillamt) {
		this.keepamtfillamt = keepamtfillamt;
	}
	public BigDecimal getDeductamt() {
		return deductamt;
	}
	public void setDeductamt(BigDecimal deductamt) {
		this.deductamt = deductamt;
	}
	public Integer getSalarytype() {
		return salarytype;
	}
	public void setSalarytype(Integer salarytype) {
		this.salarytype = salarytype;
	}
    }