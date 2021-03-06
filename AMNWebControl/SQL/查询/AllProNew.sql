USE [MasterDatabase2014]
GO
/****** Object:  StoredProcedure [dbo].[get_month_days]    Script Date: 01/14/2014 15:45:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[get_month_days] 
@year   int,  
@month  int,  
@day    int output  
as  
begin  
  
declare @leap int --1 is leap 0 is not leap  
begin  
 if( (@year%4=0 and @year%100<>0) or @year%400=0)  
  set @leap =1  
 else   
  set @leap = 0  
  
 if(@month=1)  
  set @day=31  
 if(@month=2)  
 begin  
  if(@leap=1)  
   set @day=29  
  else  
   set @day=28  
 end  
 if(@month=3)  
  set @day=31  
 if(@month=4)  
  set @day=30  
 if(@month=5)  
  set @day=31  
 if(@month=6)  
  set @day=30  
 if(@month=7)  
  set @day=31  
 if(@month=8)  
  set @day=31  
 if(@month=9)  
  set @day=30  
 if(@month=10)  
  set @day=31  
 if(@month=11)  
  set @day=30  
 if(@month=12)  
  set @day=31  
end  
end
GO
/****** Object:  StoredProcedure [dbo].[upg_caculate_personal_tax]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_caculate_personal_tax](      
 @basicm float ,--个人所得税起征金额      
 @totalmoney float ,--个人月收入      
 @tax float  output , --应扣税      
 @income float output --实际税后收入      
)      
as      
begin      
 declare @cha float      
 set @cha = @totalmoney - @basicm      
      
      
--if (@cha<=0)         set @tax=0      
-- if (@cha>0 and @cha<=500)      set @tax=@cha*0.05      
-- if (@cha>500 and @cha<=2000)  set @tax=@cha*0.1-25      
-- if (@cha>2000 and @cha<=5000)  set @tax=@cha*0.15-125      
-- if (@cha>5000 and @cha<=20000)  set @tax=@cha*0.2-375      
-- if (@cha>20000 and @cha<=40000)  set @tax=@cha*0.25-1375      
-- if (@cha>40000 and @cha<=60000)  set @tax=@cha*0.30-3375      
-- if (@cha>60000 and @cha<=80000)  set @tax=@cha*0.35-6375      
-- if (@cha>80000 and @cha<=100000) set @tax=@cha*0.4-10375      
-- if (@cha>100000  )     set @tax=@cha*0.45-15375      
     
--     1     不超过1500元的                3    
--　 　2     超过1500元至4500元的部分      10    
--　　 3     超过4500元至9000元的部分      20    
--　　 4     超过9000元至35000元的部分     25    
--　　 5     超过35000元至55000元的部分    30    
--　　 6     超过55000元至80000元的部分    35    
--　　 7     超过80000元的部分             45    
 if (@cha<=0)         set @tax=0      
 if (@cha>0 and @cha<=1500)      set @tax=@cha*0.03      
 if (@cha>1500 and @cha<=4500)  set @tax=@cha*0.1-105      
 if (@cha>4500 and @cha<=9000)  set @tax=@cha*0.2-555     
 if (@cha>9000 and @cha<=35000)  set @tax=@cha*0.25-1005    
 if (@cha>35000 and @cha<=55000)  set @tax=@cha*0.30-2755      
 if (@cha>55000 and @cha<=80000)  set @tax=@cha*0.35-5505      
 if (@cha>80000 ) set @tax=@cha*0.45-13505      
    
     
    
       
 set @income = @totalmoney - @tax      
end
GO
/****** Object:  StoredProcedure [dbo].[upg_date_plus]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_date_plus]
	@fromdate varchar(8),
	@addday float,
	@result varchar(8) output
as
begin
	declare @tmpdate datetime
	declare @year varchar(4) --年份
	declare @month varchar(2)--月份
	declare @day varchar(2) --日份
	set @tmpdate = dateadd(day,@addday,convert(datetime,@fromdate))
	set @year = year(@tmpdate)
	set @month = month(@tmpdate)
	set @day = day(@tmpdate)
	if(len(@month)=1)
		set @month = '0'+@month
	if(len(@day)=1)
		set @day = '0'+@day
	set @result=@year+@month+@day
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_trade_payinfo_daybyday]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_comp_trade_payinfo_daybyday] 
(
	@compid			varchar(10),	--结算门店
	@datefrom		varchar(10),	--起始日期
	@dateto			varchar(10)		--结束日期
)  
as
begin

	delete payinfodaybyday from payinfodaybyday,compchaininfo where paycompid=relationcomp and curcomp=@compid and paydate between @datefrom and @dateto
	--收银
	insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
		select a.cscompid,financedate,'SY_P',cspaymode,sum(isnull(csitemamt,0))
	from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),compchaininfo
	where a.cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto
	  and a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,1)=1
	group by a.cscompid,cspaymode,financedate
	
	insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
	select a.cscompid,financedate,'SY_G1',cspaymode,sum(isnull(csitemamt,0))
	from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),compchaininfo
	where  a.cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto
	  and a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,1)=2 and ISNULL(abs(saletype),1)=1
	group by a.cscompid,cspaymode,financedate
	
	insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
	select a.cscompid,financedate,'SY_G2',cspaymode,sum(isnull(csitemamt,0))
	from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),compchaininfo
	where  a.cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto
	  and a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,1)=2 and ISNULL(abs(saletype),1)=2
	group by a.cscompid,cspaymode,financedate
	
	
	--售卡
	insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,financedate,paybilltype,paymode,sum(isnull(payamt,0))
	from msalecardinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where salecompid=paycompid and salebillid=paybillid and paybilltype='SK' 
	  and salecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and ISNULL(salebakflag,0)=0
	group by paycompid,paybilltype,paymode,financedate
	--充值
	insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,financedate,paybilltype,paymode,sum(isnull(payamt,0))
	from mcardrechargeinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where rechargecompid=paycompid and rechargebillid=paybillid and paybilltype='CZ' 
	  and rechargecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and ISNULL(salebakflag,0)=0 and isnull(backbillid,'')=''
	group by paycompid,paybilltype,paymode,financedate
	--卡异动
	insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,financedate,paybilltype,paymode,sum(isnull(payamt,0))
	from mcardchangeinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where changecompid=paycompid and changebillid=paybillid and paybilltype='ZK' 
	  and changecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and ISNULL(salebakflag,0)=0
	group by paycompid,paybilltype,paymode,financedate
	--退卡
	insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,financedate,paybilltype,paymode,sum(isnull(payamt,0))
	from mcardchangeinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where changecompid=paycompid and changebillid=paybillid and paybilltype='TK' 
	  and changecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and ISNULL(salebakflag,0)=0
	group by paycompid,paybilltype,paymode,financedate
	--合作项目
	--insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
	--select paycompid,financedate,'HZ_O',paymode,sum(isnull(payamt,0))
	--from mcooperatesaleinfo with(nolock),dpayinfo with(nolock),compchaininfo
	--where salecompid=paycompid and salebillid=paybillid and paybilltype='HZ' 
	--  and salecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and  ISNULL(salebillflag,0)=2
	--  and ISNULL(slaepaymode,1)=2
	--group by paycompid,paybilltype,paymode,financedate
	
	insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,financedate,'HZ_I',paymode,sum( case  when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then isnull(payamt,0)*0.5    
          when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then isnull(payamt,0)    
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then isnull(payamt,0)    
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 0    
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then isnull(payamt,0)*0.6    
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 0 end   ) 
	from mcooperatesaleinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where salecompid=paycompid and salebillid=paybillid and paybilltype='HZ' 
	  and salecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and  ISNULL(salebillflag,0)=2
	--  and ISNULL(slaepaymode,1)=1
	group by paycompid,paybilltype,paymode,financedate
	
	--条码卡开卡

	insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
	select salecompid,saledate,'TMK',paycode,SUM(isnull(payamt,0))
	from 
	(
	select salecompid,saledate,paycode=secondpaymode,payamt=sum(isnull(secondpayamt,0)) 
	from msalebarcodecardinfo with(nolock),compchaininfo
	where salecompid=relationcomp and curcomp=@compid and saledate between @datefrom and @dateto and  isnull(secondpayamt,0)<>0
	group by salecompid,secondpaymode,saledate
	union select salecompid,saledate,paycode=firstpaymode,payamt=sum(isnull(firstpayamt,0)) 
	from msalebarcodecardinfo with(nolock),compchaininfo
	where salecompid=relationcomp and curcomp=@compid and saledate between @datefrom and @dateto and  isnull(firstpayamt,0)<>0
	group by salecompid,firstpaymode,saledate
	) as a group by  salecompid,saledate,paycode
	--疗程兑换
	insert payinfodaybyday(paycompid,paydate,paybilltype,paymode,payamt)
	select a.changecompid,changedate,'LXDH',changepaymode,sum(isnull(changebycashamt,0))  
	from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock),compchaininfo
	where a.changecompid=relationcomp and curcomp=@compid and changedate between @datefrom and @dateto
	  and a.changecompid=b.changecompid and a.changebillid=b.changebillid and a.changedate between @datefrom and @dateto and ISNULL(salebakflag,0)=0
	group by a.changecompid,changepaymode,changedate
	
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_trade_payinfo]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_comp_trade_payinfo] 
(
	@compid			varchar(10),		--门店号	
	@datefrom		varchar(10),	--起始日期
	@dateto			varchar(10)			--结束日期
)  
as
begin

	--收银
	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
		select a.cscompid,financedate,'SY_P',cspaymode,sum(isnull(csitemamt,0))
	from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),compchaininfo
	where a.cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto
	  and a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,1)=1 
	group by a.cscompid,cspaymode,financedate
	
	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
	select a.cscompid,financedate,'SY_G1',cspaymode,sum(isnull(csitemamt,0))
	from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),compchaininfo
	where  a.cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto
	  and a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,1)=2 and ISNULL(abs(saletype),1)=1
	group by a.cscompid,cspaymode,financedate
	
	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
	select a.cscompid,financedate,'SY_G2',cspaymode,sum(isnull(csitemamt,0))
	from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),compchaininfo
	where  a.cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto
	  and a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,1)=2 and ISNULL(abs(saletype),1)=2
	group by a.cscompid,cspaymode,financedate
	
	
	--售卡
	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,financedate,paybilltype,paymode,sum(isnull(payamt,0))
	from msalecardinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where salecompid=paycompid and salebillid=paybillid and paybilltype='SK' 
	  and salecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and ISNULL(salebakflag,0)=0
	group by paycompid,paybilltype,paymode,financedate
	--充值
	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,financedate,paybilltype,paymode,sum(isnull(payamt,0))
	from mcardrechargeinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where rechargecompid=paycompid and rechargebillid=paybillid and paybilltype='CZ' 
	  and rechargecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and ISNULL(salebakflag,0)=0 and isnull(backbillid,'')=''
	group by paycompid,paybilltype,paymode,financedate
	--卡异动
	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,financedate,paybilltype,paymode,sum(isnull(payamt,0))
	from mcardchangeinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where changecompid=paycompid and changebillid=paybillid and paybilltype='ZK' 
	  and changecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and ISNULL(salebakflag,0)=0
	group by paycompid,paybilltype,paymode,financedate
	--退卡
	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,financedate,paybilltype,paymode,sum(isnull(payamt,0))
	from mcardchangeinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where changecompid=paycompid and changebillid=paybillid and paybilltype='TK' 
	  and changecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and ISNULL(salebakflag,0)=0
	group by paycompid,paybilltype,paymode,financedate
	--合作项目
	
	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,financedate,paybilltype,paymode,sum( case  when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then isnull(payamt,0)*0.5    
          when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then isnull(payamt,0)    
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then isnull(payamt,0)    
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 0    
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then isnull(payamt,0)*0.6    
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 0 end   ) 
	from mcooperatesaleinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where salecompid=paycompid and salebillid=paybillid and paybilltype='HZ' 
	  and salecompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto and  ISNULL(salebillflag,0)=2
	 -- and ISNULL(slaepaymode,1)=1
	group by paycompid,paybilltype,paymode,financedate
	
	-- select geh00c,geh94d,17,geh03c,geh10c,1,sum( case  when  geh03c='001' and ISNULL(geh04c,'1')='1' then isnull(geh11f,0)*0.5    
 --         when  geh03c='001' and ISNULL(geh04c,'1')='2' then isnull(geh11f,0)    
 --         when  geh03c='002' and ISNULL(geh04c,'1')='1' then isnull(geh11f,0)    
 --         when  geh03c='002' and ISNULL(geh04c,'1')='2' then 0    
 --         when  geh03c='003' and ISNULL(geh04c,'1')='1' then isnull(geh11f,0)*0.6    
 --         when  geh03c='003' and ISNULL(geh04c,'1')='2' then 0 end   )                                      
 --from gem06 ,gam26          
 --where gaz02c=geh00c  and gaz01c= @compid         
 --and geh94d >= @fromdate   and   geh94d <= @todate         
 --and ISNULL(geh12i,0)=1                     
 --group by geh00c,geh94d,geh03c,geh10c    
 
	
	--条码卡开卡
	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
	select salecompid,saledate,'TMK',firstpaymode,sum(isnull(firstpayamt,0)) 
	from msalebarcodecardinfo with(nolock),compchaininfo
	where salecompid=relationcomp and curcomp=@compid and saledate between @datefrom and @dateto 
	group by salecompid,firstpaymode,saledate
	

	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
	select salecompid,saledate,'TMK',secondpaymode,sum(isnull(secondpayamt,0)) 
	from msalebarcodecardinfo with(nolock),compchaininfo
	where salecompid=relationcomp and curcomp=@compid and saledate between @datefrom and @dateto
	group by salecompid,secondpaymode,saledate
	--疗程兑换
	insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)
	select a.changecompid,changedate,'LXDH',changepaymode,sum(isnull(changebycashamt,0))  
	from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock),compchaininfo
	where a.changecompid=relationcomp and curcomp=@compid and changedate between @datefrom and @dateto
	  and a.changecompid=b.changecompid and a.changebillid=b.changebillid and a.changedate between @datefrom and @dateto and ISNULL(salebakflag,0)=0
	group by a.changecompid,changepaymode,changedate
	
end
GO
/****** Object:  StoredProcedure [dbo].[upg_yejiAnalysis_byMonth_HT]    Script Date: 01/14/2014 15:45:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_yejiAnalysis_byMonth_HT]    
(    
	@compid  varchar(10),    
	@month   varchar(10)   
)    
as    
begin   
 
	                                  
	

  
    create table #shopyejianalysis  
	(    
		compid				varchar(10) not null,    
		compname			varchar(40) null,    
		mmonth				varchar(10)	null,
		totalAmt			float  null, -- //本月总营业额    
		totalAmtA			float  null, -- 去年同月总营业额    
		totalAmtB			float  null, -- 上月总营业额    
		totalAmtRateA		float  null,--  营业额同比    
		totalAmtRateB		float  null,--  营业额环比    
	     
		totalFactAmt		float  null,--  本月总实业绩    
		totalFactAmtA		float  null,--  去年同月总实业绩    
		totalFactAmtB		float  null,--  上月总实业绩    
		totalFactAmtRateA	float  null,--  实业绩同比    
		totalFactAmtRateB	float  null,--  实业绩环比    
	     
		saleCardAmt			float  null,--  本月总售卡    
		saleCardAmtA		float  null,--  去年同月总售卡    
		saleCardAmtB		float  null,--  上月总售卡    
		saleCardAmtRateA	float  null,--  售卡同比    
		saleCardAmtRateB	float  null,--  售卡环比    
	     
		pinCardAmt			float  null,--  本月总销卡    
		pinCardAmtA			float  null,--  去年同月总销卡    
		pinCardAmtB			float  null,--  上月总销卡    
		pinCardAmtRateA		float  null,--  销卡同比    
		pinCardAmtRateB		float  null,--  销卡环比    
	     
		buyGoodsAmt			float  null,--  本月总买产品    
		buyGoodsAmtA		float  null,--  去年同月总买产品    
		buyGoodsAmtB		float  null,--  上月买产品业额    
		buyGoodsAmtRateA	float  null,--  买产品同比    
		buyGoodsAmtRateB	float  null,--  买产品环比    
	)   
	create nonclustered index idx_shopyejianalysis_mmonth on #shopyejianalysis(compid,mmonth)	
	--插入选择的门店编号到#diarialBill_byday_fromShops中                                        
	insert #shopyejianalysis(compid,compname,mmonth)                                        
	 select compno,compname,@month                                        
	 from companyinfo,compchaininfo,compchainstruct
	 where curcomp= @compid and  relationcomp=compno and curcompno=compno and complevel=4
	
	if len(@month)=6    
		set @month=@month+'01' 
		
	----获得上年同月 
	declare @tyear varchar(10)    
	declare @tmonth varchar(10)    
	declare @beforeyearMonth varchar(20)  
	set @beforeyearMonth=DATEADD(year, -1, @month)    
	set @tyear = year(@beforeyearMonth)    
	set @tmonth = month(@beforeyearMonth)    
	if(cast(@tmonth as int)<10)    
		set @tmonth='0'+@tmonth    
	set @beforeyearMonth=@tyear+@tmonth  
     
    ----获得本年同月 
    declare @beforeMonth varchar(20)    
	set @beforeMonth=DATEADD(month, -1, @month)    
	set @tyear = year(@beforeMonth)    
	set @tmonth = month(@beforeMonth)    
	if(cast(@tmonth as int)<10)    
		set @tmonth='0'+@tmonth    
	set @beforeMonth=@tyear+@tmonth 
	
	
	--总营业额totalAmt
	update #shopyejianalysis set totalAmt=ISNULL((select SUM(isnull(total,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@month,1,6)+'%'),0)
	update #shopyejianalysis set totalAmtA=ISNULL((select SUM(isnull(total,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@beforeyearMonth,1,6)+'%'),0)
	update #shopyejianalysis set totalAmtB=ISNULL((select SUM(isnull(total,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@beforeMonth,1,6)+'%'),0)
	 
	 --总实业绩totalFactAmt
	update a set totalFactAmt=ISNULL((select SUM(isnull(realtotalyeji,0)) from compclasstraderesult b
	 where a.compid=b.compid and ddate like '%'+SUBSTRING(@month,1,6)+'%'),0) from #shopyejianalysis a
	update a set totalFactAmtA=ISNULL((select SUM(isnull(realtotalyeji,0)) from compclasstraderesult b 
	 where a.compid=b.compid and ddate like '%'+SUBSTRING(@beforeyearMonth,1,6)+'%'),0) from #shopyejianalysis a
	update a set totalFactAmtB=ISNULL((select SUM(isnull(realtotalyeji,0)) from compclasstraderesult b
	 where a.compid=b.compid and ddate like '%'+SUBSTRING(@beforeMonth,1,6)+'%'),0) from #shopyejianalysis a
	 
	 --总卡异动业绩saleCardAmt
	update #shopyejianalysis set saleCardAmt=ISNULL((select SUM(isnull(totalCardTrans,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@month,1,6)+'%'),0)
	update #shopyejianalysis set saleCardAmtA=ISNULL((select SUM(isnull(totalCardTrans,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@beforeyearMonth,1,6)+'%'),0)
	update #shopyejianalysis set saleCardAmtB=ISNULL((select SUM(isnull(totalCardTrans,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@beforeMonth,1,6)+'%'),0)
	 
	 	 --总销卡业绩pinCardAmt
	update #shopyejianalysis set pinCardAmt=ISNULL((select SUM(isnull(cardsalesservices,0)+isnull(cardsalesprod,0)+isnull(acquisitionCardServices,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@month,1,6)+'%'),0)
	update #shopyejianalysis set pinCardAmtA =ISNULL((select SUM(isnull(cardsalesservices,0)+isnull(cardsalesprod,0)+isnull(acquisitionCardServices,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@beforeyearMonth,1,6)+'%'),0)
	update #shopyejianalysis set pinCardAmtB =ISNULL((select SUM(isnull(cardsalesservices,0)+isnull(cardsalesprod,0)+isnull(acquisitionCardServices,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@beforeMonth,1,6)+'%'),0)
    
    
    --总产品销售业绩buyGoodsAmt
	update #shopyejianalysis set buyGoodsAmt=ISNULL((select SUM(isnull(cashProd,0)+isnull(creditProd,0)+isnull(checkProd,0)+isnull(zftProd,0)+isnull(ockkProd,0)+isnull(tgkkProd,0)+isnull(cardSalesprod,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@month,1,6)+'%'),0)
	update #shopyejianalysis set buyGoodsAmtA =ISNULL((select SUM(isnull(cashProd,0)+isnull(creditProd,0)+isnull(checkProd,0)+isnull(zftProd,0)+isnull(ockkProd,0)+isnull(tgkkProd,0)+isnull(cardSalesprod,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@beforeyearMonth,1,6)+'%'),0)
	update #shopyejianalysis set buyGoodsAmtB =ISNULL((select SUM(isnull(cashProd,0)+isnull(creditProd,0)+isnull(checkProd,0)+isnull(zftProd,0)+isnull(ockkProd,0)+isnull(tgkkProd,0)+isnull(cardSalesprod,0)) from detial_trade_byday_fromshops 
	 where compid=shopId and dateReport like '%'+SUBSTRING(@beforeMonth,1,6)+'%'),0)
    
    
	update #shopyejianalysis set totalAmtA=1 where totalAmtA=0    
	update #shopyejianalysis set totalFactAmtA=1 where totalFactAmtA=0    
	update #shopyejianalysis set saleCardAmtA=1 where saleCardAmtA=0    
	update #shopyejianalysis set pinCardAmtA=1 where pinCardAmtA=0    
	update #shopyejianalysis set buyGoodsAmtA=1 where buyGoodsAmtA=0    
	update #shopyejianalysis set totalAmtB=1 where totalAmtB=0    
	update #shopyejianalysis set totalFactAmtB=1 where totalFactAmtB=0    
	update #shopyejianalysis set saleCardAmtB=1 where saleCardAmtB=0    
	update #shopyejianalysis set pinCardAmtB=1 where pinCardAmtB=0    
	update #shopyejianalysis set buyGoodsAmtB=1 where buyGoodsAmtB=0    
    
    
	update #shopyejianalysis set totalAmtRateA=isnull(totalAmt,0)/isnull(totalAmtA,1),totalAmtRateB=isnull(totalAmt,0)/isnull(totalAmtB,1),    
     
        totalFactAmtRateA=isnull(totalFactAmt,0)/isnull(totalFactAmtA,1),totalFactAmtRateB=isnull(totalFactAmt,0)/isnull(totalFactAmtB,1),    
     
        saleCardAmtRateA=isnull(saleCardAmt,0)/isnull(saleCardAmtA,1),saleCardAmtRateB=isnull(saleCardAmt,0)/isnull(saleCardAmtB,1),    
     
        pinCardAmtRateA=isnull(pinCardAmt,0)/isnull(pinCardAmtA,1),pinCardAmtRateB=isnull(pinCardAmt,0)/isnull(pinCardAmtB,1),    
    
        buyGoodsAmtRateA=isnull(buyGoodsAmt,0)/isnull(buyGoodsAmtA,1),buyGoodsAmtRateB=isnull(buyGoodsAmt,0)/isnull(buyGoodsAmtB,1)    
    
	update #shopyejianalysis set totalAmtA=0,totalAmtRateA=0 where totalAmtA=1    
	update #shopyejianalysis set totalFactAmtA=0,totalFactAmtRateA=0 where totalFactAmtA=1    
	update #shopyejianalysis set saleCardAmtA=0,saleCardAmtRateA=0 where saleCardAmtA=1    
	update #shopyejianalysis set pinCardAmtA=0,pinCardAmtRateA=0 where pinCardAmtA=1    
	update #shopyejianalysis set buyGoodsAmtA=0,buyGoodsAmtRateA=0 where buyGoodsAmtA=1    
	update #shopyejianalysis set totalAmtB=0,totalAmtRateB=0 where totalAmtB=1    
	update #shopyejianalysis set totalFactAmtB=0,totalFactAmtRateB=0 where totalFactAmtB=1    
	update #shopyejianalysis set saleCardAmtB=0,saleCardAmtRateB=0 where saleCardAmtB=1    
	update #shopyejianalysis set pinCardAmtB=0,pinCardAmtRateB=0 where pinCardAmtB=1    
	update #shopyejianalysis set buyGoodsAmtB=0,buyGoodsAmtRateB=0 where buyGoodsAmtB=1    
    
	select compid,compname,mmonth,totalAmt,totalAmtA,totalAmtB,totalAmtRateA=convert(numeric(20,2),totalAmtRateA),totalAmtRateB=convert(numeric(20,2),totalAmtRateB),    
		totalFactAmt,totalFactAmtA,totalFactAmtB,totalFactAmtRateA=convert(numeric(20,2),totalFactAmtRateA),totalFactAmtRateB=convert(numeric(20,2),totalFactAmtRateB),    
		saleCardAmt,saleCardAmtA,saleCardAmtB,saleCardAmtRateA=convert(numeric(20,2),saleCardAmtRateA),saleCardAmtRateB=convert(numeric(20,2),saleCardAmtRateB),    
		pinCardAmt,pinCardAmtA,pinCardAmtB,pinCardAmtRateA=convert(numeric(20,2),pinCardAmtRateA),pinCardAmtRateB=convert(numeric(20,2),pinCardAmtRateB),    
		buyGoodsAmt,buyGoodsAmtA,buyGoodsAmtB,buyGoodsAmtRateA=convert(numeric(20,2),buyGoodsAmtRateA),buyGoodsAmtRateB=convert(numeric(20,2),buyGoodsAmtRateB)     
	from #shopyejianalysis    
	order by  compid asc
   
    drop table #shopyejianalysis    
end
GO
/****** Object:  StoredProcedure [dbo].[upg_validate_noperformance_in3days_byday]    Script Date: 01/14/2014 15:45:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_validate_noperformance_in3days_byday]   
as  
begin   
		declare @compid  varchar(10)  
		set @compid='001'  
		declare @curdate varchar(10)  
		declare @enddate varchar(10)  
		select @curdate=substring(convert(varchar(20),getdate(),102),1,4) + substring(convert(varchar(20),getdate(),102),6,2)+ substring(convert(varchar(20),getdate(),102),9,2)  
		exec upg_date_plus  @curdate,-3,@enddate output  
  
		create table #empinfo  
		(  
			compid   varchar(10) null, --门店编号  
			empid   varchar(20) null, --员工编号  
			empinnerno  varchar(20) null, --员工内部编号  
			department  varchar(10) null, --部门编号  
			comdate   varchar(10) null, --到职日期  
		)  
  
	--插入到职人员  
	insert #empinfo(compid,empid,empinnerno,department)  
    select compno,staffno,Manageno,department from staffinfo,compchaininfo   
	where compno=relationcomp and curcomp=@compid and curstate='2'   
		and ISNULL(businessflag,0)=1  
		and staffno<> compno+'300' and staffno<> compno+'400' and staffno<> compno+'500' and staffno<> compno+'600'  
   
   
   
  
    --获得这个星期内跨店调动/到职/重回公司的员工并过滤掉  
	delete #empinfo where empinnerno in ( select manageno from staffhistory where manageno=empinnerno  and effectivedate> @enddate )  
   
	--排除在收银作业中有业绩的员工  
  
	 delete #empinfo  from #empinfo,mconsumeinfo a with(nolock),dconsumeinfo b with(nolock)  
	 where a.cscompid=b.cscompid and a.csbillid=b.csbillid and csdate between @enddate and @curdate  
      and a.cscompid=compid and ( csfirstsaler=empid or cssecondsaler=empid or csthirdsaler=empid)   
  
 
	--排除在开卡中有业绩的员工  
   
	delete #empinfo  from #empinfo,msalecardinfo with(nolock)  
	where salecompid=compid and  saledate between @enddate and @curdate  
		and ( firstsalerid=empid or secondsalerid=empid or thirdsalerid=empid or fourthsalerid=empid or fifthsalerid=empid or sixthsalerid=empid or seventhsalerid=empid or eighthsalerid=empid or ninthsalerid=empid or tenthsalerid=empid)   
		

 --排除在充值还款中有业绩的员工  
	delete #empinfo  from #empinfo,mcardrechargeinfo with(nolock)  
   where rechargecompid=compid and  rechargedate between @enddate and @curdate  
	and ( firstsalerid=empid or secondsalerid=empid or thirdsalerid=empid or fourthsalerid=empid or fifthsalerid=empid or sixthsalerid=empid or seventhsalerid=empid or eighthsalerid=empid or ninthsalerid=empid or tenthsalerid=empid)   
		
  
  
  
 delete #empinfo  from #empinfo,mproexchangeinfo a with(nolock) , dproexchangeinfo b with(nolock)
 where  a.changecompid=b.changecompid and a.changebillid=b.changebillid and  a.changecompid=compid and  changedate between @enddate and @curdate  
    and ( firstsalerid=empid or secondsalerid=empid or thirdsalerid=empid )
    
 delete noperformanceemp from noperformanceemp,compchaininfo  where ddate=@curdate and curcomp=@compid and relationcomp=compid  
 insert noperformanceemp (compid,empid,empinnerno,ddate)  
 select compid,empid,empinnerno,@curdate from #empinfo  
 drop table #empinfo  
end  

--exec upg_validate_noperformance_in3days_byday
GO
/****** Object:  StoredProcedure [dbo].[upg_prj_goods_consume_analysize]    Script Date: 01/14/2014 15:45:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_prj_goods_consume_analysize]              
(              
  @compid			varchar(10),			--公司编号          
  @topnum			int,					--前多少名      
  @fromdate			varchar(18),			--开始日期             
  @todate			varchar(18),			--结束日期      
  @prjsmallamt      float,					--项目开始金额      
  @prjbigamt        float,					--项目结束金额  
  @goodssmallamt    float,					--产品开始金额      
  @goodsbigamt      float					--产品结束金额           
                
)              
as -- 项目消耗统计分析          
begin                 
     create table #prj_info (                
        compid			varchar(150)     null,       -- 公司编号
        compname		varchar(150)     null,       -- 公司编号             
        iseqno			int			     null,       -- 项目编号 
        prjno			varchar(20)      null,       -- 项目编号         
        prjname			varchar(40)      null,       -- 项目名称                  
        prjtype			varchar(30)      null,       -- 类别        
        prjcnt          float            null,       -- 数量      
        prjamt          float            null,       -- 金额            
        goodsno			varchar(20)      null,       -- 项目编号      
        goodsname		varchar(40)      null,       -- 项目名称                  
        goodstype		varchar(30)      null,       -- 类别        
        goodscnt        float            null,       -- 数量      
        goodsamt        float            null       -- 金额
     )      
     
 
     declare @strSql varchar(2000)   
     set @strSql='insert #prj_info(compid,iseqno,prjno,prjcnt,prjamt)   select  top '+str(@topnum)+' curcomp ,row_number() over(order by  SUM(ISNULL(csitemamt,0)) desc), csitemno,SUM(ISNULL(csitemcount,0)),csitemamt=SUM(ISNULL(csitemamt,0)) 
     from mconsumeinfo a with(nolock), dconsumeinfo b with(nolock), compchaininfo
     where curcomp= '+ str(@compid) +' and a.cscompid=relationcomp
       and financedate between '+str(@fromdate)+'  and '+str(@todate)+' 
       and a.cscompid=b.cscompid and a.csbillid=b.csbillid and csinfotype=1
     group by curcomp,csitemno
     having SUM(ISNULL(csitemamt,0))  between '+str(@prjsmallamt)+'  and '+str(@prjbigamt)+' 
     order by  SUM(ISNULL(csitemamt,0)) desc'
     execute(@strSql)   
     set @strSql='update c set goodsno=goodsno_s,goodscnt=goodscnt_s,goodsamt=goodsamt_s
     from  #prj_info c,(  select  top '+str(@topnum)+' compid=curcomp ,iseqno=row_number() over(order by  SUM(ISNULL(csitemamt,0)) desc), goodsno_s=csitemno,goodscnt_s=SUM(ISNULL(csitemcount,0)),goodsamt_s=SUM(ISNULL(csitemamt,0)) 
     from mconsumeinfo a with(nolock), dconsumeinfo b with(nolock), compchaininfo
     where curcomp= '+ str(@compid) +' and a.cscompid=relationcomp
       and financedate between '+str(@fromdate)+'  and '+str(@todate)+' 
       and a.cscompid=b.cscompid and a.csbillid=b.csbillid and csinfotype=2
     group by curcomp,csitemno
     having SUM(ISNULL(csitemamt,0))  between '+str(@goodssmallamt)+'  and '+str(@goodsbigamt)+' 
     ) as d where c.compid=d.compid and c.iseqno=d.iseqno '
 
	 execute(@strSql)   
       
        
     update a set prjname=b.prjname,prjtype=prjreporttype
     from #prj_info a,projectnameinfo b where a.prjno=b.prjno
     
     update a set goodsname=b.goodsname,goodstype=goodspricetype
     from #prj_info a,goodsnameinfo b where a.goodsno=b.goodsno
    
	 update a set compname=b.compname
	 from #prj_info a,companyinfo b where b.compno=a.compid 
	 
	 select compid,iseqno,compname,prjno,prjname,prjtype,prjcnt,prjamt,goodsno,goodsname,goodstype,goodscnt,goodsamt 
	 from #prj_info order by iseqno asc
     drop table #prj_info      
       
end
GO
/****** Object:  StoredProcedure [dbo].[upg_prepare_yeji_detail]    Script Date: 01/14/2014 15:45:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_prepare_yeji_detail](          
	@compid				varchar(10),	-- 公司别          
	@fromdate			varchar(10),	-- 开始日期          
	@todate				varchar(10),	-- 截至日期          
	@fromempinno		varchar(20),	-- 查询开始人员内部编号         
	@toempinno			varchar(20)		-- 查询截至人员内部编号 
)     
as          
begin          
            
	
      
           
 
	CREATE tAbLE	#msalecardinfo               -- 会员卡销售单
	(
		salecompid			varchar(10)			Not NULL,   --公司编号
		salebillid			varchar(20)			Not NULL,   --销售单号
		saledate			varchar(8)				NULL,   --销售日期
		salecardno			varchar(20)				NULL,   --销售卡号
		salecardtype		varchar(20)				NULL,   --销售卡类型
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		salekeepamt			float					NULL,	--储值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)     
    insert #msalecardinfo(salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt)
	select salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt
    from msalecardinfo, dpayinfo
    where salebillid=paybillid and salecompid=paycompid and paybilltype='SK'
      and salecompid=@compid and financedate between @fromdate and @todate and ISNULL(salekeepamt,0)<>0  and ISNULL(salebakflag,0)=0
        
        
                  
         
	--卖卡第一销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,firstsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a ,cardtypenameinfo c     
	where a.firstsalerinid >= @fromempinno          
	and a.firstsalerinid <= @toempinno          
    and isnull(firstsaleamt,0)<>0   
    and salecardtype=cardtypeno
    
    --卖卡第二销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,secondsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a,cardtypenameinfo c         
	where a.secondsalerinid >= @fromempinno          
	and a.secondsalerinid <= @toempinno          
    and isnull(secondsaleamt,0)<>0            
    and salecardtype=cardtypeno     
    --卖卡第三销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,thirdsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a,cardtypenameinfo c          
	where a.thirdsalerinid >= @fromempinno          
	and a.thirdsalerinid <= @toempinno          
    and isnull(thirdsaleamt,0)<>0
    and salecardtype=cardtypeno 
    --卖卡第四销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fourthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,fourthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a ,cardtypenameinfo c        
	where a.fourthsalerinid >= @fromempinno          
	and a.fourthsalerinid <= @toempinno          
    and isnull(fourthsaleamt,0)<>0
    and salecardtype=cardtypeno 
    --卖卡第五销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fifthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,fifthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a,cardtypenameinfo c         
	where a.fifthsalerinid >= @fromempinno          
	and a.fifthsalerinid <= @toempinno          
    and isnull(fifthsaleamt,0)<>0
    and salecardtype=cardtypeno 
    --卖卡第六销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,sixthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,sixthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a,cardtypenameinfo c         
	where a.sixthsalerinid >= @fromempinno          
	and a.sixthsalerinid <= @toempinno          
    and isnull(sixthsaleamt,0)<>0
    and salecardtype=cardtypeno 
    --卖卡第七销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,seventhsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,seventhsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a ,cardtypenameinfo c        
	where a.seventhsalerinid >= @fromempinno          
	and a.seventhsalerinid <= @toempinno          
    and isnull(seventhsaleamt,0)<>0
    and salecardtype=cardtypeno  
    --卖卡第八销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,eighthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,eighthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a ,cardtypenameinfo c        
	where a.eighthsalerinid >= @fromempinno          
	and a.eighthsalerinid <= @toempinno          
    and isnull(eighthsaleamt,0)<>0
    and salecardtype=cardtypeno 
    --卖卡第九销售人员  ,cardtypenameinfo c           
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,ninthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,ninthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a ,cardtypenameinfo c        
	where a.ninthsalerinid >= @fromempinno          
	and a.ninthsalerinid <= @toempinno          
    and isnull(ninthsaleamt,0)<>0
    and salecardtype=cardtypeno 
    --卖卡第十销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,tenthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,tenthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a  ,cardtypenameinfo c       
	where a.tenthsalerinid >= @fromempinno          
	and a.tenthsalerinid <= @toempinno          
    and isnull(tenthsaleamt,0)<>0
    and salecardtype=cardtypeno
    CREATE tAbLE	#mcardrechargeinfo              -- 会员卡充值单
	(
		rechargecompid			varchar(10)			Not NULL,   --充值门店
		rechargebillid			varchar(20)			Not NULL,   --充值单号 
		rechargedate			varchar(8)				NULL,   --充值日期 
		rechargetime			varchar(6)				NULL,   --充值时间 
		rechargecardno			varchar(20)				NULL,   --会员卡号
		rechargecardtype		varchar(10)				NULL,   --卡类型
		rechargetype			int						NULL,   --续费方式( 0充值 ,6还款 ,)
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		rechargekeepamt		float					NULL,   --充值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)     
	
	insert #mcardrechargeinfo(rechargecompid,rechargebillid,rechargedate,rechargecardno,rechargecardtype,rechargetype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt)
	select rechargecompid,rechargebillid,rechargedate,rechargecardno,rechargecardtype,rechargetype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt
    from mcardrechargeinfo, dpayinfo
    where rechargebillid=paybillid and rechargecompid=paycompid and paybilltype='CZ'
      and rechargecompid=@compid and financedate between @fromdate and @todate and ISNULL(rechargekeepamt,0)<>0  and ISNULL(salebakflag,0)=0 and isnull(backbillid,'')=''
	
	
	update #mcardrechargeinfo set rechargecardtype=cardtype
	from #mcardrechargeinfo,cardinfo with(nolock)
	where rechargecardno=cardno and ISNULL(rechargetype,0)=1
	
		--卖卡第一销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,firstsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where a.firstsalerinid >= @fromempinno          
	and a.firstsalerinid <= @toempinno          
    and isnull(firstsaleamt,0)<>0   
    and rechargecardtype=cardtypeno
    --卖卡第二销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,secondsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where a.secondsalerinid >= @fromempinno          
	and a.secondsalerinid <= @toempinno          
    and isnull(secondsaleamt,0)<>0            
     and rechargecardtype=cardtypeno    
    --卖卡第三销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,thirdsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a   ,cardtypenameinfo c        
	where a.thirdsalerinid >= @fromempinno          
	and a.thirdsalerinid <= @toempinno          
    and isnull(thirdsaleamt,0)<>0
     and rechargecardtype=cardtypeno
    --卖卡第四销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fourthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,fourthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a    ,cardtypenameinfo c       
	where a.fourthsalerinid >= @fromempinno          
	and a.fourthsalerinid <= @toempinno          
    and isnull(fourthsaleamt,0)<>0
     and rechargecardtype=cardtypeno
    --卖卡第五销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fifthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,fifthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where a.fifthsalerinid >= @fromempinno          
	and a.fifthsalerinid <= @toempinno          
    and isnull(fifthsaleamt,0)<>0
     and rechargecardtype=cardtypeno
    --卖卡第六销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,sixthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,sixthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a ,cardtypenameinfo c       
	where a.sixthsalerinid >= @fromempinno          
	and a.sixthsalerinid <= @toempinno          
    and isnull(sixthsaleamt,0)<>0
    and rechargecardtype=cardtypeno 
    --卖卡第七销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,seventhsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,seventhsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a ,cardtypenameinfo c          
	where a.seventhsalerinid >= @fromempinno          
	and a.seventhsalerinid <= @toempinno          
    and isnull(seventhsaleamt,0)<>0
     and rechargecardtype=cardtypeno
    --卖卡第八销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,eighthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,eighthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where a.eighthsalerinid >= @fromempinno          
	and a.eighthsalerinid <= @toempinno          
    and isnull(eighthsaleamt,0)<>0
     and rechargecardtype=cardtypeno
    --卖卡第九销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,ninthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,ninthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where a.ninthsalerinid >= @fromempinno          
	and a.ninthsalerinid <= @toempinno          
    and isnull(ninthsaleamt,0)<>0
     and rechargecardtype=cardtypeno
    --卖卡第十销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,tenthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,tenthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where a.tenthsalerinid >= @fromempinno          
	and a.tenthsalerinid <= @toempinno          
    and isnull(tenthsaleamt,0)<>0
    and rechargecardtype=cardtypeno
     
    CREATE tAbLE	#mcardchangeinfo             -- 会员卡异动
	(
		changecompid		varchar(10)			Not NULL,   --充值门店
		changebillid		varchar(20)			Not NULL,   --充值单号 
		changetype			int					Not NULL,
		changedate			varchar(8)				NULL,   --充值日期 
		changetime			varchar(6)				NULL,   --充值时间 
		changeaftercardno	varchar(20)				NULL,   --会员卡号
		changeaftercardtype	varchar(10)				NULL,   --卡类型
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		changefillamt		float					NULL,   --充值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)   
	
	insert #mcardchangeinfo(changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt)
	select changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt
    from mcardchangeinfo, dpayinfo
    where changebillid=paybillid and changecompid=paycompid and paybilltype in ('ZK','TK')
      and changecompid=@compid and financedate between @fromdate and @todate and ISNULL(changefillamt,0)<>0   and ISNULL(salebakflag,0)=0
      
      
     	--卖卡第一销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*firstsaleamt*payamt/isnull(changefillamt,1) else   firstsaleamt*payamt/isnull(changefillamt,1) end         
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where a.firstsalerinid >= @fromempinno          
	and a.firstsalerinid <= @toempinno          
    and isnull(firstsaleamt,0)<>0   

    --卖卡第二销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*secondsaleamt*payamt/isnull(changefillamt,1) else   secondsaleamt*payamt/isnull(changefillamt,1) end      
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where a.secondsalerinid >= @fromempinno          
	and a.secondsalerinid <= @toempinno          
    and isnull(secondsaleamt,0)<>0            

    --卖卡第三销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*thirdsaleamt*payamt/isnull(changefillamt,1) else   thirdsaleamt*payamt/isnull(changefillamt,1) end   
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where a.thirdsalerinid >= @fromempinno          
	and a.thirdsalerinid <= @toempinno          
    and isnull(thirdsaleamt,0)<>0

    --卖卡第四销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fourthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*fourthsaleamt*payamt/isnull(changefillamt,1) else   fourthsaleamt*payamt/isnull(changefillamt,1) end       
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where a.fourthsalerinid >= @fromempinno          
	and a.fourthsalerinid <= @toempinno          
    and isnull(fourthsaleamt,0)<>0

    --卖卡第五销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fifthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*fifthsaleamt*payamt/isnull(changefillamt,1) else   fifthsaleamt*payamt/isnull(changefillamt,1) end   
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where a.fifthsalerinid >= @fromempinno          
	and a.fifthsalerinid <= @toempinno          
    and isnull(fifthsaleamt,0)<>0

    --卖卡第六销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,sixthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*sixthsaleamt*payamt/isnull(changefillamt,1) else   sixthsaleamt*payamt/isnull(changefillamt,1) end        
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where a.sixthsalerinid >= @fromempinno          
	and a.sixthsalerinid <= @toempinno          
    and isnull(sixthsaleamt,0)<>0
   
    --卖卡第七销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,seventhsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*seventhsaleamt*payamt/isnull(changefillamt,1) else   seventhsaleamt*payamt/isnull(changefillamt,1) end          
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where a.seventhsalerinid >= @fromempinno          
	and a.seventhsalerinid <= @toempinno          
    and isnull(seventhsaleamt,0)<>0
   
    --卖卡第八销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,eighthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*eighthsaleamt*payamt/isnull(changefillamt,1) else   eighthsaleamt*payamt/isnull(changefillamt,1) end   
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where a.eighthsalerinid >= @fromempinno          
	and a.eighthsalerinid <= @toempinno          
    and isnull(eighthsaleamt,0)<>0
   
    --卖卡第九销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,ninthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*ninthsaleamt*payamt/isnull(changefillamt,1) else  ninthsaleamt*payamt/isnull(changefillamt,1) end   
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where a.ninthsalerinid >= @fromempinno          
	and a.ninthsalerinid <= @toempinno          
    and isnull(ninthsaleamt,0)<>0
   
    --卖卡第十销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,tenthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*tenthsaleamt*payamt/isnull(changefillamt,1) else   tenthsaleamt*payamt/isnull(changefillamt,1) end   
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where a.tenthsalerinid >= @fromempinno          
	and a.tenthsalerinid <= @toempinno          
    and isnull(tenthsaleamt,0)<>0
   
    
    CREATE tAbLE	#mproexchangeinfo               -- 会员卡疗程兑换
	(
		changecompid			varchar(10)			Not NULL,   --公司编号
		changebillid			varchar(20)			Not NULL,   --销售单号
		changedate				varchar(8)				NULL,   --销售日期
		changecardno			varchar(20)				NULL,   --销售卡号
		changecardtype			varchar(20)				NULL,   --销售卡类型
		firstsalerid			varchar(20)				NULL,			--第一销售工号
		firstsalerinid			varchar(20)				NULL,			--第一销售内部编号
		firstsaleamt			float					NULL,			--第一销售分享金额
		secondsalerid			varchar(20)				NULL,			--第二销售工号
		secondsalerinid			varchar(20)				NULL,			--第二销售内部编号
		secondsaleamt			float					NULL,			--第二销售分享金额
		thirdsalerid			varchar(20)				NULL,			--第三销售工号----- 烫染师
		thirdsalerinid			varchar(20)				NULL,			--第三销售内部编号
		thirdsaleamt			float					NULL,			--第三销售分享金额
		fourthsalerid			varchar(20)				NULL,			--第四销售工号----- 烫染师
		fourthsalerinid			varchar(20)				NULL,			--第四销售内部编号
		fourthsaleamt			float					NULL,			--第四销售分享金额
		financedate				varchar(8)				NULL,   --帐务日期 
		changeproamt			float					NULL,	--储值金额
		paymode					varchar(10)				NULL,	--支付方式
		payamt					float					NULL,	--支付金额
	)  
	insert #mproexchangeinfo(changecompid,changebillid,changedate,changecardno,changecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changeproamt,paymode,payamt)   
    select a.changecompid,a.changebillid,changedate,changecardno,changecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changeproamt,changepaymode,changebycashamt 
        from mproexchangeinfo a,dproexchangeinfo b
    where a.changecompid=b.changecompid and a.changebillid=b.changebillid
    and  financedate between @fromdate and @todate and ISNULL(changeproamt,0)<>0 and a.changecompid=@compid 
    and ISNULL(backcsflag,0)=0  and ISNULL(salebakflag,0)=0
      	--卖卡第一销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,firstsaleamt        
	from #mproexchangeinfo a      
	where a.firstsalerinid >= @fromempinno          
	and a.firstsalerinid <= @toempinno          
    and isnull(firstsaleamt,0)<>0   
    
    --卖卡第二销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,secondsaleamt
	from #mproexchangeinfo a      
	where a.secondsalerinid >= @fromempinno          
	and a.secondsalerinid <= @toempinno          
    and isnull(secondsaleamt,0)<>0            
        
    --卖卡第三销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,thirdsaleamt
	from #mproexchangeinfo a      
	where a.thirdsalerinid >= @fromempinno          
	and a.thirdsalerinid <= @toempinno          
    and isnull(thirdsaleamt,0)<>0
    
    --卖卡第四销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fourthsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,fourthsaleamt
	from #mproexchangeinfo a      
	where a.fourthsalerinid >= @fromempinno          
	and a.fourthsalerinid <= @toempinno          
    and isnull(fourthsaleamt,0)<>0
    
    CREATE tAbLE	#msalebarcodecardinfo             -- 系统条码卡销售主档
	(
		salecompid			varchar(10)		not null	,--销售门店
		salebillid			varchar(20)		not null	,--销售单号
		saledate			varchar(8)			null	,--销售日期
		barcodecardno		varchar(20)			null	,--销售条码卡卡号
		firstpaymode		varchar(20)			null	,--第一支付方式
		firstpayamt			float				null	,--第一支付金额
		secondpaymode		varchar(20)			null	,--第二支付方式
		secondpayamt		varchar(20)			null	,--第二支付金额
		saleamt				float				null	,--销售总额
		firstsaleempid		varchar(20)			null	,--第一销售工号
		firstsaleempinid	varchar(20)			null	,--第一销售内部工号
		firstsaleamt		float				null	,--第一销售分享金额
		secondsaleempid		varchar(20)			null	,--第二销售工号
		secondsaleempinid	varchar(20)			null	,--第二销售内部工号
		secondsaleamt		float				null	,--第二销售分享金额
		thirdsaleempid		varchar(20)			null	,--第三销售工号
		thirdsaleempinid	varchar(20)			null	,--第三销售内部工号
		thirdsaleamt		float				null	,--第三销售分享金额
	)  
	
	insert #msalebarcodecardinfo(salecompid,salebillid,saledate,barcodecardno,firstpaymode,firstpayamt,secondpaymode,secondpayamt,saleamt,
	firstsaleempid,firstsaleempinid,firstsaleamt,secondsaleempid,secondsaleempinid,secondsaleamt,thirdsaleempid,thirdsaleempinid,thirdsaleamt)
    select salecompid,salebillid,saledate,barcodecardno,firstpaymode,firstpayamt,secondpaymode,secondpayamt,saleamt,
	firstsaleempid,firstsaleempinid,firstsaleamt,secondsaleempid,secondsaleempinid,secondsaleamt,thirdsaleempid,thirdsaleempinid,thirdsaleamt
	from msalebarcodecardinfo
    where salecompid=@compid and saledate between @fromdate and @todate and ISNULL(saleamt,0)<>0  
    
      	--卖卡第一销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsaleempinid,5,saledate,barcodecardno,'条码卡',saleamt,1,salebillid,firstpaymode,firstsaleamt        
	from #msalebarcodecardinfo a      
	where a.firstsaleempinid >= @fromempinno          
	and a.firstsaleempinid <= @toempinno          
    and isnull(firstsaleamt,0)<>0   
    
    --卖卡第二销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsaleempinid,5,saledate,barcodecardno,'条码卡',saleamt,1,salebillid,firstpaymode,secondsaleamt
	from #msalebarcodecardinfo a      
	where a.secondsaleempinid >= @fromempinno          
	and a.secondsaleempinid <= @toempinno          
    and isnull(secondsaleamt,0)<>0            
        
    --卖卡第三销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsaleempinid,5,saledate,barcodecardno,'条码卡',saleamt,1,salebillid,firstpaymode,thirdsaleamt
	from #msalebarcodecardinfo a      
	where a.thirdsaleempinid >= @fromempinno          
	and a.thirdsaleempinid <= @toempinno          
    and isnull(thirdsaleamt,0)<>0
    
    --合作项目
	CREATE tAbLE    #mcooperatesaleinfo             
	(
		salecompid				char(10)				Not NULL,   --公司编号
		salebillid				varchar(20)				Not NULL,   --异动单号
		saledate				varchar(8)				NULL    ,   --异动日期
		salecooperid			varchar(30)				NULL    ,   --合作单位
		slaepaymode				varchar(5)				NULL    ,   --支付方向 1 店内支付，2 合作单位支付
		salecostcardno			varchar(20)				NULL    ,   --会员卡号
		salecostcardtype		varchar(20)				NULL    ,   --会员卡类型
		salecostproamt			float					NULL    ,   --项目金额
		firstsalerid			varchar(20)				NULL,   --第一销售工号
		firstsalerinid			varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt			float					NULL,   --第一销售分享金额
		secondsalerid			varchar(20)				NULL,   --第二销售工号
		secondsalerinid			varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt			float					NULL,   --第二销售分享金额
		thirdsalerid			varchar(20)				NULL,   --第三销售工号
		thirdsalerinid			varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt			float					NULL,   --第三销售分享金额
		fourthsalerid			varchar(20)				NULL,   --第四销售工号
		fourthsalerinid			varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt			float					NULL,   --第四销售分享金额
		fifthsalerid			varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid			varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt			float					NULL,   --第五销售分享金额
		sixthsalerid			varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid			varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt			float					NULL,   --第六销售分享金额
		seventhsalerid			varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid		varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt			float					NULL,   --第七销售分享金额
		eighthsalerid			varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid			varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt			float					NULL,   --第八销售分享金额
		financedate				varchar(8)				NULL,   --帐务日期 
		paymode					varchar(10)				NULL,	--支付方式
		payamt					float					NULL,	--支付金额
	)
	
	insert #mcooperatesaleinfo(salecompid,salebillid,saledate,salecooperid,slaepaymode,salecostcardno,salecostcardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     financedate,salecostproamt,paymode,payamt)
	select salecompid,salebillid,saledate,salecooperid,slaepaymode,salecostcardno,salecostcardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     financedate,salecostproamt,paymode,payamt
    from mcooperatesaleinfo, dpayinfo
    where salebillid=paybillid and salecompid=paycompid and paybilltype='HZ'
      and salecompid=@compid and financedate between @fromdate and @todate and ISNULL(salecostproamt,0)<>0
     -- and ISNULL(slaepaymode,0)=1 
      and ISNULL(salebillflag,0)=2
      
      	--卖卡第一销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,firstsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where a.firstsalerinid >= @fromempinno          
	and a.firstsalerinid <= @toempinno          
    and isnull(firstsaleamt,0)<>0   
    
    --卖卡第二销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,secondsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where a.secondsalerinid >= @fromempinno          
	and a.secondsalerinid <= @toempinno          
    and isnull(secondsaleamt,0)<>0            
        
    --卖卡第三销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,thirdsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where a.thirdsalerinid >= @fromempinno          
	and a.thirdsalerinid <= @toempinno          
    and isnull(thirdsaleamt,0)<>0
    
    --卖卡第四销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fourthsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,fourthsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where a.fourthsalerinid >= @fromempinno          
	and a.fourthsalerinid <= @toempinno          
    and isnull(fourthsaleamt,0)<>0
    
    --卖卡第五销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fifthsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,fifthsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where a.fifthsalerinid >= @fromempinno          
	and a.fifthsalerinid <= @toempinno          
    and isnull(fifthsaleamt,0)<>0
    
    --卖卡第六销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,sixthsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,sixthsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where a.sixthsalerinid >= @fromempinno          
	and a.sixthsalerinid <= @toempinno          
    and isnull(sixthsaleamt,0)<>0
    
    --卖卡第七销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,seventhsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,seventhsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where a.seventhsalerinid >= @fromempinno          
	and a.seventhsalerinid <= @toempinno          
    and isnull(seventhsaleamt,0)<>0
    
    --卖卡第八销售人员          
	insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,eighthsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,eighthsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where a.eighthsalerinid >= @fromempinno          
	and a.eighthsalerinid <= @toempinno          
    and isnull(eighthsaleamt,0)<>0
    
    create table #mconsumeinfo
    (
		cscompid		varchar(10)     Not NULL,   --公司编号
		csbillid		varchar(20)		Not NULL,   --消费单号
		cscardno		varchar(20)		NULL    ,   --会员卡号
		cscardtype		varchar(10)		NULL    ,   --卡类型
		diyongcardno	varchar(20)		NULL    ,   --抵用券号
		tiaomacardno	varchar(20)		NULL    ,   --条码卡号
		financedate		varchar(8)		NULL    ,   --帐务日期 
		csinfotype		int				Not	NULL,	--消费类型  1 项目  2 产品
		csitemno		varchar(20)     NULL,		--项目/产品代码
		csitemcount		float           NULL,		--数量
		csitemamt		float           NULL,		--金额
		cspaymode		varchar(5)		NULL,		--支付方式
		csfirstsaler	varchar(20)     NULL,		--大工工号
		csfirsttype		varchar(5)     NULL,		--大工类型
		csfirstinid		varchar(20)		NULL,		--大工内部编号
		csfirstshare	float           NULL,		--大工分享
		cssecondsaler	varchar(20)     NULL,		--中工工号
		cssecondtype	varchar(5)     NULL,		--中工类型
		cssecondinid	varchar(20)		NULL,		--中工内部编号
		cssecondshare	float           NULL,		--中工分享
		csthirdsaler	varchar(20)     NULL,		--小工工号
		csthirdtype		varchar(5)		NULL,		--小工类型
		csthirdinid		varchar(20)		NULL,		--小工内部编号
		csthirdshare	float           NULL,		--小工分享
    )
    

    insert #mconsumeinfo(cscompid,csbillid,cscardno,cscardtype,financedate,csinfotype,csitemno,csitemcount,csitemamt,cspaymode,
    csfirstsaler,csfirsttype,csfirstinid,csfirstshare,cssecondsaler,cssecondtype,cssecondinid,cssecondshare,csthirdsaler,csthirdtype,csthirdinid,csthirdshare,diyongcardno,tiaomacardno)
    select a.cscompid,a.csbillid,cscardno,cscardtype,financedate,csinfotype,csitemno,csitemcount,csitemamt,cspaymode,
    csfirstsaler,csfirsttype,csfirstinid,csfirstshare,cssecondsaler,cssecondtype,cssecondinid,cssecondshare,csthirdsaler,csthirdtype,csthirdinid,csthirdshare,diyongcardno,tiaomacardno
     from  mconsumeinfo a,dconsumeinfo b
    where a.cscompid=b.cscompid and a.csbillid=b.csbillid and a.cscompid=@compid and financedate between @fromdate and @todate and ISNULL(csitemamt,0)<>0 
	
	-------------------------这句话非常重要, 美容大项如果多人分享要除相应比重---------------------------
	update #mconsumeinfo set csitemamt=convert(numeric(20,2),ISNULL(csitemamt,0)/(case when isnull(csfirstsaler,'')<>''   then 1 else 0 end +        
        case when isnull(cssecondsaler,'')<>'' then 1 else 0 end +        
        case when isnull(csthirdsaler,'')<>'' then 1 else 0 end ))        
    from #mconsumeinfo,projectnameinfo with(nolock)        
    where  csitemno=prjno        
	and prjtype='4' and prjpricetype=1        
	and (isnull(cssecondsaler,'')<>''  or  isnull(csthirdsaler,'')<>'' or isnull(csfirstsaler,'')<>'' )   
  
  
     -- 项目大工 7，8，9 (要记录项目抵用券号)
	 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,csfirstinid,isnull(convert(int,isnull(csfirsttype,'2')),1)+6,financedate,csitemno,prjname,csitemamt,csitemcount,csbillid,
      cspaymode,csitemamt,cscardno,	  case when cspaymode  in ('12','11') then diyongcardno when cspaymode='13' then tiaomacardno  else cscardtype end  
	  from #mconsumeinfo b, projectnameinfo c         
      where  b.csitemno = c.prjno and csinfotype=1
      and b.csfirstinid >= @fromempinno          
	  and b.csfirstinid <= @toempinno          
      and isnull(csitemamt,0)<>0     and ISNULL(csfirstsaler,'')<>''
      
     -- 项目中工 10，11，12
	 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,cssecondinid,isnull(convert(int,isnull(cssecondtype,'2')),1)+9,financedate,csitemno,prjname,csitemamt,csitemcount,csbillid,
      cspaymode,csitemamt,cscardno,	  case when cspaymode  in ('12','11') then diyongcardno when cspaymode='13' then tiaomacardno  else cscardtype end  
	  from #mconsumeinfo b, projectnameinfo c         
      where  b.csitemno = c.prjno and csinfotype=1
      and b.cssecondinid >= @fromempinno          
	  and b.cssecondinid <= @toempinno          
      and isnull(csitemamt,0)<>0 and ISNULL(cssecondsaler,'')<>''
      
      
      -- 项目小工 13，14，15
	 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,csthirdinid,isnull(convert(int,isnull(csthirdtype,'2')),1)+12,financedate,csitemno,prjname,csitemamt,csitemcount,csbillid,
      cspaymode,csitemamt,cscardno,	  case when cspaymode  in ('12','11') then diyongcardno when cspaymode='13' then tiaomacardno  else cscardtype end  
	  from #mconsumeinfo b, projectnameinfo c         
      where  b.csitemno = c.prjno and csinfotype=1
      and b.csthirdinid >= @fromempinno          
	  and b.csthirdinid <= @toempinno          
      and isnull(csitemamt,0)<>0  and ISNULL(csthirdsaler,'')<>''
      
       -- 产品大工 16，17，18
	 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,csfirstinid,isnull(convert(int,isnull(csfirsttype,'2')),1)+15,financedate,csitemno,goodsname,csitemamt,csitemcount,csbillid,cspaymode,csfirstshare,cscardno,cscardtype
	  from #mconsumeinfo b, goodsnameinfo c         
      where  b.csitemno = c.goodsno and csinfotype=2
      and b.csfirstinid >= @fromempinno          
	  and b.csfirstinid <= @toempinno          
      and isnull(csitemamt,0)<>0      and ISNULL(csfirstsaler,'')<>''
      
     -- 产品中工 19，20，21
	 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,cssecondinid,isnull(convert(int,isnull(cssecondtype,'2')),1)+18,financedate,csitemno,goodsname,csitemamt,csitemcount,csbillid,cspaymode,cssecondshare,cscardno,cscardtype
	  from #mconsumeinfo b, goodsnameinfo c         
      where  b.csitemno = c.goodsno and csinfotype=2
      and b.cssecondinid >= @fromempinno          
	  and b.cssecondinid <= @toempinno          
      and isnull(csitemamt,0)<>0 and ISNULL(cssecondsaler,'')<>''
      
      
      -- 产品小工 22，23，24
	 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,csthirdinid,isnull(convert(int,isnull(csthirdtype,'2')),1)+21,financedate,csitemno,goodsname,csitemamt,csitemcount,csbillid,cspaymode,csthirdshare,cscardno,cscardtype
	  from #mconsumeinfo b, goodsnameinfo c         
      where  b.csitemno = c.goodsno and csinfotype=2
      and b.csthirdinid >= @fromempinno          
	  and b.csthirdinid <= @toempinno          
      and isnull(csitemamt,0)<>0  and ISNULL(csthirdsaler,'')<>''
      
                
    drop table #msalecardinfo    
    drop table #msalebarcodecardinfo
    drop table #mcardrechargeinfo 
    drop table #mcardchangeinfo   
    drop table #mproexchangeinfo 
    drop table #mcooperatesaleinfo 
    drop table #mconsumeinfo
  
            
end
GO
/****** Object:  StoredProcedure [dbo].[upg_prepare_yeji_analysis]    Script Date: 01/14/2014 15:45:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_prepare_yeji_analysis](          
	@compid				varchar(10),	-- 公司别          
	@fromdate			varchar(10),	-- 开始日期          
	@todate				varchar(10)		-- 截至日期
)     
as          
begin          
	CREATE tAbLE	#msalecardinfo               -- 会员卡销售单
	(
		salecompid			varchar(10)			Not NULL,   --公司编号
		salebillid			varchar(20)			Not NULL,   --销售单号
		saledate			varchar(8)				NULL,   --销售日期
		salecardno			varchar(20)				NULL,   --销售卡号
		salecardtype		varchar(20)				NULL,   --销售卡类型
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		salekeepamt			float					NULL,	--储值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)     
    insert #msalecardinfo(salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt)
	select salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt
    from msalecardinfo, dpayinfo
    where salebillid=paybillid and salecompid=paycompid and paybilltype='SK'
      and salecompid=@compid and financedate between @fromdate and @todate and ISNULL(salekeepamt,0)<>0  and ISNULL(salebakflag,0)=0
                              
         
	--卖卡第一销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,firstsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a ,cardtypenameinfo c     
	where  isnull(firstsaleamt,0)<>0     and ISNULL(firstsalerinid,'')<>''          
    and salecardtype=cardtypeno
    
    --卖卡第二销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,secondsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a,cardtypenameinfo c         
	where isnull(secondsaleamt,0)<>0     and ISNULL(secondsalerinid,'')<>''          
    and salecardtype=cardtypeno     
    --卖卡第三销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,thirdsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a,cardtypenameinfo c          
	where isnull(thirdsaleamt,0)<>0 and ISNULL(thirdsalerinid,'')<>'' 
    and salecardtype=cardtypeno 
    --卖卡第四销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fourthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,fourthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a ,cardtypenameinfo c        
	where isnull(fourthsaleamt,0)<>0  and ISNULL(fourthsalerinid,'')<>'' 
    and salecardtype=cardtypeno 
    --卖卡第五销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fifthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,fifthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a,cardtypenameinfo c         
	where  isnull(fifthsaleamt,0)<>0  and ISNULL(fifthsalerinid,'')<>'' 
    and salecardtype=cardtypeno 
    --卖卡第六销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,sixthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,sixthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a,cardtypenameinfo c         
	where  isnull(sixthsaleamt,0)<>0  and ISNULL(sixthsalerinid,'')<>'' 
    and salecardtype=cardtypeno 
    --卖卡第七销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,seventhsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,seventhsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a ,cardtypenameinfo c        
	where  isnull(seventhsaleamt,0)<>0 and ISNULL(seventhsalerinid,'')<>'' 
    and salecardtype=cardtypeno  
    --卖卡第八销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,eighthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,eighthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a ,cardtypenameinfo c        
	where  isnull(eighthsaleamt,0)<>0 and ISNULL(eighthsalerinid,'')<>'' 
    and salecardtype=cardtypeno 
    --卖卡第九销售人员  ,cardtypenameinfo c           
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,ninthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,ninthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a ,cardtypenameinfo c        
	where isnull(ninthsaleamt,0)<>0 and ISNULL(ninthsalerinid,'')<>'' 
    and salecardtype=cardtypeno 
    --卖卡第十销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,tenthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,tenthsaleamt*payamt/isnull(salekeepamt,1)          
	from #msalecardinfo a  ,cardtypenameinfo c       
	where isnull(tenthsaleamt,0)<>0 and ISNULL(tenthsalerinid,'')<>'' 
    and salecardtype=cardtypeno
    CREATE tAbLE	#mcardrechargeinfo              -- 会员卡充值单
	(
		rechargecompid			varchar(10)			Not NULL,   --充值门店
		rechargebillid			varchar(20)			Not NULL,   --充值单号 
		rechargedate			varchar(8)				NULL,   --充值日期 
		rechargetime			varchar(6)				NULL,   --充值时间 
		rechargecardno			varchar(20)				NULL,   --会员卡号
		rechargecardtype		varchar(10)				NULL,   --卡类型
		rechargetype			int						NULL,   --续费方式( 0充值 ,6还款 ,)
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		rechargekeepamt		float					NULL,   --充值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)     
	
	insert #mcardrechargeinfo(rechargecompid,rechargebillid,rechargedate,rechargetype,rechargecardno,rechargecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt)
	select rechargecompid,rechargebillid,rechargedate,rechargetype,rechargecardno,rechargecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt
    from mcardrechargeinfo, dpayinfo
    where rechargebillid=paybillid and rechargecompid=paycompid and paybilltype='CZ'
      and rechargecompid=@compid and financedate between @fromdate and @todate and ISNULL(rechargekeepamt,0)<>0  and ISNULL(salebakflag,0)=0 and isnull(backbillid,'')=''
	
		
	update #mcardrechargeinfo set rechargecardtype=cardtype
	from #mcardrechargeinfo,cardinfo with(nolock)
	where rechargecardno=cardno and ISNULL(rechargetype,0)=1
		--卖卡第一销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,firstsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where isnull(firstsaleamt,0)<>0    and ISNULL(firstsalerinid,'')<>''       
    and rechargecardtype=cardtypeno
    --卖卡第二销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,secondsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where isnull(secondsaleamt,0)<>0   and ISNULL(secondsalerinid,'')<>''          
     and rechargecardtype=cardtypeno    
    --卖卡第三销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,thirdsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a   ,cardtypenameinfo c        
	where  isnull(thirdsaleamt,0)<>0 and ISNULL(thirdsalerinid,'')<>''
     and rechargecardtype=cardtypeno
    --卖卡第四销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fourthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,fourthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a    ,cardtypenameinfo c       
	where  isnull(fourthsaleamt,0)<>0 and ISNULL(fourthsalerinid,'')<>''
     and rechargecardtype=cardtypeno
    --卖卡第五销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fifthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,fifthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where  isnull(fifthsaleamt,0)<>0 and ISNULL(fifthsalerinid,'')<>''
     and rechargecardtype=cardtypeno
    --卖卡第六销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,sixthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,sixthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a ,cardtypenameinfo c       
	where isnull(sixthsaleamt,0)<>0 and ISNULL(sixthsalerinid,'')<>''
    and rechargecardtype=cardtypeno 
    --卖卡第七销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,seventhsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,seventhsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a ,cardtypenameinfo c          
	where isnull(seventhsaleamt,0)<>0 and ISNULL(seventhsalerinid,'')<>''
     and rechargecardtype=cardtypeno
    --卖卡第八销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,eighthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,eighthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where isnull(eighthsaleamt,0)<>0 and ISNULL(eighthsalerinid,'')<>''
     and rechargecardtype=cardtypeno
    --卖卡第九销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,ninthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,ninthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where  isnull(ninthsaleamt,0)<>0  and ISNULL(ninthsalerinid,'')<>''
     and rechargecardtype=cardtypeno
    --卖卡第十销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,tenthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,tenthsaleamt*payamt/isnull(rechargekeepamt,1)          
	from #mcardrechargeinfo a  ,cardtypenameinfo c         
	where  isnull(tenthsaleamt,0)<>0  and ISNULL(tenthsaleamt,'')<>''
    and rechargecardtype=cardtypeno
     
    CREATE tAbLE	#mcardchangeinfo             -- 会员卡异动
	(
		changecompid		varchar(10)			Not NULL,   --充值门店
		changebillid		varchar(20)			Not NULL,   --充值单号 
		changetype			int					Not NULL,
		changedate			varchar(8)				NULL,   --充值日期 
		changetime			varchar(6)				NULL,   --充值时间 
		changeaftercardno	varchar(20)				NULL,   --会员卡号
		changeaftercardtype	varchar(10)				NULL,   --卡类型
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		changefillamt		float					NULL,   --充值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)   
	
	insert #mcardchangeinfo(changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt)
	select changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt
    from mcardchangeinfo, dpayinfo
    where changebillid=paybillid and changecompid=paycompid and paybilltype in ('ZK','TK')
      and changecompid=@compid and financedate between @fromdate and @todate and ISNULL(changefillamt,0)<>0  and ISNULL(salebakflag,0)=0
      
  
      
     	--卖卡第一销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end ,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*firstsaleamt*payamt/isnull(changefillamt,1) else   firstsaleamt*payamt/isnull(changefillamt,1) end         
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where isnull(firstsaleamt,0)<>0   and ISNULL(firstsalerinid,'')<>'' 

    --卖卡第二销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*secondsaleamt*payamt/isnull(changefillamt,1) else   secondsaleamt*payamt/isnull(changefillamt,1) end      
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where isnull(secondsaleamt,0)<>0    and ISNULL(secondsalerinid,'')<>''            

    --卖卡第三销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*thirdsaleamt*payamt/isnull(changefillamt,1) else   thirdsaleamt*payamt/isnull(changefillamt,1) end   
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where isnull(thirdsaleamt,0)<>0  and ISNULL(thirdsalerinid,'')<>''            

    --卖卡第四销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fourthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*fourthsaleamt*payamt/isnull(changefillamt,1) else   fourthsaleamt*payamt/isnull(changefillamt,1) end       
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where isnull(fourthsaleamt,0)<>0  and ISNULL(fourthsalerinid,'')<>''            

    --卖卡第五销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fifthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*fifthsaleamt*payamt/isnull(changefillamt,1) else   fifthsaleamt*payamt/isnull(changefillamt,1) end   
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where isnull(fifthsaleamt,0)<>0  and ISNULL(fifthsalerinid,'')<>''            

    --卖卡第六销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,sixthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*sixthsaleamt*payamt/isnull(changefillamt,1) else   sixthsaleamt*payamt/isnull(changefillamt,1) end        
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where isnull(sixthsaleamt,0)<>0 and ISNULL(sixthsalerinid,'')<>''            
   
    --卖卡第七销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,seventhsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*seventhsaleamt*payamt/isnull(changefillamt,1) else   seventhsaleamt*payamt/isnull(changefillamt,1) end          
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where  isnull(seventhsaleamt,0)<>0 and ISNULL(seventhsalerinid,'')<>''    
   
    --卖卡第八销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,eighthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*eighthsaleamt*payamt/isnull(changefillamt,1) else   eighthsaleamt*payamt/isnull(changefillamt,1) end   
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where isnull(eighthsaleamt,0)<>0  and ISNULL(eighthsalerinid,'')<>''  
   
    --卖卡第九销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,ninthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*ninthsaleamt*payamt/isnull(changefillamt,1) else  ninthsaleamt*payamt/isnull(changefillamt,1) end   
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where isnull(ninthsaleamt,0)<>0 and ISNULL(ninthsalerinid,'')<>''  
   
    --卖卡第十销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,tenthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*tenthsaleamt*payamt/isnull(changefillamt,1) else   tenthsaleamt*payamt/isnull(changefillamt,1) end   
	from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno 
	where isnull(tenthsaleamt,0)<>0  and ISNULL(tenthsalerinid,'')<>''  
   
    
    CREATE tAbLE	#mproexchangeinfo               -- 会员卡疗程兑换
	(
		changecompid			varchar(10)			Not NULL,   --公司编号
		changebillid			varchar(20)			Not NULL,   --销售单号
		changedate				varchar(8)				NULL,   --销售日期
		changecardno			varchar(20)				NULL,   --销售卡号
		changecardtype			varchar(20)				NULL,   --销售卡类型
		firstsalerid			varchar(20)				NULL,			--第一销售工号
		firstsalerinid			varchar(20)				NULL,			--第一销售内部编号
		firstsaleamt			float					NULL,			--第一销售分享金额
		secondsalerid			varchar(20)				NULL,			--第二销售工号
		secondsalerinid			varchar(20)				NULL,			--第二销售内部编号
		secondsaleamt			float					NULL,			--第二销售分享金额
		thirdsalerid			varchar(20)				NULL,			--第三销售工号----- 烫染师
		thirdsalerinid			varchar(20)				NULL,			--第三销售内部编号
		thirdsaleamt			float					NULL,			--第三销售分享金额
		fourthsalerid			varchar(20)				NULL,			--第四销售工号----- 烫染师
		fourthsalerinid			varchar(20)				NULL,			--第四销售内部编号
		fourthsaleamt			float					NULL,			--第四销售分享金额
		financedate				varchar(8)				NULL,   --帐务日期 
		changeproamt			float					NULL,	--储值金额
		paymode					varchar(10)				NULL,	--支付方式
		payamt					float					NULL,	--支付金额
	)  
	insert #mproexchangeinfo(changecompid,changebillid,changedate,changecardno,changecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changeproamt,paymode,payamt)   
    select a.changecompid,a.changebillid,changedate,changecardno,changecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changeproamt,changepaymode,changebycashamt 
        from mproexchangeinfo a,dproexchangeinfo b
    where a.changecompid=b.changecompid and a.changebillid=b.changebillid
    and  financedate between @fromdate and @todate and ISNULL(changeproamt,0)<>0 and a.changecompid=@compid  and ISNULL(salebakflag,0)=0
    
      	--卖卡第一销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,firstsaleamt        
	from #mproexchangeinfo a      
	where isnull(firstsaleamt,0)<>0   and ISNULL(firstsalerinid,'')<>''   
    
    --卖卡第二销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,secondsaleamt
	from #mproexchangeinfo a      
	where  isnull(secondsaleamt,0)<>0  and ISNULL(secondsalerinid,'')<>''              
        
    --卖卡第三销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,thirdsaleamt
	from #mproexchangeinfo a      
	where isnull(thirdsaleamt,0)<>0 and ISNULL(thirdsalerinid,'')<>''             
    
    --卖卡第四销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fourthsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,fourthsaleamt
	from #mproexchangeinfo a      
	where isnull(fourthsaleamt,0)<>0 and ISNULL(fourthsalerinid,'')<>''             
    
    CREATE tAbLE	#msalebarcodecardinfo             -- 系统条码卡销售主档
	(
		salecompid			varchar(10)		not null	,--销售门店
		salebillid			varchar(20)		not null	,--销售单号
		saledate			varchar(8)			null	,--销售日期
		barcodecardno		varchar(20)			null	,--销售条码卡卡号
		firstpaymode		varchar(20)			null	,--第一支付方式
		firstpayamt			float				null	,--第一支付金额
		secondpaymode		varchar(20)			null	,--第二支付方式
		secondpayamt		varchar(20)			null	,--第二支付金额
		saleamt				float				null	,--销售总额
		firstsaleempid		varchar(20)			null	,--第一销售工号
		firstsaleempinid	varchar(20)			null	,--第一销售内部工号
		firstsaleamt		float				null	,--第一销售分享金额
		secondsaleempid		varchar(20)			null	,--第二销售工号
		secondsaleempinid	varchar(20)			null	,--第二销售内部工号
		secondsaleamt		float				null	,--第二销售分享金额
		thirdsaleempid		varchar(20)			null	,--第三销售工号
		thirdsaleempinid	varchar(20)			null	,--第三销售内部工号
		thirdsaleamt		float				null	,--第三销售分享金额
	)  
	
	insert #msalebarcodecardinfo(salecompid,salebillid,saledate,barcodecardno,firstpaymode,firstpayamt,secondpaymode,secondpayamt,saleamt,
	firstsaleempid,firstsaleempinid,firstsaleamt,secondsaleempid,secondsaleempinid,secondsaleamt,thirdsaleempid,thirdsaleempinid,thirdsaleamt)
    select salecompid,salebillid,saledate,barcodecardno,firstpaymode,firstpayamt,secondpaymode,secondpayamt,saleamt,
	firstsaleempid,firstsaleempinid,firstsaleamt,secondsaleempid,secondsaleempinid,secondsaleamt,thirdsaleempid,thirdsaleempinid,thirdsaleamt
	from msalebarcodecardinfo
    where salecompid=@compid and saledate between @fromdate and @todate and ISNULL(saleamt,0)<>0 
    
      	--卖卡第一销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsaleempinid,5,saledate,barcodecardno,'条码卡',saleamt,1,salebillid,firstpaymode,firstsaleamt        
	from #msalebarcodecardinfo a      
	where isnull(firstsaleamt,0)<>0   and ISNULL(firstsaleempinid,'')<>''         
    
    --卖卡第二销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsaleempinid,5,saledate,barcodecardno,'条码卡',saleamt,1,salebillid,firstpaymode,secondsaleamt
	from #msalebarcodecardinfo a      
	where isnull(secondsaleamt,0)<>0   and ISNULL(secondsaleempinid,'')<>''                    
        
    --卖卡第三销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsaleempinid,5,saledate,barcodecardno,'条码卡',saleamt,1,salebillid,firstpaymode,thirdsaleamt
	from #msalebarcodecardinfo a      
	where  isnull(thirdsaleamt,0)<>0  and ISNULL(thirdsaleempinid,'')<>''          
    
    --合作项目
	CREATE tAbLE    #mcooperatesaleinfo             
	(
		salecompid				char(10)				Not NULL,   --公司编号
		salebillid				varchar(20)				Not NULL,   --异动单号
		saledate				varchar(8)				NULL    ,   --异动日期
		salecooperid			varchar(30)				NULL    ,   --合作单位
		slaepaymode				varchar(5)				NULL    ,   --支付方向 1 店内支付，2 合作单位支付
		salecostcardno			varchar(20)				NULL    ,   --会员卡号
		salecostcardtype		varchar(20)				NULL    ,   --会员卡类型
		salecostproamt			float					NULL    ,   --项目金额
		firstsalerid			varchar(20)				NULL,   --第一销售工号
		firstsalerinid			varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt			float					NULL,   --第一销售分享金额
		secondsalerid			varchar(20)				NULL,   --第二销售工号
		secondsalerinid			varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt			float					NULL,   --第二销售分享金额
		thirdsalerid			varchar(20)				NULL,   --第三销售工号
		thirdsalerinid			varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt			float					NULL,   --第三销售分享金额
		fourthsalerid			varchar(20)				NULL,   --第四销售工号
		fourthsalerinid			varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt			float					NULL,   --第四销售分享金额
		fifthsalerid			varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid			varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt			float					NULL,   --第五销售分享金额
		sixthsalerid			varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid			varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt			float					NULL,   --第六销售分享金额
		seventhsalerid			varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid		varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt			float					NULL,   --第七销售分享金额
		eighthsalerid			varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid			varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt			float					NULL,   --第八销售分享金额
		financedate				varchar(8)				NULL,   --帐务日期 
		paymode					varchar(10)				NULL,	--支付方式
		payamt					float					NULL,	--支付金额
	)
	
	insert #mcooperatesaleinfo(salecompid,salebillid,saledate,salecooperid,slaepaymode,salecostcardno,salecostcardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     financedate,salecostproamt,paymode,payamt)
	select salecompid,salebillid,saledate,salecooperid,slaepaymode,salecostcardno,salecostcardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     financedate,salecostproamt,paymode,payamt
    from mcooperatesaleinfo, dpayinfo
    where salebillid=paybillid and salecompid=paycompid and paybilltype='HZ'
      and salecompid=@compid and financedate between @fromdate and @todate and ISNULL(salecostproamt,0)<>0
     -- and ISNULL(slaepaymode,0)=1 
      and ISNULL(salebillflag,0)=2
      
      	--卖卡第一销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,firstsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,firstsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where isnull(firstsaleamt,0)<>0   
    
    --卖卡第二销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,secondsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,secondsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where isnull(secondsaleamt,0)<>0            
        
    --卖卡第三销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,thirdsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,thirdsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where  isnull(thirdsaleamt,0)<>0
    
    --卖卡第四销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fourthsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,fourthsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where  isnull(fourthsaleamt,0)<>0
    
    --卖卡第五销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,fifthsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,fifthsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where isnull(fifthsaleamt,0)<>0
    
    --卖卡第六销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,sixthsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,sixthsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where isnull(sixthsaleamt,0)<>0
    
    --卖卡第七销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,seventhsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,seventhsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where isnull(seventhsaleamt,0)<>0
    
    --卖卡第八销售人员          
	insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)          
	select @compid,eighthsalerinid,case	when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27    
										when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then 28    
										when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 29    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30    
										when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,eighthsaleamt*payamt/isnull(salecostproamt,1)          
	from #mcooperatesaleinfo a      
	where isnull(eighthsaleamt,0)<>0
    
    create table #mconsumeinfo
    (
		cscompid		varchar(10)     Not NULL,   --公司编号
		csbillid		varchar(20)		Not NULL,   --消费单号
		cscardno		varchar(20)		NULL    ,   --会员卡号
		cscardtype		varchar(10)		NULL    ,   --卡类型
		diyongcardno	varchar(20)		NULL    ,   --抵用券号
		tiaomacardno	varchar(20)		NULL    ,   --条码卡号
		financedate		varchar(8)		NULL    ,   --帐务日期 
		csinfotype		int				Not	NULL,	--消费类型  1 项目  2 产品
		csitemno		varchar(20)     NULL,		--项目/产品代码
		csitemcount		float           NULL,		--数量
		csitemamt		float           NULL,		--金额
		cspaymode		varchar(5)		NULL,		--支付方式
		csfirstsaler	varchar(20)     NULL,		--大工工号
		csfirsttype		varchar(5)     NULL,		--大工类型
		csfirstinid		varchar(20)		NULL,		--大工内部编号
		csfirstshare	float           NULL,		--大工分享
		cssecondsaler	varchar(20)     NULL,		--中工工号
		cssecondtype	varchar(5)     NULL,		--中工类型
		cssecondinid	varchar(20)		NULL,		--中工内部编号
		cssecondshare	float           NULL,		--中工分享
		csthirdsaler	varchar(20)     NULL,		--小工工号
		csthirdtype		varchar(5)		NULL,		--小工类型
		csthirdinid		varchar(20)		NULL,		--小工内部编号
		csthirdshare	float           NULL,		--小工分享
    )
    

    insert #mconsumeinfo(cscompid,csbillid,cscardno,cscardtype,financedate,csinfotype,csitemno,csitemcount,csitemamt,cspaymode,
    csfirstsaler,csfirsttype,csfirstinid,csfirstshare,cssecondsaler,cssecondtype,cssecondinid,cssecondshare,csthirdsaler,csthirdtype,csthirdinid,csthirdshare,diyongcardno,tiaomacardno)
    select a.cscompid,a.csbillid,cscardno,cscardtype,financedate,csinfotype,csitemno,csitemcount,csitemamt,cspaymode,
    csfirstsaler,csfirsttype,csfirstinid,csfirstshare,cssecondsaler,cssecondtype,cssecondinid,cssecondshare,csthirdsaler,csthirdtype,csthirdinid,csthirdshare,diyongcardno,tiaomacardno
     from  mconsumeinfo a,dconsumeinfo b
    where a.cscompid=b.cscompid and a.csbillid=b.csbillid and a.cscompid=@compid and financedate between @fromdate and @todate and ISNULL(csitemamt,0)<>0 
	
	-------------------------这句话非常重要, 美容大项如果多人分享要除相应比重---------------------------
	update #mconsumeinfo set csitemamt=convert(numeric(20,2),ISNULL(csitemamt,0)/(case when isnull(csfirstsaler,'')<>''   then 1 else 0 end +        
        case when isnull(cssecondsaler,'')<>'' then 1 else 0 end +        
        case when isnull(csthirdsaler,'')<>'' then 1 else 0 end ))        
    from #mconsumeinfo,projectnameinfo with(nolock)        
    where  csitemno=prjno        
	and prjtype='4' and prjpricetype=1        
	and (isnull(cssecondsaler,'')<>''  or  isnull(csthirdsaler,'')<>'' or isnull(csfirstsaler,'')<>'' )   
  
  
     -- 项目大工 7，8，9 (要记录项目抵用券号)
	 insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,csfirstinid,isnull(convert(int,isnull(csfirsttype,'2')),1)+6,financedate,csitemno,prjname,csitemamt,csitemcount,csbillid,
      cspaymode,csitemamt,cscardno,	  case when cspaymode  in ('12','11') then diyongcardno when cspaymode='13' then tiaomacardno  else cscardtype end  
	  from #mconsumeinfo b, projectnameinfo c         
      where  b.csitemno = c.prjno and csinfotype=1
      and isnull(csitemamt,0)<>0      and ISNULL(csfirstsaler,'')<>''
      
     -- 项目中工 10，11，12
	 insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,cssecondinid,isnull(convert(int,isnull(cssecondtype,'2')),1)+9,financedate,csitemno,prjname,csitemamt,csitemcount,csbillid,
      cspaymode,csitemamt,cscardno,	  case when cspaymode  in ('12','11') then diyongcardno when cspaymode='13' then tiaomacardno   else cscardtype end  
	  from #mconsumeinfo b, projectnameinfo c         
      where  b.csitemno = c.prjno and csinfotype=1
       and isnull(csitemamt,0)<>0  and ISNULL(cssecondsaler,'')<>''
      
      
      -- 项目小工 13，14，15
	 insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,csthirdinid,isnull(convert(int,isnull(csthirdtype,'2')),1)+12,financedate,csitemno,prjname,csitemamt,csitemcount,csbillid,
      cspaymode,csitemamt,cscardno,	  case when cspaymode  in ('12','11') then diyongcardno when cspaymode='13' then tiaomacardno   else cscardtype end  
	  from #mconsumeinfo b, projectnameinfo c         
      where  b.csitemno = c.prjno and csinfotype=1
      and isnull(csitemamt,0)<>0 and ISNULL(csthirdsaler,'')<>''
      
       -- 产品大工 16，17，18
	 insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,csfirstinid,isnull(convert(int,isnull(csfirsttype,'2')),1)+15,financedate,csitemno,goodsname,csitemamt,csitemcount,csbillid,cspaymode,csfirstshare,cscardno,cscardtype
	  from #mconsumeinfo b, goodsnameinfo c         
      where  b.csitemno = c.goodsno and csinfotype=2
       and isnull(csitemamt,0)<>0      and ISNULL(csfirstsaler,'')<>''
      
     -- 产品中工 19，20，21
	 insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,cssecondinid,isnull(convert(int,isnull(cssecondtype,'2')),1)+18,financedate,csitemno,goodsname,csitemamt,csitemcount,csbillid,cspaymode,cssecondshare,cscardno,cscardtype
	  from #mconsumeinfo b, goodsnameinfo c         
      where  b.csitemno = c.goodsno and csinfotype=2
      and isnull(csitemamt,0)<>0  and ISNULL(cssecondsaler,'')<>''
      
      
      -- 产品小工 22，23，24
	 insert #allstaff_work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)          
	 select cscompid,csthirdinid,isnull(convert(int,isnull(csthirdtype,'2')),1)+21,financedate,csitemno,goodsname,csitemamt,csitemcount,csbillid,cspaymode,csthirdshare,cscardno,cscardtype
	  from #mconsumeinfo b, goodsnameinfo c         
      where  b.csitemno = c.goodsno and csinfotype=2
      and isnull(csitemamt,0)<>0  and ISNULL(csthirdsaler,'')<>''
      
                
    drop table #msalecardinfo    
    drop table #msalebarcodecardinfo
    drop table #mcardrechargeinfo 
    drop table #mcardchangeinfo   
    drop table #mproexchangeinfo 
    drop table #mcooperatesaleinfo 
    drop table #mconsumeinfo
  
            
end
GO
/****** Object:  StoredProcedure [dbo].[upg_prepare_ticheng_analysis]    Script Date: 01/14/2014 15:45:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_prepare_ticheng_analysis](          
	@compid				varchar(10),	-- 公司别          
	@fromdate			varchar(10),	-- 开始日期          
	@todate				varchar(10)		-- 截至日期
) 
as
begin
		create table #staff_work_salary
		(
			strCompId				varchar(10)		null,						--门店编号
			staffno					varchar(20)		null,						--员工编号
			staffinid				varchar(20)		null,						--员工内部编号
			staffname				varchar(30)		null,						--员工名称
			staffposition			varchar(10)		null,						--员工职位
			staffpcid				varchar(20)		null,						--员工身份证
			staffmark				varchar(300)	null,						--员工备注
			staffbankaccountno		varchar(30)		null,						--员工银行账号
			resulttye				varchar(5)		NULL,						--业绩方式 0--额度方式 -美发虚业绩  2 美发实业绩
			resultrate				float			NULL,						--业绩系数
			baseresult				float			NULL,						--业绩基数
			computedays				int				NULL,						--计算天数
			workdays				int				NULL,						--工作天数
			stafftotalyeji			float			null,						--员工总业绩
			staffshopyeji			float			null,						--员工门店业绩
			salaryflag				int				null,						--税前 ,税后 
			staffbasesalary			float			null,						--员工基本工资
			beatysubsidy			float			null,						--美容补贴
			leaveldebit				float			null,						--离职扣款
			staffsubsidy			float			null,						--补贴
			staffdebit				float			null,						--扣款
			latdebit				float			null,						--吃迟到
			staffreward				float			null,						--奖励
			otherdebit				float			null,						--其他扣款
			staffsocials			float			null,						--社保
			needpaysalary			float			null,						--应付工资
			staydebit				float			null,						--住宿
			studydebit				float			null,						--学习费用
			staffcost				float			null,						--成本
			salarydebit				float			null,						--扣税
			factpaysalary			float			null,						--应付工资
			staffpositionname		varchar(30)		null,						--员工职位
		) 
	
		 
		insert #staff_work_salary(strCompId,staffinid,stafftotalyeji,workdays,computedays)
		select compid,person_inid,SUM(ISNULL(staffyeji,0)) ,COUNT(distinct salary_date),datediff(day,@fromdate,@todate)+1
		from staff_work_salary 
		where compid=@compid and salary_date between @fromdate and @todate group by  compid,person_inid
		
		insert #staff_work_salary(strCompId,staffinid,stafftotalyeji,workdays,computedays)
		select @compid,manageno,0,0,datediff(day,@fromdate,@todate)+1
		from staffhistory where effectivedate between @fromdate and @todate and oldcompid=@compid
		and manageno not in ( select person_inid from staff_work_salary 
		where compid=@compid and salary_date between @fromdate and @todate)
		group by manageno
		--添加没有业绩的人员
		insert #staff_work_salary(strCompId,staffinid,stafftotalyeji)
		select compno,Manageno,0 from staffinfo where Manageno not in (select staffinid from #staff_work_salary) and compno=@compid
		group by compno,Manageno
		
		insert #staff_work_salary(strCompId,staffinid,stafftotalyeji)
		select oldcompid,Manageno,0 from staffhistory where Manageno not in (select staffinid from #staff_work_salary) and oldcompid=@compid and effectivedate>@todate
		group by  oldcompid,Manageno
		
		
		update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position,a.staffpcid=b.pccid,a.staffbankaccountno=b.bankno,
		--a.staffbasesalary=isnull(b.basesalary,0)*ISNULL(workdays,0)/ISNULL(computedays,1) ,
		a.staffbasesalary=isnull(b.basesalary,0) ,
					 a.resulttye=b.resulttye,a.resultrate=b.resultrate,a.baseresult=b.baseresult,
					 a.salaryflag=b.salaryflag,a.staffsocials=b.socialsecurity,a.staffmark=b.remark
		from #staff_work_salary a,staffinfo b 
		where a.staffinid=b.Manageno 
		
		 --计算门店业绩提成（管理层）                                                              
		declare @beaut_yeji		float                                                              
		declare @hair_yeji		float                          
		declare @trh_yeji		float      
		declare @trh_yejifact   float                                                      
		declare @total_yeji		float    
		select @beaut_yeji=sum(isnull(beautyeji,0)),@hair_yeji=sum(isnull(hairyeji,0)),@trh_yeji=sum(isnull(footyeji,0)),@total_yeji=sum(isnull(totalyeji,0)) ,@trh_yejifact=sum(isnull(realfootyeji,0))
		from compclasstraderesult  where compid=@compid and ddate between @fromdate and @todate
		
		--烫染总监 008 拿的是烫染业绩基数
		update   #staff_work_salary set staffshopyeji=(ISNULL(@trh_yeji,0) -isnull(baseresult,0))*0.02                    
		 where  staffposition='008' and ISNULL(@trh_yeji,0) >=isnull(baseresult,0)       
		 --烫染总监 00801 拿的是烫染业绩基数  
		 update   #staff_work_salary set staffshopyeji=ISNULL(@trh_yejifact,0)*0.02                    
		 where  staffposition='00801'   
		 update   #staff_work_salary set staffshopyeji=isnull(staffshopyeji,0)+2000            
		 where  staffposition='00801' and ISNULL(@trh_yeji,0) >=isnull(baseresult,0)                                                             
         --其他管理层来的是分享业绩
         update #staff_work_salary                                                               
			set staffshopyeji = case when isnull(resulttye,'')='1' then isnull(@hair_yeji,0)*isnull(resultrate,0)*ISNULL(workdays,0)/ISNULL(computedays,1)                                                              
			when isnull(resulttye,'')='2' then isnull(@beaut_yeji,0)*isnull(resultrate,0)*ISNULL(workdays,0)/ISNULL(computedays,1)   
			when isnull(resulttye,'')='3' then isnull(@trh_yeji,0)*isnull(resultrate,0)*ISNULL(workdays,0)/ISNULL(computedays,1)               
			when isnull(resulttye,'')='4' then isnull(@total_yeji,0)*isnull(resultrate,0)*ISNULL(workdays,0)/ISNULL(computedays,1)  else 0 end  
	    where   staffposition<>'008'   
        
        
        --美容师补贴                  
		--美容师入职超过一年每月补贴200, 两年 400 (七天没上班没有补贴)                  
		update a                                   
		set beatysubsidy=isnull((case when DATEDIFF ( day, arrivaldate ,ISNULL(@fromdate,'') ) between 364 and 727 then 200              
				when DATEDIFF ( day, arrivaldate ,ISNULL(@fromdate,'') )> 727 then 400                  
				 else 0 end),0)                  
		from #staff_work_salary a,staffinfo  b                  
		where a.staffinid=b.Manageno and b.position in ('004','00401','00402') and isnull(a.workdays,0)>23   and ISNULL(curstate,0)=2   
  
		--收银小单提成
		   declare @ccount int 
		 select @ccount=COUNT(distinct csbillid)-2000 from mconsumeinfo with(nolock)     
		where cscompid=@compid     
			and financedate between @fromdate and @todate   
			and ISNULL(backcsflag,0)=0    
			and ISNULL(backcsbillid,'')=''     
     
		 if(@ccount>0)    
		 begin    
		 update #staff_work_salary set staffshopyeji=isnull(staffshopyeji,0)+ISNULL(@ccount,0)*0.1 where staffposition in('01201','01202')    
		 end     
		 set @ccount=0
		  select @ccount=count(distinct a.csbillid)-300    
		 from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),projectnameinfo c    
		 where a.cscompid=b.cscompid    
		   and a.csbillid=b.csbillid
		   and csitemno=prjno    
		   and prjpricetype=1    
		   and prjtype='4'    
		   and a.cscompid=@compid    
		   and a.financedate between @fromdate and @todate    
		   	and ISNULL(backcsflag,0)=0    
			and ISNULL(backcsbillid,'')=''         
		    
		     
		 if(@ccount>0)    
		 begin    
		 update #staff_work_salary set staffshopyeji=isnull(staffshopyeji,0)+ISNULL(@ccount,0)*2 where staffposition='002'    
		 end 
   
		 --罚款                                 
            
		update #staff_work_salary                                   
		set staffdebit=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='02' ),0)                               
              
         --奖励                                
            
		update #staff_work_salary                                   
		set staffreward=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='03' ),0)                               
  
          
		--迟到早退                                 
            
		update #staff_work_salary                                   
		set latdebit=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='04' ),0)                               
  
		--其他扣款                                
            
		update #staff_work_salary                                   
		set otherdebit=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='05' ),0)                               
  
  
		--学习费                                 
            
		update #staff_work_salary                                   
		set studydebit=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='09' ),0)                               
                      
		--口成本                                 
            
		update #staff_work_salary                                   
		set staffcost=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='08' ),0)                               
                       
		----住宿                                
		update #staff_work_salary                                   
		set staffcost=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='07' ),0)                               
  
		--补贴                                
            
		update #staff_work_salary                                   
		set staffsubsidy=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='06' ),0)                               
  
	    ----离职异动单增加离职种类 正常离职,自动离职, 自动离职需扣款600                               
		update #staff_work_salary set leaveldebit=600  where staffinid in (select staffmangerno from staffchangeinfo where changetype=1 and leaveltype=2 and validatestartdate  between @fromdate and @todate )                
		update #staff_work_salary 
		    set needpaysalary=ISNULL(stafftotalyeji,0)+ ISNULL(staffshopyeji,0)+
							  ISNULL(staffbasesalary,0)+ISNULL(beatysubsidy,0)-
							  ISNULL(leaveldebit,0)+ISNULL(staffsubsidy,0)-
							  ISNULL(staffdebit,0)-ISNULL(latdebit,0)+
							  ISNULL(staffreward,0)-ISNULL(otherdebit,0)-
							  ISNULL(staffsocials,0),
				factpaysalary=ISNULL(stafftotalyeji,0)+ ISNULL(staffshopyeji,0)+
							  ISNULL(staffbasesalary,0)+ISNULL(beatysubsidy,0)-
							  ISNULL(leaveldebit,0)+ISNULL(staffsubsidy,0)-
							  ISNULL(staffdebit,0)-ISNULL(latdebit,0)+
							  ISNULL(staffreward,0)-ISNULL(otherdebit,0)-
							  ISNULL(staffsocials,0)
		 declare @total_salary float,@deduct_tax int,@empid_ex varchar(20),@inid_ey varchar(20)  ,@sebao float                                                            
		 declare cur_emp_salary cursor for                                                              
		 select staffinid,staffno,needpaysalary,salaryflag,staffsocials from #staff_work_salary                                                               
		 open cur_emp_salary                                                              
		 fetch from cur_emp_salary into @inid_ey,@empid_ex,@total_salary,@deduct_tax ,@sebao                                                             
		 while(@@fetch_status=0)                                                              
		 begin                  
		  if(ISNULL(@sebao,0)>0)                      
		  begin                                                          
		   declare @tmpTax float, @tmpIncome float                                                              
		   set @tmpTax=0                                                              
		   set @tmpIncome=0                                                              
		   if(isnull(@deduct_tax,0)=0)                                                              
		   begin                              
		   if(@total_salary>8000)                                                    
		   exec upg_caculate_personal_tax 3500,8000,@tmpTax output,@tmpIncome output                                                              
		   else                                                  
		   exec upg_caculate_personal_tax 3500,@total_salary,@tmpTax output,@tmpIncome output                          
		   update #staff_work_salary                                                              
			  set salarydebit = -1*isnull(@tmpTax,0),factpaysalary=isnull(needpaysalary,0)-isnull(@tmpTax,0)                                                                                 
			where staffinid = @inid_ey                                             
		                                           
		   end                                               
		  end                                 
		  fetch from cur_emp_salary into @inid_ey,@empid_ex,@total_salary,@deduct_tax,@sebao                                                              
		 end                                                              
		 close cur_emp_salary                                                              
		 deallocate cur_emp_salary  
		 
		 update #staff_work_salary set staffpositionname=parentcodevalue
		 from commoninfo,#staff_work_salary
		 where parentcodekey=staffposition and infotype='GZGW'
 
		update #staff_work_salary set factpaysalary=factpaysalary-ISNULL(staydebit,0)-ISNULL(studydebit,0)-ISNULL(staffcost,0)
        
		select strCompId,staffno,staffinid,staffname,staffposition,staffpositionname,staffpcid,staffbankaccountno,workdays,computedays,
		       stafftotalyeji,staffshopyeji,staffbasesalary,beatysubsidy,leaveldebit,staffsubsidy,staffdebit,latdebit,staffcost,
		       staffreward,otherdebit,staffsocials,needpaysalary,staydebit,studydebit,salarydebit,factpaysalary,staffmark
		from #staff_work_salary  where staffno not in (RTRIM(strCompId)+'300',RTRIM(strCompId)+'400',RTRIM(strCompId)+'500',RTRIM(strCompId)+'600') order by staffno
		drop table #staff_work_salary
end
GO
/****** Object:  StoredProcedure [dbo].[upg_handsalecardbill_card]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_handsalecardbill_card]
@compid			varchar(10),	--销售公司      
@billid			varchar(20),	--销售单号 
@cardtype		varchar(20)	--卡种
as      
begin 
	--更新内部工号
	update msalecardinfo set firstsalerinid=manageno from msalecardinfo,staffinfo	where salecompid=@compid and salebillid=@billid and firstsalerid=staffno and compno=@compid
	update msalecardinfo set secondsalerinid=manageno from msalecardinfo,staffinfo	where salecompid=@compid and salebillid=@billid and secondsalerid=staffno and compno=@compid
	update msalecardinfo set thirdsalerinid=manageno from msalecardinfo,staffinfo	where salecompid=@compid and salebillid=@billid and thirdsalerid=staffno and compno=@compid
	update msalecardinfo set fourthsalerinid=manageno from msalecardinfo,staffinfo	where salecompid=@compid and salebillid=@billid and fourthsalerid=staffno and compno=@compid
	update msalecardinfo set fifthsalerinid=manageno from msalecardinfo,staffinfo	where salecompid=@compid and salebillid=@billid and fifthsalerid=staffno and compno=@compid
	update msalecardinfo set sixthsalerinid=manageno from msalecardinfo,staffinfo	where salecompid=@compid and salebillid=@billid and sixthsalerid=staffno and compno=@compid
	update msalecardinfo set seventhsalerinid=manageno from msalecardinfo,staffinfo where salecompid=@compid and salebillid=@billid and seventhsalerid=staffno and compno=@compid
	update msalecardinfo set eighthsalerinid=manageno from msalecardinfo,staffinfo	where salecompid=@compid and salebillid=@billid and eighthsalerid=staffno and compno=@compid
	declare @salecardno			varchar(20)		--销售卡号
	declare @saledate			varchar(8)		--销售日期
	declare @saletime			varchar(9)		--销售时间
	declare @membername			varchar(30)		--会员姓名
	declare @memberphone		varchar(20)		--会员手机
	declare @membersex			int				--会员性别
	declare @memberpcid			varchar(30)		--会员生分证号
	declare @memberbirthday		varchar(8)		--会员生日
	declare @acc2keepamt		float			--储值金额
	declare @debtamt			float			--储值欠款(AMN不允许欠款)
	declare @acc2totalamt		float			--储值总额
	declare @acc4totalamt		float			--疗程总额
	declare @paykeeptotalamt	float			--支付总额
	declare @paydebttotalamt	float			--支付总欠款
	declare @corpscardno		varchar(10)		--团购号

	select @salecardno=salecardno,@saledate=saledate,@saletime=saletime,@membername=membername,@memberphone=memberphone,
	@membersex=membersex,@memberpcid=memberpcid,@memberbirthday=memberbirthday,@corpscardno=corpscardno 
	from msalecardinfo with(nolock) where salecompid=@compid and salebillid=@billid
	

	select @paykeeptotalamt=SUM(ISNULL(payamt,0)) from dpayinfo with(nolock)  where paycompid=@compid and paybillid=@billid and paybilltype='SK' and ISNULL(paymode,'')<>'5'
	select @paydebttotalamt=SUM(ISNULL(payamt,0)) from dpayinfo with(nolock)  where paycompid=@compid and paybillid=@billid and paybilltype='SK' and ISNULL(paymode,'')='5'
	select @acc4totalamt=sum(ISNULL(saleproamt,0)) from dsalecardproinfo with(nolock) where salecompid=@compid and salebillid=@billid and salebilltype=1
	set @acc2totalamt=ISNULL(@paykeeptotalamt,0)-ISNULL(@acc4totalamt,0)
	
	--更新会员卡号
		--获得该卡种的有效期限
		declare @carduselife float
		select @carduselife=carduselife from cardtypeinfo where cardtypeno=@cardtype and cardtypemodeid='SCM'
		declare @cutoffdate varchar(10)
		select @cutoffdate=convert(varchar(10),dateadd(day,@carduselife,cast(@saledate as datetime)),120)
		select @cutoffdate=substring(@cutoffdate,1,4)+substring(@cutoffdate,6,2)+substring(@cutoffdate,9,2)
		update cardinfo set salecarddate=@saledate,cutoffdate=@cutoffdate,cardstate=4,salebillno=@billid,cardsource=0 where cardno=@salecardno
	--新增账户金额
		if exists(select 1 from cardaccount where cardno=@salecardno and accounttype=2)
			update cardaccount set accountbalance=@acc2totalamt,accountdatefrom=@saledate,accountdateend=@cutoffdate where cardno=@salecardno and accounttype=2
		else
		begin
			insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)
			values(@compid,@salecardno,2,@acc2totalamt,0,@saledate,@cutoffdate,'')
		end
		if exists(select 1 from cardaccount where cardno=@salecardno and accounttype=4)
			update cardaccount set accountbalance=@acc4totalamt,accountdatefrom=@saledate,accountdateend=@cutoffdate where cardno=@salecardno and accounttype=4
		else
		begin
			insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)
			values(@compid,@salecardno,4,@acc4totalamt,0,@saledate,@cutoffdate,'')
		end
	--新增会员基本资料
		if exists(select 1 from memberinfo where cardnotomemberno=@salecardno )
			update memberinfo set membervesting=@compid,memberno=@salecardno,membercreatedate=@saledate,
			 membername=@membername,membertphone=@memberphone,membersex=@membersex,memberpaperworkno=@memberpcid,
			 memberbirthday=@memberbirthday where cardnotomemberno=@salecardno
		else
		begin
			insert memberinfo(membervesting,memberno,membercreatedate,membername,membermphone,membersex,memberpaperworkno,memberbirthday,cardnotomemberno)
			values(@compid,@salecardno,@saledate,@membername,@memberphone,@membersex,@memberpcid,@memberbirthday,@salecardno)
		end
	--新增账户历史
		declare @costaccountseqno		float
		declare @costaccount2lastamt	float		--储值账户前次余额
		declare @costaccount4lastamt	float		--疗程账户前次余额

		select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@salecardno 
		if(ISNULL(@acc2totalamt,0)>0) --储值账户
		begin
				--生成账户历史
				select top 1 @costaccount2lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@salecardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc 
				insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
				values(@compid,@salecardno,2,isnull(@costaccountseqno,0),0,@acc2totalamt,'SK',@billid,@saledate,@costaccount2lastamt)
				set @costaccountseqno=@costaccountseqno+1
		end
	
		if(ISNULL(@acc4totalamt,0)>0) --疗程账户
		begin	
				--生成账户历史
				select top 1 @costaccount4lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@salecardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc 
				insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
				values(@compid,@salecardno,4,isnull(@costaccountseqno,0),0,@acc4totalamt,'SK',@billid,@saledate,@costaccount4lastamt)
				set @costaccountseqno=@costaccountseqno+1
				
		end
	--新增异动历史
		declare @saleaccountseqno		float
		select @saleaccountseqno=MAX(changeseqno)+1 from cardchangehistory where changecardno=@salecardno 
		insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
		values(@compid,@salecardno,isnull(@saleaccountseqno,0),2,@billid,0,4,@saledate,'')
	
	--生成疗程信息
		declare @proseqno float
		select @proseqno=MAX(proseqno)+1 from cardproaccount with(nolock) where cardno=@salecardno 
		insert cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,saledate,cutoffdate,proremark,createbilltype,createbillno)
		select @compid,@salecardno,saleproid,ISNULL(@proseqno,0)+ISNULL(seleproseqno,0),saleprotype,ISNULL(saleprocount,0)+ISNULL(sendprocount,0),0,ISNULL(saleprocount,0)+ISNULL(sendprocount,0),
		ISNULL(saleproamt,0),0,ISNULL(saleproamt,0),@saledate,procutoffdate,saleproremark,'SK', @billid
		from dsalecardproinfo where salecompid=@compid and salebillid=@billid and salebilltype=1
		
	--处理团购信息
	if(ISNULL(@corpscardno,'')<>'')
	begin
		update corpsbuyinfo set corpssate=2,useincompid=@compid,useinbillno=@billid,useindate =@saledate where corpscardno=@corpscardno
	end		
end
GO
/****** Object:  StoredProcedure [dbo].[upg_handrechargecardbill_card_back]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_handrechargecardbill_card_back]  
@compid   varchar(10), --续卡公司        
@billid   varchar(20) --续卡单号  
as        
begin   
	declare @salecardno    varchar(20)  --续卡卡号  
	declare @saledate    varchar(8)  --续卡日期  
	declare @saletime    varchar(9)  --续卡时间  
   
	declare @rechargetype   int    --续费方式( 0充值 ,6还款)    
	declare @rechargekeepamt  float   --充值金额  
	declare @rechargedebtamt  float   --欠款金额  
	declare @curcardamt    float   --异动前余额  
	declare @curcarddebtamt   float   --异动前欠款  
	declare @rechargeaccounttype varchar(10)  --充值账户  
	declare @acc4totalamt  float   --疗程总额  
	declare @carduselife float  
	 
   
	select @salecardno=rechargecardno,@saledate=rechargedate,@saletime=rechargetime,@rechargetype=rechargetype,  
			@rechargekeepamt=rechargekeepamt,@rechargedebtamt=rechargedebtamt,@curcardamt=curcardamt,@curcarddebtamt=curcarddebtamt,@rechargeaccounttype=rechargeaccounttype  
	from mcardrechargeinfo with(nolock) where rechargecompid=@compid and rechargebillid=@billid  
   
	select @acc4totalamt=sum(ISNULL(saleproamt,0)) from dsalecardproinfo with(nolock) where salecompid=@compid and salebillid=@billid and salebilltype=2  
   
 --更新账户金额  
 if(@rechargetype=0)--充值  
 begin  
	  update cardaccount set accountbalance=isnull(accountbalance,0)+ISNULL(@rechargekeepamt,0)*(-1),accountdebts=isnull(accountdebts,0)+ISNULL(@rechargedebtamt,0)*(-1) where cardno=@salecardno and accounttype=CAST(@rechargeaccounttype as int)  
 end  
 else --还款  
 begin  
   update cardaccount set accountbalance=isnull(accountbalance,0)+ISNULL(@rechargekeepamt,0)*(-1),accountdebts=isnull(accountdebts,0)-ISNULL(@rechargekeepamt,0)*(-1) where cardno=@salecardno and accounttype=CAST(@rechargeaccounttype as int)  
 end  
 --新增账户历史  
  declare @costaccountseqno  float  
  select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@salecardno   
  insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  
  values(@compid,@salecardno,CAST(@rechargeaccounttype as int),isnull(@costaccountseqno,0),0,isnull(@rechargekeepamt,0)*(-1),'CZ',@billid,@saledate,ISNULL(@curcardamt,0)+ISNULL(@rechargekeepamt,0))  
  set @costaccountseqno=@costaccountseqno+1  
    
   
 --生成疗程信息  
  delete cardproaccount where cardno=@salecardno and createbilltype='CZ' and createbillno=@billid
  
  if(ISNULL(@acc4totalamt,0)>0)  
  begin  
    update cardaccount set accountbalance=isnull(accountbalance,0)+isnull(@acc4totalamt,0)*(-1) where cardno=@salecardno and accounttype=4    
	declare @costaccount4lastamt float   
	select top 1 @costaccount4lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@salecardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc   
	insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  
	values(@compid,@salecardno,4,isnull(@costaccountseqno,0),0,isnull(@acc4totalamt,0)*(-1),'CZ',@billid,@saledate,isnull(@costaccount4lastamt,0))  
  end  
  update mcardrechargeinfo set salebakflag=1 where rechargecompid=@compid and rechargebillid=@billid  
end
GO
/****** Object:  StoredProcedure [dbo].[upg_handrechargecardbill_card]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_handrechargecardbill_card]  
@compid   varchar(10), --续卡公司        
@billid   varchar(20), --续卡单号   
@cardtype  varchar(20) --卡种  
as        
begin   
 --更新内部工号  
 update mcardrechargeinfo set firstsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and firstsalerid=staffno and compno=@compid  
 update mcardrechargeinfo set secondsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and secondsalerid=staffno and compno=@compid  
 update mcardrechargeinfo set thirdsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and thirdsalerid=staffno and compno=@compid  
 update mcardrechargeinfo set fourthsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and fourthsalerid=staffno and compno=@compid  
 update mcardrechargeinfo set fifthsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and fifthsalerid=staffno and compno=@compid  
 update mcardrechargeinfo set sixthsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and sixthsalerid=staffno and compno=@compid  
 update mcardrechargeinfo set seventhsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and seventhsalerid=staffno and compno=@compid  
 update mcardrechargeinfo set eighthsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and eighthsalerid=staffno and compno=@compid  
 declare @salecardno    varchar(20)  --续卡卡号  
 declare @saledate    varchar(8)  --续卡日期  
 declare @saletime    varchar(9)  --续卡时间  
   
 declare @rechargetype   int    --续费方式( 0充值 ,6还款)    
 declare @rechargekeepamt  float   --充值金额  
 declare @rechargedebtamt  float   --欠款金额  
 declare @curcardamt    float   --异动前余额  
 declare @curcarddebtamt   float   --异动前欠款  
 declare @rechargeaccounttype varchar(10)  --充值账户  
 declare @acc4totalamt  float   --疗程总额  
 declare @carduselife float  
 select @carduselife=carduselife from cardtypeinfo where cardtypeno=@cardtype and cardtypemodeid='SCM'  
 declare @cutoffdate varchar(10)  
 select @cutoffdate=convert(varchar(10),dateadd(day,@carduselife,cast(@saledate as datetime)),120)  
   
 select @salecardno=rechargecardno,@saledate=rechargedate,@saletime=rechargetime,@rechargetype=rechargetype,  
        @rechargekeepamt=rechargekeepamt,@rechargedebtamt=rechargedebtamt,@curcardamt=curcardamt,@curcarddebtamt=curcarddebtamt,@rechargeaccounttype=rechargeaccounttype  
 from mcardrechargeinfo with(nolock) where rechargecompid=@compid and rechargebillid=@billid  
   
 select @cutoffdate=substring(@cutoffdate,1,4)+substring(@cutoffdate,6,2)+substring(@cutoffdate,9,2)  
 select @acc4totalamt=sum(ISNULL(saleproamt,0)) from dsalecardproinfo with(nolock) where salecompid=@compid and salebillid=@billid and salebilltype=2  
   
 --更新账户金额  
 if(@rechargetype=0)--充值  
 begin  
  if exists(select 1 from cardaccount where cardno=@salecardno and accounttype=CAST(@rechargeaccounttype as int))  
  begin  
    update cardaccount set accountbalance=isnull(accountbalance,0)+ISNULL(@rechargekeepamt,0),accountdebts=isnull(accountdebts,0)+ISNULL(@rechargedebtamt,0) where cardno=@salecardno and accounttype=CAST(@rechargeaccounttype as int)  
  end  
  else  
  begin  
   insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)  
   values(@compid,@salecardno,CAST(@rechargeaccounttype as int),ISNULL(@rechargekeepamt,0),ISNULL(@rechargedebtamt,0),@saledate,@cutoffdate,'')  
  end  
 end  
 else --还款  
 begin  
   update cardaccount set accountbalance=isnull(accountbalance,0)+ISNULL(@rechargekeepamt,0),accountdebts=isnull(accountdebts,0)-ISNULL(@rechargekeepamt,0) where cardno=@salecardno and accounttype=CAST(@rechargeaccounttype as int)  
 end  
 --新增账户历史  
  declare @costaccountseqno  float  
  select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@salecardno   
  insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  
  values(@compid,@salecardno,CAST(@rechargeaccounttype as int),isnull(@costaccountseqno,0),0,@rechargekeepamt,'CZ',@billid,@saledate,@curcardamt)  
  set @costaccountseqno=@costaccountseqno+1  
    
   
 --生成疗程信息  
  declare @proseqno float  
  select @proseqno=MAX(proseqno)+1 from cardproaccount with(nolock) where cardno=@salecardno   
  insert cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,saledate,cutoffdate,proremark,createbilltype,createbillno)  
  select @compid,@salecardno,saleproid,ISNULL(@proseqno,0)+ISNULL(seleproseqno,0),saleprotype,ISNULL(saleprocount,0)+ISNULL(sendprocount,0),0,ISNULL(saleprocount,0)+ISNULL(sendprocount,0),  
  ISNULL(saleproamt,0),0,ISNULL(saleproamt,0),@saledate,procutoffdate,saleproremark,'CZ',@billid   
  from dsalecardproinfo where salecompid=@compid and salebillid=@billid and salebilltype=2  
    
  if(ISNULL(@acc4totalamt,0)>0)  
  begin  
   if exists(select 1 from cardaccount where cardno=@salecardno and accounttype=4)  
    update cardaccount set accountbalance=isnull(accountbalance,0)+@acc4totalamt where cardno=@salecardno and accounttype=4  
   else  
   begin  
    insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)  
    values(@compid,@salecardno,4,@acc4totalamt,0,@saledate,@cutoffdate,'')  
   end  
   declare @costaccount4lastamt float   
   select top 1 @costaccount4lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@salecardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc   
   insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  
   values(@compid,@salecardno,4,isnull(@costaccountseqno,0),0,@acc4totalamt,'CZ',@billid,@saledate,isnull(@costaccount4lastamt,0))  
  end  
end
GO
/****** Object:  StoredProcedure [dbo].[upg_handle_card_allot]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_handle_card_allot]    
(    
 @compid varchar(10), --单据公司    
 @billid varchar(20) --领用单据编号    
)    
as    
begin    
    
	create table #dcardallotment   
	(    
		cardclass varchar(10) null, --卡类型    
		cardfrom  varchar(20) null, --卡开始编号    
		cardto   varchar(20)   null, --卡结束编号    
		storage   varchar(10) null, --仓库编号    
		compid   varchar(10) null, --公司编号    
	)    
     
	insert into #dcardallotment(cardclass,cardfrom,cardto,storage,compid)    
	select cardtypeid,cardnofrom,cardnoto,callotwareid,a.callotcompid    
	  from mcardallotment a,dcardallotment b   
	 where a.callotcompid=@compid and a.callotbillid=@billid    
	   and a.callotcompid = b.callotcompid and a.callotbillid = b.callotbillid  and isnull(cardtypeid,'')<>'' and isnull(cardnofrom,'')<>''    
     
  declare @varCardClass varchar(10)     
  declare @varCardFrom varchar(20)     
  declare @varCardTo varchar(20)     
  declare @varStorage varchar(10)    
  declare @varCompid varchar(10)    
  declare @cardlength int     
  declare @filterNum varchar(10)    
     
  select @cardlength = cast(paramvalue as int) from sysparaminfo where compid=@compid and paramid='SP019'    
  select @filterNum = isnull(paramvalue,'')  from sysparaminfo where compid=@compid and paramid='SP020'    
     
  declare cur_dcardallotment cursor for    
  select cardclass,cardfrom,cardto,storage,compid  from #dcardallotment   
  open cur_dcardallotment     
  fetch cur_dcardallotment into @varCardClass,@varCardFrom,@varCardTo,@varStorage,@varCompid    
  while @@fetch_status=0    
  begin    
		declare @rangeStart varchar(20)    
		declare @rangeEnd varchar(20)    
		declare @leftRangeCount int    
		declare @rightRangeCount int    
      
		set @leftRangeCount=0    
		set @rightRangeCount=0    
		--理论情况下，不会出现卡号范围出现交叉的情况;预防万一，取top 1    
		select top 1 @rangeStart=cardfrom,@rangeEnd=cardto    
		from cardstock    
		where cardclass = @varCardClass and storage = @varStorage and compid = @varCompid    
			and substring(cardfrom,1,len(cardfrom)-@cardlength) = substring(@varCardFrom,1,len(@varCardFrom)-@cardlength)    
			and substring(@varCardFrom,len(@varCardFrom)-@cardlength+1,@cardlength)>=substring(cardfrom,len(cardfrom)-@cardlength+1,@cardlength)    
			and substring(@varCardTo,len(@varCardTo)-@cardlength+1,@cardlength)<=substring(cardto,len(cardto)-@cardlength+1,@cardlength)    
        
		if(@rangeStart <> @varCardFrom)    
		begin    
			declare @leftStartNum int    
			declare @leftEndNum int    
			select @leftEndNum = cast(substring(@varCardFrom,len(@varCardFrom)-@cardlength+1,@cardlength) as int)    
			select @leftStartNum = cast(substring(@rangeStart,len(@rangeStart)-@cardlength+1,@cardlength) as int)    
			if(isnull(@filterNum,'')='')    
			begin    
				set @leftRangeCount = @leftEndNum-@leftStartNum    
			end    
			else    
			begin    
				declare @leftTempNum  int     
				select @leftTempNum = @leftStartNum    
				while(@leftTempNum<@leftEndNum)    
				begin    
					if(substring(cast(@leftTempNum as varchar),len(cast(@leftTempNum as varchar)),1)!=@filterNum)    
						set @leftRangeCount = @leftRangeCount + 1    
					set @leftTempNum = @leftTempNum + 1    
				end    
			end    
		end    
		if(@rangeEnd <> @varCardTo)    
		begin    
			declare @rightStartNum int    
			declare @rightEndNum int    
			select @rightEndNum = cast(substring(@rangeEnd,len(@rangeEnd)-@cardlength+1,@cardlength) as int)    
			select @rightStartNum = cast(substring(@varCardTo,len(@varCardTo)-@cardlength+1,@cardlength) as int)    
			if(isnull(@filterNum,'')='')    
			begin    
				set @rightRangeCount = @rightEndNum-@rightStartNum    
			end    
			else    
			begin    
				declare @rightTempNum  int     
				select @rightTempNum = @rightStartNum    
				while(@rightTempNum<@rightEndNum)    
				begin    
					if(substring(cast(@rightTempNum as varchar),len(cast(@rightTempNum as varchar)),1)!=@filterNum)    
						set @rightRangeCount = @rightRangeCount + 1    
					set @rightTempNum = @rightTempNum + 1    
				end    
			end    
		end    
       
		declare @cardPrefix varchar(20)    
		set @cardPrefix = substring(@varCardFrom,1,len(@varCardFrom)-@cardlength)    
     
  
  
		if(@leftRangeCount != 0)    
		begin    
			declare @cardSuffix varchar(20)    
			set @cardSuffix = cast(cast(substring(@varCardFrom,len(@cardPrefix)+1,@cardlength) as int)-1 as varchar)    
        
			if(substring(@cardSuffix,len(@cardSuffix),1)=@filterNum )    
			set @cardSuffix = cast(substring(@varCardFrom,len(@cardPrefix)+1,@cardlength) as int)-2    
  
  
			--  加0------------------  
			declare @cardSuffixLen int   
			set @cardSuffixLen=len(@cardSuffix)   
			if(@cardSuffixLen<@cardlength)  
			begin  
				while(@cardlength-@cardSuffixLen>0)  
				begin  
					set @cardSuffix='0'+@cardSuffix  
					set @cardSuffixLen=@cardSuffixLen+1  
				end  
			end  
			-----------------------------------      
			declare @leftRangeEndX varchar(20)    
			set @leftRangeEndX = @cardPrefix+@cardSuffix    
     
			insert into cardstock(cardclass,cardfrom,cardto,ccount,storage,compid)    
			values(@varCardClass,@rangeStart,@leftRangeEndX,@leftRangeCount,@varStorage,@varCompid)    
		end    
         
		if(@rightRangeCount !=0)    
		begin    
			declare @cardSuffix2 varchar(20)    
			set @cardSuffix2 = cast(cast(substring(@varCardTo,len(@cardPrefix)+1,@cardlength) as int)+1 as varchar)    
        
			if(substring(@cardSuffix2,len(@cardSuffix2),1)=@filterNum )    
			set @cardSuffix2 = cast(substring(@varCardTo,len(@cardPrefix)+1,@cardlength) as int)+2    
        
  
			-- 加0------------------  
			declare @cardSuffix2Len int   
			set @cardSuffix2Len=len(@cardSuffix2)   
			if(@cardSuffix2Len<@cardlength)  
			begin  
				 while(@cardlength-@cardSuffix2Len>0)  
				 begin  
					set @cardSuffix2='0'+@cardSuffix2  
					set @cardSuffix2Len=@cardSuffix2Len+1  
			end  
		end  
		-----------------------------------  
  
		declare @rightRangeStartX varchar(20)    
		set @rightRangeStartX = @cardPrefix+@cardSuffix2    
		insert into cardstock(cardclass,cardfrom,cardto,ccount,storage,compid)    
		values(@varCardClass,@rightRangeStartX,@rangeEnd,@rightRangeCount,@varStorage,@varCompid)    
	end    
       
	delete cardstock 	where cardclass=@varCardClass and cardfrom=@rangeStart and cardto=@rangeEnd    
     
  fetch cur_dcardallotment into @varCardClass,@varCardFrom,@varCardTo,@varStorage,@varCompid    
  end    
  close cur_dcardallotment    
  deallocate cur_dcardallotment    
      
  drop table #dcardallotment     
end
GO
/****** Object:  StoredProcedure [dbo].[upg_handconsumbill_card_back]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_handconsumbill_card_back]
@compid			varchar(10),	--消费公司      
@sendid			varchar(20),	--消费单号 
@costdate		varchar(8),		--消费日期
@costtype		int	,			-- 1 散客消费 2 会员消费
@oldsendid		varchar(20)		--原始消费单号 
as      
begin 
	--更新内部工号
	update dconsumeinfo set csfirstinid=manageno from dconsumeinfo,staffinfo where cscompid=@compid and csbillid=@sendid and csfirstsaler=staffno and compno=@compid
	update dconsumeinfo set cssecondinid=manageno from dconsumeinfo,staffinfo where cscompid=@compid and csbillid=@sendid and cssecondsaler=staffno and compno=@compid
	update dconsumeinfo set csthirdinid=manageno from dconsumeinfo,staffinfo where cscompid=@compid and csbillid=@sendid and csthirdsaler=staffno and compno=@compid
	
	declare @cardno varchar(20)			--消费卡号
	declare @costaccount2	float		--储值账户
	declare @costaccount3	float		--积分账户
	declare @costaccount4	float		--疗程账户
	declare @costaccount5	float		--收购卡账户
	declare @tuangoucardno	varchar(20)	--团购卡号
	declare @tiaomacardno	varchar(20)	--条码卡号
	declare @diyongcardno	varchar(20)	--抵用券号
	
	select @cardno=cscardno,@tuangoucardno=isnull(tuangoucardno,''),@tiaomacardno=isnull(tiaomacardno,''),@diyongcardno=isnull(diyongcardno,''),
	@costaccount2=SUM(case when paymode='4' then isnull(payamt,0) else 0 end ),
	@costaccount3=SUM(case when paymode='7' then isnull(payamt,0) else 0 end ),
	@costaccount4=SUM(case when paymode='9' then isnull(payamt,0) else 0 end ),
	@costaccount5=SUM(case when paymode='A' then isnull(payamt,0) else 0 end )
	from mconsumeinfo with(nolock),dpayinfo with(nolock)
	where cscompid=paycompid and csbillid=paybillid and paybilltype='SY'
	and cscompid=@compid and  csbillid=@sendid
	group by cscardno,isnull(tuangoucardno,''),isnull(tiaomacardno,''),isnull(diyongcardno,'')

	--团购卡
	if(isnull(@tuangoucardno,'')<>'')
	begin
		update corpsbuyinfo set corpssate=1,useincompid='', useinbillno='',useindate='' where corpscardno=@tuangoucardno
	end
	
	--抵用券
	if(isnull(@diyongcardno,'')<>'')
	begin
		update nointernalcardinfo set cardstate=1,usedate='', useinproject='' where cardno=@diyongcardno and cardtype=1
		--update a set a.usecount=ISNULL(a.usecount,0)-ISNULL(b.csitemcount,0),a.lastcount=ISNULL(a.lastcount,0)+ISNULL(b.csitemcount,0),
		             --a.useamt=ISNULL(a.useamt,0)-ISNULL(b.csitemamt,0),a.lastamt=ISNULL(a.lastamt,0)+ISNULL(b.csitemamt,0) 
		 --from dnointernalcardinfo a,dconsumeinfo b
		--where cscompid=@compid and csbillid=@oldsendid and cspaymode='11' and a.ineritemno=b.csitemno and a.cardno=@diyongcardno
		
	end
	
	--条码卡
	if(isnull(@tiaomacardno,'')<>'')
	begin
		update a set a.usecount=ISNULL(a.usecount,0)-ISNULL(b.csitemcount,0),a.lastcount=ISNULL(a.lastcount,0)+ISNULL(b.csitemcount,0),
		             a.useamt=ISNULL(a.useamt,0)-ISNULL(b.csitemamt,0),a.lastamt=ISNULL(a.lastamt,0)+ISNULL(b.csitemamt,0) 
		 from dnointernalcardinfo a,dconsumeinfo b
		where cscompid=@compid and csbillid=@oldsendid and cspaymode='13' and a.ineritemno=b.csitemno and a.cardno=@tiaomacardno
		
	end
	
	--唯一产品条码
	update dgoodsbarinfo set barnostate=2,costbillo='',coststore='' where costbillo=@oldsendid   and coststore=@compid
	if(@costtype=1)
		return 
	declare @costaccountseqno	float		--最新账户序号
	declare @costaccount2lastamt	float		--储值账户前次余额
	declare @costaccount3lastamt	float		--积分账户前次余额
	declare @costaccount4lastamt	float		--疗程账户前次余额
	declare @costaccount5lastamt	float		--收购卡账户前次余额
	
	select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@cardno 
	--更新会员卡账户+账户历史 (若是疗程账户大于0,则需扣除相应的疗程明细)
	if(ISNULL(@costaccount2,0)<>0) --储值账户
	begin
				--生成账户历史
			select top 1 @costaccount2lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc 
			if(ISNULL(@costaccount2lastamt,0)=0)
				select @costaccount2lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=2
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@cardno,2,ISNULL(@costaccountseqno,0),2,@costaccount2,'SY',@sendid,@costdate,@costaccount2lastamt)
			set @costaccountseqno=ISNULL(@costaccountseqno,0)+1
			--更新账户
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount2,0) where cardno=@cardno and accounttype='2'
		
	end
	if(ISNULL(@costaccount3,0)<>0) --积分账户
	begin
			--生成账户历史
			select top 1 @costaccount3lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=3 order by chagedate desc,changeseqno desc 
			if(ISNULL(@costaccount3lastamt,0)=0)
				select @costaccount3lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=3
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@cardno,3,ISNULL(@costaccountseqno,0),2,@costaccount3,'SY',@sendid,@costdate,@costaccount3lastamt)
			set @costaccountseqno=ISNULL(@costaccountseqno,0)+1
			--更新账户
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount3,0) where cardno=@cardno and accounttype='3'
			
	end
	if(ISNULL(@costaccount4,0)<>0) --疗程账户
	begin	
			--生成账户历史
			select top 1 @costaccount4lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc 
			if(ISNULL(@costaccount4lastamt,0)=0)
				select @costaccount4lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=4
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@cardno,4,ISNULL(@costaccountseqno,0),2,@costaccount4,'SY',@sendid,@costdate,@costaccount4lastamt)
			set @costaccountseqno=ISNULL(@costaccountseqno,0)+1
			--更新账户
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount4,0) where cardno=@cardno and accounttype='4'
			
			--消耗疗程明细
			update cardproaccount set costcount=isnull(costcount,0)+isnull(csitemcount,0),
									  lastcount=isnull(lastcount,0)-isnull(csitemcount,0),
									  costamt=isnull(costamt,0)+isnull(csitemamt,0),
									  lastamt=isnull(lastamt,0)-isnull(csitemamt,0)
			from cardproaccount,
			(select csitemno,csproseqno,csitemcount=sum(ISNULL(csitemcount,0)),csitemamt=SUM(isnull(csitemamt,0)) from dconsumeinfo where cspaymode='9' and cscompid=@compid and  csbillid=@sendid group by csitemno,csproseqno) as costdetial
			 where cardno=@cardno and projectno=csitemno and proseqno=csproseqno
	end
	if(ISNULL(@costaccount5,0)<>0) --收购账户
	begin
				--生成账户历史
			select top 1 @costaccount5lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=5 order by chagedate desc,changeseqno desc 
			if(ISNULL(@costaccount5lastamt,0)=0)
			select @costaccount5lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=5
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@cardno,5,ISNULL(@costaccountseqno,0),2,@costaccount5,'SY',@sendid,@costdate,@costaccount5lastamt)
			--更新账户
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount5,0) where cardno=@cardno and accounttype='5'
		
	end
	
	--生成项目交易明细
	insert cardtransactionhistory(transactioncompid,transactioncardno,transactiondate,transactiontype,codeno,codename,ccount,price,billtype,billno,firstempid,secondempid,thirthempid,paymode)
	select cscompid,@cardno,@costdate,3,csitemno,prjname,csitemcount,csitemamt,'SY',csbillid,csfirstsaler,cssecondsaler,csthirdsaler,cspaymode
	 from dconsumeinfo a,compchaininfo b,projectinfo c
	  where  csinfotype=1  and cscompid=@compid and  csbillid=@sendid
	  and  c.prjno = a.csitemno and  b.curcomp=c.prisource and b.relationcomp=@compid


	--生成产品交易明细
	insert cardtransactionhistory(transactioncompid,transactioncardno,transactiondate,transactiontype,codeno,codename,ccount,price,billtype,billno,firstempid,secondempid,thirthempid,paymode)
	select cscompid,@cardno,@costdate,4,csitemno,goodsname,csitemcount,csitemamt,'SY',csbillid,csfirstsaler,cssecondsaler,csthirdsaler,cspaymode
	 from dconsumeinfo a,compchaininfo b,goodsinfo c
	  where  csinfotype=2  and cscompid=@compid and  csbillid=@sendid
	  and  c.goodsno = a.csitemno and  b.curcomp=c.goodssource and b.relationcomp=@compid



end
GO
/****** Object:  StoredProcedure [dbo].[upg_handconsumbill_card]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_handconsumbill_card]
@compid			varchar(10),	--消费公司      
@sendid			varchar(20),	--消费单号 
@costdate		varchar(8),		--消费日期
@costtype		int				-- 1 散客消费 2 会员消费
as      
begin 
	--更新内部工号
	update dconsumeinfo set csfirstinid=manageno from dconsumeinfo,staffinfo where cscompid=@compid and csbillid=@sendid and csfirstsaler=staffno and compno=@compid
	update dconsumeinfo set cssecondinid=manageno from dconsumeinfo,staffinfo where cscompid=@compid and csbillid=@sendid and cssecondsaler=staffno and compno=@compid
	update dconsumeinfo set csthirdinid=manageno from dconsumeinfo,staffinfo where cscompid=@compid and csbillid=@sendid and csthirdsaler=staffno and compno=@compid
	
	declare @cardno varchar(20)			--消费卡号
	declare @costaccount2	float		--储值账户
	declare @costaccount3	float		--积分账户
	declare @costaccount4	float		--疗程账户
	declare @costaccount5	float		--收购卡账户
	declare @tuangoucardno	varchar(20)	--团购卡号


	
	select @cardno=cscardno,@tuangoucardno=tuangoucardno,
	@costaccount2=SUM(case when paymode='4' then isnull(payamt,0) else 0 end ),
	@costaccount3=SUM(case when paymode='7' then isnull(payamt,0) else 0 end ),
	@costaccount4=SUM(case when paymode='9' then isnull(payamt,0) else 0 end ),
	@costaccount5=SUM(case when paymode='A' then isnull(payamt,0) else 0 end )
	from mconsumeinfo with(nolock),dpayinfo with(nolock)
	where cscompid=paycompid and csbillid=paybillid and paybilltype='SY'
	and cscompid=@compid and  csbillid=@sendid
	group by cscardno,tuangoucardno

	if(isnull(@tuangoucardno,'')<>'')
	begin
		update corpsbuyinfo set corpssate=2,useincompid=@compid, useinbillno=@sendid,useindate=@costdate where corpscardno=@tuangoucardno
	end
	
	
	if(@costtype=1)
		return 

	
	
	
	declare @costaccountseqno	float		--最新账户序号
	declare @costaccount2lastamt	float		--储值账户前次余额
	declare @costaccount3lastamt	float		--积分账户前次余额
	declare @costaccount4lastamt	float		--疗程账户前次余额
	declare @costaccount5lastamt	float		--收购卡账户前次余额
	
	select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@cardno 
	--更新会员卡账户+账户历史 (若是疗程账户大于0,则需扣除相应的疗程明细)
	if(ISNULL(@costaccount2,0)<>0) --储值账户
	begin
				--生成账户历史
			select top 1 @costaccount2lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc 
			if(ISNULL(@costaccount2lastamt,0)=0)
				select @costaccount2lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=2
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@cardno,2,ISNULL(@costaccountseqno,0),2,@costaccount2,'SY',@sendid,@costdate,@costaccount2lastamt)
			set @costaccountseqno=ISNULL(@costaccountseqno,0)+1
			--更新账户
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount2,0) where cardno=@cardno and accounttype='2'
		
	end
	if(ISNULL(@costaccount3,0)<>0) --积分账户
	begin
			--生成账户历史
			select top 1 @costaccount3lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=3 order by chagedate desc, changeseqno desc 
			if(ISNULL(@costaccount3lastamt,0)=0)
				select @costaccount3lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=3
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@cardno,3,ISNULL(@costaccountseqno,0),2,@costaccount3,'SY',@sendid,@costdate,@costaccount3lastamt)
			set @costaccountseqno=ISNULL(@costaccountseqno,0)+1
			--更新账户
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount3,0) where cardno=@cardno and accounttype='3'
			
	end
	if(ISNULL(@costaccount4,0)<>0) --疗程账户
	begin	
			--生成账户历史
			select top 1 @costaccount4lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc 
			if(ISNULL(@costaccount4lastamt,0)=0)
				select @costaccount4lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=4
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@cardno,4,ISNULL(@costaccountseqno,0),2,@costaccount4,'SY',@sendid,@costdate,@costaccount4lastamt)
			set @costaccountseqno=ISNULL(@costaccountseqno,0)+1
			--更新账户
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount4,0) where cardno=@cardno and accounttype='4'
			
			--消耗疗程明细
			update cardproaccount set costcount=isnull(costcount,0)+isnull(csitemcount,0),
									  lastcount=isnull(lastcount,0)-isnull(csitemcount,0),
									  costamt=isnull(costamt,0)+isnull(csitemamt,0),
									  lastamt=isnull(lastamt,0)-isnull(csitemamt,0)
			from cardproaccount,
			(select csitemno,csproseqno,csitemcount=sum(ISNULL(csitemcount,0)),csitemamt=SUM(isnull(csitemamt,0)) from dconsumeinfo where cspaymode='9' and cscompid=@compid and  csbillid=@sendid group by csitemno,csproseqno) as costdetial
			 where cardno=@cardno and projectno=csitemno and proseqno=csproseqno
	end
	if(ISNULL(@costaccount5,0)<>0) --收购账户
	begin
				--生成账户历史
			select top 1 @costaccount5lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=5 order by chagedate desc,changeseqno desc 
			if(ISNULL(@costaccount5lastamt,0)=0)
			select @costaccount5lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=5
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@cardno,5,ISNULL(@costaccountseqno,0),2,@costaccount5,'SY',@sendid,@costdate,@costaccount5lastamt)
			--更新账户
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount5,0) where cardno=@cardno and accounttype='5'
		
	end
	
	--生成项目交易明细
	insert cardtransactionhistory(transactioncompid,transactioncardno,transactiondate,transactiontype,codeno,codename,ccount,price,billtype,billno,firstempid,secondempid,thirthempid,paymode)
	select cscompid,@cardno,@costdate,3,csitemno,prjname,csitemcount,csitemamt,'SY',csbillid,csfirstsaler,cssecondsaler,csthirdsaler,cspaymode
	 from dconsumeinfo a,compchaininfo b,projectinfo c
	  where  csinfotype=1  and cscompid=@compid and  csbillid=@sendid
	  and  c.prjno = a.csitemno and  b.curcomp=c.prisource and b.relationcomp=@compid


	--生成产品交易明细
	insert cardtransactionhistory(transactioncompid,transactioncardno,transactiondate,transactiontype,codeno,codename,ccount,price,billtype,billno,firstempid,secondempid,thirthempid,paymode)
	select cscompid,@cardno,@costdate,4,csitemno,goodsname,csitemcount,csitemamt,'SY',csbillid,csfirstsaler,cssecondsaler,csthirdsaler,cspaymode
	 from dconsumeinfo a,compchaininfo b,goodsinfo c
	  where  csinfotype=2  and cscompid=@compid and  csbillid=@sendid
	  and  c.goodsno = a.csitemno and  b.curcomp=c.goodssource and b.relationcomp=@compid



end
GO
/****** Object:  StoredProcedure [dbo].[upg_handcardchangebill_back]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_handcardchangebill_back]
@compid			varchar(10),	--兑换公司      
@billid			varchar(20)		--兑换单号 		
as      
begin 
	declare @changetype		int				--转卡类型
	declare @oldcardno		varchar(20)		--转卡老卡号
	declare @newcardno		varchar(20)		--转卡新卡号
	declare @rechargeamt	float			--充值金额
	declare @detamt			float			--欠款金额
	declare @oldkeepamt		float			--原金额
	declare @olddetamt		float			--原欠款金额
	 --获取单据信息
	 select @changetype=changetype,@oldcardno=changebeforcardno,
	        @oldkeepamt=curaccountkeepamt,@olddetamt=curaccountdebtamt,
	        @rechargeamt=changefillamt,@detamt=changdebtamt,@newcardno=changeaftercardno
	 from mcardchangeinfo where changecompid=@compid and changebillid=@billid
	 
	 if(isnull(@changetype,0)=0)  --折扣转卡
	 begin
		  --将会员信息还原到老卡
		  delete memberinfo where memberno=@oldcardno
		  update memberinfo set memberno=@oldcardno where memberno=@newcardno
		  --将原先的账户历史清除
		  delete cardaccountchangehistory where changecardno=@newcardno and changebilltype='ZK' and changecompid=@compid and changebillno=@billid 
		  --再将原有的卡历史转移过去
		  update cardaccountchangehistory set changecardno=@oldcardno where changecardno=@newcardno
		  --将交易历史转移过去
		  update cardtransactionhistory set transactioncardno=@oldcardno where transactioncardno=@newcardno
		  --将异动历史转移过去
		  delete cardchangehistory where changecardno=@oldcardno
		  delete cardchangehistory where changecardno=@newcardno

		  --将老卡卡状态置为开卡状态
		  update cardinfo set cardstate=4 where cardno=@oldcardno
		   --将新卡卡状态置为未销售
		  update cardinfo set cardstate=1 where cardno=@newcardno
		   delete cardsoninfo where parentcardno=@newcardno
		  --将老卡账户还原
		  if not exists(select 1 from cardaccount where   cardno = @oldcardno and accounttype=2)      
		  begin      
				insert into cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts)       
				values(@compid,@oldcardno,2,ISNULL(@oldkeepamt,0),ISNULL(@olddetamt,0))      
		  end    
		  else
		  begin
				update cardaccount set accountbalance=ISNULL(@oldkeepamt,0),accountdebts=ISNULL(@olddetamt,0) where cardno=@oldcardno and accounttype=2
		  end
		  
		  update cardaccount set cardno= @oldcardno where  cardno=@newcardno  and ISNULL(accounttype,0)<>2
		  --将疗程账户更新到老卡
		  delete cardproaccount where cardno=@oldcardno
		  update cardproaccount set cardno=@oldcardno where cardno=@newcardno
		  --将新卡账户清0
		  update cardaccount set accountbalance=0 where cardno=@newcardno 
	 end
	 else if(isnull(@changetype,0)=1)  --收购转卡
	 begin
		  --将会员信息还原到老卡
		  delete memberinfo where memberno=@oldcardno
		  update memberinfo set memberno=@oldcardno where memberno=@newcardno
		  --将原先的账户历史清除
		  delete cardaccountchangehistory where changecardno=@newcardno and changebilltype='ZK' and changecompid=@compid and changebillno=@billid 
		  --再将原有的卡历史转移过去
		  update cardaccountchangehistory set changecardno=@oldcardno where changecardno=@newcardno
		  --将交易历史转移过去
		  update cardtransactionhistory set transactioncardno=@oldcardno where transactioncardno=@newcardno
		   delete cardchangehistory where changecardno=@oldcardno
		   delete cardchangehistory where changecardno=@newcardno
		  --将老卡卡状态置为开卡状态
		  update cardinfo set cardstate=4 where cardno=@oldcardno
		   --将新卡卡状态置为未销售
		  update cardinfo set cardstate=1 where cardno=@newcardno
		  delete cardsoninfo where parentcardno=@newcardno
		  --将老卡账户还原
		  if not exists(select 1 from cardaccount where   cardno = @oldcardno and accounttype=5)      
		  begin      
				insert into cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts)       
				values(@compid,@oldcardno,5,ISNULL(@oldkeepamt,0),ISNULL(@olddetamt,0))      
		  end    
		  else
		  begin
				update cardaccount set accountbalance=ISNULL(@oldkeepamt,0),accountdebts=ISNULL(@olddetamt,0) where cardno=@oldcardno and accounttype=5
		  end
		  --将新卡账户清0
		  update cardaccount set accountbalance=0 where cardno=@newcardno 
	 end
	 else if(isnull(@changetype,0)=2)  --竞争转卡
	 begin
		  --将原先的账户历史清除
		  delete cardaccountchangehistory where changecardno=@newcardno and changebilltype='ZK' and changecompid=@compid and changebillno=@billid 
		  --将新卡卡状态置为未销售
		  update cardinfo set cardstate=1 where cardno=@newcardno
		  delete cardsoninfo where parentcardno=@newcardno
		  delete cardchangehistory where changecardno=@newcardno
		  delete cardtransactionhistory where transactioncardno=@newcardno
		  delete memberinfo where memberno=@newcardno
		  --将新卡账户清0
		  update cardaccount set accountbalance=0 where cardno=@newcardno 
	 end
	 update mcardchangeinfo set salebakflag=1 where changecompid=@compid and changebillid=@billid
					
end
GO
/****** Object:  StoredProcedure [dbo].[upg_handaccountChangebill_card_back]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_handaccountChangebill_card_back]
@compid			varchar(10),	--兑换公司      
@billid			varchar(20),	--兑换单号 
@proseqno		float			--反冲疗程序号			
as      
begin 

	declare @changecardno				varchar(20)		--兑换卡号
	declare @changedate					varchar(8)		--兑换日期
	declare @changetime					varchar(9)		--兑换时间
	declare @changeaccount				varchar(5)		--抵用账户
	declare @changeaccountamt			float			--抵用账户金额
	declare @inseraccount4amt			float			--兑换疗程账户金额
	
	select @changecardno=changecardno,@changedate=changedate,@changetime=changetime,@changeaccount=ISNULL(changeaccounttype,0)
	from mproexchangeinfo with(nolock) where changecompid=@compid and changebillid=@billid
	
	select @changeaccountamt=SUM(ISNULL(changebyaccountamt,0)),@inseraccount4amt=SUM(isnull(changebyaccountamt,0)+ISNULL(changebyproaccountamt,0)+ISNULL(changebycashamt,0))
	from  dproexchangeinfo with(nolock) where changecompid=@compid and changebillid=@billid and changeseqno=@proseqno
	
	--更新账户金额
		if(ISNULL(@changeaccountamt,0)>0)--抵用账户
		begin
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-isnull(@changeaccountamt,0)*(-1) where accounttype=@changeaccount and cardno=@changecardno
		end
	
		if(ISNULL(@inseraccount4amt,0)>0)--兑换疗程账户
		begin
			update cardaccount set accountbalance=ISNULL(accountbalance,0)+isnull(@inseraccount4amt,0)*(-1) where accounttype='4' and cardno=@changecardno
		end
	--新增账户历史
		declare @costaccountseqno		float
		declare @curcardamt				float
		select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@changecardno 
		if(ISNULL(@changeaccountamt,0)>0)--抵用账户
		begin
			set @curcardamt=0
			select top 1 @curcardamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@changecardno  and changeaccounttype=CAST(@changeaccount as int) order by chagedate desc,changeseqno desc 
			--新增账户历史	
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@changecardno,CAST(@changeaccount as int),isnull(@costaccountseqno,0),12,isnull(@changeaccountamt,0)*(-1),'DH',@billid,@changedate,@curcardamt)
			set @costaccountseqno=@costaccountseqno+1
		end
	
		if(ISNULL(@inseraccount4amt,0)>0)--新增疗程账户
		begin
			set @curcardamt=0
			select top 1 @curcardamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@changecardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc 
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@changecardno,4,isnull(@costaccountseqno,0),11,isnull(@inseraccount4amt,0)*(-1),'DH',@billid,@changedate,@curcardamt)
			set @costaccountseqno=@costaccountseqno+1
		end
		--生成疗程信息
		  
		delete c from mproexchangeinfo a,dproexchangeinfo b,cardproaccount c 
				where a.changecompid=b.changecompid and a.changebillid=b.changebillid
				 and b.changecompid=@compid and b.changebillid=@billid and b.changeseqno=@proseqno
				and a.changecardno=c.cardno and b.changeproid=c.projectno and a.changebillid=c.createbillno 
				and c.createbilltype='LCDH' and c.lastcount=b.changeprocount and ( c.createseqno=@proseqno  or ISNULL(c.createseqno,0)=0) 
		
	--处理抵用券状态
	 update nointernalcardinfo set cardstate=1,usedate=''
	 from nointernalcardinfo a,dproexchangeinfo b
	 where b.changecompid=@compid and b.changebillid=@billid and b.nointernalcardno=a.cardno
	 --设置反冲标准位
	-- update mproexchangeinfo  set backcsflag=1 where  changecompid=@compid and changebillid=@billid
	 update dproexchangeinfo  set salebakflag=1 where  changecompid=@compid and changebillid=@billid and changeseqno=@proseqno
 
end
GO
/****** Object:  StoredProcedure [dbo].[upg_handaccountChangebill_card]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_handaccountChangebill_card]
@compid			varchar(10),	--兑换公司      
@billid			varchar(20)		--兑换单号 
as      
begin 
	--更新内部工号
	update dproexchangeinfo set firstsalerinid=manageno		from dproexchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and firstsalerid=staffno and compno=@compid
	update dproexchangeinfo set secondsalerinid=manageno	from dproexchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and secondsalerid=staffno and compno=@compid
	update dproexchangeinfo set thirdsalerinid=manageno		from dproexchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and thirdsalerid=staffno and compno=@compid
	update dproexchangeinfo set fourthsalerinid=manageno	from dproexchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and fourthsalerid=staffno and compno=@compid
	
	declare @changecardno				varchar(20)		--兑换卡号
	declare @changedate					varchar(8)		--兑换日期
	declare @changetime					varchar(9)		--兑换时间
	
	declare @changeaccount				varchar(5)		--抵用账户
	declare @changeaccountamt			float			--抵用账户金额
	declare @changeaccount4amt			float			--抵用疗程账户金额
	declare @inseraccount4amt			float			--兑换疗程账户金额
	
	select @changecardno=changecardno,@changedate=changedate,@changetime=changetime,@changeaccount=ISNULL(changeaccounttype,0)
	from mproexchangeinfo with(nolock) where changecompid=@compid and changebillid=@billid
	
	select @changeaccountamt=SUM(ISNULL(changebyaccountamt,0)),@changeaccount4amt=SUM(ISNULL(changebyproaccountamt,0)),@inseraccount4amt=SUM(isnull(changebyaccountamt,0)+ISNULL(changebyproaccountamt,0)+ISNULL(changebycashamt,0))
	from  dproexchangeinfo with(nolock) where changecompid=@compid and changebillid=@billid
	
	--更新账户金额
		if(ISNULL(@changeaccountamt,0)>0)--抵用账户
		begin
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-@changeaccountamt where accounttype=@changeaccount and cardno=@changecardno
		end
		if(ISNULL(@changeaccount4amt,0)>0)--抵用疗程账户
		begin
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-@changeaccount4amt where accounttype='4' and cardno=@changecardno
		end
		if(ISNULL(@inseraccount4amt,0)>0)--兑换疗程账户
		begin
			if not exists(select 1 from cardaccount where   cardno = @changecardno and accounttype=4)      
			begin      
					insert into cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts)       
					values(@compid,@changecardno,4,ISNULL(@inseraccount4amt,0),0)      
			end  
			else
			begin
				update cardaccount set accountbalance=ISNULL(accountbalance,0)+@inseraccount4amt where accounttype='4' and cardno=@changecardno
			end
		end
	--新增账户历史
		declare @costaccountseqno		float
		declare @curcardamt				float
		select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@changecardno 
		if(ISNULL(@changeaccountamt,0)>0)--抵用账户
		begin
			set @curcardamt=0
			select top 1 @curcardamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@changecardno  and changeaccounttype=CAST(@changeaccount as int) order by chagedate desc,changeseqno desc 
		
			--新增账户历史	
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@changecardno,CAST(@changeaccount as int),isnull(@costaccountseqno,0),12,@changeaccountamt,'DH',@billid,@changedate,@curcardamt)
			set @costaccountseqno=@costaccountseqno+1
		end
		if(ISNULL(@changeaccount4amt,0)>0)--抵用疗程账户
		begin
			set @curcardamt=0
			select top 1 @curcardamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@changecardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc 
		
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@changecardno,4,isnull(@costaccountseqno,0),13,@changeaccount4amt,'DH',@billid,@changedate,@curcardamt)
			set @costaccountseqno=@costaccountseqno+1
		end
		if(ISNULL(@inseraccount4amt,0)>0)--新增疗程账户
		begin
			set @curcardamt=0
			select top 1 @curcardamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@changecardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc 
		
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@changecardno,4,isnull(@costaccountseqno,0),11,@inseraccount4amt,'DH',@billid,@changedate,@curcardamt)
			set @costaccountseqno=@costaccountseqno+1
		end
	--生成疗程信息
		declare @proseqno float
		select @proseqno=MAX(proseqno)+1 from cardproaccount with(nolock) where cardno=@changecardno 
		insert cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,saledate,cutoffdate,proremark,createbilltype,createbillno,createseqno)
		select changecompid,@changecardno,changeproid,ISNULL(@proseqno,0)+ISNULL(changeseqno,0),4,ISNULL(changeprocount,0),0,ISNULL(changeprocount,0),
		isnull(changebyaccountamt,0)+ISNULL(changebyproaccountamt,0)+ISNULL(changebycashamt,0),0,isnull(changebyaccountamt,0)+ISNULL(changebyproaccountamt,0)+ISNULL(changebycashamt,0),@changedate,'',changemark,'LCDH',@billid ,changeseqno
		from dproexchangeinfo where changecompid=@compid and changebillid=@billid 
	--生成抵扣信息	
		if(ISNULL(@changeaccount4amt,0)>0)--抵用疗程账户
		begin
			update a set costcount=ISNULL(a.costcount,0)+ISNULL(b.changeprocount,0),
									  lastcount=ISNULL(a.lastcount,0)-ISNULL(b.changeprocount,0),
									  costamt=ISNULL(a.costamt,0)+ISNULL(b.changeproamt,0),
									  lastamt=ISNULL(a.lastamt,0)-ISNULL(b.changeproamt,0),
									  exchangeseqno=ISNULL(b.changeseqno,0),
									  changecompid=ISNULL(b.changecompid,''),
									  changebillid=ISNULL(b.changebillid,'')
			from cardproaccount a,dproexchangeinfobypro b
			where b.changecompid=@compid and b.changebillid=@billid and a.cardno=@changecardno and a.projectno=b.changeproid and a.proseqno=b.bproseqno
		end
	--处理抵用券状态
	 update nointernalcardinfo set cardstate=2,usedate=@changedate
	 from nointernalcardinfo a,dproexchangeinfo b
	 where b.changecompid=@compid and b.changebillid=@billid and b.nointernalcardno=a.cardno
end
GO
/****** Object:  StoredProcedure [dbo].[upg_hand_staff_instore]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_hand_staff_instore]            
(              
  @compid varchar(10),              
  @datefrom varchar(8),              
  @dateto varchar(8)              
)              
as              
begin     
	 if(isnull(@datefrom,'')='') return;          
	 create table #empinfobydate(          
		seqno int identity not null,          
		inid varchar(20) null,          
		empid varchar(20) null,          
		datefrom varchar(8) null,          
		dateto  varchar(8) null,          
		position varchar(10) null,          
		salary  float null,          
		sharetype varchar(5) null,          
		sharerate float null,          
		deducttax int null,           
	 )          
	 declare @curdate varchar(8)          
	 declare @tomorrow varchar(8)          
	 declare @datetoplus varchar(8)           
	 select @curdate=substring(convert(varchar(20),getdate(),102),1,4) + substring(convert(varchar(20),getdate(),102),6,2)+ substring(convert(varchar(20),getdate(),102),9,2)          
	 select @tomorrow= substring(convert(varchar(20),dateadd(day,1,getdate()),102),1,4)           
		  + substring(convert(varchar(20),dateadd(day,1,getdate()),102),6,2)          
		  + substring(convert(varchar(20),dateadd(day,1,getdate()),102),9,2)          
	 select @datetoplus= substring(convert(varchar(20),dateadd(day,1,@dateto),102),1,4)             
		  + substring(convert(varchar(20),dateadd(day,1,@dateto),102),6,2)            
		  + substring(convert(varchar(20),dateadd(day,1,@dateto),102),9,2)            
	           
	 if(@datefrom = @dateto and @datefrom=@curdate)           
	 begin          
		insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)          
		select manageno,staffno,@curdate,@tomorrow,position,basesalary,resulttye,resultrate,salaryflag          
		from staffinfo with (NOLOCK)          
		where compno=@compid          
	              
	              
	    select  inid,empid=staffno,staffname   
		from #empinfobydate ,staffinfo         
		where isnull(inid,'')<>''  and inid=Manageno
		group by inid,staffno,staffname  
		order by      staffno  
	         
		return          
	 end          
	          
	CREATE TABLE    #staffhistory              
	(
		seqno			int identity    NOT NULL,   --序号
		manageno		varchar(20)      NULL,   --内部管理编号 
		changetype		varchar(20)         NULL,   --异动类型 本店调动  1：跨店调动  2：薪资调整  3:员工离职 4：入职申请  5：重回公司
		oldcompid		varchar(10)         NULL,   --老门店编号 
		oldempid		varchar(20)         NULL,   --老员工编号
		olddepid		varchar(10)			null,	--老部门
		oldpostion		varchar(10)          NULL,   --老门店职位
		oldsalary		float               NULL,   --原工资
		oldyjtype		varchar(5)          NULL,   --原业绩方式
		oldyjrate		float               NULL,   --原业绩系数
		newcompid		varchar(10)         NULL,   --新门店编号 
		newempid		varchar(20)         NULL,   --新员工编号
		newdepid		varchar(10)			null,	--新部门
		newpostion		varchar(10)          NULL,   --新门店职位
		newsalary		float               NULL,   --新工资
		newyjtype		varchar(5)          NULL,   --新业绩方式
		newyjrate		float               NULL,   --新业绩系数
		effectivedate	varchar(8)          NULL,   --实际生效日期
		optionbill		varchar(20)			null,	--单据编号
		CONSTRAINT PK_staffhistory PRIMARY KEY NONCLUSTERED(seqno)
	)        
	  insert into #staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype,oldyjrate,
		newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,effectivedate,optionbill)          
	  select manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype,oldyjrate,
		newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,effectivedate,optionbill
	  from staffhistory with (NOLOCK)          
	  where oldcompid=@compid --and hal19c between @datefrom and @dateto          
		 or (changetype='5' and newcompid=@compid) or (changetype='1' and newcompid=@compid)  
	           
	  --过滤掉“从本店做跨店调动到本店的错误记录”        
	  delete #staffhistory where changetype='1' and newcompid=oldcompid         
	  insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)        
	  select manageno,staffno,@datefrom,@datetoplus,position,basesalary,resulttye,resultrate,salaryflag           
	  from staffinfo with (NOLOCK)          
	  where manageno not in (select manageno from #staffhistory) and compno=@compid         
		and isnull(curstate,'')<>'1' --wjg 2009/10/29 不包括未到职的新员工        


	  declare @originDate varchar(8)          
	  select @originDate = '20090101'--min(hal19c) from #ham12          
	  declare @changeStartDate varchar(8),@lastemp varchar(20),@lasttype varchar(20),@lastnewcompid varchar(10),@lastdatefrom varchar(8)            
	  set @changeStartDate=@originDate--@datefrom          
	  set @lastemp=''          
	  set @lasttype=''          
	  set @lastnewcompid=''        
	  set @lastdatefrom=''        
	  declare @isfirst int         
	  set @isfirst = 1          
	  declare @inid varchar(20),@empid varchar(20),@validdate varchar(8),@position varchar(10),@changetype varchar(20)          
	  declare @salary float,@sharetype varchar(5),@sharerate float,@deducttax int          
	  declare @newcompid varchar(10),@newempid varchar(20),@newposition varchar(10),@newsalary float        
	  declare @newsharetype varchar(5),@newsharerate float,@newdeducttax int          
	           
	  declare cur_staffhistory cursor for           
	  select manageno,changetype,oldempid,effectivedate,oldpostion,oldsalary,oldyjtype,oldyjrate,0,newempid,newpostion,newsalary,newyjtype,newyjrate,0,newcompid          
	  from #staffhistory order by manageno ASC,effectivedate ASC          
	  open cur_staffhistory          
	  fetch from cur_staffhistory into @inid,@changetype,@empid,@validdate,@position,@salary,@sharetype,@sharerate,@deducttax,@newempid,          
		@newposition,@newsalary,@newsharetype,@newsharerate,@newdeducttax,@newcompid          
	  while(@@fetch_status=0)          
	  begin          
	      
	  if(@inid <> @lastemp )          
	  begin          
	   if(@changeStartDate<=@dateto)           
	   begin          
		insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)          
		select manageno,staffno,@changeStartDate,@datetoplus,position,basesalary,resulttye,resultrate,salaryflag           
		from staffinfo WITH (nolock) where manageno=@lastemp and @lasttype in ('0','2','4')--haa34c加索引          
	        
		if((@lasttype='1' and @lastnewcompid=@compid) or (@lasttype='5'))        
		begin        
		 update #empinfobydate set dateto=@datetoplus         
		 where inid=@lastemp and dateto=@changeStartDate and datefrom=@lastdatefrom        
		end        
	            
	   end          
	             
		 set @lastemp = @inid          
		 set @lasttype = @changetype         
		 set @lastnewcompid=@newcompid         
		 --set @originDate = @validdate        
		 if(@changetype='4' or @changetype='5' or (@changetype='1' and @newcompid=@compid))        
		set @changeStartDate = @validdate        
		 else        
		set @changeStartDate = @originDate--@datefrom          
	  end          
	            
	  if(@changetype='5' or (@changetype='1' and @newcompid=@compid))          
	  begin    

		 insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)          
		 values(@inid,@newempid,@validdate,@validdate,@newposition,@newsalary,@newsharetype,@newsharerate,@newdeducttax)         
		 set @lastdatefrom=@validdate         
	  end          
	  else         
	  begin          
		 insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)          
		 values(@inid,@empid,@changeStartDate,@validdate,@position,@salary,@sharetype,@sharerate,@deducttax)        
		 set @lastdatefrom=@changeStartDate          
	  end          
	        
	     
	        
	  set @lastemp = @inid          
	  set @changeStartDate = @validdate          
	  set @lasttype = @changetype          
	  set @lastnewcompid=@newcompid        
	  fetch from cur_staffhistory into @inid,@changetype,@empid,@validdate,@position,@salary,@sharetype,@sharerate,@deducttax,@newempid,@newposition,@newsalary,@newsharetype,@newsharerate,@newdeducttax,@newcompid          
	  end          
		close cur_staffhistory          
	  deallocate cur_staffhistory           
	 --          
	    
	  if(@changeStartDate<=@dateto)           
	  begin          
			insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)          
			select manageno,staffno,@changeStartDate,@datetoplus,position,basesalary,resulttye,resultrate,salaryflag           
			from staffinfo WITH (nolock) where manageno=@lastemp and @lasttype in ('0','2','4')--haa34c加索引  
			        
		           
			if((@lasttype='1' and @lastnewcompid=@compid) or (@lasttype='5'))        
			begin        
				update #empinfobydate set dateto=@datetoplus         
				where inid=@lastemp and dateto=@changeStartDate and datefrom=@lastdatefrom        
			end        
	            
	  end    
	  delete #empinfobydate where  dateto<@datefrom
	  delete #empinfobydate where  datefrom>@dateto
		
	  select  inid,empid=staffno,staffname   
	  from #empinfobydate ,staffinfo         
	  where isnull(inid,'')<>''  and inid=Manageno
	  group by inid,staffno,staffname  
	  order by      staffno  
	           
	  drop table #empinfobydate          
	  drop table #staffhistory           
end
GO
/****** Object:  StoredProcedure [dbo].[upg_hand_personManger_byday]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_hand_personManger_byday]  
(
	@fromdate	varchar(8),                
	@todate		varchar(8)
)
as
begin
	CREATE tAbLE    #staffchangeinfo     
	(
		changecompid			varchar(10)     not null,	--公司别
		changebillid			varchar(20) 	not null,	--申请单号
		changetype				int				not	null,	--申请类型  0-薪资调整 1--离职申请 2--入职申请 3--重回公司申请 4--请假申请,5--本店调动,6--跨店调动
		changestaffno			varchar(20)			null,	--异动前员工编号
		appchangecompid			varchar(10)			null,	--异动前申请公司
		staffpcid				varchar(20)			null,	--员工身份证
		staffphone				varchar(20)			null,   --手机号码
		staffmangerno			varchar(20)			null,   --员工内部编号
		changedate				varchar(8)			null,	--申请日期
		validatestartdate		varchar(8)			null,	--当changetype=0 的时候此值是可以重用的日期 
															--当changetype=1 的时候此值是实际离职日期 
															--当changetype=2 的时候此值是实际入职日期 
															--当changetype=3 的时候此值是实际重回公司日期 
															--当changetype=4 的时候此值是 请假开始日期 
															--当changetype=5 的时候此值是本店调动开始日期 
															--当changetype=6 的时候此值是跨店调动开始日期 
		validateenddate			varchar(8)			null,	--当changetype=4 的时候此值是 请假结束日期 --当fhb02i=3的时候此值回家时间
		beforedepartment		varchar(20)			null,	--异动前部门
		beforepostation			varchar(10)			null,	--异动前职位
		beforesalary			float				null,	--异动前薪资
		beforesalarytype		int					null,	--异动前 0，税前 1 税后  
		beforeyejitype			varchar(5)			null,   --异动前业绩方式 1-美发业绩  2-美容业绩  3-总业绩
		beforeyejirate			float				null,   --异动前业绩系数
		beforeyejiamt			float				null,   --异动前业绩基数
		aftercompid				varchar(20)			null,	--异动后门店
		afterstaffno			varchar(20)			null,	--异动后工号
		afterdepartment			varchar(20)			null,	--异动后部门
		afterpostation			varchar(10)			null,	--异动后职位
		aftersalary				float				null,	--异动后薪资
		aftersalarytype			int					null,	--异动后 0，税前 1 税后  
		afteryejitype			varchar(5)			null,   --异动后业绩方式 1-美发业绩  2-美容业绩  3-总业绩
		afteryejirate			float				null,   --异动后业绩系数
		afteryejiamt			float				null,   --异动后业绩基数

		remark					varchar(200)		null,	--备注
		
	)
	insert #staffchangeinfo(changecompid,changebillid,changetype,changestaffno,appchangecompid,staffpcid,staffphone,staffmangerno,
	changedate,validatestartdate,validateenddate,beforedepartment,beforepostation,beforesalary,beforeyejiamt,beforeyejitype,beforeyejirate,
	aftercompid,afterstaffno,afterdepartment,afterpostation,aftersalary,afteryejiamt,afteryejitype,afteryejirate,remark)
	select changecompid,changebillid,changetype,changestaffno,appchangecompid,staffpcid,staffphone,staffmangerno,
	changedate,validatestartdate,validateenddate,beforedepartment,beforepostation,beforesalary,beforeyejiamt,beforeyejitype,beforeyejirate,
	aftercompid,afterstaffno,afterdepartment,afterpostation,aftersalary,afteryejiamt,afteryejitype,afteryejirate,remark
	from staffchangeinfo where validatestartdate between @fromdate and @todate and billflag=3
	
	declare @changecompid		varchar(10)			--异动门店
	declare @changebillid		varchar(20)			--异动单据
	declare @oldcompid			varchar(10)			--原门店编号                
	declare @oldstaffno			varchar(20)         --原工号     
	declare @olddepartment		varchar(10)			--原部门   
	declare @oldposition		varchar(10)			--原职位     
	declare @oldsalary			float				--原工资  
	declare @oldyejitype		varchar(5)			--异动前业绩方式 1-美发业绩  2-美容业绩  3-总业绩
    declare @oldyejirate		float				--异动前业绩系数
    declare @oldyejiamt			float				--异动前业绩基数    
    
    declare @newcompid			varchar(10)			--新门店编号     
	declare @newstaffno			varchar(20)			--新工号 
	declare @newdepartment		varchar(10)			--新部门编号    
	declare	@newposition		varchar(10)			--新职位   
	declare @newsalary			float				--原工资  
	declare @newyejitype		varchar(5)			--异动前业绩方式 1-美发业绩  2-美容业绩  3-总业绩
    declare @newyejirate		float				--异动前业绩系数
    declare @newyejiamt			float				--异动前业绩基数    
    declare @leaveltype			int					--离职类型  1 正常离职 2 自动离职
	declare @note				varchar(80)			--备注       
	declare @billtype			int					--单据类型  0-薪资调整 1--离职申请 2--入职申请 3--重回公司申请 4--请假申请,5--本店调动,6--跨店调动
	declare @validDate			varchar(8)          --生效日期
	declare @inlineno			varchar(20)			--员工内部编号
	
	declare cursor_staffchange cursor          
	for    
		select	changecompid,changebillid,changetype,changestaffno,appchangecompid,staffmangerno,validatestartdate,beforedepartment,beforepostation,beforesalary,beforeyejitype,beforeyejirate,afteryejiamt,
				aftercompid,afterstaffno,afterdepartment,afterpostation,aftersalary,afteryejitype,afteryejirate,@newyejiamt,remark
		from #staffchangeinfo
	open cursor_staffchange
	fetch cursor_staffchange into @changecompid,@changebillid,@billtype, @oldstaffno,@oldcompid,@inlineno,@validDate,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,
				@newcompid,@newstaffno ,@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@note
	while @@fetch_status=0                
	begin  
			--保留历史算法 0本店调动  1：跨店调动  2：薪资调整  3:员工离职 4：入职申请  5：重回公司
			if(@billtype = 2)--入职申请               
			begin      
				insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)        
				values(@inlineno,4,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,
				@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@validDate,@changebillid,@note)
				
				update staffinfo set curstate='2',arrivaldate=@validDate where manageno=@inlineno
				update staffchangeinfo set billflag=8  where changecompid=@changecompid and changebillid=@changebillid 
				
		                 
			end    
			else if(@billtype = 1) --离职申请               
			begin          
				insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)        
				values(@inlineno,3,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,'99999',@inlineno+'LZ',
										@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@validDate,@changebillid,@note)

				update staffinfo set curstate='3',compno='99999',staffno=@inlineno+'LZ',leavedate=@validDate where manageno=@inlineno
				---------------------------------------------------------------      
				delete sysuserinfo where   frominnerno=@inlineno 
				update staffchangeinfo set billflag=8  where changecompid=@changecompid and changebillid=@changebillid          
			end   
			else if(@billtype = 3) --重回公司
			begin
				insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)        
				values(@inlineno,5,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,
										@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@validDate,@changebillid,@note)

				update staffinfo set curstate='2',compno=@newcompid,staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,
									 resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt
				 where manageno=@inlineno
				---------------------------------------------------------------      
				
				update staffchangeinfo set billflag=8  where changecompid=@changecompid and changebillid=@changebillid      
			end
			else if(@billtype = 5) --本店调动
			begin
				insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)        
				values(@inlineno,0,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,
										@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@validDate,@changebillid,@note)

				update staffinfo set staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,
									 resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt
				 where manageno=@inlineno
				---------------------------------------------------------------      
				
				update staffchangeinfo set billflag=8  where changecompid=@changecompid and changebillid=@changebillid    
			end
			else if(@billtype = 6) --本店调动
			begin
				insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)        
				values(@inlineno,1,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,
										@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@validDate,@changebillid,@note)

				update staffinfo set compno=@newcompid,staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,
									 resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt
				 where manageno=@inlineno
				---------------------------------------------------------------      
				
				update staffchangeinfo set billflag=8  where changecompid=@changecompid and changebillid=@changebillid    
			end      
		
		fetch	cursor_staffchange into @changecompid,@changebillid,@billtype, @oldstaffno,@oldcompid,@inlineno,@validDate,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,
				@newcompid,@newstaffno ,@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@note
	end                
	close cursor_staffchange                
	deallocate cursor_staffchange       
	drop table #staffchangeinfo
end
GO
/****** Object:  StoredProcedure [dbo].[upg_hand_personManger_anon]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_hand_personManger_anon]  
(
	@compid		varchar(10),                 
	@billNo		varchar(20)   
)
as
begin
	--当前日期
	declare @curdate varchar(10)    
	select @curdate=substring(convert(varchar(20),getdate(),102),1,4) + substring(convert(varchar(20),getdate(),102),6,2)+ substring(convert(varchar(20),getdate(),102),9,2)            
	
	declare @oldcompid			varchar(10)			--原门店编号                
	declare @oldstaffno			varchar(20)         --原工号     
	declare @olddepartment		varchar(10)			--原部门   
	declare @oldposition		varchar(10)			--原职位     
	declare @oldsalary			float				--原工资  
	declare @oldyejitype		varchar(5)			--异动前业绩方式 1-美发业绩  2-美容业绩  3-总业绩
    declare @oldyejirate		float				--异动前业绩系数
    declare @oldyejiamt			float				--异动前业绩基数    
    
    declare @newcompid			varchar(10)			--新门店编号     
	declare @newstaffno			varchar(20)			--新工号 
	declare @newdepartment		varchar(10)			--新部门编号    
	declare	@newposition		varchar(10)			--新职位   
	declare @newsalary			float				--原工资  
	declare @newyejitype		varchar(5)			--异动前业绩方式 1-美发业绩  2-美容业绩  3-总业绩
    declare @newyejirate		float				--异动前业绩系数
    declare @newyejiamt			float				--异动前业绩基数    
    declare @leaveltype			int					--离职类型  1 正常离职 2 自动离职
	declare @note				varchar(80)			--备注       
	declare @billtype			int					--单据类型  0-薪资调整 1--离职申请 2--入职申请 3--重回公司申请 4--请假申请,5--本店调动,6--跨店调动
	declare @validDate			varchar(8)          --生效日期
	declare @inlineno			varchar(20)			--员工内部编号    
	declare @changemark			varchar(200)			--员工内部编号    
	
	
	select @oldcompid=appchangecompid, @oldstaffno=changestaffno,@olddepartment=beforedepartment,@oldposition=beforepostation,@oldsalary=beforesalary,
	       @oldyejitype=beforeyejitype,@oldyejirate=beforeyejirate,@oldyejiamt=beforeyejiamt,@newstaffno=afterstaffno,@newcompid=aftercompid,
	       @newdepartment=afterdepartment,@newposition=afterpostation,@newsalary=aftersalary,@newyejitype=afteryejitype,@newyejirate=afteryejirate,
	       @newyejiamt=afteryejiamt,@leaveltype=leaveltype,@note=remark,@validDate=validatestartdate,@inlineno=staffmangerno,@billtype=changetype,@changemark=remark
	from staffchangeinfo where changecompid=@compid and changebillid=@billNo and billflag=3
	
	--保留历史算法 0本店调动  1：跨店调动  2：薪资调整  3:员工离职 4：入职申请  5：重回公司
	if(@billtype = 2)--入职申请               
	begin      
		insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)        
		values(@inlineno,4,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,
		@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@curdate,@billNo,@changemark)
		
		update staffinfo set curstate='2',arrivaldate=@curdate where manageno=@inlineno
		update staffchangeinfo set billflag=8  where changecompid=@compid and changebillid=@billNo 
		
                 
    end    
    else if(@billtype = 1) --离职申请               
    begin          
		insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)        
		values(@inlineno,3,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,'99999',@inlineno+'LZ',
								@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@curdate,@billNo,@changemark)

		update staffinfo set curstate='3',compno='99999',staffno=@inlineno+'LZ',leavedate=@curdate where manageno=@inlineno
		---------------------------------------------------------------      
		delete sysuserinfo where   frominnerno=@inlineno 
        update staffchangeinfo set billflag=8  where changecompid=@compid and changebillid=@billNo          
	end   
	else if(@billtype = 3) --重回公司
	begin
		insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)        
		values(@inlineno,5,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,
								@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@curdate,@billNo,@changemark)

		update staffinfo set curstate='2',compno=@newcompid,staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,
		                     resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt
		 where manageno=@inlineno
		---------------------------------------------------------------      
		
        update staffchangeinfo set billflag=8  where changecompid=@compid and changebillid=@billNo      
	end
	else if(@billtype = 5) --本店调动
	begin
		insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)        
		values(@inlineno,0,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,
								@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@curdate,@billNo,@changemark)

		update staffinfo set staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,
		                     resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt
		 where manageno=@inlineno
		---------------------------------------------------------------      
		
        update staffchangeinfo set billflag=8  where changecompid=@compid and changebillid=@billNo    
	end
	else if(@billtype = 6) --本店调动
	begin
		insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)        
		values(@inlineno,1,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,
								@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@curdate,@billNo,@changemark)

		update staffinfo set compno=@newcompid,staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,
		                     resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt
		 where manageno=@inlineno
		---------------------------------------------------------------      
		
        update staffchangeinfo set billflag=8  where changecompid=@compid and changebillid=@billNo    
	end      
              
end
GO
/****** Object:  StoredProcedure [dbo].[upg_get_empinfo_by_date_comps]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_get_empinfo_by_date_comps]          
(          
  @compid varchar(10),          
  @datefrom varchar(8),          
  @dateto varchar(8)          
)          
as          
begin          
 if(isnull(@datefrom,'')='') return;          
 create table #empinfobydate(          
    seqno int identity not null,          
    inid varchar(20) null,          
    empid varchar(20) null,          
    datefrom varchar(8) null,          
    dateto  varchar(8) null,          
    position varchar(10) null,          
    salary  float null,          
    sharetype varchar(5) null,          
    sharerate float null,          
    deducttax int null,           
 )          
 declare @curdate varchar(8)          
 declare @tomorrow varchar(8)          
 declare @datetoplus varchar(8)           
 select @curdate=substring(convert(varchar(20),getdate(),102),1,4) + substring(convert(varchar(20),getdate(),102),6,2)+ substring(convert(varchar(20),getdate(),102),9,2)          
 select @tomorrow= substring(convert(varchar(20),dateadd(day,1,getdate()),102),1,4)           
      + substring(convert(varchar(20),dateadd(day,1,getdate()),102),6,2)          
      + substring(convert(varchar(20),dateadd(day,1,getdate()),102),9,2)          
 select @datetoplus= substring(convert(varchar(20),dateadd(day,1,@dateto),102),1,4)             
      + substring(convert(varchar(20),dateadd(day,1,@dateto),102),6,2)            
      + substring(convert(varchar(20),dateadd(day,1,@dateto),102),9,2)            
           
 if(@datefrom = @dateto and @datefrom=@curdate)           
 begin          
    insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)          
    select manageno,staffno,@curdate,@tomorrow,position,basesalary,resulttye,resultrate,salaryflag          
    from staffinfo with (NOLOCK)          
    where compno=@compid          
              
    select @compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax          
    from #empinfobydate          
    return          
 end          
          
CREATE TABLE    #staffhistory              
(
	seqno			int identity    NOT NULL,   --序号
	manageno		varchar(20)      NULL,   --内部管理编号 
    changetype		varchar(20)         NULL,   --异动类型 0本店调动  1：跨店调动  2：薪资调整  3:员工离职 4：入职申请  5：重回公司
	oldcompid		varchar(10)         NULL,   --老门店编号 
	oldempid		varchar(20)         NULL,   --老员工编号
	olddepid		varchar(10)			null,	--老部门
	oldpostion		varchar(10)          NULL,   --老门店职位
    oldsalary		float               NULL,   --原工资
    oldyjtype		varchar(5)          NULL,   --原业绩方式
    oldyjrate		float               NULL,   --原业绩系数
	newcompid		varchar(10)         NULL,   --新门店编号 
	newempid		varchar(20)         NULL,   --新员工编号
	newdepid		varchar(10)			null,	--新部门
	newpostion		varchar(10)          NULL,   --新门店职位
    newsalary		float               NULL,   --新工资
    newyjtype		varchar(5)          NULL,   --新业绩方式
    newyjrate		float               NULL,   --新业绩系数
    effectivedate	varchar(8)          NULL,   --实际生效日期
	optionbill		varchar(20)			null,	--单据编号
)        
  insert into #staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype,oldyjrate,
	newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,effectivedate,optionbill)          
  select manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype,oldyjrate,
	newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,effectivedate,optionbill
  from staffhistory with (NOLOCK)          
  where oldcompid=@compid --and hal19c between @datefrom and @dateto          
     or (changetype='5' and newcompid=@compid) or (changetype='1' and newcompid=@compid)  
           
  --过滤掉“从本店做跨店调动到本店的错误记录”        
  delete #staffhistory where changetype='1' and newcompid=oldcompid         
  insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)        
  select manageno,staffno,@datefrom,@datetoplus,position,basesalary,resulttye,resultrate,salaryflag           
  from staffinfo with (NOLOCK)          
  where manageno not in (select manageno from #staffhistory) and compno=@compid         
    and isnull(curstate,'')<>'1' --wjg 2009/10/29 不包括未到职的新员工        


  declare @originDate varchar(8)          
  select @originDate = '20090101'--min(hal19c) from #ham12          
  declare @changeStartDate varchar(8),@lastemp varchar(20),@lasttype varchar(20),@lastnewcompid varchar(10),@lastdatefrom varchar(8)            
  set @changeStartDate=@originDate--@datefrom          
  set @lastemp=''          
  set @lasttype=''          
  set @lastnewcompid=''        
  set @lastdatefrom=''        
  declare @isfirst int         
  set @isfirst = 1          
  declare @inid varchar(20),@empid varchar(20),@validdate varchar(8),@position varchar(10),@changetype varchar(20)          
  declare @salary float,@sharetype varchar(5),@sharerate float,@deducttax int          
  declare @newcompid varchar(10),@newempid varchar(20),@newposition varchar(10),@newsalary float        
  declare @newsharetype varchar(5),@newsharerate float,@newdeducttax int          
           
  declare cur_staffhistory cursor for           
  select manageno,changetype,oldempid,effectivedate,oldpostion,oldsalary,oldyjtype,oldyjrate,0,newempid,newpostion,newsalary,newyjtype,newyjrate,0,newcompid          
  from #staffhistory order by manageno ASC,effectivedate ASC          
  open cur_staffhistory          
  fetch from cur_staffhistory into @inid,@changetype,@empid,@validdate,@position,@salary,@sharetype,@sharerate,@deducttax,@newempid,          
    @newposition,@newsalary,@newsharetype,@newsharerate,@newdeducttax,@newcompid          
  while(@@fetch_status=0)          
  begin          
      
  if(@inid <> @lastemp )          
  begin          
   if(@changeStartDate<=@dateto)           
   begin          
    insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)          
    select manageno,staffno,@changeStartDate,@datetoplus,position,basesalary,resulttye,resultrate,salaryflag           
    from staffinfo WITH (nolock) where manageno=@lastemp and @lasttype in ('0','2','4')--haa34c加索引          
        
    if((@lasttype='1' and @lastnewcompid=@compid) or (@lasttype='5'))        
    begin        
     update #empinfobydate set dateto=@datetoplus         
     where inid=@lastemp and dateto=@changeStartDate and datefrom=@lastdatefrom        
    end        
            
   end          
             
     set @lastemp = @inid          
     set @lasttype = @changetype         
     set @lastnewcompid=@newcompid         
     --set @originDate = @validdate        
     if(@changetype='4' or @changetype='5' or (@changetype='1' and @newcompid=@compid))        
    set @changeStartDate = @validdate        
     else        
    set @changeStartDate = @originDate--@datefrom          
  end          
            
  if(@changetype='5' or (@changetype='1' and @newcompid=@compid))          
  begin    

     insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)          
     values(@inid,@newempid,@validdate,@validdate,@newposition,@newsalary,@newsharetype,@newsharerate,@newdeducttax)         
     set @lastdatefrom=@validdate         
  end          
  else         
  begin          
     insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)          
     values(@inid,@empid,@changeStartDate,@validdate,@position,@salary,@sharetype,@sharerate,@deducttax)        
     set @lastdatefrom=@changeStartDate          
  end          
        
     
        
  set @lastemp = @inid          
  set @changeStartDate = @validdate          
  set @lasttype = @changetype          
  set @lastnewcompid=@newcompid        
  fetch from cur_staffhistory into @inid,@changetype,@empid,@validdate,@position,@salary,@sharetype,@sharerate,@deducttax,@newempid,@newposition,@newsalary,@newsharetype,@newsharerate,@newdeducttax,@newcompid          
  end          
	close cur_staffhistory          
  deallocate cur_staffhistory           
 --          
    
  if(@changeStartDate<=@dateto)           
  begin          
		insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)          
		select manageno,staffno,@changeStartDate,@datetoplus,position,basesalary,resulttye,resultrate,salaryflag           
		from staffinfo WITH (nolock) where manageno=@lastemp and @lasttype in ('0','2','4')--haa34c加索引  
		        
	           
		if((@lasttype='1' and @lastnewcompid=@compid) or (@lasttype='5'))        
		begin        
			update #empinfobydate set dateto=@datetoplus         
			where inid=@lastemp and dateto=@changeStartDate and datefrom=@lastdatefrom        
		end        
            
  end          
          
     
  select @compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax          
  from #empinfobydate          
  where isnull(inid,'')<>''   order by      empid  
           
  drop table #empinfobydate          
  drop table #staffhistory          
end
GO
/****** Object:  StoredProcedure [dbo].[upg_create_trade_dailybillcount]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_create_trade_dailybillcount]    
(      
	@compid   varchar(10),      
	@datefrom varchar(8),      
	@dateto   varchar(8)      
)      
as      
begin      
	 create table #result(      
		compid					varchar(10) null,
		compidname				varchar(50) null,        
		cash_bill_amt			float		null,      
		cash_bill_num			int			null,      
		cash_bill_price			float		null,      
		card_bill_amt			float		null,      
		card_bill_num			int			null,      
		card_bill_price			float		null,      
		bill_amt				float		null,      
		bill_num				int			null,      
		bill_price				float		null,    
		mrbigprj_num			int			null,    
		mfbigprj_num			int			null    
	 )      

	CREATE TABLE    #dpayinfo_billcount               --  单据--支付明细
	(
		paycompid		varchar(10)      NULL,   --公司编号
		paybillid		varchar(20)      NULL,   --单据编号
		paymode			varchar(5)		 NULL,   --支付方式
		payamt			float			 NULL,   --支付金额
	) 
	create clustered index idx_dpayinfo on #dpayinfo_billcount(paycompid,paymode)      
      
	insert into #dpayinfo_billcount(paycompid,paybillid,paymode,payamt)      
	select paycompid,paybillid,paymode,sum(isnull(payamt,0))
	from mconsumeinfo with(nolock),dpayinfo with(nolock),compchaininfo
	where cscompid=paycompid and csbillid=paybillid and paybilltype='SY' 
	  and cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto
	group by paycompid,paybillid,paymode,financedate  
      

	
 insert into #result(compid,compidname)      
 select relationcomp,compname from compchaininfo,companyinfo where curcomp=@compid  and compno=relationcomp     
      
 create table #temp_result_for_amt(compid varchar(10) not null, flag int null, amt float null)      
 create table #temp_result_for_num(compid varchar(10) not null, flag int null, num int null)      
      
 insert into #temp_result_for_amt(compid,flag,amt)       
 select paycompid,1,isnull(sum(payamt),0) from #dpayinfo_billcount where isnull(paymode,'') in ('1','2','6','14','15') group by paycompid      
       
 insert into #temp_result_for_amt(compid,flag,amt)       
 select paycompid,2,isnull(sum(payamt),0) from #dpayinfo_billcount where isnull(paymode,'') in ('4','A','9') group by paycompid      
      
 insert into #temp_result_for_amt(compid,flag,amt)       
 select paycompid,3,isnull(sum(payamt),0) from #dpayinfo_billcount group by paycompid      
      
 create table #distinct_bill(compid varchar(10) not null,flag int null,billid varchar(20))      
 insert into #distinct_bill(compid,flag,billid)       
 select distinct paycompid,1,paybillid from #dpayinfo_billcount where isnull(paymode,'') in ('1','2','6','14','15')       
       
 insert into #distinct_bill(compid,flag,billid)       
 select distinct paycompid,2,paybillid from #dpayinfo_billcount where isnull(paymode,'') in ('4','A','9')       
      
 insert into #distinct_bill(compid,flag,billid)       
 select distinct paycompid,3,paybillid from #dpayinfo_billcount        
       
       
       
 insert into #temp_result_for_num(compid,flag,num)       
 select compid,1,count(*) from #distinct_bill where flag=1 group by compid      
      
 insert into #temp_result_for_num(compid,flag,num)       
 select compid,2,count(*) from #distinct_bill where flag=2 group by compid      
      
 insert into #temp_result_for_num(compid,flag,num)       
 select compid,3,count(*) from #distinct_bill where flag=3 group by compid      
      
 update #result set cash_bill_amt = isnull(b.amt,0)      
 from #result a,#temp_result_for_amt b      
 where a.compid = b.compid and b.flag=1      
      
 update #result set cash_bill_num = isnull(b.num,0)      
 from #result a,#temp_result_for_num b      
 where a.compid = b.compid and b.flag=1      
      
 update #result set card_bill_amt = isnull(b.amt,0)      
 from #result a,#temp_result_for_amt b      
 where a.compid = b.compid and b.flag=2      
      
 update #result set card_bill_num = isnull(b.num,0)      
 from #result a,#temp_result_for_num b      
 where a.compid = b.compid and b.flag=2      
      
 update #result set bill_amt = isnull(b.amt,0)      
 from #result a,#temp_result_for_amt b      
 where a.compid = b.compid and b.flag=3      
      
 update #result set bill_num = isnull(b.num,0)      
 from #result a,#temp_result_for_num b      
 where a.compid = b.compid and b.flag=3      
       
 update #result set cash_bill_price = isnull(cash_bill_amt,0)/cash_bill_num where isnull(cash_bill_num,0)<>0      
 update #result set card_bill_price = isnull(card_bill_amt,0)/card_bill_num where isnull(card_bill_num,0)<>0      
 update #result set bill_price = isnull(bill_amt,0)/bill_num where isnull(bill_num,0)<>0      
  
  
	CREATE tAbLE    #dconsumeinfo  
	(
		cscompid		varchar(10)     Not NULL,   --公司编号
		csbillid		varchar(20)     Not NULL,   --单据编号
		csitemno		varchar(20)     NULL,		--项目/产品代码
		cspaymode		varchar(5)		NULL,		--支付方式	
	)
	insert #dconsumeinfo(cscompid,csbillid,csitemno,cspaymode)
	select b.cscompid,b.csbillid,csitemno,cspaymode
	from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),compchaininfo
	where a.cscompid=b.cscompid and a.csbillid=b.csbillid and b.csinfotype='1' and ISNULL(backcsflag,0)=0 and ISNULL(backcsbillid,'')=''
	  and a.cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto
  

--美容客单数    mrbigprj_num                 
   update a set mrbigprj_num=convert(numeric(20,2),b.beatycostcount)                          
   from #result a  ,                          
  (select cscompid,beatycostcount=COUNT( distinct csbillid)  from #dconsumeinfo, projectnameinfo c  with(nolock)                     
    where csitemno=prjno  and prjtype='4' and ISNULL(prjpricetype,0)=1              
    group by cscompid ) b                          
   where a.compid=b.cscompid        
                         
                       
   --美发客单数    mfbigprj_num                
   update a set a.mfbigprj_num=convert(numeric(20,2),b.haircostcount)                          
   from #result a  ,                          
  (select cscompid,haircostcount=COUNT( distinct csbillid)  from #dconsumeinfo, projectnameinfo c  with(nolock)                     
    where  csitemno=prjno  and prjtype='3' and ISNULL(prjpricetype,0)=1              
    group by cscompid ) b                          
   where a.compid=b.cscompid            
   
  select compid,compidname,cash_bill_amt,cash_bill_num,cash_bill_price,card_bill_amt,card_bill_num,card_bill_price,bill_amt,bill_num,bill_price,mrbigprj_num,mfbigprj_num
  from   #result ,compchainstruct where compid=curcompno and complevel=4
 drop table #result     
 drop table #dconsumeinfo 
 drop table #dpayinfo_billcount      
 drop table #temp_result_for_amt      
 drop table #temp_result_for_num      
 drop table #distinct_bill      
end
GO
/****** Object:  StoredProcedure [dbo].[upg_Confirm_CardChangeCard]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_Confirm_CardChangeCard]    
(    
 @compid			varchar(10),		--门店号	
 @billid			varchar(20),		--单号
 @changedate		varchar(8),			--日期
 @oldcardno			varchar(20),		--老卡号
 @newcardno			varchar(20),		--新卡号  
 @changetype		varchar(20)			--卡变更类型 0 折扣转卡 1 收购转卡 2竞争转卡 3换卡 4挂失卡 5补卡 6老卡并老卡 7老卡并新卡
)    
as   
begin
	declare @saleaccountseqno		float
	declare @curfillamt				float		--充值金额
	declare	@curdebtamt				float		--欠款金额
	declare @membername				varchar(20)	--会员姓名		
	declare @membertphone			varchar(20)	--会员手机		
	declare @oldaccfillamt			float		--原卡账户金额
	declare	@oldaccdebtamt			float		--原卡账户欠款金额
	declare @oldprofillamt			float		--原卡疗程账户金额
	declare	@oldprodebtamt			float		--原卡疗程账户欠款金额
	declare @prodetialseqno 		float		--疗程明细序号
	declare @costaccountseqno 		float		--账户历史序号
	declare @costaccount2lastamt	float		--账户历史最后余额
	declare @costaccount4lastamt	float		--账户历史最后余额
	declare @SP042					varchar(2)	--是否启用卡变更欠款累加 0:禁用 1:启用
	select @SP042=paramvalue from sysparaminfo where compid=@compid and paramid='SP042'
	select	@curfillamt=ISNULL(changefillamt,0),@curdebtamt=ISNULL(changdebtamt,0),
				@oldaccfillamt=isnull(curaccountkeepamt,0),@oldaccdebtamt=isnull(curaccountdebtamt,0),
				@membername=ISNULL(membername,''),@membertphone=ISNULL(memberphone,'') from  mcardchangeinfo where changecompid=@compid and changebillid=@billid
	
	if(@changetype=5)
	begin
		update  cardinfo set cardstate=11 where cardno=@oldcardno  --已补卡状态
		--新增异动历史

		select @saleaccountseqno=MAX(changeseqno)+1 from cardchangehistory where changecardno=@newcardno 
		insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
		values(@compid,@newcardno,isnull(@saleaccountseqno,0),7,@billid,3,4,@changedate,@oldcardno)
		select @saleaccountseqno=MAX(changeseqno)+1 from cardchangehistory where changecardno=@oldcardno 
		insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
		values(@compid,@oldcardno,isnull(@saleaccountseqno,0),7,@billid,9,11,@changedate,@newcardno)
	end
	else if(@changetype=3)
	begin
		update  cardinfo set cardstate=13 where cardno=@oldcardno  --已换卡状态
		--新增异动历史
		select @saleaccountseqno=MAX(changeseqno)+1 from cardchangehistory where changecardno=@newcardno 
		insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
		values(@compid,@newcardno,isnull(@saleaccountseqno,0),13,@billid,3,4,@changedate,@oldcardno)
		select @saleaccountseqno=MAX(changeseqno)+1 from cardchangehistory where changecardno=@oldcardno 
		insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
		values(@compid,@oldcardno,isnull(@saleaccountseqno,0),13,@billid,9,13,@changedate,@newcardno)
	end
	else if(@changetype in (0,1,2))
	begin
		update  cardinfo set cardstate=6 where cardno=@oldcardno   --已转卡状态
			--新增异动历史
		select @saleaccountseqno=MAX(changeseqno)+1 from cardchangehistory where changecardno=@newcardno 
		insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
		values(@compid,@newcardno,isnull(@saleaccountseqno,0),11,@billid,1,4,@changedate,@oldcardno)
		select @saleaccountseqno=MAX(changeseqno)+1 from cardchangehistory where changecardno=@oldcardno 
		insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
		values(@compid,@oldcardno,isnull(@saleaccountseqno,0),11,@billid,4,6,@changedate,@newcardno)
	end
	else if(@changetype=8)
	begin
		update mcardchangeinfo set firstsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and firstsalerid=staffno and compno=@compid
		update mcardchangeinfo set secondsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and secondsalerid=staffno and compno=@compid
		update mcardchangeinfo set thirdsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and thirdsalerid=staffno and compno=@compid
		update mcardchangeinfo set fourthsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and fourthsalerid=staffno and compno=@compid
		update mcardchangeinfo set fifthsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and fifthsalerid=staffno and compno=@compid
		update mcardchangeinfo set sixthsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and sixthsalerid=staffno and compno=@compid
		update mcardchangeinfo set seventhsalerinid=manageno from mcardchangeinfo,staffinfo where changecompid=@compid and changebillid=@billid and seventhsalerid=staffno and compno=@compid
		update mcardchangeinfo set eighthsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and eighthsalerid=staffno and compno=@compid
	
	
		update  cardinfo set cardstate=7 where cardno=@oldcardno   --已退卡状态
			--新增异动历史
		select @saleaccountseqno=MAX(changeseqno)+1 from cardchangehistory where changecardno=@oldcardno 
		insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
		values(@compid,@oldcardno,isnull(@saleaccountseqno,0),8,@billid,4,7,@changedate,'')
		
		--清空账户
		update 	 cardaccount set accountbalance=0,accountdebts=0 where cardno=@oldcardno 
		--增加账户历史
		select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@oldcardno 
		select top 1 @costaccount2lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@oldcardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc 
		select top 1 @costaccount4lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@oldcardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc 
		
		insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
		values(@compid,@oldcardno,2,isnull(@costaccountseqno,0),1,@oldaccfillamt,'TK',@billid,@changedate,@costaccount2lastamt)
		set @costaccountseqno=isnull(@costaccountseqno,0)+1
		insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
		values(@compid,@oldcardno,4,isnull(@costaccountseqno,0),1,@oldprofillamt,'TK',@billid,@changedate,@costaccount4lastamt)
		--清空疗程明细
		update  cardproaccount set lastcount=0,lastamt=0 where cardno=@oldcardno
			
	end
	if(@changetype in (0,1,2,3,5,6,7))
	begin 
		update mcardchangeinfo set firstsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and firstsalerid=staffno and compno=@compid
		update mcardchangeinfo set secondsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and secondsalerid=staffno and compno=@compid
		update mcardchangeinfo set thirdsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and thirdsalerid=staffno and compno=@compid
		update mcardchangeinfo set fourthsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and fourthsalerid=staffno and compno=@compid
		update mcardchangeinfo set fifthsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and fifthsalerid=staffno and compno=@compid
		update mcardchangeinfo set sixthsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and sixthsalerid=staffno and compno=@compid
		update mcardchangeinfo set seventhsalerinid=manageno from mcardchangeinfo,staffinfo where changecompid=@compid and changebillid=@billid and seventhsalerid=staffno and compno=@compid
		update mcardchangeinfo set eighthsalerinid=manageno from mcardchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and eighthsalerid=staffno and compno=@compid
	
		if(@changetype in (0,1,2,3,5))  --转卡换卡补卡 变更卡信息
		begin
			update  cardinfo set cardstate=4 where cardno=@newcardno  --正常开卡状态
			update cardsoninfo set parentcardno=@newcardno where parentcardno=@oldcardno
			if(@changetype=2) --竞争转卡
			begin
				--更新新卡储值账户
				if not exists(select 1 from cardaccount where   cardno = @newcardno and accounttype=2)      
				begin      
					insert into cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts)       
					values(@compid,@newcardno,2,ISNULL(@oldaccfillamt,0)+ISNULL(@curfillamt,0),ISNULL(@oldaccdebtamt,0))      
				end    
				else
				begin
					update cardaccount set accountbalance=ISNULL(accountbalance,0)+ISNULL(@oldaccfillamt,0)+ISNULL(@curfillamt,0),accountdebts=ISNULL(accountdebts,0)+ISNULL(@oldaccdebtamt,0) where cardno=@newcardno and accounttype=2
				end
			
				insert memberinfo(membervesting,memberno,membername,membertphone,cardnotomemberno)
				values( @compid,@newcardno,@membername,@membertphone,@newcardno)
				if(ISNULL(@oldaccfillamt,0)>0) --储值账户
				begin
					--生成账户历史
					select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@newcardno 
					select top 1 @costaccount2lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@newcardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc 
					insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
					values(@compid,@newcardno,2,isnull(@costaccountseqno,0),ISNULL(@changetype,0)+7,ISNULL(@oldaccfillamt,0)+ISNULL(@curfillamt,0),'ZK',@billid,@changedate,@costaccount2lastamt)
					set @costaccountseqno=@costaccountseqno+1
				end
			end
			else
			begin
				if(@changetype=1) --收购转卡
				begin
				    delete cardaccount where  cardno=@oldcardno and accounttype=2
					update cardaccount set accounttype=2 where cardno=@oldcardno and accounttype=5
					
				end
				delete memberinfo where memberno=@newcardno
				update memberinfo set memberno=@newcardno,cardnotomemberno=@newcardno where memberno=@oldcardno
				delete cardaccount where cardno=@newcardno
				update cardaccount set cardno=@newcardno where cardno=@oldcardno
				update cardaccount set accountbalance=ISNULL(accountbalance,0)+ISNULL(@curfillamt,0),accountdebts=ISNULL(accountdebts,0)+ISNULL(@curdebtamt,0) where cardno=@newcardno and accounttype=2
				update cardproaccount set cardno=@newcardno where cardno=@oldcardno
				update cardaccountchangehistory set changecardno=@newcardno where changecardno=@oldcardno
				update cardtransactionhistory set transactioncardno=@newcardno where transactioncardno=@oldcardno
			
				if(ISNULL(@curfillamt,0)>0) --储值账户
				begin
					--生成账户历史
					select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@newcardno 
					select top 1 @costaccount2lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@newcardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc 
					if(@changetype=1)
						set @costaccount2lastamt=ISNULL(@oldaccfillamt,0)
					insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
					values(@compid,@newcardno,2,isnull(@costaccountseqno,0),ISNULL(@changetype,0)+7,@curfillamt,'ZK',@billid,@changedate,@costaccount2lastamt)
					set @costaccountseqno=@costaccountseqno+1
				end
			end
		end
		else if(@changetype in (6,7))  
		begin
			if(@changetype =6)--老卡并老卡 @oldcardno 为目标卡
			begin
				update  cardinfo set cardstate=12 from dcardchangeinfo,cardinfo where cardno=oldcardno and changecompid=@compid and changebillid=@billid  --已补卡状态
				--新增异动历史
				insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
				select @compid,oldcardno,99,12,@billid,4,12,@changedate,@oldcardno from dcardchangeinfo where changecompid=@compid and changebillid=@billid  --已补卡状态
			end
			else if(@changetype =7)--老卡并新卡 @oldcardno 为目标卡
			begin
				update  cardinfo set cardstate=12 from dcardchangeinfo,cardinfo where cardno=oldcardno and changecompid=@compid and changebillid=@billid  --已补卡状态
				update  cardinfo set cardstate=4 where cardno=@oldcardno  --正常开卡状态
				--新增异动历史
				select @saleaccountseqno=MAX(changeseqno)+1 from cardchangehistory where changecardno=@oldcardno 
				insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
				values(@compid,@oldcardno,isnull(@saleaccountseqno,0),12,@billid,1,4,@changedate,'')
				insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
				select @compid,oldcardno,99,12,@billid,4,12,@changedate,@oldcardno from dcardchangeinfo where changecompid=@compid and changebillid=@billid  --已补卡状态
			end
			--充值金额+老卡列表原有额度
			select @curfillamt=ISNULL(@curfillamt,0)+SUM(ISNULL(curaccountkeepamt,0)) from  dcardchangeinfo where changecompid=@compid and changebillid=@billid 
			--充值欠款  / 老卡列表原有欠款 @SP042
			if(ISNULL(@SP042,'0')='0') --不累加欠款
			begin
				select @curdebtamt=ISNULL(@curdebtamt,0)+SUM(ISNULL(curaccountdebtamt,0)) from  dcardchangeinfo where changecompid=@compid and changebillid=@billid 
			end
			--老卡列表疗程账户欠款
			select @oldprofillamt=SUM(ISNULL(proaccountkeepamt,0)) from  dcardchangeinfo where changecompid=@compid and changebillid=@billid 
			select @oldprodebtamt=SUM(ISNULL(proaccountdebtamt,0)) from  dcardchangeinfo where changecompid=@compid and changebillid=@billid 
			----------------------------------------更新账户 Start-----------------------------------------------
			--清空老卡账户
			update cardaccount set accountbalance=0,accountdebts=0 from cardaccount,dcardchangeinfo where changecompid=@compid and changebillid=@billid  and cardno=oldcardno
			--更新新卡储值账户
			if not exists(select 1 from cardaccount where   cardno = @oldcardno and accounttype=2)      
			begin      
				insert into cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts)       
				values(@compid,@oldcardno,2,ISNULL(@curfillamt,0),ISNULL(@curdebtamt,0))      
			end    
			else
			begin
				update cardaccount set accountbalance=ISNULL(accountbalance,0)+ISNULL(@curfillamt,0),accountdebts=ISNULL(accountdebts,0)+ISNULL(@curdebtamt,0) where cardno=@oldcardno and accounttype=2
			end
			if(ISNULL(@oldprofillamt,0)<>0 or ISNULL(@oldprodebtamt,0)<>0)
			begin
				--更新新卡疗程账户
				if not exists(select 1 from cardaccount where   cardno = @oldcardno and accounttype=4)      
				begin      
					insert into cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts)       
					values(@compid,@oldcardno,4,ISNULL(@oldprofillamt,0),ISNULL(@oldprodebtamt,0))      
				end    
				else
				begin
					update cardaccount set accountbalance=ISNULL(accountbalance,0)+ISNULL(@oldprofillamt,0),accountdebts=ISNULL(accountdebts,0)+ISNULL(@oldprodebtamt,0) where cardno=@oldcardno and accounttype=4
				end
			end
			----------------------------------------更新账户 end-----------------------------------------------
			select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@oldcardno 
			----------------------------------------更新账户历史 Start-----------------------------------------------
			if(ISNULL(@curfillamt,0)>0) --储值账户
			begin
				--生成账户历史
				select top 1 @costaccount2lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@oldcardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc 
				insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
				values(@compid,@oldcardno,2,isnull(@costaccountseqno,0),10,@curfillamt,'ZK',@billid,@changedate,@costaccount2lastamt)
				set @costaccountseqno=isnull(@costaccountseqno,0)+1
			end
			if(ISNULL(@oldprofillamt,0)>0) --疗程账户
			begin
				select top 1 @costaccount2lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@oldcardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc 
				insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
				values(@compid,@oldcardno,4,isnull(@costaccountseqno,0),10,@oldprofillamt,'ZK',@billid,@changedate,@costaccount2lastamt)
				set @costaccountseqno=isnull(@costaccountseqno,0)+1
			end
			----------------------------------------更新账户历史 end-----------------------------------------------
			----------------------------------------合并疗程明细 Start---------------------------------------------
			select @prodetialseqno=MAX(proseqno) from cardproaccount where cardno=@oldcardno
			insert cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,saledate,cutoffdate,proremark,prostopeflag,exchangeseqno)
			select cardvesting,@oldcardno,projectno,isnull(@prodetialseqno,0)+1+row_number() over(order by proseqno desc),propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,saledate,cutoffdate,proremark,prostopeflag,exchangeseqno 
			from cardproaccount a,dcardchangeinfo b where b.changecompid=@compid and b.changebillid=@billid  and cardno=oldcardno
			delete a from cardproaccount a,dcardchangeinfo b where b.changecompid=@compid and b.changebillid=@billid  and cardno=oldcardno
			----------------------------------------合并疗程明细 end-----------------------------------------------
			----------------------------------------合并交易明细 Start---------------------------------------------
			update cardtransactionhistory set transactioncardno=@oldcardno
			from cardtransactionhistory a,dcardchangeinfo b where b.changecompid=@compid and b.changebillid=@billid  and transactioncardno=oldcardno
			
			----------------------------------------合并交易明细 end---------------------------------------------
		end
		
	end
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_goods_stock_text]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_compute_goods_stock_text]   
(        
 @compid   varchar(10),   --会馆编号        
 @date   varchar(10),   --日期         
 @goodsfrom  varchar(20),  --物品编号from         
 @goodsto  varchar(20),   --物品编号to         
 @garagefrom  varchar(20)        --仓库编号from     
    
)        
as    
begin        
     
    --存放入库的数量(进货单位向标准单位转换)           
   create table #inser_stock       
   (        
  id    int identity not null,        
  goodsno   varchar(20)  null,--物品编号        
  goodsname  varchar(80)  null,--物品名称        
  garageno  varchar(20)  null,--仓库编号        
  garagename  varchar(20)  null,--仓库名称        
  goodstype  varchar(20)  null,--产品类型      
  goodstypename varchar(20)  null,--产品类型名称   
  unit   varchar(10)  null,--单位      
  price   float   null,--单价    
  quantity  float   null,--数量            
  amt    float   null,--金额          
  primary key (id),        
   )        
   -- 存放出库、销货、耗用的数（消耗单位向标准单位转换）     
   create table #outser_stock         
   (        
    id    int identity        not null,        
    goodsno   varchar(20)    null,--物品编号        
    goodsname  varchar(80)    null,--物品名称        
    garageno   varchar(20)    null,--仓库编号        
    garagename  varchar(20)    null,--仓库名称        
    unit    varchar(10)    null,--单位        
    quantity   float     null,--数量        
    primary key (id),        
   )        
   insert #inser_stock(goodsno,goodsname,garageno,garagename,unit,quantity)        
   select  changegoodsno,'',changewareid,'','',isnull(sum(changecount),0)       
                from  mgoodsstockinfo a,dgoodsstockinfo b    
                where a.changecompid= b.changecompid         
                  and a.changetype= b.changetype         
                  and a.changebillno= b.changebillno        
                  and a.changetype='1'         
                  and a.changedate<=@date     
                  and a.changecompid= @compid    
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')    
                  and (changewareid = @garagefrom or ISNULL(@garagefrom,'')='')    
       group by changegoodsno,changewareid        
           
           select * from #inser_stock
               
      insert #outser_stock(goodsno,goodsname,garageno,garagename,unit,quantity)        
      select  changegoodsno,'',changewareid,'','',isnull(sum(changecount),0)       
                from  mgoodsstockinfo a,dgoodsstockinfo b    
                where a.changecompid= b.changecompid         
                  and a.changetype= b.changetype         
                  and a.changebillno= b.changebillno        
                  and a.changetype<>'1'         
                  and a.changedate<=@date    
                  and a.changecompid= @compid        
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')    
                  and (changewareid = @garagefrom or ISNULL(@garagefrom,'')='')     
      group by changegoodsno,changewareid      
             select * from #outser_stock
   --更新仓库名称        
   update a        
   set a.garagename =b.warehousename        
   from #inser_stock a,compwarehouse b        
   where a.garageno = b.warehouseno        
  and b.compno = @compid        
            
       
        
   --统计出库(包括销货)的数量更新库存量        
        
  update t        
  set t.quantity = isnull(t.quantity,0)- isnull(a.quantity,0)         
  from #outser_stock a, #inser_stock t        
  where  a.goodsno=t.goodsno         
   and a.garageno=t.garageno             
          
  --更新产品名        
  update a set  a.goodsname=b.goodsname,a.unit=b.saleunit ,  
    a.goodstype=b.goodspricetype ,  
    a.goodstypename=c.parentcodevalue,  
    a.price=b.storesalseprice,  
    a.amt=isnull(a.quantity,0)*isnull(b.storesalseprice,0)  
  from #inser_stock a, goodsinfo b ,commoninfo  c ,compchaininfo   
  where curcomp=goodssource and relationcomp=@compid and a.goodsno = b.goodsno     
  and  b.goodspricetype=c.parentcodekey and c.infotype='WPTJ'  
            
          
     
    select goodsno,goodsname,garageno,garagename,unit,goodstype,goodstypename,quantity,price,amt       
    from #inser_stock    
    order by goodsno       
        
  drop table #inser_stock        
  drop table #outser_stock        
         
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_goods_stock]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_goods_stock]   
(      
 @compid   varchar(10),   --会馆编号      
 @date   varchar(10),   --日期       
 @goodsfrom  varchar(20),  --物品编号from       
 @goodsto  varchar(20),   --物品编号to       
 @garagefrom  varchar(20)        --仓库编号from   
  
)      
as  
begin      
   
    --存放入库的数量(进货单位向标准单位转换)         
   create table #inser_stock     
   (      
		id				int identity	not null,      
		goodsno			varchar(20)		null,--物品编号      
		goodsname		varchar(80)		null,--物品名称      
		garageno		varchar(20)		null,--仓库编号      
		garagename		varchar(20)		null,--仓库名称      
		goodstype		varchar(20)		null,--产品类型    
		goodstypename	varchar(20)		null,--产品类型名称 
		unit			varchar(10)		null,--单位    
		price			float			null,--单价  
		quantity		float			null,--数量          
		amt				float			null,--金额        
		primary key (id),      
   )      
   -- 存放出库、销货、耗用的数（消耗单位向标准单位转换）   
   create table #outser_stock       
   (      
	   id				int identity        not null,      
	   goodsno			varchar(20)				null,--物品编号      
	   goodsname		varchar(80)				null,--物品名称      
	   garageno			varchar(20)				null,--仓库编号      
	   garagename		varchar(20)				null,--仓库名称      
	   unit				varchar(10)				null,--单位      
	   quantity			float					null,--数量      
	   primary key (id),      
   )      
   insert #inser_stock(goodsno,goodsname,garageno,garagename,unit,quantity)      
   select  changegoodsno,'',changewareid,'','',isnull(sum(changecount),0)     
                from  mgoodsstockinfo a,dgoodsstockinfo b  
                where a.changecompid= b.changecompid       
                  and a.changetype= b.changetype       
                  and a.changebillno= b.changebillno      
                  and a.changetype='1'       
                  and a.changedate<=@date   
                  and a.changecompid= @compid  
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')  
                  and (changewareid = @garagefrom or ISNULL(@garagefrom,'')='')  
       group by changegoodsno,changewareid      
         
             
      insert #outser_stock(goodsno,goodsname,garageno,garagename,unit,quantity)      
      select  changegoodsno,'',changewareid,'','',isnull(sum(changecount),0)     
                from  mgoodsstockinfo a,dgoodsstockinfo b  
                where a.changecompid= b.changecompid       
                  and a.changetype= b.changetype       
                  and a.changebillno= b.changebillno      
                  and a.changetype<>'1'       
                  and a.changedate<=@date  
                  and a.changecompid= @compid      
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')  
                  and (changewareid = @garagefrom or ISNULL(@garagefrom,'')='')   
      group by changegoodsno,changewareid    
	  --更新仓库名称      
	  update a      
	  set a.garagename =b.warehousename      
	  from #inser_stock a,compwarehouse b      
	  where a.garageno = b.warehouseno      
		and b.compno = @compid      
          
     
      
   --统计出库(包括销货)的数量更新库存量      
      
  update t      
  set t.quantity = isnull(t.quantity,0)- isnull(a.quantity,0)       
  from #outser_stock a, #inser_stock t      
  where  a.goodsno=t.goodsno       
   and a.garageno=t.garageno           
        
  --更新产品名      
  update a set  a.goodsname=b.goodsname,a.unit=b.saleunit ,
				a.goodstype=b.goodspricetype ,
				a.goodstypename=c.parentcodevalue,
				a.price=b.storesalseprice,
				a.amt=isnull(a.quantity,0)*isnull(b.storesalseprice,0)
  from #inser_stock a, goodsinfo b ,commoninfo  c ,compchaininfo 
  where curcomp=goodssource and relationcomp=@compid and a.goodsno = b.goodsno   
  and  b.goodspricetype=c.parentcodekey and c.infotype='WPTJ'
          
        
   
    select goodsno,goodsname,garageno,garagename,unit,goodstype,goodstypename,quantity,price,amt      
    from #inser_stock  
    order by goodsno     
      
  drop table #inser_stock      
  drop table #outser_stock      
       
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_costoldpersanlysis_by_date_comps]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_compute_costoldpersanlysis_by_date_comps]   
(  
 @compid varchar(10),  
 @fromdate varchar(10),  
 @todate varchar(10),  
 @ccount int   
)  
as  
begin  
	 create table #tbl_emp  
	 (  
	  compid varchar(10) not null,  
	  empno varchar(20) not null  
	 )  
   
	 create table #tbl_ggm02_zj  
	 (  
	  compid varchar(10) not null,  
	  billno varchar(20) not null,  
	  empno varchar(20) null,  
	  srvtype varchar(10) null,  
	  seq  float null  
	 )  
  
	 insert into #tbl_emp(compid,empno)  
	 select compno,manageno  
	 from staffinfo,compchaininfo  
	 where compno=relationcomp and curcomp=@compid  
	   and position in('006','007')  
	   
	
   
	 insert into #tbl_ggm02_zj(compid,billno,empno,srvtype,seq)  
	 select a.cscompid,a.csbillid,csfirstinid,csfirsttype, csseqno 
	 from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),#tbl_emp  
	 where a.cscompid=b.cscompid  
	   and a.csbillid=b.csbillid  
	   and b.cscompid=compid  
	   and csfirstinid=empno  
	   and csfirsttype='1'  
	   and financedate between @fromdate and @todate  
	   and ISNULL(csfirstinid,'')<>'' 
     
  
		insert into #tbl_ggm02_zj(compid,billno,empno,srvtype,seq)  
	 select a.cscompid,a.csbillid,cssecondinid,cssecondtype, csseqno 
	 from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),#tbl_emp  
	 where a.cscompid=b.cscompid  
	   and a.csbillid=b.csbillid  
	   and b.cscompid=compid  
	   and cssecondinid=empno  
	   and cssecondtype='1'  
	   and financedate between @fromdate and @todate  
	   and ISNULL(cssecondinid,'')<>''  
	   
    insert into #tbl_ggm02_zj(compid,billno,empno,srvtype,seq)  
	 select a.cscompid,a.csbillid,csthirdinid,csthirdtype, csseqno 
	 from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),#tbl_emp  
	 where a.cscompid=b.cscompid  
	   and a.csbillid=b.csbillid  
	   and b.cscompid=compid  
	   and csthirdinid=empno  
	   and csthirdtype='1'  
	   and financedate between @fromdate and @todate
	   and ISNULL(csthirdinid,'')<>''  
  

		 select compid,c.compname,empno,staffname,sum(ccount) ccount,sum(ccount)-@ccount as difcount  
		 from (select compid,billno,empno,seq,1 as ccount  
		 from #tbl_ggm02_zj  
		 group by compid,billno,empno,seq) b,companyinfo c,staffinfo  d
		 where b.compid=c.compno  
		   and c.compno=b.compid  
		   and manageno=b.empno  
		 group by compid,c.compname,empno,staffname  
		 order by compid,empno
   
	drop table #tbl_emp
	drop table #tbl_ggm02_zj
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_costanlysis_by_date_comps]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_compute_costanlysis_by_date_comps]                 
(          
 @compid varchar(10),  ---公司编号        
 @fromdate varchar(10),--开始日期        
 @todate varchar(10),  --结束日期     
 @viewtype int         --查看对象       
)          
as          
begin          
	create table #empinfobydatez_js          
	(                                    
		seqno int identity not null,                                                            
		inid varchar(20) null,                                                            
		empid varchar(20) null,                                                            
		datefrom varchar(8) null,                                                            
		dateto  varchar(8) null,                                                            
		position varchar(10) null,                                                            
		salary  float null,                                                            
		sharetype varchar(5) null,                                                            
		sharerate float null,                                                            
		deducttax int null                                                            
	)          
	create clustered index idx_empinfobydatez_js on #empinfobydatez_js(empid,datefrom,dateto)                
           
	create table #resulet          
	(          
		empno varchar(20) not null,          
		empname varchar(30) null,          
		inid varchar(20) null,          
		oldxf float   null, --老客洗头        
		xfcount float  null,--洗头个数        
		mrcount float  null,--美容项目个数          
		olditem float   null,--老客项目          
		itemcount float null,--项目数          
		oldcount float  null,--老客人数          
		hlcount float  null,--护理个数        
		rfcount float   null,--染发个数        
		recount float   null,--热烫        
		tfcount float   null,--烫发个数        
		gmitem   float  null,--购买疗程数        
	)                                
           
	 create table #tbl_temp          
	 (          
	  billno varchar(20) not null,          
	  inid varchar(20) null,          
	  seq float null,          
	  empno varchar(20) not null,          
	  itemid varchar(20) not null,          
	  clenttype float  null,          
	  ccount float  null,          
	 )          
           
	 create table #tbl_dis          
	 (          
	  billno varchar(20) not null,          
	  empno varchar(20) not null,          
	  inid varchar(20) not null,          
	  itemid varchar(20) not null,          
	  clenttype float null,          
	  ccount float null          
	 )          
	           
           
	    
		 if(@viewtype=1)        
		 begin          
		 insert into #resulet(empno,inid)        
		 select staffno,manageno    
		 from staffinfo        
		 where compno=@compid         
		   and position in('004','00401','00402','008','00901','00902','00903','00904')        
		 end          
		 else if(@viewtype=2)        
		 begin        
		 insert into #resulet(empno,inid)        
		 select staffno,manageno        
		 from staffinfo        
		 where compno=@compid         
		   and position in('004','00401','00402')    
		 end        
		 else if(@viewtype=3)        
		 begin        
		 insert into #resulet(empno,inid)        
		 select staffno,manageno  
		 from staffinfo        
		 where compno=@compid    
		   and position in('008','00901','00902','00903','00904')    
		 end        
         
		 insert into #tbl_temp(billno,inid,empno,itemid,clenttype,ccount,seq)          
		 select b.csbillid,csfirstinid,csfirstsaler,csitemno,csfirsttype,csitemcount,csitemamt          
		 from mconsumeinfo a with(nolock),dconsumeinfo b  with(nolock),#resulet          
		 where a.cscompid=b.cscompid          
		   and a.csbillid=b.csbillid          
		   and financedate between @fromdate and @todate          
		   and csfirstinid=inid    
		   and ISNULL(csfirstinid,'')<>''    
		   and ISNULL(a.backcsbillid,'')=''    
		   and ISNULL(a.backcsflag,0)=0    
           
	   insert into #tbl_temp(billno,inid,empno,itemid,clenttype,ccount,seq)          
		 select b.csbillid,cssecondinid,cssecondsaler,csitemno,cssecondtype,csitemcount,csitemamt          
		 from mconsumeinfo a with(nolock),dconsumeinfo b  with(nolock),#resulet          
		 where a.cscompid=b.cscompid          
		   and a.csbillid=b.csbillid          
		   and financedate between @fromdate and @todate          
		   and cssecondinid=inid    
		   and ISNULL(cssecondinid,'')<>''    
		   and ISNULL(a.backcsbillid,'')=''    
		   and ISNULL(a.backcsflag,0)=0         
             
		   insert into #tbl_temp(billno,inid,empno,itemid,clenttype,ccount,seq)          
		 select b.csbillid,csthirdinid,csthirdsaler,csitemno,csthirdtype,csitemcount,csitemamt          
		 from mconsumeinfo a with(nolock),dconsumeinfo b  with(nolock),#resulet          
		 where a.cscompid=b.cscompid          
		   and a.csbillid=b.csbillid          
		   and financedate between @fromdate and @todate          
		   and csthirdinid=inid    
		   and ISNULL(csthirdinid,'')<>''    
		   and ISNULL(a.backcsbillid,'')=''    
		   and ISNULL(a.backcsflag,0)=0           
           
		 create table #table_temp          
		 (          
		 empno varchar(20) not null,          
		 itemid varchar(20) null,    
		 ccount float  null,          
		 )          
		           
		           
		 insert into #tbl_dis(billno,empno,inid,itemid,clenttype,ccount)          
		 select billno,empno,inid,itemid,clenttype,ccount          
		 from #tbl_temp          
		 group by billno,empno,inid,itemid,clenttype,ccount,seq          
           
		 --计算洗发做的个数        
		 insert #table_temp(empno,ccount)        
		 select inid,SUM(ccount)        
		 from #tbl_dis,projectnameinfo        
		 where itemid=prjno    
		   and prjreporttype in('00','01')    
		 group by inid        
           
		 update a set xfcount=ccount        
		 from #resulet a,#table_temp b        
		 where a.inid=b.empno        
		           
		 delete #table_temp          
           
		--老客洗发        
		insert #table_temp(empno,ccount)        
		 select inid,SUM(ccount)        
		 from #tbl_dis,projectnameinfo        
		 where itemid=prjno         
		   and prjreporttype in('00','01')    
		   and clenttype=1    
		 group by inid    
           
            
		 update a set oldxf=ccount          
		 from #resulet a,#table_temp b          
		 where a.inid=b.empno          
		           
		 delete #table_temp          
           
		 --美容项目        
		 insert #table_temp(empno,ccount)          
		 select inid,SUM(ccount)          
		 from #tbl_dis,projectnameinfo          
		 where itemid=prjno            
		   and prjreporttype='4'          
		   and prjpricetype=1  
		 group by inid          
           
           
		 update a set mrcount=ccount          
		 from #resulet a,#table_temp b          
		 where a.inid=b.empno          
		          
		 delete #table_temp          
		           
		 --老客项目        
		 insert #table_temp(empno,ccount)          
		 select inid,SUM(ccount)          
		 from #tbl_dis,projectnameinfo          
		 where itemid=prjno    
		   and prjpricetype=1          
		   and clenttype=1          
		 group by inid          
           
           
		 update a set olditem=ccount          
		 from #resulet a,#table_temp b          
		 where a.inid=b.empno          
		          
		 delete #table_temp          
           
		 --老客        
		 insert #table_temp(empno,ccount)          
		 select inid,1          
		 from #tbl_dis  
		 where clenttype=1          
		 group by billno,inid          
		           
		 update a set oldcount=ccount        
		 from #resulet a,(select empno,SUM(ccount) ccount from #table_temp group by empno) b        
		 where a.inid=b.empno        
		           
		 delete #table_temp        
           
		 --项目个数        
		 insert #table_temp(empno,ccount)        
		 select inid,SUM(ccount)  
		 from #tbl_dis,projectnameinfo  
		 where itemid=prjno         
		   and prjpricetype=1  
		 group by inid        
		           
           
		 update a set itemcount=ccount          
		 from #resulet a,#table_temp b          
		 where a.inid=b.empno        
		          
		 delete #table_temp        
		         
         
         
		 if(@viewtype=1 or @viewtype=3)        
		 begin        
		 --计算护理的个数        
		  insert #table_temp(empno,ccount)        
		  select inid,SUM(ccount)        
		  from #tbl_dis,projectnameinfo        
		  where itemid=prjno      
			and prjreporttype in('04','05','06','07')         
		  group by inid        
		        
		  update a set hlcount=ccount  
		  from #resulet a,#table_temp b        
		  where a.inid=b.empno  
		          
		  delete #table_temp        
		          
		  --计算烫染的个数        
		  insert #table_temp(empno,ccount)  
		  select inid,SUM(ccount)  
		  from #tbl_dis,projectnameinfo  
		  where itemid=prjno  
			and prjreporttype='03'  
		  group by inid  
          
          
		  update a set tfcount=ccount        
		  from #resulet a,#table_temp b        
		  where a.inid=b.empno        
		        
		  delete #table_temp        
          
		  --计算染发的个数        
		  insert #table_temp(empno,ccount)        
		  select inid,SUM(ccount)        
		  from #tbl_dis,projectnameinfo        
		  where itemid=prjno         
			and prjreporttype='02'        
		  group by inid        
          
		  update a set rfcount=ccount        
		  from #resulet a,#table_temp b        
		  where a.inid=b.empno        
		        
		  delete #table_temp        
          
		  --计算热烫的个数        
		  insert #table_temp(empno,ccount)        
		  select inid,SUM(ccount)        
		  from #tbl_dis,projectnameinfo        
		  where itemid=prjno          
			and prjreporttype='03'    
			and prjname like '%(热烫)%'        
		  group by inid        
          
		  update a set recount=ccount        
		  from #resulet a,#table_temp b        
		  where a.inid=b.empno        
		        
		  delete #table_temp        
		         
		  delete #tbl_temp        
		  --购买疗程        
		  insert into #tbl_temp(billno,empno,inid,ccount,itemid)        
		  select b.changebillid,b.firstsalerid,b.firstsalerinid,procount,changeproid    
		  from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock)   
		  where a.changecompid=@compid     and a.changecompid=b.changecompid and a.changebillid=b.changebillid
			and changedate between @fromdate and @todate    
			and ISNULL(firstsalerinid,'')<>'' 
        
 	     insert into #tbl_temp(billno,empno,inid,ccount,itemid)        
		  select b.changebillid,b.secondsalerid,b.secondsalerinid,procount,changeproid    
		  from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock)   
		  where a.changecompid=@compid     and a.changecompid=b.changecompid and a.changebillid=b.changebillid
			and changedate between @fromdate and @todate    
			and ISNULL(secondsalerinid,'')<>''    
			
			insert into #tbl_temp(billno,empno,inid,ccount,itemid)        
		  select b.changebillid,b.thirdsalerid,b.thirdsalerinid,procount,changeproid    
		  from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock)   
		  where a.changecompid=@compid     and a.changecompid=b.changecompid and a.changebillid=b.changebillid
			and changedate between @fromdate and @todate
			 and ISNULL(thirdsalerinid,'')<>''    
			 
			 
			insert into #tbl_temp(billno,empno,inid,ccount,itemid)        
		  select b.changebillid,b.fourthsalerid,b.fourthsalerinid,procount,changeproid    
		  from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock)   
		  where a.changecompid=@compid     and a.changecompid=b.changecompid and a.changebillid=b.changebillid
			and changedate between @fromdate and @todate
			 and ISNULL(fourthsalerinid,'')<>''    
        
		  insert into #table_temp(empno,itemid,ccount)    
		  select inid,itemid,SUM(ccount)    
		  from #tbl_temp    
		  group by inid,itemid    
      
		 
     
     
		  update a set gmitem=b.ccount    
		  from #resulet a,(select empno,SUM(ccount) ccount from #table_temp group by empno) b    
		  where a.inid=b.empno    
		 end        
           
		 update #resulet set empname=staffname          
		 from #resulet,staffinfo        
		 where inid=manageno        
              
           
		 select * from #resulet         
		 where ISNULL(xfcount,0)+ISNULL(oldxf,0)+ISNULL(mrcount,0)+        
			   ISNULL(olditem,0)+ISNULL(itemcount,0)+ISNULL(oldcount,0)+        
			   ISNULL(hlcount,0)+ISNULL(rfcount,0)+ISNULL(tfcount,0)+        
			   isnull(recount,0)+ISNULL(gmitem,0)>0          
           
		 drop table #empinfobydatez_js          
		 drop table #resulet          
		 drop table #table_temp          
		 drop table #tbl_temp          
		 drop table #tbl_dis          
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_prjchange_yeji]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_compute_comp_prjchange_yeji]                      
(                                
 @compid varchar(10) ,                                
 @datefrom varchar(8),                                
 @dateto varchar(8)                              
)                                
as                                
begin                                                      
    
	 create table #prjchange_yeji_resultx(                                
		compid			varchar(10)		null,	--门店编号         
		compname		varchar(50)		null,	--门店名称 
		projecttype		varchar(10)		null,	--类型编号    
		projectamt		float			null,	--类型金额              
	 )               
 
	insert #prjchange_yeji_resultx(compid,projecttype,projectamt )      
	select a.changecompid,prjreporttype+'Amt',sum(isnull(changebyaccountamt,0)+ISNULL(changebycashamt,0))
	from mproexchangeinfo a ,dproexchangeinfo b,commoninfo,compchaininfo,projectnameinfo
	where infotype='XMTJ' and prjno=b.changeproid and prjreporttype=parentcodekey
	and a.changecompid=b.changecompid and a.changebillid=b.changebillid
	and a.changecompid =relationcomp and curcomp=@compid and changedate between @datefrom and @dateto    
	group by a.changecompid,prjreporttype       
        
    update a set compname= b.compname  from  #prjchange_yeji_resultx a,companyinfo b  where a.compid=b.compno
    
	declare @sqltitle varchar(600)
	select @sqltitle = isnull(@sqltitle + '],[' , '') + parentcodekey+'Amt' from commoninfo where infotype='XMTJ' 
	set @sqltitle = '[' + @sqltitle + ']'

	exec ('select * from (select * from #prjchange_yeji_resultx ) a pivot (max(projectamt) for projecttype in (' + @sqltitle + ')) b order by compid')
	         
	drop table #prjchange_yeji_resultx                           
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_other_satff_salary_daybyday]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_comp_other_satff_salary_daybyday]
(
	@compid			varchar(10)	,
	@staffinid		varchar(20)	,
	@datefrom		varchar(8)	,
	@dateto			varchar(8)
)
as
begin
		create table #allstaff_work_detail_daybyday
		(
			seqno			int	identity		not null,       
			person_inid		varchar(20)			NULL, --员工内部编号
			action_id		int				NULL, --单据类型
			srvdate			varchar(10)			NULL, --日期
			code			varchar(20)			NULL, --项目代码,或是卡号,产品码
			name			varchar(40)			NULL, --名称
			payway			varchar(20)			NULL, --支付方式
			billamt			float				NULL, --营业金额
			ccount			float				NULL, --数量
			cost			float				NULL, --成本
			staffticheng	float				NULL, --提成
			staffyeji		float				NULL, --虚业绩
			prj_type		varchar(20)			NULL, --项目类别
			cls_flag        int					NULL, -- 1:项目 2:产品 3:卡
			billid			varchar(20)			NULL, --单号
			paycode			varchar(20)			NULL, --支付代码
			compid			varchar(10)			NULL, --公司别
			cardid			varchar(20)			NULL, --会员卡号
			cardtype		varchar(20)			NULL, --会员卡类型
			postation		varchar(10)			NULL, --员工部门
		)     
		
		insert #allstaff_work_detail_daybyday (compid,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,cardid,cardtype,postation)
		select compid,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,cardid,cardtype,postation
		from allstaff_work_detail_daybyday where compid=@compid and person_inid=@staffinid
		--美发师做美发项目 超过指定业绩增高普通的提成比率                                
		--美发师业绩超2.5W,首席超5W,总监 7W 美发项目提成比率0.35                                
		create  table #emp_yeji_total_resultx                                 
		(                                
			inid		varchar(20) null,  --员工内部编号                                   
			yeji		float  null, -- 业绩                 
			lv			float  null, --提成比率                                     
		)                                   
            
       	insert #emp_yeji_total_resultx(inid,yeji)                                       
		select  person_inid,sum(isnull(staffyeji,0)) from #allstaff_work_detail_daybyday where ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15') group by person_inid                              
                
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #allstaff_work_detail_daybyday a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='003'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		and b.yeji>=25000 and  prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #allstaff_work_detail_daybyday a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='006'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		and b.yeji>=50000 and  prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #allstaff_work_detail_daybyday a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='007'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		 and b.yeji>=70000  and prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
               
               
		---美容师A类              
		if(@compid in ('008','017','019','026','032'))              
		begin              
               
		 update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05  
		 from #allstaff_work_detail_daybyday a,#emp_yeji_total_resultx b              
		 where a.person_inid=b.inid              
		   and ISNULL(a.postation,'')='004'              
		   and (action_id>=7 and action_id<=15)  
		   and paycode not in('11','12','7','8','A','13')  
		   and cardtype not in('ZK')  
		   and yeji>=70000  
		   and code not in('300','3002','301','302','303','305','306','309','311')              
		   and action_id not in (26,27,28,29,30,31)  
  
		  --条码卡支付的时候需要判断是否为赠送  
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05  
		  from #allstaff_work_detail_daybyday a,nointernalcardinfo,#emp_yeji_total_resultx b  
		  where a.person_inid=b.inid               
			and a.cardtype=cardno              
			and ISNULL(a.postation,'')='004'              
			and (action_id>=7 and action_id<=15)
			and paycode='13'              
			and yeji>=70000                 
			and isnull(entrytype,0)=0              
			and code not in('300','3002','301','302','303','305','306','309','311')              
			and action_id not in (26,27,28,29,30,31)              
		                  
                 
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.02              
		 from #allstaff_work_detail_daybyday a,#emp_yeji_total_resultx b              
		 where a.person_inid=b.inid and ISNULL(a.postation,'')='004'              
		   and paycode not in('11','12','7','8','A','13')              
		   and cardtype not in('ZK')              
		   and (action_id>=7 and action_id<=15)
		   and yeji<70000               
		   and yeji>=50000              
		   and code not in('300','3002','301','302','303','305','306','309','311')              
		   and action_id not in (26,27,28,29,30,31)              
                 
		   --条码卡支付的时候需要判断是否为赠送              
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.02              
		  from #allstaff_work_detail_daybyday a,nointernalcardinfo,#emp_yeji_total_resultx b              
		  where a.person_inid=b.inid               
			and a.cardtype=cardno              
			and ISNULL(a.postation,'')='004'              
			and (action_id>=7 and action_id<=15)       
			and paycode='13'              
			and yeji<70000               
			and yeji>=50000              
			and isnull(entrytype,0)=0              
			and action_id not in (26,27,28,29,30,31)              
	end              
	drop table #emp_yeji_total_resultx                                
                                 
                                
                                            

               
	
  
	create table #allstaff_work_analysis_daybyday
	(
		ddate				varchar(10)			NULL,	--日期
		person_inid			varchar(20)			NULL,	--员工内部编号
		staffno				varchar(30)			NULL,	--员工工号
		staffname			varchar(30)			NULL,	--员工姓名
		staffposition		varchar(30)			NULL,	--员工职位
		oldcostcount		float				NULL,	--老客项目数
		newcostcount		float				NULL,	--新客项目数
		trcostcount			float				NULL,	--烫染项目数
		cashbigcost			float				NULL,	--现金大项
		cashsmallcost		float				NULL,	--现金小项
		cashhulicost		float				NULL,	--现金护理
		cardbigcost			float				NULL,	--销卡大项
		cardsmallcost		float				NULL,	--销卡小项
		cardhulicost		float				NULL,	--销卡护理
		cardprocost			float				NULL,	--疗程消费
		cardsgcost			float				NULL,	--收购卡消费
		cardpointcost		float				NULL,	--积分消费
		projectdycost		float				NULL,	--项目抵用券
		cashdycost			float				NULL,	--现金抵用券
		tmcardcost			float				NULL,	--条卡卡消费
		salegoodsamt		float				NULL,	--产品销售
		salecardsamt		float				NULL,	--卡销售
		prochangeamt		float				NULL,	--疗程兑换
		saletmkamt			float				NULL,	--条码卡销售
		qhpayinner			float				NULL,	--全韩店内支付
		qhpayouter			float				NULL,	--全韩对方支付
		jdpayinner			float				NULL,	--暨大店内支付
		smpayinner			float				NULL,	--私密店内支付
		staffyeji			float				NULL,	--员工提成合计
	) 
	create clustered index idx_work_analysis_person_inid on #allstaff_work_analysis_daybyday(person_inid)  
	insert #allstaff_work_analysis_daybyday(person_inid,ddate,staffyeji)
	select person_inid,srvdate,SUM(isnull(staffticheng,0)) from #allstaff_work_detail_daybyday where isnull(person_inid,'')<>'' group by person_inid,srvdate
	
	update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position
	from #allstaff_work_analysis_daybyday a,staffinfo b
	where a.person_inid=b.Manageno
	
	--现金大项
	update a set cashbigcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode in ('1','2','6','0','14','15') ),0)
	from #allstaff_work_analysis_daybyday a
	
	--现金小项
	update a set cashsmallcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode in ('1','2','6','0','14','15') ),0)
	from #allstaff_work_analysis_daybyday a
	
	--现金护理
	update a set cashhulicost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode in ('1','2','6','0','14','15') ),0)
	from #allstaff_work_analysis_daybyday a
	
	--销卡大项
	update a set cardbigcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode ='4' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--销卡小项
	update a set cardsmallcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode  ='4' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--销卡护理
	update a set cardhulicost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode  ='4' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--疗程消费
	update a set cardprocost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='9' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--收购卡消费
	update a set cardsgcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='A' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--积分消费
	update a set cardpointcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='7' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--项目抵用券消费
	update a set projectdycost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='11' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--现金抵用券消费
	update a set cashdycost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='12' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--条码卡消费
	update a set tmcardcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='13' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--产品销售
	update a set salegoodsamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=16 and b.action_id<=24  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--会员卡销售
	update a set salecardsamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=1 and b.action_id<=3  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--疗程兑换
	update a set prochangeamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =4  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--条码卡销售
	update a set saletmkamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =5  ),0)
	from #allstaff_work_analysis_daybyday a
	

	--全韩店内支付
	update a set qhpayinner=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =28  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--全韩对方支付
	update a set qhpayouter=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =27 ),0)
	from #allstaff_work_analysis_daybyday a
	
	--暨大店内支付
	update a set jdpayinner=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =28  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--私密店内支付
	update a set smpayinner=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =29  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--新客项目数
	update a set oldcostcount=ISNULL((
		select count(distinct billid) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id in (8,11,14) and isnull(ccount,0)>0  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--老客项目数
	update a set newcostcount=ISNULL((
		select count(distinct billid) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id in (7,10,13) and isnull(ccount,0)>0  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--烫染项目数
	update a set trcostcount=ISNULL((
		select count(distinct billid) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and isnull(ccount,0)>0 and b.code=c.prjno and c.prjreporttype in ('02','03') ),0)
	from #allstaff_work_analysis_daybyday a
	

	 

		delete staff_work_salary where compid=@compid and salary_date between @datefrom and @dateto and person_inid=@staffinid
		insert staff_work_salary(compid,person_inid,salary_date,oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,
				cardbigcost,cardsmallcost,cardhulicost,cardprocost,cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,
				salegoodsamt,salecardsamt,prochangeamt,saletmkamt,qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji)
		select @compid,person_inid,ddate,sum(isnull(oldcostcount,0)),sum(isnull(newcostcount,0)),sum(isnull(trcostcount,0)),
				sum(isnull(cashbigcost,0)),sum(isnull(cashsmallcost,0)),sum(isnull(cashhulicost,0)),
				sum(isnull(cardbigcost,0)),sum(isnull(cardsmallcost,0)),sum(isnull(cardhulicost,0)),
				sum(isnull(cardprocost,0)),sum(isnull(cardsgcost,0)),sum(isnull(cardpointcost,0)),
				sum(isnull(projectdycost,0)),sum(isnull(cashdycost,0)),sum(isnull(tmcardcost,0)),
				sum(isnull(salegoodsamt,0)),sum(isnull(salecardsamt,0)),sum(isnull(prochangeamt,0)),
				sum(isnull(saletmkamt,0)),sum(isnull(qhpayinner,0)),sum(isnull(qhpayouter,0)),
				sum(isnull(jdpayinner,0)),sum(isnull(smpayinner,0)),sum(isnull(staffyeji,0))
		from #allstaff_work_analysis_daybyday
		group by person_inid,ddate
		drop table #allstaff_work_detail_daybyday
		drop table #allstaff_work_analysis_daybyday
	end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_detial_trade]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_comp_detial_trade] 
(
	@compid			varchar(10),		--门店号	
	@datefrom			varchar(10),	--起始日期
	@dateto			varchar(10)			--结束日期
)  
as
begin
	--获取门店支付信息
	CREATE TABLE    #dpayinfo               --  单据--支付明细
	(
		paycompid		varchar(10)      NULL,   --公司编号
		paydate			varchar(10)      NOT NULL,   --结算日期
		paybilltype		varchar(5)		 NULL,   --单据类别  SY 收银  SK  售卡  CZ 充值 ZK  转卡 HZ 合作项目 TK 退卡
		paymode			varchar(5)		 NULL,   --支付方式
		payamt			float			 NULL,   --支付金额
	)
	create nonclustered index idx_dpayinfoday_paydate on #dpayinfo(paycompid,paydate)	
	----------------------------需要数据库日结-------------upg_compute_comp_trade_payinfo_daybyday------------
	insert #dpayinfo(paycompid,paydate,paybilltype,paymode,payamt)
	select paycompid,paydate,paybilltype,paymode,payamt from payinfodaybyday,compchaininfo where  curcomp=@compid and paycompid=relationcomp 
	--计算门店营业情况
	delete detial_trade_byday_fromshops from payinfodaybyday,compchaininfo where curcomp=@compid and shopId=relationcomp and  dateReport between @datefrom and @dateto
	create table #detial_trade_byday_fromShops      --用于结算查询门店的日结信息                                          
	(                                                                              
		 shopId						varchar(10)			NULL,	--门店编号                                           
		 shopName					varchar(20)			NULL,	--门店名称                                           
		 dateReport					varchar(8)			NULL,	--结账日期                                           
		 total						float				NULL,	--总收入 
		                                                                  
		 cashService				float				NULL,	--现金服务                                                 
		 cashProd					float				NULL,	--现金产品                                                 
		 cashCardTrans				float				NULL,	--现金(卡异动)                                    
		 cashBackCard				float				NULL,	--现金(退卡)                                              
		 cashTotal					float				NULL,	--现金合计(扣除现金退卡)                                                
		                                                                                                 
		 creditService				float				NULL,	--银行卡服务                                                
		 creditProd					float				NULL,	--银行卡产品                                                
		 creditTrans				float				NULL,	--银行卡(卡异动)                                      
		 creditBackCard				float				NULL,	---银行卡(退卡)                                           
		 creditTotal				float				NULL,	--银行卡合计(扣除银行卡退卡)                                                
		                                                                                                  
		 checkService				float				NULL,	--支票服务                                                 
		 checkProd					float				NULL,	--支票产品                                                 
		 checkTrans					float				NULL,	--支票(卡异动)                                       
		 checkBackCard				float				NULL,	--支票(退卡)                                           
		 checkTotal					float				NULL,	--支票合计(扣除支票退卡)                                   
		                               
		 zftService					float				NULL,	--指付通服务                                                 
		 zftProd					float				NULL,	--指付通产品                                                 
		 zftTrans					float				NULL,	--指付通(卡异动)                                       
		 zftBackCard				float				NULL,	--指付通(退卡)                                           
		 zftTotal					float				NULL,	--指付通合计(扣除支付通退卡)                                 
		                               
		 ockService					float				NULL,	--OK卡服务                                                 
		 ockkProd					float				NULL,	--OK卡产品                                                 
		 ockTrans					float				NULL,	--OK卡(卡异动)                                         
		 ockTotal					float				NULL,	--OK卡合计
		 
		 tgkService					float				NULL,	--团购卡服务                                                 
		 tgkkProd					float				NULL,	--团购卡产品                                   
		 tgkTrans					float				NULL,	--团购卡(卡异动)                                                  
		 tgkTotal					float				NULL,	--团购卡合计         
		 
		 totalCardTrans				float				NULL,	--卡异动(卡销售,卡充值,卡升级)	
		                           
		 cashchangeSale				float				NULL,	--现金兑换销售                          
		 bankchangeSale				float				NULL,	--银行卡兑换销售 
		  
		 cashbytmkSale				float				NULL,	--现金条码卡销售                              
		 bankbytmkSale				float				NULL,	--银行卡条码卡销售                              
		 checkbytmkSale				float				NULL ,	--支票条码卡销售                              
		 fingerbytmkSale			float				NULL,	--指付通条码卡销售                              
		 okpqypwybytmkSale			float				NULL,	--OK卡条码卡销售
		  
		 cashhezprj					float				NULL,	--现金合作项目                           
		 bankhezprj					float				NULL,	--银行卡合作项目                  
		 sumcashhezprj				float				NULL,	--现金合作项目(店内支付的现金)                                    
		                                                                                                  
		                                                  
		                                                                                                             
		 cardSalesServices          float				NULL,	--销卡服务                                                 
		 cardSalesprod				float				NULL,	--销卡产品   
		  staffsallprod				float				NULL,	--员工产品              
		 acquisitionCardServices	float				NULL,	--收购转卡服务 
		 costpointTotal				float				NULL,	--积分服务   
		 cashdyService				float				NULL,   --现金抵用券服务                              
		 prjdyService				float				NULL,   --项目抵用券服务
		 tmkService					float				NULL,   --条码卡 服务
		 manageSigning				float				NULL,	--经理签单                                         
		 payOutRegister				float				NULL,	--支出登记 
	)  
	
	---------------------------开始计算营业数据-------------------START-------------------------------    
	declare @tmpdate varchar(8)                                    
	declare @tmpenddate varchar(8)                                    
	set @tmpenddate = @datefrom                                    
	set @tmpdate = @datefrom                                    
    while (@tmpenddate <= @dateto)                                    
    begin                                    
	  --插入选择的门店编号到#diarialBill_byday_fromShops中                                        
		insert #detial_trade_byday_fromShops(shopId,shopName,dateReport)                                        
		select compno,compname,@tmpenddate                                        
		from companyinfo,compchaininfo
		where curcomp= @compid and  relationcomp=compno   
		                                   
		execute upg_date_plus @tmpdate,1,@tmpenddate output                                    
		set @tmpdate = @tmpenddate                                    
    end 

	--总收入
    update #detial_trade_byday_fromShops        
	  set total=ISNULL((select sum(case when  paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
										when  paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid ),0)
		
    
    
      --卡异动
    update #detial_trade_byday_fromShops        
	set totalCardTrans=ISNULL((select  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
													when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  ),0)
	
   
    --现金服务
    update #detial_trade_byday_fromShops        
	  set cashService=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='1' and isnull(paybilltype,'')='SY_P' ),0)
	 --现金产品
    update #detial_trade_byday_fromShops        
	  set cashProd=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='1' and isnull(paybilltype,'') in ('SY_G1','SY_G2')),0)
	 --现金卡异动
    update #detial_trade_byday_fromShops        
	  set cashCardTrans=ISNULL((select  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
													when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='1'),0)
	--现金退卡
    update #detial_trade_byday_fromShops        
	  set cashBackCard=ISNULL((select  sum(isnull(payamt,0)*(-1))
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='1' and isnull(paybilltype,'')='TK'),0)

	--现金总收入
	 update #detial_trade_byday_fromShops        
	  set cashTotal=ISNULL((select sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
										when    isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='1'),0)
			
	--银行卡服务
    update #detial_trade_byday_fromShops        
	  set creditService=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='6' and isnull(paybilltype,'')='SY_P' ),0)
	 --银行卡产品
    update #detial_trade_byday_fromShops        
	  set creditProd=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='6' and isnull(paybilltype,'') in ('SY_G1','SY_G2') ),0)
	 --银行卡卡异动
    update #detial_trade_byday_fromShops        
	  set creditTrans=ISNULL((select  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
													when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='6'),0)
	--银行卡退卡
    update #detial_trade_byday_fromShops        
	  set creditBackCard=ISNULL((select  sum(isnull(payamt,0)*(-1))
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='6' and isnull(paybilltype,'')='TK'),0)

	--银行卡总收入
	 update #detial_trade_byday_fromShops        
	  set creditTotal=ISNULL((select sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
										when    isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='6'),0)
	
	--支票服务
    update #detial_trade_byday_fromShops        
	  set checkService=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='2' and isnull(paybilltype,'')='SY_P' ),0)
	 --支票产品
    update #detial_trade_byday_fromShops        
	  set checkProd=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='2' and isnull(paybilltype,'') in ('SY_G1','SY_G2')),0)
	 --支票卡异动
    update #detial_trade_byday_fromShops        
	  set checkTrans=ISNULL((select  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
													when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='2'),0)
	--支票退卡
    update #detial_trade_byday_fromShops        
	  set checkBackCard=ISNULL((select  sum(isnull(payamt,0)*(-1))
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='2' and isnull(paybilltype,'')='TK'),0)

	--支票总收入
	 update #detial_trade_byday_fromShops        
	  set checkTotal=ISNULL((select sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
										when    isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='2'),0)
		
	--指付通服务
    update #detial_trade_byday_fromShops        
	  set zftService=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='14' and isnull(paybilltype,'')='SY_P' ),0)
	 --指付通产品
    update #detial_trade_byday_fromShops        
	  set zftProd=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='14' and isnull(paybilltype,'') in ('SY_G1','SY_G2') ),0)
	 --指付通卡异动
    update #detial_trade_byday_fromShops        
	  set zftTrans=ISNULL((select  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
													when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='14'),0)
	--指付通退卡
    update #detial_trade_byday_fromShops        
	  set zftBackCard=ISNULL((select  sum(isnull(payamt,0)*(-1))
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='14' and isnull(paybilltype,'')='TK'),0)

	--指付通总收入
	 update #detial_trade_byday_fromShops        
	  set zftTotal=ISNULL((select sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
										when    isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='14'),0)
	--OK卡服务
    update #detial_trade_byday_fromShops        
	  set ockService=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='15' and isnull(paybilltype,'')='SY_P' ),0)
	 --OK卡产品
    update #detial_trade_byday_fromShops        
	  set ockkProd=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='15' and isnull(paybilltype,'') in ('SY_G1','SY_G2') ),0)
	 --OK卡卡异动
    update #detial_trade_byday_fromShops        
	  set ockTrans=ISNULL((select  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
													when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='15'),0)
	--OK卡总收入
	 update #detial_trade_byday_fromShops        
	  set ockTotal=ISNULL((select sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
										when    isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='15'),0)
	 
	 --团购卡服务
    update #detial_trade_byday_fromShops        
	  set tgkService=ISNULL((select sum( isnull(payamt,0) ) from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='16' and isnull(paybilltype,'')='SY_P' ),0)
	 --团购卡产品
    update #detial_trade_byday_fromShops        
	    set tgkkProd=ISNULL((select sum( isnull(payamt,0) ) from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='16' and isnull(paybilltype,'') in ('SY_G1','SY_G2') ),0)
	 --团购卡卡异动
    update #detial_trade_byday_fromShops        
	  set tgkTrans=ISNULL((select  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
											when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='16'),0)
	--团购卡总收入
	 update #detial_trade_byday_fromShops        
	  set tgkTotal=ISNULL((select sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
										when    isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='16'),0)
									
	--现金兑换销售
	 update #detial_trade_byday_fromShops        
	  set cashchangeSale=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='1' and isnull(paybilltype,'')='LXDH' ),0)
	--银行卡兑换销售 
	update #detial_trade_byday_fromShops        
	  set bankchangeSale=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='6' and isnull(paybilltype,'')='LXDH' ),0)
	
	--现金条码卡销售    
	update #detial_trade_byday_fromShops        
	  set cashbytmkSale=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='1' and isnull(paybilltype,'')='TMK' ),0)
	--银行卡条码卡销售   
	update #detial_trade_byday_fromShops        
	  set bankbytmkSale=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='6' and isnull(paybilltype,'')='TMK' ),0)
	--支票条码卡销售  
	update #detial_trade_byday_fromShops        
	  set checkbytmkSale=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='2' and isnull(paybilltype,'')='TMK' ),0)
	--指付通条码卡销售    
	update #detial_trade_byday_fromShops        
	  set fingerbytmkSale=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='14' and isnull(paybilltype,'')='TMK' ),0)
	--OK卡条码卡销售
	update #detial_trade_byday_fromShops        
	  set okpqypwybytmkSale=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='15' and isnull(paybilltype,'')='TMK' ),0)
	 			
	--现金合作项目 
	update #detial_trade_byday_fromShops        
	  set cashhezprj=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='1' and isnull(paybilltype,'') in ('HZ_I') ),0)
	--银行卡合作项目		
	update #detial_trade_byday_fromShops        
	  set bankhezprj=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode ='6' and isnull(paybilltype,'')  in ('HZ_I') ),0)
	--现金合作项目(店内支付的现金) 
	update #detial_trade_byday_fromShops        
	  set sumcashhezprj=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid   and isnull(paybilltype,'')='HZ_I' ),0)
	--销卡服务      
	update #detial_trade_byday_fromShops        
	  set cardSalesServices=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode  in ('4','9') and isnull(paybilltype,'')='SY_P' ),0)
	--销卡产品      
	update #detial_trade_byday_fromShops        
	  set cardSalesprod=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode='4' and isnull(paybilltype,'') in ('SY_G1','SY_G2') ),0)
	
	update #detial_trade_byday_fromShops        
	  set staffsallprod=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid   and isnull(paybilltype,'') ='SY_G2' ),0)
	
	
	--收购转卡服务 
	update #detial_trade_byday_fromShops        
	  set acquisitionCardServices=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode='A' and isnull(paybilltype,'')='SY_P' ),0)
	--积分服务 
	update #detial_trade_byday_fromShops        
	  set costpointTotal=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode='7' and isnull(paybilltype,'')='SY_P' ),0)
								     
	--现金抵用券服务 
	update #detial_trade_byday_fromShops        
	  set cashdyService=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode='12' and isnull(paybilltype,'')='SY_P' ),0)
								     
	--项目抵用券服务 
	update #detial_trade_byday_fromShops        
	  set prjdyService=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode='11' and isnull(paybilltype,'')='SY_P' ),0)
								     
	--条码卡服务 
	update #detial_trade_byday_fromShops        
	  set tmkService=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode='13' and isnull(paybilltype,'')='SY_P' ),0)

	 --条码卡服务 
	update #detial_trade_byday_fromShops        
	  set manageSigning=ISNULL((select sum( isnull(payamt,0) )
						  from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode='8'  ),0)
	
	--支出登记
	update #detial_trade_byday_fromShops        
	  set payOutRegister=ISNULL((select sum( isnull(payoutitemamt,0) )
						  from mpayoutinfo a,dpayoutinfo b,compchaininfo where a.payoutcompid=b.payoutcompid and a.payoutbillid=b.payoutbillid and a.payoutdate=dateReport and curcomp= shopId and  relationcomp=a.payoutcompid  and b.payoutbillstate='3'  ),0)
	
	---------------------------开始计算营业数据-------------------END---------------------------------    
	insert detial_trade_byday_fromshops(shopId,shopName,dateReport,total, cashService,cashProd,cashCardTrans,cashTotal,cashBackCard,
			creditService,creditProd,creditTrans,creditTotal,creditBackCard,checkService,checkProd,checkTrans,checkTotal,checkBackCard,
			zftService,zftProd,zftTrans, zftTotal,zftBackCard,ockService,ockkProd,ockTrans, ockTotal,tgkService, tgkkProd,tgkTrans,tgkTotal, 
			totalCardTrans, cashchangesale,bankchangesale,cashbytmkSale,bankbytmkSale,checkbytmkSale,fingerbytmkSale,okpqypwybytmkSale,
			cashhezprj,bankhezprj,sumcashhezprj,cardsalesservices, cardsalesprod,staffsallprod,acquisitionCardServices,costpointTotal,cashdyService,prjdyService,tmkService,
			manageSigning, payOutRegister )                                    
	select  shopId,shopName,dateReport,total,
	        cashService,cashProd,cashCardTrans,cashTotal,cashBackCard,
			creditService,creditProd,creditTrans,creditTotal,creditBackCard,
			checkService,checkProd,checkTrans,checkTotal,checkBackCard,
			zftService,zftProd,zftTrans, zftTotal,zftBackCard,
			ockService,ockkProd,ockTrans, ockTotal,
			tgkService, tgkkProd,tgkTrans,tgkTotal, 
			totalCardTrans, cashchangesale,bankchangesale,cashbytmkSale,bankbytmkSale,checkbytmkSale,fingerbytmkSale,okpqypwybytmkSale,
			cashhezprj,bankhezprj,sumcashhezprj,
			cardSalesServices, cardSalesprod,staffsallprod,acquisitionCardServices,costpointTotal,cashdyService,prjdyService,tmkService,
			manageSigning, payOutRegister                      
         from #detial_trade_byday_fromShops 
 
	drop table #dpayinfo
	drop table #detial_trade_byday_fromShops
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_avg_analysis_bytype]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_comp_avg_analysis_bytype] 
(                                    
	@compid varchar(10) ,                                    
	@datefrom varchar(8),                                    
	@dateto varchar(8),
    @ordertype varchar(5)      --0按日期范围,1 按完整月 2,按完整周期   
)                                    
as                                    
begin     
 create table #comp_analysis_avg                      
 (                      
	seardate			varchar(40)		null,                      
	compid				varchar(10)		null,                      
	compname			varchar(30)		null,                    
	totalxuyeji			float			null,	--虚业绩                  
	totalshiyeji		float			null,	--实业绩                  
	costcardrate		float			null,	--耗卡率                  
	memcount			float			null,	--会员数                  
	goodmemcount		float			null,	--有效会员数                  
	addmemcount			float			null,	--新增会员数                  
	addbeatypromems		float			null,	--新增美容疗程数                  
	addhairpromems		float			null,	--新增美发疗程数                  
	addpromems			float			null,	--新增疗程数                  
	beatycount			float			null,	--美容会员数                   
	beatygoodcount		float			null,	--美容有效会员数          
	beatygoodcountl		float			null,	--上月美容有效会员数                 
	beatyallcountl		float			null,	-- 美容类两月都来会员数                    
	beatygoodrate		float			null,	--美容会员保有率                   
	haircount			float			null,	--美发会员数                   
	hairgoodcount		float			null,	--美发有效会员数                     
	 hairgoodrate		float			null,	--美发会员保有率          
	hairgoodcountl		float			null,	--上月美发有效会员数                   
	hairallcountl		float			null,	-- 美发类两月都来会员数                    
	leavelcount			float			null,	--离职人数          
	leavelcorecount		float			null,	--核心员工离职人数                   
	factdatefrom		varchar(10)		null,	--实际起始日期            
	factdateto			varchar(10)		null,   --实际结束日期        
         
	factdatefroml		varchar(10)		null,	--前月起始日期            
	factdatetol			varchar(10)		null,   --前月实际结束日期 
	totalxuyejizb		varchar(10)		null,	--虚业绩指标            
	costcardratezb		varchar(10)		null,  --耗卡率 指标              
	goodmemcountzb		varchar(10)		null,  --有效会员数  指标            
	addpromemszb		varchar(10)		null,  --新增疗程数  指标             
	beatygoodratezb		varchar(10)		null,  --美容会员保有率 指标             
	hairgoodratezb		varchar(10)		null,  --美发会员保有率  指标            
	leavelcountzb		varchar(10)		null,  --离职人数指标             
 )                     
             
	declare @targgetfromdate varchar(10)             
	declare @targgettodate varchar(10)          
	declare @datefroml varchar(10)                
	declare @datetol varchar(10)                
	declare @year varchar(4) --年份                
	declare @month varchar(2)--月份                
	declare @day varchar(2) --日份           
	declare @nmonth int         
	set @nmonth=1          
            
             
	if(@ordertype='0')--按完整日期            
	begin            
		insert #comp_analysis_avg(seardate,compid,compname,factdatefrom,factdateto)                    
		select @datefrom+'-'+@dateto,relationcomp,compname,@datefrom,@dateto from compchaininfo,compchainstruct,companyinfo where curcomp=@compid and complevel=4 and curcompno=relationcomp and compno=relationcomp                       
	end            
	else if(@ordertype='1')--按完整月            
	begin            
		declare @monthcount int            
		set @monthcount=1            
		set @targgetfromdate=@datefrom             
		declare @monthdays int            
		declare @curyear int            
		declare @curmonth int           
		while(@targgetfromdate<@dateto)            
		begin            
			set @curyear=cast(SUBSTRING(@targgetfromdate,1,4) as int)            
			set @curmonth=cast(SUBSTRING(@targgetfromdate,5,2) as int)        
          
			exec get_month_days @curyear,@curmonth,@monthdays output      
              
			set @targgettodate=SUBSTRING(@targgetfromdate,1,4)+SUBSTRING(@targgetfromdate,5,2)+CAST(@monthdays as varchar(2))           
			--------------------------------------------------------------------------------        
			set @datefroml = dateadd(MONTH,-1*(@nmonth),convert(datetime,@targgetfromdate))                
			set @year = year(@datefroml)                
			set @month = month(@datefroml)                
			set @day = day(@datefroml)                
			if(len(@month)=1)                
				set @month = '0'+@month                
			if(len(@day)=1)                
				set @day = '0'+@day               
			set @datefroml=@year+@month+@day                
         
			set @datetol = dateadd(MONTH,-1*(@nmonth),convert(datetime,@targgettodate))         
               
			set @year = year(@datetol)              
			set @month = month(@datetol)                
			set @day = day(@datetol)                
			if(len(@month)=1)                
				set @month = '0'+@month                
			if(len(@day)=1)                
				set @day = '0'+@day                
			set @datetol=@year+@month+@day          
			print '111111111111111111111111'       
      
		   --------------------------------------------------------------------------------        
		   if(@targgettodate<=@dateto)            
		   begin            
		             
			insert #comp_analysis_avg(seardate,compid,compname,factdatefrom,factdateto,factdatefroml,factdatetol)                    
			select @targgetfromdate+'-'+@targgettodate+'[第'+cast(@monthcount as varchar(3))+'月]',relationcomp,compname        
			,@targgetfromdate,@targgettodate,@datefroml,@datetol from compchaininfo,compchainstruct,companyinfo where curcomp=@compid and complevel=4 and curcompno=relationcomp and compno=relationcomp                                            
		      
			exec upg_date_plus @targgettodate,1,@targgetfromdate output            
		          
			--------------------------------------------------------------------------------        
			set @datefroml = dateadd(MONTH,-1*(@nmonth),convert(datetime,@targgetfromdate))                
			set @year = year(@datefroml)                
			set @month = month(@datefroml)                
			set @day = day(@datefroml)                
			if(len(@month)=1)                
				set @month = '0'+@month                
			if(len(@day)=1)                
				set @day = '0'+@day                
			set @datefroml=@year+@month+@day                
		                 
			set @datetol = dateadd(MONTH,-1*(@nmonth),convert(datetime,@targgettodate))                
			set @year = year(@datetol)                
			set @month = month(@datetol)                
			set @day = day(@datetol)                
			if(len(@month)=1)                
			set @month = '0'+@month                
			if(len(@day)=1)                
			set @day = '0'+@day                
			set @datetol=@year+@month+@day           
			--------------------------------------------------------------------------------        
			set @monthcount=@monthcount+1            
		end            
		else            
		begin            
			insert #comp_analysis_avg(seardate,compid,compname,factdatefrom,factdateto,factdatefroml,factdatetol)                    
			select @targgetfromdate+'-'+@dateto+'[第'+cast(@monthcount as varchar(3))+'月]',relationcomp,compname,@targgetfromdate,@dateto,@datefroml,@datetol  from compchaininfo,compchainstruct,companyinfo where curcomp=@compid and complevel=4 and curcompno=relationcomp and compno=relationcomp 
			break            
		end     
	end            
	end              
	else if(@ordertype='2')--按完整周            
	begin            
		  declare @curweekindex int             
		  declare @weekcount int             
		  set @weekcount=1            
		  set @targgetfromdate=@datefrom            
		  while(@targgetfromdate<@dateto)            
		  begin               
				set @curweekindex=datepart(dw,cast(@targgetfromdate as datetime))            
				if(@curweekindex=1) --星期天(往后移一天)            
				begin            
					exec upg_date_plus @targgetfromdate,1,@targgetfromdate output            
					exec upg_date_plus @targgetfromdate,6,@targgettodate output                
				end            
				else if(@curweekindex=2) --星期1(不用动)            
				begin                
					set @targgetfromdate=@targgetfromdate            
					exec upg_date_plus @targgetfromdate,6,@targgettodate output               
				end            
				else if(@curweekindex>=3) --星期2(往后移六天)            
				begin            
					declare @statdate int            
					set @statdate=9-@curweekindex            
					exec upg_date_plus @targgetfromdate,@statdate,@targgetfromdate output            
					exec upg_date_plus @targgetfromdate,6,@targgettodate output 
				end            
				if(@targgettodate<=@dateto)            
				begin            
					insert #comp_analysis_avg(seardate,compid,compname,factdatefrom,factdateto)                    
					select @targgetfromdate+'-'+@targgettodate+'[第'+cast(@weekcount as varchar(3))+'完整周]',relationcomp,compname,@targgetfromdate,@targgettodate  from compchaininfo,compchainstruct,companyinfo where curcomp=@compid and complevel=4 and curcompno=relationcomp and compno=relationcomp 
					exec upg_date_plus @targgettodate,1,@targgetfromdate output            
					set @weekcount=@weekcount+1              
				end            
				else            
				begin            
					break            
			   end            
		end             
	end            
            
 --------------------------------------------------------------------------------        
	set @datefroml = dateadd(MONTH,-1*(@nmonth),convert(datetime,@datefrom))                
	set @year = year(@datefroml)                
	set @month = month(@datefroml)                
	set @day = day(@datefroml)                
	if(len(@month)=1)                
		set @month = '0'+@month                
	if(len(@day)=1)                
		set @day = '0'+@day                
	set @datefroml=@year+@month+@day                
                 
	set @datetol = dateadd(MONTH,-1*(@nmonth),convert(datetime,@dateto))                
	set @year = year(@datetol)                
	set @month = month(@datetol)                
	set @day = day(@datetol)                
	if(len(@month)=1)                
		set @month = '0'+@month                
	if(len(@day)=1)                
		set @day = '0'+@day                
	set @datetol=@year+@month+@day           
   -------------------------------------------------------------------------------- 
   
	create table #yq_classed_yeji_result_ans                  
	(                  
		compid				varchar(10)		null,      
		ddate				varchar(10)		null,            
		beaut_yeji			float			null,                                                            
		hair_yeji			float			null,                     
		trh_yeji			float			null,                                                    
		total_yeji			float			null,                  
		costbycard			float			null,                  
		realytotal_yeji		float			null,                 
		cardsale_yeji		float			null 
	)  
	
   insert #yq_classed_yeji_result_ans(compid,ddate,beaut_yeji,hair_yeji,trh_yeji,total_yeji,realytotal_yeji,costbycard,cardsale_yeji)
   select compid,ddate,beautyeji,hairyeji,footyeji,totalyeji,realtotalyeji,ISNULL(cardSalesServices,0)+ISNULL(cardSalesprod,0),totalCardTrans
   from compclasstraderesult a,detial_trade_byday_fromshops b
   where a.compid=b.shopId and a.ddate=b.dateReport and a.ddate between @datefroml and @dateto
   
   update #yq_classed_yeji_result_ans set cardsale_yeji=1 where isnull(cardsale_yeji,0)=0  
    
    update #comp_analysis_avg set totalxuyeji=convert(numeric(20,2),total_yeji),                  
								  totalshiyeji=convert(numeric(20,2),realytotal_yeji),                  
								  costcardrate=convert(numeric(20,2),costbycard/cardsale_yeji)
    from (select a.compid,a.factdatefrom,total_yeji=SUM(ISNULL(total_yeji,0)),realytotal_yeji=SUM(ISNULL(realytotal_yeji,0)),costbycard=sum(ISNULL(costbycard,0)),cardsale_yeji=SUM(ISNULL(cardsale_yeji,0))          
		  from #comp_analysis_avg a, #yq_classed_yeji_result_ans b where b.compid=a.compid and b.ddate between factdatefrom and factdateto
		  group by  a.compid,a.factdatefrom
     )c,#comp_analysis_avg d 
    where c.compid=d.compid and c.factdatefrom=d.factdatefrom
    
      
     
	CREATE tAbLE    #cardinfo              
	(
		cardvesting			varchar(10)     NULL,   --卡归属门店 
		cardno				varchar(20)		NULL,	--卡号 
		cardtype			varchar(10)		NULL,		--卡种(会员卡类别设定) 
		salecarddate		varchar(8)		NULL    ,   --售卡日期
		cutoffdate			varchar(8)		NULL    ,   --截止有效日期
		cardstate			int				NULL       --状态(未销售, 销售未开卡, 正常使用中, 挂失转卡,越期可续卡 , 越期作废卡)
		
	)
	CREATE NONCLUSTERED index idx_cardinfo_cardno_cardvesting on #cardinfo(cardvesting,cardno)
	insert #cardinfo(cardvesting,cardno,cardtype,salecarddate,cutoffdate,cardstate)
	select cardvesting,cardno,cardtype,salecarddate,cutoffdate,cardstate
	from cardinfo,compchaininfo
	where cardvesting=relationcomp and curcomp=@compid 
	

	
	
	create table #prj_cls(                                      
                                    
		prjid varchar(20) null,--项目编号                                      
		prjcls varchar(10) null--项目类别                                      
	)                                      
	create clustered index idx_clust_prj_cls on #prj_cls(prjcls,prjid)                                      
	insert into #prj_cls(prjid,prjcls)                                      
	select prjno,prjreporttype from projectnameinfo    
	
	create table #m_d_consumeinfo(                                  
	  cscompid			varchar(10)     NULL,   --公司编号                                  
	  csbillid			varchar(20)		NULL,   --消费单号                               
	  financedate		varchar(8)		NULL,   --帐务日期 
	  cscardno			varchar(20)		NULL,   --会员卡号
	  csinfotype		int             NULL,	--消费类型  1 项目  2 产品 
	  csitemno			varchar(20)     NULL,   --项目代码                                  
	  csitemamt			float           NULL,   --金额 
	  cspaymode			varchar(5)		NULL,	--支付方式                                     
	)  
	
	create nonclustered index index_m_d_consumeinfo_csitemno  on #m_d_consumeinfo(cscompid,cscardno)  
	create nonclustered index index_m_d_consumeinfo_financedate  on #m_d_consumeinfo(cscompid,cscardno)  
	                                  
	insert into #m_d_consumeinfo(cscompid,csbillid,csinfotype,financedate,cscardno,csitemno,csitemamt,cspaymode)                                  
	select a.cscompid,a.csbillid,csinfotype,a.financedate,cscardno,csitemno,csitemamt,cspaymode
	   from mconsumeinfo a WITH (NOLOCK),dconsumeinfo b with (nolock),compchaininfo                                   
	  where a.cscompid=b.cscompid                                  
		and a.csbillid=b.csbillid                                  
		and a.cscompid =relationcomp 
		and curcomp= @compid                                  
		and financedate>=@datefroml                                   
		and financedate<=@dateto
		and cspaymode in ('4','9')
		and csinfotype=1   
 
	if exists(select 1 from compchainstruct where curcompno=@compid and complevel=4)
	begin
	
 		insert #cardinfo(cardvesting,cardno,cardtype,salecarddate,cutoffdate,cardstate)
		select cardvesting,cardno,cardtype,salecarddate,cutoffdate,cardstate
		from cardinfo,#m_d_consumeinfo
		where cscardno=cardno and cardvesting<>@compid and cscompid=@compid
	end
	
	--总会员数  
    update a set a.memcount=convert(numeric(20,2),(select sum(b.memcount) from             
              (select cardvesting,memcount=count(distinct cardno),salecarddate=isnull(salecarddate,'20100101') 
                from #cardinfo with(nolock) where  cardstate in (4,5)   group by cardvesting,salecarddate ) b               
                 where a.compid=b.cardvesting  and salecarddate<= factdateto      ))                  
   from #comp_analysis_avg a
    
 
   --有效会员数                  
   update a set a.goodmemcount=convert(numeric(20,2),(select sum(b.goodmemcount) from             
               (select c.compid,goodmemcount=count(distinct cardno),c.factdatefrom  
               from #cardinfo with(nolock) ,#m_d_consumeinfo with(nolock),#comp_analysis_avg c                  
               where cscardno=cardno   and  c.compid=cscompid and financedate between factdatefrom  and factdateto group by  c.compid,c.factdatefrom ) b                  
               where a.compid=b.compid and a.factdatefrom=b.factdatefrom ))                  
   from #comp_analysis_avg a    
   

                
  --新增会员数                  
  update a set a.addmemcount=convert(numeric(20,2),(select sum(b.addmemcount) from             
               (select cardvesting,addmemcount=count(distinct cardno),salecarddate=isnull(salecarddate,'20100101') 
                from #cardinfo where  cardstate in (4,5)   group by cardvesting,salecarddate) b  where   a.compid=b.cardvesting and  isnull(salecarddate,'20100101') between factdatefrom  and factdateto) )                 
   from #comp_analysis_avg a 
   
   --美容会员数 
   update a set a.beatycount=convert(numeric(20,2),(select sum(c.beatycount) from              
               (select a.cardvesting, beatycount=count(distinct a.cardno),salecarddate=isnull(salecarddate,'20100101')
                from #cardinfo a,cardproaccount b with(nolock),#prj_cls with(nolock)                 
                where  a.cardvesting=b.cardvesting and a.cardno=b.cardno and prjid=b.projectno and prjcls  in ('00','08','09','10','11','12','13','14','15','17','18','19','20','21','22','23')                          
                and    cardstate in (4,5) group by a.cardvesting,salecarddate  ) c                  
              where a.compid=c.cardvesting  and isnull(salecarddate,'20100101')<= factdateto))                
    from #comp_analysis_avg a     
   
   
       --有效美容会员数                  
	update a set a.beatygoodcount=convert(numeric(20,2),(select sum(b.beatygoodcount) from              
               (select c.compid, beatygoodcount=count(distinct cardno),c.factdatefrom            
                from #cardinfo with(nolock) ,#m_d_consumeinfo with(nolock),#prj_cls with(nolock),#comp_analysis_avg c          
                where cscardno=cardno and csitemno=prjid and prjcls  in ('00','08','09','10','11','12','13','14','15','17','18','19','20','21','22','23')                    
                and cspaymode='9' and  c.compid=cscompid and financedate between factdatefrom  and factdateto group by  c.compid,c.factdatefrom ) b                  
                 where a.compid=b.compid and a.factdatefrom=b.factdatefrom ))                  
	from #comp_analysis_avg a  
  
  
	--美发会员数                  
	update a set a.haircount=convert(numeric(20,2),(select sum(b.haircount) from    
	(select e.cardvesting,haircount=count(distinct e.cardno),salecarddate=isnull(salecarddate,'20100101')           
	from #cardinfo e ,cardproaccount f with(nolock),#prj_cls with(nolock)                 
	where e.cardvesting=f.cardvesting and e.cardno=f.cardno                    
	and prjid=f.projectno and prjcls in ('02','03','04','05','06','07')  and    cardstate in (4,5)
	group by e.cardvesting,salecarddate ) b                    
	where a.compid=b.cardvesting and isnull(salecarddate,'20100101') <=  factdateto))   
	from #comp_analysis_avg a   
   
    --有效美发会员数                  
	update a set a.hairgoodcount= convert(numeric(20,2),(select sum(b.hairgoodcount)  from                  
	(select  c.compid,hairgoodcount=count(distinct cardno),c.factdatefrom             
	from #cardinfo with(nolock) ,#m_d_consumeinfo with(nolock),#prj_cls with(nolock),#comp_analysis_avg c                    
	where cscardno=cardno and csitemno=prjid and prjcls  in ('02','03','04','05','06','07')                     
	and cspaymode='9'   and  c.compid=cscompid and financedate between factdatefrom  and factdateto group by  c.compid,c.factdatefrom)  b                  
	where a.compid=b.compid and a.factdatefrom=b.factdatefrom ))            
	from #comp_analysis_avg a    
 
	if(@ordertype='1')--按完整月            
	begin        
		create table #abc_card          
		(                                                    
			compid			varchar(10)		null,                                                    
			cardid			varchar(20)		null,	--卡号          
			xiangmu			varchar(20)		null,	--项目编号           
			zhifu			int				null,	--支付方式         
			factdatefrom	varchar(10)		null,            
			targgettodate	varchar(10)		null,                                                
		)          
		--上月消费卡号          
		insert #abc_card (compid,cardid,xiangmu,zhifu,factdatefrom,targgettodate)          
		select cscompid,cscardno,csitemno,cspaymode,factdatefrom,factdateto from #m_d_consumeinfo,#comp_analysis_avg with(nolock)                               
		where financedate between factdatefroml  and factdatetol and cscompid=compid          
	             
	             
		create table #abc_card01          
		(                                                    
			compid			varchar(10)		null,                                                    
			cardid			varchar(20)		null,--卡号          
			xiangmu			varchar(20)		null,--项目编号          
			zhifu			int				null,--支付方式         
			factdatefrom	varchar(10)		null,            
			targgettodate	varchar(10)		null,                             
		)          
	           
		--本月消费卡号          
		insert #abc_card01 (compid,cardid,xiangmu,zhifu,factdatefrom,targgettodate)          
		select cscompid,cscardno,csitemno,cspaymode,factdatefrom,factdateto from #m_d_consumeinfo,#comp_analysis_avg with(nolock)                               
		  where financedate between factdatefrom  and factdateto and cscompid=compid                
	           
	           
	         
		 --有效美容会员数                  
		update a set a.beatygoodcountl=convert(numeric(20,2),(select sum(b.beatygoodcount) from              
				   (select c.compid, beatygoodcount=count(distinct cardno),c.factdatefrom            
					from #cardinfo with(nolock) ,#m_d_consumeinfo with(nolock),#prj_cls with(nolock),#comp_analysis_avg c          
					where cscardno=cardno and csitemno=prjid and prjcls  in ('00','08','09','10','11','12','13','14','15','17','18','19','20','21','22','23')                    
					  and cspaymode='9' and  c.compid=cscompid and financedate between factdatefroml  and factdatetol group by  c.compid,c.factdatefrom ) b                  
		where a.compid=b.compid and a.factdatefrom=b.factdatefrom ))                  
		from #comp_analysis_avg a            
	          
		 --有效美发会员数                  
		update a set a.hairgoodcountl= convert(numeric(20,2),(select sum(b.hairgoodcount)  from                  
					(select  c.compid,hairgoodcount=count(distinct cardno),c.factdatefrom             
					from #cardinfo with(nolock) ,#m_d_consumeinfo with(nolock),#prj_cls with(nolock),#comp_analysis_avg c                    
					where cscardno=cardno  and csitemno=prjid and prjcls in ('02','03','04','05','06','07')                     
					  and cspaymode='9'  and  c.compid=cscompid and financedate between factdatefroml  and factdatetol group by  c.compid,c.factdatefrom)  b                  
		where a.compid=b.compid and a.factdatefrom=b.factdatefrom ))            
		from #comp_analysis_avg a           
	         
		--美容疗程两月都来会员数                       
		update a set a.beatyallcountl=convert(numeric(20,2),b.beatyallcountl)                              
		from #comp_analysis_avg a  , (select f.compid,beatyallcountl=count(distinct f.cardid),e.factdatefrom        
										    from #abc_card e with(nolock),#abc_card01  f with(nolock),#prj_cls g with(nolock)          
											where e.compid = f.compid and e.cardid = f.cardid and f.zhifu = '9'          
											  and g.prjid = f.xiangmu and prjcls  in ('00','08','09','10','11','12','13','14','15','17','18','19','20','21','22','23')        
											  and e.factdatefrom=f.factdatefrom           
											group by f.compid,e.factdatefrom) b                     
		where a.compid=b.compid  and a.factdatefrom=b.factdatefrom        
	          
	         
		 --美容疗程两月都来会员数                       
		update a set a.hairallcountl=convert(numeric(20,2),b.hairallcountl)                              
		from #comp_analysis_avg a  ,(select f.compid,hairallcountl=count(distinct f.cardid),e.factdatefrom        
									   from #abc_card e with(nolock),#abc_card01  f with(nolock),#prj_cls g with(nolock)          
									   where e.compid = f.compid and e.cardid = f.cardid and f.zhifu = '9'          
									    and g.prjid = f.xiangmu and prjcls  in ('02','03','04','05','06','07')          
										and e.factdatefrom=f.factdatefrom         
										group by f.compid,e.factdatefrom) b                     
		where a.compid=b.compid  and a.factdatefrom=b.factdatefrom        
	          
	        
	        
		update #comp_analysis_avg set beatygoodcountl=1 where ISNULL(beatygoodcountl,0)=0                          
		update #comp_analysis_avg set beatygoodrate=convert(numeric(20,3),beatyallcountl/beatygoodcountl)                          
	           
		update #comp_analysis_avg set hairallcountl=1 where ISNULL(hairallcountl,0)=0                          
		update #comp_analysis_avg set hairgoodrate=convert(numeric(20,3),hairallcountl*1.0/hairgoodcount)          
	                         
		drop table #abc_card01        
		drop table #abc_card        
	-----------------------------------------------------------------------------------------------------          
	end        
	else        
	begin        
		update #comp_analysis_avg set beatycount=1 where ISNULL(beatycount,0)=0                    
		update #comp_analysis_avg set haircount=1 where ISNULL(haircount,0)=0                    
		update #comp_analysis_avg set beatygoodrate=convert(numeric(20,3),beatygoodcount/beatycount)                    
		update #comp_analysis_avg set hairgoodrate=convert(numeric(20,3),hairgoodcount/haircount)                    
	                         
		update #comp_analysis_avg set beatycount=0 where ISNULL(beatycount,0)=1                    
		update #comp_analysis_avg set haircount=0 where ISNULL(haircount,0)=1             
	end 
	
	--新增疗程数                  
	update a set a.addbeatypromems=d.addbeatypromems,addhairpromems=d.addhairpromems,addpromems=d.addpromems                  
	from #comp_analysis_avg a,                  
   ( select e.changecompid,c.factdatefrom,addbeatypromems=convert(numeric(20,2),sum(case when prjtype='4' then isnull(changeprocount,0)/ISNULL(ysalecount,1) end )),                  
			addhairpromems=convert(numeric(20,2),sum(case when prjtype IN ('3','6') then   isnull(changeprocount,0)/ISNULL(ysalecount,1)  end)),                  
			addpromems=convert(numeric(20,2),SUM(isnull(changeprocount,0)/ISNULL(ysalecount,1)))                  
	from mproexchangeinfo  e ,dproexchangeinfo f,#comp_analysis_avg c ,projectinfo                  
	where  e.changecompid=f.changecompid and e.changebillid=f.changebillid and e.changecompid=c.compid and changedate  between c.factdatefrom  and c.factdateto 
	  and prjmodeId='SPM' and prjno=changeproid              
	group by e.changecompid,c.factdatefrom ) d                  
	where a.compid=d.changecompid  and a.factdatefrom=d.factdatefrom    
	
	--计算离职指标
	create table #leavelstaff    
	(    
		compid		varchar(10) null,    
		inid		varchar(20) null,    
		inserdate	varchar(10) null,    
		leveldate	varchar(10) null,    
		postion		varchar(10) null,    
	)  
	
	insert #leavelstaff(compid,inid,leveldate,postion)    
	select oldcompid,a.manageno,max(effectivedate),position   
	  from staffhistory a,compchaininfo ,staffinfo b with(nolock)                  
      where changetype='3' and oldcompid=relationcomp and curcomp=@compid    
        and a.manageno=b.Manageno and ISNULL(curstate,'1')='3'      
        and effectivedate>=@datefroml                                           
	    and effectivedate<=@dateto                        
      group by oldcompid,a.manageno,position    
          
            
    update #leavelstaff set inserdate=isnull((select MIN(effectivedate) from staffhistory where manageno=inid and changetype='4' ),'') 
    update #leavelstaff set inserdate='20120101' where ISNULL(inserdate,'')=''   
    
                  --离职人数                  
    update a set a.leavelcount =convert(numeric(20,2),(select sum(b.leavelcount)  from                
    (select compid,leavelcount=count(distinct inid),leveldate from #leavelstaff                  
      where DATEDIFF ( MONTH ,inserdate ,leveldate )>3                  
      group by compid,leveldate ) b                  
     where a.compid=b.compid and leveldate   between factdatefrom  and factdateto))            
      from #comp_analysis_avg a      
          
   --核心员工    
    update a set a.leavelcorecount =convert(numeric(20,2),(select sum(b.leavelcorecount)  from                
    (select compid,leavelcorecount=count(distinct inid),leveldate from #leavelstaff                  
      where DATEDIFF ( MONTH ,inserdate ,leveldate )>3  and  postion in ('001','00101','00102','00103','00104','00105','008','006','007')         
      group by compid,leveldate ) b                  
     where a.compid=b.compid and leveldate   between factdatefrom  and factdateto))            
      from #comp_analysis_avg a 
             
	update #comp_analysis_avg set totalxuyejizb=paramvalue from #comp_analysis_avg a,sysparaminfo b where a.compid=b.compid and paramid='SP033'            
	update #comp_analysis_avg set costcardratezb=paramvalue from #comp_analysis_avg a,sysparaminfo b where a.compid=b.compid and paramid='SP034'            
	update #comp_analysis_avg set goodmemcountzb=paramvalue from #comp_analysis_avg a,sysparaminfo b where a.compid=b.compid and paramid='SP035'            
	update #comp_analysis_avg set addpromemszb=paramvalue from #comp_analysis_avg a,sysparaminfo b where a.compid=b.compid and paramid='SP036'            
	update #comp_analysis_avg set beatygoodratezb=paramvalue from #comp_analysis_avg a,sysparaminfo b where a.compid=b.compid and paramid='SP037'            
	update #comp_analysis_avg set hairgoodratezb=paramvalue from #comp_analysis_avg a,sysparaminfo b where a.compid=b.compid and paramid='SP038'            
	update #comp_analysis_avg set leavelcountzb=paramvalue from #comp_analysis_avg a,sysparaminfo b where a.compid=b.compid and paramid='SP039'            
                 
                
  select seardate,compid,compname,totalxuyeji,totalshiyeji,costcardrate,memcount,goodmemcount,addmemcount,                
   addbeatypromems,addhairpromems,addpromems,beatycount,beatygoodcount,beatygoodrate,haircount,hairgoodcount,    
   hairgoodrate,leavelcount,leavelcorecount,totalxuyejizb,costcardratezb,goodmemcountzb,addpromemszb,beatygoodratezb,hairgoodratezb,leavelcountzb            
   from #comp_analysis_avg order by compid                        

   drop table #leavelstaff 
   drop table #cardinfo
   drop table #prj_cls
   drop table #m_d_consumeinfo
   drop table #yq_classed_yeji_result_ans          
   drop table #comp_analysis_avg                    
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_check_trade_bydate]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_check_trade_bydate] 
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
GO
/****** Object:  StoredProcedure [dbo].[upg_changestock_analysis]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_changestock_analysis](    
	@compid			varchar(10),			--门店编号    
	@datefrom		varchar(10),			--日期   
	@dateto			varchar(10),			--日期  
	@goodsfrom		varchar(20),			--物品编号   
	@goodsto		varchar(20),			--物品编号   
	@wareid			varchar(20)			--仓库编号
)    
as
begin    

 create table #changedetail      
 (      
		id					int identity		not null,      
		itemno				varchar(30)				null, -- item no      
		itemname			varchar(50)				null, -- item no 
		storage				varchar(20)				null, -- 仓库      
		storagename			varchar(60)				null, -- 仓库      
		ddate				varchar(8)				null , -- 异动日期     
		ttime				varchar(8)				null , --  异动时间 
		insercount			float					null , -- 入库数量      
		outrecount			float					null , -- 出库数量      
		changetype			varchar(20)				null , -- 异动类别      
		billno				varchar(20)				null , -- 异动单号      
		stock				float					null , -- 库存数量  
		primary key (id),      
 )  
 
	insert #changedetail( itemno, storage, ddate, ttime,insercount, outrecount ,changetype, billno )
	select  changegoodsno,changewareid,changedate,changetime,
  	case a.changetype when 1 then changecount else 0 end,
  	case a.changetype when 1 then 0 else changecount end,      
	case a.changetype when '1' then case changeoption when 1 then '正常入库' when 2 then '盘盈入库' when 3 then '客户退货' when 4 then '调拨入库' end     
                        when '2' then case changeoption when 1 then '正常出库' when 2 then '盘亏出库' when 3 then '供应商退货' when 4 then '调拨出库' when 5 then '损坏' when 6 then '赠送' else '出库' end    
                        when '3' then '销货'     
                        when '5' then '套餐'     
                        when '6' then '套餐'     
                        else '消耗' end,a.changebillno
                from  mgoodsstockinfo a,dgoodsstockinfo b  
                where a.changecompid= b.changecompid       
                  and a.changetype= b.changetype       
                  and a.changebillno= b.changebillno 
                  and a.changedate  between @datefrom and @dateto  
                  and a.changecompid= @compid  
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')  
                  and (changewareid = @wareid or ISNULL(@wareid,'')='')  
       order by a.changecompid,changegoodsno,changedate,changetime     
       
        update #changedetail set stock =       
  isnull((select sum(insercount) from #changedetail A       
  where A.id <= #changedetail.id and A.itemno = #changedetail.itemno and A.storage = #changedetail.storage ), 0)    
      
 update #changedetail set stock = stock -       
  isnull((select sum(outrecount) from #changedetail A       
  where A.id <= #changedetail.id and A.itemno = #changedetail.itemno and A.storage = #changedetail.storage ), 0)      
      
      
 delete #changedetail where ddate < @datefrom or ddate > @dateto     
   --更新仓库名称      
 update a      
	  set a.storagename =b.warehousename      
	  from #changedetail a,compwarehouse b      
	  where a.storage = b.warehouseno      
		and b.compno = @compid      
  --更新产品名      
  update a set  a.itemname=b.goodsname
  from #changedetail a, goodsnameinfo b 
  where a.itemno = b.goodsno  
       

  select itemno,itemname,storage,storagename,ddate,insercount,outrecount,changetype,billno,stock from #changedetail order by ddate,ttime
  drop table #changedetail
     
end
GO
/****** Object:  StoredProcedure [dbo].[upg_analysis_system_error_new]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_analysis_system_error_new]                      
(                      
	@compid varchar(10),                      
	@fromdate varchar(8),                      
	@todate varchar(8)                      
)                      
as                      
begin                      
 declare @TIME_VALVE int                      
 set @TIME_VALVE = 5--单位：秒                      
 create table #error                      
 (                      
  errid  varchar(5)  not null,                      
  description varchar(500) null                      
 )                      
 insert into #error(errid,description) values('E0001','单据重复')                                        
 insert into #error(errid,description) values('E0003','业绩未分享或未完全分享')                      
 --1-开卡单 2-充值单 3-还款单 4-折扣转卡单 5-收购转卡单 6-竞争转卡单 7-会员并卡单 8-退卡单                      
 --9-收银单                       
               
  create table #result                      
 (                      
  compid  varchar(10)  not null,                      
  billtype varchar(5)  null,                      
  errid  varchar(5)  null,                      
  errdescript varchar(500) null,                      
  remark  varchar(2048) null,                      
 )                   
  create table #emp_deperror(                        
  compid varchar(10) null,                        
  inid varchar(20) null,--内部编号                        
  empid varchar(20) null,--员工编号       
  emppos varchar(10) null,                     
  empdep varchar(10) null,--员工部门             
  datefrom varchar(8) null,                        
  dateto varchar(8) null,                        
 )        
 insert into #emp_deperror(compid,empid,emppos)                                
 select distinct compno,staffno,position                        
 from staffinfo with (nolock)                                
 where @compid=compno                   
  declare @mfstaffshareamt		float
  declare @mfstaffsharecount	int   
  declare @trstaffshareamt		float
  declare @trstaffsharecount	int            
 --判断单据重复的规则：                      
 --1、在时间阀值区间内，有两张以及两张以上的单据内容相同；                      
                       
 CREATE tAbLE	#msalecardinfo               -- 会员卡销售单
	(
		salecompid			varchar(10)			Not NULL,   --公司编号
		salebillid			varchar(20)			Not NULL,   --销售单号
		saledate			varchar(8)				NULL,   --销售日期
		salecardno			varchar(20)				NULL,   --销售卡号
		salecardtype		varchar(20)				NULL,   --销售卡类型
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		salekeepamt			float					NULL,	--储值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)     
    insert #msalecardinfo(salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt)
	select salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt
    from msalecardinfo, dpayinfo
    where salebillid=paybillid and salecompid=paycompid and paybilltype='SK'
      and salecompid=@compid and financedate between @fromdate and @todate and ISNULL(salekeepamt,0)<>0 and ISNULL(salebakflag,0)=0
                              
         

              
   if exists(select 1 from #msalecardinfo)                        
   begin              
		declare @e0003_salebillid varchar(20) 
		declare @e0003_salecardno varchar(20)           
		declare @e0003_bill_msalecardinfo varchar(1024)                        
		set @e0003_bill_msalecardinfo = ''                        
                
		declare @e0003_firstsalerid varchar(20), @e0003_secondsalerid varchar(20), @e0003_thirdsalerid varchar(20),
				@e0003_fourthsalerid varchar(20),@e0003_fifthsalerid varchar(20) , @e0003_sixthsalerid varchar(20) ,
				@e0003_seventhsalerid varchar(20) ,@e0003_eighthsalerid varchar(20) , @e0003_ninthsalerid varchar(20) , @e0003_tenthsalerid varchar(20)   
		declare @e0003_firstsaleamt float, @e0003_secondsaleamt float, @e0003_thirdsaleamt float,
				@e0003_fourthsaleamt float, @e0003_fifthsaleamt float, @e0003_sixthsaleamt float, 
				@e0003_seventhsaleamt float, @e0003_eighthsaleamt float, @e0003_ninthsaleamt float,@e0003_tenthsaleamt float               
                
		declare cur_#msalecardinfo03err3 cursor for                        
		select salebillid,salecardno,
				isnull(firstsalerid,''),isnull(secondsalerid,''),isnull(thirdsalerid,''),isnull(fourthsalerid,''),isnull(fifthsalerid,''),isnull(sixthsalerid,''),isnull(seventhsalerid,''),isnull(eighthsalerid,''),isnull(ninthsalerid,''),isnull(tenthsalerid,''),
					firstsaleamt,secondsaleamt,thirdsaleamt,fourthsaleamt,fifthsaleamt,sixthsaleamt,seventhsaleamt,eighthsaleamt,ninthsaleamt,tenthsaleamt
				 from #msalecardinfo                         
		open cur_#msalecardinfo03err3                        
		fetch cur_#msalecardinfo03err3 into  @e0003_salebillid,@e0003_salecardno,
		@e0003_firstsalerid,@e0003_secondsalerid,@e0003_thirdsalerid,@e0003_fourthsalerid,
		@e0003_fifthsalerid,@e0003_sixthsalerid,@e0003_seventhsalerid,@e0003_eighthsalerid,
		@e0003_ninthsalerid,@e0003_tenthsalerid,              
        @e0003_firstsaleamt,@e0003_secondsaleamt,@e0003_thirdsaleamt,@e0003_fourthsaleamt,
        @e0003_fifthsaleamt,@e0003_sixthsaleamt,@e0003_seventhsaleamt,@e0003_eighthsaleamt, 
        @e0003_ninthsaleamt,@e0003_tenthsaleamt            
                                    
		while(@@fetch_status=0)                        
		begin  
		
			set @mfstaffshareamt=0
			set @mfstaffsharecount=0
			set @trstaffshareamt=0
			set @trstaffsharecount=0
			set @e0003_bill_msalecardinfo=''
			--第一销售
			if(isnull(@e0003_firstsalerid,'')<>'' and ISNULL(@e0003_firstsaleamt,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_firstsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_firstsaleamt,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_firstsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_firstsaleamt,0)>10000)
						  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第一销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_firstsaleamt,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第二销售
			if(isnull(@e0003_secondsalerid,'')<>'' and ISNULL(@e0003_secondsaleamt,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_secondsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_secondsaleamt,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_secondsaleamt=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_secondsaleamt,0)>10000)
						  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第二销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_secondsaleamt,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第三销售
			if(isnull(@e0003_thirdsalerid,'')<>'' and ISNULL(@e0003_thirdsaleamt,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_thirdsaleamt,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_thirdsaleamt,0)>10000)
						  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第三销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_thirdsaleamt,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第四销售
			if(isnull(@e0003_fourthsalerid,'')<>'' and ISNULL(@e0003_fourthsaleamt,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fourthsaleamt,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_fourthsaleamt,0)>10000)
						  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第四销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fourthsaleamt,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第五销售
			if(isnull(@e0003_fifthsalerid,'')<>'' and ISNULL(@e0003_fifthsaleamt,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fifthsaleamt,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_fifthsaleamt,0)>10000)
						  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第五销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fifthsaleamt,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第六销售
			if(isnull(@e0003_sixthsalerid,'')<>'' and ISNULL(@e0003_sixthsaleamt,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_sixthsaleamt,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_sixthsaleamt,0)>10000)
						  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第六销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_sixthsaleamt,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第七销售
			if(isnull(@e0003_seventhsalerid,'')<>'' and ISNULL(@e0003_seventhsaleamt,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_seventhsaleamt,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_seventhsaleamt,0)>10000)
						  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第七销售烫发师分享业绩不能超过10000' 
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_seventhsaleamt,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			              
			--第八销售
			if(isnull(@e0003_eighthsalerid,'')<>'' and ISNULL(@e0003_eighthsaleamt,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_eighthsaleamt,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_eighthsaleamt,0)>10000)
						  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第八销售烫发师分享业绩不能超过10000' 
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_eighthsaleamt,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第九销售
			if(isnull(@e0003_ninthsalerid,'')<>'' and ISNULL(@e0003_ninthsaleamt,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_ninthsaleamt,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_ninthsaleamt,0)>10000)
						  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第九销售烫发师分享业绩不能超过10000' 
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_ninthsaleamt,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			
			--第十销售
			if(isnull(@e0003_tenthsalerid,'')<>'' and ISNULL(@e0003_tenthsaleamt,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_tenthsaleamt,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_tenthsaleamt,0)>10000)
						  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第十销售烫发师分享业绩不能超过10000'  
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_tenthsaleamt,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end    
			if(ISNULL(@trstaffshareamt,0)>ISNULL(@mfstaffshareamt,0))
			     set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'美发人员与对应的烫发师分享业绩不匹配'               
			 if(ISNULL(@trstaffsharecount,0)>ISNULL(@mfstaffsharecount,0))
			     set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'分享美发人员少于烫发师人数'
			if(@e0003_bill_msalecardinfo<>'')              
			begin                
				insert into #result(compid,billtype,errid,remark)                        
				values(@compid,'1','E0003',@e0003_bill_msalecardinfo+'单号'+@e0003_salebillid)                
			end                
                      
			fetch cur_#msalecardinfo03err3 into  @e0003_salebillid,@e0003_salecardno,
		@e0003_firstsalerid,@e0003_secondsalerid,@e0003_thirdsalerid,@e0003_fourthsalerid,
		@e0003_fifthsalerid,@e0003_sixthsalerid,@e0003_seventhsalerid,@e0003_eighthsalerid,
		@e0003_ninthsalerid,@e0003_tenthsalerid,              
        @e0003_firstsaleamt,@e0003_secondsaleamt,@e0003_thirdsaleamt,@e0003_fourthsaleamt,
        @e0003_fifthsaleamt,@e0003_sixthsaleamt,@e0003_seventhsaleamt,@e0003_eighthsaleamt, 
        @e0003_ninthsaleamt,@e0003_tenthsaleamt               
		end                        
		close cur_#msalecardinfo03err3                        
		deallocate cur_#msalecardinfo03err3                        
                        
	end                        
                                       
	drop table #msalecardinfo                      
                    
    CREATE tAbLE	#mcardrechargeinfo              -- 会员卡充值单
	(
		rechargecompid			varchar(10)			Not NULL,   --充值门店
		rechargebillid			varchar(20)			Not NULL,   --充值单号 
		rechargedate			varchar(8)				NULL,   --充值日期 
		rechargetime			varchar(6)				NULL,   --充值时间 
		rechargecardno			varchar(20)				NULL,   --会员卡号
		rechargecardtype		varchar(10)				NULL,   --卡类型
		rechargetype			int						NULL,   --续费方式( 0充值 ,6还款 ,)
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		rechargekeepamt		float					NULL,   --充值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)     
	
	insert #mcardrechargeinfo(rechargecompid,rechargebillid,rechargedate,rechargetype,rechargecardno,rechargecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt)
	select rechargecompid,rechargebillid,rechargedate,rechargetype,rechargecardno,rechargecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt
    from mcardrechargeinfo, dpayinfo
    where rechargebillid=paybillid and rechargecompid=paycompid and paybilltype='CZ'
      and rechargecompid=@compid and financedate between @fromdate and @todate and ISNULL(rechargekeepamt,0)<>0 and ISNULL(salebakflag,0)=0
     
   if exists(select 1 from #mcardrechargeinfo)                        
   begin                        
		declare @e0003_bill_mcardrechargeinfo varchar(1024)                        
		set @e0003_bill_mcardrechargeinfo = ''                        
        declare @e0003_rechargebillid varchar(20)         
		declare @e0003_rechargecardno varchar(20)                
		declare @e0003_firstsalerid_cz varchar(20), @e0003_secondsalerid_cz varchar(20), @e0003_thirdsalerid_cz varchar(20),
				@e0003_fourthsalerid_cz varchar(20),@e0003_fifthsalerid_cz varchar(20) , @e0003_sixthsalerid_cz varchar(20) ,
				@e0003_seventhsalerid_cz varchar(20) ,@e0003_eighthsalerid_cz varchar(20) , @e0003_ninthsalerid_cz varchar(20) , @e0003_tenthsalerid_cz varchar(20)   
		declare @e0003_firstsaleamt_cz float, @e0003_secondsaleamt_cz float, @e0003_thirdsaleamt_cz float,
				@e0003_fourthsaleamt_cz float, @e0003_fifthsaleamt_cz float, @e0003_sixthsaleamt_cz float, 
				@e0003_seventhsaleamt_cz float, @e0003_eighthsaleamt_cz float, @e0003_ninthsaleamt_cz float,@e0003_tenthsaleamt_cz float  
				         
		declare cur_#mcardrechargeinfo03err3 cursor for                        
		select rechargebillid,rechargecardno, 
				isnull(firstsalerid,''),isnull(secondsalerid,''),isnull(thirdsalerid,''),isnull(fourthsalerid,''),isnull(fifthsalerid,''),isnull(sixthsalerid,''),isnull(seventhsalerid,''),isnull(eighthsalerid,''),isnull(ninthsalerid,''),isnull(tenthsalerid,''),
				firstsaleamt,secondsaleamt,thirdsaleamt,fourthsaleamt,fifthsaleamt,sixthsaleamt,seventhsaleamt,eighthsaleamt,ninthsaleamt,tenthsaleamt
		from #mcardrechargeinfo              
		open cur_#mcardrechargeinfo03err3                        
		fetch cur_#mcardrechargeinfo03err3 into  @e0003_rechargebillid,@e0003_rechargecardno,
		@e0003_firstsalerid_cz,@e0003_secondsalerid_cz,@e0003_thirdsalerid_cz,@e0003_fourthsalerid_cz,
		@e0003_fifthsalerid_cz,@e0003_sixthsalerid_cz,@e0003_seventhsalerid_cz,@e0003_eighthsalerid_cz,
		@e0003_ninthsalerid_cz,@e0003_tenthsalerid_cz,              
        @e0003_firstsaleamt_cz,@e0003_secondsaleamt_cz,@e0003_thirdsaleamt_cz,@e0003_fourthsaleamt_cz,
        @e0003_fifthsaleamt_cz,@e0003_sixthsaleamt_cz,@e0003_seventhsaleamt_cz,@e0003_eighthsaleamt_cz, 
        @e0003_ninthsaleamt_cz,@e0003_tenthsaleamt_cz               
	                                    
		while(@@fetch_status=0)                        
		begin         
	                
			set @e0003_bill_mcardrechargeinfo=''
			set @mfstaffshareamt=0
			set @mfstaffsharecount=0
			set @trstaffshareamt=0
			set @trstaffsharecount=0
		
			--第一销售
			if(isnull(@e0003_firstsalerid_cz,'')<>'' and ISNULL(@e0003_firstsaleamt_cz,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_firstsalerid_cz=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_firstsaleamt_cz,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_firstsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_firstsaleamt_cz,0)>10000)
						  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第一销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_firstsaleamt_cz,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第二销售
			if(isnull(@e0003_secondsalerid_cz,'')<>'' and ISNULL(@e0003_secondsaleamt_cz,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_secondsalerid_cz=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_secondsaleamt_cz,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_secondsaleamt_cz=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_secondsaleamt_cz,0)>10000)
						  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第二销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_secondsaleamt_cz,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第三销售
			if(isnull(@e0003_thirdsalerid_cz,'')<>'' and ISNULL(@e0003_thirdsaleamt_cz,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid_cz=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_thirdsaleamt_cz,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_thirdsaleamt_cz,0)>10000)
						  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第三销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_thirdsaleamt_cz,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第四销售
			if(isnull(@e0003_fourthsalerid_cz,'')<>'' and ISNULL(@e0003_fourthsaleamt_cz,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid_cz=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fourthsaleamt_cz,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_fourthsaleamt_cz,0)>10000)
						  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第四销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fourthsaleamt_cz,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第五销售
	
			if(isnull(@e0003_fifthsalerid_cz,'')<>'' and ISNULL(@e0003_fifthsaleamt_cz,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid_cz=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fifthsaleamt_cz,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_fifthsaleamt_cz,0)>10000)
						  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第五销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fifthsaleamt_cz,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第六销售
			if(isnull(@e0003_sixthsalerid_cz,'')<>'' and ISNULL(@e0003_sixthsaleamt_cz,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_sixthsaleamt_cz,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_sixthsaleamt_cz,0)>10000)
						  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第六销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_sixthsaleamt_cz,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第七销售
			if(isnull(@e0003_seventhsalerid_cz,'')<>'' and ISNULL(@e0003_seventhsaleamt_cz,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid_cz=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_seventhsaleamt_cz,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_seventhsaleamt_cz,0)>10000)
						  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第七销售烫发师分享业绩不能超过10000' 
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_seventhsaleamt_cz,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			              
			--第八销售
			if(isnull(@e0003_eighthsalerid_cz,'')<>'' and ISNULL(@e0003_eighthsaleamt_cz,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid_cz=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_eighthsaleamt_cz,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_eighthsaleamt_cz,0)>10000)
						  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第八销售烫发师分享业绩不能超过10000' 
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_eighthsaleamt_cz,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第九销售
			if(isnull(@e0003_ninthsalerid_cz,'')<>'' and ISNULL(@e0003_ninthsaleamt_cz,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid_cz=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_ninthsaleamt_cz,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_ninthsaleamt_cz,0)>10000)
						  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第九销售烫发师分享业绩不能超过10000' 
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_ninthsaleamt_cz,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			
			--第十销售
			if(isnull(@e0003_tenthsalerid_cz,'')<>'' and ISNULL(@e0003_tenthsaleamt_cz,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid_cz=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_tenthsaleamt_cz,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_tenthsaleamt_cz,0)>10000)
						  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第十销售烫发师分享业绩不能超过10000'  
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_tenthsaleamt_cz,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end    
			
			if(ISNULL(@trstaffshareamt,0)>ISNULL(@mfstaffshareamt,0))
			     set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'美发人员与对应的烫发师分享业绩不匹配'               
			 if(ISNULL(@trstaffsharecount,0)>ISNULL(@mfstaffsharecount,0))
			     set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_msalecardinfo,'')+'分享美发人员少于烫发师人数'               
			
			if(@e0003_bill_mcardrechargeinfo<>'')              
			begin                
				insert into #result(compid,billtype,errid,remark)                        
				values(@compid,'2','E0003',@e0003_bill_mcardrechargeinfo+'单号'+@e0003_rechargebillid)                
			end                
	                      
			fetch cur_#mcardrechargeinfo03err3 into  @e0003_rechargebillid,@e0003_rechargecardno,
				@e0003_firstsalerid_cz,@e0003_secondsalerid_cz,@e0003_thirdsalerid_cz,@e0003_fourthsalerid_cz,
				@e0003_fifthsalerid_cz,@e0003_sixthsalerid_cz,@e0003_seventhsalerid_cz,@e0003_eighthsalerid_cz,
				@e0003_ninthsalerid_cz,@e0003_tenthsalerid_cz,              
				@e0003_firstsaleamt_cz,@e0003_secondsaleamt_cz,@e0003_thirdsaleamt_cz,@e0003_fourthsaleamt_cz,
				@e0003_fifthsaleamt_cz,@e0003_sixthsaleamt_cz,@e0003_seventhsaleamt_cz,@e0003_eighthsaleamt_cz, 
				@e0003_ninthsaleamt_cz,@e0003_tenthsaleamt_cz   
      end                        
	  close cur_#mcardrechargeinfo03err3                        
	  deallocate cur_#mcardrechargeinfo03err3                      
	  end
      
     drop table #mcardrechargeinfo    
     
     
        CREATE tAbLE	#mcardchangeinfo             -- 会员卡异动
	(
		changecompid		varchar(10)			Not NULL,   --充值门店
		changebillid		varchar(20)			Not NULL,   --充值单号 
		changetype			int					Not NULL,
		changedate			varchar(8)				NULL,   --充值日期 
		changetime			varchar(6)				NULL,   --充值时间 
		changeaftercardno	varchar(20)				NULL,   --会员卡号
		changeaftercardtype	varchar(10)				NULL,   --卡类型
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		changefillamt		float					NULL,   --充值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)   
	
	insert #mcardchangeinfo(changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt)
	select changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt
    from mcardchangeinfo, dpayinfo
    where changebillid=paybillid and changecompid=paycompid and paybilltype in ('ZK','TK')
      and changecompid=@compid and financedate between @fromdate and @todate and ISNULL(changefillamt,0)<>0 and ISNULL(salebakflag,0)=0
      
      
    if exists(select 1 from #mcardchangeinfo)                        
	begin                        
		declare @e0003_changebillid varchar(20)	
		declare @e0003_bill_mcardchangeinfo varchar(1024)                        
		set @e0003_bill_mcardchangeinfo = ''                        
		declare @e0003_firstsalerid_zk varchar(20), @e0003_secondsalerid_zk varchar(20), @e0003_thirdsalerid_zk varchar(20),
				@e0003_fourthsalerid_zk varchar(20),@e0003_fifthsalerid_zk varchar(20) , @e0003_sixthsalerid_zk varchar(20) ,
				@e0003_seventhsalerid_zk varchar(20) ,@e0003_eighthsalerid_zk varchar(20) , @e0003_ninthsalerid_zk varchar(20) , @e0003_tenthsalerid_zk varchar(20)   
		declare @e0003_firstsaleamt_zk float, @e0003_secondsaleamt_zk float, @e0003_thirdsaleamt_zk float,
				@e0003_fourthsaleamt_zk float, @e0003_fifthsaleamt_zk float, @e0003_sixthsaleamt_zk float, 
				@e0003_seventhsaleamt_zk float, @e0003_eighthsaleamt_zk float, @e0003_ninthsaleamt_zk float,@e0003_tenthsaleamt_zk float  
			        
		declare cur_#mcardchangeinfoerr3 cursor for                        
		select changebillid, 
				isnull(firstsalerid,''),isnull(secondsalerid,''),isnull(thirdsalerid,''),isnull(fourthsalerid,''),isnull(fifthsalerid,''),isnull(sixthsalerid,''),isnull(seventhsalerid,''),isnull(eighthsalerid,''),isnull(ninthsalerid,''),isnull(tenthsalerid,''),
				firstsaleamt,secondsaleamt,thirdsaleamt,fourthsaleamt,fifthsaleamt,sixthsaleamt,seventhsaleamt,eighthsaleamt,ninthsaleamt,tenthsaleamt
		from #mcardchangeinfo              
		open cur_#mcardchangeinfoerr3                        
		fetch cur_#mcardchangeinfoerr3 into  @e0003_changebillid,
				@e0003_firstsalerid_zk,@e0003_secondsalerid_zk,@e0003_thirdsalerid_zk,@e0003_fourthsalerid_zk,
				@e0003_fifthsalerid_zk,@e0003_sixthsalerid_zk,@e0003_seventhsalerid_zk,@e0003_eighthsalerid_zk,
				@e0003_ninthsalerid_zk,@e0003_tenthsalerid_zk,              
				@e0003_firstsaleamt_zk,@e0003_secondsaleamt_zk,@e0003_thirdsaleamt_zk,@e0003_fourthsaleamt_zk,
				@e0003_fifthsaleamt_zk,@e0003_sixthsaleamt_zk,@e0003_seventhsaleamt_zk,@e0003_eighthsaleamt_zk, 
				@e0003_ninthsaleamt_zk,@e0003_tenthsaleamt_zk                     
		while(@@fetch_status=0)                        
		begin  
			set @e0003_bill_mcardchangeinfo=''
			set @mfstaffshareamt=0
			set @mfstaffsharecount=0
			set @trstaffshareamt=0
			set @trstaffsharecount=0
			--第一销售
			if(isnull(@e0003_firstsalerid_zk,'')<>'' and ISNULL(@e0003_firstsaleamt_zk,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_firstsalerid_zk=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_firstsaleamt_zk,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_firstsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_firstsaleamt_zk,0)>10000)
						  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第一销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_firstsaleamt_zk,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第二销售
			if(isnull(@e0003_secondsalerid_zk,'')<>'' and ISNULL(@e0003_secondsaleamt_zk,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_secondsalerid_zk=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_secondsaleamt_zk,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_secondsaleamt_zk=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_secondsaleamt_zk,0)>10000)
						  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第二销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_secondsaleamt_zk,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第三销售
			if(isnull(@e0003_thirdsalerid_zk,'')<>'' and ISNULL(@e0003_thirdsaleamt_zk,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid_zk=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_thirdsaleamt_zk,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_thirdsaleamt_zk,0)>10000)
						  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第三销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_thirdsaleamt_zk,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第四销售
			if(isnull(@e0003_fourthsalerid_zk,'')<>'' and ISNULL(@e0003_fourthsaleamt_zk,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid_zk=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fourthsaleamt_zk,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_fourthsaleamt_zk,0)>10000)
						  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第四销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fourthsaleamt_zk,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第五销售
			if(isnull(@e0003_fifthsalerid_zk,'')<>'' and ISNULL(@e0003_fifthsaleamt_zk,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid_zk=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fifthsaleamt_zk,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_fifthsaleamt_zk,0)>10000)
						  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第五销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fifthsaleamt_zk,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第六销售
			if(isnull(@e0003_sixthsalerid_zk,'')<>'' and ISNULL(@e0003_sixthsaleamt_zk,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_sixthsaleamt_zk,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_sixthsaleamt_zk,0)>10000)
						  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第六销售烫发师分享业绩不能超过10000'
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_sixthsaleamt_zk,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第七销售
			if(isnull(@e0003_seventhsalerid_zk,'')<>'' and ISNULL(@e0003_seventhsaleamt_zk,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid_zk=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_seventhsaleamt_zk,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_seventhsaleamt_zk,0)>10000)
						  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第七销售烫发师分享业绩不能超过10000' 
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_seventhsaleamt_zk,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			              
			--第八销售
			if(isnull(@e0003_eighthsalerid_zk,'')<>'' and ISNULL(@e0003_eighthsaleamt_zk,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid_zk=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_eighthsaleamt_zk,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_eighthsaleamt_zk,0)>10000)
						  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第八销售烫发师分享业绩不能超过10000' 
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_eighthsaleamt_zk,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			--第九销售
			if(isnull(@e0003_ninthsalerid_zk,'')<>'' and ISNULL(@e0003_ninthsaleamt_zk,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid_zk=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_ninthsaleamt_zk,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_ninthsaleamt_zk,0)>10000)
						  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第九销售烫发师分享业绩不能超过10000' 
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_ninthsaleamt_zk,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end
			
			--第十销售
			if(isnull(@e0003_tenthsalerid_zk,'')<>'' and ISNULL(@e0003_tenthsaleamt_zk,0)>0) 
			begin
				if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid_zk=empid and emppos  in ('00102','003','006','007') )
				begin
					set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_tenthsaleamt_zk,0)
					set @mfstaffsharecount=@mfstaffsharecount+1
				end  
				if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )  
				begin
					if(ISNULL(@e0003_tenthsaleamt_zk,0)>10000)
						  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'第十销售烫发师分享业绩不能超过10000'  
					set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_tenthsaleamt_zk,0)
					set @trstaffsharecount=@trstaffsharecount+1
				end 
			end    
			if(ISNULL(@trstaffshareamt,0)>ISNULL(@mfstaffshareamt,0))
			     set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'美发人员与对应的烫发师分享业绩不匹配'               
			 if(ISNULL(@trstaffsharecount,0)>ISNULL(@mfstaffsharecount,0))
			     set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_msalecardinfo,'')+'分享美发人员少于烫发师人数'
			     
			if(@e0003_bill_mcardchangeinfo<>'')              
			begin                
				insert into #result(compid,billtype,errid,remark)                        
				values(@compid,'4','E0003',@e0003_bill_mcardchangeinfo+'单号'+@e0003_changebillid)                
			end                
                      
			fetch cur_#mcardchangeinfoerr3 into  @e0003_changebillid,
				@e0003_firstsalerid_zk,@e0003_secondsalerid_zk,@e0003_thirdsalerid_zk,@e0003_fourthsalerid_zk,
				@e0003_fifthsalerid_zk,@e0003_sixthsalerid_zk,@e0003_seventhsalerid_zk,@e0003_eighthsalerid_zk,
				@e0003_ninthsalerid_zk,@e0003_tenthsalerid_zk,              
				@e0003_firstsaleamt_zk,@e0003_secondsaleamt_zk,@e0003_thirdsaleamt_zk,@e0003_fourthsaleamt_zk,
				@e0003_fifthsaleamt_zk,@e0003_sixthsaleamt_zk,@e0003_seventhsaleamt_zk,@e0003_eighthsaleamt_zk, 
				@e0003_ninthsaleamt_zk,@e0003_tenthsaleamt_zk       
		end                        
		close cur_#mcardchangeinfoerr3                        
		deallocate cur_#mcardchangeinfoerr3  
	end              
 
	drop table #mcardchangeinfo            
               
     CREATE tAbLE	#mproexchangeinfo               -- 会员卡疗程兑换
	(
		changecompid			varchar(10)			Not NULL,   --公司编号
		changebillid			varchar(20)			Not NULL,   --销售单号
		changedate				varchar(8)				NULL,   --销售日期
		changecardno			varchar(20)				NULL,   --销售卡号
		changecardtype			varchar(20)				NULL,   --销售卡类型
		firstsalerid			varchar(20)				NULL,			--第一销售工号
		firstsalerinid			varchar(20)				NULL,			--第一销售内部编号
		firstsaleamt			float					NULL,			--第一销售分享金额
		secondsalerid			varchar(20)				NULL,			--第二销售工号
		secondsalerinid			varchar(20)				NULL,			--第二销售内部编号
		secondsaleamt			float					NULL,			--第二销售分享金额
		thirdsalerid			varchar(20)				NULL,			--第三销售工号----- 烫染师
		thirdsalerinid			varchar(20)				NULL,			--第三销售内部编号
		thirdsaleamt			float					NULL,			--第三销售分享金额
		fourthsalerid			varchar(20)				NULL,			--第四销售工号----- 烫染师
		fourthsalerinid			varchar(20)				NULL,			--第四销售内部编号
		fourthsaleamt			float					NULL,			--第四销售分享金额
		financedate				varchar(8)				NULL,   --帐务日期 
		changeproamt			float					NULL,	--储值金额
		paymode					varchar(10)				NULL,	--支付方式
		payamt					float					NULL,	--支付金额
	)  
	insert #mproexchangeinfo(changecompid,changebillid,changedate,changecardno,changecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changeproamt,paymode,payamt)   
    select a.changecompid,a.changebillid,changedate,changecardno,changecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changebyaccountamt,changepaymode,changebycashamt 
        from mproexchangeinfo a,dproexchangeinfo b
    where a.changecompid=b.changecompid and a.changebillid=b.changebillid
    and  financedate between @fromdate and @todate and ISNULL(changeproamt,0)<>0 and a.changecompid=@compid and ISNULL(salebakflag,0)=0
    
    if exists(select 1 from #mproexchangeinfo)                        
	begin                        
		declare @e0010_bill_mproexchangeinfo varchar(1024)                        
		set @e0010_bill_mproexchangeinfo = ''           
		declare @e0010_changecompid varchar(10),  @e0010_changecardno varchar(20) ,  @e0010_changebillid varchar(20)          
		declare @e0010_changeproamt float,   @e0010_payamt float                     
		declare @e0010_firstsalerid varchar(20), @e0010_secondsalerid varchar(20), @e0010_gcq14c varchar(20), @e0010_gcq15c varchar(20)                  
		declare @e0010_firstsaleamt float, @e0010_secondsaleamt float, @e0010_gcq16f float, @e0010_gcq17f float          
                
		declare cur_#mproexchangeinfo1err10 cursor for                        
		select changecompid,changecardno,changebillid,changeproamt,payamt,firstsalerid,firstsaleamt,secondsalerid,secondsaleamt from #mproexchangeinfo           
		open cur_#mproexchangeinfo1err10                        
		fetch cur_#mproexchangeinfo1err10 into  @e0010_changecompid,@e0010_changecardno,@e0010_changebillid,@e0010_changeproamt,@e0010_payamt,          
		@e0010_firstsalerid,@e0010_firstsaleamt,@e0010_secondsalerid,@e0010_secondsaleamt        
                              
		while(@@fetch_status=0)                        
		begin   
			set @e0010_bill_mproexchangeinfo='' 
			if(ISNULL(@e0010_changeproamt,0)+ISNULL(@e0010_payamt,0)>0 and ISNULL(@e0010_firstsaleamt,0)+ISNULL(@e0010_secondsaleamt,0)=0)          
			begin          
				insert into #result(compid,billtype,errid,remark)                        
				values(@compid,'10','E0010','本次疗程兑换未做分享'+'卡号'+@e0010_changecardno+'单号'+@e0010_changebillid)             
			end           
                      
			fetch cur_#mproexchangeinfo1err10 into  @e0010_changecompid,@e0010_changecardno,@e0010_changebillid,@e0010_changeproamt,@e0010_payamt,          
			@e0010_firstsalerid,@e0010_firstsaleamt,@e0010_secondsalerid,@e0010_secondsaleamt           
		end                        
		close cur_#mproexchangeinfo1err10                        
		deallocate cur_#mproexchangeinfo1err10                        
                        
	end 
 
    drop table #mproexchangeinfo   
     
     
   ----------------------add by liujie (ggm03 业绩分享)--------------------------------------------                      
	create table #mconsumeinfo                      
	(                      
		cscompid  varchar(10)     NOT NULL,   --公司编号                      
		csbillid  varchar(20)     NOT NULL,   --销售单号                      
		cscardno  varchar(20)     NULL    ,   --                      
	)                  
	insert into #mconsumeinfo(cscompid,csbillid,cscardno)                      
	select a.cscompid,a.csbillid,cscardno                    
	from mconsumeinfo   a with (nolock),dconsumeinfo  b                
	where a.cscompid=@compid                       
      and a.cscompid=b.cscompid and a.csbillid=b.csbillid                      
      and a.financedate between @fromdate and @todate  
      and ISNULL(csinfotype,0)=2 and ISNULL(backcsflag,0)=0
	 and convert(numeric(20,2),isnull(csitemamt,0))<>convert(numeric(20,2),isnull(csfirstshare,0)+isnull(cssecondshare,0)+isnull(csthirdshare,0))                      
        
    
	--e0003                      
	if exists(select 1 from #mconsumeinfo)                      
	begin                      
		declare @e0003_bill_9 varchar(1024)                      
		set @e0003_bill_9 = ''                      
		declare @e0003_csbillid varchar(20),@e0003_cscardno varchar(20)                      
		declare cur_mconsumeinfo cursor for                      
		select csbillid,cscardno from #mconsumeinfo                       
		open cur_mconsumeinfo                      
		fetch cur_mconsumeinfo into  @e0003_csbillid,@e0003_cscardno                      
		while(@@fetch_status=0)                      
		begin                      
			if(@e0003_bill_9='')                      
				set @e0003_bill_9 = @e0003_bill_9+@e0003_csbillid+'('+@e0003_cscardno+')'                      
			else                      
				set @e0003_bill_9 = @e0003_bill_9+','+@e0003_csbillid+'('+@e0003_cscardno+')'                      
			fetch cur_mconsumeinfo into @e0003_csbillid,@e0003_cscardno                      
		end                      
		close cur_mconsumeinfo                      
		deallocate cur_mconsumeinfo                      
                      
		insert into #result(compid,billtype,errid,remark)                      
		values(@compid,'9','E0003',@e0003_bill_9)                      
	end                      
    drop table #mconsumeinfo               
                        
                                              
 -------------------------------------------------------------------------------------                      
 update a set errdescript = description                       
 from #result a,#error b                      
 where a.errid = b.errid                      
                      
 select compid,billtype,errid,errdescript,remark                       
 from #result                    
                      
                      
 drop table #result               
                   
 drop table #emp_deperror                 
 drop table #error                      
end
GO
/****** Object:  StoredProcedure [dbo].[upg_analysis_system_error]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_analysis_system_error]                      
(                      
	@compid varchar(10),                      
	@fromdate varchar(8),                      
	@todate varchar(8)                      
)                      
as                      
begin                      
 declare @TIME_VALVE int                      
 set @TIME_VALVE = 5--单位：秒                      
 create table #error                      
 (                      
  errid  varchar(5)  not null,                      
  description varchar(500) null                      
 )                      
 insert into #error(errid,description) values('E0001','单据重复')                                        
 insert into #error(errid,description) values('E0003','业绩未分享或未完全分享')                      
 --1-开卡单 2-充值单 3-还款单 4-折扣转卡单 5-收购转卡单 6-竞争转卡单 7-会员并卡单 8-退卡单                      
 --9-收银单                       
               
  create table #result                      
 (                      
  compid  varchar(10)  not null,                      
  billtype varchar(5)  null,                      
  errid  varchar(5)  null,                      
  errdescript varchar(500) null,                      
  remark  varchar(2048) null,                      
 )                   
  create table #emp_deperror(                        
  compid varchar(10) null,                        
  inid varchar(20) null,--内部编号                        
  empid varchar(20) null,--员工编号       
  emppos varchar(10) null,                     
  empdep varchar(10) null,--员工部门             
  datefrom varchar(8) null,                        
  dateto varchar(8) null,                        
 )        
 insert into #emp_deperror(compid,empid,emppos)                                
 select distinct compno,staffno,position                        
 from staffinfo with (nolock)                                
 where @compid=compno                   
                
 --判断单据重复的规则：                      
 --1、在时间阀值区间内，有两张以及两张以上的单据内容相同；                      
                       
 CREATE tAbLE	#msalecardinfo               -- 会员卡销售单
	(
		salecompid			varchar(10)			Not NULL,   --公司编号
		salebillid			varchar(20)			Not NULL,   --销售单号
		saledate			varchar(8)				NULL,   --销售日期
		salecardno			varchar(20)				NULL,   --销售卡号
		salecardtype		varchar(20)				NULL,   --销售卡类型
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		salekeepamt			float					NULL,	--储值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)     
    insert #msalecardinfo(salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt)
	select salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt
    from msalecardinfo, dpayinfo
    where salebillid=paybillid and salecompid=paycompid and paybilltype='SK'
      and salecompid=@compid and financedate between @fromdate and @todate and ISNULL(salekeepamt,0)<>0
                              
         
        
                   
              
   if exists(select 1 from #msalecardinfo)                        
   begin              
		declare @e0003_salebillid varchar(20) 
		declare @e0003_salecardno varchar(20)           
		declare @e0003_bill_msalecardinfo varchar(1024)                        
		set @e0003_bill_msalecardinfo = ''                        
                
		declare @e0003_firstsalerid varchar(20), @e0003_secondsalerid varchar(20), @e0003_thirdsalerid varchar(20), @e0003_eighthsalerid varchar(20) , @e0003_ninthsalerid varchar(20) , @e0003_tenthsalerid varchar(20)     
		declare @e0003_firstsaleamt float, @e0003_secondsaleamt float, @e0003_thirdsaleamt float, @e0003_eighthsaleamt float, @e0003_ninthsaleamt float, @e0003_tenthsaleamt float              
                
		declare cur_#msalecardinfo03err3 cursor for                        
		select salebillid,salecardno,firstsalerid,secondsalerid,thirdsalerid,eighthsalerid,ninthsalerid,tenthsalerid,firstsaleamt,secondsaleamt,thirdsaleamt,eighthsaleamt,ninthsaleamt,tenthsaleamt from #msalecardinfo                         
		open cur_#msalecardinfo03err3                        
		fetch cur_#msalecardinfo03err3 into  @e0003_salebillid,@e0003_salecardno,@e0003_firstsalerid,@e0003_secondsalerid,@e0003_thirdsalerid,@e0003_eighthsalerid,@e0003_ninthsalerid,@e0003_tenthsalerid,              
        @e0003_firstsaleamt,@e0003_secondsaleamt,@e0003_thirdsaleamt,@e0003_eighthsaleamt,@e0003_ninthsaleamt,@e0003_tenthsaleamt              
                                    
		while(@@fetch_status=0)                        
		begin  
		
			set @e0003_bill_msalecardinfo=''
			if(isnull(@e0003_eighthsalerid,'')<>'' and ISNULL(@e0003_eighthsaleamt,0)>0)              
			begin              
				if not exists( select 1 from #emp_deperror where @e0003_firstsalerid=empid and emppos  in ('00102','003','006','007') )              
					set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第一销售'+isnull(@e0003_firstsalerid,'')+'不属于美发师但是对应的烫发师分享了相应的业绩'               
				else if( ( convert(numeric(20,1),ISNULL(@e0003_firstsaleamt,0))<10000  and convert(numeric(20,1),ISNULL(@e0003_firstsaleamt,0))<>convert(numeric(20,1),ISNULL(@e0003_eighthsaleamt,0)))             
				or ( convert(numeric(20,1),ISNULL(@e0003_firstsaleamt,0))>=10000 and convert(numeric(20,1),ISNULL(@e0003_eighthsaleamt,0))<>10000))              
						set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第一销售'+isnull(@e0003_firstsalerid,'')+'与对应的烫发师分享业绩不匹配'               
                  
			end   
			if(isnull(@e0003_ninthsalerid,'')<>'' and ISNULL(@e0003_ninthsaleamt,0)>0)              
			begin              
				if not exists( select 1 from #emp_deperror where @e0003_secondsalerid=empid and emppos  in ('00102','003','006','007') )              
					set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第二销售'+isnull(@e0003_secondsalerid,'')+'不属于美发师但是对应的烫发师分享了相应的业绩'               
				else if(  ( convert(numeric(20,1),ISNULL(@e0003_secondsaleamt,0))<10000   and convert(numeric(20,1),ISNULL(@e0003_secondsaleamt,0))<>convert(numeric(20,1),ISNULL(@e0003_ninthsaleamt,0)))            
					or ( convert(numeric(20,1),ISNULL(@e0003_secondsaleamt,0))>=10000  and convert(numeric(20,1),ISNULL(@e0003_ninthsaleamt,0))<>10000))              
					set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第二销售'+isnull(@e0003_secondsalerid,'')+'与对应的烫发师分享业绩不匹配'               
			end              
         
			if(isnull(@e0003_tenthsalerid,'')<>'' and ISNULL(@e0003_tenthsaleamt,0)>0)              
			begin              
				if not exists( select 1 from #emp_deperror where @e0003_thirdsalerid=empid and emppos  in ('00102','003','006','007') )              
					set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第三销售'+isnull(@e0003_thirdsalerid,'')+'不属于美发师但是对应的烫发师分享了相应的业绩'               
				else if( (convert(numeric(20,1),ISNULL(@e0003_thirdsaleamt,0))<10000  and convert(numeric(20,1),ISNULL(@e0003_thirdsaleamt,0))<>convert(numeric(20,1),ISNULL(@e0003_tenthsaleamt,0)))            
					or  (convert(numeric(20,1),ISNULL(@e0003_thirdsaleamt,0))>=10000 and convert(numeric(20,1),ISNULL(@e0003_tenthsaleamt,0))<>10000 ))              
					set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第三销售'+isnull(@e0003_thirdsalerid,'')+'与对应的烫发师分享业绩不匹配'               
			end              
                 
               
			if(@e0003_bill_msalecardinfo<>'')              
			begin                
				insert into #result(compid,billtype,errid,remark)                        
				values(@compid,'1','E0003',@e0003_bill_msalecardinfo+'单号'+@e0003_salebillid)                
			end                
                      
			fetch cur_#msalecardinfo03err3 into  @e0003_salebillid,@e0003_salecardno,@e0003_firstsalerid,@e0003_secondsalerid,@e0003_thirdsalerid,@e0003_tenthsalerid,@e0003_ninthsalerid,@e0003_tenthsalerid,              
               @e0003_firstsaleamt,@e0003_secondsaleamt,@e0003_thirdsaleamt,@e0003_eighthsaleamt,@e0003_ninthsaleamt,@e0003_tenthsaleamt              
		end                        
		close cur_#msalecardinfo03err3                        
		deallocate cur_#msalecardinfo03err3                        
                        
	end                        
                                       
	drop table #msalecardinfo                      
                    
    CREATE tAbLE	#mcardrechargeinfo              -- 会员卡充值单
	(
		rechargecompid			varchar(10)			Not NULL,   --充值门店
		rechargebillid			varchar(20)			Not NULL,   --充值单号 
		rechargedate			varchar(8)				NULL,   --充值日期 
		rechargetime			varchar(6)				NULL,   --充值时间 
		rechargecardno			varchar(20)				NULL,   --会员卡号
		rechargecardtype		varchar(10)				NULL,   --卡类型
		rechargetype			int						NULL,   --续费方式( 0充值 ,6还款 ,)
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		rechargekeepamt		float					NULL,   --充值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)     
	
	insert #mcardrechargeinfo(rechargecompid,rechargebillid,rechargedate,rechargetype,rechargecardno,rechargecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt)
	select rechargecompid,rechargebillid,rechargedate,rechargetype,rechargecardno,rechargecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt
    from mcardrechargeinfo, dpayinfo
    where rechargebillid=paybillid and rechargecompid=paycompid and paybilltype='CZ'
      and rechargecompid=@compid and financedate between @fromdate and @todate and ISNULL(rechargekeepamt,0)<>0
      
      
   if exists(select 1 from #mcardrechargeinfo)                        
   begin                        
		declare @e0003_bill_mcardrechargeinfo varchar(1024)                        
		set @e0003_bill_mcardrechargeinfo = ''                        
        declare @e0003_rechargebillid varchar(20)         
		declare @e0003_rechargecardno varchar(20)                
		declare @e0003_firstsalerid_cz varchar(20), @e0003_secondsalerid_cz varchar(20), @e0003_thirdsalerid_cz varchar(20), @e0003_eighthsalerid_cz varchar(20) , @e0003_ninthsalerid_cz varchar(20) , @e0003_tenthsalerid_cz varchar(20)                  
		declare @e0003_firstsaleamt_cz float, @e0003_secondsaleamt_cz float, @e0003_thirdsaleamt_cz float, @e0003_eighthsaleamt_cz float, @e0003_ninthsaleamt_cz float, @e0003_tenthsaleamt_cz float              
	                
		declare cur_#mcardrechargeinfo03err3 cursor for                        
		select rechargebillid,rechargecardno, firstsalerid,secondsalerid,thirdsalerid,eighthsalerid,ninthsalerid,tenthsalerid,firstsaleamt,secondsaleamt,thirdsaleamt,eighthsaleamt,ninthsaleamt,tenthsaleamt from #mcardrechargeinfo              
		open cur_#mcardrechargeinfo03err3                        
		fetch cur_#mcardrechargeinfo03err3 into  @e0003_rechargebillid,@e0003_rechargecardno,@e0003_firstsalerid_cz,@e0003_secondsalerid_cz,@e0003_thirdsalerid_cz,@e0003_eighthsalerid_cz,@e0003_ninthsalerid_cz,@e0003_tenthsalerid_cz,              
				   @e0003_firstsaleamt_cz,@e0003_secondsaleamt_cz,@e0003_thirdsaleamt_cz,@e0003_eighthsaleamt_cz,@e0003_ninthsaleamt_cz,@e0003_tenthsaleamt_cz              
	                                    
		while(@@fetch_status=0)                        
		begin         
	                
			set @e0003_bill_mcardrechargeinfo=''
			if(isnull(@e0003_eighthsalerid_cz,'')<>'' and ISNULL(@e0003_eighthsaleamt_cz,0)>0)              
			begin              
				if not exists( select 1 from #emp_deperror where @e0003_firstsalerid_cz=empid and emppos  in ('00102','003','006','007') )              
					set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第一销售'+isnull(@e0003_firstsalerid_cz,'')+'不属于美发师但是对应的烫发师分享了相应的业绩'               
				else if( (convert(numeric(20,1),ISNULL(@e0003_firstsaleamt_cz,0))<10000  and convert(numeric(20,1),ISNULL(@e0003_firstsaleamt_cz,0))<>convert(numeric(20,1),ISNULL(@e0003_eighthsaleamt_cz,0)))            
				or   (convert(numeric(20,1),ISNULL(@e0003_firstsaleamt_cz,0))>=10000 and convert(numeric(20,1),ISNULL(@e0003_eighthsaleamt_cz,0))<>10000 ))              
					set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第一销售'+isnull(@e0003_firstsalerid_cz,'')+'与对应的烫发师分享业绩不匹配'               
	                  
			end              
	                 
			if(isnull(@e0003_ninthsalerid_cz,'')<>'' and ISNULL(@e0003_ninthsaleamt_cz,0)>0)              
			begin              
				if not exists( select 1 from #emp_deperror where @e0003_secondsalerid_cz=empid and emppos  in ('00102','003','006','007') )              
					set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第二销售'+isnull(@e0003_secondsalerid_cz,'')+'不属于美发师但是对应的烫发师分享了相应的业绩'               
				else if( (convert(numeric(20,1),ISNULL(@e0003_secondsaleamt_cz,0))<10000  and convert(numeric(20,1),ISNULL(@e0003_secondsaleamt_cz,0))<>convert(numeric(20,1),ISNULL(@e0003_ninthsaleamt_cz,0)))            
				or   (convert(numeric(20,1),ISNULL(@e0003_secondsaleamt_cz,0))>=10000 and convert(numeric(20,1),ISNULL(@e0003_ninthsaleamt_cz,0))<>10000 ))              
					set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第二销售'+isnull(@e0003_secondsalerid_cz,'')+'与对应的烫发师分享业绩不匹配'               
			end              
	                 
			if(isnull(@e0003_tenthsalerid_cz,'')<>'' and ISNULL(@e0003_tenthsaleamt_cz,0)>0)              
			begin              
				if not exists( select 1 from #emp_deperror where @e0003_thirdsalerid_cz=empid and emppos  in ('00102','003','006','007') )              
					set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第三销售'+isnull(@e0003_thirdsalerid_cz,'')+'不属于美发师但是对应的烫发师分享了相应的业绩'               
				else if( (convert(numeric(20,1),ISNULL(@e0003_thirdsaleamt_cz,0))<10000  and convert(numeric(20,1),ISNULL(@e0003_thirdsaleamt_cz,0))<>convert(numeric(20,1),ISNULL(@e0003_tenthsaleamt_cz,0)))            
				or   (convert(numeric(20,1),ISNULL(@e0003_thirdsaleamt_cz,0))>=10000 and convert(numeric(20,1),ISNULL(@e0003_tenthsaleamt_cz,0))<>10000 ))              
					set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第三销售'+isnull(@e0003_thirdsalerid_cz,'')+'与对应的烫发师分享业绩不匹配'               
			end  
			   
			if(@e0003_bill_mcardrechargeinfo<>'')              
			begin                
				insert into #result(compid,billtype,errid,remark)                        
				values(@compid,'2','E0003',@e0003_bill_mcardrechargeinfo+'单号'+@e0003_rechargebillid)                
			end                
	                      
			fetch cur_#mcardrechargeinfo03err3 into  @e0003_rechargebillid,@e0003_rechargecardno,@e0003_firstsalerid_cz,@e0003_secondsalerid_cz,@e0003_thirdsalerid_cz,@e0003_eighthsalerid_cz,@e0003_ninthsalerid_cz,@e0003_tenthsalerid_cz,              
				   @e0003_firstsaleamt_cz,@e0003_secondsaleamt_cz,@e0003_thirdsaleamt_cz,@e0003_eighthsaleamt_cz,@e0003_ninthsaleamt_cz,@e0003_tenthsaleamt_cz              
	   end                        
	  close cur_#mcardrechargeinfo03err3                        
	  deallocate cur_#mcardrechargeinfo03err3                      
	  end
      
     drop table #mcardrechargeinfo    
     
     
        CREATE tAbLE	#mcardchangeinfo             -- 会员卡异动
	(
		changecompid		varchar(10)			Not NULL,   --充值门店
		changebillid		varchar(20)			Not NULL,   --充值单号 
		changetype			int					Not NULL,
		changedate			varchar(8)				NULL,   --充值日期 
		changetime			varchar(6)				NULL,   --充值时间 
		changeaftercardno	varchar(20)				NULL,   --会员卡号
		changeaftercardtype	varchar(10)				NULL,   --卡类型
		firstsalerid		varchar(20)				NULL,   --第一销售工号
		firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
		firstsaleamt		float					NULL,   --第一销售分享金额
		secondsalerid		varchar(20)				NULL,   --第二销售工号
		secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
		secondsaleamt		float					NULL,   --第二销售分享金额
		thirdsalerid		varchar(20)				NULL,   --第三销售工号
		thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
		thirdsaleamt		float					NULL,   --第三销售分享金额
		fourthsalerid		varchar(20)				NULL,   --第四销售工号
		fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
		fourthsaleamt		float					NULL,   --第四销售分享金额
		fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
		fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
		fifthsaleamt		float					NULL,   --第五销售分享金额
		sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
		sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
		sixthsaleamt		float					NULL,   --第六销售分享金额
		seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
		seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
		seventhsaleamt		float					NULL,   --第七销售分享金额
		eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
		eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
		eighthsaleamt		float					NULL,   --第八销售分享金额
		ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
		ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
		ninthsaleamt		float					NULL,   --第九销售分享金额
		tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
		tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
		tenthsaleamt		float					NULL,   --第十销售分享金额
		financedate			varchar(8)				NULL,   --帐务日期 
		changefillamt		float					NULL,   --充值金额
		paymode				varchar(10)				NULL,	--支付方式
		payamt				float					NULL,	--支付金额
	)   
	
	insert #mcardchangeinfo(changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt)
	select changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt
    from mcardchangeinfo, dpayinfo
    where changebillid=paybillid and changecompid=paycompid and paybilltype in ('ZK','TK')
      and changecompid=@compid and financedate between @fromdate and @todate and ISNULL(changefillamt,0)<>0
      
      
    if exists(select 1 from #mcardchangeinfo)                        
	begin                        
		declare @e0003_changebillid varchar(20)	
		declare @e0003_bill_mcardchangeinfo varchar(1024)                        
		set @e0003_bill_mcardchangeinfo = ''                        
		declare @e0003_firstsalerid_zk varchar(20), @e0003_secondsalerid_zk varchar(20), @e0003_thirdsalerid_zk varchar(20), @e0003_eighthsalerid_zk varchar(20) , @e0003_ninthsalerid_zk varchar(20) , @e0003_tenthsalerid_zk varchar(20)                  
		declare @e0003_firstsaleamt_zk float, @e0003_secondsaleamt_zk float, @e0003_thirdsaleamt_zk float, @e0003_eighthsaleamt_zk float, @e0003_ninthsaleamt_zk float, @e0003_tenthsaleamt_zk float      
                
		declare cur_#mcardchangeinfoerr3 cursor for                        
		select changebillid, firstsalerid_zk,secondsalerid_zk,thirdsalerid_zk,eighthsalerid_zk,ninthsalerid_zk,tenthsalerid_zk, firstsaleamt_zk,secondsaleamt_zk,thirdsaleamt_zk,eighthsaleamt_zk,ninthsaleamt_zk,tenthsaleamt_zk from #mcardchangeinfo              
		open cur_#mcardchangeinfoerr3                        
		fetch cur_#mcardchangeinfoerr3 into  @e0003_changebillid,@e0003_firstsalerid_zk,@e0003_secondsalerid_zk,@e0003_thirdsalerid_zk,@e0003_eighthsalerid_zk,@e0003_ninthsalerid_zk,@e0003_tenthsalerid_zk,              
            @e0003_firstsaleamt_zk,@e0003_secondsaleamt_zk,@e0003_thirdsaleamt_zk,@e0003_eighthsaleamt_zk,@e0003_ninthsaleamt_zk,@e0003_tenthsaleamt_zk              
                              
		while(@@fetch_status=0)                        
		begin  
			set @e0003_bill_mcardchangeinfo=''
			if(isnull(@e0003_eighthsalerid_zk,'')<>'' and ISNULL(@e0003_eighthsaleamt_zk,0)>0)              
			begin              
				if not exists( select 1 from #emp_deperror,#mcardchangeinfo where @e0003_firstsalerid_zk=empid and emppos  in ('00102','003','006','007') )              
					set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第一销售'+isnull(@e0003_firstsalerid_zk,'')+'不属于美发师但是对应的烫发师分享了相应的业绩'               
				else if( (convert(numeric(20,1),ISNULL(@e0003_firstsaleamt_zk,0))<10000  and convert(numeric(20,1),ISNULL(@e0003_firstsaleamt_zk,0))<>convert(numeric(20,1),ISNULL(@e0003_eighthsaleamt_zk,0)))            
				or  (convert(numeric(20,1),ISNULL(@e0003_firstsaleamt_zk,0))>=10000 and convert(numeric(20,1),ISNULL(@e0003_eighthsaleamt_zk,0))<>10000) )              
					set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第一销售'+isnull(@e0003_firstsalerid_zk,'')+'与对应的烫发师分享业绩不匹配'               
                  
			end              
                 
			if(isnull(@e0003_ninthsalerid_zk,'')<>'' and ISNULL(@e0003_ninthsaleamt_zk,0)>0)              
			begin              
				if not exists( select 1 from #emp_deperror,#mcardchangeinfo where @e0003_secondsalerid_zk=empid and emppos  in ('00102','003','006','007') )              
					set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第二销售'+isnull(@e0003_secondsalerid_zk,'')+'不属于美发师但是对应的烫发师分享了相应的业绩'               
				else if( (convert(numeric(20,1),ISNULL(@e0003_secondsaleamt_zk,0))<10000  and convert(numeric(20,1),ISNULL(@e0003_secondsaleamt_zk,0))<>convert(numeric(20,1),ISNULL(@e0003_ninthsaleamt_zk,0)))            
					or  (convert(numeric(20,1),ISNULL(@e0003_secondsaleamt_zk,0))>=10000 and convert(numeric(20,1),ISNULL(@e0003_ninthsaleamt_zk,0))<>10000) )              
					set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第二销售'+isnull(@e0003_secondsalerid_zk,'')+'与对应的烫发师分享业绩不匹配'               
			end              
                 
			if(isnull(@e0003_tenthsalerid_zk,'')<>'' and ISNULL(@e0003_tenthsaleamt_zk,0)>0)              
			begin              
				if not exists( select 1 from #emp_deperror,#mcardchangeinfo where @e0003_thirdsalerid_zk=empid and emppos  in ('00102','003','006','007') )              
					set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第三销售'+isnull(@e0003_thirdsalerid_zk,'')+'不属于美发师但是对应的烫发师分享了相应的业绩'               
				else if( (convert(numeric(20,1),ISNULL(@e0003_thirdsaleamt_zk,0))<10000  and convert(numeric(20,1),ISNULL(@e0003_thirdsaleamt_zk,0))<>convert(numeric(20,1),ISNULL(@e0003_tenthsaleamt_zk,0)))            
				or  (convert(numeric(20,1),ISNULL(@e0003_thirdsaleamt_zk,0))>=10000 and convert(numeric(20,1),ISNULL(@e0003_tenthsaleamt_zk,0))<>10000) )              
				set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第三销售'+isnull(@e0003_thirdsalerid_zk,'')+'与对应的烫发师分享业绩不匹配'               
			end       
                
			if(@e0003_bill_mcardchangeinfo<>'')              
			begin                
				insert into #result(compid,billtype,errid,remark)                        
				values(@compid,'4','E0003',@e0003_bill_mcardchangeinfo+'单号'+@e0003_changebillid)                
			end                
                      
			fetch cur_#mcardchangeinfoerr3 into  @e0003_changebillid,@e0003_firstsalerid_zk,@e0003_secondsalerid_zk,@e0003_thirdsalerid_zk,@e0003_eighthsalerid_zk,@e0003_ninthsalerid_zk,@e0003_tenthsalerid_zk,              
				@e0003_firstsaleamt_zk,@e0003_secondsaleamt_zk,@e0003_thirdsaleamt_zk,@e0003_eighthsaleamt_zk,@e0003_ninthsaleamt_zk,@e0003_tenthsaleamt_zk              
		end                        
		close cur_#mcardchangeinfoerr3                        
		deallocate cur_#mcardchangeinfoerr3  
	end              
 
	drop table #mcardchangeinfo            
               
     CREATE tAbLE	#mproexchangeinfo               -- 会员卡疗程兑换
	(
		changecompid			varchar(10)			Not NULL,   --公司编号
		changebillid			varchar(20)			Not NULL,   --销售单号
		changedate				varchar(8)				NULL,   --销售日期
		changecardno			varchar(20)				NULL,   --销售卡号
		changecardtype			varchar(20)				NULL,   --销售卡类型
		firstsalerid			varchar(20)				NULL,			--第一销售工号
		firstsalerinid			varchar(20)				NULL,			--第一销售内部编号
		firstsaleamt			float					NULL,			--第一销售分享金额
		secondsalerid			varchar(20)				NULL,			--第二销售工号
		secondsalerinid			varchar(20)				NULL,			--第二销售内部编号
		secondsaleamt			float					NULL,			--第二销售分享金额
		thirdsalerid			varchar(20)				NULL,			--第三销售工号----- 烫染师
		thirdsalerinid			varchar(20)				NULL,			--第三销售内部编号
		thirdsaleamt			float					NULL,			--第三销售分享金额
		fourthsalerid			varchar(20)				NULL,			--第四销售工号----- 烫染师
		fourthsalerinid			varchar(20)				NULL,			--第四销售内部编号
		fourthsaleamt			float					NULL,			--第四销售分享金额
		financedate				varchar(8)				NULL,   --帐务日期 
		changeproamt			float					NULL,	--储值金额
		paymode					varchar(10)				NULL,	--支付方式
		payamt					float					NULL,	--支付金额
	)  
	insert #mproexchangeinfo(changecompid,changebillid,changedate,changecardno,changecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changeproamt,paymode,payamt)   
    select a.changecompid,a.changebillid,changedate,changecardno,changecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changebyaccountamt,changepaymode,changebycashamt 
        from mproexchangeinfo a,dproexchangeinfo b
    where a.changecompid=b.changecompid and a.changebillid=b.changebillid
    and  financedate between @fromdate and @todate and ISNULL(changeproamt,0)<>0 and a.changecompid=@compid
    
    if exists(select 1 from #mproexchangeinfo)                        
	begin                        
		declare @e0010_bill_mproexchangeinfo varchar(1024)                        
		set @e0010_bill_mproexchangeinfo = ''           
		declare @e0010_changecompid varchar(10),  @e0010_changecardno varchar(20) ,  @e0010_changebillid varchar(20)          
		declare @e0010_changeproamt float,   @e0010_payamt float                     
		declare @e0010_firstsalerid varchar(20), @e0010_secondsalerid varchar(20), @e0010_gcq14c varchar(20), @e0010_gcq15c varchar(20)                  
		declare @e0010_firstsaleamt float, @e0010_secondsaleamt float, @e0010_gcq16f float, @e0010_gcq17f float          
                
		declare cur_#mproexchangeinfo1err10 cursor for                        
		select changecompid,changecardno,changebillid,changeproamt,payamt,firstsalerid,firstsaleamt,secondsalerid,secondsaleamt from #mproexchangeinfo           
		open cur_#mproexchangeinfo1err10                        
		fetch cur_#mproexchangeinfo1err10 into  @e0010_changecompid,@e0010_changecardno,@e0010_changebillid,@e0010_changeproamt,@e0010_payamt,          
		@e0010_firstsalerid,@e0010_firstsaleamt,@e0010_secondsalerid,@e0010_secondsaleamt        
                              
		while(@@fetch_status=0)                        
		begin   
			set @e0010_bill_mproexchangeinfo='' 
			if(ISNULL(@e0010_changeproamt,0)+ISNULL(@e0010_payamt,0)>0 and ISNULL(@e0010_firstsaleamt,0)+ISNULL(@e0010_secondsaleamt,0)=0)          
			begin          
				insert into #result(compid,billtype,errid,remark)                        
				values(@compid,'10','E0010','本次疗程兑换未做分享'+'卡号'+@e0010_changecardno+'单号'+@e0010_changebillid)             
			end           
                      
			fetch cur_#mproexchangeinfo1err10 into  @e0010_changecompid,@e0010_changecardno,@e0010_changebillid,@e0010_changeproamt,@e0010_payamt,          
			@e0010_firstsalerid,@e0010_firstsaleamt,@e0010_secondsalerid,@e0010_secondsaleamt           
		end                        
		close cur_#mproexchangeinfo1err10                        
		deallocate cur_#mproexchangeinfo1err10                        
                        
	end 
 
    drop table #mproexchangeinfo   
                         
                                              
 -------------------------------------------------------------------------------------                      
 update a set errdescript = description                       
 from #result a,#error b                      
 where a.errid = b.errid                      
                      
 select compid,billtype,errid,errdescript,remark                       
 from #result                    
                      
                      
 drop table #result               
                   
 drop table #emp_deperror                 
 drop table #error                      
end
GO
/****** Object:  StoredProcedure [dbo].[upg_amn_createcardno_quan]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_amn_createcardno_quan]           
(               
	@compid  varchar(10), ---登录公司                
	@cardfromno varchar(20), --开始卡号                
	@cardtono  varchar(20) ,--结束卡号              
	@cardclass  int , ---卡类别               
	@carduseclass int  ,---卡类别          
	@facePrice  float, --面值        
	@cardBef  varchar(10),--卡前缀        
	@contion  varchar(600),    
	@sendate varchar(10), --有效期 
	@enabledate varchar(10), --启用日期
	@outercardtype	int	   --1 条码卡 2 抵用券    

)          
as                
begin                
	declare @cardcount float     --卡数量                
	declare @cardno     varchar(20) --insert 到gcm01中的卡号          
	set     @cardno=@cardfromno   
            
	if(@cardfromno>@cardtono or len(isnull(@cardfromno,''))<>len(isnull(@cardtono,'')))              
	begin            
		return            
	end          
    declare @sql nvarchar(600)  
	declare @count int            
	set @count = 0; 
	declare @targcardno varchar(20)        
    
    if(@outercardtype=2)  
    begin     
		while((@cardno <= @cardtono) and len(@cardno)=len(@cardtono) and @count<6000)                
		begin               
			
			set @targcardno=@cardBef+@cardno        
			insert nointernalcardinfo(cardvesting,cardno,cardtype,cardstate,carduseflag,cardfaceamt,entrytype,oldvalidate,lastvalidate,enabledate)        
			values( @compid,@targcardno,@cardclass,1,@carduseclass,@facePrice,0,@sendate,@sendate,@enabledate)       
			if(@carduseclass=1 and ISNULL(@contion,'')<>'')
			begin 
				--插入项目明细
				set @sql=' insert dnointernalcardinfo(cardvesting,cardno,seqno,ineritemno,entrycount,lastcount,entryamt,lastamt) select '''+@compid+''' ,'''+@targcardno+''',s,p,c,c,a,a from ('+@contion+')  as tb '         
				exec sp_executesql @sql        
			end
			set @count  = @count+1            
			set @cardno=cast((cast(@cardno as int )+1) as varchar(20))        
			while(len(@cardno)<len(@cardtono))                
			begin                
				set @cardno = '0'+@cardno                
			end          
		end   	
	end 
	else
	begin
		while((@cardno <= @cardtono) and len(@cardno)=len(@cardtono) and @count<6000)            
		begin           
			set @targcardno=@cardBef+@cardno    
			insert nointernalcardinfo(cardvesting,cardno,cardtype,cardstate,carduseflag)    
			values( @compid,@targcardno,@cardclass,0,@carduseclass)    
			set @count  = @count+1        
			set @cardno=cast((cast(@cardno as int )+1) as varchar(20))    
			while(len(@cardno)<len(@cardtono))            
			begin            
				set @cardno = '0'+@cardno            
			end      
		end          
	end            
end
GO
/****** Object:  StoredProcedure [dbo].[upg_amn_createcardno]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_amn_createcardno] 
(       
	@compid  varchar(10), ---登录公司        
	@cardfromno varchar(20), --开始卡号        
	@cardtono  varchar(20) ,--结束卡号      
	@cardclass varchar(10)  ---卡类别       
)  
as        
begin        
	declare @cardcount float     --卡数量        
	declare @cardno     varchar(20) --insert 到gcm01中的卡号        
 
	declare @cardlength	int        
	declare @filterNum		varchar(10)     
	declare @temp varchar(20)      
      
	if(@cardfromno>@cardtono or len(isnull(@cardfromno,''))<>len(isnull(@cardtono,'')))      
	begin    
		return    
	end     
	declare @count int    
	set @count = 0;   
  
	select @cardlength = cast(paramvalue as int) from sysparaminfo where compid=@compid and paramid='SP019'    
	select @filterNum = isnull(paramvalue,'')  from sysparaminfo where compid=@compid and paramid='SP020'    
       
	set @cardno = @cardfromno        
	declare @cardseq int        
    
	declare @cardbefore varchar(20)--卡的前部分        
	declare @cardafter varchar(20) --卡的后部分        
	while((@cardno <= @cardtono) and len(@cardno)=len(@cardtono) and @count<200)        
	begin        
		set @count  = @count+1    
      
		if((substring(@cardno,len(@cardno),1))=isnull(@filterNum,''))        
		begin        
			set @cardseq = cast(substring(@cardno,(len(@cardno)-@cardlength+1),@cardlength) as int)        
			set @cardbefore = substring(@cardno,1,len(@cardno)-@cardlength)        
			set @cardseq = @cardseq+1        
			set @cardafter = cast(@cardseq as varchar(20))        
			while(len(@cardafter)<@cardlength)        
			begin        
				set @cardafter = '0'+@cardafter        
			end        
			set @cardno = @cardbefore+@cardafter        
			continue;        
		end        
		else        
		begin    
			if not exists(select 1 from cardinfo where cardno = @cardno )        
			begin        
				insert into cardinfo (cardvesting,cardno,cardtype,cardstate,cardsource)      
				values(@compid,@cardno,@cardclass,1,0)         
				declare @operationdate varchar(8)  
				declare @operationtime varchar(6)  
				select @operationdate = substring(convert(varchar(20),getdate(),102),1,4) + substring(convert(varchar(20),getdate(),102),6,2)+substring(convert(varchar(20),getdate(),102),9,2)  
				set @operationtime = substring(convert(varchar(40),getdate(),120),12,2)+ substring(convert(varchar(40),getdate(),120),15,2)  
				insert into sysoperationlog(userid,program,operation,operationdate,operationtime,origatedate,compid,keyvalue1,keyvalue2, keyvalue3,keyvalue4)      
				values('','AC006','A',@operationdate,@operationtime,@operationdate,@compid,@cardno,@cardclass,'','未销售卡登记' )   
			end        
			if not exists(select 1 from cardaccount where   cardno = @cardno)      
			begin      
				insert into cardaccount(cardvesting,cardno,accounttype,accountbalance)       
				values(@compid,@cardno,2,0)      
			end         
			set @cardseq = cast(substring(@cardno,(len(@cardno)-@cardlength+1),@cardlength) as int)        
			set @cardbefore = substring(@cardno,1,len(@cardno)-@cardlength)        
			set @cardseq = @cardseq+1        
			set @cardafter = cast(@cardseq as varchar(20))        
			while(len(@cardafter)<@cardlength)        
			begin        
					set @cardafter = '0'+@cardafter        
			end      
			if(len(@cardafter)=@cardlength)  
			begin    
				set @cardno = @cardbefore+@cardafter  
			end        
		end        
	end        
end
GO
/****** Object:  StoredProcedure [dbo].[upg_allotcard_createcardno]    Script Date: 01/14/2014 15:45:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_allotcard_createcardno]    
@compid  varchar(10), ---登录公司      
@sendid  varchar(10), --申请公司       
@billno  varchar(20)      
as      
begin      
 declare @cardclass  varchar(10)---卡类别      
 declare @cardfromno varchar(20) --卡代码开始号码      
 declare @cardtono varchar(20) --卡代码结束号码      
 declare @cardcount float     --卡数量      
 declare @cardno     varchar(20) --insert 到gcm01中的卡号          
 
 declare @cardlength  int      
 declare @filterNum varchar(10)   
 
 select @cardlength = cast(paramvalue as int) from sysparaminfo where compid=@compid and paramid='SP019'    
 select @filterNum = isnull(paramvalue,'')  from sysparaminfo where compid=@compid and paramid='SP020'   
       
   
 declare cursor_allot cursor for      
 select cardtypeid,cardnofrom,cardnoto,ccount from dcardallotment  where callotcompid=@compid and callotbillid=@billno      
 open cursor_allot      
 fetch cursor_allot into @cardclass,@cardfromno,@cardtono,@cardcount      
 while @@fetch_status=0      
 begin      
		set @cardno = @cardfromno      
		declare @cardseq int      
		declare @cardbefore varchar(20)--卡的前部分      
		declare @cardafter varchar(20) --卡的后部分      
		while(@cardno <= @cardtono)      
		begin      
		if((substring(@cardno,len(@cardno),1))=isnull(@filterNum,''))      
		begin      
			set @cardseq = substring(@cardno,(len(@cardno)-@cardlength+1),@cardlength)      
			set @cardbefore = substring(@cardno,1,len(@cardno)-@cardlength)      
			set @cardseq = @cardseq+1      
			set @cardafter = cast(@cardseq as varchar(20))      
			while(len( cast(@cardafter as varchar(20)))<@cardlength)      
			begin      
				set @cardafter = '0'+@cardafter      
			end      
			set @cardno = @cardbefore+@cardafter      
			continue;      
		end      
		else      
		begin      
			if not exists(select 1 from cardinfo where cardno = @cardno)      
			begin      
				insert into cardinfo (cardvesting,cardno,cardtype,cardstate,cardsource)      
				values(@sendid,@cardno,@cardclass,1,0)      
				declare @operationdate varchar(8)  
				declare @operationtime varchar(6)  
				select @operationdate = substring(convert(varchar(20),getdate(),102),1,4) + substring(convert(varchar(20),getdate(),102),6,2)+  
						substring(convert(varchar(20),getdate(),102),9,2)  
				set @operationtime = substring(convert(varchar(40),getdate(),120),12,2)+ substring(convert(varchar(40),getdate(),120),15,2)  
				insert into sysoperationlog(userid,program,operation,operationdate,operationtime,origatedate,compid,keyvalue1,keyvalue2, keyvalue3,keyvalue4)      
				values('','CC003','A',@operationdate,@operationtime,@operationdate,@sendid,@cardno,@cardclass,@billno,'会员卡配发' )     
			end      
			if not exists(select 1 from cardaccount where   cardno = @cardno)      
			begin      
				insert into cardaccount(cardvesting,cardno,accounttype,accountbalance)       
				values(@sendid,@cardno,2,0)      
			end      
			set @cardseq = substring(@cardno,(len(@cardno)-@cardlength+1),@cardlength)      
			set @cardbefore = substring(@cardno,1,len(@cardno)-@cardlength)      
			set @cardseq = @cardseq+1      
			set @cardafter = cast(@cardseq as varchar(20))      
			while(len( cast(@cardafter as varchar(20)))<@cardlength)      
			begin      
				set @cardafter = '0'+@cardafter      
			end      
			set @cardno = @cardbefore+@cardafter      
		end      
  end      
  fetch cursor_allot into  @cardclass,@cardfromno,@cardtono,@cardcount      
  end      
 close cursor_allot      
 deallocate cursor_allot      
end
GO
/****** Object:  StoredProcedure [dbo].[upg_all_personal_comm_paymode]    Script Date: 01/14/2014 15:45:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_all_personal_comm_paymode](                                
	@compid				varchar(10),	-- 公司别                                
	@fromdate			varchar(10),	-- 开始日期                                
	@todate				varchar(10),	-- 截至日期
    @handtype			int				--1 查询 2 日结 3 员工业绩统计
   )                                                    
as                                
begin                              
	create table #allstaff_work_detail
	(
		seqno			int	identity		not null,       
		person_inid		varchar(20)			NULL, --员工内部编号
		action_id		int				NULL, --单据类型
		srvdate			varchar(10)			NULL, --日期
		code			varchar(20)			NULL, --项目代码,或是卡号,产品码
		name			varchar(40)			NULL, --名称
		payway			varchar(20)			NULL, --支付方式
		billamt			float				NULL, --营业金额
		ccount			float				NULL, --数量
		cost			float				NULL, --成本
		staffticheng	float				NULL, --提成
		staffyeji		float				NULL, --虚业绩
		prj_type		varchar(20)			NULL, --项目类别
		cls_flag        int					NULL, -- 1:项目 2:产品 3:卡
		billid			varchar(20)			NULL, --单号
		paycode			varchar(20)			NULL, --支付代码
		compid			varchar(10)			NULL, --公司别
		cardid			varchar(20)			NULL, --会员卡号
		cardtype		varchar(20)			NULL, --会员卡类型
		postation		varchar(10)			NULL, --员工部门
	)     
	create clustered index idx_work_detail_action_id on #allstaff_work_detail(action_id,code)  
	   
	exec upg_prepare_yeji_analysis @compid,@fromdate, @todate
  
	update #allstaff_work_detail set payway=parentcodevalue 
	from #allstaff_work_detail,commoninfo
	where infotype='ZFFS' and parentcodekey=paycode
	
	update #allstaff_work_detail set cardtype='' where cardtype='ZK' and paycode<>'4'
	
	create table #empinfobydate(                                
		seqno		int	identity		not null,                    
		compid		varchar(10)			null,                        
		inid		varchar(20)			null,                                
		empid		varchar(20)			null,                                
		datefrom	varchar(8)			null,                                
		dateto		varchar(8)			null,                                
		position	varchar(10)			null,                                
		salary		float				null,                                
		sharetype	varchar(5)			null,                                
		sharerate	float				null,                                
		deducttax	int					null,                                 
	 )                       
                   
    insert into #empinfobydate(compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                           
	exec upg_get_empinfo_by_date_comps @compid,@fromdate,@todate  
    --门店模式
	declare @comptypebyfinger varchar(5)                  
	select  @comptypebyfinger=ISNULL(compmode,'1') from companyinfo where compno=@compid   
	                                  
	 declare @empPostion varchar(10)                                              
	 declare @tmpSeqId int                                                        
	 declare @tmpEmpId varchar(20)                                                        
	 declare @tmpItem varchar(10)                                                        
	 declare @tmpYeji float                                                        
	 declare @tmpPrjId varchar(20)                                                        
	 declare @tmpDate varchar(8)                                                        
	 declare @paycode varchar(10)                      
	 declare @emp_total_yeji float                                       
	                                       
	 declare @GOODS_TYPE varchar(5)                                      
	 declare @PROJECT_COST float                                    
	 declare @Performance_Ratio float                                    
	 declare @Wage_Rates float                                    
	 declare @CARD_SALE_RATE float                                     
	 set @CARD_SALE_RATE=0.06                                    
	 declare @PROJECT_TYPE varchar(5)                                    
	 declare @GOODS_SALE_RATE_buty float                                    
	 declare @GOODS_SALE_RATE_hair float                                  
	 declare @GOODS_SALE_RATE_finger float --美发售产品提成比率                                       
	                                     
	 set  @GOODS_SALE_RATE_buty=0.1                                           
	 set  @GOODS_SALE_RATE_hair=0.05                          
	 set  @GOODS_SALE_RATE_finger=0.6                                  
	 declare @cardtype  varchar(20)  --会员卡类别                                    
	 declare @quan float                                
	 declare @fuflag float --正负单据                                
	 declare @businessflag int --是否为业务人员 0--不是 1--是                                 
	 declare @empinid varchar(20)                            
	 declare @proflag int --项目类别                                               
	 declare cur_yeji_ticheng cursor for                                                         
	 select seqno,person_inid,action_id,staffyeji,code,srvdate ,paycode,isnull(cardtype,''),isnull(ccount,0)                                        
	 from #allstaff_work_detail                                                        
	 declare @empTicheng float     
	                                                        
	 open cur_yeji_ticheng                                                        
	 fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate,@paycode ,@cardtype,@quan                                                          
	 while(@@fetch_status=0)                    
	 begin                                     
		set @empTicheng = 0   
		--更新员工的最新职位      
		select @empPostion=position ,@empinid=inid from #empinfobydate where inid=@tmpEmpId and @tmpDate>=datefrom and @tmpDate<dateto                     
	    -- 查看员工是否是业务人员   
	    select @businessflag=ISNULL(businessflag,0)  from staffinfo with(nolock)  where manageno=@tmpEmpId                             
	    -- 如果是非业务人员提成为0
	    if(@businessflag=0 or @empPostion in ('001','00101','00102','00103','00104','002','010','011','01101','01102','01201','01202','803'))                                      
		begin                                
			update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                   
			fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan                                
			continue                                   
		end
		-- 16，17，18，19，20，21，22，23，24-售产品  
		if(isnull(@tmpItem,'') in ('16','17','18','19','20','21','22','23','24'))                                                
		begin      
				select   @GOODS_TYPE=isnull(goodstype,1)  from  goodsnameinfo with(nolock) where goodsno=@tmpPrjId                                      
			if(isnull(@GOODS_TYPE,'300'))='400'----美容产品(扣除成本20%提成10%)                                      
			begin                                      
				update #allstaff_work_detail set staffyeji=isnull(staffyeji,0)*0.8 where seqno=@tmpSeqId                                       
				set @empTicheng = isnull(@tmpYeji,0)*0.8 *@GOODS_SALE_RATE_buty                                       
			end                                      
			else if(isnull(@GOODS_TYPE,'300'))='300'----美发产品（提成5%）                                       
			begin                                      
				update #allstaff_work_detail set staffyeji=isnull(staffyeji,0) where seqno=@tmpSeqId                                       
				set @empTicheng = isnull(@tmpYeji,0)*@GOODS_SALE_RATE_hair                                         
			end                                     
			else if(isnull(@GOODS_TYPE,'300'))='500'----美甲产品（产品不扣成本 4，6分）                                     
			begin                                         
				set @empTicheng = isnull(@tmpYeji,0)*@GOODS_SALE_RATE_finger--0.4                                      
			end                                  
			else if(isnull(@GOODS_TYPE,'300'))='700'----卡诗产品（无业绩无提成）                                           
			begin                                         
				update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                       
				set @empTicheng = 0                                
			end
                                                               
		end  
		-- 开卡+充值+转卡+条码卡开卡 1，2，3，5
		else if(isnull(@tmpItem,'')='1' or isnull(@tmpItem,'')='2'  or isnull(@tmpItem,'')='3' or isnull(@tmpItem,'')='5')                                     
		begin                                       
			if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师1.5%                                   
				set @empTicheng = @tmpYeji*0.015                                     
			else if (isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2%                                   
				set @empTicheng = @tmpYeji*0.02                                      
			else    -- 其他职位都是6%               
				set @empTicheng = @tmpYeji*@CARD_SALE_RATE                                              
		end   
		-- 合作项目销售  26，27，28，29，30，31                                     
	    else if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27'                        
			or  isnull(@tmpItem,'')='28' or isnull(@tmpItem,'')='29'                        
			or  isnull(@tmpItem,'')='30' or isnull(@tmpItem,'')='31' )                                   
		begin                                       
			if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27' or isnull(@tmpItem,'')='30' )                        
				set @empTicheng = @tmpYeji*0.06                           
			else if (isnull(@tmpItem,'')='28')                        
				set @empTicheng = @tmpYeji*0.3                        
		end  
		--疗程兑换  4
		else if(isnull(@tmpItem,'')='4')                               
		begin                                
			if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师2%                                   
				set @empTicheng = @tmpYeji*0.02                                     
			else if ( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2.5%                                   
				set @empTicheng = @tmpYeji*0.025                                    
			--首席，总监，设计师记业绩20%,提成20%                                 
			else if ( isnull(@empPostion,'')='003' or isnull(@empPostion,'')='006' or isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00102')                                  
			begin                                
				update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.2 where seqno=@tmpSeqId                                  
				set @empTicheng = isnull(@tmpYeji,0)*0.2*0.2                
			end                                
		end   
		--项目消费 7,8,9,10,11,12,13,14,15
		else
		begin
			set @fuflag=@quan   
			if(isnull(@paycode,'')='9') --疗程                                                  
			begin                                                    
				--疗程消费美容师和美发师按照设定成本和业绩比率走                                
				select @proflag=isnull(prjpricetype,2),@PROJECT_COST=isnull(costprice,0),@Performance_Ratio=isnull(lyjrate,1),@Wage_Rates=isnull(ltcrate,1),@PROJECT_TYPE=prjtype  
				from  projectinfo ,compchaininfo 
				where prisource=curcomp and relationcomp=@compid and prjno=@tmpPrjId                                 
				
				update #allstaff_work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                       
				set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                      
	                                       
				--烫染师 非疗程卡 记80%业绩 记5%提成 纯疗程卡记120块的业绩，6块钱的提成                                
				if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )                                    
				begin      
					if( isnull(@cardtype,'') ='MR' or isnull(@cardtype,'')='MF')                                    
					begin                                
						update #allstaff_work_detail set staffyeji=120*@fuflag where seqno=@tmpSeqId                                       
						set @empTicheng=6*@fuflag                                
					end                                 
					else                                
					begin                                     
						update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.8 where seqno=@tmpSeqId                         
						set @empTicheng = isnull(@tmpYeji,0)*0.8*0.05                                
						if(isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')--四级烫染师                                
							set @empTicheng = isnull(@tmpYeji,0)*0.8*0.06                                
					end                                   
				end                                                      
				if(isnull(@empPostion,'')in('004','00401','00402') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                
				begin                                
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                
				if(isnull(@empPostion,'') in ('003','006','007','00901','00902','00903','00904','008')  and ISNULL(@PROJECT_TYPE,'') ='4' and ISNULL(@proflag,2)=1)                                
				begin                                
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                 
				--美发的输入在中工和小工位置的(美发师,首席,总监)无提成                                
				if((@PROJECT_TYPE='3' or @PROJECT_TYPE='6') and isnull(@empPostion,'') in ('003','006','007') and isnull(@tmpItem,'') not in ('7','8','9'))                                
				begin                                
					set @empTicheng = 0                                 
				end                                
				if(@cardtype ='MFOLD' )                                 
				begin                                
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng =0                                
				end                                                   
			end 
			else if( (@cardtype ='ZK' and isnull(@paycode,'')='4') or isnull(@paycode,'')='$'  or isnull(@paycode,'')='A' or isnull(@paycode,'')='7' or isnull(@paycode,'')='11'  or isnull(@paycode,'')='12'  )               
			begin                             
					if(isnull(@tmpYeji,0)>0)                                
						set @fuflag=1                                
					else                                
						set @fuflag=-1                                
					--项目抵用券使用面值做业绩                                
					if(isnull(@paycode,'')='11' )                                
					begin      
						select @tmpYeji=ISNULL(cardfaceamt,0) from nointernalcardinfo where cardno=@cardtype                        
						if(ISNULL(@fuflag,0)<0)                      
							set  @tmpYeji=ISNULL(@tmpYeji,0)*(-1)                             
						update #allstaff_work_detail set staffyeji=@tmpYeji,billamt=@tmpYeji where seqno=@tmpSeqId                                  
					end                                 
					--烫染师 记业绩24% 提成5%                                
					if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                    
					begin                                
						update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
						set @empTicheng = isnull(@tmpYeji,0)*0.24*0.05                                  
						if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')          
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.06                                  
					end                                  
					--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                 
					else                                 
					begin                                
						update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
						set @empTicheng = isnull(@tmpYeji,0)*0.24*0.22                                
					end                                  
					---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                
					---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3   
					                  
					if(@tmpPrjId='300' or @tmpPrjId='3002' or @tmpPrjId='301' or @tmpPrjId='302' or @tmpPrjId='303' or @tmpPrjId='305' or @tmpPrjId='306'  or @tmpPrjId='309'   or @tmpPrjId='311'  )                                
					begin                                
						if(isnull(@empPostion,'')in('004','00401','00402') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                               
						begin                                
							if(@tmpPrjId='300' or @tmpPrjId='302' or @tmpPrjId='303'  or @tmpPrjId='309' )                                
							begin                                
								update #allstaff_work_detail set staffyeji=20*@fuflag where seqno=@tmpSeqId                                  
								set @empTicheng =6*@fuflag                                
							end                                 
							else                                
							begin                                
								update #allstaff_work_detail set staffyeji=5*@fuflag where seqno=@tmpSeqId                                  
								set @empTicheng =1.5*@fuflag                                
							end                                
						end                                       
						if((@tmpPrjId='300' or @tmpPrjId='3002') and isnull(@tmpItem,'') not in ('7','8','9'))                                
						begin                                
							if(isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007')--首席总监                                
							begin                                
								update #allstaff_work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
								set @empTicheng =3*@fuflag                                
							end                                       
							else if(isnull(@empPostion,'')='003')                                
							begin                                
								update #allstaff_work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
								set @empTicheng =2.8*@fuflag                                
							end                                       
							else                                 
							begin                                
								update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
								set @empTicheng =0                                
							end                                
						end                                
					end                         
			end 
			else
			begin
					select @proflag=isnull(prjpricetype,2),@PROJECT_COST=isnull(costprice,0),@Performance_Ratio=isnull(kyjrate,1),@Wage_Rates=isnull(ktcrate,1),@PROJECT_TYPE=prjtype  
					from  projectinfo ,compchaininfo 
					where prisource=curcomp and relationcomp=@compid  and prjno=@tmpPrjId  
					if(@PROJECT_TYPE<>'5')--美甲成本单独算                  
					begin                  
						update #allstaff_work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                       
					end                  
					set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                      
					--首席总监 扣除成本后×业绩比率，提成 30%                                
					if((isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007') and ISNULL(@PROJECT_TYPE,'0')<>'6' )                                    
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*0.3                                     
					--烫染师 扣除成本后记业绩5%                                
					if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  )                                    
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0)*0.05                                     
					if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0)*0.06                                     
		                 
					---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                
					---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3                                    
					if(@tmpPrjId='300' or @tmpPrjId='3002' or @tmpPrjId='301' or @tmpPrjId='302' or @tmpPrjId='303' or @tmpPrjId='305' or @tmpPrjId='306'  or @tmpPrjId='309'   or @tmpPrjId='311'  )                                
					begin                                
						if(isnull(@empPostion,'')='003') --美发师                                
						begin                                
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.75 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.75*0.28                                  
						end                                 
						else if(isnull(@empPostion,'')='006') --美发师                                
						begin                                
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.85 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.85*0.3                                  
						end                             
						else if(isnull(@empPostion,'')='007') --美发师                                
						begin                                
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.89 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.89*0.3                                  
						end                                 
						else if(isnull(@empPostion,'')in('004','00401','00402') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                        
						begin                                
							if(@tmpPrjId='300' or @tmpPrjId='302' or @tmpPrjId='303'  or @tmpPrjId='309' )                              begin                                
								update #allstaff_work_detail set staffyeji=20*@fuflag where seqno=@tmpSeqId                                  
							set @empTicheng =6*@fuflag                                
						end                                 
						else                                
						begin                                
							update #allstaff_work_detail set staffyeji=5*@fuflag where seqno=@tmpSeqId                                  
							set @empTicheng =1.5*@fuflag                                
						end                                
					end  
					if((@tmpPrjId='300' or @tmpPrjId='3002') and isnull(@tmpItem,'') not in ('7','8','9'))                 
					begin                                      
						if(isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007')--首席总监        
						begin                                
							update #allstaff_work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
							set @empTicheng =3*@fuflag                                
						end                                       
						else if(isnull(@empPostion,'')='003')                                
						begin                                
							update #allstaff_work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
							set @empTicheng =2.8*@fuflag                                
						end                                       
						else                                 
						begin                                
							update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
							set @empTicheng =0                                
						end                        
		                                       
					end                                
				end                                
				if(isnull(@paycode,'')='13' )                                
				begin                                
					declare @tmcardfrom int --0 正常开卡,1 赠送开卡                                
					select @tmcardfrom=ISNULL(entrytype,0) from nointernalcardinfo where cardno=@cardtype                                
					if(ISNULL(@tmcardfrom,0)=1)                                
					begin                                
						--烫染师 记业绩24% 提成5%                                
						if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                    
						begin                                
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.05                                  
							if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )                                
								set @empTicheng = isnull(@tmpYeji,0)*0.24*0.06                                  
						end                                  
						--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                 
						else                                 
						begin                                
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.22                                
						end                                 
					end                                 
				end        
				--美发的输入在中工和小工位置的(美发师,首席,总监)无提成                                
				if((@PROJECT_TYPE='3' or @PROJECT_TYPE='6') and isnull(@empPostion,'') in ('003','006','007') and isnull(@tmpItem,'') not in ('7','8','9'))                                
				begin                                
					set @empTicheng = 0                                 
				end                                
				else if(@PROJECT_TYPE='5')-- 美甲业绩扣10%的成本，提成4，6分                                
				begin                                
					if(isnull(@empPostion,'')='005' )                                
					begin                    
						if( ISNULL(@comptypebyfinger,'1')='1')                    
						begin                       
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.9 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.9*0.6                      
						end                              
					end                                
					else                                
					begin                                
						update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
						set @empTicheng =0                                
					end                                
				end  
				                                  
				if(isnull(@empPostion,'') in('004','00401','00402')  and @tmpPrjId in ('48811') and isnull(@paycode,'')<>'13')                                
				begin                                
					if(isnull(@tmpItem,'') in ('7','8','9'))                
					begin       
						if(isnull(@tmpYeji,0)<100)     
							set @tmpYeji=100                      
						update #allstaff_work_detail set staffyeji= isnull(@tmpYeji,0)*0.8 where seqno=@tmpSeqId           
						set @empTicheng = isnull(@tmpYeji,0)*0.8*0.3*@fuflag                
					end                           
					else                                
					begin                                
						update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
						set @empTicheng = 0               
					end                                
				end                               
				if(isnull(@empPostion,'')in('004','00401','00402') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                
				begin                       
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                
				if(isnull(@empPostion,'') in ('003','006','007','00901','00902','00903','00904','008')  and ISNULL(@PROJECT_TYPE,'') ='4' and ISNULL(@proflag,2)=1)                                
				begin                                
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                 
			end 
		end              
		---更新提成                             
		update #allstaff_work_detail set staffticheng=@empTicheng,person_inid=@empinid,postation=@empPostion where seqno=@tmpSeqId                                                                  
	  fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan                          
	 end                                                        
	 close cur_yeji_ticheng                                                        
	 deallocate cur_yeji_ticheng            
	 
		if(@handtype=2)
		begin
			delete allstaff_work_detail_daybyday where compid=@compid and srvdate=@fromdate
			insert allstaff_work_detail_daybyday(compid,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,cardid,cardtype,postation)
			select compid,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,cardid,cardtype,postation
			from #allstaff_work_detail
			
			
		end
		 --美发师做美发项目 超过指定业绩增高普通的提成比率                                
		--美发师业绩超2.5W,首席超5W,总监 7W 美发项目提成比率0.35                                
		create  table #emp_yeji_total_resultx                                 
		(                                
			inid		varchar(20) null,  --员工内部编号                                   
			yeji		float  null, -- 业绩                 
			lv			float  null, --提成比率                                     
		)                                   
            
       	insert #emp_yeji_total_resultx(inid,yeji)                                       
		select  person_inid,sum(isnull(staffyeji,0)) from #allstaff_work_detail where ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15') group by person_inid                              
                
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='003'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		and b.yeji>=25000 and  prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='006'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		and b.yeji>=50000 and  prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='007'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		 and b.yeji>=70000  and prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
               
               
		---美容师A类              
		if(@compid in ('008','017','019','026','032'))              
		begin              
               
		 update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05  
		 from #allstaff_work_detail a,#emp_yeji_total_resultx b              
		 where a.person_inid=b.inid              
		   and ISNULL(a.postation,'')='004'              
		   and (action_id>=7 and action_id<=15)  
		   and paycode not in('11','12','7','8','A','13')  
		   and cardtype not in('ZK')  
		   and yeji>=70000  
		   and code not in('300','3002','301','302','303','305','306','309','311')              
		   and action_id not in (26,27,28,29,30,31)  
  
		  --条码卡支付的时候需要判断是否为赠送  
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05  
		  from #allstaff_work_detail a,nointernalcardinfo,#emp_yeji_total_resultx b  
		  where a.person_inid=b.inid               
			and a.cardtype=cardno              
			and ISNULL(a.postation,'')='004'              
			and (action_id>=7 and action_id<=15)
			and paycode='13'              
			and yeji>=70000                 
			and isnull(entrytype,0)=0              
			and code not in('300','3002','301','302','303','305','306','309','311')              
			and action_id not in (26,27,28,29,30,31)              
		                  
                 
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.02              
		 from #allstaff_work_detail a,#emp_yeji_total_resultx b              
		 where a.person_inid=b.inid and ISNULL(a.postation,'')='004'              
		   and paycode not in('11','12','7','8','A','13')              
		   and cardtype not in('ZK')              
		   and (action_id>=7 and action_id<=15)
		   and yeji<70000               
		   and yeji>=50000              
		   and code not in('300','3002','301','302','303','305','306','309','311')              
		   and action_id not in (26,27,28,29,30,31)              
                 
		   --条码卡支付的时候需要判断是否为赠送              
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.02              
		  from #allstaff_work_detail a,nointernalcardinfo,#emp_yeji_total_resultx b              
		  where a.person_inid=b.inid               
			and a.cardtype=cardno              
			and ISNULL(a.postation,'')='004'              
			and (action_id>=7 and action_id<=15)       
			and paycode='13'              
			and yeji<70000               
			and yeji>=50000              
			and isnull(entrytype,0)=0              
			and action_id not in (26,27,28,29,30,31)              
	end              
	drop table #emp_yeji_total_resultx                                
                                 
                                
                                            
                    
	--现有提成方式                  
	--承包方式:五五分成,扣5%成本                  
	--公司直营:个人业绩1-5000 50% 扣10%成本,5000以上35%扣10%成本                               
                    
    if(ISNULL(@comptypebyfinger,'1')='3') --承包方式                  
    begin                  
		update #allstaff_work_detail set staffyeji=ISNULL(staffyeji,0)*0.95,staffticheng=isnull(staffticheng,0)*0.95*0.5                  
		from #allstaff_work_detail,staffinfo where Manageno=person_inid and position = '005'                  
    end             
    else  if(ISNULL(@comptypebyfinger,'1')='2') --公司直营                   
    begin                  
                  
			update #allstaff_work_detail set staffyeji=ISNULL(staffyeji,0)*0.9,staffticheng=isnull(staffticheng,0)*0.5*0.9                  
			where  person_inid in (select person_inid from #allstaff_work_detail ,staffinfo where Manageno=person_inid and position = '005'                  
			group by person_inid having SUM(ISNULL(staffyeji,0))<=5000 )                   
                    
                        
			update #allstaff_work_detail set staffyeji=ISNULL(staffyeji,0)*0.9,staffticheng=isnull(staffticheng,0)*0.35*0.9                  
			where  person_inid in (select person_inid from #allstaff_work_detail ,staffinfo where Manageno=person_inid and position = '005'                  
			group by person_inid having SUM(ISNULL(staffyeji,0))>5000 )                   
    end   
               

  
	create table #allstaff_work_analysis
	(
		person_inid			varchar(20)			NULL,	--员工内部编号
		staffno				varchar(30)			NULL,	--员工工号
		staffname			varchar(30)			NULL,	--员工姓名
		staffposition		varchar(30)			NULL,	--员工职位
		oldcostcount		float				NULL,	--老客项目数
		newcostcount		float				NULL,	--新客项目数
		trcostcount			float				NULL,	--烫染项目数
		cashbigcost			float				NULL,	--现金大项
		cashsmallcost		float				NULL,	--现金小项
		cashhulicost		float				NULL,	--现金护理
		cardbigcost			float				NULL,	--销卡大项
		cardsmallcost		float				NULL,	--销卡小项
		cardhulicost		float				NULL,	--销卡护理
		cardprocost			float				NULL,	--疗程消费
		cardsgcost			float				NULL,	--收购卡消费
		cardpointcost		float				NULL,	--积分消费
		projectdycost		float				NULL,	--项目抵用券
		cashdycost			float				NULL,	--现金抵用券
		tmcardcost			float				NULL,	--条卡卡消费
		salegoodsamt		float				NULL,	--产品销售
		salecardsamt		float				NULL,	--卡销售
		prochangeamt		float				NULL,	--疗程兑换
		saletmkamt			float				NULL,	--条码卡销售
		qhpayinner			float				NULL,	--全韩店内支付
		qhpayouter			float				NULL,	--全韩对方支付
		jdpayinner			float				NULL,	--暨大店内支付
		smpayinner			float				NULL,	--私密店内支付
		staffyeji			float				NULL,	--员工提成合计
		staffcashyeji		float				NULL,	--员工现金业绩合计
	) 
	create clustered index idx_work_analysis_person_inid on #allstaff_work_analysis(person_inid)  
	
	if(@handtype=3)--员工业绩统计表
	begin
		insert #allstaff_work_analysis(person_inid,staffyeji)
		select person_inid,SUM(isnull(staffyeji,0)) from #allstaff_work_detail where isnull(person_inid,'')<>'' group by person_inid 
		
		update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position
		from #allstaff_work_analysis a,staffinfo b
		where a.person_inid=b.Manageno
		
		--现金大项
		update a set cashbigcost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode in ('1','2','6','0','14','15') ),0)
		from #allstaff_work_analysis a
		
		--现金小项
		update a set cashsmallcost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode in ('1','2','6','0','14','15') ),0)
		from #allstaff_work_analysis a
		
		--现金护理
		update a set cashhulicost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode in ('1','2','6','0','14','15') ),0)
		from #allstaff_work_analysis a
		
		--销卡大项
		update a set cardbigcost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode ='4' ),0)
		from #allstaff_work_analysis a
		
		--销卡小项
		update a set cardsmallcost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode  ='4' ),0)
		from #allstaff_work_analysis a
		
		--销卡护理
		update a set cardhulicost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode  ='4' ),0)
		from #allstaff_work_analysis a
		
		--疗程消费
		update a set cardprocost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='9' ),0)
		from #allstaff_work_analysis a
		
		--收购卡消费
		update a set cardsgcost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='A' ),0)
		from #allstaff_work_analysis a
		
		--积分消费
		update a set cardpointcost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='7' ),0)
		from #allstaff_work_analysis a
		
		--项目抵用券消费
		update a set projectdycost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='11' ),0)
		from #allstaff_work_analysis a
		
		--现金抵用券消费
		update a set cashdycost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='12' ),0)
		from #allstaff_work_analysis a
		
		--条码卡消费
		update a set tmcardcost=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='13' ),0)
		from #allstaff_work_analysis a
		
		--产品销售
		update a set salegoodsamt=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id >=16 and b.action_id<=24  ),0)
		from #allstaff_work_analysis a
		
		--会员卡销售
		update a set salecardsamt=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id >=1 and b.action_id<=3  ),0)
		from #allstaff_work_analysis a
		
		--疗程兑换
		update a set prochangeamt=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id =4  ),0)
		from #allstaff_work_analysis a
		
		--条码卡销售
		update a set saletmkamt=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id =5  ),0)
		from #allstaff_work_analysis a
		

		--全韩店内支付
		update a set qhpayinner=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id =28  ),0)
		from #allstaff_work_analysis a
		
		--全韩对方支付
		update a set qhpayouter=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id =27 ),0)
		from #allstaff_work_analysis a
		
		--暨大店内支付
		update a set jdpayinner=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id =28  ),0)
		from #allstaff_work_analysis a
		
		--私密店内支付
		update a set smpayinner=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id =29  ),0)
		from #allstaff_work_analysis a
		
		--现金业绩
		update a set staffcashyeji=ISNULL((
			select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid  and b.paycode in ('1','2','6','0','14','15') and ISNULL(b.action_id,-1)<>4 ),0)
		from #allstaff_work_analysis a
		
		--新客项目数
		update a set  newcostcount=ISNULL((
			select count(distinct billid) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id in (8,11,14) and isnull(ccount,0)>0  ),0)
		from #allstaff_work_analysis a
		
		--老客项目数
		update a set oldcostcount=ISNULL((
			select count(distinct billid) from #allstaff_work_detail b
			 where a.person_inid=b.person_inid and b.action_id in (7,10,13) and isnull(ccount,0)>0  ),0)
		from #allstaff_work_analysis a
		
		--烫染项目数
		update a set trcostcount=ISNULL((
			select count(distinct billid) from #allstaff_work_detail b,projectnameinfo c
			 where a.person_inid=b.person_inid  and isnull(ccount,0)>0 and b.code=c.prjno and c.prjreporttype in ('02','03') ),0)
		from #allstaff_work_analysis a
	
		
		
	
		
		select  person_inid,staffno,staffname,staffposition,oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,
				cardbigcost,cardsmallcost,cardhulicost,cardprocost,cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,
				salegoodsamt,salecardsamt,prochangeamt,saletmkamt,qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji,staffcashyeji
		from #allstaff_work_analysis order by staffno
		
		drop table #empinfobydate
		drop table #allstaff_work_detail 
		drop table #allstaff_work_analysis 
		
		return 
	end
	
	insert #allstaff_work_analysis(person_inid,staffyeji)
	select person_inid,SUM(isnull(staffticheng,0)) from #allstaff_work_detail where isnull(person_inid,'')<>'' group by person_inid 
	
	update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position
	from #allstaff_work_analysis a,staffinfo b
	where a.person_inid=b.Manageno
	
	--现金大项
	update a set cashbigcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode in ('1','2','6','0','14','15') ),0)
	from #allstaff_work_analysis a
	
	--现金小项
	update a set cashsmallcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode in ('1','2','6','0','14','15') ),0)
	from #allstaff_work_analysis a
	
	--现金护理
	update a set cashhulicost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode in ('1','2','6','0','14','15') ),0)
	from #allstaff_work_analysis a
	
	--销卡大项
	update a set cardbigcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode ='4' ),0)
	from #allstaff_work_analysis a
	
	--销卡小项
	update a set cardsmallcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode  ='4' ),0)
	from #allstaff_work_analysis a
	
	--销卡护理
	update a set cardhulicost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode  ='4' ),0)
	from #allstaff_work_analysis a
	
	--疗程消费
	update a set cardprocost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='9' ),0)
	from #allstaff_work_analysis a
	
	--收购卡消费
	update a set cardsgcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='A' ),0)
	from #allstaff_work_analysis a
	
	--积分消费
	update a set cardpointcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='7' ),0)
	from #allstaff_work_analysis a
	
	--项目抵用券消费
	update a set projectdycost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='11' ),0)
	from #allstaff_work_analysis a
	
	--现金抵用券消费
	update a set cashdycost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='12' ),0)
	from #allstaff_work_analysis a
	
	--条码卡消费
	update a set tmcardcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='13' ),0)
	from #allstaff_work_analysis a
	
	--产品销售
	update a set salegoodsamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id >=16 and b.action_id<=24  ),0)
	from #allstaff_work_analysis a
	
	--会员卡销售
	update a set salecardsamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id >=1 and b.action_id<=3  ),0)
	from #allstaff_work_analysis a
	
	--疗程兑换
	update a set prochangeamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id =4  ),0)
	from #allstaff_work_analysis a
	
	--条码卡销售
	update a set saletmkamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id =5  ),0)
	from #allstaff_work_analysis a
	

	--全韩店内支付
	update a set qhpayinner=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id =28  ),0)
	from #allstaff_work_analysis a
	
	--全韩对方支付
	update a set qhpayouter=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id =27 ),0)
	from #allstaff_work_analysis a
	
	--暨大店内支付
	update a set jdpayinner=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id =28  ),0)
	from #allstaff_work_analysis a
	
	--私密店内支付
	update a set smpayinner=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id =29  ),0)
	from #allstaff_work_analysis a
	
	--新客项目数
	update a set newcostcount =ISNULL((
		select count(distinct billid) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id in (8,11,14) and isnull(ccount,0)>0  ),0)
	from #allstaff_work_analysis a
	
	--老客项目数
	update a set oldcostcount=ISNULL((
		select count(distinct billid) from #allstaff_work_detail b
		 where a.person_inid=b.person_inid and b.action_id in (7,10,13) and isnull(ccount,0)>0  ),0)
	from #allstaff_work_analysis a
	
	--烫染项目数
	update a set trcostcount=ISNULL((
		select count(distinct billid) from #allstaff_work_detail b,projectnameinfo c
		 where a.person_inid=b.person_inid  and isnull(ccount,0)>0 and b.code=c.prjno and c.prjreporttype in ('02','03') ),0)
	from #allstaff_work_analysis a
	
	--select seqno,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,compid,cardid,cardtype 
	--from #allstaff_work_detail order by action_id,srvdate
	if(@handtype=1)
	begin
		select  person_inid,staffno,staffname,staffposition,oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,
				cardbigcost,cardsmallcost,cardhulicost,cardprocost,cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,
				salegoodsamt,salecardsamt,prochangeamt,saletmkamt,qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji 
		from #allstaff_work_analysis order by staffno
	end
	else
	begin
	 --   delete allstaff_work_yeji_daybyday  where compid=@compid and yeji_date =@fromdate
		--insert allstaff_work_yeji_daybyday(yeji_date,compid,person_inid,staffno,staffname,staffposition,
		--oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,cardbigcost,cardsmallcost,cardhulicost,cardprocost,
		--cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,salegoodsamt,salecardsamt,prochangeamt,saletmkamt,
		--qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji)
		--select @fromdate,@compid,person_inid,staffno,staffname,staffposition,
		--oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,cardbigcost,cardsmallcost,cardhulicost,cardprocost,
		--cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,salegoodsamt,salecardsamt,prochangeamt,saletmkamt,
		--qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji from #allstaff_work_analysis
		

		delete staff_work_salary where compid=@compid and salary_date =@fromdate
		insert staff_work_salary(compid,person_inid,salary_date,oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,
				cardbigcost,cardsmallcost,cardhulicost,cardprocost,cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,
				salegoodsamt,salecardsamt,prochangeamt,saletmkamt,qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji)
				
		select @compid,person_inid,@fromdate,sum(isnull(oldcostcount,0)),sum(isnull(newcostcount,0)),sum(isnull(trcostcount,0)),
				sum(isnull(cashbigcost,0)),sum(isnull(cashsmallcost,0)),sum(isnull(cashhulicost,0)),
				sum(isnull(cardbigcost,0)),sum(isnull(cardsmallcost,0)),sum(isnull(cardhulicost,0)),
				sum(isnull(cardprocost,0)),sum(isnull(cardsgcost,0)),sum(isnull(cardpointcost,0)),
				sum(isnull(projectdycost,0)),sum(isnull(cashdycost,0)),sum(isnull(tmcardcost,0)),
				sum(isnull(salegoodsamt,0)),sum(isnull(salecardsamt,0)),sum(isnull(prochangeamt,0)),
				sum(isnull(saletmkamt,0)),sum(isnull(qhpayinner,0)),sum(isnull(qhpayouter,0)),
				sum(isnull(jdpayinner,0)),sum(isnull(smpayinner,0)),sum(isnull(staffyeji,0))
		from #allstaff_work_analysis
		group by person_inid
		
		update a
		set staffcashyeji=isnull((select  sum(isnull(staffyeji,0)) from #allstaff_work_detail b where ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15') and a.person_inid=b.person_inid  ),0)                            
		from staff_work_salary a where salary_date=@fromdate
		
	end
	
	drop table #empinfobydate
    drop table #allstaff_work_detail 
    drop table #allstaff_work_analysis 
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_classed_trade]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_comp_classed_trade]    
(
	@compid			varchar(10),		--门店号	
	@datefrom		varchar(10),		--起始日期
	@dateto			varchar(10),		--结束日期
	@usetype		int					--查询类型 1 显示 2被调用
)  
as
begin
	--计算业绩相关常量                                  
	declare @PRJ_BEAUT_CLASS_CODE varchar(10) --项目类别之美容类代码                                  
	declare @PRJ_HAIR_CLASS_CODE varchar(10)  --项目类别之美发类代码                                  
	declare @PRJ_FOOT_CLASS_CODE varchar(10)  --项目类别之足疗类代码                                  
	declare @PRJ_FINGER_CLASS_CODE varchar(10) --项目类别之美甲类代码                                  
	declare @GOODS_BEAUT_CLASS_CODE varchar(10) --物品类别之美容类代码                                  
	declare @GOODS_HAIR_CLASS_CODE varchar(10)  --物品类别之美发类代码                                  
	declare @DEP_BEAUT_CODE varchar(10)  --美容部门代码                                  
	declare @DEP_HAIR_CODE varchar(10)   --美发部门代码                                  
	declare @DEP_FOOT_CODE varchar(10)   --美发部门代码                                  
	declare @DEP_FINGER_CODE varchar(10)   --美发部门代码                                  
                                   
	set @PRJ_BEAUT_CLASS_CODE = '4'                                  
	set @PRJ_HAIR_CLASS_CODE = '3'                                  
	set @PRJ_FOOT_CLASS_CODE = '6'                                  
	set @PRJ_FINGER_CLASS_CODE = '5'                     
                                  
	set @GOODS_BEAUT_CLASS_CODE = '400'                                  
	set @GOODS_HAIR_CLASS_CODE = '300'                      
                                 
	set @DEP_BEAUT_CODE = '003'  --美容部                                 
	set @DEP_HAIR_CODE = '004'   --美发部                              
	set @DEP_FINGER_CODE = '005' --美甲部                     
	set @DEP_FOOT_CODE = '006'  --烫染部                              
                                  
	--11.项目消费美容类业绩; 12.项目消费美发类业绩；13.项目消费总业绩;14.项目消费足疗类业绩；15.项目消费美甲类业绩                                  
	--21.售美容类产品业绩;   22.售美发类产品业绩；  23.售产品总业绩；                                  
	--31.开卡美容类业绩；	 32.开卡美发类业绩； 33.开卡总业绩;    34.开卡足疗类业绩；    35.开卡美甲类业绩                                  
	--41.帐户异动美容类业绩；42.帐户异动美发类业绩；43.帐户异动总业绩 44.帐户异动足疗类业绩；45.帐户异动美甲类业绩                                  
	--51.卡异动美容类业绩；  52.卡异动美发类业绩；  53.卡异动总业绩   54.卡异动足疗类业绩；  55.卡异动美甲类业绩                                  
                                  
	--81.项目消费美容类实业绩；82.项目消费美发类实业绩；83.项目消费总实业绩；84.项目消费足疗类实业绩；85.项目消费美甲类实业绩                                  
	--91.售美容类产品实业绩；  92.售美发类产品实业绩；  93.售产品总实业绩；   
	
	--系统业绩表
	create table #yeji_result(                                  
		compid			varchar(10)			not null,		--门店编号                                 
		id				int					identity,		-- 流水号                                  
		item			varchar(5)				null,		-- 业绩项目类别                                  
		yeji			float					null,		-- 业绩                                  
		datefrom		varchar(8)				null,		-- 开始日期                                  
		dateto			varchar(8)				null,		-- 截止日期                                  
	) 
	--项目表                                 
	create table #prj_cls(                                  
		compid			varchar(10)		null,                                  
		prjid			varchar(20)		null,--项目编号                                  
		prjcls			varchar(10)		null--项目类别                                  
	 )                                  
	create clustered index idx_clust_prj_cls on #prj_cls(compid,prjcls,prjid)      
	                            
	insert into #prj_cls(compid,prjid,prjcls)                                  
	select relationcomp,prjno,prjtype from projectnameinfo,compchaininfo where curcomp=@compid                         
     
    --产品表                             
	create table #goods_cls(                                  
		compid			varchar(10)		null,                    
		goodsid			varchar(20)		null,--物品编号                                  
		goodscls		varchar(10)		null--物品类别                              
	 )                                  
	create clustered index idx_clust_goods_cls on #goods_cls(compid,goodsid,goodscls) 
	                                 
	insert into #goods_cls(compid,goodsid,goodscls)                                  
	select relationcomp,goodsno,goodstype from goodsnameinfo with(NOLOCK),compchaininfo where curcomp=@compid                                   
                   
	create table #emp_dep(                                
		compid		varchar(10)			null,                                
		inid		varchar(20)			null,--内部编号                               
		empid		varchar(20)			null,--员工编号                                
		empdep		varchar(10)			null,--员工部门                                
		datefrom	varchar(8)			null,                                
		dateto		varchar(8)			null,                                
	)                                
	create clustered index idx_tmp_emp_dep on #emp_dep(compid,empid,datefrom,dateto)     
                            
	create table #empinfobydate(                                
		seqno		int	identity		not null,                    
		compid		varchar(10)			null,                        
		inid		varchar(20)			null,                                
		empid		varchar(20)			null,                                
		datefrom	varchar(8)			null,                                
		dateto		varchar(8)			null,                                
		position	varchar(10)			null,                                
		salary		float				null,                                
		sharetype	varchar(5)			null,                                
		sharerate	float				null,                                
		deducttax	int					null,                                 
	 )                       
                   
    insert into #empinfobydate(compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                                
	exec upg_get_empinfo_by_date_comps @compid,@datefrom,@dateto                    
                
	insert into #emp_dep(compid,inid,empid,empdep,datefrom,dateto)                                
	select distinct compid,inid,empid,department,datefrom,dateto                                
	from #empinfobydate,staffinfo with (nolock)                                
	where inid=manageno and isnull(inid,'')<>''           
            
	update #emp_dep set empdep=olddepid from #emp_dep,staffhistory where effectivedate > datefrom and effectivedate<= dateto and manageno=inid   
	
	
	 --计算项目消费虚业绩                                  
	create table #m_d_consumeinfo(                                  
	  cscompid			varchar(10)     NULL,   --公司编号                                  
	  csbillid			varchar(20)		NULL,   --消费单号                               
	  financedate		varchar(8)		NULL,   --帐务日期 
	  csinfotype		int             NULL,	--消费类型  1 项目  2 产品 
	  csitemno			varchar(20)     NULL,   --项目代码                                  
	  csitemamt			float           NULL,   --金额                                  
	  csfirstsaler		varchar(20)     NULL,   --大工代码                                  
	  csfirstinid		varchar(20)		NULL,   --大工内部编号  
	  csfirstshare		float           NULL,	--大工分享                              
	  cssecondsaler		varchar(20)     NULL,   --中工代码                                  
	  cssecondinid		varchar(20)		NULL,   --中工内部编号 
	  cssecondshare		float           NULL,	--中工分享                               
	  csthirdsaler		varchar(20)     NULL,   --小工代码        
	  csthirdinid		varchar(20)		NULL,   --小工内部编号 
	  csthirdshare		float           NULL,	--小工分享                      
	  cspaymode			varchar(5)      NULL   --项目支付方式                                     
	)  
	create nonclustered index index_m_d_consumeinfo_csitemno  on #m_d_consumeinfo(cscompid,csinfotype,csitemno)  
	create nonclustered index index_m_d_consumeinfo_financedate  on #m_d_consumeinfo(cscompid,csinfotype,financedate)  
	 
	 insert into #m_d_consumeinfo(cscompid,csbillid,csinfotype,financedate,csitemno,csitemamt,csfirstsaler,csfirstinid,cssecondsaler,cssecondinid,csthirdsaler,csthirdinid,cspaymode,csfirstshare,cssecondshare,csthirdshare)                                  
	 select a.cscompid,a.csbillid,csinfotype,a.financedate,csitemno,csitemamt,csfirstsaler,csfirstinid,cssecondsaler,cssecondinid,csthirdsaler,csthirdinid,cspaymode,csfirstshare,cssecondshare,csthirdshare                                  
	   from mconsumeinfo a WITH (NOLOCK),dconsumeinfo b with (nolock)                                  
	  where a.cscompid=b.cscompid                                  
		and a.csbillid=b.csbillid                                  
		and a.cscompid = @compid                                  
		and financedate>=@datefrom                                   
		and financedate<=@dateto                                    
	--11.项目消费美容类业绩; 12.项目消费美发类业绩；13.项目消费总业绩                                  
	--81.项目消费美容类实业绩；82.项目消费美发类实业绩；83.项目消费总实业绩；84.项目消费足疗类实业绩；85.项目消费美甲类实业绩                                  
	--------------------------------------------计算项目虚实业绩----------Start-----------------------------------------------
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'11',isnull(sum(csitemamt),0)                                  
		 from #m_d_consumeinfo,#prj_cls                                  
		 where cscompid=compid                                   
		   and csitemno=prjid                                   
		   and prjcls=@PRJ_BEAUT_CLASS_CODE                                  
		   and isnull(cspaymode,'') in ('1','2','6','14','15','16') 
		   and isnull(csinfotype,1)=1                                  
		 group by cscompid                                
		                                 
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'12',isnull(sum(csitemamt),0)                                  
		 from #m_d_consumeinfo,#prj_cls                                  
		 where cscompid=compid                                   
		   and csitemno=prjid                                   
		   and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)                            
		   and isnull(cspaymode,'') in ('1','2','6','14','15','16')
		   and isnull(csinfotype,1)=1                                        
		 group by cscompid                       
		                     
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'13',isnull(sum(isnull(csitemamt,0) ),0)                                  
		 from #m_d_consumeinfo                                  
			where isnull(cspaymode,'') in ('1','2','6','14','15','16')  
			and isnull(csinfotype,1)=1           
		 group by cscompid                                
		 
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'15',isnull(sum(csitemamt),0)                                  
		 from #m_d_consumeinfo,#prj_cls                                  
		 where cscompid=compid                                   
		   and csitemno=prjid                                   
		   and prjcls=@PRJ_FINGER_CLASS_CODE                                  
		   and isnull(cspaymode,'') in ('1','2','6','16')  
		   and isnull(csinfotype,1)=1                                
		 group by cscompid       
		                      
		  insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'81',isnull(sum(csitemamt),0)                                  
		 from #m_d_consumeinfo,#prj_cls                              
		 where cscompid=compid                                   
		   and csitemno=prjid                                   
		   and prjcls=@PRJ_BEAUT_CLASS_CODE         
		   and isnull(csinfotype,1)=1                                    
		 group by cscompid                                  
		                                   
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'82',isnull(sum(csitemamt),0)                          
		 from #m_d_consumeinfo,#prj_cls                                  
		 where cscompid=compid                                   
		   and csitemno=prjid                                   
		   and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)   
		   and isnull(csinfotype,1)=1                                            
		 group by cscompid                                  
		                                             
		                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'83',isnull(sum(isnull(csitemamt,0) ),0) 
		 from #m_d_consumeinfo   
		 where isnull(csinfotype,1)=1                                    
		 group by cscompid         
		     
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'84',isnull(sum(csitemamt),0)                          
		 from #m_d_consumeinfo,#prj_cls                                  
		 where cscompid=compid                                   
		   and csitemno=prjid                                   
		   and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)     
		   and cspaymode='9'                      
		   and isnull(csinfotype,1)=1               
		 group by cscompid     
		                      
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'85',isnull(sum(csitemamt),0)                              
		 from #m_d_consumeinfo,#prj_cls                                  
		 where cscompid=compid                                   
		   and csitemno=prjid                                   
		   and prjcls=@PRJ_FINGER_CLASS_CODE    
		   and isnull(csinfotype,1)=1                                
		 group by cscompid   
		 
    --------------------------------------------计算项目虚实业绩-------END--------------------------------------------------
    
    --21.售美容类产品业绩;   22.售美发类产品业绩；  23.售产品总业绩                                  
	--91.售美容类产品实业绩；  92.售美发类产品实业绩；  93.售产品总实业绩；                                  
	                      
	--------------------------------------------计算产品虚实业绩-------Start------------------------------------------------
		  insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '21'
								when empdep=@DEP_HAIR_CODE		then '22'
								when empdep=@DEP_FOOT_CODE		then '24x'
								when empdep=@DEP_FINGER_CODE    then '25' end ,isnull(sum(csfirstshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=csfirstinid                                  
			and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                
			and financedate>=datefrom and financedate<dateto    and cscompid=compid  
			and isnull(csinfotype,1)=2                          
		  group by cscompid,empdep                                  
		                                  
		   insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '21'
								when empdep=@DEP_HAIR_CODE		then '22'
								when empdep=@DEP_FOOT_CODE	then '24x'
								when empdep=@DEP_FINGER_CODE    then '25' end,isnull(sum(cssecondshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=cssecondinid                                   
			and isnull(cspaymode,'') in ('1','2','6','14','15','16')                              
			and financedate>=datefrom and financedate<dateto   and cscompid=compid 
			and isnull(csinfotype,1)=2                                
		  group by cscompid,empdep                                 
		                                  
		   insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '21'
								when empdep=@DEP_HAIR_CODE		then '22'
								when empdep=@DEP_FOOT_CODE		then '24x'
								when empdep=@DEP_FINGER_CODE    then '25' end,isnull(sum(csthirdshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=csthirdinid                                  
		   and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                
		   and financedate>=datefrom and financedate<dateto  and cscompid=compid 
		   and isnull(csinfotype,1)=2                               
		  group by cscompid,empdep                                 
		                                  
		                            
		                  
		  insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'23',sum(isnull(csitemamt,0))                                 
		 from #m_d_consumeinfo                                  
		 where isnull(cspaymode,'') in ('1','2','6','14','15','16')
		 and isnull(csinfotype,1)=2          
		 group by cscompid                       
		                     
		                     
		 insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,'24',isnull(sum(csfirstshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=csfirstinid and empdep=@DEP_FOOT_CODE                                  
			--and isnull(cspaymode,'') in ('1','2','6','14','15')                                
			and financedate>=datefrom and financedate<dateto    and cscompid=compid 
			and isnull(csinfotype,1)=2                               
		  group by cscompid                     
		                            
		                                  
		   insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,'24',isnull(sum(cssecondshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=cssecondinid and empdep=@DEP_FOOT_CODE                                  
			--and isnull(cspaymode,'') in ('1','2','6','14','15')                              
			and financedate>=datefrom and financedate<dateto and cscompid=compid  
			 and isnull(csinfotype,1)=2                                 
		  group by cscompid                     
		           
		   insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,'24',isnull(sum(csthirdshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=csthirdinid and empdep=@DEP_FOOT_CODE                                  
		  -- and isnull(cspaymode,'') in ('1','2','6','14','15')                     
		   and financedate>=datefrom and financedate<dateto     and cscompid=compid   
		    and isnull(csinfotype,1)=2                            
		  group by cscompid                    
			                              
		                     
		                                 
		                                 
		                                 
		   insert into #yeji_result(compid,item,yeji)                               
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '91'
								when empdep=@DEP_HAIR_CODE		then '92'
								when empdep=@DEP_FOOT_CODE	then '94'
								when empdep=@DEP_FINGER_CODE    then '95' end,isnull(sum(csfirstshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=csfirstinid                           
		  and financedate>=datefrom and financedate<dateto    and cscompid=compid    
		   and isnull(csinfotype,1)=2                            
		  group by cscompid,empdep                               
		                         	            
		   insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '91'
								when empdep=@DEP_HAIR_CODE		then '92'
								when empdep=@DEP_FOOT_CODE	then '94'
								when empdep=@DEP_FINGER_CODE    then '95' end,isnull(sum(cssecondshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=cssecondinid                             
		  and financedate>=datefrom and financedate<dateto     and cscompid=compid
		   and isnull(csinfotype,1)=2                              
		  group by cscompid,empdep                                 
		            	          
		   insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '91'
								when empdep=@DEP_HAIR_CODE		then '92'
								when empdep=@DEP_FOOT_CODE		then '94'
								when empdep=@DEP_FINGER_CODE    then '95' end,isnull(sum(csthirdshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=csthirdinid              
		  and financedate>=datefrom and financedate<dateto    and cscompid=compid   
		   and isnull(csinfotype,1)=2                              
		  group by cscompid ,empdep                              
		                                  
	     
		                                 
		  insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'93',ISNULL(SUM(csitemamt),0)--isnull(sum(isnull(csfirstshare,0)+isnull(cssecondshare,0)+isnull(csthirdshare,0)),0)                                  
		 from #m_d_consumeinfo     
		 where isnull(csinfotype,1)=2                               
		 group by cscompid                                  
	                         
		        
	--------------------------------------------计算产品虚实业绩-------END--------------------------------------------------
		  drop table #m_d_consumeinfo
	 
	 CREATE TABLE #msalecardinfo  -- 会员卡销售单                                  
	 (                                  
		  salecompid			varchar(10)			NULL,		--公司编号                                  
		  salebillid			varchar(20)			NULL,		--销售单号                                  
		  firstsalerinid		varchar(20)			NULL,		--负责业务                                  
		  firstsaleamt			float				NULL,		--第一销售虚业绩                                  
		  secondsalerinid		varchar(20)			NULL    ,   --第二卖卡人                                  
		  secondsaleamt			float				NULL    ,	--第二销售虚业绩                                   
		  thirdsalerinid		varchar(20)			NULL    ,   --第三卖卡人                                  
		  thirdsaleamt			float				NULL    ,	--第三销售虚业绩                                   
		  fourthsalerinid		varchar(20)			NULL    ,   --第4卖卡人                                  
		  fourthsaleamt			float				NULL    ,	--第四人员业绩                                  
		  fifthsalerinid		varchar(20)			NULL    ,   --第5卖卡人                                  
		  fifthsaleamt			float				NULL    ,	--第五人员业绩                                  
		  sixthsalerinid		varchar(20)			NULL    ,   --第6卖卡人                                  
		  sixthsaleamt			float				NULL    ,	--第六人员业绩                                  
		  seventhsalerinid		varchar(20)			NULL    ,   --第7卖卡人                                  
		  seventhsaleamt		float				NULL    ,	--第七人员业绩                                  
		  eighthsalerinid		varchar(20)			NULL    ,   --第8卖卡人                                  
		  eighthsaleamt			float				NULL    ,	--第八人员业绩 
		  ninthsalerinid		varchar(20)			NULL    ,   --第9卖卡人                                  
		  ninthsaleamt			float				NULL    ,	--第9人员业绩  
		  tenthsalerinid		varchar(20)			NULL    ,   --第10卖卡人                                  
		  tenthsaleamt			float				NULL    ,	--第10人员业绩                             
		  financedate			varchar(8)			NULL ,		--帐务日期                                  
		  salebakflag			int					NULL                                  
	 )
	 create clustered index index_msalecardinfo on #msalecardinfo(salecompid,financedate)                                  

	insert into #msalecardinfo(salecompid,salebillid,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,
			fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,
			eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,financedate,salebakflag)                                     
	select salecompid,salebillid,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,
			fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,
			eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,financedate,salebakflag                                  
	from msalecardinfo with (nolock)                               
	where salecompid=@compid and financedate>=@datefrom and financedate<=@dateto and isnull(salebakflag,0)<>1   and ISNULL(salebakflag,0)=0
	
	--------------------------------------------计算售卡业绩-------Start------------------------------------------------
	
			--第1卖卡人业绩                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(firstsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=firstsalerinid  and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep                               
		                 
		                         
		                                  
		 --第2卖卡人业绩      
		 insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(secondsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=secondsalerinid   and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep     
		                             
		              
		 --第3卖卡人业绩    
		  insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(thirdsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=thirdsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep  
		                               
		                  
		 --第4卖卡人业绩   
		  insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(fourthsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=fourthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep  
		                                
		                             
		 --第5卖卡人业绩     
		   insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(fifthsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=fifthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep  
		                              
		                
		 --第6卖卡人业绩        
		 insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(sixthsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=sixthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep  
		                         
		 --第7卖卡人业绩  
		  insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(seventhsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=seventhsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep 
		                       
		 --第8卖卡人业绩     
		  insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(eighthsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=eighthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep 
		 
		  --第8卖卡人业绩     
		  insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(eighthsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=eighthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep 
		 
		  --第8卖卡人业绩     
		  insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(eighthsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=eighthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep 
		 
		  --第9卖卡人业绩     
		  insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(ninthsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=ninthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep 
		 
		  --第10卖卡人业绩     
		  insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case when empdep=@DEP_BEAUT_CODE		then '31'
								when empdep=@DEP_HAIR_CODE		then '32'
								when empdep=@DEP_FINGER_CODE	then '35'  end ,isnull(sum(tenthsaleamt),0)                                   
		 from #emp_dep ,#msalecardinfo                                  
		 where inid=tenthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                  
		 group by salecompid ,empdep 
		
		
		 insert into #yeji_result(compid,item,yeji)                                    
		 select salecompid,'33',isnull(sum(payamt),0)                                     
		 from #msalecardinfo,dpayinfo with(nolock)                                  
		 where isnull(salebakflag,0)<>1 and salecompid=paycompid and salebillid=paybillid and paybilltype='SK' and paymode in ('1','2','6','14','15','16')                                   
		 group by salecompid                                   
		                                  
		 drop table #msalecardinfo             
		  
	--------------------------------------------计算售卡业绩-------END--------------------------------------------------                                
	--41.帐户异动美容类业绩；42.帐户异动美发类业绩；43.帐户异动总业绩                                  
	 CREATE TABLE  #mcardrechargeinfo               -- 帐户异动单                                  
	 (                                  
		  rechargecompid		varchar(10)			NULL,   --公司编号                                   
		  rechargebillid		varchar(20)			NULL,   --异动单号                
		  rechargetype			int					NULL,   --异动类别( 0充值 ,1取款 5欠款 6还款) 
		  firstsalerinid		varchar(20)			NULL,   --销售人员                                  
		  firstsaleamt			float				NULL,   --第一销售虚业绩                                   
		  secondsalerinid		varchar(20)			NULL,   --第二销售                                  
		  secondsaleamt			float				NULL,   --第二销售虚业绩                                    
		  thirdsalerinid		varchar(20)			NULL,   --第三负责人                              
		  thirdsaleamt			float				NULL,   --第三销售虚业绩                                   
		  fourthsalerinid		varchar(20)			NULL,   --第4负责人                                  
		  fourthsaleamt			float				NULL,	--第四人员业绩                                  
		  fifthsalerinid		varchar(20)			NULL,   --第5负责人                                  
		  fifthsaleamt			float				NULL,	--第五人员业绩                                  
		  sixthsalerinid		varchar(20)			NULL,   --第6负责人                                  
		  sixthsaleamt			float				NULL,	--第六人员业绩                                  
		  seventhsalerinid		varchar(20)			NULL,   --第7负责人                                  
		  seventhsaleamt		float				null,	--第七人员业绩                                  
		  eighthsalerinid		varchar(20)			NULL,   --第8负责人                                  
		  eighthsaleamt			float				NULL,	--第八人员业绩  
		  ninthsalerinid		varchar(20)			NULL    ,   --第9卖卡人                                  
		  ninthsaleamt			float				NULL    ,	--第9人员业绩  
		  tenthsalerinid		varchar(20)			NULL    ,   --第10卖卡人                                  
		  tenthsaleamt			float				NULL    ,	--第10人员业绩  
		  backbillid			varchar(20)			NULL,   --反充的时候对应原始单号                        
		  financedate			varchar(8)			NULL,   --帐务日期                    
		  salebakflag			int					NULL                                  
	 )
	 create clustered index index_mcardrechargeinfo on #mcardrechargeinfo(rechargecompid,financedate)                                     
	 insert into #mcardrechargeinfo(rechargecompid,rechargebillid,rechargetype,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,
			fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,
			eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,financedate,salebakflag,backbillid)                                  
	select rechargecompid,rechargebillid,rechargetype,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,
			fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,
			eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,financedate,salebakflag,backbillid                                    
	from mcardrechargeinfo with (nolock)                                
	where rechargecompid=@compid and financedate>=@datefrom and financedate<=@dateto and rechargetype in (0,1,6) and ISNULL(salebakflag,0)=0  and isnull(backbillid,'')=''                                 
 	--------------------------------------------计算充值业绩-------Start--------------------------------------------------  
	--第1负责人充值/还款业绩                                   
		 insert into #yeji_result(compid,item,yeji)                                  
		 select rechargecompid,case when empdep=@DEP_BEAUT_CODE	then '41'
								when empdep=@DEP_HAIR_CODE		then '42'
								when empdep=@DEP_FINGER_CODE	then '45' end ,isnull(sum(firstsaleamt),0)                                  
		 from #emp_dep ,#mcardrechargeinfo                                  
		 where inid=firstsalerinid   and financedate>=datefrom and financedate<dateto   and rechargecompid=compid                                
		 group by rechargecompid,empdep                                  
		                  
		              
		 --第2负责人充值/还款业绩                                   
		 insert into #yeji_result(compid,item,yeji)                                  
		 select rechargecompid,case when empdep=@DEP_BEAUT_CODE	then '41'
								when empdep=@DEP_HAIR_CODE		then '42'
								when empdep=@DEP_FINGER_CODE	then '45' end,isnull(sum(secondsaleamt),0)                                  
		 from #emp_dep ,#mcardrechargeinfo                                  
		 where inid=secondsalerinid   and financedate>=datefrom and financedate<dateto     and rechargecompid=compid                               
		 group by rechargecompid,empdep                                      
		                
		 --第3负责人充值/还款业绩                                
		 insert into #yeji_result(compid,item,yeji)                                  
		 select rechargecompid,case when empdep=@DEP_BEAUT_CODE	then '41'
								when empdep=@DEP_HAIR_CODE		then '42'
								when empdep=@DEP_FINGER_CODE	then '45' end,isnull(sum(thirdsaleamt),0)                                  
		 from #emp_dep ,#mcardrechargeinfo                                  
		 where inid=thirdsalerinid     and financedate>=datefrom and financedate<dateto     and rechargecompid=compid                            
		 group by rechargecompid  ,empdep                                     
		                                  
		              
		 --第4负责人充值/还款业绩                                   
		 insert into #yeji_result(compid,item,yeji)                                  
		 select rechargecompid,case when empdep=@DEP_BEAUT_CODE	then '41'
								when empdep=@DEP_HAIR_CODE		then '42'
								when empdep=@DEP_FINGER_CODE	then '45' end,isnull(sum(fourthsaleamt),0)                                  
		 from #emp_dep ,#mcardrechargeinfo                                  
		 where inid=fourthsalerinid       and financedate>=datefrom and financedate<dateto   and rechargecompid=compid                             
		 group by rechargecompid,empdep                                       
		                                  
		 
		 --第5负责人充值/还款业绩  
		 
		 insert into #yeji_result(compid,item,yeji)                                  
		 select rechargecompid,case when empdep=@DEP_BEAUT_CODE	then '41'
								when empdep=@DEP_HAIR_CODE		then '42'
								when empdep=@DEP_FINGER_CODE	then '45' end,isnull(sum(fifthsaleamt),0)                                   
		 from #emp_dep ,#mcardrechargeinfo                                  
		 where inid=fifthsalerinid      and financedate>=datefrom and financedate<dateto    and rechargecompid=compid                            
		 group by rechargecompid,empdep                                       
		                           
		 --第6负责人充值/还款业绩                                    
		 insert into #yeji_result(compid,item,yeji)                                  
		 select rechargecompid,case when empdep=@DEP_BEAUT_CODE	then '41'
								when empdep=@DEP_HAIR_CODE		then '42'
								when empdep=@DEP_FINGER_CODE	then '45' end,isnull(sum(sixthsaleamt),0)                                   
		 from #emp_dep ,#mcardrechargeinfo                                  
		 where inid=sixthsalerinid    and financedate>=datefrom and financedate<dateto   and rechargecompid=compid                              
		 group by rechargecompid,empdep                                       
		                                  
		 --第7负责人充值/还款业绩                                   
		 insert into #yeji_result(compid,item,yeji)                                  
		 select rechargecompid,case when empdep=@DEP_BEAUT_CODE	then '41'
								when empdep=@DEP_HAIR_CODE		then '42'
								when empdep=@DEP_FINGER_CODE	then '45' end,isnull(sum(seventhsaleamt),0)                                   
		 from #emp_dep ,#mcardrechargeinfo                                  
		 where inid=seventhsalerinid     and financedate>=datefrom and financedate<dateto   and rechargecompid=compid                             
		 group by rechargecompid ,empdep                                      
		                           
		 --第8负责人充值/还款业绩                                   
		 insert into #yeji_result(compid,item,yeji)                                  
		 select rechargecompid,case when empdep=@DEP_BEAUT_CODE	then '41'
								when empdep=@DEP_HAIR_CODE		then '42'
								when empdep=@DEP_FINGER_CODE	then '45' end,isnull(sum(eighthsaleamt),0)                                   
		 from #emp_dep ,#mcardrechargeinfo                                  
		 where inid=eighthsalerinid      and financedate>=datefrom and financedate<dateto  and rechargecompid=compid                             
		 group by rechargecompid ,empdep    
		 
		  --第9负责人充值/还款业绩                                   
		 insert into #yeji_result(compid,item,yeji)                                  
		 select rechargecompid,case when empdep=@DEP_BEAUT_CODE	then '41'
								when empdep=@DEP_HAIR_CODE		then '42'
								when empdep=@DEP_FINGER_CODE	then '45' end,isnull(sum(ninthsaleamt),0)                                   
		 from #emp_dep ,#mcardrechargeinfo                                  
		 where inid=ninthsalerinid      and financedate>=datefrom and financedate<dateto  and rechargecompid=compid                             
		 group by rechargecompid ,empdep   
		 
		  --第10负责人充值/还款业绩                                   
		 insert into #yeji_result(compid,item,yeji)                                  
		 select rechargecompid,case when empdep=@DEP_BEAUT_CODE	then '41'
								when empdep=@DEP_HAIR_CODE		then '42'
								when empdep=@DEP_FINGER_CODE	then '45' end,isnull(sum(tenthsaleamt),0)                                   
		 from #emp_dep ,#mcardrechargeinfo                                  
		 where inid=tenthsalerinid      and financedate>=datefrom and financedate<dateto  and rechargecompid=compid                             
		 group by rechargecompid ,empdep                                     
		    
		 insert into #yeji_result(compid,item,yeji)                                    
		 select rechargecompid,'43',isnull(sum(payamt),0)                                     
		 from #mcardrechargeinfo ,dpayinfo with(nolock)                    
		 where rechargetype in (0,1,6) and rechargecompid=paycompid and rechargebillid=paybillid and paybilltype='CZ' and paymode in ('1','2','6','14','15','16') and isnull(salebakflag,0)=0 and isnull(backbillid,'')=''                                   
		 group by rechargecompid  
		          
		          
		    
		                         
		 drop table #mcardrechargeinfo 
		 --------------------------------------------计算充值业绩-------End--------------------------------------------------  
		 
    --51.卡异动美容类业绩；  52.卡异动美发类业绩；  53.卡异动总业绩                                  
	CREATE TABLE #mcardchangeinfo               -- 会员卡--异动单                                  
	(                                  
		  changecompid			varchar(10)			NULL,   --公司编号                                  
		  changebillid			varchar(20)			NULL,   --异动单号                                  
		  changetype			int					NULL,   --异动类别(0 折扣转卡 1 收购转卡 2竞争转卡 3换卡 4挂失卡 5补卡 6老卡并老卡 7老卡并新卡 8退卡
		  firstsalerinid		varchar(20)			NULL,   --销售人员                                  
		  firstsaleamt			float				NULL,   --第一销售虚业绩                                   
		  secondsalerinid		varchar(20)			NULL,   --第二销售                                  
		  secondsaleamt			float				NULL,   --第二销售虚业绩                                    
		  thirdsalerinid		varchar(20)			NULL,   --第三负责人                              
		  thirdsaleamt			float				NULL,   --第三销售虚业绩                                   
		  fourthsalerinid		varchar(20)			NULL,   --第4负责人                                  
		  fourthsaleamt			float				NULL,	--第四人员业绩                                  
		  fifthsalerinid		varchar(20)			NULL,   --第5负责人                                  
		  fifthsaleamt			float				NULL,	--第五人员业绩                                  
		  sixthsalerinid		varchar(20)			NULL,   --第6负责人                                  
		  sixthsaleamt			float				NULL,	--第六人员业绩                                  
		  seventhsalerinid		varchar(20)			NULL,   --第7负责人                                  
		  seventhsaleamt		float				null,	--第七人员业绩                                  
		  eighthsalerinid		varchar(20)			NULL,   --第8负责人                                  
		  eighthsaleamt			float				NULL,	--第八人员业绩  
		  ninthsalerinid		varchar(20)			NULL    ,   --第9卖卡人                                  
		  ninthsaleamt			float				NULL    ,	--第9人员业绩  
		  tenthsalerinid		varchar(20)			NULL    ,   --第10卖卡人                                  
		  tenthsaleamt			float				NULL    ,	--第10人员业绩                                
		  financedate			varchar(8)			NULL,   --帐务日期                                    
		  salebakflag			int					NULL                                     
	 )                                  
	 create clustered index index_mcardchangeinfo on #mcardchangeinfo(changecompid,financedate)   
	 
	insert into #mcardchangeinfo(changecompid,changebillid,changetype,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,
			fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,
			eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,financedate,salebakflag)       
	select changecompid,changebillid,changetype,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,
			fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,
			eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,financedate,salebakflag                                  
	from mcardchangeinfo with (nolock)                                 
	where changecompid=@compid and financedate>=@datefrom and financedate<=@dateto and ISNULL(salebakflag,0)=0                                 
      and changetype in (0,1,2,3,5,6,7,8)                       
                                  
	update #mcardchangeinfo set	firstsaleamt = 0-ISNULL(firstsaleamt,0),secondsaleamt = 0-ISNULL(secondsaleamt,0),
								thirdsaleamt = 0-ISNULL(thirdsaleamt,0),fourthsaleamt = 0-ISNULL(fourthsaleamt,0),
								fifthsaleamt = 0-ISNULL(fifthsaleamt,0),sixthsaleamt = 0-ISNULL(sixthsaleamt,0),
								seventhsaleamt = 0-ISNULL(seventhsaleamt,0),eighthsaleamt = 0-ISNULL(eighthsaleamt,0)                                 
	where changetype = 8      
	--------------------------------------------计算卡异动业绩-------Start------------------------------------------------
	                             
		 --第1销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,case		when empdep=@DEP_BEAUT_CODE		then '51'
								when empdep=@DEP_HAIR_CODE		then '52'
								when empdep=@DEP_FINGER_CODE	then '55' end ,isnull(sum(firstsaleamt),0)                                   
		 from #emp_dep ,#mcardchangeinfo                                  
		 where inid=firstsalerinid   and financedate>=datefrom and financedate<dateto    and changecompid=compid                              
		 group by changecompid,empdep                                  
		                                  
		           
		                                  
		 --第2销售                                  
		 insert into #yeji_result(compid,item,yeji)                     
		 select changecompid,case		when empdep=@DEP_BEAUT_CODE		then '51'
								when empdep=@DEP_HAIR_CODE		then '52'
								when empdep=@DEP_FINGER_CODE	then '55' end,isnull(sum(secondsaleamt),0)                                   
		 from #emp_dep ,#mcardchangeinfo                                  
		 where inid=secondsalerinid  and financedate>=datefrom and financedate<dateto      and changecompid=compid                           
		 group by changecompid,empdep                                  
		                                  
		                        
		 --第3销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,case		when empdep=@DEP_BEAUT_CODE		then '51'
								when empdep=@DEP_HAIR_CODE		then '52'
								when empdep=@DEP_FINGER_CODE	then '55' end,isnull(sum(thirdsaleamt),0)                                   
		 from #emp_dep ,#mcardchangeinfo                                  
		 where inid=thirdsalerinid    and financedate>=datefrom and financedate<dateto  and changecompid=compid                             
		 group by changecompid,empdep                                  
		                   
		 --第4销售                                 
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,case		when empdep=@DEP_BEAUT_CODE		then '51'
								when empdep=@DEP_HAIR_CODE		then '52'
								when empdep=@DEP_FINGER_CODE	then '55' end,isnull(sum(fourthsaleamt),0)                                   
		 from #emp_dep ,#mcardchangeinfo                                  
		 where inid=fourthsalerinid   and financedate>=datefrom and financedate<dateto    and changecompid=compid                            
		 group by changecompid,empdep                                  
			                           
		 --第5销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,case		when empdep=@DEP_BEAUT_CODE		then '51'
								when empdep=@DEP_HAIR_CODE		then '52'
								when empdep=@DEP_FINGER_CODE	then '55' end,isnull(sum(fifthsaleamt),0)                                   
		 from #emp_dep ,#mcardchangeinfo                                  
		 where inid=fifthsalerinid   and financedate>=datefrom and financedate<dateto     and changecompid=compid                            
		 group by changecompid,empdep                                  
		                        
		 --第6销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,case   when empdep=@DEP_BEAUT_CODE		then '51'
									when empdep=@DEP_HAIR_CODE		then '52'
									when empdep=@DEP_FINGER_CODE	then '55' end,isnull(sum(sixthsaleamt),0)                                   
		 from #emp_dep ,#mcardchangeinfo                                  
		 where inid=sixthsalerinid   and financedate>=datefrom and financedate<dateto      and changecompid=compid                          
		 group by changecompid ,empdep                                 
		        
		 --第7销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,case		when empdep=@DEP_BEAUT_CODE		then '51'
								when empdep=@DEP_HAIR_CODE		then '52'
								when empdep=@DEP_FINGER_CODE	then '55' end,isnull(sum(seventhsaleamt),0)                                   
		 from #emp_dep ,#mcardchangeinfo                                  
		 where inid=seventhsalerinid    and financedate>=datefrom and financedate<dateto      and changecompid=compid                          
		 group by changecompid ,empdep                                 
		                                
		 --第8销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,case		when empdep=@DEP_BEAUT_CODE		then '51'
								when empdep=@DEP_HAIR_CODE		then '52'
								when empdep=@DEP_FINGER_CODE	then '55' end,isnull(sum(eighthsaleamt),0)                                   
		 from #emp_dep ,#mcardchangeinfo                                  
		 where inid=eighthsalerinid   and financedate>=datefrom and financedate<dateto  and changecompid=compid                            
		 group by changecompid,empdep     
		 
		  --第9销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,case		when empdep=@DEP_BEAUT_CODE		then '51'
								when empdep=@DEP_HAIR_CODE		then '52'
								when empdep=@DEP_FINGER_CODE	then '55' end,isnull(sum(ninthsaleamt),0)                                   
		 from #emp_dep ,#mcardchangeinfo                                  
		 where inid=ninthsalerinid   and financedate>=datefrom and financedate<dateto  and changecompid=compid                            
		 group by changecompid,empdep  
		 
		  --第10销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,case		when empdep=@DEP_BEAUT_CODE		then '51'
								when empdep=@DEP_HAIR_CODE		then '52'
								when empdep=@DEP_FINGER_CODE	then '55' end,isnull(sum(tenthsaleamt),0)                                   
		 from #emp_dep ,#mcardchangeinfo                                  
		 where inid=tenthsalerinid   and financedate>=datefrom and financedate<dateto  and changecompid=compid                            
		 group by changecompid,empdep                               
		                      
		 ----------                               
		 insert into #yeji_result(compid,item,yeji)                                    
		 select changecompid,'53',isnull(sum(case when changetype=8 then 0-payamt else payamt end),0)                                     
		 from #mcardchangeinfo,dpayinfo with(nolock)                                   
		 where isnull(salebakflag,0)<>1 and changecompid=paycompid and changebillid=paybillid and paybilltype in ('ZK','TK') and paymode in ('1','2','6','14','15','16')                                   
		 group by changecompid                                 
		                                  
		 drop table #mcardchangeinfo                                   
                     
	--------------------------------------------计算卡异动业绩-------End-------------------------------------------------- 
	--疗程兑换
	create table #mproexchangeinfo(                    
		changecompid			varchar(10)				NULL,			--公司别                    
		changebillid			varchar(20)				NULL,			--单据编号                  
		changeproid				varchar(20)				NULL,			--项目编号                    
		changedate				varchar(10)				NULL,           --财务日期   
		changebycashamt			float					NULL,			--充值金额（现金金额）    
		firstsalerinid			varchar(20)				NULL,			--第一销售内部编号
		firstsaleamt			float					NULL,			--第一销售分享金额
		secondsalerinid			varchar(20)				NULL,			--第二销售内部编号
		secondsaleamt			float					NULL,			--第二销售分享金额
		thirdsalerinid			varchar(20)				NULL,			--第三销售内部编号
		thirdsaleamt			float					NULL,			--第三销售分享金额
		fourthsalerinid			varchar(20)				NULL,			--第四销售内部编号
		fourthsaleamt			float					NULL,			--第四销售分享金额
	)      
	insert #mproexchangeinfo( changecompid,changebillid,changeproid,changedate,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,
			fourthsalerinid,fourthsaleamt,changebycashamt)                    
	select a.changecompid,a.changebillid,changeproid,changedate,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,
			fourthsalerinid,fourthsaleamt,changebycashamt                    
	from mproexchangeinfo a WITH (NOLOCK) , dproexchangeinfo b WITH (NOLOCK)                     
	where a.changecompid=b.changecompid and a.changebillid=b.changebillid and 
	 a.changecompid=@compid and changedate>=@datefrom and changedate<=@dateto 
	 and ISNULL(salebakflag,0)=0    
    --------------------------------------------计算疗程兑换业绩-------Start------------------------------------------------
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,'61',isnull(sum(changebycashamt),0)                                  
		 from #mproexchangeinfo,#prj_cls                                  
		 where changecompid=compid                                   
		   and changeproid=prjid                                   
		   and prjcls=@PRJ_BEAUT_CLASS_CODE                                                
		 group by changecompid                                
		                                 
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,'62',isnull(sum(changebycashamt),0)                                  
		 from #mproexchangeinfo,#prj_cls                                  
		 where changecompid=compid                                   
		   and changeproid=prjid                                   
		   and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @DEP_FOOT_CODE)                 
		 group by changecompid                       
		                          
		
		 insert into #yeji_result(compid,item,yeji)                                    
		 select changecompid,'63',isnull(sum(changebycashamt),0)                                     
		 from #mproexchangeinfo                                        
		 group by changecompid                         
		                     
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,'64',isnull(sum(firstsaleamt),0)                                   
		 from #emp_dep ,#mproexchangeinfo                                  
		 where inid=firstsalerinid and empdep=@DEP_FOOT_CODE  and changedate>=datefrom and changedate<dateto  and changecompid=compid                               
		 group by changecompid                                  
		            
		
		                      
		  insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,'64',isnull(sum(secondsaleamt),0)                                   
		 from #emp_dep ,#mproexchangeinfo                                  
		 where inid=secondsalerinid and empdep=@DEP_FOOT_CODE  and changedate>=datefrom and changedate<dateto    and changecompid=compid                                   
		 group by changecompid                        
		                    
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,'64',isnull(sum(thirdsaleamt),0)                                   
		 from #emp_dep ,#mproexchangeinfo                                  
		 where inid=thirdsalerinid and empdep=@DEP_FOOT_CODE  and changedate>=datefrom and changedate<dateto    and changecompid=compid                                   
		 group by changecompid                     
		                                
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,'64',isnull(sum(fourthsaleamt),0)                                   
		 from #emp_dep ,#mproexchangeinfo                                  
		 where inid=fourthsalerinid and empdep=@DEP_FOOT_CODE  and changedate>=datefrom and changedate<dateto     and changecompid=compid                                  
		 group by changecompid                      
		                     
		                            
		 insert into #yeji_result(compid,item,yeji)                                  
		 select changecompid,'65',isnull(sum(changebycashamt),0)                                  
		 from #mproexchangeinfo,#prj_cls                                  
		 where changecompid=compid                                   
		   and changeproid=prjid                                   
		   and prjcls=@PRJ_FINGER_CLASS_CODE                                  
		 group by changecompid                        
		                     
		                     
		drop table #mproexchangeinfo  
    --------------------------------------------计算疗程兑换业绩-------End-------------------------------------------------- 
    --销售条码卡
     create table #msalebarcodecardinfo                    
	(                    
		 salecompid				varchar(10)				NULL,--公司编号   
		 salebillid				varchar(20)				NULL,--转卡单                    
		 barcodecardno			varchar(20)				NULL,--转卡单                    
		 saledate				varchar(10)				NULL,--操作 
		 saleamt				float				null	,--销售总额              
		 firstsalerinid			varchar(20)				NULL,			--第一销售内部编号
		 firstsaleamt			float					NULL,			--第一销售分享金额
		 secondsalerinid		varchar(20)				NULL,			--第二销售内部编号
		 secondsaleamt			float					NULL,			--第二销售分享金额
		 thirdsalerinid			varchar(20)				NULL,			--第三销售内部编号
		 thirdsaleamt			float					NULL,			--第三销售分享金额                   
	) 
	insert #msalebarcodecardinfo(salecompid,salebillid,saledate,saleamt,barcodecardno,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt)                    
    select salecompid,salebillid,saledate,saleamt,barcodecardno,firstsaleempinid,firstsaleamt,secondsaleempinid,secondsaleamt,thirdsaleempinid,thirdsaleamt
	  from msalebarcodecardinfo WITH (NOLOCK)                                        
	 where saledate  between @datefrom and @dateto and salecompid= @compid    
	         
	          
    --------------------------------------------计算条码卡销售业绩-------Start--------------------------------------------------        
			 --第1销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case     when empdep=@DEP_BEAUT_CODE		then '71'
									when empdep=@DEP_HAIR_CODE		then '72'
									when empdep=@DEP_FINGER_CODE	then '75' end,isnull(sum(firstsaleamt),0)                                   
		 from #emp_dep ,#msalebarcodecardinfo                                  
		 where inid=firstsalerinid   and saledate>=datefrom and saledate<dateto     and salecompid=compid                                 
		 group by salecompid ,empdep                                 
		                        
		                    
		  --第2销售              
		  insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case     when empdep=@DEP_BEAUT_CODE		then '71'
									when empdep=@DEP_HAIR_CODE		then '72'
									when empdep=@DEP_FINGER_CODE	then '75' end,isnull(sum(secondsaleamt),0)                                   
		 from #emp_dep ,#msalebarcodecardinfo                                  
		 where inid=secondsalerinid  and saledate>=datefrom and saledate<dateto     and salecompid=compid                                 
		 group by salecompid ,empdep 
		 
	
		   --第3销售              
		  insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case     when empdep=@DEP_BEAUT_CODE		then '71'
									when empdep=@DEP_HAIR_CODE		then '72'
									when empdep=@DEP_FINGER_CODE	then '75' end,isnull(sum(thirdsaleamt),0)                                   
		 from #emp_dep ,#msalebarcodecardinfo                                  
		 where inid=thirdsalerinid    and saledate>=datefrom and saledate<dateto     and salecompid=compid                                 
		 group by salecompid ,empdep
		  
  
		 ----------                                  
		 insert into #yeji_result(compid,item,yeji)                                    
		 select salecompid,'73',isnull(sum(isnull(saleamt,0)),0)                                     
		 from #msalebarcodecardinfo                                        
		 group by salecompid           
		                    
		drop table #msalebarcodecardinfo   
    --------------------------------------------计算条码卡销售业绩-------End--------------------------------------------------  
    --合作项目
        CREATE TABLE #mcooperatesaleinfo               -- 会员卡--异动单                                  
		(                                  
		  salecompid			varchar(10)			NULL	,   --公司编号         
		  salebillid			varchar(20)			NULL    ,   --单据编号        
		  saledate				varchar(10)			NULL    ,   --销售日期
		 
		  firstsalerinid		varchar(20)			NULL	,   --第一销售内部编号      
		  firstsaleamt			float				NULL	,	--第一销售虚业绩                                  
		  secondsalerinid		varchar(20)			NULL    ,   --第二卖卡人                                  
		  secondsaleamt			float				NULL    ,	--第二销售虚业绩                                   
		  thirdsalerinid		varchar(20)			NULL    ,   --第三卖卡人                                  
		  thirdsaleamt			float				NULL    ,	--第三销售虚业绩                                   
		  fourthsalerinid		varchar(20)			NULL    ,   --第4卖卡人                                  
		  fourthsaleamt			float				NULL    ,	--第四人员业绩                                  
		  fifthsalerinid		varchar(20)			NULL    ,   --第5卖卡人                                  
		  fifthsaleamt			float				NULL    ,	--第五人员业绩                                  
		  sixthsalerinid		varchar(20)			NULL    ,   --第6卖卡人                                  
		  sixthsaleamt			float				NULL    ,	--第六人员业绩                                  
		  seventhsalerinid		varchar(20)			NULL    ,   --第7卖卡人                                  
		  seventhsaleamt		float				NULL    ,	--第七人员业绩                                  
		  eighthsalerinid		varchar(20)			NULL    ,   --第8卖卡人                                  
		  eighthsaleamt			float				NULL    ,	--第八人员业绩        
		 )                                  
		 create clustered index index_mcooperatesaleinfo on #mcooperatesaleinfo(salecompid,saledate) 
	
		
		 insert #mcooperatesaleinfo(salecompid,salebillid,saledate,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,
			fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,
			eighthsalerinid,eighthsaleamt)
		 select salecompid,salebillid,saledate,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,
			fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,
			eighthsalerinid,eighthsaleamt
		 from   mcooperatesaleinfo  where salecompid=@compid and saledate  between @datefrom and @dateto
		 and ISNULL(salebillflag,0)=2 and ISNULL(slaepaymode,1)=1
		  
		  
		  
	    --------------------------------------------计算合作项目销售业绩-------Start-------------------------------------------------- 
	     --第1销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case     when empdep=@DEP_BEAUT_CODE		then 'A1'
									when empdep=@DEP_HAIR_CODE		then 'A2'
									when empdep=@DEP_FINGER_CODE	then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(firstsaleamt,0)),0))                                   
		 from #emp_dep ,#mcooperatesaleinfo                                  
		 where inid=firstsalerinid   and saledate>=datefrom and saledate<dateto    and salecompid=compid                              
		 group by salecompid   ,empdep                               
		                                  
		 
		        
		 --第2销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case     when empdep=@DEP_BEAUT_CODE		then 'A1'
									when empdep=@DEP_HAIR_CODE		then 'A2'
									when empdep=@DEP_FINGER_CODE	then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(secondsaleamt,0)),0))                          
		 from #emp_dep ,#mcooperatesaleinfo                                  
		 where empid=secondsalerinid and empdep=@DEP_BEAUT_CODE   and saledate>=datefrom and saledate<dateto    and salecompid=compid                              
		 group by salecompid  ,empdep                                
		 
		  --第3销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case     when empdep=@DEP_BEAUT_CODE		then 'A1'
									when empdep=@DEP_HAIR_CODE		then 'A2'
									when empdep=@DEP_FINGER_CODE	then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(thirdsaleamt,0)),0))             
		 from #emp_dep ,#mcooperatesaleinfo                                  
		 where empid=thirdsalerinid and empdep=@DEP_BEAUT_CODE   and saledate>=datefrom and saledate<dateto    and salecompid=compid                              
		 group by salecompid,empdep                                  
		     
		         
		  --第4销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case     when empdep=@DEP_BEAUT_CODE		then 'A1'
									when empdep=@DEP_HAIR_CODE		then 'A2'
									when empdep=@DEP_FINGER_CODE	then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(fourthsaleamt,0)),0))                                       
		 from #emp_dep ,#mcooperatesaleinfo                                  
		 where empid=fourthsalerinid and empdep=@DEP_BEAUT_CODE   and saledate>=datefrom and saledate<dateto    and salecompid=compid                              
		 group by salecompid ,empdep                                 
		       
		         
		  --第5销售                                  
		 insert into #yeji_result(compid,item,yeji)          
		 select salecompid,case     when empdep=@DEP_BEAUT_CODE		then 'A1'
									when empdep=@DEP_HAIR_CODE		then 'A2'
									when empdep=@DEP_FINGER_CODE	then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(fifthsaleamt,0)),0))                                       
		 from #emp_dep ,#mcooperatesaleinfo                                  
		 where empid=fifthsalerinid and empdep=@DEP_BEAUT_CODE   and saledate>=datefrom and saledate<dateto    and salecompid=compid                              
		 group by salecompid  ,empdep                               
		             
		                           
		 --第6销售                                  
		 insert into #yeji_result(compid,item,yeji)                                  
		 select salecompid,case     when empdep=@DEP_BEAUT_CODE		then 'A1'
									when empdep=@DEP_HAIR_CODE		then 'A2'
									when empdep=@DEP_FINGER_CODE	then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(sixthsaleamt,0)),0))                                        
		 from #emp_dep ,#mcooperatesaleinfo                                  
		 where empid=sixthsalerinid and empdep=@DEP_BEAUT_CODE   and saledate>=datefrom and saledate<dateto    and salecompid=compid                              
		 group by salecompid ,empdep                                 
		                 
		                                   
		 insert into #yeji_result(compid,item,yeji)    
		 select salecompid,'A3',isnull(sum(payamt),0)                                     
		 from #mcooperatesaleinfo,dpayinfo with(nolock)                                   
		 where  salecompid=paycompid and salebillid=paybillid and paybilltype='HZ' and paymode in ('1','2','6','14','15','16')                                   
		 group by salecompid     
		              
		 drop table #mcooperatesaleinfo        
			
		--------------------------------------------计算合作项目销售业绩-------End--------------------------------------------------
	--------------------------------------------计算分类业绩-------Start------------------------------------------------
		create table #cls_yeji_resultx(                              
		  compid varchar(10) not null,                              
		  beaut_yeji float null,                              
		  hair_yeji float null,                              
		  foot_yeji float null,                              
		  finger_yeji float null,                              
		  total_yeji float null,                              
		  real_beaut_yeji float null,                              
		  real_hair_yeji float null,                              
		  real_foot_yeji float null,                              
		  real_finger_yeji float null,                              
		  real_total_yeji  float null                              
		 )                              
		                              
		 create table #sum_yeji(compid varchar(10) not null,flag int null, yeji float null)                              
		 insert into #sum_yeji(compid,flag,yeji)                              
		 select compid,1,isnull(sum(yeji),0)                               
		 from #yeji_result                               
		 where item in ('11','21','31','41','51','61','71','A1')                  
		 group by compid                              
		                               
		 insert into #sum_yeji(compid,flag,yeji)                              
		 select compid,2,isnull(sum(yeji),0)                              
		 from #yeji_result                               
		 where item in ('12','22','32','42','52','62','72','A2','24x')                               
		 group by compid                              
		                              
		 insert into #sum_yeji(compid,flag,yeji)                              
		 select compid,3,isnull(sum(yeji),0)                             
		 from #yeji_result                               
		 where item in ('13','23','33','43','53','63','73','A3')                               
		 group by compid                              
		                              
		 insert into #sum_yeji(compid,flag,yeji)                              
		 select compid,4,isnull(sum(yeji),0)                               
		 from #yeji_result                               
		 where item in ('14','24','34','44','54','64','74','A4')                   
		 group by compid                              
		                              
		 insert into #sum_yeji(compid,flag,yeji)                              
		 select compid,5,isnull(sum(yeji),0)                               
		 from #yeji_result                               
		 where item in ('15','25','35','45','55','65','75','A5')                               
		 group by compid                              
		                              
		 insert into #sum_yeji(compid,flag,yeji)                              
		 select compid,6,isnull(sum(yeji),0)                               
		 from #yeji_result                               
		 where item in ('81','91')                               
		 group by compid                              
		                              
		 insert into #sum_yeji(compid,flag,yeji)               
		 select compid,7,isnull(sum(yeji),0)                               
		 from #yeji_result                               
		 where item in ('82','92','94')                               
		 group by compid                              
		                               
		 insert into #sum_yeji(compid,flag,yeji)                              
		 select compid,8,isnull(sum(yeji),0)                               
		 from #yeji_result                               
		 where item   in ('84','94')                           
		 group by compid                              
		                             
		 insert into #sum_yeji(compid,flag,yeji)                              
		 select compid,9,isnull(sum(yeji),0)                               
		 from #yeji_result                               
		 where item  in ('85','95')                              
		 group by compid                              
		                         
		 insert into #sum_yeji(compid,flag,yeji)                              
		 select compid,10,isnull(sum(yeji),0)                               
		 from #yeji_result                               
		 where item in ('83','93')                               
		 group by compid                              
		                               
		 insert into #cls_yeji_resultx(compid,beaut_yeji,hair_yeji,total_yeji)                              
		 select @compid,0,0,0                             
		                              
		 update a                               
		 set beaut_yeji = isnull(yeji,0)                               
		 from #cls_yeji_resultx a,#sum_yeji b                              
		 where b.flag=1                              
		   and a.compid=b.compid                              
		                               
		 update a                               
		 set hair_yeji = isnull(yeji,0)                               
		 from #cls_yeji_resultx a,#sum_yeji b                              
		 where b.flag=2                              
		   and a.compid=b.compid                              
		                              
		 update a                               
		 set total_yeji = isnull(yeji,0)                               
		 from #cls_yeji_resultx a,#sum_yeji b                              
		 where b.flag=3                              
		   and a.compid=b.compid                 
		                              
		 update a                               
		 set foot_yeji = isnull(yeji,0)                               
		 from #cls_yeji_resultx a,#sum_yeji b                        
		 where b.flag=4                              
		   and a.compid=b.compid                              
		                               
		 update a     
		set finger_yeji = isnull(yeji,0)                               
		 from #cls_yeji_resultx a,#sum_yeji b                              
		 where b.flag=5                              
		   and a.compid=b.compid                              
		----------                              
		 update a                               
		 set real_beaut_yeji = isnull(yeji,0)                               
		 from #cls_yeji_resultx a,#sum_yeji b                              
		 where b.flag=6                              
		   and a.compid=b.compid                              
		                               
		 update a                               
		 set real_hair_yeji = isnull(yeji,0)                               
		 from #cls_yeji_resultx a,#sum_yeji b                              
		 where b.flag=7                              
		   and a.compid=b.compid                              
		                              
		 update a                               
		 set real_total_yeji = isnull(yeji,0)                               
		 from #cls_yeji_resultx a,#sum_yeji b                              
		 where b.flag=10                              
		   and a.compid=b.compid                              
		                              
		 update a                               
		 set real_foot_yeji = isnull(yeji,0)                               
		 from #cls_yeji_resultx a,#sum_yeji b                              
		 where b.flag=8                              
		   and a.compid=b.compid                              
		                               
		 update a                               
		 set real_finger_yeji = isnull(yeji,0)                               
		 from #cls_yeji_resultx a,#sum_yeji b                              
		 where b.flag=9                              
		   and a.compid=b.compid                              
		                               
	delete #cls_yeji_resultx where ISNULL(total_yeji,0)=0  and isnull(foot_yeji,0)=0
	
	if(@usetype=1)
	begin
		select compid,@datefrom,beaut_yeji,hair_yeji,foot_yeji,finger_yeji,total_yeji,                              
		  real_beaut_yeji,real_hair_yeji,real_foot_yeji,real_finger_yeji,real_total_yeji                              
		from #cls_yeji_resultx                               
		 order by compid asc 
	end    
	else
	begin
		insert #cls_yeji_result_search(compid,ddate,beaut_yeji,hair_yeji,foot_yeji,finger_yeji,total_yeji,                              
		  real_beaut_yeji,real_hair_yeji,real_foot_yeji,real_finger_yeji,real_total_yeji)
		  select compid,@datefrom,beaut_yeji,hair_yeji,foot_yeji,finger_yeji,total_yeji,                              
		  real_beaut_yeji,real_hair_yeji,real_foot_yeji,real_finger_yeji,real_total_yeji                              
		from #cls_yeji_resultx  
	end      
	--------------------------------------------计算分类业绩-------End--------------------------------------------------  

    drop table #cls_yeji_resultx
	drop table #prj_cls
	drop table #yeji_result
	drop table #goods_cls
	drop table #empinfobydate
	drop table #emp_dep
end
GO
/****** Object:  StoredProcedure [dbo].[upg_create_trade_dailydata]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_create_trade_dailydata]
(
	@compid			varchar(10),		--门店号	
	@datefrom		varchar(10),		--起始日期
	@dateto			varchar(10),		--结束日期
	@showtype		int					--显示类型(1 日记账 2 对账表)
)  
as 
begin
	
	CREATE TABLE    #dpayinfo_billcount               --  单据--支付明细
	(
		paycompid		varchar(10)      NULL,   --公司编号
		paydate			varchar(10)      NULL,   --支付日期
		paybilltype		varchar(5)		 NULL,   --单据类别  SY 收银  SK  售卡  CZ 充值 ZK  转卡 HZ 合作项目 TK 退卡
		paymode			varchar(5)		 NULL,   --支付方式
		payamt			float			 NULL,   --支付金额
	)

	exec upg_compute_comp_trade_payinfo @compid,@datefrom,@dateto
	
	if(@showtype=1)
	begin
		create table #tradedailydata   --门店营业临时表
		(
			seqno			int identity(1,1)	NOT	NULL,	--默认编号
			tradeTitle		varchar(50)				NULL,	--营业内容
			tradeAmt		varchar(30)				NULL,	--营业金额
			valueflag		int						NULL	--显示标示
		)
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '门店名称',compname,1 from companyinfo where compno=@compid

		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '总营业额',
		convert(numeric(20,1),SUM(case when  paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
				 when  paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end )) ,2
		from #dpayinfo_billcount where paycompid=@compid 
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '员工购买产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,3
		from #dpayinfo_billcount
		where paycompid=@compid and paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')='SY_G2'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '现金服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),4 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '现金产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),5 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '现金退卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),6 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'')='TK'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '现金卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
											  when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)),7 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '现金条码卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),8
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'')='TMK'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '现金疗程兑换',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),9 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'')='LXDH'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '现金合作项目',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,10
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'')='HZ'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '现金合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)),11 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='1'
		
		-------------------------银行卡-----------------------------------
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '银行卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),12 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '银行卡产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),13 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '银行卡退卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),14 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'')='TK'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '银行卡卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
											  when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)) ,15
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '银行卡条码卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),16
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'')='TMK'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '银行卡疗程兑换',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,17
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'')='LXDH'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '银行卡合作项目',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),18
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'')='HZ'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '银行卡合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)),19 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='6'
		
			-------------------------支票-----------------------------------
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '支票服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),20 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '支票产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,21
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '支票退卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,22
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'')='TK'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '支票卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
											  when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)) ,23
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '支票条码卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),24 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'')='TMK'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '支票疗程兑换',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),25 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'')='LXDH'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '支票合作项目',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),26
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'')='HZ'
		
			
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '支票合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)),27 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='2'
		
		-------------------------指付通-----------------------------------
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '指付通服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,28
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '指付通产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,29
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '指付通退卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,30
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'')='TK'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '指付通卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
											  when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)) ,31
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '指付通条码卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,32
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'')='TMK'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '指付通疗程兑换',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,33
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'')='LXDH'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '指付通合作项目',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,34
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'')='HZ'
		
			
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '指付通合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)) ,35
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='14'
		
		-------------------------OK卡-----------------------------------
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select 'OK卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,36
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='15' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select 'OK卡产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,37
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='15' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')
		
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select 'OK卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
											  when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)) ,38
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='15' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select 'OK卡条码卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),39 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='15' and  isnull(paybilltype,'')='TMK'
		

		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select 'OK卡合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)) ,40
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='15'
		
		-------------------------团购卡-----------------------------------
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '团购卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),41
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='16' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '团购卡产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,42
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='16' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')
		
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '团购卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
											  when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)),43 
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='16' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')
		


		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '团购卡合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)) ,44
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='16'
		
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '卡异动合计',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
											  when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)),45
		from #dpayinfo_billcount
		where paycompid=@compid and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')
		
		------------------------------------销卡---------------------------------------
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '销卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,46
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='4' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '疗程服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,47
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='9' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '收购卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,48
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='A' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '销卡产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,49
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='4' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '积分账户服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,50
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='7' and  isnull(paybilltype,'')='SY_P'
		
		
		
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '现金抵用券服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,51
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='12' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '项目抵用券服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,52
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='11' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '条码卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0) ),53
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='13' and  isnull(paybilltype,'')='SY_P'
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '支付登记',0,54
		
		insert #tradedailydata(tradeTitle,tradeAmt,valueflag)
		select '经理签单',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,55
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='8' 
	
		select tradeTitle,tradeAmt,valueflag from #tradedailydata
		drop table #tradedailydata
	end
	else
	begin
		create table #tradedatedata   --门店新建对账
		(
			tradedate			varchar(50)					NULL,	--营业日期
			totalcashamt		float					NULL,	--现金合计
			staffcashamt		float					NULL,	--员工现金产品
			hezuocashamt		float					NULL	--员工现金产品
		)
		insert #tradedatedata(tradedate,totalcashamt,staffcashamt,hezuocashamt)
			select paydate,isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0) ,
						   isnull(sum(case when  isnull(paybilltype,'')='SP_G2' then isnull(payamt,0) else 0 end),0),
						   isnull(sum(case when  isnull(paybilltype,'')='HZ' then isnull(payamt,0) else 0 end),0)
		from #dpayinfo_billcount
		where paycompid=@compid and paymode ='1' 
		group by paydate
		
		insert #tradedatedata(tradedate,totalcashamt,staffcashamt,hezuocashamt)
		select '',sum(isnull(totalcashamt,0)),sum(isnull(staffcashamt,0)),sum(isnull(hezuocashamt,0))
		from #tradedatedata
		
		select tradedate,totalcashamt,staffcashamt,hezuocashamt from #tradedatedata
		drop table #tradedatedata
	end
	drop table #dpayinfo_billcount
end
GO
/****** Object:  StoredProcedure [dbo].[upg_diarialBill_byday_analysis]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_diarialBill_byday_analysis] 
(             
  @compid  varchar(10),   -- 已经能支持各级别的公司              
  @fromdate varchar(10),  --开始日期              
  @todate   varchar(10)  --结束日期      
)            
as              
begin                      
	CREATE TABLE    #dpayinfo_billcount             --  单据--支付明细
	(
		paycompid		varchar(10)      NULL,   --公司编号
		paydate			varchar(10)      NULL,   --支付日期
		paybilltype		varchar(5)		 NULL,   --单据类别  SY 收银  SK  售卡  CZ 充值 ZK  转卡 HZ 合作项目 TK 退卡
		paymode			varchar(5)		 NULL,   --支付方式
		payamt			float			 NULL,   --支付金额
	)

	exec upg_compute_comp_trade_payinfo @compid,@fromdate,@todate
    
    
    select  paycompid,paydate,paybilltype,paymode,payamt=sum(isnull(payamt,0)) 
    from #dpayinfo_billcount group by paycompid,paydate,paybilltype,paymode
    drop table #dpayinfo_billcount
end
GO
/****** Object:  StoredProcedure [dbo].[upg_personal_comm_paymodeTest]    Script Date: 01/14/2014 15:45:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_personal_comm_paymodeTest](                                
	@compid				varchar(10),	-- 公司别                                
	@fromdate			varchar(10),	-- 开始日期                                
	@todate				varchar(10),	-- 截至日期
	@fromempinno		varchar(20),	-- 查询开始人员内部编号         
	@toempinno			varchar(20)		-- 查询截至人员内部编号 
)                                                                   
as                                
begin                              
	create table #work_detail
	(
		seqno			int	identity		not null,       
		person_inid		varchar(20)			NULL, --员工内部编号
		action_id		int				NULL, --单据类型
		srvdate			varchar(10)			NULL, --日期
		code			varchar(20)			NULL, --项目代码,或是卡号,产品码
		name			varchar(40)			NULL, --名称
		payway			varchar(20)			NULL, --支付方式
		billamt			float				NULL, --营业金额
		ccount			float				NULL, --数量
		cost			float				NULL, --成本
		staffticheng	float				NULL, --提成
		staffyeji		float				NULL, --虚业绩
		prj_type		varchar(20)			NULL, --项目类别
		cls_flag        int					NULL, -- 1:项目 2:产品 3:卡
		billid			varchar(20)			NULL, --单号
		paycode			varchar(20)			NULL, --支付代码
		compid			varchar(10)			NULL, --公司别
		cardid			varchar(20)			NULL, --会员卡号
		cardtype		varchar(20)			NULL, --会员卡类型
		postation		varchar(10)			NULL, --职位
	)     
	exec upg_prepare_yeji_detail @compid,@fromdate, @todate,@fromempinno, @toempinno  
  
	update #work_detail set payway=parentcodevalue 
	from #work_detail,commoninfo
	where infotype='ZFFS' and parentcodekey=paycode
	
	
	update #work_detail set cardtype='' where cardtype='ZK' and paycode<>'4'
	
	create table #empinfobydate(                                
		seqno		int	identity		not null,                    
		compid		varchar(10)			null,                        
		inid		varchar(20)			null,                                
		empid		varchar(20)			null,                                
		datefrom	varchar(8)			null,                                
		dateto		varchar(8)			null,                                
		position	varchar(10)			null,                                
		salary		float				null,                                
		sharetype	varchar(5)			null,                                
		sharerate	float				null,                                
		deducttax	int					null,                                 
	 )                       
                   
    insert into #empinfobydate(compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                           
	exec upg_get_empinfo_by_date_comps @compid,@fromdate,@todate  
    --门店模式
	declare @comptypebyfinger varchar(5)                  
	select  @comptypebyfinger=ISNULL(compmode,'1') from companyinfo where compno=@compid   
	                                  
	 declare @empPostion varchar(10)                                              
	 declare @tmpSeqId int                                                        
	 declare @tmpEmpId varchar(20)                                                        
	 declare @tmpItem varchar(10)                                                        
	 declare @tmpYeji float                                                        
	 declare @tmpPrjId varchar(20)                                                        
	 declare @tmpDate varchar(8)                                                        
	 declare @paycode varchar(10)                      
	 declare @emp_total_yeji float                                       
	                                       
	 declare @GOODS_TYPE varchar(5)                                      
	 declare @PROJECT_COST float                                    
	 declare @Performance_Ratio float                                    
	 declare @Wage_Rates float                                    
	 declare @CARD_SALE_RATE float                                     
	 set @CARD_SALE_RATE=0.06                                    
	 declare @PROJECT_TYPE varchar(5)                                    
	 declare @GOODS_SALE_RATE_buty float                                    
	 declare @GOODS_SALE_RATE_hair float                                  
	 declare @GOODS_SALE_RATE_finger float --美发售产品提成比率                                       
	                                     
	 set  @GOODS_SALE_RATE_buty=0.1                                           
	 set  @GOODS_SALE_RATE_hair=0.05                          
	 set  @GOODS_SALE_RATE_finger=0.6                                  
	 declare @cardtype  varchar(20)  --会员卡类别                                    
	 declare @quan float                                
	 declare @fuflag float --正负单据                                
	 declare @businessflag int --是否为业务人员 0--不是 1--是                                 
	 declare @empinid varchar(20)                            
	 declare @proflag int --项目类别                                               
	 declare cur_yeji_ticheng cursor for                                                         
	 select seqno,person_inid,action_id,staffyeji,code,srvdate ,paycode,isnull(cardtype,''),isnull(ccount,0)                                        
	 from #work_detail                                                        
	 declare @empTicheng float     
	                                                        
	 open cur_yeji_ticheng                                                        
	 fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate,@paycode ,@cardtype,@quan                                                          
	 while(@@fetch_status=0)                    
	 begin          
			select 1111111                           
		set @empTicheng = 0   
		--更新员工的最新职位      
		select @empPostion=position ,@empinid=inid from #empinfobydate where inid=@tmpEmpId and @tmpDate>=datefrom and @tmpDate<dateto                     
	    -- 查看员工是否是业务人员   
	    select @businessflag=ISNULL(businessflag,0)  from staffinfo with(nolock)  where manageno=@tmpEmpId                             
	    -- 如果是非业务人员提成为0
	    if(@businessflag=0 or @empPostion in ('001','00101','00102','00103','00104','002','010','011','01101','01102','01201','01202','803'))                                      
		begin                                
			update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                   
			fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan                                
			continue                                   
		end
		-- 16，17，18，19，20，21，22，23，24-售产品  
		if(isnull(@tmpItem,'') in ('16','17','18','19','20','21','22','23','24'))                                                
		begin      
	
				select   @GOODS_TYPE=isnull(goodstype,1)  from  goodsnameinfo with(nolock) where goodsno=@tmpPrjId                                      
			if(isnull(@GOODS_TYPE,'300'))='400'----美容产品(扣除成本20%提成10%)                                      
			begin                                      
				update #work_detail set staffyeji=isnull(staffyeji,0)*0.8 where seqno=@tmpSeqId                                       
				set @empTicheng = isnull(@tmpYeji,0)*0.8 *@GOODS_SALE_RATE_buty                                       
			end                                      
			else if(isnull(@GOODS_TYPE,'300'))='300'----美发产品（提成5%）                                       
			begin                                      
				update #work_detail set staffyeji=isnull(staffyeji,0) where seqno=@tmpSeqId                                       
				set @empTicheng = isnull(@tmpYeji,0)*@GOODS_SALE_RATE_hair                                         
			end                                     
			else if(isnull(@GOODS_TYPE,'300'))='500'----美甲产品（产品不扣成本 4，6分）                                     
			begin                                         
				set @empTicheng = isnull(@tmpYeji,0)*@GOODS_SALE_RATE_finger--0.4                                      
			end                                  
			else if(isnull(@GOODS_TYPE,'300'))='700'----卡诗产品（无业绩无提成）                                           
			begin                                         
				update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                       
				set @empTicheng = 0                                
			end
                                                               
		end  
		-- 开卡+充值+转卡+条码卡开卡 1，2，3，5
		else if(isnull(@tmpItem,'')='1' or isnull(@tmpItem,'')='2'  or isnull(@tmpItem,'')='3' or isnull(@tmpItem,'')='5')                                     
		begin                                       
			if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师1.5%                                   
				set @empTicheng = @tmpYeji*0.015                                     
			else if (isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2%                                   
				set @empTicheng = @tmpYeji*0.02                                      
			else    -- 其他职位都是6%               
				set @empTicheng = @tmpYeji*@CARD_SALE_RATE                                              
		end   
		-- 合作项目销售  26，27，28，29，30，31                                     
	    else if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27'                        
			or  isnull(@tmpItem,'')='28' or isnull(@tmpItem,'')='29'                        
			or  isnull(@tmpItem,'')='30' or isnull(@tmpItem,'')='31' )                                   
		begin                                       
			if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27' or isnull(@tmpItem,'')='30' )                        
				set @empTicheng = @tmpYeji*0.06                           
			else if (isnull(@tmpItem,'')='28')                        
				set @empTicheng = @tmpYeji*0.3                        
		end  
		--疗程兑换  4
		else if(isnull(@tmpItem,'')='4')                               
		begin                                
			if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师2%                                   
				set @empTicheng = @tmpYeji*0.02                                     
			else if ( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2.5%                                   
				set @empTicheng = @tmpYeji*0.025                                    
			--首席，总监，设计师记业绩20%,提成20%                                 
			else if ( isnull(@empPostion,'')='003' or isnull(@empPostion,'')='006' or isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00102')                                  
			begin                                
				update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.2 where seqno=@tmpSeqId                                  
				set @empTicheng = isnull(@tmpYeji,0)*0.2*0.2                
			end                                
		end   
		--项目消费 7,8,9,10,11,12,13,14,15
		else
		begin
			set @fuflag=@quan   
			if(isnull(@paycode,'')='9') --疗程                                                  
			begin                                                    
				--疗程消费美容师和美发师按照设定成本和业绩比率走                                
				select @proflag=isnull(prjpricetype,2),@PROJECT_COST=isnull(costprice,0),@Performance_Ratio=isnull(lyjrate,1),@Wage_Rates=isnull(ltcrate,1),@PROJECT_TYPE=prjtype  
				from  projectinfo ,compchaininfo 
				where prisource=curcomp and relationcomp=@compid and prjno=@tmpPrjId                                 
				
				update #work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                       
				set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                      
	                                       
				--烫染师 非疗程卡 记80%业绩 记5%提成 纯疗程卡记120块的业绩，6块钱的提成                                
				if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )                                    
				begin      
					if( isnull(@cardtype,'') ='MR' or isnull(@cardtype,'')='MF')                                    
					begin                                
						update #work_detail set staffyeji=120*@fuflag where seqno=@tmpSeqId                                       
						set @empTicheng=6*@fuflag                                
					end                                 
					else                                
					begin                                     
						update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.8 where seqno=@tmpSeqId                         
						set @empTicheng = isnull(@tmpYeji,0)*0.8*0.05                                
						if(isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')--四级烫染师                                
							set @empTicheng = isnull(@tmpYeji,0)*0.8*0.06                                
					end                                   
				end                                                      
				if(isnull(@empPostion,'')in('004','00401','00402') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                
				begin                                
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                
				if(isnull(@empPostion,'') in ('003','006','007','00901','00902','00903','00904','008')  and ISNULL(@PROJECT_TYPE,'') ='4' and ISNULL(@proflag,2)=1)                                
				begin                                
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                 
				--美发的输入在中工和小工位置的(美发师,首席,总监)无提成                                
				if((@PROJECT_TYPE='3' or @PROJECT_TYPE='6') and isnull(@empPostion,'') in ('003','006','007') and isnull(@tmpItem,'') not in ('7','8','9'))                                
				begin                                
					set @empTicheng = 0                                 
				end                                
				if(@cardtype ='MFOLD' )                                 
				begin                                
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng =0                                
				end                                                   
			end 
			else if( (@cardtype ='ZK' and isnull(@paycode,'')='4') or isnull(@paycode,'')='$'  or isnull(@paycode,'')='A' or isnull(@paycode,'')='7' or isnull(@paycode,'')='11'  or isnull(@paycode,'')='12'  )               
			begin                             
					if(isnull(@tmpYeji,0)>0)                                
						set @fuflag=1                                
					else                                
						set @fuflag=-1                                
					--项目抵用券使用面值做业绩                                
					if(isnull(@paycode,'')='11' )                                
					begin      
						select @tmpYeji=ISNULL(cardfaceamt,0) from nointernalcardinfo where cardno=@cardtype                        
						if(ISNULL(@fuflag,0)<0)                      
							set  @tmpYeji=ISNULL(@tmpYeji,0)*(-1)                             
						update #work_detail set staffyeji=@tmpYeji,billamt=@tmpYeji where seqno=@tmpSeqId                                  
					end                                 
					--烫染师 记业绩24% 提成5%                                
					if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                    
					begin                                
						update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
						set @empTicheng = isnull(@tmpYeji,0)*0.24*0.05                                  
						if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')          
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.06                                  
					end                                  
					--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                 
					else                                 
					begin                                
						update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
						set @empTicheng = isnull(@tmpYeji,0)*0.24*0.22                                
					end                                  
					---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                
					---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3   
					                  
					if(@tmpPrjId='300' or @tmpPrjId='3002' or @tmpPrjId='301' or @tmpPrjId='302' or @tmpPrjId='303' or @tmpPrjId='305' or @tmpPrjId='306'  or @tmpPrjId='309'   or @tmpPrjId='311'  )                                
					begin                                
						if(isnull(@empPostion,'')in('004','00401','00402') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                               
						begin                                
							if(@tmpPrjId='300' or @tmpPrjId='302' or @tmpPrjId='303'  or @tmpPrjId='309' )                                
							begin                                
								update #work_detail set staffyeji=20*@fuflag where seqno=@tmpSeqId                                  
								set @empTicheng =6*@fuflag                                
							end                                 
							else                                
							begin                                
								update #work_detail set staffyeji=5*@fuflag where seqno=@tmpSeqId                                  
								set @empTicheng =1.5*@fuflag                                
							end                                
						end                                       
						if((@tmpPrjId='300' or @tmpPrjId='3002') and isnull(@tmpItem,'') not in ('7','8','9'))                                
						begin                                
							if(isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007')--首席总监                                
							begin                                
								update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
								set @empTicheng =3*@fuflag                                
							end                                       
							else if(isnull(@empPostion,'')='003')                                
							begin                                
								update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
								set @empTicheng =2.8*@fuflag                                
							end                                       
							else                                 
							begin                                
								update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
								set @empTicheng =0                                
							end                                
						end                                
					end                         
			end 
			else
			begin
					select 1111111
					select @proflag=isnull(prjpricetype,2),@PROJECT_COST=isnull(costprice,0),@Performance_Ratio=isnull(kyjrate,1),@Wage_Rates=isnull(ktcrate,1),@PROJECT_TYPE=prjtype  
					from  projectinfo ,compchaininfo 
					where prisource=curcomp and relationcomp=@compid  and prjno=@tmpPrjId  
					if(@PROJECT_TYPE<>'5')--美甲成本单独算                  
					begin                  
						update #work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                       
					end                  
					set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                      
					--首席总监 扣除成本后×业绩比率，提成 30%                                
					if((isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007') and ISNULL(@PROJECT_TYPE,'0')<>'6' )                                    
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*0.3                                     
					--烫染师 扣除成本后记业绩5%                                
					if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  )                                    
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0)*0.05                                     
					if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0)*0.06                                     
		                 
					---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                
					---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3                                    
					if(@tmpPrjId='300' or @tmpPrjId='3002' or @tmpPrjId='301' or @tmpPrjId='302' or @tmpPrjId='303' or @tmpPrjId='305' or @tmpPrjId='306'  or @tmpPrjId='309'   or @tmpPrjId='311'  )                                
					begin                                
						if(isnull(@empPostion,'')='003') --美发师                                
						begin                                
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.75 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.75*0.28                                  
						end                                 
						else if(isnull(@empPostion,'')='006') --美发师                                
						begin                                
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.85 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.85*0.3                                  
						end                             
						else if(isnull(@empPostion,'')='007') --美发师                                
						begin                                
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.89 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.89*0.3                                  
						end                                 
						else if(isnull(@empPostion,'')in('004','00401','00402') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                        
						begin                                
							if(@tmpPrjId='300' or @tmpPrjId='302' or @tmpPrjId='303'  or @tmpPrjId='309' )                              begin                                
								update #work_detail set staffyeji=20*@fuflag where seqno=@tmpSeqId                                  
							set @empTicheng =6*@fuflag                                
						end                                 
						else                                
						begin                                
							update #work_detail set staffyeji=5*@fuflag where seqno=@tmpSeqId                                  
							set @empTicheng =1.5*@fuflag                                
						end                                
					end  
					if((@tmpPrjId='300' or @tmpPrjId='3002') and isnull(@tmpItem,'') not in ('7','8','9'))                 
					begin                                      
						if(isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007')--首席总监        
						begin                                
							update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
							set @empTicheng =3*@fuflag                                
						end                                       
						else if(isnull(@empPostion,'')='003')                                
						begin                                
							update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
							set @empTicheng =2.8*@fuflag                                
						end                                       
						else                                 
						begin                                
							update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
							set @empTicheng =0                                
						end                        
		                                       
					end                                
				end                                
				if(isnull(@paycode,'')='13' )                                
				begin                                
					declare @tmcardfrom int --0 正常开卡,1 赠送开卡                                
					select @tmcardfrom=ISNULL(entrytype,0) from nointernalcardinfo where cardno=@cardtype                                
					if(ISNULL(@tmcardfrom,0)=1)                                
					begin                                
						--烫染师 记业绩24% 提成5%                                
						if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                    
						begin                                
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.05                                  
							if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                
								set @empTicheng = isnull(@tmpYeji,0)*0.24*0.06                                  
						end                                  
						--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                 
						else                                 
						begin                                
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.22                                
						end                                 
					end                                 
				end        
				--美发的输入在中工和小工位置的(美发师,首席,总监)无提成                                
				if((@PROJECT_TYPE='3' or @PROJECT_TYPE='6') and isnull(@empPostion,'') in ('003','006','007') and isnull(@tmpItem,'') not in ('7','8','9'))                                
				begin                                
					set @empTicheng = 0                                 
				end                                
				else if(@PROJECT_TYPE='5')-- 美甲业绩扣10%的成本，提成4，6分                                
				begin                                
					if(isnull(@empPostion,'')='005' )                                
					begin                    
						if( ISNULL(@comptypebyfinger,'1')='1')                    
						begin                       
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.9 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.9*0.6                      
						end                              
					end                                
					else                                
					begin                                
						update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
						set @empTicheng =0                                
					end                                
				end  
				                                  
				if(isnull(@empPostion,'') in('004','00401','00402')  and @tmpPrjId in ('48811') and isnull(@paycode,'')<>'13')                                
				begin                                
					if(isnull(@tmpItem,'') in ('7','8','9'))                
					begin       
						if(isnull(@tmpYeji,0)<100)     
							set @tmpYeji=100                      
						update #work_detail set staffyeji= isnull(@tmpYeji,0)*0.8 where seqno=@tmpSeqId           
						set @empTicheng = isnull(@tmpYeji,0)*0.8*0.3*@fuflag                
					end                           
					else                                
					begin                                
						update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
						set @empTicheng = 0               
					end                                
				end                               
				if(isnull(@empPostion,'')in('004','00401','00402') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                
				begin                       
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                
				if(isnull(@empPostion,'') in ('003','006','007','00901','00902','00903','00904','008')  and ISNULL(@PROJECT_TYPE,'') ='4' and ISNULL(@proflag,2)=1)                                
				begin                                
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                 
			end 
		end              
		---更新提成                             
		update #work_detail set staffticheng=@empTicheng,person_inid=@empinid,postation=@empPostion where seqno=@tmpSeqId                                                                  
	  fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan                          
	 end                                                        
	 close cur_yeji_ticheng                                                        
	 deallocate cur_yeji_ticheng                       
	
  
                           
		--美发师做美发项目 超过指定业绩增高普通的提成比率                                
		--美发师业绩超2.5W,首席超5W,总监 7W 美发项目提成比率0.35                                
		create  table #emp_yeji_total_resultx                                 
		(                                
			inid		varchar(20) null,  --员工内部编号                                   
			yeji		float  null, -- 业绩                 
			lv			float  null, --提成比率                                     
		)                                   
                                
		insert #emp_yeji_total_resultx(inid,yeji)                                       
		select  person_inid,sum(isnull(staffyeji,0)) from #work_detail where ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15') group by person_inid                              
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='003'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		and b.yeji>=25000 and  prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='006'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		and b.yeji>=50000 and  prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='007'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		 and b.yeji>=70000  and prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
               
               
		---美容师A类              
		if(@compid in ('008','017','019','026','032'))              
		begin              
               
		 update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05  
		 from #work_detail a,#emp_yeji_total_resultx b              
		 where a.person_inid=b.inid              
		   and ISNULL(a.postation,'')='004'              
		   and (action_id>=7 and action_id<=15)              
		   and paycode not in('11','12','7','8','A','13')  
		   and cardtype not in('ZK')  
		   and yeji>=70000  
		   and code not in('300','3002','301','302','303','305','306','309','311')              
		   and action_id not in (26,27,28,29,30,31)  
  
		  --条码卡支付的时候需要判断是否为赠送  
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05  
		  from #work_detail a,nointernalcardinfo,#emp_yeji_total_resultx b  
		  where a.person_inid=b.inid               
			and a.cardtype=cardno              
			and ISNULL(a.postation,'')='004'              
			and (action_id>=7 and action_id<=15)             
			and paycode='13'              
			and yeji>=70000                 
			and isnull(entrytype,0)=0              
			and code not in('300','3002','301','302','303','305','306','309','311')              
			and action_id not in (26,27,28,29,30,31)              
		                  
                 
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.02              
		 from #work_detail a,#emp_yeji_total_resultx b              
		 where a.person_inid=b.inid and ISNULL(a.postation,'')='004'              
		   and paycode not in('11','12','7','8','A','13')              
		   and cardtype not in('ZK')              
		  and (action_id>=7 and action_id<=15)            
		   and yeji<70000               
		   and yeji>=50000              
		   and code not in('300','3002','301','302','303','305','306','309','311')              
		   and action_id not in (26,27,28,29,30,31)              
                 
		   --条码卡支付的时候需要判断是否为赠送              
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.02              
		  from #work_detail a,nointernalcardinfo,#emp_yeji_total_resultx b              
		  where a.person_inid=b.inid               
			and a.cardtype=cardno              
			and ISNULL(a.postation,'')='004'              
			and (action_id>=7 and action_id<=15)             
			and paycode='13'              
			and yeji<70000               
			and yeji>=50000              
			and isnull(entrytype,0)=0              
			and action_id not in (26,27,28,29,30,31)              
	end              
	drop table #emp_yeji_total_resultx                                
                                 
                                
                                            
                    
	--现有提成方式                  
	--承包方式:五五分成,扣5%成本                  
	--公司直营:个人业绩1-5000 50% 扣10%成本,5000以上35%扣10%成本                               
                    
    if(ISNULL(@comptypebyfinger,'1')='3') --承包方式                  
    begin                  
		update #work_detail set staffyeji=ISNULL(staffyeji,0)*0.95,staffticheng=isnull(staffticheng,0)*0.95*0.5                  
		from #work_detail,staffinfo where Manageno=person_inid and position = '005'                  
    end             
    else  if(ISNULL(@comptypebyfinger,'1')='2') --公司直营                   
    begin                  
                  
			update #work_detail set staffyeji=ISNULL(staffyeji,0)*0.9,staffticheng=isnull(staffticheng,0)*0.5*0.9                  
			where  person_inid in (select person_inid from #work_detail ,staffinfo where Manageno=person_inid and position = '005'                  
			group by person_inid having SUM(ISNULL(staffyeji,0))<=5000 )                   
                    
                        
			update #work_detail set staffyeji=ISNULL(staffyeji,0)*0.9,staffticheng=isnull(staffticheng,0)*0.35*0.9                  
			where  person_inid in (select person_inid from #work_detail ,staffinfo where Manageno=person_inid and position = '005'                  
			group by person_inid having SUM(ISNULL(staffyeji,0))>5000 )                   
    end    
    
	select seqno,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,compid,cardid,cardtype 
	from #work_detail order by action_id,srvdate
	

	drop table #empinfobydate
    drop table #work_detail 
   
end
GO
/****** Object:  StoredProcedure [dbo].[upg_personal_comm_paymode]    Script Date: 01/14/2014 15:45:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_personal_comm_paymode](                                
	@compid				varchar(10),	-- 公司别                                
	@fromdate			varchar(10),	-- 开始日期                                
	@todate				varchar(10),	-- 截至日期
	@fromempinno		varchar(20),	-- 查询开始人员内部编号         
	@toempinno			varchar(20)		-- 查询截至人员内部编号 
)                                                                   
as                                
begin                              
	create table #work_detail
	(
		seqno			int	identity		not null,       
		person_inid		varchar(20)			NULL, --员工内部编号
		action_id		int				NULL, --单据类型
		srvdate			varchar(10)			NULL, --日期
		code			varchar(20)			NULL, --项目代码,或是卡号,产品码
		name			varchar(40)			NULL, --名称
		payway			varchar(20)			NULL, --支付方式
		billamt			float				NULL, --营业金额
		ccount			float				NULL, --数量
		cost			float				NULL, --成本
		staffticheng	float				NULL, --提成
		staffyeji		float				NULL, --虚业绩
		prj_type		varchar(20)			NULL, --项目类别
		cls_flag        int					NULL, -- 1:项目 2:产品 3:卡
		billid			varchar(20)			NULL, --单号
		paycode			varchar(20)			NULL, --支付代码
		compid			varchar(10)			NULL, --公司别
		cardid			varchar(20)			NULL, --会员卡号
		cardtype		varchar(20)			NULL, --会员卡类型
		postation		varchar(10)			NULL, --职位
	)     
	exec upg_prepare_yeji_detail @compid,@fromdate, @todate,@fromempinno, @toempinno  
  
	update #work_detail set payway=parentcodevalue 
	from #work_detail,commoninfo
	where infotype='ZFFS' and parentcodekey=paycode
	
	
	update #work_detail set cardtype='' where cardtype='ZK' and paycode<>'4'
	
	create table #empinfobydate(                                
		seqno		int	identity		not null,                    
		compid		varchar(10)			null,                        
		inid		varchar(20)			null,                                
		empid		varchar(20)			null,                                
		datefrom	varchar(8)			null,                                
		dateto		varchar(8)			null,                                
		position	varchar(10)			null,                                
		salary		float				null,                                
		sharetype	varchar(5)			null,                                
		sharerate	float				null,                                
		deducttax	int					null,                                 
	 )                       
                   
    insert into #empinfobydate(compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                           
	exec upg_get_empinfo_by_date_comps @compid,@fromdate,@todate  
    --门店模式
	declare @comptypebyfinger varchar(5)                  
	select  @comptypebyfinger=ISNULL(compmode,'1') from companyinfo where compno=@compid   
	                                  
	 declare @empPostion varchar(10)                                              
	 declare @tmpSeqId int                                                        
	 declare @tmpEmpId varchar(20)                                                        
	 declare @tmpItem varchar(10)                                                        
	 declare @tmpYeji float                                                        
	 declare @tmpPrjId varchar(20)                                                        
	 declare @tmpDate varchar(8)                                                        
	 declare @paycode varchar(10)                      
	 declare @emp_total_yeji float                                       
	                                       
	 declare @GOODS_TYPE varchar(5)                                      
	 declare @PROJECT_COST float                                    
	 declare @Performance_Ratio float                                    
	 declare @Wage_Rates float                                    
	 declare @CARD_SALE_RATE float                                     
	 set @CARD_SALE_RATE=0.06                                    
	 declare @PROJECT_TYPE varchar(5)                                    
	 declare @GOODS_SALE_RATE_buty float                                    
	 declare @GOODS_SALE_RATE_hair float                                  
	 declare @GOODS_SALE_RATE_finger float --美发售产品提成比率                                       
	                                     
	 set  @GOODS_SALE_RATE_buty=0.1                                           
	 set  @GOODS_SALE_RATE_hair=0.05                          
	 set  @GOODS_SALE_RATE_finger=0.6                                  
	 declare @cardtype  varchar(20)  --会员卡类别                                    
	 declare @quan float                                
	 declare @fuflag float --正负单据                                
	 declare @businessflag int --是否为业务人员 0--不是 1--是                                 
	 declare @empinid varchar(20)                            
	 declare @proflag int --项目类别                                               
	 declare cur_yeji_ticheng cursor for                                                         
	 select seqno,person_inid,action_id,staffyeji,code,srvdate ,paycode,isnull(cardtype,''),isnull(ccount,0)                                        
	 from #work_detail                                                        
	 declare @empTicheng float     
	                                                        
	 open cur_yeji_ticheng                                                        
	 fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate,@paycode ,@cardtype,@quan                                                          
	 while(@@fetch_status=0)                    
	 begin                                     
		set @empTicheng = 0   
		--更新员工的最新职位      
		select @empPostion=position ,@empinid=inid from #empinfobydate where inid=@tmpEmpId and @tmpDate>=datefrom and @tmpDate<dateto                     
	    -- 查看员工是否是业务人员   
	    select @businessflag=ISNULL(businessflag,0)  from staffinfo with(nolock)  where manageno=@tmpEmpId                             
	    -- 如果是非业务人员提成为0
	    if(@businessflag=0 or @empPostion in ('001','00101','00102','00103','00104','002','010','011','01101','01102','01201','01202','803'))                                      
		begin                                
			update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                   
			fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan                                
			continue                                   
		end
		-- 16，17，18，19，20，21，22，23，24-售产品  
		if(isnull(@tmpItem,'') in ('16','17','18','19','20','21','22','23','24'))                                                
		begin      
				select   @GOODS_TYPE=isnull(goodstype,1)  from  goodsnameinfo with(nolock) where goodsno=@tmpPrjId                                      
			if(isnull(@GOODS_TYPE,'300'))='400'----美容产品(扣除成本20%提成10%)                                      
			begin                                      
				update #work_detail set staffyeji=isnull(staffyeji,0)*0.8 where seqno=@tmpSeqId                                       
				set @empTicheng = isnull(@tmpYeji,0)*0.8 *@GOODS_SALE_RATE_buty                                       
			end                                      
			else if(isnull(@GOODS_TYPE,'300'))='300'----美发产品（提成5%）                                       
			begin                                      
				update #work_detail set staffyeji=isnull(staffyeji,0) where seqno=@tmpSeqId                                       
				set @empTicheng = isnull(@tmpYeji,0)*@GOODS_SALE_RATE_hair                                         
			end                                     
			else if(isnull(@GOODS_TYPE,'300'))='500'----美甲产品（产品不扣成本 4，6分）                                     
			begin                                         
				set @empTicheng = isnull(@tmpYeji,0)*@GOODS_SALE_RATE_finger--0.4                                      
			end                                  
			else if(isnull(@GOODS_TYPE,'300'))='700'----卡诗产品（无业绩无提成）                                           
			begin                                         
				update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                       
				set @empTicheng = 0                                
			end
                                                               
		end  
		-- 开卡+充值+转卡+条码卡开卡 1，2，3，5
		else if(isnull(@tmpItem,'')='1' or isnull(@tmpItem,'')='2'  or isnull(@tmpItem,'')='3' or isnull(@tmpItem,'')='5')                                     
		begin                                       
			if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师1.5%                                   
				set @empTicheng = @tmpYeji*0.015                                     
			else if (isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2%                                   
				set @empTicheng = @tmpYeji*0.02                                      
			else    -- 其他职位都是6%               
				set @empTicheng = @tmpYeji*@CARD_SALE_RATE                                              
		end   
		-- 合作项目销售  26，27，28，29，30，31                                     
	    else if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27'                        
			or  isnull(@tmpItem,'')='28' or isnull(@tmpItem,'')='29'                        
			or  isnull(@tmpItem,'')='30' or isnull(@tmpItem,'')='31' )                                   
		begin                                       
			if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27' or isnull(@tmpItem,'')='30' )                        
				set @empTicheng = @tmpYeji*0.06                           
			else if (isnull(@tmpItem,'')='28')                        
				set @empTicheng = @tmpYeji*0.3                        
		end  
		--疗程兑换  4
		else if(isnull(@tmpItem,'')='4')                               
		begin                                
			if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师2%                                   
				set @empTicheng = @tmpYeji*0.02                                     
			else if ( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2.5%                                   
				set @empTicheng = @tmpYeji*0.025                                    
			--首席，总监，设计师记业绩20%,提成20%                                 
			else if ( isnull(@empPostion,'')='003' or isnull(@empPostion,'')='006' or isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00102')                                  
			begin                                
				update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.2 where seqno=@tmpSeqId                                  
				set @empTicheng = isnull(@tmpYeji,0)*0.2*0.2                
			end                                
		end   
		--项目消费 7,8,9,10,11,12,13,14,15
		else
		begin
			set @fuflag=@quan   
			if(isnull(@paycode,'')='9') --疗程                                                  
			begin                                                    
				--疗程消费美容师和美发师按照设定成本和业绩比率走                                
				select @proflag=isnull(prjpricetype,2),@PROJECT_COST=isnull(costprice,0),@Performance_Ratio=isnull(lyjrate,1),@Wage_Rates=isnull(ltcrate,1),@PROJECT_TYPE=prjtype  
				from  projectinfo ,compchaininfo 
				where prisource=curcomp and relationcomp=@compid and prjno=@tmpPrjId                                 
				
				update #work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                       
				set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                      
	                                       
				--烫染师 非疗程卡 记80%业绩 记5%提成 纯疗程卡记120块的业绩，6块钱的提成                                
				if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )                                    
				begin      
					if( isnull(@cardtype,'') ='MR' or isnull(@cardtype,'')='MF')                                    
					begin                                
						update #work_detail set staffyeji=120*@fuflag where seqno=@tmpSeqId                                       
						set @empTicheng=6*@fuflag                                
					end                                 
					else                                
					begin                                     
						update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.8 where seqno=@tmpSeqId                         
						set @empTicheng = isnull(@tmpYeji,0)*0.8*0.05                                
						if(isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')--四级烫染师                                
							set @empTicheng = isnull(@tmpYeji,0)*0.8*0.06                                
					end                                   
				end                                                      
				if(isnull(@empPostion,'')in('004','00401','00402') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                
				begin                                
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                
				if(isnull(@empPostion,'') in ('003','006','007','00901','00902','00903','00904','008')  and ISNULL(@PROJECT_TYPE,'') ='4' and ISNULL(@proflag,2)=1)                                
				begin                                
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                 
				--美发的输入在中工和小工位置的(美发师,首席,总监)无提成                                
				if((@PROJECT_TYPE='3' or @PROJECT_TYPE='6') and isnull(@empPostion,'') in ('003','006','007') and isnull(@tmpItem,'') not in ('7','8','9'))                                
				begin                                
					set @empTicheng = 0                                 
				end                                
				if(@cardtype ='MFOLD' )                                 
				begin                                
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng =0                                
				end                                                   
			end 
			else if( (@cardtype ='ZK' and isnull(@paycode,'')='4') or isnull(@paycode,'')='$'  or isnull(@paycode,'')='A' or isnull(@paycode,'')='7' or isnull(@paycode,'')='11'  or isnull(@paycode,'')='12'  )               
			begin                             
					if(isnull(@tmpYeji,0)>0)                                
						set @fuflag=1                                
					else                                
						set @fuflag=-1                                
					--项目抵用券使用面值做业绩                                
					if(isnull(@paycode,'')='11' )                                
					begin      
						select @tmpYeji=ISNULL(cardfaceamt,0) from nointernalcardinfo where cardno=@cardtype                        
						if(ISNULL(@fuflag,0)<0)                      
							set  @tmpYeji=ISNULL(@tmpYeji,0)*(-1)                             
						update #work_detail set staffyeji=@tmpYeji,billamt=@tmpYeji where seqno=@tmpSeqId                                  
					end                                 
					--烫染师 记业绩24% 提成5%                                
					if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                    
					begin                                
						update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
						set @empTicheng = isnull(@tmpYeji,0)*0.24*0.05                                  
						if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')          
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.06                                  
					end                                  
					--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                 
					else                                 
					begin                                
						update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
						set @empTicheng = isnull(@tmpYeji,0)*0.24*0.22                                
					end                                  
					---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                
					---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3   
					                  
					if(@tmpPrjId='300' or @tmpPrjId='3002' or @tmpPrjId='301' or @tmpPrjId='302' or @tmpPrjId='303' or @tmpPrjId='305' or @tmpPrjId='306'  or @tmpPrjId='309'   or @tmpPrjId='311'  )                                
					begin                                
						if(isnull(@empPostion,'')in('004','00401','00402') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                               
						begin                                
							if(@tmpPrjId='300' or @tmpPrjId='302' or @tmpPrjId='303'  or @tmpPrjId='309' )                                
							begin                                
								update #work_detail set staffyeji=20*@fuflag where seqno=@tmpSeqId                                  
								set @empTicheng =6*@fuflag                                
							end                                 
							else                                
							begin                                
								update #work_detail set staffyeji=5*@fuflag where seqno=@tmpSeqId                                  
								set @empTicheng =1.5*@fuflag                                
							end                                
						end                                       
						if((@tmpPrjId='300' or @tmpPrjId='3002') and isnull(@tmpItem,'') not in ('7','8','9'))                                
						begin                                
							if(isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007')--首席总监                                
							begin                                
								update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
								set @empTicheng =3*@fuflag                                
							end                                       
							else if(isnull(@empPostion,'')='003')                                
							begin                                
								update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
								set @empTicheng =2.8*@fuflag                                
							end                                       
							else                                 
							begin                                
								update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
								set @empTicheng =0                                
							end                                
						end                                
					end                         
			end 
			else
			begin
					select @proflag=isnull(prjpricetype,2),@PROJECT_COST=isnull(costprice,0),@Performance_Ratio=isnull(kyjrate,1),@Wage_Rates=isnull(ktcrate,1),@PROJECT_TYPE=prjtype  
					from  projectinfo ,compchaininfo 
					where prisource=curcomp and relationcomp=@compid  and prjno=@tmpPrjId  
					if(@PROJECT_TYPE<>'5')--美甲成本单独算                  
					begin                  
						update #work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                       
					end                  
					set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                      
					--首席总监 扣除成本后×业绩比率，提成 30%                                
					if((isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007') and ISNULL(@PROJECT_TYPE,'0')<>'6' )                                    
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*0.3                                     
					--烫染师 扣除成本后记业绩5%                                
					if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  )                                    
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0)*0.05                                     
					if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0)*0.06                                     
		                 
					---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                
					---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3                                    
					if(@tmpPrjId='300' or @tmpPrjId='3002' or @tmpPrjId='301' or @tmpPrjId='302' or @tmpPrjId='303' or @tmpPrjId='305' or @tmpPrjId='306'  or @tmpPrjId='309'   or @tmpPrjId='311'  )                                
					begin                                
						if(isnull(@empPostion,'')='003') --美发师                                
						begin                                
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.75 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.75*0.28                                  
						end                                 
						else if(isnull(@empPostion,'')='006') --美发师                                
						begin                                
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.85 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.85*0.3                                  
						end                             
						else if(isnull(@empPostion,'')='007') --美发师                                
						begin                                
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.89 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.89*0.3                                  
						end                                 
						else if(isnull(@empPostion,'')in('004','00401','00402') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                        
						begin                                
							if(@tmpPrjId='300' or @tmpPrjId='302' or @tmpPrjId='303'  or @tmpPrjId='309' )                              begin                                
								update #work_detail set staffyeji=20*@fuflag where seqno=@tmpSeqId                                  
							set @empTicheng =6*@fuflag                                
						end                                 
						else                                
						begin                                
							update #work_detail set staffyeji=5*@fuflag where seqno=@tmpSeqId                                  
							set @empTicheng =1.5*@fuflag                                
						end                                
					end  
					if((@tmpPrjId='300' or @tmpPrjId='3002') and isnull(@tmpItem,'') not in ('7','8','9'))                 
					begin                                      
						if(isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007')--首席总监        
						begin                                
							update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
							set @empTicheng =3*@fuflag                                
						end                                       
						else if(isnull(@empPostion,'')='003')                                
						begin                                
							update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                
							set @empTicheng =2.8*@fuflag                                
						end                                       
						else                                 
						begin                                
							update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
							set @empTicheng =0                                
						end                        
		                                       
					end                                
				end                                
				if(isnull(@paycode,'')='13' )                                
				begin                                
					declare @tmcardfrom int --0 正常开卡,1 赠送开卡                                
					select @tmcardfrom=ISNULL(entrytype,0) from nointernalcardinfo where cardno=@cardtype                                
					if(ISNULL(@tmcardfrom,0)=1)                                
					begin                                
						--烫染师 记业绩24% 提成5%                                
						if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                    
						begin                                
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.05                                  
							if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                
								set @empTicheng = isnull(@tmpYeji,0)*0.24*0.06                                  
						end                                  
						--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                 
						else                                 
						begin                                
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.22                                
						end                                 
					end                                 
				end        
				--美发的输入在中工和小工位置的(美发师,首席,总监)无提成                                
				if((@PROJECT_TYPE='3' or @PROJECT_TYPE='6') and isnull(@empPostion,'') in ('003','006','007') and isnull(@tmpItem,'') not in ('7','8','9'))                                
				begin                                
					set @empTicheng = 0                                 
				end                                
				else if(@PROJECT_TYPE='5')-- 美甲业绩扣10%的成本，提成4，6分                                
				begin                                
					if(isnull(@empPostion,'')='005' )                                
					begin                    
						if( ISNULL(@comptypebyfinger,'1')='1')                    
						begin                       
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.9 where seqno=@tmpSeqId                                  
							set @empTicheng = isnull(@tmpYeji,0)*0.9*0.6                      
						end                              
					end                                
					else                                
					begin                                
						update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
						set @empTicheng =0                                
					end                                
				end  
				                                  
				if(isnull(@empPostion,'') in('004','00401','00402')  and @tmpPrjId in ('48811') and isnull(@paycode,'')<>'13')                                
				begin                                
					if(isnull(@tmpItem,'') in ('7','8','9'))                
					begin       
						if(isnull(@tmpYeji,0)<100)     
							set @tmpYeji=100                      
						update #work_detail set staffyeji= isnull(@tmpYeji,0)*0.8 where seqno=@tmpSeqId           
						set @empTicheng = isnull(@tmpYeji,0)*0.8*0.3*@fuflag                
					end                           
					else                                
					begin                                
						update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
						set @empTicheng = 0               
					end                                
				end                               
				if(isnull(@empPostion,'')in('004','00401','00402') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                
				begin                       
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                
				if(isnull(@empPostion,'') in ('003','006','007','00901','00902','00903','00904','008')  and ISNULL(@PROJECT_TYPE,'') ='4' and ISNULL(@proflag,2)=1)                                
				begin                                
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                  
					set @empTicheng = 0                                 
				end                                 
			end 
		end              
		---更新提成                             
		update #work_detail set staffticheng=@empTicheng,person_inid=@empinid,postation=@empPostion where seqno=@tmpSeqId                                                                  
	  fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan                          
	 end                                                        
	 close cur_yeji_ticheng                                                        
	 deallocate cur_yeji_ticheng                       
	
  
                           
		--美发师做美发项目 超过指定业绩增高普通的提成比率                                
		--美发师业绩超2.5W,首席超5W,总监 7W 美发项目提成比率0.35                                
		create  table #emp_yeji_total_resultx                                 
		(                                
			inid		varchar(20) null,  --员工内部编号                                   
			yeji		float  null, -- 业绩                 
			lv			float  null, --提成比率                                     
		)                                   
                                
		insert #emp_yeji_total_resultx(inid,yeji)                                       
		select  person_inid,sum(isnull(staffyeji,0)) from #work_detail where ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15') group by person_inid                              
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='003'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		and b.yeji>=25000 and  prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='006'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		and b.yeji>=50000 and  prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='007'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		 and b.yeji>=70000  and prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
               
               
		---美容师A类              
		if(@compid in ('008','017','019','026','032'))              
		begin              
               
		 update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05  
		 from #work_detail a,#emp_yeji_total_resultx b              
		 where a.person_inid=b.inid              
		   and ISNULL(a.postation,'')='004'              
		   and (action_id>=7 and action_id<=15)              
		   and paycode not in('11','12','7','8','A','13')  
		   and cardtype not in('ZK')  
		   and yeji>=70000  
		   and code not in('300','3002','301','302','303','305','306','309','311')              
		   and action_id not in (26,27,28,29,30,31)  
  
		  --条码卡支付的时候需要判断是否为赠送  
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05  
		  from #work_detail a,nointernalcardinfo,#emp_yeji_total_resultx b  
		  where a.person_inid=b.inid               
			and a.cardtype=cardno              
			and ISNULL(a.postation,'')='004'              
			and (action_id>=7 and action_id<=15)             
			and paycode='13'              
			and yeji>=70000                 
			and isnull(entrytype,0)=0              
			and code not in('300','3002','301','302','303','305','306','309','311')              
			and action_id not in (26,27,28,29,30,31)              
		                  
                 
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.02              
		 from #work_detail a,#emp_yeji_total_resultx b              
		 where a.person_inid=b.inid and ISNULL(a.postation,'')='004'              
		   and paycode not in('11','12','7','8','A','13')              
		   and cardtype not in('ZK')              
		  and (action_id>=7 and action_id<=15)            
		   and yeji<70000               
		   and yeji>=50000              
		   and code not in('300','3002','301','302','303','305','306','309','311')              
		   and action_id not in (26,27,28,29,30,31)              
                 
		   --条码卡支付的时候需要判断是否为赠送              
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.02              
		  from #work_detail a,nointernalcardinfo,#emp_yeji_total_resultx b              
		  where a.person_inid=b.inid               
			and a.cardtype=cardno              
			and ISNULL(a.postation,'')='004'              
			and (action_id>=7 and action_id<=15)             
			and paycode='13'              
			and yeji<70000               
			and yeji>=50000              
			and isnull(entrytype,0)=0              
			and action_id not in (26,27,28,29,30,31)              
	end              
	drop table #emp_yeji_total_resultx                                
                                 
                                
                                            
                    
	--现有提成方式                  
	--承包方式:五五分成,扣5%成本                  
	--公司直营:个人业绩1-5000 50% 扣10%成本,5000以上35%扣10%成本                               
                    
    if(ISNULL(@comptypebyfinger,'1')='3') --承包方式                  
    begin                  
		update #work_detail set staffyeji=ISNULL(staffyeji,0)*0.95,staffticheng=isnull(staffticheng,0)*0.95*0.5                  
		from #work_detail,staffinfo where Manageno=person_inid and position = '005'                  
    end             
    else  if(ISNULL(@comptypebyfinger,'1')='2') --公司直营                   
    begin                  
                  
			update #work_detail set staffyeji=ISNULL(staffyeji,0)*0.9,staffticheng=isnull(staffticheng,0)*0.5*0.9                  
			where  person_inid in (select person_inid from #work_detail ,staffinfo where Manageno=person_inid and position = '005'                  
			group by person_inid having SUM(ISNULL(staffyeji,0))<=5000 )                   
                    
                        
			update #work_detail set staffyeji=ISNULL(staffyeji,0)*0.9,staffticheng=isnull(staffticheng,0)*0.35*0.9                  
			where  person_inid in (select person_inid from #work_detail ,staffinfo where Manageno=person_inid and position = '005'                  
			group by person_inid having SUM(ISNULL(staffyeji,0))>5000 )                   
    end    
    
	select seqno,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,compid,cardid,cardtype 
	from #work_detail order by action_id,srvdate
	

	drop table #empinfobydate
    drop table #work_detail 
   
end
GO
/****** Object:  StoredProcedure [dbo].[upg_inoutstock_analysis]    Script Date: 01/14/2014 15:45:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_inoutstock_analysis](    
	@compid			varchar(10),			--门店编号    
	@datefrom		varchar(10),			--日期   
	@dateto			varchar(10),			--日期  
	@goodsfrom		varchar(20),			--物品编号   
	@goodsto		varchar(20),			--物品编号   
	@wareid			varchar(20)			--仓库编号
)    
as
begin    

  create table #begstockquan    --存放期初量    
  (    
		id				int identity	not null,      
		goodsno			varchar(20)		null,--物品编号      
		goodsname		varchar(80)		null,--物品名称      
		garageno		varchar(20)		null,--仓库编号      
		garagename		varchar(20)		null,--仓库名称      
		goodstype		varchar(20)		null,--产品类型    
		goodstypename	varchar(20)		null,--产品类型名称 
		unit			varchar(10)		null,--单位    
		price			float			null,--单价  
		quantity		float			null,--数量          
		amt				float			null,--金额   
		primary key (id),    
  )    
  create table #endstockquan    -- 存放期末量    
  (    
		id				int identity	not null,      
		goodsno			varchar(20)		null,--物品编号      
		goodsname		varchar(80)		null,--物品名称      
		garageno		varchar(20)		null,--仓库编号      
		garagename		varchar(20)		null,--仓库名称      
		goodstype		varchar(20)		null,--产品类型    
		goodstypename	varchar(20)		null,--产品类型名称 
		unit			varchar(10)		null,--单位    
		price			float			null,--单价  
		quantity		float			null,--数量          
		amt				float			null,--金额     
   primary key (id),    
  )    
  create table #instock      --存放入库量    
  (    
		id				int identity			not null,    
		goodsno			varchar(20)					null,--物品编号    
		garageno		varchar(20)					null,--仓库编号    
		quantity		float						null,--数量    
		amt				float						null,--入库金额 
   primary key (id),    
  )    
  create table #outstock      --存放出库量    
  (    
		id				int identity			not null,    
		goodsno			varchar(20)					null,--物品编号    
		garageno		varchar(20)					null,--仓库编号 
		quantity		float						null,--数量    
		amt				float						null,--出库金额 
   primary key (id),    
  )     
   create table #sendstock      -- 发货途中 
  (    
		id				int identity			not null,    
		goodsno			varchar(20)					null,--物品编号    
		garageno		varchar(20)					null,--仓库编号 
		quantity		float						null,--数量    
		amt				float						null,--发货金额 
   primary key (id),    
  )     
  
      
	  --对日期处理,对1天的数据进行查询    
	  declare @start_date   varchar(8)    
	  declare @strtmp     varchar(10)    
	       
	  set @strtmp = convert(varchar(10),dateadd(day,-1,@datefrom),120)    
	  set @start_date = substring(@strtmp,1,4)+substring(@strtmp,6,2)+substring(@strtmp,9,2)    
      
   
		insert #instock(goodsno,garageno,quantity,amt)    
		select  changegoodsno,changewareid,isnull(sum(changecount),0),isnull(sum(changeamt),0)     
                from  mgoodsstockinfo a,dgoodsstockinfo b  
                where a.changecompid= b.changecompid       
                  and a.changetype= b.changetype       
                  and a.changebillno= b.changebillno      
                  and a.changetype='1'       
                  and a.changedate  between @datefrom and @dateto  
                  and a.changecompid= @compid  
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')  
                  and (changewareid = @wareid or ISNULL(@wareid,'')='')  
                  and b.changebillno not in ('00120131231001x','00120131231002x')
       group by changegoodsno,changewareid     
         
       insert #outstock(goodsno,garageno,quantity,amt)    
      select  changegoodsno,changewareid,isnull(sum(changecount),0),isnull(sum(changeamt),0)     
                from  mgoodsstockinfo a,dgoodsstockinfo b  
                where a.changecompid= b.changecompid       
                  and a.changetype= b.changetype       
                  and a.changebillno= b.changebillno      
                  and a.changetype<>'1'      
                  and a.changedate  between @datefrom and @dateto  
                  and a.changecompid= @compid  
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')  
                  and (changewareid = @wareid or ISNULL(@wareid,'')='')  
                   and b.changebillno not in ('00120131231001x','00120131231002x')
       group by changegoodsno,changewareid      
   
  
		
     insert #sendstock(goodsno,garageno,quantity,amt)    
      select  ordergoodsno,headwareno,isnull(sum(downordercount),0),isnull(sum(downorderamt),0)     
               from mgoodsorderinfo a,dgoodsorderinfo b
               where a.ordercompid=b.ordercompid and a.orderbillid=b.orderbillid      
                  and ISNULL(a.invalid,0)<>'1'      
                  and a.downorderdate  between @datefrom and @dateto  
                  and a.downordercompid= @compid and isnull(orderstate,0)=2
                  and (ordergoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')  
                  and (headwareno = @wareid or ISNULL(@wareid,'')='')  
       group by ordergoodsno,headwareno     
     
     --期初库存量    
     insert #begstockquan(goodsno,goodsname,garageno,garagename,unit,goodstype,goodstypename,quantity,price,amt)    
     exec upg_compute_goods_stock @compid,@start_date,@goodsfrom,@goodsto,@wareid
	 --期末库存量    
     insert #endstockquan(goodsno,goodsname,garageno,garagename,unit,goodstype,goodstypename,quantity,price,amt)    
     exec upg_compute_goods_stock @compid,@dateto,@goodsfrom,@goodsto,@wareid
	
  select goodsno=a.goodsno,goodsname=a.goodsname,garageno=a.garageno,garagename=a.garagename,    
   pquan=sum(isnull(b.quantity,0)),unit=a.unit,    
   pamt=sum(isnull(b.amt,0)),    
   inquan=sum(isnull(c.quantity,0)),   
   inamt =sum(isnull(c.amt,0)) ,
   outquan=sum(isnull(d.quantity,0)),    
   outamt=sum(isnull(d.amt,0)),
   equan=sum(isnull(a.quantity,0)),    
   eamt=sum(isnull(a.amt,0))  ,
   squan=sum(isnull(e.quantity,0)),    
   samt=sum(isnull(e.amt,0))   
  from #endstockquan a    
  left outer join #begstockquan b on a.goodsno = b.goodsno and a.garageno = b.garageno    
  left outer join #instock c on a.goodsno = c.goodsno and a.garageno = c.garageno    
  left outer join #outstock d on a.goodsno = d.goodsno and a.garageno = d.garageno   
  left outer join #sendstock e on a.goodsno = e.goodsno and a.garageno = e.garageno  
  group by a.goodsno,a.goodsname,a.garageno,a.garagename,a.unit    
    
    
       
  drop table #begstockquan    
  drop table #endstockquan    
  drop table #instock    
  drop table #outstock    
     
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_prjclassed_yeji]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[upg_compute_comp_prjclassed_yeji]                        
(                                  
 @compid varchar(10) ,                                  
 @datefrom varchar(8),                                  
 @dateto varchar(8)                                
)                                  
as                                  
begin                                  
--计算业绩相关常量                                      
 declare @PRJ_BEAUT_CLASS_CODE varchar(10) --项目类别之美容类代码                                      
 declare @PRJ_HAIR_CLASS_CODE varchar(10)  --项目类别之美发类代码                                      
 declare @PRJ_FOOT_CLASS_CODE varchar(10)  --项目类别之足疗类代码                                      
 declare @PRJ_FINGER_CLASS_CODE varchar(10) --项目类别之美甲类代码                                      
 declare @GOODS_BEAUT_CLASS_CODE varchar(10) --物品类别之美容类代码                                      
 declare @GOODS_HAIR_CLASS_CODE varchar(10)  --物品类别之美发类代码                                      
 declare @DEP_BEAUT_CODE varchar(10)  --美容部门代码                                      
 declare @DEP_HAIR_CODE varchar(10)   --美发部门代码                                      
 declare @DEP_FOOT_CODE varchar(10)   --美发部门代码                                      
 declare @DEP_FINGER_CODE varchar(10)   --美发部门代码                                      
                                       
 set @PRJ_BEAUT_CLASS_CODE = '4'                                      
 set @PRJ_HAIR_CLASS_CODE = '3'                                      
 set @PRJ_FOOT_CLASS_CODE = '6'                                      
 set @PRJ_FINGER_CLASS_CODE = '5'                         
                                      
 set @GOODS_BEAUT_CLASS_CODE = '400'                                      
 set @GOODS_HAIR_CLASS_CODE = '300'                          
                                     
 set @DEP_BEAUT_CODE = '003'  --美容部                                     
 set @DEP_HAIR_CODE = '004'   --美发部                                  
 set @DEP_FINGER_CODE = '005' --美甲部                         
 set @DEP_FOOT_CODE = '006'  --烫染部                                  
                                      
 --11.项目消费美容类业绩; 12.项目消费美发类业绩；13.项目消费总业绩;14.项目消费足疗类业绩；15.项目消费美甲类业绩                                      
 --21.售美容类产品业绩;   22.售美发类产品业绩；  23.售产品总业绩；                                      
 --31.开卡美容类业绩；  32.开卡美发类业绩； 33.开卡总业绩;    34.开卡足疗类业绩；    35.开卡美甲类业绩                                      
 --41.帐户异动美容类业绩；42.帐户异动美发类业绩；43.帐户异动总业绩 44.帐户异动足疗类业绩；45.帐户异动美甲类业绩                                      
 --51.卡异动美容类业绩；  52.卡异动美发类业绩；  53.卡异动总业绩   54.卡异动足疗类业绩；  55.卡异动美甲类业绩                                      
                                      
 --81.项目消费美容类实业绩；82.项目消费美发类实业绩；83.项目消费总实业绩；84.项目消费足疗类实业绩；85.项目消费美甲类实业绩                                      
 --91.售美容类产品实业绩；  92.售美发类产品实业绩；  93.售产品总实业绩；                                      
 create table #yeji_result(                                      
  compid  varchar(10) not null,                                      
  id         int  identity       , -- 流水号                                      
  item  varchar(5)  null, -- 业绩项目类别                                      
  yeji  float  null, -- 业绩                                      
  datefrom varchar(8) null, -- 开始日期                                      
  dateto  varchar(8)  null, -- 截止日期                                      
 )                                      
 create table #prj_cls(                                      
                                    
  prjid varchar(20) null,--项目编号                                      
  prjcls varchar(10) null--项目类别                                      
 )                                      
 create clustered index idx_clust_prj_cls on #prj_cls(prjcls,prjid)                                      
 insert into #prj_cls(prjid,prjcls)                                      
 select prjno,prjtype from projectnameinfo                               
                                      
              
 create table #emp_dep(                                    
  compid varchar(10) null,                                    
  inid varchar(20) null,--内部编号                                   
  empid varchar(20) null,--员工编号                                    
  empdep varchar(10) null,--员工部门                                    
  datefrom varchar(8) null,                                    
  dateto varchar(8) null,                                    
 )                                    
 create clustered index idx_tmp_emp_dep on #emp_dep(compid,empid,datefrom,dateto)                                    
 create table #empinfobydate(                                    
  seqno int identity not null,                        
  compid varchar(10) null,                            
  inid varchar(20) null,                                    
  empid varchar(20) null,                                    
  datefrom varchar(8) null,                                    
  dateto  varchar(8) null,                                    
  position varchar(10) null,                                    
  salary  float null,                                    
  sharetype varchar(5) null,                                    
  sharerate float null,                                    
  deducttax int null,                                     
 )                           
                       
    declare @compidid varchar(10)                      
    declare cur_each_comp cursor for                      
	 select relationcomp from compchaininfo  where curcomp=@compid                      
	 open cur_each_comp                      
	 fetch cur_each_comp into @compidid                      
	 while @@fetch_status=0                      
	 begin                      
	   insert into #empinfobydate(compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                                    
	   exec upg_get_empinfo_by_date_comps @compidid,@datefrom,@dateto                        
	                      
	   fetch cur_each_comp into @compidid                      
	 end                      
	 close cur_each_comp                      
	 deallocate cur_each_comp                      
                      
                                             
	insert into #emp_dep(compid,inid,empid,empdep,datefrom,dateto)                                
	select distinct compid,inid,empid,department,datefrom,dateto                                
	from #empinfobydate,staffinfo with (nolock)                                
	where inid=manageno and isnull(inid,'')<>''           
                                   
                       
                                       
	--计算项目消费虚业绩                                  
	create table #m_d_consumeinfo(                                  
	  cscompid			varchar(10)     NULL,   --公司编号                                  
	  csbillid			varchar(20)		NULL,   --消费单号                               
	  financedate		varchar(8)		NULL,   --帐务日期 
	  csinfotype		int             NULL,	--消费类型  1 项目  2 产品 
	  csitemno			varchar(20)     NULL,   --项目代码                                  
	  csitemamt			float           NULL,   --金额                                  
	  csfirstsaler		varchar(20)     NULL,   --大工代码                                  
	  csfirstinid		varchar(20)		NULL,   --大工内部编号  
	  csfirstshare		float           NULL,	--大工分享                              
	  cssecondsaler		varchar(20)     NULL,   --中工代码                                  
	  cssecondinid		varchar(20)		NULL,   --中工内部编号 
	  cssecondshare		float           NULL,	--中工分享                               
	  csthirdsaler		varchar(20)     NULL,   --小工代码        
	  csthirdinid		varchar(20)		NULL,   --小工内部编号 
	  csthirdshare		float           NULL,	--小工分享                      
	  cspaymode			varchar(5)      NULL   --项目支付方式                                     
	)  
	create nonclustered index index_m_d_consumeinfo_csitemno  on #m_d_consumeinfo(cscompid,csinfotype,csitemno)  
	create nonclustered index index_m_d_consumeinfo_financedate  on #m_d_consumeinfo(cscompid,csinfotype,financedate)  
	                                  
	insert into #m_d_consumeinfo(cscompid,csbillid,csinfotype,financedate,csitemno,csitemamt,csfirstsaler,csfirstinid,cssecondsaler,cssecondinid,csthirdsaler,csthirdinid,cspaymode,csfirstshare,cssecondshare,csthirdshare)                                  
	select a.cscompid,a.csbillid,csinfotype,a.financedate,csitemno,csitemamt,csfirstsaler,csfirstinid,cssecondsaler,cssecondinid,csthirdsaler,csthirdinid,cspaymode,csfirstshare,cssecondshare,csthirdshare                                  
	   from mconsumeinfo a WITH (NOLOCK),dconsumeinfo b with (nolock),compchaininfo                                   
	  where a.cscompid=b.cscompid                                  
		and a.csbillid=b.csbillid                                  
		and a.cscompid =relationcomp 
		and curcomp= @compid                                  
		and financedate>=@datefrom                                   
		and financedate<=@dateto                
                              
		insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'11',isnull(sum(csitemamt),0)                                  
		 from #m_d_consumeinfo,#prj_cls                                  
		 where csitemno=prjid                                   
		   and prjcls=@PRJ_BEAUT_CLASS_CODE                                  
		   --and isnull(cspaymode,'') in ('1','2','6','14','15','16') 
		   and isnull(csinfotype,1)=1                                  
		 group by cscompid                                
		                                 
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'12',isnull(sum(csitemamt),0)                                  
		 from #m_d_consumeinfo,#prj_cls                                  
		 where csitemno=prjid                                   
		   and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)                            
		   --and isnull(cspaymode,'') in ('1','2','6','14','15','16')
		   and isnull(csinfotype,1)=1                                        
		 group by cscompid                       
		                     
		                             
		 
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'15',isnull(sum(csitemamt),0)                                  
		 from #m_d_consumeinfo,#prj_cls                                  
		 where csitemno=prjid                                   
		   and prjcls=@PRJ_FINGER_CLASS_CODE                                  
		   --and isnull(cspaymode,'') in ('1','2','6','16')  
		   and isnull(csinfotype,1)=1                                
		 group by cscompid       
		                      
		  
    --------------------------------------------计算项目虚实业绩-------END--------------------------------------------------
    
    --21.售美容类产品业绩;   22.售美发类产品业绩；  23.售产品总业绩                                  
	--91.售美容类产品实业绩；  92.售美发类产品实业绩；  93.售产品总实业绩；                                  
	                      
	--------------------------------------------计算产品虚实业绩-------Start------------------------------------------------
		  insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '21'
								when empdep=@DEP_HAIR_CODE		then '22'
								when empdep=@DEP_FOOT_CODE		then '24x'
								when empdep=@DEP_FINGER_CODE    then '25' end ,isnull(sum(csfirstshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=csfirstinid                                  
			--and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                
			and financedate>=datefrom and financedate<dateto    and cscompid=compid  
			and isnull(csinfotype,1)=2                          
		  group by cscompid,empdep                                  
		                                  
		   insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '21'
								when empdep=@DEP_HAIR_CODE		then '22'
								when empdep=@DEP_FOOT_CODE	then '24x'
								when empdep=@DEP_FINGER_CODE    then '25' end,isnull(sum(cssecondshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=cssecondinid                                   
			--and isnull(cspaymode,'') in ('1','2','6','14','15','16')                              
			and financedate>=datefrom and financedate<dateto   and cscompid=compid 
			and isnull(csinfotype,1)=2                                
		  group by cscompid,empdep                                 
		                                  
		   insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '21'
								when empdep=@DEP_HAIR_CODE		then '22'
								when empdep=@DEP_FOOT_CODE		then '24x'
								when empdep=@DEP_FINGER_CODE    then '25' end,isnull(sum(csthirdshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=csthirdinid                                  
		   --and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                
		   and financedate>=datefrom and financedate<dateto  and cscompid=compid 
		   and isnull(csinfotype,1)=2                               
		  group by cscompid,empdep                                 
		                                  
		
		                  
	
		        
	--------------------------------------------计算产品虚实业绩-------END--------------------------------------------------
	
                           
         
	create table #prjchange_yeji_resultx(                                
		compid					varchar(10)		null,	--门店编号         
		compname				varchar(50)		null,	--门店名称 
        beaut_prj_yeji			float			null,  --美容项目                          
		hair_prj_yeji			float			null,  --美发项目                           
		finger_prj_yeji			float			null,  --美甲项目                             
		beaut_goods_yeji		float			null,  --美容产品                              
		hair_goods_yeji			float			null,  --美发产品                             
		finger_goods_yeji		float			null,  --美甲产品 
		projecttype				varchar(10)		null,	--类型编号    
		projectamt				float			null,	--类型金额              
	 )     
     
    insert #prjchange_yeji_resultx(compid,projecttype,projectamt )      
	select a.cscompid,prjreporttype+'Amt',sum(isnull(csitemamt,0))
	from #m_d_consumeinfo a ,commoninfo,compchaininfo,projectnameinfo
	where infotype='XMTJ' and a.csinfotype='1' and prjno=a.csitemno and prjreporttype=parentcodekey
	and a.cscompid =relationcomp and curcomp=@compid and financedate between @datefrom and @dateto    
	group by a.cscompid,prjreporttype  order by cscompid,prjreporttype                                
	
	update a set compname= b.compname  from  #prjchange_yeji_resultx a,companyinfo b  where a.compid=b.compno
	
    update a set beaut_prj_yeji=b.beaut_prj_yeji,hair_prj_yeji=b.hair_prj_yeji,finger_prj_yeji=b.finger_prj_yeji,
				 beaut_goods_yeji=b.beaut_goods_yeji,hair_goods_yeji=b.hair_goods_yeji,finger_goods_yeji=b.finger_goods_yeji
      from  #prjchange_yeji_resultx a,(
				select compid,beaut_prj_yeji=isnull(sum( case when item in ('11','61') then yeji else 0 end ),0),              
					hair_prj_yeji=isnull(sum( case when item in ('12','62') then yeji else 0 end ),0),        
					finger_prj_yeji=isnull(sum( case when item='15' then yeji else 0 end ),0),                
					beaut_goods_yeji=isnull(sum( case when item='21' then yeji else 0 end ),0),              
					hair_goods_yeji=isnull(sum( case when item='22' then yeji else 0 end ),0),        
					finger_goods_yeji=isnull(sum( case when item='25' then yeji else 0 end ),0)              
			   from #yeji_result     group by compid ) as b
	where a.compid=b.compid
	  
	
	
	declare @sqltitle varchar(600)
	select @sqltitle = isnull(@sqltitle + '],[' , '') + parentcodekey+'Amt' from commoninfo where infotype='XMTJ' 
	set @sqltitle = '[' + @sqltitle + ']'
	
	exec ('select * from (select * from #prjchange_yeji_resultx ) a pivot (max(projectamt) for projecttype in (' + @sqltitle + ')) b order by compid')
	    

	drop table #m_d_consumeinfo                                      
	drop table #prj_cls                                                                 
	drop table #emp_dep                                  
	drop table #yeji_result                                  
	drop table #prjchange_yeji_resultx                             
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_satff_salary_daybyday]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_comp_satff_salary_daybyday]
(
	@compid			varchar(10)	,
	@datefrom		varchar(8)	,
	@dateto			varchar(8)
)
as
begin
	declare @targetcompid varchar(10)
	declare @tmpdate varchar(8)                                    
	declare @tmpenddate varchar(8)
	declare @targetinid		varchar(20)                                  
	set @tmpenddate = @datefrom                                    
	set @tmpdate = @datefrom 
	
	 delete a from staff_work_salary a,compchaininfo b where  curcomp= @compid  and compid=relationcomp and salary_date between @datefrom and   @dateto                             
   
    while (@tmpenddate <= @dateto)                                    
    begin                                    
	  --插入选择的门店编号到#diarialBill_byday_fromShops中                                        
		
		declare cur_each_comp cursor for
		select relationcomp from compchaininfo where curcomp= @compid  
		open cur_each_comp
		fetch cur_each_comp into @targetcompid
		while @@fetch_status=0
		begin
			exec upg_all_personal_comm_paymode @targetcompid,@tmpenddate,@tmpenddate,2 
			if(@tmpenddate=@dateto)
			begin
					create  table #emp_yeji_total_daybyday                                 
					(                                
						inid		varchar(20) null,  --员工内部编号   
						postation	varchar(10)	null,                                
						yeji		float  null, -- 业绩                                       
					) 
       				insert #emp_yeji_total_daybyday(inid,yeji)                                       
					select  person_inid,sum(isnull(staffyeji,0)) from allstaff_work_detail_daybyday 
					where compid=@targetcompid and srvdate between @datefrom and @dateto  
					and ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15')
					group by person_inid 
					update a set   a.postation=b.position 
					from   #emp_yeji_total_daybyday a,staffinfo b where a.inid=b.manageno
					
					declare cur_each_salaryOther cursor for
					select  inid from #emp_yeji_total_daybyday 
					where (postation='003' and ISNULL(yeji,0)>=25000 ) 
					or (postation='006' and ISNULL(yeji,0)>=50000 ) 
					or (postation='007' and ISNULL(yeji,0)>=70000 ) 
					or (postation='004' and ISNULL(yeji,0)>=50000 ) 
					open cur_each_salaryOther
					fetch cur_each_salaryOther into @targetinid
					while @@fetch_status=0
					begin
						exec upg_compute_comp_other_satff_salary_daybyday @targetcompid, @targetinid,@datefrom,@dateto
						fetch cur_each_salaryOther into @targetinid
					end
					close cur_each_salaryOther
					deallocate cur_each_salaryOther
					
					drop table #emp_yeji_total_daybyday                     
                
                  
    
			end
			fetch cur_each_comp into @targetcompid
		end
		close cur_each_comp
		deallocate cur_each_comp
		
		   
		execute upg_date_plus @tmpdate,1,@tmpenddate output                                    
		set @tmpdate = @tmpenddate                                    
    end 
    truncate table allstaff_work_detail_daybyday
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_tradedata]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_comp_tradedata]    
(
	@compid			varchar(10),		--门店号	
	@datefrom			varchar(10),	--起始日期
	@dateto			varchar(10)			--结束日期
)  
as
begin
	create table #comptrade   --门店营业临时表
	(
		compid				varchar(10)		null,	--门店编号
		compname			varchar(30)		null,	--门店名称
		tradecashamt		float			null,	--现金总收入
		tradebankamt		float			null,	--银行卡总收入
		tradefingeramt		float			null,	--指付通总收入
		tradeokcardamt		float			null,	--OK卡总收入
		tradetgcardamt		float			null,	--团购卡总收入
		tradeczcardamt		float			null,	--储值卡消费
		tradelccardamt		float			null,	--疗程卡消费
		tradecashdyamt		float			null,	--现金抵用券消费
		tradeprojdyamt		float			null,	--项目抵用券消费
		tradetmcardamt		float			null,	--条码卡消费
		trademrfakeamt		float			null,	--美容虚业绩
		trademffakeamt		float			null,	--美发虚业绩
		tradetrfakeamt		float			null,	--烫染虚业绩
		tradefgfakeamt		float			null,	--美甲虚业绩
		tradetotalamt		float			null,	--总需业绩
		traderealtotalamt	float			null,	--总需业绩
	)
	
	CREATE TABLE    #dpayinfo_billcount             --  单据--支付明细
	(
		paycompid		varchar(10)      NULL,   --公司编号
		paydate			varchar(10)      NULL,   --支付日期
		paybilltype		varchar(5)		 NULL,   --单据类别  SY 收银  SK  售卡  CZ 充值 ZK  转卡 HZ 合作项目 TK 退卡
		paymode			varchar(5)		 NULL,   --支付方式
		payamt			float			 NULL,   --支付金额
	)

	exec upg_compute_comp_trade_payinfo @compid,@datefrom,@dateto
	
	
	insert #comptrade(compid,compname,tradetotalamt,tradecashamt,tradebankamt,tradefingeramt,tradeokcardamt,tradetgcardamt,tradeczcardamt,
	tradelccardamt,tradecashdyamt,tradeprojdyamt,tradetmcardamt)
	select paycompid,compname,
	SUM(case when  paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
			 when  paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end ) ,
	SUM(case when  paymode='1' then payamt else 0 end ) ,
	SUM(case when  paymode='6' then payamt else 0 end ) ,
	SUM(case when  paymode='14' then payamt else 0 end ) ,
	SUM(case when  paymode='15' then payamt else 0 end ) ,
	SUM(case when  paymode='16' then payamt else 0 end ) ,
	SUM(case when paybilltype	in ('SY_P','SY_G1','SY_G2') and paymode in ('4','5')then payamt else 0 end ) ,
	SUM(case when paybilltype	in ('SY_P','SY_G1','SY_G2') and paymode='9' then payamt else 0 end ) ,
	SUM(case when paybilltype	in ('SY_P','SY_G1','SY_G2') and paymode='11' then payamt else 0 end ) ,
	SUM(case when paybilltype	in ('SY_P','SY_G1','SY_G2') and paymode='12' then payamt else 0 end ) ,
	SUM(case when paybilltype	in ('SY_P','SY_G1','SY_G2') and paymode='13' then payamt else 0 end ) 
	from #dpayinfo_billcount,companyinfo where paycompid=compno  group by paycompid,compname
	

	create table #cls_yeji_result_search(                              
		  compid varchar(10) not null,    
		  ddate varchar(10) null,                           
		  beaut_yeji float null,                              
		  hair_yeji float null,                              
		  foot_yeji float null,                              
		  finger_yeji float null,                              
		  total_yeji float null,                              
		  real_beaut_yeji float null,                              
		  real_hair_yeji float null,                              
		  real_foot_yeji float null,                              
		  real_finger_yeji float null,                              
		  real_total_yeji  float null                              
		 ) 
	--获取分类统计
	exec upg_compute_comp_classed_trade  @compid,@datefrom,@dateto,2

	update a set trademrfakeamt=beaut_yeji,trademffakeamt=hair_yeji,tradetrfakeamt=foot_yeji,tradefgfakeamt=finger_yeji,traderealtotalamt=real_total_yeji
	  from #comptrade a,#cls_yeji_result_search b where a.compid=b.compid

	select compid,compname,tradecashamt,tradebankamt,tradefingeramt,tradeokcardamt,tradetgcardamt,tradeczcardamt,tradelccardamt,
	tradecashdyamt,tradeprojdyamt,tradetmcardamt,trademrfakeamt,trademffakeamt,tradetrfakeamt,tradefgfakeamt,tradetotalamt,traderealtotalamt
	from #comptrade
	
	drop table #cls_yeji_result_search
	drop table #dpayinfo_billcount
	drop table #comptrade
end
GO
/****** Object:  StoredProcedure [dbo].[upg_compute_comp_classed_trade_daybyday]    Script Date: 01/14/2014 15:45:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[upg_compute_comp_classed_trade_daybyday]    
(
	@compid			varchar(10),		--门店号	
	@datefrom		varchar(10),		--起始日期
	@dateto			varchar(10)			--结束日期
)  
as
begin
	delete compclasstraderesult from compclasstraderesult,compchaininfo where curcomp=@compid and relationcomp=compid and ddate between @datefrom and @dateto
	create table #cls_yeji_result_search(                              
		  compid varchar(10) not null,    
		  ddate varchar(10) null,                           
		  beaut_yeji float null,                              
		  hair_yeji float null,                              
		  foot_yeji float null,                              
		  finger_yeji float null,                              
		  total_yeji float null,                              
		  real_beaut_yeji float null,                              
		  real_hair_yeji float null,                              
		  real_foot_yeji float null,                              
		  real_finger_yeji float null,                              
		  real_total_yeji  float null                              
		 ) 
 
	declare @tmpdate varchar(8)                                    
	declare @tmpenddate varchar(8)                                    
	set @tmpenddate = @datefrom                                    
	set @tmpdate = @datefrom                                    
    while (@tmpenddate <= @dateto)                                    
    begin                                    
	  --插入选择的门店编号到#diarialBill_byday_fromShops中                                        
		  
		declare @tocompid varchar(10)
		declare cur_each_comp cursor for
		select relationcomp from compchaininfo where curcomp=@compid 
		open cur_each_comp
		fetch cur_each_comp into @tocompid
		while @@fetch_status = 0
		begin
			exec upg_compute_comp_classed_trade	@tocompid,@tmpenddate,@tmpenddate,2
			fetch cur_each_comp into @tocompid
		end
		close cur_each_comp
		deallocate cur_each_comp                                  
		execute upg_date_plus @tmpdate,1,@tmpenddate output                                    
		set @tmpdate = @tmpenddate                                    
    end 
    insert compclasstraderesult(compid,ddate,beautyeji,hairyeji,footyeji,fingeryeji,totalyeji,realbeautyeji,realhairyeji,realfootyeji,realfingeryeji,realtotalyeji)
		    select compid,ddate,beaut_yeji,hair_yeji,foot_yeji,finger_yeji,total_yeji,real_beaut_yeji,real_hair_yeji,real_foot_yeji,real_finger_yeji,real_total_yeji from #cls_yeji_result_search	
    drop table #cls_yeji_result_search
end
GO
