package com.amani.model;

/**
 * Wxbandcard entity. @author MyEclipse Persistence Tools
 */

public class Wxbandcard implements java.io.Serializable {

	// Fields

	private String uuid;//唯一编号
	private String cardno;//会员卡号
	private String randomno;//18位随机码
	private String createdate;//创建日期
	private String validate;//有效日期
	private String openid;//OPENID

	// Constructors

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/** default constructor */
	public Wxbandcard() {
	}

	/** minimal constructor */
	public Wxbandcard(String uuid, String cardno, String randomno) {
		this.uuid = uuid;
		this.cardno = cardno;
		this.randomno = randomno;
	}

	/** full constructor */
	public Wxbandcard(String uuid, String cardno, String randomno,
			String createdate, String validate) {
		this.uuid = uuid;
		this.cardno = cardno;
		this.randomno = randomno;
		this.createdate = createdate;
		this.validate = validate;
	}

	// Property accessors

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getRandomno() {
		return this.randomno;
	}

	public void setRandomno(String randomno) {
		this.randomno = randomno;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getValidate() {
		return this.validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

}