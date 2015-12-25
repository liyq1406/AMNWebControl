package com.amani.model;

import java.math.BigDecimal;

public class DetialTradeTyday {
	 private String 			shopId						;//门店编号                                           
	 private String 			shopName					;//门店名称                                           
	 private String 			dateReport					;//结账日期      
	 private String 			strtotal					;//结账日期      
	 private BigDecimal			total						;//总收入                                                                  
	 private BigDecimal			cashservice					;//现金服务                                                 
	 private BigDecimal			cashprod					;//现金产品                                                 
	 private BigDecimal			cashcardtrans				;//现金(卡异动)                                    
	 private BigDecimal			cashbackcard				;//现金(退卡)        
	 private BigDecimal			cashYearCard				;//现金年卡
	 private BigDecimal			cashtotal					;//现金合计(扣除现金退卡)                                                
	                                                                                                 
	 private BigDecimal			creditservice				;//银行卡服务                                                
	 private BigDecimal			creditprod					;//银行卡产品                                                
	 private BigDecimal			credittrans					;//银行卡(卡异动)                                      
	 private BigDecimal			creditbackcard				;//-银行卡(退卡)     
	 private BigDecimal			creditYeardcard				;//现金年卡
	 private BigDecimal			credittotal					;//银行卡合计(扣除银行卡退卡)                                                
	                                                                                                  
	 private BigDecimal			checkservice				;//支票服务                                                 
	 private BigDecimal			checkprod					;//支票产品                                                 
	 private BigDecimal			checktrans					;//支票(卡异动)                                       
	 private BigDecimal			checkbackcard				;//支票(退卡)                                           
	 private BigDecimal			checktotal					;//支票合计(扣除支票退卡)                                   
	                               
	 private BigDecimal			zftservice					;//指付通服务                                                 
	 private BigDecimal			zftprod						;//指付通产品                                                 
	 private BigDecimal			zfttrans					;//指付通(卡异动)                                       
	 private BigDecimal			zftbackcard					;//指付通(退卡)                                           
	 private BigDecimal			zfttotal					;//指付通合计(扣除支付通退卡)                                 
	                               
	 private BigDecimal			ockservice					;//oK卡服务                                                 
	 private BigDecimal			ockkprod					;//oK卡产品                                                 
	 private BigDecimal			ocktrans					;//oK卡(卡异动)                                       
	 private BigDecimal			ockbackcard					;//oK卡(退卡)                                           
	 private BigDecimal			ocktotal					;//oK卡合计(扣除oK卡退卡) 
	 
	 private BigDecimal			tgkservice					;//团购卡服务                                                 
	 private BigDecimal			tgkkprod					;//团购卡产品                                   
	 private BigDecimal			tgktrans					;//团购卡(卡异动)                                                  
	 private BigDecimal			tgktotal					;//团购卡合计                                
	 
	 
	 private BigDecimal			totalcardtrans				;//卡异动(卡销售,卡充值,卡升级)	
	                           
	 private BigDecimal			cashchangesale				;//现金兑换销售                          
	 private BigDecimal			bankchangesale				;//银行卡兑换销售 
	  
	 private BigDecimal			cashbytmksale				;//现金条码卡销售                              
	 private BigDecimal			bankbytmksale				;//银行卡条码卡销售                              
	 private BigDecimal			checkbytmksale				;//支票条码卡销售                              
	 private BigDecimal			fingerbytmksale				;//指付通条码卡销售                              
	 private BigDecimal			okpqypwybytmksale			;//oK卡条码卡销售
	  
	 private BigDecimal			cashhezprj					;//现金合作项目                           
	 private BigDecimal			bankhezprj					;//银行卡合作项目                  
	 private BigDecimal			sumcashhezprj				;//现金合作项目(店内支付的现金)                                    
	                                                                                                  
	                                                  
	                                                                                                             
	 private BigDecimal			cardsalesservices          ;//销卡服务                                      
	 private BigDecimal			yearsaleservice             ;//年卡服务
	 private BigDecimal			jfysk_xj;//接发应收款(现金)
	 private BigDecimal			jfysk_yhk;//接发应收款(银行卡)
	 private BigDecimal			jfysk_qt;//接发应收款(其他)
	 private BigDecimal			jfysk;//接发应收款(合计)
	 
