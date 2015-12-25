package com.amani.model;

import java.math.BigDecimal;

public class Salaryrateinfo {
	
	private String compno				   				;	// 公司别
	//产品提成
	private BigDecimal cpcostratemr						;   // 美容产品成本系数
	private BigDecimal cpsalaryratemr					;   // 美容产品提成系数
	private BigDecimal cpcostratemf						;   // 美发产品成本系数
	private BigDecimal cpsalaryratemf					;   // 美发产品提成系数
	private BigDecimal cpcostratemj						;   // 美甲产品成本系数
	private BigDecimal cpsalaryratemj					;   // 美甲产品提成系数
	private BigDecimal cpcostrateks						;  	// 卡诗产品成本系数
	private BigDecimal cpsalaryrateks					;   // 卡诗产品提成系数
	
	//卡金提成
	private BigDecimal kjsalaryratetrsa					;	//一;二;三级烫染师卡金提成系数
	private BigDecimal kjsalaryratetrsb					;	//四级烫染师;烫染督导卡金提成系数
	private BigDecimal kjsalaryratemrc					;	//美容师C卡金提成系数
	private BigDecimal kjsalaryratemr					;	//其他美容师提成系数
	private BigDecimal kjsalaryratemf					;	//美发人员提成系数
	
	//疗程兑换提成
	private BigDecimal dhcostratetrsa					;	//一;二;三级烫染师疗程兑换成本系数
	private BigDecimal dhcostratetrsb					;	//四级烫染师;烫染督导疗程兑换成本系数
	private BigDecimal dhcostratemf						;	//美发人员疗程兑换成本系数
	private BigDecimal dhsalaryratetrsa					;	//一;二;三级烫染师疗程兑换提成系数
	private BigDecimal dhsalaryratetrsb					;	//四级烫染师;烫染督导疗程兑换提成系数
	private BigDecimal dhsalaryratemf					;	//美发人员疗程兑换提成系数
	
	//合作项目提成
	private BigDecimal hzsalaryrateqh					;	//全韩私密合作提成比率
	private BigDecimal hzsalaryratejd					;	//暨大合作提成比率
	
	private BigDecimal jfdhsalaryreward					;	//建发疗程兑换提成奖励
	private BigDecimal jjdhyejireward					;	//肩颈疗程兑换卡金奖励
	private BigDecimal jjfwsalaryreward					;	//肩颈服务提成奖励
	private BigDecimal jsrsalaryreward					;	//介绍人提成奖励
	private BigDecimal tjsrsalarycost					;	//被介绍人扣除提成
	
	//项目消费特殊提成方式
	private BigDecimal mfsalaryratefiveup				;	//美发人员5个月上的提成比率(限美发类项目)
	private BigDecimal mfsalaryratefivedown				;	//美发人员5个月下的提成比率(限美发类项目)
	
	private BigDecimal olccostyejifixed					;	//老疗程卡固定业绩
	private BigDecimal olccostsalaryfixed				;	//老疗程卡固定提成
	private BigDecimal nlccostratetr					;	//烫染师疗程成本
	
	private BigDecimal yjkcostratemrmf					;	//原价卡美容美发成本
	private BigDecimal yjksalaryratemrmf				;	//原价卡美容美发提成比率
	private BigDecimal yjkcostratetr					;	//原价卡烫染师成本比率
	
	private BigDecimal salaryratetra					;	//一;二;三级烫染师提成比率
	private BigDecimal salaryratetrb					;	//四级烫染师;烫染督导提成比率
	
	private BigDecimal xjccostratesjs					;	//设计师洗剪吹成本
	private BigDecimal xjccostratesx					;	//首席洗剪吹成本
	private BigDecimal xjccostratezj					;	//总监洗剪吹成本
	
	private BigDecimal xjcsalaryfixeddb					;	//洗剪吹中工达标 10
	private BigDecimal xjcsalaryfixedndb				;	//洗剪吹中工不达标 5
	private BigDecimal xjcsalaryfixednhg				;	//洗剪吹中工不合格 3
	
