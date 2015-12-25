package com.amani.model;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Compchainstruct  implements java.io.Serializable {


	private String curcompno;
	private String parentcompno;
	private Integer complevel;
	private String createdate;
	private String curcompname;
	public String getCurcompno() {
		return curcompno;
	}
	public void setCurcompno(String curcompno) {
		this.curcompno = curcompno;
	}
	public String getParentcompno() {
		return parentcompno;
	}
	public void setParentcompno(String parentcompno) {
		this.parentcompno = parentcompno;
	}
	public Integer getComplevel() {
		return complevel;
	}
	public void setComplevel(Integer complevel) {
		this.complevel = complevel;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getCurcompname() {
		return curcompname;
	}
	public void setCurcompname(String curcompname) {
		this.curcompname = curcompname;
	}
	
}