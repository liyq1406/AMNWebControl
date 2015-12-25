package com.amani.model;


/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mgoodsinventory  implements java.io.Serializable {


	private MgoodsinventoryId id;
	private String inventdate;	
	private String inventtime;	
    private String inventwareid;
    private String inventstaffid;
    private String inventstaffname;
    private String inventopationerid;
    private String inventopationdate;
    private String inventinserbillid;
    private String inventouterbillid;
    private String binventcompid;
    private String binventcompname;
    private String binventbillid;
    private Integer invalid;
    private Integer inventflag;
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	public MgoodsinventoryId getId() {
		return id;
	}
	public void setId(MgoodsinventoryId id) {
		this.id = id;
	}
	public String getInventdate() {
		return inventdate;
	}
	public void setInventdate(String inventdate) {
		this.inventdate = inventdate;
	}
	public String getInventtime() {
		return inventtime;
	}
	public void setInventtime(String inventtime) {
		this.inventtime = inventtime;
	}
	public String getInventwareid() {
		return inventwareid;
	}
	public void setInventwareid(String inventwareid) {
		this.inventwareid = inventwareid;
	}
	public String getInventstaffid() {
		return inventstaffid;
	}
	public void setInventstaffid(String inventstaffid) {
		this.inventstaffid = inventstaffid;
	}
	public String getInventstaffname() {
		return inventstaffname;
	}
	public void setInventstaffname(String inventstaffname) {
		this.inventstaffname = inventstaffname;
	}
	public String getInventopationerid() {
		return inventopationerid;
	}
	public void setInventopationerid(String inventopationerid) {
		this.inventopationerid = inventopationerid;
	}
	public String getInventopationdate() {
		return inventopationdate;
	}
	public void setInventopationdate(String inventopationdate) {
		this.inventopationdate = inventopationdate;
	}
	public String getInventinserbillid() {
		return inventinserbillid;
	}
	public void setInventinserbillid(String inventinserbillid) {
		this.inventinserbillid = inventinserbillid;
	}
	public String getInventouterbillid() {
		return inventouterbillid;
	}
	public void setInventouterbillid(String inventouterbillid) {
		this.inventouterbillid = inventouterbillid;
	}
	public String getBinventcompid() {
		return binventcompid;
	}
	public void setBinventcompid(String binventcompid) {
		this.binventcompid = binventcompid;
	}
	public String getBinventcompname() {
		return binventcompname;
	}
	public void setBinventcompname(String binventcompname) {
		this.binventcompname = binventcompname;
	}
	public String getBinventbillid() {
		return binventbillid;
	}
	public void setBinventbillid(String binventbillid) {
		this.binventbillid = binventbillid;
	}
	public Integer getInventflag() {
		return inventflag;
	}
	public void setInventflag(Integer inventflag) {
		this.inventflag = inventflag;
	}
	
}