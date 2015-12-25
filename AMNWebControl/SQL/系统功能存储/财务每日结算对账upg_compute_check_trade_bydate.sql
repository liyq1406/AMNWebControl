
if exists(select 1 from sysobjects where type='P' and name='upg_compute_check_trade_bydate')
	drop procedure upg_compute_check_trade_bydate
go
create procedure upg_compute_check_trade_bydate 
(
	@compid			varchar(10),		--门店号	
	@datefrom		varchar(10),	--起始日期
	@dateto			varchar(10)			--结束日期
)  
as
begin
	           
	create table #check_trade_bydate                  
	(                  
		shopId			varchar(10)		null,	--门店编号
		shopName		varchar(40)		null,	--门店名称       
		dateReport		varchar(10)		null,	--日期业绩                 
		beaut_yeji		float			null,	--美容业绩                  
		hair_yeji		float			null,	--美发业绩                  
		finger_yeji		float			null,	--烫染护业绩                   
		foot_yeji		float			null,	--美甲业绩                   
		other_yeji		float			null,   --其他业绩 
		total_yeji		float			null,	--总业绩              
		cash_total		float			null,	--现金合计                   
		bank_total		float			null,	--银行卡合计                  
		ok_total		float			null,	--ok卡合计                  
		tg_total		float			null,	--团购合计              
		dztgcost		float			null,	--大众点评网            
		mttgcost		float			null,	--美团网                
		empgoodssale	float			null,	--员工产品                  
		zp_total		float			null,	--支票合计                  
		zft_total		float			null,	--指付通合计 
		cardyd_total	float			null,	--卡异动    
		card_total		float			null,	--销卡合计  
		prjdyjcost		float			null,	--项目抵用券                  
		cashdyjcost		float			null,	--现金抵用券   
		tmkcost			float			null,	--条码卡消费 
		sgcard_total	float			null,	--收购卡服务    
		jifen_total		float			null,	--积分消费    
		tk_total		float			null,	--退卡   
		tmkbuy_total	float			null,	--购买条码卡            
		tmkzs_total		float			null,	--赠送条码卡  
		hzitem_total	float			null,	--合作项目         
	 )   
	 

    declare @tmpdate varchar(8)                                    
	declare @tmpenddate varchar(8)                                    
	set @tmpenddate = @datefrom                                    
	set @tmpdate = @datefrom                                    
    while (@tmpenddate <= @dateto)                                    
    begin                                    
	  --插入选择的门店编号到#diarialBill_byday_fromShops中                                        
		insert #check_trade_bydate(shopId,shopName,dateReport)                                        
		select compno,compname,@tmpenddate                                        
		from companyinfo,compchaininfo
		where curcomp= @compid and  relationcomp=compno  
		execute upg_date_plus @tmpdate,1,@tmpenddate output                                    
		set @tmpdate = @tmpenddate                                    
    end

    update a
    set beaut_yeji=isnull(b.realbeautyeji,0),hair_yeji=isnull(b.realhairyeji,0),finger_yeji=isnull(b.realfingeryeji,0),foot_yeji=isnull(b.realfootyeji,0),total_yeji=isnull(b.realtotalyeji,0),other_yeji=isnull(b.realtotalyeji,0)-ISNULL(b.realbeautyeji,0)-ISNULL(b.realhairyeji,0)-ISNULL(b.realfingeryeji,0)
    from  #check_trade_bydate a,compclasstraderesult b
    where a.shopId=b.compid and a.dateReport=b.ddate
    

	 
	  update a set a.cash_total=isnull(b.cashtotal,0),a.bank_total=isnull(b.credittotal,0),a.ok_total=isnull(b.ocktotal,0),                  
	  a.tg_total=isnull(b.tgktotal,0),a.card_total=isnull(b.cardsalesservices,0)+isnull(b.cardsalesprod,0)+isnull(b.acquisitioncardservices,0),
	  a.zp_total=isnull(b.checktotal,0),a.zft_total=isnull(b.zfttotal,0),                  
	  a.cashdyjcost=isnull(b.cashdyservice,0),a.prjdyjcost=isnull(b.prjdyservice,0),a.tk_total=isnull(b.tgktotal,0),a.cardyd_total=isnull(b.totalcardtrans,0),a.hzitem_total=isnull(b.sumcashhezprj,0),
	  a.sgcard_total=isnull(b.acquisitioncardservices,0),a.jifen_total=isnull(b.costpointtotal,0),a.tmkcost=isnull(b.tmkservice,0) ,
	  empgoodssale=ISNULL(b.staffsallprod,0)   
	  from #check_trade_bydate a,detial_trade_byday_fromshops b      
	  where a.shopId=b.shopId and a.dateReport=b.dateReport
  
      update a set dztgcost=(ISNULL((select sum(ISNULL(corpsamt,0)) from corpsbuyinfo b 	
           where a.shopId=b.useincompid and a.dateReport=b.useindate and corpssource='01' and ISNULL(corpssate,0)=2 ) ,0))
      from #check_trade_bydate a

	   update a set mttgcost=(ISNULL((select sum(ISNULL(corpsamt,0)) from corpsbuyinfo b 	
           where a.shopId=b.useincompid and a.dateReport=b.useindate and corpssource='02' and ISNULL(corpssate,0)=2 ) ,0))
      from #check_trade_bydate a
	  
	   update a set tmkbuy_total=(ISNULL((select sum(ISNULL(saleamt,0)) from msalebarcodecardinfo b ,nointernalcardinfo c
           where  a.shopId=b.salecompid and a.dateReport=b.saledate and b.barcodecardno=c.cardno and  cardtype=2 and  entrytype=0 ) ,0))
      from #check_trade_bydate a

	   update a set tmkzs_total=(ISNULL((select sum(ISNULL(saleamt,0)) from msalebarcodecardinfo b ,nointernalcardinfo c
           where  a.shopId=b.salecompid and a.dateReport=b.saledate and b.barcodecardno=c.cardno and  cardtype=2 and  entrytype=1 ) ,0))
      from #check_trade_bydate a
	  

		if exists(select 1 from compchainstruct where curcompno=@compid and complevel=4 )
		begin
			  select shopId,shopName,dateReport,beaut_yeji=sum(beaut_yeji),hair_yeji=sum(hair_yeji),finger_yeji=sum(finger_yeji),foot_yeji=sum(foot_yeji),other_yeji=sum(other_yeji),total_yeji=sum(total_yeji),
				   cash_total=sum(cash_total),bank_total=sum(bank_total),ok_total=sum(ok_total),tg_total=sum(tg_total),dztgcost=sum(dztgcost),mttgcost=sum(mttgcost),empgoodssale=sum(empgoodssale),card_total=sum(card_total),zp_total=sum(zp_total),zft_total=sum(zft_total),
				   prjdyjcost=sum(prjdyjcost),cashdyjcost=sum(cashdyjcost),tmkcost=sum(tmkcost),tmkbuy_total=sum(tmkbuy_total),tmkzs_total=sum(tmkzs_total),cardyd_total=sum(cardyd_total),hzitem_total=sum(hzitem_total),sgcard_total=sum(sgcard_total),jifen_total=sum(jifen_total),tk_total=sum(tk_total)
			 from #check_trade_bydate 
			 where ISNULL(total_yeji,0)>0   
			 group by shopId,dateReport,shopName
		end
		else
		begin
			select shopId,shopName,dateReport='',beaut_yeji=sum(beaut_yeji),hair_yeji=sum(hair_yeji),finger_yeji=sum(finger_yeji),foot_yeji=sum(foot_yeji),other_yeji=sum(other_yeji),total_yeji=sum(total_yeji),
				   cash_total=sum(cash_total),bank_total=sum(bank_total),ok_total=sum(ok_total),tg_total=sum(tg_total),dztgcost=sum(dztgcost),mttgcost=sum(mttgcost),empgoodssale=sum(empgoodssale),card_total=sum(card_total),zp_total=sum(zp_total),zft_total=sum(zft_total),
				   prjdyjcost=sum(prjdyjcost),cashdyjcost=sum(cashdyjcost),tmkcost=sum(tmkcost),tmkbuy_total=sum(tmkbuy_total),tmkzs_total=sum(tmkzs_total),cardyd_total=sum(cardyd_total),hzitem_total=sum(hzitem_total),sgcard_total=sum(sgcard_total),jifen_total=sum(jifen_total),tk_total=sum(tk_total)
			 from #check_trade_bydate 
			 where ISNULL(total_yeji,0)>0   
			 group by shopId,shopName
		end
	   

	  
	 
    drop table #check_trade_bydate                         
                                   
                
end
go

--exec upg_compute_check_trade_bydate '002','20131201','20131231'