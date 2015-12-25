package com.amani.model;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Useroverall  implements java.io.Serializable {


	private UseroverallId id;
    private String bmodevalue;
	private String descriptions;

	private String showflag;
	public String getShowflag() {
		return showflag;
	}
	public void setShowflag(String showflag) {
		this.showflag = showflag;
	}
	public UseroverallId getId() {
		return id;
	}
	public void setId(UseroverallId id) {
		this.id = id;
	}

	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public String getBmodevalue() {
		return bmodevalue;
	}
	public void setBmodevalue(String bmodevalue) {
		this.bmodevalue = bmodevalue;
	}

}