	public BigDecimal getJfysk_xj() {
		return jfysk_xj;
	}
	public void setJfysk_xj(BigDecimal jfysk_xj) {
		this.jfysk_xj = jfysk_xj;
	}
	public BigDecimal getJfysk_yhk() {
		return jfysk_yhk;
	}
	public void setJfysk_yhk(BigDecimal jfysk_yhk) {
		this.jfysk_yhk = jfysk_yhk;
	}
	public BigDecimal getJfysk_qt() {
		return jfysk_qt;
	}
	public void setJfysk_qt(BigDecimal jfysk_qt) {
		this.jfysk_qt = jfysk_qt;
	}
	public BigDecimal getJfysk() {
		return jfysk;
	}
	public void setJfysk(BigDecimal jfysk) {
		this.jfysk = jfysk;
	}
	public BigDecimal getCashYearCard() {
		return cashYearCard;
	}
	public void setCashYearCard(BigDecimal cashYearCard) {
		this.cashYearCard = cashYearCard;
	}
	public BigDecimal getCreditYeardcard() {
		return creditYeardcard;
	}
	public void setCreditYeardcard(BigDecimal creditYeardcard) {
		this.creditYeardcard = creditYeardcard;
	}
	public BigDecimal getYearsaleservice() {
		return yearsaleservice;
	}
	public void setYearsaleservice(BigDecimal yearsaleservice) {
		this.yearsaleservice = yearsaleservice;
	}
	private BigDecimal			cardsalesprod				;//销卡产品   
	 private BigDecimal			cardsalestotal				;//销卡合计   
	 private BigDecimal			staffsallprod				;//员工产品   
	 private BigDecimal			acquisitioncardservices		;//收购转卡服务 
	 private BigDecimal			costpointtotal				;//积分服务   
	 private BigDecimal			cashdyservice				;//现金抵用券服务                              
	 private BigDecimal			prjdyservice				;//项目抵用券服务
	 private BigDecimal			tmkservice					;//条码卡 服务  
	 private BigDecimal			tmksendservice				;//条码卡 服务    
	 private BigDecimal			managesigning				;//经理签单                                         
	 private BigDecimal			payoutRegister				;//支出登记  
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getDateReport() {
		return dateReport;
	}
	public void setDateReport(String dateReport) {
		this.dateReport = dateReport;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getCashservice() {
		return cashservice;
	}
	public void setCashservice(BigDecimal cashservice) {
		this.cashservice = cashservice;
	}
	public BigDecimal getCashprod() {
		return cashprod;
	}
	public void setCashprod(BigDecimal cashprod) {
		this.cashprod = cashprod;
	}
	public BigDecimal getCashcardtrans() {
		return cashcardtrans;
	}
	public void setCashcardtrans(BigDecimal cashcardtrans) {
		this.cashcardtrans = cashcardtrans;
	}
	public BigDecimal getCashbackcard() {
		return cashbackcard;
	}
	public void setCashbackcard(BigDecimal cashbackcard) {
		this.cashbackcard = cashbackcard;
	}
	public BigDecimal getCashtotal() {
		return cashtotal;
	}
	public void setCashtotal(BigDecimal cashtotal) {
		this.cashtotal = cashtotal;
	}
	public BigDecimal getCreditservice() {
		return creditservice;
	}
	public void setCreditservice(BigDecimal creditservice) {
		this.creditservice = creditservice;
	}
	public BigDecimal getCreditprod() {
		return creditprod;
	}
	public void setCreditprod(BigDecimal creditprod) {
		this.creditprod = creditprod;
	}
	public BigDecimal getCredittrans() {
		return credittrans;
	}
	public void setCredittrans(BigDecimal credittrans) {
		this.credittrans = credittrans;
	}
	public BigDecimal getCreditbackcard() {
		return creditbackcard;
	}
	public void setCreditbackcard(BigDecimal creditbackcard) {
		this.creditbackcard = creditbackcard;
	}
	public BigDecimal getCredittotal() {
		return credittotal;
	}
	public void setCredittotal(BigDecimal credittotal) {
		this.credittotal = credittotal;
	}
	public BigDecimal getCheckservice() {
		return checkservice;
	}
	public void setCheckservice(BigDecimal checkservice) {
		this.checkservice = checkservice;
	}
	public BigDecimal getCheckprod() {
		return checkprod;
	}
	public void setCheckprod(BigDecimal checkprod) {
		this.checkprod = checkprod;
	}
	public BigDecimal getChecktrans() {
		return checktrans;
	}
	public void setChecktrans(BigDecimal checktrans) {
		this.checktrans = checktrans;
	}
	public BigDecimal getCheckbackcard() {
		return checkbackcard;
	}
	public void setCheckbackcard(BigDecimal checkbackcard) {
		this.checkbackcard = checkbackcard;
	}
	public BigDecimal getChecktotal() {
		return checktotal;
	}
	public void setChecktotal(BigDecimal checktotal) {
		this.checktotal = checktotal;
	}
	public BigDecimal getZftservice() {
		return zftservice;
	}
	public void setZftservice(BigDecimal zftservice) {
		this.zftservice = zftservice;
	}
	public BigDecimal getZftprod() {
		return zftprod;
	}
	public void setZftprod(BigDecimal zftprod) {
		this.zftprod = zftprod;
	}
	public BigDecimal getZfttrans() {
		return zfttrans;
	}
	public void setZfttrans(BigDecimal zfttrans) {
		this.zfttrans = zfttrans;
	}
	public BigDecimal getZftbackcard() {
		return zftbackcard;
	}
	public void setZftbackcard(BigDecimal zftbackcard) {
		this.zftbackcard = zftbackcard;
	}
	public BigDecimal getZfttotal() {
		return zfttotal;
	}
	public void setZfttotal(BigDecimal zfttotal) {
		this.zfttotal = zfttotal;
	}
	public BigDecimal getOckservice() {
		return ockservice;
	}
	public void setOckservice(BigDecimal ockservice) {
		this.ockservice = ockservice;
	}
	public BigDecimal getOckkprod() {
		return ockkprod;
	}
	public void setOckkprod(BigDecimal ockkprod) {
		this.ockkprod = ockkprod;
	}
	public BigDecimal getOcktrans() {
		return ocktrans;
	}
	public void setOcktrans(BigDecimal ocktrans) {
		this.ocktrans = ocktrans;
	}
	public BigDecimal getOckbackcard() {
		return ockbackcard;
	}
	public void setOckbackcard(BigDecimal ockbackcard) {
		this.ockbackcard = ockbackcard;
	}
	public BigDecimal getOcktotal() {
		return ocktotal;
	}
	public void setOcktotal(BigDecimal ocktotal) {
		this.ocktotal = ocktotal;
	}
	public BigDecimal getTgkservice() {
		return tgkservice;
	}
	public void setTgkservice(BigDecimal tgkservice) {
		this.tgkservice = tgkservice;
	}
	public BigDecimal getTgkkprod() {
		return tgkkprod;
	}
	public void setTgkkprod(BigDecimal tgkkprod) {
		this.tgkkprod = tgkkprod;
	}
	public BigDecimal getTgktrans() {
		return tgktrans;
	}
	public void setTgktrans(BigDecimal tgktrans) {
		this.tgktrans = tgktrans;
	}
	public BigDecimal getTgktotal() {
		return tgktotal;
	}
	public void setTgktotal(BigDecimal tgktotal) {
		this.tgktotal = tgktotal;
	}

	public BigDecimal getTotalcardtrans() {
		return totalcardtrans;
	}
	public void setTotalcardtrans(BigDecimal totalcardtrans) {
		this.totalcardtrans = totalcardtrans;
	}
	public BigDecimal getCashchangesale() {
		return cashchangesale;
	}
	public void setCashchangesale(BigDecimal cashchangesale) {
		this.cashchangesale = cashchangesale;
	}
	public BigDecimal getBankchangesale() {
		return bankchangesale;
	}
	public void setBankchangesale(BigDecimal bankchangesale) {
		this.bankchangesale = bankchangesale;
	}
	public BigDecimal getCashbytmksale() {
		return cashbytmksale;
	}
	public void setCashbytmksale(BigDecimal cashbytmksale) {
		this.cashbytmksale = cashbytmksale;
	}
	public BigDecimal getBankbytmksale() {
		return bankbytmksale;
	}
	public void setBankbytmksale(BigDecimal bankbytmksale) {
		this.bankbytmksale = bankbytmksale;
	}
	public BigDecimal getCheckbytmksale() {
		return checkbytmksale;
	}
	public void setCheckbytmksale(BigDecimal checkbytmksale) {
		this.checkbytmksale = checkbytmksale;
	}
	public BigDecimal getFingerbytmksale() {
		return fingerbytmksale;
	}
	public void setFingerbytmksale(BigDecimal fingerbytmksale) {
		this.fingerbytmksale = fingerbytmksale;
	}
	public BigDecimal getOkpqypwybytmksale() {
		return okpqypwybytmksale;
	}
	public void setOkpqypwybytmksale(BigDecimal okpqypwybytmksale) {
		this.okpqypwybytmksale = okpqypwybytmksale;
	}
	public BigDecimal getCashhezprj() {
		return cashhezprj;
	}
	public void setCashhezprj(BigDecimal cashhezprj) {
		this.cashhezprj = cashhezprj;
	}
	public BigDecimal getBankhezprj() {
		return bankhezprj;
	}
	public void setBankhezprj(BigDecimal bankhezprj) {
		this.bankhezprj = bankhezprj;
	}
	public BigDecimal getSumcashhezprj() {
		return sumcashhezprj;
	}
	public void setSumcashhezprj(BigDecimal sumcashhezprj) {
		this.sumcashhezprj = sumcashhezprj;
	}
	public BigDecimal getCardsalesservices() {
		return cardsalesservices;
	}
	public void setCardsalesservices(BigDecimal cardsalesservices) {
		this.cardsalesservices = cardsalesservices;
	}
	public BigDecimal getCardsalesprod() {
		return cardsalesprod;
	}
	public void setCardsalesprod(BigDecimal cardsalesprod) {
		this.cardsalesprod = cardsalesprod;
	}
	public BigDecimal getAcquisitioncardservices() {
		return acquisitioncardservices;
	}
	public void setAcquisitioncardservices(BigDecimal acquisitioncardservices) {
		this.acquisitioncardservices = acquisitioncardservices;
	}
	public BigDecimal getCostpointtotal() {
		return costpointtotal;
	}
	public void setCostpointtotal(BigDecimal costpointtotal) {
		this.costpointtotal = costpointtotal;
	}
	public BigDecimal getCashdyservice() {
		return cashdyservice;
	}
	public void setCashdyservice(BigDecimal cashdyservice) {
		this.cashdyservice = cashdyservice;
	}
	public BigDecimal getPrjdyservice() {
		return prjdyservice;
	}
	public void setPrjdyservice(BigDecimal prjdyservice) {
		this.prjdyservice = prjdyservice;
	}
	public BigDecimal getTmkservice() {
		return tmkservice;
	}
	public void setTmkservice(BigDecimal tmkservice) {
		this.tmkservice = tmkservice;
	}
	public BigDecimal getManagesigning() {
		return managesigning;
	}
	public void setManagesigning(BigDecimal managesigning) {
		this.managesigning = managesigning;
	}
	public BigDecimal getPayoutRegister() {
		return payoutRegister;
	}
	public void setPayoutRegister(BigDecimal payoutRegister) {
		this.payoutRegister = payoutRegister;
	}
	public BigDecimal getCardsalestotal() {
		return cardsalestotal;
	}
	public void setCardsalestotal(BigDecimal cardsalestotal) {
		this.cardsalestotal = cardsalestotal;
	}
	public BigDecimal getStaffsallprod() {
		return staffsallprod;
	}
	public void setStaffsallprod(BigDecimal staffsallprod) {
		this.staffsallprod = staffsallprod;
	}
	public String getStrtotal() {
		return strtotal;
	}
	public void setStrtotal(String strtotal) {
		this.strtotal = strtotal;
	}
	public BigDecimal getTmksendservice() {
		return tmksendservice;
	}
	public void setTmksendservice(BigDecimal tmksendservice) {
		this.tmksendservice = tmksendservice;
	}
}
