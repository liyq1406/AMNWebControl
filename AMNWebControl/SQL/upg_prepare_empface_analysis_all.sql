alter procedure upg_prepare_empface_analysis_all(                  
 @compid  varchar(10), -- 公司别                  
 @fromdate  varchar(10), -- 开始日期                  
 @todate  varchar(10) -- 截至日期  
)     
as        
begin   
 CREATE table #staffkqrecordinfo   
 (  
  compid    varchar(10)    NULL, --考勤门店  
  machineid   varchar(20)    NULL,   --考勤机号  
  personid   varchar(10)    NULL, --考勤手指号  
  ddate    varchar(10)    NULL, --考勤日期  
  ttime    varchar(10)    NULL, --考勤时间  
 )  
 

   
	if(@compid='001') --指纹号为工号  
	begin  
			insert #staffkqrecordinfo(compid,machineid,personid,ddate,ttime)  
			select @compid,machineid,personid,ddate,ttime
			from  staffkqrecordinfo a where ddate between @fromdate and @todate and '99001'=machineid 
			
			update a set personid=fingerno from #staffkqrecordinfo a,staffinfo b  
			where a.compid='001' and personid=CAST(staffno as int) and compno='001'  
			
			
			update #staffkqrecordinfo set personid=22091 where machineid='99001' and personid='26808'       
			update #staffkqrecordinfo set personid=25976 where machineid='99001' and personid='26806'       
			update #staffkqrecordinfo set personid=22093 where machineid='99001' and personid='26818'        
			update #staffkqrecordinfo set personid=136903 where machineid='99001' and personid='10905'        
			update #staffkqrecordinfo set personid=137230 where machineid='99001' and personid='103016'       
			update #staffkqrecordinfo set personid=26475 where machineid='99001' and personid='10515'      
			update #staffkqrecordinfo set personid=21031 where machineid='99001' and personid='10302'  
    
    
	end  
	else
	begin
		
		insert #staffkqrecordinfo(compid,machineid,personid,ddate,ttime)  
		select @compid,machineid,personid,ddate,ttime
		from  staffkqrecordinfo a,sysparaminfo b  where ddate between @fromdate and @todate  
		and paramid='SP072'  and b.compid=@compid and paramvalue=machineid 
	end
   
   
 create table #faceresult  
 (  
  workdate		varchar(10)  null,  
  weekdays		varchar(10)  null,  
  compid		varchar(10)  null,  
  compname		varchar(30)  null,  
  staffno		varchar(20)  null, 
  fingerno		int				null, 
  staffname		varchar(20)  null,  
  ontime		varchar(20)  null,  
  downtime		varchar(20)  null  
 )  
   
 declare @tmpenddate varchar(8)    
 set @tmpenddate = @fromdate  
 while (@tmpenddate <= @todate)                                            
    begin    
   insert #faceresult(staffno,staffname,fingerno,workdate,weekdays)  
   select staffno,staffname,fingerno,@tmpenddate,case (datepart(dw,cast(@tmpenddate as datetime)))   
       when 1  then '星期日'  
       when 2 then '星期一'  
       when 3  then '星期二'  
       when 4  then '星期三'  
       when 5  then '星期四'  
       when 6  then '星期五'  
       when 7  then '星期六'  
       end from staffinfo a,#staffkqrecordinfo b
       where a.fingerno=b.personid
       group by staffno,staffname,a.fingerno
   execute upg_date_plus @tmpenddate,1,@tmpenddate output           
 end  
   

   
 update a set ontime=ttime,compid=d.compno,compname=d.compname  
 from #faceresult a,(  
 select  row_number() over( PARTITION BY ddate,personid order by min(ISNULL(ttime,0)) asc)  fid,compno,compname,ddate,personid,ttime  
 from  companyinfo b,#staffkqrecordinfo c where b.compno=c.compid and ttime<'140000' group by compno,compname,personid,ddate,ttime) d  
 where a.workdate=d.ddate and d.fid=1  and a.fingerno=d.personid
   
 update a set downtime=ttime,compid=d.compno,compname=d.compname  
 from #faceresult a,(  
 select  row_number() over( PARTITION BY ddate,personid order by max(ISNULL(ttime,0)) desc)  fid,compno,compname,ddate,personid,ttime  
 from  companyinfo b,#staffkqrecordinfo c where b.compno=c.compid and ttime>'140000'  group by compno,compname,personid,ddate,ttime) d  
 where a.workdate=d.ddate and d.fid=1   and a.fingerno=d.personid
   
   
 select department=c.parentcodevalue,workdate,weekdays,compid=isnull(compid,''),compname=isnull(compname,''),a.staffno,b.staffname,ontime=isnull(ontime,''),downtime=isnull(downtime,'')
 from #faceresult a,staffinfo b,commoninfo c
 where a.fingerno=b.fingerno  and c.infotype='BMZL' and c.parentcodekey=department
  order by  department, a.staffno,workdate
 drop table #staffkqrecordinfo  
 drop table #faceresult  
end  
  