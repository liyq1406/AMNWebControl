--create table staffbaseamt
--(
--	compno			varchar(10)		null,
--	compname		varchar(20)		null,
--	staffno			varchar(10)		null,
--	staffname		varchar(20)		null,
--	arrivaldate		varchar(20)		null,
--	arrivalmonth	int				null,
--	inseramt		float			null,
--	outamt			float			null,
--)

insert staffbaseamt(compno,compname,staffno,staffname,arrivaldate)
select a.compno,b.compname,staffno,staffname,arrivaldate 
from staffinfo a,companyinfo b
where a.compno=b.compno and curstate=2


select arrivaldate,datediff(m,arrivaldate,'20140210') from 
update staffbaseamt set arrivalmonth=datediff(m,arrivaldate,'20140115')  where arrivaldate>'20130430'

update staffbaseamt set arrivalmonth=datediff(m,'20130430','20140115')  where arrivaldate<'20130430'

update staffbaseamt set arrivalmonth=0  where arrivaldate>'20140115'

update staffbaseamt set inseramt=0
update a set inseramt=60 from staffbaseamt a,birthdayinfo b where compno=newcompno and newstaffno= a.staffno
update a set inseramt=60 from staffbaseamt a,birthdayinfo5 b where b.staffname=a.staffname and b.staffno= a.staffno


update c set inseramt=50 from birthdayinfo6 a,staffinfo b ,staffbaseamt c
where a.pcid=b.pccid and b.compno=c.compno and b.staffno=c.staffno



update a set staffinid=b.manageno from staffbaseamt a,staffinfo b
where a.compno=b.compno and a.staffno=b.staffno


update a set a.staffinid=b.manageno from staffbaseamt a,staffhistory b where ISNULL(staffinid,'')='' and a.staffno=b.oldempid and effectivedate>='20140224'

select * from staffbaseamt

alter table staffbaseamt
add computedate	varchar(10) null 

update staffbaseamt set computedate='20140228'


select * from staffbaseamt
