package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Staffrewardinfo  implements java.io.Serializable {


	private StaffrewardinfoId id;
	private String bentrycompid;
	private String bentrybillid;
    private Integer entryflag;
	private String handcompid;	
	private String handstaffid;	
	private String handstaffname;
    private String handstaffinid;
    private String entryreason;
    private String entrydate;
    private String entrytype;	
    private Integer  billflag;	
    private String checkrewardstaff;	
    private String checkrewardstaffname;	
    private String checkrewardremark;	
    private String checkpersonid;
    private String checkdate;	
    private Integer  checkflag;	
    private String  checkinheadrewardstaff;	
    private String  checkinheadrewardstaffname;	
    private String checkinheadrewardremark;
    private String  checkinheadpersonid;
    private String  checkinheaddate;
    private Integer  checkinheadflag;
    private BigDecimal	checkrewardamt;
    private BigDecimal  checkinheadrewardamt;
    private Integer  invalid;
	public StaffrewardinfoId getId() {
		return id;
	}
	public void setId(StaffrewardinfoId id) {
		this.id = id;
	}
	public Integer getEntryflag() {
		return entryflag;
	}
	public void setEntryflag(Integer entryflag) {
		this.entryflag = entryflag;
	}
	public String getHandcompid() {
		return handcompid;
	}
	public void setHandcompid(String handcompid) {
		this.handcompid = handcompid;
	}
	public String getHandstaffid() {
		return handstaffid;
	}
	public void setHandstaffid(String handstaffid) {
		this.handstaffid = handstaffid;
	}
	public String getHandstaffinid() {
		return handstaffinid;
	}
	public void setHandstaffinid(String handstaffinid) {
		this.handstaffinid = handstaffinid;
	}
	public String getEntryreason() {
		return entryreason;
	}
	public void setEntryreason(String entryreason) {
		this.entryreason = entryreason;
	}
	public String getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}
	public String getEntrytype() {
		return entrytype;
	}
	public void setEntrytype(String entrytype) {
		this.entrytype = entrytype;
	}
	public String getCheckrewardstaff() {
		return checkrewardstaff;
	}
	public void setCheckrewardstaff(String checkrewardstaff) {
		this.checkrewardstaff = checkrewardstaff;
	}
	public String getCheckrewardstaffname() {
		return checkrewardstaffname;
	}
	public void setCheckrewardstaffname(String checkrewardstaffname) {
		this.checkrewardstaffname = checkrewardstaffname;
	}
	public String getCheckrewardremark() {
		return checkrewardremark;
	}
	public void setCheckrewardremark(String checkrewardremark) {
		this.checkrewardremark = checkrewardremark;
	}
	public String getCheckpersonid() {
		return checkpersonid;
	}
	public void setCheckpersonid(String checkpersonid) {
		this.checkpersonid = checkpersonid;
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
	public String getCheckinheadrewardstaff() {
		return checkinheadrewardstaff;
	}
	public void setCheckinheadrewardstaff(String checkinheadrewardstaff) {
		this.checkinheadrewardstaff = checkinheadrewardstaff;
	}
	public String getCheckinheadrewardstaffname() {
		return checkinheadrewardstaffname;
	}
	public void setCheckinheadrewardstaffname(String checkinheadrewardstaffname) {
		this.checkinheadrewardstaffname = checkinheadrewardstaffname;
	}
	public String getCheckinheadrewardremark() {
		return checkinheadrewardremark;
	}
	public void setCheckinheadrewardremark(String checkinheadrewardremark) {
		this.checkinheadrewardremark = checkinheadrewardremark;
	}
	public String getCheckinheadpersonid() {
		return checkinheadpersonid;
	}
	public void setCheckinheadpersonid(String checkinheadpersonid) {
		this.checkinheadpersonid = checkinheadpersonid;
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
	public BigDecimal getCheckrewardamt() {
		return checkrewardamt;
	}
	public void setCheckrewardamt(BigDecimal checkrewardamt) {
		this.checkrewardamt = checkrewardamt;
	}
	public BigDecimal getCheckinheadrewardamt() {
		return checkinheadrewardamt;
	}
	public void setCheckinheadrewardamt(BigDecimal checkinheadrewardamt) {
		this.checkinheadrewardamt = checkinheadrewardamt;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	public String getBentrycompid() {
		return bentrycompid;
	}
	public void setBentrycompid(String bentrycompid) {
		this.bentrycompid = bentrycompid;
	}
	public String getBentrybillid() {
		return bentrybillid;
	}
	public void setBentrybillid(String bentrybillid) {
		this.bentrybillid = bentrybillid;
	}
	public String getHandstaffname() {
		return handstaffname;
	}
	public void setHandstaffname(String handstaffname) {
		this.handstaffname = handstaffname;
	}
	public Integer getBillflag() {
		return billflag;
	}
	public void setBillflag(Integer billflag) {
		this.billflag = billflag;
	}
	
}