	declare @tcompid varchar(10)
	declare cur_each_comp cursor for
	select curcompno from compchainstruct  where complevel=4  

	open cur_each_comp

	fetch cur_each_comp into @tcompid
	while @@fetch_status=0
	begin
		
			 exec upg_analysis_system_2013 @tcompid,'20130101','20131231'
		fetch cur_each_comp into @tcompid
	end
	close cur_each_comp
	deallocate cur_each_comp
	
	
	
	
	--create table computallavg
	--(
	--	compid	   varchar(10),
	--	lvltype    int  null,
	--	monthr	   float  null,
	--)
	--delete computallavg
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month1r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month2r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month3r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month4r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month5r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month6r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month7r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month8r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month9r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr) 
	--select compno,resusttyep,month10r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month11r 	 from analysisresult where resusttyep=310
	--insert computallavg(compid,lvltype,monthr)
	--select compno,resusttyep,month12r 	 from analysisresult where resusttyep=310
	
	
	-- update analysisresult set monthf_5r=ISNULL(monthr,0)/ISNULL(monthc,0)
	-- from analysisresult,(
	-- select compid,lvltype,monthr=sum(monthr),monthc=SUM(case when ISNULL(monthr,0)>0 then 1 else 0 end ) 
	-- from (select compid,lvltype,monthr,row_number() over( PARTITION BY compid,lvltype order by max(ISNULL(monthr,0)) desc) fid 
	-- from computallavg group  by compid,lvltype,monthr) A
	-- where fid<=5
	-- group by compid,lvltype) B
	-- where compno=compid and resusttyep=lvltype and ISNULL(monthc,0)>0
	 
	-- update analysisresult set montha_5r=ISNULL(monthr,0)/ISNULL(monthc,0)
	-- from analysisresult,(
	-- select compid,lvltype,monthr=sum(monthr),monthc=SUM(case when ISNULL(monthr,0)>0 then 1 else 0 end ) 
	-- from (select compid,lvltype,monthr,row_number() over( PARTITION BY compid,lvltype order by max(ISNULL(monthr,0)) asc) fid 
	-- from computallavg group  by compid,lvltype,monthr) A
	-- where fid<=5
	-- group by compid,lvltype) B
	-- where compno=compid and resusttyep=lvltype and ISNULL(monthc,0)>0
	 
	
		
	 update analysisresult set resusttyep=201,resusttyeptext='总业绩' where  resusttyep=1
	 update analysisresult set resusttyep=202,resusttyeptext='美发虚业绩' where  resusttyep=2
	 update analysisresult set resusttyep=203,resusttyeptext='美容虚业绩' where  resusttyep=3
	 update analysisresult set resusttyep=204,resusttyeptext='美容虚业绩占比' where  resusttyep=110
	 update analysisresult set resusttyep=205,resusttyeptext='美甲虚业绩' where  resusttyep=4
	 update analysisresult set resusttyep=206,resusttyeptext='总实业绩' where  resusttyep=5
	 update analysisresult set resusttyep=207,resusttyeptext='耗卡率(总销卡/卡异动)' where  resusttyep=6
	 update analysisresult set resusttyep=208,resusttyeptext='美容实业绩占比' where  resusttyep=7 
	 update analysisresult set resusttyep=209,resusttyeptext='美发实业绩[包含美发产品]' where  resusttyep=8 
	 update analysisresult set resusttyep=210,resusttyeptext='洗剪吹消耗' where  resusttyep=10 
	 update analysisresult set resusttyep=211,resusttyeptext='烫发消耗' where  resusttyep=11 
	 update analysisresult set resusttyep=212,resusttyeptext='染发消耗' where  resusttyep=12 
	 update analysisresult set resusttyep=213,resusttyeptext='护理消耗' where  resusttyep=13 
	 update analysisresult set resusttyep=214,resusttyeptext='头皮消耗' where  resusttyep=14 
	 update analysisresult set resusttyep=215,resusttyeptext='洗剪吹消耗占比' where  resusttyep=90 
	 update analysisresult set resusttyep=216,resusttyeptext='烫发消耗占比' where  resusttyep=91 
	 update analysisresult set resusttyep=217,resusttyeptext='染发消耗占比' where  resusttyep=92 
	 update analysisresult set resusttyep=218,resusttyeptext='护理消耗占比' where  resusttyep=93 
	 update analysisresult set resusttyep=219,resusttyeptext='头皮消耗占比' where  resusttyep=94 
	 
	 update analysisresult set resusttyep=220,resusttyeptext='美发总项目数[包含洗剪吹]' where  resusttyep=15 
	 update analysisresult set resusttyep=221,resusttyeptext='洗剪吹项目数' where  resusttyep=16 
	 update analysisresult set resusttyep=222,resusttyeptext='烫发项目数' where  resusttyep=17 
	 update analysisresult set resusttyep=223,resusttyeptext='染发项目数' where  resusttyep=18
	 update analysisresult set resusttyep=224,resusttyeptext='护理项目数' where  resusttyep=19 
	 update analysisresult set resusttyep=225,resusttyeptext='头皮项目数' where  resusttyep=20 
	 
	 update analysisresult set resusttyep=226,resusttyeptext='洗剪吹项目数占比' where  resusttyep=95 
	 update analysisresult set resusttyep=227,resusttyeptext='烫发项目数占比' where  resusttyep=96 
	 update analysisresult set resusttyep=228,resusttyeptext='染发项目数占比' where  resusttyep=97
	 update analysisresult set resusttyep=229,resusttyeptext='护理项目数占比' where  resusttyep=98 
	 update analysisresult set resusttyep=230,resusttyeptext='头皮项目数占比' where  resusttyep=99 
	 
	
	 update analysisresult set resusttyep=231,resusttyeptext='美发总均价' where  resusttyep=21 
	 update analysisresult set resusttyep=232,resusttyeptext='洗剪吹均价' where  resusttyep=22 
	 update analysisresult set resusttyep=233,resusttyeptext='烫发均价' where  resusttyep=23 
	 update analysisresult set resusttyep=234,resusttyeptext='染发均价' where  resusttyep=24
	 update analysisresult set resusttyep=235,resusttyeptext='护理均价' where  resusttyep=25 
	 update analysisresult set resusttyep=236,resusttyeptext='头皮均价' where  resusttyep=26 
	 
	 update analysisresult set resusttyep=237,resusttyeptext='美发总客单数[包含洗剪吹]' where  resusttyep=27 
	 update analysisresult set resusttyep=238,resusttyeptext='美发总客单价' where  resusttyep=28 
	 update analysisresult set resusttyep=239,resusttyeptext='美发疗程客单数' where  resusttyep=29
	 update analysisresult set resusttyep=240,resusttyeptext='美发疗程客单价' where  resusttyep=30
	 update analysisresult set resusttyep=241,resusttyeptext='美发疗程客单数占比' where  resusttyep=31 
	 
	 update analysisresult set resusttyep=242,resusttyeptext='美容实业绩[包含美容产品.干洗水洗]' where  resusttyep=32 
	 update analysisresult set resusttyep=243,resusttyeptext='养生类SPA总消耗' where  resusttyep=33 
	 update analysisresult set resusttyep=244,resusttyeptext='面部类消耗' where  resusttyep=34 
	 update analysisresult set resusttyep=245,resusttyeptext='胸部类消耗' where  resusttyep=35 
	 update analysisresult set resusttyep=246,resusttyeptext='其他类消耗' where  resusttyep=36 
	 update analysisresult set resusttyep=247,resusttyeptext='老疗程消耗' where  resusttyep=37 
	
	 update analysisresult set resusttyep=248,resusttyeptext='养生类SPA总消耗占比' where  resusttyep=100 
	 update analysisresult set resusttyep=249,resusttyeptext='面部类消耗占比' where  resusttyep=101 
	 update analysisresult set resusttyep=250,resusttyeptext='胸部类消耗占比' where  resusttyep=102 
	 update analysisresult set resusttyep=251,resusttyeptext='其他类消耗占比' where  resusttyep=103 
	 update analysisresult set resusttyep=252,resusttyeptext='老疗程消耗占比' where  resusttyep=104 
	 	
	 update analysisresult set resusttyep=253,resusttyeptext='美容总项目数[不包含干洗水洗]' where  resusttyep=38 
	 update analysisresult set resusttyep=254,resusttyeptext='养生项目数' where  resusttyep=39 
	 update analysisresult set resusttyep=255,resusttyeptext='面部项目数' where  resusttyep=40 
	 update analysisresult set resusttyep=256,resusttyeptext='胸部项目数' where  resusttyep=41
	 update analysisresult set resusttyep=257,resusttyeptext='其他项目数' where  resusttyep=42 
	 update analysisresult set resusttyep=258,resusttyeptext='老疗程项目数' where  resusttyep=43 
	
	
	 update analysisresult set resusttyep=259,resusttyeptext='养生项目数占比' where  resusttyep=105 
	 update analysisresult set resusttyep=260,resusttyeptext='面部项目数占比' where  resusttyep=106 
	 update analysisresult set resusttyep=261,resusttyeptext='胸部项目数占比' where  resusttyep=107
	 update analysisresult set resusttyep=262,resusttyeptext='其他项目数占比' where  resusttyep=108 
	 update analysisresult set resusttyep=263,resusttyeptext='老疗程项目数占比' where  resusttyep=109 
	 
	 
	 update analysisresult set resusttyep=264,resusttyeptext='美容均价' where  resusttyep=44 
	 update analysisresult set resusttyep=265,resusttyeptext='养生类均价' where  resusttyep=45 
	 update analysisresult set resusttyep=266,resusttyeptext='面部类均价' where  resusttyep=46 
	 update analysisresult set resusttyep=267,resusttyeptext='胸部类均价' where  resusttyep=47
	 update analysisresult set resusttyep=268,resusttyeptext='其他类均价' where  resusttyep=48 
	 update analysisresult set resusttyep=269,resusttyeptext='老疗程均价' where  resusttyep=49 
	 	
	 update analysisresult set resusttyep=270,resusttyeptext='美容部客单数[不包含干洗水洗]' where  resusttyep=50 
	 update analysisresult set resusttyep=271,resusttyeptext='美容部客单价' where  resusttyep=51 
	 update analysisresult set resusttyep=272,resusttyeptext='美容疗程客单数' where  resusttyep=52 
	 update analysisresult set resusttyep=273,resusttyeptext='美容疗程客单价' where  resusttyep=53
	 update analysisresult set resusttyep=274,resusttyeptext='美容疗程客单占比' where  resusttyep=54 

	 update analysisresult set resusttyep=275,resusttyeptext='总流失人数' where  resusttyep=55 
	 update analysisresult set resusttyep=276,resusttyeptext='美发部流人数' where  resusttyep=56 
	 update analysisresult set resusttyep=277,resusttyeptext='烫染部流失人数' where  resusttyep=57 
	 update analysisresult set resusttyep=278,resusttyeptext='美容部流失人数' where  resusttyep=58
	 update analysisresult set resusttyep=279,resusttyeptext='大堂接待收银流失人数' where  resusttyep=59 
	 update analysisresult set resusttyep=280,resusttyeptext='后勤流失人数' where  resusttyep=60 
	
	 
	 update analysisresult set resusttyep=281,resusttyeptext='3个月内总流失人数' where  resusttyep=61 
	 update analysisresult set resusttyep=282,resusttyeptext='3个月内美发部流人数' where  resusttyep=62 
	 update analysisresult set resusttyep=283,resusttyeptext='3个月内烫染部流失人数 ' where  resusttyep=63 
	 update analysisresult set resusttyep=284,resusttyeptext='3个月内美容部流失人数' where  resusttyep=64
	 update analysisresult set resusttyep=285,resusttyeptext='3个月内大堂接待收银流失人数' where  resusttyep=65 
	 update analysisresult set resusttyep=286,resusttyeptext='3个月内后勤流失人数' where  resusttyep=66 
	 	
	 update analysisresult set resusttyep=287,resusttyeptext='6个月内总流失人数' where  resusttyep=67
	 update analysisresult set resusttyep=288,resusttyeptext='6个月内美发部流人数' where  resusttyep=68 
	 update analysisresult set resusttyep=289,resusttyeptext='6个月内烫染部流失人数 ' where  resusttyep=69 
	 update analysisresult set resusttyep=290,resusttyeptext='6个月内美容部流失人数' where  resusttyep=70
	 update analysisresult set resusttyep=291,resusttyeptext='6个月内大堂接待收银流失人数' where  resusttyep=71 
	 update analysisresult set resusttyep=292,resusttyeptext='6个月内后勤流失人数' where  resusttyep=72
	 
	  update analysisresult set resusttyep=293,resusttyeptext='12个月上总流失人数' where  resusttyep=73
	 update analysisresult set resusttyep=294,resusttyeptext='12个月上美发部流人数' where  resusttyep=74 
	 update analysisresult set resusttyep=295,resusttyeptext='12个月上烫染部流失人数 ' where  resusttyep=75 
	 update analysisresult set resusttyep=296,resusttyeptext='12个月上美容部流失人数' where  resusttyep=76
	 update analysisresult set resusttyep=297,resusttyeptext='12个月上大堂接待收银流失人数' where  resusttyep=77 
	 update analysisresult set resusttyep=298,resusttyeptext='12个月上后勤流失人数' where  resusttyep=78 
	 
	 
	 update analysisresult set resusttyep=299,resusttyeptext='3-6个月低于3000 的员工' where  resusttyep=79
	 update analysisresult set resusttyep=300,resusttyeptext='3-6个月美发部低于3000 的员工' where  resusttyep=80 
	 update analysisresult set resusttyep=301,resusttyeptext='3-6个月烫染部低于3000 ' where  resusttyep=81 
	 update analysisresult set resusttyep=302,resusttyeptext='3-6个月美容部低于3000 的员工' where  resusttyep=82
	 
	 update analysisresult set resusttyep=303,resusttyeptext='6-12个月低于5000 的员工' where  resusttyep=83
	 update analysisresult set resusttyep=304,resusttyeptext='6-12个月美发部低于5000 的员工' where  resusttyep=84 
	 update analysisresult set resusttyep=305,resusttyeptext='6-12个月烫染部低于5000 ' where  resusttyep=85 
	 update analysisresult set resusttyep=306,resusttyeptext='6-12个月美容部低于5000 的员工' where  resusttyep=86
	 				
	 update analysisresult set resusttyep=307,resusttyeptext='12个月上低于7000 的员工' where  resusttyep=87
	 update analysisresult set resusttyep=308,resusttyeptext='12个月上美发部低于7000 的员工' where  resusttyep=88 
	 update analysisresult set resusttyep=309,resusttyeptext='12个月上烫染部低于7000 ' where  resusttyep=89 
	 update analysisresult set resusttyep=310,resusttyeptext='12个月上美容部低于7000 的员工' where  resusttyep=111
	 update analysisresult set resusttyep=311,resusttyeptext='门店客单数' where  resusttyep=112	
  
 
	
	 update analysisresult set montha_12r=convert(numeric(20,4),ISNULL(months_12r,0)/(  
  (case when ISNULL(month1r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month2r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month3r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month4r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month5r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month6r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month7r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month8r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month9r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month10r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month11r,0)>0 then 1 else 0 end )  
 +(case when ISNULL(month12r,0)>0 then 1 else 0 end )))  
 where ISNULL(months_12r,0)>0  
 		
 update A set A.monthf_5r=convert(numeric(20,4),B.monthf_5r)
 from analysisresult A ,(select resusttyep,resusttyeptext,monthf_5r=sum(montha_12r)/5  from 
 (select resusttyep,compno,resusttyeptext,montha_12r ,row_number() over( PARTITION BY resusttyeptext order by SUM(ISNULL(montha_12r,0)) desc) fid
 from analysisresult   where compno not in ('040','037','004','010')
 group by resusttyep,compno,resusttyeptext,montha_12r
) A where fid<=5  group by resusttyep,resusttyeptext ) B
where A.resusttyep=B.resusttyep


 update A set A.montha_5r=convert(numeric(20,4),B.montha_5r)
 from analysisresult A ,(select resusttyep,resusttyeptext,montha_5r=sum(montha_12r)/5  from 
 (select resusttyep,compno,resusttyeptext,montha_12r ,row_number() over( PARTITION BY resusttyeptext order by SUM(ISNULL(montha_12r,0)) asc) fid
 from analysisresult  where compno not in ('040','037','004','010') and ISNULL(montha_12r,0)<>0 
 group by resusttyep,compno,resusttyeptext,montha_12r
) A where fid<=5  group by resusttyep,resusttyeptext ) B
where A.resusttyep=B.resusttyep


	 
	 
			
	
	 
	 --create table riteminfo
	 --(
		--nno		int		null,
		--nname	varchar(40)	null
	 --)
	 
	 ----insert riteminfo(nno,nname)
	 ----select resusttyep,resusttyeptext from analysisresult group by resusttyep,resusttyeptext 
	 go
	 declare @tcompid varchar(10)
	 declare @targerno   int 
	 set @targerno=210
	 while(@targerno<312)
	 begin
			
			declare cur_each_comp cursor for
			select curcompno from compchainstruct  where complevel=4

			open cur_each_comp

			fetch cur_each_comp into @tcompid
			while @@fetch_status=0
			begin
				   if not exists(select 1 from analysisresult where compno= @tcompid and resusttyep=@targerno) 
				   begin
					insert analysisresult(compno,resusttyep,resusttyeptext)
					select @tcompid,@targerno,nname from riteminfo where nno=@targerno
				  end
					fetch cur_each_comp into @tcompid
			end
			close cur_each_comp
			deallocate cur_each_comp
		
		
		set @targerno=@targerno+1
	 end
	 
	 
--update  analysisresult set resusttyep =215 where resusttyep=2500 


