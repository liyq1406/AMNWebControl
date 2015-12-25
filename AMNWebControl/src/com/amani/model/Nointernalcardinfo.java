package com.amani.model;

import java.math.BigDecimal;


public class Nointernalcardinfo  implements java.io.Serializable {
    // Fields    
	private static final long serialVersionUID = 2430164525225856755L;
	private NointernalcardinfoId id;
     private Integer cardtype;
     private BigDecimal cardfaceamt;
     private Integer carduseflag;
     private Integer entrytype;
     private Integer cardstate;
     private String usedate;
     private String useinproject;
     private String oldvalidate;
     private String lastvalidate;
     private String enabledate;
     private String bcardvesting;
     private String bcardno;
     private String carduseflagText;
     private String cardstateText;
     private String entrytypeText;
     private Integer uespassward;
     private String cardpassward;
     private Integer createtype;
     private Integer createcardtype;
     private String createcardtypename;
     private String phone;
     private String remark;
     private String cardId;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getCreatecardtype() {
		return createcardtype;
	}
	public void setCreatecardtype(Integer createcardtype) {
		this.createcardtype = createcardtype;
	}
	public Integer getUespassward() {
		return uespassward;
	}
	public void setUespassward(Integer uespassward) {
		this.uespassward = uespassward;
	}
	public String getCardpassward() {
		return cardpassward;
	}
	public void setCardpassward(String cardpassward) {
		this.cardpassward = cardpassward;
	}
	public String getCardstateText() {
		return cardstateText;
	}
	public void setCardstateText(String cardstateText) {
		this.cardstateText = cardstateText;
	}
	public String getCarduseflagText() {
		return carduseflagText;
	}
	public void setCarduseflagText(String carduseflagText) {
		this.carduseflagText = carduseflagText;
	}
	public NointernalcardinfoId getId() {
		return id;
	}
	public void setId(NointernalcardinfoId id) {
		this.id = id;
	}

	public Integer getCardtype() {
		return cardtype;
	}
	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}
	
	public Integer getCarduseflag() {
		return carduseflag;
	}
	public void setCarduseflag(Integer carduseflag) {
		this.carduseflag = carduseflag;
	}
	public Integer getEntrytype() {
		return entrytype;
	}
	public void setEntrytype(Integer entrytype) {
		this.entrytype = entrytype;
	}
	public Integer getCardstate() {
		return cardstate;
	}
	public void setCardstate(Integer cardstate) {
		this.cardstate = cardstate;
	}
	public String getUsedate() {
		return usedate;
	}
	public void setUsedate(String usedate) {
		this.usedate = usedate;
	}
	public String getUseinproject() {
		return useinproject;
	}
	public void setUseinproject(String useinproject) {
		this.useinproject = useinproject;
	}
	public String getOldvalidate() {
		return oldvalidate;
	}
	public void setOldvalidate(String oldvalidate) {
		this.oldvalidate = oldvalidate;
	}
	public String getLastvalidate() {
		return lastvalidate;
	}
	public void setLastvalidate(String lastvalidate) {
		this.lastvalidate = lastvalidate;
	}
	public BigDecimal getCardfaceamt() {
		return cardfaceamt;
	}
	public void setCardfaceamt(BigDecimal cardfaceamt) {
		this.cardfaceamt = cardfaceamt;
	}
	public String getBcardvesting() {
		return bcardvesting;
	}
	public void setBcardvesting(String bcardvesting) {
		this.bcardvesting = bcardvesting;
	}
	public String getBcardno() {
		return bcardno;
	}
	public void setBcardno(String bcardno) {
		this.bcardno = bcardno;
	}
	public String getEntrytypeText() {
		return entrytypeText;
	}
	public void setEntrytypeText(String entrytypeText) {
		this.entrytypeText = entrytypeText;
	}
	public String getEnabledate() {
		return enabledate;
	}
	public void setEnabledate(String enabledate) {
		this.enabledate = enabledate;
	}
	public Integer getCreatetype() {
		return createtype;
	}
	public void setCreatetype(Integer createtype) {
		this.createtype = createtype;
	}
	public String getCreatecardtypename() {
		return createcardtypename;
	}
	public void setCreatecardtypename(String createcardtypename) {
		this.createcardtypename = createcardtypename;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
}