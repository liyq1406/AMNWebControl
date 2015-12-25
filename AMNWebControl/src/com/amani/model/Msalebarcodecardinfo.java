package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Msalebarcodecardinfo  implements java.io.Serializable {


	private MsalebarcodecardinfoId id;
	private String saledate;
	private String saletime;
	private String cappempid;
	private String packageNo;
	private String operationer;
	private String barcodecardno;
	
    private String firstpaymode;
	private BigDecimal firstpayamt;	
    private String secondpaymode;
	private BigDecimal secondpayamt;	
	private BigDecimal saleamt;
	
	private String firstsaleempid;
	private String firstsaleempinid;
	private BigDecimal firstsaleamt;
	private String secondsaleempid;
    private String secondsaleempinid;
	private BigDecimal secondsaleamt;
	private String thirdsaleempid;
    private String thirdsaleempinid;
    private BigDecimal thirdsaleamt;
    private int salebakflag;
    private int saleMonths;
    private String usecardno;
    private String usecardtype;
    private BigDecimal usecardpayamt;
    private Integer ishz;
    
    
	public Integer getIshz() {
		return ishz;
	}
	public void setIshz(Integer ishz) {
		this.ishz = ishz;
	}
	public String getUsecardno() {
		return usecardno;
	}
	public void setUsecardno(String usecardno) {
		this.usecardno = usecardno;
	}
	public String getUsecardtype() {
		return usecardtype;
	}
	public void setUsecardtype(String usecardtype) {
		this.usecardtype = usecardtype;
	}
	public BigDecimal getUsecardpayamt() {
		return usecardpayamt;
	}
	public void setUsecardpayamt(BigDecimal usecardpayamt) {
		this.usecardpayamt = usecardpayamt;
	}
	public int getSalebakflag() {
		return salebakflag;
	}
	public void setSalebakflag(int salebakflag) {
		this.salebakflag = salebakflag;
	}
	public MsalebarcodecardinfoId getId() {
		return id;
	}
	public void setId(MsalebarcodecardinfoId id) {
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
	public String getCappempid() {
		return cappempid;
	}
	public void setCappempid(String cappempid) {
		this.cappempid = cappempid;
	}
	public String getOperationer() {
		return operationer;
	}
	public void setOperationer(String operationer) {
		this.operationer = operationer;
	}
	public String getBarcodecardno() {
		return barcodecardno;
	}
	public void setBarcodecardno(String barcodecardno) {
		this.barcodecardno = barcodecardno;
	}
	public String getFirstsaleempid() {
		return firstsaleempid;
	}
	public void setFirstsaleempid(String firstsaleempid) {
		this.firstsaleempid = firstsaleempid;
	}
	public String getFirstsaleempinid() {
		return firstsaleempinid;
	}
	public void setFirstsaleempinid(String firstsaleempinid) {
		this.firstsaleempinid = firstsaleempinid;
	}
	public BigDecimal getFirstsaleamt() {
		return firstsaleamt;
	}
	public void setFirstsaleamt(BigDecimal firstsaleamt) {
		this.firstsaleamt = firstsaleamt;
	}
	public String getSecondsaleempid() {
		return secondsaleempid;
	}
	public void setSecondsaleempid(String secondsaleempid) {
		this.secondsaleempid = secondsaleempid;
	}
	public String getSecondsaleempinid() {
		return secondsaleempinid;
	}
	public void setSecondsaleempinid(String secondsaleempinid) {
		this.secondsaleempinid = secondsaleempinid;
	}
	public BigDecimal getSecondsaleamt() {
		return secondsaleamt;
	}
	public void setSecondsaleamt(BigDecimal secondsaleamt) {
		this.secondsaleamt = secondsaleamt;
	}
	public String getThirdsaleempid() {
		return thirdsaleempid;
	}
	public void setThirdsaleempid(String thirdsaleempid) {
		this.thirdsaleempid = thirdsaleempid;
	}
	public String getThirdsaleempinid() {
		return thirdsaleempinid;
	}
	public void setThirdsaleempinid(String thirdsaleempinid) {
		this.thirdsaleempinid = thirdsaleempinid;
	}
	public BigDecimal getThirdsaleamt() {
		return thirdsaleamt;
	}
	public void setThirdsaleamt(BigDecimal thirdsaleamt) {
		this.thirdsaleamt = thirdsaleamt;
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
	public BigDecimal getSaleamt() {
		return saleamt;
	}
	public void setSaleamt(BigDecimal saleamt) {
		this.saleamt = saleamt;
	}
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public int getSaleMonths() {
		return saleMonths;
	}
	public void setSaleMonths(int saleMonths) {
		this.saleMonths = saleMonths;
	}
	
}