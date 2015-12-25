alter procedure upg_compute_comp_detial_trade     
(    
 @compid   varchar(10),  --门店号     
 @datefrom   varchar(10), --起始日期    
 @dateto   varchar(10)   --结束日期    
)      
as    
begin    
 --获取门店支付信息    
 CREATE TABLE    #dpayinfo               --  单据--支付明细    
 (    
  paycompid  varchar(10)      NULL,   --公司编号    
  paydate   varchar(10)      NOT NULL,   --结算日期    
  paybilltype  varchar(5)   NULL,   --单据类别  SY 收银  SK  售卡  CZ 充值 ZK  转卡 HZ 合作项目 TK 退卡    
  paymode   varchar(5)   NULL,   --支付方式    
  payamt   float    NULL,   --支付金额    
 )    
 create nonclustered index idx_dpayinfoday_paydate on #dpayinfo(paycompid,paydate)     
 ----------------------------需要数据库日结-------------upg_compute_comp_trade_payinfo_daybyday------------    
 insert #dpayinfo(paycompid,paydate,paybilltype,paymode,payamt)    
 select paycompid,paydate,paybilltype,paymode,payamt from payinfodaybyday,compchaininfo where  curcomp=@compid and paycompid=relationcomp     
 --计算门店营业情况    
 delete detial_trade_byday_fromshops from payinfodaybyday,compchaininfo where curcomp=@compid and shopId=relationcomp and  dateReport between @datefrom and @dateto    
 create table #detial_trade_byday_fromShops      --用于结算查询门店的日结信息                                              
 (                                                                                  
   shopId      varchar(10)   NULL, --门店编号                                               
   shopName     varchar(20)   NULL, --门店名称                                               
   dateReport     varchar(8)   NULL, --结账日期                                               
   total      float    NULL, --总收入     
                                                                        
   cashService    float    NULL, --现金服务                                                     
   cashProd     float    NULL, --现金产品                                                     
   cashCardTrans    float    NULL, --现金(卡异动)                                        
   cashBackCard    float    NULL, --现金(退卡)                                                  
   cashTotal     float    NULL, --现金合计(扣除现金退卡)                                                    
                                                                                                       
   creditService    float    NULL, --银行卡服务                                                    
   creditProd     float    NULL, --银行卡产品                                                    
   creditTrans    float    NULL, --银行卡(卡异动)                                          
   creditBackCard    float    NULL, ---银行卡(退卡)                                               
   creditTotal    float    NULL, --银行卡合计(扣除银行卡退卡)                                                    
                                                                                                        
   checkService    float    NULL, --支票服务                                                     
   checkProd     float    NULL, --支票产品                                                     
   checkTrans     float    NULL, --支票(卡异动)                                           
   checkBackCard    float    NULL, --支票(退卡)                                               
   checkTotal     float    NULL, --支票合计(扣除支票退卡)                                       
                                     
   zftService     float    NULL, --指付通服务                                                     
   zftProd     float    NULL, --指付通产品                                                     
   zftTrans     float    NULL, --指付通(卡异动)                                           
   zftBackCard    float    NULL, --指付通(退卡)                                               
   zftTotal     float    NULL, --指付通合计(扣除支付通退卡)                                     
                                     
   ockService     float    NULL, --OK卡服务                                                     
   ockkProd     float    NULL, --OK卡产品                                                     
   ockTrans     float    NULL, --OK卡(卡异动)                                             
   ockTotal     float    NULL, --OK卡合计    
       
   tgkService     float    NULL, --团购卡服务                                                     
   tgkkProd     float    NULL, --团购卡产品                                       
   tgkTrans     float    NULL, --团购卡(卡异动)                                                      
   tgkTotal     float    NULL, --团购卡合计             
       
   totalCardTrans    float    NULL, --卡异动(卡销售,卡充值,卡升级)     
                                 
   cashchangeSale    float    NULL, --现金兑换销售                              
   bankchangeSale    float    NULL, --银行卡兑换销售     
        
   cashbytmkSale    float    NULL, --现金条码卡销售                                  
   bankbytmkSale    float    NULL, --银行卡条码卡销售                                  
   checkbytmkSale    float    NULL , --支票条码卡销售                                  
   fingerbytmkSale   float    NULL, --指付通条码卡销售                                  
   okpqypwybytmkSale   float    NULL, --OK卡条码卡销售    
        
   cashhezprj     float    NULL, --现金合作项目                               
   bankhezprj     float    NULL, --银行卡合作项目                      
   sumcashhezprj    float    NULL, --现金合作项目(店内支付的现金)                                        
                                                                                                        
                                                        
                                                                                                                   
   cardSalesServices          float    NULL, --销卡服务                                                     
   cardSalesprod    float    NULL, --销卡产品       
    staffsallprod    float    NULL, --员工产品                  
   acquisitionCardServices float    NULL, --收购转卡服务     
   costpointTotal    float    NULL, --积分服务       
   cashdyService    float    NULL,   --现金抵用券服务                                  
   prjdyService    float    NULL,   --项目抵用券服务    
   tmkService     float    NULL,   --条码卡 服务 
   tmksendService   float    NULL,   --赠送条码卡 服务   
   manageSigning    float    NULL, --经理签单                                             
   payOutRegister    float    NULL, --支出登记     
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
 --不包含原价卡
 ----销卡服务          
 --update #detial_trade_byday_fromShops            
 --  set cardSalesServices=ISNULL((select sum( isnull(payamt,0) )    
 --       from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode  in ('4','9','17') and isnull(paybilltype,'')='SY_P' ),0)    
 ----销卡产品          
 --update #detial_trade_byday_fromShops            
 --  set cardSalesprod=ISNULL((select sum( isnull(payamt,0) )    
 --       from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode in ('4','17') and isnull(paybilltype,'') in ('SY_G1','SY_G2') ),0)    
     
   --销卡服务(不包含原价卡)
   update #detial_trade_byday_fromShops            
   set cardSalesServices=ISNULL((select sum( isnull(b.csitemamt,0) )    
        from mconsumeinfo a,dconsumeinfo b,compchaininfo c
        where a.cscompid=b.cscompid and a.csbillid=b.csbillid 
        and a.financedate=dateReport and curcomp= shopId and  relationcomp=a.cscompid  and b.cspaymode in ('4','9','17')  
        and isnull(b.csinfotype,1)=1 and isnull(a.cscardtype,'')<>'ZK'  ),0)   
           
   --销卡产品(不包含原价卡)     
   update #detial_trade_byday_fromShops            
   set cardSalesprod=ISNULL((select sum( isnull(b.csitemamt,0) )    
        from mconsumeinfo a,dconsumeinfo b,compchaininfo c
        where a.cscompid=b.cscompid and a.csbillid=b.csbillid 
        and a.financedate=dateReport and curcomp= shopId and  relationcomp=a.cscompid  and b.cspaymode in ('4','9','17') 
        and isnull(b.csinfotype,1)=2 and isnull(a.cscardtype,'')<>'ZK' ),0)  
        
           
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
 --update #detial_trade_byday_fromShops            
 --  set tmkService=ISNULL((select sum( isnull(payamt,0) )    
 --       from payinfodaybyday,compchaininfo where paydate=dateReport and curcomp= shopId and  relationcomp=paycompid  and paymode='13' and isnull(paybilltype,'')='SY_P' ),0)    
  
  --销售条码卡服务
  update #detial_trade_byday_fromShops            
   set tmkService=ISNULL((select sum( isnull(b.csitemamt,0) )    
        from mconsumeinfo a,dconsumeinfo b,compchaininfo c,nointernalcardinfo d
        where a.cscompid=b.cscompid and a.csbillid=b.csbillid 
        and a.financedate=dateReport and curcomp= shopId and  relationcomp=a.cscompid  and b.cspaymode='13'  
        and isnull(a.tiaomacardno,'')=cardno and ISNULL(cardtype,0)=2 and ISNULL(entrytype,0)=0 ),0)    
     
     update #detial_trade_byday_fromShops            
   set tmksendService=ISNULL((select sum( isnull(b.csitemamt,0) )    
        from mconsumeinfo a,dconsumeinfo b,compchaininfo c,nointernalcardinfo d
        where a.cscompid=b.cscompid and a.csbillid=b.csbillid 
        and a.financedate=dateReport and curcomp= shopId and  relationcomp=a.cscompid  and b.cspaymode='13'  
        and isnull(a.tiaomacardno,'')=cardno and ISNULL(cardtype,0)=2 and ISNULL(entrytype,0)=1 ),0)   
           
  --经理签单服务     
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
   cashhezprj,bankhezprj,sumcashhezprj,cardsalesservices, cardsalesprod,staffsallprod,acquisitionCardServices,costpointTotal,cashdyService,prjdyService,tmkService,tmksendservice,    
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
   cardSalesServices, cardSalesprod,staffsallprod,acquisitionCardServices,costpointTotal,cashdyService,prjdyService,tmkService,tmksendService,    
   manageSigning, payOutRegister                          
         from #detial_trade_byday_fromShops     
     
 drop table #dpayinfo    
 drop table #detial_trade_byday_fromShops    
end 