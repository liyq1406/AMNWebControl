package com.amani.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 微信商品表
 */
@Entity
@Table(name="wx_product")
public class WxProduct implements Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 				//主键编号
	private Integer cid;	//所属的分类，关联表(goodsinfo )
	private String name; //名称
	private BigDecimal price; //微信产品价格
	private Integer num; //购买限制，0为不限购
	private Integer type; //商品类型(1：面部，2：身体 3：套餐)
	private Integer catgory; //商品分类，1：到店，2：家居
	@Column(name="[group]")
	private Integer group; //所属分组,关联[amn_product_group]
	private Integer sumnum; //商品总数
	private BigDecimal originalcost; //原价
	private Integer isopen; //是否上架，默认是false
	private String card_id; //优惠券/卡券类型id,关联表[wx_card]
	private String content; //详情
	private Integer ishot; //是否热门，默认为fals
	private String intro; //产品参数
	private String mainimageurl; //主图的url
	private Integer sort; //排序顺序
	private Integer minute; //大概分钟
	@Transient
	private String strJson;
	@Transient
	private String mainimagename;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getCatgory() {
		return catgory;
	}
	public void setCatgory(Integer catgory) {
		this.catgory = catgory;
	}
	public Integer getGroup() {
		return group;
	}
	public void setGroup(Integer group) {
		this.group = group;
	}
	public Integer getSumnum() {
		return sumnum;
	}
	public void setSumnum(Integer sumnum) {
		this.sumnum = sumnum;
	}
	public BigDecimal getOriginalcost() {
		return originalcost;
	}
	public void setOriginalcost(BigDecimal originalcost) {
		this.originalcost = originalcost;
	}
	public Integer getIsopen() {
		return isopen;
	}
	public void setIsopen(Integer isopen) {
		this.isopen = isopen;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIshot() {
		return ishot;
	}
	public void setIshot(Integer ishot) {
		this.ishot = ishot;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getMainimageurl() {
		return mainimageurl;
	}
	public void setMainimageurl(String mainimageurl) {
		this.mainimageurl = mainimageurl;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getMinute() {
		return minute;
	}
	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	public String getStrJson() {
		return strJson;
	}
	public void setStrJson(String strJson) {
		this.strJson = strJson;
	}
	public String getMainimagename() {
		return mainimagename;
	}
	public void setMainimagename(String mainimagename) {
		this.mainimagename = mainimagename;
	}
}
