package com.amani.model;

import java.math.BigDecimal;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dproexchangeinfo  implements java.io.Serializable {


	private 	DproexchangeinfoId id;
	private 	String 			changeproid;
	private 	String 			changeproname;
	private 	BigDecimal 		procount;
	private 	BigDecimal 		changeprocount;
	private 	BigDecimal 		changeprorate;
	private 	BigDecimal 		changeproamt;
	private 	BigDecimal 		changebyproaccountamt;
	private 	String 			changeaccounttype;
	private 	BigDecimal 		changebyaccountamt;
	private 	BigDecimal 		changebysendaccountamt;
	private 	String 			changepaymode;
	private 	BigDecimal 		changebycashamt;
	private 	String 			nointernalcardno;
	private 	BigDecimal 		changebydyqamt;
	private 	String 			firstsalerid;
	private 	BigDecimal 		firstsaleamt;
	private 	String 			firstsalerinid;
   
	private	 	String 			secondsalerid;
    private 	String 			secondsalerinid;
    private 	BigDecimal 		secondsaleamt;
    
    private	 	String 			thirdsalerid;
    private 	String 			thirdsalerinid;
    private 	BigDecimal 		thirdsaleamt;
    
    private 	String 			fourthsalerid;
    private 	String 			fourthsalerinid;
    private 	BigDecimal 		fourthsaleamt;
    
    private 	String 			changemark;
    private 	BigDecimal 		standchangeproamt;
    private 	BigDecimal 		standchangeprocount;
    private String optionuserno;
	private String optiondate;
	private String optiontime;
    private 	Integer salebakflag;
    private 	BigDecimal 		changebyfaceamt;
    
	public Integer getSalebakflag() {
		return salebakflag;
	}
	public void setSalebakflag(Integer salebakflag) {
		this.salebakflag = salebakflag;
	}
	public DproexchangeinfoId getId() {
		return id;
	}
	public void setId(DproexchangeinfoId id) {
		this.id = id;
	}
	public String getChangeproid() {
		return changeproid;
	}
	public void setChangeproid(String changeproid) {
		this.changeproid = changeproid;
	}
	public String getChangeproname() {
		return changeproname;
	}
	public void setChangeproname(String changeproname) {
		this.changeproname = changeproname;
	}
	public BigDecimal getChangeprocount() {
		return changeprocount;
	}
	public void setChangeprocount(BigDecimal changeprocount) {
		this.changeprocount = changeprocount;
	}
	public BigDecimal getChangeproamt() {
		return changeproamt;
	}
	public void setChangeproamt(BigDecimal changeproamt) {
		this.changeproamt = changeproamt;
	}
	public String getChangeaccounttype() {
		return changeaccounttype;
	}
	public void setChangeaccounttype(String changeaccounttype) {
		this.changeaccounttype = changeaccounttype;
	}
	public BigDecimal getChangebyaccountamt() {
		return changebyaccountamt;
	}
	public void setChangebyaccountamt(BigDecimal changebyaccountamt) {
		this.changebyaccountamt = changebyaccountamt;
	}
	public String getChangepaymode() {
		return changepaymode;
	}
	public void setChangepaymode(String changepaymode) {
		this.changepaymode = changepaymode;
	}
	public BigDecimal getChangebycashamt() {
		return changebycashamt;
	}
	public void setChangebycashamt(BigDecimal changebycashamt) {
		this.changebycashamt = changebycashamt;
	}
	public String getNointernalcardno() {
		return nointernalcardno;
	}
	public void setNointernalcardno(String nointernalcardno) {
		this.nointernalcardno = nointernalcardno;
	}
	public BigDecimal getChangebydyqamt() {
		return changebydyqamt;
	}
	public void setChangebydyqamt(BigDecimal changebydyqamt) {
		this.changebydyqamt = changebydyqamt;
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
	public BigDecimal getChangeprorate() {
		return changeprorate;
	}
	public void setChangeprorate(BigDecimal changeprorate) {
		this.changeprorate = changeprorate;
	}
	public BigDecimal getChangebyproaccountamt() {
		return changebyproaccountamt;
	}
	public void setChangebyproaccountamt(BigDecimal changebyproaccountamt) {
		this.changebyproaccountamt = changebyproaccountamt;
	}
	public BigDecimal getProcount() {
		return procount;
	}
	public void setProcount(BigDecimal procount) {
		this.procount = procount;
	}
	public String getChangemark() {
		return changemark;
	}
	public void setChangemark(String changemark) {
		this.changemark = changemark;
	}
	public BigDecimal getStandchangeproamt() {
		return standchangeproamt;
	}
	public void setStandchangeproamt(BigDecimal standchangeproamt) {
		this.standchangeproamt = standchangeproamt;
	}
	public BigDecimal getStandchangeprocount() {
		return standchangeprocount;
	}
	public void setStandchangeprocount(BigDecimal standchangeprocount) {
		this.standchangeprocount = standchangeprocount;
	}
	public BigDecimal getChangebysendaccountamt() {
		return changebysendaccountamt;
	}
	public void setChangebysendaccountamt(BigDecimal changebysendaccountamt) {
		this.changebysendaccountamt = changebysendaccountamt;
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
	public BigDecimal getChangebyfaceamt() {
		return changebyfaceamt;
	}
	public void setChangebyfaceamt(BigDecimal changebyfaceamt) {
		this.changebyfaceamt = changebyfaceamt;
	}
}