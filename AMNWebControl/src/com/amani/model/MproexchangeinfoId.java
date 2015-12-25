package com.amani.model;

import java.math.BigDecimal;



public class MproexchangeinfoId  implements java.io.Serializable {


    // Fields    

     private String changecompid;
     private String changebillid;
   
     
     public MproexchangeinfoId(String changecompid, String changebillid ) {
         this.changecompid = changecompid;
         this.changebillid = changebillid;
     }
     public MproexchangeinfoId() {
     }
	public String getChangecompid() {
		return changecompid;
	}
	public void setChangecompid(String changecompid) {
		this.changecompid = changecompid;
	}
	public String getChangebillid() {
		return changebillid;
	}
	public void setChangebillid(String changebillid) {
		this.changebillid = changebillid;
	}

	
}