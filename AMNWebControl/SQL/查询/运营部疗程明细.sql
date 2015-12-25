
create table #prjsaleorcostdetial
(
	compid		varchar(10)	null,
	compname	varchar(40)	null,
	prjno		varchar(20)	null,
	prjname		varchar(40)	null,	
	inseroutcount	float		null,
	inseroutamt	float		null,
	outercount	float		null,
	outeramt	float		null,
	costcount	float		null,
	costamt		float		null,
	cardno		varchar(20)		null,
)

insert #prjsaleorcostdetial(compid,compname,prjno,prjname)
select gaz02c,gae03c,gda01c,gda03c from gam26,gam05,gdm01,b_perf 
where gaz02c=gae01c and gaz01c='001' and gda00c='001' and gda13c='09' and bran_id=gaz02c and lvl=4

update #prjsaleorcostdetial set inseroutcount=totalcount,inseroutamt=totalamt,cardno=gcq01c
from #prjsaleorcostdetial ,
(select gcq00c,gcq01c,gcq03c,totalcount=SUM(ISNULL(gcq05f,0)),totalamt=SUM(ISNULL(gcq06f,0)+isnull(gcq06f,0)) 
  from gcm23 where gcq07d between '20130701' and '20130731' group by gcq00c,gcq01c,gcq03c) as totaltable
where compid=gcq00c and prjno=gcq03c  

update a set outercount=totalcount,costcount=totalamt
from #prjsaleorcostdetial a ,
(select compid,cardno,procid,totalcount=SUM(ISNULL(changecount,0)),totalamt=SUM(ISNULL(changeamt,0)) 
  from gcm06_exchange where ddate between '20130701' and '20130731' group by compid,cardno,procid) as b
where a.compid=b.compid and a.prjno=b.procid and a.cardno=b.cardno

update a set outercount=isnull(outercount,0)+totalcount,costcount=isnull(costcount,0)+totalamt
from #prjsaleorcostdetial a ,
(select compid,cardno,procid,totalcount=SUM(ISNULL(changecount,0)),totalamt=SUM(ISNULL(changeamt,0)) 
  from gcm06_pay where ddate between '20130701' and '20130731' group by compid,cardno,procid) as b
where a.compid=b.compid and a.prjno=b.procid and a.cardno=b.cardno

update a set costcount=totalcount,costamt=totalamt
from #prjsaleorcostdetial a ,
(select ggb00c,ggb03c,gga08c,totalcount=SUM(ISNULL(ggb05f,0)),totalamt=SUM(ISNULL(ggb11f,0)) 
  from ggm01,ggm02 where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130701' and '20130731' and ggb27c='9' group by ggb00c,ggb03c,gga08c) as b
where a.compid=b.ggb00c and a.prjno=b.ggb03c and a.cardno=b.gga08c

select '门店号'=compid,'门店名称'=compname,'项目编号'=prjno,'项目名称'=prjname,
 '疗程兑换次数'=sum(isnull(inseroutcount,0)),'疗程兑换金额'=sum(isnull(inseroutamt,0)),'疗程兑出次数'=sum(isnull(outercount,0)),
 '疗程兑出金额'=sum(isnull(outeramt,0)),'消耗次数'=sum(isnull(costcount,0)) ,'消耗金额'=sum(isnull(costamt,0))
 from #prjsaleorcostdetial  where ISNULL(inseroutcount,0)>0
 group by compid,compname,prjno,prjname
 order by compid,prjno
drop table #prjsaleorcostdetial


		