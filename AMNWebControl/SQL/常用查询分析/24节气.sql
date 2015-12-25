select a.changecompid,compname,changeproid,prjname,procount=convert(numeric(20,0),sum(changeprocount)/ysalecount),convert(numeric(20,0),sum(changeproamt))
 from mproexchangeinfo a,dproexchangeinfo b,projectinfo,companyinfo
where a.changecompid=b.changecompid and a.changebillid=b.changebillid
and b.changeproid=prjno and prjmodeId='SPM' and prjreporttype='11'
and compno=a.changecompid
group by a.changecompid,compname,changeproid,prjname,ysalecount
order by changecompid,procount desc

select changeproid,prjname,convert(numeric(20,0),sum(changeprocount)/ysalecount),changeproamt=convert(numeric(20,0),sum(changeproamt))
 from mproexchangeinfo a,dproexchangeinfo b,projectinfo,companyinfo
where a.changecompid=b.changecompid and a.changebillid=b.changebillid
and b.changeproid=prjno and prjmodeId='SPM' and prjreporttype='11'
and compno=a.changecompid
group by changeproid,prjname,ysalecount
order by changeproamt desc


select a.changecompid,compname,changeproamt=convert(numeric(20,0),sum(changeproamt))
 from mproexchangeinfo a,dproexchangeinfo b,projectinfo,companyinfo
where a.changecompid=b.changecompid and a.changebillid=b.changebillid
and b.changeproid=prjno and prjmodeId='SPM' and prjreporttype='11'
and compno=a.changecompid
group by a.changecompid,compname
order by changeproamt desc

select cardvesting,compname,COUNT(cardno) from nointernalcardinfo,companyinfo
 where isnull(uespassward,'')<>'' and cardstate=1 and compno=cardvesting group by cardvesting,compname
 order by cardvesting