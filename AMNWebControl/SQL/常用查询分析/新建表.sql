
--013031	ÉÏº£°¢ÂêÄá×ê¿¨
--013531	ÉÏº£°¢ÂêÄá½ð¿¨
--014031	ÉÏº£°¢ÂêÄá4ÕÛ
--015031	ÉÏº£°¢ÂêÄá5ÕÛ
--016531	ÉÏº£°¢ÂêÄá6.5ÕÛ

--016031	ÉÏº£°¢ÂêÄá6ÕÛ(2013)
--018031	ÉÏº£°¢ÂêÄá×ê¿¨(2013)
--018531	ÉÏº£°¢ÂêÄá½ð¿¨(2013)
--019031	ÉÏº£°¢ÂêÄá5ÕÛ(2013)


create table zongnedd
(
	compid			varchar(10)	null,
	compname		varchar(30)	null,
	ddate			varchar(10)	null,
	t013031opencount	float		null,
	t013031openamt		float		null,
	t013031changecount	float		null,
	t013031changeamt	float		null,
	
	t013531opencount	float		null,
	t013531openamt		float		null,
	t013531changecount	float		null,
	t013531changeamt	float		null,
	
	t014031opencount	float		null,
	t014031openamt		float		null,
	t014031changecount	float		null,
	t014031changeamt	float		null,
	
	t015031opencount	float		null,
	t015031openamt		float		null,
	t015031changecount	float		null,
	t015031changeamt	float		null,
	
	t016531opencount	float		null,
	t016531openamt		float		null,
	t016531changecount	float		null,
	t016531changeamt	float		null
)
CREATE NONCLUSTERED index idx_zongnedd_compid on zongnedd(compid,ddate)


select opencount=SUM(ISNULL(t013031opencount,0)+ISNULL(t013531opencount,0)+ISNULL(t014031opencount,0)+ISNULL(t015031opencount,0)+ISNULL(t016531opencount,0))
		,changecount=SUM(ISNULL(t013031changecount,0)+ISNULL(t013531changecount,0)+ISNULL(t014031changecount,0)+ISNULL(t015031changecount,0)+ISNULL(t016531changecount,0))
	   ,openamt=SUM(ISNULL(t013031openamt,0)+ISNULL(t013531openamt,0)+ISNULL(t014031openamt,0)+ISNULL(t015031openamt,0)+ISNULL(t016531openamt,0))  
	   ,changeamt=SUM(ISNULL(t013031changeamt,0)+ISNULL(t013531changeamt,0)+ISNULL(t014031changeamt,0)+ISNULL(t015031changeamt,0)+ISNULL(t016531changeamt,0))  
from zongnedd 



delete zongnedd where ISNULL(t013031opencount,0)+ISNULL(t013531opencount,0)+ISNULL(t014031opencount,0)
+ISNULL(t015031opencount,0)+ISNULL(t016531opencount,0)+ISNULL(t013031changecount,0)
+ISNULL(t013531changecount,0)+ISNULL(t014031changecount,0)+ISNULL(t015031changecount,0)+ISNULL(t016531changecount,0)=0

select * from zongnedd


insert zongnedd(compid,compname,ddate)
select gae01c,gae03c,'201308' from gam05

insert zongnedd(compid,compname,ddate)
select gae01c,gae03c,'201309' from gam05

insert zongnedd(compid,compname,ddate)
select gae01c,gae03c,'201310' from gam05

insert zongnedd(compid,compname,ddate)
select gae01c,gae03c,'201311' from gam05

insert zongnedd(compid,compname,ddate)
select gae01c,gae03c,'201211' from gam05


insert zongnedd(compid,compname,ddate)
select gae01c,gae03c,'201210' from gam05

create table zongnedd_11
(
	compid			varchar(10)	null,
	compname		varchar(30)	null,
	ddate			varchar(10)	null,
	t016031opencount	float		null,
	t016031openamt		float		null,
	t016031changecount	float		null,
	t016031changeamt	float		null,
	
	t018031opencount	float		null,
	t018031openamt		float		null,
	t018031changecount	float		null,
	t018031changeamt	float		null,
	
	t018531opencount	float		null,
	t018531openamt		float		null,
	t018531changecount	float		null,
	t018531changeamt	float		null,
	
	t019031opencount	float		null,
	t019031openamt		float		null,
	t019031changecount	float		null,
	t019031changeamt	float		null,
)

