package com.amani.model;

import java.math.BigDecimal;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Companyinfo  implements java.io.Serializable {


	private String compno;
	private String compname;
	private String compstate;
	private String compphone;
	private String compaddress;
	private String comptradelicense;
	private String compfex;
	private String compzipcode;
	private String compadslno;
	private String compadslpassword;
	private String compipaddress;
	private String compipaddressex;
	private BigDecimal comparea;
	private BigDecimal comprent;
	private String compresponsible;
	private String compmode;
	private String compstateText;
	private String createdate;
	private String region;
	private String mangerPassword;
	private BigDecimal mirrornumber;
	private String shopwf1;//开店流程1
	private String shopwf2;
	private String shopwf3;
	private String shopwf4;
	private String shopwf5;
	private String shopwf6;
	private String shopwf7;
	private String shopwf8;
	private String shopwf9;
	private String shopwf10;//开店流程10
	private String ipadpwd;//ipad密码
	private Integer model;//美容提成模式
	private String imgUrl;//门店照片
	private String xcoordinate;
	private String ycoordinate;
	
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	public String getIpadpwd() {
		return ipadpwd;
	}
	public void setIpadpwd(String ipadpwd) {
		this.ipadpwd = ipadpwd;
	}
	public String getMangerPassword() {
		return mangerPassword;
	}
	public void setMangerPassword(String mangerPassword) {
		this.mangerPassword = mangerPassword;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getCompno() {
		return compno;
	}
	public void setCompno(String compno) {
		this.compno = compno;
	}
	public String getCompname() {
		return compname;
	}
	public void setCompname(String compname) {
		this.compname = compname;
	}
	public String getCompstate() {
		return compstate;
	}
	public void setCompstate(String compstate) {
		this.compstate = compstate;
	}
	public String getCompphone() {
		return compphone;
	}
	public void setCompphone(String compphone) {
		this.compphone = compphone;
	}
	public String getCompaddress() {
		return compaddress;
	}
	public void setCompaddress(String compaddress) {
		this.compaddress = compaddress;
	}
	public String getComptradelicense() {
		return comptradelicense;
	}
	public void setComptradelicense(String comptradelicense) {
		this.comptradelicense = comptradelicense;
	}
	public String getCompfex() {
		return compfex;
	}
	public void setCompfex(String compfex) {
		this.compfex = compfex;
	}
	public String getCompzipcode() {
		return compzipcode;
	}
	public void setCompzipcode(String compzipcode) {
		this.compzipcode = compzipcode;
	}
	public String getCompadslno() {
		return compadslno;
	}
	public void setCompadslno(String compadslno) {
		this.compadslno = compadslno;
	}
	public String getCompadslpassword() {
		return compadslpassword;
	}
	public void setCompadslpassword(String compadslpassword) {
		this.compadslpassword = compadslpassword;
	}

	public BigDecimal getComparea() {
		return comparea;
	}
	public void setComparea(BigDecimal comparea) {
		this.comparea = comparea;
	}
	public BigDecimal getComprent() {
		return comprent;
	}
	public void setComprent(BigDecimal comprent) {
		this.comprent = comprent;
	}
	public String getCompstateText() {
		return compstateText;
	}
	public void setCompstateText(String compstateText) {
		this.compstateText = compstateText;
	}
	public String getCompresponsible() {
		return compresponsible;
	}
	public void setCompresponsible(String compresponsible) {
		this.compresponsible = compresponsible;
	}
	public String getCompmode() {
		return compmode;
	}
	public void setCompmode(String compmode) {
		this.compmode = compmode;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCompipaddress() {
		return compipaddress;
	}
	public void setCompipaddress(String compipaddress) {
		this.compipaddress = compipaddress;
	}
	public String getCompipaddressex() {
		return compipaddressex;
	}
	public void setCompipaddressex(String compipaddressex) {
		this.compipaddressex = compipaddressex;
	}

	public BigDecimal getMirrornumber() {
		return mirrornumber;
	}
	/**
	 *  @BareFieldName : mirrornumber
	 *
	 *  @return  the mirrornumber
	 *
	 *  @param mirrornumber the mirrornumber to set
	 *
	 **/
	
	public void setMirrornumber(BigDecimal mirrornumber) {
		this.mirrornumber = mirrornumber;
	}
	public String getShopwf1() {
		return shopwf1;
	}
	public void setShopwf1(String shopwf1) {
		this.shopwf1 = shopwf1;
	}
	public String getShopwf2() {
		return shopwf2;
	}
	public void setShopwf2(String shopwf2) {
		this.shopwf2 = shopwf2;
	}
	public String getShopwf3() {
		return shopwf3;
	}
	public void setShopwf3(String shopwf3) {
		this.shopwf3 = shopwf3;
	}
	public String getShopwf4() {
		return shopwf4;
	}
	public void setShopwf4(String shopwf4) {
		this.shopwf4 = shopwf4;
	}
	public String getShopwf5() {
		return shopwf5;
	}
	public void setShopwf5(String shopwf5) {
		this.shopwf5 = shopwf5;
	}
	public String getShopwf6() {
		return shopwf6;
	}
	public void setShopwf6(String shopwf6) {
		this.shopwf6 = shopwf6;
	}
	public String getShopwf7() {
		return shopwf7;
	}
	public void setShopwf7(String shopwf7) {
		this.shopwf7 = shopwf7;
	}
	public String getShopwf8() {
		return shopwf8;
	}
	public void setShopwf8(String shopwf8) {
		this.shopwf8 = shopwf8;
	}
	public String getShopwf9() {
		return shopwf9;
	}
	public void setShopwf9(String shopwf9) {
		this.shopwf9 = shopwf9;
	}
	public String getShopwf10() {
		return shopwf10;
	}
	public void setShopwf10(String shopwf10) {
		this.shopwf10 = shopwf10;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getXcoordinate() {
		return xcoordinate;
	}
	public void setXcoordinate(String xcoordinate) {
		this.xcoordinate = xcoordinate;
	}
	public String getYcoordinate() {
		return ycoordinate;
	}
	public void setYcoordinate(String ycoordinate) {
		this.ycoordinate = ycoordinate;
	}
}