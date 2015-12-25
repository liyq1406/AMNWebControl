package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mcardapponline  implements java.io.Serializable {


	private McardapponlineId id;
	private String cappdate;
	private String capptime;
	private String cappempid;
	private Integer cappbillflag;
	private String cappopationper;
	private String cappopationdate;
	private String cappconfirmper;
	private String cappconfirmdate;
	private String cappconfirmcompid;
	private String bcappcompid;
    private String bcappcompbillid;
	private String bcappcompidText;
	private String cappempText;
    private String cappbillflagText;
    private Integer invalid;
	public McardapponlineId getId() {
		return id;
	}
	public void setId(McardapponlineId id) {
		this.id = id;
	}
	public String getCappdate() {
		return cappdate;
	}
	public void setCappdate(String cappdate) {
		this.cappdate = cappdate;
	}
	public String getCapptime() {
		return capptime;
	}
	public void setCapptime(String capptime) {
		this.capptime = capptime;
	}
	public String getCappempid() {
		return cappempid;
	}
	public void setCappempid(String cappempid) {
		this.cappempid = cappempid;
	}
	public Integer getCappbillflag() {
		return cappbillflag;
	}
	public void setCappbillflag(Integer cappbillflag) {
		this.cappbillflag = cappbillflag;
	}
	public String getCappopationper() {
		return cappopationper;
	}
	public void setCappopationper(String cappopationper) {
		this.cappopationper = cappopationper;
	}
	public String getCappopationdate() {
		return cappopationdate;
	}
	public void setCappopationdate(String cappopationdate) {
		this.cappopationdate = cappopationdate;
	}
	public String getCappconfirmper() {
		return cappconfirmper;
	}
	public void setCappconfirmper(String cappconfirmper) {
		this.cappconfirmper = cappconfirmper;
	}
	public String getCappconfirmdate() {
		return cappconfirmdate;
	}
	public void setCappconfirmdate(String cappconfirmdate) {
		this.cappconfirmdate = cappconfirmdate;
	}
	public String getCappconfirmcompid() {
		return cappconfirmcompid;
	}
	public void setCappconfirmcompid(String cappconfirmcompid) {
		this.cappconfirmcompid = cappconfirmcompid;
	}
	public String getBcappcompid() {
		return bcappcompid;
	}
	public void setBcappcompid(String bcappcompid) {
		this.bcappcompid = bcappcompid;
	}
	public String getBcappcompbillid() {
		return bcappcompbillid;
	}
	public void setBcappcompbillid(String bcappcompbillid) {
		this.bcappcompbillid = bcappcompbillid;
	}
	public String getCappbillflagText() {
		return cappbillflagText;
	}
	public void setCappbillflagText(String cappbillflagText) {
		this.cappbillflagText = cappbillflagText;
	}
	public String getBcappcompidText() {
		return bcappcompidText;
	}
	public void setBcappcompidText(String bcappcompidText) {
		this.bcappcompidText = bcappcompidText;
	}
	public String getCappempText() {
		return cappempText;
	}
	public void setCappempText(String cappempText) {
		this.cappempText = cappempText;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	
}