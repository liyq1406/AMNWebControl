package com.amani.model;



public class DreturnproinfoId  implements java.io.Serializable {


    // Fields    
    private String  returncompid;
    private String  returnbillid;
    private Double  returnseqno;
    private Integer returnprotype;
    public DreturnproinfoId(String returncompid, String returnbillid,int returnprotype,double returnseqno ) {
        this.returncompid = returncompid;
        this.returnbillid = returnbillid;
        this.returnseqno=returnseqno;
        this.returnprotype=returnprotype;
    }

    public DreturnproinfoId()
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

	public Integer getReturnprotype() {
		return returnprotype;
	}

	public void setReturnprotype(Integer returnprotype) {
		this.returnprotype = returnprotype;
	}
}