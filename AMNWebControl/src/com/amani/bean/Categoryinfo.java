package com.amani.bean;

public class Categoryinfo {
	private String 	compno;//当前门店
	private String 	categoryno;//类别编号
	private String 	categoryname;//类别名称
	private String 	categorymark;//类别备注

	
	
	public String getCompno() {
		return compno;
	}
	/**
	 *  @BareFieldName : compno
	 *
	 *  @return  the compno
	 *
	 *  @param compno the compno to set
	 *
	 **/
	
	public void setCompno(String compno) {
		this.compno = compno;
	}
	/**
	 *  @BareFieldName : categoryno
	 *
	 *  @return  the categoryno
	 *
	 *
	 **/
	
	public String getCategoryno() {
		return categoryno;
	}
	/**
	 *  @BareFieldName : categoryno
	 *
	 *  @return  the categoryno
	 *
	 *  @param categoryno the categoryno to set
	 *
	 **/
	
	public void setCategoryno(String categoryno) {
		this.categoryno = categoryno;
	}
	/**
	 *  @BareFieldName : categoryname
	 *
	 *  @return  the categoryname
	 *
	 *
	 **/
	
	public String getCategoryname() {
		return categoryname;
	}
	/**
	 *  @BareFieldName : categoryname
	 *
	 *  @return  the categoryname
	 *
	 *  @param categoryname the categoryname to set
	 *
	 **/
	
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	
	public String getCategorymark() {
		return categorymark;
	}
	/**
	 *  @BareFieldName : categorymark
	 *
	 *  @return  the categorymark
	 *
	 *  @param categorymark the categorymark to set
	 *
	 **/
	
	public void setCategorymark(String categorymark) {
		this.categorymark = categorymark;
	}
	

	
}
