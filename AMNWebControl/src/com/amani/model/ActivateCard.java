package com.amani.model;

public class ActivateCard {
	//membership_number和code值保持一致。
	private String membership_number;//会员卡编号，由开发者填入，作为序列号显示在用户的卡包里。可与Code码保持等值。
	private String code;//创建会员卡时获取的初始code。
	
	private String card_id;//微信card_id;
	private float init_bonus;//初始积分，不填为0。
	private float init_balance;//初始余额，不填为0。
	public String getMembership_number() {
		return membership_number;
	}
	public void setMembership_number(String membership_number) {
		this.membership_number = membership_number;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public float getInit_bonus() {
		return init_bonus;
	}
	public void setInit_bonus(float init_bonus) {
		this.init_bonus = init_bonus;
	}
	public float getInit_balance() {
		return init_balance;
	}
	public void setInit_balance(float init_balance) {
		this.init_balance = init_balance;
	}
	
	
	
}
