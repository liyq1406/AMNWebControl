if exists(select 1 from sysobjects where type='P' and name='upg_analysis_system_shop_rijie')
	drop procedure upg_analysis_system_shop_rijie
go
CREATE procedure upg_analysis_system_shop_rijie                        
(                        
 @compid	varchar(10),                        
 @mmonth	varchar(6)                      
)  
as  
begin  

     delete jsanalysisresult where compno=@compid and mmonth=@mmonth
     declare @fromdate varchar(10)
     declare @todate varchar(10)
     
     declare @tmpdate datetime
	 declare @year varchar(4) --年份
	 declare @month varchar(2)--月份
	 declare @day varchar(2) --日份 
	 
     set @fromdate=@mmonth+'01'
     set @tmpdate = dateadd(MONTH,1,convert(datetime,@fromdate))
     set @tmpdate = dateadd(day,-1,@tmpdate)
	 set @year = year(@tmpdate)
	 set @month = month(@tmpdate)
	 set @day = day(@tmpdate)
	 if(len(@month)=1)
		set @month = '0'+@month
	 if(len(@day)=1)
		set @day = '0'+@day
	 set @todate=@year+@month+@day
	
	insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	select @compid,@mmonth,1,'总业绩',convert(numeric(20,1),SUM(ISNULL(totalyeji,0)))  
    from compclasstraderesult where compid=@compid and  ddate between @fromdate and @todate 
    group by SUBSTRING(ddate,1,6)
  
	insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	select @compid,@mmonth,2,'美发虚业绩',convert(numeric(20,1),SUM(ISNULL(hairyeji,0)))  
    from compclasstraderesult where compid=@compid and  ddate between @fromdate and @todate 
    group by SUBSTRING(ddate,1,6)
    
    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	select @compid,@mmonth,3,'美容虚业绩',convert(numeric(20,1),SUM(ISNULL(beautyeji,0)))  
    from compclasstraderesult where compid=@compid and  ddate between @fromdate and @todate 
    group by SUBSTRING(ddate,1,6)
    
    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	select @compid,@mmonth,4,'美容营业额占比',convert(numeric(20,4),SUM(ISNULL(beautyeji,4))/SUM(ISNULL(totalyeji,0)))
    from compclasstraderesult where compid=@compid and  ddate between @fromdate and @todate   and isnull(realtotalyeji,0)>0 
    group by SUBSTRING(ddate,1,6)
    
    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	select @compid,@mmonth,5,'总实业绩',convert(numeric(20,4),SUM(ISNULL(realtotalyeji,4)))
    from compclasstraderesult where compid=@compid and  ddate between @fromdate and @todate   
    group by SUBSTRING(ddate,1,6)
    
    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	select @compid,@mmonth,6,'耗卡率(储值销卡/卡异动)',convert(numeric(20,4),SUM(ISNULL(cardsalesservices,1))/ISNULL(sum(ISNULL(totalcardtrans,0)),0))  
    from detial_trade_byday_fromshops where shopId=@compid and  dateReport between @fromdate and @todate and  ISNULL(totalcardtrans,0)>0 
    group by SUBSTRING(dateReport,1,6)
    
     insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	select @compid,@mmonth,7,'美容部消耗占比 ',convert(numeric(20,4),SUM(ISNULL(realbeautyeji,4))/SUM(ISNULL(realtotalyeji,0)))  
    from compclasstraderesult where compid=@compid and  ddate between @fromdate and @todate    and isnull(realtotalyeji,0)>0 
    group by SUBSTRING(ddate,1,6)
    
    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	select @compid,@mmonth,9,'美发实业绩[包含美发产品]',convert(numeric(20,4),SUM(ISNULL(realhairyeji,4)))
    from compclasstraderesult where compid=@compid and  ddate between @fromdate and @todate   
    group by SUBSTRING(ddate,1,6)
    
      create table  #m_dconsumeinfo
   (
		cscompid		varchar(10)     NULL,   --公司编号
		csbillid		varchar(20)	    NULL,   --消费单号
		financedate		varchar(8)		NULL    ,   --帐务日期
		backcsflag		int				NULL    ,   --是否已经返销: 0-没有返销 1--已经返销
		backcsbillid	varchar(20)		NULL    ,   --返销单号
		csitemno		varchar(20)     NULL,		--项目/产品代码
		csitemunit		varchar(5)      NULL,		--单位
		csitemcount		float           NULL,		--数量
		csitemamt		float           NULL,		--金额
		cspaymode		varchar(5)		NULL,		--支付方式
   )
   
   insert #m_dconsumeinfo(cscompid,csbillid,financedate,backcsflag,backcsbillid,csitemno,csitemunit,csitemcount,csitemamt,cspaymode)
   select a.cscompid,a.csbillid,financedate,backcsflag,backcsbillid,csitemno,csitemunit,csitemcount,csitemamt,cspaymode
   from mconsumeinfo a,dconsumeinfo b
   where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between @fromdate and @todate and a.cscompid=@compid
   
    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	select @compid,@mmonth,8,'总客单数',count(distinct csbillid)
    from #m_dconsumeinfo where cscompid=@compid and  financedate between @fromdate and @todate   
    group by SUBSTRING(financedate,1,6)
    
    
      insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	  select @compid,@mmonth,10,'洗剪吹消耗',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
		from #m_dconsumeinfo b,projectnameinfo c  
		where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('01')  
		and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
		group by  SUBSTRING(b.financedate,1,6)  
    
    
      insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	  select @compid,@mmonth,11,'烫发消耗',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
		from #m_dconsumeinfo b,projectnameinfo c  
		where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('03')  
		and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
		group by  SUBSTRING(b.financedate,1,6)  
		
      insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	  select @compid,@mmonth,12,'染发消耗',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
		from #m_dconsumeinfo b,projectnameinfo c  
		where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('02')  
		and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
		group by  SUBSTRING(b.financedate,1,6) 
		
		insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	  select @compid,@mmonth,13,'护理消耗',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
		from #m_dconsumeinfo b,projectnameinfo c  
		where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('04','05','07','14')  
		and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
		group by  SUBSTRING(b.financedate,1,6)   
		
		
		insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	    select @compid,@mmonth,14,'头皮消耗',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
		from #m_dconsumeinfo b,projectnameinfo c  
		where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('06')  
		and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
		group by  SUBSTRING(b.financedate,1,6) 
		
		
		insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	    select @compid,@mmonth,15,'洗剪吹项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('01') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
	    from #m_dconsumeinfo b,projectnameinfo c  
	    where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
	     and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
	     and b.cscompid=@compid and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
	     group by  SUBSTRING(b.financedate,1,6)
	     
	     insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	     select @compid,@mmonth,16,'烫发消耗占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('03') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
		  from #m_dconsumeinfo b,projectnameinfo c  
		  where  b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
		   and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
		  and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
		  group by  SUBSTRING(b.financedate,1,6)
		
			insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	      select @compid,@mmonth,17,'染发消耗占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('02') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
		  from #m_dconsumeinfo b,projectnameinfo c  
		  where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
		   and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
		  and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
		  group by  SUBSTRING(b.financedate,1,6)
		  
		  
		  insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	      select @compid,@mmonth,18,'护理消耗占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('04','05','07','14') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
		  from #m_dconsumeinfo b,projectnameinfo c  
		  where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
		   and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
		  and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
		  group by  SUBSTRING(b.financedate,1,6)
		
		   
		   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	      select @compid,@mmonth,19,'头皮消耗占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('06') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
		   from #m_dconsumeinfo b,projectnameinfo c  
		  where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
		   and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
		  and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
		  group by  SUBSTRING(b.financedate,1,6)
		   
		   
		  insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
	      select @compid,@mmonth,20,'美发总项目数[包含洗剪吹]',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
		  from #m_dconsumeinfo b,projectnameinfo c  
		  where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
		  and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
		  and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
		  group by  SUBSTRING(b.financedate,1,6)
		  
		  
		   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
		    select @compid,@mmonth,21,'洗剪吹项目数',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
		  from #m_dconsumeinfo b,projectnameinfo c  
		  where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('01')  
		  and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
		  group by  SUBSTRING(b.financedate,1,6)
		  
		  
		   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
		     select @compid,@mmonth,22,'烫发项目数',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
		  from #m_dconsumeinfo b,projectnameinfo c  
		  where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('03')  
		  and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 
		  group by  SUBSTRING(b.financedate,1,6)
		  
		   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
		    select @compid,@mmonth,23,'染发项目数',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
		  from #m_dconsumeinfo b,projectnameinfo c  
		  where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('02')  
		  and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
		  group by  SUBSTRING(b.financedate,1,6)
		  
		   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
		    select @compid,@mmonth,24,'护理项目数',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
		  from #m_dconsumeinfo b,projectnameinfo c  
		  where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('04','05','07','14')  
		  and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
		  group by  SUBSTRING(b.financedate,1,6)
		   
		   
		   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
		    select @compid,@mmonth,25,'头皮项目数',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
		  from #m_dconsumeinfo b,projectnameinfo c  
		  where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('06')  
		  and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
		  group by  SUBSTRING(b.financedate,1,6)
		  
		    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
		    select @compid,@mmonth,26,'洗剪吹项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('01') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
			  from #m_dconsumeinfo b,projectnameinfo c  
			  where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
			   and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
			  and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
			  group by  SUBSTRING(b.financedate,1,6)
		  
		  
		    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
		       select @compid,@mmonth,27,'烫发项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('03') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
			  from #m_dconsumeinfo b,projectnameinfo c  
			  where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
			   and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
			  and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
			  group by  SUBSTRING(b.financedate,1,6)
			  
			     insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
			      select @compid,@mmonth,28,'染发项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('02') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
				  from #m_dconsumeinfo b,projectnameinfo c  
				  where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
				   and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
				  and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
				   group by  SUBSTRING(b.financedate,1,6)
			   
			        insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
			         select @compid,@mmonth,29,'护理项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('04','05','07','14') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
				     from #m_dconsumeinfo b,projectnameinfo c  
				     where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
				     and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
				     and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
				     group by  SUBSTRING(b.financedate,1,6)
				  
				      insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				      select @compid,@mmonth,30,'头皮项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('06') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
					  from #m_dconsumeinfo b,projectnameinfo c  
					  where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
					   and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
					  and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					  group by  SUBSTRING(b.financedate,1,6)
				   
				   
				      insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				      select @compid,@mmonth,31,'美发总均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					  from #m_dconsumeinfo b,projectnameinfo c  
					  where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
					   and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
					  and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					  group by  SUBSTRING(b.financedate,1,6)
				   
				   
				      insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				      select @compid,@mmonth,32,'洗剪吹均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					  from #m_dconsumeinfo b,projectnameinfo c  
					  where b.csitemno=c.prjno  and isnull(c.prjreporttype,'') in ('01')  
					  and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					  group by  SUBSTRING(b.financedate,1,6)
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,33,'烫发均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno  and isnull(c.prjreporttype,'') in ('03')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					    group by  SUBSTRING(b.financedate,1,6)
				  
				  
				  
				        insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,34,'染发均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('02')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					    group by  SUBSTRING(b.financedate,1,6)
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,35,'护理均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('04','05','07','14')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					    group by  SUBSTRING(b.financedate,1,6)
					  
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,36,'头皮均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('06')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					    group by  SUBSTRING(b.financedate,1,6)
					  
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,37,'美发总客单数[包含洗剪吹]',count(distinct b.csbillid)   
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
					    and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					    group by  SUBSTRING(b.financedate,1,6)
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,38,'美发总客单价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
					    and isnull(c.prjreporttype,'') in ('01','03','02','04','05','07','14','06')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					    group by  SUBSTRING(b.financedate,1,6)
					  
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,39,'美发疗程客单数',count(distinct b.csbillid)   
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'')='9'  
					    group by  SUBSTRING(b.financedate,1,6)
					  
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,40,'美发疗程客单价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'')='9'  
					    group by  SUBSTRING(b.financedate,1,6)
					    
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,41,'美发疗程客单数占比',convert(numeric(20,4),(count(distinct case when  isnull(cspaymode,'')='9' then b.csbillid else '' end )-1)*1.0/count(distinct b.csbillid))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('3','6')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					    group by  SUBSTRING(b.financedate,1,6)
					  
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,42,'美容实业绩[包含美容产品.干洗水洗]',convert(numeric(20,1),SUM(ISNULL(realbeautyeji,0)))  
					    from compclasstraderesult where compid=@compid and  ddate between @fromdate and @todate
					    group by SUBSTRING(ddate,1,6)
					  
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,43,'养生类SPA总消耗',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('08','12','19','20','21')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 
					    group by  SUBSTRING(b.financedate,1,6)
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,44,'面部类消耗',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('10','17')  
					    and b.cscompid=@compid and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 
					    group by  SUBSTRING(b.financedate,1,6)
					  
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,45,'胸部类消耗',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('18')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 
					    group by  SUBSTRING(b.financedate,1,6)
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,46,'其他类消耗',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('09','11','13','22','23')  
					    and b.cscompid=@compid and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 
					    group by  SUBSTRING(b.financedate,1,6)
					  
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,47,'老疗程消耗',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('15')  
					    and b.cscompid=@compid and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 
					    group by  SUBSTRING(b.financedate,1,6)
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,48,'养生类SPA总消耗占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('08','12','19','20','21') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					    and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					    group by  SUBSTRING(b.financedate,1,6)
					  
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,49,'面部类消耗占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('10','17') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
					    from #m_dconsumeinfo b,projectnameinfo c  
					    where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					    and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					    and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					    group by  SUBSTRING(b.financedate,1,6)
					  
					    insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				        select @compid,@mmonth,50,'胸部类消耗占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('18') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
						from #m_dconsumeinfo b,projectnameinfo c  
						where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
						and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
						and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
						group by  SUBSTRING(b.financedate,1,6)
						  
						  
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,51,'其他类消耗占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('09','11','13','22','23') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					   group by  SUBSTRING(b.financedate,1,6)
						  
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,52,'老疗程消耗占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('15') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					   group by  SUBSTRING(b.financedate,1,6)
					  
					  
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,53,'美容总项目数[不包含干洗水洗]',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')   
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
										  
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,54,'养生项目数',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('08','12','19','20','21')  
					   and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					  
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,55,'面部项目数',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in  ('10','17')  
					   and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					  
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,56,'胸部项目数',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('18')  
					   and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					  
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,57,'其他类项目数',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjreporttype,'') in ('09','11','13','22','23')  
					   and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					  
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,58,'老项目项目数',convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjreporttype,'')  in ('15')  
					   and b.cscompid=@compid   and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					  
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,59,'养生项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('08','12','19','20','21') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					   group by  SUBSTRING(b.financedate,1,6)
					  
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,60,'面部项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('10','17') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,61,'胸部项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('18') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					   group by  SUBSTRING(b.financedate,1,6)
										   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,62,'其他类项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('09','11','13','22','23') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0))) 
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,63,'老项目项目数占比',convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'') in ('15') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,64,'美容均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,65,'养生类均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno  and isnull(c.prjreporttype,'') in ('08','12','19','20','21')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,66,'面部类均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno  and isnull(c.prjreporttype,'') in ('10','17')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,67,'胸部类均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno  and isnull(c.prjreporttype,'') in ('18')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,68,'其他类均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno  and isnull(c.prjreporttype,'') in ('09','11','13','22','23')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,69,'老项目均价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno  and isnull(c.prjreporttype,'') in ('15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,70,'美容部客单数[不包含干洗水洗]',count(distinct b.csbillid)   
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,71,'美容部客单价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and isnull(c.prjreporttype,'') in ('08','12','19','20','21','10','17','18','09','11','13','22','23','15')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0  
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,72,'美容疗程客单数',count(distinct b.csbillid)   
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'')='9'  
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,73,'美容疗程客单价',convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'')='9'  
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   insert jsanalysisresult(compno,mmonth,resusttyep,resusttyeptext,resultamt)
				       select @compid,@mmonth,74,'美容疗程客单占比',convert(numeric(20,4),(count(distinct case when  isnull(cspaymode,'')='9' then b.csbillid else '' end )-1)*1.0/count(distinct b.csbillid))  
					   from #m_dconsumeinfo b,projectnameinfo c  
					   where b.csitemno=c.prjno and isnull(c.prjtype,'') in ('4')  
					   and b.cscompid=@compid  and  isnull(backcsbillid,'')='' and ISNULL(backcsflag,0)=0   
					   group by  SUBSTRING(b.financedate,1,6)
					   
					   
		   drop table #m_dconsumeinfo
end  
go








