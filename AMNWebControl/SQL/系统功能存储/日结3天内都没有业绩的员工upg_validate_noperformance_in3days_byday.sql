if exists(select 1 from sysobjects where type='P' and name='upg_validate_noperformance_in3days_byday')
	drop procedure upg_validate_noperformance_in3days_byday
go
create procedure upg_validate_noperformance_in3days_byday   
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

