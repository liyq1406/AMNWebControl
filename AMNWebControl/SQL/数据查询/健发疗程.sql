select a.changecompid,compname,changeproid,prjname,procount=convert(numeric(20,0),sum(changeprocount)/ysalecount),convert(numeric(20,0),sum(changeproamt))
 from mproexchangeinfo a,dproexchangeinfo b,projectinfo,companyinfo
where a.changecompid=b.changecompid and a.changebillid=b.changebillid
and b.changeproid=prjno and prjmodeId='SPM00101' and prjno in ('380093800','3610024')
and compno=a.changecompid
and changedate between '20140701' and '20140731' 
group by a.changecompid,compname,changeproid,prjname,ysalecount
order by changecompid,procount desc