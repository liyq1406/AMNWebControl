package com.amani.model;
public class MreturnproinfoId  implements java.io.Serializable {


    // Fields    

     private String returncompid;
     private String returnbillid;
   
     
     public MreturnproinfoId(String returncompid, String returnbillid ) {
         this.returncompid = returncompid;
         this.returnbillid = returnbillid;
     }
     public MreturnproinfoId() {
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
	
}