select opencount=SUM(ISNULL(t016031opencount,0)+ISNULL(t018031opencount,0)+ISNULL(t018531opencount,0)+ISNULL(t019031opencount,0))
		,changecount=SUM(ISNULL(t016031changecount,0)+ISNULL(t018031changecount,0)+ISNULL(t018531changecount,0)+ISNULL(t019031changecount,0))
	   ,openamt=SUM(ISNULL(t016031openamt,0)+ISNULL(t018031openamt,0)+ISNULL(t018531openamt,0)+ISNULL(t019031openamt,0))
	   ,changeamt=SUM(ISNULL(t016031changeamt,0)+ISNULL(t018031changeamt,0)+ISNULL(t018531changeamt,0)+ISNULL(t019031changeamt,0))
from zongnedd_11 


delete zongnedd_11
insert zongnedd_11(compid,compname,ddate)
select gae01c,gae03c,'201311' from gam05



update a  set a.t019031openamt=agsc05f,a.t019031opencount=b.cgca02c
from zongnedd_11 a,(select gna00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gnm01,gcm01 ,gsm03
where gca01c=gna13c and gna80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='019031'
and isnull(gna99i,0)=0 and gsc04c<>'5' and gsc00c=gna00c and gsc01c=gna01c and gsc02c='gn'
group by gna00c) b
where a.compid=b.gna00c   and a.ddate='201311'

update a  set a.t016031openamt=agsc05f,a.t016031opencount=b.cgca02c
from zongnedd_11 a,(select gna00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gnm01,gcm01 ,gsm03
where gca01c=gna13c and gna80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='016031'
and isnull(gna99i,0)=0 and gsc04c<>'5' and gsc00c=gna00c and gsc01c=gna01c and gsc02c='gn'
group by gna00c) b
where a.compid=b.gna00c   and a.ddate='201311'

update a  set a.t018031openamt=agsc05f,a.t018031opencount=b.cgca02c
from zongnedd_11 a,(select gna00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gnm01,gcm01 ,gsm03
where gca01c=gna13c and gna80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='018031'
and isnull(gna99i,0)=0 and gsc04c<>'5' and gsc00c=gna00c and gsc01c=gna01c and gsc02c='gn'
group by gna00c) b
where a.compid=b.gna00c   and a.ddate='201311'

update a  set a.t018531openamt=agsc05f,a.t018531opencount=b.cgca02c
from zongnedd_11 a,(select gna00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gnm01,gcm01 ,gsm03
where gca01c=gna13c and gna80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='018531'
and isnull(gna99i,0)=0 and gsc04c<>'5' and gsc00c=gna00c and gsc01c=gna01c and gsc02c='gn'
group by gna00c) b
where a.compid=b.gna00c   and a.ddate='201311'

update a  set a.t019031changeamt=agsc05f,a.t019031changecount=b.cgca02c
from zongnedd_11 a,(select gea00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='019031'
and isnull(gea99i,0)=0 and gsc04c<>'5' and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001' and gea02i=4
group by gea00c) b
where a.compid=b.gea00c   and a.ddate='201311'

update a  set a.t016031changeamt=agsc05f,a.t016031changecount=b.cgca02c
from zongnedd_11 a,(select gea00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='016031'
and isnull(gea99i,0)=0 and gsc04c<>'5' and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001' and gea02i=4
group by gea00c) b
where a.compid=b.gea00c   and a.ddate='201311'

update a  set a.t018031changeamt=agsc05f,a.t018031changecount=b.cgca02c
from zongnedd_11 a,(select gea00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='018031'
and isnull(gea99i,0)=0 and gsc04c<>'5' and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001' and gea02i=4
group by gea00c) b
where a.compid=b.gea00c   and a.ddate='201311'


update a  set a.t018531changeamt=agsc05f,a.t018531changecount=b.cgca02c
from zongnedd_11 a,(select gea00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='018531'
and isnull(gea99i,0)=0 and gsc04c<>'5' and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001' and gea02i=4
group by gea00c) b
where a.compid=b.gea00c   and a.ddate='201311'


--016031	ÉÏº£°¢ÂêÄá6ÕÛ(2013)
--018031	ÉÏº£°¢ÂêÄá×ê¿¨(2013)
--018531	ÉÏº£°¢ÂêÄá½ð¿¨(2013)
--019031	ÉÏº£°¢ÂêÄá5ÕÛ(2013)
