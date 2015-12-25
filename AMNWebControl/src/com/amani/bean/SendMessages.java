package com.amani.bean;

public class SendMessages {

	private String phones;
	private String smgText;
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getSmgText() {
		return smgText;
	}
	public void setSmgText(String smgText) {
		this.smgText = smgText;
	}
	public SendMessages(String phones, String smgText) {
		super();
		this.phones = phones;
		this.smgText = smgText;
	}
	
}