	private BigDecimal mrsalaryfixedtmk					;	//美容新项目条码卡固定提成
	private BigDecimal mrsalaryfixedty					;	//美容新项目体验固定提成
	public String getCompno() {
		return compno;
	}
	public void setCompno(String compno) {
		this.compno = compno;
	}
	public BigDecimal getCpcostratemr() {
		return cpcostratemr;
	}
	public void setCpcostratemr(BigDecimal cpcostratemr) {
		this.cpcostratemr = cpcostratemr;
	}
	public BigDecimal getCpsalaryratemr() {
		return cpsalaryratemr;
	}
	public void setCpsalaryratemr(BigDecimal cpsalaryratemr) {
		this.cpsalaryratemr = cpsalaryratemr;
	}
	public BigDecimal getCpcostratemf() {
		return cpcostratemf;
	}
	public void setCpcostratemf(BigDecimal cpcostratemf) {
		this.cpcostratemf = cpcostratemf;
	}
	public BigDecimal getCpsalaryratemf() {
		return cpsalaryratemf;
	}
	public void setCpsalaryratemf(BigDecimal cpsalaryratemf) {
		this.cpsalaryratemf = cpsalaryratemf;
	}
	public BigDecimal getCpcostratemj() {
		return cpcostratemj;
	}
	public void setCpcostratemj(BigDecimal cpcostratemj) {
		this.cpcostratemj = cpcostratemj;
	}
	public BigDecimal getCpsalaryratemj() {
		return cpsalaryratemj;
	}
	public void setCpsalaryratemj(BigDecimal cpsalaryratemj) {
		this.cpsalaryratemj = cpsalaryratemj;
	}
	public BigDecimal getCpcostrateks() {
		return cpcostrateks;
	}
	public void setCpcostrateks(BigDecimal cpcostrateks) {
		this.cpcostrateks = cpcostrateks;
	}
	public BigDecimal getCpsalaryrateks() {
		return cpsalaryrateks;
	}
	public void setCpsalaryrateks(BigDecimal cpsalaryrateks) {
		this.cpsalaryrateks = cpsalaryrateks;
	}
	public BigDecimal getKjsalaryratetrsa() {
		return kjsalaryratetrsa;
	}
	public void setKjsalaryratetrsa(BigDecimal kjsalaryratetrsa) {
		this.kjsalaryratetrsa = kjsalaryratetrsa;
	}
	public BigDecimal getKjsalaryratetrsb() {
		return kjsalaryratetrsb;
	}
	public void setKjsalaryratetrsb(BigDecimal kjsalaryratetrsb) {
		this.kjsalaryratetrsb = kjsalaryratetrsb;
	}
	public BigDecimal getKjsalaryratemrc() {
		return kjsalaryratemrc;
	}
	public void setKjsalaryratemrc(BigDecimal kjsalaryratemrc) {
		this.kjsalaryratemrc = kjsalaryratemrc;
	}
	public BigDecimal getKjsalaryratemr() {
		return kjsalaryratemr;
	}
	public void setKjsalaryratemr(BigDecimal kjsalaryratemr) {
		this.kjsalaryratemr = kjsalaryratemr;
	}
	public BigDecimal getKjsalaryratemf() {
		return kjsalaryratemf;
	}
	public void setKjsalaryratemf(BigDecimal kjsalaryratemf) {
		this.kjsalaryratemf = kjsalaryratemf;
	}
	public BigDecimal getDhcostratetrsa() {
		return dhcostratetrsa;
	}
	public void setDhcostratetrsa(BigDecimal dhcostratetrsa) {
		this.dhcostratetrsa = dhcostratetrsa;
	}
	public BigDecimal getDhcostratetrsb() {
		return dhcostratetrsb;
	}
	public void setDhcostratetrsb(BigDecimal dhcostratetrsb) {
		this.dhcostratetrsb = dhcostratetrsb;
	}
	public BigDecimal getDhcostratemf() {
		return dhcostratemf;
	}
	public void setDhcostratemf(BigDecimal dhcostratemf) {
		this.dhcostratemf = dhcostratemf;
	}
	public BigDecimal getDhsalaryratetrsa() {
		return dhsalaryratetrsa;
	}
	public void setDhsalaryratetrsa(BigDecimal dhsalaryratetrsa) {
		this.dhsalaryratetrsa = dhsalaryratetrsa;
	}
	public BigDecimal getDhsalaryratetrsb() {
		return dhsalaryratetrsb;
	}
	public void setDhsalaryratetrsb(BigDecimal dhsalaryratetrsb) {
		this.dhsalaryratetrsb = dhsalaryratetrsb;
	}
	public BigDecimal getDhsalaryratemf() {
		return dhsalaryratemf;
	}
	public void setDhsalaryratemf(BigDecimal dhsalaryratemf) {
		this.dhsalaryratemf = dhsalaryratemf;
	}
	public BigDecimal getHzsalaryrateqh() {
		return hzsalaryrateqh;
	}
	public void setHzsalaryrateqh(BigDecimal hzsalaryrateqh) {
		this.hzsalaryrateqh = hzsalaryrateqh;
	}
	public BigDecimal getHzsalaryratejd() {
		return hzsalaryratejd;
	}
	public void setHzsalaryratejd(BigDecimal hzsalaryratejd) {
		this.hzsalaryratejd = hzsalaryratejd;
	}
	public BigDecimal getJfdhsalaryreward() {
		return jfdhsalaryreward;
	}
	public void setJfdhsalaryreward(BigDecimal jfdhsalaryreward) {
		this.jfdhsalaryreward = jfdhsalaryreward;
	}
	public BigDecimal getJjdhyejireward() {
		return jjdhyejireward;
	}
	public void setJjdhyejireward(BigDecimal jjdhyejireward) {
		this.jjdhyejireward = jjdhyejireward;
	}
	public BigDecimal getJjfwsalaryreward() {
		return jjfwsalaryreward;
	}
	public void setJjfwsalaryreward(BigDecimal jjfwsalaryreward) {
		this.jjfwsalaryreward = jjfwsalaryreward;
	}
	public BigDecimal getJsrsalaryreward() {
		return jsrsalaryreward;
	}
	public void setJsrsalaryreward(BigDecimal jsrsalaryreward) {
		this.jsrsalaryreward = jsrsalaryreward;
	}
	public BigDecimal getTjsrsalarycost() {
		return tjsrsalarycost;
	}
	public void setTjsrsalarycost(BigDecimal tjsrsalarycost) {
		this.tjsrsalarycost = tjsrsalarycost;
	}
	public BigDecimal getMfsalaryratefiveup() {
		return mfsalaryratefiveup;
	}
	public void setMfsalaryratefiveup(BigDecimal mfsalaryratefiveup) {
		this.mfsalaryratefiveup = mfsalaryratefiveup;
	}
	public BigDecimal getMfsalaryratefivedown() {
		return mfsalaryratefivedown;
	}
	public void setMfsalaryratefivedown(BigDecimal mfsalaryratefivedown) {
		this.mfsalaryratefivedown = mfsalaryratefivedown;
	}
	public BigDecimal getOlccostyejifixed() {
		return olccostyejifixed;
	}
	public void setOlccostyejifixed(BigDecimal olccostyejifixed) {
		this.olccostyejifixed = olccostyejifixed;
	}
	public BigDecimal getOlccostsalaryfixed() {
		return olccostsalaryfixed;
	}
	public void setOlccostsalaryfixed(BigDecimal olccostsalaryfixed) {
		this.olccostsalaryfixed = olccostsalaryfixed;
	}
	public BigDecimal getNlccostratetr() {
		return nlccostratetr;
	}
	public void setNlccostratetr(BigDecimal nlccostratetr) {
		this.nlccostratetr = nlccostratetr;
	}
	public BigDecimal getYjkcostratemrmf() {
		return yjkcostratemrmf;
	}
	public void setYjkcostratemrmf(BigDecimal yjkcostratemrmf) {
		this.yjkcostratemrmf = yjkcostratemrmf;
	}
	public BigDecimal getYjksalaryratemrmf() {
		return yjksalaryratemrmf;
	}
	public void setYjksalaryratemrmf(BigDecimal yjksalaryratemrmf) {
		this.yjksalaryratemrmf = yjksalaryratemrmf;
	}
	public BigDecimal getYjkcostratetr() {
		return yjkcostratetr;
	}
	public void setYjkcostratetr(BigDecimal yjkcostratetr) {
		this.yjkcostratetr = yjkcostratetr;
	}
	public BigDecimal getSalaryratetra() {
		return salaryratetra;
	}
	public void setSalaryratetra(BigDecimal salaryratetra) {
		this.salaryratetra = salaryratetra;
	}
	public BigDecimal getSalaryratetrb() {
		return salaryratetrb;
	}
	public void setSalaryratetrb(BigDecimal salaryratetrb) {
		this.salaryratetrb = salaryratetrb;
	}
	public BigDecimal getXjccostratesjs() {
		return xjccostratesjs;
	}
	public void setXjccostratesjs(BigDecimal xjccostratesjs) {
		this.xjccostratesjs = xjccostratesjs;
	}
	public BigDecimal getXjccostratesx() {
		return xjccostratesx;
	}
	public void setXjccostratesx(BigDecimal xjccostratesx) {
		this.xjccostratesx = xjccostratesx;
	}
	public BigDecimal getXjccostratezj() {
		return xjccostratezj;
	}
	public void setXjccostratezj(BigDecimal xjccostratezj) {
		this.xjccostratezj = xjccostratezj;
	}
	public BigDecimal getXjcsalaryfixeddb() {
		return xjcsalaryfixeddb;
	}
	public void setXjcsalaryfixeddb(BigDecimal xjcsalaryfixeddb) {
		this.xjcsalaryfixeddb = xjcsalaryfixeddb;
	}
	public BigDecimal getXjcsalaryfixedndb() {
		return xjcsalaryfixedndb;
	}
	public void setXjcsalaryfixedndb(BigDecimal xjcsalaryfixedndb) {
		this.xjcsalaryfixedndb = xjcsalaryfixedndb;
	}
	public BigDecimal getXjcsalaryfixednhg() {
		return xjcsalaryfixednhg;
	}
	public void setXjcsalaryfixednhg(BigDecimal xjcsalaryfixednhg) {
		this.xjcsalaryfixednhg = xjcsalaryfixednhg;
	}
	public BigDecimal getMrsalaryfixedtmk() {
		return mrsalaryfixedtmk;
	}
	public void setMrsalaryfixedtmk(BigDecimal mrsalaryfixedtmk) {
		this.mrsalaryfixedtmk = mrsalaryfixedtmk;
	}
	public BigDecimal getMrsalaryfixedty() {
		return mrsalaryfixedty;
	}
	public void setMrsalaryfixedty(BigDecimal mrsalaryfixedty) {
		this.mrsalaryfixedty = mrsalaryfixedty;
	}
}
