alter procedure upg_compute_comp_trade_payinfo   
(  
 @compid   varchar(10),  --门店号   
 @datefrom  varchar(10), --起始日期  
 @dateto   varchar(10)   --结束日期  
)    
as  
begin  
  
 --收银  
 insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)  
  select a.cscompid,financedate,'SY_P',cspaymode,sum(isnull(csitemamt,0))  
 from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),compchaininfo  
 where a.cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto  
   and a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,1)=1   
   --and ISNULL(backcsbillid,'')='' and ISNULL(backcsflag,0)=0
 group by a.cscompid,cspaymode,financedate  
   
 insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)  
 select a.cscompid,financedate,'SY_G1',cspaymode,sum(isnull(csitemamt,0))  
 from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),compchaininfo  
 where  a.cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto  
   and a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,1)=2 and ISNULL(abs(saletype),1)=1 
    -- and ISNULL(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 
 group by a.cscompid,cspaymode,financedate  
   
 insert #dpayinfo_billcount(paycompid,paydate,paybilltype,paymode,payamt)  
 select a.cscompid,financedate,'SY_G2',cspaymode,sum(isnull(csitemamt,0))  
 from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),compchaininfo  
 where  a.cscompid=relationcomp and curcomp=@compid and financedate between @datefrom and @dateto  
   and a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,1)=2 and ISNULL(abs(saletype),1)=2  
     and ISNULL(backcsbillid,'')='' and ISNULL(backcsflag,0)=0
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