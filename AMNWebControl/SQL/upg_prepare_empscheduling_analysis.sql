alter procedure upg_prepare_empscheduling_analysis(                  
 @compid	varchar(10),	-- 公司别                  
 @fromdate  varchar(10),	-- 开始日期 
 @empid		varchar(20)		--员工编号
)     
as        
begin   
	set @fromdate=@fromdate+'01'
	declare @todate varchar(10)
	declare @tmpdate datetime
	declare @year varchar(4) --年份
	declare @month varchar(2)--月份
	declare @day varchar(2) --日份
	set  @tmpdate=DateAdd(m,1,@fromdate)
	set  @tmpdate=DateAdd(day,-1,@tmpdate)
	set @year = year(@tmpdate)
	set @month = month(@tmpdate)
	set @day = day(@tmpdate)
	if(len(@month)=1)
	set @month = '0'+@month
	if(len(@day)=1)
		set @day = '0'+@day
    set @todate=@year+@month+@day
    
     create table #schedulingresult  
	 (  
		compno		varchar(10)		null,
		workdate	varchar(10)		null,  
		weekdays	varchar(10)		null,  
		ccount		int				null
	 ) 
	  
		declare @tmpenddate varchar(8)    
		set @tmpenddate = @fromdate  
		while (@tmpenddate <= @todate)                                            
		begin    
			insert #schedulingresult(compno,workdate,weekdays)  
			values(@compid,@tmpenddate,case (datepart(dw,cast(@tmpenddate as datetime)))   
				when 1  then '星期日'  
				when 2 then '星期一'  
				when 3  then '星期二'  
				when 4  then '星期三'  
				when 5  then '星期四'  
				when 6  then '星期五'  
				when 7  then '星期六'  
				end)  
			execute upg_date_plus @tmpenddate,1,@tmpenddate output           
		end  
    update #schedulingresult set ccount=ISNULL((select count(schedulemp) from compschedulinfo where compno=@compid and scheduldate=workdate and (@empid=schedulemp or @empid='' ) ),0)
	select compno,workdate,weekdays,ccount from #schedulingresult order by workdate
	drop table #schedulingresult
end  
 
 go
  exec upg_prepare_empscheduling_analysis '002','201404',''