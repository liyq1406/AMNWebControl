package com.amani.model;

import java.math.BigDecimal;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Staffinfo  implements java.io.Serializable {


	private StaffinfoId id;
	private String bstaffno;
	private String bcompno;
	private String bcompname;
	private String staffname;
	private String staffename;
	private Integer staffsex;
	private String department; 
	private String departmentText; 
    private String position;
    private String positiontitle;
    private String positionText;
    private String positiontitleText;
    private String arrivaldate; 
    private String apparrivaldate; 
    private String leavedate;
    private String contractdate; 
    private String pccid; 
    private String educational; 
    private String birthdate; 
    private BigDecimal height; 
    private BigDecimal bodyweight; 
    private String aaddress; 
    private String qqno; 
    private String mobilephone; 
    private String email; 
    private String healthno; 
    private String healthdate; 
    private String curstate; 
    private BigDecimal socialsecurity; 
    private String remark; 
    private String staffmark;
    private String searchpassword; 
    private String staffpassword; 
    private String manageno; 
    private String reservecontect; 
    private String reservephone; 
    private String reserveaddress; 
    private String introductioner; 
    private String socialsource;
    private Integer leveltype; 
    private BigDecimal basesalary; 
    private Integer businessflag; 
    private String banktype; 
    private String banktypeText;
    private String bankno; 
    private String resulttye; 
    private String resulttyeText; 
    private BigDecimal resultrate; 
    private BigDecimal baseresult; 
    private Integer salaryflag;
    private Integer fingerno;
    private String fingernotext;
    private Integer absencesalary;
    private Integer stafftype;
    private Integer tichengmode;
    private BigDecimal zerenjin; 
    private String fillno;
    private Integer mangerflag;
    private Integer hairqualified;
    private Integer trkcqualified;
    private String years;
    private Integer iscurr;
    private Integer ismoney;
    
    public Integer getIscurr() {
		return iscurr;
	}
	public void setIscurr(Integer iscurr) {
		this.iscurr = iscurr;
	}
	public Integer getIsmoney() {
		return ismoney;
	}
	public void setIsmoney(Integer ismoney) {
		this.ismoney = ismoney;
	}
	private String displayname; 
    private String staffintroduction; 
    
	/**
	 *  @BareFieldName : years
	 *
	 *  @return  the years
	 *
	 *
	 **/
	
	public String getYears() {
		return years;
	}
	/**
	 *  @BareFieldName : years
	 *
	 *  @return  the years
	 *
	 *  @param years the years to set
	 *
	 **/
	
	public void setYears(String years) {
		this.years = years;
	}
	public Integer getTrkcqualified() {
		return trkcqualified;
	}
	public void setTrkcqualified(Integer trkcqualified) {
		this.trkcqualified = trkcqualified;
	}
	public Integer getHairqualified() {
		return hairqualified;
	}
	public void setHairqualified(Integer hairqualified) {
		this.hairqualified = hairqualified;
	}
	public Integer getMangerflag() {
		return mangerflag;
	}
	public void setMangerflag(Integer mangerflag) {
		this.mangerflag = mangerflag;
	}
	public String getFillno() {
		return fillno;
	}
	public void setFillno(String fillno) {
		this.fillno = fillno;
	}
	public Integer getTichengmode() {
		return tichengmode;
	}
	public void setTichengmode(Integer tichengmode) {
		this.tichengmode = tichengmode;
	}
	public Integer getStafftype() {
		return stafftype;
	}
	public void setStafftype(Integer stafftype) {
		this.stafftype = stafftype;
	}
	public Integer getAbsencesalary() {
		return absencesalary;
	}
	public void setAbsencesalary(Integer absencesalary) {
		this.absencesalary = absencesalary;
	}
	public String getFingernotext() {
		return fingernotext;
	}
	public void setFingernotext(String fingernotext) {
		this.fingernotext = fingernotext;
	}
	public Integer getFingerno() {
		return fingerno;
	}
	public void setFingerno(Integer fingerno) {
		this.fingerno = fingerno;
	}
	public StaffinfoId getId() {
		return id;
	}
	public void setId(StaffinfoId id) {
		this.id = id;
	}

	public String getBstaffno() {
		return bstaffno;
	}
	public void setBstaffno(String bstaffno) {
		this.bstaffno = bstaffno;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getStaffename() {
		return staffename;
	}
	public void setStaffename(String staffename) {
		this.staffename = staffename;
	}
	public Integer getStaffsex() {
		return staffsex;
	}
	public void setStaffsex(Integer staffsex) {
		this.staffsex = staffsex;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getArrivaldate() {
		return arrivaldate;
	}
	public void setArrivaldate(String arrivaldate) {
		this.arrivaldate = arrivaldate;
	}
	public String getContractdate() {
		return contractdate;
	}
	public void setContractdate(String contractdate) {
		this.contractdate = contractdate;
	}
	public String getPccid() {
		return pccid;
	}
	public void setPccid(String pccid) {
		this.pccid = pccid;
	}
	public String getEducational() {
		return educational;
	}
	public void setEducational(String educational) {
		this.educational = educational;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public BigDecimal getHeight() {
		return height;
	}
	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	public BigDecimal getBodyweight() {
		return bodyweight;
	}
	public void setBodyweight(BigDecimal bodyweight) {
		this.bodyweight = bodyweight;
	}
	public String getAaddress() {
		return aaddress;
	}
	public void setAaddress(String aaddress) {
		this.aaddress = aaddress;
	}
	public String getQqno() {
		return qqno;
	}
	public void setQqno(String qqno) {
		this.qqno = qqno;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHealthno() {
		return healthno;
	}
	public void setHealthno(String healthno) {
		this.healthno = healthno;
	}
	public String getHealthdate() {
		return healthdate;
	}
	public void setHealthdate(String healthdate) {
		this.healthdate = healthdate;
	}
	public String getCurstate() {
		return curstate;
	}
	public void setCurstate(String curstate) {
		this.curstate = curstate;
	}
	public BigDecimal getSocialsecurity() {
		return socialsecurity;
	}
	public void setSocialsecurity(BigDecimal socialsecurity) {
		this.socialsecurity = socialsecurity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSearchpassword() {
		return searchpassword;
	}
	public void setSearchpassword(String searchpassword) {
		this.searchpassword = searchpassword;
	}
	public String getStaffpassword() {
		return staffpassword;
	}
	public void setStaffpassword(String staffpassword) {
		this.staffpassword = staffpassword;
	}
	public String getManageno() {
		return manageno;
	}
	public void setManageno(String manageno) {
		this.manageno = manageno;
	}
	public String getReservecontect() {
		return reservecontect;
	}
	public void setReservecontect(String reservecontect) {
		this.reservecontect = reservecontect;
	}
	public String getReservephone() {
		return reservephone;
	}
	public void setReservephone(String reservephone) {
		this.reservephone = reservephone;
	}
	public String getReserveaddress() {
		return reserveaddress;
	}
	public void setReserveaddress(String reserveaddress) {
		this.reserveaddress = reserveaddress;
	}
	public String getIntroductioner() {
		return introductioner;
	}
	public void setIntroductioner(String introductioner) {
		this.introductioner = introductioner;
	}
	public Integer getLeveltype() {
		return leveltype;
	}
	public void setLeveltype(Integer leveltype) {
		this.leveltype = leveltype;
	}
	public BigDecimal getBasesalary() {
		return basesalary;
	}
	public void setBasesalary(BigDecimal basesalary) {
		this.basesalary = basesalary;
	}
	public Integer getBusinessflag() {
		return businessflag;
	}
	public void setBusinessflag(Integer businessflag) {
		this.businessflag = businessflag;
	}
	public String getBanktype() {
		return banktype;
	}
	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}
	public String getBankno() {
		return bankno;
	}
	public void setBankno(String bankno) {
		this.bankno = bankno;
	}
	public String getResulttye() {
		return resulttye;
	}
	public void setResulttye(String resulttye) {
		this.resulttye = resulttye;
	}
	public BigDecimal getResultrate() {
		return resultrate;
	}
	public void setResultrate(BigDecimal resultrate) {
		this.resultrate = resultrate;
	}
	public BigDecimal getBaseresult() {
		return baseresult;
	}
	public void setBaseresult(BigDecimal baseresult) {
		this.baseresult = baseresult;
	}
	public Integer getSalaryflag() {
		return salaryflag;
	}
	public void setSalaryflag(Integer salaryflag) {
		this.salaryflag = salaryflag;
	}
	public String getLeavedate() {
		return leavedate;
	}
	public void setLeavedate(String leavedate) {
		this.leavedate = leavedate;
	}
	public String getDepartmentText() {
		return departmentText;
	}
	public void setDepartmentText(String departmentText) {
		this.departmentText = departmentText;
	}
	public String getPositionText() {
		return positionText;
	}
	public void setPositionText(String positionText) {
		this.positionText = positionText;
	}
	public String getBanktypeText() {
		return banktypeText;
	}
	public void setBanktypeText(String banktypeText) {
		this.banktypeText = banktypeText;
	}
	public String getResulttyeText() {
		return resulttyeText;
	}
	public void setResulttyeText(String resulttyeText) {
		this.resulttyeText = resulttyeText;
	}
	public String getBcompno() {
		return bcompno;
	}
	public void setBcompno(String bcompno) {
		this.bcompno = bcompno;
	}
	public String getBcompname() {
		return bcompname;
	}
	public void setBcompname(String bcompname) {
		this.bcompname = bcompname;
	}
	public String getApparrivaldate() {
		return apparrivaldate;
	}
	public void setApparrivaldate(String apparrivaldate) {
		this.apparrivaldate = apparrivaldate;
	}
	public String getPositiontitle() {
		return positiontitle;
	}
	public void setPositiontitle(String positiontitle) {
		this.positiontitle = positiontitle;
	}
	public String getPositiontitleText() {
		return positiontitleText;
	}
	public void setPositiontitleText(String positiontitleText) {
		this.positiontitleText = positiontitleText;
	}
	public String getStaffmark() {
		return staffmark;
	}
	public void setStaffmark(String staffmark) {
		this.staffmark = staffmark;
	}
	public String getSocialsource() {
		return socialsource;
	}
	public void setSocialsource(String socialsource) {
		this.socialsource = socialsource;
	}
	public BigDecimal getZerenjin() {
		return zerenjin;
	}
	public void setZerenjin(BigDecimal zerenjin) {
		this.zerenjin = zerenjin;
	}
	/**
	 *  @BareFieldName : displayname
	 *
	 *  @return  the displayname
	 *
	 *
	 **/
	
	public String getDisplayname() {
		return displayname;
	}
	/**
	 *  @BareFieldName : displayname
	 *
	 *  @return  the displayname
	 *
	 *  @param displayname the displayname to set
	 *
	 **/
	
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	/**
	 *  @BareFieldName : staffintroduction
	 *
	 *  @return  the staffintroduction
	 *
	 *
	 **/
	
	public String getStaffintroduction() {
		return staffintroduction;
	}
	/**
	 *  @BareFieldName : staffintroduction
	 *
	 *  @return  the staffintroduction
	 *
	 *  @param staffintroduction the staffintroduction to set
	 *
	 **/
	
	public void setStaffintroduction(String staffintroduction) {
		this.staffintroduction = staffintroduction;
	} 
	
}