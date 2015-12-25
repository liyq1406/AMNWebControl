package com.amani.model;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Commoninfo  implements java.io.Serializable {


	private CommoninfoId id;
	private String infoname;
	private String codevalue;
	private String parentcodevalue;
	private String codesource;
	private String binfotype; //配合前端GRID显示
    private String bcodekey; //配合前端GRID显示
    private String bparentcodekey; //配合前端GRID显示
    private Integer useflag;
	public Integer getUseflag() {
		return useflag;
	}
	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}
	public CommoninfoId getId() {
		return id;
	}
	public void setId(CommoninfoId id) {
		this.id = id;
	}
	public String getInfoname() {
		return infoname;
	}
	public void setInfoname(String infoname) {
		this.infoname = infoname;
	}
	public String getCodevalue() {
		return codevalue;
	}
	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}
	public String getCodesource() {
		return codesource;
	}
	public void setCodesource(String codesource) {
		this.codesource = codesource;
	}
	public String getBinfotype() {
		return binfotype;
	}
	public void setBinfotype(String binfotype) {
		this.binfotype = binfotype;
	}
	public String getBcodekey() {
		return bcodekey;
	}
	public void setBcodekey(String bcodekey) {
		this.bcodekey = bcodekey;
	}
	public String getBparentcodekey() {
		return bparentcodekey;
	}
	public void setBparentcodekey(String bparentcodekey) {
		this.bparentcodekey = bparentcodekey;
	}
	public String getParentcodevalue() {
		return parentcodevalue;
	}
	public void setParentcodevalue(String parentcodevalue) {
		this.parentcodevalue = parentcodevalue;
	}
	
}