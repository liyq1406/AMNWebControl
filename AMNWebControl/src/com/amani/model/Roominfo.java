package com.amani.model;

import java.math.BigDecimal;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Roominfo  implements java.io.Serializable {


	private String compno;//公司编号
	private String roomno;//房间编号
	private String roomname;//房间名称
	public String getCompno() {
		return compno;
	}
	public void setCompno(String compno) {
		this.compno = compno;
	}
	public String getRoomno() {
		return roomno;
	}
	public void setRoomno(String roomno) {
		this.roomno = roomno;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	
	
}