package com.amani.model;

/**
 * Yearsellinfo entity. @author MyEclipse Persistence Tools
 */

public class Yearsellinfo implements java.io.Serializable {

	// Fields

	private YearsellinfoId id;
	private String cardno;
	private String phone;
	private String name;
	private String cardtype;
	private String cid;
	private String idtphone;
	private String idtname;
	private String img;
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getIdtname() {
		return idtname;
	}

	public void setIdtname(String idtname) {
		this.idtname = idtname;
	}

	private String userid;
	private String accountdate;
	private String cashpaycode;
	private Double cashamt;
	private String storedpay;
	private Double storedamt;
	private String strImage;
	private Double zsamt;

	// Constructors

	public Double getZsamt() {
		return zsamt;
	}

	public void setZsamt(Double zsamt) {
		this.zsamt = zsamt;
	}

	public String getStrImage() {
		return strImage;
	}

	public void setStrImage(String strImage) {
		this.strImage = strImage;
	}

	/** default constructor */
	public Yearsellinfo() {
	}

	/** minimal constructor */
	public Yearsellinfo(YearsellinfoId id, String cardno, String phone,
			String name) {
		this.id = id;
		this.cardno = cardno;
		this.phone = phone;
		this.name = name;
	}

	/** full constructor */
	public Yearsellinfo(YearsellinfoId id, String cardno, String phone,
			String name, String cardtype, String cid, String idtphone,
			String userid, String accountdate, String cashpaycode,
			Double cashamt, String storedpay, Double storedamt) {
		this.id = id;
		this.cardno = cardno;
		this.phone = phone;
		this.name = name;
		this.cardtype = cardtype;
		this.cid = cid;
		this.idtphone = idtphone;
		this.userid = userid;
		this.accountdate = accountdate;
		this.cashpaycode = cashpaycode;
		this.cashamt = cashamt;
		this.storedpay = storedpay;
		this.storedamt = storedamt;
	}

	// Property accessors

	public YearsellinfoId getId() {
		return this.id;
	}

	public void setId(YearsellinfoId id) {
		this.id = id;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getIdtphone() {
		return this.idtphone;
	}

	public void setIdtphone(String idtphone) {
		this.idtphone = idtphone;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccountdate() {
		return this.accountdate;
	}

	public void setAccountdate(String accountdate) {
		this.accountdate = accountdate;
	}

	public String getCashpaycode() {
		return this.cashpaycode;
	}

	public void setCashpaycode(String cashpaycode) {
		this.cashpaycode = cashpaycode;
	}

	public Double getCashamt() {
		return this.cashamt;
	}

	public void setCashamt(Double cashamt) {
		this.cashamt = cashamt;
	}

	public String getStoredpay() {
		return this.storedpay;
	}

	public void setStoredpay(String storedpay) {
		this.storedpay = storedpay;
	}

	public Double getStoredamt() {
		return this.storedamt;
	}

	public void setStoredamt(Double storedamt) {
		this.storedamt = storedamt;
	}

}