package com.amani.model;

import java.math.BigDecimal;



/**
 * PapercardId generated by MyEclipse - Hibernate Tools
 */

public class DcardnoinsertId  implements java.io.Serializable {


    // Fields    

     private String cinsertcompid;
     private String cinsertbillid;
     private Double seqno;
     
     public DcardnoinsertId(String cinsertcompid, String cinsertbillid,double seqno ) {
         this.cinsertcompid = cinsertcompid;
         this.cinsertbillid = cinsertbillid;
         this.seqno=seqno;
     }
     public DcardnoinsertId() {
     }
	public String getCinsertcompid() {
		return cinsertcompid;
	}
	public void setCinsertcompid(String cinsertcompid) {
		this.cinsertcompid = cinsertcompid;
	}
	public String getCinsertbillid() {
		return cinsertbillid;
	}
	public void setCinsertbillid(String cinsertbillid) {
		this.cinsertbillid = cinsertbillid;
	}
	public Double getSeqno() {
		return seqno;
	}
	public void setSeqno(Double seqno) {
		this.seqno = seqno;
	}
	
	
}