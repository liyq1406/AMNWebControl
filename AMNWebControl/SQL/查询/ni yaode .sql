

create table #loadPrjDate
(
	compid	varchar(10)	null,
	compname	varchar(20)	null,
	mmonth		varchar(10)	null,
	干洗		float		null,
	洗吹干洗	float		null,
	洗剪吹		float		null,
	美发大项	float		null,
	美容大项	float		null
)
go
insert #loadPrjDate(compid,compname,mmonth,干洗,洗吹干洗,洗剪吹,美发大项,美容大项)
select ggb00c,gae03c,SUBSTRING(ggb01c,4,6) as '月份',
干洗= sum(case when ggb03c ='300' then ISNULL(ggb05f,0) end),
洗吹干洗= sum(case when ggb03c ='303' then ISNULL(ggb05f,0)  end),
洗剪吹= sum(case when ggb03c in ('302','305') then ISNULL(ggb05f,0)  end),
美发大项= sum(case when (ggb03c not in ('303','302','305','306') and gda04c in ('3','6')) then ISNULL(ggb05f,0)  end),
美容大项= sum(case when (gda04c = '4' and gda30i ='1') then ISNULL(ggb05f,0)  end)
from ggm02,gdm01,ggm01,gam05 where ggb03c = gda01c and gda00c= ggb00c and SUBSTRING(ggb01c,4,6) between '201301' and '201312' and isnull(gga99i,'0')='0' and ISNULL(gga30c,'')=''
and gga00c= ggb00c and gga01c =ggb01c and gga00c = gae01c
group by ggb00c,gae03c,SUBSTRING(ggb01c,4,6)
order by ggb00c,月份

select compid,compname,mmonth,干洗,洗吹干洗,洗剪吹,美发大项,美容大项 from #loadPrjDate

	declare @sqltitle varchar(600)
	set @sqltitle = '[201301],[201302],[201303],[201304],[201305],[201306],[201307],[201308],[201309],[201310],[201311],[201312]'
	
	exec ('select * from (select compid,compname,mmonth,总美发= convert(numeric(20,0),isnull(干洗,0)+isnull(洗吹干洗,0)+isnull(洗剪吹,0)+isnull(美发大项,0)) from #loadPrjDate ) a pivot (max(总美发) for mmonth in (' + @sqltitle + ')) b order by compid')
	exec ('select * from (select compid,compname,mmonth, 美容大项=convert(numeric(20,0),美容大项) from #loadPrjDate ) a pivot (max(美容大项) for mmonth in (' + @sqltitle + ')) b order by compid')
	
	
drop table #loadPrjDate


