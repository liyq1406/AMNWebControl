package com.amani.model;



public class SendpointcardId  implements java.io.Serializable {


    // Fields    

     private String sendcompid;
     private String sendbillid;
     private Integer sendtype;


    // Constructors

    /** default constructor */
    public SendpointcardId() {
    }

    
    /** full constructor */
    public SendpointcardId(String sendcompid, String sendbillid, Integer sendtype) {
        this.sendcompid = sendcompid;
        this.sendbillid = sendbillid;
        this.sendtype = sendtype;
    }


	public String getSendcompid() {
		return sendcompid;
	}


	public void setSendcompid(String sendcompid) {
		this.sendcompid = sendcompid;
	}


	public String getSendbillid() {
		return sendbillid;
	}


	public void setSendbillid(String sendbillid) {
		this.sendbillid = sendbillid;
	}


	public Integer getSendtype() {
		return sendtype;
	}


	public void setSendtype(Integer sendtype) {
		this.sendtype = sendtype;
	}

   
}