--exec upg_create_trade_dailybillcount '001','20130801','20130831'
if exists(select 1 from sysobjects where type='P' and name='upg_create_trade_dailybillcount')
	drop procedure upg_create_trade_dailybillcount
go
create procedure upg_create_trade_dailybillcount    
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
go


			
		