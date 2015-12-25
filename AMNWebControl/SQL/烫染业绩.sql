select √≈µÍ±‡∫≈=compid,√≈µÍ√˚≥∆=compname, 
ÃÃ»æ–È“µº®=convert(numeric(20,1),SUM(ISNULL(footyeji,0))),
ÃÃ»æ µ“µº®=convert(numeric(20,1),SUM(ISNULL(realfootyeji,0))),
Õ∑∆§∂“ªªΩ∂Ó=convert(numeric(20,1),ISNULL(changeproamt,0))
  from compclasstraderesult,companyinfo  ,
    
  where ddate between '20140401' and '20140430' and compid=compno and compid=ff.changecompid
  group by compid,compname,ISNULL(changeproamt,0)
  order by compid
  
 
 select √≈µÍ±‡∫≈,√≈µÍ√˚≥∆,ÃÃ»æ–È“µº®,ÃÃ»æ µ“µº®,Õ∑∆§∂“ªªΩ∂Ó=convert(numeric(20,1),ISNULL(changeproamt,0)) from (
select √≈µÍ±‡∫≈=compid,√≈µÍ√˚≥∆=compname, 
ÃÃ»æ–È“µº®=convert(numeric(20,1),SUM(ISNULL(footyeji,0))),
ÃÃ»æ µ“µº®=convert(numeric(20,1),SUM(ISNULL(realfootyeji,0)))
  from compclasstraderesult,companyinfo 
  where ddate between '20140401' and '20140430' and compid=compno 
  group by compid,compname) gg
  left join (select  b.changecompid,changeproamt=SUM(ISNULL(b.changeproamt,0)) from mproexchangeinfo a,dproexchangeinfo b,projectnameinfo c
  where a.changebillid=b.changebillid and a.changecompid=b.changecompid
  and a.financedate  between '20140401' and '20140430'
  and b.changeproid=c.prjno and c.prjreporttype='06'
  group by  b.changecompid ) ff  on gg.√≈µÍ±‡∫≈=ff.changecompid
  order by √≈µÍ±‡∫≈
  
 
 
