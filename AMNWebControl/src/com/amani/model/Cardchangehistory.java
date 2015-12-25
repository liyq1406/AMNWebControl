package com.amani.model;

public class Cardchangehistory {
	private String changecompid;
	private String changecardno;
	private Integer changetype;
	private String changetypeText;
	private String changebillid;
	private Integer beforestate;
	private Integer afterstate;
	private String  chagedate;
	private String targetcardno;
	public String getTargetcardno() {
		return targetcardno;
	}
	public void setTargetcardno(String targetcardno) {
		this.targetcardno = targetcardno;
	}
	public String getChangecompid() {
		return changecompid;
	}
	public void setChangecompid(String changecompid) {
		this.changecompid = changecompid;
	}
	public String getChangecardno() {
		return changecardno;
	}
	public void setChangecardno(String changecardno) {
		this.changecardno = changecardno;
	}
	public Integer getChangetype() {
		return changetype;
	}
	public void setChangetype(Integer changetype) {
		this.changetype = changetype;
	}
	public String getChangetypeText() {
		return changetypeText;
	}
	public void setChangetypeText(String changetypeText) {
		this.changetypeText = changetypeText;
	}
	public String getChangebillid() {
		return changebillid;
	}
	public void setChangebillid(String changebillid) {
		this.changebillid = changebillid;
	}
	public Integer getBeforestate() {
		return beforestate;
	}
	public void setBeforestate(Integer beforestate) {
		this.beforestate = beforestate;
	}
	public Integer getAfterstate() {
		return afterstate;
	}
	public void setAfterstate(Integer afterstate) {
		this.afterstate = afterstate;
	}
	public String getChagedate() {
		return chagedate;
	}
	public void setChagedate(String chagedate) {
		this.chagedate = chagedate;
	}
}
