package com.amani.model;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Sysuserinfo  implements java.io.Serializable {


	private String userno;
	private String username;
	private String userpwd;
	private Integer enableflag;
	private String userrole;
	private String frominnerno;
	private String fromcompno;
	private String datefrom;
	private String dateto;
	private String strEmpId;
	private String callcenterqueue;
	private String callcenterinterface;
	public String getCallcenterqueue() {
		return callcenterqueue;
	}
	public void setCallcenterqueue(String callcenterqueue) {
		this.callcenterqueue = callcenterqueue;
	}
	public String getCallcenterinterface() {
		return callcenterinterface;
	}
	public void setCallcenterinterface(String callcenterinterface) {
		this.callcenterinterface = callcenterinterface;
	}
	public String getStrEmpId() {
		return strEmpId;
	}
	public void setStrEmpId(String strEmpId) {
		this.strEmpId = strEmpId;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public Integer getEnableflag() {
		return enableflag;
	}
	public void setEnableflag(Integer enableflag) {
		this.enableflag = enableflag;
	}
	public String getUserrole() {
		return userrole;
	}
	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}
	public String getFrominnerno() {
		return frominnerno;
	}
	public void setFrominnerno(String frominnerno) {
		this.frominnerno = frominnerno;
	}
	public String getFromcompno() {
		return fromcompno;
	}
	public void setFromcompno(String fromcompno) {
		this.fromcompno = fromcompno;
	}
	public String getDatefrom() {
		return datefrom;
	}
	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}
	public String getDateto() {
		return dateto;
	}
	public void setDateto(String dateto) {
		this.dateto = dateto;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}