package com.amani.model;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Sysmodeinfo  implements java.io.Serializable {


	private SysmodeinfoId id;
	private String modulename;
	private Integer modulevel;
	private String moduletype;
	private String remark;
	private String moduletitle;
	private String moduleurl;
	private Integer modulewidth;
	private Integer moduleheight;
	private Integer showtype;
	public Integer getShowtype() {
		return showtype;
	}
	public void setShowtype(Integer showtype) {
		this.showtype = showtype;
	}
	public String getModuletitle() {
		return moduletitle;
	}
	public void setModuletitle(String moduletitle) {
		this.moduletitle = moduletitle;
	}
	public String getModuleurl() {
		return moduleurl;
	}
	public void setModuleurl(String moduleurl) {
		this.moduleurl = moduleurl;
	}
	public Integer getModulewidth() {
		return modulewidth;
	}
	public void setModulewidth(Integer modulewidth) {
		this.modulewidth = modulewidth;
	}
	public Integer getModuleheight() {
		return moduleheight;
	}
	public void setModuleheight(Integer moduleheight) {
		this.moduleheight = moduleheight;
	}
	public SysmodeinfoId getId() {
		return id;
	}
	public void setId(SysmodeinfoId id) {
		this.id = id;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	
	public Integer getModulevel() {
		return modulevel;
	}
	public void setModulevel(Integer modulevel) {
		this.modulevel = modulevel;
	}
	public String getModuletype() {
		return moduletype;
	}
	public void setModuletype(String moduletype) {
		this.moduletype = moduletype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}