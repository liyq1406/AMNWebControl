select costcompno,costcompname,case when costtype=1 then '美发大项' else '美容大项' end ,
  convert(numeric(20,1),m1costamt),convert(numeric(20,1),m1costcount),convert(numeric(20,1),m1costamt/m1costcount),convert(numeric(20,1),m2costamt),convert(numeric(20,1),m2costcount),convert(numeric(20,1),m2costamt/m2costcount),
  convert(numeric(20,1),m3costamt),convert(numeric(20,1),m3costcount),convert(numeric(20,1),m3costamt/m3costcount),convert(numeric(20,1),m4costamt),convert(numeric(20,1),m4costcount),convert(numeric(20,1),m4costamt/m4costcount),
  convert(numeric(20,1),m5costamt),convert(numeric(20,1),m5costcount),convert(numeric(20,1),m5costamt/m5costcount),convert(numeric(20,1),m6costamt),convert(numeric(20,1),m6costcount),convert(numeric(20,1),m6costamt/m6costcount),
  convert(numeric(20,1),m7costamt),convert(numeric(20,1),m7costcount),convert(numeric(20,1),m7costamt/m7costcount),convert(numeric(20,1),m8costamt),convert(numeric(20,1),m8costcount),convert(numeric(20,1),m8costamt/m8costcount),
  convert(numeric(20,1),m9costamt),convert(numeric(20,1),m9costcount),convert(numeric(20,1),m9costamt/m9costcount),convert(numeric(20,1),m10costamt),convert(numeric(20,1),m10costcount),convert(numeric(20,1),m10costamt/m10costcount),
  convert(numeric(20,1),m11costamt),convert(numeric(20,1),m11costcount),convert(numeric(20,1),m11costamt/m11costcount),convert(numeric(20,1),m12costamt),convert(numeric(20,1),m12costcount),convert(numeric(20,1),m12costamt/m12costcount)
 from projectCostanlysis order by costtype ,costcompno
 
 
 
delete projectCostanlysis
insert projectCostanlysis(costcompno,costcompname,costtype)
select gae01c,gae03c,1 from gam05,b_perf where gae01c=bran_id and lvl=4

insert projectCostanlysis(costcompno,costcompname,costtype)
select gae01c,gae03c,2 from gam05,b_perf where gae01c=bran_id and lvl=4


update a  set m1costamt=ISNULL(ggb11f,0),m1costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130101' and '20130131'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m2costamt=ISNULL(ggb11f,0),m2costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130201' and '20130228'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m3costamt=ISNULL(ggb11f,0),m3costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130301' and '20130331'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m4costamt=ISNULL(ggb11f,0),m4costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130401' and '20130430'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m5costamt=ISNULL(ggb11f,0),m5costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130501' and '20130531'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m6costamt=ISNULL(ggb11f,0),m6costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130601' and '20130630'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m7costamt=ISNULL(ggb11f,0),m7costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130701' and '20130731'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m8costamt=ISNULL(ggb11f,0),m8costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130801' and '20130831'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m9costamt=ISNULL(ggb11f,0),m9costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130901' and '20130930'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m10costamt=ISNULL(ggb11f,0),m10costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20131001' and '20131031'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m11costamt=ISNULL(ggb11f,0),m11costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20131101' and '20131130'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

update a  set m12costamt=ISNULL(ggb11f,0),m12costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20131201' and '20131231'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('4') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=2

 


update a  set m1costamt=ISNULL(ggb11f,0),m1costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130101' and '20130131'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m2costamt=ISNULL(ggb11f,0),m2costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130201' and '20130228'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m3costamt=ISNULL(ggb11f,0),m3costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130301' and '20130331'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m4costamt=ISNULL(ggb11f,0),m4costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130401' and '20130430'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m5costamt=ISNULL(ggb11f,0),m5costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130501' and '20130531'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m6costamt=ISNULL(ggb11f,0),m6costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130601' and '20130630'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m7costamt=ISNULL(ggb11f,0),m7costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130701' and '20130731'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m8costamt=ISNULL(ggb11f,0),m8costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130801' and '20130831'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m9costamt=ISNULL(ggb11f,0),m9costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20130901' and '20130930'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m10costamt=ISNULL(ggb11f,0),m10costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20131001' and '20131031'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m11costamt=ISNULL(ggb11f,0),m11costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20131101' and '20131130'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1

update a  set m12costamt=ISNULL(ggb11f,0),m12costcount=ISNULL(gga01c,0)
from projectCostanlysis a,(select gga00c,ggb11f=SUM(ISNULL(ggb11f,0)),gga01c=COUNT(distinct gga01c)
from ggm01 ,ggm02 ,gdm01
where gga00c=ggb00c and gga01c=ggb01c and gga80d between '20131201' and '20131231'
and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
and gga00c=gda00c and ggb03c=gda01c and gda04c in ('3','6') and gda30i=1
group by gga00c) b
where a.costcompno=b.gga00c and a.costtype=1