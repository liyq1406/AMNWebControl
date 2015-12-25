package com.amani.tools;

import com.amani.model.*;

import java.util.*;
/**
 * 
 * @author LiuJie Jun 24, 2013 2:31:45 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class SystemInformation {
	private String sysName;
	private String sysVersion;
	private String database;
	private String sysDate;
	private String sysTime;


	
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	public String getSysVersion() {
		return sysVersion;
	}
	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}
	public String getSysDate() {
		return sysDate;
	}
	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}
	public String getSysTime() {
		return sysTime;
	}
	public void setSysTime(String sysTime) {
		this.sysTime = sysTime;
	}
	
}
