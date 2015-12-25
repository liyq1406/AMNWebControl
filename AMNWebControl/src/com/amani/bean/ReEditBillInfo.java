package com.amani.bean;

import java.math.BigDecimal;

public class ReEditBillInfo {
	private Integer billType;
	private Integer billCount;
	private String billTypeName;
	private String strCompId;
	private String strBillId;
	private String billdate;
	private double changeseqno;
	private int backflag;
	private String strOldCardNo;
	private String strCardNo;
	private String strMemberName;
	private String strCardType;
	private String strCardTypeName;
	private String strPrjNo;
	private String strPrjName;
	private int billinsertype;
	private String firstsalerid;
	private BigDecimal firstsaleamt;
	private String firstsalerinid;
	private String itemInid;
	private double yearAmt;
	private int isxnj;
   
	public int getIsxnj() {
		return isxnj;
	}
	public void setIsxnj(int isxnj) {
		this.isxnj = isxnj;
	}
	public double getYearAmt() {
		return yearAmt;
	}
	public void setYearAmt(double yearAmt) {
		this.yearAmt = yearAmt;
	}
	public String getItemInid() {
		return itemInid;
	}
	public void setItemInid(String itemInid) {
		this.itemInid = itemInid;
	}
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
    
    private Boolean editType1;
    private Boolean editType2;
    private Boolean editType3;
    private Boolean editType4;
    private Boolean editType5;
    private Boolean editType6;
    private Boolean editType7;
    private Boolean editType8;
    private Boolean editType9;
    private Boolean editType10;
    private Boolean editType11;
    private Boolean editType12;
    private Boolean editType13;
    private Boolean editType14;
    private Boolean editType15;
    
	
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
    
	private String optionuserno;
	private String optiondate;
	private String optiontime;
	
	
	//美容经理
	private String beautyManager;
	//顾问
	private String consultant;
		
	private String beautyManagerinid;
		
	private String consultantinid;
	
	
	private String beautyMangerNo;
	private String beautyGw;
	private String beautyMangerNoInid;
	private String beautyGwInId;
		
	public Boolean getEditType14() {
		return editType14;
	}
	public void setEditType14(Boolean editType14) {
		this.editType14 = editType14;
	}
	public Boolean getEditType15() {
		return editType15;
	}
	public void setEditType15(Boolean editType15) {
		this.editType15 = editType15;
	}
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
	public String getBeautyMangerNoInid() {
		return beautyMangerNoInid;
	}
	public void setBeautyMangerNoInid(String beautyMangerNoInid) {
		this.beautyMangerNoInid = beautyMangerNoInid;
	}
	public String getBeautyGwInId() {
		return beautyGwInId;
	}
	public void setBeautyGwInId(String beautyGwInId) {
		this.beautyGwInId = beautyGwInId;
	}
	//美容经理提成
	private BigDecimal beautyPerf;
		
	//美容经理提成
	private BigDecimal consultantPerf;
	private String consultant1;
	private String consultant1inid;
	private BigDecimal consultant1Perf;
	
	/**
	 *  @BareFieldName : editType13
	 *
	 *  @return  the editType13
	 *
	 *
	 **/
	
	public Boolean getEditType13() {
		return editType13;
	}
	/**
	 *  @BareFieldName : editType13
	 *
	 *  @return  the editType13
	 *
	 *  @param editType13 the editType13 to set
	 *
	 **/
	
	public void setEditType13(Boolean editType13) {
		this.editType13 = editType13;
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
	public Boolean getEditType11() {
		return editType11;
	}
	public void setEditType11(Boolean editType11) {
		this.editType11 = editType11;
	}
	public Boolean getEditType12() {
		return editType12;
	}
	public void setEditType12(Boolean editType12) {
		this.editType12 = editType12;
	}
	
	public String getOptionuserno() {
		return optionuserno;
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
	public Boolean getEditType1() {
		return editType1;
	}
	public void setEditType1(Boolean editType1) {
		this.editType1 = editType1;
	}
	public Boolean getEditType2() {
		return editType2;
	}
	public void setEditType2(Boolean editType2) {
		this.editType2 = editType2;
	}
	public Boolean getEditType3() {
		return editType3;
	}
	public void setEditType3(Boolean editType3) {
		this.editType3 = editType3;
	}
	public Boolean getEditType4() {
		return editType4;
	}
	public void setEditType4(Boolean editType4) {
		this.editType4 = editType4;
	}
	public Boolean getEditType5() {
		return editType5;
	}
	public void setEditType5(Boolean editType5) {
		this.editType5 = editType5;
	}
	public Boolean getEditType6() {
		return editType6;
	}
	public void setEditType6(Boolean editType6) {
		this.editType6 = editType6;
	}
	public Boolean getEditType7() {
		return editType7;
	}
	public void setEditType7(Boolean editType7) {
		this.editType7 = editType7;
	}
	public Boolean getEditType8() {
		return editType8;
	}
	public void setEditType8(Boolean editType8) {
		this.editType8 = editType8;
	}
	public Boolean getEditType9() {
		return editType9;
	}
	public void setEditType9(Boolean editType9) {
		this.editType9 = editType9;
	}
	public Boolean getEditType10() {
		return editType10;
	}
	public void setEditType10(Boolean editType10) {
		this.editType10 = editType10;
	}
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	public Integer getBillCount() {
		return billCount;
	}
	public void setBillCount(Integer billCount) {
		this.billCount = billCount;
	}
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}
	public String getStrBillId() {
		return strBillId;
	}
	public void setStrBillId(String strBillId) {
		this.strBillId = strBillId;
	}
	public String getStrCardNo() {
		return strCardNo;
	}
	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}
	public String getStrMemberName() {
		return strMemberName;
	}
	public void setStrMemberName(String strMemberName) {
		this.strMemberName = strMemberName;
	}
	public String getStrCardType() {
		return strCardType;
	}
	public void setStrCardType(String strCardType) {
		this.strCardType = strCardType;
	}
	public String getStrCardTypeName() {
		return strCardTypeName;
	}
	public void setStrCardTypeName(String strCardTypeName) {
		this.strCardTypeName = strCardTypeName;
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
	public String getBillTypeName() {
		return billTypeName;
	}
	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}
	public double getChangeseqno() {
		return changeseqno;
	}
	public void setChangeseqno(double changeseqno) {
		this.changeseqno = changeseqno;
	}
	public int getBackflag() {
		return backflag;
	}
	public void setBackflag(int backflag) {
		this.backflag = backflag;
	}
	public String getBilldate() {
		return billdate;
	}
	public void setBilldate(String billdate) {
		this.billdate = billdate;
	}
	public String getStrPrjNo() {
		return strPrjNo;
	}
	public void setStrPrjNo(String strPrjNo) {
		this.strPrjNo = strPrjNo;
	}
	public String getStrPrjName() {
		return strPrjName;
	}
	public void setStrPrjName(String strPrjName) {
		this.strPrjName = strPrjName;
	}
	public int getBillinsertype() {
		return billinsertype;
	}
	public void setBillinsertype(int billinsertype) {
		this.billinsertype = billinsertype;
	}
	public String getStrOldCardNo() {
		return strOldCardNo;
	}
	public void setStrOldCardNo(String strOldCardNo) {
		this.strOldCardNo = strOldCardNo;
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
}
