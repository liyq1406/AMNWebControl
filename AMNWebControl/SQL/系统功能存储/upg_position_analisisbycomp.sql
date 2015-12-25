
if exists(select 1 from sysobjects where type='P' and name='upg_position_analisisbycomp')
	drop procedure upg_position_analisisbycomp
go
CREATE procedure upg_position_analisisbycomp      
(        
  @compid        varchar(10),       --公司编号    
  @type          int,               --查询类型 1,离职 2，入职 3 本店调动  ，4,跨店调动    
  @fromdate      varchar(18),       --开始日期       
  @todate        varchar(18)        --结束日期        
)        
as -- 每月人事异动数据分析报表    
begin     
 create table #temp (          
     
      compid        varchar(10)     null,       -- 门店编号    
      compname      varchar(40)     null,       -- 门店名称        
      oldposid      varchar(16)     null,       -- 原职位编号 
      oldposcount   int				null,       -- 职位数量
      totalcount	int				null,       -- 总数量
  )    

 if @type=1 or @type=2    
 begin    
  -- 没有统计辞退申请和重回公司申请    
  insert into #temp(compid,oldposid)    
  select appchangecompid,beforepostation    
  from staffchangeinfo,staffinfo,compchaininfo     
  where changetype=@type     
   and staffmangerno=manageno 
   and appchangecompid=relationcomp
   and curcomp=@compid    
   and validatestartdate between @fromdate and @todate  
   and billflag=8  
 end    
 if @type=3 or @type=4    
 begin    
      
    if @type=3     
    begin     
    
		  insert into #temp(compid,oldposid)    
		  select appchangecompid,beforepostation    
		  from staffchangeinfo,staffinfo,compchaininfo     
		  where changetype=5     
		   and staffmangerno=manageno 
		   and appchangecompid=relationcomp
		   and curcomp=@compid    
		   and validatestartdate between @fromdate and @todate  
		   and billflag=8  
    end    
	if @type=4     
	begin     
	      insert into #temp(compid,oldposid)    
		  select appchangecompid,beforepostation    
		  from staffchangeinfo,staffinfo,compchaininfo     
		  where changetype=6     
		   and staffmangerno=manageno 
		   and appchangecompid=relationcomp
		   and curcomp=@compid    
		   and validatestartdate between @fromdate and @todate  
		   and billflag=8  
	end 
 end    
 
 update #temp set compname=b.compname
 from #temp a,companyinfo b
 where compid=compno
 
 update a set oldposcount=cnt
 from #temp a,(
 select compid,oldposid,cnt=count(1) from #temp    
 group by compid,oldposid ) b
 where a.compid=b.compid and a.oldposid=b.oldposid   
 
  update a set totalcount=b.oldposcount
 from #temp a,(
 select compid,oldposcount=sum(isnull(oldposcount,0)) from #temp    
 group by compid ) b
 where a.compid=b.compid   
 

	declare @sqltitle varchar(1000)  
	select @sqltitle = isnull(@sqltitle + '],[' , '') + parentcodekey+'Count' from commoninfo where infotype='GZGW'   
	set @sqltitle = '[' + @sqltitle + ']'  
 
    
  update #temp set oldposid=oldposid+'Count'
  exec ('select * from (select * from #temp ) a pivot (max(oldposcount) for oldposid in (' + @sqltitle + ')) b order by compid')  

 drop table #temp
end
go
