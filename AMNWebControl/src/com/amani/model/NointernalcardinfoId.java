package com.amani.model;



public class NointernalcardinfoId  implements java.io.Serializable {


    // Fields    

     private String cardvesting;
     private String cardno;
     private String createcardtype;
     private String createcardtypename;


    // Constructors

    public String getCreatecardtype() {
		return createcardtype;
	}


	public void setCreatecardtype(String createcardtype) {
		this.createcardtype = createcardtype;
	}


	/** default constructor */
    public NointernalcardinfoId() {
    }

    
    /** full constructor */
    public NointernalcardinfoId(String cardvesting, String cardno) {
        this.cardvesting = cardvesting;
        this.cardno = cardno;
    }


	public String getCardvesting() {
		return cardvesting;
	}


	public void setCardvesting(String cardvesting) {
		this.cardvesting = cardvesting;
	}


	public String getCardno() {
		return cardno;
	}


	public void setCardno(String cardno) {
		this.cardno = cardno;
	}


	public String getCreatecardtypename() {
		return createcardtypename;
	}


	public void setCreatecardtypename(String createcardtypename) {
		this.createcardtypename = createcardtypename;
	}


}