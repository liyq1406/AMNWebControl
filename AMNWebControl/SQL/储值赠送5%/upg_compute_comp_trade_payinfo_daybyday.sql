alter procedure upg_compute_comp_trade_payinfo_daybyday     
(    
 @compid   varchar(10), --结算门店    
 @datefrom  varchar(10), --起始日期    
 @dateto   varchar(10)  --结束日期    
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
 union all select salecompid,saledate,paycode=firstpaymode,payamt=sum(isnull(firstpayamt,0))     
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