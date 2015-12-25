--create table fillamtTable
--(
--	ddate		varchar(10) null,
--	cardtype		varchar(10) null,
--	cardtypenamr	varchar(30)	null,
--	opencount		float		null,
--	openamt			float		null,
--	fillcount		float		null,
--	fillamt			float		null,
--	changecount		float		null,
--	changeamt		float		null,
--	combincount		float		null,
--	combamt			float		null
--)
delete fillamtTable
insert fillamtTable(ddate,cardtype,cardtypenamr)
select '201308',gak01c,gak02c from gam10 where gak00c='001' and gak01c in ('013031','013531','014031','015031','016531')
insert fillamtTable(ddate,cardtype,cardtypenamr)
select '201309',gak01c,gak02c from gam10 where gak00c='001' and gak01c in ('013031','013531','014031','015031','016531')
insert fillamtTable(ddate,cardtype,cardtypenamr)
select '201310',gak01c,gak02c from gam10 where gak00c='001' and gak01c in ('013031','013531','014031','015031','016531')

----------------------------------201308
update a set a.opencount=cgca02c,a.openamt=agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gna01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gnm01,gcm01 ,gsm03
where gca01c=gna13c and gna80d between '20130801' and '20130831' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gna99i,0)=0  and gsc04c<>'5' 
and gsc00c=gna00c and gsc01c=gna01c and gsc02c='gn' and gna00c<>'001'
group by gna01c,gca02c ) c group by gca02c) b
where a.ddate='201308' and a.cardtype=b.gca02c

update a set a.changecount=cgca02c,a.changeamt=agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gea01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20130801' and '20130831' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gea99i,0)=0 and gea02i=4 and gsc04c<>'5' 
and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001'
group by gea01c,gca02c ) c group by gca02c) b
where a.ddate='201308' and a.cardtype=b.gca02c

update a set a.combincount=cgca02c,a.combamt =agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gea01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20130801' and '20130831' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gea99i,0)=0 and gea02i=12 and gsc04c<>'5' 
and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001'
group by gea01c,gca02c ) c group by gca02c) b
where a.ddate='201308' and a.cardtype=b.gca02c

update a set a.fillcount=cgca02c,a.fillamt=agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gcl01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gcm10,gcm01 ,gsm03
where gca01c=gcl03c and gcl80d between '20130801' and '20130831' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gcl99i,0)=0  and isnull(gcl54c,'')='' and gsc04c<>'5' 
and gsc00c=gcl00c and gsc01c=gcl01c and gsc02c='gz' and gcl00c<>'001'
group by gcl01c,gca02c ) c group by gca02c) b
where a.ddate='201308' and a.cardtype=b.gca02c



----------------------------------201309

update a set a.opencount=cgca02c,a.openamt=agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gna01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gnm01,gcm01 ,gsm03
where gca01c=gna13c and gna80d between '20130901' and '20130930' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gna99i,0)=0  and gsc04c<>'5' 
and gsc00c=gna00c and gsc01c=gna01c and gsc02c='gn' and gna00c<>'001'
group by gna01c,gca02c ) c group by gca02c) b
where a.ddate='201309' and a.cardtype=b.gca02c

update a set a.changecount=cgca02c,a.changeamt=agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gea01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20130901' and '20130930' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gea99i,0)=0 and gea02i=4 and gsc04c<>'5' 
and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001'
group by gea01c,gca02c ) c group by gca02c) b
where a.ddate='201309' and a.cardtype=b.gca02c

update a set a.combincount=cgca02c,a.combamt =agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gea01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20130901' and '20130930' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gea99i,0)=0 and gea02i=12 and gsc04c<>'5' 
and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001'
group by gea01c,gca02c ) c group by gca02c) b
where a.ddate='201309' and a.cardtype=b.gca02c

update a set a.fillcount=cgca02c,a.fillamt=agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gcl01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gcm10,gcm01 ,gsm03
where gca01c=gcl03c and gcl80d between '20130901' and '20130930' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gcl99i,0)=0  and isnull(gcl54c,'')='' and gsc04c<>'5' 
and gsc00c=gcl00c and gsc01c=gcl01c and gsc02c='gz' and gcl00c<>'001'
group by gcl01c,gca02c ) c group by gca02c) b
where a.ddate='201309' and a.cardtype=b.gca02c



----------------------------------201310

update a set a.opencount=cgca02c,a.openamt=agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gna01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gnm01,gcm01 ,gsm03
where gca01c=gna13c and gna80d between '20131001' and '20131031' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gna99i,0)=0  and gsc04c<>'5' 
and gsc00c=gna00c and gsc01c=gna01c and gsc02c='gn' and gna00c<>'001'
group by gna01c,gca02c ) c group by gca02c) b
where a.ddate='201310' and a.cardtype=b.gca02c

update a set a.changecount=cgca02c,a.changeamt=agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gea01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131001' and '20131031' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gea99i,0)=0 and gea02i=4 and gsc04c<>'5' 
and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001'
group by gea01c,gca02c ) c group by gca02c) b
where a.ddate='201310' and a.cardtype=b.gca02c

update a set a.combincount=cgca02c,a.combamt =agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gea01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131001' and '20131031' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gea99i,0)=0 and gea02i=12 and gsc04c<>'5' 
and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001'
group by gea01c,gca02c ) c group by gca02c) b
where a.ddate='201310' and a.cardtype=b.gca02c

update a set a.fillcount=cgca02c,a.fillamt=agsc05f
from fillamtTable a,(
select gca02c,cgca02c=count(cgca02c),agsc05f=sum(agsc05f) from ( 
select gcl01c, gca02c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gcm10,gcm01 ,gsm03
where gca01c=gcl03c and gcl80d between '20131001' and '20131031' and gca00c=gca13d 
and gca02c  in ('013031','013531','014031','015031','016531')
and isnull(gcl99i,0)=0  and isnull(gcl54c,'')='' and gsc04c<>'5' 
and gsc00c=gcl00c and gsc01c=gcl01c and gsc02c='gz' and gcl00c<>'001'
group by gcl01c,gca02c ) c group by gca02c) b
where a.ddate='201310' and a.cardtype=b.gca02c


select * from fillamtTable

