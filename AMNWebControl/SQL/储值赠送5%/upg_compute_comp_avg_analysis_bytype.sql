alter procedure upg_compute_comp_avg_analysis_bytype       
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
 seardate   varchar(40)  null,                            
 compid    varchar(10)  null,                            
 compname   varchar(30)  null,                          
 totalxuyeji   float   null, --虚业绩                        
 totalshiyeji  float   null, --实业绩                        
 costcardrate  float   null, --耗卡率                        
 memcount   float   null, --会员数                        
 goodmemcount  float   null, --有效会员数                        
 addmemcount   float   null, --新增会员数                        
 addbeatypromems  float   null, --新增美容疗程数                        
 addhairpromems  float   null, --新增美发疗程数                        
 addpromems   float   null, --新增疗程数                        
 beatycount   float   null, --美容会员数                         
 beatygoodcount  float   null, --美容有效会员数                
 beatygoodcountl  float   null, --上月美容有效会员数                       
 beatyallcountl  float   null, -- 美容类两月都来会员数                          
 beatygoodrate  float   null, --美容会员保有率                         
 haircount   float   null, --美发会员数                         
 hairgoodcount  float   null, --美发有效会员数                           
  hairgoodrate  float   null, --美发会员保有率                
 hairgoodcountl  float   null, --上月美发有效会员数                         
 hairallcountl  float   null, -- 美发类两月都来会员数                          
 leavelcount   float   null, --离职人数                
 leavelcorecount  float   null, --核心员工离职人数                         
 factdatefrom  varchar(10)  null, --实际起始日期                  
 factdateto   varchar(10)  null,   --实际结束日期              
               
 factdatefroml  varchar(10)  null, --前月起始日期                  
 factdatetol   varchar(10)  null,   --前月实际结束日期       
 totalxuyejizb  varchar(10)  null, --虚业绩指标                  
 costcardratezb  varchar(10)  null,  --耗卡率 指标                    
 goodmemcountzb  varchar(10)  null,  --有效会员数  指标                  
 addpromemszb  varchar(10)  null,  --新增疗程数  指标                   
 beatygoodratezb  varchar(10)  null,  --美容会员保有率 指标                   
 hairgoodratezb  varchar(10)  null,  --美发会员保有率  指标                  
 leavelcountzb  varchar(10)  null,  --离职人数指标                   
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
  compid    varchar(10)  null,            
  ddate    varchar(10)  null,                  
  beaut_yeji   float   null,                                                                  
  hair_yeji   float   null,                           
  trh_yeji   float   null,                                                          
  total_yeji   float   null,                        
  costbycard   float   null,                        
  realytotal_yeji  float   null,                       
  cardsale_yeji  float   null       
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
  cardvesting   varchar(10)     NULL,   --卡归属门店       
  cardno    varchar(20)  NULL, --卡号       
  cardtype   varchar(10)  NULL,  --卡种(会员卡类别设定)       
  salecarddate  varchar(8)  NULL    ,   --售卡日期      
  cutoffdate   varchar(8)  NULL    ,   --截止有效日期      
  cardstate   int    NULL       --状态(未销售, 销售未开卡, 正常使用中, 挂失转卡,越期可续卡 , 越期作废卡)      
        
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
   cscompid   varchar(10)     NULL,   --公司编号                                        
   csbillid   varchar(20)  NULL,   --消费单号                                     
   financedate  varchar(8)  NULL,   --帐务日期       
   cscardno   varchar(20)  NULL,   --会员卡号      
   csinfotype  int             NULL, --消费类型  1 项目  2 产品       
   csitemno   varchar(20)     NULL,   --项目代码                                        
   csitemamt   float           NULL,   --金额       
   cspaymode   varchar(5)  NULL, --支付方式                                           
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
  and cspaymode in ('4','9','17')      
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
   compid   varchar(10)  null,                                                          
   cardid   varchar(20)  null, --卡号                
   xiangmu   varchar(20)  null, --项目编号                 
   zhifu   int    null, --支付方式               
   factdatefrom varchar(10)  null,                  
   targgettodate varchar(10)  null,                                                      
  )                
  --上月消费卡号                
  insert #abc_card (compid,cardid,xiangmu,zhifu,factdatefrom,targgettodate)                
  select cscompid,cscardno,csitemno,cspaymode,factdatefrom,factdateto from #m_d_consumeinfo,#comp_analysis_avg with(nolock)                                     
  where financedate between factdatefroml  and factdatetol and cscompid=compid                
                    
                    
  create table #abc_card01                
  (                                                          
   compid   varchar(10)  null,                                                          
   cardid   varchar(20)  null,--卡号                
   xiangmu   varchar(20)  null,--项目编号                
   zhifu   int    null,--支付方式               
   factdatefrom varchar(10)  null,                  
   targgettodate varchar(10)  null,                                   
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
   print 11111111    
 --新增疗程数                        
 update a set a.addbeatypromems=d.addbeatypromems,addhairpromems=d.addhairpromems,addpromems=d.addpromems                        
 from #comp_analysis_avg a,                        
   ( select e.changecompid,c.factdatefrom,addbeatypromems=convert(numeric(20,2),sum(case when prjtype='4' then isnull(changeprocount,0)/ISNULL(ysalecount,1) end )),                        
   addhairpromems=convert(numeric(20,2),sum(case when prjtype IN ('3','6') then   isnull(changeprocount,0)/ISNULL(ysalecount,1)  end)),                        
   addpromems=convert(numeric(20,2),SUM(isnull(changeprocount,0)/ISNULL(ysalecount,1)))                        
 from mproexchangeinfo  e ,dproexchangeinfo f,#comp_analysis_avg c ,projectinfo,sysparaminfo g                        
 where  e.changecompid=f.changecompid and e.changebillid=f.changebillid and e.changecompid=c.compid  
   and changedate  between c.factdatefrom  and c.factdateto       
   and g.compid=e.changecompid and g.paramid='SP059' and prjmodeId=g.paramvalue and prjno=changeproid and ISNULL(ysalecount,1)>0                   
 group by e.changecompid,c.factdatefrom ) d                        
 where a.compid=d.changecompid  and a.factdatefrom=d.factdatefrom          
    print 11111111    
 --计算离职指标      
 create table #leavelstaff          
 (          
  compid  varchar(10) null,          
  inid  varchar(20) null,          
  inserdate varchar(10) null,          
  leveldate varchar(10) null,          
  postion  varchar(10) null,          
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