--create table consprjanlysis
--(
--	compid		varchar(10)	null,
--	ddate		varchar(6)	null,
--	itemno		varchar(20)	null,
--	itemname	varchar(40)	null,
--	itemtype	varchar(5)	null,
--	itemamt		float		null,
--	fid			int			null,
--)


--create table consolumnanlysis
--(
--	compid		varchar(10)	null,
--	compname	varchar(30)	null,
--	lvltype		varchar(10)	null,
--	lvl			int			null,
--	m1itemno	varchar(50)	null,
--	m1itemamt	float		null,
--	m2itemno	varchar(50)	null,
--	m2itemamt	float		null,
--	m3itemno	varchar(50)	null,
--	m3itemamt	float		null,
--	m4itemno	varchar(50)	null,
--	m4itemamt	float		null,
--	m5itemno	varchar(50)	null,
--	m5itemamt	float		null,
--	m6itemno	varchar(50)	null,
--	m6itemamt	float		null,
--	m7itemno	varchar(50)	null,
--	m7itemamt	float		null,
--	m8itemno	varchar(50)	null,
--	m8itemamt	float		null,
--	m9itemno	varchar(50)	null,
--	m9itemamt	float		null,
--	m10itemno	varchar(50)	null,
--	m10itemamt	float		null,
--	m11itemno	varchar(50)	null,
--	m11itemamt	float		null,
--	m12itemno	varchar(50)	null,
--	m12itemamt	float		null
--)

delete consprjanlysis
insert consprjanlysis(compid,ddate,itemno,itemtype,itemamt,fid)
select ggb00c,gga80d,ggb03c,gda04c,ggb11f,fid
from (
select ggb00c,gga80d=SUBSTRING(gga80d,1,6),ggb03c,gda04c,ggb11f=SUM(ISNULL(ggb11f,0)), 
row_number() over( PARTITION BY ggb00c,SUBSTRING(gga80d,1,6),gda04c order by SUM(ISNULL(ggb11f,0)) desc) fid
 from ( 
 select ggb00c,gga80d,ggb03c,gda04c=case when gda04c='6' then '3' else  gda04c end,ggb11f
	from  ggm01,ggm02,gdm01
	where gga00c=ggb00c and gga01c=ggb01c 
	and gga80d between '20130101' and '20131231'
	and gda00c=gga00c and gda01c=ggb03c and gda13c not in ('00','01','14','15')
) as b
group by ggb00c,ggb03c,gda04c,SUBSTRING(gga80d,1,6)) AS a
where fid<=3
order by ggb00c,gga80d,fid

update consprjanlysis set itemname=gda03c
from consprjanlysis,gdm01 where gda00c=compid and gda01c=itemno




delete consolumnanlysis

insert consolumnanlysis(compid,compname,lvltype,lvl)
select gae01c,gae03c,'美容',1 from gam05,b_perf where gae01c=bran_id and lvl=4

insert consolumnanlysis(compid,compname,lvltype,lvl)
select gae01c,gae03c,'美容',2 from gam05,b_perf where gae01c=bran_id and lvl=4

insert consolumnanlysis(compid,compname,lvltype,lvl)
select gae01c,gae03c,'美容',3 from gam05,b_perf where gae01c=bran_id and lvl=4

insert consolumnanlysis(compid,compname,lvltype,lvl)
select gae01c,gae03c,'美发',1 from gam05,b_perf where gae01c=bran_id and lvl=4

insert consolumnanlysis(compid,compname,lvltype,lvl)
select gae01c,gae03c,'美发',2 from gam05,b_perf where gae01c=bran_id and lvl=4

insert consolumnanlysis(compid,compname,lvltype,lvl)
select gae01c,gae03c,'美发',3 from gam05,b_perf where gae01c=bran_id and lvl=4

update a
set m1itemno=b.itemname,m1itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201301' and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m1itemno=b.itemname,m1itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201301'and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m2itemno=b.itemname,m2itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201302' and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m3itemno=b.itemname,m3itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201303' and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m4itemno=b.itemname,m4itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201304' and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m5itemno=b.itemname,m5itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201305' and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m6itemno=b.itemname,m6itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201306' and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m7itemno=b.itemname,m7itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201307' and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m8itemno=b.itemname,m8itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201308' and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m9itemno=b.itemname,m9itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201309' and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m10itemno=b.itemname,m10itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='2013010' and a.lvltype='美容' and isnull(itemtype,'')='4'

update a
set m11itemno=b.itemname,m11itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201311' and a.lvltype='美容' and isnull(itemtype,'')='4'


update a
set m12itemno=b.itemname,m12itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201312' and a.lvltype='美容' and isnull(itemtype,'')='4'



update a
set m1itemno=b.itemname,m1itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201301'and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')


update a
set m2itemno=b.itemname,m2itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201302' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')

update a
set m3itemno=b.itemname,m3itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201303' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')

update a
set m4itemno=b.itemname,m4itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201304' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')

update a
set m5itemno=b.itemname,m5itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201305' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')

update a
set m6itemno=b.itemname,m6itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201306' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')

update a
set m7itemno=b.itemname,m7itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201307' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')

update a
set m8itemno=b.itemname,m8itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid 
and b.ddate='201308' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')

update a
set m9itemno=b.itemname,m9itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201309' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')

update a
set m10itemno=b.itemname,m10itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='2013010' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')

update a
set m11itemno=b.itemname,m11itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201311' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')


update a
set m12itemno=b.itemname,m12itemamt=b.itemamt
from consolumnanlysis a,consprjanlysis b
where a.compid=b.compid and a.lvl=b.fid
and b.ddate='201312' and a.lvltype='美发' and isnull(itemtype,'') in ('3','6')

select * from consolumnanlysis order by compid,lvl