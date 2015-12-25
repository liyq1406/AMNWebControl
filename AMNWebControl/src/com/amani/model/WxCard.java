package com.amani.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微信卡券表
 */
@Entity
@Table(name="wx_card")
public class WxCard implements Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false,updatable=false)
	private Integer id; 				//主键编号
	private BigDecimal price;	//卡券对应的价格
	private String compid;	//卡券使用的门店
	private String paramid;	//卡券的在erp中的code，
	private String card_id;	//卡券类型id
	private String code_type;	//Code展示类型，"CODE_TYPE_TEXT"，文本；"CODE_TYPE_BARCODE"，一维码 ；"CODE_TYPE_QRCODE"，二维码；"CODE_TYPE_ONLY_QRCODE",二维码无code显示；"CODE_TYPE_ONLY_BARCODE",一维码无code显示；
	private String logo_url;	//卡券的商户logo，建议像素为300*300。
	private String brand_name;	//商户名字,字数上限为12个汉字。
	private String title;	//卡券名，字数上限为9个汉字。(建议涵盖卡券属性、服务及金额)。
	private String sub_title;	//券名，字数上限为18个汉字。鸳鸯锅底+牛肉1份+土豆一份
	private String color;	//券颜色。按色彩规范标注填写Color010-Color100。
	private String notice;	//卡券使用提醒，字数上限为16个汉字。
	private String description;	//卡券使用说明，字数上限为1024个汉字。
	private Integer quantity;	//卡券库存的数量，不支持填写0，上限为100000000。
	private String type;		//default 'DATE_TYPE_FIX_TIME_RANGE',  --DATE_TYPE_FIX_TIME_RANGE表示固定日期区间，DATE_TYPE_FIX_TERM表示固定时长（自领取后按天算。
	private Integer begin_timestamp;//type为DATE_TYPE_FIX_TIME_RANGE时专用，表示起用时间。从1970年1月1日00:00:00至起用时间的秒数，最终需转换为字符串形态传入。（东八区时间，单位为秒）
	private Integer end_timestamp;	//type为DATE_TYPE_FIX_TIME_RANGE时专用，表示结束时间，建议设置为截止日期的23:59:59过期。（东八区时间，单位为秒）
	private Integer fixed_term;		//type为DATE_TYPE_FIX_TERM时专用，表示自领取后多少天内有效，领取后当天有效填写0。（单位为天）
	private Integer fixed_begin_term;	//--type为DATE_TYPE_FIX_TERM时专用，表示自领取后多少天开始生效。（单位为天）  
	private String card_type;	//GROUPON	团购券类型;CASH	代金券类型。DISCOUNT	折扣券类型。	GIFT	礼品券类型。GENERAL_COUPON	优惠券类型。
	private String deal_detail;	//团购券专用，团购详情。
	private Integer least_cost;	//代金券专用，表示起用金额。（单位为分)
	private Integer reduce_cost;//代金券专用，表示减免金额。（单位为分）
	private Integer discount;	//折扣券专用，表示打折额度（百分比）。填30就是七折。
	private String gift;		//礼品券专用，填写礼品的名称。
	private String default_detail;	//优惠券专用，填写优惠详情
	private Integer state;	//创建，1，已审核
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getCompid() {
		return compid;
	}
	public void setCompid(String compid) {
		this.compid = compid;
	}
	public String getParamid() {
		return paramid;
	}
	public void setParamid(String paramid) {
		this.paramid = paramid;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getCode_type() {
		return code_type;
	}
	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}
	public String getLogo_url() {
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getBegin_timestamp() {
		return begin_timestamp;
	}
	public void setBegin_timestamp(Integer begin_timestamp) {
		this.begin_timestamp = begin_timestamp;
	}
	public Integer getEnd_timestamp() {
		return end_timestamp;
	}
	public void setEnd_timestamp(Integer end_timestamp) {
		this.end_timestamp = end_timestamp;
	}
	public Integer getFixed_term() {
		return fixed_term;
	}
	public void setFixed_term(Integer fixed_term) {
		this.fixed_term = fixed_term;
	}
	public Integer getFixed_begin_term() {
		return fixed_begin_term;
	}
	public void setFixed_begin_term(Integer fixed_begin_term) {
		this.fixed_begin_term = fixed_begin_term;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getDeal_detail() {
		return deal_detail;
	}
	public void setDeal_detail(String deal_detail) {
		this.deal_detail = deal_detail;
	}
	public Integer getLeast_cost() {
		return least_cost;
	}
	public void setLeast_cost(Integer least_cost) {
		this.least_cost = least_cost;
	}
	public Integer getReduce_cost() {
		return reduce_cost;
	}
	public void setReduce_cost(Integer reduce_cost) {
		this.reduce_cost = reduce_cost;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public String getGift() {
		return gift;
	}
	public void setGift(String gift) {
		this.gift = gift;
	}
	public String getDefault_detail() {
		return default_detail;
	}
	public void setDefault_detail(String default_detail) {
		this.default_detail = default_detail;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
