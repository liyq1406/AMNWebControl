package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Staffchangeinfo  implements java.io.Serializable {


	private StaffchangeinfoId id;
    private String bchangecompid;
    private String bchangebillid;
    private Integer bchangetype;
	private String changestaffno;
	private String changestaffname;
	private String appchangecompid;
	private String appchangecompname;
	private String staffpcid;
	private String staffphone;
	private String staffmangerno;
	private String changedate;
	private String arrivaldate;
	private String validatestartdate;
	private String validateenddate;
	private String beforedepartment;
	private String beforepostation;
	private String beforedepartmentText;
	private String beforepostationText;
	private BigDecimal beforesalary;
	private Integer beforesalarytype;
	private String beforeyejitype;
	private BigDecimal beforeyejirate;
	private BigDecimal beforeyejiamt;
	private String aftercompid;
	private String aftercompname;
	private String afterstaffno;
	private String afterdepartment;
	private String afterpostation;
	private String afterdepartmentText;
	private String afterpostationText;
	private BigDecimal aftersalary;
	private Integer aftersalarytype;
	private String afteryejitype;
	private BigDecimal afteryejirate;
	private BigDecimal afteryejiamt;
	private BigDecimal socialsecurity; 
	private Integer leaveltype;
	private String leaveltypeText;
	private String checkcompid;
	private String checkstaffid;
	private String checkdate;
	private Integer checkflag;
	
	private String checkinheadcompid;
	private String checkinheadstaffid;
	private String checkinheaddate;
	private Integer checkinheadflag;
	
	private String comfirmcompid;
	private String comfirmstaffid;
	private String comfirmdate;
	private Integer comfirmflag;
	
	private Integer billflag;
	private String remark;
	private String staffmark;
	private String fillno;
	public String getFillno() {
		return fillno;
	}
	public void setFillno(String fillno) {
		this.fillno = fillno;
	}
	public StaffchangeinfoId getId() {
		return id;
	}
	public void setId(StaffchangeinfoId id) {
		this.id = id;
	}
	public String getChangestaffno() {
		return changestaffno;
	}
	public void setChangestaffno(String changestaffno) {
		this.changestaffno = changestaffno;
	}
	public String getAppchangecompid() {
		return appchangecompid;
	}
	public void setAppchangecompid(String appchangecompid) {
		this.appchangecompid = appchangecompid;
	}
	public String getStaffpcid() {
		return staffpcid;
	}
	public void setStaffpcid(String staffpcid) {
		this.staffpcid = staffpcid;
	}
	public String getStaffphone() {
		return staffphone;
	}
	public void setStaffphone(String staffphone) {
		this.staffphone = staffphone;
	}
	public String getStaffmangerno() {
		return staffmangerno;
	}
	public void setStaffmangerno(String staffmangerno) {
		this.staffmangerno = staffmangerno;
	}
	public String getChangedate() {
		return changedate;
	}
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}
	public String getValidatestartdate() {
		return validatestartdate;
	}
	public void setValidatestartdate(String validatestartdate) {
		this.validatestartdate = validatestartdate;
	}
	public String getValidateenddate() {
		return validateenddate;
	}
	public void setValidateenddate(String validateenddate) {
		this.validateenddate = validateenddate;
	}
	public String getBeforedepartment() {
		return beforedepartment;
	}
	public void setBeforedepartment(String beforedepartment) {
		this.beforedepartment = beforedepartment;
	}
	public String getBeforepostation() {
		return beforepostation;
	}
	public void setBeforepostation(String beforepostation) {
		this.beforepostation = beforepostation;
	}
	public BigDecimal getBeforesalary() {
		return beforesalary;
	}
	public void setBeforesalary(BigDecimal beforesalary) {
		this.beforesalary = beforesalary;
	}
	public Integer getBeforesalarytype() {
		return beforesalarytype;
	}
	public void setBeforesalarytype(Integer beforesalarytype) {
		this.beforesalarytype = beforesalarytype;
	}
	public String getBeforeyejitype() {
		return beforeyejitype;
	}
	public void setBeforeyejitype(String beforeyejitype) {
		this.beforeyejitype = beforeyejitype;
	}
	public BigDecimal getBeforeyejirate() {
		return beforeyejirate;
	}
	public void setBeforeyejirate(BigDecimal beforeyejirate) {
		this.beforeyejirate = beforeyejirate;
	}
	public String getAftercompid() {
		return aftercompid;
	}
	public void setAftercompid(String aftercompid) {
		this.aftercompid = aftercompid;
	}
	public String getAfterstaffno() {
		return afterstaffno;
	}
	public void setAfterstaffno(String afterstaffno) {
		this.afterstaffno = afterstaffno;
	}
	public String getAfterdepartment() {
		return afterdepartment;
	}
	public void setAfterdepartment(String afterdepartment) {
		this.afterdepartment = afterdepartment;
	}
	public String getAfterpostation() {
		return afterpostation;
	}
	public void setAfterpostation(String afterpostation) {
		this.afterpostation = afterpostation;
	}
	public BigDecimal getAftersalary() {
		return aftersalary;
	}
	public void setAftersalary(BigDecimal aftersalary) {
		this.aftersalary = aftersalary;
	}
	public Integer getAftersalarytype() {
		return aftersalarytype;
	}
	public void setAftersalarytype(Integer aftersalarytype) {
		this.aftersalarytype = aftersalarytype;
	}
	public String getAfteryejitype() {
		return afteryejitype;
	}
	public void setAfteryejitype(String afteryejitype) {
		this.afteryejitype = afteryejitype;
	}
	public BigDecimal getAfteryejirate() {
		return afteryejirate;
	}
	public void setAfteryejirate(BigDecimal afteryejirate) {
		this.afteryejirate = afteryejirate;
	}
	public Integer getLeaveltype() {
		return leaveltype;
	}
	public void setLeaveltype(Integer leaveltype) {
		this.leaveltype = leaveltype;
	}
	public String getCheckcompid() {
		return checkcompid;
	}
	public void setCheckcompid(String checkcompid) {
		this.checkcompid = checkcompid;
	}
	public String getCheckstaffid() {
		return checkstaffid;
	}
	public void setCheckstaffid(String checkstaffid) {
		this.checkstaffid = checkstaffid;
	}
	public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public Integer getCheckflag() {
		return checkflag;
	}
	public void setCheckflag(Integer checkflag) {
		this.checkflag = checkflag;
	}
	public String getCheckinheadcompid() {
		return checkinheadcompid;
	}
	public void setCheckinheadcompid(String checkinheadcompid) {
		this.checkinheadcompid = checkinheadcompid;
	}
	public String getCheckinheadstaffid() {
		return checkinheadstaffid;
	}
	public void setCheckinheadstaffid(String checkinheadstaffid) {
		this.checkinheadstaffid = checkinheadstaffid;
	}
	public String getCheckinheaddate() {
		return checkinheaddate;
	}
	public void setCheckinheaddate(String checkinheaddate) {
		this.checkinheaddate = checkinheaddate;
	}
	public Integer getCheckinheadflag() {
		return checkinheadflag;
	}
	public void setCheckinheadflag(Integer checkinheadflag) {
		this.checkinheadflag = checkinheadflag;
	}
	public String getComfirmcompid() {
		return comfirmcompid;
	}
	public void setComfirmcompid(String comfirmcompid) {
		this.comfirmcompid = comfirmcompid;
	}
	public String getComfirmstaffid() {
		return comfirmstaffid;
	}
	public void setComfirmstaffid(String comfirmstaffid) {
		this.comfirmstaffid = comfirmstaffid;
	}
	public String getComfirmdate() {
		return comfirmdate;
	}
	public void setComfirmdate(String comfirmdate) {
		this.comfirmdate = comfirmdate;
	}
	public Integer getComfirmflag() {
		return comfirmflag;
	}
	public void setComfirmflag(Integer comfirmflag) {
		this.comfirmflag = comfirmflag;
	}
	public Integer getBillflag() {
		return billflag;
	}
	public void setBillflag(Integer billflag) {
		this.billflag = billflag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAppchangecompname() {
		return appchangecompname;
	}
	public void setAppchangecompname(String appchangecompname) {
		this.appchangecompname = appchangecompname;
	}
	public String getAftercompname() {
		return aftercompname;
	}
	public void setAftercompname(String aftercompname) {
		this.aftercompname = aftercompname;
	}
	public String getChangestaffname() {
		return changestaffname;
	}
	public void setChangestaffname(String changestaffname) {
		this.changestaffname = changestaffname;
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
	public String getArrivaldate() {
		return arrivaldate;
	}
	public void setArrivaldate(String arrivaldate) {
		this.arrivaldate = arrivaldate;
	}
	public BigDecimal getBeforeyejiamt() {
		return beforeyejiamt;
	}
	public void setBeforeyejiamt(BigDecimal beforeyejiamt) {
		this.beforeyejiamt = beforeyejiamt;
	}
	public BigDecimal getAfteryejiamt() {
		return afteryejiamt;
	}
	public void setAfteryejiamt(BigDecimal afteryejiamt) {
		this.afteryejiamt = afteryejiamt;
	}
	public BigDecimal getSocialsecurity() {
		return socialsecurity;
	}
	public void setSocialsecurity(BigDecimal socialsecurity) {
		this.socialsecurity = socialsecurity;
	}
	public String getBeforedepartmentText() {
		return beforedepartmentText;
	}
	public void setBeforedepartmentText(String beforedepartmentText) {
		this.beforedepartmentText = beforedepartmentText;
	}
	public String getBeforepostationText() {
		return beforepostationText;
	}
	public void setBeforepostationText(String beforepostationText) {
		this.beforepostationText = beforepostationText;
	}
	public String getAfterdepartmentText() {
		return afterdepartmentText;
	}
	public void setAfterdepartmentText(String afterdepartmentText) {
		this.afterdepartmentText = afterdepartmentText;
	}
	public String getAfterpostationText() {
		return afterpostationText;
	}
	public void setAfterpostationText(String afterpostationText) {
		this.afterpostationText = afterpostationText;
	}
	public String getLeaveltypeText() {
		return leaveltypeText;
	}
	public void setLeaveltypeText(String leaveltypeText) {
		this.leaveltypeText = leaveltypeText;
	}
	public String getStaffmark() {
		return staffmark;
	}
	public void setStaffmark(String staffmark) {
		this.staffmark = staffmark;
	}

}