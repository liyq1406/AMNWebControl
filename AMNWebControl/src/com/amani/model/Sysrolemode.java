package com.amani.model;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Sysrolemode  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private SysrolemodeId id;
	private String browsepurview;
	private String editpurview;
	private String exportpurview;
	private String postpurview;
	private String confirmpurview;
	private String invalidpurview;
	
	private Boolean browsepurviewflag;
	private Boolean editpurviewflag;
	private Boolean exportpurviewflag;
	private Boolean postpurviewflag;
	private Boolean confirmpurviewflag;
	private Boolean invalidpurviewflag;
	
	
	private Integer disabledflag;
	private String strShowFlag;
	private String bsysmodeno;
	private String bsysmodename;
	private String bfunctionno;
	private String bfunctioname;
	private boolean testflag;
	public boolean isTestflag() {
		return testflag;
	}
	public void setTestflag(boolean testflag) {
		this.testflag = testflag;
	}
	public String getBsysmodeno() {
		return bsysmodeno;
	}
	public void setBsysmodeno(String bsysmodeno) {
		this.bsysmodeno = bsysmodeno;
	}
	public String getBsysmodename() {
		return bsysmodename;
	}
	public void setBsysmodename(String bsysmodename) {
		this.bsysmodename = bsysmodename;
	}
	public String getStrShowFlag() {
		return strShowFlag;
	}
	public void setStrShowFlag(String strShowFlag) {
		this.strShowFlag = strShowFlag;
	}
	public SysrolemodeId getId() {
		return id;
	}
	public void setId(SysrolemodeId id) {
		this.id = id;
	}
	public String getBrowsepurview() {
		return browsepurview;
	}
	public void setBrowsepurview(String browsepurview) {
		this.browsepurview = browsepurview;
	}
	public String getEditpurview() {
		return editpurview;
	}
	public void setEditpurview(String editpurview) {
		this.editpurview = editpurview;
	}
	public String getExportpurview() {
		return exportpurview;
	}
	public void setExportpurview(String exportpurview) {
		this.exportpurview = exportpurview;
	}
	public String getPostpurview() {
		return postpurview;
	}
	public void setPostpurview(String postpurview) {
		this.postpurview = postpurview;
	}
	public String getConfirmpurview() {
		return confirmpurview;
	}
	public void setConfirmpurview(String confirmpurview) {
		this.confirmpurview = confirmpurview;
	}

	public String getInvalidpurview() {
		return invalidpurview;
	}
	public void setInvalidpurview(String invalidpurview) {
		this.invalidpurview = invalidpurview;
	}
	public Integer getDisabledflag() {
		return disabledflag;
	}
	public void setDisabledflag(Integer disabledflag) {
		this.disabledflag = disabledflag;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getBfunctionno() {
		return bfunctionno;
	}
	public void setBfunctionno(String bfunctionno) {
		this.bfunctionno = bfunctionno;
	}
	public String getBfunctioname() {
		return bfunctioname;
	}
	public void setBfunctioname(String bfunctioname) {
		this.bfunctioname = bfunctioname;
	}
	public Boolean getBrowsepurviewflag() {
		return browsepurviewflag;
	}
	public void setBrowsepurviewflag(Boolean browsepurviewflag) {
		this.browsepurviewflag = browsepurviewflag;
	}
	public Boolean getEditpurviewflag() {
		return editpurviewflag;
	}
	public void setEditpurviewflag(Boolean editpurviewflag) {
		this.editpurviewflag = editpurviewflag;
	}
	public Boolean getExportpurviewflag() {
		return exportpurviewflag;
	}
	public void setExportpurviewflag(Boolean exportpurviewflag) {
		this.exportpurviewflag = exportpurviewflag;
	}
	public Boolean getPostpurviewflag() {
		return postpurviewflag;
	}
	public void setPostpurviewflag(Boolean postpurviewflag) {
		this.postpurviewflag = postpurviewflag;
	}
	public Boolean getConfirmpurviewflag() {
		return confirmpurviewflag;
	}
	public void setConfirmpurviewflag(Boolean confirmpurviewflag) {
		this.confirmpurviewflag = confirmpurviewflag;
	}
	public Boolean getInvalidpurviewflag() {
		return invalidpurviewflag;
	}
	public void setInvalidpurviewflag(Boolean invalidpurviewflag) {
		this.invalidpurviewflag = invalidpurviewflag;
	} 

}