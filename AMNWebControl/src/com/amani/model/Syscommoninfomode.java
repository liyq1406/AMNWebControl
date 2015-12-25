package com.amani.model;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Syscommoninfomode  implements java.io.Serializable {

	private SyscommoninfomodeId id;
	private String modename;
	private String modesource;
	private String parentmodeid;
    private String bmodeid;
    private Integer bmodetype;
    private String strModeTypeText;
    private String createdate;
    private String createemp;
    private String modesourceName;
    private String parentmodeName;
    private Integer invalid;
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getCreateemp() {
		return createemp;
	}
	public void setCreateemp(String createemp) {
		this.createemp = createemp;
	}
	public String getStrModeTypeText() {
		return strModeTypeText;
	}
	public void setStrModeTypeText(String strModeTypeText) {
		this.strModeTypeText = strModeTypeText;
	}
	public SyscommoninfomodeId getId() {
		return id;
	}
	public void setId(SyscommoninfomodeId id) {
		this.id = id;
	}
	public String getModename() {
		return modename;
	}
	public void setModename(String modename) {
		this.modename = modename;
	}
	public String getModesource() {
		return modesource;
	}
	public void setModesource(String modesource) {
		this.modesource = modesource;
	}
	public String getParentmodeid() {
		return parentmodeid;
	}
	public void setParentmodeid(String parentmodeid) {
		this.parentmodeid = parentmodeid;
	}
	public String getBmodeid() {
		return bmodeid;
	}
	public void setBmodeid(String bmodeid) {
		this.bmodeid = bmodeid;
	}
	public Integer getBmodetype() {
		return bmodetype;
	}
	public void setBmodetype(Integer bmodetype) {
		this.bmodetype = bmodetype;
	}
	public String getModesourceName() {
		return modesourceName;
	}
	public void setModesourceName(String modesourceName) {
		this.modesourceName = modesourceName;
	}
	public String getParentmodeName() {
		return parentmodeName;
	}
	public void setParentmodeName(String parentmodeName) {
		this.parentmodeName = parentmodeName;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
}