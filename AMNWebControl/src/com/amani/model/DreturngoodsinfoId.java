package com.amani.model;



public class DreturngoodsinfoId  implements java.io.Serializable {


    // Fields    
    private String returncompid;
    private String returnbillid;
    private Double returnseqno;
    
    public DreturngoodsinfoId(String returncompid, String returnbillid,double returnseqno ) {
        this.returncompid = returncompid;
        this.returnbillid = returnbillid;
        this.returnseqno=returnseqno;
    }

    public DreturngoodsinfoId()
    {
    	
    }
	public String getReturncompid() {
		return returncompid;
	}

	public void setReturncompid(String returncompid) {
		this.returncompid = returncompid;
	}

	public String getReturnbillid() {
		return returnbillid;
	}

	public void setReturnbillid(String returnbillid) {
		this.returnbillid = returnbillid;
	}

	public Double getReturnseqno() {
		return returnseqno;
	}

	public void setReturnseqno(Double returnseqno) {
		this.returnseqno = returnseqno;
	}
    
	
}