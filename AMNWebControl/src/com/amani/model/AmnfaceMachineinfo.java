package com.amani.model;

public class AmnfaceMachineinfo {
	private String compno;
	private String compname;
	private String machineno;
	private String machineip;
	private String machineversion;
	private String createfromdate;
	private String createtodate;
	private int createseqno;
	public int getCreateseqno() {
		return createseqno;
	}
	public void setCreateseqno(int createseqno) {
		this.createseqno = createseqno;
	}
	public String getCompno() {
		return compno;
	}
	public void setCompno(String compno) {
		this.compno = compno;
	}
	public String getCompname() {
		return compname;
	}
	public void setCompname(String compname) {
		this.compname = compname;
	}
	public String getMachineno() {
		return machineno;
	}
	public void setMachineno(String machineno) {
		this.machineno = machineno;
	}
	public String getMachineversion() {
		return machineversion;
	}
	public void setMachineversion(String machineversion) {
		this.machineversion = machineversion;
	}
	public String getCreatefromdate() {
		return createfromdate;
	}
	public void setCreatefromdate(String createfromdate) {
		this.createfromdate = createfromdate;
	}
	public String getCreatetodate() {
		return createtodate;
	}
	public void setCreatetodate(String createtodate) {
		this.createtodate = createtodate;
	}
	public String getMachineip() {
		return machineip;
	}
	public void setMachineip(String machineip) {
		this.machineip = machineip;
	}
}
