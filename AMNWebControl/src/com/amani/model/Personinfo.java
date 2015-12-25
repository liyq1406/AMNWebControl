package com.amani.model;

import java.util.List;

public class Personinfo {
	  private String fingerMacineIp; //考勤机ip
	  private int  fingerMacineId;
	  private  int personID; //员工指纹
	  private String password;
	  private int cardNo;
	  private String userName; //员工姓名
	  private int dept;
	  private int group;
	  private String time;     //考勤时间
	  private int kqOption;
	  private int fpMark;
	  private int other;
	  private String ddate;    //考勤日期
	  private String ttime;     //考勤时间
	  private int stat;          
	  private int workType;
	  private int workTyte;
	  private int machineid;
	  private int id;
	  
	  private List<Personinfo> IsPersoninfo;
	  

	public List<Personinfo> getIsPersoninfo() {
		return IsPersoninfo;
	}
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getDept() {
		return dept;
	}
	public void setDept(int dept) {
		this.dept = dept;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	
	
	public int getKqOption() {
		return kqOption;
	}
	public void setKqOption(int kqOption) {
		this.kqOption = kqOption;
	}
	public int getFpMark() {
		return fpMark;
	}
	public void setFpMark(int fpMark) {
		this.fpMark = fpMark;
	}
	public int getOther() {
		return other;
	}
	public void setOther(int other) {
		this.other = other;
	}
	public String getDdate() {
		return ddate;
	}
	public void setDdate(String ddate) {
		this.ddate = ddate;
	}
	public String getTtime() {
		return ttime;
	}
	public void setTtime(String ttime) {
		this.ttime = ttime;
	}
	public String getFingerMacineIp() {
		return fingerMacineIp;
	}
	public void setFingerMacineIp(String fingerMacineIp) {
		this.fingerMacineIp = fingerMacineIp;
	}
	public int getFingerMacineId() {
		return fingerMacineId;
	}
	public void setFingerMacineId(int fingerMacineId) {
		this.fingerMacineId = fingerMacineId;
	}
	public int getStat() {
		return stat;
	}
	public void setStat(int stat) {
		this.stat = stat;
	}

	public int getWorkType() {
		return workType;
	}
	public void setWorkType(int workType) {
		this.workType = workType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getMachineid() {
		return machineid;
	}
	public void setMachineid(int machineid) {
		this.machineid = machineid;
	}
	public void setIsPersoninfo(List<Personinfo> isPersoninfo) {
		IsPersoninfo = isPersoninfo;
	}
	public int getWorkTyte() {
		return workTyte;
	}
	public void setWorkTyte(int workTyte) {
		this.workTyte = workTyte;
	}


}
